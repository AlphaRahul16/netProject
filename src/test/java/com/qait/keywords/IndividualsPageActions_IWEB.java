package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.omg.CORBA.OMGVMCID;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

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
			logMessage("Step : Check " + fieldName + " to search member\n");
		} else {
			logMessage("Step : member detail is already checked\n");
		}
		clickGoButton();
		handleAlert();
	}

	public void enterFieldValue(String fieldName, String fieldValue) {
		wait.hardWait(5);
		isElementDisplayed("inp_fieldName", fieldName);
		element("inp_fieldName", fieldName).sendKeys(fieldValue);

		logMessage("STEP : " + fieldName + " as " + fieldValue + " is entered in inp_fieldName\n");

	}

	public void clickGoButton() {
		isElementDisplayed("btn_Go");
		if (isIEBrowser()) {
			clickUsingXpathInJavaScriptExecutor(element("btn_Go"));
		} else {
			element("btn_Go").click();
		}
		logMessage("STEP:  go button is clicked in btn_Go\n");

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

	public void editEmail_NCW_CCED(String emailType, String emailID, String position) {
		clickOnEditButton(position);
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

	public void clickOnEditButton(String position) {
		// isElementDisplayed("link_editBtn",position);
		// element("link_editBtn",position).click();
		// logMessage("Step : edit button is clicked \n");

		isElementDisplayed("link_editEmail", position);
		element("link_editEmail", position).click();
		logMessage("Step : edit button is clicked \n");

	}

	public void verifyMemberDetails_OMA(String fName, String lName, String add, String city, String zipCode,
			String addressType, String contactId, String userEmail, String caseId) {

		hardWaitForIEBrowser(10);
		verifyElementTextContains("txt_memberDetails", fName);
		logMessage("ASSERT PASSED :" + fName + " is verified in txt_memberDetails\n");
		verifyElementTextContains("txt_memberDetails", lName);
		logMessage("ASSERT PASSED :" + lName + " is verified in txt_memberDetails\n");
		if (getOmaSheetValue(caseId, "Has Summer mailing address?").equalsIgnoreCase("show")) {
			String startDateInString = getOmaSheetValue(caseId, "Select Summer mailing start day") + "/"
					+ getOmaSheetValue(caseId, "Select Summer mailing  start month");
			String endDateInString = getOmaSheetValue(caseId, "Select Summer mailing end month") + "/"
					+ getOmaSheetValue(caseId, "Select Summer mailing end day");
			Date startDate = DateUtil.convertStringToDate(startDateInString, "dd/MM");
			Date endDate = DateUtil.convertStringToDate(endDateInString, "dd/MM");
			Date currentDate = DateUtil.convertStringToDate(DateUtil.getCurrentdateInStringWithGivenFormate("dd/MM"),
					"dd/MM");
			if (currentDate.after(startDate) && currentDate.before(endDate)) {
				verifyElementTextContains("txt_memberDetails",
						getOmaSheetValue(caseId, "Enter Summer mailing address"));
				logMessage("ASSERT PASSED :" + getOmaSheetValue(caseId, "Enter Summer mailing address")
						+ " is verified in txt_memberDetails\n");
				verifyElementTextContains("txt_memberDetails", getOmaSheetValue(caseId, "Enter  Summer city"));
				logMessage("ASSERT PASSED :" + getOmaSheetValue(caseId, "Enter  Summer city")
						+ " is verified in txt_memberDetails\n");
				verifyElementTextContains("txt_memberDetails", getOmaSheetValue(caseId, "Enter  Summer zip code"));
				logMessage("ASSERT PASSED :" + getOmaSheetValue(caseId, "Enter  Summer zip code")
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

		verifyElementTextContains("txt_memberDetails", map().get("street"));
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
		verifyElementTextContent("txt_memberDetails", memDetails[0]);
		logMessage("ASSERT PASSED :" + memDetails[0] + " is verified in txt_memberDetails\n");
		verifyElementTextContent("txt_memberDetails", memDetails[1]);
		logMessage("ASSERT PASSED :" + memDetails[1] + " is verified in txt_memberDetails\n");
		verifyElementTextContent("txt_memberDetails", memDetails[2]);
		logMessage("ASSERT PASSED :" + memDetails[2] + " is verified in txt_memberDetails\n");

		if (!(memDetails[3].equalsIgnoreCase(""))) {
			verifyElementTextContent("txt_memberDetails", memDetails[3]);
			logMessage("ASSERT PASSED :" + memDetails[3] + " is verified in txt_memberDetails\n");
		}
		verifyElementTextContent("txt_memberDetails", memDetails[4]);
		logMessage("ASSERT PASSED :" + memDetails[4] + " is verified in txt_memberDetails\n");
		verifyElementTextContent("txt_memberDetails", memDetails[5]);
		logMessage("ASSERT PASSED :" + memDetails[5] + " is verified in txt_memberDetails\n");

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
		logMessage("Step:  go button is clicked in btn_Go\n");
	}

	public void clickOnMemberShipMenu(String menuName) {
		clickUsingXpathInJavaScriptExecutor(element("btn_memberShip", menuName));

		logMessage("Step :" + menuName + " is clicked in btn_memberShip\n");

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
		logMessage("Step : click on page number link in link_pageLink\n");
	}

	public void verifyMemberBenefitsDetail(String caseId, String invoiceNumber) {
		navigateToProductsMenuOnHoveringMore();
		verifyMemberProductDetails("txt_divisionPubName", getOmaSheetValue(caseId, "Iweb Division Name?"));
		verifyMemberProductDetails("txt_divisionPubName", getOmaSheetValue(caseId, "Iweb LS Name?"));

		verifyInvoiceNumber_AACT(invoiceNumber);
		logMessage("ASSERT PASSED : member benefit details is verified in products menu \n");
		navigateToSubscriptionMenuOnHoveringMore();
		verifyMemberProductDetails("txt_divisionPubName", getOmaSheetValue(caseId, "Iweb Pub Name?"));
		logMessage("ASSERT PASSED : member benefit details is verified in subscriptions menu \n");

	}

	public void verifyMemberBenefitsDetail_AACTOMA(String caseId, String invoiceNumber) {
		navigateToProductsMenuOnHoveringMoreAACT();
		verifyMemberProductDetails("txt_divisionPubName", getAACT_OmaSheetValue(caseId, "IWEB AACT Product Name?"));
		verifyInvoiceNumber_AACT(invoiceNumber);
		logMessage("ASSERT PASSED : member benefit details is verified in products menu \n");
		navigateToSubscriptionMenuOnHoveringMoreAACT();
		verifyMemberProductDetails("txt_divisionPubName",
				getAACT_OmaSheetValue(caseId, "IWEB AACT Subscription Name?"));
		logMessage("ASSERT PASSED : member benefit details is verified in subscriptions menu \n");
	}

	public void verifyMemberBenefitsDetail_Reinstate(String caseId, String invoiceNumber) {
		navigateToProductsMenuOnHoveringMore();

		verifyMemberProductDetails_Reinstate("txt_divisionPubName_reinstate",
				getOmaSheetValue(caseId, "Iweb Division Name?"), invoiceNumber);
		verifyMemberProductDetails_Reinstate("txt_divisionPubName_reinstate", getOmaSheetValue(caseId, "Iweb LS Name?"),
				invoiceNumber);
		logMessage("ASSERT PASSED : member benefit details is verified in products menu \n");
		navigateToSubscriptionMenuOnHoveringMore();

		verifyMemberProductDetails_Reinstate("txt_divisionPubName_reinstate",
				getOmaSheetValue(caseId, "Iweb Pub Name?"), invoiceNumber);
		logMessage("ASSERT PASSED : member benefit details is verified in subscriptions menu \n");
	}

	public void navigateToSubscriptionMenuOnHoveringMore() {
		try {
			wait.hardWait(5);
			isElementDisplayed("img_moreMenu");
			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));

			isElementDisplayed("link_moreMenuName", "Subscriptions");
			clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName", "Subscriptions"));

			logMessage("Step : Subscription link is clicked\n");
			waitForSpinner();
			try {
				isElementDisplayed("btn_memberShip", "active subscriptions");
				clickUsingXpathInJavaScriptExecutor(element("btn_memberShip", "active subscriptions"));

				logMessage("Step : Navigate to subscription menu on clicking more button\n");
			} catch (Exception E) {
				logMessage("Step : active subscriptions already expanded");
			}
		} catch (StaleElementReferenceException stEx) {

			wait.hardWait(5);
			isElementDisplayed("img_moreMenu");

			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));

			isElementDisplayed("link_moreMenuName", "Subscriptions");
			element("link_moreMenuName", "Subscriptions").click();
			logMessage("Step : Subscription link is clicked\n");
			waitForSpinner();
			isElementDisplayed("btn_memberShip", "active subscriptions");
			clickUsingXpathInJavaScriptExecutor(element("btn_memberShip", "active subscriptions"));

			logMessage("Step : Navigate to subscription menu on clicking more button\n");
		}

	}

	public void navigateToContactInfoMenuOnHoveringMore() {
		try {
			wait.hardWait(5);
			isElementDisplayed("img_moreMenu");
			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));
			logMessage("Step : Navigate to Contact Info menu on clicking more button\n");
			isElementDisplayed("link_moreMenuName", "Contact Info");
			clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName", "Contact Info"));

			logMessage("Step : Contact Info link is clicked\n");
			waitForSpinner();
			try {
				isElementDisplayed("btn_memberShip", "e-mail addresses");
				clickUsingXpathInJavaScriptExecutor(element("btn_memberShip", "e-mail addresses"));

				logMessage("Step :  e-mail addresses menu is expanded\n");
			} catch (Exception E) {
				logMessage("Step :  e-mail addresses menu already expanded\n");
			}
		} catch (StaleElementReferenceException stEx) {

			wait.hardWait(5);
			isElementDisplayed("img_moreMenu");
			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));
			logMessage("Step : Navigate to Contact Info menu on clicking more button\n");
			isElementDisplayed("link_moreMenuName", "Contact Info");
			clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName", "Contact Info"));

			logMessage("Step : Contact Info link is clicked\n");
			waitForSpinner();
			try {
				isElementDisplayed("btn_memberShip", "e-mail addresses");
				clickUsingXpathInJavaScriptExecutor(element("btn_memberShip", "e-mail addresses"));
				logMessage("Step :  e-mail addresses menu is expanded\n");
			} catch (Exception E) {
				logMessage("Step :  e-mail addresses menu already expanded\n");
			}
		}
	}

	public void navigateToSubscriptionMenuOnHoveringMore_CreateMember() {
		try {
			wait.hardWait(5);
			isElementDisplayed("img_moreMenu");
			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));

			isElementDisplayed("link_moreMenuName", "Subscriptions");
