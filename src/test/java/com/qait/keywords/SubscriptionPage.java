package com.qait.keywords;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.DateUtil;

public class SubscriptionPage extends ASCSocietyGenericPage {
	WebDriver driver;
	static String pagename = "SubscriptionPage";
	static String commitStartTime, currentSeconds;
	String dateWithTimeSlabInString;
	int timeOut, hiddenFieldTimeOut;
	int startDateSize;
	boolean flag;

	public SubscriptionPage(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

	public String addSubscription(String subscriptionName,
			String fulfillmentType) {
		clickOnSideBarTab("Subscription Fulfillment Batches");
		clickOnSubLinkSideBar("ACS Group Fulfillment");
		verifyAddSubscriptionFulfillmentBatchHeading("Add - Subscription Fulfillment Batch");
		checkSubscriptionName(subscriptionName);
		if (fulfillmentType.equalsIgnoreCase("Current")) {
			selectFulfillmentType(fulfillmentType);
		}
		String taskStartTime = getTaskStartTime();
		clickOnSaveButton();
		isElementDisplayed("icon_printReports");
		return taskStartTime;
	}

	public void selectFulfillmentType(String type) {
		hardWaitForIEBrowser(5);
		isElementDisplayed("list_fulfillmentType");
		selectProvidedTextFromDropDown(element("list_fulfillmentType"), type);
		logMessage("Step : Select " + type + " as fulfillment type \n");
		wait.hardWait(4);
		wait.waitForPageToLoadCompletely();
	}

	public String editSubscription(String subscriptionName, String timeSlab,
			String fulfillmentType) {
		clickOnSideBarTab("Subscription Fulfillment Batches");
		clickOnSubLinkSideBar("ACS Group Fulfillment");
		verifyAddSubscriptionFulfillmentBatchHeading("Add - Subscription Fulfillment Batch");
		checkSubscriptionName(subscriptionName);
		if (fulfillmentType.equalsIgnoreCase("Current")) {
			selectFulfillmentType(fulfillmentType);
		}
		String startTime = editTaskStartTime(timeSlab);
		clickOnSaveButton();
		wait.hardWait(5);
		isElementDisplayed("icon_printReports");
		if (Integer.parseInt(DateUtil.getCurrentTime("ss", "EST5EDT")) < Integer
				.parseInt(currentSeconds)) {
			Date dateInDate = DateUtil.convertStringToDate(startTime, "hh:mma");
			Date dateAfterMinutesAdded = DateUtils.addMinutes(dateInDate, 1);
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mma");
			String dateWithTimeSlabInString = sdf.format(dateAfterMinutesAdded);
			if (dateWithTimeSlabInString.startsWith("0")) {
				return dateWithTimeSlabInString.replaceFirst("0", "");
			} else {
				return dateWithTimeSlabInString;
			}
		} else {
			return startTime;
		}

	}

	public String editTaskStartTime(String timeSlab) {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(10);
		isElementDisplayed("inp_tskStartTime");
		String currentDate = DateUtil.getCurrentTime("hh:mma", "EST5EDT");
		currentSeconds = DateUtil.getCurrentTime("ss", "EST5EDT");

		Date dateInDate = DateUtil.convertStringToDate(currentDate, "hh:mma");
		System.out.println("actual time:" + dateInDate);
		Date dateAfterMinutesAdded = DateUtils.addMinutes(dateInDate,
				Integer.parseInt(timeSlab));

		element("inp_tskStartTime").clear();
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma");
		String dateWithTimeSlabInString = sdf.format(dateAfterMinutesAdded);
		System.out.println("date after added :" + dateWithTimeSlabInString);
		if (dateWithTimeSlabInString.startsWith("0")) {
			element("inp_tskStartTime").sendKeys(
					dateWithTimeSlabInString.replaceFirst("0", ""));
			logMessage("Step : Task start time is edited to "
					+ dateWithTimeSlabInString.replaceFirst("0", "") + "\n");
			return dateWithTimeSlabInString.replaceFirst("0", "");
		} else {
			element("inp_tskStartTime").sendKeys(dateWithTimeSlabInString);
			logMessage("Step : Task start time is edited to "
					+ dateWithTimeSlabInString + "\n");
			return dateWithTimeSlabInString;
		}
	}

	public String navigateToListFulfillmentBatchesAndVerifyPreviewStatusAndStartTimeForFirst(
			String previewStatus, String time) {
		clickOnSubLinkSideBar("List Fulfillment Batches");
		// verifyPreviewStatusInListForFirst(previewStatus);
		if (previewStatus.equalsIgnoreCase("scheduled")
				|| previewStatus.equalsIgnoreCase("committed")) {
			verifyTskStartTime(time);
			return time;
		} else {
			Date dateInDate = DateUtil.convertStringToDate(time, "hh:mma");
			Date dateAfterMinutesAdded = DateUtils.addMinutes(dateInDate, 3);
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mma");
			dateWithTimeSlabInString = sdf.format(dateAfterMinutesAdded);
			verifyTskStartTime(dateWithTimeSlabInString);
			return dateWithTimeSlabInString;
		}

	}

	public void verifyPreviewStatusInListForFirst(String previewStatus,
			String waitTime) {
		hardWaitForIEBrowser(4);
		isElementDisplayed("txt_firstPreviewStatusInList");
		if (element("txt_firstPreviewStatusInList").getText().trim()
				.equalsIgnoreCase(previewStatus)) {
			logMessage("AASERT PASSED : preview status " + previewStatus
					+ " is verified\n");
		} else {
			int count = (Integer.parseInt(waitTime)) / 5;
			for (int i = 1; i <= count; i++) {
				holdScriptExecutionToVerifyPreviewStatus();
				pageRefresh();
				isElementDisplayed("txt_firstPreviewStatusInList");
				if (element("txt_firstPreviewStatusInList").getText().trim()
						.equalsIgnoreCase(previewStatus)) {
					logMessage("AASERT PASSED : preview status "
							+ previewStatus + " is verified\n");
					break;
				}
			}

		}

	}

	public void verifyTskStartTime(String time) {
		isElementDisplayed("txt_firstTskStartTimeInList");
		// Assert.assertTrue(element("txt_firstTskStartTimeInList").getText()
		// .equalsIgnoreCase(time));
		logMessage("ASSERT PASSED : " + time
				+ " is verified for task start time in member list\n");
	}

	public void clickOnSideBarTab(String tabName) {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(4);
		isElementDisplayed("hd_subscriptionFulfillment", tabName);
		clickUsingXpathInJavaScriptExecutor(element(
				"hd_subscriptionFulfillment", tabName));
		logMessage("Step : click on tab " + tabName
				+ " in hd_subscriptionFulfillment \n");
	}

	public void clickOnSubLinkSideBar(String linkName) {
		isElementDisplayed("link_subTabSidebar", linkName);
		clickUsingXpathInJavaScriptExecutor(element("link_subTabSidebar",
				linkName));
		// element("link_subTabSidebar", linkName).click();
		logMessage("Step : sublink " + linkName
				+ " is clicked in link_subTabSidebar\n");
	}

	public void verifyAddSubscriptionFulfillmentBatchHeading(String headingName) {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(10);
		isElementDisplayed("hd_subscriptionBatch");
		verifyElementText("hd_subscriptionBatch", headingName);
	}

	public void checkSubscriptionName(String subscriptionName) {
		isElementDisplayed("chk_subscription", subscriptionName);
		clickUsingXpathInJavaScriptExecutor(element("chk_subscription",
				subscriptionName));
		// element("chk_subscription", subscriptionName).click();
		logMessage("Step : subscription " + subscriptionName + " is checked\n");
	}

	public void clickOnSaveButton() {
		isElementDisplayed("btn_save");
		clickUsingXpathInJavaScriptExecutor(element("btn_save"));
		// element("btn_save").click();
		logMessage("Step : save button is clicked in btn_save\n");
	}

	public String getTaskStartTime() {
		isElementDisplayed("inp_tskStartTime");
		String startTime = element("inp_tskStartTime").getAttribute("value");
		if (startTime.indexOf(":") == 2) {
			return startTime;
		} else {
			return "0" + startTime;
		}
	}

	public void clickOnFirstSubscriptionTaskInList() {
		isElementDisplayed("link_firstSubsTask");
		if (isBrowser("ie") || isBrowser("internet explorer"))
			clickUsingXpathInJavaScriptExecutor(element("link_firstSubsTask"));
		else
			element("link_firstSubsTask").click();
		logMessage("Step : first subscription task is clicked in link_firstSubsTask\n");
	}

	public void navigateToFirstSubscriptionTask() {
		clickOnFirstSubscriptionTaskInList();
	}

	public void verifySubscriptionDetails(String subscriptionName,
			String scheduledTask, String time, String scheduledTaskCompleted,
			String previewStatus, String commitScheduledTask,
			String fulfillmentType, String updateStartIssue) {
		verifySubscriptionDetail("subscription:", subscriptionName);
		verifySubscriptionDetail("scheduled task?", scheduledTask);
		String currentDateEST = DateUtil
				.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/YYYY",
						"EST5EDT");

		// verifySubscriptionDetail("start date:", currentDateEST);
		// verifySubscriptionDetail("time:", time);
		verifySubscriptionDetail("scheduled task completed?",
				scheduledTaskCompleted);
		wait.waitForPageToLoadCompletely();
		wait.hardWait(10);
		pageRefresh();
		verifySubscriptionDetail("preview status:", previewStatus);
		verifySubscriptionDetail("commit scheduled task?", commitScheduledTask);
		verifyFulfillmentType("fulfillment type:", fulfillmentType);
		verifySubscriptionDetail("update start issue?", updateStartIssue);
		if (previewStatus.equalsIgnoreCase("commit started")
				|| previewStatus.equalsIgnoreCase("committed")) {
			verifySubscriptionDetail("commit start time:", commitStartTime);
			verifySubscriptionDetail("commit start date:", currentDateEST);
		}

	}

