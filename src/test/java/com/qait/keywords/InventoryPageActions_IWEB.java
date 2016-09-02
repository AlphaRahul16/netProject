package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
		super(driver, "IndividualsPage");
		this.driver = driver;
	}

	public void clickOnProcessFulfillOrdersLink() {
		isElementDisplayed("link_productOrder");
		element("link_productOrder").click();
		logMessage("Step:: Clicked on Process Fulfill Orders Link On Inventory | Fulfillment Orders | Overview and Setup");
	}

	public void enterInvoiceDateFromAndToAndClickOnSearchButton() {
		isElementDisplayed("inp_datefrom");
		isElementDisplayed("inp_dateto");
		isElementDisplayed("btn_search");
		element("inp_datefrom").sendKeys(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"));
		element("inp_dateto").sendKeys(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"));
		element("btn_search").click();
		logMessage("Step:: Enter Invoice Start Date And End Date And Click On Seacrh Button to Proceed Further");
		
	}

	public void verifySoldProductIsCheckedByDefaultUnderSelectOrdersToFulfill(String productName) {
		waitForSpinner();
		isElementDisplayed("chkbox_soldproduct",productName);
		Assert.assertTrue(element("chkbox_soldproduct",productName).isSelected());
		logMessage("[ASSERTION PASSED]:: Verified Sold Product "+productName+" Is Checked By Default Under Select Orders to Fulfill");
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
			logMessage("STEP : wait for spinner to be disappeared \n");

		} catch (Exception Exp) {

			logMessage("STEP : spinner is not present \n");
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
	}

	public void verifyFulfillmentReportsOnClickingProcessSelectedItems() {
		isElementDisplayed("btn_ship");
		element("btn_ship").click();
		waitForPageToLoad();
		isElementDisplayed("txt_reports");
		logMessage("[ASSERTION PASSED]:: Verified Fulfillment Reports Page On Clicking Process Selected Items");
	}

	public void verifyFulfillmentGroupProfilePage(String productName) {
		isElementDisplayed("bttn_continue");
		element("bttn_continue").click();
		waitForPageToLoad();
		isElementDisplayed("productName_orders", productName);
		logMessage("Verified Product Name On Order Fulfillment Process Page");
	}

	public void VerifyFulfillmentGroupProfilePageOnClickingShippedButton() {
		isElementDisplayed("btn_ship");	
		element("btn_ship").click();
		logMessage("Step: Clicked On Fulfill | Remove | Hold Selected Items Button");
	}
	
	public void verifyProductNameUnderLineItems(String productName) {
		isElementDisplayed("lineitem_product",productName);
		logMessage("Verified Product Name Under Line Items");
	}

	public void verifyAlertMessageOnClickingACSFulfillmentReportsIcon() {
		isElementDisplayed("icon_reports");
		element("icon_reports").click();
		String alert_msg = driver.switchTo().alert().getText().trim();
		Assert.assertEquals("The process is scheduled and you will receive an email when it is finished.", alert_msg);
		logMessage("[ASSERTION PASSED]:: Verified Alert Message On Clicking ACS Fulfillment Reports ICON");
	}



}