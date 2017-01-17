package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.formula.functions.Countif.StringMatcher;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.fasterxml.jackson.core.format.MatchStrength;
import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.DateUtil;

public class MemberShipRenewalPage extends GetPage {
	WebDriver driver;
	static String pagename = "MemberShipRenewalPage";
	int timeOut, hiddenFieldTimeOut;
	boolean flag = false;

	public MemberShipRenewalPage(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

	public String addACSRenewalCycle(String expMonth, String expYear,
			String renewalYear, String type, String renewalLength) {
		// verifyAddACSRenewalCyclePage();
		// switchToFrame("iframe1");
		selectACSCycleRenewalDetail("expireMonth", expMonth);
		selectACSCycleRenewalDetail("expireYear", expYear);
		selectACSCycleRenewalDetail("renewalYear", renewalYear);
		selectACSCycleRenewalDetail("renewalLength", renewalLength);
		String actualName = type + "_"
				+ DateUtil.getCurrentdateInStringWithGivenFormate("MM-dd-YYYY")
				+ "_" + System.currentTimeMillis();
		enterACSCycleRenewalDetails("type", actualName);
		waitForSpinner();
		wait.waitForPageToLoadCompletely();
		clickOnSaveButton();
		// switchToDefaultContent();
		String renewalCycleName = expYear + expMonth + " " + actualName;
		// verifyACSRenewalCycleAdded(renewalCycleName);
		return renewalCycleName;
	}

	/*
	 * public String getACSRenewaldetail(String detailName) {
	 * isElementDisplayed("inp_" + detailName); String detailValue =
	 * element("inp_" + detailName).getText().trim(); return detailValue; }
	 */

	public void clickOnPlusIcon(String membershipSetupName) {
		isElementDisplayed("btn_plusIconMemSetup", membershipSetupName);
		element("btn_plusIconMemSetup", membershipSetupName).click();
		logMessage("STEP : Plus icon is clicked for " + membershipSetupName
				+ " \n");
	}

	public void verifyAddACSRenewalCyclePage() {
		isElementDisplayed("txt_addACSRenewalCycle");
	}

	public void selectACSCycleRenewalDetail(String detailName,
			String detailValue) {
		isElementDisplayed("list_" + detailName);
		selectProvidedTextFromDropDown(element("list_" + detailName),
				detailValue);
		logMessage("STEP : Select " + detailValue + " for " + detailName
				+ " \n");
		waitForSpinner();
	}

	public void enterACSCycleRenewalDetails(String detailName,
			String detailValue) {
		isElementDisplayed("inp_" + detailName);
		element("inp_" + detailName).sendKeys(detailValue);
		logMessage("STEP : " + detailValue + " is entered for " + detailValue);

	}

	public void clickOnSaveButton() {
		isElementDisplayed("btn_save");
		element("btn_save").click();
		logMessage("STEP : Save button is clicked\n");
	}

	public void waitForSpinner() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		try {
			// handleAlert();
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("img_spinner");
			wait.waitForElementToDisappear(element("img_spinner"));
			logMessage("STEP : Wait for spinner to be disappeared \n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (NoSuchElementException | AssertionError | TimeoutException Exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("STEP : Spinner is not present \n");
		}
	}

	public void selectValueAtAddMembershipRenewalPage(String detailName,
			String detailValue) {
		isElementDisplayed("list_dropdownAtAddMembershipRenewal", detailName);
		selectProvidedTextFromDropDown(
				element("list_dropdownAtAddMembershipRenewal", detailName),
				detailValue);
		logMessage("STEP : Select value " + detailValue + " for " + detailName
				+ "\n");
		waitForSpinner();
	}

	public void selectBatchAtAddMembershipRenewalPage(String detailName,
			String detailValue) {
		isElementDisplayed("list_selectBatch", detailName);
		selectProvidedTextFromDropDown(element("list_selectBatch", detailName),
				detailValue);
		logMessage("STEP : Select value " + detailValue + " for " + detailName
				+ "\n");
		waitForSpinner();
	}

	public String enterRunTaskDateTime(String timeSlab) {
		isElementDisplayed("inp_runTaskDateTime");
		String currentDate = DateUtil.getCurrentTime("hh:mm a", "EST5EDT");
		Date dateInDate = DateUtil.convertStringToDate(currentDate, "hh:mm a");
		Date dateAfterMinutesAdded = DateUtils.addMinutes(dateInDate,
				Integer.parseInt(timeSlab) + 11);
		System.out.println("dateAfterMinutesAdded:" + dateAfterMinutesAdded);
		SimpleDateFormat sdf = new SimpleDateFormat("h:mm:ss a");
		String dateWithTimeSlabInString = sdf.format(dateAfterMinutesAdded);
		System.out.println("dateWithTimeSlabInString:"
				+ dateWithTimeSlabInString);
		String runTaskDateTime = DateUtil
				.getCurrentdateInStringWithGivenFormateForTimeZone(
						"M/dd/YYYY", "EST5EDT")
				+ " " + dateWithTimeSlabInString;
		System.out.println("runTaskDateTime:" + runTaskDateTime);
		element("inp_runTaskDateTime").click();
		wait.waitForPageToLoadCompletely();
		element("inp_runTaskDateTime").clear();
		wait.hardWait(3);
		element("inp_runTaskDateTime").sendKeys(runTaskDateTime);
		element("inp_runTaskDateTime").sendKeys(Keys.ENTER);
		wait.hardWait(2);
		logMessage("STEP : Enter " + runTaskDateTime
				+ " for run task date time\n");
		String runTaskDateTime1 = DateUtil
				.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/YYYY",
						"EST5EDT")
				+ " " + dateWithTimeSlabInString;
		return runTaskDateTime1;
	}

