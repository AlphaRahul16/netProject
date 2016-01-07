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

public class ASM_Donate_Smoke {

	TestSessionInitiator test;
	static String app_url_Donate;
	YamlInformationProvider getASM_Donate;
	Map<String, Object> mapDonateSmoke;

	String headerName = this.getClass().getSimpleName();

//	@Test
//	public void Step03_TC01_Enter_Invalid_UserName_And_Verify_ASM_Error() {
//		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
//				.getStackTrace()[1].getMethodName());
//		test.asm_Donate.loginIntoApplication(
//				DataProvider.getColumnData(tcId, headerName),
//				getASM_Donate.getASM_Donate("password"));
//		test.asmErrorPage.verifyASMError(YamlReader
//				.getYamlValue("ASM_URLRejectedErrorMsz"));
//	}
//
//	@Test
//	public void Step01_TC02_Enter_Donate_In_Program_And_Verify_ASM_Error_Not_Present() {
//		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
//				.getStackTrace()[1].getMethodName());
//		test.asm_Donate.enterDonateValue("Project SEED",
//				DataProvider.getColumnData(tcId, headerName));
//		test.asm_Donate.clickOnSubmitPaymentButton();
//		test.asm_Donate.verifyCurrentPage("Contact info");
//		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
//				.getYamlValue("ASM_URLRejectedErrorMsz"));
//		
//
//	}
//
//	@Test
//	public void Step02_TC03_Enter_Other_Program_And_Verify_ASM_Error_Not_Present() {
//		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
//				.getStackTrace()[1].getMethodName());
//		test.asm_Donate.enterOtherProgram(DataProvider.getColumnData(tcId,
//				headerName));
//		test.asm_Donate.enterOtherAmount(getASM_Donate
//				.getASM_MakeDonate("donate"));
//		test.asm_Donate.clickOnSubmitPaymentButton();
//		test.asm_Donate.verifyCurrentPage("Contact info");
//		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
//				.getYamlValue("ASM_URLRejectedErrorMsz"));
//
//	}
//
//	@Test
//	public void Step04_TC04_Enter_Valid_Username_And_Verify_ASM_Error_Not_Present() {
//		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
//				.getStackTrace()[1].getMethodName());
//		test.asm_Donate.loginIntoApplication(
//				DataProvider.getColumnData(tcId, headerName),
//				getASM_Donate.getASM_Donate("password"));
//		test.asm_Donate.verifyCurrentPage("Make a donation");
//		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
//				.getYamlValue("ASM_URLRejectedErrorMsz"));
//
//	}
//
//	@Test
//	public void Step05_TC05_Enter_Valid_Password_And_Verify_ASM_Error_Not_Present() {
//		String tcId = "TC05";
//		test.asm_Donate.loginIntoApplication(
//				getASM_Donate.getASM_Donate("userName"),
//				DataProvider.getColumnData(tcId, headerName));
//		test.asm_Donate.verifyCurrentPage("Make a donation");
//		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
//				.getYamlValue("ASM_URLRejectedErrorMsz"));
//
//	}
//
//	@Test
//	public void Step06_TC06_Enter_Invalid_Username_And_Verify_ASM_Error_Not_Present() {
//		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
//				.getStackTrace()[1].getMethodName());
//		test.asm_Donate.loginIntoApplication(
//				DataProvider.getColumnData(tcId, headerName),
//				getASM_Donate.getASM_Donate("password"));
//		test.asm_Donate.verifyLoginErrorMessagePresent(getASM_Donate
//				.getASM_Donate("LoginErrorMessage"));
//		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
//				.getYamlValue("ASM_URLRejectedErrorMsz"));
//	}
//
//	@Test
//	public void Step07_TC07_Enter_Valid_Firstname_And_Verify_ASM_Error_Not_Present() {
//		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
//				.getStackTrace()[1].getMethodName());
//		test.asm_Donate.verifyCurrentPage("Make a donation");
//		test.asm_Donate.enterDonateValue("Project SEED",
//				getASM_Donate.getASM_MakeDonate("donate"));
//		test.asm_Donate.clickOnSubmitPaymentButton();
//		test.asm_Donate.verifyCurrentPage("Contact info");
//		test.asm_Donate.clickOnContinueAsGuest();
//		test.asm_Donate.enterRequiredDetailsInNonMemberForm(
//				DataProvider.getColumnData(tcId, headerName),
//				getASM_Donate.getASM_Donate_ContactInfo("lastName"),
//				getASM_Donate.getASM_Donate_ContactInfo("email"),
//				getASM_Donate.getASM_Donate_ContactInfo("phone"),
//				getASM_Donate.getASM_Donate_ContactInfo("address"),
//				getASM_Donate.getASM_Donate_ContactInfo("city"),
//				getASM_Donate.getASM_Donate_ContactInfo("state"),
//				getASM_Donate.getASM_Donate_ContactInfo("zipcode"),
//				getASM_Donate.getASM_Donate_ContactInfo("country"));
//		test.asm_Donate.verifyCurrentPage("Confirm your donation");
//		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
//				.getYamlValue("ASM_URLRejectedErrorMsz"));
//	}
//
//	@Test
//	public void Step08_TC08_Enter_Valid_Lastname_And_Verify_ASM_Error_Not_Present() {
//		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
//				.getStackTrace()[1].getMethodName());
//		test.asm_Donate.verifyCurrentPage("Make a donation");
//		test.asm_Donate.enterDonateValue("Project SEED",
//				getASM_Donate.getASM_MakeDonate("donate"));
//		test.asm_Donate.clickOnSubmitPaymentButton();
//		test.asm_Donate.verifyCurrentPage("Contact info");
//		test.asm_Donate.clickOnContinueAsGuest();
//		test.asm_Donate.enterRequiredDetailsInNonMemberForm(
//				getASM_Donate.getASM_Donate_ContactInfo("firstName"),
//				DataProvider.getColumnData(tcId, headerName),
//				getASM_Donate.getASM_Donate_ContactInfo("email"),
//				getASM_Donate.getASM_Donate_ContactInfo("phone"),
//				getASM_Donate.getASM_Donate_ContactInfo("address"),
//				getASM_Donate.getASM_Donate_ContactInfo("city"),
//				getASM_Donate.getASM_Donate_ContactInfo("state"),
//				getASM_Donate.getASM_Donate_ContactInfo("zipcode"),
//				getASM_Donate.getASM_Donate_ContactInfo("country"));
//		test.asm_Donate.verifyCurrentPage("Confirm your donation");
//		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
//				.getYamlValue("ASM_URLRejectedErrorMsz"));
//	}
//
//	@Test
//	public void Step09_TC09_Enter_Valid_Email_And_Verify_ASM_Error_Not_Present() {
//		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
//				.getStackTrace()[1].getMethodName());
//
//		test.asm_Donate.verifyCurrentPage("Make a donation");
//		test.asm_Donate.enterDonateValue("Project SEED",
//				getASM_Donate.getASM_MakeDonate("donate"));
//		test.asm_Donate.clickOnSubmitPaymentButton();
//		test.asm_Donate.verifyCurrentPage("Contact info");
//		test.asm_Donate.clickOnContinueAsGuest();
//		test.asm_Donate.enterRequiredDetailsInNonMemberForm(
//				getASM_Donate.getASM_Donate_ContactInfo("firstName"),
//				getASM_Donate.getASM_Donate_ContactInfo("lastName"),
//				DataProvider.getColumnData(tcId, headerName),
//				getASM_Donate.getASM_Donate_ContactInfo("phone"),
//				getASM_Donate.getASM_Donate_ContactInfo("address"),
//				getASM_Donate.getASM_Donate_ContactInfo("city"),
//				getASM_Donate.getASM_Donate_ContactInfo("state"),
//				getASM_Donate.getASM_Donate_ContactInfo("zipcode"),
//				getASM_Donate.getASM_Donate_ContactInfo("country"));
//		test.asm_Donate.verifyCurrentPage("Confirm your donation");
//		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
//				.getYamlValue("ASM_URLRejectedErrorMsz"));
//	}
//
//	@Test
//	public void Step10_TC10_Enter_Invalid_Email_And_Verify_ASM_Error_Not_Present() {
//		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
//				.getStackTrace()[1].getMethodName());
//
//		test.asm_Donate.verifyCurrentPage("Make a donation");
//		test.asm_Donate.enterDonateValue("Project SEED",
//				getASM_Donate.getASM_MakeDonate("donate"));
//		test.asm_Donate.clickOnSubmitPaymentButton();
//		test.asm_Donate.verifyCurrentPage("Contact info");
//		test.asm_Donate.clickOnContinueAsGuest();
//		test.asm_Donate.enterRequiredDetailsInNonMemberForm(
//				getASM_Donate.getASM_Donate_ContactInfo("firstName"),
//				getASM_Donate.getASM_Donate_ContactInfo("lastName"),
//				DataProvider.getColumnData(tcId, headerName),
//				getASM_Donate.getASM_Donate_ContactInfo("phone"),
//				getASM_Donate.getASM_Donate_ContactInfo("address"),
//				getASM_Donate.getASM_Donate_ContactInfo("city"),
//				getASM_Donate.getASM_Donate_ContactInfo("state"),
//				getASM_Donate.getASM_Donate_ContactInfo("zipcode"),
//				getASM_Donate.getASM_Donate_ContactInfo("country"));
//		test.asm_Donate.verifyErrorMessage(getASM_Donate
//				.getASM_Donate("emailErrorMessage"));
//		test.asm_Donate.verifyCurrentPage("Contact info");
//		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
//				.getYamlValue("ASM_URLRejectedErrorMsz"));
//	}

