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

	String app_url_iweb;
    String[] customerSortNames;
	private int caseID;
	

	
	@BeforeClass
	public void open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_iweb = getYamlValue("app_url_IWEB");
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
		test.acsScarfReviewPage.assignReviewerToAChapter("Online Reviewer",0);
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
	
	@DataProvider(name = "loginDetails")
	public static Object[][] Reviewer_Details() {
		
		int[] reviewerCount={0,1,2,3};
			return new Object[][] {{reviewerCount[0]},{reviewerCount[1]},{reviewerCount[2]}, {reviewerCount[3]}};
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
