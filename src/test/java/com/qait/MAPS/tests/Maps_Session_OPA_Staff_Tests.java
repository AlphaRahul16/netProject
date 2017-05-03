package com.qait.MAPS.tests;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class Maps_Session_OPA_Staff_Tests extends BaseTest {

	private String maps_url, downloadedFilePath;
	private String griduniqueName = "Selenium_Test_Grid_" + System.currentTimeMillis();
	private String[] roles = { "OPA Staff", "Program Viewer", "Program Chair Sessioning", "Abstract Editor",
			"Session Admin" };
	private String[] fastColumnNames = { "Submission Role", "Control ID", "Edit Abstract", "Final ID", "Sessions",
			"Title", "Presenting Author", "Presentation Type", "Sci-Mix Consideration", "Symposia", "Abstract Status",
			"Date Submitted" };
	List<String> sortColumnList = new ArrayList<String>();
	List<String> controlId = new ArrayList<String>();
	

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
	public void Step_0004_MAPS_Session_1_Click_On_Session_In_Top_Navigation_Menu() {
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Session");
		test.maps_SSOPage.verifyUserIsOnTabPage("Session");
		test.maps_reviewpage.verifyPageHeader("Multiple Role Selection");
		test.maps_sessionpage.verifyApplicationDisplaysRadioButtonOnClickingSessionTab(roles);
	}

	/**
	 * Session : OPA Staff
	 */
	@Test
	public void Step_0010_MAPS_Session_6_User_Should_Navigate_To_Abstract_Page_On_Clicking_Go_Button() {
		test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("OPA Staff");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Select");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Abstracts Assigned To Me");

	}

	/**
	 * Session : OPA Staff
	 */
	@Test
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
	@Test
	public void Step_0015_MAPS_Session_11_Verify_Application_Allows_User_To_Create_A_New_Grid() {
		test.maps_reviewpage.clickOnButton("Save/Edit");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Save Grid Configuration");
		test.maps_reviewpage.enterDetailsAtSaveGridConfigurationPage("Abstract_" + griduniqueName);
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Save");
	}
	@Test
	public void Step_0021_MAPS_Session_17_Verify_Application_Filters_Result_On_Basis_Of_Criteria_For_OPA_Staff() {
		String recordName = test.maps_sessionpage.getRandomRecordFromTable("6");
		test.maps_reviewpage.enterValueInFilter(recordName);
		test.maps_sessionpage.verifyFilterResults(recordName, 1, 7);
		test.maps_reviewpage.clickOnCrossImageForNamedDropdown("Filter");
	}
	@Test
	public void Step_0032_MAPS_Session_28_Verify_Application_Exports_Relevant_Csv_File_On_Clicking_Any_Otion_Under_Export_To_Excel_Dropdown() {
		downloadedFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + "DownloadedFiles" + File.separator
				+ YamlReader.getYamlValue("Session.OPA_Staff.File_Downloaded_Name") + ".csv";
		test.maps_sessionpage._deleteExistingCSVFile(downloadedFilePath);
		test.maps_sessionpage.clickOnSaveButton("Export to Excel");
		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Export to Excel (All Columns)");
		test.maps_sessionpage.waitForProcessBarToDisappear();
		test.maps_sessionpage.verifyValidFileIsDownloaded(downloadedFilePath);

		test.maps_sessionpage.clickOnDownloadButtonAndVerifyValidFileIsDownloaded("Export  to Excel (All Columns)",
				YamlReader.getYamlValue("Session.OPA_Staff.File_Downloaded_Name"));
		test.maps_sessionpage.clickOnSaveButton("Export to Excel");
		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Export to Excel (Displayed Columns)");
		test.maps_sessionpage.waitForProcessBarToDisappear();

		test.maps_sessionpage.clickOnDownloadButtonAndVerifyValidFileIsDownloaded("Export to Excel (Displayed Columns)",
				YamlReader.getYamlValue("Session.OPA_Staff.File_Downloaded_Name"));
		test.maps_sessionpage._deleteExistingCSVFile(downloadedFilePath);
		test.maps_sessionpage.clickOnSaveButton("Export to Excel");
		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Export to Excel (Displayed Columns)");
		test.maps_sessionpage.waitForProcessBarToDisappear();
		test.maps_sessionpage.verifyValidFileIsDownloaded(downloadedFilePath);
	}


	@Test // updated
	public void Step_0034_MAPS_Session_30_Verify_Application_Switches_Views_On_Selecting_Options_From_Toggle_View_Dropdown() {
		test.maps_sessionpage.clickOnSaveButton("Toggle View");
		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Fast");
		test.maps_sessionpage.clickOnArrowButton("Title");
		test.maps_sessionpage.hoverOverColumnHeader("Columns");
		test.maps_sessionpage.verifyColumnHeaders(test.maps_sessionpage.getCheckedColumnHeadings());
		test.maps_sessionpage.clickOnSaveButton("Toggle View");
		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Full");
		test.maps_sessionpage.clickOnArrowButton("Title");
		test.maps_sessionpage.hoverOverColumnHeader("Columns");
		test.maps_sessionpage.verifyColumnHeaders(test.maps_sessionpage.getCheckedColumnHeadings());
	}
	
	@Test
	public void Step_0038_MAPS_Session_34_Verify_Edit_Link_Is_Present_For_Abstracts_And_User_Is_Navigated_To_Review_And_Submit_Page_On_Clicking_Edit_Link() {
		test.maps_sessionpage.expandColumnWidth("Edit Abstract", "60");
		test.maps_sessionpage.clickOnArrowButton("Edit Abstract");
		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Sort Ascending");
		test.maps_sessionpage.verifyFilterResults("Edit", 1, 4);
		test.maps_sessionpage.selectAbstractForEditing(1, 4);
		test.maps_submissionPage.verifyPageHeaderForASection("Review & Submit");
	}


	@Test
	public void Step_0041_MAPS_Session_37_Verify_Edit_Link_Is_Present_For_Abstracts_And_User_Is_Navigated_To_Abstracts_Page_On_Clicking_Finish_Button() {
		test.maps_sessionpage.clickOnArrowButton("Edit Abstract");
		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Sort Ascending");
		test.maps_sessionpage.verifyFilterResults("Edit", 1, 4);
		test.maps_sessionpage.editAbstractDetails("Test Title", "Test Abstract");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Abstracts Assigned To Me");
	}

	@Test
	public void Step_0048_MAPS_Session_44_Verify_User_Is_Able_To_Add_Sorting_Scenarios_By_Clicking_Add_Button_On_Sort_Popup() {
		test.maps_sessionpage.clickOnArrowButton("Title");
		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Configure Sort");
		test.maps_sessionpage.verifyPopupMessage("Sort");
		sortColumnList = test.maps_sessionpage.getTableDataFromSortPopUpWindow("col-F_SORT_FIELD", fastColumnNames);
		test.maps_sessionpage.clickOnAddButton("Add");
		test.maps_sessionpage.verifyColumnNamesPresentForSorting(sortColumnList);
		test.maps_sessionpage.selectColumnForSorting("Title");
		test.maps_sessionpage.verifySortingOrderIsPresent("Title");
		test.maps_sessionpage.clickOnSaveButton("Close");
	}
	
	@Test // functionality to be added
	public void Step_0052_MAPS_Session_48_Verify_Application_Sorts_Results_On_Basis_Of_Criteria() {
		List<String> dataBeforeSorting = test.maps_sessionpage.getTableData("1", "3");
		test.maps_sessionpage.clickOnArrowButton("Control ID");
		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Configure Sort");
		test.maps_sessionpage.verifyPopupMessage("Sort");
		test.maps_sessionpage.clickOnAddButton("Add");
		test.maps_sessionpage.selectColumnForSorting("Control ID");
		test.maps_sessionpage.clickOnSaveButton("Apply");
		List<String> dataAfterSorting = test.maps_sessionpage.getTableData("1", "3");
		test.maps_sessionpage.verifyDataIsSorted(dataBeforeSorting, dataAfterSorting);
	}
}