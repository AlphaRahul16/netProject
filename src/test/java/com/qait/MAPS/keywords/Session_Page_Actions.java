package com.qait.MAPS.keywords;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
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
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.itextpdf.text.log.SysoCounter;
import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.CSVFileReaderWriter;
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

	// public void clickButtonToContinueToNextPage(String buttonName) {
	//
	// isElementDisplayed("lnk_selButton", buttonName);
	// element("lnk_selButton", buttonName).click();
	// logMessage("Step : " + buttonName + " button is clicked\n");
	// }

	public void clickButtononLeftNavigationPanel(String buttonName) {
		wait.hardWait(6);
		isElementDisplayed("lnk_selButton", buttonName);
		clickUsingXpathInJavaScriptExecutor(element("lnk_selButton",buttonName));
		//element("lnk_selButton", buttonName).click();
//		wait.hardWait(3);
//		waitForProcessBarToDisappear();
		logMessage("Step : '" + buttonName + "' button is clicked on left navigation panel\n");
	}

	public void verifyTitleForRoles(String title) {
		wait.hardWait(2);
		wait.waitForElementToBeVisible(element("btn_navPanel", title));
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
			Assert.assertTrue(isElementDisplayed("lnk_selButton", text),
					" option " + text + " is not displayed on application\n");
			logMessage("ASSERT PASSED : verified options " + text + " is displayed on page\n");
		}
	}

	public void verifyColumnsOnTypesPage(String[] columnOptions) {
		for (String text : columnOptions) {
			Assert.assertTrue(isElementDisplayed("heading_sectionName", text, "1"),
					" option " + text + " is not displayed on application\n");
			logMessage("ASSERT PASSED : verified options " + text + " is displayed on page\n");
		}
	}

	public String getValueFromProgramsTable() {
		isElementDisplayed("txt_hostDetails", "program_id");
		String programID = elements("txt_hostDetails", "program_id").get(1).getText();
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
		logMessage("STEP: Entered " + value + " in " + field + "field \n");
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
	
	public void verifyButtonsUnderRoomAvailability(String[] options) {
		ScrollPage(0, -100);
		wait.hardWait(5);
		String elementId=verifyCancelButtonOnRoomAvailabilityPage("1", options[0]);
		verifyCancelButtonOnRoomAvailabilityPage("2", options[1]);
		clickOnCancelButton(elementId, "Cancel");
	}
	
	public String verifyCancelButtonOnRoomAvailabilityPage(String index, String expectedValue){		
		isElementDisplayed("btn_cancel",index);
		String id=element("btn_cancel",index).getAttribute("id");
		System.out.println("Id is:"+id);
		Assert.assertEquals(executeJavascriptReturnValue("document.querySelector('#"+id+" button').innerHTML"), expectedValue,"ASSERT FAILED: "+expectedValue+" button is not displayed on page\n");
		logMessage("ASSERT PASSED: "+expectedValue+" button is displayed on page\n");
		return id;
	}
	
	public void clickOnCancelButton(String elementId,String btnName){
		executeJavascript("document.querySelector('#"+elementId+" button').click();");
		logMessage("Step: Clicked on "+btnName+" button\n");
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
		clickUsingXpathInJavaScriptExecutor(element("chkbox_room", fieldname));
		//click(element("chkbox_room", fieldname));
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
		wait.waitForElementToBeClickable(element("btn_sessionTypes", btnName));
		isElementDisplayed("btn_sessionTypes", btnName);
		// clickUsingXpathInJavaScriptExecutor(element("btn_Types", btnName));
		click(element("btn_sessionTypes", btnName));
		logMessage("Step : Clicked on " + btnName + "\n");
	}
	
	public void clickOnButtonUnderGridConfiguration(String btnName) {
		wait.hardWait(2);
		wait.waitForElementToBeClickable(element("btn_sessionTypes", btnName));
		isElementDisplayed("btn_sessionTypes", btnName);
		click(element("btn_sessionTypes", btnName));
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

	public void isPrintSelectedButtonDisplayed(String buttonName) {
		wait.hardWait(2);
		isElementDisplayed("btn_Types", buttonName);
		logMessage("ASSERT PASSED : " + buttonName + " button is displayed on page\n");
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
		wait.hardWait(1);
		isElementDisplayed("txt_tableResult", classLabel, expValue);
		Assert.assertTrue(checkIfElementIsThere("txt_tableResult", classLabel, expValue),
				"ASSERT FAILED: '" + expValue + "' is not added \n");
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

	public void ConvertDataInLowerCaseAndVerifyDataIsSorted(List<String> dataBeforeSorting,
			List<String> dataAfterSorting) {
		int index = 0;
		dataBeforeSorting = convertDataToLowerCase(dataBeforeSorting);
		dataAfterSorting = convertDataToLowerCase(dataAfterSorting);
		System.out.println("-----convert data to lower case before sorting:" + dataBeforeSorting);
		System.out.println("-----convert data to lower case after sorting:" + dataAfterSorting);

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

	public void verifyDataIsSorted(List<String> dataBeforeSorting, List<String> dataAfterSorting) {
		int index = 0;
		// dataBeforeSorting = convertDataToLowerCase(dataBeforeSorting);
		// dataAfterSorting = convertDataToLowerCase(dataAfterSorting);
		System.out.println("----list size:" + dataBeforeSorting.size());
		System.out.println("-----data before sorting:" + dataBeforeSorting);
		System.out.println("-----sorted data:" + dataAfterSorting);

		Collections.sort(dataBeforeSorting);
		System.out.println("-----data after sorting:" + dataBeforeSorting);

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

	public String getRandomRecordFromTable(String columnIndex) {
		wait.hardWait(2);
		isElementDisplayed("txt_totalRecords");
		int randomnumber = generateRandomNumberWithInRange(1, (elements("txt_totalRecords").size()) - 1);
		isElementDisplayed("btn_recordsname", String.valueOf(randomnumber), columnIndex);
		System.out.println("-----random record:"
				+ element("btn_recordsname", String.valueOf(randomnumber), columnIndex).getText().trim());
		return element("btn_recordsname", String.valueOf(randomnumber), columnIndex).getText().trim();
	}

	public String selectaRecordFromTheList(int number, String columnValue) {
		isElementDisplayed("chkbox_records", String.valueOf(number));
		click(element("chkbox_records", String.valueOf(number)));
		logMessage("Step : Record is selected from the list at position " + number);
		return element("btn_recordsname", String.valueOf(number), columnValue).getText().trim();
	}

	public void selectValueForSymposium(String dropdownName, String symposiumType) {
		isElementDisplayed("dropdown_programField", dropdownName);
		clickUsingXpathInJavaScriptExecutor(element("dropdown_programField", dropdownName));
		wait.hardWait(2);
		// click(element("dropdown_programField", dropdownName));
		logMessage("STEP: " + dropdownName + " is clicked \n");
		selectValueFromDropDown(symposiumType);
	}

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

	public String addHostforSymposium(int dropFieldindex, String label) {
		wait.hardWait(3);
		// waitForLoadingImageToDisappear("Loading...");
		isElementDisplayed("txt_hostDetails", label);
		WebElement sourcelocator = null, destinationlocator = null;
		String value = null;
		sourcelocator = elements("txt_hostDetails", label).get(1);
		elements("txt_hostDetails", label).get(1).click();
		value = elements("txt_hostDetails", label).get(1).getText();
		isElementDisplayed("txt_dropField");
		destinationlocator = elements("txt_dropField").get(dropFieldindex);

		dragAndDrop(sourcelocator, destinationlocator);
		wait.hardWait(2);
		waitForLoadingImageToDisappear("Loading...");
		// waitForLoaderToDisappear();
		logMessage("STEP: '" + value + "' is selected \n");
		return value;
	}

	
//	public String getHostDetails(String label) {
//		isElementDisplayed("txt_hostDetails", label);
//		return elements("txt_hostDetails", label).get(1).getText();
//	}

	public void addRoleForHost(String hostRole) {
		isElementDisplayed("txt_hostDetails", "session_host_role");
		click(elements("txt_hostDetails", "session_host_role").get(1));
		selectValueFromDropDown(hostRole);
	}

	public void searchAbstract(String searchBy, String value) {
		isElementDisplayed("inp_programField", searchBy);
		element("inp_programField", searchBy).clear();
		element("inp_programField", searchBy).sendKeys(value);
		logMessage("STEP: Abstract is searched by " + searchBy + " with value " + value + "\n");
	}

	public void clickOnButtonByIndexing(String text, String index) {
		isElementDisplayed("btn_remove", text, index);
		clickUsingXpathInJavaScriptExecutor(element("btn_remove", text, index));
		// click(element("btn_remove", text, index));
		wait.hardWait(2);
		logMessage("STEP: '" + text + "' button is clicked \n");
	}

	public void selectAbstract(String text, int index) {
		isElementDisplayed("txt_hostDetails", text);
		//clickUsingXpathInJavaScriptExecutor(elements("txt_hostDetails", text).get(index));
		click(elements("txt_hostDetails", text).get(index));
		logMessage("STEP: An Abstract is selected with value " + text + " \n");
	}

	public void clickOnSaveButton(String btnName) {
		isElementDisplayed("btn_Types", btnName);
		clickUsingXpathInJavaScriptExecutor(element("btn_Types", btnName));
		// click(element("btn_Types", btnName));
		logMessage("Step : Clicked on " + btnName + " button \n");
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
//		click(element("table_columnDate", "col-session_role_name"));
		clickUsingXpathInJavaScriptExecutor(element("table_columnDate", "col-session_role_name"));
		isElementDisplayed("listItem_SymposiumType","x-combo-list-item", rolevalue);
		click(element("listItem_SymposiumType","x-combo-list-item", rolevalue));
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
		wait.hardWait(4);
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

	public void clickOnDownloadButtonAndVerifyValidFileIsDownloaded(String btnName, String fileName,
			String downloadedFilePath) {
		// String file_name = fileName + File.separator + ".csv";
		CSVFileReaderWriter._deleteExistingCSVFile(downloadedFilePath, fileName);
		clickOnButtonUnderSessioning(btnName);

		wait.hardWait(5);
		verifyValidFileIsDownloaded(downloadedFilePath + File.separator + fileName + ".csv");

	}

	public void verifyAddedCriteriaIsDeleted(String critiria) {

		Assert.assertFalse(checkIfElementIsThere("select_role", critiria));
		logMessage("ASSERT PASSED: '" + critiria + "' is deleted \n");

	}

	public void importValidFile(List<String> data, String downloadedFilePath) {
		CSVFileReaderWriter.writeDataInAlreadyExistingCSVFile(downloadedFilePath, data);
		wait.hardWait(5);
		importFileWithValidData(downloadedFilePath);

	}

	public void importFileWithValidData(String downloadedFilePath) {
		isElementDisplayed("inp_fileupload", "Please upload your file:", "1");
		String value = getValUsingXpathInJavaScriptExecutor(element("inp_fileupload", "Please upload your file:", "1"));
		System.out.println("value of inp file path:::::" + value);
		File sourceFile = new File(downloadedFilePath);
		clickUsingXpathInJavaScriptExecutor(element("btn_Types", "Browse..."));
		performClickByActionBuilder(element("btn_Types", "Browse..."));
		wait.hardWait(3);
		importFileUsingRobot(downloadedFilePath);
		wait.hardWait(5);
		logMessage("Step: File is imported after adding valid data \n");

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
		// click(element("drpdown_Symposium", label));
		logMessage("STEP: '" + label + "' is clicked \n");
		// wait.hardWait(2);
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

	public String rightClickOnSession() {
		wait.hardWait(2);
		isElementDisplayed("txt_session");
		String session_name=element("txt_session").getText().replace("(", "").replace(")", "");
		rightClick(element("txt_session"));		
		logMessage("STEP: Right clicked on session "+session_name+" \n");
		return session_name.trim();
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
		selectSymposiaIfNotSelecetd();
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

	public void selectSymposiaIfNotSelecetd() {
		System.out.println("-------value selected is:"
				+ getSelectedTextFromDropDown(element("select_presentationType", "symposia_title")));
		if (getSelectedTextFromDropDown(element("select_presentationType", "symposia_title"))
				.equalsIgnoreCase("None Selected")) {
			element("select_presentationType", "symposia_title").click();
			isElementDisplayed("select_symposiumType");
			// selectDropDownValue(element("select_symposiumType"), 2);
			click(element("select_symposiumType"));
		}
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
		wait.hardWait(2);
		isElementDisplayed("column_headers", columnName);
		executeJavascript("document.querySelector('div[qtip=\"Edit Abstract\"]').style.width=\"100px\"");
		logMessage("Step: " + columnName + " column is expanded\n");
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
			clickOnButtonByIndexing("Delete", "1");
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
		logMessage("Step : SearchTerm is fetched as ' " + searchTerm.get(listIndex) + "' \n");
		return searchTerm.get(listIndex);

	}

	public void doubleClickOnRow(String index) {
		doubleClick(element("chkbox_records", index));
		logMessage("Step : Double clicked on table row\n");
		wait.hardWait(2);
	}

	public void inputTextInFilter(String label, String value, String index) {
		wait.hardWait(2);
		isElementDisplayed("input_filter", label, index);
		element("input_filter", label, index).clear();
		element("input_filter", label, index).sendKeys(value);
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

	public void verifyOwnerIsDeleted(String colName, String expValue) {
		waitForLoaderToDisappear();
		int size = elements("txt_tableResult", colName, expValue).size();
		Assert.assertTrue(size <= 1);
		logMessage("STEP :" + expValue + " is deleted from " + colName + "\n");
	}

	public void selectValueFromDropDown(String priviousValue, String value) {
		isElementDisplayed("listitem_dropdown", priviousValue, value);
		click(element("listitem_dropdown", priviousValue, value));
		logMessage("Step : Role is selected as " + value + "\n");
	}

	public void verifyRowIsDeleted(String expValue, int index, int columnIndex) {
		waitForLoaderToDisappear();
		Assert.assertFalse(	checkIfElementIsThere("txt_instruction", String.valueOf(index), String.valueOf(columnIndex)),
				"ASSERT FAILED :" + expValue + " is not deleted \n");
		logMessage("ASSERT PASSED :" + expValue + " is deleted \n");

	}

	public void verifyLabelName(String text) {
		isElementDisplayed("txt_label", text);
		logMessage("STEP : " + text + " label is verified \n");
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
		wait.hardWait(4);
	}

	public void clickOnSessionBuilderTab(String tabName) {
		isElementDisplayed("btn_navPanel", tabName);
		try {
			wait.resetExplicitTimeout(4);
			wait.resetImplicitTimeout(4);
			elements("btn_navPanel", tabName).get(0).click();
		} catch (TimeoutException te) {
			clickUsingXpathInJavaScriptExecutor(element("btn_navPanel", tabName));
		}
		wait.resetExplicitTimeout(timeOut);
		wait.resetImplicitTimeout(timeOut);
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
		int totalsymposia = Integer
				.parseInt((elements("txt_SchedulerGrid", "my-paging-text").get(3).getText().split(" "))[1].trim());
		System.out.println(totalsymposia);
		Assert.assertTrue((symposiasize > 1 && symposiasize <= totalsymposia),
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
		
		Assert.assertTrue(checkIfElementIsThere("txt_controlId", classlbl,expValue));
		logMessage("ASSERT PASSED: '" + expValue + "' is present \n");
	}
	public void verifyAddedAbstractsInTable(String classlbl, String expValue,int expSize) {
		isElementDisplayed("txt_controlId", classlbl,expValue);
		Assert.assertTrue(elements("txt_controlId", classlbl,expValue).size()==expSize,
				"ASSERT FAILED: " + expValue + " is not present \n");
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

	public void verifyWithdrawRow(String fontText, String text) {
		waitForLoaderToDisappear();
		Assert.assertTrue(checkIfElementIsThere("row_withdraw", fontText, text));
		logMessage("ASSERT PASSED: " + text + " with " + fontText + " is displayed \n");
	}

	public void verifyAvailableOptionsOnSaveGridPopUpUnderAbstracts(String fieldname) {
		isElementDisplayed("inp_fileupload", fieldname, "1");
		logMessage("ASSERT PASSED : input box is available for " + fieldname);

	}

	public void rightClickOnSessionList(String recordnumber) {
		rightClick(element("chkbox_records", recordnumber));
	}

	public String getHostColoumData(String hostname, String index) {
		return element("input_editableColumnindex", hostname, index).getText().trim();
	}

	public String getTextformTable(int index, int columnIndex) {
		isElementDisplayed("txt_tableData", String.valueOf(index), String.valueOf(columnIndex));
		return elements("txt_tableData", String.valueOf(index), String.valueOf(columnIndex)).get(0).getText();
	}

//	public void enterTextInPopUpInput(String labelName, String index, String value) {
//		isElementDisplayed("input_popup", labelName, index);
//		element("input_popup", labelName, index).clear();
//		element("input_popup", labelName, index).sendKeys(value);
//	}

	public String subtractValue(String value, int toBeSubtrcated) {
		int value1 = Integer.parseInt(value);
		int subtrectrdValue = value1 - toBeSubtrcated;
		return String.valueOf(subtrectrdValue);
	}

	public void isFinalIDAllocatedToAbstract(String privousFinalId) {
		String value = " ";
		if (!privousFinalId.equals(value)) {
			verifyPopupMessage("Confirm");
			clickOnButtonByIndexing("Yes", "1");
		}
	}

	public void clickOnLinkText(String text, String linkText) {
		isElementDisplayed("btn_lnkTxt", text, linkText);
		element("btn_lnkTxt", text, linkText).click();
		logMessage("Step : " + linkText + " button is clicked\n");
	}

	public void verifyLableFormateAfterEdit(String text) {
		Assert.assertTrue(checkIfElementIsThere("txt_label", text));
		logMessage("Step : " + text + " is successfully edited\n");
	}

	public void enterValuesInAssignDurationPage(String label, String value) {
		isElementDisplayed("inp_assignDuration", label);
		element("inp_assignDuration", label).clear();
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
		String value = element("txt_instruction", text).getText().trim();
		logMessage("[info]:: '" + value + " is selected \n");
		return value;
	}

	public void verifyTimeDurationGetUpdated(String newtimeDurationLabel, String timeDurationLabel) {
		Assert.assertFalse(timeDurationLabel.contentEquals(newtimeDurationLabel));
		logMessage("ASSERT PASSED: Current duration is updated to " + newtimeDurationLabel + " from "
				+ timeDurationLabel + "\n");

	}

	public void enterValuesInAssignAbstractFinalID(String finalId, String hashtag) {
		isElementDisplayed("inp_assignDuration", "New");
		elements("inp_assignDuration", "New").get(0).clear();
		elements("inp_assignDuration", "New").get(0).sendKeys(finalId);
		logMessage("STEP: " + finalId + " is entered as FinalD Name \n");
		elements("inp_assignDuration", "New").get(1).clear();
		elements("inp_assignDuration", "New").get(1).sendKeys(hashtag);
		logMessage("STEP: " + hashtag + " is entered as hash tag \n");

	}

	public void clickCheckboxOnAbstractAssignPage(String[] abstractTypes) {
		for (String type : abstractTypes) {
			clickCheckboxOnSaveGridConfiguration(type);
		}
	}

	public void verifyFieldsArePrefilledWithSavedDetails(String exp_finalID, String exp_hashtag) {
		isElementDisplayed("inp_assignDuration", "New");
		String actual_finalID = getValUsingXpathInJavaScriptExecutor(elements("inp_assignDuration", "New").get(0));
		Assert.assertEquals(actual_finalID.trim(), exp_finalID,
				"ASSERT FAILED: final id should be " + exp_finalID + " but found " + actual_finalID);
		logMessage("ASSERT PASSED: final id should be " + exp_finalID + " but found " + actual_finalID);
		String actual_hashtag = getValUsingXpathInJavaScriptExecutor(elements("inp_assignDuration", "New").get(1));
		Assert.assertEquals(actual_hashtag.trim(), exp_hashtag,
				"ASSERT FAILED: final id should be " + exp_hashtag + " but found " + actual_hashtag);

	}

	public void verifyFinalIdIsDeleted(String value) {
		Assert.assertFalse(checkIfElementIsThere("txt_label", value),
				"ASSERT FAILED : " + value + " is not deleted \n");
		logMessage("ASSERT PASSED : " + value + " is deleted \n");
	}

	public String clickOnPencilIconCloumn(String colHeader, String expVal,String newVal) {
		isElementDisplayed("txt_tableResult", colHeader, expVal);
		doubleClick(element("txt_tableResult", colHeader, expVal));
		String updatedVal = newVal + System.currentTimeMillis();
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
		Iterator<?> mapIterator = eventInfo.entrySet().iterator();
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
		isElementDisplayed("lnk_selButton", btnName);
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

	public void verifySessionIsdeletedFromScheduleSession(String sessionName) {
		Assert.assertFalse(checkIfElementIsThere("btn_navPanel", sessionName));
		logMessage("ASSERT PASSED: " + sessionName + " is deleted \n");
	}

	public void verifyApplicationPrintsReportsTable() throws AWTException {
		if (isBrowser("chrome")) {
			changeWindow(1);
			isPrintSelectedButtonDisplayed("Print");
			clickOnButtonUnderSessioning("Cancel");
			changeWindow(0);
		} else if (isBrowser("firefox")) {
			wait.hardWait(4);
			Robot r = new Robot();
			r.keyPress(KeyEvent.VK_ESCAPE);
		}

	}

	public List<String> getDataForImportedFile(Map<String, Object> yamlValues) {
		List<String> data = new ArrayList<String>();
		String value = null;
		Set<String> keys = yamlValues.keySet();
		for (Iterator<String> i = keys.iterator(); i.hasNext();) {
			String key = (String) i.next();
			if (key.equals("Session_Title")) {
				value = (String) yamlValues.get(key) + System.currentTimeMillis();
			} else {
				value = (String) yamlValues.get(key);
			}
			System.out.println(key + " = " + value);
			data.add(value);
		}
		return data;

	}

	public List<String> getDisplayedColumnName() {
		List<String> colName = new ArrayList<String>();
		wait.hardWait(3);
		// System.out.println(element("txt_colName").getText());
		int totalCol = elements("txt_displayedCol").size();
		System.out.println("totalCol******" + totalCol);
		for (int i = 0; i < totalCol; i++) {
			colName.add(elements("txt_displayedCol").get(i).getText());
			System.out.println("col name::::" + elements("txt_displayedCol").get(i).getText());
		}
		return colName;
	}

	public void verifyExportedFileWithColumns(String csvFile, List<String> colHeader) {
		boolean flag = true;
		String dataFromDownloadedFile = DataProvider.csvReaderRowSpecific(csvFile, "No", "1");
		System.out.println("dataFromDownloadedFile:   " + dataFromDownloadedFile);
		for (String column_header : colHeader) {
			if (column_header.contains("Abbrev")) {
				column_header = "Abbreviation";
			} else if (column_header.contains("# of Assigned Abstracts")) {
				column_header = "Num Abstracts Assigned";
			}
			System.out.println("##### column_header:::" + column_header);

			Assert.assertTrue(dataFromDownloadedFile.trim().contains(column_header.trim()),
					"ASSERT FAILED: " + column_header + " is not present \n");
		}
		logMessage("ASSERT PASSED: All coloumns are present \n");

	}

	public List<String> getAllColumnName() {
		List<String> colName = new ArrayList<String>();
		int totalCol = elements("txt_AllColName").size();
		for (int i = 6; i < totalCol; i++) {
			colName.add(elements("txt_AllColName").get(i).getText());
		}
		return colName;
	}

	public void verifyPopUpText(String msg) {
		Assert.assertTrue(checkIfElementIsThere("txt_popUpmsg", msg));
		logMessage("Step: '" + msg + "' is displayed \n");
	}

	public void enterColumnFilterData(String filterdata, String index) {
		isElementDisplayed("txt_filterData", index);
		element("txt_filterData", index).sendKeys(filterdata);
		logMessage("Step: Filter data is entered as " + filterdata + "\n");
	}

	public void enterFilterData(String filterdata, String index) {
		enterColumnFilterData(filterdata, index);
		wait.hardWait(4);
	}

	public void verifyAbstractGridIsDisplayed(String[] abstractColumns) {
		for (String columnName : abstractColumns) {
			isElementDisplayed("btn_navPanel", columnName);
			logMessage("ASSERT PASSED: Abstract grid column " + columnName + " is displayed\n");
		}
	}

	public void selectUnassignedAbstracts() {
		isElementDisplayed("inp_sessionType", "unassigned_abstracts");
		element("inp_sessionType", "unassigned_abstracts").click();
		logMessage("Step: check the unassigned abstracts \n");

	}

	public String selectAbtractByStatus(String status) {
		// isElementDisplayed("chkbox_column", status,"5");
		String cID = elements("txt_recordTitle", status, "3").get(0).getText();
		System.out.println("cid::" + cID + "\n");

		elements("chkbox_column", status, "5").get(0).click();

		logMessage("STEP: Abstract '" + cID + "' is selected \n");
		return cID;

	}

	public void verifyDropDownOnSessionAdmin(String label) {

		isElementDisplayed("inp_sessionType", "unassigned_abstracts");
		element("inp_sessionType", "unassigned_abstracts").click();
		logMessage("Step: check the unassigned abstracts \n");
	}

	public void clickClosePopUpButton(String buttonname) {
		isElementDisplayed("btn_close", buttonname);
		click(element("btn_close", buttonname));
		logMessage("Step: Close pop up button is clicked\n");
	}

	public void verifyUserDetailsInFilterResults(String filterindex) {
		Assert.assertTrue(getHostColoumData("session_host_email", "1")
				.equals(getValUsingXpathInJavaScriptExecutor(element("input_filter", "Search", filterindex))));
		logMessage("ASSERT PASSED : Filtered result is succesfully verified as "
				+ getHostColoumData("session_host_email", "1"));
	}

	public void verifyAbstractIsAddedInCurrentAssignedAbstract(String abstractTitle) {
		Assert.assertTrue(checkIfElementIsThere("txt_EditLink", abstractTitle),
				"Assert Fail : " + abstractTitle + " is not added in Current Assigned Abstract \n");
		logMessage("ASSERT PASSED : " + abstractTitle + " is added in Current Assigned Abstract \n");

	}

	public void handlePopUpIfAppears(String msg, String buttonName) {
		if (checkIfElementIsThere("btn_navPanel", msg)) {
			clickOnButtonByIndexing(buttonName, "1");
		} else {
			logMessage("[info]: No popup appears \n");
		}
	}

	public void verifyCheckBoxOnCreateSessionPage(String text) {
		Assert.assertTrue(checkIfElementIsThere("chkbox_room", text),
				"Assert Failed: " + text + " check box is not present \n");
		logMessage("ASSERT PASSED: '" + text + "' check box is present \n");

	}

	public void verifyOptionsOfSessionTypeDropDown(String sessionType, String chkboxOralAndPoster,
			String[] textBoxForOralAndPoster, String[] selectionLabelOralAndPoster, String[] drpdownOralAndPoster,
			String chkboxSciMix, String drpdownSciMix) {
		switch (sessionType) {
		case "Oral":
		case "Poster":
			verifyChkBoxOnCreateSessionPage(chkboxOralAndPoster);
			for (String lblName : textBoxForOralAndPoster) {
				verifyTextboxOnCreateSession(lblName);
			}
			for (String lblName : selectionLabelOralAndPoster) {
				verifySelectionOptionOnCreateSession(lblName);
			}
			for (String lblName : drpdownOralAndPoster) {
				verifyfrpdownOptionOnCreateSession(lblName);
			}
			break;

		case "Sci-Mix":
			verifyfrpdownOptionOnCreateSession(drpdownSciMix);
			verifySelectionOptionOnCreateSession(chkboxSciMix);
			break;

		case "Default":
			logMessage("Step : session type " + sessionType + " is not present \n");
		}

	}

	public void verifyfrpdownOptionOnCreateSession(String lblName) {
		Assert.assertTrue(checkIfElementIsThere("drpDown_sympType", lblName),
				"Assert Failed: " + lblName + " check box is not present \n");
		logMessage("ASSERT PASSED: '" + lblName + "' check box is present \n");

	}

	public void verifySelectionOptionOnCreateSession(String text) {
		Assert.assertTrue(elements("sel_theme", text).size() > 0);
		logMessage("ASSERT PASSED: '" + text + "' is present \n");
	}

	public void verifyTextboxOnCreateSession(String lblName) {
		Assert.assertTrue(checkIfElementIsThere("inp_FnclCosponsor", lblName),
				"Assert Failed: " + lblName + " check box is not present \n");
		logMessage("ASSERT PASSED: '" + lblName + "' check box is present \n");
	}

	public void verifyChkBoxOnCreateSessionPage(String text) {
		Assert.assertTrue(checkIfElementIsThere("chkbox_SciMix", text),
				"Assert Failed: " + text + " check box is not present \n");
		logMessage("ASSERT PASSED: '" + text + "' check box is present \n");

	}

	public void verifyTextAreaOnCreateSessionPage(String[] fieldsOnCreateSession) {
		for (String id : fieldsOnCreateSession) {
			verifyTextAreaOnCreateSessionPage(id);
		}
	}

	public void verifyTextAreaOnCreateSessionPage(String id) {
		Assert.assertTrue(checkIfElementIsThere("inp_programField", id), "ASSERT FAILED: " + id + " is not present \n");
		logMessage("ASSERT PASSED: " + id + " is present \n");
	}

	public void verifyLabelName(String[] labelOnCreateSession) {
		for (String lblText : labelOnCreateSession) {
			verifyLabelName(lblText);
		}
	}

	public void verifyCheckBoxOnCreateSessionPage(String[] chkboxOnCreateSession) {
		for (String lblText : chkboxOnCreateSession) {
			verifyCheckBoxOnCreateSessionPage(lblText);
		}
	}

	public void verifyFieldForSessionTypeDropDown(String[] sessionTypes, String chkboxOralAndPoster,
			String[] textBoxForOralAndPoster, String[] selectionLabelOralAndPoster, String[] drpdownOralAndPoster,
			String chkboxSciMix, String drpdownSciMix) {
		for (String sessiontype : sessionTypes) {
			selectValueForSymposium("session_type", sessiontype);
			System.out.println("############sessiontype##########" + sessiontype);
			handlePopUpIfAppears("Confirm", "Yes");
			verifyOptionsOfSessionTypeDropDown(sessiontype, chkboxOralAndPoster, textBoxForOralAndPoster,
					selectionLabelOralAndPoster, drpdownOralAndPoster, chkboxSciMix, drpdownSciMix);
		}

	}

	public void verifyOwnerIsAddedInCurrentOwner(String colName, String value, int expSize) {
		wait.hardWait(2);
		Assert.assertTrue(elements("txt_tableResult", colName, value).size() == expSize,
				"ASSERT FAILED: " + value + " is not added \n");
		logMessage("ASSERT PASSED: " + value + " is added in Current Owner\n");

	}

	public void verifySessionOwnerIsAdded(String text, String value) {
		Assert.assertTrue(checkIfElementIsThere("txt_sessionOwner",text,value));
		logMessage("ASSERT PASSED: "+value+" is displayed under "+text+" \n");
		
	}

	public void addHostforSessionBulider(String colname, String host_email, String locatorindex) {
		wait.hardWait(3);
		isElementDisplayed("txt_tableResult", colname, host_email);
		WebElement sourcelocator = element("txt_tableResult", colname, host_email);
		clickUsingXpathInJavaScriptExecutor(element("txt_tableResult", colname, host_email));
		//element("txt_tableResult", colname, host_email).click();
		isElementDisplayed("table_dropLocation", "2");
		WebElement destinationlocator = element("table_dropLocation", locatorindex);
		dragAndDrop(sourcelocator, destinationlocator);
		wait.hardWait(2);
		waitForLoadingImageToDisappear("Loading...");
		logMessage("STEP: '" + host_email + "' is selected as Host \n");

	}

	public void clickCheckboxOfaRecord(String host_email) {
		wait.hardWait(2);
		isElementDisplayed("chkbox_email", host_email);
		try {
			element("chkbox_email", host_email).click();
		} catch (WebDriverException e) {
			clickUsingXpathInJavaScriptExecutor(element("chkbox_email", host_email));
		}
		logMessage("Step: '" + host_email + "' is selected \n");
	}	
	
	public void enterValueInAddPlaceholderpage(String sessionTitle, String sessiondesc) {
		enterValuesForProgram("title", sessionTitle);
		enterValuesForProgram("session_event_desc", sessiondesc);
		
	}

	public void checkWhetherHostIsAlreadyPresent(String classlbl, String host_name) {
		if (checkIfElementIsThere("txt_tableResult", classlbl, classlbl)) {
			clickParticularRecordFromList(host_name, "1");
			clickOnButtonByIndexing("Delete Hosts", "1");
			verifyPopupMessage("Are you sure you want to delete the selected session hosts?");
			clickOnButtonByIndexing("Yes", "1");
			verifyDataIsDeleted(classlbl,host_name);

		} else {
			logMessage("[Info::] '" + host_name + "' is not present \n");
		}
	}

	public void verifyAbstractIsDeleted(String classlbl, String expValue) {
		waitForLoadingImageToDisappear("deleteing data");
		Assert.assertFalse(checkIfElementIsThere("lnk_ControlId", expValue,classlbl),
				"Assert Failed : " + expValue + " is not deleted in Current Assigned Abstract \n");
		logMessage("ASSERT PASSED : " + expValue + " is deleted in Current Assigned Abstract \n");
		
	}
	public void verifyAbstractIsAddedInCurrentlyAssignedAbstracts(String classlbl, String expValue) {
		isElementDisplayed("lnk_ControlId", expValue,classlbl);
		Assert.assertTrue(checkIfElementIsThere("lnk_ControlId", expValue,classlbl),
				"Assert Failed : " + expValue + " is not deleted in Current Assigned Abstract \n");
		logMessage("ASSERT PASSED : " + expValue + " is deleted in Current Assigned Abstract \n");
		
	}

	public void clickOnAssignAbstract() {
		wait.hardWait(1);
		if (checkIfElementIsThere("btn_saveAndEdit", "Assign Hosts", "1")) {
			clickOnSaveAndEditButton("Assign Hosts", 1);
		}
		isElementDisplayed("btn_saveAndEdit", "Assign Abstracts","1");
		clickOnSaveAndEditButton("Assign Abstracts", 1);
		logMessage("STEP: clicked on 'Assign Abstracts' button \n");
	}	

}
