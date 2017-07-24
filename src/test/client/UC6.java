package test.client;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import framework.action.WebViewActions;
import framework.config.Config;
import framework.driver.DriverFactory;
import pageobject.client.InstallationPage;
import pageobject.client.LoginPage;
import pageobject.client.OrderPage;
import pageobject.client.ProductPage;
import test.BaseTest;
import util.Log;
import util.Random;

public class UC6 extends BaseTest {
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

	@Test
	public void UC6_CatalogueProduct() {
		InstallationPage insPage = new InstallationPage(android);
		String ins = "a";
		insPage.addInstallation(ins, "Switzerland", "Nordschweiz");
		insPage.openInstallation(ins);
		OrderPage orderPage = new OrderPage(android);
		// Verify installation name
		Assert.assertEquals(orderPage.getInstallationName(), ins, "Expect installation name is correct but is's not. ");

		// Add order
		String order = ins + "#order" + Random.randomNumber(3);
		orderPage.createOrder(order);
		Assert.assertEquals(orderPage.isOrderExisting(order), true, "Expect order is created but is's not. ");
		orderPage.openOrder(order);

		// Add product
		ProductPage prodPage = new ProductPage(android);
		String groupName = "Kanal";
		String prodName = "Kanal";
		prodPage.addProduct(groupName, prodName);
		Assert.assertEquals(prodPage.isProductExisting(prodName), true, "Expected product is added but it's not.");

		prodPage.changeAmount(prodName, true, 3);
		Assert.assertEquals(prodPage.getAmount(prodName), 3, "Expect amount is increased but it's not. ");

		prodPage.changeAmount(prodName, false, 2);
		Assert.assertEquals(prodPage.getAmount(prodName), 1, "Expect amount is decreased but it's not. ");

	}
}
