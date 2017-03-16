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
		enterLoginFields("LOGIN_USER_ID",userid);
		enterLoginFields("PASSWORD",password);
		clickLoginButton();
	}


	private void clickLoginButton() {
	isElementDisplayed("btn_Login");
	click(element("btn_Login"));
	logMessage("Step : Log In Button is clicked on SSO Page");
		
	}


	private void enterLoginFields(String fieldname ,String fieldvalue) {
		isElementDisplayed("inp_fields",fieldname);
		EnterTextInField(element("inp_fields",fieldname),fieldvalue );
		logMessage("Step : "+fieldname+" on SSO Page is entered as "+fieldvalue);
		
	}



}
