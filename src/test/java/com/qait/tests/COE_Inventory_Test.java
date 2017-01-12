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

public class COE_Inventory_Test extends BaseTest {

	String app_url_IWEB, individualName;
	String price;
	List<String> customerFullNameList;
	String productName = null, productCode = null;
	private String caseID;

	public COE_Inventory_Test() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "COE_Inventory";
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public COE_Inventory_Test(String caseID) {
		this.caseID = caseID;
	}

	@BeforeClass
	public void OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.homePageIWEB.addValuesInMap("COE_Inventory", caseID);
		app_url_IWEB = getYamlValue("app_url_IWEB");
		System.out.println("App URL Iweb::"+app_url_IWEB);
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

	@Test
	public void Step02_Run_Query_And_Verify_User_Is_On_Individual_Profile_Page() {
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.clickOnTab("Query Individual");
		test.memberShipPage.selectAndRunQuery(getYamlValue("COE_Inventory.queryName"));
		individualName = test.acsAddressValidation.verifyIndividualProfilePage();
		customerFullNameList = test.memberShipPage.getCustomerFullNameAndContactID();
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Individuals | " + individualName);
	}

	@Test
	public void Step03_Click_On_Order_Entry_Button_And_Verify_Centralized_Order_Entry_Page() {
		test.memberShipPage.clickOnOrderEntryIcon();
		test.memberShipPage.verifyCentralizedOrderEntryPage("Centralized Order Entry");
	}

	@Test
	public void Step04_Click_Select_Product_And_Merchandise_Option_and_Verify_Centralized_Order_Entry_Merchandise_Window() {
		test.memberShipPage.clickOnSelectProduct();
		test.memberShipPage.selectRandomProductForCRMInventory();
		productName = test.memberShipPage.getProductNameFromCOEPage();
		productCode = test.memberShipPage.getProductCodeFromCOEPage();
		test.memberShipPage.clickOnSaveAndFinish();
	}

	@Test
	public void Step05_Verify_that_Selected_Item_Is_Added_Into_Line_Items() {
		test.memberShipPage.verifyProductNameInLineItem(productName);
	}

	@Test
	public void Step06_Select_Selenium_Batch_And_Payment_Details_For_CRM_Inventory_And_Verify_Centralized_Order_Entry_page() {
		test.memberShipPage.selectBatchAndPaymentDetailsForCRMInventory(
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.batch"),
				YamlReader.getYamlValue("COE_Inventory.PaymentType"),
				test.homePageIWEB.map().get("paymentmethod?").trim(),
				test.homePageIWEB.map().get("Card Number?").trim(), YamlReader
				.getYamlValue("Acs_CreateMember_IWEB.expireDate"), 
				test.homePageIWEB.map().get("CVV?").trim(),
				YamlReader.getYamlValue("COE_Inventory.checkNumber"));
	}

	@Test
	public void Step07_Click_on_More_tab_And_Select_Invoices_Option_and_Expand_Invoices_Open_Batch() {
		test.individualsPage.navigateToInvoicesMenuOnHoveringMore();
	}

	@Test
	public void Step08_Verify_Invoice_Added_In_Invoices_With_Current_Date_As_Trancation_Date() {
		test.memberShipPage.verifyInvoiceIsAdded(customerFullNameList.get(0).trim());
	}

	@Test
	public void Step09_Verify_Paid_In_Full_Yes_And_Current_Date_As_Transaction_Date_And_Product_Code_On_Invoice_Profile_Page() {
		test.memberShipPage.clickOnStudentMemberName(1);
		test.invoicePage.verifyInvoiceProfile("Yes");
		test.invoicePage.verifyProductCodeInlineItem(productCode, productName);
	}

}
