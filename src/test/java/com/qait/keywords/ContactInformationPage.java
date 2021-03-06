package com.qait.keywords;

import java.util.ArrayList;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.ConfigPropertyReader;

public class ContactInformationPage extends ASCSocietyGenericPage {

	WebDriver driver;
	ArrayList<String> memberDetailUnique = new ArrayList<>();

	public ContactInformationPage(WebDriver driver) {
		super(driver, "ContactInformationPage");
		this.driver = driver;
	}

	public String[] enterContactInformation(String email, String firstName, String lastName, String addressType,
			String address, String city, String country, String state, String zipCode) {

		wait.hardWait(6);
		String userEmail = enterEmail(email);
		String fname = enterMemberContactDetail("firstName", firstName); // + System.currentTimeMillis()
		String lname = enterMemberContactDetail("lastName", lastName + System.currentTimeMillis());
		selectMemberContactDetail("addressType", addressType);

		if (addressType.equalsIgnoreCase("Work")) {
			wait.waitForElementToBeVisible(element("inp_organization"));
		}
		enterMemberContactDetail("address", address);
		enterMemberContactDetail("city", city);
		selectMemberContactDetail("countryName", country);
		if (country.equalsIgnoreCase("UNITED STATES")) {
			selectMemberContactDetail("stateName", state);
		} else {
			try {
				wait.waitForElementToDisappear(element("list_stateName"));
			} catch (Exception E) {
				logMessage("Element not present");
			}
		}
		enterMemberContactDetail("zipCode", zipCode);
		String[] arr = { userEmail, fname, lname };
		return arr;
	}
	
	public String[] enterContactInformationOmaDiscount(String email, String firstName, String lastName, String addressType,
			String address, String city, String country, String state, String zipCode) {

		wait.hardWait(6);
		String userEmail = enterEmail(email);
		String fname = enterMemberContactDetail("firstName", firstName); // + System.currentTimeMillis()
		String lname = enterMemberContactDetail("lastName", lastName + System.currentTimeMillis());
		selectMemberContactDetail("addressType", addressType);

		if (addressType.equalsIgnoreCase("Work")) {
			wait.waitForElementToBeVisible(element("inp_organization"));
		}
		enterMemberContactDetail("address", address);
		enterMemberContactDetail("city", city);
		selectMemberContactDetail("countryName", country);
		if (ConfigPropertyReader.getProperty("tier").equalsIgnoreCase("dev4")) {
			selectMemberContactDetail("stateName", state);
		} else {
			if (country.equalsIgnoreCase("UNITED STATES")) {
				selectMemberContactDetail("stateName", state);
			} else {
				try {
					wait.waitForElementToDisappear(element("list_stateName"));
				} catch (Exception E) {
					logMessage("Element not present");
				}
			 }
			}
		enterMemberContactDetail("zipCode", zipCode);
		String[] arr = { userEmail, fname, lname };
		return arr;
	}


	public void clickContinue() {
		try {
			wait.hardWait(3);
			isElementDisplayed("btn_continue");
//			element("btn_continue").click();
			 clickUsingXpathInJavaScriptExecutor(element("btn_continue"));
			wait.hardWait(1);
			logMessage("STEP : Click on btn_continue\n");
		} catch (StaleElementReferenceException stlRef) {
			// isElementDisplayed("btn_continue");
			clickUsingXpathInJavaScriptExecutor(element("btn_continue"));
			logMessage("STEP : Click on btn_continue\n");
		}

	}

	private String enterEmail(String email) {
		System.out.println("email:" + email);
		if (!email.equalsIgnoreCase("null")) {
			isElementDisplayed("inp_email");
			String[] emailSubstring = email.split("@");
			String userEmail = emailSubstring[0] + System.currentTimeMillis() + "@" + emailSubstring[1];
			element("inp_email").clear();
			element("inp_email").sendKeys(userEmail);
			logMessage("STEP : " + userEmail + " entered in the inp_email");
			return userEmail;
		} else {
			logMessage("Email is not present in data sheet\n");
			return null;
		}

	}

