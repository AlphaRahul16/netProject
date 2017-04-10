package com.qait.MAPS.tests;

import java.lang.reflect.Method;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class Maps_Session_Tests extends BaseTest {

	private String maps_url, symposiumTitle,selectedsymposia;
	private String griduniqueName = "Selenium_Test_Grid_" + System.currentTimeMillis();
	private String[] roles = { "OPA Staff", "Program Viewer", "Program Chair Sessioning", "Abstract Editor",
			"Session Admin" };
	private String[] programViewerleftpaneloptions = { "Dashboard & Instructions", "Symposia Viewer", "Sessioning",
			"Session Viewer", "Abstracts" };
	private String[] leftPanelOptionsSessionAdmin = { "Dashboard & Instructions", "Meeting Setup", "Sessioning",
			"Invitations & Email", "Reports", "Data Export" };
	private String[] importType = { "Download template", "Download resources", "Browse", "Import", "Cancel" };
	private String[] buttonProgramArea = { "Add Program Area", "Delete Program Areas", "Export to Excel",
			"Import Program Areas" };
	private String[] programAreaTableHeader = { "Program Area Name", "Type", "Color", "Abstract Submission Role",
			"Owners" };
	private String[] optionSessioning = { "Symposia", "Sessions & Events", "Symposia Viewer", "Session Viewer",
			"Session Builder", "Schedule Sessions", "Abstracts" };
	private String[] filterDropDownButtons = { "Add Room", "Delete Room" };
	private String[] filterDropDownsRoom = { "Add Room", "Delete Room" };
	private String[] fieldsAddRoom = { "Save", "Cancel" };
	private String[] filterDropDownRoomAvailability = { "Add Room", "Delete Room" };

	String roomName;


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


	/**
		 * Session : OPA Staff
		 */
	@Test

	public void MAPS_Session_0001_Click_On_Session_In_Top_Navigation_Menu() {
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Session");
		test.maps_SSOPage.verifyUserIsOnTabPage("Session");
		test.maps_reviewpage.verifyPageHeader("Multiple Role Selection");
		test.maps_sessionpage.verifyApplicationDisplaysRadioButtonOnClickingSessionTab(roles);

	}
	
	/**
	 * Session : OPA Staff
	 */
//	@Test
	public void Test04_MAPS_Session_4_User_Should_Navigate_To_Abstract_Page_On_Clicking_Go_Button() {
		test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("OPA Staff");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Select");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Abstracts Assigned To Me");

	}

	
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


	/**
	 * Session : OPA Staff
	 */

	// @Test
	public void Step_04_MAPS_Session_6_User_Should_Navigate_To_Abstract_Page_On_Clicking_Go_Button() {
		test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("OPA Staff");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Select");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");

	}

	//**
	 //    Session : OPA Staff
	 
	//@Test
	public void Step_05_MAPS_Session_7_Verify_Available_Options_On_Session_OPA_Staff_Page() {
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

	/**
	 * Session : OPA Staff
	 */
	//@Test
	public void Step_06_MAPS_Session_11_Verify_Application_Allows_User_To_Create_A_New_Grid() {
		test.maps_reviewpage.clickOnButton("Save/Edit");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Save Grid Configuration");
		test.maps_reviewpage.enterDetailsAtSaveGridConfigurationPage("Abstract_" + griduniqueName);
	}

	/**
	 * Session : Program Viewer : Symposia
	 */

	//@Test
	public void MAPS_Session_67_Click_On_Session_And_Navigate_To_Program_Viewer() {
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Session");
		test.maps_SSOPage.verifyUserIsOnTabPage("Session");
		test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("Program Viewer");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Select");
		test.maps_reviewpage.verifybuttonOnRolesPage("Set Preferences");
		test.maps_sessionpage.verifyLeftPanelOptionsOnSessionAdminPage(programViewerleftpaneloptions);
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Symposia Viewer");
	}

	/**
	 * Session : Program Viewer : Symposia
	 */

	//@Test
	public void MAPS_Session_70_Verify_Available_Options_On_Session_Program_Viewer_Page() { // List of Symposia verification is remaining due to its unavailability
		test.maps_reviewpage.verifyLinksUnderNamedModule("Save/Edit");
		test.maps_reviewpage.verifyLinksUnderNamedModule("Delete");
		test.maps_reviewpage.verifyTextField("Filter");
		test.maps_reviewpage.verifyCrossImageForNamedDropDown("Filter");
		test.maps_reviewpage.verifyExpandIconUnderNamedModule();
		test.maps_reviewpage.verifyDropDown("Found In");
		test.maps_reviewpage.verifyDropDown("Export");
		test.maps_sessionpage.isPrintSelectedButtonDisplayed("Print Selected");
		test.maps_reviewpage.verifyPaginationSectionAtTheBottomOfTheTable();
		test.maps_reviewpage.verifyDropDown("Records per page");
		test.maps_sessionpage.verifyRefreshButtonAtBottom();
	}

	/**
	 * Session : Program Viewer : Symposia
	 */

	//@Test
	public void MAPS_Session_74_Verify_Application_Allows_User_To_Create_A_New_Grid_In_Symposia() {
		test.maps_reviewpage.clickOnButton("Save/Edit");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Save Grid Configuration");
		test.maps_reviewpage.enterDetailsAtSaveGridConfigurationPage("Symposia_" + griduniqueName);
	}

	/**
	 * Session : Program Viewer : Symposia
	 */

//	@Test
	public void MAPS_Session_93_Verify_Application_Print_Selected_Symposia() {

	}

	//@Test
	 public void Step_05_MAPS_Session_915_Verify_Sections_Are_Displayed_On_Room_Availability_Page() {
	 test.maps_sessionpage.verifySectionsOnRoomAvailabilityPage("Rooms", 1);
	 test.maps_sessionpage.verifySectionsOnRoomAvailabilityPage("Room Availability", 2);
	 }

	//@Test
	public void Step_06_MAPS_Session_919_Verify_Fields_Are_Displayed_Under_Rooms_Section() {
		test.maps_sessionpage.verifyFilterDropdwonOnRoomAvailabalityPage(2);
		test.maps_sessionpage.verifyFieldsOnRoomAvailablityPage("Save/Edit", 1);
		test.maps_sessionpage.verifyFieldsOnRoomAvailablityPage("Delete", 1);
	}

	//@Test
	public void Step_04_MAPS_Session_93_Verify_Application_Print_Selected_Symposia() {
		selectedsymposia = test.maps_sessionpage.selectaRandomRecordFromTheList();
		test.maps_sessionpage.clickOnSaveButton("Print Selected");
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

	// @Test
	public void Step_07_MAPS_Session_920_Verify_Application_Displays_Filter_Results_On_Room_Availability_Page() {
		test.maps_sessionpage.clickOnDropDownImage(1);
		test.maps_sessionpage.selectRoleOnSaveGridConfiguration("Room","Test Program BT 033017");
		test.maps_sessionpage.clickOnArrowButton("Room Name");
		test.maps_sessionpage.enterFilterText("Filters", "Room1");
		test.maps_sessionpage.verifyFilterResults("Room1", 1, 3);
	}

	// @Test
	public void Step_08_MAPS_Session_921_Verify_New_Filter_Is_Added_Upon_Clicking_Save_Edit_Link() {
		String gridName = "Test" + System.currentTimeMillis();
		test.maps_sessionpage.clickOnSaveAndEditButton("Save/Edit", 1);
		test.maps_sessionpage.enterNameOnSaveGridConfiguration("Name:", gridName);
		test.maps_sessionpage.clickCheckboxOnSaveGridConfiguration("Added Filters");
		test.maps_sessionpage.clickCheckboxOnSaveGridConfiguration("Make available");
		test.maps_sessionpage.selectRoleOnSaveGridConfiguration("Role","Session Admin");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Save");
		test.maps_sessionpage.verifyFilterIsByDefaultSelected(gridName, 2);
		test.maps_sessionpage.verifyFilterResults("Room1", 1, 3);
	}

	//@Test
	public void Step_09_MAPS_Session_931_Verify_Buttons_Under_Filter_DropDown() {
		test.maps_sessionpage.verifyButtonsOnTypes(filterDropDownsRoom);
	}
	
	//@Test
	public void Step_10_MAPS_Session_932_Verify_Fields_After_Clicking_Add_Room_Button() {
		test.maps_sessionpage.clickOnSaveButton("Add Room");
		test.maps_sessionpage.verifyInputTextField("name");
		test.maps_sessionpage.verifyInputTextField("venue");
		test.maps_sessionpage.verifyButtonsOnTypes(fieldsAddRoom);
	}

	//@Test
	public void Step_11_MAPS_Session_933_Verify_User_Is_Able_To_Add_Meeting_Room() {
		roomName="Test Room1"+System.currentTimeMillis();
		String venueName="Test Venue1"+System.currentTimeMillis();
		test.maps_sessionpage.enterValueInInputtextField("name",roomName);
		test.maps_sessionpage.enterValueInInputtextField("venue",venueName);
		test.maps_sessionpage.clickOnSaveButton("Save");
		test.maps_sessionpage.verifyRoomIsAdded(roomName, venueName);
	}
	
	//@Test
	public void Step_12_MAPS_Session_966_Verify_Records_Are_Sorted_On_Criteria_Basis() {
		List<String> dataBeforeSorting=test.maps_sessionpage.getTableData("1", "4");
		test.maps_sessionpage.clickOnArrowButton("Room Name");
		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Configure Sort");
		test.maps_sessionpage.verifyPopupMessage("Sort");
		test.maps_sessionpage.clickOnAddButton("Add");
		test.maps_sessionpage.selectColumnForSorting("Building/Venue");//Room Name
		test.maps_sessionpage.clickOnSaveButton("Apply");
		List<String> dataAfterSorting=test.maps_sessionpage.getTableData("1", "4");
		test.maps_sessionpage.verifyDataIsSorted(dataBeforeSorting, dataAfterSorting);

	}
	
	//@Test
	public void Step_13_MAPS_Session_984_Verify_User_Is_Able_To_Assign_And_Unassign_Room() {
		test.maps_sessionpage.clickParticularRecordFromList(roomName);
		test.maps_sessionpage.selectLastRecordFromList();
		test.maps_sessionpage.clickOnSaveButton("Multiple Assign / Unassign");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Assign selected availability to rooms");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Successfully Saved");
		test.maps_sessionpage.clickParticularRecordFromList(roomName);
		test.maps_sessionpage.clickOnSaveButton("Multiple Assign / Unassign");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Unassign selected availability from rooms");
		test.maps_sessionpage.verifyPopupMessage("You are about to unassign selected Room Availability from rooms. Are you sure?");
		test.maps_sessionpage.clickOnSaveButton("Yes");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Successfully Deleted");
	}
	
	//@Test
	public void Step_14_MAPS_Session_987_Verify_Fields_Are_Displayed_Under_Room_Availability_Section() {
		test.maps_sessionpage.verifyFilterDropdwonOnRoomAvailabalityPage(4);
		test.maps_sessionpage.verifyFieldsOnRoomAvailablityPage("Save/Edit", 2);
		test.maps_sessionpage.verifyFieldsOnRoomAvailablityPage("Delete", 2);
	}
	
	//@Test
	public void Step_15_MAPS_Session_999_Verify_Buttons_Under_Filter_DropDown_For_Room_Availability() {
		test.maps_sessionpage.verifyButtonsOnTypes(filterDropDownRoomAvailability);
	}

	@Test
	public void MAPS_Session_0784_Verify_Application_navigates_to_Session_Admin_page_on_selecting_the_Session_Admin_radio_button() {
		test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("Session Admin");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Select");
		// page title is not available
		// test.maps_sessionpage.verifyTitleForRoles("Session Admin");
	}

	// @Test passed
	public void MAPS_Session_785_Verify_options_available_on_Session_Admin_page() {

		test.maps_reviewpage.verifybuttonOnRolesPage("Set Preferences");
		test.maps_sessionpage.verifyLeftPanelOptionsOnSessionAdminPage(leftPanelOptionsSessionAdmin);
	}

	// @Test // passed with error
	public void MAPS_Session_791_Application_navigates_the_user_to_the_Save_Grid_Configuration_on_clicking_the_Save_Edit_link() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Meeting Setup");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Programs");
		// not clicked
		test.maps_reviewpage.selectExistingConfigurationFromGridConfigurationDropdown();
		test.maps_reviewpage.clickOnButton("Save/Edit");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Save Grid Configuration");
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Close");
	}

	// @Test // Passed
	public void MAPS_Session_799_Verify_that_application_filters_the_result_on_the_basis_of_the_criteria_provided() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Meeting Setup");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Programs");
		String programID = test.maps_sessionpage.getValueFromProgramsTable();
		test.maps_reviewpage.enterValueInFilter(programID);
		test.maps_sessionpage.verifyTheResultOfFilter("program_id", programID);
	}

	// @Test //passed
	public void MAPS_Session_815_Verify_that_application_prompts_the_user_to_save_the_program_before_adding_the_owners() {
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
	public void MAPS_Session_847_Verify_that_application_navigates_to_Add_Edit_Rooms_tab_on_Saving_all_Information() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Meeting Setup");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Programs");
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Create Program");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Create Program");
		test.maps_sessionpage.enterValuesInCreateProgramPage(
				YamlReader.getYamlValue("Session.Programs.ProgramTitle") + System.currentTimeMillis(),
				YamlReader.getYamlValue("Session.Programs.Interval"));
		test.maps_sessionpage.clickOnButtonUnderSessioning("Save");
		test.maps_sessionpage.verifyPopupMessage("Add/Edit Rooms");
	}

	// @Test //need to be updated
	public void MAPS_Session_875_Verify_that_the_application_Saves_data_and_creates_a_new_program_on_clicking_Save_button() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Meeting Setup");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Programs");
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Create Program");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Create Program");
		test.maps_sessionpage.enterValuesInCreateProgramPage(
				YamlReader.getYamlValue("Session.Programs.ProgramTitle") + System.currentTimeMillis(),
				YamlReader.getYamlValue("Session.Programs.Interval"));
		test.maps_sessionpage.clickOnButtonUnderSessioning("Save");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Close");
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
		test.maps_sessionpage.clickOnButtonUnderSessioning("Add Session Detail Type");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Save and Close");
		test.maps_sessionpage.verifyApplicationShouldAddSessionDetailType(testType,
				YamlReader.getYamlValue("Session.Types.ColorCode"),
				YamlReader.getYamlValue("Session.Types.SessionType"));
	}

	// @Test //passed
	public void MAPS_Session_1080_Verify_options_available_on_the_Import_Types_popup() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Meeting Setup");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Types");
		test.maps_sessionpage.verifyPopupMessage("Types");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Import Session/Event/Symposium Types");
		test.maps_sessionpage.verifyPopupMessage("Import Types");
		test.maps_sessionpage.verifyButtonsOnTypes(importType);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Cancel");
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
		test.maps_sessionpage.clickOnButtonUnderSessioning("Import Program Areas");
		test.maps_sessionpage.verifyPopupMessage("Import Program Areas");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Cancel");
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
		test.maps_sessionpage.clickOnButtonUnderSessioning("Close");
	}

	// @Test //passed
	public void MAPS_Session_1198_Verify_application_allows_to_add_new_host_on_clicking_AddNewHost_button() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Meeting Setup");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Hosts");
		test.maps_sessionpage.verifyPopupMessage("Hosts");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Add New Host");
		String email = YamlReader.getYamlValue("Session.Host.Email") + System.currentTimeMillis() + "@acs.org";
		test.maps_sessionpage.enterValuesInAddNewHost(
				YamlReader.getYamlValue("Session.Host.First_Name") + System.currentTimeMillis(),
				YamlReader.getYamlValue("Session.Host.Last_Name") + System.currentTimeMillis(), email,
				YamlReader.getYamlValue("Session.Host.institution"));
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Save");
		test.maps_reviewpage.enterValueInFilter(email);
		test.maps_reviewpage.verifyTheResult(email);
	}


	// @Test //passed
	public void MAPS_Session_1277_Application_should_navigate_to_the_Roles_page() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Meeting Setup");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Roles");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Roles");
	}

	// @Test //passed
	public void MAPS_Session_1279_Application_should_display_Add_New_Role_popup_on_clicking_Add_role_button() {
		test.maps_sessionpage.clickOnButtonUnderSessioning("Add Role");
		test.maps_sessionpage.verifyPopupMessage("Add New Role");
		test.maps_sessionpage.clickOnButtonUnderSessionModule("Close");
	}

	// @Test //passed
	public void MAPS_Session_1308_Verify_sub_options_under_Sessioning_option() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_sessionpage.verifyLeftPanelOptionsOnSessionAdminPage(optionSessioning);
	}

	// @Test
	public void MAPS_Session_1503_Application_should_remove_the_selected_abstracts_when_user_clicks_on_Remove_Selected_button() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Symposia");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Create New Symposium");
		test.maps_sessionpage.verifyPopupMessage("Create Symposium");
		symposiumTitle = YamlReader.getYamlValue("Session.Symposium.Title") + System.currentTimeMillis();
		test.maps_sessionpage.enterValuesInCreateSymposium(symposiumTitle,
				YamlReader.getYamlValue("Session.Symposium.Type"));
		test.maps_sessionpage.clickOnButtonUnderSessionModule("Save");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Add/Edit Hosts");
		test.maps_sessionpage.addHostforSymposium();
		test.maps_sessionpage.addRoleForHost(YamlReader.getYamlValue("Session.Symposium.Host_Role"));
		test.maps_sessionpage.clickOnButtonUnderSessionModule("Save");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Successfully Saved");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Add/Remove/Withdraw Abstracts");
		test.maps_sessionpage.searchAbstract("author_first_name",
				YamlReader.getYamlValue("Session.Symposium.Abstract_AuthorFirstName"));
		test.maps_sessionpage.searchAbstract("author_last_name",
				YamlReader.getYamlValue("Session.Symposium.Abstract_AuthorLastName"));
		test.maps_sessionpage.clickOnButtonUnderSessionModule("Search");
		test.maps_sessionpage.addAbstractsInCurrentlyAssignedAbstractsSection();
		test.maps_sessionpage.selectAbstract("session_abstract_order");
		test.maps_sessionpage.clickOnButtonByIndexing("Remove Selected");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Successfully Removed");
	}

	// @Test
	public void MAPS_Session_1520_Verify_that_the_application_saves_the_session_on_clicking_Save_button() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Symposia");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Create New Symposium");
		test.maps_sessionpage.verifyPopupMessage("Create Symposium");
		symposiumTitle = YamlReader.getYamlValue("Session.Symposium.Title") + System.currentTimeMillis();
		test.maps_sessionpage.enterValuesInCreateSymposium(symposiumTitle,
				YamlReader.getYamlValue("Session.Symposium.Type"));
		test.maps_sessionpage.clickOnButtonUnderSessionModule("Save");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Add/Edit Hosts");
		test.maps_sessionpage.addHostforSymposium();
		test.maps_sessionpage.addRoleForHost(YamlReader.getYamlValue("Session.Symposium.Host_Role"));
		test.maps_sessionpage.clickOnButtonUnderSessionModule("Save");
		test.maps_sessionpage.clickOnButtonUnderSessionModule("Close");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Successfully Saved");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Add/Remove/Withdraw Abstracts");
		test.maps_sessionpage.searchAbstract("author_first_name",
				YamlReader.getYamlValue("Session.Symposium.Abstract_AuthorFirstName"));
		test.maps_sessionpage.searchAbstract("author_last_name",
				YamlReader.getYamlValue("Session.Symposium.Abstract_AuthorLastName"));
		test.maps_sessionpage.clickOnButtonUnderSessionModule("Search");
		test.maps_sessionpage.addAbstractsInCurrentlyAssignedAbstractsSection();
		test.maps_sessionpage.clickOnButtonUnderSessionModule("Save");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Successfully Saved");
		test.maps_sessionpage.clickOnButtonUnderSessionModule("Close");
		test.maps_reviewpage.enterValueInFilter(symposiumTitle);
		test.maps_sessionpage.verifyTheResultOfFilter("session_name", symposiumTitle);
	}

	// @Test //passed
	public void MAPS_Session_1524_And_1527_Verify_that_the_application_saves_the_session_on_clicking_Save_button() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Symposia");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Create New Symposium");
		test.maps_sessionpage.verifyPopupMessage("Create Symposium");
		symposiumTitle = YamlReader.getYamlValue("Session.Symposium.Title") + System.currentTimeMillis();
		test.maps_sessionpage.enterValuesInCreateSymposium(symposiumTitle,
				YamlReader.getYamlValue("Session.Symposium.Type"));
		test.maps_sessionpage.clickOnButtonUnderSessionModule("Save");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Add/Edit Hosts");
		test.maps_sessionpage.addHostforSymposium();
		test.maps_sessionpage.addRoleForHost(YamlReader.getYamlValue("Session.Symposium.Host_Role"));
		test.maps_sessionpage.clickOnButtonUnderSessionModule("Save & Close");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Successfully Saved");
		// symposiumTitle="Test_Symposium_Title1491399157076";
		test.maps_reviewpage.enterValueInFilter(symposiumTitle);
		test.maps_sessionpage.verifyTheResultOfFilter("session_name", symposiumTitle);
	}

	// @Test functionality is not verified
	public void MAPS_Session_1531_Verify_application_successfully_deletes_record_on_clicking_Yes_button_on_confirmation_popup() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Symposia");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Create New Symposium");
		test.maps_sessionpage.verifyPopupMessage("Create Symposium");
		symposiumTitle = YamlReader.getYamlValue("Session.Symposium.Title") + System.currentTimeMillis();
		test.maps_sessionpage.enterValuesInCreateSymposium(symposiumTitle,
				YamlReader.getYamlValue("Session.Symposium.Type"));
		test.maps_sessionpage.clickOnButtonUnderSessionModule("Save & Close");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Successfully Saved");
		test.maps_reviewpage.enterValueInFilter(symposiumTitle);
		test.maps_sessionpage.verifyTheResultOfFilter("session_name", symposiumTitle);
		test.maps_sessionpage.selectaRecordFromTheList(1);
		test.maps_sessionpage.clickOnButtonUnderSessionModule("Delete Selected");
		test.maps_sessionpage.verifyPopupMessage("User Information");
		test.maps_sessionpage.clickOnButtonUnderSessionModule("OK");
		test.maps_reviewpage.enterValueInFilter(symposiumTitle);
		test.maps_sessionpage.verifyDataIsDeleted("session_name", symposiumTitle);
		test.maps_sessionpage.verifyTheResultOfFilter("session_name", symposiumTitle);
	}
	
	//@Test //passed
	public void MAPS_Session_1551_Verify_that_application_downloads_the_relevant_file_on_clicking_Download_options(){
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Symposia");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Import / Export");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Import Symposium");
		test.maps_sessionpage.verifyPopupMessage("Import Symposia");
		test.maps_sessionpage.clickOnDownloadButtonAndVerifyValidFileIsDownloaded("Download template",
				YamlReader.getYamlValue("Session.Symposium.File_Download_template"));
		//YamlReader.getYamlValue("Session.Symposium.Host_Role")
		test.maps_sessionpage.clickOnDownloadButtonAndVerifyValidFileIsDownloaded("Download resources",
				YamlReader.getYamlValue("Session.Symposium.File_Download_resources"));
		test.maps_sessionpage.clickOnDownloadButtonAndVerifyValidFileIsDownloaded("Download Hosts",
				YamlReader.getYamlValue("Session.Symposium.File_Download_Hosts"));
		test.maps_sessionpage.clickOnDownloadButtonAndVerifyValidFileIsDownloaded("Download Session Owners",
				YamlReader.getYamlValue("Session.Symposium.File_Download_Session_Owners"));
		test.maps_sessionpage.clickOnButtonUnderSessionModule("Cancel");
	}
	
	@Test //passed
	public void MAPS_Session_1566_Verify_application_adds_a_new_sort_criteria_on_clicking_Add_button(){
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Symposia");
		test.maps_sessionpage.clickOnArrowButton("Title");		
		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Configure Sort");
		test.maps_sessionpage.verifyPopupMessage("Sort");
		test.maps_sessionpage.clickOnAddButton("Add");
		test.maps_sessionpage.selectColumnForSorting("Title");
		test.maps_sessionpage.clickOnSaveButton("Apply");
		test.maps_sessionpage.clickOnArrowButton("Title");		
		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Configure Sort");
		test.maps_sessionpage.verifyPopupMessage("Sort");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Title");
		test.maps_sessionpage.clickOnSaveButton("Close");
	}
	
	@Test
	public void MAPS_Session_1569_Verify_application_allows_user_to_delete_the_criteria_added(){
//		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
//		test.maps_sessionpage.clickButtononLeftNavigationPanel("Symposia");
//		test.maps_sessionpage.clickOnArrowButton("Title");		
//		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Configure Sort");
//		test.maps_sessionpage.verifyPopupMessage("Sort");
		test.maps_sessionpage.clickOnAddButton("Add");
		test.maps_sessionpage.selectColumnForSorting("Creator");
//		test.maps_sessionpage.clickOnSaveButton("Apply");
//		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Configure Sort");
//		test.maps_sessionpage.verifyPopupMessage("Sort");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Creator");
		test.maps_sessionpage.selectRoleOnSaveGridConfiguration("Criteria","Creator" );	
		test.maps_sessionpage.clickOnAddButton("Delete");
		test.maps_sessionpage.clickOnSaveButton("Close");
	}
}
