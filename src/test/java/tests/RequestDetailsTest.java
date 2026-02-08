package tests;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import base.BaseTest;
import pages.EsigningPage;
import pages.RequestDetailsPage;
import utils.ExcelDataProvider;
import utils.TabSwitchUtility;

public class RequestDetailsTest extends BaseTest {

	@Test(dataProvider = "templateData", dataProviderClass = ExcelDataProvider.class, description = "Verify that questionnaire saves correctly using template flow", groups = "smoke")
	public void testSaveQuestionnaire(String username, String password, String contractType, String templateType,
			String selfParty, String otherParty, String responsibleUser, String groupType, String productType,
			String contractTag, String requestApprover, Boolean isNonStandard) {

		// Precondition: create request with test data
		loginAndCreateRequest(username, password, contractType, templateType, selfParty, otherParty, responsibleUser,
				groupType, productType, contractTag, requestApprover, isNonStandard);

		RequestDetailsPage detailsPage = new RequestDetailsPage();
		detailsPage.openTemplatePopupAndLoad();

		SoftAssert softAssert = new SoftAssert();

		// Validation from summary page vs expected Excel values
		softAssert.assertEquals(detailsPage.getSelfPartyName(), selfParty, "Self Party Name mismatch");
		softAssert.assertEquals(detailsPage.getOtherPartyName(), otherParty, "Other Party Name mismatch");
		softAssert.assertTrue(detailsPage.getSelfPartyAddress().length() > 0, "Self Party Address should not be empty");
		softAssert.assertEquals(detailsPage.getGroupField(), groupType, "Group Field mismatch");

		softAssert.assertAll();

		// Final save + confirmation
		detailsPage.clickSave();
		String actualMessage = detailsPage.getModalMessageText();
		Assert.assertEquals(actualMessage, "Questionnaire saved successfully.", "Questionnaire not saved correctly");
	}

	@Test(dataProvider = "templateData", dataProviderClass = utils.ExcelDataProvider.class, description = "Verify that the request summary is correctly displayed on the Request Details page.", groups = "smoke")
	public void testrequestDetailsSummary(String username, String password, String contractType, String templateType,
			String selfParty, String otherParty, String responsibleUser, String groupType, String productType,
			String contractTag, String requestApprover, Boolean isNonStandard) {

		// Reuse login + create request flow
		loginAndCreateRequest(username, password, contractType, templateType, selfParty, otherParty, responsibleUser,
				groupType, productType, contractTag, requestApprover, isNonStandard);

		RequestDetailsPage detailsPage = new RequestDetailsPage();

		SoftAssert softAssert = new SoftAssert();

		String expectedTitle = contractType + " - " + otherParty + " (Distributor)";
		String expectedCounterParty = "[" + otherParty + "]";

		softAssert.assertEquals(detailsPage.contractTitle(), expectedTitle, "Mismatch: Contract Title");
		System.out.println("✅ contractTitle matched" + " " + detailsPage.contractTitle());

		softAssert.assertEquals(detailsPage.contractType(), contractType, "Mismatch: Contract Type");
		System.out.println("✅ contractType matched" + " " + detailsPage.contractType());

		softAssert.assertEquals(detailsPage.selfParty(), selfParty, "Mismatch: Self Party");
		System.out.println("✅ selfParty matched" + " " + detailsPage.selfParty());

		softAssert.assertEquals(detailsPage.counterParty(), expectedCounterParty, "Mismatch: Counter Party");
		System.out.println("✅ counterParty matched" + " " + detailsPage.counterParty());

		softAssert.assertEquals(detailsPage.productType(), productType, "Mismatch: Product Type");
		System.out.println("✅ productType matched" + " " + detailsPage.productType());

		softAssert.assertEquals(detailsPage.groupType(), groupType, "Mismatch: Group Type");
		System.out.println("✅ groupType matched" + " " + detailsPage.groupType());

		softAssert.assertEquals(detailsPage.responsibleMember(), responsibleUser, "Mismatch: Responsible Member");
		System.out.println("✅ responsibleMember matched" + " " + detailsPage.responsibleMember());

		softAssert.assertAll();
	}

