package utils;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TabSwitchUtility {

	/**
	 * Switches to the newly opened tab and waits for a specific element to be
	 * visible. Optionally refreshes the new tab before waiting.
	 *
	 * @param driver             The WebDriver instance
	 * @param elementToWaitFor   The locator of the element to wait for
	 * @param refreshAfterSwitch Whether to refresh the new tab after switching
	 */
	public static void switchToNewTabAndWaitForElement(WebDriver driver, By elementToWaitFor,
			boolean refreshAfterSwitch) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		String originalWindow = driver.getWindowHandle();

		// Wait for new tab to open
		wait.until(d -> d.getWindowHandles().size() > 1);

		// Switch to the new tab
		Set<String> windowHandles = driver.getWindowHandles();
		for (String handle : windowHandles) {
			if (!handle.equals(originalWindow)) {
				driver.switchTo().window(handle);
				break;
			}
		}

		// Optional refresh
		if (refreshAfterSwitch) {
			driver.navigate().refresh();
		}

		// Wait for the element to be visible
		wait.until(ExpectedConditions.visibilityOfElementLocated(elementToWaitFor));
	}

	/**
	 * Switches back to the original tab.
	 *
	 * @param driver   The WebDriver instance
	 * @param locator1 The handle of the original tab
	 */

	public static void switchBackToOriginalTab(WebDriver driver, String originalHandle) {

		if (driver.getWindowHandles().contains(originalHandle)) {
			driver.switchTo().window(originalHandle);
		} else {
			System.out.println("Original tab is already closed or not found.");
		}

	}

}