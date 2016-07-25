package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class ACS_Scarf_ReviewingActions extends ASCSocietyGenericPage {
	String assignedChapterNameList;
	ArrayList<String> reviewerNameList = new ArrayList<String>() ;
	WebDriver driver;
	static String pagename = "Scarf_Reviewing";
	 String[] custmerSortNames = new String[4] ;
	int timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));;
	int hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));

	public ACS_Scarf_ReviewingActions(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}
	
	public void waitForSpinner() {
		
		try {
			wait.resetImplicitTimeout(hiddenFieldTimeOut);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("img_spinner");
			handleAlert();
			wait.waitForElementToDisappear(element("img_spinner"));
			logMessage("STEP : wait for spinner to be disappeared \n");

		} catch (Exception Exp) {

			logMessage("STEP : spinner is not present \n");
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
	}

	public void assignReviewerToAChapter(String reviewertype, int reviewercount) {
		selectDropDownValue(reviewertype);
		 waitForSpinner();
		 wait.hardWait(4);
         element("list_reviewerOptions",toString().valueOf(reviewercount+1)).click();
   	     wait.hardWait(3);
        System.out.println("name "+reviewercount+" "+element("list_reviewerOptions",toString().valueOf(reviewercount+1)).getText());
        reviewerNameList.add(element("list_reviewerOptions",toString().valueOf(reviewercount+1)).getText().trim());
        waitForSpinner();

		logMessage("Step : "+reviewerNameList.get(reviewercount)+" is selected as a "+reviewertype);
		//getAssignedChapterName(assignedChapterNameList);
		clickAssignButtonToassignReviewerToChapter(getAssignedChapterName(assignedChapterNameList));

	}
	public ArrayList<String> getReviewerNameList()
	{
		return reviewerNameList;
	}

	private void clickAssignButtonToassignReviewerToChapter(String chapterName) {
		isElementDisplayed("btn_AssignChapter",chapterName);
		element("btn_AssignChapter",chapterName).click();
		logMessage("Step : Reviewer Assigned to chapter "+chapterName);
		
	}

	private String getAssignedChapterName(String assignedChapterNameList) {
		isElementDisplayed("txt_AssignedchapterName");
		assignedChapterNameList=element("txt_AssignedchapterName").getText().trim();
		System.out.println(assignedChapterNameList);
		return assignedChapterNameList;
		 
	}


	public String[] getCustomerSortName(ArrayList<String> reviewerNameList) {
	   
		System.out.println(reviewerNameList.size());
		for (int i = 0; i < reviewerNameList.size(); i++) {
			System.out.println(reviewerNameList.get(i));
			int size=reviewerNameList.get(i).split(" ").length;
			custmerSortNames[i]=reviewerNameList.get(i).split(" ")[size-1];
			System.out.println(custmerSortNames[i]);
		}
		return custmerSortNames;
	}

	public void clickOnQueryTabForScarfModule(String tabName) {
		isElementDisplayed("tab_QueryName");
		element("tab_QueryName").click();
		logMessage("Step : "+tabName+" is clicked\n");
		
	}

}
