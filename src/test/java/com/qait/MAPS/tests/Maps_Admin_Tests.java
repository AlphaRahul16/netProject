package com.qait.MAPS.tests;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.internal.Yaml;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class Maps_Admin_Tests extends BaseTest {

	private String maps_url, controlId;
	private String[] roles = { "Admin", "Editor"};
	private String[] fieldsNameEditor={"Instructions","Abstract search","Customizable Data Export"};
	private String[] inputCriterias={"Control ID","Title","Contact First Name","Contact Last Name"};
	private String[]dropDownCriterias ={"Abstract Status","Sort Result By","Secondary Sort"};
	private String[] fieldsNameAdmin={"Instructions","Abstract search","People","Customizable Data Export",
			"Reports","Special","View Schedule","Abstract Proof Configuration","Session Proof Configuration","Email","File Export","Data Export","Configuration Settings"};
	private String[] searchDropdownOptions={"Abstract Title","Abstract Contact First or Last Name",
			"Person Email Address","Person First or Last Name"};
	private String[] reviwerRoleOptionsArray = {"Staff Review","Program Chair Review","Review Admin"};

	@BeforeClass
	public void Start_Test_Session() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		maps_url = YamlReader.getYamlValue("MAPS_Url");
		test.launchMAPSApplication(maps_url);
		test.maps_SSOPage.loginWithValidCredentials(YamlReader.getYamlValue("LogIn_Details.userID"),
				YamlReader.getYamlValue("LogIn_Details.password"));
	}

	@BeforeMethod
	public void printCaseIdExecuted(Method method) {
		test.printMethodName(method.getName());
	}

	@Test
	public void Step_005_MAPS_Admin_01_Click_On_Admin_In_Top_Navigation_Menu_And_Verify_Fields() {
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Admin");
		test.maps_SSOPage.verifyUserIsOnTabPage("Admin");
		test.maps_reviewpage.verifyPageHeader("Multiple Role Selection");
		test.maps_sessionpage.verifyApplicationDisplaysRadioButtonOnClickingSessionTab(roles);
		test.maps_sessionpage.verifydropdownOnPopupWindow("Select");
	}
	
	/*@Test
	public void Step_007_MAPS_Admin_02_Verify_Application_Displays_Options_And_Fields_For_Editor() {
		test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("Editor");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Select");
		test.maps_sessionpage.isPrintSelectedButtonDisplayed("Control ID");
		test.maps_adminpage.verifySearchFieldIsDisplayed("SEARCH_FIELD_VALUE", "Search field");
		test.maps_sessionpage.isPrintSelectedButtonDisplayed("Search");
	}
	
	@Test
	public void Step_008_MAPS_Admin_03_Verify_Application_Displays_Options_On_Left_Navigation_Panel_For_Editor() {
		test.maps_adminpage.verifyFieldsOnLeftNavigationPanel(fieldsNameEditor);
	}
	
	@Test
	public void Step_009_MAPS_Admin_04_Verify_Application_Displays_Abstract_Search_And_Search_Criteria_On_Abstract__Search_Page_For_Editor() {
		test.maps_adminpage.clickLeftNavigationPanelOptions(fieldsNameEditor[1]);
		test.maps_adminpage.verifyTitleDescriptionIsDisplayed("Abstract Search");
		test.maps_adminpage.verifyFieldsInSearchCriteriaTable(inputCriterias, dropDownCriterias);
	}
	
	@Test
	public void Step_010_MAPS_Admin_05_Verify_Application_Displays_Dropdown_Values_for_Search_Criteria_For_Editor() {
		test.maps_sessionpage.clickOnSaveButton("Control ID");
		test.maps_sessionpage.verifydropdownOnPopupWindow("Abstract Title");
		test.maps_sessionpage.verifydropdownOnPopupWindow("Abstract Contact First or Last Name");
	}
	
	@Test
	public void Step_017_MAPS_Admin_11_Verify_Application_Displays_Options_And_Fields_For_Admin() {
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Admin");
		test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("Admin");	
		test.maps_sessionpage.clickButtonToContinueToNextPage("Select");
		test.maps_sessionpage.isPrintSelectedButtonDisplayed("Control ID");
		test.maps_adminpage.verifySearchFieldIsDisplayed("SEARCH_FIELD_VALUE", "Search field");
		test.maps_sessionpage.isPrintSelectedButtonDisplayed("Search");
	}
	
	@Test
	public void Step_018_MAPS_Admin_12_Verify_Application_Displays_Options_On_Left_Navigation_Panel_For_Admin() {
		test.maps_adminpage.verifyFieldsOnLeftNavigationPanel(fieldsNameAdmin);
	}
	

    @Test
	public void Step_019_MAPS_Admin_13_Verify_Application_Displays_Dropdown_Values_For_Search_Criteria_For_Admin() {
		test.maps_sessionpage.clickOnSaveButton("Control ID");
		test.maps_sessionpage.verifyLeftPanelOptionsOnSessionAdminPage(searchDropdownOptions);
		test.maps_adminpage.verifyReviewerRoleOptionInReports("Select a reviewer role", reviwerRoleOptionsArray);
	}*/

	
	@Test
	public void Step_039_MAPS_Admin_32_Verify_Application_Adds_New_User_On_Entering_Mandatory_Details() {
		test.maps_adminpage.clickLeftNavigationPanelOptions("People");
		test.maps_adminpage.clickLeftNavigationPanelOptions("add user(s)");
		test.maps_adminpage.enterDetailsToAddNewUserUnderPeople(YamlReader.getYamlValues("Admin.Add_user"));
		test.maps_adminpage.verifyAccountCreationMessage(YamlReader.getYamlValue("Admin.confimation_msg"));	
	}
	
	@Test
	public void Step_047_MAPS_Admin_40_Verify_Application_Displays_Select_Reviewer_Option_And_Diffrent_Tables() {
		test.maps_adminpage.clickLeftNavigationPanelOptions("Reports");
		test.maps_adminpage.clickLeftNavigationPanelOptions("reviewer / role report");
		test.maps_adminpage.verifyTablesPresentInReviewerReportTab();

	}
	
	
	@Test
	public void Step_048_MAPS_Admin_41_Verify_Application_Displays_Three_Reviwer_Roles() {
		test.maps_adminpage.verifyDropdownFieldsInSearchCriteriaTable("Select a reviewer role");
	}
	

	@Test
	public void Step_049_MAPS_Admin_42_Verify_Application_Displays_Last_Login_Details_Of_Individual() {
		test.maps_adminpage.verfiyDefaultFieldOnLeftNavigationPanel(YamlReader.getYamlValue("Admin.Report_LoginDetails"));
	}
	
	@Test
	public void Step_055_MAPS_Admin_47_Verify_Application_Launches_New_Window_On_Clicking_ControlID() {
		test.maps_adminpage.clickLeftNavigationPanelOptions("Special");
		test.maps_adminpage.clickLeftNavigationPanelOptions("fields in body");
	}
	
	@Test
	public void Step_056_MAPS_Admin_48_Verify_Application_Allows_To_Edit_And_Save_Changes_And_Closes_On_Clicking_Finish_Button() {

    controlId = test.maps_adminpage.selectRandomControlId();
    test.maps_adminpage.verifyApplicationLaunchesNewWindowOnClickingControlId(YamlReader.getYamlValue("Admin.Abstract_Window_title"));
    
    test.maps_submissionPage.submitTitleAndBodyDetails(YamlReader.getYamlValue(""), YamlReader.getYamlValue(""));
    
    //MAPS_Admin_82
    test.maps_adminpage.clickLeftNavigationPanelOptions("Email");
    
	}




}
