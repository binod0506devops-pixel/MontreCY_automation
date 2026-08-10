
        package base;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.CsvReader;

public class BaseTest {

    private static final Logger logger = LogManager.getLogger(BaseTest.class);

    public static WebDriver driver;
    protected WebDriverWait wait;

    // Environment data
    protected List<Map<String, String>> environmentData;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {

        // Load environment data from CSV
        environmentData = CsvReader.read(
                "testdata/environment.csv"
        );

        if (environmentData == null || environmentData.isEmpty()) {
            throw new RuntimeException("No environment data found in CSV file");
        }

        Map<String, String> data = environmentData.get(0);

        String browser = data.get("Browser");
        String url = data.get("URL");

        logger.info("====================================");
        logger.info("Starting Test Execution");
        logger.info("Browser : {}", browser);
        logger.info("URL     : {}", url);
        logger.info("====================================");

        // Initialize browser
        driver = createDriver(browser);

        // Browser timeout configuration
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        // Explicit wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Maximize only for local execution
        boolean isGitHub = System.getenv("GITHUB_ACTIONS") != null;

        if (!isGitHub) {
            try {
                driver.manage().window().maximize();
            } catch (Exception e) {
                logger.warn("Unable to maximize browser. Setting window size instead.");
                driver.manage().window().setSize(new Dimension(1920, 1080));
            }
        }

        // Open application
        driver.get(url);

        logger.info("Application Opened : {}", driver.getCurrentUrl());
    }

    private WebDriver createDriver(String browser) {

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        boolean isGitHub = System.getenv("GITHUB_ACTIONS") != null;

        if (isGitHub) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
        }

        if (browser.equalsIgnoreCase("Chrome")) {
            logger.info("Launching Chrome Browser.");
            return new ChromeDriver(options);
        }

        throw new RuntimeException("Unsupported browser : " + browser);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public List<Map<String, String>> getEnvironmentData() {
        return environmentData;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        if (driver != null) {
            driver.quit();
            logger.info("Browser closed successfully.");
        }
    }
}

