package base;

import config.ConfigReader;
import constants.FrameworkConstants;
import driver.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import listeners.TestListener;
import reports.ExtentReportManager;

/**
 * Base test class that all test classes extend.
 * Handles WebDriver lifecycle, configuration loading, and reporting setup.
 */
@Listeners(TestListener.class)
public abstract class BaseTest {

    protected static final Logger LOG = LogManager.getLogger(BaseTest.class);

    /**
     * Initializes the WebDriver, reads configuration, launches the browser,
     * and navigates to the base URL before each test method.
     */
    @BeforeMethod
    public void setUp() {
        LOG.info("===== Test Setup Started =====");
        try {
            DriverFactory.initDriver();
            WebDriver driver = DriverFactory.getDriver();

            String url = ConfigReader.getInstance().getProperty("base.url");
            driver.get(url);

           
        } catch (Exception e) {
            LOG.error("Failed to set up the test", e);
            throw e;
        }
    }

    /**
     * Captures a screenshot on failure, quits the WebDriver, and flushes the
     * Extent Report after each test method.
     */
    @AfterMethod
    public void tearDown() {
        LOG.info("===== Test Teardown Started =====");
        try {
            DriverFactory.quitDriver();
            LOG.info("WebDriver quit successfully");
        } catch (Exception e) {
            LOG.error("Error while quitting WebDriver", e);
        }
        ExtentReportManager.flushReport();
    }
}
