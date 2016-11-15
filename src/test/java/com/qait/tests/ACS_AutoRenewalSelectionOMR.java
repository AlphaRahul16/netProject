package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;


public class ACS_AutoRenewalSelectionOMR extends BaseTest{


	String app_url_IWEB,app_url_OMR;
	String queryPageUrl;
	private List<String> memDetails;
	Map<String,String> mapRenewedProductDetails;


	@Test
	public void Step01_launch_Iweb_And_Select_Valid_User_For_AutoRenewal_In_OMR()
	{
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.homePageIWEB.clickOnTab("Query Individual");
		queryPageUrl=test.individualsPage.getCurrentURL();
		test.memberShipPage.selectValidUserForAutoRenewal(YamlReader.getYamlValue("ACS_AutoRenewal_Selection_In_OMR.AutoRenewal_Query"),queryPageUrl);
		

	}
	
	@Test
	public void Step02_Navigate_To_Individual_Page_And_Get_Customer_FullName_And_Contact_ID()
	{
		test.individualsPage.clickGotoRecordForRenewal();
		test.memberShipPage.clickOnCustomerNameAndNavigateToMembershipPage();
		memDetails=test.memberShipPage.getCustomerFullNameAndContactID();

	}

	@Test

	public void Step03_Launch_OMR_Eweb_And_Verify_OMR_Homepage_Is_Displayed()
	{

		test.launchApplication(app_url_OMR);
		test.asm_OMR.loginIntoOMRApplication(memDetails);
		test.asm_OMR.selectNoIfRegularToEmeritusPromptAppears();
		test.asm_OMR.OMRLogo("Online Membership Renewal");
		test.asm_OMR.verifyWelcomePage();
	}

	@Test

	public void Step04_Save_Respective_Products_Amounts_And_Submit_Payment_Details() {

		mapRenewedProductDetails = test.asm_OMR.saveProductsWithRespectiveRenewalAmount();
		test.asm_OMR.submitPaymentDetailsForAutoRenewal(YamlReader.getYamlValue("creditCardInfo.Type"),
				(memDetails.get(0).split(" ")[1] + " " + memDetails.get(0).split(" ")[0]),
				YamlReader.getYamlValue("creditCardInfo.Number"), YamlReader.getYamlValue("creditCardInfo.cvv-number"),
				YamlReader.getYamlValue("creditCardInfo.CreditCardExpiration").split("\\/")[0],
				YamlReader.getYamlValue("creditCardInfo.CreditCardExpiration").split("\\/")[1]);
		test.asm_OMR.clickOnSubmitPayment();

	}
	@Test
	public void Step05_Verify_Print_Receipt_Message_On_Summary_Page()
	{
		test.asm_OMR.verifyPrintReceiptMessageAfterPayment();
	}

	@Test
	public void Step06_Launch_Iweb_And_Navigate_To_Membership_Page()
	{
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.clickOnSideBar("Find Individual");
		test.individualsPage.enterFieldValue("Record Number",memDetails.get(1));
		test.individualsPage.clickGoButton();
		test.memberShipPage.expandDetailsMenu("individual memberships");
	}
	
	@Test
	public void Step07_Navigate_To_Invoice_For_Renewed_Product_And_Verify_AutoPay_Status_After_Renewal()
	{
		
	
		test.memberShipPage.navigateToInvoicePageForRenewedProduct();
		test.memberShipPage.verifyAutoPayStatusAfterAutoRenewal("chkmk");
	}
	
	@Test
	public void Step08_Expand_Stored_Payment_Information_And_Verify_Child_Form_Is_Populated()
	{
	
		test.memberShipPage.expandDetailsMenu("stored payment information");
		test.invoicePage.verifyStorePaymentInformationChildFormIsPopulated(memDetails.get(0).split(" ")[1]);
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
	}
	@BeforeMethod
	public void handleTestMethodName(Method method) {
		test.printMethodName(method.getName());
	}
}