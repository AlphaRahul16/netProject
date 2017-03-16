
package com.qait.MAPS.keywords;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class SSO_Page_Actions extends ASCSocietyGenericPage {
	WebDriver driver;
	static String pagename = "SSO_Page";


	public SSO_Page_Actions(WebDriver driver) {
		super(driver, pagename);

	}


	public void verifyUserIsOnMAPSHomePage(String title) {
		boolean flag=false;
		isElementDisplayed("txt_MAPS_title");
		System.out.println(element("txt_MAPS_title").getText().trim());
		Assert.assertTrue(element("txt_MAPS_title").getText().trim().contains(title),"User is not on MAPS homepage");
		logMessage("ASSERT PASSED : User is on MAPS home page");

	}
	
	public void loginWithValidCredentials(String userid, String password)
	{
		enterLoginFields("");
		
		clickLoginButton();
	}


	private void clickLoginButton() {
		// TODO Auto-generated method stub
		
	}


	private void enterLoginFields(String fieldname) {
		
		
	}



}
