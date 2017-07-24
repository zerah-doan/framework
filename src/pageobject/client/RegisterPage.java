package pageobject.client;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import framework.action.WebViewActions;
import io.appium.java_client.android.AndroidDriver;
import util.Log;

public class RegisterPage {
	@FindBy(id = "register-email")
	private WebElement txtEmail;

	@FindBy(id = "register-password")
	private WebElement txtPassword;

	@FindBy(id = "register-password-confirm")
	private WebElement txtPasswordConfirm;

	@FindBy(xpath = "//div[@click='register()']/button")
	private WebElement btnRegister;

	@FindBy(xpath = "//div[@tab='tab-register']")
	private WebElement tabRegister;

	@FindBy(id = "company")
	private WebElement txtCompany;

	@FindBy(id = "first-name")
	private WebElement txtFirstname;

	@FindBy(id = "last-name")
	private WebElement txtLastName;

	@FindBy(id = "street")
	private WebElement txtStreet;

	@FindBy(id = "zip")
	private WebElement txtZip;

	@FindBy(id = "city")
	private WebElement txtCity;

	@FindBy(id = "sort")
	private WebElement txtCountry;

	@FindBy(id = "phone")
	private WebElement txtPhone;

	@FindBy(id = "mobile")
	private WebElement txtMobile;

	@FindBy(id = "fax")
	private WebElement txtFax;

	@FindBy(xpath = "//div[@label='Save and continue']")
	private WebElement btnSave;

	private WebViewActions action;

	public RegisterPage(AndroidDriver<WebElement> driver) {
		PageFactory.initElements(driver, this);
		this.action = new WebViewActions(driver);
	}

	public void register(String username, String password) {
		doRegister(username, password, password);
		Assert.assertEquals(action.isNotDisplayed(By.id("login")), true, "Register unsuccessfully");
		action.type(txtFirstname, "aaa");
		action.type(txtLastName, "bbb");
		action.selectDropdown(txtCountry, "France");
		action.click(btnSave);
	}

	public void doRegister(String username, String password, String confirm) {
		Log.logInfo("Registration");
		try {
			action.click(tabRegister);
		} catch (TimeoutException e) {
		}
		action.type(txtEmail, username);
		action.type(txtPassword, password);
		action.type(txtPasswordConfirm, confirm);
		action.click(btnRegister);
		action.waitForAngular();
	}

	public boolean isOnRegistration() {
		return action.isDisplayed(By.xpath("//div[@click='register()']"));
	}
}
