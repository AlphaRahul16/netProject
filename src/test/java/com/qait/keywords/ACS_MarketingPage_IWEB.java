package com.qait.keywords;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.DateUtil;

public class ACS_MarketingPage_IWEB extends ASCSocietyGenericPage{

	WebDriver driver;
	String pagename = "MarketingPage";
	String titleName,userName;
	boolean flag;
	
	public ACS_MarketingPage_IWEB(WebDriver driver)
	{
		super(driver,"MarketingPage");
		this.driver=driver;
	}
	
	
	public String getMailingListName()
	{
		return getYamlValue("mailingListName")
				+ System.currentTimeMillis();
		
	}
	
	public void verifyTitleNameForMailingListPopUpIsDisplayed() {
		Assert.assertTrue(isElementDisplayed("txt_titleName"),"Title is not displayed for mailing list pop up");
		titleName=element("txt_titleName").getText();
		logMessage("ASSERT PASSED : title is displayed as "+titleName);
	}

	
	public void sendNameInCreateMailingListPopUp(String listName)
	{
		isElementDisplayed("inptxt_mailingListName");
		element("inptxt_mailingListName").sendKeys(listName);
		logMessage("Step : Mailing list name is entered as "+listName+"\n");
	}

    public void sendListTypeInCreateMailingListPopUp(String listType)
    {
    	isElementDisplayed("drpdwn_mailingListType", listType);
		element("drpdwn_mailingListType", listType).click();
		logMessage("Step : Mailing list typeis entered as "+listType+"\n");
    }
	
	public void selectShowOnlineInCreateMailingListPopUp()
	{
		isElementDisplayed("chk_showOnline");
		element("chk_showOnline").click();
		logMessage("Step : Show online Chechbox is clickied\n");
	}
    
	public void sendStartDateInCreateMailingListPopUpWithFormat(String format)
	{
		isElementDisplayed("txt_startDate");
		element("txt_startDate").sendKeys(
				DateUtil.getCurrentdateInStringWithGivenFormate(format));
		logMessage("Step : Start date is entered as "+DateUtil.getCurrentdateInStringWithGivenFormate(format)+"\n");	
	}
	
   public void 	sendEndDateInCreateMailingListPopUpWithFormat(String format)
   {
	   isElementDisplayed("txt_endDate");
		element("txt_endDate").sendKeys(
				DateUtil.getAnyDateForType(format, 1, "month"));
		logMessage("Step : End date filled in create mailiung list pop up !!\n");  
	   
   }
    
	public void sendListInformationToMailingListPopUp(String listName, String listType) {
		switchToFrame(1);
		sendNameInCreateMailingListPopUp(listName);
		sendListTypeInCreateMailingListPopUp(listType);
		selectShowOnlineInCreateMailingListPopUp();
		sendStartDateInCreateMailingListPopUpWithFormat("MM/dd/YYYY");
		sendEndDateInCreateMailingListPopUpWithFormat("MM/dd/YYYY");
		clickOnSaveButtonDisplayedOnMailingListPopUp();
	}

	public void clickOnSaveButtonDisplayedOnMailingListPopUp()
	{
		isElementDisplayed("btn_save");
		element("btn_save").click();
		logMessage("Step : Save button is clicked\n");
		switchToDefaultContent();
	}
	
	public void verifyListNameInMailingListRecord(String listName)
	{
		Assert.assertTrue(isElementDisplayed("txt_listName",listName),"Created List Name is not displayed");
		logMessage("ASSERT PASSED : List Name "+listName+" is displayed in the mailing list\n");
	}

	
	public void gotoListFromMailingListRecord(String listName)
	{
		isElementDisplayed("txt_listName",listName);
		element("txt_listName",listName).click();
		logMessage("Step : Click on Goto List for "+listName);
		
	}

