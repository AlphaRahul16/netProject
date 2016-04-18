package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.mozilla.javascript.ast.Label;
import org.openqa.selenium.NoSuchElementException;
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

	List<String> listOfJudgesForRescused = new ArrayList<String>();
	List<String> listOfJudgesName = new ArrayList<String>();

	Map<String, List<String>> judgeDetailsMap = new HashMap<>();

	public AwardsPageActions_IWEB(WebDriver driver) {
		super(driver, "AwardsPageIWEB");
		this.driver = driver;
	}

	public void ClearStartDateAndEndDate_AllRounds() {
		holdExecution(5000);
		ClearStartDateEmpty_Round("1");
		ClearStartDateEmpty_Round("2");
		ClearStartDateEmpty_Round("3");

		ClearEndDateEmpty_Round("1");
		ClearEndDateEmpty_Round("2");
		ClearEndDateEmpty_Round("3");
	}

	public void ClearStartDateEmpty_Round(String roundNumber) {
		System.out.println("ClearStartDateEmpty_Round" + roundNumber);
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
		System.out.println("ClearEndDateEmpty_Round" + roundNumber);
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

	public String[] editStartAndEndDate_Round(String roundNumber) {
		clickOnEditRecordButton(roundNumber);
		switchToFrame("iframe1");

		String startDate = DateUtil.getAnyDateForType("M/dd/YYYY", -8, "date");
		String endDate = DateUtil.getAnyDateForType("M/dd/YYYY", 2, "month");
		editStartEndDate("start", startDate);
		wait.waitForPageToLoadCompletely();
		editStartEndDate("end", endDate);
		String[] startEndDate = { startDate, endDate };
		return startEndDate;
	}

	public void clickOnSaveButton() {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("btn_saveButton");
		element("btn_saveButton").click();
		logMessage("Step : click on save button\n");
		waitForSpinner();
	}

	public void clickOnEditRecordButton(String roundNumber) {
		System.out.println("clickOnEditRecordButton");
		isElementDisplayed("link_editRecord", roundNumber);
		element("link_editRecord", roundNumber).click();
		logMessage("Step : Edit record button is clicked in link_editRecord\n");
		// isElementDisplayed("txt_editRoundPage");
	}

	public void editStartEndDate(String start_endDate, String value) {
		System.out.println("editStartEndDate" + start_endDate);
		isElementDisplayed("inp_editStartEndDate", start_endDate);
		element("inp_editStartEndDate", start_endDate).clear();
		element("inp_editStartEndDate", start_endDate).sendKeys(value);
		logMessage("Step : Edit " + start_endDate + " date with " + value
				+ " \n");
	}

	public Set<String> getACSNomineesInEntrants() {
		isElementDisplayed("list_nominees");
		for (WebElement nominees : elements("list_nominees")) {
			nomineesNames.add(nominees.getText());
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
				element("link_paging", String.valueOf(page + 1)).click();
				waitForSpinner();
				logMessage("Step : page number " + pageCount + " is clicked\n");
				System.out.println("page no.:" + page);
				getACSNomineesInEntrants();
			} while (pageCount < sizeOfPage);
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
		System.out.println("nominees size :" + nomineesNames.size());
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
		List<String> roundNumberList = new ArrayList<>();
		isElementDisplayed("list_rounds");
		int numberOfRounds = elements("list_rounds").size();
		System.out.println("no of round" + numberOfRounds);
		if (numberOfRounds < 3) {
			for (WebElement ele : elements("list_rounds")) {
				roundNumberList.add(ele.getText().trim());
			}

			for (int i = 1; i <= roundNumberList.size(); i++) {
				if (!roundNumberList.get(i - 1).equalsIgnoreCase("Round " + i)) {
					addRound(String.valueOf(i));
				}
			}
		}
	}

	public void addRound(String roundNumber) {
		clickOnAddRoundButton("award stages/rounds");
		isElementDisplayed("lbl_addRound");
		switchToFrame("iframe1");
		enterDetailsToAddRound_Judge("stage/round description", "Round "
				+ roundNumber);
		enterDetailsToAddRound_Judge("stage/round code", "Round " + roundNumber);
		enterDetailsToAddRound_Judge("order", roundNumber);
		clickOnSaveButton();
		switchToDefaultContent();
	}

	public void clickOnRandomPage() {
		try {
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("lnk_pages", "2");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			int max = 6, min = 2;
			Random rand = new Random();
			int randomNumber = rand.nextInt((max - min) + 1) + min;
			// int randomNumber = (int) Math.random();
			String randomNumberInString = String.valueOf(randomNumber);
			isElementDisplayed("lnk_pages", randomNumberInString);

			clickUsingXpathInJavaScriptExecutor(element("lnk_pages",
					randomNumberInString));
			logMessage("Step : page at the position of " + randomNumberInString
					+ " is clicked in lnk_pages\n");

		} catch (NoSuchElementException exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}
	}

	public void enterDetailsToAddRound_Judge(String detailName,
			String detailValue) {
		isElementDisplayed("inp_editRound_Judge", detailName);
		element("inp_editRound_Judge", detailName).sendKeys(detailValue);
		logMessage("Step : " + detailValue + " is enetered for " + detailName);
	}

	public void clickOnAddRoundButton(String tabName) {
		isElementDisplayed("btn_addRounds_judges", tabName);
		element("btn_addRounds_judges", tabName).click();
		logMessage("Step : add button for " + tabName + " is clicked\n");
	}

	public void verifyNumberOfJudgesAndAdd() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("list_awardJudge");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			int numberOfJudges = elements("list_awardJudge").size();
			System.out.println("nu of judges" + numberOfJudges);
			if (numberOfJudges < 5) {
				int numberOfJudgesToAdd = 5 - numberOfJudges;
				System.out.println(numberOfJudgesToAdd);
				for (int i = 1; i <= numberOfJudgesToAdd; i++) {
					addJudges(1);
				}
			}
		} catch (Exception e) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("INFO : Judges are not present in the list of judges \n");
			int numberOfJudges = 0;
			System.out.println("nu of judges" + numberOfJudges);
			if (numberOfJudges < 5) {
				int numberOfJudgesToAdd = 5 - numberOfJudges;
				System.out.println(numberOfJudgesToAdd);
				for (int i = 1; i <= numberOfJudgesToAdd; i++) {
					addJudges(1);
				}
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
		int max = 12, min = 3;
		Random rand = new Random();
		clickOnRandomPage();
		int randomNumber = rand.nextInt((max - min) + 1) + min;
		String randomNumberInString = String.valueOf(randomNumber);
		isElementDisplayed("img_goToJudge", randomNumberInString);
		element("img_goToJudge", randomNumberInString).click();
		logMessage("Step : click on judge profile\n");
	}

	public void goToRecordForRound(String roundNumber) {
		isElementDisplayed("link_goToRecord", roundNumber);
		element("link_goToRecord", roundNumber).click();
		logMessage("Step : navigate to record for round\n");
	}

	public List<String> getRecusedStatusForRounds(String roundNumber) {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
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
			logMessage("Step : list of judges that contains rescused\n");
			return null;
		}
	}

	public List<String> getJudgesName() {
		isElementDisplayed("list_judgeName_Judges");
		for (WebElement ele : elements("list_judgeName_Judges")) {
			listOfJudgesName.add(ele.getText().trim());
		}
		for (String s : listOfJudgesName) {
			System.out.println(s);
		}
		return listOfJudgesName;
	}

	public void goToJudgeRecord(String judgeName) {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("link_goToJudgeRecord", judgeName);
		clickUsingXpathInJavaScriptExecutor(element("link_goToJudgeRecord",
				judgeName));
		logMessage("Step : navigate to judge " + judgeName + " profile\n");

	}

	public Map<String, List<String>> getJudgeDetails(List<String> judgeNames,
			String roundNumber) {
		System.out.println("judges name size: " + judgeNames.size());
		for (String judgeName : judgeNames) {
			List<String> judgeCustomerID_Weblogin = new ArrayList<String>();
			String currentUrl = getCurrentURL();
			goToJudgeRecord(judgeName);
			clickOnJudgeNameToNavigateOnProfilePage(judgeName);
			waitForSpinner();
			judgeCustomerID_Weblogin.add(getCustomerID(judgeName));
			judgeCustomerID_Weblogin.add(getWebLogin(judgeName));

			judgeDetailsMap.put(judgeName, judgeCustomerID_Weblogin);

			navigateToUrl(currentUrl);
			System.out.println("judge name : " + judgeName);
			System.out.println(judgeDetailsMap.get(judgeName).get(0));
			System.out.println(judgeDetailsMap.get(judgeName).get(1));
		}

		for (Map.Entry<String, List<String>> entry : judgeDetailsMap.entrySet()) {
			String key = entry.getKey();
			List<String> values = entry.getValue();
			System.out.println("Key = " + key);
			System.out.println("akdfhbskdfd : "
					+ entry.getValue().get(0).toString());
			System.out.println(values.size() + "___________________");
			System.out.println("Value1 = " + values.get(0) + "n");
			System.out.println("Value2 = " + values.get(1) + "n");
		}

		return judgeDetailsMap;

	}

	public void clickOnAwardsName_RoundName(String awards_roundName) {
		isElementDisplayed("lnk_awardName_RoundName", awards_roundName);
		element("lnk_awardName_RoundName", awards_roundName).click();
		logMessage("Step : click on awards name " + awards_roundName);
	}

	public void clickOnJudgeNameToNavigateOnProfilePage(String judgeName) {
		isElementDisplayed("lnk_judgeProfile", judgeName);
		element("lnk_judgeProfile", judgeName).click();
		logMessage("Step : click on judge name " + judgeName
				+ " to navigate on profile page\n");
	}

	public String getCustomerID(String judgeName) {
		isElementDisplayed("txt_customerId");
		String customerId = element("txt_customerId").getText();

		logMessage("Step : Customer ID for " + judgeName + " is " + customerId
				+ "\n");
		return customerId;
	}

	public String getWebLogin(String judgeName) {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("txt_webLogin");
			String webLogin = element("txt_webLogin").getText();
			logMessage("Step : Web Login ID for " + judgeName + " is "
					+ webLogin + "\n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			return webLogin;
		} catch (Exception e) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("INFO : web login is not present for judge " + judgeName
					+ "\n");
			return null;
		}

	}

	public void getJudgeDetail() {
		clickOnEditJudgeButton();

	}

	public void clickOnEditJudgeButton() {
		isElementDisplayed("btn_editAward");
		element("btn_editJudge").click();
		logMessage("Step : click on edit button on judge profile\n");
	}

}