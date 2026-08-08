package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DashboardPage {


    private static final Logger logger =
            LoggerFactory.getLogger(DashboardPage.class);

    private WebDriver driver;
    private WebDriverWait wait;

    // Constructor
    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ===========================
    // Locators
    // ===========================

    private By dashboardTitle = By.xpath("//h1[@class='dashboard-title']");
    private By userAvatar = By.xpath("//div[@class='user-avatar']");
    private By logoutButton = By.xpath("//button[@class='dropdown-item dropdown-button']");

    // Platform Pulse KPI Cards
    private By totalUsers = By.xpath("//span[text()='Total Users']");
    private By newSubscriptions = By.xpath("//span[text()='New Subscriptions']");
    private By revenue = By.xpath("//span[text()='Revenue']");
    private By churnRate = By.xpath("//span[text()='Churn Rate']");
    private By dau = By.xpath("//span[text()='DAU']");

    // ===========================
    // Dashboard Verification
    // ===========================

    public boolean isDashboardDisplayed() {

        logger.info("Verifying Dashboard page.");

        return wait.until(
                        ExpectedConditions.visibilityOfElementLocated(dashboardTitle))
                .isDisplayed();
    }

    public String getDashboardTitle() {

        logger.info("Getting Dashboard title.");

        return wait.until(
                        ExpectedConditions.visibilityOfElementLocated(dashboardTitle))
                .getText();
    }

    // ===========================
    // Logout
    // ===========================

    public void logout() {

        logger.info("Starting Logout process.");

        wait.until(ExpectedConditions.elementToBeClickable(userAvatar)).click();
        logger.info("Clicked User Avatar.");

        wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();

        logger.info("Logout completed successfully.");
    }

    // ===========================
    // Platform Pulse KPI Cards
    // ===========================

    public boolean isTotalUsersDisplayed() {

        logger.info("Verifying Total Users card.");

        return wait.until(
                        ExpectedConditions.visibilityOfElementLocated(totalUsers))
                .isDisplayed();
    }

    public boolean isNewSubscriptionsDisplayed() {

        logger.info("Verifying New Subscriptions card.");

        return wait.until(
                        ExpectedConditions.visibilityOfElementLocated(newSubscriptions))
                .isDisplayed();
    }

    public boolean isRevenueDisplayed() {

        logger.info("Verifying Revenue card.");

        return wait.until(
                        ExpectedConditions.visibilityOfElementLocated(revenue))
                .isDisplayed();
    }

    public boolean isChurnRateDisplayed() {

        logger.info("Verifying Churn Rate card.");

        return wait.until(
                        ExpectedConditions.visibilityOfElementLocated(churnRate))
                .isDisplayed();
    }

    public boolean isDAUDisplayed() {

        logger.info("Verifying DAU card.");

        return wait.until(
                        ExpectedConditions.visibilityOfElementLocated(dau))
                .isDisplayed();
    }
}