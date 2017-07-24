package framework.action;

import org.openqa.selenium.WebElement;

import io.appium.java_client.NoSuchContextException;
import io.appium.java_client.android.AndroidDriver;
import util.Log;

public class WebViewActions extends WebActions {
	AndroidDriver<WebElement> android;

	public WebViewActions(AndroidDriver<WebElement> driver) {
		super(driver);
		this.android = driver;
	}

	public void switchContext(String ctx) {
		try {
			android.context(ctx);
		} catch (NoSuchContextException e) {
			super.sleep(5);
			android.context(ctx);
		}
		Log.logInfo("Switch to context " + ctx);
	}
}
