package utilities;

import config.ConfigReader;
import constants.FrameworkConstants;
import driver.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Reusable explicit wait utilities.
 * Wraps WebDriverWait for common wait conditions.
 */
public final class WaitUtil {

    private static final Logger LOG = LogManager.getLogger(WaitUtil.class);

    private WaitUtil() {
        throw new IllegalStateException("Utility class cannot be instantiated");
    }

    /**
     * Creates a WebDriverWait instance with the specified timeout.
     *
     * @param timeout duration in seconds
     * @return a new WebDriverWait instance
     */
    private static WebDriverWait getWait(Duration timeout) {
        return new WebDriverWait(DriverFactory.getDriver(), timeout);
    }

    /**
     * Creates a WebDriverWait with the default timeout from config.
     *
     * @return a new WebDriverWait instance
     */
    private static WebDriverWait getDefaultWait() {
        return getWait(Duration.ofSeconds(ConfigReader.getInstance()
                .getIntProperty("explicitWait", FrameworkConstants.DEFAULT_EXPLICIT_WAIT)));
    }

    /**
     * Waits until the given element is visible on the page.
     *
     * @param element the WebElement to wait for
     * @return the visible WebElement
     */
    public static WebElement waitForVisibility(WebElement element) {
        LOG.debug("Waiting for element to be visible: {}", element);
        return getDefaultWait().until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits until the given element is visible with a custom timeout.
     *
     * @param element the WebElement to wait for
     * @param timeout custom timeout in seconds
     * @return the visible WebElement
     */
    public static WebElement waitForVisibility(WebElement element, int timeout) {
        LOG.debug("Waiting for element to be visible ({}s): {}", timeout, element);
        return getWait(Duration.ofSeconds(timeout)).until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits until the given element is clickable.
     *
     * @param element the WebElement to wait for
     * @return the clickable WebElement
     */
    public static WebElement waitForClickable(WebElement element) {
        LOG.debug("Waiting for element to be clickable: {}", element);
        return getDefaultWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Waits until the given element is clickable with a custom timeout.
     *
     * @param element the WebElement to wait for
     * @param timeout custom timeout in seconds
     * @return the clickable WebElement
     */
    public static WebElement waitForClickable(WebElement element, int timeout) {
        LOG.debug("Waiting for element to be clickable ({}s): {}", timeout, element);
        return getWait(Duration.ofSeconds(timeout)).until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Waits until the given element is no longer visible.
     *
     * @param element the WebElement to wait for invisibility
     */
    public static void waitForInvisibility(WebElement element) {
        LOG.debug("Waiting for element to be invisible: {}", element);
        getDefaultWait().until(ExpectedConditions.invisibilityOf(element));
    }

    /**
     * Waits for a custom ExpectedCondition.
     *
     * @param condition the condition to wait for
     * @param <T>       the return type of the condition
     * @return the result of the condition
     */
    public static <T> T waitForCondition(ExpectedCondition<T> condition) {
        LOG.debug("Waiting for custom condition: {}", condition);
        return getDefaultWait().until(condition);
    }

    /**
     * Waits until the page title contains the given text.
     *
     * @param titleText the text to wait for in the page title
     * @return the page title once it contains the text
     */
    public static String waitForTitleContains(String titleText) {
        LOG.debug("Waiting for page title to contain: '{}'", titleText);
        getDefaultWait().until(ExpectedConditions.titleContains(titleText));
        return DriverFactory.getDriver().getTitle();
    }

    /**
     * Waits until the page URL contains the given text.
     *
     * @param urlText the text to wait for in the URL
     */
    public static void waitForUrlContains(String urlText) {
        LOG.debug("Waiting for URL to contain: '{}'", urlText);
        getDefaultWait().until(ExpectedConditions.urlContains(urlText));
    }

    /**
     * Waits until the page has finished loading.
     */
    public static void waitForPageLoad() {
        LOG.debug("Waiting for page to fully load");
        WebDriver driver = DriverFactory.getDriver();
        getDefaultWait().until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }
}
