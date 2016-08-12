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
	List<String> customerFullNameList;
	String productName=null,productCode=null;

	@BeforeClass
	public void OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		// app_url_givingDonate = getYamlValue("app_url_givingDonate");
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
		 customerFullNameList =test.memberShipPage.getCustomerFullNameAndContactID();
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Individuals | " + individualName);
	}

	@Test
	public void Step03_Click_on_order_entry_button_and_verify_Centralized_Order_Entry_Page() {
		test.memberShipPage.clickOnOrderEntryIcon();
		test.memberShipPage.verifyCentralizedOrderEntryPage("Centralized Order Entry");
	}

	@Test
	public void Step04_Click_Select_Product_And_Merchandise_Option_and_verify_Centralized_Order_Entry_Merchandise_window() {

		test.memberShipPage.clickOnSelectProduct();
		test.memberShipPage.selectRandomProduct("merchandise");
		productName=test.memberShipPage.getProductNameFromCOEPage();
		productCode=test.memberShipPage.getProductCodeFromCOEPage();
		test.memberShipPage.clickOnSaveAndFinish();
		
	}

	@Test
	public void Step05_Verify_that_the_selected_item_is_added_into_Line_Items() {
		
		test.memberShipPage.verifyProductNameInLineItem(productName);
	}

	@Test
	public void Step06_Create_batch_named_Selenium_Testing_and_enter_QA_in_Security_Group_and_Verify_Centralized_Order_Entry_page() {
		test.memberShipPage.selectBatchAndPaymentDetails_subscription(
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.batch"),
				YamlReader.getYamlValue("CRM_Inventory.PaymentType"),
				YamlReader.getYamlValue("CRM_Inventory.paymentMethod"), "", "", "",
				YamlReader.getYamlValue("CRM_Inventory.checkNumber"));
	}

	@Test
	public void Step07_Click_on_More_tab_and_Select_Invoices_option_and_Expand_invoices_open_batch() {
		test.individualsPage.navigateToInvoicesMenuOnHoveringMore();
	}

	@Test
	public void Step08_verify_invoice_is_added_in_the_invoices_open_batch_with_trancation_date_is_current_date() {
		test.memberShipPage.verifyInvoiceIsAdded(customerFullNameList.get(0).trim());
		
	}
	
	@Test
	public void step09_verify_Invoice_profile_page_Verify_paid_in_full_is_Yes_trancation_date_with_current_date_and_product_code()
	{
		test.memberShipPage.clickOnStudentMemberName(1);
		test.invoicePage.verifyInvoiceProfile("Yes");
		test.invoicePage.verifyProductCodeInlineItem(productCode);
	}
}
