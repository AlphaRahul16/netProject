package com.qait.tests.ASM_tests;
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

public class ASM_Meeting_Smoke {

	TestSessionInitiator test;

	String app_url_Meeting;
	YamlInformationProvider getASM_Meeting;
	Map<String, Object> mapMeetingSmoke;
	String headerName = this.getClass().getSimpleName();

	@Test
	public void Step01_TC01_Enter_Database_Query_In_Special_Service_And_Verify_ASM_Error() {
		String tcId = test.asm_Meeting.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_Meeting.loginInToApplication(
				getASM_Meeting.getASM_MeetingInfo("userName"),
				getASM_Meeting.getASM_MeetingInfo("password"));
		test.asm_Meeting.selectReqiuredFieldsOnRegistration(
				getASM_Meeting.getASM_MeetingInfo("professionalDiscipline"),
				getASM_Meeting.getASM_MeetingInfo("conference"),
				getASM_Meeting.getASM_MeetingInfo("speakerPoster"));
		test.asm_Meeting.enterTextInSpecialService(DataProvider.getColumnData(
				tcId, headerName));
		test.asm_Meeting.clickOnContinueButton();
		test.asmErrorPage.verifyASMError(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step02_TC02_Enter_InValid_Payment_Info_And_Verify_ASM_Error_Present() {
		String tcId = test.asm_Meeting.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_Meeting.loginInToApplication(
				getASM_Meeting.getASM_MeetingInfo("userName"),
				getASM_Meeting.getASM_MeetingInfo("password"));
		test.asm_Meeting.selectReqiuredFieldsOnRegistration(
				getASM_Meeting.getASM_MeetingInfo("professionalDiscipline"),
				getASM_Meeting.getASM_MeetingInfo("conference"),
				getASM_Meeting.getASM_MeetingInfo("speakerPoster"));
		test.asm_Meeting.enterTextInSpecialService(getASM_Meeting
				.getASM_MeetingInfo("specialService"));
		test.asm_Meeting.clickOnContinueButton();
		test.asm_Meeting.checkAdvancedRegistrationAndTicketEvents();
		test.asm_Meeting.enterPaymentInfo(
				YamlReader.getYamlValue("creditCardInfo.Type"),
				DataProvider.getColumnData(tcId, headerName),
				DataProvider.getColumnData(tcId, headerName),
				getASM_Meeting.getASM_MeetingInfo("expirationDate"),
				DataProvider.getColumnData(tcId, headerName));
		test.asmErrorPage.verifyASMError(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));

	}

	@Test
	public void Step03_TC03_Enter_Valid_Username_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Meeting.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_Meeting.loginInToApplication(
				DataProvider.getColumnData(tcId, headerName),
				getASM_Meeting.getASM_MeetingInfo("password"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step04_TC04_Enter_InValid_Username_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Meeting.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_Meeting.loginInToApplication(
				DataProvider.getColumnData(tcId, headerName),
				getASM_Meeting.getASM_MeetingInfo("password"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.asm_Meeting.verifyLoginErrorMessagePresent(getASM_Meeting
				.getASM_MeetingInfo("LoginErrorMessage"));

	}

	@Test
	public void Step05_TC05_Enter_Link_In_Special_Service_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Meeting.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_Meeting.loginInToApplication(
				getASM_Meeting.getASM_MeetingInfo("userName"),
				getASM_Meeting.getASM_MeetingInfo("password"));
		test.asm_Meeting.selectReqiuredFieldsOnRegistration(
				getASM_Meeting.getASM_MeetingInfo("professionalDiscipline"),
				getASM_Meeting.getASM_MeetingInfo("conference"),
				getASM_Meeting.getASM_MeetingInfo("speakerPoster"));
		test.asm_Meeting.enterTextInSpecialService(DataProvider.getColumnData(
				tcId, headerName));
		test.asm_Meeting.clickOnContinueButton();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step06_TC06_Paste_Content_In_Special_Service_And_Verify_ASM_Error_Not_Present() {
		test.asm_Meeting.loginInToApplication(
				getASM_Meeting.getASM_MeetingInfo("userName"),
				getASM_Meeting.getASM_MeetingInfo("password"));
		test.asm_Meeting.selectReqiuredFieldsOnRegistration(
				getASM_Meeting.getASM_MeetingInfo("professionalDiscipline"),
				getASM_Meeting.getASM_MeetingInfo("conference"),
				getASM_Meeting.getASM_MeetingInfo("speakerPoster"));
		test.asm_Meeting.selectOneWordAndCopyPasteInSpecialServiceTextArea();
		test.asm_Meeting.clickOnContinueButton();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step07_TC07_Enter_Characters_More_Than_255_In_Special_Service_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Meeting.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_Meeting.loginInToApplication(
				getASM_Meeting.getASM_MeetingInfo("userName"),
				getASM_Meeting.getASM_MeetingInfo("password"));
		test.asm_Meeting.selectReqiuredFieldsOnRegistration(
				getASM_Meeting.getASM_MeetingInfo("professionalDiscipline"),
				getASM_Meeting.getASM_MeetingInfo("conference"),
				getASM_Meeting.getASM_MeetingInfo("speakerPoster"));
		test.asm_Meeting.enterTextInSpecialService(DataProvider.getColumnData(
				tcId, headerName));
		test.asm_Meeting.verifyAletText(getASM_Meeting
				.getASM_MeetingInfo("alertMessage"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step08_TC08_Enter_Valid_Number_Of_Night_Info_And_Verify_ASM_Error_Not_Present() {
		test.asm_Meeting.loginInToApplication(
				getASM_Meeting.getASM_MeetingInfo("userName"),
				getASM_Meeting.getASM_MeetingInfo("password"));
		test.asm_Meeting.selectReqiuredFieldsOnRegistration(
				getASM_Meeting.getASM_MeetingInfo("professionalDiscipline"),
				getASM_Meeting.getASM_MeetingInfo("conference"),
				getASM_Meeting.getASM_MeetingInfo("speakerPoster"));
		test.asm_Meeting.enterTextInSpecialService(getASM_Meeting
				.getASM_MeetingInfo("specialService"));
		test.asm_Meeting.clickOnContinueButton();
		test.asm_Meeting.checkAdvancedRegistrationAndTicketEvents();
		test.asm_Meeting.enterPaymentInfo(
				YamlReader.getYamlValue("creditCardInfo.Type"),
				YamlReader.getYamlValue("creditCardInfo.Holder-name"),
				YamlReader.getYamlValue("creditCardInfo.Number"),
				getASM_Meeting.getASM_MeetingInfo("expirationDate"),
				YamlReader.getYamlValue("creditCardInfo.cvv-number"));
		test.asm_Meeting.clickOnContinueButton();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}
	
	@Test
	public void Step09_TC09_Enter_InValid_Number_Of_Night_Info_And_Verify_ASM_Error_Not_Present() {
		test.asm_Meeting.loginInToApplication(
				getASM_Meeting.getASM_MeetingInfo("userName"),
				getASM_Meeting.getASM_MeetingInfo("password"));
		test.asm_Meeting.selectReqiuredFieldsOnRegistration(
				getASM_Meeting.getASM_MeetingInfo("professionalDiscipline"),
				getASM_Meeting.getASM_MeetingInfo("conference"),
				getASM_Meeting.getASM_MeetingInfo("speakerPoster"));
		test.asm_Meeting.enterTextInSpecialService(getASM_Meeting
				.getASM_MeetingInfo("specialService"));
		test.asm_Meeting.clickOnContinueButton();
		test.asm_Meeting.checkAdvancedRegistrationAndTicketEvents();
		test.asm_Meeting.enterPaymentInfo(
				YamlReader.getYamlValue("creditCardInfo.Type"),
				YamlReader.getYamlValue("creditCardInfo.Holder-name"),
				YamlReader.getYamlValue("creditCardInfo.Number"),
				getASM_Meeting.getASM_MeetingInfo("expirationDate"),
				YamlReader.getYamlValue("creditCardInfo.cvv-number"));
		test.asm_Meeting.clickOnContinueButton();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}
	
	@Test
	public void Step10_TC10_Enter_Valid_Payment_Info_And_Verify_ASM_Error_Not_Present() {
		test.asm_Meeting.loginInToApplication(
				getASM_Meeting.getASM_MeetingInfo("userName"),
				getASM_Meeting.getASM_MeetingInfo("password"));
		test.asm_Meeting.selectReqiuredFieldsOnRegistration(
				getASM_Meeting.getASM_MeetingInfo("professionalDiscipline"),
				getASM_Meeting.getASM_MeetingInfo("conference"),
				getASM_Meeting.getASM_MeetingInfo("speakerPoster"));
		test.asm_Meeting.enterTextInSpecialService(getASM_Meeting
				.getASM_MeetingInfo("specialService"));
		test.asm_Meeting.clickOnContinueButton();
		test.asm_Meeting.checkAdvancedRegistrationAndTicketEvents();
		test.asm_Meeting.enterPaymentInfo(
				YamlReader.getYamlValue("creditCardInfo.Type"),
				YamlReader.getYamlValue("creditCardInfo.Holder-name"),
				YamlReader.getYamlValue("creditCardInfo.Number"),
				getASM_Meeting.getASM_MeetingInfo("expirationDate"),
				YamlReader.getYamlValue("creditCardInfo.cvv-number"));
		test.asm_Meeting.clickOnContinueButton();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step11_TC11_Enter_InValid_Payment_Info_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Meeting.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_Meeting.loginInToApplication(
				getASM_Meeting.getASM_MeetingInfo("userName"),
				getASM_Meeting.getASM_MeetingInfo("password"));
		test.asm_Meeting.selectReqiuredFieldsOnRegistration(
				getASM_Meeting.getASM_MeetingInfo("professionalDiscipline"),
				getASM_Meeting.getASM_MeetingInfo("conference"),
				getASM_Meeting.getASM_MeetingInfo("speakerPoster"));
		test.asm_Meeting.enterTextInSpecialService(getASM_Meeting
				.getASM_MeetingInfo("specialService"));
		test.asm_Meeting.clickOnContinueButton();
		test.asm_Meeting.checkAdvancedRegistrationAndTicketEvents();
		test.asm_Meeting.enterPaymentInfo(
				YamlReader.getYamlValue("creditCardInfo.Type"),
				YamlReader.getYamlValue("creditCardInfo.Holder-name"),
				DataProvider.getColumnData(tcId, headerName),
				getASM_Meeting.getASM_MeetingInfo("expirationDate"),
				YamlReader.getYamlValue("creditCardInfo.cvv-number"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));

	}

	@Test
	public void Step12_TC12_Enter_Valid_Payment_Info_And_Verify_Message_And_ASM_Error_Not_Present() {
		test.asm_Meeting.loginInToApplication(
				getASM_Meeting.getASM_MeetingInfo("userName"),
				getASM_Meeting.getASM_MeetingInfo("password"));
		test.asm_Meeting.selectReqiuredFieldsOnRegistration(
				getASM_Meeting.getASM_MeetingInfo("professionalDiscipline"),
				getASM_Meeting.getASM_MeetingInfo("conference"),
				getASM_Meeting.getASM_MeetingInfo("speakerPoster"));
		test.asm_Meeting.enterTextInSpecialService(getASM_Meeting
				.getASM_MeetingInfo("specialService"));
		test.asm_Meeting.clickOnContinueButton();
		test.asm_Meeting.checkAdvancedRegistrationAndTicketEvents();
		test.asm_Meeting.enterPaymentInfo(
				YamlReader.getYamlValue("creditCardInfo.Type"),
				YamlReader.getYamlValue("creditCardInfo.Holder-name"),
				YamlReader.getYamlValue("creditCardInfo.Number"),
				getASM_Meeting.getASM_MeetingInfo("expirationDate"),
				YamlReader.getYamlValue("creditCardInfo.cvv-number"));
		test.asm_Meeting.clickOnContinueButton();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.asm_Meeting.verifyMessageOnSubmitPaymentDetails(getASM_Meeting
				.getASM_MeetingInfo("submitPaymentMessage"));
	}

	@BeforeMethod
	public void launchApplication() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_Meeting = getYamlValue("app_url_Meeting");
		mapMeetingSmoke = YamlReader
				.getYamlValues("ASM_Meeting_SmokeChecklistData");
		getASM_Meeting = new YamlInformationProvider(mapMeetingSmoke);
		test.launchApplication(app_url_Meeting);
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
		//test.closeBrowserSession();
	}

}
