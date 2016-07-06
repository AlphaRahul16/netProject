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
import java.util.concurrent.Callable;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.ConfigPropertyReader;
import com.qait.automation.utils.DateUtil;
import com.sun.jna.platform.mac.Carbon.EventTypeSpec;
import com.thoughtworks.selenium.webdriven.commands.IsElementPresent;

public class ACS_Scarf_Reporting extends ASCSocietyGenericPage {
	List<String> answers;
	WebDriver driver;
	static String pagename = "Scarf_Reporting";
	String reportingStartDate, reportingEndDate, chapterName, customerLname, customerFname;
	Map<String,List<String>> reportAnswers=new HashMap<String,List<String>>();
	Map<String,List<String>> eventsMap=new HashMap<String,List<String>>();
	static int scarfReportIterationCount=1;
	List<String>list=new ArrayList<String>();
	String arrayData[]={"Event","Location","Date","No of ACS Student","No of NON ACS Chapter Members",
			"No of Faculty","People Served"};
	String titleArray[]={"Events","Location","Date","Number of ACS Student","Number of Non-ACS Chapter Members",
			"Number of Faculty","Number of People Served"};


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

	public void loginWithLastNameAndMemberId(String lastName, String id) {
		isElementDisplayed("radio_lastName");
		element("radio_lastName").click();
		logMessage("STEP : Selected Login with Last Name/Member Number\n");
		enterMemberLastName_Id(lastName,"Credential1","Last Name");
		enterMemberLastName_Id(id,"Credential2","Member Number");
		clickOnLoginButton();
		logMessage("Login With " + lastName + " and " + id ); 
	}
	
	public void enterMemberLastName_Id(String lastName,String credential,String field){
		isElementDisplayed("inp_ln_id", credential);
		element("inp_ln_id", credential).sendKeys(lastName);
		logMessage("STEP : Enter "+lastName+" in "+field);
	}
	
	public void clickOnLoginButton(){
		isElementDisplayed("btn_login");
		if(ConfigPropertyReader.getProperty("browser").equalsIgnoreCase("chrome")){
			System.out.println("in else");
			clickUsingXpathInJavaScriptExecutor(element("btn_login"));
		}
		else{
		element("btn_login").click();
        logMessage("STEP : Clicked on login button\n");
		}
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
		logMessage("STEP : Clicked on cross link to remove officer");
		confirmOfficerDeletion();
	}
	
