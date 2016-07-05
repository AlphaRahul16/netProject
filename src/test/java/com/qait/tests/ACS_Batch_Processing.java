package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;
import com.thoughtworks.selenium.webdriven.commands.IsEditable;

public class ACS_Batch_Processing {
	TestSessionInitiator test;
	String app_url_iweb;
	String batchName;
	int numberOfDivisions;
	

	@Test
	public void Step_01_Navigate_To_Accounting_Module_And_Click_Query_Batch()
	{
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Accounting");
		test.homePageIWEB.clickOnSideBarTab("Batch");
	}
	
	@Test
	public void Step_02_Select_And_Run_Batch_Processing_Query()
	{
		test.homePageIWEB.clickOnTab("Query Batch");
		test.memberShipPage.selectAndRunQuery("Selenium: Batch processing test");
	}
	
	
	@Test
	public void Step_03_Edit_Batch_Control_Details_And_Verify_Total_And_Count()
	{
		test.acsbatchProcessing.clickEditButtonOnBatchProcessingPage();
		test.acsbatchProcessing.enterDetailsForBatchProcessingAndClickSaveButton();
		test.acsbatchProcessing.verifyDetailsOnBatchSummaryInfo("total");
		test.acsbatchProcessing.verifyDetailsOnBatchSummaryInfo("count");
	}
	
	@Test
	public void Step_04_Press_Batch_Processing_Buttons_And_verify_PopUp_Window()
	{

	test.acsbatchProcessing.clickOnBatchProcessButtonsAndVerifyPopUpWindowAppears();
	}
	
	@Test
	public void Step_05_Verify_Batch_Details_After_Processing_Is_Completed()
	{

	test.acsbatchProcessing.verifyBatchDetailsAfterProcessing();
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
