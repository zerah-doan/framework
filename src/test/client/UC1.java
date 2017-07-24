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
import pageobject.client.LoginPage;
import pageobject.client.RegisterPage;
import test.BaseTest;
import util.Log;
import util.Random;

public class UC1 extends BaseTest {
	WebViewActions action;

	@Parameters({ "device" })
	@BeforeMethod
	public void beforeMethod(@Optional(Config.DEVICE) String device, Method rs) {
		Log.startOfTC(rs.getName());
		android = new DriverFactory().getAndroidDriver(device);
		action = new WebViewActions(android);
		action.switchContext("WEBVIEW_ch.soxes.testproair");
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult rs) {
		android.quit();
		Log.endOfTC(rs);

	}

	@Test(groups = "", description = "", enabled = false)
	public void UC1_Login() {
		SoftAssert sa = new SoftAssert();
		WebViewActions action = new WebViewActions(android);
		LoginPage page = new LoginPage(android);

		// Empty username & password
		page.doLogin("", "");
		sa.assertEquals(action.getText(By.xpath("//input[@id='login']/../following-sibling::div/div")),
				"Login cannot be blank.");
		sa.assertEquals(action.getText(By.xpath("//input[@id='password']/../following-sibling::div/div")),
				"Password cannot be blank.");

		// Empty password
		page.doLogin("zerah@soxes.ch", "");
		sa.assertEquals(action.getText(By.xpath("//input[@id='password']/../following-sibling::div/div")),
				"Password cannot be blank.");

		// Empty username
		page.doLogin("", "hihi@123456");
		sa.assertEquals(action.getText(By.xpath("//input[@id='login']/../following-sibling::div/div")),
				"Login cannot be blank.");

		// Unregistered user
		page.doLogin("12345@qwert.com", "hihi@123456");
		sa.assertEquals(action.getText(By.xpath("//input[@id='password']/../following-sibling::div/div")),
				"Incorrect username or password.");

		// Incorrect password
		page.doLogin("abc@yopmail.com", "b");
		sa.assertEquals(action.getText(By.xpath("//input[@id='password']/../following-sibling::div/div")),
				"Incorrect username or password.");

		// Valid user
		page.doLogin("abc@yopmail.com", "123456");
		sa.assertEquals(action.isDisplayed(By.xpath("//button[@ng-click='createInstallation()']")), true,
				"Expect login successfully but it's not. ");
		sa.assertAll();
	}

	@Test(groups = "", description = "")
	public void UC1_Register() {
		SoftAssert sa = new SoftAssert();
		WebViewActions action = new WebViewActions(android);
		RegisterPage page = new RegisterPage(android);
		page.doRegister("", "", "");
		sa.assertEquals(action.getText(By.xpath("//input[@id='register-email']/../following-sibling::div/div")),
				"Email cannot be blank.");
		sa.assertEquals(action.getText(By.xpath("//input[@id='register-password']/../following-sibling::div/div")),
				"Password cannot be blank.");
		sa.assertEquals(
				action.getText(By.xpath("//input[@id='register-password-confirm']/../following-sibling::div/div")),
				"Password Confirm cannot be blank.");

		page.doRegister("abc@", "123456", "123456");
		sa.assertEquals(action.getText(By.xpath("//input[@id='register-email']/../following-sibling::div/div")),
				"Email cannot be blank.");

		String existingEmail = "zerah@soxes.ch";
		page.doRegister(existingEmail, "123456", "123456");
		sa.assertEquals(action.getText(By.xpath("//input[@id='register-email']/../following-sibling::div/div")),
				"Email \"" + existingEmail + "\" has already been taken.");

		String validEmail = "zerah" + Random.currentDateTimeAsString() + "@soxes.ch";
		page.doRegister(validEmail, "12345", "12346");
		sa.assertEquals(action.getText(By.xpath("//input[@id='register-password']/../following-sibling::div/div")),
				"Password must be equal to \"Password Confirm\".");

		validEmail = "zerah" + Random.currentDateTimeAsString() + "@soxes.ch";
		page.doRegister(validEmail, "1", "1");
		sa.assertEquals(action.getText(By.xpath("//input[@id='register-password']/../following-sibling::div/div")),
				"Password should contain at least 5 characters.");
		sa.assertAll();
		validEmail = "zerah" + Random.currentDateTimeAsString() + "@soxes.ch";
		page.register(validEmail, "123456");

	}

	@Test(groups = "", description = "", enabled = false)
	public void UC1_ResetPW() {
		LoginPage page = new LoginPage(android);
		WebViewActions action = new WebViewActions(android);

		// Empty email
		page.doResetPW("");
		Assert.assertEquals(action.getText(By.xpath("//*[@id='reset-password-block']//div[@class='form-errors']/div")),
				"Email cannot be blank.");

		// Unregistered email
		page.doResetPW("sfdfdsf@sdfasdf.ch");
		Assert.assertEquals(action.getText(By.xpath("//*[@id='reset-password-block']//div[@class='form-errors']/div")),
				"User is not registered");

		// Valid email
		page.doResetPW("zerah@soxes.ch");
		Assert.assertEquals(page.isResetPWNotDisplayed(), true, "Expect reset password successfully but it's not. ");
	}
}
