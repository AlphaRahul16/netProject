package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.ConfigPropertyReader;
import com.qait.automation.utils.DateUtil;


public class ACS_BatchProcessingActions extends ASCSocietyGenericPage {
	WebDriver driver;
	static String pagename = "ACS_Batch_Processing";
	List<String> committees = new ArrayList<String>();

	public ACS_BatchProcessingActions(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;

	}

	public void clickEditButtonOnBatchProcessingPage() {
		hardWaitForChromeBrowser(2);
		isElementDisplayed("btn_edit");
		clickUsingXpathInJavaScriptExecutor(element("btn_edit"));
//		element("btn_edit").click();
		logMessage("Step : Edit button on batch Processing page is clicked\n");
		
	}

	private String getBatchTotalAmount() {
		
		String batchTotal;
		batchTotal=element("txt_BatchTotal").getText();
		return batchTotal;
	}

	private String getBatchTotalCount() {
		String batchCount;
		batchCount=element("txt_BatchCount").getText();
		return batchCount;
		
	}

	public void fillControlDetailsEqualToBatchDetails(String type,String value) {
		if(isBrowser("ie") || (isBrowser("internet explorer")))
			element("inp_control"+type).clear();
		else{
		element("inp_control"+type).click();
	    element("inp_control"+type).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		}
		element("inp_control"+type).sendKeys(value);
		logMessage("Step : Control "+type+" as "+value+" is equal to Batch "+type);
	}
	
	
	public void clickSaveButton()
	{
		isElementDisplayed("btn_save");
		if(isBrowser("ie")|| isBrowser("internet explorer"))
			clickUsingXpathInJavaScriptExecutor(element("btn_save"));
		else
		    element("btn_save").click();
		logMessage("Step : Save button is clicked\n");
		
	}
	public List<String> enterDetailsForBatchProcessingAndClickSaveButton()
	{
		List<String> batchValues=new ArrayList<>();
		if(isBrowser("ie")|| isBrowser("internet explorer")){
			switchToFrame("iframe1");
		}
		else
		    switchToFrame(element("iframe1"));
		wait.hardWait(2);
		String batchTotal=getBatchTotalAmount();
		String batchCount=getBatchTotalCount();
		batchValues.add(batchTotal);
		batchValues.add(batchCount);
		fillControlDetailsEqualToBatchDetails("Total",batchTotal);
		fillControlDetailsEqualToBatchDetails("Count",batchCount);
		clickSaveButton();
		switchToDefaultContent();
		return batchValues;
	}
	
	public void verifyDetailsOnBatchSummaryInfo(String type,String batchTotal)
	{
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(4);
		Assert.assertTrue(pollTextForIe(type,batchTotal));
//		Assert.assertTrue(elements("txt_batchDetailsSummary",type).get(0).getText().equals(
//				elements("txt_batchDetailsSummary",type).get(1).getText()));
		logMessage("ASSERT PASSED : Control "+type+" on batch summary info is equal to Batch "+type);
	}
	

	public void clickOnBatchProcessButton(String btnName) {
		isElementDisplayed("btn_ForProcesingBatch",btnName);
		String current = driver.getWindowHandle();
		executeJavascript("window.oldConfirm = window.confirm; window.confirm = function(){return true;}");	
		if(isBrowser("ie")||isBrowser("internetexplorer")){
			clickUsingXpathInJavaScriptExecutor(element("btn_ForProcesingBatch",btnName));
			wait.hardWait(2);
            //logMessage("Step : Switched to Pop Up Window, title is verified as "+getPageTitle());
	    }
		else{
		    element("btn_ForProcesingBatch",btnName).click();
		    logMessage("Step : "+btnName+" is clicked on batch processing page\n");	
		} 
		switchWindow(current);
	}
	
	public void switchWindow(String current){
		Set<String> handles = driver.getWindowHandles();
 		for(String handle : handles){
 			if(!(handle.equalsIgnoreCase(current))){
 					driver.switchTo().window(handle);
 					break;
 			}
 		}  		
        System.out.println("In "+getPageTitle());
        driver.switchTo().window(current);
	}
	
