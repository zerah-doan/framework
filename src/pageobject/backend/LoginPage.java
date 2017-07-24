package pageobject.backend;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import util.Log;

public class LoginPage extends pageobject.client.LoginPage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public void adminLogin(String username, String password) {
		Log.logInfo("Login as admin: ");
		webAction.type(txtUsername, username);
		webAction.type(txtPassword, password);
		webAction.click(btnLogin);
		webAction.waitForAngular();
		Assert.assertEquals(webAction.isDisplayed(By.id("admins-table")), true);
		webAction.waitForAngular();
	}
}
