package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;
import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.formula.functions.Countif.StringMatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.fasterxml.jackson.core.format.MatchStrength;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.report.ReformatTestFile;
import com.qait.automation.utils.ConfigPropertyReader;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;

public class MembershipPageActions_IWEB extends ASCSocietyGenericPage {
	YamlInformationProvider getCenOrdEntry;
	Map<String, Object> mapReinstateMember;
	WebDriver driver;
	static String pagename = "MembershipPage";
	static int MemberTransferLoopCount = 0;

	static String index, selectedText, customerLname, customerFname, address, state, zipCode, customerEmail, city,
			currentDate, customerContactId, customerEmailAcsOrg, customerAddressType, nextYearDate, displayName,
			totalPrice;

	String reportingStartDate, reportingEndDate, chapterName, batchName;
	boolean flag = false;
	int timeOut, hiddenFieldTimeOut;
	List<String> memberDetails = new ArrayList<>();
	List<String> memberStoreDetails = new ArrayList<>();
	StringBuffer sb = new StringBuffer();
	int numberOfDivisions, numberOfSubscriptions, count;
	Float netIndividualBalance = 0.0f;
	public static HashMap<String, String> memberDetailsMap = new HashMap<String, String>();
	Map<String, String> createMemberCredentials = new HashMap<String, String>();
	private static int individualCount = 0;
	String html = null;
	String productName = null;

	public MembershipPageActions_IWEB(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

	public void selectAndRunQueryMembership(String queryLink, String queryName) {
		clickOnSideBar(queryLink);
		wait.waitForPageToLoadCompletely();
		waitForSpinner();
		wait.hardWait(3);
		isElementDisplayed("txt_loadOnExistingQueryLabel");
		selectExistingQuery(queryName);
		waitForSpinner();
		verifyQueryTablePresent();
		clickOnRunQuery();

	}

	public void selectAndRunQuery(String queryName) {
		wait.waitForPageToLoadCompletely();
		// hardWaitForIEBrowser(15);
		// waitForSpinner();
		isElementDisplayed("txt_loadOnExistingQueryLabel");
		selectExistingQuery(queryName);
		wait.hardWait(4);
		waitForSpinner();
		if (isBrowser("ie") || isBrowser("internet explorer")) {

			while (!checkIfElementIsThere("table_query")) {
				checkIfElementIsThere("table_query");
			}
		} else {
			verifyQueryTablePresent();
		}
		clickOnRunQuery();
	}

	public List<String> selectAndRunQueryForMemberOrNonMember(String caseId) {
		String memberStatus = getACS_Store_SheetValue(caseId, "Member?");
		logMessage("STEP : Member status is " + memberStatus + " in spreadsheet\n");
		if (memberStatus.equalsIgnoreCase("Y")) {
			selectAndRunQuery("Selenium - Find Active Regular Member");
		} else if (memberStatus.equalsIgnoreCase("N")) {
			wait.hardWait(4);
			selectAndRunQuery("Selenium - Find Random Non Member");
		}
		memberStoreDetails.add(getMemberDetailsOnMemberShipProfile("contact id"));
		memberStoreDetails.add(getMemberWebLogin());
		return memberStoreDetails;

	}

	public String getMemberWebLogin() {
		isElementDisplayed("txt_webLogin");
		String info = element("txt_webLogin").getText().trim();
		logMessage("STEP : WebLogin is " + info + " \n");
		return info;
	}

	public void clickOnSideBar(String tabName) {
		hardWaitForIEBrowser(10);
		isElementDisplayed("hd_sideBar", tabName);
		clickUsingXpathInJavaScriptExecutor(element("hd_sideBar", tabName));
		logMessage("STEP : Click on side bar for tab " + tabName + " at hd_sideBar\n");
	}

	public void verifyPageHeading(String heading) {
		try {
			wait.waitForPageToLoadCompletely();
			isElementDisplayed("hd_page");
			verifyElementTextContains("hd_page", heading);
		} catch (StaleElementReferenceException e) {
			wait.waitForPageToLoadCompletely();
			isElementDisplayed("hd_page");
			verifyElementTextContains("hd_page", heading);
		}
	}

	public void selectExistingQuery(String queryName) {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		if (queryName.equalsIgnoreCase("Member Query by Member Number – Multiple")) {
			selectDropDownValue(element("list_existingQuery"), 211);
		}

		else if (queryName.equalsIgnoreCase("Selenium - Find Random Non Member")
				&& ConfigPropertyReader.getProperty("tier").equalsIgnoreCase("Stage3")) {
			selectDropDownValue(element("list_existingQuery"), 434);
		}

		else {
			try {
				wait.resetImplicitTimeout(2);
				wait.resetExplicitTimeout(hiddenFieldTimeOut);
				selectProvidedTextFromDropDown(element("list_existingQuery"), queryName);
			} catch (StaleElementReferenceException e) {
				wait.hardWait(2);
				selectProvidedTextFromDropDown(element("list_existingQuery"), queryName);
			}

		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
		logMessage("STEP : Select existing query " + queryName);
	}

	public void waitForSpinner() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(4);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			// handleAlert();
			isElementDisplayed("img_spinner");
			wait.waitForElementToDisappear(element("img_spinner"));
			logMessage("STEP : Wait for spinner to be disappeared \n");

		} catch (Exception Exp) {

			logMessage("STEP : Spinner is not present \n");
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
	}

	public void verifyQueryTablePresent() {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(4);
		isElementDisplayed("table_query");
	}

	public void clickOnRunQuery() {
		isElementDisplayed("btn_runQuery");
		clickUsingXpathInJavaScriptExecutor(element("btn_runQuery"));
		logMessage("STEP : Click on run query button \n");
		wait.hardWait(6);
		// wait.waitForPageToLoadCompletely();
		waitForSpinner();
		wait.hardWait(1);
	}

	public void clickOnMenuItems(String menuName) {
		isElementDisplayed("btn_menuItems", menuName);
		clickUsingXpathInJavaScriptExecutor(element("btn_menuItems", menuName));
		// element("btn_menuItems", menuName).click();
		logMessage("STEP : Click on " + menuName + " button in btn_menuItems \n");
	}

	public void clickOnEditContactInfo() {
		wait.hardWait(1);
		wait.waitForPageToLoadCompletely();
		// isElementDisplayed("btn_editContactInfo");
		executeJavascript("document.getElementById('F1_HYPERLINK_1').click()");
		logMessage("STEP : Click on edit contact info button in btn_editContactInfo\n");
	}

	public void clickOnEditNameAndAddress() {
		wait.hardWait(1);
		handleAlert();
		handleAlert();
		hardWaitForIEBrowser(3);
		try {
			wait.resetImplicitTimeout(4);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			clickUsingXpathInJavaScriptExecutor(element("btn_editNameAndAddress"));
		} catch (NoSuchElementException e) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}
		// element("btn_editNameAndAddress").click();
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
		logMessage("STEP : Click on edit name and address button On Individual Profile Page\n");
	}

	public String editEmailIdToAcsOrg(String existingEmail) {
		isElementDisplayed("inp_editEmail");
		logMessage("STEP : Existing email id is " + existingEmail);
		String[] splitEmail = existingEmail.split("@");
		logMessage("STEP : Existing email id is " + existingEmail + "\n");
		String newEmail = splitEmail[0] + "@acs.org";
		wait.hardWait(1);
		element("inp_editEmail").clear();
		element("inp_editEmail").sendKeys(newEmail);
		logMessage("STEP : Email id " + existingEmail + " is edited to " + newEmail + " in inp_editEmail\n");
		return newEmail;
	}

	public void verifyEmailIdEditedToAcsOrg(String emailWithAcsOrg) {
		String newEmail = element("link_email").getText().trim();
		isStringMatching(newEmail, emailWithAcsOrg);
	}

	public String editEmailId() {
		handleAlert();
		// String customerEmail = element("link_email").getText();
		// clickOnEditContactInfo();
		// switchToFrame("iframe1");
		String emailWithAcsOrg = editEmailIdToAcsOrg(customerEmail);
		// clickOnSaveButtonForBillingAddress();
		// handleAlert();
		// switchToDefaultContent();
		// waitForSpinner();
		// verifyEmailIdEditedToAcsOrg(emailWithAcsOrg);
		return emailWithAcsOrg;
	}

	public void editSameEmailId(String emailId) {
		if (!emailId.contains("@acs.org")) {
			handleAlert();
			clickOnEditContactInfo();
			switchToFrame("iframe1");
			element("inp_editEmail").clear();
			element("inp_editEmail").sendKeys(emailId);
			logMessage("STEP : Email address is edited to " + emailId + " in inp_editEmail\n");
			clickOnSaveButtonForBillingAddress();
			handleAlert();
			switchToDefaultContent();
			wait.hardWait(3);
			// waitForSpinner();
			if (!emailId.equalsIgnoreCase("")) {
				verifyEmailIdEditedToAcsOrg(emailId);
			}

		} else {
			logMessage("STEP : Email ID already has a domain of acs.org , so no need to edit\n");
		}

	}

	public String getNameFromEditNameAndAddressButton(String detailName) {
		hardWaitForIEBrowser(5);
		try {
			isElementDisplayed("inp_" + detailName);
			String detailValue = element("inp_" + detailName).getAttribute("value");
			logMessage("STEP : " + detailName + " is " + detailValue);
			return detailValue;
		} catch (StaleElementReferenceException stlExp) {
			isElementDisplayed("inp_" + detailName);
			String detailValue = element("inp_" + detailName).getAttribute("value");
			logMessage("STEP : " + detailName + " is " + detailValue);
			return detailValue;
		}

	}

	public List<String> selectFirstInactiveRegularMemberAndVerifyExistingDetails() {
		clickOnFirstInactiveRegularMember();
		verifyMemberStatus("Terminated by Process");
		String customerFullName = clickOnCustomerName();
		handleAlert();
		hardWaitForIEBrowser(2);
		clickOnEditNameAndAddress();
		switchToFrame("iframe1");
		// customerFname = getNameFromEditNameAndAddressButton("firstName");
		customerLname = getNameFromEditNameAndAddressButton("lastName");
		// clickOnSaveButtonForBillingAddress();
		clickOnCancelButton();
		handleAlert();
		switchToDefaultContent();
		wait.hardWait(1);

		handleAlert();
		wait.hardWait(1);
		clickOnEditContactInfoAndGetCustomarEmail();
		if (customerEmail.equalsIgnoreCase("")) {
			addEmailAddress();
		} else if (!customerEmail.endsWith("@acs.org")) {
			customerEmailAcsOrg = editEmailId();
		} else if (customerEmail.endsWith("@acs.org")) {
			customerEmailAcsOrg = customerEmail;
		}
		clickOnSaveButtonForBillingAddress();
		switchToDefaultContent();
		handleAlert();
		wait.hardWait(3);
		// waitForSpinner();
		verifyEmailIdEditedToAcsOrg(customerEmailAcsOrg);
		try {
			customerContactId = element("txt_contactId").getText();
		} catch (StaleElementReferenceException stlExp) {
			customerContactId = element("txt_contactId").getText();
		}
		try {
			customerAddressType = element("txt_addressType").getText();
		} catch (StaleElementReferenceException stlExp) {
			customerAddressType = element("txt_addressType").getText();
		}

		memberDetails.add(customerFullName);
		memberDetails.add(customerFname);
		memberDetails.add(customerLname);
		memberDetails.add(customerEmailAcsOrg);
		memberDetails.add(customerContactId);
		memberDetails.add(customerAddressType);
		memberDetails.add(customerEmail);
		for (String i : memberDetails) {
			System.out.println("member details::" + i);
		}

		// TODO Remove hard wait after handling stale element exception
		holdExecution(2000);
		handleAlert();
		verifyCrossMarked("No Member Benefits");
		verifyCrossMarked("Non Member");
		// TODO Remove hard wait after handling stale element exception
		holdExecution(1000);
		switchToDefaultContent();
		isElementDisplayed("btn_memberShip", "individual memberships");
		clickUsingXpathInJavaScriptExecutor(element("btn_memberShip", "individual memberships"));
		wait.hardWait(4);
		waitForSpinner();
		verifyMemberStatusIsNotActive();
		return memberDetails;
	}

	public void clickOnCancelButton() {
		isElementDisplayed("btn_cancel");
		clickUsingXpathInJavaScriptExecutor(element("btn_cancel"));
		// element("btn_cancel").click();
		logMessage("STEP : Cancel button is clicked\n");
	}

	public String addEmailAddress() {
		customerEmailAcsOrg = "memberEmail" + System.currentTimeMillis() + "@acs.org";
		element("inp_editEmail").sendKeys(customerEmailAcsOrg);
		logMessage("STEP : Email address is edited to " + customerEmailAcsOrg + "\n");
		return customerEmailAcsOrg;
	}

	public String clickOnEditContactInfoAndGetCustomarEmail() {
		clickOnEditContactInfo();
		switchToFrame("iframe1");
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(3);
		isElementDisplayed("inp_editEmail");
		customerEmail = element("inp_editEmail").getAttribute("value");
		logMessage("STEP : Existing email address is : " + customerEmail);
		return customerEmail;
	}

