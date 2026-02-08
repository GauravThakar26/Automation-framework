package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.WaitUtils;
import driver.DriverManager;

public class LoginPageDemo {

	// Locators
	By usernameField = By.id("txtUserName");
	By passwordField = By.xpath("//div[@id='root']//input[@type='password']");
	By loginBtn = By.xpath("//div[@id='root']//input[@type='submit']");
	By errormessage = By.xpath("//div[@id='root']//div[contains(text(), 'Please enter valid user name or password')]"); // popup
	By profilePhoto = By.xpath("//label[@class='custom-file-upload fas']/div[1]");
	By okforalert = By.xpath("//input[@type='button' and contains(@class, 'btn')]"); // OK button for popup
	By forgetPasswordLink = By.partialLinkText("Forgot");
	By emailField = By.id("EmailId");
	By SignoutBtn = By.xpath("//input[@value='Sign out']");
	By FeedbackClose = By.xpath("//input[@title='Close Request']");

	// No constructor needed now, as driver will be fetched from DriverManager

	private WebDriver getDriver() {
		return DriverManager.getDriver();
	}

	public void enterUsername(String username) {
		getDriver().findElement(usernameField).sendKeys(username);
	}

	public void enterPassword(String password) {
		getDriver().findElement(passwordField).sendKeys(password);
	}

	public void clickLogin() {
		getDriver().findElement(loginBtn).click();
	}

	public void clickOk() {
		getDriver().findElement(okforalert).click();
	}

	public void clickLogout() {
		Actions actions = new Actions(getDriver());
		// Hover over the profile photo
		actions.moveToElement(getDriver().findElement(profilePhoto)).perform();
		WaitUtils.waitForVisibility(getDriver(), SignoutBtn);
		getDriver().findElement(SignoutBtn).click();
		System.out.println("Signed out successfully....");
	}

	public void authenticationOK() {
		getDriver().findElement(okforalert).click();
	}

	public boolean isErrorMessageDisplayed() {
		return getDriver().findElement(errormessage).isDisplayed();
	}

	public void FeedbackClose() {
		if (isFeedbackVisible()) {
			// Close the feedback popup if visible
			getDriver().findElement(FeedbackClose).click();
		}
	}

	private boolean isFeedbackVisible() {
		try {
			// Locator for the feedback modal close button (FeedbackClose button)
			By feedbackModalLocator = By.xpath("//input[@title='Close Request']");

			// Check if the modal exists first (no wait)
			WebElement feedbackModal = getDriver().findElement(feedbackModalLocator);

			// If the modal exists, only then wait for its visibility
			if (feedbackModal.isDisplayed()) {
				WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(0));
				wait.until(ExpectedConditions.visibilityOf(feedbackModal));
				return true; // Modal is visible
			}

			// If modal doesn't exist or isn't visible, return false
			return false;
		} catch (NoSuchElementException | TimeoutException e) {
			// Feedback modal is either not present or not visible
			return false;
		}
	}

	public void clickFogotPasswordLink() {
		getDriver().findElement(forgetPasswordLink).click();
	}

	public void login(String username, String password) {
		enterUsername(username);
		enterPassword(password);
		clickLogin();
	}
}
