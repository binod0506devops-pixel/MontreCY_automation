package test;

import java.util.HashMap;

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
public class LoginTest extends BaseTest {

    private static final Logger logger =
            LoggerFactory.getLogger(LoginTest.class);

    @Test(priority = 1, description = "TC_001 - Verify Admin Login and Dashboard Title")
    public void verifyLogin() {

        logger.info("============================================================");
        logger.info("TEST CASE : TC_001 - Verify Admin Login and Dashboard Title");
        logger.info("============================================================");

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);

        HashMap<String, String> data = testData.get(0);

        logger.info("STEP 1 : Login to MontreCY Admin Portal");

        loginPage.login(
                data.get("Username"),
                data.get("Password"));

        logger.info("STEP 2 : Validate Dashboard page");

        Assert.assertTrue(
                dashboardPage.isDashboardDisplayed(),
                "Dashboard is not displayed.");

        logger.info("STEP 3 : Validate Dashboard title");

        Assert.assertEquals(
                dashboardPage.getDashboardTitle(),
                "Dashboard",
                "Dashboard title is incorrect.");

        logger.info("TEST RESULT : PASSED");
        logger.info("============================================================");
    }

    @Test(priority = 2, description = "TC_002 - Verify Admin Logout")
    public void verifyLogout() {

        logger.info("============================================================");
        logger.info("TEST CASE : TC_002 - Verify Admin Logout");
        logger.info("============================================================");

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);

        HashMap<String, String> data = testData.get(0);

        logger.info("STEP 1 : Login to MontreCY Admin Portal");

        loginPage.login(
                data.get("Username"),
                data.get("Password"));

        logger.info("STEP 2 : Logout from MontreCY Admin Portal");

        dashboardPage.logout();

        logger.info("STEP 3 : Validate Login page");

        Assert.assertTrue(
                loginPage.isLoginPageDisplayed(),
                "Login page is not displayed after logout.");

        logger.info("TEST RESULT : PASSED");
        logger.info("============================================================");
    }
}