package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class ACS_ReportsActions extends ASCSocietyGenericPage  {
	static String pagename = "ACS_Reports";
	WebDriver driver;
	int timeOut;

	public ACS_ReportsActions(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
	} 
	

	public void selectModulesAndCategoryonReportCentralPage(String Module, String Category,String DeliveryMethod) {
		hardWaitForIEBrowser(5);
	    selectDropdownOptions(Module,"Module");
	    selectDropdownOptions(Category,"Category");
	    selectDropdownOptions(DeliveryMethod,"Delivery Method");
		
	}


	private void selectDropdownOptions(String ReportData, String fieldName) { 
		
		isElementDisplayed("drpdwn_modulesAndCategory",fieldName);
		System.out.println(element("drpdwn_modulesAndCategory",fieldName));
		System.out.println(fieldName);
		selectProvidedTextFromDropDown(element("drpdwn_modulesAndCategory",fieldName), ReportData.trim());
		wait.hardWait(1);
		wait.waitForPageToLoadCompletely();
		logMessage("Step : "+fieldName+" is select from dropdown as "+ReportData);
		
	}


	public void clickGoReportButtonForReport(String reportName) {
		isElementDisplayed("txt_reportName",reportName);
		isElementDisplayed("btn_goReport",reportName);
		hardWaitForIEBrowser(3);
		element("btn_goReport",reportName).click();
		logMessage("Step : Go Button for Report "+reportName+" is clicked\n");
		
	}


	public void verifyReceivedReport(String DeliveryMethod,String reportHeading,String current) {
		wait.hardWait(3);
		if(DeliveryMethod.equalsIgnoreCase("Run Immediately"))
		{
//		changeWindow(1);
		switchToWindowHavingIndex(1);
		isElementDisplayed("txt_reportResult",reportHeading);
		isElementDisplayed("tbl_report");
		logMessage("Step : Table is displayed in report result");
		Assert.assertTrue(element("txt_reportResult",reportHeading).isDisplayed(),"Report Status is fail");
		logMessage("ASSERT PASSED  : Report is successfully received\n");
		driver.close();
		wait.hardWait(5);
//		switchToWindowHavingIndex(0);
		if(isBrowser("ie")||(isBrowser("internetexplorer"))){
			driver.switchTo().window(current);
			}
		else
			changeWindow(0);
		}	
	}
	
	private void enterEmailDetails(String fieldName,String fielddata)
	{
		isElementDisplayed("inp_Email",fieldName);
		element("inp_Email",fieldName).click();
		element("inp_Email",fieldName).clear();
		element("inp_Email",fieldName).sendKeys(fielddata);
		logMessage("Step : "+fieldName+" is entered as "+fielddata);
	}
	
	private void clickGoButton()
	{
		isElementDisplayed("btn_Go");
		element("btn_Go").click();
		logMessage("Step : Go button is clicked\n");
	}
	
	public String enterEmailDetailsForScheduleReport(String Deliverytype,String fielddata_To,String fielddata_Subject)
	{
		String current = "";
		if(Deliverytype.equalsIgnoreCase("Schedule Report"))
		{
			if(isBrowser("ie")||(isBrowser("internetexplorer"))){
			current= driver.getWindowHandle();
			switchWindow(current);
			}
			else
			   changeWindow(1);
			enterEmailDetails("E-mail To",fielddata_To);
			enterEmailDetails("E-mail Subject",fielddata_Subject);
			clickGoButton();
		}
		return current;
			
	}
}
