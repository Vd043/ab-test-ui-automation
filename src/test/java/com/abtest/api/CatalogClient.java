package com.abtest.api;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

public class CatalogClient {

    private String json;

    public String getTitle(String sku) {
        loadJson();
        checkSku(sku);
        return extractValue("title");
    }

    public BigDecimal getPrice(String sku) {
        loadJson();
        checkSku(sku);
        return new BigDecimal(extractValue("price"));
    }

    private void loadJson() {
        if (json != null) {
            return;
        }
        InputStream in = CatalogClient.class.getClassLoader().getResourceAsStream("catalog/product.json");
        if (in == null) {
            throw new IllegalStateException("catalog/product.json missing from classpath");
        }
        try (in) {
            json = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalStateException("could not read catalog fixture", e);
        }
    }

    private void checkSku(String sku) {
        if (!json.contains("\"sku\": \"" + sku + "\"")) {
            throw new IllegalArgumentException("unknown sku: " + sku);
        }
    }

    private String extractValue(String field) {
        String token = "\"" + field + "\"";
        int start = json.indexOf(token);
        if (start < 0) {
            throw new IllegalStateException("missing field: " + field);
        }
        start = json.indexOf(':', start) + 1;
        while (start < json.length() && (json.charAt(start) == ' ' || json.charAt(start) == '\"')) {
            start++;
        }
        int end = start;
        while (end < json.length() && json.charAt(end) != '\"' && json.charAt(end) != ',' && json.charAt(end) != '}') {
            end++;
        }
        return json.substring(start, end).trim();
    }
}
