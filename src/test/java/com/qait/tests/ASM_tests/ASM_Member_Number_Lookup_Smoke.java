package com.qait.tests.ASM_tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.Map;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.DataProvider;
import com.qait.automation.utils.YamlReader;
import com.qait.keywords.YamlInformationProvider;

public class ASM_Member_Number_Lookup_Smoke {

	TestSessionInitiator test;
	String app_url_MemberNumberLookup;
	YamlInformationProvider getMemberNumberLookupInfo;
	Map<String, Object> mapMemberNumberLookupSmoke;
	String headerName = this.getClass().getSimpleName();

	 @Test
	public void Step01_TC01_Enter_Invalid_Firstname_And_Verify_ASM_Error_Present() {
		String tcId = test.asm_emailPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_MemberNumberLookup.fillMemberDetails(DataProvider
				.getColumnData(tcId, headerName), getMemberNumberLookupInfo
				.getASM_MemberNumberLookupInfo("lastName"),
				getMemberNumberLookupInfo
						.getASM_MemberNumberLookupInfo("email"));
		test.asmErrorPage.verifyASMError(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step02_TC02_Enter_Valid_Firstname_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_emailPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_MemberNumberLookup.fillMemberDetails(DataProvider
				.getColumnData(tcId, headerName), getMemberNumberLookupInfo
				.getASM_MemberNumberLookupInfo("lastName"),
				getMemberNumberLookupInfo
						.getASM_MemberNumberLookupInfo("email"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.asm_MemberNumberLookup
				.verifyMemberRecordLocated(getMemberNumberLookupInfo
						.getASM_MemberNumberLookupInfo("verifyTextForMember"));
	}

	@Test
	public void Step03_TC03_Enter_Valid_Lastname_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_emailPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_MemberNumberLookup.fillMemberDetails(getMemberNumberLookupInfo
				.getASM_MemberNumberLookupInfo("firstName"), DataProvider
				.getColumnData(tcId, headerName), getMemberNumberLookupInfo
				.getASM_MemberNumberLookupInfo("email"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.asm_MemberNumberLookup
				.verifyMemberRecordLocated(getMemberNumberLookupInfo
						.getASM_MemberNumberLookupInfo("verifyTextForMember"));
	}

	@Test
	public void Step04_TC04_Enter_Valid_Email_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_emailPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_MemberNumberLookup.fillMemberDetails(getMemberNumberLookupInfo
				.getASM_MemberNumberLookupInfo("firstName"),
				getMemberNumberLookupInfo
						.getASM_MemberNumberLookupInfo("lastName"),
				DataProvider.getColumnData(tcId, headerName));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.asm_MemberNumberLookup
				.verifyMemberRecordLocated(getMemberNumberLookupInfo
						.getASM_MemberNumberLookupInfo("verifyTextForMember"));
	}

	@Test
	public void Step05_TC05_Enter_InValid_Email_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_emailPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_MemberNumberLookup.fillMemberDetails(getMemberNumberLookupInfo
				.getASM_MemberNumberLookupInfo("firstName"),
				getMemberNumberLookupInfo
						.getASM_MemberNumberLookupInfo("lastName"),
				DataProvider.getColumnData(tcId, headerName));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.asm_MemberNumberLookup
				.verifyMemberRecordNotLocated(getMemberNumberLookupInfo
						.getASM_MemberNumberLookupInfo("verifyTextForMember"));
	}

	@BeforeClass
	public void OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		mapMemberNumberLookupSmoke = YamlReader
				.getYamlValues("ASM_MemberNumberLookup_SmokeChecklistData");
		getMemberNumberLookupInfo = new YamlInformationProvider(
				mapMemberNumberLookupSmoke);
		app_url_MemberNumberLookup = getYamlValue("app_url_MemberNumberLookup");
	}
	
	@BeforeMethod
	public void LaunchApplication() {
		
		test.launchApplication(app_url_MemberNumberLookup);
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
