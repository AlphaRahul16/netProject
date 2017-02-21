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

public class ACS_GivingApplication_Smoke extends BaseTest {

	String app_url_givingDonate;
	String app_url_IWEB;

	String productNameKey[];
	String DonateProgramNames[];
	static String uniquelastname;
	List<String> memberLoginDetails;
	List<String> memberDetails;
	Map<String, String> TotalAmountMap;
	Map<String, List<String>> mapIwebProductDetails;
	Map<String, String> mapSheetData = new HashMap<String, String>();
	private String caseID;

	String[] ProductNames;

	public ACS_GivingApplication_Smoke() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "giving_donate";
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_GivingApplication_Smoke(String caseID) {
		this.caseID = caseID;
	}

	@Test
	public void Step01_TC01_Launch_IWeb_Application_And_Navigate_To_Funds() {

		mapSheetData = test.homePageIWEB.addValuesInMap("giving_donate", caseID);
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Fundraising");
		test.homePageIWEB.clickOnSideBarTab("More...");
		test.homePageIWEB.clickOnTab("Find Fund");
	}

	@Test
	public void Step02_TC02_Retreive_Donate_All_Program_Details_And_Get_Login_Status_From_Sheet() {

		test.homePageIWEB.clickOnTab("Query Fund");
		test.memberShipPage.selectAndRunQuery("Selenium - Find funds on Donate All Programs Page");
		productNameKey = test.asm_Donate.retreiveProductDetails();
		for (int i = 0; i < productNameKey.length; i++) {
			System.out.println(productNameKey[i]);
		}

		String[] loginAs = { mapSheetData.get("Login as Member?"), mapSheetData.get("Login as Non Member?"),
				mapSheetData.get("Continue as Guest?") };

		memberLoginDetails = test.memberShipPage.loginUsingValueFromSheet(loginAs);
		mapIwebProductDetails = test.asm_Donate.getUserAddressDetails(memberLoginDetails, "PhoneNo", "Email",
				"Address");

		if (mapSheetData.get("Login_via_MemberNumber").equalsIgnoreCase("YES")) {
			memberDetails = test.memberShipPage.getCustomerLastNameAndContactID();
		}

		/*
		 * memberDetails =
		 * test.memberShipPage.getCustomerLastNameAndContactID(mapSheetData.get(
		 * "Login_via_MemberNumber"));
		 */

	}

	@Test
	public void Step03_TC03_Launch_Eweb_And_Donate_Amount_To_Programs() {

		test.launchApplication(app_url_givingDonate);
		String Amount[] = { mapSheetData.get("Program1 Donate Amount"), mapSheetData.get("Program2 Donate Amount"),
				mapSheetData.get("Program3 Donate Amount"), mapSheetData.get("Other Program Donate Amount") };

		ProductNames = test.asm_Donate.getDisplayedProductNamesOnEweb(productNameKey,
				mapSheetData.get("Other Program"));
		test.asm_Donate.verifyProductNamesFromIweb(ProductNames);
		DonateProgramNames = test.asm_Donate.checkDonationPrograms(Amount);
		test.asm_Donate.donateMoneyToProgram(ProductNames, Amount, mapSheetData.get("Other Program"));
		test.asm_Donate.verifyTotalAmountEnteredOnProductDonation(Amount);
	}

	@Test
	public void Step04_TC04_Navigate_To_ContactInfo_Page_And_Login() {

		uniquelastname = mapSheetData.get("Guest_LastName") + System.currentTimeMillis();
		test.asm_Donate.clickOnLoginButtonForSpecifiedUser(memberLoginDetails, mapSheetData.get("ValidEmailAddress"),
				mapSheetData.get("Login_via_MemberNumber"), memberDetails);
		test.asm_Donate.enterGuestRequiredDetailsInForm(memberLoginDetails, mapSheetData.get("Guest_FirstName"),
				uniquelastname, mapSheetData.get("ValidEmailAddress"), mapSheetData.get("Guest_Phone"),
				mapSheetData.get("Guest_Address"), mapSheetData.get("Guest_City"), mapSheetData.get("Guest_State"),
				mapSheetData.get("Guest_ZipCode"), mapSheetData.get("Guest_Country"));
	}

	@Test

