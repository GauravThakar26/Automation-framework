package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class BrokenLinkChecker {

	public static void main(String[] args) {
		// 1. Set up chromedriver using WebDriverManager
		io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
		// 2. Launch browser
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();

		// 3. Provide direct URL here 👇
		String testUrl = "https:abc.com/";
		driver.get(testUrl);
		int brokenLinkcount = 0;
		
		// 4. Get all <a> tag elements
		List<WebElement> links = driver.findElements(By.tagName("a"));
		System.out.println("Total links found: " + links.size());

		// 5. Check each link's status
		for (WebElement link : links) {
			String url = link.getDomAttribute("href");

			if (url == null || url.isEmpty()) {
				System.out.println("⚠️ Skipping empty or null URL");
				continue;
			}

			try {
				HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
				conn.setConnectTimeout(5000);
				conn.connect();

				int code = conn.getResponseCode();

				if (code >= 400) {
					System.out.println("❌ Broken link: " + url + " | Code: " + code);
					brokenLinkcount++;
				} else {
					System.out.println("✅ Valid link: " + url + " | Code: " + code);
				}

			} catch (Exception e) {
				System.out.println("🚫 Error checking link: " + url + " | " + e.getMessage());
			}
		}
		System.out.println("Total Broken links: " + brokenLinkcount);

		// 6. Close browser
		driver.quit();
	}
}
