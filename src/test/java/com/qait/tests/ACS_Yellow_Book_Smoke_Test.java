package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.ArrayList;
import java.util.List;
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
	boolean value;

	List<String> customerList = new ArrayList<String>();
	List<String> committeesList = new ArrayList<String>();
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
		/*test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));*/
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test
	public void Step02_Verify_User_Lands_On_Committes_Page_On_Clicking_Committee_Under_Modules_Tab() {
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Committee");
		test.homePageIWEB.verifyUserIsOnHomePage("Committee | Overview | Overview and Setup");
		test.homePageIWEB.clickOnCommitteeSetupPageLink();
		value = test.individualsPage.verifyCommitteePreferenceDate();
	}

	@Test
	public void Step03_Verify_User_Is_On_Query_Committee_Participation_Page_On_Clicking_Query_Committee_Participation_Under_Participation_From_Left_Panel() {
		test.homePageIWEB.clickOnTab("Participation");
		test.homePageIWEB.clickOnTab("Query Committee Participation");
		test.homePageIWEB.verifyUserIsOnHomePage("Committee | Participation | Query - Committee Participation");
	}

	@Test
	public void Step04_Select_And_Run_Query_Than_Verify_User_Is_On_Committee_Participation_Profile_Page() {
		test.memberShipPage.selectAndRunQuery(YamlReader.getYamlValue("Committee_Query"));
		test.homePageIWEB.verifyUserIsOnHomePage("Committee | Participation | Committee Participation Profile");
	}

	@Test
	public void Step05_Verify_User_Navigated_To_Individual_Profile_Page_On_Clicking_Customer_Name_Link_And_Fetch_ACS_Member_Number_And_Last_Name() {
		test.homePageIWEB.clickOnFindNominationTab();
		customerList = test.memberShipPage.getCustomerLastNameAndContactIDForYellowBook();
		test.launchApplication(app_url_eweb_yb);
	}

	@Test
	public void Step06_Launch_Eweb_Application_For_Yellow_Book_And_Login_With_LastName_And_Member_ID_Than_Verify_User_Is_On_Home_Page() {
		test.acsYellowBookEwebPage.loginWithLastNameAndMemberId(customerList.get(0), customerList.get(1));
		test.acsYellowBookEwebPage.verifyUserIsOnHomePageOfYellowBookEweb();
	}

	@Test
	public void Step07_Update_Address_Field_And_Verify_On_Home_Page_Of_Yellow_Book_Eweb() {
		test.acsYellowBookEwebPage.clickOnLinkOnHomePageYBEweb("contact");
		test.acsYellowBookEwebPage.verifyUserNavigatedToParticularPage("Update My Yellow Book Contact Info");
		test.acsYellowBookEwebPage.updateAddessField(test.homePageIWEB.map().get("Address field 2"));
		test.acsYellowBookEwebPage.clickOnCheckBoxAndThanClickOnSubmitButton(test.homePageIWEB.map().get("checkbox?"));
		test.acsYellowBookEwebPage
				.verifyUpdatedAddressOnYellowBookHomePage(test.homePageIWEB.map().get("Address field 2"));
	}

	@Test
	public void Step08_Click_On_My_Biography_Link_And_Update_Biography_Fields_Than_Verify_Updated_Biography_On_Yellow_Book_Eweb_Home_Page() {
		test.acsYellowBookEwebPage.clickOnLinkOnHomePageYBEweb("biography");
		test.acsYellowBookEwebPage.verifyUserNavigatedToParticularPage("Update My Yellow Book Biography");
		test.acsYellowBookEwebPage.updateBiographyFields(test.homePageIWEB.map().get("Biography Honor field"));
		test.acsYellowBookEwebPage
				.verifyUpdatedBiographyFieldOnYellowBookHomePage(test.homePageIWEB.map().get("Biography Honor field"));
	}

	@Test
	public void Step09_Verify_Presence_Of_edit_Link() {
		test.acsYellowBookEwebPage.verifyEditLinkIsDisplayed(value);
	}

	@Test
	public void Step09_Click_On_Committe_Preferences_Link_And_Choose_Four_Random_Committees_And_Verify_Selected_Committees_On_Yellow_Book_Eweb_Home_Page() {
		test.acsYellowBookEwebPage.clickOnLinkOnHomePageYBEweb("committees");
		committeesList = test.acsYellowBookEwebPage
				.selectFourRandomCommitteesFromCommitteesPreferencesPage(test.homePageIWEB.map().get("checkbox?"));
		test.acsYellowBookEwebPage.clickOnContinueButton(test.homePageIWEB.map().get("checkbox?"));
	}

	@Test
	public void Step10_Fill_The_Test_Data_In_Selected_Committee_Input_Box_And_Verify_Selected_Committees_On_Home_Page_Of_Yellow_Book_Eweb() {
		test.acsYellowBookEwebPage.fillTheTestDataInSelectedCommitteeInputBoxAndClickOnSubmitButton();
		test.acsYellowBookEwebPage.verifySelectedCommitteesOnYellowBookEwebHomePage(committeesList);
	}

	@Test
	public void Step11_Launch_Iweb_Application_For_Yellow_Book_And_Verify_Biography_Data() {
		test.launchApplication(app_url_iweb_yb);
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
		test.homePageIWEB.clickOnFindTab();
		test.individualsPage.enterFieldValue("ACS member", customerList.get(1));
		test.individualsPage.clickGoButton();
		test.individualsPage.verifyNomineeAddress(test.homePageIWEB.map().get("Address field 2"));
		test.individualsPage.clickOnAcsBiographyImage();
		test.individualsPage.verifyBioHonorsData(test.homePageIWEB.map().get("Biography Honor field"));
	}

	@Test
	public void Step12_Verify_Committee_Member_Status() {
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Committees");
		test.individualsPage.verifyCommitteeMembersStatus(committeesList);
	}

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		Reporter.log("CASE ID : " + caseID, true);
		test.homePageIWEB.addValuesInMap("yellowBook", caseID);
		app_url_eweb_yb = getYamlValue("app_url_yellowBook");
		app_url_iweb_yb = getYamlValue("app_url_iweb_yb");
		app_url_iweb_nf = getYamlValue("app_url_IWEB");
	}

	@AfterClass
	public void Close_Browser_Session() {
		test.closeBrowserSession();
	}

}
