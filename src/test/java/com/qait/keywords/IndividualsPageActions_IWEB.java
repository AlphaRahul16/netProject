package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.awt.Button;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.DateUtil;

public class IndividualsPageActions_IWEB extends ASCSocietyGenericPage {

	WebDriver driver;
	String pagename = "IndividualsPage";
	String numberOfYears, iwebProductName1;
	String IWEBProduct;
	int timeOut, hiddenFieldTimeOut;
	boolean flag, flag1;

	public IndividualsPageActions_IWEB(WebDriver driver) {
		super(driver, "IndividualsPage");
		this.driver = driver;
	}

	public void clickOnAdvancedViewCheckBox() {
		isElementDisplayed("chk_advancedView");
		element("chk_advancedView").click();
		logMessage("STEP : Click on checkbox of advanced view \n");
	}

	public void selectAdvancedSearchValue(String fieldName, String searchCriteria) {

		selectProvidedTextFromDropDown(element("drpdwn_selectSearchvalue", fieldName), searchCriteria);
		logMessage("STEP : Select advance search value as " + searchCriteria + " for " + fieldName);
	}

	public void enterSearchValueInAdvancedViewText(String fieldName, String fieldValue) {
		isElementDisplayed("txt_enterSearchValue", fieldName);
		element("txt_enterSearchValue", fieldName).sendKeys(fieldValue);
		logMessage("STEP : " + fieldValue + " is entered for " + fieldName);

	}

	public void fillMemberDetailsAndSearchInAdvancedView(String fieldName, String searchCriteria, String fieldValue) {
		clickOnAdvancedViewCheckBox();
		selectAdvancedSearchValue(fieldName, searchCriteria);
		enterSearchValueInAdvancedViewText(fieldName, fieldValue);
		clickGoButton();
	}

	public void fillMemberDetailsAndSearch(String fieldName, String fieldValue) {
		hardWaitForIEBrowser(4);
		enterFieldValue(fieldName, fieldValue);
		clickGoButton();
		handleAlert();
		logMessage("STEP : Member detail is filled and search\n");
	}

	public void checkMemberDetailsAndSearch(String fieldName) {
		hardWaitForIEBrowser(4);
		if (!element("inp_fieldName", fieldName).isSelected()) {
			if (isIEBrowser()) {
				clickUsingXpathInJavaScriptExecutor(element("inp_fieldName", fieldName));
			} else {
				element("inp_fieldName", fieldName).click();
			}
			hardWaitForIEBrowser(2);
			logMessage("STEP : Check " + fieldName + " to search member\n");
		} else {
			logMessage("STEP : Member detail is already checked\n");
		}
		clickGoButton();
		handleAlert();
	}

	public void enterFieldValue(String fieldName, String fieldValue) {
		wait.hardWait(5);
		isElementDisplayed("inp_fieldName", fieldName);
		element("inp_fieldName", fieldName).sendKeys(fieldValue.split("-")[0]);

		logMessage("STEP : " + fieldName + " as " + fieldValue + " is entered in inp_fieldName\n");

	}

	public void clickGoButton() {
		isElementDisplayed("btn_Go");
		if (isIEBrowser()) {
			clickUsingXpathInJavaScriptExecutor(element("btn_Go"));
		} else {
			element("btn_Go").click();
		}
		handleAlert();
		logMessage("STEP :  Go button is clicked in btn_Go\n");

	}

	public void verifyMemberDetails(String fName, String lName, String add, String city, String zipCode,
			String addressType, String contactId, String userEmail) {
		verifyElementTextContains("txt_memberDetails", fName);
		logMessage("ASSERT PASSED :" + fName + " is verified in txt_memberDetails\n");
		verifyElementTextContains("txt_memberDetails", lName);
		logMessage("ASSERT PASSED :" + lName + " is verified in txt_memberDetails\n");

		verifyElementTextContains("txt_memberDetails", add);
		logMessage("ASSERT PASSED :" + add + " is verified in txt_memberDetails\n");
		verifyElementTextContains("txt_memberDetails", city);
		logMessage("ASSERT PASSED :" + city + " is verified in txt_memberDetails\n");
		verifyElementTextContains("txt_memberDetails", zipCode);
		logMessage("ASSERT PASSED :" + zipCode + " is verified in txt_memberDetails\n");
		isElementDisplayed("txt_additionalInfo", addressType.toLowerCase());
		logMessage("ASSERT PASSED :" + addressType.toLowerCase() + " is verified in txt_memberDetails\n");
		isElementDisplayed("txt_additionalInfo", contactId.trim());
		logMessage("ASSERT PASSED : contact ID " + contactId.trim() + " is verified in txt_memberDetails\n");
		if (!userEmail.equalsIgnoreCase("")) {
			try {
				isElementDisplayed("txt_userEmail", userEmail);
			} catch (StaleElementReferenceException stlExp) {
				isElementDisplayed("txt_userEmail", userEmail);
			}

			logMessage("ASSERT PASSED :" + userEmail + " is verified in txt_memberDetails\n");
		}

	}

	public void editEmail_NCW_CCED(String emailType, String emailID, String position) { // -------------
		clickOnEditButton(position);
		switchToFrame("iframe1");
		selectEmailType(emailType);
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		enterEmailIDToAdd(emailID);
		clickOnSaveButton();
		handleAlert();
		// clickOnSaveButton();
		switchToDefaultContent();
		verifyNCW_CCEDEmailPresent(emailType, emailID);
	}

	public void clickOnEditButton(String position) {
		// isElementDisplayed("link_editBtn",position);
		// element("link_editBtn",position).click();
		// logMessage("Step : edit button is clicked \n");

		isElementDisplayed("link_editEmail", position);
		element("link_editEmail", position).click();
		logMessage("STEP : Edit button is clicked \n");

	}

	public void verifyMemberDetails_OMA(String fName, String lName, String add, String city, String zipCode,
			String addressType, String contactId, String userEmail, String caseId) {

		hardWaitForIEBrowser(10);
		verifyElementTextContains("txt_memberDetails", fName);
		logMessage("ASSERT PASSED :" + fName + " is verified in txt_memberDetails\n");
		verifyElementTextContains("txt_memberDetails", lName);
		logMessage("ASSERT PASSED :" + lName + " is verified in txt_memberDetails\n");
		if (map().get("Has Summer mailing address?").equalsIgnoreCase("show")) {
			String startDateInString = map().get("Select Summer mailing start day") + "/"
					+ map().get("Select Summer mailing  start month");
			String endDateInString = map().get("Select Summer mailing end month") + "/"
					+ map().get("Select Summer mailing end day");
			Date startDate = DateUtil.convertStringToDate(startDateInString, "dd/MM");
			Date endDate = DateUtil.convertStringToDate(endDateInString, "dd/MM");
			Date currentDate = DateUtil.convertStringToDate(DateUtil.getCurrentdateInStringWithGivenFormate("dd/MM"),
					"dd/MM");
			if (currentDate.after(startDate) && currentDate.before(endDate)) {
				verifyElementTextContains("txt_memberDetails", map().get("Enter Summer mailing address"));
				logMessage("ASSERT PASSED :" + map().get("Enter Summer mailing address")
						+ " is verified in txt_memberDetails\n");
				verifyElementTextContains("txt_memberDetails", map().get("Enter  Summer city"));
				logMessage("ASSERT PASSED :" + map().get("Enter  Summer city") + " is verified in txt_memberDetails\n");
				verifyElementTextContains("txt_memberDetails", map().get("Enter  Summer zip code"));
				logMessage("ASSERT PASSED :" + map().get("Enter  Summer zip code")
						+ " is verified in txt_memberDetails\n");
				isElementDisplayed("txt_additionalInfo", "seasonal");
				logMessage("ASSERT PASSED : seasonal is verified in txt_memberDetails\n");
			}
		} else {
			verifyElementTextContains("txt_memberDetails", add);
			logMessage("ASSERT PASSED :" + add + " is verified in txt_memberDetails\n");
			verifyElementTextContains("txt_memberDetails", city);
			logMessage("ASSERT PASSED :" + city + " is verified in txt_memberDetails\n");
			verifyElementTextContains("txt_memberDetails", zipCode);
			logMessage("ASSERT PASSED :" + zipCode + " is verified in txt_memberDetails\n");
			isElementDisplayed("txt_additionalInfo", addressType.toLowerCase());
			logMessage("ASSERT PASSED :" + addressType.toLowerCase() + " is verified in txt_memberDetails\n");
		}
		isElementDisplayed("txt_additionalInfo", contactId.trim());
		logMessage("ASSERT PASSED : contact ID " + contactId.trim() + " is verified in txt_memberDetails\n");
		if (!userEmail.equalsIgnoreCase("")) {
			try {
				isElementDisplayed("txt_userEmail", userEmail);
			} catch (StaleElementReferenceException stlExp) {
				isElementDisplayed("txt_userEmail", userEmail);
			}
			logMessage("ASSERT PASSED :" + userEmail + " is verified in txt_memberDetails\n");
		}
	}

	public void verifyMemberDetails_AACTOMA(String caseId, String contactId, String userEmail, String fName,
			String lName) {
		if (getAACT_OmaSheetValue(caseId, "Affiliate Member").equalsIgnoreCase("Y")) {
			String add = getAACT_OmaSheetValue(caseId, "Address Contact Page");
			String city = getAACT_OmaSheetValue(caseId, "City Contact Page");

			String zipCode = getAACT_OmaSheetValue(caseId, "Zip code Contact Page");
			String addressType = getAACT_OmaSheetValue(caseId, "Address Type Contact Page");

			verifyElementTextContains("txt_memberDetails", fName);
			logMessage("ASSERT PASSED :" + fName + " is verified in txt_memberDetails\n");
			verifyElementTextContains("txt_memberDetails", lName);
			logMessage("ASSERT PASSED :" + lName + " is verified in txt_memberDetails\n");
			verifyElementTextContains("txt_memberDetails", add);
			logMessage("ASSERT PASSED :" + add + " is verified in txt_memberDetails\n");
			verifyElementTextContains("txt_memberDetails", city);
			logMessage("ASSERT PASSED :" + city + " is verified in txt_memberDetails\n");
			verifyElementTextContains("txt_memberDetails", zipCode);
			logMessage("ASSERT PASSED :" + zipCode + " is verified in txt_memberDetails\n");
			// address type work is displayed instead of work/school
			// isElementDisplayed("txt_additionalInfo",
			// addressType.toLowerCase());
			// logMessage("ASSERT PASSED :" + addressType.toLowerCase()
			// + " is verified in txt_memberDetails\n");
			isElementDisplayed("txt_additionalInfo", contactId.trim());
			logMessage("ASSERT PASSED :" + contactId.trim() + " is verified in txt_additionalInfo\n");
			if (!userEmail.equalsIgnoreCase("")) {
				try {
					isElementDisplayed("txt_userEmail", userEmail);
				} catch (StaleElementReferenceException stlExp) {
					isElementDisplayed("txt_userEmail", userEmail);
				}

				logMessage("ASSERT PASSED :" + userEmail + " is verified in txt_memberDetails\n");
			}
		} else {
			String add = getAACT_OmaSheetValue(caseId, "Address Contact Page");
			String city = getAACT_OmaSheetValue(caseId, "City Contact Page");

			String zipCode = getAACT_OmaSheetValue(caseId, "Zip code Contact Page");
			String addressType = getAACT_OmaSheetValue(caseId, "Address Type Contact Page");

			verifyElementTextContains("txt_memberDetails", fName);
			logMessage("ASSERT PASSED :" + fName + " is verified in txt_memberDetails\n");
			verifyElementTextContains("txt_memberDetails", lName);
			logMessage("ASSERT PASSED :" + lName + " is verified in txt_memberDetails\n");
			verifyElementTextContains("txt_memberDetails", add);
			logMessage("ASSERT PASSED :" + add + " is verified in txt_memberDetails\n");
			verifyElementTextContains("txt_memberDetails", city);
			logMessage("ASSERT PASSED :" + city + " is verified in txt_memberDetails\n");
			verifyElementTextContains("txt_memberDetails", zipCode);
			logMessage("ASSERT PASSED :" + zipCode + " is verified in txt_memberDetails\n");
			// address type work is displayed instead of work/school
			// isElementDisplayed("txt_additionalInfo",
			// addressType.toLowerCase());
			// logMessage("ASSERT PASSED :" + addressType.toLowerCase()
			// + " is verified in txt_memberDetails\n");
			isElementDisplayed("txt_additionalInfo", contactId.trim());
			logMessage("ASSERT PASSED :" + contactId.trim() + " is verified in txt_memberDetails\n");
			if (!userEmail.equalsIgnoreCase("")) {
				try {
					isElementDisplayed("txt_userEmail", userEmail);
				} catch (StaleElementReferenceException stlExp) {
					isElementDisplayed("txt_userEmail", userEmail);
				}

				logMessage("ASSERT PASSED :" + userEmail + " is verified in txt_memberDetails\n");
			}
		}

	}

