package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_OMR_Discount_Test extends BaseTest{
	String app_url_IWEB, app_url_OMR;
	static String sheetname;
	private String caseID, invoiceNumber;
	Map<String, String> mapOMRDiscount = new HashMap<String, String>();
	private List<String> memDetails;
	Map<String, String> mapRenewedProductDetails;
	String Productname;

	public ACS_OMR_Discount_Test() {
		sheetname = com.qait.tests.DataProvider_FactoryClass.sheetName = "OMR_Discount";
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_OMR_Discount_Test(String caseID) {
		this.caseID = caseID;
	}
	
	@BeforeClass
	public void open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_OMR = getYamlValue("app_url_OMR");
		app_url_IWEB = getYamlValue("app_url_IWEB");
		test.launchApplication(app_url_IWEB);
		test.homePage.enterAuthentication(
				YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		System.out.println("Datasheet Name is "+sheetname);
	}
	
	@Test
	public void Step01_Launch_IWEB_Application_And_Navigate_To_Find_Members_Tab() {
		mapOMRDiscount = test.homePageIWEB.addValuesInMap(sheetname, caseID);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");

	}
	
	@Test(dependsOnMethods={"Step01_Launch_IWEB_Application_And_Navigate_To_Find_Members_Tab"})
	public void Step02_Select_Valid_User_For_Renewal_And_Verify_Term_Start_And_End_Dates_Is_Empty() {
		test.memberShipPage.selectValidUserForRenewalAccordingToCountry(mapOMRDiscount);
		Productname=test.individualsPage.verifyYearForOMRDiscountByPrice(mapOMRDiscount);
	}
	
	@Test(dependsOnMethods={"Step02_Select_Valid_User_For_Renewal_And_Verify_Term_Start_And_End_Dates_Is_Empty"})
	public void Step03_Verify_Payment_Status_And_Invoice_Details_Before_Renewal() {
		test.individualsPage.clickGotoRecordForRenewal();
		invoiceNumber = test.invoicePage.verifyInvoiceDetailsBeforeRenewal();
		test.invoicePage.expandDetailsMenu("line items");
		test.individualsPage.verifyOverrideStatusForOMRDiscount(Productname);

	}
	
	@Test(dependsOnMethods={"Step03_Verify_Payment_Status_And_Invoice_Details_Before_Renewal"})
	public void Step04_Navigate_to_Membership_Page_And_Fetch_Member_Details() {
		test.memberShipPage.clickOnCustomerNameAndNavigateToMembershipPage();
		memDetails = test.memberShipPage.getCustomerAllDetails(invoiceNumber);
	}
	

}
