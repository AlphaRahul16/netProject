package com.qait.keywords;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;



import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.getpageobjects.GetPage;

public class ASM_CCEDPage  extends ASCSocietyGenericPage{
	WebDriver driver;
	static String pagename = "ASM_CCEDPage";

	public ASM_CCEDPage(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

	public void enterZipCode(String zipCode) {
		isElementDisplayed("inp_zipCode");
		element("inp_zipCode").clear();
		element("inp_zipCode").sendKeys(zipCode);
		logMessage("Step : " + zipCode + " is entered in inp_zipCode\n");
	}

	public void selectSearchTypeAndNavigateToFindCoordinator(String searchType) {
		isElementDisplayed("inp_searchType", searchType);
		element("inp_searchType").click();
		logMessage("Step : select search type is " + searchType + "\n");
		if(searchType.equalsIgnoreCase("State")){
			selectStateAndFindCCEDCoordinator(map().get("searchValue"));
		}else{
			enterZipCodeAndFindCCEDCoordinator(map().get("searchValue"));
		}

	}

	public void clickFindCCEDCoordinator() {
		isElementDisplayed("inp_findCCEDCoordinator");
		click(element("inp_findCCEDCoordinator"));
		logMessage("Step : find cced coordinator button is clicked in inp_findCCEDCoordinator\n");
	}

	public void waitForCCEDSpinnerToDisappear() {
		isElementDisplayed("img_spinner");
		wait.waitForElementToDisappear(element("img_spinner"));
	}

	public void enterZipCodeAndFindCCEDCoordinator(String zipCode) {
		enterZipCode(zipCode);
		clickFindCCEDCoordinator();
	}

	public void selectStateAndFindCCEDCoordinator(String state) {
		isElementDisplayed("adr_state");
		selectProvidedTextFromDropDown(element("adr_state"), state);
		clickFindCCEDCoordinator();
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

	public void verifyPageTitle(String pageTitle) {
		isElementDisplayed("txt_title");
		verifyElementText("txt_title", pageTitle);
	}

}
