package com.abtest.support;

import java.net.URL;

public final class FixturePaths {

    private FixturePaths() {
    }

    public static String pdpFixture(String fileName) {
        URL resource = FixturePaths.class.getClassLoader().getResource("fixtures/" + fileName);
        if (resource == null) {
            throw new IllegalStateException("Missing fixture: " + fileName);
        }
        try {
            return resource.toURI().toString();
        } catch (Exception e) {
            throw new IllegalStateException("Bad fixture path: " + fileName, e);
        }
    }
}
