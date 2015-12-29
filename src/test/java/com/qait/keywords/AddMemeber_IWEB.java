package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class AddMemeber_IWEB extends ASCSocietyGenericPage {

	WebDriver driver;
	static String pagename = "AddMember_IWEB";
	int timeOut, hiddenFieldTimeOut;

	public AddMemeber_IWEB(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

	public void enterMemberDetailsInAddIndividual(String fName,
			String middleName, String lName, String country, String street,
			String city, String abrv_state, String inPostalCode,
			String phnCountry, String phnNumber, String outPostalCode) {
		if (lName.equalsIgnoreCase("")) {
			enterMemberDetails("first name",
					"firstName" + System.currentTimeMillis());
		} else {
			enterMemberDetails("first name", fName);
		}

		if (lName.equalsIgnoreCase("")) {
			enterMemberDetail("middleName",
					"middleName" + System.currentTimeMillis());
		} else {
			enterMemberDetail("middleName", middleName);
		}

		if (lName.equalsIgnoreCase("")) {
			enterMemberDetail("lastName",
					"lastName" + System.currentTimeMillis());
		} else {
			enterMemberDetail("lastName", lName);
		}

		selectMemberDetails("individualType", "Individual");
		selectMemberDetails("country", country);
		waitForSpinner();
		enterMemberDetail("addressLine1", street);
		enterMemberDetails("city/state/zip", city);
		selectMemberDetails("state", abrv_state);
		enterMemberDetail("postalCode", inPostalCode);
		selectMemberDetails("list_phnCountry", phnCountry);
		enterMemberDetails("number", phnNumber);
		clickOnSaveButton();
		handleAlert();
		if (isWindow()) {
			switchWindow();
			getAndVerifyMemberDetailInAddVerify("Address Line 1", street);
			getAndVerifyMemberDetailInAddVerify("City", city);

			verifySelectedTextFromDropDown(element("State"), abrv_state);
			getAndVerifyMemberDetailInAddVerify("Zip", inPostalCode + "-"
					+ outPostalCode);
			verifySelectedTextFromDropDown(element("Country"), country);
			clickOnAddVerifySaveButton();
			switchToDefaultContent();
		}
		if(!outPostalCode.equalsIgnoreCase("")){
			getAndVerifyMemberDetail("postalCode", inPostalCode + "-"
					+ outPostalCode);
			clickOnSaveButton();
		}
		
	}

	public void enterMemberDetails(String detailName, String detailValue) {
		isElementDisplayed("inp_memberDetailInAdd", detailName);
		element("inp_memberDetailInAdd", detailName).sendKeys(detailValue);
		logMessage("Step : enter " + detailValue + " in " + detailName + " \n");
	}

	public void selectMemberDetails(String detailName, String detailValue) {
		isElementDisplayed("list_" + detailName);
		selectProvidedTextFromDropDown(element("list_" + detailName),
				detailValue);
		logMessage("Step : select " + detailValue + " in " + detailName + " \n");
	}

	public void enterMemberDetail(String detailName, String detailValue) {
		isElementDisplayed("inp_" + detailName);
		element("inp_" + detailName).sendKeys(detailValue);
		logMessage("Step : enter " + detailValue + " in " + detailName + " \n");
	}

	public void enterAddVerifyMemberDetails(String detailName,
			String detailValue) {
		isElementDisplayed("inp_addVerify", detailName);
		element("inp_addVerify", detailName).sendKeys(detailValue);
		logMessage("Step : enter " + detailValue + " in " + detailName + " \n");
	}

	public void getAndVerifyMemberDetail(String detailName, String detailValue) {
		isElementDisplayed("inp_" + detailName);
		String actualText = element("inp_" + detailName).getText().trim();
		Assert.assertTrue(actualText.equalsIgnoreCase(detailValue));
		logMessage("ASSERT PASSED : Verified " + detailValue + " in "
				+ detailName + " \n");
	}

	public void getAndVerifyMemberDetailInAddVerify(String detailName,
			String detailValue) {
		isElementDisplayed("inp_addVerify", detailName);
		String actualText = element("inp_addVerify", detailName).getText()
				.trim();
		Assert.assertTrue(actualText.equalsIgnoreCase(detailValue));
		logMessage("ASSERT PASSED : Verified " + detailValue + " in "
				+ detailName + " \n");
	}

	public void clickOnSaveButton() {
		isElementDisplayed("btn_save");
		element("btn_save").click();
		logMessage("Step : save button is clicked in btn_save\n");
	}

	public void clickOnAddVerifySaveButton() {
		isElementDisplayed("btn_addVerifySave");
		element("btn_addVerifySave").click();
		logMessage("Step : save button is clicked in btn_addVerifySave\n");
	}

	public void waitForSpinner() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		try {
			handleAlert();
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("img_spinner");
			wait.waitForElementToDisappear(element("img_spinner"));
			logMessage("STEP : Wait for spinner to be disappeared \n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (NoSuchElementException | AssertionError | TimeoutException Exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("STEP : Spinner is not present \n");
		}
	}

}
