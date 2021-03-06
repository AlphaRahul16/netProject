package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class ACS_Scarf_ReviewingActions extends ASCSocietyGenericPage {
	String assignedChapterNameList;
	static String assignedchaptername;
	int reviewerNameCount = 0;
	ArrayList<String> reviewerNameList = new ArrayList<String>();
	WebDriver driver;
	static String pagename = "Scarf_Reviewing";
	String[] custmerSortNames = new String[4];
	List<Integer> randomNumberList = new ArrayList<>();
	public int randomReviewer;
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
			logMessage("STEP : Wait for spinner to be disappeared \n");

		} catch (Exception Exp) {

			logMessage("STEP : Spinner is not present \n");
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
	}

	public int generateUniqueRandomNumber(int reviwerListSize) {
		randomReviewer = ASCSocietyGenericPage.generateRandomNumberWithInRange(0, reviwerListSize - 1);
		if (randomNumberList.contains(randomReviewer)) {
			randomReviewer = ASCSocietyGenericPage.generateRandomNumberWithInRange(0, reviwerListSize - 1);
		} else
			randomNumberList.add(randomReviewer);
		System.out.println("------random number list:"+randomNumberList);
		return randomReviewer;
	}

	public String assignReviewerToAChapter(String reviewertype, int reviewercount) {
		hardWaitForChromeBrowser(2);
		selectDropDownValue(reviewertype);
		waitForSpinner();
		wait.hardWait(2);

		int reviwerListSize = elements("list_reviewerNameOptions").size();
		wait.hardWait(2);
		if (reviewertype.equals("Online Reviewer")) {
			randomReviewer = generateUniqueRandomNumber(reviwerListSize);
		} else
			randomReviewer = ASCSocietyGenericPage.generateRandomNumberWithInRange(0, reviwerListSize - 1);

		System.out.println(reviwerListSize);
		elements("list_reviewerNameOptions").get(randomReviewer).click();
		wait.hardWait(3);

		try {
			reviewerNameList.add(elements("list_reviewerNameOptions").get(randomReviewer).getText().trim());
		} catch (StaleElementReferenceException e) {
			System.out.println("In Catch");
			reviewerNameList.add(elements("list_reviewerNameOptions").get(randomReviewer).getText().trim());
		}

		waitForSpinner();
		logMessage("STEP : " + reviewerNameList.get(reviewerNameCount) + " is selected as a " + reviewertype);
		if ((reviewertype.equals("Online Reviewer")) && (reviewercount == 0) && (assignedchaptername == null)) {
			assignedchaptername = getAssignedChapterName();

		}
		System.out.println("----" + assignedchaptername);
		clickAssignButtonToassignReviewerToChapter(reviewertype, assignedchaptername);
		reviewerNameCount++;
		return assignedchaptername;
	}

	public ArrayList<String> getReviewerNameList() {
		return reviewerNameList;
	}

	private void clickAssignButtonToassignReviewerToChapter(String reviewertype, String chapterName) {
		isElementDisplayed("btn_AssignChapter", chapterName);
		if (reviewertype.equalsIgnoreCase("Faculty Decision Panel Reviewer")) {
			executeJavascript("scroll(3000,0);");
			wait.hardWait(3);
		}
		wait.hardWait(2);
		scrollDown(element("btn_AssignChapter", chapterName));
		wait.waitForElementToBeClickable(element("btn_AssignChapter", chapterName));
		try {
			// hoverClick(element("btn_AssignChapter",chapterName));
			wait.hardWait(3);
			clickUsingXpathInJavaScriptExecutor(element("btn_AssignChapter", chapterName));
		} catch (WebDriverException e) {
			clickUsingXpathInJavaScriptExecutor(element("btn_AssignChapter", chapterName));
		}
		waitForSpinner();
		logMessage("STEP : Reviewer Assigned to chapter " + chapterName);

	}

	private String getAssignedChapterName() {
		isElementDisplayed("txt_AssignedchapterName");
		String assignedChapterName = element("txt_AssignedchapterName").getText().trim();

		return assignedChapterName;

	}

	public String[] getCustomerSortName(ArrayList<String> reviewerNameList) {
		String ComplexReviewerName;
		String[] complexNameArray;
		System.out.println(reviewerNameList.size());
		for (int i = 0; i < reviewerNameList.size(); i++) {
			System.out.println(reviewerNameList.get(i));
			if (reviewerNameList.get(i).contains(",")) {
				ComplexReviewerName = reviewerNameList.get(i).split(",")[0].trim();
			} else {
				ComplexReviewerName = reviewerNameList.get(i);
			}
			complexNameArray = ComplexReviewerName.split(" ");
			int size = complexNameArray.length;
			custmerSortNames[i] = complexNameArray[size - 1];
			System.out.println(custmerSortNames[i]);
		}
		return custmerSortNames;
	}

	public void clickOnQueryTabForScarfModule(String tabName) {
		isElementDisplayed("tab_QueryName");
		// element("tab_QueryName").click();
		clickUsingXpathInJavaScriptExecutor(element("tab_QueryName"));
		logMessage("STEP : " + tabName + " is clicked\n");

	}

	public void assignChapterName(String chapterName) {
		if (chapterName.contains("Student Chapter")) {
			assignedchaptername = chapterName.split("Student Chapter")[0].trim();
		} else
			assignedchaptername = chapterName;
	}

}
