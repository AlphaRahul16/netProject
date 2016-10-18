package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;


public class ACS_MYD_Transfer extends BaseTest{
	
	String app_url_IWEB;
	private String caseID;
	double previousAmount, newAmount;
	
	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IWEB");
	}
	@BeforeMethod
	public void handleTestMethodName(Method method) {
		test.printMethodName(method.getName());
			if (caseID != null) {
				Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
			}
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
	
	@Test(dependsOnMethods="Step01_Launch_Iweb_Application")
	public void Step02_Verify_User_Navigated_To_Membership_Page_On_Clicking_Membership_Under_Modules_Tab(){
		 
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
		test.homePageIWEB.verifyUserIsOnHomePage("Membership | Overview | Overview and Setup");
	}
	
	@Test(dependsOnMethods="Step02_Verify_User_Navigated_To_Membership_Page_On_Clicking_Membership_Under_Modules_Tab")
	public void Step03_Select_Query_In_Query_Membership_Page(){
		 
		test.homePageIWEB.clickOnSideBarTab("Members");
		test.memberShipPage.clickOnTab("Query Membership");
		test.memberShipPage.selectAndRunQuery(getYamlValue("MYDTransfer.queryName"));
		test.memberShipPage.verifyOueryAskAtRunTimePage();
	    test.memberShipPage.selectMemberPackage(test.memberShipPage.map().get("Original MemberPackage"));
	    test.memberShipPage.clickOnGoButtonAfterPackageSelection();
	}
	
	@Test(dependsOnMethods="Step03_Select_Query_In_Query_Membership_Page")
	public void Step04_Verify_Membership_Profile_Page(){
		 
		test.homePageIWEB.verifyUserIsOnHomePage("Membership | Members | Membership Profile");
		test.memberShipPage.getContactIdOfUser("Customer");
		test.memberShipPage.verifyMemberTypeAndPackage("Regular Member","Active Renewed-No Response");
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("invoices");
		test.memberShipPage.verifyTermEndDateAndStartDateIsEmpty();	
		test.memberShipPage.verfiyMemberPackage(test.memberShipPage.map().get("Original MemberPackage"));
		test.memberShipPage.verifyRenewalPackage(test.memberShipPage.map().get("Original MemberPackage"));
	    test.memberShipPage.clickOnMYDTransferButton();
	}
	
	@Test(dependsOnMethods="Step04_Verify_Membership_Profile_Page")
	public void Step05_Select_Term_And_New_Package_For_Renewal_And_Verify_Balance_Amount(){
		 
		test.memberShipPage.verifyTransferPackagePage();
		previousAmount=test.memberShipPage.getBalanceAmount();
		test.memberShipPage.selectTerm(test.memberShipPage.map().get("Years"));
		test.memberShipPage.selectNewPackage(test.memberShipPage.map().get("New MemberPackage?"));
		newAmount=test.memberShipPage.getBalanceAmount();
		test.memberShipPage.verifyChangeInAmountBalance(previousAmount,newAmount,
				test.memberShipPage.map().get("Original MemberPackage"),test.memberShipPage.map().get("Years"));
		test.memberShipPage.clickOnTransferNowButton();  
	}
	
	@Test(dependsOnMethods="Step05_Select_Term_And_New_Package_For_Renewal_And_Verify_Balance_Amount")
	public void Step06_Verify_Member_Package_And_Changed_Renewal_Package(){
		 
		test.memberShipPage.verfiyMemberPackage(test.memberShipPage.map().get("Original MemberPackage"));
		test.memberShipPage.verifyRenewalPackage(test.memberShipPage.map().get("New MemberPackage?"));
		test.memberShipPage.verifyProductPackage(test.memberShipPage.map().get("Product Package?"));
		test.memberShipPage.verifyTermEndDateAndStartDateIsEmpty();	
	}
}
