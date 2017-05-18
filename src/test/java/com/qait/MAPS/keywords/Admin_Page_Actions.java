package com.qait.MAPS.keywords;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class Admin_Page_Actions extends ASCSocietyGenericPage {

	WebDriver driver;
	static String pagename = "Admin_Page";

	public Admin_Page_Actions(WebDriver driver) {
		super(driver, pagename);
	}

	public void verifySearchFieldIsDisplayed(String fieldName, String title) {
		isElementDisplayed("inp_searchField", fieldName);
		logMessage("ASSERT PASSED: " + title + " is displayed on page\n");
	}

	public void clickLeftNavigationPanelOptions(String fieldName) {
		isElementDisplayed("lnk_leftPanelInstructions", fieldName);
		click(element("lnk_leftPanelInstructions", fieldName));
		logMessage("Step : " + fieldName + " is clicked on left navigation panel\n");
	}
	
	public void verifyFieldsOnLeftNavigationPanel(String[] fieldsName){		
		for(String field:fieldsName){
			if(field.equals("Instructions")){
				verfiyDefaultFieldOnLeftNavigationPanel(field);
			}
			else{
			isElementDisplayed("lnk_leftPanelInstructions",field);
			logMessage("ASSERT PASSED: "+field+" option is displayed on page\n");
			}
		}
	}
	
	public void verfiyDefaultFieldOnLeftNavigationPanel(String fieldName){
		isElementDisplayed("lnk_defaultInstructions",fieldName);
		logMessage("ASSERT PASSED: "+fieldName+" option is displayed on page\n");
	}
	
	public void verifyTitleDescriptionIsDisplayed(String title){
		isElementDisplayed("txt_titleDescription",title);
		logMessage("ASSERT PASSED: Description is displayed for "+title+"\n");
	}
	
	public void verifyInputFieldsInSearchCriteriaTable(String criteriaHeading){
		isElementDisplayed("inp_usrDetails",criteriaHeading);
		logMessage("Step : Search Criteria "+criteriaHeading+" is present in search criteria table\n");
	}
	
	public void verifyDropdownFieldsInSearchCriteriaTable(String criteriaHeading){
		isElementDisplayed("drpdwn_usrDetails",criteriaHeading);
		logMessage("Step : Search Criteria "+criteriaHeading+" is present in search criteria table\n");
	}
	
	public void verifyFieldsInSearchCriteriaTable(String[] inputCriterias,String[] dropDownCriterias){
		for(String criteriaName: inputCriterias){
			verifyInputFieldsInSearchCriteriaTable(criteriaName);
		}
		
		for(String criteriaName: dropDownCriterias){
			verifyDropdownFieldsInSearchCriteriaTable(criteriaName);
		}
		verifySearchButton();
	}
	
	public void verifySearchButton(){
		isElementDisplayed("img_searchButton");
		logMessage("ASSERT PASSED: Search button is displayed in table\n");
	}
}
