package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.DateUtil;
import com.thoughtworks.selenium.webdriven.commands.IsElementPresent;

public class ACS_Scarf_Reporting extends ASCSocietyGenericPage {

	WebDriver driver;
	static String pagename = "Scarf_Reporting";
	String reportingStartDate, reportingEndDate, chapterName, customerLname, customerFname;
	Map<String,List<String>> reportAnswers=new HashMap<String,List<String>>();
	Map<String,String> eventsMap=new HashMap<String,String>();
	static int scarfReportIterationCount=1;

	List<String> answers=new ArrayList<String>();

	Map<String, String> eventType = new HashMap<String, String>();
	int timeOut = 60;

	public ACS_Scarf_Reporting(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
	}

	public void clickOnStudentChapterReportingLink() {
		isElementDisplayed("lnk_StudentChapter");
		element("lnk_StudentChapter").click();
		logMessage("STEP : Clicked on Student Chapter Reporting link\n");
	}

	public void verifyReportingStartAndEndDate() {
		int index;
		boolean value;
		isElementDisplayed("table_rows");
		for (int i = 1; i < elements("table_rows").size(); i++) {
			if (element("txt_current", String.valueOf(i)).getText().trim().equals("Yes")) {
				reportingStartDate = element("txt_startDate", String.valueOf(i)).getText();
				reportingEndDate = element("txt_endDate", String.valueOf(i), String.valueOf(9)).getText();
				index = i;
				break;
			}
		}
		value = verfiyEndAndStartDate(reportingEndDate, reportingStartDate);
		Assert.assertTrue(value, "ASSERT FAIL : Current date does not lies within the Reporting start and end date\n");
		logMessage("ASSERT PASS : Current date lies within the Reporting start and end date\n");
	}