	@Test(dataProvider = "templateData", dataProviderClass = ExcelDataProvider.class, description = "Verify that the document version is deleted successfully.", groups = "smoke")
	public void deleteVersion(String username, String password, String contractType, String templateType,
			String selfParty, String otherParty, String responsibleUser, String groupType, String productType,
			String contractTag, String requestApprover, Boolean isNonStandard) throws InterruptedException {

		// Precondition: create request with test data
		loginAndCreateRequest(username, password, contractType, templateType, selfParty, otherParty, responsibleUser,
				groupType, productType, contractTag, requestApprover, isNonStandard);

		RequestDetailsPage detailsPage = new RequestDetailsPage();
		detailsPage.openTemplatePopupAndLoad();
		detailsPage.clickSave();
		detailsPage.clickOk();
		detailsPage.approveRequest();
		detailsPage.deleteVersionandConfirm();
		String actualMessage = detailsPage.deletemessagePopup();
		System.out.println(actualMessage);
		String expectedMessage = "Version is Deleted Successfully.";

		Assert.assertEquals(actualMessage, expectedMessage, "Popup message didn't match!");
		detailsPage.deleteVersionOk();
	}

	@Test(dataProvider = "templateData", dataProviderClass = ExcelDataProvider.class, description = "Verify that the document version can be locked and unlocked successfully.", groups = "smoke")
	public void testLockUnlockVersion(String username, String password, String contractType, String templateType,
			String selfParty, String otherParty, String responsibleUser, String groupType, String productType,
			String contractTag, String requestApprover, Boolean isNonStandard) throws InterruptedException {

		// Precondition: create request with test data
		loginAndCreateRequest(username, password, contractType, templateType, selfParty, otherParty, responsibleUser,
				groupType, productType, contractTag, requestApprover, isNonStandard);

		RequestDetailsPage detailsPage = new RequestDetailsPage();
		SoftAssert softAssert = new SoftAssert();
		detailsPage.openTemplatePopupAndLoad();
		detailsPage.clickSave();
		detailsPage.clickOk();
		detailsPage.approveRequest();
		detailsPage.lockVersionandConfirm();
		String actualMessage = detailsPage.lockversionmessagePopup();
		String expectedMessage = "Document Locked successfully.";
		detailsPage.lockVersionOk();
		softAssert.assertEquals(actualMessage, expectedMessage, "Popup message didn't match!");
		System.out.println("Version locked successfully....");
		detailsPage.unlockVersionandConfirm();
		String actualMessage1 = detailsPage.lockversionmessagePopup();
		String expectedMessage1 = "Document Unlocked successfully.";
		Assert.assertEquals(actualMessage1, expectedMessage1, "Popup message didn't match!");
		detailsPage.unlockVersionOk();
		System.out.println("Version unlocked successfully....");
	}

	@Test(dataProvider = "templateData", dataProviderClass = ExcelDataProvider.class, description = "Verify that the request can be closed and displays the correct status and date.", groups = "regression")
	public void testcloseRequest(String username, String password, String contractType, String templateType,
			String selfParty, String otherParty, String responsibleUser, String groupType, String productType,
			String contractTag, String requestApprover, Boolean isNonStandard) throws InterruptedException {

		// Precondition: create request with test data
		loginAndCreateRequest(username, password, contractType, templateType, selfParty, otherParty, responsibleUser,
				groupType, productType, contractTag, requestApprover, isNonStandard);

		RequestDetailsPage detailsPage = new RequestDetailsPage();
		SoftAssert softAssert = new SoftAssert();
		detailsPage.openTemplatePopupAndLoad();
		detailsPage.clickSave();
		detailsPage.clickOk();
		detailsPage.approveRequest();
		detailsPage.closeRequest("test");
		String actualStatusField = detailsPage.ClosedStatus();
		String expectedStatusField = "test";
		Assert.assertEquals(actualStatusField, expectedStatusField, "Mismatch: closed request status");
		System.out.println("✅ Closed status matched: " + " " + detailsPage.ClosedStatus());
		String actualStatusField1 = detailsPage.ClosedDate(); // e.g., "11 Sept 2025"

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
		LocalDate actualDate = LocalDate.parse(actualStatusField1, formatter);

		LocalDate expectedDate = LocalDate.now();

		Assert.assertEquals(actualDate, expectedDate, "❌ Mismatch: Closed request date");
		System.out.println("✅ Closed request date matched: " + actualDate);
		softAssert.assertAll();
	}

