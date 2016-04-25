package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.HashMap;
import java.util.Map;

import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;

public class ACS_Yellow_Book_Smoke_Test {

	TestSessionInitiator test;
	String app_url_eweb_yb;
	String app_url_iweb_yb;
	String app_url_iweb_nf;
	Map<String, String> mapSheetData = new HashMap<String, String>();
	private String caseID;

	public ACS_Yellow_Book_Smoke_Test() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "yellowBook";
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_Yellow_Book_Smoke_Test(String caseID) {
		this.caseID = caseID;
	}

	@Test
	public void Step01_Launch_Iweb_Application_For_Yellow_Book() {
		test.launchApplication(app_url_iweb_yb);
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test
	public void Step02_Verify_User_Lands_On_Committes_Page_On_Clicking_Committee_Under_Modules_Tab(){
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Committee");
		test.homePageIWEB.verifyUserIsOnHomePage("Committee | Overview | Overview and Setup");
	}
	
	@Test
	public void Step03_Verify_User_Is_On_Query_Committee_Participation_Page_On_Clicking_Query_Committee_Participation_Under_Participation_From_Left_Panel(){
		test.homePageIWEB.clickOnTab("Participation");
		test.homePageIWEB.clickOnTab("Query Committee Participation");
		test.homePageIWEB.verifyUserIsOnHomePage("Committee | Participation | Query - Committee Participation");
	}
	
	@Test
	public void Step04_Select_And_Run_Query_Than_Verify_User_Is_On_Committee_Participation_Profile_Page(){
		test.memberShipPage.selectAndRunQuery(YamlReader.getYamlValue("Committee_Query"));
		test.homePageIWEB.verifyUserIsOnHomePage("Committee | Participation | Committee Participation Profile");
	}
	
	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		Reporter.log("CASE ID : " + caseID, true);
		mapSheetData = test.homePageIWEB.addValuesInMap("yellowBook", caseID);
		app_url_eweb_yb = getYamlValue("app_url_yellowBook");
		app_url_iweb_yb = getYamlValue("app_url_iweb_yb");
		app_url_iweb_nf = getYamlValue("app_url_IWEB");
	}

	@AfterClass
	public void Close_Browser_Session() {
		test.closeBrowserSession();
	}

}
