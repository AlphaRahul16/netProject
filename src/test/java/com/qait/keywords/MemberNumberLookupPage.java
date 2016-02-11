package com.qait.keywords;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.GetPage;

public class MemberNumberLookupPage extends GetPage {
	WebDriver driver;
	static String pagename = "ACS_MemberNumberLookupPage";

	public MemberNumberLookupPage(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

	public void enterMemberDetail(String detailName, String detailValue) {
		isElementDisplayed("inp_memberDetail",detailName);
		element("inp_memberDetail", detailName).sendKeys(detailValue);
		logMessage("Step : enter " + detailValue + " for " + detailName + " \n");
	}

	public void enterMemberDetailsInMemberNumberLookup(String firstName,
			String lastName, String email) {
		enterMemberDetail("FirstName", firstName);
		enterMemberDetail("LastName", lastName);
		enterMemberDetail("Email", email);
	}

	public void checkCertify() {
		isElementDisplayed("chk_certify");
		element("chk_certify").click();
		logMessage("Step : certify check box is selected \n");
	}

	public void clickOnSubmitButton() {
		isElementDisplayed("btn_submit");
		element("btn_submit").click();
		logMessage("Step : certify check box is selected \n");
	}

	public void verifyMemberNumber(String memberNumber) {
		isElementDisplayed("txt_memberNumber", memberNumber);
		logMessage("ASSERT PASSED : member number " + memberNumber
				+ " is verified\n");
	}

	public void verifyThankYouMessage() {
		isElementDisplayed("txt_thankYouMessage");
		logMessage("ASSERT PASSED : thank you message Thank you for being an ACS member. is verified\n");
	}

}
