package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.List;
import java.util.Map;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.DataProvider;
import com.qait.automation.utils.YamlReader;
import com.qait.keywords.YamlInformationProvider;

public class ASM_OMR_Smoke {

	TestSessionInitiator test;
	String app_url_OMR, app_url_IWEB;
	List<String> memberDetail;
	YamlInformationProvider getASM_OMR;
	Map<String, Object> mapOMRSmoke;
	String headerName = this.getClass().getSimpleName();

	@Test
	public void Step01_TC01_Enter_Invalid_UserName_And_Verify_ASM_Error() {
		test.launchApplication(app_url_OMR);
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_OMR.loginIntoApplication_ACS_ID(
				DataProvider.getColumnData(tcId, headerName),
				getASM_OMR.getASM_OMR("password"));
		test.asm_OMR.switchToFrame("eWebFrame");
		test.asmErrorPage.verifyASMError(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));

	}

	@Test
	public void Step02_TC02_Enter_Invalid_UserName_And_Verify_Error_Message_And_ASM_Error_Not_Present() {
		test.launchApplication(app_url_OMR);
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_OMR.loginIntoApplication_ACS_ID(
				DataProvider.getColumnData(tcId, headerName),
				getASM_OMR.getASM_OMR("password"));
		test.asm_OMR.verifyLoginErrorMessage(getASM_OMR
				.getASM_OMR("LoginErrorMessage"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step03_TC03_Enter_Invalid_Notice_Number_And_Verify_Error_Message_And_ASM_Error_Not_Present() {
		test.launchApplication(app_url_OMR);
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_OMR.loginIntoApplication_LastName_NoticeNumber(
				getASM_OMR.getASM_OMR("lastName"),
				DataProvider.getColumnData(tcId, headerName));
		test.asm_OMR.verifyLoginErrorMessage(getASM_OMR
				.getASM_OMR("ErrorMessage_noticeNumber_memberNumber"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step04_TC04_Enter_Invalid_Member_Number_And_Verify_Error_Message_And_ASM_Error_Not_Present() {
		test.launchApplication(app_url_OMR);
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_OMR.loginIntoApplication_LastName_MemberNumber(
				getASM_OMR.getASM_OMR("lastName"),
				DataProvider.getColumnData(tcId, headerName));
		test.asm_OMR.verifyLoginErrorMessage(getASM_OMR
				.getASM_OMR("ErrorMessage_noticeNumber_memberNumber"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step05_TC05_Enter_Valid_Member_Number_And_Verify_ASM_Error_Not_Present() {
		test.launchApplication(app_url_OMR);
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_OMR.loginIntoApplication_LastName_MemberNumber(
				getASM_OMR.getASM_OMR("lastName"),
				DataProvider.getColumnData(tcId, headerName));
		test.asm_OMR.verifyWelcomePage();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step06_TC06_Enter_Valid_Notice_Number_Verify_ASM_Error_Not_Present() {
		test.launchApplication(app_url_OMR);
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_OMR.loginIntoApplication_LastName_NoticeNumber(
				getASM_OMR.getASM_OMR("lastName"),
				DataProvider.getColumnData(tcId, headerName));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step07_TC07_Enter_valid_UserName_And_Verify_ASM_Error_Not_Present() {
		test.launchApplication(app_url_OMR);
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_OMR.loginIntoApplication_ACS_ID(
				DataProvider.getColumnData(tcId, headerName),
				getASM_OMR.getASM_OMR("password"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step08_TC08_Enter_Invalid_Organization_And_Verify_ASM_Error_Not_Present() {
		test.launchApplication(app_url_OMR);
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_OMR.loginIntoApplication_LastName_NoticeNumber(
				getASM_OMR.getASM_OMR("lastName"),
				getASM_OMR.getASM_OMR("noticeNumber"));
		test.asm_OMR.updateAddress("Organization",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_OMR.verifyAddressUpdated(DataProvider.getColumnData(tcId,
				headerName));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step09_TC09_Enter_Valid_Organization_And_Verify_ASM_Error_Not_Present() {
		test.launchApplication(app_url_OMR);
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_OMR.loginIntoApplication_LastName_NoticeNumber(
				getASM_OMR.getASM_OMR("lastName"),
				getASM_OMR.getASM_OMR("noticeNumber"));
		test.asm_OMR.updateAddress("Organization",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_OMR.verifyAddressUpdated(DataProvider.getColumnData(tcId,
				headerName));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step10_TC10_Enter_Valid_Dept_Mail_Stop_And_Verify_ASM_Error_Not_Present() {
		test.launchApplication(app_url_OMR);
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_OMR.loginIntoApplication_LastName_NoticeNumber(
				getASM_OMR.getASM_OMR("lastName"),
				getASM_OMR.getASM_OMR("noticeNumber"));
		test.asm_OMR.updateAddress("Dept./Mail Stop",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_OMR.verifyAddressUpdated(DataProvider.getColumnData(tcId,
				headerName));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step11_TC11_Enter_Valid_Address_And_Verify_ASM_Error_Not_Present() {
		test.launchApplication(app_url_OMR);
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_OMR.loginIntoApplication_LastName_NoticeNumber(
				getASM_OMR.getASM_OMR("lastName"),
				getASM_OMR.getASM_OMR("noticeNumber"));
		test.asm_OMR.updateAddress("Address",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_OMR.verifyAddressUpdated(DataProvider.getColumnData(tcId,
				headerName));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step12_TC12_Enter_Valid_City_And_Verify_ASM_Error_Not_Present() {
		test.launchApplication(app_url_OMR);
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_OMR.loginIntoApplication_LastName_NoticeNumber(
				getASM_OMR.getASM_OMR("lastName"),
				getASM_OMR.getASM_OMR("noticeNumber"));
		test.asm_OMR.updateAddress("City",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_OMR.verifyAddressUpdated(DataProvider.getColumnData(tcId,
				headerName));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step13_TC13_Enter_Valid_ZipCode_And_Verify_ASM_Error_Not_Present() {
		test.launchApplication(app_url_OMR);
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_OMR.loginIntoApplication_LastName_NoticeNumber(
				getASM_OMR.getASM_OMR("lastName"),
				getASM_OMR.getASM_OMR("noticeNumber"));
		test.asm_OMR.updateAddress("Zip Code /Postal",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_OMR.verifyAddressUpdated(DataProvider.getColumnData(tcId,
				headerName));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step14_TC14_Enter_Invalid_Email_And_Verify_Error_Message_And_ASM_Error_Not_Present() {
		test.launchApplication(app_url_OMR);
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_OMR.loginIntoApplication_LastName_NoticeNumber(
				getASM_OMR.getASM_OMR("lastName"),
				getASM_OMR.getASM_OMR("noticeNumber"));
		test.asm_OMR.updateAddress("Email",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_OMR.verifyErrorMessageOnInvalidEmailAddress(getASM_OMR
				.getASM_OMR("InvalidEmailErrorMessage"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step15_TC15_Enter_Valid_Email_And_Verify_ASM_Error_Not_Present() {
		test.launchApplication(app_url_OMR);
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_OMR.loginIntoApplication_LastName_NoticeNumber(
				getASM_OMR.getASM_OMR("lastName"),
				getASM_OMR.getASM_OMR("noticeNumber"));
		String email = test.asm_OMR.updateAddress("Email",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_OMR.verifyUpdatedEmailAddress(email);
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step16_TC16_Enter_Valid_Credit_Card_Name_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB
				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
		test.homePageIWEB.GoToMemberShipModule();
		memberDetail = test.memberShipPage
				.searchNonRenewalMemberAndGetDetails();
		test.launchApplication(app_url_OMR);
		test.asm_OMR.loginIntoApplication_LastName_MemberNumber(
				memberDetail.get(0), memberDetail.get(1));
		test.asm_OMR.renewConfirmYourInformation();
		test.asm_OMR.submitPaymentDetails(
				YamlReader.getYamlValue("creditCardInfo.Type"),
				DataProvider.getColumnData(tcId, headerName),
				YamlReader.getYamlValue("creditCardInfo.Number"),
				YamlReader.getYamlValue("creditCardInfo.cvv-number"),
				getASM_OMR.getASM_OMR_CreditCardDate("expirationDate"),
				getASM_OMR.getASM_OMR_CreditCardDate("expirationYear"));
		test.asm_OMR.clickOnSubmitPayment();
		test.asm_OMR.verifyNavigationPage(getASM_OMR
				.getASM_OMR("pageOnMakePayment"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step17_TC17_Enter_Valid_CVV_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB
				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
		test.homePageIWEB.GoToMemberShipModule();
		memberDetail = test.memberShipPage
				.searchNonRenewalMemberAndGetDetails();
		test.launchApplication(app_url_OMR);
		test.asm_OMR.loginIntoApplication_LastName_MemberNumber(
				memberDetail.get(0), memberDetail.get(1));
		test.asm_OMR.renewConfirmYourInformation();
		test.asm_OMR.submitPaymentDetails(
				YamlReader.getYamlValue("creditCardInfo.Type"),
				YamlReader.getYamlValue("creditCardInfo.Holder-name"),
				YamlReader.getYamlValue("creditCardInfo.Number"),
				DataProvider.getColumnData(tcId, headerName),
				getASM_OMR.getASM_OMR_CreditCardDate("expirationDate"),
				getASM_OMR.getASM_OMR_CreditCardDate("expirationYear"));
		test.asm_OMR.clickOnSubmitPayment();
		test.asm_OMR.verifyNavigationPage(getASM_OMR
				.getASM_OMR("pageOnMakePayment"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step19_TC18_Enter_Valid_Credit_Card_Number_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB
				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
		test.homePageIWEB.GoToMemberShipModule();
		memberDetail = test.memberShipPage
				.searchNonRenewalMemberAndGetDetails();
		test.launchApplication(app_url_OMR);
		test.asm_OMR.loginIntoApplication_LastName_MemberNumber(
				memberDetail.get(0), memberDetail.get(1));
		test.asm_OMR.renewConfirmYourInformation();
		test.asm_OMR.submitPaymentDetails(
				YamlReader.getYamlValue("creditCardInfo.Type"),
				YamlReader.getYamlValue("creditCardInfo.Holder-name"),
				DataProvider.getColumnData(tcId, headerName),
				YamlReader.getYamlValue("creditCardInfo.cvv-number"),
				getASM_OMR.getASM_OMR_CreditCardDate("expirationDate"),
				getASM_OMR.getASM_OMR_CreditCardDate("expirationYear"));
		test.asm_OMR.clickOnSubmitPayment();
		test.asm_OMR.verifyNavigationPage(getASM_OMR
				.getASM_OMR("pageOnMakePayment"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step18_TC19_Enter_Invalid_Credit_Card_Number_And_Verify_Error_Message_And_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB
				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
		test.homePageIWEB.GoToMemberShipModule();
		memberDetail = test.memberShipPage
				.searchNonRenewalMemberAndGetDetails();
		test.launchApplication(app_url_OMR);
		test.asm_OMR.loginIntoApplication_LastName_MemberNumber(
				memberDetail.get(0), memberDetail.get(1));
		test.asm_OMR.renewConfirmYourInformation();
		test.asm_OMR.submitPaymentDetails(
				YamlReader.getYamlValue("creditCardInfo.Type"),
				YamlReader.getYamlValue("creditCardInfo.Holder-name"),
				DataProvider.getColumnData(tcId, headerName),
				YamlReader.getYamlValue("creditCardInfo.cvv-number"),
				getASM_OMR.getASM_OMR_CreditCardDate("expirationDate"),
				getASM_OMR.getASM_OMR_CreditCardDate("expirationYear"));
		test.asm_OMR.verifyErrorMessage(getASM_OMR
				.getASM_OMR("creditCardErrorMessage"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@BeforeMethod
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_OMR = getYamlValue("app_url_OMR");
		app_url_IWEB = getYamlValue("app_url_IWEB");
		mapOMRSmoke = YamlReader.getYamlValues("ASM_OMR_SmokeChecklistData");
		getASM_OMR = new YamlInformationProvider(mapOMRSmoke);
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
		 test.closeBrowserSession();
	}

}
