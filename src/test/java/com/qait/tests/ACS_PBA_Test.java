package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;

public class ACS_PBA_Test {

	TestSessionInitiator test;
	String app_url_IWEB, individualName, webLogin, app_url_PUBS,
			passportAmountValue, subscriptionsAmountValue, totalAmount,customerId;

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IWEB");
		app_url_PUBS = getYamlValue("app_url_PUBS");
	}

	@Test
	public void Step01_Launch_Iweb_Application_And_Verify_User_Is_On_Home_Page() {
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(
				YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB
				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test
	public void Step02_Select_And_Run_Query_And_Verify_User_Is_On_Individual_Profile_Page() {
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.clickOnTab("Query Individual");
		test.memberShipPage.selectAndRunQuery(getYamlValue("PBA.queryName"));
		individualName = test.acsAddressValidation
				.verifyIndividualProfilePage();
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Individuals | "
				+ individualName);
	}

	@Test
	public void Step03_Fetch_WebLogin_And_Customer_Id_From_Individual_Profile_Page_And_Launch_Eweb_PBA_Application_Verify_User_Is_On_Eweb_Login_Page() {
		webLogin = test.memberShipPage.getMemberWebLogin();
		customerId=test.memberShipPage.getCustomerID();
		test.launchApplication(app_url_PUBS);
		test.asm_PUBSPage.verifyUserIsOnEwebLoginPage();
	}

	@Test
	public void Step04_Login_To_Eweb_Application_Using_WebLogin_And_Password_And_Verify_User_Is_On_Home_Page_Of_Eweb_PBA() {
		test.asm_PUBSPage.loginInToApplication(webLogin, getYamlValue("password"));
		test.asm_PUBSPage.verifyUserIsOnHomePageForEwebPBA(individualName);
	}

	@Test
	public void Step05_Add_Product_For_EPassport_And_ESubscriptions_And_Verify_Total_Amount_For_Added_Products() {
		test.asm_PUBSPage.clickOnAddAnEPassportButton();
		test.asm_PUBSPage.clickOnAddButtonInPassport();
		passportAmountValue = test.asm_PUBSPage.passportValue();
		test.asm_PUBSPage.clickOnSaveButton();
		test.asm_PUBSPage.clickOnAddAnESubscriptionButton();
		test.asm_PUBSPage.clickOnFirstAddButton();
		subscriptionsAmountValue = test.asm_PUBSPage.subscriptionValue();
		test.asm_PUBSPage.clickOnSaveButton();
		test.asm_PUBSPage.SavingProductNameAndAmount();
		test.asm_PUBSPage.verifyTotalAmountForAddedProducts();
	}
	
	@Test
	public void Step06_Fill_Billing_Information_And_Place_Order() {
		test.asm_PUBSPage.submitPaymentDetails(
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.paymentMethod"),
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.holderName"),
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.cardNumber"),
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.cvvNumber"),
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.yearValue"));
		test.asm_PUBSPage.clickOnPlaceOrder();
	}
	
	@Test
	public void Step07_Verify_ProductName_And_ProductAmount_With_Downloaded_PDF_Receipt()
	{
	test.asm_PUBSPage.clockOnPrintOrderReceipt();
	test.asm_PUBSPage.verifyDataFromPdfFile();
	}
	
	@Test
	public void Step08_Verify_Selected_Products_With_Amount_On_Iweb_Application()
	{
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB
		.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.clickOnTab("Find Individual");
		test.asm_PUBSPage.clickOnGoButtonWithCustomerLoginId(customerId);
		test.asm_PUBSPage.clickOnActiveSubscription();
		test.asm_PUBSPage.verifyDataFromInitialPage();
	}
	
	
	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result)
	{
		test.takescreenshot.takeScreenShotOnException(result);
	}
	
	//@AfterClass
	public void Close_Browser_Session() {
		test.closeBrowserWindow();
	}
	
	
	
	
	
	

}
