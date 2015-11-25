package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.Map;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.DataProvider;
import com.qait.automation.utils.YamlReader;
import com.qait.keywords.YamlInformationProvider;

public class ASM_Email_Smoke {

	TestSessionInitiator test;
	String app_url_email;
	YamlInformationProvider getEmailLoginInfo;
	Map<String, Object> mapEmailSmoke;
	String headerName = this.getClass().getSimpleName();

	 @Test
	public void Step01_TC01_Enter_Invalid_Username_And_Verify_ASM_Error_Present() {
		String tcId = test.asm_emailPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_emailPage.loginInToApplication(
				DataProvider.getColumnData(tcId, headerName),
				getEmailLoginInfo.getASM_email_LoginInfo("password"));
		test.asmErrorPage.verifyASMError(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step02_TC02_Enter_Invalid_Username_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_emailPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.launchApplication(app_url_email);
		test.asm_emailPage.loginInToApplication(
				DataProvider.getColumnData(tcId, headerName),
				getEmailLoginInfo.getASM_email_LoginInfo("password"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.asm_emailPage.verifyLoginErrorMessagePresent(YamlReader
				.getYamlValue("ASM_emailSmokeChecklistData.LoginErrorMessage"));
	}

	@Test
	public void Step03_TC03_Enter_Valid_Username_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_emailPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_emailPage.loginInToApplication(
				DataProvider.getColumnData(tcId, headerName),
				getEmailLoginInfo.getASM_email_LoginInfo("password"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.asm_emailPage.verifyLoginSuccessfully();
	}

	@Test
	public void Step04_TC04_Select_Email_Id_From_DropDown_And_Verify_ASM_Error_Not_Present() {
		test.asm_emailPage.selectEmailIdAndVerifySubscribedAutomatically();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step05_TC05_Subscribe_SubCategory_And_Verify_ASM_Error_Not_Present() {
		test.asm_emailPage.VerifyFirstSubscribed_UnsubscribedSuccessfully();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step06_TC06_Added_New_Valid_Email_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_emailPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_emailPage.addedNewMailAndVerified(DataProvider.getColumnData(
				tcId, headerName));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step07_TC07_Added_New_Invalid_Email_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_emailPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_emailPage
				.enterNewInvalidEmailAndVerifyFailMessage(
						DataProvider.getColumnData(tcId, headerName),
						YamlReader
								.getYamlValue("ASM_emailSmokeChecklistData.InvalidEmailFailMessage"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step08_TC08_Unsubscribe_All_And_Verify_ASM_Error_Not_Present() {
		test.asm_emailPage.verifyUnsubscribeOnclickingUnsubscribeAllButton();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step09_TC09_Unsubscribe_On_Select_All_And_Verify_ASM_Error_Not_Present() {
		test.asm_emailPage
				.checkFirstUnsubscribeSelectAllButtonAndVerifySubscribeToAllSubCategory();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@BeforeClass
	public void OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		mapEmailSmoke = YamlReader.getYamlValues("ASM_emailSmokeChecklistData");
		getEmailLoginInfo = new YamlInformationProvider(mapEmailSmoke);
		app_url_email = getYamlValue("app_url_email");
		test.launchApplication(app_url_email);
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
	}

	@AfterClass(alwaysRun = true)
	public void Close_Test_Session() {
		test.closeBrowserSession();
	}
}
