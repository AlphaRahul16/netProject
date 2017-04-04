package com.qait.MAPS.keywords;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.DateUtil;

public class Session_Page_Actions extends ASCSocietyGenericPage {

	WebDriver driver;
	static String pagename = "Session_Page";

	public Session_Page_Actions(WebDriver driver) {
		super(driver, pagename);
	}

	public void verifyApplicationDisplaysRadioButtonOnClickingSessionTab(String[] role) {
		int i = 0;
		isElementDisplayed("txt_userrole");
		for (WebElement ele : elements("txt_userrole")) {
			Assert.assertTrue(ele.getText().equals(role[i]),
					"user role " + role + " is not displayed on application\n");
			logMessage("ASSERT PASSED : verified user role " + role + " is displayed on session page\n");
			i++;
		}
	}

	public void clickNamedRadioButtonOnRoleSelectionPage(String role) {
		isElementDisplayed("rdbtn_userrole", role);
		element("rdbtn_userrole", role).click();
		logMessage("Step : " + role + " button is selected on Multiple role selection page\n");
		wait.waitForPageToLoadCompletely();
	}

	public void clickButtonToContinueToNextPage(String buttonName) {
		isElementDisplayed("lnk_selButton", buttonName);
		element("lnk_selButton", buttonName).click();
		logMessage("Step : " + buttonName + " button is clicked\n");
	}

	public void clickButtononLeftNavigationPanel(String buttonName) {
		isElementDisplayed("btn_navPanel", buttonName);
		wait.hardWait(2);
		element("btn_navPanel", buttonName).click();
		wait.hardWait(2);
		logMessage("Step : " + buttonName + " button is clicked on left navigation panel\n");
	}

	public void verifyApplicationShouldDisplayOptionsOnAbstractPage(String options) {

	}

	public void verifyTitleForRoles(String title) {
		// isElementDisplayed("btn_navPanel",title)
		// logMessage("Step:");
	}

	public void verifyLeftPanelOptionsOnSessionAdminPage(String[] leftPanelOptions) {
		// int i = 0;
		for (String text : leftPanelOptions) {
			Assert.assertTrue(isElementDisplayed("btn_navPanel", text),
					" option " + text + " is not displayed on application\n");
			logMessage("ASSERT PASSED : verified options " + text + " is displayed on session admin page\n");
			// i++;
		}
	}

	public String getValueFromProgramsTable() {
		isElementDisplayed("txt_programTableData", "program_id");
		String programID = elements("txt_programTableData", "program_id").get(1).getText();
		logMessage("STEP: Program id is fetched as " + programID + " from program table \n");
		return programID;
	}

	public void verifyTheResultOfFilter(String expProgramID) {
		isElementDisplayed("txt_programTableData", "program_id");
		String actualProgramId = elements("txt_programTableData", "program_id").get(1).getText();
		Assert.assertEquals(elements("txt_programTableData", "program_id").get(1).getText(), expProgramID,
				"ASSERT FAILED: Expected Program id is " + expProgramID + " but found " + actualProgramId + " \n");
		logMessage("STEP: Program id is fetched as " + actualProgramId + " from program table \n");
	}

	public void enterValuesInCreateProgramPage(String ProgramTitle, String interval) {
		enterValuesForProgram("program_title", ProgramTitle);
		enterValuesForProgram("start_date", DateUtil.getCurrentdateInStringWithGivenFormate("EEE M/dd/yyyy"));
		enterValuesForProgram("end_date", DateUtil.getCurrentdateInStringWithGivenFormate("EEE M/dd/yyyy"));
		selectValuesForProgram("daily_start_time", 3);
		selectValuesForProgram("daily_end_time", 5);
		enterValuesForProgram("interval", interval);
	}

	private void selectValuesForProgram(String field, int index) {
		isElementDisplayed("dropdown_programField", field);
		click(element("dropdown_programField", field));
		logMessage("STEP: " + field + " is clicked \n");

		isElementDisplayed("listItem_programField");
		String value = elements("listItem_programField").get(index).getText();
		System.out.println("" + value);
		click(elements("listItem_programField").get(index));
		logMessage("STEP: " + value + " is selected \n");
	}

	
	public void verifyRefreshButtonAtBottom()
	{
		Assert.assertTrue(isElementDisplayed("btn_refresh")," refresh button not available on the page\n");
		logMessage("ASSERT PASSED : refresh button is displayed at page bottom\n");
	}


	private void enterValuesForProgram(String field, String value) {
		isElementDisplayed("inp_programField", field);
		element("inp_programField", field).sendKeys(value);
		logMessage("STEP: Enterd " + value + " in " + field + "field \n");
	}

	public void verifyPopupMessage(String msg) {
		waitForLoaderToDisappear();
		Assert.assertTrue(isElementDisplayed("btn_navPanel", msg));
		logMessage("ASSERT PASSED: '" + msg + "' is displayed \n");
	}

	public void verifyButtonsOnTypes(String[] options) {
		for (String text : options) {
			Assert.assertTrue(isElementDisplayed("btn_Types", text),
					" option " + text + " is not displayed on application\n");
			logMessage("ASSERT PASSED : verified options " + text + " is displayed on session admin page\n");
		}
	}

	public void verifyTextUnderMeetingSetup(String text) {
		Assert.assertTrue(isElementDisplayed("txt_instruction", text));
		logMessage("ASSERT PASSED: " + text + " is displayed \n");

	}

	public void entervaluesinAddNewTypes(String typeName, String colorCode, String sessionType) {
		enterValuesForProgram("session_type_name", typeName);
		enterValuesForProgram("session_type_description", typeName);
		enterValuesForProgram("session_type_color2", colorCode);
		selectValueForSessionType("Session Type", sessionType);

	}

	private void selectValueForSessionType(String label, String value) {
		isElementDisplayed("radioBtn_sessionType", value);
		element("radioBtn_sessionType", value).click();
		logMessage("STEP: " + value + " is selected as " + label + " \n");
	}

//	public void selectValueForSessionDetailType(String value) {
//		//isElementDisplayed("")
//		isElementDisplayed("inp_sessionType", "session_detail_type_id");
//		element("inp_sessionType", "session_detail_type_id").sendKeys(value);
//		wait.hardWait(5);
//		logMessage("STEP: Session Detailed Type is selected as " + value + "\n");
//	}

	public void verifyApplicationShouldAddSessionDetailType(String testType, String colorCode, String sessionType) {
		verifyDataOfTypePage(testType, colorCode, "Color Code");
		verifyDataOfTypePage(testType, sessionType, "Session Type");
	}

	private void verifyDataOfTypePage(String testType, String colorCode, String text) {
		Assert.assertTrue(isElementDisplayed("tabledata_type", testType, colorCode));
		logMessage("ASSERT PASSED: '" + testType + "' is displayed with " + text + " " + colorCode);
	}

	public void verifyListOfProgramAreas(String text) {
		Assert.assertTrue(isElementDisplayed("list_table"));
		logMessage("ASSERT PASSED: List of " + text + " is verified \n");

	}

	public void verifyProgramIsaddedInTable() {
		// TODO Auto-generated method stub
		isElementDisplayed("txt_instruction","");
	}

}
