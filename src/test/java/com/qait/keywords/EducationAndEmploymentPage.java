package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;
import static com.qait.automation.utils.YamlReader.getYamlValue;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.DateUtil;

public class EducationAndEmploymentPage extends ASCSocietyGenericPage {

	WebDriver driver;
	DateUtil date1;
	int timeOut, hiddenFieldTimeOut;
	String pagename = "EducationAndEmploymentPage";
	String studentAvailable, majorInChemistryValue, chemistryTeacherValue,
			subToken, seasonMonthFrom, month, year, isSummerMaillingAdd,

			hasChemistryTeacher, degree, hasChemistryProfessionalExp,

			employerNameUnique, currentStudent, chemistryTeacher,
			summerMaillingAdd, warningMessageWithQuotes, warningMessage,
			stringWithoutQuotes, pastYear, pastMonth, futureYear, futureMonth;

	String csvdatafilepath_OMA = getYamlValue("csv-data-file.path_OMA");
	String csvdatafilepath_PriceValues = getYamlValue("csv-data-file.path_PriceValue");
	String csvSeparator = getYamlValue("csv-data-file.data-separator");

	public EducationAndEmploymentPage(WebDriver driver) {
		super(driver, "EducationAndEmploymentPage");
		this.driver = driver;
	}

	private void isMajorChemistry(String degreeWithPastDate,
			String degreeWithFutureDate, String major, String month,
			String year, String hasChemistryTeacher, String yearText) {
		majorInChemistryValue = "Yes";
		date1 = new DateUtil();
		pastYear = date1.getDate("Over 1 year ago")[0];
		pastMonth = date1.getDate("Over 1 year ago")[1];
		futureYear = date1.getDate("Over 30 days from now")[0];
		futureMonth = date1.getDate("Over 30 days from now")[1];

		clickRadioButton_Detail("majorInChemistry", majorInChemistryValue);
		if (degreeWithFutureDate.equalsIgnoreCase("Null")
				&& !(degreeWithPastDate.equalsIgnoreCase("Null"))) {
			selectPastDegreeWithOtherDetail(degreeWithPastDate, major, month,
					year);
		} else if (degreeWithPastDate.equalsIgnoreCase("Null")
				&& !degreeWithFutureDate.equalsIgnoreCase("Null")) {
			selectFutureDegreeWithOtherDetail(degreeWithFutureDate, major,
					month, year, degreeWithPastDate);
		} else if (!degreeWithPastDate.equalsIgnoreCase("Null")
				&& !degreeWithFutureDate.equalsIgnoreCase("Null")) {
			if (yearText.equalsIgnoreCase("Over 1 year ago")
					|| yearText.equalsIgnoreCase("Within past year")) {
				selectPastDegreeWithOtherDetail(degreeWithPastDate, major,
						month, year);
				selectFutureDegreeWithOtherDetail(degreeWithFutureDate, major,
						futureMonth, futureYear, degreeWithPastDate);
			} else if (yearText.equalsIgnoreCase("Over 30 days from now")
					|| yearText.equalsIgnoreCase("Within 30 days from now")) {
				selectPastDegreeWithOtherDetail(degreeWithPastDate, major,
						pastMonth, pastYear);
				selectFutureDegreeWithOtherDetail(degreeWithFutureDate, major,
						month, year, degreeWithPastDate);
			}
		}
		verifyFieldVisibility("lbl_chemistryTeacher", hasChemistryTeacher);
	}

	public void selectPastDegreeWithOtherDetail(String degree, String major,
			String month, String year) {
		if (degree.equalsIgnoreCase("NULL")) {
			logMessage("Step: degree is null\n");
		} else {
			String[] temp = degree.split("\\_");
			int degreeCount = temp.length;
			if (degreeCount <= 1) {
				fillDegree(temp[0], 1);
				selectListOfDropdown("major", major, 1);
				selectListOfDropdown("month", month, 1);
				selectListOfDropdown("year", year, 1);
				click(element("btn_addAnotherDegree"));
				holdExecution(2000);
			} else {
				for (int i = 1; i <= degreeCount; i++) {
					fillDegree(temp[i - 1], i);
					selectListOfDropdown("major", major, i);
					selectListOfDropdown("month", month, i);
					selectListOfDropdown("year", year, i);
					if (!(degreeCount == i)) {
						click(element("btn_addAnotherDegree"));
					}
				}
			}
		}

	}

