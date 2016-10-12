package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.GetPage;

public class ASM_memberNumberLookupPage extends GetPage {
	WebDriver driver;
	static String pagename = "ASM_memberNumberLookupPage";
	int timeOut, hiddenFieldTimeOut;

	public ASM_memberNumberLookupPage(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

	public void fillMemberDetails(String firstname, String lastName,
			String email) {
		enterMemberInfo("FirstName", firstname);
		enterMemberInfo("LastName", lastName);
		enterMemberInfo("Email", email);
		checkCertifyCheckbox();
		clickOnSubmitButton();
	}

	public void enterMemberInfo(String memberInfoType, String memberInfo) {
		isElementDisplayed("inp_memberInfo", memberInfoType);
		element("inp_memberInfo", memberInfoType).sendKeys(memberInfo);
		logMessage("STEP : " + memberInfo + " is entered in " + memberInfoType
				+ " \n");
	}

	public void checkCertifyCheckbox() {
		isElementDisplayed("chk_certify");
		if (!element("chk_certify").isSelected()) {
			element("chk_certify").click();
			logMessage("STEP : Certify checkbox is checked in chk_certify\n");
		} else {
			logMessage("STEP : Certify checkbox is already checked in chk_certify\n");
		}
	}

	public void clickOnSubmitButton() {
		isElementDisplayed("btn_submit");
		element("btn_submit").click();
		logMessage("STEP : Submit button is clicked in btn_submit\n");
	}

	public void verifyMemberRecordLocated(String memberRecordLocated) {
		isElementDisplayed("txt_memberRecordLocated");
		verifyElementText("txt_memberRecordLocated", memberRecordLocated);

	}

	public void verifyMemberRecordNotLocated(String memberRecordLocated) {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("txt_memberRecordLocated");
			verifyElementText("txt_memberRecordLocated", memberRecordLocated);
		} catch (NoSuchElementException exp) {
			logMessage("ASSERT PASSED : member record is not displayed\n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}

	}
}
