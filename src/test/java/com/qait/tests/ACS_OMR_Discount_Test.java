package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_OMR_Discount_Test extends BaseTest{
	String app_url_IWEB, app_url_OMR;
	static String sheetname;
	private String caseID, invoiceNumber;
	Map<String, String> mapOMRDiscount = new HashMap<String, String>();
	private List<String> memDetails;
	Map<String, String> mapRenewedProductDetails;
	String[] ProductPrice;
	int expectedDiscount;

	public ACS_OMR_Discount_Test() {
		sheetname = com.qait.tests.DataProvider_FactoryClass.sheetName = "OMR_Discount";
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_OMR_Discount_Test(String caseID) {
		this.caseID = caseID;
	}
	
	@BeforeClass
	public void open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_OMR = getYamlValue("app_url_OMR");
		app_url_IWEB = getYamlValue("app_url_IWEB");
		test.launchApplication(app_url_IWEB);
		test.homePage.enterAuthentication(
				YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		System.out.println("Datasheet Name is "+sheetname);
	}
	
	@Test
	public void Step01_Launch_IWEB_Application_And_Navigate_To_Find_Members_Tab() {
		mapOMRDiscount = test.homePageIWEB.addValuesInMap(sheetname, caseID);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
		test.homePageIWEB.clickOnSideBarTab("Members");
	}
	
	@Test
	public void Step02_Select_Valid_User_For_Renewal_And_Verify_Term_Start_And_End_Dates_Is_Empty() {
		test.memberShipPage.selectValidUserForRenewalAccordingToCountry(mapOMRDiscount);
		ProductPrice=test.individualsPage.verifyYearForOMRDiscountByPrice(mapOMRDiscount);
		
		
	}
	
	@Test
	public void Step03_Verify_Payment_Status_And_Invoice_Details_Before_Renewal() {
	
		test.individualsPage.clickGotoRecordForRenewal();
		invoiceNumber = test.invoicePage.verifyInvoiceDetailsBeforeRenewal();
		test.invoicePage.expandDetailsMenu("line items");
		test.invoicePage.clickApplyDiscountButton();
		test.individualsPage.verifyOverrideStatusForOMRDiscount(ProductPrice[0]);
		expectedDiscount=test.individualsPage.verifyDiscountedPriceFortheRecord(mapOMRDiscount.get("Discount_Percentage?"),ProductPrice);

	}
	
	@Test
	public void Step04_Navigate_to_Membership_Page_And_Fetch_Member_Details() {
		test.memberShipPage.clickOnCustomerNameAndNavigateToMembershipPage();
		memDetails = test.memberShipPage.getCustomerFullNameBasedOnInvoice(invoiceNumber);
	}
	
	@Test
	public void Step05_launch_Eweb_Renewal_Application_And_Login_With_Valid_Credentials() {
		test.launchApplication(app_url_OMR);
		test.asm_OMR.selectNoIfRegularToEmeritusPromptAppears();
		test.asm_OMR.loginIntoOMRApplicationForDiscount(mapOMRDiscount, memDetails);
		test.asm_OMR.OMRLogo("Online Membership Renewal");
	}

	@Test
	public void Step06_Verify_Member_Can_Renew_For_Multiple_Years_After_Selecting_Currency_As_INR() {
		test.asm_OMR.FillRequiredDetailsForStudentMember(mapOMRDiscount);
		test.asm_OMR.verifyDiscountedPriceIsDisplayedOnOMREweb(ProductPrice[0],expectedDiscount,ProductPrice[1]);
		mapRenewedProductDetails = test.asm_OMR
				.addMembershipsForRegularMember(mapOMRDiscount);
		test.asm_OMR.verifyMemberCanRenewForMultipleYearsOrNot(mapOMRDiscount.get("Member_Status?"));

	}
	
	@Test
	public void Step07_Submit_Payment_Details_And_Verify_Renewal_Summary_On_CheckoutPage() {
		test.asm_OMR.submitPaymentDetails(mapOMRDiscount.get("CreditCard_Type"),
				(memDetails.get(0).split(" ")[1] + " " + memDetails.get(0)
						.split(" ")[0]), mapOMRDiscount.get("Visa_Card_Number"), mapOMRDiscount.get("Diners_Card_Number"),
				mapOMRDiscount.get("Discover_Card_Number"),mapOMRDiscount.get("AMEX_Card_Number"),
				mapOMRDiscount.get("CreditCard_CVV_Number"), mapOMRDiscount
						.get("CreditCardExpiration_Month"), mapOMRDiscount
						.get("CreditCardExpiration_Year"));

		test.asm_OMR
				.verifyRenewedProductsSummaryOnCheckOutPage(mapRenewedProductDetails);
		test.asm_OMR.clickOnSubmitPayment();
		//test.asm_OMR.verifyPrintReceiptMessageAfterPayment();
	}
	
	@Test
	public void Step08_Navigate_to_Latest_invoice_And_verify_Details_After_Renewal() {

		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.clickOnSideBarTab("Invoice");
		test.memberShipPage.clickOnSideBar("Find Invoice");
		test.individualsPage.enterFieldValue("Invoice Code", memDetails.get(3));
		test.individualsPage.clickGoButton();
		test.memberShipPage.clickInvoiceHeading("Transaction Date");
		test.memberShipPage.clickOnInvoiceNumber();
		test.invoicePage.verifyInvoiceDetailsAfterRenewal();
		test.invoicePage.expandDetailsMenu("line items");
		test.invoicePage
				.verifyRenewedProductsPriceInsideLineItems(mapRenewedProductDetails);
		test.invoicePage.collapseDetailsMenu("line items");

	}

	
	
}