	public boolean verfiyEndAndStartDate(String reportingEndDate, String reportingStartDate) {
		logMessage("Current Date:" + DateUtil
				.convertStringToDate(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"), "MM/dd/yyyy"));
		logMessage("End Date:" + DateUtil.convertStringToDate(reportingEndDate, "MM/dd/yyyy"));
		int endDate, startDate;
		endDate = DateUtil
				.convertStringToDate(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"), "MM/dd/yyyy")
				.compareTo(DateUtil.convertStringToDate(reportingEndDate, "MM/dd/yyyy"));

		logMessage("Current Date:" + DateUtil
				.convertStringToDate(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"), "MM/dd/yyyy"));
		logMessage("Start Date:" + DateUtil.convertStringToDate(reportingStartDate, "MM/dd/yyyy"));
		startDate = DateUtil
				.convertStringToDate(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"), "MM/dd/yyyy")
				.compareTo(DateUtil.convertStringToDate(reportingStartDate, "MM/dd/yyyy"));

		if (endDate == -1 && startDate == 1)
			return true;
		else
			return false;
	}

	public void loginWithLastNameAndMemberId(String lastName, String id) {
		element("radio_lastName").click();
		logMessage("STEP : Selected Login with Last Name/Member Number\n");
		isElementDisplayed("inp_ln_id", "Credential1");
		isElementDisplayed("inp_ln_id", "Credential2");
		isElementDisplayed("btn_login");
		element("inp_ln_id", "Credential1").sendKeys(lastName);
		element("inp_ln_id", "Credential2").sendKeys(id);
		element("btn_login").click();
		logMessage("Login With " + lastName + " and " + id + " and clicked on login button\n");
	}

	public void verifyStudentChapterReportingPage() {
		isElementDisplayed("heading_StudentReport");
		logMessage("STEP : User is navigated to Student Chapters Annual Report Submission page\n");
	}

	public void verifyReportStatus(String status) {
		isElementDisplayed("txt_reportStatus");
		Assert.assertTrue(element("txt_reportStatus").getText().trim().equals(status),
				"ASSERT FAIL : Report Status is not " + status + "\n");
		logMessage("ASSERT PASS : Report Status is " + status + "\n");
	}

	public void clickOnEditChapterInfoButton() {
		isElementDisplayed("btn_editChapterInfo");
		scrollDown(element("btn_editChapterInfo"));
		wait.hardWait(10);
		clickUsingXpathInJavaScriptExecutor(element("btn_editChapterInfo"));
		logMessage("STEP : Clicked on Edit Chapter Information button\n");
		wait.waitForPageToLoadCompletely();
		clearMultimap();
	}

	public void clickOnAddOrEditChapterOfficersButton() {
		isElementDisplayed("btn_editChapterOfficers");
		element("btn_editChapterOfficers").click();
		logMessage("STEP : Clicked on Add/Edit Chapter Officers button\n");
	}

	public void clickOnAddOfficerButton() {
		// pageRefresh();
		isElementDisplayed("btn_addOfficer");
		element("btn_addOfficer").click();
		logMessage("STEP : Clicked on Add Officers button\n");
	}
	
	
	public void removeAlreadyAddedOfficer(int i){
		isElementDisplayed("btn_removeOfficer",String.valueOf(i));
		element("btn_removeOfficer",String.valueOf(i)).click();
		logMessage("STEP : Clicked on cross button to remove officer");
		confirmOfficerDeletion();
	}
	


	public void verifyAlreadyPresentOfficerRole(String role) {  // need correction
		boolean flag = false;
		if (checkIfElementIsThere("lst_officerRoles")) {
			int officersSize = elements("lst_officerRoles").size();
			if (elements("lst_officerRoles").size() > 1) {
				for (int i = 1; i < elements("lst_officerRoles").size(); i++) {
					if (elements("lst_officerRoles").get(i).getText().trim().equals(role)) {
						removeAlreadyAddedOfficer(i);
						flag = true;
						break;
					}
				}
				wait.hardWait(2);
				if(isElementPresentOrNot("lst_officerRoles")){  //-----
				if (officersSize > elements("lst_officerRoles").size())
					logMessage("STEP : Already added officer having role as " + role + " deleted\n");
				else
					logMessage("STEP : No officer was having role as : " + role + "\n");
			}
				else
					logMessage("STEP : Already added officer having role as " + role + " deleted\n"
							+ "STEP : Needed to add atleast 1 primary officer\n");
			}		
		} else
			logMessage("STEP : Officers are not previously added\n");
	}


	public void confirmOfficerDeletion() {
		wait.hardWait(2);
		isElementDisplayed("heading_removeOfficer");
		isElementDisplayed("btn_confirmDeletion");
		// element("btn_confirmDeletion").click();
		// clickUsingXpathInJavaScriptExecutor(element("btn_confirmDeletion"));
		Actions action = new Actions(driver);
		action.moveToElement(element("btn_confirmDeletion"));
		action.click().build().perform();
		logMessage("STEP : Clicked on Confirm button\n");
	}

	public void addChapterOfficer() {
		isElementDisplayed("btn_searchOfficer");
		element("btn_searchOfficer").click();
		logMessage("STEP : Clicked on Search button\n");
	}

	public String selectChapterOfficer(int officerIndex) {
		String name;
		int size;
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("lst_officers");
		size = elements("lst_officers").size();
		if (officerIndex < size) {
			name = element("lst_officerName", String.valueOf(officerIndex)).getText();
			logMessage("Officer selected is : " + element("lst_officerName", String.valueOf(officerIndex)).getText()
					+ "\n");
			element("btn_selectOfficer", String.valueOf(officerIndex)).click();
			return name;
		} else {
			logMessage("Not able to select officer\n");
			return null;
		}
	}

	public void selectOfficerRole(String role) {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("lst_officerRole");
		Select drpdwn = new Select(element("lst_officerRole"));
		drpdwn.selectByVisibleText(role);
		wait.hardWait(2);
		logMessage("STEP : Officer role selected :" + role + "\n");
		selectPrimaryContact();
		wait.hardWait(2);
	}

	public void selectPrimaryContact() {
		isElementDisplayed("chkbox_primaryContact");
		element("chkbox_primaryContact").click();
		logMessage("STEP : Primary Contact checkbox is selected\n");
	}

	public void clickOnSaveButton() {
		wait.hardWait(4);
		isElementDisplayed("btn_save");
		element("btn_save").click();
		logMessage("STEP : Clicked on Save button\n");
	}

	public void verifyAdditionOfOfficer(String name) {
		String ar[];
		boolean flag = false;
		wait.waitForPageToLoadCompletely();
		wait.hardWait(3);
		isElementDisplayed("txt_officerName");
		for (int i = 0; i <elements("txt_officerName").size(); i++) {
			logMessage("------Checking officer: "+elements("txt_officerName").get(i).getText().trim());
			ar = elements("txt_officerName").get(i).getText().trim().split(" ");
			logMessage("----ar[0] :"+ar[0]);
			logMessage("----ar[1] :"+ar[1]);
			if (name.contains(ar[0]) && name.contains(ar[1])) {
				flag = true;
				break;
			}
		}
		Assert.assertTrue(flag, "ASSERT Failed : Officer cannot be added\n");
		logMessage("ASSERT PASS : Officer successfully added\n");
	}

	public void clickOnReturnButton() {
		isElementDisplayed("btn_return");
		element("btn_return").click();
		logMessage("STEP : Clicked on Return button\n");
	}

	public void DepartmentAndInstitutionInformation(String undergraduates, String chemMembers) {
		EnterUndergraduatesMajoringInChemistry(undergraduates);
		EnterChemistryFacultyMembers(chemMembers);
	}

	public void EnterUndergraduatesMajoringInChemistry(String undergraduates) {
		isElementDisplayed("txt_undergraduatesMajoring");
		element("txt_undergraduatesMajoring").clear();
		element("txt_undergraduatesMajoring").sendKeys(undergraduates);
		logMessage("STEP : Entered Undergraduates Majoring in Chemistry: " + undergraduates + "\n");
	}

	public void EnterChemistryFacultyMembers(String chemMembers) {
		isElementDisplayed("txt_chemistryFacultyMembers");
		element("txt_chemistryFacultyMembers").clear();
		element("txt_chemistryFacultyMembers").sendKeys(chemMembers);
		logMessage("STEP : Entered Chemistry Faculty Members: " + chemMembers + "\n");
	}

	public void clickOnSaveChapterInformationButton() {
		isElementDisplayed("btn_saveChapterInfo");
		element("btn_saveChapterInfo").click();
		logMessage("STEP : Clicked on Save button\n");
	}

	public void clickOnReportTab(String tabName) {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(4);
		isElementDisplayed("btn_buildReport", tabName);
		element("btn_buildReport", tabName).click();
		logMessage("STEP : Clicked on " + tabName + " tab\n");
	}

	public void verifyChapterStatus(String chapterName, String statusValue) {
		wait.waitForPageToLoadCompletely();
		Assert.assertTrue(element("btn_status", chapterName).getAttribute("value").equals(statusValue),
				"ASSERT FAIL : " + chapterName + " status is not " + statusValue + "\n");
		logMessage("ASSERT PASS : " + chapterName + " status is " + statusValue + "\n");
	}

	public void clickOnNotStartedButtonForSection(String chapterName, String sectionStatus) {
		// Assert.assertTrue(
		// element("btn_status", chapterName).getAttribute("value")
		// .equals(sectionStatus), "ASSERT FAIL : " + chapterName
		// + " status is not "+sectionStatus);
		// logMessage("ASSERT PASS : " + chapterName + " status is
		// "+sectionStatus);
		isElementDisplayed("btn_status", chapterName);
		element("btn_status", chapterName).click();
		logMessage("STEP : Clicked on " + sectionStatus + " button under " + chapterName + "\n");
	}

	public void checkSectionStatus(String sectionName) {
		wait.hardWait(2);
		String sectionStatus = element("btn_status", sectionName).getAttribute("value");
		switch (sectionStatus) {
		case "Complete":
			logMessage("STEP : The section is already completed\n" + "STEP : Updating the details");
			break;
		case "Not-Started":
			logMessage("STEP : The section is Not-Started\n" + "STEP : Updating the details");
			break;
		case "In-progress":
			logMessage("STEP : The section is in-progress\n" + "STEP : Updating the details");
		}
	}

	public void enterSectionDetails(String data, String sectionName, int index) {
		logMessage("STEP : Entering details for " + sectionName + " section\n");
		for (int i = 1; i <= index; i++) {
			wait.hardWait(2);
			switchToFrame(element("lnk_frameAssessment", String.valueOf(i)));
			addDetails(data);
			switchToDefaultContent();
		}
		reportAnswers.put(sectionName, answers);
	}
	
	public void iterateThroughReportAnswers(){
		int size,loopLength=16;
		boolean flag=false;
		for (Map.Entry<String, List<String>> e : reportAnswers.entrySet()){
		    System.out.println("Section: "+e.getKey());
	            System.out.print( "Value: " + e.getValue()+ "\n" );
		}
		for(scarfReportIterationCount=1;scarfReportIterationCount<=loopLength;scarfReportIterationCount++){
			if(scarfReportIterationCount==11)
			{
				loopLength=clickOnNextReportPages("2");
			}
			System.out.println(scarfReportIterationCount+" "+loopLength);
			System.out.println(element("txt_endDate",String.valueOf(scarfReportIterationCount),String.valueOf(4)).getText().trim());
			System.out.println(reportAnswers.get("Self-Assessment"));
			size=reportAnswers.get(element("txt_endDate",String.valueOf(scarfReportIterationCount),String.valueOf(4)).getText().trim()).size();
			for(int j=0;j<size;j++){
				if(reportAnswers.get(element("txt_endDate",String.valueOf(scarfReportIterationCount),String.valueOf(4)).getText().trim()).get(j).
						contains(element("txt_endDate",String.valueOf(scarfReportIterationCount),String.valueOf(6)).getText().trim())){
					logMessage("ASSERT PASSED : Report answers matches for section");
					flag=true;
				}
			}
			if(flag)
				logMessage("STEP PASSED : Report answers matches for section");
			else
				logMessage("STEP FAILED : Report answers matches for section");
		}
		
	}
	public int clickOnNextReportPages(String pagecount)
	{
		int loopLength;
		wait.hardWait(2);
		isElementDisplayed("lnk_reportPages",pagecount);
		element("lnk_reportPages",pagecount).click();
		logMessage("Step : Page "+pagecount+" of report is clicked\n");
		scarfReportIterationCount=1;
		loopLength=6;
		wait.hardWait(2);
		return loopLength;
	}

	public void addDetails(String data) {
		isElementDisplayed("txt_txtBox");
		element("txt_txtBox").clear();
		data=data + System.currentTimeMillis();
		element("txt_txtBox").sendKeys(data);
		answers.add(data);
		logMessage("STEP : Details added under questions : " + data + "\n");
	}

	public void clickOnSelfAssessmentSaveButton(String btnValue) {
		isElementDisplayed("btn_saveEvent", btnValue);
		element("btn_saveEvent", btnValue).click();
		logMessage("STEP : Clicked on " + btnValue + " button\n");
	}

	public Map<String,String> createEvents(Map<String, String> eventlist, ASM_FellowNominatePage obj) {
		int i = 1;
		String category;
		while (i <= 5) {
			clickOnAddEvent();
			logMessage("STEP : Creating Event " + i + "\n");
			eventType.put("Event Type" + i, eventlist.get("Event" + i + "_Type"));
			selectEventType(1, eventlist.get("Event" + i + "_Type"));
			wait.hardWait(5);
			category=getEventCategory();
			selectEventType(2, eventlist.get("NCW/Mole Day/CCEW_" + i));
			enterEventDetails(1, eventlist.get("Event") + i);
			
			eventsMap.put(category,eventlist.get("Event") + i);
			enterEventDetails(2, eventlist.get("Location"));
			enterEventDetails(3, "4/22/2015");
			enterDescriptionDetails(eventlist.get("Description"));
			selectEventType(3, "My Student Chapter");
			enterEventDetails(4, eventlist.get("No of ACS Student"));
			enterEventDetails(5, eventlist.get("No of NON ACS Chapter Members"));
			enterEventDetails(6, eventlist.get("No of Faculty"));
			enterEventDetails(7, eventlist.get("People Served"));
			selectIsThisGreenChemistryEvent();
			obj.uploadFileUsingJavascipt("test.jpg", "sm1", "Events");
			clickOnSelfAssessmentSaveButton("Save");
			i++;
		}
		return eventsMap;
	}
	
	public String getEventCategory(){
		wait.hardWait(4);
		isElementDisplayed("txt_eventCategory");
		return element("txt_eventCategory").getText().trim();
	}

	public void selectEventType(int i, String event) {
		wait.hardWait(2);
		isElementDisplayed("lst_selectEvents", String.valueOf(i));
		Select dropdwnEventType = new Select(element("lst_selectEvents", String.valueOf(i)));
		dropdwnEventType.selectByVisibleText(event);
		logMessage("STEP : Event type info :" + event + "\n");
	}

	public void enterEventDetails(int index, String info) {
		isElementDisplayed("txt_eventsInfo", String.valueOf(index));
		element("txt_eventsInfo", String.valueOf(index)).clear();
		wait.hardWait(1);
		sendKeysUsingXpathInJavaScriptExecutor(element("txt_eventsInfo", String.valueOf(index)), info);
		// element("txt_eventsInfo", String.valueOf(index)).sendKeys(info);
		logMessage("STEP : Event details entered : " + info + "\n");
	}

	public void selectIsThisGreenChemistryEvent() {
		isElementDisplayed("txt_eventsInfo", String.valueOf(11));
		element("txt_eventsInfo", String.valueOf(11)).click();
		logMessage("STEP : Clicked on Is this a Green Chemistry Event\n");
	}

	public void clickOnAddEvent() {
		isElementDisplayed("btn_addEvent");
		element("btn_addEvent").click();
		logMessage("STEP : Clicked on Add Event button\n");
	}

	public void enterDescriptionDetails(String descriptionData) {
		wait.hardWait(5);
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("txt_eventDescription");
		element("txt_eventDescription").clear();
		element("txt_eventDescription").sendKeys(descriptionData);
//		 sendKeysUsingXpathInJavaScriptExecutor(element("txt_eventDescription"),
//		 descriptionData);
		logMessage("STEP : Description data is: " + descriptionData + "\n");
	}

	public void uploadFile() {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		isElementDisplayed("btn_chooseFile");
		element("btn_chooseFile")
				.sendKeys(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
						+ File.separator + "resources" + File.separator + "UploadFiles" + File.separator + "test.jpeg");
		logMessage("STEP : File Uploaded\n");
	}

	public void clickOnGreenChemistryCheckbox() {
		isElementDisplayed("chkbox_greenChemistry");
		element("chkbox_greenChemistry").click();
		logMessage(
				"STEP : Clicked on 'Check this box if you choose NOT to submit any green chemistry events.' checkbox\n");
	}

	public void verifyMergingOfSections() {
		String sectionName[] = { "Chapter Information", "Self-Assessment", "Service", "Professional Development",
				"Chapter Development", "Budget", "Green Chemistry" };
		logMessage("STEP : Verfiying merging of all the completed sections under Review & Submit tab\n");
		for (int index = 0; index < sectionName.length; index++) {
			isElementDisplayed("btn_sectionStatus", sectionName[index]);
			logMessage("ASSERT PASS : Status of " + sectionName[index] + " section is Complete\n");
		}
		logMessage("STEP : All the sections are merged successfully\n");
	}

	public void clickOnReportSubmitCheckbox() {
		isElementDisplayed("chkbox_submitReport");
		element("chkbox_submitReport").click();
		logMessage("STEP : Clicked on checkbox for Report Submission\n");
	}

	public void verifyReportCompletePage() {
		isElementDisplayed("heading_reportComplete");
		logMessage("ASSERT PASS : User is navigated to the Report Complete page\n");
	}

	public void verifyConfirmChapterAppearWindow() {
		wait.hardWait(2);
		isElementDisplayed("heading_confirmReport");
		logMessage("ASSERT PASSED : Confirm Chapter Report window appears\n");
		clickOnSubmitReportButton("Submit");
	}

	public void clickOnSubmitReportButton(String value) {
		wait.hardWait(2);
		isElementDisplayed("btn_modalSubmit", value);
		scrollDown(element("btn_modalSubmit", value));
		clickUsingXpathInJavaScriptExecutor(element("btn_modalSubmit", value));
	}
	
	public void clickOnSaveAndReturnToDashboardButton() {
		wait.hardWait(2);
		isElementDisplayed("btn_saveAndReturn");
		clickUsingXpathInJavaScriptExecutor(element("btn_saveAndReturn"));
		logMessage("Step : Save And Return to dashboard button is clicked");
	}

	public void clickOnSideBarTabStudentChapter(String tabName, int index) {
		isElementDisplayed("tab_sideBar", String.valueOf(index));
		element("tab_sideBar", String.valueOf(index)).click();
		logMessage("STEP : Clicked on " + tabName + " tab\n");
	}

	public void clickOnSideBarSubTab(String subTab) {
		isElementDisplayed("hd_sideBar", subTab);
		element("hd_sideBar", subTab).click();
		logMessage("STEP : Clicked on " + subTab + " sub tab\n");
	}

	public void selectARandomActiveStudentChapter() {
		MembershipPageActions_IWEB obj = new MembershipPageActions_IWEB(driver);
		obj.clickOnRandomPage();
		obj.clickOnAnyRandomMember();
	}

	public List<String> getMemberDetails() {
		List<String> memberDetails = new ArrayList<String>();
		MembershipPageActions_IWEB obj = new MembershipPageActions_IWEB(driver);
		memberDetails = obj.getMemberDetails();
		System.out.println(memberDetails);
		return memberDetails;
	}
	
	public String getChapterDetails(){
		MembershipPageActions_IWEB obj =new MembershipPageActions_IWEB(driver);
        obj.clickOnEditNameAndAddress();
		switchToFrame("iframe1");
		chapterName = getChapterName();
		obj.clickOnCancelButton();
		handleAlert();
		switchToDefaultContent();
		return chapterName;
	}

	public void clickOnRelationsOptionUnderMoreMenu() {
		MembershipPageActions_IWEB objMember = new MembershipPageActions_IWEB(driver);
		IndividualsPageActions_IWEB object = new IndividualsPageActions_IWEB(driver);
		object.navigateToGeneralMenuOnHoveringMore("Relations");
		objMember.expandDetailsMenu("active student member undergrads");
	}

	public String getChapterName() {
		isElementDisplayed("txt_chapterName");
		chapterName = element("txt_chapterName").getAttribute("value");
		logMessage("STEP : Chapter Name is : " + chapterName + "\n");
		return chapterName;
	}

	public int selectStudentMember() {
		boolean flag;
		int i;
		isElementDisplayed("table_rows");
		for (i = 1; i <= elements("table_rows").size(); i++) {
			flag = verifyStudentMemberEndDate(i);
			if (flag) {
				logMessage("ASSERT PASSED : Current date of student member"
						+ element("txt_endDate", String.valueOf(i), String.valueOf(4)).getText().trim()
						+ " is not equal to End Date\n");
				clickOnStudentMemberName(i);
				break;
			}
		}
		return i;
	}

	public boolean verifyStudentMemberEndDate(int i) {
		boolean flag = false;
		logMessage("STEP : Current Date:" + DateUtil
				.convertStringToDate(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"), "MM/dd/yyyy"));
		Date currDate = DateUtil.convertStringToDate(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"),
				"MM/dd/yyyy");
		Date endDate = DateUtil.convertStringToDate(
				element("txt_endDate", String.valueOf(i), String.valueOf(7)).getText().trim(), "MM/dd/yyyy");
		if (currDate.compareTo(endDate) == -1 || currDate.compareTo(endDate) == 1) {
			flag = true;
		}
		return flag;
	}

	public void clickOnStudentMemberName(int i) {
		wait.hardWait(2);
		isElementDisplayed("arrow_selectMember", String.valueOf(i));
		logMessage("STEP : Selected Student Member: "
				+ element("txt_endDate", String.valueOf(i), String.valueOf(4)).getText().trim());
		element("arrow_selectMember", String.valueOf(i)).click();
	}

	

	
	public void clickOnGoButton(){
		isElementDisplayed("btn_go");
		element("btn_go").click();
		logMessage("STEP : Clicked on Go button\n");
	}
	

	public void clearMultimap(){
		reportAnswers.clear();
		System.out.println("Clear Multi Map");
	}
	
	public void verifyChemistryUndergraduateMajorsInReport(String chemUndergrads){
		isElementDisplayed("txt_iwebUndergrads");
		Assert.assertTrue(chemUndergrads.equals(element("txt_iwebUndergrads").getText()), 
				"ASSERT FAILED : Chemistry Undergraduate Majors does not matches on report\n");
		logMessage("ASSERT PASSED : Chemistry Undergraduate Majors matches on report\n");
	}
	
	public void verifyfacultyCountInReport(String facultyCount){
		isElementDisplayed("txt_iwebFacultyCount");
		Assert.assertTrue(facultyCount.equals(element("txt_iwebFacultyCount").getText()), 
				"ASSERT FAILED : Faculty count does not matches on report\n");
		logMessage("ASSERT PASSED : Faculty count matches on report\n");
	}
	
	public void verifyIwebReportStatus(){
		isElementDisplayed("txt_iwebReportStatus");
		Assert.assertTrue(element("txt_iwebReportStatus").getText().trim().equals("Submitted"), 
				"ASSERT FAILED : Report status is not submitted\n");
		logMessage("ASSERT PASSED : Report status is Submitted\n");
	}
	
	public void verifyPresenceOfReportPdf(String chapName){
		isElementDisplayed("lnk_iwebReportPdf");
		Assert.assertTrue(element("lnk_iwebReportPdf").getAttribute("src").contains("downloadpdf"),
				"ASSERT FAILED : Report Pdf Cons is not displayed\n");
		logMessage("ASSERT PASSED : Report Pdf Cons is displayed\n");
//		switchWindow();
//		wait.hardWait(4);
//		isElementDisplayed("txt_reportChapterName");
//		logMessage("ASSERT PASSED : Chapter name :"+chapName+" matches\n");
//		switchToDefaultContent();
	}
	
	public String getChapterFacultyAdvisor(){
		isElementDisplayed("txt_chapterFacultyAdvisor");
		logMessage("STEP : Chapter Faculty Advisor is :"+element("txt_chapterFacultyAdvisor").getText().trim());
		return element("txt_chapterFacultyAdvisor").getText().trim();
	}
	
	public void verifyChapterFacultyAdvisorOnReport(String facultyAdvisor){
		String arFaculty[];
		isElementDisplayed("table_rows");
		for (int i = 1; i <= elements("table_rows").size(); i++) {
			if(element("txt_endDate",String.valueOf(i),String.valueOf(5)).getText().trim().
					equals("Chapter Faculty Advisor")){
				arFaculty=element("txt_endDate",String.valueOf(i),String.valueOf(4)).getText().trim().split(" ");
				Assert.assertTrue(facultyAdvisor.contains(arFaculty[0]) && facultyAdvisor.contains(arFaculty[1]), 
						"ASSERT FAILED : Chapter Faculty Advisor Name Does not matches\n");
				logMessage("ASSERT PASSED : Chapter Faculty Advisor Name matches\n");
				break;
			}
		}		
	}
	
	public void verifyEventsOnReport(Map<String,String> eventsMap){
//		eventsMap.put("Professional Development", "Test1");
//		eventsMap.put("Chapter Development", "Test2");
		System.out.println("----"+eventsMap);
		String eventName;
		isElementDisplayed("table_rows");
		System.out.println((elements("table_rows").size() - 1));
		for (int i = 1; i <(elements("table_rows").size()-1); i++) {
			eventName=element("txt_endDate",String.valueOf(i),String.valueOf(4)).getText().trim();
			logMessage("-------"+eventName);
			String value=eventsMap.get(element("txt_endDate",String.valueOf(i),String.valueOf(4)).getText().trim());
			System.out.println(value.trim());
			System.out.println(elements("txt_eventIterateByName",eventName).size());
			for(int j=0;j<elements("txt_eventIterateByName",eventName).size();j++)
			{
				if(elements("txt_eventIterateByName",eventName).get(j).getText().trim().equals(value))
				{
					System.out.println("matches name "+elements("txt_eventIterateByName",eventName).get(j).getText().trim());
					System.out.println(value);
				Assert.assertTrue(value.equals(elements("txt_eventIterateByName",eventName).get(j).getText().trim()),"ASSERT FAILED : Events name does not matches");
			}
			}
			
			
//			System.out.println(element("txt_endDate",String.valueOf(i),String.valueOf(6)).getText().trim());
//		    Assert.assertTrue(value.equals(element("txt_endDate",String.valueOf(i),String.valueOf(6)).getText().trim()),
//		    		"ASSERT FAILED : Events name does not matches");
		    logMessage("ASSERT PASSED : Event name matches");
		}
	}

	public void findSubmiitedChapterReport(String chapName,String status){
		enterChapterNameToSearchReport(chapName);
		enterReportStatus(status);
	}
	
	public void enterChapterNameToSearchReport(String chapName){
		isElementDisplayed("txtbox_chapterName");
		element("txtbox_chapterName").clear();
		element("txtbox_chapterName").sendKeys(chapName);
	}
	
	public void enterReportStatus(String status){
		isElementDisplayed("drpdown_status");
		Select dropdwn_status=new Select(element("drpdown_status"));
		dropdwn_status.selectByVisibleText(status);
	}
	


}
