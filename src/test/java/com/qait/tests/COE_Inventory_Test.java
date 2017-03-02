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
import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class COE_Inventory_Test extends BaseTest {

	String app_url_IWEB, individualName;
	String price;
	List<String> customerFullNameList;
	String productName = null, productCode = null;
	private String batchprefix = "ACS: ";

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
		test.homePage.addValuesInMap("COE_Inventory", caseID);
		app_url_IWEB = getYamlValue("app_url_IWEB");
		System.out.println("App URL Iweb::" + app_url_IWEB);
	}

	@BeforeMethod
	public void handleTestMethodName(Method method) {
		test.printMethodName(method.getName());
	}

	@Test
	public void Step01_Launch_Iweb_Application_And_Verify_User_Is_On_Home_Page() {
		Reporter.log("STEP: Case id : "+caseID,true);
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test(dependsOnMethods = "Step01_Launch_Iweb_Application_And_Verify_User_Is_On_Home_Page")
	public void Step02_Run_Query_And_Verify_User_Is_On_Individual_Profile_Page() {
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.clickOnTab("Query Individual");
		test.memberShipPage.selectAndRunQuery(getYamlValue("COE_Inventory.queryName"));
		individualName = test.acsAddressValidation.verifyIndividualProfilePage();
		customerFullNameList = test.memberShipPage.getCustomerFullNameAndContactID();
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Individuals | " + individualName);
	}

	@Test(dependsOnMethods = "Step02_Run_Query_And_Verify_User_Is_On_Individual_Profile_Page")
	public void Step03_Click_On_Order_Entry_Button_And_Verify_Centralized_Order_Entry_Page() {
		test.memberShipPage.clickOnOrderEntryIcon();
		test.memberShipPage.verifyCentralizedOrderEntryPage("Centralized Order Entry");
	}

	@Test(dependsOnMethods = "Step03_Click_On_Order_Entry_Button_And_Verify_Centralized_Order_Entry_Page")
	public void Step04_Click_Select_Product_And_Merchandise_Option_and_Verify_Centralized_Order_Entry_Merchandise_Window() {
		test.memberShipPage.clickOnSelectProduct();
		test.memberShipPage.selectMerchandise("merchandise");
		test.memberShipPage.enterProductCode(ASCSocietyGenericPage.map().get("Product Code").trim());
		test.memberShipPage.clickOnSearchDisplayNameButton();
		productName = test.memberShipPage.getProductNameFromCOEPage();
		productCode = test.memberShipPage.getProductCodeFromCOEPage();
		test.memberShipPage.clickOnSaveAndFinish();
		// test.memberShipPage.selectRandomProductForCRMInventory();
	}

	@Test(dependsOnMethods = "Step04_Click_Select_Product_And_Merchandise_Option_and_Verify_Centralized_Order_Entry_Merchandise_Window")
	public void Step05_Verify_that_Selected_Item_Is_Added_Into_Line_Items() {
		test.memberShipPage.verifyProductNameInLineItem(productName);
	}

	@Test(dependsOnMethods = "Step05_Verify_that_Selected_Item_Is_Added_Into_Line_Items")
	public void Step06_Select_Selenium_Batch_And_Payment_Details_For_CRM_Inventory_And_Verify_Centralized_Order_Entry_page() {
		/*
		 * test.memberShipPage.selectBatchAndPaymentDetailsForCRMInventory(
		 * YamlReader.getYamlValue("Acs_CreateMember_IWEB.batch"),
		 * YamlReader.getYamlValue("COE_Inventory.PaymentType"),
		 * test.homePageIWEB.map().get("Payment_Method").trim(),test.
		 * homePageIWEB.map().get("Card Number?").trim(), YamlReader
		 * .getYamlValue("Acs_CreateMember_IWEB.expireDate"),
		 * test.homePageIWEB.map().get("CVV?").trim(),
		 * YamlReader.getYamlValue("COE_Inventory.checkNumber"));
		 */
		test.memberShipPage.selectAndAddBatchIFNotPresent(batchprefix + ASCSocietyGenericPage.map().get("Batch_Name?"+System.currentTimeMillis()),
				ASCSocietyGenericPage.map().get("Payment_Type"), ASCSocietyGenericPage.map().get("Payment_Method"));
		test.memberShipPage.fillAllTypeOFPaymentDetails(ASCSocietyGenericPage.map().get("Payment_Method"),
				ASCSocietyGenericPage.map().get("Visa_Card_Number"), ASCSocietyGenericPage.map().get("Diners_Card_Number"),
				ASCSocietyGenericPage.map().get("Reference_Number"), ASCSocietyGenericPage.map().get("Discover_Card_Number"),
				ASCSocietyGenericPage.map().get("AMEX_Card_Number"), ASCSocietyGenericPage.map().get("Expiry_Date"),
				ASCSocietyGenericPage.map().get("CVV_Number"), ASCSocietyGenericPage.map().get("Check_Number"));
		test.memberShipPage.navigateToCRMPageByClickingSaveAndFinish();

	}

	@Test(dependsOnMethods = "Step06_Select_Selenium_Batch_And_Payment_Details_For_CRM_Inventory_And_Verify_Centralized_Order_Entry_page")
	public void Step07_Click_on_More_tab_And_Select_Invoices_Option_and_Expand_Invoices_Open_Batch() {
		test.individualsPage.navigateToInvoicesMenuOnHoveringMore();
	}

	@Test(dependsOnMethods = "Step07_Click_on_More_tab_And_Select_Invoices_Option_and_Expand_Invoices_Open_Batch")
	public void Step08_Verify_Invoice_Added_In_Invoices_With_Current_Date_As_Trancation_Date() {
		test.memberShipPage.verifyInvoiceIsAdded(customerFullNameList.get(0).trim());
	}

	@Test(dependsOnMethods = "Step08_Verify_Invoice_Added_In_Invoices_With_Current_Date_As_Trancation_Date")
	public void Step09_Verify_Paid_In_Full_Yes_And_Current_Date_As_Transaction_Date_And_Product_Code_On_Invoice_Profile_Page() {
		test.memberShipPage.clickOnStudentMemberName(1);
		test.invoicePage.verifyInvoiceProfile("Yes");
		test.invoicePage.verifyProductCodeInlineItem(productCode, productName);
	}

}
