package pageobject.client;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import framework.action.WebActions;
import framework.action.WebViewActions;
import io.appium.java_client.android.AndroidDriver;
import util.Log;

public class LoginPage {
	@FindBy(id = "login")
	protected WebElement txtUsername;

	@FindBy(id = "password")
	protected WebElement txtPassword;

	@FindBy(xpath = "//div[@click='login()']/button")
	protected WebElement btnLogin;

	@FindBy(xpath = "//a[@href='#!/public/login/restore']")
	protected WebElement lnkResetPW;

	@FindBy(id = "reset-email")
	protected WebElement txtResetEmail;

	@FindBy(xpath = "//span[@label='Reset Password']/button")
	protected WebElement btnResetPW;

	@FindBy(xpath = "//button[@ng-click='$dismiss()']")
	protected WebElement btnCancelResetPW;

	private WebViewActions wvAction;
	protected WebActions webAction;

	public LoginPage(AndroidDriver<WebElement> driver) {
		PageFactory.initElements(driver, this);
		this.wvAction = new WebViewActions(driver);
	}

	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.webAction = new WebActions(driver);
	}

	public void login(String username, String password) {
		doLogin(username, password);
		Assert.assertEquals(wvAction.isNotDisplayed(By.id("login")), true, "Expect login successfully but it's not. ");
		waitFotSyncThenClosePopUp();
	}

	public void doLogin(String username, String password) {
		Log.logInfo("Login");
		wvAction.type(txtUsername, username);
		wvAction.type(txtPassword, password);
		wvAction.click(btnLogin);
		wvAction.waitForAngular();
	}

	public void doResetPW(String email) {
		Log.logInfo("Reset Password");
		if (!isResetPWDisplayed()) {
			wvAction.click(lnkResetPW);
		}
		wvAction.type(txtResetEmail, email);
		wvAction.click(btnResetPW);
		wvAction.waitForAngular();
	}

	public boolean isResetPWDisplayed() {
		wvAction.sleep(5);
		return wvAction
				.isDisplayed(By.xpath("//div[@uib-modal-window='modal-window' and .//h3/text()='Reset Password']"));
	}

	public boolean isResetPWNotDisplayed() {
		return wvAction
				.isNotDisplayed(By.xpath("//div[@uib-modal-window='modal-window' and .//h3/text()='Reset Password']"));
	}

	public void waitFotSyncThenClosePopUp() {
		wvAction.waitUntilInvisible(By.xpath("//div[@class='modal-header' and .//text()='Data Synchronization']"));
	}
}
