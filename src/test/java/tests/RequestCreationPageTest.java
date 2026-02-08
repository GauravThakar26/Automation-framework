package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import base.BaseTest;
import utils.ExcelDataProvider;

public class RequestCreationPageTest extends BaseTest {

	@Test(dataProvider = "templateData", dataProviderClass = ExcelDataProvider.class, description = "Verify that a new contract request can be created using a predefined template", groups = "smoke")
	public void testforrequestCreationwithTemplate(String username, String password, String contractType,
			String templateType, String selfParty, String otherParty, String responsibleUser, String groupType,
			String productType, String contractTag, String requestApprover, Boolean isNonStandard) {

		// isNonStandard should be FALSE in this sheet's rows
		loginAndCreateRequest(username, password, contractType, templateType, selfParty, otherParty, responsibleUser,
				groupType, productType, contractTag, requestApprover, isNonStandard);	
		Assert.assertTrue(getDriver().getCurrentUrl().contains("RequestDetails"), "URL validation failed");
	
	}

	@Test(dataProvider = "thirdPartyData", dataProviderClass = ExcelDataProvider.class, description = "Verify that a new contract request can be created by uploading a third-party document", groups = "smoke")
	public void testforrequestCreationwithThirdParty(String username, String password, String contractType,
			String templateType, String selfParty, String otherParty, String responsibleUser, String groupType,
			String productType, String contractTag, String requestApprover, Boolean isNonStandard) {

		// isNonStandard should be TRUE in this sheet's rows
		loginAndCreateRequest(username, password, contractType, templateType, selfParty, otherParty, responsibleUser,
				groupType, productType, contractTag, requestApprover, isNonStandard);
	
		Assert.assertTrue(getDriver().getCurrentUrl().contains("RequestDetails"), "URL validation failed");
		
		
		
	}

}
