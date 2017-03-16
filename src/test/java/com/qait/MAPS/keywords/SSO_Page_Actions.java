package com.qait.MAPS.keywords;

import org.openqa.selenium.WebDriver;

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
		if(element("txt_MAPS_title").getText().trim().contains(title)?flag=true:flag);
			
		
		
	}



}
