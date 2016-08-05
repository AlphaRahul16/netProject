package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.DataProvider;
import com.qait.automation.utils.DateUtil;

public class ACS_AwardsVoting_Test extends BaseTest {

	String app_url_IWEB, app_url_Awards, currentAwardName;
	String[] startEndDate;
	Map<String, List<String>> memberDetail = new HashMap<>();
	List<String> memberDetails, nameOfJudges, rescusedJudges;
	Set<String> numberOfNomineesInEntrants = new HashSet<>();
	int numberOfDivisions, maxPossibleNominees;
	Map<String, String> mapAwardsNomination = new HashMap<String, String>();
	Map<String, String> createMemberCredentials;
	List<List<String>> listOfFirstAndLastName = new ArrayList<>();

	Map<Integer, String> confirmNominees = new HashMap<Integer, String>();
	List<Map<Integer, String>> listsOfRanks = new ArrayList<Map<Integer, String>>();
	private String caseID;

	public ACS_AwardsVoting_Test() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "AwardsVoting";
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_AwardsVoting_Test(String caseID) {
		this.caseID = caseID;
	}

	@Test
	public void Step01_TC01_Launch_Iweb_Application_And_Select_Award() {
		test.homePageIWEB.addValuesInMap("AwardsVoting", caseID);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Awards");
		test.homePageIWEB.clickOnTab("Find Award");
		// test.individualsPage.enterFieldValue("Award Name","F. Albert Cotton Award in Synthetic Inorganic Chemistry");
		// test.individualsPage.enterFieldValue("Award Year","2018");
		test.individualsPage.enterFieldValue("Award Year",
				DateUtil.getAnyDateForType("YYYY", 2, "year"));
		test.individualsPage.clickGoButton();
		// currentAwardName =
		// "F. Albert Cotton Award in Synthetic Inorganic Chemistry:2018";
		currentAwardName = test.individualsPage
				.selectRandomGeneralAward_AwardNomination(DataProvider
						.getRandomSpecificLineFromTextFile(
								"GeneralAwardList_2018").trim());
	}

	@Test(dependsOnMethods = "Step01_TC01_Launch_Iweb_Application_And_Select_Award")
	public void Step02_TC02_Verify_Nominees_And_Set_Start_End_Dates_For_Round1_IWEB_Application() {
		test.awardsPageAction.editAwardStartAndEndDate();
		test.awardsPageAction.expandDetailsMenu("award stages/rounds");
		test.awardsPageAction.uncheckClosedCheckbox_VotingClosed(
				currentAwardName, "1");
		test.awardsPageAction.collapseDetailsMenu("award stages/rounds");
		test.individualsPage.navigateToEntrantsMenuOnHoveringMore();
		numberOfNomineesInEntrants = test.awardsPageAction
				.allACSNomineesInEntrants();
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("General");
		test.awardsPageAction.expandDetailsMenu("award stages/rounds");
		test.awardsPageAction.verifyOrAddRoundsPresents();
		test.awardsPageAction.ClearStartDateAndEndDate_AllRounds();
		startEndDate = test.awardsPageAction.editStartAndEndDate_Round(1);
		test.awardsPageAction.clickOnSaveButton();
		test.awardsPageAction.switchToDefaultContent();
		test.awardsPageAction.collapseDetailsMenu("award stages/rounds");
	}

	public void VerifyNumberOfJudgesToAdd_GetJudgeDetails(String roundNumber) {
		test.awardsPageAction.expandDetailsMenu("award judges");
		test.awardsPageAction.verifyNumberOfJudgesAndAdd(Integer
				.parseInt(roundNumber));
		nameOfJudges = test.awardsPageAction.getJudgesName(roundNumber);
		test.awardsPageAction.collapseDetailsMenu("award judges");
		test.awardsPageAction.expandDetailsMenu("award stages/rounds");
		test.awardsPageAction.goToRecordForRound(roundNumber);
		test.awardsPageAction.expandDetailsMenu("award judge");
		test.awardsPageAction.goToJudgeRecord(nameOfJudges.get(0));
		test.awardsPageAction.expandDetailsMenu("acs award judge score");
		test.awardsPageAction.verifyACSAwardStageEntriesInThisStageIsEmpty();
		test.awardsPageAction.navigateToBackPage();
		test.awardsPageAction.expandDetailsMenu("award judge");
		rescusedJudges = test.awardsPageAction
				.getRecusedStatusForRounds(roundNumber);
		test.awardsPageAction.clickOnAwardsName_RoundName(currentAwardName);
		test.awardsPageAction.expandDetailsMenu("award judges");
		test.awardsPageAction.goToRecordForRound(roundNumber);
		memberDetail = test.awardsPageAction.getJudgeDetails(nameOfJudges,
				roundNumber);
	}

