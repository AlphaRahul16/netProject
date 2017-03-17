package com.qait.MAPS.tests;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class Maps_Submission_Test extends BaseTest {
	private String maps_url;
	
	@BeforeClass
	public void Start_Test_Session() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		maps_url = YamlReader.getYamlValue("MAPS_Url");
	}
	
	@Test
	public void Test01_MAPS_1_Launch_Application() {
		test.launchMAPSApplication(maps_url);
		test.maps_SSOPage.verifyUserIsOnMAPSHomePage("Meeting Abstracts Programming System");
	}
	
	@Test
	public void Test02_MAPS_3_LogIn_With_Valid_Credentials() {
        test.maps_SSOPage.loginWithValidCredentials(YamlReader.getYamlValue("LogIn_Details.userID"), YamlReader.getYamlValue("LogIn_Details.password"));
	}
	
	@Test
	public void Test03_MAPS_6_Verify_User_Is_Navigated_To_Welcome_Page() {
		test.maps_SSOPage.verifyUserIsOnTabPage("Welcome");	
	}
	
	@Test
	public void Test04_MAPS_15_Verify_User_Is_Navigated_To_General_And_Contact_Information_Page(){
		test.maps_SSOPage.clickOnUserProfileName();
		test.maps_SSOPage.clickOnUserInformationLink("General Information");
		test.maps_SSOPage.verifyPageHeader("General Information");
//		test.maps_SSOPage.clickOnLeftMenuInformationLink("Contact Information");
//		test.maps_SSOPage.verifyPageHeader("Contact Information");
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
		test.maps_submissionPage.selectRandomActiveSubmissionProgram();
		test.maps_submissionPage.clickOnContinueButtonOnProgramArea();
		test.maps_submissionPage.clickOnPopUpContinueButtonOnSelectingProgramArea("Continue With This Type");
		test.maps_submissionPage.verifyPageHeaderForASection("Title/Body");
	}
	
	@Test
	public void Test08_MAPS_94_Submit_Details_On_Title_Body_Page() {//verification to be added in the end
		test.maps_submissionPage.submitTitleAndBodyDetails("Test Title","Test Abstract");
		test.maps_submissionPage.uploadImage("test.jpeg");
		test.maps_submissionPage.clickOnSaveAndContinueButton();
		test.maps_submissionPage.verifyPageHeaderForASection("Select Symposium");
	}
	
	@Test
	public void Test09_MAPS_106_Submit_Details_Select_Symposium_Page() {
		test.maps_submissionPage.submitDetailsOnSelectSymposiumPage("1. Oral Only","Coffee & Cocoa Products","1. Consider for Sci-Mix");
		test.maps_submissionPage.clickOnSaveAndContinueButton();
		test.maps_submissionPage.verifyPageHeaderForASection("Authors");
		
	}
	
	@Test
	public void Test10_MAPS_118_Verify_Application_Creates_New_institution_And_Navigates_Back_To_Author_Page() {//verification to be added in the end
		test.maps_submissionPage.clickShowAffiliationsButton();
		test.maps_submissionPage.selectAMandatoryAffiliation("AUTHOR_INSTITUTION", "Create New Institution");
		test.maps_submissionPage.createNewInstitution(YamlReader.getYamlValue("Submission_Author_Step.institution"), YamlReader.getYamlValue("Submission_Author_Step.department"), 
				YamlReader.getYamlValue("Submission_Author_Step.city"), YamlReader.getYamlValue("Submission_Author_Step.state"), YamlReader.getYamlValue("Submission_Author_Step.country"));
		test.maps_submissionPage.verifyPageHeaderForASection("Authors");
	}
	
	@Test
	public void Test11_MAPS_199_Verify_Step_Incomplete_Window_Appears(){
		test.maps_submissionPage.clickOnSaveAndContinueButton();
		test.maps_submissionPage.verifyPopUpHeader("Step Incomplete");
		test.maps_submissionPage.clickOnSaveAndContinueButtonInFooter("Save & Continue");
		test.maps_submissionPage.verifyPageHeaderForASection("Review & Submit");
		test.maps_submissionPage.clickOnLinkUnderCreateNewSubmission("Disclosures");
		test.maps_submissionPage.verifyPageHeaderForASection("Disclosures");
		test.maps_submissionPage.enterDetailsInDisclosuresSection();
		test.maps_submissionPage.clickOnSaveAndContinueButton();
		test.maps_submissionPage.verifyPageHeaderForASection("Review & Submit");
		test.maps_submissionPage.verifyAllStepsAreCompleteOnReviewAndSubmitPage(5);
		test.maps_submissionPage.clickOnSubmitButton();
	}


}
