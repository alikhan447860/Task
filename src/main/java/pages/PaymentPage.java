package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BasePage;
import driver.DriverFactory;

public class PaymentPage extends BasePage {

    // ==============================
    // Locators
    // ==============================
	private final By paymentFrame =
	        By.cssSelector("iframe.payment-container-iframe");

	private final By coreFrame =
	        By.name("core");

	private final By stripeFrame =
	        By.cssSelector("iframe[title='Secure payment input frame']");
	
    private final By paymentMethods =
            By.xpath("//div[@data-test-id='RadioGroup']//label");

    private final By totalPrice =
            By.xpath("//div[normalize-space()='Total']/following::div[contains(@class,'price')][1]");

    private final By cardNumber =
    	    By.xpath("//*[@id='payment-numberInput']");

    	private final By expiry =
    	    By.xpath("//*[@id='payment-expiryInput']");

    	private final By cvc =
    	    By.xpath("//*[@id='payment-cvcInput']");

    	private final By email =
    	    By.id("x-text-input-input-email");
    	
    	private final By emailFrame =
    	        By.cssSelector("iframe[src*='text-input/email']");

    // ==============================
    // Print Payment Methods
    // ==============================
    	public PaymentPage switchToPaymentFrame() {

    	    WebDriver driver = DriverFactory.getDriver();
    	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

    	    // Outer payment iframe
    	    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
    	            By.cssSelector("iframe.payment-container-iframe")));

    	    // Core iframe
    	    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
    	            By.name("core")));

    	    // Wait until Stripe iframe appears
    	    wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
    	            By.tagName("iframe"), 0));

    	    List<WebElement> frames = driver.findElements(By.tagName("iframe"));

    	    for (WebElement frame : frames) {

    	        driver.switchTo().frame(frame);

    	        if (driver.findElements(By.id("payment-numberInput")).size() > 0) {

    	            LOG.info("Card frame found.");

    	            return this;
    	        }

    	        driver.switchTo().parentFrame();
    	    }

    	    throw new RuntimeException("Card iframe not found.");
    	}
    	public PaymentPage waitForPaymentForm() {

    	    WebDriverWait wait =
    	            new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(60));

    	    wait.until(ExpectedConditions.presenceOfElementLocated(cardNumber));

    	    LOG.info("Card field loaded");

    	    return this;
    	}
    public PaymentPage printPaymentMethods() {

        LOG.info("Available Payment Methods");

        List<WebElement> methods =
                DriverFactory.getDriver().findElements(paymentMethods);

        List<String> methodNames = new ArrayList<>();

        for (WebElement method : methods) {

            String text = method.getText().trim();

            methodNames.add(text);

            LOG.info(text);
        }

        return this;
    }

    // ==============================
    // Get Total Price
    // ==============================

    public String getTotalPrice() {

        WebElement total =
                waitForVisibility(totalPrice);

        String price = total.getText().trim();

        LOG.info("Total Price : {}", price);

        return price;
    }

    // ==============================
    // Validate Price
    // ==============================

    public PaymentPage validatePrice(String expectedPrice) {

        String actualPrice = getTotalPrice();

        if (!actualPrice.equals(expectedPrice)) {

            throw new RuntimeException(
                    "Price Mismatch. Expected : "
                            + expectedPrice
                            + " Actual : "
                            + actualPrice);
        }

        LOG.info("Price Validated Successfully");

        return this;
    }
    // ==============================
    // Enter Card Number
    // ==============================

    public PaymentPage enterCardNumber(String cardNo) {

        LOG.info("Entering Card Number");

        type(waitForVisibility(cardNumber), cardNo);

        return this;
    }

    // ==============================
    // Enter Expiry Date
    // ==============================

    public PaymentPage enterExpiry(String expiryDate) {

        LOG.info("Entering Expiry Date");

        type(waitForVisibility(expiry), expiryDate);

        return this;
    }

    // ==============================
    // Enter CVV
    // ==============================

    public PaymentPage enterCVV(String cvv) {

        LOG.info("Entering CVV");

        type(waitForVisibility(cvc), cvv);

        return this;
    }

    // ==============================
    // Enter Email
    // ==============================

    public PaymentPage enterEmail(String emailAddress) {

        LOG.info("Entering Email");

        DriverFactory.getDriver().switchTo().parentFrame();

        WebDriverWait wait = new WebDriverWait(
                DriverFactory.getDriver(),
                Duration.ofSeconds(30));

        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(emailFrame));

        type(waitForVisibility(email), emailAddress);

        return this;
    }

    // ==============================
    // Fill Complete Card Details
    // ==============================

    public PaymentPage fillCardDetails(String cardNo,
                                       String expiryDate,
                                       String cvv,
                                       String emailAddress) {

        return enterCardNumber(cardNo)
                .enterExpiry(expiryDate)
                .enterCVV(cvv)
                .enterEmail(emailAddress);
    }
    
    
}