package com.qait.MAPS.tests;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class Maps_Session_Tests extends BaseTest {

	private String griduniqueName = "Test_Grid_" + System.currentTimeMillis();
	private String maps_url, symposiumTitle, selectedsymposia, gridName, lastRecordData, roomName, sessionTitle,
			downloadedFilePath;

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
	private String[] filterDropDownsRoom = { "Add Room", "Delete Room" };
	private String program_area_name = "program_area" + System.currentTimeMillis();
	private String[] fieldsAddRoom = { "Cancel", "Save" };
	private String[] filterDropDownRoomAvailability = { "Add Availability", "Delete Availability" };
	private String[] fastColumnNames = { "Submission Role", "Control ID", "Edit Abstract", "Final ID", "Sessions",
			"Title", "Presenting Author", "Presentation Type", "Sci-Mix Consideration", "Symposia", "Abstract Status",
			"Date Submitted" };
	private String[] fullColumnNames = { "Submission Role", "Control ID", "Edit Abstract", "Final ID", "Sessions",
			"Title", "Presenting Author", "Presentation Type", "Sci-Mix Consideration", "Institutions (all)",
			"Symposia", "Avg Score", "Abstract Status", "Date Submitted" };

	List<String> sortColumnList = new ArrayList<String>();

	@DataProvider
	public String[][] getData() {
		String[][] data = new String[3][2];
		data[0][0] = YamlReader.getYamlValue("Session.Session_Builder.Session_Type1");
		data[0][1] = YamlReader.getYamlValue("Session.Session_Builder.Session_Type4");
		data[1][0] = YamlReader.getYamlValue("Session.Session_Builder.Session_Type3");
		data[1][1] = YamlReader.getYamlValue("Session.Session_Builder.Session_Type2");
		data[2][0] = YamlReader.getYamlValue("Session.Session_Builder.Session_Type2");
		data[2][1] = YamlReader.getYamlValue("Session.Session_Builder.Session_Type1");
		return data;
	}

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
	public void Step_0001_MAPS_1_Launch_Application() {
		test.launchMAPSApplication(maps_url);
		// test.maps_SSOPage.verifyUserIsOnMAPSHomePage("Meeting Abstracts
		// Programming System");
	}

	/**
	 * Test case : Submission class MAPS_3 : login functionality
	 */
	@Test
	public void Step_0002_MAPS_3_LogIn_With_Valid_Credentials() {
		test.maps_SSOPage.loginWithValidCredentials(YamlReader.getYamlValue("LogIn_Details.userID"),
				YamlReader.getYamlValue("LogIn_Details.password"));
	}

	@Test
	public void Step_0004_MAPS_Session_1_Click_On_Session_In_Top_Navigation_Menu() {
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Session");
		test.maps_SSOPage.verifyUserIsOnTabPage("Session");
		test.maps_reviewpage.verifyPageHeader("Multiple Role Selection");
		test.maps_sessionpage.verifyApplicationDisplaysRadioButtonOnClickingSessionTab(roles);
	}

	/**
	 * Session : OPA Staff
	 */
	//@Test
	public void Step_0010_MAPS_Session_6_User_Should_Navigate_To_Abstract_Page_On_Clicking_Go_Button() {
		test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("OPA Staff");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Select");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Abstracts Assigned To Me");

	}
	
	/**
	 * Session : OPA Staff
	 */
	//@Test
	public void Step_0011_MAPS_Session_7_Verify_Available_Options_On_Session_OPA_Staff_Page() {
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
	public void Step_0015_MAPS_Session_11_Verify_Application_Allows_User_To_Create_A_New_Grid() {
		test.maps_reviewpage.clickOnButton("Save/Edit");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Save Grid Configuration");
		test.maps_reviewpage.enterDetailsAtSaveGridConfigurationPage("Abstract_" + griduniqueName);
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Save");
	}
	

	/**
	 * Session : Program Viewer : Symposia
	 */
	//@Test
	public void Step_0021_MAPS_Session_17_Verify_Application_Filters_Result_On_Basis_Of_Criteria_For_OPA_Staff() {
		String recordName = test.maps_sessionpage.getRandomRecordFromTable("6");
		test.maps_reviewpage.enterValueInFilter(recordName);
		test.maps_sessionpage.verifyFilterResults(recordName, 1, 7);
		test.maps_reviewpage.clickOnCrossImageForNamedDropdown("Filter");
		// String
		// recordName=test.maps_sessionpage.getRandomRecordFromTable("6");
		// test.maps_reviewpage.enterValueInFilter(recordName);
		// test.maps_sessionpage.verifyFilterResults(recordName, 1, 7);
		// test.maps_reviewpage.clickOnCrossImageForNamedDropdown("Filter");
	}

	//@Test
	public void Step_0032_MAPS_Session_28_Verify_Application_Exports_Relevant_Csv_File_On_Clicking_Any_Otion_Under_Export_To_Excel_Dropdown() {
		test.maps_sessionpage.clickOnButtonUnderSessioning("Export to Excel");
		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Export to Excel (All Columns)");
		test.maps_sessionpage.waitForProcessBarToDisappear();
		test.maps_sessionpage.clickOnDownloadButtonAndVerifyValidFileIsDownloaded("Export to Excel (All Columns)",
				YamlReader.getYamlValue("Session.OPA_Staff.File_Downloaded_Name"));
		test.maps_sessionpage.clickOnButtonUnderSessioning("Export to Excel");
		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Export to Excel (Displayed Columns)");
		test.maps_sessionpage.waitForProcessBarToDisappear();
		test.maps_sessionpage.clickOnDownloadButtonAndVerifyValidFileIsDownloaded("Export to Excel (Displayed Columns)",
				YamlReader.getYamlValue("Session.OPA_Staff.File_Downloaded_Name"));
	}



	//@Test
	public void Step_0041_MAPS_Session_37_Verify_Edit_Link_Is_Present_For_Abstracts_And_User_Is_Navigated_To_Abstracts_Page_On_Clicking_Finish_Button() {
		test.maps_sessionpage.clickOnArrowButton("Edit Abstract");
		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Sort Ascending");
		test.maps_sessionpage.verifyFilterResults("Edit", 1, 4);
		test.maps_submissionPage.submitTitleAndBodyDetails("Test Title", "Test Abstract");
		test.maps_submissionPage.uploadImage("test.jpeg");
		test.maps_submissionPage.clickOnSaveAndContinueButton();
		test.maps_submissionPage.verifyPageHeaderForASection("Properties");
		test.maps_submissionPage.clickOnSaveAndContinueButton();
		test.maps_submissionPage.verifyPageHeaderForASection("Authors");
		test.maps_submissionPage.clickOnSaveAndContinueButton();
		test.maps_submissionPage.verifyPageHeaderForASection("Disclosures");
		test.maps_submissionPage.clickOnSaveAndContinueButton();
		test.maps_submissionPage.verifyPageHeaderForASection("Review & Submit");
		test.maps_submissionPage.clickOnNamedButton("Finish");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Abstracts Assigned To Me");
	}

	@Test
	public void Step_0072_MAPS_Session_67_Click_On_Session_And_Navigate_To_Program_Viewer() {
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Session");
		test.maps_SSOPage.verifyUserIsOnTabPage("Session");
		test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("Program Viewer");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Select");
		test.maps_reviewpage.verifybuttonOnRolesPage("Set Preferences");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_sessionpage.verifyLeftPanelOptionsOnSessionAdminPage(programViewerleftpaneloptions);
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Symposia Viewer");
	}
	
	/**
	 * Session : Program Viewer : Symposia
	 */
	@Test
	public void Step_0076_MAPS_Session_70_Verify_Available_Options_On_Session_Program_Viewer_Page() {
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
		//test.maps_sessionpage.
	}

	/**
	 * Session : Program Viewer : Symposia
	 */
	@Test
	public void Step_0080_MAPS_Session_74_Verify_Application_Allows_User_To_Create_A_New_Grid_In_Symposia() {
		test.maps_reviewpage.clickOnButton("Save/Edit");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Save Grid Configuration");
		test.maps_reviewpage.enterDetailsAtSaveGridConfigurationPage(griduniqueName);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Save");
		test.maps_sessionpage.waitForProcessBarToDisappear();
	}
	
	/**
	 * Session : Program Viewer : Symposia
	 */
	@Test
	public void Step_0086_MAPS_Session_80_Verify_Application_Should_Filter_Results_On_The_Basis_Of_Criteria_Program_Viewer() {
		String recordName = test.maps_sessionpage.getRandomRecordFromTable("3");
		test.maps_reviewpage.enterValueInFilter(recordName);
		test.maps_sessionpage.verifyFilterResults(recordName, 1, 4);
		test.maps_reviewpage.clickOnCrossImageForNamedDropdown("Filter");
	}
	
	/**
	 * Session : Program Viewer : Symposia
	 */
	@Test
	public void Step_0099_MAPS_Session_93_Verify_Application_Print_Selected_Symposia() {
		test.maps_sessionpage.waitForProcessBarToDisappear();
		test.maps_sessionpage.selectaRecordFromTheList(1);
		selectedsymposia = test.maps_sessionpage.getCheckedColumnData("1","3");  // have to add this function
		test.maps_sessionpage.clickOnButtonUnderSessioning("Print Selected");
		test.maps_sessionpage.verifyTitleForRoles("Print Symposia Preview");
		test.maps_sessionpage.verifyPrintPreviewTableContents(selectedsymposia);
		test.maps_submissionPage.clickOnNamedButton("Cancel");

	}
	
	/**
	 * Session : Program Viewer : Symposia
	 */
	@Test
	public void Step_0100_MAPS_Session_94_Verify_Application_Print_All_Symposia_on_Clicking_Print_Selected_Button() {
		int symposiasize;
		test.maps_sessionpage.selectHeaderCheckbox();
		symposiasize=test.maps_sessionpage.getSelectedListSize();
		test.maps_sessionpage.clickOnButtonUnderSessioning("Print Selected");
		test.maps_sessionpage.verifyTitleForRoles("Print Symposia Preview");
		test.maps_sessionpage.verifyAllSelectedListIsPresentInPrintPreview(symposiasize);
		test.maps_submissionPage.clickOnNamedButton("Cancel");
		
	}
	
	/**
	 * Session : Program Viewer : Symposia
	 */
	@Test
	public void Step_0103_MAPS_Session_97_Verify_Application_Download_Csv_File_On_Clicking_Export_To_Excel_Dropdown_On_Program_Viewer() {
		test.maps_sessionpage.clickOnButtonUnderSessioning("Export");
		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Export to Excel (Displayed Columns)");
		test.maps_sessionpage.waitForProcessBarToDisappear();
		test.maps_sessionpage.verifyValidFileIsDownloaded(YamlReader.getYamlValue("Session.Program_Viewer.File_Downloaded_Name"));
	}
	
	@Test
	public void Step_0104_MAPS_Session_98_Verify_Application_Download_Csv_File_On_Clicking_Export_To_Excel_Dropdown_On_Program_Viewer() {
		test.maps_sessionpage.clickOnButtonUnderSessioning("Export");
		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Export to Excel (All Columns)");
		test.maps_sessionpage.waitForProcessBarToDisappear();
		test.maps_sessionpage.verifyValidFileIsDownloaded(YamlReader.getYamlValue("Session.Program_Viewer.File_Downloaded_Name"));
	}
	
	@Test
	public void Step_0115_MAPS_Session_109_Verify_Application_Sorts_The_Results_On_The_Basis_Of_Criteria_Program_Viewer()
	{
		List<String> dataBeforeSorting = test.maps_sessionpage.getTableData("1", "4");
		test.maps_sessionpage.clickOnArrowButton("Title");
		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Configure Sort");
		test.maps_sessionpage.verifyPopupMessage("Sort");
		test.maps_sessionpage.clickOnAddButton("Add");
		test.maps_sessionpage.selectColumnForSorting("Title");													
		test.maps_sessionpage.clickOnButtonUnderSessioning("Apply");
		List<String> dataAfterSorting = test.maps_sessionpage.getTableData("1", "4");
		test.maps_sessionpage.verifyDataIsSorted(dataBeforeSorting, dataAfterSorting);
	}
	
	@Test
	public void Step_0178_MAPS_Session_171_Verify_Available_Options_On_Session__Viewer_Under_Program_Viewer_Page() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Session Viewer");
		Step_0076_MAPS_Session_70_Verify_Available_Options_On_Session_Program_Viewer_Page();
		
	}
	
	@Test
	public void Step_0182_MAPS_Session_175_Verify_Application_Allows_User_To_Create_A_New_Grid_On_Session__Viewer_Under_Program_Viewer_Page() {
		test.maps_reviewpage.clickOnButton("Save/Edit");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Save Grid Configuration");
		test.maps_reviewpage.enterDetailsAtSaveGridConfigurationPage("Session_" + griduniqueName);
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Save");
		
	}
	
	@Test
	public void Step_0188_MAPS_Session_181_Verify_that_application_filters_the_result_on_the_basis_of_the_criteria_provided_Session_Viewer()
	{
		String recordName = test.maps_sessionpage.getRandomRecordFromTable("4");
		test.maps_reviewpage.enterValueInFilter(recordName);
		test.maps_sessionpage.verifyFilterResults(recordName, 1, 5);
		test.maps_reviewpage.clickOnCrossImageForNamedDropdown("Filter");
	}
	
	@Test
	public void Step_0204_MAPS_Session_197_Verify_Application_Print_Selected_Symposia() {
		String selectedsession;
		test.maps_sessionpage.waitForProcessBarToDisappear();
		test.maps_sessionpage.selectaRecordFromTheList(1);
		selectedsession = test.maps_sessionpage.getCheckedColumnData("1","3");  // have to add this function
		test.maps_sessionpage.clickOnButtonUnderSessioning("Print Selected");
		test.maps_sessionpage.verifyTitleForRoles("Print Sessions/Events Preview");
		test.maps_sessionpage.verifyPrintPreviewTableContents(selectedsession);
		test.maps_submissionPage.clickOnNamedButton("Cancel");

	}
	
	/**
	 * Session : Program Viewer : Symposia
	 */
	@Test
	public void Step_0205_MAPS_Session_198_Verify_Application_Print_All_Symposia_on_Clicking_Print_Selected_Button() {
		int symposiasize;
		test.maps_sessionpage.selectHeaderCheckbox();
		symposiasize=test.maps_sessionpage.getSelectedListSize();
		test.maps_sessionpage.clickOnButtonUnderSessioning("Print Selected");
		test.maps_sessionpage.verifyTitleForRoles("Print Sessions/Events Preview");
		test.maps_sessionpage.verifyAllSelectedListIsPresentInPrintPreview(symposiasize);
		test.maps_submissionPage.clickOnNamedButton("Cancel");
		
	}
	
	@Test
	public void Step_3173_MAPS_Session_3147_Verify_application_displays_appropriate_search_results_on_clicking_Search_button_after_providing_valid_search_criteria() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Schedule Sessions");
		test.maps_sessionpage.verifyPopupMessage("Select Program");
		test.maps_sessionpage.selectValueForCreateNewSessionFromSymposium("Programs",
				YamlReader.getYamlValue("Session.Schedule_Sessions.Programs"));
		test.maps_sessionpage.clickOnButtonByIndexing("OK","1");
		test.maps_sessionpage.rightClickOnSession();
		test.maps_sessionpage.clickButtonToContinueToNextPage("Edit Item");
		test.maps_sessionpage.verifyPopupMessage("Edit Session");
		test.maps_sessionpage.enterValuesForProgram("session_name",
				YamlReader.getYamlValue("Session.Schedule_Sessions.Session_Title")+System.currentTimeMillis());
		test.maps_sessionpage.clickOnButtonByIndexing("Save","1");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Add/Edit Hosts");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Search for New Hosts");
		test.maps_sessionpage.verifyPopupMessage("Search Hosts");		
		test.maps_sessionpage.searchAbstract("session_host_first_name",
				YamlReader.getYamlValue("Session.Schedule_Sessions.owner_first_name"));
		test.maps_sessionpage.searchAbstract("session_host_last_name",
				YamlReader.getYamlValue("Session.Schedule_Sessions.owner_last_name"));
		test.maps_sessionpage.clickOnButtonUnderSessioning("Search");
		test.maps_sessionpage.verifyAddedDetails("session_host_first_name",
				YamlReader.getYamlValue("Session.Schedule_Sessions.owner_first_name"));
		test.maps_sessionpage.verifyAddedDetails("session_host_last_name",
				YamlReader.getYamlValue("Session.Schedule_Sessions.owner_last_name"));
	
	}
	
	@Test	
	public void Step_3177_MAPS_Session_3151_Verify_application_adds_selected_host_under_CurrentHost_section_on_clicking_the_Add_hosts_to_current_session_button (){
		test.maps_sessionpage.selectaRecordFromTheList(1);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Add Hosts to Current Session");
		test.maps_sessionpage.clickOnButtonByIndexing("Close","2");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Add/Edit Hosts");
		//test.maps_sessionpage.verifyFilterResults(filterResult, index, columnIndex);
		test.maps_sessionpage.verifyAddedDetails("session_host_first_name", 
				YamlReader.getYamlValue("Session.Schedule_Sessions.owner_first_name"));
		test.maps_sessionpage.verifyAddedDetails("session_host_last_name", 
				YamlReader.getYamlValue("Session.Schedule_Sessions.owner_last_name"));
	}
	
	
	public void Step_3178_MAPS_Session_3152_Verify_application_allows_user_to_add_a_new_host_by_clicking_Add_New_Host_button(){
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Schedule Sessions");
		test.maps_sessionpage.verifyPopupMessage("Select Program");
		test.maps_sessionpage.selectValueForCreateNewSessionFromSymposium("Programs",
				YamlReader.getYamlValue("Session.Schedule_Sessions.Programs"));
		test.maps_sessionpage.clickOnButtonUnderSessioning("OK");
		test.maps_sessionpage.rightClickOnSession();
		test.maps_sessionpage.clickButtonToContinueToNextPage("Edit Item");
		test.maps_sessionpage.verifyPopupMessage("Edit Session");
		test.maps_sessionpage.enterValuesForProgram("session_name",
				YamlReader.getYamlValue("Session.Schedule_Sessions.Session_Title")+System.currentTimeMillis());
		test.maps_sessionpage.clickOnButtonUnderSessioning("Save");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Add/Edit Hosts");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Search for New Hosts");
		test.maps_sessionpage.verifyPopupMessage("Search Hosts");		
		test.maps_sessionpage.searchAbstract("session_host_first_name",
				YamlReader.getYamlValue("Session.Schedule_Sessions.owner_first_name"));
		test.maps_sessionpage.clickOnButtonUnderSessioning("Search");
		test.maps_sessionpage.verifyAddedDetails("session_host_first_name",
				YamlReader.getYamlValue("Session.Schedule_Sessions.owner_first_name"));
		test.maps_sessionpage.selectaRecordFromTheList(1);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Add New Host");
		String email = YamlReader.getYamlValue("Session.Host.Email") + System.currentTimeMillis() + "@acs.org";
		test.maps_sessionpage.enterValuesInAddNewHost(
				YamlReader.getYamlValue("Session.Host.First_Name") + System.currentTimeMillis(),
				YamlReader.getYamlValue("Session.Host.Last_Name") + System.currentTimeMillis(), email,
				YamlReader.getYamlValue("Session.Host.institution"));
		test.maps_sessionpage.clickOnButtonByIndexing("Save", "2");
		test.maps_sessionpage.verifyAddedDetails("session_host_email",email);
		test.maps_sessionpage.verifyAddedDetails("session_host_institution",YamlReader.getYamlValue("Session.Host.institution"));
	
	}
	
	public void Step_3227_MAPS_Session_3201_Verify_application_adds_hosts_in_Current_Hosts_section_if_user_drags_and_drops_records_from_Available_Hosts_section(){
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Schedule Sessions");
		test.maps_sessionpage.verifyPopupMessage("Select Program");
		test.maps_sessionpage.selectValueForCreateNewSessionFromSymposium("Programs",
				YamlReader.getYamlValue("Session.Schedule_Sessions.Programs"));
		test.maps_sessionpage.clickOnButtonUnderSessioning("OK");
		test.maps_sessionpage.rightClickOnSession();
		test.maps_sessionpage.clickButtonToContinueToNextPage("Edit Item");
		test.maps_sessionpage.verifyPopupMessage("Edit Session");
		test.maps_sessionpage.enterValuesForProgram("session_name",
				YamlReader.getYamlValue("Session.Schedule_Sessions.Session_Title")+System.currentTimeMillis());
		test.maps_sessionpage.clickOnButtonUnderSessioning("Save");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Add/Edit Hosts");
		String hostLastname=test.maps_sessionpage.addHostforSymposium("session_host_last_name");
		test.maps_sessionpage.verifyAddedDetails("session_host_email",hostLastname);
	}
	
	public void Step_3228_MAPS_Session_3202_Verify_application_sorts_current_hosts_section_on_clicking_column_headers(){
		List<String> dataBeforeSorting = test.maps_sessionpage.getTableData("1", "17");
		test.maps_sessionpage.clickOnColumnHeaders("# of Assigned to Sessions");
		List<String> dataAfterSorting = test.maps_sessionpage.getTableData("1", "17");
		test.maps_sessionpage.verifyDataIsSorted(dataBeforeSorting, dataAfterSorting);
	}
	
	public void Step_3237_MAPS_Session_3211_Verify_application_only_removes_record_on_clicking_Yes_button_on_the_confirmation_popup(){
		test.maps_reviewpage.enterValueInFilter(YamlReader.getYamlValue("Session.Schedule_Sessions.owner_email"));
		String value=test.maps_sessionpage.addHostforSymposium("session_host_email");
		test.maps_sessionpage.verifyAddedDetails("session_host_email", value);
		test.maps_sessionpage.selectAbstractForEditing(2, 2);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Remove Selected");
		test.maps_sessionpage.verifyPopupMessage("Confirm");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Yes");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Information");
	
	}
	
	public void Step_3252_MAPS_Session_3226_Verify_application_successfully_assigns_selected_abstracts_on_clicking_AssignSelectedAbstracts(){
		
	}

	
	
	
}
