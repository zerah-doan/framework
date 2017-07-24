package pageobject.backend;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import framework.action.WebActions;
import util.Log;

public class MasterDataPage {
	@FindBy(xpath = "//*[@id='proair-nav-bar']//li/a[./text()='Master Data']")

	private WebElement lnkMasterData;

	@FindBy(xpath = "//*[@id='countries-table']//a[.//text()='Add New Country']")
	private WebElement btnAddCountry;

	@FindBy(xpath = "//*[@id='countries-table']//table[@role='treegrid']//tr/td/input[@name='name']")
	private WebElement txtCountry;

	@FindBy(xpath = "//*[@id='countries-table']//table[@role='treegrid']//tr/td/input[@name='currency']")
	private WebElement txtCurrency;

	@FindBy(xpath = "//*[@id='countries-table']//table[@role='treegrid']//tr/td/input[@name='vat_amount']")
	private WebElement txtTax;

	@FindBy(xpath = "//a[.//text()='Update']")
	private WebElement btnUpdate;

	@FindBy(xpath = "//a[.//text()='Cancel']")
	private WebElement btnCancelUpdate;

	@FindBy(xpath = "//*[@id='categories-table']//a[.//text()='Add New Category']")
	private WebElement btnAddCategory;

	@FindBy(xpath = "//*[@id='categories-table']//table[@role='grid']//tr/td/input[@name='name']")
	private WebElement txtCategory;

	private WebActions action;

	public MasterDataPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.action = new WebActions(driver);
	}

	public void doAddCountry(String country, String currency, String tax) {
		Log.logInfo("Add country");
		action.click(btnAddCountry);
		action.waitForAngular();
		fillCountry(country, currency, tax);
		action.click(btnUpdate);
		action.waitForAngular();
	}

	public void fillCountry(String country, String currency, String tax) {
		action.type(txtCountry, country);
		action.type(txtCurrency, currency);
		enterTax(tax);
	}

	public void doUpdateCountry(String country, String newCountry, String currency, String tax) {
		Log.logInfo("Update country");
		clickEditCountry(country);
		fillCountry(newCountry, currency, tax);
		action.click(btnUpdate);
		action.waitForAngular();
	}

	public boolean isCountryExisting(String country) {
		return action.isDisplayed(
				By.xpath("//*[@id='countries-table']//table[@role='treegrid']//tr/td[.//text()='" + country + "']"));
	}

	public boolean isCountryDeleted(String country) {
		return action.isNotDisplayed(
				By.xpath("//*[@id='countries-table']//table[@role='treegrid']//tr/td[.//text()='" + country + "']"));
	}

	public void doDeleteCountry(String country) {
		Log.logInfo("Delete country");
		action.click(By.xpath("//*[@id='countries-table']//table[@role='treegrid']//tr[td//text()='" + country
				+ "']/td/a[./text()='Delete']"));
		action.confirmPopup(true);
	}

	public void clickEditCountry(String country) {
		action.click(By.xpath("//*[@id='countries-table']//table[@role='treegrid']//tr[td//text()='" + country
				+ "']/td/a[./text()='Edit']"));
	}

	public void goToPage() {
		Log.logInfo("Go to Master Data page");
		action.click(lnkMasterData);
		action.waitForAngular();
		action.waitForAngular();
	}

	private void enterTax(String tax) {
		Log.logInfo("---Enter tax");
		action.click(By
				.xpath("//*[@id='countries-table']//table[@role='treegrid']//tr/td//input[@style='display: block;']"));
		action.waitForAngular();
		action.type(
				By.xpath("//*[@id='countries-table']//table[@role='treegrid']//tr/td//input[@style='display: block;']"),
				tax);
	}

	public void doAddCategory(String catName) {
		Log.logInfo("Add category");
		action.click(btnAddCategory);
		action.waitForAngular();
		action.type(txtCategory, catName);
		action.click(btnUpdate);
		action.waitForAngular();
	}

	public boolean isCategoryExisting(String catName) {
		return action.isDisplayed(By.xpath("//*[@id='categories-table']//table//tr/td[.//text()='" + catName + "']"));
	}

	public boolean isCategoryNotExisting(String catName) {
		return action
				.isNotDisplayed(By.xpath("//*[@id='categories-table']//table//tr/td[.//text()='" + catName + "']"));
	}

	public void doEditCategory(String oldName, String newName) {
		Log.logInfo("Edit category");
		action.click(By
				.xpath("//*[@id='categories-table']//table//tr[./td//text()='" + oldName + "']//a[.//text()='Edit']"));
		action.waitForAngular();
		action.type(txtCategory, newName);
		action.click(btnUpdate);
		action.waitForAngular();
	}

	public void doDeleteCategory(String cat) {
		Log.logInfo("Delete category");
		action.click(
				By.xpath("//*[@id='categories-table']//table//tr[./td//text()='" + cat + "']//a[.//text()='Delete']"));
		action.confirmPopup(true);
		action.waitForAngular();
	}
}
