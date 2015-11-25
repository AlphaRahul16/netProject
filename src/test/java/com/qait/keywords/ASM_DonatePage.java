package com.qait.keywords;

import org.apache.commons.lang3.text.WordUtils;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class ASM_DonatePage extends GetPage {
	WebDriver driver;
	static String url;
	static String pagename = "ASM_DonatePage";

	public ASM_DonatePage(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

	public void loginIntoApplication(String userName, String password) {
		clickOnLoginButton();
		clickOnACSIDRadioButton();
		wait.hardWait(3);
		enterUserName(userName);
		enterPassword(password);
		clickOnVerifyButton();
	}

	public void clickOnLoginButton() {
		isElementDisplayed("btn_login");
		element("btn_login").click();
		logMessage("Step : Login button is clicked in btn_login\n");
	}

	public void clickOnACSIDRadioButton() {
		isElementDisplayed("rad_acsId");
		if (!element("rad_acsId").isSelected())
			element("rad_acsId").click();
		logMessage("Step : ACS ID radio button is checked in rad_acsId\n");
	}

	public void enterUserName(String userName) {
		isElementDisplayed("inp_username");
		element("inp_username").clear();
		element("inp_username").sendKeys(userName);
		logMessage("Step : " + userName + " is entered in inp_username\n");
	}

	public void enterPassword(String password) {
		isElementDisplayed("inp_password");
		element("inp_password").clear();
		element("inp_password").sendKeys(password);
		logMessage("Step : " + password + " is entered in inp_username\n");
	}

	public void clickOnVerifyButton() {
		isElementDisplayed("btn_verify");
		element("btn_verify").click();
		logMessage("Step : Verify button is clicked in btn_verify\n");
		wait.hardWait(3);
	}

	public void enterDonateValue(String donateProgram, String donateValue) {
		try {
			isElementDisplayed("inp_DonateProgram", donateProgram);
			element("inp_DonateProgram", donateProgram).clear();
			element("inp_DonateProgram", donateProgram).sendKeys(donateValue);
			logMessage("Step : " + donateValue + " is entered for "
					+ donateProgram + " in inp_DonateProgram\n");
		} catch (StaleElementReferenceException stlExp) {
			isElementDisplayed("inp_DonateProgram", donateProgram);
			element("inp_DonateProgram", donateProgram).clear();
			element("inp_DonateProgram", donateProgram).sendKeys(donateValue);
			logMessage("Step : " + donateValue + " is entered for "
					+ donateProgram + " in inp_DonateProgram\n");
		}

	}

	public void clickOnContinueButton() {
		isElementDisplayed("btn_continue");
		element("btn_continue").click();
		logMessage("Step : click on continue button in btn_continue\n");
		wait.hardWait(3);
	}

	public void enterOtherProgram(String otherProgramValue) {
		isElementDisplayed("inp_otherProgram");
		element("inp_otherProgram").clear();
		element("inp_otherProgram").sendKeys(otherProgramValue);
		logMessage("Step : " + otherProgramValue
				+ " is entered in inp_otherProgram\n");
	}

	public void verifyCurrentPage(String pageName) {
		wait.hardWait(3);
		try {
			wait.waitForPageToLoadCompletely();
		} catch (TimeoutException timeOutExp) {
			logMessage("Page didn't load completely and perform an actions on loaded elements \n");
		}

		url = getCurrentURL();
		String[] splitUrl = url.split("Code=");
		if (splitUrl[1].equalsIgnoreCase("allprograms")) {
			Assert.assertTrue(pageName.contains("Make a donation"));
			logMessage("ASSERT PASSED : Current page is Make a donation page\n");
		} else if (splitUrl[1].equalsIgnoreCase("ContactInfo")) {
			Assert.assertTrue(pageName.contains("Contact info"));
			logMessage("ASSERT PASSED : Current page is Contact Info page\n");
		} else if (splitUrl[1].equalsIgnoreCase("Checkout")) {
			Assert.assertTrue(pageName.contains("Confirm your donation"));
			logMessage("ASSERT PASSED : Current page is Confirm your donation page\n");
		} else if (splitUrl[1].equalsIgnoreCase("confirmation")) {
			Assert.assertTrue(pageName.contains("confirmation"));
			logMessage("ASSERT PASSED : Current page is confirmation page\n");
		} else {
			Assert.fail("Current page is not expected\n");
		}
	}

	public void enterOtherAmount(String otherAmount) {
		isElementDisplayed("inp_otherAmount");
		element("inp_otherAmount").sendKeys(otherAmount);
		logMessage("Step : " + otherAmount + " is entered in inp_otherAmount\n");
	}

	public void logOut() {
		isElementDisplayed("btn_logout");
		element("btn_logout").click();
		logMessage("Step : log out button is clicked\n");
	}

	public void clickOnContinueAsGuest() {
		isElementDisplayed("btn_continueAsGuest");
		element("btn_continueAsGuest").click();
		logMessage("Step : continue as a guest button is clicked in btn_continueAsGuest\n");
		// verifyContinueAsGuest();
	}

	public void verifyContinueAsGuest() {
		try {
			isElementDisplayed("fieldSet_form");
			logMessage("ASSERT PASSED : Continue as a guest form is appeared\n");
		} catch (Exception stlExp) {
			isElementDisplayed("fieldSet_form");
			logMessage("ASSERT PASSED : Continue as a guest form is appeared\n");
		}

	}

	public void enterRequiredDetailsInNonMemberForm(String firstName,
			String lastName, String email, String phone, String address,
			String city, String state, String zipcode, String country) {
		enterNonMemberFieldValue("FirstName", firstName);
		enterNonMemberFieldValue("LastName", lastName);
		enterNonMemberFieldValue("Email", email);
		enterNonMemberFieldValue("Phone", phone);
		enterNonMemberFieldValue("Adr_Line1", address);
		enterNonMemberFieldValue("City", city);
		selectNonMemberFieldValue("State", state);
		enterNonMemberFieldValue("ZipCode", zipcode);
		selectNonMemberFieldValue("Country", country);
		clickOnContinueButton();
	}

	public void enterNonMemberFieldValue(String fieldName, String fieldValue) {
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

	public void verifyLoginErrorMessagePresent(String errorMessage) {
		isElementDisplayed("txt_loginErrorMessage");
		verifyElementTextContains("txt_loginErrorMessage", errorMessage);
		logMessage("ASSERT PASSED : Error message " + errorMessage
				+ " is appeared on invalid credentials\n");
	}

	public void verifyErrorMessage(String errorMessage) {
		isElementDisplayed("txt_errorMessage");
		verifyElementTextContains("txt_errorMessage", errorMessage);
		logMessage("ASSERT PASSED : Error message "
				+ errorMessage
				+ " is appeared on enter invalid data in contact info in txt_errorMessage\n");
	}

	public void checkGiftToSomeoneCheckBox() {
		isElementDisplayed("inp_giftToSomene");
		if (!element("inp_giftToSomene").isSelected()) {
			element("inp_giftToSomene").click();
			logMessage("Step : gift to someone checkbox is checked in inp_giftToSomene\n");
		} else {
			logMessage("Step : gift to someone checkbox is already checked\n");
		}

	}

	public void checkInHonor_MemoryCheckbox(String checkboxName) {
		isElementDisplayed("chk_honor_memory", checkboxName);
		if (!element("chk_honor_memory", checkboxName).isSelected()) {
			element("chk_honor_memory", checkboxName).click();
			logMessage("Step : " + checkboxName
					+ " is checked in chk_honor_memory\n");
		} else {
			logMessage("Step : " + checkboxName
					+ " is already checked in chk_honor_memory\n");
		}
	}

	public void enterHonor_MemoryValue(String honor_memory,
			String honor_memoryValue) {
		isElementDisplayed("inp_honor_memory",
				WordUtils.capitalize(honor_memory));
		element("inp_honor_memory", WordUtils.capitalize(honor_memory))
				.sendKeys(honor_memoryValue);
		logMessage("Step : " + honor_memoryValue + " is entered for "
				+ honor_memory + " in inp_honor_memory\n");
	}

	public void enterRecipientEmail(String emailAddress) {
		isElementDisplayed("inp_recipientEmail");
		element("inp_recipientEmail").sendKeys(emailAddress);
		logMessage("Step : " + emailAddress
				+ " is entered in inp_recipientEmail\n");
	}

	public void selectCreditCardType(String cardType) {
		isElementDisplayed("list_cardType");
		selectProvidedTextFromDropDown(element("list_cardType"), cardType);
	}

	public void enterCreditCardHolderName(String cardholderName) {
		isElementDisplayed("inp_cardHolderName");
		element("inp_cardHolderName").sendKeys(cardholderName);
		logMessage("Step : " + cardholderName
				+ " is entered in inp_cardHolderName\n");
	}

	public void enterCreditCardNumber(String cardNumber) {
		isElementDisplayed("inp_cardNumber");
		element("inp_cardNumber").sendKeys(cardNumber);
		logMessage("Step : " + cardNumber + " is entered in inp_cardNumber\n");
	}

	public void enterCVVNumber(String cvvNumber) {
		isElementDisplayed("inp_CVVNumber");
		element("inp_CVVNumber").sendKeys(cvvNumber);
		logMessage("Step : " + cvvNumber + " is entered in inp_CVVNumber\n");
	}

	public void selectExpirationDate_Year(String date_year,
			String date_yearValue) {
		isElementDisplayed("list_expiration" + date_year);
		selectProvidedTextFromDropDown(element("list_expiration" + date_year),
				date_yearValue);
		logMessage("Step : " + date_yearValue + " is selected for " + date_year
				+ " in list_expiration\n");
	}

	public void enterRequiredDetailsToConfirmYourDonation(String honor_memory,
			String honor_memoryValue, String sendCardType,
			String... sendCardInfo) {
		checkGiftToSomeoneCheckBox();
		checkInHonor_MemoryCheckbox(honor_memory);
		enterHonor_MemoryValue(honor_memory, honor_memoryValue);
		enterSendCardInfo(sendCardType, sendCardInfo);

	}

	public void enterPaymentDetails(String cardType, String cardholderName,
			String cardNumber, String cvvNumber, String date_Value,
			String year_Value) {
		selectCreditCardType(cardType);
		enterCreditCardHolderName(cardholderName);
		enterCreditCardNumber(cardNumber);
		enterCVVNumber(cvvNumber);
		selectExpirationDate_Year("Date", date_Value);
		selectExpirationDate_Year("Year", year_Value);
		clickOnContinueButton();
	}

	public void enterSendCardInfo(String sendCardType, String[] sendCardinfo) {
		if (sendCardType.equalsIgnoreCase("Email")) {
			enterRecipientEmail(sendCardinfo[0]);
		} else if (sendCardType.equalsIgnoreCase("Postal")) {
			checkSendCradType(sendCardType);
			enterPostalEmailInTo(sendCardinfo[0]);
			enterPostalMailInfo("Adr_Line1", sendCardinfo[1]);
			enterPostalMailInfo("City", sendCardinfo[2]);
			selectPostalMailInfo("State", sendCardinfo[3]);
			enterPostalMailInfo("ZipCode", sendCardinfo[4]);
		}
	}

	public void enterPostalEmailInTo(String postalEmail) {
		isElementDisplayed("inp_to");
		element("inp_to").sendKeys(postalEmail);
		logMessage("Step : " + postalEmail + " is entered in inp_to\n");
	}

	public void checkSendCradType(String typeName) {
		isElementDisplayed("rad_sendCardType", typeName);
		if (!element("rad_sendCardType", typeName).isSelected()) {
			element("rad_sendCardType", typeName).click();
			logMessage("Step : " + typeName
					+ " is checked in rad_sendCardType\n");
		} else {
			logMessage("Step : " + typeName
					+ " is already checked in rad_sendCardType\n");
		}
	}

	public void enterPostalMailInfo(String fieldName, String fieldValue) {
		isElementDisplayed("inp_postalMailInfo", fieldName);
		element("inp_postalMailInfo", fieldName).sendKeys(fieldValue);
		logMessage("Step : " + fieldValue
				+ " is entered in inp_postalMailInfo\n");
	}

	public void selectPostalMailInfo(String fieldName, String fieldValue) {
		isElementDisplayed("list_postalMailInfo", fieldName);
		selectProvidedTextFromDropDown(
				element("list_postalMailInfo", fieldName), fieldValue);
		logMessage("Step : " + fieldValue
				+ " is entered in list_postalMailInfo\n");
	}

	public void verifyCreditCardErrorMessage(String errorMessage) {
		isElementDisplayed("txt_creditCardError");
		verifyElementTextContains("txt_creditCardError", errorMessage);
		logMessage("ASSERT PASSED : Verified error message " + errorMessage
				+ " in txt_creditCardError\n");
	}

}
