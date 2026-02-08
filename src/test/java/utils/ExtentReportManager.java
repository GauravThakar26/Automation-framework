package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
	private static ExtentReports extent;

	public static ExtentReports getReportInstance() {
		if (extent == null) {
			String timeStamp = new SimpleDateFormat("MMM_dd_yyyy_HH-mm-ss").format(new Date());
			String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport_" + timeStamp + ".html";

			ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);

			// Appearance settings
			reporter.config().setTheme(Theme.STANDARD);
			reporter.config().setReportName("📘 Automation Test Report");
			reporter.config().setDocumentTitle("📄 Razorsign | Test Execution Summary");
			reporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a");
			reporter.config().setTimelineEnabled(true);

			reporter.config().setCss(
				    ".card-toolbar.node-info .badge.badge-default { display: none !important; } " +
				    ".name { " +
				    "  font-weight: bold !important; " +
				    "  font-size: 16px !important; " +
				    "  color: #2c3e50 !important; " +
				    "  padding: 5px 10px !important; " +
				    "  cursor: pointer !important; " +
				    "  transition: background-color 0.3s ease !important; " +
				    "} " +
				    ".name:hover { " +
				    "  background-color: #f0f8ff !important; " +
				    "} " +
				    ".badge.log { " +
				    "  font-size: 90% !important; " +
				    "} " +
				    ".pass-bg { " +
				    "  background-color: #38b31e !important; " +   // nice green
				    "  color: white !important; " +
				    "  font-weight: 600 !important; " +
				    "  font-size: 13px !important; " +
				    "  padding: 5px 10px !important; " +
				    "  border-radius: 12px !important; " +
				    "  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.15) !important; " +
				    "}"
				);

			reporter.config().setJs("document.title = 'Razorsign - Automated Report';");
			extent = new ExtentReports();
			extent.attachReporter(reporter);
			extent.setAnalysisStrategy(AnalysisStrategy.CLASS);

			// System Info
			extent.setSystemInfo("👤 QA Engineer", "Gaurav Thakar");
			extent.setSystemInfo("🌐 Environment", "DEMO");
			extent.setSystemInfo("💻 OS", System.getProperty("os.name"));
			extent.setSystemInfo("🧪 Java Version", System.getProperty("java.version"));
			extent.setSystemInfo("🧭 Browser", "Chrome");
			extent.setSystemInfo("📅 Report Generated On",
					new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date()));
			extent.setSystemInfo("📋 Pass Criteria", "All critical flows must pass");
		}

		return extent;
	}
}





