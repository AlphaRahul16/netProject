package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;

public class ACS_AACT_OMR extends BaseTest {

	private String webLogin, matterType;
	public String contactID;
	private String[] memDetails;
	List<String> updatedValues = new ArrayList<String>();
	
	String app_url_IWEB = getYamlValue("app_url_IWEB");
	String app_url_AACT_OMR = getYamlValue("app_url_AACT_OMR");

	public ACS_AACT_OMR() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "AACT_OMR";
	}

	@BeforeClass
	public void initiateTestSeesion() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		if (caseID != null) {
			Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		}
	}

	@BeforeMethod
	public void printCaseIdExecuted(Method method) {
		test.printMethodName(method.getName());

	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_AACT_OMR(String caseID) {
		this.caseID = caseID;
	}

	@Test
	public void Step05_Launch_EWEB_Application_and_Login_with_Weblogin() {
		test.homePageIWEB.addValuesInMap("AACT_OMR", caseID);
		test.launchApplication(app_url_AACT_OMR);
		test.homePage.verifyUserIsOnHomePage("AACT OMR >> Login");
		test.acs_aactOmr.loginWithWeblogin("madison", "password");
		test.award_ewebPage.clickOnLoginButton();
		test.homePage.verifyUserIsOnHomePage("AACT OMR >> Demographics");

	}

	@Test
	public void Step06_click_on_Edit_link_and_update_contact_info() {
		test.acs_aactOmr.clickOnLink("edit");
		test.acs_aactOmr.clickButtonByInputValue("Change Email");
		test.acs_aactOmr.editEmail();
		test.acs_aactOmr.clickButtonByInputValue("Return to Renewal");
	}

	@Test
	public void Step07_Select_value_for_How_do_you_want_to_receive_ChemMatters_Update_details_of_About_You() {
		String type = test.homePageIWEB.map().get("How do you want to receive ChemMatters?");
		test.acs_aactOmr.selectValuesForChemMatters("How do you want to receive", type);
		updatedValues=test.acs_aactOmr.updateAboutYou();
		test.acs_aactOmr.clickButtonByInputValue("Save");
	}

	@Test
	public void Step08_Verify_details_of_About_You_and_Enter_Gender_and_Experience() {
		test.acs_aactOmr.verifyDetailsOfUpdateAboutYou(updatedValues);
		test.acs_aactOmr.enterGenderAndExperienceDetails(test.homePageIWEB.map().get("Gender").trim(), "11");

	}

	@Test
	public void Step09_Enter_Billing_Information() {
		test.acs_aactOmr.enterPaymentInfo(test.homePageIWEB.map().get("CreditCardType").trim(),
				test.homePageIWEB.map().get("CardholderName").trim(),
				test.homePageIWEB.map().get("CreditCardNumber").trim(), test.homePageIWEB.map().get("cvv").trim(),
				test.homePageIWEB.map().get("ExpirationMonth").trim(),test.homePageIWEB.map().get("ExpirationYear").trim());
		test.acs_aactOmr.clickButtonByInputValue("chkAutoRenewal");
	}

}
