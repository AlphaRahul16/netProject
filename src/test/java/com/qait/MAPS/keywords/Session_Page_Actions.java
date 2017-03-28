package com.qait.MAPS.keywords;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class Session_Page_Actions extends ASCSocietyGenericPage {

	WebDriver driver;
	static String pagename = "Session_Page";

	public Session_Page_Actions(WebDriver driver) {
		super(driver, pagename);
	}

	public void verifyApplicationDisplaysRadioButtonOnClickingSessionTab(String[] role) {
		int i=0;
		isElementDisplayed("txt_userrole");
		for (WebElement ele : elements("txt_userrole")) {
			Assert.assertTrue(ele.getText().equals(role[i]), "user role "+role+" is not displayed on application\n");
			logMessage("ASSERT PASSED : verified user role "+role+" is displayed on session page\n");
			i++;
		}
	}

	private void verifyNamedRadioButtonOnSessionPage(String role) {
		
		
		
		
		
	}
	
	


}
