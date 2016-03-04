package com.qait.keywords;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class ASM_CCEDPage extends ASCSocietyGenericPage {
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

	public void selectSearchTypeAndNavigateToCoordinator(String searchType) {
		isElementDisplayed("inp_searchType", searchType);
		element("inp_searchType", searchType).click();
		logMessage("Step : select search type is " + searchType + "\n");
		if (searchType.equalsIgnoreCase("State")) {
			selectStateAndFindCCEDCoordinator(map().get("searchValue"));
			clickViewCoordinatorsFromThisSection(map().get("localSectionID"));
			verifyCoordinatorName(map().get("coordinatorName"));
			clickContactCoordinator();

		} else {
			enterZipCodeAndFindCCEDCoordinator(map().get("searchValue"));
			verifyCoordinatorName(map().get("coordinatorName"));
			clickContactCoordinator();
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
		isElementDisplayed("select_state");
		selectProvidedTextFromDropDown(element("select_state"), state);
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

	public void clickViewCoordinatorsFromThisSection(String localSectionId) {
		isElementDisplayed("lnk_viewCoordinator", localSectionId);
		element("lnk_viewCoordinator", localSectionId).click();
		logMessage("STEP : View Coordinator From This Section for " + localSectionId + " is clicked\n");
	}

	public void clickContactCoordinator() {
		verifyContactCordinatorIsPresent();
		element("lnk_contactCordinator").click();
		logMessage("Step : Link Contact Coordinator is clicked\n");
	}

	public void verifyCoordinatorName(String coordinatorName) {
		isElementDisplayed("txt_CoordinatorName", coordinatorName);
		logMessage("ASSERT PASSED : Coordinator's Name is " + coordinatorName + " verified");
	}

	public void FillOutFormToContactCoordinatorAndClickSubmit(String fname, String lname, String emailid, String city,
			String phone) {
		enterMemberContactDetail(fname, map().get("emailFirstName"));
		enterMemberContactDetail(lname, map().get("emailLastName"));
		enterMemberContactDetail(emailid, map().get("emailID"));
		enterMemberContactDetail(city, map().get("emailCity"));
		enterMemberContactDetail(phone, map().get("emailPhone"));
		selectState(map().get("emailState"));
		enterQuestionsComments(map().get("emailText"));
		isElementDisplayed("btn_submit");
		element("btn_submit").click();
		logMessage("Step : Details Entered into form and thus Submit Button is clicked\n");
	}

	public void enterQuestionsComments(String emailText) {
		isElementDisplayed("txt_questionsComments");
		element("txt_questionsComments").clear();
		element("txt_questionsComments").sendKeys(emailText);
		logMessage("Step: " + emailText + " entered in " + " Questions/Comments \n");
	}

	private void enterMemberContactDetail(String detailType, String detailValue) {
		if (!detailValue.equalsIgnoreCase("null")) {
			isElementDisplayed("inp_formDetails", detailType);
			element("inp_formDetails", detailType).clear();
			element("inp_formDetails", detailType).sendKeys(detailValue);
			logMessage("Step: " + detailValue + " is entered in the " + detailType + "\n");
		} else {
			logMessage("Error : " + detailType + "is not present in data sheet\n");
		}

	}

	public void selectState(String state) {
		isElementDisplayed("select_state");
		selectProvidedTextFromDropDown(element("select_state"), state);
		logMessage("Step: " + state + " entered in the State select option\n");
	}

	public void VerifyThankyouMessage() {
		isElementDisplayed("txt_thankYouMessage");
		Assert.assertTrue(element("txt_thankYouMessage").getText().trim()
				.equalsIgnoreCase("Thank you. Your form has been submitted."));
		logMessage("ASSERT PASSED : thank you message Thank you. Your form has been submitted. is verified\n");
	}

}
