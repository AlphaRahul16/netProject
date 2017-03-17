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
		Assert.assertTrue(element("txt_MAPS_title").getText().trim().contains(title),"User is not on MAPS homepage");
		logMessage("ASSERT PASSED : User is on MAPS home page\n");

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
	logMessage("Step : Log In Button is clicked on SSO Page\n");
		
	}


	private void enterLoginFields(String fieldname ,String fieldvalue) {
		isElementDisplayed("inp_fields",fieldname);
		EnterTextInField(element("inp_fields",fieldname),fieldvalue );
		logMessage("Step : "+fieldname+" on SSO Page is entered as "+fieldvalue+"\n");
		
	}
	
	public void clickOnTabOnUpperNavigationBar(String tabName)
	{
		isElementDisplayed("btn_nav_Heading",tabName);
		click(element("btn_nav_Heading",tabName));
		logMessage("Step : "+tabName+" on upper navigation bar is clicked\n");
	}

	
	public void verifyUserIsOnTabPage(String tabName){
		isElementDisplayed("tab_homePage",tabName);
		logMessage("ASSERT PASSED : User is navigated to "+tabName+" page\n");
	}
	
	public void clickOnUserProfileName(){
		isElementDisplayed("tab_accountProfile");
		click(element("tab_accountProfile"));
		logMessage("Step: Clicked on User's profile name");
	}
	
	public void clickOnUserInformationLink(String lnkName){
		isElementDisplayed("lnk_userInfo",lnkName);
		click(element("lnk_userInfo",lnkName));
		logMessage("Step : Clicked on "+lnkName+" link\n");
	}
	
	public void clickOnLeftMenuInformationLink(String lnkName){
		isElementDisplayed("lnk_leftUserInfo",lnkName);
		click(element("lnk_leftUserInfo",lnkName));
		logMessage("Step : Clicked on "+lnkName+" left navigation menu\n");
	}
	
	public void verifyPageHeader(String headerName){
		isElementDisplayed("txt_infoHeader");
		Assert.assertTrue(element("txt_infoHeader").getText().contains(headerName));
		logMessage("ASSERT PASSED: Page header is verified as "+headerName+"\n");
	}

}
