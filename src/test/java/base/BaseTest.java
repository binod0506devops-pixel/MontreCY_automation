package base;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import driver.DriverFactory;
import pages.ContentPage;
import pages.DashboardPage;
import pages.LoginPage;
import utils.CsvReader;
import utils.DriverManager;

public class BaseTest {

    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    private static final Duration IMPLICIT_WAIT = Duration.ofSeconds(0);
    private static final Duration PAGE_LOAD_TIMEOUT = Duration.ofSeconds(30);
    private static final Duration EXPLICIT_WAIT = Duration.ofSeconds(20);

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected List<Map<String, String>> environmentData;

    protected LoginPage loginPage;
    protected ContentPage contentPage;
    protected DashboardPage dashboardPage;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        environmentData = CsvReader.read("testdata/environment.csv");

        if (environmentData == null || environmentData.isEmpty()) {
            throw new RuntimeException("No environment data found in CSV file");
        }

        Map<String, String> data = environmentData.get(0);
        String browser = getBrowser(data);
        String url = data.get("URL");
        boolean headless = getHeadlessValue();

        if (url == null || url.trim().isEmpty()) {
            throw new RuntimeException("URL is missing in environment.csv");
        }

        driver = DriverFactory.createDriver(browser, headless);
        DriverManager.setDriver(driver);
        driver = DriverManager.getDriver();

        loginPage = new LoginPage(driver);
        contentPage = new ContentPage(driver);
        dashboardPage = new DashboardPage(driver);

        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT);
        driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT);
        wait = new WebDriverWait(driver, EXPLICIT_WAIT);

        if (!headless) {
            maximizeBrowser();
        }

        driver.get(url);

        logger.info("Test setup completed.");
    }

    protected void loginAndNavigate() {
        loginPage.login(
                environmentData.get(0).get("Username"),
                environmentData.get(0).get("Password")
        );

        contentPage.waitForDashboard();
    }

    private String getBrowser(Map<String, String> data) {
        String systemBrowser = System.getProperty("browser");

        if (systemBrowser != null && !systemBrowser.trim().isEmpty()) {
            return systemBrowser.trim();
        }

        String csvBrowser = data.get("Browser");

        if (csvBrowser == null || csvBrowser.trim().isEmpty()) {
            throw new RuntimeException("Browser is missing in environment.csv");
        }

        return csvBrowser.trim();
    }

    private boolean getHeadlessValue() {
        String headlessProperty = System.getProperty("headless");

        if (headlessProperty == null || headlessProperty.trim().isEmpty()) {
            return false;
        }

        return Boolean.parseBoolean(headlessProperty);
    }

    private void maximizeBrowser() {
        try {
            driver.manage().window().maximize();
        } catch (Exception e) {
            logger.warn("Unable to maximize browser. Setting window size instead.");

            try {
                driver.manage().window().setSize(new Dimension(1920, 1080));
            } catch (Exception windowException) {
                logger.warn("Unable to set browser window size.", windowException);
            }
        }
    }

    public WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    public List<Map<String, String>> getEnvironmentData() {
        return environmentData;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        try {
            DriverManager.quitDriver();
        } catch (Exception e) {
            logger.error("Error while closing browser.", e);
        } finally {
            driver = null;
            wait = null;
            DriverManager.removeDriver();
        }
    }
}