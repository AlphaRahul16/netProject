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

public class Maps_Session_Program_Viewer_Tests extends BaseTest {

	private String maps_url, symposiumTitle, selectedsymposia, gridName, lastRecordData, roomName, sessionTitle,
			downloadedFilePath;
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
		test.maps_sessionpage.verifyLeftPanelOptionsOnSessionAdminPage(programViewerleftpaneloptions);
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
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

	/**
	 * Session : Program Viewer : Symposia
	 */
	@Test
	public void Step_0080_MAPS_Session_74_Verify_Application_Allows_User_To_Create_A_New_Grid_In_Symposia() {
		test.maps_reviewpage.clickOnButton("Save/Edit");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Save Grid Configuration");
		test.maps_reviewpage.enterDetailsAtSaveGridConfigurationPage("Symposia_" + griduniqueName);
	}

	@Test
	public void Step_0099_MAPS_Session_93_Verify_Application_Print_Selected_Symposia() {
		selectedsymposia = test.maps_sessionpage.selectaRandomRecordFromTheList();
		test.maps_sessionpage.clickOnSaveButton("Print Selected");
	}
}