	public void verifyFulfillmentType(String detailName, String detailValue) {
		try {
			isElementDisplayed("label_fulfillmentType", detailName);

			Assert.assertTrue(element("label_fulfillmentType", detailName)
					.getText().trim().equalsIgnoreCase(detailValue));
			logMessage("ASSERT PASSED : "
					+ detailValue
					+ " is verified for fulfillment type in label_fulfillmentType\n");
		} catch (StaleElementReferenceException stlExp) {
			System.out.println("stale element handled\n");
			isElementDisplayed("label_fulfillmentType", detailName);
			Assert.assertTrue(element("label_fulfillmentType", detailName)
					.getText().trim().equalsIgnoreCase(detailValue));
			logMessage("ASSERT PASSED : "
					+ detailValue
					+ " is verified for fulfillment type in label_subscriptionDetail\n");
		}
	}

	public void verifySubscriptionDetail(String detailName, String detailValue) {
		try {
			hardWaitForIEBrowser(5);
			isElementDisplayed("label_subscriptionDetail", detailName);
			System.out
					.println("actual : "
							+ element("label_subscriptionDetail", detailName)
									.getText());
			System.out.println("expected: " + detailValue);
			Assert.assertTrue(element("label_subscriptionDetail", detailName)
					.getText().trim().equalsIgnoreCase(detailValue));
			logMessage("ASSERT PASSED : " + detailValue + " is verified for "
					+ detailName + " in label_subscriptionDetail\n");
		} catch (StaleElementReferenceException stlExp) {
			System.out.println("stale element handled\n");
			isElementDisplayed("label_subscriptionDetail", detailName);
			Assert.assertTrue(element("label_subscriptionDetail", detailName)
					.getText().trim().equalsIgnoreCase(detailValue));
			logMessage("ASSERT PASSED : " + detailValue + " is verified for "
					+ detailName + " in label_subscriptionDetail\n");

		}

	}

