package test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;

public class LoginTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(LoginTest.class);

    @Test(priority = 1, description = "TC_001 - Verify Admin Login and Dashboard Title")
    public void verifyLogin() {
        String username = environmentData.get(0).get("Username");
        String password = environmentData.get(0).get("Password");

        validateCredentials(username, password);
        loginPage.login(username, password);

        Assert.assertTrue(
                dashboardPage.isDashboardDisplayed(),
                "Dashboard is not displayed after login."
        );

        Assert.assertEquals(
                dashboardPage.getDashboardTitle(),
                "Dashboard",
                "Dashboard title is incorrect."
        );

        logger.info("TC_001 PASSED");
    }

    @Test(priority = 2, description = "TC_002 - Verify Admin Logout")
    public void verifyLogout() {
        loginAndNavigate();

        dashboardPage.logout();

        Assert.assertTrue(
                loginPage.isLoginPageDisplayed(),
                "Login page is not displayed after logout."
        );

        logger.info("TC_002 PASSED");
    }

    private void validateCredentials(String username, String password) {
        Assert.assertNotNull(username, "Username is missing in environment.csv.");
        Assert.assertNotNull(password, "Password is missing in environment.csv.");
        Assert.assertFalse(username.trim().isEmpty(), "Username is empty in environment.csv.");
        Assert.assertFalse(password.trim().isEmpty(), "Password is empty in environment.csv.");
    }
}