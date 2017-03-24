package com.qait.MAPS.tests;

import java.lang.reflect.Method;

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
		test.maps_ReviewPage.verifyPageHeader("Multiple Role Selection");
		test.maps_ReviewPage.selectRoleForReview("Review Admin");
		test.maps_ReviewPage.clickOnButton("Select");
		test.maps_ReviewPage.selectAbstractType("Reviewer Score Report");
		test.maps_ReviewPage.verifyAbstractTitleUnderReviewModule("Reviewer Score Report");
	}

	@Test
	public void MAPS_Review_Admin_02_Verify_options_available_on_ReviewerScoreReport_page() {
		test.maps_ReviewPage.verifyLinksUnderReviewModule("Save/Edit");
		test.maps_ReviewPage.verifyLinksUnderReviewModule("Delete");
		test.maps_ReviewPage.verifyLinksUnderReviewModule("Clear Filters");
		test.maps_ReviewPage.verifyLinksUnderReviewModule("Hide Reviewer Comments");
		test.maps_ReviewPage.verifyTextField("Filter");
		test.maps_ReviewPage.verifyDropDown("Found In");
		test.maps_ReviewPage.verifyDropDown("Import/Export to Excel");
		test.maps_ReviewPage.verifyRoleDropDown();
		test.maps_ReviewPage.verifyExpandIconUnderReviewModule();
		test.maps_ReviewPage.verifyButton("Mass Update");
		test.maps_ReviewPage.verifyReviewerScoreReportTable();
		test.maps_ReviewPage.verifyPaginationSectionAtTheBottomOfTheTable();
	}

	@Test
	public void MAPS_Review_Admin_07_Verify_application_allows_user_to_add_new_view_in_Grid_Configuration_dropdown() {
		test.maps_ReviewPage.clickOnButton("Save/Edit");
		test.maps_ReviewPage.verifyAbstractTitleUnderReviewModule("Save Grid Configuration");
		test.maps_ReviewPage.enterDetailsAtSaveGridConfigurationPage("Test" + System.currentTimeMillis());
		test.maps_ReviewPage.clickOnButtonAtSaveGridConfigurationPage("Save");
	}

	@Test
	public void MAPS_Review_Admin_17_Verify_application_displays_all_results_when_search_term_is_provided_in_the_Filter_field() {
		String cID = test.maps_ReviewPage.getValueFromReviewerScoreReportTable();
		test.maps_ReviewPage.enterValueInFilter(cID);
		test.maps_ReviewPage.verifyTheResult(cID);
	}

	@Test
	public void MAPS_Review_Admin_91_Verify_options_available_under_Records_per_Page_dropdown() {
		String pageSize[] = { "10", "25", "50" };
		test.maps_ReviewPage.verifyOptionsffromRecordsPerPageDropdown(pageSize);
	}
}
