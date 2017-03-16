package com.qait.MAPS.keywords;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class Submission_Page_Actions extends ASCSocietyGenericPage {

	WebDriver driver;
	static String pagename = "Submission_Page";


	public Submission_Page_Actions(WebDriver driver) {
		super(driver, pagename);

	}

}
