package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.io.File;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class ASM_NominatePage extends GetPage {
	WebDriver driver;
	static String pagename = "ASM_NominatePage";
	int timeOut, hiddenFieldTimeOut;
	boolean flag;

	public ASM_NominatePage(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

	public void loginInToApplication(String lastName, String memberNumber) {
		enterUserName(lastName);
		enterPassword(memberNumber);
		clickOnVerifyButton();
		wait.waitForPageToLoadCompletely();
	}

	public void loginInToApplication_ACSID(String userName, String password) {
		hardWaitForIEBrowser(5);
		selectACSIDRadioButton();
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		enterUserName(userName);
		enterPassword(password);
		clickOnVerifyButton();
		wait.waitForPageToLoadCompletely();
	}

	public void enterUserName(String userName) {
		isElementDisplayed("inp_username");
		element("inp_username").clear();
		element("inp_username").sendKeys(userName);
		logMessage("Step : " + userName + " is entered in inp_username\n");
	}

	public void enterPassword(String password) {
		isElementDisplayed("inp_password");
		element("inp_password").clear();
		element("inp_password").sendKeys(password);
		logMessage("Step : " + password + " is entered in inp_username\n");
	}

	public void clickOnVerifyButton() {
		isElementDisplayed("btn_verify");
		element("btn_verify").click();
		logMessage("Step : Verify button is clicked in btn_verify\n");
		wait.hardWait(3);
	}

	public void verifyLoginErrorMessage(String errorMessage) {
		isElementDisplayed("txt_loginErrorMessage");
		String actualErrorMessage = element("txt_loginErrorMessage").getText();
		System.out.println("actual error message :" + actualErrorMessage);
		if (!actualErrorMessage.startsWith(errorMessage)) {
			Assert.fail("Error message not verified");
		} else {
			logMessage("ASSERT PASSED : Error message is verified in txt_loginErrorMessage\n");
		}
	}

	public void selectACSIDRadioButton() {
		isElementDisplayed("rad_acsId");
		element("rad_acsId").click();
		logMessage("Step : ACS ID radio button is selected in rad_acsId\n");
	}

	public void navigateToVerifyEligibilityTab(String awardName,
			String nomineeName, String nomineePosition, String discipline) {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(3);
		// clickOnConfirmButton();
		waitForSpinner();
		hardWaitForIEBrowser(3);
		// removeIncompletedSubmision();
		clickOnArrowButton();
		clickOnAwardInList();
		clickOnCreateNewNominationButton();
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(3);
		selectIKnowTheirNameRadioButton();
		enterLastName(nomineeName);
		waitForSpinner();
		clickOnSelectNomineeButton();
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(3);
		verifyCurrentTab("Verify Eligibility");
		selectNoForYearOfExperience();
		selectSafeLabPracticesUnknownRadioButton();
		selectNoForDiscussedAwardNominationRadioButton();
		enterNomineePosition(nomineePosition);
		selectDiscipline(discipline);
	}

	public void navigateToPrepareNominationTab(String awardName,
			String nomineeName, String nomineePosition, String discipline,
			String suggestedCitation, String recommendation,
			String pubPatentsFilename, String biographicalSketchFileName,
			String supportForm1FileName, String support1LastName) {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(3);
		// clickOnConfirmButton();
		waitForSpinner();
		// removeIncompletedSubmision();

		clickOnArrowButton();
		clickOnAwardInList();
		clickOnCreateNewNominationButton();
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(3);
		selectIKnowTheirNameRadioButton();
		enterLastName(nomineeName);
		waitForSpinner();
		clickOnSelectNomineeButton();
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(3);
		// verifyCurrentTab("Verify Eligibility");
		selectSafeLabPracticesUnknownRadioButton();
		selectNoForDiscussedAwardNominationRadioButton();
		enterNomineePosition(nomineePosition);
		selectDiscipline(discipline);
		patentQuestion();
		selectNoForYearOfExperience();
		clickOnContinueButton();
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(3);
		clickOnConfirm_preparedNominationButton();
		// verifyCurrentTab("Prepare Nomination");
		enterSuggstedCitation(suggestedCitation);
		enterRecommendation(recommendation);
		selectFileToUpload("pubsAndPatents", pubPatentsFilename, "1");
		waitForSpinnerOnUpload();
		selectFileToUpload("biography", biographicalSketchFileName, "2");
		waitForSpinnerOnUpload();
		clickOnIKnowTheirName_Support("1");
		enterLastName_support("1", support1LastName);
		waitForSpinnerOnSearch();

		clickOnSelectNomineeButton();
		selectFileToUpload("supporter1", supportForm1FileName, "3");
		waitForSpinnerOnUpload();

	}

	public void uploadFile(String fieldName, String Filename, String index) {
		verifyCurrentTab("Prepare Nomination");
		selectFileToUpload(fieldName, Filename, index);
	}
	
	

	public void verifyErrorMessageOnInvalidFileUpload(String errorMessage) {
		
		Assert.assertTrue(getAlertText().contains(errorMessage));
		logMessage("ASSERT PASSED : Error message " + errorMessage
				+ " is verified on uploaded invalid file\n");

	}

	public void uploadFileInRecommendation(String fileName) {
		selectUploadRecommendation();
		selectFileToUpload("recommendation", fileName, "0");
		waitForSpinnerOnUpload();
	}
	
	public void uploadInValidFileInRecommendation(String fileName) {
		selectUploadRecommendation();
		selectFileToUpload("recommendation", fileName, "0");
//		waitForSpinnerOnUpload();
	}

	public void removeIncompletedSubmision() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(10);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("link_removeIncompleteSubmission");
			element("link_removeIncompleteSubmission").click();
			handleAlert();
			waitForSpinnerOnUpload();
			isElementDisplayed("txt_nominationRemoved");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("Step : Incomplete submission removed \n");
		} catch (NoSuchElementException exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("Step : Incomplete submission is not removed \n");
		}
	}

	public void removeUploadedFile(String nominationFieldName) {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("link_remove", nominationFieldName);
			element("link_remove", nominationFieldName).click();
			logMessage("Step : click on remove link in link_remove\n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			handleAlert();
			waitForSpinnerOnUpload();
		} catch (NoSuchElementException exp) {
			logMessage("Step : file is not uploaded so remove button is not present \n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}
	}

	public void selectUploadRecommendation() {
		isElementDisplayed("rad_uploadRecommendation");
		element("rad_uploadRecommendation").click();
		logMessage("Step : select upload button for recommendation in rad_uploadRecommendation\n");
	}

	public void uploadFileForSupportForm(String support2LastName,
			String supportForm2FileName, String supportFormNumber) {
		clickOnIKnowTheirName_Support(supportFormNumber);
		enterLastName_support(supportFormNumber, support2LastName);
		waitForSpinnerOnSearch();
		wait.hardWait(5);
		if (supportFormNumber.equalsIgnoreCase("2")) {
			clickOnSelectNomineeButtonForSupport2();
			selectFileToUpload("supporter" + supportFormNumber,
					supportForm2FileName, "4");
		} else if (supportFormNumber.equalsIgnoreCase("1")) {
			clickOnSelectNomineeButton();
			selectFileToUpload("supporter" + supportFormNumber,
					supportForm2FileName, "3");
		}

	}

	public void clickOnConfirmButton() {
		isElementDisplayed("btn_confirm");
		element("btn_confirm").click();
		logMessage("Step : confirm button is clicked in btn_confirm\n");
	}

	public void waitForSpinner() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("img_spinner");
			logMessage("wait for spinner to disappear\n");
			wait.waitForElementToDisappear(element("img_spinner"));
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (NoSuchElementException exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("Spinner is not present \n");
		}
	}

	public void waitForSpinnerOnSearch() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		try {
			wait.hardWait(2);
			wait.resetImplicitTimeout(5);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("img_spinnerOnSearch");
			logMessage("wait for spinner to disappear\n");
			wait.waitForElementToDisappear(element("img_spinnerOnSearch"));
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (NoSuchElementException exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("Spinner is not present \n");
		}
	}

	public void waitForSpinnerOnUpload() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		try {
			wait.hardWait(1);
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("span_spinnerOnUpload");
			logMessage("wait for spinner to disappear\n");
			wait.waitForElementToDisappear(element("span_spinnerOnUpload"));
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (NoSuchElementException exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("Spinner is not present \n");
		}
	}

	public void clickOnArrowButton() {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("btn_arrow");
		// clickAndHold(element("btn_arrow"));
		clickUsingXpathInJavaScriptExecutor(element("btn_arrow"));
		// element("btn_arrow").click();
		logMessage("Step : Arrow button is clicked in btn_arrow\n");
	}

	public void clickOnAwardInList() {
		isElementDisplayed("li_awards");
		for (WebElement element : elements("li_awards")) {
			if (!(element.getText().endsWith("(team)") || element.getText()
					.equalsIgnoreCase("ACS Award for Creative Invention:2017"))) {
				String awardName = element.getText();
				element.click();
				logMessage("Step : " + awardName + " is clicked in li_awards\n");
				flag = true;
				break;
			} else {
				flag = false;
			}
		}
		if (!flag)
			Assert.fail("Step : award name is not present in list of awards\n");
	}

	public void clickOnCreateNewNominationButton() {
		isElementDisplayed("inp_createNewNomination");
		element("inp_createNewNomination").click();
		logMessage("Step : Create new nomination button is clicked in inp_createNewNomination\n");
	}

	public void selectIKnowTheirNameRadioButton() {
		isElementDisplayed("rad_knowTheirName");
		element("rad_knowTheirName").click();
		logMessage("Step : I Know Their Name radio button is clicked in rad_knowTheirName\n");
	}

	public void enterLastName(String lastname) {
		isElementDisplayed("inp_findByName");
		element("inp_findByName").sendKeys(lastname);
		logMessage("Step : " + lastname + " is entered in inp_findByName\n");
	}

	public void clickOnSelectNomineeButton() {
		isElementDisplayed("btn_selectNomineeSupport1");
		element("btn_selectNomineeSupport1").click();
		logMessage("Step : Select Nominee button is clicked in btn_selectNomineeSupport1\n");
	}

	public void clickOnSelectNomineeButtonForSupport2() {
		isElementDisplayed("btn_selectNomineeSupport2");
		element("btn_selectNomineeSupport2").click();
		logMessage("Step : Select Nominee button is clicked in btn_selectNomineeSupport2\n");
	}

	public void verifyCurrentTab(String tabName) {
		// wait.waitForPageToLoadCompletely();
		// wait.hardWait(2);
		// isElementDisplayed("tab_currentTab");
		// verifyElementTextContains("tab_currentTab", tabName);
	}

	public void selectSafeLabPracticesUnknownRadioButton() {
		isElementDisplayed("rad_safeLabPractices");
		element("rad_safeLabPractices").click();
		logMessage("Step : safe laboratory practices radio button is selected for unknown in rad_safeLabPractices\n");
	}

	public void selectNoForDiscussedAwardNominationRadioButton() {
		isElementDisplayed("rad_discussedAwardNomination");
		element("rad_discussedAwardNomination").click();
		logMessage("Step : have discussed award nomination radio button is selected for No in rad_discussedAwardNomination\n");
	}

	public void selectDiscipline(String discipline) {
		isElementDisplayed("list_selectDiscipline");
		selectProvidedTextFromDropDown(element("list_selectDiscipline"),
				discipline);
		logMessage("Step : " + discipline
				+ " is selected in list_selectDiscipline\n");
	}

	public void enterNomineePosition(String positionName) {
		isElementDisplayed("inp_nomineePosition");
		element("inp_nomineePosition").clear();
		element("inp_nomineePosition").sendKeys(positionName);
		logMessage("Step : " + positionName
				+ " is entered in inp_nomineePosition\n");
	}

	public void clickOnContinueButton() {
		isElementDisplayed("btn_continue");
		element("btn_continue").click();
		logMessage("Step : continue button is clicked in btn_continue\n");
	}

	public void clickOnConfirm_preparedNominationButton() {
		isElementDisplayed("btn_confirm_PrepareNomination");
		element("btn_confirm_PrepareNomination").click();
		logMessage("Step : confirm button is clicked in btn_confirm_PrepareNomination\n");
	}

	public void enterSuggstedCitation(String suggestedCitation) {
		isElementDisplayed("txtAr_suggestedCitation");
		element("txtAr_suggestedCitation").sendKeys(suggestedCitation);
		logMessage("Step : " + suggestedCitation
				+ " is entered in txtAr_suggestedCitation\n");
	}

	public void enterRecommendation(String recommendation) {
		isElementDisplayed("txtAr_recommendation");
		element("txtAr_recommendation").sendKeys(recommendation);
		logMessage("Step : " + recommendation
				+ " is entered in txtAr_recommendation\n");
	}

	public void selectFileToUpload(String fieldname, String fileName,
			String inputNumber) {
		wait.hardWait(5);
		removeUploadedFile(fieldname);
		String path = "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "UploadFiles" + File.separator;
		File filePath = new File(path + fileName);
		isElementDisplayed("inp_" + fieldname + "Uploads");
		WebElement ele = (WebElement) executeJavascript1("return document.getElementsByClassName('qq-upload-button')["
				+ inputNumber + "].getElementsByTagName('input')[0]");
		ele.sendKeys(filePath.getAbsolutePath());
hardWaitForIEBrowser(2);
		logMessage("Step : " + fileName + " is uploaded in " + fieldname
				+ " \n");
	}

	public void clickOnIKnowTheirName_Support(String formNumber) {
		isElementDisplayed("inp_support" + formNumber + "IKnowThierName");
		element("inp_support" + formNumber + "IKnowThierName").click();
		logMessage("Step : I Konw Their Name radio button is clicked for supportform"
				+ formNumber
				+ " in inp_support"
				+ formNumber
				+ "IKnowThierName");
	}

	public void enterLastName_support(String formNumber, String lastName) {
		isElementDisplayed("inp_support" + formNumber + "FindByName");
		element("inp_support" + formNumber + "FindByName").sendKeys(lastName);
		logMessage("Step : " + lastName + " is entered in support form "
				+ formNumber + " in inp_support" + formNumber + "FindByName \n");
	}

	public void clickOnSubmitButton() {
		isElementDisplayed("btn_submit");
		element("btn_submit").click();
		logMessage("Step : click on submit button in btn_submit\n");
		waitForSpinnerOnUpload();
		verifyNominationSubmitted();
	}

	public void verifyNominationSubmitted() {
		isElementDisplayed("txt_nominationSubmitted");
		logMessage("Step : verified nomination is submitted\n");
	}

	public void clickonGoBackButton() {
		isElementDisplayed("btn_cancel");
		element("btn_cancel").click();
		logMessage("Step : click on go back button in btn_cancel\n");
	}

	public void verifyDashBoardIsPresent() {
		verifyPageTitleExact("Nominator Dashboard");
	}

	public void patentQuestion() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("txt_patentQuestion");
			selectDateContent("day", "02");
			selectDateContent("month", "May");
			selectDateContent("year", "2016");
			enterPatentInfo("Name", "Patent Name XYZ");
			enterPatentInfo("Number", "123");
			enterPatentInfo("Description", "Decription xyz");

			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (NoSuchElementException exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}
	}

	public void selectDateContent(String day_month_year, String value) {
		isElementDisplayed("list_patentDate_" + day_month_year);
		selectProvidedTextFromDropDown(element("list_patentDate_"
				+ day_month_year), value);
		logMessage("Step : " + day_month_year + " is selected in "
				+ day_month_year + " \n");
	}

	public void enterPatentInfo(String infoType, String infoValue) {
		isElementDisplayed("inp_patent" + infoType);
		element("inp_patent" + infoType).sendKeys(infoValue);
		logMessage("Step : ");
	}

	public void selectNoForYearOfExperience() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("rad_yearOfExperience");
			element("rad_yearOfExperience").click();
			logMessage("Step : No is selected for year of experience in rad_yearOfExperience\n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (NoSuchElementException exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}

	}

}
