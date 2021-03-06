package com.qait.keywords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.DateUtil;

public class ACS_AACT_OMR_Action extends ASCSocietyGenericPage {

	WebDriver driver;
	static String pagename = "ACS_AACT_OMR_Page";
	String memberType;

	public ACS_AACT_OMR_Action(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

	public void loginWithWeblogin(String weblogin, String password) {

		loginWithValidCredntials("input-user-name", weblogin);
		loginWithValidCredntials("input-password", password);

	}

	public void loginWithValidCredntials(String field, String value) {
		isElementDisplayed("title_header", field);
		element("title_header", field).sendKeys(value);
		logMessage("STEP: " + field + " is entered as " + value + " \n");
	}

	public void clickButtonByInputValue(String btnName) {
		isElementDisplayed("btn_byValue", btnName);
		clickUsingXpathInJavaScriptExecutor(element("btn_byValue", btnName));
		logMessage("STEP: Click on '" + btnName + "' button \n");
	}

	public void clickOnLink(String linkName) {
		isElementDisplayed("link_contact", linkName);
		element("link_contact", linkName).click();
		logMessage("STEP: Click on " + linkName + " link \n");
	}

	public String editEmailOnUpdateAboutYouPage(String email) {
		email = email.replaceAll("XXX", "");
		String newEmail = new StringBuffer(email).insert(email.length() - 4, "xxx").toString();
		sendKeysUsingXpathInJavaScriptExecutor(element("inp_editME"), newEmail);
		clickButtonById("btnEmailSave", "Save");
		logMessage("STEP: Email address is changed into '" + email + "' \n");
		return newEmail;
	}

	public void selectValuesForChemMatters(String label, String type, String membershipType, String countryName) {
		isElementDisplayed("drpdown_chemMatters", label);
		if ((membershipType.equalsIgnoreCase("Student") || (!countryName.equalsIgnoreCase("UNITED STATES")))
				&& (type.equalsIgnoreCase("Print and Electronic") || (type.equalsIgnoreCase("Print")))) {

			selectProvidedTextFromDropDown(element("drpdown_chemMatters", label), "Electronic");
		} else {

			selectProvidedTextFromDropDown(element("drpdown_chemMatters", label), type);
		}
		if (checkIfElementIsThere("img_procesing")) {
			wait.waitForElementToDisappear(element("img_procesing"));
		}

		logMessage("STEP: '" + label + "' is selected as " + type);
	}

	public Map<String, List<String>> updateDetailsInAboutYouSection(String membershipType) {
		 memberType = element("txt_detailsAboutYou", "MemberCategory").getText().trim();
		// logMessage("STEP: Member type is "+memberType);
		Map<String, List<String>> updatedDetailsOfMember = new HashMap<String, List<String>>();

		if (membershipType.equalsIgnoreCase("Teacher") || membershipType.equalsIgnoreCase("Student")) {
			clickButtonByInputValue("Update About You");
			verifyPageHeader("title_header", "top-title", "Update About You");
			// updatedValuesGrade = updateDetailsForTeacher("GradesTaughtChk");
			List<String> updatedValuesGrade = updateDetailsForTeacher("GradesTaughtChk");
			List<String> updatedValuesSubject = updateDetailsForTeacher("SubjectsTaughtChk");
			// List<String>updatedValuesSetting =
			// updateDetailsForTeacher("InstructionalSettingsChk");
			try {
				isElementDisplayed("chked_labelsOnUpdateAboutYou", "InstructionalSettingsChk");
				logMessage("STEP: Instructional Settings are already checked \n");

			} catch (NoSuchElementException e) {
				elements("unchked_label").get(1).click();
				logMessage("STEP: Checked the values for Instructional Settings \n");
			}

			updatedDetailsOfMember.put("GradesList", updatedValuesGrade);
			updatedDetailsOfMember.put("SubjectsList", updatedValuesSubject);
			// updatedDetailsOfMember.put("Setting", updatedValuesSetting);

			logMessage("STEP: Update the details of About You for " + membershipType + "\n");
			clickButtonByInputValue("Save");
		} else {
			logMessage("STEP: Member type is " + membershipType + "\n");
		}
		return updatedDetailsOfMember;
	}

	public List<String> updateDetailsForTeacher(String value) {
		getCheckedValuesOnUpdateAboutYou(value);
		return checkTheValuesOnUpdateAboutYou(value);

	}

	public void verifyPageHeader(String locator, String text, String title) {
		isElementDisplayed(locator, text);
		Assert.assertTrue(title.trim().equals(element(locator, text).getText().trim()),
				"ASSERT FAILED: Expected Page header is " + title + " but found "
						+ element(locator, text).getText().trim());
		logMessage("ASSERT PASSED: Verified Page header is " + title + "\n");
	}

	public void getCheckedValuesOnUpdateAboutYou(String value) {
		try {
			isElementDisplayed("chked_labelsOnUpdateAboutYou", value);
			int size = elements("chked_labelsOnUpdateAboutYou", value).size();
			while (size > 0) {
				size--;
				elements("chked_labelsOnUpdateAboutYou", value).get(size).click();
			}
			logMessage("STEP: All checked values are unchecked \n");
		} catch (NoSuchElementException e) {
			logMessage("STEP: No value is already checked");
		}

	}

	public List<String> checkTheValuesOnUpdateAboutYou(String value) {

		List<String> checkedValues = new ArrayList<String>();
		int size = elements("unchked_label", value).size();
		for (int i = size - 1; i > size - 10; i--) {

			checkedValues.add(elements("txt_label", value).get(i).getText().trim());
			elements("unchked_label", value).get(i).click();
			logMessage("STEP: " + elements("txt_label", value).get(i).getText().trim() + " is checked for " + value
					+ "\n");
		}
		logMessage("\nSTEP: values of '" + value + "' field are updated \n");
		return checkedValues;
	}

	public void enterGenderExperienceAndGraduationDetails(String gender, String experience, String gradMonth,
			String gradYear,String memberType) {
		if (memberType.equalsIgnoreCase("Teacher") || memberType.equalsIgnoreCase("Student")) {
			isElementDisplayed("inp_value", "YearsExp");
			sendKeysUsingXpathInJavaScriptExecutor(element("inp_value", "YearsExp"), experience);
			logMessage("STEP: 'Chemistry teaching experience' is entered as " + experience + " \n");
		}
		if (memberType.equalsIgnoreCase("Student")) {
			isElementDisplayed("list_cardInfo", "GradMonth");
			isElementDisplayed("list_cardInfo", "GradYear");
			selectProvidedTextFromDropDown(element("list_cardInfo", "GradMonth"), gradMonth);
			selectProvidedTextFromDropDown(element("list_cardInfo", "GradYear"), gradYear);
			logMessage("STEP: 'Graduation month' is entered as " + gradMonth + " and year as " + gradYear + " \n");
		}
		isElementDisplayed("list_cardInfo", "Gender");
		selectProvidedTextFromDropDown(element("list_cardInfo", "Gender"), gender);
		logMessage("STEP: Gender is selected as " + gender + "\n");
	}

	
	private void verifyDetailsOfList(Map<String, List<String>> checkedValues, String listName) {

		int size = checkedValues.size();
		isElementDisplayed("txt_detailsAboutYou", listName);
		boolean flag = true;
		String details = element("txt_detailsAboutYou", listName).getText().trim();
		while (size > 0) {

			if (!details.contains(checkedValues.get(listName).get(size - 1))) {
				flag = false;
				break;
			}

			size--;
		}
		Assert.assertTrue(flag, "ASSERT FAILED: All Details are not verified ");
		logMessage("ASSERT PASSED: All the Details of " + listName + " is verified \n");
	}

	public void enterPaymentInfo(String paymentMethod, String creditCardHolderName, String creditCardNumber,
			String CvvNumber, String expMonth, String expYear) {
		selectCreditCardInfo("CreditCardType", paymentMethod);
		enterCreditCardInfo("CardholderName", creditCardHolderName);
		enterCreditCardInfo("CreditCardNumber", creditCardNumber);
		selectCreditCardInfo("ExpirationMonth", expMonth);
		selectCreditCardInfo("ExpirationYear", expYear);
		wait.waitForPageToLoadCompletely();
		wait.hardWait(3);
		enterCreditCardInfo("CcvNumber", CvvNumber);
	}

	private void enterCreditCardInfo(String creditCardInfo, String value) {
		isElementDisplayed("inp_cardInfo", creditCardInfo);
		element("inp_cardInfo", creditCardInfo).sendKeys(value);
		logMessage("STEP :" + creditCardInfo + "is entered  as " + value + "\n");
	}

	private void selectCreditCardInfo(String creditCardInfo, String value) {
		isElementDisplayed("list_cardInfo", creditCardInfo);
		// wait.waitForPageToLoadCompletely();
		selectProvidedTextFromDropDown(element("list_cardInfo", creditCardInfo), value);
		logMessage("STEP :" + creditCardInfo + " is selected  as " + value + "\n");

	}

	public void checkAutoRenwealCheckbox(String memberType) {
		if (!memberType.equalsIgnoreCase("Student")) {
			checktheCheckbox("chkAutoRenewal");
			wait.hardWait(5);
			isElementDisplayed("inp_editEmail", "lblWarning");
			logMessage("Step: Auto renewal check box is checked \n");
		}
	}

	private void checktheCheckbox(String checkboxname) {
		wait.hardWait(5);
		isElementDisplayed("unchked_label", checkboxname);
		element("unchked_label", checkboxname).click();
		logMessage("STEP: Auto Renew checkbox is selected \n");
	}

	public void clickButtonById(String btn, String label) {

		isElementDisplayed("inp_editEmail", btn);
		clickUsingXpathInJavaScriptExecutor(element("inp_editEmail", btn));
		logMessage("STEP: '" + label + "' button is clicked \n");
	}

	public void verifydetailsOnOnlineMembershipRenewalPage(String value, String locator) {

		isElementDisplayed("txt_detailsAboutYou", locator);
		String text = element("txt_detailsAboutYou", locator).getText().trim();
		if (text.contains("year(s)")) {
			text = text.replace("year(s)", "").trim();
		}
		text.trim();
		Assert.assertTrue(value.equals(text),
				"ASSERT FAILED: Expected value of " + locator + " is " + value + " but found " + text);
		logMessage("ASSERT PASSED: " + locator + " is verified as " + value + "\n");
	}

	public boolean makeSchoolAddressAsPrimaryAddress() {

		boolean flag = false;
		if (checkIfElementIsThere("txt_detailsAboutYou", "homePrimary")
				&& checkIfElementIsThere("inp_editEmail", "btnWorkAddress")) {

			clickButtonById("btnWorkAddress", "Change Address");
			wait.hardWait(5);
			checktheCheckbox("isPrimary");
			clickButtonByInputValue("Save");
			wait.hardWait(5);
			logMessage("STEP: 'school/work address' is selected as primary \n");
			flag = true;
		} else {
			logMessage("STEP: 'school/work address' is already primary or null");
		}
		clickButtonByInputValue("Return to Renewal");
		return flag;
	}

	public void verifyWorkAddressIsPrimary(boolean isPrimary) {

		if (!isPrimary) {
			isElementDisplayed("txt_detailsAboutYou", "workPrimary");
			logMessage("STEP: verified school/work address is primary");
		} else {
			try {
				isElementDisplayed("txt_detailsAboutYou", "homePrimary");
				logMessage("STEP: verified home address is primary");
			} catch (Exception e) {
				logMessage("STEP: No primary Address \n");
			}
		}
	}

	public void verifyDetailsOfSummaryPage(String email, String gender, String experience, String CreditCardType,
			String CardholderName, String CreditCardNumber, String ExpirationMonth, String ExpirationYear) {

		verifydetailsOnOnlineMembershipRenewalPage(email, "EmailAddress");
		verifydetailsOnOnlineMembershipRenewalPage(gender, "Gender");
		verifydetailsOnOnlineMembershipRenewalPage(CreditCardType, "CreditCardType");
		verifydetailsOnOnlineMembershipRenewalPage(CardholderName, "CardholderName");
		verifyCreditCardNumber(CreditCardNumber, "CreditCardNumber");
		if (memberType.equalsIgnoreCase("Teacher") || memberType.equalsIgnoreCase("Student")) {
			verifydetailsOnOnlineMembershipRenewalPage(experience, "YearsExp");
		}

		String ExpirationDate = ExpirationMonth + "/" + ExpirationYear;
		DateUtil.convertStringToParticularDateFormat(ExpirationDate, "MM/yyyy");
		verifydetailsOnOnlineMembershipRenewalPage(
				DateUtil.convertStringToParticularDateFormat(ExpirationDate, "MM/yyyy"), "ExpirationDate");

	}

	private void verifyCreditCardNumber(String creditCardNumber, String locator) {

		isElementDisplayed("txt_detailsAboutYou", locator);
		String text = element("txt_detailsAboutYou", locator).getText().trim();
		Assert.assertTrue("************".equals(text.substring(0, 12).trim()));
		logMessage("ASSERT PASSED: Credit Card number has first eleven digits as * \n");
		Assert.assertTrue(creditCardNumber.substring(12).trim().equals(text.substring(12).trim()),
				"ASSERT FAILED: Last four digit of Credit Card doest not match");
		logMessage("ASSERT PASSED: Credit Card number displays only last four digits \n");

	}

	public String getDetailsfromOnlineMembershipPage(String field) {
		isElementDisplayed("txt_detailsAboutYou", field);
		String value = element("txt_detailsAboutYou", field).getText().trim();
		logMessage("STEP: " + field + " is " + value + " \n");
		return value;
	}

	public boolean getPrimaryAddress() {

		return checkIfElementIsThere("txt_detailsAboutYou", "homePrimary");
	}

	public void verifyMembershipType(String type, String category) {
		Assert.assertTrue(type.equals(getMembershipName(category)),
				"ASSERT FAILED: Expected Membership type is " + type + " but found " + getMembershipName(category));
		logMessage("ASSERT PASSED: Membership type is verified as " + type);

	}

	public String getMembershipName(String category) {
		isElementDisplayed("txt_detailsAboutYou", category);
		String membershipType = element("txt_detailsAboutYou", category).getText().trim();
		return membershipType;
	}

	public void clickOnSubmitPaymentOnOnlineMembershipRenewalPage() {

		isElementDisplayed("inp_editEmail", "btnPrevious");
		clickButtonById("btnNext", "Submit Payment");
		wait.waitForPageToLoadCompletely();
	}

	public String getMembershipDetailsFromMembershipInvoiceTable(String field) {
		isElementDisplayed("txt_membershipItems", "1");
		String details = element("txt_membershipItems", "1").getText();
		logMessage("STEP: " + field + " is '" + details + "' \n");
		return details;
	}

	public void verifyDetailsOfUpdateAboutYou(String membershipType, Map<String, List<String>> updatedDetailsOfMember) {
		// TODO Auto-generated method stub
		if (membershipType.equalsIgnoreCase("Teacher") || membershipType.equalsIgnoreCase("Student")) {
			verifyDetailsOfList(updatedDetailsOfMember, "GradesList");
			verifyDetailsOfList(updatedDetailsOfMember, "SubjectsList");
		}

	}

}