	public void selectFutureDegreeWithOtherDetail(String degree, String major,
			String month, String year, String degreeWithPastDate) {
		if (degree.equalsIgnoreCase("NULL")) {
			logMessage("Step: degree is null\n");
		} else {
			String[] temp = degree.split("\\_");
			int degreeCount = temp.length;
			if (degreeCount <= 1) {
				if (!degreeWithPastDate.equalsIgnoreCase("Null")) {
					fillDegree(temp[0], 2);
					selectListOfDropdown("major", major, 2);
					selectListOfDropdown("month", month, 2);
					selectListOfDropdown("year", year, 2);
					click(element("btn_addAnotherDegree"));
					holdExecution(2000);
				} else {
					fillDegree(temp[0], 1);
					selectListOfDropdown("major", major, 1);
					selectListOfDropdown("month", month, 1);
					selectListOfDropdown("year", year, 1);
					click(element("btn_addAnotherDegree"));
					holdExecution(2000);
				}

			} else {
				for (int i = 1; i <= degreeCount; i++) {
					fillDegree(temp[i - 1], i);
					selectListOfDropdown("major", major, i);
					selectListOfDropdown("month", month, i);
					selectListOfDropdown("year", year, i);
					if (!(degreeCount == i)) {
						click(element("btn_addAnotherDegree"));
					}
				}
			}
		}

	}

	private void isMajorChemistry(String chemistryTeacherStatus,
			String hasChemistryTeacher) {
		majorInChemistryValue = "No";
		clickRadioButton_Detail("majorInChemistry", majorInChemistryValue);
		verifyFieldVisibility("lbl_chemistryTeacher", hasChemistryTeacher);
		if (chemistryTeacherStatus.equalsIgnoreCase("Y")) {
			chemistryTeacherValue = "Yes";
			clickRadioButton_Detail("chemistryTeacher", chemistryTeacherValue);
		} else if (chemistryTeacherStatus.equalsIgnoreCase("N")) {
			chemistryTeacherValue = "No";
			clickRadioButton_Detail("chemistryTeacher", chemistryTeacherValue);
		} else if (!chemistryTeacherStatus.equalsIgnoreCase("")) {
			logMessage("Step : chemistry Teacher Status is not valid in data sheet");
			Assert.fail("ASSERT FAILED : chemistry Teacher Status is not valid in data sheet");
		} else {
			logMessage("Step : chemistry Teacher Status is not present in data sheet");
		}
	}

	public void fillDegree(String degree, int index) {
		if (degree.equalsIgnoreCase("AA")) {
			degree = "Associate";
			selectListOfDropdown("Degree", degree, index);
		}
		if (degree.equalsIgnoreCase("BS")) {
			degree = "Bachelor";
			selectListOfDropdown("Degree", degree, index);
		}
		if (degree.equalsIgnoreCase("MS")) {
			degree = "Master";
			selectListOfDropdown("Degree", degree, index);
		}
		if (degree.equalsIgnoreCase("PhD")) {
			degree = "Doctorate";
			selectListOfDropdown("Degree", degree, index);
		}
		if (degree.equalsIgnoreCase("MS-PhD")) {
			degree = "Doctorate";
			selectListOfDropdown("Degree", degree, index);
		}

	}

	private void selectEduAndEmpDetail(String detailtype, String detailValue) {
		if (detailValue.equalsIgnoreCase("")
				|| detailValue.equalsIgnoreCase("NULL")) {
			logMessage("Step : " + detailtype
					+ " value is null or invalid to select in drop down");
		} else {
			isElementDisplayed("list_" + detailtype, detailValue);
			selectProvidedTextFromDropDown(element("list_" + detailtype),
					detailValue);
			logMessage("Step: " + detailValue + " selected in the list_"
					+ detailtype + "\n");
		}

	}

