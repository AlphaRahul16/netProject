package com.qait.MAPS.tests;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class Maps_Session_Tests extends BaseTest {

	private String maps_url;
	private String griduniqueName = "Selenium_Test_Grid_" + System.currentTimeMillis();
	private String[] roles = { "OPA Staff", "Program Viewer", "Program Chair Sessioning", "Abstract Editor",
			"Session Admin" };
	private String[] leftPanelOptions = { "Dashboard & Instructions", "Meeting Setup", "Sessioning",
			"Invitations & Email", "Reports", "Data Export" };
	private String[] importType = { "Download template", "Download resources", "Browse", "Import", "Cancel" };
	private String[] buttonProgramArea = { "Add Program Area", "Delete Program Areas", "Export to Excel",
			"Import Program Areas" };
	private String[] programAreaTableHeader = { "Program Area Name", "Type", "Color", "Abstract Submission Role",
			"Owners" };
	private String[] optionSessioning = { "Symposia", "Sessions & Events", "Symposia Viewer", "Session Viewer",
			"Session Builder", "Schedule Sessions", "Abstracts" };
	private String[] filterDropDownButtons = {"Add Room","Delete Room"};


	@BeforeClass
	public void Start_Test_Session() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		maps_url = YamlReader.getYamlValue("MAPS_Url");
	}

	@BeforeMethod
	public void printCaseIdExecuted(Method method) {
		test.printMethodName(method.getName());
	}

	/**
	 * Test case : Submission class MAPS_1 : launch application
	 */
	@Test

	public void MAPS_1_Launch_Application() {
		test.launchMAPSApplication(maps_url);
		test.maps_SSOPage.verifyUserIsOnMAPSHomePage("Meeting Abstracts Programming System");
	}

	/**
	 * Test case : Submission class MAPS_3 : login functionality
	 */
	@Test

	public void MAPS_3_LogIn_With_Valid_Credentials() {
		test.maps_SSOPage.loginWithValidCredentials(YamlReader.getYamlValue("LogIn_Details.userID"),
				YamlReader.getYamlValue("LogIn_Details.password"));
	}

	@Test

	public void MAPS_Session_0001_Click_On_Session_In_Top_Navigation_Menu() {
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Session");
		test.maps_SSOPage.verifyUserIsOnTabPage("Session");
		test.maps_reviewpage.verifyPageHeader("Multiple Role Selection");
		test.maps_sessionpage.verifyApplicationDisplaysRadioButtonOnClickingSessionTab(roles);

	}

	// @Test
	// public void
	// Test04_MAPS_Session_4_User_Should_Navigate_To_Abstract_Page_On_Clicking_Go_Button()
	// {
	// test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("OPA
	// Staff");
	// test.maps_sessionpage.clickButtonToContinueToNextPage("Select");
	// test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
	// test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Abstracts
	// Assigned To Me");
	// }
	//
	// @Test
	// public void
	// MAPS_Session_67_Verify_options_available_on_Program_Viewer_page(){
	//
	// }

	// @Test
	public void Step_04_MAPS_Session_914_Verify_User_Navigates_To_Room_Availability_Page() {
		// test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Session");
		test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("Session Admin");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Select");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Meeting Setup");
		test.maps_sessionpage.clickSubHeadingLeftNavigationPanel("Room Availability");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Room Availability");
	}

	// @Test
	public void Step_04_MAPS_Session_6_User_Should_Navigate_To_Abstract_Page_On_Clicking_Go_Button() {
		test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("OPA Staff");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Select");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");

	}

	// @Test
	public void Step_05_MAPS_Session_7_Verify_available_options_on_Session_page() {
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Abstracts Assigned To Me");
		test.maps_reviewpage.verifyLinksUnderNamedModule("Save/Edit");
		test.maps_reviewpage.verifyLinksUnderNamedModule("Delete");
		test.maps_reviewpage.verifyTextField("Filter");
		test.maps_reviewpage.verifyCrossImageForNamedDropDown("Filter");
		test.maps_reviewpage.verifyExpandIconUnderNamedModule();
		test.maps_reviewpage.verifyDropDown("Found In");
		test.maps_reviewpage.verifyDropDown("Export to Excel");
		test.maps_reviewpage.verifyDropDown("Toggle View");
		test.maps_reviewpage.verifyPaginationSectionAtTheBottomOfTheTable();
		test.maps_reviewpage.verifyDropDown("Records per page");
		test.maps_sessionpage.verifyRefreshButtonAtBottom();
	}

	// @Test
	public void Step_06_MAPS_Session_11_Verify_Application_Allows_User_To_Create_A_New_Grid() {
		test.maps_reviewpage.clickOnButton("Save/Edit");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Save Grid Configuration");
		test.maps_reviewpage.enterDetailsAtSaveGridConfigurationPage(griduniqueName);
	}

	// @Test
	// public void
	// Step_05_MAPS_Session_915_Verify_Sections_Are_Displayed_On_Room_Availability_Page()
	// {
	// test.maps_sessionpage.verifySectionsOnRoomAvailabilityPage("Rooms", 1);
	// test.maps_sessionpage.verifySectionsOnRoomAvailabilityPage("Room
	// Availability", 2);
	// }
	//
	// @Test
	// public void
	// Step_06_MAPS_Session_919_Verify_Fields_Are_Displayed_Under_Rooms_Section()
	// {
	// test.maps_sessionpage.verifyFilterDropdwonOnRoomAvailabalityPage(2);
	// test.maps_sessionpage.verifyFieldsOnRoomAvailablityPage("Save/Edit", 1);
	// test.maps_sessionpage.verifyFieldsOnRoomAvailablityPage("Delete", 1);
	// }
	//
	// @Test
	// public void
	// Step_07_MAPS_Session_920_Verify_Application_Displays_Filter_Results_On_Room_Availability_Page()
	// {
	// test.maps_sessionpage.enterProgramName("Test Program BT 033017", 1);
	// test.maps_sessionpage.clickOnArrowButton("Room Name");
	// test.maps_sessionpage.enterFilterText("Filters", "Room1");
	// test.maps_sessionpage.verifyFilterResults("Room1", 1, 3);
	// }

	@Test

	public void Step_07_MAPS_Session_920_Verify_Application_Displays_Filter_Results_On_Room_Availability_Page(){
		test.maps_sessionpage.clickOnDropDownImage(1);
		test.maps_sessionpage.selectRoleOnSaveGridConfiguration("Test Program BT 033017");
		test.maps_sessionpage.clickOnArrowButton("Room Name");
		test.maps_sessionpage.enterFilterText("Filters", "Room1");
		test.maps_sessionpage.verifyFilterResults("Room1", 1, 3);
	}
	
	@Test
	public void Step_08_MAPS_Session_921_Verify_New_Filter_Is_Added_Upon_Clicking_Save_Edit_Link(){
        String gridName="Test"+System.currentTimeMillis();
        test.maps_sessionpage.clickOnSaveAndEditButton("Save/Edit", 1);
		test.maps_sessionpage.enterNameOnSaveGridConfiguration("Name:",gridName);
		test.maps_sessionpage.clickCheckboxOnSaveGridConfiguration("Added Filters");
		test.maps_sessionpage.clickCheckboxOnSaveGridConfiguration("Make available");
		test.maps_sessionpage.selectRoleOnSaveGridConfiguration("Session Admin");
		test.maps_sessionpage.clickOnSaveButton("Save");
		test.maps_sessionpage.verifyFilterIsByDefaultSelected(gridName, 2);
		test.maps_sessionpage.verifyFilterResults("Room1", 1, 3);
	}
	
	@Test
	public void Step_09_MAPS_Session_931_Verify_Buttons_Under_Filter_DropDown(){
        test.maps_sessionpage.verifyButtonsOnTypes(filterDropDownButtons);
	}

	public void MAPS_Session_0784_Verify_Application_navigates_to_Session_Admin_page_on_selecting_the_Session_Admin_radio_button() {
		test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("Session Admin");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Select");
		// page title is not available
		// test.maps_sessionpage.verifyTitleForRoles("Session Admin");
	}

	// @Test passed
	public void MAPS_Session_0785_Verify_options_available_on_Session_Admin_page() {

		test.maps_reviewpage.verifybuttonOnRolesPage("Set Preferences");
		test.maps_sessionpage.verifyLeftPanelOptionsOnSessionAdminPage(leftPanelOptions);
	}

	// @Test // passed with error
	public void MAPS_Session_0791_Application_navigates_the_user_to_the_Save_Grid_Configuration_on_clicking_the_Save_Edit_link() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Meeting Setup");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Programs");
		// not clicked
		test.maps_reviewpage.selectExistingConfigurationFromGridConfigurationDropdown();
		test.maps_reviewpage.clickOnButton("Save/Edit");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Save Grid Configuration");
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Close");
	}

	// @Test // Passed
	public void MAPS_Session_0799_Verify_that_application_filters_the_result_on_the_basis_of_the_criteria_provided() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Meeting Setup");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Programs");
		String programID = test.maps_sessionpage.getValueFromProgramsTable();
		test.maps_reviewpage.enterValueInFilter(programID);
		test.maps_sessionpage.verifyTheResultOfFilter(programID);
	}

	// @Test //passed
	public void MAPS_Session_0815_Verify_that_application_prompts_the_user_to_save_the_program_before_adding_the_owners() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Meeting Setup");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Programs");
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Create Program");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Create Program");
		test.maps_sessionpage.enterValuesInCreateProgramPage(
				YamlReader.getYamlValue("Session.Programs.ProgramTitle") + System.currentTimeMillis(),
				YamlReader.getYamlValue("Session.Programs.Interval"));
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Add Owners");
		test.maps_sessionpage.verifyPopupMessage("The program must be saved before adding owners. Ok?");
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("No");
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Close");
	}

	// @Test // not verified functionality
	public void MAPS_Session_0847_Verify_that_application_navigates_to_Add_Edit_Rooms_tab_on_Saving_all_Information() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Meeting Setup");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Programs");
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Create Program");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Create Program");
		test.maps_sessionpage.enterValuesInCreateProgramPage(
				YamlReader.getYamlValue("Session.Programs.ProgramTitle") + System.currentTimeMillis(),
				YamlReader.getYamlValue("Session.Programs.Interval"));
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Save");
		test.maps_sessionpage.verifyPopupMessage("Add/Edit Rooms");
	}

	// @Test //need to be updated
	public void MAPS_Session_0875_Verify_that_the_application_Saves_data_and_creates_a_new_program_on_clicking_Save_button() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Meeting Setup");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Programs");
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Create Program");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Create Program");
		test.maps_sessionpage.enterValuesInCreateProgramPage(
				YamlReader.getYamlValue("Session.Programs.ProgramTitle") + System.currentTimeMillis(),
				YamlReader.getYamlValue("Session.Programs.Interval"));
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Save");
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Close");
		// test.maps_sessionpage.verifyProgramIsaddedInTable();
	}

	// @Test //passed
	public void MAPS_Session_1056_Verify_that_application_navigates_to_Types_page_on_clicking_Type_option_under_the_Meeting_Setup() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Meeting Setup");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Types");
		test.maps_sessionpage.verifyPopupMessage("Types");
	}

	// @Test //passed
	public void MAPS_Session_1057_Verify_options_available_on_the_Types_page() {
		String[] buttonTypes = { "Add Type", "Delete Types", "Export to Excel",
				"Import Session/Event/Symposium Types" };
		test.maps_sessionpage.verifyButtonsOnTypes(buttonTypes);
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Instructions");
		String[] typeTableHeader = { "Session / Event / Symposium Type Name", "Type", "Color" };
		test.maps_sessionpage.verifyLeftPanelOptionsOnSessionAdminPage(typeTableHeader);
		test.maps_sessionpage.verifyListOfProgramAreas("Types");
	}

	// @Test //passed
	public void MAPS_Session_1065_Verify_that_application_adds_Session_Detail_Type_on_clicking_the_Add_Session_Detail_Type_button() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Meeting Setup");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Types");
		test.maps_sessionpage.verifyPopupMessage("Types");
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Add Type");
		test.maps_sessionpage.verifyPopupMessage("Add New Type");
		String testType = YamlReader.getYamlValue("Session.Types.TestType") + System.currentTimeMillis();
		test.maps_sessionpage.entervaluesinAddNewTypes(testType, YamlReader.getYamlValue("Session.Types.ColorCode"),
				YamlReader.getYamlValue("Session.Types.SessionType"));
		test.maps_reviewpage.selectValueForSessionDetailType();
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Add Session Detail Type");
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Save and Close");
		test.maps_sessionpage.verifyApplicationShouldAddSessionDetailType(testType,
				YamlReader.getYamlValue("Session.Types.ColorCode"),
				YamlReader.getYamlValue("Session.Types.SessionType"));
	}

	// @Test //passed
	public void MAPS_Session_1080_Verify_options_available_on_the_Import_Types_popup() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Meeting Setup");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Types");
		test.maps_sessionpage.verifyPopupMessage("Types");
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Import Session/Event/Symposium Types");
		test.maps_sessionpage.verifyPopupMessage("Import Types");
		test.maps_sessionpage.verifyButtonsOnTypes(importType);
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Cancel");
	}

	// @Test //passed
	public void MAPS_Session_1105_Verify_that_application_navigates_to_Program_Areas_page_on_clicking_Program_Area_under_Meeting_Setup() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Meeting Setup");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Program Areas");
		test.maps_sessionpage.verifyPopupMessage("Program Areas");
	}

	// @Test //passed
	public void MAPS_Session_1106_Verify_options_available_on_Program_Areas_page() {

		test.maps_sessionpage.verifyButtonsOnTypes(buttonProgramArea);
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Instructions");
		test.maps_reviewpage.verifyExpandIconUnderNamedModule();
		test.maps_sessionpage.verifyLeftPanelOptionsOnSessionAdminPage(programAreaTableHeader);
		test.maps_sessionpage.verifyListOfProgramAreas("Program Area");
	}

	// @Test //passed
	public void MAPS_Session_1151_Verify_that_application_displays_Import_Program_Area_popup_on_clicking_Import_Program_Areas_button() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Meeting Setup");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Program Areas");
		test.maps_sessionpage.verifyPopupMessage("Program Areas");
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Import Program Areas");
		test.maps_sessionpage.verifyPopupMessage("Import Program Areas");
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Cancel");
	}

	// @Test // verifyApplicationShouldAllowToSelectGridConfiguration
	public void MAPS_Session_1180_Verify_that_application_allows_to_select_different_grid_views_from_Grid_Configuration_dropdown() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Meeting Setup");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Hosts");
		test.maps_sessionpage.verifyPopupMessage("Hosts");
		String gridConfig = test.maps_reviewpage.selectExistingConfigurationFromGridConfigurationDropdown();
		// test.maps_reviewpage.verifyApplicationShouldAllowToSelectGridConfiguration(gridConfig);
	}

	// @Test //passed
	public void MAPS_Session_1181_Verify_that_application_navigates_to_the_SaveGridConfiguration_on_clicking_Save_Edit_link() {
		test.maps_reviewpage.clickOnButton("Save/Edit");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Save Grid Configuration");
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Close");
	}

	// @Test //passed
	public void MAPS_Session_1198_Verify_application_allows_to_add_new_host_on_clicking_AddNewHost_button() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Meeting Setup");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Hosts");
		test.maps_sessionpage.verifyPopupMessage("Hosts");
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Add New Host");
		String email = YamlReader.getYamlValue("Session.Host.Email") + System.currentTimeMillis() + "@acs.org";
		test.maps_reviewpage.enterValuesInAddNewHost(
				YamlReader.getYamlValue("Session.Host.First_Name") + System.currentTimeMillis(),
				YamlReader.getYamlValue("Session.Host.Last_Name") + System.currentTimeMillis(), email,
				YamlReader.getYamlValue("Session.Host.institution"));
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Save");
		test.maps_reviewpage.enterValueInFilter(email);
		test.maps_reviewpage.verifyTheResult(email);
	}

	@Test
	public void MAPS_Session_1277_Application_should_navigate_to_the_Roles_page() {
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Session");
		test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("Session Admin");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Select");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Meeting Setup");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Roles");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Roles");
	}

	@Test
	public void MAPS_Session_1279_Application_should_display_Add_New_Role_popup_on_clicking_Add_role_button() {
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Add Role");
		test.maps_sessionpage.verifyPopupMessage("Add New Role");
	}

	@Test
	public void MAPS_Session_1308_Verify_sub_options_under_Sessioning_option() {
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Session");
		test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("Session Admin");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Select");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_sessionpage.verifyLeftPanelOptionsOnSessionAdminPage(optionSessioning);
	}

	@Test
	public void MAPS_Session_1503_Application_should_remove_the_selected_abstracts_when_user_clicks_on_Remove_Selected_button() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Symposia");
	}
}
