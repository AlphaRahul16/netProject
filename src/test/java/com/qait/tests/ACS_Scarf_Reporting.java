package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.XlsReader;
import com.qait.automation.utils.YamlReader;

public class ACS_Scarf_Reporting {
	static String sheetName;
	TestSessionInitiator test;

	String app_url_iweb, custId, officerName, chapFacultyAdvisor;
	String chapterName;
	List<Integer> rowNumberList = new ArrayList<Integer>();
	List<String> memberDetails = new ArrayList<String>();
	HashMap<String, String> dataList = new HashMap<String, String>();
	Map<String, List<String>> eventsMap = new HashMap<String, List<String>>();
	private int caseID;

	int i, j;

	public ACS_Scarf_Reporting() {
		sheetName = com.qait.tests.Data_Provider_Factory_Class_Xml.sheetName = "ScarfReporting";
	}

	@Factory(dataProviderClass = com.qait.tests.Data_Provider_Factory_Class_Xml.class, dataProvider = "data")
	public ACS_Scarf_Reporting(int caseID) {
		this.caseID = caseID;
	}

	@BeforeClass
	public void open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		dataList = XlsReader.addValuesInTheMapForExcel(sheetName, caseID);
		app_url_iweb = getYamlValue("app_url_IwebReporting");
	}
	
	@DataProvider(name="details")
	public static Object[][] answerDetails(){
		return new Object[][] {{"Service","In-progress", 4,"Save","Complete"},
			{"Professional Development", "In-progress", 3,"Save","Complete"},
			{"Chapter Development", "In-progress",3,"Save","Complete"}};
	}
		
	@Test
	public void Step01_Launch_Iweb_Application() {
		test.launchApplication(app_url_iweb);
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
//		chapterName="Monroe Community College Student Chapter";
	}

	@Test
	public void Step02_User_Navigated_To_Scarf_Reporting_Page() {
		Reporter.log("CASE ID : " + caseID, true);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnSacrfReportingModule();
		test.homePageIWEB.verifyUserIsOnHomePage("Scarf Reporting | Overview | Student Chapter Reporting Setup");
	}

	@Test
	public void Step03_Go_To_Student_Chapter_reporting_Page_And_Verify_Start_And_End_Date() {
		test.acsScarfReporting.clickOnStudentChapterReportingLink();
		test.memberShipPage.expandDetailsMenu("academic year");
		test.memberShipPage.verifyReportingStartAndEndDate();
	}

	@Test
	public void Step04_User_Navigated_To_Membership_Page_And_Select_Query_To_Find_Active_Student_Chapter() {
		Reporter.log("CASE ID : " + caseID, true);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
		test.homePageIWEB.verifyUserIsOnHomePage("Membership | Overview | Overview and Setup");
		test.homePageIWEB.clickOnSideBarTab("Chapters");
		test.memberShipPage.clickOnTab("Query Chapter");
		test.memberShipPage.selectAndRunQuery(getYamlValue("ScarfReporting.queryName"));
		test.memberShipPage.verifyOueryAskAtRunTimePage();
		test.memberShipPage.clickOnGoButtonAfterPackageSelection();
	}

	@Test
	public void Step05_Select_Active_Student_Chapter_And_Get_Member_Details() {
		test.memberShipPage.selectARandomActiveStudentChapter();
		chapterName = test.memberShipPage.getChapterDetails();
		test.memberShipPage.clickOnRelationsOptionUnderMoreMenu();
		test.memberShipPage.selectStudentMember();
		memberDetails = test.memberShipPage.getMemberDetails();
	}

	@Test
	public void Step06_Login_Into_Student_Chapter_Eweb_Application_And_Verify_Report_Status() {
		test.launchApplication(getYamlValue("app_url_EwebReporting")); //"McKinley","30938296" "LaRoe", "30845095"
        test.acsScarfReporting.loginWithLastNameAndMemberId(memberDetails.get(0), memberDetails.get(1));// "McKinley","30938296" "Davis","30761435""Wolf","308413336
//        test.acsScarfReporting.loginWithLastNameAndMemberId("Landrum","30793155");//"Tchalla","30926975"
		test.acsScarfReporting.verifyStudentChapterReportingPage();
		test.acsScarfReporting.verifyReportStatus("Pending");
		chapFacultyAdvisor = test.acsScarfReporting.getChapterFacultyAdvisor();
	}

	@Test
	public void Step07_Add_Primary_Chapter_Officer() {
		boolean flag;
		test.acsScarfReporting.clickOnEditChapterInfoButton();
		test.acsScarfReporting.clickOnAddOrEditChapterOfficersButton();
		flag=test.acsScarfReporting.verifyAlreadyPresentOfficerRole(dataList.get("Officer Role"));
		test.acsScarfReporting.verifypresenceOfMoreThanTwoPrimaryContact(flag);
		test.acsScarfReporting.clickOnAddOfficerButton();
		test.acsScarfReporting.addChapterOfficer();
		officerName = test.acsScarfReporting.selectChapterOfficer(1);
		test.acsScarfReporting.selectOfficerRole(dataList.get("Officer Role"));
		test.acsScarfReporting.clickOnSaveButton();
		test.acsScarfReporting.verifyAdditionOfOfficer(officerName);
		test.acsScarfReporting.clickOnReturnButton();
	}

	@Test
	public void Step08_Add_Details_In_Department_Institution_Information_Section() {
		test.acsScarfReporting.DepartmentAndInstitutionInformation(dataList.get("Undergraduates Majoring in Chemistry"),
				dataList.get("Chemistry faculty Members"));
		test.acsScarfReporting.clickOnSaveChapterInformationButton();
		test.acsScarfReporting.verifyStudentChapterReportingPage();
		test.acsScarfReporting.clickOnReportTab("report");
		test.acsScarfReporting.verifyChapterStatus("Chapter Information", "Complete");
	}

	@Test
	public void Step09_Enter_Details_For_Self_Assessment_Section() {
		test.acsScarfReporting.checkSectionStatus("Self-Assessment");
		test.acsScarfReporting.clickOnNotStartedButtonForSection("Self-Assessment", "Not-Started");
		test.acsScarfReporting.enterSectionDetails(dataList.get("Answers"), "Self-Assessment", 4);
		test.acsScarfReporting.clickOnSelfAssessmentSaveButton("Save");
	}

	@Test
	public void Step10_Add_Five_Events() {
		test.acsScarfReporting.clickOnReportTab("events");
		eventsMap = test.acsScarfReporting.createEvents(dataList, test.asm_FellowNomiate);
		test.acsScarfReporting.clickOnReportTab("report");
	}
	
	@Test(dataProvider="details")
	public void Step11_Enter_Details_For_Service_Professional_And_Chapter_Development_Section(String section,String status,Integer indexLoop,String button,String newStatus) {
		test.acsScarfReporting.checkSectionStatus(section);
		test.acsScarfReporting.clickOnNotStartedButtonForSection(section, status);
		test.acsScarfReporting.enterSectionDetails(dataList.get("Answers"), section, indexLoop);
		test.acsScarfReporting.clickOnSelfAssessmentSaveButton(button);
		test.acsScarfReporting.verifyChapterStatus(section, newStatus);
	}

	@Test
	public void Step14_Enter_Details_For_Budget_Section() {
		test.acsScarfReporting.checkSectionStatus("Budget");
		test.acsScarfReporting.clickOnNotStartedButtonForSection("Budget", "Not-Started");
		test.acsScarfReporting.enterSectionDetails(dataList.get("Answers"), "Budget", 1);
		test.acsScarfReporting.verifyAdditionOfTextDatainBudgetSection(dataList.get("Answers"), 1);
		test.asm_FellowNomiate.uploadFileUsingJavascipt("ExcelTestSheet.xls", "bp1", "Budget");
		test.acsScarfReporting.clickOnSelfAssessmentSaveButton("Save");
		test.acsScarfReporting.verifyChapterStatus("Budget", "Complete");
	}

	@Test
	public void Step15_Enter_Details_For_Green_Chemistry_Section() {		
		test.acsScarfReporting.checkSectionStatus("Green Chemistry");
		test.acsScarfReporting.clickOnNotStartedButtonForSection("Green Chemistry", "In-progress");
		test.acsScarfReporting.enterSectionDetails(dataList.get("Answers"), "Green Chemistry", 1);
		test.acsScarfReporting.clickOnGreenChemistryCheckbox();
		test.acsScarfReporting.clickOnSelfAssessmentSaveButton("Save");
		test.acsScarfReporting.verifyChapterStatus("Green Chemistry", "Complete");
	}

	@Test
	public void Step16_Verify_Merging_Of_All_Sections_And_Report_Submission() {
		test.acsScarfReporting.clickOnReportTab("review-and-submit");
		test.acsScarfReporting.verifyMergingOfSections();
		test.acsScarfReporting.clickOnReportSubmitCheckbox();
		test.acsScarfReporting.clickOnSelfAssessmentSaveButton("Submit");
		test.acsScarfReporting.verifyConfirmChapterAppearWindow();
		test.acsScarfReporting.verifyReportCompletePage();
		test.acsScarfReporting.clickOnSaveAndReturnToDashboardButton();
		test.acsScarfReporting.verifyReportStatus("Submitted");
	}

	@Test
	public void Step17_Launch_Iweb_Application_And_Navigate_Scarf_Reporting() {
		test.launchApplication(app_url_iweb);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnSacrfReportingModule();
		test.homePageIWEB.verifyUserIsOnHomePage("Scarf Reporting | Overview | Student Chapter Reporting Setup");
	}

	@Test
	public void Step18_Search_For_The_Submitted_Chapter_Report() {
		test.acsScarfReporting.clickOnSideBarTabStudentChapter("Student Chapter Report", 2);
		test.acsScarfReporting.clickOnSideBarSubTab("Find");
		test.acsScarfReporting.findSubmiitedChapterReport(chapterName, "Submitted");// chapterName "Kennesaw State University Student Chapter"
		test.acsScarfReporting.clickOnGoButton();
	}

	@Test
	public void Step19_Verify_Submitted_Report_Details() {
//		eventsMap=test.acsScarfReporting.addevents();
		test.acsScarfReporting.verifyIwebReportStatus();
		test.acsScarfReporting
				.verifyChemistryUndergraduateMajorsInReport(dataList.get("Undergraduates Majoring in Chemistry"));
		test.acsScarfReporting.verifyfacultyCountInReport(dataList.get("Chemistry faculty Members"));
		test.memberShipPage.expandDetailsMenu("individual relationships");
		test.acsScarfReporting.verifyChapterFacultyAdvisorOnReport(chapFacultyAdvisor);
		test.memberShipPage.collapseDetailsMenu("individual relationships");
		test.memberShipPage.expandDetailsMenu("event list");
		test.acsScarfReporting.verifyEvents(eventsMap);
		test.acsScarfReporting.verifyPresenceOfReportPdf(chapterName);
		test.memberShipPage.collapseDetailsMenu("event list");
		test.memberShipPage.expandDetailsMenu("report answers");
		test.acsScarfReporting.iterateThroughReportAnswers();
	}

	@AfterClass
	public void Close_Browser_Session() {
//		test.getDriver();
		test.acsScarfReviewPage.assignChapterName(chapterName);
		test.closeBrowserSession();
	}
	
	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result)
	{	
		test.takescreenshot.takeScreenShotOnException(result);
	}

}
