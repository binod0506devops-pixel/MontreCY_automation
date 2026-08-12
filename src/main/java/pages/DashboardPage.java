package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DashboardPage {

    private static final Logger logger = LoggerFactory.getLogger(DashboardPage.class);

    private final WebDriver driver;
    private final CommonActions commonActions;

    private final By dashboardTitle = By.xpath("//h1[@class='dashboard-title']");
    private final By userAvatar = By.xpath("//div[@class='user-avatar']");
    private final By logoutButton = By.xpath("//button[@class='dropdown-item dropdown-button']");

    private final By totalUsers = By.xpath("//span[text()='Total Users']");
    private final By newSubscriptions = By.xpath("//span[text()='New Subscriptions']");
    private final By revenue = By.xpath("//span[text()='Revenue']");
    private final By churnRate = By.xpath("//span[text()='Churn Rate']");
    private final By dau = By.xpath("//span[text()='DAU']");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.commonActions = new CommonActions(driver);
    }

    public boolean isDashboardDisplayed() {
        logger.info("Verifying Dashboard page.");
        return commonActions.isDisplayed(dashboardTitle);
    }

    public String getDashboardTitle() {
        logger.info("Getting Dashboard title.");
        return commonActions.getText(dashboardTitle);
    }

    public void logout() {
        logger.info("Starting Logout process.");
        commonActions.click(userAvatar);
        commonActions.click(logoutButton);
        logger.info("Logout completed successfully.");
    }

    public boolean isTotalUsersDisplayed() {
        logger.info("Verifying Total Users card.");
        return commonActions.isDisplayed(totalUsers);
    }

    public boolean isNewSubscriptionsDisplayed() {
        logger.info("Verifying New Subscriptions card.");
        return commonActions.isDisplayed(newSubscriptions);
    }

    public boolean isRevenueDisplayed() {
        logger.info("Verifying Revenue card.");
        return commonActions.isDisplayed(revenue);
    }

    public boolean isChurnRateDisplayed() {
        logger.info("Verifying Churn Rate card.");
        return commonActions.isDisplayed(churnRate);
    }

    public boolean isDAUDisplayed() {
        logger.info("Verifying DAU card.");
        return commonActions.isDisplayed(dau);
    }
}
