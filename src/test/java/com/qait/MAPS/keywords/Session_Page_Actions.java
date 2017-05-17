package com.qait.MAPS.keywords;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.DataProvider;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;

public class Session_Page_Actions extends ASCSocietyGenericPage {

	WebDriver driver;
	private String downloadedFilePath;
	static String pagename = "Session_Page";
	Map<String, String> editableColumnsMap = new HashMap<>();

	public Session_Page_Actions(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
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
		wait.hardWait(6);
		isElementDisplayed("btn_navPanel", buttonName);
		// clickUsingXpathInJavaScriptExecutor(element("btn_navPanel",
		// buttonName));
		element("btn_navPanel", buttonName).click();
		wait.hardWait(3);
		waitForProcessBarToDisappear();
		logMessage("Step : " + buttonName + " button is clicked on left navigation panel\n");
	}

	public void verifyTitleForRoles(String title) {
		wait.hardWait(2);
		isElementDisplayed("btn_navPanel", title);
		logMessage("Step: " + title + " is verified as " + title);
	}

	public void verifyTitleForRoles(String title, String fielname) {
		wait.hardWait(2);
		isElementDisplayed("btn_navPanel", title);
		logMessage("Step: " + fielname + " is verified as " + title);
	}

	public void verifyLeftPanelOptionsOnSessionAdminPage(String[] leftPanelOptions) {
		for (String text : leftPanelOptions) {
			Assert.assertTrue(isElementDisplayed("btn_navPanel", text),
					" option " + text + " is not displayed on application\n");
			logMessage("ASSERT PASSED : verified options " + text + " is displayed on page\n");
		}
	}

	public String getValueFromProgramsTable() {
		isElementDisplayed("txt_programTableData", "program_id");
		String programID = elements("txt_programTableData", "program_id").get(1).getText();
		logMessage("STEP: Program id is fetched as " + programID + " from program table \n");
		return programID;
	}

	public void enterValuesInCreateProgramPage(String ProgramTitle, String interval) {
		enterValuesForProgram("program_title", ProgramTitle);
		enterValuesForProgram("start_date", DateUtil.getCurrentdateInStringWithGivenFormate("EEE M/dd/yyyy"));
		enterValuesForProgram("end_date", DateUtil.getCurrentdateInStringWithGivenFormate("EEE M/dd/yyyy"));
		selectValuesForProgram("daily_start_time", 3);
		selectValuesForProgram("daily_end_time", 5);
		enterValuesForProgram("interval", interval);
	}

	public void selectValuesForProgram(String field, int index) {
		isElementDisplayed("dropdown_programField", field);
		click(element("dropdown_programField", field));
		logMessage("STEP: " + field + " is clicked \n");
		selectValueByIndexing(index);
	}

	private void selectValueByIndexing(int index) {
		isElementDisplayed("listItem_programField");
		String value = elements("listItem_programField").get(index).getText();
		System.out.println("" + value);
		click(elements("listItem_programField").get(index));
		logMessage("STEP: " + value + " is selected \n");
	}

	public void verifyRefreshButtonAtBottom() {
		wait.hardWait(2);
		Assert.assertTrue(isElementDisplayed("btn_refresh"), " refresh button not available on the page\n");
		logMessage("ASSERT PASSED : refresh button is displayed at page bottom\n");
	}

