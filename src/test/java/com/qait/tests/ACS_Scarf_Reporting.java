package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.XlsReader;
import com.qait.automation.utils.YamlReader;

public class ACS_Scarf_Reporting {
	static String sheetName;
	TestSessionInitiator test;
	String app_url_iweb, custId, officerName,chapterName;
	List<Integer> rowNumberList = new ArrayList<Integer>();
	List<String> memberDetails=new ArrayList<String>();
	HashMap<String, String> dataList = new HashMap<String, String>();
	private int caseID;

	int i, j;
	
	public ACS_Scarf_Reporting() {
		sheetName= com.qait.tests.Data_Provider_Factory_Class_Xml.sheetName="ScarfReporting";
	}


	@Factory(dataProviderClass = com.qait.tests.Data_Provider_Factory_Class_Xml.class, dataProvider = "data")
	public ACS_Scarf_Reporting(int caseID) {
		this.caseID = caseID;
	}
	
	@BeforeClass
	public void open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		System.out.println("Row Num::"+caseID);
		dataList = XlsReader.addValuesInTheMapForExcel(sheetName, caseID);
		app_url_iweb = getYamlValue("app_url_IwebReporting");
	}
	
//	@Test
	public void Step01_Launch_Iweb_Application(){
		test.launchApplication(app_url_iweb);
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}
	
//	@Test
	public void Step02_Verify_User_Navigated_To_Scarf_Reporting_Page(){
		Reporter.log("CASE ID : " + caseID, true);
		test.homePageIWEB.clickOnModuleTab(); 
		test.homePageIWEB.clickOnSacrfReportingModule();
		test.homePageIWEB.verifyUserIsOnHomePage("Scarf Reporting | Overview | Student Chapter Reporting Setup");
	}
	
//	@Test
	public void Step03_Click_On_Student_Chapter_reporting_Link(){
		test.acsScarfReporting.clickOnStudentChapterReportingLink();
		test.memberShipPage.expandDetailsMenu("academic year");
		test.acsScarfReporting.verifyReportingStartAndEndDate();
	}
	
//	@Test
	public void Step04_Verify_User_Navigated_To_Membership_Page_On_Clicking_Membership_Under_Modules_Tab(){
		Reporter.log("CASE ID : " + caseID, true);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
		test.homePageIWEB.verifyUserIsOnHomePage("Membership | Overview | Overview and Setup");
	}
	
//	@Test
	public void Step05_Select_Query_In_Query_Chapter_Page(){
		Reporter.log("CASE ID : " + caseID, true);
		test.homePageIWEB.clickOnSideBarTab("Chapters");
		test.memberShipPage.clickOnTab("Query Chapter");
		test.memberShipPage.selectAndRunQuery(getYamlValue("ScarfReporting.queryName"));
		test.memberShipPage.verifyOueryAskAtRunTimePage();
//	    test.memberShipPage.selectMemberPackage(test.memberShipPage.map().get("Original MemberPackage"));
	    test.memberShipPage.clickOnGoButtonAfterPackageSelection();
	}
	
//	@Test
	public void Step06_Select_Active_Student_Chapter_And_Get_Member_Details(){
		test.acsScarfReporting.selectARandomActiveStudentChapter();
		chapterName=test.acsScarfReporting.getChapterDetails();
		test.acsScarfReporting.clickOnRelationsOptionUnderMoreMenu();
		test.acsScarfReporting.selectStudentMember();
		memberDetails=test.acsScarfReporting.getMemberDetails();
	}
	
	@Test
	public void Step07_Launch_Eweb_Application_For_Student_Chapter_Reporting_And_Login_With_LastName_And_Member_ID() {
		test.launchApplication(getYamlValue("app_url_EwebReporting"));
		test.acsScarfReporting.loginWithLastNameAndMemberId("Richard","30919509");	//memberDetails.get(0),memberDetails.get(1)
		test.acsScarfReporting.verifyStudentChapterReportingPage();
		test.acsScarfReporting.verifyReportStatus("Pending");
	}
	
	@Test
	public void Step08_Add_Chapter_Officer(){
		test.acsScarfReporting.clickOnEditChapterInfoButton();
		test.acsScarfReporting.clickOnAddOrEditChapterOfficersButton();
		test.acsScarfReporting.verifyAlreadyPresentOfficerRole(dataList.get("Officer Role"));
		test.acsScarfReporting.clickOnAddOfficerButton();
		test.acsScarfReporting.addChapterOfficer();
		officerName=test.acsScarfReporting.selectChapterOfficer(1);
		test.acsScarfReporting.selectOfficerRole(dataList.get("Officer Role"));
		test.acsScarfReporting.clickOnSaveButton();
		test.acsScarfReporting.verifyAdditionOfOfficer(officerName);
		test.acsScarfReporting.clickOnReturnButton();
	}
	
