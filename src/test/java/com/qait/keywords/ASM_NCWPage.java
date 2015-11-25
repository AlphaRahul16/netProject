package com.qait.keywords;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.GetPage;

public class ASM_NCWPage extends GetPage {
	WebDriver driver;
	static String pagename = "ASM_NCWPage";

	public ASM_NCWPage(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

	public void enterZipCode(String zipCode) {
		isElementDisplayed("inp_zipCode");
		element("inp_zipCode").clear();
		element("inp_zipCode").sendKeys(zipCode);
		logMessage("Step : " + zipCode + " is entered in inp_zipCode\n");
	}

	public void clickFindNCWCoordinator() {
		isElementDisplayed("inp_findNCWCoordinator");
		click(element("inp_findNCWCoordinator"));
		logMessage("Step : find NCW coordinator button is clicked in inp_findNCWCoordinator\n");
	}

	public void waitForCCEDSpinnerToDisappear() {
		isElementDisplayed("img_spinner");
		wait.waitForElementToDisappear(element("img_spinner"));
	}

	public void enterZipCodeAndFindNCWCoordinator(String zipCode) {
		enterZipCode(zipCode);
		clickFindNCWCoordinator();
	}

	public void verifyContactCordinatorIsPresent() {
		isElementDisplayed("lnk_contactCordinator");
		logMessage("ASSERT PASSED : contact cordinator is present in lnk_contactCordinator\n");
	}

	public void verifyZipCodeErrorMessage(String errorMessage) {
		try {
			isElementDisplayed("txt_zipCodeErrorMsz");
			verifyElementText("txt_zipCodeErrorMsz", errorMessage);
		} catch (StaleElementReferenceException stlExp) {
			isElementDisplayed("txt_zipCodeErrorMsz");
			verifyElementText("txt_zipCodeErrorMsz", errorMessage);
		}

	}

}
