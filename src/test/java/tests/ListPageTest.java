package tests;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.ListPage;
import pages.LoginPageDemo;
import utils.ExcelDataProvider;

public class ListPageTest extends BaseTest {
	@Test(dataProvider = "loginData", dataProviderClass = ExcelDataProvider.class, description = "Verify that records on the list page are displayed in descending order based on the 'Added On' field.", groups = "smoke")

	public void testDescendingOrderOnListPage(String username, String password) throws InterruptedException {

		LoginPageDemo loginpage = new LoginPageDemo();
		loginpage.login(username, password);
		loginpage.FeedbackClose();

		ListPage listPage = new ListPage();
		List<String> addedOnTexts = listPage.getAddedOnTexts();
		System.out.println("Is sorted: " + listPage.isRecordsSortedDescending());
		System.out.println("Days list: " + listPage.getDaysAgoList());
		System.out.println("Actual 'Added On' values: " + addedOnTexts);
		assertTrue(listPage.isRecordsSortedDescending(), "Records are not sorted in descending order by the 'Added On' date.");

	}

	@Test(dataProvider = "thirdPartyData", dataProviderClass = ExcelDataProvider.class, description = "Verify that the correct approver name is displayed on the list page.", groups = "smoke")
	public void testApproverNameDisplayedCorrectly(String username, String password, String contractType, String templateType,
			String selfParty, String otherParty, String responsibleUser, String groupType, String productType,
			String contractTag, String requestApprover, Boolean isNonStandard) throws InterruptedException {

		loginAndCreateRequest(username, password, contractType, templateType, selfParty, otherParty, responsibleUser,
				groupType, productType, contractTag, requestApprover, isNonStandard);
		ListPage listPage1 = new ListPage();
		listPage1.searchReqID();
		listPage1.getRequestApproverName();
		System.out.println("expected approver name is :" + requestApprover);
		System.out.println("Actual approver name is:" + listPage1.getRequestApproverName());
		Assert.assertEquals(listPage1.getRequestApproverName(), requestApprover, "Mismatch: Approver name does not match.");
		System.out.println("✅ Approver name matched");
	}

}