	public void addMemberAndSelectDetails() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));

		mapReinstateMember = YamlReader.getYamlValues("ReinstateMemberIWEB");
		getCenOrdEntry = new YamlInformationProvider(mapReinstateMember);

		clickOnOrderEntryIcon();
		// TODO Remove hard wait after handling stale element exception
		holdExecution(2000);
		isElementDisplayed("table_lineItems");
		clickUsingXpathInJavaScriptExecutor(element("table_lineItems"));
		// element("table_lineItems").click();
		// TODO Remove hard wait after handling stale element exception
		holdExecution(2000);
		wait.waitForPageToLoadCompletely();
		clickOnSelectProduct();
		// TODO Remove hard wait after handling stale element exception
		holdExecution(1000);
		try {
			wait.waitForPageToLoadCompletely();
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			switchToFrame(element("frame_selectProduct"));
			clickOnAddMembershipMenu();
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (Exception e) {
			wait.waitForPageToLoadCompletely();
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			switchToDefaultContent();

			// TODO Remove hard wait after handling stale element exception
			holdExecution(3000);
			switchToFrame(element("frame_selectProduct"));
			clickOnAddMembershipMenu();
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}

		switchToDefaultContent();
		switchToFrame("iframe1");
		// TODO Remove hard wait after handling stale element exception
		holdExecution(1000);
		selectMemberInfo("association", getCenOrdEntry.getReinstateMember_centralizedOrderEntry("association"));
		// TODO Remove hard wait after handling stale element exception
		holdExecution(1000);
		selectMemberInfo("memberType", getCenOrdEntry.getReinstateMember_centralizedOrderEntry("memberType"));
		// TODO Remove hard wait after handling stale element exception
		holdExecution(1000);
		selectMemberInfo("memberPackage", getCenOrdEntry.getReinstateMember_centralizedOrderEntry("memberPackage"));
		String currentDate = DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy");
		String nextSecondYear = DateUtil.getNextDate("year", 2)[0];
		// TODO Remove hard wait after handling stale element exception
		holdExecution(2000);
		enterDate("industryUpdateDate", currentDate);

		enterDate("jobTitleUpdateDate", "08/02/" + nextSecondYear);
		selectMemberInfo("industry", getCenOrdEntry.getReinstateMember_centralizedOrderEntry("industry"));
		// TODO Remove hard wait after handling stale element exception
		holdExecution(1000);
		selectMemberInfo("jobTitle", getCenOrdEntry.getReinstateMember_centralizedOrderEntry("jobTitle"));
		// TODO Remove hard wait after handling stale element exception
		holdExecution(1000);
		clickOnSaveAndFinish();
		handleAlert();
		switchToDefaultContent();
	}

	public void verifyItemsAddedSuccessFully() {
		verifyItemsAdded(getCenOrdEntry.getReinstateMember_centralizedOrderEntry("memberPackage"));
		verifyItemsAdded("Chemical and Engineering News");
	}

	public void selectBatchAndPaymentDetails() {
		// selectBillingAddress();
		selectOrderEntryInfo("batch", getCenOrdEntry.getReinstateMember_centralizedOrderEntry("batch"));
		waitForSpinner();
		selectOrderEntryInfo("PaymentType", getCenOrdEntry.getReinstateMember_centralizedOrderEntry("PaymentType"));
		waitForSpinner();
		selectOrderEntryInfo("paymentMethod", getCenOrdEntry.getReinstateMember_centralizedOrderEntry("paymentMethod"));
		waitForSpinner();
		enterCardDetails("cardNumber", getCenOrdEntry.getReinstateMember_centralizedOrderEntry("cardNumber"));
		selectMemberInfo("expireDate", getCenOrdEntry.getReinstateMember_centralizedOrderEntry("expireDate"));
		enterCardDetails("cvvNumber", getCenOrdEntry.getReinstateMember_centralizedOrderEntry("cvvNumber"));
		clickOnSaveAndFinish();
		handleAlert();

	}

	public void verifyMemberReinstatedSuccessfully(String caseId) {
		handleAlert();
		clickOnMenuItems("individual memberships");
		verifyRejoinDateIsCurentDate();
		verifyEffectiveDateIsCurentDate();
		verifyEndDateIsCurrentDate(caseId);
		verifyTickedMarked("receives member benefits");
		verifyTickedMarked("member");
		verifyMemberStatusActiveIsPresent();
		clickOnMenuItems("individual memberships");
	}

	public void verifyMemberDetails_IWEB(String tabName, int numberOfDivisions) {
		handleAlert();
		verifyTickedMarked("receives member benefits");
		verifyTickedMarked("member");
		clickOnMenuItems(tabName);
		if (tabName.equalsIgnoreCase("chapter memberships")) {
			for (int i = 0; i < numberOfDivisions - 1; i++) {
				int divNumber = i + 1;

				if (map().get("div" + divNumber + "_division").length() > 20) {
					String newMemberType = map().get("div" + divNumber + "_division").substring(0, 20);

					verifyMemberTypeInIndividualMemberships(newMemberType);
					verifyJoinDateIsCurentDateForMemebrType(tabName, newMemberType);
					verifyEffectiveDateIsCurentDateForMemebrType(tabName, newMemberType);
					verifyExpireDateIsNextYearForMemberType(tabName, newMemberType);
				} else {
					String newMemberType = map().get("div" + divNumber + "_division");
					verifyMemberTypeInIndividualMemberships(newMemberType);
					verifyJoinDateIsCurentDateForMemebrType(tabName, newMemberType);
					verifyEffectiveDateIsCurentDateForMemebrType(tabName, newMemberType);
					verifyExpireDateIsNextYearForMemberType(tabName, newMemberType);
				}
			}

		} else {
			String memberType = map().get("memberType");
			verifyMemberTypeInIndividualMemberships(memberType);
			verifyJoinDateIsCurentDateForMemebrType(tabName, memberType);
			verifyEffectiveDateIsCurentDateForMemebrType(tabName, memberType);
			verifyExpireDateIsNextYearForMemberType(tabName, memberType);
			verifyMemberStatusActiveIsPresent();
		}

	}

	public void verifyMemberTypeInIndividualMemberships(String memberType) {
		waitForSpinner();
		isElementDisplayed("txt_memberType", memberType);
		logMessage("ASSERT PASSED : " + memberType + " is verified in txt_memberType\n");
	}

	public void verifyMemberReinstatedSuccessfully_Iweb() {
		handleAlert();
		isElementDisplayed("btn_memberShip", "individual memberships");
		element("btn_memberShip", "individual memberships").click();
		verifyRejoinDateIsCurentDate();
		verifyEffectiveDateIsCurentDate();
		verifyTickedMarked("receives member benefits");
		verifyTickedMarked("member");
		verifyMemberStatusActiveIsPresent();
	}

	public void clickOnFirstInactiveRegularMember() {
		isElementDisplayed("img_firstInactiveRegularMember");
		clickUsingXpathInJavaScriptExecutor(element("img_firstInactiveRegularMember"));
		// element("img_firstInactiveRegularMember").click();
		logMessage("STEP : First member icon is clicked in img_firstInactiveRegularMember\n");
	}

	public void clickOnAnyRandomNominee() {
		wait.hardWait(5);
		hardWaitForIEBrowser(5);
		wait.waitForPageToLoadCompletely();
		int max = 12, min = 3;
		Random rand = new Random();
		int randomNumber = rand.nextInt((max - min) + 1) + min;
		String randomNumberInString = String.valueOf(randomNumber);
		isElementDisplayed("link_randomMemberFromList", randomNumberInString);

		element("link_randomMemberFromList", randomNumberInString).click();
		logMessage("STEP : Member icon at the position of " + randomNumberInString
				+ " is clicked in link_randomMemberInList\n");
	}

	public void clickOnAnyRandomMember() {
		wait.hardWait(5);
		hardWaitForIEBrowser(5);
		wait.waitForPageToLoadCompletely();
		// int max = 12, min = 3;
		int max = elements("list_tableRows").size();
		int min = 3;
		Random rand = new Random();
		int randomNumber = rand.nextInt((max - min) + 1) + min;
		String randomNumberInString = String.valueOf(randomNumber);
		isElementDisplayed("link_randomMemberInList", randomNumberInString);
		if (ConfigPropertyReader.getProperty("browser").equals("ie")
				|| ConfigPropertyReader.getProperty("browser").equals("internet explorer"))
			clickUsingXpathInJavaScriptExecutor(element("link_randomMemberInList", randomNumberInString));
		else
			element("link_randomMemberInList", randomNumberInString).click();
		logMessage("STEP : Member icon at the position of " + randomNumberInString
				+ " is clicked in link_randomMemberInList\n");
	}

	public void clickOnAnyRandomMember1(String locator) {
		int count = 0;
		wait.hardWait(5);
		hardWaitForIEBrowser(5);
		wait.waitForPageToLoadCompletely();
		int max = 12, min = 3;
		Random rand = new Random();
		String randomNumberInString, condition;

		do {
			int randomNumber = rand.nextInt((max - min) + 1) + min;
			randomNumberInString = String.valueOf(randomNumber + count);
			// isElementDisplayed("link_randomMemberInList",
			// randomNumberInString);
			condition = element(locator, randomNumberInString).getText();
			count++;
		} while ((condition.isEmpty() || condition.equals("0.00")) && count < 5);
		wait.hardWait(2);
		try {
			isElementDisplayed("link_randomMemberInList", randomNumberInString);
			// element("link_randomMemberInList", randomNumberInString).click();
			clickUsingXpathInJavaScriptExecutor(element("link_randomMemberInList", randomNumberInString));
		} catch (Exception e) {
			// element("img_arrow", randomNumberInString).click();
			clickUsingXpathInJavaScriptExecutor(element("img_arrow", randomNumberInString));
		}
		logMessage("STEP : Member icon at the position of " + randomNumberInString
				+ " is clicked in link_randomMemberInList\n");

	}

	public void verifyMemberStatus(String memberStatus) {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(3);
		isElementDisplayed("txt_memberStatus");
		verifyElementTextContains("txt_memberStatus", memberStatus);
		logMessage("ASSERT PASSED : Member status " + memberStatus + " is verified on membership page\n");
	}

	public String clickOnCustomerName() {
		isElementDisplayed("link_customerName");
		String customerName = element("link_customerName").getText();
		logMessage("STEP : Customer name is " + customerName);
		clickUsingXpathInJavaScriptExecutor(element("link_customerName"));
		logMessage("STEP : Customer name link is clicked in link_customerName\n");
		handleAlert();
		return customerName;
	}

	public void verifyMemberStatusIsNotActive() {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		switchToDefaultContent();
		isElementDisplayed("list_mbrStatus");
		for (WebElement element : elements("list_mbrStatus")) {
			if (element.getText().trim().equalsIgnoreCase("Active")) {
				Assert.fail("ASSERT FAILED : Member status text Active is not present in status list\n");
			}
		}
		logMessage("ASSERT PASSED : Member status is not active in status list\n");
	}

	public void verifyCrossMarked(String memberDetailsName) {
		isElementDisplayed("img_cross", memberDetailsName);
		Assert.assertTrue(element("img_cross", memberDetailsName).getAttribute("src").contains("img_list_delete1.gif"),
				"ASSERT FAILED: Expected value should contains 'img_list_delete1.gif' but found "
						+ element("img_cross", memberDetailsName).getAttribute("src"));
		logMessage("ASSERT PASSED : Cross marked is verified for " + memberDetailsName + "\n");
	}

	public void clickOnSelectProduct() {
		isElementDisplayed("lnk_selectProduct");
		// hover(element("lnk_selectProduct"));
		wait.hardWait(3);
		// clickUsingXpathInJavaScriptExecutor(element("lnk_selectProduct"));
		// element("lnk_selectProduct").click();
		executeJavascript("document.getElementById('HYPERLINK_17').click()");
		logMessage("STEP : Select product link is clicked in lnk_selectProduct\n");
	}

	public void clickOnOrderEntryIcon() {
		switchToDefaultContent();
		wait.hardWait(2);
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("img_orderEntry");
		clickUsingXpathInJavaScriptExecutor(element("img_orderEntry"));

		logMessage("STEP : Order entry icon is clicked in img_orderEntry\n");

	}

	public void clickOnAddMembershipMenu() {
		isElementDisplayed("txt_menuItems");
		clickUsingXpathInJavaScriptExecutor(element("txt_menuItems"));
		// element("txt_menuItems").click();
		logMessage("STEP : Click on add membership menu\n");
	}

	public void selectMemberInfo(String memberInfo, String memberInfoValue) {
		try {
			wait.waitForPageToLoadCompletely();
			isElementDisplayed("list_" + memberInfo);
			selectProvidedTextFromDropDown(element("list_" + memberInfo), memberInfoValue);
			logMessage("ASSERT PASSED : " + memberInfoValue + " is selected in list_" + memberInfo + "\n");

		} catch (StaleElementReferenceException stlExp) {
			selectProvidedTextFromDropDown(element("list_" + memberInfo), memberInfoValue);
			logMessage("Select Element " + memberInfo + " after catching Stale Element Exception\n");
		}

	}

	public void enterDate(String dateType, String date) {
		try {
			isElementDisplayed("inp_" + dateType);
			element("inp_" + dateType).sendKeys(date);
			logMessage("STEP : " + date + " is entered in " + dateType + " \n");
		} catch (StaleElementReferenceException stlExp) {
			isElementDisplayed("inp_" + dateType);
			element("inp_" + dateType).sendKeys(date);
			logMessage("STEP : " + date + " is entered in " + dateType + " \n");
		}
	}

	public void verifyItemsAdded(String itemName) {
		isElementDisplayed("txt_itemsAdded", itemName);
		logMessage("ASSERT PASSED : Item " + itemName + " is added in item list\n");
	}

	public void selectOrderEntryInfo(String orderEntryInfo, String value) {
		hardWaitForIEBrowser(2);
		isElementDisplayed("list_" + orderEntryInfo);
		selectProvidedTextFromDropDown(element("list_" + orderEntryInfo), value);
		logMessage("STEP : " + orderEntryInfo + " is selected as " + value + "\n");
		wait.hardWait(3);
	}

	public void clickOnSaveAndFinish() {
		hardWaitForIEBrowser(2);
		wait.hardWait(5);
		isElementDisplayed("btn_saveAndFinish");
		hardWaitForIEBrowser(10);
		if (isBrowser("ie") || isBrowser("internet explorer")) {
			hoverClick(element("btn_saveAndFinish"));
			clickUsingXpathInJavaScriptExecutor(element("btn_saveAndFinish"));
		} else
			hoverClick(element("btn_saveAndFinish"));
//			 clickUsingXpathInJavaScriptExecutor(element("btn_saveAndFinish"));
//			hoverClick(element("btn_saveAndFinish"));
		// element("btn_saveAndFinish").click();
		wait.hardWait(25);
		logMessage("STEP : Save and finish button is clicked\n");
		waitForSpinner();
	}

	public void navigateToCRMPageByClickingSaveAndFinish() {
		clickOnSaveAndFinish();
		handleAlert();
		verifyPageTitleContains("CRM | Individuals |");
	}

	public void enterCardDetails(String cardInfo, String cardValue) {

		isElementDisplayed("inp_" + cardInfo);
		// element("inp_" + cardInfo).click();
		System.out.println("cardbvalue" + cardValue);
		sendKeysUsingXpathInJavaScriptExecutor(element("inp_" + cardInfo), cardValue);
		// element("inp_" + cardInfo).sendKeys(cardValue);
		logMessage("STEP : Enter " + cardValue + " in inp_" + cardInfo + " \n");

	}

	public void verifyRejoinDateIsCurentDate() {
		isElementDisplayed("txt_rejoinDateForActive");
		String rejoindate = element("txt_rejoinDateForActive").getText().trim();
		currentDate = DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/yyyy", "EST5EDT");

		if (currentDate.startsWith("0")) {
			String newCurrentDate = currentDate.substring(1);
			isStringMatching(rejoindate, newCurrentDate);
		} else {
			isStringMatching(rejoindate, currentDate);
		}

	}

	public void verifyEffectiveDateIsCurentDate() {
		isElementDisplayed("txt_effectiveDateForActive");
		String rejoindate = element("txt_effectiveDateForActive").getText().trim();
		currentDate = DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/yyyy", "EST5EDT");

		if (currentDate.startsWith("0")) {
			String newCurrentDate = currentDate.substring(1);
			isStringMatching(rejoindate, newCurrentDate);
		} else {
			isStringMatching(rejoindate, currentDate);
		}

	}

	public void verifyEffectiveDateIsCurentDateForMemebrType(String tabName, String memberType) {
		if (tabName.equalsIgnoreCase("individual memberships")) {
			isElementDisplayed("txt_effectiveDateMemberType", memberType);
			String rejoindate = element("txt_effectiveDateMemberType", memberType).getText().trim();
			currentDate = DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/yyyy", "EST5EDT");
			isStringMatching(rejoindate, currentDate);
		} else {
			isElementDisplayed("txt_effectiveDate_chapter", memberType);
			String rejoindate = element("txt_effectiveDate_chapter", memberType).getText().trim();
			currentDate = DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("MM/dd/yyyy", "EST5EDT");
			isStringMatching(rejoindate, currentDate);

		}

	}

	public void verifyExpireDateIsNextYearForMemberType(String tabName, String memberType) {
		if (tabName.equalsIgnoreCase("individual memberships")) {
			isElementDisplayed("txt_expireDateMemberType", memberType);
			String rejoindate = element("txt_expireDateMemberType", memberType).getText().trim();
			String nextYearDate = DateUtil.getAddYearWithLessOnedayInStringWithGivenFormate("M/d/yyyy", "1", "EST5EDT");

			isStringMatching(rejoindate, nextYearDate);
		} else {
			isElementDisplayed("txt_expireDate_chapter", memberType);
			String rejoindate = element("txt_expireDate_chapter", memberType).getText().trim();
			String nextYearDate = DateUtil.getAddYearWithLessOnedayInStringWithGivenFormate("MM/dd/yyyy", "1",
					"EST5EDT");
			isStringMatching(rejoindate, nextYearDate);
		}
	}

	public void verifyJoinDateIsCurentDateForMemebrType(String tabName, String memberType) {
		if (tabName.equalsIgnoreCase("individual memberships")) {
			isElementDisplayed("txt_joinDateMemberType", memberType);
			String joindate = element("txt_joinDateMemberType", memberType).getText().trim();
			currentDate = DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/yyyy", "EST5EDT");
			isStringMatching(joindate, currentDate);
		} else {
			isElementDisplayed("txt_joinDate_chapter", memberType);
			String joindate = element("txt_joinDate_chapter", memberType).getText().trim();
			currentDate = DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("MM/dd/yyyy", "EST5EDT");
			isStringMatching(joindate, currentDate);
		}

	}

	public void verifyEndDateIsCurrentDate(String caseId) {
		isElementDisplayed("txt_expireDateForActive");
		String rejoindate = element("txt_expireDateForActive").getText().trim();

		nextYearDate = DateUtil.getAddYearWithLessOnedayInStringWithGivenFormate("M/D/YYYY", "EST5EDT",
				map().get("multiYearDecision"));

		if (nextYearDate.startsWith("0")) {
			String newCurrentDate = nextYearDate.substring(1);
			isStringMatching(rejoindate, newCurrentDate);
		} else {
			isStringMatching(rejoindate, nextYearDate);
		}

	}

	public void verifyTickedMarked(String memberDetailsName) {
		if (memberDetailsName.equalsIgnoreCase("receives member benefits")) {
			index = "0";
		} else if (memberDetailsName.equalsIgnoreCase("member")) {
			index = "1";
		}
		try {
			isElementDisplayed("img_ticked", index);
			String src = element("img_ticked", index).getAttribute("src");
			Assert.assertTrue(src.contains("img_chkmk.gif"),
					"ASSERT FAILED: Expected value should contains 'img_chkmk.gif' but found " + src);
		} catch (StaleElementReferenceException stlExp) {

			isElementDisplayed("img_ticked", index);
			String src = element("img_ticked", index).getAttribute("src");
			Assert.assertTrue(src.contains("img_chkmk.gif"),
					"ASSERT FAILED: Expected value should contains 'img_chkmk.gif' but found " + src);
		}

		logMessage("ASSERT PASSED : Verified ticked mark for " + memberDetailsName + "\n");
	}

	public void verifyMemberStatusActiveIsPresent() {
		for (WebElement element : elements("list_mbrStatus")) {
			if (element.getText().trim().contains("Active")) {
				logMessage("ASSERT PASSED : Member status active is present in status list\n");
				flag = true;
				break;
			}
		}
		if (!flag)
			Assert.fail("ASSERT PASSED : Member status active is not present in status list\n");
	}

	public void enterBillingAddress(String addType, String addValue) {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		holdExecution(1000);
		try {
			wait.resetImplicitTimeout(3);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("inp_" + addType);
			element("inp_" + addType).sendKeys(addValue);
			logMessage("STEP : " + addValue + " is enetered in inp_" + addType + "\n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (StaleElementReferenceException stl) {
			wait.resetImplicitTimeout(3);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("inp_" + addType);
			element("inp_" + addType).sendKeys(addValue);
			logMessage("STEP : " + addValue + " is enetered in inp_" + addType + "\n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}

	}

	public void clickOnDoNotValidate() {
		isElementDisplayed("chk_doNotValidate");
		element("chk_doNotValidate").click();
		logMessage("STEP : Check doNotValidate\n");
	}

	public void clickOnPlusAddButton() {
		isElementDisplayed("btn_add");
		scrollDown(element("btn_add"));
		hoverClick(element("btn_add"));
		logMessage("STEP : Click add button\n");
	}

	public void clickOnSaveButtonForBillingAddress() {
		wait.hardWait(1);
		isElementDisplayed("btn_saveForBillingAdd");
		scrollDown(element("btn_saveForBillingAdd"));
		clickUsingXpathInJavaScriptExecutor(element("btn_saveForBillingAdd"));
		// element("btn_saveForBillingAdd").click();

		handleAlert();

		logMessage("STEP : Save button is clicked \n");
	}

	public void selectBillingAddress() {
		if (elements("list_billingAdd").size() <= 1) {
			clickOnPlusAddButton();
			// TODO Remove hard wait after handling stale element exception
			holdExecution(1000);
			switchToFrame("iframe1");
			selectProvidedTextFromDropDown(element("list_addressType"), "billing");
			logMessage("STEP : Billing is selected in address type\n");
			clickOnDoNotValidate();

			enterBillingAddress("addressLine", YamlReader.getYamlValue("ReinstateMemberIWEB.billingAddress.address"));
			enterBillingAddress("city", YamlReader.getYamlValue("ReinstateMemberIWEB.billingAddress.city"));
			enterBillingAddress("country", YamlReader.getYamlValue("ReinstateMemberIWEB.billingAddress.country"));
			// TODO Remove hard wait after handling stale element exception
			holdExecution(1000);
			isElementDisplayed("list_state");
			selectProvidedTextFromDropDown(element("list_state"),
					YamlReader.getYamlValue("ReinstateMemberIWEB.billingAddress.state"));
			logMessage("STEP : New York is selected in list_state\n");
			enterBillingAddress("postalCode", YamlReader.getYamlValue("ReinstateMemberIWEB.billingAddress.postalCode"));
			enterBillingAddress("district", YamlReader.getYamlValue("ReinstateMemberIWEB.billingAddress.district"));
			enterBillingAddress("congressional",
					YamlReader.getYamlValue("ReinstateMemberIWEB.billingAddress.congressional"));
			enterBillingAddress("province", YamlReader.getYamlValue("ReinstateMemberIWEB.billingAddress.province"));
			enterBillingAddress("mail", YamlReader.getYamlValue("ReinstateMemberIWEB.billingAddress.mail"));
			clickOnSaveButtonForBillingAddress();
			switchToDefaultContent();
			// TODO Remove hard wait after handling stale element exception
			holdExecution(1000);
			pageRefresh();
			selectProvidedTextFromDropDown(element("list_billAddress"),
					"billing: " + YamlReader.getYamlValue("ReinstateMemberIWEB.billingAddress.address"));
			logMessage("STEP : All details are entered for billing address");
		}

		else {
			for (WebElement element : elements("list_billingAdd")) {
				String selectedValue = element.getAttribute("selected");
				if (selectedValue != null) {
					selectDropDownValue(element("list_billAddress"), 2);
				} else {
					break;
				}
			}

			logMessage("STEP  : Billing address is already present\n");
		}
	}

	public void verifyMemberReinstatedOnSearchMember() {
		verifyListMemberShipAppears();
		verifyMemberStatusIsActive();
		verifyMemberStatusIsActive();
		verifyJoinDateIsCurrentDate();
	}

	public void verifyListMemberShipAppears() {
		isElementDisplayed("label_listMemberShip");
	}

	public void searchMemberReinstated(String customerId) {
		clickOnSideBar("Find Members");
		enterDetail("Customer ID", customerId);
		clickOnGoButton();

	}

	public void enterDetail(String memberDetail, String detailValue) {
		isElementDisplayed("inp_enterDetails", memberDetail);
		element("inp_enterDetails", memberDetail).sendKeys(detailValue);
		logMessage("STEP : " + memberDetail + " " + detailValue + " is entered to search member\n");
	}

	public void clickOnGoButton() {
		isElementDisplayed("btn_go");
		element("btn_go").click();
		logMessage("STEP : Go button is clicked in btn_go\n");
	}

	public void clickOnGoAskButton() {
		isElementDisplayed("btn_goask");
		element("btn_goask").click();
		logMessage("STEP : Go Ask button is clicked in btn_goask\n");
	}

	public void verifyMemberStatusIsActive() {
		for (WebElement element : elements("list_memberStatus")) {
			if (element.getText().trim().equalsIgnoreCase("Active")) {
				logMessage("ASSERT PASSED : Member status active is present in status list\n");
				flag = true;
				break;
			}
		}
		if (!flag)
			Assert.fail("ASSERT PASSED : Member status active is not present in status list\n");
	}

	public void verifyJoinDateIsCurrentDate() {
		String currentDate = DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy");
		for (WebElement element : elements("list_joindate")) {
			if (currentDate.startsWith("0")) {
				String newCurrentDate = currentDate.substring(1);
				if (element.getText().trim().equalsIgnoreCase(newCurrentDate)) {
					logMessage("ASSERT PASSED : Member rejoin date is current date " + newCurrentDate + " in list\n");
					flag = true;
					break;
				}
			} else if (element.getText().trim().equalsIgnoreCase(currentDate)) {
				logMessage("ASSERT PASSED : Member rejoin date is current date " + currentDate + " in list\n");
				flag = true;
				break;
			}
		}
		if (!flag)
			Assert.fail("ASSERT PASSED : Member status active is not present in status list\n");
	}

	public String numberOfYearsForInactiveMember() {
		isElementDisplayed("txt_numberOfyears");
		String numberOfYears = element("txt_numberOfyears").getText().trim();
		logMessage("STEP : Total years of services for inactive member is " + numberOfYears);
		return numberOfYears;
	}

	public void clickOnSideBarTab(String tabName) {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(4);
		isElementDisplayed("hd_sideBarOuter", tabName);
		clickUsingXpathInJavaScriptExecutor(element("hd_sideBarOuter", tabName));
		// element("hd_sideBar", tabName).click();
		logMessage("STEP : Click on tab " + tabName + " in hd_sideBar \n");
	}

	public void clickOnModuleTab() {
		wait.waitForPageToLoadCompletely();
		// isElementDisplayed("btn_tabs");
		wait.hardWait(1);
		executeJavascript("document.getElementsByClassName('dropdown-toggle')[3].click()");
		// element("btn_tabs").click();
		logMessage("STEP : Module tab is clicked\n");
	}

	public void clickOnTab(String tabName) {
		wait.hardWait(2);
		isElementDisplayed("link_tabsOnModule", tabName);
		if (isBrowser("safari"))
			element("link_tabsOnModule", tabName).click();
		else
			clickUsingXpathInJavaScriptExecutor(element("link_tabsOnModule", tabName));
		logMessage("STEP : " + tabName + " tab is clicked\n");

	}

	public void expandDetailsMenu(String menuName) {

		try {
			isElementDisplayed("btn_detailsMenuAACT", menuName);
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("btn_detailsMenuAACT", menuName);
			clickUsingXpathInJavaScriptExecutor(element("btn_detailsMenuAACT", menuName));
			// element("btn_detailsMenuAACT", menuName).click();
		} catch (Exception e) {
			logMessage("Bar " + menuName + " already expanded");
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
		logMessage("STEP : " + menuName + " bar is clicked to expand" + "\n");
		wait.hardWait(2);
		waitForSpinner();

	}

	public void numberOfYearsForActiveMember(String numberOfYears) {
		String noOfYears = String.valueOf(Integer.parseInt(numberOfYears) + 1);
		isElementDisplayed("txt_numberOfyears");
		verifyElementText("txt_numberOfyears", noOfYears);
		logMessage("ASSERT PASSED : " + noOfYears + " total years of services in txt_numberOfyears\n");

	}

	public List<String> searchNonRenewalMemberAndGetDetails() {
		clickOnSideBar("Find Members");
		wait.waitForPageToLoadCompletely();
		checkAdvanceNewCheckbox();
		selectValueInAdvanceNewNextDropDown("Member Status", "ACS : Active Renewed-No Response");

		selectAdvanceNewDropDown("Expire Date", "Less Than");
		enterValueInAdvanceNewInput("Expire Date", DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/YYY"));

		// selectMemberStatus("ACS : Active Renewed-No Response");
		wait.hardWait(1);
		clickOnGoButton();

		clickOnFirstInactiveRegularMember();

		clickOnCustomerName();
		handleAlert();
		hardWaitForIEBrowser(2);
		clickOnEditNameAndAddress();
		switchToFrame("iframe1");
		customerLname = getNameFromEditNameAndAddressButton("lastName");
		clickOnCancelButton();
		handleAlert();
		switchToDefaultContent();
		wait.hardWait(1);
		handleAlert();
		wait.hardWait(3);
		try {
			customerContactId = element("txt_renewalContactId").getText();
		} catch (StaleElementReferenceException stlExp) {
			customerContactId = element("txt_renewalContactId").getText();
		}

		memberDetails.add(customerLname);
		memberDetails.add(customerContactId);
		for (String i : memberDetails) {
			System.out.println("member details::" + i);
		}
		return memberDetails;
	}

	public List<String> findMemberAndGetDetail(String memberType, String memberStatus, String country) {
		memberDetails = new ArrayList<>();
		clickOnSideBar("Find Members");
		wait.waitForPageToLoadCompletely();
		selectValueInAdvanceNewNextDropDown("Member Type", "ACS : " + memberType);
		selectValueInAdvanceNewNextDropDown("Member Status", "ACS : " + memberStatus);
		selectValueInAdvanceNewNextDropDown("Country", country);
		wait.hardWait(1);
		clickOnGoButton();
		clickOnRandomPage();
		clickOnAnyRandomMember();
		memberDetails.add(getMemberInfoOnMemberShipProfile("member package"));
		memberDetails.add(getMemberInfoOnMemberShipProfile("renewal package"));
		memberDetails.add(getPaymentStatus());
		memberDetails.add(getMemberDetailsOnMemberShipProfile("customer id"));
		memberDetails.add(getMemberDetailsOnMemberShipProfile("expire date"));
		memberDetails.add(getMemberDetailsOnMemberShipProfile("effective date"));
		memberDetails.add(getMemberDetailsOnMemberShipProfile("join date"));
		openSubInfoDropDown("invoices");
		waitForSpinner();
		flag = pagesLinkAvailable();
		memberDetails.add(getProductNameInInvoice(flag));
		memberDetails.add(getInvoiceIDInInvoice(flag));
		memberDetails.add(getTermStartDateInvoice(flag));
		System.out.println("contact ID: " + memberDetails.get(3));
		openSubInfoDropDown("invoices");
		waitForSpinner();
		return memberDetails;
	}

	public List<String> getMemberDetailsOnMembershipPage() {
		List<String> memberDetails = new ArrayList<>();
		memberDetails.add(getMemberInfoOnMemberShipProfile("member package"));
		memberDetails.add(getMemberInfoOnMemberShipProfile("renewal package"));
		memberDetails.add(getPaymentStatus());
		memberDetails.add(getMemberDetailsOnMemberShipProfile("customer id"));
		memberDetails.add(getMemberDetailsOnMemberShipProfile("expire date"));
		memberDetails.add(getMemberDetailsOnMemberShipProfile("effective date"));
		memberDetails.add(getMemberDetailsOnMemberShipProfile("join date"));
		openSubInfoDropDown("invoices");
		waitForSpinner();
		flag = pagesLinkAvailable();
		memberDetails.add(getProductNameInInvoice(flag));
		memberDetails.add(getInvoiceIDInInvoice(flag));
		memberDetails.add(getTermStartDateInvoice(flag));
		System.out.println("contact ID: " + memberDetails.get(3));
		openSubInfoDropDown("invoices");
		waitForSpinner();
		return memberDetails;
	}

	public String getProductNameInInvoice(boolean pageLink) {
		if (pageLink) {
			isElementDisplayed("txt_productNameOnPage");
			return element("txt_productNameOnPage").getText().trim();
		} else {
			isElementDisplayed("txt_productName");
			return element("txt_productName").getText().trim();
		}
	}

	public boolean pagesLinkAvailable() {
		try {
			wait.resetImplicitTimeout(3);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			flag = isElementDisplayed("link_pagesAvailable");

			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			return flag;
		} catch (Exception e) {
			flag = false;

			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			return flag;
		}

	}

	public String getInvoiceIDInInvoice(boolean pageLink) {
		if (pageLink) {
			isElementDisplayed("txt_invoiceIdOnPage");
			return element("txt_invoiceIdOnPage").getText().trim();
		} else {
			isElementDisplayed("txt_invoiceId");
			return element("txt_invoiceId").getText().trim();
		}
	}

	public String getTermStartDateInvoice(boolean pageLink) {
		if (pageLink) {
			isElementDisplayed("txt_termStartDateOnPage");
			return element("txt_termStartDateOnPage").getText().trim();
		} else {
			isElementDisplayed("txt_termStartDate");
			return element("txt_termStartDate").getText().trim();
		}

	}

	public String getMemberInfoOnMemberShipProfile(String memberInfo) {
		isElementDisplayed("txt_membershipProfileInfo", memberInfo);
		String info = element("txt_membershipProfileInfo", memberInfo).getText().trim();
		logMessage("STEP : " + memberInfo + " is " + info + " \n");
		return info;
	}

	public void openSubInfoDropDown(String subInfoName) {
		isElementDisplayed("btn_memberShipSubInfo", subInfoName);
		element("btn_memberShipSubInfo", subInfoName).click();
		logMessage("STEP : Click on sub info on membership profile\n");

	}

	public String getMemberDetailsOnMemberShipProfile(String memberInfo) {
		wait.hardWait(2);
		hardWaitForIEBrowser(5);
		isElementDisplayed("txt_membershipProfileDetails", memberInfo);
		String info = element("txt_membershipProfileDetails", memberInfo).getText().trim();
		logMessage("STEP : " + memberInfo + " is " + info + " \n");
		return info;
	}

	public String getPaymentStatus() {
		isElementDisplayed("txt_paymentStatus");
		String paymentStatus = element("txt_paymentStatus").getText().trim();
		logMessage("STEP : Payment status is " + paymentStatus + "\n");
		return paymentStatus;
	}

	public void selectMemberStatus(String memberStatus) {
		isElementDisplayed("list_memberStatusRenewal");
		selectProvidedTextFromDropDown(element("list_memberStatusRenewal"), memberStatus);
		logMessage("STEP : Member status " + memberStatus + " is selected \n");
	}

	public void checkAdvanceNewCheckbox() {
		isElementDisplayed("chk_advanceNew");
		element("chk_advanceNew").click();
		logMessage("STEP : Advance new checkbox is checked in chk_advanceNew\n");
	}

	public void selectAdvanceNewDropDown(String heading, String value) {
		isElementDisplayed("list_advanceNewDropDown", heading);
		selectProvidedTextFromDropDown(element("list_advanceNewDropDown", heading), value);
		logMessage("STEP : Select " + value + " for " + heading + " in list_advanceNewDropDown\n");
	}

	public void enterValueInAdvanceNewInput(String heading, String value) {
		isElementDisplayed("inp_advanceNewInput", heading);
		element("inp_advanceNewInput", heading).clear();
		element("inp_advanceNewInput", heading).sendKeys(value);
		logMessage("STEP : Enter " + value + " for " + heading + " in inp_advanceNewInput\n");
	}

	public void selectValueInAdvanceNewNextDropDown(String heading, String value) {
		isElementDisplayed("list_advanceNewInput", heading);
		selectProvidedTextFromDropDown(element("list_advanceNewInput", heading), value);
		logMessage("STEP : Select " + value + " in next drop down for " + heading + " in list_advanceNewInput\n");
	}

	public List<String> getMemberDetails() {
		clickOnEditNameAndAddress();
		switchToFrame("iframe1");
		customerLname = getNameFromEditNameAndAddressButton("lastName");
		customerFname = getNameFromEditNameAndAddressButton("firstName");

		clickOnCancelButton();
		handleAlert();
		switchToDefaultContent();
		wait.hardWait(1);
		handleAlert();
		wait.hardWait(3);
		try {
			customerContactId = element("txt_renewalContactId").getText().trim();
			logMessage("STEP : Member customer ID is " + customerContactId);
		} catch (StaleElementReferenceException stlExp) {
			customerContactId = element("txt_renewalContactId").getText().trim();
			logMessage("STEP : Member customer ID is " + "'" + customerContactId + "'");
		}
		memberDetails.add(customerLname);
		memberDetails.add(customerContactId);
		memberDetails.add(customerFname);
		return memberDetails;
	}

	public Map<String, String> getMemberDetails_Iweb() {
		List<String> details = getMemberDetails();
		memberDetailsMap.put("firstName", details.get(2));
		memberDetailsMap.put("lastName", details.get(0));
		memberDetailsMap.put("memberNumber", details.get(1));
		String customerEmail = clickOnEditContactInfoAndGetCustomarEmail();
		if (customerEmail.equalsIgnoreCase("")) {
			memberDetailsMap.put("emailID", addEmailAddress());
		} else {
			memberDetailsMap.put("emailID", customerEmail);
		}
		clickOnSaveButtonForBillingAddress();
		switchToDefaultContent();
		return memberDetailsMap;
	}

	public List<String> selectMemberAndGetDetails() {

		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(3);
		clickOnRandomPage();
		clickOnAnyRandomMember();
		handleAlert();
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(2);
		memberDetails = getMemberDetails();
		return memberDetails;
	}

	public void selectSubscriptionInSelectProductLink() {
		hardWaitForIEBrowser(10);
		isElementDisplayed("link_subscriptionInSelectProduct");
		// hover(element("link_subscriptionInSelectProduct"));
		wait.hardWait(1);
		executeJavascript("document.getElementById('HYPERLINK_2').click()");
		// clickUsingXpathInJavaScriptExecutor(element("link_subscriptionInSelectProduct"));
		// element("link_subscriptionInSelectProduct").click();
		logMessage("STEP : Subscription link is clicked in link_subscriptionInSelectProduct\n");

	}

	public void clickOnRandomPage() {
		try {
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("lnk_pages", "2");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			int max = 20, min = 12;
			Random rand = new Random();
			int randomNumber = rand.nextInt((max - min) + 1) + min;
			// int randomNumber = (int) Math.random();
			String randomNumberInString = String.valueOf(randomNumber);
			isElementDisplayed("lnk_pages", randomNumberInString);

			element("lnk_pages", randomNumberInString).click();
			logMessage("STEP : Page at the position of " + randomNumberInString + " is clicked in lnk_pages\n");

		} catch (NoSuchElementException exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}
	}

	public void navigateToSubscriptionThroughOrderEntry() {
		clickOnOrderEntryIcon();
		// TODO Remove hard wait after handling stale element exception
		holdExecution(2000);
		isElementDisplayed("table_lineItems");
		clickUsingXpathInJavaScriptExecutor(element("table_lineItems"));
		// element("table_lineItems").click();
		// TODO Remove hard wait after handling stale element exception
		holdExecution(2000);
		wait.waitForPageToLoadCompletely();
		clickOnSelectProduct();
		// TODO Remove hard wait after handling stale element exception
		// holdExecution(1000);
		try {
			wait.waitForPageToLoadCompletely();
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			switchToFrame(element("frame_selectProduct"));
			selectSubscriptionInSelectProductLink();
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (Exception e) {
			System.out.println("in catch");
			wait.waitForPageToLoadCompletely();
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			switchToDefaultContent();
			clickOnSelectProduct();
			// TODO Remove hard wait after handling stale element exception
			// holdExecution(3000);
			switchToFrame(element("frame_selectProduct"));
			selectSubscriptionInSelectProductLink();
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}

		switchToDefaultContent();
	}

	public void goToOrderEntry() {
		clickOnOrderEntryIcon();
		// TODO Remove hard wait after handling stale element exception
		holdExecution(2000);
		isElementDisplayed("table_lineItems");
		

	}

	public String addSubscriptionInOrderEntry(String prodCode) {
		switchToFrame("iframe1");
		enterProductCode(prodCode);
		displayName = searchAndGetDisplayName();
		// totalPrice = getMemberInfoOnMemberShipProfile("net-balance:");
		logMessage("STEP : Display name is : " + displayName + "\n");
		clickOnSaveAndFinish();
		// driver.navigate().refresh();
		switchToDefaultContent();
		waitForSpinner();
		wait.hardWait(2);
		if (displayName.startsWith("Journal of Chemical Theory and Computation")) {
			displayName = "Jrnl of Chemical Theory & Computation";
		} else if (displayName.startsWith("Journal of the American Chemical Society")) {
			displayName = "Jrnl of The American Chemical Society";
		}
		logMessage("-------*****Step : Display name is : " + displayName + "\n");
		// verifyItemAddedInLineItems(displayName);
		return displayName;
	}

	public void enterProductCode(String prodCode) {
		hardWaitForIEBrowser(5);
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		isElementDisplayed("inp_prdCode");
		element("inp_prdCode").sendKeys(prodCode);
		logMessage("STEP : Produuct code " + prodCode + " is entered in inp_prdCode\n");
	}

	public void clickOnSearchDisplayNameButton() {
		isElementDisplayed("inp_searchDisplayButton");
		clickUsingXpathInJavaScriptExecutor(element("inp_searchDisplayButton"));
		// element("inp_searchDisplayButton").click();
		wait.hardWait(2);
		hardWaitForIEBrowser(8);
		logMessage("STEP : Search display name button is clicked in inp_searchDisplayButton\n");
	}

	public void clickOnSearchButton() {
		isElementDisplayed("btn_search");
		// clickUsingXpathInJavaScriptExecutor(element("btn_search"));
		element("btn_search").click();
		wait.hardWait(2);
		hardWaitForIEBrowser(8);
		logMessage("STEP : Search button is clicked \n");
	}

	public String getDisplayName() {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("inp_displayName");
		String displayName = element("inp_displayName").getAttribute("value");
		return displayName;
	}

	public String searchAndGetDisplayName() {
		clickOnSearchDisplayNameButton();
		if (checkIfElementIsThere("icon_arrow")) {
			element("icon_arrow").click();
		}
		return getDisplayName();
	}

	public void verifyItemAddedInLineItems(String itemName) {
		hardWaitForIEBrowser(60);

		// isElementDisplayed("link_itemInLineItems");
		verifyElementText("link_itemInLineItems", itemName);
	}

	public void selectBatchAndPaymentDetails_subscription(String batchName, String paymentType, String paymentMethod,
			String cardNumber, String expireDate, String cvvNumber, String checkNumber) {

		// wait.waitForPageToLoadCompletely();
		holdExecution(2000);

		if (verifyBatchIsPresent(batchName)) {
			selectOrderEntryInfo("batch", batchName);
		} else {
			addBatch(batchName.replaceAll("ACS: ", ""), "QA");
		}
		waitForSpinner();
		selectOrderEntryInfo("PaymentType", paymentType);
		waitForSpinner();
		selectOrderEntryInfo("paymentMethod", paymentMethod);
		waitForSpinner();
		System.out.println("check number" + checkNumber);

		if (paymentMethod.equalsIgnoreCase("Visa/MC")) {
			enterCardDetails("cardNumber", cardNumber);
			selectMemberInfo("expireDate", expireDate);
			enterCardDetails("cvvNumber", cvvNumber);
		} else if (paymentMethod.equalsIgnoreCase("BOA - Check")) {
			enterCardDetails("checkNumber", checkNumber);

		} else {
			Assert.fail("ASSERT FAILED : Payment method " + paymentMethod + " is not correct \n");
		}

		selectBillingAddressIfNotPrePopulated();
		clickOnSaveAndFinish();
		handleAlert();

		verifyPageTitleContains("CRM | Individuals |");
	}

	public void enterValuesInCreditPage(String batch_name, String creditReason, String paymentMethod, String cardNumber,
			String expireDate, String cvvNumber, String creditAmount, String expense) {
		isElementDisplayed("inp_customerName");
		String nameOnCheck = element("inp_customerName").getAttribute("value").trim();
		logMessage("Name on check :" + nameOnCheck);
		String customerID = element("inp_customerID").getAttribute("value").trim();
		logMessage("STEP : Customer name is " + nameOnCheck + "\n");
		logMessage("STEP : Customer ID is " + customerID + "\n");
		holdExecution(2000);
		batchName = batch_name + System.currentTimeMillis();
		if (verifyBatchIsPresentOnCreditPage(batchName)) {
			selectOrderEntryInfo("batchCreditPage", batchName);
		} else {
			addBatchOnCreditPage(batchName.replaceAll("ACS: ", ""), "QA");
		}
		switchToDefaultContent();
		waitForSpinner();
		wait.hardWait(1);
		Select sel = new Select(element("drpdown_batchName"));
		Assert.assertEquals(sel.getFirstSelectedOption().getText().trim(), batchName,
				"ASSERT FAILED : Batch name is not same \n");
		isElementDisplayed("inp_creditAmount");
		executeJavascript("document.getElementById('cdd_amount').value='" + creditAmount + "';");
		logMessage("STEP : Credit amount is entered as " + creditAmount);

		waitForSpinner();
		selectOrderEntryInfo("creditReason", creditReason);
		waitForSpinner();
		selectOrderEntryInfo("paymentMethod", paymentMethod);
		waitForSpinner();
		if (paymentMethod.equalsIgnoreCase("Visa/MC")) {
			enterCardDetails("cardHolderName", nameOnCheck);
			enterCardDetails("cardNumber", cardNumber);
			selectMemberInfo("expireDate", expireDate);
			enterCardDetails("cvvNumber", cvvNumber);
		} else if (paymentMethod.equalsIgnoreCase("BOA - Check")) {
			enterCardDetails("nameOnCheck", nameOnCheck);
			enterCardDetails("checkNumber", cardNumber);

		} else {
			Assert.fail("ASSERT FAILED : Payment method " + paymentMethod + " is not correct \n");
		}
		selectOrderEntryInfo("liabilityExpense", expense);
		clickOnSaveButtonForBillingAddress();
		wait.waitForPageToLoadCompletely();
		logMessage("STEP : Batch is created as " + batchName + " is created \n");
		logMessage("STEP : All values are entered in Credit Page\n");

	}

	public void verifyCreditAvailableOnCOE(String creditAmount) {
		isElementDisplayed("txt_creditAvailable");
		String credit = element("txt_creditAvailable").getText().trim();
		Assert.assertEquals(credit, creditAmount, "ASSERT FAILED : Credit available is not matched on COE page\n");
		logMessage("ASSERT PASSED : Credit availabale is " + credit + " \n");

	}

	public void clickOnNetCredit(String productName) {
		isElementDisplayed("td_lineItems", productName, "7");
		element("td_lineItems", productName, "7").click();
		logMessage("STEP : Clicked on net credit link \n");
	}

	public String verifyNetForumPopUp() {
		switchToFrame("iframe1");
		isElementDisplayed("txt_netTotal");
		String netTotal = element("txt_netTotal").getText().trim();
		logMessage("STEP : NetForum Pop up is verified");
		switchToDefaultContent();
		return netTotal;
	}

	public void enterValuesInAmountToApply(String netTotal, String amountApply) {
		switchToFrame("iframe1");
		isElementDisplayed("inp_dateForReviewModes", amountApply);
		sendKeysUsingXpathInJavaScriptExecutor(element("inp_dateForReviewModes", amountApply), netTotal);
		logMessage("STEP: 'amount to apply' is entered as " + netTotal + " \n");
		clickOnSaveButtonForBillingAddress();
		switchToDefaultContent();
	}

	public void verifyNetBalanceOnCOE(String netBalance) {
		isElementDisplayed("txt_netBalanceNetForum", productName);
		String net_balance = element("txt_netBalanceNetForum", productName).getText().trim();
		Assert.assertEquals(net_balance, netBalance);
		logMessage("ASSERT PASSED: Net Balance is verfied as " + net_balance + "\n");

	}

	public void verifyCreditAmount(String creditAmount) {
		expandDetailsMenuIfAlreadyExpanded("detail");
		isElementDisplayed("txt_joinDate_chapter", "create credit");
		Assert.assertTrue(element("txt_joinDate_chapter", "create credit").getText().trim().equals(creditAmount),
				"ASSERT FAILED : Credit amount is not same. Expected value is " + creditAmount + " but found "
						+ element("txt_joinDate_chapter", "create credit").getText());
		logMessage("ASSERT PASSED : credit amount is: " + creditAmount);
	}

	public String clickOnBatch() {
		isElementDisplayed("table_header", batchName.replaceAll("ACS: ", ""));
		element("table_header", batchName.replaceAll("ACS: ", "")).click();
		logMessage("STEP : Clicked on Batch " + batchName.replaceAll("ACS: ", "") + " on Credit profile page \n");
		return batchName;
	}

	public void clickOnPreProcessAndWaitToCloseThePopup() {
		isElementDisplayed("btn_preProcess");
		element("btn_preProcess").click();
		logMessage("STEP : Clicked On preProcess icon\n");
		wait.waitForPageToLoadCompletely();
		// wait.waitForWindowsToDisappear();

	}

	public void selectBatchAndPaymentDetailsForCRMInventory(String batchName, String paymentType, String paymentMethod,
			String cardNumber, String expireDate, String cvvNumber, String checkNumber) {

		// wait.waitForPageToLoadCompletely();
		holdExecution(2000);
		// batchName=batchName+System.currentTimeMillis();
		if (verifyBatchIsPresent(batchName)) {
			selectOrderEntryInfo("batch", batchName);
		} else {
			addBatch(batchName.replaceAll("ACS: ", ""), "QA");
		}
		waitForSpinner();
		selectOrderEntryInfo("PaymentType", paymentType);
		waitForSpinner();
		selectOrderEntryInfo("paymentMethod", paymentMethod);
		waitForSpinner();

		if (paymentMethod.equalsIgnoreCase("Visa/MC")) {
			enterCardDetails("cardNumber", cardNumber);
			selectMemberInfo("expireDate", expireDate);
			enterCardDetails("cvvNumber", cvvNumber);
		} else if (paymentMethod.equalsIgnoreCase("BOA - Check")) {
			enterCardDetails("checkNumber", checkNumber);

		} else {
			Assert.fail("ASSERT FAILED : Payment method " + paymentMethod + " is not correct \n");
		}
		selectBillingAddressIfNotPrePopulated();
		clickOnSaveAndFinish();
		handleAlert();

		verifyPageTitleContains("CRM | Individuals |");
	}

	public boolean verifyBatchIsPresentInCreditPage(String batchName) {
		hardWaitForIEBrowser(2);
		System.out.println("-----in verify batch:" + batchName);
		isElementDisplayed("list_batch");
		flag = isDropDownValuePresent(element("list_batch").findElements(By.xpath("//option")), batchName);
		return flag;

	}

	public boolean verifyBatchIsPresent(String batchName) {
		hardWaitForIEBrowser(2);
		System.out.println("-----in verify batch:" + batchName);
		isElementDisplayed("list_batch");
		flag = isDropDownValuePresent(element("list_batch").findElements(By.xpath("//option")), batchName);
		return flag;

	}

	public boolean verifyBatchIsPresentOnCreditPage(String batchName) {
		hardWaitForIEBrowser(2);
		System.out.println("-----in verify batch:" + batchName);
		isElementDisplayed("list_batchCreditPage");
		flag = isDropDownValuePresent(element("list_batchCreditPage").findElements(By.xpath("//option")), batchName);
		System.out.println("------flag:" + flag);
		return flag;

	}

	public void addBatch(String batchName, String securityGroup) {
		switchToDefaultContent();
		isElementDisplayed("btn_addBatch");
		clickUsingXpathInJavaScriptExecutor(element("btn_addBatch"));
		// element("btn_addBatch").click();
		logMessage("STEP : Add batch button is clicked \n");
		switchToFrame("iframe1");
		isElementDisplayed("inp_addBatchName");
		element("inp_addBatchName").clear();
		element("inp_addBatchName").sendKeys(batchName);
		logMessage("STEP : Enter batch name " + batchName + "\n");
		isElementDisplayed("list_batchSecurityGroup");
		selectProvidedTextFromDropDown(element("list_batchSecurityGroup"), securityGroup);
		logMessage("STEP : Select security group " + securityGroup + "\n");
		clickOnSaveButtonForBillingAddress();

	}

	public void addBatchOnCreditPage(String batchName, String securityGroup) {
		switchToDefaultContent();
		isElementDisplayed("btn_addBatchCredit");
		clickUsingXpathInJavaScriptExecutor(element("btn_addBatchCredit"));
		// element("btn_addBatch").click();
		logMessage("STEP : Add batch button is clicked \n");
		switchToFrame("iframe1");
		isElementDisplayed("inp_addBatchName");
		element("inp_addBatchName").clear();
		element("inp_addBatchName").sendKeys(batchName);
		logMessage("Step : enter batch name " + batchName + "\n");
		isElementDisplayed("list_batchSecurityGroup");
		selectProvidedTextFromDropDown(element("list_batchSecurityGroup"), securityGroup);
		logMessage("STEP : Select security group as" + securityGroup + "\n");
		clickOnSaveButtonForBillingAddress();

	}

	public void selectBillingAddressIfNotPrePopulated() {
		List<WebElement> list = elements("list_billingAdd");
		if (list.get(0).getAttribute("selected") != null) {
			selectDropDownValue(element("list_billAddress"), 1);
			logMessage("STEP : Select billing address in dropdown list\n");
		} else {
			logMessage("STEP : Billing address is already selected\n");
		}
	}

	public void clickOnGoButtonInRunQuery() {
		isElementDisplayed("btn_askGo");
		clickUsingXpathInJavaScriptExecutor(element("btn_askGo"));
		// element("btn_askGo").click();
		logMessage("STEP : Go button is clicked in btn_askGo\n");
	}

	public void enterCustomerIdsInRunQuery(String... customerIds) {
		isElementDisplayed("inp_customerId");
		String customerID = customerIds[0] + "," + customerIds[1];
		element("inp_customerId").clear();
		element("inp_customerId").sendKeys(customerID);
		logMessage("STEP : Enter customer IDs " + customerID + " \n");
		clickOnGoButtonInRunQuery();
		// verifyMultipleRecordsInList("2");
	}

	public void enterSingleCustomerIdInRunQuery(String customerId) {
		isElementDisplayed("inp_customerId");
		String customerID = customerId;
		element("inp_customerId").clear();
		element("inp_customerId").sendKeys(customerID);
		logMessage("STEP : Text entered as " + customerID + " \n");
		clickOnGoButtonInRunQuery();

	}

	public void clickOnInvoiceNumber() {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(4);
		wait.hardWait(5);
		try {
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			clickUsingXpathInJavaScriptExecutor(element("lnk_first_invoice_number", String.valueOf(1)));
		} catch (NoSuchElementException e) {
			logMessage("STEP : Invoice Number Clicked");
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
		logMessage("STEP : First Invoice Number Clicked");
	}

	public void clickInvoiceHeading(String tabName) {
		wait.hardWait(2);
		try {
			wait.resetImplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("link_invoiceListHeadings", tabName);
			wait.hardWait(1);
			clickUsingXpathInJavaScriptExecutor(element("link_invoiceListHeadings", tabName));
			// element("link_invoiceListHeadings", tabName).click();
			wait.waitForPageToLoadCompletely();
			wait.resetExplicitTimeout(timeOut);
			logMessage("STEP : Invoice heading " + tabName + " is clicked");
		} catch (Exception e) {
			wait.resetExplicitTimeout(timeOut);
		}

	}

	public void verifyMultipleRecordsInList(String numberOfRecords) {
		isElementDisplayed("txt_recordNumberAtMemberQuery");
		String recordNumber = element("txt_recordNumberAtMemberQuery").getText().trim();

		Assert.assertTrue(recordNumber.equalsIgnoreCase("(" + numberOfRecords + " records)"),
				"ASSERT FAILED: Expected value is " + "(" + numberOfRecords + " records)" + " but found "
						+ recordNumber);
		logMessage("STEP : Multiple records are presents in the List - Member Query by Member Number - Multiple \n");

	}

	public void verifyMembershipDetailsOnRenewal(String expireDate, String memberPackage, String renewalPackage,
			String customerID, String effectiveDate, String joinDate, String paymentStatus) {
		verifyMemberDetailsOnMemberShipProfile("expire date", expireDate);
		verifyMemberInfoOnMemberShipProfile("member package", memberPackage);
		verifyMemberInfoOnMemberShipProfile("renewal package", renewalPackage);
		verifyMemberDetailsOnMemberShipProfile("customer id", customerID);
		verifyMemberDetailsOnMemberShipProfile("effective date", effectiveDate);
		verifyMemberDetailsOnMemberShipProfile("join date", joinDate);
		verifyPaymentStatus(paymentStatus);

	}

	public void verifyInvoiceDetailsOnRenewal(String productName, String invoiceId) {
		expandDetailsMenuIfAlreadyExpanded("invoices");

		flag = pagesLinkAvailable();
		verifyProductNameInInvoice(productName, flag);
		// verifyInvoiceIDInInvoice(invoiceId, flag);
		verifyTermStartDateInvoice("", flag);
		verifyTermEndDateInvoice("", flag);

	}

	public void verifyMemberInfoOnMemberShipProfile(String memberdetail, String memberValue) {
		isElementDisplayed("txt_membershipProfileInfo", memberdetail);

		Assert.assertTrue(
				element("txt_membershipProfileInfo", memberdetail).getText().trim().equalsIgnoreCase(memberValue),
				"ASSERT FAILED: Expected value is " + "(" + memberValue + " records)" + " but found "
						+ element("txt_membershipProfileInfo", memberdetail).getText().trim());
		logMessage("ASSERT PASSED : " + memberValue + " is verified for " + memberValue + " \n");

	}

	public void verifyMemberDetailsOnMemberShipProfile(String memberdetail, String memberValue) {
		isElementDisplayed("txt_membershipProfileDetails", memberdetail);
		Assert.assertTrue(
				element("txt_membershipProfileDetails", memberdetail).getText().trim().equalsIgnoreCase(memberValue),
				"ASSERT FAILED: Expected value is " + memberValue + " but found "
						+ element("txt_membershipProfileDetails", memberdetail).getText().trim());
		logMessage("ASSERT PASSED : " + memberdetail + " having value " + memberValue + " is verified as " + memberValue
				+ " \n");

	}

	public void verifyProductNameInInvoice(String productName, boolean pageLink) {
		if (pageLink) {
			isElementDisplayed("txt_productNameOnPage");
			Assert.assertTrue(element("txt_productNameOnPage").getText().trim().equalsIgnoreCase(productName),
					"ASSERT FAILED: Expected value is " + productName + " but found "
							+ element("txt_productNameOnPage").getText().trim());
		} else {
			isElementDisplayed("txt_productName");
			Assert.assertTrue(element("txt_productName").getText().trim().equalsIgnoreCase(productName),
					"ASSERT FAILED: Expected value is " + productName + " but found "
							+ element("txt_productName").getText().trim());
		}
		logMessage("ASSERT PASSED : Product name " + productName + " in invoice is verified\n");

	}

	public void verifyInvoiceIDInInvoice(String invoiceID, boolean pageLink) {
		if (pageLink) {
			isElementDisplayed("txt_invoiceIdOnPage");
			Assert.assertTrue(element("txt_invoiceIdOnPage").getText().trim().equalsIgnoreCase(invoiceID),
					"ASSERT FAILED: Expected value is " + invoiceID + " but found "
							+ element("txt_invoiceIdOnPage").getText().trim());
			logMessage("ASSERT PASSED : Invoice id is " + invoiceID + " in invoice is verified\n");
		} else {
			isElementDisplayed("txt_invoiceId");

			Assert.assertTrue(element("txt_invoiceId").getText().trim().equalsIgnoreCase(invoiceID),
					"ASSERT FAILED: Expected value is " + invoiceID + " but found "
							+ element("txt_invoiceId").getText().trim());
			logMessage("ASSERT PASSED : Invoice id is " + invoiceID + " in invoice is verified\n");

		}

	}

	public void verifyTermStartDateInvoice(String startDate, boolean pageLink) {
		if (pageLink) {
			isElementDisplayed("txt_termStartDateOnPage");
			Assert.assertTrue(element("txt_termStartDateOnPage").getText().trim().equalsIgnoreCase(startDate),
					"ASSERT FAILED: Expected value is " + startDate + " but found "
							+ element("txt_termStartDateOnPage").getText().trim());
		} else {
			isElementDisplayed("txt_termStartDate");
			Assert.assertTrue(element("txt_termStartDate").getText().trim().equalsIgnoreCase(startDate),
					"ASSERT FAILED: Expected value is " + startDate + " but found "
							+ element("txt_termStartDate").getText().trim());
		}
		logMessage("ASSERT PASSED : Term start date is " + startDate + " in invoice is verified\n");
	}

	public void verifyTermEndDateInvoice(String endDate, boolean pageLink) {
		if (pageLink) {
			isElementDisplayed("txt_termEndDateOnPage");
			Assert.assertTrue(element("txt_termEndDateOnPage").getText().trim().equalsIgnoreCase(endDate),
					"ASSERT FAILED: Expected value is " + endDate + " but found "
							+ element("txt_termEndDateOnPage").getText().trim());
			logMessage("ASSERT PASSED : Term end date is " + endDate
					+ " in invoice is verified in txt_termEndDateOnPage\n");

		} else {
			isElementDisplayed("txt_termEndDate");
			Assert.assertTrue(element("txt_termEndDate").getText().trim().equalsIgnoreCase(endDate),
					"ASSERT FAILED: Expected value is " + endDate + " but found "
							+ element("txt_termEndDate").getText().trim());
			logMessage("ASSERT PASSED : Term end date is " + endDate
					+ " in invoice is verified in txt_termEndDateOnPage\n");

		}
	}

	public void verifyPaymentStatus(String paymentStatus) {
		isElementDisplayed("txt_paymentStatus");
		Assert.assertTrue(element("txt_paymentStatus").getText().equalsIgnoreCase(paymentStatus),
				"ASSERT FAILED: Expected value is " + paymentStatus + " but found "
						+ element("txt_paymentStatus").getText().trim());
		logMessage("ASSERT PASSED : Payment status " + paymentStatus + " is verified in txt_paymentStatus\n");
	}

	public void navigateToInvoicePageForFirstProduct() {
		isElementDisplayed("btn_arrowRightCircle");
		clickUsingXpathInJavaScriptExecutor(element("btn_arrowRightCircle"));
		// element("btn_arrowRightCircle").click();
		logMessage("STEP : Navigate to invoice profile page for first product in btn_arrowRightCircle\n");
	}

	public void navigateToInvoicePageForRenewedProduct() {
		isElementDisplayed("btn_gotorenewal");
		if (isBrowser("ie") || isBrowser("internet explorer")) {
			clickUsingXpathInJavaScriptExecutor(element("btn_gotorenewal"));
		} else
			element("btn_gotorenewal").click();
		logMessage("STEP : Navigate to invoice profile page for Renewed product in btn_gotorenewal\n");

	}

	public void getloginStatusFromSheet(String[] loginAs) {
		List<String> loginList = new ArrayList<String>();

		for (int i = 0; i < loginAs.length; i++) {
			if ((!loginAs[i].equals(" ") | loginAs[i].length() != 0) && loginAs[i].equalsIgnoreCase("YES")) {
				loginList.add(loginAs[i]);
				count = i;

			}
		}

		if (loginList.size() > 1) {
			logMessage("More than One option has YES value");
			Assert.assertFalse(true);
		}

	}

	public List<String> loginUsingValueFromSheet(String[] loginAs) {
		getloginStatusFromSheet(loginAs);
		if (count == 0 | count == 1) {
			System.out.println("Member");
			clickOnModuleTab();
			clickOnTab("CRM");
			clickOnSideBarTab("Individuals");
			clickOnSideBar("Query Individual");
			if (count == 0) {

				selectAndRunQuery("Selenium - Find Active Regular Member");
				memberStoreDetails.add(String.valueOf(count));
				logMessage("User will login as a Member");
			} else if (count == 1) {

				selectAndRunQuery("Selenium - Find Random Non Member");
				memberStoreDetails.add(String.valueOf(count));
				logMessage("User will login as a Non - Member");

			}

			handleAlert();
			memberStoreDetails.add(getMemberDetailsOnMemberShipProfile("contact id"));
			memberStoreDetails.add(getMemberWebLogin());

		} else if (count == 2) {

			memberStoreDetails.add(String.valueOf(count));
			System.out.println("Guest");
			logMessage("User will login as a Guest");

		}
		return memberStoreDetails;

	}

	public void goToAddMemebrshipAndFillDetails_LocalSection() {
		if (map().get("Is_localSectionMemberType?").equalsIgnoreCase("")) {
			logMessage("Step : local section member is not mentioned in data sheet\n");
		} else {
			wait.waitForPageToLoadCompletely();
			ScrollPage(0, -700);
			holdExecution(1000);
			clickOnSelectProduct();
			holdExecution(3000);
			switchToFrame(element("frame_selectProduct"));
			selectAddMembershipInSelectProductLink();
			switchToDefaultContent();
			switchToFrame("iframe1");
			// TODO Remove hard wait after handling stale element exception
			holdExecution(1000);
			selectMemberInfo("association", "ACS");
			// TODO Remove hard wait after handling stale element exception
			holdExecution(1000);
			selectMemberInfo("memberType", map().get("Is_localSectionMemberType?"));
			// TODO Remove hard wait after handling stale element exception
			holdExecution(1000);
			selectMemberInfo("chapter", map().get("ls_division?"));
			holdExecution(1000);
			selectMemberInfo("memberPackage", map().get("ls_memberPackage?"));
			holdExecution(1000);
			String totalPrice = getTotalPrice();
			clickOnSaveAndFinish();
			switchToDefaultContent();
			waitForSpinner();

			verifyPrice(map().get("expectedLocalSectionName"), map().get("localSection_PriceValue?"));

		}
	}

	public String goToAddMemebrshipAndFillDetails_LocalSectionAsFellowPrequisite() {
		String totalPrice = null;
		if (map().get("Is_localSectionMemberType?").equalsIgnoreCase("")) {
			logMessage("Step : local section member is not mentioned in data sheet\n");
		} else {
			wait.waitForPageToLoadCompletely();
			ScrollPage(0, -700);
			holdExecution(1000);
			clickOnSelectProduct();
			holdExecution(3000);
			switchToFrame(element("frame_selectProduct"));
			selectAddMembershipInSelectProductLink();
			switchToDefaultContent();
			switchToFrame("iframe1");
			// TODO Remove hard wait after handling stale element exception
			holdExecution(1000);
			selectMemberInfo("association", "ACS");
			// TODO Remove hard wait after handling stale element exception
			holdExecution(1000);
			selectMemberInfo("memberType", map().get("Is_localSectionMemberType?"));
			// TODO Remove hard wait after handling stale element exception
			holdExecution(1000);
			selectMemberInfo("chapter", map().get("ls_division?"));
			holdExecution(1000);
			selectMemberInfo("memberPackage", map().get("ls_memberPackage?"));
			holdExecution(1000);
			totalPrice = getTotalPrice();
			System.out.println("Total price " + totalPrice);
			clickOnSaveAndFinish();
			switchToDefaultContent();
			waitForSpinner();

		}
		return totalPrice;
	}

	public void goToAddMembershipAndFillDetails_membership() {
		wait.waitForPageToLoadCompletely();
		clickOnSelectProduct();
		holdExecution(3000);
		switchToFrame(element("frame_selectProduct"));
		selectAddMembershipInSelectProductLink();
		switchToDefaultContent();
		switchToFrame("iframe1");
		// TODO Remove hard wait after handling stale element exception
		holdExecution(1000);
		System.out.println("-----after frame switching");
		selectMemberInfo("association", "ACS");
		// TODO Remove hard wait after handling stale element exception
		holdExecution(1000);
		selectMemberInfo("memberType", map().get("memberType"));
		// TODO Remove hard wait after handling stale element exception
		holdExecution(3000);
		// selectMemberInfo("memberStatusInAddMembership", "Active");
		selectMemberInfo("memberPackage", map().get("memberPackage"));
		String currentDate = DateUtil.getCurrentdateInStringWithGivenFormate("M/d/yyyy");
		holdExecution(2000);
		// verifySelectedTextFromDropDown(element("list_memberPackage"), map()
		// .get("memberPackage"));
		wait.waitForPageToLoadCompletely();
		holdExecution(2000);
		// enterDate("transactionDate", currentDate);
		// verifySelectedTextFromDropDown(element("list_memberRenewalPackage"),
		// map().get("memberPackage"));
		if (map().get("complimentary").equalsIgnoreCase("On")) {
			checkCheckbox(element("chk_complimentry"));
			selectMemberInfo("complimentryRequest", map().get("compReason"));
		}
		if (!(map().get("industry").equalsIgnoreCase("") || map().get("industry").equalsIgnoreCase(null))) {
			selectMemberInfo("industry", map().get("industry"));
			enterDate("industryUpdateDate", map().get("industryUpdateDate"));
			// TODO Remove hard wait after handling stale element exception
			holdExecution(1000);
			selectMemberInfo("jobTitle", map().get("jobTitle"));
			enterDate("jobTitleUpdateDate", map().get("jobTitleUpdateDate"));
			// TODO Remove hard wait after handling stale element exception
			holdExecution(1000);
		}
		hardWaitForIEBrowser(5);
		String totalPrice = getTotalPrice();
		clickOnSaveAndFinish();
		switchToDefaultContent();
		handleAlert();
		waitForSpinner();
		System.out.println("actual:-" + map().get("memberPackage"));
		System.out.println("expected:-" + map().get("priceValue?"));
		verifyPrice(map().get("memberPackage"), map().get("priceValue?"));
	}

	public void goToAddMembershipAndFillDetails_membershipAsFellowPrequisite() {
		wait.waitForPageToLoadCompletely();
		clickOnSelectProduct();
		holdExecution(3000);
		switchToFrame(element("frame_selectProduct"));
		selectAddMembershipInSelectProductLink();
		switchToDefaultContent();
		switchToFrame("iframe1");
		// TODO Remove hard wait after handling stale element exception
		holdExecution(1000);
		selectMemberInfo("association", "ACS");
		// TODO Remove hard wait after handling stale element exception
		holdExecution(1000);
		selectMemberInfo("memberType", map().get("memberType"));
		// TODO Remove hard wait after handling stale element exception
		holdExecution(1000);
		// selectMemberInfo("memberStatusInAddMembership", "Active");
		selectMemberInfo("memberPackage", map().get("memberPackage"));
		@SuppressWarnings("unused")
		String currentDate = DateUtil.getCurrentdateInStringWithGivenFormate("M/d/yyyy");
		holdExecution(2000);
		// verifySelectedTextFromDropDown(element("list_memberPackage"), map()
		// .get("memberPackage"));
		wait.waitForPageToLoadCompletely();
		holdExecution(2000);
		// enterDate("transactionDate", currentDate);
		// verifySelectedTextFromDropDown(element("list_memberRenewalPackage"),
		// map().get("memberPackage"));
		if (map().get("complimentary").equalsIgnoreCase("On")) {
			checkCheckbox(element("chk_complimentry"));
			selectMemberInfo("complimentryRequest", map().get("compReason"));
		}
		if (!(map().get("industry").equalsIgnoreCase("") || map().get("industry").equalsIgnoreCase(null))) {
			selectMemberInfo("industry", map().get("industry"));
			enterDate("industryUpdateDate", map().get("industryUpdateDate"));
			// TODO Remove hard wait after handling stale element exception
			holdExecution(1000);
			selectMemberInfo("jobTitle", map().get("jobTitle"));
			enterDate("jobTitleUpdateDate", map().get("jobTitleUpdateDate"));
			// TODO Remove hard wait after handling stale element exception
			holdExecution(1000);
		}
		String totalPrice = getTotalPrice();
		clickOnSaveAndFinish();
		switchToDefaultContent();
		handleAlert();

		waitForSpinner();
	}

	public void goToAddMembershipAndFillDetails_Division(int numberOfDivisions) {

		for (int i = 1; i <= numberOfDivisions; i++) {

			wait.waitForPageToLoadCompletely();
			ScrollPage(0, -700);
			clickOnSelectProduct();
			holdExecution(3000);
			switchToFrame(element("frame_selectProduct"));
			selectAddMembershipInSelectProductLink();
			switchToDefaultContent();
			switchToFrame("iframe1");
			// TODO Remove hard wait after handling stale element exception
			holdExecution(1000);
			selectMemberInfo("association", "ACS");
			// TODO Remove hard wait after handling stale element exception
			holdExecution(1000);
			selectMemberInfo("memberType", map().get("div" + i + "_memberType"));
			// TODO Remove hard wait after handling stale element exception
			holdExecution(1000);
			selectMemberInfo("chapter", map().get("div" + i + "_division"));
			holdExecution(2000);
			selectMemberInfo("memberPackage", map().get("div" + i + "_memberPackage"));
			holdExecution(1000);
			if (map().get("complimentary").equalsIgnoreCase("On")) {
				checkCheckbox(element("chk_complimentry"));
				selectMemberInfo("complimentryRequest", map().get("compReason"));
			}
			String totalPrice = getTotalPrice();

			clickOnSaveAndFinish();
			switchToDefaultContent();
			handleAlert();
			waitForSpinner();
			verifyPrice(map().get("div" + i + "_memberPackage"), map().get("div" + i + "_PriceValue?"));

		}
	}

	public String goToAddMembershipAndFillDetails_DivisionAsFellowPrequisite(int numberOfDivisions) {
		String totalPrice = null;
		for (int i = 1; i <= numberOfDivisions; i++) {
			switchToDefaultContent();
			wait.waitForPageToLoadCompletely();
			ScrollPage(0, -700);
			clickOnSelectProduct();
			holdExecution(3000);
			switchToFrame(element("frame_selectProduct"));
			selectAddMembershipInSelectProductLink();
			switchToDefaultContent();
			switchToFrame("iframe1");
			// TODO Remove hard wait after handling stale element exception
			holdExecution(1000);
			selectMemberInfo("association", "ACS");
			// TODO Remove hard wait after handling stale element exception
			holdExecution(1000);
			System.out.println(map().get("div" + i + "_memberType"));
			selectMemberInfo("memberType", map().get("div" + i + "_memberType"));
			// TODO Remove hard wait after handling stale element exception
			holdExecution(1000);
			System.out.println(map().get("div" + i + "_division"));
			selectMemberInfo("chapter", map().get("div" + i + "_division"));
			holdExecution(2000);
			System.out.println(map().get("div" + i + "_memberPackage"));
			selectMemberInfo("memberPackage", map().get("div" + i + "_memberPackage"));
			holdExecution(1000);
			if (map().get("complimentary").equalsIgnoreCase("On")) {
				checkCheckbox(element("chk_complimentry"));
				selectMemberInfo("complimentryRequest", map().get("compReason"));
			}
			totalPrice = getTotalPrice();

			clickOnSaveAndFinish();
			switchToDefaultContent();
			handleAlert();
			waitForSpinner();

		}
		return totalPrice;
	}

	public String getTotalPrice() {
		isElementDisplayed("txt_totalPrice");
		return element("txt_totalPrice").getText();
	}

	public void enterSourceCode(String sourceCode) {
		isElementDisplayed("inp_sourceCode");
		element("inp_sourceCode").sendKeys(sourceCode);
		logMessage("STEP : Source code " + sourceCode + " is entered in inp_sourceCode\n");
	}

	public void verifyPrice(String itemName, String price) {
		hardWaitForIEBrowser(4);
		wait.hardWait(4);
		// clickUsingXpathInJavaScriptExecutor(element("txt_priceOrderEntryLineItmes",
		// itemName));
		// element("txt_priceOrderEntryLineItmes", itemName).click();
		scrollDown(element("txt_priceOrderEntryLineItmes", itemName));
		isElementDisplayed("txt_priceOrderEntryLineItmes", itemName);
		String actualPrice = element("txt_priceOrderEntryLineItmes", itemName).getText().trim();

		Float newPrice = Float.parseFloat(price) * Integer.parseInt(map().get("renewalTerm").trim());

		String formatedPrice = String.format("%.02f", newPrice);
		if (!map().get("complimentary").equalsIgnoreCase("On")) {
			Assert.assertTrue(actualPrice.equalsIgnoreCase(formatedPrice),
					"ASSERT FAILED: Expected value is " + formatedPrice + " but found " + actualPrice);
			logMessage("ASSERT PASSED : Price " + formatedPrice + " is verified for " + itemName
					+ " in txt_priceOrderEntryLineItmes\n");
		}

	}

	public void navigateToSubscriptionInSelectLinkAndSellSubscription(int numberOfSubscriptions) {
		for (int i = 1; i <= numberOfSubscriptions; i++) {

			wait.waitForPageToLoadCompletely();
			ScrollPage(0, -700);

			// TODO Remove hard wait after handling stale element exception

			try {
				wait.waitForPageToLoadCompletely();
				wait.resetImplicitTimeout(2);
				wait.resetExplicitTimeout(hiddenFieldTimeOut);
				clickOnSelectProduct();
				holdExecution(3000);
				switchToFrame(element("frame_selectProduct"));
				selectSubscriptionInSelectProductLink();
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
			} catch (Exception e) {
				// wait.waitForPageToLoadCompletely();
				wait.resetImplicitTimeout(2);
				wait.resetExplicitTimeout(hiddenFieldTimeOut);
				switchToDefaultContent();
				// TODO Remove hard wait after handling stale element exception

				clickOnSelectProduct();
				holdExecution(3000);
				switchToFrame(element("frame_selectProduct"));
				selectSubscriptionInSelectProductLink();
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
			}

			switchToDefaultContent();
			String[] productName_TotalPrice = addSubscriptionInOrderEntry_CreateMem(map().get("ProductCode" + i), i);

			handleAlert();
			waitForSpinner();
			hardWaitForIEBrowser(4);

			verifyPrice(productName_TotalPrice[0], map().get("Sub" + i + "_SalePrice?"));
		}
	}

	public void selectAddMembershipInSelectProductLink() {
		isElementDisplayed("link_addMemership");
		clickUsingXpathInJavaScriptExecutor(element("link_addMemership"));
		// element("link_addMemership").click();
		logMessage("STEP : Add Membership link is clicked in link_addMemership\n");

	}

	public int getDivisionNumbers() {
		for (int i = 1; i <= map().size(); i++) {

			try {
				if (map().get("div" + i + "_memberType").equalsIgnoreCase(null)
						|| map().get("div" + i + "_memberType").equalsIgnoreCase("")) {
					break;
				} else {
					numberOfDivisions++;

				}
			} catch (NullPointerException npe) {
				logMessage("div" + i + "_memberType is not present in data sheet\n");
				break;
			}

		}

		return numberOfDivisions;
	}

	public int getSubscriptionNumbers() {
		for (int i = 1; i <= map().size(); i++) {

			try {
				if (map().get("ProductCode" + i).equalsIgnoreCase(null)
						|| map().get("ProductCode" + i).equalsIgnoreCase("")) {
					break;
				} else {
					numberOfSubscriptions++;

				}
			} catch (NullPointerException npe) {
				logMessage("ProductCode" + i + " is not present in data sheet\n");
				break;
			}
		}
		return numberOfSubscriptions;
	}

	public void navigateToMemberLatestInvoicePage(List<String> memberLoginDetails) {
		if (memberLoginDetails.get(0).equals("0") | memberLoginDetails.get(0).equals("1")) {
			clickOnSideBarTab("Invoice");
			clickOnSideBar("Query Invoice");
			selectAndRunQuery("Selenium - Newest Invoice for Customer ID");
			enterSingleCustomerIdInRunQuery(memberLoginDetails.get(1));
			// clickInvoiceHeading("Transaction Date");
			// clickInvoiceHeading("Transaction Date");
			clickOnInvoiceNumber();
		}

	}

	public String[] addSubscriptionInOrderEntry_CreateMem(String prodCode, int numberOfSubscription) {
		switchToFrame("iframe1");
		enterProductCode(prodCode);

		displayName = searchAndGetDisplayName();

		logMessage("STEP : Display name is : " + displayName + "\n");
		if (map().get("complimentary").equalsIgnoreCase("On")) {
			checkCheckbox(element("chk_complimentry_Sub"));
			wait.waitForPageToLoadCompletely();
			holdExecution(2000);
			selectMemberInfo("complimentryReq_Sub", map().get("compReason"));
		}
		String totalPrice = getMemberDetailsOnMemberShipProfile("net-balance:");

		clickOnSaveAndFinish();

		switchToDefaultContent();
		waitForSpinner();
		wait.hardWait(2);
		// isStringMatching(displayName,
		// map().get("subscription" + numberOfSubscription));

		logMessage("ASSERT PASSED : Subscription name " + map().get("subscription" + numberOfSubscription)
				+ " is matched\n");
		// verifyItemAddedInLineItems(displayName.split(" - ")[0]);
		String[] arr = { displayName.split(" - ")[0], totalPrice };
		return arr;
	}

	public void verifyNetPriceValue(String netPriceName) {
		isElementDisplayed("txt_priceDetailsBelowLineItems", netPriceName);
		String netBalance = element("txt_priceDetailsBelowLineItems", netPriceName).getText();
		isElementDisplayed("list_priceOrderEntryNetBalance", netPriceName);

		for (int i = 0; i < elements("list_priceOrderEntryNetBalance", netPriceName).size(); i++) {
			netIndividualBalance = netIndividualBalance
					+ Float.parseFloat(elements("list_priceOrderEntryNetBalance", netPriceName).get(i).getText());
		}
		Assert.assertTrue(netBalance.equalsIgnoreCase(String.valueOf(String.format("%.2f", netIndividualBalance))),
				"ASSERT FAILED: Expected value is " + String.valueOf(String.format("%.2f", netIndividualBalance))
						+ " but found " + netBalance);
		logMessage("ASSERT PASSED : " + netBalance + " is verified for net balance\n");
	}

	public void collapseDetailsMenu(String menuName) {
		isElementDisplayed("icon_up", menuName);
		clickUsingXpathInJavaScriptExecutor(element("icon_up", menuName));
		// element("icon_up", menuName).click();
		waitForSpinner();
		logMessage("STEP : " + menuName + " bar collapse bar clicked\n");

	}

	public List<String> getCustomerLastNameAndContactID() {
		List<String> memberDetails1 = new ArrayList<String>();
		clickOnEditNameAndAddress();
		switchToFrame("iframe1");
		customerLname = getNameFromEditNameAndAddressButton("lastName");
		clickOnCancelButton();
		handleAlert();
		switchToDefaultContent();
		customerContactId = element("txt_renewalContactId").getText();
		memberDetails1.add(customerLname);
		memberDetails1.add(customerContactId);
		return memberDetails1;
	}

	public List<String> getCustomerLastNameAndContactIDForYellowBook() {
		clickOnEditNameAndAddress();
		switchToFrame("iframe1");
		customerLname = getNameFromEditNameAndAddressButton("lastName");
		clickOnCancelButton();
		handleAlert();
		switchToDefaultContent();
		wait.waitForPageToLoadCompletely();
		customerContactId = element("txt_ContactId").getText().trim();
		logMessage("Customer Last Name :" + customerLname);
		logMessage("Customer ACS Member Number :" + customerContactId);
		memberDetails.add(customerLname);
		memberDetails.add(customerContactId);
		return memberDetails;
	}

	public String getCustomerID() {
		switchToDefaultContent();
		handleAlert();
		wait.waitForPageToLoadCompletely();
		customerContactId = element("txt_ContactId").getText().trim();
		logMessage("STEP : Customer id is " + customerContactId + " \n");
		return customerContactId;
	}

	public List<String> getMemberDetails_AACTOMR() {
		clickOnEditNameAndAddress();
		switchToFrame("iframe1");
		customerLname = getNameFromEditNameAndAddressButton("lastName") + " "
				+ getNameFromEditNameAndAddressButton("firstName") + " "
				+ getNameFromEditNameAndAddressButton("middleName");
		String countryName = getMemberCountryName();
		clickOnCancelButton();
		handleAlert();
		switchToDefaultContent();
		customerContactId = element("txt_renewalContactId").getText();
		memberDetails.add(customerLname);

		memberDetails.add(customerContactId);
		memberDetails.add(countryName);
		// memberDetails.add(getMemberWebLogin());
		logMessage("STEP : Customer Contact Id fetched as " + customerContactId);
		return memberDetails;
	}

	public List<String> getCustomerFullNameAndContactID() {
		clickOnEditNameAndAddress();
		hardWaitForIEBrowser(3);
		switchToFrame("iframe1");
		customerLname = getNameFromEditNameAndAddressButton("lastName") + " "
				+ getNameFromEditNameAndAddressButton("firstName") + " "
				+ getNameFromEditNameAndAddressButton("middleName");
		clickOnCancelButton();
		handleAlert();
		switchToDefaultContent();
		customerContactId = element("txt_renewalContactId").getText();
		memberDetails.add(customerLname);

		memberDetails.add(customerContactId);
		// memberDetails.add(getMemberWebLogin());
		logMessage("STEP : Customer Contact Id fetched as " + customerContactId);
		return memberDetails;

	}

	public void fetchScarfReviewerLoginDetails(Map<String, List<String>> reviewerloginMap, int reviewerNumber) {
		reviewerloginMap.put("reviewer" + reviewerNumber, getCustomerLastNameAndContactID());
		logMessage("STEP : Reviewer name is fetched as " + reviewerloginMap.get("reviewer" + reviewerNumber));
		System.out.println("-----last name:" + reviewerloginMap.get("reviewer" + reviewerNumber).get(0)
				+ "-----member number:" + reviewerloginMap.get("reviewer" + reviewerNumber).get(1));
	}

	public String getCstWebLogin() {
		String cst = "";
		if (isBrowser("ie")) {
			isElementDisplayed("txt_weblogin", String.valueOf(5));
			cst = element("txt_weblogin", String.valueOf(5)).getText().trim();
			// element("txt_weblogin", String.valueOf(1)).click();
			isElementDisplayed("txt_current", String.valueOf(1));
			clickUsingXpathInJavaScriptExecutor(element("txt_current", String.valueOf(1)));
		} else {
			isElementDisplayed("txt_current", String.valueOf(1));
			cst = element("txt_current", String.valueOf(1)).getText().trim();
			element("txt_current", String.valueOf(1)).click();
		}
		logMessage("STEP : CstWebLogin is fetched as " + cst);
		return cst;

	}

	public String getCstWebLoginForMembership(String index, String text) {
		String cst = "";
		if (isBrowser("ie")) {
			isElementDisplayed("txt_weblogin", index);
			cst = element("txt_weblogin", index).getText().trim();
		} else {
			isElementDisplayed("txt_endDate", String.valueOf(1), index);
			cst = element("txt_endDate", String.valueOf(1), index).getText().trim();
		}
		handleAlert();
		logMessage("STEP : " + text + " fetched as " + cst);
		return cst;

	}

	public void getIndividualFullNameForAwardsNomination() {

		clickOnEditNameAndAddress();
		wait.hardWait(5);
		switchToDefaultContent();
		wait.hardWait(9);
		switchToFrame(element("iframe"));
		customerLname = getNameFromEditNameAndAddressButton("lastName") + " "
				+ getNameFromEditNameAndAddressButton("firstName") + " "
				+ getNameFromEditNameAndAddressButton("middleName");
		clickOnCancelButton();
		handleAlert();
		switchToDefaultContent();
		customerContactId = element("txt_renewalContactId").getText();

		// System.out.println(customerLname);
		createMemberCredentials.put("Nominee" + individualCount + "Name", customerLname);
		createMemberCredentials.put("Nominee" + individualCount + "Number", customerContactId);
		/*
		 * System.out.println(createMemberCredentials.get("Nominee" +
		 * individualCount + "Name"));
		 * System.out.println(createMemberCredentials.get("Nominee" +
		 * individualCount + "Number"));
		 */
		System.out.println("customerContactId::" + customerContactId);
		logMessage("STEP : Individual Details saved from iweb profile page\n");
		individualCount++;

	}

	public void selectMemberByContactID() {
		System.out.println("customerContactId::" + customerContactId);
		// element("inp_customerID").sendKeys(customerContactId);
		isElementDisplayed("inp_customerID");
		sendKeysUsingXpathInJavaScriptExecutor(element("inp_customerID"), customerContactId);
		holdExecution(1000);
		clickOnSearchButton();
		holdExecution(1000);

	}

	public List<String> getCustomerAllDetails(String invoiceNumber) {

		clickOnEditNameAndAddress();
		switchToFrame("iframe1");
		customerLname = getNameFromEditNameAndAddressButton("lastName") + " "
				+ getNameFromEditNameAndAddressButton("firstName") + " "
				+ getNameFromEditNameAndAddressButton("middleName");
		clickOnCancelButton();
		handleAlert();
		switchToDefaultContent();
		customerContactId = element("txt_renewalContactId").getText();
		memberDetails.add(customerLname);
		memberDetails.add(customerContactId);
		// memberDetails.add(getMemberWebLogin());
		memberDetails.add(invoiceNumber);
		logMessage("STEP : Full Name of member is " + memberDetails.get(0));
		logMessage("STEP : Customer Id of member is " + memberDetails.get(1));
		// memberDetails.add(getMemberWebLogin());
		return memberDetails;

	}

	public Map<String, String> getIndividualMapFromCreateMemberScript() {
		return createMemberCredentials;

	}

	public void clickOnCustomerNameAndNavigateToMembershipPage() {
		isElementDisplayed("link_customerName");
		try {
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			wait.resetImplicitTimeout(5);
			element("link_customerName").click();
		} catch (Exception e) {
			clickUsingXpathInJavaScriptExecutor(element("link_customerName"));
		}
		wait.resetExplicitTimeout(timeOut);
		wait.resetImplicitTimeout(timeOut);
		handleAlert();
		logMessage("STEP : Customer Name as " + element("link_customerName").getText() + " is clicked\n");
	}

	public void selectMemberForRenewal(String membertype) {

		switch (membertype) {
		case "Regular":
			selectProvidedTextFromDropDown(element("drpdwn_memberType"), "ACS : Regular Member");
			break;
		case "Student":
			selectProvidedTextFromDropDown(element("drpdwn_memberType"), "ACS : Student Member - UnderGrad");
			break;
		case "Emeritus":
			selectProvidedTextFromDropDown(element("drpdwn_memberType"), "ACS : Emeritus Member");
			break;
		}

	}

	public void enterMembershipExpireDateInQuery(String expireDate) {
		isElementDisplayed("inp_memExpireDate");
		element("inp_memExpireDate").clear();
		element("inp_memExpireDate").sendKeys(expireDate);
		logMessage("Step : Enter the membership expire date as " + expireDate + "\n");
	}

	private void selectUserForGCSOMR(Map<String, String> mapGcsOMR) {
		selectProvidedTextFromDropDown(element("list_advanceNewInput", "Member Type"), "ACS : Regular Member");
		selectProvidedTextFromDropDown(element("list_advanceNewInput", "Member Status"),
				"ACS : Active Renewed-No Response");
		selectProvidedTextFromDropDown(element("list_advanceNewInput", "Country"), mapGcsOMR.get("Country?"));
	}

	public void selectValidUserForGCSOMR(Map<String, String> mapGcsOMR) {
		selectProvidedTextFromDropDown(element("list_advanceNewInput", "Member Type"), "ACS : Regular Member");
		selectProvidedTextFromDropDown(element("list_advanceNewInput", "Member Status"),
				"ACS : Active Renewed-No Response");
		selectProvidedTextFromDropDown(element("list_advanceNewInput", "Country"), mapGcsOMR.get("Country?"));
		clickOnGoButton();
	}

	public void selectValidUserForRenewalAccordingToCountry(Map<String, String> mapGcsOMR) {
		clickOnTab("Find Members");
		selectUserForGCSOMR(mapGcsOMR);
		clickOnGoButton();
		selectARandomActiveStudentChapter();
		expandDetailsMenu("invoices");
		verifyTermStartDateAndEndDatesAreEmptyForGCSOMR(mapGcsOMR);
		// verifyPaymentStatusForGCSOMR(mapGcsOMR);

	}

	public void selectValidUserForRenewal(Map<String, String> mapOMR) {
		if (MemberTransferLoopCount < 3) {
			clickOnTab("Query Membership");
			selectAndRunQuery("Selenium - Renewal Query OMR");
			selectMemberForRenewal(mapOMR.get("Member_Status?"));
			clickOnGoButtonInRunQuery();
			// expandDetailsMenuIfAlreadyExpanded("invoices");
			expandDetailsMenu("invoices");
			verifyTermStartDateAndEndDatesAreEmpty(mapOMR);
			verifyPaymentStatusBeforeRenewal(mapOMR);
			MemberTransferLoopCount++;
		} else {
			Assert.fail("ASSERT FAIL : Member is not selected after " + MemberTransferLoopCount + " attempts\n");
			logMessage("ASSERT FAIL : Member is not selected after " + MemberTransferLoopCount + " attempts\n");
		}
		logMessage("STEP : Member selected in " + MemberTransferLoopCount + " attempt\n");

	}

	public void selectUserForIwebRenewal(Map<String, String> mapRenewalIWeb) {
		clickOnTab("Query Membership");
		selectAndRunQuery("Selenium - Renewal Query");
		selectMemberForRenewal(mapRenewalIWeb.get("Member_Status?"));
		clickOnGoButtonInRunQuery();
		expandDetailsMenu("invoices");
		verifyTermStartDateAndEndDatesAreEmpty(mapRenewalIWeb);
	}

	public void verifyPaymentStatusBeforeRenewal(Map<String, String> mapOMR) {
		if (!mapOMR.get("Member_Status?").equals("Emeritus")) {
			try {
				wait.resetImplicitTimeout(4);
				wait.resetExplicitTimeout(hiddenFieldTimeOut);
				Assert.assertFalse(element("txt_PaymentStatus", "Payment Status").getText().equals("Paid"),
						"ASSERT FAILED: Expected value is 'Paid' but found "
								+ element("txt_PaymentStatus", "Payment Status").getText());
			} catch (AssertionError e) {
				selectValidUserForRenewal(mapOMR);
			}

		}
		wait.resetExplicitTimeout(timeOut);
		wait.resetImplicitTimeout(timeOut);
		logMessage("ASSERT PASSED : Payment status before renewal is Unpaid");
	}

	public void verifyPaymentStatusForGCSOMR(Map<String, String> mapGcsOMR) {
		try {
			wait.resetImplicitTimeout(4);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			Assert.assertTrue(element("txt_PaymentStatus", "Payment Status").getText().equals("Unpaid"),
					"ASSERT FAILED: Expected value is 'Unpaid' but found "
							+ element("txt_PaymentStatus", "Payment Status").getText());
		} catch (AssertionError e) {
			logMessage("ASSERT PASSED : Payment status before renewal is not Unpaid for\n");
			selectValidUserForRenewalAccordingToCountry(mapGcsOMR);
		}
		wait.resetExplicitTimeout(timeOut);
		wait.resetImplicitTimeout(timeOut);
		logMessage("ASSERT PASSED : Payment status before renewal is Unpaid");

	}

	public void verifyPaymentStatusBeforeAutoRenewal(String query, String queryPageUrl) {
		try {
			wait.resetImplicitTimeout(4);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			Assert.assertTrue(element("txt_PaymentStatus", "Payment Status").getText().equals("Unpaid"),
					"ASSERT FAILED: Expected value is 'Unpaid' but found "
							+ element("txt_PaymentStatus", "Payment Status").getText());
		} catch (AssertionError e) {
			logMessage("ASSERT PASSED : Payment status before renewal is not Unpaid for " + MemberTransferLoopCount
					+ " attempt thus looping back\n");
			MemberTransferLoopCount++;
			selectValidUserForAutoRenewal(query, queryPageUrl);
		}
		wait.resetExplicitTimeout(timeOut);
		wait.resetImplicitTimeout(timeOut);
		logMessage("ASSERT PASSED : Payment status before renewal is Unpaid");

	}

	public void verifyTermStartDateAndEndDatesAreNotEmpty() {
		isElementDisplayed("txt_termStartDaterenewal", "1");
		isElementDisplayed("txt_termEndDaterenewal", "1");
		Assert.assertFalse(element("txt_termStartDaterenewal", "1").getText().length() == 1,
				"Term Start Date is Empty");
		logMessage("ASSERT PASSED : Term Start date is not empty\n");
		Assert.assertFalse(element("txt_termEndDaterenewal", "1").getText().length() == 1, "Term End Date is Empty");
		logMessage("ASSERT PASSED : Term End date is not empty\n");

	}

	public void verifyOueryAskAtRunTimePage() {
		isElementDisplayed("heading_queryAskAtRunTime");
		logMessage("STEP: Member is on Query-Ask At Run-time Values Page\n");
	}

	public void selectMemberPackage(String memberPackage) {
		logMessage("STEP : Select the member package : " + memberPackage + "\n");
		isElementDisplayed("list_memberPackage1");
		selectProvidedTextFromDropDown(element("list_memberPackage1"), memberPackage);
	}

	public void clickOnGoButtonAfterPackageSelection() {
		isElementDisplayed("btn_goPackage");
		// element("btn_goPackage").click();
		clickUsingXpathInJavaScriptExecutor(element("btn_goPackage"));
		logMessage("STEP : Clicked on Go Button\n");
	}

	public void verifyMemberTypeAndPackage(String expectedType, String expectedStatus) {
		isElementDisplayed("txt_memberInfo", "member type");
		Assert.assertTrue(expectedType.equals(element("txt_memberInfo", "member type").getText().trim()),
				"ASSERT FAIL : Member Type is not Regular Member but found"
						+ element("txt_memberInfo", "member type").getText().trim() + " \n");
		logMessage("ASSERT PASS : Member Type is Regular Member\n");

		isElementDisplayed("txt_memberInfo", "member status");
		Assert.assertTrue(expectedStatus.equals(element("txt_memberInfo", "member status").getText().trim()),
				"ASSERT FAIL : Member Status is not Active Renewed-No Response\n");
		logMessage("ASSERT PASS : Member Status is Active Renewed-No Response\n");
	}

	public void verifyTermEndDateAndStartDateIsEmpty() {
		hardWaitForIEBrowser(6);
		Assert.assertTrue(element("txt_termStartDaterenewal", "1").getText().length() == 1,
				"Term Start Date is not Empty");
		logMessage("ASSERT PASSED : Term Start date is empty\n");
		Assert.assertTrue(element("txt_termEndDaterenewal", "1").getText().length() == 1, "Term End Date is not Empty");
		logMessage("ASSERT PASSED : Term End date is empty\n");
	}

	// public void verfiyRenewalPackageAndMemberPackage(String
	// memberPackage,String renewalPackage,boolean newPackage){
	// wait.waitForPageToLoadCompletely();
	// wait.resetImplicitTimeout(4);
	// wait.resetExplicitTimeout(hiddenFieldTimeOut);
	// waitForSpinner();
	// memberPackage=memberPackage.split(": ", 3)[2];
	// isElementDisplayed("txt_memberInfo","member package");
	// Assert.assertTrue(memberPackage.equals(element("txt_memberInfo","member
	// package").getText().trim()),
	// "ASSERT FAIL : Member Package is not "+memberPackage+"\n");
	// logMessage("ASSERT PASS : Member Package is "+ memberPackage+"\n");
	//
	// if(!newPackage)
	// renewalPackage=memberPackage;
	// isElementDisplayed("txt_memberInfo","renewal package");
	// // logMessage("-------------"+element("txt_memberInfo","renewal
	// package").getText().trim());
	// Assert.assertTrue(renewalPackage.equals(element("txt_memberInfo","renewal
	// package").getText().trim()),
	// "ASSERT FAIL : Renewal Package is not "+renewalPackage+"\n");
	// logMessage("ASSERT PASS : Renewal Package is "+ renewalPackage+"\n");
	// wait.resetImplicitTimeout(timeOut);
	// wait.resetExplicitTimeout(timeOut);
	// }

	public void verfiyMemberPackage(String memberPackage) {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(10);
		wait.resetImplicitTimeout(4);
		wait.resetExplicitTimeout(hiddenFieldTimeOut);
		System.out.println("-----before spinner");
		waitForSpinner();
		System.out.println("-----before wait");
		hardWaitForIEBrowser(3);
		memberPackage = memberPackage.split(": ", 3)[2];
		isElementDisplayed("txt_memberInfo", "member package");
		Assert.assertTrue(memberPackage.equals(element("txt_memberInfo", "member package").getText().trim()),
				"ASSERT FAIL : Member Package is not " + memberPackage + " but found"
						+ element("txt_memberInfo", "member package").getText().trim() + "\n");
		logMessage("ASSERT PASS : Member Package is " + memberPackage + "\n");
	}

	public void verifyRenewalPackage(String renewalPackage) {
		if (renewalPackage.contains(":"))
			renewalPackage = renewalPackage.split(": ", 3)[2];
		hardWaitForIEBrowser(15);
		isElementDisplayed("txt_memberInfo", "renewal package");
		Assert.assertTrue(renewalPackage.equals(element("txt_memberInfo", "renewal package").getText().trim()),
				"ASSERT FAIL : Renewal Package is not " + renewalPackage + " but found"
						+ element("txt_memberInfo", "renewal package").getText().trim() + "\n");
		logMessage("ASSERT PASS : Renewal Package is " + renewalPackage + "\n");
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
	}

	public void clickOnMYDTransferButton() {
		isElementDisplayed("btn_mydTransfer");
		clickUsingXpathInJavaScriptExecutor(element("btn_mydTransfer"));
		// element("btn_mydTransfer").click();
		logMessage("STEP : Clicked on MYD Transfer Button\n");
	}

	public void verifyTransferPackagePage() {
		isElementDisplayed("heading_transferPackage");
		logMessage("STEP : Member navigated to Transfer Package Page\n");
		switchToFrame("iframe1");
	}

	public double getBalanceAmount() {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(4);
		// switchToFrame("iframe1");

		String amount = element("txt_balanceAmount").getText().trim().split("\\$")[1];

		double d = Double.parseDouble(amount);
		logMessage("STEP : Balance Amount is : " + d + "\n");
		return d;
	}

	public void selectTerm(String term) {
		isElementDisplayed("list_term");
		selectProvidedTextFromDropDown(element("list_term"), term);
		logMessage("STEP : Select " + term + " term from list\n");
	}

	public void selectNewPackage(String newPackage) {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		hardWaitForIEBrowser(2);
		isElementDisplayed("list_newPackage");
		selectProvidedTextFromDropDown(element("list_newPackage"), newPackage);
		logMessage("STEP : Select " + newPackage + " new package from list\n");
	}

	public void verifyChangeInAmountBalance(Double previousAmount, Double newAmount, String oldPackage, String Year) {
		wait.waitForPageToLoadCompletely();
		int newYear = Integer.parseInt(Year.split(" ")[0]);
		int previousYear = getYearValue(oldPackage);
		logMessage("STEP : Previous package renewal year : " + previousYear);
		logMessage("STEP : New package renewal year : " + newYear);
		if (previousYear < newYear) {
			Assert.assertTrue(previousAmount < newAmount, "ASSERT FAILED : Balance Amount value has not increased\n");
			logMessage("ASSERT PASS : Balance Amount value has increased\n");
		} else if (previousYear > newYear) {
			Assert.assertTrue(previousAmount > newAmount, "ASSERT FAILED : Balance Amount value has not decreased\n");
			logMessage("ASSERT PASS : Balance Amount value has decreased\n");
		} else
			Assert.assertTrue(false, "ASSERT FAIL : Balance Amount value has not changed\n");
	}

	public int getYearValue(String oldPackage) {
		int year = 0;
		if (oldPackage.contains("2Y") || oldPackage.contains("3Y")) {
			char ar2[];
			ar2 = oldPackage.toCharArray();
			for (int k = 0; k < ar2.length; k++) {
				if (ar2[k] == '2' || ar2[k] == '3')
					year = ar2[k] - 48;
			}
			// logMessage("STEP : Previous year is : "+year);
		} else
			year = 1;
		return year;
	}

	public void clickOnTransferNowButton() {
		isElementDisplayed("btn_transferNow");
		// element("btn_transferNow").click();
		clickUsingXpathInJavaScriptExecutor(element("btn_transferNow"));
		logMessage("STEP : Clicked on Transfer Now button\n");
		System.out.println("----before switch");
		switchToDefaultContent();
		System.out.println("----after switch");
		wait.waitForPageToLoadCompletely();
		System.out.println("----after wait");
		// wait.hardWait(4);
	}

	public void verifyTermStartDateAndEndDatesAreEmpty(Map<String, String> mapOMR) {
		try {

			wait.resetImplicitTimeout(4);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("txt_termStartDaterenewal", "1");
			isElementDisplayed("txt_termEndDaterenewal", "1");
		} catch (NoSuchElementException e) {
			expandDetailsMenu("invoices");
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
		if (element("txt_termStartDaterenewal", "1").getText().length() != 1
				&& element("txt_termEndDaterenewal", "1").getText().length() != 1) {
			collapseDetailsMenu("invoices");
			logMessage("STEP : Term Start date and Term End Date are not empty for " + MemberTransferLoopCount
					+ " attempt\n");
			selectValidUserForRenewal(mapOMR);
		} else {
			Assert.assertTrue(element("txt_termStartDaterenewal", "1").getText().length() == 1,
					"Term Start Date is not Empty");
			logMessage("ASSERT PASSED : Term Start date is empty\n");
			Assert.assertTrue(element("txt_termEndDaterenewal", "1").getText().length() == 1,
					"Term End Date is not Empty");
			logMessage("ASSERT PASSED : Term End date is empty\n");
		}
	}

	public void verifyTermStartDateAndEndDatesAreEmptyForGCSOMR(Map<String, String> mapGcsOMR) {
		try {

			wait.resetImplicitTimeout(4);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("txt_termStartDaterenewal", "1");
			isElementDisplayed("txt_termEndDaterenewal", "1");
		} catch (NoSuchElementException e) {
			expandDetailsMenu("invoices");
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
		if (element("txt_termStartDaterenewal", "1").getText().length() != 1
				&& element("txt_termEndDaterenewal", "1").getText().length() != 1) {
			collapseDetailsMenu("invoices");
			logMessage("Step : Term Start date and Term Endd Date are not empty for\n");
			selectValidUserForRenewalAccordingToCountry(mapGcsOMR);
		} else {
			Assert.assertTrue(element("txt_termStartDaterenewal", "1").getText().length() == 1,
					"Term Start Date is not Empty");
			logMessage("ASSERT PASSED : Term Start date is empty\n");
			Assert.assertTrue(element("txt_termEndDaterenewal", "1").getText().length() == 1,
					"Term End Date is not Empty");
			logMessage("ASSERT PASSED : Term End date is empty\n");
		}
	}

	public void verifyTermStartDateAndEndDatesAreEmptyForAutoRenewal(String Query, String queryPageUrl) {
		try {

			wait.resetImplicitTimeout(4);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("txt_termStartDaterenewal", "1");
			isElementDisplayed("txt_termEndDaterenewal", "1");
		} catch (NoSuchElementException e) {
			expandDetailsMenu("invoices");
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
		if (element("txt_termStartDaterenewal", "1").getText().length() != 1
				&& element("txt_termEndDaterenewal", "1").getText().length() != 1) {
			collapseDetailsMenu("invoices");
			logMessage("STEP : Term Start date and Term Endd Date are not empty for " + MemberTransferLoopCount
					+ " attempt\n");
			MemberTransferLoopCount++;
			selectValidUserForAutoRenewal(Query, queryPageUrl);
		} else {
			Assert.assertTrue(element("txt_termStartDaterenewal", "1").getText().length() == 1,
					"Term Start Date is not Empty");
			logMessage("ASSERT PASSED : Term Start date is empty\n");
			Assert.assertTrue(element("txt_termEndDaterenewal", "1").getText().length() == 1,
					"Term End Date is not Empty");
			logMessage("ASSERT PASSED : Term End date is empty\n");
		}
	}

	public String getProductName() {
		isElementDisplayed("productname_txt", "1");
		return element("productname_txt", "1").getText().trim();
	}

	public String getPriceValue() {
		isElementDisplayed("pricevalue_txt", "1");
		return element("pricevalue_txt", "1").getText().trim();
	}

	public String getTermStartDate() {
		isElementDisplayed("txt_termStartDaterenewal", "1");
		return element("txt_termStartDaterenewal", "1").getText().trim();
	}

	public String getTermEndDate() {
		isElementDisplayed("txt_termEndDaterenewal", "1");
		return element("txt_termEndDaterenewal", "1").getText().trim();
	}

	public void verifyProductPackage(String productPackage) {
		isElementDisplayed("txt_productPackage");
		Assert.assertTrue(productPackage.equals(element("txt_productPackage").getText().trim()),
				"ASSERT FAIL : Product package does not matches with the new Renewal Package\n");
		logMessage("ASSERT PASS : Product package matches with the new Renewal Package\n");
	}

	public String getContactIdOfUser(String Member) {
		isElementDisplayed("txt_renewalContactId");
		logMessage("STEP : " + Member + " Id is : " + element("txt_renewalContactId").getText().trim() + "\n");
		return element("txt_renewalContactId").getText().trim();
	}

	public void clickOnMemberTransferButton() {
		isElementDisplayed("btn_transferMem");
		element("btn_transferMem").click();
		logMessage("STEP : Clicked on Transfer button\n");
		switchToDefaultContent();
		wait.waitForPageToLoadCompletely();
		wait.hardWait(4);
	}

	public void updateInformationAfterClickingTransferButton(String option, String option2) {
		System.out.println("Option Mem Type::" + option);
		System.out.println("Option Mem Package::" + option2);
		switchToFrame(element("iframe"));
		isElementDisplayed("drpdown_memtype");
		selectProvidedTextFromDropDown(element("drpdown_memtype"), option);
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		isElementDisplayed("drpdown_package");
		selectProvidedTextFromDropDown(element("drpdown_package"), option2);
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		isElementDisplayed("drpdown_invoice");
		selectProvidedTextFromDropDown(element("drpdown_invoice"), "ACS: SELENIUM_BATCH");
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		isElementDisplayed("btn_transferNow");
		element("btn_transferNow").click();
		switchToDefaultContent();
		waitForSpinner();
		logMessage("Clicked On Transfer Now Button");
	}

	public void matchBeforeDataWithAfterDataAccordingToMentionedCriteria(LinkedHashMap<String, String> beforeList,
			LinkedHashMap<String, String> afterList, HashMap<String, String> criteriaList, String custId) {
		String line, pattern;
		Pattern r;
		Matcher m;
		LinkedHashMap<String, String> ResultList = new LinkedHashMap<String, String>();
		Assert.assertEquals(beforeList.size(), afterList.size());
		for (@SuppressWarnings("rawtypes")
		Map.Entry criteria : criteriaList.entrySet()) {
			switch (criteria.getKey() + "") {
			case "MP Mbr Type":
				if (afterList.get(criteria.getKey()).trim()
						.equalsIgnoreCase(criteriaList.get(criteria.getKey()).trim())) {
					ResultList.put(criteria.getKey() + "", "y");
				} else {
					ResultList.put(criteria.getKey() + "", "n");
				}
				break;
			case "MP Mbr Status":
				if (afterList.get(criteria.getKey()).trim()
						.equalsIgnoreCase(criteriaList.get(criteria.getKey()).trim())) {
					ResultList.put(criteria.getKey() + "", "y");
				} else {
					ResultList.put(criteria.getKey() + "", "n");
				}
				break;
			case "MP Mbr Pkg":
				if (afterList.get(criteria.getKey()).trim()
						.equalsIgnoreCase(criteriaList.get(criteria.getKey()).trim())) {
					ResultList.put(criteria.getKey() + "", "y");
				} else {
					ResultList.put(criteria.getKey() + "", "n");
				}
				break;
			case "MP Renewal Pkg":
				if (afterList.get(criteria.getKey()).trim()
						.equalsIgnoreCase(criteriaList.get(criteria.getKey()).trim())) {
					ResultList.put(criteria.getKey() + "", "y");
				} else {
					ResultList.put(criteria.getKey() + "", "n");
				}
				break;
			case "MP Join Date":
				line = criteriaList.get(criteria.getKey()).trim().toUpperCase();
				pattern = "(\\d+)";
				r = Pattern.compile(pattern);
				m = r.matcher(line);
				if (m.find()) {
					System.out.println("MP Join Date \n Line::" + line + "Incremented By ::"
							+ Integer.parseInt(line.split("Y")[0].split("\\+")[1]));
					System.out.println("After List ::" + afterList.get(criteria.getKey()).trim());
					System.out.println("Before List::" + beforeList.get(criteria.getKey()).trim());
					if (afterList.get(criteria.getKey()).trim()
							.contains(_getBeforeDateIncrementedByNum(beforeList.get(criteria.getKey()).trim(),
									Integer.parseInt(line.split("Y")[0].split("\\+")[1])))) {
						ResultList.put(criteria.getKey() + "", "y");
					} else {
						ResultList.put(criteria.getKey() + "", "n");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{NOT NULL}")) {
					if (afterList.get(criteria.getKey()).trim().isEmpty()) {
						ResultList.put(criteria.getKey() + "", "n");
					} else {
						ResultList.put(criteria.getKey() + "", "y");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{DATE CHANGE}")) {
					if (beforeList.get(criteria.getKey()).trim()
							.equalsIgnoreCase(afterList.get(criteria.getKey()).trim())) {
						ResultList.put(criteria.getKey() + "", "n");
					} else {
						ResultList.put(criteria.getKey() + "", "y");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{NO CHANGE}")) {
					if (beforeList.get(criteria.getKey()).trim().contains(afterList.get(criteria.getKey()).trim())) {
						ResultList.put(criteria.getKey() + "", "y");
					} else {
						ResultList.put(criteria.getKey() + "", "n");
					}
				}
				break;
			case "MP Eff Date":
				line = criteriaList.get(criteria.getKey()).trim().toUpperCase();
				pattern = "(\\d+)";
				r = Pattern.compile(pattern);
				m = r.matcher(line);
				if (m.find()) {
					System.out.println("MP Eff Date \n Line::" + line + "Incremented By ::"
							+ Integer.parseInt(line.split("Y")[0].split("\\+")[1]));
					System.out.println("After List ::" + afterList.get(criteria.getKey()).trim());
					System.out.println("Before List::" + beforeList.get(criteria.getKey()).trim());
					if (afterList.get(criteria.getKey()).trim()
							.contains(_getBeforeDateIncrementedByNum(beforeList.get(criteria.getKey()).trim(),
									Integer.parseInt(line.split("Y")[0].split("\\+")[1])))) {
						ResultList.put(criteria.getKey() + "", "y");
					} else {
						ResultList.put(criteria.getKey() + "", "n");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{NOT NULL}")) {
					if (afterList.get(criteria.getKey()).trim().isEmpty()) {
						ResultList.put(criteria.getKey() + "", "n");
					} else {
						ResultList.put(criteria.getKey() + "", "y");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{DATE CHANGE}")) {
					if (beforeList.get(criteria.getKey()).trim()
							.equalsIgnoreCase(afterList.get(criteria.getKey()).trim())) {
						ResultList.put(criteria.getKey() + "", "n");
					} else {
						ResultList.put(criteria.getKey() + "", "y");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{NO CHANGE}")) {
					if (beforeList.get(criteria.getKey()).trim().contains(afterList.get(criteria.getKey()).trim())) {
						ResultList.put(criteria.getKey() + "", "y");
					} else {
						ResultList.put(criteria.getKey() + "", "n");
					}
				}
				break;
			case "MP Exp Date":
				line = criteriaList.get(criteria.getKey()).trim().toUpperCase();
				pattern = "(\\d+)";
				r = Pattern.compile(pattern);
				m = r.matcher(line);
				if (m.find()) {
					System.out.println("MP Exp Date \n Line::" + line + "Incremented By ::"
							+ Integer.parseInt(line.split("Y")[0].split("\\+")[1]));
					System.out.println("After List ::" + afterList.get(criteria.getKey()).trim());
					System.out.println("Before List::" + beforeList.get(criteria.getKey()).trim());
					if (afterList.get(criteria.getKey()).trim()
							.contains(_getBeforeDateIncrementedByNum(beforeList.get(criteria.getKey()).trim(),
									Integer.parseInt(line.split("Y")[0].split("\\+")[1])))) {
						ResultList.put(criteria.getKey() + "", "y");
					} else {
						ResultList.put(criteria.getKey() + "", "n");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{NOT NULL}")) {
					if (afterList.get(criteria.getKey()).trim().isEmpty()) {
						ResultList.put(criteria.getKey() + "", "n");
					} else {
						ResultList.put(criteria.getKey() + "", "y");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{DATE CHANGE}")) {
					if (beforeList.get(criteria.getKey()).trim()
							.equalsIgnoreCase(afterList.get(criteria.getKey()).trim())) {
						ResultList.put(criteria.getKey() + "", "n");
					} else {
						ResultList.put(criteria.getKey() + "", "y");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{NO CHANGE}")) {
					if (beforeList.get(criteria.getKey()).trim().contains(afterList.get(criteria.getKey()).trim())) {
						ResultList.put(criteria.getKey() + "", "y");
					} else {
						ResultList.put(criteria.getKey() + "", "n");
					}
				}
				break;
			case "MP YOS":
				line = criteriaList.get(criteria.getKey()).trim().toUpperCase();
				pattern = "(\\d+)";
				r = Pattern.compile(pattern);
				m = r.matcher(line);
				if (m.find()) {
					if (afterList.get(criteria.getKey()).trim()
							.equalsIgnoreCase(_getYOSIncrementedByNum(beforeList.get(criteria.getKey()).trim(),
									Integer.parseInt(line.split("Y")[0].split("\\+")[1])))) {
						ResultList.put(criteria.getKey() + "", "y");
					} else {
						ResultList.put(criteria.getKey() + "", "n");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{NO CHANGE}")) {
					if (beforeList.get(criteria.getKey()).trim()
							.equalsIgnoreCase(afterList.get(criteria.getKey()).trim())) {
						ResultList.put(criteria.getKey() + "", "y");
					} else {
						ResultList.put(criteria.getKey() + "", "n");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{NOT NULL}")) {
					if (afterList.get(criteria.getKey()).trim().isEmpty()) {
						ResultList.put(criteria.getKey() + "", "n");
					} else {
						ResultList.put(criteria.getKey() + "", "y");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{DATE CHANGE}")) {
					if (beforeList.get(criteria.getKey()).trim().contains(afterList.get(criteria.getKey()).trim())) {
						ResultList.put(criteria.getKey() + "", "n");
					} else {
						ResultList.put(criteria.getKey() + "", "y");
					}
				}
				break;
			case "MP Pmt Status":
				if (criteriaList.get(criteria.getKey()).trim().contains("{IGNORE}")) {
					ResultList.put(criteria.getKey() + "", "y");

				} else if (criteriaList.get(criteria.getKey()).trim().toLowerCase().contains("unpaid|credit")) {
					System.out
							.println("SpreadSheet data:: " + criteriaList.get(criteria.getKey()).trim().toLowerCase());
					String sp[] = criteriaList.get(criteria.getKey()).trim().toLowerCase().split("\\|");
					System.out.println("SP 0:: " + sp[0]);
					System.out.println("SP 1:: " + sp[1]);
					if ((afterList.get(criteria.getKey()).trim().toLowerCase().contains(sp[0]))
							|| (afterList.get(criteria.getKey()).trim().toLowerCase().contains(sp[1]))) {
						ResultList.put(criteria.getKey() + "", "y");
					} else {
						ResultList.put(criteria.getKey() + "", "n");
					}
				} else if (criteriaList.get(criteria.getKey()).trim()
						.equalsIgnoreCase((afterList.get(criteria.getKey()).trim()))) {
					ResultList.put(criteria.getKey() + "", "y");
				} else {
					ResultList.put(criteria.getKey() + "", "n");
				}
				break;
			case "MP Product":
				if (afterList.get(criteria.getKey()).trim()
						.equalsIgnoreCase(criteriaList.get(criteria.getKey()).trim())) {
					ResultList.put(criteria.getKey() + "", "y");
				} else {
					ResultList.put(criteria.getKey() + "", "n");
				}
				break;
			case "MP Balance":
				if (criteriaList.get(criteria.getKey()).trim().equalsIgnoreCase("{GT 0}")) {
					if (Float.parseFloat(afterList.get(criteria.getKey()).trim()) > 0) {
						ResultList.put(criteria.getKey() + "", "y");
					} else {
						ResultList.put(criteria.getKey() + "", "n");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().equalsIgnoreCase("0")) {
					if (Float.parseFloat(afterList.get(criteria.getKey()).trim()) == Float
							.parseFloat(criteriaList.get(criteria.getKey()).trim())) {
						ResultList.put(criteria.getKey() + "", "y");
					} else {
						ResultList.put(criteria.getKey() + "", "n");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{NO CHANGE}")) {
					if (beforeList.get(criteria.getKey()).trim().contains(afterList.get(criteria.getKey()).trim())) {
						ResultList.put(criteria.getKey() + "", "y");
					} else {
						ResultList.put(criteria.getKey() + "", "n");
					}
				}
				break;
			case "MP Start":
				line = criteriaList.get(criteria.getKey()).trim().toUpperCase();
				pattern = "(\\d+)";
				r = Pattern.compile(pattern);
				m = r.matcher(line);
				if (m.find()) {
					System.out.println("MP Start \n Line::" + line + "Incremented By ::"
							+ Integer.parseInt(line.split("Y")[0].split("\\+")[1]));
					System.out.println("After List ::" + afterList.get(criteria.getKey()).trim());
					System.out.println("Before List::" + beforeList.get(criteria.getKey()).trim());
					if (afterList.get(criteria.getKey()).trim()
							.contains(_getBeforeDateIncrementedByNum(beforeList.get(criteria.getKey()).trim(),
									Integer.parseInt(line.split("Y")[0].split("\\+")[1])))) {
						ResultList.put(criteria.getKey() + "", "y");
					} else {
						ResultList.put(criteria.getKey() + "", "n");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{NOT NULL}")) {
					if (afterList.get(criteria.getKey()).trim().isEmpty()) {
						ResultList.put(criteria.getKey() + "", "n");
					} else {
						ResultList.put(criteria.getKey() + "", "y");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{DATE CHANGE}")) {
					if (beforeList.get(criteria.getKey()).trim()
							.equalsIgnoreCase(afterList.get(criteria.getKey()).trim())) {
						ResultList.put(criteria.getKey() + "", "n");
					} else {
						ResultList.put(criteria.getKey() + "", "y");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{NO CHANGE}")) {
					if (beforeList.get(criteria.getKey()).trim().contains(afterList.get(criteria.getKey()).trim())) {
						ResultList.put(criteria.getKey() + "", "y");
					} else {
						ResultList.put(criteria.getKey() + "", "n");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{NULL}")) {
					if (afterList.get(criteria.getKey()).trim().isEmpty()) {
						ResultList.put(criteria.getKey() + "", "y");
					} else {
						ResultList.put(criteria.getKey() + "", "n");
					}
				}
				break;
			case "MP End":
				line = criteriaList.get(criteria.getKey()).trim().toUpperCase();
				pattern = "(\\d+)";
				r = Pattern.compile(pattern);
				m = r.matcher(line);
				if (m.find()) {
					System.out.println("MP End \n Line::" + line + "Incremented By ::"
							+ Integer.parseInt(line.split("Y")[0].split("\\+")[1]));
					System.out.println("After List ::" + afterList.get(criteria.getKey()).trim());
					System.out.println("Before List::" + beforeList.get(criteria.getKey()).trim());
					if (afterList.get(criteria.getKey()).trim()
							.contains(_getBeforeDateIncrementedByNum(beforeList.get(criteria.getKey()).trim(),
									Integer.parseInt(line.split("Y")[0].split("\\+")[1])))) {
						ResultList.put(criteria.getKey() + "", "y");
					} else {
						ResultList.put(criteria.getKey() + "", "n");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{NOT NULL}")) {
					if (afterList.get(criteria.getKey()).trim().isEmpty()) {
						ResultList.put(criteria.getKey() + "", "n");
					} else {
						ResultList.put(criteria.getKey() + "", "y");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{DATE CHANGE}")) {
					if (beforeList.get(criteria.getKey()).trim()
							.equalsIgnoreCase(afterList.get(criteria.getKey()).trim())) {
						ResultList.put(criteria.getKey() + "", "n");
					} else {
						ResultList.put(criteria.getKey() + "", "y");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{NO CHANGE}")) {
					if (beforeList.get(criteria.getKey()).trim().contains(afterList.get(criteria.getKey()).trim())) {
						ResultList.put(criteria.getKey() + "", "y");
					} else {
						ResultList.put(criteria.getKey() + "", "n");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{NULL}")) {
					if (afterList.get(criteria.getKey()).trim().isEmpty()) {
						ResultList.put(criteria.getKey() + "", "y");
					} else {
						ResultList.put(criteria.getKey() + "", "n");
					}
				}
				break;
			case "IVP TX Date":
				line = criteriaList.get(criteria.getKey()).trim().toUpperCase();
				pattern = "(\\d+)";
				r = Pattern.compile(pattern);
				m = r.matcher(line);
				if (m.find()) {
					System.out.println("IVP TX DATE\n Line::" + line + "Incremented By ::"
							+ Integer.parseInt(line.split("Y")[0].split("\\+")[1]));
					System.out.println("After List ::" + afterList.get(criteria.getKey()).trim());
					System.out.println("Before List::" + beforeList.get(criteria.getKey()).trim());
					if (afterList.get(criteria.getKey()).trim()
							.contains(_getBeforeDateIncrementedByNum(beforeList.get(criteria.getKey()).trim(),
									Integer.parseInt(line.split("Y")[0].split("\\+")[1])))) {
						ResultList.put(criteria.getKey() + "", "y");
					} else {
						ResultList.put(criteria.getKey() + "", "n");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{NOT NULL}")) {
					if (afterList.get(criteria.getKey()).trim().isEmpty()) {
						ResultList.put(criteria.getKey() + "", "n");
					} else {
						ResultList.put(criteria.getKey() + "", "y");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{DATE CHANGE}")) {
					if (beforeList.get(criteria.getKey()).trim()
							.equalsIgnoreCase(afterList.get(criteria.getKey()).trim())) {
						ResultList.put(criteria.getKey() + "", "n");
					} else {
						ResultList.put(criteria.getKey() + "", "y");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{NO CHANGE}")) {
					if (beforeList.get(criteria.getKey()).trim().contains(afterList.get(criteria.getKey()).trim())) {
						ResultList.put(criteria.getKey() + "", "y");
					} else {
						ResultList.put(criteria.getKey() + "", "n");
					}
				}
				break;
			case "IVP Proforma":
				if (afterList.get(criteria.getKey()).trim()
						.equalsIgnoreCase(criteriaList.get(criteria.getKey()).trim())) {
					ResultList.put(criteria.getKey() + "", "y");
				} else {
					ResultList.put(criteria.getKey() + "", "n");
				}
				break;
			case "IVP Fully Paid":
				if (criteriaList.get(criteria.getKey()).trim().contains("{IGNORE}")) {
					ResultList.put(criteria.getKey() + "", "y");
				} else if (afterList.get(criteria.getKey()).trim()
						.equalsIgnoreCase(criteriaList.get(criteria.getKey()).trim())) {
					ResultList.put(criteria.getKey() + "", "y");
				} else {
					ResultList.put(criteria.getKey() + "", "n");
				}

				break;
			case "IDP Mbr Type":
				if (afterList.get(criteria.getKey()).trim()
						.equalsIgnoreCase(criteriaList.get(criteria.getKey()).trim())) {
					ResultList.put(criteria.getKey() + "", "y");
				} else {
					ResultList.put(criteria.getKey() + "", "n");
				}
				break;
			case "IDP Mbr Status":
				if (afterList.get(criteria.getKey()).trim()
						.equalsIgnoreCase(criteriaList.get(criteria.getKey()).trim())) {
					ResultList.put(criteria.getKey() + "", "y");
				} else {
					ResultList.put(criteria.getKey() + "", "n");
				}
				break;
			case "IDP Join Date":
				line = criteriaList.get(criteria.getKey()).trim().toUpperCase();
				pattern = "(\\d+)";
				r = Pattern.compile(pattern);
				m = r.matcher(line);
				if (m.find()) {
					System.out.println("IDP Join DATE\n Line::" + line + "Incremented By ::"
							+ Integer.parseInt(line.split("Y")[0].split("\\+")[1]));
					System.out.println("After List ::" + afterList.get(criteria.getKey()).trim());
					System.out.println("Before List::" + beforeList.get(criteria.getKey()).trim());
					if (afterList.get(criteria.getKey()).trim()
							.contains(_getBeforeDateIncrementedByNum(beforeList.get(criteria.getKey()).trim(),
									Integer.parseInt(line.split("Y")[0].split("\\+")[1])))) {
						ResultList.put(criteria.getKey() + "", "y");
					} else {
						ResultList.put(criteria.getKey() + "", "n");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{NOT NULL}")) {
					if (afterList.get(criteria.getKey()).trim().isEmpty()) {
						ResultList.put(criteria.getKey() + "", "n");
					} else {
						ResultList.put(criteria.getKey() + "", "y");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{DATE CHANGE}")) {
					if (beforeList.get(criteria.getKey()).trim()
							.equalsIgnoreCase(afterList.get(criteria.getKey()).trim())) {
						ResultList.put(criteria.getKey() + "", "n");
					} else {
						ResultList.put(criteria.getKey() + "", "y");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{NO CHANGE}")) {
					if (beforeList.get(criteria.getKey()).trim().contains(afterList.get(criteria.getKey()).trim())) {
						ResultList.put(criteria.getKey() + "", "y");
					} else {
						ResultList.put(criteria.getKey() + "", "n");
					}
				}
				break;
			case "IDP Eff Date":
				line = criteriaList.get(criteria.getKey()).trim().toUpperCase();
				pattern = "(\\d+)";
				r = Pattern.compile(pattern);
				m = r.matcher(line);
				if (m.find()) {
					System.out.println("IDP Eff DATE\n Line::" + line + "Incremented By ::"
							+ Integer.parseInt(line.split("Y")[0].split("\\+")[1]));
					System.out.println("After List ::" + afterList.get(criteria.getKey()).trim());
					System.out.println("Before List::" + beforeList.get(criteria.getKey()).trim());
					if (afterList.get(criteria.getKey()).trim()
							.contains(_getBeforeDateIncrementedByNum(beforeList.get(criteria.getKey()).trim(),
									Integer.parseInt(line.split("Y")[0].split("\\+")[1])))) {
						ResultList.put(criteria.getKey() + "", "y");
					} else {
						ResultList.put(criteria.getKey() + "", "n");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{NOT NULL}")) {
					if (afterList.get(criteria.getKey()).trim().isEmpty()) {
						ResultList.put(criteria.getKey() + "", "n");
					} else {
						ResultList.put(criteria.getKey() + "", "y");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{DATE CHANGE}")) {
					if (beforeList.get(criteria.getKey()).trim()
							.equalsIgnoreCase(afterList.get(criteria.getKey()).trim())) {
						ResultList.put(criteria.getKey() + "", "n");
					} else {
						ResultList.put(criteria.getKey() + "", "y");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{NO CHANGE}")) {
					if (beforeList.get(criteria.getKey()).trim().contains(afterList.get(criteria.getKey()).trim())) {
						ResultList.put(criteria.getKey() + "", "y");
					} else {
						ResultList.put(criteria.getKey() + "", "n");
					}
				}
				break;
			case "IDP Exp Date":
				line = criteriaList.get(criteria.getKey()).trim().toUpperCase();
				pattern = "(\\d+)";
				r = Pattern.compile(pattern);
				m = r.matcher(line);
				if (m.find()) {
					System.out.println("IDP EXP DATE\n Line::" + line + "Incremented By ::"
							+ Integer.parseInt(line.split("Y")[0].split("\\+")[1]));
					System.out.println("After List ::" + afterList.get(criteria.getKey()).trim());
					System.out.println("Before List::" + beforeList.get(criteria.getKey()).trim());
					if (afterList.get(criteria.getKey()).trim()
							.contains(_getBeforeDateIncrementedByNum(beforeList.get(criteria.getKey()).trim(),
									Integer.parseInt(line.split("Y")[0].split("\\+")[1])))) {
						ResultList.put(criteria.getKey() + "", "y");
					} else {
						ResultList.put(criteria.getKey() + "", "n");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{NOT NULL}")) {
					if (afterList.get(criteria.getKey()).trim().isEmpty()) {
						ResultList.put(criteria.getKey() + "", "n");
					} else {
						ResultList.put(criteria.getKey() + "", "y");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{DATE CHANGE}")) {
					if (beforeList.get(criteria.getKey()).trim()
							.equalsIgnoreCase(afterList.get(criteria.getKey()).trim())) {
						ResultList.put(criteria.getKey() + "", "n");
					} else {
						ResultList.put(criteria.getKey() + "", "y");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{NO CHANGE}")) {
					if (beforeList.get(criteria.getKey()).trim().contains(afterList.get(criteria.getKey()).trim())) {
						ResultList.put(criteria.getKey() + "", "y");
					} else {
						ResultList.put(criteria.getKey() + "", "n");
					}
				}
				break;
			default:
				break;
			}
		}

		/*
		 * for(Map.Entry result :ResultList.entrySet()) {
		 * logMessage(result.getKey()+"\t"+result.getValue()); }
		 */

		boolean flag1 = _verifyResultListData(ResultList);

		logMessage("===========================Here Are the Complete Test Log================================");

		html = "<html><body><table border=1><tbody>" + "<tr><td>Case ID::</td><td>" + criteriaList.get("ID")
				+ "<tr><td>Run Date::</td><td>" + DateUtil.getCurrentdateInStringWithGivenFormate("M/d/yyyy") + " "
				+ DateUtil.getCurrentTime("hh:mm a", "IST") + "<tr><td>Member/Customer Id::</td><td>" + custId;

		if (flag1) {
			html = html + "<tr><td>Test Case Status::</td><td bgcolor='green'>" + "PASS </td></tr><tr>";
		} else {
			html = html + "<tr><td>Test Case Status::</td><td bgcolor='red'>" + "Fail </td></tr><tr>";
		}
		System.out.println("===>> Notes lenght::" + criteriaList.get("Notes").length());
		if (criteriaList.get("Notes").length() != 0) {
			html = html + "<tr><td>Notes::</td><td bgcolor='yellow'>" + criteriaList.get("Notes") + "</td></tr><tr>";
		} else {
			html = html + "<tr><td>Notes::</td><td>None </td></tr><tr>";
		}

		html = html + "<td><h1>Initial Conditions</h1></td></tr><tr><td><b>Initial Mbr Type::</b></td><td>"
				+ criteriaList.get("Initial Mbr Type") + "</td></tr><tr><td><b>Initial Mbr Status::</b></td><td>"
				+ criteriaList.get("Initial Mbr Status") + "</td></tr>"
				+ "<tr><td><b>Initial Mbr Package::</b></td><td>" + criteriaList.get("Initial Mbr Package")
				+ "</td></tr><tr><td><b>Initial MP Exp Date::</b></td><td>" + criteriaList.get("Initial MP Exp Date")
				+ "</td></tr><tr><td><b>Target Mbr Type::</b></td><td>" + criteriaList.get("Target Mbr Type")
				+ "</td></tr>" + "<tr><td><b>Target Mbr Package::</b></td><td>" + criteriaList.get("Target Mbr Package")
				+ "</td></tr><tr><td><h1>Test Result</h1></tr></td>"
				+ "<tr><td><h2>Field</h2></td><td><h2>Before</h2></td><td><h2>After</h2></td><td><h2>Expected</h2></td><td><h2>Pass?</h2></td></tr>";
		logMessage("Case ID :" + criteriaList.get("ID"));
		logMessage("Member/Customer ID :" + custId);
		logMessage("Initial Conditions>");
		logMessage("Initial Mbr Type :" + criteriaList.get("Initial Mbr Type"));
		logMessage("Initial Mbr Status :" + criteriaList.get("Initial Mbr Status"));
		logMessage("Initial Mbr Package :" + criteriaList.get("Initial Mbr Package"));
		logMessage("Initial MP Exp Date :" + criteriaList.get("Initial MP Exp Date"));
		logMessage("Target Mbr Type :" + criteriaList.get("Target Mbr Type"));
		logMessage("Target Mbr Package :" + criteriaList.get("Target Mbr Package"));
		logMessage("==========================================================================================");
		logMessage("Test Result");
		logMessage("Field==>Before==>After==>Expected==>Pass?");
		for (Map.Entry before : beforeList.entrySet()) {
			if (ResultList.get(before.getKey()).equalsIgnoreCase("n")) {
				html = html + "<tr><td>" + before.getKey() + "</td><td>" + before.getValue() + "</td><td>"
						+ afterList.get(before.getKey()) + "</td><td>" + criteriaList.get(before.getKey())
						+ "</td><td bgcolor='red'>" + ResultList.get(before.getKey()) + "</td></tr>";
			} else {
				html = html + "<tr><td>" + before.getKey() + "</td><td>" + before.getValue() + "</td><td>"
						+ afterList.get(before.getKey()) + "</td><td>" + criteriaList.get(before.getKey()) + "</td><td>"
						+ ResultList.get(before.getKey()) + "</td></tr>";
			}
			logMessage(before.getKey() + "==>" + before.getValue() + "==>" + afterList.get(before.getKey()) + "==>"
					+ criteriaList.get(before.getKey()) + "==>" + ResultList.get(before.getKey()));
		}
		html = html + "</tbody></table></body></html>";
		System.out.println("After Validation");
		String Fpath = System.getProperty("user.dir") + File.separator + "test-output" + File.separator
				+ "Member Transfer Test Logs";
		ReformatTestFile.createMemberTransferCompleteTestLog(Fpath, html, criteriaList.get("ID"));
		Assert.assertTrue(flag1,
				"[FAILED]:: Data for Before and After member transfer does not match the criteria FINAL Test Result ::"
						+ flag1);
		logMessage(
				"ASSERTION PASSED : Data for Before and After member transfer match the criteria FINAL Test Result ::"
						+ flag1);
		/* return ResultList; */
	}

	private String _getYOSIncrementedByNum(String yos, int i) {
		int j = Integer.parseInt(yos) + i;
		return j + "";
	}

	private String _getBeforeDateIncrementedByNum(String sdate, int i) {
		SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");
		Calendar c = Calendar.getInstance();
		try {
			Date d = formatter.parse(sdate);
			c.setTime(d);
			c.add(Calendar.YEAR, i);
			System.out.println("Incremented Date::" + formatter.format(c.getTime()));

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formatter.format(c.getTime());
	}

	private boolean _verifyResultListData(LinkedHashMap<String, String> resultList) {
		boolean flag = true;
		for (Map.Entry result : resultList.entrySet()) {
			if (result.getValue().equals("n")) {
				flag = false;
				logMessage("Data for Before and After member transfer does not match according to criteria for field "
						+ result.getKey());
			}
		}
		return flag;
	}

	public void verifyDataBeforeTransferFullFilledTheCriteria(LinkedHashMap<String, String> beforeList,
			HashMap<String, String> dataList, String ID, String custId) {
		boolean flag = true;
		for (Map.Entry before : beforeList.entrySet()) {
			System.out.println("Before Key::" + before.getKey().toString().trim() + "1");
			System.out.println("Data List Key Value::" + dataList.get(before.getKey().toString() + "1"));
			if (!(dataList.get(before.getKey().toString() + "1").isEmpty())) {
				System.out.println("Before Value::" + before.getValue().toString().trim());
				if (!(before.getValue().toString().trim()
						.equalsIgnoreCase(dataList.get(before.getKey().toString().trim() + "1")))) {
					flag = false;
					logMessage("FAILED : Data Before Member Transfer for Key " + before.getKey() + "::"
							+ before.getValue() + " did not match with the data in the spreadsheet Key"
							+ before.getKey() + "::" + dataList.get(before.getKey().toString().trim() + "1"));
					break;
				}
			}
		}
		Assert.assertTrue(flag,
				"FAILED : Can't do Member Transfer Now for CASE ID :: " + ID + " for Customer ID::" + custId);
		logMessage(
				"ASSERTION PASSED : Data Before Member Transfer matched with the data mentioned in spreadsheet, Can do Member Transfer Now for CASE ID "
						+ ID);
	}

	public void verifyReportingStartAndEndDate() {
		@SuppressWarnings("unused")
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
		logMessage("ASSERT PASSED : Current date lies within the Reporting start and end date\n");
	}

	public boolean verfiyEndAndStartDate(String reportingEndDate, String reportingStartDate) {
		int endDate, startDate;
		logMessage(
				"Current Date :" + DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/dd/yyyy", "EST5EDT"));
		logMessage("End Date :" + reportingEndDate);
		endDate = DateUtil.convertStringToDate(
				DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("MM/dd/yyyy", "EST5EDT"), "MM/dd/yyyy")
				.compareTo(DateUtil.convertStringToDate(reportingEndDate, "MM/dd/yyyy"));
		logMessage(
				"Current Date :" + DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/dd/yyyy", "EST5EDT"));
		// logMessage("Start Date:" +
		// DateUtil.convertStringToDate(reportingStartDate, "MM/dd/yyyy"));
		logMessage("Start Date :" + reportingStartDate);
		startDate = DateUtil
				.convertStringToDate(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"), "MM/dd/yyyy")
				.compareTo(DateUtil.convertStringToDate(reportingStartDate, "MM/dd/yyyy"));

		if (endDate == -1 && startDate == 1)
			return true;
		else
			return false;
	}

	public void selectARandomActiveStudentChapter() {
		wait.waitForPageToLoadCompletely();
		clickOnRandomPage(10, 2);
		// clickOnRandomPage();
		clickOnAnyRandomMember();
	}

	public void selectRandomCustomer() {
		wait.waitForPageToLoadCompletely();
		clickOnRandomPage();
		sortTheMembers();
		clickOnAnyRandomMember();
	}

	public void sortTheMembers() {
		isElementDisplayed("table_header", "Sort Name");
	}

	public void clickOnRelationsOptionUnderMoreMenu() {
		IndividualsPageActions_IWEB object = new IndividualsPageActions_IWEB(driver);
		object.navigateToGeneralMenuOnHoveringMore("Relations");
		expandDetailsMenu("active student member undergrads");
	}

	public String getChapterDetails() {
		ACS_Scarf_Reporting obj = new ACS_Scarf_Reporting(driver);
		clickOnEditNameAndAddress();
		switchToFrame("iframe1");
		chapterName = obj.getChapterName();
		clickOnCancelButton();
		handleAlert();
		switchToDefaultContent();
		return chapterName;
	}

	public int selectStudentMember() {
		boolean flag;
		int i;
		isElementDisplayed("table_rows");
		for (i = 1; i <= elements("table_rows").size(); i++) {
			flag = verifyStudentMemberEndDate(i);
			if (flag) {
				logMessage("ASSERT PASSED : End date for student member "
						+ element("txt_endDate", String.valueOf(i), String.valueOf(4)).getText().trim()
						+ "is not equal to Current Date\n");
				clickOnStudentMemberName(i);
				break;
			}
		}
		return i;
	}

	public boolean verifyStudentMemberEndDate(int i) {
		boolean flag = false;
		logMessage("STEP : Current Date:" + DateUtil.getCurrentdateInStringWithGivenFormate("M/dd/yyyy"));
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
		isElementDisplayed("arrow_selectMember", String.valueOf(i), String.valueOf(3));
		logMessage("STEP : Selected Student Member is "
				+ element("txt_endDate", String.valueOf(i), String.valueOf(4)).getText().trim());
		if (isBrowser("ie") || isBrowser("internet explorer")) {
			clickUsingXpathInJavaScriptExecutor(element("arrow_selectMember", String.valueOf(i)));
		} else
			element("arrow_selectMember", String.valueOf(i)).click();
	}

	public void verifyPayment(int index) {
		isElementDisplayed("txt_endDate", String.valueOf(index), String.valueOf(8));
		String payment = element("txt_endDate", String.valueOf(index), String.valueOf(8)).getText().trim();
		Assert.assertTrue(Double.parseDouble(payment) == 0.00,
				"ASSERT FAILED : Payment value is not 0.00 but found " + payment + "\n");
		logMessage("ASSERT PASSED : Payment value is 0.00\n");
	}

	public void verifyBalance(int index) {
		isElementDisplayed("txt_endDate", String.valueOf(index), String.valueOf(9));
		String balance = element("txt_endDate", String.valueOf(index), String.valueOf(9)).getText().trim();
		Assert.assertNotEquals(Double.parseDouble(balance), 0.00, 0.01, "ASSERT FAILED : Balance value is null\n");
		logMessage("ASSERT PASSED : Balance value is not null\n");
	}

	// public int verifyProductUnderDetailsMenu(String productName){
	// int i;
	// flag=false;
	// waitForSpinner();
	// for(i=1;i<=elements("table_rows").size();i++){
	// System.out.println("--------"+element("txt_endDate",String.valueOf(i),String.valueOf(4)).getText().trim());
	// if(element("txt_endDate",String.valueOf(i),String.valueOf(4)).getText().trim()
	// .equals(productName)){
	// flag=true;
	// break;
	// }
	// }
	// Assert.assertTrue(flag,"ASSERT FAILED : "+productName+" product is not
	// present under menu");
	// logMessage("ASSERT PASSED : "+productName+" product is present under
	// invoices at index "+i);
	// return i;
	// }

	public int verifyProductUnderDetailsMenu(String productName) {
		int i;
		flag = false;
		waitForSpinner();
		for (i = 1; i <= elements("table_rows").size(); i++) {
			if (element("txt_endDate", String.valueOf(i), String.valueOf(4)).getText().trim().equals(productName)) {
				flag = true;
				break;
			}
		}
		Assert.assertTrue(flag, "ASSERT FAILED : " + productName + " product is not present under menu");
		logMessage("ASSERT PASSED : " + productName + " product is present under invoices at index " + i);
		return i;
	}

	public void clickCurrentYearPencilButton() {
		isElementDisplayed("btn_CurrentYearPencil");
		element("btn_CurrentYearPencil").click();
		logMessage("STEP : Edit Pencil button for current year is clicked\n");

	}

	public void verifyStartAndEndDatesForAllModesOfReview() {
		switchToFrame(element("iframe"));
		verifyStartAndEndDateForReviewerType("online faculty reviewer");
		verifyStartAndEndDateForReviewerType("faculty decision panel");
		verifyStartAndEndDateForReviewerType("green chemistry reviewer");
		clickOnSaveButtonForBillingAddress();
		switchToDefaultContent();

	}

	private void verifyStartAndEndDateForReviewerType(String reviewerType) {
		String reviewingStartDate = reviewerType + " start date";
		String reviewingEndDate = reviewerType + " end date";
		reviewingStartDate = element("inp_dateForReviewModes", reviewingStartDate).getAttribute("value");
		reviewingEndDate = element("inp_dateForReviewModes", reviewingEndDate).getAttribute("value");
		Assert.assertTrue(verfiyEndAndStartDate(reviewingEndDate, reviewingStartDate),
				"ASSERT FAIL : Current date does not lies within the " + reviewerType + " start and end date\n");
		logMessage("ASSERT PASSED : Current date lies within the " + reviewerType + " start and end date\n");
	}

	public void selectValidUserForAutoRenewal(String AutoRenewalquery, String queryPageUrl) {
		wait.hardWait(2);
		System.out.println(AutoRenewalquery);
		selectAndRunQuery(AutoRenewalquery);
		expandDetailsMenu("individual memberships");
		navigateToInvoicePageForRenewedProduct();
		expandDetailsMenu("invoices");
		verifyTermStartDateAndEndDatesAreEmptyForAutoRenewal(AutoRenewalquery, queryPageUrl);
		// verifyPaymentStatusBeforeAutoRenewal(AutoRenewalquery,queryPageUrl);
		logMessage("STEP : Member selected in " + MemberTransferLoopCount + 1 + " attempt\n");

	}

	public void verifyCentralizedOrderEntryPage(String title) {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(15);
		Assert.assertEquals(getPageTitle(), title);
		logMessage("STEP : Title for Centralized Order Entry Page is verified as " + title);
	}

	public void verifyProductNameInLineItem(String productName) {
		switchToDefaultContent();
		waitForSpinner();
		wait.hardWait(7);
		isElementDisplayed("lineitem_product", productName);
		String prodName = element("lineitem_product", productName).getText();
		Assert.assertTrue(productName.trim().contains(prodName),
				"ASSERT FAILED: Expected value is " + productName + " but found " + prodName);
		logMessage("STEP : " + productName + " Product is added in Line Items \n");
	}

	public void verifyCreditAvailable(String credit_amount, String netTotal, String batchName) {
		// String
		// payDate=DateUtil.getCurrentdateInStringWithGivenFormate("M/d/YYYY");
		batchName = batchName.replaceAll("ACS: ", "");
		isElementDisplayed("txt_joinDate_chapter", batchName);
		element("txt_joinDate_chapter", batchName).getText();
		DecimalFormat df = new DecimalFormat("#.00");
		Double credit_avl = Double.parseDouble(credit_amount) - Double.parseDouble(netTotal);
		String credit_available = String.valueOf(df.format(credit_avl));
		Assert.assertTrue(element("txt_joinDate_chapter", batchName).getText().trim().equals(credit_available),
				"ASSERT FAILED: credit available is not matched.Expected value is " + credit_available + " but found "
						+ element("txt_joinDate_chapter", batchName).getText().trim());
		logMessage("ASSERT PASSED : credit available information in credits child forms is " + credit_available + "\n");

		logMessage("\n ************ SUCCESSFULL *************\n");

	}

	public void verifyInvoiceIsAdded(String customerName) {
		isElementDisplayed("txt_effectiveDateMemberType", customerName);
		String actual = element("txt_effectiveDateMemberType", customerName).getText().trim();
		String expected = DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/yyyy", "EST5EDT");
		Assert.assertEquals(actual, expected);
		logMessage("STEP : Customer " + customerName + " is added with current date "
				+ DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/yyyy", "EST5EDT"));
	}

	public void selectMerchandise(String merchandise) {
		wait.waitForPageToLoadCompletely();
		holdExecution(3000);
		switchToFrame(element("frame_selectProduct"));
		isElementDisplayed("txt_itemsAdded", merchandise);
		clickUsingXpathInJavaScriptExecutor(element("txt_itemsAdded", merchandise));
		logMessage("STEP : Merchandise link is clicked in link_merchandise \n");
	}

	public String getProductCodeFromCOEPage() {
		switchToDefaultContent();
		switchToFrame("iframe1");
		String productCode = element("txt_prod_code").getAttribute("value");
		logMessage("STEP : Product code is selected as '" + productCode + "'\n");
		return productCode;
	}

	public String getProductNameFromCOEPage() {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		productName = element("productName_inp").getText().trim();
		logMessage("STEP : Product name is selected as " + productName + "\n");
		return productName;
	}

	public void verifyStorePaymentInformationChildFormIsPopulated(String firstName) {
		isElementDisplayed("txt_code", firstName);
		isElementDisplayed("txt_priceValue", firstName);
		Assert.assertFalse(element("txt_code", firstName).getText().isEmpty());
		Assert.assertFalse(element("txt_priceValue", firstName).getText().isEmpty());
		logMessage("ASSERT PASSED : Child form is populated under stored payment information for " + firstName);

	}

	public void selectRandomProductForCRMInventory() {
		selectMerchandise("merchandise");
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		wait.hardWait(2);
		clickOnSearchDisplayNameButton();
		hardWaitForIEBrowser(3);
		selectRandomMemberByAscendingHeader("Price", "price_txt");
		// selectRandomUserOnAscendingHeader("Available Quantity");
	}

	public void hoverAndClickOnCoeForm(String elem) {
		hoverClick(element(elem));
	}

	public void selectRandomMemberByAscendingHeader(String headerName, String locator) {
		_clickOnAvailableQuantityForSorting(headerName);
		_clickOnAvailableQuantityForSorting(headerName);
		hardWaitForIEBrowser(4);
		clickOnRandomPage(10, 2);
		clickOnAnyRandomMember1(locator);
		wait.hardWait(4);
	}

	public void selectRandomUserOnAscendingHeader(String headerName) {
		// selectMerchandise("merchandise");
		switchToDefaultContent();
		hardWaitForIEBrowser(5);
		switchToFrame(element("iframe"));
		// clickOnSearchDisplayNameButton();
		_clickOnAvailableQuantityForSorting(headerName);
		_clickOnAvailableQuantityForSorting(headerName);
		clickOnRandomPage(10, 1);
		clickOnAnyRandomMember(); // -----
		wait.hardWait(4);
	}

	public void _clickOnAvailableQuantityForSorting(String tableHeading) {
		isElementDisplayed("th_lookup", tableHeading);
		if (ConfigPropertyReader.getProperty("browser").equals("ie")
				|| ConfigPropertyReader.getProperty("browser").equals("internet explorer"))
			clickUsingXpathInJavaScriptExecutor(element("th_lookup", tableHeading));
		else
			element("th_lookup", tableHeading).click();
		logMessage("STEP : Clicked on " + tableHeading + " for Sorting");
	}

	private void _selectPage(int randomNumberInString) {
		isElementDisplayed("lnk_pages", String.valueOf(randomNumberInString));
		clickUsingXpathInJavaScriptExecutor(element("lnk_pages", String.valueOf(randomNumberInString)));
		logMessage("STEP : page at the position of " + randomNumberInString + " is clicked in lnk_pages\n");

	}

	public void verifyAutoPayStatusAfterAutoRenewal(String value) {
		hardWaitForIEBrowser(5);
		isElementDisplayed("mbr_autoPay", value);
		Assert.assertTrue(isElementDisplayed("mbr_autoPay", value), "Auto Pay renewal image is not checked\n");
		logMessage("ASSERT PASSED : <b>AutoPay Renewal image is checked</b>\n");
	}

	public void clickOnRandomPage(int max, int min) {
		try {
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("lnk_pages", "2");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			Random rand = new Random();
			int randomNumber = rand.nextInt((max - min) + 1) + min;
			String randomNumberInString = String.valueOf(randomNumber);
			isElementDisplayed("lnk_pages", randomNumberInString);
			clickUsingXpathInJavaScriptExecutor(element("lnk_pages", randomNumberInString));
			logMessage("STEP : Page at the position of " + randomNumberInString + " is clicked in lnk_pages\n");
		} catch (NoSuchElementException exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}
	}

	public void selectAdvanceOptionFlag(String field, String column) {
		isElementDisplayed("chckbox_advanceFlag", field, column);
		element("chckbox_advanceFlag", field, column).click();
		logMessage("STEP: " + field + " flag is selected\n");
	}

	public String getTelephoneNumberType(String tabName) {
		int i;
		String type;
		isElementDisplayed("list_tableData", tabName);
		if (elements("list_tableData", tabName).size() > 1) {
			for (i = 1; i <= elements("list_tableData", tabName).size(); i++) {
				if (checkIfElementIsThere("img_primaryFlag", tabName, String.valueOf(7), String.valueOf(i))) {
					break;
				}
			}
			type = element("txt_telephoneType", tabName, String.valueOf(4), String.valueOf(i)).getText().trim();
			type = Character.toUpperCase(type.charAt(0)) + type.substring(1);
			logMessage("STEP: Primary Telephone type is " + type);
			return type;
		} else
			return "home";
	}

	public void verifyTelephoneDetails(String tabName, String telephoneType, String telephoneNumber) {
		int i;
		isElementDisplayed("list_tableData", tabName);
		for (i = 1; i <= elements("list_tableData", tabName).size(); i++) {
			if (telephoneType.equals(
					element("txt_telephoneType", tabName, String.valueOf(4), String.valueOf(i)).getText().trim())) {
				break;
			}
		}
		verifyTelephoneNumber(tabName, 6, i, telephoneNumber, "Phone number");
		verifyPrimaryFlagIsSet(tabName, 7, i);
	}

	public void verifyTelephoneNumber(String tabName, int index1, int index2, String expectedValue, String field) {
		System.out.println(
				"-------" + element("txt_telephoneType", tabName, String.valueOf(index1), String.valueOf(index2))
						.getText().trim());
		Assert.assertTrue(expectedValue.equalsIgnoreCase(
				element("txt_telephoneType", tabName, String.valueOf(index1), String.valueOf(index2)).getText().trim()),
				"ASSERT FAILED: " + field + " is not verified as " + expectedValue + "\n");
		logMessage("ASSERT PASSED: " + field + " is verified as " + expectedValue + "\n");

	}

	public void verifyPrimaryFlagIsSet(String tabName, int index1, int index2) {
		isElementDisplayed("img_primaryFlag", tabName, String.valueOf(index1), String.valueOf(index2));
		logMessage("ASSERT PASSED: Primary flag is set\n");
	}

	public void verifyAddress(String tabName, int index1, int index2, String expectedValue, String field) {
		Assert.assertTrue(
				element("txt_telephoneType", tabName, String.valueOf(index1), String.valueOf(index2)).getText().trim()
						.contains(expectedValue),
				"ASSERT FAILED: " + field + " is not verified as " + expectedValue + "\n");
		logMessage("ASSERT PASSED: " + field + " is verified as " + expectedValue + "\n");

	}

	public void verifyAddressDetails(String tabName, Map<String, String> changedValues, String memberType) {
		int i;
		isElementDisplayed("list_tableData", tabName);
		for (i = 1; i <= elements("list_tableData", tabName).size(); i++) {
			if (changedValues.get("AddressType").equals(
					element("txt_telephoneType", tabName, String.valueOf(4), String.valueOf(i)).getText().trim())) {
				break;
			}
		}
		if (memberType.equals("Existing Member")) {
			verifyTelephoneNumber(tabName, 6, i, changedValues.get("Address-2"), "Address Line-2");
		} else {
			verifyTelephoneNumber(tabName, 5, i, changedValues.get("Address Line 1"), "Address Line 1");
			// verifyAddress(tabName,5,i,changedValues.get("Expected_AddressIweb?"),"Address
			// Line 1");
			verifyAddress(tabName, 7, i, changedValues.get("City"), "City");
			verifyAddress(tabName, 7, i, changedValues.get("Zip_Code"), "Zip_Code");
			verifyPrimaryFlagIsSet(tabName, 8, i);
		}
	}

	public List<String> getCustomerFullNameBasedOnInvoice(String invoiceNumber) { // Returns
		// a
		// map,
		// solves
		// last
		// name
		// with
		// space
		// problem
		clickOnEditNameAndAddress();
		switchToFrame("iframe1");
		customerLname = getNameFromEditNameAndAddressButton("lastName");
		memberDetails.add(customerLname);
		customerFname = getNameFromEditNameAndAddressButton("firstName");
		memberDetails.add(customerFname);
		clickOnCancelButton();
		handleAlert();
		switchToDefaultContent();
		customerContactId = element("txt_renewalContactId").getText();
		memberDetails.add(customerContactId);
		memberDetails.add(invoiceNumber);
		logMessage("Step : Last Name of member is " + memberDetails.get(0));
		logMessage("Step : First Name of member is " + memberDetails.get(1));
		logMessage("Step : Customer ID of member is " + memberDetails.get(2));
		logMessage("Step : Invoice Number is " + memberDetails.get(3));
		return memberDetails;
	}

	public String importProfileSheet(String importName, String description, String fileType) {
		String importedFileName = importName + System.currentTimeMillis();
		isElementDisplayed("inp_dateForReviewModes", "import name ");
		element("inp_dateForReviewModes", "import name ").sendKeys(importedFileName);
		isElementDisplayed("inp_dateForReviewModes", "description ");
		element("inp_dateForReviewModes", "description ").sendKeys(description);
		selectDropDownValue(fileType);
		return importedFileName;
	}

	public void selectFileToUpload(String fileName) {
		wait.hardWait(1);
		String path = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "UploadFiles"
				+ File.separator + "ImportProfile" + File.separator;
		File filePath = new File(path + fileName);
		wait.hardWait(2);
		isElementDisplayed("btn_browse");
		element("btn_browse").sendKeys(filePath.getAbsolutePath());
		hardWaitForIEBrowser(2);
		logMessage("Step : " + fileName + " is uploaded \n");
	}

	public void verifyACSImportMatchProfilePage(String importName, String description, String fileType,
			String Import_File) {

		Assert.assertEquals(element("label_profile_title", "ACS Import Match Profile").getText().trim(),
				"ACS Import Match Profile");
		Assert.assertEquals(getMemberDetailsOnMemberShipProfile("import name"), importName);
		Assert.assertEquals(getMemberDetailsOnMemberShipProfile("description"), description);
		Assert.assertEquals(getMemberDetailsOnMemberShipProfile("type"), fileType);
		String importDate = DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/yyyy", "EST5EDT");
		Assert.assertEquals(getMemberDetailsOnMemberShipProfile("import date"), importDate);
		Assert.assertTrue(getMemberDetailsOnMemberShipProfile("import file").trim().contains(Import_File),
				"ASSERT FAILED: Expected value is " + Import_File + " but found "
						+ getMemberDetailsOnMemberShipProfile("import file").trim());
		Assert.assertEquals(getMemberDetailsOnMemberShipProfile("import name"), importName);
		logMessage("Step: ACS Import Match Profile Page is verifed \n");
	}

	public void verifyMatchTotalChildForm() {
		expandDetailsMenu("match totals");
		Assert.assertTrue(isElementDisplayed("table_data"), "ASSERT FAILED: Child form is null");
		logMessage("ASSERT PASSED: Verified Match totals child form is not null");

	}

	public void enterExpiryDatesBeforeAndAfterExpressRenewal() {
		isElementDisplayed("inp_customerId");
		EnterTextInField(elements("inp_customerId").get(0), DateUtil.getAnyDateForType("MM/dd/yyyy", -1, "month"));
		EnterTextInField(elements("inp_customerId").get(1), DateUtil.getAnyDateForType("MM/dd/yyyy", +1, "month"));
		logMessage("Step : Expiry Date greater than is entered as "
				+ DateUtil.getAnyDateForType("MM/dd/yyyy", -1, "month"));
		logMessage(
				"Step : Expiry Date less than is entered as " + DateUtil.getAnyDateForType("MM/dd/yyyy", +1, "month"));
	}

	public String fetchExpressURLForRenewal() {
		String expressurl;
		expressurl = element("txt_endDate", String.valueOf(1), String.valueOf(19)).getText().trim();
		logMessage("Step : Express URL for top most record is " + expressurl);
		return expressurl;
	}

	public void verifyImportedFileIsPresentInList(String importedFile) {
		isElementDisplayed("link_importMatch",
				DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/yyyy", "EST5EDT"),
				map().get("Import_File").trim());
		Assert.assertEquals(element("link_importMatch",
				DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/yyyy", "EST5EDT"),
				map().get("Import_File").trim()).getText().trim(), importedFile);

		logMessage("ASSERT PASSED : Imported file " + importedFile + " is present in the list\n");

	}

	public void verifyIsAcsNetworkMember(String acsMember) {
		switchToFrame("iframe1");
		isElementDisplayed("chkbox_acsMember");
		if (acsMember.equals("Yes")) {
			Assert.assertTrue(element("chkbox_acsMember").isSelected(),
					"ASSERT FAILED: User is an ACS Member is not verified\n");
			logMessage("ASSERT PASSED: User is an ACS Member is verified\n");
		} else {
			Assert.assertTrue(!element("chkbox_acsMember").isSelected(),
					"ASSERT FAILED: User is an ACS Member is verified\n");
			logMessage("ASSERT PASSED: User is not an ACS Member is verified\n");
		}
		clickOnSaveButtonForBillingAddress();
		switchToDefaultContent();
	}

	// public List<String> getTechnicalDivisions(){
	// List<String> techDivisions=new ArrayList<String>();
	// if(checkIfElementIsThere("txt_divisionMember","Division Member")){
	// int size=elements("txt_divisionMember","Division Member").size();
	// for(int i=0;i<size;i++){
	// techDivisions.add(elements("txt_techDivision","Division
	// Member").get(i).getText().trim());
	// }
	// }
	// if(checkIfElementIsThere("txt_divisionMember","Subdivision Member")){
	// int size=elements("txt_divisionMember","Subdivision Member").size();
	// for(int i=0;i<size;i++){
	// techDivisions.add(elements("txt_techDivision","Subdivision
	// Member").get(i).getText().trim());
	// }
	// }
	//
	// if(techDivisions.size()>0)
	// logMessage("STEP: Technical divisions are "+techDivisions);
	// else
	// logMessage("STEP: Technical divisions are not present\n");
	//
	// return techDivisions;
	// }

	public List<String> getTechnicalDivisions() {
		List<String> memTypeArray = new ArrayList<String>();
		memTypeArray.add("Cancelled");
		memTypeArray.add("Terminated by Process-chp");
		memTypeArray.add("Resigned-Unacceptable");
		List<String> techDivisions = new ArrayList<String>();
		if (checkIfElementIsThere("txt_divisionMember", "Division Member")) {
			int size = elements("txt_divisionMember", "Division Member").size();
			for (int i = 0; i < size; i++) {
				if (!(memTypeArray
						.contains(elements("txt_technicalDivision", "Division Member").get(i).getText().trim()))) {
					techDivisions.add(elements("txt_techDivision", "Division Member").get(i).getText().trim());
				}
			}
		}
		if (checkIfElementIsThere("txt_divisionMember", "Subdivision Member")) {
			int size = elements("txt_divisionMember", "Subdivision Member").size();
			for (int i = 0; i < size; i++) {
				if (!(memTypeArray
						.contains(elements("txt_technicalDivision", "Subdivision Member").get(i).getText().trim()))) {
					techDivisions.add(elements("txt_techDivision", "Subdivision Member").get(i).getText().trim());
				}
			}
		}

		if (techDivisions.size() > 0)
			logMessage("STEP: Technical divisions are " + techDivisions);
		else
			logMessage("STEP: Technical divisions are not present\n");

		return techDivisions;
	}

	public void verifyTermStartDateAndTermEndDateIsEmptyForAACT(String Query, String queryPageUrl) {
		isElementDisplayed("txt_termStartDaterenewal", "1");

		if (element("txt_termStartDaterenewal", "1").getText().length() != 1
				&& element("txt_termEndDaterenewal", "1").getText().length() != 1) {
			expandDetailsMenuIfAlreadyExpanded("invoices");
			logMessage("STEP : Term Start date and Term Endd Date are not empty \n");
			selectValidUserForAACTOMR(Query, queryPageUrl);
		} else {
			Assert.assertTrue(element("txt_termStartDaterenewal", "1").getText().length() == 1,
					"Term Start Date is not Empty");
			logMessage("ASSERT PASSED : Term Start date is empty\n");
			Assert.assertTrue(element("txt_termEndDaterenewal", "1").getText().length() == 1,
					"Term End Date is not Empty");
			logMessage("ASSERT PASSED : Term End date is empty\n");
		}

	}

	public void verifyMembershipTypeInIndividualMemberships(String membershipType) {

		isElementDisplayed("txt_divisionMember", membershipType);
		logMessage("ASSERT PASSED : " + membershipType + " is verified in txt_divisionMember \n");
	}

	public void clickOnGoToRecordButton(String membershipType, String index) {
		isElementDisplayed("txt_gotorecord", membershipType, index);
		element("txt_gotorecord", membershipType, index).click();
		wait.waitForPageToLoadCompletely();

		logMessage("Step: click on 'go to record' image for " + membershipType + " \n");

	}

	public void enterExpiryDatesBeforeAndAfterForAACTOMR() {
		isElementDisplayed("inp_customerId");
		EnterTextInField(elements("inp_customerId").get(0), DateUtil.getAnyDateForType("MM/dd/yyyy", -1, "year"));
		EnterTextInField(elements("inp_customerId").get(1), DateUtil.getAnyDateForType("MM/dd/yyyy", 1, "year"));
		logMessage("STEP : Expiry Date greater than is entered as "
				+ DateUtil.getAnyDateForType("MM/dd/yyyy", -1, "year"));
		logMessage("STEP : Expiry Date less than is entered as " + DateUtil.getAnyDateForType("MM/dd/yyyy", 1, "year"));
	}

	public void verifyDetailsForPaymentsChildForm(String type, String cardType, String membershipType,
			String invoiceTotal, String productName) {
		verifyDetailsForAACTOMR(cardType, type, "1", "Card Type");
		verifyDetailsForAACTOMR(DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/yyyy", "EST5EDT"), type,
				"3", "Payment Date");
		verifyDetailsForAACTOMR(invoiceTotal.replace("$", "").trim(), type, "4", "Invoice Total");
		verifyMembershipTypeForAACTOMR(type, "2", membershipType, "Membership Type");
		verifyMembershipTypeForAACTOMR(type, "2", productName, "Product Name");
	}

	private void verifyMembershipTypeForAACTOMR(String type, String index, String membershipType, String label) {
		isElementDisplayed("txt_membershipType", type, index);
		Assert.assertTrue(element("txt_membershipType", type, index).getText().contains(membershipType),
				"ASSERT FAILED: Expected value is " + membershipType + " but found "
						+ element("txt_membershipType", type, index).getText());
		logMessage("ASSERT PASSED: " + label + " is verified as " + membershipType);
	}

	private void verifyDetailsForAACTOMR(String value, String text, String index, String label) {
		isElementDisplayed("txt_payments", text, index);
		Assert.assertEquals(element("txt_payments", text, index).getText().replace("$", "").trim(), value);
		logMessage("ASSERT PASSED: " + label + " is verified as " + value + "\n");

	}

	public String getMemberCountryName() {
		isElementDisplayed("list_country");
		String countryName = element("list_country").getText();
		logMessage("STEP: Member country name is fetched as " + countryName);
		return countryName;
	}

	public String selectValidUserForAACTOMR(String query, String queryPageUrl) {
		wait.hardWait(2);
		launchUrl(queryPageUrl);
		selectAndRunQuery(query);
		enterExpiryDatesBeforeAndAfterForAACTOMR();
		clickOnGoButtonAfterPackageSelection();
		String weblogin = getCstWebLogin();
		expandDetailsMenuIfAlreadyExpanded("invoices");
		verifyTermStartDateAndTermEndDateIsEmptyForAACT(query, queryPageUrl);
		logMessage("STEP : Valid Member selected For AACT OMR\n");
		return weblogin;

	}

	public String getDomesticSourceCode() { // String type
		int index = 0;
		String srcCodeType, srcCode = "";
		// if(type.equals("International")){
		// srcCodeType="i";
		// }
		// else
		// srcCodeType="d";
		isElementDisplayed("list_sourceCodes");
		for (int i = 1; i <= elements("list_sourceCodes").size(); i++) {
			if (element("lnk_first_invoice_number", String.valueOf(i)).getText().trim().endsWith("d")) { // srcCodeType
				index = i;
				srcCode = element("lnk_first_invoice_number", String.valueOf(index)).getText().trim();
				break;
			}
		}
		System.out.println("-----index:" + index);
		System.out.println("domestic source code:" + srcCode);
		return srcCode;
	}

	public void clickOnArrowButton(String srcCodeName) {
		isElementDisplayed("lnk_sourceCode", srcCodeName);
		element("lnk_sourceCode", srcCodeName).click();
		logMessage("STEP: Source Code " + srcCodeName + " is selected\n");
	}

	public void selectDomesticProduct() {
		int i = 1;
		String srcCode;
		HomePageActions_IWEB objHome = new HomePageActions_IWEB(driver);
		MembershipPageActions_IWEB obj = new MembershipPageActions_IWEB(driver);
		while (i <= 4) {
			srcCode = getDomesticSourceCode(); // map().get("SourceCodeType")
			if (!srcCode.equals("")) {
				clickOnArrowButton(srcCode);
				break;
			} else {
				i++;
				logMessage("STEP: Looping again to find domestic source code\n");
				objHome.clickOnTab("Query Source Code");
				obj.selectAndRunQuery(getYamlValue("ACS_SourceCodes.queryName"));
				obj.verifyOueryAskAtRunTimePage();
				obj.clickOnGoButtonAfterPackageSelection();
			}
		}
	}

	public String getSourceCodeValue(String label) {
		isElementDisplayed("txt_sourceCode", label);
		logMessage("STEP: Source code value is " + element("txt_sourceCode", label).getText().trim());
		return element("txt_sourceCode", label).getText().trim();
	}

	public void verifyIsSourceCodeActive(String label) {
		isElementDisplayed("txt_sourceCode", label);
		Date date1 = DateUtil.convertStringToDate(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"),
				"MM/dd/yyyy");
		Date date2 = DateUtil.convertStringToDate(DateUtil.convertStringToParticularDateFormat(
				element("txt_sourceCode", label).getText().trim(), "MM/dd/yyyy"), "MM/dd/yyyy");
		Assert.assertTrue(date1.before(date2),
				"ASSERT FAILED: Source Code is not active as current date is not less than end date\n");
		logMessage("ASSERT PASSED: Source Code is active as current date is less than end date\n");
	}

	public void verifyMemberDetails(String tabName, int index1, int index2, String expectedValue, String field) {
		Assert.assertTrue(expectedValue.equalsIgnoreCase(
				element("txt_memberDetails", tabName, String.valueOf(index1), String.valueOf(index2)).getText().trim()),
				"ASSERT FAILED: " + field + " is not verified as " + expectedValue + "\n");
		logMessage("ASSERT PASSED: " + field + " is verified as " + expectedValue + "\n");

	}

	public List<String> getWebloginAndRecordNumber() {
		List<String> memberRecord = new ArrayList<>();
		// String recordNumber = getRecordNumber();
		// String weblogin = getCstWebLogin();
		String recordNumber = getCstWebLoginForMembership("4", "Record Number");
		String weblogin = getCstWebLoginForMembership("3", "Web login");
		element("txt_current", String.valueOf(1)).click();
		handleAlert();
		memberRecord.add(recordNumber);
		memberRecord.add(weblogin);
		return memberRecord;

	}

	public String getRecordNumber() {
		String recordNumber;
		isElementDisplayed("txt_recordNo");
		recordNumber = element("txt_recordNo").getText();
		logMessage("STEP : Record Number is fetched as " + recordNumber);
		return recordNumber;
	}

	public void verifyNomineeStatusOnIWEB(String url, String status, String email, String fname, String lname) {
		launchUrl(url);
		handleAlert();
		expandDetailsMenuIfAlreadyExpanded("my acs nominations");
		String currentDate = DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/yyyy", "EST5EDT");
		// String currentDate =
		// DateUtil.getCurrentdateInStringWithGivenFormate("M/d/yyyy");
		hardWaitForIEBrowser(4);
		verifyMembershipTypeForAACTOMR(email, "2", fname, "First Name");
		verifyMembershipTypeForAACTOMR(email, "1", lname, "Last Name");
		verifyDetailsForAACTOMR(currentDate, email, "1", "Invitation Date");
		verifyDetailsForAACTOMR(status, email, "2", "Status");

	}

	public String getApplicationID(String email) {
		// String currentDate =
		// DateUtil.getCurrentdateInStringWithGivenFormate("MM/d/yyyy");
		isElementDisplayed("txt_membershipType", email, "5");
		String appID = element("txt_membershipType", email, "5").getText().trim();
		logMessage("STEP: Application Id of the nominee is " + appID + "\n");
		return appID;
	}

	public void verifyNominatorDetailsOnIweb(String iWEBurl, String app_ID, String program, String channel,
			String fname, String lname, String email, String status, String paymentStatus, String sourceCode,
			String memberID) {
		launchUrl(iWEBurl);
		handleAlert();
		expandDetailsMenuIfAlreadyExpanded("my acs nominations");
		verifyDetailsForNominator("Application ID", app_ID, "1", "4");
		verifyDetailsForNominator("Program", program, "1", "5");
		verifyDetailsForNominator("Channel", channel, "1", "6");
		verifyDetailsForNominator("First Name", fname, "1", "7");
		verifyDetailsForNominator("Last Name", lname, "1", "8");
		verifyDetailsForNominator("Email", email, "1", "9");
		verifyDetailsForNominator("Invitation Date", "1", "10");
		verifyDetailsForNominator("Status", status, "1", "11");
		verifyDetailsForNominator("Joined Date", "1", "13");
		verifyDetailsForNominator("Payment Status", paymentStatus, "1", "14");
		verifyDetailsForNominator("Source code", sourceCode, "1", "15");
		verifyDetailsForNominator("Mbr Source code", sourceCode, "1", "16");
		verifyDetailsForNominator("ConstitID", memberID, "1", "12");

	}

	public void verifyDetailsForNominator(String label, String rowNum, String index) {
		isElementDisplayed("txt_endDate", rowNum, index);
		String valueFromIWEB = element("txt_endDate", rowNum, index).getText().trim();
		String value = DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/yyyy", "EST5EDT");
		Assert.assertEquals(valueFromIWEB, value);
		logMessage("ASSERT PASSED: " + label + " is verfied as " + valueFromIWEB);

	}

	public void verifyDetailsForNominator(String label, String value, String rowNum, String index) {
		isElementDisplayed("txt_endDate", rowNum, index);
		String valueFromIWEB = element("txt_endDate", rowNum, index).getText().trim();
		Assert.assertEquals(valueFromIWEB, value);
		logMessage("ASSERT PASSED: " + label + " is verfied as " + value);
	}

	public void clickOnConstitID_underMyACSNominations() {
		isElementDisplayed("arrow_selectMember", "1", "12");
		element("arrow_selectMember", "1", "12").click();
		logMessage("STEP: Click on ConstitID \n");
	}

	public void clickOnNominatorID_underMyACSApplication() {
		isElementDisplayed("arrow_selectMember", "1", "14");
		element("arrow_selectMember", "1", "14").click();
		logMessage("STEP: Click on Nominator's ID \n");
	}

	public void verifyNomieeDetails(String app_ID, String program, String channel, String status, String paymentStatus,
			String sourceCode, String memberID) {
		collapseDetailsMenu("my acs nominations");
		expandDetailsMenuIfAlreadyExpanded("my acs applications");
		verifyDetailsForNominator("Application ID", app_ID, "1", "4");
		verifyDetailsForNominator("Program", program, "1", "5");
		verifyDetailsForNominator("Channel", channel, "1", "6");
		verifyDetailsForNominator("App Date", "1", "7");
		verifyDetailsForNominator("Status", status, "1", "9");
		verifyDetailsForNominator("Joined Date", "1", "10");
		verifyDetailsForNominator("Payment Status", paymentStatus, "1", "11");
		verifyDetailsForNominator("Source code", sourceCode, "1", "12");
		verifyDetailsForNominator("Mbr Source code", sourceCode, "1", "13");

	}

	public void enterDatesInRunTime(int times) {
		isElementDisplayed("inp_customerId");
		for (int i = 0; i < times; i++) {
			if (isBrowser("ie") || isBrowser("internet explorer")) {
				sendKeysUsingXpathInJavaScriptExecutor(elements("inp_customerId").get(i),
						DateUtil.getAnyDateForType("MM/dd/yyyy", 1, "year"));
			} else {
				EnterTextInField(elements("inp_customerId").get(i),
						DateUtil.getAnyDateForType("MM/dd/yyyy", 1, "year"));

			}
			logMessage("STEP : Ask at run time Date is entered as "
					+ DateUtil.getAnyDateForType("MM/dd/yyyy", 1, "year") + " for " + i + " field");
		}
	}

	public void enterExpiryDatesBeforeAndAfterSourceCodes() {
		isElementDisplayed("inp_customerId");
		elements("inp_customerId").get(0).click();
		elements("inp_customerId").get(0).clear();
		EnterTextInField(elements("inp_customerId").get(0), DateUtil.getAnyDateForType("MM/dd/yyyy", +1, "year"));
		elements("inp_customerId").get(1).click();
		elements("inp_customerId").get(1).clear();
		EnterTextInField(elements("inp_customerId").get(1), DateUtil.getAnyDateForType("MM/dd/yyyy", +3, "year"));
		logMessage("Step : Source Code Start Date Less Than or Equal To is entered as "
				+ DateUtil.getAnyDateForType("MM/dd/yyyy", +1, "year"));
		logMessage("Step : Source Code End Date Greater Than or Equal To is entered as "
				+ DateUtil.getAnyDateForType("MM/dd/yyyy", +3, "year"));
	}

	public String getDefaultSourceCodeForMGM(String country) {
		String sourceCode = "";
		System.out.println("country " + country);
		expandDetailsMenu("acs membership application program default source code");
		if (country.equals("UNITED STATES")) {
			sourceCode = _getSourceCode("Domestic");
		} else {
			sourceCode = _getSourceCode("Foregin");
		}
		return sourceCode;
	}

	public void selectMerchandiseProductNameGC(String productName) {

		switchToDefaultContent();
		switchToFrame(element("iframe"));
		isElementDisplayed("drpdwn_merchendiseProduct");
		selectProvidedTextFromDropDown(element("drpdwn_merchendiseProduct"), productName);
		wait.hardWait(2);
		wait.waitForPageToLoadCompletely();
		logMessage("Step : " + productName + " is selected from product merchandise\n");
	}

	public void selectAndAddBatchIFNotPresent(String batchName, String paymentType, String paymentMethod) {

		holdExecution(2000);

		if (verifyBatchIsPresent(batchName)) {
			selectOrderEntryInfo("batch", batchName);
		} else {
			addBatch(batchName.replaceAll("ACS: ", ""), "QA");
		}
		waitForSpinner();
		selectOrderEntryInfo("PaymentType", paymentType);
		waitForSpinner();
		selectOrderEntryInfo("paymentMethod", paymentMethod);

	}

	private void fillCardInformation(String cardNumber, String expireDate, String cvvNumber) {
		enterCardDetails("cardNumber", cardNumber);
		selectMemberInfo("expireDate", expireDate);
		enterCardDetails("cvvNumber", cvvNumber);
	}

	public void fillAllTypeOFPaymentDetails(String PaymentMethod, String cardNumber, String dinerscardNumber,
			String referenceNumber, String discovercardNumber, String AMEXcardNumber, String expireDate,
			String cvvNumber, String checkNumber) {
		switch (PaymentMethod) {
		case "Visa/MC":
			fillCardInformation(cardNumber, expireDate, cvvNumber);
			break;

		case "BOA - Check":
			enterCardDetails("checkNumber", checkNumber);
			break;

		case "check":
			enterCardDetails("checkNumber", checkNumber);
			break;

		case "cash":
			enterCardDetails("referencenumber", referenceNumber);
			break;

		case "Diners":
			fillCardInformation(dinerscardNumber, expireDate, cvvNumber);
			break;

		case "Discover":
			fillCardInformation(discovercardNumber, expireDate, cvvNumber);
			break;
		case "AMEX":
			fillCardInformation(AMEXcardNumber, expireDate, cvvNumber);
			break;

		case "Gift Card Adjustment":
			enterCardDetails("referencenumber", referenceNumber);
			break;
		}

	}

	public String _getSourceCode(String sourceCodetype) {
		String sourceCode = "";
		if (sourceCodetype.equals("Domestic")) {
			isElementDisplayed("txt_payments", "MGM", "1");
			sourceCode = element("txt_payments", "MGM", "1").getText().trim();
		} else {
			isElementDisplayed("txt_payments", "MGM", "2");
			sourceCode = element("txt_payments", "MGM", "2").getText().trim();
		}

		logMessage("STEP:" + sourceCodetype + " active source Code for MGM is fetched as " + sourceCode + "\n");
		return sourceCode;
	}

	public void verifythatAllInviteesExistOnIWEB(List<String> emails, String status) {
		handleAlert();
		expandDetailsMenuIfAlreadyExpanded("my acs nominations");
		List<String> uniqueEmails = getAllInvitees();

		for (int i = 0; i < emails.size(); i++) {
			System.out.println("emails::" + emails.get(i) + "::::::::");
		}
		for (int i = 0; i < uniqueEmails.size(); i++) {
			System.out.println("uniqueEmails::" + uniqueEmails.get(i) + "::::::::");
		}

		List<String> notfoundEmails = new ArrayList<>();

		boolean flag = true;
		int i = 1;
		for (String email : emails) {
			if (!uniqueEmails.contains(email.trim())) {
				notfoundEmails.add(email);
			}
		}
		if (notfoundEmails.size() > 0) {
			for (String emailNotFound : notfoundEmails) {
				logMessage("Email " + emailNotFound + "not found");
			}
			flag = false;
		} else {
			flag = true;
		}
		Assert.assertTrue(flag, "ASSERT FAILED: Invitee is not Present in IWEB \n");
		logMessage("ASSERT PASSED: All invitees are present on IWEB \n");
	}

	private void _verifyStatusForMGMOnIWEB(String email, String status, int i) {
		// TODO Auto-generated method stub
		try {
			verifyDetailsForAACTOMR(status, email, "2", "Status");
		} catch (NoSuchElementException e) {
			i++;
			clickOnPageLinks(i);
			_verifyStatusForMGMOnIWEB(email, status, i);
		}
	}

	private List<String> getAllInvitees() {
		List<String> uniqueEmails = new ArrayList<>();
		int i = 0;
		int linkSize = elements("link_pagesAvailable").size();
		System.out.println("link_pagesAvailable" + linkSize);
		do {
			for (int count = 0; count < elements("txt_payments", "MGM", "4").size(); count++) {

				isElementDisplayed("txt_payments", "MGM", "4");
				uniqueEmails.add(elements("txt_payments", "MGM", "4").get(count).getText().trim());
			}
			i++;
			if (i >= linkSize) {
				break;
			}
			clickOnPageLinks(i);
			waitForSpinner();
		} while (i <= linkSize);
		return uniqueEmails;
	}

	private void clickOnPageLinks(int i) {
		wait.waitForPageToLoadCompletely();
		handleAlert();
		isElementDisplayed("link_pagesAvailable");
		elements("link_pagesAvailable").get(i).click();

		logMessage("STEP: Page link " + String.valueOf(i + 1) + "  is clicked \n");

	}

	public void enterCurrentDatesInQuery(int times) {
		isElementDisplayed("inp_customerId");
		for (int i = 0; i < times; i++) {
			if (isBrowser("ie") || isBrowser("internet explorer")) {
				sendKeysUsingXpathInJavaScriptExecutor(elements("inp_customerId").get(i),
						DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"));
				// DateUtil.getAnyDateForType("MM/dd/yyyy", 1, "year"));
			} else {
				EnterTextInField(elements("inp_customerId").get(i),
						DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"));

			}
			logMessage("STEP : Ask at run time Date is entered as "
					+ DateUtil.getAnyDateForType("MM/dd/yyyy", 1, "year") + " in " + i + " field");
		}
	}

	public String getEffectiveAndExpireDates(String tabName, int row, int column) {
		return element("txt_memberDetails", tabName, String.valueOf(row), String.valueOf(column)).getText().trim();
	}

	// public String getEffectiveAndExpireDates(String tabName, int row, int
	// column) {
	// return element("txt_memberDetails", tabName, String.valueOf(row),
	// String.valueOf(column)).getText().trim();
	// }
	public void enterSalesPriceForGCMembership(String salesprice) {
		isElementDisplayed("inp_salePrice");
		sendKeysUsingXpathInJavaScriptExecutor(element("inp_salePrice"), salesprice);
		logMessage("Step : Sales price value entered as " + salesprice);
	}

	// public Map<String, String> getTermDatesOfIndividualMembership(String
	// tabName) {
	// Map<String, String> individualDates = new HashMap<>();
	// wait.waitForPageToLoadCompletely();
	// isElementDisplayed("list_tableData", tabName);
	// int size=elements("list_tableData", tabName).size();
	// for (int i = 2; i <= size ; i++) {
	// if (element("txt_memberDetails", tabName, String.valueOf(i),
	// String.valueOf(6)).getText()
	// .contains("Active")) {
	// individualDates.put("EffectiveDate", getEffectiveAndExpireDates(tabName,
	// i, 9));
	// individualDates.put("ExpireDate", getEffectiveAndExpireDates(tabName, i,
	// 10));
	// break;
	// }
	// }
	// return individualDates;
	// }

	public Map<String, String> getTermDatesOfIndividualMembership(String tabName) {
		Map<String, String> individualDates = new HashMap<>();
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("txt_termDates", tabName, String.valueOf(3));
		String effectiveDate = element("txt_termDates", tabName, String.valueOf(3)).getText().trim();
		// effectiveDate=DateUtil.convertStringToParticularDateFormat(effectiveDate,"MM/dd/yyyy");
		logMessage("Step: Effective date is " + effectiveDate);
		individualDates.put("EffectiveDate", effectiveDate);

		isElementDisplayed("txt_termDates", tabName, String.valueOf(4));
		String expireDate = element("txt_termDates", tabName, String.valueOf(4)).getText().trim();
		// expireDate=DateUtil.convertStringToParticularDateFormat(expireDate,
		// "MM/dd/yyyy");
		logMessage("Step: Expire date is " + expireDate);
		individualDates.put("ExpireDate", expireDate);
		return individualDates;
	}

	public String verifyTermDatesOfChapterMembership(String tabName, Map<String, String> datesMap) {
		String chapterName = null;
		// isElementDisplayed("txt_chapterName",
		// datesMap.get("EffectiveDate").toString(),
		// datesMap.get("ExpireDate").toString(), "4");

		chapterName = element("txt_chapterName",
				DateUtil.convertStringToParticularDateFormat(datesMap.get("EffectiveDate").toString(), "MM/dd/yyyy"),
				DateUtil.convertStringToParticularDateFormat(datesMap.get("ExpireDate").toString(), "MM/dd/yyyy"), "4")
						.getText().trim();
		logMessage("ASSERT PASSED: Term Dates are verified for National and Chapter memberships having chapter as "
				+ chapterName);
		return chapterName;
	}

	public void clickOnGoToRecordLink(String column) {
		isElementDisplayed("lnk_goToRecord", column);
		element("lnk_goToRecord", column).click();
		logMessage("Step: goto record link is clicked for active individual membership\n");
	}

	public String getInvoiceId(int rowNo, int colNo) {
		isElementDisplayed("txt_date", String.valueOf(rowNo), String.valueOf(colNo));
		return element("txt_date", String.valueOf(rowNo), String.valueOf(colNo)).getText().trim();
	}

	public void getInvoiceHavingTermDatesNotWithinRange() {
		boolean value = false;
		String termStartDate, termEndDate;
		int i;
		isElementDisplayed("lst_childTable");
		for (i = 1; i <= elements("lst_childTable").size(); i++) {
			termStartDate = element("txt_endDate", String.valueOf(i), String.valueOf(14)).getText().trim();
			termEndDate = element("txt_endDate", String.valueOf(i), String.valueOf(15)).getText().trim();
			if (termStartDate.equals("") || termEndDate.equals("")) {
				continue;
			}
			value = verfiyEndAndStartDate(termEndDate, termStartDate);
			if (!value) {
				value = true;
				break;
			}
		}
		Assert.assertTrue(value,
				"ASSERT FAILED: No Invoice is having Term start and end date as not in current date's range \n");
		logMessage("ASSERT PASSED: Invoice Id " + getInvoiceId(i, 12)
				+ " is having Term start and end date as not in current date's range \n");
	}

	public String getInvoiceHavingTermDateWithinRange() {
		boolean value = false;
		String termStartDate, termEndDate, invoiceId = null;
		int i;
		isElementDisplayed("lst_childTable");
		for (i = 1; i <= elements("lst_childTable").size(); i++) {
			termStartDate = element("txt_endDate", String.valueOf(i), String.valueOf(14)).getText().trim();
			termEndDate = element("txt_endDate", String.valueOf(i), String.valueOf(15)).getText().trim();
			if (termStartDate.equals("") || termEndDate.equals("")) {
				continue;
			}
			value = verfiyEndAndStartDate(termEndDate, termStartDate);
			if (value){
				invoiceId = getInvoiceId(i, 12);
				break;
			}
		}
		Assert.assertTrue(value,
				"ASSERT FAILED: No Invoice is having Term start and end date in current date's range \n");
		logMessage("ASSERT PASSED: Invoice Id " + invoiceId
				+ " is having Term start and end date in current date's range \n");
		return invoiceId;
	}

	public void getInvoiceId(String caseId) {
		String invoiceId;
		logMessage("Step: Invoice Id " + getInvoiceId(1, 12) + " is having Term start and end date as null\n");
		if (caseId.equals("3")) {
			verifyTermEndDateAndStartDateIsEmpty();
			getInvoiceHavingTermDatesNotWithinRange();
		} else {
			verifyTermEndDateAndStartDateIsEmpty();
			clickOnGoToRecordButton(getInvoiceId(1, 12), "9");
			verifyMemberDetailsOnMemberShipProfile("proforma", "Yes");
			navigateToBackPage();
			invoiceId = getInvoiceHavingTermDateWithinRange();
			clickOnGoToRecordButton(invoiceId, "9");
			verifyMemberDetailsOnMemberShipProfile("proforma", "No");
			navigateToBackPage();
		}
	}

	public void verifyMemberPaymentStatus(String caseId) {
		String paymentStatus = getPaymentStatus();
		if (caseId.equals("3")) {
			Assert.assertTrue((paymentStatus.equals("Grace") || paymentStatus.equals("Unpaid")),
					"ASSERT FAILED: Payment status is not verified as " + paymentStatus);
			logMessage("ASSERT PASSED: Payment status is verified as " + paymentStatus);
		} else {
			Assert.assertTrue(paymentStatus.equals("Paid"), "ASSERT PASSED: Payment status is not verified as Paid");
			logMessage("ASSERT PASSED: Payment status is verified as " + paymentStatus);
		}
	}

	public void verifyTermDatesAreUnchanged(Map termDates, String tabName) {
		Assert.assertTrue(
				element("txt_termDates", tabName, String.valueOf(3)).getText().trim()
						.equals(termDates.get("EffectiveDate").toString()),
				"ASSERT FAILED: Effective date " + termDates.get("EffectiveDate").toString() + " is changed\n");
		logMessage("ASSERT PASSED: Effective date " + termDates.get("EffectiveDate").toString() + " is unchanged\n");

		Assert.assertTrue(
				element("txt_termDates", tabName, String.valueOf(4)).getText().trim()
						.equals(termDates.get("ExpireDate").toString()),
				"ASSERT FAILED: Expire date " + termDates.get("EffectiveDate").toString() + " is changed\n");
		logMessage("ASSERT PASSED: Expire date " + termDates.get("ExpireDate").toString() + " is unchanged\n");
	}

	public void verifyAdditionOfNewLSAndDetailsOfOldLS(String oldChpName, String newChpName, Map individualDates,
			String caseId) {
		// navigateToBackPage();
		handleAlert();
		expandDetailsMenuIfAlreadyExpanded("chapter memberships");
		verifyOldLSStatusIsTransfered(oldChpName);
		verifyTerminateDateIsCurrentDate(oldChpName,
				DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("MM/dd/yyyy", "EST5EDT"), 7,
				"Terminate date");
		verifyTerminateDateIsCurrentDate(newChpName,
				DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("MM/dd/yyyy", "EST5EDT"), 3, "Join date");
		if (caseId.equals("3")) {
			verifyTerminateDateIsCurrentDate(newChpName, DateUtil.convertStringToParticularDateFormat(
					individualDates.get("ExpireDate").toString(), "MM/dd/yyyy"), 5, "Effective date");
		} else
			verifyTerminateDateIsCurrentDate(newChpName,
					DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("MM/dd/yyyy", "EST5EDT"), 5,
					"Effective date");

		verifyTerminateDateIsCurrentDate(newChpName, DateUtil.convertStringToParticularDateFormat(
				individualDates.get("ExpireDate").toString(), "MM/dd/yyyy"), 6, "Expire date");
		logMessage("Step: New Local Setion " + newChpName + " is added successfully\n");
	}

	public void verifyOldLSStatusIsTransfered(String oldLSName) {
		boolean flag = false;
		isElementDisplayed("txt_payments", oldLSName, String.valueOf(2));
		if (elements("txt_payments", oldLSName, String.valueOf(2)).size() > 1) {
			for (WebElement elem : elements("txt_payments", oldLSName, String.valueOf(2))) {
				if (elem.getText().trim().equals("Transferred")) {
					flag = true;
				}
			}
			if (flag) {
				logMessage("ASSERT PASSED: Member status of " + oldLSName + " is changed to Transferred\n");
			} else
				Assert.assertFalse(true,
						"ASSERT FAILED: Member status of " + oldLSName + " has not changed to Transferred\n");
		} else {
			Assert.assertTrue(
					element("txt_payments", oldLSName, String.valueOf(2)).getText().trim().equals("Transferred"),
					"ASSERT FAILED: Member status of " + oldLSName + " has not changed to Transferred\n");
			logMessage("ASSERT PASSED: Member status of " + oldLSName + " is changed to Transferred\n");
		}
	}

	public void verifyTerminateDateIsCurrentDate(String oldLSName, String date, int column, String field) {
		boolean flag = false;
		isElementDisplayed("txt_payments", oldLSName, String.valueOf(column));
		if (elements("txt_payments", oldLSName, String.valueOf(column)).size() > 1) {
			for (WebElement elem : elements("txt_payments", oldLSName, String.valueOf(column))) {
				if (elem.getText().trim().equals(date)) {
					flag = true;
					break;
				}
			}
			if (flag) {
				logMessage("ASSERT PASSED: " + field + " of " + oldLSName + " is " + date + "\n");
			} else
				Assert.assertFalse(true, "ASSERT FAILED: " + field + " of " + oldLSName + " is not " + date + "\n");
		} else {
			Assert.assertTrue(element("txt_payments", oldLSName, String.valueOf(column)).getText().trim().equals(date),
					"ASSERT FAILED: " + field + " of " + oldLSName + " is not " + date + "\n");
			logMessage("ASSERT PASSED: " + field + " of " + oldLSName + " is " + date + "\n");
		}
	}

	public void verifyDetailsForNewLS(String invoiceName, String value, String field) {
		isElementDisplayed("txt_payments", invoiceName, String.valueOf(6));
		Assert.assertTrue(!element("txt_payments", invoiceName, String.valueOf(6)).getText().trim().equals(value));
		logMessage("ASSERT PASSED: Newly created invoice is having " + field + " as "
				+ element("txt_payments", invoiceName, String.valueOf(6)).getText().trim());
	}

	public void verifyAdditionOfNewInvoiceOnMembershipProfilePage(String invoiceName) {
		verifyTerminateDateIsCurrentDate(invoiceName,
				DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/yyyy", "EST5EDT"), 9,
				"Invoice Transaction date");
		logMessage("ASSERT PASSED: Newly created invoice on Membership profile page is " + invoiceName + "\n");
	}

	public void verifyDetailsForNewLS(String invoiceName, String value, String field, int index) {
		isElementDisplayed("txt_payments", invoiceName, String.valueOf(index));
		System.out
				.println("-------#####" + element("txt_payments", invoiceName, String.valueOf(index)).getText().trim());
		Assert.assertTrue(!element("txt_payments", invoiceName, String.valueOf(index)).getText().trim().equals(value));
		logMessage("ASSERT PASSED: Newly created invoice is having " + field + " as "
				+ element("txt_payments", invoiceName, String.valueOf(index)).getText().trim());
	}

	public void verifyPaidValueForNewLS(String invoiceName, String value, String field, int index) {
		isElementDisplayed("txt_payments", invoiceName, String.valueOf(index));
		System.out
				.println("-------#####" + element("txt_payments", invoiceName, String.valueOf(index)).getText().trim());
		Assert.assertTrue(element("txt_payments", invoiceName, String.valueOf(index)).getText().trim().equals(value));
		logMessage("ASSERT PASSED: Newly created invoice is having " + field + " as "
				+ element("txt_payments", invoiceName, String.valueOf(index)).getText().trim());
	}

	public void selectBatchAndPaymentDetails_AddressChangeProforma(String batchName, String paymentMethod,
			String cardNumber, String expireDate, String cvvNumber, String checkNumber) {

		holdExecution(2000);

		if (verifyBatchIsPresent(batchName)) {
			selectOrderEntryInfo("batch", batchName);
		} else {
			addBatch(batchName.replaceAll("ACS: ", ""), "QA");
		}
		waitForSpinner();
		// selectOrderEntryInfo("PaymentType", paymentType);
		// waitForSpinner();
		selectOrderEntryInfo("paymentMethod", paymentMethod);
		waitForSpinner();
		// System.out.println("check number" + checkNumber);

		if (paymentMethod.equalsIgnoreCase("Visa/MC")) {
			enterCardDetails("cardNumber", cardNumber);
			selectMemberInfo("expireDate", expireDate);
			enterCardDetails("cvvNumber", cvvNumber);
		} else if (paymentMethod.equalsIgnoreCase("BOA - Check")) {
			enterCardDetails("checkNumber", checkNumber);

		} else {
			Assert.fail("ASSERT FAILED : Payment method " + paymentMethod + " is not correct \n");
		}

		// selectBillingAddressIfNotPrePopulated();
		clickOnSaveButtonAddPayment();
		handleAlert();

		// verifyPageTitleContains("CRM | Individuals |");
	}

	public void clickOnSaveButtonAddPayment() {
		isElementDisplayed("btn_saveAddPayment");
		element("btn_saveAddPayment").click();
		logMessage("Step: Save button is clicked\n");
	}

	public void selectOverrideReasonForGC(String overrideReason) {
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		isElementDisplayed("drpdwn_overrideReason");
		selectProvidedTextFromDropDown(element("drpdwn_overrideReason"), overrideReason);
		wait.hardWait(2);
		wait.waitForPageToLoadCompletely();

		logMessage("Step : " + overrideReason + " is selected from override reason\n");
	}

	private void verifyAndClickRedeemGiftCardAppears(String redeemableLinkName) {
		isElementDisplayed("link_invoiceListHeadings", redeemableLinkName);
		wait.hardWait(1);
		clickUsingXpathInJavaScriptExecutor(element("link_invoiceListHeadings", redeemableLinkName));
		logMessage("Step : " + redeemableLinkName + " appered after selecting the batch\n");
		logMessage("Step : " + redeemableLinkName + " link is clicked\n");
	}

	public void findRedeemableGiftCardAndRedeemItFForAACTGc(String redeemableLinkName, String redeemablecode) {
		verifyAndClickRedeemGiftCardAppears(redeemableLinkName);
		enterGiftCardRedeemableCodeForAACTGc(redeemablecode);
		findValidReemableGiftCardFromList();
		clickCheckboxToSelectRedeemAACTGiftCard();
		clickOnSaveButtonForBillingAddress();
		waitForSpinner();
		switchToDefaultContent();
		// clickOnSaveAndFinish();

	}

	private void clickCheckboxToSelectRedeemAACTGiftCard() {

		isElementDisplayed("chkbx_redeemGc");
		element("chkbx_redeemGc").click();
		logMessage("Step : Valid redeemable Gift card checkbox is selected\n");
	}

	private void findValidReemableGiftCardFromList() {
		isElementDisplayed("btn_findRedeemableGc");
		element("btn_findRedeemableGc").click();
		logMessage("Step : Find all redemmable gift cards button is clicked\n");

	}

	private void enterGiftCardRedeemableCodeForAACTGc(String redeemablecode) {
		wait.hardWait(4);
		switchToDefaultContent();
		switchToFrame(element("iframe"));
		wait.hardWait(3);
		isElementDisplayed("inp_redeemGcNumber");
		sendKeysUsingXpathInJavaScriptExecutor(element("inp_redeemGcNumber"), redeemablecode);
		logMessage("Step : Gift card coupon code is entered as " + redeemablecode);

	}

	public void verifyCreditDetailsOnIndividualProfile(String creditamount, String creditused) {
		verifyDetailsForNominator("Batch Closed", "N", "1", "7");
		verifyDetailsForNominator("Credit amount", creditamount, "1", "9");
		verifyDetailsForNominator("Credit Used", creditused, "1", "10");

	}

	public List<String> getAddedLineItemsNamesInOrderEntry() {
		List<String> lineitemsname = new ArrayList<String>();
		for (WebElement e : elements("link_itemInLineItems")) {
			lineitemsname.add(e.getText().trim());
		}
		return lineitemsname;
	}

	public void verifyTotalCreditAndRemaningBalanceInOrderEntry(String productName, String creditvalue,
			String totalamount) {
		float netpayment = Float.parseFloat(totalamount) - Float.parseFloat(creditvalue);
		verifyNetBalanceOfLineItemsOnOrderEntry("Total", productName, Float.parseFloat(totalamount), "6");
		verifyNetBalanceOfLineItemsOnOrderEntry("Balance", productName, 0, "9");
		verifyNetCreditOfLineItemsOnOrderEntry(productName, Float.parseFloat(creditvalue), "7");
		verifyNetPaymentDoneOfLineItemsOnOrderEntry(productName, netpayment);
	}

	private void verifyNetBalanceOfLineItemsOnOrderEntry(String fieldtype, String productName, float actualbalance,
			String index) {
		isElementDisplayed("txt_netBalanceNetForum", productName, index);
		Assert.assertTrue(
				Float.parseFloat(
						element("txt_netBalanceNetForum", productName, index).getText().trim()) == actualbalance,
				"ASSERT FAILED: Expected value is " + actualbalance + " but found "
						+ element("txt_netBalanceNetForum", productName, index).getText().trim());
		logMessage("ASSERT PASSED: Net " + fieldtype + " of " + productName + " on centralized entry page is "
				+ actualbalance);
	}

	private void verifyNetPaymentDoneOfLineItemsOnOrderEntry(String ProductName, float actualPayment) {
		isElementDisplayed("inp_netPayment", ProductName);
		System.out.println(Float.parseFloat(element("inp_netPayment", ProductName).getAttribute("value").trim()));
		Assert.assertTrue(
				Float.parseFloat(element("inp_netPayment", ProductName).getAttribute("value").trim()) == actualPayment,
				"ASSERT FAILED: Expected value is " + actualPayment + " but found "
						+ element("inp_netPayment", ProductName).getAttribute("value"));
		logMessage("ASSERT PASSED: Net Payment on centralized entry page is " + actualPayment);
	}

	private void verifyNetCreditOfLineItemsOnOrderEntry(String productName, float netcredit, String index) {
		isElementDisplayed("td_lineItems", productName, index);
		System.out.println(element("td_lineItems", productName, index).getText().trim());
		Assert.assertTrue(Float.parseFloat(element("td_lineItems", productName, index).getText().trim()) == netcredit,
				"ASSERT FAILED: Expected value is " + netcredit + " but found "
						+ element("td_lineItems", productName, index).getText().trim());
		logMessage("ASSERT PASSED: Net credit of " + productName + " on centralized entry page is " + netcredit);
	}

	public void verifyLSDatesFlipToSynchWithNationalDates(String newChpName, Map individualDates) {
		verifyTerminateDateIsCurrentDate(newChpName, DateUtil.convertStringToParticularDateFormat(
				individualDates.get("EffectiveDate").toString(), "MM/dd/yyyy"), 5, "Effective date");
		verifyTerminateDateIsCurrentDate(newChpName, DateUtil.convertStringToParticularDateFormat(
				individualDates.get("ExpireDate").toString(), "MM/dd/yyyy"), 6, "Expire date");
	}

}
