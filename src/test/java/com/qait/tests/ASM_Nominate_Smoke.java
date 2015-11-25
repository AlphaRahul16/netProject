package com.qait.tests;

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

public class ASM_Nominate_Smoke {

	TestSessionInitiator test;
	String app_url_Nominate;
	YamlInformationProvider getASM_Nominate;
	Map<String, Object> mapNominateSmoke;
	String headerName = this.getClass().getSimpleName();

	@Test
	public void Step01_TC01_Enter_Invalid_Lastname_And_Verify_ASM_Error() {
		String tcId = test.asm_NominatePage.getTestCaseID(Thread
				.currentThread().getStackTrace()[1].getMethodName());
		test.asm_NominatePage.loginInToApplication(
				DataProvider.getColumnData(tcId, headerName),
				getASM_Nominate.getASM_NominateInfo("memberNumber"));
		test.asmErrorPage.verifyASMError(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step02_TC02_Enter_Valid_Lastname_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_NominatePage.getTestCaseID(Thread
				.currentThread().getStackTrace()[1].getMethodName());
		test.asm_NominatePage.loginInToApplication(
				DataProvider.getColumnData(tcId, headerName),
				getASM_Nominate.getASM_NominateInfo("memberNumber"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step03_TC03_Enter_Invalid_Lastname_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_NominatePage.getTestCaseID(Thread
				.currentThread().getStackTrace()[1].getMethodName());
		test.asm_NominatePage.loginInToApplication(
				DataProvider.getColumnData(tcId, headerName),
				getASM_Nominate.getASM_NominateInfo("memberNumber"));
		test.asm_NominatePage.verifyLoginErrorMessage(getASM_Nominate
				.getASM_NominateInfo("loginErrorMessage"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step04_TC04_Enter_Valid_ACSID_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_NominatePage.getTestCaseID(Thread
				.currentThread().getStackTrace()[1].getMethodName());
		test.asm_NominatePage.loginInToApplication_ACSID(
				DataProvider.getColumnData(tcId, headerName),
				getASM_Nominate.getASM_NominateInfo("password"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step05_TC05_Enter_InValid_ACSID_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_NominatePage.getTestCaseID(Thread
				.currentThread().getStackTrace()[1].getMethodName());
		test.asm_NominatePage.loginInToApplication_ACSID(
				DataProvider.getColumnData(tcId, headerName),
				getASM_Nominate.getASM_NominateInfo("password"));
		test.asm_NominatePage.verifyLoginErrorMessage(getASM_Nominate
				.getASM_NominateInfo("loginErrorMessage"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step06_TC06_Enter_Valid_Nominees_Present_Position_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_NominatePage.getTestCaseID(Thread
				.currentThread().getStackTrace()[1].getMethodName());
		test.asm_NominatePage.loginInToApplication_ACSID(
				getASM_Nominate.getASM_NominateInfo("userName"),
				getASM_Nominate.getASM_NominateInfo("password"));
		test.asm_NominatePage.navigateToVerifyEligibilityTab(
				getASM_Nominate.getASM_NominateInfo("awardName"),
				getASM_Nominate.getASM_NominateInfo("nomineeName"),
				getASM_Nominate.getASM_NominateInfo("nomineePosition"),
				getASM_Nominate.getASM_NominateInfo("discipline"));
		test.asm_NominatePage.enterNomineePosition(DataProvider.getColumnData(
				tcId, headerName));
		test.asm_NominatePage.clickOnContinueButton();
		test.asm_NominatePage.verifyCurrentTab("Confirm Nomination");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step07_TC07_Enter_Valid_Suggested_Citation_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_NominatePage.getTestCaseID(Thread
				.currentThread().getStackTrace()[1].getMethodName());
		test.asm_NominatePage.loginInToApplication_ACSID(
				getASM_Nominate.getASM_NominateInfo("userName"),
				getASM_Nominate.getASM_NominateInfo("password"));
		test.asm_NominatePage.navigateToPrepareNominationTab(getASM_Nominate
				.getASM_NominateInfo("awardName"), getASM_Nominate
				.getASM_NominateInfo("nomineeName"), getASM_Nominate
				.getASM_NominateInfo("nomineePosition"), getASM_Nominate
				.getASM_NominateInfo("discipline"), DataProvider.getColumnData(
				tcId, headerName), getASM_Nominate
				.getASM_NominateInfo("recommendation"), getASM_Nominate
				.getASM_NominateInfo("pubPatentsFilename"), getASM_Nominate
				.getASM_NominateInfo("biographicalSketchFileName"),
				getASM_Nominate.getASM_NominateInfo("supportForm1FileName"),
				getASM_Nominate.getASM_NominateInfo("support1LastName"));
		test.asm_NominatePage.clickOnContinueButton();
		test.asm_NominatePage.verifyCurrentTab("Confirm Nomination");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step08_TC08_Enter_Valid_Recommendation_Text_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_NominatePage.getTestCaseID(Thread
				.currentThread().getStackTrace()[1].getMethodName());
		test.asm_NominatePage.loginInToApplication_ACSID(
				getASM_Nominate.getASM_NominateInfo("userName"),
				getASM_Nominate.getASM_NominateInfo("password"));
		test.asm_NominatePage.navigateToPrepareNominationTab(getASM_Nominate
				.getASM_NominateInfo("awardName"), getASM_Nominate
				.getASM_NominateInfo("nomineeName"), getASM_Nominate
				.getASM_NominateInfo("nomineePosition"), getASM_Nominate
				.getASM_NominateInfo("discipline"), getASM_Nominate
				.getASM_NominateInfo("suggestedCitation"), DataProvider
				.getColumnData(tcId, headerName), getASM_Nominate
				.getASM_NominateInfo("pubPatentsFilename"), getASM_Nominate
				.getASM_NominateInfo("biographicalSketchFileName"),
				getASM_Nominate.getASM_NominateInfo("supportForm1FileName"),
				getASM_Nominate.getASM_NominateInfo("support1LastName"));
		test.asm_NominatePage.clickOnContinueButton();
		test.asm_NominatePage.verifyCurrentTab("Confirm Nomination");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step09_TC09_Enter_InValid_Recommendation_Text_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_NominatePage.getTestCaseID(Thread
				.currentThread().getStackTrace()[1].getMethodName());
		test.asm_NominatePage.loginInToApplication_ACSID(
				getASM_Nominate.getASM_NominateInfo("userName"),
				getASM_Nominate.getASM_NominateInfo("password"));
		test.asm_NominatePage.navigateToPrepareNominationTab(getASM_Nominate
				.getASM_NominateInfo("awardName"), getASM_Nominate
				.getASM_NominateInfo("nomineeName"), getASM_Nominate
				.getASM_NominateInfo("nomineePosition"), getASM_Nominate
				.getASM_NominateInfo("discipline"), getASM_Nominate
				.getASM_NominateInfo("suggestedCitation"), DataProvider
				.getColumnData(tcId, headerName), getASM_Nominate
				.getASM_NominateInfo("pubPatentsFilename"), getASM_Nominate
				.getASM_NominateInfo("biographicalSketchFileName"),
				getASM_Nominate.getASM_NominateInfo("supportForm1FileName"),
				getASM_Nominate.getASM_NominateInfo("support1LastName"));
		test.asm_NominatePage.clickOnContinueButton();
		test.asm_NominatePage.verifyCurrentTab("Prepare Nomination");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step10_TC10_Upload_Valid_File_In_Recommendations_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_NominatePage.getTestCaseID(Thread
				.currentThread().getStackTrace()[1].getMethodName());
		test.asm_NominatePage.loginInToApplication_ACSID(
				getASM_Nominate.getASM_NominateInfo("userName"),
				getASM_Nominate.getASM_NominateInfo("password"));
		test.asm_NominatePage.navigateToPrepareNominationTab(getASM_Nominate
				.getASM_NominateInfo("awardName"), getASM_Nominate
				.getASM_NominateInfo("nomineeName"), getASM_Nominate
				.getASM_NominateInfo("nomineePosition"), getASM_Nominate
				.getASM_NominateInfo("discipline"), getASM_Nominate
				.getASM_NominateInfo("suggestedCitation"), getASM_Nominate
				.getASM_NominateInfo("recommendation"), getASM_Nominate
				.getASM_NominateInfo("pubPatentsFilename"), getASM_Nominate
				.getASM_NominateInfo("biographicalSketchFileName"),
				getASM_Nominate.getASM_NominateInfo("supportForm1FileName"),
				getASM_Nominate.getASM_NominateInfo("support1LastName"));
		test.asm_NominatePage.uploadFileInRecommendation(DataProvider
				.getColumnData(tcId, headerName));

		test.asm_NominatePage.clickOnContinueButton();
		test.asm_NominatePage.verifyCurrentTab("Confirm Nomination");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step11_TC11_Upload_InValid_File_In_Recommendations_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_NominatePage.getTestCaseID(Thread
				.currentThread().getStackTrace()[1].getMethodName());
		test.asm_NominatePage.loginInToApplication_ACSID(
				getASM_Nominate.getASM_NominateInfo("userName"),
				getASM_Nominate.getASM_NominateInfo("password"));
		test.asm_NominatePage.navigateToPrepareNominationTab(getASM_Nominate
				.getASM_NominateInfo("awardName"), getASM_Nominate
				.getASM_NominateInfo("nomineeName"), getASM_Nominate
				.getASM_NominateInfo("nomineePosition"), getASM_Nominate
				.getASM_NominateInfo("discipline"), getASM_Nominate
				.getASM_NominateInfo("suggestedCitation"), getASM_Nominate
				.getASM_NominateInfo("recommendation"), getASM_Nominate
				.getASM_NominateInfo("pubPatentsFilename"), getASM_Nominate
				.getASM_NominateInfo("biographicalSketchFileName"),
				getASM_Nominate.getASM_NominateInfo("supportForm1FileName"),
				getASM_Nominate.getASM_NominateInfo("support1LastName"));

		test.asm_NominatePage.uploadInValidFileInRecommendation(DataProvider
				.getColumnData(tcId, headerName));
		
		test.asm_NominatePage
				.verifyErrorMessageOnInvalidFileUpload(getASM_Nominate
						.getASM_NominateInfo("FileUploadErrorMessage"));
		test.asm_NominatePage.clickOnContinueButton();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step12_TC12_Upload_Valid_File_In_Publication_And_Patents_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_NominatePage.getTestCaseID(Thread
				.currentThread().getStackTrace()[1].getMethodName());
		test.asm_NominatePage.loginInToApplication_ACSID(
				getASM_Nominate.getASM_NominateInfo("userName"),
				getASM_Nominate.getASM_NominateInfo("password"));
		test.asm_NominatePage.navigateToPrepareNominationTab(getASM_Nominate
				.getASM_NominateInfo("awardName"), getASM_Nominate
				.getASM_NominateInfo("nomineeName"), getASM_Nominate
				.getASM_NominateInfo("nomineePosition"), getASM_Nominate
				.getASM_NominateInfo("discipline"), getASM_Nominate
				.getASM_NominateInfo("suggestedCitation"), getASM_Nominate
				.getASM_NominateInfo("recommendation"), DataProvider
				.getColumnData(tcId, headerName), getASM_Nominate
				.getASM_NominateInfo("biographicalSketchFileName"),
				getASM_Nominate.getASM_NominateInfo("supportForm1FileName"),
				getASM_Nominate.getASM_NominateInfo("support1LastName"));
		test.asm_NominatePage.clickOnContinueButton();
		test.asm_NominatePage.verifyCurrentTab("Confirm Nomination");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step13_TC13_Upload_InValid_File_In_Publication_And_Patents_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_NominatePage.getTestCaseID(Thread
				.currentThread().getStackTrace()[1].getMethodName());
		test.asm_NominatePage.loginInToApplication_ACSID(
				getASM_Nominate.getASM_NominateInfo("userName"),
				getASM_Nominate.getASM_NominateInfo("password"));
		test.asm_NominatePage.navigateToPrepareNominationTab(getASM_Nominate
				.getASM_NominateInfo("awardName"), getASM_Nominate
				.getASM_NominateInfo("nomineeName"), getASM_Nominate
				.getASM_NominateInfo("nomineePosition"), getASM_Nominate
				.getASM_NominateInfo("discipline"), getASM_Nominate
				.getASM_NominateInfo("suggestedCitation"), getASM_Nominate
				.getASM_NominateInfo("recommendation"), getASM_Nominate
				.getASM_NominateInfo("pubPatentsFilename"), getASM_Nominate
				.getASM_NominateInfo("biographicalSketchFileName"),
				getASM_Nominate.getASM_NominateInfo("supportForm1FileName"),
				getASM_Nominate.getASM_NominateInfo("support1LastName"));
		test.asm_NominatePage.uploadFile("pubsAndPatents",
				DataProvider.getColumnData(tcId, headerName), "1");
		test.asm_NominatePage
				.verifyErrorMessageOnInvalidFileUpload(getASM_Nominate
						.getASM_NominateInfo("FileUploadErrorMessage"));
		test.asm_NominatePage.clickOnContinueButton();
		test.asm_NominatePage.verifyCurrentTab("Prepare Nomination");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step14_TC14_Upload_Valid_File_In_Biographical_Sketch_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_NominatePage.getTestCaseID(Thread
				.currentThread().getStackTrace()[1].getMethodName());
		test.asm_NominatePage.loginInToApplication_ACSID(
				getASM_Nominate.getASM_NominateInfo("userName"),
				getASM_Nominate.getASM_NominateInfo("password"));
		test.asm_NominatePage.navigateToPrepareNominationTab(
				getASM_Nominate.getASM_NominateInfo("awardName"),
				getASM_Nominate.getASM_NominateInfo("nomineeName"),
				getASM_Nominate.getASM_NominateInfo("nomineePosition"),
				getASM_Nominate.getASM_NominateInfo("discipline"),
				getASM_Nominate.getASM_NominateInfo("suggestedCitation"),
				getASM_Nominate.getASM_NominateInfo("recommendation"),
				getASM_Nominate.getASM_NominateInfo("pubPatentsFilename"),
				DataProvider.getColumnData(tcId, headerName),
				getASM_Nominate.getASM_NominateInfo("supportForm1FileName"),
				getASM_Nominate.getASM_NominateInfo("support1LastName"));
		test.asm_NominatePage.clickOnContinueButton();
		test.asm_NominatePage.verifyCurrentTab("Confirm Nomination");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step15_TC15_Upload_InValid_File_In_Biographical_Sketch_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_NominatePage.getTestCaseID(Thread
				.currentThread().getStackTrace()[1].getMethodName());
		test.asm_NominatePage.loginInToApplication_ACSID(
				getASM_Nominate.getASM_NominateInfo("userName"),
				getASM_Nominate.getASM_NominateInfo("password"));
		test.asm_NominatePage.navigateToPrepareNominationTab(getASM_Nominate
				.getASM_NominateInfo("awardName"), getASM_Nominate
				.getASM_NominateInfo("nomineeName"), getASM_Nominate
				.getASM_NominateInfo("nomineePosition"), getASM_Nominate
				.getASM_NominateInfo("discipline"), getASM_Nominate
				.getASM_NominateInfo("suggestedCitation"), getASM_Nominate
				.getASM_NominateInfo("recommendation"), getASM_Nominate
				.getASM_NominateInfo("pubPatentsFilename"), getASM_Nominate
				.getASM_NominateInfo("biographicalSketchFileName"),
				getASM_Nominate.getASM_NominateInfo("supportForm1FileName"),
				getASM_Nominate.getASM_NominateInfo("support1LastName"));
		test.asm_NominatePage.uploadFile("biography",
				DataProvider.getColumnData(tcId, headerName), "2");
		test.asm_NominatePage
				.verifyErrorMessageOnInvalidFileUpload(getASM_Nominate
						.getASM_NominateInfo("FileUploadErrorMessage"));
		test.asm_NominatePage.clickOnContinueButton();
		test.asm_NominatePage.verifyCurrentTab("Prepare Nomination");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step16_TC16_Upload_Valid_File_In_Support_Form1_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_NominatePage.getTestCaseID(Thread
				.currentThread().getStackTrace()[1].getMethodName());
		test.asm_NominatePage.loginInToApplication_ACSID(
				getASM_Nominate.getASM_NominateInfo("userName"),
				getASM_Nominate.getASM_NominateInfo("password"));
		test.asm_NominatePage.navigateToPrepareNominationTab(getASM_Nominate
				.getASM_NominateInfo("awardName"), getASM_Nominate
				.getASM_NominateInfo("nomineeName"), getASM_Nominate
				.getASM_NominateInfo("nomineePosition"), getASM_Nominate
				.getASM_NominateInfo("discipline"), getASM_Nominate
				.getASM_NominateInfo("suggestedCitation"), getASM_Nominate
				.getASM_NominateInfo("recommendation"), getASM_Nominate
				.getASM_NominateInfo("pubPatentsFilename"), getASM_Nominate
				.getASM_NominateInfo("biographicalSketchFileName"),
				DataProvider.getColumnData(tcId, headerName), getASM_Nominate
						.getASM_NominateInfo("support1LastName"));
		test.asm_NominatePage.clickOnContinueButton();
		test.asm_NominatePage.verifyCurrentTab("Confirm Nomination");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step17_TC17_Upload_InValid_File_In_Support_Form1_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_NominatePage.getTestCaseID(Thread
				.currentThread().getStackTrace()[1].getMethodName());
		test.asm_NominatePage.loginInToApplication_ACSID(
				getASM_Nominate.getASM_NominateInfo("userName"),
				getASM_Nominate.getASM_NominateInfo("password"));
		test.asm_NominatePage.navigateToPrepareNominationTab(getASM_Nominate
				.getASM_NominateInfo("awardName"), getASM_Nominate
				.getASM_NominateInfo("nomineeName"), getASM_Nominate
				.getASM_NominateInfo("nomineePosition"), getASM_Nominate
				.getASM_NominateInfo("discipline"), getASM_Nominate
				.getASM_NominateInfo("suggestedCitation"), getASM_Nominate
				.getASM_NominateInfo("recommendation"), getASM_Nominate
				.getASM_NominateInfo("pubPatentsFilename"), getASM_Nominate
				.getASM_NominateInfo("biographicalSketchFileName"),
				getASM_Nominate.getASM_NominateInfo("supportForm1FileName"),
				getASM_Nominate.getASM_NominateInfo("support1LastName"));
		test.asm_NominatePage.uploadFile("supporter1",
				DataProvider.getColumnData(tcId, headerName), "3");
		test.asm_NominatePage
				.verifyErrorMessageOnInvalidFileUpload(getASM_Nominate
						.getASM_NominateInfo("FileUploadErrorMessage"));
		test.asm_NominatePage.clickOnContinueButton();
		test.asm_NominatePage.verifyCurrentTab("Prepare Nomination");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step18_TC18_Upload_Valid_File_In_Support_Form2_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_NominatePage.getTestCaseID(Thread
				.currentThread().getStackTrace()[1].getMethodName());
		test.asm_NominatePage.loginInToApplication_ACSID(
				getASM_Nominate.getASM_NominateInfo("userName"),
				getASM_Nominate.getASM_NominateInfo("password"));
		test.asm_NominatePage.navigateToPrepareNominationTab(getASM_Nominate
				.getASM_NominateInfo("awardName"), getASM_Nominate
				.getASM_NominateInfo("nomineeName"), getASM_Nominate
				.getASM_NominateInfo("nomineePosition"), getASM_Nominate
				.getASM_NominateInfo("discipline"), getASM_Nominate
				.getASM_NominateInfo("suggestedCitation"), getASM_Nominate
				.getASM_NominateInfo("recommendation"), getASM_Nominate
				.getASM_NominateInfo("pubPatentsFilename"), getASM_Nominate
				.getASM_NominateInfo("biographicalSketchFileName"),
				getASM_Nominate.getASM_NominateInfo("supportForm1FileName"),
				getASM_Nominate.getASM_NominateInfo("support1LastName"));
		test.asm_NominatePage.uploadFileForSupportForm(
				getASM_Nominate.getASM_NominateInfo("support2LastName"),
				DataProvider.getColumnData(tcId, headerName), "2");
		test.asm_NominatePage.clickOnContinueButton();
		test.asm_NominatePage.verifyCurrentTab("Confirm Nomination");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step19_TC19_Upload_InValid_File_In_Support_Form2_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_NominatePage.getTestCaseID(Thread
				.currentThread().getStackTrace()[1].getMethodName());
		test.asm_NominatePage.loginInToApplication_ACSID(
				getASM_Nominate.getASM_NominateInfo("userName"),
				getASM_Nominate.getASM_NominateInfo("password"));
		test.asm_NominatePage.navigateToPrepareNominationTab(getASM_Nominate
				.getASM_NominateInfo("awardName"), getASM_Nominate
				.getASM_NominateInfo("nomineeName"), getASM_Nominate
				.getASM_NominateInfo("nomineePosition"), getASM_Nominate
				.getASM_NominateInfo("discipline"), getASM_Nominate
				.getASM_NominateInfo("suggestedCitation"), getASM_Nominate
				.getASM_NominateInfo("recommendation"), getASM_Nominate
				.getASM_NominateInfo("pubPatentsFilename"), getASM_Nominate
				.getASM_NominateInfo("biographicalSketchFileName"),
				getASM_Nominate.getASM_NominateInfo("supportForm1FileName"),
				getASM_Nominate.getASM_NominateInfo("support1LastName"));
		test.asm_NominatePage.uploadFileForSupportForm(
				getASM_Nominate.getASM_NominateInfo("support2LastName"),
				DataProvider.getColumnData(tcId, headerName), "2");
		test.asm_NominatePage
				.verifyErrorMessageOnInvalidFileUpload(getASM_Nominate
						.getASM_NominateInfo("FileUploadErrorMessage"));
		test.asm_NominatePage.clickOnContinueButton();
		test.asm_NominatePage.verifyCurrentTab("Prepare Nomination");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step20_TC20_Fill_Details_In_Prepared_Nomination_And_Submit_And_Verify_ASM_Error_Not_Present() {
		test.asm_NominatePage.loginInToApplication_ACSID(
				getASM_Nominate.getASM_NominateInfo("userName"),
				getASM_Nominate.getASM_NominateInfo("password"));
		test.asm_NominatePage.navigateToPrepareNominationTab(getASM_Nominate
				.getASM_NominateInfo("awardName"), getASM_Nominate
				.getASM_NominateInfo("nomineeName"), getASM_Nominate
				.getASM_NominateInfo("nomineePosition"), getASM_Nominate
				.getASM_NominateInfo("discipline"), getASM_Nominate
				.getASM_NominateInfo("suggestedCitation"), getASM_Nominate
				.getASM_NominateInfo("recommendation"), getASM_Nominate
				.getASM_NominateInfo("pubPatentsFilename"), getASM_Nominate
				.getASM_NominateInfo("biographicalSketchFileName"),
				getASM_Nominate.getASM_NominateInfo("supportForm1FileName"),
				getASM_Nominate.getASM_NominateInfo("support1LastName"));
		test.asm_NominatePage.clickOnContinueButton();
		test.asm_NominatePage.verifyCurrentTab("Confirm Nomination");
		test.asm_NominatePage.clickOnSubmitButton();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step21_TC21_Fill_Details_In_Prepared_Nomination_And_Go_Back_And_Verify_ASM_Error_Not_Present() {
		test.asm_NominatePage.loginInToApplication_ACSID(
				getASM_Nominate.getASM_NominateInfo("userName"),
				getASM_Nominate.getASM_NominateInfo("password"));
		test.asm_NominatePage.navigateToPrepareNominationTab(getASM_Nominate
				.getASM_NominateInfo("awardName"), getASM_Nominate
				.getASM_NominateInfo("nomineeName"), getASM_Nominate
				.getASM_NominateInfo("nomineePosition"), getASM_Nominate
				.getASM_NominateInfo("discipline"), getASM_Nominate
				.getASM_NominateInfo("suggestedCitation"), getASM_Nominate
				.getASM_NominateInfo("recommendation"), getASM_Nominate
				.getASM_NominateInfo("pubPatentsFilename"), getASM_Nominate
				.getASM_NominateInfo("biographicalSketchFileName"),
				getASM_Nominate.getASM_NominateInfo("supportForm1FileName"),
				getASM_Nominate.getASM_NominateInfo("support1LastName"));
		test.asm_NominatePage.clickOnContinueButton();
		test.asm_NominatePage.verifyCurrentTab("Confirm Nomination");
		test.asm_NominatePage.clickonGoBackButton();
		test.asm_NominatePage.verifyCurrentTab("Prepare Nomination");
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@BeforeMethod
	public void launchApplication() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_Nominate = getYamlValue("app_url_Nominate");
		mapNominateSmoke = YamlReader
				.getYamlValues("ASM_Nominate_SmokeChecklistData");
		getASM_Nominate = new YamlInformationProvider(mapNominateSmoke);
		test.launchApplication(app_url_Nominate);
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
		test.closeBrowserSession();
	}

}
