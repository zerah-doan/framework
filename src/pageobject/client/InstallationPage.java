package pageobject.client;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import framework.action.WebViewActions;
import io.appium.java_client.android.AndroidDriver;
import util.Log;
import util.Random;

public class InstallationPage {
	@FindBy(id = "search")
	public WebElement txtSearch;

	@FindBy(id = "sort")
	public WebElement btnSort;

	@FindBy(xpath = "//button[@ng-click='createInstallation()']")
	public WebElement btnAddIns;

	@FindBy(id = "installation-name")
	public WebElement txtInsName;

	@FindBy(id = "installation-nr")
	public WebElement txtInsNum;

	@FindBy(id = "order-nr")
	public WebElement txtOrderNum;

	@FindBy(id = "project-nr")
	public WebElement txtProjectNum;

	@FindBy(id = "description")
	public WebElement txtComments;

	@FindBy(id = "company")
	public WebElement txtCompany;

	@FindBy(id = "city")
	public WebElement txtOrderCity;

	@FindBy(id = "zip")
	public WebElement txtOrderZip;

	@FindBy(id = "street")
	public WebElement txtOrderStreet;

	@FindBy(id = "responsible-person")
	public WebElement txtResponsiblePerson;

	@FindBy(id = "phone")
	public WebElement txtOrderPhone;

	@FindBy(id = "order-date")
	public WebElement txtOrderDate;

	@FindBy(id = "city-delivery")
	public WebElement txtDeliveryCity;

	@FindBy(id = "zip-delivery")
	public WebElement txtDeliveryZip;

	@FindBy(id = "street-delivery")
	public WebElement txtDeliveryStreet;

	@FindBy(id = "phone-delivery")
	public WebElement txtDeliveryPhone;

	@FindBy(id = "date-delivery")
	public WebElement txtDeliveryDate;

	@FindBy(id = "time-delivery")
	public WebElement txtDeliveryTime;

	@FindBy(id = "country")
	public WebElement btnCountry;

	@FindBy(id = "region")
	public WebElement btnRegion;

	@FindBy(xpath = "//button[@ng-click='cancel()']")
	public WebElement btnClose;

	@FindBy(xpath = "//button[@ng-click='ok()']")
	public WebElement btnSave;

	@FindBy(xpath = "//button[@ng-click='nextStep()']")
	public WebElement btnNext;

	private WebViewActions action;

	public InstallationPage(AndroidDriver<WebElement> driver) {
		PageFactory.initElements(driver, this);
		this.action = new WebViewActions(driver);
	}

	public void searchIns(String txt) {
		Log.logInfo("Search installation: ");
		action.type(txtSearch, txt);
		action.waitForAngular();
	}

	public enum SortType {
		NAME_ASC, NAME_DES
	}

	public void sort(SortType type) {
		switch (type) {
		case NAME_ASC:
			action.selectDropdown(btnSort, "Name ↑");
			break;
		case NAME_DES:
			action.selectDropdown(btnSort, "Name ↓");
			break;
		}
		action.waitForAngular();
	}

	public void addInstallation(String name, String country, String region) {
		Log.logInfo("Add installation");
		action.click(btnAddIns);
		fillObject(name, "", "", "", "");
		action.click(btnNext);
		if (isDuplicatedName()) {
			action.type(txtInsName, name + Random.randomNumber(3));
			action.click(btnNext);
		}
		fillOrdrer("", "", "", "", "", "", "");
		action.click(btnNext);
		fillDelivery("", "", "", "", "", "");
		action.click(btnNext);
		fillSuppier(country, region);
		action.click(btnNext);
		action.waitForAngular();
		action.waitForAngular();
	}

	public void fillObject(String name, String insNum, String orderNum, String projNum, String comment) {
		action.type(txtInsName, name);
		action.type(txtInsNum, insNum);
		action.type(txtOrderNum, orderNum);
		action.type(txtProjectNum, projNum);
		// action.type(txtComments, comment);
	}

	public void fillOrdrer(String company, String city, String zip, String street, String resPerson, String phone,
			String orderDate) {
		action.type(txtCompany, company);
		action.type(txtOrderStreet, street);
		action.type(txtOrderZip, zip);
		action.type(txtOrderCity, city);

		action.type(txtResponsiblePerson, resPerson);
		action.type(txtOrderPhone, phone);
		// action.type(txtOrderDate, orderDate);
	}

	public void fillDelivery(String street, String city, String zip, String contact, String date, String time) {
		action.type(txtDeliveryStreet, street);
		action.type(txtDeliveryZip, zip);
		action.type(txtDeliveryCity, city);

		action.type(txtDeliveryPhone, contact);
		// action.type(txtDeliveryDate, date);
		// action.type(txtDeliveryTime, time);
	}

	public void fillSuppier(String country, String region) {
		try {
			action.selectDropdown(btnCountry, country);
		} catch (TimeoutException e) {
			action.selectDropdown(btnCountry, 1);
		}
		try {
			action.selectDropdown(btnRegion, region);
		} catch (TimeoutException e) {
			action.selectDropdown(btnRegion, 1);
		}

	}

	public boolean isAddInsModalClosed() {
		return action.isNotDisplayed(By.xpath("//div[@class='modal fade in' and .//h3/text()='Create Installation']"));
	}

	public boolean isInstallationExisted(String name) {
		return action.isDisplayed(By.xpath("//div[@installation]//*[./text()='" + name + "']"));
	}

	public boolean isInstallationDeleted(String name) {
		return action.isNotDisplayed(By.xpath("//div[@installation]//*[./text()='" + name + "']"));
	}

	public void deleteInstallation(String name, boolean option) {
		Log.logInfo("Delete installation: " + name);
		action.click(By.xpath("//div[@class='card' and @installation and .//div[@class='card-header']//text()='" + name
				+ "']//div[@class='close-button']"));
		if (option) {
			action.click(By.xpath("//div[@class='popup']//button[.//text()='Yes']"));
		} else {
			action.click(By.xpath("//div[@class='popup']//button[.//text()='No']"));
		}
		action.waitForAngular();
	}

	public void openInstallation(String name) {
		Log.logInfo("Open installation: " + name);
		action.click(By.xpath("//div[@class='card' and @installation and .//div/text()='" + name + "']"));
		action.waitForAngular();
		action.waitForAngular();
	}

	public boolean isDuplicatedName() {
		return action.isDisplayed(By.xpath("//div[./text()='Installation name should be unique']"))
				|| action.isDisplayed(By.xpath("//div[./text()='Anlage Name existiert schon']"))
				|| action.isDisplayed(By.xpath("//div[./text()='Nom de l'intallation existe déjà']"))
				|| action.isDisplayed(By.xpath("//div[./text()='Anlage Name muss unique sein']"));
	}
}
