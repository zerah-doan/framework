package framework.action;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import framework.config.Config;
import io.appium.java_client.android.AndroidDriver;
import util.Log;

public class WebActions {
	private WebDriver driver;
	private WebDriverWait wait;
	JavascriptExecutor executor;

	public WebActions(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Config.TIME_OUT, Config.SLEEP_BETWEEN_POLL);
		this.executor = (JavascriptExecutor) driver;
	}

	public WebActions(AndroidDriver<WebElement> driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Config.TIME_OUT, Config.SLEEP_BETWEEN_POLL);
		this.executor = (JavascriptExecutor) driver;
	}

	public WebElement findElement(By by) {
		waitUntilVisible(by);
		return driver.findElement(by);
	}

	public List<WebElement> findElements(By by) {
		waitUntilVisible(by);
		return driver.findElements(by);
	}

	public void click(WebElement ele) {
		waitUntilVisible(ele);
		String tagName = ele.getTagName();
		String txt = ele.getText().replace("\n", " ");
		try {
			ele.click();
		} catch (WebDriverException e) {
			ele.click();
		}
		Log.logInfo("------Click " + txt + " " + tagName);
	}

	public void click(By by) {
		WebElement ele = findElement(by);
		try {
			click(ele);
		} catch (StaleElementReferenceException e) {
			ele = findElement(by);
			click(ele);
		}
	}

	public void type(WebElement ele, String txt) {
		waitUntilVisible(ele);
		try {
			ele.clear();
		} catch (InvalidElementStateException e) {
		}
		if (!txt.equals("")) {
			ele.sendKeys(txt);
			Log.logInfo("------Type " + txt + " to " + ele.getTagName());
		}
	}

	public void type(By by, String txt) {
		WebElement ele = findElement(by);
		type(ele, txt);
	}

	public String getText(WebElement ele) {
		Log.logInfo("------Get text of " + ele.getTagName() + " = " + ele.getText());
		return ele.getText();
	}

	public String getText(By by) {
		try {
			WebElement ele = findElement(by);
			return getText(ele);
		} catch (NoSuchElementException e) {
			Log.logInfo("------Get text of non-existing element => return empty string");
			return "";
		}
	}

	public void selectByVisibleText(Select ele, String txt) {
		if (!txt.equals("")) {
			ele.selectByVisibleText(txt);
			Log.logInfo("------Select " + txt);
		}
	}

	public void selectByVisibleText(By by, String txt) {
		Select ele = new Select(findElement(by));
		selectByVisibleText(ele, txt);
	}

	public void selectDropdown(WebElement ele, String val) {
		Log.logInfo("---Select dropdown: " + val);
		click(ele);
		click(ele.findElement(By.xpath("./../ul/li[.//text()='" + val + "']")));
	}

	public void selectDropdown(WebElement ele, int index) {
		Log.logInfo("---Select dropdown by index: " + index);
		click(ele);
		click(ele.findElement(By.xpath("./../ul/li[" + index + "]")));
	}

	public void selectKendoDropdown(WebElement ele, String val) {
		Log.logInfo("---Select dropdown: " + val);
		click(ele);
		click(By.xpath("//ul[@aria-hidden='false']/li[.//text()='" + val + "']"));
	}

	public void waitForAngular() {
		sleep(1);
		((JavascriptExecutor) driver).executeScript(
				"angular.element(document.body).injector().get('$browser').notifyWhenNoOutstandingRequests(function(){console.log('Done loading!');})");
	}

	public void waitUntilVisible(WebElement ele) {
		wait.until(ExpectedConditions.visibilityOf(ele));
	}

	public void waitUntilVisible(By by) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	public void waitUntilInvisible(By by) {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}

	public boolean isDisplayed(By by) {
		try {
			waitUntilVisible(by);
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean isNotDisplayed(By by) {
		try {
			waitUntilInvisible(by);
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public void sleep(Integer s) {
		try {
			Thread.sleep(s * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void confirmPopup(boolean selection) {
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		if (selection) {
			alert.accept();
			Log.logInfo("------Accept alert popup");
		} else {
			alert.dismiss();
			Log.logInfo("------Dismiss alert popup");
		}
	}

	public void scrollIntoView(WebElement ele) {
		executor.executeScript("arguments[0].scrollIntoView();", ele);
	}

	public void executeScript(String script) {
		executor.executeScript(script);
	}

	public void typeNumbericBox(WebElement eleClicked, WebElement eleTyped, String txt) {
		Log.logInfo("---Type to numbernic box: ");
		click(eleClicked);
		waitForAngular();
		type(eleTyped, txt);
	}

	public void checkCheckbox(WebElement ele, boolean state) {
		if (state) {
			if (!ele.isSelected()) {
				ele.click();
			}
			Log.logInfo("------Check checkbox");
		} else {
			if (ele.isSelected()) {
				ele.click();
			}
			Log.logInfo("------Uncheck checkbox");
		}
		waitForAngular();
	}

	public void checkCheckbox(By by, boolean state) {
		WebElement ele = findElement(by);
		checkCheckbox(ele, state);
	}
}
