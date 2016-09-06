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

	String app_url_IWEB, individualName;
	String price,batchName;
	List<String> customerFullNameList;
	String productName = null, productCode = null;
	private String caseID;
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
		test.memberShipPage.enterValuesInCreditPage(
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.batch"),
				YamlReader.getYamlValue("ACS_Create_Manual_Credit_Test.CreditReason"),
				test.homePageIWEB.map().get("paymentmethod?").trim(),
				test.homePageIWEB.map().get("Card Number?").trim(), 
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.expireDate"), 
				test.homePageIWEB.map().get("CVV?").trim(),
				YamlReader.getYamlValue("ACS_Create_Manual_Credit_Test.CreditAmount"),
				YamlReader.getYamlValue("ACS_Create_Manual_Credit_Test.liability_Expense"));
				//YamlReader.getYamlValue("COE_Inventory.checkNumber"));
	}/*
	@Test
	public void Step03_Verify_User_Is_On_Credit_Profile_Page_Click_on_Batch_and_pre_process()
	{
		test.homePageIWEB.verifyUserIsOnHomePage("Accounting | Credit | Credit Profile");
		test.memberShipPage.clickOnBatch(batchName);
		test.memberShipPage.clickOnPreProcessAndWaitToCloseThePopup();
	}
	@Test
	public void Step04_Click_On_pre_process_Navigate_To_Individuals()
	{
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("CRM");
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.clickOnTab("Find Individual");/*
		individualName = test.acsAddressValidation.verifyIndividualProfilePage();
		customerFullNameList = test.memberShipPage.getCustomerFullNameAndContactID();
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Individuals | " + individualName);
		test.memberShipPage.clickOnOrderEntryIcon();
		test.memberShipPage.verifyCentralizedOrderEntryPage("Centralized Order Entry");
		test.memberShipPage.clickOnSelectProduct();
		price = test.memberShipPage.selectRandomProductForCRMInventory();
		productName = test.memberShipPage.getProductNameFromCOEPage();
		productCode = test.memberShipPage.getProductCodeFromCOEPage();
		test.memberShipPage.clickOnSaveAndFinish();
	}*/
}
