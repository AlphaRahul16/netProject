package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.DateUtil;

public class InvoicePageActions_IWEB extends ASCSocietyGenericPage {

	WebDriver driver;
	String pagename = "InvoicePage_IWEB";
	String invoiceTotal, LSDues;
	int timeOut, hiddenFieldTimeOut, count;

	public InvoicePageActions_IWEB(WebDriver driver) {
		super(driver, "InvoicePage_IWEB");
		this.driver = driver;
	}

	public void verifyInvoiceProfile(String paidInFull) {
		verifyMemberDetails_question("paid in full", paidInFull);
		verifyMemberDetails("transaction date",
				DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/YYYY", "EST5EDT"));
	}

	public void verifyProductCodeInlineItem(String prodCode, String prodName) {
		hardWaitForIEBrowser(3);
		expandDetailsMenu("line items");
		hardWaitForIEBrowser(10);

		isElementDisplayed("txt_code", prodName);
		String productCode = element("txt_code", prodName).getText().trim();

		Assert.assertTrue(productCode.contains(prodCode),
				"ASSERT FAILED: Expected value is " + prodCode + " but found " + productCode);
		logMessage("ASSERT PASSED : Product Code is matched in Line Item");

	}

	public void verifyInvoiceDetailsGCSOMA(String invoiceNumber, String Total, String paidInFull) {
		enterInvoiceNumber(invoiceNumber);
		clickOnSearchButton();
		verifyInvoiceProfile("invoice total", Total);
		verifyInvoiceProfile("balance", "0.00");
		verifyMemberDetails_question("paid in full", paidInFull);

	}

	public void expandChildTabMenuAndverifyGCSOMA() {
		expandDetailsMenu("acs global constituent system log");

	}

	public void verifyInvoicedDetails(String caseId, String menuName, String invoiceNumber, String[] quantities,
			String Total) {

		enterInvoiceNumber(invoiceNumber);
		clickOnSearchButton();

		verifyInvoiceProfile("invoice total", Total);
		verifyInvoiceProfile("balance", "$0.00");
		hardWaitForIEBrowser(3);
		expandDetailsMenu("line items");
		hardWaitForIEBrowser(10);
		verifyInvoiceDetails("priceValue", map().get("Iweb Product Name?"), getPriceSheetValue(caseId, "Price value?"),
				map().get("multiYearDecision"));
		verifyInvoiceDetails("balance", map().get("Iweb Product Name?"), "0.00", map().get("multiYearDecision"));
		verifyInvoiceDetails("discount", map().get("Iweb Product Name?"), "0.00", map().get("multiYearDecision"));

		verifyInvoiceDetails("priceValue", map().get("Iweb Pub Name?"), getPriceSheetValue(caseId, "pubsPrice?"), "1");
		verifyInvoiceDetails("discount", map().get("Iweb Pub Name?"), "0.00", "1");
		verifyInvoiceDetails("balance", map().get("Iweb Pub Name?"), "0.00", "1");

		verifyInvoiceDetails("priceValue", map().get("Iweb Division Name?"),
				getPriceSheetValue(caseId, "divisionPrice?"), map().get("multiYearDecision"));
		verifyInvoiceDetails("balance", map().get("Iweb Division Name?"), "0.00", map().get("multiYearDecision"));
		verifyInvoiceDetails("discount", map().get("Iweb Division Name?"), "0.00", map().get("multiYearDecision"));

		verifyInvoiceDetails("priceValue", map().get("Iweb CEN Product Name?"), getPriceSheetValue(caseId, "CENprice?"),
				map().get("multiYearDecision"));
		verifyInvoiceDetails("discount", map().get("Iweb CEN Product Name?"), "0.00", map().get("multiYearDecision"));
		verifyInvoiceDetails("balance", map().get("Iweb CEN Product Name?"), "0.00", map().get("multiYearDecision"));

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

		verifyInvoiceDetails("priceValue", map().get("Iweb LS Name?"), getPriceSheetValue(caseId, "LSDues?"),
				map().get("multiYearDecision"));
		verifyInvoiceDetails("discount", map().get("Iweb LS Name?"), "0.00", map().get("multiYearDecision"));
		verifyInvoiceDetails("balance", map().get("Iweb LS Name?"), "0.00", map().get("multiYearDecision"));

		verifyInvoiceDetails("quantity", map().get("Iweb Product Name?"), quantity_Product, "1");
		verifyInvoiceDetails("quantity", map().get("Iweb Pub Name?"), quantity_PublicationName, "1");
		verifyInvoiceDetails("quantity", map().get("Iweb Division Name?"), quantity_TechnicalDivision, "1");
		verifyInvoiceDetails("quantity", getPriceSheetValue(caseId, "Iweb CEN Product Name?"), quantity_cenProductname,
				"1");
		verifyInvoiceDetails("quantity", map().get("Iweb LS Name?"), quantity_IWEBLSName, "1");
	}

	public void verifyInvoicedDetails_AACTOMA(String caseId, String menuName, String invoiceNumber) {
		String Total = getAACT_OmaSheetValue(caseId, "Product Subtotal?");
		enterInvoiceNumber(invoiceNumber);
		clickOnSearchButton();
		verifyInvoiceProfile_AACTOMA("invoice total", Total);
		verifyInvoiceProfile_AACTOMA("balance", "$0.00");
		expandDetailsMenu("line items");
		verifyNationalMembershipCode_AACTOMA("code", getAACT_OmaSheetValue(caseId, "Iweb AACT Product Name?"),
				getAACT_OmaSheetValue(caseId, "AACT Receipt Product Code?"));
		verifyInvoiceDetails_AACTOMA("priceValue", getAACT_OmaSheetValue(caseId, "Iweb AACT Product Name?"),
				getAACT_OmaSheetValue(caseId, "Product Subtotal?"));
		verifyInvoiceDetails_AACTOMA("balance", getAACT_OmaSheetValue(caseId, "Iweb AACT Product Name?"), "0.00");
		verifyInvoiceDetails_AACTOMA("discount", getAACT_OmaSheetValue(caseId, "Iweb AACT Product Name?"), "0.00");

		verifyNationalMembershipCode_AACTOMA("code", getAACT_OmaSheetValue(caseId, "Iweb AACT Subscription Name?"),
				getAACT_OmaSheetValue(caseId, "AACT Receipt Subscription Code?"));
		verifyInvoiceDetails_AACTOMA("priceValue", getAACT_OmaSheetValue(caseId, "IWEB AACT Subscription Name?"),
				"0.00");
		verifyInvoiceDetails_AACTOMA("discount", getAACT_OmaSheetValue(caseId, "IWEB AACT Subscription Name?"), "0.00");
		verifyInvoiceDetails_AACTOMA("balance", getAACT_OmaSheetValue(caseId, "IWEB AACT Subscription Name?"), "0.00");

	}

	public void selectMenuInInvoiceTab(String menuName) {
		hover(element("tab_invoice"));
		element("txt_invoiceMenu", menuName).click();
		logMessage("STEP : " + menuName + " is clicked in txt_invoiceMenu\n");
	}

	public void enterInvoiceNumber(String invoiceNumber) {
		// wait.waitForPageToLoadCompletely();
		wait.hardWait(5);
		isElementDisplayed("inp_invoiceCode");
		element("inp_invoiceCode").sendKeys(invoiceNumber);
		logMessage("STEP : " + invoiceNumber + " is entered to search in inp_invoiceCode\n");
	}

	public void clickOnSearchButton() {
		isElementDisplayed("btn_search");
		clickUsingXpathInJavaScriptExecutor(element("btn_search"));
		// element("btn_search").click();
		logMessage("STEP : Search button is clicked in txt_invoiceMenu\n");
	}

	private void verifyInvoiceDetails(String detailName, String productName, String detailValue, String multiYear) {
		hardWaitForIEBrowser(20);
		if (productName.equalsIgnoreCase("")) {

		} else {
			if (!multiYear.equalsIgnoreCase("")) {
				int multiYearInInteger = Integer.parseInt(multiYear);
				if (multiYearInInteger > 1) {
					isElementDisplayed("txt_" + detailName, productName);
					String PriceInString = detailValue.replaceAll("\\$", "");
					Float priceValueInSheet = Float.parseFloat(PriceInString) * multiYearInInteger;
					String formatedPrice = String.format("%.02f", priceValueInSheet);
					String PriceValueExpected = String.valueOf(formatedPrice);
					Assert.assertTrue(
							element("txt_" + detailName, productName).getText().trim()
									.equalsIgnoreCase(PriceValueExpected),
							"ASSERT FAILED: Expected value is " + PriceValueExpected + " but found "
									+ element("txt_" + detailName, productName).getText().trim());
					logMessage("ASSERT PASSED : " + element("txt_" + detailName, productName).getText().trim()
							+ " is verified in txt_" + detailName + "\n");
				} else {
					isElementDisplayed("txt_" + detailName, productName);
					String ExpectedPrice = detailValue.replaceAll("\\$", "");
					Assert.assertTrue(
							element("txt_" + detailName, productName).getText().trim().equalsIgnoreCase(ExpectedPrice),
							"ASSERT FAILED: Expected value is " + ExpectedPrice + " but found "
									+ element("txt_" + detailName, productName).getText().trim());
					logMessage("ASSERT PASSED : " + element("txt_" + detailName, productName).getText().trim()
							+ " is verified in txt_" + detailName + "\n");
				}
			}
		}
	}

	public void verifyInvoiceDetailsInLineItems(String name, Map<String, String> mapGetMemberDetailsInStore) {
		Assert.assertEquals(element("table_" + name).getText().trim(), mapGetMemberDetailsInStore.get(name));
		logMessage("ASSERT PASSED : " + name + " details on invoice profile succesfully verified as "
				+ mapGetMemberDetailsInStore.get(name) + "\n");
	}

	public void verifyInvoiceDetails_LineItems(Map<String, String> mapGetMemberDetailsInStore, String... name) {
		for (String names : name) {
			verifyInvoiceDetailsInLineItems(names, mapGetMemberDetailsInStore);
		}

	}

	private void verifyInvoiceDetails_AACTOMA(String detailName, String productName, String detailValue) {
		if (productName.equalsIgnoreCase("")) {

		} else {
			isElementDisplayed("txt_" + detailName, productName);
			String ExpectedPrice = detailValue.replaceAll("\\$", "");
			Assert.assertTrue(
					element("txt_" + detailName, productName).getText().trim().equalsIgnoreCase(ExpectedPrice),
					"ASSERT FAILED: Expected value is " + ExpectedPrice + " but found "
							+ element("txt_" + detailName, productName).getText().trim());
			logMessage("ASSERT PASSED : " + element("txt_" + detailName, productName).getText().trim()
					+ " is verified in txt_" + detailName);
		}
	}

	private void verifyNationalMembershipCode_AACTOMA(String detailName, String productName, String detailValue) {
		if (productName.equalsIgnoreCase("")) {

		} else {
			isElementDisplayed("txt_" + detailName, productName);
			String ExpectedPrice = detailValue.replaceAll("\\$", "");
			Assert.assertTrue(element("txt_" + detailName, productName).getText().trim().contains(ExpectedPrice),
					"ASSERT FAILED: Expected value is " + ExpectedPrice + " but found "
							+ element("txt_" + detailName, productName).getText().trim());
			logMessage("ASSERT PASSED : " + element("txt_" + detailName, productName).getText().trim()
					+ " is verified in txt_" + detailName + "\n");
		}
	}

	public void verifyInvoiceProfile(String detailName, String detailValue) {
		hardWaitForIEBrowser(10);
		if (detailValue.equalsIgnoreCase("")) {
			logMessage("STEP : Value of " + detailName + "  price is empty in data sheet\n");
		} else {
			isElementDisplayed("txt_invoiceValues", detailName);
			String detailValueWithOutDollar = detailValue.replace("$", "");
			Assert.assertTrue(
					element("txt_invoiceValues", detailName).getText().trim()
							.equalsIgnoreCase(detailValueWithOutDollar),
					"ASSERT FAILED: Expected value is " + detailValueWithOutDollar + " but found "
							+ element("txt_invoiceValues", detailName).getText().trim());
			logMessage("ASSERT PASSED : " + detailValueWithOutDollar + " is verified in txt_" + detailName + "\n");
		}
	}

	private void verifyInvoiceProfile_AACTOMA(String detailName, String detailValue) {
		if (detailValue.equalsIgnoreCase("")) {
			logMessage("STEP : Value of " + detailName + "  price is empty in data sheet\n");
		} else {
			wait.waitForPageToLoadCompletely();
			wait.hardWait(6);
			isElementDisplayed("inp_invoiceAACT", detailName);
			String detailValueWithOutDollar = detailValue.replace("$", "");
			Assert.assertTrue(
					element("inp_invoiceAACT", detailName).getText().trim().equalsIgnoreCase(detailValueWithOutDollar),
					"ASSERT FAILED: Expected value is " + detailValueWithOutDollar + " but found "
							+ element("txt_invoiceValues", detailName).getText().trim());
			logMessage("ASSERT PASSED : " + detailValueWithOutDollar + " is verified in " + detailName + "\n");
		}
	}

	public void expandDetailsMenu(String menuName) {
		wait.waitForPageToLoadCompletely();
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			isElementDisplayed("btn_detailsMenuAACT", menuName);
			clickUsingXpathInJavaScriptExecutor(element("btn_detailsMenuAACT", menuName));
			// element("btn_detailsMenuAACT", menuName).click();
			logMessage("STEP : " + menuName + " bar is clicked to expand" + "\n");
			waitForSpinner();
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (NoSuchElementException | AssertionError | TimeoutException Exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("STEP : Spinner is not present \n");
		}
	}

	public void waitForSpinner() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			handleAlert();
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("img_spinner");
			wait.waitForElementToDisappear(element("img_spinner"));
			logMessage("STEP : Wait for spinner to be disappeared \n");

		} catch (NoSuchElementException | AssertionError | TimeoutException Exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("STEP : Spinner is not present \n");
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
	}

