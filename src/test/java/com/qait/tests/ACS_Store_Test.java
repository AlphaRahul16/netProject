package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ser.SerializerCache.TypeKey;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.report.ResultsIT;
import com.qait.automation.utils.YamlReader;
import com.qait.keywords.YamlInformationProvider;

public class ACS_Store_Test {
	TestSessionInitiator test;
	String app_url_Store;
	String app_url_IWEB;
	String headerName = this.getClass().getSimpleName();
	YamlInformationProvider getACSStore;
	YamlInformationProvider getStoreCaseId;
	String invoice_number;
	Map<String, Object> mapACSStore;
	Map<String, String> mapGetMemberDetailsInStore;
	Map<String, String> mapShippingAddressDetails = new HashMap<String, String>();
	List<String> memberStoreDetails;
	Map<String, Object> userInfo = null;

	ACS_Store_Test(Map<String, Object> usereInfoMap) {
		this.userInfo = usereInfoMap;
		getStoreCaseId = new YamlInformationProvider(usereInfoMap);
	}

	@Test
	public void Step01_TC01_Search_Product_Using_Product_key() {
		String caseId = getStoreCaseId.get_ACSStoreCaseId("CASEID");
		test.navigateToIWEBUrlOnNewBrowserTab(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(
				getACSStore.getAuthenticationInfo("userName"),
				getACSStore.getAuthenticationInfo("password"));
		test.homePageIWEB
				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.clickOnSideBar("Query Individual");
		memberStoreDetails = test.memberShipPage
				.selectAndRunQueryForMemberOrNonMember(caseId);
	}

	@Test
	public void Step02_TC02_Navigate_To_Shopping_Cart_Page_And_Verify_Product_Details() {
		String caseId = getStoreCaseId.get_ACSStoreCaseId("CASEID");
		String Product_id = test.homePageIWEB.getACS_Store_SheetValue(caseId,
				"Product_id");
		test.launchApplication(app_url_Store);
		test.asm_storePage.searchText(Product_id);
		test.asm_storePage.verifySearchSuccessfully();
		test.asm_storePage.NavigateToShoppingCartPage(
				memberStoreDetails.get(1),
				getACSStore.getACSStoreInfo("password"));
		test.asm_storePage.verifyUserIsOnShoppingCartPage();
		mapGetMemberDetailsInStore = test.asm_storePage
				.verifyProductDetailsOnShoppingCartPage(caseId, "priceValue",
						"discount", "description");

	}

	@Test
	public void Step03_TC03_Navigate_To_Secure_Checkout_Page_And_Get_Prepopulated_Address_Feilds() {
		String caseId = getStoreCaseId.get_ACSStoreCaseId("CASEID");
		String Address = test.homePageIWEB.getACS_Store_SheetValue(caseId,
				"Address");
		String City = test.homePageIWEB.getACS_Store_SheetValue(caseId, "City");
		String ZipCode = test.homePageIWEB.getACS_Store_SheetValue(caseId,
				"ZipCode");
		String State = test.homePageIWEB.getACS_Store_SheetValue(caseId,
				"State");

		test.asm_storePage.clickProceedToCheckoutAtBottom();
		mapShippingAddressDetails = test.asm_storePage
				.getPrepopulatedShippingAddressFeildsAndContinue();
		test.asm_storePage.selectAndEnterNewShippingAddress(
				mapShippingAddressDetails.get("First Name"),
				mapShippingAddressDetails.get("Last Name"), Address, City,
				State, ZipCode);

	}

	@Test
	public void Step04_TC04_Fill_Payment_Details_On_Payment_Information_Page() {
		test.asm_storePage.enterPaymentInformation_ACSStore(
				getACSStore.getCreditCardInfo("Type"),
				mapShippingAddressDetails.get("First Name") + " "
						+ mapShippingAddressDetails.get("Last Name"),
				getACSStore.getCreditCardInfo("Number"),
				getACSStore.getCreditCardInfo("CreditCardExpiration"),
				getACSStore.getCreditCardInfo("cvv-number"));
		test.asm_storePage
				.verifyApplicationAcceptsDataAndNavigatesToSecureCheckoutPage();
	}

	@Test
	public void Step05_TC05_Verify_Order_Summary_And_Place_The_order() {
		String caseId = getStoreCaseId.get_ACSStoreCaseId("CASEID");
		String Shipping = test.homePageIWEB.getACS_Store_SheetValue(caseId,
				"Shipping?");
		String Tax = test.homePageIWEB.getACS_Store_SheetValue(caseId, "Tax?");
		String orderTotal = test.asm_storePage
				.getOrderTotalSummaryAtCheckoutPage();
		test.asm_storePage.verifyOrderSummaryAtCheckoutPage(caseId, Shipping,
				Tax, orderTotal);
		test.asm_storePage.clickPlaceYourOrder();
		test.asm_storePage.verifyThankyouMessageAfterOrderCompletion(
				getACSStore.getACSStoreInfo("maxWaitTimeForPrintMessage"),
				"Print Your Receipt");
	}

	@Test
	public void Step06_TC06_Launch_IWEB_Application_Under_Test() {
		test.navigateToIWEBUrlOnNewBrowserTab(app_url_IWEB);
		test.homePageIWEB
				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test
	public void Step07_TC07_Navigate_To_Query_Invoice_Page_And_Run_Query() {

		test.homePageIWEB.clickFindForIndividualsSearch();
		test.individualsPage.fillMemberDetailsAndSearch("Record Number",
				memberStoreDetails.get(0));
		test.homePageIWEB.clickOnSideBarTab("Invoice");
		test.memberShipPage.clickOnSideBar("Query Invoice");
		test.memberShipPage
				.selectAndRunQuery("Selenium - Newest Invoice for Customer ID");
		test.memberShipPage.enterSingleCustomerIdInRunQuery(memberStoreDetails
				.get(0));
		test.memberShipPage.clickInvoiceHeading("Transaction Date");
		test.memberShipPage.clickInvoiceHeading("Transaction Date");
		test.memberShipPage.clickOnInvoiceNumber();
	}

	@Test
	public void Step08_TC08_Verify_User_Store_Details_On_Invoice_Profile_Page() {
		String caseId = getStoreCaseId.get_ACSStoreCaseId("CASEID");
		mapGetMemberDetailsInStore = test.asm_storePage
				.verifyBillingAndShippingAddress(caseId);
		test.invoicePage.verifyInvoiceProfile("balance", "0.00");
		test.invoicePage.verifyInvoiceProfile("invoice total",
				mapGetMemberDetailsInStore.get("Order Total"));
		test.invoicePage.expandDetailsMenu("line items");
		test.invoicePage.verifyInvoiceDetails_LineItems(
				mapGetMemberDetailsInStore, "priceValue", "quantity", "total",
				"discount", "description");
		test.invoicePage.collapseDetailsMenu("line items");
		test.invoicePage.expandDetailsMenu("shipping");
		test.invoicePage.verifyInvoiceDetails_LineItems(
				mapGetMemberDetailsInStore, "Shipping");
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult e) {
		test.takescreenshot.takeScreenShotOnException(e);
	}

	@AfterClass
	public void Close_Browser_Session() {
		test.closeBrowserSession();
	}

	@BeforeClass
	public void OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		mapACSStore = YamlReader.getYamlValues("ACS_Store");
		app_url_IWEB = getYamlValue("app_url_IWEB");
		getACSStore = new YamlInformationProvider(mapACSStore);
		app_url_Store = getYamlValue("app_url_Store");

	}

}
