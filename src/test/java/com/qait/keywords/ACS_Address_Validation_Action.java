package com.qait.keywords;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class ACS_Address_Validation_Action extends ASCSocietyGenericPage{
	static String pagename = "MembershipPage";
	WebDriver driver;

	public ACS_Address_Validation_Action(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}
	
	public void clickOnTab(String tabName) {
		isElementDisplayed("link_tabsOnModule", tabName);
		element("link_tabsOnModule", tabName).click();
		logMessage("STEP : " + tabName + " tab is clicked\n");
	}
}
