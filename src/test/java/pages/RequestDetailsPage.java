package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitUtils;
import driver.DriverManager;

public class RequestDetailsPage {

	// Locators for add Questionnaire pop up and alert pop ups
	By selectTemplateButton = By.xpath("//a[normalize-space()='Select Template']");
	By LoadQuestionnaireBtn = By.xpath("//input[@value='Load']");
	By selfPartyNameField = By.id("Self Party 1 Name");
	By selfPartyAddressField = By.id("Self Party 1 Address");
	By otherPartyNameField = By.id("Other Party 1 Name");
	By groupField = By.id("Group Name");
	By saveBtn = By.xpath("//input[@value='Save']");
	By clickOk = By.cssSelector("input[value='Ok']");
	By modalMessageLocator = By.cssSelector(".popupText");

	// Locators for summary details
	By contractTitle = By.xpath("//*[@id=\"scrollbar1N\"]/div/div/div[2]/div[2]/div");
	By contractType = By.xpath("//div[@class='summery-wrapN']//div[4]//div[2]//div[1]");
	By selfParty = By.xpath("//*[@id=\"scrollbar1N\"]/div/div/div[8]/div[2]/div");
	By counterParty = By.xpath("//*[@id=\"scrollbar1N\"]/div/div/div[9]/div[2]/div");
	By scrollContainerLocator = By.xpath("//*[@id='contractsFilterList']/div/div[1]");// to scroll down summary list
	By productType = By.xpath("//*[@id=\"scrollbar1N\"]/div/div/div[10]/div[2]/div");
	By groupType = By.xpath("//*[@id=\"scrollbar1N\"]/div/div/div[11]/div[2]/div");
	By responsibleMember = By.xpath("//*[@id=\"scrollbar1N\"]/div/div/div[15]/div[2]/div");

	// Locators for Approve request
	By approveButton = By.xpath("//input[@value='Approve']");
	By approveRequestToggle = By.xpath("//label[normalize-space()='Approve']//span[@class='checkmark']");
	By approveRequestSubmit = By.id("ApproveContract");
	By requestApprovedOk = By.xpath("//input[@value='Ok']");
	By requestApprovedMessage = By.xpath("//div[@class='request-wrap']//div//div[contains(text(),'Approved')]");

	// Locators for Lock version
	By threeDots = By.xpath("//*[@id=\"root\"]/div/div[2]/div[1]/div[1]/div[4]/ul/li[5]");
	By lockVersion = By.xpath("//input[@value='Lock Version']");
	By lockversionYes = By.cssSelector("input[value='Yes']");
	By lockmessagePopup = By.xpath("//div[@class='popupText']");
	By documentLockOk = By.xpath("//input[@value='Ok']");

	// Locators for UnLock version
	By unlockVersion = By.xpath("//input[@value='Unlock version']");
	By unlockVersionYes = By.xpath("//*[@id=\"root\"]/div/div[2]/div[1]/div[2]/div/div/div[3]/input[1]");
	By unlockmessagePopup = By.xpath("//div[@class='popupText']");
	By documentUnLockOk = By.xpath("//*[@id=\"root\"]/div/div[2]/div[1]/div[2]/div/div/div[3]/input");

	// Locators for delete version
	By deleteVersion = By.xpath("//input[@value='Delete Version']");
	By deleteYes = By.xpath("//*[@id=\"root\"]/div/div[2]/div[1]/div[2]/div/div/div[3]/input[1]");
	By deletemessagePopup = By.xpath("//div[@class='popupText']");
	By deleteVersionOk = By.xpath("//*[@id=\"root\"]/div/div[2]/div[2]/div/div/div[3]/input");

	// Locators for the share mail option
	By shareMailIcon = By.xpath("//span[@title='Send Mail']");
	By toMail = By.xpath("//textarea[@placeholder=\"@User's / Other Party Or Email Address\"]");
	By subjectHeading = By.id("SubjectInput");
	By subjectBodyText = By.id("BodyInput");
	By sendMailBtn = By.id("SendEmail");

	By requestID = By.xpath("//*[@id=\"root\"]/div/div[2]/div[1]/div[1]/div[2]/span[1]");
	By proceedBtn = By.xpath("//input[@value='Proceed']");

	// close Request locators
	By closeRequest = By.xpath("//input[@title='Close Request']");
	By statusDropdown = By
			.xpath("//*[@id=\"root\"]/div/div[2]/div[1]/div[2]/div/div/div/div[2]/div/div/div/div[2]/div");
	By closeDate = By.xpath("//*[@id=\"scrollbar1N\"]/div[1]/div/div[1]/div[1]/div[2]/div");
	By closeContractBtn = By.xpath("//input[@id='CloseContract']");
	By requestClosedOkBtn = By.xpath("//input[@value='Ok']");
	By requestClosedStatusField = By.xpath("//*[@id=\"scrollbar1N\"]/div[1]/div/div[1]/div[2]/div[2]/div");
	By clickEsign = By.xpath("//input[@value='eSign']");

	// Locators for sign details dropdown
	By summaryDropdown = By.xpath("//*[@id=\"contractsFilterList\"]/div/div[1]/div[1]/div/div/div[1]/div[2]");
	By recallOption = By.xpath("//button[@title='Recall']");
	By recallConfirm = By.xpath("//input[@value='Ok']");
	By recallModalMessage = By.xpath("//div[@class='popupText']");
	By recallStatus = By.xpath("//div[contains(text(),'Status: Recalled')]");

