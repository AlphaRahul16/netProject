package com.qait.keywords;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.YamlReader;

public class ASM_AACTPage extends ASCSocietyGenericPage {
	WebDriver driver;
	static String pagename = "ASM_AACTPage";
	boolean flag;
	Map<String, Object> mapASM_AACTSmoke = YamlReader.getYamlValues("AACT_SmokeChecklistData");
	YamlInformationProvider getKeyValueAACT = new YamlInformationProvider(mapASM_AACTSmoke);
	String memberType;

	String address = getKeyValueAACT.get_AACTAboutYouInfo("address");
	String city = getKeyValueAACT.get_AACTAboutYouInfo("city");
	String country = getKeyValueAACT.get_AACTAboutYouInfo("country");
	String state = getKeyValueAACT.get_AACTAboutYouInfo("state");
	String zipCode = getKeyValueAACT.get_AACTAboutYouInfo("zipCode");
	String instructionalSetting = getKeyValueAACT.get_AACTAboutYouInfo("instructionalSetting");
	String grade = getKeyValueAACT.get_AACTAboutYouInfo("grade");
	String subject = getKeyValueAACT.get_AACTAboutYouInfo("subject");

	public ASM_AACTPage(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

	public void verifyInvalidEmailFormat() {
		isElementDisplayed("txt_invalidEmailFormate");
		logMessage(
				"ASSERT PASSED : Invalid email format occured on enter invalid email id in txt_invalidEmailFormate\n");
	}

	public void selectAddressType(String addressType) {
		for (WebElement element : elements("list_addressType")) {
			if (element.getText().equalsIgnoreCase(addressType)) {
				click(element);
				logMessage("STEP : Address type " + addressType + " is selected in list_addressType\n");
			}
		}
		if (addressType.equalsIgnoreCase("Work/School")) {
			wait.waitForElementToBeVisible(element("inp_organization"));
		}
	}

	public void enterValidInformationAtAboutYouPage() {
		enterMemberContactDetail("AddressLine1", address);
		enterMemberContactDetail("City", city);
		selectMemberContactDetail("Country", country);
		if (country.equalsIgnoreCase("UNITED STATES")) {
			selectMemberContactDetail("State", state);
			enterMemberContactDetail("ZipCode", zipCode);
		} else {
			wait.waitForElementToDisappear(element("list_schoolWorkInfo", "State"));
		}
		selectDemographicDetail("Gender", "Male");
		wait.hardWait(5);
		wait.waitForPageToLoadCompletely();

		enterDemographicDetail("YearsExp", "10");
		checkMemberContactDetail(instructionalSetting);
		checkMemberContactDetail(grade);
		checkMemberContactDetail(subject);
	}

	public void enterMemberContactDetail(String detailType, String detailValue) {
		try {
			if (!detailValue.equalsIgnoreCase("null")) {
				wait.waitForPageToLoadCompletely();
				wait.hardWait(2);
				isElementDisplayed("inp_schoolWorkInfo", detailType);
				element("inp_schoolWorkInfo", detailType).clear();
				element("inp_schoolWorkInfo", detailType).sendKeys(detailValue);
				logMessage("STEP : " + detailValue + " entered in the " + detailType + "\n");
			} else {
				logMessage("Member detail is not present in data sheet\n");
			}
		} catch (StaleElementReferenceException stlExp) {
			if (!detailValue.equalsIgnoreCase("null")) {
				wait.waitForPageToLoadCompletely();
				wait.hardWait(2);
				isElementDisplayed("inp_schoolWorkInfo", detailType);
				element("inp_schoolWorkInfo", detailType).clear();
				element("inp_schoolWorkInfo", detailType).sendKeys(detailValue);
				logMessage("STEP : " + detailValue + " entered in the " + detailType + "\n");
			} else {
				logMessage("Member detail is not present in data sheet\n");
			}
		}

	}

	public void verifyMemberContactDetail(String detailType, String detailValue) {
		if (!detailValue.equalsIgnoreCase("null")) {
			try {
				isElementDisplayed("inp_schoolWorkInfo", detailType);
			} catch (StaleElementReferenceException stlExp) {
				isElementDisplayed("inp_schoolWorkInfo", detailType);
			}
			try {
				Assert.assertTrue(element("inp_schoolWorkInfo", detailType).getAttribute("value").trim()
						.equalsIgnoreCase(detailValue));
			} catch (StaleElementReferenceException stlExp) {
				Assert.assertTrue(element("inp_schoolWorkInfo", detailType).getAttribute("value").trim()
						.equalsIgnoreCase(detailValue));
			}
			logMessage("ASSERT PASSED : " + detailValue + " is verified in " + detailType + "\n");
		} else {
			logMessage(detailType + " value is not present in data sheet\n");
		}

	}

	public void verifyMemberContactDetails(String caseId) {
		verifyMemberContactDetail("Organization", getAACT_OmaSheetValue(caseId, "Organization Contact Page"));
		verifyMemberContactDetail("DeptMail", getAACT_OmaSheetValue(caseId, "Dept/Mail Stop Contact Page"));
		verifyMemberContactDetail("AddressLine1", getAACT_OmaSheetValue(caseId, "Address Contact Page"));
		verifyMemberContactDetail("City", getAACT_OmaSheetValue(caseId, "City Contact Page"));
		String countrySelected = verifySelectedDropDownValue("Country",
				getAACT_OmaSheetValue(caseId, "Country Contact Page"));

		if (countrySelected.equalsIgnoreCase("UNITED STATES")) {
			verifySelectedDropDownValue("State", getAACT_OmaSheetValue(caseId, "State Contact Page"));
		}
		verifyMemberContactDetail("ZipCode", getAACT_OmaSheetValue(caseId, "Zip code Contact Page"));

	}

	public String verifySelectedDropDownValue(String detailType, String detailValue) {
		if (!detailValue.equalsIgnoreCase("null")) {
			isElementDisplayed("txt_selectedDetail", detailType);
			Assert.assertTrue(element("txt_selectedDetail", detailType).getText().equalsIgnoreCase(detailValue));
			logMessage("AASERT PASSED : " + detailValue + " is verified for " + detailType + " \n");
			return detailValue;
		} else {
			logMessage(detailType + " value is not present in data sheet\n");
			return null;
		}

	}

	public void selectMemberContactDetail(String detailType, String detailValue) {
		if (!detailValue.equalsIgnoreCase("null")) {
			wait.hardWait(3);
			isElementDisplayed("list_schoolWorkInfo", detailType);
			selectProvidedTextFromDropDown(element("list_schoolWorkInfo", detailType), detailValue);
			logMessage("Step: " + detailValue + " selected in the " + detailType + "\n");
		} else {
			logMessage(detailType + " value is not present in data sheet\n");

		}
	}

	public void checkMemberContactDetail(String detailValue) {
		if (!detailValue.equalsIgnoreCase("null")) {
			isElementDisplayed("chk_atAboutYouPage", detailValue);
			element("chk_atAboutYouPage", detailValue).click();
			logMessage("STEP : " + detailValue + " is checked in the chk_atAboutYouPage\n");
		} else {
			logMessage("Member detail is not present in data sheet\n");
		}

	}

	public void verifyPaymentErrorPresent() {
		isElementDisplayed("txt_paymentError");
		logMessage("ASSERT PASSED : Payment error is verified in txt_paymentError\n");
	}

	public void enterMemberDetailsAtAboutYouPage(String caseId) {
		isSelectChemistryTeacher(caseId);
		isSelectStudentMember(caseId);
		isSelectAffiliateMember(caseId);

	}

	public void selectMemberType(String memberType) {
		if (!memberType.equalsIgnoreCase("null")) {
			hardWaitForIEBrowser(4);
			isElementDisplayed("rad_memberType", memberType);
			if (!element("rad_memberType", memberType).isSelected()) {
				clickUsingXpathInJavaScriptExecutor(element("rad_memberType", memberType));
				// element("rad_memberType", memberType).click();
				logMessage("STEP : Select member type " + memberType + "\n");
			} else {
				logMessage("STEP : member type " + memberType + " is already selected \n");
			}
		} else {
			logMessage("Member type is not present in data sheet\n");
		}

	}

	public void selectDemographicDetail(String detailType, String detailValue) {
		if (!detailValue.equalsIgnoreCase("null")) {
			hardWaitForChromeBrowser(2);
			isElementDisplayed("list_demographicInfo", detailType);
			wait.waitForElementToBeClickable(element("list_demographicInfo", detailType));
			selectProvidedTextFromDropDown(element("list_demographicInfo", detailType), detailValue);
			logMessage("STEP : " + detailValue + " is selected in " + detailType + "\n");
		} else {
			logMessage(detailType + " value is not present in data sheet\n");
		}

	}

	public void enterDemographicDetail(String detailType, String detailValue) {
		if (!detailValue.equalsIgnoreCase("null")) {
			isElementDisplayed("inp_demographicInfo", detailType);
			element("inp_demographicInfo", detailType).clear();
			element("inp_demographicInfo", detailType).sendKeys(detailValue);
			logMessage("STEP : " + detailValue + " is entered in " + detailType + "\n");
		} else {
			logMessage(detailType + " value is not present in data sheet\n");
		}
	}

	public void verifyChemistryTeacherExpFieldVisibility(String caseId) {
		verifyFieldVisibility("inp_demographicInfo", "YearsExp",
				getAACT_OmaSheetValue(caseId, "Has Chemistry Teaching Experience?"));
	}

	public void verifyExpectedGraduationMonthFieldVisibility(String caseId) {
		verifyFieldVisibility("list_demographicInfo", "GradMonth",
				getAACT_OmaSheetValue(caseId, "Has Expected Graduation Month?"));

	}

	public void verifyExpectedGraduationYearFieldVisibility(String caseId) {
		verifyFieldVisibility("list_demographicInfo", "GradYear",
				getAACT_OmaSheetValue(caseId, "Has Expected Graduation Year?"));

	}

	public void isSelectChemistryTeacher(String caseId) {
		if (getAACT_OmaSheetValue(caseId, "Teacher Member").equalsIgnoreCase("Y")) {
			selectMemberType("Teacher");
			if (!getAACT_OmaSheetValue(caseId, "Address Type").equalsIgnoreCase("Work/School")) {
				enterMemberContactDetail("Organization", getAACT_OmaSheetValue(caseId, "Organization About You Page"));
				enterMemberContactDetail("DeptMail", getAACT_OmaSheetValue(caseId, "Dept/Mail Stop About You Page"));
				enterMemberContactDetail("AddressLine1", getAACT_OmaSheetValue(caseId, "address About You Page"));
				enterMemberContactDetail("City", getAACT_OmaSheetValue(caseId, "City About You Page"));
				selectMemberContactDetail("Country", getAACT_OmaSheetValue(caseId, "country About You Page"));
				if (getAACT_OmaSheetValue(caseId, "Country About You Page").equalsIgnoreCase("UNITED STATES")) {
					selectMemberContactDetail("State", getAACT_OmaSheetValue(caseId, "state About You Page"));
				} else {
					wait.waitForElementToDisappear(element("list_schoolWorkInfo", "State"));
				}
				enterMemberContactDetail("ZipCode", getAACT_OmaSheetValue(caseId, "Zip code About You Page"));
			} else {
				verifyMemberContactDetails(caseId);
			}

			selectDemographicDetail("Gender", getAACT_OmaSheetValue(caseId, "Gender"));
			wait.hardWait(5);
			wait.waitForPageToLoadCompletely();
			verifyChemistryTeacherExpFieldVisibility(caseId);
			verifyExpectedGraduationMonthFieldVisibility(caseId);
			verifyExpectedGraduationYearFieldVisibility(caseId);
			enterDemographicDetail("YearsExp", getAACT_OmaSheetValue(caseId, "Chemistry Teaching Experience"));
			checkMemberContactDetail(getAACT_OmaSheetValue(caseId, "Instructional settings"));
			checkMemberContactDetail(getAACT_OmaSheetValue(caseId, "Grades"));
			checkMemberContactDetail(getAACT_OmaSheetValue(caseId, "Subjects"));
		}

		else if (getAACT_OmaSheetValue(caseId, "Teacher Member").equalsIgnoreCase("N")) {
			logMessage("Teacher member is not to select\n");
		} else {
			logMessage("Teacher member value is not present in data sheet\n");
		}
	}

	public void isSelectStudentMember(String caseId) {
		if (getAACT_OmaSheetValue(caseId, "Student Member").equalsIgnoreCase("Y")) {
			selectMemberType("Student");

			if (!getAACT_OmaSheetValue(caseId, "Address Type").equalsIgnoreCase("Work/School")) {
				enterMemberContactDetail("Organization", getAACT_OmaSheetValue(caseId, "Organization About You Page"));
				enterMemberContactDetail("DeptMail", getAACT_OmaSheetValue(caseId, "Dept/Mail Stop About You Page"));
				enterMemberContactDetail("AddressLine1", getAACT_OmaSheetValue(caseId, "address About You Page"));
				enterMemberContactDetail("City", getAACT_OmaSheetValue(caseId, "City About You Page"));
				wait.hardWait(2);
				selectMemberContactDetail("Country", getAACT_OmaSheetValue(caseId, "Country About You Page"));
				if (getAACT_OmaSheetValue(caseId, "Country About You Page").equalsIgnoreCase("UNITED STATES")) {
					selectMemberContactDetail("State", getAACT_OmaSheetValue(caseId, "state About You Page"));
				} else {
					try {
						wait.waitForElementToDisappear(element("list_schoolWorkInfo", "State"));
					} catch (Exception E) {
						logMessage("list_schoolWorkInfo not visible");
					}
				}
				selectMemberContactDetail("Country", getAACT_OmaSheetValue(caseId, "Country About You Page"));
				enterMemberContactDetail("ZipCode", getAACT_OmaSheetValue(caseId, "Zip code About You Page"));
			} else {
				verifyMemberContactDetails(caseId);
			}

			selectDemographicDetail("Gender", getAACT_OmaSheetValue(caseId, "Gender"));
			wait.hardWait(5);
			wait.waitForPageToLoadCompletely();
			verifyChemistryTeacherExpFieldVisibility(caseId);
			verifyExpectedGraduationMonthFieldVisibility(caseId);
			verifyExpectedGraduationYearFieldVisibility(caseId);
			wait.hardWait(2);
			enterDemographicDetail("YearsExp", getAACT_OmaSheetValue(caseId, "Chemistry Teaching Experience"));
			selectDemographicDetail("GradMonth", getAACT_OmaSheetValue(caseId, "Expected Graduation Month"));
			wait.hardWait(5);
			wait.waitForPageToLoadCompletely();
			selectDemographicDetail("GradYear", getAACT_OmaSheetValue(caseId, "Expected Graduation Year"));
			wait.hardWait(5);
			wait.waitForPageToLoadCompletely();
			checkMemberContactDetail(getAACT_OmaSheetValue(caseId, "Instructional settings"));
			checkMemberContactDetail(getAACT_OmaSheetValue(caseId, "Grades"));
			checkMemberContactDetail(getAACT_OmaSheetValue(caseId, "Subjects"));
		} else if (getAACT_OmaSheetValue(caseId, "Student Member").equalsIgnoreCase("N")) {
			logMessage("Student member is not to select\n");
		} else {
			logMessage("Student member value is not present in data sheet\n");
		}
	}

	public void isSelectAffiliateMember(String caseId) {
		if (getAACT_OmaSheetValue(caseId, "Affiliate Member").equalsIgnoreCase("Y")) {
			selectMemberType("Affiliate");
			wait.hardWait(3);
			enterAffiliateTitle(getAACT_OmaSheetValue(caseId, "Member Title"));
			selectDemographicDetail("Gender", getAACT_OmaSheetValue(caseId, "Gender"));
			verifyChemistryTeacherExpFieldVisibility(caseId);
			verifyExpectedGraduationMonthFieldVisibility(caseId);
			verifyExpectedGraduationYearFieldVisibility(caseId);
		} else if (getAACT_OmaSheetValue(caseId, "Affiliate Member").equalsIgnoreCase("N")) {
			logMessage("Affiliate member not to select\n");
		} else {
			logMessage("Affiliate member value is not present in data sheet\n");
		}

	}

	public void enterAffiliateTitle(String title) {
		if (!title.equalsIgnoreCase("null")) {
			isElementDisplayed("inp_affiliateTitle");
			element("inp_affiliateTitle").clear();
			element("inp_affiliateTitle").sendKeys(title);
			logMessage("STEP : " + title + " is entered in inp_affiliateTitle\n");
		} else {
			logMessage("Effiliate title in data sheet is not present\n");
		}

	}

	public void verifyMemberOrNonMemberDetails() {
		// TODO Auto-generated method stub

	}

	
	

}
