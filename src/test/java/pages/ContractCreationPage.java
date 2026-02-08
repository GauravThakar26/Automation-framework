package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import driver.DriverManager;

import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class ContractCreationPage {

    private By periodRangeInput = By.id("PeriodFromToDate");
    private By executionDateInput = By.xpath("//*[@id='date_ExecutionDate1']/div/input");
    private By saveButton = By.xpath("//input[@value='Create']");
    private By clickOk = By.xpath("//input[@value='Ok']");
    private By confirmTaskCreation = By.xpath("//input[@value='No']");

    private WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    /**
     * Selects the period range in a single input field.
     * Opens calendar, selects fromDate and toDate dynamically.
     */
    public void selectPeriodRange(String fromDate, String toDate) {
        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement periodInput = wait.until(ExpectedConditions.elementToBeClickable(periodRangeInput));
        periodInput.click(); // Open the calendar
        selectDateFromCalendar(fromDate); // Click From date
        selectDateFromCalendar(toDate);   // Click To date
    }

    /**
     * Selects a single date from the calendar popup by:
     * - Navigating to the correct month/year
     * - Clicking the day using aria-label
     */
    private void selectDateFromCalendar(String dateStr) {
        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(dateStr, formatter);

        String day = String.format("%02d", date.getDayOfMonth()); // Leading zero
        String month = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        String year = String.valueOf(date.getYear());

        // Navigate to correct month/year
        navigateToMonthYear(month, year);

        // Build flexible XPath to match aria-label that includes month, day (with leading zero), and year
        By dateLocator = By.xpath("//div[contains(@aria-label, '" + month + "') and contains(@aria-label, '" + day + "') and contains(@aria-label, '" + year + "') and contains(@class,'rmdp-day')]");

        wait.until(ExpectedConditions.elementToBeClickable(dateLocator)).click();
    }


    /**
     * Navigates the calendar to the target month and year using the right arrow.
     */
    private void navigateToMonthYear(String targetMonth, String targetYear) {
        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Parse target date to YearMonth for comparison
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);
        YearMonth target = YearMonth.parse(targetMonth + " " + targetYear, formatter);

        while (true) {
            WebElement monthElem = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='rmdp-header-values']/span[1]")));
            WebElement yearElem = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='rmdp-header-values']/span[2]")));

            String currentMonth = monthElem.getText().trim();
            String currentYear = yearElem.getText().trim();
            YearMonth current = YearMonth.parse(currentMonth + " " + currentYear, formatter);

            if (current.equals(target)) {
                break; // We're on the correct month/year
            }

            // Decide navigation direction
            if (current.isBefore(target)) {
                driver.findElement(By.cssSelector(".rmdp-arrow-container.rmdp-right")).click();
            } else {
                driver.findElement(By.cssSelector(".rmdp-arrow-container.rmdp-left")).click();
            }

            // Wait until header updates (avoid stale or out-of-sync state)
            String finalCurrentMonth = currentMonth;
            String finalCurrentYear = currentYear;
            wait.until(d -> {
                WebElement newMonth = d.findElement(By.xpath("//div[@class='rmdp-header-values']/span[1]"));
                WebElement newYear = d.findElement(By.xpath("//div[@class='rmdp-header-values']/span[2]"));
                return !newMonth.getText().trim().equals(finalCurrentMonth)
                    || !newYear.getText().trim().equals(finalCurrentYear);
            });
        }
    }


    /**
     * Clicks execution date input to auto-set current date.
     */
    public void clickExecutionDate() {
        WebElement execDate = getDriver().findElement(executionDateInput);
        execDate.click();
    }

    /**
     * Saves the contract form and handles confirmation popups.
     */
    public void clickSave() {
        getDriver().findElement(saveButton).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(clickOk));
        getDriver().findElement(clickOk).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmTaskCreation));
        getDriver().findElement(confirmTaskCreation).click();
    }
}