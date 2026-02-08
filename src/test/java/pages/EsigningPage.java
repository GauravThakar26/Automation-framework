package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


import driver.DriverManager;
import utils.WaitUtils;

public class EsigningPage {

	private WebDriver getDriver() {
		return DriverManager.getDriver();
	}

	// Esign Mode locators
	private By sequentialToggle = By.xpath("//div[@class='form-group1']//div[@class='row']//div[1]//input[1]");
	private By parallelToggle = By.xpath("//div[@class='col-md-4']//div[2]//input[1]");

	// Add Signatory details locators
	private By userName = By.xpath("//input[@id='name']");
	private By emailAddress = By.xpath("//input[@id='emailaddress']");

	// Type locators
	private By anyEsignToggle = By.xpath("//*[@id=\"signTypeddl0\"]");
	private By esignToggle = By.xpath(
			"/html/body/div/div/main/div/div[4]/div/div[1]/div[2]/div[1]/div/div/div/div[1]/form/div[4]/fieldset/div[2]/input");
	private By esignwithoutOTPToggle = By.xpath(
			"/html/body/div/div/main/div/div[4]/div/div[1]/div[2]/div[1]/div/div/div/div[1]/form/div[4]/fieldset/div[3]/input");
	private By aadharToggle = By.xpath("//label[contains(text(), 'Aadhar')]/preceding-sibling::input");

	// Sign Pages locators

	private By allPage = By.id("signpageNoddl0");
	private By firstPage = By.id("signpageNoddl1");
	private By lastPage = By.id("signpageNoddl2");
	private By customPage = By.id("signpageNoddl3");
	private By customAllPage = By.id("signpageNoddl4");

	private By AddRecipient = By.xpath("//input[@onclick='AddRecipient()']");
	private By sendForSignature = By.xpath("//input[@id='sendforsignature']");
	private By okBtn = By.xpath("//div[@id='divRedirect']//button[@id='btnOk']");
	private By addSignatoryBtn = By.xpath("//span[@class='glyphicon glyphicon-plus']");

	public By getElementAfterRefreshLocator() {
		return (userName);
	}

	public void enterSignatoryDetails(String name, String email) {
		getDriver().findElement(userName).clear();
		getDriver().findElement(userName).sendKeys(name);
		getDriver().findElement(emailAddress).clear();
		getDriver().findElement(emailAddress).sendKeys(email);
		getDriver().findElement(AddRecipient).click();

	}

	public void selectSignType(String type) {
		switch (type.toLowerCase()) {
		case "anyEsign":
			getDriver().findElement(anyEsignToggle).click();
			break;
		case "esign":
			getDriver().findElement(esignToggle).click();
			break;
		case "esignwithoutotp":
			getDriver().findElement(esignwithoutOTPToggle).click();
			break;
		case "aadhar":
			getDriver().findElement(aadharToggle).click();
			break;
		}
	}

	public void selectSignPages(String pageOption) {
		switch (pageOption.toLowerCase()) {
		case "all":
			getDriver().findElement(allPage).click();
			break;
		case "first":
			getDriver().findElement(firstPage).click();
			break;
		case "last":
			getDriver().findElement(lastPage).click();
			break;
		case "custom":
			getDriver().findElement(customPage).click();
			break;
		case "customAll":
			getDriver().findElement(customAllPage).click();
			break;
		}
	}

	public void clickSendForSignature() {
		getDriver().findElement(sendForSignature).click();
		WaitUtils.fluentWaitForVisibility(getDriver(), okBtn);
		getDriver().findElement(okBtn).click();

	}

}