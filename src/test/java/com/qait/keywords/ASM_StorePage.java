package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.YamlReader;

public class ASM_StorePage extends ASCSocietyGenericPage {
	WebDriver driver;
	String pagename = "ASM_StorePage";
	Map<String, String> map = new HashMap<String, String>();
	Map<String, String> map2 = new HashMap<String, String>();
	Map<String, Object> mapStoreSmoke = YamlReader.getYamlValues("ASM_StoreSmokeChecklist_Data");
	YamlInformationProvider getStoreSmokeValue = new YamlInformationProvider(mapStoreSmoke);
	ASMErrorPage asmErrorPage;
	String FirstName = getStoreSmokeValue.getASM_StoreShippingAddress("FName");
	String LastName = getStoreSmokeValue.getASM_StoreShippingAddress("LName");
	String MiddleName = getStoreSmokeValue.getASM_StoreShippingAddress("MName");
	String CompanyName = getStoreSmokeValue.getASM_StoreShippingAddress("Company");
	String Department = getStoreSmokeValue.getASM_StoreShippingAddress("Department");
	String Adr_Line1 = getStoreSmokeValue.getASM_StoreShippingAddress("Address");
	String Adr_Line2 = getStoreSmokeValue.getASM_StoreShippingAddress("Address2");
	String City = getStoreSmokeValue.getASM_StoreShippingAddress("City");
	String State = getStoreSmokeValue.getASM_StoreShippingAddress("State");
	String ZipCode = getStoreSmokeValue.getASM_StoreShippingAddress("ZipCode");
	String Country = getStoreSmokeValue.getASM_StoreShippingAddress("Country");
	String Province = getStoreSmokeValue.getASM_StoreShippingAddress("IntlProvince");
	String CardHolderName = getStoreSmokeValue.getASM_StorePaymentInfo("CardHolderName");
	String CreditCardNumber = getStoreSmokeValue.getASM_StorePaymentInfo("CreditCardNumber");
	String CreditCardVerificationNumber = getStoreSmokeValue.getASM_StorePaymentInfo("CreditCardVerificationNumber");
	String CreditCardType = getStoreSmokeValue.getASM_StorePaymentInfo("CreditCardType");
	String CreditCardExpiration = getStoreSmokeValue.getASM_StorePaymentInfo("CreditCardExpiration");
	int timeOut, hiddenFieldTimeOut;

	public ASM_StorePage(WebDriver driver) {
		super(driver, "ASM_StorePage");
		this.driver = driver;
	}

	public void searchText(String searchValue) {
		enterTextInSearchField(searchValue);
		clickSearchButton();
	}

	public void enterTextInSearchField(String searchValue) {
		isElementDisplayed("inp_search");
		element("inp_search").clear();
		element("inp_search").sendKeys(searchValue);
		logMessage("STEP : " + searchValue + " is entered in inp_search\n");
	}

	public void clickSearchButton() {
		isElementDisplayed("btn_search");
		element("btn_search").click();
		logMessage("STEP : search button is clicked in btn_search\n");
	}

	public void verifySearchSuccessfully() {
		hardWaitForIEBrowser(2);
		isElementDisplayed("txt_searchList");
		verifyElementTextContains("txt_searchList", "Search Results");
		logMessage("STEP : Verify search successfully in txt_searchList\n");
	}

	public void clickLoginButton() {
		isElementDisplayed("lnk_logIn");
		clickUsingXpathInJavaScriptExecutor(element("lnk_logIn"));
		logMessage("STEP : LogIn button is clicked in lnk_logIn\n");
	}

	public void enterUserName(String userName) {
		try {
			isElementDisplayed("inp_userName");
			element("inp_userName").clear();
			element("inp_userName").sendKeys(userName);
			logMessage("STEP : " + userName + " is entered in inp_userName\n");
		} catch (StaleElementReferenceException stlExp) {
			isElementDisplayed("inp_userName");
			element("inp_userName").clear();
			element("inp_userName").sendKeys(userName);
			logMessage("STEP : " + userName + " is entered in inp_userName\n");
		}

	}

	public void enterPassword(String password) {
		wait.hardWait(3);
		isElementDisplayed("inp_password");
		element("inp_password").clear();
		element("inp_password").sendKeys(password);
		logMessage("STEP : " + password + " is entered in inp_password\n");
	}

