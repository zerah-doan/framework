package test.backend;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import framework.action.WebActions;
import framework.config.Config;
import framework.driver.DriverFactory;
import pageobject.backend.LoginPage;
import pageobject.backend.SubscriptionPage;
import test.BaseTest;
import util.Log;
import util.Random;

public class UCxx extends BaseTest {
	WebActions wa;

	@Parameters({ "browser", "appUrl" })
	@BeforeMethod
	public void beforeMethod(@Optional(Config.BROWSER) String browser, @Optional(Config.APP_URL) String appUrl,
			Method rs) {
		Log.startOfTC(rs.getName());
		driver = new DriverFactory().getWebDriver(browser, appUrl);
		wa = new WebActions(driver);
		LoginPage loginPage = new LoginPage(driver);
		loginPage.adminLogin("admin@soxes.ch", "root");
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult rs) {
		driver.quit();
		Log.endOfTC(rs);
	}

	@Test
	public void UCxx_SubscriptionPackage() {
		SubscriptionPage page = new SubscriptionPage(driver);
		page.moveToPage();

		// Add
		String title = "Package " + Random.currentDateTimeAsString();
		page.addPackage(title, "Description of " + title, Random.randomNumber(2));
		Assert.assertEquals(page.isPackageExisting(title), true, "Expect package is added but it's not! ");

		// Edit
		String editedTitle = title + " edit";
		page.editPackage(title, editedTitle, "Descrtion of " + editedTitle, Random.randomNumber(2));
		Assert.assertEquals(page.isPackageExisting(editedTitle), true, "Expect package is edited but it's not! ");

		// Add price to package
		String cur = Random.randomString(3);
		page.addPrice(editedTitle, cur, Random.randomNumber(2));
		Assert.assertEquals(page.isPriceExisting(editedTitle, cur), true, "Expect price is added but it's not! ");

		// Edit price
		String editedCur = Random.randomString(3);
		page.editPrice(editedTitle, cur, editedCur, Random.randomNumber(2));
		Assert.assertEquals(page.isPriceExisting(editedTitle, editedCur), true,
				"Expect price is edited but it's not! ");

		// Delete price
		page.deletePrice(editedTitle, editedCur);
		Assert.assertEquals(page.isPriceNotExisting(editedTitle, editedCur), true,
				"Expect price is delete but it's not! ");
		// Delete
		page.deletePackage(editedTitle);
		Assert.assertEquals(page.isPackageNotExisting(editedTitle), true, "Expect package is deleted but it's not! ");
	}
}
