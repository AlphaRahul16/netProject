package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.Map;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.DataProvider;
import com.qait.automation.utils.YamlReader;
import com.qait.keywords.YamlInformationProvider;

public class ASM_Giving_Green_Smoke {

	TestSessionInitiator test;
	static String app_url_GivinGreen;
	static YamlInformationProvider getASM_givingGreen, getASM_Donate;
	static Map<String, Object> mapGivingGreenSmoke, mapDonateSmoke;

	String headerName = this.getClass().getSimpleName();
//
//	@Test
//	public void Step03_TC01_Enter_Invalid_UserName_And_Verify_ASM_Error() {
//
//		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
//				.getStackTrace()[1].getMethodName());
//
//		test.asm_givingGreen.loginIntoApplicationWithACS_ID(
//				DataProvider.getColumnData(tcId, headerName),
//				getASM_givingGreen.getASM_GivingGreen("password"));
//		test.asmErrorPage.verifyASMError(YamlReader
//				.getYamlValue("ASM_URLRejectedErrorMsz"));
//	}

//	@Test
//	public void Step01_TC02_Enter_Amount_And_Verify_ASM_Error_Not_Present() {
//		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
//				.getStackTrace()[1].getMethodName());
//		test.asm_givingGreen.selectAmount(DataProvider.getColumnData(tcId,
//				headerName));
//		test.asm_givingGreen.loginIntoApplicationWithACS_ID(
//				getASM_givingGreen.getASM_GivingGreen("userName"),
//				getASM_givingGreen.getASM_GivingGreen("password"));
//		test.asm_givingGreen.clickOnContinueButton();
//		test.asm_givingGreen.verifyCurrentPage("Confirm your donation");
//		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
//				.getYamlValue("ASM_URLRejectedErrorMsz"));
//	}
//
//	@Test
//	public void Step02_TC03_Enter_Other_Amount_And_Verify_ASM_Error_Not_Present() {
//		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
//				.getStackTrace()[1].getMethodName());
//		test.asm_givingGreen.selectAndEnterOtherAmount(DataProvider
//				.getColumnData(tcId, headerName));
//		test.asm_givingGreen.loginIntoApplicationWithACS_ID(
//				getASM_givingGreen.getASM_GivingGreen("userName"),
//				getASM_givingGreen.getASM_GivingGreen("password"));
//		test.asm_givingGreen.clickOnContinueButton();
//		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
//				.getYamlValue("ASM_URLRejectedErrorMsz"));
//	}
//
//	@Test
//	public void Step04_TC04_Enter_Invalid_Username_And_Verify_ASM_Error_Not_Present() {
//		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
//				.getStackTrace()[1].getMethodName());
//		test.asm_givingGreen.loginIntoApplicationWithACS_ID(
//				DataProvider.getColumnData(tcId, headerName),
//				getASM_givingGreen.getASM_GivingGreen("password"));
//		//test.asm_givingGreen.clickOnContinueButton();
//		test.asm_givingGreen.verifyLoginErrorMessagePresent(getASM_givingGreen
//				.getASM_GivingGreen("LoginErrorMessage"));
//		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
//				.getYamlValue("ASM_URLRejectedErrorMsz"));
//	}

