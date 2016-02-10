package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;

public class ACS_IndividualLandingPage_Smoke {
	TestSessionInitiator test;
	String app_url_IWEB;
	private String caseID;
	static String uniquelastname;
	String[] ProductNames;
	Map<String, String> mapSheetData = new HashMap<String, String>();
	Map<String, String> mapAmountNames;
	int donationcount;
	String[] totalAmount;
	Map<String, String> mapFundOrder;
	List<String> memberLoginDetails;
	List<String> memberDetails;
	Map<String, String> TotalAmountMap;
	Map<String, List<String>> mapIwebProductDetails;

	
	public ACS_IndividualLandingPage_Smoke() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "landingPage";
	}
	
	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_IndividualLandingPage_Smoke(String caseID) {
		this.caseID = caseID;
	}


	@Test
	public void Step01_TC01_Launch_IWeb_Application_And_Navigate_To_Funds() {
		Reporter.log("CASE ID : " + caseID, true);
		mapSheetData = test.homePageIWEB.addValuesInMap("landingPage", caseID);
		test.navigateToIWEBUrlOnNewBrowserTab(app_url_IWEB);
		test.homePageIWEB.enterAuthentication("C00616", "ACS2016#");
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Fundraising");
		test.homePageIWEB.clickOnSideBarTab("More...");
		test.homePageIWEB.clickOnTab("Find Fund");
		test.individualsPage.enterFieldValue("Fund Code", mapSheetData.get("Application_Codes"));
		test.individualsPage.clickGoButton();
		test.invoicePage.expandDetailsMenu("suggested donation amounts");
		test.fundpofilePage.deleteSuggestedDonationAmount();
		donationcount = test.fundpofilePage.addDonationAmountFromDataSheet(mapSheetData);

	}

	@Test
	public void Step02_TC02_Retreive_Landing_Page_Details_And_Get_Login_Status_From_Sheet() {
		Reporter.log("CASE ID : " + caseID, true);
		mapFundOrder = test.fundpofilePage.arrangeDisplayOrderInAscendingOrder(donationcount, mapSheetData);

		String[] loginAs = { mapSheetData.get("Login as Member?"), mapSheetData.get("Login as Non Member?"),
				mapSheetData.get("Continue as Guest?") };

		memberLoginDetails = test.memberShipPage.loginUsingValueFromSheet(loginAs);
		mapIwebProductDetails = test.asm_Donate.getUserAddressDetails(memberLoginDetails, "PhoneNo", "Email",
				"Address");
		memberDetails = test.memberShipPage.getCustomerLastNameAndContactID(mapSheetData.get("Login_via_MemberNumber"));
		test.launchApplication(mapSheetData.get("Application_Url"));
		test.asm_Donate.verifyIndividualDonationDisplayOrder(donationcount, mapSheetData, mapFundOrder);
		ProductNames = test.asm_Donate.donateAmountToSpecifiedFund(mapSheetData);
		totalAmount = test.asm_Donate.getTotalAmountDonatedForIndividualLandingPage();

	}

	@Test
	public void Step03_TC03_Navigate_To_ContactInfo_Page_And_Login() {
		Reporter.log("CASE ID : " + caseID, true);

		uniquelastname = mapSheetData.get("Guest_LastName") + System.currentTimeMillis();
		test.asm_Donate.clickOnLoginButtonForSpecifiedUser(memberLoginDetails, mapSheetData.get("ValidEmailAddress"),
				mapSheetData.get("Login_via_MemberNumber"), memberDetails);
		test.asm_Donate.enterGuestRequiredDetailsInFormForIndividualLandingPage(memberLoginDetails, mapSheetData.get("Guest_FirstName"),
				uniquelastname, mapSheetData.get("ValidEmailAddress"), mapSheetData.get("Guest_Phone"),
				mapSheetData.get("Guest_Address"), mapSheetData.get("Guest_City"), mapSheetData.get("Guest_State"),
				mapSheetData.get("Guest_ZipCode"), mapSheetData.get("Guest_Country"));
	}

	@Test
	public void Step04_TC04_Navigate_To_Confirm_Your_Donation_Page_And_Verify_Details() {
		Reporter.log("CASE ID : " + caseID, true);

		test.asm_Donate.sendCardOrEmailFromSpreadsheet(mapSheetData.get("SelectSendCardOption?"),
				mapSheetData.get("InHonorOf?"), mapSheetData.get("InMemoryOf?"), mapSheetData.get("DonotSendCard?"),
				mapSheetData.get("SendCardVia_Email?"), mapSheetData.get("SendCardVia_PostalMail?"));

		test.asm_Donate.selectASendCardMethod(mapSheetData.get("SelectSendCardOption?"),
				mapSheetData.get("DonotSendCard?"), mapSheetData.get("SendCardVia_Email?"),
				mapSheetData.get("SendCardVia_PostalMail?"), mapSheetData.get("Recepient_Email"),
				mapSheetData.get("Personalised_Message"), mapSheetData.get("PostalMail_To"),
				mapSheetData.get("PostalMail_Address"), mapSheetData.get("PostalMail_City"),
				mapSheetData.get("PostalMail_State"), mapSheetData.get("PostalMail_ZipCode"));

		test.asm_Donate.BreakMyDonationForMonthlyPaymentsForIndividualLanding(mapSheetData.get("BreakMyDonation?"),
				mapSheetData.get("Pledge_Months"));
		test.asm_Donate.enterPaymentDetailsForACSDonateSmoke(memberLoginDetails,
				YamlReader.getYamlValue("creditCardInfo.Type"),
				mapSheetData.get("Guest_FirstName") + " " + uniquelastname, mapSheetData.get("CreditCard_Number"),
				mapSheetData.get("CreditCard_CVV_Number"), mapSheetData.get("CreditCardExpiration_Month"),
				mapSheetData.get("CreditCardExpiration_Year"));

	}

	@Test
	public void Step05_TC05_Verify_ThankyouMessage_At_Confirm_Your_Donation_Page() {
		Reporter.log("CASE ID : " + caseID, true);

		test.asm_Donate.verifyProductPledgedSummaryOnConfirmDonationPage(ProductNames, totalAmount,
				mapSheetData.get("Pledge_Months"));
		TotalAmountMap = test.asm_Donate.verifyTotalAmountOnDonationPage(totalAmount,
				mapSheetData.get("Pledge_Months"));
		test.asm_Donate.verifyThankyouMessageAfterDonation();
		test.asm_Donate.verifyPrintReceiptMessageAfterDonation();
		test.asm_Donate.verifyConfirmationEmailAfterDonation(mapSheetData.get("ValidEmailAddress"));
	}

	@Test
	public void Step06_TC06_Navigate_To_Iweb_And_Retreive_Lastest_Invoice_For_Donor() {
		Reporter.log("CASE ID : " + caseID, true);
		test.navigateToIWEBUrlOnNewBrowserTab(app_url_IWEB);
		test.memberShipPage.navigateToMemberLatestInvoicePage(memberLoginDetails);
		if (memberLoginDetails.get(0).equals("2")) {
			test.homePageIWEB.clickFindForIndividualsSearch();
			test.individualsPage.enterFieldValue("Last Name", uniquelastname);
			test.individualsPage.enterFieldValue("First Name", mapSheetData.get("Guest_FirstName"));
			test.individualsPage.clickGoButton();
			test.individualsPage.verifyMemberIsNotCreated();
			test.individualsPage.navigateToInvoicesMenuOnHoveringMore();
			test.individualsPage.clickOnInvoiceArrowButtonToNavigateFinancialPage();

		}

	}

	@Test
	public void Step07_TC07_Navigate_To_Iweb_And_verify_Lastest_Invoice_For_User() {
		Reporter.log("CASE ID : " + caseID, true);
		test.invoicePage.validateBalanceAndTotalForInvoiceForIndividualLandingPages(TotalAmountMap, totalAmount);
		test.invoicePage.verifyEmailStatus(TotalAmountMap);
		test.invoicePage.expandDetailsMenu("line items");
		test.invoicePage.verfifyproductInsideLineItemsForIndividualLanding(TotalAmountMap, totalAmount, ProductNames,
				mapSheetData);
		test.invoicePage.collapseDetailsMenu("line items");
		test.invoicePage.expandDetailsMenu("acs giving invoice details");
		test.invoicePage.verifyGivingInvoiceDetails(mapSheetData.get("SendCardVia_Email?"),
				mapSheetData.get("SendCardVia_PostalMail?"), mapSheetData.get("DonotSendCard?"), ProductNames[0],
				mapSheetData.get("SelectSendCardOption?"));
		test.invoicePage.collapseDetailsMenu("acs giving invoice details");
	}

	@BeforeClass
	public void OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IWEB");

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
