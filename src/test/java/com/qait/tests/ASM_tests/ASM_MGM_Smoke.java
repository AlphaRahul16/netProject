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

public class ASM_MGM_Smoke {

	TestSessionInitiator test;
	String app_url_MGM;
	String headerName = this.getClass().getSimpleName();
	YamlInformationProvider getASM_MGM;
	Map<String, Object> mapMGMSmoke;

	@Test
	public void Step01_TC01_Enter_Invalid_UserName_And_Verify_ASM_Error() {
		String tcId = test.asm_MGM.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_MGM.loginInToApplication(
				DataProvider.getColumnData(tcId, headerName),
				getASM_MGM.getASM_MGMInfo("password"));
		test.asmErrorPage.verifyASMError(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step02_TC03_Enter_Invalid_UserName_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_MGM.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_MGM.loginInToApplication(
				DataProvider.getColumnData(tcId, headerName),
				getASM_MGM.getASM_MGMInfo("password"));
		test.asm_MGM.verifyErrorMessage(getASM_MGM
				.getASM_MGMInfo("LoginErrorMessage"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step03_TC02_Enter_valid_UserName_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_MGM.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_MGM.loginInToApplication(
				DataProvider.getColumnData(tcId, headerName),
				getASM_MGM.getASM_MGMInfo("password"));
		test.asm_MGM.verifyWelcomePage();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step04_TC04_Enter_valid_First_Name_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_MGM.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_MGM.loginInToApplication(
				getASM_MGM.getASM_MGMInfo("userName"),
				getASM_MGM.getASM_MGMInfo("password"));
		test.asm_MGM.verifyWelcomePage();
		String email = test.asm_MGM.submitMemberDetailsToInvite(
				DataProvider.getColumnData(tcId, headerName),
				getASM_MGM.getASM_MGM_memberDetailsToInvite("lastName"),
				getASM_MGM.getASM_MGM_memberDetailsToInvite("email"));
		test.asm_MGM.verifyInviteMemberSuccessfully(email);
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step05_TC05_Enter_valid_Last_Name_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_MGM.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_MGM.loginInToApplication(
				getASM_MGM.getASM_MGMInfo("userName"),
				getASM_MGM.getASM_MGMInfo("password"));
		test.asm_MGM.verifyWelcomePage();
		String email = test.asm_MGM.submitMemberDetailsToInvite(
				getASM_MGM.getASM_MGM_memberDetailsToInvite("firstName"),
				DataProvider.getColumnData(tcId, headerName),
				getASM_MGM.getASM_MGM_memberDetailsToInvite("email"));
		test.asm_MGM.verifyInviteMemberSuccessfully(email);
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step06_TC06_Enter_valid_Email_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_MGM.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_MGM.loginInToApplication(
				getASM_MGM.getASM_MGMInfo("userName"),
				getASM_MGM.getASM_MGMInfo("password"));
		test.asm_MGM.verifyWelcomePage();
		String email = test.asm_MGM.submitMemberDetailsToInvite(
				getASM_MGM.getASM_MGM_memberDetailsToInvite("firstName"),
				getASM_MGM.getASM_MGM_memberDetailsToInvite("lastName"),
				DataProvider.getColumnData(tcId, headerName));
		test.asm_MGM.verifyInviteMemberSuccessfully(email);
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@BeforeMethod
	public void launchApplication() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_MGM = getYamlValue("app_url_MGM");
		mapMGMSmoke = YamlReader.getYamlValues("ASM_MGM_SmokeChecklistData");
		getASM_MGM = new YamlInformationProvider(mapMGMSmoke);
		test.launchApplication(app_url_MGM);
		
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
		test.closeBrowserSession();
	}

}
