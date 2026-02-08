package pages;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitUtils;
import driver.DriverManager;

public class RequestCreationPage {

	// Locators for the request creation page
	By contractTypeDropdown = By.id("react-select-2-input");
	By TemplateSelection = By.id("react-select-3-input"); // Template selection
	By templateTypeToggle = By.xpath("//label[2]//span[1]"); // non-standard document template type
	By selfPartyDropdown = By.xpath("//*[@id=\"react-select-9-input\"]");
	By otherPartyDropdown = By.xpath("//*[@id=\"react-select-10-input\"]");
	By responsibleUserDropdown = By.id("react-select-11-input");
	By groupTypeDropdown = By.id("react-select-12-input");
	By productTypeDropdown = By.id("react-select-13-input");
	By contractTitleInput = By.xpath("//input[@class='mandatory']");
	By contractTagDropdown = By.id("react-select-14-input");
	By documentUploadArea = By.xpath("//label[contains(text(), 'Contract Copy')]/following::input[@type='file'][1]");
	By firstOption = By.xpath("//label/input[@type='radio' and @value='Self Contract']");
	By secondOption = By.xpath("//label/input[@type='radio' and @value='Third Party Upload']");
	By saveButton = By.xpath("//input[@value='Create']");
	By clickOk = By.xpath("//input[@value='Ok']");
	By documentUpload = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div[1]/div[3]/input");
	By FileUploadSuccessfullOkBtn = By.xpath("//input[@value='Ok']");

	By requestApprovername = By.xpath("//*[@id=\"react-select-18-input\"]");

	// Helper to get driver from DriverManager
	private WebDriver getDriver() {
		return DriverManager.getDriver();
	}

	public void fillContractRequestForm(String contractType, String templateType, String selfParty, String otherParty,
			String responsibleUser, String groupType, String productType, String contractTag, String requestApprover,
			boolean isNonStandard) {
		String generatedTitle = contractType + " - " + otherParty;
		selectDropdownOption(contractTypeDropdown, contractType);
		WebElement titleInput = getDriver().findElement(contractTitleInput);
		titleInput.clear();
		titleInput.sendKeys(generatedTitle);

		if (isNonStandard) {
			nonStandardDocument(); // Upload document and skip template selection
		} else {
			selectDropdownOption(TemplateSelection, templateType);
		}
		selectDropdownOption(selfPartyDropdown, selfParty);

		WaitUtils.waitForVisibility(getDriver(), otherPartyDropdown);
		selectDropdownOption(otherPartyDropdown, otherParty);

		selectDropdownOption(responsibleUserDropdown, responsibleUser);
		if (requestApprover != null && !requestApprover.trim().isEmpty()) {
			selectDropdownOption(requestApprovername, requestApprover);
			System.out.println("✅ Approver selected: " + requestApprover);
		} else {
			System.out.println("Approver not provided — skipping approver selection.");
		}

		if (groupType != null && !groupType.isEmpty()) {
			selectDropdownOption(groupTypeDropdown, groupType);
		} else {
			System.out.println("Group type is empty or null. Skipping dropdown selection.");
		}
		if (productType != null && !productType.isEmpty()) {
			selectDropdownOption(productTypeDropdown, productType);
		} else {
			System.out.println("Group type is empty or null. Skipping dropdown selection.");
		}
		//selectDropdownOption(contractTagDropdown, contractTag);
	}

	public void clickSave() {
		getDriver().findElement(saveButton).click();
		WaitUtils.fluentWaitForVisibility(getDriver(), clickOk);
		getDriver().findElement(clickOk).click();
	}

	private void nonStandardDocument() { // select template type Third party and upload document
		WebElement nonStandardToggle = getDriver().findElement(templateTypeToggle);
		nonStandardToggle.click();

		WebElement firstFileInput = getDriver().findElement(documentUpload);
		firstFileInput.sendKeys(
				"D:\\Gaurav_Thakar\\Backup automationProject\\11-09-2025 backup\\Razorsign\\TestData\\Service Provider Agreement SA.docx");

		WaitUtils.fluentWaitForVisibility(getDriver(), FileUploadSuccessfullOkBtn);
		getDriver().findElement(FileUploadSuccessfullOkBtn).click();
		System.out.println("File uploaded successfully.....");

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void selectDropdownOption(By dropdownLocator, String value) {
		WebDriver driver = getDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownLocator));
		WebElement dropdown = driver.findElement(dropdownLocator);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dropdown);
		dropdown.click();

		// Type the value
		dropdown.sendKeys(value);

		// Wait for options to load (if applicable)
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", dropdown);

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		wait.until(ExpectedConditions.textToBePresentInElementValue(dropdown, value));

		// Select it
		dropdown.sendKeys(Keys.ENTER);
	}

}
