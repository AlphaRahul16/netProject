package com.qait.MAPS.tests;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class Maps_Submission_Test extends BaseTest {
	private String maps_url;
	private String programName;
	private int randProgramindex;

	@BeforeClass
	public void Start_Test_Session() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		maps_url = YamlReader.getYamlValue("MAPS_Url");
	}

	@BeforeMethod
	public void printCaseIdExecuted(Method method) {
		test.printMethodName(method.getName());
	}

	@Test
	public void Test01_MAPS_1_Launch_Application() {
		test.launchMAPSApplication(maps_url);
		test.maps_SSOPage.verifyUserIsOnMAPSHomePage("Meeting Abstracts Programming System");
	}

	@Test
	public void Test02_MAPS_3_LogIn_With_Valid_Credentials() {
		test.maps_SSOPage.loginWithValidCredentials(YamlReader.getYamlValue("LogIn_Details.userID"),
				YamlReader.getYamlValue("LogIn_Details.password"));
	}

	@Test
	public void Test03_MAPS_6_Verify_User_Is_Navigated_To_Welcome_Page() {
		test.maps_SSOPage.verifyUserIsOnTabPage("Welcome");
	}

	@Test
	public void Test04_MAPS_15_Verify_User_Is_Navigated_To_General_And_Contact_Information_Page() {
		test.maps_SSOPage.clickOnUserProfileName();
		test.maps_SSOPage.clickOnUserInformationLink("General Information");
		test.maps_SSOPage.verifyPageHeader("General Information");
		 test.maps_SSOPage.clickOnLeftMenuInformationLink("Contact Information");
		 test.maps_SSOPage.verifyPageHeader("Contact Information");
	}

	@Test
	public void Test05_MAPS_30_Click_On_Submission_In_Top_Navigation_Menu() {
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Submission");
		test.maps_SSOPage.verifyUserIsOnTabPage("Submission");
		test.maps_submissionPage.verifyPageHeaderForASection("View Submissions");
	}

	@Test
	public void Test06_MAPS_32_Click_On_Create_New_Submission_Page_Link_And_Verify_That_User_Is_Navigated_To_Program_Area_Page() {
		test.maps_submissionPage.clickOnNamedButton("Create New Submission");
		test.maps_submissionPage.verifyPageHeaderForASection("Program Area");
	}

	@Test
	public void Test07_MAPS_42_Select_A_Active_Program_Area_And_Click_Continue_Button_User_Navigated_To_Title_Body_Page() {
		randProgramindex=test.maps_submissionPage.selectRandomActiveSubmissionProgram();
		programName = test.maps_submissionPage.getSelectedProgramName(randProgramindex);	
		test.maps_submissionPage.clickOnContinueButtonOnProgramArea();
		test.maps_submissionPage.clickOnPopUpContinueButtonOnSelectingProgramArea("Continue With This Type");
		test.maps_submissionPage.verifyPageHeaderForASection("Title/Body");
	}

	@Test
	public void Test08_MAPS_94_Submit_Details_On_Title_Body_Page() {
		test.maps_submissionPage.submitTitleAndBodyDetails("Test Title", "Test Abstract");
		test.maps_submissionPage.uploadImage("test.jpeg");
		test.maps_submissionPage.clickOnSaveAndContinueButton();
		test.maps_submissionPage.verifyPageHeaderForASection("Select Symposium");
	}

	@Test // for drafts saving
	public void Test09_MAPS_105_Verify_Abstract_Is_Submitted_As_Drafts() {
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Welcome");
		test.maps_submissionPage.clickOnDraftStatusActionButton("Yes, Leave this as draft");
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Submission");
		test.maps_submissionPage.verifyNamedSectionIsDisplayed("drafts");
		test.maps_submissionPage.verifyApplicationDisplaysSpecifiedAbstractUnderSpecifiedSections("drafts", "Draft");
		test.maps_submissionPage.verifyAvailableOptionsForDraftedProgram(
				YamlReader.getYamlValue("Submission_draft_Options"), programName,"drafts");
		test.maps_submissionPage.selectPreDraftedAbstractForEditing("drafts", programName, "Edit Draft");
		test.maps_submissionPage.verifyPageHeaderForASection("Title/Body");
		test.maps_submissionPage.clickOnSaveAndContinueButton();
		test.maps_submissionPage.verifyPageHeaderForASection("Select Symposium");
	}

	@Test
	public void Test10_MAPS_106_Submit_Details_Select_Symposium_Page() {
		test.maps_submissionPage.submitDetailsOnSelectSymposiumPage(
				YamlReader.getYamlValue("Submission_Symposium_Step.presentation_type"),
				YamlReader.getYamlValue("Submission_Symposium_Step.scimix_value"));
		test.maps_submissionPage.clickOnSaveAndContinueButton();
		test.maps_submissionPage.verifyPageHeaderForASection("Authors");
	}

	@Test
	public void Test11_MAPS_118_Verify_Application_Creates_New_institution_And_Navigates_Back_To_Author_Page() {
		test.maps_submissionPage.clickShowAffiliationsButton();
		test.maps_submissionPage.selectAMandatoryAffiliation("AUTHOR_INSTITUTION", "Create New Institution");
		test.maps_submissionPage.createNewInstitution(YamlReader.getYamlValue("Submission_Author_Step.institution"),
				YamlReader.getYamlValue("Submission_Author_Step.department"),
				YamlReader.getYamlValue("Submission_Author_Step.city"),
				YamlReader.getYamlValue("Submission_Author_Step.state"),
				YamlReader.getYamlValue("Submission_Author_Step.country"));
		test.maps_submissionPage.verifyPageHeaderForASection("Authors");
	}

	@Test
	public void Test12_MAPS_119_Verify_Application_Auto_Selects_Newly_Created_Institution_For_Particular_Affiliation() {
		test.maps_submissionPage.verifyCreatedInstitutionIsSelected("AUTHOR_INSTITUTION",
				YamlReader.getYamlValue("Submission_Author_Step.institution"));
	}

	@Test
	public void Test13_MAPS_145_Verify_That_Application_Displays_Matching_Results_On_Providing_Valid_Search_Criteria() {
		test.maps_submissionPage.clickAddAuthorButton();
		test.maps_submissionPage.searchAuthorByEnteringDetails(
				YamlReader.getYamlValue("Submission_Author_Step.author_search_criteria"),
				YamlReader.getYamlValue("Submission_Author_Step.author_search_value"));
		test.maps_submissionPage.verifyValidSearchResultsAreDisplayed(
				YamlReader.getYamlValue("Submission_Author_Step.author_search_value"));
	}

	@Test
	public void Test14_MAPS_165_Verify_That_Application_Should_Navigate_To_Disclosures_Page_On_Clicking_Save_And_Continue() {
		test.maps_submissionPage.clickOnSaveAndContinueButton();
		test.maps_submissionPage.verifyPageHeaderForASection("Disclosures");
	}

	@Test
	public void Test15_MAPS_199_Verify_Step_Incomplete_Window_Appears_On_Clicking_Save_And_Continue_Button_And_User_Navigates_To_Review_And_Submit_Page() {
		test.maps_submissionPage.clickOnSaveAndContinueButton();
		test.maps_submissionPage.verifyPopUpHeader("Step Incomplete");
		test.maps_submissionPage.clickOnSaveAndContinueButtonInFooter("Save & Continue");
		test.maps_submissionPage.verifyPageHeaderForASection("Review & Submit");
	}

	@Test
	public void Test16_MAPS_239_Fill_Mandatory_Details_On_Disclosure_Page_And_Click_On_Submit_Button() {

		test.maps_submissionPage.clickOnLinkUnderCreateNewSubmission("Disclosures");
		test.maps_submissionPage.verifyPageHeaderForASection("Disclosures");
		test.maps_submissionPage.enterDetailsInDisclosuresSection();
		test.maps_submissionPage.clickOnSaveAndContinueButton();
		test.maps_submissionPage.verifyPageHeaderForASection("Review & Submit");
		test.maps_submissionPage.verifyAllStepsAreCompleteOnReviewAndSubmitPage(5);
		test.maps_submissionPage.clickOnSubmitButton();

	}

	@Test
	public void Test17_MAPS_223_Verify_Application_Displays_Abstracts_Drafts_Under_Draft_Section() {
		test.maps_submissionPage.verifySuccessAlertMessage(YamlReader.getYamlValue("Success_alert_msg"));
		// test.maps_submissionPage.verifyNamedSectionIsDisplayed("drafts");
		// test.maps_submissionPage.verifyApplicationDisplaysSpecifiedAbstractUnderSpecifiedSections("drafts",
		// "Draft");
	}

	@Test
	public void Test18_MAPS_229_Verify_Aplication_Displays_Submitted_Abstracts_Under_Submissions_Section() {
		test.maps_submissionPage.verifyNamedSectionIsDisplayed("submissions");
	}

	// @Test
	// public void
	// Test18_MAPS_224_Verify_All_Options_Available_Under_Action_Dropdown_Drafts()
	// {
	// test.maps_submissionPage
	// .verifyAvailableOptionsForDraftedProgram(YamlReader.getYamlValue("Submission_draft_Options"),programName);
	// }

	@Test
	public void Test19_MAPS_230_Verify_Status_Of_Submitted_Abstract_Changed_To_UnderReview() {
		test.maps_submissionPage.verifyStatusOfSubmittedAbstract("subs",programName,"Under Review");
	}

	@Test
	public void Test20_MAPS_232_Verify_Application_Displays_Move_To_Draft_Status_Popup_On_Clicking_Edit_Option_For_Submitted_Abstracts() {
//		test.maps_submissionPage.selectEditActionForSubmittedAbstracts(programName, "Edit");
		test.maps_submissionPage.selectPreDraftedAbstractForEditing("subs", programName, "Edit");
		test.maps_submissionPage.verifyPopUpHeaderOnSubmissionPage("Move to Draft status?");
		test.maps_submissionPage.clickOnDraftStatusActionButton("No, Do Not Move");
	}

	@Test
	public void Test21_MAPS_26_Application_LogsOut_On_Clicking_LogOut_Link() {
		test.maps_submissionPage.logOutFromMAPSApplication();
	}

}
