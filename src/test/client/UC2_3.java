package test.client;

import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import framework.action.WebViewActions;
import framework.config.Config;
import framework.driver.DriverFactory;
import pageobject.client.InstallationPage;
import pageobject.client.InstallationPage.SortType;
import pageobject.client.LoginPage;
import test.BaseTest;
import util.Log;

public class UC2_3 extends BaseTest {
	WebViewActions action;

	@Parameters({ "device" })
	@BeforeMethod
	public void beforeMethod(@Optional(Config.DEVICE) String device, Method rs) {
		Log.startOfTC(rs.getName());
		android = new DriverFactory().getAndroidDriver(device);
		action = new WebViewActions(android);
		action.switchContext("WEBVIEW_ch.soxes.testproair");
		LoginPage login = new LoginPage(android);
		login.login("abc@yopmail.com", "123456");
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult rs) {
		android.quit();
		Log.endOfTC(rs);
	}

	@Test(groups = "", description = "", enabled = true)
	public void UC3_Installation() {
		InstallationPage page = new InstallationPage(android);
		SoftAssert sa = new SoftAssert();

		// Add
		page.addInstallation("a", "Switzerland", "Alle");
		Assert.assertEquals(page.isAddInsModalClosed(), true);
		Assert.assertEquals(page.isInstallationExisted("a"), true);

		page.addInstallation("b", "Switzerland", "Alle");
		Assert.assertEquals(page.isAddInsModalClosed(), true);
		Assert.assertEquals(page.isInstallationExisted("b"), true);

		page.addInstallation("c", "Switzerland", "Alle");
		Assert.assertEquals(page.isAddInsModalClosed(), true);
		Assert.assertEquals(page.isInstallationExisted("c"), true);

		// Sort
		page.sort(SortType.NAME_ASC);
		page.sort(SortType.NAME_DES);

		// Search
		page.searchIns("a");
		Assert.assertEquals(page.isInstallationExisted("a"), true);
		Assert.assertEquals(page.isInstallationDeleted("b"), true);
		Assert.assertEquals(page.isInstallationDeleted("c"), true);
		page.searchIns("");

		// Open
		page.openInstallation("a");
		Assert.assertEquals(action.isDisplayed(By.xpath("//button[.//text()='Create Order']")), true);
		action.click(By.xpath("//button[@ng-click='goBack()']"));

		// Delete
		page.deleteInstallation("a", false);
		Assert.assertEquals(page.isInstallationExisted("a"), true);

		page.deleteInstallation("a", true);
		Assert.assertEquals(page.isInstallationDeleted("a"), true);

		// Remove data
		page.deleteInstallation("b", true);
		page.deleteInstallation("c", true);

		sa.assertAll();
	}

}
