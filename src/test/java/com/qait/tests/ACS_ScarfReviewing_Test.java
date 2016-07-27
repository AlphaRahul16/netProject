package com.qait.tests;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;
import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.XlsReader;
import com.qait.automation.utils.YamlReader;

public class ACS_ScarfReviewing_Test {
	static String sheetName;
	String app_url_eweb_rev;
	TestSessionInitiator test;
	HashMap<String, String> ReviewMap = new HashMap<String, String>();

	String app_url_iweb;

	private int caseID;
	
	public ACS_ScarfReviewing_Test() {
		sheetName = com.qait.tests.Data_Provider_Factory_Class_Xml.sheetName = "ScarfReviewing";
	}
	
	@Factory(dataProviderClass = com.qait.tests.Data_Provider_Factory_Class_Xml.class, dataProvider = "data")
	public ACS_ScarfReviewing_Test(int caseID) {
		this.caseID = caseID;
	}
	
	@BeforeClass
	public void open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		ReviewMap = XlsReader.addValuesInTheMapForExcel(sheetName, caseID);
		app_url_iweb = getYamlValue("app_url_IWEB");
	}
	
	@Test
	public void Step01_Launch_Iweb_Application() {
		test.launchApplication(app_url_iweb);
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}
	
	@Test
	public void Step02_User_Navigated_To_Student_Chapter_Reporting_Link() {
		Reporter.log("CASE ID : " + caseID, true);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnSacrfReportingModule();
		test.homePageIWEB.verifyUserIsOnHomePage("Scarf Reporting | Overview | Student Chapter Reporting Setup");
		test.acsScarfReporting.clickOnStudentChapterReportingLink();
		test.memberShipPage.expandDetailsMenu("academic year");
	}
	
	@Test
	public void Step03_User_Reviewing_Date_Is_Open_In_Current_Year_For_All_Reviewing_Modes()
	{
		
	}
	
	
	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result)
	{
		test.takescreenshot.takeScreenShotOnException(result);

	}
	
//	@AfterClass
	public void close_Browser_Window()
	{
	
		test.closeBrowserWindow();
	}
	
}
