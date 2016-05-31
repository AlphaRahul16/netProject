package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;


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
	public void enterDetailsForBatchProcessing()
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
	
	public void clickButtonForBatchProcessing(String button)
	{
		isElementDisplayed("btn_ForProcesingBatch");
	    element("btn_ForProcesingBatch",button).click();
		wait.hardWait(4);
		changeWindow(1);
		System.out.println(getPageTitle());
	}
	
	

}
