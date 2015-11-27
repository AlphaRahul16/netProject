package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;

public class MembershipPageActions_IWEB extends ASCSocietyGenericPage {
	YamlInformationProvider getCenOrdEntry;
	Map<String, Object> mapReinstateMember;
	WebDriver driver;
	static String pagename = "MembershipPage";
	static String index, selectedText, customerLname, customerFname, address,
			state, zipCode, customerEmail, city, currentDate,
			customerContactId, customerEmailAcsOrg, customerAddressType,
			nextYearDate, displayName;
	boolean flag = false;
	int timeOut, hiddenFieldTimeOut;
	List<String> memberDetails = new ArrayList<>();
	StringBuffer sb = new StringBuffer();

	public MembershipPageActions_IWEB(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

	public void selectAndRunQueryMembership(String queryName) {
		clickOnSideBar("Query Membership");
		wait.waitForPageToLoadCompletely();
		waitForSpinner();
		wait.hardWait(3);
		isElementDisplayed("txt_loadOnExistingQueryLabel");
		selectExistingQuery(queryName);
		waitForSpinner();
		verifyQueryTablePresent();
		clickOnRunQuery();
	}

	public void clickOnSideBar(String tabName) {
		isElementDisplayed("hd_sideBar", tabName);
		clickUsingXpathInJavaScriptExecutor(element("hd_sideBar", tabName));
		logMessage("Step : click on side bar for tab " + tabName
				+ " at hd_sideBar\n");
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
		wait.hardWait(1);
		if (queryName
				.equalsIgnoreCase("Member Query by Member Number – Multiple")) {
			selectDropDownValue(element("list_existingQuery"), 196);
		} else {
			selectProvidedTextFromDropDown(element("list_existingQuery"),
					queryName);
		}
		logMessage("Step : Select existing query " + queryName);
	}

	public void waitForSpinner() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		try {
			handleAlert();
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("img_spinner");
			wait.waitForElementToDisappear(element("img_spinner"));
			logMessage("STEP : wait for spinner to be disappeared \n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (NoSuchElementException | AssertionError | TimeoutException Exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("STEP : spinner is not present \n");
		}
	}

	public void verifyQueryTablePresent() {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("table_query");
	}

	public void clickOnRunQuery() {
		isElementDisplayed("btn_runQuery");
		clickUsingXpathInJavaScriptExecutor(element("btn_runQuery"));
		logMessage("Step : click on run query button in btn_runQuery \n");
	}

	public void clickOnMenuItems(String menuName) {
		isElementDisplayed("btn_menuItems", menuName);

		element("btn_menuItems", menuName).click();
		logMessage("Step : click on " + menuName
				+ " button in btn_menuItems \n");
	}

	public void clickOnEditContactInfo() {
		wait.hardWait(1);
		isElementDisplayed("btn_editContactInfo");
		executeJavascript("document.getElementById('F1_HYPERLINK_1').click()");
		logMessage("Step : Click on edit contact info button in btn_editContactInfo\n");
	}

	public void clickOnEditNameAndAddress() {
		wait.hardWait(1);
		handleAlert();
		handleAlert();
		isElementDisplayed("btn_editNameAndAddress");
		clickUsingXpathInJavaScriptExecutor(element("btn_editNameAndAddress"));
		// element("btn_editNameAndAddress").click();
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
		logMessage("Step : email id " + existingEmail + " is edited to "
				+ newEmail + " in inp_editEmail\n");
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
			logMessage("Step : Email address is edited to " + emailId
					+ " in inp_editEmail\n");
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
			String detailValue = element("inp_" + detailName).getAttribute(
					"value");
			logMessage("Step : " + detailName + " is " + detailValue);
			return detailValue;
		} catch (StaleElementReferenceException stlExp) {
			isElementDisplayed("inp_" + detailName);
			String detailValue = element("inp_" + detailName).getAttribute(
					"value");
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
		customerFname = getNameFromEditNameAndAddressButton("firstName");
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
		clickOnMenuItems("individual memberships");
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
		customerEmailAcsOrg = "inactivememberEmail"
				+ System.currentTimeMillis() + "@acs.org";
		element("inp_editEmail").sendKeys(customerEmailAcsOrg);
		logMessage("Step : email address is edited to " + customerEmailAcsOrg
				+ "\n");
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
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));

