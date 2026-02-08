package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import driver.DriverManager;
import utils.WaitUtils;
import java.util.ArrayList;
import java.util.List;

public class ListPage {

	// No constructor needed, as driver will be fetched from DriverManager
	private WebDriver getDriver() {
		return DriverManager.getDriver();
	}

	// Locator for the "Added On" column cell
	private By addedOnLocator = By.xpath(
			"//div[@class='collgGrid']//p[contains(@class,'subTxtGrid')][contains(text(),'ago') or contains(text(),'hour') or contains(text(),'minute') or contains(text(),'just now') or contains(text(),'today')]");
	private By pendingColumn = By.cssSelector(".pendingApprovalFilterIcon");
	private By requestApprover = By.cssSelector("div:nth-child(3) div:nth-child(6) div:nth-child(1) p:nth-child(1)");

	private By homePageLink = By.xpath("//a[@class='leftHome selected']");
	private By searchID = By.xpath("//input[@placeholder='Search']");

	// Fetches all "Added On" cell texts from the list page

	public List<String> getAddedOnTexts() {
		List<WebElement> elements = getDriver().findElements(addedOnLocator);
		List<String> texts = new ArrayList<>();
		for (WebElement el : elements) {
			texts.add(el.getText().toLowerCase().trim());

		}
		return texts;

	}

	// Parses the "Added On" texts to a list of daysAgo integers
	public List<Integer> getDaysAgoList() {
		List<String> addedOnTexts = getAddedOnTexts();
		List<Integer> daysAgoList = new ArrayList<>();

		for (String text : addedOnTexts) {
			int daysAgo = 0;
			if (text.contains("day")) {
				try {
					daysAgo = Integer.parseInt(text.split(" ")[0]);
				} catch (NumberFormatException e) {
					daysAgo = Integer.MAX_VALUE; // fallback for invalid format
				}
			} else if (text.contains("hour") || text.contains("minute") || text.contains("just now")
					|| text.contains("today")) {
				daysAgo = 0; // treat as most recent
			} else {
				daysAgo = Integer.MAX_VALUE; // unknown format
			}

			daysAgoList.add(daysAgo);

		}

		return daysAgoList;

	}

	// Checks if the daysAgoList is sorted in ascending order (which means records
	// are descending by date)
	public boolean isRecordsSortedDescending() {
		List<Integer> daysAgoList = getDaysAgoList();

		for (int i = 0; i < daysAgoList.size() - 1; i++) {
			if (daysAgoList.get(i) > daysAgoList.get(i + 1)) {
				return false;
			}
		}
		return true;
	}

	public void searchReqID() {
		RequestDetailsPage page = new RequestDetailsPage();
		/* WaitUtils.fluentWaitForVisibility(getDriver(), addedOnLocator); */

		String requestIDvalue = page.getRequestID();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getDriver().findElement(homePageLink).click();

		WebElement el = getDriver().findElement(searchID);

		((JavascriptExecutor) getDriver()).executeScript("arguments[0].value =arguments[1];", el, requestIDvalue);
		el.sendKeys(Keys.ENTER);
	}

	public String getRequestApproverName() {
		WaitUtils.fluentWaitForVisibility(getDriver(), requestApprover);
		return getDriver().findElement(requestApprover).getText();
	}
}
