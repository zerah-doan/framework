package pageobject.backend;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import framework.action.WebActions;
import util.Log;

public class SubscriptionPage {
	@FindBy(xpath = "//*[@id='proair-nav-bar']//li/a[./text()='Subscription Packages']")
	private WebElement lnkSubscription;

	@FindBy(xpath = "//a[./text()='Add New Package']")
	private WebElement btnAddPackage;

	@FindBy(xpath = "//a[./text()='Add New Package Price']")
	private WebElement btnAddPrice;

	@FindBy(xpath = "//*[@id='packages-table']//table//tr/td/a[./text()='Update']")
	private WebElement btnUpdate;

	@FindBy(xpath = "//*[@id='packages-table']//table//tr/td/a[./text()='Cancel']")
	private WebElement btnCancel;

	@FindBy(xpath = "//*[@id='packages-table']//table//tr/td//input[@name='title']")
	private WebElement txtTitle;

	@FindBy(xpath = "//*[@id='packages-table']//table//tr/td//input[@name='description']")
	private WebElement txtDescription;

	@FindBy(xpath = "//*[@id='packages-table']//table//tr/td[@data-container-for='period_view']//input")
	private WebElement txtPeriod;

	@FindBy(xpath = "//*[@id='packages-table']//table//tr//table//tr/td/input[@name='currency']")
	private WebElement txtCurrency;

	@FindBy(xpath = "//*[@id='packages-table']//table//tr//table//tr//input[@name='amount']")
	private WebElement txtAmount;

	private WebActions action;

	public SubscriptionPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.action = new WebActions(driver);
	}

	public void moveToPage() {
		action.click(lnkSubscription);
		action.waitForAngular();
		action.waitForAngular();
	}

	public void fillPackage(String title, String des, String period) {
		action.type(txtTitle, title);
		action.type(txtDescription, des);
		action.type(txtPeriod, period);
	}

	public void addPackage(String title, String des, String period) {
		Log.logInfo("---Add package: ");
		action.click(btnAddPackage);
		fillPackage(title, des, period);
		action.click(btnUpdate);
		action.waitForAngular();
	}

	public void clickEditPackage(String pkgTitle) {
		action.click(By
				.xpath("//*[@id='packages-table']//table//tr[./td[.//text()='" + pkgTitle + "']]//a[./text()='Edit']"));
	}

	public void editPackage(String oldTitle, String newTitle, String des, String period) {
		Log.logInfo("---Edit package " + oldTitle + ": ");
		clickEditPackage(oldTitle);
		fillPackage(newTitle, des, period);
		action.click(btnUpdate);
		action.waitForAngular();
	}

	public void deletePackage(String pkgTitle) {
		Log.logInfo("---Delete package " + pkgTitle + ": ");
		action.click(By.xpath(
				"//*[@id='packages-table']//table//tr[./td[.//text()='" + pkgTitle + "']]//a[./text()='Delete']"));
		action.confirmPopup(true);
		action.waitForAngular();
	}

	public boolean isPackageExisting(String pkgTitle) {
		return action.isDisplayed(By.xpath("//*[@id='packages-table']//table//tr[./td[.//text()='" + pkgTitle + "']]"));
	}

	public boolean isPackageNotExisting(String pkgTitle) {
		return action
				.isNotDisplayed(By.xpath("//*[@id='packages-table']//table//tr[./td[.//text()='" + pkgTitle + "']]"));
	}

	public void expandPackage(String pkgTitle) {
		By by = By.xpath("(//*[@id='packages-table']//table//tr[./td[.//text()='" + pkgTitle
				+ "']]/td)[1]/a[@class='k-icon k-i-expand']");
		if (action.isDisplayed(by)) {
			action.click(by);
			action.waitForAngular();
		}
	}

	public void collapsePackage(String pkgTitle) {
		By by = By.xpath("(//*[@id='packages-table']//table//tr[./td[.//text()='" + pkgTitle
				+ "']]/td)[1]/a[@class='k-icon k-i-collapse']");
		if (action.isDisplayed(by)) {
			action.click(by);
			action.waitForAngular();
		}
	}

	public void addPrice(String pkgTitle, String cur, String amount) {
		Log.logInfo("---Add price to " + pkgTitle + " package: ");
		expandPackage(pkgTitle);
		action.click(btnAddPrice);
		fillPrice(cur, amount);
		action.click(btnUpdate);
		action.waitForAngular();
	}

	public void fillPrice(String cur, String amount) {
		action.type(txtCurrency, cur);
		action.type(txtAmount, amount);
	}

	public void editPrice(String pkgTitle, String oldCur, String cur, String amount) {
		Log.logInfo("---Edit " + oldCur + " currency of " + pkgTitle + " package: ");
		clickEditPrice(pkgTitle, oldCur);
		action.type(txtCurrency, cur);
		action.typeNumbericBox(txtAmount, txtAmount, amount);
		action.click(btnUpdate);
		action.waitForAngular();
	}

	public void clickEditPrice(String pkgTitle, String cur) {
		// expandPackage(pkgTitle);
		action.click(By.xpath(
				"//*[@id='packages-table']//table//tr//table//tr[./td[./text()='" + cur + "']]/td/a[./text()='Edit']"));
	}

	public void deletePrice(String pkgTitle, String cur) {
		Log.logInfo("---Delete " + cur + " currency of " + pkgTitle + " package: ");
		// expandPackage(pkgTitle);
		action.click(By.xpath("//*[@id='packages-table']//table//tr//table//tr[./td[./text()='" + cur
				+ "']]/td/a[./text()='Delete']"));
		action.confirmPopup(true);
		action.waitForAngular();
	}

	public boolean isPriceExisting(String pkgTitle, String cur) {
		return action
				.isDisplayed(By.xpath("//*[@id='packages-table']//table//tr//table//tr/td[./text()='" + cur + "']"));
	}

	public boolean isPriceNotExisting(String pkgTitle, String cur) {
		return action
				.isNotDisplayed(By.xpath("//*[@id='packages-table']//table//tr//table//tr/td[./text()='" + cur + "']"));
	}
}
