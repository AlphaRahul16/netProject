package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;

public class ACS_pba {

	TestSessionInitiator test;
	String app_url_IWEB, individualName, webLogin, app_url_PUBS,
			passportAmountValue, subscriptionsAmountValue, totalAmount,customerId;

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IWEB");
	}

	@Test
	public void Step01_Launch_Iweb_Application() {
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(
				YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB
				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test
	public void Step02_Select_Query_In_Query_Individual_Page() {
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.clickOnTab("Query Individual");
		test.memberShipPage.selectAndRunQuery(getYamlValue("PBA.queryName"));
	}

	@Test
	public void Step03_Verify_User_Naviagated_To_Individual_Profile_Page() {
		individualName = test.acsAddressValidation
				.verifyIndividualProfilePage();
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Individuals | "
				+ individualName);
		webLogin = test.memberShipPage.getMemberWebLogin();
		customerId=test.memberShipPage.getCustomerID();

	}

	@Test
	public void Step04_Login_in_IWEB() {
		app_url_PUBS = getYamlValue("app_url_PUBS");
		test.launchApplication(app_url_PUBS);
		test.asm_PUBSPage.loginInToApplication(webLogin, getYamlValue("password"));
	}

	@Test
	public void Step05_Add_EPassport() {
		test.asm_PUBSPage.clickOnAddAnEPassportButton();
		test.asm_PUBSPage.clickOnAddButtonInPassport();
		passportAmountValue = test.asm_PUBSPage.passportValue();
		test.asm_PUBSPage.clickOnSaveButton();
	}

	@Test
	public void Step05_Add_ESubscriptions() {
		test.asm_PUBSPage.clickOnAddAnESubscriptionButton();
		test.asm_PUBSPage.clickOnFirstAddButton();
		subscriptionsAmountValue = test.asm_PUBSPage.subscriptionValue();
		test.asm_PUBSPage.clickOnSaveButton();
	}

	@Test
	public void Step06_Verify_Total_And_Save_Data()
	{
		test.asm_PUBSPage.SavingProductNameAndAmount();
		test.asm_PUBSPage.verifyTotalValue();
	}
	
	@Test
	public void Step07_Fill_Billing_Information_And_Place_Order() {
		test.asm_PUBSPage.submitPaymentDetails(
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.paymentMethod"),
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.holderName"),
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.cardNumber"),
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.cvvNumber"),
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.yearValue"));
		test.asm_PUBSPage.clickOnPlaceOrder();
	}

	

}
