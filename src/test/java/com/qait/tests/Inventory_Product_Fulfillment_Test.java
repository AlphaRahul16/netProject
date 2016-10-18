package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class Inventory_Product_Fulfillment_Test extends BaseTest {

	String app_url_IWEB, individualName;
	String price;
	List<String> customerFullNameList;
	String productName = null, productCode = null;
	private String caseID;

	public Inventory_Product_Fulfillment_Test() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "COE_Inventory";
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public Inventory_Product_Fulfillment_Test(String caseID) {
		this.caseID = caseID;
	}

	@BeforeClass
	public void OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.homePageIWEB.addValuesInMap("COE_Inventory", caseID);
		app_url_IWEB = getYamlValue("app_url_IWEB");
		System.out.println("App URL Iweb::" + app_url_IWEB);
	}
	
	@BeforeMethod
	public void handleTestMethodName(Method method) {
		test.printMethodName(method.getName());
	}

	@Test
	public void Step01_Launch_Iweb_Application_And_Verify_User_Is_On_Home_Page() {
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test(dependsOnMethods="Step01_Launch_Iweb_Application_And_Verify_User_Is_On_Home_Page")
	public void Step02_Select_And_Run_Query_And_Verify_User_Is_On_Individual_Profile_Page() {
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.clickOnTab("Query Individual");
		test.memberShipPage.selectAndRunQuery(getYamlValue("Product_Fulfillment_Inventory.queryName"));
		individualName = test.acsAddressValidation.verifyIndividualProfilePage();
		customerFullNameList = test.memberShipPage.getCustomerFullNameAndContactID();
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Individuals | " + individualName);
	}

	@Test(dependsOnMethods="Step02_Select_And_Run_Query_And_Verify_User_Is_On_Individual_Profile_Page")
	public void Step03_Click_On_Order_Entry_Button_And_Verify_Centralized_Order_Entry_Page() {
		test.memberShipPage.clickOnOrderEntryIcon();
		//test.memberShipPage.verifyCentralizedOrderEntryPage("Centralized Order Entry");
	}

	@Test(dependsOnMethods="Step03_Click_On_Order_Entry_Button_And_Verify_Centralized_Order_Entry_Page")
	public void Step04_Click_Select_Product_And_Merchandise_Option_and_Verify_Centralized_Order_Entry_Merchandise_Window() {
		test.memberShipPage.clickOnSelectProduct();
		test.memberShipPage.selectMerchandise("merchandise");
		test.memberShipPage.enterProductCode(test.homePageIWEB.map().get("Product Code").trim());
		test.memberShipPage.clickOnSearchDisplayNameButton();
		productName = test.memberShipPage.getProductNameFromCOEPage();
		productCode = test.memberShipPage.getProductCodeFromCOEPage();
		test.memberShipPage.clickOnSaveAndFinish();
	}

	@Test(dependsOnMethods="Step04_Click_Select_Product_And_Merchandise_Option_and_Verify_Centralized_Order_Entry_Merchandise_Window")
	public void Step05_Verify_that_Selected_Item_Is_Added_Into_Line_Items() {
		test.memberShipPage.verifyProductNameInLineItem(productName);
	}

	@Test(dependsOnMethods="Step05_Verify_that_Selected_Item_Is_Added_Into_Line_Items")
	public void Step06_Select_Selenium_Batch_And_Payment_Details_For_CRM_Inventory_And_Verify_Centralized_Order_Entry_page() {
		test.memberShipPage.selectBatchAndPaymentDetailsForCRMInventory(
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.batch"),
				YamlReader.getYamlValue("COE_Inventory.PaymentType"),
				test.homePageIWEB.map().get("paymentmethod?").trim(),
				test.homePageIWEB.map().get("Card Number?").trim(),
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.expireDate"), test.homePageIWEB.map().get("CVV?").trim(),
				YamlReader.getYamlValue("COE_Inventory.checkNumber"));
	}

	@Test(dependsOnMethods="Step06_Select_Selenium_Batch_And_Payment_Details_For_CRM_Inventory_And_Verify_Centralized_Order_Entry_page")
	public void Step07_Click_on_More_tab_And_Select_Invoices_Option_and_Expand_Invoices_Open_Batch() {
		test.individualsPage.navigateToInvoicesMenuOnHoveringMore();
	}

	@Test(dependsOnMethods="Step07_Click_on_More_tab_And_Select_Invoices_Option_and_Expand_Invoices_Open_Batch")
	public void Step08_Verify_Invoice_Added_In_Invoices_With_Current_Date_As_Trancation_Date() {
		test.memberShipPage.verifyInvoiceIsAdded(customerFullNameList.get(0).trim());
	}

	@Test(dependsOnMethods="Step08_Verify_Invoice_Added_In_Invoices_With_Current_Date_As_Trancation_Date")
	public void Step09_Navigate_To_Inventory_Module_And_Click_On_Overview_Under_Fulfillment_Orders_And_Verify_User_Is_On_Fulfillment_Orders_Page() {
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Inventory");
		test.homePageIWEB.verifyUserIsOnHomePage("Inventory | Overview | Overview and Setup");
		test.homePageIWEB.clickOnFulfillmentOrdersTab();
		test.homePageIWEB.verifyUserIsOnHomePage("Inventory | Fulfillment Orders | Overview and Setup");
	}

	@Test(dependsOnMethods="Step09_Navigate_To_Inventory_Module_And_Click_On_Overview_Under_Fulfillment_Orders_And_Verify_User_Is_On_Fulfillment_Orders_Page")
	public void Step10_Click_On_Process_Fullfill_Orders_Link_And_Verify_User_Is_On_Order_Fulfillment_With_Packing_Slips_Page() {
		test.inventoryIweb.clickOnProcessFulfillOrdersLink();
		test.homePageIWEB.verifyUserIsOnHomePage("Inventory | Fulfillment | Order Fulfillment with Packing Slips");
	}

	@Test(dependsOnMethods="Step10_Click_On_Process_Fullfill_Orders_Link_And_Verify_User_Is_On_Order_Fulfillment_With_Packing_Slips_Page")
	public void Step11_Click_On_Search_Button_On_Entering_Invoice_Start_And_End_Date_And_Verify_Sold_Product_Is_Checked_By_Default_Under_Select_Orders_To_Fulfill() {
		test.inventoryIweb.enterInvoiceDateFromAndToAndClickOnSearchButton();
		test.inventoryIweb.verifySoldProductIsCheckedByDefaultUnderSelectOrdersToFulfill(productName);
	}
	
	@Test(dependsOnMethods="Step11_Click_On_Search_Button_On_Entering_Invoice_Start_And_End_Date_And_Verify_Sold_Product_Is_Checked_By_Default_Under_Select_Orders_To_Fulfill")
	public void Step12_Click_On_Process_Selected_Items_Button_To_Confirm_Orders_And_Verify_Fulfillment_Reports(){
		test.inventoryIweb.verifyFulfillmentReportsOnClickingProcessSelectedItems();
	}
	
	@Test(dependsOnMethods="Step12_Click_On_Process_Selected_Items_Button_To_Confirm_Orders_And_Verify_Fulfillment_Reports")
	public void Step13_Click_On_Continue_Button_And_Verify_Fulfillment_Reports_Page(){
		test.inventoryIweb.verifyFulfillmentGroupProfilePage(productName);
	}
	
	@Test(dependsOnMethods="Step13_Click_On_Continue_Button_And_Verify_Fulfillment_Reports_Page")
	public void Step14_Click_On_Shipped_Button_And_Verify_Fulfillment_Group_Profile_Page(){
		test.inventoryIweb.VerifyFulfillmentGroupProfilePageOnClickingShippedButton();
		test.homePageIWEB.verifyUserIsOnHomePage("Inventory | Fulfillment | Fulfillment Group Profile");
		//test.inventoryIweb.verifyProductNameUnderLineItems(productName);
	}
	
	@Test(dependsOnMethods="Step14_Click_On_Shipped_Button_And_Verify_Fulfillment_Group_Profile_Page")
	public void Step15_Click_On_Icon_ACS_Fulfillment_Reports_And_Verify_Alert_Message(){
		test.inventoryIweb.verifyAlertMessageOnClickingACSFulfillmentReportsIcon();
	}

}
