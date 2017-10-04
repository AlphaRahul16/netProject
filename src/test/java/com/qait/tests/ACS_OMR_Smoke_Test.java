
package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
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
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_OMR_Smoke_Test(String caseID) {
		this.caseID = caseID;
	}

	@Test
	public void Step01_TC01_launch_Iweb_And_Select_Valid_User_For_Renewal() {
		Reporter.log("STEP: Case id : "+caseID,true);
		mapOMR = ASCSocietyGenericPage.addValuesInMap(sheetname, caseID);
		test.homePageIWEB.clickOnSideBarTab("Invoice");
		test.homePageIWEB.clickOnTab("Query Invoice");
		test.memberShipPage.selectAndRunQuery(YamlReader.getYamlValue("New_Member_Benefits.OMR"));
//		test.homePageIWEB.clickOnModuleTab();
//		test.homePageIWEB.clickOnTab("Membership");
//		test.homePageIWEB.clickOnSideBarTab("Members");
//		test.memberShipPage.selectValidUserForRenewal(mapOMR, YamlReader.getYamlValue("New_Member_Benefits.OMR"));
	}

	@Test (dependsOnMethods = { "Step01_TC01_launch_Iweb_And_Select_Valid_User_For_Renewal" })
	public void Step02_TC01_Verify_Payment_Status_And_Invoice_Details_Before_Renewal() {
		//test.individualsPage.clickGotoRecordForRenewal();
		test.memberShipPage.clickOnGoButtonAfterPackageSelection();
		invoiceNumber = test.invoicePage.verifyInvoiceDetailsBeforeRenewal();

	}

	@Test (dependsOnMethods = { "Step02_TC01_Verify_Payment_Status_And_Invoice_Details_Before_Renewal" })
	public void Step03_TC01_Navigate_to_Membership_Page_And_Fetch_Member_Details() {
		test.memberShipPage.clickOnCustomerNameAndNavigateToMembershipPage();
		memDetails = test.memberShipPage.getCustomerAllDetails(invoiceNumber);
		//test.memberShipPage.getCustomerDetails("lastName","firstName");
	}

	@Test (dependsOnMethods = { "Step03_TC01_Navigate_to_Membership_Page_And_Fetch_Member_Details" })
	public void Step04_TC01_launch_Eweb_Renewal_Application_And_Login_With_Valid_Credentials() {
		test.launchApplication(app_url_OMR);
		test.asm_OMR.loginIntoApplicationWithValidChoice(mapOMR, memDetails);
		test.asm_OMR.OMRLogo("Online Membership Renewal");
		test.asm_OMR.verifyWelcomePage();
		test.asm_OMR.selectNoIfRegularToEmeritusPromptAppears();
	}

	@Test (dependsOnMethods = { "Step04_TC01_launch_Eweb_Renewal_Application_And_Login_With_Valid_Credentials" })
	public void Step05_TC01_Add_Membership_For_Member() {
		test.asm_OMR.FillRequiredDetailsForStudentMember(mapOMR);
		mapRenewedProductDetails = test.asm_OMR
				.addMembershipsForRegularMember(mapOMR);
		test.asm_OMR.verifyPublicationIsAdded(mapOMR.get("Publications_Name"));
	}

	@Test (dependsOnMethods = { "Step05_TC01_Add_Membership_For_Member" })
	public void Step06_TC01_Submit_Payment_Details_And_Verify_Renewal_Summary_On_CheckoutPage() {
		test.asm_OMR.submitPaymentDetails(memDetails.get(1) + " " + memDetails.get(0));
		//test.asm_OMR.verifyRenewedProductsSummaryOnCheckOutPage(mapRenewedProductDetails);		
		test.asm_OMR.clickOnSubmitPayment();
		test.asm_OMR.verifyPrintReceiptMessageAfterPayment();
		test.asm_OMR.logoutFromApplication();
	}

	@Test (dependsOnMethods = { "Step06_TC01_Submit_Payment_Details_And_Verify_Renewal_Summary_On_CheckoutPage" })
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

	@Test (dependsOnMethods = { "Step07_TC01_Navigate_to_Latest_invoice_And_verify_Details_After_Renewal" })
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

	@BeforeMethod
	public void print_test_method_And_Case_id(Method method) {
		test.printMethodName(method.getName());
		Reporter.log("CASE ID::" + this.caseID + "\n", true);
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
	}


}

