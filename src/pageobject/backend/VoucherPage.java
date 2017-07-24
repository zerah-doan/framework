package pageobject.backend;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import framework.action.WebActions;
import util.Log;

public class VoucherPage {
	@FindBy(xpath = "//*[@id='proair-nav-bar']//li/a[./text()='Vouchers']")
	private WebElement lnkVoucher;

	@FindBy(xpath = "//a[./text()='Add New Voucher']")
	private WebElement btnAddVoucher;

	@FindBy(xpath = "//*[@id='vouchers-grid']//table//tr//input[@name='code']")
	private WebElement txtVoucherCode;

	@FindBy(xpath = "//*[@id='vouchers-grid']//table//tr/td[.//input[@name='quantity']]//input[@style='display: block;']")
	private WebElement txtVoucherQuantity;

	@FindBy(xpath = "//*[@id='vouchers-grid']//table//tr/td[.//input[@name='discount']]//input[@style='display: block;']")
	private WebElement txtDiscount;

	@FindBy(xpath = "//*[@id='vouchers-grid']//table//tr/td[.//input[@name='free_period_in_month']]//input[@style='display: block;']")
	private WebElement txtFreeMonth;

	@FindBy(xpath = "//*[@id='vouchers-grid']//table//tr//input[@data-bind='value:active_to']")
	private WebElement txtValidTo;

	@FindBy(xpath = "//*[@id='vouchers-grid']//table//tr//a[./text()='Update']")
	private WebElement btnUpdate;

	@FindBy(xpath = "//*[@id='vouchers-grid']//table//tr//a[./text()='Cancel']")
	private WebElement btnCancel;
	@FindBy(xpath = "//*[@id='vouchers-grid']//table//tr//input[@name='status']")
	private WebElement chkStatus;

	private WebActions action;

	public VoucherPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.action = new WebActions(driver);
	}

	public void goToPage() {
		action.click(lnkVoucher);
		action.waitForAngular();
		action.waitForAngular();
	}

	public void addNewVoucher(String code, String quantity, String discount, String freeMonth, String activeTo) {
		Log.logInfo("Add new voucher");
		action.click(btnAddVoucher);
		fillVoucherInfo(code, quantity, discount, freeMonth, activeTo);
		action.click(btnUpdate);
		action.waitForAngular();
	}

	public void changeStatus(String code, boolean status) {
		Log.logInfo("Change voucher status");
		action.checkCheckbox(
				By.xpath(
						"//*[@id='vouchers-grid']//table//tr[./td[.//text()='" + code + "']]//input[@type='checkbox']"),
				status);

	}

	public boolean isVoucherExisting(String code) {
		return action.isDisplayed(By.xpath("//*[@id='vouchers-grid']//table//tr[./td[.//text()='" + code + "']]"));
	}

	public boolean getVoucherStatus(String code) {
		return action
				.findElement(By.xpath(
						"//*[@id='vouchers-grid']//table//tr[./td[.//text()='" + code + "']]//input[@type='checkbox']"))
				.isSelected();
	}

	public void fillVoucherInfo(String code, String quantity, String discount, String freeMonth, String activeTo) {
		action.type(txtVoucherCode, code);
		action.typeNumbericBox(txtVoucherQuantity, txtVoucherQuantity, quantity);
		if (discount.equals("")) {
			action.typeNumbericBox(txtFreeMonth, txtFreeMonth, freeMonth);
		} else {
			action.typeNumbericBox(txtDiscount, txtDiscount, discount);
		}
		action.type(txtValidTo, activeTo);
	}
}