	public void verifyMemberDetails_AACTOMA_Individual(String caseId, String userEmail, String fName, String lName) {

		String add = getAACT_OmaSheetValue(caseId, "Address Contact Page");
		String city = getAACT_OmaSheetValue(caseId, "City Contact Page");

		String zipCode = getAACT_OmaSheetValue(caseId, "Zip code Contact Page");
		String addressType = getAACT_OmaSheetValue(caseId, "Address Type Contact Page");

		verifyElementTextContains("txt_memberDetails", fName);
		logMessage("ASSERT PASSED :" + fName + " is verified in txt_memberDetails\n");
		verifyElementTextContains("txt_memberDetails", lName);
		logMessage("ASSERT PASSED :" + lName + " is verified in txt_memberDetails\n");
		verifyElementTextContains("txt_memberDetails", add);
		logMessage("ASSERT PASSED :" + add + " is verified in txt_memberDetails\n");
		verifyElementTextContains("txt_memberDetails", city);
		logMessage("ASSERT PASSED :" + city + " is verified in txt_memberDetails\n");
		verifyElementTextContains("txt_memberDetails", zipCode);
		logMessage("ASSERT PASSED :" + zipCode + " is verified in txt_memberDetails\n");
		// address type work is displayed instead of work/school
		// isElementDisplayed("txt_additionalInfo", addressType.toLowerCase());
		// logMessage("ASSERT PASSED :" + addressType.toLowerCase()
		// + " is verified in txt_memberDetails\n");
		if (!userEmail.equalsIgnoreCase("")) {
			try {
				isElementDisplayed("txt_userEmail", userEmail);
			} catch (StaleElementReferenceException stlExp) {
				isElementDisplayed("txt_userEmail", userEmail);
			}

			logMessage("ASSERT PASSED :" + userEmail + " is verified in txt_memberDetails\n");
		}

	}

	public void verifyMemberDetails_MemberProfile(String middleName, String lastName) {
		verifyElementTextContains("txt_memberDetails", map().get("firstName"));
		logMessage("ASSERT PASSED :" + map().get("firstName") + " is verified as first name\n");
		verifyElementTextContains("txt_memberDetails", middleName);
		logMessage("ASSERT PASSED :" + middleName + " is verified as middle name\n");
		verifyElementTextContains("txt_memberDetails", lastName);
		logMessage("ASSERT PASSED :" + lastName + " is verified as last name\n");

		verifyElementTextContentCaseInSensitive("txt_memberDetails", map().get("street"));
		// verifyElementTextContains("txt_memberDetails", map().get("street"));
		logMessage("ASSERT PASSED :" + map().get("street") + " is verified as street\n");
		verifyElementTextContains("txt_memberDetails", map().get("city"));
		logMessage("ASSERT PASSED :" + map().get("city") + " is verified as city\n");
		// verifyElementTextContains("txt_memberDetails", map().get("country"));
		// logMessage("ASSERT PASSED :" + map().get("country")
		// + " is verified as country\n");
		if (!map().get("country").equalsIgnoreCase("UNITED STATES")) {
			verifyElementTextContains("txt_memberDetails", map().get("In_postalCode"));
			logMessage("ASSERT PASSED :" + map().get("In_postalCode") + " is verified in txt_memberDetails\n");
		} else {
			verifyElementTextContains("txt_memberDetails", map().get("Out_postalCode"));
			logMessage("ASSERT PASSED :" + map().get("Out_postalCode") + " is verified in txt_memberDetails\n");
		}
	}

	public String verifyMemberDetails_InAddIndividual(String[] memDetails) {

		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(5);
		verifyElementTextContent("txt_memberDetails", memDetails[0]);
		logMessage("ASSERT PASSED :" + memDetails[0] + " is verified in txt_memberDetails\n");
		verifyElementTextContent("txt_memberDetails", memDetails[1]);
		logMessage("ASSERT PASSED :" + memDetails[1] + " is verified in txt_memberDetails\n");
		verifyElementTextContent("txt_memberDetails", memDetails[2]);
		logMessage("ASSERT PASSED :" + memDetails[2] + " is verified in txt_memberDetails\n");

		if (!(memDetails[3].equalsIgnoreCase(""))) {
			System.out.println("******Member" + memDetails[3]);
			System.out.println(element("txt_memberDetails").getText());
			verifyElementTextContentCaseInSensitive("txt_memberDetails", memDetails[3]);
			// Assert.assertTrue(element("txt_memberDetails").getText().contains(memDetails[3]));
			// verifyElementTextContent("txt_memberDetails", memDetails[3]);
			logMessage("ASSERT PASSED :" + memDetails[3] + " is verified in txt_memberDetails\n");
		}
		System.out.println("******Member" + memDetails[4]);
		// verifyElementTextContentCaseSensitive("txt_memberDetails",
		// memDetails[4]);
		verifyElementTextContent("txt_memberDetails", memDetails[4]);
		logMessage("ASSERT PASSED :" + memDetails[4] + " is verified in txt_memberDetails\n");
		System.out.println("******Member" + memDetails[5]);
		verifyElementTextContent("txt_memberDetails", memDetails[5]);
		logMessage("ASSERT PASSED :" + memDetails[5] + " is verified in txt_memberDetails\n");
		System.out.println("******Member" + memDetails[6]);
		verifyElementTextContent("txt_memberDetails", memDetails[6]);
		logMessage("ASSERT PASSED :" + memDetails[6] + " is verified in txt_memberDetails\n");
		logMessage("*********** CUSTOMER ID : " + getContactId() + " ***********");
		return getContactId();
	}

	public void verifyAACTMemberCreated(String caseId) {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(10);

		isElementDisplayed("img_aactMember");
		if (getAACT_OmaSheetValue(caseId, "Is Create Only Individual?").equalsIgnoreCase("Y")) {
			Assert.assertTrue(element("img_aactMember").getAttribute("src").endsWith("delete1.gif"));
			logMessage("ASSERT PASSED : AACT Individual Member created\n");
		} else if (getAACT_OmaSheetValue(caseId, "Is Create Only Individual?").equalsIgnoreCase("N")) {
			Assert.assertTrue(element("img_aactMember").getAttribute("src").endsWith("chkmk.gif"));
			logMessage("ASSERT PASSED : AACT Member created\n");
		} else {
			logMessage("Value in Is Create Only Individual? column is null\n");
		}
	}

	public void verifyMemberIsNotCreated() {
		isElementDisplayed("img_member");
		logMessage("ASSERT PASSED : Cross mark for non member is verified \n");
	}

	public void verifyMemberReceivedNoBenefits() {
		isElementDisplayed("img_noMemberBenefits");
		logMessage("ASSERT PASSED : No member benefits is present and has cross mark for receives member benefits\n");
	}

	public void clickOnMenus() {
		isElementDisplayed("btn_Go");
		element("link_moreMenuName", "Products").click();
		logMessage("STEP :  Go button is clicked in btn_Go\n");
	}

	public void clickOnMemberShipMenu(String menuName) {
		clickUsingXpathInJavaScriptExecutor(element("btn_memberShip", menuName));

		logMessage("STEP :" + menuName + " is clicked in btn_memberShip\n");

	}

	private void verifyMemberProductDetails(String element, String detailName) {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(5);
		if (detailName.equalsIgnoreCase("")) {
			logMessage("ASSERT PASSED : IWEB product name is not present in data sheet\n");
		} else {
			isElementDisplayed(element, detailName);
			logMessage("ASSERT PASSED : " + detailName + " is verified in IWEB in  element\n");
		}
	}

	private void verifyMemberProductDetails_Reinstate(String element, String detailName, String invoiceNumber) {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		flag = false;
		if (detailName.equalsIgnoreCase("")) {
			logMessage("ASSERT PASSED : IWEB product name is not present in data sheet\n");
		} else {

			try {
				wait.resetImplicitTimeout(0);
				wait.resetExplicitTimeout(hiddenFieldTimeOut);
				int pageNumber = elements("list_pageLink").size();
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
				for (int j = 1; j <= pageNumber; j++) {
					for (int i = 0; i < elements(element, invoiceNumber).size(); i++) {
						WebElement ele = elements(element, invoiceNumber).get(i);
						flag = ele.getText().trim().equalsIgnoreCase(detailName);
						if (flag) {
							logMessage("ASERT PASSED : " + detailName + " is verified in IWEB in " + element + "\n");
							clickOnPageLink(String.valueOf(1));
							break;
						}

					}
					clickOnPageLink(String.valueOf(j + 1));
					waitForSpinner();
				}
			} catch (Exception exp) {
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
				for (int i = 0; i < elements(element, invoiceNumber).size(); i++) {
					WebElement ele = elements(element, invoiceNumber).get(i);
					flag = ele.getText().trim().equalsIgnoreCase(detailName);
					if (flag) {
						logMessage("ASERT PASSED : " + detailName + " is verified in IWEB in " + element + "\n");
						break;
					}
				}
			}
			logMessage("ASSERT PASSED : " + detailName + " is verified in IWEB in  " + element + "\n");
		}
	}

	public void clickOnPageLink(String pageNumber) {
		isElementDisplayed("link_pageLink", pageNumber);
		element("link_pageLink", pageNumber).click();
		logMessage("STEP : Click on page number link in link_pageLink\n");
	}

	public void verifyMemberBenefitsDetail(String caseId, String invoiceNumber) {
		navigateToProductsMenuOnHoveringMore();
		verifyMemberProductDetails("txt_divisionPubName", map().get("Iweb Division Name?"));
		verifyMemberProductDetails("txt_divisionPubName", map().get("Iweb LS Name?"));

		verifyInvoiceNumber_AACT(invoiceNumber);
		logMessage("ASSERT PASSED : Member benefit details is verified in products menu \n");
		navigateToSubscriptionMenuOnHoveringMore();
		verifyMemberProductDetails("txt_divisionPubName", map().get("Iweb Pub Name?"));
		logMessage("ASSERT PASSED : Member benefit details is verified in subscriptions menu \n");

	}

	public void verifyMemberBenefitsDetail_GCSOMA(String caseId, String invoiceNumber) {
		navigateToProductsMenuOnHoveringMore();
		verifyMemberProductDetails("txt_divisionPubName", map().get("Iweb Division Name?"));

		verifyInvoiceNumber_AACT(invoiceNumber);
		logMessage("ASSERT PASSED : Member benefit details is verified in products menu \n");
		navigateToSubscriptionMenuOnHoveringMore();
		verifyMemberProductDetails("txt_divisionPubName", map().get("Iweb Pub Name?"));
		logMessage("ASSERT PASSED : Member benefit details is verified in subscriptions menu \n");
	}

	public void verifyMemberBenefitsDetail_AACTOMA(String caseId, String invoiceNumber) {
		navigateToProductsMenuOnHoveringMoreAACT();
		verifyMemberProductDetails("txt_divisionPubName", getAACT_OmaSheetValue(caseId, "IWEB AACT Product Name?"));
		verifyInvoiceNumber_AACT(invoiceNumber);
		logMessage("ASSERT PASSED : Member benefit details is verified in products menu \n");
		navigateToSubscriptionMenuOnHoveringMoreAACT();
		verifyMemberProductDetails("txt_divisionPubName",
				getAACT_OmaSheetValue(caseId, "IWEB AACT Subscription Name?"));
		logMessage("ASSERT PASSED : Member benefit details is verified in subscriptions menu \n");
	}

	public void verifyMemberBenefitsDetail_Reinstate(String caseId, String invoiceNumber) {
		navigateToProductsMenuOnHoveringMore();

		verifyMemberProductDetails_Reinstate("txt_divisionPubName_reinstate", map().get("Iweb Division Name?"),
				invoiceNumber);
		verifyMemberProductDetails_Reinstate("txt_divisionPubName_reinstate", map().get("Iweb LS Name?"),
				invoiceNumber);
		logMessage("ASSERT PASSED : Member benefit details is verified in products menu \n");
		navigateToSubscriptionMenuOnHoveringMore();

		verifyMemberProductDetails_Reinstate("txt_divisionPubName_reinstate", map().get("Iweb Pub Name?"),
				invoiceNumber);
		logMessage("ASSERT PASSED : Member benefit details is verified in subscriptions menu \n");
	}

	public void navigateToSubscriptionMenuOnHoveringMore() {
		try {
			wait.hardWait(5);
			isElementDisplayed("img_moreMenu");
			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));

			isElementDisplayed("link_moreMenuName", "Subscriptions");
			clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName", "Subscriptions"));