	private void selectListOfDropdown(String detailtype, String detailValue,
			int sizeOfList) {
		if (detailValue.equalsIgnoreCase("")
				|| detailValue.equalsIgnoreCase("NULL")) {
			logMessage("Step : " + detailtype
					+ " value is null or blank to select from list");
		} else {
			try {
				isElementDisplayed("list_" + detailtype,
						Integer.toString(sizeOfList));
				selectProvidedTextFromDropDown(
						element("list_" + detailtype,
								Integer.toString(sizeOfList)), detailValue);
				logMessage("Step: " + detailValue + " selected in the list_"
						+ detailtype + "\n");
			} catch (StaleElementReferenceException stlExp) {
				isElementDisplayed("list_" + detailtype,
						Integer.toString(sizeOfList));
				selectProvidedTextFromDropDown(
						element("list_" + detailtype,
								Integer.toString(sizeOfList)), detailValue);
				logMessage("Step: " + detailValue + " selected in the list_"
						+ detailtype + "\n");
			}

		}

	}

	private void clickRadioButton_Detail(String detailtype, String detailValue) {
		isElementDisplayed("rad_" + detailtype, detailValue);
		clickUsingXpathInJavaScriptExecutor(element("rad_" + detailtype, detailValue));
		//click(element("rad_" + detailtype, detailValue));
		logMessage("Step: " + detailValue + " is checked in rad_" + detailtype
				+ "\n");
	}

	private void currentStudentYes(String universityName, String detailType,
			String isUniversity) {
		studentAvailable = "Yes";
		clickRadioButton_Detail("currentStudent", studentAvailable);
		verifyFieldVisibility("inp_universityName", isUniversity);
		enterEduAndEmpDetail(detailType, universityName);

	}

	private void enterEduAndEmpDetail(String detailType, String detailvalue) {
		if (detailvalue.equalsIgnoreCase("")) {
			logMessage("Step : " + detailType
					+ " value is not present in data sheet\n");
		} else {
			isElementDisplayed("inp_" + detailType);
			element("inp_" + detailType).sendKeys(detailvalue);
			logMessage("Step: " + detailvalue + " enetered in the inp_"
					+ detailType + "\n");
		}

	}

	private void currentStudentNo(String isUniversity) {
		studentAvailable = "No";
		clickRadioButton_Detail("currentStudent", studentAvailable);
		verifyFieldVisibility("inp_universityName", isUniversity);
	}

	public void enterEmployerDetails(String caseId) {
		verifyEmployerNameFieldVisibility(getOmaSheetValue(caseId,
				"Has Employer?"));
		if (getOmaSheetValue(caseId, "Has Employer?").equalsIgnoreCase("Show")) {
			employerNameUnique = getOmaSheetValue(caseId, "Enter Employer Name")
					+ System.currentTimeMillis();
			enterEduAndEmpDetail("employerName", employerNameUnique);
		}
		verifyIndustryTypeFieldVisibility(getOmaSheetValue(caseId,
				"Has Employer Industry category?"));
		if (getOmaSheetValue(caseId, "Has Employer Industry category?")
				.equalsIgnoreCase("Show")) {
			selectEduAndEmpDetail("industryType",
					getOmaSheetValue(caseId, "Select Industry Category"));
		}
		verifyPrimaryTitleFieldVisibility(getOmaSheetValue(caseId,
				"Has Primary title category"));
		if (getOmaSheetValue(caseId, "Has Primary title category")
				.equalsIgnoreCase("Show")) {
			selectEduAndEmpDetail("occupationTitle",
					getOmaSheetValue(caseId, "Select Primary Title"));
		} else {
			logMessage("Step: employer details not present \n");
		}
	}

	public void verifyEmployerNameFieldVisibility(String hasEmployer) {
		verifyFieldVisibility("inp_employerName", hasEmployer);
	}

