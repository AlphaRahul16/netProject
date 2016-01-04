package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
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
	List<String> memberLoginDetails;
	String caseId="4";

	@Test
	public void Step01_TC01_Launch_IWeb_Application_And_Navigate_To_Funds() {
		test.navigateToIWEBUrlOnNewBrowserTab(app_url_IWEB);
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

		String[] loginAs={test.homePage.getACS_Giving_SheetValue(caseId,"Login as Member?"),
				test.homePage.getACS_Giving_SheetValue(caseId,"Login as Non Member?"),
				test.homePage.getACS_Giving_SheetValue(caseId,"Continue as Guest?")};  

		memberLoginDetails=	test.memberShipPage.loginUsingValueFromSheet(loginAs);

		test.asm_Donate.getUserAddressDetails(memberLoginDetails,"PhoneNo","Email","Address");
	}

	@Test
	public void Step03_TC03_Launch_Eweb_Giving_Application()
	{
		test.launchApplication(app_url_givingDonate);
		String  Amount[]={test.homePage.getACS_Giving_SheetValue(caseId,"Program1 Donate Amount"),
				test.homePage.getACS_Giving_SheetValue(caseId,"Program2 Donate Amount"),test.homePage.getACS_Giving_SheetValue(caseId,"Program3 Donate Amount"),
				test.homePage.getACS_Giving_SheetValue(caseId,"Other Program Donate Amount")};

		String ProductNames[]={test.homePage.getACS_Giving_SheetValue(caseId,"Program1"),
				test.homePage.getACS_Giving_SheetValue(caseId,"Program2"),test.homePage.getACS_Giving_SheetValue(caseId,"Program3"),
				test.homePage.getACS_Giving_SheetValue(caseId,"Other Program")};

		test.asm_Donate.verifyProductNamesFromIweb(productNameKey);

		String ProgramNames[]=test.asm_Donate.checkDonationPrograms(ProductNames);
		test.asm_Donate.donateMoneyToProgram(ProgramNames,Amount);
		test.asm_Donate.verifyTotalAmountDonated(Amount);
	}

	@Test
	public void Step04_TC04_Navigate_To_ContactInfo_Page_And_Login()
	{
		test.asm_Donate.clickOnLoginButtonForSpecifiedUser(memberLoginDetails,test.homePage.getACS_Giving_SheetValue(caseId,"ValidEmailAddress"));

		test.asm_Donate. enterGuestRequiredDetailsInForm(memberLoginDetails,test.homePage.getACS_Giving_SheetValue(caseId,"Guest_FirstName"), test.homePage.getACS_Giving_SheetValue(caseId,"Guest_LastName")
				, test.homePage.getACS_Giving_SheetValue(caseId,"ValidEmailAddress"), test.homePage.getACS_Giving_SheetValue(caseId,"Guest_Phone")
				,test.homePage.getACS_Giving_SheetValue(caseId,"Guest_Address"),test.homePage.getACS_Giving_SheetValue(caseId,"Guest_City"),
				test.homePage.getACS_Giving_SheetValue(caseId,"Guest_State"),test.homePage.getACS_Giving_SheetValue(caseId,"Guest_ZipCode"),
				test.homePage.getACS_Giving_SheetValue(caseId,"Guest_Country"));
	}

	@Test
	public void Step05_TC05_Navigate_To_Confirm_Your_Donation_Page_And_Verify_Details()
	{
		String ProductNames[]={test.homePage.getACS_Giving_SheetValue(caseId,"Program1"),
				test.homePage.getACS_Giving_SheetValue(caseId,"Program2"),test.homePage.getACS_Giving_SheetValue(caseId,"Program3"),
				test.homePage.getACS_Giving_SheetValue(caseId,"Other Program")};

		String  Amount[]={test.homePage.getACS_Giving_SheetValue(caseId,"Program1 Donate Amount"),
				test.homePage.getACS_Giving_SheetValue(caseId,"Program2 Donate Amount"),test.homePage.getACS_Giving_SheetValue(caseId,"Program3 Donate Amount"),
				test.homePage.getACS_Giving_SheetValue(caseId,"Other Program Donate Amount")};

		test.asm_Donate.verifyProductDetailsOnConfirmDonationPage(ProductNames,Amount);
		test.asm_Donate.verifyTotalAmountOnDonationPage(Amount);

		test.asm_Donate.sendCardOrEmailFromSpreadsheet(test.homePage.getACS_Giving_SheetValue(caseId,"SelectSendCardOption?"),
				test.homePage.getACS_Giving_SheetValue(caseId,"InHonorOf?"),test.homePage.getACS_Giving_SheetValue(caseId,"InMemoryOf?"),
				test.homePage.getACS_Giving_SheetValue(caseId,"DonotSendCard?"),test.homePage.getACS_Giving_SheetValue(caseId,"SendCardVia_Email?")
				,test.homePage.getACS_Giving_SheetValue(caseId,"SendCardVia_PostalMail?"));

		test.asm_Donate.selectASendCardMethod(test.homePage.getACS_Giving_SheetValue(caseId,"DonotSendCard?"),
				test.homePage.getACS_Giving_SheetValue(caseId,"SendCardVia_Email?"),test.homePage.getACS_Giving_SheetValue(caseId,"SendCardVia_PostalMail?"),
				test.homePage.getACS_Giving_SheetValue(caseId,"Recepient_Email"),test.homePage.getACS_Giving_SheetValue(caseId,"Personalised_Message"),test.homePage.getACS_Giving_SheetValue(caseId,"PostalMail_To")
				,test.homePage.getACS_Giving_SheetValue(caseId,"PostalMail_Address"),test.homePage.getACS_Giving_SheetValue(caseId,"PostalMail_City"),
				test.homePage.getACS_Giving_SheetValue(caseId,"PostalMail_State"),test.homePage.getACS_Giving_SheetValue(caseId,"PostalMail_ZipCode"));
		
		test.asm_Donate.enterPaymentDetailsForACSDonateSmoke(memberLoginDetails,YamlReader
				.getYamlValue("creditCardInfo.Type"),test.homePage.getACS_Giving_SheetValue(caseId,"Guest_FirstName")+" "+test.homePage.getACS_Giving_SheetValue(caseId,"Guest_LastName"), YamlReader
				.getYamlValue("creditCardInfo.Number"), YamlReader
				.getYamlValue("creditCardInfo.cvv-number"), 
				test.homePage.getACS_Giving_SheetValue(caseId,"CreditCardExpiration_Month"),
		test.homePage.getACS_Giving_SheetValue(caseId,"CreditCardExpiration_Year"));
		
		test.asm_Donate.verifyCurrentPage("confirmation");

	}
	
	@Test
	public void Step06_TC06_verify_Details_At_Confirm_Your_Donation_Page()
	{
		String ProductNames[]={test.homePage.getACS_Giving_SheetValue(caseId,"Program1"),
				test.homePage.getACS_Giving_SheetValue(caseId,"Program2"),test.homePage.getACS_Giving_SheetValue(caseId,"Program3"),
				test.homePage.getACS_Giving_SheetValue(caseId,"Other Program")};

		String  Amount[]={test.homePage.getACS_Giving_SheetValue(caseId,"Program1 Donate Amount"),
				test.homePage.getACS_Giving_SheetValue(caseId,"Program2 Donate Amount"),test.homePage.getACS_Giving_SheetValue(caseId,"Program3 Donate Amount"),
				test.homePage.getACS_Giving_SheetValue(caseId,"Other Program Donate Amount")};

		test.asm_Donate.verifyProductDetailsOnConfirmDonationPage(ProductNames,Amount);
		test.asm_Donate.verifyTotalAmountOnDonationPage(Amount);
		test.asm_Donate.verifyThankyouMessageAfterDonation();
		test.asm_Donate.verifyPrintReceiptMessageAfterDonation();
		test.asm_Donate.verifyConfirmationEmailAfterDonation(test.homePage.getACS_Giving_SheetValue(caseId,"ValidEmailAddress"));
		
	}
	
	

	@BeforeClass
	public void OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_givingDonate = getYamlValue("app_url_givingDonate");
		app_url_IWEB=getYamlValue("app_url_IWEB");

	}
}