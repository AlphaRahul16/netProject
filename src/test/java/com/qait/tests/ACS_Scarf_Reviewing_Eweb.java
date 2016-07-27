package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;

public class ACS_Scarf_Reviewing_Eweb {

	static String sheetName;
	TestSessionInitiator test;
	String app_url_eweb;
	int index,i=0;
	Map<String,List<String>> credentials = new HashMap<String,List<String>>();
	List<String> arList=new ArrayList<String>();
	List<String> arList1=new ArrayList<String>();

	@BeforeClass
	public void open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_eweb = getYamlValue("app_url_ScarfReviewing");
        arList.add("Abrams");
        arList.add("2346003");
        arList1.add("Aleman");
        arList1.add("2339139");
		credentials.put("user0",arList);
		credentials.put("user1",arList1);
    }
	
/*	@Test  
	public void Step01_Launch_Eweb_Application_And_Enter_Reviews_By_First_Online_Reviewer(){
		test.launchApplication(app_url_eweb);
		test.acsScarfReporting.loginWithLastNameAndMemberId(credentials.get("user"+i).get(0),credentials.get("user"+i).get(1)); //"Easter", "2175095"
		test.acsScarfReporting.verifyStudentChapterReportingPage();
		index=test.acsScarfReviewing.verifyChapterOnTheReviewPageAndClickOnreviewButton("Belmont University Student Chapter","list_ChapterList");//Arcadia University Student Chapter"
	    test.acsScarfReviewing.verifyChapterStatus("Not Started",index); //In Progress
	    test.acsScarfReviewing.selectChapterReviewImage(index);
		test.acsScarfReporting.clickOnNotStartedButtonForSection("Self-Assessment", "Start");
		i++;
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
		test.acsScarfReviewing.clickOnSubmitButton("Submit");
		test.acsScarfReviewing.enterOverallReview("nice work test data");
		test.acsScarfReviewing.clickOnSubmitButton("Submit");
		test.acsScarfReviewing.clickOnReturnToDashboardButton();
	    test.acsScarfReviewing.verifyChapterStatus("Submitted",index); //Not Started
	}
	
	@Test
	public void Step04_Launch_Eweb_Application_And_Enter_Reviews_By_Second_Online_Reviewer(){
		Step01_Launch_Eweb_Application_And_Enter_Reviews_By_First_Online_Reviewer();
	}
	
	@Test(dataProvider="Sections")
	public void Step05_Enter_Reviews_For_All_Sections(String sectionName){
		Step02_Enter_Reviews_For_All_The_Sections(sectionName);
	}
	
	@Test
	public void Step06_Submit_Reviews_And_Verify_Review_Status_By_Second_Online_Reviewer(){
        Step03_Submit_Reviews_And_Verify_Review_Status();
	}
	
	@Test
	public void Step07_Launch_Eweb_Application_And_Enter_Reviews_By_FDP_Reviewer(){
		test.launchApplication(app_url_eweb);
		test.acsScarfReporting.loginWithLastNameAndMemberId("Hare","2250525"); 
		test.acsScarfReviewing.verifyReviewerTypeWindow("Faculty Decision Panel Reviewer");
		index=test.acsScarfReviewing.verifyChapterOnTheReviewPageAndClickOnreviewButton("Bard College Student Chapter","list_notStartedChapters");//  Belmont University Student Chapter  .....Arcadia University Student Chapter"
	    test.acsScarfReviewing.selectChapterReviewImage(index);
		test.acsScarfReporting.clickOnNotStartedButtonForSection("Self-Assessment", "Start");
	}
	
	@Test(dataProvider="Sections")
	public void Step08_Enter_Reviewes_For_Fdp_Reviewer_Sections(String sectionName){
		test.acsScarfReviewing.enterCommentsForSectionsByFdpReviewer(sectionName,1);
		test.acsScarfReviewing.clickOnNextButton();
	}
	
	@Test
	public void Step09_Submit_Reviews_And_Verify_Review_Status_By_Second_Online_Reviewer(){
		test.acsScarfReviewing.enterOverallRating("Outstanding");
		test.acsScarfReviewing.clickOnSubmitButton("Submit");
		test.acsScarfReviewing.enterOverallReview("nice work test data");
		test.acsScarfReviewing.clickOnSubmitButton("Submit");
		test.acsScarfReviewing.clickOnReturnToDashboardButton();
	    test.acsScarfReviewing.clickOnSubmittedChaptersTab("Submitted"); 	
		test.acsScarfReviewing.verifyChapterOnTheReviewPageAndClickOnreviewButton("Bard College Student Chapter","list_notStartedChapters");
	} */
	
	@Test  
	public void Step10_Launch_Eweb_Application_And_Enter_Reviews_By_Green_Chemistry_Reviewer(){
		test.launchApplication(app_url_eweb);
		test.acsScarfReporting.loginWithLastNameAndMemberId("Constable","00816994"); 
		test.acsScarfReporting.verifyStudentChapterReportingPage();
		index=test.acsScarfReviewing.verifyChapterOnReviewPageForGCReviewer("Aquinas College Student Chapter","list_ChapterList");
	    test.acsScarfReviewing.verifyChapterStatus("Not Started",index);
	    test.acsScarfReviewing.selectChapterReviewImage(index);
	    test.acsScarfReviewing.enterRatingByGreenChemistryReviewer("Yes");
		test.acsScarfReviewing.enterComments(YamlReader.getYamlValue("ScarfReviewer.reviewComments"));
		test.acsScarfReviewing.clickOnSubmitButton("Save & Submit");
	    test.acsScarfReviewing.verifyChapterStatus("Submitted",index); 
		test.acsScarfReviewing.verifyFinalReview("Yes", index);
	    i++;
	}

	
	@DataProvider(name="Sections")
	public static Object[][] provideSectionNames(){
		return new Object[][] {{"Self-Assessment"},{"Service"},{"Professional Development"},{"Chapter Development"},{"Budget"},{"Overall Report Assessment"}};
	}
	
}
