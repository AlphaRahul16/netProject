package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.XlsReader;
import com.qait.automation.utils.YamlReader;

public class ACS_AACT_OMR extends BaseTest{

 private String caseID;
 public String contactID,weblogin;
 private String[] memDetails;
 int numberOfDivisions, numberOfSubscriptions;
 String app_url_IWEB = getYamlValue("app_url_IWEB");
 HashMap<String, String> dataList = new HashMap<String, String>();
// String app_url_AACT_OMR = getYamlValue("app_url_AACT_OMR");
String  individualName;
List<String> customerFullNameList;
 

 public ACS_AACT_OMR() {
  com.qait.tests.DataProvider_FactoryClass.sheetName = "AACT_OMR";
 }

 @BeforeClass
	public void Start_Test_Session() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		
 	}
 
 @BeforeMethod
 public void printCaseIdExecuted(Method method) {
  test.printMethodName(method.getName());
  if (caseID != null) {
   Reporter.log(" TEST CASE ID : " + caseID + " \n", true);
  }
 }

 @Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
 public ACS_AACT_OMR(String caseID) {
  this.caseID = caseID;
 }
 
 @Test
 public void Step01_Launch_IWEB_Application() {
  
  test.homePageIWEB.addValuesInMap("AACT_OMR", caseID);
  test.launchApplication(app_url_IWEB);
  //test.homePage.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");

 }
 
 @Test
 public void Step02_Open_Membership_Page() {
  
	  	test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
		test.homePageIWEB.clickOnSideBarTab("Members");
		test.homePageIWEB.clickOnTab("Query Membership");
		test.homePageIWEB.verifyUserIsOnHomePage("Query - Membership");

 }
 
 @Test
 public void Step03_Select_And_Run_Query_In_Membership_Page(){

	    test.memberShipPage.selectAndRunQuery(getYamlValue("AACT_OMR.queryName"));
	    test.memberShipPage.enterExpiryDatesBeforeAndAfterExpressRenewal();
	    test.memberShipPage.clickOnGoButtonAfterPackageSelection();
 }

 @Test
 public void Step04_Fetch_CstWebLogin_And_Verify_TermStartDate_And_TermEndDate() {

	 weblogin=test.memberShipPage.getCstWebLogin();
	 test.memberShipPage.expandDetailsMenu("invoices");
	 test.memberShipPage.verifyTermStartDateAndTermEndDateIsEmptyForAACT();
	 test.memberShipPage.collapseDetailsMenu("invoices");
 }

}