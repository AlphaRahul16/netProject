package com.qait.keywords;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class HomePageActions extends ASCSocietyGenericPage {

	WebDriver driver;
	String pagename = "HomePage";
	boolean flag;

	public HomePageActions(WebDriver driver) {
		super(driver, "HomePage");
		this.driver = driver;
	}

	public void verifyUserIsOnHomePage(String pageTitle) {
		verifyPageTitleExact(pageTitle);
		isElementDisplayed("txt_pageHeader");
		logMessage("ASSERT PASSED: verified that user is on " + this.pagename
				+ "\n");
	}

	public void waitForTabToDissappear(String tabName) {
		wait.waitForElementToDisappear(element("txt_tabName", tabName));
	}

	public boolean verifyCurrentTab(String tabName) {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(10);
		try {
			return isElementDisplayed("txt_tabName", tabName);
		} catch (StaleElementReferenceException stlExp) {
			return isElementDisplayed("txt_tabName", tabName);
		}

	}

	public void clickOnTab(String tabName) {
		click(element("lnk_tab", tabName));
		logMessage("Step : click on tab " + tabName + "\n");

	}

	public void waitForCurrentTabToDisappear(String tabName) {
		wait.waitForElementToDisappear(element("txt_tabName", tabName));
	}

	public void verifyAboutYouPage(String caseId) {
	
		wait.waitForPageToLoadCompletely();
		if (getAACT_OmaSheetValue(caseId, "Has About You Page?")
				.equalsIgnoreCase("null")) {
			logMessage("Step : value is not entered in the data sheet");
		}
		flag = verifyCurrentTab("About You");
		if (getAACT_OmaSheetValue(caseId, "Has About You Page?")
				.equalsIgnoreCase("Y")) {
			Assert.assertTrue(flag);
		} else if (getAACT_OmaSheetValue(caseId, "Has About You Page?")
				.equalsIgnoreCase("N")) {
			Assert.assertFalse(flag, "About page is not expected\n");

		}
	
	}

	public void verifyConfirmationPage(String caseId) {
		wait.waitForPageToLoadCompletely();
		if (getAACT_OmaSheetValue(caseId, "Has Confirmation Page?")
				.equalsIgnoreCase("null")) {
			logMessage("Step : value is not entered in the data sheet");
		}
		flag = verifyCurrentTab("Confirmation");
		if (getAACT_OmaSheetValue(caseId, "Has Confirmation Page?")
				.equalsIgnoreCase("Y")) {
			Assert.assertTrue(flag);
		} else if (getAACT_OmaSheetValue(caseId, "Has Confirmation Page?")
				.equalsIgnoreCase("N")) {
			Assert.assertFalse(flag, "Confirmation page is not expected\n");
		}
	}

	public void verifyCheckoutPage(String caseId) {
		wait.waitForPageToLoadCompletely();
		if (getAACT_OmaSheetValue(caseId, "Has Checkout Page?")
				.equalsIgnoreCase("null")) {
			logMessage("Step : value is not entered in the data sheet");
		}
		flag = verifyCurrentTab("Checkout");
		if (getAACT_OmaSheetValue(caseId, "Has Checkout Page?")
				.equalsIgnoreCase("Y")) {
			Assert.assertTrue(flag);
		} else if (getAACT_OmaSheetValue(caseId, "Has Checkout Page?")
				.equalsIgnoreCase("N")) {
			Assert.assertFalse(flag, "Checkout page is not expected\n");
		}

	}


}
