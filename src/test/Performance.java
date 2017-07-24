package test;

import java.lang.reflect.Method;

import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.driver.DriverFactory;
import io.appium.java_client.android.AndroidDriver;
import pageobject.client.InstallationPage;
import util.Log;
import util.Random;

public class Performance extends BaseTest {
	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult rs) {
		android.quit();
		Log.endOfTC(rs);
	}

	@Test
	public void f(Method rs) {
		Log.startOfTC(rs.getName());
		AndroidDriver<WebElement> android = new DriverFactory().getAndroidDriver("TabS2");
		InstallationPage page = new InstallationPage(android);
		int i = 0;
		while (i < 100) {
			page.addInstallation("Ins" + Random.currentDateTimeAsString(), "Belarus", "Second");

		}
	}
}
