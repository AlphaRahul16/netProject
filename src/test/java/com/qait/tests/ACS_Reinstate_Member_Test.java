package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_Reinstate_Member_Test extends BaseTest{
	String csvSeparator;
	String app_url_IWEB;
	String customerName;

	@Test
	public void Step00_Launch_Application_Under_Test() {
		test.launchApplication(app_url_IWEB);
		test.homePage.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"), YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB
				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test
	public void Step01_Get_Inactive_Regular_Member_List() {
		test.homePageIWEB.GoToMemberShipModule();
		test.memberShipPage.selectAndRunQueryMembership("Query Membership",
				"QTP - Find Inactive Regular Member");
	}

	@Test
	public void Step02_Select_First_Inactive_Regular_Member_And_Reinstate() {
		customerName = test.memberShipPage
				.selectFirstInactiveRegularMemberAndVerifyExistingDetails()
				.get(0);
		test.memberShipPage.addMemberAndSelectDetails();
		test.memberShipPage.verifyItemsAddedSuccessFully();
		test.memberShipPage.selectBatchAndPaymentDetails();
	}

	@Test
	public void Step03_verify_Member_Reinstated_Successfully() {
		Reporter.log("****** CUSTOMER NAME : " + customerName + " ******\n",
				true);
		test.memberShipPage.verifyMemberReinstatedSuccessfully_Iweb();
	}

	/**
	 * * Following methods are to setup and clean up the tests
	 */
	@BeforeClass
	public void Start_Test_Session() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IWEB");
	}
	@BeforeMethod
	public void handleTestMethodName(Method method) {
		test.printMethodName(method.getName());
	}
}
