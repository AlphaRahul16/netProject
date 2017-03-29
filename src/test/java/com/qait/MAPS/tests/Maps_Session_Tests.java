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
	private String[] roles = { "OPA Staff", "Program Viewer", "Program Chair Sessioning", "Abstract Editor",
			"Session Admin" };

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
	public void MAPS_Session_1_Click_On_Session_In_Top_Navigation_Menu() {
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Session");
		test.maps_SSOPage.verifyUserIsOnTabPage("Session");
		test.maps_reviewpage.verifyPageHeader("Multiple Role Selection");
		test.maps_sessionpage.verifyApplicationDisplaysRadioButtonOnClickingSessionTab(roles);

	}

	// @Test
	// public void
	// MAPS_Session_4_User_Should_Navigate_To_Abstract_Page_On_Clicking_Go_Button()
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
	// MAPS_Session_67_Verify_options_available_on_Program_Viewer_page() {
	//
	// }

	@Test
	public void MAPS_Session_784_Verify_Application_navigates_to_Session_Admin_page_on_selecting_the_Session_Admin_radio_button() {
		test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("Session Admin");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Select");
		// page title is not available
		// test.maps_sessionpage.verifyTitleForRoles("Session Admin");
	}

	// @Test passed
	public void MAPS_Session_785_Verify_options_available_on_Session_Admin_page() {
		String[] leftPanelOptions = { "Dashboard & Instructions", "Meeting Setup", "Sessioning", "Invitations & Email",
				"Reports", "Data Export" };
		test.maps_reviewpage.verifybuttonOnRolesPage("Set Preferences");
		test.maps_sessionpage.verifyLeftPanelOptionsOnSessionAdminPage(leftPanelOptions);
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

	//@Test // Passed
	public void MAPS_Session_799_Verify_that_application_filters_the_result_on_the_basis_of_the_criteria_provided() {
		String programID = test.maps_sessionpage.getValueFromProgramsTable();
		test.maps_reviewpage.enterValueInFilter(programID);
		test.maps_sessionpage.verifyTheResultOfFilter(programID);
	}

	@Test
	public void MAPS_Session_815_Verify_that_application_prompts_the_user_to_save_the_program_before_adding_the_owners() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Meeting Setup");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Programs");
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Create Program");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Create Program");
		test.maps_sessionpage.enterValuesInCreateProgramPage("QAIT_Test" + System.currentTimeMillis(), "30");
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Add Owners");
		test.maps_sessionpage.verifyPopupMessage("The program must be saved before adding owners. Ok?");
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("No");
	}

	//
	// @Test//not verified functionality
	// public void
	// MAPS_Session_847_Verify_that_application_navigates_to_Add_Edit_Rooms_tab_on_Saving_all_Information(){
	// test.maps_sessionpage.clickButtononLeftNavigationPanel("Meeting Setup");
	// test.maps_sessionpage.clickButtononLeftNavigationPanel("Programs");
	// test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Create
	// Program");
	// test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Create
	// Program");
	// test.maps_sessionpage.enterValuesInCreateProgramPage("QAIT_Test" +
	// System.currentTimeMillis(), "30");
	// test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Save");
	// test.maps_sessionpage.verifyPopupMessage("Add/Edit Rooms");
	// }
	// @Test
	// public void
	// MAPS_Session_875_Verify_that_the_application_Saves_data_and_creates_a_new_program_on_clicking_Save_button()
	// {
	// test.maps_sessionpage.clickButtononLeftNavigationPanel("Meeting Setup");
	// test.maps_sessionpage.clickButtononLeftNavigationPanel("Programs");
	// test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Create
	// Program");
	// test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Create
	// Program");
	// test.maps_sessionpage.enterValuesInCreateProgramPage("QAIT_Test" +
	// System.currentTimeMillis(), "30");
	// test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Save");
	// }
	//
	// @Test
	public void MAPS_Session_1056_Verify_that_application_navigates_to_Types_page_on_clicking_Type_option_under_the_Meeting_Setup() {
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Meeting Setup");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Types");
		test.maps_sessionpage.verifyPopupMessage("Types");
	}

	// @Test
	public void MAPS_Session_1057_Verify_options_available_on_the_Types_page() {
		String[] button = { "Add Type", "Delete Types", "Export to Excel", "Import Session/Event/Symposium Types" };
		test.maps_sessionpage.verifyButtonsOnTypes(button);
	}
}
