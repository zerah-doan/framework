package test;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import framework.driver.DriverFactory;

public class demo3 extends BaseTest {

	@Test
	public void f2() {
		System.out.println(driver.getTitle());
		driver.findElement(By.xpath("//*[@id='UHSearchBox']")).sendKeys("aaaaa");
		driver.findElement(By.xpath("//*[@id='UHSearchWeb']")).click();

	}

	@BeforeMethod
	public void beforeMethod() {
		driver = new DriverFactory().getWebDriver("gc", "https://vn.yahoo.com/");
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod() {
		new DriverFactory().removeWebDriver();

	}

}