		mapReinstateMember = YamlReader.getYamlValues("ReinstateMemberIWEB");
		getCenOrdEntry = new YamlInformationProvider(mapReinstateMember);
		clickOnOrderEntryIcon();
		// TODO Remove hard wait after handling stale element exception
		holdExecution(2000);
		isElementDisplayed("table_lineItems");
		element("table_lineItems").click();
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
			clickOnSelectProduct();
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
		selectMemberInfo(
				"association",
				getCenOrdEntry
						.getReinstateMember_centralizedOrderEntry("association"));
		// TODO Remove hard wait after handling stale element exception
		holdExecution(1000);
		selectMemberInfo("memberType",
				getCenOrdEntry
						.getReinstateMember_centralizedOrderEntry("memberType"));
		// TODO Remove hard wait after handling stale element exception
		holdExecution(1000);
		selectMemberInfo(
				"memberPackage",
				getCenOrdEntry
						.getReinstateMember_centralizedOrderEntry("memberPackage"));
		String currentDate = DateUtil
				.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy");
		String nextSecondYear = DateUtil.getNextDate("year", 2)[0];
		// TODO Remove hard wait after handling stale element exception
		holdExecution(2000);
		enterDate("industryUpdateDate", currentDate);
		enterDate("jobTitleUpdateDate", "08/02/" + nextSecondYear);
		selectMemberInfo("industry",
				getCenOrdEntry
						.getReinstateMember_centralizedOrderEntry("industry"));
		// TODO Remove hard wait after handling stale element exception
		holdExecution(1000);
		selectMemberInfo("jobTitle",
				getCenOrdEntry
						.getReinstateMember_centralizedOrderEntry("jobTitle"));
		// TODO Remove hard wait after handling stale element exception
		holdExecution(1000);
		clickOnSaveAndFinish();
		handleAlert();

	}

	public void verifyItemsAddedSuccessFully() {
		verifyItemsAdded(getCenOrdEntry
				.getReinstateMember_centralizedOrderEntry("memberPackage"));
		verifyItemsAdded("Chemical and Engineering News");
	}

	public void selectBatchAndPaymentDetails() {
		// selectBillingAddress();
		selectOrderEntryInfo("batch",
				getCenOrdEntry
						.getReinstateMember_centralizedOrderEntry("batch"));
		waitForSpinner();
		selectOrderEntryInfo(
				"PaymentType",
				getCenOrdEntry
						.getReinstateMember_centralizedOrderEntry("PaymentType"));
		waitForSpinner();
		selectOrderEntryInfo(
				"paymentMethod",
				getCenOrdEntry
						.getReinstateMember_centralizedOrderEntry("paymentMethod"));
		waitForSpinner();
		enterCardDetails("cardNumber",
				getCenOrdEntry
						.getReinstateMember_centralizedOrderEntry("cardNumber"));
		selectMemberInfo("expireDate",
				getCenOrdEntry
						.getReinstateMember_centralizedOrderEntry("expireDate"));
		enterCardDetails("cvvNumber",
				getCenOrdEntry
						.getReinstateMember_centralizedOrderEntry("cvvNumber"));
		clickOnSaveAndFinish();
		handleAlert();

	}

	public void verifyMemberReinstatedSuccessfully(String caseId) {
		handleAlert();
		clickOnMenuItems("individual memberships");
		verifyRejoinDateIsCurentDate();
		verifyEffectiveDateIsCurentDate();
		verifyEndDateIsCuurentDate(caseId);
		verifyTickedMarked("receives member benefits");
		verifyTickedMarked("member");
		verifyMemberStatusActiveIsPresent();
		clickOnMenuItems("individual memberships");
	}

	public void verifyMemberReinstatedSuccessfully_Iweb() {
		handleAlert();
		clickOnMenuItems("individual memberships");
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
		wait.hardWait(1);
		wait.waitForPageToLoadCompletely();
		int max = 12, min = 3;
		Random rand = new Random();
		int randomNumber = rand.nextInt((max - min) + 1) + min;
		String randomNumberInString = String.valueOf(randomNumber);
		isElementDisplayed("link_randomMemberInList", randomNumberInString);
		clickUsingXpathInJavaScriptExecutor(element("link_randomMemberInList",
				randomNumberInString));
		logMessage("Step : Member icon at the position of "
				+ randomNumberInString
				+ " is clicked in link_randomMemberInList\n");
	}

	public void verifyMemberStatus(String memberStatus) {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(3);
		isElementDisplayed("txt_memberStatus");
		verifyElementTextContains("txt_memberStatus", memberStatus);
		logMessage("ASSERT PASSED : member status " + memberStatus
				+ " is verified on membership page\n");
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
		Assert.assertTrue(element("img_cross", memberDetailsName).getAttribute(
				"src").contains("img_list_delete1.gif"));
		logMessage("ASSERT PASSED : cross marked is verified for "
				+ memberDetailsName + "\n");
	}

	public void clickOnSelectProduct() {
		isElementDisplayed("lnk_selectProduct");
		element("lnk_selectProduct").click();
		logMessage("Step : select product link is clicked in lnk_selectProduct\n");
	}

	public void clickOnOrderEntryIcon() {
		isElementDisplayed("img_orderEntry");
		if (isBrowser("ie") || isBrowser("internetexplorer")) {
			clickUsingXpathInJavaScriptExecutor(element("img_orderEntry"));
		} else {
			element("img_orderEntry").click();
		}
		logMessage("Step : order entry icon is clicked in img_orderEntry\n");
	}

	public void clickOnAddMembershipMenu() {
		isElementDisplayed("txt_menuItems");
		element("txt_menuItems").click();
		logMessage("Step: click on add membership menu\n");
	}

	public void selectMemberInfo(String memberInfo, String memberInfoValue) {
		try {
			isElementDisplayed("list_" + memberInfo);
			selectProvidedTextFromDropDown(element("list_" + memberInfo),
					memberInfoValue);
			logMessage("ASSERT PASSED : " + memberInfoValue
					+ " is selected in list_" + memberInfo + "\n");
		} catch (StaleElementReferenceException stlExp) {
			selectProvidedTextFromDropDown(element("list_" + memberInfo),
					memberInfoValue);
			logMessage("select Element " + memberInfo
					+ " after catching Stale Element Exception\n");
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
		logMessage("ASSERT PASSED : item " + itemName
				+ " is added in item list\n");
	}

	public void selectOrderEntryInfo(String orderEntryInfo, String value) {
		isElementDisplayed("list_" + orderEntryInfo);
		selectProvidedTextFromDropDown(element("list_" + orderEntryInfo), value);
		logMessage("Step : " + value + " is selected in " + orderEntryInfo
				+ "\n");
	}

	public void clickOnSaveAndFinish() {
		isElementDisplayed("btn_saveAndFinish");
		element("btn_saveAndFinish").click();
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
		currentDate = DateUtil
				.getCurrentdateInStringWithGivenFormate("M/d/yyyy");

		if (currentDate.startsWith("0")) {
			String newCurrentDate = currentDate.substring(1);
			isStringMatching(rejoindate, newCurrentDate);
		} else {
			isStringMatching(rejoindate, currentDate);
		}

	}

	public void verifyEffectiveDateIsCurentDate() {
		isElementDisplayed("txt_effectiveDateForActive");
		String rejoindate = element("txt_effectiveDateForActive").getText()
				.trim();
		currentDate = DateUtil
				.getCurrentdateInStringWithGivenFormate("M/d/yyyy");

		if (currentDate.startsWith("0")) {
			String newCurrentDate = currentDate.substring(1);
			isStringMatching(rejoindate, newCurrentDate);
		} else {
			isStringMatching(rejoindate, currentDate);
		}

	}

	public void verifyEndDateIsCuurentDate(String caseId) {
		isElementDisplayed("txt_expireDateForActive");
		String rejoindate = element("txt_expireDateForActive").getText().trim();

		nextYearDate = DateUtil
				.getAddYearWithLessOnedayInStringWithGivenFormate("M/D/YYYY",
						"EST5EDT",
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
		isElementDisplayed("img_ticked", index);
		Assert.assertTrue(element("img_ticked", index).getAttribute("src")
				.contains("img_chkmk.gif"));
		logMessage("ASSERT PASSED : verified ticked mark for "
				+ memberDetailsName + "\n");
	}

	public void verifyMemberStatusActiveIsPresent() {
		for (WebElement element : elements("list_mbrStatus")) {
			if (element.getText().trim().equalsIgnoreCase("Active")) {
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
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		holdExecution(1000);
		try {
			wait.resetImplicitTimeout(3);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("inp_" + addType);
			element("inp_" + addType).sendKeys(addValue);
			logMessage("Step : " + addValue + " is enetered in inp_" + addType
					+ "\n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (StaleElementReferenceException stl) {
			wait.resetImplicitTimeout(3);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("inp_" + addType);
			element("inp_" + addType).sendKeys(addValue);
			logMessage("Step : " + addValue + " is enetered in inp_" + addType
					+ "\n");
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
			selectProvidedTextFromDropDown(element("list_addressType"),
					"billing");
			logMessage("Step : billing is selected in address type\n");
			clickOnDoNotValidate();

			enterBillingAddress(
					"addressLine",
					YamlReader
							.getYamlValue("ReinstateMemberIWEB.billingAddress.address"));
			enterBillingAddress(
					"city",
					YamlReader
							.getYamlValue("ReinstateMemberIWEB.billingAddress.city"));
			enterBillingAddress(
					"country",
					YamlReader
							.getYamlValue("ReinstateMemberIWEB.billingAddress.country"));
			// TODO Remove hard wait after handling stale element exception
			holdExecution(1000);
			isElementDisplayed("list_state");
			selectProvidedTextFromDropDown(
					element("list_state"),
					YamlReader
							.getYamlValue("ReinstateMemberIWEB.billingAddress.state"));
			logMessage("Step : New York is selected in list_state\n");
			enterBillingAddress(
					"postalCode",
					YamlReader
							.getYamlValue("ReinstateMemberIWEB.billingAddress.postalCode"));
			enterBillingAddress(
					"district",
					YamlReader
							.getYamlValue("ReinstateMemberIWEB.billingAddress.district"));
			enterBillingAddress(
					"congressional",
					YamlReader
							.getYamlValue("ReinstateMemberIWEB.billingAddress.congressional"));
			enterBillingAddress(
					"province",
					YamlReader
							.getYamlValue("ReinstateMemberIWEB.billingAddress.province"));
			enterBillingAddress(
					"mail",
					YamlReader
							.getYamlValue("ReinstateMemberIWEB.billingAddress.mail"));
			clickOnSaveButtonForBillingAddress();
			switchToDefaultContent();
			// TODO Remove hard wait after handling stale element exception
			holdExecution(1000);
			pageRefresh();
			selectProvidedTextFromDropDown(
					element("list_billAddress"),
					"billing: "
							+ YamlReader
									.getYamlValue("ReinstateMemberIWEB.billingAddress.address"));
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
		logMessage("Step : " + memberDetail + " " + detailValue
				+ " is entered to search member\n");
	}

	public void clickOnGoButton() {
		isElementDisplayed("btn_go");
		element("btn_go").click();
		logMessage("Step : Go button is clicked in btn_go\n");
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
		String currentDate = DateUtil
				.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy");
		for (WebElement element : elements("list_joindate")) {
			if (currentDate.startsWith("0")) {
				String newCurrentDate = currentDate.substring(1);
				if (element.getText().trim().equalsIgnoreCase(newCurrentDate)) {
					logMessage("ASSERT PASSED : member rejoin date is current date "
							+ newCurrentDate + " in list\n");
					flag = true;
					break;
				}
			} else if (element.getText().trim().equalsIgnoreCase(currentDate)) {
				logMessage("ASSERT PASSED : member rejoin date is current date "
						+ currentDate + " in list\n");
				flag = true;
				break;
			}
		}
		if (!flag)
			Assert.fail("ASSERT PASSED : member status active is not present in status list\n");
	}

	public String numberOfYearsForInactiveMember() {
		isElementDisplayed("txt_numberOfyears");
		String numberOfYears = element("txt_numberOfyears").getText();
		logMessage("Step : total years of services for inactive member is "
				+ numberOfYears);
		return numberOfYears;
	}

	public void numberOfYearsForActiveMember(String numberOfYears) {
		String noOfYears = String.valueOf(Integer.parseInt(numberOfYears) + 1);
		isElementDisplayed("txt_numberOfyears");
		verifyElementText("txt_numberOfyears", noOfYears);
		logMessage("ASSERT PASSED : " + noOfYears
				+ " total years of services in txt_numberOfyears\n");

	}

	public List<String> searchNonRenewalMemberAndGetDetails() {
		clickOnSideBar("Find Members");
		wait.waitForPageToLoadCompletely();
		checkAdvanceNewCheckbox();
		selectValueInAdvanceNewNextDropDown("Member Status",
				"ACS : Active Renewed-No Response");

		selectAdvanceNewDropDown("Expire Date", "Less Than");
		enterValueInAdvanceNewInput("Expire Date",
				DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/YYY"));

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

	public List<String> findMemberAndGetDetail(String memberType,
			String memberStatus, String country) {
		memberDetails = new ArrayList<>();
		clickOnSideBar("Find Members");
		wait.waitForPageToLoadCompletely();
		selectValueInAdvanceNewNextDropDown("Member Type", "ACS : "
				+ memberType);
		selectValueInAdvanceNewNextDropDown("Member Status", "ACS : "
				+ memberStatus);
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
		memberDetails
				.add(getMemberDetailsOnMemberShipProfile("effective date"));
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
			System.out.println(flag);
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			return flag;
		} catch (Exception e) {
			flag = false;
			System.out.println("in catch: " + flag);
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
		String info = element("txt_membershipProfileInfo", memberInfo)
				.getText().trim();
		logMessage("Step : " + memberInfo + " is " + info + " \n");
		return info;
	}

	public void openSubInfoDropDown(String subInfoName) {
		isElementDisplayed("btn_memberShipSubInfo", subInfoName);
		element("btn_memberShipSubInfo", subInfoName).click();
		logMessage("Step : click on sub info on membership profile\n");

	}

	public String getMemberDetailsOnMemberShipProfile(String memberInfo) {
		isElementDisplayed("txt_membershipProfileDetails", memberInfo);
		String info = element("txt_membershipProfileDetails", memberInfo)
				.getText().trim();
		logMessage("Step : " + memberInfo + " is " + info + " \n");
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
		selectProvidedTextFromDropDown(element("list_memberStatusRenewal"),
				memberStatus);
		logMessage("STEP : member status " + memberStatus + " is selected \n");
	}

	public void checkAdvanceNewCheckbox() {
		isElementDisplayed("chk_advanceNew");
		element("chk_advanceNew").click();
		logMessage("STEP : Advance new checkbox is checked in chk_advanceNew\n");
	}

	public void selectAdvanceNewDropDown(String heading, String value) {
		isElementDisplayed("list_advanceNewDropDown", heading);
		selectProvidedTextFromDropDown(
				element("list_advanceNewDropDown", heading), value);
		logMessage("STEP : select " + value + " for " + heading
				+ " in list_advanceNewDropDown\n");
	}

	public void enterValueInAdvanceNewInput(String heading, String value) {
		isElementDisplayed("inp_advanceNewInput", heading);
		element("inp_advanceNewInput", heading).clear();
		element("inp_advanceNewInput", heading).sendKeys(value);
		logMessage("STEP : enter " + value + " for " + heading
				+ " in inp_advanceNewInput\n");
	}

	public void selectValueInAdvanceNewNextDropDown(String heading, String value) {
		isElementDisplayed("list_advanceNewInput", heading);
		selectProvidedTextFromDropDown(
				element("list_advanceNewInput", heading), value);
		logMessage("STEP : select " + value + " in next drop down for "
				+ heading + " in list_advanceNewInput\n");
	}

	public List<String> getMemberDetails() {
		clickOnEditNameAndAddress();
		switchToFrame("iframe1");
		customerLname = getNameFromEditNameAndAddressButton("lastName");
		logMessage("Step : Member last name is " + customerLname);
		// isElementDisplayed("inp_editEmail");
		// customerEmail = element("inp_editEmail").getAttribute("value");
		clickOnCancelButton();
		handleAlert();
		switchToDefaultContent();
		wait.hardWait(1);
		handleAlert();
		wait.hardWait(3);
		try {
			customerContactId = element("txt_renewalContactId").getText().trim();
			logMessage("Step : Member contact number is " + customerContactId);
		} catch (StaleElementReferenceException stlExp) {
			customerContactId = element("txt_renewalContactId").getText().trim();
			logMessage("Step : Member contact number is " + customerContactId);
		}
		memberDetails.add(customerLname);
		memberDetails.add(customerContactId);
		// memberDetails.add(customerEmail);
		return memberDetails;
	}

	public List<String> selectMemberAndGetDetails() {
		clickOnRandomPage();
		clickOnAnyRandomMember();
		getMemberDetails();
		return memberDetails;
	}

	public void selectSubscriptionInSelectProductLink() {
		isElementDisplayed("link_subscriptionInSelectProduct");
		element("link_subscriptionInSelectProduct").click();
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
			clickUsingXpathInJavaScriptExecutor(element("lnk_pages",
					randomNumberInString));
			logMessage("Step : page at the position of " + randomNumberInString
					+ " is clicked in lnk_pages\n");
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
		element("table_lineItems").click();
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
			selectSubscriptionInSelectProductLink();
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (Exception e) {
			wait.waitForPageToLoadCompletely();
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			switchToDefaultContent();
			clickOnSelectProduct();
			// TODO Remove hard wait after handling stale element exception
			holdExecution(3000);
			switchToFrame(element("frame_selectProduct"));
			selectSubscriptionInSelectProductLink();
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}

		switchToDefaultContent();
	}

	public String addSubscriptionInOrderEntry(String prodCode) {
		switchToFrame("iframe1");
		enterProductCode(prodCode);
		displayName = searchAndGetDisplayName();

		logMessage("Step : Display name is : " + displayName + "\n");
		clickOnSaveAndFinish();
		switchToDefaultContent();
		waitForSpinner();
		wait.hardWait(2);
		if (displayName
				.startsWith("Journal of Chemical Theory and Computation")) {
			displayName = "Jrnl of Chemical Theory & Computation";
		} else if (displayName
				.startsWith("Journal of the American Chemical Society")) {
			displayName = "Jrnl of The American Chemical Society";
		}
		verifyItemAddedInLineItems(displayName);
		return displayName;
	}

	public void enterProductCode(String prodCode) {
		isElementDisplayed("inp_prdCode");
		element("inp_prdCode").sendKeys(prodCode);
		logMessage("Step : Produuct code " + prodCode
				+ " is entered in inp_prdCode\n");
	}

	public void clickOnSearchDisplayNameButton() {
		isElementDisplayed("inp_searchDisplayButton");
		element("inp_searchDisplayButton").click();
		wait.hardWait(2);
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
		isElementDisplayed("link_itemInLineItems");
		verifyElementText("link_itemInLineItems", itemName);
	}

	public void selectBatchAndPaymentDetails_subscription(String batchName,
			String paymentType, String paymentMethod, String cardNumber,
			String expireDate, String cvvNumber) {
		selectOrderEntryInfo("batch", batchName);
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
		element("btn_askGo").click();
		logMessage("Step : Go button is clicked in btn_askGo\n");
	}

	public void enterCustomerIdsInRunQuery(String... customerIds) {
		isElementDisplayed("inp_customerId");
		String customerID = customerIds[0] + "," + customerIds[1];
		element("inp_customerId").clear();
		element("inp_customerId").sendKeys(customerID);
		logMessage("Step : enter customer IDs " + customerID + " \n");
		clickOnGoButtonInRunQuery();
	}

	public void verifyMultipleRecordsInList(String numberOfRecords) {
		isElementDisplayed("txt_recordNumberAtMemberQuery");
		String recordNumber = element("txt_recordNumberAtMemberQuery")
				.getText().trim();
		System.out.println(recordNumber);
		Assert.assertTrue(recordNumber.equalsIgnoreCase("(" + numberOfRecords
				+ " records)"));
		logMessage("Step : multiple records are presents in the List - Member Query by Member Number - Multiple \n");

	}

	public void verifyMembershipDetailsOnRenewal(String expireDate,
			String memberPackage, String renewalPackage, String customerID,
			String effectiveDate, String joinDate, String paymentStatus) {
		verifyMemberDetailsOnMemberShipProfile("expire date", expireDate);
		verifyMemberInfoOnMemberShipProfile("member package", memberPackage);
		verifyMemberInfoOnMemberShipProfile("renewal package", renewalPackage);
		verifyMemberDetailsOnMemberShipProfile("customer id", customerID);
		verifyMemberDetailsOnMemberShipProfile("effective date", effectiveDate);
		verifyMemberDetailsOnMemberShipProfile("join date", joinDate);
		verifyPaymentStatus(paymentStatus);

	}

	public void verifyInvoiceDetailsOnRenewal(String productName,
			String invoiceId) {
		openSubInfoDropDown("invoices");
		flag = pagesLinkAvailable();
		verifyProductNameInInvoice(productName, flag);
		verifyInvoiceIDInInvoice(invoiceId, flag);
		verifyTermStartDateInvoice("", flag);
		verifyTermEndDateInvoice("", flag);

	}

	public void verifyMemberInfoOnMemberShipProfile(String memberdetail,
			String memberValue) {
		isElementDisplayed("txt_membershipProfileInfo", memberdetail);
		System.out.println(element("txt_membershipProfileInfo", memberdetail)
				.getText().trim());
		System.out.println(memberValue);
		Assert.assertTrue(element("txt_membershipProfileInfo", memberdetail)
				.getText().trim().equalsIgnoreCase(memberValue));
		logMessage("ASSERT PASSED : " + memberValue + " is verified for "
				+ memberValue + " \n");

	}

	public void verifyMemberDetailsOnMemberShipProfile(String memberdetail,
			String memberValue) {
		isElementDisplayed("txt_membershipProfileDetails", memberdetail);
		System.out
				.println(element("txt_membershipProfileDetails", memberdetail)
						.getText().trim());
		System.out.println(memberValue);
		Assert.assertTrue(element("txt_membershipProfileDetails", memberdetail)
				.getText().trim().equalsIgnoreCase(memberValue));
		logMessage("ASSERT PASSED : " + memberValue + " is verified for "
				+ memberValue + " \n");

	}

	public void verifyProductNameInInvoice(String productName, boolean pageLink) {
		if (pageLink) {
			isElementDisplayed("txt_productNameOnPage");
			System.out.println(element("txt_productNameOnPage").getText()
					.trim());
			System.out.println(productName);
			Assert.assertTrue(element("txt_productNameOnPage").getText().trim()
					.equalsIgnoreCase(productName));
			logMessage("ASSERT PASSED : product name " + productName
					+ " in invoice is verified\n");
		} else {
			isElementDisplayed("txt_productName");
			System.out.println(element("txt_productName").getText().trim());
			System.out.println(productName);
			Assert.assertTrue(element("txt_productName").getText().trim()
					.equalsIgnoreCase(productName));
			logMessage("ASSERT PASSED : product name " + productName
					+ " in invoice is verified\n");

		}

	}

	public void verifyInvoiceIDInInvoice(String invoiceID, boolean pageLink) {
		if (pageLink) {
			isElementDisplayed("txt_invoiceIdOnPage");
			System.out.println(element("txt_invoiceIdOnPage").getText().trim());
			System.out.println(invoiceID);
			Assert.assertTrue(element("txt_invoiceIdOnPage").getText().trim()
					.equalsIgnoreCase(invoiceID));
			logMessage("ASSERT PASSED : invoice id is " + invoiceID
					+ " in invoice is verified\n");
		} else {
			isElementDisplayed("txt_invoiceId");
			System.out.println(element("txt_invoiceId").getText().trim());
			System.out.println(invoiceID);
			Assert.assertTrue(element("txt_invoiceId").getText().trim()
					.equalsIgnoreCase(invoiceID));
			logMessage("ASSERT PASSED : invoice id is " + invoiceID
					+ " in invoice is verified\n");

		}

	}

	public void verifyTermStartDateInvoice(String startDate, boolean pageLink) {
		if (pageLink) {
			isElementDisplayed("txt_termStartDateOnPage");
			System.out.println(element("txt_termStartDateOnPage").getText()
					.trim());
			System.out.println(startDate);
			Assert.assertTrue(element("txt_termStartDateOnPage").getText()
					.trim().equalsIgnoreCase(startDate));
			logMessage("ASSERT PASSED : invoice id is " + startDate
					+ " in invoice is verified\n");
		} else {
			isElementDisplayed("txt_termStartDate");
			System.out.println(element("txt_termStartDate").getText().trim());
			System.out.println(startDate);
			Assert.assertTrue(element("txt_termStartDate").getText().trim()
					.equalsIgnoreCase(startDate));
			logMessage("ASSERT PASSED : start date is " + startDate
					+ " in invoice is verified\n");
		}
	}

	public void verifyTermEndDateInvoice(String endDate, boolean pageLink) {
		if (pageLink) {
			isElementDisplayed("txt_termEndDateOnPage");
			System.out.println(element("txt_termEndDateOnPage").getText()
					.trim());
			System.out.println(endDate);
			Assert.assertTrue(element("txt_termEndDateOnPage").getText().trim()
					.equalsIgnoreCase(endDate));
			logMessage("ASSERT PASSED : end date is " + endDate
					+ " in invoice is verified\n");
		} else {
			isElementDisplayed("txt_termEndDate");
			System.out.println(element("txt_termEndDate").getText().trim());
			System.out.println(endDate);
			Assert.assertTrue(element("txt_termEndDate").getText().trim()
					.equalsIgnoreCase(endDate));
			logMessage("ASSERT PASSED : end date is " + endDate
					+ " in invoice is verified\n");
		}
	}

	public void verifyPaymentStatus(String paymentStatus) {
		isElementDisplayed("txt_paymentStatus");
		System.out.println(element("txt_paymentStatus").getText().trim());
		System.out.println(paymentStatus);
		Assert.assertTrue(element("txt_paymentStatus").getText()
				.equalsIgnoreCase(paymentStatus));
		logMessage("ASSERT PASSED : payment status " + paymentStatus
				+ " is verified\n");
	}

}
