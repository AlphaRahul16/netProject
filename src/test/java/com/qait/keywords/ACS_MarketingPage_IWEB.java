package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;
import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.ConfigPropertyReader;
import com.qait.automation.utils.DateUtil;

public class ACS_MarketingPage_IWEB extends ASCSocietyGenericPage {

	WebDriver driver;
	String pagename = "MarketingPage";
	String titleName, userName;
	boolean flag;

	public ACS_MarketingPage_IWEB(WebDriver driver) {
		super(driver, "MarketingPage");
		this.driver = driver;
	}

	public String getMailingListName() {
		return getYamlValue("mailingListName") + System.currentTimeMillis();

	}

	public void makeMailingListVisible() {
		// switchToFrame(1);
		switchToFrame("iframe1");
		if (!element("chk_showOnlineInListCategory").isSelected()) {
			element("chk_showOnlineInListCategory").click();
			logMessage("STEP : Show online checkbox is already selected\n");
		} else
			logMessage("STEP : Show online checkbox is already selected\n");
	}

	public void clickOnCancelButton() {
		isElementDisplayed("btn_cancelEditMailingList");
		clickUsingXpathInJavaScriptExecutor(element("btn_cancelEditMailingList"));
		logMessage("STEP : Clicked on Cancel button\n");
	}

	public boolean verifyVisibility(String listType, String current) {
		boolean flag = false;
		for (WebElement element : elements("list_categoriesInMailingList")) {
			if (element.getText().trim().equalsIgnoreCase(listType)) {
				clickUsingXpathInJavaScriptExecutor(element("arrow_selectListType", listType));
				wait.hardWait(3);
				makeMailingListVisible();
				clickOnCancelButton();
				wait.hardWait(2);
				// switchWindow(current);
				// switchToFrame(element("txt_heading"));
				switchToDefaultContent();
				flag = true;
				break;
			}
		}
		return flag;
	}

	public List<String> verifyVisibilityOfGivenListCategory(String listType) {
		List<String> categoryList = new ArrayList<String>();
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		wait.resetImplicitTimeout(5);
		wait.resetExplicitTimeout(hiddenFieldTimeOut);
		isElementDisplayed("list_pageLinks");
		int sizeOfPage = elements("list_pageLinks").size();
		System.out.println("page size:" + sizeOfPage);
		int page = 1;
		String current = driver.getWindowHandle();
		if (!verifyVisibility(listType, current)) {
			do {
				System.out.println("page number :" + page);
				page++;
				isElementDisplayed("link_page", String.valueOf(page));
				clickUsingXpathInJavaScriptExecutor(element("link_page", String.valueOf(page)));
				waitForSpinner();
				logMessage("STEP : Page number " + page + " is clicked\n");
				categoryList = getMailingCategoryList("mailing list type");
				if (verifyVisibility(listType, current)) {
					System.out.println("-----in if");
					break;
				}

			} while (page <= sizeOfPage);
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}
		return categoryList;
	}

