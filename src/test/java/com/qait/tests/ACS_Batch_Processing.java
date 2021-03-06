package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_Batch_Processing extends BaseTest {

	String app_url_iweb;
	String batchName;
	int numberOfDivisions;
	List<String> batchValues = new ArrayList<String>();

	@Test
	public void Step_01_Navigate_To_Accounting_Module_And_Click_Query_Batch() {
		test.launchApplication(app_url_iweb);
		test.homePageIWEB.enterAuthentication(
				YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Accounting");
		test.homePageIWEB.clickOnSideBarTab("Batch");
	}

	@Test(dependsOnMethods="Step_01_Navigate_To_Accounting_Module_And_Click_Query_Batch")
	public void Step_02_Select_And_Run_Batch_Processing_Query() {
		test.homePageIWEB.clickOnTab("Query Batch");
		test.memberShipPage
				.selectAndRunQuery("Selenium: Batch processing test");
	}

	@Test(dependsOnMethods="Step_02_Select_And_Run_Batch_Processing_Query")
	public void Step_03_Edit_Batch_Control_Details_And_Verify_Total_And_Count() {
		test.acsbatchProcessing.clickEditButtonOnBatchProcessingPage();
		batchValues = test.acsbatchProcessing
				.enterDetailsForBatchProcessingAndClickSaveButton();
		test.acsbatchProcessing.verifyDetailsOnBatchSummaryInfo("total",
				batchValues.get(0));
		test.acsbatchProcessing.verifyDetailsOnBatchSummaryInfo("count",
				batchValues.get(1));
	}

	@Test(dependsOnMethods="Step_03_Edit_Batch_Control_Details_And_Verify_Total_And_Count")
	public void Step_04_Press_Batch_Processing_Buttons_And_verify_PopUp_Window() {

		test.acsbatchProcessing
				.clickOnBatchProcessButtonsAndVerifyPopUpWindowAppears();
	}

	@Test(dependsOnMethods="Step_04_Press_Batch_Processing_Buttons_And_verify_PopUp_Window")
	public void Step_05_Verify_Batch_Details_After_Processing_Is_Completed() {

		test.acsbatchProcessing.verifyBatchDetailsAfterProcessing();
	}

	@BeforeClass
	public void open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_iweb = getYamlValue("app_url_IWEB");
	}
	@BeforeMethod
	public void handleTestMethodName(Method method) {
		test.printMethodName(method.getName());
	}

}
