package constants;

import java.io.File;

/**
 * Constants used across the automation framework.
 * Centralizes all static values to avoid magic strings.
 */
public final class FrameworkConstants {

    private FrameworkConstants() {
        throw new IllegalStateException("Utility class cannot be instantiated");
    }

    /** Path to the configuration properties file */
    public static final String CONFIG_FILE_PATH =
            System.getProperty("user.dir") + File.separator + "src" + File.separator
                    + "test" + File.separator + "resources" + File.separator + "config.properties";

    /** Directory to store screenshots */
    public static final String SCREENSHOT_DIR =
            System.getProperty("user.dir") + File.separator + "screenshots" + File.separator;

    /** Directory to store Extent Reports */
    public static final String REPORT_DIR =
            System.getProperty("user.dir") + File.separator + "reports" + File.separator;

    /** Directory to store logs */
    public static final String LOG_DIR =
            System.getProperty("user.dir") + File.separator + "logs" + File.separator;

    /** Default explicit wait timeout in seconds */
    public static final int DEFAULT_EXPLICIT_WAIT = 20;

    /** Page load timeout in seconds */
    public static final int PAGE_LOAD_TIMEOUT = 30;

    /** Script timeout in seconds */
    public static final int SCRIPT_TIMEOUT = 15;

    /** Screenshot timestamp format */
    public static final String SCREENSHOT_TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";

    /** Report timestamp format */
    public static final String REPORT_TIMESTAMP_FORMAT = "yyyy_MM_dd_HHmmss";

    /** Supported browser names */
    public static final String BROWSER_CHROME = "chrome";
    public static final String BROWSER_FIREFOX = "firefox";
    public static final String BROWSER_EDGE = "edge";
}
