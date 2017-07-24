package test.client;

import org.testng.annotations.Test;

import framework.action.WebViewActions;
import framework.config.Config;
import framework.driver.DriverFactory;
import pageobject.client.InstallationPage;
import pageobject.client.LoginPage;
import pageobject.client.OrderPage;
import pageobject.client.OrderPage.SortType;
import test.BaseTest;
import util.Log;
import util.Random;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

public class UC4_5 extends BaseTest {
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

	@Test(groups = "", description = "")
	public void UC5_Order() {
		InstallationPage insPage = new InstallationPage(android);
		String ins = "a";
		insPage.addInstallation(ins, "Switzerland", "Alle");
		insPage.openInstallation(ins);
		OrderPage orderPage = new OrderPage(android);
		// Verify installation name
		Assert.assertEquals(orderPage.getInstallationName(), ins, "Expect installation name is correct but is's not. ");

		// Add
		String order = ins + "#order" + Random.randomNumber(3);
		orderPage.createOrder(order);
		Assert.assertEquals(orderPage.isOrderExisting(order), true, "Expect order is created but is's not. ");

		String order2 = ins + "#order" + Random.randomNumber(3);
		orderPage.createOrder(order2);
		Assert.assertEquals(orderPage.isOrderExisting(order2), true, "Expect order is created but is's not. ");

		String order3 = ins + "#order" + Random.randomNumber(3);
		orderPage.createOrder(order3);
		Assert.assertEquals(orderPage.isOrderExisting(order3), true, "Expect order is created but is's not. ");

		// Sort
		orderPage.sort(SortType.DATE_ASC);
		orderPage.sort(SortType.DATE_DES);
		orderPage.sort(SortType.NAME_ASC);
		orderPage.sort(SortType.NAME_DES);

		// Search
		orderPage.searchOrder(order);
		Assert.assertEquals(orderPage.isOrderExisting(order), true, "Expect " + order + " is created but is's not. ");
		Assert.assertEquals(orderPage.isOrderNotExisting(order2), true,
				"Expect " + order2 + " is created but is's not. ");
		Assert.assertEquals(orderPage.isOrderNotExisting(order3), true,
				"Expect " + order3 + " is created but is's not. ");

		orderPage.searchOrder("");
		Assert.assertEquals(orderPage.isOrderExisting(order), true, "Expect " + order + " is created but is's not. ");
		Assert.assertEquals(orderPage.isOrderExisting(order2), true, "Expect " + order2 + " is created but is's not. ");
		Assert.assertEquals(orderPage.isOrderExisting(order3), true, "Expect " + order3 + " is created but is's not. ");

		// Delete
		orderPage.deleteOrder(order, false);
		Assert.assertEquals(orderPage.isOrderExisting(order), true, "Expect " + order + " is created but is's not. ");
		orderPage.deleteOrder(order, true);
		Assert.assertEquals(orderPage.isOrderNotExisting(order), true,
				"Expect " + order + " is created but is's not. ");

		// clear data
		orderPage.deleteOrder(order2, true);
		orderPage.deleteOrder(order3, true);
		orderPage.backToInsOverview();
		insPage.deleteInstallation(ins, true);
	}
}
