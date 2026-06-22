package com.abtest.experiments;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ExperimentContextProvider {

    public void setVariantCookie(WebDriver driver, VariantIntent intent) {
        driver.manage().addCookie(new Cookie("exp_" + intent.experimentKey, intent.variantKey));
    }

    public VariantContext readFromPage(WebDriver driver, VariantIntent intent) {
        WebElement body = driver.findElement(By.tagName("body"));
        String variant = body.getAttribute("data-variant");
        if (variant == null || variant.isEmpty()) {
            variant = "control";
        }

        VariantContext ctx = new VariantContext(intent.experimentKey, variant, intent.variantKey.equals(variant));
        VariantContextHolder.set(ctx);
        return ctx;
    }
}
