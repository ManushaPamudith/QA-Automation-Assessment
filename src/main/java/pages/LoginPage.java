
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Login page URL
    private final String LOGIN_URL =
            "https://staging.globaledulink.co.uk/us/login";

    // Locators
    private final By emailField =
            By.id("email");

    private final By passwordField =
            By.id("password");

    private final By loginButton =
            By.cssSelector("button[type='submit']");

    // Supports both Show and Hide password states
    private final By showPasswordButton =
            By.cssSelector(
                "button[aria-label='Show password'], " +
                "button[aria-label='Hide password']"
            );

    private final By forgotPasswordLink =
            By.cssSelector("a[href='/us/forgot-password']");

    private final By rememberMeCheckbox =
            By.xpath("//label[contains(., 'Remember me')]//input[@type='checkbox']");

    // Error message locators
    private final By emailRequiredError =
            By.xpath("//p[normalize-space()='Email is required.']");

    private final By invalidEmailError =
            By.xpath("//p[normalize-space()='The email field must be a valid email address.']");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(15)
        );
    }

    // Open login page
    public void openLoginPage() {
        driver.get(LOGIN_URL);

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(emailField)
        );
    }

    // Enter email
    public void enterEmail(String email) {

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(emailField)
        );

        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
    }

    // Enter password
    public void enterPassword(String password) {

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(passwordField)
        );

        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    // Click login button
    public void clickLogin() {

        wait.until(
            ExpectedConditions.elementToBeClickable(loginButton)
        ).click();
    }

    // Show / Hide password
    public void clickPasswordVisibility() {

        wait.until(
            ExpectedConditions.elementToBeClickable(showPasswordButton)
        ).click();
    }

    // Get password input type
    public String getPasswordInputType() {

        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(passwordField)
        ).getAttribute("type");
    }

    // Click forgot password
    public void clickForgotPassword() {

        wait.until(
            ExpectedConditions.elementToBeClickable(forgotPasswordLink)
        ).click();

        wait.until(
            ExpectedConditions.urlContains("/us/forgot-password")
        );
    }

    // Click remember me
    public void clickRememberMe() {

        wait.until(
            ExpectedConditions.elementToBeClickable(rememberMeCheckbox)
        ).click();
    }

    // Check remember me status
    public boolean isRememberMeSelected() {

        return wait.until(
            ExpectedConditions.presenceOfElementLocated(
                rememberMeCheckbox
            )
        ).isSelected();
    }

    // Get email required error
    public String getEmailRequiredError() {

        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                emailRequiredError
            )
        ).getText();
    }

    // Get invalid email error
    public String getInvalidEmailError() {

        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                invalidEmailError
            )
        ).getText();
    }

    // Get current URL
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // Wait until login is successful
    public boolean waitForSuccessfulLogin() {

        return wait.until(
            ExpectedConditions.not(
                ExpectedConditions.urlContains("/login")
            )
        );
    }
}
