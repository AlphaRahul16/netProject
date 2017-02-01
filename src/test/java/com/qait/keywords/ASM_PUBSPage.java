package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;

public class ASM_PUBSPage extends ASCSocietyGenericPage {
	WebDriver driver;
	static String pagename = "ASM_PUBSPage";
	public List<String> productName;
	public List<String> productAmount;
	String taxAmount, shippingAmount;
	int timeOut, hiddenFieldTimeOut;

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

	}

	private Double getProductAmount(String productAmount) {
		productAmount = productAmount.substring(productAmount.indexOf('$') + 1);
		return Double.parseDouble(productAmount);
	}

	public void clickOnGoButtonWithCustomerLoginId(String customerID) {
		isElementDisplayed("txt_recordNumber");
		element("txt_recordNumber").sendKeys(customerID);
		logMessage("STEP : Send customer id  " + customerID + " to text field !!");
		isElementDisplayed("btn_search");
		element("btn_search").click();
		logMessage("STEP : Click to the search button !!");
	}

	public void clickOnActiveSubscription() {
		hardWaitForChromeBrowser(3);
		handleAlert();
		isElementDisplayed("lnk_moreTab");
		element("lnk_moreTab").click();
		logMessage("STEP : More tab is expanded !!");
		hardWaitForChromeBrowser(2);
		isElementDisplayed("lnk_subscriptionTab");
		element("lnk_subscriptionTab").click();
		logMessage("Subscription tab is selected in expanded more option !!");
		wait.waitForPageToLoadCompletely();
		waitForSpinner();
		isElementDisplayed("lnk_activeSubscription");
		element("lnk_activeSubscription").click();
		logMessage("STEP : Active subscription expanded !!");
		wait.waitForPageToLoadCompletely();
		waitForSpinner();
	}

	public void verifyDataFromInitialPage() {
		wait.waitForPageToLoadCompletely();
		int i = 0;
		for (String name : productName) {
			wait.hardWait(1);
			isElementDisplayed("td_subscription", name, String.valueOf(getProductAmount(productAmount.get(i))));
			i++;
		}
		logMessage("ASSERTION PASSED : Verified Product Names and amount under Active Subscription on IWEB");
	}

	public void waitForSpinner() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			handleAlert();
			wait.resetImplicitTimeout(3);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("img_spinner");
			wait.waitForElementToDisappear(element("img_spinner"));
			logMessage("STEP : Wait for spinner to be disappeared \n");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (Exception e) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("STEP : Spinner is not present \n");
		}
	}

	public void clickOnPrintOrderReceipt() {
		isElementDisplayed("btn_printReceipt");
		element("btn_printReceipt").click();
		logMessage("STEP : Clicked on print order receipt !!");
		wait.hardWait(15);
		_verifyPDFFileIsDownloaded("report");
	}

	private void _verifyPDFFileIsDownloaded(String fileName) {
		String source = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + "DownloadedFiles" + File.separator + fileName
				+ ".pdf";
		File sourceFile = new File(source);
		Assert.assertTrue(sourceFile.exists());
	}

	public void verifyDataFromPdfFile() throws IOException {
		System.out.println("===================================================================================");
		String pdfContent = extractFromPdf("report", 1,"downloads");
		System.out.println("PDF Content::" + pdfContent);

		for (String product_Name : productName) {
			System.out.println("In PDF Method::" + product_Name);
		}

		System.out.println("====================================================================================");

		for (String product_Amount : productAmount) {
			System.out.println("In PDF Method for Amount::" + product_Amount);
			Assert.assertTrue(pdfContent.contains(product_Amount));
			logMessage("ASSERTION PASSED : Verified Product Amount " + product_Amount + " in Invoice Receipt");
		}

		for (String product_Name : productName) {
			System.out.println("In PDF Method::" + product_Name);
			if (product_Name.contains(YamlReader.getYamlValue("ACS_PBA_Product.shortName"))) {
				Assert.assertTrue(pdfContent.contains(YamlReader.getYamlValue("ACS_PBA_Product.fullName")));
			} else {
				Assert.assertTrue(pdfContent.contains(product_Name));
			}
			logMessage("ASSERTION PASSED : Verified Product Name " + product_Name + " is available in Invoice Receipt");
		}

	}

	public void _deleteExistingFIleFile(String fileName) {
		String source = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + "DownloadedFiles" + File.separator + fileName
				+ ".pdf";

		File sourceFile = new File(source);
		// try {
		// System.out.println("-----"+sourceFile.exists());
		// FileUtils.forceDelete(sourceFile);
		// logMessage("Already Existed File is deleted from location " +
		// sourceFile.getAbsolutePath());
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		if (sourceFile.exists()) {

			System.out.println(sourceFile.delete());
			sourceFile.delete();
			logMessage("STEP: Already Existed File is deleted from location " + sourceFile.getAbsolutePath());
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
		logMessage("STEP : " + userName + " is entered in inp_userName\n");
	}

	public void enterPassword(String password) {
		isElementDisplayed("inp_password");
		element("inp_password").sendKeys(password);
		logMessage("STEP : " + password + " is entered in inp_password\n");
	}

	public void clickOnVerifyButton() {
		isElementDisplayed("btn_verify");
		element("btn_verify").click();
		logMessage("STEP : Verify button is clicked in btn_verify\n");
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
		logMessage("STEP : Add an e-passport button is clicked in btn_passportAdd\n");

	}

	public void clickOnAddButtonInPassport() {
		hardWaitForChromeBrowser(3);
		isElementDisplayed("btn_add");
		element("btn_add").click();
		logMessage("STEP : Add button is clicked in btn_add \n");
		wait.waitForPageToLoadCompletely();

	}

	public String passportValue() {
		isElementDisplayed("txt_amount");
		String passportAmountValue = element("txt_amount").getText();
		passportAmountValue = passportAmountValue.substring(passportAmountValue.indexOf("$") + 1);
		logMessage("STEP : PassportAmountValue " + passportAmountValue + " is saved \n");
		return passportAmountValue;
	}

	public String subscriptionValue() {
		isElementDisplayed("txt_amount");
		String subscriptionAmountValue = element("txt_amount").getText();
		subscriptionAmountValue = subscriptionAmountValue.substring(subscriptionAmountValue.indexOf("$") + 1);
		logMessage("STEP : SubscriptionAmountValue " + subscriptionAmountValue + " is saved \n");
		return subscriptionAmountValue;
	}

	public void verifyTotalAmountForAddedProducts() {
		Double total = 0.0;
		for (int i = 0; i < productAmount.size(); i++) {
			total += getProductAmount(productAmount.get(i));
		}
		isElementDisplayed("txt_taxAmount");
		taxAmount = element("txt_taxAmount").getText();
		taxAmount = taxAmount.substring(taxAmount.indexOf('$') + 1);
		total += Double.parseDouble(taxAmount);
		logMessage("STEP : TaxAmount $" + taxAmount + " is retrieved !!");
		wait.hardWait(1);

		isElementDisplayed("txt_shippingAmount");
		shippingAmount = element("txt_shippingAmount").getText();
		shippingAmount = shippingAmount.substring(shippingAmount.indexOf('$') + 1);
		total += Double.parseDouble(shippingAmount);
		logMessage("STEP : ShippingAmount $" + shippingAmount + " is retrieved !!");
		wait.hardWait(1);

		isElementDisplayed("txt_invoiceValue");
		String invoiceValue = element("txt_invoiceValue").getText();
		invoiceValue = invoiceValue.substring(invoiceValue.indexOf("$") + 1);
		Double double_invoiceValue = (Double) Double.parseDouble(invoiceValue);
		logMessage("STEP : InvoiceAmount $" + double_invoiceValue + " is retrieved !!");
		wait.hardWait(1);

		total = setPrecision(total, 2);
		double_invoiceValue = setPrecision(double_invoiceValue, 2);

		logMessage("STEP : Total from previous page =" + total + " & invoice from this page =" + double_invoiceValue);
		Assert.assertEquals(total, double_invoiceValue);
		logMessage("ASSERTION PASSED : Verified Total Invoice Amount!!");

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
		logMessage("STEP : Add an e-subscription button is clicked in btn_subscriptionAdd\n");
	}

	public void verifyESubscriptionPage() {
		isElementDisplayed("txt_eSubscription");
	}

	public void clickOnFirstAddButton() {
		hardWaitForChromeBrowser(3);
		isElementDisplayed("btn_add");
		element("btn_add").click();
		logMessage("STEP : Add button is clicked in btn_add \n");
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(3);
		if(checkIfElementIsThere("chk_archive")){
			element("chk_archive").click();
			logMessage("STEP : Add button is clicked in chk_archive \n");
		}
	}

	public void clickOnSaveButton() {
		isElementDisplayed("btn_save");
		element("btn_save").click();
		logMessage("STEP : Save button is clicked in btn_save \n");
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(3);
		// verifySavedSuccessfully();
	}

	public void verifySubcriptionAdded() {
		isElementDisplayed("txt_added");
		logMessage("ASSERT PASSED : Verify added successfully in txt_added\n");
	}

	public void verifySavedSuccessfully() {
		isElementDisplayed("tr_saved");
	}

	public void submitPaymentDetails(String cardType, String cardholderName, String cardNumber, String cvvNumber,
			String year_Value) {
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
		logMessage("STEP : " + cardholderName + " is entered in inp_cardHolderName\n");
	}

	public void enterCreditCardNumber(String cardNumber) {
		isElementDisplayed("inp_cardNumber");
		element("inp_cardNumber").sendKeys(cardNumber);
		logMessage("STEP : " + cardNumber + " is entered in inp_cardNumber\n");
	}

	public void enterCVVNumber(String cvvNumber) {
		isElementDisplayed("inp_CVVNumber");
		element("inp_CVVNumber").sendKeys(cvvNumber);
		logMessage("STEP : " + cvvNumber + " is entered in inp_CVVNumber\n");
	}

	public void selectExpirationYear(String yearValue) {
		isElementDisplayed("list_expirationYear");
		selectProvidedTextFromDropDown(element("list_expirationYear"), yearValue);
		logMessage("STEP : " + yearValue + " is selected for in list_expirationYear\n");
	}

	public void clickOnConfirmOrderButton() {
		isElementDisplayed("btn_ConfirmOrder");
		element("btn_ConfirmOrder").click();
		logMessage("STEP : Confirm Order button is clicked in btn_ConfirmOrder\n");
	}

	public void clickOnPlaceOrder() {
		isElementDisplayed("btn_placeOrder");
		element("btn_placeOrder").click();
		logMessage("STEP : Click on place order button");
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
		logMessage("ASSERTION PASSED : Verified User Is On Eweb Login Page");
	}

	public void verifyUserIsOnHomePageForEwebPBA(String individualName) {
		_deleteExistingFIleFile("report");
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(2);
		wait.hardWait(2);
		String s = (String) executeJavascriptReturnValue("document.getElementsByTagName('span')[3].innerHTML;");
		Assert.assertTrue(individualName.contains(s));
		logMessage("ASSERTION PASSED : Verified User Is On Home Page for Eweb Application");
	}

	public void verifyDataFromPdfFileForAACTOMR(String membershipType, String invoiceTotal, String customerID,
			String productName) throws IOException {
		boolean flag = false;
		for (int i = 0; i <= 4; i++) {
			wait.hardWait(15);
			flag = _verifyPDFFileIsDownloadedForAACTOMR("report");
			if (flag) {
				Assert.assertTrue(flag);
				logMessage("ASSERTION PASSED: report.pdf file is downloaded");
				String pdfContent = extractFromPdf("report", 1,"downloads");
				System.out.println("PDF Content::" + pdfContent);
				verifyDetailsFromPDFFile(pdfContent, membershipType, "Membership Type");
				verifyDetailsFromPDFFile(pdfContent, invoiceTotal, "invoice Total");
				verifyDetailsFromPDFFile(pdfContent, DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/yyyy", "EST5EDT"),
						"Payment Date");
				verifyDetailsFromPDFFile(pdfContent, customerID, "Membership ID");
				verifyDetailsFromPDFFile(pdfContent, productName, "Product Name");
				break;
			} else {
				logMessage("STEP: waiting for report.pdf file to be downloaded");
			}
		}
		if (!flag) {
			Assert.assertTrue(flag, "ASSERT FAILED: report.pdf is not downloaded");
		}
//		if(isBrowser("chrome")){
//			changeWindow(1);
//			driver.close();
//		}
	}

	private void verifyDetailsFromPDFFile(String pdfContent, String value, String text) {
		System.out.println(value + " as " + text);
		Assert.assertTrue(pdfContent.contains(value));
		logMessage("ASSERTION PASSED : Verified " + text + " as " + value);

	}

	private boolean _verifyPDFFileIsDownloadedForAACTOMR(String fileName) {
		String source = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + "DownloadedFiles" + File.separator + fileName
				+ ".pdf";
		File sourceFile = new File(source);
		boolean flag = sourceFile.exists();
		return flag;
		// Assert.assertTrue(sourceFile.exists());
	}
}
