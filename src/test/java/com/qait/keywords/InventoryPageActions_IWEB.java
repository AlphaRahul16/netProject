package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.DateUtil;

public class InventoryPageActions_IWEB extends ASCSocietyGenericPage {

	WebDriver driver;
	String pagename = "InventoryProductFulfillment";
	String numberOfYears, iwebProductName1;
	String IWEBProduct;
	int timeOut, hiddenFieldTimeOut;
	boolean flag, flag1;

	public InventoryPageActions_IWEB(WebDriver driver) {
		super(driver, "InventoryProductFulfillment");
		this.driver = driver;
	}

	public void clickOnProcessFulfillOrdersLink() {
		wait.hardWait(3);
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("link_productOrder");
		if (isBrowser("ie") || isBrowser("internet explorer")) {

			clickUsingXpathInJavaScriptExecutor(element("link_productOrder"));
			}
		 else {
			 element("link_productOrder").click();
		}
		
		
		logMessage("STEP : Clicked on Process Fulfill Orders Link On Inventory | Fulfillment Orders | Overview and Setup");
	}

	public void enterInvoiceDateFromAndToAndClickOnSearchButton() {
		isElementDisplayed("inp_datefrom");
		isElementDisplayed("inp_dateto");
		isElementDisplayed("btn_search");
		element("inp_datefrom").sendKeys(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"));
		element("inp_dateto").sendKeys(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"));
		if (isBrowser("ie") || isBrowser("internet explorer")) {

			clickUsingXpathInJavaScriptExecutor(element("btn_search"));
			}
		 else {
			element("btn_search").click();
		}
		
		logMessage("STEP : Enter Invoice Start Date And End Date And Click On Seacrh Button to Proceed Further");
		
	}

	public void verifySoldProductIsCheckedByDefaultUnderSelectOrdersToFulfill(String productName) {
		waitForSpinner();
		isElementDisplayed("chkbox_soldproduct",productName);
		Assert.assertTrue(element("chkbox_soldproduct",productName).isSelected(),
				"ASSERTION FAILED : Verified Sold Product "+productName+" is not Checked By Default Under Select Orders to Fulfill");
		logMessage("ASSERTION PASSED : Verified Sold Product "+productName+" Is Checked By Default Under Select Orders to Fulfill");
	}
	
	public void waitForSpinner() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			// handleAlert();
			isElementDisplayed("img_spinner");
			wait.waitForElementToDisappear(element("img_spinner"));
			logMessage("STEP : Wait for spinner to be disappeared \n");

		} catch (Exception Exp) {

			logMessage("STEP : Spinner is not present \n");
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
	}

	public void verifyFulfillmentReportsOnClickingProcessSelectedItems() {
		isElementDisplayed("btn_ship");
		if (isBrowser("ie") || isBrowser("internet explorer")) {

			clickUsingXpathInJavaScriptExecutor(element("btn_ship"));
			}
		 else {
			 element("btn_ship").click();
		}
		
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("txt_reports");
		logMessage("ASSERTION PASSED : Verified Fulfillment Reports Page On Clicking Process Selected Items");
	}

	public void verifyFulfillmentGroupProfilePage(String productName) {
		isElementDisplayed("bttn_continue");
		if (isBrowser("ie") || isBrowser("internet explorer")) {

			clickUsingXpathInJavaScriptExecutor(element("bttn_continue"));
			}
		 else {
			 element("bttn_continue").click();
		}	
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("productName_orders", productName);
		logMessage("Verified Product Name On Order Fulfillment Process Page");
	}

	public void VerifyFulfillmentGroupProfilePageOnClickingShippedButton() {
		isElementDisplayed("btn_ship");	
		if (isBrowser("ie") || isBrowser("internet explorer")) {

			clickUsingXpathInJavaScriptExecutor(element("btn_ship"));
			}
		 else {
			 element("btn_ship").click();
		}
		
		logMessage("STEP : Clicked On Fulfill | Remove | Hold Selected Items Button ");
	}
	
	public void verifyProductNameUnderLineItems(String productName) {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(3);
		isElementDisplayed("lineitem_product",productName);
		logMessage("Verified Product Name Under Line Items");
	}

	public void verifyAlertMessageOnClickingACSFulfillmentReportsIcon() {
		isElementDisplayed("icon_reports");
		element("icon_reports").click();
		logMessage("STEP : Reports ICON is clicked \n");
		String alert_msg = driver.switchTo().alert().getText().trim();
		System.out.println("Alert Message::"+alert_msg);
		driver.switchTo().alert().accept();
		//handleAlertUsingRobot();
		Assert.assertEquals(alert_msg,"The process is scheduled and you will receive an email when it is finished.");
		logMessage("ASSERTION PASSED : Verified Alert Message :: "+alert_msg+" On Clicking ACS Fulfillment Reports ICON");
	}

}
