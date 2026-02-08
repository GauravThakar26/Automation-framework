package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import base.BaseTest;
import pages.ContractCreationPage;
import pages.RequestDetailsPage;
import utils.ExcelDataProvider;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ContractCreationTest extends BaseTest {

	@Test(dataProvider = "autoapprovedContract", dataProviderClass = ExcelDataProvider.class, description = "Verify that a contract is successfully created after request approval and version locking.", groups = "smoke")
	public void testcreateContract(String username, String password, String contractType, String templateType,
			String selfParty, String otherParty, String responsibleUser, String groupType, String productType,
			String contractTag, String requestApprover, Boolean isNonStandard) throws InterruptedException {

		// Use format "dd MMMM yyyy" (e.g., "11 September 2025") as expected by the POM
		DateTimeFormatter displayFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy");

		String periodFrom = LocalDate.now().plusDays(1).format(displayFormat);
		String periodTo = LocalDate.now().plusYears(1).format(displayFormat);

		//Precondition: Log in and create a new contract request
		loginAndCreateRequest(username, password, contractType, templateType, selfParty, otherParty, responsibleUser,
				groupType, productType, contractTag, requestApprover, isNonStandard);

		RequestDetailsPage detailsPage = new RequestDetailsPage();
		detailsPage.openTemplatePopupAndLoad();
		detailsPage.clickSave();
		detailsPage.clickOk();
		detailsPage.lockVersionandConfirm();
		detailsPage.lockversionmessagePopup();
		detailsPage.lockVersionOk();
		detailsPage.clickProceed();

		SoftAssert softAssert = new SoftAssert();

		// Validate that user is navigate to contract creation page
		softAssert.assertTrue(getDriver().getCurrentUrl().contains("addcontract"), "Expected to navigate to contract creation page.");

		ContractCreationPage contractPage = new ContractCreationPage();
		contractPage.clickExecutionDate(); // auto-set today's execution date

		// Select from and to dates dynamically via calendar
		contractPage.selectPeriodRange(periodFrom, periodTo);

		// Verify period range input field shows the correct value
		String selectedPeriod = getDriver().findElement(By.id("PeriodFromToDate")).getDomAttribute("value");
		System.out.println(selectedPeriod);

		// Save the contract form and handle confirmation popups
		contractPage.clickSave();
		System.out.println(getDriver().getCurrentUrl());
		Assert.assertTrue(getDriver().getCurrentUrl().contains("ContractDetails"), "Expected to navigate to Contract Details page.");

		softAssert.assertAll();
	}

}
