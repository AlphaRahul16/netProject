package com.qait.MAPS.tests;

import java.awt.AWTException;
import java.io.File;
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
import com.qait.automation.utils.CSVFileReaderWriter;
import com.qait.automation.utils.YamlReader;

public class Maps_Session_Admin_Sessioning_Tests extends BaseTest {

	private String maps_url, symposiumTitle, sessionName, sessionTitle, eventname, finalID, email, sessionBuilderTitle,
			levelName;
	private String[] roles = { "OPA Staff", "Program Viewer", "Program Chair Sessioning", "Abstract Editor",
			"Session Admin" };
	private String[] leftPanelOptionsSessionAdmin = { "Dashboard & Instructions", "Meeting Setup", "Sessioning",
			"Invitations & Email", "Reports", "Data Export" };
	private String[] optionSessioning = { "Symposia", "Sessions & Events", "Symposia Viewer", "Session Viewer",
			"Session Builder", "Schedule Sessions", "Abstracts" };
	private String program_area_name = "program_area" + System.currentTimeMillis();
	private String[] abstractTypes = { "Placeholders", "Withdrawn Presentations", "Stubs" };
	List<String> sortColumnList = new ArrayList<String>();
	List<String> controlId = new ArrayList<String>();
	List<String> abstractDetails;
	Map<String, String> eventInfo = new HashMap<>();
	List<String> dataofFile = new ArrayList<String>();
	private String timeDurationLabel, hashtag;
	private String downloadedFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "test" + File.separator + "resources" + File.separator + "DownloadedFiles";

