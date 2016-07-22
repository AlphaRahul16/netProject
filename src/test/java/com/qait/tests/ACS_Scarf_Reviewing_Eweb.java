package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;

public class ACS_Scarf_Reviewing_Eweb {

	static String sheetName;
	TestSessionInitiator test;
	String app_url_eweb;
	int index;

	@BeforeClass
	public void open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_eweb = getYamlValue("app_url_ScarfReviewing");
	}
	
	@Test  
	public void Step01_Launch_Eweb_Application_And__Select_Chapter_Review_Button(){
		test.launchApplication(app_url_eweb);
		test.acsScarfReporting.loginWithLastNameAndMemberId("Easter", "2175095");
		test.acsScarfReporting.verifyStudentChapterReportingPage();
		index=test.acsScarfReviewing.verifyChapterOnTheReviewPageAndClickOnreviewButton("California State Polytechnic University-Pomona Student Chapter");//Arcadia University Student Chapter"
	    test.acsScarfReviewing.verifyChapterStatus("In Progress",index); //Not Started
	    test.acsScarfReviewing.selectChapterReviewImage(index);
		test.acsScarfReporting.clickOnNotStartedButtonForSection("Self-Assessment", "Start");
	}

	@Test(dataProvider="Sections")
	public void Step02_Enter_Reviews_For_All_The_Sections(String sectionName){
        test.acsScarfReviewing.enterRating("Commendable",sectionName); //Outstanding
        test.acsScarfReviewing.enterCommentsForSections(sectionName,"test data");
		test.acsScarfReviewing.clickOnNextButton();
	}
	
	@Test
	public void Step03_Submit_Reviews_And_Verify_Review_Status(){
		test.acsScarfReviewing.enterOverallRating("Outstanding");
		test.acsScarfReviewing.clickOnSubmitButton();
		test.acsScarfReviewing.enterOverallReview("nice work test data");
		test.acsScarfReviewing.clickOnSubmitButton();
		test.acsScarfReviewing.clickOnReturnToDashboardButton();
	    test.acsScarfReviewing.verifyChapterStatus("Submitted",index); //Not Started
	}
	
	
	@DataProvider(name="Sections")
	public static Object[][] provideSectionNames(){
		return new Object[][] {{"Self-Assessment"},{"Service"},{"Professional Development"},{"Chapter Development"},{"Budget"},{"Overall Report Assessment"}};
	}
	
}
