package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.XlsReader;
import com.qait.automation.utils.YamlReader;

public class ACS_Scarf_Reporting {
	static String sheetName;
	TestSessionInitiator test;
	String app_url_iweb, custId;
	List<Integer> rowNumberList = new ArrayList<Integer>();
	HashMap<String, String> dataList = new HashMap<String, String>();
	private int caseID;

	int i, j;
	
	public ACS_Scarf_Reporting() {
		sheetName=com.qait.tests.DataProvider_FactoryClass.sheetName = "ScarfReporting";
	}


	@Factory(dataProviderClass = com.qait.tests.Data_Provider_Factory_Class_Xml.class, dataProvider = "data")
	public ACS_Scarf_Reporting(int caseID) {
		this.caseID = caseID;
	}
	
	@BeforeClass
	public void open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		System.out.println("Row Num::"+caseID);
		dataList = XlsReader.addValuesInTheMapForExcel(sheetName, caseID);
		app_url_iweb = getYamlValue("app_url_IwebReporting");
	}
	
	@Test
	public void Step01_Launch_Iweb_Application(){
		test.launchApplication(app_url_iweb);
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
//		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
//				YamlReader.getYamlValue("Authentication.password"));  //chrome
	
	}
	
	@Test
	public void Step02_Verify_User_Navigated_To_Scarf_Reporting_Page(){
		Reporter.log("CASE ID : " + caseID, true);
		test.homePageIWEB.clickOnModuleTab(); 
		test.homePageIWEB.clickOnSacrfReportingModule();
		test.homePageIWEB.verifyUserIsOnHomePage("Scarf Reporting | Overview | Student Chapter Reporting Setup");
	}
	
	

}
