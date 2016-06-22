package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;

public class ACS_Reports {
	TestSessionInitiator test;
String app_url_IWEB;
static String sheetname;
private String caseID;
public Map<String,String> mapReport = new HashMap<String, String>();

public ACS_Reports() {
	sheetname=com.qait.tests.DataProvider_FactoryClass.sheetName = "Reports";
	System.out.println("SheetName "+sheetname);
}

@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
public ACS_Reports(String caseID) {
	this.caseID = caseID;
	System.out.println(caseID);
}

@Test
public void Step01()
{
	System.out.println(caseID);
	mapReport=test.homePageIWEB.addValuesInMap(sheetname, caseID);
	Iterator iterate = mapReport.keySet().iterator();
	while(iterate.hasNext())
	{
		String key = iterate.next().toString();
		System.out.println("Key "+key+" value "+mapReport.get(key));
	}
	test.homePageIWEB.clickOnModuleTab();
	test.homePageIWEB.clickOnTab("Reports");
	test.acsreportPage.selectModulesAndCategoryonReportCentralPage(mapReport);
}

@Test
public void Step02()
{
test.acsreportPage.clickGoReportButtonForReport(mapReport.get("Report Name"));
}



@AfterMethod
public void take_screenshot_on_failure(ITestResult result)
{
	test.takescreenshot.takeScreenShotOnException(result);


}
@BeforeClass
public void open_Browser_Window()
{
	test = new TestSessionInitiator(this.getClass().getSimpleName());
	app_url_IWEB =getYamlValue("app_url_IWEB");
	test.launchApplication(app_url_IWEB);
	
}

@AfterClass
public void close_Browser_Window()
{

	test.closeBrowserWindow();
}

}
