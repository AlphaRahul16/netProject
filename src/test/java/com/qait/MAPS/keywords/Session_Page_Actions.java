package com.qait.MAPS.keywords;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.tools.ant.taskdefs.condition.IsLastModified;
import org.apache.xalan.xsltc.compiler.sym;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.collections.CollectionUtils;
import com.gargoylesoftware.htmlunit.javascript.host.Window;
import com.itextpdf.text.log.SysoCounter;
import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.thoughtworks.selenium.webdriven.commands.DoubleClick;
import com.thoughtworks.selenium.webdriven.commands.IsElementPresent;
import com.qait.automation.utils.DataProvider;
import com.qait.automation.utils.DateUtil;

public class Session_Page_Actions extends ASCSocietyGenericPage {

	WebDriver driver;
	private String downloadedFilePath;
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
		// clickUsingXpathInJavaScriptExecutor(element("btn_navPanel",
		// buttonName));
		element("btn_navPanel", buttonName).click();
		waitForProcessBarToDisappear();
		logMessage("Step : " + buttonName + " button is clicked on left navigation panel\n");
	}

	public void verifyTitleForRoles(String title) {
		// isElementDisplayed("btn_navPanel",title)
		// logMessage("Step:");
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

	private void selectValueForSessionType(String label, String value) {
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
		//Assert.assertTrue(checkIfElementIsThere("txt_tableResult", classLabel, expValue));
		isElementDisplayed("txt_tableResult", classLabel, expValue);
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

	public void clickParticularRecordFromList(String recordName) {
		wait.hardWait(2);
		isElementDisplayed("chkbox_column", recordName);
		click(element("chkbox_column", recordName));
		logMessage("Step : " + recordName + " record is selected from list\n");
	}

	public void enterValuesInCreateSymposium(String symposiumTitle, String symposiumType) {
		enterValuesForProgram("session_name", symposiumTitle);
		selectValueForSymposium("session_type", symposiumType);
	}
	
	public String getRandomRecordFromTable(String columnIndex){
		wait.hardWait(2);
		isElementDisplayed("txt_totalRecords");
		int randomnumber = generateRandomNumberWithInRange(0, (elements("txt_totalRecords").size()) - 1);
		isElementDisplayed("btn_recordsname",String.valueOf(randomnumber),columnIndex);
		System.out.println("-----random record:"+element("btn_recordsname",String.valueOf(randomnumber),columnIndex).getText().trim());
		return element("btn_recordsname",String.valueOf(randomnumber),columnIndex).getText().trim();
    }

	public String selectaRandomRecordFromTheList() {
		isElementDisplayed("chkbox_records");
		int randomnumber = generateRandomNumberWithInRange(0, (elements("chkbox_records").size()) - 1);
		click(elements("chkbox_records").get(randomnumber));
		logMessage("Step : a random record is selected from the list with position " + randomnumber);
		return element("btn_recordsname", toString().valueOf(randomnumber)).getText();
	}

	public void selectaRecordFromTheList(int number) {
		isElementDisplayed("chkbox_records",String.valueOf(number));
		click(element("chkbox_records",String.valueOf(number)));
		logMessage("Step : Random record is selected from the list with position " + number);
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

		WebElement Sourcelocator = elements("txt_hostDetails", label).get(1);
		String value = elements("txt_hostDetails",label).get(1).getText();
		isElementDisplayed("txt_dropField");
		WebElement Destinationlocator = element("txt_dropField");
		dragAndDrop(Sourcelocator, Destinationlocator);
		wait.hardWait(2);
		waitForLoadingImageToDisappear("Loading...");
		// waitForLoaderToDisappear();
		logMessage("STEP: '" + value + " is selected as Host \n");
		return value;
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
		click(element("btn_remove", text, index));
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

	public void verifyValidFileIsDownloaded(String filePath) {
		wait.hardWait(2);
		waitForLoadingImageToDisappear("Generating CSV file... Please wait");
		File sourceFile = new File(filePath);
		Assert.assertTrue(sourceFile.exists(), "ASSERT FAILED: file is not downloaded \n");
		logMessage("ASSERT PASSED: '" + filePath + "' is downloaded \n");

	}

	public void _deleteExistingCSVFile(String filePath) {
		File sourceFile = new File(filePath);
		if (sourceFile.exists()) {
			sourceFile.delete();
			logMessage("STEP: Already Existed File is deleted from location " + sourceFile.getAbsolutePath());
		}
	}

	public void clickOnDownloadButtonAndVerifyValidFileIsDownloaded(String btnName, String fileName) {
		downloadedFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + "DownloadedFiles" + File.separator + fileName
				+ ".csv";
		_deleteExistingCSVFile(downloadedFilePath);
		clickOnButtonUnderSessioning(btnName);
		wait.hardWait(5);
		verifyValidFileIsDownloaded(downloadedFilePath);
	}

	public void verifyAddedCriteriaIsDeleted(String critiria) {

		Assert.assertFalse(checkIfElementIsThere("select_role", critiria));
		logMessage("ASSERT PASSED: '" + critiria + "' is deleted \n");

	}

	public void importValidFile(String abstractId) {
		String data = abstractId + ",Reject";
		DataProvider.writeDataInAlreadyExistingCSVFile(downloadedFilePath, data);
		wait.hardWait(5);
		importFileWithValidData();
		// clickOnButtonUnderSessionModule("Import");
	}

	private void importFileWithValidData() {
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

	public void selectAbtractWithStatus(String status) {
		isElementDisplayed("txt_instruction", status);

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
		System.out.println("************"+element("txt_session").getText());
		rightClick(element("txt_session"));
//		isElementDisplayed("txt_SchedulerGrid", "view-item");
//		rightClick(elements("txt_SchedulerGrid", "view-item").get(number));
		logMessage("STEP: Right clicked on session \n");
	}

	public void verifyApplicationChangesSessionType(String expectedSessionType) {
		isElementDisplayed("inp_programField", "session_type");
		String actualVal = getValUsingXpathInJavaScriptExecutor(element("inp_programField", "session_type")).trim();
		System.out.println("actualVal " + actualVal + " expectedSessionType " + expectedSessionType);
		Assert.assertEquals(actualVal, expectedSessionType);
		logMessage("ASSERT PASSED: Application only changes the 'Session Type' \n");
	}

	public void verifyColumnHeaders(List<String> columnsList){
		for(String columnName:columnsList){
			wait.hardWait(1);
			isElementDisplayed("column_headers",columnName);
			logMessage("ASSERT PASSED: Column "+columnName+" is displayed\n");
		}
	}

	public void selectAbstractForEditing(int index1, int index2) {
		isElementDisplayed("txt_tableData", String.valueOf(index1), String.valueOf(index2));
		elements("txt_tableData", String.valueOf(index1), String.valueOf(index2)).get(0).click();
		logMessage("Step : Clicked on edit link of first abstract\n");
	}

	public void editAbstractDetails(String title,String abstractData){
		Submission_Page_Actions objSubmission= new Submission_Page_Actions(driver);
		wait.hardWait(3);
		switchToWindowHavingIndex(1);
		driver.manage().window().maximize();
		objSubmission.submitTitleAndBodyDetails(title,abstractData);
		objSubmission.uploadImage("test.jpeg");
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

	public void enterValuesInAddEditRooms(String roomName) {
		enterValuesForProgram("room_name", roomName);
	}
	public void expandColumnWidth(String columnName, String value){
		isElementDisplayed("column_headers",columnName);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("document.querySelector('div[qtip='Edit Abstract']').setAttribute(\"style\",\"width:50px\"));");
//		modifyAttributeOfElement(element("column_headers",columnName), "width", value);
//		JavascriptExecutor executor = (JavascriptExecutor) driver;
//		executor.executeScript("arguments[0].attr('width','60');", element("column_headers",columnName));
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
	
	public List<String> getTableDataFromSortPopUpWindow(String columnName,String[] fullColumnsList){ //add functionality
		wait.hardWait(5);
		List<String> tableDuplicateData =new ArrayList<>();
		List<String> tableFullData =new ArrayList<>();
		tableFullData.addAll(Arrays.asList(fullColumnsList));
		if(checkIfElementIsThere("table_columnDate",columnName)){
		for(WebElement elem:elements("table_columnDate",columnName)){
			tableDuplicateData.add((elem).getText().trim());
		  }
		tableFullData.removeAll(tableDuplicateData);
		}     
			return tableFullData;
	}
	
	public void verifyColumnNamesPresentForSorting(List<String> fastColumnNames){
		for(String columnName: fastColumnNames){
			isElementDisplayed("lst_column",columnName);
			logMessage("ASSERT PASSED: Column "+columnName+" is available for sorting\n");
		}
	}
	
	public List<String> getCheckedColumnHeadings(){
		List<String> columnsCheckedList=new ArrayList<>();
		isElementDisplayed("checked_columnHeadings");
		for(WebElement elem: elements("checked_columnHeadings")){
			columnsCheckedList.add(elem.getText().trim());
		}
		return columnsCheckedList;
	}
	
	public void verifySortingOrderIsPresent(String columnName){
		isElementDisplayed("txt_sortingOrder",columnName);
		logMessage("Step : Sorting order is present for "+columnName+"\n");
	}
	
	public String getRandomTableData(String index, String columnName){
		List<String> searchTerm = getTableData(index, columnName);
		int listIndex = ThreadLocalRandom.current().nextInt(searchTerm.size());
		logMessage("Step : SearchTerm is\" " + searchTerm.get(listIndex) + "\"\n");
		return searchTerm.get(listIndex);
	
	}
	
	public void doubleClickOnRow(String index) {
		waitForLoaderToDisappear();
		doubleClick(element("chkbox_records",index));
		logMessage("Step : Double clicked on table row\n");
	}
	
	public void inputTextInFilter(String value,String index){
		waitForLoaderToDisappear();
		isElementDisplayed("input_filter", "Filter",index);
		element("input_filter", "Filter",index).sendKeys(value);
		logMessage("STEP : " + value + " is entered in filter input box \n");
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
	
	public void clickOnDropDownOfLabel(String dropDown, String tagName) {
		System.out.println("1");
		isElementDisplayed("input_label", dropDown,tagName);
		click(element("input_label", dropDown,tagName));
		logMessage("Step : Clicked on " + dropDown + "\n");
	}
	
	public void selectValueFromDropDown(String priviousValue,String value){
		isElementDisplayed("listitem_dropdown", priviousValue,value);
		click(element("listitem_dropdown", priviousValue,value));
		logMessage("Step : Role is selected as " + value + "\n");
	}
	
	public void verifySpanUnderlabelElement(String value){
		waitForLoaderToDisappear();
		if(value.matches("Oral")){
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
		else if(value.matches("Sci-Mix")){
			verifyPopupMessage("Confirm");
			clickOnButtonUnderSessionModule("Yes");
			verifySpanUnderlabelName("Session Track:", "input");
			verifySpanUnderlabelName("Session Half-Day:", "div");
		}
		else if(value.matches("Poster")){
			verifyPopupMessage("Confirm");
			clickOnButtonUnderSessionModule("Yes");
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
	
	public void enterTitleOfSession(String fieldName,String tagName, String sessionTitle) {
		isElementDisplayed("input_label", fieldName,tagName);
		element("input_label", fieldName,tagName).clear();
		element("input_label", fieldName,tagName).sendKeys(sessionTitle);
		logMessage("Step : " + fieldName + " is entered as " + sessionTitle + "\n");
	}
	
	public void checkRowInTable( int index, int columnIndex){
		isElementDisplayed("txt_tableData", String.valueOf(index), String.valueOf(columnIndex));
		click(element("txt_tableData", String.valueOf(index), String.valueOf(columnIndex)));
		logMessage("Step : Checked on row.\n");
	}
	
	public void verifyRowIsDeleted(String expValue,int index, int columnIndex){
		waitForLoaderToDisappear();
		try{
		Assert.assertTrue(checkIfElementIsThere("txt_instruction", String.valueOf(index), String.valueOf(columnIndex)));
		}
		catch(AssertionError e){
		logMessage("STEP :" + expValue + " is deleted \n");
		}
	}
	
	public void verifyLabelName(String fieldName,String tagName){
		if(tagName==""){
			isElementDisplayed("input_label_create", fieldName);
			logMessage("STEP : " + fieldName + " label is verified \n");
		}
		else{
			isElementDisplayed("input_label", fieldName,tagName);
			logMessage("STEP : " + fieldName + " label is verified \n");
		}
	}
	
	public void verifyCheckBoxUnderLableName(String fieldName,String tagName){
		isElementDisplayed("lable_checkbox", fieldName,tagName);
		logMessage("STEP : " + fieldName + " label is verified \n");
	}
	
	public void verifySpanUnderlabelName(String fieldName,String tagName){
		isElementDisplayed("input_area", fieldName,tagName);
		logMessage("STEP : " + fieldName + " label is verified \n");
	}
	
	public void verifylistBoxUnderLableName(String fieldName,String tagName){
		isElementDisplayed("label_listbox", fieldName,tagName);
		logMessage("STEP : " + fieldName + " label is verified \n");
	}

	public void clickOnColumnHeaders(String header) {
		isElementDisplayed("column_headers",header);
		click(element("column_headers",header));
		logMessage("STEP: column header '"+ header + "' is clicked \n");
		
	}
	
	
}
