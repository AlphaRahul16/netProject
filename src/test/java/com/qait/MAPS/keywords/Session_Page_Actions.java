package com.qait.MAPS.keywords;

import org.apache.tools.ant.taskdefs.condition.IsLastModified;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.thoughtworks.selenium.webdriven.commands.IsElementPresent;

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
        element("lnk_selButton",buttonName).click();
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
	
	public void clickSubHeadingLeftNavigationPanel(String headingName){
//		wait.hardWait(5);
		waitForLoaderToDisappear();
		isElementDisplayed("lnk_sessionTypes",headingName);
//		click(element("lnk_sessionTypes",headingName));
		clickUsingXpathInJavaScriptExecutor(element("lnk_sessionTypes",headingName));
		logMessage("Step : "+headingName+" sub-heading is clicked\n");
	}
	
	public void verifySectionsOnRoomAvailabilityPage(String sectionName,int index){
		isElementDisplayed("heading_sectionName",sectionName,String.valueOf(index));
		logMessage("ASSERT PASSED: "+sectionName+" section is present on Rooms Availability page\n");
	}
	
	public void clickOnSaveAndEditButton(String btnName, int index){
		isElementDisplayed("btn_saveAndEdit",btnName,String.valueOf(index));
		click(element("btn_saveAndEdit",btnName,String.valueOf(index)));
		logMessage("Step : Clicked on "+btnName+" button\n");
	}
	
	public void enterRoomNameOnSaveGridConfiguration(String fieldName,String roomName){
		isElementDisplayed("inp_roomName",fieldName);
		element("inp_roomName",fieldName).sendKeys(roomName);
		logMessage("Step : "+fieldName+" is entered as "+roomName+"\n");
	}
	
	public void clickCheckboxOnSaveGridConfiguration(String fieldname){
		isElementDisplayed("chkbox_room",fieldname);
		click(element("chkbox_room",fieldname));
		logMessage("Step : Clicked on "+fieldname+" checkbox\n");
	}
	
	public void selectRoleOnSaveGridConfiguration(String role){
		isElementDisplayed("select_role",role);
		click(element("select_role",role));
		logMessage("Step : Role is selected as "+role+"\n");
	}
	
	public void verifyCreatedFilterIsByDefaultSelected(String filterName){
		((JavascriptExecutor) driver).executeScript("$x('(//div[contains(@class,\'x-form-field-wrap\') and @role=\"combobox\"])[2]')");
	}
	
	public void verifyFieldsOnRoomAvailablityPage(String fieldName,int index){
		isElementDisplayed("btn_saveAndEdit",fieldName,String.valueOf(index));
		logMessage("ASSERT PASSED: "+fieldName+" field is present on Rooms Availability page\n");
	}
	
	public void verifyFilterDropdwonOnRoomAvailabalityPage(){
		isElementDisplayed("drpdwn_filters");
		logMessage("ASSERT PASSED: Filter dropdwon is displayed on Room Availability page\n");
	}
	
	public void clickOnArrowButton(String label){
		wait.hardWait(5);
        isElementDisplayed("btn_navPanel",label);
        hover(element("btn_navPanel",label));
		isElementDisplayed("btn_arrow",label);
		click(element("btn_arrow",label));
		logMessage("Step : Clicked on arrow button next to "+label+"\n");
	}


}
