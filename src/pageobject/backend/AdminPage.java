package pageobject.backend;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import framework.action.WebActions;
import util.Log;

public class AdminPage {
	@FindBy(xpath = "//*[@id='admins-table']//a[./text()='Add New Administrators']")
	private WebElement btnAddUser;

	@FindBy(xpath = "//*[@id='admins-table']//table/tbody/tr//input[@name='first_name']")
	private WebElement txtFirstName;

	@FindBy(xpath = "//*[@id='admins-table']//table/tbody/tr//input[@name='last_name']")
	private WebElement txtLastName;

	@FindBy(xpath = "//*[@id='admins-table']//table/tbody/tr//input[@name='email']")
	private WebElement txtEmail;

	@FindBy(xpath = "//*[@id='admins-table']//table/tbody/tr//input[@name='password']")
	private WebElement txtPassword;

	@FindBy(xpath = "//a[./text()='Update']")
	private WebElement btnUpdateUser;

	@FindBy(xpath = "//a[./text()='Cancel']")
	private WebElement btnCancelUpdate;

	private WebActions action;

	public AdminPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.action = new WebActions(driver);
	}

	public void doAddUser(String firstName, String lastName, String email, String password) {
		Log.logInfo("Add user");
		action.click(btnAddUser);
		action.waitForAngular();
		fillUserInfo(firstName, lastName, email, password);
		action.click(btnUpdateUser);
		action.waitForAngular();
	}

	public void fillUserInfo(String firstName, String lastName, String email, String password) {
		action.type(txtFirstName, firstName);
		action.type(txtLastName, lastName);
		action.type(txtEmail, email);
		action.type(txtPassword, password);
	}

	public void doUpdateUser(String email, String firstName, String lastName, String newEmail, String password) {
		Log.logInfo("Update user with email = " + email);
		clickEditUser(email);
		action.waitForAngular();
		fillUserInfo(firstName, lastName, newEmail, password);
		action.click(btnUpdateUser);
		action.waitForAngular();
	}

	public void deleleUser(String email) {
		Log.logInfo("Delete user with email = " + email);
		clickDeleteUser(email);
		action.confirmPopup(true);
	}

	public boolean isUserExisting(String email) {
		return action.isDisplayed(
				By.xpath("//*[@id='admins-table']//table//tr[@data-uid and ./td//text()='" + email + "']"));
	}

	public boolean isUserNotExisting(String email) {
		return action.isNotDisplayed(
				By.xpath("//*[@id='admins-table']//table//tr[@data-uid and ./td//text()='" + email + "']"));
	}

	public void clickEditUser(String email) {
		action.click(By.xpath("//*[@id='admins-table']//table//tr[@data-uid and ./td//text()='" + email
				+ "']/td/a[.//text()='Edit']"));
	}

	public void clickDeleteUser(String email) {
		action.click(By.xpath("//*[@id='admins-table']//table//tr[@data-uid and ./td//text()='" + email
				+ "']/td/a[.//text()='Delete']"));
	}

	public void searchByEmail(String email) {

	}
}
