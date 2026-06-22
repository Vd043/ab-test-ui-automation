package com.abtest.locators;

import com.abtest.support.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MultiStrategyLocator {

    private final WebDriverWait wait;

    public MultiStrategyLocator(WebDriver driver) {
        this.wait = new WebDriverWait(driver, TestConfig.waitTimeout());
    }

    public WebElement findFirstVisible(By... locators) {
        WebElement found = wait.until(drv -> {
            for (By by : locators) {
                try {
                    WebElement el = drv.findElement(by);
                    if (el.isDisplayed()) {
                        return el;
                    }
                } catch (NoSuchElementException | StaleElementReferenceException ignored) {
                }
            }
            return null;
        });

        if (found == null) {
            throw new IllegalStateException("Could not find visible element for locators provided");
        }
        return found;
    }
}
