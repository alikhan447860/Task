package pages;

import base.BasePage;
import driver.DriverFactory;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object for Stumble Guys Login Flow
 */
public class LoginPage extends BasePage {

    /* ===========================
            LOCATORS
       =========================== */

    // Home Page
	private final By profileIcon =
		    By.xpath("(//button[.//img[@alt='avatar']])[2]");

    private final By loginButton =
            By.xpath("(//button[contains(.,'Login')])[2]");

    private final By continueWithEmailButton =
            By.xpath("//button[.//span[contains(normalize-space(),'Continue with email')]]");

    // Scopely Login Page
    private final By emailTextbox =
            By.cssSelector("input[name='email']");
    
    private final By continueButton =
            By.cssSelector("button[data-test-id='site-email-input-submit-button']");
    private final By otpBoxes =
            By.cssSelector("input[inputmode='numeric']");
    
    private final By signInButton =
            By.cssSelector("button[class*='OneTimeCodeInput_submitButton']");

    /* ===========================
        COOKIE ACCEPT (Shadow DOM)
       =========================== */

    /**
     * Accepts cookie popup if it is displayed.
     * Cookie popup is inside Shadow DOM.
     */
	public LoginPage acceptCookiesIfPresent() {

		try {

			WebElement host = DriverFactory.getDriver().findElement(By.id("usercentrics-cmp-ui"));

			SearchContext shadow = host.getShadowRoot();

			WebElement accept = shadow.findElement(By.cssSelector("#accept"));

			System.out.println("Accept Button Found");

			accept.click();

			System.out.println("Clicked");

		} catch (Exception e) {

			e.printStackTrace(); // <-- IMPORTANT

		}

		return this;
	}

    /* ===========================
            PROFILE ICON
       =========================== */

    /**
     * Click Profile Icon
     */
	public LoginPage clickProfileIcon() {

	    LOG.info("Clicking Profile Icon");

	    WebDriverWait wait = new WebDriverWait(
	            DriverFactory.getDriver(),
	            Duration.ofSeconds(20));

	    WebElement button = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(profileIcon));

	    ((JavascriptExecutor) DriverFactory.getDriver())
	            .executeScript("arguments[0].scrollIntoView({block:'center'});", button);

	    ((JavascriptExecutor) DriverFactory.getDriver())
	            .executeScript("arguments[0].click();", button);

	    return this;
	}

    /* ===========================
            LOGIN BUTTON
       =========================== */

    /**
     * Click Login Button
     */
    public LoginPage clickLoginButton() {

        LOG.info("Clicking Login Button");

        WebElement element =
                DriverFactory.getDriver().findElement(loginButton);

        waitForClickable(element);

        click(element);

        return this;
    }
    public LoginPage clickContinueWithEmail() {

        LOG.info("Clicking Continue with Email");

        WebElement button = waitForClickable(continueWithEmailButton);

        ((JavascriptExecutor) DriverFactory.getDriver())
                .executeScript("arguments[0].click();", button);

        return this;
    }    
    
    public LoginPage enterEmail(String email) {

        LOG.info("Entering Email");

        WebElement textbox = waitForVisibility(emailTextbox);

        textbox.clear();
        textbox.sendKeys(email);

        return this;
    }
    public LoginPage clickContinueButton() {

        LOG.info("Clicking Continue Button");

        WebElement button = DriverFactory.getDriver()
                .findElement(continueButton);

        waitForClickable(button);

        ((JavascriptExecutor) DriverFactory.getDriver())
                .executeScript("arguments[0].click();", button);

        return this;
    }
    public LoginPage waitForOTPPage() {

        LOG.info("Waiting for OTP Page");

        WebDriverWait wait = new WebDriverWait(
                DriverFactory.getDriver(),
                Duration.ofSeconds(30));

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                otpBoxes, 5));

        LOG.info("OTP Screen Loaded Successfully");

        return this;
   
    	}
    public LoginPage enterOTP(String otp) {

        LOG.info("Entering OTP: {}", otp);

        WebDriverWait wait = new WebDriverWait(
                DriverFactory.getDriver(),
                Duration.ofSeconds(20));

        List<WebElement> boxes = wait.until(
                ExpectedConditions.numberOfElementsToBeMoreThan(otpBoxes, 5));

        if (otp.length() != 6) {
            throw new IllegalArgumentException("OTP must be 6 digits.");
        }

        for (int i = 0; i < 6; i++) {
            boxes.get(i).clear();
            boxes.get(i).sendKeys(String.valueOf(otp.charAt(i)));
        }

        return this;
    }
    public LoginPage clickSignIn() {

        LOG.info("Waiting for Sign In button to become enabled");

        WebDriverWait wait = new WebDriverWait(
                DriverFactory.getDriver(),
                Duration.ofSeconds(20));

        WebElement button = wait.until(driver -> {

            WebElement btn = driver.findElement(signInButton);

            return btn.isEnabled() ? btn : null;
        });

        ((JavascriptExecutor) DriverFactory.getDriver())
                .executeScript("arguments[0].click();", button);

        LOG.info("Clicked Sign In");

        return this;
    }
}