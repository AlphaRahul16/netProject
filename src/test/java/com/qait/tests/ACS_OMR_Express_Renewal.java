package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_OMR_Express_Renewal extends BaseTest {
	String app_url_IWEB;
	private String expressURL, productDetails, customername;
	Map<String, String> mapRenewedProductDetails =  new HashMap<String, String>();
	
	@BeforeClass
	public void open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IWEB");
		test.launchApplication(app_url_IWEB);
		test.homePage.enterAuthentication(
				YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
	}
	
	@Test
	public void Step01_Launch_IWEB_Application_And_Navigate_To_Find_Members_Tab() {
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.homePageIWEB.clickOnTab("Query Individual");
		test.memberShipPage.selectAndRunQuery("Selenium - BP Renewal Express Url");
		test.memberShipPage.enterExpiryDatesBeforeAndAfterExpressRenewal();
		test.memberShipPage.clickOnGoButtonAfterPackageSelection();
		expressURL=test.memberShipPage.fetchExpressURLForRenewal();
	}
	
	@Test
	public void Step02_Launch_IWEB_Application_And_Navigate_To_Find_Members_Tab() {
	  test.launchApplication(expressURL);
	  test.asm_OMR.verifyUserIsOnExpressRenewalHomepage("Online Membership Renewal Express");
	  test.asm_OMR.switchToEwebRenewalFrame();
	  customername=test.asm_OMR.getMemberNameFromOMRHomepage();
	  test.asm_OMR.verifyUserCompleteAddressIsNotDisplayedOnHomePage();
	  mapRenewedProductDetails=test.asm_OMR.saveProductsWithRespectiveRenewalAmount();
	}

	@Test
	public void Step03_Submit_Payment_Details_And_Verify_Renewal_Summary_On_CheckoutPage() {
		test.asm_OMR.submitPaymentDetailsForAutoRenewal(YamlReader.getYamlValue("creditCardInfo.Type"),
				(customername.split(" ")[0]+" "+customername.split(" ")[1]),
				YamlReader.getYamlValue("creditCardInfo.Number"), YamlReader.getYamlValue("creditCardInfo.cvv-number"),
				YamlReader.getYamlValue("creditCardInfo.CreditCardExpiration").split("\\/")[0],
				YamlReader.getYamlValue("creditCardInfo.CreditCardExpiration").split("\\/")[1]);

		test.asm_OMR
				.verifyRenewedProductsSummaryOnCheckOutPage(mapRenewedProductDetails);
		test.asm_OMR.clickOnSubmitPayment();
		test.asm_OMR.verifyPrintReceiptMessageAfterPayment();
	}
	
}
