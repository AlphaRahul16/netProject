package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.getpageobjects.GetPage;

public class ASM_NominatePage extends GetPage {
	WebDriver driver;
	static String pagename = "ASM_NominatePage";
	int timeOut, hiddenFieldTimeOut;
	boolean flag;
	static int NomineeVisitedAwards = 0;
	Map<String, String> mapNomineeNames = new HashMap<String, String>();

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
		logMessage("STEP : " + userName + " is entered in inp_username\n");
	}

	public void enterPassword(String password) {
		isElementDisplayed("inp_password");
		element("inp_password").clear();
		element("inp_password").sendKeys(password);
		logMessage("STEP : " + password + " is entered in inp_password\n");
	}

	public void clickOnVerifyButton() {
		isElementDisplayed("btn_verify");
		element("btn_verify").click();
		logMessage("STEP : Verify button is clicked in btn_verify\n");
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
		logMessage("STEP : ACS ID radio button is selected in rad_acsId\n");
	}

	public void navigateToVerifyEligibilityTab(String awardName, String nomineeName, String nomineePosition,
			String discipline) {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(3);
		clickOnConfirmButton();
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

	public void navigateToPrepareNominationTab(String awardName, String nomineeName, String nomineePosition,
			String discipline, String suggestedCitation, String recommendation, String pubPatentsFilename,
			String biographicalSketchFileName, String supportForm1FileName, String support1LastName) {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(3);
		clickOnConfirmButton();
		waitForSpinner();
		removeIncompletedSubmision();

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
		try {
			wait.resetImplicitTimeout(2);

			uploadFileForPatent("test.pdf");
			wait.resetImplicitTimeout(timeOut);
		} catch (NoSuchElementException e) {
			wait.resetImplicitTimeout(timeOut);
		}
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

		Assert.assertTrue(getAlertText().contains(errorMessage),
				"ASSERT FAILED: Error msg should contains '" + errorMessage + "' but found " + getAlertText());
		logMessage("ASSERT PASSED : Error message " + errorMessage + " is verified on uploaded invalid file\n");

	}

	public void uploadFileInRecommendation(String fileName) {
		selectUploadRecommendation();
		selectFileToUpload_Awards("recommendation", fileName, "0");
		waitForSpinnerOnUpload();
	}

	public void uploadFileForPatent(String fileName) {
		selectFileToUpload("recommendation", fileName, "0");
		waitForSpinnerOnUpload();
	}

	public void uploadInValidFileInRecommendation(String fileName) {
		selectUploadRecommendation();
		selectFileToUpload("recommendation", fileName, "0");
		// waitForSpinnerOnUpload();
	}

	public void removeIncompletedSubmision() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(10);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("link_removeIncompleteSubmission");
			for (WebElement ele : elements("link_removeIncompleteSubmission")) {
				ele.click();
				handleAlert();
				ele.click();
				handleAlert();
				waitForSpinnerOnUpload();
			}
			waitForSpinnerOnUpload();
			// isElementDisplayed("txt_nominationRemoved");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("STEP : Incomplete submission removed \n");
		} catch (NoSuchElementException exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("STEP : Incomplete submission is not removed \n");
		}
	}

	public void removeUploadedFile(String nominationFieldName) {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("link_remove", nominationFieldName);
			element("link_remove", nominationFieldName).click();
			logMessage("STEP : Click on remove link in link_remove\n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			handleAlert();
			waitForSpinnerOnUpload();
		} catch (NoSuchElementException exp) {
			logMessage("STEP : File is not uploaded so remove button is not present \n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}
	}

	public void selectUploadRecommendation() {
		wait.hardWait(3);
		isElementDisplayed("rad_uploadRecommendation");
		element("rad_uploadRecommendation").click();
		logMessage("STEP : Select upload button for recommendation in rad_uploadRecommendation\n");
	}

	public void uploadFileForSupportForm(String support2LastName, String supportForm2FileName,
			String supportFormNumber) {
		clickOnIKnowTheirName_Support(supportFormNumber);
		enterLastName_support(supportFormNumber, support2LastName);
		waitForSpinnerOnSearch();
		wait.hardWait(5);
		if (supportFormNumber.equalsIgnoreCase("2")) {
			clickOnSelectNomineeButtonForSupport2();
			selectFileToUpload("supporter" + supportFormNumber, supportForm2FileName, "4");
		} else if (supportFormNumber.equalsIgnoreCase("1")) {
			clickOnSelectNomineeButton();
			selectFileToUpload("supporter" + supportFormNumber, supportForm2FileName, "3");
		}

	}

	public void clickOnConfirmButton() {
		try {
			wait.resetImplicitTimeout(2);
			isElementDisplayed("btn_confirm");
			element("btn_confirm").click();
			wait.resetExplicitTimeout(timeOut);
			logMessage("STEP : Confirm button is clicked in btn_confirm\n");
		} catch (NoSuchElementException e) {
			wait.resetExplicitTimeout(timeOut);
		}
	}

	public void waitForSpinner() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(5);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("img_spinner");
			logMessage("Wait for spinner to disappear\n");
			wait.waitForElementToDisappear(element("img_spinner"));
		} catch (NoSuchElementException exp) {
			logMessage("Spinner is not present \n");
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
	}

	public void waitForSpinnerOnSearch() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			wait.hardWait(2);
			wait.resetImplicitTimeout(3);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("img_spinnerOnSearch");
			logMessage("Wait for spinner to disappear\n");
			wait.waitForElementToDisappear(element("img_spinnerOnSearch"));
		} catch (NoSuchElementException exp) {

			logMessage("Spinner is not present \n");
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
	}

	public void waitForSpinnerOnUpload() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			wait.hardWait(1);
			wait.resetImplicitTimeout(3);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("span_spinnerOnUpload");
			logMessage("Wait for spinner to disappear\n");
			wait.waitForElementToDisappear(element("span_spinnerOnUpload"));
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (Exception exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("Spinner is not present \n");
		}
	}

	public void waitForSpinnerOnSubmitNomination() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			wait.hardWait(1);
			wait.resetImplicitTimeout(3);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("img_submitnomination");
			logMessage("Wait for spinner to disappear\n");
			wait.waitForElementToDisappear(element("img_submitnomination"));
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (Exception exp) {
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
			if (!(element.getText().endsWith("(team)")
					|| element.getText().equalsIgnoreCase("ACS Award for Creative Invention:2017"))) {
				String awardName = element.getText();
				element.click();
				logMessage("STEP : " + awardName + " is clicked in li_awards\n");
				flag = true;
				break;
			} else {
				flag = false;
			}
		}
		if (!flag)
			Assert.fail("STEP : award name is not present in list of awards\n");
	}

	public void clickOnCreateNewNominationButton() {
		wait.hardWait(6);
		dynamicWait(20,"inp_createNewNomination", " ");
		isElementDisplayed("inp_createNewNomination");
		wait.hardWait(1);
		element("inp_createNewNomination").click();
		logMessage("STEP : Create new nomination button is clicked in inp_createNewNomination\n");
	}

	public void selectIKnowTheirNameRadioButton() {
		isElementDisplayed("rad_knowTheirName");
		element("rad_knowTheirName").click();
		logMessage("STEP : I Know Their Name radio button is clicked in rad_knowTheirName\n");
	}

	public void enterLastName(String lastname) {
		isElementDisplayed("inp_findByName");
		element("inp_findByName").sendKeys(lastname);
		logMessage("STEP : " + lastname + " is entered in inp_findByName\n");
	}

	public void enterMemberNumber(String membernumber) {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(3);
		isElementDisplayed("inp_findbymembernumber");
		element("inp_findbymembernumber").sendKeys(membernumber);
		logMessage("STEP : " + membernumber + " is entered in inp_findbymembernumber\n");
	}

	public void clickFindMemberBynumberButton() {

		wait.hardWait(1);
		isElementDisplayed("btn_findMemberByNumber");
		element("btn_findMemberByNumber").click();
		logMessage("STEP : Find Member button is clicked in btn_findMemberByNumber\n");

	}

	public void enterSupporterMemberNumber(String formnumber, String membernumber) {
		isElementDisplayed("inp_support" + formnumber + "FindByNum");
		element("inp_support" + formnumber + "FindByNum").sendKeys(membernumber);
		logMessage("STEP : " + membernumber + " is entered in inp_findbymembernumber\n");
	}

	public void ClickSupporterFindMemberButton(String formnumber) {
		isElementDisplayed("btn_findSupporter" + formnumber + "ByNumber");
		element("btn_findSupporter" + formnumber + "ByNumber").click();
		logMessage("STEP : Find Member for supporter " + formnumber + " is clicked inp_findbymembernumber\n");
	}

	public void clickOnSelectNomineeButton() {
		try {
			wait.hardWait(1);
			wait.resetImplicitTimeout(3);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);

			elements("btn_selectNomineeSupport1").get(0).click();

		} catch (Exception e) {
			System.out.println("Exception");
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
		logMessage("STEP : Select Nominee button is clicked in btn_selectNomineeSupport1\n");
	}

	public void clickOnSelectNomineeButtonForAwardsNomination() {

		wait.hardWait(1);
		elements("btn_selectNomineeSupport1").get(0).click();
		wait.hardWait(1);
		logMessage("STEP : Select Nominee button is clicked in btn_selectNomineeSupport1\n");

	}

	public void clickOnSelectNomineeButtonForSupport2() {

		isElementDisplayed("btn_selectNomineeSupport2");
		element("btn_selectNomineeSupport2").click();
		logMessage("STEP : Select Nominee button is clicked in btn_selectNomineeSupport2\n");
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
		logMessage("STEP : Safe laboratory practices radio button is selected for unknown in rad_safeLabPractices\n");
	}

	public void selectSafeLabPracticesRadioButton(String value) {
		isElementDisplayed("rad_dynsafelabpractice", value);
		element("rad_dynsafelabpractice", value).click();
		logMessage("STEP : Safe laboratory practices radio button " + value
				+ " is selected for unknown in rad_dynsafelabpractice\n");
	}

	public void selectNoForDiscussedAwardNominationRadioButton() {
		isElementDisplayed("rad_discussedAwardNomination");
		element("rad_discussedAwardNomination").click();
		logMessage("STEP : Have discussed award nomination radio button is selected as No \n");
	}

	public void selectValueForDiscussedAwardNominationRadioButton(String value) {
		System.out.println("value" + value);
		isElementDisplayed("rad_disscusNomination", value);
		element("rad_disscusNomination", value).click();
		logMessage("STEP : Have discussed award nomination radio button as " + value + "\n");
	}

	public void selectDiscipline(String discipline) {
		isElementDisplayed("list_selectDiscipline");
		selectProvidedTextFromDropDown(element("list_selectDiscipline"), discipline);
		logMessage("STEP : " + discipline + " is selected in list_selectDiscipline\n");
	}

	public void enterNomineePosition(String positionName) {
		isElementDisplayed("inp_nomineePosition");
		element("inp_nomineePosition").clear();
		element("inp_nomineePosition").sendKeys(positionName);
		logMessage("STEP : " + positionName + " is entered in inp_nomineePosition\n");
	}

	public void clickOnContinueButton() {
		wait.hardWait(3);
		isElementDisplayed("btn_continue");
		clickUsingXpathInJavaScriptExecutor(element("btn_continue"));
//		element("btn_continue").click();
		logMessage("STEP : continue button is clicked in btn_continue\n");
	}

	public void clickOnConfirm_preparedNominationButton() {
		isElementDisplayed("btn_confirm_PrepareNomination");
		element("btn_confirm_PrepareNomination").click();
		logMessage("STEP : confirm button is clicked in btn_confirm_PrepareNomination\n");
	}

	public void enterSuggstedCitation(String suggestedCitation) {
		isElementDisplayed("txtAr_suggestedCitation");
		element("txtAr_suggestedCitation").sendKeys(suggestedCitation);
		logMessage("STEP : " + suggestedCitation + " is entered in txtAr_suggestedCitation\n");
	}

	public void enterRecommendation(String recommendation) {
		isElementDisplayed("txtAr_recommendation");
		element("txtAr_recommendation").sendKeys(recommendation);
		logMessage("STEP : " + recommendation + " is entered in txtAr_recommendation\n");
	}

	public void selectFileToUpload(String fieldname, String fileName, String inputNumber) {
		wait.hardWait(5);
		removeUploadedFile(fieldname);
		String path = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "UploadFiles"
				+ File.separator;
		File filePath = new File(path + fileName);
		isElementDisplayed("inp_" + fieldname + "Uploads");
		WebElement ele = (WebElement) executeJavascript1("return document.getElementsByClassName('qq-upload-button')["
				+ inputNumber + "].getElementsByTagName('input')[0]");
		ele.sendKeys(filePath.getAbsolutePath());
		hardWaitForIEBrowser(2);
		logMessage("STEP : " + fileName + " is uploaded in " + fieldname + " \n");
	}

	public void selectFileToUpload_Awards(String fieldname, String fileName, String inputNumber) {
		wait.hardWait(1);
		String path = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "UploadFiles"
				+ File.separator;
		File filePath = new File(path + fileName);
		wait.hardWait(2);
		isElementDisplayed("btn_upload_item", fieldname);
		WebElement ele = (WebElement) executeJavascript1("return document.getElementsByClassName('qq-upload-button')["
				+ inputNumber + "].getElementsByTagName('input')[0]");
		ele.sendKeys(filePath.getAbsolutePath());
		hardWaitForIEBrowser(2);
		logMessage("STEP : " + fileName + " is uploaded in " + fieldname + " \n");
	}

	public void clickOnIKnowTheirName_Support(String formNumber) {
		isElementDisplayed("inp_support" + formNumber + "IKnowThierName");
		element("inp_support" + formNumber + "IKnowThierName").click();
		logMessage("STEP : I Konw Their Name radio button is clicked for supportform " + formNumber + " in inp_support "
				+ formNumber + " IKnowThierName");
	}

	public void enterLastName_support(String formNumber, String lastName) {
		isElementDisplayed("inp_support" + formNumber + "FindByName");
		wait.hardWait(1);
		// element("inp_support" + formNumber +
		// "FindByName").sendKeys(lastName);
		element("inp_support" + formNumber + "FindByName").sendKeys(Keys.chord(Keys.CONTROL, "a"), lastName);
		logMessage("STEP : " + lastName + " is entered in support form " + formNumber + " in inp_support " + formNumber
				+ " FindByName \n");
	}

	public void clickOnSubmitButton() {
		isElementDisplayed("btn_submit");
		element("btn_submit").click();
		logMessage("STEP : click on submit button in btn_submit\n");
		waitForSpinnerOnSubmitNomination();
		wait.waitForPageToLoadCompletely();
		verifyNominationSubmitted();
		wait.waitForPageToLoadCompletely();
	}

	public void verifyNominationSubmitted() {
		wait.waitForElementToBeVisible(element("txt_nominationSubmitted"));
		isElementDisplayed("txt_nominationSubmitted");
		logMessage("STEP : verifiy nomination is submitted\n");
	}

	public void clickonGoBackButton() {
		isElementDisplayed("btn_cancel");
		element("btn_cancel").click();
		logMessage("STEP : click on go back button in btn_cancel\n");
	}

	public void verifyDashBoardIsPresent() {
		verifyPageTitleExact("Nominator Dashboard");
	}

	public void patentQuestion() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
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
		selectProvidedTextFromDropDown(element("list_patentDate_" + day_month_year), value);
		logMessage("STEP : " + day_month_year + " is selected in " + day_month_year + " \n");
	}

	public void enterPatentInfo(String infoType, String infoValue) {
		isElementDisplayed("inp_patent" + infoType);
		element("inp_patent" + infoType).sendKeys(infoValue);
		logMessage("STEP : ");
	}

	public void selectNoForYearOfExperience() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("rad_yearOfExperience");
			element("rad_yearOfExperience").click();
			logMessage("STEP : No is selected for year of experience in rad_yearOfExperience\n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (NoSuchElementException exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}

	}

	public void clickOnConfirmNominatorAdressDetailsIfAppears_AwardNomination() {

		try {
			wait.resetImplicitTimeout(8);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("btn_nominatorAdressConfirm");
			element("btn_nominatorAdressConfirm").click();
			wait.waitForElementToDisappear(element("btn_nominatorAdressConfirm"));
		} catch (NoSuchElementException e) {
			logMessage("STEP : Confirm Address button does not appeared\n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
		logMessage("STEP : Nominator address details confirmed" + "\n");
	}

	public void selectAwardFromAwardListAndVerifyNominationMessage(String currentAwardName) {
		verifyAwardIsPresentInAwardListAndSelectAward(currentAwardName);
		verifyTooltipMessageAppearsAfterSelectingAward();
	}

	private void verifyTooltipMessageAppearsAfterSelectingAward() {
		wait.hardWait(3);
		isElementDisplayed("txt_NominationTooltip");
		verifyElementTextContains("txt_NominationTooltip", "Nominations must be submitted before");

	}

	private void verifyAwardIsPresentInAwardListAndSelectAward(String currentAwardName) {

		clickOnArrowButton();
		for (WebElement iterable_element : elements("list_drpdnAwards")) {
			if (iterable_element.getText().trim().equals(currentAwardName)) {
				Assert.assertEquals(iterable_element.getText().trim(), currentAwardName);
				logMessage("ASSERT PASSED : " + currentAwardName + " is displayed in awards dropdown\n");
				iterable_element.click();
			}
		}
		logMessage("STEP : " + currentAwardName + " is selected from the list\n");

	}

	public void SearchNomineeByMemeberNameOrNumber(Map<String, String> mapAwardsNomination,
			Map<String, String> createMemberCredentials) {
		if (mapAwardsNomination.get("SearchNomieeBy_memberNumber?").equalsIgnoreCase("Yes")) {
			enterMemberNumber(createMemberCredentials.get("Nominee1Number"));
			clickFindMemberBynumberButton();
		} else {
			wait.waitForPageToLoadCompletely();
			wait.hardWait(2);
			selectIKnowTheirNameRadioButton();
			enterLastName(createMemberCredentials.get("Nominee1Name").split(" ")[0]);

		}
		waitForSpinner();
		clickOnSelectNomineeButtonForAwardsNomination();
	}

	private String saveNomineeInformation() {
		wait.hardWait(5);
		String NomineeName = ReverseStringWords(element("txt_Nomineename_123").getText());
		System.out.println("Nominee Name " + NomineeName);
		logMessage("STEP : Nominator Name is " + NomineeName);
		return NomineeName;
	}

	public void FillEligibilityQuestionsDetails_AwardsNomination(Map<String, String> mapAwardsNomination) {
		verifyCurrentTab("Verify Eligibility");
		// selectNoForYearOfExperience(); // Button deleted in New Build,
		// verified date 11/08/2016
		selectSafeLabPracticesRadioButton(mapAwardsNomination.get("SafeLabPractices?"));
		explainCodeOfConductIfNo(mapAwardsNomination.get("SafeLabPractices?"),
				mapAwardsNomination.get("CodeOfConduct_Explaination?"));
		selectValueForDiscussedAwardNominationRadioButton(mapAwardsNomination.get("DisscussAwardNomination?"));
		enterNomineePosition(mapAwardsNomination.get("EligibilityQuestions_NomineePosition"));
		selectDiscipline(mapAwardsNomination.get("EligibilityQuestions_professionalDiscipline"));
	}

	private void explainCodeOfConductIfNo(String codeStatus, String conductExplanation) {

		if (codeStatus.equalsIgnoreCase("No")) {
			EnterTextInField(element("txtarea_codeOfConductExplained"), conductExplanation);
			logMessage("Step : Code of Conduct Explanation is entered as " + conductExplanation);

		}
	}

	public void selectSupporterts(String formNumber, Map<String, String> createMemberCredentials) {
		System.out.println("Nominee" + (Integer.parseInt(formNumber) + 1) + "Name");
		enterLastName_support(formNumber,
				createMemberCredentials.get("Nominee" + (Integer.parseInt(formNumber) + 1) + "Name").split(" ")[0]);
		waitForSpinnerOnSearch();
		clickOnSelectNomineeButtonForAwardsNomination();
	}

	public void FillDetailsOnVerifyEligibilityPage(Map<String, String> mapAwardsNomination,
			Map<String, String> createMemberCredentials) {
		enterSuggstedCitation(mapAwardsNomination.get("SuggestCitation_Text"));
		EnterTextOrUploadFileInRecomendation_AwardsNomination(mapAwardsNomination.get("UploadFileFor_Recommendation?"),
				mapAwardsNomination.get("Recommendation_Text"), mapAwardsNomination.get("FileNameForReccomendation"));

		selectFileToUpload_Awards("pubsAndPatents", mapAwardsNomination.get("FileNameForPubPatents"), "1");
		waitForSpinnerOnUpload();
		verifyOpenAndRemoveButtonIsDisplayedOnUploadingFile("pubsAndPatents");

		selectFileToUpload_Awards("biography", mapAwardsNomination.get("FileNameForBioSketch"), "2");
		waitForSpinnerOnUpload();
		verifyOpenAndRemoveButtonIsDisplayedOnUploadingFile("biography");
		SelectSupporterByMemberNumberOrName("1", mapAwardsNomination, createMemberCredentials);
		selectFileToUpload_Awards("supporter1", mapAwardsNomination.get("FileNameForSupportForm1"), "3");
		waitForSpinnerOnUpload();
		verifyOpenAndRemoveButtonIsDisplayedOnUploadingFile("supporter1");

		SelectSupporterByMemberNumberOrName("2", mapAwardsNomination, createMemberCredentials);
		selectFileToUpload_Awards("supporter2", mapAwardsNomination.get("FileNameForSupportForm2"), "4");
		waitForSpinnerOnUpload();
		verifyOpenAndRemoveButtonIsDisplayedOnUploadingFile("supporter2");

		// clickOnContinueButton();
	}

	public void SelectSupporterByMemberNumberOrName(String formNumber, Map<String, String> mapAwardsNomination,
			Map<String, String> createMemberCredentials) {
		if (formNumber.equals("1")) {
			if (mapAwardsNomination.get("SearchSupporter1By_memberNumber").equalsIgnoreCase("Yes")) {
				enterSupporterMemberNumber(formNumber, createMemberCredentials.get("Nominee2Number"));
				ClickSupporterFindMemberButton(formNumber);
				clickOnSelectNomineeButtonForAwardsNomination();
			} else {
				clickOnIKnowTheirName_Support(formNumber);
				selectSupporterts(formNumber, createMemberCredentials);
			}
		} else if (formNumber.equals("2")) {
			if (mapAwardsNomination.get("SearchSupporter2By_memberNumber").equalsIgnoreCase("Yes")) {
				enterSupporterMemberNumber(formNumber, createMemberCredentials.get("Nominee3Number"));
				ClickSupporterFindMemberButton(formNumber);
				clickOnSelectNomineeButtonForAwardsNomination();
			} else {
				clickOnIKnowTheirName_Support(formNumber);
				selectSupporterts(formNumber, createMemberCredentials);
			}
		}
	}

	public void EnterTextOrUploadFileInRecomendation_AwardsNomination(String isUploadFile, String RecommendationText,
			String fileNameReccomendation) {
		if (isUploadFile.equalsIgnoreCase("Yes")) {
			uploadFileInRecommendation(fileNameReccomendation);
			verifyOpenAndRemoveButtonIsDisplayedOnUploadingFile("recommendation");
		} else {
			enterRecommendation(RecommendationText);
		}
	}

	private void verifyOpenAndRemoveButtonIsDisplayedOnUploadingFile(String Typeid) {

		isElementDisplayed("link_remove", Typeid);
		logMessage("Step : Remove button is displayed for type " + Typeid);
		isElementDisplayed("link_open", Typeid);
		logMessage("STEP : Open button is displayed for type " + Typeid);
	}

	public void clickSaveForLaterButtonToNavigateToHomePage() {
		isElementDisplayed("btn_saveForLater");
		element("btn_saveForLater").click();
		logMessage("STEP : save for later button is clicked, naviagtes to home page\n");
	}

	public void clickEditButtonOnHomePage() {
		isElementDisplayed("btn_editNominee");
		element("btn_editNominee").click();
		logMessage("STEP : edit Nominee button is clicked\n");
	}

	public void verifyAwardStatusOnHomePageAwardNomination(String awardName, String expectedStatus) {
		isElementDisplayed("txt_awardStatus", awardName);
		Assert.assertEquals(element("txt_awardStatus", awardName).getText().trim(), expectedStatus,
				"ASSERT FAILED: Expected status is " + expectedStatus + " but found "
						+ element("txt_awardStatus", awardName).getText().trim());
		logMessage("ASSERT PASSED : Award Nomination status on Home page verified as " + expectedStatus + "\n");
	}

	public void NavigateToConfirmNominationPageAndVerifyNominationDetails(Map<String, String> mapAwardsNomination) {
		clickEditButtonOnHomePage();
		clickOnContinueButton();
		wait.hardWait(2);
		clickOnContinueButton();
		verifyNominationDetailsOnConfirmNominationPage(mapAwardsNomination);

	}

	private void verifyNominationDetailsOnConfirmNominationPage(Map<String, String> mapAwardsNomination) {

		Assert.assertEquals(elements("chkbox_labPractice").get(0).getText().trim(),
				(mapAwardsNomination.get("SafeLabPractices?")));
		logMessage("ASSERT PASSED : Safe Laboratory Practices on confirm Nomination Page is verified as "
				+ mapAwardsNomination.get("SafeLabPractices?"));

		Assert.assertEquals(elements("chkbox_labPractice").get(1).getText().trim(),
				(mapAwardsNomination.get("DisscussAwardNomination?")));
		logMessage(
				"ASSERT PASSED : Have you discussed this award nomination with the nominee on confirm Nomination Page is verified as "
						+ mapAwardsNomination.get("DisscussAwardNomination?"));

		Assert.assertEquals(element("txt_positionTitle").getText().trim(),
				(mapAwardsNomination.get("EligibilityQuestions_NomineePosition")));
		logMessage("ASSERT PASSED : Nominee's present position on confirm Nomination Page is verified as "
				+ mapAwardsNomination.get("EligibilityQuestions_NomineePosition"));

		Assert.assertEquals(element("txt_industry").getText().trim(),
				(mapAwardsNomination.get("EligibilityQuestions_professionalDiscipline")));
		logMessage("ASSERT PASSED : Nominee's professional discipline on confirm Nomination Page is verified as "
				+ mapAwardsNomination.get("EligibilityQuestions_professionalDiscipline"));

		Assert.assertTrue(
				element("txt_confirmSuggestedCitation").getText().trim()
						.contains(mapAwardsNomination.get("SuggestCitation_Text")),
				"ASSERT FAILED: Expected value is '" + mapAwardsNomination.get("SuggestCitation_Text") + " but found "
						+ element("txt_confirmSuggestedCitation").getText().trim());
		logMessage("ASSERT PASSED : Suggested citation on confirm Nomination Page is verified as "
				+ mapAwardsNomination.get("SuggestCitation_Text"));
	}

	public void navigateToHomePageFromEligibilityQuestionPage() {
		clickEditButtonOnHomePage();
		clickOnContinueButton();
		wait.waitForPageToLoadCompletely();
		clickOnConfirm_preparedNominationButton();

	}

	public void loginInToAwardsNominateApplication(Map<String, String> mapAwardsNomination,
			Map<String, String> createMemberCredentials) {
		loginInToApplication(createMemberCredentials.get("Nominee0Name").split(" ")[0],
				createMemberCredentials.get("Nominee0Number"));

	}

	public void verifyPdfContent() {
		ASCSocietyGenericPage.extractAndCompareTextFromPdfFile("Test Biographical Sketch", "Test Biography", 1);
		ASCSocietyGenericPage.extractAndCompareTextFromPdfFile("Test Publications & Patents", "Test Publications", 1);
		ASCSocietyGenericPage.extractAndCompareTextFromPdfFile("Test Recommendation", "Test Recommendation", 1);
		ASCSocietyGenericPage.extractAndCompareTextFromPdfFile("Test Support Form 1", "Support Form 1", 1);
		ASCSocietyGenericPage.extractAndCompareTextFromPdfFile("Test Support Form 2", "Support Form 2", 1);

	}

}