	private String enterSameEmail(String email) {
		if (!email.equalsIgnoreCase("null")) {
			isElementDisplayed("inp_email");
			element("inp_email").clear();
			element("inp_email").sendKeys(email);
			logMessage("STEP : " + email + " entered in the inp_email");
			return email;
		} else {
			logMessage("Email is not present in data sheet\n");
			return null;
		}
	}

	private String enterMemberContactDetail(String detailType, String detailValue) {
		if (!detailValue.equalsIgnoreCase("null")) {
			isElementDisplayed("inp_" + detailType);
			element("inp_" + detailType).clear();
			element("inp_" + detailType).sendKeys(detailValue);
			logMessage("STEP : " + detailType + " is entered as " + detailValue + "\n");
			return detailValue;
		} else {
			logMessage("" + detailType + "is not present in data sheet\n");
			return null;
		}

	}

	private void selectMemberContactDetail(String detailType, String detailValue) {
		if (!detailValue.equalsIgnoreCase("null")) {
			wait.hardWait(3);
			isElementDisplayed("list_" + detailType);
			selectProvidedTextFromDropDown(element("list_" + detailType), detailValue);
			logMessage("STEP : " + detailValue + " is selected in the " + detailType + "\n");
		} else {
			logMessage("" + detailType + "is not present in data sheet\n");
		}
	}

	public void enterEmail_ASMOMA(String email) {
		isElementDisplayed("inp_email");
		String[] emailSubstring = email.split("@");
		String userEmail = emailSubstring[0] + System.currentTimeMillis() + "@" + emailSubstring[1];
		element("inp_email").clear();
		element("inp_email").sendKeys(userEmail);
		logMessage("STEP : " + userEmail + " entered in the inp_email");

	}

	public void enterInvalidEmail_ASMOMA(String userEmail) {
		isElementDisplayed("inp_email");
		element("inp_email").clear();
		element("inp_email").sendKeys(userEmail);
		logMessage("STEP : " + userEmail + " entered in the inp_email");

	}

	public void enterPhoneNumber(String phnNumber) {
		enterMemberContactDetail("phone", phnNumber);
	}

	public void enterCity(String cityName) {
		enterMemberContactDetail("city", cityName);
	}

	public void selectCountry(String country) {
		selectMemberContactDetail("countryName", country);
		if (country.equalsIgnoreCase("UNITED STATES"))
			selectMemberContactDetail("stateName", "Alaska");
	}

	public void enterZipCode(String zipCode) {
		enterMemberContactDetail("zipCode", zipCode);
	}

	public void enterLastName(String lastName) {
		element("inp_lastName").clear();
		enterMemberContactDetail("lastName", lastName);

	}

	public void enterMiddleName(String middleName) {
		element("inp_middleName").clear();
		enterMemberContactDetail("middleName", middleName);

	}

	public void enterContactNumber(String contactNumber) {
		element("inp_memberNumber").clear();
		enterMemberContactDetail("memberNumber", contactNumber);

	}

	public void enterAddress2(String address2) {
		element("inp_address2").clear();
		enterMemberContactDetail("address2", address2);

	}

	public String enterContactInformationWithMemberNumber(String email, String firstName, String lastName,
			String memberNumber, String addressType, String address, String city, String country, String state,
			String zipCode) {

		String userEmail = enterSameEmail(email);
		enterMemberContactDetail("firstName", firstName);
		enterMemberContactDetail("lastName", lastName);
		enterMemberContactDetail("memberNumber", memberNumber);
		holdExecution(1000);
		selectMemberContactDetail("addressType", addressType);

		if (addressType.equalsIgnoreCase("Work")) {
			wait.waitForPageToLoadCompletely();
			wait.waitForElementToBeVisible(element("inp_organization"));
		}
		enterMemberContactDetail("address", address);
		enterMemberContactDetail("city", city);
		selectMemberContactDetail("countryName", country);
		if (country.equalsIgnoreCase("UNITED STATES")) {
			selectMemberContactDetail("stateName", state);
		} else {
			wait.waitForElementToDisappear(element("list_stateName"));
		}
		enterMemberContactDetail("zipCode", zipCode);

		return userEmail;
	}

