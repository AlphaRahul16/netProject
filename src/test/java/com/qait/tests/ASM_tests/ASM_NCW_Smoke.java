package com.qait.tests.ASM_tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.DataProvider;
import com.qait.automation.utils.YamlReader;

public class ASM_NCW_Smoke {

	TestSessionInitiator test;
	String app_url_NCW;
	String headerName = this.getClass().getSimpleName();

	@Test
	public void Step01_TC01_Enter_Invalid_Zip_Code_And_Verify_ASM_Error() {
		String tcId = test.asm_NCWPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_NCWPage.enterZipCodeAndFindNCWCoordinator(DataProvider
				.getColumnData(tcId, headerName));
		test.asmErrorPage.verifyASMError(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step02_TC02_Enter_Valid_Zip_Code_And_Verify_No_ASM_Error() {
		String tcId = test.asm_NCWPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_NCWPage.enterZipCodeAndFindNCWCoordinator(DataProvider
				.getColumnData(tcId, headerName));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.asm_NCWPage.verifyContactCordinatorIsPresent();
	}

	@Test
	public void Step03_TC03_Enter_Valid_Zip_Code_And_Verify_No_ASM_Error() {
		String tcId = test.asm_NCWPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_NCWPage.enterZipCodeAndFindNCWCoordinator(DataProvider
				.getColumnData(tcId, headerName));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.asm_NCWPage.verifyContactCordinatorIsPresent();
	}

	@BeforeMethod
	public void LaunchApplication() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_NCW = getYamlValue("app_url_NCW");
		test.launchApplication(app_url_NCW);
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
		test.closeBrowserSession();

	}

}
