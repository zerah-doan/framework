package pageobject.backend;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import framework.action.WebActions;
import util.Log;

public class AdvertisingPage {

	@FindBy(xpath = "//a[./text()='Add New Advertisement']")
	private WebElement btnAddAdv;

	@FindBy(xpath = "//*[@id='advertisements-grid']//table//tr//input[@name='supplier_name']")
	private WebElement txtCompany;

	@FindBy(xpath = "//*[@id='advertisements-grid']//table//tr//input[@name='supplier_street']")
	private WebElement txtStreet;

	@FindBy(xpath = "//*[@id='advertisements-grid']//table//tr//input[@name='supplier_zip']")
	private WebElement txtZip;

	@FindBy(xpath = "//*[@id='advertisements-grid']//table//tr//input[@name='supplier_city']")
	private WebElement txtCity;

	@FindBy(xpath = "//*[@id='advertisements-grid']//table//tr//input[@name='supplier_name']")
	private WebElement drpCountry;

	@FindBy(xpath = "//*[@id='advertisements-grid']//table//tr//input[@name='supplier_phone']")
	private WebElement txtTelephone;

	@FindBy(xpath = "//*[@id='advertisements-grid']//table//tr//input[@name='supplier_email']")
	private WebElement txtEmail;

	@FindBy(xpath = "//*[@id='advertisements-grid']//table//tr//input[@name='supplier_url']")
	private WebElement txtUrl;

	@FindBy(xpath = "//*[@id='advertisements-grid']//table//tr//input[@id='fileUpload']")
	private WebElement txtLogo;

	@FindBy(xpath = "//*[@id='advertisements-grid']//table//tr//input[@data-value-field='closing_date']")
	private WebElement txtExpiration;

	@FindBy(xpath = "//*[@id='advertisements-grid']//table//tr//input[@data-bind='value:weight']")
	private WebElement txtWeight;

	@FindBy(xpath = "//*[@id='advertisements-grid']//table//tr//a[./text()='Update']")
	private WebElement btnUpdate;

	@FindBy(xpath = "//*[@id='advertisements-grid']//table//tr//a[./text()='Cancel']")
	private WebElement btnCancelUpdate;

	@FindBy(xpath = "//div[./text()='Save Category']")
	private WebElement btnSaveCat;

	private WebActions action;

	public AdvertisingPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.action = new WebActions(driver);
	}

	public void goToPage() {
		action.click(By.xpath("//*[@id='proair-nav-bar']/ul/li[./a/text()='Advertisements']"));
		action.waitForAngular();
		action.waitForAngular();
		action.waitForAngular();
	}

	public void addAdvertising(String company, String street, String zip, String city, String phone, String email) {
		Log.logInfo("Add advertising user");
		action.click(btnAddAdv);
		action.waitForAngular();
		fillRequiredInfo(company, street, zip, city, phone, email);
		action.click(btnUpdate);
		action.waitForAngular();
	}

	public void editAdvertising(String oldCompany, String company, String street, String zip, String city, String phone,
			String email) {
		Log.logInfo("Edit advertising user: " + oldCompany);
		clickEditAdvertising(oldCompany);
		action.waitForAngular();
		fillRequiredInfo(company, street, zip, city, phone, email);
		action.click(btnUpdate);
		action.waitForAngular();
	}

	public void fillRequiredInfo(String company, String street, String zip, String city, String phone, String email) {
		action.type(txtCompany, company);
		action.type(txtStreet, street);
		action.type(txtZip, zip);
		action.type(txtCity, city);
		action.type(txtTelephone, phone);
		action.type(txtEmail, email);

	}

	public void fillNotRequiredInfo(String url, String logo, String exp, String country, String region, int weight) {

	}

	public void openCategoryByRow(int row) {
		action.click(By.xpath("//*[@id='advertisements-grid']//table//tr[" + row + "]//a[./text()='Category']"));
		action.waitForAngular();
	}

	public void openCategory(String company) {
		action.click(By
				.xpath("//*[@id='advertisements-grid']//table//tr[count(//*[@id='advertisements-grid']//table//tr[.//text()='"
						+ company + "']/preceding-sibling::*)+1]//a[./text()='Category']"));
		action.waitForAngular();
		action.waitForAngular();
	}

	public void clickEditAdvertising(String company) {
		action.click(By
				.xpath("//*[@id='advertisements-grid']//table//tr[count(//*[@id='advertisements-grid']//table//tr[.//text()='"
						+ company + "']/preceding-sibling::*)+1]//a[./text()='Edit']"));
		action.waitForAngular();
	}

	public void deleteAdvertising(String company) {
		Log.logInfo("Delete advertising user: " + company);
		action.click(By
				.xpath("//*[@id='advertisements-grid']//table//tr[count(//*[@id='advertisements-grid']//table//tr[.//text()='"
						+ company + "']/preceding-sibling::*)+1]//a[./text()='Delete']"));
		action.confirmPopup(true);
	}

	public boolean isCompanyExisting(String company) {
		return action.isDisplayed(By.xpath("//*[@id='advertisements-grid']//table//tr[.//text()='" + company + "']"));
	}

	public boolean isCompanyNotExisting(String company) {
		return action
				.isNotDisplayed(By.xpath("//*[@id='advertisements-grid']//table//tr[.//text()='" + company + "']"));
	}

	public void selectCategory(String company, List<String> cats) {
		Log.logInfo("Select category for company: " + company);
		openCategory(company);
		List<WebElement> allCat = action.findElements(By.xpath("//form[@name='userForm']//input[@type='checkbox']"));
		String catTitle;
		for (WebElement cat : allCat) {
			catTitle = cat.findElement(By.xpath("./following-sibling::p")).getText();
			if (cats.contains(catTitle) && !cat.isSelected()) {
				Log.logInfo("---Select category: " + catTitle);
				action.click(cat);
				action.type(cat.findElement(By.xpath("../following-sibling::input")), catTitle + " description");
			}
		}
		action.click(btnSaveCat);
		action.waitForAngular();
	}
}
