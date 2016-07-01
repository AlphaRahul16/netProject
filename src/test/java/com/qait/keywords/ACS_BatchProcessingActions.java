package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
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
		isElementDisplayed("btn_edit");
		element("btn_edit").click();
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
		element("inp_control"+type).click();
		element("inp_control"+type).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		element("inp_control"+type).sendKeys(value);
		logMessage("Step : Control "+type+" as "+value+" is equal to Batch "+type);
	}
	
	
	public void clickSaveButton()
	{
		isElementDisplayed("btn_save");
		element("btn_save").click();
		logMessage("Step : Save button is clicked\n");
		
	}
	public void enterDetailsForBatchProcessingAndClickSaveButton()
	{
		switchToFrame(element("iframe1"));
		fillControlDetailsEqualToBatchDetails("Total",getBatchTotalAmount());
		fillControlDetailsEqualToBatchDetails("Count",getBatchTotalCount());
		clickSaveButton();
		switchToDefaultContent();
	}
	
	public void verifyDetailsOnBatchSummaryInfo(String type)
	{
		System.out.println(elements("txt_batchDetailsSummary",type).get(0).getText());
		System.out.println(elements("txt_batchDetailsSummary",type).get(1).getText());
		Assert.assertTrue(elements("txt_batchDetailsSummary",type).get(0).getText().equals(
				elements("txt_batchDetailsSummary",type).get(1).getText()));
		logMessage("ASSERT PASSED : Control "+type+" on batch summary info is equal to Batch "+type);
	}
	

	public void clickOnBatchProcessButton(String btnName) {
		isElementDisplayed("btn_ForProcesingBatch",btnName);
		wait.hardWait(2);
		element("btn_ForProcesingBatch",btnName).click();
		logMessage("Step : "+btnName+" is clicked on batch processing page\n");
		
	}

	public void verifyPopUpWindowVisibility() {
		SwitchToPopUpWindowAndVerifyTitle();
		wait.waitForPageToLoadCompletely();
		
	}

	public void clickOnBatchProcessButtonsAndVerifyPopUpWindowAppears() {
        clickOnBatchProcessButton("PreProcess");
		verifyPopUpWindowVisibility();
		clickOnBatchProcessButton("CloseButton");
		waitForAlertToAppear();
		verifyPopUpWindowVisibility();
		clickOnBatchProcessButton("PostButton");
		verifyPopUpWindowVisibility();
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
