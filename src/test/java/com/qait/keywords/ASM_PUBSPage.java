package com.qait.keywords;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.GetPage;

public class ASM_PUBSPage extends GetPage {
	WebDriver driver;
	static String pagename = "ASM_PUBSPage";

	public ASM_PUBSPage(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

	public void loginInToApplication(String userName, String password) {
		enterUserName(userName);
		enterPassword(password);
		clickOnVerifyButton();
	}

	public void enterUserName(String userName) {
		isElementDisplayed("inp_userName");
		element("inp_userName").sendKeys(userName);
		logMessage("Step : " + userName + " is entered in inp_userName\n");
	}

	public void enterPassword(String password) {
		isElementDisplayed("inp_password");
		element("inp_password").sendKeys(password);
		logMessage("Step : " + password + " is entered in inp_password\n");
	}

	public void clickOnVerifyButton() {
		isElementDisplayed("btn_verify");
		element("btn_verify").click();
		logMessage("Step : verify button is clicked in btn_verify\n");
	}

	public void addSubscription() {
		wait.hardWait(2);
		clickOnAddAnESubscriptionButton();
		wait.hardWait(2);
		hardWaitForIEBrowser(5);
		verifyESubscriptionPage();
		clickOnFirstAddButton();
		wait.hardWait(3);
		clickOnSaveButton();
	}

	public void clickOnAddAnESubscriptionButton() {
		isElementDisplayed("btn_subscriptionAdd");
		element("btn_subscriptionAdd").click();
		logMessage("Step : add an e-subscription button is clicked in btn_subscriptionAdd\n");
	}

	public void verifyESubscriptionPage() {
		isElementDisplayed("txt_eSubscription");
	}

	public void clickOnFirstAddButton() {
		isElementDisplayed("btn_add");
		element("btn_add").click();
		logMessage("Step : add button is clicked in btn_add \n");
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(3);
		// verifySubcriptionAdded();
	}

	public void clickOnSaveButton() {
		isElementDisplayed("btn_save");
		element("btn_save").click();
		logMessage("Step : save button is clicked in btn_save \n");
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(3);
		// verifySavedSuccessfully();
	}

	public void verifySubcriptionAdded() {
		isElementDisplayed("txt_added");
		logMessage("ASSERT PASSED : verify added successfully in txt_added\n");
	}

	public void verifySavedSuccessfully() {
		isElementDisplayed("tr_saved");
	}

	public void submitPaymentDetails(String cardType, String cardholderName,
			String cardNumber, String cvvNumber, String year_Value) {
		verifyPaymentPage();
		selectCreditCardType(cardType);
		enterCreditCardHolderName(cardholderName);
		enterCreditCardNumber(cardNumber);
		enterCVVNumber(cvvNumber);
		selectExpirationYear(year_Value);
		clickOnConfirmOrderButton();
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(3);
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

	public void selectExpirationYear(String yearValue) {
		isElementDisplayed("list_expirationYear");
		selectProvidedTextFromDropDown(element("list_expirationYear"),
				yearValue);
		logMessage("Step : " + yearValue
				+ " is selected for in list_expirationYear\n");
	}

	public void clickOnConfirmOrderButton() {
		isElementDisplayed("btn_ConfirmOrder");
		element("btn_ConfirmOrder").click();
		logMessage("Step : Confirm Order button is clicked in btn_ConfirmOrder\n");
	}

	public void clickOnPlaceOrder() {
		isElementDisplayed("btn_placeOrder");
		element("btn_placeOrder").click();
		logMessage("Step : click on place order in btn_placeOrder\n");
	}

	public void verifyReceiptPage() {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(2);
		isElementDisplayed("txt_receipt");
	}

	public void verifyPaymentPage() {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(2);
		isElementDisplayed("txt_paymentPage");
	}
}
