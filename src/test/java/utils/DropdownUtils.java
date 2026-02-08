package utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class DropdownUtils {


    /**
     * Selects an option from a React autosuggest dropdown using suggestion click.
     */
    public static void selectDropdownOption(WebDriver getDriver, By dropdownLocator, String value, By suggestionLocator) {
        WebDriverWait wait = new WebDriverWait(getDriver, Duration.ofSeconds(10));

        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
        ((JavascriptExecutor) getDriver).executeScript("arguments[0].scrollIntoView(true);", dropdown);
        dropdown.click();
        dropdown.sendKeys(value);
       

        wait.until(ExpectedConditions.visibilityOfElementLocated(suggestionLocator));
        WebElement suggestion = getDriver.findElement(suggestionLocator);
        ((JavascriptExecutor) getDriver).executeScript("arguments[0].click();", suggestion);
    }

    /**
     * Selects an option from a React autosuggest dropdown using keyboard navigation.
     * Useful when suggestions are not clickable or rendered in a virtual list.
     */
    public static void selectByTypingAndKeyboard(WebDriver getDriver, By dropdownLocator, String value) {
        WebDriverWait wait = new WebDriverWait(getDriver, Duration.ofSeconds(10));

        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
        ((JavascriptExecutor) getDriver).executeScript("arguments[0].scrollIntoView(true);", dropdown);
        dropdown.click();
        dropdown.sendKeys(value);

        // Optional: wait for value to appear in input
        wait.until(ExpectedConditions.textToBePresentInElementValue(dropdown, value));

        // Use keyboard to select
        dropdown.sendKeys(Keys.ARROW_DOWN);
        dropdown.sendKeys(Keys.ENTER);
    }
    
    
}
