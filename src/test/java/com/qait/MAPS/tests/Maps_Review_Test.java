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
import com.qait.automation.utils.CSVFileReaderWriter;
import com.qait.automation.utils.DataProvider;
import com.qait.automation.utils.YamlReader;

public class Maps_Review_Test extends BaseTest {
	List<String> dataForImportedFile = new ArrayList<String>();
	private String maps_url;
	private String griduniqueName, multigridname;
	private String absract_id, abstract_details = "Test_Abstract" + System.currentTimeMillis();
	private String downloadedFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "test" + File.separator + "resources" + File.separator + "DownloadedFiles";
	private String programName;
	private List<String> colHeader;

	@BeforeClass
	public void Start_Test_Session() {
		System.out.println("in before class");
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		maps_url = YamlReader.getYamlValue("MAPS_Url");
	}

	@BeforeMethod
	public void printCaseIdExecuted(Method method) {
		test.printMethodName(method.getName());
	}

	@Test (groups={"P1","P2"})
	public void Step_00_MAPS_Review_Admin_00_Launch_Application_And_Verify_Home_Page() {
		test.launchMAPSApplication(maps_url);
		test.maps_SSOPage.loginWithValidCredentials(YamlReader.getYamlValue("LogIn_Details.userID"),
				YamlReader.getYamlValue("LogIn_Details.password"));
		test.maps_SSOPage.verifyUserIsOnTabPage("Welcome");
	}

	@Test (groups={"P1","P2"}) //passed 
	public void Step_06_MAPS_Review_Admin_01_Verify_that_application_navigates_to_ReviewerScoreReport_page() {
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Review");
		test.maps_reviewpage.verifyPageHeader("Multiple Role Selection");
		test.maps_reviewpage.selectRoleForReview("Review Admin");
		test.maps_reviewpage.clickOnButton("Select");
		test.maps_reviewpage.clickOnButton("Reviewer Score Report");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Reviewer Score Report");
	}

	//@Test (groups={"P1"})//passed
	public void  Step_12_MAPS_Review_Admin_02_Verify_options_available_on_ReviewerScoreReport_page() {
		test.maps_reviewpage.verifyLinksUnderNamedModule("Save/Edit");
		test.maps_reviewpage.verifyLinksUnderNamedModule("Delete");
		test.maps_reviewpage.verifyLinksUnderNamedModule("Clear Search");
		test.maps_reviewpage.verifyLinksUnderNamedModule("Hide Reviewer Comments");
		test.maps_reviewpage.verifyTextField("Search");
		test.maps_reviewpage.verifyCrossImageForNamedDropDown("Search");
		test.maps_reviewpage.verifyDropDown("Found In");
		test.maps_reviewpage.verifyDropDown("Import/Export to Excel");
		test.maps_reviewpage.verifyRoleDropDown();
		test.maps_reviewpage.verifyExpandIconUnderNamedModule();
		test.maps_reviewpage.verifyButton("Mass Update");
		test.maps_reviewpage.verifyReviewerScoreReportTable();
		test.maps_reviewpage.verifyPaginationSectionAtTheBottomOfTheTable();
	}
	
	//@Test(groups = { "P2" }) //passed
	public void  Step_07_MAPS_Review_Admin_03_Verify_columns_Information_available_under_Reviewer_score_report_table() {
		test.maps_sessionpage.clickOnArrowButton("Title");
		test.maps_sessionpage.hoverOverColumnHeader("Columns");
		test.maps_sessionpage.verifyColumnHeaders(test.maps_sessionpage.getCheckedColumnHeadings());
	}

	//@Test(groups = { "P2" }) //passed
	public void  Step_08_MAPS_Review_Admin_04_Verify_that_application_displays_greyedout_Grid_Configuration_dropdown_if_no_grid_view_is_available() {
		test.maps_reviewpage.verifyGridConfigurationDropdownNotDisplayed("Grid Configuration Dropdown");
	}

	//@Test(groups = { "P2" }) //passed
	public void  Step_09_MAPS_Review_Admin_05_Information_available_on_Save_Grid_Configuration_popup() {
		test.maps_reviewpage.clickOnButton("Save/Edit");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Save Grid Configuration");
		test.maps_sessionpage.verifyTextboxOnGridConfigPopup("Name");
		String chkBoxGridConfiguration[] = { "Use as Default", "Added Filters",
				"Make available to all users of this site" };
		test.maps_sessionpage.verifyCheckBoxOnGridConfigPopup(chkBoxGridConfiguration);
		test.maps_sessionpage.verifyRoleOptionsOnClickingChkbox("Roles","Make available to all users of this site");
	}

