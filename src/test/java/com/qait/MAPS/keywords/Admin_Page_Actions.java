package com.qait.MAPS.keywords;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class Admin_Page_Actions extends ASCSocietyGenericPage {

	WebDriver driver;
	static String pagename = "Admin_Page";

	public Admin_Page_Actions(WebDriver driver) {
		super(driver, pagename);
	}
	
	public void clickLeftNavigationPanelOptions(String fieldName)
	{
		isElementDisplayed("lnk_leftPanelInstructions",fieldName);
		click(element("lnk_leftPanelInstructions",fieldName));
		logMessage("Step : "+fieldName+" is clicked on left navigation panel\n");
	}
	
	public void enterUserDetailsToAdd(String name,String value)
	{
		isElementDisplayed("inp_usrDetails",name);
		EnterTextInField(element("inp_usrDetails",name), value);
		logMessage("Step : user "+name+" is entered as "+value);
	}
}