	public void clickVerifyButton() {
		wait.hardWait(3);
		isElementDisplayed("btn_verify");
		clickUsingXpathInJavaScriptExecutor(element("btn_verify"));
		// element("btn_verify").click();
		logMessage("STEP : Verify button is clicked in btn_verify\n");
	}

	public void loginIntoApplication(String userName, String password) {
		wait.waitForPageToLoadCompletely();
		clickLoginButton();
		enterUserName(userName);
		enterPassword(password);
		clickVerifyButton();
	}

	public void verifyLoginSuccessfully() {
		try {
			isElementDisplayed("lnk_logOut");
			element("lnk_logOut").click();
			isElementDisplayed("lnk_logIn");
			logMessage("ASSERT PASSED : Logout successfully\n");
		} catch (Exception exp) {
			isElementDisplayed("lnk_logIn");
			element("lnk_logIn").click();
			logMessage(
					"STEP : LogIn button is clicked for logout successfully due to issue occured intermitently Bug Id is 9131-6434260 in lnk_logIn\n");
			isElementDisplayed("lnk_logOut");
			element("lnk_logOut").click();
			isElementDisplayed("lnk_logIn");
			logMessage("ASSERT PASSED : Logout successfully\n");
		}
	}

	public void verifyNotLoginSuccessfully() {
		isElementDisplayed("btn_verify");
		logMessage("STEP : Verify button is clicked in btn_verify\n");
	}

	public void preRequisiteForShippingAddress(String userName, String password) {
		wait.waitForPageToLoadCompletely();
		clickLoginButton();
		enterUserName(userName);
		enterPassword(password);
		clickVerifyButton();
		wait.waitForPageToLoadCompletely();
		wait.hardWait(5);
		clickSecondFeatureItem();
		wait.waitForPageToLoadCompletely();
		wait.hardWait(5);
		clickAddToCartButton();
		wait.hardWait(10);
		clickProceedToCheckoutAtBottom();
		wait.waitForPageToLoadCompletely();
		wait.hardWait(5);
		clickShippingAddressRadioButton("Shipping Address");
		wait.hardWait(3);
		clickShippingAddressRadioButton("New Address");
	}

	public void preRequisiteForBillingAddress(String userName, String password) {
		wait.waitForPageToLoadCompletely();
		clickLoginButton();
		enterUserName(userName);
		enterPassword(password);
		clickVerifyButton();
		wait.waitForPageToLoadCompletely();
		wait.hardWait(5);
		clickSecondFeatureItem();
		wait.waitForPageToLoadCompletely();
		wait.hardWait(5);
		clickAddToCartButton();
		wait.hardWait(10);
		clickProceedToCheckoutAtBottom();
		wait.hardWait(5);

	}

	public void clickSecondFeatureItem() {
		isElementDisplayed("lnk_secondFeatureItem");
		clickUsingXpathInJavaScriptExecutor(element("lnk_secondFeatureItem"));
		logMessage("STEP : Second feature link is clicked at home page in lnk_secondFeatureItem\n");

	}

	public void clickAddToCartButton() {
		isElementDisplayed("btn_addToCart");
		clickUsingXpathInJavaScriptExecutor(element("btn_addToCart"));
		logMessage("STEP : Add to cart button is clicked in btn_addToCart\n");

	}

	public void clickProceedToCheckoutAtBottom() {
		isElementDisplayed("btn_proceedToCheckoutTop");
		clickUsingXpathInJavaScriptExecutor(element("btn_proceedToCheckoutTop"));
		logMessage("STEP : Proceed to checkout button is clicked at bottom in btn_proceedToCheckoutTop\n");

	}

	public void clickShippingAddressRadioButton(String shippingAddressRadio) {
		wait.hardWait(2);
		isElementDisplayed("rad_ShippingAdd", shippingAddressRadio);
		element("rad_ShippingAdd", shippingAddressRadio).click();
		logMessage("STEP :  " + shippingAddressRadio + " radio button is clicked at bottom in rad_ShippingAdd\n");

	}

