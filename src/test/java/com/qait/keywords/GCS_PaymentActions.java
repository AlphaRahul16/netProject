package com.qait.keywords;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.GetPage;

public class GCS_PaymentActions extends GetPage {
	WebDriver driver;
	static String url;
	static String pagename = "GCS_PaymentPage";
	
	
	public GCS_PaymentActions(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}	
	
	public void clickOnPaymentButtonNamedAs(String buttonName)
	{
		isElementDisplayed("rad_paymentType",buttonName);
		element("rad_paymentType",buttonName).click();
		wait.waitForPageToLoadCompletely();
		logMessage("Step : payment type is selected as "+buttonName+" \n");
	}
	
	public void fillBillingInformationAs(String placeholdername,String placeholdervalue)
	{
		isElementDisplayed("inp_billingInfo",placeholdername);
		element("inp_billingInfo").click();
		element("inp_billingInfo").sendKeys(placeholdervalue);
		logMessage("Step : Billing information in "+placeholdername+" is entered as "+placeholdervalue);
	}
	
	public void fillPaymentDetailsFor(String name,String value)
	{
		isElementDisplayed("inp_paymentDetails",name);
		element("inp_billingInfo").click();
		element("inp_paymentDetails",name).sendKeys(value);
		logMessage("Step : Payment details in "+name+" is entered as "+value);
	}
	
	public void enterExpirationMonth(int monthValue)
	{
		isElementDisplayed("drpdwn_expMonth");
		selectDropDownValue(element("drpdwn_expMonth"), monthValue);
		logMessage("Step : Expiration Months is entered as "+monthValue);
	}
	
	public void enterExpirationYear(int yearValue)
	{
		isElementDisplayed("drpdwn_expYear");
		selectDropDownValue(element("drpdwn_expYear"), yearValue);
		logMessage("Step : Expiration Months is entered as "+yearValue);
	}
	
	public void clickOnPayNowButton()
	{
		isElementDisplayed("btn_payNow");
		element("btn_payment").click();
		logMessage("Step : Pay Now button is clicked\n");
		wait.waitForPageToLoadCompletely();
	}
	
	public void clickSimulateTransactionButton()
	{
		isElementDisplayed("btn_payment","Simulate Transaction");
		element("btn_payment","Simulate Transaction").click();
		wait.hardWait(1);
		handleAlert();
		handleAlert();
		logMessage("Step : Simulate Transaction button is clicked\n");
	}
	
	public void EnterDetailsOnBankPaymentPageAndProcessFutherSimulation(String MobileNumber,String EmailId,String cardNumber,String NameOnCard
		,int expirationMonth,int expirationYear,String cvv,String BankName)
	{
		fillBillingInformationAs("Mobile Number",MobileNumber);
		fillBillingInformationAs("E-Mail Id",EmailId);
		fillPaymentDetailsFor("Credit Card Number",cardNumber);
		fillPaymentDetailsFor("Name on Card",cardNumber);
		enterExpirationMonth(expirationMonth);
		enterExpirationYear(expirationYear);
		fillPaymentDetailsFor("CVV / CVV2",cvv);
		fillPaymentDetailsFor("Bank Name",cvv);
		
		clickOnPayNowButton();
		clickSimulateTransactionButton();
		

	}
	
	
	

	

}
