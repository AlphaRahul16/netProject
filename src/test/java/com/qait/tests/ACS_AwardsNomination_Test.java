package com.qait.tests;



import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;
import org.testng.internal.Yaml;

import com.qait.automation.utils.DataProvider;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseUi;

public class ACS_AwardsNomination_Test {
	TestSessionInitiator test;
	String app_url_Nominate,app_url_IWEB,app_url_nominateFellow;
	private String caseID;
	private String currentAwardName;
	private String NomineeName;
	List<String> memberDetails;
	Map<String,String> mapNomineeNames;
	Map<String,String> mapAwardsNomination=new HashMap<String,String>();
	
	public ACS_AwardsNomination_Test() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "AwardNomination";
	}
	
	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_AwardsNomination_Test(String caseID) {
		this.caseID = caseID;

	}
	
	@Test
	public void Step01_TC01_Launch_Iweb_And_Select_General_Award() {
		mapAwardsNomination=test.homePageIWEB.addValuesInMap("AwardNomination", caseID);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Awards");
		test.homePageIWEB.clickOnTab("Find Award");
		test.individualsPage.enterFieldValue("Award Year",DateUtil.getAnyDateForType("YYYY",1,"year"));
		test.individualsPage.clickGoButton();
		currentAwardName=test.individualsPage.selectRandomGeneralAward_AwardNomination(DataProvider.getRandomSpecificLineFromTextFile("GeneralAwardList").trim());
		test.fundpofilePage.editpostToWebAndRemoveFromWebDates_AwardNomination();
		test.invoicePage.expandDetailsMenu("award stages/rounds");
		//test.invoicePage.verifyStartDateIsNotEmpty_AwardsNomination();
		test.invoicePage.collapseDetailsMenu("award stages/rounds");
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("CRM");
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.clickOnSideBar("Query Individual");
	    test.memberShipPage.selectAndRunQuery("Selenium - Find Active Regular Member");
	    memberDetails = test.memberShipPage.getCustomerFullNameAndContactID();
	}
	
	@Test
	public void Step02_TC02_Launch_Eweb() {  
		test.launchApplication(app_url_Nominate);
		test.asm_NominatePage.loginInToAwardsNominateApplication(mapAwardsNomination,memberDetails);
		test.asm_NominatePage.clickOnConfirmNominatorAdressDetailsIfAppears_AwardNomination();
		test.asm_NominatePage.selectAwardFromAwardListAndVerifyNominationMessage(currentAwardName);
		
		test.asm_NominatePage.clickOnCreateNewNominationButton(); 
		for (String name : mapAwardsNomination.keySet()) {
			String key = name.toString();
			String value = mapAwardsNomination.get(name).toString();
			System.out.println(key + " " + value);
		}
		NomineeName=test.asm_NominatePage.SearchNomineeByMemeberNameOrNumber(mapAwardsNomination);
		test.asm_NominatePage.FillEligibilityQuestionsDetails_AwardsNomination(mapAwardsNomination);
		test.asm_NominatePage.clickSaveForLaterButtonToNavigateToHomePage();
		test.asm_NominatePage.verifyAwardStatusOnHomePageAwardNomination(currentAwardName,"Incomplete Submission");
		
		test.asm_NominatePage.navigateToHomePageFromEligibilityQuestionPage();
		mapNomineeNames=test.asm_NominatePage.FillDetailsOnVerifyEligibilityPage(mapAwardsNomination);
		test.asm_NominatePage.clickSaveForLaterButtonToNavigateToHomePage();
		test.asm_NominatePage.verifyAwardStatusOnHomePageAwardNomination(currentAwardName,"Waiting for Confirmation");
	 
		test.asm_NominatePage.NavigateToConfirmNominationPageAndVerifyNominationDetails(mapAwardsNomination);
		test.asm_NominatePage.clickOnSubmitButton();
		test.asm_NominatePage.verifyNominationSubmitted();
		test.asm_NominatePage.verifyAwardStatusOnHomePageAwardNomination(currentAwardName,"Nomination completed");
		
		//test.asm_NominatePage.verifyDocumentsAreDownloadableOnConfirmNominationPage("AwardNomination");
	}
	
	@Test
	public void Step03_TC03_Launch_Iweb_And_Verify_Details() {  
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Awards");
		test.homePageIWEB.clickOnTab("Find Award");
		test.individualsPage.enterFieldValue("Award Year",DateUtil.getAnyDateForType("YYYY",1,"year"));
		test.individualsPage.clickGoButton();
		
		test.individualsPage.selectRandomGeneralAward_AwardNomination(currentAwardName);
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Entrants");
		test.invoicePage.expandDetailsMenu("acs nominee/ entry");
		
		test.individualsPage.selectNomineeEntryForVerification(memberDetails.get(0).trim());
		test.invoicePage.expandDetailsMenu("acs award entry supporter");
		test.individualsPage.verifyDetailsForAwardsNomination(mapAwardsNomination,mapNomineeNames);
		test.invoicePage.collapseDetailsMenu("acs award entry supporter");
		
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Documents");
		test.invoicePage.expandDetailsMenu("document");
		test.individualsPage.verifyLetterDocumentsOnAwardEntryProfilePage(mapAwardsNomination);
		test.invoicePage.collapseDetailsMenu("document");
		test.individualsPage.clickOnEditButtonAndVerifyNomineeDetails_AwardRequirementsAndRecommendation(mapAwardsNomination);
		
		
	}
	
	
	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IWEB");
		app_url_Nominate = getYamlValue("app_url_Nominate");
		test.launchApplication(app_url_IWEB);
	test.homePageIWEB.enterAuthenticationAutoIt();
	}
	@AfterClass
	public void Close_Browser_Session() {
		//test.closeBrowserSession();
	}
	
}
