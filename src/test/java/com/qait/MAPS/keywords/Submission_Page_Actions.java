package com.qait.MAPS.keywords;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class Submission_Page_Actions extends ASCSocietyGenericPage {

	WebDriver driver;
	static String pagename = "Submission_Page";


	public Submission_Page_Actions(WebDriver driver) {
		super(driver, pagename);

	}
	
	public void clickOnCreateNewSubmissionLink(String linkName)
	{
		isElementDisplayed("lnk_createSubmission",linkName);
		click(element("lnk_createSubmission",linkName));
		logMessage("Step : "+linkName+" link is clicked under View Submission tab");
	}
	
	
	public void verifyPageHeaderForASection(String header)
	{
			isElementDisplayed("txt_pageHeader");
			Assert.assertTrue(element("txt_pageHeader").getText().contains(header));
			logMessage("ASSERT PASSED: Page header is verified as "+header);
		}
	}
	
	

