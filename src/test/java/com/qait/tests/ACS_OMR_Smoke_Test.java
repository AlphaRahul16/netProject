package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.omg.CORBA.OMGVMCID;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;

public class ACS_OMR_Smoke_Test {
	TestSessionInitiator test;
	String app_url_IWEB,app_url_OMR;
	static String sheetname;
	private String caseID,invoiceNumber;
	Map<String,String> mapOMR=new HashMap<String,String>();
	private List<String> memDetails;
	Map<String,String> mapRenewedProductDetails;

	public ACS_OMR_Smoke_Test() {
		sheetname=com.qait.tests.DataProvider_FactoryClass.sheetName = "OMR";
		System.out.println("SheetName"+sheetname);
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_OMR_Smoke_Test(String caseID) {
		this.caseID = caseID;

	}

	@Test
	public void Step01_TC01_launch_Iweb_And_Fetch_Member_Details()
	{
		mapOMR=test.homePageIWEB.addValuesInMap(sheetname, caseID);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
		test.homePageIWEB.clickOnSideBarTab("Members");
		test.memberShipPage.selectValidUserForRenewal(mapOMR);
		test.memberShipPage.verifyTermStartDateAndEndDatesAreEmpty(mapOMR);
		test.invoicePage.verifyPaymentStatusBeforeRenewal();	
		test.individualsPage.clickGotoRecordForRenewal();
		invoiceNumber=test.invoicePage.verifyInvoiceDetailsBeforeRenewal();
		test.memberShipPage.clickOnCustomerNameAndNavigateToMembershipPage();
		memDetails=test.memberShipPage.getCustomerAllDetails(invoiceNumber);

	}

	@Test
	public void Step02_TC01_launch_Eweb_And_Login_With_Valid_Credentials()
	{
		
		test.launchApplication(app_url_OMR);
		test.asm_OMR.loginIntoApplicationWithValidChoice(mapOMR,memDetails);
		test.asm_OMR.OMRLogo("Online Membership Renewal");
		test.asm_OMR.verifyWelcomePage();
		
		test.asm_OMR.FillRequiredDetailsForStudentMember(mapOMR);
		mapRenewedProductDetails=test.asm_OMR.addMembershipsForRegularMember(mapOMR);
//		Iterator iterator = mapRenewedProductDetails.keySet().iterator();
//
//		while (iterator.hasNext()) {
//		   String key = iterator.next().toString();
//		   String value = mapRenewedProductDetails.get(key);
//
//		   System.out.println(key + " " + value);
//		}
		test.asm_OMR.submitPaymentDetails(mapOMR.get("CreditCard_Type"),(memDetails.get(0).split(" ")[1]+" "+memDetails.get(0).split(" ")[0]), mapOMR.get("CreditCard_Number")
				, mapOMR.get("CreditCard_CVV_Number"), mapOMR.get("CreditCardExpiration_Month"), mapOMR.get("CreditCardExpiration_Year"));
		test.asm_OMR.verifyRenewedProductsSummaryOnCheckOutPage(mapRenewedProductDetails);
		test.asm_OMR.clickOnSubmitPayment();
		test.asm_OMR.verifyPrintReceiptMessageAfterPayment();
		//test.asm_OMR.verifyMembershipRenewedAgainLoginIntoApplication(mapOMR,memDetails);
	}
	
	@Test
	public void Step03_TC01_launch_Iweb_And_Verify_Renewal_Details()
	{

		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.clickOnSideBarTab("Invoice");
		test.memberShipPage.clickOnSideBar("Find Invoice");
		
		test.individualsPage.enterFieldValue("Invoice Code",memDetails.get(2));
		//test.individualsPage.enterFieldValue("Invoice Code","16294437");
		
		test.individualsPage.clickGoButton();
		test.invoicePage.verifyInvoiceDetailsAfterRenewal();
		test.invoicePage.expandDetailsMenu("line items");
		test.invoicePage.verifyRenewedProductsPriceInsideLineItems(mapRenewedProductDetails);
		test.invoicePage.collapseDetailsMenu("line items");
	
		test.invoicePage.verifyAdjustedLinesItemsForEmeritusMember(mapOMR.get("Member_Status?"),mapRenewedProductDetails);
		
		test.memberShipPage.clickOnCustomerNameAndNavigateToMembershipPage();
		test.invoicePage.expandDetailsMenu("individual memberships");
		test.memberShipPage.navigateToInvoicePageForFirstProduct();
		test.invoicePage.verifyPaymentStatusAfterRenewal(mapOMR.get("Member_Status?"));
		test.invoicePage.expandDetailsMenu("invoices");
		test.memberShipPage.verifyTermStartDateAndEndDatesAreNotEmpty();
		test.invoicePage.collapseDetailsMenu("invoices");
	}


	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result)
	{
		test.takescreenshot.takeScreenShotOnException(result);
		test.closeBrowserSession();

	}
	@BeforeClass
	public void open_Browser_Window()
	{
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_OMR = getYamlValue("app_url_OMR");
		app_url_IWEB =getYamlValue("app_url_IWEB");
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"), 
				YamlReader.getYamlValue("Authentication.password"));
		System.out.println(sheetname);
		mapOMR=test.homePageIWEB.addValuesInMap(sheetname, caseID);
	}

	//@AfterClass
	public void close_Browser_Window()
	{

		test.closeBrowserWindow();
	}
}
