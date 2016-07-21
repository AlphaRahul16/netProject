package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class ACS_Scarf_ReviewingActions extends ASCSocietyGenericPage {
	ArrayList<String> assignedChapterNameList = new ArrayList<String>() ;
	ArrayList<String> reviewerNameList = new ArrayList<String>() ;
	WebDriver driver;
	static String pagename = "Scarf_Reviewing";
	int timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));;
	int hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));

	public ACS_Scarf_ReviewingActions(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}
	
	public void waitForSpinner() {
		
		try {
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			// handleAlert();
			isElementDisplayed("img_spinner");
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
		//selectDropDownValue(element("drpdwn_Reviwerlist"), reviewercount);
		elements("list_reviewerOptions").get(reviewercount).click();
        reviewerNameList.add(elements("list_reviewerOptions").get(reviewercount).getText().trim());
		logMessage("Step : "+reviewerNameList.get(reviewercount)+" is selected as a "+reviewertype);
		//waitForSpinner();
		getAssignedChapterName(assignedChapterNameList);
		clickAssignButtonToassignReviewerToChapter();
		waitForSpinner();
		
		
		
	}

	private void clickAssignButtonToassignReviewerToChapter() {
		isElementDisplayed("btn_AssignChapter");
		elements("btn_AssignChapter").get(0).click();
		logMessage("Step : Reviewer Assigned to chapter");
		
	}

	private void getAssignedChapterName(ArrayList<String> assignedChapterNameList) {
		isElementDisplayed("txt_AssignedchapterName");
		assignedChapterNameList.add(element("txt_AssignedchapterName").getText().trim());
		System.out.println(assignedChapterNameList.get(0));
		
	}

}
