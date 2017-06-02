package com.qait.MAPS.keywords;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.gargoylesoftware.htmlunit.javascript.host.Iterator;
import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class Admin_Page_Actions extends ASCSocietyGenericPage {

	WebDriver driver;
	static String pagename = "Admin_Page";

	public Admin_Page_Actions(WebDriver driver) {
		super(driver, pagename);
	}

	public void clickLeftNavigationPanelOptions(String fieldName) {
		isElementDisplayed("lnk_leftPanelInstructions", fieldName);
		click(element("lnk_leftPanelInstructions", fieldName));
		logMessage("Step : " + fieldName + " is clicked on left navigation panel\n");
	}

	public void enterUserDetailsToAdd(String name, String value) {
		isElementDisplayed("inp_usrDetails", name);
		EnterTextInField(element("inp_usrDetails", name), value);
		logMessage("Step : Detail " + name + " is entered as " + value);
	}

	public void verifySearchFieldIsDisplayed(String fieldName, String title) {
		isElementDisplayed("inp_searchField", fieldName);
		logMessage("ASSERT PASSED: " + title + " is displayed on page\n");
	}

	public void verifyFieldsOnLeftNavigationPanel(String[] fieldsName) {
		for (String field : fieldsName) {
			if (field.equals("Instructions")) {
				verfiyDefaultFieldOnLeftNavigationPanel(field);
			} else {
				isElementDisplayed("lnk_leftPanelInstructions", field);
				logMessage("ASSERT PASSED: " + field + " option is displayed on page\n");
			}
		}
	}

	public void verfiyDefaultFieldOnLeftNavigationPanel(String fieldName) {
		isElementDisplayed("lnk_defaultInstructions", fieldName);
		logMessage("ASSERT PASSED: " + fieldName + " option is displayed on page\n");
	}

	public void verifyTitleDescriptionIsDisplayed(String title) {
		isElementDisplayed("txt_titleDescription", title);
		logMessage("ASSERT PASSED: Description is displayed for " + title + "\n");
	}

	public void verifyInputFieldsInSearchCriteriaTable(String criteriaHeading) {
		isElementDisplayed("inp_usrDetails", criteriaHeading);
		logMessage("Step : Search Criteria " + criteriaHeading + " is present in search criteria table\n");
	}

	public void verifyDropdownFieldsInSearchCriteriaTable(String criteriaHeading) {
		isElementDisplayed("drpdwn_usrDetails", criteriaHeading);
		logMessage("Step : Search Criteria " + criteriaHeading + " is present in search criteria table\n");
	}

	public void verifyFieldsInSearchCriteriaTable(String[] inputCriterias, String[] dropDownCriterias) {
		for (String criteriaName : inputCriterias) {
			verifyInputFieldsInSearchCriteriaTable(criteriaName);
		}

		for (String criteriaName : dropDownCriterias) {
			verifyDropdownFieldsInSearchCriteriaTable(criteriaName);
		}
		verifySearchButton();
	}

	public void verifySearchButton() {
		isElementDisplayed("img_searchButton");
		logMessage("ASSERT PASSED: Search button is displayed in table\n");
	}


	public void clickNamedButtonImage(String buttonName)
	{
		isElementDisplayed("img_searchButton",buttonName);
		click(element("img_searchButton",buttonName));
		logMessage("Step : "+buttonName+" button is clicked\n");
		wait.hardWait(2);
		wait.waitForPageToLoadCompletely();
	}

	private void selectOptionsToAddUser(String fieldname, String fieldvalue)
	{
		isElementDisplayed("drpdwn_usrDetails",fieldname);
		selectProvidedTextFromDropDown(element("drpdwn_usrDetails",fieldname), fieldvalue);
	}

	private void checkRoleForUser(String roleName)
	{
		isElementDisplayed("chkbox_selectRole",roleName);
		click(element("chkbox_selectRole",roleName));
		logMessage("Step : role "+roleName+" is selected for user\n");
	}

	public void verifyAccountCreationMessage(String msg)
	{
		isElementDisplayed("txt_confim_msg",msg);
		logMessage("ASSERT PASSED : confirmation message for account creation is displayed as "+msg);
	}

	public void enterDetailsToAddNewUserUnderPeople(Map<String, Object> userDetails)
	{
		long uniquefield = System.currentTimeMillis();
		enterUserDetailsToAdd("First Name", toString().valueOf(userDetails.get("FirstName")));
		enterUserDetailsToAdd("Last Name", toString().valueOf(userDetails.get("LastName"))+uniquefield);
		clickNamedButtonImage("next");
		enterUserDetailsToAdd("E-mail", toString().valueOf(userDetails.get("Email")));
		enterUserDetailsToAdd("Institution", toString().valueOf(userDetails.get("Institution")));
		enterUserDetailsToAdd("City", toString().valueOf(userDetails.get("City")));
		selectOptionsToAddUser("Country", toString().valueOf(userDetails.get("Country")));
		clickNamedButtonImage("next");
		enterUserDetailsToAdd("User ID", toString().valueOf(userDetails.get("UserId"))+uniquefield);
		clickNamedButtonImage("next");
		checkRoleForUser(toString().valueOf(userDetails.get("Role")));
		clickNamedButtonImage("finish");

	}

	public void verifyReviewerRoleOptionInReports(String fieldname, String reviwerRoleOptionsArray[])
	{
		int i=0;
		for (WebElement iterable_element : elements("lst_options_drpdown",fieldname)) {
			Assert.assertTrue(iterable_element.getText().equals(reviwerRoleOptionsArray[i]));
			logMessage("ASSERT PASSED : option "+reviwerRoleOptionsArray[i++]+" is verified in "+fieldname+" dropdown\n");
		}
	}

	public void verifyTablesPresentInReviewerReportTab()
	{
		isElementDisplayed("table_report");
		logMessage("ASSERT PASSED : Diffrent tables for suboptions are displayed\n");
	}

	public String selectRandomControlId() {
		int randomlink =  generateRandomNumberWithInRange(0, elements("lnk_controlId").size());
		isElementDisplayed("lnk_controlId");
		String controlId = elements("lnk_controlId").get(randomlink).getText();
		elements("lnk_controlId").get(randomlink).click();
		return controlId;

	}

	public void verifyApplicationLaunchesNewWindowOnClickingControlId(String windowTitle) {
		changeWindow(1);
		Assert.assertTrue(getPageTitle().contains(windowTitle));
		logMessage("ASSERT PASSED : new window title is verified as "+windowTitle);
	}

	public void clickEditLinkForTitleAndBody()
	{
		isElementDisplayed("btn_editTitle");
		click(element("btn_editTitle"));
		logMessage("Step : Edit button is clicked For title and body\n");
	}

	public String checkActiveCheckboxOfTemplate()
	{
		int randomCheckbox = 0 ;
		String rolename;
		if(isElementDisplayed("chkbox_chked_activate"))
		{
			 randomCheckbox = generateRandomNumberWithInRange(0, (elements("chkbox_chked_activate").size()-1));
			System.out.println(randomCheckbox);
			elements("chkbox_chked_activate").get(randomCheckbox).click();
			logMessage("Step: Active checkbox is unchecked to inactive\n");
		}
		rolename = elements("txt_chked_txtBox_name").get(randomCheckbox).getText();
		return rolename;
		


	}

	public void enterSearchCriteria(HashMap criteriaList){
		HashMap<String, String> searchCriteria = new HashMap<String, String>();
		searchCriteria=criteriaList;
		for (String key : searchCriteria.keySet()) {
			enterUserDetailsToAdd(key,searchCriteria.get(key));
		}		
	}

	public void navigateToOriginalAdminWindow() {
		changeWindow(0);

	}

	public void verifyStatusForRoleIsChangedTo(String status, String statusRoleName) {
		isElementDisplayed("txt_status",statusRoleName);
		Assert.assertTrue(element("txt_status",statusRoleName).getText().trim().equals(status));
		logMessage("ASSERT PASSED : Status for role "+statusRoleName+" is verifed as "+status+"\n");
		
	}

	public void checkInactiveCheckboxOfTemplate(String statusRoleName) {
		isElementDisplayed("chkbox_inactiveRole",statusRoleName);
		click(element("chkbox_inactiveRole",statusRoleName));
		logMessage("Step : Inactive checkbox is checked to active for role "+statusRoleName);
		
	}

	public void addAndVerifyTemplateIsAddedToCustomEmail(String templateName) {
		enterTemplateNameAndClickAddButton(templateName);
		verifyAddedTemplateIsDisplayedOnTheList(templateName);
	}

	private void enterTemplateNameAndClickAddButton(String templateName) {
		isElementDisplayed("inp_templateName");
		EnterTextInField(element("inp_templateName"), templateName);
		clickNamedButtonImage("add");	
	}
	
	private void verifyAddedTemplateIsDisplayedOnTheList(String templateName)
	{
		isElementDisplayed("lnk_templateName",templateName);
		logMessage("ASSERT PASSED : Added template is displayed in the custom template list\n");
	}

	public void verifyTablesHeadingsSuboptions(String[] emailLogHeadings) {
		int i=0;
		for (WebElement ele : elements("txt_emaillog_tbl_headings")) {
			System.out.println(ele.getText().replace("\r", "").replace("\n", "").trim());
			System.out.println(emailLogHeadings[i]);
			Assert.assertTrue(ele.getText().replace("\r", "").replace("\n", "").trim().equals(emailLogHeadings[i]));
			logMessage("ASSERT PASSED : Table sub headings is verified as "+emailLogHeadings[i]);
			i++;
		}
		
	}
	private void selectEmailTemplateToSearchEmail(String searchCriteria, String searchType) {
		isElementDisplayed("select_email_template",searchType);
		selectProvidedTextFromDropDown(element("select_email_template",searchType), searchCriteria);
	    logMessage("Step : Email template "+searchType+" is selected as "+searchCriteria);
	}
	
	public void enterEmailSearchCriteriaFields(String emailTemplateName,String emailStatus)
	{
		selectEmailTemplateToSearchEmail(emailTemplateName, "EMAIL_SEARCH_TEMPLATEID");
		selectEmailTemplateToSearchEmail(emailStatus, "EMAIL_SEARCH_STATUS");
	}
	
	
}