	@Test(dataProvider = "templateData", dataProviderClass = ExcelDataProvider.class, description = "Verify that the request can be reopend.", groups = "regression")
	public void testreopenRequest(String username, String password, String contractType, String templateType,
			String selfParty, String otherParty, String responsibleUser, String groupType, String productType,
			String contractTag, String requestApprover, Boolean isNonStandard) throws InterruptedException {

		// Precondition: create request with test data
		loginAndCreateRequest(username, password, contractType, templateType, selfParty, otherParty, responsibleUser,
				groupType, productType, contractTag, requestApprover, isNonStandard);

		RequestDetailsPage detailsPage = new RequestDetailsPage();
		detailsPage.openTemplatePopupAndLoad();
		detailsPage.clickSave();
		detailsPage.clickOk();
		detailsPage.approveRequest();
		detailsPage.closeRequest("test");
		detailsPage.reopenRequest();
		String actaulRequestReopenMessage = detailsPage.reopenRequestMessage();
		String expectedRequestReopenMessage = "Request is Reopened Successfully.";
		Assert.assertEquals(actaulRequestReopenMessage, expectedRequestReopenMessage,
				"Mismatch: request not reopened successfully");
		detailsPage.clickOk();
	}

	@Test(dataProvider = "templateData", dataProviderClass = ExcelDataProvider.class, description = "Verify that the request approval process works and shows the correct confirmation.", groups = "smoke")
	public void testapproveRequest(String username, String password, String contractType, String templateType,
			String selfParty, String otherParty, String responsibleUser, String groupType, String productType,
			String contractTag, String requestApprover, Boolean isNonStandard) throws InterruptedException {

		// Precondition: create request with test data
		loginAndCreateRequest(username, password, contractType, templateType, selfParty, otherParty, responsibleUser,
				groupType, productType, contractTag, requestApprover, isNonStandard);

		RequestDetailsPage detailsPage = new RequestDetailsPage();
		detailsPage.openTemplatePopupAndLoad();
		detailsPage.clickSave();

		detailsPage.clickOk();

		detailsPage.approveRequest();
		String confirmationMessage = detailsPage.getrequestApprovedMessage();
		System.out.println(detailsPage.getrequestApprovedMessage());
		Assert.assertTrue(confirmationMessage.contains("Approved"), "Remark field should indicate approval.");

		System.out.println("Approval Confirmation: " + confirmationMessage);
	}

	@Test(dataProvider = "templateData", dataProviderClass = ExcelDataProvider.class, description = "Verify that the sign recall functionality works after sending the document for e-signature.", groups = "regression")
	public void testsignRecallTest(String username, String password, String contractType, String templateType,
			String selfParty, String otherParty, String responsibleUser, String groupType, String productType,
			String contractTag, String requestApprover, Boolean isNonStandard) throws InterruptedException {

		// Precondition: create request with test data
		loginAndCreateRequest(username, password, contractType, templateType, selfParty, otherParty, responsibleUser,
				groupType, productType, contractTag, requestApprover, isNonStandard);

		RequestDetailsPage detailsPage = new RequestDetailsPage();
		detailsPage.openTemplatePopupAndLoad();
		detailsPage.clickSave();
		detailsPage.clickOk();
		detailsPage.approveRequest();
		detailsPage.lockVersionandConfirm();
		detailsPage.lockVersionOk();
		detailsPage.ClickEsign();

		WebDriver driver = getDriver();
		String originalHandle = driver.getWindowHandle();

		// Switch to new tab and wait for element
		EsigningPage eSignPage = new EsigningPage();
		By locator = eSignPage.getElementAfterRefreshLocator();
		TabSwitchUtility.switchToNewTabAndWaitForElement(driver, locator, true);

		// Fill and submit eSign
		eSignPage.enterSignatoryDetails("Gaurav Thakar", "gthakar@practiceleague.com");
		eSignPage.clickSendForSignature();

		// After clicking OK, tab closes and app returns to original tab
		TabSwitchUtility.switchBackToOriginalTab(driver, originalHandle);

		// Continue in original tab
		RequestDetailsPage detailsPage1 = new RequestDetailsPage();
		detailsPage1.gotoSignDetails();
		detailsPage1.recallEsign();
		String actaulrecallMessage = detailsPage1.getrecallModalMessage();
		System.out.println(actaulrecallMessage);
		Assert.assertEquals(actaulrecallMessage, "Sign recalled successfully.");
	}

