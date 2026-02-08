package tests;

import base.BaseTest;
import pages.LoginPageDemo;
import utils.ExcelDataProvider;
import utils.WaitUtils;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTestDemo extends BaseTest {
	@Test(description = "Verify that a user can successfully log in and log out using valid credentials.", groups = "smoke", dataProvider = "loginData", dataProviderClass = ExcelDataProvider.class)
	public void testValidLoginLogout(String username, String password) {

		LoginPageDemo loginPage = new LoginPageDemo();
		loginPage.login(username, password);
		loginPage.FeedbackClose();
		loginPage.clickLogout();
		System.out.println("Running test with user: " + username);
		Assert.assertTrue(getDriver().getPageSource().contains("Welcome to"), "Login attempt failed....");
	}

	@Test(groups = "regression", dataProvider = "loginData", dataProviderClass = ExcelDataProvider.class)
	public void testBlankUsername(String username, String password) throws InterruptedException {
		LoginPageDemo loginPage = new LoginPageDemo();
		loginPage.enterUsername(""); // Blank username for this test
		loginPage.enterPassword(password); // Password from data provider
		loginPage.clickLogin();

		// Wait for alert message to appear
		WaitUtils.waitForVisibility(getDriver(), By.xpath("//div[@id='root']//p[contains(text(), 'Alert')]"));
		loginPage.clickOk();

		// Assert that alert message is shown indicating login failure, NOT the welcome
		// page
		Assert.assertTrue(getDriver().getPageSource().contains("Welcome to"),
				"Alert message not displayed for blank username");
	}

	@Test(groups = "regression", dataProvider = "loginData", dataProviderClass = ExcelDataProvider.class)
	public void testBlankPassword(String username, String password) {
		LoginPageDemo loginPage = new LoginPageDemo();
		loginPage.enterUsername(username); // username from data provider
		loginPage.enterPassword(""); // blank password for this test
		loginPage.clickLogin();

		// Wait for alert message to appear
		utils.WaitUtils.waitForVisibility(getDriver(), By.xpath("//div[@id='root']//p[contains(text(), 'Alert')]"));
		loginPage.clickOk();

		// Assert alert is displayed due to blank password, not successful login
		Assert.assertTrue(getDriver().getPageSource().contains("Welcome to"),
				"Alert message not displayed for blank password");
	}

	@Test(groups = "regression", dataProvider = "loginData", dataProviderClass = ExcelDataProvider.class)
	public void testInvalidUsername(String username, String password) {
		LoginPageDemo loginPage = new LoginPageDemo();

		// Use an invalid username (hardcoded or from data, but here keep invalid)
		loginPage.enterUsername("gthakar");
		loginPage.enterPassword(password); // password from data provider

		loginPage.clickLogin();
		utils.WaitUtils.waitForVisibility(getDriver(), By.xpath("//div[@id='root']//p[contains(text(), 'Alert')]"));

		loginPage.authenticationOK();

		// Assert alert is displayed, login should fail for invalid username
		Assert.assertTrue(getDriver().getPageSource().contains("Welcome to"),
				"Alert message not displayed for invalid username");
	}

	@Test(groups = "regression", dataProvider = "loginData", dataProviderClass = ExcelDataProvider.class)
	public void testInvalidPassword(String username, String password) {
		LoginPageDemo loginPage = new LoginPageDemo();

		loginPage.enterUsername(username); // username from data provider
		loginPage.enterPassword("wrongpassword"); // invalid password hardcoded

		loginPage.clickLogin();
		utils.WaitUtils.waitForVisibility(getDriver(), By.xpath("//div[@id='root']//p[contains(text(), 'Alert')]"));

		loginPage.authenticationOK();

		// Assert alert is displayed, login should fail for invalid password
		Assert.assertTrue(getDriver().getPageSource().contains("Welcome to"),
				"Alert message not displayed for invalid password");
	}

	/*
	 * @Test(priority = 6, groups = "Forgot Password") public void
	 * testforgetPasswordLink() { LoginPageDemo loginPage = new
	 * LoginPageDemo(getdriver()); loginPage.clickFogotPasswordLink();
	 * Assert.assertTrue(loginPage.isFogotPasswordPageVisible(),
	 * "Forgot Password page not visible."); }
	 * 
	 */

}