package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;

public class ACS_Batch_Processing {
	TestSessionInitiator test;
	String app_url_iweb;
	
	@Test
	public void Step_01()
	{
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Accounting");
		test.homePageIWEB.clickOnSideBarTab("Batch");
		test.homePageIWEB.clickOnTab("Query Batch");
		test.memberShipPage.selectAndRunQuery("Selenium: Batch processing test");
	}
	
	@Test
	public void Step_02()
	{
		test.acsbatchProcessing.clickEditButtonOnBatchProcessingPage();
		test.acsbatchProcessing.enterDetailsForBatchProcessing();
		test.acsbatchProcessing.verifyDetailsOnBatchSummaryInfo("total");
		test.acsbatchProcessing.verifyDetailsOnBatchSummaryInfo("count");
	}
	
	
	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result)
	{
		test.takescreenshot.takeScreenShotOnException(result);
	

	}
	@BeforeClass
	public void open_Browser_Window()
	{
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_iweb =getYamlValue("app_url_IWEB");
		test.launchApplication(app_url_iweb);
	}

	@AfterClass
	public void close_Browser_Window()
	{
	
		test.closeBrowserWindow();
	}
}
