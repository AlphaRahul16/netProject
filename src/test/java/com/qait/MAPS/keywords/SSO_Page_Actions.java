package com.qait.MAPS.keywords;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class SSO_Page_Actions extends ASCSocietyGenericPage {
	WebDriver driver;
	static String pagename = "SSO_Page";


	public SSO_Page_Actions(WebDriver driver) {
		super(driver, pagename);

	}



}
