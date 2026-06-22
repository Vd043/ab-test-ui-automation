package com.abtest.support;

import java.time.Duration;

public final class TestConfig {

    private TestConfig() {
    }

    public static Duration waitTimeout() {
        int seconds = Integer.getInteger("wait.timeout.seconds", 8);
        return Duration.ofSeconds(seconds);
    }
}