	public void enterValidTextsInShippingAddressFields(String shippingAddressFieldName,
			String shippingAddressFieldValue) {
		wait.hardWait(2);
		isElementDisplayed("inp_shippingAddressFields", shippingAddressFieldName);
		element("inp_shippingAddressFields", shippingAddressFieldName).clear();
		element("inp_shippingAddressFields", shippingAddressFieldName).sendKeys(shippingAddressFieldValue);
		logMessage("STEP : " + shippingAddressFieldValue + " is entered for " + shippingAddressFieldName
				+ " in inp_userName\n");

	}

	public void enterValidTextsInBillingAddressFields(String billingAddressFieldName, String billingAddressFieldValue) {
		wait.hardWait(2);
		isElementDisplayed("inp_billingAddressFields", billingAddressFieldName);
		element("inp_billingAddressFields", billingAddressFieldName).clear();
		element("inp_billingAddressFields", billingAddressFieldName).sendKeys(billingAddressFieldValue);
		logMessage("STEP : " + billingAddressFieldValue + " is entered for " + billingAddressFieldName
				+ " in inp_billingAddressFields\n");
	}

	public void selectValidTextsInShippingAddressFields(String shippingAddressFieldName,
			String shippingAddressFieldValue) {
		isElementDisplayed("list_shipping" + shippingAddressFieldName, shippingAddressFieldName);
		selectProvidedTextFromDropDown(element("list_shipping" + shippingAddressFieldName), shippingAddressFieldValue);
		logMessage("STEP : " + shippingAddressFieldValue + " is entered for list_shipping" + shippingAddressFieldName
				+ " in inp_userName\n");
	}

	public void selectValidTextsInBillingAddressFields(String billingAddressFieldName,
			String billingAddressFieldValue) {
		isElementDisplayed("list_billing" + billingAddressFieldName, billingAddressFieldName);
		selectProvidedTextFromDropDown(element("list_billing" + billingAddressFieldName), billingAddressFieldValue);
		logMessage("STEP : " + billingAddressFieldValue + " is entered for list_billing" + billingAddressFieldName
				+ " in list_billing\n");
	}

	public void clickOnContinue() {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("btn_continue");
		clickUsingXpathInJavaScriptExecutor(element("btn_continue"));
		logMessage("STEP : Continue button is clicked in btn_continue\n");
		wait.waitForPageToLoadCompletely();

	}

	public void FillValidShippingInformation() {
		enterValidTextsInShippingAddressFields("FirstName", FirstName);
		enterValidTextsInShippingAddressFields("LastName", LastName);
		enterValidTextsInShippingAddressFields("MiddleName", MiddleName);
		enterValidTextsInShippingAddressFields("CompanyName", CompanyName);
		enterValidTextsInShippingAddressFields("Department", Department);
		enterValidTextsInShippingAddressFields("Adr_Line1", Adr_Line1);
		enterValidTextsInShippingAddressFields("Adr_Line2", Adr_Line2);
		enterValidTextsInShippingAddressFields("City", City);
		selectValidTextsInShippingAddressFields("State", State);
		enterValidTextsInShippingAddressFields("ZipCode", ZipCode);
		selectValidTextsInShippingAddressFields("Country", Country);
		enterValidTextsInShippingAddressFields("Province", Province);

	}

	public void enterShippingAddress(String fieldName, String fieldValue) {
		enterValidTextsInShippingAddressFields(fieldName, fieldValue);
	}

	public void enterBillingAddress(String fieldName, String fieldValue) {
		enterValidTextsInBillingAddressFields(fieldName, fieldValue);
	}

	public void selectShippingAddress(String fieldName, String fieldValue) {
		selectValidTextsInShippingAddressFields(fieldName, fieldValue);
	}

	public void selectBillingAddress(String fieldName, String fieldValue) {
		selectValidTextsInBillingAddressFields(fieldName, fieldValue);
	}

	public void appAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToPaymentInfoPage() {
		clickOnContinue();
		asmErrorPage = new ASMErrorPage(driver);
		asmErrorPage.verifyASMErrorNotPresent(YamlReader.getYamlValue("ASM_URLRejectedErrorMsz"));
		navigateToBackPage();

	}

	public void verifyPaymentInformationPageIsPresent() {
		isElementDisplayed("hd_storeHeading");
		logMessage("ASSERT PASSED : Payment Information page is verified in hd_storeHeading\n");
	}

