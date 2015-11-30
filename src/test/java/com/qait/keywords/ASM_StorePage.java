package com.qait.keywords;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.YamlReader;

public class ASM_StorePage extends GetPage {
	WebDriver driver;
	String pagename = "ASM_StorePage";
	 Map<String,String> map = new HashMap<String,String>();
	Map<String, Object> mapStoreSmoke = YamlReader
			.getYamlValues("ASM_StoreSmokeChecklist_Data");
	YamlInformationProvider getStoreSmokeValue = new YamlInformationProvider(
			mapStoreSmoke);
	ASMErrorPage asmErrorPage;
	String FirstName = getStoreSmokeValue.getASM_StoreShippingAddress("FName");
	String LastName = getStoreSmokeValue.getASM_StoreShippingAddress("LName");
	String MiddleName = getStoreSmokeValue.getASM_StoreShippingAddress("MName");
	String CompanyName = getStoreSmokeValue
			.getASM_StoreShippingAddress("Company");
	String Department = getStoreSmokeValue
			.getASM_StoreShippingAddress("Department");
	String Adr_Line1 = getStoreSmokeValue
			.getASM_StoreShippingAddress("Address");
	String Adr_Line2 = getStoreSmokeValue
			.getASM_StoreShippingAddress("Address2");
	String City = getStoreSmokeValue.getASM_StoreShippingAddress("City");
	String State = getStoreSmokeValue.getASM_StoreShippingAddress("State");
	String ZipCode = getStoreSmokeValue.getASM_StoreShippingAddress("ZipCode");
	String Country = getStoreSmokeValue.getASM_StoreShippingAddress("Country");
	String Province = getStoreSmokeValue
			.getASM_StoreShippingAddress("IntlProvince");
	String CardHolderName = getStoreSmokeValue
			.getASM_StorePaymentInfo("CardHolderName");
	String CreditCardNumber = getStoreSmokeValue
			.getASM_StorePaymentInfo("CreditCardNumber");
	String CreditCardVerificationNumber = getStoreSmokeValue
			.getASM_StorePaymentInfo("CreditCardVerificationNumber");
	String CreditCardType = getStoreSmokeValue
			.getASM_StorePaymentInfo("CreditCardType");
	String CreditCardExpiration = getStoreSmokeValue
			.getASM_StorePaymentInfo("CreditCardExpiration");

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
		logMessage("Step : " + searchValue + " is entered in inp_search\n");
	}

	public void clickSearchButton() {
		isElementDisplayed("btn_search");
		element("btn_search").click();
		logMessage("Step : search button is clicked in btn_search\n");
	}

	public void verifySearchSuccessfully() {
		isElementDisplayed("txt_searchList");
		verifyElementTextContains("txt_searchList", "Search Results");
		logMessage("Step : verify search successfully in txt_searchList\n");
	}

	public void clickLoginButton() {
		isElementDisplayed("lnk_logIn");
		clickUsingXpathInJavaScriptExecutor(element("lnk_logIn"));
		// element("lnk_logIn").click();
		logMessage("Step : logIn button is clicked in lnk_logIn\n");
	}

	public void enterUserName(String userName) {
		try {
			isElementDisplayed("inp_userName");
			element("inp_userName").clear();
			element("inp_userName").sendKeys(userName);
			logMessage("Step : " + userName + " is entered in inp_userName\n");
		} catch (StaleElementReferenceException stlExp) {
			isElementDisplayed("inp_userName");
			element("inp_userName").clear();
			element("inp_userName").sendKeys(userName);
			logMessage("Step : " + userName + " is entered in inp_userName\n");
		}

	}

	public void enterPassword(String password) {
		isElementDisplayed("inp_password");
		element("inp_password").clear();
		element("inp_password").sendKeys(password);
		logMessage("Step : " + password + " is entered in inp_userName\n");
	}

	public void clickVerifyButton() {
		isElementDisplayed("btn_verify");
		element("btn_verify").click();
		logMessage("Step : Verify button is clicked in btn_verify\n");
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
			logMessage("ASSERT PASSED : logout successfully\n");
		} catch (Exception exp) {
			isElementDisplayed("lnk_logIn");
			element("lnk_logIn").click();
			logMessage("Step : logIn button is clicked for logout successfully due to issue occured intermitently Bug Id is 9131-6434260 in lnk_logIn\n");
			isElementDisplayed("lnk_logOut");
			element("lnk_logOut").click();
			isElementDisplayed("lnk_logIn");
			logMessage("ASSERT PASSED : logout successfully\n");
		}
	}

	public void verifyNotLoginSuccessfully() {
		isElementDisplayed("btn_verify");
		logMessage("Step : Verify button is clicked in btn_verify\n");
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
		// wait.waitForPageToLoadCompletely();
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
		// wait.waitForPageToLoadCompletely();
		wait.hardWait(10);
		clickProceedToCheckoutAtBottom();
		wait.hardWait(5);
		// clickShippingAddressRadioButton("Shipping Address");
		// clickShippingAddressRadioButton("New Address");

	}

	public void clickSecondFeatureItem() {
		isElementDisplayed("lnk_secondFeatureItem");
		clickUsingXpathInJavaScriptExecutor(element("lnk_secondFeatureItem"));
		// element("lnk_secondFeatureItem").click();
		logMessage("Step : second feature link is clicked at home page in lnk_secondFeatureItem\n");

	}

	public void clickAddToCartButton() {
		isElementDisplayed("btn_addToCart");
		clickUsingXpathInJavaScriptExecutor(element("btn_addToCart"));
	// element("btn_addToCart").click();
		logMessage("Step : Add to cart button is clicked in btn_addToCart\n");

	}

	public void clickProceedToCheckoutAtBottom() {
		isElementDisplayed("btn_proceedToCheckoutTop");
		clickUsingXpathInJavaScriptExecutor(element("btn_proceedToCheckoutTop"));
		// element("btn_proceedToCheckoutTop").click();
		logMessage("Step : proceed to checkout button is clicked at bottom in btn_proceedToCheckoutTop\n");

	}

	public void clickShippingAddressRadioButton(String shippingAddressRadio) {
		wait.hardWait(2);
		isElementDisplayed("rad_ShippingAdd", shippingAddressRadio);
		// clickUsingXpathInJavaScriptExecutor(element("rad_ShippingAdd",
		// shippingAddressRadio));
		element("rad_ShippingAdd", shippingAddressRadio).click();
		logMessage("Step :  " + shippingAddressRadio
				+ " radio button is clicked at bottom in rad_ShippingAdd\n");

	}

	public void enterValidTextsInShippingAddressFields(
			String shippingAddressFieldName, String shippingAddressFieldValue) {
		wait.hardWait(2);
		isElementDisplayed("inp_shippingAddressFields",
				shippingAddressFieldName);
		element("inp_shippingAddressFields", shippingAddressFieldName).clear();
		element("inp_shippingAddressFields", shippingAddressFieldName)
				.sendKeys(shippingAddressFieldValue);
		logMessage("Step : " + shippingAddressFieldValue + " is entered for "
				+ shippingAddressFieldName + " in inp_userName\n");

	}

	public void enterValidTextsInBillingAddressFields(
			String billingAddressFieldName, String billingAddressFieldValue) {
		wait.hardWait(2);
		isElementDisplayed("inp_billingAddressFields", billingAddressFieldName);
		element("inp_billingAddressFields", billingAddressFieldName).clear();
		element("inp_billingAddressFields", billingAddressFieldName).sendKeys(
				billingAddressFieldValue);
		logMessage("Step : " + billingAddressFieldValue + " is entered for "
				+ billingAddressFieldName + " in inp_billingAddressFields\n");
	}

	public void selectValidTextsInShippingAddressFields(
			String shippingAddressFieldName, String shippingAddressFieldValue) {
		isElementDisplayed("list_shipping" + shippingAddressFieldName,
				shippingAddressFieldName);
		selectProvidedTextFromDropDown(element("list_shipping"
				+ shippingAddressFieldName), shippingAddressFieldValue);
		logMessage("Step : " + shippingAddressFieldValue
				+ " is entered for list_shipping" + shippingAddressFieldName
				+ " in inp_userName\n");
	}

	public void selectValidTextsInBillingAddressFields(
			String billingAddressFieldName, String billingAddressFieldValue) {
		isElementDisplayed("list_billing" + billingAddressFieldName,
				billingAddressFieldName);
		selectProvidedTextFromDropDown(element("list_billing"
				+ billingAddressFieldName), billingAddressFieldValue);
		logMessage("Step : " + billingAddressFieldValue
				+ " is entered for list_billing" + billingAddressFieldName
				+ " in list_billing\n");
	}

	public void clickOnContinue() {
		wait.waitForPageToLoadCompletely();
		holdExecution(5000);
		isElementDisplayed("btn_continue");
		clickUsingXpathInJavaScriptExecutor(element("btn_continue"));
		logMessage("Step : continue button is clicked in btn_continue\n");
		wait.waitForPageToLoadCompletely();
		holdExecution(9000);
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
		asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
		// verifyPaymentInformationPageIsPresent();
		navigateToBackPage();

	}

	public void verifyPaymentInformationPageIsPresent() {
		isElementDisplayed("hd_storeHeading");
		logMessage("ASSERT PASSED : Payment Information page is verified in hd_storeHeading\n");
	}

	public void clickBillingAddressRadioButton(String billingAddressRadio) {
		wait.hardWait(5);
		isElementDisplayed("rad_billingAdd", billingAddressRadio);
		clickUsingXpathInJavaScriptExecutor(element("rad_billingAdd",
				billingAddressRadio));
		// element("rad_billingAdd", billingAddressRadio).click();
		logMessage("Step :  " + billingAddressRadio
				+ " radio button is clicked at bottom in rad_billingAdd\n");
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
		element("inp_phone_email", phone_email).sendKeys(
				getStoreSmokeValue.getASM_StoreShippingAddress(phone_email));
		logMessage("Step : "
				+ getStoreSmokeValue.getASM_StoreShippingAddress(phone_email)
				+ "  is entered in inp_phone_email\n");
	}

	public void checkAddNewShippingPhoneNumber() {
		isElementDisplayed("chk_newPhone");
		clickUsingXpathInJavaScriptExecutor(element("chk_newPhone"));
		// element("chk_newPhone").click();
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
		logMessage("Step : " + email_phoneValue
				+ "  is entered in inp_phone_email\n");
	}

	public void enterPaymentInfo(String infoName, String infoValue) {
		isElementDisplayed("inp_paymentInfo", infoName);
		element("inp_paymentInfo", infoName).clear();
		element("inp_paymentInfo", infoName).sendKeys(infoValue);
		logMessage("Step : " + infoValue + "  is entered in inp_paymentInfo\n");
	}

	public void selectPaymentInfo(String infoName, String infoValue) {
		wait.hardWait(3);
		selectProvidedTextFromDropDown(element("list_paymentInfo", infoName),
				infoValue);
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
	
	public void enterPaymentInformation_ACSSTore(String CardType,String HolderName,String CCNumber,String Expiration,String CVV) {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(5);
		selectPaymentInfo("CardType",CardType);
		enterPaymentInfo("CardHolderName",HolderName);
		enterPaymentInfo("CCNumber",CCNumber);
		selectPaymentInfo("Expiration",Expiration);
		enterPaymentInfo("CVV",CVV);
		
	}

	public void verifySecureCheckoutPageIsPresent() {
		isElementDisplayed("hd_secureCheckout");
		logMessage("ASSERT PASSED : Secure Checkout page is verified in hd_secureCheckout\n");
	}

	public void verifyApplicationAcceptsDataAndNavigatesToSecureCheckoutPage() {
		clickOnContinue();
		verifySecureCheckoutPageIsPresent();
	}
	
	public void getPriceValuesOfMembersAndNonMembers()
	{
		isElementDisplayed("txt_price");
	    map.put("Price", elements("txt_price").get(0).getText().replace("$", "").trim());
	    map.put("Members Pay",elements("txt_price").get(1).getText().replace("$", "").trim());
		logMessage("Step : Price Values for Member is "+map.get("Members Pay")+" And Non Member is :"+map.get("Price"));
	}

	public void clickOnSearchedProductLink() {
     isElementDisplayed("lnk_searched_product");
     map.put("Product Name", element("lnk_searched_product").getText().trim());
     element("lnk_searched_product").click();
     logMessage("Searched Product is Displayed as :"+map.get("Product Name"));
		
	}
	
	public void verifyQuantityIsPrepopulatedFeild(String quantity)
	{
		isElementDisplayed("txt_box_Quantity");
		element("txt_box_Quantity").getAttribute("value").equals(quantity);
		map.put("quantity",element("txt_box_Quantity").getAttribute("value") );
	}

	public void verifyUserIsOnShoppingCartPage() {
	 Assert.assertTrue(element("txt_shoppingCart").getText().equals("Shopping Cart"),"Shopping Cart page is not Displayed");
	}

	public void NavigateToShoppingCartPage(String userName, String password) {
		getPriceValuesOfMembersAndNonMembers();
		clickOnSearchedProductLink();
		verifyQuantityIsPrepopulatedFeild("1");
		wait.waitForPageToLoadCompletely();
		clickAddToCartButton();
		enterUserName(userName);
		enterPassword(password);
		clickVerifyButton();
		
	}

	public void verifyProductDetailsOnShoppingCartPage() {
	
		double totalpriceofproduct = getProductDetails("Price") * Integer.parseInt(element("txt_quantity").getAttribute("value")) - getProductDetails("discount");
		System.out.println(totalpriceofproduct);
		Assert.assertTrue(getProductDetails("total")==totalpriceofproduct);
		double ordertotal=getStoreSummaryValues("Subtotal")+ getStoreSummaryValues("Shipping")
				+getStoreSummaryValues("Tax");
		System.out.println(ordertotal);
		Assert.assertTrue(getStoreSummaryValues("Order Total")==ordertotal);
		storeProductDetailsInMap();
		
	}
	
	private void storeProductDetailsInMap()
	{
		storeOrderSummaryValues("Price");
		storeOrderSummaryValues("discount");
		storeOrderSummaryValues("total");
		StoreOrderShippingDetailsValues("Shipping");
		StoreOrderShippingDetailsValues("Subtotal");
		StoreOrderShippingDetailsValues("Tax");
		StoreOrderShippingDetailsValues("Order Total");
	}
	
	private double getStoreSummaryValues(String storeName)
	{
		return Double.parseDouble(element("txt_store_Summary",storeName).getText());
	}

	private double getProductDetails(String string) {
	
		return Double.parseDouble(element("txt_"+string).getText().replace("$", "").trim());
	}
	
	private void getPrepopulatedShippingAddressValues(String name)
	{
		map.put(name,element("inp_shipping_adress",name).getAttribute("value"));
	}
	
	private void storeOrderSummaryValues(String name)
	{	
			map.put(name,element("txt_"+name).getText().replace("$", "").trim());	
	}
	
	private void StoreOrderShippingDetailsValues(String storeName)
	{
		map.put(storeName,element("txt_store_Summary",storeName).getText());
	}
	
	
public void printMap()
{
	for (Map.Entry<String, String> e : map.entrySet()) {
	
	    System.out.println("key.."+e.getKey()+"..value..."+e.getValue());
	}
}

public void getPrepopulatedShippingAddressFeilds() {
	getPrepopulatedShippingAddressValues("First Name");
	getPrepopulatedShippingAddressValues("Last Name");
	getPrepopulatedShippingAddressValues("Address");
	getPrepopulatedShippingAddressValues("City");
	getPrepopulatedShippingAddressValues("Zip Code");
	map.put("Country",element("inp_country").getText());
	printMap();
	
}

public void verifyOrderSummaryAtCheckoutPage() {

	verifyProductDetailsFromMappedValues("Price");
	verifyProductDetailsFromMappedValues("quantity");
	verifyProductDetailsFromMappedValues("discount");
	verifyProductDetailsFromMappedValues("total");
	getShippingSummaryDetailsFromMappedValues("Shipping");
	getShippingSummaryDetailsFromMappedValues("Subtotal");
	getShippingSummaryDetailsFromMappedValues("Tax");
	getShippingSummaryDetailsFromMappedValues("Order Total");

}

private void verifyProductDetailsFromMappedValues(String name) {
	if(name.equals("quantity"))
	{
		System.out.println(element("txt_summary_"+name).getText());
		System.out.println("map value.."+map.get(name));
		Assert.assertTrue(map.get(name).equals(element("txt_summary_"+name).getText().trim()));
	
	}
	else
	{
		System.out.println(element("txt_summary_"+name).getText().replace("$", "").trim());
		System.out.println("map value.."+map.get(name));
	Assert.assertTrue(map.get(name).equals(element("txt_summary_"+name).getText().replace("$", "").trim()));
	
	}
}
private void getShippingSummaryDetailsFromMappedValues(String name)
{
	Assert.assertTrue(map.get(name).equals(element("txt_order_shipping_summary",name).getText()));
	System.out.println(element("txt_order_shipping_summary",name).getText());
	System.out.println(map.get(name));
}

public void clickPlaceYourOrder()
{
	element("btn_placeOrder").click();
	logMessage("ASSERT PASS:: Place Your Order Button Clicked");
}

public void verifyThankyouMessageAfterOrderCompletion() {
	isElementDisplayed("msg_thankyou");
	System.out.println(element("msg_thankyou").getText().trim());
	Assert.assertTrue(element("msg_thankyou").getText().trim().equals("Thank you, your order is complete!"));
	isElementDisplayed("lnk_print_receipt");
	System.out.println(element("lnk_print_receipt").getText());
	Assert.assertTrue(element("lnk_print_receipt").getText().equals("Print Your Receipt"));
	
}

}