	public void enterValuesForProgram(String field, String value) {
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

	public void selectValueForSessionType(String label, String value) {
		isElementDisplayed("radioBtn_sessionType", value);
		element("radioBtn_sessionType", value).click();
		logMessage("STEP: " + value + " is selected as " + label + " \n");
	}

	// public void selectValueForSessionDetailType(String value) {
	//
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

	public void selectRoleOnSaveGridConfiguration(String label, String role) {
		isElementDisplayed("select_role", role);
		click(element("select_role", role));
		logMessage("Step : '" + label + "' is selected as " + role + "\n");
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

	public void enterFilterText(String drpdwnValue, String filterText) {
		hoverOverColumnHeader(drpdwnValue);
		isElementDisplayed("inp_filtertext");
		wait.hardWait(2);
		element("inp_filtertext").clear();
		click(element("inp_filtertext"));
		element("inp_filtertext").sendKeys(filterText);
		logMessage("Step : Filter text is entered as " + filterText + "\n");
	}

	public void hoverOverColumnHeader(String drpdwnValue) {
		isElementDisplayed("lnk_filters", drpdwnValue);
		hover(element("lnk_filters", drpdwnValue));
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
			Assert.assertTrue(
					org.apache.commons.lang3.StringUtils.containsIgnoreCase(ele.getText().trim(), filterResult),
					"ASSERT FAILED: Filter results " + ele.getText().trim() + " does not contains " + filterResult
							+ "\n");
		}
		logMessage("ASSERT PASSED: Filter results contains " + filterResult + "\n");
	}

	public void clickOnDropDownImage(int index) {
		isElementDisplayed("img_dropDown", String.valueOf(index));
		click(element("img_dropDown", String.valueOf(index)));
		logMessage("Step : Clicked on dropdown image\n");
	}

	public void clickOnButtonUnderSessioning(String btnName) {
		wait.hardWait(2);
		wait.waitForElementToBeClickable(element("btn_Types", btnName));
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
				wait.hardWait(1);
			}
		}
		Assert.assertTrue(flag, "ASSERT FAILED: Filter value is not " + filterName + " by default\n");
		logMessage("ASSERT PASSED: Filter value is " + filterName + " by default\n");
	}

	// public void clickOnButtonUnderSessionModule(String text) {
	// isElementDisplayed("btn_close", text);
	// click(elements("btn_close", text).get(0));
	// logMessage("STEP: clicked on '" + text + "' button \n");
	// }

	public void isPrintSelectedButtonDisplayed(String buttonName) {
		wait.hardWait(2);
		isElementDisplayed("btn_Types", buttonName);
		logMessage("ASSERT PASSED : " + buttonName + " Button is displayed on Session page\n");
	}

	public void enterValuesInAddNewHost(String fname, String lname, String email, String institution) {
		enterValueInInputtextField("session_host_first_name", fname);
		enterValueInInputtextField("session_host_last_name", lname);
		enterValueInInputtextField("session_host_institution", institution);
		enterValueInInputtextField("session_host_email", email);

	}

	public void enterValueInInputtextField(String text, String value) {
		isElementDisplayed("inp_addHost", text);
		element("inp_addHost", text).sendKeys(value);
		logMessage("STEP: " + value + " is entered as " + text + "\n");
	}

	public void verifyInputTextField(String fieldName) {
		wait.hardWait(1);
		isElementDisplayed("inp_addHost", fieldName);
		logMessage("ASSERT PASSED: " + fieldName + " is displayed on page\n");
	}

	public void verifyRoomIsAdded(String roomName, String venueName) {
		verifyAddedDetails("name", roomName);
		verifyAddedDetails("venue", venueName);
	}

	public void verifyAddedDetails(String classLabel, String expValue) {
		wait.hardWait(2);
		isElementDisplayed("txt_tableResult", classLabel, expValue);
		logMessage("ASSERT PASSED: " + expValue + " is added in table\n");
	}

	public void verifyAddedDetailsForHost(String classLabel, String expValue) {
		wait.hardWait(2);
		String value = elements("txt_tableResult", classLabel, expValue).get(1).getText();
		System.out.println("*****value" + value);
		logMessage("ASSERT PASSED: " + expValue + " is added in table\n");
	}

	public void selectOptionsUnderColumnHeaders(String option) {
		isElementDisplayed("lnk_filters", option);
		click(element("lnk_filters", option));
		logMessage("Step : Clicked on " + option + " option under column headers\n");
	}

	public void selectColumnForSorting(String columnName) {
		isElementDisplayed("lst_column", columnName);
		click(element("lst_column", columnName));
		logMessage("Step : " + columnName + " column is selected\n");
	}

	public void clickOnAddButton(String btnName) {
		isElementDisplayed("btn_add", btnName);
		click(element("btn_add", btnName));
		logMessage("Step : " + btnName + " button is clicked\n");
	}

	public List<String> getTableData(String index, String columnName) {
		List<String> tableData = new ArrayList<>();
		waitForLoaderToDisappear();

		wait.hardWait(4);
		isElementDisplayed("txt_tableData", index, columnName);
		for (WebElement ele : elements("txt_tableData", index, columnName)) {
			tableData.add(ele.getText().trim());
		}
		return tableData;
	}

	public List<String> getColumnSpecificTableData(String columnName) {
		wait.hardWait(5);
		List<String> tableData = new ArrayList<>();
		isElementDisplayed("table_columnDate", columnName);
		for (int i = 1; i < elements("table_columnDate", columnName).size(); i++) {
			tableData.add(elements("table_columnDate", columnName).get(i).getText().trim());
		}
		return tableData;
	}

	public void verifyDataIsSorted(List<String> dataBeforeSorting, List<String> dataAfterSorting) {
		int index = 0;
		dataBeforeSorting = convertDataToLowerCase(dataBeforeSorting);
		dataAfterSorting = convertDataToLowerCase(dataAfterSorting);
		Collections.sort(dataBeforeSorting);
		for (String beforeSorting : dataBeforeSorting) {
			System.out.println("----data before sorting:" + beforeSorting);
			System.out.println("----data after sorting:" + dataAfterSorting.get(index));
			Assert.assertTrue(beforeSorting.equals(dataAfterSorting.get(index)),
					"ASSERT FAILED: Data is not sorted properly\n");
			logMessage("ASSERT PASSED: Data is sorted properly\n");
			index++;
		}
	}

	public List<String> convertDataToLowerCase(List<String> arrayList) {
		ListIterator<String> iterator = arrayList.listIterator();
		while (iterator.hasNext()) {
			iterator.set(iterator.next().toLowerCase());
		}
		return arrayList;
	}

	public void selectLastRecordFromList() {
		isElementDisplayed("chkbox_records", "last()");
		click(element("chkbox_records", "last()"));
		logMessage("Step : Last record is clicked from list\n");
	}

	public void clickParticularRecordFromList(String recordName, String index) {
		wait.hardWait(4);
		isElementDisplayed("chkbox_column", recordName, index);
		click(element("chkbox_column", recordName, index));
		logMessage("Step : " + recordName + " record is selected from list\n");
	}

	public void enterValuesInCreateSymposium(String symposiumTitle, String symposiumType) {
		enterValuesForProgram("session_name", symposiumTitle);
		selectValueForSymposium("session_type", symposiumType);
	}

	// public String selectaRandomRecordFromTheList() {
	// isElementDisplayed("chkbox_records");
	// int randomnumber = generateRandomNumberWithInRange(0,
	// (elements("chkbox_records").size()) - 1);
	// click(elements("chkbox_records").get(randomnumber));
	// logMessage("Step : a random record is selected from the list with
	// position " + randomnumber);
	// return element("btn_recordsname",
	// toString().valueOf(randomnumber)).getText();
	// }

	public String getRandomRecordFromTable(String columnIndex) {
		wait.hardWait(2);
		isElementDisplayed("txt_totalRecords");
		int randomnumber = generateRandomNumberWithInRange(1, (elements("txt_totalRecords").size()) - 1);
		isElementDisplayed("btn_recordsname", String.valueOf(randomnumber), columnIndex);
		System.out.println("-----random record:"
				+ element("btn_recordsname", String.valueOf(randomnumber), columnIndex).getText().trim());
		return element("btn_recordsname", String.valueOf(randomnumber), columnIndex).getText().trim();
	}

	// public String selectaRandomRecordFromTheList(String index) {
	// isElementDisplayed("chkbox_records");
	// click(elements("chkbox_records").get(index));
	// logMessage("Step : a random record is selected from the list with
	// position " + randomnumber);
	// return element("btn_recordsname", index).getText();
	// }

	public String selectaRecordFromTheList(int number, String columnValue) {
		isElementDisplayed("chkbox_records", String.valueOf(number));
		click(element("chkbox_records", String.valueOf(number)));
		logMessage("Step : Record is selected from the list at position " + number);
		return element("btn_recordsname", String.valueOf(number), columnValue).getText().trim();
	}

	public void selectValueForSymposium(String dropdownName, String symposiumType) {
		isElementDisplayed("dropdown_programField", dropdownName);
		click(element("dropdown_programField", dropdownName));
		logMessage("STEP: " + dropdownName + " is clicked \n");
		selectValueFromDropDown(symposiumType);
	}

	public String addHostforSymposium(String label) {
		waitForLoadingImageToDisappear("Loading...");
		isElementDisplayed("txt_hostDetails", label);
		elements("txt_hostDetails", label).get(1).click();
		WebElement Sourcelocator = elements("txt_hostDetails", label).get(1);
		String value = elements("txt_hostDetails", label).get(1).getText();
		isElementDisplayed("txt_dropField");
		WebElement Destinationlocator = elements("txt_dropField").get(1);
		dragAndDrop(Sourcelocator, Destinationlocator);
		wait.hardWait(2);
		waitForLoadingImageToDisappear("Loading...");
		// waitForLoaderToDisappear();
		logMessage("STEP: '" + value + " is selected as Host \n");
		return value;
	}

	public String getHostDetails(String label) {
		isElementDisplayed("txt_hostDetails", label);
		return elements("txt_hostDetails", label).get(1).getText();
	}

	// public String addHostforSymposium(String label) {
	// waitForLoadingImageToDisappear("Loading...");
	// isElementDisplayed("txt_linkEmail", label);
	// //elements("txt_controlId", label).get(0).click();
	// WebElement Sourcelocator = elements("txt_linkEmail", label).get(0);
	// String value = elements("txt_linkEmail", label).get(0).getText();
	// isElementDisplayed("txt_dropField");
	// WebElement Destinationlocator = element("txt_dropField");
	// dragAndDrop(Sourcelocator, Destinationlocator);
	// wait.hardWait(2);
	// waitForLoadingImageToDisappear("Loading...");
	// // waitForLoaderToDisappear();
	// logMessage("STEP: '" + value + " is selected as Host \n");
	// return value;
	// }
	public List<String> addHostforSymposium(String label, int numberOfHost) {
		List<String> abstractDetails = new ArrayList<>();
		waitForLoadingImageToDisappear("Loading...");
		isElementDisplayed("txt_hostDetails", label);
		for (int i = 1; i <= numberOfHost; i++) {
			WebElement Sourcelocator = elements("txt_hostDetails", label).get(1);
			String value = elements("txt_hostDetails", label).get(1).getText();
			abstractDetails.add(value);
			isElementDisplayed("txt_dropField");
			WebElement Destinationlocator = elements("txt_dropField").get(1);
			dragAndDrop(Sourcelocator, Destinationlocator);
			wait.hardWait(2);
			waitForLoadingImageToDisappear("Loading...");
			logMessage("STEP: '" + value + " is selected as Host \n");
		}
		return abstractDetails;
	}

	public void addRoleForHost(String hostRole) {
		isElementDisplayed("txt_hostDetails", "session_host_role");
		// System.out.println("size##########" + elements("txt_hostDetails",
		// "session_host_role").size());
		click(elements("txt_hostDetails", "session_host_role").get(1));
		selectValueFromDropDown(hostRole);
	}

	public void searchAbstract(String searchBy, String value) {
		isElementDisplayed("inp_programField", searchBy);
		element("inp_programField", searchBy).sendKeys(value);
		logMessage("STEP: Abstract is searched by " + searchBy + " with value " + value + "\n");
	}

	public void addAbstractsInCurrentlyAssignedAbstractsSection() {
		isElementDisplayed("btn_navPanel", "Search Results");
		wait.hardWait(5);
		waitForLoadingImageToDisappear("Loading...");
		isElementDisplayed("txt_hostDetails", "title");
		WebElement Sourcelocator = elements("txt_hostDetails", "title").get(2);
		isElementDisplayed("txt_dropField");
		WebElement Destinationlocator = element("txt_dropField");
		dragAndDrop(Sourcelocator, Destinationlocator);
		wait.hardWait(2);
		waitForLoadingImageToDisappear("Loading...");
	}

	public void clickOnButtonByIndexing(String text, String index) {
		isElementDisplayed("btn_remove", text, index);
		// clickUsingXpathInJavaScriptExecutor(element("btn_remove", text,
		// index));
		click(element("btn_remove", text, index));
		wait.hardWait(2);
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

	public void selectSessionTopicWhenAddingProgramArea(String topicname) {
		isElementDisplayed("btn_SessionTopic", topicname);
		click(element("btn_SessionTopic", topicname));
		logMessage("Step : Session name in program area is selected as " + topicname);
	}

	public void verifyAndAcceptProgramAreaAlertText(String alertText) {
		Assert.assertTrue(element("txt_alertAddOwner").getText().equals(alertText),
				"Alert box text does not match with expected text " + getAlertText());
		logMessage("ASSERT PASSED : Alert box is appered on clicking Add Owners button with text " + alertText);
		clickOnButtonUnderSessioning("Yes");
	}

	public void verifyCorrectSearchResultsAreDisplayed(String fieldname, String expected_value) {
		isElementDisplayed("txt_searchResults", fieldname);
		Assert.assertTrue(element("txt_searchResults", fieldname).getText().equals(expected_value),
				" Owner's" + fieldname + " is not displayed\n");
		logMessage("ASSERT PASSED : " + fieldname + " in search results is verified as " + expected_value);

	}

	public void selectAvailableSearchRecord(String index1, String index2) {
		isElementDisplayed("txt_tableData", index1, index2);
		click(element("txt_tableData", index1, index2));
		logMessage("Step : Search record is elected from the list");
	}

	public void setRoleForTheUser(String rolevalue) {
		isElementDisplayed("table_columnDate", "col-session_role_name");
		click(element("table_columnDate", "col-session_role_name"));
		isElementDisplayed("listItem_SymposiumType", rolevalue);
		click(element("listItem_SymposiumType", rolevalue));
		logMessage("Step : Role selected for owner as " + rolevalue);
		wait.hardWait(3);
		Assert.assertTrue(element("table_columnDate", "col-session_role_name").getText().trim().equals(rolevalue));
		logMessage("ASSERT PASSED : Selected role for owner is verifed " + rolevalue);
	}

	public void verifyProgramAreaIsAdded(String programname, String programtype, String color) {
		verifyAddedDetails("session_topic_name", programname);
		verifyAddedDetails("session_kind_name", programtype);
		verifyAddedDetails("session_topic_color", color);
	}

	public void selectCurrentDate() {
		isElementDisplayed("date_currentDate");
		click(element("date_currentDate"));
		logMessage("Step : Selected Current date from Calendar\n");
	}

	public void verifyRoomsAreFilteredAccordingToDate(List<String> filteredData, String criteria) {
		System.out.println("-----*****data:" + filteredData);
		for (String date : filteredData) {
			Date convertedDate = DateUtil.convertStringToDate(date, "E, d MMM yy");
			Date currentDate = DateUtil
					.convertStringToDate(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"), "MM/dd/yyyy");
			Assert.assertTrue(compareDates(convertedDate, currentDate, criteria),
					"ASSERT PASSED: Filtered Date " + date + " is not " + criteria + " current date\n");
			logMessage("ASSERT PASSED: Filtered Date " + date + " is " + criteria + " current date\n");
		}
	}

	public boolean compareDates(Date convertedDate, Date currentDate, String criteria) {
		boolean value = false;
		switch (criteria) {
		case "Before":
			value = convertedDate.before(currentDate);
			break;
		case "After":
			value = convertedDate.after(currentDate);
			break;
		case "On":
			value = convertedDate.equals(currentDate);
			break;
		default:
			logMessage("Step : Enter correct choice\n");
		}
		return value;
	}

	public void clickOnPlusIcon(String roomName) {
		isElementDisplayed("btn_add_column", roomName);
		click(element("btn_add_column", roomName));
		logMessage("Step : plus icon next to " + roomName + " is expanded\n");
	}

	public String getCheckedColumnData(String index1, String index2) {
		isElementDisplayed("txt_chckdColumnData", index1, index2);
		return element("txt_chckdColumnData", index1, index2).getText().trim();
	}

	public void verifyDataIsDeleted(String title, String expValue) {
		waitForLoadingImageToDisappear("deleteing data");
		Assert.assertFalse(checkIfElementIsThere("txt_tableResult", title, expValue));
		logMessage("ASSERT PASSED: " + expValue + " is deleted \n");
	}

	public void verifyValidFileIsDownloaded(String filepath,String filename) {
		wait.hardWait(2);
//		String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
//				+ File.separator + "resources" + File.separator + "DownloadedFiles" + File.separator + filename
//				+ ".csv";
		String filePath=filepath+ File.separator + filename	+ ".csv";
		waitForLoadingImageToDisappear("Generating CSV file... Please wait");
		File sourceFile = new File(filePath);
		Assert.assertTrue(sourceFile.exists(), "ASSERT FAILED: file is not downloaded \n");
		logMessage("ASSERT PASSED: '" + filePath + "' is downloaded \n");

	}

	public void _deleteExistingCSVFile(String filePath,String filename) {

		/*
		 * File sourceFile = new File(filePath); if (sourceFile.exists()) {
		 * sourceFile.delete(); logMessage(
		 * "STEP: Already Existed File is deleted from location " +
		 * sourceFile.getAbsolutePath()); }
		 */
//		String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
//				+ File.separator + "resources" + File.separator + "DownloadedFiles" + File.separator;
		System.out.println(filePath);
		File folder = new File(filePath);
		final File[] files = folder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(final File dir, final String name) {
				System.out.println(name);
				// if(name.matches(filename+"(.*).csv"))
				return name.matches(filename + "(.*).csv");

			}
		});
		for (final File file : files) {
			System.out.println("deleted file " + file);
			if (!file.delete()) {
				System.err.println("Can't remove " + file.getAbsolutePath());
			}
		}
	}

	public void clickOnDownloadButtonAndVerifyValidFileIsDownloaded(String btnName, String fileName,String downloadedFilePath) {
//		downloadedFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
//				+ File.separator + "resources" + File.separator + "DownloadedFiles" + File.separator + fileName
//				+ ".csv";
//		
		//String filePath=downloadedFilePath+ File.separator + fileName + ".csv";
		 _deleteExistingCSVFile(downloadedFilePath,fileName);
		//String filePath=downloadedFilePath+ File.separator + fileName + ".csv";
		clickOnButtonUnderSessioning(btnName);
		wait.hardWait(5);
		verifyValidFileIsDownloaded(downloadedFilePath,fileName);

	}

	public void verifyAddedCriteriaIsDeleted(String critiria) {

		Assert.assertFalse(checkIfElementIsThere("select_role", critiria));
		logMessage("ASSERT PASSED: '" + critiria + "' is deleted \n");

	}

	public void importValidFile(String abstractId, String downloadedFilePath) {
		String data = abstractId + ",Reject";
		DataProvider.writeDataInAlreadyExistingCSVFile(downloadedFilePath, data);
		wait.hardWait(5);
		importFileWithValidData(downloadedFilePath);
		// clickOnButtonUnderSessionModule("Import");
	}

	public void importFileWithValidData(String downloadedFilePath) {
		isElementDisplayed("inp_fileupload", "Please upload your file:", "1");
		sendKeysUsingXpathInJavaScriptExecutor(element("inp_fileupload", "Please upload your file", "1"),
				downloadedFilePath);
		// element("inp_fileupload", "Please upload your file",
		// "1").sendKeys(downloadedFilePath);
		wait.hardWait(5);
		System.out.println("*********downloadedFilePath   " + downloadedFilePath);
		logMessage("STEP: File is imported after adding valid data \n");

	}

	public void enterValuesInCreateSession(String sessionName, String sessionDuration, String sessionType,
			String symposiaSubmissionType) {
		enterValuesForProgram("session_name", sessionName);
		enterValuesForProgram("session_duration", sessionDuration);
		selectValueForSymposium("session_type", sessionType);
		selectValueForSession("Symposia Submission Type", symposiaSubmissionType);
	}

	private void selectValueForSession(String text, String symposiaSubmissionType) {
		isElementDisplayed("drpDown_sympType", text);
		click(element("drpDown_sympType", text));
		logMessage("STEP: '" + text + "' is clicked \n");
		selectValueFromDropDown(symposiaSubmissionType);

	}

	private void selectValueFromDropDown(String value) {
		isElementDisplayed("listItem_SymposiumType", "combo-list-item", value);
		click(element("listItem_SymposiumType", "combo-list-item", value));
		logMessage("STEP: " + value + " is selected \n");
	}

	public void selectAbtract(String value) {
		isElementDisplayed("txt_instruction", value);
		element("txt_instruction", value).click();
		logMessage("STEP: Abstract '" + value + "' is selected \n");

	}

	public void selectAbtract(List<String> values) {
		for (String value : values) {
			isElementDisplayed("txt_instruction", value);
			element("txt_instruction", value).click();
			logMessage("STEP: Abstract '" + value + "' is selected \n");
		}
	}

	public void enterDetailsInCreateNewSessionFromSymposium(String sessionType, String SessionProgramArea,
			String Symposium) {
		selectValueForCreateNewSessionFromSymposium("Session Type", sessionType);
		selectValueForCreateNewSessionFromSymposium("Session Program Area", SessionProgramArea);
		wait.hardWait(2);
		selectValueForCreateNewSessionFromSymposium("Symposium:", Symposium);

	}

	public void selectValueForCreateNewSessionFromSymposium(String label, String sessionType) {
		isElementDisplayed("drpdown_Symposium", label);
		clickUsingXpathInJavaScriptExecutor(element("drpdown_Symposium", label));
		click(element("drpdown_Symposium", label));
		logMessage("STEP: '" + label + "' is clicked \n");
		wait.hardWait(2);
		selectValueFromDropDown(sessionType);
	}

	public void enterValuesInCreateSessionFromSymposium(String duration, String symposiaSubmissionType) {
		enterValuesForProgram("session_duration", duration);
		selectValueForSession("Symposia Submission Type", symposiaSubmissionType);

	}

	public void verifySchedulerGrid() {
		waitForProcessBarToDisappear();
		isElementDisplayed("txt_SchedulerGrid", "pagecontents");
		Assert.assertTrue(checkIfElementIsThere("txt_SchedulerGrid", "pagecontents"));
		logMessage("STEP: Scheduler Grid is present \n");
	}

	public void selectValuesForMeetingday() {

		isElementDisplayed("drpDown_meetingDay", "Meeting Day");
		click(element("drpDown_meetingDay", "Meeting Day"));
		logMessage("STEP: Meeting Day dropdown is selected \n");
		selectValueByIndexing(2);
	}

	public void rightClickOnSession() {
		wait.hardWait(2);
		isElementDisplayed("txt_session");
		System.out.println("************" + element("txt_session").getText());
		rightClick(element("txt_session"));
		// isElementDisplayed("txt_SchedulerGrid", "view-item");
		// rightClick(elements("txt_SchedulerGrid", "view-item").get(number));
		logMessage("STEP: Right clicked on session \n");
	}

	public void verifyApplicationChangesSessionType(String expectedSessionType) {
		isElementDisplayed("inp_programField", "session_type");
		String actualVal = getValUsingXpathInJavaScriptExecutor(element("inp_programField", "session_type")).trim();
		System.out.println("actualVal " + actualVal + " expectedSessionType " + expectedSessionType);
		Assert.assertEquals(actualVal, expectedSessionType);
		logMessage("ASSERT PASSED: Application only changes the 'Session Type' \n");
	}

	public void verifyColumnHeaders(List<String> columnsList) {
		for (String columnName : columnsList) {
			wait.hardWait(1);
			isElementDisplayed("column_headers", columnName);
			logMessage("ASSERT PASSED: Column " + columnName + " is displayed\n");
		}
	}

	public void selectAbstractForEditing(int index1, int index2) {
		isElementDisplayed("txt_tableData", String.valueOf(index1), String.valueOf(index2));
		elements("txt_tableData", String.valueOf(index1), String.valueOf(index2)).get(0).click();
		logMessage("Step : Clicked on edit link of first abstract\n");
	}

	public void editAbstractDetails(String title, String abstractData) {
		Submission_Page_Actions objSubmission = new Submission_Page_Actions(driver);
		wait.hardWait(3);
		System.out.println("------size:" + driver.getWindowHandles().size());
		switchToWindowHavingIndex(1);
		// String currentHandle=driver.getWindowHandle();
		// System.out.println("-------currentHandle:"+currentHandle);
		// driver.switchTo().window(currentHandle);
		driver.manage().window().maximize();
		objSubmission.clickOnEditButton("2");
		objSubmission.submitTitleAndBodyDetails(title, abstractData);
		objSubmission.clickOnSaveAndContinueButton();
		objSubmission.verifyPageHeaderForASection("Properties");
		objSubmission.clickOnSaveAndContinueButton();
		objSubmission.verifyPageHeaderForASection("Authors");
		objSubmission.clickOnSaveAndContinueButton();
		objSubmission.verifyPageHeaderForASection("Disclosures");
		objSubmission.clickOnSaveAndContinueButton();
		objSubmission.verifyPageHeaderForASection("Review & Submit");
		objSubmission.verifyAbstractAnswersForSubmission(2, "Title", title);
		objSubmission.verifyAbstractAnswersForSubmission(2, "Abstract", abstractData);
		objSubmission.clickOnNamedButton("Finish");
		switchToWindowHavingIndex(0);
	}

	public void verifyPrintPreviewTableContents(String value) {
		isElementDisplayed("tbl_contents", value);
		logMessage("ASSERT PASSED : print preview table title is verified as " + value);
	}

	public void selectHeaderCheckbox() {
		isElementDisplayed("table_columnDate", "hd-checker");
		click(element("table_columnDate", "hd-checker"));
		logMessage("Step : Header checkbox is clicked\n");

	}

	public void enterValuesInAddEditRooms(String roomName) {
		enterValuesForProgram("room_name", roomName);
	}

	public void expandColumnWidth(String columnName, String value) {
		isElementDisplayed("column_headers", columnName);
		JavascriptExecutor executor = (JavascriptExecutor) driver;

		executeJavascript("document.querySelector('div[qtip=\"Edit Abstract\"]').style.width=\"100px\"");
		// executeJavascript("document.querySelector('.primaryNav
		// >li:nth-child(7) ul').style.display ='block'");
		// executeJavascript("document.querySelector('div[qtip=\'Edit
		// Abstract\']').setAttribute(\"style\",\"width:50px\"));");
		// executor.executeScript(
		// "document.querySelector('div[qtip='Edit
		// Abstract']').setAttribute(\"style\",\"width:50px\"));");
		// modifyAttributeOfElement(element("column_headers",columnName),
		// "width", value);
		// JavascriptExecutor executor = (JavascriptExecutor) driver;
		// executor.executeScript("arguments[0].attr('width','60');",
		// element("column_headers",columnName));
	}

	public void modifyAttributeOfElement(WebElement element, String attribute, String value) {
		try {
			wait.hardWait(3);
			((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1],arguments[2]);",
					element, attribute, value);
		} catch (WebDriverException web) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String> getTableDataFromSortPopUpWindow(String columnName, String[] fullColumnsList) { // add
																										// functionality
		wait.hardWait(5);
		List<String> tableDuplicateData = new ArrayList<>();
		List<String> tableFullData = new ArrayList<>();
		tableFullData.addAll(Arrays.asList(fullColumnsList));
		if (checkIfElementIsThere("table_columnDate", columnName)) {
			for (WebElement elem : elements("table_columnDate", columnName)) {
				tableDuplicateData.add((elem).getText().trim());
			}
			tableFullData.removeAll(tableDuplicateData);
		}
		return tableFullData;
	}

	public void deleteExistingSortingCriteria(String columnName, String criteriaName) {
		int index = 0;
		if (checkIfElementIsThere("table_columnDate", columnName)) {
			for (WebElement elem : elements("table_columnDate", columnName)) {
				index++;
				if (elem.getText().trim().equals(criteriaName)) {
					break;
				}
			}
			selectARowInSortWindowPopup(columnName, index - 1);
			clickOnAddButton("Delete");
		}
	}

	public void selectARowInSortWindowPopup(String columnName, int index) {
		isElementDisplayed("table_columnDate", columnName);
		elements("table_columnDate", columnName).get(index).click();
		logMessage("Step: Pre-existing criteria " + elements("table_columnDate", columnName).get(index).getText().trim()
				+ " is selected\n");
	}

	public void verifyColumnNamesPresentForSorting(String[] fastColumnNames) {
		for (String columnName : fastColumnNames) {
			isElementDisplayed("lst_column", columnName);
			logMessage("ASSERT PASSED: Column " + columnName + " is available for sorting\n");
		}
	}

	public List<String> getCheckedColumnHeadings() {
		List<String> columnsCheckedList = new ArrayList<>();
		isElementDisplayed("checked_columnHeadings");
		for (WebElement elem : elements("checked_columnHeadings")) {
			columnsCheckedList.add(elem.getText().trim());
		}
		return columnsCheckedList;
	}

	public void verifySortingOrderIsPresent(String columnName) {
		wait.hardWait(5);
		isElementDisplayed("txt_sortingOrder", columnName);
		logMessage("Step : Sorting order is present for " + columnName + "\n");
	}

	public String getRandomTableData(String index, String columnName) {
		List<String> searchTerm = getTableData(index, columnName);
		int listIndex = ThreadLocalRandom.current().nextInt(searchTerm.size());
		logMessage("Step : SearchTerm is\" " + searchTerm.get(listIndex) + "\"\n");
		return searchTerm.get(listIndex);

	}

	public void doubleClickOnRow(String index) {
		waitForLoaderToDisappear();
		doubleClick(element("chkbox_records", index));
		logMessage("Step : Double clicked on table row\n");
	}

	public void inputTextInFilter(String value, String index) {
		waitForLoaderToDisappear();
		isElementDisplayed("input_filter", "Filter", index);
		element("input_filter", "Filter", index).sendKeys(value);
		logMessage("STEP : " + value + " is entered in filter input box \n");
		waitForLoaderToDisappear();
	}

	public void selectRoleOnSaveGridConfiguration(String role) {
		isElementDisplayed("select_role", role);
		click(element("select_role", role));
		logMessage("Step : Role is selected as " + role + "\n");
	}

	public void verifyIsDataDeleted(String expValue) {
		waitForLoaderToDisappear();
		Assert.assertFalse(checkIfElementIsThere("txt_instruction", expValue));
		logMessage("STEP :" + expValue + " is deleted \n");
	}

	public void verifyIsHostDeleted(String expValue) {
		waitForLoaderToDisappear();
		int size = elements("txt_emptyTable").size();
		Assert.assertTrue(size <= 1);
		// Assert.assertFalse(checkIfElementIsThere("txt_instruction",
		// expValue));
		logMessage("STEP :" + expValue + " is deleted \n");
	}

	public void clickOnDropDownOfLabel(String dropDown, String tagName) {
		System.out.println("1");
		isElementDisplayed("input_label", dropDown, tagName);
		click(element("input_label", dropDown, tagName));
		logMessage("Step : Clicked on " + dropDown + "\n");
	}

	public void selectValueFromDropDown(String priviousValue, String value) {
		isElementDisplayed("listitem_dropdown", priviousValue, value);
		click(element("listitem_dropdown", priviousValue, value));
		logMessage("Step : Role is selected as " + value + "\n");
	}

	public void verifySpanUnderlabelElement(String value) {
		waitForLoaderToDisappear();
		if (value.matches("Oral")) {
			verifyCheckBoxUnderLableName("Sci-Mix:", "input");
			verifySpanUnderlabelName("Financial Co-sponsor:", "textarea");
			verifySpanUnderlabelName("Newsworthy Reason:", "textarea");
			verifylistBoxUnderLableName("Themes:", "div");
			verifylistBoxUnderLableName("Co-sponsor - Nominal:", "div");
			verifylistBoxUnderLableName("Co-sponsor - Cooperative:", "div");
			verifylistBoxUnderLableName("Session Half-Day:", "div");
			verifySpanUnderlabelName("Symposia Submission Type:", "input");
			verifySpanUnderlabelName("Session Track:", "input");
			verifyCheckBoxUnderLableName("Newsworthy:", "input");
		} else if (value.matches("Sci-Mix")) {
			verifyPopupMessage("Confirm");
			clickOnButtonByIndexing("Yes", "1");
			verifySpanUnderlabelName("Session Track:", "input");
			verifySpanUnderlabelName("Session Half-Day:", "div");
		} else if (value.matches("Poster")) {
			verifyPopupMessage("Confirm");
			clickOnButtonByIndexing("Yes", "1");
			verifyCheckBoxUnderLableName("Sci-Mix:", "input");
			verifySpanUnderlabelName("Financial Co-sponsor:", "textarea");
			verifySpanUnderlabelName("Newsworthy Reason:", "textarea");
			verifylistBoxUnderLableName("Themes:", "div");
			verifylistBoxUnderLableName("Co-sponsor - Nominal:", "div");
			verifylistBoxUnderLableName("Co-sponsor - Cooperative:", "div");
			verifylistBoxUnderLableName("Session Half-Day:", "div");
			verifySpanUnderlabelName("Symposia Submission Type:", "input");
			verifySpanUnderlabelName("Session Track:", "input");
			verifyCheckBoxUnderLableName("Newsworthy:", "input");
		}
	}

	public void enterTitleOfSession(String fieldName, String tagName, String sessionTitle) {
		isElementDisplayed("input_label", fieldName, tagName);
		element("input_label", fieldName, tagName).clear();
		element("input_label", fieldName, tagName).sendKeys(sessionTitle);
		logMessage("Step : " + fieldName + " is entered as " + sessionTitle + "\n");
	}

	public void checkRowInTable(int index, int columnIndex) {
		isElementDisplayed("txt_tableData", String.valueOf(index), String.valueOf(columnIndex));
		click(element("txt_tableData", String.valueOf(index), String.valueOf(columnIndex)));
		logMessage("Step : Checked on row.\n");
	}

	public void verifyRowIsDeleted(String expValue, int index, int columnIndex) {
		waitForLoaderToDisappear();
		try {
			Assert.assertTrue(
					checkIfElementIsThere("txt_instruction", String.valueOf(index), String.valueOf(columnIndex)));
		} catch (AssertionError e) {
			logMessage("STEP :" + expValue + " is deleted \n");
		}
	}

	public void verifyLabelName(String fieldName, String tagName) {
		if (tagName == "") {
			isElementDisplayed("txt_label", fieldName);
			logMessage("STEP : " + fieldName + " label is verified \n");
		} else {
			isElementDisplayed("input_label", fieldName, tagName);
			logMessage("STEP : " + fieldName + " label is verified \n");
		}
	}

	public void verifyCheckBoxUnderLableName(String fieldName, String tagName) {
		isElementDisplayed("lable_checkbox", fieldName, tagName);
		logMessage("STEP : " + fieldName + " label is verified \n");
	}

	public void verifySpanUnderlabelName(String fieldName, String tagName) {
		isElementDisplayed("input_area", fieldName, tagName);
		logMessage("STEP : " + fieldName + " label is verified \n");
	}

	public void verifylistBoxUnderLableName(String fieldName, String tagName) {
		isElementDisplayed("label_listbox", fieldName, tagName);
		logMessage("STEP : " + fieldName + " label is verified \n");
	}

	public List<String> getEditableColumnsList() {
		List<String> editableColumnsList = new ArrayList<>();
		isElementDisplayed("img_editColumnHeading");
		for (WebElement elem : elements("img_editColumnHeading")) {
			editableColumnsList.add(elem.getText().trim());
		}
		return editableColumnsList;
	}

	public Map<String, String> addDataInMap() {
		editableColumnsMap.put("Final ID", "final_id");
		editableColumnsMap.put("Title", "title");
		editableColumnsMap.put("Presenting Author", "presenterList");
		editableColumnsMap.put("Presenter Institution", "presentersInstitutions");
		editableColumnsMap.put("Presentation Type", "presentationType.name");
		editableColumnsMap.put("Sci-Mix Consideration", "subPresentationType.name");
		return editableColumnsMap;
	}

	public void selectEditableColumn(List<String> editableColumnsList, Map<String, String> editableColumnsMap) {
		isElementDisplayed("input_editableColumn", editableColumnsMap.get(editableColumnsList.get(0)));
		System.out.println(
				"-------######" + elements("input_editableColumn", editableColumnsMap.get(editableColumnsList.get(0)))
						.get(0).getText().trim());
		doubleClick(elements("input_editableColumn", editableColumnsMap.get(editableColumnsList.get(0))).get(0));
		logMessage("Step : Double clicked on first element of " + editableColumnsList.get(0) + " column\n");
	}

	public void editColumnData(List<String> editableColumnsList, Map<String, String> editableColumnsMap,
			String editedData) {
		// isElementDisplayed("input_editableColumn",editableColumnsMap.get(editableColumnsList.get(0)));
		// System.out.println("**********"+elements("input_editableColumn",editableColumnsMap.get(editableColumnsList.get(0))).get(0).getText().trim());
		//// elements("input_editableColumn",editableColumnsMap.get(editableColumnsList.get(0))).get(0).clear();
		// sendKeysUsingXpathInJavaScriptExecutor(elements("input_editableColumn",editableColumnsMap.get(editableColumnsList.get(0))).get(0),
		// editedData);
		//// elements("input_editableColumn",editableColumnsMap.get(editableColumnsList.get(0))).get(0).sendKeys(editedData);
		// logMessage("Step: Column data is updated as "+editedData);
		isElementDisplayed("inp_editColumnData");
		System.out.println("**********" + element("inp_editColumnData").getText().trim());
		element("inp_editColumnData").clear();
		element("inp_editColumnData").sendKeys(editedData);
		logMessage("Step: Column data is updated as " + editedData);
	}

	public void selectEditableColumnAndEditData(List<String> editableColumnsList, String editedData) {
		editableColumnsMap = addDataInMap();
		selectEditableColumn(editableColumnsList, editableColumnsMap);
		editColumnData(editableColumnsList, editableColumnsMap, editedData);
		click(element("table_abstracts"));
	}

	public void verifyDataIsEdited(List<String> editableColumnsList, String editedData) {
		wait.hardWait(2);
		Assert.assertTrue(
				elements("input_editableColumn", editableColumnsMap.get(editableColumnsList.get(0))).get(0).getText()
						.trim().equals(editedData),
				"ASSERT FAILED : Updated data as " + editedData + " does not matches with data on page as "
						+ elements("input_editableColumn", editableColumnsMap.get(editableColumnsList.get(0))).get(0)
								.getText().trim()
						+ "\n");
		logMessage("ASSERT PASSED : Updated data as " + editedData + " matches with data on page as "
				+ elements("input_editableColumn", editableColumnsMap.get(editableColumnsList.get(0))).get(0).getText()
						.trim()
				+ "\n");
	}

	public void clickOnTopScroller() {
		isElementDisplayed("top_scroller");
		click(element("top_scroller"));
		logMessage("Step : Clicked on Top Scoller\n");
	}

	public void clickOnMainPage(int index) {
		isElementDisplayed("table_abstracts");
		click(elements("table_abstracts").get(index));
		logMessage("Step : Clicked on main page\n");
	}

	public void clickOnSessionBuilderTab(String tabName) {
		isElementDisplayed("btn_navPanel", tabName);
		elements("btn_navPanel", tabName).get(0).click();
		logMessage("Step: Clicked on " + tabName + " tab\n");
	}

	public void verifyAbstractsListIsPresent(String className) {
		wait.hardWait(2);
		isElementDisplayed("list_abstracts", className);
		Assert.assertTrue(elements("list_abstracts", className).size() > 0,
				"ASSERT FAILED: Abstracts list is not present on page\n");
		logMessage("ASSERT PASSED: Abstracts list is present on page\n");
	}

	public void verifyAbstractsViewIsDisplayed() {
		wait.hardWait(1);
		if (checkIfElementIsThere("btn_saveAndEdit", "Assign Hosts", "1")) {
			logMessage("Step: Abstracts view is displayed by default\n");
		} else {
			clickOnSaveAndEditButton("Assign Abstracts", 1);
			waitForLoadingImageToDisappear("Loading...");
			isElementDisplayed("btn_saveAndEdit", "Assign Hosts");
		}
	}

	public void clickOnColumnHeaders(String header) {
		isElementDisplayed("column_headers", header);
		click(element("column_headers", header));
		logMessage("STEP: column header '" + header + "' is clicked \n");

	}

	public int getSelectedListSize() {
		return elements("txt_totalRecords").size();

	}

	public void verifyAllSelectedListIsPresentInPrintPreview(int symposiasize) {
		isElementDisplayed("txt_SchedulerGrid", "my-paging-text");
		Assert.assertTrue(
				elements("txt_SchedulerGrid", "my-paging-text").get(3).getText().contains(String.valueOf(symposiasize)),
				"All selected elements from list are not printed\n");
		logMessage("ASSERT PASSED :  All selected options list is avalaible in print preview\n");

	}

	public List<String> SelectRecords(int number) {
		List<String> controlIds = new ArrayList<String>();
		for (int i = 1; i <= number; i++) {
			selectaRecordFromTheList(i, "2");
			String cid = getCheckedColumnData(String.valueOf(i), String.valueOf(1));
			System.out.println("** cid:   " + cid);
			controlIds.add(cid);
		}
		logMessage("STEP: " + number + " records are selected \n");
		return controlIds;
	}

	public void verifyAddedAbstractsInTable(String classlbl, String expValue) {
		isElementDisplayed("txt_controlId", classlbl, expValue);
		// Assert.assertTrue(checkIfElementIsThere("txt_controlId", classlbl,
		// expValue));
		logMessage("ASSERT PASSED: '" + expValue + "' is present \n");
	}

	public void verifyAddedAbstracts(String colHeader, List<String> controlId) {
		for (String cid : controlId) {
			verifyAddedAbstractsInTable(colHeader, cid);
		}
	}

	public void verifyAddedPlaceHolder(String label, String placeholderValue) {
		Assert.assertTrue(checkIfElementIsThere("listItem_SymposiumType", label, placeholderValue));
		logMessage("ASSERT PASSED: " + placeholderValue + "is present \n");
	}

	public void verifyWithDrawRow(String fontText, String text) {
		waitForLoaderToDisappear();
		Assert.assertTrue(checkIfElementIsThere("row_withdraw", fontText, text));
		logMessage("ASSERT PASSED: " + text + " with " + fontText + " is displayed \n");
	}

	public void rightClickOnSessionList(String recordnumber) {
		rightClick(element("chkbox_records", recordnumber));
	}

	public String getHostColoumData(String hostname) {
		return elements("input_editableColumn").get(0).getText();
	}

	public void verifyAvailableOptionsOnSaveGridPopUpUnderAbstracts(String fieldname) {
		isElementDisplayed("inp_fileupload", "1");
		logMessage("ASSERT PASSED : input box is available for " + fieldname);

	}

	public void enterDurationOnCreateSessionPopUp(String fieldName, String roomName) {
		isElementDisplayed("inp_sessionAbbrev", fieldName);
		element("inp_sessionAbbrev", fieldName).clear();
		element("inp_sessionAbbrev", fieldName).sendKeys(roomName);
		logMessage("Step : " + fieldName + " is entered as " + roomName + "\n");
	}

	public void clickOnButtonUnderSessionModule(String text) {
		isElementDisplayed("btn_close", text);
		click(elements("btn_close", text).get(0));
		logMessage("STEP: clicked on '" + text + "' button \n");
	}

	public String getTextformTable(int index, int columnIndex) {
		isElementDisplayed("txt_tableData", String.valueOf(index), String.valueOf(columnIndex));
		return elements("txt_tableData", String.valueOf(index), String.valueOf(columnIndex)).get(0).getText();
	}

	public void enterTextInPopUpInput(String labelName, String index, String value) {
		isElementDisplayed("input_popup", labelName, index);
		element("input_popup", labelName, index).clear();
		element("input_popup", labelName, index).sendKeys(value);
	}

	public String subtractValue(String value, int toBeSubtrcated) {
		int value1 = Integer.parseInt(value);
		int subtrectrdValue = value1 - toBeSubtrcated;
		return String.valueOf(subtrectrdValue);
	}

	public void isFinalIDAllocatedToAbstract(String privousFinalId) {
		String value = " ";
		if (!privousFinalId.equals(value)) {
			verifyPopupMessage("Confirm");
			clickOnButtonUnderSessionModule("Yes");
		}
	}

	public void clickOnLinkText(String text, String linkText) {
		isElementDisplayed("btn_lnkTxt", text, linkText);
		element("btn_lnkTxt", text, linkText).click();
		logMessage("Step : " + linkText + " button is clicked\n");
	}

	public void verifyLableFormateAfterEdit(String text) {
		Assert.assertTrue(checkIfElementIsThere("inp_label", text));
		logMessage("Step : " + text + " is successfully edited\n");
	}

	public void verifyFormateIsDeleted(String text) {
		waitForLoaderToDisappear();
		try {
			Assert.assertTrue(checkIfElementIsThere("inp_label", text));
		} catch (AssertionError e) {
			logMessage("STEP :" + text + " formate is deleted \n");
		}
	}

	public void doubleClickToEditTableData(int index, int columnIndex) {
		waitForLoaderToDisappear();
		doubleClick(element("txt_tableData", String.valueOf(index), String.valueOf(columnIndex)));
		logMessage("Step : Double clicked on table row\n");
	}

	public void verifyInputBoxInTableData(int index) {
		isElementDisplayed("inp_saveGridFilters", String.valueOf(index));
		Assert.assertTrue(checkIfElementIsThere("inp_saveGridFilters", String.valueOf(index)));
		logMessage("STEP : Edit field on double click \n");
	}

	public void rowIsNotPresentThenAddRowInCurrentlyAssignedAbstracts(int index, int columnIndex) {
		try {
			Assert.assertTrue(
					checkIfElementIsThere("txt_tableData", String.valueOf(index), String.valueOf(columnIndex)));
			logMessage("STEP: Row is not present, add a new row \n");
		} catch (AssertionError e) {
			clickOnArrowButton("Presenting Author");
			enterFilterText("Filters", YamlReader.getYamlValue("Session.Session_Builder.Author_Last_Name"));
			addHostforCurrentlyAssignedAbstracts();
		}
	}

	// public void addHostforSymposium() {
	// waitForLoadingImageToDisappear("Loading...");
	// isElementDisplayed("txt_hostDetails", "session_host_last_name");
	//
	// WebElement Sourcelocator = elements("txt_hostDetails",
	// "session_host_last_name").get(1);
	// String hostlastname = elements("txt_hostDetails",
	// "session_host_last_name").get(1).getText();
	// isElementDisplayed("txt_dropField");
	// WebElement Destinationlocator = element("txt_dropField");
	// dragAndDrop(Sourcelocator, Destinationlocator);
	// wait.hardWait(2);
	// waitForLoadingImageToDisappear("Loading...");
	// // waitForLoaderToDisappear();
	// logMessage("STEP: '" + hostlastname + " is selected as Host \n");
	// }
	public void addHostforCurrentlyAssignedAbstracts() {
		waitForLoadingImageToDisappear("Loading...");
		isElementDisplayed("txt_hostDetails", "presenterList");

		WebElement Sourcelocator = elements("txt_hostDetails", "presenterList ").get(1);
		String hostlastname = elements("txt_hostDetails", "presenterList ").get(1).getText();
		isElementDisplayed("txt_dropField");
		WebElement Destinationlocator = element("txt_dropField");
		dragAndDrop(Sourcelocator, Destinationlocator);
		wait.hardWait(2);
		waitForLoadingImageToDisappear("Loading...");
		// waitForLoaderToDisappear();
		logMessage("STEP: '" + hostlastname + " is selected as Host \n");
	}

	public void enterValuesInAssignDurationPage(String label, String value) {
		isElementDisplayed("inp_assignDuration", label);
		element("inp_assignDuration", label).sendKeys(value);
		logMessage("STPE: " + value + " is entered in " + label + "\n");

	}

	public void verifyDurationForAssignedAbstracts(String title, String expValue, int numberOfAbstract) {
		for (int i = 1; i <= numberOfAbstract; i++) {
			verifyAddedDetails(title, expValue);
		}
	}

	public String getTimeDurationValues(String text) {
		isElementDisplayed("txt_instruction", text);
		return element("txt_instruction", text).getText().trim();
	}

	public void verifyTimeDuration(String text, String timeDurationLabel) {
		isElementDisplayed("txt_instruction", text);
		System.out.println("******** timeDurationLabel " + timeDurationLabel);
		System.out.println("actual value: " + element("txt_instruction", text).getText().trim());
		Assert.assertFalse(timeDurationLabel.contentEquals(element("txt_instruction", text).getText().trim()));
		logMessage("ASSERT PASSED: Current duration is updated \n");

	}

	public void enterValuesInAssignAbstractFinalID(String finalId, String hashtag) {
		isElementDisplayed("inp_assignDuration", "New");
		elements("inp_assignDuration", "New").get(0).sendKeys(finalId);
		logMessage("STEP: " + finalId + " is entered as FinalD Name \n");
		elements("inp_assignDuration", "New").get(1).sendKeys(hashtag);
		logMessage("STEP: " + hashtag + " is entered as hash tag \n");

	}

	public void clickCheckboxOnAbstractAssignPage(String[] abstractTypes) {
		for (String type : abstractTypes) {
			clickCheckboxOnSaveGridConfiguration(type);
		}
	}

	public void verifyFieldsArePrefilledWithSavedDetails() {
		isElementDisplayed("inp_assignDuration", "New");
		String finalId = getValUsingXpathInJavaScriptExecutor(elements("inp_assignDuration", "New").get(0));
		logMessage("STEP: " + finalId + " is entered as FinalD Name \n");
		String hashtag = getValUsingXpathInJavaScriptExecutor(elements("inp_assignDuration", "New").get(1));
		logMessage("STEP: " + hashtag + " is entered as hash tag \n");

	}

	public void verifyFinalIdIsDeleted(String value) {
		Assert.assertFalse(checkIfElementIsThere("txt_label", value));
		logMessage("STEP : " + value + " is deleted \n");
	}

	public String clickOnPencilIconCloumn(String colHeader, String expVal) {
		isElementDisplayed("txt_tableResult", colHeader, expVal);
		doubleClick(element("txt_tableResult", colHeader, expVal));
		String updatedVal = expVal + System.currentTimeMillis();
		sendKeysUsingXpathInJavaScriptExecutor(elements("inp_editColumnData").get(1), updatedVal);
		return updatedVal;
	}

	public void expandSideTab(String tabName) {
		isElementDisplayed("img_expandTab", tabName);
		click(element("img_expandTab", tabName));
		logMessage("Step: " + tabName + " is expanded\n");
	}

	public void clickOnUpdatedEventName(String eventName) {
		isElementDisplayed("lnk_eventName", eventName);
		click(element("lnk_eventName", eventName));
		logMessage("Step: Clicked on event " + eventName + "\n");
	}

	public void verifySessionOrEventInformation(Map<String, String> eventInfo) {
		Iterator mapIterator = eventInfo.entrySet().iterator();
		while (mapIterator.hasNext()) {
			Map.Entry pair = (Map.Entry) mapIterator.next();
			System.out.println(pair.getKey() + " = " + pair.getValue());
			System.out.println(element("txt_eventInfo", pair.getKey().toString()).getText().trim());

			Assert.assertEquals(element("txt_eventInfo", pair.getKey().toString()).getText().trim(), pair.getValue(),
					"ASSERT FAILED: " + pair.getKey() + " value is not updated as " + pair.getValue());
			logMessage("ASSERT PASSED: " + pair.getKey() + " value is updated as " + pair.getValue());
		}
	}

	public String getRandomSessionName() {
		isElementDisplayed("txt_sessionItinerary");
		int randomnumber = generateRandomNumberWithInRange(0, (elements("txt_sessionItinerary").size()) - 1);
		System.out.println("-----random record:" + elements("txt_sessionItinerary").get(randomnumber).getText().trim());
		return elements("txt_sessionItinerary").get(randomnumber).getText().trim();
	}

	public void verifyFilterResultsForSessions(String expectedSessionName) {
		isElementDisplayed("txt_sessionItinerary");
		Assert.assertEquals(element("txt_sessionItinerary").getText().trim(), expectedSessionName,
				"ASSERT FAILED: Filetr results " + element("txt_sessionItinerary").getText().trim()
						+ " does not matches with " + expectedSessionName + "\n");
		logMessage("ASSERT FAILED: Filetr results " + element("txt_sessionItinerary").getText().trim()
				+ " matches with " + expectedSessionName + "\n");
	}

	public void rightClickOnTopLevelSession(String lnkName) {
		wait.hardWait(2);
		isElementDisplayed("btn_navPanel", lnkName);
		System.out.println("************" + element("btn_navPanel", lnkName).getText());
		rightClick(element("btn_navPanel", lnkName));
		logMessage("STEP: Right clicked on Top level session \n");
	}

	public void verifydropdownOnPopupWindow(String btnName) {
		isElementDisplayed("lnk_selButton");
		logMessage("Step : " + btnName + " is displayed on page\n");
	}

	public void selectSessionOrEvent(String sessionName, String index) {
		isElementDisplayed("chkbox_column", sessionName, index);
		elements("chkbox_column", sessionName, index).get(1).click();
		logMessage("Step : " + sessionName + " is selected\n");
	}

	public void verifySelectedSessionIsRemovedFromList(String sessionName, String index) {
		Assert.assertTrue(elements("chkbox_column", sessionName, index).size() == 1,
				"ASSERT FAILED: Session " + sessionName + " is not removed from list\n");
		logMessage("ASSERT PASSED: Session " + sessionName + " is removed from list\n");
	}

}
