package base;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import driver.DriverFactory;
import utilities.ElementActions;
import utilities.JavaScriptUtil;
import utilities.WaitUtil;

/**
 * Base page class that all page objects extend.
 * Provides reusable wrapper methods for common Selenium operations.
 */
public abstract class BasePage {

    protected final Logger LOG = LogManager.getLogger(getClass());

    /**
     * Clicks on the given element.
     *
     * @param element the WebElement to click
     */
    public void click(WebElement element) {
        ElementActions.click(element);
    }

    /**
     * Types text into the given element after clearing existing text.
     *
     * @param element the WebElement to type into
     * @param text    the text to type
     */
    public void type(WebElement element, String text) {
        ElementActions.type(element, text);
    }

    /**
     * Clears the text from the given element.
     *
     * @param element the WebElement to clear
     */
    public void clear(WebElement element) {
        ElementActions.clear(element);
    }

    /**
     * Returns the visible text of the given element.
     *
     * @param element the WebElement to get text from
     * @return the visible text
     */
    public String getText(WebElement element) {
        return ElementActions.getText(element);
    }

    /**
     * Checks if the given element is displayed on the page.
     *
     * @param element the WebElement to check
     * @return true if displayed
     */
    public boolean isDisplayed(WebElement element) {
        return ElementActions.isDisplayed(element);
    }

    /**
     * Waits until the given element is visible on the page.
     *
     * @param element the WebElement to wait for
     * @return the visible WebElement
     */
    public WebElement waitForVisibility(WebElement element) {
        return WaitUtil.waitForVisibility(element);
    }
    
    
    public WebElement waitForVisibility(By locator) {
		WebElement element = DriverFactory.getDriver().findElement(locator);
		return WaitUtil.waitForVisibility(element);
	}
    /**
     * Waits until the given element is clickable.
     *
     * @param element the WebElement to wait for
     * @return the clickable WebElement
     */
    
    public WebElement waitForClickable(WebElement element) {
        return WaitUtil.waitForClickable(element);
    }
    
    protected WebElement waitForClickable(By locator) {
        WebElement element = DriverFactory.getDriver().findElement(locator);
        return WaitUtil.waitForClickable(element);
    }
    
    /**
     * Scrolls the page to bring the given element into view.
     *
     * @param element the WebElement to scroll to
     */
    
    public void scrollIntoView(WebElement element) {
        JavaScriptUtil.scrollIntoView(element);
    }

    /**
     * Clicks an element using JavaScript.
     *
     * @param element the WebElement to click
     */
    
    public void jsClick(WebElement element) {
        JavaScriptUtil.jsClick(element);
    }

    /**
     * Hovers over the given element.
     *
     * @param element the WebElement to hover over
     */
    public void hover(WebElement element) {
        ElementActions.hover(element);
    }

    /**
     * Double-clicks the given element.
     *
     * @param element the WebElement to double-click
     */
    public void doubleClick(WebElement element) {
        ElementActions.doubleClick(element);
    }

    /**
     * Right-clicks the given element.
     *
     * @param element the WebElement to right-click
     */
    public void rightClick(WebElement element) {
        ElementActions.rightClick(element);
    }

    /**
     * Selects an option from a dropdown by visible text.
     *
     * @param element   the dropdown WebElement
     * @param visibleText the visible text to select
     */
    public void selectByVisibleText(WebElement element, String visibleText) {
        ElementActions.selectByVisibleText(element, visibleText);
    }

    /**
     * Selects an option from a dropdown by value attribute.
     *
     * @param element the dropdown WebElement
     * @param value   the value attribute to select
     */
    public void selectByValue(WebElement element, String value) {
        ElementActions.selectByValue(element, value);
    }

    /**
     * Selects an option from a dropdown by index.
     *
     * @param element the dropdown WebElement
     * @param index   the index to select
     */
    public void selectByIndex(WebElement element, int index) {
        ElementActions.selectByIndex(element, index);
    }

    /**
     * Gets an attribute value from the given element.
     *
     * @param element   the WebElement
     * @param attribute the attribute name
     * @return the attribute value
     */
    public String getAttribute(WebElement element, String attribute) {
        return ElementActions.getAttribute(element, attribute);
    }
    
    
    public void jsScrollToLocatorTest(WebElement element) {
    	 WebDriverWait wait = new WebDriverWait(
  	            DriverFactory.getDriver(),
  	            Duration.ofSeconds(20));
    	  ((JavascriptExecutor) DriverFactory.getDriver())
          .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
 
    }
    
    public void jsClickLocatorTest(WebElement element) {
    	 WebDriverWait wait = new WebDriverWait(
  	            DriverFactory.getDriver(),
  	            Duration.ofSeconds(20));
  	  ((JavascriptExecutor) DriverFactory.getDriver())
		  .executeScript("arguments[0].click();", element);
	}
}
