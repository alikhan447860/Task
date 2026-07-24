package driver;

import constants.FrameworkConstants;
import config.ConfigReader;
import exceptions.FrameworkException;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.Locale;

/**
 * Factory class for creating and managing WebDriver instances.
 * Uses ThreadLocal to support parallel test execution.
 */
public final class DriverFactory {

    private static final Logger LOG = LogManager.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {
        throw new IllegalStateException("Utility class cannot be instantiated");
    }

    /**
     * Returns the WebDriver instance for the current thread.
     *
     * @return the current thread's WebDriver
     */
    public static WebDriver getDriver() {
        if (DRIVER.get() == null) {
            throw new FrameworkException("WebDriver is not initialized. Call initDriver() first.");
        }
        return DRIVER.get();
    }

    /**
     * Initializes the WebDriver based on the browser specified in config.properties.
     * Configures browser options, timeouts, and other settings.
     */
    public static void initDriver() {
        String browser = ConfigReader.getInstance().getProperty("browser", "chrome").toLowerCase(Locale.ROOT);
        boolean headless = ConfigReader.getInstance().getBooleanProperty("headless", false);
        int explicitWait = ConfigReader.getInstance().getIntProperty("explicitWait",
                FrameworkConstants.DEFAULT_EXPLICIT_WAIT);

        LOG.info("Initializing WebDriver for browser: '{}' | headless: {}", browser, headless);

        WebDriver driver = createDriver(browser, headless);

        configureDriver(driver, explicitWait);

        DRIVER.set(driver);
        LOG.info("WebDriver initialized successfully for thread: {}", Thread.currentThread().getId());
    }

    /**
     * Creates a WebDriver instance for the specified browser.
     *
     * @param browser  the browser name
     * @param headless whether to run in headless mode
     * @return a new WebDriver instance
     */
    private static WebDriver createDriver(String browser, boolean headless) {
        switch (browser) {
            case FrameworkConstants.BROWSER_CHROME:
                return createChromeDriver(headless);
            case FrameworkConstants.BROWSER_FIREFOX:
                return createFirefoxDriver(headless);
            case FrameworkConstants.BROWSER_EDGE:
                return createEdgeDriver(headless);
            default:
                throw new FrameworkException("Unsupported browser: '" + browser
                        + "'. Supported browsers: chrome, firefox, edge");
        }
    }

    /**
     * Creates and configures a Chrome WebDriver.
     *
     * @param headless whether to run in headless mode
     * @return a Chrome WebDriver instance
     */
    private static WebDriver createChromeDriver(boolean headless) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        LOG.info("Creating Chrome WebDriver");
        return new ChromeDriver(options);
    }

    /**
     * Creates and configures a Firefox WebDriver.
     *
     * @param headless whether to run in headless mode
     * @return a Firefox WebDriver instance
     */
    private static WebDriver createFirefoxDriver(boolean headless) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("--headless");
        }
        options.addArguments("--disable-notifications");
        LOG.info("Creating Firefox WebDriver");
        return new FirefoxDriver(options);
    }

    /**
     * Creates and configures an Edge WebDriver.
     *
     * @param headless whether to run in headless mode
     * @return an Edge WebDriver instance
     */
    private static WebDriver createEdgeDriver(boolean headless) {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--no-sandbox");
        LOG.info("Creating Edge WebDriver");
        return new EdgeDriver(options);
    }

    /**
     * Configures the WebDriver with timeouts and other settings.
     *
     * @param driver       the WebDriver instance to configure
     * @param explicitWait the explicit wait timeout in seconds
     */
    private static void configureDriver(WebDriver driver, int explicitWait) {
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(FrameworkConstants.PAGE_LOAD_TIMEOUT));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(FrameworkConstants.SCRIPT_TIMEOUT));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(explicitWait));
        LOG.info("WebDriver configured: maximize, delete cookies, timeouts set");
    }

    /**
     * Quits the WebDriver for the current thread and removes it from ThreadLocal.
     */
    public static void quitDriver() {
        if (DRIVER.get() != null) {
            LOG.info("Quitting WebDriver for thread: {}", Thread.currentThread().getId());
            DRIVER.get().quit();
            DRIVER.remove();
        }
    }
}