	public void clickBillingAddressRadioButton(String billingAddressRadio) {
		wait.hardWait(5);
		isElementDisplayed("rad_billingAdd", billingAddressRadio);
		clickUsingXpathInJavaScriptExecutor(element("rad_billingAdd", billingAddressRadio));
		logMessage("STEP :  " + billingAddressRadio + " radio button is clicked at bottom in rad_billingAdd\n");
	}

	public void clickOnNewAddressInBillingAddress() {
		clickBillingAddressRadioButton("New Address");
	}

	public void FillValidBillingInformation() {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(5);
		enterValidTextsInBillingAddressFields("FirstName", FirstName);
		enterValidTextsInBillingAddressFields("LastName", LastName);
		enterValidTextsInBillingAddressFields("MiddleName", MiddleName);
		enterValidTextsInBillingAddressFields("CompanyName", CompanyName);
		enterValidTextsInBillingAddressFields("Department", Department);
		enterValidTextsInBillingAddressFields("Adr_Line1", Adr_Line1);
		enterValidTextsInBillingAddressFields("Adr_Line2", Adr_Line2);
		enterValidTextsInBillingAddressFields("City", City);
		selectValidTextsInBillingAddressFields("State", State);
		enterValidTextsInBillingAddressFields("ZipCode", ZipCode);
		selectValidTextsInBillingAddressFields("Country", Country);
		enterValidTextsInBillingAddressFields("Province", Province);
	}

	public void enterPhone_Email(String phone_email) {
		isElementDisplayed("inp_phone_email", phone_email);
		element("inp_phone_email", phone_email).clear();
		element("inp_phone_email", phone_email).sendKeys(getStoreSmokeValue.getASM_StoreShippingAddress(phone_email));
		logMessage("STEP : " + getStoreSmokeValue.getASM_StoreShippingAddress(phone_email)
				+ "  is entered in inp_phone_email\n");
	}

	public void checkAddNewShippingPhoneNumber() {
		isElementDisplayed("chk_newPhone");
		clickUsingXpathInJavaScriptExecutor(element("chk_newPhone"));
		wait.waitForElementToDisappear(element("list_phone"));
	}

	public void enterAdditionalContactInformation() {
		checkAddNewShippingPhoneNumber();
		enterPhone_Email("Phone");
		enterPhone_Email("Email");
	}

	public void enterEmail_Phone(String email_phone, String email_phoneValue) {
		isElementDisplayed("inp_phone_email", email_phone);
		element("inp_phone_email", email_phone).clear();
		element("inp_phone_email", email_phone).sendKeys(email_phoneValue);
		logMessage("STEP : " + email_phoneValue + "  is entered in inp_phone_email\n");
	}

	public void enterPaymentInfo(String infoName, String infoValue) {
		isElementDisplayed("inp_paymentInfo", infoName);
		//element("inp_paymentInfo", infoName).clear();
		element("inp_paymentInfo", infoName).sendKeys(infoValue);
		logMessage("STEP : " + infoValue + "  is entered in inp_paymentInfo\n");
	}

	public void selectPaymentInfo(String infoName, String infoValue) {
		wait.hardWait(3);
		selectProvidedTextFromDropDown(element("list_paymentInfo", infoName), infoValue);
	}

	public void enterPaymentInformation() {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(5);
		selectPaymentInfo("CardType", CreditCardType);
		enterPaymentInfo("CardHolderName", CardHolderName);
		enterPaymentInfo("CCNumber", CreditCardNumber);
		selectPaymentInfo("Expiration", CreditCardExpiration);
		enterPaymentInfo("CVV", CreditCardVerificationNumber);
	}

	public void verifySecureCheckoutPageIsPresent() {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(8);
		isElementDisplayed("hd_secureCheckout");
		logMessage("ASSERT PASSED : Secure Checkout page is verified in hd_secureCheckout\n");
	}

	public void verifyApplicationAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToSecureCheckoutPage() {
		clickOnContinue();
		asmErrorPage = new ASMErrorPage(driver);
		asmErrorPage.verifyASMErrorNotPresent(YamlReader.getYamlValue("ASM_URLRejectedErrorMsz"));
		verifySecureCheckoutPageIsPresent();
		navigateToBackPage();
	}

	public void NavigateToShoppingCartPage(String userName, String password) {
		clickOnSearchedProductLink();
		verifyQuantityIsPrepopulatedFeild("1");
		wait.waitForPageToLoadCompletely();
		clickAddToCartButton();
		enterUserName(userName);
		enterPassword(password);
		clickVerifyButton();

	}

