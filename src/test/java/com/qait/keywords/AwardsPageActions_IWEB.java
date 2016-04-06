package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.mozilla.javascript.ast.Label;
import org.openqa.selenium.WebDriver;
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

	public AwardsPageActions_IWEB(WebDriver driver) {
		super(driver, "AwardsPageIWEB");
		this.driver = driver;
	}

	public void ClearStartDateAndEndDate_AllRounds() {
		ClearStartDateEmpty_Round("1");
		ClearStartDateEmpty_Round("2");
		ClearStartDateEmpty_Round("3");

		ClearEndDateEmpty_Round("1");
		ClearEndDateEmpty_Round("2");
		ClearEndDateEmpty_Round("3");
	}

	public void ClearStartDateEmpty_Round(String roundNumber) {
		isElementDisplayed("txt_startDateRounds", roundNumber);
		element("txt_startDateRounds", roundNumber).click();
		if (element("txt_startDateRounds", roundNumber).getText().contains("/")) {
			clickOnEditRecordButton(roundNumber);
			switchToFrame("iframe1");
			editStartEndDate("start", "");
			clickOnSaveButton();
			switchToDefaultContent();
			logMessage("Step : Start date for round " + roundNumber
					+ " is cleared\n");
		} else {
			logMessage("Step : Start date is already empty \n");
		}
	}

	public void ClearEndDateEmpty_Round(String roundNumber) {
		isElementDisplayed("txt_endDateRounds", roundNumber);
		if (element("txt_endDateRounds", roundNumber).getText().contains("/")) {
			clickOnEditRecordButton(roundNumber);
			switchToFrame("iframe1");
			editStartEndDate("end", "");
			clickOnSaveButton();
			switchToDefaultContent();

			logMessage("Step : End date for round " + roundNumber
					+ " is cleared\n");
		} else {
			logMessage("Step : End date is already empty \n");
		}
	}

	public void editStartAndEndDate_Round(String roundNumber) {
		clickOnEditRecordButton(roundNumber);
		switchToFrame("iframe1");
		editStartEndDate("start",
				DateUtil.getAnyDateForType("M/dd/YYYY", -5, "date"));
		editStartEndDate("end",
				DateUtil.getAnyDateForType("M/dd/YYYY", 2, "month"));

	}

	public void clickOnSaveButton() {
		isElementDisplayed("btn_saveButton");
		element("btn_saveButton").click();
		logMessage("Step : click on save button\n");
		waitForSpinner();
	}

	public void clickOnEditRecordButton(String roundNumber) {
		isElementDisplayed("link_editRecord", roundNumber);
		element("link_editRecord", roundNumber).click();
		logMessage("Step : Edit record button is clicked in link_editRecord\n");
		// isElementDisplayed("txt_editRoundPage");
	}

	public void editStartEndDate(String start_endDate, String value) {
		isElementDisplayed("inp_editStartEndDate", start_endDate);
		element("inp_editStartEndDate", start_endDate).sendKeys(value);
		logMessage("Step : Edit " + start_endDate + " with " + value + " \n");
	}

	public Set<String> getACSNomineesInEntrants() {
		isElementDisplayed("list_nominees");
		for (WebElement nominees : elements("list_nominees")) {
			nomineesNames.add(nominees.getText());
		}
		for (String nomineee : nomineesNames) {
			System.out.println(nomineee);
		}
		if (nomineesNames.size() < 0) {
			Assert.fail("ASSERT FAILED : " + nomineesNames.size()
					+ " Nominees are exists in award selected\n");
		}
		return nomineesNames;
	}

	public Set<String> allACSNomineesInEntrants() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		try {
			wait.waitForPageToLoadCompletely();
			wait.resetImplicitTimeout(5);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("list_paging");
			int sizeOfPage = elements("list_paging").size();
			System.out.println("page size:" + sizeOfPage);
			int page = 0;

			getACSNomineesInEntrants();
			do {
				System.out.println("page number :" + page);
				page++;
				isElementDisplayed("link_paging", String.valueOf(page + 1));
				element("link_paging", String.valueOf(page + 1)).click();
				waitForSpinner();
				logMessage("Step : page number " + page + 1 + " is clicked\n");
				System.out.println("page no.:" + page);
				getACSNomineesInEntrants();
			} while (page < sizeOfPage);
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (Exception exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("Step : Paging is not present for awards selecting\n");
		}
		for (String nominee : nomineesNames) {
			System.out.println(nominee);
		}
		return nomineesNames;
	}

	public void waitForSpinner() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		try {
			handleAlert();
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("img_spinner");
			logMessage("STEP : wait for spinner to be disappeared \n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (Exception Exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (AssertionError Exp) {
			logMessage("STEP : spinner is not present \n");
		}
	}

	public void verifyOrAddRoundsPresents() {
		Set<String> roundNumberSet = new HashSet<>();
		isElementDisplayed("list_rounds");
		int numberOfRounds = elements("list_rounds").size();
		if (numberOfRounds < 3) {
			for (WebElement ele : elements("list_rounds")) {
				roundNumberSet.add(ele.getText().trim());
			}
			List<String> roundNumberList = new ArrayList<>(roundNumberSet);
			for (int i = 1; i <= roundNumberList.size(); i++) {
				if (!roundNumberList.get(i - 1).equalsIgnoreCase("Round " + i)) {
					addRound(String.valueOf(i));
				}
			}
		}
	}

	public void addRound(String roundNumber) {
		clickOnAddRoundButton("stages/rounds");
		isElementDisplayed("lbl_addRound");
		switchToFrame("iframe1");
		enterDetailsToAddRound_Judge("stage/round description", "Round "
				+ roundNumber);
		enterDetailsToAddRound_Judge("stage/round code", "Round " + roundNumber);
		enterDetailsToAddRound_Judge("order", roundNumber);
		clickOnSaveButton();
		switchToDefaultContent();
	}

	public void enterDetailsToAddRound_Judge(String detailName,
			String detailValue) {
		isElementDisplayed("inp_editRound_Judge", detailName);
		element("inp_editRound_Judge", detailName).sendKeys(detailValue);
		logMessage("Step : " + detailValue + " is enetered for " + detailName);
	}

	public void clickOnAddRoundButton(String tabName) {
		isElementDisplayed("btn_addRounds_judges", tabName);
		element("btn_addRounds_judges",tabName).click();
		logMessage("Step : add button for " + tabName + " is clicked\n");
	}

	public void verifyNumberOfJudgesAndAdd() {
		Set<String> judgeNumberSet = new HashSet<>();
		isElementDisplayed("list_awardJudge");
		int numberOfJudges = elements("list_awardJudge").size();
		if (numberOfJudges < 5) {
			for (WebElement ele : elements("list_awardJudge")) {
				judgeNumberSet.add(ele.getText().trim());
			}
			List<String> judgeNumberList = new ArrayList<>(judgeNumberSet);
			for (int i = 1; i <= 5 - judgeNumberList.size(); i++) {
				addJudges(i);
			}
		}
	}

	public void addJudges(int roundNumber) {
		clickOnAddRoundButton("award judges");
		switchToFrame("iframe1");
		selectProvidedTextFromDropDown(element("list_selectRoundNumber"),
				"Round " + roundNumber);
		clickOnSearchButtonOnEditRecord();
		selectJudgeName();
		clickOnSaveButton();
		switchToDefaultContent();
	}

	public void clickOnSearchButtonOnEditRecord() {
		isElementDisplayed("btn_search");
		element("btn_search").click();
		logMessage("Step : search button is clicked \n");
	}

	public void selectJudgeName() {
		isElementDisplayed("img_goToJudge");
		element("img_goToJudge").click();
		logMessage("Step : click on judge profile\n");
	}
}