package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage {

    private static final Logger logger =
            LoggerFactory.getLogger(LoginPage.class);

    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ===========================
    // Locators
    // ===========================

    private By txtEmail = By.xpath("//input[@placeholder='Email']");
    private By txtPassword = By.xpath("//input[@placeholder='Password']");
    private By btnLogin = By.xpath("//button[@type='submit']");

    // ===========================
    // Actions
    // ===========================

    public void enterEmail(String username) {

        logger.info("Entering Email.");

        wait.until(ExpectedConditions.visibilityOfElementLocated(txtEmail)).clear();
        driver.findElement(txtEmail).sendKeys(username);
    }

    public void enterPassword(String password) {

        logger.info("Entering Password.");

        wait.until(ExpectedConditions.visibilityOfElementLocated(txtPassword)).clear();
        driver.findElement(txtPassword).sendKeys(password);
    }

    public void clickLogin() {

        logger.info("Clicking Login button.");

        wait.until(ExpectedConditions.elementToBeClickable(btnLogin)).click();
    }

    public void login(String username, String password) {

        logger.info("Starting Login Process.");

        enterEmail(username);
        enterPassword(password);
        clickLogin();

        logger.info("Login completed successfully.");
    }

    // ===========================
    // Verification
    // ===========================

    public boolean isLoginPageDisplayed() {

        logger.info("Verifying Login page.");

        return wait.until(
                        ExpectedConditions.visibilityOfElementLocated(btnLogin))
                .isDisplayed();
    }
}