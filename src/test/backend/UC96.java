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
import pageobject.backend.MasterDataPage;
import test.BaseTest;
import util.Log;
import util.Random;

public class UC96 extends BaseTest {
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
	public void UC96_BE_Master_Data() {
		MasterDataPage page = new MasterDataPage(driver);
		page.goToPage();

		// Add
		String country = "country" + Random.currentDateTimeAsString();
		page.doAddCountry(country, Random.randomString(3), Random.randomNumber(2));
		Assert.assertEquals(page.isCountryExisting(country), true, "Expect country is added but it's not. ");

		// Edit
		String editedCountry = country + "2";
		page.doUpdateCountry(country, editedCountry, Random.randomString(3), Random.randomNumber(2));
		Assert.assertEquals(page.isCountryExisting(editedCountry), true, "Expect country is edited but it's not. ");

		// Delete

		page.doDeleteCountry(editedCountry);
		Assert.assertEquals(page.isCountryDeleted(editedCountry), true, "Expect country is deleted but it's not. ");

		// Add
		String cat = "Cat " + Random.currentDateTimeAsString();
		page.doAddCategory(cat);
		Assert.assertEquals(page.isCategoryExisting(cat), true, "Expect category is added but it's not. ");

		// Edit
		String editedCat = cat + "2";
		page.doEditCategory(cat, editedCat);
		Assert.assertEquals(page.isCategoryExisting(editedCat), true, "Expect category is edited but it's not. ");

		// Delete

		page.doDeleteCategory(editedCat);
		Assert.assertEquals(page.isCategoryNotExisting(editedCat), true, "Expect category is deleted but it's not. ");

	}
}
