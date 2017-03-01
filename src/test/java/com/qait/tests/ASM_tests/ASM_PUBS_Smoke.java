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

public class ASM_PUBS_Smoke {

	TestSessionInitiator test;
	String app_url_PUBS;
	YamlInformationProvider getPUBSInfo;
	Map<String, Object> mapPUBSSmoke;
	String headerName = this.getClass().getSimpleName();

	@Test
	public void Step01_TC01_Enter_Invalid_Username_And_Verify_ASM_Error() {
		String tcId = test.asm_PUBSPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_PUBSPage.loginInToApplication(
				DataProvider.getColumnData(tcId, headerName),
				getPUBSInfo.getASM_PUBSInfo("userName"));
		test.asmErrorPage.verifyASMError(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step02_TC02_Enter_Valid_Credit_Card_Number_And_Verify_No_ASM_Error() {
		String tcId = test.asm_PUBSPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_PUBSPage.loginInToApplication(
				getPUBSInfo.getASM_PUBSInfo("userName"),
				getPUBSInfo.getASM_PUBSInfo("password"));
		test.asm_PUBSPage.addSubscription();
//		test.asm_PUBSPage.submitPaymentDetails(
//				YamlReader.getYamlValue("creditCardInfo.Type"),
//				YamlReader.getYamlValue("creditCardInfo.Holder-name"),
//				DataProvider.getColumnData(tcId, headerName),
//				YamlReader.getYamlValue("creditCardInfo.cvv-number"),
//				getPUBSInfo.getASM_PUBSInfo("expirationYear"));
		test.asm_PUBSPage.clickOnPlaceOrder();
		test.asm_PUBSPage.verifyReceiptPage();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step03_TC03_Enter_InValid_Credit_Card_Number_And_Verify_No_ASM_Error() {
		String tcId = test.asm_PUBSPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_PUBSPage.loginInToApplication(
				getPUBSInfo.getASM_PUBSInfo("userName"),
				getPUBSInfo.getASM_PUBSInfo("password"));
		test.asm_PUBSPage.addSubscription();
//		test.asm_PUBSPage.submitPaymentDetails(
//				YamlReader.getYamlValue("creditCardInfo.Type"),
//				YamlReader.getYamlValue("creditCardInfo.Holder-name"),
//				DataProvider.getColumnData(tcId, headerName),
//				YamlReader.getYamlValue("creditCardInfo.cvv-number"),
//				getPUBSInfo.getASM_PUBSInfo("expirationYear"));
		test.asm_PUBSPage.verifyPaymentPage();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step04_TC04_Enter_Valid_CVV_And_Verify_No_ASM_Error() {
		String tcId = test.asm_PUBSPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_PUBSPage.loginInToApplication(
				getPUBSInfo.getASM_PUBSInfo("userName"),
				getPUBSInfo.getASM_PUBSInfo("password"));
		test.asm_PUBSPage.addSubscription();
//		test.asm_PUBSPage.submitPaymentDetails(
//				YamlReader.getYamlValue("creditCardInfo.Type"),
//				YamlReader.getYamlValue("creditCardInfo.Holder-name"),
//				YamlReader.getYamlValue("creditCardInfo.Number"),
//				DataProvider.getColumnData(tcId, headerName),
//				getPUBSInfo.getASM_PUBSInfo("expirationYear"));
		test.asm_PUBSPage.clickOnPlaceOrder();
		test.asm_PUBSPage.verifyReceiptPage();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step05_TC05_Enter_Valid_Credit_Card_Holder_Name_And_Verify_No_ASM_Error() {
		String tcId = test.asm_PUBSPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_PUBSPage.loginInToApplication(
				getPUBSInfo.getASM_PUBSInfo("userName"),
				getPUBSInfo.getASM_PUBSInfo("password"));
		test.asm_PUBSPage.addSubscription();
//		test.asm_PUBSPage.submitPaymentDetails(
//				YamlReader.getYamlValue("creditCardInfo.Type"),
//				DataProvider.getColumnData(tcId, headerName),
//				YamlReader.getYamlValue("creditCardInfo.Number"),
//				YamlReader.getYamlValue("creditCardInfo.cvv-number"),
//				getPUBSInfo.getASM_PUBSInfo("expirationYear"));
		test.asm_PUBSPage.clickOnPlaceOrder();
		test.asm_PUBSPage.verifyReceiptPage();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@BeforeMethod
	public void launchApplication() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		mapPUBSSmoke = YamlReader.getYamlValues("ASM_PUBS_SmokeChecklistData");
		getPUBSInfo = new YamlInformationProvider(mapPUBSSmoke);
		app_url_PUBS = getYamlValue("app_url_PUBS");
		test.launchApplication(app_url_PUBS);
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
		test.closeBrowserSession();
	}

}
