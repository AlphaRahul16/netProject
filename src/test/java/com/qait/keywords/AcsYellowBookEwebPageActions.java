package com.qait.keywords;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class AcsYellowBookEwebPageActions extends ASCSocietyGenericPage {
	WebDriver driver;
	static String pagename = "ACS_YellowBook_EWEB";
	List<String> committees = new ArrayList<String>();

	public AcsYellowBookEwebPageActions(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

	public void enterCredentials_LastNameMemberNumber_ACSID(String credentialType, String nameOfJudge,
			Map<String, List<String>> memberDetail) {
		System.out.println("login credential:" + nameOfJudge.split(" ")[0]);
		System.out.println("contact id:" + memberDetail.get(nameOfJudge).get(0));
		System.out.println("web login::" + memberDetail.get(nameOfJudge).get(1));
		checkCredentialType(credentialType);
		if (credentialType.equalsIgnoreCase("lastNameMemberNumber")) {
			enterCredential("credential1", nameOfJudge.split(" ")[0], credentialType);
			enterCredential("credential2", memberDetail.get(nameOfJudge).get(0), credentialType);
		} else if (credentialType.equalsIgnoreCase("ACSID")) {
			enterCredential("credential1", memberDetail.get(nameOfJudge).get(1), credentialType);
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

	public void enterCredential(String credential1_2, String credentialValue, String credentialType) {
		isElementDisplayed("inp_" + credential1_2);
		element("inp_" + credential1_2).clear();
		element("inp_" + credential1_2).sendKeys(credentialValue);
		;
		logMessage("STEP : " + credentialValue + " is entered in " + credentialType + "\n");
	}

	public void checkCredentialType(String typeName) {
		isElementDisplayed("rad_" + typeName);
		element("rad_" + typeName).click();
		logMessage("STEP : Check credential type " + typeName + "\n");
	}

	public void clickOnLoginButton() {
		isElementDisplayed("btn_login");
		element("btn_login").click();
		logMessage("STEP : Click on login button\n");
	}

	public void verifyLoginInAwardApplicationSuccessfully(String judgeName) {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("hd_welcome");
		Assert.assertTrue(element("hd_welcome").getText().contains(judgeName.split(" ")[0]),
				"Heading is not contains last name");
		Assert.assertTrue(element("hd_welcome").getText().contains(judgeName.split(" ")[1]),
				"Heading is not contains first name");
		logMessage("ASSERT PASSED : Judge name " + judgeName + " login successfully\n");
	}

	public void loginWithLastNameAndMemberId(String lastName, String id) {
		isElementDisplayed("inp_ln_id", "Credential1");
		isElementDisplayed("inp_ln_id", "Credential2");
		isElementDisplayed("inp_verify");
		element("inp_ln_id", "Credential1").sendKeys(lastName);
		element("inp_ln_id", "Credential2").sendKeys(id);
		element("inp_verify").click();
		logMessage("Login with " + lastName + " and " + id + " and clicked on login button");
	}

	public void verifyUserIsOnHomePageOfYellowBookEweb() {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("header_home", "YellowBook Home");
		logMessage("ASSERTION PASSED : Verified User Is On Home Page of Yellow Book Eweb");
	}

	public void clickOnLinkOnHomePageYBEweb(String link) {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("link_header", link);
		element("link_header", link).click();
		logMessage("Clicked On My " + link + " on Home Page Of Yellow Book Eweb");
	}

	public void verifyUserNavigatedToParticularPage(String title) {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("txt_toptitle");
		Assert.assertEquals(element("txt_toptitle").getText().trim(), title);
		logMessage("ASSERTION PASSED : Verified User Navigated to " + title + " Page");
	}

	public void updateAddessField(String value) {
		isElementDisplayed("inp_address2");
		System.out.println("1::" + element("inp_address2").getAttribute("value"));
		element("inp_address2").clear();
		System.out.println("2::" + element("inp_address2").getAttribute("value"));
		element("inp_address2").sendKeys(value);
		System.out.println("3::" + element("inp_address2").getAttribute("value"));
		logMessage("Updated Address field 2 with value " + value);
	}

	public void clickOnCheckBoxAndThanClickOnSubmitButton(String str) {
		if (str.equalsIgnoreCase("Yes")) {
			isElementDisplayed("chkbox_update");
			element("chkbox_update").click();
		}
		isElementDisplayed("btn_submit");
		clickUsingXpathInJavaScriptExecutor(element("btn_submit"));
		//element("btn_submit").click();
	}

	public void verifyUpdatedAddressOnYellowBookHomePage(String address) {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("txt_updated_add");
		// logMessage(element("txt_updated_add").getText().trim());
		Assert.assertTrue(element("txt_updated_add").getText().trim().contains(address));
		logMessage("ASSERTION PASSED : Verified Updated Address" + address + " on Yellow Book Home Page");
	}

	public void updateBiographyFields(String value) {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("inp_honor");
		element("inp_honor").clear();
		element("inp_honor").sendKeys(value);
		isElementDisplayed("btn_submit");
		element("btn_submit").click();
		logMessage("Updated Honor field with value " + value + " on Biography Page");
	}

	public void verifyUpdatedBiographyFieldOnYellowBookHomePage(String honorValue) {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("txt_updated_honor", "Honors");
		Assert.assertEquals(element("txt_updated_honor", "Honors").getText().trim(), honorValue);
		logMessage("ASSERTION PASSED : Verified Updated Biography Updated Field On Yellow Book Home Page");
	}

	public List<String> selectFourRandomCommitteesFromCommitteesPreferencesPage(String str) {
		wait.waitForPageToLoadCompletely();
		int i = 0;
		if (str.equalsIgnoreCase("Yes")) {
			if (checkIfElementIsThere("txt_currentCommittee")) {
				logMessage("First committee pre-selected");
				_selectCommitteesWithoutWarningImage(2);
				_selectCommitteesWithWarningImage();
				i = 3;
			} else {
				_selectCommitteesWithoutWarningImage(3);
				_selectCommitteesWithWarningImage();
				i = 4;
			}
		} else {
			if (checkIfElementIsThere("txt_currentCommittee")) {
				_selectCommitteesWithoutWarningImage(3);
				i = 3;
			} else {
				_selectCommitteesWithoutWarningImage(4);
				i = 4;
			}
		}
		return _getSelectedCommitteesList(i);
	}

	private List<String> _getSelectedCommitteesList(int size) {
		wait.waitForPageToLoadCompletely();
		for (int i = 1; i <= size; i++) {
			logMessage("Committee" + i + "::" + element("txt_select_committee", "" + i).getText().trim());
			committees.add(element("txt_select_committee", "" + i).getText().trim());
		}
		return committees;
	}

	private void _selectCommitteesWithWarningImage() {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		Select dropdown_committee = new Select(element("drpdown_img_alert"));
		dropdown_committee.selectByIndex(1);
		// selectProvidedTextFromDropDown(element("drpdown_img_alert"), "1st");
		logMessage("Selected 1 Committee With Warning Image");
	}

	private void _selectCommitteesWithoutWarningImage(int i) {
		int j = 1;
		wait.waitForPageToLoadCompletely();
		for (WebElement element : elements("drpdown_select")) {
			wait.hardWait(2);
			Select dropdown_committee = new Select(element);
			dropdown_committee.selectByIndex(1);
			// selectDropDownValu("2nd");
			// selectProvidedTextFromDropDown(element, "1st");
			if (j == i) {
				break;
			}
			j++;
		}
		logMessage("Selected " + i + " committees with out warning image");
	}

	public void clickOnContinueButton(String str) {
		isElementDisplayed("bttn_continue");
		element("bttn_continue").click();
		if (str.equalsIgnoreCase("Yes")) {
			isElementDisplayed("modal_popup");
			isElementDisplayed("chkbox_understand");
			isElementDisplayed("btn_cont");
			logMessage(
					"ASSERTION PASSED : Verified Modal Box pop up is displayed with I Understand check box when selected committees have conflicts");
			element("chkbox_understand").click();
			element("btn_cont").click();
			logMessage("Clicked on I understand check box and continue button on Modal Pop up");
		} else {
			Assert.assertFalse(checkIfElementIsThere("modal_popup"),
					"ASSERTION FAILED :  Modal Box pop up is displayed with I Understand check box when selected committees have no conflicts");
			logMessage(
					"ASSERTION PASSED : Verified Modal Box pop up is not displayed with I Understand check box when selected committees have conflicts");
		}
	}

	public void fillTheTestDataInSelectedCommitteeInputBoxAndClickOnSubmitButton() {
		wait.waitForPageToLoadCompletely();
		handleAlert();
		isElementDisplayed("inp_committee");
		for (WebElement element : elements("inp_committee")) {
			element.sendKeys("Test" + System.currentTimeMillis());
		}
		isElementDisplayed("btn_submit");
		element("btn_submit").click();
		logMessage("STEP : Filled the test data in Selected Committees Input Box and Click on Submit Button");
	}

	public void verifySelectedCommitteesOnYellowBookEwebHomePage(List<String> committeesList) {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("bttn_return_to_yb");
		element("bttn_return_to_yb").click();
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("list_select_committees");
		int i = 0;
		for (WebElement element : elements("list_select_committees")) {
			System.out.println("Actual Committees" + i + "::" + element.getText().trim());
			System.out.println("Expected Committees" + i + "::" + committeesList.get(i));
			Assert.assertEquals(element.getText().trim(), committeesList.get(i));
			i++;
		}
		logMessage("ASSERTION PASSED : Verified Selected Committees are displayed on Yellow Book Eweb Home Page");
	}

	public void verifyEditLinkIsDisplayed(boolean value) {
		if (value) {
			isElementDisplayed("lnk_edit");
			logMessage("[INFO] : Current Date lies between Start and End Date");
			logMessage("[INFO] : Edit link is present for Committee Preferences");
		} else {
			logMessage("[INFO] : Current Date does not lies between Start and End Date");
			logMessage("[INFO] : Edit link is not present for Committee Preferences");
		}

	}

}
