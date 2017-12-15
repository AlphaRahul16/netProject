package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;
import static com.qait.automation.utils.YamlReader.getYamlValue;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;

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
		verifyAddAnotherDegreeButtonPosition();
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
	
	public void verifyAddAnotherDegreeButtonPosition(){
		isElementDisplayed("btn_addAnotherDegreePosition");
		logMessage("Step: Add another degree button is right justified\n");
	}

	public void selectPastDegreeWithOtherDetail(String degree, String major,
			String month, String year) {
		
		
		if (degree.equalsIgnoreCase("NULL")) {
			logMessage("STEP : Degree is null\n");
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
			logMessage("STEP : Degree is null\n");
		} else {
			String[] temp = degree.split("\\_");
			int degreeCount = temp.length;
			if (degreeCount <= 1) {
				wait.hardWait(3);
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
			String hasChemistryTeacher,String expText) {
		System.out.println("has chem ------------" + hasChemistryTeacher);
		majorInChemistryValue = "No";
		clickRadioButton_Detail("majorInChemistry", majorInChemistryValue);
		verifyFieldVisibility("lbl_chemistryTeacher", hasChemistryTeacher);
		verifyTextUnderPreCollegeChemitryTeacher(expText);
		if (chemistryTeacherStatus.equalsIgnoreCase("Y")) {
			chemistryTeacherValue = "Yes";
			clickRadioButton_Detail("chemistryTeacher", chemistryTeacherValue);
		} else if (chemistryTeacherStatus.equalsIgnoreCase("N")) {
			chemistryTeacherValue = "No";
			clickRadioButton_Detail("chemistryTeacher", chemistryTeacherValue);
		} else if (!chemistryTeacherStatus.equalsIgnoreCase("")) {
			logMessage("STEP : chemistry Teacher Status is not valid in data sheet");
			Assert.fail("ASSERT FAILED : chemistry Teacher Status is not valid in data sheet");
		} else {
			logMessage("STEP : chemistry Teacher Status is not present in data sheet");
		}
	}
	
	public void verifyTextUnderPreCollegeChemitryTeacher(String expText){
		isElementDisplayed("txt_chemistryTeacher");
		Assert.assertEquals(element("txt_chemistryTeacher").getText().trim(), expText,
				"ASSERT FAILED: "+expText+" is not displayed under Pre-College Chemistry Teacher question\n");
		logMessage("ASSERT PASSED: "+expText+" is verified under Pre-College Chemistry Teacher question\n");
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
			logMessage("STEP : " + detailtype
					+ " value is null or invalid to select in drop down");
		} else {
			isElementDisplayed("list_" + detailtype, detailValue);
			selectProvidedTextFromDropDown(element("list_" + detailtype),
					detailValue);
			logMessage("STEP : " + detailValue + " selected in the list_"
					+ detailtype + "\n");
		}

	}

	private void selectListOfDropdown(String detailtype, String detailValue,
			int sizeOfList) {
		if (detailValue.equalsIgnoreCase("")
				|| detailValue.equalsIgnoreCase("NULL")) {
			logMessage("STEP : " + detailtype
					+ " value is null or blank to select from list");
		} else {
			try {
				isElementDisplayed("list_" + detailtype,
						Integer.toString(sizeOfList));
				selectProvidedTextFromDropDown(
						element("list_" + detailtype,
								Integer.toString(sizeOfList)), detailValue);
				logMessage("STEP : " + detailValue + " selected in the list_"
						+ detailtype + "\n");
			} catch (StaleElementReferenceException stlExp) {
				isElementDisplayed("list_" + detailtype,
						Integer.toString(sizeOfList));
				selectProvidedTextFromDropDown(
						element("list_" + detailtype,
								Integer.toString(sizeOfList)), detailValue);
				logMessage("STEP : " + detailValue + " selected in the list_"
						+ detailtype + "\n");
			}

		}

	}

	private void clickRadioButton_Detail(String detailtype, String detailValue) {
		isElementDisplayed("rad_" + detailtype, detailValue);
		clickUsingXpathInJavaScriptExecutor(element("rad_" + detailtype,
				detailValue));
		// click(element("rad_" + detailtype, detailValue));
		logMessage("STEP : " + detailValue + " is checked in rad_" + detailtype
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
			logMessage("STEP : " + detailType
					+ " value is not present in data sheet\n");
		} else {
			isElementDisplayed("inp_" + detailType);
			element("inp_" + detailType).sendKeys(detailvalue);
			logMessage("STEP : " + detailvalue + " enetered in the inp_"
					+ detailType + "\n");
		}

	}

	private void currentStudentNo(String isUniversity) {
		studentAvailable = "No";
		clickRadioButton_Detail("currentStudent", studentAvailable);
		verifyFieldVisibility("inp_universityName", isUniversity);
	}

	public void enterEmployerDetails(String caseId) {
		verifyEmployerNameFieldVisibility(map().get("Has Employer?"));
		if (map().get("Has Employer?").equalsIgnoreCase("Show")) {
			employerNameUnique = map().get("Enter Employer Name")
					+ System.currentTimeMillis();
			enterEduAndEmpDetail("employerName", employerNameUnique);
		}
		verifyIndustryTypeFieldVisibility(map().get(
				"Has Employer Industry category?"));
		if (map().get("Has Employer Industry category?").equalsIgnoreCase(
				"Show")) {
			selectEduAndEmpDetail("industryType",
					map().get("Select Industry Category"));
		}
		verifyPrimaryTitleFieldVisibility(map().get(
				"Has Primary title category"));
		if (map().get("Has Primary title category").equalsIgnoreCase("Show")) {
			selectEduAndEmpDetail("occupationTitle",
					map().get("Select Primary Title"));
		} else {
			logMessage("STEP : employer details not present \n");
		}
	}

	public void verifyEmployerNameFieldVisibility(String hasEmployer) {
		verifyFieldVisibility("inp_employerName", hasEmployer);
	}

	public void isSummerMailingAddress(String caseId) {
		if (map().get("Has Summer mailing address?").equalsIgnoreCase("Show")) {
			summerMaillingAdd = "Yes";
			isElementDisplayed("txt_summerMailingAdd");
			clickRadioButton_Detail("summerMaillingAdd", summerMaillingAdd);
			selectEduAndEmpDetail("seasonMonthFrom",
					map().get("Select Summer mailing  start month"));
			holdExecution(3000);
			wait.waitForPageToLoadCompletely();
			selectEduAndEmpDetail("seasonDayFrom",
					map().get("Select Summer mailing start day"));
			holdExecution(2000);
			wait.waitForPageToLoadCompletely();
			selectEduAndEmpDetail("seasonMonthTo",
					map().get("Select Summer mailing end month"));
			holdExecution(2000);
			wait.waitForPageToLoadCompletely();
			selectEduAndEmpDetail("seasonDayTo",
					map().get("Select Summer mailing end day"));

			holdExecution(2000);
			wait.waitForPageToLoadCompletely();
			enterEduAndEmpDetail("address",
					map().get("Enter Summer mailing address"));
			enterEduAndEmpDetail("city", map().get("Enter  Summer city"));
			selectEduAndEmpDetail("country", map()
					.get("Select  Summer country"));
			if (!map().get("Select  Summer country").equalsIgnoreCase(
					"UNITED STATES"))
				try {
					wait.waitForElementToDisappear(element("list_state"));
				} catch (Exception E) {
					logMessage("list_state does not exsist \n");
				}
			else
				selectEduAndEmpDetail("state", map()
						.get("Select  Summer state"));
			enterEduAndEmpDetail("zipCode", map().get("Enter  Summer zip code"));
		} else {
			logMessage("STEP : Summer mailing address not present \n");
		}

	}

	public void enterEducationAndEmploymentInformation() {
		date1 = new DateUtil();
		try {
			year = date1.getDate(map().get(
					"Evaluate degree type and enter past/future date"))[0];
			month = date1.getDate(map().get(
					"Evaluate degree type and enter past/future date"))[1];
		} catch (NullPointerException ex) {
			logMessage("Date not present to enter or not applicable in data sheet\n");
		}

		if (map().get("Current Student Status").equalsIgnoreCase("Y")) {
			currentStudentYes(map().get("Enter University Name"),
					"universityName", map().get("Has College or University ?"));
		} else if (map().get("Current Student Status").equalsIgnoreCase("N")) {
			
			currentStudentNo(map().get("Has College or University ?").trim());
		} else {
			logMessage("Student status is invalid in data sheet");
			Assert.fail("ASSERT FAILED : Current student status is not valid in data sheet");
		}

		if (map().get("Chemistry Major Status").equalsIgnoreCase("Y")) {

			
			isMajorChemistry(
					map().get("Select Degree(s) with past date"),
					map().get("Select Degree(s) with future date"),
					map().get("Select Chemistry Major value"),
					month,
					year,

					map().get("Has Chemistry teacher?"),
					map().get("Evaluate degree type and enter past/future date"));
		} else if (map().get("Chemistry Major Status").equalsIgnoreCase("N")) {
			isMajorChemistry(map().get("Chemistry Teacher Status"),
					map().get("Has Chemistry teacher?")
					,YamlReader.getYamlValue("OMA_SmokeChecklistData.txtPreCollegeChemistryTeacher"));

		} else {
			logMessage("Chemistry status is invalid in data sheet");
			Assert.fail("ASSERT FAILED : Chemistry major status is not valid in data sheet");
		}
		verifySummerMailingAddressFiledVisibility(map().get(
				"Has Summer mailing address?"));
		System.out.println("case id :-" + map().get("CASE ID"));
		isSummerMailingAddress(map().get("CASE ID"));
		verifyIfApplicableConditionForEmployee("name", "(if applicable)");
		verifyIfApplicableConditionForEmployee("industry", "(if applicable)");
		verifyIfApplicableConditionForEmployee("occupation", "(if applicable)");
		verifyConditionForDemographics("Demographics", YamlReader.getYamlValue("OMA_SmokeChecklistData.txtDemographicsInformation"));
		enterEmployerDetails(map().get("CASE ID"));
		verifyJobExpFieldVisibility(map().get(
				"Has Chemistry professional experience?"));
		enterJobExperience(map().get("Has Chemistry professional experience?"),
				map().get("Enter Job Experience Value"));
	}
	
	public void verifyIfApplicableConditionForEmployee(String empDetail,String expText){
		if(empDetail.equals("name")){
			Assert.assertTrue(element("condition_empDetail",empDetail).getText().trim().contains(expText),
					"ASSERT FAILED: "+expText+" condition is not present for employee "+empDetail+"\n");
			logMessage("ASSERT PASSED: "+expText+" condition is present for employee "+empDetail+"\n");
		}
		else{
			Assert.assertTrue(!(element("condition_empDetail",empDetail).getText().trim().contains(expText)),
					"ASSERT FAILED: "+expText+" condition should not be present for employee "+empDetail+"\n");
			logMessage("ASSERT PASSED: "+expText+" condition is not present for employee "+empDetail+"\n");
		}
	}

	private void verifyIndustryTypeFieldVisibility(String hasIndustryCategory) {
		verifyFieldVisibility("list_industryType", hasIndustryCategory);
	}

	public void enterEducationAndEmploymentInformation_123() {
		date1 = new DateUtil();
		try {
			year = date1.getDate(map().get(
					"Evaluate degree type and enter past/future date"))[0];
			month = date1.getDate(map().get(
					"Evaluate degree type and enter past/future date"))[1];
		} catch (NullPointerException ex) {
			logMessage("Date not present to enter or not applicable in data sheet\n");
		}

		if (map().get("Current Student Status").equalsIgnoreCase("Y")) {
			currentStudentYes(map().get("Enter University Name"),
					"universityName", map().get("Has College or University ?"));
		} else if (map().get("Current Student Status").equalsIgnoreCase("N")) {
			currentStudentNo(map().get("Has College or University ?"));
		} else {
			logMessage("Student status is invalid in data sheet");
			Assert.fail("ASSERT FAILED : Current student status is not valid in data sheet");
		}
		if (map().get("Chemistry Major Status").equalsIgnoreCase("Y")) {
			isMajorChemistry(
					map().get("Select Degree(s) with past date"),
					map().get("Select Degree(s) with future date"),
					map().get("Select Chemistry Major value"),
					month,
					year,
					map().get("Has Chemistry teacher?"),
					map().get("Evaluate degree type and enter past/future date"));
		} else if (map().get("Chemistry Major Status").equalsIgnoreCase("N")) {
			System.out.println("chemistry teacher ----------------"
					+ map().get("Chemistry Teacher Status"));
			System.out.println("-----------"
					+ map().get("Has Chemistry teacher?"));
			isMajorChemistry(map().get("Chemistry Teacher Status"),
					map().get("Has Chemistry teacher?"),
					YamlReader.getYamlValue("OMA_SmokeChecklistData.txtPreCollegeChemistryTeacher"));
		} else {
			logMessage("Chemistry status is invalid in data sheet");
			Assert.fail("ASSERT FAILED : Chemistry major status is not valid in data sheet");
		}
		verifySummerMailingAddressFiledVisibility(map().get(
				"Has Summer mailing address?"));
		isSummerMailingAddress("1");
		enterEmployerDetails("1");
		verifyJobExpFieldVisibility(map().get(
				"Has Chemistry professional experience?"));
		enterJobExperience(map().get("Has Chemistry professional experience?"),
				map().get("Enter Job Experience Value"));
	}

	private void verifyPrimaryTitleFieldVisibility(String hasPrimaryTitle) {
		verifyFieldVisibility("list_occupationTitle", hasPrimaryTitle);
	}

	private void verifyJobExpFieldVisibility(String isJobExp) {
		verifyFieldVisibility("txtAr_professionalExp", isJobExp);
	}

	private void verifySummerMailingAddressFiledVisibility(
			String isSummerMaillingAdd) {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(4);
		hardWaitForIEBrowser(10);
		verifyFieldVisibility("txt_summerMailingAdd", isSummerMaillingAdd);
	}

	public void enterJobExperience(String isJobExp, String jobExp) {
		if (isJobExp.equalsIgnoreCase("SHOW")) {
			element("txtAr_professionalExp").sendKeys(jobExp);
			logMessage("STEP : " + jobExp
					+ " is entered in txtAr_professionalExp\n");
		}
	}

	public boolean verifyDisplayedMessage(String caseId) {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		warningMessage = map().get("Error messaging?");
		String messageType = "No";
		if (!warningMessage.equalsIgnoreCase("")) {
			isElementDisplayed("lbl_warnings");
			String actualMsg = element("lbl_warnings").getText().trim();
			System.out.println("actual msz: " + actualMsg);
			System.out.println("warning Message: " + warningMessage);
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
			} catch (Exception e) {
				// catch (NoSuchElementException e) {
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
				logMessage("STEP : Error message is empty\n");
			}

			return false;
		}
	}

	public void enterEmployerName(String employerName) {
		enterEduAndEmpDetail("employerName", employerName);
	}

	public void enterJobProfessionalExp(String jobExp) {
		element("txtAr_professionalExp").sendKeys(jobExp);
		logMessage("STEP : job experience value " + jobExp + " is entered");
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
	
	public void verifyEducationSectionHeaders(String headerName){
		isElementDisplayed("header_sectionName",headerName);
		logMessage("ASSERT PASSED: "+headerName+" section is displayed on Education & Employment page\n");
	}
	
	public void verifyConditionForDemographics(String header,String expectedText){
		isElementDisplayed("txt_headerCondition",header);
		Assert.assertEquals(element("txt_headerCondition",header).getText().trim(), expectedText,
				"ASSERT FAILED: "+expectedText+" text is not present corresponding to "+header+" header\n");
		logMessage("ASSERT PASSED: "+expectedText+" text is present corresponding to "+header+" header\n");
	}

}
