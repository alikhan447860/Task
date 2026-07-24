package utilities;

import driver.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

/**
 * Reusable JavaScript executor utilities.
 * Provides methods for executing JavaScript commands on the page.
 */
public final class JavaScriptUtil {

    private static final Logger LOG = LogManager.getLogger(JavaScriptUtil.class);

    private JavaScriptUtil() {
        throw new IllegalStateException("Utility class cannot be instantiated");
    }

    /**
     * Returns a JavascriptExecutor instance for the current driver.
     *
     * @return a JavascriptExecutor instance
     */
    private static JavascriptExecutor getJsExecutor() {
        return (JavascriptExecutor) DriverFactory.getDriver();
    }

    /**
     * Clicks an element using JavaScript.
     *
     * @param element the WebElement to click
     */
    public static void jsClick(WebElement element) {
        LOG.debug("Performing JavaScript click on element: {}", element);
        getJsExecutor().executeScript("arguments[0].click();", element);
        LOG.info("JavaScript click executed successfully");
    }

    /**
     * Scrolls the page to bring the given element into view.
     *
     * @param element the WebElement to scroll to
     */
    public static void scrollIntoView(WebElement element) {
        LOG.debug("Scrolling element into view via JS: {}", element);
        getJsExecutor().executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
        LOG.info("Element scrolled into view");
    }

    /**
     * Scrolls the page by the given pixel offsets.
     *
     * @param x the horizontal scroll offset
     * @param y the vertical scroll offset
     */
    public static void scrollTo(int x, int y) {
        LOG.debug("Scrolling page to position ({}, {})", x, y);
        getJsExecutor().executeScript("window.scrollTo(" + x + ", " + y + ");");
    }

    /**
     * Scrolls down by the given pixel amount.
     *
     * @param pixels the number of pixels to scroll down
     */
    public static void scrollDown(int pixels) {
        LOG.debug("Scrolling down by {} pixels", pixels);
        getJsExecutor().executeScript("window.scrollBy(0, " + pixels + ");");
    }

    /**
     * Scrolls up by the given pixel amount.
     *
     * @param pixels the number of pixels to scroll up
     */
    public static void scrollUp(int pixels) {
        LOG.debug("Scrolling up by {} pixels", pixels);
        getJsExecutor().executeScript("window.scrollBy(0, -" + pixels + ");");
    }

    /**
     * Scrolls to the top of the page.
     */
    public static void scrollToTop() {
        LOG.debug("Scrolling to top of page");
        getJsExecutor().executeScript("window.scrollTo(0, 0);");
    }

    /**
     * Scrolls to the bottom of the page.
     */
    public static void scrollToBottom() {
        LOG.debug("Scrolling to bottom of page");
        getJsExecutor().executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    /**
     * Sets a value on an input element using JavaScript.
     *
     * @param element the input WebElement
     * @param value   the value to set
     */
    public static void setValue(WebElement element, String value) {
        LOG.debug("Setting value '{}' on element via JS: {}", value, element);
        getJsExecutor().executeScript("arguments[0].value='" + value + "';", element);
        LOG.info("Value set successfully via JavaScript");
    }

    /**
     * Highlights an element by adding a border (useful for debugging).
     *
     * @param element the WebElement to highlight
     */
    public static void highlightElement(WebElement element) {
        LOG.debug("Highlighting element: {}", element);
        getJsExecutor().executeScript(
                "arguments[0].style.border='3px solid red';", element);
    }

    /**
     * Removes the readonly attribute from an element.
     *
     * @param element the WebElement
     */
    public static void removeReadonly(WebElement element) {
        LOG.debug("Removing readonly attribute from element: {}", element);
        getJsExecutor().executeScript("arguments[0].removeAttribute('readonly');", element);
    }

    /**
     * Executes a custom JavaScript snippet.
     *
     * @param script the JavaScript code to execute
     * @return the result of the JavaScript execution
     */
    public static Object executeScript(String script) {
        LOG.debug("Executing custom JavaScript: {}", script);
        return getJsExecutor().executeScript(script);
    }
}
