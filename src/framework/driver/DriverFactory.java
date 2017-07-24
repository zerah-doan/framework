package framework.driver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import framework.config.Config;
import framework.device.Devices;
import io.appium.java_client.android.AndroidDriver;
import util.Log;

public class DriverFactory {
	private WebDriver webDriver;
	private AndroidDriver<WebElement> androidDriver;
	private static ThreadLocal<WebDriver> localThread = new ThreadLocal<WebDriver>();

	public WebDriver getWebDriver(String browser, String url) {
		switch (browser) {
		case "chrome":
		case "gc":
		default:
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-extensions");
			System.setProperty("webdriver.chrome.driver", Config.CHROME_DRIVER_PATH);
			webDriver = new ChromeDriver(options);
			break;
		case "firefox":
		case "ff":
			webDriver = new FirefoxDriver();
			break;
		case "safari":
			webDriver = new SafariDriver();
			break;
		}
		webDriver.manage().timeouts().implicitlyWait(Config.IMPLICIT_WAIT, TimeUnit.SECONDS);
		webDriver.manage().timeouts().pageLoadTimeout(Config.PAGELOAD_TIMEOUT, TimeUnit.SECONDS);
		webDriver.manage().window().maximize();
		localThread.set(webDriver);
		webDriver = localThread.get();
		webDriver.get(url);
		Log.logInfo("Openning " + url + " with " + browser + " browser");
		return webDriver;
	}

	public void removeWebDriver() {
		localThread.get().quit();
		localThread.remove();
	}

	public AndroidDriver<WebElement> getAndroidDriver(String device) {
		DesiredCapabilities cap = new Devices(device).getCap();
		try {
			androidDriver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), cap);
			androidDriver.manage().timeouts().implicitlyWait(Config.IMPLICIT_WAIT, TimeUnit.SECONDS);

		} catch (MalformedURLException e) {
			Log.logWarn(e.getMessage());
		}
		return androidDriver;
	}
}
