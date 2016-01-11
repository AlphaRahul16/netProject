package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.TestNG;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;
import com.qait.keywords.YamlInformationProvider;

public class ACS_GivingApplication_Smoke {

	TestSessionInitiator test;
	String app_url_givingDonate;
	String app_url_IWEB;
	YamlInformationProvider getGivingDetails;
	String productNameKey[];
	String DonateProgramNames[];
	static String uniquelastname;
	List<String> memberLoginDetails;
	Map<String,String> TotalAmountMap;
	Map<String,List<String>> mapIwebProductDetails;
	Map<String,String> mapSheetData = new HashMap<String,String>();
	private String caseID;


	@Factory(dataProviderClass = com.qait.tests.DataProvider_GivingApplicationSmoke.class, dataProvider = "data")
	public ACS_GivingApplication_Smoke (String caseID) {
		System.out.println("factory " + caseID);
		this.caseID = caseID;
	}


	@Test
	public void Step01_TC01_Launch_IWeb_Application_And_Navigate_To_Funds() {
		mapSheetData=test.homePageIWEB.addValuesInMap("giving_donate", caseID);
		test.navigateToIWEBUrlOnNewBrowserTab(app_url_IWEB);
		test.homePageIWEB.enterAuthentication("C00616","ACS2016#");
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Fundraising");
		test.homePageIWEB.clickOnSideBarTab("More...");
		test.homePageIWEB.clickOnTab("Find Fund");
		test.homePageIWEB.clickOnTab("Query Fund");
		test.memberShipPage.selectAndRunQuery("QTP - Find funds on Donate All Programs Page");
		productNameKey=test.asm_Donate.retreiveProductDetails();
	}

	@Test
	public void Step02_TC02_getloginStatusFromSheet() {

		String[] loginAs={mapSheetData.get("Login as Member?"),mapSheetData.get("Login as Non Member?"),
				mapSheetData.get("Continue as Guest?")};  

		memberLoginDetails=	test.memberShipPage.loginUsingValueFromSheet(loginAs);
		mapIwebProductDetails=test.asm_Donate.getUserAddressDetails(memberLoginDetails,"PhoneNo","Email","Address");
	}

	@Test
	public void Step03_TC03_Launch_Eweb_Giving_Application()
	{
		test.launchApplication(app_url_givingDonate);
		String  Amount[]={mapSheetData.get("Program1 Donate Amount"),mapSheetData.get("Program2 Donate Amount"),
				mapSheetData.get("Program3 Donate Amount"),mapSheetData.get("Other Program Donate Amount")};

		String ProductNames[]={mapSheetData.get("Program1"),mapSheetData.get("Program2"),mapSheetData.get("Program3"),
				mapSheetData.get("Other Program")};

		test.asm_Donate.verifyProductNamesFromIweb(productNameKey);
		DonateProgramNames=test.asm_Donate.checkDonationPrograms(ProductNames);
		test.asm_Donate.donateMoneyToProgram(DonateProgramNames,Amount);
		test.asm_Donate.verifyTotalAmountEnteredOnProductDonation(Amount);
	}

	@Test
	public void Step04_TC04_Navigate_To_ContactInfo_Page_And_Login()
	{
		uniquelastname=mapSheetData.get("Guest_LastName")+System.currentTimeMillis();
		test.asm_Donate.clickOnLoginButtonForSpecifiedUser(memberLoginDetails,mapSheetData.get("ValidEmailAddress"));
		test.asm_Donate. enterGuestRequiredDetailsInForm(memberLoginDetails,mapSheetData.get("Guest_FirstName"),uniquelastname,
				mapSheetData.get("ValidEmailAddress"), mapSheetData.get("Guest_Phone"),mapSheetData.get("Guest_Address"),mapSheetData.get("Guest_City"),
				mapSheetData.get("Guest_State"),mapSheetData.get("Guest_ZipCode"),mapSheetData.get("Guest_Country"));
	}

