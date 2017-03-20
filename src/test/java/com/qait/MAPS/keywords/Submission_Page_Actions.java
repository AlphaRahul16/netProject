package com.qait.MAPS.keywords;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
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
		click(element("btn_saveContinue"));
		logMessage("Step : Clicked on Save & Continue button\n");
		wait.waitForPageToLoadCompletely();
		wait.hardWait(4);
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

	public void submitDetailsOnSelectSymposiumPage(String presentationType, String symposium, String sciMix) {
		selectPresentationType("type", presentationType);
		selectSymposium();
		selectPresentationType("sub_type", sciMix);

	}

	public void clickOnNamedButton(String linkName) {
		wait.waitForElementToBeVisible(element("lnk_createSubmission", linkName));
		isElementDisplayed("lnk_createSubmission", linkName);
		click(element("lnk_createSubmission", linkName));
		logMessage("Step : " + linkName + " link is clicked under View Submission tab\n");
		wait.hardWait(4);
	}

	public void verifyPageHeaderForASection(String header) {
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

	public void selectRandomActiveSubmissionProgram() {
		int sizeofActivePrograms;
		wait.hardWait(4);
		isElementDisplayed("btn_activeProgram");
		sizeofActivePrograms = elements("btn_activeProgram").size();
		click(elements("btn_activeProgram")
				.get(ASCSocietyGenericPage.generateRandomNumberWithInRange(0, sizeofActivePrograms)));
		logMessage("Step : Active Submission program is selected\n");
	}
	
	public String getSelectedProgramName()
	{
		String programName = null;
		programName=element("txt_activeProgramName").getText().trim();
		return programName;
	}

	public void clickOnContinueButtonOnProgramArea() {

		isElementDisplayed("btn_ContinueProgram");
		click(element("btn_ContinueProgram"));
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
		isElementDisplayed("txt_popupHeader");
		Assert.assertTrue(element("txt_popupHeader").getText().contains(headerName));
		logMessage("ASSERT PASSED: Popup Header is verified as " + headerName + "\n");
	}


	public void verifyCreatedInstitutionIsSelected(String selectionType,String institution) {
		isElementDisplayed("select_affiliations",selectionType);
		String selectedinstitution= getSelectedTextFromDropDown(element("select_affiliations",selectionType));
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
		if(searchValue.contains("Name"))
		{
			Assert.assertTrue(element("txt_AuthorsearchResults","Name").getText().contains("searchValue"));
			logMessage("ASSERT PASSED : Verified search results is equal to entered Name as "+searchValue);
		}
		else if( searchValue.contains("Email"))
		{
			Assert.assertTrue(element("txt_AuthorsearchResults","Email").getText().contains("searchValue"));
			logMessage("ASSERT PASSED : Verified search results is equal to entered Email as "+searchValue);
		}
		
		
	}
	
	public void verifySuccessAlertMessage(String successmsg)
	{
		isElementDisplayed("txt_successAlert");
		Assert.assertTrue(element("txt_successAlert").getText().equals(successmsg), " expected success message is not displayed\n");
		logMessage("ASSERT PASSED : success message is verified on submission as "+successmsg+"/n");

	}
	
	public void verifyNamedSectionIsDisplayed(String SectionName)
	{
		isElementDisplayed("tbl_section",SectionName);
		logMessage("ASSERT PASSED : "+SectionName+" section is displayed for abstracts\n");
	}

	public void verifyApplicationDisplaysSpecifiedAbstractUnderSpecifiedSections(String sectionName, String Status) {
		isElementDisplayed("txt_Tablesections_Status",sectionName);
		for (WebElement ele : elements("txt_Tablesections_Status",sectionName)) {
			Assert.assertTrue(ele.getText().trim().equals(Status),"Status under "+sectionName+" is not "+Status+"/n");
			
		}
		logMessage("Status under "+sectionName+" is verified as "+Status+"/n");
		
	}
	
	public void logOutFromMAPSApplication()
	{
		clickOnNamedButton("Log Out");
	}
	public void clickOnSaveAndContinueButtonInFooter(String btnName) {
		isElementDisplayed("btn_saveandContinueFooter", btnName);
		click(element("btn_saveandContinueFooter", btnName));
		logMessage("Step : Clicked on Save & Continue button on Step Incomplete page\n");
	}
	
	public void clickOnLinkUnderCreateNewSubmission(String lnkName){
		isElementDisplayed("lnk_submissionSteps",lnkName);
		click(element("lnk_submissionSteps",lnkName));
		logMessage("Step : Clicked on "+lnkName+" under Create New Submission section\n");

	}
	
	public void enterDetailsInDisclosuresSection(){
		selectRadioDetailsInDisclosuresSection("Reason for Submitting","1");
		selectRadioDetailsInDisclosuresSection("Agree to Bylaws","2");
		selectCheckboxDetailsInDisclosuresSection("Registration Requirement");
		selectCheckboxDetailsInDisclosuresSection("Meeting Attendance");
		selectCheckboxDetailsInDisclosuresSection("Withdrawal Confirmation");
		selectCheckboxDetailsInDisclosuresSection("Multiple Submissions");
	}
	
	public void selectRadioDetailsInDisclosuresSection(String label,String index){
		isElementDisplayed("radio_disclosures",label,index);
		click(element("radio_disclosures",label,index));
		logMessage("Step : "+label+" value is selected as "+element("radio_disclosures",label,index).getText().trim());
	}
	
	public void selectCheckboxDetailsInDisclosuresSection(String label){
		isElementDisplayed("chckbox_disclosures",label);
		click(element("chckbox_disclosures",label));
		logMessage("Step : I agree checkbox is clicked for "+label+" section\n");
	}
	
	public void clickOnSubmitButton(){
		isElementDisplayed("btn_submit");
		click(element("btn_submit"));
		logMessage("Step : Submit button is clicked\n");
	}
	
	public void verifyAllStepsAreCompleteOnReviewAndSubmitPage(int count){
		int index=1;
		while(index<=count){
			isElementDisplayed("img_chkCompletedStep",String.valueOf(index));
			logMessage("ASSERT PASSED: Step"+index+" is verified as complete\n");
		}
	}

	public void verifyAvailableOptionsForDraftedProgram(String draftoptions) {
		
		String optionsArray[]=draftoptions.split(",");
		for (WebElement ele : elements("sel_Drafts_options")) {
			System.out.println(ele.getText().trim());
			Assert.assertTrue(ele.getText().trim().equals(optionsArray[0].trim()),"Mentioned options are not available under draft options");
			logMessage("ASSERT PASSED: "+ele.getText().trim()+" options is displayed under draft action\n");
		}
		
	}

}
