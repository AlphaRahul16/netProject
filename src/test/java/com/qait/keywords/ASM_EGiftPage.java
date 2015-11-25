package com.qait.keywords;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.GetPage;

public class ASM_EGiftPage extends GetPage {
	WebDriver driver;
	static String pagename = "ASM_EGiftPage";

	public ASM_EGiftPage(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

	public void selectFirstMemberCategory() {
		isElementDisplayed("rad_memberCategories");
		element("rad_memberCategories").click();
		logMessage("Step : member category is selected in rad_memberCategories\n");
	}

	public void verifyCurrentTab(String tabName) {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("txt_currentTab");
		verifyElementText("txt_currentTab", tabName);
	}

	public void fillGiftsAndRecipientDetails(String firstName, String lastName,
			String email) {
		verifyCurrentTab("Gift and Recipient Details");
		selectFirstMemberCategory();
		enterTextInRecipientInfo("FirstName", firstName);
		enterTextInRecipientInfo("LastName", lastName);
		enterTextInRecipientInfo("Email", email);

	}

	public void enterTextInRecipientInfo(String fieldName, String fieldValue) {
		isElementDisplayed("inp_" + fieldName);
		element("inp_" + fieldName).sendKeys(fieldValue);
		logMessage("Step : " + fieldValue + " is entered in ");
	}

	public void clickOnContinueButton() {
		isElementDisplayed("btn_continue");
		element("btn_continue").click();
		logMessage("Step : continue button is clicked in btn_continue\n");
	}

	public void enterPersonalMessage(String personalMessage) {
		isElementDisplayed("txtAr_personalMessage");
		element("txtAr_personalMessage").sendKeys(personalMessage);
		logMessage("Step : " + personalMessage
				+ " is entered in txtAr_personalMessage\n");
	}

	public void verifyErrorMessage(String errorMessage) {
		isElementDisplayed("txt_errorMessage");
		verifyElementText("txt_errorMessage", errorMessage);
	}

	public void selectPaymentInfo(String infoName, String infoValue) {
		selectProvidedTextFromDropDown(element("list_" + infoName), infoValue);
	}

	public void enterPaymentInfo(String infoName, String infoValue) {
		isElementDisplayed("inp_" + infoName);
		element("inp_" + infoName).clear();
		element("inp_" + infoName).sendKeys(infoValue);
		logMessage("Step : " + infoValue + "  is entered in inp_" + infoName
				+ "\n");
	}

	public void enterPaymentInformation(String CreditCardType,
			String CardHolderName, String CreditCardNumber,
			String expirationMonth, String expirationYear, String cvvNumber) {
		selectPaymentInfo("cardType", CreditCardType);
		enterPaymentInfo("cardHolderName", CardHolderName);
		enterPaymentInfo("cardNumber", CreditCardNumber);
		selectPaymentInfo("expMonth", expirationMonth);
		selectPaymentInfo("expYear", expirationYear);
		enterPaymentInfo("cvvNumber", cvvNumber);
		checkTermsAndConditionCheckBox();
	}

	public void checkTermsAndConditionCheckBox() {
		isElementDisplayed("chk_termsAndCondition");
		if (!element("chk_termsAndCondition").isSelected()) {
			element("chk_termsAndCondition").click();
			logMessage("Step : terms and conditin checkbox is checked in chk_termsAndCondition\n");
		} else {
			logMessage("Step : terms and conditin checkbox is already checked in chk_termsAndCondition\n");
		}
	}

	public void clickOnSubmitButton() {
		isElementDisplayed("inp_submit");
		element("inp_submit").click();
		logMessage("Step : submit button is clicked in inp_submit\n");
	}

	public void clickOnPrintReceiptButtonAndVerifyReceiptPage() {
		isElementDisplayed("btn_printReceipt");
		element("btn_printReceipt").click();
		logMessage("Step : print receipt button is clicked in btn_printReceipt\n");
		verifyReceiptPage();
	}

	public void clickOnPurchaseAnotherGiftButton() {
		isElementDisplayed("inp_purchaseAnotherGift");
		element("inp_purchaseAnotherGift").click();
		logMessage("Step : purchase another gift button is clicked in inp_purchaseAnotherGift\n");
	}

	public void verifyReceiptPage() {
		isElementDisplayed("txt_PrintReceiptTitle");
		String pageTitle = element("txt_PrintReceiptTitle").getText();
		logMessage("Step : page title is : " + pageTitle
				+ "receipt page is verified in txt_PrintReceiptTitle\n");
	}
}
