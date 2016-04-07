package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.utils.DataProvider;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;
import com.qait.automation.TestSessionInitiator;

public class ACS_AwardsVoting_Test {
	TestSessionInitiator test;
	String app_url_IWEB, app_url_Awards;
	private String caseID;
	private String currentAwardName;

	List<String> memberDetails, nameOfJudges;

	int numberOfDivisions;
	Map<String, String> mapAwardsNomination = new HashMap<String, String>();
	Map<String, String> createMemberCredentials;

	public ACS_AwardsVoting_Test() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "AwardsVoting";

	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_AwardsVoting_Test(String caseID) {
		this.caseID = caseID;
	}

	@Test
	public void Step01_TC01_Launch_Iweb_And_Select_Award() {
		mapAwardsNomination = test.homePageIWEB.addValuesInMap("AwardsVoting",
				caseID);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Awards");
		test.homePageIWEB.clickOnTab("Find Award");
		test.individualsPage.enterFieldValue("Award Year",
				DateUtil.getAnyDateForType("YYYY", 1, "year"));
		test.individualsPage.clickGoButton();
		currentAwardName = test.individualsPage
				.selectRandomGeneralAward_AwardNomination(DataProvider
						.getRandomSpecificLineFromTextFile("GeneralAwardList")
						.trim());
	}

	@Test
	public void Step02_TC02_Verify_Nominees_And_Set_Start_End_Dates_Round_1() {

		test.individualsPage.navigateToEntrantsMenuOnHoveringMore();
		test.awardsPageAction.allACSNomineesInEntrants();
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("General");
		test.invoicePage.expandDetailsMenu("award stages/rounds");
		test.awardsPageAction.verifyOrAddRoundsPresents();
		test.awardsPageAction.ClearStartDateAndEndDate_AllRounds();
		test.awardsPageAction.editStartAndEndDate_Round("1");
		test.awardsPageAction.clickOnSaveButton();
		test.awardsPageAction.switchToDefaultContent();
		test.invoicePage.collapseDetailsMenu("award stages/rounds");
	}

	@Test
	public void Step03_TC03_Add_Judges_Fetch_Rescused_Status() {
		test.invoicePage.expandDetailsMenu("award judges");
		test.awardsPageAction.verifyNumberOfJudgesAndAdd();
		nameOfJudges = test.awardsPageAction.getJudgesName();
		test.invoicePage.collapseDetailsMenu("award judges");
		test.invoicePage.expandDetailsMenu("award stages/rounds");
		test.awardsPageAction.goToRecordForRound("1");
		test.invoicePage.expandDetailsMenu("award judge");
		test.awardsPageAction.getRecusedStatusForRounds("1");
		test.awardsPageAction.clickOnAwardsName_RoundName(currentAwardName);
		test.invoicePage.expandDetailsMenu("award judges");
		test.awardsPageAction.getJudgeDetails(nameOfJudges, "1");
	}

	// @Test
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
				.selectNomineeEntryForVerification(createMemberCredentials.get(
						"Nominee0Name").trim());
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
		app_url_Awards = getYamlValue("app_url_Awards");
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(
				YamlReader.getYamlValue("Authentication.userName"),
				"Authentication.password");
	}

	// @AfterClass
	public void Close_Browser_Session() {
		test.closeBrowserSession();
	}

}
