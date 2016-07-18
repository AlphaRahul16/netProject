package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.ArrayList;
import java.util.List;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;
import com.qait.keywords.YamlInformationProvider;

public class ACS_Void_Invoice_Test {

	TestSessionInitiator test;
	String app_url_IWEB;
	int index;
	List<String> productList=new ArrayList<String>();

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
	public void Step02_User_Navigated_To_Membership_Page() {
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
		test.homePageIWEB.verifyUserIsOnHomePage("Membership | Overview | Overview and Setup");
	}	
	@Test
	public void Step03_Select_Query_In_Query_Membership_Page(){
		test.homePageIWEB.clickOnSideBarTab("Members");
		test.memberShipPage.clickOnTab("Query Membership");
		test.memberShipPage.selectAndRunQuery(YamlReader.getYamlValue("VoidWithAdjustment.queryName"));
		test.homePageIWEB.verifyUserIsOnHomePage("Membership | Members | Membership Profile");
	}
	
	@Test
	public void Step04_Verify_Payment_And_Balance_Fields_Values(){
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("invoices");
		index=test.memberShipPage.verifyProductUnderDetailsMenu(YamlReader.getYamlValue("VoidWithAdjustment.prodName"));
		test.memberShipPage.verifyPayment(index);
		test.memberShipPage.verifyBalance(index);
	}
	
	@Test
	public void Step05_User_Navigated_To_Accounting_Invoice_Page_And_Verify_Balance_And_Paid_Fields(){
		test.memberShipPage.clickOnStudentMemberName(index);
		test.homePageIWEB.verifyUserIsOnHomePage("Accounting | Invoice | Accounting Invoice Profile");
		test.invoicePage.verifyMemberDetails_question(YamlReader.getYamlValue("VoidWithAdjustment.memberDetailsQuestion1"), "No");
		test.invoicePage.verifyBalanceIsNotNull(YamlReader.getYamlValue("VoidWithAdjustment.memberDetailsQuestion2"), 0.00);
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("line items");
		test.acsVoidInvoice.verifyProductUnderLineItems(YamlReader.getYamlValue("VoidWithAdjustment.prodName"),5);
		productList=test.acsVoidInvoice.getProductsUnderLineItemsMenu(5);
	}
	
	@Test
	public void Step06_Creation_Of_New_Batch(){
		test.invoicePage.getDataFromInvoiceProfilePage("invoice number");
		test.acsVoidInvoice.clickOnVoidInvoiceButton("void invoice", 4);
		test.acsVoidInvoice.createBatch(1,6,"QA");
		test.acsVoidInvoice.enterActionValues(YamlReader.getYamlValue("VoidWithAdjustment.actionValue"));
		test.acsVoidInvoice.clickOnSaveButton();
	}
	
	@Test
	public void Step07_Verify_Void_Invoice_Message_And_Voided_Line_Items(){
		test.acsVoidInvoice.verifyVoidInvoiceMessage(getYamlValue("VoidWithAdjustment.voidMessage"));
		test.acsVoidInvoice.verifyMessageUnderLineItemsMenu(getYamlValue("VoidWithAdjustment.emptyItemsMessage"));
		test.invoicePage.verifyMemberDetails(YamlReader.getYamlValue("VoidWithAdjustment.memberDetailsQuestion2"),"0.00");
		test.invoicePage.verifyMemberDetails_question(YamlReader.getYamlValue("VoidWithAdjustment.memberDetailsQuestion1"), "Yes");
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("adjusted/voided line items");
		test.acsVoidInvoice.verifyItemsUnderVoidedLineItemsMenu(productList,4);
	}
	
	@Test 
	public void Step08_Verify_User_Is_Not_A_Member(){
		test.memberShipPage.clickOnCustomerName();
		test.acsVoidInvoice.verifyNotAMember("2", "receives member benefits");
		test.acsVoidInvoice.verifyNotAMember("5", "member");
	}
	
	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result)
	{
		test.takescreenshot.takeScreenShotOnException(result);
	}
	
//	@AfterClass(alwaysRun = true)
	public void Close_Test_Session() {
		test.closeBrowserSession();
	}
	
}
