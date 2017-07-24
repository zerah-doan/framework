package test;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;

import framework.config.Config;
import io.appium.java_client.android.AndroidDriver;
import util.Log;

public class BaseTest {
	// driver
	protected WebDriver driver;
	protected static AndroidDriver<WebElement> android;

	// report
	protected ExtentReports extentReport;

	@BeforeSuite
	public void beforeSuite() {
		extentReport = new ExtentReports(
				Config.PROJECT_PATH + File.separator + "test-output" + File.separator + "ProAirReport.html", true);
		Log.setExtentReport(extentReport);
	}

	@AfterSuite
	public void afterSuite() {
		extentReport.flush();
		extentReport.close();
	}
}