	@Test
	public void Step11_TC11_Enter_Valid_Phone_Number_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());

		test.asm_Donate.verifyCurrentPage("Make a donation");
		test.asm_Donate.enterDonateValue("Project SEED",
				getASM_Donate.getASM_MakeDonate("donate"));
		test.asm_Donate.clickOnSubmitPaymentButton();
		test.asm_Donate.verifyCurrentPage("Contact info");
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
	public void Step12_TC12_Enter_Valid_Phone_Number_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());

		test.asm_Donate.verifyCurrentPage("Make a donation");
		test.asm_Donate.enterDonateValue("Project SEED",
				getASM_Donate.getASM_MakeDonate("donate"));
		test.asm_Donate.clickOnSubmitPaymentButton();
		test.asm_Donate.verifyCurrentPage("Contact info");
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
	public void Step13_TC13_Enter_Invalid_Address_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());

		test.asm_Donate.verifyCurrentPage("Make a donation");
		test.asm_Donate.enterDonateValue("Project SEED",
				getASM_Donate.getASM_MakeDonate("donate"));
		test.asm_Donate.clickOnSubmitPaymentButton();
		test.asm_Donate.verifyCurrentPage("Contact info");
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
		test.asm_Donate.verifyErrorMessage(getASM_Donate
				.getASM_Donate("addressErrorMessage"));
		test.asm_Donate.verifyCurrentPage("Contact info");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step14_TC14_Enter_Valid_Address_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());

		test.asm_Donate.verifyCurrentPage("Make a donation");
		test.asm_Donate.enterDonateValue("Project SEED",
				getASM_Donate.getASM_MakeDonate("donate"));
		test.asm_Donate.clickOnSubmitPaymentButton();
		test.asm_Donate.verifyCurrentPage("Contact info");
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
	public void Step15_TC15_Enter_Valid_City_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());

		test.asm_Donate.verifyCurrentPage("Make a donation");
		test.asm_Donate.enterDonateValue("Project SEED",
				getASM_Donate.getASM_MakeDonate("donate"));
		test.asm_Donate.clickOnSubmitPaymentButton();
		test.asm_Donate.verifyCurrentPage("Contact info");
		test.asm_Donate.clickOnSubmitPaymentButton();
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
	public void Step16_TC16_Enter_Valid_State_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());

		test.asm_Donate.verifyCurrentPage("Make a donation");
		test.asm_Donate.enterDonateValue("Project SEED",
				getASM_Donate.getASM_MakeDonate("donate"));
		test.asm_Donate.clickOnSubmitPaymentButton();
		test.asm_Donate.verifyCurrentPage("Contact info");
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
	public void Step17_TC17_Enter_Valid_Zipcode_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());

		test.asm_Donate.verifyCurrentPage("Make a donation");
		test.asm_Donate.enterDonateValue("Project SEED",
				getASM_Donate.getASM_MakeDonate("donate"));
		test.asm_Donate.clickOnSubmitPaymentButton();
		test.asm_Donate.verifyCurrentPage("Contact info");
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

	@Test
	public void Step18_TC18_Enter_InHonorOf_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());

		test.asm_Donate.verifyCurrentPage("Make a donation");
		test.asm_Donate.enterDonateValue("Project SEED",
				getASM_Donate.getASM_MakeDonate("donate"));
		test.asm_Donate.clickOnSubmitPaymentButton();
		test.asm_Donate.verifyCurrentPage("Contact info");
		test.asm_Donate.clickOnContinueAsGuest();

		test.asm_Donate.enterRequiredDetailsInNonMemberForm(
				getASM_Donate.getASM_Donate_ContactInfo("firstName"),
				getASM_Donate.getASM_Donate_ContactInfo("lastName"),
				getASM_Donate.getASM_Donate_ContactInfo("email"),
				getASM_Donate.getASM_Donate_ContactInfo("phone"),
				getASM_Donate.getASM_Donate_ContactInfo("address"),
				getASM_Donate.getASM_Donate_ContactInfo("city"),
				getASM_Donate.getASM_Donate_ContactInfo("state"),
				getASM_Donate.getASM_Donate_ContactInfo("zipcode"),
				getASM_Donate.getASM_Donate_ContactInfo("country"));

		test.asm_Donate.verifyCurrentPage("Confirm your donation");

		test.asm_Donate.enterRequiredDetailsToConfirmYourDonation("honor",
				DataProvider.getColumnData(tcId, headerName), "Email",
				getASM_Donate
						.getASM_Donate_ConfirmYourDonation("recipientEmail"));
		test.asm_Donate.enterPaymentDetails(YamlReader
				.getYamlValue("creditCardInfo.Type"), YamlReader
				.getYamlValue("creditCardInfo.Holder-name"), YamlReader
				.getYamlValue("creditCardInfo.Number"), YamlReader
				.getYamlValue("creditCardInfo.cvv-number"), getASM_Donate
				.getASM_Donate_ConfirmYourDonation("expirationDate"),
				getASM_Donate
						.getASM_Donate_ConfirmYourDonation("expirationYear"));
		test.asm_Donate.verifyCurrentPage("confirmation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step19_TC19_Enter_InMemoryOf_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());

		test.asm_Donate.verifyCurrentPage("Make a donation");
		test.asm_Donate.enterDonateValue("Project SEED",
				getASM_Donate.getASM_MakeDonate("donate"));
		test.asm_Donate.clickOnSubmitPaymentButton();
		test.asm_Donate.verifyCurrentPage("Contact info");
		test.asm_Donate.clickOnContinueAsGuest();
		test.asm_Donate.enterRequiredDetailsInNonMemberForm(
				getASM_Donate.getASM_Donate_ContactInfo("firstName"),
				getASM_Donate.getASM_Donate_ContactInfo("lastName"),
				getASM_Donate.getASM_Donate_ContactInfo("email"),
				getASM_Donate.getASM_Donate_ContactInfo("phone"),
				getASM_Donate.getASM_Donate_ContactInfo("address"),
				getASM_Donate.getASM_Donate_ContactInfo("city"),
				getASM_Donate.getASM_Donate_ContactInfo("state"),
				getASM_Donate.getASM_Donate_ContactInfo("zipcode"),
				getASM_Donate.getASM_Donate_ContactInfo("country"));

		test.asm_Donate.verifyCurrentPage("Confirm your donation");

		test.asm_Donate.enterRequiredDetailsToConfirmYourDonation("memory",
				DataProvider.getColumnData(tcId, headerName), "Email",
				getASM_Donate
						.getASM_Donate_ConfirmYourDonation("recipientEmail"));
		test.asm_Donate.enterPaymentDetails(YamlReader
				.getYamlValue("creditCardInfo.Type"), YamlReader
				.getYamlValue("creditCardInfo.Holder-name"), YamlReader
				.getYamlValue("creditCardInfo.Number"), YamlReader
				.getYamlValue("creditCardInfo.cvv-number"), getASM_Donate
				.getASM_Donate_ConfirmYourDonation("expirationDate"),
				getASM_Donate
						.getASM_Donate_ConfirmYourDonation("expirationYear"));
		test.asm_Donate.verifyCurrentPage("confirmation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step20_TC20_Enter_Invalid_Recipient_Email_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());

		test.asm_Donate.verifyCurrentPage("Make a donation");
		test.asm_Donate.enterDonateValue("Project SEED",
				getASM_Donate.getASM_MakeDonate("donate"));
		test.asm_Donate.clickOnSubmitPaymentButton();
		test.asm_Donate.verifyCurrentPage("Contact info");
		test.asm_Donate.clickOnContinueAsGuest();
		test.asm_Donate.enterRequiredDetailsInNonMemberForm(
				getASM_Donate.getASM_Donate_ContactInfo("firstName"),
				getASM_Donate.getASM_Donate_ContactInfo("lastName"),
				getASM_Donate.getASM_Donate_ContactInfo("email"),
				getASM_Donate.getASM_Donate_ContactInfo("phone"),
				getASM_Donate.getASM_Donate_ContactInfo("address"),
				getASM_Donate.getASM_Donate_ContactInfo("city"),
				getASM_Donate.getASM_Donate_ContactInfo("state"),
				getASM_Donate.getASM_Donate_ContactInfo("zipcode"),
				getASM_Donate.getASM_Donate_ContactInfo("country"));
		test.asm_Donate.verifyCurrentPage("Confirm your donation");
		test.asm_Donate.enterRequiredDetailsToConfirmYourDonation("honor",
				getASM_Donate.getASM_Donate_ConfirmYourDonation("inMemoryOf"),
				"Email", DataProvider.getColumnData(tcId, headerName));
		test.asm_Donate.enterPaymentDetails(YamlReader
				.getYamlValue("creditCardInfo.Type"), YamlReader
				.getYamlValue("creditCardInfo.Holder-name"), YamlReader
				.getYamlValue("creditCardInfo.Number"), YamlReader
				.getYamlValue("creditCardInfo.cvv-number"), getASM_Donate
				.getASM_Donate_ConfirmYourDonation("expirationDate"),
				getASM_Donate
						.getASM_Donate_ConfirmYourDonation("expirationYear"));
		test.asm_Donate.verifyCurrentPage("confirmation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step21_TC21_Enter_Valid_Recipient_Email_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_Donate.verifyCurrentPage("Make a donation");
		test.asm_Donate.enterDonateValue("Project SEED",
				getASM_Donate.getASM_MakeDonate("donate"));
		test.asm_Donate.clickOnSubmitPaymentButton();
		test.asm_Donate.verifyCurrentPage("Contact info");
		test.asm_Donate.clickOnContinueAsGuest();
		test.asm_Donate.enterRequiredDetailsInNonMemberForm(
				getASM_Donate.getASM_Donate_ContactInfo("firstName"),
				getASM_Donate.getASM_Donate_ContactInfo("lastName"),
				getASM_Donate.getASM_Donate_ContactInfo("email"),
				getASM_Donate.getASM_Donate_ContactInfo("phone"),
				getASM_Donate.getASM_Donate_ContactInfo("address"),
				getASM_Donate.getASM_Donate_ContactInfo("city"),
				getASM_Donate.getASM_Donate_ContactInfo("state"),
				getASM_Donate.getASM_Donate_ContactInfo("zipcode"),
				getASM_Donate.getASM_Donate_ContactInfo("country"));

		test.asm_Donate.verifyCurrentPage("Confirm your donation");

		test.asm_Donate.enterRequiredDetailsToConfirmYourDonation("memory",
				getASM_Donate.getASM_Donate_ConfirmYourDonation("inMemoryOf"),
				"Email", DataProvider.getColumnData(tcId, headerName));

		test.asm_Donate.enterPaymentDetails(YamlReader
				.getYamlValue("creditCardInfo.Type"), YamlReader
				.getYamlValue("creditCardInfo.Holder-name"), YamlReader
				.getYamlValue("creditCardInfo.Number"), YamlReader
				.getYamlValue("creditCardInfo.cvv-number"), getASM_Donate
				.getASM_Donate_ConfirmYourDonation("expirationDate"),
				getASM_Donate
						.getASM_Donate_ConfirmYourDonation("expirationYear"));
		test.asm_Donate.verifyCurrentPage("confirmation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step22_TC22_Enter_Valid_Email_In_Postal_Address_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());

		test.asm_Donate.verifyCurrentPage("Make a donation");
		test.asm_Donate.enterDonateValue("Project SEED",
				getASM_Donate.getASM_MakeDonate("donate"));
		test.asm_Donate.clickOnSubmitPaymentButton();
		test.asm_Donate.verifyCurrentPage("Contact info");
		test.asm_Donate.clickOnContinueAsGuest();
		test.asm_Donate.enterRequiredDetailsInNonMemberForm(
				getASM_Donate.getASM_Donate_ContactInfo("firstName"),
				getASM_Donate.getASM_Donate_ContactInfo("lastName"),
				getASM_Donate.getASM_Donate_ContactInfo("email"),
				getASM_Donate.getASM_Donate_ContactInfo("phone"),
				getASM_Donate.getASM_Donate_ContactInfo("address"),
				getASM_Donate.getASM_Donate_ContactInfo("city"),
				getASM_Donate.getASM_Donate_ContactInfo("state"),
				getASM_Donate.getASM_Donate_ContactInfo("zipcode"),
				getASM_Donate.getASM_Donate_ContactInfo("country"));
		test.asm_Donate.verifyCurrentPage("Confirm your donation");
		test.asm_Donate.enterRequiredDetailsToConfirmYourDonation("memory",
				getASM_Donate.getASM_Donate_ConfirmYourDonation("inMemoryOf"),
				"Postal", DataProvider.getColumnData(tcId, headerName),
				getASM_Donate.getASM_Donate_ConfirmYourDonation("address"),
				getASM_Donate.getASM_Donate_ConfirmYourDonation("city"),
				getASM_Donate.getASM_Donate_ConfirmYourDonation("state"),
				getASM_Donate.getASM_Donate_ConfirmYourDonation("zipCode"));

		test.asm_Donate.enterPaymentDetails(YamlReader
				.getYamlValue("creditCardInfo.Type"), YamlReader
				.getYamlValue("creditCardInfo.Holder-name"), YamlReader
				.getYamlValue("creditCardInfo.Number"), YamlReader
				.getYamlValue("creditCardInfo.cvv-number"), getASM_Donate
				.getASM_Donate_ConfirmYourDonation("expirationDate"),
				getASM_Donate
						.getASM_Donate_ConfirmYourDonation("expirationYear"));

		test.asm_Donate.verifyCurrentPage("confirmation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step23_TC23_Enter_Valid_Address_In_Postal_Address_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());

		test.asm_Donate.verifyCurrentPage("Make a donation");
		test.asm_Donate.enterDonateValue("Project SEED",
				getASM_Donate.getASM_MakeDonate("donate"));
		test.asm_Donate.clickOnSubmitPaymentButton();
		test.asm_Donate.verifyCurrentPage("Contact info");
		test.asm_Donate.clickOnContinueAsGuest();
		test.asm_Donate.enterRequiredDetailsInNonMemberForm(
				getASM_Donate.getASM_Donate_ContactInfo("firstName"),
				getASM_Donate.getASM_Donate_ContactInfo("lastName"),
				getASM_Donate.getASM_Donate_ContactInfo("email"),
				getASM_Donate.getASM_Donate_ContactInfo("phone"),
				getASM_Donate.getASM_Donate_ContactInfo("address"),
				getASM_Donate.getASM_Donate_ContactInfo("city"),
				getASM_Donate.getASM_Donate_ContactInfo("state"),
				getASM_Donate.getASM_Donate_ContactInfo("zipcode"),
				getASM_Donate.getASM_Donate_ContactInfo("country"));
		test.asm_Donate.verifyCurrentPage("Confirm your donation");
		test.asm_Donate.enterRequiredDetailsToConfirmYourDonation("memory",
				getASM_Donate.getASM_Donate_ConfirmYourDonation("inMemoryOf"),
				"Postal",
				getASM_Donate.getASM_Donate_ConfirmYourDonation("To"),
				DataProvider.getColumnData(tcId, headerName),
				getASM_Donate.getASM_Donate_ConfirmYourDonation("city"),
				getASM_Donate.getASM_Donate_ConfirmYourDonation("state"),
				getASM_Donate.getASM_Donate_ConfirmYourDonation("zipCode"));

		test.asm_Donate.enterPaymentDetails(YamlReader
				.getYamlValue("creditCardInfo.Type"), YamlReader
				.getYamlValue("creditCardInfo.Holder-name"), YamlReader
				.getYamlValue("creditCardInfo.Number"), YamlReader
				.getYamlValue("creditCardInfo.cvv-number"), getASM_Donate
				.getASM_Donate_ConfirmYourDonation("expirationDate"),
				getASM_Donate
						.getASM_Donate_ConfirmYourDonation("expirationYear"));

		test.asm_Donate.verifyCurrentPage("confirmation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step24_TC24_Enter_Valid_City_In_Postal_Address_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());

		test.asm_Donate.verifyCurrentPage("Make a donation");
		test.asm_Donate.enterDonateValue("Project SEED",
				getASM_Donate.getASM_MakeDonate("donate"));
		test.asm_Donate.clickOnSubmitPaymentButton();
		test.asm_Donate.verifyCurrentPage("Contact info");
		test.asm_Donate.clickOnContinueAsGuest();
		test.asm_Donate.enterRequiredDetailsInNonMemberForm(
				getASM_Donate.getASM_Donate_ContactInfo("firstName"),
				getASM_Donate.getASM_Donate_ContactInfo("lastName"),
				getASM_Donate.getASM_Donate_ContactInfo("email"),
				getASM_Donate.getASM_Donate_ContactInfo("phone"),
				getASM_Donate.getASM_Donate_ContactInfo("address"),
				getASM_Donate.getASM_Donate_ContactInfo("city"),
				getASM_Donate.getASM_Donate_ContactInfo("state"),
				getASM_Donate.getASM_Donate_ContactInfo("zipcode"),
				getASM_Donate.getASM_Donate_ContactInfo("country"));
		test.asm_Donate.verifyCurrentPage("Confirm your donation");
		test.asm_Donate.enterRequiredDetailsToConfirmYourDonation("memory",
				getASM_Donate.getASM_Donate_ConfirmYourDonation("inMemoryOf"),
				"Postal",
				getASM_Donate.getASM_Donate_ConfirmYourDonation("To"),
				getASM_Donate.getASM_Donate_ConfirmYourDonation("address"),
				DataProvider.getColumnData(tcId, headerName),
				getASM_Donate.getASM_Donate_ConfirmYourDonation("state"),
				getASM_Donate.getASM_Donate_ConfirmYourDonation("zipCode"));

		test.asm_Donate.enterPaymentDetails(YamlReader
				.getYamlValue("creditCardInfo.Type"), YamlReader
				.getYamlValue("creditCardInfo.Holder-name"), YamlReader
				.getYamlValue("creditCardInfo.Number"), YamlReader
				.getYamlValue("creditCardInfo.cvv-number"), getASM_Donate
				.getASM_Donate_ConfirmYourDonation("expirationDate"),
				getASM_Donate
						.getASM_Donate_ConfirmYourDonation("expirationYear"));

		test.asm_Donate.verifyCurrentPage("confirmation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step25_TC25_Enter_Valid_ZipCode_In_Postal_Address_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());

		test.asm_Donate.verifyCurrentPage("Make a donation");
		test.asm_Donate.enterDonateValue("Project SEED",
				getASM_Donate.getASM_MakeDonate("donate"));
		test.asm_Donate.clickOnSubmitPaymentButton();
		test.asm_Donate.verifyCurrentPage("Contact info");
		test.asm_Donate.clickOnContinueAsGuest();
		test.asm_Donate.enterRequiredDetailsInNonMemberForm(
				getASM_Donate.getASM_Donate_ContactInfo("firstName"),
				getASM_Donate.getASM_Donate_ContactInfo("lastName"),
				getASM_Donate.getASM_Donate_ContactInfo("email"),
				getASM_Donate.getASM_Donate_ContactInfo("phone"),
				getASM_Donate.getASM_Donate_ContactInfo("address"),
				getASM_Donate.getASM_Donate_ContactInfo("city"),
				getASM_Donate.getASM_Donate_ContactInfo("state"),
				getASM_Donate.getASM_Donate_ContactInfo("zipcode"),
				getASM_Donate.getASM_Donate_ContactInfo("country"));
		test.asm_Donate.verifyCurrentPage("Confirm your donation");
		test.asm_Donate.enterRequiredDetailsToConfirmYourDonation("memory",
				getASM_Donate.getASM_Donate_ConfirmYourDonation("inMemoryOf"),
				"Postal",
				getASM_Donate.getASM_Donate_ConfirmYourDonation("To"),
				getASM_Donate.getASM_Donate_ConfirmYourDonation("address"),
				getASM_Donate.getASM_Donate_ConfirmYourDonation("city"),
				getASM_Donate.getASM_Donate_ConfirmYourDonation("state"),
				DataProvider.getColumnData(tcId, headerName));

		test.asm_Donate.enterPaymentDetails(YamlReader
				.getYamlValue("creditCardInfo.Type"), YamlReader
				.getYamlValue("creditCardInfo.Holder-name"), YamlReader
				.getYamlValue("creditCardInfo.Number"), YamlReader
				.getYamlValue("creditCardInfo.cvv-number"), getASM_Donate
				.getASM_Donate_ConfirmYourDonation("expirationDate"),
				getASM_Donate
						.getASM_Donate_ConfirmYourDonation("expirationYear"));

		test.asm_Donate.verifyCurrentPage("confirmation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step26_TC26_Enter_Valid_Credit_Card_Number_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());

		test.asm_Donate.verifyCurrentPage("Make a donation");
		test.asm_Donate.enterDonateValue("Project SEED",
				getASM_Donate.getASM_MakeDonate("donate"));
		test.asm_Donate.clickOnSubmitPaymentButton();
		test.asm_Donate.verifyCurrentPage("Contact info");
		test.asm_Donate.clickOnContinueAsGuest();
		test.asm_Donate.enterRequiredDetailsInNonMemberForm(
				getASM_Donate.getASM_Donate_ContactInfo("firstName"),
				getASM_Donate.getASM_Donate_ContactInfo("lastName"),
				getASM_Donate.getASM_Donate_ContactInfo("email"),
				getASM_Donate.getASM_Donate_ContactInfo("phone"),
				getASM_Donate.getASM_Donate_ContactInfo("address"),
				getASM_Donate.getASM_Donate_ContactInfo("city"),
				getASM_Donate.getASM_Donate_ContactInfo("state"),
				getASM_Donate.getASM_Donate_ContactInfo("zipcode"),
				getASM_Donate.getASM_Donate_ContactInfo("country"));
		test.asm_Donate.verifyCurrentPage("Confirm your donation");

		test.asm_Donate.enterPaymentDetails(YamlReader
				.getYamlValue("creditCardInfo.Type"), YamlReader
				.getYamlValue("creditCardInfo.Holder-name"), DataProvider
				.getColumnData(tcId, headerName), YamlReader
				.getYamlValue("creditCardInfo.cvv-number"), getASM_Donate
				.getASM_Donate_ConfirmYourDonation("expirationDate"),
				getASM_Donate
						.getASM_Donate_ConfirmYourDonation("expirationYear"));

		test.asm_Donate.verifyCurrentPage("confirmation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step27_TC27_Enter_InValid_Credit_Card_Number_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());

		test.asm_Donate.verifyCurrentPage("Make a donation");
		test.asm_Donate.enterDonateValue("Project SEED",
				getASM_Donate.getASM_MakeDonate("donate"));
		test.asm_Donate.clickOnSubmitPaymentButton();
		test.asm_Donate.verifyCurrentPage("Contact info");
		test.asm_Donate.clickOnContinueAsGuest();
		test.asm_Donate.enterRequiredDetailsInNonMemberForm(
				getASM_Donate.getASM_Donate_ContactInfo("firstName"),
				getASM_Donate.getASM_Donate_ContactInfo("lastName"),
				getASM_Donate.getASM_Donate_ContactInfo("email"),
				getASM_Donate.getASM_Donate_ContactInfo("phone"),
				getASM_Donate.getASM_Donate_ContactInfo("address"),
				getASM_Donate.getASM_Donate_ContactInfo("city"),
				getASM_Donate.getASM_Donate_ContactInfo("state"),
				getASM_Donate.getASM_Donate_ContactInfo("zipcode"),
				getASM_Donate.getASM_Donate_ContactInfo("country"));
		test.asm_Donate.verifyCurrentPage("Confirm your donation");
		test.asm_Donate.enterPaymentDetails(YamlReader
				.getYamlValue("creditCardInfo.Type"), YamlReader
				.getYamlValue("creditCardInfo.Holder-name"), DataProvider
				.getColumnData(tcId, headerName), YamlReader
				.getYamlValue("creditCardInfo.cvv-number"), getASM_Donate
				.getASM_Donate_ConfirmYourDonation("expirationDate"),
				getASM_Donate
						.getASM_Donate_ConfirmYourDonation("expirationYear"));
		test.asm_Donate.verifyCreditCardErrorMessage(getASM_Donate
				.getASM_Donate("creditCardErrorMessage"));
		test.asm_Donate.verifyCurrentPage("Confirm your donation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step28_TC28_Enter_Valid_CVV_Number_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());

		test.asm_Donate.verifyCurrentPage("Make a donation");
		test.asm_Donate.enterDonateValue("Project SEED",
				getASM_Donate.getASM_MakeDonate("donate"));
		test.asm_Donate.clickOnSubmitPaymentButton();
		test.asm_Donate.verifyCurrentPage("Contact info");
		test.asm_Donate.clickOnContinueAsGuest();
		test.asm_Donate.enterRequiredDetailsInNonMemberForm(
				getASM_Donate.getASM_Donate_ContactInfo("firstName"),
				getASM_Donate.getASM_Donate_ContactInfo("lastName"),
				getASM_Donate.getASM_Donate_ContactInfo("email"),
				getASM_Donate.getASM_Donate_ContactInfo("phone"),
				getASM_Donate.getASM_Donate_ContactInfo("address"),
				getASM_Donate.getASM_Donate_ContactInfo("city"),
				getASM_Donate.getASM_Donate_ContactInfo("state"),
				getASM_Donate.getASM_Donate_ContactInfo("zipcode"),
				getASM_Donate.getASM_Donate_ContactInfo("country"));
		test.asm_Donate.verifyCurrentPage("Confirm your donation");
		test.asm_Donate.enterPaymentDetails(YamlReader
				.getYamlValue("creditCardInfo.Type"), YamlReader
				.getYamlValue("creditCardInfo.Holder-name"), YamlReader
				.getYamlValue("creditCardInfo.Number"), DataProvider
				.getColumnData(tcId, headerName), getASM_Donate
				.getASM_Donate_ConfirmYourDonation("expirationDate"),
				getASM_Donate
						.getASM_Donate_ConfirmYourDonation("expirationYear"));
		test.asm_Donate.verifyCurrentPage("confirmation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	

	@BeforeMethod
	public void LaunchApplication() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_Donate = getYamlValue("app_url_givingDonate");
		mapDonateSmoke = YamlReader
				.getYamlValues("ASM_Donate_SmokeChecklistData");
		getASM_Donate = new YamlInformationProvider(mapDonateSmoke);
		test.launchApplication(app_url_Donate);
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
		test.closeBrowserSession();
	}

	
}