	public String fillDetailsAtAddMembershipRenewalPage(String association,
			String renewalYear, String renewalCycleName, String batchName,
			String timeSlab) {
		selectValueAtAddMembershipRenewalPage("association", association);
		selectValueAtAddMembershipRenewalPage("renewal year", renewalYear);
		selectValueAtAddMembershipRenewalPage("renewal cycle name",
				renewalCycleName);
		selectBatchAtAddMembershipRenewalPage("batch", batchName);
		String runTaskDateTime = enterRunTaskDateTime(timeSlab);

		wait.hardWait(3);
		// waitForSpinner();
		// wait.hardWait(1);
		// isElementDisplayed("txt_Now");
		// element("txt_Now").click();
		// waitForSpinner();
		// runTaskDateTime=element("txt_beginDate").getAttribute("value").trim();
		// System.out.println("-------time is:"+runTaskDateTime);
		clickOnSaveButton();
		handleAlert();

		return runTaskDateTime;
	}

	public void verifyRenewalForScheduled(String renewalCycleName,
			String associationCode, String runTaskDateTime, String proforma,
			String status, String createInvoiceScheduleTask, String batchName) {
		verifyRenewalCycleName("renewal cycle name", renewalCycleName);
		verifyScheduleDetails("association code", associationCode);
		verifyScheduleDetails_question("proforma", proforma);

		verifyRunTaskdateTimeName(runTaskDateTime);
		verifyRenewalCycleName("status", status);
		verifyScheduleDetails_question("create invoice scheduled task",
				createInvoiceScheduleTask);

		verifyBatchAtRenewal(batchName.replaceAll("ACS: ", ""));
	}

	public void verifyRunTaskdateTimeName(String detailValue) {
		isElementDisplayed("txt_beginDate");

		isStringMatching(element("txt_beginDate").getText().trim(), detailValue);

		logMessage("ASSERT PASSED : " + detailValue
				+ " is verified for run task datetime \n");
	}

	public void verifyBatchAtRenewal(String batchName) {
		isElementDisplayed("lnk_batch", batchName);
		logMessage("AASERT PASSED : Batch name " + batchName + " is verfied\n");
	}

	public void verifyRenewalCycleName(String detailName, String detailValue) {
		isElementDisplayed("txt_renewalCycleName", detailName);
		Assert.assertTrue(element("txt_renewalCycleName", detailName).getText()
				.trim().equalsIgnoreCase(detailValue));
		logMessage("ASSERT PASSED : " + detailValue + " is verified for "
				+ detailName + " \n");
	}

	public void verifyScheduleDetails(String detailName, String detailValue) {
		isElementDisplayed("txt_renewalDetails", detailName);
		isStringMatching(element("txt_renewalDetails", detailName).getText()
				.trim(), detailValue);
		logMessage("ASSERT PASSED : " + detailValue + " is verified for "
				+ detailName + " \n");
	}

