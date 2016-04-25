package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.DateUtil;

public class ACS_Awards_EWEB_PageActions extends ASCSocietyGenericPage {
	WebDriver driver;
	static String pagename = "ACS_Awards_EWEB";
	List<String> selectedNomineeFirstNameList = new ArrayList();
	List<String> selectedNomineeLastNameList = new ArrayList();
	int timeOut, hiddenFieldTimeOut;

	public ACS_Awards_EWEB_PageActions(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

	public void enterCredentials_LastNameMemberNumber_ACSID(
			String credentialType, String nameOfJudge,
			Map<String, List<String>> memberDetail) {
		System.out.println("login credential:" + nameOfJudge.split(" ")[0]);
		System.out
				.println("contact id:" + memberDetail.get(nameOfJudge).get(0));
		System.out
				.println("web login::" + memberDetail.get(nameOfJudge).get(1));
		checkCredentialType(credentialType);
		if (credentialType.equalsIgnoreCase("lastNameMemberNumber")) {
			enterCredential("credential1", nameOfJudge.split(" ")[0],
					credentialType);
			enterCredential("credential2",
					memberDetail.get(nameOfJudge).get(0), credentialType);
		} else if (credentialType.equalsIgnoreCase("ACSID")) {
			enterCredential("credential1",
					memberDetail.get(nameOfJudge).get(1), credentialType);
			enterCredential("credential1", "beers418", credentialType);
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
		Assert.assertTrue(
				element("hd_welcome").getText().contains(
						judgeName.split(" ")[0]),
				"Heading is not contains last name");
		Assert.assertTrue(
				element("hd_welcome").getText().contains(
						judgeName.split(" ")[1]),
				"Heading is not contains first name");
		logMessage("ASSERT PASSED : judge name " + judgeName
				+ " login successfully\n");
	}

	public void verifyStatus(List<String> rescusedJudges, String nameOfJudge) {
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
		}
	}

	public void verifyAwardName(String awardname) {
		isElementDisplayed("txt_awardName", awardname);
		logMessage("ASSERT PASSED : award name " + awardname + " is verified\n");
	}

	public void verifyNumberOfDays(String dateFormate,
			String endDate) {
		long numberOfDays = DateUtil.numberOfDaysBetweenTwoDays(dateFormate,
				DateUtil.getCurrentdateInStringWithGivenFormate(dateFormate), endDate);
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

	public void verifySubmitBallotDate(String date) {
		String dateInGivenFormat = DateUtil.convertStringToDate(date,
				"M/dd/YYYY").toString();
		isElementDisplayed("txt_submitBallotDate");
		Assert.assertTrue(element("txt_submitBallotDate").getText()
				.equalsIgnoreCase(dateInGivenFormat),
				"ASSERT FAILED : actual: "
						+ element("txt_submitBallotDate").getText()
						+ " expected: " + dateInGivenFormat);
	}

	public void clickOnFiveYearNomineeMemoLink(String awardName) {
		isElementDisplayed("lnk_fiveYearNomineeMemo", awardName);
		element("lnk_fiveYearNomineeMemo", awardName).click();
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
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("list_selectedNomineesPrepopulated");
			for (WebElement ele : elements("list_selectedNomineesPrepopulated")) {
				isElementDisplayed("list_selectedNomineesPrepopulated");
				ele.click();
			}
			logMessage("Step : Unselect all selected nominees\n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (Exception e) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("Step : Nominees are not already selected\n");
		}

	}

	public List<List<String>> selectRandomNominees(int numberOfNomineesToSelect) {
		List<List<String>> listOfFirstAndLastName = new ArrayList<>();

		for (int i = 1; i <= numberOfNomineesToSelect; i++) {
			Random rand = new Random();
			isElementDisplayed("list_nominees");
			int max = elements("list_nominees").size() - 1;
			int min = 0;

			System.out.println("min : ============:" + min
					+ " max : ============ " + max);
			int randomNumber = rand.nextInt((max - min) + 1) + min;
			System.out.println("random number :----------" + randomNumber);
			elements("list_nominees").get(randomNumber).click();
		}

		for (WebElement element : elements("list_selectedNomineesFirstName")) {
			String selectedNomineeFirstName = element.getText();
			selectedNomineeFirstNameList.add(selectedNomineeFirstName);
		}

		for (WebElement element : elements("list_selectedNomineesLastName")) {
			String selectedNomineeLastName = element.getText();
			selectedNomineeLastNameList.add(selectedNomineeLastName);
		}

		for (String selectedNomineeFirstName : selectedNomineeFirstNameList) {
			System.out.println("first name : " + selectedNomineeFirstName);
		}

		for (String selectedNomineeLastName : selectedNomineeLastNameList) {
			System.out.println("last name : " + selectedNomineeLastName);
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
			System.out.println("save btn text _________________________"
					+ ele.getAttribute("value"));
			if (ele.getAttribute("value").contains(buttonName)) {
				clickUsingXpathInJavaScriptExecutor(ele);
				logMessage("Step :  " + buttonName + " button is clicked \n");
			}
		}

	}

	public Map<Integer, String> enterRankForNominee(int maxPossibleNominees) {
		List<Integer> ranks = new ArrayList<Integer>();
		Map<Integer, String> nomineeRanks = new HashMap<Integer, String>();
		isElementDisplayed("heading_rankAward");
		logMessage("Info: User is navigated to Rank Award Nominees Page");
		ranks = generateRandomNumber(maxPossibleNominees);
		for (int i = 0; i < ranks.size(); i++) {
			logMessage("----" + ranks.get(i));
		}
		logMessage("list size:" + ranks.size());
		for (int i = 0; i < maxPossibleNominees; i++) { // 10
			Select dropdown_rank = new Select(element("drpdwn_rank",
					String.valueOf(i + 1)));
			dropdown_rank.selectByVisibleText(String.valueOf(ranks.get(i)));
		}
		nomineeRanks = enterNomineeRankAndData(maxPossibleNominees);
		clickOnConfirmBallotButton();

		return nomineeRanks;
	}

	public void clickOnConfirmBallotButton() {
		isElementDisplayed("btn_confirmBallot");
		element("btn_confirmBallot").click();
		logMessage("Info: Confirm Ballot Button is clicked\n");

	}

	public List<Integer> generateRandomNumber(int maxPossibleNominees) {
		int max = maxPossibleNominees + 1, min = 1, num; // 11
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
			// logMessage("Key: " + i + " name: " + name1);
			// Pattern p = Pattern.compile("[a-zA-Z_ ]+");
			// Matcher m = p.matcher(name1);
			// if (m.find()) {
			// logMessage("name in group:" + m.group());
			// }
		}
		Iterator<Entry<Integer, String>> it = nomineeName.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
		}
		return nomineeName;
	}

