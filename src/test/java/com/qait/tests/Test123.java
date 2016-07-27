package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;
import com.qait.keywords.YamlInformationProvider;

public class Test123 {
	String app_url="http://69.32.200.222/ilrn/resource/resource.do#/activity/book/LIFE_H_US_C/activity/350070084";
    WebDriver driver;
	
	@Test
	public void Step01_Navigate_To_Reports_Module() throws InterruptedException
	{
		Actions action = new Actions(driver);
		driver.findElement(By.xpath(".//*[@id='dynamicFieldLogin']/input")).click();
		driver.findElement(By.xpath(".//*[@id='dynamicFieldLogin']/input")).sendKeys("test_r1_rls4.12_s6");
		driver.findElement(By.xpath(".//*[@id='dynamicFieldPasswd']/input")).click();
		driver.findElement(By.xpath(".//*[@id='dynamicFieldPasswd']/input")).sendKeys("password");
		driver.findElement(By.xpath(".//*[@id='signin-btn']")).click();
		Thread.sleep(4000);
		driver.get(app_url);
		Thread.sleep(6000);
		WebElement frame = driver.findElement(By.id("activity_container"));
		Thread.sleep(2000);
			driver.switchTo().frame(frame);
			Thread.sleep(2000);
		driver.findElement(By.xpath(".//*[@id='content']/div[5]/div[2]/div[2]/button[1]")).click();
		Thread.sleep(5000);
		WebElement source=  driver.findElement(By.xpath("(//li[@class='sentence'])[1]")); 
		Thread.sleep(2000);
		WebElement target = driver.findElement(By.xpath("(//li[@class='sentence'])[6]/preceding-sibling::li[1]"));
		Thread.sleep(4000);
		action.dragAndDrop(source, target).perform();
	}

	
	@BeforeClass
	public void open_Browser_Window() 
	{
	driver = new FirefoxDriver();
	driver.get(app_url);

	
	    

	}



}
