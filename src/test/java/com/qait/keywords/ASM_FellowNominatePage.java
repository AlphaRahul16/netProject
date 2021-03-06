package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.text.WordUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.YamlReader;

public class ASM_FellowNominatePage extends GetPage {
	WebDriver driver;
	static String pagename = "ASM_FellowNominatePage";
	YamlInformationProvider getFellowNominated;
	Map<String, Object> mapFellowNominatedSmoke;
	Map<String, String> mapFellowNomineeDetails = new HashMap<String, String>();
	String NomineeName;
	static int NomineeCount = 0;

	int timeOut, hiddenFieldTimeOut;

	public ASM_FellowNominatePage(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

	public void loginInToApplication(String username, String password) {
		enterCredencial("username", username);
		enterCredencial("password", password);
		clickOnLoginButton();
		// deleteAllNominations();
	}

	public void loginInToApplicationByLastNameAndMemberNumber(String username, String password) {
		clickLastNameMemberNumberRadiobutton();
		enterCredencial("username", username);
		enterCredencial("password", password);
		clickOnLoginButton();
		// deleteAllNominations();
	}

	private void clickPreviewNominationButton() {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		isElementDisplayed("btn_previewNomination");
		element("btn_previewNomination").click();
	}

	private void clickLastNameMemberNumberRadiobutton() {
		isElementDisplayed("rad_lastNameMemNumber");
		click(element("rad_lastNameMemNumber"));
		logMessage("Last Name/Member Number Button clicked\n");

	}

	public void enterCredencial(String fieldName, String fieldValue) {
		isElementDisplayed("inp_" + fieldName);
		element("inp_" + fieldName).clear();
		element("inp_" + fieldName).sendKeys(fieldValue);
		logMessage("STEP : " + fieldValue + " is entered in " + fieldName + " \n");

	}

	public void deleteAllNominations() {
		isElementDisplayed("list_delete");
		int size = elements("list_delete").size();
		for (int a = 0; a < size; a++) {
			try {
				elements("list_delete").get(a).click();
			} catch (StaleElementReferenceException ex) {
				elements("list_delete").get(a).click();
			}
			isElementDisplayed("btn_deleteCofirm");
			element("btn_deleteCofirm").click();
		}
	}

	public void clickOnLoginButton() {
		isElementDisplayed("btn_login");
		click(element("btn_login"));
		logMessage("STEP : Login button is clicked in btn_login\n");
	}

	public void verifyLoginErrorMessagePresent(String errorMessage) {
		isElementDisplayed("txt_loginErrorMessage");
		verifyElementTextContains("txt_loginErrorMessage", errorMessage);
		logMessage("ASSERT PASSED : Error message " + errorMessage + " is appeared on invalid credecials\n");
	}

	public void verifyLoginSuccessfully() {
		isElementDisplayed("hdng_welcome");
		// verifyElementTextContains("hdng_welcome", "Welcome");
	}

	public void clickOnStartNomination() {
		hardWaitForIEBrowser(2);
		scrollDown(element("txt_startNominationDiv"));
		scrollDown(element("btn_startNomination"));
		isElementDisplayed("btn_startNomination");
		element("btn_startNomination").click();
		logMessage("STEP : Start nomination button is clicked in btn_startNomination\n");
	}

	public void clickOnConfirm() {
		isElementDisplayed("btn_confirm");
		click(element("btn_confirm"));
		logMessage("STEP : Confirm button is clicked in btn_confirm\n");
	}

	public void selectDesignateYourNomination(String designateName) {
		isElementDisplayed("rad_designateYourNomination", designateName);
		click(element("rad_designateYourNomination", designateName));
		logMessage("STEP : Select radio button for " + designateName + " is clicked in rad_designateYourNomination\n");
	}

	public void clickOnContinueWithNomination() {
		isElementDisplayed("btn_countinueWithNomination");
		click(element("btn_countinueWithNomination"));
		logMessage("STEP : Continue with nomination button is clicked in btn_countinueWithNomination\n");
	}

	public void clickOnConfirmSelection() {
		isElementDisplayed("btn_confirmSelection");
		click(element("btn_confirmSelection"));
		logMessage("STEP : confirm selection button is clicked in btn_confirmSelection\n");
	}

	public void selectSearchType(String searchType) {
		isElementDisplayed("rad_searchNominee", searchType);
		click(element("rad_searchNominee", searchType));
		logMessage("STEP : select search type " + searchType + " in rad_searchNominee\n");
	}

	public void enterSearchValue(String searchType, String searchValue) {
		isElementDisplayed("inp_enterName_numberToSearch", searchType);
		element("inp_enterName_numberToSearch", searchType).sendKeys(searchValue);
		logMessage("STEP : enter search value " + searchValue + " for search type " + searchType
				+ " in inp_enterName_numberToSearch\n");
	}

	public void clickOnFindNumber() {
		isElementDisplayed("btn_findMember");
		click(element("btn_findMember"));
		logMessage("STEP : click find number button in btn_findMember\n");
	}

	public void preRequisiteToidentifyNomineeByName_Number() {
		clickOnStartNomination();
		clickOnConfirm();
		selectDesignateYourNomination("Local Section");
		selectDesignateYourNomination("Philadelphia");
		clickOnContinueWithNomination();
		clickOnConfirmSelection();
	}

	public void preRequisiteForPrepareNomination(String searchType, String searchValue, String nominationName) {
		clickOnStartNomination();
		clickOnConfirm();
		selectDesignateYourNomination("Local Section");
		selectDesignateYourNomination("Philadelphia");
		clickOnContinueWithNomination();
		clickOnConfirmSelection();
		selectSearchTypeAndSearchNominee(searchType, searchValue);
		selectNominee();
		clickOnSaveAndContinueButton();
		wait.waitForPageToLoadCompletely();
		verifyCurrentNomination(nominationName);
	}

	public void preRequisiteForACSNomination(String NominationType, String searchType, String searchValue,
			String nominationName) {
		clickOnStartNomination();
		clickOnConfirm();
		selectDesignateYourNomination(NominationType);
		selectRadioButtonInsideFellowType(NominationType);
		verifyEligibilitySectionWithContinueAndCancelButtons();
		clickOnContinueWithNomination();
		clickOnConfirmSelection();
		selectSearchTypeAndSearchNominee(searchType, searchValue);
		selectNominee();
		saveNomineeInformation(NominationType);
		clickOnSaveAndContinueButton();
		wait.waitForPageToLoadCompletely();

	}

	public void verifyNominatedPersonCannotBeNominatedAgain(String NominationType, String searchType) {
		clickOnStartNomination();
		clickOnConfirm();
		selectDesignateYourNomination(NominationType);
		selectRadioButtonInsideFellowType(NominationType);
		verifyEligibilitySectionWithContinueAndCancelButtons();
		clickOnContinueWithNomination();
		clickOnConfirmSelection();
		System.out.println(mapFellowNomineeDetails.get("Nominee " + NominationType));
		selectSearchTypeAndSearchNominee(searchType,
				ReverseStringWords(mapFellowNomineeDetails.get("Nominee " + NominationType)));
		// verifyErrorMessageForReNomination();

	}

	private void verifyErrorMessageForReNomination() {
		wait.hardWait(2);
		wait.waitForElementToBeVisible(element("txt_errmsg_renomination"));
		Assert.assertTrue(
				element("txt_errmsg_renomination").getText()
						.contains("We were unable to locate a person with the information you provided"),
				"ASSERT FAILED: Expected message should contains 'We were unable to locate a person with the information you provided' but found "
						+ element("txt_errmsg_renomination").getText());
		logMessage("ASSERT PASSED : Error message for re-nomination of same persion verified as "
				+ element("txt_errmsg_renomination").getText());
	}

	private void saveNomineeInformation(String NominationType) {

		NomineeName = ReverseStringWords(element("txt_NomineeName").getText());
		System.out.println("Nominee Name " + NomineeName);
		mapFellowNomineeDetails.put("Nominee " + NominationType, NomineeName);
		logMessage("Nominee Name for " + NominationType + " is " + NomineeName);

	}

	public void preRequisiteForACSIndividualNomination(String NominationType, String searchType, String searchValue,
			String nominationName) {
		String NomineeName;
		clickOnStartNomination();
		clickOnConfirm();
		selectDesignateYourNomination(NominationType);
		clickOnConfirmSelection();
		selectSearchTypeAndSearchNominee(searchType, searchValue);
		selectFellowNominees(searchValue);
		saveNomineeInformation(NominationType);
		clickOnSaveAndContinueButton();
		wait.waitForPageToLoadCompletely();
	}

	public void selectSearchTypeAndSearchNominee(String searchType, String searchValue) {

		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		selectSearchType(searchType);
		String[] searchValue1 = searchValue.split(",");
		searchValue = searchValue1[NomineeCount];
		System.out.println("Search value " + searchValue);
		enterSearchValue(searchType, searchValue);
		if (searchType.equalsIgnoreCase("Num")) {
			clickOnFindNumber();
		} else {
			try {
				wait.resetImplicitTimeout(2);
				wait.resetExplicitTimeout(hiddenFieldTimeOut);
				isElementDisplayed("img_search");
				wait.waitForElementToDisappear(element("img_search"));
			} catch (Exception exp) {
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
			}

		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);

	}

	public void verifyErrorMessage(String errorMessage) {
		isElementDisplayed("txt_searchErrorMsg");
		verifyElementText("txt_searchErrorMsg", errorMessage);
		logMessage("ASSERT PASSED : Error message is verified and text is :" + errorMessage + " \n");
	}

	public void logoutApplication() {
		isElementDisplayed("btn_logOut");
		click(element("btn_logOut"));
		logMessage("STEP : Logout Application in btn_logOut\n");
	}

	public void selectNominee() {
		wait.hardWait(5);
		wait.waitForPageToLoadCompletely();
		// wait.waitForElementToBeVisible(element("btn_firstNominee"));
		isElementDisplayed("btn_firstNominee");
		element("btn_firstNominee").click();
		logMessage("STEP : Select first nominee in btn_firstNominee\n");
	}

	public void selectFellowNominees(String SearchValue) {
		wait.hardWait(2);
		wait.waitForPageToLoadCompletely();
		try {
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			wait.waitForElementToBeVisible(element("btn_firstNominee"));
			isElementDisplayed("btn_firstNominee");
			element("btn_firstNominee").click();
		}

		catch (NoSuchElementException e) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			NomineeCount++;
			selectSearchTypeAndSearchNominee("Name", SearchValue);
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
		logMessage("STEP : select first nominee in btn_firstNominee\n");
	}

	public void clickOnSaveAndContinueButton() {
		isElementDisplayed("btn_saveAnContinue");
//		element("btn_saveAnContinue").click();
		clickUsingXpathInJavaScriptExecutor(element("btn_saveAnContinue"));
		logMessage("STEP : Save and continue button is clicked in btn_saveAnContinue\n");
	}

	public void verifyCurrentNomination(String nominationName) {
		isElementDisplayed("fieldset_currentNomination", nominationName);
	}

	public void enterNominationFieldValue(String fieldName, String fieldValue) {
		isElementDisplayed("inp_nominationField", fieldName);
		element("inp_nominationField", fieldName).clear();
		element("inp_nominationField", fieldName).sendKeys(fieldValue);
		logMessage("STEP : " + fieldValue + " is entered in " + fieldName + " \n");
	}

	public void clickReturnToDashBoardButton() {
		isElementDisplayed("btn_ReturnToDashboard");
		element("btn_ReturnToDashboard").click();
		logMessage("STEP : Return To Dashboard is clicked\n");
	}

	public void clickReturnToDashBoardButtonOnPopUp() {
		isElementDisplayed("btn_ReturnToDashboardPopUp");
		element("btn_ReturnToDashboardPopUp").click();
		logMessage("STEP : Return To Dashboard on Pop Up is clicked\n");
	}

	public void selectNominationFieldValue(String fieldName, String fieldValue) {
		isElementDisplayed("list_selectNominationField", fieldName);
		selectProvidedTextFromDropDown(element("list_selectNominationField", fieldName), fieldValue);
		logMessage("STEP : " + fieldValue + " is selected in " + fieldName + " \n");
	}

	public void clickOnSaveButton(String nominationChecklistName) {
		isElementDisplayed("btn_nominationSave", nominationChecklistName);
		element("btn_nominationSave", nominationChecklistName).click();
		logMessage("STEP : Save button is clicked in btn_nominationSave\n");
		wait.waitForPageToLoadCompletely();
	}

	public void enterEducationDetails(String institution, String degree, String major, String gradYear) {
		enterNominationFieldValue("Institution", institution);
		selectNominationFieldValue("Degree", degree);
		enterNominationFieldValue("Major", major);
		wait.waitForPageToLoadCompletely();
		enterNominationFieldValue("GradYear", gradYear);
		wait.waitForPageToLoadCompletely();
		clickOnSaveButton("education");
	}

	public void enterAwardCitationDetails(String ProfessionCitation, String Communitycitation) {
		enterTextInProfessionCitation(ProfessionCitation);
		enterTextInCommunityCitation(Communitycitation);
		clickOnSaveButton("citation");
	}

	private void makeTextItalicAndUnderline() {
		element("txtarea_awards").sendKeys(Keys.chord(Keys.CONTROL, "a"));
		switchToDefaultContent();
		element("btn_underline").click();
		wait.hardWait(1);
		element("btn_italic").click();

	}

	private void enterTextInProfessionCitation(String ProfessionCitation) {
		switchToFrame(element("iframe1_Awards"));
		element("txtarea_awards").click();
		element("txtarea_awards").clear();
		System.out.println(ProfessionCitation);
		element("txtarea_awards").sendKeys(ProfessionCitation);
		System.out.println(element("txtarea_awards").getText());
		switchToDefaultContent();

	}

	private void enterTextInCommunityCitation(String Communitycitation) {
		switchToFrame(element("iframe2_Awards"));
		element("txtarea_awards").click();
		element("txtarea_awards").clear();
		System.out.println(Communitycitation);
		element("txtarea_awards").sendKeys(Communitycitation);
		System.out.println(element("txtarea_awards").getText());
		switchToDefaultContent();
	}

	public void verifyAddMoreButton() {
		isElementDisplayed("btn_AddMore");
	}

	public void verifyErrorMessage_Nomination(String errorMessage) {
		isElementDisplayed("txt_errorMessage");
		if (element("txt_errorMessage").getText().endsWith(errorMessage)) {
			logMessage("STEP : " + errorMessage + " is verified in txt_errorMessage\n");
		}
	}

	public void selectNominationChecklistName(String nominationChecklistName) {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("lnk_nominationChecklist", nominationChecklistName);
		click(element("lnk_nominationChecklist", nominationChecklistName));
		// performClickByActionBuilder(element("lnk_nominationChecklist",
		// nominationChecklistName));
		// element("lnk_nominationChecklist", nominationChecklistName).click();
		logMessage("STEP : " + nominationChecklistName + " is clicked in lnk_nominationChecklist\n");

	}

	public void enterNominationDetails(String fieldName, String fieldValue) {
		try {
			wait.resetExplicitTimeout(4);
			wait.resetImplicitTimeout(4);
			isElementDisplayed("inp_professionalHistory", fieldName);
			element("inp_professionalHistory", fieldName).clear();
			element("inp_professionalHistory", fieldName).sendKeys(fieldValue);
			logMessage("STEP : " + fieldValue + " is entered in " + fieldName + " in inp_professionalHistory\n");
		} catch (StaleElementReferenceException exp) {
			isElementDisplayed("inp_professionalHistory", fieldName);
			element("inp_professionalHistory", fieldName).clear();
			element("inp_professionalHistory", fieldName).sendKeys(fieldValue);
			logMessage("STEP : " + fieldValue + " is entered in " + fieldName + " in inp_professionalHistory\n");
			wait.resetExplicitTimeout(timeOut);
			wait.resetImplicitTimeout(timeOut);
		}
		wait.resetExplicitTimeout(timeOut);
		wait.resetImplicitTimeout(timeOut);

	}

	public void fillDetailsForProfessionalHistory(String employer, String title, String fromDate, String toDate,
			String description) {
		enterNominationDetails("Employer", employer);
		enterNominationDetails("Title", title);
		enterNominationDetails("FromDate", fromDate);
		enterNominationDetails("ToDate", toDate);
		clickOnAllFieldsRequiredHeading();
		switchToFrame("txtHistoryDesc_ifr");
		enterDescription(description);
		switchToDefaultContent();
		clickOnSaveButton("history");
	}

	public void enterDescription(String description) {
		isElementDisplayed("txtarea_awards");
		element("txtarea_awards").click();
		element("txtarea_awards").clear();
		element("txtarea_awards").sendKeys(description);
		logMessage("STEP : " + description + " is entered in inp_description\n");
	}

	public void fillDetailsForProfessionalOrganizationAffiliations(String professionalOrg, String title,
			String fromDate, String toDate) {
		enterNominationDetails("Name", professionalOrg);
		enterNominationDetails("Title", title);
		enterNominationDetails("FromDate", fromDate);
		enterNominationDetails("ToDate", toDate);

	}

	public void fillDetailsForVolunteerServiceToTheACSComunity(String title, String fromDate, String toDate,
			String description, String saveName) {
		enterNominationDetails("Title", title);
		enterNominationDetails("FromDate", fromDate);
		enterNominationDetails("ToDate", toDate);
		// clickOnAllFieldsRequiredHeading();
		String frame = WordUtils.capitalize(saveName);
		// switchoFrame("txt" + frame + "Desc_ifr");
		System.out.println(frame);
		switchToFrame(element("frameGeneral", frame));
		enterDescription(description);
		switchToDefaultContent();

	}

	private void enterImpactSummary(String fieldValue) {

		isElementDisplayed("txtarea_impactsummary");
		element("txtarea_impactsummary").clear();
		element("txtarea_impactsummary").sendKeys(fieldValue);
		logMessage("STEP : " + fieldValue + " is entered in Volunteer Service \n");

	}

	public void clickOnAllFieldsRequiredHeading() {
		isElementDisplayed("txt_allFieldsRequired");
		element("txt_allFieldsRequired").click();
	}

	public void fillDetailsForHonorAndAwards(String name, String sponsor, String year) {
		enterNominationDetails("Name", name);
		enterNominationDetails("Sponsor", sponsor);
		enterNominationDetails("Year", year);
		clickOnSaveButton("award");
	}

	public void searchByName_NumberForSecondaryNominatorAndVerifyMemberList(String searchType, String nominatorNumber,
			String name_number) {
		if (searchType.equalsIgnoreCase("number")) {
			selectRadioButtonForNumber(nominatorNumber);
			searchByNumber(nominatorNumber, name_number);
			clickFindMemberButton(nominatorNumber);
			verifyListOfMembersIsPresent(nominatorNumber);
		} else if (searchType.equalsIgnoreCase("name")) {
			searchName(nominatorNumber, name_number);
			waitForSearchImageToDissappear(nominatorNumber);

			verifyListOfMembersIsPresent(nominatorNumber);
		} else {
			logMessage("STEP : Search type is not mention correctly");
		}

	}

	public void searchName(String nominatorNumber, String name) {
		isElementDisplayed("inp_searchName", nominatorNumber);
		element("inp_searchName", nominatorNumber).clear();
		element("inp_searchName", nominatorNumber).sendKeys(name);
		logMessage("STEP : " + name + " is entered to search for nominator" + nominatorNumber + "\n");
	}

	public void waitForSearchImageToDissappear(String nominatorNumber) {
		try {
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("img_searchNominator", nominatorNumber);
			logMessage("Wait for search image is to be dissappear for nominator" + nominatorNumber + " \n");
			wait.waitForElementToDisappear(element("img_searchNominator", nominatorNumber));
			wait.waitForPageToLoadCompletely();
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (Exception exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}
	}

	public void verifyListOfMembersIsPresent(String nominatorNumber) {
		isElementDisplayed("list_selectMember", nominatorNumber);
		int sizeOfMembers = elements("list_selectMember", nominatorNumber).size();
		if (sizeOfMembers > 0) {
			logMessage("ASSERT PASSED : List of members is present on search by name in secondary nominator "
					+ nominatorNumber + " \n");
		} else {
			Assert.fail(
					"Member list is not present on search by name in secondary nominator " + nominatorNumber + " \n");
		}
	}

	public void selectFirstNominator(String nominatorNumber) {
		isElementDisplayed("list_selectMember", nominatorNumber);
		elements("list_selectMember", nominatorNumber).get(0).click();
		logMessage("STEP : First nominator is selected \n");
	}

	public void selectRadioButtonForNumber(String nominatorNumber) {
		isElementDisplayed("rad_searchNumber", nominatorNumber);
		element("rad_searchNumber", nominatorNumber).click();
		logMessage("STEP : Radio button for search by number is selected in rad_searchNumber\n");
	}

	public void searchByNumber(String nominatorNumber, String number) {
		isElementDisplayed("inp_searchByNumber", nominatorNumber);
		element("inp_searchByNumber", nominatorNumber).clear();
		element("inp_searchByNumber", nominatorNumber).sendKeys(number);
		logMessage("STEP : " + number + " is entered in inp_searchByNumber\n");
	}

	public void clickFindMemberButton(String nominatorNumber) {
		isElementDisplayed("btn_findMemberNominator", nominatorNumber);
		element("btn_findMemberNominator", nominatorNumber).click();
		logMessage("STEP : Find member button is clicked in btn_findMemberNominator\n");

	}

	public void fillAllRequiredDetailsToSubmit() {
		mapFellowNominatedSmoke = YamlReader.getYamlValues("ASM_fellowNominatedSmokeChecklistData");
		getFellowNominated = new YamlInformationProvider(mapFellowNominatedSmoke);
		wait.waitForPageToLoadCompletely();

		// enterEducationDetails(
		// getFellowNominated
		// .getASM_fellowNominated_EduDetails("Institution"),
		// getFellowNominated.getASM_fellowNominated_EduDetails("Degree"),
		// getFellowNominated.getASM_fellowNominated_EduDetails("Major"),
		// getFellowNominated
		// .getASM_fellowNominated_EduDetails("GraduationYear"));
		// verifyAddMoreButton();
		// selectNominationChecklistName("Professional History");
		//
		// fillDetailsForProfessionalHistory(
		// getFellowNominated
		// .getASM_fellowNominated_ProfDetails("Employer"),
		// getFellowNominated.getASM_fellowNominated_ProfDetails("Title"),
		// getFellowNominated
		// .getASM_fellowNominated_ProfDetails("FromDate"),
		// getFellowNominated.getASM_fellowNominated_ProfDetails("ToDate"),
		// getFellowNominated
		// .getASM_fellowNominated_ProfDetails("Description"));
		// verifyAddMoreButton();
		selectNominationChecklistName("Professional Organization Affiliations");
		fillDetailsForProfessionalOrganizationAffiliations(
				getFellowNominated.getASM_fellowNominated_ProfOrgDetails("ProfessionalOrg"),
				getFellowNominated.getASM_fellowNominated_ProfOrgDetails("Position_title"),
				getFellowNominated.getASM_fellowNominated_ProfOrgDetails("FromDate"),
				getFellowNominated.getASM_fellowNominated_ProfOrgDetails("ToDate"));
		verifyAddMoreButton();
		selectNominationChecklistName("Volunteer Service to the ACS Community");
		fillDetailsForVolunteerServiceToTheACSComunity(getFellowNominated.getASM_fellowNominated_ProfDetails("Title"),
				getFellowNominated.getASM_fellowNominated_ProfDetails("FromDate"),
				getFellowNominated.getASM_fellowNominated_ProfDetails("ToDate"),
				getFellowNominated.getASM_fellowNominated_ProfDetails("Description"), "service");
		verifyAddMoreButton();
		selectNominationChecklistName("Contributions to the Science / Profession");

		fillDetailsForVolunteerServiceToTheACSComunity(getFellowNominated.getASM_fellowNominated_ProfDetails("Title"),
				getFellowNominated.getASM_fellowNominated_ProfDetails("FromDate"),
				getFellowNominated.getASM_fellowNominated_ProfDetails("ToDate"),
				getFellowNominated.getASM_fellowNominated_ProfDetails("Description"), "contribution");
		verifyAddMoreButton();
		selectNominationChecklistName("Honors and Awards");
		fillDetailsForHonorAndAwards(getFellowNominated.getASM_fellowNominated_HonorAndAwardsDetails("Name"),
				getFellowNominated.getASM_fellowNominated_HonorAndAwardsDetails("Sponcor"),
				getFellowNominated.getASM_fellowNominated_HonorAndAwardsDetails("Year"));
		verifyAddMoreButton();
		selectNominationChecklistName("Select Supporters and Upload Letters");
		searchByName_NumberForSecondaryNominatorAndVerifyMemberList("name", "1",
				getFellowNominated.getASM_fellowNominated_SelectSupportAndUploadLetters("SecondaryNominator1"));
		selectFirstNominator("1");

		selectFileToUpload("Secondary1", "pdf-test.pdf", "Secondary1");
		searchByName_NumberForSecondaryNominatorAndVerifyMemberList("name", "2",
				getFellowNominated.getASM_fellowNominated_SelectSupportAndUploadLetters("SecondaryNominator2"));
		selectFirstNominator("2");
		selectFileToUpload("Secondary2", "pdf-test.pdf", "Secondary2");
		selectFileToUpload("Primary", "pdf-test.pdf", "Primary");
		selectFileToUpload("Attestation", "pdf-test.pdf", "Attestation");

		selectNominationChecklistName("Upload Resume/CV");
		selectFileToUpload("Resume", "pdf-test.pdf", "");
		selectNominationChecklistName("Upload Supplementary Information");
		selectFileToUpload("SupplementryMaterial", "pdf-test.pdf", "");
		clickOnContinueToNextButton();
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(4);
		clickOnContinueToSubmit();
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(3);
		checkForSubmitNomination();
		hardWaitForIEBrowser(2);
		clickOnSubmitNomination();
		hardWaitForIEBrowser(2);
		clickOnSubmitButton();
	}

	public void fillAllRequiredDetailsToSubmitACSFellowsNominations(String NominationType) {
		mapFellowNominatedSmoke = YamlReader.getYamlValues("ACS_fellowNominatedData");
		getFellowNominated = new YamlInformationProvider(mapFellowNominatedSmoke);
		wait.waitForPageToLoadCompletely();
		wait.waitForPageToLoadCompletely();
		ClickCodeOfConductButton(getFellowNominated.getASM_fellowNominated("CodeOfConductResponse"),
				getFellowNominated.getASM_fellowNominated("CodeConExplain"));
		clickOnSaveButton("codeofconduct");
		verifyNumberOfCitationsAs("Code of Conduct", "1");

		selectNominationChecklistName("Award Citations");
		enterAwardCitationDetails(getFellowNominated.getASM_fellowNominated("ScienceCitationMsg"),
				getFellowNominated.getASM_fellowNominated("CommunityCitationMsg"));
		verifyNumberOfCitationsAs("Award Citations", "2");

		selectNominationChecklistName("Professional Organization Affiliations");
		fillDetailsForProfessionalOrganizationAffiliations(
				getFellowNominated.getASM_fellowNominated_ProfOrgDetails("ProfessionalOrg"),
				getFellowNominated.getASM_fellowNominated_ProfOrgDetails("Position_title"),
				getFellowNominated.getASM_fellowNominated_ProfOrgDetails("FromDate"),
				getFellowNominated.getASM_fellowNominated_ProfOrgDetails("ToDate"));
		clickReturnToDashBoardButtonOnPopUp();
		//verifyPopUpDataonReturningToDashboard(getFellowNominated.getASM_fellowNominated("EditDataSavedMsg"));
		clickGotoDashboardButtonOnPopUp();
		returnToCurrentNomination("Professional Organization Affiliations");
		clickOnSaveButton("affiliation");
		verifyNumberOfCitationsAs("Professional Organization Affiliations", "1");

		selectNominationChecklistName("Volunteer Service to the ACS Community");
		fillDetailsForVolunteerServiceToTheACSComunity(getFellowNominated.getASM_fellowNominated_ProfDetails("Title"),
				getFellowNominated.getASM_fellowNominated_ProfDetails("FromDate"),
				getFellowNominated.getASM_fellowNominated_ProfDetails("ToDate"),
				getFellowNominated.getASM_fellowNominated_ProfDetails("Description"), "service");
		enterImpactSummary(getFellowNominated.getASM_fellowNominated_ProfDetails("impactSummaryMsg"));
		clickOnSaveButton("service");
		verifyNumberOfCitationsAs("Volunteer Service to the ACS Community", "1");

		selectNominationChecklistName("Contributions to the Science / Profession");
		fillDetailsForVolunteerServiceToTheACSComunity(getFellowNominated.getASM_fellowNominated_ProfDetails("Title"),
				getFellowNominated.getASM_fellowNominated_ProfDetails("FromDate"),
				getFellowNominated.getASM_fellowNominated_ProfDetails("ToDate"),
				getFellowNominated.getASM_fellowNominated_ProfDetails("Description"), "contribution");
		enterImpactSummary(getFellowNominated.getASM_fellowNominated_ProfDetails("impactSummaryMsg"));
		clickOnSaveButton("contribution");
		verifyNumberOfCitationsAs("Contributions to the Science / Profession", "1");

		selectNominationChecklistName("Honors and Awards");
		fillDetailsForHonorAndAwards(getFellowNominated.getASM_fellowNominated_HonorAndAwardsDetails("Name"),
				getFellowNominated.getASM_fellowNominated_HonorAndAwardsDetails("Sponcor"),
				getFellowNominated.getASM_fellowNominated_HonorAndAwardsDetails("Year"));
		verifyNumberOfCitationsAs("Honors and Awards", "1");

		selectNominationChecklistName("Select Supporters and Upload Letters");
		searchByName_NumberForSecondaryNominatorAndVerifyMemberList("name", "1",
				getFellowNominated.getASM_fellowNominated_SelectSupportAndUploadLetters("SecondaryNominator1"));
		selectFirstNominator("1");

		selectFileToUpload("Secondary1", "pdf-test.pdf", "Secondary1");
		searchByName_NumberForSecondaryNominatorAndVerifyMemberList("name", "2",
				getFellowNominated.getASM_fellowNominated_SelectSupportAndUploadLetters("SecondaryNominator2"));
		selectFirstNominator("2");
		selectFileToUpload("Secondary2", "pdf-test.pdf", "Secondary2");
		selectFileToUpload("Primary", "pdf-test.pdf", "Primary");
		if (NominationType.equals("Local Section") || NominationType.equals("Technical Division")) {
			selectFileToUpload("Attestation", "pdf-test.pdf", "Attestation");
			verifyNumberOfCitationsAs("Select Supporters and Upload Letters", "4");
		} else {
			verifyNumberOfCitationsAs("Select Supporters and Upload Letters", "3");
		}

		selectNominationChecklistName("Upload Resume/CV");
		selectFileToUpload("Resume", "pdf-test.pdf", "");
		verifyNumberOfCitationsAs("Upload Resume/CV", "1");

		selectNominationChecklistName("Upload Supplementary Information");
		selectFileToUpload("SupplementryMaterial", "pdf-test.pdf", "");
		verifyNumberOfCitationsAs("Upload Supplementary Information", "1");

		clickOnContinueToNextButton();
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(4);
		verifyEditFunctionalityForSavedNomination(NominationType,
				getFellowNominated.getASM_fellowNominated("CodeConExplain"));
		clickOnContinueToSubmit();
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(3);
		checkForSubmitNomination();
		hardWaitForIEBrowser(2);
		clickOnSubmitNomination();
		hardWaitForIEBrowser(2);
		clickOnSubmitButton();
	}

	private void returnToCurrentNomination(String NominationName) {

		isElementDisplayed("btn_Edit");
		element("btn_Edit").click();
		wait.hardWait(6);
		selectNominationChecklistName(NominationName);
		wait.hardWait(6);
		element("btn_Edit").click();
		logMessage("STEP : User returned to current nomination\n");

	}

	private void clickGotoDashboardButtonOnPopUp() {
		isElementDisplayed("btn_gotoDashboardOnPopUp");
		element("btn_gotoDashboardOnPopUp").click();
		logMessage("STEP : Goto Dashboard button on PopUp is clicked\n");

	}

	private void verifyPopUpDataonReturningToDashboard(String popupdata) {
		isElementDisplayed("returnPopUp");
		System.out.println(element("returnPopUp").getText());
		System.out.println(popupdata);
		Assert.assertEquals(element("returnPopUp").getText(),(popupdata));
		logMessage("STEP : Return To Dashboard data is verified as " + popupdata);

	}

	private void verifyEditFunctionalityForSavedNomination(String NominationType, String cocExplain) {
		if (NominationType.equals("Individual")) {
			clickOnHomeButton();
			clickOnEditButtonForSavedNomination("Individual Nomination");
			selectNominationChecklistName("Edit");
			EditCodeOfConductResponse(cocExplain);
			clickOnSaveButton("codeofconduct");
			selectNominationChecklistName("Submit Nomination");
		}

	}

	private void clickOnHomeButton() {
		isElementDisplayed("btn_home");
		click(element("btn_home"));
		logMessage("STEP : Home Button is clicked\n");

	}

	private void EditCodeOfConductResponse(String cocExplain) {
		System.out.println(element("btn_codeofconduct_checked").getAttribute("value"));
		if (element("btn_codeofconduct_checked").getAttribute("value").equals("1")) {
			click(element("rad_codeConductNo"));
			explainCodeOfConductIfNo(cocExplain);
		} else if (element("btn_codeofconduct_checked").getAttribute("value").equals("0")) {
			click(element("rad_codeConductYes"));
		}

	}

	private void verifyNumberOfCitationsAs(String tabName, String numberOfCitations) {
		wait.hardWait(4);
		isElementDisplayed("txt_citationNumber", tabName);
		wait.waitForPageToLoadCompletely();
		System.out.println(element("txt_citationNumber", tabName).getText());
		System.out.println(numberOfCitations);
		Assert.assertEquals(element("txt_citationNumber", tabName).getText(),(numberOfCitations));
		logMessage("ASSERT PASSED : " + tabName + " citation number is verified as " + numberOfCitations + "\n");
	}

	public void selectFileToUpload(String fieldname, String fileName, String frameName) {
		String iframe = "document.getElementById('ifBridge" + frameName + "')";
		wait.hardWait(2);
		String path = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "UploadFiles"
				+ File.separator;
		File filePath = new File(path + fileName);
		isElementDisplayed("link_upload" + fieldname + "Nominator");

		executeJavascript("document.getElementById('ifBridge" + frameName + "');" + iframe
				+ ".style.visibility='visible';" + iframe + ".style.display='block';" + iframe
				+ ".setAttribute('width','500');" + iframe + ".setAttribute('height','200');" + iframe
				+ ".contentWindow.document.getElementById('fuName').style.visibility='visible';" + iframe
				+ ".contentWindow.document.getElementById('fuName').style.display='block';" + iframe
				+ ".contentWindow.document.getElementById('fuName').setAttribute('height','500');" + iframe
				+ ".contentWindow.document.getElementById('fuName').setAttribute('width','200')");
		switchToFrame("ifBridge" + frameName + "");
		isElementDisplayed("inp_upload");
		element("inp_upload").sendKeys(filePath.getAbsolutePath());
		switchToDefaultContent();
		hardWaitForIEBrowser(2);
		logMessage("STEP : " + fileName + " is uploaded in " + fieldname + " \n");
		verifyFileUploaded(fileName, fieldname);
	}

	public void uploadFileUsingJavascipt(String filename, String frameName, String section) {
		String iframe = "document.getElementById('" + frameName + "')";
		String path = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "UploadFiles"
				+ File.separator;
		File filePath = new File(path + filename);
		if (checkIfElementIsThere("btn_deleteFile"))
			logMessage("STEP : File already uploaded");
		else {
			isElementDisplayed("btn_chooseFile");

			executeJavascript("document.getElementById('" + frameName + "');" + iframe + ".style.visibility='visible';"
					+ iframe + ".style.display='block';" + iframe + ".setAttribute('height','500');" + iframe
					+ ".setAttribute('width','500');" + iframe
					+ ".contentWindow.document.getElementById('fuName').style.visibility='visible';" + iframe
					+ ".contentWindow.document.getElementById('fuName').style.display='block';" + iframe
					+ ".contentWindow.document.getElementById('fuName').setAttribute('height','500');" + iframe
					+ ".contentWindow.document.getElementById('fuName').setAttribute('width','500')");
			System.out.println("javascript loaded");
			switchToFrame(driver.findElement(By.cssSelector("#" + frameName)));
			isElementDisplayed("inp_upload");
			element("inp_upload").sendKeys(filePath.getAbsolutePath());
			switchToDefaultContent();
			wait.hardWait(4);
			logMessage("STEP : " + filename + " file is uploaded\n");
			isElementDisplayed("btn_deleteFile");
			logMessage("ASSERT PASSED : File is uploaded for " + section);
		}
	}

	public static void uploadFile(String fileLocation) {
		try {
			// Setting clipboard with file location
			setClipboardData(fileLocation);
			// native key strokes for CTRL, V and ENTER keys
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.delay(2000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	public static void setClipboardData(String string) {
		// StringSelection is a class that can be used for copy and paste
		// operations.
		StringSelection stringSelection = new StringSelection(string);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	}

	public void removeUploadedFile(String nominationFieldName) {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("link_delete", nominationFieldName);
			element("link_delete", nominationFieldName).click();
			clickOnConfirm();
			logMessage("STEP : Click on delete link in link_delete\n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			handleAlert();
		} catch (NoSuchElementException exp) {
			logMessage("STEP : File is not uploaded so remove button is not present \n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}
	}

	public void verifyFileUploaded(String fileName, String nominatorName) {
		try {
			wait.resetExplicitTimeout(3);
			wait.resetImplicitTimeout(3);
			wait.waitForPageToLoadCompletely();
			isElementDisplayed("link_delete" + nominatorName + "Nominator");
			logMessage("ASSERT PASSED : File " + fileName + " is uploaded for " + nominatorName + "\n");
		} catch (StaleElementReferenceException exp) {
			isElementDisplayed("link_delete" + nominatorName + "Nominator");
			logMessage("ASSERT PASSED : File " + fileName + " is uploaded for " + nominatorName + "\n");
			wait.resetExplicitTimeout(timeOut);
			wait.resetImplicitTimeout(timeOut);
		}
		wait.resetExplicitTimeout(timeOut);
		wait.resetImplicitTimeout(timeOut);
	}

	public void verifyCurrentTab(String tabName) {
		isElementDisplayed("tab_current");
		verifyElementText("tab_current", tabName);
	}

	public void clickOnContinueToNextButton() {
		isElementDisplayed("link_nextPage");
		element("link_nextPage").click();
		logMessage("STEP : Continue to next page is clicked in link_nextPage\n");
	}

	public void verifySessionExpiresInThirtySeconds() {
		wait.hardWait(30);
		wait.resetImplicitTimeout(0);
		wait.resetExplicitTimeout(0);
		isElementDisplayed("btn_keepSession");
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
		logMessage("ASSERT PASSED : Session expired in 30 seconds is verified \n");
	}

	public void clickOnContinueToSubmit() {
		isElementDisplayed("btn_submitContinue");
		element("btn_submitContinue").click();
		logMessage("STEP : Contnue button is clicked for submit \n");
	}

	public void checkForSubmitNomination() {
		isElementDisplayed("chk_submitNomination");
		if (!element("chk_submitNomination").isSelected()) {
			element("chk_submitNomination").click();
			logMessage("STEP : Checkbox is checked for submit nomination in chk_submitNomination\n");
		} else {
			logMessage("STEP : Checkbox is already checked for submit nomination in chk_submitNomination\n");
		}
	}

	public void clickOnSubmitNomination() {
		isElementDisplayed("btn_submitNomination");
		element("btn_submitNomination").click();
		logMessage("STEP : Click on submit nomination in btn_submitNomination\n");
	}

	public void clickOnSubmitButton() {
		isElementDisplayed("btn_saveSubmit");
		element("btn_saveSubmit").click();
		logMessage("STEP : Save button is clicked inbtn_saveSubmit\n");
	}

	public void verifyNominationCompleted() {
		isElementDisplayed("txt_successNomination");
		verifyElementText("txt_successNomination", "Nomination Complete");
	}

	public void verifyUserIsOnFellowsDashboard() {
		verifyTabOnDashboard("Contact Information");
		verifyTabOnDashboard("Program Information");
		verifyTabOnDashboard("My Fellows Nominations");

	}

	private void verifyTabOnDashboard(String tabname) {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		isElementDisplayed("txt_dashboardHeadings", tabname);
		Assert.assertTrue(element("txt_dashboardHeadings", tabname).getText().contains(tabname),
				"ASSERT FAILED: Expected is "+tabname+" but found "+element("txt_dashboardHeadings", tabname).getText());
		logMessage("ASSERT PASSED : " + tabname + " is verified on dashboard\n");

	}

	public void selectRadioButtonInsideFellowType(String fellowtype) {
		isElementDisplayed("rad_localsectionArea", fellowtype);
		element("rad_localsectionArea", fellowtype).click();
		logMessage("STEP : Radio Button inside" + fellowtype + " is clicked\n");

	}

	private void collapseDetailsMenu(String menuName) {
		// isElementDisplayed("icon_up", menuName);
		wait.waitForPageToLoadCompletely();
		wait.hardWait(1);
		clickUsingXpathInJavaScriptExecutor(element("icon_up", menuName));
		// element("icon_up", menuName).click();
		waitForSpinner();
		logMessage("STEP : " + menuName + " bar collapse bar clicked\n");

	}

	public void expandDetailsMenu(String menuName) {
		// isElementDisplayed("btn_detailsMenuAACT", menuName);
		wait.waitForPageToLoadCompletely();
		wait.hardWait(1);
		clickUsingXpathInJavaScriptExecutor(element("btn_detailsMenuAACT", menuName));
		// element("btn_detailsMenuAACT", menuName).click();
		logMessage("STEP : " + menuName + " bar is clicked to expand" + "\n");
		waitForSpinner();

	}

	public void waitForSpinner() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			// handleAlert();
			wait.hardWait(3);
			wait.resetImplicitTimeout(5);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			// isElementDisplayed("img_spinner");
			logMessage("STEP : Wait for spinner to be disappeared \n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (Exception Exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);

		} catch (AssertionError Exp) {

			logMessage("STEP : Spinner is not present \n");
		}
	}

	public void verifyEligibilitySectionWithContinueAndCancelButtons() {
		wait.hardWait(3);
		verifyTabOnDashboard("Eligibility");
		isElementDisplayed("btn_countinueWithNomination");
		isElementDisplayed("btn_CancelEligibity");

	}

	private void ClickCodeOfConductButton(String choice, String conductExplanation) {
		System.out.println(getFellowNominated.getASM_fellowNominated("CodeOfConductResponse"));
		choice = choice.replace("\"", "").trim();
		if (choice.equalsIgnoreCase("Yes")) {
			System.out.println("Choice is " + choice);
			isElementDisplayed("rad_codeConductYes");
			click(element("rad_codeConductYes"));
		} else if (choice.equalsIgnoreCase("No")) {
			System.out.println("Choice is " + choice);
			isElementDisplayed("rad_codeConductNo");
			click(element("rad_codeConductNo"));
			explainCodeOfConductIfNo(conductExplanation);
		}
		logMessage("STEP : Code of conduct details filled with choice as : " + choice);

	}

	private void explainCodeOfConductIfNo(String conductExplanation) {
		EnterTextInField(element("txtarea_CodeOfConductExpl"), conductExplanation);
		logMessage("Step : Code of Conduct Explanation is entered as " + conductExplanation);

	}

	private void verifyDetailsForCodeOfConduct(String Value, String cocexplanation, String FellowType) {
		isElementDisplayed("txt_codeOfConductValue");
		System.out.println(element("txt_codeOfConductValue").getText());
		Value = Value.replace("\"", "");
		System.out.println(Value);
		if (FellowType.equals("Individual")) {
			if (Value.equals("No")) {
				verifyCodeOfConductDetailsOnIweb("Yes", "");
			} else if (Value.equals("Yes")) {
				verifyCodeOfConductDetailsOnIweb("No", cocexplanation);
			}
		} else {
			verifyCodeOfConductDetailsOnIweb(Value, cocexplanation);
		}

	}

	private void verifyCodeOfConductDetailsOnIweb(String expectedValue, String cocexplanation) {
		isElementDisplayed("txt_codeOfConductValue");
		Assert.assertEquals(element("txt_codeOfConductValue").getText().trim(), expectedValue);
		logMessage("ASSERT PASSED : Code Of Conduct value verified as " + expectedValue);
		Assert.assertEquals(element("txt_cocExplanation").getText().trim(), cocexplanation);
		logMessage("ASSERT PASSED : Code Of Conduct Explanation on iweb verified as " + cocexplanation);
	}

	private void verifyScienceAndCommunityCitationMessages(String ScienceMsg, String CommunityMsg) {
		Assert.assertEquals(elements("txt_codeOfConductValue").get(0).getText().trim(),(ScienceMsg.trim()));
		logMessage("ASSERT PASSED : Science Citation Message is verified as " + ScienceMsg);
		Assert.assertEquals(elements("txt_codeOfConductValue").get(1).getText().trim(),(CommunityMsg.trim()));
		logMessage("ASSERT PASSED : Community Citation Message is verified as " + CommunityMsg);
	}

	private void verifyServiceToAcsDetailsForFellowNomination(String MenuName) {

		int index = 4;
		String[] AcsServicesArray = { getFellowNominated.getASM_fellowNominated_ProfDetails("Title"),
				getFellowNominated.getASM_fellowNominated_ProfDetails("Description"),
				getFellowNominated.getASM_fellowNominated_ProfDetails("FromDate"),
				getFellowNominated.getASM_fellowNominated_ProfDetails("ToDate"),
				getFellowNominated.getASM_fellowNominated_ProfDetails("ToDate"),
				getFellowNominated.getASM_fellowNominated_ProfDetails("impactSummaryMsg") };

		String[] ProfessionalOrg = { getFellowNominated.getASM_fellowNominated_ProfOrgDetails("ProfessionalOrg"),
				getFellowNominated.getASM_fellowNominated_ProfOrgDetails("Position_title"),
				getFellowNominated.getASM_fellowNominated_ProfOrgDetails("FromDate"),
				getFellowNominated.getASM_fellowNominated_ProfOrgDetails("ToDate"),
				getFellowNominated.getASM_fellowNominated_ProfOrgDetails("ToDate"), };

		String[] honors = { getFellowNominated.getASM_fellowNominated_HonorAndAwardsDetails("Name"),
				getFellowNominated.getASM_fellowNominated_HonorAndAwardsDetails("Sponcor"),
				getFellowNominated.getASM_fellowNominated_HonorAndAwardsDetails("Year") };

		if (MenuName.equals("professional organization affiliation")) {

			for (int i = 0; i < ProfessionalOrg.length; i++) {
				Assert.assertEquals(element("txt_fellowIwebDetails", toString().valueOf(index)).getText().trim()
						,(ProfessionalOrg[i].trim()));
				logMessage("ASSERT PASSED : Details in " + MenuName + " verified as " + ProfessionalOrg[i].trim());
				index++;
			}
		} else if (MenuName.equals("honors and awards")) {
			for (int i = 0; i < honors.length; i++) {
				Assert.assertEquals(element("txt_fellowIwebDetails", toString().valueOf(index)).getText().trim()
						,(honors[i].trim()));
				logMessage("ASSERT PASSED : Details in " + MenuName + " verified as " + honors[i].trim());
				index++;
			}
		} else {
			for (int i = 0; i < AcsServicesArray.length; i++) {
				Assert.assertEquals(element("txt_fellowIwebDetails", toString().valueOf(index)).getText().trim()
						,(AcsServicesArray[i].trim()));
				logMessage("ASSERT PASSED : Details in " + MenuName + " verified as " + AcsServicesArray[i].trim());
				index++;
			}
		}

	}

	public void verifyDetailsOnIwebForFellowNomination(String FellowType) {
		mapFellowNominatedSmoke = YamlReader.getYamlValues("ACS_fellowNominatedData");
		getFellowNominated = new YamlInformationProvider(mapFellowNominatedSmoke);
		expandDetailsMenu("code of conduct");
		verifyDetailsForCodeOfConduct(getFellowNominated.getASM_fellowNominated("CodeOfConductResponse"),
				getFellowNominated.getASM_fellowNominated("CodeConExplain"), FellowType);
		collapseDetailsMenu("code of conduct");

		expandDetailsMenu("award citations");
		verifyScienceAndCommunityCitationMessages(getFellowNominated.getASM_fellowNominated("ScienceCitationMsg"),
				getFellowNominated.getASM_fellowNominated("CommunityCitationMsg"));
		collapseDetailsMenu("award citations");

		expandDetailsMenu("service to the acs");
		verifyServiceToAcsDetailsForFellowNomination("service to the acs");
		collapseDetailsMenu("service to the acs");

		expandDetailsMenu("contribution to the science/profession");
		verifyServiceToAcsDetailsForFellowNomination("contribution to the science/profession");
		collapseDetailsMenu("contribution to the science/profession");

		expandDetailsMenu("honors and awards");
		verifyServiceToAcsDetailsForFellowNomination("honors and awards");
		collapseDetailsMenu("honors and awards");

		expandDetailsMenu("professional organization affiliation");
		verifyServiceToAcsDetailsForFellowNomination("professional organization affiliation");
		collapseDetailsMenu("professional organization affiliation");

		expandDetailsMenu("nominee letters");
		collapseDetailsMenu("nominee letters");

	}

	public String selectNomineeForParticularFellowType(String fellowType) {

		System.out.println("Map FelloeType " + mapFellowNomineeDetails.get("Nominee " + fellowType));
		return mapFellowNomineeDetails.get("Nominee " + fellowType);
	}

	public void verifyUserIsAbleToViewSubmittedNominations() {

		clickOnViewButtonForSavedNomination("Individual Nomination");
		clickPreviewNominationButton();
		verifyUserIsAbleToSeeAllViewButtons();
		clickOnHomeButton();
	}

	private void clickOnEditButtonForSavedNomination(String FellowType) {
		isElementDisplayed("lnk_editNominations", FellowType);
		click(element("lnk_editNominations", FellowType));
		logMessage("STEP : Edit Button for Saved Nomination " + FellowType + " is clicked\n");
	}

	private void clickOnViewButtonForSavedNomination(String FellowType) {
		isElementDisplayed("lnk_viewNominations", FellowType);
		click(element("lnk_viewNominations", FellowType));
		logMessage("STEP : View Button for Saved Nomination " + FellowType + " is clicked\n");
	}

	private void verifyUserIsAbleToSeeAllViewButtons() {
		Assert.assertTrue(elements("btn_view").size() == 9,"ASSERT FAILED: Expected size of the button is '9' nut found "+elements("btn_view").size());
		logMessage("STEP : All view Button are enabled\n");
	}

	public void clickPrintPDFButton(String Fellowtype) {

		String downloadFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + "DownloadedFiles";
		isElementDisplayed("lnk_printPDF", Fellowtype);
		click(element("lnk_printPDF", Fellowtype));
		logMessage("STEP : Print PDF button is clicked for " + Fellowtype + "\n");
		Assert.assertTrue(isFileDownloaded(downloadFilePath, "FellowsNomination"),
				"Failed to download Expected document");
		logMessage("ASSERT PASSED : Pdf file downloaded for " + Fellowtype + " submitted nomination\n");
	}

}
