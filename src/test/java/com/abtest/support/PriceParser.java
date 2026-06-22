package com.abtest.support;

import java.math.BigDecimal;

public final class PriceParser {

    private PriceParser() {
    }

    public static BigDecimal parse(String raw) {
        String cleaned = raw.replaceAll("[^0-9.]", "");
        return new BigDecimal(cleaned);
    }
}