	@Test
	public void Step05_TC05_Enter_InValid_MemberNumber_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_givingGreen.loginIntoApplicationWith_Lastname_Member_Number(
				getASM_givingGreen.getASM_GivingGreen("lastName"),
				DataProvider.getColumnData(tcId, headerName));
		//test.asm_givingGreen.clickOnContinueButton();
		test.asm_givingGreen.verifyLoginErrorMessagePresent(getASM_givingGreen
				.getASM_GivingGreen("ErrorMessage_lastname_memberNumber"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step06_TC06_Enter_Valid_Username_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_givingGreen.loginIntoApplicationWithACS_ID(
				DataProvider.getColumnData(tcId, headerName),
				getASM_givingGreen.getASM_GivingGreen("password"));
		test.asm_givingGreen.clickOnContinueButton();
		test.asm_givingGreen.verifyCurrentPage("Confirm your donation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step07_TC07_Enter_Valid_lastname_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_givingGreen.loginIntoApplicationWith_Lastname_Member_Number(
				DataProvider.getColumnData(tcId, headerName),
				getASM_givingGreen.getASM_GivingGreen("memberNumber"));
		test.asm_givingGreen.clickOnContinueButton();
		test.asm_givingGreen.verifyCurrentPage("Confirm your donation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step08_TC08_Enter_InValid_Email_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_givingGreen.loginIntoApplicationWithACS_ID(
				getASM_givingGreen.getASM_GivingGreen("userName"),
				getASM_givingGreen.getASM_GivingGreen("password"));
		test.asm_givingGreen.enterRequiredDetailsInNonMemberForm(
				DataProvider.getColumnData(tcId, headerName),
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("phone"),
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("address"),
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("city"),
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("state"),
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("zipcode"),
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("country"));
		test.asm_givingGreen.clickOnContinueButton();
		test.asm_givingGreen.verifyErrorMessage(getASM_givingGreen
				.getASM_GivingGreen("emailErrorMessage"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));

	}

	@Test
	public void Step09_TC09_Enter_Valid_Email_At_Contact_Info_Page_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_givingGreen.loginIntoApplicationWithACS_ID(
				getASM_givingGreen.getASM_GivingGreen("userName"),
				getASM_givingGreen.getASM_GivingGreen("password"));
		test.asm_givingGreen.enterRequiredDetailsInNonMemberForm(
				DataProvider.getColumnData(tcId, headerName),
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("phone"),
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("address"),
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("city"),
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("state"),
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("zipcode"),
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("country"));
		test.asm_givingGreen.clickOnContinueButton();
		test.asm_givingGreen.verifyCurrentPage("Confirm your donation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));

	}

	@Test
	public void Step11_TC11_Enter_Valid_Address_At_Contact_Info_Page_And_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_givingGreen.loginIntoApplicationWithACS_ID(
				getASM_givingGreen.getASM_GivingGreen("userName"),
				getASM_givingGreen.getASM_GivingGreen("password"));
		test.asm_givingGreen.enterRequiredDetailsInNonMemberForm(
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("email"),
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("phone"),
				DataProvider.getColumnData(tcId, headerName),
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("city"),
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("state"),
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("zipcode"),
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("country"));
		test.asm_givingGreen.clickOnContinueButton();
		test.asm_givingGreen.verifyCurrentPage("Confirm your donation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));

	}

	@Test
	public void Step12_TC12_Enter_Valid_City_At_Contact_Info_Page_And_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_givingGreen.loginIntoApplicationWithACS_ID(
				getASM_givingGreen.getASM_GivingGreen("userName"),
				getASM_givingGreen.getASM_GivingGreen("password"));
		test.asm_givingGreen.enterRequiredDetailsInNonMemberForm(
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("email"),
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("phone"),
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("address"),
				DataProvider.getColumnData(tcId, headerName),
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("state"),
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("zipcode"),
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("country"));
		test.asm_givingGreen.clickOnContinueButton();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));

	}

	@Test
	public void Step13_TC13_Enter_Valid_zipcode_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_givingGreen.loginIntoApplicationWithACS_ID(
				getASM_givingGreen.getASM_GivingGreen("userName"),
				getASM_givingGreen.getASM_GivingGreen("password"));
		test.asm_givingGreen.enterRequiredDetailsInNonMemberForm(
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("email"),
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("phone"),
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("address"),
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("city"),
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("state"),
				DataProvider.getColumnData(tcId, headerName),
				getASM_givingGreen.getASM_GivingGreen_ContactInfo("country"));
		test.asm_givingGreen.clickOnContinueButton();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));

	}

	@Test
	public void Step14_TC14_Enter_InHonorOf_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_givingGreen.loginIntoApplicationWithACS_ID(
				getASM_givingGreen.getASM_GivingGreen("userName"),
				getASM_givingGreen.getASM_GivingGreen("password"));
		test.asm_givingGreen.clickOnContinueButton();
		test.asm_givingGreen.verifyCurrentPage("Confirm your donation");
		test.asm_Donate.enterRequiredDetailsToConfirmYourDonation("honor",
				DataProvider.getColumnData(tcId, headerName), "Email",
				getASM_givingGreen
						.getASM_Donate_ConfirmYourDonation("recipientEmail"));
		test.asm_Donate.enterPaymentDetails(YamlReader
				.getYamlValue("creditCardInfo.Type"), YamlReader
				.getYamlValue("creditCardInfo.Holder-name"), YamlReader
				.getYamlValue("creditCardInfo.Number"), YamlReader
				.getYamlValue("creditCardInfo.cvv-number"), getASM_givingGreen
				.getASM_Donate_ConfirmYourDonation("expirationDate"),
				getASM_givingGreen
						.getASM_Donate_ConfirmYourDonation("expirationYear"));
		test.asm_givingGreen.verifyCurrentPage("confirmation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));

	}

	@Test
	public void Step15_TC15_Enter_InMemoryOf_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_givingGreen.loginIntoApplicationWithACS_ID(
				getASM_givingGreen.getASM_GivingGreen("userName"),
				getASM_givingGreen.getASM_GivingGreen("password"));
		test.asm_givingGreen.clickOnContinueButton();
		test.asm_givingGreen.verifyCurrentPage("Confirm your donation");
		test.asm_Donate.enterRequiredDetailsToConfirmYourDonation("memory",
				DataProvider.getColumnData(tcId, headerName), "Email",
				getASM_givingGreen
						.getASM_Donate_ConfirmYourDonation("recipientEmail"));
		test.asm_Donate.enterPaymentDetails(YamlReader
				.getYamlValue("creditCardInfo.Type"), YamlReader
				.getYamlValue("creditCardInfo.Holder-name"), YamlReader
				.getYamlValue("creditCardInfo.Number"), YamlReader
				.getYamlValue("creditCardInfo.cvv-number"), getASM_givingGreen
				.getASM_Donate_ConfirmYourDonation("expirationDate"),
				getASM_givingGreen
						.getASM_Donate_ConfirmYourDonation("expirationYear"));
		test.asm_givingGreen.verifyCurrentPage("confirmation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));

	}

	@Test
	public void Step18_TC18_Enter_Invalid_Recipient_Email_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_givingGreen.loginIntoApplicationWithACS_ID(
				getASM_givingGreen.getASM_GivingGreen("userName"),
				getASM_givingGreen.getASM_GivingGreen("password"));
		test.asm_givingGreen.clickOnContinueButton();
		test.asm_givingGreen.verifyCurrentPage("Confirm your donation");
		test.asm_Donate.enterRequiredDetailsToConfirmYourDonation("honor",
				getASM_givingGreen
						.getASM_Donate_ConfirmYourDonation("inMemoryOf"),
				"Email", DataProvider.getColumnData(tcId, headerName));
		test.asm_Donate.enterPaymentDetails(YamlReader
				.getYamlValue("creditCardInfo.Type"), YamlReader
				.getYamlValue("creditCardInfo.Holder-name"), YamlReader
				.getYamlValue("creditCardInfo.Number"), YamlReader
				.getYamlValue("creditCardInfo.cvv-number"), getASM_givingGreen
				.getASM_Donate_ConfirmYourDonation("expirationDate"),
				getASM_givingGreen
						.getASM_Donate_ConfirmYourDonation("expirationYear"));
		test.asm_givingGreen.verifyCurrentPage("confirmation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));

	}

	@Test
	public void Step19_TC19_Enter_Valid_Recipient_Email_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_givingGreen.loginIntoApplicationWithACS_ID(
				getASM_givingGreen.getASM_GivingGreen("userName"),
				getASM_givingGreen.getASM_GivingGreen("password"));
		test.asm_givingGreen.clickOnContinueButton();
		test.asm_givingGreen.verifyCurrentPage("Confirm your donation");
		test.asm_Donate.enterRequiredDetailsToConfirmYourDonation("honor",
				getASM_givingGreen
						.getASM_Donate_ConfirmYourDonation("inMemoryOf"),
				"Email", DataProvider.getColumnData(tcId, headerName));
		test.asm_Donate.enterPaymentDetails(YamlReader
				.getYamlValue("creditCardInfo.Type"), YamlReader
				.getYamlValue("creditCardInfo.Holder-name"), YamlReader
				.getYamlValue("creditCardInfo.Number"), YamlReader
				.getYamlValue("creditCardInfo.cvv-number"), getASM_givingGreen
				.getASM_Donate_ConfirmYourDonation("expirationDate"),
				getASM_givingGreen
						.getASM_Donate_ConfirmYourDonation("expirationYear"));
		test.asm_givingGreen.verifyCurrentPage("confirmation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));

	}

	@Test
	public void Step20_TC20_Enter_Valid_Email_In_Postal_Address_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_givingGreen.loginIntoApplicationWithACS_ID(
				getASM_givingGreen.getASM_GivingGreen("userName"),
				getASM_givingGreen.getASM_GivingGreen("password"));
		test.asm_givingGreen.clickOnContinueButton();
		test.asm_givingGreen.verifyCurrentPage("Confirm your donation");
		test.asm_Donate
				.enterRequiredDetailsToConfirmYourDonation(
						"memory",
						getASM_givingGreen
								.getASM_Donate_ConfirmYourDonation("inMemoryOf"),
						"Postal", DataProvider.getColumnData(tcId, headerName),
						getASM_givingGreen
								.getASM_Donate_ConfirmYourDonation("address"),
						getASM_givingGreen
								.getASM_Donate_ConfirmYourDonation("city"),
						getASM_givingGreen
								.getASM_Donate_ConfirmYourDonation("state"),
						getASM_givingGreen
								.getASM_Donate_ConfirmYourDonation("zipCode"));
		test.asm_Donate.enterPaymentDetails(YamlReader
				.getYamlValue("creditCardInfo.Type"), YamlReader
				.getYamlValue("creditCardInfo.Holder-name"), YamlReader
				.getYamlValue("creditCardInfo.Number"), YamlReader
				.getYamlValue("creditCardInfo.cvv-number"), getASM_givingGreen
				.getASM_Donate_ConfirmYourDonation("expirationDate"),
				getASM_givingGreen
						.getASM_Donate_ConfirmYourDonation("expirationYear"));
		test.asm_givingGreen.verifyCurrentPage("confirmation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));

	}

	@Test
	public void Step21_TC21_Enter_Valid_Addres_In_Postal_Address_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_givingGreen.loginIntoApplicationWithACS_ID(
				getASM_givingGreen.getASM_GivingGreen("userName"),
				getASM_givingGreen.getASM_GivingGreen("password"));
		test.asm_givingGreen.clickOnContinueButton();
		test.asm_givingGreen.verifyCurrentPage("Confirm your donation");
		test.asm_Donate
				.enterRequiredDetailsToConfirmYourDonation(
						"memory",
						getASM_givingGreen
								.getASM_Donate_ConfirmYourDonation("inMemoryOf"),
						"Postal",
						getASM_givingGreen
								.getASM_Donate_ConfirmYourDonation("recipientEmail"),
						DataProvider.getColumnData(tcId, headerName),
						getASM_givingGreen
								.getASM_Donate_ConfirmYourDonation("city"),
						getASM_givingGreen
								.getASM_Donate_ConfirmYourDonation("state"),
						getASM_givingGreen
								.getASM_Donate_ConfirmYourDonation("zipCode"));
		test.asm_Donate.enterPaymentDetails(YamlReader
				.getYamlValue("creditCardInfo.Type"), YamlReader
				.getYamlValue("creditCardInfo.Holder-name"), YamlReader
				.getYamlValue("creditCardInfo.Number"), YamlReader
				.getYamlValue("creditCardInfo.cvv-number"), getASM_givingGreen
				.getASM_Donate_ConfirmYourDonation("expirationDate"),
				getASM_givingGreen
						.getASM_Donate_ConfirmYourDonation("expirationYear"));
		test.asm_givingGreen.verifyCurrentPage("confirmation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));

	}

	@Test
	public void Step22_TC22_Enter_Valid_City_In_Postal_Address_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_givingGreen.loginIntoApplicationWithACS_ID(
				getASM_givingGreen.getASM_GivingGreen("userName"),
				getASM_givingGreen.getASM_GivingGreen("password"));
		test.asm_givingGreen.clickOnContinueButton();
		test.asm_givingGreen.verifyCurrentPage("Confirm your donation");
		test.asm_Donate
				.enterRequiredDetailsToConfirmYourDonation(
						"memory",
						getASM_givingGreen
								.getASM_Donate_ConfirmYourDonation("inMemoryOf"),
						"Postal",
						getASM_givingGreen
								.getASM_Donate_ConfirmYourDonation("recipientEmail"),
						getASM_givingGreen
								.getASM_Donate_ConfirmYourDonation("address"),
						DataProvider.getColumnData(tcId, headerName),
						getASM_givingGreen
								.getASM_Donate_ConfirmYourDonation("state"),
						getASM_givingGreen
								.getASM_Donate_ConfirmYourDonation("zipCode"));
		test.asm_Donate.enterPaymentDetails(YamlReader
				.getYamlValue("creditCardInfo.Type"), YamlReader
				.getYamlValue("creditCardInfo.Holder-name"), YamlReader
				.getYamlValue("creditCardInfo.Number"), YamlReader
				.getYamlValue("creditCardInfo.cvv-number"), getASM_givingGreen
				.getASM_Donate_ConfirmYourDonation("expirationDate"),
				getASM_givingGreen
						.getASM_Donate_ConfirmYourDonation("expirationYear"));
		test.asm_givingGreen.verifyCurrentPage("confirmation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));

	}

	@Test
	public void Step23_TC23_Enter_Valid_State_In_Postal_Address_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_givingGreen.loginIntoApplicationWithACS_ID(
				getASM_givingGreen.getASM_GivingGreen("userName"),
				getASM_givingGreen.getASM_GivingGreen("password"));
		test.asm_givingGreen.clickOnContinueButton();
		test.asm_givingGreen.verifyCurrentPage("Confirm your donation");
		test.asm_Donate
				.enterRequiredDetailsToConfirmYourDonation(
						"memory",
						getASM_givingGreen
								.getASM_Donate_ConfirmYourDonation("inMemoryOf"),
						"Postal",
						getASM_givingGreen
								.getASM_Donate_ConfirmYourDonation("recipientEmail"),
						getASM_givingGreen
								.getASM_Donate_ConfirmYourDonation("address"),
						getASM_givingGreen
								.getASM_Donate_ConfirmYourDonation("city"),
						DataProvider.getColumnData(tcId, headerName),
						getASM_givingGreen
								.getASM_Donate_ConfirmYourDonation("zipCode"));
		test.asm_Donate.enterPaymentDetails(YamlReader
				.getYamlValue("creditCardInfo.Type"), YamlReader
				.getYamlValue("creditCardInfo.Holder-name"), YamlReader
				.getYamlValue("creditCardInfo.Number"), YamlReader
				.getYamlValue("creditCardInfo.cvv-number"), getASM_givingGreen
				.getASM_Donate_ConfirmYourDonation("expirationDate"),
				getASM_givingGreen
						.getASM_Donate_ConfirmYourDonation("expirationYear"));
		test.asm_givingGreen.verifyCurrentPage("confirmation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));

	}

	@Test
	public void Step24_TC24_Enter_Valid_ZipCode_In_Postal_Address_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_givingGreen.loginIntoApplicationWithACS_ID(
				getASM_givingGreen.getASM_GivingGreen("userName"),
				getASM_givingGreen.getASM_GivingGreen("password"));
		test.asm_givingGreen.clickOnContinueButton();
		test.asm_givingGreen.verifyCurrentPage("Confirm your donation");
		test.asm_Donate
				.enterRequiredDetailsToConfirmYourDonation(
						"memory",
						getASM_givingGreen
								.getASM_Donate_ConfirmYourDonation("inMemoryOf"),
						"Postal",
						getASM_givingGreen
								.getASM_Donate_ConfirmYourDonation("recipientEmail"),
						getASM_givingGreen
								.getASM_Donate_ConfirmYourDonation("address"),
						getASM_givingGreen
								.getASM_Donate_ConfirmYourDonation("city"),
						getASM_givingGreen
								.getASM_Donate_ConfirmYourDonation("state"),
						DataProvider.getColumnData(tcId, headerName));
		test.asm_Donate.enterPaymentDetails(YamlReader
				.getYamlValue("creditCardInfo.Type"), YamlReader
				.getYamlValue("creditCardInfo.Holder-name"), YamlReader
				.getYamlValue("creditCardInfo.Number"), YamlReader
				.getYamlValue("creditCardInfo.cvv-number"), getASM_givingGreen
				.getASM_Donate_ConfirmYourDonation("expirationDate"),
				getASM_givingGreen
						.getASM_Donate_ConfirmYourDonation("expirationYear"));
		test.asm_givingGreen.verifyCurrentPage("confirmation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));

	}

	@Test
	public void Step25_TC25_Enter_Valid_Credit_Card_Number_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_givingGreen.loginIntoApplicationWithACS_ID(
				getASM_givingGreen.getASM_GivingGreen("userName"),
				getASM_givingGreen.getASM_GivingGreen("password"));
		test.asm_givingGreen.clickOnContinueButton();
		test.asm_givingGreen.verifyCurrentPage("Confirm your donation");
		test.asm_Donate.enterPaymentDetails(YamlReader
				.getYamlValue("creditCardInfo.Type"), YamlReader
				.getYamlValue("creditCardInfo.Holder-name"), DataProvider
				.getColumnData(tcId, headerName), YamlReader
				.getYamlValue("creditCardInfo.cvv-number"), getASM_givingGreen
				.getASM_Donate_ConfirmYourDonation("expirationDate"),
				getASM_givingGreen
						.getASM_Donate_ConfirmYourDonation("expirationYear"));
		test.asm_givingGreen.verifyCurrentPage("confirmation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));

	}

	@Test
	public void Step26_TC26_Enter_InValid_Credit_Card_Number_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_givingGreen.loginIntoApplicationWithACS_ID(
				getASM_givingGreen.getASM_GivingGreen("userName"),
				getASM_givingGreen.getASM_GivingGreen("password"));

		test.asm_givingGreen.clickOnContinueButton();
		test.asm_givingGreen.verifyCurrentPage("Confirm your donation");

		test.asm_Donate.enterPaymentDetails(YamlReader
				.getYamlValue("creditCardInfo.Type"), YamlReader
				.getYamlValue("creditCardInfo.Holder-name"), DataProvider
				.getColumnData(tcId, headerName), YamlReader
				.getYamlValue("creditCardInfo.cvv-number"), getASM_givingGreen
				.getASM_Donate_ConfirmYourDonation("expirationDate"),
				getASM_givingGreen
						.getASM_Donate_ConfirmYourDonation("expirationYear"));
		test.asm_givingGreen.verifyCreditCardErrorMessage(getASM_givingGreen
				.getASM_GivingGreen("creditCardErrorMessage"));
		test.asm_givingGreen.verifyCurrentPage("Confirm your donation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));

	}

	@Test
	public void Step27_TC27_Enter_Valid_CVV_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_givingGreen.loginIntoApplicationWithACS_ID(
				getASM_givingGreen.getASM_GivingGreen("userName"),
				getASM_givingGreen.getASM_GivingGreen("password"));

		test.asm_givingGreen.clickOnContinueButton();
		test.asm_givingGreen.verifyCurrentPage("Confirm your donation");

		test.asm_Donate.enterPaymentDetails(YamlReader
				.getYamlValue("creditCardInfo.Type"), YamlReader
				.getYamlValue("creditCardInfo.Holder-name"), YamlReader
				.getYamlValue("creditCardInfo.Number"), DataProvider
				.getColumnData(tcId, headerName), getASM_givingGreen
				.getASM_Donate_ConfirmYourDonation("expirationDate"),
				getASM_givingGreen
						.getASM_Donate_ConfirmYourDonation("expirationYear"));
		test.asm_givingGreen.verifyCurrentPage("confirmation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));

	}

	@Test
	public void Step28_TC28_Enter_Valid_First_Name_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_Donate.clickOnContinueAsGuest();
		test.asm_Donate.enterRequiredDetailsInNonMemberForm(
				DataProvider.getColumnData(tcId, headerName),
				getASM_Donate.getASM_Donate_ContactInfo("lastName"),
				getASM_Donate.getASM_Donate_ContactInfo("email"),
				getASM_Donate.getASM_Donate_ContactInfo("phone"),
				getASM_Donate.getASM_Donate_ContactInfo("address"),
				getASM_Donate.getASM_Donate_ContactInfo("city"),
				getASM_Donate.getASM_Donate_ContactInfo("state"),
				getASM_Donate.getASM_Donate_ContactInfo("zipcode"),
				getASM_Donate.getASM_Donate_ContactInfo("country"));
		test.asm_givingGreen.verifyCurrentPage("Confirm your donation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step29_TC29_Enter_Valid_Last_Name_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_Donate.clickOnContinueAsGuest();
		test.asm_Donate.enterRequiredDetailsInNonMemberForm(
				getASM_Donate.getASM_Donate_ContactInfo("firstName"),
				DataProvider.getColumnData(tcId, headerName),
				getASM_Donate.getASM_Donate_ContactInfo("email"),
				getASM_Donate.getASM_Donate_ContactInfo("phone"),
				getASM_Donate.getASM_Donate_ContactInfo("address"),
				getASM_Donate.getASM_Donate_ContactInfo("city"),
				getASM_Donate.getASM_Donate_ContactInfo("state"),
				getASM_Donate.getASM_Donate_ContactInfo("zipcode"),
				getASM_Donate.getASM_Donate_ContactInfo("country"));
		test.asm_givingGreen.verifyCurrentPage("Confirm your donation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step30_TC30_Enter_Valid_Email_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_Donate.clickOnContinueAsGuest();
		test.asm_Donate.enterRequiredDetailsInNonMemberForm(
				getASM_Donate.getASM_Donate_ContactInfo("firstName"),
				getASM_Donate.getASM_Donate_ContactInfo("lastName"),
				DataProvider.getColumnData(tcId, headerName),
				getASM_Donate.getASM_Donate_ContactInfo("phone"),
				getASM_Donate.getASM_Donate_ContactInfo("address"),
				getASM_Donate.getASM_Donate_ContactInfo("city"),
				getASM_Donate.getASM_Donate_ContactInfo("state"),
				getASM_Donate.getASM_Donate_ContactInfo("zipcode"),
				getASM_Donate.getASM_Donate_ContactInfo("country"));
		test.asm_givingGreen.verifyCurrentPage("Confirm your donation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step31_TC31_Enter_InValid_Email_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_Donate.clickOnContinueAsGuest();
		test.asm_Donate.enterRequiredDetailsInNonMemberForm(
				getASM_Donate.getASM_Donate_ContactInfo("firstName"),
				getASM_Donate.getASM_Donate_ContactInfo("lastName"),
				DataProvider.getColumnData(tcId, headerName),
				getASM_Donate.getASM_Donate_ContactInfo("phone"),
				getASM_Donate.getASM_Donate_ContactInfo("address"),
				getASM_Donate.getASM_Donate_ContactInfo("city"),
				getASM_Donate.getASM_Donate_ContactInfo("state"),
				getASM_Donate.getASM_Donate_ContactInfo("zipcode"),
				getASM_Donate.getASM_Donate_ContactInfo("country"));
		test.asm_givingGreen.verifyErrorMessage(getASM_Donate
				.getASM_Donate("emailErrorMessage"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step32_TC32_Enter_Valid_Phone_Number_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_Donate.clickOnContinueAsGuest();
		test.asm_Donate.enterRequiredDetailsInNonMemberForm(
				getASM_Donate.getASM_Donate_ContactInfo("firstName"),
				getASM_Donate.getASM_Donate_ContactInfo("lastName"),
				getASM_Donate.getASM_Donate_ContactInfo("email"),
				DataProvider.getColumnData(tcId, headerName),
				getASM_Donate.getASM_Donate_ContactInfo("address"),
				getASM_Donate.getASM_Donate_ContactInfo("city"),
				getASM_Donate.getASM_Donate_ContactInfo("state"),
				getASM_Donate.getASM_Donate_ContactInfo("zipcode"),
				getASM_Donate.getASM_Donate_ContactInfo("country"));
		test.asm_Donate.verifyCurrentPage("Confirm your donation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step33_TC33_Enter_Valid_Address_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_Donate.clickOnContinueAsGuest();
		test.asm_Donate.enterRequiredDetailsInNonMemberForm(
				getASM_Donate.getASM_Donate_ContactInfo("firstName"),
				getASM_Donate.getASM_Donate_ContactInfo("lastName"),
				getASM_Donate.getASM_Donate_ContactInfo("email"),
				getASM_Donate.getASM_Donate_ContactInfo("phone"),
				DataProvider.getColumnData(tcId, headerName),
				getASM_Donate.getASM_Donate_ContactInfo("city"),
				getASM_Donate.getASM_Donate_ContactInfo("state"),
				getASM_Donate.getASM_Donate_ContactInfo("zipcode"),
				getASM_Donate.getASM_Donate_ContactInfo("country"));
		test.asm_Donate.verifyCurrentPage("Confirm your donation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step34_TC34_Enter_Valid_City_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_Donate.clickOnContinueAsGuest();
		test.asm_Donate.enterRequiredDetailsInNonMemberForm(
				getASM_Donate.getASM_Donate_ContactInfo("firstName"),
				getASM_Donate.getASM_Donate_ContactInfo("lastName"),
				getASM_Donate.getASM_Donate_ContactInfo("email"),
				getASM_Donate.getASM_Donate_ContactInfo("phone"),
				getASM_Donate.getASM_Donate_ContactInfo("address"),
				DataProvider.getColumnData(tcId, headerName),
				getASM_Donate.getASM_Donate_ContactInfo("state"),
				getASM_Donate.getASM_Donate_ContactInfo("zipcode"),
				getASM_Donate.getASM_Donate_ContactInfo("country"));
		test.asm_Donate.verifyCurrentPage("Confirm your donation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step35_TC35_Enter_Valid_State_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_Donate.clickOnContinueAsGuest();
		test.asm_Donate.enterRequiredDetailsInNonMemberForm(
				getASM_Donate.getASM_Donate_ContactInfo("firstName"),
				getASM_Donate.getASM_Donate_ContactInfo("lastName"),
				getASM_Donate.getASM_Donate_ContactInfo("email"),
				getASM_Donate.getASM_Donate_ContactInfo("phone"),
				getASM_Donate.getASM_Donate_ContactInfo("address"),
				getASM_Donate.getASM_Donate_ContactInfo("city"),
				DataProvider.getColumnData(tcId, headerName),
				getASM_Donate.getASM_Donate_ContactInfo("zipcode"),
				getASM_Donate.getASM_Donate_ContactInfo("country"));
		test.asm_Donate.verifyCurrentPage("Confirm your donation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step36_TC36_Enter_Valid_Zipcode_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_givingGreen.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_Donate.clickOnContinueAsGuest();
		test.asm_Donate.enterRequiredDetailsInNonMemberForm(
				getASM_Donate.getASM_Donate_ContactInfo("firstName"),
				getASM_Donate.getASM_Donate_ContactInfo("lastName"),
				getASM_Donate.getASM_Donate_ContactInfo("email"),
				getASM_Donate.getASM_Donate_ContactInfo("phone"),
				getASM_Donate.getASM_Donate_ContactInfo("address"),
				getASM_Donate.getASM_Donate_ContactInfo("city"),
				getASM_Donate.getASM_Donate_ContactInfo("state"),
				DataProvider.getColumnData(tcId, headerName),
				getASM_Donate.getASM_Donate_ContactInfo("country"));
		test.asm_Donate.verifyCurrentPage("Confirm your donation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@BeforeMethod
	public void launchApplication() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_GivinGreen = getYamlValue("app_url_givingGreen");
		mapGivingGreenSmoke = YamlReader
				.getYamlValues("ASM_Giving_Green_SmokeChecklistData");
		getASM_givingGreen = new YamlInformationProvider(mapGivingGreenSmoke);
		mapDonateSmoke = YamlReader
				.getYamlValues("ASM_Donate_SmokeChecklistData");
		getASM_Donate = new YamlInformationProvider(mapDonateSmoke);
		test.launchApplication(app_url_GivinGreen);
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
		test.closeBrowserSession();
	}


}
