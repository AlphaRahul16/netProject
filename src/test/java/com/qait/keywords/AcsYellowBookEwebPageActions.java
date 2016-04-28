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

public class AcsYellowBookEwebPageActions extends ASCSocietyGenericPage {
	WebDriver driver;
	static String pagename = "ACS_YellowBook_EWEB";

	public AcsYellowBookEwebPageActions(WebDriver driver) {
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

	public void loginWithLastNameAndMemberId(String lastName, String id) {
		isElementDisplayed("inp_ln_id","Credential1");
		isElementDisplayed("inp_ln_id","Credential2");
		isElementDisplayed("inp_verify");
		element("inp_ln_id","Credential1").sendKeys(lastName);
		element("inp_ln_id","Credential2").sendKeys(id);
		element("inp_verify").click();
		logMessage("Login With "+lastName+" and "+id+" and clicked on login button");
	}

	public void verifyUserIsOnHomePageOfYellowBookEweb() {
		isElementDisplayed("header_home","YellowBook Home");
		logMessage("[ASSERTION PASSED]:: Verified User Is On Home Page of Yellow Book Eweb");
	}

	public void clickOnLinkOnHomePageYBEweb(String link) {
		isElementDisplayed("link_header",link);
		element("link_header",link).click();
		logMessage("Clicked On My "+link+" on Home Page Of Yellow Book Eweb");
	}

	public void verifyUserNavigatedToParticularPage(String title) {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("txt_toptitle");
		Assert.assertEquals(element("txt_toptitle").getText().trim(),title);
		logMessage("[ASSERTION PASSED]:: Verified User Navigated to "+title+" Page");
	}

	public void updateAddessField(String value) {
		isElementDisplayed("inp_address2");
		element("inp_address2").clear();
		element("inp_address2").sendKeys(value);
		logMessage("Updated Address field 2 with value "+value);
	}

	public void clickOnCheckBoxAndThanClickOnSubmitButton(String str) {
		if (str.equalsIgnoreCase("Yes")) {
			isElementDisplayed("chkbox_update");
			element("chkbox_update").click();
		}
		isElementDisplayed("btn_submit");
		element("btn_submit").click();
	}

	public void verifyUpdatedAddressOnYellowBookHomePage(String address) {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("txt_updated_add");
		Assert.assertTrue(element("txt_updated_add").getText().contains(address));
		logMessage("[ASSERTION PASSED]:: Verified Updated Address"+address+" on Yellow Book Home Page");
	}

	public String updateBiographyFields(String value) {
		isElementDisplayed("inp_honor");
		String honorValue = element("inp_honor").getText().trim()+ value;
		element("inp_honor").clear();
		element("inp_honor").sendKeys(honorValue);
		isElementDisplayed("btn_submit");
		element("btn_submit").click();
		logMessage("Updated Honor field with value "+honorValue+" on Biography Page");
		return honorValue;
	}

	public void verifyUpdatedBiographyFieldOnYellowBookHomePage(String honorValue) {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("txt_updated_honor", "Honors");
		Assert.assertEquals(element("txt_updated_honor", "Honors").getText().trim(), honorValue);
		logMessage("[ASSERTION PASSED]:: Verified Updated Biography Updated Field On Yellow Book Home Page");
	}

	public void selectFourRandomCommitteesFromCommitteesPreferencesPage(String str) {
		wait.waitForPageToLoadCompletely();
		int i = 1;
		if (str.equalsIgnoreCase("Yes")) {
		for (WebElement element : elements("drpdown_committee")) {
			selectProvidedTextFromDropDown(element, "Ist");
			i++;
			if (i==4) {
				break;
			}
		}
		
		}
	}


}
