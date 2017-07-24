package pageobject.client;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import framework.action.WebViewActions;
import io.appium.java_client.android.AndroidDriver;
import util.Log;

public class OrderPage {
	@FindBy(xpath = "//button[@ng-click='createOrder()']")
	private WebElement btnCreateOrder;

	@FindBy(id = "search")
	private WebElement txtSearch;

	@FindBy(id = "sort")
	private WebElement btnSort;

	@FindBy(id = "")
	private WebElement btnSupplierCountry;

	@FindBy(id = "")
	private WebElement btnSupplierRegion;

	@FindBy(id = "")
	private WebElement btnCategory;

	@FindBy(id = "installation-name")
	private WebElement txtInsName;

	@FindBy(id = "installation-nr")
	private WebElement txtInsNum;

	@FindBy(id = "order-nr")
	private WebElement txtOrderNum;

	@FindBy(id = "project-nr")
	private WebElement txtProjectNum;

	@FindBy(id = "order_name")
	private WebElement txtOrderName;

	@FindBy(id = "description")
	private WebElement txtComment;

	@FindBy(id = "company")
	private WebElement txtCompany;

	@FindBy(xpath = "//form[@name='create_order_supplier']//*[@id='street']")
	private WebElement txtStreet;

	@FindBy(id = "zip")
	private WebElement txtZip;

	@FindBy(id = "city")
	private WebElement txtCity;

	@FindBy(id = "responsible-person")
	private WebElement txtResPerson;

	@FindBy(id = "phone")
	private WebElement txtPhone;

	@FindBy(id = "order-date")
	private WebElement txtOrderDate;

	@FindBy(id = "street_delivery")
	private WebElement txtDeliveryStreet;

	@FindBy(id = "zip-delivery")
	private WebElement txtDeliveryZip;

	@FindBy(id = "city-delivery")
	private WebElement txtDeliveryCity;

	@FindBy(id = "phone_delivery")
	private WebElement txtDeliveryPhone;

	@FindBy(name = "phone_delivery_2")
	private WebElement txtDeliveryPhone2;

	@FindBy(id = "date-delivery")
	private WebElement txtDeliveryDate;

	@FindBy(id = "time-delivery")
	private WebElement txtDeliveryTime;

	@FindBy(id = "")
	private WebElement btnCountry;

	@FindBy(id = "")
	private WebElement btnRegion;

	@FindBy(xpath = "//button[@ng-click='editSupplier()']")
	private WebElement btnEditSupplier;

	@FindBy(xpath = "//button[@ng-click='nextStep()']")
	private WebElement btnNext;

	@FindBy(xpath = "//button[@ng-click='cancel()']")
	private WebElement btnCancelCreation;

	@FindBy(id = "sort")
	private WebElement btnEditIns;

	@FindBy(xpath = "//button[@ng-click='goBack()']")
	private WebElement btnBack;

	public enum SortType {
		NAME_ASC, NAME_DES, DATE_ASC, DATE_DES
	}

	private WebViewActions action;

	public OrderPage(AndroidDriver<WebElement> driver) {
		PageFactory.initElements(driver, this);
		this.action = new WebViewActions(driver);
	}

	public void createOrder(String name) {
		Log.logInfo("Create Order");
		action.click(btnCreateOrder);
		action.waitForAngular();
		action.waitForAngular();
		selectSupplier();
		action.waitForAngular();
		fillObject(name, "");
		action.click(btnNext);
		fillOrder("", "", "");
		action.click(btnNext);
		fillDelivery("", "", "", "", "", "", "");
		action.click(btnNext);
		fillSupplier("", "");
		action.click(btnNext);
	}

	public void searchOrder(String txt) {
		Log.logInfo("Search Order");
		action.type(txtSearch, txt);
		action.waitForAngular();
	}

	public void sort(SortType type) {
		Log.logInfo("Sort Order");
		switch (type) {
		case NAME_ASC:
			action.selectDropdown(btnSort, "Name ↑");
			break;
		case NAME_DES:
			action.selectDropdown(btnSort, "Name ↓");
			break;

		case DATE_ASC:
			action.selectDropdown(btnSort, "Date of creation ↑");
			break;
		case DATE_DES:
			action.selectDropdown(btnSort, "Date of creation ↓");
			break;
		}
		action.waitForAngular();
	}

	public void selectSupplier(String name) {
		action.click(By.xpath("//div[@supplier and .//div[@class='card-header']/div[./text()='" + name + "']]"));
	}

	public void selectSupplier() {
		action.click(By.xpath("//div[@supplier]"));
	}

	public void fillObject(String orderName, String comment) {
		action.waitForAngular();
		action.sleep(5);
		action.type(txtOrderName, orderName);
		action.type(txtComment, comment);
	}

	public void fillOrder(String person, String phone, String orderDate) {
		action.type(txtResPerson, person);
		action.type(txtPhone, phone);
		action.type(txtOrderDate, orderDate);
	}

	public void fillDelivery(String street, String zip, String city, String phone, String phone2, String date,
			String time) {
		action.type(txtDeliveryStreet, street);
		action.type(txtDeliveryZip, zip);
		action.type(txtDeliveryCity, city);
		action.type(txtDeliveryPhone, phone);
		action.type(txtDeliveryPhone2, phone2);
		action.type(txtDeliveryDate, date);
		action.type(txtDeliveryTime, time);
	}

	public void fillSupplier(String country, String region) {
		// action.selectDropdown(btnCountry, country);
		// action.selectDropdown(btnRegion, region);
	}

	public boolean isOrderExisting(String name) {
		return action
				.isDisplayed(By.xpath("//div[@class='card' and .//div[@class='card-header']//text()='" + name + "']"));
	}

	public boolean isOrderNotExisting(String name) {
		return action.isNotDisplayed(
				By.xpath("//div[@class='card' and .//div[@class='card-header']//text()='" + name + "']"));
	}

	public void deleteOrder(String name, boolean option) {
		action.click(By.xpath("//div[@class='card' and .//div[@class='card-header']//text()='" + name + "']//i"));
		if (option) {
			action.click(By.xpath("//div[@class='popup']//button[.//text()='Yes']"));
		} else {
			action.click(By.xpath("//div[@class='popup']//button[.//text()='No']"));
		}
		action.waitForAngular();
	}

	public String getInstallationName() {
		return action.getText(By.xpath("//label[@class='installation-name ng-binding']"));
	}

	public void backToInsOverview() {
		action.scrollIntoView(btnBack);
		action.click(btnBack);
		action.waitForAngular();
	}

	public void openOrder(String name) {
		action.click(By.xpath("//div[@class='card' and .//div/text()='" + name + "']"));
		action.waitForAngular();
	}
}
