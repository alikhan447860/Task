package reports;

import config.ConfigReader;
import constants.FrameworkConstants;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * Manages the singleton instance of ExtentReports.
 * Responsible for initializing and configuring the reporting engine.
 */
public final class ExtentManager {

    private static final Logger LOG = LogManager.getLogger(ExtentManager.class);
    private static ExtentReports extentReports;

    private ExtentManager() {
        throw new IllegalStateException("Utility class cannot be instantiated");
    }

    /**
     * Returns the singleton ExtentReports instance, creating it if necessary.
     * Configures the Spark HTML reporter with custom settings.
     *
     * @return the singleton ExtentReports instance
     */
    public static synchronized ExtentReports getExtentReports() {
        if (extentReports == null) {
            createReport();
        }
        return extentReports;
    }

    /**
     * Creates and configures the ExtentReports with a Spark HTML reporter.
     */
    private static void createReport() {
        String reportPath = FrameworkConstants.REPORT_DIR + "extent-report.html";

        File reportDir = new File(FrameworkConstants.REPORT_DIR);
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        configureSparkReporter(sparkReporter);

        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);

        setSystemInfo();

        LOG.info("Extent Reports initialized successfully at: {}", reportPath);
    }

    /**
     * Configures the Spark reporter theme, title, and encoding.
     *
     * @param sparkReporter the ExtentSparkReporter to configure
     */
    private static void configureSparkReporter(ExtentSparkReporter sparkReporter) {
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("AutomationFramework Test Execution Report");
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setEncoding("utf-8");
        sparkReporter.config().thumbnailForBase64(true);
    }

    /**
     * Sets system and environment information displayed in the report.
     */
    private static void setSystemInfo() {
        extentReports.setSystemInfo("Project", "AutomationFramework");
        extentReports.setSystemInfo("Author", "SDET Team");
        extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
        extentReports.setSystemInfo("OS", System.getProperty("os.name"));
        extentReports.setSystemInfo("Browser", ConfigReader.getInstance().getProperty("browser"));
        extentReports.setSystemInfo("Base URL", ConfigReader.getInstance().getProperty("baseUrl"));
    }
}
