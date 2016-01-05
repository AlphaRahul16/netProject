package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.keywords.YamlInformationProvider;

public class ACS_Create_Member_IWEB_Test {

	TestSessionInitiator test;
	YamlInformationProvider getKeyValue;
	private String caseID;
	public String contactID;
	private String[] memDetails;
	int count;

	String app_url_IWEB = getYamlValue("app_url_IWEB");

	@Factory(dataProviderClass = com.qait.tests.DataProvider_CreateMemberIWEB.class, dataProvider = "data")
	public ACS_Create_Member_IWEB_Test(String caseID) {
		System.out.println("factory " + caseID);
		this.caseID = caseID;
	}

	@Test
	public void Step01_Launch_IWEB_Application_Under_Test() {
		test.homePageIWEB.addValuesInMap("createMember", caseID);
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(
				getYamlValue("Authentication.userName"),
				getYamlValue("Authentication.password"));
		test.homePageIWEB
				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test
	public void Step02_Add_Individual() {
		test.homePageIWEB.clickOnAddIndividual();
		memDetails = test.addMember
				.enterMemberDetailsInAddIndividual(caseID);
	}

	@Test
	public void Step03_Verify_Individual_Details() {
		contactID = test.individualsPage
				.verifyMemberDetails_InAddIndividual(caseID, memDetails);

		test.individualsPage.verifyMemberIsNotCreated();
		test.individualsPage.verifyMemberReceivedNoBenefits();
	}

	@Test
	public void Step04_Navigate_To_Order_Entry_And_Sell_Membership(){
		test.memberShipPage.goToOrderEntry();
		test.memberShipPage.goToAddMemebrshipAndFillDetails_membership();
		test.memberShipPage.goToAddMemebrshipAndFillDetails_LocalSection();
		count=test.memberShipPage.getDivisionNumbers();
	}
	
	@Test(invocationCount=2)
	public void Step05_Sell_Division(){
		
		test.memberShipPage.goToAddMembershipAndFillDetails_Division();
	}
	
	@Test
	public void Step06_Sell_Subscription(){
		test.memberShipPage.navigateToSubscriptionInSelectLinkAndSellSubscription();
		
		
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
