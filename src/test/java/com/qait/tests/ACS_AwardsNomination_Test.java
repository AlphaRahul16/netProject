package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.DataProvider;
import com.qait.automation.utils.DateUtil;

public class ACS_AwardsNomination_Test extends BaseTest{
	
	String app_url_Nominate, app_url_IWEB, app_url_nominateFellow;
	private String caseID;
	private String currentAwardName;
	List<String> memberDetails;
	private String[] memDetails;
	int numberOfDivisions;
	Map<String, String> mapAwardsNomination = new HashMap<String, String>();
	Map<String, String> createMemberCredentials;

	public ACS_AwardsNomination_Test() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "AwardNomination";
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_AwardsNomination_Test(String caseID) {
		this.caseID = caseID;

	}

	@Test(invocationCount = 4)
	public void Step01_TC01_CreateMember_As_A_Prerequisite_For_Award_Nomination() {
		test.homePageIWEB.addValuesInMap("AwardNomination", "3");
		test.homePageIWEB.clickOnAddIndividual();
		memDetails = test.addMember.enterMemberDetailsInAddIndividual();
		test.memberShipPage.getIndividualFullNameForAwardsNomination();
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("CRM");

	}

	@Test
	public void Step02_TC02_Launch_Iweb_And_Select_General_Award() {
		mapAwardsNomination = test.homePageIWEB.addValuesInMap("AwardNomination", caseID);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Awards");
		test.homePageIWEB.clickOnTab("Find Award");
		test.individualsPage.enterFieldValue("Award Year",
				DateUtil.getAnyDateForType("YYYY", 1, "year"));
		test.individualsPage.clickGoButton();
		currentAwardName = test.individualsPage
				.selectRandomGeneralAward_AwardNomination(DataProvider
						.getRandomSpecificLineFromTextFile("GeneralAwardList").trim());
		test.fundpofilePage
				.editpostToWebAndRemoveFromWebDates_AwardNomination();
		test.invoicePage.expandDetailsMenu("award stages/rounds");
		// test.invoicePage.verifyStartDateIsNotEmpty_AwardsNomination();
		test.invoicePage.collapseDetailsMenu("award stages/rounds");

	}

	@Test
	public void Step03_TC03_Launch_AwardsNominateApplication_And_Perform_Nomination() {
		createMemberCredentials = test.memberShipPage
				.getIndividualMapFromCreateMemberScript();
		test.launchApplication(app_url_Nominate);

		test.asm_NominatePage.loginInToAwardsNominateApplication(
				mapAwardsNomination, createMemberCredentials);
		test.asm_NominatePage
				.clickOnConfirmNominatorAdressDetailsIfAppears_AwardNomination();
		test.asm_NominatePage
				.selectAwardFromAwardListAndVerifyNominationMessage(currentAwardName);

		test.asm_NominatePage.clickOnCreateNewNominationButton();

		test.asm_NominatePage.SearchNomineeByMemeberNameOrNumber(
				mapAwardsNomination, createMemberCredentials);
		test.asm_NominatePage
				.FillEligibilityQuestionsDetails_AwardsNomination(mapAwardsNomination);
		test.asm_NominatePage.clickSaveForLaterButtonToNavigateToHomePage();
		test.asm_NominatePage.verifyAwardStatusOnHomePageAwardNomination(
				currentAwardName, "Incomplete Submission");

		test.asm_NominatePage.navigateToHomePageFromEligibilityQuestionPage();
		test.asm_NominatePage.FillDetailsOnVerifyEligibilityPage(
				mapAwardsNomination, createMemberCredentials);
		test.asm_NominatePage.clickSaveForLaterButtonToNavigateToHomePage();
		test.asm_NominatePage.verifyAwardStatusOnHomePageAwardNomination(
				currentAwardName, "Waiting for Confirmation");

		test.asm_NominatePage
				.NavigateToConfirmNominationPageAndVerifyNominationDetails(mapAwardsNomination);
		test.asm_NominatePage.clickOnSubmitButton();
		test.asm_NominatePage.verifyNominationSubmitted();
		test.asm_NominatePage.verifyAwardStatusOnHomePageAwardNomination(
				currentAwardName, "Nomination completed");

		// test.asm_NominatePage.verifyDocumentsAreDownloadableOnConfirmNominationPage("AwardNomination");
	}

	@Test
	public void Step04_TC04_Launch_Iweb_And_Verify_Details() {
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Awards");
		test.homePageIWEB.clickOnTab("Find Award");
		test.individualsPage.enterFieldValue("Award Year",
				DateUtil.getAnyDateForType("YYYY", 1, "year"));
		test.individualsPage.clickGoButton();

		test.individualsPage
				.selectRandomGeneralAward_AwardNomination(currentAwardName);
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Entrants");
		test.invoicePage.expandDetailsMenu("acs nominee/ entry");

		test.individualsPage
				.selectNomineeEntryForVerification(createMemberCredentials.get("Nominee0Name").trim());
		test.invoicePage.expandDetailsMenu("acs award entry supporter");
		test.individualsPage.verifyDetailsForAwardsNomination(
				mapAwardsNomination, createMemberCredentials);
		test.invoicePage.collapseDetailsMenu("acs award entry supporter");

		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Documents");
		test.invoicePage.expandDetailsMenu("document");
		test.individualsPage
				.verifyLetterDocumentsOnAwardEntryProfilePage(mapAwardsNomination);
		test.asm_NominatePage.verifyPdfContent();
		test.invoicePage.collapseDetailsMenu("document");
		test.individualsPage
				.clickOnEditButtonAndVerifyNomineeDetails_AwardRequirementsAndRecommendation(mapAwardsNomination);

	}

	@BeforeClass
	public void Open_Browser_Window() {

		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IWEB");
		app_url_Nominate = getYamlValue("app_url_Nominate");
		test.launchApplication(app_url_IWEB);
	}
}
