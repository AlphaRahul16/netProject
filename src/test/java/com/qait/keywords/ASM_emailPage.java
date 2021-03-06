package com.qait.keywords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class ASM_emailPage extends GetPage {
	WebDriver driver;
	String pagename = "ASM_emailPage";
	static boolean flag = false;
	String[] productNames;
	List<String> mailingCategories=new ArrayList<>();

	public ASM_emailPage(WebDriver driver) {
		super(driver, "ASM_emailPage");
		this.driver = driver;
	}

	public List<String> getAllCategoryOfMailingList() {
		List<String> list = new ArrayList<String>();
		for (WebElement element : elements("list_mailingListCategory")) {
			if (element.getText().equals("American Association of Chemistry Teachers")) {
				list.add("AACT");
			} else {
				list.add(element.getText());
			}
		}
		System.out.println(list);
		return list;
	}

	public void verifyMailingListInNewspaperHeading(String listName) {
		wait.hardWait(5);
		Assert.assertTrue(isElementDisplayed("btn_newslettedHeading", listName),
				"Mailing list is not displayed in NewsPaper heading");
		logMessage("ASSERT PASSED : " + listName + " list is displayed in newspaper heading \n");
	}

	public String getNewsletterActionValue(String listName) {
		isElementDisplayed("btn_newsletterAction", listName);
		return element("btn_newsletterAction", listName).getText();
	}

	public void verifyMailListIsSubscribed(String listName) {
		String actionValue = getNewsletterActionValue(listName);
		if (actionValue.equals("unsubscribe"))
			flag = true;
		else
			flag = false;
		Assert.assertTrue(flag,
				"ASSERT FAILED : The current status of the mailing list " + listName + " is unsubscribed \n");
		logMessage("ASSERT PASSED : The current status of the mailing list " + listName + " is subscribed \n");
	}

	public void changeNewsLetterActionValue(String listName) {
		isElementDisplayed("btn_newsletterAction", listName);
		wait.hardWait(3);
		if (element("btn_newsletterAction", listName).getText().equals("subscribe")){
			element("btn_newsletterAction", listName).click();
			logMessage("STEP : Clicked on Subscribe button\n");
			logMessage("STEP : The mailing list is now subscribed\n");
		}	
		else{
			element("btn_newsletterAction", listName).click();
			logMessage("STEP : Clicked on Unsubscribe button\n");
			logMessage("STEP : The mailing list is now unsubscribed\n");
		}
		handleAlert();

	}

	public void loginInToApplication(String username, String password) {
		enterCredential("username", username);
		enterCredential("password", password);
		clickOnLoginButton();
	}

	public void enterCredential(String fieldName, String fieldValue) {
		isElementDisplayed("inp_" + fieldName);
		element("inp_" + fieldName).clear();
		element("inp_" + fieldName).sendKeys(fieldValue);
		logMessage("STEP : " + fieldValue + " is entered in " + fieldName + " \n");
	}

	public void clickOnLoginButton() {
		isElementDisplayed("btn_login");
		// click(element("btn_login"));
		element("btn_login").click();
		logMessage("STEP : Login button is clicked\n");
	}

	public void verifyLoginErrorMessagePresent(String errorMessage) {
		isElementDisplayed("txt_loginErrorMessage", errorMessage);
		verifyElementTextContains("txt_loginErrorMessage", errorMessage);
		logMessage("ASSERT PASSED : Error message " + errorMessage + " is appeared on invalid credecials\n");
	}

	public void verifyLoginSuccessfully() {
		// isElementDisplayed("lnk_editAccount");

		isElementDisplayed("txt_selectCategory");
	}

	public void selectEmailIdAndVerifySubscribedAutomatically() {
		clickOnFirstChangeLink();
		String firstSubscribeName = getFirstACSSubscribedProductName();
		selectEmailInDropDownWithIndex(2);
		verifyUnscubscribeSuccessfully(firstSubscribeName);
	}

	public void clickOnFirstChangeLink() {
		isElementDisplayed("lnk_change");
		click(element("lnk_change"));
		logMessage("STEP : click on first change link to select the email id");
	}

	public void selectEmailInDropDownWithIndex(int index) {
		isElementDisplayed("drpdwn_selectEmail");
		selectDropDownValue(element("drpdwn_selectEmail"), index);
		logMessage("STEP : select email from drop down using index " + index + " \n");
	}

	public String getFirstACSSubscribedProductName() {
		isElementDisplayed("txt_ACSProductName");
		String firstAcsSubscribedProductName = element("txt_ACSProductName").getText();
		logMessage("STEP : First ACS subscribed name is : " + firstAcsSubscribedProductName + " \n");
		return firstAcsSubscribedProductName;

	}

	public void verifyUnscubscribeSuccessfully(String productName) {
		isElementDisplayed("btn_ACSSubscribe_unsubscribe", productName);
		element("btn_ACSSubscribe_unsubscribe", productName).getText().equalsIgnoreCase("unsubscribe");
		logMessage("ASSERT PASSED : Unsubscribe successfully\n");
	}

	public void clickOnFirstSubscribeButton() {
		isElementDisplayed("btn_subscribe");
		click(element("btn_subscribe"));
		logMessage("STEP : Click on first subscribe button\n");
	}

	public void clickOnUnsubscribeAllButton() {
		try {
			isElementDisplayed("btn_unsubscribeAll");
			element("btn_unsubscribeAll").click();
			logMessage("STEP : Unsubscribe all button is clicked\n");
		} catch (Exception exp) {
			clickOnFirstSubscribeButton();
			wait.hardWait(1);
			isElementDisplayed("btn_unsubscribeAll");
			element("btn_unsubscribeAll").click();
			logMessage("STEP : Unsubscribe all button is clicked\n");
		}
	}

	public void clickOnUnsubscribeAllConfirmButton() {
		isElementDisplayed("btn_unsubscribeConfirm");
//		click(element("btn_unsubscribeConfirm"));
		element("btn_unsubscribeConfirm").click();
		logMessage("STEP : Unsubscribe all confirm button is clicked\n");
		handleAlert();
	}

	public void verifyUnsubscribeOnclickingUnsubscribeAllButton() {
		flag = false;
		clickOnUnsubscribeAllButton();
		clickOnUnsubscribeAllConfirmButton();
		isElementDisplayed("list_subscribe");
		for (WebElement element : elements("list_subscribe")) {
			if (element.getText().equalsIgnoreCase("subscribe")) {
				flag = true;
			} else {
				flag = false;
			}
		}
		Assert.assertTrue(flag);
		logMessage("ASSERT PASSED : Unsubscribe all on click unsubscribe all button");
	}

	public void VerifyFirstSubscribed_UnsubscribedSuccessfully() {
		String firstSubscribedProductname = getFirstACSSubscribedProductName();
		clickOnFirstSubscribeButton();
		verifyUnscubscribeSuccessfully(firstSubscribedProductname);

	}

	public void addedNewMailAndVerified(String newEmail) {
		clickOnFirstChangeLink();
		String firstAcsSubscribedProductname = getFirstACSSubscribedProductName();
		clickOnNewMail();
		String uniqueEmail = System.currentTimeMillis() + newEmail;
		enterNewEmail(uniqueEmail);
		clickOnAddButton();
		verifyEmailAddedSuccessfully(firstAcsSubscribedProductname, uniqueEmail);

	}

	public void enterNewInvalidEmailAndVerifyFailMessage(String newMail, String failMessage) {
		clickOnFirstChangeLink();
		clickOnNewMail();
		enterNewEmail(newMail);
		clickOnAddButton();
		switchWindow();
		verifyFailMessageIsAppeared(failMessage);
		clickOnCancelButton();
	}

	public void clickOnNewMail() {
		isElementDisplayed("btn_newMail");
		click(element("btn_newMail"));
		logMessage("STEP : New button is clicked to add new email \n");

	}

	public void clickOnCancelButton() {
		isElementDisplayed("btn_cancel");
		click(element("btn_cancel"));
		logMessage("STEP : Cancel button is clicked \n");
	}

	public void enterNewEmail(String newEmail) {
		isElementDisplayed("inp_newEmail");
		element("inp_newEmail").sendKeys(newEmail);
		logMessage("STEP : " + newEmail + " is enetered in inp_newEmail\n");
	}

	public void clickOnAddButton() {
		isElementDisplayed("btn_addNewMail");
		click(element("btn_addNewMail"));
		logMessage("STEP : Add button is clicked in btn_addNewMail");
	}

	public void verifyEmailAddedSuccessfully(String productname, String addedEmail) {
		isElementDisplayed("txt_addedEmail", productname);
		Assert.assertTrue(element("txt_addedEmail", productname).getText().equalsIgnoreCase(addedEmail),
				"ASSERT FAILED: Expected added email is "+addedEmail+" but found "+element("txt_addedEmail", productname).getText());
		logMessage("ASSERT PASSED : Added email " + addedEmail + " is verified\n");
	}

	public void verifyFailMessageIsAppeared(String failedMessage) {
		isElementDisplayed("txt_failMessage");
		System.out.println(element("txt_failMessage").getText());
		System.out.println(failedMessage);
		verifyElementText("txt_failMessage", failedMessage);
	}

	public String[] selectedProductname() {
		isElementDisplayed("txt_selectedProductName");
		productNames = new String[elements("txt_selectedProductName").size()];
		for (int i = 0; i < elements("txt_selectedProductName").size(); i++) {
			productNames[i] = elements("txt_selectedProductName").get(i).getText();
		}

		return productNames;
	}

	public String getSelectedSubCategory(String productName) {
		isElementDisplayed("txt_categoryName", productName);
		String dataCategoryId = element("txt_categoryName", productName).getAttribute("data-category-id");
		return dataCategoryId;
	}

	public void checkFirstUnsubscribeSelectAllButtonAndVerifySubscribeToAllSubCategory() {
		isElementDisplayed("chk_selectAll");
		for (WebElement checkbox : elements("chk_selectAll")) {
			String checked = checkbox.getAttribute("checked");
			if (checked == null) {
				String productName = selectedProductname()[0];
				// click(checkbox);
				checkbox.click();
				logMessage("STEP : First select all check box is clicked");
				String dataCategoryId = getSelectedSubCategory(productName);
				for (WebElement subCategory : elements("txt_subscribeToAll", dataCategoryId)) {
					flag = subCategory.getAttribute("class").equalsIgnoreCase("teaser  ir");
					if (!flag) {
						Assert.fail(
								"ASSERT FAILED : On check select all button subscription not to be done successfully\n");
					}
				}
				Assert.assertTrue(flag);
				logMessage("ASSERT PASSED : On check select all button subscribe to all sub categories\n");
			} else {
				String productName = selectedProductname()[1];
				String dataCategoryId = getSelectedSubCategory(productName);
				for (WebElement subCategory : elements("txt_subscribeToAll", dataCategoryId)) {
					flag = subCategory.getAttribute("class").equalsIgnoreCase("teaser  ir");
					if (!flag) {
						Assert.fail(
								"ASSERT FAILED : On check select all button subscription not to be done successfully\n");
					}
				}
				Assert.assertTrue(flag);
				logMessage("ASSERT PASSED : On check select all button subscribe to all sub categories\n");
			}
			break;
		}
	}
	
	public Map<String,List<String>> getMailingTypesList(List<String> categoryList){
		Map<String,List<String>> mailingListMap=new HashMap<String,List<String>>();
		List<String> subscriptionList;
		for(String list: categoryList){
			if (list.equals("AACT")){
				clickOnMailingCategory("American Association of Chemistry Teachers");
			}
			else
			    clickOnMailingCategory(list);
			subscriptionList=new ArrayList<>();
			if(checkIfElementIsThere("heading_mailingLists")){
			for(WebElement elem: elements("heading_mailingLists")){
				subscriptionList.add(elem.getText());
				mailingCategories.add(list);
			}
			logMessage("STEP : Mailing lists are present\n");
			mailingListMap.put(list, subscriptionList);
		  }
			else{
//				subscriptionList.add("");
//				mailingListMap.put(list,subscriptionList);
				logMessage("STEP: Mailing list is not present\n");
			}					
		}	
		System.out.println("----map is:"+mailingListMap);
		return mailingListMap;
	}
	
	public void clickOnMailingCategory(String list){
		wait.hardWait(2);
		isElementDisplayed("btn_mailingCategory",list);
		element("btn_mailingCategory",list).click();
		logMessage("STEP : Clicked on "+list+" mailing type button");
	}

}