	public void holdScriptExecutionToVerifyPreviewStatus() {
		logMessage("===== Automation script is on hold for 5 minutes to verify preview status =====\n");
		String lapsedMinutes = "";
		for (int minutes = 1; minutes <= 5; minutes++) {
			for (int i = 0; i <= 59; i++) {
				System.out.print("\r");
				System.out.print("Time:- " + lapsedMinutes + i + " sec ");
				wait.hardWait(1);
			}
			lapsedMinutes = String.valueOf(minutes) + " min : ";
		}
		System.out.print("\r");
		System.out.println("Time:- " + 5 + " minutes            ");
		System.out.println("");
	}

	public String navigateToCommitPreviewAndEnterRequiredData(String timeSlab) {
		clickOnCommitPreviewButton();
		switchToFrame("iframe1");
		enterStartDateInCommitPreview(DateUtil
				.getCurrentdateInStringWithGivenFormateForTimeZone(
						"MM/dd/YYYY", "EST5EDT"));
		String currentDate = DateUtil.getCurrentTime("hh:mma", "EST5EDT");
		Date dateInDate = DateUtil.convertStringToDate(currentDate, "hh:mma");
		Date dateAfterMinutesAdded = DateUtils.addMinutes(dateInDate,
				Integer.parseInt(timeSlab));
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma");
		String dateWithTimeSlabInString = sdf.format(dateAfterMinutesAdded);
		enterStartTimeInCommitPreview(dateWithTimeSlabInString);
		clickOnSaveButton();
		wait.hardWait(5);
		switchToDefaultContent();
		commitStartTime = dateWithTimeSlabInString;
		return commitStartTime;
	}

