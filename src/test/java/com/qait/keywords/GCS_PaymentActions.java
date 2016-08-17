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
		isElementDisplayed("btn_payment",buttonName);
		element("btn_payment",buttonName).click();
		wait.waitForPageToLoadCompletely();
		logMessage("Step : click Procced with Payment button\n");
	}
	

	

}
