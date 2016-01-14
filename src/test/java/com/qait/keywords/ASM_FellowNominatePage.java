package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Map;

import org.apache.commons.lang3.text.WordUtils;
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

	public void enterCredencial(String fieldName, String fieldValue) {
		isElementDisplayed("inp_" + fieldName);
		element("inp_" + fieldName).clear();
		element("inp_" + fieldName).sendKeys(fieldValue);
		logMessage("Step : " + fieldValue + " is entered in " + fieldName
				+ " \n");

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
		logMessage("Step : login button is clicked in btn_login\n");
	}

	public void verifyLoginErrorMessagePresent(String errorMessage) {
		isElementDisplayed("txt_loginErrorMessage");
		verifyElementTextContains("txt_loginErrorMessage", errorMessage);
		logMessage("ASSERT PASSED : Error message " + errorMessage
				+ " is appeared on invalid credecials\n");
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
		logMessage("Step : start nomination button is clicked in btn_startNomination\n");
	}

	public void clickOnConfirm() {
		isElementDisplayed("btn_confirm");
		click(element("btn_confirm"));
		logMessage("Step : confirm button is clicked in btn_confirm\n");
	}

	public void selectDesignateYourNomination(String designateName) {
		isElementDisplayed("rad_designateYourNomination", designateName);
		click(element("rad_designateYourNomination", designateName));
		logMessage("Step : select radio button for " + designateName
				+ " is clicked in rad_designateYourNomination\n");
	}

	public void clickOnContinueWithNomination() {
		isElementDisplayed("btn_countinueWithNomination");
		click(element("btn_countinueWithNomination"));
		logMessage("Step : continue with nomination button is clicked in btn_countinueWithNomination\n");
	}

	public void clickOnConfirmSelection() {
		isElementDisplayed("btn_confirmSelection");
		click(element("btn_confirmSelection"));
		logMessage("Step : confirm selection button is clicked in btn_confirmSelection\n");
	}

	public void selectSearchType(String searchType) {
		isElementDisplayed("rad_searchNominee", searchType);
		click(element("rad_searchNominee", searchType));
		logMessage("Step : select search type " + searchType
				+ " in rad_searchNominee\n");
	}

	public void enterSearchValue(String searchType, String searchValue) {
		isElementDisplayed("inp_enterName_numberToSearch", searchType);
		element("inp_enterName_numberToSearch", searchType).sendKeys(
				searchValue);
		logMessage("Step : enter search value " + searchValue
				+ " for search type " + searchType
				+ " in inp_enterName_numberToSearch\n");
	}

	public void clickOnFindNumber() {
		isElementDisplayed("btn_findMember");
		click(element("btn_findMember"));
		logMessage("Step : click find number button in btn_findMember\n");
	}

	public void preRequisiteToidentifyNomineeByName_Number() {
		clickOnStartNomination();
		clickOnConfirm();
		selectDesignateYourNomination("Local Section");
		selectDesignateYourNomination("Philadelphia");
		clickOnContinueWithNomination();
		clickOnConfirmSelection();
	}

	public void preRequisiteForPrepareNomination(String searchType,
			String searchValue, String nominationName) {
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

	public void selectSearchTypeAndSearchNominee(String searchType,
			String searchValue) {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		selectSearchType(searchType);
		enterSearchValue(searchType, searchValue);
		if (searchType.equalsIgnoreCase("Num")) {
			clickOnFindNumber();
		} else {
			try {
				wait.resetImplicitTimeout(0);
				wait.resetExplicitTimeout(hiddenFieldTimeOut);
				isElementDisplayed("img_search");
				wait.waitForElementToDisappear(element("img_search"));
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
			} catch (Exception exp) {
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
			}
		}
	}

	public void verifyErrorMessage(String errorMessage) {
		isElementDisplayed("txt_searchErrorMsg");
		verifyElementText("txt_searchErrorMsg", errorMessage);
		logMessage("ASSERT PASSED : error message is verified and text is :"
				+ errorMessage + " \n");
	}

	public void logoutApplication() {
		isElementDisplayed("btn_logOut");
		click(element("btn_logOut"));
		logMessage("Step : LogOut Application in btn_logOut\n");
	}

	public void selectNominee() {
		isElementDisplayed("btn_firstNominee");
		element("btn_firstNominee").click();
		logMessage("Step : select first nominee in btn_firstNominee\n");
	}

	public void clickOnSaveAndContinueButton() {
		isElementDisplayed("btn_saveAnContinue");
		element("btn_saveAnContinue").click();
		logMessage("Step : Save and continue button is clicked in btn_saveAnContinue\n");
	}

	public void verifyCurrentNomination(String nominationName) {
		isElementDisplayed("fieldset_currentNomination", nominationName);
	}

	public void enterNominationFieldValue(String fieldName, String fieldValue) {
		isElementDisplayed("inp_nominationField", fieldName);
		element("inp_nominationField", fieldName).clear();
		element("inp_nominationField", fieldName).sendKeys(fieldValue);
		logMessage("Step : " + fieldValue + " is entered in " + fieldName
				+ " \n");
	}

	public void selectNominationFieldValue(String fieldName, String fieldValue) {
		isElementDisplayed("list_selectNominationField", fieldName);
		selectProvidedTextFromDropDown(
				element("list_selectNominationField", fieldName), fieldValue);
		logMessage("Step : " + fieldValue + " is selected in " + fieldName
				+ " \n");
	}

	public void clickOnSaveButton(String nominationChecklistName) {
		isElementDisplayed("btn_nominationSave", nominationChecklistName);
		element("btn_nominationSave", nominationChecklistName).click();
		logMessage("Step : Save button is clicked in btn_nominationSave\n");
		wait.waitForPageToLoadCompletely();
	}

	public void enterEducationDetails(String institution, String degree,
			String major, String gradYear) {
		enterNominationFieldValue("Institution", institution);
		selectNominationFieldValue("Degree", degree);
		enterNominationFieldValue("Major", major);
		wait.waitForPageToLoadCompletely();
		enterNominationFieldValue("GradYear", gradYear);
		wait.waitForPageToLoadCompletely();
		clickOnSaveButton("education");
	}
	
	public void enterAwardCitationDetails(String ProfessionCitation,String Communitycitation)
	{
		 enterTextInProfessionCitation(ProfessionCitation);
		 makeTextItalicAndUnderline();
		 enterTextInCommunityCitation(Communitycitation);
		 makeTextItalicAndUnderline();
		clickOnSaveButton("citation");
	}
	private void makeTextItalicAndUnderline() {
	   element("txtarea_awards").sendKeys(Keys.chord(Keys.CONTROL, "a"));
	   switchToDefaultContent();
	   element("btn_underline").click();
	   wait.hardWait(1);
	   element("btn_italic").click();
	   
	}

	private void enterTextInProfessionCitation(String ProfessionCitation)
	{
		switchToFrame(element("iframe1_Awards"));
		element("txtarea_awards").click();
		element("txtarea_awards").clear();
		System.out.println(ProfessionCitation);
		element("txtarea_awards").sendKeys(ProfessionCitation);
	System.out.println(	element("txtarea_awards").getText());
	
		
	}
	private void enterTextInCommunityCitation(String Communitycitation)
	{
		switchToFrame(element("iframe2_Awards"));
		element("txtarea_awards").click();
		element("txtarea_awards").clear();
		System.out.println(Communitycitation);
		element("txtarea_awards").sendKeys(Communitycitation);
		System.out.println(element("txtarea_awards").getText());
		
	}

	public void verifyAddMoreButton() {
		isElementDisplayed("btn_AddMore");
	}

	public void verifyErrorMessage_Nomination(String errorMessage) {
		isElementDisplayed("txt_errorMessage");
		if (element("txt_errorMessage").getText().endsWith(errorMessage)) {
			logMessage("Step : " + errorMessage
					+ " is verified in txt_errorMessage\n");
		}
	}

	public void selectNominationChecklistName(String nominationChecklistName) {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("lnk_nominationChecklist", nominationChecklistName);
		click(element("lnk_nominationChecklist", nominationChecklistName));
		// performClickByActionBuilder(element("lnk_nominationChecklist",
		// nominationChecklistName));
		// element("lnk_nominationChecklist", nominationChecklistName).click();
		logMessage("Step : " + nominationChecklistName
				+ " is clicked in lnk_nominationChecklist\n");

	}

	public void enterNominationDetails(String fieldName, String fieldValue) {
		try {
			isElementDisplayed("inp_professionalHistory", fieldName);
			element("inp_professionalHistory", fieldName).clear();
			element("inp_professionalHistory", fieldName).sendKeys(fieldValue);
			logMessage("Step : " + fieldValue + " is entered in " + fieldName
					+ " in inp_professionalHistory\n");
		} catch (StaleElementReferenceException exp) {
			isElementDisplayed("inp_professionalHistory", fieldName);
			element("inp_professionalHistory", fieldName).clear();
			element("inp_professionalHistory", fieldName).sendKeys(fieldValue);
			logMessage("Step : " + fieldValue + " is entered in " + fieldName
					+ " in inp_professionalHistory\n");
		}

	}

	public void fillDetailsForProfessionalHistory(String employer,
			String title, String fromDate, String toDate, String description) {
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
		isElementDisplayed("inp_description");
		element("inp_description").sendKeys(description);
		logMessage("Step : " + description + " is entered in inp_description\n");
	}

	public void fillDetailsForProfessionalOrganizationAffiliations(
			String professionalOrg, String title, String fromDate, String toDate) {
		enterNominationDetails("Name", professionalOrg);
		enterNominationDetails("Title", title);
		enterNominationDetails("FromDate", fromDate);
		enterNominationDetails("ToDate", toDate);
		clickOnSaveButton("affiliation");
	}

	public void fillDetailsForVolunteerServiceToTheACSComunity(String title,
			String fromDate, String toDate, String description, String saveName) {
		enterNominationDetails("Title", title);
		enterNominationDetails("FromDate", fromDate);
		enterNominationDetails("ToDate", toDate);
		clickOnAllFieldsRequiredHeading();
		wait.waitForPageToLoadCompletely();
		String frame = WordUtils.capitalize(saveName);
		switchToFrame("txt" + frame + "Desc_ifr");
		enterDescription(description);
		switchToDefaultContent();
		clickOnSaveButton(saveName);
	}

	public void clickOnAllFieldsRequiredHeading() {
		isElementDisplayed("txt_allFieldsRequired");
		element("txt_allFieldsRequired").click();
	}

	public void fillDetailsForHonorAndAwards(String name, String sponsor,
			String year) {
		enterNominationDetails("Name", name);
		enterNominationDetails("Sponsor", sponsor);
		enterNominationDetails("Year", year);
		clickOnSaveButton("award");
	}

	public void searchByName_NumberForSecondaryNominatorAndVerifyMemberList(
			String searchType, String nominatorNumber, String name_number) {
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
			logMessage("Step : search type is not mention correctly");
		}

	}

	public void searchName(String nominatorNumber, String name) {
		isElementDisplayed("inp_searchName", nominatorNumber);
		element("inp_searchName", nominatorNumber).clear();
		element("inp_searchName", nominatorNumber).sendKeys(name);
		logMessage("Step : " + name + " is entered to search for nominator"
				+ nominatorNumber + "\n");
	}

	public void waitForSearchImageToDissappear(String nominatorNumber) {
		try {
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("img_searchNominator", nominatorNumber);
			logMessage("Wait for search image is to be dissappear for nominator"
					+ nominatorNumber + " \n");
			wait.waitForElementToDisappear(element("img_searchNominator",
					nominatorNumber));
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
		int sizeOfMembers = elements("list_selectMember", nominatorNumber)
				.size();
		if (sizeOfMembers > 0) {
			logMessage("ASSERT PASSED : List of members is present on search by name in secondary nominator "
					+ nominatorNumber + " \n");
		} else {
			Assert.fail("Member list is not present on search by name in secondary nominator "
					+ nominatorNumber + " \n");
		}
	}

	public void selectFirstNominator(String nominatorNumber) {
		isElementDisplayed("list_selectMember", nominatorNumber);
		elements("list_selectMember", nominatorNumber).get(0).click();
		logMessage("Step : first nominator is selected \n");
	}

	public void selectRadioButtonForNumber(String nominatorNumber) {
		isElementDisplayed("rad_searchNumber", nominatorNumber);
		element("rad_searchNumber", nominatorNumber).click();
		logMessage("Step : radio button for search by number is selected in rad_searchNumber\n");
	}

	public void searchByNumber(String nominatorNumber, String number) {
		isElementDisplayed("inp_searchByNumber", nominatorNumber);
		element("inp_searchByNumber", nominatorNumber).clear();
		element("inp_searchByNumber", nominatorNumber).sendKeys(number);
		logMessage("Step : " + number + " is entered in inp_searchByNumber\n");
	}

	public void clickFindMemberButton(String nominatorNumber) {
		isElementDisplayed("btn_findMemberNominator", nominatorNumber);
		element("btn_findMemberNominator", nominatorNumber).click();
		logMessage("Step : find member button is clicked in btn_findMemberNominator\n");

	}

	public void fillAllRequiredDetailsToSubmit() {
		mapFellowNominatedSmoke = YamlReader
				.getYamlValues("ASM_fellowNominatedSmokeChecklistData");
		getFellowNominated = new YamlInformationProvider(
				mapFellowNominatedSmoke);
		wait.waitForPageToLoadCompletely();
		
//		enterEducationDetails(
//				getFellowNominated
//						.getASM_fellowNominated_EduDetails("Institution"),
//				getFellowNominated.getASM_fellowNominated_EduDetails("Degree"),
//				getFellowNominated.getASM_fellowNominated_EduDetails("Major"),
//				getFellowNominated
//						.getASM_fellowNominated_EduDetails("GraduationYear"));
//		verifyAddMoreButton();
//		selectNominationChecklistName("Professional History");
//
//		fillDetailsForProfessionalHistory(
//				getFellowNominated
//						.getASM_fellowNominated_ProfDetails("Employer"),
//				getFellowNominated.getASM_fellowNominated_ProfDetails("Title"),
//				getFellowNominated
//						.getASM_fellowNominated_ProfDetails("FromDate"),
//				getFellowNominated.getASM_fellowNominated_ProfDetails("ToDate"),
//				getFellowNominated
//						.getASM_fellowNominated_ProfDetails("Description"));
//		verifyAddMoreButton();
		selectNominationChecklistName("Professional Organization Affiliations");
		fillDetailsForProfessionalOrganizationAffiliations(
				getFellowNominated
						.getASM_fellowNominated_ProfOrgDetails("ProfessionalOrg"),
				getFellowNominated
						.getASM_fellowNominated_ProfOrgDetails("Position_title"),
				getFellowNominated
						.getASM_fellowNominated_ProfOrgDetails("FromDate"),
				getFellowNominated
						.getASM_fellowNominated_ProfOrgDetails("ToDate"));
		verifyAddMoreButton();
		selectNominationChecklistName("Volunteer Service to the ACS Community");
		fillDetailsForVolunteerServiceToTheACSComunity(
				getFellowNominated.getASM_fellowNominated_ProfDetails("Title"),
				getFellowNominated
						.getASM_fellowNominated_ProfDetails("FromDate"),
				getFellowNominated.getASM_fellowNominated_ProfDetails("ToDate"),
				getFellowNominated
						.getASM_fellowNominated_ProfDetails("Description"),
				"service");
		verifyAddMoreButton();
		selectNominationChecklistName("Contributions to the Science / Profession");

		fillDetailsForVolunteerServiceToTheACSComunity(
				getFellowNominated.getASM_fellowNominated_ProfDetails("Title"),
				getFellowNominated
						.getASM_fellowNominated_ProfDetails("FromDate"),
				getFellowNominated.getASM_fellowNominated_ProfDetails("ToDate"),
				getFellowNominated
						.getASM_fellowNominated_ProfDetails("Description"),
				"contribution");
		verifyAddMoreButton();
		selectNominationChecklistName("Honors and Awards");
		fillDetailsForHonorAndAwards(
				getFellowNominated
						.getASM_fellowNominated_HonorAndAwardsDetails("Name"),
				getFellowNominated
						.getASM_fellowNominated_HonorAndAwardsDetails("Sponcor"),
				getFellowNominated
						.getASM_fellowNominated_HonorAndAwardsDetails("Year"));
		verifyAddMoreButton();
		selectNominationChecklistName("Select Supporters and Upload Letters");
		searchByName_NumberForSecondaryNominatorAndVerifyMemberList(
				"name",
				"1",
				getFellowNominated
						.getASM_fellowNominated_SelectSupportAndUploadLetters("SecondaryNominator1"));
		selectFirstNominator("1");

		selectFileToUpload("Secondary1", "test.pdf", "Secondary1");
		searchByName_NumberForSecondaryNominatorAndVerifyMemberList(
				"name",
				"2",
				getFellowNominated
						.getASM_fellowNominated_SelectSupportAndUploadLetters("SecondaryNominator2"));
		selectFirstNominator("2");
		selectFileToUpload("Secondary2", "test.pdf", "Secondary2");
		selectFileToUpload("Primary", "test.pdf", "Primary");
		selectFileToUpload("Attestation", "test.pdf", "Attestation");

		selectNominationChecklistName("Upload Resume/CV");
		selectFileToUpload("Resume", "test.pdf", "");
		selectNominationChecklistName("Upload Supplementary Information");
		selectFileToUpload("SupplementryMaterial", "test.pdf", "");
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

	public void selectFileToUpload(String fieldname, String fileName,
			String frameName) {
		String iframe = "document.getElementById('ifBridge" + frameName + "')";
		wait.hardWait(2);
		String path = "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "UploadFiles" + File.separator;
		File filePath = new File(path + fileName);
		isElementDisplayed("link_upload" + fieldname + "Nominator");

		executeJavascript("document.getElementById('ifBridge"
				+ frameName
				+ "');"
				+ iframe
				+ ".style.visibility='visible';"
				+ iframe
				+ ".style.display='block';"
				+ iframe
				+ ".setAttribute('width','500');"
				+ iframe
				+ ".setAttribute('height','200');"
				+ iframe
				+ ".contentWindow.document.getElementById('fuName').style.visibility='visible';"
				+ iframe
				+ ".contentWindow.document.getElementById('fuName').style.display='block';"
				+ iframe
				+ ".contentWindow.document.getElementById('fuName').setAttribute('height','500');"
				+ iframe
				+ ".contentWindow.document.getElementById('fuName').setAttribute('width','200')");
		switchToFrame("ifBridge" + frameName + "");
		// WebElement element = (WebElement) executeJavascript1("return " +
		// iframe
		// + ".contentDocument.getElementById('fuName')");
		isElementDisplayed("inp_upload");
		element("inp_upload").sendKeys(filePath.getAbsolutePath());
		switchToDefaultContent();
		hardWaitForIEBrowser(2);
		logMessage("Step : " + fileName + " is uploaded in " + fieldname
				+ " \n");
		verifyFileUploaded(fileName, fieldname);
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
		Toolkit.getDefaultToolkit().getSystemClipboard()
				.setContents(stringSelection, null);
	}

	public void removeUploadedFile(String nominationFieldName) {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("link_delete", nominationFieldName);
			element("link_delete", nominationFieldName).click();
			clickOnConfirm();
			logMessage("Step : click on delete link in link_delete\n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			handleAlert();
		} catch (NoSuchElementException exp) {
			logMessage("Step : file is not uploaded so remove button is not present \n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}
	}

	public void verifyFileUploaded(String fileName, String nominatorName) {
		try {
			wait.waitForPageToLoadCompletely();
			isElementDisplayed("link_delete" + nominatorName + "Nominator");
			logMessage("ASSERT PASSED : File " + fileName + " is uploaded for "
					+ nominatorName + "\n");
		} catch (StaleElementReferenceException exp) {
			isElementDisplayed("link_delete" + nominatorName + "Nominator");
			logMessage("ASSERT PASSED : File " + fileName + " is uploaded for "
					+ nominatorName + "\n");
		}
	}

	public void verifyCurrentTab(String tabName) {
		isElementDisplayed("tab_current");
		verifyElementText("tab_current", tabName);
	}

	public void clickOnContinueToNextButton() {
		isElementDisplayed("link_nextPage");
		element("link_nextPage").click();
		logMessage("Step : continue to next page is clicked in link_nextPage\n");
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
		logMessage("Step : contnue button is clicked for submit \n");
	}

	public void checkForSubmitNomination() {
		isElementDisplayed("chk_submitNomination");
		if (!element("chk_submitNomination").isSelected()) {
			element("chk_submitNomination").click();
			logMessage("Step : checkbox is checked for submit nomination in chk_submitNomination\n");
		} else {
			logMessage("Step : checkbox is already checked for submit nomination in chk_submitNomination\n");
		}
	}

	public void clickOnSubmitNomination() {
		isElementDisplayed("btn_submitNomination");
		element("btn_submitNomination").click();
		logMessage("Step : click on submit nomination in btn_submitNomination\n");
	}

	public void clickOnSubmitButton() {
		isElementDisplayed("btn_saveSubmit");
		element("btn_saveSubmit").click();
		logMessage("Step : save button is clicked inbtn_saveSubmit\n");
	}

	public void verifyNominationCompleted() {
		isElementDisplayed("txt_successNomination");
		verifyElementText("txt_successNomination", "Nomination Complete");
	}
}