	@Test(dataProvider = "conditionalApproval", dataProviderClass = ExcelDataProvider.class, description = "Verify that conditional approval functions correctly.", groups = "regression")
	public void testconditionalApproval(String username, String password, String contractType, String templateType,
			String selfParty, String otherParty, String responsibleUser, String groupType, String productType,
			String contractTag, String requestApprover, Boolean isNonStandard) throws InterruptedException {

		loginAndCreateRequest(username, password, contractType, templateType, selfParty, otherParty, responsibleUser,
				groupType, productType, contractTag, requestApprover, isNonStandard);

		RequestDetailsPage detailsPage = new RequestDetailsPage();
		detailsPage.openTemplatePopupAndLoad();
		detailsPage.clickSave();
		detailsPage.clickOk();
		detailsPage.approveRequest();
		detailsPage.conditionalApprovalRemarkAdd();
		detailsPage.lockVersionandConfirm();
		System.out.println(detailsPage.isApprovedTextPresent());
		Assert.assertTrue(detailsPage.isApprovedTextPresent(), "Conditional approval not completed....");
	}

	@Test(dataProvider = "templateData", dataProviderClass = ExcelDataProvider.class, description = "Verify that a stage can be added to the request successfully.", groups = "smoke")
	public void teststageAdd(String username, String password, String contractType, String templateType,
			String selfParty, String otherParty, String responsibleUser, String groupType, String productType,
			String contractTag, String requestApprover, Boolean isNonStandard) throws InterruptedException {

		// Login and create the request
		loginAndCreateRequest(username, password, contractType, templateType, selfParty, otherParty, responsibleUser,
				groupType, productType, contractTag, requestApprover, isNonStandard);

		// Navigate to the Request Details Page
		RequestDetailsPage detailsPage = new RequestDetailsPage();
		detailsPage.openTemplatePopupAndLoad();
		detailsPage.clickSave();
		detailsPage.clickOk();

		// Approve the request
		detailsPage.approveRequest();

		// Perform the stage update
		detailsPage.performStageADD("Automation Drafting stage");
		Assert.assertTrue(detailsPage.getStageUpdateDescription(), "stage not added....");
	}

	@Test(dataProvider = "templateData", dataProviderClass = ExcelDataProvider.class, description = "Verify that an added stage can be updated successfully.", groups = "smoke")
	public void teststageUpdate(String username, String password, String contractType, String templateType,
			String selfParty, String otherParty, String responsibleUser, String groupType, String productType,
			String contractTag, String requestApprover, Boolean isNonStandard) throws InterruptedException {

		// Login and create the request
		loginAndCreateRequest(username, password, contractType, templateType, selfParty, otherParty, responsibleUser,
				groupType, productType, contractTag, requestApprover, isNonStandard);

		// Navigate to the Request Details Page
		RequestDetailsPage detailsPage = new RequestDetailsPage();
		detailsPage.openTemplatePopupAndLoad();
		detailsPage.clickSave();
		detailsPage.clickOk();

		// Approve the request
		detailsPage.approveRequest();

		// Perform the stage update
		detailsPage.performStageADD("Automation Drafting stage");
		detailsPage.editStageUpdate();
		Assert.assertTrue(detailsPage.isStageEdited(), "stage not updated....");
		detailsPage.stageEditConfirmation();
	}

	@Test(dataProvider = "templateData", dataProviderClass = ExcelDataProvider.class, description = "Verify that an added stage can be deleted successfully.", groups = "smoke")
	public void teststageDelete(String username, String password, String contractType, String templateType,
			String selfParty, String otherParty, String responsibleUser, String groupType, String productType,
			String contractTag, String requestApprover, Boolean isNonStandard) throws InterruptedException {

		// Login and create the request
		loginAndCreateRequest(username, password, contractType, templateType, selfParty, otherParty, responsibleUser,
				groupType, productType, contractTag, requestApprover, isNonStandard);

		// Navigate to the Request Details Page
		RequestDetailsPage detailsPage = new RequestDetailsPage();
		detailsPage.openTemplatePopupAndLoad();
		detailsPage.clickSave();
		detailsPage.clickOk();

		// Approve the request
		detailsPage.approveRequest();
		detailsPage.performStageADD("Automation Drafting stage");
		detailsPage.editStageUpdate();
		detailsPage.stageEditConfirmation();
		detailsPage.deleteStageUpdate();
		Assert.assertTrue(detailsPage.isStagedeleted(), "stage not deleted....");
		detailsPage.stagedeleteConfirmation();
	}