	public void waitForSpinner() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			handleAlert();
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("img_spinner");
			logMessage("STEP : Wait for spinner to be disappeared \n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (Exception Exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (AssertionError Exp) {
			logMessage("STEP : Spinner is not present \n");
		}
	}

	public void verifyTitleNameForMailingListPopUpIsDisplayed() {
		wait.waitForPageToLoadCompletely();
		Assert.assertTrue(isElementDisplayed("txt_titleName"), "Title is not displayed for mailing list pop up");
		titleName = element("txt_titleName").getText();
		logMessage("ASSERT PASSED : Title is displayed as " + titleName);
	}

	public void sendNameInCreateMailingListPopUp(String listName) {
		isElementDisplayed("inptxt_mailingListName");
		element("inptxt_mailingListName").sendKeys(listName);
		logMessage("STEP : Mailing list name is entered as " + listName + "\n");
	}

	public void sendListTypeInCreateMailingListPopUp(String listType) {
		isElementDisplayed("drpdwn_mailingListType", listType);
		element("drpdwn_mailingListType", listType).click();
		logMessage("STEP : Mailing list type is entered as " + listType + "\n");
	}

	public void selectShowOnlineInCreateMailingListPopUp() {

		isElementDisplayed("chk_showOnline");
		// element("chk_showOnline").click();
		clickUsingXpathInJavaScriptExecutor(element("chk_showOnline"));
		logMessage("STEP : Show online Chechbox is selected\n");
	}

	public void sendStartDateInCreateMailingListPopUpWithFormat(String format) {
		isElementDisplayed("txt_startDate");
		element("txt_startDate").sendKeys(DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone(format,"EST"));
		logMessage("STEP : Start date is entered as " + DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone(format,"EST") + "\n");
	}

	public void sendEndDateInCreateMailingListPopUpWithFormat(String format) {
		isElementDisplayed("txt_endDate");
		element("txt_endDate").sendKeys(DateUtil.getAnyDateForType(format, 1, "month"));
		logMessage("STEP : End date filled is entered as " + DateUtil.getAnyDateForType(format, 1, "month"));

	}

	public void sendListInformationToMailingListPopUp(String listName, String listType) {
		// switchToFrame(1);
		switchToIframe1();
		sendNameInCreateMailingListPopUp(listName);
		sendListTypeInCreateMailingListPopUp(listType);
		selectShowOnlineInCreateMailingListPopUp();
		sendStartDateInCreateMailingListPopUpWithFormat("MM/dd/YYYY");
		sendEndDateInCreateMailingListPopUpWithFormat("MM/dd/YYYY");
		clickOnSaveButtonDisplayedOnMailingListPopUp();
	}

	public void switchToIframe1() {
		switchToFrame("iframe1");

	}
	
	public void clickOnSaveButtonDisplayedOnMailingList() {
		isElementDisplayed("btn_save");
		element("btn_save").click();		
//		clickUsingXpathInJavaScriptExecutor(element("btn_save"));
		wait.hardWait(5);
		logMessage("STEP : Save button is clicked\n");
		switchToDefaultContent();
	}

	public void clickOnSaveButtonDisplayedOnMailingListPopUp() {
		isElementDisplayed("btn_save");
//		element("btn_save").click();		
		clickUsingXpathInJavaScriptExecutor(element("btn_save"));
		wait.hardWait(5);
		logMessage("STEP : Save button is clicked\n");
		//pageRefresh();
		switchToDefaultContent();
	}

	public void verifyListNameInMailingListRecord(String listName) {
		hardWaitForIEBrowser(3);
		Assert.assertTrue(isElementDisplayed("txt_listName", listName), "Created List Name is not displayed");
		logMessage("ASSERT PASSED : List Name " + listName + " is displayed in the mailing list\n");
	}

	public String getUserNameFromAddUserPopUpTextField() {
		wait.hardWait(3);
		hardWaitForIEBrowser(4);
		isElementDisplayed("txt_name");
		userName = element("txt_name").getAttribute("value");
		logMessage("STEP : Added user name is " + userName + "\n");
		return userName;
	}

	public void gotoListFromMailingListRecord(String listName) {
		isElementDisplayed("txt_listName", listName);
		if (ConfigPropertyReader.getProperty("browser").equals("ie")
				|| ConfigPropertyReader.getProperty("browser").equals("internet explorer"))
			clickUsingXpathInJavaScriptExecutor(element("txt_listName", listName));
		else
			element("txt_listName", listName).click();
		logMessage("STEP : Click on Goto List for " + listName);

	}

	public void clickOnLookUpOption() {
//		switchToFrame(1);
		switchToFrame("iframe1");
		hardWaitForIEBrowser(2);
		wait.waitForElementToBeVisible(element("btn_searchLookup"));
		isElementDisplayed("btn_searchLookup");
		if (ConfigPropertyReader.getProperty("browser").equals("ie")
				|| ConfigPropertyReader.getProperty("browser").equals("internet explorer"))
			clickUsingXpathInJavaScriptExecutor(element("btn_searchLookup"));
		else
		    element("btn_searchLookup").click();
		wait.hardWait(5);
		logMessage("STEP : Clicked on Look up icon\n");
	}

	public void verifyUserNameInList() {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		MembershipPageActions_IWEB obj = new MembershipPageActions_IWEB(driver);
		obj.expandDetailsMenuIfAlreadyExpanded("list members");
		hardWaitForIEBrowser(4);
		Assert.assertTrue(isElementDisplayed("btn_ArrowProdName", userName));
		logMessage("ASSERT PASSED : Member " + userName + " is successfully added in the list");
	}

	public void gotoArrowOfGivenUser() {
		element("btn_ArrowProdName", userName).click();
		logMessage("STEP : Member " + userName + "is selected \n");
	}

	public void clickOnCancelButtonInCommunicationPreferencesPopUp() {
		isElementDisplayed("btn_cancelInComm.Pref");
		element("btn_cancelInComm.Pref").click();
		logMessage("STEP : Cancel button under Communication Preferences is clicked\n");
		changeWindow(0);
	}

	public void clickOnAdditionaInfortmationIcon(String infoName) {
		isElementDisplayed("btn_iconOnAdditionalInfo", infoName);
		element("btn_iconOnAdditionalInfo", infoName).click();
		logMessage("STEP : " + infoName + " button is clicked \n");
	}

	public void expandListTypeInComm_Pref(String listType) {
		changeWindow(1);
		isElementDisplayed("btn_listTypeInComm.Pref", listType);
		element("btn_listTypeInComm.Pref", listType).click();
		logMessage("STEP : List Type " + listType + " is expanded\n");
	}

	public void verifyMailingListIsDisplayedInExpandedListType(String listName) {
		Assert.assertTrue(isElementDisplayed("txt_listInComm.Pref", listName),
				"Mailing list is not displayed in given list type");
		logMessage("ASSERT PASSED : List name " + listName + "is displayed in given list type");
	}

	public void verifyMailingListIsSubscribedOrUnsubscribedInExpandedListType(String listName,
			String unsubscribed_subscribed) {
		verifyMailingListIsDisplayedInExpandedListType(listName);
		if (unsubscribed_subscribed.equals("unsubscribed"))
			flag = false;
		else
			flag = true;
		isElementDisplayed("chk_listInComm.Pref", listName);
		Assert.assertEquals(element("chk_listInComm.Pref", listName).isSelected(), flag);
		logMessage("ASSERT PASSED : Mailing list " + listName + " is " + unsubscribed_subscribed);
	}

	public void verifyListsInGivenCategoryIsUnsubscribed(String listType, Map<String, List<String>> mailingListMap) {
		flag = true;
		int index = 0;
		for (WebElement element : elements("list_allSubscription", listType)) {
			index++;
			if (mailingListMap.get(listType).contains(element.getText().trim())) {
				if (element("list_allMailsInListType", listType, String.valueOf(index)).isSelected()) {
					Assert.fail("Mailing list is subscribed !!");
					flag = false;
					break;
				}
			}
		}
		Assert.assertTrue(flag, "ASSERT FAILED : The mailing list " + listType + " is not unsubscribed\n");
		logMessage("ASSERT PASSED : All the mailing lists under " + listType + " are unsubscribed\n");
	}

	// public void verifyAllListIsSubscribed(List<String> list) {
	// changeWindow(1);
	//
	// for (int i = 0; i < list.size(); i++) {
	// expandListTypeInComm_Pref(list.get(i));
	// verifyListsInGivenCategoryIsUnsubscribed(list.get(i));
	// }
	// logMessage("Step : Verified All Mailing List Is Unsubscribed");
	// clickOnCancelButtonInCommunicationPreferencesPopUp();
	// }

	public List<String> getMailingCategoryList(String tabName) {
		List<String> categoryList = new ArrayList<String>();
		isElementDisplayed("list_categoriesInMailingList");
		for (int i = 1; i <= elements("list_categoriesInMailingList").size(); i++) {
			if (!element("txt_listData", tabName, String.valueOf(5), String.valueOf(i)).getText().equals(" ")) {
				categoryList
						.add(element("txt_listData", tabName, String.valueOf(4), String.valueOf(i)).getText().trim());
			}
		}
		System.out.println("list is" + categoryList);
		return categoryList;
	}

	public void verifyAllListIsUnsubscribedOnIweb(Map<String, List<String>> mailingListMap, List<String> list) {
		changeWindow(1);
		for (int i = 0; i < list.size(); i++) {
			if (mailingListMap.containsKey(list.get(i))) {
				expandListTypeInComm_Pref((list.get(i)));
				verifyListsInGivenCategoryIsUnsubscribed(list.get(i), mailingListMap);
			}
		}
		logMessage("STEP : Verified All Mailing List Is Unsubscribed");
		clickOnCancelButtonInCommunicationPreferencesPopUp();
	}

}
