package com.qait.keywords;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.DateUtil;

public class InvoicePageActions_IWEB extends ASCSocietyGenericPage {

	WebDriver driver;
	String pagename = "InvoicePage_IWEB";
	String invoiceTotal, LSDues;

	public InvoicePageActions_IWEB(WebDriver driver) {
		super(driver, "InvoicePage_IWEB");
		this.driver = driver;
	}

	public void verifyInvoicedDetails(String caseId, String menuName,
			String invoiceNumber, String[] quantities, String Total) {

		enterInvoiceNumber(invoiceNumber);
		clickOnSearchButton();

		verifyInvoiceProfile("invoice total", Total);
		verifyInvoiceProfile("balance", "$0.00");

		expandDetailsMenu("line items");

		verifyInvoiceDetails("priceValue",
				getOmaSheetValue(caseId, "Iweb Product Name?"),
				getPriceSheetValue(caseId, "Price value?"),
				getOmaSheetValue(caseId, "multiYearDecision"));
		verifyInvoiceDetails("balance",
				getOmaSheetValue(caseId, "Iweb Product Name?"), "0.00",
				getOmaSheetValue(caseId, "multiYearDecision"));
		verifyInvoiceDetails("discount",
				getOmaSheetValue(caseId, "Iweb Product Name?"), "0.00",
				getOmaSheetValue(caseId, "multiYearDecision"));

		verifyInvoiceDetails("priceValue",
				getOmaSheetValue(caseId, "Iweb Pub Name?"),
				getPriceSheetValue(caseId, "pubsPrice?"), "1");
		verifyInvoiceDetails("discount",
				getOmaSheetValue(caseId, "Iweb Pub Name?"), "0.00", "1");
		verifyInvoiceDetails("balance",
				getOmaSheetValue(caseId, "Iweb Pub Name?"), "0.00", "1");

		verifyInvoiceDetails("priceValue",
				getOmaSheetValue(caseId, "Iweb Division Name?"),
				getPriceSheetValue(caseId, "divisionPrice?"),
				getOmaSheetValue(caseId, "multiYearDecision"));
		verifyInvoiceDetails("balance",
				getOmaSheetValue(caseId, "Iweb Division Name?"), "0.00",
				getOmaSheetValue(caseId, "multiYearDecision"));
		verifyInvoiceDetails("discount",
				getOmaSheetValue(caseId, "Iweb Division Name?"), "0.00",
				getOmaSheetValue(caseId, "multiYearDecision"));

		verifyInvoiceDetails("priceValue",
				getOmaSheetValue(caseId, "Iweb CEN Product Name?"),
				getPriceSheetValue(caseId, "CENprice?"),
				getOmaSheetValue(caseId, "multiYearDecision"));
		verifyInvoiceDetails("discount",
				getOmaSheetValue(caseId, "Iweb CEN Product Name?"), "0.00",
				getOmaSheetValue(caseId, "multiYearDecision"));
		verifyInvoiceDetails("balance",
				getOmaSheetValue(caseId, "Iweb CEN Product Name?"), "0.00",
				getOmaSheetValue(caseId, "multiYearDecision"));

		Float a1 = Float.parseFloat(quantities[0]);
		Float a2 = Float.parseFloat(quantities[1]);
		Float a3 = Float.parseFloat(quantities[2]);
		Float a4 = Float.parseFloat(quantities[3]);
		Float a5 = Float.parseFloat(quantities[4]);

		int quantity_Prod = (int) Math.round(a1);
		int quantity_Pub = (int) Math.round(a2);
		int quantity_Division = (int) Math.round(a3);
		int quantity_LSName = (int) Math.round(a4);
		int quantity_cenPrd = (int) Math.round(a5);
		String quantity_Product = String.valueOf(quantity_Prod);
		String quantity_PublicationName = String.valueOf(quantity_Pub);
		String quantity_TechnicalDivision = String.valueOf(quantity_Division);
		String quantity_IWEBLSName = String.valueOf(quantity_LSName);
		String quantity_cenProductname = String.valueOf(quantity_cenPrd);

		verifyInvoiceDetails("priceValue",
				getOmaSheetValue(caseId, "Iweb LS Name?"),
				getPriceSheetValue(caseId, "LSDues?"),
				getOmaSheetValue(caseId, "multiYearDecision"));
		verifyInvoiceDetails("discount",
				getOmaSheetValue(caseId, "Iweb LS Name?"), "0.00",
				getOmaSheetValue(caseId, "multiYearDecision"));
		verifyInvoiceDetails("balance",
				getOmaSheetValue(caseId, "Iweb LS Name?"), "0.00",
				getOmaSheetValue(caseId, "multiYearDecision"));

		verifyInvoiceDetails("quantity",
				getOmaSheetValue(caseId, "Iweb Product Name?"),
				quantity_Product, "1");
		verifyInvoiceDetails("quantity",
				getOmaSheetValue(caseId, "Iweb Pub Name?"),
				quantity_PublicationName, "1");
		verifyInvoiceDetails("quantity",
				getOmaSheetValue(caseId, "Iweb Division Name?"),
				quantity_TechnicalDivision, "1");
		verifyInvoiceDetails("quantity",
				getPriceSheetValue(caseId, "Iweb CEN Product Name?"),
				quantity_cenProductname, "1");
		verifyInvoiceDetails("quantity",
				getOmaSheetValue(caseId, "Iweb LS Name?"), quantity_IWEBLSName,
				"1");
	}

