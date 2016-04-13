package com.qait.keywords;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

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
							element("txt_status").getText().equalsIgnoreCase(
									"Status - Ballot submitted"),
							"actual status of rescused judge is "
									+ element("txt_status").getText()
									+ " and expected is " + nameOfJudge + "\n");
					logMessage("AASERT PASSED : Status Status - Ballot submitted of rescused judge "
							+ nameOfJudge + " is verified\n");
				}
			}

		} else {
			Assert.assertTrue(
					element("txt_status").getText().equalsIgnoreCase(
							"Status - Not Yet Started"),
					"actual status of rescused judge is "
							+ element("txt_status").getText()
							+ " and expected is " + nameOfJudge + "\n");
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
		isElementDisplayed("txt_submitBallotDate");
		Assert.assertTrue(element("txt_submitBallotDate").getText()
				.equalsIgnoreCase(date),
				"ASSERT FAILED : actual: "
						+ element("txt_submitBallotDate").getText()
						+ " expected: " + date);
	}

	public void clickOnFiveYearNomineeMemoLink() {
		isElementDisplayed("lnk_fiveYearNomineeMemo");
		element("lnk_fiveYearNomineeMemo").click();
		logMessage("Step : click on five year nominee memo link\n");
	}

	public void clickOnViewNominationMaterialButton() {
		isElementDisplayed("inp_viewNominationMaterial");
		element("inp_viewNominationMaterial").click();
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

	public void selectTenRandomNominees() {
		isElementDisplayed("list_nominees");
		for (WebElement element : elements("list_nominees")) {
			element.click();
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

	}

}