	public void Step05_TC05_Navigate_To_Confirm_Your_Donation_Page_And_Verify_Details() {

		String Amount[] = { mapSheetData.get("Program1 Donate Amount"), mapSheetData.get("Program2 Donate Amount"),
				mapSheetData.get("Program3 Donate Amount"), mapSheetData.get("Other Program Donate Amount") };

		test.asm_Donate.verifyProductDetailsOnConfirmDonationPage(ProductNames, Amount);
		test.asm_Donate.verifyTotalAmountOnDonationPage(Amount, mapSheetData.get("Pledge_Months"));

		test.asm_Donate.sendCardOrEmailFromSpreadsheet(mapSheetData.get("SelectSendCardOption?"),
				mapSheetData.get("InHonorOf?"), mapSheetData.get("InMemoryOf?"), mapSheetData.get("DonotSendCard?"),
				mapSheetData.get("SendCardVia_Email?"), mapSheetData.get("SendCardVia_PostalMail?"));

		test.asm_Donate.selectASendCardMethod(mapSheetData.get("SelectSendCardOption?"),
				mapSheetData.get("DonotSendCard?"), mapSheetData.get("SendCardVia_Email?"),
				mapSheetData.get("SendCardVia_PostalMail?"), mapSheetData.get("Recepient_Email"),
				mapSheetData.get("Personalised_Message"), mapSheetData.get("PostalMail_To"),
				mapSheetData.get("PostalMail_Address"), mapSheetData.get("PostalMail_City"),
				mapSheetData.get("PostalMail_State"), mapSheetData.get("PostalMail_ZipCode"));

		test.asm_Donate.BreakMyDonationForMonthlyPayments(mapSheetData.get("BreakMyDonation?"),
				mapSheetData.get("Pledge_Months"));
		test.asm_Donate.enterPaymentDetailsForACSDonateSmoke(memberLoginDetails,
				mapSheetData.get("Payment_Method"),
				mapSheetData.get("Guest_FirstName") + " " + uniquelastname, mapSheetData.get("Visa_Card_Number"),
				mapSheetData.get("Diners_Card_Number"), mapSheetData.get("Discover_Card_Number"),
				mapSheetData.get("AMEX_Card_Number"), mapSheetData.get("CVV_Number"),
				mapSheetData.get("CreditCardExpiration_Month"), mapSheetData.get("CreditCardExpiration_Year"));

	}

	@Test
	public void Step06_TC06_Verify_ThankyouMessage_At_Confirm_Your_Donation_Page() {

		String Amount[] = { mapSheetData.get("Program1 Donate Amount"), mapSheetData.get("Program2 Donate Amount"),
				mapSheetData.get("Program3 Donate Amount"), mapSheetData.get("Other Program Donate Amount") };

		test.asm_Donate.verifyProductPledgedSummaryOnConfirmDonationPage(ProductNames, Amount,
				mapSheetData.get("Pledge_Months"));
		TotalAmountMap = test.asm_Donate.verifyTotalAmountOnDonationPage(Amount, mapSheetData.get("Pledge_Months"));
		test.asm_Donate.verifyThankyouMessageAfterDonation();
		test.asm_Donate.verifyPrintReceiptMessageAfterDonation();
		test.asm_Donate.verifyConfirmationEmailAfterDonation(mapSheetData.get("ValidEmailAddress"));
	}

//	@Test
//	public void Step07_TC_07_Navigate_To_Iweb_And_Retreive_Lastest_Invoice_For_Donor() {
//
//		test.launchApplication(app_url_IWEB);
//		test.memberShipPage.navigateToMemberLatestInvoicePage(memberLoginDetails);
//		if (memberLoginDetails.get(0).equals("2")) {
//			test.homePageIWEB.clickFindForIndividualsSearch();
//			test.individualsPage.enterFieldValue("Last Name", uniquelastname);
//			test.individualsPage.enterFieldValue("First Name", mapSheetData.get("Guest_FirstName"));
//			test.individualsPage.clickGoButton();
//			test.individualsPage.verifyMemberIsNotCreated();
//			test.individualsPage.navigateToInvoicesMenuOnHoveringMore();
//			test.individualsPage.clickOnInvoiceArrowButtonToNavigateFinancialPage();
//
//		}
//
//	}
//
//	@Test
//	public void Step08_TC_08_Navigate_To_Iweb_And_verify_Lastest_Invoice_For_User() {
//
//		String Amount[] = { mapSheetData.get("Program1 Donate Amount"), mapSheetData.get("Program2 Donate Amount"),
//				mapSheetData.get("Program3 Donate Amount"), mapSheetData.get("Other Program Donate Amount") };
//		test.invoicePage.validateBalanceAndTotalForInvoice(TotalAmountMap);
//		test.invoicePage.verifyEmailStatus(TotalAmountMap);
//		test.invoicePage.expandDetailsMenu("line items");
//		test.invoicePage.verfifyproductDisplayNamesAndCodesInsideLineItems(TotalAmountMap, Amount, productNameKey,
//				mapIwebProductDetails);
//		test.invoicePage.collapseDetailsMenu("line items");
//		test.invoicePage.expandDetailsMenu("acs giving invoice details");
//		test.invoicePage.verifyGivingInvoiceDetails(mapSheetData.get("SendCardVia_Email?"),
//				mapSheetData.get("SendCardVia_PostalMail?"), mapSheetData.get("DonotSendCard?"),
//				mapSheetData.get("Other Program"), mapSheetData.get("SelectSendCardOption?"));
//
//		test.invoicePage.collapseDetailsMenu("acs giving invoice details");
//	}

	@BeforeClass
	public void OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_givingDonate = getYamlValue("app_url_givingDonate");
		app_url_IWEB = getYamlValue("app_url_IWEB");

	}

	@BeforeMethod
	public void handleTestMethodName(Method method) {
		test.printMethodName(method.getName());
	}

}