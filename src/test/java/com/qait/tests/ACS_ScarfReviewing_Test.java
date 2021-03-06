package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;

public class ACS_ScarfReviewing_Test {
	static String sheetName;
	String app_url_eweb_rev;
	TestSessionInitiator test;

	Map<String, List<String>> ReviewerLoginMap = new HashMap<String, List<String>>();
	Map<String, Map<String, String>> reviewerComments;
	String app_url_iweb, app_url_eweb;
	String assignedchaptername; // =ACS_Scarf_Reporting.chapterName;
	int index, i = 0;
	int reviewAnswersCount = 1;
	String[] customerSortNames;
	private int caseID;

	@BeforeClass
	public void open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_iweb = getYamlValue("app_url_IWEB");
		app_url_eweb = getYamlValue("app_url_ScarfReviewing");
	}

	@BeforeMethod
	public void handleTestMethodName(Method method) {
		test.printMethodName(method.getName());
	}
	@Test
	public void Step01_Launch_Iweb_Application() {
		test.launchApplication(app_url_iweb);
		test.homePageIWEB.enterAuthentication(
				YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
//		test.acsScarfReviewPage.assignChapterName("Seattle Pacific University Student Chapter");
	}

	@Test
	public void Step02_User_Navigated_To_Student_Chapter_Reporting_Link() {

		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnSacrfReportingModule();
		test.homePageIWEB
				.verifyUserIsOnHomePage("Scarf Reporting | Overview | Student Chapter Reporting Setup");
		test.acsScarfReporting.clickOnStudentChapterReportingLink();
		test.memberShipPage.expandDetailsMenu("academic year");
	}

	@Test
	public void Step03_User_Reviewing_Date_Is_Open_In_Current_Year_For_All_Reviewing_Modes() {
		test.memberShipPage.verifyReportingStartAndEndDate();
		test.memberShipPage.clickCurrentYearPencilButton();
		test.memberShipPage.verifyStartAndEndDatesForAllModesOfReview();
	}

	@Test
	public void Step04_Select_Assign_Reviewer_Option_And_Choose_Two_Online_Reviewers() {
		test.homePageIWEB.clickOnLeftMenuTab("Reviewers");
		test.homePageIWEB.clickOnTab("Assign Reviewer");
		assignedchaptername = test.acsScarfReviewPage.assignReviewerToAChapter(
				"Online Reviewer", 0);
		test.acsScarfReviewPage.assignReviewerToAChapter("Online Reviewer", 1);
		test.acsScarfReviewPage.assignReviewerToAChapter(
				"Faculty Decision Panel Reviewer", 0);
		test.acsScarfReviewPage.assignReviewerToAChapter(
				"Green Chemistry Reviewer", 0);
		customerSortNames = test.acsScarfReviewPage
				.getCustomerSortName(test.acsScarfReviewPage
						.getReviewerNameList());
		assignedchaptername = assignedchaptername + " Student Chapter";
	}

	@Test(dataProvider = "loginDetails")
	public void Step05_Execute_Scarf_Reviewer_Query_And_Fetch_Login_Details(
			int reviewerCount) {
		test.acsScarfReviewPage.clickOnQueryTabForScarfModule("Query");
		test.memberShipPage.selectAndRunQuery(YamlReader
				.getYamlValue("ScarfReviewer.queryName"));
		test.memberShipPage
				.enterSingleCustomerIdInRunQuery(customerSortNames[reviewerCount]);
		test.individualsPage
				.NavigateToIndividualProfilePageFromScarfReviewList(test.acsScarfReviewPage
						.getReviewerNameList().get(reviewerCount).trim());
		test.memberShipPage.fetchScarfReviewerLoginDetails(ReviewerLoginMap,
				reviewerCount);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnSacrfReportingModule();
		test.homePageIWEB.clickOnLeftMenuTab("Reviewers");
		test.acsScarfReviewPage.clickOnQueryTabForScarfModule("Query");
	}

	@Test
	public void Step06_Launch_Eweb_Application_And_Enter_Reviews_By_First_Online_Reviewer() {
		test.launchApplication(app_url_eweb);
		// test.acsScarfReporting.loginWithLastNameAndMemberId("Abrams","2346003");
		// //"Easter", "2175095"
		Reporter.log("---------value of i:"+i,true);
		test.acsScarfReporting.loginWithLastNameAndMemberId(ReviewerLoginMap
				.get("reviewer" + i).get(0),
				ReviewerLoginMap.get("reviewer" + i).get(1)); 
		test.acsScarfReviewing.verifyReviewerTypeWindow("Online Reviewer");
		test.acsScarfReporting.verifyStudentChapterReportingPage();
		index = test.acsScarfReviewing
				.verifyChapterOnTheReviewPageAndClickOnreviewButton(
						assignedchaptername, "list_ChapterList", "LayoutCell");
		test.acsScarfReviewing.verifyChapterStatus("Not Started", index);
		test.acsScarfReviewing.selectChapterReviewImage(index);
		test.acsScarfReporting.clickOnNotStartedButtonForSection(
				"Self-Assessment", "Start");
		test.acsScarfReviewing.initializeMap();
		i++;
	}

	@Test(dataProvider = "Sections")
	public void Step07_Enter_Reviews_For_All_The_Sections(String sectionName) {
		test.acsScarfReviewing.enterRating(
				YamlReader.getYamlValue("ScarfReviewer.reviewRating"),
				sectionName);
		test.acsScarfReviewing.enterCommentsForSections(sectionName,
				YamlReader.getYamlValue("ScarfReviewer.reviewComments"));
		test.acsScarfReviewing.clickOnNextButton();
	}

	@Test
	public void Step08_Submit_Reviews_And_Verify_Review_Status() {
		test.acsScarfReviewing.addReviewerComments("Reviewer" + i);
		test.acsScarfReviewing.enterOverallRating(YamlReader
				.getYamlValue("ScarfReviewer.overallRating"));
		test.acsScarfReviewing.clickOnSubmitButton("Submit");
		test.acsScarfReviewing.enterOverallReview(YamlReader
				.getYamlValue("ScarfReviewer.overallReviewComment"));
		test.acsScarfReviewing.clickOnSubmitButton("Submit");
		test.acsScarfReviewing.clickOnReturnToDashboardButton();
		test.acsScarfReviewing.verifyChapterStatus("Submitted", index);
	}

	@Test
	public void Step09_Launch_Eweb_Application_And_Enter_Reviews_By_Second_Online_Reviewer() {
		Step06_Launch_Eweb_Application_And_Enter_Reviews_By_First_Online_Reviewer();
	}

	@Test(dataProvider = "Sections")
	public void Step10_Enter_Reviews_For_All_Sections(String sectionName) {
		Step07_Enter_Reviews_For_All_The_Sections(sectionName);
	}

	@Test
	public void Step11_Submit_Reviews_And_Verify_Review_Status_By_Second_Online_Reviewer() {
		Step08_Submit_Reviews_And_Verify_Review_Status();
	}

	@Test
	public void Step12_Launch_Eweb_Application_And_Enter_Reviews_By_FDP_Reviewer() {
		test.acsScarfReviewing.initializeMap();
		test.launchApplication(app_url_eweb);
		// test.acsScarfReporting.loginWithLastNameAndMemberId("Hare","2250525");//"Keirstead","30037352");
		// // "Keirstead","30037352"
		test.acsScarfReporting.loginWithLastNameAndMemberId(ReviewerLoginMap
				.get("reviewer" + i).get(0),
				ReviewerLoginMap.get("reviewer" + i).get(1)); // "Hare","2250525"
		test.acsScarfReviewing
				.verifyReviewerTypeWindow("Faculty Decision Panel Reviewer");
		index = test.acsScarfReviewing
				.verifyChapterOnTheReviewPageAndClickOnreviewButton(
						assignedchaptername, "list_notStartedChapters",
						"notStarted");// notStarted Belmont University Student
										// Chapter .....Arcadia University
										// Student Chapter"
		test.acsScarfReviewing.selectChapterReviewImage(index);
		test.acsScarfReporting.clickOnNotStartedButtonForSection(
				"Self-Assessment", "Start");
		i++;
	}

	@Test(dataProvider = "Sections")
	public void Step13_Enter_Reviewes_For_Fdp_Reviewer_Sections(
			String sectionName) {
		test.acsScarfReviewing.enterCommentsForSectionsByFdpReviewer(
				sectionName, 1);
		test.acsScarfReviewing.clickOnNextButton();
	}

	@Test
	public void Step14_Submit_Reviews_And_Verify_Review_Status_By_FDP_Reviewer() {
		test.acsScarfReviewing.addReviewerComments("Reviewer" + i);
		test.acsScarfReviewing.enterOverallRating(YamlReader
				.getYamlValue("ScarfReviewer.overallRating"));
		test.acsScarfReviewing.clickOnSubmitButton("Submit");
		test.acsScarfReviewing.enterOverallReview(YamlReader
				.getYamlValue("ScarfReviewer.overallReviewComment"));
		test.acsScarfReviewing.clickOnSubmitButton("Submit");
		test.acsScarfReviewing.clickOnReturnToDashboardButton();
		test.acsScarfReviewing.clickOnSubmittedChaptersTab("submitted");
		test.acsScarfReviewing.verifySubmittedChapterOnTheReviewPage(
				assignedchaptername, "list_notStartedChapters", "submitted");
	}

	@Test
	public void Step15_Launch_Eweb_Application_And_Enter_Reviews_By_Green_Chemistry_Reviewer() {
		test.acsScarfReviewing.initializeMap();
		test.launchApplication(app_url_eweb);
		test.acsScarfReporting.loginWithLastNameAndMemberId(ReviewerLoginMap
				.get("reviewer" + i).get(0),
				ReviewerLoginMap.get("reviewer" + i).get(1)); // "Constable","00816994"
		i++;
		test.acsScarfReporting.verifyStudentChapterReportingPage();
		index = test.acsScarfReviewing.verifyChapterOnReviewPageForGCReviewer(
				assignedchaptername, "list_ChapterList", "LayoutCell");
		test.acsScarfReviewing.verifyChapterStatus("Not Started", index);
		test.acsScarfReviewing.selectChapterReviewImage(index);
		test.acsScarfReviewing.enterRatingByGreenChemistryReviewer("Yes");
		test.acsScarfReviewing.enterComments(YamlReader
				.getYamlValue("ScarfReviewer.reviewComments"));
		test.acsScarfReviewing.addReviewerComments("Reviewer" + i);
		test.acsScarfReviewing.clickOnSubmitButton("Save & Submit");
		test.acsScarfReviewing.verifyChapterStatus("Submitted", index);
		test.acsScarfReviewing.verifyFinalReview("Yes", index);
		i++;
	}

	@Test
	public void Step16_Verify_Chapter_Review_Status_On_Iweb() {
		Step01_Launch_Iweb_Application();
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnSacrfReportingModule();
		test.homePageIWEB.clickOnLeftMenuTab("Report");
		test.homePageIWEB.clickOnTab("Find");
		test.individualsPage.enterChapterNameAndStatusAndClickGoButton(
				assignedchaptername, "Submitted");
		test.individualsPage
				.navigateToGeneralMenuOnHoveringMore("Report Review");
		test.invoicePage.verifyMemberDetails("status", "Submitted");

	}

	@Test
	public void Step17_Verify_Online_Reviewers_Comments_And_Review_Status_On_Iweb() {
		reviewerComments = test.acsScarfReviewing.getReviewerCommentsMap();
		test.invoicePage.expandDetailsMenu("report online reviewer");
		test.invoicePage.verifyScarfReviewerCommentsAndStatus(
				test.acsScarfReviewPage.getReviewerNameList().get(0),
				YamlReader.getYamlValue("ScarfReviewer.overallRating"),
				"Submitted");
		test.invoicePage.verifyScarfReviewerCommentsAndStatus(
				test.acsScarfReviewPage.getReviewerNameList().get(1),
				YamlReader.getYamlValue("ScarfReviewer.overallRating"),
				"Submitted");
		test.invoicePage.collapseDetailsMenu("report online reviewer");
		test.invoicePage.expandDetailsMenu("report online reviewer answer");
	}

	@Test(dataProvider = "ReviewerSections")
	public void Step18_Verify_Online_Reviewers_Answers_On_Iweb(
			String sectionName) {
		test.acsScarfReviewing.verifyReviewerAnswers(reviewerComments, 1,
				sectionName,
				test.acsScarfReviewPage.getReviewerNameList().get(0),"report online reviewer answer");
		test.acsScarfReviewing.verifyReviewerAnswers(reviewerComments, 2,
				sectionName,
				test.acsScarfReviewPage.getReviewerNameList().get(1),"report online reviewer answer");

	}

	@Test
	public void Step19_Verify_Faculty_Panel_Reviewer_Comments_And_Review_Status_On_Iweb() {
		test.invoicePage.collapseDetailsMenu("report online reviewer answer");
		test.invoicePage
				.expandDetailsMenu("report faculty decision panel reviewer");
		test.invoicePage.verifyScarfReviewerCommentsAndStatus(
				test.acsScarfReviewPage.getReviewerNameList().get(2),
				YamlReader.getYamlValue("ScarfReviewer.overallRating"),
				"Submitted");
		test.invoicePage
				.collapseDetailsMenu("report faculty decision panel reviewer");
		test.invoicePage
				.expandDetailsMenu("report faculty decision panel answer");
	}

	@Test(dataProvider = "ReviewerSections")
	public void Step20_Verify_Faculty_Panel_Reviewer_Answers_On_Iweb(
			String sectionName) {
		test.acsScarfReviewing.verifyReviewerAnswers(reviewerComments, 3,
				sectionName,
				test.acsScarfReviewPage.getReviewerNameList().get(2),"report faculty decision panel answer");
	}

	@Test
	public void Step21_Verify_Green_Chemistry_Reviewer_Comments_And_Review_Status_On_Iweb() {

		test.invoicePage
				.collapseDetailsMenu("report faculty decision panel answer");
		test.invoicePage.expandDetailsMenu("report green chemistry reviewer");
		test.invoicePage.verifyScarfReviewerCommentsAndStatus(
				test.acsScarfReviewPage.getReviewerNameList().get(3),
				YamlReader.getYamlValue("ScarfReviewer.overallGCRating"),
				"Submitted");
		test.invoicePage.collapseDetailsMenu("report green chemistry reviewer");
		test.invoicePage
				.expandDetailsMenu("report green chemistry reviewer answer");
	}

	@Test
	public void Step22_Verify_Green_Chemistry_Reviewer_Answers_On_Iweb() {
		test.acsScarfReviewing.verifyReviewerAnswers(reviewerComments, 4,
				"Green Chemistry", test.acsScarfReviewPage
						.getReviewerNameList().get(3),"report green chemistry reviewer answer");
	}

	@DataProvider(name = "loginDetails")
	public static Object[][] Reviewer_Details() {

		int[] reviewerCount = { 0, 1, 2, 3 };
		return new Object[][] { { reviewerCount[0] }, { reviewerCount[1] },
				{ reviewerCount[2] }, { reviewerCount[3] } };
	}

	@DataProvider(name = "Sections")
	public static Object[][] provideSectionNames() {
		return new Object[][] { { "Self-Assessment" }, { "Service" },
				{ "Professional Development" }, { "Chapter Development" },
				{ "Budget" }, { "Online Reviewer Assessment" } };
	}

	@DataProvider(name = "ReviewerSections")
	public static Object[][] provideReviewerSectionNames() {
		return new Object[][] { { "Self-Assessment" }, { "Service" },
				{ "Professional Development" }, { "Chapter Development" },
				{ "Budget" }, { "Online Reviewer Assessment" } };
	}

	@AfterClass
	public void close_Browser_Window() {
		test.closeBrowserWindow();
	}
	
	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
	}

}