	@Test
	public void Step05_TC05_Navigate_To_Confirm_Your_Donation_Page_And_Verify_Details()
	{


		String  Amount[]={mapSheetData.get("Program1 Donate Amount"),
				mapSheetData.get("Program2 Donate Amount"),mapSheetData.get("Program3 Donate Amount"),
				mapSheetData.get("Other Program Donate Amount")};

		test.asm_Donate.verifyProductDetailsOnConfirmDonationPage(DonateProgramNames,Amount);
		test.asm_Donate.verifyTotalAmountOnDonationPage(Amount,mapSheetData.get("Pledge_Months"));
		test.asm_Donate.sendCardOrEmailFromSpreadsheet(mapSheetData.get("SelectSendCardOption?"),
				mapSheetData.get("InHonorOf?"),mapSheetData.get("InMemoryOf?"),
				mapSheetData.get("DonotSendCard?"),mapSheetData.get("SendCardVia_Email?")
				,mapSheetData.get("SendCardVia_PostalMail?"));

		test.asm_Donate.selectASendCardMethod(mapSheetData.get("SelectSendCardOption?"),mapSheetData.get("DonotSendCard?"),
				mapSheetData.get("SendCardVia_Email?"),mapSheetData.get("SendCardVia_PostalMail?"),
				mapSheetData.get("Recepient_Email"),mapSheetData.get("Personalised_Message"),mapSheetData.get("PostalMail_To")
				,mapSheetData.get("PostalMail_Address"),mapSheetData.get("PostalMail_City"),
				mapSheetData.get("PostalMail_State"),mapSheetData.get("PostalMail_ZipCode"));

		test.asm_Donate.BreakMyDonationForMonthlyPayments(mapSheetData.get("BreakMyDonation?"),mapSheetData.get("Pledge_Months"));
		test.asm_Donate.enterPaymentDetailsForACSDonateSmoke(memberLoginDetails,YamlReader
				.getYamlValue("creditCardInfo.Type"),mapSheetData.get("Guest_FirstName")+" "+uniquelastname, YamlReader
				.getYamlValue("creditCardInfo.Number"), YamlReader.getYamlValue("creditCardInfo.cvv-number"), 
				mapSheetData.get("CreditCardExpiration_Month"),mapSheetData.get("CreditCardExpiration_Year"));

		test.asm_Donate.verifyCurrentPage("confirmation");

	}

	@Test
	public void Step06_TC06_verify_Details_At_Confirm_Your_Donation_Page()
	{

		String  Amount[]={mapSheetData.get("Program1 Donate Amount"),
				mapSheetData.get("Program2 Donate Amount"),mapSheetData.get("Program3 Donate Amount"),
				mapSheetData.get("Other Program Donate Amount")};

		test.asm_Donate.verifyProductPledgedSummaryOnConfirmDonationPage(DonateProgramNames,Amount,mapSheetData.get("Pledge_Months"));
		TotalAmountMap=test.asm_Donate.verifyTotalAmountOnDonationPage(Amount,mapSheetData.get("Pledge_Months"));
		test.asm_Donate.verifyThankyouMessageAfterDonation();
		test.asm_Donate.verifyPrintReceiptMessageAfterDonation();
		test.asm_Donate.verifyConfirmationEmailAfterDonation(mapSheetData.get("ValidEmailAddress"));
	}

	@Test
	public void Step07_TC_07_Navigate_To_Iweb_And_Retreive_Lastest_Invoice_For_Donor()
	{
		test.navigateToIWEBUrlOnNewBrowserTab(app_url_IWEB);
		test.memberShipPage.navigateToMemberLatestInvoicePage(memberLoginDetails);
		if(memberLoginDetails.get(0).equals("2"))
		{
			test.homePageIWEB.clickFindForIndividualsSearch();
			test.individualsPage.enterFieldValue("Last Name",
					uniquelastname);
			test.individualsPage.enterFieldValue("First Name",
					mapSheetData.get("Guest_FirstName"));
			test.individualsPage.clickGoButton();
			test.individualsPage.verifyMemberIsNotCreated();
			test.individualsPage.navigateToInvoicesMenuOnHoveringMore();
			test.individualsPage.clickOnInvoiceArrowButtonToNavigateFinancialPage();
			
		}
	
		
	}
	
	@Test
	public void Step08_TC_08_Navigate_To_Iweb_And_Retreive_Lastest_Invoice_For_Guest()
	{
		String  Amount[]={mapSheetData.get("Program1 Donate Amount"),
				mapSheetData.get("Program2 Donate Amount"),mapSheetData.get("Program3 Donate Amount"),
				mapSheetData.get("Other Program Donate Amount")};
		test.invoicePage.validateBalanceAndTotalForInvoice(TotalAmountMap);
		test.invoicePage.verifyEmailStatusAsDefinedInSheet(mapSheetData.get("SendCardVia_Email?"));
		test.invoicePage.expandDetailsMenu("line items");
		test.invoicePage.verfifyproductDisplayNamesAndCodesInsideLineItems(TotalAmountMap,Amount,productNameKey,mapIwebProductDetails);
		test.invoicePage.collapseDetailsMenu("line items");
		test.invoicePage.expandDetailsMenu("acs giving invoice details");
		test.invoicePage.verifyGivingInvoiceDetails(mapSheetData.get("SendCardVia_Email?"),mapSheetData.get("SendCardVia_PostalMail?"),
				mapSheetData.get("DonotSendCard?"),	mapSheetData.get("Other Program"));
		test.invoicePage.collapseDetailsMenu("acs giving invoice details");
	}
	


	@BeforeClass
	public void OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_givingDonate = getYamlValue("app_url_givingDonate");
		app_url_IWEB=getYamlValue("app_url_IWEB");

	}
}