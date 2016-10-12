package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;

public class ACS_Chapter_Relationships_Test extends BaseTest {
	String app_url_IWEB;
	String Chaptername,individualname;

	@BeforeClass
	public void open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IWEB");
		test.launchApplication(app_url_IWEB);
		test.homePage.enterAuthentication(
				YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
	}
	
	
	@Test
	public void Step_01_Navigate_To_Accounting_Module_And_Click_Query_Batch() {
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.homePageIWEB.clickOnTab("Query Individual");
		test.memberShipPage.selectAndRunQuery("Selenium – Find Random Active Member");
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Relations");
		test.individualsPage.expandDetailsMenu("chapter relationships");
		test.fundpofilePage.clickAddDonationButton("chapter relationships");
		Chaptername=test.individualsPage.addChapterRelationshipsToIndividual();
        test.individualsPage.addRelationshiptype("QA Test Relationship");
        test.individualsPage.enterStartDateForChapter(DateUtil.getCurrentdateInStringWithGivenFormate("MM/d/yyyy"));
		test.individualsPage.clickOnSaveButton();
		test.individualsPage.verifyCurrentRecordisAddedInChapterRelationship(Chaptername,"QA Test Relationship");
	
		
		
	}
	

	@Test
	public void Step_02_Navigate_To_Membership_Module_() {
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
		test.homePageIWEB.clickOnSideBarTab("Chapters");
		test.homePageIWEB.clickOnTab("Query Chapter");
		test.memberShipPage.selectAndRunQuery("Selenium - Find Active Chapter");
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Relations");
		test.individualsPage.expandDetailsMenu("individual relationships");
		test.fundpofilePage.clickAddDonationButton("individual relationships");
		test.individualsPage.enterIndividualSortName("sort name", "QA");
		test.individualsPage.addIndividualRelationshipsToChapter();
		test.individualsPage.addRelationshiptype("QA Test Relationship");
		test.individualsPage.enterStartDateForChapter(DateUtil.getCurrentdateInStringWithGivenFormate("MM/d/yyyy"));
		individualname=test.individualsPage.getindividualRelationshipName("sort name");
		test.individualsPage.clickOnSaveButton();
		test.individualsPage.verifyCurrentRecordisAddedInChapterRelationship(Chaptername,"QA Test Relationship");
		test.individualsPage.clickPencilButtonToEditIndividualRecord(individualname);
		
		
	}
	
}
