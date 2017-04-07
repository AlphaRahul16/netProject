package com.qait.MAPS.keywords;

import java.util.Random;

import org.apache.tools.ant.taskdefs.condition.IsLastModified;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.thoughtworks.selenium.webdriven.commands.IsElementPresent;
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
			logMessage("ASSERT PASSED : verified user role " + role[i] + " is displayed on session page\n");
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
		waitForProcessBarToDisappear();
		logMessage("Step : " + buttonName + " button is clicked on left navigation panel\n");
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

	public void verifyTheResultOfFilter(String title, String expValue) {
		waitForLoaderToDisappear();
		isElementDisplayed("txt_programTableData", title);
		String actualValue = elements("txt_programTableData", title).get(1).getText();
		System.out.println("***********actualValue:::::" + actualValue);
		Assert.assertEquals(actualValue, expValue,
				"ASSERT FAILED: Expected " + title + " is " + expValue + " but found " + actualValue + " \n");
		logMessage("STEP:" + actualValue + " is exist as " + title + " \n");
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

	public void verifyRefreshButtonAtBottom() {
		Assert.assertTrue(isElementDisplayed("btn_refresh"), " refresh button not available on the page\n");
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

	// public void selectValueForSessionDetailType(String value) {
	// //isElementDisplayed("")
	// isElementDisplayed("inp_sessionType", "session_detail_type_id");
	// element("inp_sessionType", "session_detail_type_id").sendKeys(value);
	// wait.hardWait(5);
	// logMessage("STEP: Session Detailed Type is selected as " + value + "\n");
	// }

	public void verifyApplicationShouldAddSessionDetailType(String testType, String colorCode, String sessionType) {
		verifyDataOfTypePage(testType, colorCode, "Color Code");
		verifyDataOfTypePage(testType, sessionType, "Session Type");
	}

	public void clickSubHeadingLeftNavigationPanel(String headingName) {
		waitForLoaderToDisappear();
		isElementDisplayed("lnk_sessionTypes", headingName);
		clickUsingXpathInJavaScriptExecutor(element("lnk_sessionTypes", headingName));
		logMessage("Step : " + headingName + " sub-heading is clicked\n");
	}

	public void verifySectionsOnRoomAvailabilityPage(String sectionName, int index) {
		isElementDisplayed("heading_sectionName", sectionName, String.valueOf(index));
		logMessage("ASSERT PASSED: " + sectionName + " section is present on Rooms Availability page\n");
	}

	public void clickOnSaveAndEditButton(String btnName, int index) {
		isElementDisplayed("btn_saveAndEdit", btnName, String.valueOf(index));
		click(element("btn_saveAndEdit", btnName, String.valueOf(index)));
		logMessage("Step : Clicked on " + btnName + " button\n");
	}

	public void enterNameOnSaveGridConfiguration(String fieldName, String roomName) {
		isElementDisplayed("inp_roomName", fieldName);
		element("inp_roomName", fieldName).clear();
		element("inp_roomName", fieldName).sendKeys(roomName);
		logMessage("Step : " + fieldName + " is entered as " + roomName + "\n");
	}

	public void clickCheckboxOnSaveGridConfiguration(String fieldname) {
		isElementDisplayed("chkbox_room", fieldname);
		click(element("chkbox_room", fieldname));
		logMessage("Step : Clicked on " + fieldname + " checkbox\n");
	}

	public void selectRoleOnSaveGridConfiguration(String role) {
		isElementDisplayed("select_role", role);
		click(element("select_role", role));
		logMessage("Step : Role is selected as " + role + "\n");
	}

	public void verifyCreatedFilterIsByDefaultSelected(String filterName) {
		((JavascriptExecutor) driver)
				.executeScript("$x('(//div[contains(@class,\'x-form-field-wrap\') and @role=\"combobox\"])[2]')");
	}

	public void verifyFieldsOnRoomAvailablityPage(String fieldName, int index) {
		isElementDisplayed("btn_saveAndEdit", fieldName, String.valueOf(index));
		logMessage("ASSERT PASSED: " + fieldName + " field is present on Rooms Availability page\n");
	}

	public void verifyFilterDropdwonOnRoomAvailabalityPage(int index) {
		isElementDisplayed("inp_saveGridFilters", String.valueOf(index));
		logMessage("ASSERT PASSED: Filter dropdwon is displayed on Room Availability page\n");
	}

	public void clickOnArrowButton(String label) {
		wait.hardWait(5);
		isElementDisplayed("btn_navPanel", label);
		hover(element("btn_navPanel", label));
		isElementDisplayed("btn_arrow", label);
		clickUsingXpathInJavaScriptExecutor(element("btn_arrow", label));
		// click(element("btn_arrow",label));
		logMessage("Step : Clicked on arrow button next to " + label + "\n");
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
		isElementDisplayed("txt_instruction", "");
	}

	public void enterFilterText(String drpdwnValue, String filterText) {
		isElementDisplayed("lnk_filters", drpdwnValue);
		hover(element("lnk_filters", drpdwnValue));
		isElementDisplayed("inp_filtertext");
		element("inp_filtertext").clear();
		click(element("inp_filtertext"));
		element("inp_filtertext").sendKeys(filterText);
		logMessage("Step : Filter text is entered as " + filterText + "\n");
	}

	public void enterProgramName(String roomValue, int index) {
		isElementDisplayed("inp_saveGridFilters", String.valueOf(index));
		click(element("inp_saveGridFilters", String.valueOf(index)));
		element("inp_saveGridFilters", String.valueOf(index)).sendKeys(roomValue);
		logMessage("Step : Room availability value is entered as " + roomValue);
	}

	public void verifyFilterResults(String filterResult, int index, int columnIndex) {
		wait.hardWait(4);
		isElementDisplayed("txt_tableData", String.valueOf(index), String.valueOf(columnIndex));
		for (WebElement ele : elements("txt_tableData", String.valueOf(index), String.valueOf(columnIndex))) {
			Assert.assertEquals(ele.getText().trim(), filterResult,
					"ASSERT FAILED: Filter results does not contains " + filterResult + "\n");
		}
		logMessage("ASSERT PASSED: Filter results contains " + filterResult + "\n");
	}

	public void clickOnDropDownImage(int index) {
		isElementDisplayed("img_dropDown", String.valueOf(index));
		click(element("img_dropDown", String.valueOf(index)));
		logMessage("Step : Clicked on dropdown image\n");
	}

	public void clickOnButtonUnderSessioning(String btnName) {
		isElementDisplayed("btn_Types", btnName);
		click(element("btn_Types", btnName));
		logMessage("Step : Clicked on " + btnName + "\n");
	}

	public void verifyFilterIsByDefaultSelected(String filterName, int index) {
		wait.hardWait(3);
		int count = 1;
		boolean flag = false;
		while (count <= 5) {
			if ((getValUsingXpathInJavaScriptExecutor(element("inp_saveGridFilters", String.valueOf(index)))
					.equalsIgnoreCase(filterName))) {
				flag = true;
				break;
			} else {
				count++;
			}
		}
		System.out.println("-----count:" + count);
		Assert.assertTrue(flag, "ASSERT FAILED: Filter value is not " + filterName + " by default\n");
		logMessage("ASSERT PASSED: Filter value is " + filterName + " by default\n");
	}

	public void clickOnButtonUnderSessionModule(String text) {
		isElementDisplayed("btn_close", text);
		click(elements("btn_close", text).get(0));
		logMessage("STEP: clicked on '" + text + "' button \n");
	}

	public void isPrintSelectedButtonDisplayed(String buttonname) {
		isElementDisplayed("btn_Types");
		logMessage("ASSERT PASSED : Print Selected Button is displayed on Session page\n");

	}

	public void enterValuesInAddNewHost(String fname, String lname, String email, String institution) {
		enterValueInInputtextField("session_host_first_name", fname);
		enterValueInInputtextField("session_host_last_name", lname);
		enterValueInInputtextField("session_host_institution", institution);
		enterValueInInputtextField("session_host_email", email);

	}

	private void enterValueInInputtextField(String text, String value) {
		isElementDisplayed("inp_addHost", text);
		element("inp_addHost", text).sendKeys(value);
		logMessage("STEP: " + value + " is entered as " + text + "\n");
	}

	public void enterValuesInCreateSymposium(String symposiumTitle, String symposiumType) {
		enterValuesForProgram("session_name", symposiumTitle);
		selectValueForSymposium("session_type", symposiumType);
	}

	public String selectaRandomRecordFromTheList() {
		isElementDisplayed("chkbox_records");
		int randomnumber = generateRandomNumberWithInRange(0, (elements("chkbox_records").size()) - 1);
		click(elements("chkbox_records").get(randomnumber));
		logMessage("Step : a random record is selected from the list with position " + randomnumber);
		return element("btn_recordsname", toString().valueOf(randomnumber)).getText();
	}
	public void selectaRecordFromTheList(int number) {
		isElementDisplayed("chkbox_records");
		click(elements("chkbox_records").get(number));
		logMessage("Step : a random record is selected from the list with position " + number);
	}

	private void selectValueForSymposium(String dropdownName, String symposiumType) {
		isElementDisplayed("dropdown_programField", dropdownName);
		click(element("dropdown_programField", dropdownName));
		logMessage("STEP: " + dropdownName + " is clicked \n");

		isElementDisplayed("listItem_SymposiumType", symposiumType);
		click(element("listItem_SymposiumType", symposiumType));
		logMessage("STEP: " + symposiumType + " is selected \n");
	}

	public void addHostforSymposium() {
		waitForLoadingImageToDisappear();
		isElementDisplayed("txt_hostDetails", "session_host_last_name");

		WebElement Sourcelocator = elements("txt_hostDetails", "session_host_last_name").get(2);
		String hostlastname = elements("txt_hostDetails", "session_host_last_name").get(2).getText();
		isElementDisplayed("txt_dropField");
		WebElement Destinationlocator = element("txt_dropField");
		dragAndDrop(Sourcelocator, Destinationlocator);
		wait.hardWait(2);
		waitForLoadingImageToDisappear();
		// waitForLoaderToDisappear();
		logMessage("STEP: '" + hostlastname + " is selected as Host \n");
	}

	public void addRoleForHost(String hostRole) {
		isElementDisplayed("txt_hostDetails", "session_host_role");
		System.out.println("size##########" + elements("txt_hostDetails", "session_host_role").size());
		click(elements("txt_hostDetails", "session_host_role").get(1));
		isElementDisplayed("listItem_SymposiumType", hostRole);
		click(element("listItem_SymposiumType", hostRole));
		logMessage("STEP: " + hostRole + " is selected \n");

	}

	public void searchAbstract(String searchBy, String value) {
		isElementDisplayed("inp_programField", searchBy);
		element("inp_programField", searchBy).sendKeys(value);
		logMessage("STEP: Abstract is searched by " + searchBy + " with value " + value + "\n");
	}

	public void addAbstractsInCurrentlyAssignedAbstractsSection() {
		isElementDisplayed("btn_navPanel", "Search Results");
		wait.hardWait(5);
		waitForLoadingImageToDisappear();
		isElementDisplayed("txt_hostDetails", "title");

		System.out.println("size***************" + elements("txt_hostDetails", "title").size());

		WebElement Sourcelocator = elements("txt_hostDetails", "title").get(3);
		isElementDisplayed("txt_dropField");
		WebElement Destinationlocator = element("txt_dropField");
		dragAndDrop(Sourcelocator, Destinationlocator);
		wait.hardWait(2);
		waitForLoadingImageToDisappear();
	}

	public void clickOnButtonByIndexing(String text) {
		isElementDisplayed("btn_remove", text, "2");
		click(element("btn_remove", text, "2"));
		logMessage("STEP: '" + text + "' button is clicked \n");
	}

	public void selectAbstract(String text) {
		isElementDisplayed("txt_hostDetails", text);
		click(elements("txt_hostDetails", text).get(1));
		logMessage("STEP: An Abstract is selected \n");
	}

	public void clickOnSaveButton(String btnName) {
		isElementDisplayed("btn_Types", btnName);
		click(element("btn_Types", btnName));
		logMessage("Step : Clicked on " + btnName + "\n");
	}
	public void verifyDataIsDeleted(String title, String expValue) {
		waitForLoaderToDisappear();
		Assert.assertFalse(checkIfElementIsThere("txt_programTableData", title));
		logMessage("STEP:" + expValue + " is deleted \n");
	}

}
