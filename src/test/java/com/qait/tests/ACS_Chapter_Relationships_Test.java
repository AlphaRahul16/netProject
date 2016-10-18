package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;

public class ACS_Chapter_Relationships_Test extends BaseTest {
	String app_url_IWEB;
	String Chaptername,individualname;
	static String  sheetName;
	Map<String, String> mapChapterRelationships = new HashMap<String, String>();
	
	public ACS_Chapter_Relationships_Test() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "Chapter_Relationships";
		sheetName=com.qait.tests.DataProvider_FactoryClass.sheetName;
		System.out.println("sheet name "+sheetName);
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_Chapter_Relationships_Test(String caseID) {
		this.caseID = caseID;
	}

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
	public void Step_01_Navigate_To_Query_Individual_Page_And_Run_Query_To_Find_Random_Active_Member() {
		mapChapterRelationships=test.homePageIWEB.addValuesInMap(sheetName, caseID);
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.homePageIWEB.clickOnTab("Query Individual");
		test.memberShipPage.selectAndRunQuery(mapChapterRelationships.get("CRM_Query_Name"));
	}
	@Test
	public void Step_02_Navigate_To_Relations_Tab_And_Expand_Chapter_Relationships_Tab() {
	
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Relations");
		test.individualsPage.expandDetailsMenu("chapter relationships");
		test.fundpofilePage.clickAddDonationButton("chapter relationships");
	}
	@Test
	public void Step_03_Add_Chapter_Relationships_To_Random_Member_And_Save_Changes() {
		
		Chaptername=test.individualsPage.addChapterRelationshipsToIndividual();
        test.individualsPage.addRelationshiptype(mapChapterRelationships.get("Relationship_Type_To_Add"));
        test.individualsPage.enterStartDateForChapter(DateUtil.getCurrentdateInStringWithGivenFormate("MM/d/yyyy"));
		test.individualsPage.clickOnSaveButton();
	}
	@Test
	public void Step_04_Verify_Current_Record_Is_Added_In_Individual_Relationships() {
		
		test.individualsPage.verifyCurrentRecordisAddedInChapterRelationship(Chaptername,mapChapterRelationships.get("Relationship_Type_To_Add"));
	}
	@Test
	public void Step_05_Navigate_To_Query_Chapters_In_Membership_Module_And_Run_Query_To_Find_Active_Chapter() {
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
		test.homePageIWEB.clickOnSideBarTab("Chapters");
		test.homePageIWEB.clickOnTab("Query Chapter");
		test.memberShipPage.selectAndRunQuery(mapChapterRelationships.get("Chapter_Query_Name"));
	}
	@Test
	public void Step_06_Navigate_To_Relations_Tab_And_Expand_Individual_Relationships_Tab() {
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Relations");
		test.individualsPage.expandDetailsMenu("individual relationships");
		test.fundpofilePage.clickAddDonationButton("individual relationships");
	}
	@Test
	public void Step_07_Add_Individual_Relationships_To_The_Chapter_And_Save_Changes() {
		

		test.individualsPage.enterIndividualSortName("sort name", mapChapterRelationships.get("Individual_Sort_Name"));
		test.individualsPage.addIndividualRelationshipsToChapter();
		test.individualsPage.addRelationshiptype(mapChapterRelationships.get("Relationship_Type_To_Add"));
		test.individualsPage.enterStartDateForChapter(DateUtil.getCurrentdateInStringWithGivenFormate("MM/d/yyyy"));
		individualname=test.individualsPage.getindividualRelationshipName("sort name");
		test.individualsPage.clickOnSaveButton();
	}
	@Test
	public void Step_08_Verify_Current_Record_Is_Added_In_Chapter_Relationships() {
		
	
		test.individualsPage.verifyCurrentRecordisAddedInChapterRelationship(individualname,mapChapterRelationships.get("Relationship_Type_To_Add"));
	}	
	@Test
	public void Step_09_Edit_Existing_Record_And_Verify_The_C() {
		
		test.individualsPage.clickPencilButtonToEditIndividualRecord(individualname);
		test.individualsPage.addRelationshiptype(mapChapterRelationships.get("Individual_Relationship_Type_To_Edit"));
		test.individualsPage.clickOnSaveButton();
		test.individualsPage.verifyCurrentRecordisAddedInChapterRelationship(individualname,mapChapterRelationships.get("Individual_Relationship_Type_To_Edit"));
	}
	
}