	public void clickOnLookUpOption()
	{
		    switchToFrame(1);
			wait.waitForElementToBeVisible(element("btn_searchLookup"));
			isElementDisplayed("btn_searchLookup");
			element("btn_searchLookup").click();
			wait.hardWait(5);
			logMessage("STEP : Clicked on Look up icon\n");
	}
	
	
	
	public String getUserNameFromAddUserPopUpTextField()
	{
		isElementDisplayed("txt_name");
		userName=element("txt_name").getAttribute("value");
		logMessage("Step : Added user name is "+userName+"\n");
		return userName;
		
	}
	
	public void verifyUserNameInList()
	{
		MembershipPageActions_IWEB obj = new MembershipPageActions_IWEB(driver);
		obj.expandDetailsMenuIfAlreadyExpanded("list members");
		Assert.assertTrue(isElementDisplayed("btn_ArrowProdName",userName));
		logMessage("ASSERT PASSED : Member "+userName+" is successfully added in the list");
	}
	
	public void gotoArrowOfGivenUser()
	{
		element("btn_ArrowProdName",userName).click();
		logMessage("Step : Member "+userName+"is selected \n");
		
	}
	
	public void clickOnCancelButtonInCommunicationPreferencesPopUp()
	{
		isElementDisplayed("btn_cancelInComm.Pref");
		element("btn_cancelInComm.Pref").click();
		logMessage("Step : Cancel button under Communication Preferences is clicked\n");
		changeWindow(0);
	}
	
	public void clickOnAdditionaInfortmationIcon(String infoName)
	{
		isElementDisplayed("btn_iconOnAdditionalInfo",infoName);
		element("btn_iconOnAdditionalInfo",infoName).click();
		logMessage("Step : "+infoName+" button is clicked \n");
	}
	
	public void expandListTypeInComm_Pref(String listType)
	{
		changeWindow(1);
		isElementDisplayed("btn_listTypeInComm.Pref",listType);
		element("btn_listTypeInComm.Pref",listType).click();
		logMessage("Step : list Type "+listType+" is expanded\n");
	}
	
	public void verifyMailingListIsDisplayedInExpandedListType(String listName)
	{
		Assert.assertTrue(isElementDisplayed("txt_listInComm.Pref",listName),"Mailing list is not displayed in given list type");
		logMessage("ASSERT PASSED : list name "+listName+"is displayed in given list type !!");
	}
	
	
	public void verifyMailingListIsSubscribedOrUnsubscribedInExpandedListType(String listName,String unsubscribed_subscribed)
	{
		verifyMailingListIsDisplayedInExpandedListType(listName);
		if(unsubscribed_subscribed.equals("unsubscribed"))
			flag=false;
		else
			flag=true;
		isElementDisplayed("chk_listInComm.Pref",listName);
		Assert.assertEquals(element("chk_listInComm.Pref",listName).isSelected(), flag);
		logMessage("ASSERT PASSED : mailing list is "+unsubscribed_subscribed);
	}
	

	public void verifyListsInGivenCategoryIsUnsubscribed(String listType)
	{
		flag=true;
		for(WebElement element:elements("list_allMailsInListType",listType))
		{
			if(element.isSelected())
			{
				Assert.fail("Mailing list is subscribed !!");
				flag=false;
				break;
			}
		}
		Assert.assertTrue(flag,"ASSERT FAILED : The mailing list is not unsubscribed\n");
		logMessage("ASSERT PASSED : All the mailing lists are unsubscribed\n");
	}
	
	
	
	public void verifyAllListIsSubscribed(List<String> list)
	{
		changeWindow(1);
		
		for(int i=0;i<list.size();i++)
		{
			expandListTypeInComm_Pref(list.get(i));
			verifyListsInGivenCategoryIsUnsubscribed(list.get(i));
		}
		logMessage("Step : Verified All Mailing List Is Unsubscribed");
		clickOnCancelButtonInCommunicationPreferencesPopUp();
	}
	
	
	
	
	
	
	
	
	
	
}
