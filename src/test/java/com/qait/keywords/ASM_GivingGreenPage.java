package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class ASM_GivingGreenPage extends GetPage {
	WebDriver driver;
	static String url;
	static String pagename = "ASM_GivingGreenPage";
	int timeOut, hiddenFieldTimeOut;

	public ASM_GivingGreenPage(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

	public void loginIntoApplicationWithACS_ID(String userName, String password) {
		clickOnLoginButton();
		clickOnACSIDRadioButton();
		enterUserName_memberNumber(userName);
		enterPassword(password);
		clickOnLoginSubmitButton();
		wait.waitForPageToLoadCompletely();
		waitForLoaderToDisappear();
	}

	public void waitForLoaderToDisappear() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			if (isElementDisplayed("img_loginLoading")) {
				logMessage("Step : wait for load to diasappear\n");
				wait.waitForElementToDisappear(element("img_loginLoading"));
			} else {
				logMessage("Step : loader image is not displayed at login page\n");
			}
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (Exception exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("Step : loader image is not displayed at login page\n");
		}

	}

	public void loginIntoApplicationWith_Lastname_Member_Number(
			String lastName, String memberNumber) {
		clickOnLoginButton();
		clickOnLastNameAndMemberNumber_RadioButton();
		enterUserName_memberNumber(memberNumber);
		enterLastName(lastName);
		clickOnLoginSubmitButton();
		waitForLoaderToDisappear();
		wait.hardWait(2);
	}

	public void clickOnLoginButton() {
		try{
			isElementDisplayed("btn_loginMember");
			element("btn_loginMember").click();
			logMessage("Step : Login button is clicked in btn_loginMember\n");
			isElementDisplayed("div_selectMember");
		}catch(StaleElementReferenceException stlExp){
			isElementDisplayed("btn_loginMember");
			element("btn_loginMember").click();
			logMessage("Step : Login button is clicked in btn_loginMember\n");
			isElementDisplayed("div_selectMember");
		}
		
	}

	public void clickOnACSIDRadioButton() {
		isElementDisplayed("rad_acsId");
		if (!element("rad_acsId").isSelected())
			element("rad_acsId").click();
		logMessage("Step : ACS ID radio button is checked in rad_acsId\n");
	}

	public void clickOnLastNameAndMemberNumber_RadioButton() {
		isElementDisplayed("rad_lastnameMemberNumber");
		if (!element("rad_lastnameMemberNumber").isSelected()) {
			clickUsingXpathInJavaScriptExecutor(element("rad_lastnameMemberNumber"));
		}
		// element("rad_lastnameMemberNumber").click();
		logMessage("Step : ACS last name / member number radio button is checked in rad_lastnameMemberNumber\n");
		wait.hardWait(2);
	}

	public void enterUserName_memberNumber(String userName_lastName) {
		isElementDisplayed("inp_username");
		element("inp_username").clear();
		element("inp_username").sendKeys(userName_lastName);
		logMessage("Step : " + userName_lastName
				+ " is entered in inp_username\n");
	}

	public void enterPassword(String password) {
		isElementDisplayed("inp_password");
		element("inp_password").clear();
		element("inp_password").sendKeys(password);
		logMessage("Step : " + password + " is entered in inp_username\n");
	}

	public void enterLastName(String lastName) {
		isElementDisplayed("inp_lastName");
		element("inp_lastName").clear();
		element("inp_lastName").sendKeys(lastName);
		logMessage("Step : " + lastName + " is entered in inp_lastName\n");
	}

	public void clickOnLoginSubmitButton() {
		isElementDisplayed("btn_login");
		element("btn_login").click();
		logMessage("Step : login button is clicked in btn_login\n");
	}

	public void clickOnContinueButton() {
		wait.hardWait(3);
		isElementDisplayed("btn_continue");
		//element("btn_continue").click();
		clickUsingXpathInJavaScriptExecutor(element("btn_continue"));
		logMessage("Step : click on continue button in btn_continue\n");
		wait.waitForPageToLoadCompletely();
	}

	public void selectAmount(String amount) {
		isElementDisplayed("rad_amount", amount);
		if (!element("rad_amount", amount).isSelected()) {
			element("rad_amount", amount).click();
			logMessage("Step : radio button for amount " + amount
					+ " is selected in rad_amount\n");
		} else {
			logMessage("Step : radio button for amount " + amount
					+ " is already selected");
		}
	}

	public void selectAndEnterOtherAmount(String otherAmountValue) {
		isElementDisplayed("rad_otherAmount");
		element("rad_otherAmount").click();
		logMessage("Step : radio button is clicked for other amount in rad_otherAmount\n");

		isElementDisplayed("inp_otherAmount");
		element("inp_otherAmount").clear();
		element("inp_otherAmount").sendKeys(otherAmountValue);
		logMessage("Step : " + otherAmountValue
				+ " is entered in inp_otherAmount\n");

	}

	public void verifyLoginErrorMessagePresent(String errorMessage) {
		isElementDisplayed("txt_loginErrorMessage");
		System.out.println("error message is : "
				+ element("txt_loginErrorMessage").getText());
		verifyElementTextContains("txt_loginErrorMessage", errorMessage);
		logMessage("ASSERT PASSED : Error message " + errorMessage
				+ " is appeared on invalid credentials\n");
	}

	public void logOutApplication() {
		isElementDisplayed("btn_logout");
		element("btn_logout").click();
		logMessage("Step : Logout Application in btn_logout\n");
	}

	public void verifyCurrentPage(String pageName) {
//		wait.waitForPageToLoadCompletely();
//		url = getCurrentURL();
//
//		String[] splitUrl = url.split("Code=");
//		if (splitUrl[1].equalsIgnoreCase("allprograms")) {
//			
//			Assert.assertTrue(pageName.contains("Make a donation"));
//			logMessage("ASSERT PASSED : current page is Make a donation page\n");
//
//		} else if (splitUrl[1].equalsIgnoreCase("ContactInfo")) {
//			
//			Assert.assertTrue(pageName.contains("Contact info"));
//			logMessage("ASSERT PASSED : current page is Contact info page\n");
//
//		} else if (splitUrl[1].equalsIgnoreCase("Checkout")) {
//			
//			Assert.assertTrue(pageName.contains("Confirm your donation"));
//			logMessage("ASSERT PASSED : current page is Confirm your donation page\n");
//
//		} else if (splitUrl[1].equalsIgnoreCase("confirmation")) {
//			
//			Assert.assertTrue(pageName.contains("confirmation"));
//			logMessage("ASSERT PASSED : current page is confirmation page\n");
//		} else {
//			Assert.fail("Current page is not expected\n");
//		}
	}

	public void enterRequiredDetailsInNonMemberForm(String email, String phone,
			String address, String city, String state, String zipcode,
			String country) {
		enterMemberFieldValue("Email", email);
		enterMemberFieldValue("Phone", phone);
		enterMemberFieldValue("Adr_Line1", address);
		enterMemberFieldValue("City", city);
		selectNonMemberFieldValue("State", state);
		enterMemberFieldValue("ZipCode", zipcode);
		selectNonMemberFieldValue("Country", country);

	}

	public void enterMemberFieldValue(String fieldName, String fieldValue) {
		isElementDisplayed("inp_fieldName", fieldName);
		element("inp_fieldName", fieldName).clear();
		element("inp_fieldName", fieldName).sendKeys(fieldValue);
		logMessage("Step : enter field value: " + fieldValue
				+ " in field name: " + fieldName + " in inp_fieldName\n");
	}

	public void selectNonMemberFieldValue(String fieldName, String fieldValue) {
		isElementDisplayed("list_fieldName", fieldName);
		selectProvidedTextFromDropDown(element("list_fieldName", fieldName),
				fieldValue);
	}

	public void verifyErrorMessage(String errorMessage) {
		isElementDisplayed("txt_errorMessage");
		verifyElementTextContains("txt_errorMessage", errorMessage);
		logMessage("ASSERT PASSED : Verified error message " + errorMessage
				+ " in txt_errorMessage\n");
	}

	public void verifyCreditCardErrorMessage(String errorMessage) {
		isElementDisplayed("txt_creditCardError");
		verifyElementTextContains("txt_creditCardError", errorMessage);
		logMessage("ASSERT PASSED : Verified error message " + errorMessage
				+ " in txt_creditCardError\n");
	}

}
