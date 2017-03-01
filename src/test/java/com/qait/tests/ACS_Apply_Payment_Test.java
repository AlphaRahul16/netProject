package com.qait.tests;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.LogManager;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_Apply_Payment_Test extends BaseTest {

	String app_url_IWEB;
	private String caseID, batchprefix = "ACS: ";
	Map<String, String> applyPaymentMap = new HashMap<String, String>();

	public ACS_Apply_Payment_Test() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "Apply_Payment_Sheet";

	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_Apply_Payment_Test(String caseID) {
		this.caseID = caseID;
		System.out.println(this.caseID);
		System.setProperty("caseID", this.caseID);
	}

	@Test
	public void Step00_Launch_Iweb_Application() {
		Reporter.log("CASE ID::" + this.caseID, true);
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));

	}

	@Test(dependsOnMethods = "Step00_Launch_Iweb_Application")
	public void Step01_Navigate_To_Membership_Module_And_Find_Member_Test() {
		Reporter.log("CASE ID::" + this.caseID, true);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
		test.homePageIWEB.clickOnSideBarTab("Members");
		test.homePageIWEB.clickOnTab("Find Members");
		test.individualsPage.selectFieldValueToFindMember("Association", applyPaymentMap.get("Association"));
		test.individualsPage.selectFieldValueToFindMember("Member Type", applyPaymentMap.get("MemberType"));
		test.individualsPage.selectFieldValueToFindMember("Member Status", applyPaymentMap.get("MemberStatus"));
		test.individualsPage.selectFieldValueToFindMember("Member Package", applyPaymentMap.get("MemberPackage"));
		test.individualsPage.clickGoButton();
		test.individualsPage.handleAlert();
		test.memberShipPage.clickOnAnyRandomMember();

	}

	@Test(dependsOnMethods = "Step01_Navigate_To_Membership_Module_And_Find_Member_Test")
	public void Step02_Navigate_To_Product_Invoice_And_Verify_Details_Before_Apply_Payment_Test() {
		Reporter.log("CASE ID::" + this.caseID, true);
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("invoices");

		test.invoicePage.clickOnGoToArrowButton();
		test.invoicePage.verifyMemberDetails_question("proforma", applyPaymentMap.get("BeforeApplyPayment_proforma"));
		test.invoicePage.verifyMemberDetails_question("paid in full",
				applyPaymentMap.get("BeforeApplyPayment_paidInFull"));
		test.invoicePage.verifyBalanceInInvoice(test.invoicePage.getMemberDetails("invoice total"));
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("line items");
		test.invoicePage.verifyPaidClosedValueNo();

	}

	@Test(dependsOnMethods = "Step02_Navigate_To_Product_Invoice_And_Verify_Details_Before_Apply_Payment_Test")
	public void Step03_Add_Payment_And_Verify_Details_After_Apply_Payment_Test() {
		Reporter.log("CASE ID::" + this.caseID, true);
		test.invoicePage.clickOnAddPaymentIcon();
		test.applyPayment.switchToFrame("iframe1");
		test.applyPayment.verifyCheckBoxSelectedAtSelectInvoiceTab("auto default");
		test.applyPayment.verifyCheckBoxSelectedAtSelectInvoiceTab("auto distribute payment");
		test.applyPayment.clickOnNextButton();

		test.applyPayment.selectBatch(batchprefix + applyPaymentMap.get("Batch_Name?"));
		test.memberShipPage.selectOrderEntryInfo("paymentMethod", applyPaymentMap.get("Payment_Method"));
		test.memberShipPage.fillAllTypeOFPaymentDetails(applyPaymentMap.get("Payment_Method"),
				applyPaymentMap.get("Visa_Card_Number"), applyPaymentMap.get("Diners_Card_Number"),
				applyPaymentMap.get("Reference_Number"), applyPaymentMap.get("Discover_Card_Number"),
				applyPaymentMap.get("AMEX_Card_Number"), applyPaymentMap.get("Expiry_Date"),
				applyPaymentMap.get("CVV_Number"), applyPaymentMap.get("Check_Number"));
		test.applyPayment.clickOnSaveButton();
		test.applyPayment.switchToDefaultContent();
		test.invoicePage.verifyMemberDetails_question("paid in full",
				applyPaymentMap.get("AfterApplyPayment_paidInFull"));
		test.invoicePage.verifyBalanceInInvoice(applyPaymentMap.get("AfterApplyPayment_balance") + ".00");
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("line items");
		test.invoicePage.verifyPaidClosedStatus_Yes();
	}

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = YamlReader.getYamlValue("app_url_IWEB");
		applyPaymentMap = test.homePage.addValuesInMap("Apply_Payment_Sheet", caseID);
	}

	@BeforeMethod
	public void handleTestMethodName(Method method) {
		test.printMethodName(method.getName());
	}

}
