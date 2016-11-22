package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.DateUtil;

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

	public String createBatch(int index1,int index2,String group){
		String batchName;
		hardWaitForIEBrowser(5);
		switchToIframe1();
		isElementDisplayed("link_addbatch");
		clickUsingXpathInJavaScriptExecutor(element("link_addbatch"));
		logMessage("STEP : Adding a new batch\n");
		switchToDefaultContent();
		switchToFrame("iframe2");
		batchName=enterBatchName(index1);
		enterSecurityGroup(index2,group);
		clickOnSaveButton();
		wait.hardWait(1);
		return batchName;
	}

	public String createBatchForRefunds(int index1,int index2,String group)
	{
		String batchName;

		isElementDisplayed("link_addbatch");
		clickUsingXpathInJavaScriptExecutor(element("link_addbatch"));
		logMessage("STEP : Adding a new batch\n");
		switchToDefaultContent();
		switchToIframe1();
		batchName=enterBatchName(index1);
		enterSecurityGroup(index2,group);
		clickOnSaveButton();
		return batchName;

	}
	public void switchToIframe1()
	{
		switchToFrame("iframe1");

	}

	public void switchToIframe2()
	{
		switchToFrame("iframe2");

	}

	public String enterBatchName(int i){
		hardWaitForIEBrowser(4);
		isElementDisplayed("txt_batchDetails",String.valueOf(i));
		hardWaitForIEBrowser(2);
		element("txt_batchDetails",String.valueOf(i)).clear();
		String batchName="SELENIUM_BATCH"+System.currentTimeMillis();
		//		sendKeysUsingXpathInJavaScriptExecutor(element("txt_batchDetails",String.valueOf(i)), batchName);
		element("txt_batchDetails",String.valueOf(i)).sendKeys(batchName);
		logMessage("STEP : Batch name is entered as "+batchName+"\n");
		return batchName;
	}

	public void enterSecurityGroup(int index,String securityGroup){
		isElementDisplayed("drpdwn_securityGroup",String.valueOf(index));
		Select drpdwn_security= new Select(element("drpdwn_securityGroup",String.valueOf(index)));
		drpdwn_security.selectByVisibleText(securityGroup);
		logMessage("STEP : Security Group entered as "+securityGroup+"\n");
	}

	public void clickOnSaveButton(){
		wait.hardWait(6);
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("btn_save");
//		element("btn_save").click();
		clickUsingXpathInJavaScriptExecutor(element("btn_save"));
		logMessage("STEP : Clicked on Save button\n");
		switchToDefaultContent();
	}
	
	public void clickOnSaveButtonAndHandelAlert(){
		wait.hardWait(6);
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("btn_save");
		element("btn_save").click();
		waitForAlertToAppear();
		//clickUsingXpathInJavaScriptExecutor(element("btn_save"));
		logMessage("STEP : Clicked on Save button\n");
		switchToDefaultContent();
	}
	

	public void enterActionValues(String actionValue){   
		int j=2,size;
		switchToFrame("iframe1");
		isElementDisplayed("table_actions");
		size=elements("table_actions").size();
		hardWaitForIEBrowser(5);
		for(int i=1;i<size;i++){
			isElementDisplayed("drpdwn_invoiceAction",String.valueOf(j));
			wait.hardWait(2);
			hardWaitForIEBrowser(3);
			selectProvidedTextFromDropDown(element("drpdwn_invoiceAction",String.valueOf(j)), actionValue);
		   wait.hardWait(1);
			logMessage("STEP : Action value entered as "+actionValue+" for product "+i+"\n");
		   j++;
		}
	}

	public void verifyProductUnderLineItems(String productName,int index){
		boolean flag=false;int i=0;
		wait.hardWait(3);
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
		wait.hardWait(5);
		hardWaitForIEBrowser(4);

		try
		{
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
		isElementDisplayed("txt_voidInvoice");
		}
		catch(NoSuchElementException e)
		{
			saveChangesForReturnCancel();
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
		Assert.assertEquals(element("txt_voidInvoice").getText().trim(), msg,"ASSERT PASSED : Message '"+msg+"' is not displayed\n");
		logMessage("ASSERT PASSED : Message '"+msg+"' is displayed\n");
	}

	public void verifyItemsUnderVoidedLineItemsMenu(List<String>expectedProductList,int index){
		boolean flag=false;
		wait.hardWait(2);
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
		logMessage("ASSERT PASSED : Message '"+msg+"' is displayed\n");
	}

	public void verifyNotAMember(String index,String memberField){
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(5);
		isElementDisplayed("img_memberBenefits",index);
		logMessage("ASSERT PASSED : "+memberField+" field is closed\n");
	}

	public void waitForSpinner() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			handleAlert();
			isElementDisplayed("img_spinner");
			wait.waitForElementToDisappear(element("img_spinner"));
			logMessage("STEP : Wait for spinner to be disappeared \n");

		} catch (Exception Exp) {

			logMessage("STEP : Spinner is not present \n");
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
	}

	public void verifyCrediHasBeenAddedToList(String batchName,String totalCredit) {

		for (WebElement ele : elements("txt_creditbatchName")) {
			if(ele.getText().equals(batchName))
			{
				Assert.assertTrue(ele.getText().equals(batchName),"Batch Name is not present in the list");
				logMessage("ASSERT PASSED : Batch name is present in the list as "+batchName);
			}
		}

		Assert.assertTrue(element("txt_creditBatchDate",batchName).getText().trim().equals(DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/YYY","EST").trim()));
		logMessage("STEP : Credit date is verify as "+DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/YYY","EST"));
		Assert.assertTrue(element("txt_CreditTotal",batchName).getText().trim().equals(totalCredit),"Amount credit is not as expected");
		logMessage("ASSERT PASSED : Credited amount successfully verified as "+totalCredit);
	}

	public void saveChangesForReturnCancel() {
		switchToDefaultContent();
		switchToFrame("iframe1");
		clickOnSaveButton();

	}

	public void clickOnGotoCreditRecord(String batchName) {

		isElementDisplayed("btn_gotoCreditRecord",batchName);
		element("btn_gotoCreditRecord",batchName).click();
		logMessage("STEP : Goto Record is clicked for "+batchName);
	}

	public void NavigateToBatchProfilePageByClickingOnBatchName(String batchName)
	{
		isElementDisplayed("lnk_batchName",batchName);
		element("lnk_batchName",batchName).click();
		logMessage("STEP : batch Name is clicked as "+batchName);
	}

	public void enterCreditFromAndTooDates(String dateType,int days) {
		isElementDisplayed("inp_creditDate",dateType);
		element("inp_creditDate",dateType).sendKeys(DateUtil.getAnyDateForType("MM/dd/YYYY", days, "date"));
		logMessage("STEP : credit "+ dateType+" date is entered as "+DateUtil.getAnyDateForType("MM/dd/YYYY", days, "date"));

	}

	public void clickSearchRefundsButton() {
		isElementDisplayed("btn_searchRefund");
		element("btn_searchRefund").click();
		logMessage("STEP : Search Refund button is clicked\n");
		wait.waitForPageToLoadCompletely();
	}
	
	public String createBatchForFundraising(int index1,int index2,String group){
		String batchName;
		hardWaitForIEBrowser(5);
//		switchToIframe1();
		isElementDisplayed("link_addbatch");
		clickUsingXpathInJavaScriptExecutor(element("link_addbatch"));
		logMessage("STEP : Adding a new batch\n");
		switchToDefaultContent();
		switchToFrame("iframe2");
		batchName=enterBatchName(index1);
		enterSecurityGroup(index2,group);
		clickOnSaveButton();
		wait.hardWait(1);
		return batchName;
	}

}