	public void verifyInvoicedDetails_AACTOMA(String caseId, String menuName,
			String invoiceNumber) {
		String Total = getAACT_OmaSheetValue(caseId, "Product Subtotal?");
		enterInvoiceNumber(invoiceNumber);
		clickOnSearchButton();
		verifyInvoiceProfile_AACTOMA("invoice total", Total);
		verifyInvoiceProfile_AACTOMA("balance", "$0.00");
		expandDetailsMenu("line items");
		verifyNationalMembershipCode_AACTOMA("code",
				getAACT_OmaSheetValue(caseId, "Iweb AACT Product Name?"),
				getAACT_OmaSheetValue(caseId, "AACT Receipt Product Code?"));
		verifyInvoiceDetails_AACTOMA("priceValue",
				getAACT_OmaSheetValue(caseId, "Iweb AACT Product Name?"),
				getAACT_OmaSheetValue(caseId, "Product Subtotal?"));
		verifyInvoiceDetails_AACTOMA("balance",
				getAACT_OmaSheetValue(caseId, "Iweb AACT Product Name?"),
				"0.00");
		verifyInvoiceDetails_AACTOMA("discount",
				getAACT_OmaSheetValue(caseId, "Iweb AACT Product Name?"),
				"0.00");

		verifyNationalMembershipCode_AACTOMA(
				"code",
				getAACT_OmaSheetValue(caseId, "Iweb AACT Subscription Name?"),
				getAACT_OmaSheetValue(caseId, "AACT Receipt Subscription Code?"));
		verifyInvoiceDetails_AACTOMA("priceValue",
				getAACT_OmaSheetValue(caseId, "IWEB AACT Subscription Name?"),
				"0.00");
		verifyInvoiceDetails_AACTOMA("discount",
				getAACT_OmaSheetValue(caseId, "IWEB AACT Subscription Name?"),
				"0.00");
		verifyInvoiceDetails_AACTOMA("balance",
				getAACT_OmaSheetValue(caseId, "IWEB AACT Subscription Name?"),
				"0.00");

	}

	// private void selectMenuInInvoiceTab(String menuName) {
	// hover(element("tab_invoice"));
	// element("txt_invoiceMenu", menuName).click();
	// logMessage("Step : " + menuName + " is clicked in txt_invoiceMenu\n");
	// }

	private void enterInvoiceNumber(String invoiceNumber) {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("inp_invoiceCode");
		element("inp_invoiceCode").sendKeys(invoiceNumber);
		logMessage("Step : " + invoiceNumber
				+ " is entered to search in inp_invoiceCode\n");
	}

	private void clickOnSearchButton() {
		isElementDisplayed("btn_search");
		element("btn_search").click();
		logMessage("Step : search button is clicked in txt_invoiceMenu\n");
	}

	private void verifyInvoiceDetails(String detailName, String productName,
			String detailValue, String multiYear) {
		if (productName.equalsIgnoreCase("")) {

		} else {
			if (!multiYear.equalsIgnoreCase("")) {
				int multiYearInInteger = Integer.parseInt(multiYear);
				if (multiYearInInteger > 1) {
					isElementDisplayed("txt_" + detailName, productName);
					String PriceInString = detailValue.replaceAll("\\$", "");
					Float priceValueInSheet = Float.parseFloat(PriceInString)
							* multiYearInInteger;
					String formatedPrice = String.format("%.02f",
							priceValueInSheet);
					String PriceValueExpected = String.valueOf(formatedPrice);
					Assert.assertTrue(element("txt_" + detailName, productName)
							.getText().trim()
							.equalsIgnoreCase(PriceValueExpected));
					logMessage("ASSERT PASSED : "
							+ element("txt_" + detailName, productName)
									.getText().trim() + " is verified in txt_"
							+ detailName + "\n");
				} else {
					isElementDisplayed("txt_" + detailName, productName);
					String ExpectedPrice = detailValue.replaceAll("\\$", "");
					Assert.assertTrue(element("txt_" + detailName, productName)
							.getText().trim().equalsIgnoreCase(ExpectedPrice));
					logMessage("ASSERT PASSED : "
							+ element("txt_" + detailName, productName)
									.getText().trim() + " is verified in txt_"
							+ detailName + "\n");
				}
			}
		}
	}

