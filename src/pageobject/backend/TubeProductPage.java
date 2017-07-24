package pageobject.backend;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import framework.action.WebActions;
import util.Log;

public class TubeProductPage {
	@FindBy(xpath = "//*[@id='tubes-grid']//a[./text()='Add New Product']")
	private WebElement btnSaveCat;

	@FindBy(xpath = "//*[@id='tubes-grid']//table//tr[1]/td[@data-container-for='language']/span")
	private WebElement drpLanguage;

	@FindBy(xpath = "// *[@id='tubes-grid']//table//tr[1]//input[@name='article_nr']")
	private WebElement txtProdNum;

	@FindBy(xpath = "// *[@id='tubes-grid']//table//tr[1]//input[@name='name']")
	private WebElement txtProdName;

	@FindBy(xpath = "// *[@id='tubes-grid']//table//tr[1]//input[@name='field_1']")
	private WebElement txtField1;

	@FindBy(xpath = "// *[@id='tubes-grid']//table//tr[1]//input[@name='field_2']")
	private WebElement txtField2;

	@FindBy(xpath = "// *[@id='tubes-grid']//table//tr[1]//input[@name='field_3']")
	private WebElement txtField3;
	@FindBy(xpath = "// *[@id='tubes-grid']//table//tr[1]//input[@name='field_4']")
	private WebElement txtField4;

	@FindBy(xpath = "// *[@id='tubes-grid']//table//tr[1]//input[@name='field_5']")
	private WebElement txtField5;

	@FindBy(xpath = "// *[@id='tubes-grid']//table//tr[1]//input[@name='field_6']")
	private WebElement txtField6;

	@FindBy(xpath = "// *[@id='tubes-grid']//table//tr[1]//input[@name='option_1']")
	private WebElement txtOption1;

	@FindBy(xpath = "// *[@id='tubes-grid']//table//tr[1]//input[@name='option_2']")
	private WebElement txtOption2;

	@FindBy(xpath = "// *[@id='tubes-grid']//table//tr[1]//input[@name='option_3']")
	private WebElement txtOption3;

	@FindBy(xpath = "// *[@id='tubes-grid']//table//tr[1]//input[@name='option_4']")
	private WebElement txtOption4;

	@FindBy(xpath = "// *[@id='tubes-grid']//table//tr[1]//input[@name='option_5']")
	private WebElement txtOption5;

	@FindBy(xpath = "// *[@id='tubes-grid']//table//tr[1]//input[@name='option_6']")
	private WebElement txtOption6;

	@FindBy(xpath = "// *[@id='tubes-grid']//table//tr[1]/td[@data-container-for='type_1']/span")
	private WebElement drpType1;

	@FindBy(xpath = "// *[@id='tubes-grid']//table//tr[1]/td[@data-container-for='type_2']/span")
	private WebElement drpType2;

	@FindBy(xpath = "// *[@id='tubes-grid']//table//tr[1]/td[@data-container-for='type_3']/span")
	private WebElement drpType3;

	@FindBy(xpath = "// *[@id='tubes-grid']//table//tr[1]/td[@data-container-for='type_4']/span")
	private WebElement drpType4;

	@FindBy(xpath = "// *[@id='tubes-grid']//table//tr[1]/td[@data-container-for='type_5']/span")
	private WebElement drpType5;

	@FindBy(xpath = "// *[@id='tubes-grid']//table//tr[1]/td[@data-container-for='type_6']/span")
	private WebElement drpType6;

	@FindBy(xpath = "//*[@id='tubes-grid']//a[./text()='Update']")
	private WebElement btnUpdate;

	@FindBy(xpath = "//*[@id='tubes-grid']//a[./text()='Cancel']")
	private WebElement btnCancelUpdate;

	private WebActions action;

	public TubeProductPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.action = new WebActions(driver);
	}

	public void goToPage() {
		action.click(By.xpath("//*[@id='proair-nav-bar']/ul/li[./a/text()='Products: Tubes']"));
		action.waitForAngular();
		action.waitForAngular();
		action.waitForAngular();
	}

	public void addProduct(String lang, String prodNum, String prodName) {
		Log.logInfo("Add tube product: ");
		action.click(btnSaveCat);
		action.waitForAngular();
		action.selectKendoDropdown(drpLanguage, lang);
		action.type(txtProdNum, prodNum);
		action.type(txtProdName, prodName);
		fillNonRequired();
		action.click(btnUpdate);
		action.waitForAngular();
	}

	public void editProduct(String oldProdName, String newProdName, String newProdNum) {
		Log.logInfo("Edit tube product: " + oldProdName);
		clickEditProduct(oldProdName);
		if (!newProdName.equals("")) {
			action.type(txtProdName, newProdName);
		}
		if (!newProdNum.equals("")) {
			action.type(txtProdNum, newProdNum);
		}
		action.click(btnUpdate);
		action.waitForAngular();
	}

	public void fillNonRequired() {
		action.type(txtField1, "Field 1");
		action.selectKendoDropdown(drpType1, "Text");
		action.type(txtField2, "Field 2");
		action.selectKendoDropdown(drpType2, "Numeric");
		action.type(txtField3, "Field 3");
		action.selectKendoDropdown(drpType3, "Dropdown");
		action.type(txtOption3, "option 1, option 2");

		action.type(txtField5, "Field 5");
		action.selectKendoDropdown(drpType5, "Numeric");
		action.type(txtField6, "Field 6");
		action.selectKendoDropdown(drpType6, "Dropdown");
		action.type(txtOption6, "option 2, option 4");
		action.type(txtField4, "Field 4");
		action.selectKendoDropdown(drpType4, "Text");
	}

	public boolean isProductExisting(String prodName) {
		return action.isDisplayed(By.xpath("//*[@id='tubes-grid']//tr[.//text()='" + prodName + "']"));
	}

	public boolean isProductNotExisting(String prodName) {
		return action.isNotDisplayed(By.xpath("//*[@id='tubes-grid']//tr[.//text()='" + prodName + "']"));
	}

	public void clickEditProduct(String prodName) {
		action.click(By.xpath("//*[@id='tubes-grid']//tr[.//text()='" + prodName + "']//a[./text()='Edit']"));
	}

	public void deleteProduct(String prodName) {
		Log.logInfo("Delete product: " + prodName);
		action.click(By.xpath("//*[@id='tubes-grid']//tr[.//text()='" + prodName + "']//a[./text()='Delete']"));
		action.confirmPopup(true);
	}
}
