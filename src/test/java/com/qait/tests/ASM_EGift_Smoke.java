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

public class ASM_EGift_Smoke {

	TestSessionInitiator test;
	String app_url_egift;
	String headerName = this.getClass().getSimpleName();
	YamlInformationProvider getASM_EGift;
	Map<String, Object> mapEGiftSmoke;
	String app_url_Nominate;
	YamlInformationProvider getASM_Nominate;
	Map<String, Object> mapNominateSmoke;
	
	
	@Test
	public void Step00_TC00_Enter_valid_Lastname_And_Verify_ASM_Error() {
		test.launchApplication(app_url_Nominate);
		test.asm_NominatePage.loginInToApplication_ACSID(
				getASM_Nominate.getASM_NominateInfo("userName"),
				getASM_Nominate.getASM_NominateInfo("password"));
		
	}
	@Test
	public void Step01_TC01_Enter_Invalid_Lastname_And_Verify_ASM_Error() {
		String tcId = test.asm_EGiftPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_EGiftPage.fillGiftsAndRecipientDetails(
				DataProvider.getColumnData(tcId, headerName),
				getASM_EGift.getASM_EGiftInfo("lastName"),
				getASM_EGift.getASM_EGiftInfo("recipientEmail"));
		test.asm_EGiftPage.clickOnContinueButton();
		test.asmErrorPage.verifyASMError(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step02_TC02_Enter_Valid_FirstName_AND_Verify_No_ASM_Error() {
		String tcId = test.asm_EGiftPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_EGiftPage.fillGiftsAndRecipientDetails(
				DataProvider.getColumnData(tcId, headerName),
				getASM_EGift.getASM_EGiftInfo("lastName"),
				getASM_EGift.getASM_EGiftInfo("recipientEmail"));
		test.asm_EGiftPage.clickOnContinueButton();
		test.asm_EGiftPage.verifyCurrentTab("Checkout");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));

	}

