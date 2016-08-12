package com.qait.keywords;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.DateUtil;

public class ACS_MarketingPage_IWEB extends ASCSocietyGenericPage{

	WebDriver driver;
	String pagename = "MarketingPage";
	String titleName,userName;
	
	public ACS_MarketingPage_IWEB(WebDriver driver)
	{
		super(driver,"MarketingPage");
		this.driver=driver;
	}
	
	public void verifyTitleNameForMailingListPopUp() {
		isElementDisplayed("txt_titleName");
		titleName=element("txt_titleName").getText();
		logMessage("ASSERT PASSED : title "+titleName+" is verified !!");
	}


	public void sendListInformationToMailingListPopUp(String listName, String listType) {

		switchToFrame(element("frame"));
		isElementDisplayed("inptxt_mailingListName");
		element("inptxt_mailingListName").sendKeys(listName);
		logMessage("Step : send list name "+listName+" in name field !!");
		isElementDisplayed("drpdwn_mailingListType", listType);
		element("drpdwn_mailingListType", listType).click();
		logMessage("Step : Enter list type as "+listType);
		isElementDisplayed("chk_showOnline");
		element("chk_showOnline").click();
		logMessage("Step : Click on Show online Chechbox!!");
		isElementDisplayed("txt_startDate");
		element("txt_startDate").sendKeys(
				DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/YYYY"));
		logMessage("Step : Start date filled !!");
		isElementDisplayed("txt_endDate");
		element("txt_endDate").sendKeys(
				DateUtil.getAnyDateForType("MM/dd/YYYY", 1, "month"));
		logMessage("Step : End date filled !!");
		
	}

	public void clickOnSaveButtonDisplayedOnMailingListPopUp()
	{
		isElementDisplayed("btn_save");
		element("btn_save").click();
		logMessage("Step : Click on save button !!");
		switchToDefaultContent();
	}
	
	public void verifyListNameInMailingListRecord(String listName)
	{
		isElementDisplayed("txt_listName",listName);
		logMessage("ASSERT PASSED : List Name "+listName+"is Displayed !! ");
	}

	
	public void gotoListFromMailingListRecord(String listName)
	{
		element("txt_listName",listName).click();
		logMessage("Step : Goto List !!");
		
	}

	public void clickOnLookUpOption()
	{
		    switchToFrame(element("frame"));
			wait.waitForElementToBeVisible(element("btn_searchLookup"));
			isElementDisplayed("btn_searchLookup");
			element("btn_searchLookup").click();
			wait.hardWait(5);
			logMessage("STEP : Clicked on Look up icon");
	}
	
	public void clickOnRandomUser()
	{
		element("lnk_next").click();
		MembershipPageActions_IWEB obj = new MembershipPageActions_IWEB(driver);
		obj.clickOnRandomPage();
		wait.hardWait(4);
		obj.clickOnAnyRandomMember();
		wait.hardWait(4);
		
	}
	
	public String getUserNameFromAddUserPopUpTextField()
	{
		isElementDisplayed("txt_name");
		userName=element("txt_name").getAttribute("value");
		logMessage("Step : Added user name "+userName+"is retrieved !!");
		return userName;
		
	}
	
	public void verifyUserNameInList()
	{
		MembershipPageActions_IWEB obj = new MembershipPageActions_IWEB(driver);
		obj.expandDetailsMenu("list members");
		isElementDisplayed("btn_ArrowProdName",userName);
	}
	
	public void gotoArrowOfGivenUser()
	{
		element("btn_ArrowProdName",userName).click();
		logMessage("Step : Arrow with "+userName+"is clicked !!");
		
	}
	
	public void clickOnCancelButton()
	{
		isElementDisplayed("btn_cancelInComm.Pref");
		element("btn_cancelInComm.Pref").click();
		logMessage("Step : click on cancel button under Communication Preferences");
	}
	
	public void clickOnAdditionaInfortmationIcon(String infoName)
	{
		isElementDisplayed("btn_iconOnAdditionalInfo",infoName);
		element("btn_iconOnAdditionalInfo",infoName).click();
		logMessage("Step : btn with additional info "+infoName+" is clicked !!");
	}
	
	public void expandListTypeInComm_Pref(String listType)
	{
		changeWindow(1);
		isElementDisplayed("btn_listTypeInComm.Pref",listType);
		element("btn_listTypeInComm.Pref",listType).click();
		logMessage("Step : list Type "+listType+"is expanded !!");
	}
	
	public void verifyMailingListIsSubscribed(String listName)
	{
		isElementDisplayed("txt_listInComm.Pref",listName);
		logMessage("Step : list name "+listName+"is displayed in given list type !!");
		isElementDisplayed("chk_listInComm.Pref",listName);
		Assert.assertEquals(element("chk_listInComm.Pref",listName).getAttribute("checked"), "true");
		logMessage("ASSERT PASSED : mailing list is subscribed !!");
		clickOnCancelButton();
		changeWindow(0);
	}
	public void verifyMailingListIsUnSubscribed(String listName)
	{
		isElementDisplayed("txt_listInComm.Pref",listName);
		logMessage("Step : list name "+listName+"is displayed in given list type !!");
		isElementDisplayed("chk_listInComm.Pref",listName);
		Assert.assertEquals(element("chk_listInComm.Pref",listName).getAttribute("checked"), "false");
		logMessage("ASSERT PASSED : mailing list is Unsubscribed !!");
		clickOnCancelButton();
		changeWindow(0);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
