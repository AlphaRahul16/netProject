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
	private String caseID, currentAwardName;
	Map<String, List<String>> memberDetail = new HashMap<>();
	List<String> memberDetails, nameOfJudges, rescusedJudges;
	Set<String> numberOfNomineesInEntrants = new HashSet<>();
	int numberOfDivisions, maxPossibleNominees;
	Map<String, String> mapAwardsNomination = new HashMap<String, String>();
	Map<String, String> createMemberCredentials;
	List<List<String>> listOfFirstAndLastName = new ArrayList<>();
	Map<Integer, Map> listOfNomineeJudges_judgeRanks = new HashMap();
	Map<Integer, String> nomineeRanks = new HashMap<Integer, String>();
	Map<String, String> judgesRanks = new HashMap<String, String>();

	Map<Integer, String> confirmNominees = new HashMap<Integer, String>();
	List<Map<Integer, String>> listsOfRanks = new ArrayList<Map<Integer, String>>();

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

	@Test(dependsOnMethods = "Step01_TC01_Launch_Iweb_And_Select_Award")
	public void Step02_TC02_Verify_Nominees_And_Set_Start_End_Dates_Round_1() {
		test.invoicePage.expandDetailsMenu("award stages/rounds");
		test.awardsPageAction.uncheckClosedCheckbox_VotingClosed(
				currentAwardName, "1");
		test.invoicePage.collapseDetailsMenu("award stages/rounds");
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

	@Test(dependsOnMethods = "Step02_TC02_Verify_Nominees_And_Set_Start_End_Dates_Round_1")
	public void Step03_TC03_Add_Judges_Fetch_Rescused_Status() {
		test.invoicePage.expandDetailsMenu("award judges");
		test.awardsPageAction.verifyNumberOfJudgesAndAdd();
		nameOfJudges = test.awardsPageAction.getJudgesName();
		test.invoicePage.collapseDetailsMenu("award judges");
		test.invoicePage.expandDetailsMenu("award stages/rounds");
		test.awardsPageAction.goToRecordForRound("1");

		test.invoicePage.expandDetailsMenu("award judge");
		test.awardsPageAction.goToJudgeRecord(nameOfJudges.get(0));
		test.invoicePage.expandDetailsMenu("acs award judge score");
		test.awardsPageAction.verifyACSAwardStageEntriesInThisStageIsEmpty();

		test.invoicePage.expandDetailsMenu("award judge");
		rescusedJudges = test.awardsPageAction.getRecusedStatusForRounds("1");
		test.awardsPageAction.clickOnAwardsName_RoundName(currentAwardName);
		test.invoicePage.expandDetailsMenu("award judges");
		memberDetail = test.awardsPageAction.getJudgeDetails(nameOfJudges, "1");
	}

	@SuppressWarnings({ "unchecked", "static-access" })
	public void Step04_TC04_Launch_Awards_Voting_Application(int round) {
		listOfNomineeJudges_judgeRanks.put(0, nomineeRanks);
		listOfNomineeJudges_judgeRanks.put(1, judgesRanks);
		for (int i = 0; i < 5; i++) {
			test.homePageIWEB.addValuesInMap("AwardsVoting", caseID);
			test.launchApplication(app_url_Awards);
			// test.award_ewebPage.enterCredentials("ACSID");
			test.award_ewebPage.enterCredentials_LastNameMemberNumber_ACSID(
					test.award_ewebPage.map().get("LoginType"), nameOfJudges
							.get(i).trim(), memberDetail);
			test.award_ewebPage
					.verifyLoginInAwardApplicationSuccessfully(nameOfJudges
							.get(i));
			// Status - Progress Saved 04/18/2016
			// test.award_ewebPage.verifyStatus(rescusedJudges,
			// nameOfJudges.get(invocationCount));
			test.award_ewebPage.verifyAwardName(currentAwardName);
			test.award_ewebPage
					.verifyNumberOfDays("MM/d/YYYY", startEndDate[1]);
			// test.award_ewebPage.verifyNumberOfNominees(numberOfNomineesInEntrants
			// .size());
			test.award_ewebPage.verifySubmitBallotDate(startEndDate[1],
					currentAwardName);
			test.award_ewebPage
					.clickOnFiveYearNomineeMemoLink(currentAwardName);
//			test.award_ewebPage.extractAndCompareTextFromPdfFile(
//					"award_history", currentAwardName, 1, "downloads");
			maxPossibleNominees = test.award_ewebPage
					.getNumberOfPossibleNominees(currentAwardName);
			test.award_ewebPage
					.clickOnViewNominationMaterialButton(currentAwardName);
			test.award_ewebPage.unselectAllNominees();
			test.award_ewebPage
					.verifyHeaderForUnselectedNominee("You have selected 0 out of "
							+ maxPossibleNominees
							+ " possible nominations to rank.");
			listOfFirstAndLastName = test.award_ewebPage
					.selectRandomNominees(maxPossibleNominees);
			test.award_ewebPage
					.verifyHeaderForSelectedNominee("You have selected "
							+ maxPossibleNominees + " out of "
							+ maxPossibleNominees
							+ " possible nominations to rank.");
			test.award_ewebPage.provideComments(listOfFirstAndLastName,
					test.award_ewebPage.map().get("Comment Text"));
			test.award_ewebPage.clickOnViewProfileLink(listOfFirstAndLastName);
			test.award_ewebPage.clickOnProfilePdfLinkAndVerifyPdfContent(listOfFirstAndLastName);
			test.award_ewebPage
					.verifyAwardName_viewProfileLink(currentAwardName);
			test.award_ewebPage
					.verifyNominationDocuments_viewProfileLink(currentAwardName);
			test.award_ewebPage.clickOnCloseButton();
			test.award_ewebPage.clickOnRankNominees_Save("Rank Nominees");
			// nomineeRanks = test.award_ewebPage
			// .enterRankForNominee(maxPossibleNominees);

			listOfNomineeJudges_judgeRanks = test.award_ewebPage
					.enterRankForNominee_rank1ForFirstNominee(
							maxPossibleNominees, nameOfJudges, i,
							listOfFirstAndLastName, round,
							listOfNomineeJudges_judgeRanks.get(1));
			test.award_ewebPage.verifyConfirmBallotPage();
			confirmNominees = test.award_ewebPage.verifyNomineeRankAndName(
					listOfNomineeJudges_judgeRanks.get(0), maxPossibleNominees);
			test.award_ewebPage.clickOnSubmit_EditBallot("Submit Ballot");
			test.award_ewebPage.clickOnReturnToAwardDashboard();
			test.award_ewebPage
					.verifyStatusAfterBallotSubmission(currentAwardName);
			test.award_ewebPage
					.submissionDateAfterBallotSubmission(currentAwardName);
			listsOfRanks.add(listOfNomineeJudges_judgeRanks.get(0));
		}
	}

	public void Step05_TC05_Launch_Awards_Voting_IWeb() {
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Awards");
		test.homePageIWEB.clickOnTab("Find Award");

		test.individualsPage.enterFieldValue("Award Year",
				DateUtil.getAnyDateForType("YYYY", 1, "year"));
		test.individualsPage.clickGoButton();
		test.individualsPage
				.selectRandomGeneralAward_AwardNomination(currentAwardName
						.trim());
		test.invoicePage.expandDetailsMenu("award stages/rounds");
		test.awardsPageAction.goToRecordForRound("1");
		test.awardsPageAction
				.verifyUpdateScoreMessageOnClickingUpdateScore(test.homePageIWEB
						.map().get("Round1 Update Score Success Message?"));
		test.awardsPageAction
				.verifyClosedStatusOnUpdatingScore(test.homePageIWEB.map().get(
						"Round1 Closed Status?"));
		// test.invoicePage
		// .expandDetailsMenu("acs award stage - entries in this stage");
		// test.invoicePage.expandDetailsMenu("acs entries not in this stage");
		// test.awardsPageAction.verifyNomineesWithRankOne(listsOfRanks,
		// confirmNominees);
		// test.awardsPageAction.verifyNomineeWinnerStatus(test.homePageIWEB.map()
		// .get("Round1 Winner Status?"),"nominee name");

	}

	@Test(dependsOnMethods = "Step03_TC03_Add_Judges_Fetch_Rescused_Status")
	public void Step04_TC05_Awards_Voting_Awards_Voting_IWeb() {
		int votingRounds = Integer.parseInt(test.homePageIWEB.map()
				.get("Winner in rounds").replace("Round", ""));
		System.out.println("voting rounds :============= " + votingRounds);
		for (int round = votingRounds; round > 0; round--) {
			Step04_TC04_Launch_Awards_Voting_Application(round);
			Step05_TC05_Launch_Awards_Voting_IWeb();
		}
	}

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IWEB");
		app_url_Awards = getYamlValue("app_url_Awards");
		test.launchApplication(app_url_IWEB);
		// test.homePageIWEB.enterAuthenticationAutoIt();
		test.homePageIWEB.enterAuthentication(
				YamlReader.getYamlValue("Authentication.userName"),
				"Authentication.password");

	}

	// @AfterClass
	public void Close_Browser_Session() {
		test.closeBrowserSession();
	}

}
