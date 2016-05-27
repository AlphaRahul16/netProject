package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.DateUtil;

public class ACS_Awards_EWEB_PageActions extends ASCSocietyGenericPage {
	WebDriver driver;
	static String pagename = "ACS_Awards_Eweb";

	int timeOut, hiddenFieldTimeOut;

	List<Integer> ranks = new ArrayList<Integer>();
	Map<Integer, String> nomineeRanks = new HashMap<Integer, String>();

	List<Integer> uniqueRandom = new ArrayList<Integer>();
	Map<String, String> judgesRanks = new HashMap<String, String>();

	Map<Integer, Map> listOfNomineeJudges_judgeRanks = new HashMap<>();

	public ACS_Awards_EWEB_PageActions(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

	public void enterCredentials_LastNameMemberNumber_ACSID(
			String credentialType, String nameOfJudge,
			Map<String, List<String>> memberDetail) {
		logMessage("login credential:" + nameOfJudge.split(" ")[0]);
		logMessage("contact id:" + memberDetail.get(nameOfJudge).get(0));
		logMessage("web login::" + memberDetail.get(nameOfJudge).get(1));
		checkCredentialType(credentialType);
		if (credentialType.equalsIgnoreCase("lastNameMemberNumber")) {
			enterCredential("credential1", nameOfJudge.split(" ")[0],
					credentialType);
			enterCredential("credential2",
					memberDetail.get(nameOfJudge).get(0), credentialType);
		} else if (credentialType.equalsIgnoreCase("ACSID")) {
			enterCredential("credential1",
					memberDetail.get(nameOfJudge).get(1), credentialType);
			// enterCredential("credential1", "beers418", credentialType);
			enterCredential("credential2", "password", credentialType);
		}
		clickOnLoginButton();
	}

	public void enterCredentials(String credentialType) {

		element("rad_ACSID").click();
		wait.waitForPageToLoadCompletely();
		if (credentialType.equalsIgnoreCase("ACSID")) {
			// enterCredential("credential1",
			// memberDetail.get(nameOfJudge).get(1), credentialType);
			enterCredential("credential1", "davidmitchell", credentialType);
			enterCredential("credential2", "password", credentialType);
		} else
			logMessage("Cannot login");
		clickOnLoginButton();
	}

	public void enterCredential(String credential1_2, String credentialValue,
			String credentialType) {
		isElementDisplayed("inp_" + credential1_2);
		element("inp_" + credential1_2).clear();
		element("inp_" + credential1_2).sendKeys(credentialValue);
		;
		logMessage("Step : " + credentialValue + " is entered in "
				+ credentialType + "\n");
	}

	public void checkCredentialType(String typeName) {
		isElementDisplayed("rad_" + typeName);
		element("rad_" + typeName).click();
		logMessage("Step : check credential type " + typeName + "\n");
	}

	public void clickOnLoginButton() {
		isElementDisplayed("btn_login");
		element("btn_login").click();
		logMessage("Step : click on login button\n");
	}

	public void verifyLoginInAwardApplicationSuccessfully(String judgeName) {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("hd_welcome");
		logMessage("Last name " + judgeName.split(" ")[0]);
		logMessage("Heading" + element("hd_welcome").getText());

		Assert.assertTrue(
				element("hd_welcome").getText().contains(
						judgeName.split(" ")[0]),
				"Heading is not contains last name");
		logMessage("First name " + judgeName.split(" ")[1]);
		Assert.assertTrue(
				element("hd_welcome").getText().contains(
						judgeName.split(" ")[1]),
				"Heading is not contains first name");
		logMessage("ASSERT PASSED : judge name " + judgeName
				+ " login successfully\n");
	}

	public String verifyStatus(List<String> rescusedJudges, String nameOfJudge) {
		isElementDisplayed("txt_status");

		try {
			if (rescusedJudges != null || (rescusedJudges.size() >= 0)) {
				for (String rescusedJudge : rescusedJudges) {
					if (rescusedJudge.equalsIgnoreCase(nameOfJudge)) {
						Assert.assertTrue(
								element("txt_status")
										.getText()
										.trim()
										.equalsIgnoreCase(
												"Status - Ballot submitted"),
								"actual status of rescused judge is "
										+ element("txt_status").getText()
										+ " and expected is Status - Ballot submitted\n");
						logMessage("AASERT PASSED : Status Status - Ballot submitted of rescused judge "
								+ nameOfJudge + " is verified\n");
						return "Status - Ballot submitted";
					}
				}
			}
		} catch (NullPointerException NPE) {
			Assert.assertTrue(
					element("txt_status").getText().trim()
							.equalsIgnoreCase("Status - Not Yet Started"),
					"actual status of rescused judge is "
							+ element("txt_status").getText()
							+ " and expected is Status - Not Yet Started\n");
			logMessage("AASERT PASSED : Status Status - Not Yet Started of rescused judge "
					+ nameOfJudge + " is verified\n");
			return "Status - Not Yet Started";
		}
		return null;
	}

	public void verifyAwardName(String awardname) {
		isElementDisplayed("txt_awardName", awardname);
		logMessage("ASSERT PASSED : award name " + awardname + " is verified\n");
	}

	public void verifyNumberOfDays(String dateFormate, String endDate) {
		long numberOfDays = DateUtil.numberOfDaysBetweenTwoDays(dateFormate,
				DateUtil.getCurrentdateInStringWithGivenFormate(dateFormate),
				endDate);
		isElementDisplayed("txt_numberOfDaysRemaining",
				String.valueOf(numberOfDays));
		logMessage("ASSERT PASSED : number of days " + numberOfDays
				+ " is verified\n");
	}

	public void verifyNumberOfNominees(int numberOfNominees) {

		isElementDisplayed("txt_totalNominations",
				String.valueOf(numberOfNominees));
		logMessage("ASSERT PASSED : number of nominees " + numberOfNominees
				+ " is verified\n");
	}

	public void verifySubmitBallotDate(String date, String awardName) {
		isElementDisplayed("txt_submitBallotDate", awardName);
		Assert.assertTrue(
				element("txt_submitBallotDate", awardName).getText().contains(
						date),
				"ASSERT FAILED : actual: "
						+ element("txt_submitBallotDate", awardName).getText()
						+ " expected: " + date);
		logMessage("ASSERT PASSED : submit ballot date is verified as " + date
				+ "\n");
	}

	public void clickOnFiveYearNomineeMemoLink(String awardName) {

		wait.waitForPageToLoadCompletely();
		wait.hardWait(3);

		clickUsingXpathInJavaScriptExecutor(element("lnk_fiveYearNomineeMemo",
				awardName));
		logMessage("Step : click on five year nominee memo link\n");

	}

	public void clickOnViewNominationMaterialButton(String awardName) {
		isElementDisplayed("inp_viewNominationMaterial", awardName);
		element("inp_viewNominationMaterial", awardName).click();
		logMessage("Step : click on view nomination material button\n");
	}

	public void verifyCurrentTab(String tabName) {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("tab_currentTab");
		Assert.assertTrue(
				element("tab_currentTab").getText().trim().endsWith(tabName),
				"ASSERT FAILED : actual current tab is "
						+ element("tab_currentTab").getText()
						+ " and expected current tab is " + tabName + "\n");
		logMessage("ASSERT PASSED : current tab " + tabName + " is verified \n");
	}

	public void unselectAllNominees() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		try {
			wait.hardWait(4);
			wait.waitForPageToLoadCompletely();
			wait.resetImplicitTimeout(5);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("list_selectedNomineesPrepopulated");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			for (WebElement ele : elements("list_selectedNomineesPrepopulated")) {
				isElementDisplayed("list_selectedNomineesPrepopulated");
				ele.click();
				wait.hardWait(2);
			}
			logMessage("Step : Unselect all selected nominees\n");

		} catch (Exception e) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("Step : Nominees are not selected\n");
		}
	}

	public List<List<String>> selectRandomNominees(
			int numberOfNomineesToSelect, int round, List<String> nameOfJudges,
			Map<String, String> nominee_WithRankOne) {
		int j = 0;
		List<List<String>> listOfFirstAndLastName = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<String> selectedNomineeFirstNameList = new ArrayList();
		@SuppressWarnings("unchecked")
		List<String> selectedNomineeLastNameList = new ArrayList();
		System.out.println("Previous judge rank"
				+ nominee_WithRankOne.get(nameOfJudges.get(0)));
		try {
			if (nominee_WithRankOne != null
					&& !nominee_WithRankOne.get(nameOfJudges.get(0)).isEmpty()
					&& round == 1) {

				for (WebElement nominee_FirstName : elements("list_unSelectNomineesFirstName")) {
					logMessage("nominees unselected"
							+ nominee_FirstName.getText());
					if (nominee_WithRankOne.get(nameOfJudges.get(0)).toString()
							.contains(nominee_FirstName.getText())) {
						System.out.println(nominee_FirstName.getText()
								+ "matches"
								+ nominee_WithRankOne.get(nameOfJudges.get(0))
										.toString());
						elements("list_nominees").get(j).click();
						wait.hardWait(3);
						numberOfNomineesToSelect -= 1;
						break;
					}
					j++;
				}
			}
		} catch (Exception e) {
			logMessage("[INFO] : No previous nominee get rank 1 and nominee picking randomly\n");
		}

		for (int i = 0; i < numberOfNomineesToSelect; i++) {
			Random rand = new Random();
			isElementDisplayed("list_nominees");
			int sizeOfNominees = elements("list_nominees").size();
			int min = 0, max = sizeOfNominees - 1;

			int randomNumber = rand.nextInt((max - min) + 1) + min;

			elements("list_nominees").get(randomNumber).click();
			wait.hardWait(2);
		}

		for (WebElement element : elements("list_selectedNomineesFirstName")) {
			String selectedNomineeFirstName = element.getText();
			selectedNomineeFirstNameList.add(selectedNomineeFirstName);
		}

		for (WebElement element : elements("list_selectedNomineesLastName")) {
			String selectedNomineeLastName = element.getText();
			selectedNomineeLastNameList.add(selectedNomineeLastName);
		}

		int i = 0;
		for (String selectedNomineeFirstName : selectedNomineeFirstNameList) {
			logMessage("[first name] : " + selectedNomineeFirstName
					+ " ==== [last name] : "
					+ selectedNomineeLastNameList.get(i));
			i++;
		}

		listOfFirstAndLastName.add(selectedNomineeFirstNameList);
		listOfFirstAndLastName.add(selectedNomineeLastNameList);
		return listOfFirstAndLastName;
	}

	public void verifyHeaderForUnselectedNominee(String headerName) {
		isElementDisplayed("txt_unselectedNomineeHeader");
		Assert.assertTrue(element("txt_unselectedNomineeHeader").getText()
				.equalsIgnoreCase(headerName),
				"ASSERT FAILED : header name is " + headerName
						+ " is not verified for unselected nominees\n");
		logMessage("ASSERT PASSED : header name " + headerName
				+ " is verified for unselected nominees\n");
	}

	public void verifyHeaderForSelectedNominee(String headerName) {
		isElementDisplayed("txt_selectedNomineeHeader");
		Assert.assertTrue(element("txt_selectedNomineeHeader").getText()
				.equalsIgnoreCase(headerName),
				"ASSERT FAILED : header name is " + headerName
						+ " is not verified for selected nominees\n");
		logMessage("ASSERT PASSED : header name " + headerName
				+ " is verified for selected nominees\n");
	}

	public void clickOnViewProfileLink(List<List<String>> nomineeFirstNames) {
		int max = nomineeFirstNames.size();
		int min = 0;
		Random rand = new Random();
		int randomNumber = rand.nextInt((max - min) + 1) + min;
		isElementDisplayed("lnk_viewProfile",
				nomineeFirstNames.get(1).get(randomNumber));
		element("lnk_viewProfile", nomineeFirstNames.get(1).get(randomNumber))
				.click();
		logMessage("Step : view profile link is clicked for "
				+ nomineeFirstNames.get(1).get(randomNumber));
		// wait.waitForElementToDisappear(element("img_viewProfileLoader"));
		// extractAndCompareTextFromPdfFile("award_history", awardName, 1,
		// "downloads");
		// logMessage("Step : award history pdf verified \n");

	}

	public void clickOnProfilePdfLinkAndVerifyPdfContent(
			List<List<String>> nomineeFirstNames) {
		int max = nomineeFirstNames.size();
		int min = 0;
		Random rand = new Random();
		int randomNumber = rand.nextInt((max - min) + 1) + min;
		isElementDisplayed("list_img_pdfProfileDownload", nomineeFirstNames
				.get(1).get(randomNumber));
		waitForLoaderToDisappear();
		clickUsingXpathInJavaScriptExecutor(element(
				"list_img_pdfProfileDownload",
				nomineeFirstNames.get(1).get(randomNumber)));

		logMessage("Step : Profile pdf link is clicked for "
				+ nomineeFirstNames.get(1).get(randomNumber) + " user \n");
		// extractAndCompareTextFromPdfFile("AwardNomination"
		// , nomineeFirstNames.get(1).get(randomNumber), 1, "downloads");
		// logMessage("Step : award history pdf verified for "+nomineeFirstNames.get(1).get(randomNumber)+" \n");

	}

	public void enterComments(String comment) {
		isElementDisplayed("txtArea_commnetStickyNotes");
		element("txtArea_commnetStickyNotes").sendKeys(comment);
		logMessage("Step : comment " + comment + " is entered in sticky notes");
	}

	public void waitForLoaderToDisappear() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		try {
			wait.hardWait(4);
			wait.waitForPageToLoadCompletely();
			wait.resetImplicitTimeout(5);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("img_profileLinkLoader");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (Exception exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}
	}

	public void saveStickyNotes() {
		isElementDisplayed("img_closeButtonStickyNotes");
		element("img_closeButtonStickyNotes").click();
		logMessage("Step : save button is clicked in sticky notes\n");
	}

	public void provideComments(List<List<String>> nomineeFirstNames,
			String comment) {
		clickOnCommentLink(nomineeFirstNames);
		enterComments(comment);
		saveStickyNotes();
	}

	public void clickOnCommentLink(List<List<String>> nomineeFirstNames) {
		int max = nomineeFirstNames.size();
		int min = 0;
		Random rand = new Random();
		int randomNumber = rand.nextInt((max - min) + 1) + min;
		isElementDisplayed("lnk_Comment",
				nomineeFirstNames.get(1).get(randomNumber));
		element("lnk_Comment", nomineeFirstNames.get(1).get(randomNumber))
				.click();
		logMessage("Step : view profile link is clicked for "
				+ nomineeFirstNames.get(1).get(randomNumber));
		// wait.waitForElementToDisappear(element("img_viewProfileLoader"));

	}

	public void verifyAwardName_viewProfileLink(String awardName) {
		switchToFrame("TB_iframeContent");
		isElementDisplayed("txt_viewProfileAwardName", awardName);
		Assert.assertTrue(
				element("txt_viewProfileAwardName", awardName).getText()
						.endsWith(awardName),
				"ASSERT FAILED : actual award name is "
						+ element("txt_viewProfileAwardName", awardName)
								.getText() + " and expected is " + awardName
						+ "\n");
		logMessage("ASSERT PASSED : " + awardName
				+ " is verified in view profile link \n");
		switchToDefaultContent();
	}

	public void verifyNominationDocuments_viewProfileLink(String awardName) {
		switchToFrame("TB_iframeContent");
		isElementDisplayed("list_nominationsDocuments", awardName);
		Assert.assertTrue(elements("list_nominationsDocuments", awardName)
				.size() > 0,
				"ASSERT FAILED : Nomination documents are not present in view profile link\n");
		logMessage("ASSERT PASSED :Nomination documents are present in view profile link\n");
		switchToDefaultContent();
	}

	public int getNumberOfPossibleNominees(String awardName) {
		isElementDisplayed("txt_numberOfPossibleNominees", awardName);
		int maxPossibleNominees = Integer.parseInt(element(
				"txt_numberOfPossibleNominees", awardName).getText().split(
				" of ")[1].split(" ")[0]);

		logMessage("Step : Maximum possible nominees is " + maxPossibleNominees
				+ " \n");
		return maxPossibleNominees;
	}

	public void clickOnCloseButton() {
		switchToFrame("TB_iframeContent");
		isElementDisplayed("btn_close");
		element("btn_close").click();
		logMessage("Step : close button is clicked \n");
		switchToDefaultContent();
	}

	public void clickOnRankNominees_Save(String buttonName) {
		isElementDisplayed("btn_rankNominees_save");
		for (WebElement ele : elements("btn_rankNominees_save")) {
			logMessage("save btn text _________________________"
					+ ele.getAttribute("value"));
			if (ele.getAttribute("value").contains(buttonName)) {
				clickUsingXpathInJavaScriptExecutor(ele);
				logMessage("Step :  " + buttonName + " button is clicked \n");
			}
		}
	}

	public void enterRankForNomineeInRound_1(int maxPossibleNominees,
			int roundNumber, Map<String, String> judgesRanks,
			List<String> judges, int judgeNumber,
			List<List<String>> FirstnameLastname) {

		int k = 0, j = 0;
		boolean flag = false;
		ranks = generateRandomNumber(maxPossibleNominees, roundNumber);

		for (j = 0; j < maxPossibleNominees; j++) {
			Select dropdown_rank1 = new Select(element("drpdwn_rank",
					String.valueOf(1)));
			dropdown_rank1.selectByIndex(0);
			wait.hardWait(2);

		}

		logMessage("[INFO] : cleared pre populated ranks \n");

		for (j = 0; j < maxPossibleNominees; j++) {
			if (!judgesRanks.isEmpty() || judgesRanks == null) {

				k = 0;
				while (k < judgeNumber) {
					logMessage("Nominee name who had rank 1 by previous judge is ==== "
							+ judgesRanks.get(judges.get(k)) + "\n");
					logMessage("Current Nominee name to compare to provide the rank ====== "
							+ elements("txt_rankNomineeName").get(j).getText()
							+ "\n");

					if (elements("txt_rankNomineeName").get(j).getText()
							.contains(judgesRanks.get(judges.get(k)))) {
						Select dropdown_rank1 = new Select(element(
								"drpdwn_rank", String.valueOf(j + 1)));
						dropdown_rank1.selectByVisibleText(String.valueOf(1));
						wait.hardWait(5);
						flag = true;
						logMessage("\n Previous Nominee name "
								+ elements("txt_rankNomineeName").get(j)
										.getText()
								+ " matches with current Nominee name to provide the rank 1 \n");

						break;
					}
					k++;
				}

			} else {
				Select dropdown_rank1 = new Select(element("drpdwn_rank",
						String.valueOf(j + 1)));
				dropdown_rank1.selectByVisibleText(String.valueOf(1));
				wait.hardWait(1);

				flag = true;
				logMessage("\n Select Moninee name "
						+ elements("txt_rankNomineeName").get(0).getText()
						+ " for first time\n");
				break;
			}
			if (flag) {

				break;
			}
		}

		for (int i = 0; i < maxPossibleNominees - 1; i++) {
			Select dropdown_rank1 = new Select(element("drpdwn_rank",
					String.valueOf(i + 2)));
			logMessage("\n Provide ranks to nominee for the remaining nominee"
					+ elements("txt_rankNomineeName").get(i + 1).getText());
			dropdown_rank1.selectByVisibleText(String.valueOf(ranks.get(i)));
			wait.hardWait(5);
			logMessage("select : " + String.valueOf(ranks.get(i)));
		}
	}

	public void enterRankForNomineeForMultipleRounds(int maxPossibleNominees,
			List<String> judges, int judgeNumber,
			List<List<String>> FirstnameLastname, int roundNumber,
			Map<String, String> judgesRanks) {
		uniqueRandom = generateRandomNumber(maxPossibleNominees, roundNumber);

		for (int j = 0; j < uniqueRandom.size(); j++) {
			int flag = 0, k = 0;
			if (Integer.parseInt(uniqueRandom.get(j).toString()) == 1) {

				if (!judgesRanks.isEmpty()) {
					while (k < judgeNumber) {
						logMessage("Nominee name who had rank 1 by previous judge is ==== "
								+ judgesRanks.get(judges.get(k)) + "\n");
						logMessage("Current Nominee name to compare to provide the rank ====== "
								+ elements("txt_rankNomineeName").get(j)
										.getText() + "\n");

						if (judgesRanks.get(judges.get(k)).contains(
								elements("txt_rankNomineeName").get(j)
										.getText())) {
							flag = 1;
							break;
						}
						k++;
					}
				}
				if (flag == 1) {
					if (j + 1 >= uniqueRandom.size()) {

						enterRankForNomineeForMultipleRounds(
								maxPossibleNominees, judges, judgeNumber,
								FirstnameLastname, roundNumber, judgesRanks);
					}
					uniqueRandom.add(j, uniqueRandom.get(j + 1));
					uniqueRandom.remove(j + 2);
				}
			}

			Select dropdown_rank1 = new Select(element("drpdwn_rank",
					String.valueOf(j + 1)));
			dropdown_rank1.selectByVisibleText(String.valueOf(uniqueRandom
					.get(j)));

			logMessage("select : " + String.valueOf(uniqueRandom.get(j)) + "\n");
		}
	}

	public Map<Integer, Map> enterRankForNominee_rank1ForFirstNominee(
			int maxPossibleNominees, List<String> judges, int judgeNumber,
			List<List<String>> FirstnameLastname, int roundNumber,
			Map<String, String> judgesRanks) {
		logMessage("\n round number ===== " + roundNumber);
		logMessage("\n judges number ===== " + judgeNumber + 1);
		logMessage("\n current judge name ===== " + judges.get(judgeNumber));

		switch (roundNumber) {
		case 1:
			enterRankForNomineeInRound_1(maxPossibleNominees, roundNumber,
					judgesRanks, judges, judgeNumber, FirstnameLastname);
			break;
		case 2:
			enterRankForNomineeForMultipleRounds(maxPossibleNominees, judges,
					judgeNumber, FirstnameLastname, roundNumber, judgesRanks);
			break;
		case 3:
			enterRankForNomineeForMultipleRounds(maxPossibleNominees, judges,
					judgeNumber, FirstnameLastname, roundNumber, judgesRanks);
			break;
		}

		nomineeRanks = enterNomineeRankAndData(maxPossibleNominees);
		logMessage("\n registrant with rank 1 " + nomineeRanks.get(1));
		judgesRanks.put(judges.get(judgeNumber), nomineeRanks.get(1));
		clickOnConfirmBallotButton();
		listOfNomineeJudges_judgeRanks.put(0, nomineeRanks);
		listOfNomineeJudges_judgeRanks.put(1, judgesRanks);

		return listOfNomineeJudges_judgeRanks;
	}

	public void clickOnConfirmBallotButton() {
		isElementDisplayed("btn_confirmBallot");
		element("btn_confirmBallot").click();
		logMessage("Info: Confirm Ballot Button is clicked\n");
	}

	public List<Integer> generateRandomNumber(int maxPossibleNominees,
			int roundNumber) {
		int max = maxPossibleNominees + 1, min = 0, num; // 11
		switch (roundNumber) {
		case 1:
			min = 2;
			break;
		case 2:
		case 3:
			min = 1;
			break;

		}
		Random random = new Random();
		List<Integer> list = new ArrayList<Integer>();
		while (list.size() < max - min && min != 0) { // 10
			num = random.nextInt((max - min)) + min;
			if (list.contains(num))
				continue;
			else {
				System.out.println(num);
				list.add(num);
			}
		}
		return list;
	}

	public List<Integer> generateRandomNumberExceptOne(int maxPossibleNominees) {
		int max = maxPossibleNominees + 1, min = 2, num; // 11
		Random random = new Random();
		List<Integer> list = new ArrayList<Integer>();
		while (list.size() < maxPossibleNominees) { // 10
			num = random.nextInt((max - min)) + min;
			if (list.contains(num))
				continue;
			else
				list.add(num);
		}
		return list;
	}

	public Map<Integer, String> enterNomineeRankAndData(int maxPossibleNominees) {
		String name1 = "", name2 = "";
		Map<Integer, String> nomineeName = new HashMap<Integer, String>();
		for (int i = 1; i <= maxPossibleNominees; i++) {
			name1 = element("txt_nomineeName", String.valueOf(i)).getText();
			name2 = element("drpdwn_rank", String.valueOf(i)).getText();
			name1 = name1.replace(name2, "");
			nomineeName.put(i, name1.replace("\n", ""));

		}
		Iterator<Entry<Integer, String>> it = nomineeName.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			logMessage(pair.getKey() + " = " + pair.getValue());
		}
		return nomineeName;
	}

	public Map<Integer, String> verifyNomineeRankAndName(
			Map<Integer, String> expectedNomineeData, int maxPossibleNominees) {
		Map<Integer, String> actualNomineeData = new HashMap<Integer, String>();
		for (int i = 1; i <= maxPossibleNominees; i++) { // 10
			actualNomineeData.put(Integer.parseInt(element(
					"txt_confirmNomineeRank", String.valueOf(i)).getText()),
					element("txt_confirmNomineeName", String.valueOf(i))
							.getText());
		}

		Iterator<Entry<Integer, String>> it1 = expectedNomineeData.entrySet()
				.iterator();
		while (it1.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry pair = (Map.Entry) it1.next();
			logMessage(pair.getKey() + " = " + pair.getValue()+"\n");
		}
		Iterator<Entry<Integer, String>> it = actualNomineeData.entrySet()
				.iterator();
		while (it.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry pair = (Map.Entry) it.next();
			logMessage(pair.getKey() + " = " + pair.getValue()+"\n");
		}
		Assert.assertTrue(expectedNomineeData.equals(actualNomineeData),
				"Assertion Failed: Nominees are not having assigned ranks");
		logMessage("Assertion Passed: Nominees are having same assigned ranks");

		return actualNomineeData;

	}

	public void verifyConfirmBallotPage() {
		isElementDisplayed("txt_confirmBallotPage");
		Assert.assertTrue(
				element("tab_currentTab").getText().contains("Confirm Ballot"),
				"Assertion Failed: User is not on Confirm Ballot Page");
		logMessage("Assertion Passed: User is on Confirm Ballot Page\n");
	}

	public void clickOnSubmit_EditBallot(String submit_edit) {
		isElementDisplayed("btn_submit_editBallot", submit_edit);
		element("btn_submit_editBallot", submit_edit).click();
		logMessage("Step : " + submit_edit + " button is clicked\n");
	}

	public void verifyBallotConfirmationPage() {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("txt_ballotConfirmation");
		Assert.assertEquals("There are 0 awards remaining for you to judge.",
				element("txt_confirmationMessage").getText(),
				"Assertion Failed: User is not on Ballot Confirmation Page\n");
		logMessage("Assertion Passed: User is on Ballot Confirmation Page\n");
	}

	public void clickOnReturnToAwardDashboard() {
		isElementDisplayed("btn_returnToYourAwardDashboard");
		element("btn_returnToYourAwardDashboard").click();
		logMessage("Info: Clicked on Returns To Your Award Dashboard\n");
	}

	public String verifyStatusAfterBallotSubmission(String awardName) {
		isElementDisplayed("txt_status", awardName);
		Assert.assertTrue(element("txt_status", awardName).getText().trim()
				.equalsIgnoreCase("Status - Ballot Submitted")
				|| element("txt_status", awardName).getText().trim()
						.equalsIgnoreCase("Status - Voting Closed"),
				"actual status of judge is "
						+ element("txt_status", awardName).getText()
						+ " and expected is Status - Ballot submitted\n");
		logMessage("Assertion Passed: Actual status of Judge is Status-Ballot Submitted\n");
		return element("txt_status", awardName).getText().trim();

	}

	public void submissionDateAfterBallotSubmission(String awardName,
			String status) {
		isElementDisplayed("txt_ballotSubmissionDate", awardName);
		if (status.contains("Status - Voting Closed")) {
			logMessage("ASERT PASSED : Voting has been closed at this time.");
		} else {
			Assert.assertTrue(
					element("txt_ballotSubmissionDate", awardName)
							.getText()
							.trim()
							.contains(
									"You submitted your ballot on:\n"
											+ DateUtil
													.getCurrentdateInStringWithGivenFormate("MMM dd, YYYY")),
					"Assertion Failed: actual submission date is "
							+ element("txt_ballotSubmissionDate", awardName)
									.getText().trim()
							+ " and Expected is You submitted your ballot on:\n"
							+ DateUtil
									.getCurrentdateInStringWithGivenFormate("MMM dd, YYYY")
							+ "\n");
			logMessage("ASSERT PASSED : "
					+ element("txt_ballotSubmissionDate", awardName).getText()
							.trim());
		}

	}

	// public void

}
