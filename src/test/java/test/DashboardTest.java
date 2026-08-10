
        package test;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.DashboardPage;
import pages.LoginPage;
import utils.TestListener;

@Listeners(TestListener.class)
public class DashboardTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(DashboardTest.class);

    @Test(description = "TC_003 - Verify Platform Pulse KPI Cards")
    public void verifyPlatformPulseCards() {

        logger.info("============================================================");
        logger.info("TEST CASE : TC_003 - Verify Platform Pulse KPI Cards");
        logger.info("============================================================");

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);

        Map<String, String> data = environmentData.get(0);

        logger.info("STEP 1 : Login to MontreCY Admin Portal");

        loginPage.login(
                data.get("Username"),
                data.get("Password")
        );

        logger.info("STEP 2 : Validate Dashboard page");

        Assert.assertTrue(
                dashboardPage.isDashboardDisplayed(),
                "Dashboard is not displayed."
        );

        logger.info("STEP 3 : Validate Platform Pulse KPI Cards");

        Assert.assertTrue(
                dashboardPage.isTotalUsersDisplayed(),
                "Total Users card is missing."
        );

        Assert.assertTrue(
                dashboardPage.isNewSubscriptionsDisplayed(),
                "New Subscriptions card is missing."
        );

        Assert.assertTrue(
                dashboardPage.isRevenueDisplayed(),
                "Revenue card is missing."
        );

        Assert.assertTrue(
                dashboardPage.isChurnRateDisplayed(),
                "Churn Rate card is missing."
        );

        Assert.assertTrue(
                dashboardPage.isDAUDisplayed(),
                "DAU card is missing."
        );

        logger.info("TEST RESULT : PASSED");
        logger.info("============================================================");
    }
}

