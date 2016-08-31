package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.XlsReader;
import com.qait.automation.utils.YamlReader;

public class GCS_OMR_Test extends BaseTest {

	String app_url_IWEB, app_url_OMR;
	static String sheetname,invoiceNumber;
	private int caseID;
	Map<String, String> mapGcsOMR = new HashMap<String, String>();
	private List<String> memDetails;
	Map<String, String> mapRenewedProductDetails;


	public GCS_OMR_Test() {
		sheetname = com.qait.tests.Data_Provider_Factory_Class_Xml.sheetName = "GCS_OMR";
	}

	@Factory(dataProviderClass = com.qait.tests.Data_Provider_Factory_Class_Xml.class, dataProvider = "data")
	public GCS_OMR_Test(int caseID) {
		this.caseID = caseID;
	}

	@BeforeClass
	public void open_Browser_Window() {

		test = new TestSessionInitiator(this.getClass().getSimpleName());
		mapGcsOMR = XlsReader.addValuesInTheMapForExcel(sheetname, caseID);
		app_url_OMR = getYamlValue("app_url_OMR");
		app_url_IWEB = getYamlValue("app_url_IWEB");
		test.launchApplication(app_url_IWEB);
		test.homePage.enterAuthentication(
				YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		System.out.println(sheetname);

	}

	@Test
	public void Step01_Launch_Application_Under_Test() {


		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
		test.homePageIWEB.clickOnTab("Find Members");
		test.memberShipPage.selectValidUserForGCSOMR(mapGcsOMR);
		test.memberShipPage.selectARandomActiveStudentChapter();
		test.memberShipPage.expandDetailsMenu("invoices");
		//test.memberShipPage.verifyTermStartDateAndEndDatesAreEmpty(mapGcsOMR);
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
	}

	@Test
	public void Step04_TC01_launch_Eweb_Renewal_Application_And_Login_With_Valid_Credentials() {
		test.launchApplication(app_url_OMR);
		test.asm_OMR.loginIntoApplicationWithValidChoice(mapGcsOMR, memDetails);
		test.asm_OMR.OMRLogo("Online Membership Renewal");
		test.asm_OMR.selectNoIfRegularToEmeritusPromptAppears();
		test.asm_OMR.verifyWelcomePage();
	}

	@Test
	public void Step05_TC01_Add_Membership_For_Member() {
		test.asm_OMR.FillRequiredDetailsForStudentMember(mapGcsOMR);
		mapRenewedProductDetails = test.asm_OMR
				.addMembershipsForRegularMember(mapGcsOMR);
		test.asm_OMR.selectINRAsCurrencyType("Indian Rupee");
		test.asm_OMR.clickYesSurePopUpButton("Yes, I'm sure");
	}

	@Test
	public void Step06_TC01_Submit_Payment_Details_And_Verify_Renewal_Summary_On_CheckoutPage() {

		test.asm_OMR.navigateToCheckOutPageForGCSOMR();
		test.asm_OMR.clickOnSubmitPayment();
		test.asm_OMR.clickProccedWithPaymentinINR("Proceed with payment in INR");
		test.gcsPaymentPage.clickOnPaymentButtonNamedAs(mapGcsOMR.get("CreditCard_Type"));
		test.asm_OMR.clickContinueButtonToNavigateToBankPaymentPage();


		//	test.asm_OMR
		//			.verifyRenewedProductsSummaryOnCheckOutPage(mapRenewedProductDetails);
		//	test.asm_OMR.clickOnSubmitPayment();

	}
	

	@Test
	public void Step07_TC01_Bank_Payment_Page() {
		test.gcsPaymentPage.EnterDetailsOnBankPaymentPageAndProcessFutherSimulation(mapGcsOMR.get("Mobile_Number"),mapGcsOMR.get("Email_Id"),
				mapGcsOMR.get("CreditCard_Number"),(memDetails.get(0).split(" ")[1] + " " + memDetails.get(0).split(" ")[0]),Integer.parseInt(mapGcsOMR.get("CreditCardExpiration_Month")),
Integer.parseInt(mapGcsOMR.get("CreditCardExpiration_Year")),mapGcsOMR.get("CreditCard_CVV_Number"),mapGcsOMR.get("Bank_Name"));
	}
}
