package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.YamlReader;

public class ACS_Apply_Payment_Actions extends ASCSocietyGenericPage {
	WebDriver driver;
	static String pagename = "ACS_Apply_Payment_Page";
	int timeOut, hiddenFieldTimeOut;

	public ACS_Apply_Payment_Actions(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

	public void verifyCheckBoxSelectedAtSelectInvoiceTab(String labelName) {
		hardWaitForIEBrowser(3);
		isElementDisplayed("chk_selectInvoice", labelName);
		logMessage("ASSERT PASSED : Check box is selected for " + labelName
				+ "\n");
	}
	

	public void clickOnNextButton() {
		isElementDisplayed("btn_next");
		if (isIEBrowser()) {
			clickUsingXpathInJavaScriptExecutor(element("btn_next"));
			logMessage("STEP : Clicked on next button\n");
		} else {
			element("btn_next").click();
			logMessage("STEP : Clicked on next button\n");
		}

	}

	public void verifyTabName(String tabName) {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("tab_addApplyPayment", tabName);
		logMessage("ASSERT PASSED : Verify tab name " + tabName + "\n");
	}

	public void enterDetailsForPayment(String paymentMethod) {
		logMessage("STEP : Payment method to apply the payment is "	+ paymentMethod);
		if (paymentMethod.equalsIgnoreCase("BOA - Check")) {
			selectCardDetails(
					"payment method",
					YamlReader
							.getYamlValue("creditCardDetails.paymentMethodBOACheck.Type"));
			waitForSpinner();
			enterCardDetails(
					"check number",
					YamlReader
							.getYamlValue("creditCardDetails.paymentMethodBOACheck.CheckNumber"));
		} else if (paymentMethod.equalsIgnoreCase("Visa/MC")) {
			String type = YamlReader
					.getYamlValue("creditCardDetails.paymentMethodVisaMC.Type");
			String ccNumber = YamlReader
					.getYamlValue("creditCardDetails.paymentMethodVisaMC.Number");
			String expDate = YamlReader
					.getYamlValue("ACS_ApplyPayment.CreditCardExpiration");
			String CVV = YamlReader
					.getYamlValue("creditCardDetails.paymentMethodVisaMC.cvv-number");

			selectCardDetails("payment method", type);
			// waitForSpinner();
			wait.waitForPageToLoadCompletely();
			enterCardDetails("cc number", ccNumber);
			// waitForSpinner();

			selectCardDetails("expiration date", expDate);
			// waitForSpinner();
			enterCardDetails("CVV", CVV);
			// waitForSpinner();"

		}

	}

	public void selectCardDetails(String labelName, String drpDwnValue) {
		isElementDisplayed("select_addApplyPayment", labelName);
		wait.waitForPageToLoadCompletely();
		wait.hardWait(1);
		selectProvidedTextFromDropDown(
				element("select_addApplyPayment", labelName), drpDwnValue);
		logMessage("STEP : " + drpDwnValue + " is selected for " + labelName
				+ "\n");

	}

	public void enterCardDetails(String labelName, String inputValue) {
		isElementDisplayed("inp_addApplyPayment", labelName);
		element("inp_addApplyPayment", labelName).sendKeys(inputValue);
		logMessage("STEP : " + inputValue + " is selected for " + labelName
				+ "\n");

	}

	public void clickOnSaveButton() {
		isElementDisplayed("btn_Save");
		if (isIEBrowser()) {
			clickUsingXpathInJavaScriptExecutor(element("btn_Save"));
			logMessage("STEP : Save button is clicked\n");
		} else {
			element("btn_Save").click();
			logMessage("STEP : Save button is clicked\n");
		}

		waitForSpinner();
	}

	public void selectBatch(String batchName) {
		hardWaitForIEBrowser(3);
		System.out.println(batchName);
		isElementDisplayed("select_batchName");
		selectProvidedTextFromDropDown(element("select_batchName"), batchName);
		logMessage("STEP : Batch name is selected " + batchName + "\n");
		waitForSpinner();
	}

	public void waitForSpinner() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		try {
			handleAlert();
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(10);
			isElementDisplayed("img_spinner");
			wait.waitForElementToDisappear(element("img_spinner"));
			logMessage("STEP : Wait for spinner to be disappeared \n");

		} catch (WebDriverException Exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("STEP : Spinner is not present \n");
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
	}

}
