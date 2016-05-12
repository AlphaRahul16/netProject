package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;

public class ACS_Create_Member_IWEB_Test {

	TestSessionInitiator test;
	
	private String caseID;
	public String contactID;
	private String[] memDetails;
	int numberOfDivisions, numberOfSubscriptions;
	String app_url_IWEB = getYamlValue("app_url_IWEB");

	public ACS_Create_Member_IWEB_Test() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "createMember";
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_Create_Member_IWEB_Test(String caseID) {
		this.caseID = caseID;
	}

	@Test
	public void Step01_Launch_IWEB_Application_Under_Test() {
		Reporter.log("CASE ID : " + caseID, true);
		test.homePageIWEB.addValuesInMap("createMember", caseID);
		test.launchApplication(app_url_IWEB);
		
		test.homePageIWEB
				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test
	public void Step02_Add_Individual() {
		Reporter.log("CASE ID : " + caseID, true);
		test.homePageIWEB.clickOnAddIndividual();
		memDetails = test.addMember.enterMemberDetailsInAddIndividual();
	}

	@Test
	public void Step03_Verify_Individual_Details() {
		Reporter.log("CASE ID : " + caseID, true);
		contactID = test.individualsPage
				.verifyMemberDetails_InAddIndividual(memDetails);
		test.individualsPage.verifyMemberIsNotCreated();
		test.individualsPage.verifyMemberReceivedNoBenefits();
	}

	@Test
	public void Step04_Navigate_To_Order_Entry_And_Sell_Membership() {
		Reporter.log("CASE ID : " + caseID, true);
		test.memberShipPage.goToOrderEntry();
		test.memberShipPage.goToAddMembershipAndFillDetails_membership();
		test.memberShipPage.goToAddMemebrshipAndFillDetails_LocalSection();
	}

	@Test
	public void Step05_Sell_Division() {
		Reporter.log("CASE ID : " + caseID, true);
		numberOfDivisions = test.memberShipPage.getDivisionNumbers();
		test.memberShipPage
				.goToAddMembershipAndFillDetails_Division(numberOfDivisions);
	}

	@Test
	public void Step06_Sell_Subscription() {
		Reporter.log("CASE ID : " + caseID, true);
		numberOfSubscriptions = test.memberShipPage.getSubscriptionNumbers();
		test.memberShipPage
				.navigateToSubscriptionInSelectLinkAndSellSubscription(numberOfSubscriptions);
	}

	@Test
	public void Step07_Verify_NetPrice_Amount_And_Make_Payment() {
		Reporter.log("CASE ID : " + caseID, true);
		test.memberShipPage.verifyNetPriceValue("netbalance");
		test.memberShipPage.selectBatchAndPaymentDetails_subscription(
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.batch"),
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.PaymentType"),
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.paymentMethod"),
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.cardNumber"),
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.expireDate"),
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.cvvNumber"));
	}

	@Test
	public void Step08_Verify_Member_Details_In_Individual_And_Chapter_Memberships() {
		Reporter.log("CASE ID : " + caseID, true);
		test.individualsPage.verifyMemberDetails_MemberProfile(memDetails[1],
				memDetails[2]);
		test.memberShipPage.verifyMemberDetails_IWEB("individual memberships",
				1);
		test.memberShipPage.verifyMemberDetails_IWEB("chapter memberships",
				numberOfDivisions);
	}

	@Test
	public void Step09_Verify_Member_Details_In_Subscriptions() {
		Reporter.log("CASE ID : " + caseID, true);
		test.individualsPage
				.navigateToSubscriptionAndVerifySubscriptionDetails(numberOfSubscriptions);
	}

	/**
	 * * Following methods are to setup and clean up the tests
	 */
	@BeforeClass
	public void Start_Test_Session() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
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
