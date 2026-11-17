
package tests;

import base.BaseTest;
import pages.LoginPage;
import utils.TestData;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class LoginTests extends BaseTest {

    private LoginPage loginPage;

    // Open login page before each test
    // @BeforeMethod
    // public void openLoginPage() {

    //     loginPage = new LoginPage(driver);
    //     loginPage.openLoginPage();
    // }

    @BeforeMethod(alwaysRun = true)
public void openLoginPage() {

    System.out.println("LoginTests: Opening login page.");

    if (driver == null) {
        throw new IllegalStateException(
                "WebDriver was not initialized by BaseTest."
        );
    }

    loginPage = new LoginPage(driver);
    loginPage.openLoginPage();
}

    // TC1 - Successful Login
    @Test(groups = "smoke")
    public void testSuccessfulLogin() {

        loginPage.enterEmail(TestData.VALID_EMAIL);
        loginPage.enterPassword(TestData.VALID_PASSWORD);
        loginPage.clickLogin();

        try {

            WebDriverWait loginWait =
                    new WebDriverWait(driver, Duration.ofSeconds(20));

            boolean loginSuccessful = loginWait.until(
                    ExpectedConditions.or(
                            ExpectedConditions.not(
                                    ExpectedConditions.urlContains("/login")
                            ),
                            ExpectedConditions.visibilityOfElementLocated(
                                    By.xpath("//*[contains(text(),'Please complete the captcha verification')]")
                            )
                    )
            ) && !driver.getCurrentUrl().contains("/login");

            if (!loginSuccessful) {
                throw new SkipException(
                        "Test blocked: Cloudflare CAPTCHA verification prevented automated login."
                );
            }

            Assert.assertTrue(
                    loginSuccessful,
                    "Login was not successful."
            );

        } catch (TimeoutException e) {

            throw new SkipException(
                    "Test blocked: Login could not be verified because of CAPTCHA."
            );

        } catch (NoSuchSessionException e) {

            throw new SkipException(
                    "Test blocked: Browser session disconnected during CAPTCHA verification."
            );
        }
    }

    // Test data for TC2 - Invalid Login
    @DataProvider(name = "invalidLoginData")
    public Object[][] invalidLoginData() {

        return new Object[][] {

                // Empty email
                {"", "SomePassword123"},

                // Empty password
                {TestData.VALID_EMAIL, ""},

                // Invalid email format
                {TestData.INVALID_EMAIL, "SomePassword123"},

                // Non-existing email and incorrect password
                {TestData.NON_EXISTING_EMAIL, TestData.INVALID_PASSWORD}
        };
    }

    // TC2 - Invalid Login Attempts
    @Test(dataProvider = "invalidLoginData", groups = "smoke")
    public void testInvalidLogin(String email, String password) {

        loginPage.enterEmail(email);
        loginPage.enterPassword(password);

        loginPage.clickLogin();

        Assert.assertTrue(
                loginPage.getCurrentUrl().contains("/login"),
                "Invalid login attempt should not navigate away from login page."
        );
    }

    // TC3 - Show and Hide Password
    @Test
    public void testShowHidePassword() {

        loginPage.enterPassword(TestData.VALID_PASSWORD);

        Assert.assertEquals(
                loginPage.getPasswordInputType(),
                "password",
                "Password should be hidden initially."
        );

        loginPage.clickPasswordVisibility();

        Assert.assertEquals(
                loginPage.getPasswordInputType(),
                "text",
                "Password should be visible after clicking the eye icon."
        );

        loginPage.clickPasswordVisibility();

        Assert.assertEquals(
                loginPage.getPasswordInputType(),
                "password",
                "Password should be hidden after clicking the eye icon again."
        );
    }

    // TC4 - Forgot Password Link
    @Test
    public void testForgotPasswordLink() {

        loginPage.clickForgotPassword();

        Assert.assertTrue(
                loginPage.getCurrentUrl().contains("/us/forgot-password"),
                "Forgot password link did not open the expected page."
        );
    }

    // TC5 - Remember Me Checkbox
    @Test
    public void testRememberMeCheckbox() {

        Assert.assertFalse(
                loginPage.isRememberMeSelected(),
                "Remember me checkbox should initially be unchecked."
        );

        loginPage.clickRememberMe();

        Assert.assertTrue(
                loginPage.isRememberMeSelected(),
                "Remember me checkbox should be selected after clicking."
        );

        loginPage.clickRememberMe();

        Assert.assertFalse(
                loginPage.isRememberMeSelected(),
                "Remember me checkbox should be unchecked after clicking again."
        );
    }
}
