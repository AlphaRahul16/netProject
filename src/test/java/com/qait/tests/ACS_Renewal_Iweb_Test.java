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

public class ACS_Renewal_Iweb_Test extends BaseTest {

	String app_url_IWEB, invoiceNumber;
	private String caseID, batchprefix = "ACS: ";
	Map<String, String> mapRenewalIweb = new HashMap<String, String>();
	List<String> individualDatelist;

	public ACS_Renewal_Iweb_Test() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "Renewal_IWeb_Sheet";

	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_Renewal_Iweb_Test(String caseID) {
		this.caseID = caseID;
		System.out.println("case ID " + this.caseID);
		System.setProperty("caseID", this.caseID);
	}

	@Test
	public void Step01_launch_Iweb_And_Select_Valid_User_For_Renewal() {
		mapRenewalIweb = test.homePageIWEB.addValuesInMap("Renewal_IWeb_Sheet", caseID);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
		test.homePageIWEB.clickOnSideBarTab("Members");
		test.memberShipPage.selectUserForIwebRenewal(mapRenewalIweb);

	}

	@Test
	public void Step02_Verify_Payment_Status_And_Invoice_Details_Before_Renewal() {
		test.individualsPage.clickGotoRecordForRenewal();
		invoiceNumber = test.invoicePage.verifyInvoiceDetailsBeforeRenewal();

	}

	@Test
	public void Step03_Add_Payment_And_Verify_Details_After_Apply_Payment_Test() {
		Reporter.log("CASE ID::" + this.caseID, true);
		test.invoicePage.clickOnAddPaymentIcon();
		test.applyPayment.switchToFrame("iframe1");
		test.applyPayment.verifyCheckBoxSelectedAtSelectInvoiceTab("auto default");
		test.applyPayment.verifyCheckBoxSelectedAtSelectInvoiceTab("auto distribute payment");
		test.applyPayment.clickOnNextButton();

		test.applyPayment.selectBatch(batchprefix + mapRenewalIweb.get("Batch_Name?"));
		test.memberShipPage.selectOrderEntryInfo("paymentMethod", mapRenewalIweb.get("Payment_Method"));
		test.memberShipPage.fillAllTypeOFPaymentDetails(mapRenewalIweb.get("Payment_Method"),
				mapRenewalIweb.get("Visa_Card_Number"), mapRenewalIweb.get("Diners_Card_Number"),
				mapRenewalIweb.get("Reference_Number"), mapRenewalIweb.get("Discover_Card_Number"),
				test.homePageIWEB.map().get("AMEX_Card_Number"), mapRenewalIweb.get("Expiry_Date"),
				mapRenewalIweb.get("CVV_Number"), mapRenewalIweb.get("Check_Number"));
		test.applyPayment.clickOnSaveButton();
		test.applyPayment.switchToDefaultContent();
		test.invoicePage.verifyMemberDetails_question("paid in full",
				mapRenewalIweb.get("AfterApplyPayment_paidInFull"));
		test.invoicePage.verifyBalanceInInvoice(mapRenewalIweb.get("AfterApplyPayment_balance") + ".00");
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("line items");
		test.invoicePage.verifyPaidClosedStatus_Yes();
	}

	@Test
	public void Step04_Navigate_To_payment_tab_And_Verify_Payment_Amount() {
		Reporter.log("CASE ID::" + this.caseID, true);
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Payments");
		test.individualsPage.expandDetailsMenu("payments");
		test.invoicePage.verifyIndividualPaymentAmountEqualsPaymentTotal();

	}

	@Test
	public void Step05_Navigate_to_Membership_Page_And_Verify_Details_After_Renewal() {
		test.memberShipPage.clickOnCustomerNameAndNavigateToMembershipPage();
		test.individualsPage.expandDetailsMenu("individual memberships");
		individualDatelist = test.individualsPage
				.verifyDatesArePopulatedUnderIndividualMemberships("Active Renewed-No Response");
		test.individualsPage.collapseDetailsMenu("individual memberships");
		test.individualsPage.expandDetailsMenu("chapter memberships");
		test.individualsPage.verifyDatesUnderChapterMembershipMatchesIndividualDates("Active Renewed-No Response",
				individualDatelist);
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Subscriptions");
		test.individualsPage.expandDetailsMenu("active subscriptions");
		test.individualsPage.verifyActiveSubscriptionDatesIfAvailable(invoiceNumber, individualDatelist);

	}

	@BeforeClass
	public void open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IWEB");
		test.launchApplication(app_url_IWEB);
		// test.homePageIWEB.enterAuthentication(
		// YamlReader.getYamlValue("Authentication.userName"),
		// YamlReader.getYamlValue("Authentication.password"));
	}

}
