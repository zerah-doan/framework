package pageobject.client;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import framework.action.WebViewActions;
import io.appium.java_client.android.AndroidDriver;
import util.Log;

public class ProductPage {
	@FindBy(xpath = "//button[@ng-click='goBack()']")
	private WebElement btnBackToOrder;

	@FindBy(xpath = "//button[@ng-click='createProduct()']")
	private WebElement btnAddProduct;

	@FindBy(id = "search")
	private WebElement txtSearch;

	@FindBy(id = "sort")
	private WebElement btnSort;

	@FindBy(xpath = "//a[@ng-click='save()']")
	private WebElement btnSaveProduct;

	public enum SortType {
		NAME_ASC, NAME_DES, POS_ASC, POS_DES
	}

	private WebViewActions action;

	public ProductPage(AndroidDriver<WebElement> driver) {
		PageFactory.initElements(driver, this);
		this.action = new WebViewActions(driver);
	}

	public void sort(SortType type) {
		Log.logInfo("Sort product");
		switch (type) {
		case NAME_ASC:
			action.selectDropdown(btnSort, "Name ↑");
			break;
		case NAME_DES:
			action.selectDropdown(btnSort, "Name ↓");
			break;

		case POS_ASC:
			action.selectDropdown(btnSort, "Position ↑");
			break;
		case POS_DES:
			action.selectDropdown(btnSort, "Position ↓");
			break;
		}
		action.waitForAngular();
	}

	public void searchProduct(String txt) {
		Log.logInfo("Search product: " + txt);
		action.type(txtSearch, txt);
		action.waitForAngular();
	}

	public void selectSubCategory(String subCat) {
		Log.logInfo("---Select group: " + subCat);
		action.click(By.xpath("//div[@class='sub-cat' and ./div/text()='" + subCat + "']"));
		action.waitForAngular();
	}

	public void selectSubCategory() {
		Log.logInfo("---Select 1st sup category");
		action.click(By.xpath("//div[@class='sub-cat']"));
		action.waitForAngular();
	}

	public void selectProduct() {
		Log.logInfo("---Select 1st product");
		action.click(By.xpath("//div[@ng-click='selector(product)']"));
		action.waitForAngular();
		saveEditor();
	}

	public void selectProduct(String prodName) {
		Log.logInfo("---Select product: " + prodName);
		action.click(By.xpath("//div[@ng-click='selectProduct(product)' and ./div/text()='" + prodName + "']"));
		action.waitForAngular();
		saveEditor();
	}

	public void increaseAmount(String prodName) {
		Log.logInfo("Increase amount of " + prodName);
		action.click(By.xpath("//div[@class='row' and @ng-repeat and .//div[contains(@class,'product-name')]/text()='"
				+ prodName + "']//button[./text()='+']"));
	}

	public void decreaseAmount(String prodName) {
		Log.logInfo("Decrease amount of " + prodName);
		action.click(By.xpath("//div[@class='row' and @ng-repeat and .//div[contains(@class,'product-name')]/text()='"
				+ prodName + "']//button[./text()='-']"));
	}

	public void increaseAmount(int prodPos) {
		Log.logInfo("Increase amount of no" + prodPos + " product");
		action.click(By.xpath("(//div[@class='row' and @ng-repeat]//button[./text()='+'])[" + prodPos + "]"));
	}

	public void decreaseAmount(int prodPos) {
		Log.logInfo("Decrease amount of no" + prodPos + " product");
		action.click(By.xpath("(//div[@class='row' and @ng-repeat]//button[./text()='-'])[" + prodPos + "]"));
	}

	public void changeAmount(String prodName, boolean isIncreased, int amntOfChange) {
		int count = 0;
		if (isIncreased) {
			Log.logInfo("Increase amount of product by " + amntOfChange);
			while (count < amntOfChange) {
				increaseAmount(prodName);
				count++;
			}
		} else {
			Log.logInfo("Dencrease amount of product by " + amntOfChange);
			while (count < amntOfChange) {
				decreaseAmount(prodName);
				count++;
			}
		}
	}

	public boolean isProductExisting(String prodName) {
		return action.isDisplayed(
				By.xpath("//div[contains(@id,'product')]//div[contains(@class,'product-name') and ./text()='" + prodName
						+ "']"));
	}

	public boolean isProductNotExisting(String prodName) {
		return action.isNotDisplayed(
				By.xpath("//div[@class='row' and @ng-repeat and .//div[contains(@class,'product-name')]/text()='"
						+ prodName + "']"));
	}

	public void deleteProduct(String prodName) {
		Log.logInfo("Delete product " + prodName);
		action.click(By.xpath("//div[@class='row' and @ng-repeat and .//div[contains(@class,'product-name')]/text()='"
				+ prodName + "']//button[@ng-click='deleteProduct(product)']"));
	}

	public void deleteAllProduct() {
		Log.logInfo("Delete all product ");
		List<WebElement> products = action.findElements(
				By.xpath("//div[@class='row' and @ng-repeat]//button[@ng-click='deleteProduct(product)']"));
		for (WebElement product : products) {
			action.click(product);
		}
	}

	public void addProduct(String subCat, String prodName) {
		action.click(btnAddProduct);
		action.waitForAngular();
		selectSubCategory(subCat);
		selectProduct(prodName);
	}

	public void addProduct() {
		action.click(btnAddProduct);
		action.waitForAngular();
		selectSubCategory();
		selectProduct();
	}

	public int getAmount(String prodName) {
		return Integer.parseInt(action.getText(By.xpath(
				"//div[@class='row' and @ng-repeat and .//div[contains(@class,'product-name')]/text()='product 1']//div[@class='amount-body ng-binding']")));
	}

	public void saveEditor() {
		if (action.isDisplayed(By.xpath("//div[@class='modal-content']"))) {
			action.click(btnSaveProduct);
		}
	}
}