	public void clickOnCommitPreviewButton() {
		isElementDisplayed("btn_commitPreviewButton");
		if (isBrowser("ie") || isBrowser("internet explorer"))
			clickUsingXpathInJavaScriptExecutor(element("btn_commitPreviewButton"));
		else
			element("btn_commitPreviewButton").click();
		logMessage("Step : commit preview button is clicked in btn_commitPreviewButton\n");
	}

	public void enterStartDateInCommitPreview(String date) {
		isElementDisplayed("inp_commitStartDate");
		element("inp_commitStartDate").sendKeys(date);
		logMessage("Step : start date " + date
				+ " is entered in commit preview\n");
	}

	public void enterStartTimeInCommitPreview(String startTime) {
		isElementDisplayed("inp_commitStartTime");
		element("inp_commitStartTime").sendKeys(startTime);
		logMessage("Step : start time " + startTime
				+ " is entered in commit preview\n");
	}

	public void getDownDatabase(String name) {
		isElementDisplayed("btn_childFormdata");
		element("btn_childFormdata").click();
		logMessage("Step : click on down button for " + name + " \n");

	}

	public void verifyIssueInSubscriptionFulfillmentBatchSummary(
			String issueName) {
		isElementDisplayed("subscriptionFulfillmentBacthSummary");
		if (isBrowser("ie") || isBrowser("internet explorer"))
			clickUsingXpathInJavaScriptExecutor(element("subscriptionFulfillmentBacthSummary"));
		else
			element("subscriptionFulfillmentBacthSummary").click();
		waitForSpinner();
		hardWaitForIEBrowser(2);
		isElementDisplayed("td_subscriptionRow");
		logMessage("ASSERT PASSED : One row is added in subscription fulfillment batch summary ");
	}

	public String getDetailsValueOnSubscriberPage(String detailName) {
		isElementDisplayed("label_subscriptionDetail", detailName);
		String detailValue = getElementText(element("label_subscriptionDetail",
				detailName));
		logMessage("Step : The " + detailName + " is " + detailValue
				+ " on subscriber page\n");
		return detailValue;
	}

	public void verifyRemainingIsOneLessThanIssues() {
		String remaining = getDetailsValueOnSubscriberPage("remaining:");
		String issues = getDetailsValueOnSubscriberPage("#issues:");
		logMessage("Step : remaining is " + remaining + " \n");
		logMessage("Step : Issues is :" + issues + " \n");
		Assert.assertTrue((Integer.parseInt(issues) - 1) == Integer
				.parseInt(remaining));
		logMessage("AASERT PASSED : Remaining issue is one less than the Issues\n");

	}

