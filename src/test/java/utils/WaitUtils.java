package utils;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {

	// Wait for a single element
	public static void waitForVisibility(WebDriver driver, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.EXPLICIT_WAIT));
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	// Wait for multiple elements
	public static void waitForAllElements(WebDriver driver, List<By> locators) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.EXPLICIT_WAIT));
		for (By locator : locators) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		}
	}

	// Fluent wait for a single element
	public static void fluentWaitForVisibility(WebDriver driver, By locator) {
		FluentWait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(Constants.EXPLICIT_WAIT))
				.pollingEvery(Duration.ofSeconds(Constants.POLLING_INTERVAL)).ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public static void waitForElementToBeClickableAndUnblocked(WebDriver driver, By elementLocator,
			By blockingOverlayLocator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		// Wait for the blocking overlay to disappear
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(blockingOverlayLocator));
		} catch (TimeoutException e) {
			System.out.println("⚠️ Overlay did not disappear in time. Proceeding anyway.");
		}

		// Wait for the element to become clickable
		wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
	}
	public static void waitForVisibilityAndClickable(WebDriver driver, By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        
        try {
            // Wait for the element to be visible and interactable (clickable)
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            
            // Check if the element is covered by another element (e.g., modal or overlay)
            WebElement element = driver.findElement(locator);
            if (element.getCssValue("display").equals("none") || element.getCssValue("visibility").equals("hidden")) {
                throw new NoSuchElementException("Element is not visible due to CSS properties.");
            }

            // Optionally, wait for any overlay to disappear (if there's a known overlay element)
            By overlayLocator = By.cssSelector(".overlay-class");  // Replace with actual overlay selector
            try {
                wait.until(ExpectedConditions.invisibilityOfElementLocated(overlayLocator));
            } catch (TimeoutException e) {
                System.out.println("⚠️ Overlay did not disappear in time. Proceeding anyway.");
            }

        } catch (TimeoutException e) {
            throw new TimeoutException("Timed out waiting for element to be visible and clickable after " + timeoutInSeconds + " seconds.");
        }
    }


}
