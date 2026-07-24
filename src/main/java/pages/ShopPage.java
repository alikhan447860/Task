package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BasePage;
import driver.DriverFactory;
import utilities.ScreenshotUtil;

public class ShopPage extends BasePage {

    // ==============================
    // Locators
    // ==============================

    private final By closePopupButton =
            By.cssSelector("button.absolute.-right-2.-top-4");

    private final By profileIcon =
            By.xpath("(//button[.//img[@alt='avatar']])[2]");

    private final By shopButton =
            By.xpath("//*[@id=\"__next\"]/div/nav/div[2]/div/div[2]/a[3]/div");

    private final By categories =
            By.xpath("//div[contains(@class,'md:flex')]//span[contains(@class,'text-border-2')]");

    private final By vaultCategory =
            By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div[2]/button[1]");

    private final By firstProductPriceButton =
            By.xpath("(//button[contains(@class,'Card_card__price_button')])[1]");

    private String selectedProductPrice;

    // ==============================
    // Close Welcome Popup
    // ==============================

    public ShopPage closeWelcomePopup() {

        LOG.info("Closing Welcome Popup");

        try {

            WebElement popup =
                    waitForClickable(closePopupButton);

            jsClick(popup);

            LOG.info("Popup Closed");

        } catch (Exception e) {

            LOG.info("Popup Not Displayed");
        }

        return this;
    }

    // ==============================
    // Click Profile Icon
    // ==============================

    public ShopPage clickProfileIcon() {

        LOG.info("Clicking Profile Icon");

        WebElement icon =
                waitForClickable(profileIcon);

        scrollIntoView(icon);

        jsClick(icon);

        return this;
    }

    // ==============================
    // Click Shop
    // ==============================

    public ShopPage clickShop() {

        LOG.info("Opening Shop");

        WebElement shop =
                waitForClickable(shopButton);

        jsClick(shop);

        return this;
    }

    // ==============================
    // Validate Categories
    // ==============================

    public ShopPage validateAndPrintCategories() {

        LOG.info("Reading Shop Categories");

        List<WebElement> categoryList =
                DriverFactory.getDriver().findElements(categories);

        List<String> names = new ArrayList<>();

        for (WebElement element : categoryList) {

            String name = element.getText().trim();

            names.add(name);

            LOG.info("Category : {}", name);
        }

        boolean hasVault = names.stream()
                .anyMatch(name -> name.equalsIgnoreCase("The Vault"));

        boolean hasGems = names.stream()
                .anyMatch(name -> name.equalsIgnoreCase("Gems"));

        boolean hasCodes = names.stream()
                .anyMatch(name -> name.equalsIgnoreCase("Codes"));

        if (!hasVault) {
            throw new RuntimeException("The Vault Category Missing");
        }

        if (!hasGems) {
            throw new RuntimeException("Gems Category Missing");
        }

        if (!hasCodes) {
            throw new RuntimeException("Codes Category Missing");
        }

        LOG.info("All Categories Validated Successfully");

        return this;
    }
    // ==============================
    // Click The Vault
    // ==============================

    public ShopPage clickVault() {

        LOG.info("Clicking The Vault Category");

        WebElement vault = waitForVisibility(vaultCategory);

        ((JavascriptExecutor) DriverFactory.getDriver())
                .executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        vault);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        ((JavascriptExecutor) DriverFactory.getDriver())
                .executeScript("window.scrollBy(0,250);");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        ((JavascriptExecutor) DriverFactory.getDriver())
                .executeScript("arguments[0].click();", vault);

        return this;
    }

    // ==============================
    // Select First Product
    // ==============================

    public ShopPage selectFirstProduct() {

        LOG.info("Selecting First Product");

        WebElement product = waitForVisibility(firstProductPriceButton);

        selectedProductPrice = product.getText().trim();

        LOG.info("Selected Product Price : {}", selectedProductPrice);

        scrollIntoView(product);

        jsClick(product);

        return this;
    }

    // ==============================
    // Get Selected Product Price
    // ==============================

    public String getSelectedProductPrice() {

        return selectedProductPrice;
    }

    // ==============================
    // Print Selected Product Price
    // ==============================

    public ShopPage printSelectedPrice() {

        LOG.info("Product Price : {}", selectedProductPrice);

        return this;
    }

    // ==============================
    // Click Purchase Button
    // ==============================

    public PaymentPage waitForPaymentPage() {

        LOG.info("Waiting for Payment Page...");

        WebDriverWait wait = new WebDriverWait(
                DriverFactory.getDriver(),
                Duration.ofSeconds(30));

        // Wait for payment iframe
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("iframe.payment-container-iframe")));

        LOG.info("Payment Page Opened Successfully");

        String currentUrl = DriverFactory.getDriver().getCurrentUrl();

        LOG.info("Current URL : {}", currentUrl);

        ScreenshotUtil.captureScreenshot("PaymentPage");

        return new PaymentPage();
    }

}