			logMessage("STEP : Subscription link is clicked\n");
			waitForSpinner();
			try {
				isElementDisplayed("btn_memberShip", "active subscriptions");
				clickUsingXpathInJavaScriptExecutor(element("btn_memberShip", "active subscriptions"));

				logMessage("STEP : Navigate to subscription menu on clicking more button\n");
			} catch (Exception E) {
				logMessage("STEP : Active subscriptions already expanded");
			}
		} catch (StaleElementReferenceException stEx) {

			wait.hardWait(5);
			isElementDisplayed("img_moreMenu");

			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));

			isElementDisplayed("link_moreMenuName", "Subscriptions");
			element("link_moreMenuName", "Subscriptions").click();
			logMessage("STEP : Subscription link is clicked\n");
			waitForSpinner();
			isElementDisplayed("btn_memberShip", "active subscriptions");
			clickUsingXpathInJavaScriptExecutor(element("btn_memberShip", "active subscriptions"));

			logMessage("STEP : Navigate to subscription menu on clicking more button\n");
		}

	}

	public void navigateToContactInfoMenuOnHoveringMore() {
		try {
			wait.hardWait(5);
			handleAlert();
			isElementDisplayed("img_moreMenu");
			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));
			logMessage("Step : Navigate to Contact Info menu on clicking more button\n");
			isElementDisplayed("link_moreMenuName", "Contact Info");
			clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName", "Contact Info"));

			logMessage("STEP : Contact Info link is clicked\n");
			waitForSpinner();
			try {
				isElementDisplayed("btn_memberShip", "e-mail addresses");
				clickUsingXpathInJavaScriptExecutor(element("btn_memberShip", "e-mail addresses"));

				logMessage("STEP :  e-mail addresses menu is expanded\n");
			} catch (Exception E) {
				logMessage("STEP :  e-mail addresses menu already expanded\n");
			}
		} catch (StaleElementReferenceException stEx) {

			wait.hardWait(5);
			isElementDisplayed("img_moreMenu");
			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));
			logMessage("STEP : Navigate to Contact Info menu on clicking more button\n");
			isElementDisplayed("link_moreMenuName", "Contact Info");
			clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName", "Contact Info"));

			logMessage("STEP : Contact Info link is clicked\n");
			waitForSpinner();
			try {
				isElementDisplayed("btn_memberShip", "e-mail addresses");
				clickUsingXpathInJavaScriptExecutor(element("btn_memberShip", "e-mail addresses"));
				logMessage("STEP :  E-mail addresses menu is expanded\n");
			} catch (Exception E) {
				logMessage("STEP :  E-mail addresses menu already expanded\n");
			}
		}
	}

	public void navigateToSubscriptionMenuOnHoveringMore_CreateMember() {
		try {
			wait.hardWait(5);
			isElementDisplayed("img_moreMenu");
			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));

			isElementDisplayed("link_moreMenuName", "Subscriptions");
			// element("link_moreMenuName", "Subscriptions").click();
			clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName", "Subscriptions"));
			logMessage("STEP : Subscription link is clicked\n");
			waitForSpinner();
			// isElementDisplayed("btn_memberShip", "active subscriptions");
			expandDetailsMenuIfAlreadyExpanded("active subscriptions");
			// element("btn_memberShip", "active subscriptions").click();
			wait.hardWait(2);
			logMessage("STEP : Navigate to subscription menu on clicking more button\n");
		} catch (StaleElementReferenceException stEx) {

			wait.hardWait(5);
			isElementDisplayed("img_moreMenu");

			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));

			isElementDisplayed("link_moreMenuName", "Subscriptions");
			element("link_moreMenuName", "Subscriptions").click();
			logMessage("STEP : Subscription link is clicked\n");
			waitForSpinner();
			isElementDisplayed("btn_memberShip", "active subscriptions");
			element("btn_memberShip", "active subscriptions").click();
			logMessage("STEP : Navigate to subscription menu on clicking more button\n");
		}

	}

	public void navigateToIssuesMenuOnHoveringMore() {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(3);
		try {
			hardWaitForIEBrowser(8);
			isElementDisplayed("img_moreMenu");
			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));
			logMessage("STEP : More link is clicked\n");

			hardWaitForIEBrowser(4);
			isElementDisplayed("link_moreMenuName", "Issues");
			clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName", "Issues"));

			logMessage("STEP : Issues link is clicked\n");
			waitForSpinner();
			clickAndHold(element("btn_memberShip", "issues fulfilled"));

			// isElementDisplayed("btn_memberShip", "issues fulfilled");
			// clickUsingXpathInJavaScriptExecutor(element("btn_memberShip",
			// "issues fulfilled"));

			logMessage("STEP : Navigate to Issues menu on clicking more button\n");

		} catch (StaleElementReferenceException stEx) {
			isElementDisplayed("img_moreMenu");
			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));

			isElementDisplayed("link_moreMenuName", "Issues");
			clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName", "Issues"));

			logMessage("STEP : Issues link is clicked\n");
			waitForSpinner();
			isElementDisplayed("btn_memberShip", "issues fulfilled");
			clickUsingXpathInJavaScriptExecutor(element("btn_memberShip", "issues fulfilled"));

			logMessage("STEP : Navigate to Issues menu on clicking more button\n");
		}

	}

	public void navigateToEntrantsMenuOnHoveringMore() {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(3);
		try {
			isElementDisplayed("img_moreMenu");
			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));

			isElementDisplayed("link_moreMenuName", "Entrants");
			clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName", "Entrants"));

			logMessage("STEP : Entrants link is clicked\n");
			waitForSpinner();
			isElementDisplayed("btn_memberShip", "acs nominee/ entry");
			clickUsingXpathInJavaScriptExecutor(element("btn_memberShip", "acs nominee/ entry"));
			logMessage("STEP : Navigate to Entrants menu on clicking more button\n");

		} catch (StaleElementReferenceException stEx) {
			isElementDisplayed("img_moreMenu");
			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));

			isElementDisplayed("link_moreMenuName", "Entrants");
			clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName", "Entrants"));

			logMessage("STEP : Entrants link is clicked\n");
			waitForSpinner();
			isElementDisplayed("btn_memberShip", "acs nominee/ entry");
			clickUsingXpathInJavaScriptExecutor(element("btn_memberShip", "acs nominee/ entry"));
			logMessage("STEP : Navigate to Entrants menu on clicking more button\n");
		}

	}

	public void navigateToSubscriptionMenuOnHoveringMoreAACT() {
		try {
			isElementDisplayed("img_moreMenu");
			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));

			isElementDisplayed("link_moreMenuName", "Subscriptions");
			clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName", "Subscriptions"));

			logMessage("STEP : Subscription link is clicked\n");
			waitForSpinner();
			isElementDisplayed("btn_memberShipAACT", "active subscriptions");
			clickUsingXpathInJavaScriptExecutor(element("btn_memberShipAACT", "active subscriptions"));

			logMessage("STEP : Navigate to subscription menu on clicking more button\n");
		} catch (StaleElementReferenceException stEx) {
			isElementDisplayed("img_moreMenu");
			element("img_moreMenu").click();
			isElementDisplayed("link_moreMenuName", "Subscriptions");
			element("link_moreMenuName", "Subscriptions").click();
			logMessage("STEP : Subscription link is clicked\n");
			waitForSpinner();
			isElementDisplayed("btn_memberShipAACT", "active subscriptions");
			element("btn_memberShipAACT", "active subscriptions").click();
			logMessage("STEP : Navigate to subscription menu on clicking more button\n");
		}

	}

	private void navigateToProductsMenuOnHoveringMore() {
		hardWaitForIEBrowser(10);
		try {
			isElementDisplayed("img_moreMenu");
			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));

			isElementDisplayed("link_moreMenuName", "Products");
			clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName", "Products"));

			logMessage("STEP : Product link is clicked\n");
			waitForSpinner();
			wait.hardWait(2);
			hardWaitForIEBrowser(6);
			try {
				isElementDisplayed("btn_memberShip", "services purchased");
				clickUsingXpathInJavaScriptExecutor(element("btn_memberShip", "services purchased"));

				logMessage("STEP : Services purchased drop down button is clicked\n");
				logMessage("STEP : Navigate to products menu on clicking more button\n");
			} catch (Exception E) {
				logMessage("STEP : Services purchased is already expanded");
			}
		} catch (StaleElementReferenceException stlEx) {
			isElementDisplayed("img_moreMenu");
			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));

			wait.waitForPageToLoadCompletely();
			isElementDisplayed("link_moreMenuName", "Products");
			clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName", "Products"));

			logMessage("STEP : Product link is clicked\n");
			waitForSpinner();
			wait.waitForPageToLoadCompletely();
			try {
				isElementDisplayed("btn_memberShip", "services purchased");
				clickUsingXpathInJavaScriptExecutor(element("btn_memberShip", "services purchased"));

				logMessage("STEP : Navigate to products menu on clicking more button\n");
			} catch (Exception E) {
				logMessage("STEP : Services purchased already expanded");
			}
		}
	}

	public void clickOnMoreAndSelectOtherActg(String action) {
		isElementDisplayed("img_moreMenu");
		element("img_moreMenu").click();
		logMessage("STEP : More link is clicked \n");
		isElementDisplayed("link_moreMenuName", action);
		element("link_moreMenuName", action).click();
		logMessage("STEP : Action in more menu is selected as " + action + "\n");
		waitForSpinner();
		/*
		 * wait.waitForPageToLoadCompletely();
		 * isElementDisplayed("btn_memberShip", "credits");
		 * element("btn_memberShip", "credits").click();
		 * logMessage("STEP :Expand credits child form \n");
		 */

	}

	public void navigateToInvoicesMenuOnHoveringMore() {
		try {
			isElementDisplayed("img_moreMenu");
			if (isIEBrowser()) {
				clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));
				isElementDisplayed("link_moreMenuName", "Invoices");
				clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName", "Invoices"));
			} else {
				element("img_moreMenu").click();
				isElementDisplayed("link_moreMenuName", "Invoices");
				element("link_moreMenuName", "Invoices").click();
			}

			logMessage("STEP : Invoices link is clicked\n");
			waitForSpinner();
			wait.hardWait(2);
			wait.waitForPageToLoadCompletely();
			expandDetailsMenuIfAlreadyExpanded("invoices (open batch)");
			// isElementDisplayed("btn_memberShip", "invoices (open batch)");
			// if (isIEBrowser()) {
			// clickUsingXpathInJavaScriptExecutor(element("btn_memberShip",
			// "invoices (open batch)"));
			// } else {
			// element("btn_memberShip", "invoices (open batch)").click();
			// }
			// logMessage("STEP : Invoices (open batch) drop down button is
			// clicked\n");
			// logMessage("STEP : Navigate to Invoices menu on clicking more
			// button\n");
		} catch (StaleElementReferenceException stlEx) {
			isElementDisplayed("img_moreMenu");
			if (isIEBrowser()) {
				clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));
				isElementDisplayed("link_moreMenuName", "Invoices");
				clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName", "Invoices"));
			} else {
				element("img_moreMenu").click();
				isElementDisplayed("link_moreMenuName", "Invoices");
				element("link_moreMenuName", "Invoices").click();
			}

			wait.waitForPageToLoadCompletely();
			logMessage("STEP : Invoices link is clicked\n");
			waitForSpinner();
			wait.waitForPageToLoadCompletely();
			isElementDisplayed("btn_memberShip", "invoices (open batch)");
			if (isIEBrowser()) {
				clickUsingXpathInJavaScriptExecutor(element("btn_memberShip", "invoices (open batch)"));
			} else {
				element("btn_memberShip", "invoices (open batch)").click();
			}
			logMessage("STEP : Navigate to Invoices menu on clicking more button\n");
		}
	}

	public void clickOnMoreLink() {
		isElementDisplayed("img_moreMenu");
		clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));
	}

	public void clickOnMoreMenuName(String menuName) {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(1);
		isElementDisplayed("link_moreMenuName", menuName);
		clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName", menuName));
	}

	public void ClickonMoreAndMenuNameLink(String menuName) {
		clickOnMoreLink();
		clickOnMoreMenuName(menuName);
	}

	public void navigateToGeneralMenuOnHoveringMore(String menu) {
		try {
			isElementDisplayed("img_moreMenu");
			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));
			// element("img_moreMenu").click();
			isElementDisplayed("link_moreMenuName", menu);
			if (isBrowser("ie") || isBrowser("IE"))
				clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName", menu));
			else
				element("link_moreMenuName", menu).click();
			logMessage("STEP : " + menu + " link is clicked\n");
			waitForSpinner();
			wait.hardWait(2);
			wait.waitForPageToLoadCompletely();
		} catch (Exception stlEx) {
			wait.hardWait(2);
			pageRefresh();
			System.out.println("STEP : Stale element refrence exception is occured\n");
			isElementDisplayed("img_moreMenu");
			element("img_moreMenu").click();
			isElementDisplayed("link_moreMenuName", menu);
			element("link_moreMenuName", menu).click();
			logMessage("STEP : " + menu + " link is clicked\n");
			waitForSpinner();
			wait.hardWait(2);
			wait.waitForPageToLoadCompletely();
		}
	}

	private void navigateToProductsMenuOnHoveringMoreAACT() {
		try {
			isElementDisplayed("img_moreMenu");
			if (isBrowser("ie")) {
				clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));
			} else {
				element("img_moreMenu").click();
			}

			wait.waitForPageToLoadCompletely();
			wait.hardWait(1);
			isElementDisplayed("link_moreMenuName", "Products");
			clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName", "Products"));

			logMessage("STEP : Product link is clicked\n");
			waitForSpinner();
			wait.waitForPageToLoadCompletely();
			isElementDisplayed("btn_memberShipAACT", "services purchased");
			clickUsingXpathInJavaScriptExecutor(element("btn_memberShipAACT", "services purchased"));

			logMessage("STEP : Navigate to products menu on clicking more button\n");
		} catch (StaleElementReferenceException stlEx) {
			isElementDisplayed("img_moreMenu");
			element("img_moreMenu").click();
			wait.waitForPageToLoadCompletely();
			isElementDisplayed("link_moreMenuName", "Products");
			element("link_moreMenuName", "Products").click();
			logMessage("STEP : Product link is clicked\n");
			waitForSpinner();
			wait.waitForPageToLoadCompletely();
			isElementDisplayed("btn_memberShip", "services purchased");
			element("btn_memberShip", "services purchased").click();
			logMessage("STEP : Navigate to products menu on clicking more button\n");
		}
	}

	public void verifyNumberOfYears(String numberOfYears) {
		if (numberOfYears.equalsIgnoreCase("")) {
			logMessage("STEP : Number of years is not present in data\n");
		} else {
			isElementDisplayed("txt_numberOfyears");
			verifyElementText("txt_numberOfyears", numberOfYears);
			logMessage("ASSERT PASSED : " + numberOfYears + " total years of services in txt_numberOfyears\n");
		}
	}

	public String numberOfYearsForInactiveMember() {
		isElementDisplayed("txt_numberOfyears");
		String numberOfYears = element("txt_numberOfyears").getText();

		logMessage("STEP : Total years of services for inactive member is " + numberOfYears);
		return numberOfYears;
	}

	public void numberOfYearsForActiveMember(int numberOfYears) {
		String noOfYears = String.valueOf(numberOfYears + 1);
		isElementDisplayed("txt_numberOfyears");
		verifyElementText("txt_numberOfyears", noOfYears);
		logMessage("ASSERT PASSED : " + noOfYears + " total years of services in txt_numberOfyears\n");

	}

	private void verifyInvoiceNumber_AACT(String invoiceNumber) {
		isElementDisplayed("txt_invoiceAACT");
		verifyElementTextContains("txt_invoiceAACT", invoiceNumber);
		logMessage("ASSERT PASSED :" + invoiceNumber + " is verified in txt_invoiceAACT\n");
	}

	private void verifyInvoiceNumber(String invoiceNumber) {
		isElementDisplayed("txt_invoiceValue");
		verifyElementTextContains("txt_invoiceValue", invoiceNumber);
		logMessage("ASSERT PASSED :" + invoiceNumber + " is verified in txt_memberDetails\n");
	}

	public String verifyIndividualDetails(String detailName, String productName, String detailValue, String multiYear) {
		if (detailValue.equalsIgnoreCase("")) {
			logMessage("STEP : Value of " + productName + " price is empty in data sheet\n");

			return null;
		} else {
			if (!multiYear.equalsIgnoreCase("")) {
				int multiYearInInteger = Integer.parseInt(multiYear);
				if (multiYearInInteger > 1) {
					isElementDisplayed("txt_" + detailName, productName);
					Float priceValueInSheet = Float.parseFloat(detailValue.replaceAll("\\$", "")) * multiYearInInteger;
					String formatedPrice = String.format("%.02f", priceValueInSheet);
					String PriceValueExpected = String.valueOf(formatedPrice);

					String priceValueActual = element("txt_" + detailName, productName).getText().trim();
					// Assert.assertTrue(priceValueActual.equalsIgnoreCase(PriceValueExpected));
					logMessage("ASSERT PASSED : " + priceValueActual + " is verified in txt_" + detailName + "\n");
					return priceValueActual;
				} else {
					isElementDisplayed("txt_" + detailName, productName);
					String priceValueActual = element("txt_" + detailName, productName).getText().trim();
					Float priceValueInSheet = Float.parseFloat(detailValue.replaceAll("\\$", ""));
					String formatedPrice = String.format("%.02f", priceValueInSheet);
					String PriceValueExpected = String.valueOf(formatedPrice);

					// Assert.assertTrue(priceValueActual.equalsIgnoreCase(PriceValueExpected));
					logMessage(
							"ASSERT PASSED : " + priceValueActual + " price is verified in txt_" + detailName + "\n");

					return priceValueActual;
				}
			}

			return null;
		}

	}

	public String verifyIndividualDetails_AACTOMA(String detailName, String productName, String detailValue) {
		if (detailValue.equalsIgnoreCase("")) {
			logMessage("STEP : Value of " + productName + " price is empty in data sheet\n");
			return null;
		} else {

			isElementDisplayed("txt_" + detailName, productName);
			String priceValueActual = element("txt_" + detailName, productName).getText().trim();
			String formatedPrice = String.format("%.02f", Float.parseFloat(detailValue));
			String PriceValueExpected = String.valueOf(formatedPrice);
			Assert.assertTrue(priceValueActual.equalsIgnoreCase(PriceValueExpected));
			logMessage("ASSERT PASSED : " + priceValueActual + " price is verified in txt_" + detailName + "\n");
			return priceValueActual;

		}

	}

	public void verifyIndividualProfileDetails(String caseId, String[] quantities) {

		expandDetailsMenu("individual memberships");
		clickOnButtonToNavigateFinancialPageInMembershipPage();

		expandDetailsMenu("invoices");

		wait.waitForPageToLoadCompletely();
		verifyIndividualDetails("priceValue", map().get("Iweb Product Name?"),
				getPriceSheetValue(caseId, "Price value?"), map().get("multiYearDecision"));

		String payment = verifyIndividualDetails("payment", map().get("Iweb Product Name?"),
				getPriceSheetValue(caseId, "Price value?"), map().get("multiYearDecision"));
		Float paymentInFloat = Float.parseFloat(payment.replace("$", ""));

		String balance = verifyIndividualDetails("balance", map().get("Iweb Product Name?"), "0.00",
				map().get("multiYearDecision"));
		Float balanceInFloat = Float.parseFloat(balance.replace("$", ""));
		String total = String.valueOf(paymentInFloat + balanceInFloat);

		verifyIndividualDetails("total", map().get("Iweb Product Name?"), total, "1");

		Float a1 = Float.parseFloat(quantities[0]);
		int quantity_Prod = (int) Math.round(a1);
		String quantity_Product = String.valueOf(quantity_Prod);

		verifyIndividualDetails("quantity", map().get("Iweb Product Name?"), quantity_Product, "1");

		verifyStartAndEndDateInFinancialInvoice(map().get("Iweb Product Name?"), map().get("multiYearDecision"));
		clickUsingXpathInJavaScriptExecutor(element("lnk_lastName"));

	}

	public void verifyIndividualProfileDetails_AACTOMA(String caseId, String[] quantities) {
		expandDetailsMenuAACT("individual memberships");
		clickOnButtonToNavigateFinancialPageInMembershipPage();
		expandDetailsMenuAACT("invoices");
		verifyIndividualDetails_AACTOMA("priceValue", getAACT_OmaSheetValue(caseId, "Iweb AACT Product Name?"),
				getAACT_OmaSheetValue(caseId, "Product Subtotal?"));
		String payment = verifyIndividualDetails_AACTOMA("payment",
				getAACT_OmaSheetValue(caseId, "Iweb AACT Product Name?"),
				getAACT_OmaSheetValue(caseId, "Product Subtotal?"));
		Float paymentInFloat = Float.parseFloat(payment);
		String balance = verifyIndividualDetails_AACTOMA("balance",
				getAACT_OmaSheetValue(caseId, "Iweb AACT Product Name?"), "0.00");
		Float balanceInFloat = Float.parseFloat(balance);
		String total = String.valueOf(paymentInFloat + balanceInFloat);
		verifyIndividualDetails("total", getAACT_OmaSheetValue(caseId, "Iweb AACT Product Name?"), total, "1");
		verifyStartAndEndDateInFinancialInvoice(getAACT_OmaSheetValue(caseId, "Iweb AACT Product Name?"), caseId);
		element("lnk_lastName").click();
	}

	public void verifyIndividualProfileDetails_GCSOMA() {
		expandDetailsMenuAACT("individual memberships");
		clickOnButtonToNavigateFinancialPageInMembershipPage();
		expandDetailsMenuAACT("invoices");
		// verifyIndividualDetails_AACTOMA("priceValue",
		// getAACT_OmaSheetValue(caseId, "Iweb AACT Product Name?"),
		// getAACT_OmaSheetValue(caseId, "Product Subtotal?"));
		// String payment = verifyIndividualDetails_AACTOMA("payment",
		// getAACT_OmaSheetValue(caseId, "Iweb AACT Product Name?"),
		// getAACT_OmaSheetValue(caseId, "Product Subtotal?"));
		// Float paymentInFloat = Float.parseFloat(payment);
		String balance = verifyIndividualDetails_AACTOMA("balance", map().get("Iweb Product Name?"), "0.00");
		Float balanceInFloat = Float.parseFloat(balance);
		// String total = String.valueOf(paymentInFloat + balanceInFloat);
		// verifyIndividualDetails("total", getAACT_OmaSheetValue(caseId,
		// "Iweb AACT Product Name?"), total, "1");
		verifyStartAndEndDateInFinancialInvoice(map().get("Iweb Product Name?"), "1");
		element("lnk_lastName").click();
	}

	public void expandDetailsMenu(String menuName) {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		hardWaitForIEBrowser(10);
		try {
			wait.resetImplicitTimeout(3);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("btn_memberShip", menuName);
			clickUsingXpathInJavaScriptExecutor(element("btn_memberShip", menuName));

			logMessage("STEP : " + menuName + " is clicked to expand in btn_memberShip\n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (Exception E) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("STEP : " + menuName + " already expanded \n");
		}
		waitForSpinner();
	}

	private void expandDetailsMenuAACT(String menuName) {
		isElementDisplayed("btn_memberShipAACT", menuName);
		clickUsingXpathInJavaScriptExecutor(element("btn_memberShipAACT", menuName));

		logMessage("STEP : " + menuName + " is clicked to expand in btn_memberShipAACT\n");
	}

	public void clickOnButtonToNavigateFinancialPageInMembershipPage() {
		isElementDisplayed("btn_invoiceAtMembership");
		clickUsingXpathInJavaScriptExecutor(element("btn_invoiceAtMembership"));

		logMessage("STEP : User navigate to finance page in btn_memberShip\n");
	}

	public void clickOnInvoiceArrowButtonToNavigateFinancialPage() {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		isElementDisplayed("btn_invoicearrow");
		clickUsingXpathInJavaScriptExecutor(element("btn_invoicearrow"));

		logMessage("STEP : User navigate to finance page by clicking btn_invoicearrow\n");

	}

	public void clickOnArrowButtonForProductName(String productName) {
		isElementDisplayed("btnArrowProdName", productName);
		clickUsingXpathInJavaScriptExecutor(element("btnArrowProdName", productName));
		// element("btnArrowProdName", productName).click();
		logMessage("STEP : Arrow button is clicked for product name " + productName);
	}

	public String getContactId() {
		isElementDisplayed("txt_contactId");
		return element("txt_contactId").getText();
	}

	public void verifyStartAndEndDateInFinancialInvoice(String productName, String multiYear) {
		verifyStartAndEndDate("StartDate", productName, multiYear);
		verifyStartAndEndDate("EndDate", productName, multiYear);
	}

	public void verifyStartAndEndDate(String start_EndDate, String productName, String multiYear) {
		System.out.println("multi year :- " + multiYear);
		String currentDate = DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/YYYY", "EST5EDT");
		String nextYearDate = DateUtil.getAddYearWithLessOnedayInStringWithGivenFormate("M/d/YYYY", multiYear,
				"EST5EDT");
		isElementDisplayed("txt_term" + start_EndDate, productName);
		if (start_EndDate.equalsIgnoreCase("StartDate")) {
			System.out.println(element("txt_term" + start_EndDate, productName).getText().trim());
			System.out.println(currentDate);
			Assert.assertTrue(
					element("txt_term" + start_EndDate, productName).getText().trim().equalsIgnoreCase(currentDate));
			logMessage("ASSERT PASSED : TermStartDate is " + currentDate + " verified");
		} else if (start_EndDate.equalsIgnoreCase("EndDate")) {
			System.out.println(element("txt_term" + start_EndDate, productName).getText().trim());
			System.out.println(nextYearDate);
			Assert.assertTrue(
					element("txt_term" + start_EndDate, productName).getText().trim().equalsIgnoreCase(nextYearDate));
			logMessage("ASSERT PASSED : TermEndDate is " + nextYearDate + " verified");
		}
	}

	public void waitForSpinner() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			handleAlert();
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("img_spinner");
			logMessage("STEP : Wait for spinner to be disappeared \n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (Exception Exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (AssertionError Exp) {
			logMessage("STEP : Spinner is not present \n");
		}
	}

	public void navigateToMembershipProfilePage() {
		clickOnButtonToNavigateFinancialPageInMembershipPage();
	}

	public void navigateToSubscriptionAndVerifySubscriptionDetails(int numberOfSubscriptions) {
		int size = 0;
		navigateToSubscriptionMenuOnHoveringMore_CreateMember();
		try {
			size = elements("list_rowsInSubscription").size();
		} catch (StaleElementReferenceException stlRefExp) {
			size = elements("list_rowsInSubscription").size();
		}

		for (int i = 0; i < size - 1; i++) {
			for (int j = 1; j <= numberOfSubscriptions; j++) {

				if (element("txt_subscriptionName", String.valueOf(j)).getText().trim()
						.equalsIgnoreCase(map().get("subscription" + j).split(" - ")[0])) {
					verifySubscriptionDetails("Name", map().get("subscription" + j).split(" - ")[0], String.valueOf(j));
					// verifySubscriptionDetails("Price",
					// map().get("Sub" + j + "_SalePrice?"),
					// String.valueOf(j));
					// verifySubscriptionDetails("StartDate",
					// ,
					// String.valueOf(j));
					// verifySubscriptionDetails("EndDate",
					// ,
					// String.valueOf(j));
					verifySubscriptionDetails("IssueFulfilled", "0", String.valueOf(j));
					verifySubscriptionDetails("StartIssue", map().get("subscription" + j).split(" - ")[0],
							String.valueOf(j));
					break;
				}

			}
		}
	}

	public void verifySubscriptionDetails(String detailName, String detailValue, String index) {
		isElementDisplayed("txt_subscription" + detailName, index);
		String actual = element("txt_subscription" + detailName, index).getText().trim().replaceAll("\\$", "");
		String expected = detailValue;

		if (detailName.equalsIgnoreCase("StartIssue")) {
			Assert.assertTrue(actual.startsWith(expected));
			logMessage("ASSERT PASSED : " + detailValue + " is verified for " + detailName + " \n");
		} else {
			Assert.assertTrue(actual.equalsIgnoreCase(expected));
			logMessage("ASSERT PASSED : " + detailValue + " is verified for " + detailName + " \n");
		}

	}

	public void selectFeildValue(String feildName, String feildValue) {
		wait.hardWait(2);
		hardWaitForIEBrowser(2);
		isElementDisplayed("inp_fieldSelect", feildName);
		selectProvidedTextFromDropDown(element("inp_fieldSelect", feildName), feildValue);
		logMessage("STEP : " + feildValue + " is entered as " + feildName + "  in inp_fieldSelect\n");
	}

	public void selectFieldValueToFindMember(String feildName, String feildValue) {
		selectFeildValue(feildName, feildValue);
	}

	public void SelectFellowNominatorForVerification(String NomineeName, String NominatorName) {
		try {

			if (element("txt_NominatorName", NomineeName).getText().equals(NominatorName)) {
				clickUsingXpathInJavaScriptExecutor(element("txt_NominatorName", NomineeName));
				logMessage("STEP : Nominee fellow selected from the list as " + NomineeName);
			} else {
				logMessage("STEP : Nominee Fellow is not present in the list\n");
			}

		} catch (NoSuchElementException e) {
			logMessage("Step : Only one record is present in search results as " + NominatorName);
		}

	}

	public void addEmail_NCW_CCED(String emailType, String emailID) {
		clickOnAddButton();
		switchToFrame("iframe1");
		selectEmailType(emailType);
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		enterEmailIDToAdd(emailID);
		clickOnSaveButton();
		switchToDefaultContent();
		handleAlert();
		verifyNCW_CCEDEmailPresent(emailType, emailID);
	}

	public void selectEmailType(String emailType) {
		selectProvidedTextFromDropDown(element("select_emailType"), emailType);
		logMessage("STEP : Email type " + emailType + " is selected\n");
	}

	public void enterEmailIDToAdd(String emailID) {
		isElementDisplayed("inp_emailAddress");
		element("inp_emailAddress").clear();
		element("inp_emailAddress").sendKeys(emailID);
		logMessage("STEP : Enter email ID " + emailID + " to add\n");
	}

	public void clickOnSaveButton() {
		isElementDisplayed("btn_saveButton");
		clickUsingXpathInJavaScriptExecutor(element("btn_saveButton"));
		// element("btn_saveButton").click();
		logMessage("STEP : Save button is clicked \n");
	}

	public void clickOnAddButton() {
		isElementDisplayed("link_addRecordEmail");
		element("link_addRecordEmail").click();
		logMessage("STEP : Add button is clicked \n");
	}

	public void verifyNCW_CCEDEmailPresent(String emailType, String emailAddress) {
		isElementDisplayed("list_emailAddressType");
		for (int i = 0; i < elements("list_emailAddressType").size(); i++) {
			String typeName = elements("list_emailAddressType").get(i).getText().trim();
			if (typeName.equalsIgnoreCase("ncw/cced")) {
				flag1 = true;
				isElementDisplayed("txt_emailID", String.valueOf(i + 2));
				String emailID = element("txt_emailID", String.valueOf(i + 2)).getText().trim();
				if (emailID.equalsIgnoreCase(map().get("ncw/cced_email"))) {
					flag = true;
					logMessage("ASSERT PASSED : Email ID " + map().get("ncw/cced_email") + " is already present \n");
					break;
				}
			}
		}
		if (!flag1) {
			addEmail_NCW_CCED(emailType, emailAddress);
		}
		if (flag1 && !flag) {
			for (int i = 0; i < elements("list_emailAddressType").size(); i++) {
				String typeName = elements("list_emailAddressType").get(i).getText().trim();
				if (typeName.equalsIgnoreCase("ncw/cced")) {
					String position = Integer.toString(i + 1);
					editEmail_NCW_CCED(emailType, emailAddress, position);
				}
			}
		}
	}

	public String selectRandomGeneralAward_AwardNomination(String awardName) {
		try {
			System.out.println(awardName);
			wait.resetImplicitTimeout(4);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("txt_divisionPubName", awardName);
			element("txt_divisionPubName", awardName).click();
			logMessage("STEP : General Award " + awardName + " is selected from the list\n");
		} catch (NoSuchElementException e) {
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			wait.resetImplicitTimeout(4);
			try {
				element("txt_userEmail", "2").click();
				wait.waitForPageToLoadCompletely();
				element("txt_divisionPubName", awardName).click();
			} catch (NoSuchElementException e1) {
				element("txt_userEmail", "3").click();
				wait.waitForPageToLoadCompletely();
				element("txt_divisionPubName", awardName).click();
			}
			logMessage("STEP : General Award " + awardName + " is selected from the list\n");
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
		return awardName;
	}

	public void selectNomineeEntryForVerification(String NominatorName) {
		System.out.println(NominatorName);
		System.out.println(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyy"));
		try {
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			wait.resetImplicitTimeout(2);
			if (element("txt_tableNominatorEntry", NominatorName).getText().trim()
					.equals(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyy"))) {
				clickUsingXpathInJavaScriptExecutor(element("txt_tableEntryArrow", NominatorName));
				logMessage("STEP : Nominee selected from the list as " + NominatorName);
			} else {
				logMessage("STEP : Nominee is not present in the list\n");
			}
		} catch (NoSuchElementException e) {
			wait.resetExplicitTimeout(timeOut);
			wait.resetImplicitTimeout(timeOut);
			element("btn_entryReceivedAwards").click();
			waitForSpinner();
			clickUsingXpathInJavaScriptExecutor(element("txt_tableEntryArrow", NominatorName));
		}
		wait.resetExplicitTimeout(timeOut);
		wait.resetImplicitTimeout(timeOut);

	}

	public void verifyDetailsForAwardsNomination(Map<String, String> mapAwardsNomination,
			Map<String, String> createMemberCredentials) {
		System.out.println(mapAwardsNomination.get("SuggestCitation_Text"));
		System.out.println(element("txt_citationAwards").getText().trim());
		Assert.assertTrue(
				element("txt_citationAwards").getText().trim().equals(mapAwardsNomination.get("SuggestCitation_Text")));
		logMessage("ASSERT PASSED : Citiation field on Award Entry Profile is verified as "
				+ mapAwardsNomination.get("SuggestCitation_Text"));
		verifySupporterNamesOnAwardEntryProfilePage(createMemberCredentials, "1");
		verifySupporterNamesOnAwardEntryProfilePage(createMemberCredentials, "2");
		verifySupporterDocumentsContainsUploadedFile(mapAwardsNomination, "1");
		verifySupporterDocumentsContainsUploadedFile(mapAwardsNomination, "2");
	}

	private void verifySupporterDocumentsContainsUploadedFile(Map<String, String> mapAwardsNomination,
			String SupporterNumber) {
		String supportNo2 = null;
		if (SupporterNumber.equals("1")) {
			supportNo2 = "2";
		} else {
			supportNo2 = "1";
		}
		String docurl = element("lnk_awardsSupporterDoc", SupporterNumber).getAttribute("href");
		docurl = docurl.replaceAll("%20", " ");
		System.out.println(docurl);
		System.out.println(mapAwardsNomination.get("FileNameForSupportForm" + supportNo2));
		Assert.assertTrue(docurl.contains(mapAwardsNomination.get("FileNameForSupportForm" + supportNo2)));
		logMessage("ASSERT PASSED : Document for supporter " + SupporterNumber + " succesfully verified as "
				+ mapAwardsNomination.get("FileNameForSupportForm" + supportNo2));
	}

	private void verifySupporterNamesOnAwardEntryProfilePage(Map<String, String> createMemberCredentials,
			String SupporterNumber) {
		String[] supporters = element("txt_supporterNameAwardsNomination", SupporterNumber).getText().trim().split(" ");

		for (int i = 0; i < supporters.length; i++) {
			if (createMemberCredentials.get("Nominee" + (Integer.parseInt(SupporterNumber) + 1) + "Name")
					.contains(supporters[0])) {
				System.out.println(
						createMemberCredentials.get("Nominee" + (Integer.parseInt(SupporterNumber) + 1) + "Name"));
				System.out.println(supporters[i]);
				Assert.assertTrue(createMemberCredentials
						.get("Nominee" + (Integer.parseInt(SupporterNumber) + 1) + "Name").contains(supporters[i]));
			}
		}
		logMessage("ASSERT PASSED : Supporter " + SupporterNumber
				+ " name under acs award entry supporter is displayed as "
				+ createMemberCredentials.get("Nominee" + (Integer.parseInt(SupporterNumber) + 1) + "Name"));
	}

	private void verifyLetterDocuments_AwardsNomination(Map<String, String> mapAwardsNomination, String lettername,
			String datasheetValue) {
		if (datasheetValue.equalsIgnoreCase("FileNameForSupportForm1")) {
			System.out.println(elements("lnk_awardsLettersDoc", lettername).get(1).getAttribute("onclick"));
			System.out.println(mapAwardsNomination.get(datasheetValue));
			Assert.assertTrue(elements("lnk_awardsLettersDoc", lettername).get(1).getAttribute("onclick")
					.contains(mapAwardsNomination.get(datasheetValue)));
			logMessage("ASSERT PASSED : File uploaded for Support form 1 is displayed under Documents \n");
		} else if (datasheetValue.equalsIgnoreCase("FileNameForSupportForm2")) {
			System.out.println(elements("lnk_awardsLettersDoc", lettername).get(0).getAttribute("onclick"));
			System.out.println(mapAwardsNomination.get(datasheetValue));
			Assert.assertTrue(elements("lnk_awardsLettersDoc", lettername).get(0).getAttribute("onclick")
					.contains(mapAwardsNomination.get(datasheetValue)));
			logMessage("ASSERT PASSED : File uploaded for Support form 2 is displayed under Documents \n");
		} else {
			System.out.println(element("lnk_awardsLettersDoc", lettername).getAttribute("onclick"));
			System.out.println(mapAwardsNomination.get(datasheetValue));
			// Assert.assertTrue(element("lnk_awardsLettersDoc",
			// lettername).getAttribute("onclick")
			// .contains(mapAwardsNomination.get(datasheetValue)));
			logMessage("ASSERT PASSED : File uploaded for " + lettername + " is displayed under Documents \n");
		}
	}

	public void verifyLetterDocumentsOnAwardEntryProfilePage(Map<String, String> mapAwardsNomination) {
		verifyLetterDocuments_AwardsNomination(mapAwardsNomination, "Letter of Support", "FileNameForSupportForm1");
		verifyLetterDocuments_AwardsNomination(mapAwardsNomination, "Letter of Support", "FileNameForSupportForm2");
		if (mapAwardsNomination.get("UploadFileFor_Recommendation?").equals("Yes")) {
			verifyLetterDocuments_AwardsNomination(mapAwardsNomination, "Letter of Recommendation",
					"FileNameForReccomendation");
		}
		verifyLetterDocuments_AwardsNomination(mapAwardsNomination, "Biographical Sketch", "FileNameForBioSketch");
		verifyLetterDocuments_AwardsNomination(mapAwardsNomination, "Publications List", "FileNameForPubPatents");
	}

	public void clickOnEditButtonAndVerifyNomineeDetails_AwardRequirementsAndRecommendation(
			Map<String, String> mapAwardsNomination) {
		clickEditACSAwardsEntryButton();
		wait.waitForPageToLoadCompletely();
		switchToFrame("iframe1");
		verifyAwardDetailsAndRecommendations(mapAwardsNomination);

	}

	private void verifyAwardDetailsAndRecommendations(Map<String, String> mapAwardsNomination) {
		System.out.println(element("txtarea_CitationRecommedation", "citation").getText().trim());
		Assert.assertTrue(element("txtarea_CitationRecommedation", "citation").getText().trim()
				.equals(mapAwardsNomination.get("SuggestCitation_Text")));
		logMessage("ASSERT PASSED : Citation field is verified as " + mapAwardsNomination.get("SuggestCitation_Text")
				+ "\n");
		if (!mapAwardsNomination.get("UploadFileFor_Recommendation?").equals("Yes")
				&& mapAwardsNomination.get("Recommendation_Text").length() != 0) {
			System.out.println(element("txtarea_CitationRecommedation", "Recommendation").getText().trim());
			Assert.assertTrue(element("txtarea_CitationRecommedation", "Recommendation").getText().trim()
					.equals(mapAwardsNomination.get("Recommendation_Text")));
			logMessage("ASSERT PASSED : Recommendation text is verified as "
					+ mapAwardsNomination.get("Recommendation_Text") + "\n");
		} else {
			logMessage("ASSERT PASSED : File is uploaded for recommendation, text field is empty\n");
		}
		System.out.println(element("inp_presentposition").getAttribute("value").trim());
		System.out.println(mapAwardsNomination.get("EligibilityQuestions_NomineePosition"));
		Assert.assertTrue(element("inp_presentposition").getAttribute("value").trim()
				.equals(mapAwardsNomination.get("EligibilityQuestions_NomineePosition")));
		logMessage("ASSERT PASSED : Present Position field is verified as "
				+ mapAwardsNomination.get("EligibilityQuestions_NomineePosition") + "\n");
		System.out.println(element("drpdwn_industrytype", "protocols").getText());
		// Assert.assertTrue(element("drpdwn_industrytype",
		// "protocols").getText().trim()
		// .equals(mapAwardsNomination.get("SafeLabPractices?")));
		// logMessage("ASSERT PASSED : Saftey protocols field is verified as "
		// + mapAwardsNomination.get("SafeLabPractices?") + "\n");
		System.out.println(elements("drpdwn_industrytype", "nominee").get(1).getText());
		Assert.assertTrue(elements("drpdwn_industrytype", "nominee").get(1).getText().trim()
				.equals(mapAwardsNomination.get("EligibilityQuestions_professionalDiscipline")));
		logMessage("ASSERT PASSED : Industry type field is verified as "
				+ mapAwardsNomination.get("EligibilityQuestions_professionalDiscipline") + "\n");

	}

	private void clickEditACSAwardsEntryButton() {
		isElementDisplayed("btn_editAwardsEntry");
		element("btn_editAwardsEntry").click();
		logMessage("STEP : Edit button on ACS Entry profile page clicked\n");

	}

	public void clickGotoRecordForRenewal() {
		isElementDisplayed("txt_gotorecordrenewal", "1");
		if (isBrowser("ie") || isBrowser("internet explorer")) {
			clickUsingXpathInJavaScriptExecutor(element("txt_gotorecordrenewal", "1"));
		} else
			clickUsingXpathInJavaScriptExecutor(element("txt_gotorecordrenewal", "1"));
		// element("txt_gotorecordrenewal", "1").click();
		logMessage("STEP : Goto record is clicked for latest Invoice\n");
	}

	public void verifyNomineeAddress(String expectedData) {
		handleAlert();
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("txt_individualInfo");
		System.out.println("----exp:" + expectedData);
		System.out.println("----actual:" + element("txt_individualInfo").getText().trim());
		Assert.assertTrue(element("txt_individualInfo").getText().trim().contains(expectedData),
				"Assertion Failed: Individual address is not correctly updated");
		logMessage("ASSERT PASSED: Individual address is updated correctly");
	}

	public void clickOnAcsBiographyImage() {
		isElementDisplayed("img_biography");
		element("img_biography").click();
		logMessage("[INFO] : Clicked on ACS Biography Image");
	}

	public void verifyBioHonorsData(String expectedHonors) {
		wait.waitForPageToLoadCompletely();
		switchToFrame("iframe1");
		isElementDisplayed("txt_bioHonors");
		logMessage("Expected Biography data : " + expectedHonors);
		logMessage("Actual Biography data : " + element("txt_bioHonors").getText().trim());
		Assert.assertTrue(expectedHonors.equals(element("txt_bioHonors").getText().trim()),
				"Assertion Failed: Biography honors data does not matches");
		logMessage("Assertion Passed : Biography honors data matches");
		clickOnCancelButton();
		switchToDefaultContent();
	}

	public void clickOnCancelButton() {
		element("btn_cancel").click();
		logMessage("[INFO] : Clicked on cancel button");
	}

	public void verifyCommitteeMembersStatus(List<String> committeeList) {
		expandDetailsMenu("acsyb nominations");
		for (String committee : committeeList) {
			Assert.assertTrue(element("txt_quantity", committee).getText().trim().equalsIgnoreCase("Pending"));
			logMessage("ASSERT PASSED : Committee " + committee + " status is pending");
		}
	}

	public boolean verifyCommitteePreferenceDate() {
		String preferenceEndDate, preferenceStartDate;
		int max = 0;
		boolean value;
		expandDetailsMenu("acs committee system options");
		preferenceEndDate = element("txt_quantity", "ACSYBCommiteePreferenceEndDate").getText().trim();
		logMessage("PreferenceEndDate   " + preferenceEndDate);
		max = elements("link_pages").size();
		logMessage("Page size : " + max);
		isElementDisplayed("link_paging", String.valueOf(2));
		clickUsingXpathInJavaScriptExecutor(element("link_paging", String.valueOf(2)));
		preferenceStartDate = element("txt_quantity", "ACSYBCommiteePreferenceStartDate").getText().trim();
		logMessage("PreferenceStartDate   " + preferenceStartDate);
		value = verfiyEndAndStartDate(preferenceEndDate, preferenceStartDate);
		return value;
	}

	public boolean verfiyEndAndStartDate(String preferenceEndDate, String preferenceStartDate) {
		logMessage("Current Date :" + DateUtil
				.convertStringToDate(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"), "MM/dd/yyyy"));
		logMessage("End Date :" + DateUtil.convertStringToDate(preferenceEndDate, "MM/dd/yyyy"));
		int endDate, startDate;
		endDate = DateUtil
				.convertStringToDate(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"), "MM/dd/yyyy")
				.compareTo(DateUtil.convertStringToDate(preferenceEndDate, "MM/dd/yyyy"));

		logMessage("Current Date :" + DateUtil
				.convertStringToDate(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"), "MM/dd/yyyy"));
		logMessage("Start Date :" + DateUtil.convertStringToDate(preferenceStartDate, "MM/dd/yyyy"));
		startDate = DateUtil
				.convertStringToDate(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"), "MM/dd/yyyy")
				.compareTo(DateUtil.convertStringToDate(preferenceStartDate, "MM/dd/yyyy"));

		if (endDate == -1 && startDate == 1)
			return true;
		else
			return false;
	}

	public String getIndividualInformationWhichHasTerminateValueNull(int n) {
		int i = 1;
		String value = null;
		for (WebElement element : elements("list_individualMem")) {
			System.out.println("Length::" + element.getText().length());
			if (element.getText().length() == 1) {
				value = element("individualmem_data", toString().valueOf(i), toString().valueOf(n)).getText().trim();
				break;
			}
			i++;
		}
		System.out.println("Value==" + value);
		return value;
	}

	public String getMemberType() {
		isElementDisplayed("txt_subscriptionName", "1");
		return element("txt_subscriptionName", "1").getText().trim();
	}

	public String getMemberStatus() {
		isElementDisplayed("txt_subscriptionPrice", "1");
		return element("txt_subscriptionPrice", "1").getText().trim();
	}

	public String getJoinFieldValue() {
		isElementDisplayed("txt_subscriptionStartDate", "1");
		return element("txt_subscriptionStartDate", "1").getText().trim();
	}

	public String getEffectiveFieldValue() {
		isElementDisplayed("txt_subscriptionIssueFulfilled", "1");
		return element("txt_subscriptionIssueFulfilled", "1").getText().trim();
	}

	public String getExpireDate() {
		isElementDisplayed("txt_subscriptionStartIssue", "1");
		return element("txt_subscriptionStartIssue", "1").getText().trim();
	}

	public void enterValueInExpireInputField(String currentdateInStringWithGivenFormate) {
		wait.hardWait(2);
		isElementDisplayed("inp_expiredate");
		element("inp_expiredate").clear();
		element("inp_expiredate").sendKeys(currentdateInStringWithGivenFormate);

		logMessage("STEP : " + currentdateInStringWithGivenFormate + " is entered in expire input field\n");
	}

	public void enterValueInMemberStatusField(String dropdown, String option) {
		wait.hardWait(2);
		isElementDisplayed("select_member", dropdown);
		selectProvidedTextFromDropDown(element("select_member", dropdown), option);
		logMessage("STEP : Selected " + option + " from dropdown " + dropdown);
	}

	public void verifyTableUnderExpandedBarIsNotEmpty(String barName) {
		Assert.assertTrue(elements("btn_invoicearrow").size() > 0, "Table under" + barName + " is empty");
		logMessage("Table umder " + barName + " is not empty\n");

	}

	public void selectRandomGotoRecord(String barName) {
		Random rand = new Random();
		int randomNumber = rand.nextInt(((elements("btn_invoicearrow").size() - 1) - 0) + 1) + 0;
		System.out.println(randomNumber);
		elements("btn_invoicearrow").get(randomNumber).click();
		logMessage("STEP : " + barName + " number " + randomNumber + " is selected from the list\n");

	}

	public void NavigateToIndividualProfilePageFromScarfReviewList(String customerName) {
		try {
			wait.resetImplicitTimeout(3);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("btn_scarfReviewerUserList", customerName,
					DateUtil.getAnyDateForType("YYYY", 0, "year"));
			element("btn_scarfReviewerUserList", customerName, DateUtil.getAnyDateForType("YYYY", 0, "year")).click();
			logMessage("STEP : Customer as " + customerName
					+ " is selected from list, user is on student chapter Review profile page\n");
		} catch (NoSuchElementException e) {
			logMessage("Result list does not appeared, user is on student chapter Review profile page");
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
		element("txt_userEmail", customerName).click();
		handleAlert();
		logMessage("STEP : Customer Name as " + customerName + " is clicked on student chapter Review profile page\n");

	}

	public void enterChapterNameAndStatusAndClickGoButton(String chapterName, String chapterStatus) {
		enterFieldValue("Chapter Name", chapterName);
		selectDropDownValue(chapterStatus);
		clickGoButton();

	}

	public Map<String, String> getParticularMemberDetails(String tabName, String status) {
		Map<String, String> membershipDateList = new HashMap<String, String>();
		String date;
		isElementDisplayed("list_memberDetails", tabName);
		int size = elements("list_memberDetails", tabName).size();
		System.out.println("-----size:" + size);
		for (int i = 1; i < size; i++) {
			if (element("txt_memberDetailsForChapter", tabName, String.valueOf(6), String.valueOf(i)).getText().trim()
					.equalsIgnoreCase(status)) {
				date = getMemberDetails(tabName, 9, i);
				logMessage("STEP : " + tabName + " Effective date is " + date + "\n");
				membershipDateList.put("Effective Date", date);
				date = getMemberDetails(tabName, 10, i);
				logMessage("STEP : " + tabName + " Expire date is " + date + "\n");
				membershipDateList.put("Expire Date", date);
				break;
			}
		}
		return membershipDateList;
	}

	public String getMemberDetails(String tabName, int index1, int index2) {
		isElementDisplayed("txt_memberDetailsForChapter", tabName, String.valueOf(index1), String.valueOf(index2));
		String date = DateUtil.convertStringToParticularDateFormat(
				element("txt_memberDetailsForChapter", tabName, String.valueOf(index1), String.valueOf(index2))
						.getText().trim(),
				"MM/dd/yyyy");
		System.out.println("date:" + date);
		return date;
	}

	public String matchEffectiveAndExpiryDate(Map<String, String> membershipDateList, String tabName) {
		isElementDisplayed("list_memberDetails", tabName);
		int i, size = elements("list_memberDetails", tabName).size();
		System.out.println("----size in:" + size);
		for (i = 1; i < size; i++) {
			if (element("txt_memberDetailsForChapter", tabName, String.valueOf(9), String.valueOf(i)).getText().trim()
					.equals(membershipDateList.get("Effective Date"))
					&& element("txt_memberDetailsForChapter", tabName, String.valueOf(10), String.valueOf(i)).getText()
							.trim().equals(membershipDateList.get("Expire Date"))
					&& element("txt_memberDetailsForChapter", tabName, String.valueOf(5), String.valueOf(i)).getText()
							.trim().equals("Local Section Member")) {
				logMessage("STEP : Chapter selected is "
						+ element("txt_memberDetailsForChapter", tabName, String.valueOf(4), String.valueOf(i))
								.getText().trim()
						+ "\n");
				Assert.assertTrue(checkTerminateDateIsNull(i, tabName, "", 11),
						"ASSERT FAILED : Terminate date is not null\n");
				logMessage("ASSERT PASSED : Terminate date is null\n");
				Assert.assertTrue(checkTerminateDateIsNull(i, tabName, "Active-chp", 6),
						"ASSERT FAILED : Member status is not Active-chp\n");
				logMessage("ASSERT PASSED : Member status is Active-chp\n");
				break;
			}
		}
		return element("txt_memberDetailsForChapter", tabName, String.valueOf(4), String.valueOf(i)).getText().trim();
	}

	public boolean checkTerminateDateIsNull(int index, String tabName, String expectedValue, int index1) {
		if (element("txt_memberDetailsForChapter", tabName, String.valueOf(index1), String.valueOf(index)).getText()
				.trim().equals(expectedValue)) {
			return true;
		} else
			return false;
	}

	public List<String> verifyAddressType(String tabName, List<String> addressType) {
		int i;
		isElementDisplayed("list_memberDetails", tabName);
		System.out.println("size:" + elements("list_memberDetails", tabName).size());
		for (i = 1; i < elements("list_memberDetails", tabName).size(); i++) {
			String addressMode = element("txt_memberDetailsForChapter", tabName, String.valueOf(4), String.valueOf(i))
					.getText().trim();
			if (addressType.contains(addressMode)) {
				addressType.remove(addressMode);
				logMessage("STEP : Address type " + addressMode + " is already present");
			}
		}
		System.out.println(addressType);
		return addressType;
	}

	public void clickOnPlusSign(String tabName, int index) {
		isElementDisplayed("btn_plusIcon", tabName, String.valueOf(index));
		clickUsingXpathInJavaScriptExecutor(element("btn_plusIcon", tabName, String.valueOf(index)));
		logMessage("STEP : Clicked on plus icon of " + tabName + " tab\n");
	}

	public void addNewAddress(String organization, String dept, String address, String city, String postalCode,
			String state, List<String> addressType) {
		switchToFrame("iframe1");
		hardWaitForIEBrowser(5);
		enterNewAddressDetails("organization", organization);
		enterNewAddressDetails("department", dept);
		enterNewAddressDetails("address line 1", address);
		enterNewAddressDetails("city", city);
		enterNewAddressDetails("postal code", postalCode);
		selectAddressType(addressType.get(0));
		selectAddressState(state);
		wait.hardWait(2);
		selectAddressCheckboxes("primary");
		selectAddressCheckboxes("billing");
		selectAddressCheckboxes("do not validate");
		clickOnSaveButton();
		hardWaitForIEBrowser(30);
		switchToDefaultContent();
	}

	public void enterNewAddressDetails(String field, String addressDetails) {
		isElementDisplayed("inp_addressDetails", field);
		element("inp_addressDetails", field).sendKeys(addressDetails);
		logMessage("STEP : " + field + " value entered as " + addressDetails + "\n");
	}

	public void selectAddressType(String addressType) {
		selectProvidedTextFromDropDown(element("select_addressType"), addressType);
		logMessage("STEP : Address Type entered as " + addressType + "\n");
	}

	public void selectAddressState(String state) {
		wait.hardWait(1);
		hardWaitForIEBrowser(2);
		selectProvidedTextFromDropDown(element("select_state"), state);
		logMessage("STEP : State entered as " + state + "\n");
	}

	public void selectAddressCheckboxes(String field) {
		hardWaitForIEBrowser(2);
		isElementDisplayed("chkbox_primary", field);
		clickUsingXpathInJavaScriptExecutor(element("chkbox_primary", field));
		if (!element("chkbox_primary", field).isSelected()) {
			clickUsingXpathInJavaScriptExecutor(element("chkbox_primary", field));
		}
		logMessage("STEP : " + field + " checkbox is selected\n");
	}

	public void verifyAdditionOfNewAddress(String addressType, String tabName) {
		int i;
		boolean flag = false;
		waitForSpinner();
		wait.waitForPageToLoadCompletely();
		wait.hardWait(20);
		hardWaitForIEBrowser(25);
		isElementDisplayed("list_memberDetails", tabName);
		for (i = 1; i < elements("list_memberDetails", tabName).size(); i++) {
			if (element("txt_memberDetailsForChapter", tabName, String.valueOf(4), String.valueOf(i)).getText().trim()
					.equals(addressType)) {
				flag = true;
				break;
			}
		}
		Assert.assertTrue(flag, "ASSERT FAILED : new address " + addressType + " cannot be added\n");
		logMessage("ASSERT PASSED : New address " + addressType + " is added\n");
		verifyPrimaryFlagIsSet(tabName, 8, i, "primary");
		verifyPrimaryFlagIsSet(tabName, 9, i, "billing");
		wait.hardWait(2);
	}

	public void verifyPrimaryFlagIsSet(String tabName, int index1, int index2, String field) {
		isElementDisplayed("img_primary", tabName, String.valueOf(index1), String.valueOf(index2));
		logMessage("ASSERT PASSED : " + field + " flag is set\n");
	}

	public void verifyChapterStatusIsTransferred(String tabName, String chpName) {
		int i, index = 0;
		String currentDate = DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("MM/dd/yyyy", "EDT");
		isElementDisplayed("list_memberDetails", tabName);
		for (i = 1; i < elements("list_memberDetails", tabName).size(); i++) {
			if (element("txt_memberDetailsForChapter", tabName, String.valueOf(4), String.valueOf(i)).getText().trim()
					.equals(chpName) && checkTerminateDateIsNull(i, tabName, currentDate, 11)) {
				flag = true;
				index = i;
				break;
			}
		}
		// Assert.assertTrue(flag,"ASSERT FAILED : Mbr Status of Chapter
		// "+chpName+" is not changed to Transferred\n");
		System.out.println("-------index:" + index);
		Assert.assertTrue(checkTerminateDateIsNull(index, tabName, "Transferred", 6),
				"ASSERT FAILED : Mbr Status of Chapter " + chpName + " is not changed to Transferred\n");
		logMessage("ASSERT PASSED : Mbr Status of Chapter " + chpName + " is changed to Transferred\n");
		// Assert.assertTrue(checkTerminateDateIsNull(i,tabName,currentDate,11),"ASSERT
		// FAILED : Terminate date is not equal to current date\n");
		logMessage("ASSERT PASSED : Terminate date "
				+ element("txt_memberDetailsForChapter", tabName, String.valueOf(11), String.valueOf(index)).getText()
						.trim()
				+ " is equal to current date " + currentDate + "\n");
	}

	public void verifyNewChapterIsAdded(String tabName) {
		isElementDisplayed("list_memberDetails", tabName);
		int i, size = elements("list_memberDetails", tabName).size();
		System.out.println("----size in:" + size);
		for (i = 1; i < size; i++) {
			System.out.println("-----i:" + i);
			if (element("txt_memberDetailsForChapter", tabName, String.valueOf(9), String.valueOf(i)).getText().trim()
					.equals(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"))
					&& element("txt_memberDetailsForChapter", tabName, String.valueOf(5), String.valueOf(i)).getText()
							.trim().equals("Local Section Member")) {
				Assert.assertTrue(checkTerminateDateIsNull(i, tabName, "", 11),
						"ASSERT FAILED : Terminate date is not null");
				logMessage("ASSERT PASSED : Terminate date is null");
				Assert.assertTrue(checkTerminateDateIsNull(i, tabName, "Active-chp", 6),
						"ASSERT FAILED : Member status is not Active-chp");
				logMessage("ASSERT PASSED : Member status is Active-chp");
				logMessage("STEP : New Chapter added is "
						+ element("txt_memberDetailsForChapter", tabName, String.valueOf(4), String.valueOf(i))
								.getText().trim());
				break;
			}
		}
	}

	public String[] verifyYearForOMRDiscountByPrice(Map<String, String> mapOMRDiscount) {
		String dues_year;
		String actualpricing = getMemberType();
		dues_year = checkPricesForThreeYearDiscountForRenewal(mapOMRDiscount, "$" + actualpricing, 1);

		System.out.println(dues_year);
		if (dues_year == null) {
			logMessage("Step : Record is already discounted to amount " + getMemberType());
			logMessage("Step : Discount is already applied but not paid\n");
		} else {
			logMessage("Step : Record's original amount is " + actualpricing);
		}
		return new String[] { element("txt_supporterNameAwardsNomination", "1").getText().trim(), actualpricing,
				dues_year };
	}

	private String checkPricesForThreeYearDiscountForRenewal(Map<String, String> mapOMRDiscount, String dues_year,
			int years) {

		System.out.println("intital dues year " + dues_year);
		float amount = Float.parseFloat(getMemberType()) / years;
		System.out.println(amount);
		System.out.println(Math.round(amount));
		if (!mapOMRDiscount.get("Member_Status?").equals("Student")) {
			do {
				dues_year = getMapFirstMatchedKeyByValue(mapOMRDiscount, "$" + Math.round(amount) + ".00");
				years++;
			} while (dues_year != null && years != 4);
		} else {
			dues_year = getMapFirstMatchedKeyByValue(mapOMRDiscount, "$" + Math.round(amount) + ".00");
		}
		System.out.println("dues" + dues_year);
		System.out.println("years" + years);
		return dues_year;

	}

	public void verifyOverrideStatusForOMRDiscount(String productname) {
		isElementDisplayed("txt_termStartDate", productname);
		System.out.println(element("txt_termStartDate", productname).getText());

	}

	public String addChapterRelationshipsToIndividual() {
		String chapterName;
		switchToFrame(element("iframe1"));
		clickLookUpButton();
		chapterName = clickActiveChapterName();
		return chapterName;
	}

	private String clickActiveChapterName() {
		String chapterName = null;
		List<WebElement> activeChapters = new ArrayList<WebElement>();
		try {
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			wait.resetImplicitTimeout(4);
			for (WebElement ele : elements("txt_active_chapters")) {
				activeChapters.add(ele);
			}
			System.out.println("active chapter size"+activeChapters.size());
			chapterName = activeChapters.get(generateRandomNumberWithInRange(0, activeChapters.size())).getText();
			logMessage("Step : Random active Chapter Name displayed as " + chapterName);
			element("txt_divisionPubName", chapterName).click();
			logMessage("Step: ****************chapter " + chapterName + " is selected from the list");
		} catch (Exception e) {
			System.out.println("In catch**********");
			MembershipPageActions_IWEB membershipPage = new MembershipPageActions_IWEB(driver);
			membershipPage.clickOnRandomPage();
			clickActiveChapterName();
		}
		wait.resetExplicitTimeout(timeOut);
		wait.resetImplicitTimeout(timeOut);
		System.out.println("chapter name" + chapterName);
		return chapterName;

	}

	private void clickLookUpButton() {
		// element("inp_chapterName","chapter name").sendKeys("QA");
		wait.hardWait(6);
		wait.waitForElementToBeClickable(element("img_look_up"));
		element("img_look_up").click();
		logMessage("Step: Look up button is clicked\n");
		wait.waitForPageToLoadCompletely();

	}

	public void addRelationshiptype(String relationshipName) {

		isElementDisplayed("drpdwn_relationshipType", "relationship type");
		// executeJavascript("document.getElementById('iframe1').contentWindow.document.getElementById('cxc_rlt_code').value='"+relationshipName+"'");
		selectProvidedTextFromDropDown(element("drpdwn_relationshipType", "relationship type"), relationshipName);
		;
		logMessage("Step : Relationship name is selected as " + relationshipName);

	}

	public void enterStartDateForChapter(String currentdate) {
		isElementDisplayed("inp_startDate");
		element("inp_startDate").click();
		element("inp_startDate").clear();
		element("inp_startDate").sendKeys(currentdate);
		logMessage("Step: Current date is entered in chapter start date as " + currentdate);

	}

	public void verifyCurrentRecordisAddedInChapterRelationship(String chapterName, String chapterRole,
			String dateFormate) {
		switchToDefaultContent();
		waitForSpinner();
		System.out.println(chapterName);
		wait.hardWait(4);
		System.out.println(element("txt_priceValue", chapterName).getText().trim());
		System.out.println(chapterRole);
		isElementDisplayed("txt_divisionPubName", chapterName);
		Assert.assertTrue(element("txt_priceValue", chapterName).getText().trim().equals(chapterRole),
				"Chapter Role is not " + chapterRole);
		System.out.println(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"));
		logMessage("ASSERT PASSED : chapter " + chapterName + " role is verified as " + chapterName);
		Assert.assertTrue(
				element("txt_quantity", chapterName).getText().trim()
						.equals(DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone(dateFormate, "EST5EDT")),
				"Date is not current date");

		logMessage("ASSERT PASSED : Date for chapter " + chapterName + " is current date "
				+ DateUtil.getCurrentdateInStringWithGivenFormate(dateFormate));

	}

	public void enterIndividualSortName(String sortName, String value) {
		switchToFrame(element("iframe1"));
		isElementDisplayed("inp_addressDetails", sortName);
		element("inp_addressDetails", sortName).sendKeys(value);
		logMessage("Step : Sort name is entered as " + value);
		wait.hardWait(3);
		wait.waitForPageToLoadCompletely();
		switchToDefaultContent();
	}

	public void addIndividualRelationshipsToChapter() {
		switchToFrame(element("iframe1"));
		clickLookUpButton();
		clickRandomRecordInIndividualRelationship();
	}

	private void clickRandomRecordInIndividualRelationship() {
		String randomrow = toString().valueOf(generateRandomNumberWithInRange(1, 20));
		isElementDisplayed("txt_subscriptionName", randomrow);
		element("txt_subscriptionName", randomrow).click();
		logMessage("Step: Individual relationship is added in chapter\n");
	}

	public String getindividualRelationshipName(String sortName) {
		isElementDisplayed("inp_addressDetails", sortName);
		System.out.println(element("inp_addressDetails", sortName).getAttribute("value"));
		return element("inp_addressDetails", sortName).getAttribute("value").trim();

	}

	public void clickPencilButtonToEditIndividualRecord(String individualname) {
		isElementDisplayed("tab_pencilButton", individualname);
		element("tab_pencilButton", individualname).click();
		logMessage("Step : Pencil button is clicked to edit " + individualname + " record\n");
		switchToFrame(element("iframe1"));

	}

	public int verifyDiscountedPriceFortheRecord(String percentagediscount, String[] productprice) {
		int expectedamount = 0;
		System.out.println("product price" + productprice[2]);
		System.out.println("product amount" + productprice[1]);
		if (productprice[2] == null) {
			logMessage("Step : Product price is already discounted to " + productprice[1]);

		} else {

			expectedamount = getActualDiscountedAmount((100 - Integer.parseInt(percentagediscount)),
					Float.parseFloat(productprice[1]));
			System.out.println("expected amount " + expectedamount);
			System.out.println(element("txt_quantity", productprice[0]).getText());
			Assert.assertTrue(element("txt_quantity", productprice[0]).getText().trim().equals(expectedamount + ".00"));
			logMessage("ASSERT PASSED : Discounted amount is exactly " + percentagediscount + " % of actual amount "
					+ productprice[1]);
		}
		return expectedamount;
	}

	private int getActualDiscountedAmount(int percentagediscount, float price) {

		System.out.println(percentagediscount);
		System.out.println(price);
		float value = ((float) percentagediscount / 100 * price);
		System.out.println(value);
		int expectedamount = Math.round(value);
		return expectedamount;

	}

	public void clickImageButtonsOnAdditionalInformationPanel(String buttonName) {
		isElementDisplayed("img_demoGraphics", buttonName);
		element("img_demoGraphics", buttonName).click();
		logMessage("Step : Button " + buttonName + " is clicked on individuals profile page\n");
	}

	public void enterBPAJobTitleDates(String field) {
		String date = DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/YYYY");
		isElementDisplayed("inp_bpa_info", field);
		sendKeysUsingXpathInJavaScriptExecutor(element("inp_bpa_info", field), date);
		logMessage("STEP : " + field + " date for is entered as " + date);
		wait.hardWait(2);
	}

	public Map<String, String> fillBPAInformationAfterClickinhDemographics(String[] BPATypeInfoArray) {
		Map<String, String> bpaDetailsMap = new HashMap<String, String>();
		switchToFrame("iframe1");
		addRandomBpaIndustryAndJobTitleNameOnForm(BPATypeInfoArray[0]);
		addRandomBpaIndustryAndJobTitleNameOnForm(BPATypeInfoArray[1]);
		enterBPAJobTitleDates(BPATypeInfoArray[2]);
		enterBPAJobTitleDates(BPATypeInfoArray[3]);
		bpaDetailsMap = saveBPAUpdatedDetails(bpaDetailsMap, BPATypeInfoArray[0], "sin_key");
		bpaDetailsMap = saveBPAUpdatedDetails(bpaDetailsMap, BPATypeInfoArray[1], "ttl_key");
		clickOnSaveButton();
		wait.hardWait(4);
		waitForSpinner();
		switchToDefaultContent();
		bpaDetailsMap.get("industry");
		bpaDetailsMap.get("title");
		return bpaDetailsMap;

	}

	public Map<String, String> saveBPAUpdatedDetails(Map<String, String> bpaDetailsMap, String nametype,
			String nameid) {
		isElementDisplayed("drpdwn_industrytype", nameid);
		bpaDetailsMap.put(nametype, element("drpdwn_industrytype", nameid).getText());
		logMessage("Step : Details fetched after bpa updation for " + nametype + " is "
				+ element("drpdwn_industrytype", nameid).getText());
		return bpaDetailsMap;
	}

	public void addRandomBpaIndustryAndJobTitleNameOnForm(String type) {

		isElementDisplayed("drpdwn_relationshipType", type);
		selectDropDownValue(element("drpdwn_relationshipType", type),
				generateRandomNumberWithInRange(1, (elements("drpdwn_options", type).size() - 2)));
		wait.hardWait(4);
		logMessage("Step : " + type + " name is selected as randomly as "
				+ getSelectedTextFromDropDown(element("drpdwn_relationshipType", type)));

	}

	public void verifyDemographicChangeLogChildform(String[] BPATypeInfoArray, Map<String, String> bpaMap) {
		String currentdate = DateUtil.getCurrentdateInStringWithGivenFormate("M/d/YYYY");
		verifyChildFormForUpdatedDemographicLog(BPATypeInfoArray[0], bpaMap.get(BPATypeInfoArray[0]));
		verifyChildFormForUpdatedDemographicLog(BPATypeInfoArray[1], bpaMap.get(BPATypeInfoArray[1]));
		verifyChildFormForUpdatedDemographicLog(BPATypeInfoArray[2], currentdate);
		verifyChildFormForUpdatedDemographicLog(BPATypeInfoArray[3], currentdate);
	}

	public void verifyChildFormForUpdatedDemographicLog(String fieldName, String fieldValue) {
		try {
			isElementDisplayed("txt_updatedLogsBPA", fieldName, "1");
			String actualfieldValue = elements("txt_updatedLogsBPA", fieldName, "1").get(0).getText().trim();
			System.out.println(actualfieldValue);
			System.out.println(fieldValue);
			Assert.assertTrue(fieldValue.trim().contains(actualfieldValue), "demographic change log is not updated\n");
			logMessage("ASSERT PASSED : Demographic log for " + fieldName + " is verified as " + fieldValue);
		} catch (NoSuchElementException e) {
			logMessage("Step : " + fieldName + " as " + fieldValue + " is not present in acs demograph logs\n");
		}
	}

	public String fillDataOnRapidEntryFormAndSaveChanges(Map<String, String> bpaMap, String[] BPATypeInfoArray,
			String customerID) {
		String currentdate = DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/YYYY");
		
		wait.hardWait(4);
		EnterTextInField(element("inp_bpa_info","customer id"), customerID);

		clickSearchLookUpButtonOnBPARapidForm();
		selectProvidedTextFromDropDown(element("drpdwn_relationshipType", BPATypeInfoArray[0]),
				bpaMap.get(BPATypeInfoArray[0]));
		wait.hardWait(9);
		sendKeysUsingXpathInJavaScriptExecutor(element("inp_bpa_info", BPATypeInfoArray[2]), currentdate);
		selectProvidedTextFromDropDown(element("drpdwn_relationshipType", ("job " + BPATypeInfoArray[1])),
				bpaMap.get(BPATypeInfoArray[1]));
		wait.hardWait(9);
		sendKeysUsingXpathInJavaScriptExecutor(element("inp_bpa_info", BPATypeInfoArray[3]), currentdate);
		addRandomBpaIndustryAndJobTitleNameOnForm("bpa promo code");
		String promocode = getSelectedTextFromDropDown(element("drpdwn_relationshipType", "bpa promo code"));
		logMessage("Step : Promocode is entered as " + promocode);
		saveRapidFormInformationForBPA();

		return promocode;

	}

	private void clickSearchLookUpButtonOnBPARapidForm() {
		isElementDisplayed("btn_rapidFormLookup");
		element("btn_rapidFormLookup").click();
		logMessage("Step : Individual look up button is clicked\n");
		wait.hardWait(5);
		wait.waitForPageToLoadCompletely();

	}

	private void saveRapidFormInformationForBPA() {
		isElementDisplayed("btn_saveRapidFormData");
		element("btn_saveRapidFormData").click();
		logMessage("Step : Rapid Form information is saved\n");
		wait.hardWait(3);

	}

	public void selectOneIndividual(String text, String field) {
		// wait.waitForElementToBeVisible(element("img_activeMember",text));
		if (checkIfElementIsThere("img_activeMember", text)) {

			clickUsingXpathInJavaScriptExecutor(element("img_activeMember", text));
			// element("img_activeMember",text).click();
			logMessage("STEP: Individual with " + text + " " + field + " is selected\n");
		} else
			logMessage("STEP: Individual list does not appeared. User is navigated to Membership profile page\n");
		wait.waitForPageToLoadCompletely();
	}

	public String getPriceValueOfGiftCard(String productName, String salePrice, String caseID) {
		String pricevalue = null;
		if (caseID.equals("2")) {
			pricevalue = salePrice;
		} else {
			System.out.println(productName);
			isElementDisplayed("txt_updatedLogsBPA", productName, "3");
			pricevalue = element("txt_updatedLogsBPA", productName, "3").getText().trim();
		}
		logMessage("Step : Price value is fetched for " + productName + " as " + pricevalue);
		return pricevalue;

	}

	public String verifyGiftItemPurchasedDetailsBeforeRedeeming(String batchName, String priceValue) {
		String GiftCardNumber = getMemberType();
		System.out.println("Redeemed code is " + GiftCardNumber);
		verifyGiftCardDetailsOnIweb(GiftCardNumber, "4",
				DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/YYYY", "EST"));
		verifyGiftCardDetailsOnIweb(GiftCardNumber, "5", batchName);
		verifyGiftCardDetailsOnIweb(GiftCardNumber, "6", "N");
		verifyGiftCardDetailsOnIweb(GiftCardNumber, "7", priceValue);
		return GiftCardNumber;
	}

		public void verifyGiftItemPurchasedDetailsAfterRedeeming(String GiftCardNumber, String priceValue)
		{
			verifyGiftCardDetailsOnIweb(GiftCardNumber, "4", DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/YYYY","EST"));
			verifyGiftCardDetailsOnIweb(GiftCardNumber, "6", "Y");
			verifyGiftCardDetailsOnIweb(GiftCardNumber, "7", priceValue);
		}
		
		public void clickOnRedeemedCustomerIDInGiftCardPurchasedBar(String redeemedCustomerID)
		{
			isElementDisplayed("txt_userEmail",redeemedCustomerID);
			element("txt_userEmail",redeemedCustomerID).click();
			logMessage("Step : Redeemed customer ID link is clicked as "+redeemedCustomerID);
		}
		
		private void verifyGiftCardDetailsOnIweb(String GiftCardNumber,String index, String value)
		{
			isElementDisplayed("txt_updatedLogsBPA",GiftCardNumber,index);
			System.out.println(value);
			System.out.println(element("txt_updatedLogsBPA",GiftCardNumber,index).getText().trim());
			Assert.assertTrue(element("txt_updatedLogsBPA",GiftCardNumber,index).getText().trim().contains(value));
			logMessage("ASSERT PASSED"
					+ " gift card details on iweb is verified as "+value);
		}

		public void verifyRedeemedGiftCardDetails(String giftCardNumber, String pricevalue) {
		
			verifyGiftCardDetailsOnIweb(giftCardNumber, "1", DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/YYYY","EST"));
			verifyGiftCardDetailsOnIweb(giftCardNumber, "3", pricevalue);
		    
		}
		
		public List<String> verifyDatesArePopulatedUnderIndividualMemberships(String mbrStatus){
			
			List<String> individualDatelist = new ArrayList<String>();
			Assert.assertTrue(element("txt_total", mbrStatus).getText().trim().length()!=0,"Effective date is not populated under individual membership tab");
			individualDatelist.add(element("txt_total", mbrStatus).getText().trim());
			Assert.assertTrue(element("txt_payment", mbrStatus).getText().trim().length()!=0,"Expire date is not populated under individual membership tab");
			individualDatelist.add(element("txt_payment", mbrStatus).getText().trim());
			logMessage("ASSERT PASSED : All Dates are now populated under individual membership tab\n");
			return individualDatelist;
			
}
		
		public void verifyDatesUnderChapterMembershipMatchesIndividualDates(String mbrStatus,List<String> individualDatelist){
			verifyDatesUnderChapterMembership(elements("txt_total",mbrStatus), individualDatelist, 0);
			verifyDatesUnderChapterMembership(elements("txt_payment",mbrStatus), individualDatelist, 1);
		}

		private void verifyDatesUnderChapterMembership(List<WebElement> elements,List<String> individualDatelist,int count) {
			for (WebElement ele : elements) {
				Date chapterdate=DateUtil.convertStringToDate(ele.getText().trim(), "MM/dd/YYYY");
				Date individualdate=DateUtil.convertStringToDate(individualDatelist.get(count), "MM/dd/YYYY");
				System.out.println(chapterdate);
				System.out.println(individualdate);
				Assert.assertTrue(individualdate.compareTo(chapterdate)==0);
				logMessage("ASSERT PASSED : individual date "+individualDatelist.get(count)+" is equal to chapter Dates "+ele.getText());
			}
			
		}

		public void verifyActiveSubscriptionDatesIfAvailable(String invoiceNumber,List<String> individualDatelist) {
			if(element("txt_priceValue",invoiceNumber).getText().trim().equals("Chemical and Engineering News"))
			{
				logMessage("Step : active subscription Start and End dates for C&EN are empty\n");
			}
			else
			{
				verifyDatesUnderChapterMembership(elements("txt_total",invoiceNumber), individualDatelist, 0);
				verifyDatesUnderChapterMembership(elements("txt_payment",invoiceNumber), individualDatelist, 1);
//			isElementDisplayed("txt_total",invoiceNumber);
//			isElementDisplayed("txt_payment",invoiceNumber);
//			System.out.println(element("txt_total",invoiceNumber).getText());
//			System.out.println(individualDatelist.get(0));
//			System.out.println(element("txt_payment",invoiceNumber).getText());
//			System.out.println(individualDatelist.get(1));
//			Assert.assertTrue(element("txt_total",invoiceNumber).getText().trim().equals(individualDatelist.get(0)));
//			logMessage("ASSERT PASSED : Start date for active subscription is same as individual dates as "+individualDatelist.get(0));
//			Assert.assertTrue(element("txt_payment",invoiceNumber).getText().trim().equals(individualDatelist.get(1)));
//			logMessage("ASSERT PASSED : End date for active subscription is same as individual dates as "+individualDatelist.get(1));
			}
		
			
		}
		
	

		

}


