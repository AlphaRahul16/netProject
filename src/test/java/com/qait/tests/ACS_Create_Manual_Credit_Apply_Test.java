package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;
import java.util.List;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_Create_Manual_Credit_Apply_Test extends BaseTest {

	String app_url_IWEB, individualName, customerId;
	String batchName;
	List<String> customerFullNameList;
	String productName = null, productCode = null;
	private String caseID, netBalance;

	public ACS_Create_Manual_Credit_Apply_Test() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "CreditApply";
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_Create_Manual_Credit_Apply_Test(String caseID) {
		this.caseID = caseID;
	}

	@BeforeMethod
	public void handleTestMethodName(Method method) {
		test.printMethodName(method.getName());
	}

	@BeforeClass
	public void OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.homePageIWEB.addValuesInMap("CreditApply", caseID);
		app_url_IWEB = getYamlValue("app_url_IWEB");
		Reporter.log("App URL Iweb:: " + app_url_IWEB + "\n");
	}

	@Test
	public void Step00_CreateMember_As_A_Prerequisite() {
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
		test.homePageIWEB.clickOnAddIndividual();
		test.addMember.enterMemberDetailsInAddIndividual();
		customerFullNameList = test.memberShipPage.getCustomerFullNameAndContactID();
		// test.memberShipPage.getIndividualFullNameForAwardsNomination();
	}

	@Test
	public void Step01_Launch_Iweb_Application_And_Navigate_To_Add_Credit() {
		// test.homePageIWEB.clickOnModuleTab();
		// test.homePageIWEB.clickOnTab("CRM");
		// test.homePageIWEB.addValuesInMap("CreditApply", caseID);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Accounting");
		test.homePageIWEB.verifyUserIsOnHomePage("Accounting | Overview | Overview and Setup");
		test.homePageIWEB.clickOnSideBarTab("Credit");
		test.homePageIWEB.clickOnTab("Add Credit");

	}

	@Test(dependsOnMethods = "Step01_Launch_Iweb_Application_And_Navigate_To_Add_Credit")
	public void Step02_Select_A_Customer_Create_A_Batch_Enter_Values_In_Credit_Page() {
		test.memberShipPage.selectMemberByContactID();
		test.memberShipPage.clickOnSearchButton();
		// test.memberShipPage.selectRandomMemberByAscendingHeader("Mailing
		// Label","txt_tableRow");
		test.memberShipPage.enterValuesInCreditPage(YamlReader.getYamlValue("Acs_CreateMember_IWEB.batch"),
				YamlReader.getYamlValue("ACS_Create_Manual_Credit_Test.CreditReason"),
				test.homePageIWEB.map().get("paymentmethod?").trim(),
				test.homePageIWEB.map().get("Card Number?").trim(),
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.expireDate"), test.homePageIWEB.map().get("CVV?").trim(),
				YamlReader.getYamlValue("ACS_Create_Manual_Credit_Test.CreditAmount"),
				YamlReader.getYamlValue("ACS_Create_Manual_Credit_Test.liability_Expense"));
	}

	@Test(dependsOnMethods = "Step02_Select_Random_Customer_Create_A_Batch_Enter_Values_In_Credit_Page")
	public void Step03_Verify_User_Is_On_Credit_Amount_Page_And_Click_on_Batch_and_pre_process_Button() {
		test.homePageIWEB.verifyUserIsOnHomePage("Accounting | Credit | Credit Profile");
		test.memberShipPage.verifyCreditAmount(YamlReader.getYamlValue("ACS_Create_Manual_Credit_Test.CreditAmount"));
		batchName = test.memberShipPage.clickOnBatch();
		test.memberShipPage.clickOnPreProcessAndWaitToCloseThePopup();
	}

	@Test(dependsOnMethods = "Step03_Verify_User_Is_On_Credit_Amount_Page_And_Click_on_Batch_and_pre_process_Button")
	public void Step04_Navigate_To_Individuals_And_Verify_Individual_Profile_Page() {
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("CRM");
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.clickOnTab("Find Individual");
		test.individualsPage.fillMemberDetailsAndSearch("Record Number", customerFullNameList.get(1));
		individualName = test.acsAddressValidation.verifyIndividualProfilePage();
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Individuals | " + individualName);

	}

	@Test(dependsOnMethods = "Step04_Navigate_To_Individuals_And_Verify_Individual_Profile_Page")
	public void Step05_Click_On_Order_Entry_Button_And_Verify_Centralized_Order_Entry_Page() {
		test.memberShipPage.clickOnOrderEntryIcon();
		test.memberShipPage.verifyCentralizedOrderEntryPage("Centralized Order Entry");
	}

	@Test(dependsOnMethods = "Step05_Click_On_Order_Entry_Button_And_Verify_Centralized_Order_Entry_Page")
	public void Step06_Click_On_Select_Product_And_Merchandise_Option_and_Verify_Centralized_Order_Entry_Merchandise_Window() {
		test.memberShipPage.clickOnSelectProduct();
		test.memberShipPage.selectRandomProductForCRMInventory();
		productName = test.memberShipPage.getProductNameFromCOEPage();
		productCode = test.memberShipPage.getProductCodeFromCOEPage();
		test.memberShipPage.clickOnSaveAndFinish();
	}

	@Test(dependsOnMethods = "Step06_Click_On_Select_Product_And_Merchandise_Option_and_Verify_Centralized_Order_Entry_Merchandise_Window")
	public void Step07_Verify_that_Selected_Product_Is_Added_Into_Line_Items_And_Credit_Available_On_COE() {
		test.memberShipPage.verifyProductNameInLineItem(productName);
		test.memberShipPage
				.verifyCreditAvailableOnCOE(YamlReader.getYamlValue("ACS_Create_Manual_Credit_Test.CreditAmount"));
	}

	@Test(dependsOnMethods = "Step07_Verify_that_Selected_Product_Is_Added_Into_Line_Items_And_Credit_Available_On_COE")
	public void Step08_Click_On_net_Credit_Verify_NetForumPopUp_And_Enter_values_In_Amount_to_apply() {
		test.memberShipPage.clickOnNetCredit(productName);
		netBalance = test.memberShipPage.verifyNetForumPopUp();
		test.memberShipPage.enterValuesInAmountToApply(netBalance, "amount to apply");

	}

	@Test(dependsOnMethods = "Step08_Click_On_net_Credit_Verify_NetForumPopUp_And_Enter_values_In_Amount_to_apply")
	public void Step09_Verify_Net_balance_Create_New_batch_And_Submit_Payment_Details() {
		test.memberShipPage.verifyNetBalanceOnCOE(netBalance);
		test.memberShipPage.selectBatchAndPaymentDetailsForCRMInventory(
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.batch"),
				YamlReader.getYamlValue("ACS_Create_Manual_Credit_Test.paymentType"),
				test.homePageIWEB.map().get("paymentmethod?").trim(),
				test.homePageIWEB.map().get("Card Number?").trim(),
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.expireDate"), test.homePageIWEB.map().get("CVV?").trim(),
				"");
	}

	@Test(dependsOnMethods = "Step09_Verify_Net_balance_Create_New_batch_And_Submit_Payment_Details")
	public void Step10_Click_on_more_tab_Select_Other_actg_and_verify_credit_available_information_Under_Credits_Child_Form() {
		test.individualsPage.clickOnMoreAndSelectOtherActg("Other Actg");
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("credits");
		test.memberShipPage.verifyCreditAvailable(YamlReader.getYamlValue("ACS_Create_Manual_Credit_Test.CreditAmount"),
				netBalance, batchName);
	}

}
