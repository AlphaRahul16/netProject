package com.qait.MAPS.tests;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
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
	private String[] emailLogHeadings = {"E-Mail ID","Template NameSent byDate Sent/ScheduledTime Sent/Scheduled","ToCcBccFrom","# Successes# Failures# Pending","Details","Delete(Only if not sent)"};
	private String statusRoleName;
	private String templateName = "testTemplate" + System.currentTimeMillis();

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
	
	@Test
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
	}

	@Test
	public void Step_027_MAPS_Admin_20_Verify_Application_Saves_Changes_For_Early_Late_Submission_Time_Date_Override_Field_For_Admin() {
		 HashMap<String, String> searchCriteria = new HashMap<String, String>();
		 searchCriteria.put("First Name", "Kanika");
		 searchCriteria.put("Last Name", "Sharma");
		 test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Admin");
		   test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("Admin"); 
		   test.maps_sessionpage.clickButtonToContinueToNextPage("Select");
		test.maps_adminpage.clickLeftNavigationPanelOptions("People");
		test.maps_adminpage.enterSearchCriteria(searchCriteria);
	}
	
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
    test.maps_adminpage.verifyApplicationLaunchesNewWindowOnClickingControlId(YamlReader.getYamlValue("Admin.Fields_In_Body.Abstract_Window_title"));
    test.maps_adminpage.clickEditLinkForTitleAndBody();
    test.maps_submissionPage.submitTitleAndBodyDetails(YamlReader.getYamlValue("Admin.Fields_In_Body.Title_details"), YamlReader.getYamlValue("Admin.Fields_In_Body.Body_details"));
    test.maps_submissionPage.clickOnSaveAndContinueButton();
    test.maps_submissionPage.clickOnNamedButton("Step 5: Review & Submit");
    test.maps_submissionPage.clickOnNamedButton("Finish");
   // test.closeBrowserWindow();
    test.maps_adminpage.navigateToOriginalAdminWindow();

	}
	
	@Test
	public void Step_089_MAPS_Admin_81_Verify_Application_Changes_Status_From_Active_To_Inactive_When_User_Checks_Active_Checkbox() {
		  test.maps_adminpage.clickLeftNavigationPanelOptions("Email");
		  statusRoleName = test.maps_adminpage.checkActiveCheckboxOfTemplate();
		  test.maps_adminpage.clickNamedButtonImage("update_status");
		  test.maps_adminpage.verifyStatusForRoleIsChangedTo("Inactive",statusRoleName);
		  
	}

	@Test
	public void Step_090_MAPS_Admin_82_Verify_Application_Changes_Status_From_Inactive_To_Active_When_User_Checks_Inactive_Checkbox() {
		test.maps_adminpage.checkInactiveCheckboxOfTemplate(statusRoleName);
		test.maps_adminpage.clickNamedButtonImage("update_status");
		test.maps_adminpage.verifyStatusForRoleIsChangedTo("Active",statusRoleName);
	}
	
	@Test
	public void Step_098_MAPS_Admin_90_Verify_Application_Allows_User_To_Add_Template_By_Provding_Template_Name() {
		test.maps_adminpage.clickLeftNavigationPanelOptions("custom e-mail");
		test.maps_adminpage.addAndVerifyTemplateIsAddedToCustomEmail(templateName);
		
		
	}
	
	@Test
	public void Step_129_MAPS_Admin_121_Verify_Application_Displays_Email_Log_Table_From_Email_Log_Suboption() {
		test.maps_adminpage.clickLeftNavigationPanelOptions("e-mail log");
		test.maps_adminpage.verifyTablesPresentInReviewerReportTab();
		test.maps_adminpage.verifyTablesHeadingsSuboptions(emailLogHeadings);

	}
	
	@Test
	public void Step_146_MAPS_Admin_138_Verify_Application_Displays_Results_As_Per_Search_Criteria_On_Clicking_Search_Icon() {
		test.maps_adminpage.clickLeftNavigationPanelOptions("search for e-mails");
		test.maps_adminpage.enterEmailSearchCriteriaFields(YamlReader.getYamlValue("Admin.Email_Search_Criteria.Email_Template_Name"),
				YamlReader.getYamlValue("Admin.Email_Search_Criteria.Email_Status"));
		test.maps_adminpage.clickNamedButtonImage("search");
		test.maps_adminpage.verifyAccountCreationMessage(YamlReader.getYamlValue("Admin.Email_Search_Criteria.Subject"));
		test.maps_adminpage.verifyAccountCreationMessage(YamlReader.getYamlValue("Admin.Email_Search_Criteria.Result"));
		
	}
	
	@Test
	public void Step_018_MAPS_Admin_71_Verify_Application_Copies_Configuration_Of_Role_From_SelectRole_To_CopyAllConfigurationFromRole() {
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Admin");
		test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("Admin");	
		test.maps_sessionpage.clickButtonToContinueToNextPage("Select");
		test.maps_adminpage.clickLeftNavigationPanelOptions("Abstract Proof Configuration");
		test.maps_adminpage.clickOnSelectRoleDropDownAndSelectARole("Admin");
		test.maps_adminpage.clickOnCopyRoleDropDownAndSelectARole("Decision");
		test.maps_adminpage.clickOnGoButton();
	}
	
	
	@Test
	public void Step_025_MAPS_Admin_157_Verify_Application_Saves_The_Change_And_Navigates_User_Back_To_AbstractsPage() {
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Admin");
		test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("Admin");	
		test.maps_sessionpage.clickButtonToContinueToNextPage("Select");
		test.maps_adminpage.clickLeftNavigationPanelOptions("Abstract search");
		String first_name=test.maps_adminpage.getFirstNameOfUser();
		test.maps_adminpage.enterSearchTermForAbstractSearch(first_name);
		test.maps_adminpage.clickOnSearchButton();
		test.maps_adminpage.clickOnTitleOfSearchTerm(first_name);
		test.maps_adminpage.clickOnEditTitleButton();
		test.maps_adminpage.enterTitleDetails(YamlReader.getYamlValue("Admin.title"));
		test.maps_submissionPage.clickOnSaveAndContinueButton();
		test.maps_adminpage.clickOnFinishButton();
		test.maps_adminpage.verifyTitleIsEdited(YamlReader.getYamlValue("Admin.title"));
	
	}
		
	@Test
	public void Step_027_MAPS_Admin_164_Verify_Application_Changes_Status_Of_Abstract_To_AuthorWithdraw_When_Click_On_Withdraw() {
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Admin");
		test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("Admin");	
		test.maps_sessionpage.clickButtonToContinueToNextPage("Select");
		test.maps_adminpage.clickLeftNavigationPanelOptions("Abstract search");
		String first_name=test.maps_adminpage.getFirstNameOfUser();
		test.maps_adminpage.enterSearchTermForAbstractSearch(first_name);
		test.maps_adminpage.clickOnSearchButton();
		String abstractID=test.maps_adminpage.getAbstractID();
		test.maps_adminpage.clickOnWithdrawLink();	
		test.maps_adminpage.verifyStatusOnAbstractInSearchTableAfterWithdraw();
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Submission");
		test.maps_adminpage.verifyStatusOfAbstractOnSubmissionTabAfterWithdraw(abstractID);
	}

	@Test
	public void Step_028_MAPS_Admin_165_Verify_Application_Changes_Status_Of_Abstract_To_UnderReview_When_Click_On_Unwithdraw() {
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Admin");
		test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("Admin");	
		test.maps_sessionpage.clickButtonToContinueToNextPage("Select");
		test.maps_adminpage.clickLeftNavigationPanelOptions("Abstract search");
		String first_name=test.maps_adminpage.getFirstNameOfUser();
		test.maps_adminpage.enterSearchTermForAbstractSearch(first_name);
		test.maps_adminpage.clickOnSearchButton();
		String abstractID=test.maps_adminpage.getAbstractIDOfUnwithdraw();
		test.maps_adminpage.clickOnUnwithdrawLink();
		test.maps_adminpage.verifyStatusOnAbstractInSearchTableAfterUnwithdraw();
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Submission");
		test.maps_adminpage.verifyStatusOfAbstractOnSubmissionTabAfterUnwithdraw(abstractID);
	}
	
	
	@Test
	public void Step_029_MAPS_Admin_180_Verify_Functionality_Of_Start_A_New_Data_Export_Link(){
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Admin");
		test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("Admin");	
		test.maps_sessionpage.clickButtonToContinueToNextPage("Select");
		test.maps_adminpage.clickOnDataExportLinkFromLeftPanel("Data Export");
		test.maps_adminpage.clickOnStartANewDataExportLink();
		test.maps_adminpage.createANewDataExport(YamlReader.getYamlValue("Admin.data_export"));
		test.maps_adminpage.verifyExportCreated(YamlReader.getYamlValue("Admin.data_export"));
	}
	
	@Test
	public void Step_030_MAPS_Admin_158_Verify_Application_Does_Not_Display_Finish_Button_For_User_with_Draft_Status() {
			test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Admin");
			test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("Admin");	
			test.maps_sessionpage.clickButtonToContinueToNextPage("Select");
			test.maps_adminpage.clickLeftNavigationPanelOptions("Abstract search");
			String first_name=test.maps_adminpage.getFirstNameOfUser();
			test.maps_adminpage.enterSearchTermForAbstractSearch(first_name);
			test.maps_adminpage.clickOnSearchButton();
			test.maps_adminpage.clickOnTitleOfSearchTermWithDraftStatus("Draft");
			test.maps_adminpage.verifyFinishNotDisplay();	
		}
	






}
