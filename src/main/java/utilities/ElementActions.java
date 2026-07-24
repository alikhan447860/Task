package utilities;

import driver.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

/**
 * Reusable element interaction utilities.
 * Provides a clean API for common Selenium operations with built-in waits.
 */
public final class ElementActions {

    private static final Logger LOG = LogManager.getLogger(ElementActions.class);

    private ElementActions() {
        throw new IllegalStateException("Utility class cannot be instantiated");
    }

    /**
     * Clicks on the given element after waiting for it to be clickable.
     *
     * @param element the WebElement to click
     */
    public static void click(WebElement element) {
        LOG.debug("Clicking element: {}", element);
        WaitUtil.waitForClickable(element).click();
        LOG.info("Element clicked successfully");
    }

    /**
     * Types text into the given element after clearing any existing text.
     *
     * @param element the WebElement to type into
     * @param text    the text to type
     */
    public static void type(WebElement element, String text) {
        LOG.debug("Typing '{}' into element: {}", text, element);
        WaitUtil.waitForVisibility(element);
        element.clear();
        element.sendKeys(text);
        LOG.info("Text typed successfully into element");
    }

    /**
     * Clears the text from the given element.
     *
     * @param element the WebElement to clear
     */
    public static void clear(WebElement element) {
        LOG.debug("Clearing element: {}", element);
        WaitUtil.waitForVisibility(element);
        element.clear();
        LOG.info("Element cleared successfully");
    }

    /**
     * Returns the visible text of the given element.
     *
     * @param element the WebElement to get text from
     * @return the visible text of the element
     */
    public static String getText(WebElement element) {
        LOG.debug("Getting text from element: {}", element);
        String text = WaitUtil.waitForVisibility(element).getText();
        LOG.info("Text retrieved: '{}'", text);
        return text;
    }

    /**
     * Returns the value attribute of the given element.
     *
     * @param element the WebElement to get the value from
     * @return the value attribute of the element
     */
    public static String getValue(WebElement element) {
        LOG.debug("Getting value attribute from element: {}", element);
        WaitUtil.waitForVisibility(element);
        String value = element.getAttribute("value");
        LOG.info("Value retrieved: '{}'", value);
        return value;
    }

    /**
     * Checks if the given element is displayed on the page.
     *
     * @param element the WebElement to check
     * @return true if the element is displayed, false otherwise
     */
    public static boolean isDisplayed(WebElement element) {
        try {
            boolean displayed = WaitUtil.waitForVisibility(element).isDisplayed();
            LOG.debug("Element displayed status: {}", displayed);
            return displayed;
        } catch (TimeoutException | NoSuchElementException e) {
            LOG.debug("Element is not displayed: {}", element);
            return false;
        }
    }

    /**
     * Scrolls the given element into view using JavaScript.
     *
     * @param element the WebElement to scroll to
     */
    public static void scrollIntoView(WebElement element) {
        LOG.debug("Scrolling element into view: {}", element);
        JavaScriptUtil.scrollIntoView(element);
        LOG.info("Element scrolled into view");
    }

    /**
     * Clicks an element using JavaScript executor.
     *
     * @param element the WebElement to click via JavaScript
     */
    public static void jsClick(WebElement element) {
        LOG.debug("JavaScript clicking element: {}", element);
        JavaScriptUtil.jsClick(element);
        LOG.info("Element clicked via JavaScript");
    }

    /**
     * Performs a hover (mouse over) action on the given element.
     *
     * @param element the WebElement to hover over
     */
    public static void hover(WebElement element) {
        LOG.debug("Hovering over element: {}", element);
        Actions actions = new Actions(DriverFactory.getDriver());
        actions.moveToElement(WaitUtil.waitForVisibility(element)).perform();
        LOG.info("Hover action performed");
    }

    /**
     * Performs a double-click action on the given element.
     *
     * @param element the WebElement to double-click
     */
    public static void doubleClick(WebElement element) {
        LOG.debug("Double-clicking element: {}", element);
        Actions actions = new Actions(DriverFactory.getDriver());
        actions.doubleClick(WaitUtil.waitForClickable(element)).perform();
        LOG.info("Double-click performed");
    }

    /**
     * Performs a right-click (context click) action on the given element.
     *
     * @param element the WebElement to right-click
     */
    public static void rightClick(WebElement element) {
        LOG.debug("Right-clicking element: {}", element);
        Actions actions = new Actions(DriverFactory.getDriver());
        actions.contextClick(WaitUtil.waitForClickable(element)).perform();
        LOG.info("Right-click performed");
    }

    /**
     * Selects an option from a dropdown by visible text.
     *
     * @param element   the dropdown WebElement
     * @param visibleText the visible text to select
     */
    public static void selectByVisibleText(WebElement element, String visibleText) {
        LOG.debug("Selecting '{}' by visible text from dropdown: {}", visibleText, element);
        WaitUtil.waitForVisibility(element);
        Select select = new Select(element);
        select.selectByVisibleText(visibleText);
        LOG.info("Option '{}' selected by visible text", visibleText);
    }

    /**
     * Selects an option from a dropdown by value attribute.
     *
     * @param element the dropdown WebElement
     * @param value   the value attribute to select
     */
    public static void selectByValue(WebElement element, String value) {
        LOG.debug("Selecting '{}' by value from dropdown: {}", value, element);
        WaitUtil.waitForVisibility(element);
        Select select = new Select(element);
        select.selectByValue(value);
        LOG.info("Option '{}' selected by value", value);
    }

    /**
     * Selects an option from a dropdown by index.
     *
     * @param element the dropdown WebElement
     * @param index   the index to select
     */
    public static void selectByIndex(WebElement element, int index) {
        LOG.debug("Selecting index {} from dropdown: {}", index, element);
        WaitUtil.waitForVisibility(element);
        Select select = new Select(element);
        select.selectByIndex(index);
        LOG.info("Option at index {} selected", index);
    }

    /**
     * Gets an attribute value from the given element.
     *
     * @param element   the WebElement
     * @param attribute the attribute name
     * @return the attribute value, or null if not present
     */
    public static String getAttribute(WebElement element, String attribute) {
        LOG.debug("Getting attribute '{}' from element: {}", attribute, element);
        WaitUtil.waitForVisibility(element);
        String value = element.getAttribute(attribute);
        LOG.info("Attribute '{}' = '{}'", attribute, value);
        return value;
    }

    /**
     * Checks if the given element is enabled.
     *
     * @param element the WebElement to check
     * @return true if the element is enabled
     */
    public static boolean isEnabled(WebElement element) {
        LOG.debug("Checking if element is enabled: {}", element);
        boolean enabled = WaitUtil.waitForVisibility(element).isEnabled();
        LOG.debug("Element enabled status: {}", enabled);
        return enabled;
    }
}
