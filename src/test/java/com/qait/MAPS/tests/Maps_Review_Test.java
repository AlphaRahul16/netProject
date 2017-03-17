package com.qait.MAPS.tests;

import java.lang.reflect.Method;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
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

	@BeforeMethod
	public void printCaseIdExecuted(Method method) {
		test.printMethodName(method.getName());
	}

	@Test
	public void MAPS_Review_Admin_00_Launch_Application_And_Verify_Home_Page() {
		test.launchMAPSApplication(maps_url);
		test.maps_SSOPage.verifyUserIsOnMAPSHomePage("Meeting Abstracts Programming System");
		test.maps_SSOPage.loginWithValidCredentials(YamlReader.getYamlValue("LogIn_Details.userID"),
				YamlReader.getYamlValue("LogIn_Details.password"));
		test.maps_SSOPage.verifyUserIsOnTabPage("Welcome");
	}

	@Test
	public void MAPS_Review_Admin_01_Verify_that_application_navigates_to_ReviewerScoreReport_page() {
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Review");
		test.instanceOfReviewPage.verifyPageHeader("Multiple Role Selection");
		test.instanceOfReviewPage.selectRoleForReview("Review Admin");
		test.instanceOfReviewPage.clickOnButton("Select");
		test.instanceOfReviewPage.selectAbstractType("Reviewer Score Report");
		test.instanceOfReviewPage.verifyAbstractTitleUnderReviewModule("Reviewer Score Report");
	}

	@Test
	public void MAPS_Review_Admin_02_Verify_options_available_on_ReviewerScoreReport_page() {
		test.instanceOfReviewPage.verifyLinksUnderReviewModule("Save/Edit");
		test.instanceOfReviewPage.verifyLinksUnderReviewModule("Delete");
		test.instanceOfReviewPage.verifyLinksUnderReviewModule("Clear Filters");
		test.instanceOfReviewPage.verifyLinksUnderReviewModule("Hide Reviewer Comments");
		test.instanceOfReviewPage.verifyTextField("Filter");
		test.instanceOfReviewPage.verifyDropDown("Found In");
		//test.instanceOfReviewPage.verifyDropDownButtonType("Import/Export to Excel");
	}

}
