package com.qait.keywords;

import java.util.ArrayList;
import java.util.List;

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
		logMessage("STEP: Click on " + btnName + " button \n");
	}

	public void clickOnLink(String linkName) {
		isElementDisplayed("link_contact", linkName);
		element("link_contact", linkName).click();
		logMessage("STEP: Click on " + linkName + " link \n");
	}

	public void editEmailOnUpdateAboutYouPage(String email) {
		email = email.replaceAll("XXX", "");
		sendKeysUsingXpathInJavaScriptExecutor(element("inp_editME"), email);
		clickButtonById("btnEmailSave");
		logMessage("STEP: Email address is changed into '" + email + "' \n");
	}

	public void selectValuesForChemMatters(String label, String type, String membershipType) {
		isElementDisplayed("drpdown_chemMatters", label);
		System.out.println("membershipType::" + membershipType);
		if (membershipType.equalsIgnoreCase("Student")
				&& (type.equalsIgnoreCase("Print and Electronic") || (type.equalsIgnoreCase("Print")))) {

			selectProvidedTextFromDropDown(element("drpdown_chemMatters", label), "Electronic");
		} else {

			selectProvidedTextFromDropDown(element("drpdown_chemMatters", label), type);
		}
		wait.waitForPageToLoadCompletely();
		logMessage("STEP: 'How do you want to receive ChemMatters?' field is selected as " + type);
	}

	public List<String> updateDetailsfoAboutYouSection() {

		memberType = element("txt_detailsAboutYou", "MemberCategory").getText().trim();
		List<String> updatedValues = new ArrayList<String>();
		logMessage("Member Type is " + memberType);
		if (memberType.equalsIgnoreCase("Teacher") || memberType.equalsIgnoreCase("Student")) {
			clickButtonByInputValue("Update About You");
			verifyPageHeader("title_header", "top-title", "Update About You");
			updatedValues = updateDetailsForTeacher("GradesTaughtChk");
			// updatedValues = updateDetailsForTeacher("SubjectsTaughtChk");
			logMessage("STEP: Update the details of About You for " + memberType + "\n");
			clickButtonByInputValue("Save");
		} else {
			logMessage("STEP: Member type is affiliated " + memberType + "\n");
		}
		return updatedValues;
	}

	public List<String> updateDetailsForTeacher(String value) {
		getCheckedValuesOnUpdateAboutYou(value);
		return checkTheValuesOnUpdateAboutYou(value);

	}

	public void verifyPageHeader(String locator, String text, String title) {
		isElementDisplayed(locator, text);
		Assert.assertTrue(title.trim().equals(element(locator, text).getText().trim()));
		logMessage("ASSERT PASSED: Verified Page header is " + title + "\n");
	}

	public void getCheckedValuesOnUpdateAboutYou(String value) {
		isElementDisplayed("chked_labelsOnUpdateAboutYou", value);
		int size = elements("chked_labelsOnUpdateAboutYou", value).size();
		while (size > 0) {
			size--;
			elements("chked_labelsOnUpdateAboutYou", value).get(size).click();
		}
		logMessage("STEP: All checked values are unchecked \n");
	}

	public List<String> checkTheValuesOnUpdateAboutYou(String value) {

		List<String> checkedValues = new ArrayList<String>();
		int size = elements("unchked_label", value).size();
		for (int i = size - 1; i > size - 10; i--) {

			checkedValues.add(elements("txt_label", value).get(i).getText().trim());
			//System.out.println("checked values " + elements("txt_label", value).get(i).getText().trim());
			elements("unchked_label", value).get(i).click();
			logMessage("STEP: "+elements("txt_label", value).get(i).getText().trim()+" is checked for "+value+"\n");
		}

		logMessage("\nSTEP: values of '" + value + "' field are updated \n");
		return checkedValues;
	}

	public void enterGenderExperienceAndGraduationDetails(String gender, String experience, String gradMonth,
			String gradYear) {
		if (memberType.equalsIgnoreCase("Teacher") || memberType.equalsIgnoreCase("Student")) {
			isElementDisplayed("inp_value", "YearsExp");
			sendKeysUsingXpathInJavaScriptExecutor(element("inp_value", "YearsExp"), experience);
			logMessage("STEP: 'Chemistry teaching experience' is entered as " + experience + " \n");
		}
		if (memberType.equalsIgnoreCase("Student")) {
			selectProvidedTextFromDropDown(element("list_cardInfo", "GradMonth"), gradMonth);
			selectProvidedTextFromDropDown(element("list_cardInfo", "GradYear"), gradYear);
			logMessage("STEP: 'Graduation month' is entered as " + gradMonth + " and year as " + gradYear + " \n");
		}
		selectProvidedTextFromDropDown(element("list_cardInfo", "Gender"), gender);
	}

	public void verifyDetailsOfUpdateAboutYou(List<String> checkedValues) {

		if (memberType.equalsIgnoreCase("Teacher") || memberType.equalsIgnoreCase("Student")) {
			verifyDetailsOfList(checkedValues, "GradesList");
		}

		// verifyDetailsOfList(checkedValues,"SubjectsList");
	}

	private void verifyDetailsOfList(List<String> checkedValues, String listName) {

		int size = checkedValues.size();
		isElementDisplayed("txt_detailsAboutYou", listName);
		boolean flag = true;
		String details = element("txt_detailsAboutYou", listName).getText().trim();
		while (size > 0) {
			
			if (!details.contains(checkedValues.get(size - 1))) {
				flag = false;
				break;
			}
			
			size--;
		}
		Assert.assertTrue(flag);
		logMessage("ASSERT PASSED: All the Details of " + listName + " is verified \n");
	}

	public void enterPaymentInfo(String creditCardType, List<String> creditCardHolderName, String creditCardNumber,
			String CvvNumber, String expMonth, String expYear) {
		selectCreditCardInfo("CreditCardType", creditCardType);
		enterCreditCardHolderName("CardholderName", creditCardHolderName);
		enterCreditCardInfo("CreditCardNumber", creditCardNumber);
		selectCreditCardInfo("ExpirationMonth", expMonth);
		selectCreditCardInfo("ExpirationYear", expYear);
		wait.waitForPageToLoadCompletely();
		wait.hardWait(3);
		enterCreditCardInfo("CcvNumber", CvvNumber);
	}

	public void enterPaymentInfo(String creditCardType, String creditCardHolderName, String creditCardNumber,
			String CvvNumber, String expMonth, String expYear) {
		selectCreditCardInfo("CreditCardType", creditCardType);
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

	private void enterCreditCardHolderName(String creditCardInfo, List<String> value) {
		isElementDisplayed("inp_cardInfo", creditCardInfo);
		element("inp_cardInfo", creditCardInfo).sendKeys(value.get(0) + " " + value.get(1));
		logMessage("STEP :" + creditCardInfo + " is entered  as " + value + "\n");
	}

	private void selectCreditCardInfo(String creditCardInfo, String value) {
		isElementDisplayed("list_cardInfo", creditCardInfo);
		wait.waitForPageToLoadCompletely();
		selectProvidedTextFromDropDown(element("list_cardInfo", creditCardInfo), value);
		logMessage("STEP :" + creditCardInfo + " is selected  as " + value + "\n");

	}

	public void checkAutoRenwealCheckbox(String checkboxname) {
		if (!memberType.equalsIgnoreCase("Student")) {
			checktheCheckbox(checkboxname);
			wait.hardWait(5);
			isElementDisplayed("inp_editEmail", "lblWarning");
		}
	}

	private void checktheCheckbox(String checkboxname) {
		isElementDisplayed("unchked_label", checkboxname);
		element("unchked_label", checkboxname).click();
		logMessage("STEP: " + checkboxname + " checkbox is selected \n");
	}

	public void clickButtonById(String btn) {

		isElementDisplayed("inp_editEmail", btn);
		clickUsingXpathInJavaScriptExecutor(element("inp_editEmail", btn));
		logMessage("STEP: " + btn + " is clicked \n");
	}

	public void verifydetailsOnOnlineMembershipRenewalPage(String value, String locator) {

		isElementDisplayed("txt_detailsAboutYou", locator);
		String text = element("txt_detailsAboutYou", locator).getText().trim();
		if (text.contains("year(s)")) {
			text = text.replace("year(s)", "").trim();
		}
		text.trim();
		Assert.assertTrue(value.equals(text));
		logMessage("ASSERT PASSED: " + locator + " is verified as " + value + "\n");
	}

	public boolean makeSchoolAddressAsPrimaryAddress() {

		boolean flag = false;
		if (checkIfElementIsThere("txt_detailsAboutYou", "homePrimary")
				&& checkIfElementIsThere("inp_editEmail", "btnWorkAddress")) {

			clickButtonById("btnWorkAddress");
			wait.hardWait(5);
			checktheCheckbox("isPrimary");
			clickButtonByInputValue("Save");
			wait.hardWait(5);
			logMessage("STEP: 'school/work address' is selected as primary \n");
			flag = true;
		} else {
			logMessage("STEP: 'school/work address' is already primary or can not make as primary");
		}
		clickButtonByInputValue("Return to Renewal");
		return flag;
	}

	public void verifyWorkAddressIsPrimary(boolean isPrimary) {

		if (isPrimary) {
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
		Assert.assertTrue(creditCardNumber.substring(12).trim().equals(text.substring(12).trim()));
		logMessage("ASSERT PASSED: Credit Card number displays only last four digits \n");

	}

	public String getDetailsfromOnlineMembershipPage(String field) {
		isElementDisplayed("txt_detailsAboutYou", field);
		String value = element("txt_detailsAboutYou", field).getText().trim();
		return value;
	}

	public boolean getPrimaryAddress() {

		return checkIfElementIsThere("txt_detailsAboutYou", "homePrimary");
	}

	public void verifyMembershipType(String type, String category) {
		Assert.assertTrue(type.equals(getMembershipName(category)));
		logMessage("ASSERT PASSED: Membership type is verified as " + type);

	}

	public String getMembershipName(String category) {
		isElementDisplayed("txt_detailsAboutYou", category);
		String membershipType = element("txt_detailsAboutYou", category).getText().trim();
		return membershipType;
	}

	public void clickOnSubmitPaymentOnOnlineMembershipRenewalPage() {

		isElementDisplayed("inp_editEmail", "btnPrevious");
		clickButtonById("btnNext");
		wait.waitForPageToLoadCompletely();
	}

}
