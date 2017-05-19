package com.qait.MAPS.tests;

import java.lang.reflect.Method;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class Maps_Session_Program_Chair_Sessioning_Tests extends BaseTest {
	private String maps_url,gridName;
	private String[] roles = { "OPA Staff", "Program Viewer", "Program Chair Sessioning", "Abstract Editor",
			"Session Admin" };

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
	public void Step_0421_MAPS_Session_410_Verify_Grids_On_Assign_Abstracts_Page_Under_Program_Chair_Sessioning() {
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Session");
		test.maps_SSOPage.verifyUserIsOnTabPage("Session");
		test.maps_reviewpage.verifyPageHeader("Multiple Role Selection");
		test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("Program Chair Sessioning");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Select");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
//		test.maps_sessionpage.clickOnSessionBuilderTab("Session Builder");
//		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Session Builder");
//		test.maps_sessionpage.verifyAbstractsViewIsDisplayed();
//		test.maps_sessionpage.verifyAbstractsListIsPresent("x-border-panel x-border-layout-ct");
	}

	//@Test
	public void Step_0422_MAPS_Session_411_Verify_Currently_Assign_Abstracts_Section_Is_Displayed_When_Any_Session_Is_Selected() {
		test.maps_sessionpage.selectaRecordFromTheList(1,"2");
		test.maps_sessionpage.waitForLoadingImageToDisappear("Loading...");
		String heading = "Currently Assigned Abstracts" + test.maps_sessionpage.getCheckedColumnData("1", "4");
		test.maps_sessionpage.verifyPopupMessage(heading);
	}

	@Test
	public void Step_0689_MAPS_Session_677_Verify_Creation_Of_New_Grid_Under_Program_Chair_Sessioning() {
		gridName = "Test_Grid" + System.currentTimeMillis();
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Abstracts");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Abstracts Assigned To Me");
		test.maps_sessionpage.clickOnDropDownImage(1);
		test.maps_sessionpage.selectRoleOnSaveGridConfiguration("Select One...");
		test.maps_reviewpage.clickOnButton("Save/Edit");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Save Grid Configuration");
		test.maps_reviewpage.enterDetailsAtSaveGridConfigurationPage(gridName);
		test.maps_sessionpage.clickOnButtonUnderSessioning("Save");
		test.maps_sessionpage.verifyFilterIsByDefaultSelected(gridName, 1);
	}

	@Test
	public void Step_0741_MAPS_Session_728_Verify_User_Is_Navigated_To_Abstract_Page_Under_Abstract_Editor() {
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Session");
		test.maps_SSOPage.verifyUserIsOnTabPage("Session");
		test.maps_reviewpage.verifyPageHeader("Multiple Role Selection");
		test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("Abstract Editor");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Select");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
		test.maps_sessionpage.clickOnSessionBuilderTab("Abstracts");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Abstracts Assigned To Me");
	}

	@Test //failing
	public void Step_0742_MAPS_Session_729_Available_Options_On_Abstracts_Page_Under_Abstract_Editor() {
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Abstracts Assigned To Me");
		test.maps_reviewpage.verifyLinksUnderNamedModule("Save/Edit");
		test.maps_reviewpage.verifyLinksUnderNamedModule("Delete");
		test.maps_reviewpage.verifyTextField("Filter");
		test.maps_reviewpage.verifyCrossImageForNamedDropDown("Filter");
		test.maps_reviewpage.verifyExpandIconUnderNamedModule();
		test.maps_reviewpage.verifyDropDown("Found In");
		test.maps_reviewpage.verifyDropDown("Export to Excel");
		test.maps_reviewpage.verifyDropDown("Toggle View");
		test.maps_sessionpage.verifyAbstractsListIsPresent("x-grid3-body");
		test.maps_reviewpage.verifyPaginationSectionAtTheBottomOfTheTable();
		test.maps_reviewpage.verifyDropDown("Records per page");
		test.maps_sessionpage.verifyRefreshButtonAtBottom();
	}
	
	@Test
	public void Step_0768_MAPS_Session_755_Verify_Edit_Links_Are_Present_For_Abstracts_And_User_Is_Navigated_To_Review_And_Submit_Page_On_Clicking_Edit_Link() {
		test.maps_sessionpage.selectAbstractForEditing(1, 4);
		test.maps_submissionPage.verifyPageHeading("Review & Submit");
		test.maps_sessionpage.editAbstractDetails("Test Title", "Test Abstract");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Abstracts Assigned To Me");
		test.maps_sessionpage.verifyAddedDetails("title", "Test Title");
	}

	@Test
	public void Step_0774_MAPS_Session_761_Verify_Application_Sorts_Results_On_Basis_Of_Criteria_Under_Abstract_Editor() {
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
