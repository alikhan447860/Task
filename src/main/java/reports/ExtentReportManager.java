package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manages ExtentTest instances per thread using ThreadLocal.
 * Provides methods to create, retrieve, and remove test instances.
 */
public final class ExtentReportManager {

    private static final Logger LOG = LogManager.getLogger(ExtentReportManager.class);
    private static final ThreadLocal<ExtentTest> EXTENT_TEST = new ThreadLocal<>();

    private ExtentReportManager() {
        throw new IllegalStateException("Utility class cannot be instantiated");
    }

    /**
     * Creates a new test node in the Extent Report for the given test name.
     *
     * @param testName the name of the test to create in the report
     */
    public static void createTest(String testName) {
        LOG.info("Creating Extent Report test: {}", testName);
        ExtentReports extent = ExtentManager.getExtentReports();
        ExtentTest test = extent.createTest(testName);
        EXTENT_TEST.set(test);
    }

    /**
     * Returns the current thread's ExtentTest instance.
     *
     * @return the ExtentTest for the current thread
     */
    public static ExtentTest getTest() {
        return EXTENT_TEST.get();
    }

    /**
     * Assigns a category to the current test in the report.
     *
     * @param category the category name
     */
    public static void assignCategory(String category) {
        ExtentTest test = EXTENT_TEST.get();
        if (test != null) {
            test.assignCategory(category);
        }
    }

    /**
     * Assigns the author to the current test in the report.
     *
     * @param author the author name
     */
    public static void assignAuthor(String author) {
        ExtentTest test = EXTENT_TEST.get();
        if (test != null) {
            test.assignAuthor(author);
        }
    }

    /**
     * Flushes the ExtentReports to generate the final HTML report.
     */
    public static void flushReport() {
        LOG.info("Flushing Extent Report");
        ExtentManager.getExtentReports().flush();
    }

    /**
     * Removes the ExtentTest instance for the current thread.
     */
    public static void removeTest() {
        EXTENT_TEST.remove();
    }
}
