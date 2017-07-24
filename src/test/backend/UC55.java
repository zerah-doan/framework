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
import pageobject.backend.AdminPage;
import pageobject.backend.LoginPage;
import test.BaseTest;
import util.Log;

public class UC55 extends BaseTest {
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
	public void UC55_BE_Admin_User() {
		AdminPage page = new AdminPage(driver);

		// Add
		String email = "email" + util.Random.currentDateTimeAsString() + "@xyz.com";
		page.doAddUser("abc", "xyz", email, "1234567");
		Assert.assertEquals(page.isUserExisting(email), true, "Expect user is added but it's not");

		// Edit
		String editedEmail = email + ".vn";
		page.doUpdateUser(email, "abc1", "xyz1", editedEmail, "123456");
		Assert.assertEquals(page.isUserExisting(editedEmail), true, "Expect user is updated but it's not! ");

		// Delete
		page.deleleUser(editedEmail);
		Assert.assertEquals(page.isUserNotExisting(editedEmail), true, "Expect user is deleted but it's not");
	}
}
