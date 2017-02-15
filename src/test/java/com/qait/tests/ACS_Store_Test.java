package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;
import com.qait.keywords.YamlInformationProvider;

public class ACS_Store_Test extends BaseTest {

	String app_url_Store;
	String app_url_IWEB;
	String headerName = this.getClass().getSimpleName();
	String invoice_number;
	Map<String, String> mapACSStore = new HashMap<String, String>();
	Map<String, String> mapGetMemberDetailsInStore;
	Map<String, String> mapShippingAddressDetails = new HashMap<String, String>();
	List<String> memberStoreDetails;
	Map<String, Object> userInfo = null;

	public ACS_Store_Test() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "ACS_Store";
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_Store_Test(String caseID) {
		this.caseID = caseID;
	}

	@Test
	public void Step01_TC01_Search_Product_Using_Product_key() {
		mapACSStore = test.homePageIWEB.addValuesInMap("ACS_Store", caseID);
		test.launchApplication(app_url_IWEB);
		test.homePage.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));

		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.clickOnSideBar("Query Individual");
		memberStoreDetails = test.memberShipPage.selectAndRunQueryForMemberOrNonMember(caseID);
	}

	@Test
	public void Step02_TC02_Navigate_To_Shopping_Cart_Page_And_Verify_Product_Details() {
		String Product_id = mapACSStore.get("Product_id").trim();
		test.launchApplication(app_url_Store);
		test.asm_storePage.searchText(toString().valueOf(Product_id));
		test.asm_storePage.verifySearchSuccessfully();
		test.asm_storePage.NavigateToShoppingCartPage(memberStoreDetails.get(1), "password");
		test.asm_storePage.verifyUserIsOnShoppingCartPage();
		mapGetMemberDetailsInStore = test.asm_storePage.verifyProductDetailsOnShoppingCartPage(caseID,
				mapACSStore.get("priceValue?"), mapACSStore.get("discount?"), mapACSStore.get("description?"));

	}

	@Test
	public void Step03_TC03_Navigate_To_Secure_Checkout_Page_And_Get_Prepopulated_Address_Feilds() {

		test.asm_storePage.clickProceedToCheckoutAtBottom();
		mapShippingAddressDetails = test.asm_storePage.getPrepopulatedShippingAddressFeildsAndContinue();
		test.asm_storePage.selectAndEnterNewShippingAddress(mapShippingAddressDetails.get("First Name"),
				mapShippingAddressDetails.get("Last Name"), mapACSStore.get("Address"), mapACSStore.get("City"),
				mapACSStore.get("State"), mapACSStore.get("ZipCode"));

	}

	@Test
	public void Step04_TC04_Fill_Payment_Details_On_Payment_Information_Page() {
		// test.asm_storePage.enterPaymentInformation_ACSStore(
		// mapACSStore.get("Payment_Method"),
		// mapShippingAddressDetails.get("First Name") + " "
		// + mapShippingAddressDetails.get("Last Name"),
		// mapACSStore.get("Payment_Method"),
		// mapACSStore.get("Payment_Method"),
		// mapACSStore.get("Payment_Method"));
		test.asm_storePage.enterPaymentInfo("CardHolderName",
				mapShippingAddressDetails.get("First Name") + " " + mapShippingAddressDetails.get("Last Name"));

		test.asm_storePage.enterPaymentInformation_ACSStoreForAllPaymentTypes(mapACSStore.get("Payment_Method"),
				mapACSStore.get("Visa_Card_Number"), mapACSStore.get("Diners_Card_Number"),
				mapACSStore.get("Reference_Number"), mapACSStore.get("Discover_Card_Number"),
				mapACSStore.get("AMEX_Card_Number"), mapACSStore.get("Expiry_Date"), mapACSStore.get("CVV_Number"),
				mapACSStore.get("Check_Number"));
		test.asm_storePage.verifyApplicationAcceptsDataAndNavigatesToSecureCheckoutPage();
	}

	@Test
	public void Step05_TC05_Verify_Order_Summary_And_Place_The_order() {
		String Shipping = mapACSStore.get("Shipping?");
		String Tax = mapACSStore.get("Tax?");
		String orderTotal = test.asm_storePage.getOrderTotalSummaryAtCheckoutPage();
		test.asm_storePage.verifyOrderSummaryAtCheckoutPage(mapACSStore, orderTotal);
		test.asm_storePage.clickPlaceYourOrder();
		test.asm_storePage.verifyThankyouMessageAfterOrderCompletion(
				YamlReader.getYamlValue("ACS_Store.maxWaitTimeForPrintMessage"), "Print Your Receipt");
	}

	@Test
	public void Step06_TC06_Launch_IWEB_Application_Under_Test() {
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test
	public void Step07_TC07_Navigate_To_Query_Invoice_Page_And_Run_Query() {

		test.homePageIWEB.clickFindForIndividualsSearch();
		test.individualsPage.fillMemberDetailsAndSearch("Record Number", memberStoreDetails.get(0));
		test.homePageIWEB.clickOnSideBarTab("Invoice");
		test.memberShipPage.clickOnSideBar("Query Invoice");
		test.memberShipPage.selectAndRunQuery("Selenium - Newest Invoice for Customer ID");
		test.memberShipPage.enterSingleCustomerIdInRunQuery(memberStoreDetails.get(0));
		test.memberShipPage.clickInvoiceHeading("Transaction Date");
		test.memberShipPage.clickInvoiceHeading("Transaction Date");
		test.memberShipPage.clickOnInvoiceNumber();
	}

	@Test
	public void Step08_TC08_Verify_User_Store_Details_On_Invoice_Profile_Page() {
		mapGetMemberDetailsInStore = test.asm_storePage.verifyBillingAndShippingAddress(caseID);
		test.invoicePage.verifyInvoiceProfile("balance", "0.00");
		test.invoicePage.verifyInvoiceProfile("invoice total", mapGetMemberDetailsInStore.get("Order Total"));
		test.invoicePage.expandDetailsMenu("line items");
		test.invoicePage.verifyInvoiceDetails_LineItems(mapGetMemberDetailsInStore, "priceValue", "quantity", "total",
				"discount", "description");
		test.invoicePage.collapseDetailsMenu("line items");
		test.invoicePage.expandDetailsMenu("shipping");
		test.invoicePage.verifyInvoiceDetails_LineItems(mapGetMemberDetailsInStore, "Shipping");
	}

	@BeforeClass
	public void OpenBrowserWindow() {
		System.out.println("asfseg");
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = YamlReader.getYamlValue("app_url_IWEB");
		app_url_Store = YamlReader.getYamlValue("app_url_Store");

	}

	@BeforeMethod
	public void handleTestMethodName(Method method) {
		test.printMethodName(method.getName());
	}

}
