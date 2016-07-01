package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;

public class ACS_Void_Invoice_Test {

	TestSessionInitiator test;
	String app_url_IWEB;
	int index;
	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IWEB");
	}
	
	@Test
	public void Step01_Launch_Iweb_Application() {
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));		
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test
	public void Step02_User_Navigated_To_Scarf_Reporting_Page() {
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
		test.homePageIWEB.verifyUserIsOnHomePage("Membership | Overview | Overview and Setup");
	}	
	@Test
	public void Step03_Select_Query_In_Query_Membership_Page(){
		test.homePageIWEB.clickOnSideBarTab("Members");
		test.memberShipPage.clickOnTab("Query Membership");
		test.memberShipPage.selectAndRunQuery(getYamlValue("VoidWithAdjustment.queryName"));
		test.homePageIWEB.verifyUserIsOnHomePage("Membership | Members | Membership Profile");
	}
	
	@Test
	public void Step04_Verify_Payment_And_Balance_Fields_Values(){
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("invoices");
		index=test.memberShipPage.verifyProductUnderDetailsMenu("Regular Member Dues C&EN-Electronic");
		test.memberShipPage.verifyPayment(index);
		test.memberShipPage.verifyBalance(index);
	}
	
	@Test
	public void Step05_User_Navigated_To_Accounting_Invoice_Page_And_Verify_Balance_And_Paid_Fields(){
		test.memberShipPage.clickOnStudentMemberName(index);
		test.homePageIWEB.verifyUserIsOnHomePage("Accounting | Invoice | Accounting Innvoice Profile");
		test.invoicePage.verifyMemberDetails_question("paid in full", "No");
		test.invoicePage.verifyBalanceIsNotNull("balance", 0.00);
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("line items");
		index=test.memberShipPage.verifyProductUnderDetailsMenu("Regular Member Dues C&EN-Electronic");
	}
	
	
	
}
