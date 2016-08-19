package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class CRM_Inventory_Test extends BaseTest {

	String app_url_IWEB, individualName;
	String price;
	List<String> customerFullNameList;
	String productName = null, productCode = null;

	@BeforeClass
	public void OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IWEB");
	}

	@Test
	public void Step01_Launch_Iweb_Application_And_Verify_User_Is_On_Home_Page() {
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test
	public void Step02_Select_And_Run_Query_And_Verify_User_Is_On_Individual_Profile_Page() {
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.clickOnTab("Query Individual");
		test.memberShipPage.selectAndRunQuery(getYamlValue("CRM_Inventory.queryName"));
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
		price = test.memberShipPage.selectRandomProductForCRMInventory();
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
				YamlReader.getYamlValue("CRM_Inventory.PaymentType"),
				YamlReader.getYamlValue("CRM_Inventory.paymentMethod"), "", "", "",
				YamlReader.getYamlValue("CRM_Inventory.checkNumber"), price);
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
