package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.DataProvider;
import com.qait.automation.utils.YamlReader;

public class ASM_CCED_Smoke {

	TestSessionInitiator test;
	String app_url_CCED;
	String headerName = this.getClass().getSimpleName();

	@Test
	public void Step01_TC01_Enter_Invalid_Zip_Code_And_Verify_ASM_Error() {
		String tcId = test.asm_CCEDPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_CCEDPage.enterZipCodeAndFindCCEDCoordinator(DataProvider
				.getColumnData(tcId, headerName));
		test.asmErrorPage.verifyASMError(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step02_TC02_Enter_Valid_Zip_Code_Verify_No_Error_Msz() {
		String tcId = test.asm_CCEDPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_CCEDPage.enterZipCodeAndFindCCEDCoordinator(DataProvider
				.getColumnData(tcId, headerName));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.asm_CCEDPage.verifyContactCordinatorIsPresent();
	}

	@Test
	public void Step03_TC03_Enter_Valid_Zip_Code_Verify_No_Error_Msz() {
		String tcId = test.asm_CCEDPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_CCEDPage.enterZipCodeAndFindCCEDCoordinator(DataProvider
				.getColumnData(tcId, headerName));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.asm_CCEDPage.verifyContactCordinatorIsPresent();
	}

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_CCED = getYamlValue("app_url_CCED");
	}

	@BeforeMethod
	public void launchApplication() {
		test.launchApplication(app_url_CCED);
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
	}

	@AfterClass(alwaysRun = true)
	public void Close_Test_Session() {
//		test.closeBrowserSession();
	}
}
