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

	public void clickNamedRadioButtonOnRoleSelectionPage(String role) {
             isElementDisplayed("rdbtn_userrole",role);
             element("rdbtn_userrole",role).click();
             logMessage("Step : "+role+" button is selected on Multiple role selection page\n");
             wait.waitForPageToLoadCompletely();
	}
	
	public void clickButtonToContinueToNextPage(String buttonName)
	{
        isElementDisplayed("lnk_selButton",buttonName);
        element("rdbtn_userrole",buttonName).click();
        logMessage("Step : "+buttonName+" button is clicked\n");
	}
	
	public void clickButtononLeftNavigationPanel(String buttonName)
	{
        isElementDisplayed("btn_navPanel",buttonName);
        element("btn_navPanel",buttonName).click();
        logMessage("Step : "+buttonName+" button is clicked on left navigation panel\n");
	}
	
	public void verifyApplicationShouldDisplayOptionsOnAbstractPage(String options)
	{
		
	}
	
	
	
	


}
