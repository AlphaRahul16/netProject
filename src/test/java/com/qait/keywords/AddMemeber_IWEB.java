package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;


import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class AddMemeber_IWEB extends ASCSocietyGenericPage {

	WebDriver driver;
	static String pagename = "AddMember_IWEB";
	int timeOut, hiddenFieldTimeOut;

	public AddMemeber_IWEB(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

	public String[] enterMemberDetailsInAddIndividual() {
		String fName = map().get("firstName");
		String mName = map().get("middleName");
		String lName = map().get("lastName");
		String country = map().get("country");
		String street = map().get("street");
		String city = map().get("city");
		String abrState = map().get("abrv_state");
		String inPostalCode = map().get("In_postalCode");
		String phnCountry = map().get("country");
		String phnNumber = map().get("phnNumber");
		String outPostalCode = map().get("Out_postalCode");

		if (fName.equalsIgnoreCase("")) {
			fName = "FN" + System.currentTimeMillis();
			enterMemberDetails("first name", fName);
		} else {
			enterMemberDetails("first name", fName);
		}

		if (mName.equalsIgnoreCase("")) {
			mName = "Selenium";
			enterMemberDetail("middleName", mName);
		} else {
			enterMemberDetail("middleName", mName);
		}

		if (lName.equalsIgnoreCase("")) {
			lName = "LN" + System.currentTimeMillis();
			enterMemberDetail("lastName", lName);
		} else {
			enterMemberDetail("lastName", lName);
		}

		selectMemberDetails("individualType", "Individual");
		selectMemberDetails("country", country);
		hardWaitForChromeBrowser(6);
		enterMemberDetail("addressLine1", street);
		enterMemberDetails("city/state/zip", city);
		if (!(abrState.equalsIgnoreCase(""))) {
			selectMemberDetails("state", abrState);
		}
		enterMemberDetail("postalCode", inPostalCode);
		waitForSpinner();
		wait.hardWait(2);
		// selectMemberDetails("phnCountry", phnCountry);
		//
		// enterMemberDetails("number", phnNumber);
		waitForSpinner();
		wait.hardWait(1);
		clickOnSaveButton();

		handleAlert1();
		if (isWindow()) {
			switchWindow();
			getAndVerifyMemberDetailInAddVerify("Address Line 1", street);
			getAndVerifyMemberDetailInAddVerify("City", city);
			verifySelectedTextFromDropDown(element("list_state_ver"), abrState);
			getAndVerifyMemberDetailInAddVerify("Zip", outPostalCode);
			verifySelectedTextFromDropDown(element("list_country_ver"), country);
			clickOnAddVerifySaveButton();
			switchWindow();
		}
		if (!outPostalCode.equalsIgnoreCase("")
				&& country.equalsIgnoreCase("UNITED STATES")) {
			//getAndVerifyMemberDetail("postalCode", outPostalCode);
			clickOnSaveButton();
		}

		return new String[] { fName, mName, lName, street, city, abrState,
				outPostalCode, phnNumber };

	}

	public void enterMemberDetails(String detailName, String detailValue) {
		//wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(2);
		isElementDisplayed("inp_memberDetailInAdd", detailName);
		element("inp_memberDetailInAdd", detailName).clear();
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
//		wait.waitForPageToLoadCompletely();
		try {
			isElementDisplayed("inp_" + detailName);
			String actualText = element("inp_" + detailName).getAttribute(
					"value").trim();
			Assert.assertTrue(actualText.equalsIgnoreCase(detailValue));
			logMessage("ASSERT PASSED : Verified " + detailValue + " in "
					+ detailName + " \n");
		} catch (StaleElementReferenceException E) {
			isElementDisplayed("inp_" + detailName);
			String actualText = element("inp_" + detailName).getAttribute(
					"value").trim();
			Assert.assertTrue(actualText.equalsIgnoreCase(detailValue));
			logMessage("ASSERT PASSED : Verified " + detailValue + " in "
					+ detailName + " \n");
		}
	}

	public void getAndVerifyMemberDetailInAddVerify(String detailName,
			String detailValue) {
		isElementDisplayed("inp_addVerify", detailName);
		String actualText = element("inp_addVerify", detailName).getAttribute(
				"value").trim();
		Assert.assertTrue(actualText.equalsIgnoreCase(detailValue));
		logMessage("ASSERT PASSED : Verified " + detailValue + " in "
				+ detailName + " \n");
	}

	public void clickOnSaveButton() {
		isElementDisplayed("btn_save");
		clickUsingXpathInJavaScriptExecutor(element("btn_save"));
		//element("btn_save").click();
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
		System.out.println(isBrowser("chrome"));

		if(!isBrowser("chrome"))
		{
		try {
			handleAlert();
			wait.resetImplicitTimeout(3);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("img_spinner");
			wait.waitForElementToDisappear(element("img_spinner"));
			logMessage("STEP : Wait for spinner to be disappeared \n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} /*catch (NoSuchElementException | AssertionError | TimeoutException  | StaleElementReferenceException Exp) {*/
		catch(Exception e){
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("STEP : Spinner is not present \n");
		}
		}
	}

	protected void handleAlert1() {
		try {
			switchToAlert().accept();
			logMessage("Alert handled..");
			driver.switchTo().defaultContent();
		} catch (Exception e) {

			System.out.println("No Alert window appeared...");
		}
	}

}
