package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class ConfirmationPage extends ASCSocietyGenericPage {

	WebDriver driver;
	String pagename = "ConfirmationPage";
	String city, address, country, zipCode, state;
	ArrayList<String> nonVerifiedDetail = new ArrayList<String>();
	int timeOut, hiddenFieldTimeOut;

	public ConfirmationPage(WebDriver driver) {
		super(driver, "ConfirmationPage");
		this.driver = driver;
	}

	public void clickPrintButton() {
		driver.manage().deleteAllCookies();
		click(element("btn_printReceipt"));
	}

	public String getMemberDetail(String detail) {
		isElementDisplayed("txt_memberDetail", detail);
		logMessage("STEP: " + detail + " on OMA is fetched as " + element("txt_memberDetail", detail).getText() + "\n");
		return element("txt_memberDetail", detail).getText();
	}

	public void verifyMemberTypeAndName(String detailType, String detailvalue) {
		isElementDisplayed("txt_name", detailType);
		Assert.assertTrue(element("txt_name", detailType).getText().trim().equalsIgnoreCase(detailvalue),
				"ASSERT FAILED: Expected value is " + detailvalue + " but found "
						+ element("txt_name", detailType).getText());
		logMessage("ASSERT PASSED : " + detailvalue + " is verified for " + detailType + "\n");

	}

	public String[] verifyMemberDetails(String city, String zipCode, String country, String address) {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(2);
		String memberNumber = getMemberDetail("member-number");
		String invoiceNumber = getMemberDetail("invoice-number");
		String membercategory = getMemberDetail("member-category");

		isElementDisplayed("txt_memberAddress", zipCode);
		logMessage("ASSERT PASSED : " + zipCode + " is verified in  txt_memberAddress\n");
		isElementDisplayed("txt_memberAddress", city);
		logMessage("ASSERT PASSED : " + city + " is verified in  txt_memberAddress\n");
		isElementDisplayed("txt_memberAddress", country);
		logMessage("ASSERT PASSED : " + country + " is verified in  txt_memberAddress\n");
		isElementDisplayed("txt_memberAddress", address);
		logMessage("ASSERT PASSED : " + address + " is verified in  txt_memberAddress\n");

		String[] memberDetail = { memberNumber, invoiceNumber, membercategory };
		logMessage("Step : 1. memberNumber 2. invoiceNumber 3. membercategory\n");
		for (int i = 0; i < memberDetail.length; i++) {
			logMessage("STEP : Member detail " + memberDetail[i]);
		}
		return memberDetail;
	}

	public String[] verifyMemberDetails_AACTOMA(String caseId, String firstName, String lastName) {
		city = getAACT_OmaSheetValue(caseId, "City Contact Page");
		country = getAACT_OmaSheetValue(caseId, "Country Contact Page");
		zipCode = getAACT_OmaSheetValue(caseId, "Zip code Contact Page");
		address = getAACT_OmaSheetValue(caseId, "Address Contact Page");

		String memberNumber = getMemberDetail("member-number");
		String invoiceNumber = getMemberDetail("invoice-number");
		isElementDisplayed("txt_memberAddress", city);
		logMessage("ASSERT PASSED : " + city + " is verified in  txt_memberAddress\n");
		isElementDisplayed("txt_memberAddress", zipCode);
		logMessage("ASSERT PASSED : " + zipCode + " is verified in  txt_memberAddress\n");
		isElementDisplayed("txt_memberAddress", country);
		logMessage("ASSERT PASSED : " + country + " is verified in  txt_memberAddress\n");
		isElementDisplayed("txt_memberAddress", address);
		logMessage("ASSERT PASSED : " + address + " is verified in  txt_memberAddress\n");
		verifyMemberTypeAndName("MemberType", getAACT_OmaSheetValue(caseId, "Member Type?"));
		verifyMemberTypeAndName("UsersName", firstName + " " + lastName);

		String[] memberDetail = { memberNumber, invoiceNumber };
		logMessage("STEP : 1. MemberNumber 2. InvoiceNumber \n");
		for (int i = 0; i < memberDetail.length; i++) {
			logMessage("STEP : Member detail " + memberDetail[i]);
		}
		System.out.println("member detail:" + memberDetail[0]);
		return memberDetail;
	}

	public void verifyPrintReceiptContent(String caseId, String memberNumber, String invoiceNumber, String firstName,
			String lastName) {
		if (!isBrowser("firefox")) {
			logMessage("STEP : Script could not verified PDF on chrome and IE browsers\n");
		} else {
			clickOnPrintReceiptButton();
			String getWindow = driver.getWindowHandle();
			switchWindow();
			verifyReceiptFileIsInPDF();
			wait.waitForPageToLoadCompletely();
			wait.hardWait(3);
			verifyPdfContent(getAACT_OmaSheetValue(caseId, "FirstName"));
			verifyPdfContent(firstName.replace(getAACT_OmaSheetValue(caseId, "FirstName"), ""));
			verifyPdfContent(getAACT_OmaSheetValue(caseId, "LastName"));
			verifyPdfContent(lastName.replace(getAACT_OmaSheetValue(caseId, "LastName"), ""));

			verifyPdfContent(getAACT_OmaSheetValue(caseId, "City Contact Page"));

			verifyPdfContent(getAACT_OmaSheetValue(caseId, "Zip code Contact Page"));
			int lengthOfProductName = getAACT_OmaSheetValue(caseId, "AACT National Membership 1?").length();
			verifyPdfContent(getAACT_OmaSheetValue(caseId, "AACT National Membership 1?").substring(0, 19));
			verifyPdfContent(
					getAACT_OmaSheetValue(caseId, "AACT National Membership 1?").substring(19, lengthOfProductName));
			verifyPdfContent(getAACT_OmaSheetValue(caseId, "AACT National Membership 2?"));

			verifyPdfContent(getAACT_OmaSheetValue(caseId, "AACT Receipt Product Code?"));
			verifyPdfContent(getAACT_OmaSheetValue(caseId, "AACT Receipt Subscription Code?"));
			verifyPdfContent(getAACT_OmaSheetValue(caseId, "Product Subtotal?").replace("$", ""));
			verifyPdfContent(memberNumber);

			verifyPdfContent(invoiceNumber);
			closePdfFile();
			driver.switchTo().window(getWindow);
			if (nonVerifiedDetail.size() > 0) {
				logMessage("Member detail in receipt pdf file not verified are given below:-\n");
				for (String nonVerifiedDetailInReceipt : nonVerifiedDetail) {
					logMessage(nonVerifiedDetailInReceipt + "\n");
				}
				Assert.fail("Member details are not verified in receipt pdf file\n");
			}
		}

	}

	private void closePdfFile() {
		driver.close();
	}

	public void clickOnPrintReceiptButton() {
		isElementDisplayed("btn_topPrintButton");
		element("btn_topPrintButton").click();
		logMessage("STEP : Print receipt button is clicked \n");
	}

	public void verifyPdfContent(String detailValue) {
		if (detailValue.equalsIgnoreCase("") || detailValue.equalsIgnoreCase("null")) {
			logMessage("Please check the data in the AACT OMA data sheet\n");
		} else {
			timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
			hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
			try {
				wait.resetImplicitTimeout(0);
				wait.resetExplicitTimeout(hiddenFieldTimeOut);
				isElementDisplayed("pdf_content", detailValue);
				logMessage("ASSERT PASSED : " + detailValue + " is verified in receipt detail\n");
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
			} catch (Exception exp) {
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
				nonVerifiedDetail.add(detailValue);
			}

		}

	}

	public void verifyReceiptFileIsInPDF() {
		verifyPageTitleContains("ReportPDF");
	}

}
