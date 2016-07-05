package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;

public class ACS_ReturnCancelRefund {
	TestSessionInitiator test;
	String app_url_IWEB;
	
	@Test
	public void Step01_TC01_launch_Iweb()
	{
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Accounting");
		test.homePageIWEB.clickOnSideBarTab("Batch");
		test.homePageIWEB.clickOnTab("Find Batch");
		test.acsbatchProcessing.searchStoreBatchesOnFindBatchPage();
		test.individualsPage.clickGoButton();
		test.memberShipPage.selectARandomActiveStudentChapter();
		
	
		
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
		app_url_IWEB =getYamlValue("app_url_IWEB");
		test.launchApplication(app_url_IWEB);
		test.homePage.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"), YamlReader.getYamlValue("Authentication.password"));
	}

	@AfterClass
	public void close_Browser_Window()
	{

		test.closeBrowserWindow();
	}

}
