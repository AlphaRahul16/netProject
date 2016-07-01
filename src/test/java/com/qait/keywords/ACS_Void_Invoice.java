package com.qait.keywords;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class ACS_Void_Invoice extends ASCSocietyGenericPage {

	static String pageName="ACS_Void_Invoice";
	public ACS_Void_Invoice(WebDriver driver) {
		super(driver, pageName);
	}
	
	public void clickOnVoidInvoiceButton(String btnName,int index){
		isElementDisplayed("btn_invoiceAction",String.valueOf(index));
		element("btn_invoiceAction",String.valueOf(index)).click();
		logMessage("STEP : Clicked on "+btnName+" button\n");
	}

}
