package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.DateUtil;
import com.thoughtworks.selenium.webdriven.commands.IsElementPresent;

public class ACS_Scarf_Reporting extends ASCSocietyGenericPage {

	WebDriver driver;
	static String pagename = "Scarf_Reporting";
	String reportingStartDate, reportingEndDate,chapterName,customerLname,customerFname;

	Map<String,String>eventType=new HashMap<String, String>();
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
			if (element("txt_current", String.valueOf(i)).getText().trim()
					.equals("Yes")) {
				reportingStartDate = element("txt_startDate", String.valueOf(i))
						.getText();
				reportingEndDate = element("txt_endDate", String.valueOf(i),String.valueOf(9))
						.getText();
				index = i;
				break;
			}
		}
		value = verfiyEndAndStartDate(reportingEndDate, reportingStartDate);
		Assert.assertTrue(
				value,
				"ASSERT FAIL : Current date does not lies within the Reporting start and end date\n");
		logMessage("ASSERT PASS : Current date lies within the Reporting start and end date\n");
	}

	public boolean verfiyEndAndStartDate(String reportingEndDate,
			String reportingStartDate) {
		logMessage("Current Date:"
				+ DateUtil.convertStringToDate(DateUtil
						.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"),
						"MM/dd/yyyy"));
		logMessage("End Date:"
				+ DateUtil.convertStringToDate(reportingEndDate, "MM/dd/yyyy"));
		int endDate, startDate;
		endDate = DateUtil.convertStringToDate(
				DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"),
				"MM/dd/yyyy").compareTo(
				DateUtil.convertStringToDate(reportingEndDate, "MM/dd/yyyy"));

		logMessage("Current Date:"
				+ DateUtil.convertStringToDate(DateUtil
						.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"),
						"MM/dd/yyyy"));
		logMessage("Start Date:"
				+ DateUtil.convertStringToDate(reportingStartDate,
						"MM/dd/yyyy"));
		startDate = DateUtil.convertStringToDate(
				DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"),
				"MM/dd/yyyy")
				.compareTo(
						DateUtil.convertStringToDate(reportingStartDate,
								"MM/dd/yyyy"));

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
		logMessage("Login With " + lastName + " and " + id
				+ " and clicked on login button\n");
	}

	public void verifyStudentChapterReportingPage() {
		isElementDisplayed("heading_StudentReport");
		logMessage("STEP : User is navigated to Student Chapters Annual Report Submission page\n");
	}

	public void verifyReportStatus(String status) {
		isElementDisplayed("txt_reportStatus");
		Assert.assertTrue(
				element("txt_reportStatus").getText().trim().equals(status),
				"ASSERT FAIL : Report Status is not "+status+"\n");
		logMessage("ASSERT PASS : Report Status is "+status+"\n");
	}

	public void clickOnEditChapterInfoButton() {
		isElementDisplayed("btn_editChapterInfo");
		element("btn_editChapterInfo").click();
		logMessage("STEP : Clicked on Edit Chapter Information button\n");
	}

	public void clickOnAddOrEditChapterOfficersButton() {
		isElementDisplayed("btn_editChapterOfficers");
		element("btn_editChapterOfficers").click();
		logMessage("STEP : Clicked on Add/Edit Chapter Officers button\n");
	}

	public void clickOnAddOfficerButton() {
		isElementDisplayed("btn_addOfficer");
		element("btn_addOfficer").click();
		logMessage("STEP : Clicked on Add Officers button\n");
	}
	
	public void verifyAlreadyPresentOfficerRole(String role){
		boolean flag=false;
		if(elements("lst_officerRoles").size()>1){
			for(int i=1;i<elements("lst_officerRoles").size();i++){
				if(elements("lst_officerRoles").get(i).getText().trim().equals(role)){
					removeAlreadyAddedOfficer(i);
					flag=true;
					break;
				}
			}
		}
		if(flag)
		    logMessage("STEP : Already added officer having role as "+role+" deleted");
		else
			logMessage("STEP : No officer was having role as : "+role);
	}
	
	public void removeAlreadyAddedOfficer(int i){
		isElementDisplayed("btn_removeOfficer",String.valueOf(i));
		element("btn_removeOfficer",String.valueOf(i)).click();
		logMessage("STEP : Clicked on cross button to remove officer");
		confirmOfficerDeletion();
	}
	
	public void confirmOfficerDeletion(){
		wait.hardWait(3);
		isElementDisplayed("btn_confirmDeletion");
		element("btn_confirmDeletion");
		logMessage("STEP : Clicked on Confirm button");
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
			name = element("lst_officerName", String.valueOf(officerIndex))
					.getText();
			logMessage("Officer selected is : "
					+ element("lst_officerName", String.valueOf(officerIndex))
							.getText() + "\n");
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
		logMessage("STEP : Officer role selected :" + role + "\n");
		selectPrimaryContact();
	}

	public void selectPrimaryContact() {
		isElementDisplayed("chkbox_primaryContact");
		element("chkbox_primaryContact").click();
		logMessage("STEP : Primary Contact checkbox is selected\n");
	}

	public void clickOnSaveButton() {
		isElementDisplayed("btn_save");
		element("btn_save").click();
		logMessage("STEP : Clicked on Save button\n");
	}

	public void verifyAdditionOfOfficer(String name) {
		String ar[];
		wait.waitForPageToLoadCompletely();
		wait.hardWait(3);
		isElementDisplayed("txt_officerName");
		ar=element("txt_officerName").getText().trim().split(" ");
		Assert.assertTrue(name.contains(ar[0]) && name.contains(ar[1]),
				"ASSERT FAIL : Officer cannot be added\n");
		logMessage("ASSERT PASS : Officer successfully added\n");
	}

	public void clickOnReturnButton() {
		isElementDisplayed("btn_return");
		element("btn_return").click();
		logMessage("STEP : Clicked on Return button\n");
	}

	public void DepartmentAndInstitutionInformation(String undergraduates,
			String chemMembers) {
		EnterUndergraduatesMajoringInChemistry(undergraduates);
		EnterChemistryFacultyMembers(chemMembers);
	}
	
	public void EnterUndergraduatesMajoringInChemistry(String undergraduates){
		isElementDisplayed("txt_undergraduatesMajoring");
		element("txt_undergraduatesMajoring").clear();
		element("txt_undergraduatesMajoring").sendKeys(undergraduates);
		logMessage("STEP : Entered Undergraduates Majoring in Chemistry: "
				+ undergraduates + "\n");
	}
	
	public void EnterChemistryFacultyMembers(String chemMembers){
		isElementDisplayed("txt_chemistryFacultyMembers");
		element("txt_chemistryFacultyMembers").clear();
		element("txt_chemistryFacultyMembers").sendKeys(chemMembers);
		logMessage("STEP : Entered Chemistry Faculty Members: " + chemMembers
				+ "\n");
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
		logMessage("STEP : Clicked on "+tabName+" tab\n");
	}

	public void verifyChapterStatus(String chapterName,String statusValue) {
		wait.waitForPageToLoadCompletely();
		Assert.assertTrue(
				element("btn_status", chapterName).getAttribute("value")
						.equals(statusValue), "ASSERT FAIL : " + chapterName
						+ " status is not "+statusValue+"\n");
		logMessage("ASSERT PASS : " + chapterName + " status is "+statusValue+"\n");
	}

	public void clickOnNotStartedButtonForSection(String chapterName,String sectionStatus) {
		Assert.assertTrue(
				element("btn_status", chapterName).getAttribute("value")
						.equals(sectionStatus), "ASSERT FAIL : " + chapterName
						+ " status is not "+sectionStatus);
		logMessage("ASSERT PASS : " + chapterName + " status is "+sectionStatus);
		element("btn_status", chapterName).click();
		logMessage("STEP : Clicked on "+sectionStatus+" button under " + chapterName+"\n");
	}

	public void enterSectionDetails(String data, String sectionName,int index) {
		logMessage("STEP : Entering details for "+sectionName+" section\n");
		for (int i = 1; i <= index; i++) {
			wait.hardWait(2);
			switchToFrame(element("lnk_frameAssessment", String.valueOf(i)));
			addDetails(data);
			switchToDefaultContent();
		}
	}
	
	public void addDetails(String data){
		isElementDisplayed("txt_txtBox");
		element("txt_txtBox").clear();
		element("txt_txtBox").sendKeys(data+System.currentTimeMillis());
		logMessage("STEP : Details added under questions : "+data+"\n");
	}

	public void clickOnSelfAssessmentSaveButton(String btnValue) {
		isElementDisplayed("btn_saveAssessment",btnValue);
		element("btn_saveAssessment",btnValue).click();
		logMessage("STEP : Clicked on "+btnValue+" button\n");
	}

	public void createEvents(Map<String, String> eventlist,ASM_FellowNominatePage obj) {
		int i = 1;
		while(i<=5){
		clickOnAddEvent();
		logMessage("STEP : Creating Event "+i+"\n");
		eventType.put("Event Type"+i,eventlist.get("Event" + i + "_Type"));
		selectEventType(1, eventlist.get("Event" + i + "_Type"));
		wait.hardWait(5);
		selectEventType(2, eventlist.get("NCW/Mole Day/CCEW_" + i));
		enterEventDetails(1, eventlist.get("Event")+i);
		enterEventDetails(2, eventlist.get("Location"));
		enterEventDetails(3, "4/22/2015");
		enterDescriptionDetails(eventlist.get("Description"));
		selectEventType(3, "My Student Chapter");
		enterEventDetails(4, eventlist.get("No of ACS Student"));
		enterEventDetails(5, eventlist.get("No of NON ACS Chapter Members"));
		enterEventDetails(6, eventlist.get("No of Faculty"));
		enterEventDetails(7, eventlist.get("People Served"));
		selectIsThisGreenChemistryEvent();
		obj.uploadFileUsingJavascipt("test.jpg","sm1", "Events");
		clickOnSelfAssessmentSaveButton("Save");
		i++;
	  }
	}

	public void selectEventType(int i, String event) {
		wait.hardWait(2);
		isElementDisplayed("lst_selectEvents", String.valueOf(i));
		Select dropdwnEventType = new Select(element("lst_selectEvents",
				String.valueOf(i)));
		dropdwnEventType.selectByVisibleText(event);
		logMessage("STEP : Event type info :"+event+"\n");
	}

	public void enterEventDetails(int index, String info) {		
		isElementDisplayed("txt_eventsInfo", String.valueOf(index));
		element("txt_eventsInfo", String.valueOf(index)).clear();
		wait.hardWait(2);
		sendKeysUsingXpathInJavaScriptExecutor(element("txt_eventsInfo", String.valueOf(index)), info);
//		element("txt_eventsInfo", String.valueOf(index)).sendKeys(info);
		logMessage("STEP : Event details entered : "+info+"\n");
	}
	
	public void selectIsThisGreenChemistryEvent(){
		isElementDisplayed("txt_eventsInfo",String.valueOf(11));
		element("txt_eventsInfo", String.valueOf(11)).click();
        logMessage("STEP : Clicked on Is this a Green Chemistry Event\n");
	}

	public void clickOnAddEvent() {
		isElementDisplayed("btn_addEvent");
		element("btn_addEvent").click();
		logMessage("STEP : Clicked on Add Event button\n");
	}
	
	public void enterDescriptionDetails(String descriptionData){
		wait.hardWait(1);
		isElementDisplayed("txt_eventDescription");
		element("txt_eventDescription").clear();
		element("txt_eventDescription").sendKeys(descriptionData);
//		sendKeysUsingXpathInJavaScriptExecutor(element("txt_eventDescription"), descriptionData);
		logMessage("STEP : Description data is: "+descriptionData+"\n");
	}

	public void uploadFile(){
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		isElementDisplayed("btn_chooseFile");
		element("btn_chooseFile").sendKeys(System.getProperty("user.dir") + File.separator +
				"src" + File.separator + "test" + File.separator + "resources" + File.separator + "UploadFiles"
				+ File.separator + "test.jpeg");
		logMessage("STEP : File Uploaded\n");
	}
	
	public void clickOnGreenChemistryCheckbox(){
		isElementDisplayed("chkbox_greenChemistry");
		element("chkbox_greenChemistry").click();
		logMessage("STEP : Clicked on 'Check this box if you choose NOT to submit any green chemistry events.' checkbox\n");
	}
	
	public void verifyMergingOfSections(){
		String sectionName []={"Chapter Information","Self-Assessment","Service","Professional Development",
				"Chapter Development","Budget","Green Chemistry"};
		logMessage("STEP : Verfiying merging of all the completed sections under Review & Submit tab\n");
		for(int index=0;index<sectionName.length;index++){
			isElementDisplayed("btn_sectionStatus",sectionName[index]);
			logMessage("ASSERT PASS : Status of "+sectionName[index]+" section is Complete\n");
		}
	    logMessage("STEP : All the sections are merged successfully");
	}
	
	public void clickOnReportSubmitCheckbox(){
		isElementDisplayed("chkbox_submitReport");
		element("chkbox_submitReport").click();
		logMessage("STEP : Clicked on checkbox for Report Submission\n");
	}
	
	public void verifyReportCompletePage(){
		isElementDisplayed("heading_reportComplete");
	    logMessage("ASSERT PASS : User is navigated to the Report Complete page\n");
	}
	
	public void verifyConfirmChapterAppearWindow(){
		wait.hardWait(2);
		isElementDisplayed("heading_confirmReport");
		logMessage("ASSERT PASSED : Confirm Chapter Report window appears\n");
		clickOnSubmitReportButton("Submit");
	}
	
	public void clickOnSubmitReportButton(String value){
		isElementDisplayed("btn_modalSubmit",value);
		element("btn_modalSubmit",value).click();
		logMessage("STEP : Clicked on "+value+" button\n");
	}
	
	public void clickOnSideBarTabStudentChapter(String tabName, int index){
		isElementDisplayed("tab_sideBar",String.valueOf(index));
		element("tab_sideBar",String.valueOf(index)).click();
		logMessage("STEP : Clicked on "+tabName+" tab");
	}
	
	public void clickOnSideBarSubTab(String subTab){
		isElementDisplayed("hd_sideBar",subTab);
		element("hd_sideBar",subTab).click();
		logMessage("STEP : Clicked on "+subTab+" sub tab");
	}
	
	public void selectARandomActiveStudentChapter(){
		MembershipPageActions_IWEB obj =new MembershipPageActions_IWEB(driver);
		obj.clickOnRandomPage();
		obj.clickOnAnyRandomMember();
	}
	
	public List<String> getMemberDetails(){
		List<String> memberDetails=new ArrayList<String>();
		MembershipPageActions_IWEB obj =new MembershipPageActions_IWEB(driver);
		memberDetails=obj.getMemberDetails();
		memberDetails.add(obj.getMemberWebLogin());
		System.out.println(memberDetails);
		return memberDetails;
	}
	
	public String getChapterDetails(){
		MembershipPageActions_IWEB obj =new MembershipPageActions_IWEB(driver);
        obj.clickOnEditNameAndAddress();
		switchToFrame("iframe1");
		chapterName=getChapterName();
		obj.clickOnCancelButton();
		handleAlert();
		switchToDefaultContent();
		return chapterName;
	}
	
	public void clickOnRelationsOptionUnderMoreMenu(){
		MembershipPageActions_IWEB objMember =new MembershipPageActions_IWEB(driver);
		IndividualsPageActions_IWEB object=new IndividualsPageActions_IWEB(driver);
		object.navigateToGeneralMenuOnHoveringMore("Relations");
		objMember.expandDetailsMenu("active student member undergrads");
	}
	
	public String getChapterName(){
		isElementDisplayed("txt_chapterName");
		chapterName=element("txt_chapterName").getAttribute("value");
		logMessage("STEP : Chapter Name is : "+chapterName+"\n");
		return chapterName;
	}
	
	public int selectStudentMember(){
		boolean flag;
		int i;
		isElementDisplayed("table_rows");
		for(i=1;i<=elements("table_rows").size();i++){
			flag=verifyStudentMemberEndDate(i);
			if(flag){
				logMessage("ASSERT PASSED : Current date of student member"+element("txt_endDate",String.valueOf(i),String.valueOf(4)).
				getText().trim()+" is equal to End Date");
				clickOnStudentMemberName(i);
				break;
			}
		}
		return i;
	}
	
	public boolean verifyStudentMemberEndDate(int i){
		boolean flag=false;
		logMessage("STEP : Current Date:"
				+ DateUtil.convertStringToDate(DateUtil
						.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"),
						"MM/dd/yyyy"));
		Date currDate=DateUtil.convertStringToDate(
				DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"), "MM/dd/yyyy");
		Date endDate=DateUtil.convertStringToDate(
				element("txt_endDate",String.valueOf(i),String.valueOf(7)).getText().trim(),
				"MM/dd/yyyy");
		if(currDate.compareTo(endDate)==-1 || currDate.compareTo(endDate)==1){
			flag=true;
		}
		return flag;
	}
	
	public void clickOnStudentMemberName(int i){
		wait.hardWait(2);
		isElementDisplayed("arrow_selectMember",String.valueOf(i));
		logMessage("STEP : Selected Student Member: "+element("txt_endDate",String.valueOf(i),String.valueOf(4)).
				getText().trim());
		element("arrow_selectMember",String.valueOf(i)).click();
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