	public void verifyNomineeRankAndName(
			Map<Integer, String> expectedNomineeData, int maxPossibleNominees) {
		Map<Integer, String> actualNomineeData = new HashMap<Integer, String>();
		for (int i = 1; i <= maxPossibleNominees; i++) { // 10
			actualNomineeData.put(Integer.parseInt(element(
					"txt_confirmNomineeRank", String.valueOf(i)).getText()),
					element("txt_confirmNomineeName", String.valueOf(i))
							.getText());
		}
		Iterator<Entry<Integer, String>> it = actualNomineeData.entrySet()
				.iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
		}
		Assert.assertTrue(expectedNomineeData.equals(actualNomineeData),
				"Assertion Failed: Nominees are not having assigned ranks");
		logMessage("Assertion Passed: Nominees are having same assigned ranks");
	}

	public void verifyConfirmBallotPage() {
		isElementDisplayed("txt_confirmBallotPage");
		Assert.assertTrue(
				element("tab_currentTab").getText().contains("Confirm Ballot"),
				"Assertion Failed: User is not on Confirm Ballot Page");
		logMessage("Assertion Passed: User is on Confirm Ballot Page\n");
	}

	public void clickOnSubmit_EditBallot() {
		isElementDisplayed("txt_confirmBallotPage");
		element("txt_confirmBallotPage").click();
		logMessage("Step : Submit Ballot button is clicked\n");
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

	public void verifyStatusAfterBallotSubmission() {
		Assert.assertTrue(element("txt_status").getText().trim()
				.equalsIgnoreCase("Status - Ballot Submitted"),
				"actual status of judge is " + element("txt_status").getText()
						+ " and expected is Status - Ballot submitted\n");
		logMessage("Assertion Passed: Actual status of Judge is Status-Ballot Submitted\n");

		Assert.assertTrue(element("txt_ballotSubmissionDate").getText().trim()
				.contains("You submitted your ballot on:"),
				"Assertion Failed: Submission date is not present\n");
		logMessage(element("txt_ballotSubmissionDate").getText().trim());
	}

}
