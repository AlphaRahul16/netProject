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
		app_url_OMR = getYamlValue("app_url_OMR");
		app_url_IWEB = getYamlValue("app_url_IWEB");
		test.launchApplication(app_url_IWEB);
		test.homePage.enterAuthentication(
				YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		System.out.println(sheetname);
		mapGcsOMR = XlsReader.addValuesInTheMapForExcel(sheetname, caseID);
	}
	
	@Test
	public void Step01_Launch_Application_Under_Test() {


		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
		test.homePageIWEB.clickOnTab("Find Members");
		test.memberShipPage.selectValidUserForGCSOMR(mapGcsOMR);
		test.memberShipPage.selectARandomActiveStudentChapter();
	}
	

@Test
public void Step02_TC01_Verify_Payment_Status_And_Invoice_Details_Before_Renewal() {
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
}

@Test
public void Step06_TC01_Submit_Payment_Details_And_Verify_Renewal_Summary_On_CheckoutPage() {
	test.asm_OMR.submitPaymentDetails(mapGcsOMR.get("CreditCard_Type"),
			(memDetails.get(0).split(" ")[1] + " " + memDetails.get(0)
					.split(" ")[0]), mapGcsOMR.get("CreditCard_Number"),
			mapGcsOMR.get("CreditCard_CVV_Number"), mapGcsOMR
					.get("CreditCardExpiration_Month"), mapGcsOMR
					.get("CreditCardExpiration_Year"));

	test.asm_OMR
			.verifyRenewedProductsSummaryOnCheckOutPage(mapRenewedProductDetails);
	test.asm_OMR.clickOnSubmitPayment();
	test.asm_OMR.verifyPrintReceiptMessageAfterPayment();
}

}
