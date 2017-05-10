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

public class Maps_Session_Program_Viewer_Tests extends BaseTest {

	private String maps_url, selectedsymposia;
	private String griduniqueName = "Selenium_Test_Grid_" + System.currentTimeMillis();
	private String[] roles = { "OPA Staff", "Program Viewer", "Program Chair Sessioning", "Abstract Editor",
			"Session Admin" };
	private String[] programViewerleftpaneloptions = { "Dashboard & Instructions", "Symposia Viewer", "Sessioning",
			"Session Viewer", "Abstracts" };


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

	@Test // List of Symposia verification is remaining due to its
	// unavailability
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
	}


	
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
		test.maps_sessionpage.clickOnButtonUnderSessioning("Cancel");
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
		test.maps_sessionpage.clickOnButtonUnderSessioning("Cancel");
		
	}
	
	@Test
	public void Step_0103_MAPS_Session_97_Verify_Application_Download_Csv_File_On_Clicking_Export_To_Excel_Dropdown_For_Selected_Symposia() {
		test.maps_sessionpage._deleteExistingCSVFile(YamlReader.getYamlValue("Session.Program_Viewer.File_Downloaded_Name"));
		test.maps_sessionpage.clickOnButtonUnderSessioning("Export");
		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Export to Excel (Displayed Columns)");
		test.maps_sessionpage.waitForProcessBarToDisappear();
		test.maps_sessionpage.verifyValidFileIsDownloaded(YamlReader.getYamlValue("Session.Program_Viewer.File_Downloaded_Name"));
	}
	
	@Test
	public void Step_0104_MAPS_Session_98_Verify_Application_Download_Csv_File_On_Clicking_Export_To_Excel_Dropdown_For_All_Symposia() {
		test.maps_sessionpage._deleteExistingCSVFile(YamlReader.getYamlValue("Session.Program_Viewer.File_Downloaded_Name_symposia"));
		test.maps_sessionpage.clickOnButtonUnderSessioning("Export");
		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Export to Excel (All Columns)");
		test.maps_sessionpage.waitForProcessBarToDisappear();
		test.maps_sessionpage.verifyValidFileIsDownloaded(YamlReader.getYamlValue("Session.Program_Viewer.File_Downloaded_Name_symposia"));
	}

	@Test
	public void Step_0115_MAPS_Session_109_Verify_Application_Sorts_The_Results_On_The_Basis_Of_Criteria_Program_Viewer()
	{
		List<String> dataBeforeSorting = test.maps_sessionpage.getTableData("1", "4");
		test.maps_sessionpage.clickOnArrowButton("Title");
		//test.maps_sessionpage.clickOnArrowButton("Control ID");
		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Configure Sort");
		test.maps_sessionpage.verifyPopupMessage("Sort");
		test.maps_sessionpage.clickOnAddButton("Add");
		test.maps_sessionpage.selectColumnForSorting("Title");													
		test.maps_sessionpage.clickOnButtonUnderSessioning("Apply");
		List<String> dataAfterSorting = test.maps_sessionpage.getTableData("1", "4");
		test.maps_sessionpage.verifyDataIsSorted(dataBeforeSorting, dataAfterSorting);
	}
	
	@Test
	public void Step_0179_MAPS_Session_171_Verify_Options_Available_On_Session_Viewer_Page()
	{
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Session Viewer");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Sessions & Events Assigned To Me (View Only)");
		test.maps_reviewpage.verifyLinksUnderNamedModule("Save/Edit");
		test.maps_reviewpage.verifyLinksUnderNamedModule("Delete");
		test.maps_reviewpage.verifyTextField("Filter");
		test.maps_reviewpage.verifyCrossImageForNamedDropDown("Filter");
		test.maps_reviewpage.verifyExpandIconUnderNamedModule();
		test.maps_reviewpage.verifyDropDown("Found In");
		test.maps_reviewpage.verifyDropDown("Export to Excel");
		test.maps_sessionpage.isPrintSelectedButtonDisplayed("Print Selected");
		test.maps_reviewpage.verifyPaginationSectionAtTheBottomOfTheTable();
		test.maps_reviewpage.verifyDropDown("Records per page");
		test.maps_sessionpage.verifyRefreshButtonAtBottom();
	}
	
	
	@Test
	public void Step_0182_MAPS_Session_175_Verify_Application_Allows_User_To_Create_A_New_Grid_On_Session_Viewer_Under_Program_Viewer_Page() {
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
	public void Step_0200_MAPS_Session_193_Verify_Application_Print_Selected_Symposia() {
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
	public void Step_0201_MAPS_Session_194_Verify_Application_Print_All_Symposia_on_Clicking_Print_Selected_Button() {
		int symposiasize;
		test.maps_sessionpage.selectHeaderCheckbox();
		symposiasize=test.maps_sessionpage.getSelectedListSize();
		test.maps_sessionpage.clickOnButtonUnderSessioning("Print Selected");
		test.maps_sessionpage.verifyTitleForRoles("Print Sessions/Events Preview");
		test.maps_sessionpage.verifyAllSelectedListIsPresentInPrintPreview(symposiasize);
		test.maps_submissionPage.clickOnNamedButton("Cancel");
		
	}
	
	@Test
	public void Step_0204_MAPS_Session_197_Verify_Application_Download_Csv_File_On_Clicking_Export_To_Excel_Dropdown_For_Selected_Session() {
		test.maps_sessionpage._deleteExistingCSVFile(YamlReader.getYamlValue("Session.Program_Viewer.File_Downloaded_Name_session"));
		test.maps_sessionpage.clickOnButtonUnderSessioning("Export");
		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Export to Excel (Displayed Columns)");
		test.maps_sessionpage.waitForProcessBarToDisappear();
		test.maps_sessionpage.verifyValidFileIsDownloaded(YamlReader.getYamlValue("Session.Program_Viewer.File_Downloaded_Name_session"));
	}
		
	/**
	 * Session : Program Viewer : Symposia
	 */
	@Test
	public void Step_0205_MAPS_Session_198_Verify_Application_Download_Csv_File_On_Clicking_Export_To_Excel_Dropdown_For_All_Sessions() {
		test.maps_sessionpage._deleteExistingCSVFile(YamlReader.getYamlValue("Session.Program_Viewer.File_Downloaded_Name"));
		test.maps_sessionpage.clickOnButtonUnderSessioning("Export");
		test.maps_sessionpage.selectOptionsUnderColumnHeaders("Export to Excel (All Columns)");
		test.maps_sessionpage.waitForProcessBarToDisappear();
		test.maps_sessionpage.verifyValidFileIsDownloaded(YamlReader.getYamlValue("Session.Program_Viewer.File_Downloaded_Name"));
		
	}
	
	@Test
	public void Step_0276_MAPS_Session_269_Verify_Application_Launches_View_Event_PopUp_After_Clicking_View_Option()
	{
		test.maps_sessionpage.rightClickOnSessionList("1");
		test.maps_sessionpage.clickButtonToContinueToNextPage("View");
		test.maps_sessionpage.verifyTitleForRoles("View Session");
		test.maps_submissionPage.clickOnNamedButton("Cancel");
		
	}
	
	@Test
	public void Step_0279_MAPS_Session_272_Verify_Application_Filter_Result_In_View_Host()
	{
		test.maps_sessionpage.doubleClickOnRow("1");
		test.maps_sessionpage.clickOnSessionBuilderTab("View Host");
		test.maps_sessionpage.inputTextInFilter(test.maps_sessionpage.getHostColoumData("session_host_email"), "3");	
	}

	@Test
	public void Step_0312_MAPS_Session_304_Verify_Application_Navigates_To_Abstracts_On_Selecting_Abstract_Option()
	{
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Abstracts");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Abstracts Assigned To Me");
	}
	
	@Test
	public void Step_0313_MAPS_Session_305_Verify_Options_Available_Under_Abstracts()
	{
		test.maps_reviewpage.verifyLinksUnderNamedModule("Save/Edit");
		test.maps_reviewpage.verifyLinksUnderNamedModule("Delete");
		test.maps_reviewpage.verifyTextField("Filter");
		test.maps_reviewpage.verifyCrossImageForNamedDropDown("Filter");
		test.maps_reviewpage.verifyExpandIconUnderNamedModule();
		test.maps_reviewpage.verifyDropDown("Found In");
		test.maps_reviewpage.verifyDropDown("Export to Excel");
		test.maps_sessionpage.isPrintSelectedButtonDisplayed("Print Selected");
		test.maps_reviewpage.verifyPaginationSectionAtTheBottomOfTheTable();
		test.maps_reviewpage.verifyDropDown("Records per page");
		test.maps_sessionpage.verifyRefreshButtonAtBottom();
	}
	
	@Test
	public void Step_0315_MAPS_Session_307_Verify_Application_Allows_User_To_Create_A_New_Grid_On_Abstracts_Under_Program_Viewer_Page()
	{
		test.maps_reviewpage.clickOnButton("Save/Edit");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Save Grid Configuration");
	}
	
	@Test
	public void Step_0316_MAPS_Session_308_Verify_Options_Available_On_Save_Grid_Configuration_Under_Abstracts()
	{
		test.maps_sessionpage.verifyAvailableOptionsOnSaveGridPopUpUnderAbstracts("Use as Default");
		test.maps_sessionpage.verifyAvailableOptionsOnSaveGridPopUpUnderAbstracts("Added Filters");
		test.maps_sessionpage.verifyAvailableOptionsOnSaveGridPopUpUnderAbstracts("Make available to all users of this site");
		test.maps_sessionpage.isPrintSelectedButtonDisplayed("Save");
		test.maps_sessionpage.isPrintSelectedButtonDisplayed("Close");
		
	}


}