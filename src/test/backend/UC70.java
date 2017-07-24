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
import pageobject.backend.ChannelProductPage;
import pageobject.backend.LoginPage;
import test.BaseTest;
import util.Log;
import util.Random;

public class UC70 extends BaseTest {
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
	public void UC70_ChannelProduct() {
		ChannelProductPage page = new ChannelProductPage(driver);
		page.goToPage();

		// Add
		String prodName = Random.randomString(5);
		page.addProduct("DE", Random.randomNumber(3), prodName);
		Assert.assertEquals(page.isProductExisting(prodName), true, "Expect product is added but it's not! ");

		// Edit
		String editedProdName = prodName + " - edited";
		page.editProduct(prodName, editedProdName, "");
		Assert.assertEquals(page.isProductExisting(editedProdName), true, "Expect product is edited but it's not! ");

		// Delete
		page.deleteProduct(editedProdName);
		Assert.assertEquals(page.isProductNotExisting(editedProdName), true,
				"Expect product is deleted but it's not! ");
	}
}