	public boolean pollTextForIe(String type,String batchValue){
		boolean flag=false;
		int pollingTimeOut=Integer.parseInt(ConfigPropertyReader.getProperty("pollingTimeOut"));
		for(int i=1;i<=pollingTimeOut;i++){
			try {
				Thread.sleep(1000);
			if(elements("txt_batchDetailsSummary",type).get(0).getText().equals(batchValue)){
				flag=true;
				break;
			}
			} catch (InterruptedException e) {
				logMessage("STEP : Interrupted exception" );
			}
		}
		return flag;	
	}

	public void verifyPopUpWindowVisibility() {
		SwitchToPopUpWindowAndVerifyTitle();
		//wait.waitForPageToLoadCompletely();
		
	}

	public void clickOnBatchProcessButtonsAndVerifyPopUpWindowAppears() {
        clickOnBatchProcessButton("PreProcess");
		//verifyPopUpWindowVisibility();
		clickOnBatchProcessButton("CloseButton");    
//		waitForAlertToAppear();
//		verifyPopUpWindowVisibility();
		clickOnBatchProcessButton("PostButton");
//		verifyPopUpWindowVisibility();
		clickOnBatchProcessButton("ACSBatchSalesTaxButton");	
	}

	public void verifyBatchDetailsAfterProcessing() {
		verifyBatchSaleRequestAs("sale request");
		verifyCloseDateAndPostDateContainsCurrentSystemDate();
		verifyClosedByAndPostByDateNotEmpty("close");
		verifyClosedByAndPostByDateNotEmpty("post");
		
	}

	private void verifyClosedByAndPostByDateNotEmpty(String fieldName) {
		isElementDisplayed("txt_postedClosedUserDate",fieldName);
		System.out.println(element("txt_postedClosedUserDate",fieldName).getText().length());
		Assert.assertTrue(element("txt_postedClosedUserDate",fieldName).getText().length()!=1,fieldName+" date is null");
		logMessage("ASSERT PASSED : "+fieldName+" date is not empty\n");
		
	}

	private void verifyCloseDateAndPostDateContainsCurrentSystemDate() {
		isElementDisplayed("txt_closeDateAndSaleRequest","close date");
		isElementDisplayed("txt_postDate");
		System.out.println(element("txt_closeDateAndSaleRequest","close date").getText().trim());
		System.out.println(DateUtil.getCurrentdateInStringWithGivenFormate("M/d/YYYY"));
		Assert.assertTrue(element("txt_closeDateAndSaleRequest","close date").getText().trim().contains(DateUtil.getCurrentdateInStringWithGivenFormate("M/d/YYYY")));
		logMessage("ASSERT PASSED : Batch close date contains current Date as "+element("txt_closeDateAndSaleRequest","close date").getText().trim());
		Assert.assertTrue(element("txt_postDate").getText().trim().contains(DateUtil.getCurrentdateInStringWithGivenFormate("M/d/YYYY")));
		logMessage("ASSERT PASSED : Batch post date contains current Date as "+element("txt_closeDateAndSaleRequest","close date").getText().trim());
		
	}

	private void verifyBatchSaleRequestAs(String field) {
		isElementDisplayed("txt_closeDateAndSaleRequest",field);
		wait.hardWait(5);
		Assert.assertTrue(element("txt_closeDateAndSaleRequest",field).getText().equalsIgnoreCase("Yes"),"Batch Sale request is not Yes");;
		logMessage("ASSERT PASSED : Batch Sale Request is displayed as Yes\n");
		
	}

	public String enterFieldsToAddNewBatch() {
		String batchName;
		isElementDisplayed("inp_batchAddFields","batch name");
		batchName="Selenium_Batch_Processing_Test"+System.currentTimeMillis();
		EnterTextInField(element("inp_batchAddFields","batch name"),batchName);
		selectProvidedTextFromDropDown(element("drpdwn_securitygroup","security group"), "QA");
		clickSaveButton();
		return batchName;
		
	}
	
	

}
