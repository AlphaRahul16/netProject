package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

public class ACS_OMR_Express_Renewal extends BaseTest {
	String app_url_IWEB;
	private String expressURL, customername;
	static String sheetname;
	private String imvoicenumber;
	Map<String, String> mapRenewedProductDetails = new HashMap<String, String>();
	Map<String, String> mapExpressRenewal = new HashMap<String, String>();
	Map<String, String> mapOMR = new HashMap<String, String>();
	

	@BeforeClass
	public void open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IWEB");
		//test.enterAuthenticationAutoIt();
		test.launchApplication(app_url_IWEB);
	}
	
	public ACS_OMR_Express_Renewal() {
		  com.qait.tests.DataProvider_FactoryClass.sheetName = "OMR_Express_Datasheet";
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_OMR_Express_Renewal(String caseID) {
		this.caseID = caseID;
	}
	
	@Test
	public void Step01_Launch_IWEB_Application_And_Run_Express_Renewal_Query() {
		mapExpressRenewal = test.homePageIWEB.addValuesInMap("OMR_Express_Datasheet", caseID);

		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.homePageIWEB.clickOnTab("Query Individual");
		test.memberShipPage.selectAndRunQuery("Selenium - BP Renewal Express Url");
	}

	@Test(dependsOnMethods = { "Step01_Launch_IWEB_Application_And_Run_Express_Renewal_Query" })
	public void Step02_Enter_Before_Amd_After_Expiry_Dates_And_Fetch_Express_Renewal_URL() {
	//	test.memberShipPage.enterExpiryDatesBeforeAndAfterExpressRenewal();
		test.memberShipPage.clickOnGoButtonAfterPackageSelection();
		expressURL = test.memberShipPage.fetchExpressURLForRenewal();
	}

	@Test(dependsOnMethods = { "Step02_Enter_Before_Amd_After_Expiry_Dates_And_Fetch_Express_Renewal_URL" })
	public void Step03_Launch_Express_Renewal_URL_And_Verify_Complete_Address_Is_Not_Displayed_On_HomePage() {
		test.launchApplication(expressURL);
		test.asm_OMR.selectNoIfRegularToEmeritusPromptAppears();
		test.asm_OMR.confirmStudentDetailsForExpressRenewal("Bachelor");
		test.asm_OMR.verifyUserIsOnExpressRenewalHomepage("Online Membership Renewal Express");
		test.asm_OMR.switchToEwebRenewalFrame();

		test.asm_OMR.verifyUserCompleteAddressIsNotDisplayedOnHomePage();
	}

	@Test(dependsOnMethods = {
			"Step03_Launch_Express_Renewal_URL_And_Verify_Complete_Address_Is_Not_Displayed_On_HomePage" })
	public void Step04_Save_All_Renewed_product_Details_And_Fetch_Member_Name() {
		mapRenewedProductDetails = test.asm_OMR.saveProductsWithRespectiveRenewalAmount();
		customername = test.asm_OMR.getMemberNameFromOMRHomepage();
	}

	@Test(dependsOnMethods = { "Step04_Save_All_Renewed_product_Details_And_Fetch_Member_Name" })
	public void Step05_Submit_Payment_Details_And_Verify_Renewal_Summary_On_CheckoutPage() {	
		test.asm_OMR.submitPaymentDetails(customername.split(" ")[0]+" "+customername.split(" ")[1]);
		test.asm_OMR
				.verifyRenewedProductsSummaryOnCheckOutPage(mapRenewedProductDetails);

		test.asm_OMR.clickOnSubmitPayment();
		imvoicenumber = test.asm_OMR.geInvoiceNumberOnOMRReceiptPage("RenewalNumber");
		test.asm_OMR.verifyPrintReceiptMessageAfterPayment();
	}

	@Test(dependsOnMethods = { "Step05_Submit_Payment_Details_And_Verify_Renewal_Summary_On_CheckoutPage" })
	public void Step06_Navigate_to_Latest_invoice_And_verify_Details_After_Express_Renewal() {

		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.clickOnSideBarTab("Invoice");
		test.memberShipPage.clickOnSideBar("Find Invoice");
		test.individualsPage.enterFieldValue("Invoice Code", imvoicenumber);
		test.individualsPage.clickGoButton();
		test.memberShipPage.clickInvoiceHeading("Transaction Date");
		test.memberShipPage.clickOnInvoiceNumber();
		test.invoicePage.verifyInvoiceDetailsAfterRenewal();
		test.invoicePage.expandDetailsMenu("line items");
		test.invoicePage.verifyRenewedProductsPriceInsideLineItems(mapRenewedProductDetails);
		// test.invoicePage.collapseDetailsMenu("line items");

	}

}