	public ArrayList<String> enterContactInformation_AACT(String caseId) {
		String userEmail = enterEmail(getAACT_OmaSheetValue(caseId, "Email ID"));
		String fName = enterMemberContactDetail("firstName",
				getAACT_OmaSheetValue(caseId, "FirstName") + System.currentTimeMillis());
		String lName = enterMemberContactDetail("lastName",
				getAACT_OmaSheetValue(caseId, "LastName") + System.currentTimeMillis());
		selectMemberContactDetail("addressType", getAACT_OmaSheetValue(caseId, "Address Type"));
		if (!getAACT_OmaSheetValue(caseId, "Address Type").equalsIgnoreCase("Home")) {
			enterMemberContactDetail("organization", getAACT_OmaSheetValue(caseId, "Organization Contact Page"));
			enterMemberContactDetail("dept/mailStop", getAACT_OmaSheetValue(caseId, "Dept/Mail Stop Contact Page"));
		}
		enterMemberContactDetail("address", getAACT_OmaSheetValue(caseId, "Address Contact Page"));
		enterMemberContactDetail("city", getAACT_OmaSheetValue(caseId, "City Contact Page"));
		selectMemberContactDetail("countryName", getAACT_OmaSheetValue(caseId, "Country Contact Page"));
		if (getAACT_OmaSheetValue(caseId, "Country Contact Page").equalsIgnoreCase("UNITED STATES")) {
			wait.waitForPageToLoadCompletely();
			selectMemberContactDetail("stateName", getAACT_OmaSheetValue(caseId, "State Contact Page"));
		} else {
			try {
				wait.waitForElementToDisappear(element("list_stateName"));
			} catch (Exception E) {
				logMessage("list_stateName not present");
			}
		}
		enterMemberContactDetail("zipCode", getAACT_OmaSheetValue(caseId, "Zip code Contact Page"));
		memberDetailUnique.add(userEmail);
		memberDetailUnique.add(fName);
		memberDetailUnique.add(lName);

		return memberDetailUnique;
	}

	public boolean getCreateOnlyIndividualMember(String caseId) {
		if (getAACT_OmaSheetValue(caseId, "Is Create Only Individual?").equalsIgnoreCase("Y")) {
			return true;
		} else if (getAACT_OmaSheetValue(caseId, "Is Create Only Individual?").equalsIgnoreCase("N")) {
			return false;
		} else {
			logMessage("Is individual create only column has null value");
			return false;
		}
	}

	public void verifyDetailsArePrepopulated() {
		_verifyDeatilsFromInputField("Phone");
		_verifyDeatilsFromList("AddressType");
		_verifyDeatilsFromInputField("Address");
		_verifyDeatilsFromInputField("City");
		_verifyDeatilsFromList("Country");
		_verifyDeatilsFromList("State");
		_verifyDeatilsFromInputField("ZipCode");

	}

	private void _verifyDeatilsFromList(String field) {
		isElementDisplayed("list_MGMDetails", field);
		String value = element("list_MGMDetails", field).getText();
		Assert.assertTrue(element("list_MGMDetails", field).getText() != "",
				"ASSERT FAILED: " + field + " is not populated");
		logMessage("ASSERT PASSED: " + field + " is already populated with " + value + " \n");
	}

	private void _verifyDeatilsFromInputField(String field) {
		isElementDisplayed("inp_MGMDetails", field);
		String value = element("inp_MGMDetails", field).getAttribute("value");
		Assert.assertTrue(element("inp_MGMDetails", field).getText() != "",
				"ASSERT FAILED: " + field + " is not populated");
		logMessage("ASSERT PASSED: " + field + " is already populated with " + value + " \n");

	}

}
