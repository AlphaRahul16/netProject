package com.qait.MAPS.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class Maps_Review_Test extends BaseTest {
	private String maps_url;

	@BeforeClass
	public void Start_Test_Session() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		maps_url = YamlReader.getYamlValue("MAPS_Url");
	}

	@Test
	public void MAPS_Review_00_Launch_Application_And_Verify_Home_Page() {
		test.launchMAPSApplication(maps_url);
		test.maps_SSOPage.verifyUserIsOnMAPSHomePage("Meeting Abstracts Programming System");
		test.maps_SSOPage.loginWithValidCredentials(YamlReader.getYamlValue("LogIn_Details.userID"),
				YamlReader.getYamlValue("LogIn_Details.password"));
		test.maps_SSOPage.verifyUserIsOnMAPSHomePage("Welcome");
	}

	@Test
	public void MAPS_Review_01_Click_On_Submission_In_Top_Navigation_Menu() {
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Review");
		test.instanceOfReviewPage.verifyPageHeader("Multiple Role Selection");
		test.instanceOfReviewPage.selectRoleForReview("Review Admin");
//		test.maps_SSOPage.verifyUserIsOnTabPage("Submission");
//		test.maps_submissionPage.verifyPageHeaderForASection("View Submissions");
	}

}
