package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.utils.YamlReader;
import com.qait.automation.TestSessionInitiator;


public class ACS_MYD_Transfer {
	
	TestSessionInitiator test;
	String app_url_IWEB;
	private String caseID;
	double previousAmount, newAmount;
	
	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IWEB");
	}
	
	public ACS_MYD_Transfer() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "MYDTransfer";
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_MYD_Transfer(String caseID) {
		this.caseID = caseID;
	}
	
	@Test
	public void Step01_Launch_Iweb_Application() {
		test.homePageIWEB.addValuesInMap("MYDTransfer", caseID);
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}
	
	@Test
	public void Step02_Verify_User_Navigated_To_Membership_Page_On_Clicking_Membership_Under_Modules_Tab(){
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
		test.homePageIWEB.verifyUserIsOnHomePage("Membership | Overview | Overview and Setup");
	}
	
	@Test
	public void Step03_Select_Query_In_Query_Membership_Page(){
		test.homePageIWEB.clickOnSideBarTab("Members");
		test.memberShipPage.clickOnTab("Query Membership");
		test.memberShipPage.selectAndRunQuery(getYamlValue("membership.queryName"));
		test.memberShipPage.verifyOueryAskAtRunTimePage();
	    test.memberShipPage.selectMemberPackage(test.memberShipPage.map().get("Original MemberPackage"));
	}
	
	@Test
	public void Step04_Verify_Membership_Profile_Page(){
		test.homePageIWEB.verifyUserIsOnHomePage("Membership | Members | Membership Profile");
		test.memberShipPage.verifyMemberTypeAndPackage("Regular Member","Active Renewed-No Response");
		test.memberShipPage.expandDetailsMenu("invoices");
		test.memberShipPage.verifyTermEndDateAndStartDateIsEmpty();
		test.memberShipPage.verfiyRenewalPackageAndMemberPackage(test.memberShipPage.map().get("Original MemberPackage"));
	    test.memberShipPage.clickOnMYDTransferButton();
	}
	
	@Test
	public void Step05_Select_Term_And_New_Package_For_Renewal(){
		test.memberShipPage.verifyTransferPackagePage();
		previousAmount=test.memberShipPage.getBalanceAmount();
		test.memberShipPage.selectTerm(test.memberShipPage.map().get("Years"));
		test.memberShipPage.selectNewPackage(test.memberShipPage.map().get("New MemberPackage?"));
		newAmount=test.memberShipPage.getBalanceAmount();
		test.memberShipPage.verifyChangeInAmountBalance(previousAmount,newAmount);
		test.memberShipPage.clickOnTransferNowButton();
	}
	
	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result)
	{
		test.takescreenshot.takeScreenShotOnException(result);
	}
	
	@AfterClass
	public void Close_Browser_Session() {
		test.closeBrowserWindow();
	}
}