	public void isSummerMailingAddress(String caseId) {
		if (getOmaSheetValue(caseId, "Has Summer mailing address?")
				.equalsIgnoreCase("Show")) {
			summerMaillingAdd = "Yes";
			isElementDisplayed("txt_summerMailingAdd");
			clickRadioButton_Detail("summerMaillingAdd", summerMaillingAdd);
			selectEduAndEmpDetail(
					"seasonMonthFrom",
					getOmaSheetValue(caseId,
							"Select Summer mailing  start month"));
			holdExecution(3000);
			wait.waitForPageToLoadCompletely();
			selectEduAndEmpDetail("seasonDayFrom",
					getOmaSheetValue(caseId, "Select Summer mailing start day"));
			holdExecution(2000);
			wait.waitForPageToLoadCompletely();
			selectEduAndEmpDetail("seasonMonthTo",
					getOmaSheetValue(caseId, "Select Summer mailing end month"));
			holdExecution(2000);
			wait.waitForPageToLoadCompletely();
			selectEduAndEmpDetail("seasonDayTo",
					getOmaSheetValue(caseId, "Select Summer mailing end day"));
			

			holdExecution(2000);
			wait.waitForPageToLoadCompletely();
			enterEduAndEmpDetail("address",
					getOmaSheetValue(caseId, "Enter Summer mailing address"));
			enterEduAndEmpDetail("city",
					getOmaSheetValue(caseId, "Enter  Summer city"));
			selectEduAndEmpDetail("country",
					getOmaSheetValue(caseId, "Select  Summer country"));
			if (!getOmaSheetValue(caseId, "Select  Summer country")
					.equalsIgnoreCase("UNITED STATES"))
				wait.waitForElementToDisappear(element("list_state"));
			else
				selectEduAndEmpDetail("state",
						getOmaSheetValue(caseId, "Select  Summer state"));
			enterEduAndEmpDetail("zipCode",
					getOmaSheetValue(caseId, "Enter  Summer zip code"));
		} else {
			logMessage("Step: summer mailing address not present \n");
		}

	}

	public void enterEducationAndEmploymentInformation(String caseId) {
		date1 = new DateUtil();
		try {
			year = date1.getDate(getOmaSheetValue(caseId,
					"Evaluate degree type and enter past/future date"))[0];
			month = date1.getDate(getOmaSheetValue(caseId,
					"Evaluate degree type and enter past/future date"))[1];
		} catch (NullPointerException ex) {
			logMessage("date not present to enter or not applicable in data sheet\n");
		}

		if (getOmaSheetValue(caseId, "Current Student Status")
				.equalsIgnoreCase("Y")) {
			currentStudentYes(
					getOmaSheetValue(caseId, "Enter University Name"),
					"universityName",
					getOmaSheetValue(caseId, "Has College or University ?"));
		} else if (getOmaSheetValue(caseId, "Current Student Status")
				.equalsIgnoreCase("N")) {
			currentStudentNo(getOmaSheetValue(caseId,
					"Has College or University ?"));
		} else {
			logMessage("student status is invalid in data sheet");
			Assert.fail("ASSERT FAILED: current student status is not valid in data sheet");
		}
		if (getOmaSheetValue(caseId, "Chemistry Major Status")
				.equalsIgnoreCase("Y")) {
			isMajorChemistry(
					getOmaSheetValue(caseId, "Select Degree(s) with past date"),
					getOmaSheetValue(caseId,
							"Select Degree(s) with future date"),
					getOmaSheetValue(caseId, "Select Chemistry Major value"),
					month,
					year,
					getOmaSheetValue(caseId, "Has Chemistry teacher?"),
					getOmaSheetValue(caseId,
							"Evaluate degree type and enter past/future date"));
		} else if (getOmaSheetValue(caseId, "Chemistry Major Status")
				.equalsIgnoreCase("N")) {
			isMajorChemistry(
					getOmaSheetValue(caseId, "Chemistry Teacher Status"),
					getOmaSheetValue(caseId, "Has Chemistry teacher?"));
		} else {
			logMessage("chemistry status is invalid in data sheet");
			Assert.fail("ASSERT FAILED: chemistry major status is not valid in data sheet");
		}
		verifySummerMailingAddressFiledVisibility(getOmaSheetValue(caseId,
				"Has Summer mailing address?"));
		isSummerMailingAddress(caseId);
		enterEmployerDetails(caseId);
		verifyJobExpFieldVisibility(getOmaSheetValue(caseId,
				"Has Chemistry professional experience?"));
		enterJobExperience(
				getOmaSheetValue(caseId,
						"Has Chemistry professional experience?"),
				getOmaSheetValue(caseId, "Enter Job Experience Value"));
	}

