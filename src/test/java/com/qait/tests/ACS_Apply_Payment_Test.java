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

	}

	@Test
	public void Step01_Navigate_To_Membership_Module_And_Find_Member_Test() {
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
		test.homePageIWEB.clickOnSideBarTab("Members");
		test.homePageIWEB.clickOnTab("Find Members");
		test.individualsPage
				.selectFieldValueToFindMember(
						"Association",
						YamlReader
								.getYamlValue("ACS_ApplyPayment.DetailsToFindMember.Association"));
		test.individualsPage
				.selectFieldValueToFindMember(
						"Member Type",
						YamlReader
								.getYamlValue("ACS_ApplyPayment.DetailsToFindMember.MemberType"));
		test.individualsPage
				.selectFieldValueToFindMember(
						"Member Status",
						YamlReader
								.getYamlValue("ACS_ApplyPayment.DetailsToFindMember.MemberStatus"));
		test.individualsPage
				.selectFieldValueToFindMember(
						"Member Package",
						YamlReader
								.getYamlValue("ACS_ApplyPayment.DetailsToFindMember.MemberPackage"));
		test.individualsPage.clickGoButton();
		test.individualsPage.handleAlert();
		test.memberShipPage.clickOnAnyRandomMember();

	}

	@Test
	public void Step02_Navigate_To_Product_Invoice_And_Verify_Details_Before_Apply_Payment_Test() {
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("invoices");

		test.invoicePage.clickOnGoToArrowButton();
		test.invoicePage.verifyMemberDetails_question("proforma", YamlReader
				.getYamlValue("ACS_ApplyPayment.BeforeApplyPayment.proforma"));
		test.invoicePage
				.verifyMemberDetails_question(
						"paid in full",
						YamlReader
								.getYamlValue("ACS_ApplyPayment.BeforeApplyPayment.paidInFull"));
		test.invoicePage.verifyBalanceInInvoice(test.invoicePage
				.getMemberDetails("invoice total"));
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("line items");
		test.invoicePage.verifyPaidClosedValueNo();

	}

	@Test
	public void Step03_Add_Payment_And_Verify_Details_After_Apply_Payment_Test() {
		test.invoicePage.clickOnAddPaymentIcon();
		test.applyPayment.switchToFrame("iframe1");
		test.applyPayment
				.verifyCheckBoxSelectedAtSelectInvoiceTab("auto default");
		test.applyPayment
				.verifyCheckBoxSelectedAtSelectInvoiceTab("auto distribute payment");
		test.applyPayment.clickOnNextButton();

		test.applyPayment.selectBatch(YamlReader
				.getYamlValue("ACS_ApplyPayment.Batch"));
		test.applyPayment.enterDetailsForPayment(YamlReader.getYamlValue("ACS_ApplyPayment.PaymentMethod.Select"));
		test.applyPayment.clickOnSaveButton();
		test.applyPayment.switchToDefaultContent();
		// test.invoicePage.verifyMemberDetails_question("proforma", YamlReader
		// .getYamlValue("ACS_ApplyPayment.AfterApplyPayment.proforma"));
		test.invoicePage
				.verifyMemberDetails_question(
						"paid in full",
						YamlReader
								.getYamlValue("ACS_ApplyPayment.AfterApplyPayment.paidInFull"));
		test.invoicePage.verifyBalanceInInvoice(YamlReader
				.getYamlValue("ACS_ApplyPayment.AfterApplyPayment.balance"));
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

	//@AfterClass(alwaysRun = true)
	public void Close_Test_Session() {
		test.closeBrowserSession();
	}
}
