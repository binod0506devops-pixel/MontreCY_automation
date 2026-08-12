package utils;

import com.aventstack.extentreports.ExtentTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener, ISuiteListener {

    private static final Logger logger =
            LoggerFactory.getLogger(TestListener.class);

    private long suiteStartTime;

    // =========================================================
    // SUITE START
    // =========================================================

    @Override
    public void onStart(ISuite suite) {

        suiteStartTime = System.currentTimeMillis();

        // Initialize Extent Report
        ExtentManager.getExtentReports();

        logger.info("");
        logger.info("============================================================");
        logger.info("TEST EXECUTION STARTED");
        logger.info("Suite : {}", suite.getName());
        logger.info("============================================================");
    }

    // =========================================================
    // TEST START
    // =========================================================

    @Override
    public void onTestStart(ITestResult result) {

        String testName = result.getMethod().getDescription();

        if (testName == null || testName.trim().isEmpty()) {
            testName = result.getMethod().getMethodName();
        }

        ExtentTest test = ExtentManager
                .getExtentReports()
                .createTest(testName);

        ExtentTestManager.setTest(test);

        logger.info("");
        logger.info("TEST STARTED : {}", testName);
    }

    // =========================================================
    // TEST PASSED
    // =========================================================

    @Override
    public void onTestSuccess(ITestResult result) {

        String testName = result.getMethod().getMethodName();

        if (ExtentTestManager.getTest() != null) {
            ExtentTestManager
                    .getTest()
                    .pass("Test Passed");
        }

        logger.info("TEST PASSED : {}", testName);
    }

    // =========================================================
    // TEST FAILED
    // =========================================================

    @Override
    public void onTestFailure(ITestResult result) {

        String testName = result.getMethod().getMethodName();

        if (ExtentTestManager.getTest() != null) {
            ExtentTestManager
                    .getTest()
                    .fail(result.getThrowable());
        }

        logger.error(
                "TEST FAILED : {}",
                testName,
                result.getThrowable()
        );
    }

    // =========================================================
    // TEST SKIPPED
    // =========================================================

    @Override
    public void onTestSkipped(ITestResult result) {

        String testName = result.getMethod().getMethodName();

        if (ExtentTestManager.getTest() != null) {
            ExtentTestManager
                    .getTest()
                    .skip("Test Skipped");
        }

        logger.warn("TEST SKIPPED : {}", testName);
    }

    // =========================================================
    // SUITE FINISH
    // =========================================================

    @Override
    public void onFinish(ISuite suite) {

        long duration =
                (System.currentTimeMillis() - suiteStartTime) / 1000;

        int passed = 0;
        int failed = 0;
        int skipped = 0;

        /*
         * suite.getResults() contains results from ALL
         * <test> sections inside the TestNG suite.
         */
        for (var suiteResult : suite.getResults().values()) {

            var context = suiteResult.getTestContext();

            passed += context.getPassedTests().size();
            failed += context.getFailedTests().size();
            skipped += context.getSkippedTests().size();
        }

        int total =
                passed + failed + skipped;

        double passRate =
                total > 0
                        ? (passed * 100.0) / total
                        : 0.0;

        // Flush Extent Report ONCE after complete suite
        ExtentManager
                .getExtentReports()
                .flush();

        ExtentTestManager.unload();

        // =====================================================
        // FINAL SUITE SUMMARY
        // =====================================================

        logger.info("");
        logger.info("============================================================");
        logger.info("TEST EXECUTION SUMMARY");
        logger.info("------------------------------------------------------------");
        logger.info("Suite       : {}", suite.getName());
        logger.info("Total Tests : {}", total);
        logger.info("Passed      : {}", passed);
        logger.info("Failed      : {}", failed);
        logger.info("Skipped     : {}", skipped);
        logger.info(
                "Pass Rate   : {}%",
                String.format("%.2f", passRate)
        );
        logger.info("Duration    : {} sec", duration);
        logger.info("============================================================");
    }
}