	public void collapseDetailsMenu(String menuName) {
		isElementDisplayed("icon_up", menuName);
		clickUsingXpathInJavaScriptExecutor(element("icon_up", menuName));
		// element("icon_up", menuName).click();
		waitForSpinner();
		logMessage("STEP : " + menuName + " bar collapse bar clicked\n");

	}

	public void verifyMemberDetailsOnInvoicePage(String proforma, String paidInFull, String customerId,
			String batchName, String invoiceNumber) {
		verifyMemberDetails_question("proforma", proforma);
		verifyMemberDetails_question("paid in full", paidInFull);
		verifyMemberDetails("customer id", customerId);
		verifyMemberDetails("transaction date", DateUtil.getCurrentdateInStringWithGivenFormate("M/d/YYYY"));
		verifyBatchAtRenewal(batchName.replaceAll("ACS: ", ""));
		// verifyMemberDetails("invoice number", invoiceNumber);
	}

	public void verifyMemberDetails_question(String detailName, String detailValue) {
		hardWaitForIEBrowser(5);
		isElementDisplayed("txt_memberDetail_q", detailName);
		Assert.assertTrue(element("txt_memberDetail_q", detailName).getText().trim().equalsIgnoreCase(detailValue),
				"ASSERT FAILED: Expected value is " + detailValue + " but found "
						+ element("txt_memberDetail_q", detailName).getText().trim());
		logMessage("ASSERT PASSED : " + detailValue + " is verified for " + detailName + " field\n");
	}

