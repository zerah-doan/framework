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
import pageobject.backend.VoucherPage;
import test.BaseTest;
import util.Log;
import util.Random;

public class UC97 extends BaseTest {

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
	public void UC97_BE_Voucher() {
		VoucherPage page = new VoucherPage(driver);
		page.goToPage();

		String code = Random.randomString(3) + Random.randomNumber(3);
		page.addNewVoucher(code, Random.randomNumber(2), Random.randomNumber(2), Random.randomNumber(2),
				"2017-05-29 11:30:00");
		Assert.assertEquals(page.isVoucherExisting(code), true, "Expect voucher is added but it's not! ");

		page.changeStatus(code, false);
		Assert.assertEquals(page.getVoucherStatus(code), false, "Expect voucher status is inactive but it's not! ");
	}
}
