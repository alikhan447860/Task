package listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reports.ExtentReportManager;
import utilities.ScreenshotUtil;

/**
 * TestNG listener that integrates with Extent Reports.
 * Logs test lifecycle events and captures screenshots on failure.
 */
public class TestListener implements ITestListener {

    private static final Logger LOG = LogManager.getLogger(TestListener.class);

    /**
     * Called when a test method starts.
     * Creates a new test entry in the Extent Report.
     *
     * @param result the ITestResult for the starting test
     */
    @Override
    public void onTestStart(ITestResult result) {
        LOG.info("========== TEST STARTED: {} ==========", result.getMethod().getMethodName());
        ExtentReportManager.createTest(result.getMethod().getMethodName());
        ExtentReportManager.assignCategory(result.getTestContext().getCurrentXmlTest().getName());
    }

    /**
     * Called when a test method succeeds.
     * Logs the success status in the Extent Report.
     *
     * @param result the ITestResult for the passed test
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        LOG.info("========== TEST PASSED: {} ==========", result.getMethod().getMethodName());
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            test.log(Status.PASS, "Test Passed: " + result.getMethod().getMethodName());
        }
    }

    /**
     * Called when a test method fails.
     * Logs the failure with the exception message and attaches a screenshot.
     *
     * @param result the ITestResult for the failed test
     */
    @Override
    public void onTestFailure(ITestResult result) {
        LOG.error("========== TEST FAILED: {} ==========", result.getMethod().getMethodName());
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            test.log(Status.FAIL, "Test Failed: " + result.getMethod().getMethodName());
            test.log(Status.FAIL, "Failure Reason: " + result.getThrowable().getMessage());

            try {
                String screenshotPath = ScreenshotUtil.captureScreenshot(result.getMethod().getMethodName());
                test.addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");
                LOG.info("Failure screenshot attached to report: {}", screenshotPath);
            } catch (Exception e) {
                LOG.error("Failed to capture failure screenshot", e);
                test.log(Status.WARNING, "Could not capture failure screenshot");
            }
        }
    }

    /**
     * Called when a test method is skipped.
     * Logs the skip status in the Extent Report.
     *
     * @param result the ITestResult for the skipped test
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        LOG.warn("========== TEST SKIPPED: {} ==========", result.getMethod().getMethodName());
        ExtentTest test = ExtentReportManager.getTest();
        if (test != null) {
            test.log(Status.SKIP, "Test Skipped: " + result.getMethod().getMethodName());
            if (result.getThrowable() != null) {
                test.log(Status.SKIP, "Skip Reason: " + result.getThrowable().getMessage());
            }
        }
    }

    /**
     * Called when a test method fails but within its success percentage.
     *
     * @param result the ITestResult
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        LOG.warn("Test failed but within success percentage: {}", result.getMethod().getMethodName());
    }

    /**
     * Called before any test suite starts.
     *
     * @param context the ITestContext
     */
    @Override
    public void onStart(ITestContext context) {
        LOG.info("========== SUITE STARTED: {} ==========", context.getName());
    }

    /**
     * Called after all test suite methods have completed.
     * Flushes the Extent Report.
     *
     * @param context the ITestContext
     */
    @Override
    public void onFinish(ITestContext context) {
        LOG.info("========== SUITE FINISHED: {} ==========", context.getName());
        ExtentReportManager.flushReport();
    }
}
