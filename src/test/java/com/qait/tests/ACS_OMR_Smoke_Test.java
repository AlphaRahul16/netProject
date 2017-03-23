
package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_OMR_Smoke_Test extends BaseTest {

	String app_url_IWEB, app_url_OMR;
	static String sheetname;
	private String caseID, invoiceNumber;
	Map<String, String> mapOMR = new HashMap<String, String>();
	private List<String> memDetails;
	Map<String, String> mapRenewedProductDetails;

	public ACS_OMR_Smoke_Test() {
		sheetname = com.qait.tests.DataProvider_FactoryClass.sheetName = "OMR";
		System.out.println("SheetName" + sheetname);
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_OMR_Smoke_Test(String caseID) {
		this.caseID = caseID;
	}

	@Test
	public void Step01_TC01_launch_Iweb_And_Select_Valid_User_For_Renewal() {
		Reporter.log("STEP: Case id : "+caseID,true);
		mapOMR = test.homePageIWEB.addValuesInMap(sheetname, caseID);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
		test.homePageIWEB.clickOnSideBarTab("Members");
		test.memberShipPage.selectValidUserForRenewal(mapOMR);

	}

	@Test
	public void Step02_TC01_Verify_Payment_Status_And_Invoice_Details_Before_Renewal() {
		test.individualsPage.clickGotoRecordForRenewal();
		invoiceNumber = test.invoicePage.verifyInvoiceDetailsBeforeRenewal();

	}

	@Test
	public void Step03_TC01_Navigate_to_Membership_Page_And_Fetch_Member_Details() {
		test.memberShipPage.clickOnCustomerNameAndNavigateToMembershipPage();
		memDetails = test.memberShipPage.getCustomerAllDetails(invoiceNumber);
		//test.memberShipPage.getCustomerDetails("lastName","firstName");
	}

	@Test
	public void Step04_TC01_launch_Eweb_Renewal_Application_And_Login_With_Valid_Credentials() {
		test.launchApplication(app_url_OMR);
		test.asm_OMR.loginIntoApplicationWithValidChoice(mapOMR, memDetails);
		test.asm_OMR.OMRLogo("Online Membership Renewal");
		test.asm_OMR.verifyWelcomePage();
		test.asm_OMR.selectNoIfRegularToEmeritusPromptAppears();
	}

	@Test
	public void Step05_TC01_Add_Membership_For_Member() {
		test.asm_OMR.FillRequiredDetailsForStudentMember(mapOMR);
		mapRenewedProductDetails = test.asm_OMR
				.addMembershipsForRegularMember(mapOMR);
	}

	@Test
	public void Step06_TC01_Submit_Payment_Details_And_Verify_Renewal_Summary_On_CheckoutPage() {
		test.asm_OMR.submitPaymentDetails(memDetails.get(1) + " " + memDetails.get(0));
		test.asm_OMR.verifyRenewedProductsSummaryOnCheckOutPage(mapRenewedProductDetails);
		test.asm_OMR.clickOnSubmitPayment();
		test.asm_OMR.verifyPrintReceiptMessageAfterPayment();
		test.asm_OMR.logoutFromApplication();
	}

	@Test
	public void Step07_TC01_Navigate_to_Latest_invoice_And_verify_Details_After_Renewal() {

		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.clickOnSideBarTab("Invoice");
		test.memberShipPage.clickOnSideBar("Find Invoice");
		test.individualsPage.enterFieldValue("Invoice Code", memDetails.get(2));
		test.individualsPage.clickGoButton();
		test.invoicePage.verifyInvoiceDetailsAfterRenewal();
		test.invoicePage.expandDetailsMenu("line items");
		test.invoicePage
				.verifyRenewedProductsPriceInsideLineItems(mapRenewedProductDetails);
		test.invoicePage.collapseDetailsMenu("line items");
		test.invoicePage.verifyAdjustedLinesItemsForEmeritusMember(
				mapOMR.get("Member_Status?"), mapRenewedProductDetails);

	}

	@Test
	public void Step08_TC01_Navigate_to_Membership_Page_And_Verify_Details_After_Renewal() {
		test.memberShipPage.clickOnCustomerNameAndNavigateToMembershipPage();
		test.invoicePage.expandDetailsMenu("individual memberships");
		test.memberShipPage.navigateToInvoicePageForRenewedProduct();
		test.invoicePage.verifyPaymentStatusAfterRenewal(mapOMR
				.get("Member_Status?"));
		test.invoicePage.expandDetailsMenu("invoices");
		test.memberShipPage.verifyTermStartDateAndEndDatesAreNotEmpty();
		test.invoicePage.collapseDetailsMenu("invoices");
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
		System.out.println(sheetname);
	}


}

