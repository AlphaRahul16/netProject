package com.qait.tests;



import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.List;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.utils.DataProvider;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.TestSessionInitiator;

public class ACS_AwardsNomination_Test {
	TestSessionInitiator test;
	String app_url_nominateFellow;
	String app_url_Nominate;
	private String caseID;
	String app_url_IWEB;
	List<String> memberDetails;
	
	public ACS_AwardsNomination_Test() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "AwardNomination";
	}
	
	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_AwardsNomination_Test(String caseID) {
		this.caseID = caseID;

	}
	
	@Test
	public void Step01_TC01_Launch_Iweb_And_Select_General_Award() {
		test.homePageIWEB.addValuesInMap("AwardNomination", caseID);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Awards");
		test.homePageIWEB.clickOnTab("Find Award");
		test.individualsPage.enterFieldValue("Award Year",DateUtil.getAnyDateForType("YYYY",1,"year"));
		test.individualsPage.clickGoButton();
		test.individualsPage.selectRandomGeneralAward_AwardNomination(DataProvider.getRandomSpecificLineFromTextFile("GeneralAwardList").trim());
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
		test.asm_FellowNomiate.loginInToApplication(memberDetails.get(0).split(" ")[0],memberDetails.get(1));
	}
	
	
	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IWEB");
		app_url_Nominate = getYamlValue("app_url_Nominate");
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication("C00616",
		"Zx605@95");
	}
	@AfterClass
	public void Close_Browser_Session() {
		//test.closeBrowserSession();
	}
	
}
