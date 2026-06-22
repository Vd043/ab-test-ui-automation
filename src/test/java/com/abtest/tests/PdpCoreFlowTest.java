package com.abtest.tests;

import com.abtest.api.CatalogClient;
import com.abtest.experiments.ExperimentContextProvider;
import com.abtest.experiments.VariantContext;
import com.abtest.experiments.VariantContextHolder;
import com.abtest.experiments.VariantIntent;
import com.abtest.pages.PdpPage;
import com.abtest.support.DriverFactory;
import com.abtest.support.FixturePaths;
import com.abtest.support.PriceParser;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class PdpCoreFlowTest {

    private WebDriver driver;
    private ExperimentContextProvider contextProvider;
    private CatalogClient catalog;

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.createChrome();
        contextProvider = new ExperimentContextProvider();
        catalog = new CatalogClient();
    }

    @AfterMethod
    public void tearDown() {
        VariantContextHolder.clear();
        if (driver != null) {
            driver.quit();
        }
    }

    @DataProvider(name = "pdpFixtures")
    public Object[][] pdpFixtures() {
        return new Object[][]{
                {"control-pdp.html", "control"},
                {"sticky-cta-pdp.html", "sticky-bottom"}
        };
    }

    @Test(groups = {"p0", "smoke", "exp-pdp-cta"}, dataProvider = "pdpFixtures")
    public void addToCartOnControlAndStickyVariant(String htmlFile, String variant) {
        VariantIntent intent = new VariantIntent("pdp-cta", variant);
        driver.get(FixturePaths.pdpFixture(htmlFile));

        VariantContext ctx = contextProvider.readFromPage(driver, intent);
        assertThat(ctx.variantKey).isEqualTo(variant);

        PdpPage pdp = new PdpPage(driver, ctx);
        assertThat(pdp.getProductTitle()).isEqualTo(catalog.getTitle("cashmere-sweater"));
        assertThat(pdp.getDisplayedPrice()).isEqualByComparingTo(catalog.getPrice("cashmere-sweater"));

        pdp.addToCart();
        assertThat(pdp.getCartCount()).isEqualTo(1);
    }

    @Test(groups = {"smoke"})
    public void priceParserHandlesDollarSign() {
        assertThat(PriceParser.parse("$89.00")).isEqualByComparingTo(new BigDecimal("89.00"));
    }

    @Test(groups = {"smoke"})
    public void catalogFixtureHasProduct() {
        assertThat(catalog.getTitle("cashmere-sweater")).isEqualTo("Cashmere Sweater");
        assertThat(catalog.getPrice("cashmere-sweater")).isEqualByComparingTo(new BigDecimal("89.00"));
    }
}
