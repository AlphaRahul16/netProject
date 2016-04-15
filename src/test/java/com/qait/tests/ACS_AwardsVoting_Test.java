package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	String[] startEndDate;
	private String caseID;
	private String currentAwardName;
	Map<String, List<String>> memberDetail = new HashMap<>();
	List<String> memberDetails, nameOfJudges, rescusedJudges;
	Set<String> numberOfNomineesInEntrants = new HashSet<>();
	int numberOfDivisions,maxPossibleNominees;
	Map<String, String> mapAwardsNomination = new HashMap<String, String>();
	Map<String, String> createMemberCredentials;
	List<List<String>> listOfFirstAndLastName = new ArrayList<>();

	public ACS_AwardsVoting_Test() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "AwardsVoting";
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_AwardsVoting_Test(String caseID) {
		this.caseID = caseID;
	}

	@Test
	public void Step01_TC01_Launch_Iweb_And_Select_Award() {
		test.homePageIWEB.addValuesInMap("AwardsVoting", caseID);
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
		numberOfNomineesInEntrants = test.awardsPageAction
				.allACSNomineesInEntrants();
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("General");
		test.invoicePage.expandDetailsMenu("award stages/rounds");
		test.awardsPageAction.verifyOrAddRoundsPresents();
		test.awardsPageAction.ClearStartDateAndEndDate_AllRounds();
		startEndDate = test.awardsPageAction.editStartAndEndDate_Round("1");
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
		rescusedJudges = test.awardsPageAction.getRecusedStatusForRounds("1");
		test.awardsPageAction.clickOnAwardsName_RoundName(currentAwardName);
		test.invoicePage.expandDetailsMenu("award judges");
		memberDetail = test.awardsPageAction.getJudgeDetails(nameOfJudges, "1");
	}

	@Test
	public void Step04_TC04_Launch_Awards_Voting_Application() {
		int i = 0;
		test.launchApplication(app_url_Awards);
		test.award_ewebPage.enterCredentials_LastNameMemberNumber_ACSID(
				test.award_ewebPage.map().get("LoginType"), nameOfJudges.get(i)
						.trim(), memberDetail);
		test.award_ewebPage
				.verifyLoginInAwardApplicationSuccessfully(nameOfJudges.get(i));
		test.award_ewebPage.verifyStatus(rescusedJudges, nameOfJudges.get(i));
		test.award_ewebPage.verifyAwardName(currentAwardName);
		// test.award_ewebPage.verifyNumberOfDays(numberOfDays);
		test.award_ewebPage.verifyNumberOfNominees(numberOfNomineesInEntrants
				.size());
		// test.award_ewebPage.verifySubmitBallotDate(startEndDate[1]);
		test.award_ewebPage.clickOnFiveYearNomineeMemoLink();
		test.award_ewebPage.clickOnViewNominationMaterialButton();
		test.award_ewebPage.verifyCurrentTab("Select Nominees");
		maxPossibleNominees=test.award_ewebPage.getNumberOfPossibleNominees();
		test.award_ewebPage
				.verifyHeaderForUnselectedNominee("You have selected 0 out of "
						+ maxPossibleNominees
						+ " possible nominations to rank.");
		listOfFirstAndLastName = test.award_ewebPage
				.selectRandomNominees(maxPossibleNominees);
		test.award_ewebPage.verifyHeaderForSelectedNominee("You have selected "
				+ maxPossibleNominees + " out of "
				+ maxPossibleNominees
				+ " possible nominations to rank.");
		test.award_ewebPage.clickOnViewProfileLink(listOfFirstAndLastName
				.get(1));
		test.award_ewebPage.verifyAwardName_viewProfileLink(currentAwardName);
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
