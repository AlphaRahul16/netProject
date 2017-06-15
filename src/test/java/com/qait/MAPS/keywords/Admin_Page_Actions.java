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
		wait.hardWait(3);
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

	public void clickNamedButtonImage(String buttonName) {
		isElementDisplayed("img_searchButton", buttonName);
		click(element("img_searchButton", buttonName));
		logMessage("Step : " + buttonName + " button is clicked\n");
		wait.hardWait(2);
		wait.waitForPageToLoadCompletely();
	}

	private void selectOptionsToAddUser(String fieldname, String fieldvalue) {
		isElementDisplayed("drpdwn_usrDetails", fieldname);
		selectProvidedTextFromDropDown(element("drpdwn_usrDetails", fieldname), fieldvalue);
	}

	private void checkRoleForUser(String roleName) {
		isElementDisplayed("chkbox_selectRole", roleName);
		click(element("chkbox_selectRole", roleName));
		logMessage("Step : role " + roleName + " is selected for user\n");
	}

	public void verifyAccountCreationMessage(String msg) {
		isElementDisplayed("txt_confim_msg", msg);
		logMessage("ASSERT PASSED : confirmation message for account creation is displayed as " + msg);
	}

	public void enterDetailsToAddNewUserUnderPeople(Map<String, Object> userDetails) {
		long uniquefield = System.currentTimeMillis();
		enterUserDetailsToAdd("First Name", toString().valueOf(userDetails.get("FirstName")));
		enterUserDetailsToAdd("Last Name", toString().valueOf(userDetails.get("LastName")) + uniquefield);
		clickNamedButtonImage("next");
		enterUserDetailsToAdd("E-mail", toString().valueOf(userDetails.get("Email")));
		enterUserDetailsToAdd("Institution", toString().valueOf(userDetails.get("Institution")));
		enterUserDetailsToAdd("City", toString().valueOf(userDetails.get("City")));
		selectOptionsToAddUser("Country", toString().valueOf(userDetails.get("Country")));
		clickNamedButtonImage("next");
		enterUserDetailsToAdd("User ID", toString().valueOf(userDetails.get("UserId")) + uniquefield);
		clickNamedButtonImage("next");
		checkRoleForUser(toString().valueOf(userDetails.get("Role")));
		clickNamedButtonImage("finish");

	}

	public void verifyReviewerRoleOptionInReports(String fieldname, String reviwerRoleOptionsArray[]) {
		int i = 0;
		for (WebElement iterable_element : elements("lst_options_drpdown", fieldname)) {
			Assert.assertTrue(iterable_element.getText().equals(reviwerRoleOptionsArray[i]));
			logMessage("ASSERT PASSED : option " + reviwerRoleOptionsArray[i++] + " is verified in " + fieldname
					+ " dropdown\n");
		}
	}

	public void verifyTablesPresentInReviewerReportTab() {
		isElementDisplayed("table_report");
		logMessage("ASSERT PASSED : Diffrent tables for suboptions are displayed\n");
	}

	public String selectRandomControlId() {
		int randomlink = generateRandomNumberWithInRange(0, elements("lnk_controlId").size());
		isElementDisplayed("lnk_controlId");
		String controlId = elements("lnk_controlId").get(randomlink-1).getText();
		elements("lnk_controlId").get(randomlink).click();
		return controlId;

	}

	public void verifyApplicationLaunchesNewWindowOnClickingControlId(String windowTitle) {
		changeWindow(1);
		Assert.assertTrue(getPageTitle().contains(windowTitle));
		logMessage("ASSERT PASSED : new window title is verified as " + windowTitle);
	}

	public void clickEditLinkForTitleAndBody() {
		isElementDisplayed("btn_editTitle");
		click(element("btn_editTitle"));
		logMessage("Step : Edit button is clicked For title and body\n");
	}

	public String checkActiveCheckboxOfTemplate() {
		int randomCheckbox = 0;
		String rolename;
		if (isElementDisplayed("chkbox_chked_activate")) {
			randomCheckbox = generateRandomNumberWithInRange(0, (elements("chkbox_chked_activate").size() - 1));
			System.out.println(randomCheckbox);
			elements("chkbox_chked_activate").get(randomCheckbox).click();
			logMessage("Step: Active checkbox is unchecked to inactive\n");
		}
		rolename = elements("txt_chked_txtBox_name").get(randomCheckbox).getText();
		return rolename;

	}

	public void clickOnSearchButton() {
		isElementDisplayed("img_searchButton");
		click(element("img_searchButton"));
		logMessage("Step : Clicked on Search button\n");
	}

	public void enterSearchCriteria(HashMap criteriaList) {
		HashMap<String, String> searchCriteria = new HashMap<String, String>();
		searchCriteria = criteriaList;
		for (String key : searchCriteria.keySet()) {
			enterUserDetailsToAdd(key, searchCriteria.get(key));
		}
	}

	public void navigateToOriginalAdminWindow() {
		changeWindow(0);

	}

	public void verifyStatusForRoleIsChangedTo(String status, String statusRoleName) {
		isElementDisplayed("txt_status", statusRoleName);
		Assert.assertTrue(element("txt_status", statusRoleName).getText().trim().equals(status));
		logMessage("ASSERT PASSED : Status for role " + statusRoleName + " is verifed as " + status + "\n");

	}

	public void checkInactiveCheckboxOfTemplate(String statusRoleName) {
		isElementDisplayed("chkbox_inactiveRole", statusRoleName);
		click(element("chkbox_inactiveRole", statusRoleName));
		logMessage("Step : Inactive checkbox is checked to active for role " + statusRoleName);

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

	private void verifyAddedTemplateIsDisplayedOnTheList(String templateName) {
		isElementDisplayed("lnk_templateName", templateName);
		logMessage("ASSERT PASSED : Added template is displayed in the custom template list\n");
	}

	public void verifyTablesHeadingsSuboptions(String[] emailLogHeadings) {
		int i = 0;
		for (WebElement ele : elements("txt_emaillog_tbl_headings")) {
			System.out.println(ele.getText().replace("\r", "").replace("\n", "").trim());
			System.out.println(emailLogHeadings[i]);
			Assert.assertTrue(ele.getText().replace("\r", "").replace("\n", "").trim().equals(emailLogHeadings[i]));
			logMessage("ASSERT PASSED : Table sub headings is verified as " + emailLogHeadings[i]);
			i++;
		}

	}

	public String enterValueInCurrentTemplatePage(String newEmail) {

		isElementDisplayed("inp_searchField", "EMAIL_TEMPLATE_FROM");
		// getValUsingXpathInJavaScriptExecutor(element("inp_searchField",
		// "EMAIL_TEMPLATE_FROM"))
		String previousValue = getValUsingXpathInJavaScriptExecutor(element("inp_searchField", "EMAIL_TEMPLATE_FROM"))
				.trim();
		System.out.println("###### previousValue" + previousValue);
		element("inp_searchField", "EMAIL_TEMPLATE_FROM").clear();
		element("inp_searchField", "EMAIL_TEMPLATE_FROM").sendKeys(newEmail);
		logMessage("Step: " + newEmail + " is entered \n");
		return previousValue;

	}

	public void verifyChangesAreMadeInCurrentTemplateSection(String expectedValue) {
		isElementDisplayed("inp_searchField", "EMAIL_TEMPLATE_FROM");
		String actualValue = getValUsingXpathInJavaScriptExecutor(element("inp_searchField", "EMAIL_TEMPLATE_FROM"))
				.trim();
		// System.out.println("###### previousValue" + previousValue);
		Assert.assertEquals(actualValue, expectedValue,
				"Assert failed: Expected value is " + expectedValue + " but found " + actualValue + "\n");
		logMessage("ASSERT PASSED: 'EMAIL_TEMPLATE_FROM' is updated as " + expectedValue + "\n");

	}

	private void selectEmailTemplateToSearchEmail(String searchCriteria, String searchType) {
		isElementDisplayed("select_email_template", searchType);
		selectProvidedTextFromDropDown(element("select_email_template", searchType), searchCriteria);
		logMessage("Step : Email template " + searchType + " is selected as " + searchCriteria);
	}

	public void enterEmailSearchCriteriaFields(String emailTemplateName, String emailStatus) {
		selectEmailTemplateToSearchEmail(emailTemplateName, "EMAIL_SEARCH_TEMPLATEID");
		selectEmailTemplateToSearchEmail(emailStatus, "EMAIL_SEARCH_STATUS");
	}
	

	public void clickOnSelectRoleDropDownAndSelectARole(String role) {
		isElementDisplayed("drpdwn_selectRole");
		selectProvidedTextFromDropDown(element("drpdwn_selectRole"),role);
		
	}

	public void clickOnCopyRoleDropDownAndSelectARole(String role) {
		isElementDisplayed("drpdwn_copyRole");
		selectProvidedTextFromDropDown(element("drpdwn_copyRole"),role);
		
	}

	public void clickOnGoButton() {
		isElementDisplayed("btn_go");
		click(element("btn_go"));
		hardWaitForChromeBrowser(5);
		handleAlert();
		
	}

	public void enterSearchTermForAbstractSearch(String searchTerm) {
		isElementDisplayed("txt_searchField");
		element("txt_searchField").sendKeys(searchTerm);
		
	}

	public void clickOnTitleOfSearchTerm(String value) {
		wait.hardWait(3);
		for (WebElement ele : elements("list_searchTitle", value)) {
			click(ele);
			break;
		}		
	}
	
	public void clickOnTitleOfSearchTermWithDraftStatus(String value) {
		for (WebElement ele : elements("list_titleWithDraftStatus", value)) {
			click(ele);
			break;
		}
		}

	public void clickOnEditTitleButton() {
		switchToWindowHavingIndex(1);
		isElementDisplayed("btn_editBody");
		click(element("btn_editBody"));	
	}

	public void enterTitleDetails(String title) {
		switchToDefaultContent();
		switchToFrame(element("iframe_title"));
		isElementDisplayed("txt_editTitle");
		element("txt_editTitle").clear();
		element("txt_editTitle").sendKeys(title);
		switchToDefaultContent();
		
	}

	public void clickOnFinishButton() {
		isElementDisplayed("lnk_ReviewSubmit");
		click(element("lnk_ReviewSubmit"));	
		isElementDisplayed("btn_finish");
		click(element("btn_finish"));	
		switchWindow();
	}

	public void verifyFinishNotDisplay() {
		switchToWindowHavingIndex(1);
		Assert.assertFalse(checkIfElementIsThere("btn_finish"));
		closeSwitchedWindow(1);
		switchToWindowHavingIndex(0);
	}
	
	public String getAbstractID() {
		String abstractID = null;
		wait.hardWait(3);
		for (WebElement ele : elements("list_abstractID")) {
			abstractID= ele.getText();
			break;
		}	
		return abstractID;
	}
	
	public String getAbstractIDOfUnwithdraw() {
		String abstractID = null;
		for (WebElement ele : elements("list_abstractIDUnwithdraw")) {
			abstractID= ele.getText();
			break;
		}	
		System.out.println(abstractID);
		return abstractID;
	}


	public void clickOnWithdrawLink() {
		for (WebElement ele : elements("lnk_withdraw")) {
			click(ele);
			break;
		}	
		handleAlert();
	}
	
	public void clickOnUnwithdrawLink() {
		for (WebElement ele : elements("lnk_unwithdraw")) {
			click(ele);
			break;
		}	
		handleAlert();		
	}

	public void verifyStatusOnAbstractInSearchTableAfterWithdraw() {
		isElementDisplayed("lnk_unwithdraw");	
	}

	public void verifyStatusOfAbstractOnSubmissionTabAfterWithdraw(String abstractID) {
		isElementDisplayed("txt_abstractID",abstractID);
		
	}

	public void verifyStatusOnAbstractInSearchTableAfterUnwithdraw() {
		isElementDisplayed("lnk_withdraw");		
	}

	public void verifyStatusOfAbstractOnSubmissionTabAfterUnwithdraw(String abstractID) {
		isElementDisplayed("txt_abstractIDUnwithdraw",abstractID);
	}

	public void clickOnStartANewDataExportLink() {
		isElementDisplayed("img_startANewDataExport");
		click(element("img_startANewDataExport"));
	}

	public void createANewDataExport(String exportName) {
		switchToWindowHavingIndex(1);
		isElementDisplayed("txt_exportName");
		element("txt_exportName").sendKeys(exportName);
		isElementDisplayed("btn_exportGo");
		click(element("btn_exportGo"));
		handleAlert();
		switchWindow();
	}

	public void verifyExportCreated(String value) {
		isElementDisplayed("list_exportName",value);
		
	}

	public void clickOnDataExportLinkFromLeftPanel(String value) {
		wait.hardWait(3);
		isElementDisplayed("lnk_dataExport",value);
		click(element("lnk_dataExport",value));
		wait.hardWait(3);
		//waitForProcessBarToDisappear();
	}

	public void verifyTitleIsEdited(String title) {
		isElementDisplayed("lnk_abstractTitle",title);
	}

	public String getFirstNameOfUser() {
		isElementDisplayed("lnk_username");
		String displayName=element("lnk_username").getText();
		String[] splited = displayName.split("\\s+");
		return splited[0];
	}


}
