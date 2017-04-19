package com.qait.MAPS.keywords;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class Submission_Page_Actions extends ASCSocietyGenericPage {

	WebDriver driver;
	static String pagename = "Submission_Page";

	public Submission_Page_Actions(WebDriver driver) {
		super(driver, pagename);
	}

	public void submitTitleAndBodyDetails(String title, String abstractData) {
		enterTitleDetails(title, "1");
		enterTitleDetails(abstractData, "2");
	}

	public void enterTitleDetails(String title, String index) {
		switchToFrame(element("iframe", index));
		isElementDisplayed("txt_title");
		element("txt_title").sendKeys(title);
		switchToDefaultContent();
	}

	public void clickOnSaveAndContinueButton() {
		scrollDown(element("btn_saveContinue"));
		isElementDisplayed("btn_saveContinue");
		// click(element("btn_saveContinue"));
		clickUsingXpathInJavaScriptExecutor(element("btn_saveContinue"));
		logMessage("Step : Clicked on Save & Continue button\n");
		wait.waitForPageToLoadCompletely();
	}

	public void uploadImage(String filename) {
		String path = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "UploadFiles"
				+ File.separator;
		File filePath = new File(path + filename);
		isElementDisplayed("btn_selectImage");
		element("btn_selectImage").sendKeys(filePath.getAbsolutePath());
		isElementDisplayed("btn_uploadImage");
		click(element("btn_uploadImage"));
	}

	public void submitDetailsOnSelectSymposiumPage(String presentationType, String sciMix) {
		selectPresentationType("type", presentationType);
		selectSymposium();
		selectPresentationType("sub_type", sciMix);

	}

	public void clickOnNamedButton(String linkName) {
		wait.waitForElementToBeVisible(element("lnk_createSubmission", linkName));
		isElementDisplayed("lnk_createSubmission", linkName);
		scrollDown(element("lnk_createSubmission", linkName));
		performClickByActionBuilder(element("lnk_createSubmission", linkName));
		logMessage("Step : " + linkName + " link is clicked under View Submission tab\n");
		wait.hardWait(4);
	}

	public void verifyPageHeaderForASection(String header) {
		wait.hardWait(5);
		dynamicWait(20, "txt_pageHeader", "");
		isElementDisplayed("txt_pageHeader");
		System.out.println(element("txt_pageHeader").getText().trim());
		Assert.assertTrue(element("txt_pageHeader").getText().trim().contains(header));
		logMessage("ASSERT PASSED: Page header is verified as " + header + "\n");
	}

	public void selectPresentationType(String dropdownType, String dropdownValue) {
		isElementDisplayed("select_presentationType", dropdownType);
		selectProvidedTextFromDropDown(element("select_presentationType", dropdownType), dropdownValue);
	}

	public void selectSymposium() {
		isElementDisplayed("select_symposiumType");
		// selectDropDownValue(element("select_symposiumType"), 2);
		click(element("select_symposiumType"));
	}

	public void verifyAuthorIsPresent() {
		isElementDisplayed("list_authors");
		Assert.assertTrue(elements("").size() > 1, "ASSERT FAILED: Author Details are not already present\n");
		logMessage("ASSERT PASSED: Author Details are already present\n");
	}

	public void clickOnShowAffiliationButton() {
		isElementDisplayed("btn_showAffiliations");
		click(element("btn_showAffiliations"));
		logMessage("Step : Show Affiliations button is clicked\n");
	}

	public int selectRandomActiveSubmissionProgram() {
		int sizeofActivePrograms, randomProg;
		wait.hardWait(4);
		isElementDisplayed("btn_activeProgram");
		sizeofActivePrograms = elements("btn_activeProgram").size();
		randomProg = ASCSocietyGenericPage.generateRandomNumberWithInRange(0, (sizeofActivePrograms - 1));
		click(elements("btn_activeProgram").get(randomProg));
		logMessage("Step : Active Submission program is selected\n");
		return randomProg;
	}

	public String getSelectedProgramName(int index) {
		String programName = null, programid;
		boolean flag;
		// includeJquery();
		// programid=executeJqueryAndReturnString("window.jQuery('input[name=XIK_SELECTED_ROLE_ID]:checked').attr('id')");
		for (WebElement element : elements("btn_activeProgram")) {
			programid = element.getAttribute("id");
			flag = (boolean) executeJavascriptReturnValue("document.getElementById('" + programid + "').checked");
			if (flag) {
				System.out.println(element("txt_activeProgramName", programid).getText().trim());
				programName = element("txt_activeProgramName", programid).getText().trim();
			} else {
				System.out.println("Not found");
			}
			// System.out.println(element.getCssValue("checked"));
			// System.out.println(element.ge);
		}
		// executeJavascript("");
		// System.out.println(programid);
		// programName=element("txt_activeProgramName",programid).getText().trim();
		// logMessage("Step : selected program name is "+programName);
		return programName;
	}

	public void clickOnContinueButtonOnProgramArea() {
		isElementDisplayed("btn_ContinueProgram");
		wait.hardWait(5);
		element("btn_ContinueProgram").click();
		logMessage("Step : Continue Button on Program Area page is clicked\n");

	}

	public void clickOnPopUpContinueButtonOnSelectingProgramArea(String buttonName) {
		isElementDisplayed("btn_popUp_Continue", buttonName);
		click(element("btn_popUp_Continue", buttonName));
		logMessage("Step : " + buttonName + " Button on Pop Up is clicked\n");
	}

	public void clickShowAffiliationsButton() {
		isElementDisplayed("btn_showAffiliations");
		click(element("btn_showAffiliations"));
		logMessage("Step : Show Affiliations button is clicked inside Authors step\n");
	}

	public void selectAMandatoryAffiliation(String selectionType, String selectionValue) {
		isElementDisplayed("select_affiliations", selectionType);
		selectProvidedTextFromDropDown(element("select_affiliations", selectionType), selectionValue);
		logMessage("Step : " + selectionType + " is selected as " + selectionValue);
	}

	public void createNewInstitution(String institutionName, String departmentName, String CityName, String StateName,
			String CountryName) {
		isElementDisplayed("txt_createInstitution_dialog");
		Assert.assertTrue(element("txt_createInstitution_dialog").getText().equals("Create Institution"),
				"Create Institution dialog does not appeared\n");
		logMessage("ASSERT PASSED : Create instituion pop up box is displayed\n");
		enterInputFieldsOnInstitutionPopUpBox("Institution", institutionName);
		enterInputFieldsOnInstitutionPopUpBox("Department", departmentName);
		enterInputFieldsOnInstitutionPopUpBox("City", CityName);
		selectProvidedTextFromDropDown(element("select_Institution_dialog", "INSTITUTION_STATE"), StateName);
		selectProvidedTextFromDropDown(element("select_Institution_dialog", "INSTITUTION_COUNTRY"), CountryName);
		clickOnPopUpContinueButtonOnSelectingProgramArea("Submit Created Institution");
	}

	private void enterInputFieldsOnInstitutionPopUpBox(String fieldName, String fieldValue) {
		isElementDisplayed("inp_institution_Fields", fieldName);
		EnterTextInField(element("inp_institution_Fields", fieldName), fieldValue);
		logMessage("Step : " + fieldName + " on create institution is entered as " + fieldValue + "\n");

	}

	// public void verifyCreatedInstitutionIsSelected(String
	// selectionType,String yamlValue) {
	// element("select_affiliations",selectionType).getAttribute(name)
	// selec
	//
	// }

	public void verifyPopUpHeader(String headerName) {
		wait.hardWait(3);
		isElementDisplayed("txt_popupHeader");
		Assert.assertTrue(element("txt_popupHeader").getText().contains(headerName));
		logMessage("ASSERT PASSED: Popup Header is verified as " + headerName + "\n");
	}

	public void verifyCreatedInstitutionIsSelected(String selectionType, String institution) {
		isElementDisplayed("select_affiliations", selectionType);
		String selectedinstitution = getSelectedTextFromDropDown(element("select_affiliations", selectionType));
		System.out.println(selectedinstitution);
		System.out.println(institution);
		Assert.assertTrue(selectedinstitution.contains(institution));
		logMessage("ASSERT PASSED : verified Created institution is selected\n");

	}

	public void clickAddAuthorButton() {

		isElementDisplayed("btn_addAuthor");
		click(element("btn_addAuthor"));
		logMessage("Step : Add author button is clicked\n");

	}

	public void searchAuthorByEnteringDetails(String searchCriteria, String searchValue) {
		enterInputFieldsOnInstitutionPopUpBox(searchCriteria, searchValue);
		clickOnNamedButton("Search");

	}

	public void verifyValidSearchResultsAreDisplayed(String searchValue) {
		if (searchValue.contains("Name")) {
			Assert.assertTrue(element("txt_AuthorsearchResults", "Name").getText().contains("searchValue"));
			logMessage("ASSERT PASSED : Verified search results is equal to entered Name as " + searchValue);
		} else if (searchValue.contains("Email")) {
			Assert.assertTrue(element("txt_AuthorsearchResults", "Email").getText().contains("searchValue"));
			logMessage("ASSERT PASSED : Verified search results is equal to entered Email as " + searchValue);
		}

	}

	public void verifySuccessAlertMessage(String successmsg) {
		isElementDisplayed("txt_successAlert");
		System.out.println("Actual:" + element("txt_successAlert").getText().trim());
		System.out.println("Expected:" + successmsg);
		Assert.assertTrue(element("txt_successAlert").getText().trim().contains(successmsg),
				" expected success message is not displayed\n");
		logMessage("ASSERT PASSED : success message is verified on submission as " + successmsg + "/n");

	}

	public void verifyNamedSectionIsDisplayed(String SectionName) {
		isElementDisplayed("tbl_section", SectionName);
		logMessage("ASSERT PASSED : " + SectionName + " section is displayed for abstracts\n");
	}

	public void verifyApplicationDisplaysSpecifiedAbstractUnderSpecifiedSections(String sectionName, String Status) {
		isElementDisplayed("txt_Tablesections_Status", sectionName);
		for (WebElement ele : elements("txt_Tablesections_Status", sectionName)) {
			Assert.assertTrue(ele.getText().trim().equals(Status),
					"Status under " + sectionName + " is not " + Status + "/n");
			logMessage("Status under " + sectionName + " is verified as " + Status + "\n");
		}
	}

	public void logOutFromMAPSApplication() {
		clickOnNamedButton("Log Out");
	}

	public void clickOnSaveAndContinueButtonInFooter(String btnName) {
		isElementDisplayed("btn_saveandContinueFooter", btnName);
		click(element("btn_saveandContinueFooter", btnName));
		logMessage("Step : Clicked on Save & Continue button on Step Incomplete page\n");
	}

	public void clickOnLinkUnderCreateNewSubmission(String lnkName) {
		isElementDisplayed("lnk_submissionSteps", lnkName);
		click(element("lnk_submissionSteps", lnkName));
		logMessage("Step : Clicked on " + lnkName + " under Create New Submission section\n");

	}

	public void enterDetailsInDisclosuresSection() {
		selectRadioDetailsInDisclosuresSection("Reason for Submitting", "1");
		selectRadioDetailsInDisclosuresSection("Agree to Bylaws", "2");
		selectCheckboxDetailsInDisclosuresSection("Registration Requirement");
		selectCheckboxDetailsInDisclosuresSection("Meeting Attendance");
		selectCheckboxDetailsInDisclosuresSection("Withdrawal Confirmation");
		selectCheckboxDetailsInDisclosuresSection("Multiple Submissions");
	}

	public void selectRadioDetailsInDisclosuresSection(String label, String index) {
		isElementDisplayed("radio_disclosures", label, index);
		click(element("radio_disclosures", label, index));
		logMessage("Step : " + label + " value is selected as "
				+ element("radio_disclosures", label, index).getText().trim());
	}

	public void selectCheckboxDetailsInDisclosuresSection(String label) {
		isElementDisplayed("chckbox_disclosures", label);
		click(element("chckbox_disclosures", label));
		logMessage("Step : I agree checkbox is clicked for " + label + " section\n");
	}

	public void clickOnSubmitButton() {
		isElementDisplayed("btn_submit");
		click(element("btn_submit"));
		logMessage("Step : Submit button is clicked\n");
	}

	public void verifyAllStepsAreCompleteOnReviewAndSubmitPage(int count) {
		int index = 1;
		while (index <= count) {
			isElementDisplayed("img_chkCompletedStep", String.valueOf(index));
			logMessage("ASSERT PASSED: Step" + index + " is verified as complete\n");
			index++;
		}
	}

	public void selectEditActionForSubmittedAbstracts(String programName, String action) {
		isElementDisplayed("select_submissionAction", programName);
		selectProvidedTextFromDropDown(element("select_submissionAction", programName), action);
		logMessage("Step: " + action + " action is selected for " + programName + "\n");
	}

	public void verifyPopUpHeaderOnSubmissionPage(String headerName) {
		isElementDisplayed("btn_draftStatus", headerName);
		logMessage("ASSERT PASSED: Poup window " + headerName + " is verified\n");
	}

	public void clickOnDraftStatusActionButton(String headerName) {
		isElementDisplayed("btn_draftStatus", headerName);
		click(element("btn_draftStatus", headerName));
		logMessage("Step : " + headerName + " button is clicked\n");
	}

	public void verifyAvailableOptionsForDraftedProgram(String draftoptions, String programName, String sectionId) {

		boolean flag = false;
		String optionsArray[] = draftoptions.split(",", 3);
		System.out.println("----program name:" + programName);

		for (WebElement ele : elements("sel_Drafts_options", sectionId, programName)) {
			System.out.println("actual:" + ele.getText().trim());
			for (String str : optionsArray) {
				System.out.println("expected:" + str);
				if (str.equals(ele.getText().trim())) {
					flag = true;
					break;
				}
			}
			Assert.assertTrue(flag,
					"Mentioned option" + ele.getText().trim() + " is not available under draft options\n");
			logMessage("ASSERT PASSED: " + ele.getText().trim() + " option is displayed under draft action\n");
		}

		// for (WebElement ele : elements("sel_Drafts_options", sectionId,
		// programName)) {
		//
		// System.out.println(ele.getText().trim());
		// if(ele.getText().trim().equals(optionsArray[i]))
		// flag=true;
		// else
		// System.out.println("expected:" + optionsArray[0]);
		// Assert.assertTrue(ele.getText().trim().equals(optionsArray[0].trim()),
		// "Mentioned options are not available under draft options");
		// logMessage("ASSERT PASSED: " + ele.getText().trim() + " options is
		// displayed under draft action\n");
		// }
	}

	public void selectPreDraftedAbstractForEditing(String sectionId, String programName, String draftOption) {
		isElementDisplayed("select_editDraft", sectionId, programName);
		selectProvidedTextFromDropDown(element("select_editDraft", sectionId, programName), draftOption);
		logMessage("Step : " + draftOption + " is selected for " + programName + " program\n");
	}

	public void verifyStatusOfSubmittedAbstract(String sectionId, String programName, String status, String index) {
		isElementDisplayed("txt_abstractStatus", sectionId, programName, index);
		Assert.assertTrue(
				element("txt_abstractStatus", sectionId, programName, index).getText().trim().equalsIgnoreCase(status),
				"ASSERT FAILED: Status of submitted abstract " + programName + " is not " + status + "\n");
		logMessage("ASSERT PASSED: Status of submitted abstract" + programName + " is " + status + "\n");
	}

	public String getIDofAbstract(String subs, String title, String index, String label) {
		isElementDisplayed("txt_abstractStatus", subs, title, index);
		String value = element("txt_abstractStatus", subs, title, index).getText().trim();
		logMessage("Step: '" + label + "' of " + title + " is fetched as " + value + "\n");
		return value;
	}

}
