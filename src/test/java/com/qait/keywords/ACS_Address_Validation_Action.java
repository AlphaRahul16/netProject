package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.ConfigPropertyReader;


public class ACS_Address_Validation_Action extends ASCSocietyGenericPage {
	static String pagename = "ACS_Address_Validation";
	String currentWindow;
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

	public String getZipCode() {
		String address[], zipCode;
		address = element("txt_zipCode").getText().split(" ");
		zipCode=address[address.length-1];
		logMessage("STEP: Zip code is : " + zipCode + "\n");
		return zipCode;
	}

	public void clickOnEditNameAndAddressButton() {                    //javascript added
		isElementDisplayed("btn_editName&Address");
		clickUsingXpathInJavaScriptExecutor(element("btn_editName&Address"));
//		element("btn_editName&Address").click();
		logMessage("STEP: Edit Name & Address button is clicked" + "\n");
	}

	public void verifyIndividualNameAndAddressInformationPage() {
		wait.hardWait(1);
//		wait.waitForPageToLoadCompletely();
		switchToFrame("iframe1");
		isElementDisplayed("heading_individualName&Addr");
		logMessage("STEP : User is navigated to Individual Name & Address Information Page" + "\n");
	}

	public void verifyZipCode(String expectedZipCode) {
		isElementDisplayed("input_zipcode");
		logMessage("STEP : Expected Zipcode: "+expectedZipCode);
		logMessage("STEP : Actual Zipcode: "+element("input_zipcode").getAttribute("value").trim());
		Assert.assertTrue(expectedZipCode.equals(element("input_zipcode").getAttribute("value").trim()),
				"ASSERT FAILED : The ZipCode does not matches\n");
		logMessage("ASSERT PASSED : The ZipCode matches \n");
	}

	public void enterZipCode(String expectedZipCode, String bogusCode) {
		if (ConfigPropertyReader.getProperty("browser").equalsIgnoreCase("IE")
				|| ConfigPropertyReader.getProperty("browser")
						.equalsIgnoreCase("ie")
				|| ConfigPropertyReader.getProperty("browser")
						.equalsIgnoreCase("internetexplorer")){
			clickUsingXpathInJavaScriptExecutor(element("input_zipcode"));
		}
		element("input_zipcode").clear();
		element("input_zipcode").sendKeys(bogusCode);
		logMessage("STEP : Bogus code " + bogusCode + " is entered in ZipCode field" + "\n");
		
		currentWindow = driver.getWindowHandle();
		System.out.println("-------current window is:"+currentWindow);
		
		clickOnSaveButton();
		switchToDefaultContent();
	}

	public void clickOnSaveButton() {                          
		isElementDisplayed("btn_save");
		clickUsingXpathInJavaScriptExecutor(element("btn_save"));
//		element("btn_save").click();
		logMessage("STEP: Clicked on Save Button \n");
		handleAlert();
	}

	public void verifyAddressVerificationWindow() {         
		hardWaitForIEBrowser(3);		
		changeWindow(1);
//		switchToWindowHavingIndex(1);
		isElementDisplayed("heading_address");
		logMessage("STEP : User is navigated to Address Verification Window \n");
	}

	public void verifyAddressVerificationPageZipCode(String expectedZipCode) {
		Assert.assertTrue(expectedZipCode.equals(element("txt_verificationZipCode").getAttribute("value")),
				"ASSERT FAILED : The ZipCode on Address Verification window "
						+ element("txt_verificationZipCode").getAttribute("value")
						+ " does not matches with the ZipCode " + expectedZipCode + "\n");
		logMessage("ASSERT PASSED : The ZipCode from Address Verification window "
				+ element("txt_verificationZipCode").getAttribute("value") + " matches with the ZipCode "
				+ expectedZipCode + "\n");
		clickOnSaveButtonOnAddressVerficationPage();	
//		switchToWindowHavingIndex(0);
	}

	public void clickOnSaveButtonOnAddressVerficationPage() {            
		isElementDisplayed("btn_verificationSave");
		if (ConfigPropertyReader.getProperty("browser").equalsIgnoreCase("IE")
				|| ConfigPropertyReader.getProperty("browser")
						.equalsIgnoreCase("ie")
				|| ConfigPropertyReader.getProperty("browser")
						.equalsIgnoreCase("internetexplorer")){
			clickUsingXpathInJavaScriptExecutor(element("btn_verificationSave"));
			logMessage("STEP : Clicked on Save Button on Address Verification Window");
			hardWaitForIEBrowser(2);
			changeWindow(0);
		}
		else{
		  element("btn_verificationSave").click();
		  logMessage("STEP : Clicked on Save Button on Address Verification Window");
		  changeWindow(0);
		}
	}

	public void verifyReplacementOfZipCode(String expectedZipCode) {
//		wait.waitForPageToLoadCompletely();
		wait.hardWait(1);
		verifyIndividualNameAndAddressInformationPage();
		verifyZipCode(expectedZipCode);
		logMessage("STEP : The bogus ZipCode is replaced by the original ZipCode\n");
		clickOnCancelButton();
	}

	public void clickOnCancelButton() {                               
		isElementDisplayed("btn_cancel");
		if (ConfigPropertyReader.getProperty("browser").equalsIgnoreCase("IE")
				|| ConfigPropertyReader.getProperty("browser")
						.equalsIgnoreCase("ie")
				|| ConfigPropertyReader.getProperty("browser")
						.equalsIgnoreCase("internetexplorer")){
			clickUsingXpathInJavaScriptExecutor(element("btn_cancel"));
		}
		else{
		element("btn_cancel").click();
		logMessage("STEP : Clicked on Cancel button\n");
		}
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
	
	public void waitForPageReadyState(){
		System.out.println("----"+String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")));
		while(String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")).equalsIgnoreCase("interactive") || 
				String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")).equalsIgnoreCase("loading")){
		System.out.println("****interactive");
		wait.hardWait(1);
		}
		System.out.println(String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")));
	}

}
