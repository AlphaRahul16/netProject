package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;
import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
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
		switchToFrame(1);
		if (!element("chk_showOnlineInListCategory").isSelected()) {
			element("chk_showOnlineInListCategory").click();
		}
		switchToDefaultContent();
	}

	public boolean verifyVisibility(String listType) {
		boolean flag = false;
		for (WebElement element : elements("list_categoriesInMailingList")) {
			if (element.getText().trim().equalsIgnoreCase(listType)) {
				element("arrow_selectListType").click();
				wait.hardWait(3);
				makeMailingListVisible();
				flag = true;
				break;
			}
		}
		return flag;
	}

	public void verifyVisibilityOfGivenListCategory(String listType) {
		MembershipPageActions_IWEB obj = new MembershipPageActions_IWEB(driver);
		obj.expandDetailsMenu("mailing list type");
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		try {

			wait.resetImplicitTimeout(5);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("list_pageLinks");
			int sizeOfPage = elements("list_pageLinks").size() ;
			System.out.println("page size:" + sizeOfPage);
			int page = 0;
			int pageCount = page + 1;
			
			if (!verifyVisibility(listType)) {
				do {
					System.out.println("page number :" + page);
					page++;
					isElementDisplayed("link_page", String.valueOf(page + 1));
					// element("link_paging", String.valueOf(page + 1)).click();
					clickUsingXpathInJavaScriptExecutor(element("link_page",
							String.valueOf(page + 1)));
					waitForSpinner();
					logMessage("Step : page number " + page + 1
							+ " is clicked\n");
					System.out.println("page no.:" + page);
					if (verifyVisibility(listType))
						break;
				} while (pageCount < sizeOfPage);
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
			}
		} catch (Exception exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			verifyVisibility(listType);
			logMessage("Step : Paging is not present for awards selecting\n");
		}

	}

	public void waitForSpinner() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		try {
			handleAlert();
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("img_spinner");
			logMessage("STEP : wait for spinner to be disappeared \n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (Exception Exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (AssertionError Exp) {
			logMessage("STEP : spinner is not present \n");
		}
	}

	public void verifyTitleNameForMailingListPopUpIsDisplayed() {
		System.out.println("hello !!!");
		Assert.assertTrue(isElementDisplayed("txt_titleName"),
				"Title is not displayed for mailing list pop up");
		titleName = element("txt_titleName").getText();
		logMessage("ASSERT PASSED : title is displayed as " + titleName);
	}

	public void sendNameInCreateMailingListPopUp(String listName) {
		isElementDisplayed("inptxt_mailingListName");
		element("inptxt_mailingListName").sendKeys(listName);
		logMessage("Step : send list name " + listName
				+ " in name field of in create mailiung list pop up!!\n");
	}

	public void sendListTypeInCreateMailingListPopUp(String listType) {
		isElementDisplayed("drpdwn_mailingListType", listType);
		element("drpdwn_mailingListType", listType).click();
		logMessage("Step : Enter list type as " + listType
				+ " in create mailiung list pop up\n");
	}

	public void selectShowOnlineInCreateMailingListPopUp() {
		isElementDisplayed("chk_showOnline");
		element("chk_showOnline").click();
		logMessage("Step : Click on Show online Chechbox in create mailiung list pop up!!\n");
	}

	public void sendStartDateInCreateMailingListPopUpWithFormat(String format) {
		isElementDisplayed("txt_startDate");
		element("txt_startDate").sendKeys(
				DateUtil.getCurrentdateInStringWithGivenFormate(format));
		logMessage("Step : Start date filled in create mailiung list pop up!!\n");
	}

	public void sendEndDateInCreateMailingListPopUpWithFormat(String format) {
		isElementDisplayed("txt_endDate");
		element("txt_endDate").sendKeys(
				DateUtil.getAnyDateForType(format, 1, "month"));
		logMessage("Step : End date filled in create mailiung list pop up !!\n");

	}

	public void sendListInformationToMailingListPopUp(String listName,
			String listType) {
		switchToFrame(1);
		sendNameInCreateMailingListPopUp(listName);
		sendListTypeInCreateMailingListPopUp(listType);
		selectShowOnlineInCreateMailingListPopUp();
		sendStartDateInCreateMailingListPopUpWithFormat("MM/dd/YYYY");
		sendEndDateInCreateMailingListPopUpWithFormat("MM/dd/YYYY");
		clickOnSaveButtonDisplayedOnMailingListPopUp();

	}

	public void clickOnSaveButtonDisplayedOnMailingListPopUp() {
		isElementDisplayed("btn_save");
		element("btn_save").click();
		logMessage("Step : Click on save button in create mailiung list pop up !!\n");
		switchToDefaultContent();
	}

	public void verifyListNameInMailingListRecord(String listName) {
		Assert.assertTrue(isElementDisplayed("txt_listName", listName),
				"Created List Name is not displayed");
		logMessage("ASSERT PASSED : List Name " + listName + "is Displayed !! ");
	}

	public void gotoListFromMailingListRecord(String listName) {
		isElementDisplayed("txt_listName", listName);
		element("txt_listName", listName).click();
		logMessage("Step : Click on Goto List for " + listName);

	}

	public void clickOnLookUpOption() {
		switchToFrame(1);
		wait.waitForElementToBeVisible(element("btn_searchLookup"));
		isElementDisplayed("btn_searchLookup");
		element("btn_searchLookup").click();
		wait.hardWait(5);
		logMessage("STEP : Clicked on Look up icon\n");
	}

	public String getUserNameFromAddUserPopUpTextField() {
		isElementDisplayed("txt_name");
		userName = element("txt_name").getAttribute("value");
		logMessage("Step : Added user name " + userName + "is retrieved !!\n");
		return userName;

	}

	public void verifyUserNameInList() {
		MembershipPageActions_IWEB obj = new MembershipPageActions_IWEB(driver);
		obj.expandDetailsMenu("list members");
		Assert.assertTrue(isElementDisplayed("btn_ArrowProdName", userName));
		logMessage("ASSERT PASSED : verify userName in List");
	}

	public void gotoArrowOfGivenUser() {
		element("btn_ArrowProdName", userName).click();
		logMessage("Step : Arrow with " + userName + "is clicked !!");

	}

	public void clickOnCancelButtonInCommunicationPreferencesPopUp() {
		isElementDisplayed("btn_cancelInComm.Pref");
		element("btn_cancelInComm.Pref").click();
		logMessage("Step : click on cancel button under Communication Preferences");
		changeWindow(0);
	}

	public void clickOnAdditionaInfortmationIcon(String infoName) {
		isElementDisplayed("btn_iconOnAdditionalInfo", infoName);
		element("btn_iconOnAdditionalInfo", infoName).click();
		logMessage("Step : btn with additional info " + infoName
				+ " is clicked !!\n");
	}

	public void expandListTypeInComm_Pref(String listType) {
		changeWindow(1);
		isElementDisplayed("btn_listTypeInComm.Pref", listType);
		element("btn_listTypeInComm.Pref", listType).click();
		logMessage("Step : list Type " + listType + "is expanded !!\n");
	}

	public void verifyMailingListIsDisplayedInExpandedListType(String listName) {
		Assert.assertTrue(isElementDisplayed("txt_listInComm.Pref", listName),
				"Mailing list is not displayed in given list type");
		logMessage("ASSERT PASSED : list name " + listName
				+ "is displayed in given list type !!");
	}

	public void verifyMailingListIsSubscribedOrUnsubscribedInExpandedListType(
			String listName, String unsubscribed_subscribed) {
		verifyMailingListIsDisplayedInExpandedListType(listName);
		if (unsubscribed_subscribed.equals("unsubscribed"))
			flag = false;
		else
			flag = true;
		isElementDisplayed("chk_listInComm.Pref", listName);
		Assert.assertEquals(element("chk_listInComm.Pref", listName)
				.isSelected(), flag);
		logMessage("ASSERT PASSED : mailing list is " + unsubscribed_subscribed);
	}

	public void verifyListsInGivenCategoryIsUnsubscribed(String listType) {
		for (WebElement element : elements("list_allMailsInListType", listType)) {
			if (element.isSelected()) {
				Assert.fail("Mailing list is subscribed !!");
			}
		}
	}

	public void verifyAllListIsSubscribed(List<String> list) {
		changeWindow(1);

		for (int i = 0; i < list.size(); i++) {
			expandListTypeInComm_Pref(list.get(i));
			verifyListsInGivenCategoryIsUnsubscribed(list.get(i));
		}
		logMessage("ASSERT PASSED : Verified All Mailing List Is Unsubscribed !!");
		clickOnCancelButtonInCommunicationPreferencesPopUp();
		changeWindow(0);
	}

}