	public void verifyScheduleDetails_question(String detailName,
			String detailValue) {
		isElementDisplayed("txt_renewalDetail_q", detailName);

		isStringMatching(element("txt_renewalDetail_q", detailName).getText()
				.trim(), detailValue);

		logMessage("ASSERT PASSED : " + detailValue + " is verified for "
				+ detailName + " \n");
	}

	public void clickOnSubInfoDropdown(String subInfoName) {
		isElementDisplayed("btn_memberShipRenewalSubInfo", subInfoName);
		element("btn_memberShipRenewalSubInfo", subInfoName).click();
		logMessage("STEP : Sub info " + subInfoName + " is clicked\n");
	}

	public void verifyRenewalsSubInfo(String detailName, String detailValue) {
		isElementDisplayed("txt_" + detailName);

		isStringMatching(element("txt_" + detailName).getText().trim(),
				detailValue);

		logMessage("ASSERT PASSED : " + detailValue + " is verified for "
				+ detailName + " \n");
	}

	public void verifyRenewalSubDetails(String numberOfRenewals,
			String numberofInvoicesCreated, String numberOfErrors,
			String waitTime) {
		verifyRenewalsSubInfo("numberOfRenewal", numberOfRenewals);
		// verifyRenewalsSubInfo("numberOfInvoicesCreated",
		// numberofInvoicesCreated);
		isElementDisplayed("txt_" + "numberOfInvoicesCreated",
				numberofInvoicesCreated);
		if (element("txt_numberOfInvoicesCreated").getText().trim()
				.equalsIgnoreCase(numberofInvoicesCreated)) {
			logMessage("AASERT PASSED : Number of invoice created "
					+ numberofInvoicesCreated + " is verified\n");
		} else {
			int count = (Integer.parseInt(waitTime)) / 2;
			for (int i = 1; i <= count; i++) {
				if (i > 1) {
					System.out.print("\r");
					System.out.println("******Script waited for: " + i * 2
							+ " minutes******\n");
					System.out.println("");
				}
				holdScriptExecutionToVerifyInvoiceCreated(numberofInvoicesCreated);
				pageRefresh();
				isElementDisplayed("txt_" + "numberOfInvoicesCreated",
						numberofInvoicesCreated);

				if (element("txt_numberOfInvoicesCreated").getText().trim()
						.equalsIgnoreCase(numberofInvoicesCreated)) {
					logMessage("ASSERT PASSED : Number of Invoices Created "
							+ numberofInvoicesCreated + " is verified\n");
					break;
				}
			}

		}

		verifyRenewalsSubInfo("numberOfErrors", numberOfErrors);
	}

	public void verifyNoResultDisplay() {
		isElementDisplayed("txt_noResultsDisplay");
		logMessage("ASSERT PASSED : Verified no result is displayed \n");
	}

	public void clickOnCreateRenewalInvoices() {
		isElementDisplayed("btn_createRenewalInvoices");
		element("btn_createRenewalInvoices").click();
		logMessage("STEP : Create renewal invoice button is clicked\n");
	}

	public String navigateToCreateRenewalInvoicesAndEnterInvoiceTaskStartTimeAndDate(
			String timeSlab) {
		clickOnCreateRenewalInvoices();
		isElementDisplayed("txt_scheduleCreateRenewalInvoice");
		switchToFrame("iframe1");
		enterInvoiceTaskDetails("date",
				DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone(
						"M/dd/YYYY", "EST5EDT"));
		String currentDate = DateUtil.getCurrentTime("hh:mma", "EST5EDT");
		Date dateInDate = DateUtil.convertStringToDate(currentDate, "hh:mma");
		Date dateAfterMinutesAdded = DateUtils.addMinutes(dateInDate,
				Integer.parseInt(timeSlab) + 11);
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mma");
		String dateWithTimeSlabInString = sdf.format(dateAfterMinutesAdded);
		enterInvoiceTaskDetails("time", dateWithTimeSlabInString);
		clickOnSaveButton();
		wait.hardWait(1);
		switchToDefaultContent();
		return dateWithTimeSlabInString;
	}

