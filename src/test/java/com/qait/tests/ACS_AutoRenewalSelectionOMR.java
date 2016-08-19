package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_AutoRenewalSelectionOMR extends BaseTest {

	String app_url_IWEB, app_url_OMR;
	String queryPageUrl;
	private String caseID, invoiceNumber;
	private List<String> memDetails;
	Map<String, String> mapRenewedProductDetails;

	@Test
	public void Step01_TC01_launch_Iweb_And_Select_Valid_User_For_AutoRenewal_In_OMR() {
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.homePageIWEB.clickOnTab("Query Individual");
		queryPageUrl = test.individualsPage.getCurrentURL();
		test.memberShipPage
				.selectValidUserForAutoRenewal(
						YamlReader
								.getYamlValue("ACS_AutoRenewal_Selection_In_OMR.AutoRenewal_Query"),
						queryPageUrl);
		test.individualsPage.clickGotoRecordForRenewal();
		test.memberShipPage.clickOnCustomerNameAndNavigateToMembershipPage();
		memDetails = test.memberShipPage.getCustomerFullNameAndContactID();

	}

	@Test(dependsOnMethods = "Step01_TC01_launch_Iweb_And_Select_Valid_User_For_AutoRenewal_In_OMR")
	public void Step02_TC01_Launch_Eweb_OMR() {

		test.launchApplication(app_url_OMR);
		test.asm_OMR.loginIntoOMRApplication(memDetails);
		test.asm_OMR.OMRLogo("Online Membership Renewal");
		test.asm_OMR.verifyWelcomePage();
	}

	@Test(dependsOnMethods = "Step02_TC01_Launch_Eweb_OMR")
	public void Step03_Verify_Renewal_Summary_On_CheckoutPage() {
		System.out.println(YamlReader.getYamlValue(
				"creditCardInfo.CreditCardExpiration").split("\\/")[0]);
		System.out.println(YamlReader.getYamlValue(
				"creditCardInfo.CreditCardExpiration").split("\\/")[1]);
		mapRenewedProductDetails = test.asm_OMR
				.saveProductsWithRespectiveRenewalAmount();
		test.asm_OMR.submitPaymentDetailsForAutoRenewal(YamlReader
				.getYamlValue("creditCardInfo.Type"),
				(memDetails.get(0).split(" ")[1] + " " + memDetails.get(0)
						.split(" ")[0]), YamlReader
						.getYamlValue("creditCardInfo.Number"), YamlReader
						.getYamlValue("creditCardInfo.cvv-number"), YamlReader
						.getYamlValue("creditCardInfo.CreditCardExpiration")
						.split("\\/")[0],
				YamlReader.getYamlValue("creditCardInfo.CreditCardExpiration")
						.split("\\/")[1]);
		test.asm_OMR
				.verifyRenewedProductsSummaryOnCheckOutPage(mapRenewedProductDetails);
		test.asm_OMR.clickOnSubmitPayment();
		test.asm_OMR.verifyPrintReceiptMessageAfterPayment();
	}

	@Test(dependsOnMethods = "Step03_Verify_Renewal_Summary_On_CheckoutPage")
	public void Step04_Launch_Iweb_To_Verify_Details() {
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.clickOnSideBar("Find Individual");
		test.individualsPage
				.enterFieldValue("Record Number", memDetails.get(1));
		test.individualsPage.clickGoButton();
		test.memberShipPage.expandDetailsMenu("individual memberships");
		test.memberShipPage.navigateToInvoicePageForRenewedProduct();
		test.memberShipPage.verifyAutoPayStatusAfterAutoRenewal("chkmk");
		// test.memberShipPage.collapseDetailsMenu("invoices");
		test.memberShipPage.expandDetailsMenu("stored payment information");

		test.memberShipPage
				.verifyStorePaymentInformationChildFormIsPopulated(memDetails
						.get(0).split(" ")[1]);

		test.memberShipPage.collapseDetailsMenu("stored payment information");
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
