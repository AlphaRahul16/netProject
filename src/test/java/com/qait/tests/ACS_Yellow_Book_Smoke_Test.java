package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	List<String> customerList	= new ArrayList<String>();
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
	
	@Test
	public void Step05_Verify_User_Navigated_To_Individual_Profile_Page_On_Clicking_Customer_Name_Link_And_Fetch_ACS_Member_Number_And_Last_Name(){
		test.homePageIWEB.clickOnFindNominationTab();
		customerList = test.memberShipPage.getCustomerLastNameAndContactIDForYellowBook();
		test.launchApplication(app_url_eweb_yb);
	}
	
	@Test
	public void Step06_Launch_Eweb_Application_For_Yellow_Book_And_Login_With_LastName_And_Member_ID_Than_Verify_User_Is_On_Home_Page(){
		test.launchApplication(app_url_eweb_yb);
		test.acsYellowBookEwebPage.loginWithLastNameAndMemberId(customerList.get(0),customerList.get(1));
		test.acsYellowBookEwebPage.verifyUserIsOnHomePageOfYellowBookEweb();
	}
	
	@Test
	public void Step07_Update_Address_Field_And_Verify_On_Home_Page_Of_Yellow_Book_Eweb(){
		test.acsYellowBookEwebPage.clickOnLinkOnHomePageYBEweb("contact");
		test.acsYellowBookEwebPage.verifyUserNavigatedToParticularPage("State: Update My Yellow Book Contact Info");
		test.acsYellowBookEwebPage.UpdateAddessField(mapSheetData.get("Address field 2"));
		test.acsYellowBookEwebPage.clickOnCheckBoxAndThanClickOnSubmitButton(mapSheetData.get("checkbox?"));
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
