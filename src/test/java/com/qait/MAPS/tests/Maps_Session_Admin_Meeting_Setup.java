package com.qait.MAPS.tests;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class Maps_Session_Admin_Meeting_Setup extends BaseTest {
	private String maps_url, gridName, lastRecordData, roomName,ProgramTitle;
	private String[] roles = { "OPA Staff", "Program Viewer", "Program Chair Sessioning", "Abstract Editor",
			"Session Admin" };
	private String[] leftPanelOptionsSessionAdmin = {"Meeting Setup", "Sessioning",
			"Invitations & Email", "Reports", "Data Export" };
	private String[] importType = { "Download template", "Download resources", "Browse", "Import", "Cancel" };
	private String[] buttonProgramArea = { "Add Program Area", "Delete Program Areas", "Export to Excel",
			"Import Program Areas" };
	private String[] programAreaTableHeader = { "Program Area Name", "Type", "Color", "Abstract Submission Role",
			"Owners" };
	private String[] filterDropDownsRoom = { "Add Room", "Delete Room" };
	private String program_area_name = "program_area" + System.currentTimeMillis();
	private String[] fieldsAddRoom = {"Cancel", "Save"};
	private String[] fieldsRoom = { "Close", "Save" };
	private String[] filterDropDownRoomAvailability = { "Add Availability", "Delete Availability" };
	List<String> sortColumnList = new ArrayList<String>();
	List<String> controlId = new ArrayList<String>();

	List<String> abstractDetails;
	Map<String, String> eventInfo = new HashMap<>();
	
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

	@Test // passed
	public void Step_0004_MAPS_Session_1_Click_On_Session_In_Top_Navigation_Menu() {
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Session");
		test.maps_SSOPage.verifyUserIsOnTabPage("Session");
		test.maps_reviewpage.verifyPageHeader("Multiple Role Selection");
		test.maps_sessionpage.verifyApplicationDisplaysRadioButtonOnClickingSessionTab(roles);
	}

	@Test // page title is not available // passed
	public void Step_0798_MAPS_Session_784_Verify_Application_navigates_to_Session_Admin_page_on_selecting_the_Session_Admin_radio_button() {
		test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("Session Admin");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Select");
	}

	@Test // passed
	public void Step_0799_MAPS_Session_785_Verify_options_available_on_Session_Admin_page() {
		test.maps_reviewpage.verifybuttonOnRolesPage("Set Preferences");
		test.maps_sessionpage.verifyLeftPanelOptionsOnSessionAdminPage(leftPanelOptionsSessionAdmin);
	}

	@Test // passed
	public void Step_0807_MAPS_Session_791_Application_navigates_the_user_to_the_Save_Grid_Configuration_on_clicking_the_Save_Edit_link() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Meeting Setup");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Programs");
		test.maps_reviewpage.selectExistingConfigurationFromGridConfigurationDropdown(0,0);
		test.maps_reviewpage.clickOnButton("Save/Edit");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Save Grid Configuration");
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Close");
	}

	@Test // Passed
	public void Step_0815_MAPS_Session_799_Verify_that_application_filters_the_result_on_the_basis_of_the_criteria_provided() {
		String programID = test.maps_sessionpage.getValueFromProgramsTable();
		test.maps_reviewpage.enterValueInFilter(programID);
		test.maps_sessionpage.verifyAddedDetails("program_id", programID);
	}

	@Test // passed
	public void Step_0831_MAPS_Session_815_Verify_that_application_prompts_the_user_to_save_the_program_before_adding_the_owners() {
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

//	@Test // issue reported(// not verified functionality)
	public void Step_0863_MAPS_Session_847_Verify_that_application_navigates_to_Add_Edit_Rooms_tab_on_Saving_all_Information() {
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Create Program");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Create Program");
		test.maps_sessionpage.enterValuesInCreateProgramPage(
				YamlReader.getYamlValue("Session.Programs.ProgramTitle") + System.currentTimeMillis(),
				YamlReader.getYamlValue("Session.Programs.Interval"));
		test.maps_sessionpage.clickOnButtonUnderGridConfiguration("Save");
//		test.maps_sessionpage.clickOnButtonUnderSessioning("Save");
		test.maps_sessionpage.verifyPopupMessage("Add/Edit Rooms");
	}

	@Test // passed
	public void Step_0891_MAPS_Session_875_Verify_that_the_application_Saves_data_and_creates_a_new_program_on_clicking_Save_button() {
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Create Program");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Create Program");
		ProgramTitle = YamlReader.getYamlValue("Session.Programs.ProgramTitle") + System.currentTimeMillis();
		test.maps_sessionpage.enterValuesInCreateProgramPage(ProgramTitle,
				YamlReader.getYamlValue("Session.Programs.Interval"));
		test.maps_sessionpage.clickOnButtonUnderGridConfiguration("Save");		
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Successfully Saved");
		test.maps_sessionpage.ScrollPage(0, -1000);
		test.maps_sessionpage.clickOnSessionBuilderTab("Add/Edit Rooms");
		test.maps_sessionpage.ScrollPage(0, +1000);
		test.maps_sessionpage.enterValuesInAddEditRooms(YamlReader.getYamlValue("Session.Programs.Room"));
		test.maps_sessionpage.ScrollPage(0, +1000);
		test.maps_sessionpage.clickOnButtonUnderGridConfiguration("Save");
		test.maps_sessionpage.clickOnButtonUnderGridConfiguration("Close");		
		test.maps_reviewpage.enterValueInFilter(ProgramTitle);
		test.maps_sessionpage.verifyFilterResults(ProgramTitle, 1, 3);
	}

	@Test // passed
	public void Step_0931_MAPS_Session_914_Verify_User_Navigates_To_Room_Availability_Page() {
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Session");
		test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("Session Admin");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Select");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Meeting Setup");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Room Availability");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Room Availability");
	}

	@Test // passed
	public void Step_0932_MAPS_Session_915_Verify_Sections_Are_Displayed_On_Room_Availability_Page() {
		test.maps_sessionpage.verifySectionsOnRoomAvailabilityPage("Rooms", 1);
		test.maps_sessionpage.verifySectionsOnRoomAvailabilityPage("Room Availability", 2);
	}

	@Test // passed
	public void Step_0936_MAPS_Session_919_Verify_Fields_Are_Displayed_Under_Rooms_Section() {
		test.maps_sessionpage.verifyFilterDropdwonOnRoomAvailabalityPage(2);
		test.maps_sessionpage.verifyFieldsOnRoomAvailablityPage("Save/Edit", 1);
		test.maps_sessionpage.verifyFieldsOnRoomAvailablityPage("Delete", 1);
	}

	@Test // passed
	public void Step_0937_MAPS_Session_920_Verify_Application_Displays_Filter_Results_On_Room_Availability_Page() {
		test.maps_sessionpage.clickOnDropDownImage(1);
		test.maps_sessionpage.selectRoleOnSaveGridConfiguration("Room", "Test Program BT");
		test.maps_sessionpage.waitForLoadingImageToDisappear("Loading...");
		test.maps_sessionpage.clickOnArrowButton("Room Name");
		test.maps_sessionpage.enterFilterText("Filters", "Test Room BT");
		test.maps_sessionpage.verifyFilterResults("Test Room BT", 1, 3);
	}

	@Test // passed
	public void Step_0938_MAPS_Session_921_Verify_New_Filter_Is_Added_Upon_Clicking_Save_Edit_Link() {
		String gridName = "Test" + System.currentTimeMillis();
		test.maps_sessionpage.clickOnSaveAndEditButton("Save/Edit", 1);
		test.maps_sessionpage.enterNameOnSaveGridConfiguration("Name:", gridName);
		test.maps_sessionpage.clickCheckboxOnSaveGridConfiguration("Added Filters");
		test.maps_sessionpage.clickCheckboxOnSaveGridConfiguration("Make available");
		test.maps_sessionpage.selectRoleOnSaveGridConfiguration("Role", "Session Admin");
		test.maps_sessionpage.clickOnButtonUnderGridConfiguration("Save");	
		test.maps_sessionpage.verifyFilterIsByDefaultSelected(gridName, 2);
		test.maps_sessionpage.verifyFilterResults("Test Room BT", 1, 3);
	}

	@Test // passed
	public void Step_0948_MAPS_Session_931_Verify_Buttons_Under_Filter_DropDown() {
		test.maps_sessionpage.verifyButtonsOnTypes(filterDropDownsRoom);
	}

	@Test // passed
	public void Step_0949_MAPS_Session_932_Verify_Fields_After_Clicking_Add_Room_Button() {		
		test.maps_sessionpage.clickOnButtonUnderSessioning("Add Room");
		test.maps_sessionpage.verifyInputTextField("name");
		test.maps_sessionpage.verifyInputTextField("venue");
		test.maps_sessionpage.verifyButtonsOnTypes(fieldsAddRoom);
	}

	@Test // passed
	public void Step_0950_MAPS_Session_933_Verify_User_Is_Able_To_Add_Meeting_Room() {
		roomName = "Test Room BT" + System.currentTimeMillis();
		String venueName = "Test Venue BT" + System.currentTimeMillis();
		test.maps_sessionpage.enterValueInInputtextField("name", roomName);
		test.maps_sessionpage.enterValueInInputtextField("venue", venueName);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Save");
		test.maps_sessionpage.waitForLoadingImageToDisappear("Loading...");
		test.maps_sessionpage.verifyRoomIsAdded(roomName, venueName);
	}

	@Test // passed
	public void Step_0983_MAPS_Session_966_Verify_Records_Are_Sorted_On_Criteria_Basis() {
		List<String> dataToBeSorted = test.maps_sessionpage.getTableData("1", "4");
		test.maps_sessionpage.clickOnArrowButton("Room Name");
		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Configure Sort");
		test.maps_sessionpage.verifyPopupMessage("Sort");
		test.maps_sessionpage.deleteExistingSortingCriteria("col-F_SORT_FIELD", "Building/Venue");
		test.maps_sessionpage.clickOnAddButton("Add");
		test.maps_sessionpage.selectColumnForSorting("Building/Venue");
		test.maps_sessionpage.clickOnButtonUnderGridConfiguration("Apply");
		List<String> sortedData = test.maps_sessionpage.getTableData("1", "4");
		test.maps_sessionpage.verifyDataIsSorted(dataToBeSorted, sortedData);
	}

	@Test // passed
	public void Step_1001_MAPS_Session_984_Verify_User_Is_Able_To_Assign_And_Unassign_Room() {
		test.maps_sessionpage.clickParticularRecordFromList(roomName, "2");
		test.maps_sessionpage.selectLastRecordFromList();
		lastRecordData = test.maps_sessionpage.getCheckedColumnData("last()", "1");
		test.maps_sessionpage.clickOnSaveButton("Multiple Assign / Unassign");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Assign selected availability to rooms");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Successfully Saved");
		test.maps_sessionpage.clickParticularRecordFromList(roomName, "2");
		test.maps_sessionpage.clickOnSaveButton("Multiple Assign / Unassign");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Unassign selected availability from rooms");
		test.maps_sessionpage
				.verifyPopupMessage("You are about to unassign selected Room Availability from rooms. Are you sure?");
		test.maps_sessionpage.clickOnSaveButton("Yes");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Successfully Deleted");
	}

	@Test // passed
	public void Step_1004_MAPS_Session_987_Verify_Fields_Are_Displayed_Under_Room_Availability_Section() {
		test.maps_sessionpage.verifyFilterDropdwonOnRoomAvailabalityPage(4);
		test.maps_sessionpage.verifyFieldsOnRoomAvailablityPage("Save/Edit", 2);
		test.maps_sessionpage.verifyFieldsOnRoomAvailablityPage("Delete", 2);
	}

	@Test // passed
	public void Step_1005_MAPS_Session_988_Verify_Application_Displays_Filter_Results_Under_Rooms_Availability_Section() {
		test.maps_sessionpage.clickOnArrowButton("Day/Date");
		test.maps_sessionpage.hoverOverColumnHeader("Filters");
		// test.maps_sessionpage.selectOptionsUnderColumnHeaders("Before");
		test.maps_sessionpage.hoverOverColumnHeader("Before");
		test.maps_sessionpage.selectCurrentDate();
		List<String> filteredData = test.maps_sessionpage.getColumnSpecificTableData("td-date");
		test.maps_sessionpage.verifyRoomsAreFilteredAccordingToDate(filteredData, "Before");
	}

	@Test //passed // unable to locate save button on save grid config pop-up
	public void Step_1006_MAPS_Session_989_Verify_Application_Displays_Filter_Results_Under_Rooms_Availability_Section() {
		gridName = "Test" + System.currentTimeMillis();
		test.maps_sessionpage.clickOnSaveAndEditButton("Save/Edit", 2);
		test.maps_sessionpage.enterNameOnSaveGridConfiguration("Name:", gridName);
		test.maps_sessionpage.clickCheckboxOnSaveGridConfiguration("Added Filters");
		test.maps_sessionpage.clickCheckboxOnSaveGridConfiguration("Make available");
		// test.maps_sessionpage.selectRoleOnSaveGridConfiguration("Session
		// Admin");
//		test.maps_sessionpage.clickOnButtonByIndexing("Save", "1");
		test.maps_sessionpage.ScrollPage(0, +1000);
		test.maps_sessionpage.clickOnButtonUnderGridConfiguration("Save");
		test.maps_sessionpage.verifyFilterIsByDefaultSelected(gridName, 4);
	}

	@Test //passed
	public void Step_1009_MAPS_Session_992_Verify_User_Is_Able_To_Delete_Selected_Filter() {
		test.maps_sessionpage.clickOnDropDownImage(4);
		test.maps_sessionpage.selectRoleOnSaveGridConfiguration(gridName);
		test.maps_sessionpage.clickOnSaveAndEditButton("Delete", 2);
		test.maps_sessionpage.clickOnSaveButton("Yes");
	}

	@Test //passed
	public void Step_1016_MAPS_Session_999_Verify_Buttons_Under_Filter_DropDown_For_Room_Availability() {
		test.maps_sessionpage.verifyButtonsOnTypes(filterDropDownRoomAvailability);
	}

