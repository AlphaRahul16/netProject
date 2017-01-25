package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_PBA_Test extends BaseTest{

	List<String> memDetails;
	String app_url_IWEB, individualName, webLogin, app_url_PUBS, passportAmountValue, subscriptionsAmountValue,
			totalAmount, customerId;
	String fName,LName;
	

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IWEB");
		app_url_PUBS = getYamlValue("app_url_PUBS");
	}
	
	@BeforeMethod
	public void handleTestMethodName(Method method) {
		test.printMethodName(method.getName());
	}
	
	@Test
	public void Step01_Launch_Iweb_Application_And_Verify_User_Is_On_Home_Page() {
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test(dependsOnMethods="Step01_Launch_Iweb_Application_And_Verify_User_Is_On_Home_Page")
	public void Step02_Select_And_Run_Query_And_Verify_User_Is_On_Individual_Profile_Page() {
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.clickOnTab("Query Individual");
		test.memberShipPage.selectAndRunQuery(getYamlValue("PBA.queryName"));
		individualName = test.acsAddressValidation.verifyIndividualProfilePage();
		memDetails = test.memberShipPage.getCustomerFullNameAndContactID();
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Individuals | " + individualName);
	}

	@Test(dependsOnMethods="Step02_Select_And_Run_Query_And_Verify_User_Is_On_Individual_Profile_Page")
	public void Step03_Fetch_WebLogin_And_Customer_Id_From_Individual_Profile_Page_And_Launch_Eweb_PBA_Application_Verify_User_Is_On_Eweb_Login_Page() {
		webLogin = test.memberShipPage.getMemberWebLogin();
		customerId = test.memberShipPage.getCustomerID();
		test.launchApplication(app_url_PUBS);
		test.asm_PUBSPage.verifyUserIsOnEwebLoginPage();
	}

	@Test(dependsOnMethods="Step03_Fetch_WebLogin_And_Customer_Id_From_Individual_Profile_Page_And_Launch_Eweb_PBA_Application_Verify_User_Is_On_Eweb_Login_Page")
	public void Step04_Login_To_Eweb_Application_Using_WebLogin_And_Password_And_Verify_User_Is_On_Home_Page_Of_Eweb_PBA() {
		test.asm_PUBSPage.loginInToApplication(webLogin, "password");
		System.out.println("individual =  " + individualName);
		test.asm_PUBSPage.verifyUserIsOnHomePageForEwebPBA(individualName);
	}

	@Test(dependsOnMethods="Step04_Login_To_Eweb_Application_Using_WebLogin_And_Password_And_Verify_User_Is_On_Home_Page_Of_Eweb_PBA")
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

	@Test(dependsOnMethods="Step05_Add_Product_For_EPassport_And_ESubscriptions_And_Verify_Total_Amount_For_Added_Products")
	public void Step06_Fill_Billing_Information_And_Place_Order() {
		test.asm_PUBSPage.submitPaymentDetails(YamlReader.getYamlValue("Acs_CreateMember_IWEB.paymentMethod"),memDetails.get(0).split(" ")[1]+" "+memDetails.get(0).split(" ")[0],
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.cardNumber"),
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.cvvNumber"),
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.yearValue"));
		test.asm_PUBSPage.clickOnPlaceOrder();
	}

	@Test(dependsOnMethods="Step06_Fill_Billing_Information_And_Place_Order")
	public void Step07_Verify_ProductName_And_ProductAmount_With_Downloaded_PDF_Receipt() throws IOException {
		test.asm_PUBSPage.clickOnPrintOrderReceipt();
		test.asm_PUBSPage.verifyDataFromPdfFile();
	}


	@Test(dependsOnMethods="Step07_Verify_ProductName_And_ProductAmount_With_Downloaded_PDF_Receipt")
	public void Step08_Verify_Selected_Products_With_Amount_On_Iweb_Application() {
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.clickOnTab("Find Individual");
		test.asm_PUBSPage.clickOnGoButtonWithCustomerLoginId(customerId);
		test.asm_PUBSPage.clickOnActiveSubscription();
		test.asm_PUBSPage.verifyDataFromInitialPage();
	}

	

}
