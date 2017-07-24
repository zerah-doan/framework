package pageobject.backend;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import framework.action.WebActions;
import framework.action.WebViewActions;
import io.appium.java_client.android.AndroidDriver;

public class BasePage<T> {
	WebDriver wd;
	AndroidDriver<WebElement> ad;
	WebActions wActions;
	WebViewActions wVActions;

	protected BasePage(WebDriver driver) {
		this.wd = driver;
	}

	protected BasePage(AndroidDriver<WebElement> driver) {
		this.ad = driver;
	}
}
