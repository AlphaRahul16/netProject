package com.qait.keywords;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.getpageobjects.GetPage;

public class ASM_PUBSPage extends ASCSocietyGenericPage {
	WebDriver driver;
	static String pagename = "ASM_PUBSPage";
	public List<String> productName;
	public List<String> productAmount;
	String taxAmount;

	public ASM_PUBSPage(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

	public void SavingProductNameAndAmount() {
		productName = new ArrayList<String>();
		productAmount = new ArrayList<String>();
		for (WebElement product : elements("list_productName")) {
			productName.add(product.getAttribute("textContent").trim());
		}

		for (WebElement amount : elements("list_productAmount")) {
			productAmount.add(amount.getAttribute("textContent").trim());
		}
		System.out.println("============================Product Name And Amount List===========================================");
		
		for (String name : productName) {
			System.out.println(name);
		}
		for (String amount : productAmount) {
			System.out.println(amount);
		}
	}

	private Double getProductAmount(String productAmount) {
		productAmount = productAmount.substring(productAmount.indexOf('$') + 1);
		return Double.parseDouble(productAmount);
	}

	public void clickOnGoButtonWithCustomerLoginId(String customerID) {
		isElementDisplayed("txt_recordNumber");
		element("txt_recordNumber").sendKeys(customerID);
		logMessage("Step : send customer id  " + customerID
				+ " to text field !!");
		isElementDisplayed("btn_search");
		element("btn_search").click();
		logMessage("Step : click to the search button !!");
	}

	public void clickOnActiveSubscription() {
		isElementDisplayed("lnk_moreTab");
		element("lnk_moreTab").click();
		logMessage("step: more tab is expanded !!");
		isElementDisplayed("lnk_subscriptionTab");
		element("lnk_subscriptionTab").click();
		logMessage("subscription tab is selected in expanded more option !!");
		wait.hardWait(6);
		isElementDisplayed("lnk_activeSubscription");
		element("lnk_activeSubscription").click();
		logMessage("Step: active subscription expanded !!");
		wait.hardWait(2);
	}

	public void verifyDataFromInitialPage() {
		for (int i = 0; i < productName.size(); i++) {
			System.out.println("Product Name::"+productName.get(i));
			System.out.println("Product Name::"+productAmount.get(i));
			isElementDisplayed("td_subscription", productName.get(i),
					String.valueOf(productAmount.get(i)));
		}
	}
	
	public void clockOnPrintOrderReceipt()
	{
		isElementDisplayed("btn_printReceipt");
		element("btn_printReceipt").click();
		logMessage("Step: click on print order receipt !!");
		wait.hardWait(5);
	}
	
	public void verifyDataFromPdfFile()
	{

		for (String product_Name : productName) {
			System.out.println("In PDF Method::"+product_Name);
			extractAndCompareTextFromPdfFile("report", product_Name, 1);
		}
		
		for (String product_Amount : productAmount) {
			System.out.println("In PDF Method::"+product_Amount);
			extractAndCompareTextFromPdfFile("report", product_Amount, 1);
		}
	}

	public void loginInToApplication(String userName, String password) {
		enterUserName(userName);
		enterPassword(password);
		clickOnVerifyButton();
	}

	public void enterUserName(String userName) {
		isElementDisplayed("inp_userName");
		element("inp_userName").sendKeys(userName);
		logMessage("Step : " + userName + " is entered in inp_userName\n");
	}

	public void enterPassword(String password) {
		isElementDisplayed("inp_password");
		element("inp_password").sendKeys(password);
		logMessage("Step : "
				+ password
				+ " is entered in inp_passworclickOnAddAnESubscriptionButtond\n");
	}

	public void clickOnVerifyButton() {
		isElementDisplayed("btn_verify");
		element("btn_verify").click();
		logMessage("Step : verify button is clicked in btn_verify\n");
	}

	public void addSubscription() {
		wait.hardWait(2);
		clickOnAddAnESubscriptionButton();
		wait.hardWait(2);
		hardWaitForIEBrowser(5);
		verifyESubscriptionPage();
		clickOnFirstAddButton();
		wait.hardWait(3);
		clickOnSaveButton();
	}

	public void clickOnAddAnEPassportButton() {
		isElementDisplayed("btn_passportAdd");
		element("btn_passportAdd").click();
		logMessage("Step : add an e-passport button is clicked in btn_passportAdd\n");

	}

	public void clickOnAddButtonInPassport() {
		isElementDisplayed("btn_add");
		element("btn_add").click();
		logMessage("Step : add button is clicked in btn_add \n");
		wait.waitForPageToLoadCompletely();

	}

	public String passportValue() {
		isElementDisplayed("txt_amount");
		String passportAmountValue = element("txt_amount").getText();
		passportAmountValue = passportAmountValue.substring(passportAmountValue
				.indexOf("$") + 1);
		logMessage("Step : passportAmountValue " + passportAmountValue
				+ " is saved \n");
		return passportAmountValue;
	}

	public String subscriptionValue() {
		isElementDisplayed("txt_amount");
		String subscriptionAmountValue = element("txt_amount").getText();
		subscriptionAmountValue = subscriptionAmountValue
				.substring(subscriptionAmountValue.indexOf("$") + 1);
		logMessage("Step : subscriptionAmountValue " + subscriptionAmountValue
				+ " is saved \n");
		return subscriptionAmountValue;
	}

	public void verifyTotalAmountForAddedProducts() {
		Double total = 0.0;
		for (int i = 0; i < productAmount.size(); i++) {
			total += getProductAmount(productAmount.get(i));
		}
		taxAmount = element("txt_taxAmount").getText();
		taxAmount = taxAmount.substring(taxAmount.indexOf('$') + 1);
		total += Double.parseDouble(taxAmount);

		String invoiceValue = element("txt_invoiceValue").getText();
		invoiceValue = invoiceValue.substring(invoiceValue.indexOf("$") + 1);
		Double double_invoiceValue = (Double) Double.parseDouble(invoiceValue);

		total = setPrecision(total, 2);
		double_invoiceValue = setPrecision(double_invoiceValue, 2);

		logMessage("Step : total from previous page =" + total
				+ " & invoice from this page =" + double_invoiceValue);
		Assert.assertEquals(total, double_invoiceValue);
		logMessage("[ASSERTION PASSED]: Verified Total Invoice Amount!!");

	}

	public Double setPrecision(Double value, int precisionLength) {
		int number = 10;
		number = (int) Math.pow(number, precisionLength);
		value = value * number;
		value = (double) Math.round(value);
		value = value / number;
		return value;
	}

	public void clickOnAddAnESubscriptionButton() {
		isElementDisplayed("btn_subscriptionAdd");
		element("btn_subscriptionAdd").click();
		logMessage("Step : add an e-subscription button is clicked in btn_subscriptionAdd\n");
	}

	public void verifyESubscriptionPage() {
		isElementDisplayed("txt_eSubscription");
	}

	public void clickOnFirstAddButton() {
		isElementDisplayed("btn_add");
		element("btn_add").click();
		logMessage("Step : add button is clicked in btn_add \n");
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(3);
		isElementDisplayed("chk_archive");
		element("chk_archive").click();
		logMessage("Step : add button is clicked in chk_archive \n");

		// verifySubcriptionAdded();
	}

	public void clickOnSaveButton() {
		isElementDisplayed("btn_save");
		element("btn_save").click();
		logMessage("Step : save button is clicked in btn_save \n");
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(3);
		// verifySavedSuccessfully();
	}

	public void verifySubcriptionAdded() {
		isElementDisplayed("txt_added");
		logMessage("ASSERT PASSED : verify added successfully in txt_added\n");
	}

	public void verifySavedSuccessfully() {
		isElementDisplayed("tr_saved");
	}

	public void submitPaymentDetails(String cardType, String cardholderName,
			String cardNumber, String cvvNumber, String year_Value) {
		verifyPaymentPage();
		selectCreditCardType(cardType);
		enterCreditCardHolderName(cardholderName);
		enterCreditCardNumber(cardNumber);
		enterCVVNumber(cvvNumber);
		selectExpirationYear(year_Value);
		clickOnConfirmOrderButton();
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(3);
	}

	public void selectCreditCardType(String cardType) {
		isElementDisplayed("list_cardType");
		selectProvidedTextFromDropDown(element("list_cardType"), cardType);
	}

	public void enterCreditCardHolderName(String cardholderName) {
		isElementDisplayed("inp_cardHolderName");
		element("inp_cardHolderName").sendKeys(cardholderName);
		logMessage("Step : " + cardholderName
				+ " is entered in inp_cardHolderName\n");
	}

	public void enterCreditCardNumber(String cardNumber) {
		isElementDisplayed("inp_cardNumber");
		element("inp_cardNumber").sendKeys(cardNumber);
		logMessage("Step : " + cardNumber + " is entered in inp_cardNumber\n");
	}

	public void enterCVVNumber(String cvvNumber) {
		isElementDisplayed("inp_CVVNumber");
		element("inp_CVVNumber").sendKeys(cvvNumber);
		logMessage("Step : " + cvvNumber + " is entered in inp_CVVNumber\n");
	}

	public void selectExpirationYear(String yearValue) {
		isElementDisplayed("list_expirationYear");
		selectProvidedTextFromDropDown(element("list_expirationYear"),
				yearValue);
		logMessage("Step : " + yearValue
				+ " is selected for in list_expirationYear\n");
	}

	public void clickOnConfirmOrderButton() {
		isElementDisplayed("btn_ConfirmOrder");
		element("btn_ConfirmOrder").click();
		logMessage("Step : Confirm Order button is clicked in btn_ConfirmOrder\n");
	}

	public void clickOnPlaceOrder() {
		isElementDisplayed("btn_placeOrder");
		element("btn_placeOrder").click();
		logMessage("Step : click on place order in btn_placeOrder\n");
	}

	public void verifyReceiptPage() {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(2);
		isElementDisplayed("txt_receipt");
	}

	public void verifyPaymentPage() {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(2);
		isElementDisplayed("txt_paymentPage");
	}

	public void verifyUserIsOnEwebLoginPage() {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(2);
		isElementDisplayed("inp_userName");
		isElementDisplayed("inp_password");
		isElementDisplayed("btn_verify");
		logMessage("[ASSERTION PASSED]:: Verified User Is On Eweb Login Page");
	}

	public void verifyUserIsOnHomePageForEwebPBA(String individualName) {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(2);
		wait.hardWait(2);
		String s = (String) executeJavascriptReturnValue("document.getElementsByTagName('span')[3].innerHTML;");
		Assert.assertEquals(s.trim(), individualName);
		logMessage("[ASSERTION PASSED]:: Verified User Is On Home Page for Eweb Application");
	}
}
