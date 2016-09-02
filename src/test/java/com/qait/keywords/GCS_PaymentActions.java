package com.qait.keywords;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.getpageobjects.GetPage;

public class GCS_PaymentActions extends ASCSocietyGenericPage {
	WebDriver driver;
	static String url;
	static String pagename = "GCS_PaymentPage";

	public GCS_PaymentActions(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

	public void clickOnPaymentButtonNamedAs(String buttonName) {
		isElementDisplayed("btn_payment", buttonName);
		element("btn_payment", buttonName).click();
		wait.waitForPageToLoadCompletely();
		logMessage("Step : payment type is selected as " + buttonName + " \n");
	}

	
	public void fillBillingInformationAs(String placeholdername,String placeholdervalue)
	{
		isElementDisplayed("inp_billingInfo",placeholdername);
		element("inp_billingInfo",placeholdername).click();
		element("inp_billingInfo",placeholdername).sendKeys(placeholdervalue);
		logMessage("Step : Billing information in "+placeholdername+" is entered as "+placeholdervalue);
	}
	
	public void fillPaymentDetailsFor(String name,String value)
	{
		
		isElementDisplayed("inp_paymentDetails",name);
		element("inp_paymentDetails",name).click();
		wait.hardWait(1);
		element("inp_paymentDetails",name).sendKeys(value);
		logMessage("Step : Payment Details for "+name+" is entered as "+value);

	}
	public void enterCardNumber(String cardName,String value)
	{
	
		isElementDisplayed("inp_CardNumber",cardName);
		element("inp_CardNumber",cardName).click();
		wait.hardWait(1);
		element("inp_CardNumber",cardName).sendKeys(value);
		logMessage("Step : Card Number is entered as "+value);
	}

	public void enterExpirationMonth(String monthValue,String cardType) {
		System.out.println(cardType);
		isElementDisplayed("drpdwn_expMonth",cardType);
		selectProvidedTextFromDropDown(element("drpdwn_expMonth",cardType), monthValue);
		logMessage("Step : Expiration Months is entered as " + monthValue);
	}

	public void enterExpirationYear(String yearValue,String cardType) {
		isElementDisplayed("drpdwn_expYear",cardType);
		selectProvidedTextFromDropDown(element("drpdwn_expYear",cardType), yearValue);
		logMessage("Step : Expiration Months is entered as " + yearValue);
	}

	public void clickOnPayNowButton() {
		isElementDisplayed("btn_payNow");
		element("btn_payNow").click();
		logMessage("Step : Pay Now button is clicked\n");
		wait.waitForPageToLoadCompletely();
	}

	public void clickSimulateTransactionButton(String value) {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(3);
		isElementDisplayed("inp_submit", value);
		element("inp_submit", value).click();
		wait.hardWait(1);
		handleAlert();
		handleAlert();
		logMessage("Step : Simulate Transaction button is clicked\n");
	}

	public void EnterDetailsOnBankPaymentPageAndProcessFutherSimulation(
			String paymentType, String MobileNumber, String EmailId,
			String CreditcardNumber,String debitCardNumber, String NameOnCard, String expirationMonth,
			String expirationYear, String cvv, String BankName) {
		wait.hardWait(4);
		wait.waitForPageToLoadCompletely();
		fillBillingInformationAs("Mobile Number", MobileNumber);
		fillBillingInformationAs("E-Mail Id", EmailId);
         if (paymentType.contains("Banking")) {
			
        	 selectMajorBankRadioButtonForPaymentThroughNetBanking();
        	 selectBankNameFromListForPaymentThroughNetBanking(BankName);

		}
         else
         {
         if (paymentType.contains("Credit")) {
			enterCardNumber("Credit Card Number", CreditcardNumber);
		} else if (paymentType.contains("Debit")) {
			enterCardNumber("Debit Card Number",debitCardNumber);
		}
            fillPaymentDetailsFor("Name on Card", BankName);
			enterExpirationMonth(expirationMonth,paymentType.substring(0,1).toLowerCase());
			enterExpirationYear(expirationYear,paymentType.substring(0,1).toLowerCase());
			fillPaymentDetailsFor("CVV / CVV2", cvv);
			fillPaymentDetailsFor("Bank Name", BankName);

	}
         clickOnPayNowButton();
         if(paymentType.contains("Banking"))
         {
        	 
        	 clickSimulateTransactionButton("Click To Transfer Funds");
         }
         else
         {
        	 clickSimulateTransactionButton("Simulate Transaction"); 
         }
     	  	
	}
	



private void selectMajorBankRadioButtonForPaymentThroughNetBanking()
{
	isElementDisplayed("rad_majorBankId");
	element("rad_majorBankId").click();
	logMessage("Step : Major Bank radio button is selected\n");
}

private void selectBankNameFromListForPaymentThroughNetBanking(String BankName)
{
	isElementDisplayed("drpdwn_BankName");
	selectProvidedTextFromDropDown(element("drpdwn_BankName"), BankName);
	logMessage("Step : "+BankName+" is selected from list on Names\n");
}
}