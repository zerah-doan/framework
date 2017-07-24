package test;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import framework.driver.DriverFactory;

public class demo2 extends BaseTest {

	@Test
	public void f1() {
		System.out.println(driver.getTitle());
		driver.findElement(By.xpath("//*[@id='sb_ifc0']")).sendKeys("aaaaa");
		driver.findElement(By.xpath("//*[@id='tsf']/div[2]/div[3]/center/input[1]")).click();
	}

	@BeforeMethod
	public void beforeMethod() {
		driver = new DriverFactory().getWebDriver("gc", "https://www.google.com.vn/");
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod() {
		new DriverFactory().removeWebDriver();

	}

}
