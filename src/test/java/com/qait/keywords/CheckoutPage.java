package com.qait.keywords;

import java.util.Calendar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class CheckoutPage extends ASCSocietyGenericPage {

	WebDriver driver;
	String pagename = "CheckoutPage";
	String memberName, productName, priceValue,

	PublicationName, TechnicalDivision, multiYearDecisionValue, priceValues,
			amountValue, cenStatus;
	boolean flag;
	String[] quantity = new String[5];
	String[] productNames = new String[5];
	float amountInFloat;
	int currentMonthInInteger = Calendar.getInstance().get(Calendar.MONTH)+1;
	static int mutliYearInInteger = 0;
	int nextYearInInteger = Calendar.getInstance().get(Calendar.YEAR) + 1;

	public CheckoutPage(WebDriver driver) {
		super(driver, "CheckoutPage");
		this.driver = driver;
	}

	public void enterPaymentInfo(String creditCardType,
			String creditCardHolderName, String creditCardNumber,
			String CvvNumber) {
		selectCreditCardInfo("Type", creditCardType);
		enterCreditCardInfo("creditCardHoldNo", creditCardHolderName);
		enterCreditCardInfo("creditCardNumber", creditCardNumber);
		wait.waitForPageToLoadCompletely();
		wait.hardWait(3);
		selectExpirationMonth("ExpirationMonth");
		selectNextYear("ExpirationYear");
		enterCreditCardInfo("CcvNumber", CvvNumber);
	}

	public void clickAtTestStatement() {
		isElementDisplayed("inp_AtTestStatement");
		click(element("inp_AtTestStatement"));
		logMessage("Step: at test statement is clicked in  inp_AtTestStatement\n");
	}

	public void clickSubmitButtonAtBottom() {
		isElementDisplayed("btn_submitBottom");
		click(element("btn_submitBottom"));
		logMessage("Step: submit button at bottom is clicked in  btn_submitBottom\n");
		//cancelOutPopUp();
	}

	public void clickSubmitButton() {
		isElementDisplayed("btn_submitBottom");
		click(element("btn_submitBottom"));
		logMessage("Step: submit button at bottom is clicked in  btn_submitBottom\n");

	}

	public void cancelOutPopUp() {
		isElementDisplayed("btn_cancelPopup");
		click(element("btn_cancelPopup"));
		logMessage("Step: cancel in pop up is clicked in  btn_cancelPopup\n");
	}

	public void verifyMemberName(String caseId) {
		if (getOmaSheetValue(caseId, "Member Type?").equalsIgnoreCase("")) {
			logMessage("Step : member name is not present in data sheet\n");
		} else {
			verifyElementText("txt_memberName",
					getOmaSheetValue(caseId, "Member Type?"));
			logMessage("ASSERT PASSED : "
					+ getOmaSheetValue(caseId, "Member Type?")
					+ " is verified in  txt_memberName\n");
		}

	}

	public void verifyMemberName_AACTOMA(String caseId) {
		if (getAACT_OmaSheetValue(caseId, "Member Type?").equalsIgnoreCase("")) {
			logMessage("Step : member name is not present in data sheet\n");
		} else {
			verifyElementText("txt_memberName",
					getAACT_OmaSheetValue(caseId, "Member Type?"));
			logMessage("ASSERT PASSED : "
					+ getAACT_OmaSheetValue(caseId, "Member Type?")
					+ " is verified in  txt_memberName\n");
		}

	}

	public void verifyMemberDetail(String caseId) {
		MemberDetails("memberName", getOmaSheetValue(caseId, "Member Type?"));
		MemberDetails("productName", getOmaSheetValue(caseId, "Product?"));
	}

	public void verifyMemberDetail_AACTOMA(String caseId) {
		hardWaitForIEBrowser(4);
		wait.waitForPageToLoadCompletely();
		MemberDetails("memberName",
				getAACT_OmaSheetValue(caseId, "Member Type?"));
		// MemberDetails("productName", getOmaSheetValue(caseId, "Product?"));
	}

	public void MemberDetails(String memberDetail, String memberDetailvalue) {
		if (memberDetailvalue.equalsIgnoreCase("")) {
			logMessage("Step : " + memberDetail
					+ " value is not present in data\n");
		} else {
			verifyElementTextContains("txt_" + memberDetail, memberDetailvalue);
			logMessage("ASSERT PASSED : " + memberDetailvalue
					+ " is verified in  txt_" + memberDetail + "\n");
		}

	}

	public void verifyMemberEmail(String memberEmail) {
		verifyElementText("txt_userEmail", memberEmail);
		logMessage("ASSERT PASSED : " + memberEmail
				+ " is verified in  txt_userEmail\n");
	}

	public void verifyTechnicalDivision(String caseId) {
		if (getOmaSheetValue(caseId, "Technical Division").equalsIgnoreCase("")) {
			logMessage("Step : division name is not present in data sheet\n");
		} else {
			isElementDisplayed("txt_technicalDivision",
					getOmaSheetValue(caseId, "Technical Division"));
			logMessage("ASSERT PASSED : "
					+ getOmaSheetValue(caseId, "Technical Division")
					+ " is verified in  txt_technicalDivision\n");
		}

	}

	public void verifyPublication(String caseId) {
		if (getOmaSheetValue(caseId, "PublicationName").equalsIgnoreCase("")) {
			logMessage("Step : publication name is not present in data sheet\n");
		} else {
			isElementDisplayed("txt_publication",
					getOmaSheetValue(caseId, "PublicationName"));
			logMessage("ASSERT PASSED : "
					+ getOmaSheetValue(caseId, "PublicationName")
					+ " is verified in  txt_publication\n");
		}

	}

	private void enterCreditCardInfo(String creditCardInfo, String value) {
		isElementDisplayed("inp_" + creditCardInfo);
		element("inp_" + creditCardInfo).sendKeys(value);
		logMessage("Step: " + value + " is entered in  inp_" + creditCardInfo
				+ "\n");
	}

	private void selectCreditCardInfo(String creditCardInfo, String value) {
		isElementDisplayed("list_creditCard" + creditCardInfo);
		selectProvidedTextFromDropDown(element("list_creditCard"
				+ creditCardInfo), value);
		logMessage("Step: " + value + " is selected in  list_creditCard"
				+ creditCardInfo + "\n");
	}

	private void selectExpirationMonth(String ExpirationMonth) {
		if (currentMonthInInteger < 10) {
			String currentMonth = "0" + String.valueOf(currentMonthInInteger);
			isElementDisplayed("list_" + ExpirationMonth);
			selectProvidedTextFromDropDown(element("list_" + ExpirationMonth),
					currentMonth);
			logMessage("Step: " + currentMonth + " is selected in  list_"
					+ ExpirationMonth + "\n");

		} else {
			String currentMonth = String.valueOf(currentMonthInInteger);
			isElementDisplayed("list_" + ExpirationMonth);
			selectProvidedTextFromDropDown(element("list_" + ExpirationMonth),
					currentMonth);
			logMessage("Step: " + currentMonth + " is selected in  list_"
					+ ExpirationMonth + "\n");
		}
	}

	private void selectNextYear(String ExpirationYear) {
		if (nextYearInInteger < 10) {
			String nextYear = "0" + String.valueOf(nextYearInInteger);
			isElementDisplayed("list_" + ExpirationYear);
			selectProvidedTextFromDropDown(element("list_" + ExpirationYear),
					nextYear);
			logMessage("Step: " + nextYear + " is selected in  list_"
					+ ExpirationYear + "\n");
		} else {
			String nextYear = String.valueOf(nextYearInInteger);
			isElementDisplayed("list_" + ExpirationYear);
			selectProvidedTextFromDropDown(element("list_" + ExpirationYear),
					nextYear);
			logMessage("Step: " + nextYear + " is selected in  list_"
					+ ExpirationYear + "\n");
		}

	}

	public String[] verifyPriceValues(String caseId) {
		String[] productNames = { getOmaSheetValue(caseId, "Product?"),
				getOmaSheetValue(caseId, "Iweb Pub Name?"),
				getOmaSheetValue(caseId, "Iweb Division Name?"),
				getPriceSheetValue(caseId, "LSproductName?"),
				getPriceSheetValue(caseId, "CENproductName?") };
		verifyMultiYearShow_Hide(getOmaSheetValue(caseId, "multiYearFlag?"));
		if (!getOmaSheetValue(caseId, "multiYearDecision").equalsIgnoreCase("")) {
			mutliYearInInteger = Integer.parseInt(getOmaSheetValue(caseId,
					"multiYearDecision"));
		} else {
			mutliYearInInteger = 0;
		}
		if (getOmaSheetValue(caseId, "multiYearFlag?").equalsIgnoreCase("SHOW")) {
			if (getOmaSheetValue(caseId, "multiYearDecision").equalsIgnoreCase(
					"2")) {
				multiYearDecisionValue = "Two";
				click(element("rad_multiYear", multiYearDecisionValue));
				logMessage("Step : multiYearDecision " + multiYearDecisionValue
						+ " value is clicked in rad_multiYear\n");
				logMessage("Step : wait for price values to be changed after selection of multi year value\n");
				wait.waitForElementToDisappear(element("txt_multiYearWait"));
			} else if (getOmaSheetValue(caseId, "multiYearDecision")
					.equalsIgnoreCase("3")) {
				multiYearDecisionValue = "Three";
				click(element("rad_multiYear", multiYearDecisionValue));
				logMessage("Step : multiYearDecision " + multiYearDecisionValue
						+ " value is clicked in rad_multiYear\n");
				logMessage("Step : wait for price values to be changed after selection of multi year value\n");
				wait.waitForElementToDisappear(element("txt_multiYearWait"));
			} else {
				logMessage("Step : multiyear flag is not present in price value sheet\n");
			}
		}

		// verifyPriceType(getOmaSheetValue(caseId, "Iweb Pub Name?"),
		// "Tax",
		// getPriceSheetValue(caseId, "pubsTax?"), 1);
		// verifyPriceType(getOmaSheetValue(caseId, "Iweb Pub Name?"),
		// "amount", getPriceSheetValue(caseId, "pubsPrice?"), 1);
		verifyPriceType(getOmaSheetValue(caseId, "Iweb Pub Name?"), "shipping",
				getPriceSheetValue(caseId, "pubsShipping?"), 1);

		verifyPriceType(getOmaSheetValue(caseId, "Iweb Division Name?"), "Tax",
				getPriceSheetValue(caseId, "divisionTax?"), mutliYearInInteger);
		verifyPriceType(getOmaSheetValue(caseId, "Iweb Division Name?"),
				"amount", getPriceSheetValue(caseId, "divisionPrice?"),
				mutliYearInInteger);
		verifyPriceType(getOmaSheetValue(caseId, "Iweb Division Name?"),
				"shipping", getPriceSheetValue(caseId, "divisionShipping?"),
				mutliYearInInteger);
		verifyPriceType(getOmaSheetValue(caseId, "Product?"), "Tax",
				getPriceSheetValue(caseId, "productTax?"), mutliYearInInteger);
		wait.hardWait(3);
		verifyPriceType(getOmaSheetValue(caseId, "Product?"), "amount",
				getPriceSheetValue(caseId, "Price value?"), mutliYearInInteger);
		verifyPriceType(getOmaSheetValue(caseId, "Product?"), "shipping",
				getPriceSheetValue(caseId, "productShipping?"),
				mutliYearInInteger);
		verifyPriceType(getPriceSheetValue(caseId, "CENproductName?"), "Tax",
				getPriceSheetValue(caseId, "CENTax?"), mutliYearInInteger);
		verifyPriceType(getPriceSheetValue(caseId, "CENproductName?"),
				"amount", getPriceSheetValue(caseId, "CENprice?"),
				mutliYearInInteger);
		verifyPriceType(getPriceSheetValue(caseId, "CENproductName?"),
				"shipping", getPriceSheetValue(caseId, "CENShipping?"),
				mutliYearInInteger);
		verifyPriceType(getOmaSheetValue(caseId, "Iweb LS Name?"), "Tax",
				getPriceSheetValue(caseId, "LSTax?"), mutliYearInInteger);
		verifyPriceType(getOmaSheetValue(caseId, "Iweb LS Name?"), "shipping",
				getPriceSheetValue(caseId, "LSShipping?"), mutliYearInInteger);
		verifyPriceType(getOmaSheetValue(caseId, "Iweb LS Name?"), "amount",
				getPriceSheetValue(caseId, "LSDues?"), mutliYearInInteger);

		verifyCENProductName("txt_cen_LSProduct",
				getPriceSheetValue(caseId, "CENproductName?"));
		verifyLSProductName("txt_cen_LSProduct",
				getOmaSheetValue(caseId, "Iweb LS Name?"));
		return getProductQuantity(productNames);
	}

	private String[] getProductQuantity(String[] productName) {
		for (int i = 0; i < productName.length; i++) {
			isElementDisplayed("txt_quantity", productName[i]);
			quantity[i] = element("txt_quantity", productName[i]).getText();

		}
		return quantity;
	}

	private void verifyMultiYearShow_Hide(String multiYearFlagValue) {
		verifyFieldVisibility("div_multiYear", multiYearFlagValue);
	}

	private void verifyCENProductName(String elementName, String cenProductName) {
		if (cenProductName.equalsIgnoreCase("")) {
			logMessage("Step : cen product name is not present in data sheet\n");
		} else {
			isElementDisplayed(elementName, cenProductName);
			logMessage("ASSERT PASSED : " + cenProductName + " is verified in "
					+ elementName + "\n");
		}

	}

	private void verifyLSProductName(String elementName, String LSProductName) {
		if (LSProductName.equalsIgnoreCase("")) {
			logMessage("Step : local section product name is not present in data sheet\n");
		} else {
			isElementDisplayed(elementName, LSProductName);
			logMessage("ASSERT PASSED  : " + LSProductName + " is verified in "
					+ elementName + "\n");
		}

	}

	private void verifyPriceType(String productName, String priceType,
			String priceValue, int multiYear) {
		if (productName.equalsIgnoreCase("")) {

		} else {
			priceValues = element("txt_" + priceType, productName).getText()
					.replace("$", "");
			if (priceValue.equalsIgnoreCase("")) {
				logMessage("STEP : price value for " + productName
						+ " is not present in data sheet\n");
			}
			if (multiYear > 1) {
				String priceInDataSheet = priceValue.trim().replaceAll("\\$",
						"");
				Float price = Float.parseFloat(priceInDataSheet);
				String priceValueInDataSheet = "$"
						+ String.valueOf(price * multiYear);
				Float actualPriceValue = Float.parseFloat(priceValues);
				String actualPriceValueInString = "$" + actualPriceValue;
				Assert.assertEquals(actualPriceValueInString,
						priceValueInDataSheet);
				logMessage("ASSERT PASSED : " + priceValue + " " + priceType
						+ " for " + productName + " is verified in txt_"
						+ priceType + "\n");
			} else {
				isElementDisplayed("txt_" + priceType, productName);
				Assert.assertEquals("$" + priceValues, priceValue);
				logMessage("ASSERT PASSED : " + priceValue
						+ " is verified in txt_" + priceType + "\n");
			}
		}

	}

	private void verifyPriceType_AACTOMA(String productName, String priceType,
			String priceValue) {
		if (productName.equalsIgnoreCase("")) {
			logMessage("Step : product name is not present in data sheet to verify price values at checkout page\n");
		} else {
			priceValues = element("txt_" + priceType, productName).getText();
			if (priceValue.equalsIgnoreCase("")) {
				logMessage("STEP : price value for " + productName
						+ " is not present in data sheet\n");
			}
			isElementDisplayed("txt_" + priceType, productName);
			Assert.assertEquals(priceValues, priceValue);
			logMessage("ASSERT PASSED : " + priceValue + " is verified in txt_"
					+ priceType + "\n");
		}
	}

	public void verifyPaymentErrorPresent(String errorMsz) {
		verifyElementTextContains("txt_paymentError", errorMsz);
		logMessage("ASSERT PASSED : " + errorMsz
				+ " is verified in txt_paymentError\n");
	}

	private String getTotalAmountValue(String totalPriceName) {
		for (WebElement element : elements("list_Totalvalues", totalPriceName)) {
			amountValue = element.getText().replaceAll("\\$", "");
			amountInFloat = amountInFloat + Float.parseFloat(amountValue);
		}
		String formatedPrice = String.format("%.02f", amountInFloat);
		String amountInString = "$" + String.valueOf(formatedPrice);
		return amountInString;
	}

	public String verifyProductSubTotal(String totalPriceName,
			String subtotalName) {
		String totalAmountExpected = getTotalAmountValue(totalPriceName);
		String totalAmountActual = getTotal(subtotalName);
		Assert.assertEquals(totalAmountExpected, totalAmountActual);
		logMessage("ASSERT PASSED : " + totalAmountExpected + " and "
				+ totalAmountActual + " is equal in txt_paymentError\n");
		return totalAmountActual;
	}

	private String getTotal(String value) {
		if (value.equalsIgnoreCase("Tax")) {
			isElementDisplayed("txt_taxTotal", value);
			return element("txt_taxTotal", value).getText();
		} else {
			isElementDisplayed("txt_total", value);
			return element("txt_total", value).getText();
		}
	}

	public String verifyTotal() {
		float productSubTotalInfloat = Float.parseFloat(getTotal(
				"Product Subtotal").replaceAll("\\$", ""));
		float shippingTotalInfloat = Float.parseFloat(getTotal("Shipping")
				.replaceAll("\\$", ""));
		float taxTotalInfloat = Float.parseFloat(getTotal("Tax").replaceAll(
				"\\$", ""));
		float TotalInfloat = Float.parseFloat(getTotal("Total").replaceAll(
				"\\$", ""));
		float totalPrice = productSubTotalInfloat + shippingTotalInfloat
				+ taxTotalInfloat;
		Assert.assertEquals(totalPrice, TotalInfloat);
		logMessage("ASSERT PASSED : total price value " + totalPrice
				+ " is verified \n");
		String formatedPrice = String.format("%.02f", TotalInfloat);
		String totalInString = "$" + String.valueOf(formatedPrice);
		return totalInString;
	}

	public String[] getDeliveryMethods(String caseId) {
		String deliveryMethodsInSheet = getAACT_OmaSheetValue(caseId,
				"Delivery Method?");
		String[] deliveryMethods = deliveryMethodsInSheet.split("_");
		return deliveryMethods;
	}

	public void verifyDeliveryMethodOptions(String caseId) {
		isElementDisplayed("list_deliveryOptions");
		for (WebElement deliveryMethod : elements("list_deliveryOptions")) {
			for (String deliveryMethodInSheet : getDeliveryMethods(caseId)) {
				if (deliveryMethod.getText().equalsIgnoreCase(
						deliveryMethodInSheet)) {
					flag = true;
					logMessage("AASERT PASSED : " + deliveryMethod.getText()
							+ " is verified in delivery methods list\n");
					break;
				}
			}
			if (!flag) {
				logMessage("AASERT FAILED : " + deliveryMethod.getText()
						+ " is not verified in delivery methods list\n");
			}
		}
		if (!flag) {
			Assert.fail("AASERT FAILED : Delivery methods are not verified in the list\n");
		}
	}

	public void verifyDefaultDeliveryMethod(String caseId) {
		isElementDisplayed("list_deliveryMethods");
		if (getSelectedTextFromDropDown(element("list_deliveryMethods"))
				.equalsIgnoreCase(
						getAACT_OmaSheetValue(caseId,
								"Default Delivery Method?"))) {
			logMessage("ASSERT PASSED : "
					+ getAACT_OmaSheetValue(caseId, "Default Delivery Method?")
					+ " is by default selected\n");
			flag = true;
		}
		if (!flag) {
			Assert.fail(getAACT_OmaSheetValue(caseId,
					"Default Delivery Method?")
					+ " is not by default selected\n");
		}
	}

	public void selectDeliveryMethod(String caseId) {
		isElementDisplayed("list_deliveryMethods");
		selectProvidedTextFromDropDown(element("list_deliveryMethods"),
				getAACT_OmaSheetValue(caseId, "Select Delivery Method"));
		if (getSelectedTextFromDropDown(element("list_deliveryMethods"))
				.equalsIgnoreCase(
						getAACT_OmaSheetValue(caseId, "Select Delivery Method"))) {
			logMessage("Step : "
					+ getAACT_OmaSheetValue(caseId, "Select Delivery Method")
					+ " is selected in delivery method\n");
		} else {
			Assert.fail("Delivery method is not selected\n");
		}

	}

	public void selectAndVerifyAllAndDefaultDeliveryMethods(String caseId) {
		verifyDeliveryMethodOptions(caseId);
		verifyDefaultDeliveryMethod(caseId);
		selectDeliveryMethod(caseId);
	}

	public void verifyAACTNationalMembership(String caseId) {
		for (WebElement ele : elements("list_AACTNationalMem")) {
			if (ele.getText()
					.equalsIgnoreCase(
							getAACT_OmaSheetValue(caseId,
									"AACT National Membership 1?"))) {
				logMessage("ASSERT PASSED : AACT national membership "
						+ getAACT_OmaSheetValue(caseId,
								"AACT National Membership 1?")
						+ " is verified \n");
			} else if (ele.getText()
					.equalsIgnoreCase(
							getAACT_OmaSheetValue(caseId,
									"AACT National Membership 2?"))) {
				logMessage("ASSERT PASSED : AACT national membership "
						+ getAACT_OmaSheetValue(caseId,
								"AACT National Membership 2?")
						+ " is verified \n");
			} else {
				Assert.fail("AACT National Membership is not vewrified\n");
			}
		}

	}

	public void verifyPriceValues_AACT(String caseId) {
		verifyPriceType_AACTOMA(
				getAACT_OmaSheetValue(caseId, "AACT National Membership 1?"),
				"shipping", "$0.00");

		verifyPriceType_AACTOMA(
				getAACT_OmaSheetValue(caseId, "AACT National Membership 1?"),
				"Tax", "$0.00");
		verifyPriceType_AACTOMA(
				getAACT_OmaSheetValue(caseId, "AACT National Membership 1?"),
				"amount", getAACT_OmaSheetValue(caseId, "Product Subtotal?"));

		verifyPriceType_AACTOMA(
				getAACT_OmaSheetValue(caseId, "AACT National Membership 2?"),
				"shipping", "$0.00");

		verifyPriceType_AACTOMA(
				getAACT_OmaSheetValue(caseId, "AACT National Membership 2?"),
				"Tax", "$0.00");
		verifyPriceType_AACTOMA(
				getAACT_OmaSheetValue(caseId, "AACT National Membership 2?"),
				"amount", "$0.00");

	}

}
