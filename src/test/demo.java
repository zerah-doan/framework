package test;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import framework.driver.DriverFactory;

public class demo extends BaseTest {

	@Test
	public void f1() {
		// driver = cmon.openBrowser("gc", "https://www.google.com.vn/");
		driver = new DriverFactory().getWebDriver("gc", "https://www.google.com.vn/");
		System.out.println(driver.getTitle());
		driver.findElement(By.xpath("//*[@id='sb_ifc0']")).sendKeys("aaaaa");
		driver.findElement(By.xpath("//*[@id='tsf']/div[2]/div[3]/center/input[1]")).click();
	}

	@Test
	public void f2() {
		driver = new DriverFactory().getWebDriver("gc", "https://vn.yahoo.com/");
		System.out.println(driver.getTitle());
		driver.findElement(By.xpath("//*[@id='UHSearchBox']")).sendKeys("aaaaa");
		driver.findElement(By.xpath("//*[@id='UHSearchWeb']")).click();

	}

	@Test
	public void f3() {
		driver = new DriverFactory().getWebDriver("gc", "http://www.samsung.com/us/");
		System.out.println(driver.getTitle());
		driver.findElement(By.xpath("//*[@id='inner-wrap']/div/div[2]/div[1]/div/div/div[1]/div/div/a")).click();
	}

	@Test
	public void f4() {
		driver = new DriverFactory().getWebDriver("chrome", "https://www.microsoft.com/vi-vn/");
		System.out.println(driver.getTitle());
		driver.findElement(By.xpath("//*[@id='work1']/a/span")).click();
	}

	@BeforeMethod
	public void beforeMethod() {
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod() {
		new DriverFactory().removeWebDriver();

	}

}
