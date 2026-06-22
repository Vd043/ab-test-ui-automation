package com.abtest.pages;

import com.abtest.experiments.VariantContext;
import com.abtest.locators.MultiStrategyLocator;
import com.abtest.support.PriceParser;
import com.abtest.support.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;

public class PdpPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private MultiStrategyLocator locator;

    public PdpPage(WebDriver driver, VariantContext variant) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TestConfig.waitTimeout());
        this.locator = new MultiStrategyLocator(driver);
    }

    public String getProductTitle() {
        WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-testid='product-title'], h1")));
        return title.getText().trim();
    }

    public BigDecimal getDisplayedPrice() {
        WebElement price = locator.findFirstVisible(
                By.cssSelector("[data-testid='sticky-price']"),
                By.cssSelector("[data-testid='price']"));
        return PriceParser.parse(price.getText());
    }

    public void addToCart() {
        WebElement cta = locator.findFirstVisible(
                By.cssSelector(".sticky-cta [data-testid='add-to-cart']"),
                By.cssSelector("[data-testid='add-to-cart']"),
                By.xpath("//button[contains(., 'Add to Cart')]"));
        cta.click();
        wait.until(d -> getCartCount() > 0);
    }

    public int getCartCount() {
        WebElement count = driver.findElement(By.cssSelector("[data-testid='cart-count'], #cart-count"));
        return Integer.parseInt(count.getText().trim());
    }
}
