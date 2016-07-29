package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.utils.YamlReader;
import com.qait.automation.TestSessionInitiator;


public class ACS_MYD_Transfer extends BaseTest{
	
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
		Reporter.log("CASE ID : " + caseID, true);
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}
	
	@Test
	public void Step02_Verify_User_Navigated_To_Membership_Page_On_Clicking_Membership_Under_Modules_Tab(){
		Reporter.log("CASE ID : " + caseID, true);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
		test.homePageIWEB.verifyUserIsOnHomePage("Membership | Overview | Overview and Setup");
	}
	
	@Test
	public void Step03_Select_Query_In_Query_Membership_Page(){
		Reporter.log("CASE ID : " + caseID, true);
		test.homePageIWEB.clickOnSideBarTab("Members");
		test.memberShipPage.clickOnTab("Query Membership");
		test.memberShipPage.selectAndRunQuery(getYamlValue("MYDTransfer.queryName"));
		test.memberShipPage.verifyOueryAskAtRunTimePage();
	    test.memberShipPage.selectMemberPackage(test.memberShipPage.map().get("Original MemberPackage"));
	    test.memberShipPage.clickOnGoButtonAfterPackageSelection();
	}
	
	@Test
	public void Step04_Verify_Membership_Profile_Page(){
		Reporter.log("CASE ID : " + caseID, true);
		test.homePageIWEB.verifyUserIsOnHomePage("Membership | Members | Membership Profile");
		test.memberShipPage.getContactIdOfUser("Customer");
		test.memberShipPage.verifyMemberTypeAndPackage("Regular Member","Active Renewed-No Response");
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("invoices");
		test.memberShipPage.verifyTermEndDateAndStartDateIsEmpty();	
		test.memberShipPage.verfiyMemberPackage(test.memberShipPage.map().get("Original MemberPackage"));
		test.memberShipPage.verifyRenewalPackage(test.memberShipPage.map().get("Original MemberPackage"));
	    test.memberShipPage.clickOnMYDTransferButton();
	}
	
	@Test
	public void Step05_Select_Term_And_New_Package_For_Renewal_And_Verify_Balance_Amount(){
		Reporter.log("CASE ID : " + caseID, true);
		test.memberShipPage.verifyTransferPackagePage();
		previousAmount=test.memberShipPage.getBalanceAmount();
		test.memberShipPage.selectTerm(test.memberShipPage.map().get("Years"));
		test.memberShipPage.selectNewPackage(test.memberShipPage.map().get("New MemberPackage?"));
		newAmount=test.memberShipPage.getBalanceAmount();
		test.memberShipPage.verifyChangeInAmountBalance(previousAmount,newAmount,
				test.memberShipPage.map().get("Original MemberPackage"),test.memberShipPage.map().get("Years"));
		test.memberShipPage.clickOnTransferNowButton();  
	}
	
	@Test
	public void Step06_Verify_Member_Package_And_Changed_Renewal_Package(){
		Reporter.log("CASE ID : " + caseID, true);
		test.memberShipPage.verfiyMemberPackage(test.memberShipPage.map().get("Original MemberPackage"));
		test.memberShipPage.verifyRenewalPackage(test.memberShipPage.map().get("New MemberPackage?"));
		test.memberShipPage.verifyProductPackage(test.memberShipPage.map().get("Product Package?"));
		test.memberShipPage.verifyTermEndDateAndStartDateIsEmpty();	
	}
}
