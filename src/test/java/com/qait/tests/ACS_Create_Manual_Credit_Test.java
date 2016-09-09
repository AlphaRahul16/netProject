package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_Create_Manual_Credit_Test extends BaseTest {

	String app_url_IWEB, individualName,customerId;
	String price,batchName;
	List<String> customerFullNameList;
	String productName = null, productCode = null;
	private String caseID,netBalance;
	public ACS_Create_Manual_Credit_Test()
	{
		com.qait.tests.DataProvider_FactoryClass.sheetName = "CreditApply";
	}
	
	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public  ACS_Create_Manual_Credit_Test(String caseID) {
		this.caseID = caseID;
	}
	
	@BeforeClass
	public void OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.homePageIWEB.addValuesInMap("CreditApply", caseID);
		app_url_IWEB = getYamlValue("app_url_IWEB");
		System.out.println("App URL Iweb:: "+app_url_IWEB);
	}
	
	@Test
	public void Step01_Launch_Iweb_Application_And_Navigate_To_Add_Credit() {
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Accounting");
		test.homePageIWEB.verifyUserIsOnHomePage("Accounting | Overview | Overview and Setup");
		test.homePageIWEB.clickOnSideBarTab("Credit");
		test.homePageIWEB.clickOnTab("Add Credit");
		
	}
	
	@Test
	public void Step02_Select_Random_Customer_Create_A_Batch_Enter_Values_In_Credit_Amount_And_Credit_Reason()
	{
		test.memberShipPage.clickOnSearchButton();
		test.memberShipPage.selectRandomUserOnAscendingHeader("Sort Name");
		customerId=test.memberShipPage.enterValuesInCreditPage(
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.batch"),
				YamlReader.getYamlValue("ACS_Create_Manual_Credit_Test.CreditReason"),
				test.homePageIWEB.map().get("paymentmethod?").trim(),
				test.homePageIWEB.map().get("Card Number?").trim(), 
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.expireDate"), 
				test.homePageIWEB.map().get("CVV?").trim(),
				YamlReader.getYamlValue("ACS_Create_Manual_Credit_Test.CreditAmount"),
				YamlReader.getYamlValue("ACS_Create_Manual_Credit_Test.liability_Expense"));
				//YamlReader.getYamlValue("COE_Inventory.checkNumber"));
	}
	@Test
	public void Step03_Verify_User_Is_On_Credit_Profile_Page_Click_on_Batch_and_pre_process()
	{
		test.homePageIWEB.verifyUserIsOnHomePage("Accounting | Credit | Credit Profile");
		test.memberShipPage.verifyCreditAmount(YamlReader.getYamlValue("ACS_Create_Manual_Credit_Test.CreditAmount"));
		test.memberShipPage.clickOnBatch();
		test.memberShipPage.clickOnPreProcessAndWaitToCloseThePopup();
	}
	@Test
	public void Step04_Navigate_To_Individualsand_Verify_Individual_Profile_Page()
	{
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("CRM");
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.clickOnTab("Find Individual");
		test.individualsPage.fillMemberDetailsAndSearch("Record Number", customerId);
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Individuals | " + individualName);
		
	}
	@Test
	public void Step05_Click_On_Order_Entry_Button_And_Verify_Centralized_Order_Entry_Page() {
		
			test.memberShipPage.clickOnOrderEntryIcon();
			test.memberShipPage.verifyCentralizedOrderEntryPage("Centralized Order Entry");
	}

	@Test
	public void Step06_Click_Select_Product_And_Merchandise_Option_and_Verify_Centralized_Order_Entry_Merchandise_Window() {
		
			test.memberShipPage.clickOnSelectProduct();
			price = test.memberShipPage.selectRandomProductForCRMInventory();
			productName = test.memberShipPage.getProductNameFromCOEPage();
			productCode = test.memberShipPage.getProductCodeFromCOEPage();
			test.memberShipPage.clickOnSaveAndFinish();
	}
	@Test
	public void Step07_Verify_that_Selected_Item_Is_Added_Into_Line_Items_and_credit_available () {
			test.memberShipPage.verifyProductNameInLineItem(productName);
			test.memberShipPage.verifyCreditAvailable(YamlReader.getYamlValue("ACS_Create_Manual_Credit_Test.CreditAmount")); 
	}
	
	@Test
	public void Step08_Click_On_net_Credit_in_line_items_And_Enter_values_In_Amount_to_apply()
	{
		test.memberShipPage.clickOnNetCredit(productName);
		netBalance=test.memberShipPage.verifyNetForumPopUp();
		//test.memberShipPage.enterValuesInAmountToApply(netBalance,"amount to apply");
		
	}/*
	@Test
	public void Step09_Verify_Net_balance_And_net_payment_Create_New_batch_Select_prepaid_from_dropdown_type_and_Verify_CRM_Individual_Profile_page()
	{
		test.memberShipPage.verifyNetBalance(YamlReader.getYamlValue("Acs_CreateMember_IWEB.batch"),
				YamlReader.getYamlValue("ACS_Create_Manual_Credit_Test.CreditReason"),
				test.homePageIWEB.map().get("paymentmethod?").trim(),
				test.homePageIWEB.map().get("Card Number?").trim(), 
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.expireDate"), 
				test.homePageIWEB.map().get("CVV?").trim());
				
	}
	
	/*@Test
	public void Step10_Click_on_more_tab_Select_Other_actg_Expand_credits_child_form_and_verify_credit_available_information()
	{
		test.memberShipPage.clickOnMoreAndSelectOtherActg();
	}
	*/
	
	
	
	
	
	
}