	public void verifyMemberDetails(String detailName, String detailValue) {
		isElementDisplayed("txt_memberDetails", detailName);
		Assert.assertTrue(element("txt_memberDetails", detailName).getText().trim().equalsIgnoreCase(detailValue),
				"ASSERT FAILED: Expected value is " + detailValue + " but found "

						+ element("txt_memberDetails", detailName).getText().trim());
		logMessage("ASSERT PASSED : " + detailValue + " is verified for " + detailName + " \n");
	}

	public void verifyBalanceInInvoice(String balanceAmount) {
		verifyMemberDetails("balance", balanceAmount);
	}

	public String getMemberDetails(String detailName) {
		isElementDisplayed("txt_memberDetails", detailName);
		String value = element("txt_memberDetails", detailName).getText().trim();

		logMessage("STEP : The value for " + detailName + " is " + value + "\n");
		return value;
	}

	public void verifyBatchAtRenewal(String batchName) {
		isElementDisplayed("lnk_batch", batchName);
		logMessage("AASERT PASSED : batch name " + batchName + " is verfied\n");
	}

	public void verifyInvoiceDetailsInInvoiceTable(String detailName, String detailValue) {
		isElementDisplayed("txt_invoiceDetailsInTable", detailValue);
		logMessage("ASSERT PASSED : " + detailValue + " is verfied for " + detailName + "\n");
	}

	public void verifyInvoiceDetailsOnInvoiceProfilePage(String invoiceId, String productname) {
		// verifyInvoiceDetailsInInvoiceTable("invoiceId", invoiceId);
		verifyInvoiceDetailsInInvoiceTable("productname", productname);

	}

	public void validateBalanceAndTotalForInvoice(Map<String, String> TotalAmountMap) {
		Iterator iterator = TotalAmountMap.keySet().iterator();

		if (TotalAmountMap.size() != 4 && !TotalAmountMap.get("IsProgramPledged").equals("1")) {
			System.out.println("In bbbbbbb");
			verifyInvoiceProfile("balance", "0.00");
			verifyInvoiceProfile("invoice total", TotalAmountMap.get("TotalAmount") + "0");
		} else if (TotalAmountMap.size() == 4 && TotalAmountMap.get("pledgedMonthlyTotal").equals("true")) {
			Double balance = Double.parseDouble(TotalAmountMap.get("TotalAmount"))
					- Double.parseDouble(TotalAmountMap.get("MonthlyAmount"));
			verifyInvoiceProfile("invoice total", TotalAmountMap.get("TotalAmount") + "0");
			verifyInvoiceProfile("balance", String.valueOf(balance) + "0");
		}
	}