	//@Test(groups = { "P2" }) //in progress
	public void  Step_10_MAPS_Review_Admin_06_verify_the_functionality_of_Edit_Instructions_link() {
//		test.maps_reviewpage.clickOnlinkOnGridCongirationPopup("[EDIT INSTRUCTIONS]", "Save Grid Configuration");
//		test.maps_sessionpage.verifyPopupMessage("Instructions");
//		test.maps_sessionpage.clickOnButtonByIndexing("Close", "2");
		test.maps_sessionpage.clickOnButtonByIndexing("Close", "1");
	}

	//@Test (groups = { "P1" }) //passed
	public void Step_11_MAPS_Review_Admin_07_and_08_Verify_application_allows_user_to_add_new_view_in_Grid_Configuration_dropdown() {
		griduniqueName=YamlReader.getYamlValue("Review.GridConfigName") + System.currentTimeMillis();
		test.maps_reviewpage.clickOnButton("Save/Edit");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Save Grid Configuration");
		test.maps_reviewpage.enterDetailsAtSaveGridConfigurationPage(griduniqueName);
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Save");
		test.maps_sessionpage.clickOnDropDownImage(1);
		test.maps_sessionpage.selectRoleOnSaveGridConfiguration(griduniqueName);
	}
	
	//@Test(groups = { "P2" }) //passed
	public void Step_15_MAPS_Review_Admin_10_Verify_application_allows_user_to_edit_the_added_Grid_Configuration(){
		String editedGrid = "Edited_grid" + griduniqueName;
		test.maps_sessionpage.clickOnDropDownImage(1);
		test.maps_sessionpage.selectRoleOnSaveGridConfiguration(griduniqueName);
		test.maps_reviewpage.clickOnButton("Save/Edit");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Save Grid Configuration");
		test.maps_reviewpage.enterDetailsAtSaveGridConfigurationPage(editedGrid);
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Save");
		test.maps_sessionpage.clickOnDropDownImage(1);
		test.maps_sessionpage.selectRoleOnSaveGridConfiguration(editedGrid);
	}
	
	//@Test(groups = { "P2" }) //passed
	public void Step_16_MAPS_Review_Admin_11_Verify_application_allows_user_to_add_multiple_grid_configurations(){
		multigridname = YamlReader.getYamlValue("Review.GridConfigName") + System.currentTimeMillis();
		test.maps_sessionpage.clickOnDropDownImage(1);
		test.maps_sessionpage.selectRoleOnSaveGridConfiguration("Select One...");
		test.maps_reviewpage.clickOnButton("Save/Edit");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Save Grid Configuration");
		test.maps_reviewpage.enterDetailsAtSaveGridConfigurationPage(multigridname);
		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Save");
		test.maps_sessionpage.clickOnDropDownImage(1);
		test.maps_sessionpage.selectRoleOnSaveGridConfiguration(multigridname);
	}
	
	//@Test(groups = { "P2" }) //passed
	public void Step_17_MAPS_Review_Admin_12_Verif_application_display_confirmation_popup_on_clicking_delete_link_for_an_existing_grid_configuration(){
		multigridname="Edited_gridTest_gridconfig1498478638904";
		test.maps_sessionpage.clickOnDropDownImage(1);
		test.maps_sessionpage.selectRoleOnSaveGridConfiguration(multigridname);
		test.maps_reviewpage.clickOnButton("Delete");
		test.maps_sessionpage.verifyPopupMessage(YamlReader.getYamlValue("Review.delete_msg_gridConfig"));
	}
	
	//@Test(groups = { "P2" }) //passed
	public void Step_18_MAPS_Review_Admin_13_Verify_applicatio_deletes_grid_configuration_on_clicking_Yes_button_on_confirmation_popup(){
		test.maps_sessionpage.clickOnButtonByIndexing("Yes", "1");
		test.maps_sessionpage.clickOnDropDownImage(1);
		test.maps_sessionpage.verifyIsDataDeleted(multigridname);
		test.maps_sessionpage.clickOnDropDownImage(1);
	}
	

	//@Test (groups = { "P1" }) //passed
	public void Step_22_MAPS_Review_Admin_17_Verify_application_displays_all_results_when_search_term_is_provided_in_the_Filter_field() {
		String cID = test.maps_reviewpage.getValueFromReviewerScoreReportTable();
		test.maps_reviewpage.enterValueInFilter(cID);
		test.maps_reviewpage.verifyTheResult(cID);
	}
	
//	@Test(groups = { "P2" }) // in progress
//	public void Step_24_MAPS_Review_Admin_19_Verify_options_available_under_Found_In_dropdown() {
//		test.maps_sessionpage.clickOnDropdownOnReviewScoreReport("Found In");
//		test.maps_sessionpage.verifyColumnHeaders();
//	}

