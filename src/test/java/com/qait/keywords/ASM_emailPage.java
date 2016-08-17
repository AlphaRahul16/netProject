package com.qait.keywords;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class ASM_emailPage extends GetPage {
	WebDriver driver;
	String pagename = "ASM_emailPage";
	static boolean flag = false;
	String[] productNames;

	public ASM_emailPage(WebDriver driver) {
		super(driver, "ASM_emailPage");
		this.driver = driver;
	}

	public List<String> getAllCategoryOfMailingList() {
		List<String> list = new ArrayList<String>();
		for (WebElement element : elements("list_mailingListCategory")) {
			if (element.getText().equals(
					"American Association of Chemistry Teachers")) {
				list.add("AACT");
			} else {
				list.add(element.getText());
			}
		}
		return list;
	}

	public void verifyMailingListInNewspaperHeading(String listName) {
		Assert.assertTrue(
				isElementDisplayed("btn_newslettedHeading", listName),
				"Mailing list is not displayed in NewsPaper heading");
		logMessage("ASSERT PASSED : " + listName
				+ " is displayed in newspaper heading !!\n");
	}

	public String getNewsletterActionValue(String listName) {
		isElementDisplayed("btn_newsletterAction", listName);
		return element("btn_newsletterAction", listName).getText();
	}

	public void verifyMailListIsSubscribed(String listName) {
		String actionValue = getNewsletterActionValue(listName);
		logMessage("Step : News letter action value " + actionValue
				+ " is retrieved !!");
		Assert.assertEquals(actionValue, "unsubscribe");
		logMessage("ASSERT PASSED : verify user is subscribed !!");
	}

	public void changeNewsLetterActionValue(String listName) {
		isElementDisplayed("btn_newsletterAction", listName);
		element("btn_newsletterAction", listName).click();
		
		if(element("btn_newsletterAction", listName).getText().equals("subscribe"))
			logMessage("Step: unsubscribe the mailing list !!\n");
		else
			logMessage("Step : subscribe the mailing list !!\n");
		//wait.hardWait(3);
		handleAlert();
		
	}

	public void loginInToApplication(String username, String password) {
		enterCredencial("username", username);
		enterCredencial("password", password);
		clickOnLoginButton();
	}

	public void enterCredencial(String fieldName, String fieldValue) {
		isElementDisplayed("inp_" + fieldName);
		element("inp_" + fieldName).clear();
		element("inp_" + fieldName).sendKeys(fieldValue);
		logMessage("Step : " + fieldValue + " is entered in " + fieldName
				+ " \n");
	}

	public void clickOnLoginButton() {
		isElementDisplayed("btn_login");
		click(element("btn_login"));
		logMessage("Step : login button is clicked in btn_login\n");
	}

	public void verifyLoginErrorMessagePresent(String errorMessage) {
		isElementDisplayed("txt_loginErrorMessage", errorMessage);
		verifyElementTextContains("txt_loginErrorMessage", errorMessage);
		logMessage("ASSERT PASSED : Error message " + errorMessage
				+ " is appeared on invalid credecials\n");
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
		logMessage("Step : click on first change link to select the email id");
	}

	public void selectEmailInDropDownWithIndex(int index) {
		isElementDisplayed("drpdwn_selectEmail");
		selectDropDownValue(element("drpdwn_selectEmail"), index);
		logMessage("Step : select email from drop down using index " + index
				+ " \n");
	}

	public String getFirstACSSubscribedProductName() {
		isElementDisplayed("txt_ACSProductName");
		String firstAcsSubscribedProductName = element("txt_ACSProductName")
				.getText();
		logMessage("Step : First ACS subscribed name is : "
				+ firstAcsSubscribedProductName + " \n");
		return firstAcsSubscribedProductName;

	}

	public void verifyUnscubscribeSuccessfully(String productName) {
		isElementDisplayed("btn_ACSSubscribe_unsubscribe", productName);
		element("btn_ACSSubscribe_unsubscribe", productName).getText()
				.equalsIgnoreCase("unsubscribe");
		logMessage("ASSERT PASSED : unsubscribe successfully\n");
	}

	public void clickOnFirstSubscribeButton() {
		isElementDisplayed("btn_subscribe");
		click(element("btn_subscribe"));
		logMessage("Step : click on first subscribe button\n");
	}

	public void clickOnUnsubscribeAllButton() {
		try {
			isElementDisplayed("btn_unsubscribeAll");
			click(element("btn_unsubscribeAll"));
			logMessage("Step : unsubscribe all button is clicked\n");
		} catch (Exception exp) {
			clickOnFirstSubscribeButton();
			isElementDisplayed("btn_unsubscribeAll");
			click(element("btn_unsubscribeAll"));
			logMessage("Step : unsubscribe all button is clicked\n");
		}
	}

	public void clickOnUnsubscribeAllConfirmButton() {
		isElementDisplayed("btn_unsubscribeConfirm");
		click(element("btn_unsubscribeConfirm"));
		logMessage("Step : unsubscribe all confirm button is clicked\n");
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
		logMessage("ASSERT PASSED : unsubscribe all on click unsubscribe all button");
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

	public void enterNewInvalidEmailAndVerifyFailMessage(String newMail,
			String failMessage) {
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
		logMessage("Step : new button is clicked to add new email \n");

	}

	public void clickOnCancelButton() {
		isElementDisplayed("btn_cancel");
		click(element("btn_cancel"));
		logMessage("Step : cancel button is clicked \n");
	}

	public void enterNewEmail(String newEmail) {
		isElementDisplayed("inp_newEmail");
		element("inp_newEmail").sendKeys(newEmail);
		logMessage("Step : " + newEmail + " is enetered in inp_newEmail\n");
	}

	public void clickOnAddButton() {
		isElementDisplayed("btn_addNewMail");
		click(element("btn_addNewMail"));
		logMessage("Step : add button is clicked in btn_addNewMail");
	}

	public void verifyEmailAddedSuccessfully(String productname,
			String addedEmail) {
		isElementDisplayed("txt_addedEmail", productname);
		Assert.assertTrue(element("txt_addedEmail", productname).getText()
				.equalsIgnoreCase(addedEmail));
		logMessage("ASSERT PASSED : added email " + addedEmail
				+ " is verified\n");
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
			productNames[i] = elements("txt_selectedProductName").get(i)
					.getText();
		}

		return productNames;
	}

	public String getSelectedSubCategory(String productName) {
		isElementDisplayed("txt_categoryName", productName);
		String dataCategoryId = element("txt_categoryName", productName)
				.getAttribute("data-category-id");
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
				logMessage("Step : First select all check box is clicked");
				String dataCategoryId = getSelectedSubCategory(productName);
				for (WebElement subCategory : elements("txt_subscribeToAll",
						dataCategoryId)) {
					flag = subCategory.getAttribute("class").equalsIgnoreCase(
							"teaser  ir");
					if (!flag) {
						Assert.fail("ASSERT FAILED : On check select all button subscription not to be done successfully\n");
					}
				}
				Assert.assertTrue(flag);
				logMessage("ASSERT PASSED : on check select all button subscribe to all sub categories\n");
			} else {
				String productName = selectedProductname()[1];
				String dataCategoryId = getSelectedSubCategory(productName);
				for (WebElement subCategory : elements("txt_subscribeToAll",
						dataCategoryId)) {
					flag = subCategory.getAttribute("class").equalsIgnoreCase(
							"teaser  ir");
					if (!flag) {
						Assert.fail("ASSERT FAILED : On check select all button subscription not to be done successfully\n");
					}
				}
				Assert.assertTrue(flag);
				logMessage("ASSERT PASSED : on check select all button subscribe to all sub categories\n");
			}
			break;
		}
	}

}
