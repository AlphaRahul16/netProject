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

public class ASM_OMA_Smoke {
	TestSessionInitiator test;
	String app_url;
	Map<String, Object> mapASM_OMASmoke, mapASM_creditCardInfo;
	YamlInformationProvider getKeyValue, getCreditCardValue;

	String headerName = this.getClass().getSimpleName();

	@Test
	public void Step01_TC01_Enter_Last_Name_And_Verify_ASM_Error() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValue.getASM_OMASmokeInfo("email"),
				getKeyValue.getASM_OMASmokeInfo("firstname"),
				getKeyValue.getASM_OMASmokeInfo("lastname"),
				getKeyValue.getASM_OMASmokeInfo("type"),
				getKeyValue.getASM_OMASmokeInfo("address"),
				getKeyValue.getASM_OMASmokeInfo("city"),
				getKeyValue.getASM_OMASmokeInfo("country"),
				getKeyValue.getASM_OMASmokeInfo("state"),
				getKeyValue.getASM_OMASmokeInfo("zipCode"));
		test.ContactInfoPage.enterLastName(DataProvider.getColumnData(tcId,
				headerName));
		test.ContactInfoPage.clickContinue();
		test.asmErrorPage.verifyASMError(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step02_TC02_Enter_Valid_Email_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				DataProvider.getColumnData(tcId, headerName),
				getKeyValue.getASM_OMASmokeInfo("firstname"),
				getKeyValue.getASM_OMASmokeInfo("lastname"),
				getKeyValue.getASM_OMASmokeInfo("type"),
				getKeyValue.getASM_OMASmokeInfo("address"),
				getKeyValue.getASM_OMASmokeInfo("city"),
				getKeyValue.getASM_OMASmokeInfo("country"),
				getKeyValue.getASM_OMASmokeInfo("state"),
				getKeyValue.getASM_OMASmokeInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("Education & Employment");
	}

	@Test
	public void Step03_TC03_Enter_Invalid_Email_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValue.getASM_OMASmokeInfo("email"),
				getKeyValue.getASM_OMASmokeInfo("firstname"),
				getKeyValue.getASM_OMASmokeInfo("lastname"),
				getKeyValue.getASM_OMASmokeInfo("type"),
				getKeyValue.getASM_OMASmokeInfo("address"),
				getKeyValue.getASM_OMASmokeInfo("city"),
				getKeyValue.getASM_OMASmokeInfo("country"),
				getKeyValue.getASM_OMASmokeInfo("state"),
				getKeyValue.getASM_OMASmokeInfo("zipCode"));
		test.ContactInfoPage.enterInvalidEmail_ASMOMA(DataProvider
				.getColumnData(tcId, headerName));
		test.ContactInfoPage.clickContinue();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("Contact Information");

	}

	@Test
	public void Step04_TC04_Enter_Valid_First_Name_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValue.getASM_OMASmokeInfo("email"),
				DataProvider.getColumnData(tcId, headerName),
				getKeyValue.getASM_OMASmokeInfo("lastname"),
				getKeyValue.getASM_OMASmokeInfo("type"),
				getKeyValue.getASM_OMASmokeInfo("address"),
				getKeyValue.getASM_OMASmokeInfo("city"),
				getKeyValue.getASM_OMASmokeInfo("country"),
				getKeyValue.getASM_OMASmokeInfo("state"),
				getKeyValue.getASM_OMASmokeInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("Education & Employment");
	}

	@Test
	public void Step05_TC05_Enter_Valid_Middle_Name_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValue.getASM_OMASmokeInfo("email"),
				getKeyValue.getASM_OMASmokeInfo("firstname"),
				getKeyValue.getASM_OMASmokeInfo("lastname"),
				getKeyValue.getASM_OMASmokeInfo("type"),
				getKeyValue.getASM_OMASmokeInfo("address"),
				getKeyValue.getASM_OMASmokeInfo("city"),
				getKeyValue.getASM_OMASmokeInfo("country"),
				getKeyValue.getASM_OMASmokeInfo("state"),
				getKeyValue.getASM_OMASmokeInfo("zipCode"));
		test.ContactInfoPage.enterMiddleName(DataProvider.getColumnData(tcId,
				headerName));
		test.ContactInfoPage.clickContinue();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("Education & Employment");
	}

	@Test
	public void Step06_TC06_Enter_Valid_Last_Name_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValue.getASM_OMASmokeInfo("email"),
				getKeyValue.getASM_OMASmokeInfo("firstname"),
				DataProvider.getColumnData(tcId, headerName),
				getKeyValue.getASM_OMASmokeInfo("type"),
				getKeyValue.getASM_OMASmokeInfo("address"),
				getKeyValue.getASM_OMASmokeInfo("city"),
				getKeyValue.getASM_OMASmokeInfo("country"),
				getKeyValue.getASM_OMASmokeInfo("state"),
				getKeyValue.getASM_OMASmokeInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("Education & Employment");
	}

	@Test
	public void Step07_TC07_Enter_Valid_Address_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValue.getASM_OMASmokeInfo("email"),
				getKeyValue.getASM_OMASmokeInfo("firstname"),
				getKeyValue.getASM_OMASmokeInfo("lastname"),
				getKeyValue.getASM_OMASmokeInfo("type"),
				DataProvider.getColumnData(tcId, headerName),
				getKeyValue.getASM_OMASmokeInfo("city"),
				getKeyValue.getASM_OMASmokeInfo("country"),
				getKeyValue.getASM_OMASmokeInfo("state"),
				getKeyValue.getASM_OMASmokeInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("Education & Employment");
	}

	@Test
	public void Step08_TC08_Enter_Valid_Phone_Number_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValue.getASM_OMASmokeInfo("email"),
				getKeyValue.getASM_OMASmokeInfo("firstname"),
				getKeyValue.getASM_OMASmokeInfo("lastname"),
				getKeyValue.getASM_OMASmokeInfo("type"),
				getKeyValue.getASM_OMASmokeInfo("address"),
				getKeyValue.getASM_OMASmokeInfo("city"),
				getKeyValue.getASM_OMASmokeInfo("country"),
				getKeyValue.getASM_OMASmokeInfo("state"),
				getKeyValue.getASM_OMASmokeInfo("zipCode"));
		test.ContactInfoPage.enterPhoneNumber(DataProvider.getColumnData(tcId,
				headerName));
		test.ContactInfoPage.clickContinue();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("Education & Employment");
	}

	@Test
	public void Step09_TC09_Enter_Valid_Phone_Number_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValue.getASM_OMASmokeInfo("email"),
				getKeyValue.getASM_OMASmokeInfo("firstname"),
				getKeyValue.getASM_OMASmokeInfo("lastname"),
				getKeyValue.getASM_OMASmokeInfo("type"),
				getKeyValue.getASM_OMASmokeInfo("address"),
				getKeyValue.getASM_OMASmokeInfo("city"),
				getKeyValue.getASM_OMASmokeInfo("country"),
				getKeyValue.getASM_OMASmokeInfo("state"),
				getKeyValue.getASM_OMASmokeInfo("zipCode"));
		test.ContactInfoPage.enterPhoneNumber(DataProvider.getColumnData(tcId,
				headerName));
		test.ContactInfoPage.clickContinue();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("Education & Employment");
	}

	@Test
	public void Step10_TC10_Enter_Valid_City_Name_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValue.getASM_OMASmokeInfo("email"),
				getKeyValue.getASM_OMASmokeInfo("firstname"),
				getKeyValue.getASM_OMASmokeInfo("lastname"),
				getKeyValue.getASM_OMASmokeInfo("type"),
				getKeyValue.getASM_OMASmokeInfo("address"),
				DataProvider.getColumnData(tcId, headerName),
				getKeyValue.getASM_OMASmokeInfo("country"),
				getKeyValue.getASM_OMASmokeInfo("state"),
				getKeyValue.getASM_OMASmokeInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("Education & Employment");
	}

	@Test
	public void Step12_TC12_Enter_Valid_Zipcode_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValue.getASM_OMASmokeInfo("email"),
				getKeyValue.getASM_OMASmokeInfo("firstname"),
				getKeyValue.getASM_OMASmokeInfo("lastname"),
				getKeyValue.getASM_OMASmokeInfo("type"),
				getKeyValue.getASM_OMASmokeInfo("address"),
				getKeyValue.getASM_OMASmokeInfo("city"),
				getKeyValue.getASM_OMASmokeInfo("country"),
				getKeyValue.getASM_OMASmokeInfo("state"),
				DataProvider.getColumnData(tcId, headerName));
		test.ContactInfoPage.clickContinue();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("Education & Employment");
	}

	@Test
	public void Step13_TC13_Enter_Valid_Employer_Name_Current_Student_No_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValue.getASM_OMASmokeInfo("email"),
				getKeyValue.getASM_OMASmokeInfo("firstname"),
				getKeyValue.getASM_OMASmokeInfo("lastname"),
				getKeyValue.getASM_OMASmokeInfo("type"),
				getKeyValue.getASM_OMASmokeInfo("address"),
				getKeyValue.getASM_OMASmokeInfo("city"),
				getKeyValue.getASM_OMASmokeInfo("country"),
				getKeyValue.getASM_OMASmokeInfo("state"),
				getKeyValue.getASM_OMASmokeInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("Education & Employment");
		test.EduAndEmpPage.enterEducationAndEmploymentInformation_ASMOMA();
		test.EduAndEmpPage.enterEmployerName(DataProvider.getColumnData(tcId,
				headerName));
		test.ContactInfoPage.clickContinue();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("Benefits");
	}

	@Test
	public void Step14_TC14_Enter_Invalid_Employer_Name_Current_Student_No_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValue.getASM_OMASmokeInfo("email"),
				getKeyValue.getASM_OMASmokeInfo("firstname"),
				getKeyValue.getASM_OMASmokeInfo("lastname"),
				getKeyValue.getASM_OMASmokeInfo("type"),
				getKeyValue.getASM_OMASmokeInfo("address"),
				getKeyValue.getASM_OMASmokeInfo("city"),
				getKeyValue.getASM_OMASmokeInfo("country"),
				getKeyValue.getASM_OMASmokeInfo("state"),
				getKeyValue.getASM_OMASmokeInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("Education & Employment");
		test.EduAndEmpPage.enterEducationAndEmploymentInformation_ASMOMA();
		test.EduAndEmpPage.enterEmployerName(DataProvider.getColumnData(tcId,
				headerName));
		test.ContactInfoPage.clickContinue();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("Benefits");
	}

	@Test
	public void Step15_TC15_Enter_Valid_Job_Experience_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValue.getASM_OMASmokeInfo("email"),
				getKeyValue.getASM_OMASmokeInfo("firstname"),
				getKeyValue.getASM_OMASmokeInfo("lastname"),
				getKeyValue.getASM_OMASmokeInfo("type"),
				getKeyValue.getASM_OMASmokeInfo("address"),
				getKeyValue.getASM_OMASmokeInfo("city"),
				getKeyValue.getASM_OMASmokeInfo("country"),
				getKeyValue.getASM_OMASmokeInfo("state"),
				getKeyValue.getASM_OMASmokeInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("Education & Employment");
		test.EduAndEmpPage.enterEducationAndEmploymentInformation_ASMOMA();
		test.EduAndEmpPage.enterJobProfessionalExp(DataProvider.getColumnData(
				tcId, headerName));
		test.ContactInfoPage.clickContinue();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("Benefits");
	}

	@Test
	public void Step16_TC16_Enter_Valid_Job_Experience_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValue.getASM_OMASmokeInfo("email"),
				getKeyValue.getASM_OMASmokeInfo("firstname"),
				getKeyValue.getASM_OMASmokeInfo("lastname"),
				getKeyValue.getASM_OMASmokeInfo("type"),
				getKeyValue.getASM_OMASmokeInfo("address"),
				getKeyValue.getASM_OMASmokeInfo("city"),
				getKeyValue.getASM_OMASmokeInfo("country"),
				getKeyValue.getASM_OMASmokeInfo("state"),
				getKeyValue.getASM_OMASmokeInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("Education & Employment");
		test.EduAndEmpPage.enterEducationAndEmploymentInformation_ASMOMA();
		test.EduAndEmpPage.enterJobProfessionalExp(DataProvider.getColumnData(
				tcId, headerName));
		test.ContactInfoPage.clickContinue();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("Benefits");
	}

	@Test
	public void Step17_TC17_Enter_University_Name_And_Verify_Asm_Error_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValue.getASM_OMASmokeInfo("email"),
				getKeyValue.getASM_OMASmokeInfo("firstname"),
				getKeyValue.getASM_OMASmokeInfo("lastname"),
				getKeyValue.getASM_OMASmokeInfo("type"),
				getKeyValue.getASM_OMASmokeInfo("address"),
				getKeyValue.getASM_OMASmokeInfo("city"),
				getKeyValue.getASM_OMASmokeInfo("country"),
				getKeyValue.getASM_OMASmokeInfo("state"),
				getKeyValue.getASM_OMASmokeInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("Education & Employment");
		test.EduAndEmpPage
				.enterEducationAndEmploymentInformation_ASMOMA_Student_Yes();
		test.EduAndEmpPage.enterUniversityName_ASMOMA(DataProvider
				.getColumnData(tcId, headerName));
		test.ContactInfoPage.clickContinue();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("Benefits");
	}

	@Test
	public void Step18_TC18_Enter_Valid_Employer_Name_Current_Student_Yes_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValue.getASM_OMASmokeInfo("email"),
				getKeyValue.getASM_OMASmokeInfo("firstname"),
				getKeyValue.getASM_OMASmokeInfo("lastname"),
				getKeyValue.getASM_OMASmokeInfo("type"),
				getKeyValue.getASM_OMASmokeInfo("address"),
				getKeyValue.getASM_OMASmokeInfo("city"),
				getKeyValue.getASM_OMASmokeInfo("country"),
				getKeyValue.getASM_OMASmokeInfo("state"),
				getKeyValue.getASM_OMASmokeInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("Education & Employment");
		test.EduAndEmpPage
				.enterEducationAndEmploymentInformation_ASMOMA_Student_Yes();
		test.EduAndEmpPage.enterUniversityName_ASMOMA(getKeyValue
				.getASM_OMASmokeInfo("university"));
		test.EduAndEmpPage.enterEmployerName(DataProvider.getColumnData(tcId,
				headerName));
		test.ContactInfoPage.clickContinue();

		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step19_TC19_Enter_Valid_Employer_Name_Current_Student_Yes_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValue.getASM_OMASmokeInfo("email"),
				getKeyValue.getASM_OMASmokeInfo("firstname"),
				getKeyValue.getASM_OMASmokeInfo("lastname"),
				getKeyValue.getASM_OMASmokeInfo("type"),
				getKeyValue.getASM_OMASmokeInfo("address"),
				getKeyValue.getASM_OMASmokeInfo("city"),
				getKeyValue.getASM_OMASmokeInfo("country"),
				getKeyValue.getASM_OMASmokeInfo("state"),
				getKeyValue.getASM_OMASmokeInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("Education & Employment");
		test.EduAndEmpPage
				.enterEducationAndEmploymentInformation_ASMOMA_Student_Yes();
		test.EduAndEmpPage.enterUniversityName_ASMOMA(getKeyValue
				.getASM_OMASmokeInfo("university"));
		test.EduAndEmpPage.enterEmployerName(DataProvider.getColumnData(tcId,
				headerName));
		test.ContactInfoPage.clickContinue();

		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step20_TC20_Enter_Valid_Credit_Card_Number_In_Checkout_page_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValue.getASM_OMASmokeInfo("email"),
				getKeyValue.getASM_OMASmokeInfo("firstname"),
				getKeyValue.getASM_OMASmokeInfo("lastname"),
				getKeyValue.getASM_OMASmokeInfo("type"),
				getKeyValue.getASM_OMASmokeInfo("address"),
				getKeyValue.getASM_OMASmokeInfo("city"),
				getKeyValue.getASM_OMASmokeInfo("country"),
				getKeyValue.getASM_OMASmokeInfo("state"),
				getKeyValue.getASM_OMASmokeInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("Education & Employment");
		test.EduAndEmpPage.enterEducationAndEmploymentInformation_ASMOMA();
		test.EduAndEmpPage.enterJobProfessionalExp(getKeyValue
				.getASM_OMASmokeInfo("jobExp"));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("Benefits");
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("Checkout");
		test.checkoutPage.enterPaymentInfo(
				YamlReader.getYamlValue("creditCardInfo.Type"),
				YamlReader.getYamlValue("creditCardInfo.Holder-name"),
				DataProvider.getColumnData(tcId, headerName),
				YamlReader.getYamlValue("creditCardInfo.cvv-number"));
		test.checkoutPage.clickAtTestStatement();
		test.ContactInfoPage.clickContinue();
		test.checkoutPage.clickSubmitButtonAtBottom();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("Confirmation");
	}

	@Test
	public void Step21_TC21_Enter_InValid_Credit_Card_Number_In_Checkoutpage_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValue.getASM_OMASmokeInfo("email"),
				getKeyValue.getASM_OMASmokeInfo("firstname"),
				getKeyValue.getASM_OMASmokeInfo("lastname"),
				getKeyValue.getASM_OMASmokeInfo("type"),
				getKeyValue.getASM_OMASmokeInfo("address"),
				getKeyValue.getASM_OMASmokeInfo("city"),
				getKeyValue.getASM_OMASmokeInfo("country"),
				getKeyValue.getASM_OMASmokeInfo("state"),
				getKeyValue.getASM_OMASmokeInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("Education & Employment");
		test.EduAndEmpPage.enterEducationAndEmploymentInformation_ASMOMA();
		test.EduAndEmpPage.enterJobProfessionalExp(getKeyValue
				.getASM_OMASmokeInfo("jobExp"));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("Benefits");
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("Checkout");
		test.checkoutPage.enterPaymentInfo(
				YamlReader.getYamlValue("creditCardInfo.Type"),
				YamlReader.getYamlValue("creditCardInfo.Holder-name"),
				DataProvider.getColumnData(tcId, headerName),
				YamlReader.getYamlValue("creditCardInfo.cvv-number"));
		test.checkoutPage.clickAtTestStatement();
		test.ContactInfoPage.clickContinue();
		test.checkoutPage.clickSubmitButton();
		test.checkoutPage.verifyPaymentErrorPresent(getKeyValue
				.getASM_OMASmokeInfo("paymentErrorMessage"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step22_TC22_Enter_Valid_CVV_Number_In_Checkoutpage_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValue.getASM_OMASmokeInfo("email"),
				getKeyValue.getASM_OMASmokeInfo("firstname"),
				getKeyValue.getASM_OMASmokeInfo("lastname"),
				getKeyValue.getASM_OMASmokeInfo("type"),
				getKeyValue.getASM_OMASmokeInfo("address"),
				getKeyValue.getASM_OMASmokeInfo("city"),
				getKeyValue.getASM_OMASmokeInfo("country"),
				getKeyValue.getASM_OMASmokeInfo("state"),
				getKeyValue.getASM_OMASmokeInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("Education & Employment");
		test.EduAndEmpPage.enterEducationAndEmploymentInformation_ASMOMA();
		test.EduAndEmpPage.enterJobProfessionalExp(getKeyValue
				.getASM_OMASmokeInfo("jobExp"));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("Benefits");
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("Checkout");
		test.checkoutPage.enterPaymentInfo(
				YamlReader.getYamlValue("creditCardInfo.Type"),
				YamlReader.getYamlValue("creditCardInfo.Holder-name"),
				YamlReader.getYamlValue("creditCardInfo.Number"),
				DataProvider.getColumnData(tcId, headerName));
		test.checkoutPage.clickAtTestStatement();
		test.ContactInfoPage.clickContinue();
		test.checkoutPage.clickSubmitButtonAtBottom();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("Confirmation");
	}

	@Test
	public void Step23_TC23_Enter_Valid_Card_Holder_Name_In_Checkoutpage_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValue.getASM_OMASmokeInfo("email"),
				getKeyValue.getASM_OMASmokeInfo("firstname"),
				getKeyValue.getASM_OMASmokeInfo("lastname"),
				getKeyValue.getASM_OMASmokeInfo("type"),
				getKeyValue.getASM_OMASmokeInfo("address"),
				getKeyValue.getASM_OMASmokeInfo("city"),
				getKeyValue.getASM_OMASmokeInfo("country"),
				getKeyValue.getASM_OMASmokeInfo("state"),
				getKeyValue.getASM_OMASmokeInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("Education & Employment");
		test.EduAndEmpPage.enterEducationAndEmploymentInformation_ASMOMA();
		test.EduAndEmpPage.enterJobProfessionalExp(getKeyValue
				.getASM_OMASmokeInfo("jobExp"));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("Benefits");
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("Checkout");
		test.checkoutPage.enterPaymentInfo(
				YamlReader.getYamlValue("creditCardInfo.Type"),
				DataProvider.getColumnData(tcId, headerName),
				YamlReader.getYamlValue("creditCardInfo.Number"),
				YamlReader.getYamlValue("creditCardInfo.cvv-number"));
		test.checkoutPage.clickAtTestStatement();
		test.ContactInfoPage.clickContinue();
		test.checkoutPage.clickSubmitButtonAtBottom();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("Confirmation");
	}

	@BeforeMethod
	public void launchApplication() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url = getYamlValue("app_url_OMA");
		mapASM_OMASmoke = YamlReader.getYamlValues("OMA_SmokeChecklistData");
		getKeyValue = new YamlInformationProvider(mapASM_OMASmoke);
		mapASM_creditCardInfo = YamlReader.getYamlValues("creditCardInfo");
		getCreditCardValue = new YamlInformationProvider(mapASM_creditCardInfo);
		test.launchApplication(app_url);
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		
		test.takescreenshot.takeScreenShotOnException(result);
		test.closeBrowserSession();
	}


}
