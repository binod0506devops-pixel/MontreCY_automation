package driver;

import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public final class DriverFactory {

    private static final Logger logger =
            LogManager.getLogger(DriverFactory.class);

    private DriverFactory() {
        // Utility class
    }

    public static WebDriver createDriver(String browser, boolean headless) {

        if (browser == null || browser.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Browser cannot be null or empty."
            );
        }

        String browserName = browser.trim().toLowerCase(Locale.ROOT);

        logger.info(
                "Creating driver. Browser: {}, Headless: {}, Thread: {}",
                browserName,
                headless,
                Thread.currentThread().getId()
        );

        switch (browserName) {

            case "chrome":
                return createChromeDriver(headless);

            case "firefox":
                return createFirefoxDriver(headless);

            case "edge":
                return createEdgeDriver(headless);

            default:
                throw new IllegalArgumentException(
                        "Unsupported browser: " + browser
                );
        }
    }

    private static WebDriver createChromeDriver(boolean headless) {

        ChromeOptions options = new ChromeOptions();

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
        }

        logger.info("Launching Chrome browser.");

        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver(boolean headless) {

        FirefoxOptions options = new FirefoxOptions();

        if (headless) {
            options.addArguments("-headless");
            options.addArguments("--width=1920");
            options.addArguments("--height=1080");
        }

        logger.info("Launching Firefox browser.");

        return new FirefoxDriver(options);
    }

    private static WebDriver createEdgeDriver(boolean headless) {

        EdgeOptions options = new EdgeOptions();

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
        }

        logger.info("Launching Edge browser.");

        return new EdgeDriver(options);
    }
}