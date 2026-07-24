package utilities;

import constants.FrameworkConstants;
import driver.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for capturing and saving screenshots.
 */
public final class ScreenshotUtil {

    private static final Logger LOG = LogManager.getLogger(ScreenshotUtil.class);

    private ScreenshotUtil() {
        throw new IllegalStateException("Utility class cannot be instantiated");
    }

    /**
     * Captures a screenshot and returns the file path.
     *
     * @param screenshotName the name of the screenshot file (without extension)
     * @return the absolute path of the saved screenshot
     */
    public static String captureScreenshot(String screenshotName) {
        String timestamp = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern(FrameworkConstants.SCREENSHOT_TIMESTAMP_FORMAT));
        String fileName = screenshotName + "_" + timestamp + ".png";
        String filePath = FrameworkConstants.SCREENSHOT_DIR + fileName;

        File screenshotDir = new File(FrameworkConstants.SCREENSHOT_DIR);
        if (!screenshotDir.exists()) {
            screenshotDir.mkdirs();
        }

        try {
            TakesScreenshot ts = (TakesScreenshot) DriverFactory.getDriver();
            File sourceFile = ts.getScreenshotAs(OutputType.FILE);
            org.apache.commons.io.FileUtils.copyFile(sourceFile, new File(filePath));
            LOG.info("Screenshot captured successfully: {}", filePath);
        } catch (IOException e) {
            LOG.error("Failed to capture screenshot: {}", screenshotName, e);
        }

        return filePath;
    }

    /**
     * Captures a screenshot as a byte array (for report embedding).
     *
     * @return the screenshot as a byte array
     */
    public static byte[] captureScreenshotAsBytes() {
        TakesScreenshot ts = (TakesScreenshot) DriverFactory.getDriver();
        return ts.getScreenshotAs(OutputType.BYTES);
    }

    /**
     * Captures a screenshot as a Base64 encoded string (for report embedding).
     *
     * @return the screenshot as a Base64 string
     */
    public static String captureScreenshotAsBase64() {
        TakesScreenshot ts = (TakesScreenshot) DriverFactory.getDriver();
        return ts.getScreenshotAs(OutputType.BASE64);
    }
}