	private void verifyInvoiceDetails_AACTOMA(String detailName,
			String productName, String detailValue) {
		if (productName.equalsIgnoreCase("")) {

		} else {
			isElementDisplayed("txt_" + detailName, productName);
			String ExpectedPrice = detailValue.replaceAll("\\$", "");
			Assert.assertTrue(element("txt_" + detailName, productName)
					.getText().trim().equalsIgnoreCase(ExpectedPrice));
			logMessage("ASSERT PASSED : "
					+ element("txt_" + detailName, productName).getText()
							.trim() + " is verified in txt_" + detailName
					+ "\n");
		}
	}

	private void verifyNationalMembershipCode_AACTOMA(String detailName,
			String productName, String detailValue) {
		if (productName.equalsIgnoreCase("")) {

		} else {
			isElementDisplayed("txt_" + detailName, productName);
			String ExpectedPrice = detailValue.replaceAll("\\$", "");
			Assert.assertTrue(element("txt_" + detailName, productName)
					.getText().trim().contains(ExpectedPrice));
			logMessage("ASSERT PASSED : "
					+ element("txt_" + detailName, productName).getText()
							.trim() + " is verified in txt_" + detailName
					+ "\n");
		}
	}

	private void verifyInvoiceProfile(String detailName, String detailValue) {
		if (detailValue.equalsIgnoreCase("")) {
			logMessage("Step : value of " + detailName
					+ "  price is empty in data sheet\n");
		} else {
			isElementDisplayed("txt_invoiceValues", detailName);
			String detailValueWithOutDollar = detailValue.replace("$", "");
			Assert.assertTrue(element("txt_invoiceValues", detailName)
					.getText().trim()
					.equalsIgnoreCase(detailValueWithOutDollar));
			logMessage("ASSERT PASSED : " + detailValueWithOutDollar
					+ " is verified in txt_" + detailName + "\n");
		}
	}

	private void verifyInvoiceProfile_AACTOMA(String detailName,
			String detailValue) {
		if (detailValue.equalsIgnoreCase("")) {
			logMessage("Step : value of " + detailName
					+ "  price is empty in data sheet\n");
		} else {
			isElementDisplayed("inp_invoiceAACT", detailName);
			String detailValueWithOutDollar = detailValue.replace("$", "");
			Assert.assertTrue(element("inp_invoiceAACT", detailName).getText()
					.trim().equalsIgnoreCase(detailValueWithOutDollar));
			logMessage("ASSERT PASSED : " + detailValueWithOutDollar
					+ " is verified in " + detailName + "\n");
		}
	}

	public void expandDetailsMenu(String menuName) {
		isElementDisplayed("btn_detailsMenuAACT", menuName);
		element("btn_detailsMenuAACT", menuName).click();
		logMessage("Step : " + menuName + " is clicked to expand");
	}

	public void verifyMemberDetailsOnInvoicePage(String proforma,
			String paidInFull, String customerId, String batchName,
			String invoiceNumber) {
		verifyMemberDetails_question("proforma", proforma);
		verifyMemberDetails_question("paid in full", paidInFull);
		verifyMemberDetails("customer id", customerId);
		verifyMemberDetails("transaction date",
				DateUtil.getCurrentdateInStringWithGivenFormate("M/d/YYYY"));
		verifyBatchAtRenewal(batchName.replaceAll("ACS: ", ""));
//		verifyMemberDetails("invoice number", invoiceNumber);
	}

	public void verifyMemberDetails_question(String detailName,
			String detailValue) {
		isElementDisplayed("txt_memberDetail_q", detailName);
		System.out.println("actual : "
				+ element("txt_memberDetail_q", detailName).getText().trim());
		System.out.println("exp: " + detailValue);
		Assert.assertTrue(element("txt_memberDetail_q", detailName).getText()
				.trim().equalsIgnoreCase(detailValue));
		logMessage("ASSERT PASSED : " + detailValue + " is verified for "
				+ detailName + " in txt_memberDetail_q\n");
	}

	public void verifyMemberDetails(String detailName, String detailValue) {
		isElementDisplayed("txt_memberDetails", detailName);
		System.out.println("actual : "
				+ element("txt_memberDetails", detailName).getText().trim());
		System.out.println("exp:" + detailValue);
		Assert.assertTrue(element("txt_memberDetails", detailName).getText()
				.trim().equalsIgnoreCase(detailValue));
		logMessage("ASSERT PASSED : " + detailValue + " is verified for "
				+ detailName + " \n");
	}

	public void verifyBatchAtRenewal(String batchName) {
		isElementDisplayed("lnk_batch", batchName);
		logMessage("AASERT PASSED : batch name " + batchName + " is verfied\n");
	}

	public void verifyInvoiceDetailsInInvoiceTable(String detailName,
			String detailValue) {
		isElementDisplayed("txt_invoiceDetailsInTable", detailValue);
		logMessage("ASSERT PASSED : " + detailValue + " is verfied for "
				+ detailName + "\n");
	}
	
	
	public void verifyInvoiceDetailsOnInvoiceProfilePage(String invoiceId,
			String productname) {
//		verifyInvoiceDetailsInInvoiceTable("invoiceId", invoiceId);
		verifyInvoiceDetailsInInvoiceTable("productname", productname);
	
		
	}

}
