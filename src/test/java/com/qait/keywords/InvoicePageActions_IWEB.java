package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.DateUtil;

public class InvoicePageActions_IWEB extends ASCSocietyGenericPage {

	WebDriver driver;
	String pagename = "InvoicePage_IWEB";
	String invoiceTotal, LSDues;
	int timeOut, hiddenFieldTimeOut;

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
		logMessage("STEP : " + invoiceNumber
				+ " is entered to search in inp_invoiceCode\n");
	}

	private void clickOnSearchButton() {
		isElementDisplayed("btn_search");
		element("btn_search").click();
		logMessage("STEP : Search button is clicked in txt_invoiceMenu\n");
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

	public void verifyInvoiceDetailsInLineItems(String name,
			Map<String, String> mapGetMemberDetailsInStore) {
		Assert.assertTrue(element("table_" + name).getText().trim()
				.equals(mapGetMemberDetailsInStore.get(name)));
		logMessage("ASSERT PASSED : " + name
				+ " details on invoice profile succesfully verified as "
				+ mapGetMemberDetailsInStore.get(name) + "\n");
	}

	public void verifyInvoiceDetails_LineItems(
			Map<String, String> mapGetMemberDetailsInStore, String... name) {
		for (String names : name) {
			verifyInvoiceDetailsInLineItems(names, mapGetMemberDetailsInStore);
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
							.trim() + " is verified in txt_" + detailName);
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

	public void verifyInvoiceProfile(String detailName, String detailValue) {
		if (detailValue.equalsIgnoreCase("")) {
			logMessage("Step : value of " + detailName
					+ "  price is empty in data sheet\n");
		} else {
			isElementDisplayed("txt_invoiceValues", detailName);
			String detailValueWithOutDollar = detailValue.replace("$", "");

			System.out.println(detailValueWithOutDollar);
			System.out.println(element("txt_invoiceValues", detailName).getText().trim());
			Assert.assertTrue(element("txt_invoiceValues", detailName).getText().trim()

					.equalsIgnoreCase(detailValueWithOutDollar));
			logMessage("ASSERT PASSED : " + detailValueWithOutDollar
					+ " is verified in txt_" + detailName + "\n");
		}
	}

	private void verifyInvoiceProfile_AACTOMA(String detailName,
			String detailValue) {
		if (detailValue.equalsIgnoreCase("")) {
			logMessage("Step : Value of " + detailName
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
		logMessage("STEP : " + menuName + " bar is clicked to expand" + "\n");
		waitForSpinner();

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
			logMessage("STEP : Wait for spinner to be disappeared \n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (NoSuchElementException | AssertionError | TimeoutException Exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("STEP : Spinner is not present \n");
		}
	}

	public void collapseDetailsMenu(String menuName) {
		isElementDisplayed("icon_up", menuName);
		element("icon_up", menuName).click();
		waitForSpinner();
		logMessage("STEP : " + menuName + " bar collapse bar clicked\n");

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
		// verifyMemberDetails("invoice number", invoiceNumber);
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
		// verifyInvoiceDetailsInInvoiceTable("invoiceId", invoiceId);
		verifyInvoiceDetailsInInvoiceTable("productname", productname);

	}

	public void validateBalanceAndTotalForInvoice(Map<String,String> TotalAmountMap) {
		Iterator iterator = TotalAmountMap.keySet().iterator();
		while (iterator.hasNext()) {
		   String key = iterator.next().toString();
		   String value = TotalAmountMap.get(key);

		   System.out.println(key + " " + value);
		}
         if(TotalAmountMap.size()!=4&&!TotalAmountMap.get("IsProgramPledged").equals("1"))
         {
        	 System.out.println("In bbbbbbb");
	verifyInvoiceProfile("balance", "0.00");
	verifyInvoiceProfile("invoice total",TotalAmountMap.get("TotalAmount")+"0");
	System.out.println(TotalAmountMap.size());
         }
         else if(TotalAmountMap.size()==4&&TotalAmountMap.get("pledgedMonthlyTotal").equals("true"))
         {
        	 Double balance=  Double.parseDouble(TotalAmountMap.get("TotalAmount"))-Double.parseDouble( TotalAmountMap.get("MonthlyAmount"));
        	 verifyInvoiceProfile("invoice total",TotalAmountMap.get("TotalAmount")+"0");
        	 verifyInvoiceProfile("balance", String.valueOf(balance)+"0");
        		System.out.println(TotalAmountMap.size());
        	 System.out.println("In aaaaaaaaaaa");
         }
	}

	public void verfifyproductDisplayNamesAndCodesInsideLineItems(Map<String,String> TotalAmountMap,String[] Amount,String[] productNameKey,Map<String,List<String>> mapIwebProductDetails) {
		 if(TotalAmountMap.size()!=4&&!TotalAmountMap.get("IsProgramPledged").equals("1"))
         {
			 for(int i=0;i<elements("table_description").size();i++)
			 {
				 for(int j=0;j<productNameKey.length;j++)
				 {
	     	if(elements("table_description").get(i).getText().trim().equals(mapIwebProductDetails.get(productNameKey[j]).get(0)))
			     { 
	     		Assert.assertTrue(elements("table_description").get(i).getText().trim().equals(mapIwebProductDetails.get(productNameKey[j]).get(0)));
	     		logMessage("ASSERT PASSED : Product Display Name verified as "+mapIwebProductDetails.get(productNameKey[j]).get(0));
	     	 	Assert.assertTrue(elements("table_code").get(i).getText().trim().equals(mapIwebProductDetails.get(productNameKey[j]).get(1)));
	     	 	logMessage("ASSERT PASSED : Product Code for Product "+productNameKey[j]+" is verified as "+mapIwebProductDetails.get(productNameKey[j]).get(1));
				Assert.assertTrue(elements("table_quantity").get(i).getText().trim().equals("1"));
				logMessage("ASSERT PASSED : Product Quantity is verified as 1 ");
				Assert.assertTrue(elements("table_discount").get(i).getText().trim().equals("0.00"));
				logMessage("ASSERT PASSED : Product discount is verified as 0.00 ");
				Assert.assertTrue(elements("table_balance").get(i).getText().trim().equals("0.00"));
				logMessage("ASSERT PASSED : Product balance is verified as 0.00 ");
				verifyEachProductAmountOnListItems(productNameKey[i],Amount);
       			 }
 			 }
			 }
         }
		
	}

	private void verifyEachProductAmountOnListItems(String productName,String[] Amount) {
		if(productName.length()!=0)
		{
			switch(productName)
			{
			case "Project SEED": Assert.assertTrue(element("table_priceValue").getText().trim().equals(Amount[0]+".00"));
			                     break;
			                     
			case "ACS Scholars Program": Assert.assertTrue(element("table_priceValue").getText().trim().equals(Amount[1]+".00"));
                                 break;
                                 
			case "Advancing Chemistry Teaching": Assert.assertTrue(element("table_priceValue").getText().trim().equals(Amount[2]+".00"));
                                 break;
                                 
			default : Assert.assertTrue(element("table_priceValue").getText().trim().equals(Amount[3]+".00"));
                                 break;
			
			}
		}
	}
	
	

}
