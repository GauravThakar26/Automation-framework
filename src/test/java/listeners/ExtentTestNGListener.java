package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import base.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ExtentReportManager;
import utils.ScreenshotUtils;

import java.util.HashMap;
import java.util.Map;

public class ExtentTestNGListener implements ITestListener {

	private ExtentReports extent = ExtentReportManager.getReportInstance();

	private static ThreadLocal<ExtentTest> classLevelTest = new ThreadLocal<>();
	private static ThreadLocal<ExtentTest> methodLevelTest = new ThreadLocal<>();

	private static Map<String, ExtentTest> classTestMap = new HashMap<>();

	@Override
	public void onTestStart(ITestResult result) {
		String className = result.getTestClass().getName();
		String methodName = result.getMethod().getMethodName();

		// Mask sensitive data
		Object[] originalParams = result.getParameters();
		Object[] maskedParams = new Object[originalParams.length];

		for (int i = 0; i < originalParams.length; i++) {
			String paramStr = String.valueOf(originalParams[i]);
			if (i == 1 || paramStr.toLowerCase().contains("pass")) {
				maskedParams[i] = "*****";
			} else {
				maskedParams[i] = paramStr;
			}
		}

		String maskedParamStr = java.util.Arrays.toString(maskedParams);

		// ✅ Create class-level node
		ExtentTest classTest = classTestMap.computeIfAbsent(className, name -> extent.createTest(name));
		classLevelTest.set(classTest);

		// ✅ Create method-level node WITHOUT parameters
		ExtentTest methodTest = classTest.createNode(methodName);
		methodTest.info("Parameters: " + maskedParamStr); //log parameters
		methodLevelTest.set(methodTest);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ExtentTest test = methodLevelTest.get();
		if (test != null) {
			test.pass("Test Passed");
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		ExtentTest test = methodLevelTest.get();
		if (test != null) {
			test.fail(result.getThrowable());

			Object currentClass = result.getInstance();
			WebDriver driver = null;

			if (currentClass instanceof BaseTest) {
				driver = BaseTest.getDriver();
			}

			if (driver != null) {
				String screenshotPath = ScreenshotUtils.captureScreenshot(driver, result.getMethod().getMethodName());
				test.addScreenCaptureFromPath(screenshotPath);
			} else {
				System.err.println("WebDriver is null — cannot capture screenshot.");
			}
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ExtentTest test = methodLevelTest.get();
		if (test != null) {
			test.skip("Test Skipped");
		}
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}

}