	private String itenarySessionName;
	private List<String> colHeader;
	private String downloadedFile;

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
		test.maps_sessionpage.clickButtonToContinueToNextPage("Select");
		// page title is not available
		// test.maps_sessionpage.verifyTitleForRoles("Session Admin");
	}

	@Test // passed
	public void Step_0799_MAPS_Session_785_Verify_options_available_on_Session_Admin_page() {

		test.maps_reviewpage.verifybuttonOnRolesPage("Set Preferences");
		test.maps_sessionpage.verifyLeftPanelOptionsOnSessionAdminPage(leftPanelOptionsSessionAdmin);
	}

	@Test // passed
	public void Step_1330_MAPS_Session_1308_Verify_sub_options_under_Sessioning_option() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_sessionpage.verifyLeftPanelOptionsOnSessionAdminPage(optionSessioning);
	}

	@Test // passed
	public void Step_1333_MAPS_Session_1310_Available_Options_On_Session_Admin_Sessioning_Symposia_Page() {
		test.maps_sessionpage.clickSubHeadingLeftNavigationPanel("Symposia");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Symposia");
		test.maps_reviewpage.verifyLinksUnderNamedModule("Save/Edit");
		test.maps_reviewpage.verifyLinksUnderNamedModule("Delete");
		test.maps_reviewpage.verifyTextField("Filter");
		test.maps_reviewpage.verifyCrossImageForNamedDropDown("Filter");
		test.maps_reviewpage.verifyExpandIconUnderNamedModule();
		test.maps_reviewpage.verifyDropDown("Found In");
		test.maps_reviewpage.verifyDropDown("Export");
		test.maps_reviewpage.verifyDropDown("Toggle View");
		test.maps_reviewpage.verifyPaginationSectionAtTheBottomOfTheTable();
		test.maps_reviewpage.verifyDropDown("Records per page");
		test.maps_sessionpage.verifyRefreshButtonAtBottom();
	}

	@Test // not executed
	public void Step_1526_MAPS_Session_1503_Application_should_remove_the_selected_abstracts_when_user_clicks_on_Remove_Selected_button() {
		test.maps_sessionpage.clickOnButtonUnderSessioning("Create New Symposium");
		test.maps_sessionpage.verifyPopupMessage("Create Symposium");
		symposiumTitle = YamlReader.getYamlValue("Session.Symposium.Title") + System.currentTimeMillis();
		test.maps_sessionpage.enterValuesInCreateSymposium(symposiumTitle,
				YamlReader.getYamlValue("Session.Symposium.Type"));
		test.maps_sessionpage.clickOnButtonByIndexing("Save", "1");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Add/Edit Hosts");
		test.maps_sessionpage.addHostforSymposium("session_host_last_name");
		test.maps_sessionpage.addRoleForHost(YamlReader.getYamlValue("Session.Symposium.Host_Role"));
		test.maps_sessionpage.clickOnButtonByIndexing("Save", "1");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Successfully Saved");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Add/Remove/WithdrawAbstracts");
		test.maps_sessionpage.searchAbstract("author_first_name",
				YamlReader.getYamlValue("Session.Symposium.Abstract_AuthorFirstName"));
		test.maps_sessionpage.searchAbstract("author_last_name",
				YamlReader.getYamlValue("Session.Symposium.Abstract_AuthorLastName"));
		test.maps_sessionpage.clickOnButtonByIndexing("Search", "1");
		test.maps_sessionpage.addAbstractsInCurrentlyAssignedAbstractsSection();
		test.maps_sessionpage.selectAbstract("session_abstract_order");
		test.maps_sessionpage.clickOnButtonByIndexing("Remove Selected", "2");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Successfully Removed");
	}

	@Test // passed
	public void Step_1543_MAPS_Session_1520_Verify_that_the_application_saves_the_session_on_clicking_Save_button() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Symposia");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Create New Symposium");
		test.maps_sessionpage.verifyPopupMessage("Create Symposium");
		symposiumTitle = YamlReader.getYamlValue("Session.Symposium.Title") + System.currentTimeMillis();
		test.maps_sessionpage.enterValuesInCreateSymposium(symposiumTitle,
				YamlReader.getYamlValue("Session.Symposium.Type"));
		test.maps_sessionpage.clickOnButtonByIndexing("Save", "1");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Add/Edit Hosts");
		test.maps_sessionpage.addHostforSymposium("session_host_last_name");
		test.maps_sessionpage.addRoleForHost(YamlReader.getYamlValue("Session.Symposium.Host_Role"));
		test.maps_sessionpage.clickOnButtonByIndexing("Save", "1");
		test.maps_sessionpage.clickOnButtonByIndexing("Close", "1");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Successfully Saved");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Add/Remove/Withdraw Abstracts");
		test.maps_sessionpage.searchAbstract("author_first_name",
				YamlReader.getYamlValue("Session.Symposium.Abstract_AuthorFirstName"));
		test.maps_sessionpage.searchAbstract("author_last_name",
				YamlReader.getYamlValue("Session.Symposium.Abstract_AuthorLastName"));
		test.maps_sessionpage.clickOnButtonByIndexing("Search", "1");
		test.maps_sessionpage.addAbstractsInCurrentlyAssignedAbstractsSection();
		test.maps_sessionpage.clickOnButtonByIndexing("Save", "1");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Successfully Saved");
		test.maps_sessionpage.clickOnButtonByIndexing("Close", "1");
		test.maps_reviewpage.enterValueInFilter(symposiumTitle);
		test.maps_sessionpage.verifyAddedDetails("session_name", symposiumTitle);
	}

	@Test // passed
	public void Step_1546_MAPS_Session_1524_And_1527_Verify_that_the_application_saves_the_session_on_clicking_Save_button() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Symposia");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Create New Symposium");
		test.maps_sessionpage.verifyPopupMessage("Create Symposium");
		symposiumTitle = YamlReader.getYamlValue("Session.Symposium.Title") + System.currentTimeMillis();
		test.maps_sessionpage.enterValuesInCreateSymposium(symposiumTitle,
				YamlReader.getYamlValue("Session.Symposium.Type"));
		test.maps_sessionpage.clickOnButtonByIndexing("Save", "1");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Add/Edit Hosts");
		test.maps_sessionpage.addHostforSymposium("session_host_last_name");
		test.maps_sessionpage.addRoleForHost(YamlReader.getYamlValue("Session.Symposium.Host_Role"));
		test.maps_sessionpage.clickOnButtonByIndexing("Save & Close", "1");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Successfully Saved");
		// symposiumTitle="Test_Symposium_Title1491399157076";
		test.maps_reviewpage.enterValueInFilter(symposiumTitle);
		test.maps_sessionpage.verifyAddedDetails("session_name", symposiumTitle);
	}

	@Test // passed
	public void Step_1553_MAPS_Session_1531_Verify_application_successfully_deletes_record_on_clicking_Yes_button_on_confirmation_popup() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Symposia");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Create New Symposium");
		test.maps_sessionpage.verifyPopupMessage("Create Symposium");
		symposiumTitle = YamlReader.getYamlValue("Session.Symposium.Title") + System.currentTimeMillis();
		test.maps_sessionpage.enterValuesInCreateSymposium(symposiumTitle,
				YamlReader.getYamlValue("Session.Symposium.Type"));
		test.maps_sessionpage.clickOnButtonByIndexing("Save & Close", "1");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Successfully Saved");
		test.maps_reviewpage.enterValueInFilter(symposiumTitle);
		test.maps_sessionpage.verifyAddedDetails("session_name", symposiumTitle);
		test.maps_sessionpage.selectaRecordFromTheList(1, "2");
		test.maps_sessionpage.clickOnButtonByIndexing("Delete Selected", "1");
		test.maps_sessionpage.verifyPopupMessage("Confirm");
		test.maps_sessionpage.clickOnButtonByIndexing("Yes", "1");
		test.maps_reviewpage.enterValueInFilter(symposiumTitle);
		test.maps_sessionpage.verifyDataIsDeleted("session_name", symposiumTitle);
	}

	@Test // passed
	public void Step_1567_MAPS_Session_1545_Verify_application_print_Page_On_Clicking_Print_Button() {
		String selectedsymposia, selected_title;
		test.maps_sessionpage.waitForProcessBarToDisappear();
		test.maps_sessionpage.selectaRecordFromTheList(1, "2");
		selected_title = test.maps_sessionpage.getCheckedColumnData("1", "3");
		selectedsymposia = test.maps_sessionpage.getCheckedColumnData("1", "4"); // have
																					// to
																					// add
																					// this
																					// function
		test.maps_sessionpage.clickOnButtonUnderSessioning("Print Selected");
		test.maps_sessionpage.verifyTitleForRoles("Print Symposia Preview");
		test.maps_sessionpage.verifyPrintPreviewTableContents(selectedsymposia);
		test.maps_sessionpage.verifyPrintPreviewTableContents(selected_title);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Cancel");
	}

	@Test // passed
	public void Step_1573_MAPS_Session_1551_Verify_that_application_downloads_the_relevant_file_on_clicking_Download_options() {
		// test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Symposia");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Import / Export");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Import Symposium");
		test.maps_sessionpage.verifyPopupMessage("Import Symposia");
		test.maps_sessionpage.clickOnDownloadButtonAndVerifyValidFileIsDownloaded("Download template",
				YamlReader.getYamlValue("Session.Symposium.File_Download_template"), downloadedFilePath);
		test.maps_sessionpage.clickOnDownloadButtonAndVerifyValidFileIsDownloaded("Download resources",
				YamlReader.getYamlValue("Session.Symposium.File_Download_resources"), downloadedFilePath);
		test.maps_sessionpage.clickOnDownloadButtonAndVerifyValidFileIsDownloaded("Download Hosts",
				YamlReader.getYamlValue("Session.Symposium.File_Download_Hosts"), downloadedFilePath);
		test.maps_sessionpage.clickOnDownloadButtonAndVerifyValidFileIsDownloaded("Download Session Owners",
				YamlReader.getYamlValue("Session.Symposium.File_Download_Session_Owners"), downloadedFilePath);

	}

	@Test // not passed
	public void Step_1575_MAPS_Session_1553_Verify_application_imports_the_browsed_file_on_clicking_the_Import_button() {

		dataofFile = test.maps_sessionpage
				.getDataForImportedFile(YamlReader.getYamlValues("Session.Symposium_FileData"));
		test.maps_sessionpage.importValidFile(dataofFile, downloadedFilePath + File.separator
				+ YamlReader.getYamlValue("Session.Symposium.File_Download_template") + ".csv");
		test.maps_sessionpage.clickOnButtonByIndexing("Import", "1");
		test.maps_sessionpage.verifyPopUpText("Import successfully completed.");
		test.maps_sessionpage.clickOnButtonByIndexing("Ok", "1");
		// test.maps_sessionpage.clickOnButtonByIndexing("Cancel", "1");

	}

	public void Step_1578_MAPS_Session_1556_Verify_application_exports_the_list_of_sessions_events_with_all_the_columns_on_clicking_Export_to_Excel_All_Columns() {
		String fileName = YamlReader.getYamlValue("Session.Symposium.File_Export_To_Excel_All_Col");
		downloadedFile = downloadedFilePath + File.separator + fileName + ".csv";
		CSVFileReaderWriter._deleteExistingCSVFile(downloadedFilePath, fileName);

		test.maps_sessionpage.clickOnButtonUnderSessioning("Import / Export");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Export to Excel (All Columns)");
		test.maps_sessionpage.verifyValidFileIsDownloaded(downloadedFile);
		test.maps_sessionpage.clickOnArrowButton("Symposium Type");
		test.maps_sessionpage.hoverOverColumnHeader("Columns");
		colHeader = test.maps_sessionpage.getAllColumnName();
		test.maps_sessionpage.verifyExportedFileWithColumns(downloadedFilePath, colHeader);
	}

	public void Step_1579_MAPS_Session_1557_Verify_application_exports_the_list_of_sessions_events_with_selected_columns_on_clicking_the_Export_to_Excel_Displayed_Columns() {
		String fileName = YamlReader.getYamlValue("Session.Symposium.File_Export_To_Excel_All_Col");
		downloadedFile = downloadedFilePath + File.separator + fileName + ".csv";
		CSVFileReaderWriter._deleteExistingCSVFile(downloadedFilePath, fileName);

		test.maps_sessionpage.clickButtononLeftNavigationPanel("Symposia");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Import / Export");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Export to Excel (Displayed Columns)");
		test.maps_sessionpage.verifyValidFileIsDownloaded(downloadedFile);
		colHeader = test.maps_sessionpage.getDisplayedColumnName();
		test.maps_sessionpage.verifyExportedFileWithColumns(downloadedFilePath, colHeader);

	}

	@Test
	public void Step_1588_MAPS_Session_1566_Verify_application_adds_new_sort_criteria_on_clicking_Add_button() {
		test.maps_sessionpage.clickOnArrowButton("Title");
		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Configure Sort");
		test.maps_sessionpage.verifyPopupMessage("Sort");
		test.maps_sessionpage.clickOnAddButton("Add");
		test.maps_sessionpage.selectColumnForSorting("Session Kind");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Session Kind");
	}

	@Test // passed
	public void Step_1591_MAPS_Session_1569_Verify_application_allows_user_to_delete_the_criteria_added() {

		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sort");
		test.maps_sessionpage.selectRoleOnSaveGridConfiguration("Criteria", "Session Kind");
		test.maps_sessionpage.clickOnAddButton("Delete");
		test.maps_sessionpage.verifyAddedCriteriaIsDeleted("Session Kind");
		test.maps_sessionpage.clickOnSaveButton("Close");
	}

	@Test // passed
	public void Step_1599_MAPS_Session_1577_Verify_application_sort_the_records_on_the_basis_of_the_criteria_added() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Symposia");
		List<String> dataBeforeSorting = test.maps_sessionpage.getTableData("1", "12");
		test.maps_sessionpage.clickOnArrowButton("# of Assigned Abstracts");
		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Configure Sort");
		test.maps_sessionpage.verifyPopupMessage("Sort");
		test.maps_sessionpage.clickOnAddButton("Add");
		test.maps_sessionpage.selectColumnForSorting("# of Assigned Abstracts");
		test.maps_sessionpage.clickOnSaveButton("Apply");
		List<String> dataAfterSorting = test.maps_sessionpage.getTableData("1", "12");
		test.maps_sessionpage.verifyDataIsSorted(dataBeforeSorting, dataAfterSorting);
	}

	@Test // passed
	public void Step_1618_MAPS_Session_1595_Verify_that_Application_navigates_to_Sessions_Events_page_on_clicking_Session_Events_link() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessions & Events");
		test.maps_sessionpage.verifyPopupMessage("Sessions & Events");
	}

	@Test // passed
	public void Step_1640_MAPS_Session_1617_Verify_application_launches_CreateSession_popup_on_clicking_CreateNewSession() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessions & Events");
		test.maps_sessionpage.verifyPopupMessage("Sessions & Events");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Create New");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Create New Session");
		test.maps_sessionpage.verifyPopupMessage("Create Session");
	}

	@Test // passed
	public void Step_1761_MAPS_Session_1738_Verify_application_navigates_to_Add_Remove_Withdraw_Abstracts_tab() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessions & Events");
		test.maps_sessionpage.verifyPopupMessage("Sessions & Events");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Create New");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Create New Session");
		test.maps_sessionpage.verifyPopupMessage("Create Session");
		sessionTitle = YamlReader.getYamlValue("Session.Session_Events.Title") + System.currentTimeMillis();
		test.maps_sessionpage.enterValuesInCreateSession(sessionTitle,
				YamlReader.getYamlValue("Session.Session_Events.Duration"),
				YamlReader.getYamlValue("Session.Session_Events.Type"),
				YamlReader.getYamlValue("Session.Session_Events.Symposia_Submission_Type"));
		test.maps_sessionpage.clickOnButtonByIndexing("Save", "1");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Add/Edit Hosts");
		test.maps_sessionpage.addHostforSymposium("session_host_last_name");
		test.maps_sessionpage.addRoleForHost(YamlReader.getYamlValue("Session.Symposium.Host_Role"));
		test.maps_sessionpage.clickOnButtonByIndexing("Save", "1");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Add/Remove/Withdraw Abstracts");
		test.maps_sessionpage.verifyPopupMessage("Search");
	}

	@Test // issue
	public void Step_1775_MAPS_Session_1752_Verify_application_successfully_assigns_selected_abstracts_on_clicking_Yes_button_on_confirmation_popup() {
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Successfully Saved");
		test.maps_sessionpage.searchAbstract("author_first_name",
				YamlReader.getYamlValue("Session.Symposium.Abstract_AuthorFirstName"));
		test.maps_sessionpage.searchAbstract("author_last_name",
				YamlReader.getYamlValue("Session.Symposium.Abstract_AuthorLastName"));
		test.maps_sessionpage.clickOnButtonByIndexing("Search", "1");
		test.maps_sessionpage.selectAbtract("Sessioned");
	}

	@Test // passed
	public void Step_1896_MAPS_Session_1873_Verify_application_launches_Create_New_Session_from_Symposia_popup_on_clicking_CreateNewSessionfromSymposia_option() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessions & Events");
		test.maps_sessionpage.verifyPopupMessage("Sessions & Events");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Create New");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Create New Session from Symposium");
		test.maps_sessionpage.verifyPopupMessage("Create New Session from Symposium");
	}

	@Test // passed
	public void Step_2020_MAPS_Session_1997_Verify_application_navigates_to_SearchResult_tab_on_clicking_Search_button() {
		test.maps_sessionpage.enterDetailsInCreateNewSessionFromSymposium(
				YamlReader.getYamlValue("Session.Session_Events.Type"),
				YamlReader.getYamlValue("Session.Session_Events.Session_Program_Area"),
				YamlReader.getYamlValue("Session.Session_Events.Symposium"));
		test.maps_sessionpage.clickOnButtonUnderSessioning("Continue");
		test.maps_sessionpage.verifyPopupMessage("Create Session");
		test.maps_sessionpage.enterValuesInCreateSessionFromSymposium(
				YamlReader.getYamlValue("Session.Session_Events.Duration"),
				YamlReader.getYamlValue("Session.Session_Events.Symposia_Submission_Type"));
		test.maps_sessionpage.clickOnButtonByIndexing("Save", "1");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Add/Remove/Withdraw Abstracts");
		test.maps_sessionpage.verifyPopupMessage("Search");
		test.maps_sessionpage.searchAbstract("author_first_name",
				YamlReader.getYamlValue("Session.Symposium.Abstract_AuthorFirstName"));
		test.maps_sessionpage.searchAbstract("author_last_name",
				YamlReader.getYamlValue("Session.Symposium.Abstract_AuthorLastName"));
		test.maps_sessionpage.clickOnButtonByIndexing("Search", "1");
		test.maps_sessionpage.verifyPopupMessage("Search Results");
		test.maps_sessionpage.verifyAddedDetails("author_name",
				YamlReader.getYamlValue("Session.Symposium.Abstract_AuthorLastName") + ", "
						+ YamlReader.getYamlValue("Session.Symposium.Abstract_AuthorFirstName"));
	}

	@Test // passed
	public void Step_2022_MAPS_Session_1999_Verify_application_allows_user_to_filter_the_results_by_provding_the_keyword_in_Filter_text_field() {
		test.maps_sessionpage.clickOnButtonByIndexing("Save & Close", "1");
		test.maps_reviewpage.enterValueInFilter("Oral");
		test.maps_sessionpage.verifyFilterResults("session_type", 1, 8);
	}

	@Test
	public void Step_2354_MAPS_Session_2330_Verify_that_the_application_navigates_to_symposia_Viewer_page() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Symposia Viewer");
		test.maps_sessionpage.verifyPopupMessage("Symposia (View Only)");
	}

	@Test
	public void Step_2355_MAPS_Session_2331_Verify_that_the_application_filters_the_result_on_the_basis_of_the_criteria_provided() {
		String searchTerm = test.maps_sessionpage.getRandomTableData("1", "2");
		test.maps_reviewpage.enterValueInFilter(searchTerm);
		test.maps_sessionpage.verifyFilterResults(searchTerm, 1, 2);
	}

	@Test
	public void Step_2398_MAPS_Session_2374_Verify_that_the_application_displays_View_Symposia_popup_on_double_clicking_the_available_symposia() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Symposia Viewer");
		test.maps_sessionpage.doubleClickOnRow("1");
		test.maps_sessionpage.verifyPopupMessage("View Symposium");
	}

	@Test
	public void Step_2401_MAPS_Session_2377_Verify_that_the_application_filters_the_result_provided_in_the_Filter_textbox() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("View Hosts");
		String searchTerm = test.maps_sessionpage.getRandomTableData("2", "6");
		test.maps_sessionpage.inputTextInFilter(searchTerm, "3");
		test.maps_sessionpage.verifyFilterResults(searchTerm, 2, 6);
	}

	@Test
	public void Step_2437_MAPS_Session_2413_Verify_that_the_application_remains_on_the_same_page_on_clicking_the_Save_button() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Information");
		test.maps_sessionpage.clickOnButtonByIndexing("Save", "1");
		test.maps_sessionpage.verifyPopupMessage("View Symposium");
		test.maps_sessionpage.clickOnButtonByIndexing("Close", "1");
	}

	@Test
	public void Step_2441_MAPS_Session_2416_Verify_that_the_application_navigates_to_the_Session_Viewer_page() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Session Viewer");
		test.maps_sessionpage.verifyPopupMessage("Sessions & Events (View Only)");
	}

	@Test
	public void Step_2556_MAPS_Session_2530_2541_Verify_that_the_application_adds_a_new_sort_criteria_on_clicking_the_Add_button_and_apply_button() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Session Builder");
		List<String> dataBeforeSorting = test.maps_sessionpage.getTableData("2", "3");
		test.maps_sessionpage.clickOnArrowButton("Control ID");
		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Configure Sort");
		test.maps_sessionpage.verifyPopupMessage("Sort");
		test.maps_sessionpage.clickOnAddButton("Add");
		test.maps_sessionpage.selectColumnForSorting("Control ID");
		test.maps_sessionpage.clickOnSaveButton("Apply");
		List<String> dataAfterSorting = test.maps_sessionpage.getTableData("2", "3");
		test.maps_sessionpage.verifyDataIsSorted(dataBeforeSorting, dataAfterSorting);
	}

	@Test
	public void Step_2559_MAPS_Session_2533_Verify_that_the_application_allows_the_user_to_delete_the_criteria_added() {
		test.maps_sessionpage.clickOnArrowButton("Control ID");
		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Configure Sort");
		test.maps_sessionpage.verifyPopupMessage("Sort");
		test.maps_sessionpage.selectRoleOnSaveGridConfiguration("Control ID");
		test.maps_sessionpage.clickOnAddButton("Delete");
		test.maps_sessionpage.verifyIsDataDeleted("Control ID");
		test.maps_sessionpage.clickOnSaveButton("Close");
	}

	@Test
	public void Step_2574_MAPS_Session_2548_Verify_that_the_application_adds_a_new_sort_criteria_on_clicking_the_Add_button() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Session Builder");
		// test.maps_sessionpage.clickOnButtonUnderSessioning("Create Session");
		// test.maps_sessionpage.verifyPopupMessage("Create Session");
		// test.maps_sessionpage.verifyLabelElement();

	}

	@Test(dataProvider = "getData")
	public void Step_2578_MAPS_Session_2552_Verify_the_different_options_displayed_on_selecting_options_from_Session_Type_dropdown(
			String sessionType, String priviousValue) {
		test.maps_sessionpage.clickOnDropDownOfLabel("Session Type:", "img");
		test.maps_sessionpage.selectValueFromDropDown(priviousValue, sessionType);
		test.maps_sessionpage.verifySpanUnderlabelElement(sessionType);
	}

	public void Step_2583_MAPS_Session_2557_Application_should_save_the_session_on_clicking_Yes_button() {
		String sessionTitle = YamlReader.getYamlValue("Session.Session_Builder.Title") + System.currentTimeMillis();
		test.maps_sessionpage.enterTitleOfSession("Session Title:", "textarea", sessionTitle);
		test.maps_sessionpage.clickOnDropDownOfLabel("Session Type:", "img");
		test.maps_sessionpage.selectValueFromDropDown(YamlReader.getYamlValue("Session.Session_Builder.Session_Type2"),
				YamlReader.getYamlValue("Session.Session_Builder.Session_Type3"));
		test.maps_sessionpage.clickOnButtonByIndexing("Yes", "1");
		test.maps_sessionpage.enterTitleOfSession("Duration (in minutes):", "input",
				YamlReader.getYamlValue("Session.Session_Builder.Duration"));
		test.maps_sessionpage.clickOnButtonUnderSessioning("Add Owners");
		test.maps_sessionpage.verifyPopupMessage("The session must be saved before adding owners. Ok?");
		test.maps_sessionpage.clickOnButtonByIndexing("Yes", "1");
		test.maps_sessionpage.verifyPopupMessage("Session Owners");
	}

	@Test
	public void Step_2588_MAPS_Session_2562_Verify_that_the_application_add_the_records_under_the_Current_Owner_section_on_clicking_the_Add_Selected_button() {
		test.maps_sessionpage.enterTitleOfSession("Email Address:", "input",
				YamlReader.getYamlValue("Session.Session_Builder.Email"));
		test.maps_sessionpage.clickOnButtonByIndexing("Search", "1");
		test.maps_sessionpage.verifyFilterResults(YamlReader.getYamlValue("Session.Session_Builder.Email"), 4, 5);
		test.maps_sessionpage.checkRowInTable(4, 5);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Add Selected");
		test.maps_sessionpage.verifyFilterResults(YamlReader.getYamlValue("Session.Session_Builder.Email"), 3, 5);
	}

	@Test
	public void Step_2591_MAPS_Session_2565_Verify_that_the_application_successfully_deletes_the_record_on_clicking_the_Yes_button_on_the_confirmation_popup() {
		test.maps_sessionpage.checkRowInTable(3, 5);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Remove Selected");
		test.maps_sessionpage.verifyPopupMessage("Confirm");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Yes");
		test.maps_sessionpage.verifyRowIsDeleted(YamlReader.getYamlValue("Session.Session_Builder.Email"), 3, 5);
	}

	@Test
	public void Step_2611_MAPS_Session_2585_Verify_that_the_application_saves_the_changes_and_closes_the_popup_on_clicking_the_Save_and_Close_button() {
		test.maps_sessionpage.clickOnButtonUnderSessioning("Add Selected");
		test.maps_sessionpage.clickOnButtonByIndexing("Save and Close", "1");
		test.maps_sessionpage.verifyLabelName("Session Owner(s):", "div");
		test.maps_sessionpage.clickOnButtonByIndexing("Close", "1");

	}

	@Test
	public void Step_2617_MAPS_Session_2591_Verify_that_the_application_displays_appropriate_search_results_on_clicking_Search_button_after_providing_the_valid_search_criteria() {
		String sessionTitle = YamlReader.getYamlValue("Session.Session_Builder.Title") + System.currentTimeMillis();
		test.maps_sessionpage.clickOnButtonUnderSessioning("Create Session");
		test.maps_sessionpage.verifyPopupMessage("Create Session");
		test.maps_sessionpage.enterTitleOfSession("Session Title:", "textarea", sessionTitle);
		test.maps_sessionpage.clickOnDropDownOfLabel("Session Type:", "img");
		test.maps_sessionpage.selectValueFromDropDown("Poster", "Sci-Mix");
		test.maps_sessionpage.enterTitleOfSession("Duration (in minutes):", "input",
				YamlReader.getYamlValue("Session.Session_Builder.Duration"));
		test.maps_sessionpage.clickOnButtonByIndexing("Save", "1");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Add/Edit Hosts");
		test.maps_sessionpage.clickOnButtonByIndexing("Search for New Hosts", "2");
		test.maps_sessionpage.verifyPopupMessage("Search Hosts");
		test.maps_sessionpage.enterTitleOfSession("Email Address:", "input",
				YamlReader.getYamlValue("Session.Session_Builder.Email"));
		test.maps_sessionpage.clickOnButtonByIndexing("Search", "1");
		test.maps_sessionpage.verifyFilterResults(YamlReader.getYamlValue("Session.Session_Builder.Email"), 5, 5);
	}

	@Test
	public void Step_2620_MAPS_Session_2594_Verify_that_the_application_adds_the_selected_hosts_in_the_Available_Hosts_section_on_clicking_the_Add_to_the_list_of_available_hosts_Only_button() {
		test.maps_sessionpage.checkRowInTable(5, 5);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Add to List of Available Hosts Only");
		test.maps_sessionpage.clickOnButtonByIndexing("Close", "2");
		test.maps_reviewpage.enterValueInFilter(YamlReader.getYamlValue("Session.Session_Builder.Email"));
		test.maps_sessionpage.verifyFilterResults(YamlReader.getYamlValue("Session.Session_Builder.Email"), 3, 14);
	}

	@Test
	public void Step_2621_MAPS_Session_2595_Verify_that_the_application_adds_the_selected_host_under_the_Current_Host_section_on_clicking_the_Add_hosts_to_current_session_button() {
		test.maps_sessionpage.clickOnButtonByIndexing("Search for New Hosts", "2");
		test.maps_sessionpage.verifyPopupMessage("Search Hosts");
		test.maps_sessionpage.enterTitleOfSession("Email Address:", "input",
				YamlReader.getYamlValue("Session.Session_Builder.Email"));
		test.maps_sessionpage.clickOnButtonUnderSessioning("Search");
		test.maps_sessionpage.checkRowInTable(5, 5);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Add Hosts to Current Session");
		test.maps_sessionpage.clickOnButtonByIndexing("Close", "2");
		test.maps_sessionpage.verifyFilterResults(YamlReader.getYamlValue("Session.Session_Builder.Email"), 4, 15);
	}

	@Test
	public void Step_2622_MAPS_Session_2596_Verify_that_the_application_allows_the_user_to_add_a_new_host_by_clicking_the_Add_New_Host_button() {
		String email = YamlReader.getYamlValue("Session.Host.Email") + System.currentTimeMillis() + "@acs.org";
		test.maps_sessionpage.clickOnButtonByIndexing("Search for New Hosts", "2");
		test.maps_sessionpage.verifyPopupMessage("Search Hosts");
		test.maps_sessionpage.enterTitleOfSession("Email Address:", "input", email);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Search");
		test.maps_sessionpage.clickOnButtonByIndexing("OK", "1");
		test.maps_sessionpage.waitForLoadingImageToDisappear("Loading...");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Add New Host");
		test.maps_sessionpage.enterValuesInAddNewHost(
				YamlReader.getYamlValue("Session.Host.First_Name") + System.currentTimeMillis(),
				YamlReader.getYamlValue("Session.Host.Last_Name") + System.currentTimeMillis(), email,
				YamlReader.getYamlValue("Session.Host.institution"));
		test.maps_sessionpage.clickOnButtonByIndexing("Save", "2");
		test.maps_sessionpage.verifyFilterResults(email, 5, 5);
		test.maps_sessionpage.clickOnButtonByIndexing("Close", "2");
	}

	@Test
	public void Step_2671_MAPS_Session_2645_Verify_that_the_application_adds_the_hosts_in_the_Current_Hosts_section_if_user_drags_and_drops_the_records_from_the_Available_Hosts_section() {
		test.maps_sessionpage.addHostforSymposium("session_host_last_name");
		test.maps_sessionpage.verifyFilterResults("session_host_last_name", 4, 14);
	}

	@Test
	public void Step_2681_MAPS_Session_2655_Verify_that_the_application_only_removes_the_record_on_clicking_Yes_button_on_the_confirmation_popup() {
		test.maps_sessionpage.checkRowInTable(4, 14);
		test.maps_sessionpage.clickOnButtonByIndexing("Remove Selected", "1");
		test.maps_sessionpage
				.verifyPopupMessage("You are about to remove the selected Hosts from the session. Are you sure?");
		test.maps_sessionpage.clickOnButtonByIndexing("Yes", "1");
		test.maps_sessionpage.verifyRowIsDeleted("", 4, 14);
	}

	@Test
	public void Step_2688_MAPS_Session_2662_Verify_that_the_application_navigates_the_user_to_the_Search_Result_tab_on_clicking_Search_button_after_providing_the_valid_search_criteria() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Add/Remove/Withdraw Abstracts");
		test.maps_sessionpage.enterDurationOnCreateSessionPopUp("Author First Name:",
				YamlReader.getYamlValue("Session.Session_Builder.Author_First_Name"));
		test.maps_sessionpage.enterDurationOnCreateSessionPopUp("Author Last Name:",
				YamlReader.getYamlValue("Session.Session_Builder.Author_Last_Name"));
		test.maps_sessionpage.clickOnButtonUnderSessioning("Search");
		String authorName = YamlReader.getYamlValue("Session.Session_Builder.Author_Last_Name") + ", "
				+ YamlReader.getYamlValue("Session.Session_Builder.Author_First_Name");
		test.maps_sessionpage.verifyFilterResults(authorName, 5, 5);
	}

	@Test
	public void Step_2696_MAPS_Session_2670_Verify_that_the_application_successfully_assigns_the_selected_abstracts_on_clicking_the_Assign_Selected_Abstracts() {
		test.maps_sessionpage.checkRowInTable(5, 5);
		test.maps_sessionpage.clickOnButtonByIndexing("Assign Selected Abstracts", "1");
		String authorName = YamlReader.getYamlValue("Session.Session_Builder.Author_Last_Name") + ", "
				+ YamlReader.getYamlValue("Session.Session_Builder.Author_First_Name");
		test.maps_sessionpage.verifyFilterResults(authorName, 6, 8);
	}

	@Test
	public void Step_2710_MAPS_Session_2684_Verify_that_the_application_displays_the_newly_added_Placeholder_in_the_Currently_Assigned_Abstract_section() {
		String sessionBuilderTitle1 = YamlReader.getYamlValue("Session.Session_Builder.Title")
				+ System.currentTimeMillis();
		test.maps_sessionpage.checkRowInTable(6, 1);
		test.maps_sessionpage.clickOnButtonByIndexing("Remove Selected", "2");
		test.maps_sessionpage.clickOnButtonByIndexing("Add Placeholder", "1");
		test.maps_sessionpage.verifyPopupMessage("Add / Edit Placeholder");
		test.maps_sessionpage.enterNameOnSaveGridConfiguration("Name:", sessionBuilderTitle1);
		test.maps_sessionpage.enterTitleOfSession("Description:",
				YamlReader.getYamlValue("Session.Session_Builder.Title_Description"), "1");
		test.maps_sessionpage.clickOnButtonByIndexing("Save and Close", "1");
		test.maps_sessionpage.verifyFilterResults(sessionBuilderTitle1, 6, 7);
	}

	@Test
	public void Step_2763_MAPS_Session_2737_Verify_that_application_withdraws_the_abstract_from_all_the_sessions_and_displays_W_in_front_of_the_Abstract_withdrawn_on_clicking_Withdraw_Abstract_button() {
		test.maps_sessionpage.checkRowInTable(6, 1);
		test.maps_sessionpage.clickOnButtonByIndexing("Remove Selected", "2");
		test.maps_sessionpage.checkRowInTable(5, 5);
		test.maps_sessionpage.clickOnButtonByIndexing("Assign Selected Abstracts", "1");
		test.maps_sessionpage.waitForLoadingImageToDisappear("Loading...");
		test.maps_sessionpage.checkRowInTable(6, 1);
		String controlID = test.maps_sessionpage.getTextformTable(6, 2);
		test.maps_sessionpage.clickOnButtonByIndexing("Withdraw Selected", "1");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Withdraw Selected");
		test.maps_sessionpage.verifyWithDrawRow("(W)", controlID);
	}

	@Test
	public void Step_2764_MAPS_Session_2738_Verify_that_application_withdraws_the_abstract_from_the_current_session_and_displays_SW_in_front_of_the_abstract_wihtdrawn_on_cliking_Withdraw_Selected_This_Session_only_button() {
		test.maps_sessionpage.checkRowInTable(6, 1);
		test.maps_sessionpage.clickOnButtonByIndexing("Withdraw Selected", "1");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Withdraw Selected");
		test.maps_sessionpage.waitForLoadingImageToDisappear("Loading...");
		test.maps_sessionpage.checkRowInTable(6, 1);
		String controlID = test.maps_sessionpage.getTextformTable(6, 2);
		test.maps_sessionpage.clickOnButtonByIndexing("Withdraw Selected", "1");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Withdraw (This Session Only)");
		test.maps_sessionpage.verifyWithDrawRow("(SW)", controlID);
	}

	@Test
	public void Step_2767_MAPS_Session_2741_Verify_that_application_removes_the_selected_abstracts_when_user_clicks_on_Remove_Selected_button() {
		test.maps_sessionpage.checkRowInTable(6, 1);
		String controlID = test.maps_sessionpage.getTextformTable(6, 2);
		test.maps_sessionpage.clickOnButtonByIndexing("Remove Selected", "2");
		test.maps_sessionpage.verifyRowIsDeleted(controlID, 3, 5);
	}

	@Test
	public void Step_2772_MAPS_Session_2746_Verify_that_the_application_displays_the_updated_Duration_for_all_the_available_abstracts_under_the_Currently_Assigned_Abstracts_section() {
		test.maps_sessionpage.checkRowInTable(5, 5);
		test.maps_sessionpage.clickOnButtonByIndexing("Assign Selected Abstracts", "1");
		test.maps_sessionpage.waitForLoadingImageToDisappear("Loading...");
		test.maps_sessionpage.checkRowInTable(6, 1);
		test.maps_sessionpage.clickOnButtonByIndexing("Mass Update", "1");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Assign Durations");
		test.maps_sessionpage.verifyPopupMessage("Assign Abstracts Duration");
		test.maps_sessionpage.enterTextInPopUpInput("Assign each Abstract a duration of", "1",
				YamlReader.getYamlValue("Session.Session_Builder.Duration"));
		test.maps_sessionpage.clickOnButtonByIndexing("Assign", "1");
		test.maps_sessionpage.waitForLoadingImageToDisappear("Loading...");
		test.maps_sessionpage.verifyFilterResults(YamlReader.getYamlValue("Session.Session_Builder.Duration"), 6, 15);
	}

	@Test
	public void Step_2774_MAPS_Session_2748_2749_Verify_that_user_can_assign_less_than_available_time() {
		test.maps_sessionpage.checkRowInTable(6, 1);
		test.maps_sessionpage.clickOnButtonByIndexing("Mass Update", "1");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Assign Durations");
		test.maps_sessionpage.verifyPopupMessage("Assign Abstracts Duration");
		String duration = test.maps_sessionpage
				.subtractValue(YamlReader.getYamlValue("Session.Session_Builder.Duration"), 2);
		test.maps_sessionpage.enterTextInPopUpInput("Assign each Abstract a duration of", "1", duration);
		test.maps_sessionpage.clickOnButtonByIndexing("Assign", "1");
		test.maps_sessionpage.waitForLoadingImageToDisappear("Loading...");
		test.maps_sessionpage.verifyFilterResults(duration, 6, 15);
	}

	@Test
	public void Step_2782_MAPS_Session_2756_Verify_that_the_applicaiton_assigns_the_Final_IDs_on_clicking_the_Continue_button_on_the_Select_types_of_Items_to_Assign_popup() {
		String privousFinalId = test.maps_sessionpage.getTextformTable(6, 4);
		test.maps_sessionpage.checkRowInTable(6, 1);
		test.maps_sessionpage.clickOnButtonByIndexing("Mass Update", "1");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Assign Final ID(s)");
		test.maps_sessionpage.verifyPopupMessage("Assign Abstract Final ID(s)");
		test.maps_sessionpage.enterTextInPopUpInput("New", "1",
				YamlReader.getYamlValue("Session.Session_Builder.Final_ID"));
		finalID = YamlReader.getYamlValue("Session.Session_Builder.Final_ID") + System.currentTimeMillis();
		test.maps_sessionpage.enterTextInPopUpInput("New", "2", finalID);
		test.maps_sessionpage.clickButtonToContinueToNextPage("save");
		test.maps_sessionpage.clickOnLinkText(finalID, "run");
		test.maps_sessionpage.clickCheckboxOnSaveGridConfiguration("Placeholders");
		test.maps_sessionpage.clickCheckboxOnSaveGridConfiguration("Withdrawn Presentations");
		test.maps_sessionpage.clickCheckboxOnSaveGridConfiguration("Stubs");
		test.maps_sessionpage.selectValueForSessionType("Exclude Updating Final ID(s) that already exist", "No");
		test.maps_sessionpage.clickOnButtonByIndexing("Continue", "1");
		test.maps_sessionpage.isFinalIDAllocatedToAbstract(privousFinalId);
		test.maps_sessionpage.waitForLoadingImageToDisappear("Loading...");
		test.maps_sessionpage.verifyFilterResults(finalID, 6, 4);
	}

	@Test
	public void Step_2783_MAPS_Session_2757_Verify_that_the_application_allows_the_user_to_edit_the_saved_format() {
		test.maps_sessionpage.checkRowInTable(6, 1);
		test.maps_sessionpage.clickOnButtonByIndexing("Mass Update", "1");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Assign Final ID(s)");
		test.maps_sessionpage.verifyPopupMessage("Assign Abstract Final ID(s)");
		test.maps_sessionpage.clickOnLinkText(finalID, "edit");
		test.maps_sessionpage.enterTextInPopUpInput("New", "1",
				YamlReader.getYamlValue("Session.Session_Builder.Final_ID"));
		finalID = YamlReader.getYamlValue("Session.Session_Builder.Final_ID") + System.currentTimeMillis();
		test.maps_sessionpage.enterTextInPopUpInput("New", "2", finalID);
		test.maps_sessionpage.clickButtonToContinueToNextPage("save");
		test.maps_sessionpage.verifyLableFormateAfterEdit(finalID);
	}

	@Test
	public void Step_2784_MAPS_Session_2758_Verify_that_the_application_allows_the_user_to_delete_the_saved_format() {
		test.maps_sessionpage.clickOnLinkText(finalID, "delete");
		test.maps_sessionpage.verifyFormateIsDeleted(finalID);
		test.maps_sessionpage.clickOnButtonByIndexing("Close", "2");
	}

	@Test
	public void Step_2798_MAPS_Session_2772_Verify_that_the_application_allows_the_user_to_edit_the_fields_with_a_Pencil_icon_by_double_clicking_the_fields() {
		test.maps_sessionpage.doubleClickToEditTableData(6, 8);
		test.maps_sessionpage.verifyInputBoxInTableData(18);
	}

	@Test
	public void Step_2800_MAPS_Session_2774_Verify_that_the_application_saves_the_session_on_clicking_the_Save_button() {
		test.maps_sessionpage.clickOnButtonByIndexing("Remove Selected", "2");
		test.maps_sessionpage.waitForLoadingImageToDisappear("Loading...");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Save");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Add/Edit Hosts");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Save");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Close");
		test.maps_sessionpage.clickOnArrowButton("Title");
		test.maps_sessionpage.enterFilterText("Filters", sessionBuilderTitle);
		test.maps_sessionpage.verifyFilterResults(sessionBuilderTitle, 1, 5);
	}

	@Test
	public void Step_2805_MAPS_Session_2779_Verify_that_the_application_only_deletes_the_record_on_clicking_Yes_button_on_the_confirmation_popup() {
		test.maps_sessionpage.checkRowInTable(1, 1);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Delete Session");
		test.maps_sessionpage.verifyPopupMessage("You are about to delete the selected sessions. Are you sure?");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Yes");
		test.maps_sessionpage.verifyRowIsDeleted(sessionBuilderTitle, 3, 5);
	}

	// @Test
	public void Step_2823_MAPS_Session_2796_Verify_the_Grids_available_on_the_page_if_Assign_Abstracts_option_is_selected() {

	}

	@Test
	public void Step_2824_MAPS_Session_2797_Verify_that_the_application_displays_the_Currently_Assign_Abstracts_section_if_user_selects_any_of_the_session() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Session Builder");
		test.maps_sessionpage.waitForLoadingImageToDisappear("Loading...");
		test.maps_sessionpage.checkRowInTable(1, 1);
		test.maps_sessionpage.verifyPopupMessage("Currently Assigned Abstracts");
	}

	@Test
	public void Step_2971_MAPS_Session_2948_Verify_that_the_applicaiton_assigns_the_Final_IDs_on_clicking_the_Continue_button_on_the_Select_types_of_Items_to_Assign_popup() {
		test.maps_sessionpage.clickOnArrowButton("Title");
		test.maps_sessionpage.enterFilterText("Filters",
				YamlReader.getYamlValue("Session.Session_Builder.Title_Description"));
		test.maps_sessionpage.waitForLoadingImageToDisappear("Loading...");
		test.maps_sessionpage.checkRowInTable(1, 1);
		test.maps_sessionpage.rowIsNotPresentThenAddRowInCurrentlyAssignedAbstracts(3, 1);
		String privousFinalId = test.maps_sessionpage.getTextformTable(3, 4);
		test.maps_sessionpage.checkRowInTable(3, 1);
		test.maps_sessionpage.clickOnButtonByIndexing("Mass Update", "1");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Assign Final ID(s)");
		test.maps_sessionpage.verifyPopupMessage("Assign Abstract Final ID(s)");
		test.maps_sessionpage.enterTextInPopUpInput("New", "1",
				YamlReader.getYamlValue("Session.Session_Builder.Final_ID"));
		finalID = YamlReader.getYamlValue("Session.Session_Builder.Final_ID") + System.currentTimeMillis();
		test.maps_sessionpage.enterTextInPopUpInput("New", "2", finalID);
		test.maps_sessionpage.clickButtonToContinueToNextPage("save");
		test.maps_sessionpage.clickOnLinkText(finalID, "run");
		test.maps_sessionpage.clickCheckboxOnSaveGridConfiguration("Placeholders");
		test.maps_sessionpage.clickCheckboxOnSaveGridConfiguration("Withdrawn Presentations");
		test.maps_sessionpage.clickCheckboxOnSaveGridConfiguration("Stubs");
		test.maps_sessionpage.selectValueForSessionType("Exclude Updating Final ID(s) that already exist", "No");
		test.maps_sessionpage.clickOnButtonByIndexing("Continue", "1");
		test.maps_sessionpage.isFinalIDAllocatedToAbstract(privousFinalId);
		test.maps_sessionpage.waitForLoadingImageToDisappear("Loading...");
		test.maps_sessionpage.verifyFilterResults(finalID, 3, 4);
	}

	@Test
	public void Step_2980_MAPS_Session_2957_Verify_that_the_applicaiton_assigns_the_DOIs_on_clicking_the_Continue_button_on_the_Select_types_of_Items_to_Assign_popup() {
		test.maps_sessionpage.checkRowInTable(3, 1);
		test.maps_sessionpage.clickOnButtonByIndexing("Remove Selected", "1");
		test.maps_sessionpage.waitForLoadingImageToDisappear("Loading...");
		test.maps_sessionpage.rowIsNotPresentThenAddRowInCurrentlyAssignedAbstracts(3, 1);
		test.maps_sessionpage.checkRowInTable(3, 1);
		test.maps_sessionpage.clickOnButtonByIndexing("Mass Update", "1");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Assign DOI(s)");
		test.maps_sessionpage.verifyPopupMessage("Assign Abstract DOI(s)");
		test.maps_sessionpage.enterTextInPopUpInput("New", "1",
				YamlReader.getYamlValue("Session.Session_Builder.Final_ID"));
		// finalID =
		// YamlReader.getYamlValue("Session.Session_Builder.Final_ID")+System.currentTimeMillis();
		test.maps_sessionpage.enterTextInPopUpInput("New", "2", finalID);
		test.maps_sessionpage.clickButtonToContinueToNextPage("save");
		test.maps_sessionpage.clickOnLinkText(finalID, "run");
		;
		test.maps_sessionpage.clickCheckboxOnSaveGridConfiguration("Placeholders");
		test.maps_sessionpage.clickCheckboxOnSaveGridConfiguration("Withdrawn Presentations");
		test.maps_sessionpage.clickCheckboxOnSaveGridConfiguration("Stubs");
		test.maps_sessionpage.selectValueForSessionType("Exclude Updating Final ID(s) that already exist", "No");
		test.maps_sessionpage.clickOnButtonByIndexing("Continue", "1");
		test.maps_sessionpage.waitForLoadingImageToDisappear("Loading...");
		// test.maps_sessionpage.verifyFilterResults(finalID,3,4);

	}

	@Test
	public void Step_2980_MAPS_Session_2985_Verify_that_application_successfully_adds_the_new_host_on_clicking_the_Save_button() {
		test.maps_sessionpage.clickButtonToContinueToNextPage("Assign Hosts");
		test.maps_sessionpage.clickOnButtonByIndexing("Search for New Hosts", "1");
		test.maps_sessionpage.verifyPopupMessage("Search Hosts");
		email = YamlReader.getYamlValue("Session.Host.Email") + System.currentTimeMillis() + "@acs.org";
		test.maps_sessionpage.enterDurationOnCreateSessionPopUp("Email Address:", email);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Search");
		test.maps_sessionpage.clickOnButtonUnderSessioning("OK");
		test.maps_sessionpage.waitForLoadingImageToDisappear("Loading...");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Add New Host");
		test.maps_sessionpage.enterValuesInAddNewHost(
				YamlReader.getYamlValue("Session.Host.First_Name") + System.currentTimeMillis(),
				YamlReader.getYamlValue("Session.Host.Last_Name") + System.currentTimeMillis(), email,
				YamlReader.getYamlValue("Session.Host.institution"));
		test.maps_sessionpage.clickOnButtonByIndexing("Save", "1");
		test.maps_sessionpage.verifyFilterResults(email, 4, 5);
		test.maps_sessionpage.clickOnButtonByIndexing("Close", "1");
	}

	@Test
	public void Step_2980_MAPS_Session_3058_Verify_that_the_application_allows_the_user_to_add_the_Hosts_by_dragging_the_host_from_Available_Hosts_Section_to_Current_Hosts_section() {
		test.maps_sessionpage.clickOnArrowButton("Email Address");
		test.maps_sessionpage.enterFilterText("Filters", email);
		test.maps_sessionpage.addHostforSymposium("");
		test.maps_sessionpage.verifyFilterResults(email, 3, 15);
	}

	@Test
	public void Step_2980_MAPS_Session_3059_Verify_that_application_removes_the_selected_Host_from_the_list_on_clicking_Remove_Selected_button() {
		test.maps_sessionpage.checkRowInTable(3, 1);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Remove Selected");
		test.maps_sessionpage.verifyPopupMessage("Confirm");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Yes");
		test.maps_sessionpage.verifyRowIsDeleted(email, 3, 15);
	}

	@Test // passed
	public void Step_3102_MAPS_Session_3077_Verify_application_prompts_to_select_a_Program_as_user_clicks_on_Schedule_Session() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Schedule Sessions");
		test.maps_sessionpage.verifyPopupMessage("Select Program");
	}

	@Test // passed
	public void Step_3104_MAPS_Session_3079_Verify_application_navigates_to_the_Schedule_Session_page_on_clicking_Ok_button() {
		test.maps_sessionpage.selectValueForCreateNewSessionFromSymposium("Programs",
				YamlReader.getYamlValue("Session.Schedule_Sessions.Programs"));
		test.maps_sessionpage.clickOnButtonByIndexing("OK", "1");
		test.maps_sessionpage.verifyPopupMessage("Schedule Sessions");
		test.maps_sessionpage.verifySchedulerGrid();
	}

	@Test // passed
	public void Step_3113_MAPS_Session_3087_Verify_application_displays_Grid_for_the_particular_day_as_selected_from_MeetingDay_dropdown() {
		test.maps_sessionpage.selectValuesForMeetingday();
		test.maps_sessionpage.verifySchedulerGrid();
	}

	@Test // passed
	public void Step_3139_MAPS_Session_3113_Verify_application_changes_Session_Type_only_after_clicking_Yes_button_on_confirmation_popup() {
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
				YamlReader.getYamlValue("Session.Schedule_Sessions.Session_Title") + System.currentTimeMillis());
		test.maps_sessionpage.selectValueForSymposium("session_type",
				YamlReader.getYamlValue("Session.Schedule_Sessions.Session_Type"));
		test.maps_sessionpage.verifyPopupMessage("Confirm");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Yes");
		test.maps_sessionpage
				.verifyApplicationChangesSessionType(YamlReader.getYamlValue("Session.Schedule_Sessions.Session_Type"));
	}

	@Test // passed
	public void Step_3143_MAPS_Session_3117_Verify_application_displays_appropriate_search_result_on_clicking_Search_button_after_providing_valid_search_results() {
		test.maps_sessionpage.clickOnButtonUnderSessioning("Add Owners");
		test.maps_sessionpage.verifyPopupMessage("Session Owners");
		test.maps_sessionpage.searchAbstract("owner_first_name",
				YamlReader.getYamlValue("Session.Schedule_Sessions.owner_first_name"));
		test.maps_sessionpage.searchAbstract("owner_last_name",
				YamlReader.getYamlValue("Session.Schedule_Sessions.owner_last_name"));
		test.maps_sessionpage.clickOnButtonUnderSessioning("Search");
		test.maps_sessionpage.verifyAddedDetails("owner_first_name",
				YamlReader.getYamlValue("Session.Schedule_Sessions.owner_first_name"));
	}

	@Test // passed
	public void Step_3168_MAPS_Session_3142_Verify_application_saves_changes_and_closes_popup_on_clicking_SaveAndClose_button() {
		test.maps_sessionpage.selectaRecordFromTheList(1, "2");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Add Selected");
		test.maps_sessionpage.verifyAddedDetails("owner_first_name",
				YamlReader.getYamlValue("Session.Schedule_Sessions.owner_first_name"));
		test.maps_sessionpage.clickOnButtonUnderSessioning("Save and Close");

	}

	@Test // passed
	public void Step_3173_MAPS_Session_3147_Verify_application_displays_appropriate_search_results_on_clicking_Search_button_after_providing_valid_search_criteria() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Schedule Sessions");
		test.maps_sessionpage.verifyPopupMessage("Select Program");
		test.maps_sessionpage.selectValueForCreateNewSessionFromSymposium("Programs",
				YamlReader.getYamlValue("Session.Schedule_Sessions.Programs"));
		test.maps_sessionpage.clickOnButtonByIndexing("OK", "1");
		test.maps_sessionpage.rightClickOnSession();
		test.maps_sessionpage.clickButtonToContinueToNextPage("Edit Item");
		test.maps_sessionpage.verifyPopupMessage("Edit Session");
		test.maps_sessionpage.enterValuesForProgram("session_name",
				YamlReader.getYamlValue("Session.Schedule_Sessions.Session_Title") + System.currentTimeMillis());
		test.maps_sessionpage.clickOnButtonByIndexing("Save", "1");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Add/Edit Hosts");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Search for New Hosts");
		test.maps_sessionpage.verifyPopupMessage("Search Hosts");
		test.maps_sessionpage.searchAbstract("session_host_first_name",
				YamlReader.getYamlValue("Session.Schedule_Sessions.owner_first_name"));
		test.maps_sessionpage.searchAbstract("session_host_last_name",
				YamlReader.getYamlValue("Session.Schedule_Sessions.owner_last_name"));
		test.maps_sessionpage.clickOnButtonByIndexing("Search", "1");
		test.maps_sessionpage.verifyAddedDetails("session_host_first_name",
				YamlReader.getYamlValue("Session.Schedule_Sessions.owner_first_name"));
		test.maps_sessionpage.verifyAddedDetails("session_host_last_name",
				YamlReader.getYamlValue("Session.Schedule_Sessions.owner_last_name"));

	}

	@Test // passed
	public void Step_3177_MAPS_Session_3151_Verify_application_adds_selected_host_under_CurrentHost_section_on_clicking_the_Add_hosts_to_current_session_button() {
		test.maps_sessionpage.selectaRecordFromTheList(1, "2");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Add Hosts to Current Session");
		test.maps_sessionpage.clickOnButtonByIndexing("Close", "2");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Add/Edit Hosts");
		test.maps_sessionpage.verifyAddedDetails("session_host_first_name",
				YamlReader.getYamlValue("Session.Schedule_Sessions.owner_first_name"));
		test.maps_sessionpage.verifyAddedDetails("session_host_last_name",
				YamlReader.getYamlValue("Session.Schedule_Sessions.owner_last_name"));
	}

	@Test // passed
	public void Step_3178_MAPS_Session_3152_Verify_application_allows_user_to_add_a_new_host_by_clicking_Add_New_Host_button() {
		test.maps_sessionpage.clickOnButtonUnderSessioning("Search for New Hosts");
		test.maps_sessionpage.verifyPopupMessage("Search Hosts");
		test.maps_sessionpage.searchAbstract("session_host_first_name",
				YamlReader.getYamlValue("Session.Schedule_Sessions.owner_first_name"));
		test.maps_sessionpage.clickOnButtonByIndexing("Search", "1");
		test.maps_sessionpage.verifyAddedDetails("session_host_first_name",
				YamlReader.getYamlValue("Session.Schedule_Sessions.owner_first_name"));
		test.maps_sessionpage.selectaRecordFromTheList(1, "2");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Add New Host");
		String email = YamlReader.getYamlValue("Session.Host.Email") + System.currentTimeMillis() + "@acs.org";
		test.maps_sessionpage.enterValuesInAddNewHost(
				YamlReader.getYamlValue("Session.Host.First_Name") + System.currentTimeMillis(),
				YamlReader.getYamlValue("Session.Host.Last_Name") + System.currentTimeMillis(), email,
				YamlReader.getYamlValue("Session.Host.institution"));
		test.maps_sessionpage.clickOnButtonByIndexing("Save", "2");
		test.maps_sessionpage.verifyAddedDetails("session_host_email", email);
		test.maps_sessionpage.verifyAddedDetails("session_host_institution",
				YamlReader.getYamlValue("Session.Host.institution"));
		test.maps_sessionpage.clickOnButtonByIndexing("Close", "2");
	}

	@Test // passed
	public void Step_3227_MAPS_Session_3201_Verify_application_adds_hosts_in_Current_Hosts_section_if_user_drags_and_drops_records_from_Available_Hosts_section() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Add/Edit Hosts");
		String abstractDetail = test.maps_sessionpage.addHostforSymposium("session_host_email");
		test.maps_sessionpage.verifyAddedAbstractsInTable("session_host_first_name", abstractDetail);

	}

	@Test // data is not sorted
	public void Step_3228_MAPS_Session_3202_Verify_application_sorts_current_hosts_section_on_clicking_column_headers() {
		List<String> dataBeforeSorting = test.maps_sessionpage.getTableData("1", "17");
		test.maps_sessionpage.clickOnColumnHeaders("# of Assigned to Sessions");
		List<String> dataAfterSorting = test.maps_sessionpage.getTableData("1", "17");
		test.maps_sessionpage.verifyDataIsSorted(dataBeforeSorting, dataAfterSorting);
	}

	@Test // passed
	public void Step_3237_MAPS_Session_3211_Verify_application_only_removes_record_on_clicking_Yes_button_on_the_confirmation_popup() {
		// test.maps_reviewpage.enterValueInFilter(YamlReader.getYamlValue("Session.Schedule_Sessions.owner_email"));
		String value = test.maps_sessionpage.addHostforSymposium("session_host_first_name");
		test.maps_sessionpage.verifyAddedDetails("session_host_first_name ", value);
		test.maps_sessionpage.selectAbtract(value);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Remove Selected");
		test.maps_sessionpage.verifyPopupMessage("Confirm");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Yes");
		test.maps_sessionpage.verifyIsHostDeleted(value);

	}

	@Test
	public void Step_3252_MAPS_Session_3226_Verify_application_successfully_assigns_selected_abstracts_on_clicking_AssignSelectedAbstracts() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Add/Remove/Withdraw Abstract");
		test.maps_sessionpage.searchAbstract("author_first_name",
				YamlReader.getYamlValue("Session.Schedule_Sessions.owner_first_name"));
		test.maps_sessionpage.searchAbstract("author_last_name",
				YamlReader.getYamlValue("Session.Schedule_Sessions.owner_last_name"));
		test.maps_sessionpage.clickOnButtonByIndexing("Search", "1");
		test.maps_sessionpage.verifyAddedDetails("author_name",
				YamlReader.getYamlValue("Session.Schedule_Sessions.owner_last_name") + ", "
						+ YamlReader.getYamlValue("Session.Schedule_Sessions.owner_first_name"));
		// test.maps_sessionpage.inputTextInFilter(value, index);
		controlId = test.maps_sessionpage.SelectRecords(5);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Assign Selected Abstracts");
		test.maps_sessionpage.verifyPopupMessage("Confirm");
		// test.maps_sessionpage.clickOnButtonUnderSessioning("Yes");
		// test.maps_sessionpage.verifyAddedAbstracts("abstract_id", controlId);

	}

	@Test
	public void Step_3267_MAPS_Session_3241_Verify_application_displays_newly_added_Placeholder_in_Currently_Assigned_Abstract_section() {
		test.maps_sessionpage.clickOnButtonUnderSessioning("Add Placeholder");
		test.maps_sessionpage.verifyPopupMessage("Add / Edit Placeholder");
		String placeholderValue = YamlReader.getYamlValue("Session.Schedule_Sessions.placeHolder_Title")
				+ System.currentTimeMillis();
		test.maps_sessionpage.enterValuesForProgram("title", placeholderValue);
		test.maps_sessionpage.enterValuesForProgram("session_event_desc", placeholderValue);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Save and Close");
		test.maps_sessionpage.verifyAddedPlaceHolder("title abstract_placeholder", placeholderValue);
	}

	@Test
	public void Step_3320_MAPS_Session_3294_Verify_application_withdraws_abstract_from_all_sessions_and_displays_W_in_front_of_Abstract_withdrawn() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Add/Remove/Withdraw Abstract");
		test.maps_reviewpage.enterValueInFilter("Submitted");
		String value = test.maps_sessionpage.addHostforSymposium("abstract_id");
		test.maps_sessionpage.verifyAddedDetails("title", value);
		test.maps_sessionpage.selectAbtract(value);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Withdraw Selected");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Withdraw Selected");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Successful Update");
		test.maps_sessionpage.verifyWithDrawRow("(W)", value);
	}

	@Test
	public void Step_3321_MAPS_Session_3295_Verify_application_withdraws_abstract_from_current_session_and_displays_SW_in_front_of_abstract_wihtdrawn() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Add/Remove/Withdraw Abstract");
		test.maps_reviewpage.enterValueInFilter("Submitted");
		String value = test.maps_sessionpage.addHostforSymposium("abstract_id");
		test.maps_sessionpage.verifyAddedDetails("abstract_id", value);
		test.maps_sessionpage.selectAbtract(value);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Withdraw Selected");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Withdraw (This Session Only)");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Successful Update");
		test.maps_sessionpage.verifyWithDrawRow("(SW)", value);
	}

	@Test
	public void Step_3324_MAPS_Session_3298_Verify_application_removes_selected_abstracts_when_clicks_on_Remove_Selected_button() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Add/Remove/Withdraw Abstract");
		test.maps_reviewpage.enterValueInFilter("Submitted");
		String symposiumTitle = test.maps_sessionpage.addHostforSymposium("title");
		test.maps_sessionpage.verifyAddedDetails("title", symposiumTitle);
		test.maps_sessionpage.selectAbtract(symposiumTitle);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Remove Selected");
		test.maps_sessionpage.verifyDataIsDeleted("session_name", symposiumTitle);
	}

	@Test
	public void Step_3329_MAPS_Session_3303_Verify_application_displays_updated_Duration_for_all_the_available_abstracts_under_Currently_Assigned_Abstracts_section() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Add/Remove/Withdraw Abstract");
		test.maps_reviewpage.enterValueInFilter("Submitted");
		abstractDetails = test.maps_sessionpage.addHostforSymposium("abstract_id", 4);
		test.maps_sessionpage.verifyAddedAbstracts("abstract_id", abstractDetails);
		test.maps_sessionpage.selectAbtract(abstractDetails);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Mass Update");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Assign Durations");
		test.maps_sessionpage.verifyPopupMessage("Assign Abstracts Duration");
		test.maps_sessionpage.enterValuesInAssignDurationPage("Assign each Abstract a duration of",
				YamlReader.getYamlValue("Session.Schedule_Sessions.assign_Duration"));
		test.maps_sessionpage.clickOnButtonByIndexing("Assign", "1");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Successfully Saved");
		test.maps_sessionpage.verifyDurationForAssignedAbstracts("duration",
				YamlReader.getYamlValue("Session.Schedule_Sessions.assign_Duration"), 4);

	}

	@Test
	public void Step_3330_MAPS_Session_3304_Verify_application_throws_pop_message_when_user_tries_to_assign_more_than_available_duration() {
		test.maps_sessionpage.selectAbtract(abstractDetails);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Mass Update");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Assign Durations");
		test.maps_sessionpage.verifyPopupMessage("Assign Abstracts Duration");
		test.maps_sessionpage.enterValuesInAssignDurationPage("Assign each Abstract a duration of", "300");
		test.maps_sessionpage.clickOnButtonByIndexing("Assign", "1");
		test.maps_sessionpage.verifyPopupMessage(YamlReader.getYamlValue("Session.Schedule_Sessions.err_msg_duration"));
		test.maps_sessionpage.clickOnButtonByIndexing("OK", "1");
	}

	@Test
	public void Step_3331_MAPS_Session_3305_Verify_user_can_assign_less_than_available_time() {
		test.maps_sessionpage.selectAbtract(abstractDetails);
		timeDurationLabel = test.maps_sessionpage.getTimeDurationValues("Current Duration:");
		test.maps_sessionpage.enterValuesInAssignDurationPage("Assign each Abstract a duration of", "30");
		test.maps_sessionpage.clickOnButtonByIndexing("Assign", "1");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Successfully Saved");
		test.maps_sessionpage.verifyDurationForAssignedAbstracts("duration",
				YamlReader.getYamlValue("Session.Schedule_Sessions.assign_Duration"), 4);
	}

	@Test
	public void Step_3332_MAPS_Session_3306_Verify_Current_duration_get_updated_when_assign_less_than_available_time() {
		test.maps_sessionpage.verifyTimeDuration("Current Duration:", timeDurationLabel);
	}

	@Test
	public void Step_3339_MAPS_Session_3313_Verify_applicaiton_assigns_FinalIDs_on_clicking_Continue_button_on_Select_types_of_Items_to_Assign_popup() {
		String hashtag = YamlReader.getYamlValue("Session.Schedule_Sessions.hash_tag") + System.currentTimeMillis();
		abstractDetails = test.maps_sessionpage.addHostforSymposium("abstract_id", 4);
		test.maps_sessionpage.verifyAddedAbstracts("abstract_id", abstractDetails);
		test.maps_sessionpage.selectAbtract(abstractDetails);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Mass Update");
		test.maps_sessionpage.clickButtonToContinueToNextPage(" Assign Final ID(s)");
		test.maps_sessionpage.verifyPopupMessage("Assign Abstract Final ID(s)");
		test.maps_sessionpage.enterValuesInAssignAbstractFinalID(
				YamlReader.getYamlValue("Session.Schedule_Sessions.finalId_name") + System.currentTimeMillis(),
				hashtag);
		test.maps_sessionpage.clickButtonToContinueToNextPage("Save");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Run");
		test.maps_sessionpage.verifyPopupMessage("Select Types of Items to Assign");
		test.maps_sessionpage.clickCheckboxOnAbstractAssignPage(abstractTypes);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Continue");
		test.maps_sessionpage.verifyAddedDetails("final_id", hashtag);

	}

	@Test
	public void Step_3340_MAPS_Session_3314_Verify_application_allows_user_to_edit_saved_format() {
		hashtag = YamlReader.getYamlValue("Session.Schedule_Sessions.hash_tag") + System.currentTimeMillis();
		abstractDetails = test.maps_sessionpage.addHostforSymposium("abstract_id", 4);
		test.maps_sessionpage.verifyAddedAbstracts("abstract_id", abstractDetails);
		test.maps_sessionpage.selectAbtract(abstractDetails);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Mass Update");
		test.maps_sessionpage.clickButtonToContinueToNextPage(" Assign Final ID(s)");
		test.maps_sessionpage.verifyPopupMessage("Assign Abstract Final ID(s)");
		test.maps_sessionpage.enterValuesInAssignAbstractFinalID(
				YamlReader.getYamlValue("Session.Schedule_Sessions.finalId_name") + System.currentTimeMillis(),
				hashtag);
		test.maps_sessionpage.clickButtonToContinueToNextPage("Save");
		test.maps_sessionpage.clickButtonToContinueToNextPage("edit");
		test.maps_sessionpage.verifyFieldsArePrefilledWithSavedDetails();
	}

	@Test
	public void Step_3341_MAPS_Session_3315_Verify_application_allows_user_to_delete_saved_format() {
		test.maps_sessionpage.clickButtonToContinueToNextPage("Save");
		test.maps_sessionpage.clickButtonToContinueToNextPage("delete");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Successfully deleted");
		test.maps_sessionpage.verifyFinalIdIsDeleted(hashtag);
		test.maps_sessionpage.clickOnButtonByIndexing("Close", "2");
	}

	@Test
	public void Step_3355_MAPS_Session_3329_Verify_application_allows_user_to_edit_fields_with_a_Pencil_icon_by_double_clicking_fields() {
		String value = test.maps_sessionpage.addHostforSymposium("title");
		String updatedVal = test.maps_sessionpage.clickOnPencilIconCloumn("title", value);
		test.maps_sessionpage.clickOnButtonByIndexing("Save", "1");
		test.maps_sessionpage.verifyAddedDetails("title", updatedVal);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Save & Close");
	}

	@Test
	public void Step_3357_MAPS_Session_3331_Verify_application_saves_the_session_on_clicking_Save_button() {
		eventname = YamlReader.getYamlValue("Session.Schedule_Sessions.Session_Title") + System.currentTimeMillis();
		test.maps_sessionpage.expandSideTab("Sessions");
		test.maps_sessionpage.expandSideTab("Events");
		test.maps_sessionpage.rightClickOnSession();
		test.maps_sessionpage.clickButtonToContinueToNextPage("Edit Item");
		test.maps_sessionpage.verifyPopupMessage("Edit Session");
		test.maps_sessionpage.enterValuesForProgram("session_name", eventname);
		test.maps_sessionpage.enterValuesForProgram("session_duration",
				YamlReader.getYamlValue("Session.Schedule_Sessions.assign_Duration"));
		test.maps_sessionpage.clickOnButtonByIndexing("Save", "1");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Successfully Saved");
		eventInfo.put("Title", eventname);
	}

	@Test
	public void Step_3468_MAPS_Session_3442_Verify_application_saves_session_on_clicking_Save_button() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Add/Edit Hosts");
		String abstractDetail = test.maps_sessionpage.addHostforSymposium("session_host_email");
		test.maps_sessionpage.verifyAddedAbstractsInTable("session_host_first_name", abstractDetail);
		test.maps_sessionpage.clickOnButtonByIndexing("Save", "1");
		test.maps_sessionpage.verifyPopupMessage("Confirm");
		test.maps_sessionpage.clickOnSaveButton("Yes");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Successfully Saved");
		eventInfo.put("Hosts", test.maps_sessionpage.getHostDetails("session_host_first_name"));
	}

	@Test
	public void Step_3469_MAPS_Session_3443_Verify_Application_Saves_Session_On_Clicking_Save_And_Close_Button() {
		test.maps_sessionpage.clickOnButtonByIndexing("Save & Close", "1");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Successfully Saved");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Schedule Sessions");
		test.maps_sessionpage.verifyPopupMessage("Select Program");
		test.maps_sessionpage.selectValueForCreateNewSessionFromSymposium("Programs",
				YamlReader.getYamlValue("Session.Schedule_Sessions.Programs"));
		test.maps_sessionpage.clickOnButtonByIndexing("OK", "1");
		test.maps_sessionpage.expandSideTab("Sessions");
		test.maps_sessionpage.expandSideTab("Events");
		test.maps_sessionpage.clickOnUpdatedEventName(eventname);
		test.maps_sessionpage.verifySessionOrEventInformation(eventInfo);
	}

	@Test
	public void Step_3486_MAPS_Session_3459_Verify_Application_Displays_Filtered_Results_On_Itinerary_Level_Top_view() {
		test.maps_sessionpage.clickOnSaveButton("Switch to Itinerary Level View");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Itinerary Level Top");
		test.maps_reviewpage.verifyTextField("Filter");
		test.maps_reviewpage.verifyCrossImageForNamedDropDown("Filter");
		sessionName = test.maps_sessionpage.getRandomSessionName();
		test.maps_reviewpage.enterValueInFilter(sessionName);
		test.maps_sessionpage.verifyFilterResultsForSessions(sessionName);
		test.maps_reviewpage.clickOnCrossImageForNamedDropdown("Filter");
	}

	@Test
	public void Step_3500_MAPS_Session_3473_Verify_Application_Displays_Options_Button_And_Export_Level_Assignments_to_CSV_Dropdown_At_Top_Of_Add_Session() {
		test.maps_sessionpage.rightClickOnTopLevelSession("Top Level");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Add Session");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Add/Modify Session for Top Level");
		test.maps_sessionpage.selectRoleOnSaveGridConfiguration("Options");
		test.maps_sessionpage.verifydropdownOnPopupWindow("Export Level Assignments to CSV");
	}

	@Test
	public void Step_3534_MAPS_Session_3507_Verify_Application_Removes_Selected_Session_Or_Event() {
		test.maps_sessionpage.inputTextInFilter("Test Session", "2");
		String sessionTitle = test.maps_sessionpage.selectaRecordFromTheList(1, "1");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Add Selected");
		test.maps_sessionpage.waitForLoadingImageToDisappear("Loading...");
		test.maps_sessionpage.selectSessionOrEvent(sessionTitle, "1");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Remove Selected");
		test.maps_sessionpage.verifyPopupMessage("Confirm");
		test.maps_sessionpage.clickOnSaveButton("Yes");
		test.maps_sessionpage.verifyTextUnderMeetingSetup("Selected entries were deleted");
		test.maps_sessionpage.selectSessionOrEvent(sessionTitle, "1");
		test.maps_sessionpage.verifySelectedSessionIsRemovedFromList(sessionTitle, "1");
	}

	@Test

	public void Step_3534_MAPS_Session_3518_Verify_Application_Displays_Add_Or_Modify_Event_For_Top_Level_Window_With_Available_Fields() {
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Add/Modify Session for Top Level");
		test.maps_sessionpage.verifyTitleForRoles("Available Sessions", "Title");
		test.maps_sessionpage.verifyTitleForRoles("Assigned to Level", "Title");
		test.maps_sessionpage.isPrintSelectedButtonDisplayed("Close");
		test.maps_sessionpage.clickOnSaveButton("Close");
		test.maps_sessionpage.clickOnSaveButton("Ok");
	}

	@Test
	public void Step_3591_MAPS_Session_3564_Verify_Application_Launches_Add_Or_Modify_Popup_On_Clicking_Add_Level_Option() {
		test.maps_sessionpage.rightClickOnTopLevelSession("Top Level");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Add Level");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Add / Modify Level");
	}

	@Test
	public void Step_3597_MAPS_Session_3570_Verify_Application_Creates_A_Level_Or_SubLevel_On_Clicking_Add_Level_Option() {
		levelName = "Test Level" + System.currentTimeMillis();
		test.maps_sessionpage.enterNameOnSaveGridConfiguration("Name:", levelName);
		test.maps_sessionpage.selectValuesForProgram("start_time", 1);
		test.maps_sessionpage.selectValuesForProgram("end_time", 1);
		test.maps_sessionpage.clickOnSaveButton("Save");
		test.maps_sessionpage.verifyTitleForRoles(levelName, "Newly Added Level name");
	}

	@Test
	public void Step_3600_MAPS_Session_3573_Verify_Application_Saves_Changes_On_Clicking_Edit_Level_Option() {
		levelName = "QAIT level" + System.currentTimeMillis();
		test.maps_sessionpage.rightClickOnTopLevelSession(levelName);
		test.maps_sessionpage.clickButtonToContinueToNextPage("Edit Level");
		test.maps_sessionpage.enterNameOnSaveGridConfiguration("Name:", levelName);
		test.maps_sessionpage.clickOnSaveButton("Save");
		test.maps_sessionpage.verifyTitleForRoles(levelName, "Updated Level name");
	}

	@Test
	public void Step_3608_MAPS_Session_3581_Verify_Application_Launches_Add_Or_Modify_Session_Popup_On_Clicking_Edit_Session_Option() {
		test.maps_sessionpage.rightClickOnTopLevelSession(sessionName);
		test.maps_sessionpage.clickButtonToContinueToNextPage("edit");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Add/Modify Session for Top Level");
		test.maps_sessionpage.clickOnSaveButton("Close");
		test.maps_sessionpage.clickOnSaveButton("Ok");
	}

	public void Step_3611_MAPS_Session_3584_Application_Should_Delete_Session_On_Clicking_Yes_Button_On_PopUp() {
		test.maps_sessionpage.rightClickOnTopLevelSession(itenarySessionName);
		test.maps_sessionpage.clickOnSaveAndEditButton("Delete", 1);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Yes");
		test.maps_sessionpage.verifySessionIsdeletedFromScheduleSession(itenarySessionName);
	}

	@Test
	public void Step_3614_MAPS_Session_3586_Application_Navigates_User_To_Abstract_page() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Abstracts");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Abstracts");
	}

	@Test
	public void Step_3695_MAPS_Session_3665_Verify_Application_Prints_The_Table_On_Clicking_Print_Button()
			throws AWTException {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Reports");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Session Event Report");
		test.maps_sessionpage.clickOnButtonUnderSessioning("Print");
		test.maps_sessionpage.verifyApplicationPrintsReportsTable();

	}

	@Test
	public void Step_3703_MAPS_Session_3586_Application_Should_Run_Report_And_Display_Conflicting_Records_In_Table() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Participant Conflict Report");
	}

}