package test.backend;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import pageobject.backend.CataloguePage;
import pageobject.backend.LoginPage;
import test.BaseTest;
import util.Log;
import util.Random;

public class UC65 extends BaseTest {
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
	public void UC65_CatalogueUser() {
		CataloguePage page = new CataloguePage(driver);
		page.goToPage();

		// Add
		String company = "Company " + Random.randomNumber(3);
		page.addCatalogue(company, Random.randomString(5) + " St", Random.randomNumber(5),
				Random.randomString(10) + " City", Random.randomNumber(10),
				"email" + Random.randomNumber(5) + "@abc.com");
		Assert.assertEquals(page.isCompanyExisting(company), true, "Expect company is added but it's not");

		// Edit
		String editedCompany = company + " - edited";
		page.editCatalogue(company, editedCompany, Random.randomString(5) + " St", Random.randomNumber(5),
				Random.randomString(10) + " City", Random.randomNumber(10),
				"email" + Random.randomNumber(5) + "@abc.com");
		Assert.assertEquals(page.isCompanyExisting(editedCompany), true, "Expect company is edited but it's not");

		/*List<String> selectedCat = new ArrayList<>(Arrays.asList("Kanal- und Rohrsysteme", "Ventilatoren"));
		page.selectCategory(editedCompany, selectedCat);*/

		// Delete
		page.deleteCatalogue(editedCompany);
		Assert.assertEquals(page.isCompanyNotExisting(editedCompany), true, "Expect company is deleted but it's not");

	}
}