//			element("link_moreMenuName", "Subscriptions").click();
			clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName", "Subscriptions"));
			logMessage("Step : Subscription link is clicked\n");
			waitForSpinner();
			isElementDisplayed("btn_memberShip", "active subscriptions");
			element("btn_memberShip", "active subscriptions").click();
			wait.hardWait(2);
			logMessage("Step : Navigate to subscription menu on clicking more button\n");
		} catch (StaleElementReferenceException stEx) {

			wait.hardWait(5);
			isElementDisplayed("img_moreMenu");

			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));

			isElementDisplayed("link_moreMenuName", "Subscriptions");
			element("link_moreMenuName", "Subscriptions").click();
			logMessage("Step : Subscription link is clicked\n");
			waitForSpinner();
			isElementDisplayed("btn_memberShip", "active subscriptions");
			element("btn_memberShip", "active subscriptions").click();
			logMessage("Step : Navigate to subscription menu on clicking more button\n");
		}

	}

	public void navigateToIssuesMenuOnHoveringMore() {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(3);
		try {
			isElementDisplayed("img_moreMenu");
			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));

			isElementDisplayed("link_moreMenuName", "Issues");
			clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName", "Issues"));

			logMessage("Step : Issues link is clicked\n");
			waitForSpinner();
			isElementDisplayed("btn_memberShip", "issues fulfilled");
			clickUsingXpathInJavaScriptExecutor(element("btn_memberShip", "issues fulfilled"));

			logMessage("Step : Navigate to Issues menu on clicking more button\n");

		} catch (StaleElementReferenceException stEx) {
			isElementDisplayed("img_moreMenu");
			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));

			isElementDisplayed("link_moreMenuName", "Issues");
			clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName", "Issues"));

			logMessage("Step : Issues link is clicked\n");
			waitForSpinner();
			isElementDisplayed("btn_memberShip", "issues fulfilled");
			clickUsingXpathInJavaScriptExecutor(element("btn_memberShip", "issues fulfilled"));

			logMessage("Step : Navigate to Issues menu on clicking more button\n");
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

			logMessage("Step : Entrants link is clicked\n");
			waitForSpinner();
			isElementDisplayed("btn_memberShip", "acs nominee/ entry");
			clickUsingXpathInJavaScriptExecutor(element("btn_memberShip", "acs nominee/ entry"));
			logMessage("Step : Navigate to Entrants menu on clicking more button\n");

		} catch (StaleElementReferenceException stEx) {
			isElementDisplayed("img_moreMenu");
			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));

			isElementDisplayed("link_moreMenuName", "Entrants");
			clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName", "Entrants"));

			logMessage("Step : Entrants link is clicked\n");
			waitForSpinner();
			isElementDisplayed("btn_memberShip", "acs nominee/ entry");
			clickUsingXpathInJavaScriptExecutor(element("btn_memberShip", "acs nominee/ entry"));
			logMessage("Step : Navigate to Entrants menu on clicking more button\n");
		}

	}

	public void navigateToSubscriptionMenuOnHoveringMoreAACT() {
		try {
			isElementDisplayed("img_moreMenu");
			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));

			isElementDisplayed("link_moreMenuName", "Subscriptions");
			clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName", "Subscriptions"));

			logMessage("Step : Subscription link is clicked\n");
			waitForSpinner();
			isElementDisplayed("btn_memberShipAACT", "active subscriptions");
			clickUsingXpathInJavaScriptExecutor(element("btn_memberShipAACT", "active subscriptions"));

			logMessage("Step : Navigate to subscription menu on clicking more button\n");
		} catch (StaleElementReferenceException stEx) {
			isElementDisplayed("img_moreMenu");
			element("img_moreMenu").click();
			isElementDisplayed("link_moreMenuName", "Subscriptions");
			element("link_moreMenuName", "Subscriptions").click();
			logMessage("Step : Subscription link is clicked\n");
			waitForSpinner();
			isElementDisplayed("btn_memberShipAACT", "active subscriptions");
			element("btn_memberShipAACT", "active subscriptions").click();
			logMessage("Step : Navigate to subscription menu on clicking more button\n");
		}

	}

	private void navigateToProductsMenuOnHoveringMore() {
		hardWaitForIEBrowser(10);
		try {
			isElementDisplayed("img_moreMenu");
			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));

			isElementDisplayed("link_moreMenuName", "Products");
			clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName", "Products"));

			logMessage("Step : Product link is clicked\n");
			waitForSpinner();
			wait.hardWait(2);
			hardWaitForIEBrowser(6);
			try {
				isElementDisplayed("btn_memberShip", "services purchased");
				clickUsingXpathInJavaScriptExecutor(element("btn_memberShip", "services purchased"));

				logMessage("Step : services purchased drop down button is clicked\n");
				logMessage("Step : Navigate to products menu on clicking more button\n");
			} catch (Exception E) {
				logMessage("Step : services purchased is already expanded");
			}
		} catch (StaleElementReferenceException stlEx) {
			isElementDisplayed("img_moreMenu");
			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));

			wait.waitForPageToLoadCompletely();
			isElementDisplayed("link_moreMenuName", "Products");
			clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName", "Products"));

			logMessage("Step : Product link is clicked\n");
			waitForSpinner();
			wait.waitForPageToLoadCompletely();
			try {
				isElementDisplayed("btn_memberShip", "services purchased");
				clickUsingXpathInJavaScriptExecutor(element("btn_memberShip", "services purchased"));

				logMessage("Step : Navigate to products menu on clicking more button\n");
			} catch (Exception E) {
				logMessage("Step : services purchased already expanded");
			}
		}
	}

	public void navigateToInvoicesMenuOnHoveringMore() {
		try {
			isElementDisplayed("img_moreMenu");
			element("img_moreMenu").click();
			isElementDisplayed("link_moreMenuName", "Invoices");
			element("link_moreMenuName", "Invoices").click();
			logMessage("Step : Invoices link is clicked\n");
			waitForSpinner();
			wait.hardWait(2);
			wait.waitForPageToLoadCompletely();
			isElementDisplayed("btn_memberShip", "invoices (open batch)");
			element("btn_memberShip", "invoices (open batch)").click();
			logMessage("Step : invoices (open batch) drop down button is clicked\n");
			logMessage("Step : Navigate to Invoices menu on clicking more button\n");
		} catch (StaleElementReferenceException stlEx) {
			isElementDisplayed("img_moreMenu");
			element("img_moreMenu").click();
			wait.waitForPageToLoadCompletely();
			isElementDisplayed("link_moreMenuName", "Invoices");
			element("link_moreMenuName", "Invoices").click();
			logMessage("Step : Invoices link is clicked\n");
			waitForSpinner();
			wait.waitForPageToLoadCompletely();
			isElementDisplayed("btn_memberShip", "invoices (open batch)");
			element("btn_memberShip", "invoices (open batch)").click();
			logMessage("Step : Navigate to Invoices menu on clicking more button\n");
		}
	}

	public void navigateToGeneralMenuOnHoveringMore(String menu) {
		try {
			isElementDisplayed("img_moreMenu");
			element("img_moreMenu").click();
			isElementDisplayed("link_moreMenuName", menu);
			element("link_moreMenuName", menu).click();
			logMessage("Step : " + menu + " link is clicked\n");
			waitForSpinner();
			wait.hardWait(2);
			wait.waitForPageToLoadCompletely();
		} catch (StaleElementReferenceException stlEx) {
			System.out.println("Step : Stale element refrence exception is occured\n");
			isElementDisplayed("img_moreMenu");
			element("img_moreMenu").click();
			isElementDisplayed("link_moreMenuName", menu);
			element("link_moreMenuName", menu).click();
			logMessage("Step : " + menu + " link is clicked\n");
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

			logMessage("Step : Product link is clicked\n");
			waitForSpinner();
			wait.waitForPageToLoadCompletely();
			isElementDisplayed("btn_memberShipAACT", "services purchased");
			clickUsingXpathInJavaScriptExecutor(element("btn_memberShipAACT", "services purchased"));

			logMessage("Step : Navigate to products menu on clicking more button\n");
		} catch (StaleElementReferenceException stlEx) {
			isElementDisplayed("img_moreMenu");
			element("img_moreMenu").click();
			wait.waitForPageToLoadCompletely();
			isElementDisplayed("link_moreMenuName", "Products");
			element("link_moreMenuName", "Products").click();
			logMessage("Step : Product link is clicked\n");
			waitForSpinner();
			wait.waitForPageToLoadCompletely();
			isElementDisplayed("btn_memberShip", "services purchased");
			element("btn_memberShip", "services purchased").click();
			logMessage("Step : Navigate to products menu on clicking more button\n");
		}
	}

	public void verifyNumberOfYears(String numberOfYears) {
		if (numberOfYears.equalsIgnoreCase("")) {
			logMessage("Step : number of years is not present in data\n");
		} else {
			isElementDisplayed("txt_numberOfyears");
			verifyElementText("txt_numberOfyears", numberOfYears);
			logMessage("ASSERT PASSED : " + numberOfYears + " total years of services in txt_numberOfyears\n");
		}
	}

	public String numberOfYearsForInactiveMember() {
		isElementDisplayed("txt_numberOfyears");
		String numberOfYears = element("txt_numberOfyears").getText();

		logMessage("Step : total years of services for inactive member is " + numberOfYears);
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
			logMessage("Step : value of " + productName + " price is empty in data sheet\n");

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
					Assert.assertTrue(priceValueActual.equalsIgnoreCase(PriceValueExpected));
					logMessage("ASSERT PASSED : " + priceValueActual + " is verified in txt_" + detailName + "\n");
					return priceValueActual;
				} else {
					isElementDisplayed("txt_" + detailName, productName);
					String priceValueActual = element("txt_" + detailName, productName).getText().trim();
					Float priceValueInSheet = Float.parseFloat(detailValue.replaceAll("\\$", ""));
					String formatedPrice = String.format("%.02f", priceValueInSheet);
					String PriceValueExpected = String.valueOf(formatedPrice);

					Assert.assertTrue(priceValueActual.equalsIgnoreCase(PriceValueExpected));
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
			logMessage("Step : value of " + productName + " price is empty in data sheet\n");
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
		verifyIndividualDetails("priceValue", getOmaSheetValue(caseId, "Iweb Product Name?"),
				getPriceSheetValue(caseId, "Price value?"), getOmaSheetValue(caseId, "multiYearDecision"));

		String payment = verifyIndividualDetails("payment", getOmaSheetValue(caseId, "Iweb Product Name?"),
				getPriceSheetValue(caseId, "Price value?"), getOmaSheetValue(caseId, "multiYearDecision"));
		Float paymentInFloat = Float.parseFloat(payment.replace("$", ""));

		String balance = verifyIndividualDetails("balance", getOmaSheetValue(caseId, "Iweb Product Name?"), "0.00",
				getOmaSheetValue(caseId, "multiYearDecision"));
		Float balanceInFloat = Float.parseFloat(balance.replace("$", ""));
		String total = String.valueOf(paymentInFloat + balanceInFloat);

		verifyIndividualDetails("total", getOmaSheetValue(caseId, "Iweb Product Name?"), total, "1");

		Float a1 = Float.parseFloat(quantities[0]);
		int quantity_Prod = (int) Math.round(a1);
		String quantity_Product = String.valueOf(quantity_Prod);

		verifyIndividualDetails("quantity", getOmaSheetValue(caseId, "Iweb Product Name?"), quantity_Product, "1");

		verifyStartAndEndDateInFinancialInvoice(getOmaSheetValue(caseId, "Iweb Product Name?"), caseId);
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

	public void expandDetailsMenu(String menuName) {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		hardWaitForIEBrowser(10);
		try {
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("btn_memberShip", menuName);
			clickUsingXpathInJavaScriptExecutor(element("btn_memberShip", menuName));

			logMessage("Step : " + menuName + " is clicked to expand in btn_memberShip\n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (Exception E) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("Step : " + menuName + " already expanded \n");
		}
	}

	private void expandDetailsMenuAACT(String menuName) {
		isElementDisplayed("btn_memberShipAACT", menuName);
		clickUsingXpathInJavaScriptExecutor(element("btn_memberShipAACT", menuName));

		logMessage("Step : " + menuName + " is clicked to expand in btn_memberShipAACT\n");
	}

	public void clickOnButtonToNavigateFinancialPageInMembershipPage() {
		isElementDisplayed("btn_invoiceAtMembership");
		clickUsingXpathInJavaScriptExecutor(element("btn_invoiceAtMembership"));

		logMessage("Step : user navigate to finance page in btn_memberShip\n");
	}

	public void clickOnInvoiceArrowButtonToNavigateFinancialPage() {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		isElementDisplayed("btn_invoicearrow");
		clickUsingXpathInJavaScriptExecutor(element("btn_invoicearrow"));

		logMessage("Step : user navigate to finance page by clicking btn_invoicearrow\n");

	}

	public void clickOnArrowButtonForProductName(String productName) {
		isElementDisplayed("btnArrowProdName", productName);
		clickUsingXpathInJavaScriptExecutor(element("btnArrowProdName", productName));
		// element("btnArrowProdName", productName).click();
		logMessage("Step : Arrow button is clicked for product name " + productName);
	}

	public String getContactId() {
		isElementDisplayed("txt_contactId");
		return element("txt_contactId").getText();
	}

	public void verifyStartAndEndDateInFinancialInvoice(String productName, String caseId) {
		String multiYear = getOmaSheetValue(caseId, "multiYearDecision");
		verifyStartAndEndDate("StartDate", productName, caseId, multiYear);
		verifyStartAndEndDate("EndDate", productName, caseId, multiYear);
	}

	public void verifyStartAndEndDate(String start_EndDate, String productName, String caseId, String multiYear) {
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
			logMessage("ASSERT PASSED : termStartDate is " + currentDate + " verified");
		} else if (start_EndDate.equalsIgnoreCase("EndDate")) {
			System.out.println(element("txt_term" + start_EndDate, productName).getText().trim());
			System.out.println(nextYearDate);
			Assert.assertTrue(
					element("txt_term" + start_EndDate, productName).getText().trim().equalsIgnoreCase(nextYearDate));
			logMessage("ASSERT PASSED : termEndDate is " + nextYearDate + " verified");
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
			logMessage("STEP : wait for spinner to be disappeared \n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (Exception Exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (AssertionError Exp) {
			logMessage("STEP : spinner is not present \n");
		}
	}

	public void navigateToMembershipProfilePage() {
		// expandDetailsMenu("individual memberships");
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
		isElementDisplayed("inp_fieldSelect", feildName);
		selectProvidedTextFromDropDown(element("inp_fieldSelect", feildName), feildValue);
		logMessage("STEP : " + feildValue + " as " + feildValue + " is entered in inp_fieldSelect\n");
	}

	public void SelectFellowNominatorForVerification(String NomineeName, String NominatorName) {
		// System.out.println(element("txt_NominatorName",
		// NomineeName).getText());
		if (element("txt_NominatorName", NomineeName).getText().equals(NominatorName)) {
			// click(element("txt_NominatorName", NomineeName));
			// element("txt_NominatorName", NomineeName).click();
			clickUsingXpathInJavaScriptExecutor(element("txt_NominatorName", NomineeName));
			logMessage("Step : Nominee fellow selected from the list as " + NomineeName);
		} else {
			logMessage("Step : Nominee Fellow is not present in the list\n");
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
		logMessage("Step : email type " + emailType + " is selected\n");
	}

	public void enterEmailIDToAdd(String emailID) {
		isElementDisplayed("inp_emailAddress");
		element("inp_emailAddress").sendKeys(emailID);
		logMessage("Step : enter email ID " + emailID + " to add\n");
	}

	public void clickOnSaveButton() {
		isElementDisplayed("btn_saveButton");
		element("btn_saveButton").click();
		logMessage("Step : save button is clicked \n");
	}

	public void clickOnAddButton() {
		isElementDisplayed("link_addRecordEmail");
		element("link_addRecordEmail").click();
		logMessage("Step : add button is clicked \n");
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
					logMessage("ASSERT PASSED : email ID " + map().get("ncw/cced_email") + " is already present \n");
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
			wait.resetImplicitTimeout(4);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("txt_divisionPubName", awardName);
			element("txt_divisionPubName", awardName).click();
			logMessage("Step: General Award " + awardName + " is selected from the list\n");
		} catch (NoSuchElementException e) {
			wait.resetImplicitTimeout(4);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			try {
				element("txt_userEmail", "2").click();
				wait.waitForPageToLoadCompletely();
				element("txt_divisionPubName", awardName).click();
			} catch (NoSuchElementException e1) {
				element("txt_userEmail", "3").click();
				wait.waitForPageToLoadCompletely();
				element("txt_divisionPubName", awardName).click();
			}
			logMessage("Step: General Award " + awardName + " is selected from the list\n");
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
				// element("txt_tableEntryArrow", NomineeName).click();
				// element("txt_NominatorName", NomineeName).click();
				clickUsingXpathInJavaScriptExecutor(element("txt_tableEntryArrow", NominatorName));
				logMessage("Step : Nominee selected from the list as " + NominatorName);
			} else {
				logMessage("Step : Nominee is not present in the list\n");
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
		logMessage("ASSERT PASSED : citiation field on Award Entry Profile is verified as "
				+ mapAwardsNomination.get("SuggestCitation_Text"));
		verifySupporterNamesOnAwardEntryProfilePage(createMemberCredentials, "1");
		verifySupporterNamesOnAwardEntryProfilePage(createMemberCredentials, "2");
		verifySupporterDocumentsContainsUploadedFile(mapAwardsNomination, "1");
		verifySupporterDocumentsContainsUploadedFile(mapAwardsNomination, "2");
	}

	private void verifySupporterDocumentsContainsUploadedFile(Map<String, String> mapAwardsNomination,
			String SupporterNumber) {
		System.out.println(element("txt_subscriptionName", SupporterNumber).getAttribute("href"));
		System.out.println(mapAwardsNomination.get("FileNameForSupportForm" + SupporterNumber));
		Assert.assertTrue(element("lnk_awardsSupporterDoc", SupporterNumber).getAttribute("href")
				.contains(mapAwardsNomination.get("FileNameForSupportForm" + SupporterNumber)));
		logMessage("ASSERT PASSED : Document for supporter " + SupporterNumber + " succesfully verified as "
				+ mapAwardsNomination.get("FileNameForSupportForm" + SupporterNumber));
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
			System.out.println(elements("lnk_awardsLettersDoc", lettername).get(0).getAttribute("onclick"));
			System.out.println(mapAwardsNomination.get(datasheetValue));
			Assert.assertTrue(elements("lnk_awardsLettersDoc", lettername).get(0).getAttribute("onclick")
					.contains(mapAwardsNomination.get(datasheetValue)));
			logMessage("ASSERT PASSED : File uploaded for Support form 1 is displayed under Documents \n");
		} else if (datasheetValue.equalsIgnoreCase("FileNameForSupportForm2")) {
			System.out.println(elements("lnk_awardsLettersDoc", lettername).get(1).getAttribute("onclick"));
			System.out.println(mapAwardsNomination.get(datasheetValue));
			Assert.assertTrue(elements("lnk_awardsLettersDoc", lettername).get(1).getAttribute("onclick")
					.contains(mapAwardsNomination.get(datasheetValue)));
			logMessage("ASSERT PASSED : File uploaded for Support form 2 is displayed under Documents \n");
		} else {
			System.out.println(element("lnk_awardsLettersDoc", lettername).getAttribute("onclick"));
			System.out.println(mapAwardsNomination.get(datasheetValue));
			Assert.assertTrue(element("lnk_awardsLettersDoc", lettername).getAttribute("onclick")
					.contains(mapAwardsNomination.get(datasheetValue)));
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
		Assert.assertTrue(element("drpdwn_industrytype", "protocols").getText().trim()
				.equals(mapAwardsNomination.get("SafeLabPractices?")));
		logMessage("ASSERT PASSED : Saftey protocols field is verified as "
				+ mapAwardsNomination.get("SafeLabPractices?") + "\n");
		System.out.println(elements("drpdwn_industrytype", "nominee").get(1).getText());
		Assert.assertTrue(elements("drpdwn_industrytype", "nominee").get(1).getText().trim()
				.equals(mapAwardsNomination.get("EligibilityQuestions_professionalDiscipline")));
		logMessage("ASSERT PASSED : Industry type field is verified as "
				+ mapAwardsNomination.get("EligibilityQuestions_professionalDiscipline") + "\n");

	}

	private void clickEditACSAwardsEntryButton() {
		isElementDisplayed("btn_editAwardsEntry");
		element("btn_editAwardsEntry").click();
		logMessage("Step : Edit button on ACS Entry profile page clicked\n");

	}

	public void clickGotoRecordForRenewal() {
		isElementDisplayed("txt_gotorecordrenewal", "1");
		element("txt_gotorecordrenewal", "1").click();
		logMessage("Step : goto record is clicked for latest Invoice\n");
	}

	public void verifyNomineeAddress(String expectedData) {
		handleAlert();
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("txt_individualInfo");
		// logMessage("Expected data:"+expectedData);
		// logMessage("Actual
		// data:"+element("txt_individualInfo").getText().trim());
		Assert.assertTrue(element("txt_individualInfo").getText().trim().contains(expectedData),
				"Assertion Failed: Individual address is not correctly updated");
		logMessage("Assertion Passed: Individual address is updated correctly");
	}

	public void clickOnAcsBiographyImage() {
		isElementDisplayed("img_biography");
		element("img_biography").click();
		logMessage("Info: Clicked on ACS Biography Image");
	}

	public void verifyBioHonorsData(String expectedHonors) {
		wait.waitForPageToLoadCompletely();
		switchToFrame("iframe1");
		isElementDisplayed("txt_bioHonors");
		logMessage("Expected Biography data: " + expectedHonors);
		logMessage("Actual Biography data: " + element("txt_bioHonors").getText().trim());
		Assert.assertTrue(expectedHonors.equals(element("txt_bioHonors").getText().trim()),
				"Assertion Failed: Biography honors data does not matches");
		logMessage("Assertion Passed: Biography honors data matches");
		clickOnCancelButton();
		switchToDefaultContent();
	}

	public void clickOnCancelButton() {
		element("btn_cancel").click();
		logMessage("Info: Clicked on cancel button");
	}

	public void verifyCommitteeMembersStatus(List<String> committeeList) {
		expandDetailsMenu("acsyb nominations");
		for (String committee : committeeList) {
			Assert.assertTrue(element("txt_quantity", committee).getText().trim().equalsIgnoreCase("Pending"));
			logMessage("Assertion Passed: Committee " + committee + " status is pending");
		}
	}

	public boolean verifyCommitteePreferenceDate() {
		String preferenceEndDate, preferenceStartDate;
		int max = 0;
		boolean value;
		expandDetailsMenu("acs committee system options");
		preferenceEndDate = element("txt_quantity", "ACSYBCommiteePreferenceEndDate").getText().trim();
		logMessage("preferenceEndDate   " + preferenceEndDate);
		max = elements("link_pages").size();
		logMessage("Page size: " + max);
		isElementDisplayed("link_paging", String.valueOf(2));
		clickUsingXpathInJavaScriptExecutor(element("link_paging", String.valueOf(2)));
		preferenceStartDate = element("txt_quantity", "ACSYBCommiteePreferenceStartDate").getText().trim();
		logMessage("preferenceStartDate   " + preferenceStartDate);
		value = verfiyEndAndStartDate(preferenceEndDate, preferenceStartDate);
		return value;
	}

	public boolean verfiyEndAndStartDate(String preferenceEndDate, String preferenceStartDate) {
		logMessage("Current Date:" + DateUtil
				.convertStringToDate(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"), "MM/dd/yyyy"));
		logMessage("End Date:" + DateUtil.convertStringToDate(preferenceEndDate, "MM/dd/yyyy"));
		int endDate, startDate;
		endDate = DateUtil
				.convertStringToDate(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"), "MM/dd/yyyy")
				.compareTo(DateUtil.convertStringToDate(preferenceEndDate, "MM/dd/yyyy"));

		logMessage("Current Date:" + DateUtil
				.convertStringToDate(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"), "MM/dd/yyyy"));
		logMessage("Start Date:" + DateUtil.convertStringToDate(preferenceStartDate, "MM/dd/yyyy"));
		startDate = DateUtil
				.convertStringToDate(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"), "MM/dd/yyyy")
				.compareTo(DateUtil.convertStringToDate(preferenceStartDate, "MM/dd/yyyy"));

		if (endDate == -1 && startDate == 1)
			return true;
		else
			return false;
	}

	public String getIndividualInformationWhichHasTerminateValueNull(int n) {
		int i=1;
		String value = null;
			for (WebElement element : elements("list_individualMem")) {
				System.out.println("Lenght::"+element.getText().length());
			 if(element.getText().length() == 1){
				 value = element("individualmem_data",toString().valueOf(i),toString().valueOf(n)).getText().trim();
					break;
				}
			 i++;
			}
			System.out.println("Value=="+value);
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

	public void enterValueInMemberStatusField(String dropdown , String option) {
		wait.hardWait(2);
		isElementDisplayed("select_member",dropdown);
		selectProvidedTextFromDropDown(element("select_member",dropdown), option);
		logMessage("STEP : Selected " + option + " from dropdown "+dropdown);
	}

	public void verifyTableUnderExpandedBarIsNotEmpty(String barName) {
		Assert.assertTrue(elements("btn_invoicearrow").size()>0,"Table under"+barName+" is empty");
		logMessage("Table umder "+barName+" is not empty\n");
		
	}

	public void selectRandomGotoRecord(String barName) {
		Random rand = new Random();
		int randomNumber = rand.nextInt(((elements("btn_invoicearrow").size()-1) - 0) + 1) + 0;
		System.out.println(randomNumber);
		elements("btn_invoicearrow").get(randomNumber).click();
		logMessage("Step : "+barName+" number "+randomNumber+" is selected from the list\n");

	}

	/*
	 * public boolean verifyCommitteePreferenceDate(){ String
	 * preferenceEndDate,preferenceStartDate; int max=0; boolean value;
	 * expandDetailsMenu("acs committee system options");
	 * preferenceEndDate=element("txt_quantity","ACSYBCommiteePreferenceEndDate"
	 * ).getText().trim(); logMessage("preferenceEndDate ::  "
	 * +preferenceEndDate); max=elements("link_pages").size(); logMessage(
	 * "Page size: "+max); isElementDisplayed("link_paging", String.valueOf(2));
	 * clickUsingXpathInJavaScriptExecutor(element("link_paging",
	 * String.valueOf(2))); preferenceStartDate=element("txt_quantity",
	 * "ACSYBCommiteePreferenceStartDate").getText().trim(); logMessage(
	 * "preferenceStartDate  :: "+preferenceStartDate);
	 * value=verfiyEndAndStartDate(preferenceEndDate,preferenceStartDate);
	 * return value; }
	 * 
	 * public boolean verfiyEndAndStartDate(String preferenceEndDate, String
	 * preferenceStartDate){ logMessage("Current Date:"
	 * +DateUtil.convertStringToDate(DateUtil.
	 * getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"),"MM/dd/yyyy"));
	 * logMessage("End Date:"
	 * +DateUtil.convertStringToDate(preferenceEndDate,"MM/dd/yyyy")); int
	 * endDate,startDate; endDate=DateUtil.convertStringToDate(DateUtil.
	 * getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"),"MM/dd/yyyy")
	 * .compareTo(DateUtil.convertStringToDate(preferenceEndDate,"MM/dd/yyyy"));
	 * 
	 * logMessage("Current Date:"+DateUtil.convertStringToDate(DateUtil.
	 * getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"),"MM/dd/yyyy"));
	 * logMessage("Start Date:"
	 * +DateUtil.convertStringToDate(preferenceStartDate,"MM/dd/yyyy"));
	 * startDate=DateUtil.convertStringToDate(DateUtil.
	 * getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"),"MM/dd/yyyy")
	 * .compareTo(DateUtil.convertStringToDate(preferenceStartDate,"MM/dd/yyyy")
	 * );
	 * 
	 * if(endDate==-1 && startDate==1) return true; else return false; =======
	 * 
	 * public void verifyCommitteeMembersStatus(String name) {
	 * expandDetailsMenu("acsyb nominations"); for (WebElement ele :
	 * elements("txt_total", name)) {
	 * Assert.assertTrue(ele.getText().trim().equals("Pending"),
	 * "Assertion Failed: Committee member status is not pending"); logMessage(
	 * "Assertion Passed: Committee member status is pending"); } }
	 * 
	 * public boolean verifyCommitteePreferenceDate() { String
	 * preferenceEndDate, preferenceStartDate; int max = 0; boolean value;
	 * expandDetailsMenu("acs committee system options"); preferenceEndDate =
	 * element("txt_quantity",
	 * "ACSYBCommiteePreferenceEndDate").getText().trim(); logMessage(
	 * "preferenceEndDate   " + preferenceEndDate); max =
	 * elements("link_pages").size(); logMessage("Page size: " + max);
	 * isElementDisplayed("link_paging", String.valueOf(2));
	 * clickUsingXpathInJavaScriptExecutor(element("link_paging",
	 * String.valueOf(2))); preferenceStartDate = element("txt_quantity",
	 * "ACSYBCommiteePreferenceStartDate").getText().trim(); logMessage(
	 * "preferenceStartDate   " + preferenceStartDate); value =
	 * verfiyEndAndStartDate(preferenceEndDate, preferenceStartDate);
	 * logMessage(value + "------"); return value; }
	 * 
	 * public boolean verfiyEndAndStartDate(String preferenceEndDate, String
	 * preferenceStartDate) { logMessage("Current Date:" +
	 * DateUtil.convertStringToDate(DateUtil
	 * .getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"), "MM/dd/yyyy"));
	 * logMessage("End Date:" + DateUtil.convertStringToDate(preferenceEndDate,
	 * "MM/dd/yyyy")); int endDate, startDate; endDate =
	 * DateUtil.convertStringToDate(
	 * DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"),
	 * "MM/dd/yyyy").compareTo( DateUtil.convertStringToDate(preferenceEndDate,
	 * "MM/dd/yyyy"));
	 * 
	 * logMessage("Current Date:" + DateUtil.convertStringToDate(DateUtil
	 * .getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"), "MM/dd/yyyy"));
	 * logMessage("Start Date:" +
	 * DateUtil.convertStringToDate(preferenceStartDate, "MM/dd/yyyy"));
	 * startDate = DateUtil.convertStringToDate(
	 * DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"),
	 * "MM/dd/yyyy") .compareTo(
	 * DateUtil.convertStringToDate(preferenceStartDate, "MM/dd/yyyy"));
	 * 
	 * if (endDate == -1 && startDate == 1) return true; else return false;
	 * >>>>>>> bafcd3aaaa988807dc6ca4df86608e379bc30110 }
	 */
}