	@Test
	public void Step03_TC03_Enter_Valid_Recipient_Email_AND_Verify_No_ASM_Error() {
		String tcId = test.asm_EGiftPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_EGiftPage.fillGiftsAndRecipientDetails(
				getASM_EGift.getASM_EGiftInfo("firstName"),
				getASM_EGift.getASM_EGiftInfo("lastName"),
				DataProvider.getColumnData(tcId, headerName));
		test.asm_EGiftPage.clickOnContinueButton();
		test.asm_EGiftPage.verifyCurrentTab("Checkout");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));

	}

	@Test
	public void Step04_TC04_Enter_InValid_Recipient_Email_AND_Verify_No_ASM_Error() {
		String tcId = test.asm_EGiftPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_EGiftPage.fillGiftsAndRecipientDetails(
				getASM_EGift.getASM_EGiftInfo("firstName"),
				getASM_EGift.getASM_EGiftInfo("lastName"),
				DataProvider.getColumnData(tcId, headerName));
		test.asm_EGiftPage.clickOnContinueButton();
		test.asm_EGiftPage.verifyCurrentTab("Gift and Recipient Details");
		test.asm_EGiftPage.verifyErrorMessage(getASM_EGift
				.getASM_EGiftInfo("errorMessage"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));

	}

	@Test
	public void Step05_TC05_Enter_Valid_LastName_AND_Verify_No_ASM_Error() {
		String tcId = test.asm_EGiftPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_EGiftPage.fillGiftsAndRecipientDetails(
				getASM_EGift.getASM_EGiftInfo("firstName"),
				DataProvider.getColumnData(tcId, headerName),
				getASM_EGift.getASM_EGiftInfo("recipientEmail"));
		test.asm_EGiftPage.clickOnContinueButton();
		test.asm_EGiftPage.verifyCurrentTab("Checkout");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));

	}

	@Test
	public void Step06_TC06_Enter_Valid_Personal_Message_AND_Verify_No_ASM_Error() {
		String tcId = test.asm_EGiftPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_EGiftPage.fillGiftsAndRecipientDetails(
				getASM_EGift.getASM_EGiftInfo("firstName"),
				getASM_EGift.getASM_EGiftInfo("lastName"),
				getASM_EGift.getASM_EGiftInfo("recipientEmail"));
		test.asm_EGiftPage.enterPersonalMessage(DataProvider.getColumnData(
				tcId, headerName));
		test.asm_EGiftPage.clickOnContinueButton();
		test.asm_EGiftPage.verifyCurrentTab("Checkout");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step07_TC07_Enter_Valid_Credit_Card_Info_AND_Verify_No_ASM_Error() {
		test.asm_EGiftPage.fillGiftsAndRecipientDetails(
				getASM_EGift.getASM_EGiftInfo("firstName"),
				getASM_EGift.getASM_EGiftInfo("lastName"),
				getASM_EGift.getASM_EGiftInfo("recipientEmail"));
		test.asm_EGiftPage.clickOnContinueButton();
		test.asm_EGiftPage.verifyCurrentTab("Checkout");
		test.asm_EGiftPage.enterPaymentInformation(
				YamlReader.getYamlValue("creditCardInfo.Type"),
				YamlReader.getYamlValue("creditCardInfo.Holder-name"),
				YamlReader.getYamlValue("creditCardInfo.Number"),
				YamlReader.getYamlValue("creditCardInfo.expMonth"),
				YamlReader.getYamlValue("creditCardInfo.expYear"),
				YamlReader.getYamlValue("creditCardInfo.cvv-number"));
		test.asm_EGiftPage.clickOnContinueButton();
		test.asm_EGiftPage.verifyCurrentTab("Submit Payment");
		test.asm_EGiftPage.clickOnSubmitButton();
		test.asm_EGiftPage.verifyCurrentTab("Receipt");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step08_TC08_Navigate_To_Print_Receipt_Page_AND_Verify_No_ASM_Error() {
		test.asm_EGiftPage.fillGiftsAndRecipientDetails(
				getASM_EGift.getASM_EGiftInfo("firstName"),
				getASM_EGift.getASM_EGiftInfo("lastName"),
				getASM_EGift.getASM_EGiftInfo("recipientEmail"));
		test.asm_EGiftPage.clickOnContinueButton();
		test.asm_EGiftPage.verifyCurrentTab("Checkout");
		test.asm_EGiftPage.enterPaymentInformation(
				YamlReader.getYamlValue("creditCardInfo.Type"),
				YamlReader.getYamlValue("creditCardInfo.Holder-name"),
				YamlReader.getYamlValue("creditCardInfo.Number"),
				YamlReader.getYamlValue("creditCardInfo.expMonth"),
				YamlReader.getYamlValue("creditCardInfo.expYear"),
				YamlReader.getYamlValue("creditCardInfo.cvv-number"));
		test.asm_EGiftPage.clickOnContinueButton();
		test.asm_EGiftPage.verifyCurrentTab("Submit Payment");
		test.asm_EGiftPage.clickOnSubmitButton();
		test.asm_EGiftPage.verifyCurrentTab("Receipt");
		test.asm_EGiftPage.clickOnPrintReceiptButtonAndVerifyReceiptPage();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step09_TC09_Navigate_To_Egift_Page_AND_Verify_No_ASM_Error() {
		test.asm_EGiftPage.fillGiftsAndRecipientDetails(
				getASM_EGift.getASM_EGiftInfo("firstName"),
				getASM_EGift.getASM_EGiftInfo("lastName"),
				getASM_EGift.getASM_EGiftInfo("recipientEmail"));
		test.asm_EGiftPage.clickOnContinueButton();
		test.asm_EGiftPage.verifyCurrentTab("Checkout");
		test.asm_EGiftPage.enterPaymentInformation(
				YamlReader.getYamlValue("creditCardInfo.Type"),
				YamlReader.getYamlValue("creditCardInfo.Holder-name"),
				YamlReader.getYamlValue("creditCardInfo.Number"),
				YamlReader.getYamlValue("creditCardInfo.expMonth"),
				YamlReader.getYamlValue("creditCardInfo.expYear"),
				YamlReader.getYamlValue("creditCardInfo.cvv-number"));
		test.asm_EGiftPage.clickOnContinueButton();
		test.asm_EGiftPage.verifyCurrentTab("Submit Payment");
		test.asm_EGiftPage.clickOnSubmitButton();
		test.asm_EGiftPage.verifyCurrentTab("Receipt");
		test.asm_EGiftPage.clickOnPurchaseAnotherGiftButton();
		test.asm_EGiftPage.verifyCurrentTab("Gift and Recipient Details");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}


	@BeforeMethod
	public void launchApplication() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());

		mapEGiftSmoke = YamlReader
				.getYamlValues("ASM_EGift_SmokeChecklistData");
		getASM_EGift = new YamlInformationProvider(mapEGiftSmoke);
		app_url_egift = getYamlValue("app_url_egift");
		app_url_Nominate = getYamlValue("app_url_Nominate");
		mapNominateSmoke = YamlReader
				.getYamlValues("ASM_Nominate_SmokeChecklistData");
		getASM_Nominate = new YamlInformationProvider(mapNominateSmoke);
		test.asm_EGiftPage.navigateToUrl(app_url_egift);
		test.launchApplication(app_url_egift);
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
		test.closeBrowserSession();
	}


}