	@Test(groups = { "P2" }) // in progress
	public void Step_26_MAPS_Review_Admin_21_Verify_application_displays_all_related_results_when_search_term_is_provided_Filter_and_any_option_is_selected_from_Found_In_dropdown() {
		String desicion=YamlReader.getYamlValue("Review.search_decision");
		test.maps_sessionpage.inputTextInFilter("Search",desicion, "1");
		test.maps_sessionpage.clickOnDropdownOnReviewScoreReport("Found In");
		test.maps_reviewpage.selectvalueFromDropdown("Decision");
	}


//	@Test //passed
//	public void MAPS_Review_Admin_42_Verify_that_application_uploads_file_when_user_clicks_on_Import_button() {
//		String absract_id = create_Abstract_As_Prerequisite();
//		MAPS_Review_Admin_01_Verify_that_application_navigates_to_ReviewerScoreReport_page();
//		test.maps_reviewpage.clickOnButtonAtSaveGridConfigurationPage("Import/Export to Excel");
//		test.maps_reviewpage.clickOnButton("Import Decision");
//		test.maps_sessionpage.verifyPopupMessage("Import Decisions");
//		test.maps_sessionpage.clickOnDownloadButtonAndVerifyValidFileIsDownloaded("Download template",
//				YamlReader.getYamlValue("Review.downloaded_templateFile"), downloadedFilePath);
//		dataForImportedFile.add(absract_id);
//		dataForImportedFile.add(YamlReader.getYamlValue("Review.decision"));
//		System.out.println("absract_id   "+absract_id+"\n "+YamlReader.getYamlValue("Review.decision"));
//		
//		test.maps_sessionpage.importValidFile(dataForImportedFile, downloadedFilePath + File.separator
//				+ YamlReader.getYamlValue("Review.downloaded_templateFile") + ".csv");
//		test.maps_sessionpage.clickOnButtonByIndexing("Import", "1");
//		test.maps_sessionpage.verifyPopUpText("Successful import");
//		test.maps_sessionpage.verifyPopUpText("Failed: 0");
//		test.maps_sessionpage.clickOnButtonByIndexing("Ok", "1");
//
//	}
//
//	public String create_Abstract_As_Prerequisite() {
//		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Submission");
//		test.maps_SSOPage.verifyUserIsOnTabPage("Submission");
//		test.maps_submissionPage.clickOnNamedButton("Create New Submission");
//		programName=DataProvider
//				.getRandomSpecificLineFromTextFile("MapsProgramArea").trim();
//		test.maps_submissionPage.selectRansdomActiveSubmissionProgram(programName);
//		test.maps_submissionPage.clickOnContinueButtonOnProgramArea();
//
//		test.maps_submissionPage.clickOnPopUpContinueButtonOnSelectingProgramArea("Continue With This Type");
//		test.maps_submissionPage.submitTitleAndBodyDetails(abstract_details, abstract_details);
//		test.maps_submissionPage.clickOnSaveAndContinueButton();
//		
//		test.maps_submissionPage.submitDetailsOnSelectSymposiumPage(
//				YamlReader.getYamlValue("Submission_Symposium_Step.presentation_type"),
//				YamlReader.getYamlValue("Submission_Symposium_Step.scimix_value"));
//		test.maps_submissionPage.clickOnSaveAndContinueButton();
//		test.maps_submissionPage.verifyPageHeaderForASection("Authors");
//		test.maps_submissionPage.clickOnSaveAndContinueButton();
//
//		test.maps_submissionPage.verifyPageHeaderForASection("Disclosures");
//		test.maps_submissionPage.clickOnLinkUnderCreateNewSubmission("Disclosures");
//		test.maps_submissionPage.verifyPageHeaderForASection("Disclosures");
//		test.maps_submissionPage.enterDetailsInDisclosuresSection();
//		test.maps_submissionPage.clickOnSaveAndContinueButton();
//		test.maps_submissionPage.verifyPageHeaderForASection("Review & Submit");
//		test.maps_submissionPage.verifyAllStepsAreCompleteOnReviewAndSubmitPage(5);
//		test.maps_submissionPage.clickOnSubmitButton();
//
//		test.maps_submissionPage.verifySuccessAlertMessage(YamlReader.getYamlValue("Success_alert_msg"));
//		absract_id = test.maps_submissionPage.getIDofAbstract("subs", abstract_details, "1", "ID");
//		return absract_id;
//	}
//
//	@Test //passed
//	public void MAPS_Review_Admin_83_Verify_Application_displays_results_as_per_searched_term_entered_in_filter_text_field_of_the_column_header_dropdown() {
//		test.maps_reviewpage.clickOnButton("Reviewer Score Report");
//		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Reviewer Score Report");
//		test.maps_sessionpage.clickOnArrowButton("Presenter");
//		test.maps_sessionpage.enterFilterText("Filters", "Hitasheet");
//		test.maps_sessionpage.verifyFilterResults("Sil, Hitasheet", 1, 5);
//	}
//
//	 @Test //passed
//	public void MAPS_Review_Admin_91_Verify_options_available_under_Records_per_Page_dropdown() {
//		String pageSize[] = { "10", "25", "50" };
//		test.maps_reviewpage.verifyOptionsffromRecordsPerPageDropdown(pageSize);
//	}
}