	// Locators for the conditional approval
	By remarkText = By.xpath("//div[@class='rejectBoxBg_wrap']//textarea");
	By approveBtnConditional = By.id("btnApprove");
	By pendingBtnConditional = By.id("btnReject");
	By conditionalOkPopup = By.xpath("//*[@id=\"contractsFilterList\"]/div/div[1]/div[2]/div[5]/div/div/div[3]/input");
	By conditionalApprovalStatus = By.xpath("//div[@class='containerWrapper']");

	// Helper to get driver
	private WebDriver getDriver() {
		return DriverManager.getDriver();

	}

	public void gotoSignDetails() {
		WebDriver driver = getDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// 1. Wait for dropdown to be visible and clickable
		WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(summaryDropdown));

		// 2. Click the dropdown to open options
		dropdown.click();

		// 3. Wait for the input field inside the dropdown
		// locator for the option from the dropdown
		WebElement inputField = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//input[@aria-autocomplete='list' and @type='text']")));

		// 4. Type the option you want to select
		inputField.sendKeys("sign");

		// 5. Press Enter to select
		inputField.sendKeys(Keys.ENTER);
	}

	public void recallEsign() {
		WebDriver driver = getDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement recall = wait.until(ExpectedConditions.elementToBeClickable(recallOption));
		recall.click();

	}

	public String getrecallModalMessage() {
		return getDriver().findElement(recallModalMessage).getText();
	}

	public By getsummaryDropdownAfterRefresh() {
		return (summaryDropdown);
	}

	// Generic safe text get method to avoid stale element for texts on the page
	private String getSafeText(By locator) {
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
		WebElement element = wait
				.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(locator)));
		return element.getText().trim();
	}

	// Safely gets attribute value after waiting for visibility and avoiding stale
	// elements

	protected String getSafeAttribute(By locator, String attributeValue) {
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
		WebElement element = wait
				.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(locator)));
		return element.getDomAttribute(attributeValue);
	}

	// Actions
	public void openTemplatePopupAndLoad() {
		WaitUtils.fluentWaitForVisibility(getDriver(), selectTemplateButton);
		getDriver().findElement(selectTemplateButton).click();
		WaitUtils.fluentWaitForVisibility(getDriver(), LoadQuestionnaireBtn);
		getDriver().findElement(LoadQuestionnaireBtn).click();
	}

	public String getSelfPartyName() {
		return getDriver().findElement(selfPartyNameField).getDomAttribute("value");
	}

	public String getOtherPartyName() {
		return getSafeAttribute(otherPartyNameField, "value");
	}

	public String getSelfPartyAddress() {
		return getDriver().findElement(selfPartyAddressField).getDomAttribute("value");
	}

	public String getGroupField() {
		return getDriver().findElement(groupField).getDomAttribute("value");
	}

	// Get text from details page and match with request creation page
	public String contractTitle() {
		return getDriver().findElement(contractTitle).getText();
	}

	public String contractType() {
		return getDriver().findElement(contractType).getText();
	}

	public String selfParty() {
		return getDriver().findElement(selfParty).getText();
	}

	public String counterParty() {
		return getDriver().findElement(counterParty).getText();
	}

	public String productType() {
		return getDriver().findElement(productType).getText();
	}

	public String groupType() {
		return getDriver().findElement(groupType).getText();
	}

	public String responsibleMember() {
		return getDriver().findElement(responsibleMember).getText();
	}

	public void clickSave() {
		getDriver().findElement(saveBtn).click();
	}

	public String getModalMessageText() {
		return getDriver().findElement(modalMessageLocator).getText();
	}

	public void clickOk() {
		getDriver().findElement(clickOk).click();
	}

	public String getRequestID() {
		return getSafeText(requestID);
	}

	public String ClosedDate() {
		return getDriver().findElement(closeDate).getText();
	}

	public String ClosedStatus() {
		return getDriver().findElement(requestClosedStatusField).getText();
	}

	public void ClickEsign() {
		WebDriver driver = getDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(clickEsign)).click();
	}

	public void approveRequest() {
		WebDriver driver = getDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		By popupOverlay = By.className("popupOuter");
		By popupOkButton = By.xpath("//input[@value='Ok']");

		try {
			// Try to find the popup
			WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(popupOverlay));
			if (popup.isDisplayed()) {
				System.out.println("Popup detected, clicking OK.");
				wait.until(ExpectedConditions.elementToBeClickable(popupOkButton)).click();
				// Wait until popup disappears
				wait.until(ExpectedConditions.invisibilityOfElementLocated(popupOverlay));
			}
		} catch (TimeoutException e) {
			// Popup not found or not visible, so continue
			System.out.println("No approved request popup detected, proceeding...");
		}

		// click Approve button and continue flow
		wait.until(ExpectedConditions.elementToBeClickable(approveButton)).click();
		wait.until(ExpectedConditions.elementToBeClickable(approveRequestToggle)).click();
		wait.until(ExpectedConditions.elementToBeClickable(approveRequestSubmit)).click();
		wait.until(ExpectedConditions.elementToBeClickable(requestApprovedOk)).click();
	}

	public String getrequestApprovedMessage() {
		WebDriver driver = getDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement confirmationElement = wait
				.until(ExpectedConditions.visibilityOfElementLocated(requestApprovedMessage));
		return confirmationElement.getText();
	}

	// Both these methods will be used in future if needed

	/*
	 * public void selectDateFromCalendar(String dateStr) { WebDriver driver =
	 * getDriver(); // Or pass driver as param if needed WebDriverWait wait = new
	 * WebDriverWait(driver, Duration.ofSeconds(10));
	 * 
	 * // Parse date string to LocalDate DateTimeFormatter formatter =
	 * DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH); LocalDate date =
	 * LocalDate.parse(dateStr, formatter);
	 * 
	 * String day = String.valueOf(date.getDayOfMonth()); String month =
	 * date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH); String year =
	 * String.valueOf(date.getYear());
	 * 
	 * // Navigate calendar to correct month and year navigateToMonthYear(month,
	 * year);
	 * 
	 * // Build XPath for the target day based on aria-label attribute String
	 * ariaLabelPart = month + " " + day + " of " + year; By dateLocator = By
	 * .xpath("//div[contains(@aria-label, '" + ariaLabelPart +
	 * "') and contains(@class,'rmdp-day')]");
	 * 
	 * // Wait for date to be clickable and click it
	 * wait.until(ExpectedConditions.elementToBeClickable(dateLocator)).click(); }
	 * 
	 *//**
		 * Helper method to navigate calendar to the desired month and year
		 *//*
			 * private void navigateToMonthYear(String targetMonth, String targetYear) {
			 * WebDriver driver = getDriver(); WebDriverWait wait = new
			 * WebDriverWait(driver, Duration.ofSeconds(5));
			 * 
			 * while (true) { WebElement monthElem =
			 * driver.findElement(By.xpath("//div[@class='rmdp-header-values']/span[1]"));
			 * WebElement yearElem =
			 * driver.findElement(By.xpath("//div[@class='rmdp-header-values']/span[2]"));
			 * 
			 * String currentMonth = monthElem.getText().trim(); String currentYear =
			 * yearElem.getText().trim();
			 * 
			 * if (currentMonth.equalsIgnoreCase(targetMonth) &&
			 * currentYear.equals(targetYear)) { break; }
			 * 
			 * WebElement rightArrow =
			 * driver.findElement(By.cssSelector(".rmdp-arrow-container.rmdp-right"));
			 * rightArrow.click();
			 * 
			 * try { Thread.sleep(300); // Wait for calendar to update } catch
			 * (InterruptedException e) { Thread.currentThread().interrupt(); } } }
			 */

	public void closeRequest(String statusText) {
		WebDriver driver = getDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// 1. Click on Close Request button/icon
		wait.until(ExpectedConditions.elementToBeClickable(closeRequest)).click();

		// 2. Click the dropdown to open options
		WebElement dropdownContainer = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//div[contains(@class,'mandatory__value-container')]")));
		dropdownContainer.click();

		// 3. Find the dynamic react-select input
		WebElement inputField = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//input[contains(@id,'react-select') and contains(@class,'mandatory__input')]")));

		inputField.sendKeys(statusText); // Pass the status from test

		inputField.sendKeys(Keys.ENTER);

		// 4. Click on Close Contract button
		WebElement closerequestBtn = wait.until(ExpectedConditions.elementToBeClickable(closeContractBtn));
		closerequestBtn.click();
		// 5. Click on OK button in confirmation popup
		WebElement okBtn = wait.until(ExpectedConditions.elementToBeClickable(requestClosedOkBtn));
		okBtn.click();
	}

	// locator for reopen request
	By reopenRequest = By.xpath("//input[@title='Reopen Request']");
	By reopenRequestConfirmation = By.xpath("//input[@value='Yes']");
	By reopenRequestConfirmationComment = By.xpath("//textarea[@id='Description']");
	By reopenRequestConfirmationCommentSave = By.xpath("//input[@value='Save']");
	By reopenRequestConfirmationMessage = By.xpath("//div[@class='popupText']");
	By reopenRequestConfirmationMessageOkBtn = By.xpath("//input[@value='Ok']");

	public void reopenRequest() {
		WebDriver driver = getDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(reopenRequest)).click();
		WebElement reopenRequestYes = wait.until(ExpectedConditions.elementToBeClickable(reopenRequestConfirmation));
		reopenRequestYes.click();
		WebElement reopenRequestConfirmationCommentBox = wait
				.until(ExpectedConditions.elementToBeClickable(reopenRequestConfirmationComment));
		reopenRequestConfirmationCommentBox.sendKeys("Reopened request");

		WebElement reopenRequestSave = wait
				.until(ExpectedConditions.elementToBeClickable(reopenRequestConfirmationCommentSave));
		reopenRequestSave.click();
	}

	public String reopenRequestMessage() {
		WaitUtils.waitForVisibility(getDriver(), reopenRequestConfirmationMessage);
		return getDriver().findElement(reopenRequestConfirmationMessage).getText().trim();
	}

	public void deleteVersionandConfirm() {
		WebDriver driver = getDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		Actions actions = new Actions(driver);

		// Retry block for StaleElementReferenceException
		for (int attempt = 0; attempt < 3; attempt++) {
			try {
				WebElement menuTrigger = wait.until(ExpectedConditions.visibilityOfElementLocated(threeDots));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", menuTrigger);

				actions.moveToElement(menuTrigger).pause(Duration.ofSeconds(3)).perform();

				WebElement deleteVersionBtn = wait.until(ExpectedConditions.elementToBeClickable(deleteVersion));
				deleteVersionBtn.click();

				WebElement deleteYesBtn = wait.until(ExpectedConditions.elementToBeClickable(deleteYes));
				deleteYesBtn.click();

				// If everything worked, break out of retry loop
				break;
			} catch (StaleElementReferenceException e) {
				System.out.println("StaleElementReferenceException caught, retrying... attempt " + (attempt + 1));
				// Small wait before retrying
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}

	public String deletemessagePopup() {
		WaitUtils.waitForVisibility(getDriver(), deletemessagePopup);
		return getDriver().findElement(deletemessagePopup).getText().trim();
	}

	public void deleteVersionOk() {
		WaitUtils.waitForVisibility(getDriver(), deleteVersionOk);
		getDriver().findElement(deleteVersionOk).click();
	}

	public void lockVersionandConfirm() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
		Actions actions = new Actions(getDriver());

		WebElement menuTrigger = getDriver().findElement(threeDots);
		((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", menuTrigger);
		WaitUtils.waitForVisibility(getDriver(), threeDots);

		actions.moveToElement(menuTrigger).pause(Duration.ofSeconds(3)).perform();

		int retries = 3;
		while (retries > 0) {
			try {
				// Wait for lockVersionBtn and re-locate if necessary
				WebElement lockVersionBtn = wait.until(ExpectedConditions.elementToBeClickable(lockVersion));
				lockVersionBtn.click();

				// Wait for lockYesBtn and re-locate if necessary
				WebElement lockYesBtn = wait.until(ExpectedConditions.elementToBeClickable(lockversionYes));
				lockYesBtn.click();

				// If both clicks succeed, break the loop
				break;
			} catch (StaleElementReferenceException e) {
				retries--;
				if (retries == 0) {
					throw e; // Rethrow after all attempts are exhausted
				}

				// Re-locate elements if they become stale
				menuTrigger = getDriver().findElement(threeDots);
				actions.moveToElement(menuTrigger).pause(Duration.ofSeconds(3)).perform();
			}
		}
	}

	public String lockversionmessagePopup() {
		WaitUtils.fluentWaitForVisibility(getDriver(), lockmessagePopup);
		return getDriver().findElement(lockmessagePopup).getText().trim();
	}

	public void lockVersionOk() {
		WaitUtils.waitForVisibility(getDriver(), documentLockOk);
		getDriver().findElement(documentLockOk).click();
	}

	public void unlockVersionandConfirm() throws InterruptedException {
		WebElement menuTrigger = getDriver().findElement(threeDots);
		((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", menuTrigger);
		WaitUtils.waitForVisibility(getDriver(), threeDots);
		// Use Actions to move to the element
		Actions actions = new Actions(getDriver());
		actions.moveToElement(menuTrigger).pause(Duration.ofSeconds(1)) // Give the UI time to render the hover menu
				.perform();
		WaitUtils.waitForVisibility(getDriver(), unlockVersion);
		getDriver().findElement(unlockVersion).click();
		getDriver().findElement(unlockVersionYes).click();

	}

	public String unlockversionmessagePopup() {
		WaitUtils.fluentWaitForVisibility(getDriver(), unlockmessagePopup);
		return getDriver().findElement(unlockmessagePopup).getText().trim();
	}

	public void unlockVersionOk() {
		WaitUtils.waitForVisibility(getDriver(), documentUnLockOk);
		getDriver().findElement(documentUnLockOk).click();
	}

	public void clickProceed() {
		WaitUtils.waitForVisibility(getDriver(), proceedBtn);
		getDriver().findElement(proceedBtn).click();
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
		wait.until(ExpectedConditions.urlContains("addcontract"));
	}

	public void conditionalApprovalRemarkAdd() {
		WaitUtils.waitForVisibility(getDriver(), remarkText);
		getDriver().findElement(remarkText).sendKeys("Approved");
		getDriver().findElement(approveBtnConditional).click();
		WaitUtils.waitForVisibility(getDriver(), conditionalOkPopup);
		getDriver().findElement(conditionalOkPopup).click();

	}

	public boolean isApprovedTextPresent() {
		WebElement container = getDriver().findElement(conditionalApprovalStatus);
		String text = container.getText().toLowerCase();
		return text.contains("approved");
	}

	// Locators for stage update
	By addStageBtn = By.xpath("//button[normalize-space()='Add']");
	By stageDropdownOptions = By.xpath("//input[contains(@class,'mandatory__input') and @aria-autocomplete='list']");
	By stageDropdownOuterBox = By.xpath("//div[@class='mandatory__control css-13cymwt-control']");
	By assigneeNameDropdown = By.xpath("//*[@id=\"react-select-4-input\"]");
	By summaryDropdown1 = By.id("react-select-19-input");
	By departmentName = By.xpath("//input[@id='react-select-11-input']");
	By descriptionText = By.xpath("//*[@id=\"BodyInput\"]");
	By updateBtn = By.xpath("//input[@value='Update']");
	By stageOkBtn = By.xpath("//input[@type='button' and @value='Ok']");
	By statusTextTimeline = By.xpath("//div[@id='scrollbartimeline1N']//p[contains(text(),'Stage Changed')]");

	public void performStageADD(String value) { // Wait for the summary dropdown to be visible and send input "stage"
		WaitUtils.waitForVisibilityAndClickable(getDriver(), summaryDropdown1, 1);
		getDriver().findElement(summaryDropdown1).sendKeys("stage");
		getDriver().findElement(summaryDropdown1).sendKeys(Keys.ENTER);

		// Wait for the "Add" button to be visible and clickable, then click it //
		WebElement addStageButton = getDriver().findElement(addStageBtn);
		addStageButton.click();

		// Wait for the dropdown outer box to be visible and clickable, then click it
		WaitUtils.waitForVisibilityAndClickable(getDriver(), stageDropdownOuterBox, 0);
		WebElement dropdownOuterBox = getDriver().findElement(stageDropdownOuterBox);
		dropdownOuterBox.click();
		System.out.println("clicked stage update dropdown");

		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
		WaitUtils.fluentWaitForVisibility(getDriver(), stageDropdownOptions);
		WebElement stageUpdateName = getDriver().findElement(stageDropdownOptions);
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		js.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", stageUpdateName);
		stageUpdateName.sendKeys(value);

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		wait.until(ExpectedConditions.textToBePresentInElementValue(stageUpdateName, value));

		// Select it

		stageUpdateName.sendKeys(Keys.ENTER);

		System.out.println("stage selected.....");

		WebElement descriptionField = getDriver().findElement(descriptionText);
		descriptionField.sendKeys("test");
		WaitUtils.fluentWaitForVisibility(getDriver(), updateBtn);
		// click on update button
		WebElement updateButton = getDriver().findElement(updateBtn);
		updateButton.click();
		System.out.println("update button clicked....");

		// Wait for the "Ok" button to be clickable and click it
		WaitUtils.waitForVisibilityAndClickable(getDriver(), stageOkBtn, 2);
		WebElement okBtn = getDriver().findElement(stageOkBtn);
		okBtn.click();
		System.out.println("Stage Added successfully....");

	}

	public boolean getStageUpdateDescription() {
		getDriver().navigate().refresh();
		WebElement timelineText = getDriver().findElement(statusTextTimeline);

		String text1 = timelineText.getText().toLowerCase();
		System.out.println("timeline text is :" + text1);
		return text1.contains("stage changed");

	}

	// Locators for the edit stage
	By summaryDropdown3 = By.id("react-select-19-input");
	By container = By.xpath("//div[contains(@class,'gridBoxList infoTxtMouseover')]");
	By threeDots1 = By.xpath("//img[@class='rowAction']");
	By editStage = By.xpath("//button[normalize-space()='Edit']");
	By stageEditConfirmation = By.xpath("//input[@value='Ok']");

	By description1 = By.xpath("//*[@id='BodyInput']");
	By updateBtn1 = By.xpath("//input[@value='Update']");

	public void editStageUpdate() {
		WebDriver driver = getDriver();
		Actions actions = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			WebElement hoverTarget = wait.until(ExpectedConditions.presenceOfElementLocated(container));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", hoverTarget);
			actions.moveToElement(hoverTarget).perform();
			System.out.println("✅ Hovered over container.");

			WebElement threeDots = wait.until(ExpectedConditions.elementToBeClickable(threeDots1));
			actions.moveToElement(threeDots).click().perform();
			System.out.println("✅ Clicked on three dots.");

			WebElement editStageBtn = wait.until(ExpectedConditions.elementToBeClickable(editStage));
			try {
				editStageBtn.click();
			} catch (ElementNotInteractableException e) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", editStageBtn);
				System.out.println("⚠️ Used JS click for Edit button.");
			}

			WebElement descriptionField = wait.until(ExpectedConditions.visibilityOfElementLocated(description1));
			descriptionField.clear();
			descriptionField.sendKeys("Stage is edited...");
			System.out.println("✅ Description updated.");

			WebElement updateButton = wait.until(ExpectedConditions.elementToBeClickable(updateBtn1));
			updateButton.click();
			System.out.println("✅ Update button clicked after edit.");

		} catch (Exception e) {
			System.err.println("❌ Error during stage edit: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public boolean isStageEdited() {
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
		WebElement confirmationMessage = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@class='popupText' and contains(text(),'Updated')]")));
		String stageUpadteMessage = confirmationMessage.getText().trim();
		return stageUpadteMessage.contains("Record Updated Successfully.");
	}

	public void stageEditConfirmation() {
		// Wait for the "Ok" button to be clickable and click it
		WaitUtils.waitForVisibilityAndClickable(getDriver(), stageEditConfirmation, 5);
		WebElement okBtn = getDriver().findElement(stageEditConfirmation);
		okBtn.click();
		System.out.println("Stage updated successfully....");
	}

	// locators for the delete stage
	By container1 = By.xpath("//div[contains(@class,'gridBoxList infoTxtMouseover')]");
	By threeDots2 = By.xpath("//img[@class='rowAction']");
	By deleteStage = By.xpath("//button[normalize-space()='Delete']");
	By stageDeleteConfirmationMessage = By.xpath("//div[@class='popupText' and contains(text(),'Deleted')]");
	By stageDeleteConfirmationOk = By.xpath("//input[@value='Ok']");

	public void deleteStageUpdate() {
		WebDriver driver = getDriver();
		Actions actions = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			WebElement hoverTarget = wait.until(ExpectedConditions.presenceOfElementLocated(container1));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", hoverTarget);
			actions.moveToElement(hoverTarget).perform();
			System.out.println("✅ Hovered over container.");

			WebElement threeDots = wait.until(ExpectedConditions.elementToBeClickable(threeDots2));
			actions.moveToElement(threeDots).click().perform();
			System.out.println("✅ Clicked on three dots.");

			WebElement deleteStageBtn = wait.until(ExpectedConditions.elementToBeClickable(deleteStage));
			try {
				deleteStageBtn.click();
			} catch (ElementNotInteractableException e) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteStageBtn);
				System.out.println("⚠️ Used JS click for delete button.");
			}
		} catch (Exception e) {
			System.err.println("❌ Error during stage delete: " + e.getMessage());
			e.printStackTrace();
		}

	}

	public boolean isStagedeleted() {
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
		WebElement confirmationMessage = wait
				.until(ExpectedConditions.visibilityOfElementLocated(stageDeleteConfirmationMessage));
		String stagedeletedMessage = confirmationMessage.getText().trim();
		return stagedeletedMessage.contains("Record Deleted Successfully.");
	}

	public void stagedeleteConfirmation() {
		// Wait for the "Ok" button to be clickable and click it
		WaitUtils.waitForVisibilityAndClickable(getDriver(), stageDeleteConfirmationOk, 3);
		WebElement okBtn = getDriver().findElement(stageDeleteConfirmationOk);
		okBtn.click();
		System.out.println("stage deleted successfully....");
	}

//locators for the supporting document
	By summaryDropdown2 = By.id("react-select-19-input");
	By addDocumentBtn = By.xpath("//button[normalize-space()='Add']");

	// locators for the document add modal
	By uploadFile = By.xpath("//div[contains(@class,'uploadDocumentN')]//input[@type='file']");
	By categoryDropdownOuterBox = By.xpath("//div[@class='mandatory__control css-13cymwt-control']");
	By categoryDropdownOptions = By.xpath("//input[contains(@class,'mandatory__input') and @aria-autocomplete='list']");
	By description = By.xpath("//li[contains(@class,'input-box1N')]//input[contains(@type,'text')]");
	By documentsaveBtn = By.xpath("//input[contains(@value,'Save')]");
	By documentSaveConfirm = By.xpath("//input[@value='Ok']");

	public void generalDocument(String value) {
		WaitUtils.waitForVisibilityAndClickable(getDriver(), summaryDropdown2, 1);
		getDriver().findElement(summaryDropdown2).sendKeys("document");
		getDriver().findElement(summaryDropdown2).sendKeys(Keys.ENTER);

		WaitUtils.waitForVisibilityAndClickable(getDriver(), addDocumentBtn, 0);
		WebElement addDocumentButton = getDriver().findElement(addDocumentBtn);
		addDocumentButton.click();

		WaitUtils.waitForVisibilityAndClickable(getDriver(), categoryDropdownOuterBox, 0);
		WebElement dropdownOuterBox = getDriver().findElement(categoryDropdownOuterBox);
		dropdownOuterBox.click();
		System.out.println("clicked category dropdown ");

		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
		WaitUtils.fluentWaitForVisibility(getDriver(), categoryDropdownOptions);
		WebElement categoryName = getDriver().findElement(categoryDropdownOptions);
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		js.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", categoryName);
		categoryName.sendKeys(value);
		wait.until(ExpectedConditions.textToBePresentInElementValue(categoryName, value));
		categoryName.sendKeys(Keys.ENTER);
		System.out.println("category selected.....");

		// Wait for the file input to be present
		By fileInput = By.cssSelector("div.uploadDocumentN input[type='file']");
		WebDriverWait wait1 = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

		// Wait for the input to be present
		WebElement uploadInput = wait1.until(ExpectedConditions.presenceOfElementLocated(fileInput));

		JavascriptExecutor js2 = (JavascriptExecutor) getDriver();
		js2.executeScript(
				"arguments[0].style.display='block'; arguments[0].style.opacity='1'; arguments[0].disabled = false;",
				uploadInput);

		// Upload the file
		uploadInput.sendKeys(
				"D:\\Gaurav_Thakar\\Backup automationProject\\11-09-2025 backup\\Razorsign\\TestData\\Service Provider Agreement SA.docx");
		System.out.println("File upload triggered.");

		WebElement descriptionField = getDriver().findElement(description);
		descriptionField.sendKeys("test");

		WaitUtils.fluentWaitForVisibility(getDriver(), documentsaveBtn);
		WebElement documentSave = getDriver().findElement(documentsaveBtn);
		documentSave.click();
		System.out.println("Save button clicked....");
	}

	public void documentUploadConfirmation() {
		// Wait for the "Ok" button to be clickable and click it
		WaitUtils.waitForVisibilityAndClickable(getDriver(), documentSaveConfirm, 5);
		WebElement okBtn = getDriver().findElement(documentSaveConfirm);
		okBtn.click();
		System.out.println("Supporting document uploaded...");

	}

	public boolean isDocumentAttached() {
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
		WebElement confirmationMessage = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='popupText']")));
		String documentUploadMessage = confirmationMessage.getText().trim();
		return documentUploadMessage.contains("Document Save Successfully.");

	}

	// Locators for the supporting document
	By uploadIcon = By.xpath("//button[normalize-space()='Update']");
	By fileInput1 = By.xpath("//div[contains(@class,'uploadDocumentN')]//input[@type='file']");
	By documentUploadBtn = By.xpath("//input[contains(@value,'Save')]");
	By documentSaveConfirm1 = By.xpath("//input[@value='Ok']");
	By thrreDots = By.xpath("//img[@class='rowAction']");

	public void supportingDocumentUpload() {
		WaitUtils.waitForVisibilityAndClickable(getDriver(), summaryDropdown2, 1);
		getDriver().findElement(summaryDropdown2).sendKeys("document");
		getDriver().findElement(summaryDropdown2).sendKeys(Keys.ENTER);
		WebElement addDocumentButton = getDriver().findElement(uploadIcon);
		addDocumentButton.click();

		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
		WebElement uploadInput = wait.until(ExpectedConditions.presenceOfElementLocated(fileInput1));

		JavascriptExecutor js2 = (JavascriptExecutor) getDriver();
		js2.executeScript(
				"arguments[0].style.display='block'; arguments[0].style.opacity='1'; arguments[0].disabled = false;",
				uploadInput);

		// Upload the file
		uploadInput.sendKeys(
				"D:\\Gaurav_Thakar\\Backup automationProject\\11-09-2025 backup\\Razorsign\\TestData\\Service Provider Agreement SA.docx");
		System.out.println("Supporting document upload triggered.");
		WaitUtils.fluentWaitForVisibility(getDriver(), documentsaveBtn);

		WebElement documentUpload = getDriver().findElement(documentUploadBtn);
		documentUpload.click();
		System.out.println("Save button clicked....");

	}

	public void supportingdocumentUploadConfirmation() {
		// Wait for the "Ok" button to be clickable and click it
		WaitUtils.waitForVisibilityAndClickable(getDriver(), documentSaveConfirm1, 5);
		WebElement okBtn = getDriver().findElement(documentSaveConfirm1);
		okBtn.click();
		System.out.println("Supporting document uploaded...");

	}

	public boolean issupportingDocumentAttached() {
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
		WebElement confirmationMessage = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='popupText']")));
		String documentUploadMessage = confirmationMessage.getText().trim();
		return documentUploadMessage.contains("Reference Document Added Successfully");
	}

	// Locators for view supporting document
	By container2 = By.xpath("//div[@class='colgmGrid']");
	By threedots3 = By.xpath("//img[@class='rowAction']");
	By viewDocumentOption = By.xpath("//button[normalize-space()='View']");
	By viewDocumentPopUp = By.xpath("//p[normalize-space()='View Document']");

	public void viewSupportingDocument() {

		WebDriver driver = getDriver();
		Actions actions = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			WebElement hoverTarget = wait.until(ExpectedConditions.presenceOfElementLocated(container2));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", hoverTarget);
			actions.moveToElement(hoverTarget).perform();
			System.out.println("✅ Hovered over container.");

			WebElement threeDots = wait.until(ExpectedConditions.elementToBeClickable(threedots3));
			actions.moveToElement(threeDots).click().perform();
			System.out.println("✅ Clicked on three dots.");

			WebElement viewDocument = wait.until(ExpectedConditions.elementToBeClickable(viewDocumentOption));
			try {
				viewDocument.click();
			} catch (ElementNotInteractableException e) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewDocument);
				System.out.println("⚠️ Used JS click for View button.");
			}
		} catch (Exception e) {
			System.err.println("❌ Error during document view: " + e.getMessage());
			e.printStackTrace();
		}

	}

	public boolean isDocumentPreviewOpened() {
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
		WebElement documentViewModalHeading = wait
				.until(ExpectedConditions.visibilityOfElementLocated(viewDocumentPopUp));
		String modalTitle = documentViewModalHeading.getText().trim();
		return modalTitle.contains("View Document");
	}
	// Locators for delete supporting document
	By container3 = By.xpath("//div[@class='colgmGrid']");
	By threeDots4 = By.xpath("//img[@class='rowAction']");
	By deleteDocumentBtn = By.xpath("//button[normalize-space()='Delete']");
	By deleteDocumentConfirmationYes = By.xpath("//input[@value='Yes']");
	By deleteDocumentConfirmationMessage = By.xpath("//div[@class='summery-wrapN']//div[5]//div[1]//div[1]//div[2]");
	By deleteDocumentConfirmationOk = By.xpath("//div[@id='root']//div[5]//div[1]//div[1]//div[3]//input[1]");

	public void deleteSupportingDocument() {
		WebDriver driver = getDriver();
		Actions actions = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			WebElement hoverTarget = wait.until(ExpectedConditions.presenceOfElementLocated(container3));
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", hoverTarget);
			actions.moveToElement(hoverTarget).perform();
			System.out.println("✅ Hovered over container.");

			WebElement threeDots = wait.until(ExpectedConditions.elementToBeClickable(threeDots4));
			actions.moveToElement(threeDots).click().perform();
			System.out.println("✅ Clicked on three dots.");

			WebElement deleteDocument = wait.until(ExpectedConditions.elementToBeClickable(deleteDocumentBtn));
			try {
				deleteDocument.click();
			} catch (ElementNotInteractableException e) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteDocument);
				System.out.println("⚠️ Used JS click for View button.");
			}
		} catch (Exception e) {
			System.err.println("❌ Error during document delete: " + e.getMessage());
			e.printStackTrace();
		}
		WebElement confirmationYesBtn = wait
				.until(ExpectedConditions.visibilityOfElementLocated(deleteDocumentConfirmationYes));
		confirmationYesBtn.click();
	
	}
	public boolean isSupportingDocumentDeleted() {
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
		WebElement confirmationMessage = wait
				.until(ExpectedConditions.visibilityOfElementLocated(deleteDocumentConfirmationMessage));
		String documentDeletedMessage = confirmationMessage.getText().trim();
		return documentDeletedMessage.contains("Record Deleted Successfully.");		
	}

	public void supportingDocumentdeleteConfirmation() {
		// Wait for the "Ok" button to be clickable and click it
		WaitUtils.waitForVisibilityAndClickable(getDriver(), deleteDocumentConfirmationOk, 5);
		WebElement okBtn = getDriver().findElement(deleteDocumentConfirmationOk);
		okBtn.click();
		System.out.println("Document deleted successfully....");

	}
}
/*
 * public void handleOutlookAfterOkClick(WebDriver driver) { WebDriverWait wait
 * = new WebDriverWait(driver, Duration.ofSeconds(30)); String originalTab =
 * driver.getWindowHandle();
 * 
 * try { // Step 1: Click OK on the original page WebElement esignkButton =
 * wait.until(ExpectedConditions.elementToBeClickable(clickEsign));
 * esignkButton.click();
 * 
 * // Step 2: Wait for new tab to open (Outlook.com) wait.until(driver1 ->
 * driver1.getWindowHandles().size() > 1);
 * 
 * // Step 3: Switch to the new Outlook.com tab Set<String> windowHandles =
 * driver.getWindowHandles(); String outlookTab = windowHandles.stream()
 * .filter(handle -> !handle.equals(originalTab)) .findFirst() .orElseThrow(()
 * -> new RuntimeException("No new tab opened for Outlook."));
 * driver.switchTo().window(outlookTab);
 * 
 * // === Step 4: In Outlook, go to Junk Email === WebElement junkFolder =
 * wait.until(ExpectedConditions.elementToBeClickable(By.
 * xpath("//span[text()='Junk Email']"))); junkFolder.click();
 * 
 * // === Step 5: Search for "Invitation to eSign" email ===
 * wait.until(ExpectedConditions.presenceOfElementLocated(By.
 * xpath("//div[contains(@aria-label,'Message list')]"))); List<WebElement>
 * emails =
 * driver.findElements(By.xpath("//span[contains(text(),'Invitation to eSign')]"
 * )); if (emails.isEmpty()) {
 * System.out.println("No 'Invitation to eSign' emails found."); return; }
 * 
 * emails.get(0).click(); // Open the email
 * 
 * // === Step 6: Handle iframe in email (if present) === try {
 * driver.switchTo().frame(driver.findElement(By.
 * xpath("//iframe[contains(@title, 'Reading Pane')]"))); } catch
 * (NoSuchFrameException e) { // No iframe, continue }
 * 
 * WebElement reviewLink =
 * wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(
 * "Review"))); reviewLink.click();
 * 
 * // === Step 7: Switch to the eSign tab that opened === wait.until(driver1 ->
 * driver1.getWindowHandles().size() > 2); String signTab =
 * driver.getWindowHandles().stream() .filter(handle ->
 * !handle.equals(originalTab) && !handle.equals(outlookTab)) .findFirst()
 * .orElseThrow(() -> new RuntimeException("No sign tab opened."));
 * driver.switchTo().window(signTab);
 * 
 * // === Step 8: Click "Visit Link" in modal === WebElement visitLink =
 * wait.until(ExpectedConditions.elementToBeClickable(By.
 * partialLinkText("Visit Link"))); visitLink.click();
 * 
 * // === Step 9: Proceed to Sign === WebElement proceedToSign =
 * wait.until(ExpectedConditions.elementToBeClickable(By.
 * xpath("//button[contains(text(),'Proceed to Sign')]")));
 * proceedToSign.click();
 * 
 * // === Step 10: Accept Terms and click Sign === WebElement termsCheckbox =
 * wait.until(ExpectedConditions.elementToBeClickable(By.
 * xpath("//input[@type='checkbox' and @name='acceptTerms']"))); if
 * (!termsCheckbox.isSelected()) { termsCheckbox.click(); }
 * 
 * WebElement signButton =
 * wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
 * "//button[contains(text(),'Sign')]"))); signButton.click();
 * 
 * // === Step 11: Wait for 30 seconds === Thread.sleep(30000);
 * 
 * // === Step 12: Close sign and Outlook tabs === driver.close(); // Close
 * current sign tab driver.switchTo().window(outlookTab); driver.close(); //
 * Close Outlook tab
 * 
 * // === Step 13: Switch back to original tab and refresh ===
 * driver.switchTo().window(originalTab); driver.navigate().refresh();
 * 
 * } catch (Exception e) { e.printStackTrace(); } }
 */
