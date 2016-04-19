package com.qait.keywords;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.DateUtil;

public class ACS_Awards_EWEB_PageActions extends ASCSocietyGenericPage {
	WebDriver driver;
	static String pagename = "ACS_Awards_EWEB";
	List<String> selectedNomineeFirstNameList = new ArrayList();
	List<String> selectedNomineeLastNameList = new ArrayList();

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
			enterCredential("credential2",
					memberDetail.get(nameOfJudge).get(1), credentialType);
			enterCredential("credential2", "password", credentialType);
		}
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
		System.out.println("rescused judges :" + rescusedJudges);
		if (rescusedJudges != null) {
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

		} else {
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

	public void verifyNumberOfDays(String numberOfDays) {
		isElementDisplayed("txt_numberOfDaysRemaining", numberOfDays);
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
		isElementDisplayed("tab_currentTab");
		Assert.assertTrue(
				element("tab_currentTab").getText().trim().endsWith(tabName),
				"ASSERT FAILED : actual current tab is "
						+ element("tab_currentTab").getText()
						+ " and expected current tab is " + tabName + "\n");
		logMessage("ASSERT PASSED : current tab " + tabName + " is verified \n");
	}

	public List<List<String>> selectRandomNominees(int numberOfNomineesToSelect) {
		List<List<String>> listOfFirstAndLastName = new ArrayList<>();

		for (int i = 1; i <= numberOfNomineesToSelect; i++) {
			Random rand = new Random();
			isElementDisplayed("list_nominees");
			int max = elements("list_nominees").size()-1;
			int min = 0;
			
			System.out.println("min : ============:" + min+" max : ============ "+max);
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
		int max = elements("list_nominees").size();
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
		isElementDisplayed("btn_rankNominees_save", buttonName);
		element("btn_rankNominees_save", buttonName).click();
		logMessage("Step :  " + buttonName + " button is clicked \n");
	}

}
