package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;

public class ACS_Reinstate_Member_Test {
	TestSessionInitiator test;
	String csvSeparator;
	String app_url_IWEB;
	String customerName;

	@Test
	public void Step00_Launch_Application_Under_Test() {
		System.out.println(app_url_IWEB);
		test.launchApplication(app_url_IWEB);
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

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
	}

	@AfterClass(alwaysRun = true)
	public void Close_Test_Session() {
		test.closeBrowserSession();
	}

}
