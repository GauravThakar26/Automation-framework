package utils;

import org.testng.annotations.DataProvider;

public class ExcelDataProvider {

	// For most tests - reads from Sheet1

	@DataProvider(name = "dp")
	public static Object[][] getData() {
		return ExcelUtil.readDefaultSheet();
	}

	// For login tests only - reads from LoginData sheet
	@DataProvider(name = "loginData")
	public static Object[][] getLoginData1() {
		Object[][] data = ExcelUtil.readExcelData("src/test/resources/TestData1.xls", "login");
		System.out.println("🧪 DataProvider: Number of rows from Excel = " + data.length);
		return data;
	}

	@DataProvider(name = "templateData") // for template based request creation
	public static Object[][] getTemplateData() {
		return ExcelUtil.readExcelData("src/test/resources/TestData5.xls", "templateData");
	}

	@DataProvider(name = "thirdPartyData") // for third party based request creation
	public static Object[][] getThirdPartyData() {
		return ExcelUtil.readExcelData("src/test/resources/TestData5.xls", "thirdPartyData");
	}

	@DataProvider(name = "autoapprovedContract") // for request creation without approval
	public static Object[][] getautoApprovedTemplateData() {
		return ExcelUtil.readExcelData("src/test/resources/TestData5.xls", "approverName");
	}
	@DataProvider(name = "conditionalApproval") // for request creation without approval
	public static Object[][] getConditionalApprovalData() {
		return ExcelUtil.readExcelData("src/test/resources/TestData5.xls", "multilevelApproval");
	}
}
