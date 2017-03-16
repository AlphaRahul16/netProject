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
		test.maps_SSOPage.verifyUserIsOnWelcomePage("Welcome");	
	}
	
	@Test
	public void Test04_MAPS_94_Submit_Details_On_Title_Body_Page() {//verification to be added in the end
		test.maps_submissionPage.submitTitleAndBodyDetails("Test Title","Test Abstract");
		test.maps_submissionPage.uploadImage("test");
		test.maps_submissionPage.clickOnSaveAndContinueButton();
	}
	
	@Test
	public void Test05_MAPS_106_Submit_Details_Select_Symposium_Page() {
		test.maps_submissionPage.submitDetailsOnSelectSymposiumPage("1. Oral Only","Coffee & Cocoa Products","1. Consider for Sci-Mix");
		test.maps_submissionPage.clickOnSaveAndContinueButton();
	}

}
