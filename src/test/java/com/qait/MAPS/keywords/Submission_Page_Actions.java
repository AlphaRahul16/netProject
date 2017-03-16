package com.qait.MAPS.keywords;

import java.io.File;

import org.openqa.selenium.WebDriver;

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
	
	public void selectSymposium(String dropdownType,String dropdownValue){
		isElementDisplayed("select_symposiumType",dropdownType);
		selectProvidedTextFromDropDown(element("select_symposiumType",dropdownType), dropdownValue);
	}

}
