package com.qait.tests;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;
import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.XlsReader;
import com.qait.automation.utils.YamlReader;

public class ACS_ScarfReviewing_Test {
	static String sheetName;
	TestSessionInitiator test;
	
	Map<String,List<String>> ReviewerLoginMap = new HashMap<String, List<String>>();

	String app_url_iweb,app_url_eweb,assignedchaptername;
	int index,i=0;
    String[] customerSortNames;
	private int caseID;
	

	
	@BeforeClass
	public void open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_iweb = getYamlValue("app_url_IWEB");
		app_url_eweb = getYamlValue("app_url_ScarfReviewing");
	}
	
	@Test
	public void Step01_Launch_Iweb_Application() {
		test.launchApplication(app_url_iweb);
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}
	
	@Test
	public void Step02_User_Navigated_To_Student_Chapter_Reporting_Link() {
		Reporter.log("CASE ID : " + caseID, true);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnSacrfReportingModule();
		test.homePageIWEB.verifyUserIsOnHomePage("Scarf Reporting | Overview | Student Chapter Reporting Setup");
		test.acsScarfReporting.clickOnStudentChapterReportingLink();
		test.memberShipPage.expandDetailsMenu("academic year");
	}
	
	@Test
	public void Step03_User_Reviewing_Date_Is_Open_In_Current_Year_For_All_Reviewing_Modes()
	{
		test.memberShipPage.verifyReportingStartAndEndDate();
		test.memberShipPage.clickCurrentYearPencilButton();
		test.memberShipPage.verifyStartAndEndDatesForAllModesOfReview();
		
	}
	
	@Test
	public void Step04_Select_Assign_Reviewer_Option_And_Choose_Two_Online_Reviewers()
	{
		test.homePageIWEB.clickOnLeftMenuTab("Reviewers");
		test.homePageIWEB.clickOnTab("Assign Reviewer");
		assignedchaptername=test.acsScarfReviewPage.assignReviewerToAChapter("Online Reviewer",0);
		test.acsScarfReviewPage.assignReviewerToAChapter("Online Reviewer",1);
		test.acsScarfReviewPage.assignReviewerToAChapter("Faculty Decision Panel Reviewer", 0);
		test.acsScarfReviewPage.assignReviewerToAChapter("Green Chemistry Reviewer", 0);
		customerSortNames=test.acsScarfReviewPage.getCustomerSortName(test.acsScarfReviewPage.getReviewerNameList());
		
	}
	
	@Test(dataProvider="loginDetails")
	public void Step05_Execute_Scarf_Reviewer_Query_And_Fetch_Login_Details(int reviewerCount)
	{
		test.acsScarfReviewPage.clickOnQueryTabForScarfModule("Query");
		test.memberShipPage.selectAndRunQuery("BP - Reviewers");
		test.memberShipPage.enterSingleCustomerIdInRunQuery(customerSortNames[reviewerCount]);
		test.individualsPage.NavigateToIndividualProfilePageFromScarfReviewList(test.acsScarfReviewPage.getReviewerNameList().get(reviewerCount));
		test.memberShipPage.fetchScarfReviewerLoginDetails(ReviewerLoginMap,reviewerCount);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnSacrfReportingModule();
		test.homePageIWEB.clickOnLeftMenuTab("Reviewers");
		test.acsScarfReviewPage.clickOnQueryTabForScarfModule("Query");
	}
	
	@Test  
	public void Step06_Launch_Eweb_Application_And_Enter_Reviews_By_First_Online_Reviewer(){
		test.launchApplication(app_url_eweb);
		test.acsScarfReporting.loginWithLastNameAndMemberId(ReviewerLoginMap.get("reviewer"+i).get(0),ReviewerLoginMap.get("reviewer"+i).get(1)); //"Easter", "2175095"
		test.acsScarfReporting.verifyStudentChapterReportingPage();
		index=test.acsScarfReviewing.verifyChapterOnTheReviewPageAndClickOnreviewButton(assignedchaptername,"list_ChapterList");//Arcadia University Student Chapter"
	    test.acsScarfReviewing.verifyChapterStatus("Not Started",index); //In Progress
	    test.acsScarfReviewing.selectChapterReviewImage(index);
		test.acsScarfReporting.clickOnNotStartedButtonForSection("Self-Assessment", "Start");
		i++;
	}
	
	@Test(dataProvider="Sections")
	public void Step07_Enter_Reviews_For_All_The_Sections(String sectionName){
        test.acsScarfReviewing.enterRating("Commendable",sectionName); //Outstanding
        test.acsScarfReviewing.enterCommentsForSections(sectionName,"test data");
		test.acsScarfReviewing.clickOnNextButton();
	}
	
	@Test
	public void Step08_Submit_Reviews_And_Verify_Review_Status(){
		test.acsScarfReviewing.enterOverallRating("Outstanding");
		test.acsScarfReviewing.clickOnSubmitButton();
		test.acsScarfReviewing.enterOverallReview("nice work test data");
		test.acsScarfReviewing.clickOnSubmitButton();
		test.acsScarfReviewing.clickOnReturnToDashboardButton();
	    test.acsScarfReviewing.verifyChapterStatus("Submitted",index); //Not Started
	}
	
	@Test
	public void Step09_Launch_Eweb_Application_And_Enter_Reviews_By_Second_Online_Reviewer(){
		Step06_Launch_Eweb_Application_And_Enter_Reviews_By_First_Online_Reviewer();
	}
	
	@Test(dataProvider="Sections")
	public void Step10_Enter_Reviews_For_All_Sections(String sectionName){
		Step07_Enter_Reviews_For_All_The_Sections(sectionName);
	}
	
	@Test
	public void Step11_Submit_Reviews_And_Verify_Review_Status_By_Second_Online_Reviewer(){
        Step08_Submit_Reviews_And_Verify_Review_Status();
	}
	
	@Test
	public void Step12_Launch_Eweb_Application_And_Enter_Reviews_By_FDP_Reviewer(){
		test.launchApplication(app_url_eweb);
		test.acsScarfReporting.loginWithLastNameAndMemberId(ReviewerLoginMap.get("reviewer"+i).get(0),ReviewerLoginMap.get("reviewer"+i).get(1)); //"Hare","2250525"
		test.acsScarfReviewing.verifyReviewerTypeWindow("Faculty Decision Panel Reviewer");
		index=test.acsScarfReviewing.verifyChapterOnTheReviewPageAndClickOnreviewButton(assignedchaptername,"list_notStartedChapters");//  Belmont University Student Chapter  .....Arcadia University Student Chapter"
	    test.acsScarfReviewing.selectChapterReviewImage(index);
		test.acsScarfReporting.clickOnNotStartedButtonForSection("Self-Assessment", "Start");
	}
	
	@Test(dataProvider="Sections")
	public void Step13_Enter_Reviewes_For_Fdp_Reviewer_Sections(String sectionName){
		test.acsScarfReviewing.enterCommentsForSectionsByFdpReviewer(sectionName,1);
		test.acsScarfReviewing.clickOnNextButton();
	}
	
	@Test
	public void Step14_Submit_Reviews_And_Verify_Review_Status_By_Second_Online_Reviewer(){
		test.acsScarfReviewing.enterOverallRating("Outstanding");
		test.acsScarfReviewing.clickOnSubmitButton();
		test.acsScarfReviewing.enterOverallReview("nice work test data");
		test.acsScarfReviewing.clickOnSubmitButton();
		test.acsScarfReviewing.clickOnReturnToDashboardButton();
	    test.acsScarfReviewing.clickOnSubmittedChaptersTab("Submitted"); 	
		test.acsScarfReviewing.verifyChapterOnTheReviewPageAndClickOnreviewButton(assignedchaptername,"list_notStartedChapters");
	}
	
	@DataProvider(name = "loginDetails")
	public static Object[][] Reviewer_Details() {
		
		int[] reviewerCount={0,1,2,3};
			return new Object[][] {{reviewerCount[0]},{reviewerCount[1]},{reviewerCount[2]}, {reviewerCount[3]}};
	}
	
	@DataProvider(name="Sections")
	public static Object[][] provideSectionNames(){
		return new Object[][] {{"Self-Assessment"},{"Service"},{"Professional Development"},{"Chapter Development"},{"Budget"},{"Overall Report Assessment"}};
	}
	
	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result)
	{
		test.takescreenshot.takeScreenShotOnException(result);
	}
	
//	@AfterClass
	public void close_Browser_Window()
	{	
		test.closeBrowserWindow();
	}
	
}