	@Test(dataProvider = "templateData", dataProviderClass = ExcelDataProvider.class, description = "Verify that a general document can be uploaded successfully after approval.", groups = "smoke")
	public void testgeneralDocument(String username, String password, String contractType, String templateType,
			String selfParty, String otherParty, String responsibleUser, String groupType, String productType,
			String contractTag, String requestApprover, Boolean isNonStandard) throws InterruptedException {

		// Login and create the request
		loginAndCreateRequest(username, password, contractType, templateType, selfParty, otherParty, responsibleUser,
				groupType, productType, contractTag, requestApprover, isNonStandard);

		// Navigate to the Request Details Page
		RequestDetailsPage detailsPage = new RequestDetailsPage();
		detailsPage.openTemplatePopupAndLoad();
		detailsPage.clickSave();
		detailsPage.clickOk();

		// Approve the request
		detailsPage.approveRequest();

		// Upload general document
		detailsPage.generalDocument("Case Studies");
		Assert.assertTrue(detailsPage.isDocumentAttached(), "Upload confirmation message mismatch");
		detailsPage.documentUploadConfirmation();
	}

	@Test(dataProvider = "templateData", dataProviderClass = ExcelDataProvider.class, description = "Verify that a supporting document can be uploaded successfully after approval.", groups = "smoke")
	public void testAddSupportingDocument(String username, String password, String contractType, String templateType,
			String selfParty, String otherParty, String responsibleUser, String groupType, String productType,
			String contractTag, String requestApprover, Boolean isNonStandard) throws InterruptedException {

		// Login and create the request
		loginAndCreateRequest(username, password, contractType, templateType, selfParty, otherParty, responsibleUser,
				groupType, productType, contractTag, requestApprover, isNonStandard);

		// Navigate to the Request Details Page
		RequestDetailsPage detailsPage = new RequestDetailsPage();
		detailsPage.openTemplatePopupAndLoad();
		detailsPage.clickSave();
		detailsPage.clickOk();

		// Approve the request
		detailsPage.approveRequest();

		// Upload supporting document
		detailsPage.supportingDocumentUpload();
		Assert.assertTrue(detailsPage.issupportingDocumentAttached(), "Upload confirmation message mismatch");
		detailsPage.supportingdocumentUploadConfirmation();

	}
	@Test(dataProvider = "templateData", dataProviderClass = ExcelDataProvider.class, description = "Verify that user is abele to view supporting document", groups = "regression")
	public void testViewSupportingDocument(String username, String password, String contractType, String templateType,
			String selfParty, String otherParty, String responsibleUser, String groupType, String productType,
			String contractTag, String requestApprover, Boolean isNonStandard) throws InterruptedException {

		// Login and create the request
		loginAndCreateRequest(username, password, contractType, templateType, selfParty, otherParty, responsibleUser,
				groupType, productType, contractTag, requestApprover, isNonStandard);

		// Navigate to the Request Details Page
		RequestDetailsPage detailsPage = new RequestDetailsPage();
		detailsPage.openTemplatePopupAndLoad();
		detailsPage.clickSave();
		detailsPage.clickOk();

		// Approve the request
		detailsPage.approveRequest();

		// Upload supporting document
		detailsPage.supportingDocumentUpload();
		detailsPage.supportingdocumentUploadConfirmation();
		detailsPage.viewSupportingDocument();

		Assert.assertTrue(detailsPage.isDocumentPreviewOpened(), "Document View mode not opened");
		
	}
	@Test(dataProvider = "templateData", dataProviderClass = ExcelDataProvider.class, description = "Verify that a supporting document can be uploaded successfully after approval.", groups = "smoke")
	public void testDeleteSupportingDocument(String username, String password, String contractType, String templateType,
			String selfParty, String otherParty, String responsibleUser, String groupType, String productType,
			String contractTag, String requestApprover, Boolean isNonStandard) throws InterruptedException {

		// Login and create the request
		loginAndCreateRequest(username, password, contractType, templateType, selfParty, otherParty, responsibleUser,
				groupType, productType, contractTag, requestApprover, isNonStandard);

		// Navigate to the Request Details Page
		RequestDetailsPage detailsPage = new RequestDetailsPage();
		detailsPage.openTemplatePopupAndLoad();
		detailsPage.clickSave();
		detailsPage.clickOk();

		// Approve the request
		detailsPage.approveRequest();
		// Upload supporting document
		detailsPage.supportingDocumentUpload();
		detailsPage.supportingdocumentUploadConfirmation();
		detailsPage.deleteSupportingDocument();
		Assert.assertTrue(detailsPage.isSupportingDocumentDeleted(), "Supporting Document not deleted successfully..");
		detailsPage.supportingDocumentdeleteConfirmation();
	}
}
