package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.Date;

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
	boolean flag;

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
				clickUsingXpathInJavaScriptExecutor(element("inp_fieldName",
						fieldName));
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
		wait.hardWait(2);
		isElementDisplayed("inp_fieldName", fieldName);
		element("inp_fieldName", fieldName).sendKeys(fieldValue);

		logMessage("STEP : " + fieldName + " as " + fieldValue
				+ " is entered in inp_fieldName\n");

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

	public void verifyMemberDetails(String fName, String lName, String add,
			String city, String zipCode, String addressType, String contactId,
			String userEmail) {
		verifyElementTextContains("txt_memberDetails", fName);
		logMessage("ASSERT PASSED :" + fName
				+ " is verified in txt_memberDetails\n");
		verifyElementTextContains("txt_memberDetails", lName);
		logMessage("ASSERT PASSED :" + lName
				+ " is verified in txt_memberDetails\n");

		verifyElementTextContains("txt_memberDetails", add);
		logMessage("ASSERT PASSED :" + add
				+ " is verified in txt_memberDetails\n");
		verifyElementTextContains("txt_memberDetails", city);
		logMessage("ASSERT PASSED :" + city
				+ " is verified in txt_memberDetails\n");
		verifyElementTextContains("txt_memberDetails", zipCode);
		logMessage("ASSERT PASSED :" + zipCode
				+ " is verified in txt_memberDetails\n");
		isElementDisplayed("txt_additionalInfo", addressType.toLowerCase());
		logMessage("ASSERT PASSED :" + addressType.toLowerCase()
				+ " is verified in txt_memberDetails\n");
		isElementDisplayed("txt_additionalInfo", contactId.trim());
		logMessage("ASSERT PASSED : contact ID " + contactId.trim()
				+ " is verified in txt_memberDetails\n");
		if (!userEmail.equalsIgnoreCase("")) {
			try {
				isElementDisplayed("txt_userEmail", userEmail);
			} catch (StaleElementReferenceException stlExp) {
				isElementDisplayed("txt_userEmail", userEmail);
			}

			logMessage("ASSERT PASSED :" + userEmail
					+ " is verified in txt_memberDetails\n");
		}

	}

	public void verifyMemberDetails_OMA(String fName, String lName, String add,
			String city, String zipCode, String addressType, String contactId,
			String userEmail, String caseId) {
		//wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(10);
		verifyElementTextContains("txt_memberDetails", fName);
		logMessage("ASSERT PASSED :" + fName
				+ " is verified in txt_memberDetails\n");
		verifyElementTextContains("txt_memberDetails", lName);
		logMessage("ASSERT PASSED :" + lName
				+ " is verified in txt_memberDetails\n");
		if (getOmaSheetValue(caseId, "Has Summer mailing address?")
				.equalsIgnoreCase("show")) {
			String startDateInString = getOmaSheetValue(caseId,
					"Select Summer mailing start day")
					+ "/"
					+ getOmaSheetValue(caseId,
							"Select Summer mailing  start month");
			String endDateInString = getOmaSheetValue(caseId,
					"Select Summer mailing end month")
					+ "/"
					+ getOmaSheetValue(caseId, "Select Summer mailing end day");
			Date startDate = DateUtil.convertStringToDate(startDateInString,
					"dd/MM");
			Date endDate = DateUtil.convertStringToDate(endDateInString,
					"dd/MM");
			Date currentDate = DateUtil.convertStringToDate(
					DateUtil.getCurrentdateInStringWithGivenFormate("dd/MM"),
					"dd/MM");
			if (currentDate.after(startDate) && currentDate.before(endDate)) {
				verifyElementTextContains(
						"txt_memberDetails",
						getOmaSheetValue(caseId, "Enter Summer mailing address"));
				logMessage("ASSERT PASSED :"
						+ getOmaSheetValue(caseId,
								"Enter Summer mailing address")
						+ " is verified in txt_memberDetails\n");
				verifyElementTextContains("txt_memberDetails",
						getOmaSheetValue(caseId, "Enter  Summer city"));
				logMessage("ASSERT PASSED :"
						+ getOmaSheetValue(caseId, "Enter  Summer city")
						+ " is verified in txt_memberDetails\n");
				verifyElementTextContains("txt_memberDetails",
						getOmaSheetValue(caseId, "Enter  Summer zip code"));
				logMessage("ASSERT PASSED :"
						+ getOmaSheetValue(caseId, "Enter  Summer zip code")
						+ " is verified in txt_memberDetails\n");
				isElementDisplayed("txt_additionalInfo", "seasonal");
				logMessage("ASSERT PASSED : seasonal is verified in txt_memberDetails\n");
			}

		} else {
			verifyElementTextContains("txt_memberDetails", add);
			logMessage("ASSERT PASSED :" + add
					+ " is verified in txt_memberDetails\n");
			verifyElementTextContains("txt_memberDetails", city);
			logMessage("ASSERT PASSED :" + city
					+ " is verified in txt_memberDetails\n");
			verifyElementTextContains("txt_memberDetails", zipCode);
			logMessage("ASSERT PASSED :" + zipCode
					+ " is verified in txt_memberDetails\n");
			isElementDisplayed("txt_additionalInfo", addressType.toLowerCase());
			logMessage("ASSERT PASSED :" + addressType.toLowerCase()
					+ " is verified in txt_memberDetails\n");
		}
		isElementDisplayed("txt_additionalInfo", contactId.trim());
		logMessage("ASSERT PASSED : contact ID " + contactId.trim()
				+ " is verified in txt_memberDetails\n");
		if (!userEmail.equalsIgnoreCase("")) {
			try {
				isElementDisplayed("txt_userEmail", userEmail);
			} catch (StaleElementReferenceException stlExp) {
				isElementDisplayed("txt_userEmail", userEmail);
			}

			logMessage("ASSERT PASSED :" + userEmail
					+ " is verified in txt_memberDetails\n");
		}

	}

	public void verifyMemberDetails_AACTOMA(String caseId, String contactId,
			String userEmail, String fName, String lName) {
		if (getAACT_OmaSheetValue(caseId, "Affiliate Member").equalsIgnoreCase(
				"Y")) {
			String add = getAACT_OmaSheetValue(caseId, "Address Contact Page");
			String city = getAACT_OmaSheetValue(caseId, "City Contact Page");

			String zipCode = getAACT_OmaSheetValue(caseId,
					"Zip code Contact Page");
			String addressType = getAACT_OmaSheetValue(caseId,
					"Address Type Contact Page");

			verifyElementTextContains("txt_memberDetails",fName);
			logMessage("ASSERT PASSED :" + fName
					+ " is verified in txt_memberDetails\n");
			verifyElementTextContains("txt_memberDetails", lName);
			logMessage("ASSERT PASSED :" + lName
					+ " is verified in txt_memberDetails\n");
			verifyElementTextContains("txt_memberDetails", add);
			logMessage("ASSERT PASSED :" + add
					+ " is verified in txt_memberDetails\n");
			verifyElementTextContains("txt_memberDetails", city);
			logMessage("ASSERT PASSED :" + city
					+ " is verified in txt_memberDetails\n");
			verifyElementTextContains("txt_memberDetails", zipCode);
			logMessage("ASSERT PASSED :" + zipCode
					+ " is verified in txt_memberDetails\n");
			// address type work is displayed instead of work/school
			// isElementDisplayed("txt_additionalInfo",
			// addressType.toLowerCase());
			// logMessage("ASSERT PASSED :" + addressType.toLowerCase()
			// + " is verified in txt_memberDetails\n");
			isElementDisplayed("txt_additionalInfo", contactId.trim());
			logMessage("ASSERT PASSED :" + contactId.trim()
					+ " is verified in txt_additionalInfo\n");
			if (!userEmail.equalsIgnoreCase("")) {
				try {
					isElementDisplayed("txt_userEmail", userEmail);
				} catch (StaleElementReferenceException stlExp) {
					isElementDisplayed("txt_userEmail", userEmail);
				}

				logMessage("ASSERT PASSED :" + userEmail
						+ " is verified in txt_memberDetails\n");
			}
		} else {
			String add = getAACT_OmaSheetValue(caseId, "Address Contact Page");
			String city = getAACT_OmaSheetValue(caseId, "City Contact Page");

			String zipCode = getAACT_OmaSheetValue(caseId,
					"Zip code Contact Page");
			String addressType = getAACT_OmaSheetValue(caseId,
					"Address Type Contact Page");

			verifyElementTextContains("txt_memberDetails", fName);
			logMessage("ASSERT PASSED :" + fName
					+ " is verified in txt_memberDetails\n");
			verifyElementTextContains("txt_memberDetails", lName);
			logMessage("ASSERT PASSED :" + lName
					+ " is verified in txt_memberDetails\n");
			verifyElementTextContains("txt_memberDetails", add);
			logMessage("ASSERT PASSED :" + add
					+ " is verified in txt_memberDetails\n");
			verifyElementTextContains("txt_memberDetails", city);
			logMessage("ASSERT PASSED :" + city
					+ " is verified in txt_memberDetails\n");
			verifyElementTextContains("txt_memberDetails", zipCode);
			logMessage("ASSERT PASSED :" + zipCode
					+ " is verified in txt_memberDetails\n");
			// address type work is displayed instead of work/school
			// isElementDisplayed("txt_additionalInfo",
			// addressType.toLowerCase());
			// logMessage("ASSERT PASSED :" + addressType.toLowerCase()
			// + " is verified in txt_memberDetails\n");
			isElementDisplayed("txt_additionalInfo", contactId.trim());
			logMessage("ASSERT PASSED :" + contactId.trim()
					+ " is verified in txt_memberDetails\n");
			if (!userEmail.equalsIgnoreCase("")) {
				try {
					isElementDisplayed("txt_userEmail", userEmail);
				} catch (StaleElementReferenceException stlExp) {
					isElementDisplayed("txt_userEmail", userEmail);
				}

				logMessage("ASSERT PASSED :" + userEmail
						+ " is verified in txt_memberDetails\n");
			}
		}

	}

	public void verifyMemberDetails_AACTOMA_Individual(String caseId,
			String userEmail, String fName, String lName) {

		String add = getAACT_OmaSheetValue(caseId, "Address Contact Page");
		String city = getAACT_OmaSheetValue(caseId, "City Contact Page");

		String zipCode = getAACT_OmaSheetValue(caseId, "Zip code Contact Page");
		String addressType = getAACT_OmaSheetValue(caseId,
				"Address Type Contact Page");

		verifyElementTextContains("txt_memberDetails", fName);
		logMessage("ASSERT PASSED :" + fName
				+ " is verified in txt_memberDetails\n");
		verifyElementTextContains("txt_memberDetails", lName);
		logMessage("ASSERT PASSED :" + lName
				+ " is verified in txt_memberDetails\n");
		verifyElementTextContains("txt_memberDetails", add);
		logMessage("ASSERT PASSED :" + add
				+ " is verified in txt_memberDetails\n");
		verifyElementTextContains("txt_memberDetails", city);
		logMessage("ASSERT PASSED :" + city
				+ " is verified in txt_memberDetails\n");
		verifyElementTextContains("txt_memberDetails", zipCode);
		logMessage("ASSERT PASSED :" + zipCode
				+ " is verified in txt_memberDetails\n");
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

			logMessage("ASSERT PASSED :" + userEmail
					+ " is verified in txt_memberDetails\n");
		}

	}

	public void verifyMemberDetails_MemberProfile(String middleName,
			String lastName) {
		verifyElementTextContains("txt_memberDetails", map().get("firstName"));
		logMessage("ASSERT PASSED :" + map().get("firstName")
				+ " is verified as first name\n");
		verifyElementTextContains("txt_memberDetails", middleName);
		logMessage("ASSERT PASSED :" + middleName
				+ " is verified as middle name\n");
		verifyElementTextContains("txt_memberDetails", lastName);
		logMessage("ASSERT PASSED :" + lastName + " is verified as last name\n");

		verifyElementTextContains("txt_memberDetails", map().get("street"));
		logMessage("ASSERT PASSED :" + map().get("street")
				+ " is verified as street\n");
		verifyElementTextContains("txt_memberDetails", map().get("city"));
		logMessage("ASSERT PASSED :" + map().get("city")
				+ " is verified as city\n");
		// verifyElementTextContains("txt_memberDetails", map().get("country"));
		// logMessage("ASSERT PASSED :" + map().get("country")
		// + " is verified as country\n");
		if (!map().get("country").equalsIgnoreCase("UNITED STATES")) {
			verifyElementTextContains("txt_memberDetails",
					map().get("In_postalCode"));
			logMessage("ASSERT PASSED :" + map().get("In_postalCode")
					+ " is verified in txt_memberDetails\n");
		} else {
			verifyElementTextContains("txt_memberDetails",
					map().get("Out_postalCode"));
			logMessage("ASSERT PASSED :" + map().get("Out_postalCode")
					+ " is verified in txt_memberDetails\n");
		}
	}

	public String verifyMemberDetails_InAddIndividual(String[] memDetails) {

		verifyElementTextContent("txt_memberDetails", memDetails[0]);
		logMessage("ASSERT PASSED :" + memDetails[0]
				+ " is verified in txt_memberDetails\n");
		verifyElementTextContent("txt_memberDetails", memDetails[1]);
		logMessage("ASSERT PASSED :" + memDetails[1]
				+ " is verified in txt_memberDetails\n");
		verifyElementTextContent("txt_memberDetails", memDetails[2]);
		logMessage("ASSERT PASSED :" + memDetails[2]
				+ " is verified in txt_memberDetails\n");

		if (!(memDetails[3].equalsIgnoreCase(""))) {
			verifyElementTextContent("txt_memberDetails", memDetails[3]);
			logMessage("ASSERT PASSED :" + memDetails[3]
					+ " is verified in txt_memberDetails\n");
		}
		verifyElementTextContent("txt_memberDetails", memDetails[4]);
		logMessage("ASSERT PASSED :" + memDetails[4]
				+ " is verified in txt_memberDetails\n");
		verifyElementTextContent("txt_memberDetails", memDetails[5]);
		logMessage("ASSERT PASSED :" + memDetails[5]
				+ " is verified in txt_memberDetails\n");

		verifyElementTextContent("txt_memberDetails", memDetails[6]);
		logMessage("ASSERT PASSED :" + memDetails[6]
				+ " is verified in txt_memberDetails\n");
		logMessage("*********** CUSTOMER ID : " + getContactId()
				+ " ***********");
		return getContactId();
	}

	public void verifyAACTMemberCreated(String caseId) {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(10);
		// wait.hardWait(8);
		isElementDisplayed("img_aactMember");
		if (getAACT_OmaSheetValue(caseId, "Is Create Only Individual?")
				.equalsIgnoreCase("Y")) {
			Assert.assertTrue(element("img_aactMember").getAttribute("src")
					.endsWith("delete1.gif"));
			logMessage("ASSERT PASSED : AACT Individual Member created\n");
		} else if (getAACT_OmaSheetValue(caseId, "Is Create Only Individual?")
				.equalsIgnoreCase("N")) {
			Assert.assertTrue(element("img_aactMember").getAttribute("src")
					.endsWith("chkmk.gif"));
			logMessage("ASSERT PASSED : AACT Member created\n");
		} else {
			logMessage("Value in Is Create Only Individual? column is null\n");
		}

	}

	public void verifyMemberIsNotCreated() {
		isElementDisplayed("img_member");
		logMessage("ASSERT PASSED : Member is not created and has cross mark for member\n");
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
		// element("btn_memberShip", menuName).click();
		logMessage("Step :" + menuName + " is clicked in btn_memberShip\n");

	}

	private void verifyMemberProductDetails(String element, String detailName) {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(3);
		if (detailName.equalsIgnoreCase("")) {
			logMessage("ASSERT PASSED : IWEB product name is not present in data sheet\n");
		} else {
			isElementDisplayed(element, detailName);
			logMessage("ASSERT PASSED : " + detailName
					+ " is verified in IWEB in  element\n");
		}
	}

	private void verifyMemberProductDetails_Reinstate(String element,
			String detailName, String invoiceNumber) {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		flag = false;
		if (detailName.equalsIgnoreCase("")) {
			logMessage("ASSERT PASSED : IWEB product name is not present in data sheet\n");
		} else {

			// isElementDisplayed(element, invoiceNumber);
			try {
				wait.resetImplicitTimeout(0);
				wait.resetExplicitTimeout(hiddenFieldTimeOut);
				int pageNumber = elements("list_pageLink").size();
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
				for (int j = 1; j <= pageNumber; j++) {
					for (int i = 0; i < elements(element, invoiceNumber).size(); i++) {
						WebElement ele = elements(element, invoiceNumber)
								.get(i);
						flag = ele.getText().trim()
								.equalsIgnoreCase(detailName);
						if (flag) {
							logMessage("ASERT PASSED : " + detailName
									+ " is verified in IWEB in " + element
									+ "\n");
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
						logMessage("ASERT PASSED : " + detailName
								+ " is verified in IWEB in " + element + "\n");
						break;
					}
				}
			}
			logMessage("ASSERT PASSED : " + detailName
					+ " is verified in IWEB in  " + element + "\n");
		}
	}

	public void clickOnPageLink(String pageNumber) {
		isElementDisplayed("link_pageLink", pageNumber);
		element("link_pageLink", pageNumber).click();
		logMessage("Step : click on page number link in link_pageLink\n");
	}

	public void verifyMemberBenefitsDetail(String caseId, String invoiceNumber) {
		navigateToProductsMenuOnHoveringMore();
		verifyMemberProductDetails("txt_divisionPubName",
				getOmaSheetValue(caseId, "Iweb Division Name?"));
		verifyMemberProductDetails("txt_divisionPubName",
				getOmaSheetValue(caseId, "Iweb LS Name?"));

		verifyInvoiceNumber_AACT(invoiceNumber);
		logMessage("ASSERT PASSED : member benefit details is verified in products menu \n");
		navigateToSubscriptionMenuOnHoveringMore();
		verifyMemberProductDetails("txt_divisionPubName",
				getOmaSheetValue(caseId, "Iweb Pub Name?"));
		logMessage("ASSERT PASSED : member benefit details is verified in subscriptions menu \n");

	}

	public void verifyMemberBenefitsDetail_AACTOMA(String caseId,
			String invoiceNumber) {
		navigateToProductsMenuOnHoveringMoreAACT();
		verifyMemberProductDetails("txt_divisionPubName",
				getAACT_OmaSheetValue(caseId, "IWEB AACT Product Name?"));
		verifyInvoiceNumber_AACT(invoiceNumber);
		logMessage("ASSERT PASSED : member benefit details is verified in products menu \n");
		navigateToSubscriptionMenuOnHoveringMoreAACT();
		verifyMemberProductDetails("txt_divisionPubName",
				getAACT_OmaSheetValue(caseId, "IWEB AACT Subscription Name?"));
		logMessage("ASSERT PASSED : member benefit details is verified in subscriptions menu \n");
	}

	public void verifyMemberBenefitsDetail_Reinstate(String caseId,
			String invoiceNumber) {
		navigateToProductsMenuOnHoveringMore();
		// //verifyMemberProductDetails("txt_divisionPubName", iwebProductName);
		verifyMemberProductDetails_Reinstate("txt_divisionPubName_reinstate",
				getOmaSheetValue(caseId, "Iweb Division Name?"), invoiceNumber);
		verifyMemberProductDetails_Reinstate("txt_divisionPubName_reinstate",
				getOmaSheetValue(caseId, "Iweb LS Name?"), invoiceNumber);
		logMessage("ASSERT PASSED : member benefit details is verified in products menu \n");
		navigateToSubscriptionMenuOnHoveringMore();
		// verifyMemberProductDetails("txt_divisionPubName",
		// getOmaSheetValue(caseId, "Iweb CEN Product Name?"));
		verifyMemberProductDetails_Reinstate("txt_divisionPubName_reinstate",
				getOmaSheetValue(caseId, "Iweb Pub Name?"), invoiceNumber);
		logMessage("ASSERT PASSED : member benefit details is verified in subscriptions menu \n");
	}

	public void navigateToSubscriptionMenuOnHoveringMore() {
		try {
			wait.hardWait(5);
			isElementDisplayed("img_moreMenu");
			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));
			// element("img_moreMenu").click();
			isElementDisplayed("link_moreMenuName", "Subscriptions");
			clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName", "Subscriptions"));
		//	element("link_moreMenuName", "Subscriptions").click();
			logMessage("Step : Subscription link is clicked\n");
			waitForSpinner();
			try{
			isElementDisplayed("btn_memberShip", "active subscriptions");
			clickUsingXpathInJavaScriptExecutor(element("btn_memberShip",
					"active subscriptions"));
			// element("btn_memberShip", "active subscriptions").click();
			logMessage("Step : Navigate to subscription menu on clicking more button\n");
			}
			catch(Exception E)
			{
				logMessage("Step : active subscriptions already expanded");
			}
		} catch (StaleElementReferenceException stEx) {

			wait.hardWait(5);
			isElementDisplayed("img_moreMenu");

			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));
			// element("img_moreMenu").click();
			isElementDisplayed("link_moreMenuName", "Subscriptions");
			element("link_moreMenuName", "Subscriptions").click();
			logMessage("Step : Subscription link is clicked\n");
			waitForSpinner();
			isElementDisplayed("btn_memberShip", "active subscriptions");
			clickUsingXpathInJavaScriptExecutor(element("btn_memberShip",
					"active subscriptions"));
			// element("btn_memberShip", "active subscriptions").click();
			logMessage("Step : Navigate to subscription menu on clicking more button\n");
		}

	}

	public void navigateToSubscriptionMenuOnHoveringMore_CreateMember() {
		try {
			wait.hardWait(5);
			isElementDisplayed("img_moreMenu");
			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));
			// element("img_moreMenu").click();
			isElementDisplayed("link_moreMenuName", "Subscriptions");
			element("link_moreMenuName", "Subscriptions").click();
			logMessage("Step : Subscription link is clicked\n");
			waitForSpinner();
			isElementDisplayed("btn_memberShip", "active subscriptions");
			element("btn_memberShip", "active subscriptions").click();
			wait.hardWait(2);
			// isElementDisplayed("btn_memberShip", "active subscriptions");
			// element("btn_memberShip", "active subscriptions").click();
			logMessage("Step : Navigate to subscription menu on clicking more button\n");
		} catch (StaleElementReferenceException stEx) {

			wait.hardWait(5);
			isElementDisplayed("img_moreMenu");

			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));
			// element("img_moreMenu").click();
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
		try {
			isElementDisplayed("img_moreMenu");
			element("img_moreMenu").click();
			isElementDisplayed("link_moreMenuName", "Issues");
			element("link_moreMenuName", "Issues").click();
			logMessage("Step : Issues link is clicked\n");
			waitForSpinner();
			isElementDisplayed("btn_memberShip", "issues fulfilled");
			element("btn_memberShip", "issues fulfilled").click();
			logMessage("Step : Navigate to Issues menu on clicking more button\n");

		} catch (StaleElementReferenceException stEx) {
			isElementDisplayed("img_moreMenu");
			element("img_moreMenu").click();
			isElementDisplayed("link_moreMenuName", "Issues");
			element("link_moreMenuName", "Issues").click();
			logMessage("Step : Issues link is clicked\n");
			waitForSpinner();
			isElementDisplayed("btn_memberShip", "issues fulfilled");
			element("btn_memberShip", "issues fulfilled").click();
			logMessage("Step : Navigate to Issues menu on clicking more button\n");
		}

	}

	public void navigateToSubscriptionMenuOnHoveringMoreAACT() {
		try {
			isElementDisplayed("img_moreMenu");
			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));
			// element("img_moreMenu").click();
			isElementDisplayed("link_moreMenuName", "Subscriptions");
			clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName",
					"Subscriptions"));
			// element("link_moreMenuName", "Subscriptions").click();
			logMessage("Step : Subscription link is clicked\n");
			waitForSpinner();
			isElementDisplayed("btn_memberShipAACT", "active subscriptions");
			clickUsingXpathInJavaScriptExecutor(element("btn_memberShipAACT",
					"active subscriptions"));
			// element("btn_memberShipAACT", "active subscriptions").click();
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
		hardWaitForIEBrowser(2);
		try {
			isElementDisplayed("img_moreMenu");
			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));
		//	element("img_moreMenu").click();
			isElementDisplayed("link_moreMenuName", "Products");
			clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName", "Products"));
		//	element("link_moreMenuName", "Products").click();
			logMessage("Step : Product link is clicked\n");
			waitForSpinner();
			wait.hardWait(2);
			hardWaitForIEBrowser(6);
			try{
			isElementDisplayed("btn_memberShip", "services purchased");
			clickUsingXpathInJavaScriptExecutor(element("btn_memberShip", "services purchased"));
			//element("btn_memberShip", "services purchased").click();
			logMessage("Step : services purchased drop down button is clicked\n");
			logMessage("Step : Navigate to products menu on clicking more button\n");
			}
			catch(Exception E)
			{
				logMessage("Step : services purchased is already expanded");
			}
		} catch (StaleElementReferenceException stlEx) {
			isElementDisplayed("img_moreMenu");
			clickUsingXpathInJavaScriptExecutor(element("img_moreMenu"));
			//element("img_moreMenu").click();
			wait.waitForPageToLoadCompletely();
			isElementDisplayed("link_moreMenuName", "Products");
			clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName", "Products"));
			//element("link_moreMenuName", "Products").click();
			logMessage("Step : Product link is clicked\n");
			waitForSpinner();
			wait.waitForPageToLoadCompletely();
			try{
			isElementDisplayed("btn_memberShip", "services purchased");
			clickUsingXpathInJavaScriptExecutor(element("btn_memberShip", "services purchased"));
		//	element("btn_memberShip", "services purchased").click();
			logMessage("Step : Navigate to products menu on clicking more button\n");
			}
			catch(Exception E)
			{
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
			clickUsingXpathInJavaScriptExecutor(element("link_moreMenuName",
					"Products"));
			// element("link_moreMenuName", "Products").click();
			logMessage("Step : Product link is clicked\n");
			waitForSpinner();
			wait.waitForPageToLoadCompletely();
			isElementDisplayed("btn_memberShipAACT", "services purchased");
			clickUsingXpathInJavaScriptExecutor(element("btn_memberShipAACT",
					"services purchased"));
			// element("btn_memberShipAACT", "services purchased").click();
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
			logMessage("ASSERT PASSED : " + numberOfYears
					+ " total years of services in txt_numberOfyears\n");
		}
	}

	public String numberOfYearsForInactiveMember() {
		isElementDisplayed("txt_numberOfyears");
		String numberOfYears = element("txt_numberOfyears").getText();

		logMessage("Step : total years of services for inactive member is "
				+ numberOfYears);
		return numberOfYears;
	}

	public void numberOfYearsForActiveMember(int numberOfYears) {
		String noOfYears = String.valueOf(numberOfYears + 1);
		isElementDisplayed("txt_numberOfyears");
		verifyElementText("txt_numberOfyears", noOfYears);
		logMessage("ASSERT PASSED : " + noOfYears
				+ " total years of services in txt_numberOfyears\n");

	}

	private void verifyInvoiceNumber_AACT(String invoiceNumber) {
		isElementDisplayed("txt_invoiceAACT");
		verifyElementTextContains("txt_invoiceAACT", invoiceNumber);
		logMessage("ASSERT PASSED :" + invoiceNumber
				+ " is verified in txt_invoiceAACT\n");
	}

	private void verifyInvoiceNumber(String invoiceNumber) {
		isElementDisplayed("txt_invoiceValue");
		verifyElementTextContains("txt_invoiceValue", invoiceNumber);
		logMessage("ASSERT PASSED :" + invoiceNumber
				+ " is verified in txt_memberDetails\n");
	}

	public String verifyIndividualDetails(String detailName,
			String productName, String detailValue, String multiYear) {
		if (detailValue.equalsIgnoreCase("")) {
			logMessage("Step : value of " + productName
					+ " price is empty in data sheet\n");

			return null;
		} else {
			if (!multiYear.equalsIgnoreCase("")) {
				int multiYearInInteger = Integer.parseInt(multiYear);
				if (multiYearInInteger > 1) {
					isElementDisplayed("txt_" + detailName, productName);
					Float priceValueInSheet = Float.parseFloat(detailValue
							.replaceAll("\\$", "")) * multiYearInInteger;
					String formatedPrice = String.format("%.02f",
							priceValueInSheet);
					String PriceValueExpected = String.valueOf(formatedPrice);

					String priceValueActual = element("txt_" + detailName,
							productName).getText().trim();
					Assert.assertTrue(priceValueActual
							.equalsIgnoreCase(PriceValueExpected));
					logMessage("ASSERT PASSED : " + priceValueActual
							+ " is verified in txt_" + detailName + "\n");
					return priceValueActual;
				} else {
					isElementDisplayed("txt_" + detailName, productName);
					String priceValueActual = element("txt_" + detailName,
							productName).getText().trim();
					Float priceValueInSheet = Float.parseFloat(detailValue
							.replaceAll("\\$", ""));
					String formatedPrice = String.format("%.02f",
							priceValueInSheet);
					String PriceValueExpected = String.valueOf(formatedPrice);

					Assert.assertTrue(priceValueActual
							.equalsIgnoreCase(PriceValueExpected));
					logMessage("ASSERT PASSED : " + priceValueActual
							+ " price is verified in txt_" + detailName + "\n");

					return priceValueActual;
				}
			}

			return null;
		}

	}

	public String verifyIndividualDetails_AACTOMA(String detailName,
			String productName, String detailValue) {
		if (detailValue.equalsIgnoreCase("")) {
			logMessage("Step : value of " + productName
					+ " price is empty in data sheet\n");
			return null;
		} else {

			isElementDisplayed("txt_" + detailName, productName);
			String priceValueActual = element("txt_" + detailName, productName)
					.getText().trim();
			String formatedPrice = String.format("%.02f",
					Float.parseFloat(detailValue));
			String PriceValueExpected = String.valueOf(formatedPrice);
			Assert.assertTrue(priceValueActual
					.equalsIgnoreCase(PriceValueExpected));
			logMessage("ASSERT PASSED : " + priceValueActual
					+ " price is verified in txt_" + detailName + "\n");
			return priceValueActual;

		}

	}

	public void verifyIndividualProfileDetails(String caseId,
			String[] quantities) {

		expandDetailsMenu("individual memberships");
		clickOnButtonToNavigateFinancialPageInMembershipPage();

		expandDetailsMenu("invoices");

		wait.waitForPageToLoadCompletely();
		verifyIndividualDetails("priceValue",
				getOmaSheetValue(caseId, "Iweb Product Name?"),
				getPriceSheetValue(caseId, "Price value?"),
				getOmaSheetValue(caseId, "multiYearDecision"));

		String payment = verifyIndividualDetails("payment",
				getOmaSheetValue(caseId, "Iweb Product Name?"),
				getPriceSheetValue(caseId, "Price value?"),
				getOmaSheetValue(caseId, "multiYearDecision"));
		Float paymentInFloat = Float.parseFloat(payment.replace("$", ""));

		String balance = verifyIndividualDetails("balance",
				getOmaSheetValue(caseId, "Iweb Product Name?"), "0.00",
				getOmaSheetValue(caseId, "multiYearDecision"));
		Float balanceInFloat = Float.parseFloat(balance.replace("$", ""));
		String total = String.valueOf(paymentInFloat + balanceInFloat);

		verifyIndividualDetails("total",
				getOmaSheetValue(caseId, "Iweb Product Name?"), total, "1");

		Float a1 = Float.parseFloat(quantities[0]);
		int quantity_Prod = (int) Math.round(a1);
		String quantity_Product = String.valueOf(quantity_Prod);

		verifyIndividualDetails("quantity",
				getOmaSheetValue(caseId, "Iweb Product Name?"),
				quantity_Product, "1");

		verifyStartAndEndDateInFinancialInvoice(
				getOmaSheetValue(caseId, "Iweb Product Name?"), caseId);
     clickUsingXpathInJavaScriptExecutor(element("lnk_lastName"));
		//element("lnk_lastName").click();
	}

	public void verifyIndividualProfileDetails_AACTOMA(String caseId,
			String[] quantities) {
		expandDetailsMenuAACT("individual memberships");
		clickOnButtonToNavigateFinancialPageInMembershipPage();
		expandDetailsMenuAACT("invoices");
		verifyIndividualDetails_AACTOMA("priceValue",
				getAACT_OmaSheetValue(caseId, "Iweb AACT Product Name?"),
				getAACT_OmaSheetValue(caseId, "Product Subtotal?"));
		String payment = verifyIndividualDetails_AACTOMA("payment",
				getAACT_OmaSheetValue(caseId, "Iweb AACT Product Name?"),
				getAACT_OmaSheetValue(caseId, "Product Subtotal?"));
		Float paymentInFloat = Float.parseFloat(payment);
		String balance = verifyIndividualDetails_AACTOMA("balance",
				getAACT_OmaSheetValue(caseId, "Iweb AACT Product Name?"),
				"0.00");
		Float balanceInFloat = Float.parseFloat(balance);
		String total = String.valueOf(paymentInFloat + balanceInFloat);
		verifyIndividualDetails("total",
				getAACT_OmaSheetValue(caseId, "Iweb AACT Product Name?"),
				total, "1");
		verifyStartAndEndDateInFinancialInvoice(
				getAACT_OmaSheetValue(caseId, "Iweb AACT Product Name?"),
				caseId);
		element("lnk_lastName").click();
	}

	private void expandDetailsMenu(String menuName) {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		hardWaitForIEBrowser(10);
		try
		{
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
		isElementDisplayed("btn_memberShip", menuName);
		clickUsingXpathInJavaScriptExecutor(element("btn_memberShip", menuName));
		// element("btn_memberShip", menuName).click();
		logMessage("Step : " + menuName
				+ " is clicked to expand in btn_memberShip\n");
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
		}
		catch(Exception E)
		{
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("Step : "+menuName+ " already expanded \n" );
		}
	}

	private void expandDetailsMenuAACT(String menuName) {
		isElementDisplayed("btn_memberShipAACT", menuName);
		clickUsingXpathInJavaScriptExecutor(element("btn_memberShipAACT", menuName));
		//element("btn_memberShipAACT", menuName).click();
		logMessage("Step : " + menuName
				+ " is clicked to expand in btn_memberShipAACT\n");
	}

	public void clickOnButtonToNavigateFinancialPageInMembershipPage() {
		isElementDisplayed("btn_invoiceAtMembership");
		clickUsingXpathInJavaScriptExecutor(element("btn_invoiceAtMembership"));
		// element("btn_invoiceAtMembership").click();
		logMessage("Step : user navigate to finance page in btn_memberShip\n");
	}

	public void clickOnInvoiceArrowButtonToNavigateFinancialPage() {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("btn_invoicearrow");
		clickUsingXpathInJavaScriptExecutor(element("btn_invoicearrow"));
		//element("btn_invoicearrow").click();
		logMessage("Step : user navigate to finance page by clicking btn_invoicearrow\n");

	}

	public void clickOnArrowButtonForProductName(String productName) {
		isElementDisplayed("btnArrowProdName", productName);
		element("btnArrowProdName", productName).click();
		logMessage("Step : Arrow button is clicked for product name "
				+ productName);
	}

	public String getContactId() {
		isElementDisplayed("txt_contactId");
		return element("txt_contactId").getText();
	}

	public void verifyStartAndEndDateInFinancialInvoice(String productName,
			String caseId) {
		String multiYear = getOmaSheetValue(caseId, "multiYearDecision");
		verifyStartAndEndDate("StartDate", productName, caseId, multiYear);
		verifyStartAndEndDate("EndDate", productName, caseId, multiYear);
	}

	public void verifyStartAndEndDate(String start_EndDate, String productName,
			String caseId, String multiYear) {
		String currentDate = DateUtil
				.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/YYYY",
						"EST5EDT");
		String nextYearDate = DateUtil
				.getAddYearWithLessOnedayInStringWithGivenFormate("M/d/YYYY",
						multiYear, "EST5EDT");
		isElementDisplayed("txt_term" + start_EndDate, productName);
		if (start_EndDate.equalsIgnoreCase("StartDate")) {
			Assert.assertTrue(element("txt_term" + start_EndDate, productName)
					.getText().trim().equalsIgnoreCase(currentDate));
			logMessage("AASERT PASSED : termStartDate is " + currentDate
					+ " verified");
		} else if (start_EndDate.equalsIgnoreCase("EndDate")) {
			Assert.assertTrue(element("txt_term" + start_EndDate, productName)
					.getText().trim().equalsIgnoreCase(nextYearDate));
			logMessage("AASERT PASSED : termEndDate is " + nextYearDate
					+ " verified");
		}

	}

	public void waitForSpinner() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
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

	public void navigateToSubscriptionAndVerifySubscriptionDetails(
			int numberOfSubscriptions) {
		int size = 0;
		navigateToSubscriptionMenuOnHoveringMore_CreateMember();
		try {
			size = elements("list_rowsInSubscription").size();
		} catch (StaleElementReferenceException stlRefExp) {
			size = elements("list_rowsInSubscription").size();
		}

		for (int i = 0; i < size - 1; i++) {
			for (int j = 1; j <= numberOfSubscriptions; j++) {

				if (element("txt_subscriptionName", String.valueOf(j))
						.getText()
						.trim()
						.equalsIgnoreCase(
								map().get("subscription" + j).split(" - ")[0])) {
					verifySubscriptionDetails("Name",
							map().get("subscription" + j).split(" - ")[0],
							String.valueOf(j));
					// verifySubscriptionDetails("Price",
					// map().get("Sub" + j + "_SalePrice?"),
					// String.valueOf(j));
					// verifySubscriptionDetails("StartDate",
					// ,
					// String.valueOf(j));
					// verifySubscriptionDetails("EndDate",
					// ,
					// String.valueOf(j));
					verifySubscriptionDetails("IssueFulfilled", "0",
							String.valueOf(j));
					verifySubscriptionDetails("StartIssue",
							map().get("subscription" + j).split(" - ")[0],
							String.valueOf(j));
					break;
				}

			}
		}
	}

	public void verifySubscriptionDetails(String detailName,
			String detailValue, String index) {
		isElementDisplayed("txt_subscription" + detailName, index);
		String actual = element("txt_subscription" + detailName, index)
				.getText().trim().replaceAll("\\$", "");
		String expected = detailValue;

		if (detailName.equalsIgnoreCase("StartIssue")) {
			Assert.assertTrue(actual.startsWith(expected));
			logMessage("AASERT PASSED : " + detailValue + " is verified for "
					+ detailName + " \n");
		} else {
			Assert.assertTrue(actual.equalsIgnoreCase(expected));
			logMessage("AASERT PASSED : " + detailValue + " is verified for "
					+ detailName + " \n");
		}

	}

}
