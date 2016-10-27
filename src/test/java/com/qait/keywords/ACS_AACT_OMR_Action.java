package com.qait.keywords;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class ACS_AACT_OMR_Action extends ASCSocietyGenericPage {

	WebDriver driver;
	static String pagename = "ACS_AACT_OMR_Page";
	String memberType;

	public ACS_AACT_OMR_Action(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

	public void loginWithWeblogin(String weblogin, String password) {

		System.out.println("Web login::" + weblogin);
		System.out.println("password::" + password);
		loginWithValidCredntials("input-user-name", weblogin);
		loginWithValidCredntials("input-password", password);

	}

	public void loginWithValidCredntials(String field, String weblogin) {
		isElementDisplayed("title_header", field);
		element("title_header", field).sendKeys(weblogin);
		logMessage("Step: Enter " + weblogin + " as " + field + " \n");
	}

	public void clickButtonByInputValue(String btnName) {
		isElementDisplayed("btn_byValue", btnName);
		element("btn_byValue", btnName).click();

		logMessage("Step: Click on " + btnName + " button \n");
	}

	public void clickOnLink(String linkName) {
		isElementDisplayed("link_contact", linkName);
		element("link_contact", linkName).click();
		logMessage("Step: Click on " + linkName + " link \n");
	}

	public void editEmail() {
		// isElementDisplayed("inp_editEmail", "tbEmail");
		// String email1 =
		// element("inp_editEmail","tbEmail").getAttribute("value");
		// System.out.println("Email:: " + email1);
		isElementDisplayed("inp_editEmail", "lblEmail");
		String email = element("inp_editEmail", "lblEmail").getText();

		email = email.replaceAll("XXX", "");
		wait.hardWait(3);
		System.out.println("Email:: " + email);
		element("inp_editEmail", "tbEmail").clear();
		sendKeysUsingXpathInJavaScriptExecutor(element("inp_editEmail", "tbEmail"), email);
		element("inp_editEmail", "btnEmailSave").click();

		logMessage("Step: email is changed from " + " to " + email + " \n");
	}

	public void selectValuesForChemMatters(String label, String type) {
		System.out.println("------label:" + label + "----type:" + type);
		isElementDisplayed("drpdown_chemMatters", label);
		System.out.println("type::" + type);
		selectProvidedTextFromDropDown(element("drpdown_chemMatters", label), type);
	}

	public List<String>  updateAboutYou() {
		memberType = element("lbl_memberType").getText();
		List<String> updatedValues = new ArrayList<String>();
		logMessage("Member Type is " + memberType);
		if (memberType.equalsIgnoreCase("Teacher") || memberType.equalsIgnoreCase("Student")) {
			clickButtonByInputValue("Update About You");
			verifyPageHeader(".top-title", "Update About You");
			//updateDetailsForTeacher("GradesTaughtChk");
			updatedValues=updateDetailsForTeacher("SubjectsTaughtChk");
		}
		logMessage("Step: Update the details of About You for " + memberType + "\n");
		return updatedValues;
	}

	public List<String> updateDetailsForTeacher(String value) {
		getCheckedValuesOnUpdateAboutYou(value);
		return checkTheValuesOnUpdateAboutYou(value);

	}

	public void verifyPageHeader(String locator, String title) {

		System.out.println("title::" + element("title_header", "top-title").getText().trim());
		Assert.assertTrue(title.equals(element("title_header", "top-title").getText().trim()));
		logMessage("ASSERT PASSED: Page header is " + title + "\n");
	}

	public void getCheckedValuesOnUpdateAboutYou(String value) {
		isElementDisplayed("chked_labelsOnUpdateAboutYou", value);
		// element("chked_labels").isSelected();
		int size = elements("chked_labelsOnUpdateAboutYou", value).size();
		System.out.println("Size::" + size);
		while (size > 0) {
			element("chked_labelsOnUpdateAboutYou", value).click();
			System.out.println("unchecked");
			size--;
		}
		logMessage("All checked values are unchecked \n");
	}

	public List<String> checkTheValuesOnUpdateAboutYou(String value) {
		// String checkedValues[]={};
		List<String> checkedValues = new ArrayList<String>();
		int size = elements("unchked_label", value).size();
		System.out.println("size::" + size);
		for (int i = size - 1; i > size - 5; i--) {
			
			System.out.println("checked values " +elements("unchked_label", value).get(i).getText());
			checkedValues.add(elements("unchked_label", value).get(i).getText());
			elements("unchked_label", value).get(i).click();
		}

		logMessage("Step: values of '" + value + "' field are updated \n");
		return checkedValues;
	}

	public void enterGenderAndExperienceDetails(String gender, String experience) {
		if (memberType.equalsIgnoreCase("Teacher") || memberType.equalsIgnoreCase("Student")) {
			isElementDisplayed("inp_demographicInfo", "YearsExp");
			element("inp_demographicInfo", "YearsExp").sendKeys(experience);
		}
		selectProvidedTextFromDropDown(element("list_demographicInfo", "Gender"), gender);
	}

	public void verifyDetailsOfUpdateAboutYou(List<String> checkedValues) {
		
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
		isElementDisplayed("inp_cardInfo" , creditCardInfo);
		element("inp_cardInfo" , creditCardInfo).sendKeys(value);
		logMessage("STEP :"+ creditCardInfo + "is selected  as "+ value +"\n");
	}

	private void selectCreditCardInfo(String creditCardInfo, String value) {
		isElementDisplayed("list_cardInfo", creditCardInfo);
		wait.waitForPageToLoadCompletely();
		selectDropDownValue(value);
		logMessage("STEP :"+ creditCardInfo + "is selected  as "+ value +"\n");
		
	}

}
