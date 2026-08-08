package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {

    private static ExtentReports extent;

    private ExtentManager() {
        // Prevent instantiation
    }

    public static ExtentReports getExtentReports() {

        if (extent == null) {

            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")
                    .format(new Date());

            String reportDir = System.getProperty("user.dir")
                    + File.separator + "Reports";

            new File(reportDir).mkdirs();

            String reportPath = reportDir
                    + File.separator
                    + "MontreCYReport_"
                    + timestamp
                    + ".html";

            ExtentSparkReporter sparkReporter =
                    new ExtentSparkReporter(reportPath);

            // Report Configuration
            sparkReporter.config().setDocumentTitle("MontreCY Automation Report");
            sparkReporter.config().setReportName("MontreCY Admin Portal Automation Report");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            // System Information
            extent.setSystemInfo("Project", "MontreCY Admin Portal");
            extent.setSystemInfo("Application", "MontreCY");
            extent.setSystemInfo("Application URL", "https://console-sandbox.moneytreecy.in/");
            extent.setSystemInfo("Environment", "Sandbox");
            extent.setSystemInfo("Framework", "Selenium WebDriver + TestNG");
            extent.setSystemInfo("Language", "Java 17");
            extent.setSystemInfo("Browser", "Chrome");
            extent.setSystemInfo("Tester", "Binod Kumar");

        }

        return extent;
    }
}