package com.qait.MAPS.keywords;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class Review_Page_Actions extends ASCSocietyGenericPage {

	WebDriver driver;
	static String pagename = "Review_Page";


	public Review_Page_Actions(WebDriver driver) {
		super(driver, pagename);

	}

}