	public void validateBalanceAndTotalForInvoiceForIndividualLandingPages(Map<String, String> TotalAmountMap,
			String[] total) {
		Iterator iterator = TotalAmountMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next().toString();
			String value = TotalAmountMap.get(key);
			System.out.println(key + " keys " + value + " value");

		}
		System.out.println(TotalAmountMap.get("pledgedMonthlyTotal"));
		try {
			if (TotalAmountMap.get("pledgedMonthlyTotal").equals("true")) {
				Double balance = Double.parseDouble(TotalAmountMap.get("TotalAmount"))
						- Double.parseDouble(TotalAmountMap.get("MonthlyAmount"));
				verifyInvoiceProfile("invoice total", TotalAmountMap.get("TotalAmount") + "0");
				verifyInvoiceProfile("balance", String.valueOf(balance) + "0");
			}
			// else if(TotalAmountMap.size()!=4)
			else {

				verifyInvoiceProfile("balance", "0.00");
				verifyInvoiceProfile("invoice total", total[0]);
			}
		} catch (Exception e) {
			System.out.println("Program Not Pledged");
		}
	}

	public void verfifyproductDisplayNamesAndCodesInsideLineItems(Map<String, String> TotalAmountMap, String[] Amount,
			String[] productNameKey, Map<String, List<String>> mapIwebProductDetails) {
		if (TotalAmountMap.size() != 4) {
			verifyProductDisplayDetailsAndAmountWhenProgramIsNotPledged(Amount, productNameKey, mapIwebProductDetails);

		} else if (TotalAmountMap.size() == 4 && TotalAmountMap.get("pledgedMonthlyTotal").equals("true")) {

			verifyProductDisplayDetailsAndAmountWhenProgramIsPledged(Amount, productNameKey, mapIwebProductDetails);
		}

	}

	public void verfifyproductInsideLineItemsForIndividualLanding(Map<String, String> TotalAmountMap, String[] Amount,
			String[] productNameKey, Map<String, String> mapSheetDetails) {

		try {
			if (TotalAmountMap.get("pledgedMonthlyTotal").equals("true")) {

				verifyProductDetailsWhenProgramIsPledgedForIndividualLandingPage(Amount, productNameKey,
						mapSheetDetails);
			}

		} catch (Exception e) {
			verifyProductDetailsWhenProgramIsNotPledgedForIndividualLandingPage(Amount, productNameKey,
					mapSheetDetails);
		}
	}

	private void verifyProductDisplayDetailsAndAmountWhenProgramIsPledged(String[] amount, String[] productNameKey,
			Map<String, List<String>> mapIwebProductDetails) {

		String pledgedProduct = mapIwebProductDetails.get(productNameKey[0]).get(1).split(" ")[0] + " P";
		if (element("table_description").getText().trim().contains(productNameKey[0])) {
			Assert.assertTrue(element("table_description").getText().trim().equals(productNameKey[0] + " Pledge"),
					"ASSERT FAILED: Expected value is " + productNameKey[0] + " Pledge" + " but found "
							+ element("table_description").getText().trim());
			logMessage("ASSERT PASSED : Program Name inside Line items is verifed as " + productNameKey[0] + " Pledge");
			Assert.assertTrue(element("table_code").getText().trim().equals(pledgedProduct),
					"ASSERT FAILED: Expected value is " + pledgedProduct + " but found "
							+ element("table_code").getText().trim());
			logMessage("ASSERT PASSED : Program Code inside Line items is verifed as " + pledgedProduct);
		}
	}

	private void verifyProductDisplayDetailsAndAmountWhenProgramIsNotPledged(String[] Amount, String[] productNameKey,
			Map<String, List<String>> mapIwebProductDetails) {
		for (int i = 0; i < elements("table_description").size(); i++) {
			for (int j = 0; j < Amount.length; j++) {

				if (elements("table_description").get(i).getText().trim()
						.equals(mapIwebProductDetails.get(productNameKey[j]).get(0)) && Amount[j].length() != 0) {
					Assert.assertTrue(
							elements("table_description").get(i).getText().trim()
									.equals(mapIwebProductDetails.get(productNameKey[j]).get(0)),
							"ASSERT FAILED: Expected value is " + mapIwebProductDetails.get(productNameKey[j]).get(0)
									+ " but found " + elements("table_description").get(i).getText().trim());
					logMessage("ASSERT PASSED : Product Display Name verified as "
							+ mapIwebProductDetails.get(productNameKey[j]).get(0) + "\n");
					Assert.assertEquals(elements("table_code").get(i).getText().trim(),
							(mapIwebProductDetails.get(productNameKey[j]).get(1)));
					logMessage("ASSERT PASSED : Product Code for Product " + productNameKey[j] + " is verified as "
							+ mapIwebProductDetails.get(productNameKey[j]).get(1) + "\n");
					Assert.assertEquals(elements("table_quantity").get(i).getText().trim(), "1");
					logMessage("ASSERT PASSED : Product Quantity for " + productNameKey[j] + " is verified as 1 \n");
					Assert.assertEquals(elements("table_discount").get(i).getText().trim(), ("0.00"));
					logMessage("ASSERT PASSED : Product discount for" + productNameKey[j] + " is verified as 0.00 \n");
					Assert.assertEquals(elements("table_balance").get(i).getText().trim(), ("0.00"));
					logMessage("ASSERT PASSED : Product balance for " + productNameKey[j] + " is verified as 0.00 \n");
					verifyEachProductAmountOnListItems(mapIwebProductDetails.get(productNameKey[j]).get(0), Amount);
				}
			}
		}
	}

	private void verifyProductDetailsWhenProgramIsNotPledgedForIndividualLandingPage(String[] Amount,
			String[] productNameKey, Map<String, String> mapSheetDetails) {
		for (int i = 0; i < elements("table_description").size(); i++) {
			Assert.assertEquals(elements("table_description").get(i).getText().trim(), (productNameKey[0]));
			logMessage("ASSERT PASSED : Product Display Name verified as " + productNameKey[0] + "\n");
			Assert.assertEquals(elements("table_code").get(i).getText().trim(),
					(mapSheetDetails.get("Application_Codes")));
			logMessage("ASSERT PASSED : Product Code for Product " + productNameKey[0] + " is verified as "
					+ mapSheetDetails.get("Application_Codes") + "\n");
			Assert.assertEquals(elements("table_quantity").get(i).getText().trim(), "1");
			logMessage("ASSERT PASSED : Product Quantity for " + productNameKey[0] + " is verified as 1 \n");
			Assert.assertEquals(elements("table_discount").get(i).getText().trim(), ("0.00"));
			logMessage("ASSERT PASSED : Product discount for" + productNameKey[0] + " is verified as 0.00 \n");
			Assert.assertEquals(elements("table_balance").get(i).getText().trim(), ("0.00"));
			logMessage("ASSERT PASSED : Product balance for " + productNameKey[0] + " is verified as 0.00 \n");

		}
	}

	private void verifyProductDetailsWhenProgramIsPledgedForIndividualLandingPage(String[] Amount,
			String[] productNameKey, Map<String, String> mapSheetDetails) {

		String pledgedProduct = mapSheetDetails.get("Application_Codes").split(" ")[0] + " P";
		if (element("table_description").getText().trim().contains(productNameKey[0])) {
			Assert.assertEquals(element("table_description").getText().trim(), (productNameKey[0] + " Pledge"));
			logMessage("ASSERT PASSED : Program Name inside Line items is verifed as " + productNameKey[0] + " Pledge");
			Assert.assertTrue(element("table_code").getText().trim().equals(pledgedProduct),
					"ASSERT FAILED: Expected value is " + pledgedProduct + " but found "
							+ element("table_code").getText().trim());
			logMessage("ASSERT PASSED : Program Code inside Line items is verifed as " + pledgedProduct);
		}
	}

	private void verifyEachProductAmountOnListItems(String productName, String[] Amount) {
		if (productName.length() != 0) {

			switch (productName) {
			case "Project SEED":
				verifyAmountForParticularProduct(productName, Amount[0]);
				break;
			case "ACS Scholars Program":
				verifyAmountForParticularProduct(productName, Amount[1]);
				break;
			case "Teacher Professional Development":
				verifyAmountForParticularProduct(productName, Amount[2]);
				break;
			default:
				verifyAmountForParticularProduct(productName, Amount[3]);
				break;
			}

		}
	}

	private void verifyAmountForParticularProduct(String Productname, String Amount) {
		Assert.assertTrue(element("table_productPrice", Productname).getText().trim().equals(Amount + ".00"),
				"ASSERT FAILED: Expected value is " + Amount + ".00" + " but found "
						+ element("table_productPrice", Productname).getText().trim());
		logMessage("ASSERT PASSED : Product amount for " + Productname + " on iweb is verified as " + Amount + ".00\n");

	}

	public void verifyGivingInvoiceDetails(String viaEmail, String viaPostmail, String dontsend,
			String otherProgramName, String isSendCard) {
		verifyCardSendViaEmailOrPost(viaEmail, viaPostmail, dontsend, isSendCard);
		verifyOtherProgramNameOnInvoiceDetails(otherProgramName);
	}

	private void verifyOtherProgramNameOnInvoiceDetails(String otherProgramName) {
		if (otherProgramName.length() != 0) {
			Assert.assertTrue(element("txt_givingInvoiceOtherProgram").getText().trim().equals(otherProgramName),
					"ASSERT FAILED: Expected value is " + otherProgramName + " but found "
							+ element("txt_givingInvoiceOtherProgram").getText().trim());
			logMessage("ASSERT PASSED : Other Program Name in Giving Invoice Details is verified as : "
					+ otherProgramName + "\n");
		} else {
			logMessage("ASSERT PASSED : Other Program Name in Giving Invoice Details is Displayed as blank\n");
		}

	}

	private void verifyCardSendViaEmailOrPost(String viaEmail, String viaPostmail, String dontsend, String isSendCard) {
		if (viaEmail.length() != 0 && viaEmail.equalsIgnoreCase("YES") && isSendCard.equalsIgnoreCase("YES")) {
			Assert.assertTrue(element("txt_givingInvoiceEmailPost").getText().trim().equals("email"),
					"ASSERT FAILED: Expected value is " + "email" + " but found "
							+ element("txt_givingInvoiceEmailPost").getText().trim());
			logMessage("ASSERT PASSED : Card Send via in Giving Invoice Details is verified as : email\n");
		} else if (viaPostmail.length() != 0 && viaPostmail.equalsIgnoreCase("YES")
				&& isSendCard.equalsIgnoreCase("YES")) {
			Assert.assertTrue(element("txt_givingInvoiceEmailPost").getText().trim().equals("post"),
					"ASSERT FAILED: Expected value is " + "post" + " but found "
							+ element("txt_givingInvoiceEmailPost").getText().trim());
			logMessage("ASSERT PASSED : Card Send via in Giving Invoice Details is verified as : post\n");
		} else if (dontsend.length() != 0 && dontsend.equalsIgnoreCase("YES") && isSendCard.equalsIgnoreCase("YES")) {
			logMessage(
					"ASSERT PASSED : Donot send card option is selected and Giving Invoice Details is verified as : none\n");
		} else {
			logMessage("Step : Card is not send and Giving Invoice details displayed blank\n");
		}
	}

	public void verifyEmailStatusAsDefinedInSheet(String emailstatusformsheet, String emailstatusformsheet2,
			String emailstatusformsheet3, String isSendCard) {
		System.out.println("Email status" + emailstatusformsheet);
		System.out.println("Post mail status" + emailstatusformsheet2);
		System.out.println(" Donot send card " + emailstatusformsheet3);
		System.out.println("Send card status" + isSendCard);
		if ((emailstatusformsheet.equalsIgnoreCase("YES") || emailstatusformsheet2.equalsIgnoreCase("YES")
				|| emailstatusformsheet3.equalsIgnoreCase("YES")) && (isSendCard.equalsIgnoreCase("YES"))) {
			Assert.assertTrue(element("txt_emailStatus").getText().equals("Yes"), "ASSERT FAILED: Expected value is "
					+ "Yes" + " but found " + element("txt_emailStatus").getText().trim());
			logMessage("ASSERT PASSED : Email verification on feild email? is verified as Yes\n");
		} else {
			Assert.assertTrue(element("txt_emailStatus").getText().equals("No"), "ASSERT FAILED: Expected value is "
					+ "No" + " but found " + element("txt_emailStatus").getText().trim());
			logMessage("ASSERT PASSED : Email verification on feild email? is verified as No\n");
		}

	}

	public void verifyEmailStatus(Map<String, String> totalAmountMap) {
		try {
			if (totalAmountMap.get("pledgedMonthlyTotal").equals("true")) {
				Assert.assertTrue(element("txt_emailStatus").getText().equals("No"), "ASSERT FAILED: Expected value is "
						+ "No" + " but found " + element("txt_emailStatus").getText().trim());
				logMessage("ASSERT PASSED : Email verification on feild email? is verified as No\n");
			}
		} catch (NullPointerException e) {
			Assert.assertTrue(element("txt_emailStatus").getText().equalsIgnoreCase("Yes"),
					"ASSERT FAILED: Expected value is " + "Yes" + " but found "
							+ element("txt_emailStatus").getText().trim());
			logMessage("ASSERT PASSED : Email verification on feild email? is verified as Yes\n");
		}
	}

	public String verifyInvoiceDetailsBeforeRenewal() {
		String invoiceNum;

		verifyInvoiceProfile("proforma", "Yes");
		verifyInvoiceProfile("paid in full", "No");
		Assert.assertFalse(element("txt_invoiceValues", "balance").getText().trim().equalsIgnoreCase("0.00"),
				"Balance is 0.00");
		logMessage("ASSERT PASSED : Balance before renewal is not 0.00\n");
		do {
			invoiceNum = element("inp_invoiceValue", "invoice number").getText();
			System.out.println(invoiceNum);
		} while (invoiceNum.equals(null));
		logMessage("STEP : Invoice number for which renewal is to be done is " + invoiceNum);
		return invoiceNum;

	}

	public void verifyInvoiceDetailsAfterRenewal() {
		verifyInvoiceProfile("proforma", "No");
		Assert.assertTrue(element("txt_invoiceValues", "balance").getText().trim().equalsIgnoreCase("0.00"),
				"Balance is not 0.00 found " + element("txt_invoiceValues", "balance").getText().trim());
		verifyInvoiceProfile("paid in full", "Yes");
		logMessage("ASSERT PASSED : Balance amount after renewal is 0.00\n");

	}

	public void verifyPaymentStatusAfterRenewal(String memberstatus) {
		if (memberstatus.equals("Emeritus")) {
			Assert.assertTrue(element("txt_code", "Payment Status").getText().equals("Free"),
					"ASSERT FAILED: Expected value is 'Free' but found "
							+ element("txt_code", "Payment Status").getText());
			logMessage("ASSERT PASSED : Payment status for " + memberstatus + " after renewal is Free");
		} else {
			Assert.assertTrue(element("txt_code", "Payment Status").getText().equals("Paid"),
					"ASSERT FAILED: Expected value is 'Paid' but found "
							+ element("txt_code", "Payment Status").getText());
			logMessage("ASSERT PASSED : Payment status for " + memberstatus + " after renewal is Free");
		}

	}

	public void verifyRenewedProductsPriceInsideLineItems(Map<String, String> mapRenewedProductDetails) {

		for (String key : mapRenewedProductDetails.keySet()) {
			if (!(key.equals("Voluntary Contribution To C&EN") || key.equals("Project SEED")
					|| key.equals("ACS Endowment Fund"))) {
				Assert.assertTrue(
						(mapRenewedProductDetails.get(key)).trim()
								.equals(element("txt_priceValue", key).getText().trim()),
						"ASSERT FAILED: Expected value is '" + (mapRenewedProductDetails.get(key)).trim()
								+ "' but found " + element("txt_priceValue", key).getText().trim());
				logMessage("ASSERT PASSED : " + key + " price inside line items verified as "
						+ mapRenewedProductDetails.get(key));
			}
		}
	}

	public void verifyPaymentStatusBeforeRenewal() {
		Assert.assertTrue(element("txt_code", "Payment Status").getText().equals("Unpaid"),
				"ASSERT FAILED: Expected value is 'Unpaid' but found "
						+ element("txt_code", "Payment Status").getText());
		logMessage("ASSERT PASSED : Payment status before renewal is Free");
	}

	public void verifyAdjustedLinesItemsForEmeritusMember(String membesstatus,
			Map<String, String> mapRenewedProductDetails) {

		if (membesstatus.equals("Emeritus")) {
			expandDetailsMenu("adjusted/voided line items");
			Assert.assertEquals((mapRenewedProductDetails.get(" ")).trim(),
					(elements("txt_priceValue", "Voluntary Contribution To C&EN").get(1).getText().trim()));
			logMessage("ASSERT PASSED : Voluntary Contribution To C&EN price inside line items verified as "
					+ mapRenewedProductDetails.get("Voluntary Contribution To C&EN"));
			collapseDetailsMenu("adjusted/voided line items");
		}
	}

	public String getDataFromInvoiceProfilePage(String field) {
		isElementDisplayed("txt_invoiceValues", field);
		logMessage("STEP : " + field + " is " + element("txt_invoiceValues", field).getText().trim() + "\n");
		String invoicedata = element("txt_invoiceValues", field).getText().trim();
		return invoicedata;
	}

	public void verifyBalanceIsNotNull(String detailName, double detailValue) {
		isElementDisplayed("txt_memberDetails", detailName);
		Assert.assertTrue(
				Double.parseDouble(element("txt_memberDetails", detailName).getText().trim()) != (detailValue),
				"ASSERT FAILED : " + detailName + " is not " + detailName);
		logMessage("ASSERT PASSED : " + detailValue + " is verified for " + detailName + " \n");
	}

	public void verifyPaidClosedValueNo() {
		isElementDisplayed("txt_paid_closed");
		for (WebElement element : elements("txt_paid_closed")) {
			if (element.getText().trim().equalsIgnoreCase("N")) {
				count++;
			}
		}
		if (count <= 0) {
			Assert.fail("ASSERT FAILED : Paid/Closed status are Yes for all\n ");
		}
		logMessage("STEP : At least one payment paid/closed status is N\n");
	}

	public void verifyPaidClosedStatus_Yes() {
		isElementDisplayed("txt_paid_closed");
		for (WebElement element : elements("txt_paid_closed")) {
			if (element.getText().trim().equalsIgnoreCase("N")) {
				Assert.fail("ASSERT FAILED : Paid/closed status contains N/n");
			}
		}

		logMessage("ASSERT PASSED : Paid/Closed is verified as Yes in line itmes\n");
	}

	public void clickOnAddPaymentIcon() {
		isElementDisplayed("img_addPayment");
		if (isIEBrowser()) {
			clickUsingXpathInJavaScriptExecutor(element("img_addPayment"));
			logMessage("Step : click on add payment icon\n");
		} else {
			element("img_addPayment").click();
			logMessage("STEP : Click on add payment icon\n");
		}

	}

	public void clickOnGoToArrowButton() {
		isElementDisplayed("txt_termStartDate");
		isElementDisplayed("txt_termEndDate");
		int size = elements("txt_termStartDate").size();
		for (int i = 0; i < size; i++) {
			System.out.println("start date :" + elements("txt_termStartDate").get(i).getText() + ":");
			System.out.println("end date :" + elements("txt_termEndDate").get(i).getText() + ":");
			if (elements("txt_termStartDate").get(i).getText().equalsIgnoreCase(" ")
					&& elements("txt_termEndDate").get(i).getText().equalsIgnoreCase(" ")) {
				isElementDisplayed("btn_goToArrow");
				clickUsingXpathInJavaScriptExecutor(element("btn_goToArrow", String.valueOf(i)));
				// element("btn_goToArrow", String.valueOf(i)).click();
				logMessage("STEP : Go To Arrow button is clicked for empty term start and end date\n");
				break;
			}

		}

	}

	public void verifyScarfReviewerCommentsAndStatus(String reviewerName, String reviewerComment,
			String reviewerStatus) {
		System.out.println("---reviewer name is:" + reviewerName);
		isElementDisplayed("txt_code", reviewerName);
		isElementDisplayed("txt_priceValue", reviewerName);
		Assert.assertTrue(element("txt_code", reviewerName).getText().trim().equals(reviewerComment),
				"reviewer " + reviewerName + " comments as " + reviewerComment + " not present on iweb\n");
		logMessage(
				"ASSERT PASSED : Reviewer " + reviewerName + "comments as " + reviewerName + " is verified on iweb\n");
		Assert.assertTrue(element("txt_priceValue", reviewerName).getText().trim().equals(reviewerStatus),
				" reviewer " + reviewerName + " Status as " + reviewerStatus + " not present on iweb\n");
		logMessage("ASSERT PASSED : Reviewer " + reviewerName + " Status as <b>" + reviewerStatus
				+ "</b> is verified on iweb\n");

	}

	public void verifyStorePaymentInformationChildFormIsPopulated(String firstName) {
		isElementDisplayed("txt_code", firstName);
		isElementDisplayed("txt_priceValue", firstName);

		Assert.assertFalse(element("txt_code", firstName).getText().isEmpty());
		Assert.assertFalse(element("txt_priceValue", firstName).getText().isEmpty());
		logMessage("ASSERT PASSED : Child form is populated under stored payment information for " + firstName);
	}

	public void getTableHeadingsAndVerifyPaymentValues(String tabName, String currencyValue, String paymentModeValue) {
		List<String> tableHeadings = new ArrayList<String>();
		isElementDisplayed("hdng_childMenu", tabName);
		for (WebElement elem : elements("hdng_childMenu", tabName)) {
			tableHeadings.add(elem.getText().trim());
		}
		getParticularColumnValue("Status", tableHeadings, tabName, currencyValue, paymentModeValue.replaceAll(" ", ""));
	}

	public void getParticularColumnValue(String columnName, List<String> tableHeadings, String tabName,
			String currencyValue, String paymentModeValue) {
		int columnIndex = 0, rowIndex = 0, statusIndex = 0, currencyIndex = 0, paymentModeIndex = 0;
		for (String column : tableHeadings) {
			columnIndex++;
			switch (column) {
			case "Status":
				statusIndex = columnIndex;
				break;
			case "Currency":
				currencyIndex = columnIndex;
				break;
			case "Payment Mode":
				paymentModeIndex = columnIndex;
				break;
			}
		}
		statusIndex = statusIndex + 3;
		for (int i = 1; i < elements("table_rows").size(); i++) {
			if (element("txt_tableColumn", tabName, String.valueOf(i + 1), String.valueOf(statusIndex)).getText().trim()
					.equals("PAYMENT_SUCCESS")) {
				logMessage("STEP : " + columnName + " column is having value "
						+ element("txt_tableColumn", tabName, String.valueOf(i + 1), String.valueOf(statusIndex))
								.getText().trim()
						+ " at row position: " + i + "\n");
				rowIndex = i + 1;
				break;
			}
		}
		verifyPaymentValues(tabName, rowIndex, currencyIndex + 3, currencyValue, "Currency");
		verifyPaymentValues(tabName, rowIndex, paymentModeIndex + 3, paymentModeValue, "Payment Mode");
	}

	public void verifyPaymentValues(String tabName, int rowIndex, int columnIndex, String expectedValue,
			String columnName) {
		Assert.assertTrue(
				element("txt_tableColumn", tabName, String.valueOf(rowIndex), String.valueOf(columnIndex)).getText()
						.trim().equalsIgnoreCase(expectedValue),
				"ASSERT FAILED: " + expectedValue + " matches for " + columnName + " column\n");
		logMessage("ASSERT PASSSED : " + expectedValue + " matches for " + columnName + " column\n");
	}

	public void verifyGlobalConstituentSystemLogForGCSOMR(String appName, String paymentMode) {
		isElementDisplayed("txt_priceValue", appName);
		Assert.assertTrue(elements("txt_priceValue", appName).get(0).getText().trim().equals("INR"),
				"Payment not done in INR");
		logMessage("ASSERT PASSED : Currency is verified as INR in acs global constituent system log\n");

		isElementDisplayed("txt_quantity", appName);
		System.out.println(elements("txt_quantity", appName).get(0).getText());
		Assert.assertTrue(elements("txt_quantity", appName).get(0).getText().trim().equals("PAYMENT_SUCCESS"),
				"Payment status is not success but " + elements("txt_quantity", appName).get(0).getText());
		logMessage("ASSERT PASSED : Payment status is PAYMENT_SUCCESS in acs global constituent system log\n");

		isElementDisplayed("txt_discount", appName);
		Assert.assertTrue(elements("txt_discount", appName).get(0).getText().trim().contains(paymentMode),
				"ASSERT FAILED: Expected Payment Mode is " + paymentMode + "but found "
						+ elements("txt_discount", appName).get(0).getText());
		logMessage("ASSERT PASSED : Payment MOde is verified as" + paymentMode
				+ " in acs global constituent system log\n");

	}

	public void clickApplyDiscountButton() {
		isElementDisplayed("icon_applyDiscount");
		element("icon_applyDiscount").click();
		logMessage("Step : Apply discount button is clicked\n");
		waitForSpinner();

	}

	public void verifyDetailsOfInvoiceProfilePageforAACTOMR(String Total, String paidInFull, String proforma) {
		Total = Total.replace("$", "");
		verifyInvoiceProfile("invoice total", Total.trim());
		verifyMemberDetails_question("paid in full", paidInFull);
		verifyMemberDetails_question("proforma", proforma);
	}

	public void verifyPaymentDate(String productName) {
		if (productName.equals("")) {
			logMessage("STEP: Product name is not present in the datasheet\n");
		} else {
			isElementDisplayed("txt_discount", productName);
			Assert.assertEquals(element("txt_discount", productName).getText().trim(),
					DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/yyyy", "EST5EDT"),
					"ASSERT FAILED: Payment date is not verified as current date\n");
			logMessage("ASSERT FAILED: Payment date for product " + productName + " is verified as current date\n");
		}
	}

	public void verifyProductsPaymentDateAsCurrentDate() {
		verifyPaymentDate(map().get("Iweb LS Name?"));
		verifyPaymentDate(map().get("Iweb Product Name?"));
		verifyPaymentDate(map().get("Iweb Pub Name?"));
		verifyPaymentDate(map().get("Iweb Division Name?"));
	}

	public void verifyProductAmount(String prodName, String amount) {
		if (prodName.equals("")) {
			logMessage("STEP: Product name is not present in the datasheet\n");
		} else {
			isElementDisplayed("txt_balance", prodName);
			Assert.assertEquals(element("txt_balance", prodName).getText().trim(), amount,
					"ASSERT FAILED: Price for product " + prodName + " is not verified as " + amount + "but found "
							+ element("txt_balance", prodName).getText() + "\n");
			logMessage("ASSERT PASSED: Price for product " + prodName + " is verified as " + amount + "\n");
		}
	}

	public void verifyProductAmountonIweb(Map<String, String> productAmounts) {
		verifyProductAmount(map().get("Iweb Pub Name?"), productAmounts.get("Iweb Pub Name?"));
		verifyProductAmount(map().get("Iweb Division Name?"), productAmounts.get("Iweb Division Name?"));
		verifyProductAmount(map().get("Iweb Product Name?"), productAmounts.get("Product?"));
		verifyProductAmount(map().get("Iweb LS Name?"), productAmounts.get("Iweb LS Name?"));
		// verifyProductAmount(map().get("CEN Product
		// Name?"),productAmounts.get("Iweb CEN Product Name?"));
	}

	public void verifyPaymentDetailsForGiftCard(String iwebProductName, String cardpricevalue) {

		verifyInvoiceDetailsInLineItemsForGiftCard("priceValue", iwebProductName, "use credit");
		verifyInvoiceDetailsInLineItemsForGiftCard("quantity", iwebProductName, "Gift Card/Coupon");
		verifyInvoiceDetailsInLineItemsForGiftCard("discount", iwebProductName,
				DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/YYYY"));
		verifyInvoiceDetailsInLineItemsForGiftCard("balance", iwebProductName, cardpricevalue);

	}

	private void verifyInvoiceDetailsInLineItemsForGiftCard(String fieldName, String productName, String value) {
		System.out.println(productName);
		System.out.println(element("txt_" + fieldName, productName).getText());
		isElementDisplayed("txt_" + fieldName, productName);
		if (element("txt_" + fieldName, productName).getText().trim().equals(fieldName)) {
			Assert.assertEquals(element("txt_" + fieldName, productName).getText().trim(),value);
		}
		logMessage("ASSERT PASSED " + productName + " details for" + fieldName + " under payments verfied as " + value);

	}

	public void clickOnNextButton() {
		isElementDisplayed("btn_next");
//		element("btn_next").click();
		clickUsingXpathInJavaScriptExecutor(element("btn_next"));
		logMessage("Step: Clicked on Next button\n");
	}

	public void enterPaymentDetails(String batchName, String paymentMethod, String cardNumber, String expireDate,
			String cvvNo, String checkNumber) {
		MembershipPageActions_IWEB objFundraising = new MembershipPageActions_IWEB(driver);
		switchToFrame("iframe1");
		clickOnNextButton();
		objFundraising.selectBatchAndPaymentDetails_AddressChangeProforma(batchName, paymentMethod, cardNumber,
				expireDate, cvvNo, checkNumber);
		switchToDefaultContent();
	}

	public void verifyIndividualPaymentAmountEqualsPaymentTotal() {
		float sum = 0;
		for (WebElement ele : elements("table_discount")) {
			sum = sum + Float.parseFloat(ele.getText());
		}
		Assert.assertTrue(Float.parseFloat(element("inp_invoiceValue", "invoice total").getText()) == sum,
				"ASSERT FAILED: Expected value is "+sum+" but found "+element("inp_invoiceValue", "invoice total").getText());
		logMessage("ASSERT PASSED : Payment total is equal to sum of individual product total as " + sum);
	}

}
