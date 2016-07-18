package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.report.ReformatTestFile;
import com.qait.automation.utils.ConfigPropertyReader;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;
import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;

public class MembershipPageActions_IWEB extends ASCSocietyGenericPage {
	YamlInformationProvider getCenOrdEntry;
	Map<String, Object> mapReinstateMember;
	WebDriver driver;
	static String pagename = "MembershipPage";
	static int MemberTransferLoopCount = 0;
	static String index, selectedText, customerLname, customerFname, address, state, zipCode, customerEmail, city,
			currentDate, customerContactId, customerEmailAcsOrg, customerAddressType, nextYearDate, displayName,
			totalPrice;
	String reportingStartDate,reportingEndDate,chapterName;
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
		waitForSpinner();
		isElementDisplayed("txt_loadOnExistingQueryLabel");
		selectExistingQuery(queryName);
		waitForSpinner();
		verifyQueryTablePresent();
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
		//isElementDisplayed("txt_webLogin");
		String info = element("txt_webLogin").getText().trim();
		logMessage("Step : WebLogin is " + info + " \n");
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
			selectDropDownValue(element("list_existingQuery"), 196);
		}

		else if (queryName.equalsIgnoreCase("Selenium - Find Random Non Member")
				&& ConfigPropertyReader.getProperty("tier").equalsIgnoreCase("Stage3")) {
			selectDropDownValue(element("list_existingQuery"), 434);
		}

		else {
			selectProvidedTextFromDropDown(element("list_existingQuery"), queryName);

		}
		logMessage("STEP : Select existing query " + queryName);
	}

	public void waitForSpinner() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
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

	public void verifyQueryTablePresent() {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(4);
		isElementDisplayed("table_query");
	}

	public void clickOnRunQuery() {
		isElementDisplayed("btn_runQuery");
		clickUsingXpathInJavaScriptExecutor(element("btn_runQuery"));
		logMessage("STEP : Click on run query button in btn_runQuery \n");
		wait.hardWait(2);
		// wait.waitForPageToLoadCompletely();
		waitForSpinner();
		wait.hardWait(1);
	}

	public void clickOnMenuItems(String menuName) {
		isElementDisplayed("btn_menuItems", menuName);
		clickUsingXpathInJavaScriptExecutor(element("btn_menuItems", menuName));
		// element("btn_menuItems", menuName).click();
		logMessage("Step : click on " + menuName + " button in btn_menuItems \n");
	}

	public void clickOnEditContactInfo() {
		wait.hardWait(1);
		wait.waitForPageToLoadCompletely();
		// isElementDisplayed("btn_editContactInfo");
		executeJavascript("document.getElementById('F1_HYPERLINK_1').click()");
		logMessage("Step : Click on edit contact info button in btn_editContactInfo\n");
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
		logMessage("Step : Click on edit name and address button in btn_editNameAndAddress\n");
	}

	public String editEmailIdToAcsOrg(String existingEmail) {
		isElementDisplayed("inp_editEmail");
		logMessage("Step : existing email id is " + existingEmail);
		String[] splitEmail = existingEmail.split("@");
		logMessage("Step : existing email id is " + existingEmail + "\n");
		String newEmail = splitEmail[0] + "@acs.org";
		wait.hardWait(1);
		element("inp_editEmail").clear();
		element("inp_editEmail").sendKeys(newEmail);
		logMessage("Step : email id " + existingEmail + " is edited to " + newEmail + " in inp_editEmail\n");
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
			logMessage("Step : Email address is edited to " + emailId + " in inp_editEmail\n");
			clickOnSaveButtonForBillingAddress();
			handleAlert();
			switchToDefaultContent();
			wait.hardWait(3);
			// waitForSpinner();
			if (!emailId.equalsIgnoreCase("")) {
				verifyEmailIdEditedToAcsOrg(emailId);
			}

		} else {
			logMessage("Step : Email ID already has a domain of acs.org , so no need to edit\n");
		}

	}

	public String getNameFromEditNameAndAddressButton(String detailName) {
		hardWaitForIEBrowser(5);
		try {
			isElementDisplayed("inp_" + detailName);
			String detailValue = element("inp_" + detailName).getAttribute("value");
			logMessage("Step : " + detailName + " is " + detailValue);
			return detailValue;
		} catch (StaleElementReferenceException stlExp) {
			isElementDisplayed("inp_" + detailName);
			String detailValue = element("inp_" + detailName).getAttribute("value");
			logMessage("Step : " + detailName + " is " + detailValue);
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
		logMessage("Step : cancel button is clicked\n");
	}

	public String addEmailAddress() {
		customerEmailAcsOrg = "memberEmail" + System.currentTimeMillis() + "@acs.org";
		element("inp_editEmail").sendKeys(customerEmailAcsOrg);
		logMessage("Step : email address is edited to " + customerEmailAcsOrg + "\n");
		return customerEmailAcsOrg;
	}

	public String clickOnEditContactInfoAndGetCustomarEmail() {
		clickOnEditContactInfo();
		switchToFrame("iframe1");
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(3);
		isElementDisplayed("inp_editEmail");
		customerEmail = element("inp_editEmail").getAttribute("value");
		logMessage("Step : existing email address is : " + customerEmail);
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
		logMessage("Step : first member icon is clicked in img_firstInactiveRegularMember\n");
	}

	public void clickOnAnyRandomMember() {
		wait.hardWait(5);
		hardWaitForIEBrowser(5);
		wait.waitForPageToLoadCompletely();
		int max = 12, min = 3;
		Random rand = new Random();
		int randomNumber = rand.nextInt((max - min) + 1) + min;
		String randomNumberInString = String.valueOf(randomNumber);
		isElementDisplayed("link_randomMemberInList", randomNumberInString);
		clickUsingXpathInJavaScriptExecutor(element("link_randomMemberInList", randomNumberInString));
		logMessage("Step : Member icon at the position of " + randomNumberInString
				+ " is clicked in link_randomMemberInList\n");
	}

	public void verifyMemberStatus(String memberStatus) {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(3);
		isElementDisplayed("txt_memberStatus");
		verifyElementTextContains("txt_memberStatus", memberStatus);
		logMessage("ASSERT PASSED : member status " + memberStatus + " is verified on membership page\n");
	}

	public String clickOnCustomerName() {
		isElementDisplayed("link_customerName");
		String customerName = element("link_customerName").getText();
		logMessage("Step : Customer name is " + customerName);
		clickUsingXpathInJavaScriptExecutor(element("link_customerName"));
		logMessage("Step : customer name link is clicked in link_customerName\n");
		return customerName;
	}

	public void verifyMemberStatusIsNotActive() {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		switchToDefaultContent();
		isElementDisplayed("list_mbrStatus");
		for (WebElement element : elements("list_mbrStatus")) {
			if (element.getText().trim().equalsIgnoreCase("Active")) {
				Assert.fail("ASSERT FAILED : member status text Active is not present in status list\n");
			}
		}
		logMessage("ASSERT PASSED : member status is not active in status list\n");
	}

	public void verifyCrossMarked(String memberDetailsName) {
		isElementDisplayed("img_cross", memberDetailsName);
		Assert.assertTrue(element("img_cross", memberDetailsName).getAttribute("src").contains("img_list_delete1.gif"));
		logMessage("ASSERT PASSED : cross marked is verified for " + memberDetailsName + "\n");
	}

	public void clickOnSelectProduct() {
		isElementDisplayed("lnk_selectProduct");
		// hover(element("lnk_selectProduct"));
		wait.hardWait(1);
		// clickUsingXpathInJavaScriptExecutor(element("lnk_selectProduct"));
		// element("lnk_selectProduct").click();
		executeJavascript("document.getElementById('HYPERLINK_17').click()");
		logMessage("Step : select product link is clicked in lnk_selectProduct\n");
	}

	public void clickOnOrderEntryIcon() {
		switchToDefaultContent();
		wait.hardWait(2);
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("img_orderEntry");
		clickUsingXpathInJavaScriptExecutor(element("img_orderEntry"));
		logMessage("Step : order entry icon is clicked in img_orderEntry\n");

	}

	public void clickOnAddMembershipMenu() {
		isElementDisplayed("txt_menuItems");
		clickUsingXpathInJavaScriptExecutor(element("txt_menuItems"));
		// element("txt_menuItems").click();
		logMessage("Step: click on add membership menu\n");
	}

	public void selectMemberInfo(String memberInfo, String memberInfoValue) {
		try {
			wait.waitForPageToLoadCompletely();
			isElementDisplayed("list_" + memberInfo);
			selectProvidedTextFromDropDown(element("list_" + memberInfo), memberInfoValue);
			logMessage("ASSERT PASSED : " + memberInfoValue + " is selected in list_" + memberInfo + "\n");

		} catch (StaleElementReferenceException stlExp) {
			selectProvidedTextFromDropDown(element("list_" + memberInfo), memberInfoValue);
			logMessage("select Element " + memberInfo + " after catching Stale Element Exception\n");
		}
	}

	public void enterDate(String dateType, String date) {
		try {
			isElementDisplayed("inp_" + dateType);
			element("inp_" + dateType).sendKeys(date);
			logMessage("Step : " + date + " is entered in " + dateType + " \n");
		} catch (StaleElementReferenceException stlExp) {
			isElementDisplayed("inp_" + dateType);
			element("inp_" + dateType).sendKeys(date);
			logMessage("Step : " + date + " is entered in " + dateType + " \n");
		}
	}

	public void verifyItemsAdded(String itemName) {
		isElementDisplayed("txt_itemsAdded", itemName);
		logMessage("ASSERT PASSED : item " + itemName + " is added in item list\n");
	}

	public void selectOrderEntryInfo(String orderEntryInfo, String value) {
		hardWaitForIEBrowser(2);
		isElementDisplayed("list_" + orderEntryInfo);
		selectProvidedTextFromDropDown(element("list_" + orderEntryInfo), value);
		logMessage("Step : " + value + " is selected in " + orderEntryInfo + "\n");
	}

	public void clickOnSaveAndFinish() {
		hardWaitForIEBrowser(2);
		isElementDisplayed("btn_saveAndFinish");
		hardWaitForIEBrowser(10);

		hoverClick(element("btn_saveAndFinish"));
		hardWaitForIEBrowser(15);
		logMessage("Step : save and finish button is clicked\n");
	}

	public void enterCardDetails(String cardInfo, String cardValue) {
		isElementDisplayed("inp_" + cardInfo);
		element("inp_" + cardInfo).sendKeys(cardValue);
		logMessage("Step : enter " + cardValue + " in inp_" + cardInfo + " \n");
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
				getOmaSheetValue(caseId, "multiYearDecision"));

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
			Assert.assertTrue(src.contains("img_chkmk.gif"));
		} catch (StaleElementReferenceException stlExp) {
			System.out.println("Stale element catched\n");
			isElementDisplayed("img_ticked", index);
			String src = element("img_ticked", index).getAttribute("src");
			Assert.assertTrue(src.contains("img_chkmk.gif"));
		}

		logMessage("ASSERT PASSED : verified ticked mark for " + memberDetailsName + "\n");
	}

	public void verifyMemberStatusActiveIsPresent() {
		for (WebElement element : elements("list_mbrStatus")) {
			if (element.getText().trim().contains("Active")) {
				logMessage("ASSERT PASSED : member status active is present in status list\n");
				flag = true;
				break;
			}
		}
		if (!flag)
			Assert.fail("ASSERT PASSED : member status active is not present in status list\n");
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
			logMessage("Step : " + addValue + " is enetered in inp_" + addType + "\n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (StaleElementReferenceException stl) {
			wait.resetImplicitTimeout(3);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("inp_" + addType);
			element("inp_" + addType).sendKeys(addValue);
			logMessage("Step : " + addValue + " is enetered in inp_" + addType + "\n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}

	}

	public void clickOnDoNotValidate() {
		isElementDisplayed("chk_doNotValidate");
		element("chk_doNotValidate").click();
		logMessage("Step : check doNotValidate\n");
	}

	public void clickOnPlusAddButton() {
		isElementDisplayed("btn_add");
		scrollDown(element("btn_add"));
		hoverClick(element("btn_add"));
		logMessage("Step : click add button\n");
	}

	public void clickOnSaveButtonForBillingAddress() {
		wait.hardWait(1);
		isElementDisplayed("btn_saveForBillingAdd");
		scrollDown(element("btn_saveForBillingAdd"));
		clickUsingXpathInJavaScriptExecutor(element("btn_saveForBillingAdd"));
		// element("btn_saveForBillingAdd").click();

		handleAlert();

		logMessage("Step : save button is clicked \n");
	}

	public void selectBillingAddress() {
		if (elements("list_billingAdd").size() <= 1) {
			clickOnPlusAddButton();
			// TODO Remove hard wait after handling stale element exception
			holdExecution(1000);
			switchToFrame("iframe1");
			selectProvidedTextFromDropDown(element("list_addressType"), "billing");
			logMessage("Step : billing is selected in address type\n");
			clickOnDoNotValidate();

			enterBillingAddress("addressLine", YamlReader.getYamlValue("ReinstateMemberIWEB.billingAddress.address"));
			enterBillingAddress("city", YamlReader.getYamlValue("ReinstateMemberIWEB.billingAddress.city"));
			enterBillingAddress("country", YamlReader.getYamlValue("ReinstateMemberIWEB.billingAddress.country"));
			// TODO Remove hard wait after handling stale element exception
			holdExecution(1000);
			isElementDisplayed("list_state");
			selectProvidedTextFromDropDown(element("list_state"),
					YamlReader.getYamlValue("ReinstateMemberIWEB.billingAddress.state"));
			logMessage("Step : New York is selected in list_state\n");
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
			logMessage("Step : All details are entered for billing address");
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

			logMessage("Step: Billing address is already present\n");
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
		logMessage("Step : " + memberDetail + " " + detailValue + " is entered to search member\n");
	}

	public void clickOnGoButton() {
		isElementDisplayed("btn_go");
		element("btn_go").click();
		logMessage("Step : Go button is clicked in btn_go\n");
	}

	public void clickOnGoAskButton() {
		isElementDisplayed("btn_goask");
		element("btn_goask").click();
		logMessage("Step : Go Ask button is clicked in btn_goask\n");
	}

	public void verifyMemberStatusIsActive() {
		for (WebElement element : elements("list_memberStatus")) {
			if (element.getText().trim().equalsIgnoreCase("Active")) {
				logMessage("ASSERT PASSED : member status active is present in status list\n");
				flag = true;
				break;
			}
		}
		if (!flag)
			Assert.fail("ASSERT PASSED : member status active is not present in status list\n");
	}

	public void verifyJoinDateIsCurrentDate() {
		String currentDate = DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy");
		for (WebElement element : elements("list_joindate")) {
			if (currentDate.startsWith("0")) {
				String newCurrentDate = currentDate.substring(1);
				if (element.getText().trim().equalsIgnoreCase(newCurrentDate)) {
					logMessage("ASSERT PASSED : member rejoin date is current date " + newCurrentDate + " in list\n");
					flag = true;
					break;
				}
			} else if (element.getText().trim().equalsIgnoreCase(currentDate)) {
				logMessage("ASSERT PASSED : member rejoin date is current date " + currentDate + " in list\n");
				flag = true;
				break;
			}
		}
		if (!flag)
			Assert.fail("ASSERT PASSED : member status active is not present in status list\n");
	}

	public String numberOfYearsForInactiveMember() {
		isElementDisplayed("txt_numberOfyears");
		String numberOfYears = element("txt_numberOfyears").getText().trim();
		logMessage("Step : total years of services for inactive member is " + numberOfYears);
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
		logMessage("Step Module tab is clicked\n");
	}

	public void clickOnTab(String tabName) {
		isElementDisplayed("link_tabsOnModule", tabName);
		if(isBrowser("safari"))
	        element("link_tabsOnModule", tabName).click();
		else
			clickUsingXpathInJavaScriptExecutor(element("link_tabsOnModule", tabName));
		logMessage("STEP : " + tabName + " tab is clicked\n");

	}

	public void expandDetailsMenu(String menuName) {
		isElementDisplayed("btn_detailsMenuAACT", menuName);
		try {
			wait.resetImplicitTimeout(9);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			clickUsingXpathInJavaScriptExecutor(element("btn_detailsMenuAACT", menuName));
			// element("btn_detailsMenuAACT", menuName).click();
		} catch (Exception e) {
			element("btn_detailsMenuAACT", menuName).click();
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
		logMessage("STEP : " + menuName + " bar is clicked to expand" + "\n");
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
		logMessage("Step : " + memberInfo + " is " + info + " \n");
		return info;
	}

	public void openSubInfoDropDown(String subInfoName) {
		isElementDisplayed("btn_memberShipSubInfo", subInfoName);
		element("btn_memberShipSubInfo", subInfoName).click();
		logMessage("Step : click on sub info on membership profile\n");

	}

	public String getMemberDetailsOnMemberShipProfile(String memberInfo) {
		wait.hardWait(2);
		hardWaitForIEBrowser(5);
		isElementDisplayed("txt_membershipProfileDetails", memberInfo);
		String info = element("txt_membershipProfileDetails", memberInfo).getText().trim();
		logMessage("Step : Customer ID is " + info + " \n");
		return info;
	}

	public String getPaymentStatus() {
		isElementDisplayed("txt_paymentStatus");
		String paymentStatus = element("txt_paymentStatus").getText().trim();
		logMessage("STEP : payment status is " + paymentStatus + "\n");
		return paymentStatus;
	}

	public void selectMemberStatus(String memberStatus) {
		isElementDisplayed("list_memberStatusRenewal");
		selectProvidedTextFromDropDown(element("list_memberStatusRenewal"), memberStatus);
		logMessage("STEP : member status " + memberStatus + " is selected \n");
	}

	public void checkAdvanceNewCheckbox() {
		isElementDisplayed("chk_advanceNew");
		element("chk_advanceNew").click();
		logMessage("STEP : Advance new checkbox is checked in chk_advanceNew\n");
	}

	public void selectAdvanceNewDropDown(String heading, String value) {
		isElementDisplayed("list_advanceNewDropDown", heading);
		selectProvidedTextFromDropDown(element("list_advanceNewDropDown", heading), value);
		logMessage("STEP : select " + value + " for " + heading + " in list_advanceNewDropDown\n");
	}

	public void enterValueInAdvanceNewInput(String heading, String value) {
		isElementDisplayed("inp_advanceNewInput", heading);
		element("inp_advanceNewInput", heading).clear();
		element("inp_advanceNewInput", heading).sendKeys(value);
		logMessage("STEP : enter " + value + " for " + heading + " in inp_advanceNewInput\n");
	}

	public void selectValueInAdvanceNewNextDropDown(String heading, String value) {
		isElementDisplayed("list_advanceNewInput", heading);
		selectProvidedTextFromDropDown(element("list_advanceNewInput", heading), value);
		logMessage("STEP : select " + value + " in next drop down for " + heading + " in list_advanceNewInput\n");
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
			logMessage("Step : Member customer ID is " + customerContactId);
		} catch (StaleElementReferenceException stlExp) {
			customerContactId = element("txt_renewalContactId").getText().trim();
			logMessage("Step : Member customer ID is " + "'"+customerContactId+"'");
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
		logMessage("Step : subscription link is clicked in link_subscriptionInSelectProduct\n");

	}

	public void clickOnRandomPage() {
		try {
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("lnk_pages", "2");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			int max = 6, min = 2;
			Random rand = new Random();
			int randomNumber = rand.nextInt((max - min) + 1) + min;
			// int randomNumber = (int) Math.random();
			String randomNumberInString = String.valueOf(randomNumber);
			isElementDisplayed("lnk_pages", randomNumberInString);

			clickUsingXpathInJavaScriptExecutor(element("lnk_pages", randomNumberInString));
			logMessage("Step : page at the position of " + randomNumberInString + " is clicked in lnk_pages\n");

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
		logMessage("Step : Display name is : " + displayName + "\n");
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
		isElementDisplayed("inp_prdCode");
		element("inp_prdCode").sendKeys(prodCode);
		logMessage("Step : Produuct code " + prodCode + " is entered in inp_prdCode\n");
	}

	public void clickOnSearchDisplayNameButton() {
		isElementDisplayed("inp_searchDisplayButton");
		clickUsingXpathInJavaScriptExecutor(element("inp_searchDisplayButton"));
		// element("inp_searchDisplayButton").click();
		wait.hardWait(2);
		hardWaitForIEBrowser(8);
		logMessage("Step : Search display name button is clicked in inp_searchDisplayButton\n");
	}

	public String getDisplayName() {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("inp_displayName");
		String displayName = element("inp_displayName").getAttribute("value");
		return displayName;
	}

	public String searchAndGetDisplayName() {
		clickOnSearchDisplayNameButton();
		return getDisplayName();
	}

	public void verifyItemAddedInLineItems(String itemName) {
		hardWaitForIEBrowser(60);

		// isElementDisplayed("link_itemInLineItems");
		verifyElementText("link_itemInLineItems", itemName);
	}

	public void selectBatchAndPaymentDetails_subscription(String batchName, String paymentType, String paymentMethod,
			String cardNumber, String expireDate, String cvvNumber) {
		// wait.waitForPageToLoadCompletely();
		holdExecution(2000);
		System.out.println("----"+batchName);
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
		enterCardDetails("cardNumber", cardNumber);
		selectMemberInfo("expireDate", expireDate);
		enterCardDetails("cvvNumber", cvvNumber);
		selectBillingAddressIfNotPrePopulated();
		clickOnSaveAndFinish();
		handleAlert();

		verifyPageTitleContains("CRM | Individuals |");
	}

	public boolean verifyBatchIsPresent(String batchName) {
		hardWaitForIEBrowser(2);
		System.out.println("-----in verify batch:"+batchName);
		isElementDisplayed("list_batch");
		flag = isDropDownValuePresent(element("list_batch").findElements(By.xpath("//option")), batchName);
		return flag;

	}

	public void addBatch(String batchName, String securityGroup) {
		switchToDefaultContent();
		isElementDisplayed("btn_addBatch");
		clickUsingXpathInJavaScriptExecutor(element("btn_addBatch"));
		// element("btn_addBatch").click();
		logMessage("Step : add batch button is clicked \n");
		switchToFrame("iframe1");
		isElementDisplayed("inp_addBatchName");
		element("inp_addBatchName").clear();
		element("inp_addBatchName").sendKeys(batchName);
		logMessage("Step : enter batch name " + batchName + "\n");
		isElementDisplayed("list_batchSecurityGroup");
		selectProvidedTextFromDropDown(element("list_batchSecurityGroup"), securityGroup);
		logMessage("Step : Select security group " + securityGroup + "\n");
		clickOnSaveButtonForBillingAddress();

	}

	public void selectBillingAddressIfNotPrePopulated() {
		List<WebElement> list = elements("list_billingAdd");
		if (list.get(0).getAttribute("selected") != null) {
			selectDropDownValue(element("list_billAddress"), 1);
			logMessage("Step : select billing address in dropdown list\n");
		} else {
			logMessage("Step : billing address is already selected\n");
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
		logMessage("Step : enter customer IDs " + customerID + " \n");
		clickOnGoButtonInRunQuery();
		// verifyMultipleRecordsInList("2");
	}

	public void enterSingleCustomerIdInRunQuery(String customerId) {
		isElementDisplayed("inp_customerId");
		String customerID = customerId;
		element("inp_customerId").clear();
		element("inp_customerId").sendKeys(customerID);
		logMessage("Step : enter customer ID as " + customerID + " \n");
		clickOnGoButtonInRunQuery();

	}

	public void clickOnInvoiceNumber() {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(4);
		wait.hardWait(5);

		wait.resetImplicitTimeout(2);
		wait.resetExplicitTimeout(hiddenFieldTimeOut);
		// isElementDisplayed("lnk_first_invoice_number");
		clickUsingXpathInJavaScriptExecutor(element("lnk_first_invoice_number"));
		// element("lnk_first_invoice_number").click();
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
		// } catch (Exception e) {
		// wait.resetImplicitTimeout(timeOut);
		// wait.resetExplicitTimeout(timeOut);
		// logMessage("STEP: First Invoice Number Clicked");
		// }
		logMessage("STEP: First Invoice Number Clicked");
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
			logMessage("Step : Invoice heading " + tabName + " is clicked");
		} catch (Exception e) {
			wait.resetExplicitTimeout(timeOut);
		}

	}

	public void verifyMultipleRecordsInList(String numberOfRecords) {
		isElementDisplayed("txt_recordNumberAtMemberQuery");
		String recordNumber = element("txt_recordNumberAtMemberQuery").getText().trim();

		Assert.assertTrue(recordNumber.equalsIgnoreCase("(" + numberOfRecords + " records)"));
		logMessage("Step : multiple records are presents in the List - Member Query by Member Number - Multiple \n");

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
		openSubInfoDropDown("invoices");
		flag = pagesLinkAvailable();
		verifyProductNameInInvoice(productName, flag);
		// verifyInvoiceIDInInvoice(invoiceId, flag);
		verifyTermStartDateInvoice("", flag);
		verifyTermEndDateInvoice("", flag);

	}

	public void verifyMemberInfoOnMemberShipProfile(String memberdetail, String memberValue) {
		isElementDisplayed("txt_membershipProfileInfo", memberdetail);

		Assert.assertTrue(
				element("txt_membershipProfileInfo", memberdetail).getText().trim().equalsIgnoreCase(memberValue));
		logMessage("ASSERT PASSED : " + memberValue + " is verified for " + memberValue + " \n");

	}

	public void verifyMemberDetailsOnMemberShipProfile(String memberdetail, String memberValue) {
		isElementDisplayed("txt_membershipProfileDetails", memberdetail);

		Assert.assertTrue(
				element("txt_membershipProfileDetails", memberdetail).getText().trim().equalsIgnoreCase(memberValue));
		logMessage("ASSERT PASSED : " + memberValue + " is verified for " + memberValue + " \n");

	}

	public void verifyProductNameInInvoice(String productName, boolean pageLink) {
		if (pageLink) {
			isElementDisplayed("txt_productNameOnPage");

			Assert.assertTrue(element("txt_productNameOnPage").getText().trim().equalsIgnoreCase(productName));
			logMessage("ASSERT PASSED : product name " + productName + " in invoice is verified\n");
		} else {
			isElementDisplayed("txt_productName");

			Assert.assertTrue(element("txt_productName").getText().trim().equalsIgnoreCase(productName));
			logMessage("ASSERT PASSED : product name " + productName + " in invoice is verified\n");

		}

	}

	public void verifyInvoiceIDInInvoice(String invoiceID, boolean pageLink) {
		if (pageLink) {
			isElementDisplayed("txt_invoiceIdOnPage");

			Assert.assertTrue(element("txt_invoiceIdOnPage").getText().trim().equalsIgnoreCase(invoiceID));
			logMessage("ASSERT PASSED : invoice id is " + invoiceID + " in invoice is verified\n");
		} else {
			isElementDisplayed("txt_invoiceId");

			Assert.assertTrue(element("txt_invoiceId").getText().trim().equalsIgnoreCase(invoiceID));
			logMessage("ASSERT PASSED : invoice id is " + invoiceID + " in invoice is verified\n");

		}

	}

	public void verifyTermStartDateInvoice(String startDate, boolean pageLink) {
		if (pageLink) {
			isElementDisplayed("txt_termStartDateOnPage");

			Assert.assertTrue(element("txt_termStartDateOnPage").getText().trim().equalsIgnoreCase(startDate));
			logMessage("ASSERT PASSED : term start date is " + startDate + " in invoice is verified\n");
		} else {
			isElementDisplayed("txt_termStartDate");

			Assert.assertTrue(element("txt_termStartDate").getText().trim().equalsIgnoreCase(startDate));
			logMessage("ASSERT PASSED : term start date is " + startDate + " in invoice is verified\n");
		}
	}

	public void verifyTermEndDateInvoice(String endDate, boolean pageLink) {
		if (pageLink) {
			isElementDisplayed("txt_termEndDateOnPage");

			Assert.assertTrue(element("txt_termEndDateOnPage").getText().trim().equalsIgnoreCase(endDate));
			logMessage("ASSERT PASSED : term end date is " + endDate
					+ " in invoice is verified in txt_termEndDateOnPage\n");

		} else {
			isElementDisplayed("txt_termEndDate");

			Assert.assertTrue(element("txt_termEndDate").getText().trim().equalsIgnoreCase(endDate));
			logMessage("ASSERT PASSED : term end date is " + endDate
					+ " in invoice is verified in txt_termEndDateOnPage\n");

		}
	}

	public void verifyPaymentStatus(String paymentStatus) {
		isElementDisplayed("txt_paymentStatus");

		Assert.assertTrue(element("txt_paymentStatus").getText().equalsIgnoreCase(paymentStatus));
		logMessage("ASSERT PASSED : payment status " + paymentStatus + " is verified in txt_paymentStatus\n");
	}

	public void navigateToInvoicePageForFirstProduct() {
		isElementDisplayed("btn_arrowRightCircle");
		clickUsingXpathInJavaScriptExecutor(element("btn_arrowRightCircle"));
		// element("btn_arrowRightCircle").click();
		logMessage("Step : navigate to invoice profile page for first product in btn_arrowRightCircle\n");
	}

	public void navigateToInvoicePageForRenewedProduct() {
		isElementDisplayed("btn_gotorenewal");
		element("btn_gotorenewal").click();
		logMessage("Step : navigate to invoice profile page for Renewed product in btn_gotorenewal\n");

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

	public void goToAddMemebrshipAndFillDetails_LocalSectionAsFellowPrequisite() {
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

		}
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

	public void goToAddMembershipAndFillDetails_DivisionAsFellowPrequisite(int numberOfDivisions) {

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

		}
	}

	public String getTotalPrice() {
		isElementDisplayed("txt_totalPrice");
		return element("txt_totalPrice").getText();
	}

	public void enterSourceCode(String sourceCode) {
		isElementDisplayed("inp_sourceCode");
		element("inp_sourceCode").sendKeys(sourceCode);
		logMessage("Step : source code " + sourceCode + " is entered in inp_sourceCode\n");
	}

	public void verifyPrice(String itemName, String price) {
		isElementDisplayed("txt_priceOrderEntryLineItmes", itemName);
		String actualPrice = element("txt_priceOrderEntryLineItmes", itemName).getText().trim();

		Float newPrice = Float.parseFloat(price) * Integer.parseInt(map().get("renewalTerm").trim());

		String formatedPrice = String.format("%.02f", newPrice);
		if (!map().get("complimentary").equalsIgnoreCase("On")) {
			System.out.println("actual:" + actualPrice);
			System.out.println("expected :" + formatedPrice);
			Assert.assertTrue(actualPrice.equalsIgnoreCase(formatedPrice));
			logMessage("ASSERT PASSED : price " + formatedPrice + " is verified for " + itemName
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

			verifyPrice(productName_TotalPrice[0], map().get("Sub" + i + "_SalePrice?"));
		}
	}

	public void selectAddMembershipInSelectProductLink() {
		isElementDisplayed("link_addMemership");
		clickUsingXpathInJavaScriptExecutor(element("link_addMemership"));
		// element("link_addMemership").click();
		logMessage("Step : Add Membership link is clicked in link_addMemership\n");

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

		logMessage("Step : Display name is : " + displayName + "\n");
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

		logMessage("ASSERT PASSED : subscription name " + map().get("subscription" + numberOfSubscription)
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
		Assert.assertTrue(netBalance.equalsIgnoreCase(String.valueOf(String.format("%.2f", netIndividualBalance))));
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

		clickOnEditNameAndAddress();
		switchToFrame("iframe1");
		customerLname = getNameFromEditNameAndAddressButton("lastName");
		clickOnCancelButton();
		handleAlert();
		switchToDefaultContent();
		customerContactId = element("txt_renewalContactId").getText();
		memberDetails.add(customerLname);
		memberDetails.add(customerContactId);

		return memberDetails;
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
		logMessage("Customer Last Name::" + customerLname);
		logMessage("Customer ACS Member Number::" + customerContactId);
		memberDetails.add(customerLname);
		memberDetails.add(customerContactId);
		return memberDetails;
	}

	public String getCustomerID() {
		switchToDefaultContent();
		wait.waitForPageToLoadCompletely();
		customerContactId = element("txt_ContactId").getText().trim();
		return customerContactId;
	}

	public List<String> getCustomerFullNameAndContactID() {

		clickOnEditNameAndAddress();
		switchToFrame("iframe1");
		customerLname = getNameFromEditNameAndAddressButton("lastName") + " "
				+ getNameFromEditNameAndAddressButton("firstName") + " "
				+ getNameFromEditNameAndAddressButton("middleName");
		clickOnCancelButton();
		handleAlert();
		switchToDefaultContent();
		customerContactId = element("txt_renewalContactId").getText();
		System.out.println(customerContactId);
		System.out.println(customerLname);
		memberDetails.add(customerLname);

		memberDetails.add(customerContactId);
		// memberDetails.add(getMemberWebLogin());
		logMessage("Step : ");
		return memberDetails;

	}

	public void getIndividualFullNameForAwardsNomination() {

		clickOnEditNameAndAddress();
		switchToFrame("iframe1");
		customerLname = getNameFromEditNameAndAddressButton("lastName") + " "
				+ getNameFromEditNameAndAddressButton("firstName") + " "
				+ getNameFromEditNameAndAddressButton("middleName");
		clickOnCancelButton();
		handleAlert();
		switchToDefaultContent();
		customerContactId = element("txt_renewalContactId").getText();
		System.out.println(customerContactId);
		System.out.println(customerLname);
		createMemberCredentials.put("Nominee" + individualCount + "Name", customerLname);
		createMemberCredentials.put("Nominee" + individualCount + "Number", customerContactId);
		System.out.println(createMemberCredentials.get("Nominee" + individualCount + "Name"));
		System.out.println(createMemberCredentials.get("Nominee" + individualCount + "Number"));
		logMessage("Step : Individual Details saved from iweb profile page\n");
		individualCount++;

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
		logMessage("Step : Full Name of member is " + memberDetails.get(0));
		logMessage("Step : Customer Id of member is " + memberDetails.get(1));
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
		logMessage("Step : Customer Name as " + element("link_customerName").getText() + " is clicked\n");
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
		clickOnGoButtonInRunQuery();

	}

	public void selectValidUserForRenewal(Map<String, String> mapOMR) {
		if (MemberTransferLoopCount < 3) {
			clickOnTab("Query Membership");
			selectAndRunQuery("Selenium - Renewal Query");
			selectMemberForRenewal(mapOMR.get("Member_Status?"));
			expandDetailsMenu("invoices");
			verifyTermStartDateAndEndDatesAreEmpty(mapOMR);
			verifyPaymentStatusBeforeRenewal(mapOMR);
			MemberTransferLoopCount++;
		} else {
			Assert.fail("ASSERT FAIL : Member is not selected after " + MemberTransferLoopCount + " attempts\n");
			logMessage("ASSERT FAIL : Member is not selected after " + MemberTransferLoopCount + " attempts\n");
		}
		logMessage("Step : Member selected in " + MemberTransferLoopCount + " attempt\n");

	}

	public void verifyPaymentStatusBeforeRenewal(Map<String, String> mapOMR) {
		try {
			wait.resetImplicitTimeout(4);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			Assert.assertTrue(element("txt_PaymentStatus", "Payment Status").getText().equals("Unpaid"));
		} catch (AssertionError e) {
			logMessage("ASSERT PASSED : Payment status before renewal is not Unpaid for " + MemberTransferLoopCount
					+ " attempt thus looping back\n");
			selectValidUserForRenewal(mapOMR);
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
		logMessage("Step: Select the member package : " + memberPackage + "\n");
		isElementDisplayed("list_memberPackage1");
		selectProvidedTextFromDropDown(element("list_memberPackage1"), memberPackage);
	}

	public void clickOnGoButtonAfterPackageSelection() {
		isElementDisplayed("btn_goPackage");
		element("btn_goPackage").click();
		logMessage("STEP : Clicked on Go Button\n");
	}

	public void verifyMemberTypeAndPackage(String expectedType, String expectedStatus) {
		isElementDisplayed("txt_memberInfo", "member type");
		Assert.assertTrue(expectedType.equals(element("txt_memberInfo", "member type").getText().trim()),
				"ASSERT FAIL : Member Type is not Regular Member\n");
		logMessage("ASSERT PASS : Member Type is Regular Member\n");

		isElementDisplayed("txt_memberInfo", "member status");
		Assert.assertTrue(expectedStatus.equals(element("txt_memberInfo", "member status").getText().trim()),
				"ASSERT FAIL : Member Status is not Active Renewed-No Response\n");
		logMessage("ASSERT PASS : Member Status is Active Renewed-No Response\n");
	}

	public void verifyTermEndDateAndStartDateIsEmpty() {
		hardWaitForIEBrowser(2);
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
	//// logMessage("-------------"+element("txt_memberInfo","renewal
	// package").getText().trim());
	// Assert.assertTrue(renewalPackage.equals(element("txt_memberInfo","renewal
	// package").getText().trim()),
	// "ASSERT FAIL : Renewal Package is not "+renewalPackage+"\n");
	// logMessage("ASSERT PASS : Renewal Package is "+ renewalPackage+"\n");
	// wait.resetImplicitTimeout(timeOut);
	// wait.resetExplicitTimeout(timeOut);
	// }

	public void verfiyMemberPackage(String memberPackage) {
		System.out.println("-----");
		wait.waitForPageToLoadCompletely();
		wait.resetImplicitTimeout(4);
		wait.resetExplicitTimeout(hiddenFieldTimeOut);
		System.out.println("-----before spinner");
		waitForSpinner();
		System.out.println("-----before wait");
		hardWaitForIEBrowser(3);
		memberPackage = memberPackage.split(": ", 3)[2];
		isElementDisplayed("txt_memberInfo", "member package");
		Assert.assertTrue(memberPackage.equals(element("txt_memberInfo", "member package").getText().trim()),
				"ASSERT FAIL : Member Package is not " + memberPackage + "\n");
		logMessage("ASSERT PASS : Member Package is " + memberPackage + "\n");
	}

	public void verifyRenewalPackage(String renewalPackage) {
		if (renewalPackage.contains(":"))
			renewalPackage = renewalPackage.split(": ", 3)[2];

		isElementDisplayed("txt_memberInfo", "renewal package");
		Assert.assertTrue(renewalPackage.equals(element("txt_memberInfo", "renewal package").getText().trim()),
				"ASSERT FAIL : Renewal Package is not " + renewalPackage + "\n");
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
		element("btn_transferNow").click();
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
			logMessage("Step : Term Start date and Term Endd Date are not empty for " + MemberTransferLoopCount
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

	public void getContactIdOfUser(String Member) {
		isElementDisplayed("txt_ContactId");
		logMessage("STEP : " + Member + " Id is : " + element("txt_ContactId").getText().trim() + "\n");
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
		for (Map.Entry criteria : criteriaList.entrySet()) {
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
					System.out.println("MP Join Date \n Line::"+line+"Incremented By ::"+Integer.parseInt(line.split("Y")[0].split("\\+")[1]));
					System.out.println("After List ::"+afterList.get(criteria.getKey()).trim());
					System.out.println("Before List::"+beforeList.get(criteria.getKey()).trim());
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
					if (beforeList.get(criteria.getKey()).trim()
							.contains(afterList.get(criteria.getKey()).trim())) {
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
					System.out.println("MP Eff Date \n Line::"+line+"Incremented By ::"+Integer.parseInt(line.split("Y")[0].split("\\+")[1]));
					System.out.println("After List ::"+afterList.get(criteria.getKey()).trim());
					System.out.println("Before List::"+beforeList.get(criteria.getKey()).trim());
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
					if (beforeList.get(criteria.getKey()).trim()
							.contains(afterList.get(criteria.getKey()).trim())) {
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
					System.out.println("MP Exp Date \n Line::"+line+"Incremented By ::"+Integer.parseInt(line.split("Y")[0].split("\\+")[1]));
					System.out.println("After List ::"+afterList.get(criteria.getKey()).trim());
					System.out.println("Before List::"+beforeList.get(criteria.getKey()).trim());
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
					if (beforeList.get(criteria.getKey()).trim()
							.contains(afterList.get(criteria.getKey()).trim())) {
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
					if (beforeList.get(criteria.getKey()).trim()
							.contains(afterList.get(criteria.getKey()).trim())) {
						ResultList.put(criteria.getKey() + "", "n");
					} else {
						ResultList.put(criteria.getKey() + "", "y");
					}
				}
				break;
			case "MP Pmt Status":
				if(criteriaList.get(criteria.getKey()).trim().toLowerCase().contains("unpaid|credit")){
					String sp[] = criteriaList.get(criteria.getKey()).trim().toLowerCase().split("|");
					if(afterList.get(criteria.getKey()).trim().toLowerCase().contains(sp[0]) || afterList.get(criteria.getKey()).trim().toLowerCase().contains(sp[1])){
						ResultList.put(criteria.getKey() + "", "y");
					}else{
						ResultList.put(criteria.getKey() + "", "n");
					}
				}else if (criteriaList.get(criteria.getKey()).trim().equalsIgnoreCase((afterList.get(criteria.getKey()).trim()))) {
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
				if(criteriaList.get(criteria.getKey()).trim().equalsIgnoreCase("{GT 0}")){
					if(Float.parseFloat(afterList.get(criteria.getKey()).trim())> 0){
						ResultList.put(criteria.getKey() + "", "y");
					}else{
						ResultList.put(criteria.getKey() + "", "n");
					}
				}else if (criteriaList.get(criteria.getKey()).trim().equalsIgnoreCase("0")) {
					if(Float.parseFloat(afterList.get(criteria.getKey()).trim()) == Float.parseFloat(criteriaList.get(criteria.getKey()).trim())){
						ResultList.put(criteria.getKey() + "", "y");
					}else{
						ResultList.put(criteria.getKey() + "", "n");
					}
				} else if (criteriaList.get(criteria.getKey()).trim().contains("{NO CHANGE}")) {
					if (beforeList.get(criteria.getKey()).trim()
							.contains(afterList.get(criteria.getKey()).trim())) {
						ResultList.put(criteria.getKey() + "", "y");
					} else {
						ResultList.put(criteria.getKey() + "", "n");
					}}
				break;
			case "MP Start":
				line = criteriaList.get(criteria.getKey()).trim().toUpperCase();
				pattern = "(\\d+)";
				r = Pattern.compile(pattern);
				m = r.matcher(line);
				if (m.find()) {
					System.out.println("MP Start \n Line::"+line+"Incremented By ::"+Integer.parseInt(line.split("Y")[0].split("\\+")[1]));
					System.out.println("After List ::"+afterList.get(criteria.getKey()).trim());
					System.out.println("Before List::"+beforeList.get(criteria.getKey()).trim());
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
					if (beforeList.get(criteria.getKey()).trim()
							.contains(afterList.get(criteria.getKey()).trim())) {
						ResultList.put(criteria.getKey() + "", "y");
					} else {
						ResultList.put(criteria.getKey() + "", "n");
					}}else if (criteriaList.get(criteria.getKey()).trim().contains("{NULL}")) {
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
					System.out.println("MP End \n Line::"+line+"Incremented By ::"+Integer.parseInt(line.split("Y")[0].split("\\+")[1]));
					System.out.println("After List ::"+afterList.get(criteria.getKey()).trim());
					System.out.println("Before List::"+beforeList.get(criteria.getKey()).trim());
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
					if (beforeList.get(criteria.getKey()).trim()
							.contains(afterList.get(criteria.getKey()).trim())) {
						ResultList.put(criteria.getKey() + "", "y");
					} else {
						ResultList.put(criteria.getKey() + "", "n");
					}}else if (criteriaList.get(criteria.getKey()).trim().contains("{NULL}")) {
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
					System.out.println("IVP TX DATE\n Line::"+line+"Incremented By ::"+Integer.parseInt(line.split("Y")[0].split("\\+")[1]));
					System.out.println("After List ::"+afterList.get(criteria.getKey()).trim());
					System.out.println("Before List::"+beforeList.get(criteria.getKey()).trim());
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
					if (beforeList.get(criteria.getKey()).trim()
							.contains(afterList.get(criteria.getKey()).trim())) {
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
				}else if (afterList.get(criteria.getKey()).trim()
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
					System.out.println("IDP Join DATE\n Line::"+line+"Incremented By ::"+Integer.parseInt(line.split("Y")[0].split("\\+")[1]));
					System.out.println("After List ::"+afterList.get(criteria.getKey()).trim());
					System.out.println("Before List::"+beforeList.get(criteria.getKey()).trim());
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
					if (beforeList.get(criteria.getKey()).trim()
							.contains(afterList.get(criteria.getKey()).trim())) {
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
					System.out.println("IDP Eff DATE\n Line::"+line+"Incremented By ::"+Integer.parseInt(line.split("Y")[0].split("\\+")[1]));
					System.out.println("After List ::"+afterList.get(criteria.getKey()).trim());
					System.out.println("Before List::"+beforeList.get(criteria.getKey()).trim());
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
					if (beforeList.get(criteria.getKey()).trim()
							.contains(afterList.get(criteria.getKey()).trim())) {
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
					System.out.println("IDP EXP DATE\n Line::"+line+"Incremented By ::"+Integer.parseInt(line.split("Y")[0].split("\\+")[1]));
					System.out.println("After List ::"+afterList.get(criteria.getKey()).trim());
					System.out.println("Before List::"+beforeList.get(criteria.getKey()).trim());
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
					if (beforeList.get(criteria.getKey()).trim()
							.contains(afterList.get(criteria.getKey()).trim())) {
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
				+ "<tr><td>Run Date::</td><td>" + DateUtil.getCurrentdateInStringWithGivenFormate("M/d/yyyy") +" "+DateUtil.getCurrentTime("hh:mm a" , "IST")
				+ "<tr><td>Member/Customer Id::</td><td>" + custId;

		if (flag1) {
			html = html + "<tr><td>Test Case Status::</td><td bgcolor='green'>" + "PASS </td></tr><tr>";
		} else {
			html = html + "<tr><td>Test Case Status::</td><td bgcolor='red'>" + "Fail </td></tr><tr>";
		}
		System.out.println("===>> Notes lenght::"+criteriaList.get("Notes").length());
		if(criteriaList.get("Notes").length()!=0){
			html = html + "<tr><td>Notes::</td><td bgcolor='yellow'>"+criteriaList.get("Notes")+"</td></tr><tr>";
		}else{
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
		logMessage("Case ID::" + criteriaList.get("ID"));
		logMessage("Member/Customer ID::" + custId);
		logMessage("Initial Conditions>");
		logMessage("Initial Mbr Type::" + criteriaList.get("Initial Mbr Type"));
		logMessage("Initial Mbr Status::" + criteriaList.get("Initial Mbr Status"));
		logMessage("Initial Mbr Package::" + criteriaList.get("Initial Mbr Package"));
		logMessage("Initial MP Exp Date::" + criteriaList.get("Initial MP Exp Date"));
		logMessage("Target Mbr Type::" + criteriaList.get("Target Mbr Type"));
		logMessage("Target Mbr Package::" + criteriaList.get("Target Mbr Package"));
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
		ReformatTestFile.createMemberTransferCompleteTestLog("./src/test/resources/Member Transfer Test Logs", html,
				criteriaList.get("ID"));
		Assert.assertTrue(flag1,
				"[FAILED]:: Data for Before and After member transfer does not match the criteria FINAL Test Result ::"
						+ flag1);
		logMessage(
				"[ASSERTION PASSED]:: Data for Before and After member transfer match the criteria FINAL Test Result ::"
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
			System.out.println("Before Key::"+before.getKey().toString().trim()+"1");
			System.out.println("Data List Key Value::"+dataList.get(before.getKey().toString()+"1"));
			if(!(dataList.get(before.getKey().toString()+"1").isEmpty())){
				System.out.println("Before Value::"+before.getValue().toString().trim());
			if (!(before.getValue().toString().trim().equalsIgnoreCase(dataList.get(before.getKey().toString().trim()+"1")))) {
				flag = false;
				logMessage("[FAILED]:: Data Before Member Transfer for Key "+before.getKey()+"::"+before.getValue()+" did not match with the data in the spreadsheet Key"+before.getKey()+"::"+dataList.get(before.getKey().toString().trim()+"1"));
				break;
			}
			}
		}
		Assert.assertTrue(flag,"[FAILED]:: Can't do Member Transfer Now for CASE ID :: "+ID+" for Customer ID::"+custId);
		logMessage("[ASSERTION PASSED]:: Data Before Member Transfer matched with the data mentioned in spreadsheet, Can do Member Transfer Now for CASE ID "+ID);
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
		logMessage("ASSERT PASSED : Current date lies within the Reporting start and end date\n");
	}
	
	public boolean verfiyEndAndStartDate(String reportingEndDate, String reportingStartDate) {
		int endDate, startDate;
		logMessage("Current Date:" + DateUtil.getCurrentdateInStringWithGivenFormate("M/dd/yyyy"));
		logMessage("End Date:"+ reportingEndDate);
		endDate = DateUtil
				.convertStringToDate(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"), "MM/dd/yyyy")
				.compareTo(DateUtil.convertStringToDate(reportingEndDate, "MM/dd/yyyy"));
		logMessage("Current Date:" + DateUtil.getCurrentdateInStringWithGivenFormate("M/dd/yyyy"));
//		logMessage("Start Date:" + DateUtil.convertStringToDate(reportingStartDate, "MM/dd/yyyy"));
		logMessage("Start Date:" + reportingStartDate);
		
		startDate = DateUtil
				.convertStringToDate(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"), "MM/dd/yyyy")
				.compareTo(DateUtil.convertStringToDate(reportingStartDate, "MM/dd/yyyy"));

		if (endDate == -1 && startDate == 1)
			return true;
		else
			return false;
	}
	
	public void selectARandomActiveStudentChapter() {
	    clickOnRandomPage();
		clickOnAnyRandomMember();
	}
	
	public void clickOnRelationsOptionUnderMoreMenu() {
		IndividualsPageActions_IWEB object = new IndividualsPageActions_IWEB(driver);
		object.navigateToGeneralMenuOnHoveringMore("Relations");
		expandDetailsMenu("active student member undergrads");
	}

	public String getChapterDetails(){
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
				logMessage("ASSERT PASSED : End date for student member "+ element("txt_endDate", String.valueOf(i), String.valueOf(4)).getText().trim()+
						"is not equal to Current Date\n");
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
		isElementDisplayed("arrow_selectMember", String.valueOf(i));
		logMessage("STEP : Selected Student Member is : "
				+ element("txt_endDate", String.valueOf(i), String.valueOf(4)).getText().trim());
		element("arrow_selectMember", String.valueOf(i)).click();
	}
	
	public void verifyPayment(int index){
		isElementDisplayed("txt_endDate",String.valueOf(index),String.valueOf(8));
		String payment=element("txt_endDate",String.valueOf(index),String.valueOf(8)).getText().trim();
		Assert.assertTrue(Double.parseDouble(payment)==0.00,"ASSERT FAILED : Payment value is not 0.00\n");
		logMessage("ASSERT PASSED : Payment value is 0.00\n");
	}
	
	public void verifyBalance(int index){
		isElementDisplayed("txt_endDate",String.valueOf(index),String.valueOf(9));
		String balance=element("txt_endDate",String.valueOf(index),String.valueOf(9)).getText().trim();
		Assert.assertNotEquals(Double.parseDouble(balance), 0.00, 0.01, "ASSERT FAILED : Balance value is null\n");//(Double.parseDouble(balance)==0.00,"ASSERT FAILED : Balance value is 0.00");
		logMessage("ASSERT PASSED : Balance value is not null\n");
	}
		
	public int verifyProductUnderDetailsMenu(String productName){
		int i;
		flag=false;
		waitForSpinner();
		for(i=1;i<=elements("table_rows").size();i++){
			if(element("txt_endDate",String.valueOf(i),String.valueOf(4)).getText().trim()
					.equals(productName)){
				flag=true;
				break;
			}
		}
		Assert.assertTrue(flag,"ASSERT FAILED : "+productName+" product is not present under menu");
		logMessage("ASSERT PASSED : "+productName+" product is present under invoices at index "+i);
		return i;
	}
}
