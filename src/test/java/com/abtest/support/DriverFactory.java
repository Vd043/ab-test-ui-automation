package com.abtest.support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver createChrome() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1280,900");
        return new ChromeDriver(options);
    }
}
