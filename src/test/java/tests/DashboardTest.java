package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import base.BaseTest;
import pages.DashboardPage;
import pages.LoginPageDemo;
import utils.ExcelDataProvider;

  public class DashboardTest extends BaseTest {
  
	  @Test(description = "Verify that clicking the 'Create → Request' option on the dashboard navigates the user to the request creation form.", 
	          groups = "smoke", 
	          dataProvider = "loginData", 
	          dataProviderClass = ExcelDataProvider.class)
	    public void testCreateRequestNavigationFromDashboard(String username, String password) {

	        LoginPageDemo loginPage = new LoginPageDemo();
	        loginPage.login(username, password);
	        loginPage.FeedbackClose();

	        DashboardPage dashboardPage = new DashboardPage();
	        dashboardPage.clickCreateRequest();

	        Assert.assertTrue(getDriver().getPageSource().contains("Create Request"), "Request creation page was not displayed after clicking 'Create → Request'");
	    }
	  @Test(description = "Failed test case for testing", 
	          groups = "smoke", 
	          dataProvider = "loginData", 
	          dataProviderClass = ExcelDataProvider.class)
	    public void testforFailedTestcase(String username, String password) {

	        LoginPageDemo loginPage = new LoginPageDemo();
	        loginPage.login(username, password);
	        loginPage.FeedbackClose();

	        DashboardPage dashboardPage = new DashboardPage();
	        dashboardPage.clickCreateRequest();

	        Assert.assertTrue(getDriver().getPageSource().contains("Create Request1"), "Request creation page was not displayed after clicking 'Create → Request'");
	    }
  }
 
