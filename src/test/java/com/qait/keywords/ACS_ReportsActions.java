package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.Iterator;
import java.util.Map;

import org.openqa.selenium.WebDriver;

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
	

	public void selectModulesAndCategoryonReportCentralPage(Map<String, String> mapReport) {
		
	    selectDropdownOptions(mapReport,"Module");
	    selectDropdownOptions(mapReport,"Category");
	    selectDropdownOptions(mapReport,"Delivery Method");
		
	}


	private void selectDropdownOptions(Map<String, String> mapReport, String fieldName) { 
		
		isElementDisplayed("drpdwn_modulesAndCategory",fieldName);
		System.out.println(element("drpdwn_modulesAndCategory",fieldName));
		System.out.println(mapReport.get(fieldName.trim()));
		System.out.println(fieldName);
		selectProvidedTextFromDropDown(element("drpdwn_modulesAndCategory",fieldName), mapReport.get(fieldName.trim()));
		wait.hardWait(1);
		wait.waitForPageToLoadCompletely();
		logMessage("Step : "+fieldName+" is select from dropdown as "+mapReport.get(fieldName));
		
	}


	public void clickGoReportButtonForReport(String reportName) {
		isElementDisplayed("txt_reportName",reportName);
		isElementDisplayed("btn_goReport",reportName);
		click(element("btn_goReport",reportName));
		
	}
}
