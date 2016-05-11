package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.thoughtworks.selenium.webdriven.commands.IsAlertPresent;

public class ACS_Address_Validation_Action extends ASCSocietyGenericPage {
	static String pagename = "ACS_Address_Validation";
	WebDriver driver;
	int timeOut;
	

	public ACS_Address_Validation_Action(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
	}

	public String verifyIndividualProfilePage() {
		String name = "";
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("heading_name");
		name = element("heading_name").getText();
		return name;
	}

	public String fetchZipCode() {
		String address[], zipCode[];
		address = element("txt_zipCode").getText().split(",");
		zipCode = address[1].split(" ", 2);
		zipCode = zipCode[1].split(" ");
		logMessage("STEP: Zip code fetched is : " + zipCode[1] + "\n");
		return zipCode[1];
	}

	public void clickOnEditNameAndAddressButton() {
		isElementDisplayed("btn_editName&Address");
		element("btn_editName&Address").click();
		logMessage("STEP: Edit Name & Address button is clicked" + "\n");
	}

	public void verifyIndividualNameAndAddressInformationPage() {
		wait.waitForPageToLoadCompletely();
		switchToFrame("iframe1");
		isElementDisplayed("heading_individualName&Addr");
		logMessage("STEP : User is navigated to Individual Name & Address Information Page" + "\n");
	}
	
	public void verifyZipCode(String expectedZipCode){
		isElementDisplayed("input_zipcode");
		Assert.assertTrue(expectedZipCode.equals(element("input_zipcode").getAttribute("value").trim()),
				"ASSERT FAILED : Actual ZipCode " + element("input_zipcode").getAttribute("value")
						+ " does not matches with the expected ZipCode " + expectedZipCode + "\n");
		logMessage("ASSERT PASSED : Actual ZipCode " + element("input_zipcode").getAttribute("value")
				+ " matches with the expected ZipCode " + expectedZipCode + "\n");
	}

	public void enterZipCode(String expectedZipCode, String bogusCode) {
		element("input_zipcode").clear();
		element("input_zipcode").sendKeys(bogusCode);
		logMessage("STEP : Bogus code " + bogusCode + " is entered in ZipCode field" + "\n");
		clickOnSaveButton();
		switchToDefaultContent();
	}

	public void clickOnSaveButton() {
		isElementDisplayed("btn_save");
		element("btn_save").click();
		logMessage("STEP: Clicked on Save Button \n");
	}

	public void verifyAddressVerificationWindow() {
//	 handleAlert();
		 
//		getAlertText();

	    wait.resetExplicitTimeout(60);
	    wait.resetImplicitTimeout(60);
			wait.hardWait(3);
			logMessage("in try");		
			Actions action = new Actions(driver); 
        action.sendKeys(Keys.ENTER);
logMessage("*******");         
//		 Robot robot;
//		    try {
//		    	wait.resetImplicitTimeout(4);
//		    	wait.resetExplicitTimeout(4);
//		    robot = new Robot();
//
//		    robot.delay(2000);
//		    obot.keyPress(KeyEvent.VK_ENTER);
//		}catch(Exception e){
//			logMessage("No Alert Present");
//		}
		    wait.resetExplicitTimeout(timeOut);
		    wait.resetImplicitTimeout(timeOut);
//		checkAlert();
//		waitForAlertToAppear();
		switchWindow();
		isElementDisplayed("heading_address");
		logMessage("STEP : User is navigated to Address Verification Window \n");
	}

	public void verifyAddressVerificationPageZipCode(String expectedZipCode) {
		Assert.assertTrue(expectedZipCode.equals(element("txt_verificationZipCode").getAttribute("value")),
				"ASSERT FAILED : The ZipCode on Address Verification window "
						+ element("txt_verificationZipCode").getAttribute("value")
						+ " does not matches with the expected ZipCode " + expectedZipCode + "\n");
		logMessage("ASSERT PASSED : The ZipCode from Address Verification window "
				+ element("txt_verificationZipCode").getAttribute("value") + " matches with the expected ZipCode "
				+ expectedZipCode + "\n");
		clickOnSaveButtonOnAddressVerficationPage();
		switchWindow();
	}

	public void clickOnSaveButtonOnAddressVerficationPage() {
		isElementDisplayed("btn_verificationSave");
		element("btn_verificationSave").click();
		logMessage("STEP : Clicked on Save Button present on Address Verification Window");
	}

	public void verifyReplacementOfZipCode(String expectedZipCode) {
		wait.waitForPageToLoadCompletely();
		verifyIndividualNameAndAddressInformationPage();
		verifyZipCode(expectedZipCode);
		clickOnCancelButton();
	}
	
	public void clickOnCancelButton(){
		isElementDisplayed("btn_cancel");
		element("btn_cancel").click();
		logMessage("STEP : Clicked on Cancel button\n");
		switchToDefaultContent();
	}

	public void checkAlert() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 2);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {
			// exception handling
			logMessage("NO Alert Present!!!");
		}
	}

}
