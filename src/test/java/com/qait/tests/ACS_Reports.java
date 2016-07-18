package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

public class ACS_Reports {
	TestSessionInitiator test;
	String app_url_IWEB;
	static String sheetname;
	private String caseID;
	static YamlInformationProvider getReportsDetails;
	Map<String, Object> mapReportDetails;


	/**
	 * @author rahulyadav
	 */
	
	
	@Test
	public void Step01_Navigate_To_Reports_Module()
	{
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));		
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Reports");

	}

	@Test(dataProvider = "ReportsData")
	public void Step02_Test_Reports_For_All_Delivery_Methods(String DeliveryMethod)
	{
		test.acsreportPage.selectModulesAndCategoryonReportCentralPage(getReportsDetails.get_ACSReportsInfo("module"),
		getReportsDetails.get_ACSReportsInfo("category"),DeliveryMethod);
		test.acsreportPage.clickGoReportButtonForReport(getReportsDetails.get_ACSReportsInfo("report_Name"));
		test.acsreportPage.enterEmailDetailsForScheduleReport(DeliveryMethod,getReportsDetails.get_ACSReportsInfo("email_To"),getReportsDetails.get_ACSReportsInfo("email_Message"));
		test.acsreportPage.verifyReceivedReport(DeliveryMethod,getReportsDetails.get_ACSReportsInfo("report_Heading"));
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
		mapReportDetails = YamlReader.getYamlValues("Acs_Reports");
		getReportsDetails=new YamlInformationProvider(mapReportDetails);
		test.launchApplication(app_url_IWEB);

	}


	@DataProvider(name = "ReportsData")
	public static Object[][] Reports_Criteria() {
		
			return new Object[][] {{getReportsDetails.get_ACSReportsInfo("delivery_Method").split(",")[0].trim()}, 
				{getReportsDetails.get_ACSReportsInfo("delivery_Method").split(",")[1].trim()}};
	}

}
