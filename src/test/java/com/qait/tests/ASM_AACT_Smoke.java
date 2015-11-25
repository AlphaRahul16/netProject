package com.qait.tests;

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

public class ASM_AACT_Smoke {
	TestSessionInitiator test;
	String app_url;
	Map<String, Object> mapASM_AACTSmoke;
	YamlInformationProvider getKeyValueAACT;
	String headerName = this.getClass().getSimpleName();

	@Test
	public void Step01_TC01_Enter_First_Name_And_Verify_ASM_Error_Is_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValueAACT.get_AACTContactInfo("email"),
				DataProvider.getColumnData(tcId, headerName),
				getKeyValueAACT.get_AACTContactInfo("lastname"),
				getKeyValueAACT.get_AACTContactInfo("type"),
				getKeyValueAACT.get_AACTContactInfo("address"),
				getKeyValueAACT.get_AACTContactInfo("city"),
				getKeyValueAACT.get_AACTContactInfo("country"),
				getKeyValueAACT.get_AACTContactInfo("state"),
				getKeyValueAACT.get_AACTContactInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.asmErrorPage.verifyASMError(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step02_TC02_Enter_Valid_Email_Address_And_Verify_ASM_Error_Is_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValueAACT.get_AACTContactInfo("email"),
				getKeyValueAACT.get_AACTContactInfo("firstname"),
				getKeyValueAACT.get_AACTContactInfo("lastname"),
				getKeyValueAACT.get_AACTContactInfo("type"),
				getKeyValueAACT.get_AACTContactInfo("address"),
				getKeyValueAACT.get_AACTContactInfo("city"),
				getKeyValueAACT.get_AACTContactInfo("country"),
				getKeyValueAACT.get_AACTContactInfo("state"),
				getKeyValueAACT.get_AACTContactInfo("zipCode"));
		test.ContactInfoPage.enterEmail_ASMOMA(DataProvider.getColumnData(tcId,
				headerName));
		test.ContactInfoPage.clickContinue();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("About You");
	}

	@Test
	public void Step03_TC03_Enter_InValid_Email_Address_And_Verify_ASM_Error_Is_Not_Present_InValid_Email_Format_Message() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValueAACT.get_AACTContactInfo("email"),
				getKeyValueAACT.get_AACTContactInfo("firstname"),
				getKeyValueAACT.get_AACTContactInfo("lastname"),
				getKeyValueAACT.get_AACTContactInfo("type"),
				getKeyValueAACT.get_AACTContactInfo("address"),
				getKeyValueAACT.get_AACTContactInfo("city"),
				getKeyValueAACT.get_AACTContactInfo("country"),
				getKeyValueAACT.get_AACTContactInfo("state"),
				getKeyValueAACT.get_AACTContactInfo("zipCode"));
		test.ContactInfoPage.enterInvalidEmail_ASMOMA(DataProvider
				.getColumnData(tcId, headerName));
		test.ContactInfoPage.clickContinue();
		test.asm_aactPage.verifyInvalidEmailFormat();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("Contact Information");
	}

	@Test
	public void Step04_TC04_Enter_Valid_First_Name_And_Verify_ASM_Error_Is_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValueAACT.get_AACTContactInfo("email"),
				DataProvider.getColumnData(tcId, headerName),
				getKeyValueAACT.get_AACTContactInfo("lastname"),
				getKeyValueAACT.get_AACTContactInfo("type"),
				getKeyValueAACT.get_AACTContactInfo("address"),
				getKeyValueAACT.get_AACTContactInfo("city"),
				getKeyValueAACT.get_AACTContactInfo("country"),
				getKeyValueAACT.get_AACTContactInfo("state"),
				getKeyValueAACT.get_AACTContactInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("About You");

	}

	@Test
	public void Step05_TC05_Enter_Valid_Middle_Name_And_Verify_ASM_Error_Is_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValueAACT.get_AACTContactInfo("email"),
				getKeyValueAACT.get_AACTContactInfo("firstname"),
				getKeyValueAACT.get_AACTContactInfo("lastname"),
				getKeyValueAACT.get_AACTContactInfo("type"),
				getKeyValueAACT.get_AACTContactInfo("address"),
				getKeyValueAACT.get_AACTContactInfo("city"),
				getKeyValueAACT.get_AACTContactInfo("country"),
				getKeyValueAACT.get_AACTContactInfo("state"),
				getKeyValueAACT.get_AACTContactInfo("zipCode"));
		test.ContactInfoPage.enterMiddleName(DataProvider.getColumnData(tcId,
				headerName));
		test.ContactInfoPage.clickContinue();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("About You");

	}

	@Test
	public void Step06_TC06_Enter_Valid_Last_Name_And_Verify_ASM_Error_Is_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValueAACT.get_AACTContactInfo("email"),
				getKeyValueAACT.get_AACTContactInfo("firstname"),
				DataProvider.getColumnData(tcId, headerName),
				getKeyValueAACT.get_AACTContactInfo("type"),
				getKeyValueAACT.get_AACTContactInfo("address"),
				getKeyValueAACT.get_AACTContactInfo("city"),
				getKeyValueAACT.get_AACTContactInfo("country"),
				getKeyValueAACT.get_AACTContactInfo("state"),
				getKeyValueAACT.get_AACTContactInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("About You");

	}

	@Test
	public void Step07_TC07_select_Valid_Address_Type_And_Verify_ASM_Error_Is_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValueAACT.get_AACTContactInfo("email"),
				getKeyValueAACT.get_AACTContactInfo("firstname"),
				getKeyValueAACT.get_AACTContactInfo("lastname"),
				getKeyValueAACT.get_AACTContactInfo("type"),
				getKeyValueAACT.get_AACTContactInfo("address"),
				getKeyValueAACT.get_AACTContactInfo("city"),
				getKeyValueAACT.get_AACTContactInfo("country"),
				getKeyValueAACT.get_AACTContactInfo("state"),
				getKeyValueAACT.get_AACTContactInfo("zipCode"));
		test.asm_aactPage.selectAddressType(DataProvider.getColumnData(tcId,
				headerName));
		test.ContactInfoPage.clickContinue();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("About You");
	}

	@Test
	public void Step08_TC08_Enter_Valid_Address_And_Verify_ASM_Error_Is_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValueAACT.get_AACTContactInfo("email"),
				getKeyValueAACT.get_AACTContactInfo("firstname"),
				getKeyValueAACT.get_AACTContactInfo("lastname"),
				getKeyValueAACT.get_AACTContactInfo("type"),
				DataProvider.getColumnData(tcId, headerName),
				getKeyValueAACT.get_AACTContactInfo("city"),
				getKeyValueAACT.get_AACTContactInfo("country"),
				getKeyValueAACT.get_AACTContactInfo("state"),
				getKeyValueAACT.get_AACTContactInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("About You");
	}

	@Test
	public void Step09_TC09_Enter_Valid_Phone_Number_And_Verify_ASM_Error_Is_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValueAACT.get_AACTContactInfo("email"),
				getKeyValueAACT.get_AACTContactInfo("firstname"),
				getKeyValueAACT.get_AACTContactInfo("lastname"),
				getKeyValueAACT.get_AACTContactInfo("type"),
				getKeyValueAACT.get_AACTContactInfo("address"),
				getKeyValueAACT.get_AACTContactInfo("city"),
				getKeyValueAACT.get_AACTContactInfo("country"),
				getKeyValueAACT.get_AACTContactInfo("state"),
				getKeyValueAACT.get_AACTContactInfo("zipCode"));
		test.ContactInfoPage.enterPhoneNumber(DataProvider.getColumnData(tcId,
				headerName));
		test.ContactInfoPage.clickContinue();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("About You");
	}

	@Test
	public void Step10_TC10_Enter_Valid_Phone_Number_And_Verify_ASM_Error_Is_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValueAACT.get_AACTContactInfo("email"),
				getKeyValueAACT.get_AACTContactInfo("firstname"),
				getKeyValueAACT.get_AACTContactInfo("lastname"),
				getKeyValueAACT.get_AACTContactInfo("type"),
				getKeyValueAACT.get_AACTContactInfo("address"),
				getKeyValueAACT.get_AACTContactInfo("city"),
				getKeyValueAACT.get_AACTContactInfo("country"),
				getKeyValueAACT.get_AACTContactInfo("state"),
				getKeyValueAACT.get_AACTContactInfo("zipCode"));
		test.ContactInfoPage.enterPhoneNumber(DataProvider.getColumnData(tcId,
				headerName));
		test.ContactInfoPage.clickContinue();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("About You");
	}

	@Test
	public void Step11_TC11_Enter_Valid_City_And_Verify_ASM_Error_Is_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValueAACT.get_AACTContactInfo("email"),
				getKeyValueAACT.get_AACTContactInfo("firstname"),
				getKeyValueAACT.get_AACTContactInfo("lastname"),
				getKeyValueAACT.get_AACTContactInfo("type"),
				getKeyValueAACT.get_AACTContactInfo("address"),
				getKeyValueAACT.get_AACTContactInfo("city"),
				getKeyValueAACT.get_AACTContactInfo("country"),
				getKeyValueAACT.get_AACTContactInfo("state"),
				getKeyValueAACT.get_AACTContactInfo("zipCode"));
		test.ContactInfoPage.enterCity(DataProvider.getColumnData(tcId,
				headerName));
		test.ContactInfoPage.clickContinue();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("About You");
	}

	@Test
	public void Step12_TC12_Select_Country_And_Verify_ASM_Error_Is_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValueAACT.get_AACTContactInfo("email"),
				getKeyValueAACT.get_AACTContactInfo("firstname"),
				getKeyValueAACT.get_AACTContactInfo("lastname"),
				getKeyValueAACT.get_AACTContactInfo("type"),
				getKeyValueAACT.get_AACTContactInfo("address"),
				getKeyValueAACT.get_AACTContactInfo("city"),
				getKeyValueAACT.get_AACTContactInfo("country"),
				getKeyValueAACT.get_AACTContactInfo("state"),
				getKeyValueAACT.get_AACTContactInfo("zipCode"));
		test.ContactInfoPage.selectCountry(DataProvider.getColumnData(tcId,
				headerName));
		test.ContactInfoPage.clickContinue();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("About You");
	}

	@Test
	public void Step13_TC13_Enter_Valid_Zip_Code_And_Verify_ASM_Error_Is_Not_Present() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValueAACT.get_AACTContactInfo("email"),
				getKeyValueAACT.get_AACTContactInfo("firstname"),
				getKeyValueAACT.get_AACTContactInfo("lastname"),
				getKeyValueAACT.get_AACTContactInfo("type"),
				getKeyValueAACT.get_AACTContactInfo("address"),
				getKeyValueAACT.get_AACTContactInfo("city"),
				getKeyValueAACT.get_AACTContactInfo("country"),
				getKeyValueAACT.get_AACTContactInfo("state"),
				getKeyValueAACT.get_AACTContactInfo("zipCode"));
		test.ContactInfoPage.enterZipCode(DataProvider.getColumnData(tcId,
				headerName));
		test.ContactInfoPage.clickContinue();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.homePage.verifyCurrentTab("About You");
	}

	@Test
	public void Step14_TC14_Enter_Valid_Organization_Name_And_Verify_ASM_Error_Is_Not_Present_At_About_You_Page() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValueAACT.get_AACTContactInfo("email"),
				getKeyValueAACT.get_AACTContactInfo("firstname"),
				getKeyValueAACT.get_AACTContactInfo("lastname"),
				getKeyValueAACT.get_AACTContactInfo("type"),
				getKeyValueAACT.get_AACTContactInfo("address"),
				getKeyValueAACT.get_AACTContactInfo("city"),
				getKeyValueAACT.get_AACTContactInfo("country"),
				getKeyValueAACT.get_AACTContactInfo("state"),
				getKeyValueAACT.get_AACTContactInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("About You");
		test.asm_aactPage.enterValidInformationAtAboutYouPage();
		test.asm_aactPage.enterMemberContactDetail("Organization",
				DataProvider.getColumnData(tcId, headerName));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("Checkout");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));

	}

	@Test
	public void Step15_TC15_Enter_Valid_Department_Name_And_Verify_ASM_Error_Is_Not_Present_At_About_You_Page() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValueAACT.get_AACTContactInfo("email"),
				getKeyValueAACT.get_AACTContactInfo("firstname"),
				getKeyValueAACT.get_AACTContactInfo("lastname"),
				getKeyValueAACT.get_AACTContactInfo("type"),
				getKeyValueAACT.get_AACTContactInfo("address"),
				getKeyValueAACT.get_AACTContactInfo("city"),
				getKeyValueAACT.get_AACTContactInfo("country"),
				getKeyValueAACT.get_AACTContactInfo("state"),
				getKeyValueAACT.get_AACTContactInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("About You");
		test.asm_aactPage.enterValidInformationAtAboutYouPage();
		test.asm_aactPage.enterMemberContactDetail("DeptMail",
				DataProvider.getColumnData(tcId, headerName));
		test.ContactInfoPage.clickContinue();
		test.homePage.waitForTabToDissappear("About You");
		test.homePage.verifyCurrentTab("Checkout");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step16_TC16_Enter_Valid_Address_And_Verify_ASM_Error_Is_Not_Present_At_About_You_Page() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValueAACT.get_AACTContactInfo("email"),
				getKeyValueAACT.get_AACTContactInfo("firstname"),
				getKeyValueAACT.get_AACTContactInfo("lastname"),
				getKeyValueAACT.get_AACTContactInfo("type"),
				getKeyValueAACT.get_AACTContactInfo("address"),
				getKeyValueAACT.get_AACTContactInfo("city"),
				getKeyValueAACT.get_AACTContactInfo("country"),
				getKeyValueAACT.get_AACTContactInfo("state"),
				getKeyValueAACT.get_AACTContactInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("About You");
		test.asm_aactPage.enterValidInformationAtAboutYouPage();
		test.asm_aactPage.enterMemberContactDetail("AddressLine2",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_aactPage.enterMemberContactDetail("AddressLine1",
				DataProvider.getColumnData(tcId, headerName));
		test.ContactInfoPage.clickContinue();
		test.homePage.waitForTabToDissappear("About You");
		test.homePage.verifyCurrentTab("Checkout");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step17_TC17_Enter_Valid_City_And_Verify_ASM_Error_Is_Not_Present_At_About_You_Page() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValueAACT.get_AACTContactInfo("email"),
				getKeyValueAACT.get_AACTContactInfo("firstname"),
				getKeyValueAACT.get_AACTContactInfo("lastname"),
				getKeyValueAACT.get_AACTContactInfo("type"),
				getKeyValueAACT.get_AACTContactInfo("address"),
				getKeyValueAACT.get_AACTContactInfo("city"),
				getKeyValueAACT.get_AACTContactInfo("country"),
				getKeyValueAACT.get_AACTContactInfo("state"),
				getKeyValueAACT.get_AACTContactInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("About You");
		test.asm_aactPage.enterValidInformationAtAboutYouPage();
		test.asm_aactPage.enterMemberContactDetail("City",
				DataProvider.getColumnData(tcId, headerName));
		test.ContactInfoPage.clickContinue();
		test.homePage.waitForTabToDissappear("About You");
		test.homePage.verifyCurrentTab("Checkout");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step18_TC18_select_Country_And_Verify_ASM_Error_Is_Not_Present_At_About_You_Page() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValueAACT.get_AACTContactInfo("email"),
				getKeyValueAACT.get_AACTContactInfo("firstname"),
				getKeyValueAACT.get_AACTContactInfo("lastname"),
				getKeyValueAACT.get_AACTContactInfo("type"),
				getKeyValueAACT.get_AACTContactInfo("address"),
				getKeyValueAACT.get_AACTContactInfo("city"),
				getKeyValueAACT.get_AACTContactInfo("country"),
				getKeyValueAACT.get_AACTContactInfo("state"),
				getKeyValueAACT.get_AACTContactInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("About You");
		test.asm_aactPage.enterValidInformationAtAboutYouPage();
		test.asm_aactPage.selectMemberContactDetail("Country",
				DataProvider.getColumnData(tcId, headerName));
		test.ContactInfoPage.clickContinue();
		test.homePage.waitForTabToDissappear("About You");
		test.homePage.verifyCurrentTab("Checkout");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step19_TC19_Enter_Valid_Zip_Code_And_Verify_ASM_Error_Is_Not_Present_At_About_You_Page() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValueAACT.get_AACTContactInfo("email"),
				getKeyValueAACT.get_AACTContactInfo("firstname"),
				getKeyValueAACT.get_AACTContactInfo("lastname"),
				getKeyValueAACT.get_AACTContactInfo("type"),
				getKeyValueAACT.get_AACTContactInfo("address"),
				getKeyValueAACT.get_AACTContactInfo("city"),
				getKeyValueAACT.get_AACTContactInfo("country"),
				getKeyValueAACT.get_AACTContactInfo("state"),
				getKeyValueAACT.get_AACTContactInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("About You");
		test.asm_aactPage.enterValidInformationAtAboutYouPage();
		test.asm_aactPage.enterMemberContactDetail("ZipCode",
				DataProvider.getColumnData(tcId, headerName));
		test.ContactInfoPage.clickContinue();
		test.homePage.waitForTabToDissappear("About You");
		test.homePage.verifyCurrentTab("Checkout");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step20_TC20_select_Instructional_And_Verify_ASM_Error_Is_Not_Present_At_About_You_Page() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValueAACT.get_AACTContactInfo("email"),
				getKeyValueAACT.get_AACTContactInfo("firstname"),
				getKeyValueAACT.get_AACTContactInfo("lastname"),
				getKeyValueAACT.get_AACTContactInfo("type"),
				getKeyValueAACT.get_AACTContactInfo("address"),
				getKeyValueAACT.get_AACTContactInfo("city"),
				getKeyValueAACT.get_AACTContactInfo("country"),
				getKeyValueAACT.get_AACTContactInfo("state"),
				getKeyValueAACT.get_AACTContactInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("About You");
		test.asm_aactPage.enterValidInformationAtAboutYouPage();
		test.asm_aactPage.checkMemberContactDetail(DataProvider.getColumnData(
				tcId, headerName));
		test.ContactInfoPage.clickContinue();
		test.homePage.waitForTabToDissappear("About You");
		test.homePage.verifyCurrentTab("Checkout");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step21_TC21_enter_Valid_Credit_Card_Number_And_Verify_ASM_Error_Is_Not_Present_At_Checkout_Page() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValueAACT.get_AACTContactInfo("email"),
				getKeyValueAACT.get_AACTContactInfo("firstname"),
				getKeyValueAACT.get_AACTContactInfo("lastname"),
				getKeyValueAACT.get_AACTContactInfo("type"),
				getKeyValueAACT.get_AACTContactInfo("address"),
				getKeyValueAACT.get_AACTContactInfo("city"),
				getKeyValueAACT.get_AACTContactInfo("country"),
				getKeyValueAACT.get_AACTContactInfo("state"),
				getKeyValueAACT.get_AACTContactInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("About You");
		test.asm_aactPage.enterValidInformationAtAboutYouPage();
		test.ContactInfoPage.clickContinue();
		test.homePage.waitForTabToDissappear("About You");
		test.homePage.verifyCurrentTab("Checkout");
		test.checkoutPage.enterPaymentInfo(
				getKeyValueAACT.get_AACT_CreditCardInfo("Type"),
				getKeyValueAACT.get_AACT_CreditCardInfo("Holder-name"),
				DataProvider.getColumnData(tcId, headerName),
				getKeyValueAACT.get_AACT_CreditCardInfo("cvv-number"));
		test.checkoutPage.clickAtTestStatement();
		test.ContactInfoPage.clickContinue();
		test.checkoutPage.clickSubmitButtonAtBottom();
		test.homePage.verifyCurrentTab("Confirmation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step22_TC22_enter_Invalid_Credit_Card_Number_And_Verify_ASM_Error_Is_Not_Present_At_Checkout_Page() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValueAACT.get_AACTContactInfo("email"),
				getKeyValueAACT.get_AACTContactInfo("firstname"),
				getKeyValueAACT.get_AACTContactInfo("lastname"),
				getKeyValueAACT.get_AACTContactInfo("type"),
				getKeyValueAACT.get_AACTContactInfo("address"),
				getKeyValueAACT.get_AACTContactInfo("city"),
				getKeyValueAACT.get_AACTContactInfo("country"),
				getKeyValueAACT.get_AACTContactInfo("state"),
				getKeyValueAACT.get_AACTContactInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("About You");
		test.asm_aactPage.enterValidInformationAtAboutYouPage();
		test.ContactInfoPage.clickContinue();
		test.homePage.waitForTabToDissappear("About You");
		test.homePage.verifyCurrentTab("Checkout");
		test.checkoutPage.enterPaymentInfo(
				getKeyValueAACT.get_AACT_CreditCardInfo("Type"),
				getKeyValueAACT.get_AACT_CreditCardInfo("Holder-name"),
				DataProvider.getColumnData(tcId, headerName),
				getKeyValueAACT.get_AACT_CreditCardInfo("cvv-number"));
		test.checkoutPage.clickAtTestStatement();
		test.ContactInfoPage.clickContinue();
		test.checkoutPage.clickSubmitButton();
		test.asm_aactPage.verifyPaymentErrorPresent();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step23_TC23_enter_Valid_Credit_Card_Verification_And_Verify_ASM_Error_Is_Not_Present_At_Checkout_Page() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValueAACT.get_AACTContactInfo("email"),
				getKeyValueAACT.get_AACTContactInfo("firstname"),
				getKeyValueAACT.get_AACTContactInfo("lastname"),
				getKeyValueAACT.get_AACTContactInfo("type"),
				getKeyValueAACT.get_AACTContactInfo("address"),
				getKeyValueAACT.get_AACTContactInfo("city"),
				getKeyValueAACT.get_AACTContactInfo("country"),
				getKeyValueAACT.get_AACTContactInfo("state"),
				getKeyValueAACT.get_AACTContactInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("About You");
		test.asm_aactPage.enterValidInformationAtAboutYouPage();
		test.ContactInfoPage.clickContinue();
		test.homePage.waitForTabToDissappear("About You");
		test.homePage.verifyCurrentTab("Checkout");
		test.checkoutPage.enterPaymentInfo(
				getKeyValueAACT.get_AACT_CreditCardInfo("Type"),
				getKeyValueAACT.get_AACT_CreditCardInfo("Holder-name"),
				getKeyValueAACT.get_AACT_CreditCardInfo("Number"),
				DataProvider.getColumnData(tcId, headerName));
		test.checkoutPage.clickAtTestStatement();
		test.ContactInfoPage.clickContinue();
		test.checkoutPage.clickSubmitButtonAtBottom();
		test.homePage.verifyCurrentTab("Confirmation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step24_TC24_enter_Valid_Credit_Card_Holder_Name_And_Verify_ASM_Error_Is_Not_Present_At_Checkout_Page() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.ContactInfoPage.enterContactInformation(
				getKeyValueAACT.get_AACTContactInfo("email"),
				getKeyValueAACT.get_AACTContactInfo("firstname"),
				getKeyValueAACT.get_AACTContactInfo("lastname"),
				getKeyValueAACT.get_AACTContactInfo("type"),
				getKeyValueAACT.get_AACTContactInfo("address"),
				getKeyValueAACT.get_AACTContactInfo("city"),
				getKeyValueAACT.get_AACTContactInfo("country"),
				getKeyValueAACT.get_AACTContactInfo("state"),
				getKeyValueAACT.get_AACTContactInfo("zipCode"));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("About You");
		test.asm_aactPage.enterValidInformationAtAboutYouPage();
		test.ContactInfoPage.clickContinue();
		test.homePage.waitForTabToDissappear("About You");
		test.homePage.verifyCurrentTab("Checkout");
		test.checkoutPage.enterPaymentInfo(
				getKeyValueAACT.get_AACT_CreditCardInfo("Type"),
				DataProvider.getColumnData(tcId, headerName),
				getKeyValueAACT.get_AACT_CreditCardInfo("Number"),
				getKeyValueAACT.get_AACT_CreditCardInfo("cvv-number"));
		test.checkoutPage.clickAtTestStatement();
		test.ContactInfoPage.clickContinue();
		test.checkoutPage.clickSubmitButtonAtBottom();
		test.homePage.verifyCurrentTab("Confirmation");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	 @BeforeClass
	public void OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url = getYamlValue("app_url_AACT");
		mapASM_AACTSmoke = YamlReader.getYamlValues("AACT_SmokeChecklistData");
		getKeyValueAACT = new YamlInformationProvider(mapASM_AACTSmoke);
	}

	@BeforeMethod
	public void launchApplication() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url = getYamlValue("app_url_AACT");
		mapASM_AACTSmoke = YamlReader.getYamlValues("AACT_SmokeChecklistData");
		getKeyValueAACT = new YamlInformationProvider(mapASM_AACTSmoke);
		test.launchApplication(app_url);
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
		test.closeBrowserSession();
	}

	 @AfterClass(alwaysRun = true)
	public void Close_Test_Session() {
		test.closeBrowserSession();
	}

}
