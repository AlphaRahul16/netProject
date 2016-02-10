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

public class ASM_FellowNominate_Smoke {

	TestSessionInitiator test;
	String app_url_nominateFellow;
	YamlInformationProvider getFellowNominated;
	Map<String, Object> mapFellowNominatedSmoke;
	String headerName = this.getClass().getSimpleName();

	@Test
	public void Step01_TC01_Enter_Invalid_Username_And_Verify_ASM_Error_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_FellowNomiate
				.loginInToApplication(DataProvider.getColumnData(tcId,
						headerName), getFellowNominated
						.getASM_fellowNominated_LoginInfo("password"));
		test.asmErrorPage.verifyASMError(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step02_TC02_Enter_Invalid_Password_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_FellowNomiate
				.loginInToApplication(getFellowNominated
						.getASM_fellowNominated_LoginInfo("username"),
						DataProvider.getColumnData(tcId, headerName));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.asm_FellowNomiate
				.verifyLoginErrorMessagePresent(YamlReader
						.getYamlValue("ASM_fellowNominatedSmokeChecklistData.LoginErrorMessage"));
	}

	@Test
	public void Step03_TC03_Enter_Valid_Username_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_FellowNomiate
				.loginInToApplication(DataProvider.getColumnData(tcId,
						headerName), getFellowNominated
						.getASM_fellowNominated_LoginInfo("password"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		test.asm_FellowNomiate.verifyLoginSuccessfully();
	}

	@Test
	public void Step04_TC04_Enter_Invalid_Member_Number_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_FellowNomiate
				.loginInToApplication(getFellowNominated
						.getASM_fellowNominated_LoginInfo("username"),
						getFellowNominated
								.getASM_fellowNominated_LoginInfo("password"));
		test.asm_FellowNomiate.preRequisiteToidentifyNomineeByName_Number();
		test.asm_FellowNomiate.selectSearchTypeAndSearchNominee("Num",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_FellowNomiate
				.verifyErrorMessage(YamlReader
						.getYamlValue("ASM_fellowNominatedSmokeChecklistData.memberNumberSearchError"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step05_TC05_Enter_Invalid_Member_Name_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_FellowNomiate
				.loginInToApplication(getFellowNominated
						.getASM_fellowNominated_LoginInfo("username"),
						getFellowNominated
								.getASM_fellowNominated_LoginInfo("password"));
		test.asm_FellowNomiate.preRequisiteToidentifyNomineeByName_Number();
		test.asm_FellowNomiate.selectSearchTypeAndSearchNominee("Name",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_FellowNomiate
				.verifyErrorMessage(YamlReader
						.getYamlValue("ASM_fellowNominatedSmokeChecklistData.memberNameSearchError"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step06_TC06_Enter_link_In_Institution_Name_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_FellowNomiate
				.loginInToApplication(getFellowNominated
						.getASM_fellowNominated_LoginInfo("username"),
						getFellowNominated
								.getASM_fellowNominated_LoginInfo("password"));
		test.asm_FellowNomiate.preRequisiteForPrepareNomination("Name",
				getFellowNominated.getASM_fellowNominated("NomineeName"),
				"education");
		test.asm_FellowNomiate.enterAwardCitationDetails(DataProvider
				.getColumnData(tcId, headerName),getFellowNominated.getASM_fellowNominated("CommunityCitationMsg"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	
	}

	@Test
	public void Step07_TC07_Enter_valid_Institution_Name_In_Professional_History_And_Verify_ASM_Error_Not_Present() {

		test.asm_FellowNomiate
				.loginInToApplication(getFellowNominated
						.getASM_fellowNominated_LoginInfo("username"),
						getFellowNominated
								.getASM_fellowNominated_LoginInfo("password"));
		test.asm_FellowNomiate.preRequisiteForPrepareNomination("Name",
				getFellowNominated.getASM_fellowNominated("NomineeName"),
				"education");
		test.asm_FellowNomiate.enterEducationDetails(getFellowNominated
				.getASM_fellowNominated_EduDetails("Institution"),
				getFellowNominated.getASM_fellowNominated_EduDetails("Degree"),
				getFellowNominated.getASM_fellowNominated_EduDetails("Major"),
				getFellowNominated
						.getASM_fellowNominated_EduDetails("GraduationYear"));
		test.asm_FellowNomiate.verifyAddMoreButton();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step08_TC08_Enter_Invalid_Graduation_Year_In_Professional_History_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_FellowNomiate
				.loginInToApplication(getFellowNominated
						.getASM_fellowNominated_LoginInfo("username"),
						getFellowNominated
								.getASM_fellowNominated_LoginInfo("password"));
		test.asm_FellowNomiate.preRequisiteForPrepareNomination("Name",
				getFellowNominated.getASM_fellowNominated("NomineeName"),
				"education");
		test.asm_FellowNomiate.enterEducationDetails(getFellowNominated
				.getASM_fellowNominated_EduDetails("Institution"),
				getFellowNominated.getASM_fellowNominated_EduDetails("Degree"),
				getFellowNominated.getASM_fellowNominated_EduDetails("Major"),
				DataProvider.getColumnData(tcId, headerName));
		test.asm_FellowNomiate.verifyErrorMessage_Nomination(getFellowNominated
				.getASM_fellowNominated("gradYearErrorMsz"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step09_TC09_Enter_link_In_Employer_In_Professional_History_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_FellowNomiate
				.loginInToApplication(getFellowNominated
						.getASM_fellowNominated_LoginInfo("username"),
						getFellowNominated
								.getASM_fellowNominated_LoginInfo("password"));
		test.asm_FellowNomiate.preRequisiteForPrepareNomination("Name",
				getFellowNominated.getASM_fellowNominated("NomineeName"),
				"education");
		test.asm_FellowNomiate
				.selectNominationChecklistName("Professional History");
		test.asm_FellowNomiate
				.fillDetailsForProfessionalHistory(
						DataProvider.getColumnData(tcId, headerName),
						getFellowNominated
								.getASM_fellowNominated_ProfDetails("Title"),
						getFellowNominated
								.getASM_fellowNominated_ProfDetails("FromDate"),
						getFellowNominated
								.getASM_fellowNominated_ProfDetails("ToDate"),
						getFellowNominated
								.getASM_fellowNominated_ProfDetails("Description"));
		test.asm_FellowNomiate.verifyAddMoreButton();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step10_TC10_Enter_link_In_Professional_Organization_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_FellowNomiate
				.loginInToApplication(getFellowNominated
						.getASM_fellowNominated_LoginInfo("username"),
						getFellowNominated
								.getASM_fellowNominated_LoginInfo("password"));
		test.asm_FellowNomiate.preRequisiteForPrepareNomination("Name",
				getFellowNominated.getASM_fellowNominated("NomineeName"),
				"education");
		test.asm_FellowNomiate
				.selectNominationChecklistName("Professional Organization Affiliations");
		test.asm_FellowNomiate
				.fillDetailsForProfessionalOrganizationAffiliations(
						DataProvider.getColumnData(tcId, headerName),
						getFellowNominated
								.getASM_fellowNominated_ProfOrgDetails("Position_title"),
						getFellowNominated
								.getASM_fellowNominated_ProfOrgDetails("FromDate"),
						getFellowNominated
								.getASM_fellowNominated_ProfOrgDetails("ToDate"));
		test.asm_FellowNomiate.verifyAddMoreButton();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step11_TC11_Enter_less_To_Date_In_Professional_Organization_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_FellowNomiate
				.loginInToApplication(getFellowNominated
						.getASM_fellowNominated_LoginInfo("username"),
						getFellowNominated
								.getASM_fellowNominated_LoginInfo("password"));
		test.asm_FellowNomiate.preRequisiteForPrepareNomination("Name",
				getFellowNominated.getASM_fellowNominated("NomineeName"),
				"education");
		test.asm_FellowNomiate
				.selectNominationChecklistName("Professional Organization Affiliations");
		test.asm_FellowNomiate
				.fillDetailsForProfessionalOrganizationAffiliations(
						getFellowNominated
								.getASM_fellowNominated_ProfOrgDetails("ProfessionalOrg"),
						getFellowNominated
								.getASM_fellowNominated_ProfOrgDetails("Position_title"),
						getFellowNominated
								.getASM_fellowNominated_ProfOrgDetails("FromDate"),
						DataProvider.getColumnData(tcId, headerName));
		test.asm_FellowNomiate.verifyErrorMessage_Nomination(getFellowNominated
				.getASM_fellowNominated("LessToDateErrorMsz"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step12_TC12_Enter_Valid_Details_In_Professional_Organization_Affiliation_And_Verify_ASM_Error_Not_Present() {
		test.asm_FellowNomiate
				.loginInToApplication(getFellowNominated
						.getASM_fellowNominated_LoginInfo("username"),
						getFellowNominated
								.getASM_fellowNominated_LoginInfo("password"));
		test.asm_FellowNomiate.preRequisiteForPrepareNomination("Name",
				getFellowNominated.getASM_fellowNominated("NomineeName"),
				"education");
		test.asm_FellowNomiate
				.selectNominationChecklistName("Professional Organization Affiliations");
		test.asm_FellowNomiate
				.fillDetailsForProfessionalOrganizationAffiliations(
						getFellowNominated
								.getASM_fellowNominated_ProfOrgDetails("ProfessionalOrg"),
						getFellowNominated
								.getASM_fellowNominated_ProfOrgDetails("Position_title"),
						getFellowNominated
								.getASM_fellowNominated_ProfOrgDetails("FromDate"),
						getFellowNominated
								.getASM_fellowNominated_ProfOrgDetails("ToDate"));
		test.asm_FellowNomiate.verifyAddMoreButton();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step13_TC13_Enter_Less_To_Date_In_Volunteer_Service_To_The_ACS_Community_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_FellowNomiate
				.loginInToApplication(getFellowNominated
						.getASM_fellowNominated_LoginInfo("username"),
						getFellowNominated
								.getASM_fellowNominated_LoginInfo("password"));
		test.asm_FellowNomiate.preRequisiteForPrepareNomination("Name",
				getFellowNominated.getASM_fellowNominated("NomineeName"),
				"education");
		test.asm_FellowNomiate
				.selectNominationChecklistName("Volunteer Service to the ACS Community");
		test.asm_FellowNomiate.fillDetailsForVolunteerServiceToTheACSComunity(
				getFellowNominated.getASM_fellowNominated_ProfDetails("Title"),
				getFellowNominated
						.getASM_fellowNominated_ProfDetails("FromDate"),
				DataProvider.getColumnData(tcId, headerName),
				getFellowNominated
						.getASM_fellowNominated_ProfDetails("Description"),
				"service");
		test.asm_FellowNomiate.verifyErrorMessage_Nomination(getFellowNominated
				.getASM_fellowNominated("LessToDateErrorMsz"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step14_TC14_Enter_Valid_Details_In_Volunteer_Service_To_The_ACS_Community_And_Verify_ASM_Error_Not_Present() {
		test.asm_FellowNomiate
				.loginInToApplication(getFellowNominated
						.getASM_fellowNominated_LoginInfo("username"),
						getFellowNominated
								.getASM_fellowNominated_LoginInfo("password"));
		test.asm_FellowNomiate.preRequisiteForPrepareNomination("Name",
				getFellowNominated.getASM_fellowNominated("NomineeName"),
				"education");
		test.asm_FellowNomiate
				.selectNominationChecklistName("Volunteer Service to the ACS Community");
		test.asm_FellowNomiate
				.fillDetailsForVolunteerServiceToTheACSComunity(
						getFellowNominated
								.getASM_fellowNominated_ProfDetails("Title"),
						getFellowNominated
								.getASM_fellowNominated_ProfDetails("FromDate"),
						getFellowNominated
								.getASM_fellowNominated_ProfDetails("ToDate"),
						getFellowNominated
								.getASM_fellowNominated_ProfDetails("Description"),
						"service");
		test.asm_FellowNomiate.verifyAddMoreButton();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step15_TC15_Enter_Less_To_Date_In_Contributions_To_The_Science_Profession_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_FellowNomiate
				.loginInToApplication(getFellowNominated
						.getASM_fellowNominated_LoginInfo("username"),
						getFellowNominated
								.getASM_fellowNominated_LoginInfo("password"));
		test.asm_FellowNomiate.preRequisiteForPrepareNomination("Name",
				getFellowNominated.getASM_fellowNominated("NomineeName"),
				"education");
		test.asm_FellowNomiate
				.selectNominationChecklistName("Contributions to the Science / Profession");
		test.asm_FellowNomiate.fillDetailsForVolunteerServiceToTheACSComunity(
				getFellowNominated.getASM_fellowNominated_ProfDetails("Title"),
				getFellowNominated
						.getASM_fellowNominated_ProfDetails("FromDate"),
				DataProvider.getColumnData(tcId, headerName),
				getFellowNominated
						.getASM_fellowNominated_ProfDetails("Description"),
				"contribution");
		test.asm_FellowNomiate.verifyErrorMessage_Nomination(getFellowNominated
				.getASM_fellowNominated("LessToDateErrorMsz"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step16_TC16_Enter_Valid_Details_In_Contributions_To_The_Science_Profession_And_Verify_ASM_Error_Not_Present() {
		test.asm_FellowNomiate
				.loginInToApplication(getFellowNominated
						.getASM_fellowNominated_LoginInfo("username"),
						getFellowNominated
								.getASM_fellowNominated_LoginInfo("password"));
		test.asm_FellowNomiate.preRequisiteForPrepareNomination("Name",
				getFellowNominated.getASM_fellowNominated("NomineeName"),
				"education");
		test.asm_FellowNomiate
				.selectNominationChecklistName("Contributions to the Science / Profession");
		test.asm_FellowNomiate
				.fillDetailsForVolunteerServiceToTheACSComunity(
						getFellowNominated
								.getASM_fellowNominated_ProfDetails("Title"),
						getFellowNominated
								.getASM_fellowNominated_ProfDetails("FromDate"),
						getFellowNominated
								.getASM_fellowNominated_ProfDetails("ToDate"),
						getFellowNominated
								.getASM_fellowNominated_ProfDetails("Description"),
						"contribution");
		test.asm_FellowNomiate.verifyAddMoreButton();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step17_TC17_Enter_Link_In_Name_In_Honors_And_Awards_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_FellowNomiate
				.loginInToApplication(getFellowNominated
						.getASM_fellowNominated_LoginInfo("username"),
						getFellowNominated
								.getASM_fellowNominated_LoginInfo("password"));
		test.asm_FellowNomiate.preRequisiteForPrepareNomination("Name",
				getFellowNominated.getASM_fellowNominated("NomineeName"),
				"education");

		test.asm_FellowNomiate
				.selectNominationChecklistName("Honors and Awards");

		test.asm_FellowNomiate.fillDetailsForHonorAndAwards(DataProvider
				.getColumnData(tcId, headerName), getFellowNominated
				.getASM_fellowNominated_HonorAndAwardsDetails("Sponcor"),
				getFellowNominated
						.getASM_fellowNominated_HonorAndAwardsDetails("Year"));

		test.asm_FellowNomiate.verifyAddMoreButton();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step18_TC18_Enter_Link_In_Sponcor_In_Honors_And_Awards_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_FellowNomiate
				.loginInToApplication(getFellowNominated
						.getASM_fellowNominated_LoginInfo("username"),
						getFellowNominated
								.getASM_fellowNominated_LoginInfo("password"));
		test.asm_FellowNomiate.preRequisiteForPrepareNomination("Name",
				getFellowNominated.getASM_fellowNominated("NomineeName"),
				"education");

		test.asm_FellowNomiate
				.selectNominationChecklistName("Honors and Awards");

		test.asm_FellowNomiate.fillDetailsForHonorAndAwards(getFellowNominated
				.getASM_fellowNominated_HonorAndAwardsDetails("Name"),
				DataProvider.getColumnData(tcId, headerName),
				getFellowNominated
						.getASM_fellowNominated_HonorAndAwardsDetails("Year"));
		test.asm_FellowNomiate.verifyAddMoreButton();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step19_TC19_Enter_Valid_Details_In_Honors_And_Awards_And_Verify_ASM_Error_Not_Present() {
		test.asm_FellowNomiate
				.loginInToApplication(getFellowNominated
						.getASM_fellowNominated_LoginInfo("username"),
						getFellowNominated
								.getASM_fellowNominated_LoginInfo("password"));
		test.asm_FellowNomiate.preRequisiteForPrepareNomination("Name",
				getFellowNominated.getASM_fellowNominated("NomineeName"),
				"education");
		test.asm_FellowNomiate
				.selectNominationChecklistName("Honors and Awards");
		test.asm_FellowNomiate
				.fillDetailsForHonorAndAwards(
						getFellowNominated
								.getASM_fellowNominated_HonorAndAwardsDetails("Name"),
						getFellowNominated
								.getASM_fellowNominated_HonorAndAwardsDetails("Sponcor"),
						getFellowNominated
								.getASM_fellowNominated_HonorAndAwardsDetails("Year"));
		test.asm_FellowNomiate.verifyAddMoreButton();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step20_TC20_Search_By_Name_In_Secondary_Nominator_1_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_FellowNomiate
				.loginInToApplication(getFellowNominated
						.getASM_fellowNominated_LoginInfo("username"),
						getFellowNominated
								.getASM_fellowNominated_LoginInfo("password"));
		test.asm_FellowNomiate.preRequisiteForPrepareNomination("Name",
				getFellowNominated.getASM_fellowNominated("NomineeName"),
				"education");
		test.asm_FellowNomiate
				.selectNominationChecklistName("Select Supporters and Upload Letters");
		test.asm_FellowNomiate
				.searchByName_NumberForSecondaryNominatorAndVerifyMemberList(
						"name", "1",
						DataProvider.getColumnData(tcId, headerName));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step21_TC21_Search_By_Number_In_Secondary_Nominator_1_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_FellowNomiate
				.loginInToApplication(getFellowNominated
						.getASM_fellowNominated_LoginInfo("username"),
						getFellowNominated
								.getASM_fellowNominated_LoginInfo("password"));
		test.asm_FellowNomiate.preRequisiteForPrepareNomination("Name",
				getFellowNominated.getASM_fellowNominated("NomineeName"),
				"education");
		test.asm_FellowNomiate
				.selectNominationChecklistName("Select Supporters and Upload Letters");
		test.asm_FellowNomiate
				.searchByName_NumberForSecondaryNominatorAndVerifyMemberList(
						"number", "1",
						DataProvider.getColumnData(tcId, headerName));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step22_TC22_Search_By_Name_In_Secondary_Nominator_2_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_FellowNomiate
				.loginInToApplication(getFellowNominated
						.getASM_fellowNominated_LoginInfo("username"),
						getFellowNominated
								.getASM_fellowNominated_LoginInfo("password"));
		test.asm_FellowNomiate.preRequisiteForPrepareNomination("Name",
				getFellowNominated.getASM_fellowNominated("NomineeName"),
				"education");
		test.asm_FellowNomiate
				.selectNominationChecklistName("Select Supporters and Upload Letters");
		test.asm_FellowNomiate
				.searchByName_NumberForSecondaryNominatorAndVerifyMemberList(
						"name", "2",
						DataProvider.getColumnData(tcId, headerName));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step23_TC23_Search_By_Number_In_Secondary_Nominator_2_And_Verify_ASM_Error_Not_Present() {
		String tcId = test.asm_Donate.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_FellowNomiate
				.loginInToApplication(getFellowNominated
						.getASM_fellowNominated_LoginInfo("username"),
						getFellowNominated
								.getASM_fellowNominated_LoginInfo("password"));
		test.asm_FellowNomiate.preRequisiteForPrepareNomination("Name",
				getFellowNominated.getASM_fellowNominated("NomineeName"),
				"education");
		test.asm_FellowNomiate
				.selectNominationChecklistName("Select Supporters and Upload Letters");
		test.asm_FellowNomiate
				.searchByName_NumberForSecondaryNominatorAndVerifyMemberList(
						"number", "2",
						DataProvider.getColumnData(tcId, headerName));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step24_TC24_Submit_The_Nomination_And_Verify_ASM_Error_Not_Present() {
		test.asm_FellowNomiate
				.loginInToApplication(getFellowNominated
						.getASM_fellowNominated_LoginInfo("username"),
						getFellowNominated
								.getASM_fellowNominated_LoginInfo("password"));
		test.asm_FellowNomiate.preRequisiteForPrepareNomination("Name",
				getFellowNominated.getASM_fellowNominated("NomineeName"),
				"education");
		test.asm_FellowNomiate.fillAllRequiredDetailsToSubmit();
		test.asm_FellowNomiate.verifyNominationCompleted();
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@BeforeMethod
	public void OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		mapFellowNominatedSmoke = YamlReader
				.getYamlValues("ASM_fellowNominatedSmokeChecklistData");
		getFellowNominated = new YamlInformationProvider(
				mapFellowNominatedSmoke);
		app_url_nominateFellow = getYamlValue("app_url_nominateFellow");
		test.launchApplication(app_url_nominateFellow);
	}
	
	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
		//test.closeBrowserSession();
	}
}
