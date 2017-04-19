package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.DateUtil;

public class AwardsPageActions_IWEB extends ASCSocietyGenericPage {

	WebDriver driver;
	String pagename = "AwardsPageIWEB";
	boolean flag;
	Set<String> nomineesNames = new HashSet<>();
	int timeOut, hiddenFieldTimeOut;
	String newJudgeName;
	List<String> listOfJudgesForRescused = new ArrayList<String>();
	static int j = 0;
	Map<String, Integer> nomineesWithRankOne = new HashMap<>();
	List<String> listOfAcsNomineesNotInStage = new ArrayList<String>();

	public AwardsPageActions_IWEB(WebDriver driver) {
		super(driver, "AwardsPageIWEB");
		this.driver = driver;
	}

	public void ClearStartDateAndEndDate_AllRounds(int roundNumberInDataSheet) {

		ClearStartDateEmpty_Round("1", roundNumberInDataSheet);

		ClearStartDateEmpty_Round("2", roundNumberInDataSheet);

		ClearStartDateEmpty_Round("3", roundNumberInDataSheet);

		ClearEndDateEmpty_Round("1");
		ClearEndDateEmpty_Round("2");
		ClearEndDateEmpty_Round("3");

	}

	public void selectWinnerType(String winnerType) {

		isElementDisplayed("list_resetWinnerStatus");
		selectProvidedTextFromDropDown(element("list_resetWinnerStatus"), winnerType);
	}

