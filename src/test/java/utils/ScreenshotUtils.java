package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ScreenshotUtils {

    public static String captureScreenshot(WebDriver driver, String testName) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = String.valueOf(System.currentTimeMillis());
        String path = System.getProperty("user.dir") + File.separator + "test-output" + File.separator + "screenshots" + File.separator + testName + "_" + timestamp + ".png";

        File dest = new File(path);
        dest.getParentFile().mkdirs(); // Ensure directory exists

        try {
            Files.copy(src.toPath(), dest.toPath());
            System.out.println("Screenshot saved at: " + path);
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
        }

        return path;
    }


}