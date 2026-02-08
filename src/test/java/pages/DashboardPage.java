package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import utils.WaitUtils;
import driver.DriverManager;

public class DashboardPage {

	// Locators for dashboard page
	By createButton = By.xpath("//a[contains(text(),'Create')]"); // Create button
	By requestOption = By.xpath("//a[contains(text(),'Request')]"); // create request option from dropdown

	private WebDriver getDriver() {
		return DriverManager.getDriver();
	}

	// Action: Hover over "Create" and click "Request"
	public void clickCreateRequest() {
		Actions actions = new Actions(getDriver());
		WebElement createBtn = getDriver().findElement(createButton);
		actions.moveToElement(createBtn).perform();
		WaitUtils.waitForVisibility(getDriver(), requestOption);
		getDriver().findElement(requestOption).click();
	}
}