	public void ClearStartDateEmpty_Round(String roundNumber, int roundNumberInDataSheet) {
		System.out.println("ClearStartDateEmpty_Round" + roundNumber);
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(5);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("txt_startDateRounds", roundNumber);
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			element("txt_startDateRounds", roundNumber).click();
			if (element("txt_startDateRounds", roundNumber).getText().contains("/")) {
				clickOnEditRecordButton(roundNumber);
				switchToFrame("iframe1");
				editStartEndDate("start", "");
				if (roundNumberInDataSheet == 1 && roundNumber.equalsIgnoreCase("1")) {
					selectWinnerType("winner");
				} else if (roundNumberInDataSheet == 2 && roundNumber.equalsIgnoreCase("1")) {
					selectWinnerType("Advanced to Round 2");
				} else if (roundNumberInDataSheet == 2 && roundNumber.equalsIgnoreCase("2")) {
					selectWinnerType("winner");
				} else if (roundNumberInDataSheet == 3 && roundNumber.equalsIgnoreCase("1")) {
					selectWinnerType("Advanced to Round 2");
				} else if (roundNumberInDataSheet == 3 && roundNumber.equalsIgnoreCase("2")) {
					selectWinnerType("Advanced to Round 3");
				} else if (roundNumberInDataSheet == 3 && roundNumber.equalsIgnoreCase("4")) {
					selectWinnerType("winner");
				}
				clickOnSaveButton();
				switchToDefaultContent();
				logMessage("STEP : Start date for round " + roundNumber + " is cleared\n");
			} else {
				logMessage("STEP : Start date is already empty \n");
			}
		} catch (Exception e) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}

	}

	public void ClearEndDateEmpty_Round(String roundNumber) {
		System.out.println("ClearEndDateEmpty_Round" + roundNumber);
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(5);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);

			isElementDisplayed("txt_endDateRounds", roundNumber);
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			if (element("txt_endDateRounds", roundNumber).getText().contains("/")) {
				clickOnEditRecordButton(roundNumber);
				switchToFrame("iframe1");
				editStartEndDate("end", "");
				clickOnSaveButton();
				switchToDefaultContent();
				logMessage("STEP : End date for round " + roundNumber + " is cleared\n");
			} else {
				logMessage("STEP : End date is already empty \n");
			}
		} catch (Exception e) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}
	}

	public String[] editStartAndEndDate_Round(int roundNumber) {
		if (roundNumber != -1) {
			System.out.println("roundNumber " + roundNumber);
			clickOnEditRecordButton(String.valueOf(roundNumber));
			switchToFrame("iframe1");
			String startDate = DateUtil.getAnyDateForType("MM/dd/YYYY", -8, "date");
			String endDate = DateUtil.getAnyDateForType("MM/dd/YYYY", 2, "month");
			editStartEndDate("start", startDate);
			wait.waitForPageToLoadCompletely();
			editStartEndDate("end", endDate);
			String[] startEndDate = { startDate, endDate };
			System.out.println("startDate :" + startDate);
			System.out.println("endDate :" + endDate);
			// resetWinnerType();
			return startEndDate;
		} else {
			System.out.println("round number is null");
			return null;
		}
	}

	public String[] editStartAndEnddateForRoundExceptOne(int roundNumber) {
		if (roundNumber != -1) {
			String[] arr = editStartAndEndDate_Round(roundNumber);
			clickOnSaveButton();
			switchToDefaultContent();
			expandDetailsMenuIfAlreadyExpanded("award judges");
			return arr;
		} else {
			return null;
		}

	}

	public void uncheckClosedCheckbox_VotingClosed(String awardName) {
		int roundNumber;
		for (roundNumber = 1; roundNumber <= 3; roundNumber++) {
			if (checkIfElementIsThere("img_awardClosed", String.valueOf(roundNumber))) {
				goToRecordForRound(String.valueOf(roundNumber));
				boolean closedStatus = isClosedStatusOnAwardsStageProfile_Yes("Yes");
				System.out.println("closed status : " + closedStatus);
				if (closedStatus) {
					logMessage("INFO : Awards Voting Is Already Closed For Award : " + awardName + " \n");
					clickOnEditButtonInAwardsStageProfilePage();
					switchToFrame("iframe1");
					uncheckClosedCheckboxButton();
					clickOnSaveButton();
					switchToDefaultContent();
					Assert.assertFalse(isClosedStatusOnAwardsStageProfile_Yes("No"),
							"Is closed status is actual Yes and expected No\n");
				} else
					logMessage("[INFO] : Awards Voting is not closed\n");
				clickOnAwardsName_RoundName(awardName);
			} else
				logMessage("STEP : Round " + roundNumber + " status is not closed\n");
		}
	}

	public void verifyRecordClosedStatus(int roundNumber) {
		checkIfElementIsThere("img_awardClosed", String.valueOf(roundNumber));
	}

	public void clickOnEditButtonForAwards() {
		isElementDisplayed("btn_editAwards");
		element("btn_editAwards").click();
		logMessage("STEP : Edit awards button is clicked \n");
	}

	public void enterStartEndDateInEditAwards(String labelName, String inputDateValue) {
		isElementDisplayed("inp_editDateInEditAwards", labelName);
		element("inp_editDateInEditAwards", labelName).clear();
		element("inp_editDateInEditAwards", labelName).sendKeys(inputDateValue);
		logMessage("STEP : Enter " + inputDateValue + " for " + labelName + " in edit awards page \n");
	}

	public void editAwardStartAndEndDate() {
		clickOnEditButtonForAwards();
		switchToFrame("iframe1");
		enterStartEndDateInEditAwards("application start date",
				DateUtil.getAddYearWithLessOnedayInStringWithGivenFormate("MM/dd/YYYY", "-1", "EST5EDT"));
		enterStartEndDateInEditAwards("application end date",
				DateUtil.getAddYearWithLessOnedayInStringWithGivenFormate("MM/dd/YYYY", "2", "EST5EDT"));
		enterStartEndDateInEditAwards("remove from web",
				DateUtil.getAddYearWithLessOnedayInStringWithGivenFormate("MM/dd/YYYY", "2", "EST5EDT"));

		clickOnSaveButton();
		switchToDefaultContent();
	}

	public boolean isClosedStatusOnAwardsStageProfile_Yes(String closedStatus) {
		isElementDisplayed("txt_withLabelOnAwardStageProfile", "closed?");
		String actualClosedStatus = element("txt_withLabelOnAwardStageProfile", "closed?").getText().trim();
		Boolean flag = actualClosedStatus.equalsIgnoreCase("Yes");
		logMessage("Step Closed status is " + actualClosedStatus + "\n");
		return flag;
	}

	public void clickOnEditButtonInAwardsStageProfilePage() {
		isElementDisplayed("btn_editAward");
		element("btn_editAward").click();
		logMessage("STEP : click on Batch name on invoice profile page\n");
	}

	public void uncheckClosedCheckboxButton() {
		isElementDisplayed("chk_closedCheckBox");
		if (element("chk_closedCheckBox").isSelected())
			element("chk_closedCheckBox").click();
		else {
			logMessage("STEP : closed check box is already unchecked\n");
		}

		logMessage("STEP : closed check box is unchecked\n");
	}

	public void clickOnSaveButton() {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		isElementDisplayed("btn_saveButton");
		// clickUsingXpathInJavaScriptExecutor(element("btn_saveButton"));
		element("btn_saveButton").click();
		logMessage("STEP : click on save button\n");
		waitForSpinner();
	}

	public void clickOnEditRecordButton(String roundNumber) {
		System.out.println("clickOnEditRecordButton");
		isElementDisplayed("link_editRecord", roundNumber);
		element("link_editRecord", roundNumber).click();
		logMessage("STEP : Edit record button is clicked in link_editRecord\n");
		// isElementDisplayed("txt_editRoundPage");
	}

	public void editStartEndDate(String start_endDate, String value) {
		System.out.println("editStartEndDate" + start_endDate);
		isElementDisplayed("inp_editStartEndDate", start_endDate);
		element("inp_editStartEndDate", start_endDate).clear();
		element("inp_editStartEndDate", start_endDate).sendKeys(value);
		logMessage("STEP : Edit " + start_endDate + " date with " + value + " \n");
	}

	public Set<String> getACSNomineesInEntrants() {
		isElementDisplayed("list_nominees");
		for (WebElement nominees : elements("list_nominees")) {
			nomineesNames.add(nominees.getText());
		}

		return nomineesNames;
	}

	public Set<String> allACSNomineesInEntrants() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {

			wait.resetImplicitTimeout(5);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("list_paging");
			int sizeOfPage = elements("list_paging").size();
			System.out.println("page size:" + sizeOfPage);
			int page = 0;
			int pageCount = page + 1;
			getACSNomineesInEntrants();
			do {
				System.out.println("page number :" + page);
				page++;
				isElementDisplayed("link_paging", String.valueOf(page + 1));
				// element("link_paging", String.valueOf(page + 1)).click();
				clickUsingXpathInJavaScriptExecutor(element("link_paging", String.valueOf(page + 1)));
				waitForSpinner();
				logMessage("STEP : page number " + pageCount + " is clicked\n");
				System.out.println("page no.:" + page);
				getACSNomineesInEntrants();
			} while (pageCount < sizeOfPage);
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (Exception exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			getACSNomineesInEntrants();
			logMessage("STEP : Paging is not present for awards selecting\n");
		}
		for (String nominee : nomineesNames) {
			System.out.println(nominee);
		}
		System.out.println("nominees size :" + nomineesNames.size());
		int sizeOfNominee = nomineesNames.size();
		if (sizeOfNominee < 10) {
			int numberOfNomineeToAdd = 10 - sizeOfNominee;
			for (int i = 1; i <= numberOfNomineeToAdd; i++) {
				// addNominees("acs nominee/ entry");
			}

			// Assert.fail("ASSERT FAILED : Nominees Size is not greater than
			// equal to 10\n");
		}
		return nomineesNames;
	}

	public void editWinnerNomineesFromJudges() {

		wait.hardWait(4);
		if (checkIfElementIsThere("btn_editChild", "acs award winner")) {
			int sizeOfNominees = elements("btn_editChild", "acs award winner").size();
			System.out.println("sizeOfNominees: " + sizeOfNominees);
			if (sizeOfNominees > 0) {
				isElementDisplayed("btn_editChild", "acs award winner");
				int sizeOfEditButton = elements("btn_editChild", "acs award winner").size();
				System.out.println("-------size of edit button value:" + sizeOfEditButton);
				for (int i = 1; i <= sizeOfEditButton; i++) {
					// for (WebElement ele :
					// elements("btn_editChild","acs award winner")) {
					// ele.click();
					System.out.println("-------in for loop");
					if (verifyValidField(j)) {
						System.out.println("-----in if loop");
						elements("btn_editChild", "acs award winner").get(j).click();
						wait.hardWait(1);
						wait.waitForPageToLoadCompletely();
						switchToFrame("iframe1");
						selectProvidedTextFromDropDown(element("drpdwn_selectWinnerCategory"), "Please select");
						clickOnSaveButton();
						switchToDefaultContent();
						logMessage("STEP : Edit winner nominees from judges\n");
					} else {
						j++;
					}

				}
			}
		}

	}

	public void editPreviousWinnerNominees() {

		wait.hardWait(4);
		if (checkIfElementIsThere("btn_editChild", "acs award winner")) {
			int sizeOfNominees = elements("btn_editChild", "acs award winner").size();
			System.out.println("sizeOfNominees: " + sizeOfNominees);
			if (sizeOfNominees > 0) {
				isElementDisplayed("btn_editChild", "acs award winner");
				int sizeOfEditButton = elements("btn_editChild", "acs award winner").size();
				System.out.println("-------size of edit button value:" + sizeOfEditButton);
				for (int i = 1; i <= sizeOfEditButton; i++) {
					// for (WebElement ele :
					// elements("btn_editChild","acs award winner")) {
					// ele.click();
					System.out.println("-------in for loop");
					System.out.println("-----in if loop");
					elements("btn_editChild", "acs award winner").get(j).click();
					wait.hardWait(1);
					wait.waitForPageToLoadCompletely();
					switchToFrame("iframe1");
					selectProvidedTextFromDropDown(element("drpdwn_selectWinnerCategory"), "Please select");
					clickOnSaveButton();
					switchToDefaultContent();
					logMessage("STEP : Edit winner nominees from judges\n");
				}
			}
		}

	}

	public boolean verifyValidField(int j) {
		// element("img_valid","acs award
		// winner",String.valueOf(j)).isDisplayed();
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("img_valid", "acs award winner", String.valueOf(j + 1));
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			if (element("img_valid", "acs award winner", String.valueOf(j + 1)).isDisplayed()) {
				logMessage("Valid image is displayed \n");
				return true;
			} else {
				logMessage("Invalid image is displayed \n");
				return false;
			}
		} catch (Exception Exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			return false;
		}

	}

	public void waitForSpinner() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			handleAlert();
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("img_spinner");
			logMessage("STEP : Wait for spinner to be disappeared \n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (Exception Exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (AssertionError Exp) {
			logMessage("STEP : Spinner is not present \n");
		}
	}

	public void verifyOrAddRoundsPresents() {
		Map<String, String> roundNumberList = new HashMap<>();
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		roundNumberList.put("Round 1", null);
		roundNumberList.put("Round 2", null);
		roundNumberList.put("Round 3", null);
		try {
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("list_rounds");
			int numberOfRounds = elements("list_rounds").size();
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			System.out.println("no of round" + numberOfRounds);
			if (numberOfRounds < 3) {
				for (WebElement ele : elements("list_rounds")) {
					roundNumberList.put(ele.getText().trim(), ele.getText().trim());
				}
				for (int i = 1; i <= 3; i++) {

					if (roundNumberList.containsKey("Round " + i) != roundNumberList.containsValue("Round " + i)) {
						addRound(String.valueOf(i));
					}

					// if (!roundNumberList.get("Round "+i).equalsIgnoreCase(
					// "Round " + i)) {
					//
					// }
				}
			}
		} catch (NullPointerException npe) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("STEP : No any rounds are not presents\n");
		}
	}

	public void addRound(String roundNumber) {
		clickOnAddRoundButton("award stages/rounds");

		switchToFrame("iframe1");
		enterDetailsToAddRound_Judge("stage/round description", "Round " + roundNumber);
		enterDetailsToAddRound_Judge("stage/round code", "Round " + roundNumber);
		enterDetailsToAddRound_Judge("order", roundNumber);
		clickOnSaveButton();
		switchToDefaultContent();
	}

	public void clickOnRandomPage() {

		if (checkIfElementIsThere("list_pages")) {

			System.out.println("size of links:" + elements("lnk_pages").size());
			int max = elements("lnk_pages").size() - 1, min = 2;
			Random rand = new Random();
			int randomNumber = rand.nextInt((max - min) + 1) + min;

			String randomNumberInString = String.valueOf(randomNumber);
			isElementDisplayed("lnk_pages", randomNumberInString);

			clickUsingXpathInJavaScriptExecutor(element("lnk_pages", randomNumberInString));
			logMessage("STEP : Page at the position of " + randomNumberInString + " is clicked in lnk_pages\n");
		} else {
			logMessage("[Info] : Page links are not present\n");
		}

	}

	public void enterDetailsToAddRound_Judge(String detailName, String detailValue) {
		isElementDisplayed("inp_editRound_Judge", detailName);
		element("inp_editRound_Judge", detailName).sendKeys(detailValue);
		logMessage("STEP : " + detailValue + " is enetered for " + detailName);
	}

	public void clickOnAddRoundButton(String tabName) {
		wait.hardWait(4);
		isElementDisplayed("btn_addRounds_judges", tabName);
		clickUsingXpathInJavaScriptExecutor(element("btn_addRounds_judges", tabName));
		// element("btn_addRounds_judges", tabName).click();
		logMessage("STEP : Add button for " + tabName + " is clicked\n");
	}

	public void addNominees(String tabName) {
		MembershipPageActions_IWEB obj = new MembershipPageActions_IWEB(driver);
		clickOnPlusIcon(tabName);
		switchToFrame("iframe1");
		clickOnHeading("Nominator details");
		clickOnSearchIcon(2);
		obj.clickOnRandomPage();
		obj.clickOnAnyRandomNominee();

		clickOnHeading("Nominator details");
		wait.waitForPageToLoadCompletely();
		wait.hardWait(5);
		isElementDisplayed("inp_lookUpSelect");
		// if (getSearchedText() == null) {
		// clickOnSearchIcon(2);
		// obj.clickOnRandomPage();
		// obj.clickOnAnyRandomNominee();
		// clickOnHeading("Nominator details");
		// }
		clickOnSearchIcon(1);
		obj.clickOnRandomPage();
		obj.clickOnAnyRandomNominee();
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		clickOnHeading("Nominator details");
		wait.waitForPageToLoadCompletely();
		wait.hardWait(5);
		isElementDisplayed("inp_lookUpSelect");
		// if (getSearchedText() == null) {
		// clickOnSearchIcon(1);
		// obj.clickOnRandomPage();
		// obj.clickOnAnyRandomNominee();
		// clickOnHeading("Nominator details");
		// }
		enterEntryDate("entry date");
		clickOnSaveButton();
		switchToDefaultContent();
	}

	public void clickOnHeading(String heading) {
		isElementDisplayed("hd_awards", heading);
		element("hd_awards", heading).click();
		logMessage("STEP : Click on heading " + heading + " \n");
	}

	public void clickOnPlusIcon(String tabName) {
		hardWaitForIEBrowser(5);
		isElementDisplayed("btn_plusIconNominee", tabName);
		wait.waitForElementToBeClickable(element("btn_plusIconNominee", tabName));
		clickUsingXpathInJavaScriptExecutor(element("btn_plusIconNominee", tabName));

		logMessage("STEP : Clicked on plus icon under " + tabName);
	}

	public void enterEntryDate(String field) {
		String prevoiusDate = DateUtil.getAnyDateForType("MM/dd/YYYY", -2, "year");
		isElementDisplayed("inp_entryDate", field);
		element("inp_entryDate", field).sendKeys(prevoiusDate);
		logMessage("STEP : Entry date entered as " + prevoiusDate);
	}

	public void clickOnSearchIcon(int index) {
		waitForSpinner();
		wait.hardWait(3);
		isElementDisplayed("btn_searchNominee", String.valueOf(index));
		// clickUsingXpathInJavaScriptExecutor(element("btn_searchNominee",
		// String.valueOf(index)));
		// hoverClick(element("btn_searchNominee", String.valueOf(index)));
		element("btn_searchNominee", String.valueOf(index)).click();
		logMessage("STEP : Clicked on Search icon");
		wait.waitForPageToLoadCompletely();
	}

	public boolean verifyJudgeAlreadyExist() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("lbl_alreadyExistJudgeMsz");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			return true;
		} catch (Exception exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			return false;
		}

	}

	public void clickOnEditJudgesRoundButton(WebElement editJudgesRound) {
		editJudgesRound.isDisplayed();
		editJudgesRound.click();
		logMessage("STEP : Edit button is clicked\n");

	}

	public void deleteJudges() {
		if (checkIfElementIsThere("btnList_yellowPointerExpand")) {
			isElementDisplayed("btnList_yellowPointerExpand");
			int sizeOfPointer = elements("btnList_yellowPointerExpand").size();
			System.out.println("sizeOfPointer :" + sizeOfPointer);
			for (int i = sizeOfPointer; i >= 1; i--) {
				System.out.println("i :" + i);
				wait.waitForPageToLoadCompletely();
				wait.hardWait(5);
				elements("btnList_yellowPointerExpand").get(i - 1).click();
				// clickUsingXpathInJavaScriptExecutor(elements("btnList_yellowPointerExpand").get(i
				// - 1));
				logMessage("STEP : Expand yellow pointer\n");
				wait.hardWait(2);

				isElementDisplayed("btn_editJudges");

				int sizeOfEditJudges = elements("btn_editJudges").size();
				System.out.println("Size of edit judges: " + sizeOfEditJudges);
				try {
					for (int j = 0; j < sizeOfEditJudges; j++) {
						// for (WebElement ele : elements("btn_editJudges")) {
						wait.hardWait(7);
						// ele.click();
						isElementDisplayed("btn_editJudges");
						elements("btn_editJudges").get(0).click();
						switchToFrame("iframe1");
						logMessage("STEP : Click to edit judge");
						clickOnDeleteJudgeButton();
						waitForAlertToAppear();
						switchToDefaultContent();
						/*
						 * clickUsingXpathInJavaScriptExecutor(elements(
						 * "btnList_yellowPointerExpand").get(i - 2));
						 * logMessage( "Step : expand yellow pointer\n");
						 */
					}
				} catch (StaleElementReferenceException e) {
					elements("btn_editJudges").get(0).click();
					switchToFrame("iframe1");
					logMessage("STEP : Click to edit judge");
					clickOnDeleteJudgeButton();
					waitForAlertToAppear();
					switchToDefaultContent();
				}
				isElementDisplayed("btnList_yellowPointerCollapse");
				elements("btnList_yellowPointerCollapse").get(0).click();
				logMessage("STEP : Collapse yellow pointer\n");
			}

			// isElementDisplayed("btn_deleteAwards");
			// int numberOfJudges = elements("list_awardJudge",
			// Integer.toString(1))
			// .size();
			// System.out.println("Step : number of judges " + numberOfJudges);
			// for (WebElement element : elements("btn_deleteAwards")) {
			// element.click();
			// logMessage("Step : click to Delete judge");
			// waitForAlertToAppear();
			// }
		} else {
			logMessage("STEP : Yellow folder for nominees are not present\n");
		}

	}

	public void clickOnDeleteJudgeButton() {
		isElementDisplayed("btn_deleteJudge");
		element("btn_deleteJudge").click();
		logMessage("STEP : Click on delete judge button \n");
	}

	public void verifyNumberOfJudgesAndAdd(int roundNumber) {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
//		try {
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("list_awardJudge");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			int numberOfJudges = elements("list_awardJudge", Integer.toString(roundNumber)).size();
			System.out.println("number of judges " + numberOfJudges);

			if (numberOfJudges < 5) {
				int numberOfJudgesToAdd = 5 - numberOfJudges;
				System.out.println(numberOfJudgesToAdd);
				for (int i = 1; i <= numberOfJudgesToAdd; i++) {
					addJudges(roundNumber);
				}

			}
//		} catch (Exception e) {
//			wait.resetImplicitTimeout(timeOut);
//			wait.resetExplicitTimeout(timeOut);
//			logMessage("[INFO] : Judges are not present in the list of judges \n");
//			int numberOfJudges = 0;
//			System.out.println("number of judges" + numberOfJudges);
//
//			if (numberOfJudges < 5) {
//				int numberOfJudgesToAdd = 5 - numberOfJudges;
//				System.out.println(numberOfJudgesToAdd);
//				for (int i = 1; i <= numberOfJudgesToAdd; i++) {
//					wait.hardWait(2);
//					addJudges(roundNumber);
//				}
//			}
//		}
	}

	public void addJudgesForRound(int roundNumber) {
		for (int i = 1; i <= 5; i++) {
			addJudges(roundNumber);
		}
	}

	public void addJudges(int roundNumber) {
		System.out.println("=======================add judge========================");
		wait.hardWait(4);
		clickOnAddRoundButton("award judges");
		switchToFrame("iframe1");
		selectProvidedTextFromDropDown(element("list_selectRoundNumber"), "Round " + roundNumber);
		logMessage("STEP : Round " + roundNumber + " is selected in list_selectRoundNumber\n");
		waitForSpinner();
		clearJudgeNameOnAdd();
		clickOnStageAwardLabel();
		clickOnSearchButtonOnEditRecord();
		wait.hardWait(2);
		switchToDefaultContent();
		switchToFrame("iframe1");
		clickOnRandomPage();
		selectJudgeName();
		getSearchedName();

		System.out.println("judge name selected :-" + element("inp_judgeName").getText().trim());
		clickOnSaveButton();
		if (verifyJudgeAlreadyExist()) {
			System.out.println("========Already exists ==============");
			clickOnCancelButton();
			switchToDefaultContent();
			addJudges(roundNumber);
		}
		switchToDefaultContent();
	}

	public String getSearchedText() {
		isElementDisplayed("inp_lookUpSelect");
		String searchedName = element("inp_lookUpSelect").getAttribute("value");
		logMessage("STEP : Searched name is " + searchedName + "\n");
		return searchedName;
	}

	public void clickOnStageAwardLabel() {
		isElementDisplayed("lbl_stageAwardInEdit");
		element("lbl_stageAwardInEdit").click();
		logMessage("STEP : Name label is clicked on edit judge page \n");
	}

	public void clickOnCancelButton() {
		isElementDisplayed("btn_cancel");
		element("btn_cancel").click();
		logMessage("STEP : Cancel button is clicked\n");
	}

	public void clearJudgeNameOnAdd() {
		isElementDisplayed("inp_judgeName");
		element("inp_judgeName").clear();
		logMessage("STEP : Judge name is cleared\n");
	}

	public String getSearchedName() {
		isElementDisplayed("inp_judgeName");
		String searchedName = element("inp_judgeName").getAttribute("value");
		logMessage("STEP : Searched name is " + searchedName + "\n");
		return searchedName;
	}

	public void addRounds() {
		clickOnAddRoundButton("award stages/rounds");
		switchToFrame("iframe1");
		selectJudgeName();
		clickOnSaveButton();
		switchToDefaultContent();
	}

	public void clickOnSearchButtonOnEditRecord() {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(8);
		isElementDisplayed("btn_lookup");
//		isElementDisplayed("btn_srchJudges");
		// wait.waitForElementToBeClickable(element("btn_search"));
		clickUsingXpathInJavaScriptExecutor(element("btn_lookup"));
		// element("btn_search").click();
		logMessage("STEP : Search button is clicked \n");
	}

	public void selectJudgeName() {
		int max = 12, min = 3;
		Random rand = new Random();
		clickOnRandomPage();
		int randomNumber = rand.nextInt((max - min) + 1) + min;
		String randomNumberInString = String.valueOf(randomNumber);
		isElementDisplayed("img_goToJudge", randomNumberInString);
		element("img_goToJudge", randomNumberInString).click();
		logMessage("STEP : Click on judge profile\n");
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
	}

	public void goToRecordForRound(String roundNumber) {
		isElementDisplayed("link_goToRecord", roundNumber);
		element("link_goToRecord", roundNumber).click();
		logMessage("STEP : Navigate to record for round\n");
	}

	public void verifyACSAwardStageEntriesInThisStageIsEmpty() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(10);
			wait.resetExplicitTimeout(10);
			Assert.assertTrue(elements("link_acsAwardEntries").get(0).isDisplayed(),
					"Step : ACS Award Stage Entries In This Stage is empty \n");
			logMessage("STEP : ACS Award Stage Entries In This Stage is not empty\n");
			clickOnReopenSubmissionButton();
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (Exception exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("STEP : ACS Award Stage Entries In This Stage is empty \n");

		}

		// navigateToBackPage();
	}

	public void clickOnReopenSubmissionButton() {
		isElementDisplayed("img_reOpenSubmission");
		clickUsingXpathInJavaScriptExecutor(element("img_reOpenSubmission"));
		logMessage("STEP : Re open submission button is clicked\n");
	}

	public List<String> getRecusedStatusForRounds(String roundNumber) {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("list_judgeName_rescused");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			for (WebElement ele : elements("list_judgeName_rescused")) {
				listOfJudgesForRescused.add(ele.getText().trim());
			}
			for (String s : listOfJudgesForRescused) {
				System.out.println("rescused judge:" + s);
			}
			return listOfJudgesForRescused;
		} catch (Exception exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);

			logMessage("STEP : List of judges that does not contain rescused status\n");
			return null;
		}
	}

	public List<String> getJudgesName(String roundNumber) {
		List<String> listOfJudgesName = new ArrayList<String>();
		isElementDisplayed("list_judgeName_Judges", roundNumber);
		for (WebElement ele : elements("list_judgeName_Judges", roundNumber)) {
			listOfJudgesName.add(ele.getText().trim());
		}
		for (String s : listOfJudgesName) {
			System.out.println("judge name : " + s);
		}
		return listOfJudgesName;
	}

	public String goToJudgeRecord(String judgeName) {
		// wait.waitForPageToLoadCompletely();
		wait.hardWait(3);
		wait.waitForPageToLoadCompletely();
		String[] name = judgeName.split(" ");

		int flag = 0, i = 1;
		for (; i <= elements("list_judgeNames_awardJudges").size(); i++) {

			int size = 0;
			flag = 0;
			while (size < name.length) {
				flag = 0;
				System.out.println("size in while : " + size + "\n");
				System.out.println(
						" current judge " + element("text_judgeNames_awardJudges", Integer.toString(i)).getText()
								+ " current judge splitted name " + name[size] + "\n");
				if (element("text_judgeNames_awardJudges", Integer.toString(i)).getText().contains(name[size])) {
					System.out
							.println("in if : " + element("text_judgeNames_awardJudges", Integer.toString(i)).getText()
									+ "== contains ==" + (name[size]));

					size++;
					System.out.println("size value : " + size);
					flag = 1;
				} else {
					break;
				}
			}
			if (flag == 1) {
				break;
			}
		}

		if (flag == 1) {
			System.out.println("index " + i);
			newJudgeName = element("text_judgeNames_awardJudges", Integer.toString(i)).getText().trim();
			clickUsingXpathInJavaScriptExecutor(element("list_judgesNames_goToRecord", Integer.toString(i)));

		}

		logMessage("STEP : Navigate to judge " + judgeName + " profile\n");
		return newJudgeName;

	}

	public void deleteNominees() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			wait.waitForPageToLoadCompletely();
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("list_deleteNominee");
			element("img_reOpenSubmission").click();
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			// for (WebElement ele : elements("list_deleteNominee")) {
			// ele.click();
			// handleAlert();
			// }
			logMessage("STEP : Nominees are deleted from pending scores tab\n");
		} catch (Exception exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("STEP : Nominees are not presents in pending scores tab\n");

		}
	}

	public Map<String, List<String>> getJudgeDetails(List<String> judgeNames, String roundNumber) {
		Map<String, List<String>> judgeDetailsMap = new HashMap<>();
		System.out.println("judges name size: " + judgeNames.size());
		String customerLname;
		for (String judgeName : judgeNames) {
			List<String> judgeCustomerID_Weblogin = new ArrayList<String>();
			String currentUrl = getCurrentURL();
			expandDetailsMenuIfAlreadyExpanded("award judge");
			String newJudgeName = goToJudgeRecord(judgeName);
			deleteNominees();

			expandDetailsMenuIfAlreadyExpanded("acs award judge score");

			verifyACSAwardStageEntriesInThisStageIsEmpty();
			wait.hardWait(2);
			clickOnJudgeNameToNavigateOnProfilePage(newJudgeName);
			waitForSpinner();

			MembershipPageActions_IWEB obj = new MembershipPageActions_IWEB(driver);
			obj.clickOnEditNameAndAddress();
			wait.hardWait(2);
			switchToFrame("iframe1");
			customerLname = obj.getNameFromEditNameAndAddressButton("lastName");
			clickOnCancelButton();
			handleAlert();
			switchToDefaultContent();
			judgeCustomerID_Weblogin.add(getCustomerID(judgeName));
			judgeCustomerID_Weblogin.add(customerLname);

			// judgeCustomerID_Weblogin.add(getWebLogin(judgeName));

			judgeDetailsMap.put(judgeName, judgeCustomerID_Weblogin);

			navigateToUrl(currentUrl);
			System.out.println("judge name : " + judgeName);
			System.out.println(judgeDetailsMap.get(judgeName).get(0));
			System.out.println(judgeDetailsMap.get(judgeName).get(1));
		}

		for (Map.Entry<String, List<String>> entry : judgeDetailsMap.entrySet()) {
			String key = entry.getKey();

			List<String> values = entry.getValue();
			logMessage("Key = " + key);

			logMessage(values.size() + "___________________");
			logMessage("Value1 = " + values.get(0) + "n");
			logMessage("Value2 = " + values.get(1) + "n");
		}
		return judgeDetailsMap;
	}

	public void clickOnAwardsName_RoundName(String awards_roundName) {
		isElementDisplayed("lnk_awardName_RoundName", awards_roundName);
		try {
			clickUsingXpathInJavaScriptExecutor(element("lnk_awardName_RoundName", awards_roundName));
		} catch (WebDriverException webExp) {
			if (webExp.getMessage().contains("Element is not clickable")) {
				element("lnk_awardName_RoundName", awards_roundName).click();
			}
		}
		logMessage("STEP : Click on awards name " + awards_roundName);
	}

	public void clickOnJudgeNameToNavigateOnProfilePage(String judgeName) {
		if (judgeName.contains("'")) {
			isElementDisplayed("lnk_judgeProfile", judgeName.split("'")[1]);
			clickUsingXpathInJavaScriptExecutor(element("lnk_judgeProfile", judgeName.split("'")[1]));
			logMessage("STEP : Click on judge name " + judgeName.split("'")[1] + " to navigate on profile page\n");
		} else {
			try {
				System.out.println("judgeName: " + judgeName);
				String[] name = judgeName.split(" ");
				if (name.length > 0) {
					judgeName = name[0] + " " + name[1];
				}
				isElementDisplayed("lnk_judgeProfile", judgeName);
				wait.hardWait(2);
				clickUsingXpathInJavaScriptExecutor(element("lnk_judgeProfile", judgeName));
				// element("lnk_judgeProfile", judgeName).click();
				logMessage("STEP : Click on judge name " + judgeName + " to navigate on profile page\n");
			} catch (StaleElementReferenceException stlRef) {
				System.out.println("judgeName: " + judgeName);
				isElementDisplayed("lnk_judgeProfile", judgeName);
				clickUsingXpathInJavaScriptExecutor(element("lnk_judgeProfile", judgeName));
				// element("lnk_judgeProfile", judgeName).click();
				logMessage("STEP : Click on judge name " + judgeName + " to navigate on profile page\n");
			}
		}
	}

	public String getCustomerID(String judgeName) {
		isElementDisplayed("txt_customerId");
		String customerId = element("txt_customerId").getText();

		logMessage("STEP : Customer ID for " + judgeName + " is " + customerId + "\n");
		return customerId;
	}

	public String getWebLogin(String judgeName) {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("txt_webLogin");
			String webLogin = element("txt_webLogin").getText();
			logMessage("STEP : Web Login ID for " + judgeName + " is " + webLogin + "\n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			return webLogin;
		} catch (Exception e) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("[INFO] : Web login is not present for judge " + judgeName + "\n");
			return null;
		}

	}

	public void getJudgeDetail() {
		clickOnEditJudgeButton();

	}

	public void clickOnEditJudgeButton() {
		isElementDisplayed("btn_editJudge");
		element("btn_editJudge").click();
		logMessage("STEP : Click on edit button on judge profile\n");
	}

	public void clickOnUpdateScoreLink() {
		isElementDisplayed("lnk_updateScore");
		element("lnk_updateScore").click();
		logMessage("STEP : Clicked on Update score link");
	}

	public void clickOnUpdateScoreButtonOnPopup() {
		switchToFrame("iframe1");
		isElementDisplayed("btn_updateScore");
		element("btn_updateScore").click();
		logMessage("STEP : Clicked on Update Score button");
		switchToDefaultContent();
	}

	public void verifyUpdateScoreMessageOnClickingUpdateScore(String updateScoreMessage) {
		clickOnUpdateScoreLink();
		clickOnUpdateScoreButtonOnPopup();
		switchToFrame("iframe1");
		isElementDisplayed("txt_updateScoreMessage");
		Assert.assertTrue(element("txt_updateScoreMessage").getText().equalsIgnoreCase(updateScoreMessage),
				"ASSERT FAILED : Actual upadte score message is " + element("txt_updateScoreMessage").getText()
						+ " and expected is " + updateScoreMessage);
		logMessage(
				"ASSERT PASSED : Message Process Completed Successfully is displayed on clicking update score link \n");
		switchToDefaultContent();
		clickOnCloseButton();
	}

	public void clickOnCloseButton() {
		switchToFrame("iframe1");
		isElementDisplayed("btn_updateScoreClose");
		element("btn_updateScoreClose").click();
		logMessage("STEP  : Close button is clicked in update score popup\n");
		switchToDefaultContent();
	}

	public void verifyClosedStatusOnUpdatingScore(String closedMessage) {
		isElementDisplayed("txt_closedStatus");
		Assert.assertTrue(element("txt_closedStatus").getText().equalsIgnoreCase(closedMessage),
				"ASSERT FAILED: Expected value is " + closedMessage + " but found "
						+ element("txt_closedStatus").getText());
		logMessage("ASSERT PASSED : Closed status is verified as " + closedMessage + "\n");
	}

	public void verifyNomineesWithRankOne(List<List<String>> nomineeNames) {
		for (WebElement nomineeName : elements("list_acsAwardProfilesNomineesWithOnes")) {
			int winners = 0;
			boolean flag = false;
			while (winners < nomineeNames.get(0).size()) {
				System.out.println("nominee in list " + nomineeName.getText() + "nominees stored first name"
						+ nomineeNames.get(0).get(winners) + "nominees stored first name"
						+ nomineeNames.get(1).get(winners) + "\n");
				if ((nomineeName.getText().contains(nomineeNames.get(0).get(winners)))
						&& (nomineeName.getText().contains(nomineeNames.get(1).get(winners)))) {
					flag = true;
					break;

				}

				logMessage("winner present");
				winners++;
			}
			Assert.assertTrue(flag, "Nominee name " + nomineeName.getText() + " not present in the ones category\n");
			logMessage("Nominee " + nomineeName.getText() + " present in the ones category\n");
		}

	}

	public void verifyNomineeWinnerStatus(int round) {
		int status = round;
		for (WebElement winnerStatus : elements("list_acsEntriesNotStage_ColWinnerStatus")) {
			if (winnerStatus.getText().trim().equalsIgnoreCase(map().get("Round" + status + " Winner Status?"))) {
				
				logMessage("ASSERT PASSED : Winner status is verified as"
						+ map().get("Round" + status + " Winner Status?") + "\n");
				flag = true;
				break;
			}
			logMessage("ASSERT FAILED : Error in Winner status. Actual" + winnerStatus.getText().trim()
					+ " and expected" + map().get("Round" + status + " Winner Status?") + "\n");

		}
		if (!flag) {
			logMessage("ASSERT FAILED : Error in Winner status.expected"
					+ map().get("Round" + status + " Winner Status?") + "\n");
		}

		for (WebElement winnerStatus : elements("list_acsEntriesNotStage_ColNominee",
				map().get("Round" + status + " Winner Status?"))) {
			listOfAcsNomineesNotInStage.add(winnerStatus.getText());
			logMessage("STEP : Acs nominee no in stage " + winnerStatus.getText());
		}
	}

	public void editStageRound(int votingRound) {
		System.out.println("=======================edit judge========================" + votingRound);
		String voting_Round = Integer.toString(votingRound);
		String round = Integer.toString(votingRound + 1);
		int size = elements("list_links_awardJudgesEditRound", voting_Round).size();
		for (int index = 0; index < size; index++) {
			try {
				elements("list_links_awardJudgesEditRound", voting_Round).get(0).click();
				// clickUsingXpathInJavaScriptExecutor(editButton);
				// clickOnEditJudgesRoundButton(editButton);
			} catch (StaleElementReferenceException ele) {
				logMessage("[INFO] : Stale Element Reference Exception is handled\n");
				// clickUsingXpathInJavaScriptExecutor(editButton);
				elements("list_links_awardJudgesEditRound", voting_Round).get(0).click();
			}
			switchToFrame("iframe1");
			selectProvidedTextFromDropDown(element("list_selectRoundNumber"), "Round " + round);
			logMessage("Step : Round " + round + " is selected in list_selectRoundNumber\n");
			clickOnSaveButton();
			switchToDefaultContent();
		}
	}
}