//	@Test
	public void Step09_Add_Details_In_Department_Institution_Information_Section_And_Click_On_Build_Report(){
		test.acsScarfReporting.DepartmentAndInstitutionInformation(dataList.get("Undergraduates Majoring in Chemistry")
				, dataList.get("Chemistry faculty Members"));
		test.acsScarfReporting.clickOnSaveChapterInformationButton();
		test.acsScarfReporting.verifyStudentChapterReportingPage();
		test.acsScarfReporting.clickOnReportTab("report");
        test.acsScarfReporting.verifyChapterStatus("Chapter Information","Complete");
	}
	
//	@Test
	public void Step10_Enter_Details_For_Self_Assessment_Section(){
		test.acsScarfReporting.clickOnNotStartedButtonForSection("Self-Assessment","Not-Started");
		test.acsScarfReporting.enterSectionDetails(dataList.get("Answers"),"Self-Assessment",4);
		test.acsScarfReporting.clickOnSelfAssessmentSaveButton("Save");
	}
	
//	@Test
	public void Step11_Add_Five_Events(){
		test.acsScarfReporting.clickOnReportTab("events");
		test.acsScarfReporting.createEvents(dataList,test.asm_FellowNomiate);
	}
	
//	@Test
	public void Step12_Enter_Details_For_Service_Section(){
		test.acsScarfReporting.clickOnReportTab("report");
		test.acsScarfReporting.clickOnNotStartedButtonForSection("Service","In-progress");
		test.acsScarfReporting.enterSectionDetails(dataList.get("Answers"),"Service",4);
		test.acsScarfReporting.clickOnSelfAssessmentSaveButton("Save");
        test.acsScarfReporting.verifyChapterStatus("Service","Complete");
	}
	
//	@Test
	public void Step13_Enter_Details_For_Professional_Development_Section(){
		test.acsScarfReporting.clickOnNotStartedButtonForSection("Professional Development","In-progress");
		test.acsScarfReporting.enterSectionDetails(dataList.get("Answers"),"Professional Development",3);
		test.acsScarfReporting.clickOnSelfAssessmentSaveButton("Save");
        test.acsScarfReporting.verifyChapterStatus("Professional Development","Complete");
	}
	
//	@Test
	public void Step14_Enter_Details_For_Chapter_Development_Section(){
		test.acsScarfReporting.clickOnNotStartedButtonForSection("Chapter Development","In-progress");
		test.acsScarfReporting.enterSectionDetails(dataList.get("Answers"),"Chapter Development",3);
		test.acsScarfReporting.clickOnSelfAssessmentSaveButton("Save");
        test.acsScarfReporting.verifyChapterStatus("Chapter Development","Complete");
	}
	
//	@Test
	public void Step15_Enter_Details_For_Budget_Section(){
		test.acsScarfReporting.clickOnNotStartedButtonForSection("Budget","Not-Started");
		test.acsScarfReporting.enterSectionDetails(dataList.get("Answers"),"Budget",1);
		test.asm_FellowNomiate.uploadFileUsingJavascipt("ExcelTestSheet","bp1", "Events");
		test.acsScarfReporting.clickOnSelfAssessmentSaveButton("Save");
        test.acsScarfReporting.verifyChapterStatus("Budget","Complete");
	}
	
//	@Test
	public void Step16_Enter_Details_For_Green_Chemistry_Section(){
		test.acsScarfReporting.clickOnNotStartedButtonForSection("Green Chemistry","In-progress");
		test.acsScarfReporting.enterSectionDetails(dataList.get("Answers"),"Green Chemistry",1);
		test.acsScarfReporting.clickOnGreenChemistryCheckbox();
		test.acsScarfReporting.clickOnSelfAssessmentSaveButton("Save");
        test.acsScarfReporting.verifyChapterStatus("Green Chemistry","Complete");
	}
	
//	@Test
	public void Step17_Verify_Merging_Of_All_Sections_And_Report_Submission(){
		test.acsScarfReporting.clickOnReportTab("review-and-submit");
        test.acsScarfReporting.verifyMergingOfSections();
        test.acsScarfReporting.clickOnReportSubmitCheckbox();
		test.acsScarfReporting.clickOnSelfAssessmentSaveButton("Submit");
		test.acsScarfReporting.verifyConfirmChapterAppearWindow();
		test.acsScarfReporting.verifyReportCompletePage();
		test.acsScarfReporting.clickOnSubmitReportButton("Save & Return to the Dashboard");
		test.acsScarfReporting.verifyReportStatus("Submitted");
	}
	
//	@Test
	public void Step18_Launch_Iweb_Application_And_Click_On_Student_Chapter_Report_Tab(){
		test.launchApplication(app_url_iweb);
		test.homePageIWEB.clickOnModuleTab(); 
		test.homePageIWEB.clickOnSacrfReportingModule();
		test.homePageIWEB.verifyUserIsOnHomePage("Scarf Reporting | Overview | Student Chapter Reporting Setup");
	}
	
//	@Test
	public void Step19_Click_On_Find_Tab_And_Select_Submitted_Chapter_Report(){
		test.acsScarfReporting.clickOnSideBarTabStudentChapter("Student Chapter Report",2);
		test.acsScarfReporting.clickOnSideBarSubTab("Find");
		test.acsScarfReporting.findSubmiitedChapterReport(chapterName, "Submitted");
	}
	
}