	public void verifyQuantityIsPrepopulatedFeild(String quantity) {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(5);
		isElementDisplayed("txt_box_Quantity");
		Assert.assertEquals(element("txt_box_Quantity").getAttribute("value"), (quantity));
		logMessage("ASSERT PASSED : Quantity is prepolulated feild with value 1\n");
		map.put("quantity", element("txt_box_Quantity").getAttribute("value"));
		logMessage("STEP : Quantity value stored as 1\n");
	}

	public void verifyUserIsOnShoppingCartPage() {
		wait.waitForPageToLoadCompletely();
		Assert.assertEquals(element("txt_shoppingCart").getText(), ("Shopping Cart"),
				"Shopping Cart page is not Displayed");
		logMessage("ASSERT PASSED : Shopping cart page displayed\n");
	}

	public void verifyThankyouMessageAfterOrderCompletion(String waitTime, String printReceiptMessage) {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		wait.hardWait(2);
		isElementDisplayed("msg_thankyou");
		Assert.assertEquals(element("msg_thankyou").getText().trim(), ("Thank you, your order is complete!"));
		logMessage("ASSERT PASSED : Thank you message verified as " + element("msg_thankyou").getText().trim() + "\n");
		try {
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("lnk_print_receipt");
			Assert.assertEquals(element("lnk_print_receipt").getText().trim(), (printReceiptMessage));
			logMessage(
					"ASSERT PASSED : Link print receipt is shown as " + element("lnk_print_receipt").getText() + "\n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (NoSuchElementException e) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			int count = (Integer.parseInt(waitTime)) / 5;
			for (int i = 1; i <= count; i++) {
				holdScriptExecutionToVerifyPreviewStatus();
				pageRefresh();
				isElementDisplayed("lnk_print_receipt");
				if (element("lnk_print_receipt").getText().trim().equalsIgnoreCase(printReceiptMessage)) {
					logMessage("AASERT PASSED : Link print message " + printReceiptMessage + " is verified\n");
					break;
				}
			}
		}
	}

	public String getInvoiceNumberFromPrintReceipt() {
		String invoice_number = null;
		if (!isBrowser("firefox")) {
			logMessage("STEP : Script could not get invoice number from PDF on CHROME and IE browsers\n");
		} else {
			isElementDisplayed("lnk_print_receipt");
			logMessage("STEP : Click print recepit link");
			element("lnk_print_receipt").click();
			String getWindow = driver.getWindowHandle();
			switchWindow();
			wait.waitForPageToLoadCompletely();
			wait.hardWait(3);
			isElementDisplayed("invoice_number");
			invoice_number = element("invoice_number").getText();
			driver.close();
			driver.switchTo().window(getWindow);
			logMessage("STEP : Current invoice number is " + invoice_number + "\n");
		}
		return invoice_number;

	}

	public Map<String, String> verifyBillingAndShippingAddress(String caseId) {
		hardWaitForIEBrowser(10);
		verifyMemnberDetails("First Name");
		verifyMemnberDetails("Last Name");

		logMessage("ASSERT PASSED : Billing address successfully verified as "
				+ element("txt_mailing_address").getText().trim() + "\n");
		return map;
	}

	private void verifyMemnberAddress(String caseId, String name) {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("txt_mailing_address");
		String mailing_address = element("txt_mailing_address").getText().trim();
		mailing_address = mailing_address.replace("Road", "Rd").replace("Kentucky", "KY");
		Assert.assertTrue(
				mailing_address.contains(
						getACS_Store_SheetValue(caseId, name).replace("Road", "Rd").replace("Kentucky", "KY").trim()),
				"Expected is "
						+ getACS_Store_SheetValue(caseId, name).replace("Road", "Rd").replace("Kentucky", "KY").trim()
						+ "\n but found " + mailing_address);
		logMessage("ASSERT PASSED : " + name + " in mailing address successfully verified as "
				+ getACS_Store_SheetValue(caseId, name) + "\n");
	}

	private void verifyMemnberDetails(String name) {
		isElementDisplayed("txt_mailing_address");
		String mailing_address = element("txt_mailing_address").getText().trim();
		Assert.assertTrue(mailing_address.contains(map2.get(name)),
				"ASSERT FAILED: Expected is " + map2.get(name) + " but found " + mailing_address);
		logMessage("ASSERT PASSED : " + name + " in mailing address successfully verified as " + map2.get(name) + "\n");
	}

	public Map<String, String> verifyProductDetailsOnShoppingCartPage(String caseId, String priceValue, String discount,
			String description) {
		verifyProductDetails("priceValue", priceValue);
		verifyProductDetails("discount", discount);
		verifyProductDetails("description", description);
		double totalPriceOfProduct = Double.parseDouble(priceValue.replace("$", "").trim())
				* Integer.parseInt(element("txt_quantity").getAttribute("value"))
				- Double.parseDouble(discount.replace("$", "").trim());
		Assert.assertTrue(getProductDetails("total") == totalPriceOfProduct,
				"ASSERT FAILED: Expected value is " + totalPriceOfProduct + " but found " + getProductDetails("total"));
		logMessage("ASSERT PASSED : Total price with discount is verified as " + totalPriceOfProduct + "\n");
		return map;
	}

	public String getOrderTotalSummaryAtCheckoutPage() {

		double ordertotal = getStoreSummaryValues("Subtotal") + getStoreSummaryValues("Shipping")
				+ getStoreSummaryValues("Tax");
		double roundOffOrderTotal = Math.round(ordertotal * 100.0) / 100.0;
		Assert.assertTrue(getStoreSummaryValues("Order Total") == roundOffOrderTotal,
				"ASSERT FAILED: Expected value is " + roundOffOrderTotal + " but found "
						+ getStoreSummaryValues("Order Total"));
		logMessage("ASSERT PASSED : Order total is verified as " + roundOffOrderTotal + "\n");
		return Double.toString(ordertotal);
	}

	private void verifyProductDetails(String productName, String productvalues) {
		Assert.assertTrue(
				element("txt_" + productName).getText().replace("$", "").trim()
						.equalsIgnoreCase(productvalues.replace("$", "").trim()),
				"ASSERT FAILED: Expected value is " + productvalues.replace("$", "") + " but found "
						+ element("txt_" + productName).getText().replace("$", ""));
		logMessage("ASSERT PASSED : Store " + productName + " is successfully verified as "
				+ element("txt_" + productName).getText().trim() + "\n");
		map.put(productName, element("txt_" + productName).getText().replace("$", "").trim());
	}

	private double getProductDetails(String detail) {
		isElementDisplayed("txt_" + detail);
		map.put(detail, element("txt_" + detail).getText().replace("$", "").trim());
		return Double.parseDouble(element("txt_" + detail).getText().replace("$", "").trim());
	}

	private double getStoreSummaryValues(String storeName) {
		// wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		// isElementDisplayed("txt_"+storeName);
		map.put(storeName, element("txt_store_Summary", storeName).getText().trim());
		return Double.parseDouble(element("txt_store_Summary", storeName).getText().trim());
	}

	public void clickOnSearchedProductLink() {
		isElementDisplayed("lnk_searched_product");
		map.put("Product Name", element("lnk_searched_product").getText().trim());
		logMessage("STEP : Searched product is displayed as " + map.get("Product Name") + "\n");
		element("lnk_searched_product").click();
		logMessage("STEP : Searched product is clicked\n");

	}

	private void storeOrderSummaryValues(String name) {
		isElementDisplayed("txt_" + name);
		map.put(name, element("txt_" + name).getText().replace("$", "").trim());
		logMessage("STEP : Get value of " + name + " as " + map.get(name) + "\n");
	}

	public void verifyOrderSummaryAtCheckoutPage(Map<String, String> mapACSstore, String orderTotal) {
		verifyProductDetailsFromMappedValues("priceValue", mapACSstore.get("priceValue?"));
		verifyProductDetailsFromMappedValues("description", mapACSstore.get("description?"));
		verifyProductDetailsFromMappedValues("quantity", mapACSstore.get("quantity?"));
		verifyProductDetailsFromMappedValues("discount", mapACSstore.get("discount?"));
		getShippingSummaryDetailsFromMappedValues("Shipping", mapACSstore.get("Shipping?"));
		// getShippingSummaryDetailsFromMappedValues("Subtotal",mapGetMemberDetailsInStore);
		getShippingSummaryDetailsFromMappedValues("Tax", mapACSstore.get("Tax?"));
		getShippingSummaryDetailsFromMappedValues("Order Total", orderTotal);

	}

	public <map> Map<String, String> getPrepopulatedShippingAddressFeildsAndContinue() {
		hardWaitForIEBrowser(7);
		getPrepopulatedShippingAddressValues("First Name");
		getPrepopulatedShippingAddressValues("Last Name");
		getPrepopulatedShippingAddressValues("Email");
		map2.put("Country", element("inp_dropdown", "Country").getAttribute("value"));
		map2.put("Phone", element("inp_dropdown", "Phone").getAttribute("value"));
		return map2;

	}

	public void enterPaymentInformation_ACSStore(String CardType, String HolderName, String CCNumber, String Expiration,
			String CVV) {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(8);
		selectPaymentInfo("CardType", CardType);
		enterPaymentInfo("CardHolderName", HolderName);
		enterPaymentInfo("CCNumber", CCNumber);
		selectPaymentInfo("Expiration", Expiration);
		enterPaymentInfo("CVV", CVV);
	}

	public void enterPaymentInformation_ACSStoreForAllPaymentTypes(String PaymentMethod, String cardNumber,
			String dinerscardNumber, String referenceNumber, String discovercardNumber, String AMEXcardNumber,
			String expireDate, String cvvNumber, String checkNumber) {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(8);
		selectPaymentInfo("CardType", PaymentMethod);

		switch (PaymentMethod) {
		case "Visa/MC":
			enterPaymentInfo("CCNumber", cardNumber);
			break;

		case "BOA - Check":
			enterPaymentInfo("CCNumber", checkNumber);
			break;

		case "check":
			enterPaymentInfo("CCNumber", checkNumber);
			break;

		case "cash":
			enterPaymentInfo("CCNumber", referenceNumber);
			break;

		case "Diners":
			enterPaymentInfo("CCNumber", dinerscardNumber);
			break;

		case "Discover":
			enterPaymentInfo("CCNumber", discovercardNumber);
			break;
		case "AMEX":
			enterPaymentInfo("CCNumber", AMEXcardNumber);
			break;

		}
		selectPaymentInfo("Expiration", expireDate);
		enterPaymentInfo("CVV", cvvNumber);
	}

	private void getPrepopulatedShippingAddressValues(String name) {
		isElementDisplayed("inp_shipping_adress", name);
		map2.put(name, element("inp_shipping_adress", name).getAttribute("value"));
		logMessage("STEP : Get prepopulated " + name + " value as " + map2.get(name) + "\n");
	}

	public void verifyApplicationAcceptsDataAndNavigatesToSecureCheckoutPage() {
		clickOnContinue();
		verifySecureCheckoutPageIsPresent();
	}

	private void getShippingSummaryDetailsFromMappedValues(String name, String sheetvalue) {
		isElementDisplayed("txt_order_shipping_summary", name);
		wait.hardWait(2);
		double sheetvaluefloat = Double.parseDouble(sheetvalue);
		sheetvaluefloat = Math.round(sheetvaluefloat * 100.0) / 100.0;
		double actualvalue = Double.parseDouble(element("txt_order_shipping_summary", name).getText());
		Assert.assertTrue(actualvalue == sheetvaluefloat,
				"ASSERT FAILED: Expected value is " + sheetvaluefloat + " but found "+actualvalue);
		map.put(name, element("txt_order_shipping_summary", name).getText());
		logMessage("ASSERT PASSED : Product summary details for " + name + " verified successfully as "
				+ element("txt_order_shipping_summary", name).getText() + "\n");
	}

	public void clickPlaceYourOrder() {
		isElementDisplayed("btn_placeOrder");
		logMessage("STEP : Click place your order button\n");
		clickUsingXpathInJavaScriptExecutor(element("btn_placeOrder"));
		// element("btn_placeOrder").click();
		logMessage("STEP : Place your order button clicked\n");
	}

	private void verifyProductDetailsFromMappedValues(String name, String value) {
		hardWaitForIEBrowser(2);
		isElementDisplayed("txt_summary_" + name);
		Assert.assertEquals(value.replace("$", "").trim(),(element("txt_summary_" + name).getText().replace("$", "").trim()));
		logMessage("ASSERT PASSED : Product details for " + name + " verified successfully as "
				+ element("txt_summary_" + name).getText() + "\n");
	}

	public void holdScriptExecutionToVerifyPreviewStatus() {
		logMessage("===== Automation script is on hold for 5 minutes to verify preview status =====\n");
		String lapsedMinutes = "";
		for (int minutes = 1; minutes <= 5; minutes++) {
			for (int i = 0; i <= 59; i++) {

				wait.hardWait(1);
			}
			lapsedMinutes = String.valueOf(minutes) + " min : ";
		}

	}

	protected void selectProvidedTextFromDropDown(WebElement el, String text) {
		wait.waitForElementToBeVisible(el);
		scrollDown(el);

		Select sel = new Select(el);
		try {
			sel.selectByVisibleText(text);
		} catch (StaleElementReferenceException ex1) {
			sel.selectByVisibleText(text);
			logMessage("Select Element " + el + " after catching Stale Element Exception");
		} catch (Exception ex2) {
			logMessage("Element " + el + " could not be clicked! " + ex2.getMessage());
		}
	}

	public void selectAndEnterNewShippingAddress(String FirstName, String LastName, String AdrLine1, String City,
			String State, String ZipCode) {

		clickNewShippingAddressButton();
		enterValidTextsInShippingAddressFields("FirstName", FirstName);
		enterValidTextsInShippingAddressFields("LastName", LastName);
		enterValidTextsInShippingAddressFields("Adr_Line1", AdrLine1);
		enterValidTextsInShippingAddressFields("City", City);
		selectState(element("list_shippingState"));
		enterValidTextsInShippingAddressFields("ZipCode", ZipCode);
		clickOnContinue();
	}

	public void selectAndEnterNewBillingAddress(String FirstName, String LastName, String AdrLine1, String City,
			String State, String ZipCode) {
		clickNewBillingAddressButton();
		enterValidTextsInBillingAddressFields("FirstName", FirstName);
		enterValidTextsInBillingAddressFields("LastName", LastName);
		enterValidTextsInBillingAddressFields("Adr_Line1", AdrLine1);
		enterValidTextsInBillingAddressFields("City", City);
		selectState(element("list_billingState"));
		enterValidTextsInBillingAddressFields("ZipCode", ZipCode);

	}

	private void clickNewBillingAddressButton() {
		element("btn_newBill").click();
		logMessage("STEP : Click new billing Address button\n");
	}

	private void clickNewShippingAddressButton() {
		clickUsingXpathInJavaScriptExecutor(element("btn_newShip"));
		// element("btn_newShip").click();
		logMessage("STEP : Click new Shipping Address button\n");

	}

	public void selectState(WebElement el) {
		Select dropdown = new Select(el);
		dropdown.selectByValue("KY");
		logMessage("STEP : State is entered as Kentucky\n");
	}

	public void enterPaymentInformation_OMAForAllPaymentTypes(String PaymentMethod, String cardNumber,
			String dinerscardNumber, String referenceNumber, String discovercardNumber, String AMEXcardNumber,
			String expireMonth, String cvvNumber, String checkNumber, String expireYear) {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(8);
		selectPaymentInfo("CreditCardType", PaymentMethod);
		System.out.println(cardNumber);
		switch (PaymentMethod) {
		case "Visa/MC":
			enterPaymentInfo("CreditCardNumber", cardNumber);
			break;

		case "BOA - Check":
			enterPaymentInfo("CreditCardNumber", checkNumber);
			break;

		case "check":
			enterPaymentInfo("CreditCardNumber", checkNumber);
			break;

		case "cash":
			enterPaymentInfo("CreditCardNumber", referenceNumber);
			break;

		case "Diners":
			enterPaymentInfo("CreditCardNumber", dinerscardNumber);
			break;

		case "Discover":
			enterPaymentInfo("CreditCardNumber", discovercardNumber);
			break;
		case "AMEX":
			enterPaymentInfo("CreditCardNumber", AMEXcardNumber);
			break;

		}
		wait.hardWait(4);
		selectPaymentInfo("ExpirationMonth", expireMonth);
		wait.hardWait(4);
		selectPaymentInfo("ExpirationYear", expireYear);
		enterPaymentInfo("CcvNumber", cvvNumber);
		
	}

}