	@SuppressWarnings({ "unchecked" })
	public void LaunchAwardsVotingApplicationAndProvideRanksToNomieesForVoting(
			int round, int count) {
		@SuppressWarnings("rawtypes")
		Map<Integer, Map> listOfNomineeJudges_judgeRanks = new HashMap();
		Map<Integer, String> nomineeRanks = new HashMap<Integer, String>();
		Map<String, String> judgesRanks = new HashMap<String, String>();
		listOfNomineeJudges_judgeRanks.put(0, nomineeRanks);
		listOfNomineeJudges_judgeRanks.put(1, judgesRanks);
		for (int i = 0; i < 5; i++) {
			Reporter.log("==========Login In To Awards Voting Application for round:- "
					+ count + "=================");
			test.homePageIWEB.addValuesInMap("AwardsVoting", caseID);
			test.launchApplication(app_url_Awards);
			test.award_ewebPage.enterCredentials_LastNameMemberNumber_ACSID(
					test.award_ewebPage.map().get("LoginType"), nameOfJudges
							.get(i).trim(), memberDetail);
			test.award_ewebPage
					.verifyLoginInAwardApplicationSuccessfully(nameOfJudges
							.get(i));

			test.award_ewebPage.verifyAwardName(currentAwardName);
			test.award_ewebPage
					.verifyNumberOfDays("MM/d/YYYY", startEndDate[1]);

			test.award_ewebPage.verifySubmitBallotDate(startEndDate[1],
					currentAwardName);

			// test.award_ewebPage
			// .clickOnFiveYearNomineeMemoLink(currentAwardName);
			maxPossibleNominees = test.award_ewebPage
					.getNumberOfPossibleNominees(currentAwardName);
			test.award_ewebPage
					.clickOnViewNominationMaterialButton(currentAwardName);
			test.award_ewebPage.unselectAllNominees();
			test.award_ewebPage
					.verifyHeaderForUnselectedNominee("You have selected 0 out of "
							+ maxPossibleNominees
							+ " possible nominations to rank.");

			listOfFirstAndLastName = test.award_ewebPage.selectRandomNominees(
					maxPossibleNominees, round, nameOfJudges,
					listOfNomineeJudges_judgeRanks.get(1));
			test.award_ewebPage
					.verifyHeaderForSelectedNominee("You have selected "
							+ maxPossibleNominees + " out of "
							+ maxPossibleNominees
							+ " possible nominations to rank.");
			test.award_ewebPage.provideComments(listOfFirstAndLastName,
					test.award_ewebPage.map().get("Comment Text"));
			test.award_ewebPage.clickOnViewProfileLink(listOfFirstAndLastName);
			test.award_ewebPage
					.clickOnProfilePdfLinkAndVerifyPdfContent(listOfFirstAndLastName);
			test.award_ewebPage
					.verifyAwardName_viewProfileLink(currentAwardName);
			test.award_ewebPage
					.verifyNominationDocuments_viewProfileLink(currentAwardName);
			test.award_ewebPage.clickOnCloseButton();
			test.award_ewebPage.clickOnRankNominees_Save("Rank Nominees");
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
			String status = test.award_ewebPage
					.verifyStatusAfterBallotSubmission(currentAwardName);
			test.award_ewebPage.submissionDateAfterBallotSubmission(
					currentAwardName, status);
			listsOfRanks.add(listOfNomineeJudges_judgeRanks.get(0));
		}
	}

	public void LaunchIWebApplicationToVerifyVotingCompletedForRound(int round,
			int votingRounds) {
		Reporter.log("===========Launch IWEB Application and verify the updated score message and status after voting completed===============\n");
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Awards");
		test.homePageIWEB.clickOnTab("Find Award");
		test.individualsPage.enterFieldValue("Award Year",
				DateUtil.getAnyDateForType("YYYY", 2, "year"));
		// test.individualsPage.enterFieldValue("Award Name","F. Albert Cotton Award in Synthetic Inorganic Chemistry");
		// test.individualsPage.enterFieldValue("Award Year","2018");
		test.individualsPage.clickGoButton();
		test.individualsPage
				.selectRandomGeneralAward_AwardNomination(currentAwardName
						.trim());
		test.awardsPageAction.expandDetailsMenu("award stages/rounds");
		test.awardsPageAction.goToRecordForRound(String.valueOf(votingRounds));
		test.awardsPageAction
				.verifyUpdateScoreMessageOnClickingUpdateScore(test.homePageIWEB
						.map().get(
								"Round" + votingRounds
										+ " Update Score Success Message?"));
		test.awardsPageAction
				.verifyClosedStatusOnUpdatingScore(test.homePageIWEB.map().get(
						"Round" + votingRounds + " Closed Status?"));
		test.awardsPageAction
				.expandDetailsMenu("acs award stage - entries in this stage");
		test.awardsPageAction
				.expandDetailsMenu("acs entries not in this stage");
		test.awardsPageAction.verifyNomineeWinnerStatus(votingRounds);

		test.awardsPageAction.clickOnAwardsName_RoundName(currentAwardName);
		test.awardsPageAction.expandDetailsMenu("award stages/rounds");
		startEndDate = test.awardsPageAction
				.editStartAndEnddateForRoundExceptOne(round > 1 ? ++votingRounds
						: -1);
	}

	@Test(dependsOnMethods = "Step02_TC02_Verify_Nominees_And_Set_Start_End_Dates_For_Round1_IWEB_Application")
	public void Step03_TC03_Provide_Ranks_In_Awards_Voting_Application_And_Verify_Voting_Completed_In_IWEB_Application() {
		int votingRounds = Integer.parseInt(test.homePageIWEB.map()
				.get("Winner in rounds").replace("Round", ""));
		int count = 1;
		for (int round = votingRounds; round > 0; round--) {
			Reporter.log("======================voting round Number :============= "
					+ round);
			VerifyNumberOfJudgesToAdd_GetJudgeDetails(String.valueOf(count));
			LaunchAwardsVotingApplicationAndProvideRanksToNomieesForVoting(
					round, count);
			LaunchIWebApplicationToVerifyVotingCompletedForRound(round, count);
			count++;
		}
	}

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IWEB");
		app_url_Awards = getYamlValue("app_url_Awards");
		test.launchApplication(app_url_IWEB);

	}
}
