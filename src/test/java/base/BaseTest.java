package base;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ConfigReader;
import utils.Constants;
import pages.LoginPageDemo;
import pages.DashboardPage;
import pages.RequestCreationPage;
import static org.testng.Assert.assertTrue;
import driver.DriverManager;

@Listeners({ listeners.ExtentTestNGListener.class })
public class BaseTest {
	protected Properties prop;

	@BeforeMethod(alwaysRun = true)
	public void setUp(Method method) {
		try {
			prop = ConfigReader.loadProperties();
			String browser = prop.getProperty("browser");
			WebDriver localDriver;
			if (browser.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				localDriver = new ChromeDriver();
			} else if (browser.equalsIgnoreCase("edge")) {
				WebDriverManager.edgedriver().setup();
				localDriver = new EdgeDriver();
				
			} else {
				throw new RuntimeException("Unsupported browser: " + browser);
			}
			DriverManager.setDriver(localDriver);
			DriverManager.getDriver().manage().window().maximize();
			DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.IMPLICIT_WAIT));
			DriverManager.getDriver().get(prop.getProperty("baseUrl"));
			System.out.println("âœ… BaseTest setup executed");

		} catch (Exception e) {
			System.err.println("â�Œ Driver initialization failed: " + e.getMessage());
			e.printStackTrace();
			DriverManager.quitDriver();
		}
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result) {
		if (DriverManager.getDriver() != null) {
			DriverManager.quitDriver();
		} else {
			System.out.println("âš ï¸� No active driver found to quit.");
		}
	}

	// Updated reusable method: login + create request with data-driven parameters
	public void loginAndCreateRequest(String username, String password, String contractType, String templateType,
			String selfParty, String otherParty, String responsibleUser, String groupType, String productType,
			String contractTag, String requestApprover, boolean isNonStandard) {
		WebDriver driver = DriverManager.getDriver();

		LoginPageDemo loginPage = new LoginPageDemo();
		loginPage.login(username, password);
		if (isFeedbackVisible()) {
			loginPage.FeedbackClose();
		}

		DashboardPage dashboard = new DashboardPage();
		dashboard.clickCreateRequest();

		RequestCreationPage requestPage = new RequestCreationPage();
		requestPage.fillContractRequestForm(contractType, templateType, selfParty, otherParty, responsibleUser,
				groupType, productType, contractTag, requestApprover, isNonStandard);
		requestPage.clickSave();

		assertTrue(driver.getCurrentUrl().contains("RequestDetails"), "URL validation failed");
	}

	private boolean isFeedbackVisible() {
		try {
			// Locator for the feedback modal's close button (FeedbackClose button)
			By feedbackModalLocator = By.xpath("//input[@title='Close Request']");

			return true; // Feedback modal is visible
		} catch (TimeoutException e) {
			// Feedback modal is not visible, return false
			return false;
		}
	}

	public static WebDriver getDriver() {
		return DriverManager.getDriver();
	}

}