//	@Test //failed
	public void Step_1017_MAPS_Session_1000_Verify_Buttons_Under_Filter_DropDown_For_Room_Availability_Section() {
		test.maps_sessionpage.verifyFilterDropdwonOnRoomAvailabalityPage(4);		
		test.maps_sessionpage.clickOnButtonUnderSessioning("Add Availability");
		test.maps_sessionpage.verifyInputTextField("date");
		test.maps_sessionpage.verifyInputTextField("start_time");
		test.maps_sessionpage.verifyInputTextField("end_time");
		test.maps_sessionpage.verifyButtonsUnderRoomAvailability(fieldsAddRoom);
	}

	@Test
	public void Step_1048_MAPS_Session_1031_Verify_Records_Are_Sorted_On_Criteria_Basis_Under_Room_Availability_Section() {
//		test.maps_sessionpage.clickOnDropDownImage(1);
//		test.maps_sessionpage.selectRoleOnSaveGridConfiguration("Room", "Test Program BT");
//		test.maps_sessionpage.waitForLoadingImageToDisappear("Loading...");
		List<String> dataBeforeSorting=new ArrayList<>();
		List<String> dataAfterSorting=new ArrayList<>();
		dataBeforeSorting.clear();
		dataAfterSorting.clear();
		dataBeforeSorting = test.maps_sessionpage.getColumnSpecificTableData("td-date");
		test.maps_sessionpage.clickOnArrowButton("Day/Date");
		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Configure Sort");
		test.maps_sessionpage.verifyPopupMessage("Sort");
		test.maps_sessionpage.deleteExistingSortingCriteria("col-F_SORT_FIELD", "Start Time");
		test.maps_sessionpage.deleteExistingSortingCriteria("col-F_SORT_FIELD", "Day/Date");
		test.maps_sessionpage.clickOnAddButton("Add");
		test.maps_sessionpage.selectColumnForSorting("Day/Date");// Room Name
		test.maps_sessionpage.clickOnButtonUnderGridConfiguration("Apply");
		dataAfterSorting = test.maps_sessionpage.getColumnSpecificTableData("td-date");
		test.maps_sessionpage.verifyDataIsSorted(dataBeforeSorting, dataAfterSorting);
	}

	@Test //failed
	public void Step_1068_MAPS_Session_1051_Verify_Application_Displays_Added_Room_Availability_Under_Selected_Room() {
		test.maps_sessionpage.clickParticularRecordFromList(roomName, "2");
		test.maps_sessionpage.selectLastRecordFromList();
		lastRecordData = test.maps_sessionpage.getCheckedColumnData("last()", "1");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Multiple Assign / Unassign");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Assign selected availability to rooms");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Successfully Saved");
		test.maps_sessionpage.clickOnPlusIcon(roomName);
		test.maps_sessionpage.verifyAddedDetails("name", lastRecordData);
	}

	@Test // passed
	public void Step_1074_MAPS_Session_1056_Verify_that_application_navigates_to_Types_page_on_clicking_Type_option_under_the_Meeting_Setup() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Types");
		test.maps_sessionpage.verifyPopupMessage("Types");
	}

	@Test // passed
	public void Step_1075_MAPS_Session_1057_Verify_options_available_on_the_Types_page() {
		String[] buttonTypes = { "Add Type", "Delete Types", "Export to Excel",
				"Import Session/Event/Symposium Types" };
		test.maps_sessionpage.verifyButtonsOnTypes(buttonTypes);
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Instructions");
		String[] typeTableHeader = { "Session / Event / Symposium Type Name", "Type", "Color" };
		test.maps_sessionpage.verifyColumnsOnTypesPage(typeTableHeader);
		test.maps_sessionpage.verifyListOfProgramAreas("Types");
	}

	@Test // passed
	public void Step_1083_MAPS_Session_1065_Verify_that_application_adds_Session_Detail_Type_on_clicking_the_Add_Session_Detail_Type_button() {
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

	@Test // passed
	public void Step_1098_MAPS_Session_1080_Verify_options_available_on_the_Import_Types_popup() {
		test.maps_sessionpage.clickOnButtonUnderSessioning("Import Session/Event/Symposium Types");
		test.maps_sessionpage.verifyPopupMessage("Import Types");
		test.maps_sessionpage.verifyButtonsOnTypes(importType);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Cancel");

	}

	@Test // passed
	public void Step_1124_MAPS_Session_1105_Verify_that_application_navigates_to_Program_Areas_page_on_clicking_Program_Area_under_Meeting_Setup() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Program Areas");
		test.maps_sessionpage.verifyPopupMessage("Program Areas");
	}

	@Test // passed
	public void Step_1125_MAPS_Session_1106_Verify_options_available_on_Program_Areas_page() {
		test.maps_sessionpage.verifyButtonsOnTypes(buttonProgramArea);
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Instructions");
		test.maps_reviewpage.verifyExpandIconUnderNamedModule();
		test.maps_sessionpage.verifyColumnsOnTypesPage(programAreaTableHeader);
		test.maps_sessionpage.verifyListOfProgramAreas("Program Area");
	}

	@Test // passed
	public void Step_1134_MAPS_Session_1115_Verify_Application_Displays_Appropriate_Search_Results_On_Providing__Search_Criteria() {
		test.maps_sessionpage.clickOnButtonUnderSessioning("Add Program Area");
		test.maps_sessionpage.enterNameOnSaveGridConfiguration("Program Area Name", program_area_name);
		test.maps_sessionpage.enterNameOnSaveGridConfiguration("Color",
				YamlReader.getYamlValue("Session.Program_Areas.Color"));
		test.maps_sessionpage
				.selectSessionTopicWhenAddingProgramArea(YamlReader.getYamlValue("Session.Program_Areas.Type"));
		test.maps_sessionpage.clickOnButtonUnderSessioning("Add Owners");

		test.maps_sessionpage
				.verifyAndAcceptProgramAreaAlertText(YamlReader.getYamlValue("Session.Program_Areas.Alert_Text"));

		test.maps_sessionpage.enterNameOnSaveGridConfiguration("Email Address",
				YamlReader.getYamlValue("Session.Program_Areas.Email Address"));
		test.maps_sessionpage.clickOnButtonUnderSessioning("Search");

		test.maps_sessionpage.verifyCorrectSearchResultsAreDisplayed("email",
				YamlReader.getYamlValue("Session.Program_Areas.Email Address"));
	}

	@Test //passed
	public void Step_1136_MAPS_Session_1117_Verify_Application_Add_Selected_Record_To_Current_Owner_Section() {
		test.maps_sessionpage.selectAvailableSearchRecord("3", "1");
		test.maps_sessionpage.ScrollPage(0, -300);	
		test.maps_sessionpage.clickOnButtonUnderSessioning("Add Selected");
		test.maps_sessionpage.verifyCorrectSearchResultsAreDisplayed("email",
				YamlReader.getYamlValue("Session.Program_Areas.Email Address"));

	}

	@Test
	public void Step_1160_MAPS_Session_1141_Verify_Application_Allow_User_To_Set_A_Role() {
		test.maps_sessionpage.ScrollPage(0, -600);
		test.maps_sessionpage.setRoleForTheUser(YamlReader.getYamlValue("Session.Program_Areas.role"));
	}

	@Test
	public void Step_1161_MAPS_Session_1142_Verify_application_saves_and_closes_popup_on_clicking_the_Save_and_Close_button() {
		test.maps_sessionpage.clickOnButtonByIndexing("Save and Close", "2");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Save and Close");
		test.maps_sessionpage.verifyProgramAreaIsAdded(program_area_name,
				YamlReader.getYamlValue("Session.Program_Areas.Type"),
				YamlReader.getYamlValue("Session.Program_Areas.Color"));
	}

	@Test
	public void Step_1170_MAPS_Session_1151_Verify_that_application_displays_Import_Program_Area_popup_on_clicking_Import_Program_Areas_button() {
		test.maps_sessionpage.clickOnButtonUnderSessioning("Import Program Areas");
		test.maps_sessionpage.verifyPopupMessage("Import Program Areas");
		test.maps_sessionpage.clickOnButtonUnderGridConfiguration("Cancel");

	}

	@Test
	public void Step_1200_MAPS_Session_1180_Verify_that_application_allows_to_select_different_grid_views_from_Grid_Configuration_dropdown() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Hosts");
		test.maps_sessionpage.verifyPopupMessage("Hosts");
		String gridConfig = test.maps_reviewpage.selectExistingConfigurationFromGridConfigurationDropdown(0,1);
		test.maps_reviewpage.verifyApplicationShouldAllowToSelectGridConfiguration(gridConfig);
	}

	@Test
	public void Step_1201_MAPS_Session_1181_Verify_that_application_navigates_to_the_SaveGridConfiguration_on_clicking_Save_Edit_link() {
		test.maps_reviewpage.clickOnButton("Save/Edit");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Save Grid Configuration");
		test.maps_sessionpage.clickOnButtonUnderGridConfiguration("Close");
	}

	@Test
	public void Step_1218_MAPS_Session_1198_Verify_application_allows_to_add_new_host_on_clicking_AddNewHost_button() {
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

	@Test
	public void Step_1298_MAPS_Session_1277_Application_should_navigate_to_the_Roles_page() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Roles");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Roles");
	}

	@Test
	public void Step_1300_MAPS_Session_1279_Application_should_display_Add_New_Role_popup_on_clicking_Add_role_button() {		
		test.maps_sessionpage.clickOnButtonUnderSessioning("Add Role");
		test.maps_sessionpage.verifyPopupMessage("Add New Role");
		test.maps_sessionpage.clickOnButtonByIndexing("Close", "1");
	}
	
	@Test //tc to delete created program
	public void Step_1300_MAPS_Session_879_Verify_Application_Successfully_Deletes_Program_On_Clicking_Yes_Button() {
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Session");
		test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("Session Admin");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Select");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Meeting Setup");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Programs");
		test.maps_sessionpage.clickParticularRecordFromList(ProgramTitle, "2");
		test.maps_sessionpage.clickOnSaveButton("Delete Selected Programs");
		test.maps_sessionpage.clickOnSaveButton("Yes");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Program(s) deleted");
	}
}
