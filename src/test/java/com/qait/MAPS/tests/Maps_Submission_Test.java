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
	public void Test04_MAPS_30_Click_On_Submission_In_Top_Navigation_Menu() {
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Submission");
		test.maps_SSOPage.verifyUserIsOnTabPage("Submission");	
		test.maps_submissionPage.verifyPageHeaderForASection("View Submissions");
	}
	
	@Test
	public void Test05_MAPS_32_Click_On_Create_New_Submission_Page_Lik_And_Verify_That_User_Is_Navigated_To_Program_Area_Page() {
		test.maps_submissionPage.clickOnCreateNewSubmissionLink("Create New Submission");	
		test.maps_submissionPage.verifyPageHeaderForASection("Program Area");
	}
	
	
	

}
