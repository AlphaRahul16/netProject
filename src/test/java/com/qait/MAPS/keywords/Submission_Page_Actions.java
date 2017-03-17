package com.qait.MAPS.keywords;

import java.io.File;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class Submission_Page_Actions extends ASCSocietyGenericPage {

	WebDriver driver;
	static String pagename = "Submission_Page";


	public Submission_Page_Actions(WebDriver driver) {
		super(driver, pagename);
	}
	
	public void submitTitleAndBodyDetails(String title, String abstractData){
		enterTitleDetails(title,"1");
		enterTitleDetails(abstractData,"2");
	}
	
	public void enterTitleDetails(String title,String index){
		switchToFrame(element("iframe",index));
		isElementDisplayed("txt_title");
		element("txt_title").sendKeys(title);
		switchToDefaultContent();
	}
	
	public void clickOnSaveAndContinueButton(){
		isElementDisplayed("btn_saveContinue");
		click(element("btn_saveContinue"));
		logMessage("Step : Clicked on Save & Continue button\n");
	}
	
	public void uploadImage(String filename){
		String path = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "UploadFiles"
				+ File.separator;
		File filePath = new File(path + filename);
		isElementDisplayed("btn_selectImage");
		element("btn_selectImage").sendKeys(filePath.getAbsolutePath());
		isElementDisplayed("btn_uploadImage");
		click(element("btn_uploadImage"));
	}
	
	public void submitDetailsOnSelectSymposiumPage(String presentationType, String symposium,String sciMix){
		selectSymposium("type",presentationType);
		selectSymposium("symposia_title",symposium);
		selectSymposium("sub_type",sciMix);

	}

	public void clickOnNamedButton(String linkName)
	{
		wait.waitForElementToBeVisible(element("lnk_createSubmission",linkName));
		isElementDisplayed("lnk_createSubmission",linkName);
		click(element("lnk_createSubmission",linkName));
		logMessage("Step : "+linkName+" link is clicked under View Submission tab\n");
		wait.hardWait(4);

	}
	
	
	public void verifyPageHeaderForASection(String header)
	{
			isElementDisplayed("txt_pageHeader");
			Assert.assertTrue(element("txt_pageHeader").getText().contains(header));
			logMessage("ASSERT PASSED: Page header is verified as "+header+"\n");
		}

	

	public void selectSymposium(String dropdownType,String dropdownValue){
		isElementDisplayed("select_symposiumType",dropdownType);
		selectProvidedTextFromDropDown(element("select_symposiumType",dropdownType), dropdownValue);
	}
	
	public void selectRandomActiveSubmissionProgram()
	{
		int sizeofActivePrograms;
		isElementDisplayed("btn_activeProgram");
		sizeofActivePrograms = elements("btn_activeProgram").size();
		click(elements("btn_activeProgram").get(ASCSocietyGenericPage.generateRandomNumberWithInRange(0,sizeofActivePrograms)));
		logMessage("Step : Active Submission program is selected\n");
	}

	public void clickOnContinueButtonOnProgramArea() {
		
		isElementDisplayed("btn_ContinueProgram");
		click(element("btn_ContinueProgram"));
		logMessage("Step : Continue Button on Program Area page is clicked\n");
		
	}
	
	public void clickOnPopUpContinueButtonOnSelectingProgramArea(String buttonName)
	{
		isElementDisplayed("btn_popUp_Continue",buttonName);
		click(element("btn_popUp_Continue",buttonName));
		logMessage("Step : "+buttonName+" Button on Pop Up is clicked\n");
	}
	
	public void clickShowAffiliationsButton()
	{
		isElementDisplayed("btn_showAffiliations");
		click(element("btn_showAffiliations"));
		logMessage("Step : Show Affiliations button is clicked inside Authors step\n");
	}
	
	public void selectAMandatoryAffiliation(String selectionType, String selectionValue)
	{
		isElementDisplayed("select_affiliations",selectionType);
		selectProvidedTextFromDropDown(element("select_affiliations",selectionType), selectionValue);
		logMessage("Step : "+selectionType+" is selected as "+selectionValue);
	}
	
	public void createNewInstitution(String institutionName, String departmentName, String CityName,String StateName, String CountryName)
	{
		isElementDisplayed("txt_createInstitution_dialog");
		Assert.assertTrue(element("txt_createInstitution_dialog").getText().equals("Create Institution"),"Create Institution dialog does not appeared\n");
		logMessage("ASSERT PASSED : Create instituion pop up box is displayed\n");
		enterInputFieldsOnInstitutionPopUpBox("Institution",institutionName); 
		enterInputFieldsOnInstitutionPopUpBox("Department",departmentName);
		enterInputFieldsOnInstitutionPopUpBox("City",CityName);
		selectProvidedTextFromDropDown(element("select_Institution_dialog","INSTITUTION_STATE"), StateName);
		selectProvidedTextFromDropDown(element("select_Institution_dialog","INSTITUTION_COUNTRY"), CountryName);
		clickOnPopUpContinueButtonOnSelectingProgramArea("Submit Created Institution");
	}

	private void enterInputFieldsOnInstitutionPopUpBox(String fieldName,String fieldValue) {
		isElementDisplayed("inp_institution_Fields",fieldName);
		EnterTextInField(element("inp_institution_Fields",fieldName), fieldValue);
		logMessage("Step : "+fieldName+" on create institution is entered as "+fieldValue+"\n");
		
	}

//	public void verifyCreatedInstitutionIsSelected(String selectionType,String yamlValue) {
//		element("select_affiliations",selectionType).getAttribute(name)
//		selec
//		
//	}
	
	

}