	private void verifyIndustryTypeFieldVisibility(String hasIndustryCategory) {
		verifyFieldVisibility("list_industryType", hasIndustryCategory);
	}

	private void verifyPrimaryTitleFieldVisibility(String hasPrimaryTitle) {
		verifyFieldVisibility("list_occupationTitle", hasPrimaryTitle);
	}

	private void verifyJobExpFieldVisibility(String isJobExp) {
		verifyFieldVisibility("txtAr_professionalExp", isJobExp);
	}

	private void verifySummerMailingAddressFiledVisibility(
			String isSummerMaillingAdd) {
		verifyFieldVisibility("txt_summerMailingAdd", isSummerMaillingAdd);
	}

	public void enterJobExperience(String isJobExp, String jobExp) {
		if (isJobExp.equalsIgnoreCase("SHOW")) {
			element("txtAr_professionalExp").sendKeys(jobExp);
			logMessage("Step : job experience " + jobExp
					+ " is entered in txtAr_professionalExp\n");
		}
	}

	public boolean verifyDisplayedMessage(String caseId) {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		warningMessage = getOmaSheetValue(caseId, "Error messaging?");
		String messageType = "No";
		if (!warningMessage.equalsIgnoreCase("")) {
			isElementDisplayed("lbl_warnings");
			String actualMsg = element("lbl_warnings").getText().trim();
			if (element("lbl_warnings").getAttribute("id").contains("Warning")) {
				messageType = "Warning";
			} else if (element("lbl_warnings").getAttribute("id").contains(
					"Error")) {
				messageType = "Error";
			}
			logMessage(messageType + " message is displayed. Message : "
					+ actualMsg);
			boolean flag = actualMsg.equals(warningMessage);
			Assert.assertTrue(actualMsg.equals(warningMessage),
					"Expected and Actual " + messageType
							+ " messages differ. Message expected : "
							+ warningMessage + "  Message found : " + actualMsg);
			logMessage("ASSERT PASSED : Verified the displayed " + messageType
					+ " message successfully.\n Expected Message : "
					+ warningMessage + "\n Actual Message : " + actualMsg);
			return flag;
		} else {
			try {
				wait.resetImplicitTimeout(0);
				wait.resetExplicitTimeout(hiddenFieldTimeOut);
				hardWaitForIEBrowser(5);
				if (isElementDisplayed("lbl_warnings")) {
					wait.resetImplicitTimeout(timeOut);
					wait.resetExplicitTimeout(timeOut);
					Assert.fail("ASSERT FAILED : Expected error message occur while error message is empty in data sheet\n");

				}
			} 
			catch (Exception e){
			//catch (NoSuchElementException e) {
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
				logMessage("Step : Error message is empty\n");
			}

			return false;
		}
	}

	public void enterEmployerName(String employerName) {
		enterEduAndEmpDetail("employerName", employerName);
	}

	public void enterJobProfessionalExp(String jobExp) {
		element("txtAr_professionalExp").sendKeys(jobExp);
		logMessage("Step : job experience value " + jobExp + " is entered");
	}

	public void enterEducationAndEmploymentInformation_ASMOMA() {
		clickRadioButton_Detail("currentStudent", "No");
		clickRadioButton_Detail("majorInChemistry", "No");
		clickRadioButton_Detail("chemistryTeacher", "No");
		enterEduAndEmpDetail("employerName", "employer name 123");
		selectEduAndEmpDetail("industryType", "Other");
		selectEduAndEmpDetail("occupationTitle", "Other");
	}

	public void enterEducationAndEmploymentInformation_ASMOMA_Student_Yes() {
		clickRadioButton_Detail("currentStudent", "Yes");
		clickRadioButton_Detail("majorInChemistry", "No");
		enterEduAndEmpDetail("employerName", "employer name 123");
		selectEduAndEmpDetail("industryType", "Other");
		selectEduAndEmpDetail("occupationTitle", "Other");
	}

	public void enterUniversityName_ASMOMA(String universityName) {
		studentAvailable = "Yes";
		clickRadioButton_Detail("currentStudent", studentAvailable);
		enterEduAndEmpDetail("universityName", universityName);

	}

}