	public boolean verifyAlreadyPresentOfficerRole(String role) { 
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
			return true;
		} else{
			logMessage("STEP : Officers are not previously added\n");
			return false;
		}
	}

	public void confirmOfficerDeletion() {
		wait.hardWait(2);
		isElementDisplayed("heading_removeOfficer");
		isElementDisplayed("btn_confirmDeletion");
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
			logMessage("STEP : Officer "+element("lst_officerName", String.valueOf(officerIndex)).getText()+" is selected" + "\n");
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
		logMessage("STEP : Officer role " + role +" is selected\n");
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
		int i;
		boolean flag = false;
		wait.waitForPageToLoadCompletely();
		wait.hardWait(3);
		isElementDisplayed("txt_officerName");
		for ( i = 0; i <elements("txt_officerName").size(); i++) {
			ar = elements("txt_officerName").get(i).getText().trim().split(" ");
			if (name.contains(ar[0]) && name.contains(ar[1])) {
				flag = true;
				break;
			}
		}
		Assert.assertTrue(flag, "ASSERT Failed : Officer "+elements("txt_officerName").get(i).getText().trim()+" cannot be added\n");
		logMessage("ASSERT PASS : Officer "+elements("txt_officerName").get(i).getText().trim()+" is successfully added\n");
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
		logMessage("STEP : Entered data as "+undergraduates+" for Undergraduates Majoring in Chemistry\n");
	}

	public void EnterChemistryFacultyMembers(String chemMembers) {
		isElementDisplayed("txt_chemistryFacultyMembers");
		element("txt_chemistryFacultyMembers").clear();
		element("txt_chemistryFacultyMembers").sendKeys(chemMembers);
		logMessage("STEP : Entered data as "+chemMembers+" for Chemistry Faculty Members\n");
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
		logMessage("ASSERT PASS : " + chapterName + " section status is " + statusValue + "\n");
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
		logMessage("STEP : Clicked on " + sectionStatus + " button under " + chapterName + "section\n");
	}

	public void checkSectionStatus(String sectionName) {
		wait.hardWait(2);
		String sectionStatus = element("btn_status", sectionName).getAttribute("value");
		switch (sectionStatus) {
		case "Complete":
			logMessage("STEP : The section status is already completed\n");
			break;
		case "Not Started":
			logMessage("STEP : The section status is Not-Started\n");
			break;
		case "In-progress":
			logMessage("STEP : The section status is in-progress\n");
		}
	}

	public void enterSectionDetails(String data, String sectionName, int index) {
		answers=new ArrayList<String>();
		logMessage("STEP : Entering details for " + sectionName + " section\n");
		wait.waitForPageToLoadCompletely();
		
		for (int i = 1; i <= index; i++) {
			wait.hardWait(3);
			switchToFrame(element("lnk_frameAssessment", String.valueOf(i)));
			addDetails(data,answers);
			switchToDefaultContent();
		}
		reportAnswers.put(sectionName, answers);
	}
	
	public void iterateThroughReportAnswers2(){
		int size,loopLength=16;
		boolean flag=false;
		for (Map.Entry<String, List<String>> e : reportAnswers.entrySet()){
		    System.out.println("Section: "+e.getKey());
	            System.out.print( "Value: " + e.getValue()+ "\n" );
		}
		logMessage("STEP : Verifying Section answers for questions in the report");
		for(scarfReportIterationCount=1;scarfReportIterationCount<=loopLength;scarfReportIterationCount++){
			if(scarfReportIterationCount==11)
			{
				loopLength=clickOnNextReportPages("2");
			}
			System.out.println(scarfReportIterationCount+" "+loopLength);
			String sectionName=element("txt_endDate",String.valueOf(scarfReportIterationCount),String.valueOf(4)).getText().trim();
			String value=element("txt_endDate",String.valueOf(scarfReportIterationCount),String.valueOf(6)).getText().trim();
			System.out.println(element("txt_endDate",String.valueOf(scarfReportIterationCount),String.valueOf(4)).getText().trim());
			size=reportAnswers.get(element("txt_endDate",String.valueOf(scarfReportIterationCount),String.valueOf(4)).getText().trim()).size();
			for(int j=0;j<size;j++){
				if(reportAnswers.get(element("txt_endDate",String.valueOf(scarfReportIterationCount),String.valueOf(4)).getText().trim()).get(j).
						contains(element("txt_endDate",String.valueOf(scarfReportIterationCount),String.valueOf(6)).getText().trim())){
					flag=true;
				}
			}
			Assert.assertTrue(flag,"ASSERT FAILED : Report answers does not matches for section "+sectionName+" having value as :"+value);
				logMessage("STEP PASSED : Report answers matches for section "+sectionName+" having value as :"+value);
		}		
	}
	
	public void iterateThroughReportAnswers(){
		int size,pageNos=0;
		boolean flag=false;
		for (Map.Entry<String, List<String>> e : reportAnswers.entrySet()){
		    System.out.println("Section: "+e.getKey());
	            System.out.print( "Value: " + e.getValue()+ "\n" );
		}
		logMessage("STEP : Verifying Section answers for questions in the report");
		do{	
			if(pageNos!=0){
				System.out.println("in else");
				wait.hardWait(1);
				clickUsingXpathInJavaScriptExecutor(elements("list_pageNos").get(pageNos));
				wait.waitForPageToLoadCompletely();
				wait.hardWait(2);
			}
		for(scarfReportIterationCount=1;scarfReportIterationCount<=elements("list_answers").size();scarfReportIterationCount++){
			System.out.println(scarfReportIterationCount);
			String sectionName=element("txt_endDate",String.valueOf(scarfReportIterationCount),String.valueOf(4)).getText().trim();
			String value=element("txt_endDate",String.valueOf(scarfReportIterationCount),String.valueOf(6)).getText().trim();
			size=reportAnswers.get(element("txt_endDate",String.valueOf(scarfReportIterationCount),String.valueOf(4)).getText().trim()).size();
			for(int j=0;j<size;j++){
				if(reportAnswers.get(element("txt_endDate",String.valueOf(scarfReportIterationCount),String.valueOf(4)).getText().trim()).get(j).
						contains(element("txt_endDate",String.valueOf(scarfReportIterationCount),String.valueOf(6)).getText().trim())){
					flag=true;
				}
			}
			Assert.assertTrue(flag,"ASSERT FAILED : Report answers does not verifed as "+value+" for section "+sectionName+"\n");
			logMessage("STEP PASSED : Report answers verified as "+value+" for section "+sectionName+"\n");
		}
		pageNos++;	
	  }while(pageNos<elements("list_pageNos").size());
	}
	
	public void verifyEventsOnReport(Map<String,List<String>> eventsMap){
		String eventName;
		int size,i,j;
		boolean flag=false;
		isElementDisplayed("table_rows");
		System.out.println((elements("table_rows").size() - 1));			
			for (String e : eventsMap.keySet()){
			    System.out.println("Section: "+e.toString());
			    size=eventsMap.get(e.toString()).size();
			    for(i=0;i<size;i++){
			    	for(j=1;j<(elements("table_rows").size()-1);j++){
			    		if(eventsMap.get(e).get(i).equals(element("txt_endDate",String.valueOf(j),String.valueOf(6)).getText().trim())){
			    			flag=true;
			    			break;
			    		}
			    	}
			    	Assert.assertTrue(flag, "ASSERT FAILED : Event Name "+e.toString()+" having answer "+eventsMap.get(e.toString()+" is not verified\n"));
			    	logMessage("ASSERT PASSED : Event Name "+e.toString()+" having answer "+eventsMap.get(e.toString())+" is verified\n");
			    }
			}
	}
	
	public int clickOnNextReportPages(String pagecount)
	{
		int loopLength;
		wait.hardWait(5);
		isElementDisplayed("lnk_reportPages",pagecount);
		try
		{
		element("lnk_reportPages",pagecount).click();
		logMessage("Step : Page "+pagecount+" of report is clicked\n");
		}
		catch(Exception e)
		{
			clickUsingXpathInJavaScriptExecutor(element("lnk_reportPages",pagecount));
			logMessage("Step : Page "+pagecount+" of report is clicked\n");
		}
		scarfReportIterationCount=1;
		loopLength=6;
		wait.hardWait(2);
		return loopLength;
	}

	public void addDetails(String data,List<String> answers) {
		isElementDisplayed("txt_txtBox");
		element("txt_txtBox").clear();
		data=data + System.currentTimeMillis();
		element("txt_txtBox").sendKeys(data);
		answers.add(data);
		logMessage("STEP : Answer "+data+" is added under question\n");
	}

	public void clickOnSelfAssessmentSaveButton(String btnValue) {
		wait.hardWait(1);
		isElementDisplayed("btn_saveEvent", btnValue);
		element("btn_saveEvent", btnValue).click();
		logMessage("STEP : Clicked on " + btnValue + " button\n");
	}

	public Map<String,List<String>> createEvents(Map<String, String> eventlist, ASM_FellowNominatePage obj) {
		int i = 1;
		String category;
		while (i <= 5) {
			clickOnAddEvent();
			logMessage("*******STEP : Creating "+"'"+ "Event " + i + "'*******\n");
//			eventType.put("Event Type" + i, eventlist.get("Event" + i + "_Type"));
			selectEventType(1, eventlist.get("Event" + i + "_Type"),"Type");
			wait.hardWait(5);
			category=getEventCategory();
			selectEventType(3, "My Student Chapter","Audience");
			selectEventType(2, eventlist.get("NCW/Mole Day/CCEW_" + i),"NCW/Mole Day/CCEW");
			enterDescriptionDetails(eventlist.get("Description"));
			enterDetails(eventlist,i);
			selectIsThisGreenChemistryEvent();
			obj.uploadFileUsingJavascipt("test.jpg", "sm1", "Events");
			verifyAdditionOfDesciptionData(eventlist.get("Description"));
			clickOnSelfAssessmentSaveButton("Save");
			addEventsNameInMap(category,eventlist.get("Event") + i,list);
			i++;
		}		
		return eventsMap;
	}
	
	public void enterDetails(Map<String, String> eventlist,int i){
		for(int j=0;j<7;j++){
			if(arrayData[j].equals("Events"))
				enterEventDetails(j+1, eventlist.get(arrayData[j]) + i,titleArray[j]);
			else
		        enterEventDetails(j+1, eventlist.get(arrayData[j]),titleArray[j]);
		}
	}
	
	public void addEventsNameInMap(String category,String value,List<String> list2){
		if(eventsMap.containsKey(category)){
		    list2=eventsMap.get(category);
		    list2.add(value);
			eventsMap.put(category,list2);
		}
		else{
			list2=new ArrayList<String>();
			list2.add(value);
			eventsMap.put(category,list2);
		}
	}
	
	public String getEventCategory(){

		wait.hardWait(5);

		isElementDisplayed("txt_eventCategory");
		return element("txt_eventCategory").getText().trim();
	}

	public void selectEventType(int i, String event,String type) {
		wait.hardWait(2);
		isElementDisplayed("lst_selectEvents", String.valueOf(i));
		Select dropdwnEventType = new Select(element("lst_selectEvents", String.valueOf(i)));
		dropdwnEventType.selectByVisibleText(event);
		logMessage("STEP : Event information entered for "+type+" is :" + event + "\n");
	}

	public void enterEventDetails(int index, String info,String type) {
		isElementDisplayed("txt_eventsInfo", String.valueOf(index));
		element("txt_eventsInfo", String.valueOf(index)).clear();
		wait.hardWait(1);
		sendKeysUsingXpathInJavaScriptExecutor(element("txt_eventsInfo", String.valueOf(index)), info);
		logMessage("STEP : Event details entered for "+type+" is : " + info + "\n");
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
		logMessage("STEP : Description data is: " + element("txt_eventDescription").getAttribute("value").trim() + "\n");
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
			logMessage("ASSERT PASSED : Status of " + sectionName[index] + " section is Complete\n");
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
		logMessage("ASSERT PASSED : User is navigated to the Report Complete page\n");
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
		logMessage("STEP : Clicked on Save And Return to dashboard button\n");
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

	public List<String> getMemberDetails() {
		List<String> memberDetails = new ArrayList<String>();
		MembershipPageActions_IWEB obj = new MembershipPageActions_IWEB(driver);
		memberDetails = obj.getMemberDetails();
		System.out.println(memberDetails);
		return memberDetails;
	}

	public String getChapterName() {
		isElementDisplayed("txt_chapterName");
		chapterName = element("txt_chapterName").getAttribute("value");
		logMessage("STEP : Chapter Name is : " + chapterName + "\n");
		return chapterName;
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
				"ASSERT FAILED : Chemistry Undergraduate Majors value "+chemUndergrads+" is not verified in report\n");
		logMessage("ASSERT PASSED : Chemistry Undergraduate Majors value "+chemUndergrads+" is verified in report\n");
	}
	
	public void verifyfacultyCountInReport(String facultyCount){
		isElementDisplayed("txt_iwebFacultyCount");
		Assert.assertTrue(facultyCount.equals(element("txt_iwebFacultyCount").getText()), 
				"ASSERT FAILED : Faculty count value "+facultyCount+" is not verified in report\n");
		logMessage("ASSERT PASSED : Faculty count value "+facultyCount+" is verified in report\n");
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
		if(checkIfElementIsThere("txt_chapterFacultyAdvisor")){
		isElementDisplayed("txt_chapterFacultyAdvisor");
		logMessage("STEP : Chapter Faculty Advisor is : "+element("txt_chapterFacultyAdvisor").getText().trim());
		return element("txt_chapterFacultyAdvisor").getText().trim();
		}
		else{
			logMessage("STEP : Chapter Faculty Advisor is not present\n");
			return "null";
		}
	}
	
	public void verifyChapterFacultyAdvisorOnReport(String facultyAdvisor){
		String arFaculty[];
		if(facultyAdvisor.equals("null"))
			logMessage("STEP : Chapter Faculty Advisor is not present\n");
		else{
		isElementDisplayed("table_rows");
		for (int i = 1; i <= elements("table_rows").size(); i++) {
			if(element("txt_endDate",String.valueOf(i),String.valueOf(5)).getText().trim().
					equals("Chapter Faculty Advisor")){
				arFaculty=element("txt_endDate",String.valueOf(i),String.valueOf(4)).getText().trim().split(" ");
				Assert.assertTrue(facultyAdvisor.contains(arFaculty[0]) && facultyAdvisor.contains(arFaculty[1]), 
						"ASSERT FAILED : Chapter Faculty Advisor Name "+facultyAdvisor+" Does not matches in the report\n");
				logMessage("ASSERT PASSED : Chapter Faculty Advisor Name "+facultyAdvisor+" matches in the report\n");
				break;
			}
		}	
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
		logMessage("STEP : Chapter Name report to search is :"+chapName+"\n");
	}
	
	public void enterReportStatus(String status){
		isElementDisplayed("drpdown_status");
		Select dropdwn_status=new Select(element("drpdown_status"));
		dropdwn_status.selectByVisibleText(status);
		logMessage("STEP : Report status enetered is :"+status);
	}
	
	public void verifyAdditionOfTextDatainBudgetSection(String descpData,int i){
		wait.hardWait(2);
		switchToFrame(element("lnk_frameAssessment", String.valueOf(i)));
		if(element("txt_txtBox").getText().trim().equals("")){
			switchToDefaultContent();
			enterSectionDetails(descpData,"Budget",1);
		}
		else{
			logMessage("STEP : Budget data is present :"+element("txt_txtBox").getText().trim());      
            switchToDefaultContent();
		}
	}
	
	public void verifyAdditionOfDesciptionData(String descpData){
		isElementDisplayed("txt_eventDescription");
		if(element("txt_eventDescription").getAttribute("value").trim().equals("")){
			enterDescriptionDetails(descpData);		}
		else
			logMessage("STEP : Description data is present :"+element("txt_eventDescription").getAttribute("value").trim());      
		
	}
	
	public void verifypresenceOfMoreThanTwoPrimaryContact(boolean flag){
		int count=0,index=0;
		List<Integer> indexList=new ArrayList<Integer>();
		if(flag){
		if(checkIfElementIsThere("lst_primaryContact")){
		for(int i=0;i<elements("lst_primaryContact").size();i++){
			if(element("checkbox_primaryContact",String.valueOf(i)).isSelected()){
				count++;
				indexList.add(i);
			}
		}
		while(count>=2){
			removeAlreadyAddedOfficer(indexList.get(index)+1);
			index++;
			count--;
		}
	  }
		else
			logMessage("STEP : Add atleast 1 primary officer"); 
	}	
		else
			System.out.println("STEP : No Officer present");
	}
}
