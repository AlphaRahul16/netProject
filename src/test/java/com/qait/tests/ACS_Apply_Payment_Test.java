package com.qait.tests;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;

public class ACS_Apply_Payment_Test {
	TestSessionInitiator test;
	String app_url_IWEB;

	@Test
	public void Step00_Launch_Iweb_Application() {
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(
				YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
//		test.homePageIWEB
//				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test
	public void Step01_Navigate_To_Membership_Module_And_Find_Member_Test() {
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
		test.homePageIWEB.clickOnSideBarTab("Members");
		test.homePageIWEB.clickOnTab("Find Members");
		test.individualsPage.selectFieldValueToFindMember("Association", "ACS");
		test.individualsPage.selectFieldValueToFindMember("Member Type",
				"ACS : Regular Member");
		test.individualsPage.selectFieldValueToFindMember("Member Status",
				"ACS : Active Renewed-No Response");
		test.individualsPage.selectFieldValueToFindMember("Member Package",
				"ACS : Regular Member : Regular Member Dues C&EN-Print");
		test.individualsPage.clickGoButton();
		test.individualsPage.handleAlert();
		test.memberShipPage.clickOnAnyRandomMember();

	}

	@Test
	public void Step02_Navigate_To_Product_Test() {
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("invoices");

		// select member start end date
		test.invoicePage.clickOnGoToArrowButton();
		test.invoicePage.verifyMemberDetails_question("proforma", "Yes");
		test.invoicePage.verifyMemberDetails_question("paid in full", "No");
		test.invoicePage.verifyBalanceInInvoice(test.invoicePage
				.getMemberDetails("invoice total"));

		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("line items");
		test.invoicePage.verifyPaidClosedValueNo();
		test.invoicePage.clickOnAddPaymentIcon();
		test.applyPayment.switchToFrame("iframe1");
		test.applyPayment
				.verifyCheckBoxSelectedAtSelectInvoiceTab("auto default");
		test.applyPayment
				.verifyCheckBoxSelectedAtSelectInvoiceTab("auto distribute payment");
		test.applyPayment.clickOnNextButton();
		// test.applyPayment.verifyTabName("Apply Payment");
		test.applyPayment.selectBatch("ACS: SELENIUM_BATCH");
		test.applyPayment.enterDetailsForPayment("Visa/MC", "4111111111111111",
				"2017/07", "123");
		test.applyPayment.clickOnSaveButton();
		test.applyPayment.switchToDefaultContent();
		test.invoicePage.verifyMemberDetails_question("proforma", "No");
		test.invoicePage.verifyMemberDetails_question("paid in full", "Yes");
		test.invoicePage.verifyBalanceInInvoice("0.00");

		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("line items");
		test.invoicePage.verifyPaidClosedStatus_Yes();

	}

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = YamlReader.getYamlValue("app_url_IWEB");
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
	}

	// @AfterClass(alwaysRun = true)
	public void Close_Test_Session() {
		test.closeBrowserSession();
	}
}