	public void enterInvoiceTaskDetails(String detailName, String detailValue) {
		isElementDisplayed("inp_startTimeAndDate", detailName);
		element("inp_startTimeAndDate", detailName).sendKeys(detailValue);
		logMessage("STEP : Enter " + detailValue + " for " + detailName + " \n");
	}

	public void verifyErrorMessage() {
		isElementDisplayed("txt_errorMessage");

		flag = validateTextFormat(element("txt_errorMessage").getText().trim());
		Assert.assertTrue(flag);
		logMessage("ASSERT PASSED : Renewal message "
				+ element("txt_errorMessage").getText().trim()
				+ " is verified\n");
	}

	public void verifyCreateInvoiceTaskStartTimeAndDate(String time) {
		verifyScheduleDetails("create invoice task start date",
				DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone(
						"M/d/YYYY", "EST5EDT"));
		verifyScheduleDetails("create invoice task start time", time);

	}

	public void holdScriptUntilVerifyStatus(String status, String waitTime) {
		isElementDisplayed("txt_renewalCycleName", "status");
		if (element("txt_renewalCycleName", "status").getText().trim()
				.equalsIgnoreCase(status)) {
			logMessage("AASERT PASSED : Status " + status + " is verified\n");
		} else {
			int count = (Integer.parseInt(waitTime)) / 1;
			for (int i = 1; i <= count; i++) {
				if (i > 1) {
					System.out.print("\r");
					System.out.println("******Script waited for: " + (i - 1)
							+ " minutes******\n");
					System.out.println("");
				}

				holdScriptExecutionToVerifyPreviewStatus(status, 1);
				pageRefresh();
				isElementDisplayed("txt_renewalCycleName", "status");

				if (element("txt_renewalCycleName", "status").getText().trim()
						.equalsIgnoreCase(status)) {

					logMessage("ASSERT PASSED : Preview status " + status
							+ " is verified\n");
					System.out
							.println(element("txt_renewalCycleName", "status")
									.getText().trim());
					break;
				}
			}

		}

	}

	public void holdScriptExecutionToVerifyPreviewStatus(String status,
			int minutesToHoldScript) {
		logMessage("===== Automation script is on hold for "
				+ minutesToHoldScript + " minutes to verify preview status "
				+ status + " =====\n");
		String lapsedMinutes = "";
		for (int minutes = 1; minutes <= minutesToHoldScript; minutes++) {
			for (int i = 0; i <= 59; i++) {
				System.out.print("\r");
				System.out.print("Time:- " + lapsedMinutes + i + " sec ");
				wait.hardWait(1);
			}
			lapsedMinutes = String.valueOf(minutes) + " min : ";
		}
		System.out.print("\r");
		System.out.println("Time:- " + minutesToHoldScript
				+ " minutes            ");
		System.out.println("");
	}

	public void holdScriptExecutionToVerifyInvoiceCreated(String invoiceCreated) {
		logMessage("===== Automation script is on hold for 2 minutes to verify invoice created "
				+ invoiceCreated + " =====\n");
		String lapsedMinutes = "";
		for (int minutes = 1; minutes <= 2; minutes++) {
			for (int i = 0; i <= 59; i++) {
				System.out.print("\r");
				System.out.print("Time:- " + lapsedMinutes + i + " sec ");
				wait.hardWait(1);
			}
			lapsedMinutes = String.valueOf(minutes) + " min : ";
		}
		System.out.print("\r");
		System.out.println("Time:- " + 2 + " minutes            ");
		System.out.println("");
	}

	public boolean validateTextFormat(String message) {
		String format = "Dues task netFORUM_Dues_Renewal_update_Task_([0-9a-zA-Z])+ scheduled successfully. Upon completion an e-mail will be sent to";
		Pattern pattern = Pattern.compile(format);

		return pattern.matcher(message).matches();
	}

	public void verifyACSRenewalCycleAdded(String renewalCycleName) {
		isElementDisplayed("txt_addACSRenewalCycle", renewalCycleName);
		logMessage("ASSERT PASSED : ACS renewal cycle " + renewalCycleName
				+ " is added in txt_addACSRenewalCycle\n");
	}

	public void clickOnAcsRenewalCycleTab() {
		isElementDisplayed("hd_acsRenewalCycle");
		element("hd_acsRenewalCycle").click();
		logMessage("STEP : ACS renewal cycle tab is clicked in hd_acsRenewalCycle\n");
	}

}
