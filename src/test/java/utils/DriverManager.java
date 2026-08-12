
package utils;
import org.openqa.selenium.WebDriver;

public final class DriverManager {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() {
        // Utility class
    }

    public static void setDriver(WebDriver driver) {
        DRIVER.set(driver);
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();

        if (driver == null) {
            throw new IllegalStateException(
                    "WebDriver is not initialized for the current thread."
            );
        }

        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();

        if (driver != null) {
            try {
                driver.quit();
            } finally {
                DRIVER.remove();
            }
        }
    }

    public static void removeDriver() {
        DRIVER.remove();
    }
}