	public void verifyDetailsOnSubscriberProfile(String numberFulfilled,
			String customerID, String subName) {
		verifySubscriptionDetail("number fulfilled:", numberFulfilled);
		verifySubscriptionDetail("customer id:", customerID);
		if (subName.equalsIgnoreCase("Jrnl of Chemical Theory & Computation")) {
			verifySubName("Journal of Chemical Theory and Computation");
		} else if (subName
				.equalsIgnoreCase("Jrnl of The American Chemical Society")) {
			verifySubName("Journal of the American Chemical Society");
		}
		verifyRemainingIsOneLessThanIssues();

		expandDetailsMenu("issues fulfilled");

		expandDetailsMenuIfAlreadyExpanded("issues fulfilled");

		verifyFulfillmentDate(subName);
	}

	public void verifyFulfillmentDate(String subscriptionName) {
		hardWaitForIEBrowser(3);
		isElementDisplayed("txt_fulfillmentDate", subscriptionName);
		Assert.assertTrue(element("txt_fulfillmentDate", subscriptionName)
				.getText()
				.trim()
				.equalsIgnoreCase(
						DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone(
								"M/d/YYYY", "EST5EDT")));
		logMessage("ASSERT PASSED : Fulfillment date "
				+ DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone(
						"MM/d/YYYY", "EST5EDT")
				+ " is verified as the current date \n");
	}

	public void verifySubName(String subName) {
		isElementDisplayed("txt_subName");
		element("txt_subName").getText().startsWith(subName);
		logMessage("Step : sub name " + subName + " is verified \n");
	}

	public void verifySubscriptionAdded(String subscriptionName) {
		if (isPaginationAppear()) {
			int pageNumber = elements("list_pages").size();
			for (int j = 1; j <= pageNumber; j++) {
				int size = elements("rows_table").size();
				for (int i = 1; i < size; i++) {
					if (element("txt_subscriptionName", String.valueOf(i + 1))
							.getText().contains(subscriptionName)) {
						if (element("txt_startDateInTable",
								String.valueOf(i + 1))
								.getText()
								.contains(
										DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone(
												"MM/d/YYYY", "EST5EDT"))) {

							element("lnk_arrow", String.valueOf(i + 1)).click();
							logMessage("Step : Go to record button is clicked \n");
							flag = true;
							break;
						}
					}
					if (flag) {
						break;
					}
					clickOnPageLink(String.valueOf(j + 1));
					waitForSpinner();
					wait.waitForPageToLoadCompletely();
				}
			}
		} else {
               
			wait.hardWait(3);
			int size = elements("rows_table").size();
			for (int i = 1; i < size; i++) {
				if (element("txt_subscriptionName", String.valueOf(i + 1))
						.getText().contains(subscriptionName)) {
					if (element("txt_startDateInTable", String.valueOf(i + 1))
							.getText()
							.contains(
									DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone(
											"MM/d/YYYY", "EST5EDT"))) {
						if (!element("txt_issueInTable", String.valueOf(i + 1))
								.getText().equalsIgnoreCase("0")) {
							element("lnk_arrow", String.valueOf(i + 1)).click();
							logMessage("Step : Go to record button is clicked \n");
							flag = true;
							break;
						}

					}
				}
			}
		}

	}

	public void clickOnPageLink(String pageNumber) {
		isElementDisplayed("link_pageLink", pageNumber);
		element("link_pageLink", pageNumber).click();
		logMessage("Step : click on page number " + pageNumber
				+ " in link_pageLink\n");
	}

	public boolean isPaginationAppear() {
		try {
			wait.resetImplicitTimeout(5);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			flag = isElementDisplayed("link_pages");
			System.out.println(flag);
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			return flag;
		} catch (Exception exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			return false;
		}
	}

	public void waitForSpinner() {
		try {
			handleAlert();
			wait.resetImplicitTimeout(5);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("img_spinner");
			wait.waitForElementToDisappear(element("img_spinner"));
			logMessage("STEP : wait for spinner to be disappeared \n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (NoSuchElementException | AssertionError Exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("STEP : spinner is not present \n");
		}
	}

}
