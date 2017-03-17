package com.qait.MAPS.keywords;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class Review_Page_Actions extends ASCSocietyGenericPage {

	WebDriver driver;
	static String pagename = "Review_Page";

	public Review_Page_Actions(WebDriver driver) {
		super(driver, pagename);

	}

	public void verifyPageHeader(String header) {
		// TODO Auto-generated method stub
		isElementDisplayed("txt_header", header);
		logMessage("ASSERT PASSED: Verified page header as " + header + "\n");
	}

	public void selectRoleForReview(String role) {
		// TODO Auto-generated method stub
		isElementDisplayed("radiobtn_role",role);
		click(element("radiobtn_role",role));
		logMessage("STEP: "+role+" is selected for review \n ");
	}

}
