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

public class GCS_OMR_Test extends BaseTest {

	String app_url_IWEB, app_url_OMR;
	static String sheetname, invoiceNumber;
	private String caseID;
	Map<String, String> mapGcsOMR = new HashMap<String, String>();
	private List<String> memDetails;
	Map<String, String> mapRenewedProductDetails=new HashMap<String, String>();

	public GCS_OMR_Test() {
		sheetname =  com.qait.tests.DataProvider_FactoryClass.sheetName = "GCS_OMR";
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public GCS_OMR_Test(String caseID) {
		this.caseID = caseID;
	}

	@BeforeClass
	public void open_Browser_Window() {

		test = new TestSessionInitiator(this.getClass().getSimpleName());
		mapGcsOMR = test.homePageIWEB.addValuesInMap(sheetname, caseID);
		app_url_OMR = getYamlValue("app_url_OMR");
		app_url_IWEB = getYamlValue("app_url_IWEB");
		test.launchApplication(app_url_IWEB);
		test.homePage.enterAuthentication(
				YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		System.out.println(sheetname);

	}
	@BeforeMethod
	public void handleTestMethodName(Method method) {
		test.printMethodName(method.getName());
	}

	@Test
	public void Step01_Launch_IWEB_Application_And_Navigate_To_Find_Members_Tab() {
		
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
	}
	
	@Test(dependsOnMethods={"Step01_Launch_IWEB_Application_And_Navigate_To_Find_Members_Tab"})
	public void Step02_Select_Valid_User_For_Renewal_And_Verify_Term_Start_And_End_Dates_Is_Empty() {
		test.memberShipPage.selectValidUserForRenewalAccordingToCountry(mapGcsOMR);
	}

	@Test(dependsOnMethods={"Step02_Select_Valid_User_For_Renewal_And_Verify_Term_Start_And_End_Dates_Is_Empty"})
	public void Step03_Verify_Payment_Status_And_Invoice_Details_Before_Renewal() {
		test.individualsPage.clickGotoRecordForRenewal();
		invoiceNumber = test.invoicePage.verifyInvoiceDetailsBeforeRenewal();

	}

	@Test(dependsOnMethods={"Step03_Verify_Payment_Status_And_Invoice_Details_Before_Renewal"})
	public void Step04_Navigate_to_Membership_Page_And_Fetch_Member_Details() {
		test.memberShipPage.clickOnCustomerNameAndNavigateToMembershipPage();
		memDetails = test.memberShipPage.getCustomerAllDetails(invoiceNumber);
	}

	@Test(dependsOnMethods={"Step04_Navigate_to_Membership_Page_And_Fetch_Member_Details"})
	public void Step05_launch_Eweb_Renewal_Application_And_Login_With_Valid_Credentials() {
		test.launchApplication(app_url_OMR);
		test.asm_OMR.loginIntoApplicationWithValidChoice(mapGcsOMR, memDetails);
		test.asm_OMR.OMRLogo("Online Membership Renewal");
		test.asm_OMR.selectNoIfRegularToEmeritusPromptAppears();
		test.asm_OMR.verifyWelcomePage();
	}

	@Test(dependsOnMethods={"Step05_launch_Eweb_Renewal_Application_And_Login_With_Valid_Credentials"})
	public void Step06_Verify_Member_Can_Renew_For_Multiple_Years_After_Selecting_Currency_As_INR() {
		test.asm_OMR.FillRequiredDetailsForStudentMember(mapGcsOMR);
		mapRenewedProductDetails = test.asm_OMR
				.addMembershipsForRegularMember(mapGcsOMR);
		test.asm_OMR.verifyMemberCanRenewForMultipleYears();
		test.asm_OMR.selectINRAsCurrencyType("Indian Rupee");
		test.asm_OMR.clickYesSurePopUpButton("Yes, I'm sure");
		test.asm_OMR.verifyMemberCanRenewForMultipleYears();
	}

	@Test(dependsOnMethods={"Step06_Verify_Member_Can_Renew_For_Multiple_Years_After_Selecting_Currency_As_INR"})
	public void Step07_Submit_Payment_Details_And_Verify_Renewal_Summary_On_CheckoutPage() {

		test.asm_OMR.navigateToCheckOutPageForGCSOMR();
		test.asm_OMR.clickOnSubmitPayment();
		test.asm_OMR
				.clickProccedWithPaymentinINR("Proceed with payment in INR");
		test.gcsPaymentPage.clickOnPaymentButtonNamedAs(mapGcsOMR
				.get("Card_Type"));
		test.asm_OMR.clickContinueButtonToNavigateToBankPaymentPage();


	}

	@Test(dependsOnMethods={"Step07_Submit_Payment_Details_And_Verify_Renewal_Summary_On_CheckoutPage"})
	public void Step08_Enter_Details_On_Bank_Payment_Page_And_Process_Further_Simulation() {
		String name = (memDetails.get(0).split(" ")[1] + " " + memDetails
				.get(0).split(" ")[0]);
		System.out.println(name);
		test.gcsPaymentPage.EnterDetailsOnBankPaymentPageAndProcessFutherSimulation(
						mapGcsOMR.get("Card_Type"),
						mapGcsOMR.get("Mobile_Number"),
						mapGcsOMR.get("Email_Id"),
						mapGcsOMR.get("CreditCard_Number"), name,
						mapGcsOMR.get("CreditCardExpiration_Month"),
						mapGcsOMR.get("CreditCardExpiration_Year"),
						mapGcsOMR.get("CreditCard_CVV_Number"),
						mapGcsOMR.get("Bank_Name").trim());

	}


	
	@Test(dependsOnMethods={"Step08_Enter_Details_On_Bank_Payment_Page_And_Process_Further_Simulation"})
	public void Step09_Launch_Iweb_And_Search_Latest_Invoice() {
	test.launchApplication(app_url_IWEB);
	test.homePageIWEB.clickOnSideBarTab("Invoice");
	test.memberShipPage.clickOnSideBar("Find Invoice");
	test.individualsPage.enterFieldValue("Invoice Code", memDetails.get(2));
	test.individualsPage.clickGoButton();
}

	
	@Test(dependsOnMethods={"Step09_Launch_Iweb_And_Search_Latest_Invoice"})
	public void Step10_Verify_Invoice_And_Renewed_Product_Details_After_Renewal() {
		
		test.invoicePage.verifyInvoiceDetailsAfterRenewal();
		test.invoicePage.expandDetailsMenu("line items");
		test.invoicePage
				.verifyRenewedProductsPriceInsideLineItems(mapRenewedProductDetails);
		test.invoicePage.collapseDetailsMenu("line items");

	}
	
	@Test(dependsOnMethods={"Step10_Verify_Invoice_And_Renewed_Product_Details_After_Renewal"})
	public void Step11_Navigate_To_Payments_And_Verify_Global_Constituent_System_Log() {
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Payments");
		test.invoicePage.expandDetailsMenu("acs global constituent system log");
		test.invoicePage.verifyGlobalConstituentSystemLogForGCSOMR("ACS_OMR", mapGcsOMR.get("Card_Type").replace("Card", "").toUpperCase().trim());
		test.invoicePage.collapseDetailsMenu("acs global constituent system log");
	}
	
	@Test(dependsOnMethods={"Step11_Navigate_To_Payments_And_Verify_Global_Constituent_System_Log"})
	public void Step12_Navigate_To_Individual_Membership_Tab_And_verify_Payment_Status_After_Renewal() {
		test.memberShipPage.clickOnCustomerNameAndNavigateToMembershipPage();
		test.invoicePage.expandDetailsMenu("individual memberships");
		test.memberShipPage.navigateToInvoicePageForRenewedProduct();
		test.invoicePage.verifyPaymentStatusAfterRenewal(mapGcsOMR.get("Member_Status?"));
		test.invoicePage.expandDetailsMenu("invoices");
		test.memberShipPage.verifyTermStartDateAndEndDatesAreNotEmpty();
		test.invoicePage.collapseDetailsMenu("invoices");
	}

}
