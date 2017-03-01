package com.qait.keywords;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class ASM_MeetingPage extends GetPage {
	WebDriver driver;
	static String pagename = "ASM_MeetingPage";

	public ASM_MeetingPage(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

	public void loginInToApplication(String userName, String password) {
		enterUserName(userName);
		enterPassword(password);
		clickOnVerifyButton();
		wait.waitForPageToLoadCompletely();
	}

	public void enterUserName(String userName) {
		isElementDisplayed("inp_username");
		element("inp_username").clear();
		element("inp_username").sendKeys(userName);
		logMessage("STEP : " + userName + " is entered in inp_username\n");
	}

	public void enterPassword(String password) {
		isElementDisplayed("inp_password");
		element("inp_password").clear();
		element("inp_password").sendKeys(password);
		logMessage("STEP : " + password + " is entered in inp_username\n");
	}

	public void clickOnVerifyButton() {
		isElementDisplayed("btn_verify");
		element("btn_verify").click();
		logMessage("STEP : Verify button is clicked in btn_verify\n");
		wait.hardWait(3);
	}

	public void enterTextInSpecialService(String textValue) {
		isElementDisplayed("txtarea_specialService");
		element("txtarea_specialService").sendKeys(textValue);
		logMessage("STEP : " + textValue + " is entered in txtarea_specialService\n");
	}

	public void verifyAletText(String alertText) {
		String actualAlertText = getAlertText();
		Assert.assertTrue(actualAlertText.equalsIgnoreCase(alertText),
				"ASSERT FAILED: Expected alert text is " + alertText + " but found " + actualAlertText);
		logMessage("ASSERT PASSED : Alert message " + alertText + " is verified\n");
	}

	public void selectReqiuredFieldsOnRegistration(String professionalDiscipline, String conference,
			String speakerPoster) {
		isElementDisplayed("list_professionalDiscipline");
		selectProvidedTextFromDropDown(element("list_professionalDiscipline"), professionalDiscipline);
		waitForSpinnerToDisappear();
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		isElementDisplayed("list_conference");
		wait.hardWait(2);
		// selectProvidedTextFromDropDown(element("list_conference"),
		// conference);
		selectDropDownValue(element("list_conference"), 1);
		isElementDisplayed("list_speakerPoster");
		// selectProvidedTextFromDropDown(element("list_speakerPoster"),
		// speakerPoster);
		selectDropDownValue(element("list_speakerPoster"), 1);
		checkAttendeeList();

	}

	public void waitForSpinnerToDisappear() {
		try {
			isElementDisplayed("img_spinner");
			logMessage("STEP : wait for spinner to disappear\n");
			wait.waitForElementToDisappear(element("img_spinner"));
		} catch (NoSuchElementException exp) {
			logMessage("Spinner is not present\n");
		}
	}

	public void clickOnContinueButton() {
		isElementDisplayed("btn_continue");
		element("btn_continue").click();
		logMessage("STEP : Continue button is clicked in btn_continue\n");
	}

	public void verifyLoginErrorMessagePresent(String errorMessage) {
		isElementDisplayed("txt_loginErrorMessage");
		verifyElementTextContains("txt_loginErrorMessage", errorMessage);
		logMessage("ASSERT PASSED : Error message " + errorMessage + " is appeared on invalid credentials\n");
	}

	public void checkAttendeeList() {
		isElementDisplayed("chk_attendeeList");
		if (!element("chk_attendeeList").isDisplayed()) {
			element("chk_attendeeList").click();
			logMessage("STEP : Attendee list check box is checked in chk_attendeeList\n");
		} else {
			logMessage("STEP : Attendee list check box is already checked in chk_attendeeList\n");
		}
	}

	public void enterPaymentInfo(String cardType, String cardHolderName, String cardNumber, String expirationDate,
			String CCVNumber) {
		selectCardInfo("cardType", cardType);
		inputCardInfo("cardHolderName", cardHolderName);
		inputCardInfo("cardNumber", cardNumber);
		selectCardInfo("expirationDate", expirationDate);
		inputCardInfo("CCVNumber", CCVNumber);
		clickOnContinueButton();
	}

	public void selectCardInfo(String cardDetail, String cardInfo) {
		isElementDisplayed("list_" + cardDetail);
		selectProvidedTextFromDropDown(element("list_" + cardDetail), cardInfo);
		logMessage("STEP : " + cardInfo + " is selected in " + cardDetail + "\n");
	}

	public void inputCardInfo(String cardDetail, String cardInfo) {
		isElementDisplayed("inp_" + cardDetail);
		element("inp_" + cardDetail).sendKeys(cardInfo);
		logMessage("STEP : " + cardInfo + " is entered in " + cardDetail + "\n");
	}

	public void checkAdvancedRegistrationAndTicketEvents() {
		checkAdvanceRegistration();
		checkTicketEvents();
		// checkNumberOfNightsCheckbox();
		// enterNumberOfNights(numberOfNights);

		clickOnContinueButton();
	}

	public void checkAdvanceRegistration() {
		isElementDisplayed("chk_advanceRegistration");
		if (!element("chk_advanceRegistration").isSelected()) {
			element("chk_advanceRegistration").click();
			logMessage("STEP : Check advance registration check box for first product chk_advanceRegistration\n");
		} else {
			logMessage("STEP : Check box for first product already checked\n");
		}
	}

	public void checkTicketEvents() {
		isElementDisplayed("chk_ticketEvents");
		if (!element("chk_ticketEvents").isSelected()) {
			element("chk_ticketEvents").click();
			logMessage("STEP : Check ticket events check box for first product chk_ticketEvents\n");
		} else {
			logMessage("STEP : Check box for first product already checked\n");
		}
	}

	public void verifyMessageOnSubmitPaymentDetails(String message) {
		isElementDisplayed("txt_labelMessage");
		verifyElementText("txt_labelMessage", message);
	}

	public void selectOneWordAndCopyPasteInSpecialServiceTextArea() {
		Actions act = new Actions(driver);
		isElementDisplayed("txt_eventRegistration");
		act.clickAndHold(element("txt_eventRegistration")).moveToElement(element("txt_eventRegistration"), 0, 10)
				.release().build().perform();
		// act.doubleClick(element("txt_eventRegistration")).build().perform();
		act.sendKeys(Keys.chord(Keys.CONTROL, "c"));
		act.build().perform();
		element("txtarea_specialService").click();
		act.sendKeys(Keys.chord(Keys.CONTROL, "v"));
		act.build().perform();
	}

	public void checkNumberOfNightsCheckbox() {
		isElementDisplayed("chk_numberOfNights");
		if (element("chk_numberOfNights").isSelected()) {
			element("chk_numberOfNights").click();
			logMessage("STEP : Number of nights checkbox is checked in chk_numberOfNights\n");
		} else {
			logMessage("STEP : Number of nights checkbox is already checked \n");
		}

	}

	public void enterNumberOfNights(String numberOfNights) {
		isElementDisplayed("inp_numberOfNights");
		element("inp_numberOfNights").sendKeys(numberOfNights);
		logMessage("STEP : " + numberOfNights + " is entered in checkNumberOfNightsCheckbox\n");
	}

}
