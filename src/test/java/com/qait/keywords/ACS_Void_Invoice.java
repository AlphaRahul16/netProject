package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class ACS_Void_Invoice extends ASCSocietyGenericPage {

	static String pageName="ACS_Void_Invoice";
	int timeOut, hiddenFieldTimeOut;

	public ACS_Void_Invoice(WebDriver driver) {
		super(driver, pageName);
	}
	
	public void clickOnVoidInvoiceButton(String btnName,int index){
		isElementDisplayed("btn_invoiceAction",String.valueOf(index));
		clickUsingXpathInJavaScriptExecutor(element("btn_invoiceAction",String.valueOf(index)));
		logMessage("STEP : Clicked on "+btnName+" button\n");
	}
	
	public void verifyPageTitle(String heading){
		System.out.println("-----"+getPageTitle());
		Assert.assertTrue(heading.equals(getPageTitle()),"ASSERT FAILED : Page title "+heading+" is not verified\n");
	    logMessage("ASSERT PASSED : Page title "+heading+" is verified\n");
	}
	
	public void createBatch(int index1,int index2,String group){
		switchToFrame("iframe1");
		isElementDisplayed("link_addbatch");
		clickUsingXpathInJavaScriptExecutor(element("link_addbatch"));
		logMessage("STEP : Adding a new batch\n");
		enterBatchName(index1);
		enterSecurityGroup(index2,group);
		clickOnSaveButton();
	}
	public void enterBatchName(int i){
		switchToDefaultContent();
		switchToFrame("iframe2");
		isElementDisplayed("txt_batchDetails",String.valueOf(i));
		hardWaitForIEBrowser(2);
		element("txt_batchDetails",String.valueOf(i)).clear();
		String batchName="SELENIUM_BATCH"+System.currentTimeMillis();
//		sendKeysUsingXpathInJavaScriptExecutor(element("txt_batchDetails",String.valueOf(i)), batchName);
		element("txt_batchDetails",String.valueOf(i)).sendKeys(batchName);
		logMessage("STEP : Batch name is entered as "+batchName+"\n");
	}
	
	public void enterSecurityGroup(int index,String securityGroup){
		isElementDisplayed("drpdwn_securityGroup",String.valueOf(index));
		Select drpdwn_security= new Select(element("drpdwn_securityGroup",String.valueOf(index)));
		drpdwn_security.selectByVisibleText(securityGroup);
		logMessage("STEP : Security Group entered as "+securityGroup+"\n");
	}
	
	public void clickOnSaveButton(){
		wait.hardWait(2);
		isElementDisplayed("btn_save");
		clickUsingXpathInJavaScriptExecutor(element("btn_save"));
		logMessage("STEP : Clicked on Save button\n");
		switchToDefaultContent();
	}
	
	public void enterActionValues(String actionValue){   
		int j=2,size;
		switchToFrame("iframe1");
		isElementDisplayed("table_actions");
		size=elements("table_actions").size();
		for(int i=1;i<size;i++){
			isElementDisplayed("drpdwn_invoiceAction",String.valueOf(j));
			wait.hardWait(1);
			hardWaitForIEBrowser(3);
			selectProvidedTextFromDropDown(element("drpdwn_invoiceAction",String.valueOf(j)), actionValue);
		   logMessage("STEP : Action value entered as "+actionValue+" for product "+i+"\n");
		   j++;
		}
	}
	
	public void verifyProductUnderLineItems(String productName,int index){
		boolean flag=false;int i=0;
		isElementDisplayed("table_productName",String.valueOf(index));
		for(WebElement ele: elements("table_productName",String.valueOf(index))){
			if(ele.getText().trim().equals(productName)){
				flag=true;
				i++;
				break;
			}
		}
		Assert.assertTrue(flag,"ASSERT FAILED : Product "+productName+" is not present under Line Items menu\n");
		logMessage("ASSERT PASSED : Product "+productName+" is present under Line Items menu\n");
	}
	
	public List<String> getProductsUnderLineItemsMenu(int index){
		List<String> productList=new ArrayList<String>();
		isElementDisplayed("table_productName",String.valueOf(index));
		for(WebElement ele: elements("table_productName",String.valueOf(index))){
			productList.add(ele.getText().trim());
		}
		for (String str : productList) {
			System.out.println(str);
		}
		return productList;
	}
	
	public void verifyVoidInvoiceMessage(String msg){
		wait.waitForPageToLoadCompletely();
		waitForSpinner();
		hardWaitForIEBrowser(4);
		isElementDisplayed("txt_voidInvoice");
		Assert.assertEquals(element("txt_voidInvoice").getText().trim(), msg,"ASSERT PASSED : Message '"+msg+"' is not displayed\n");
		logMessage("ASSERT PASSED : Message '"+msg+"' is displayed\n");
	}
	
	public void verifyItemsUnderVoidedLineItemsMenu(List<String>expectedProductList,int index){
		boolean flag=false;
		isElementDisplayed("table_productName",String.valueOf(index));
		for(WebElement ele: elements("table_productName",String.valueOf(index))){
			for(int j=0; j<expectedProductList.size();j++){
				if(expectedProductList.get(j).contains(ele.getText().trim())){
					flag= true;
					break;
				}
			}
			Assert.assertTrue(flag,"ASSERT FAILED : "+ele.getText().trim()+" is not displayed under Adjusted/Voided Line Items\n");
			logMessage("ASSERT PASSED : "+ele.getText().trim()+" is displayed under Adjusted/Voided Line Items\n");
		}
	}
	
	public void verifyMessageUnderLineItemsMenu(String msg){
		isElementDisplayed("txt_emptyLineItems");
		Assert.assertEquals(element("txt_emptyLineItems").getText().trim(), msg,"ASSERT FAILED : Message "+msg+" is not displayed\n");
		logMessage("ASSERT PASSED : Message "+msg+" is displayed\n");
	}
	
	public void verifyNotAMember(String index,String memberField){
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(2);
		isElementDisplayed("img_memberBenefits",index);
		logMessage("ASSERT PASSED : "+memberField+" field is closed\n");
	}
	
	public void waitForSpinner() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
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

}
