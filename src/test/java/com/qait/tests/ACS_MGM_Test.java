package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;
import java.util.List;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_MGM_Test extends BaseTest {

	private String IWEBurl, MGMpageURL;
	private String app_url_IWEB, app_url_MGMUrl, app_url_MGMjoin, app_url_MGMOptOut, app_url_MGMLogout;
	private List<String> memberDetails;
	private String uniqueEmail, app_ID, memberID, fname, lname;
	String[] userDetail;
	private List<String> customerFullNameList;
	private String webLogin;
	private String constitID;
	private List<String> memberDetails1;

	public ACS_MGM_Test() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "MGM";
	}

	@BeforeClass
	public void initiateTestSession() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.homePageIWEB.addValuesInMap("MGM", caseID);
		app_url_IWEB = getYamlValue("app_url_IWEB");
		app_url_MGMUrl = getYamlValue("app_url_MGMUrl");
		app_url_MGMjoin = getYamlValue("app_url_MGMjoin");
		app_url_MGMOptOut = getYamlValue("app_url_MGMOputOut");
		app_url_MGMLogout = getYamlValue("app_url_MGMLogout");
		if (caseID != null) {
			Reporter.log("       TEST CASE ID : " + caseID + "       \n", true);
		}
		test.launchApplication(app_url_IWEB);
		test.homePage.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
	}

	@BeforeMethod
	public void printCaseIdExecuted(Method method) {
		test.printMethodName(method.getName());
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_MGM_Test(String caseID) {
		this.caseID = caseID;
	}

	public void Run_Query_And_Fetch_Weblogin_And_Record_Number(String sideBarTab, String tab, String query, int times) {

		test.homePageIWEB.clickOnSideBarTab(sideBarTab);
		test.memberShipPage.clickOnTab(tab);
		test.memberShipPage.selectAndRunQuery(query);
		test.memberShipPage.enterDatesInRunTime(times);
		test.memberShipPage.clickOnGoButtonAfterPackageSelection();

		System.out.println("url" + IWEBurl);
	}

	@Test
	public void Step00_Launch_MGM_Invite_New_Member() {
		Run_Query_And_Fetch_Weblogin_And_Record_Number("Individuals", "Query Individual",
				getYamlValue("ACS_MGM.query1"), 2);
		memberDetails = test.memberShipPage.getWebloginAndRecordNumber();
		IWEBurl = test.individualsPage.getCurrentURL();
		test.launchApplication(app_url_MGMUrl);
		test.asm_MGM.loginInToApplication(memberDetails.get(1).trim(), getYamlValue("password"));
		fname = test.homePageIWEB.map().get("MGM_FNAME").trim() + System.currentTimeMillis();
		lname = test.homePageIWEB.map().get("MGM_LNAME").trim() + System.currentTimeMillis();
		uniqueEmail = test.asm_MGM.submitMemberDetailsToInvite(fname, lname,
				test.homePageIWEB.map().get("MGM_Email").trim());

	}

	@Test(dependsOnMethods = "Step00_Launch_MGM_Invite_New_Member")
	public void Step01_Verify_Nominee_Status_On_MGM_And_IWEB() {

		MGMpageURL = test.asm_MGM.verifyNomineeStatus("Resend", uniqueEmail);
		test.memberShipPage.verifyNomineeStatusOnIWEB(IWEBurl, "PENDING", uniqueEmail, fname, lname);

		MGMpageURL = test.asm_MGM.verifyStatusAfterClickResend("Resend", MGMpageURL, uniqueEmail);
		test.memberShipPage.verifyNomineeStatusOnIWEB(IWEBurl, "PENDING", uniqueEmail, fname, lname);

		test.asm_MGM.verifyStatusAfterClickResend("Pending", MGMpageURL, uniqueEmail);
		test.memberShipPage.verifyNomineeStatusOnIWEB(IWEBurl, "PENDING", uniqueEmail, fname, lname);
		app_ID = test.memberShipPage.getApplicationID();
	}

	@Test(dependsOnMethods = "Step01_Verify_Nominee_Status_On_MGM_And_IWEB")
	public void Step02_New_Individual_Joins_ACS_And_Verify_That_The_Source_Code_Is_Populated() {
		test.launchApplication(app_url_MGMjoin + app_ID);
		Reporter.log("****** CASE ID : " + caseID + " ******\n", true);
		test.ContactInfoPage.enterContactInformationWithMemberNumber(uniqueEmail, fname, lname, "",
				test.homePageIWEB.map().get("AddressType"), test.homePageIWEB.map().get("Address"),
				test.homePageIWEB.map().get("City"), test.homePageIWEB.map().get("Country"),
				test.homePageIWEB.map().get("State"), test.homePageIWEB.map().get("ZipCode"));
		test.ContactInfoPage.clickContinue();

		test.EduAndEmpPage.enterEducationAndEmploymentInformation();
		test.ContactInfoPage.clickContinue();

		test.BenefitsPage.addACSPublicationAndTechnicalDivision(caseID);
		test.ContactInfoPage.clickContinue();
		test.checkoutPage.verifySourceCodeIsAlreadyFilled();

		test.checkoutPage.enterPaymentInfo(YamlReader.getYamlValue("creditCardInfo.Type"), fname + " " + lname,
				YamlReader.getYamlValue("creditCardInfo.Number"), YamlReader.getYamlValue("creditCardInfo.cvv-number"));
		test.checkoutPage.clickAtTestStatement();
		test.ContactInfoPage.clickContinue();
		test.checkoutPage.clickSubmitButtonAtBottom();
		constitID = test.confirmationPage.getMemberDetail("member-number");
	}

	@Test(dependsOnMethods = "Step02_New_Individual_Joins_ACS_And_Verify_That_The_Source_Code_Is_Populated")
	public void Step03_Verify_The_Nominators_Invitation_Status_On_The_MGM_Dashboard_And_In_Iweb() {
		test.launchApplication(MGMpageURL);
		test.asm_MGM.verifyStatusAfterClickResend("Joined", MGMpageURL, uniqueEmail);
		test.memberShipPage.verifyNominatorDetailsOnIweb(IWEBurl, app_ID, test.homePageIWEB.map().get("Program").trim(),
				test.homePageIWEB.map().get("Channel").trim(), fname, lname, uniqueEmail, "JOINED", "Paid",
				test.homePageIWEB.map().get("Source Code").trim(), constitID);
		customerFullNameList = test.memberShipPage.getCustomerFullNameAndContactID();
		test.memberShipPage.clickOnConstitID_underMyACSNominations();
	}

	@Test(dependsOnMethods = "Step03_Verify_The_Nominators_Invitation_Status_On_The_MGM_Dashboard_And_In_Iweb")
	public void Step04_Verify_Nomiee_Details_On_IWEB() {
		test.memberShipPage.verifyNomieeDetails(app_ID, test.homePageIWEB.map().get("Program").trim(),
				test.homePageIWEB.map().get("Channel").trim(), "JOINED", "Paid",
				test.homePageIWEB.map().get("Source Code").trim(), memberID);
	}

	@Test(dependsOnMethods = "Step04_Verify_Nomiee_Details_On_IWEB")
	public void Step05_verify_After_Clicking_The_NominatorID_Takes_To_The_Nominators_Record() {
		test.memberShipPage.clickOnNominatorID_underMyACSApplication();
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Individuals |" + customerFullNameList.get(0).trim());
		test.memberShipPage.launchUrl(app_url_MGMLogout);
	}

	// @Test
	public void Step06_Run_Query_For_Scenario2() {
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
		Run_Query_And_Fetch_Weblogin_And_Record_Number("Members", "Query Membership", getYamlValue("ACS_MGM.query2"),
				0);
		webLogin = test.memberShipPage.getCstWebLoginForMembership("7");
		IWEBurl = test.individualsPage.getCurrentURL();
	}

	// @Test(dependsOnMethods="Step06_Run_Query_For_Scenario2")
	public void Step07_Invite_New_Member_Click_On_Apply_for_ACS_Membership_link() {
		test.launchApplication(app_url_MGMUrl);
		test.asm_MGM.loginInToApplication(webLogin, getYamlValue("password"));
	}

	// @Test(dependsOnMethods="Step07_Invite_New_Member_Click_On_Apply_for_ACS_Membership_link")
	public void Step08_Ensure_individual_Is_Redirected_To_OMA_And_Information_Is_Prepopulated() {
		test.asm_MGM.clickOnApplyForACSMembership();
		test.asm_MGM.verifyDetailsArePrepopulated();
		test.memberShipPage.launchUrl(app_url_MGMLogout);
	}

	// @Test
	public void Step09_Run_Query_For_Scenario3_And_Invite_Non_Member() {
		Run_Query_And_Fetch_Weblogin_And_Record_Number("Individuals", "Query Individual",
				getYamlValue("ACS_MGM.query1"), 2);
		memberDetails = test.memberShipPage.getWebloginAndRecordNumber();
		IWEBurl = test.individualsPage.getCurrentURL();
		test.launchApplication(app_url_MGMUrl);
		test.asm_MGM.loginInToApplication(memberDetails.get(1).trim(), getYamlValue("password"));
		uniqueEmail = test.asm_MGM.submitMemberDetailsToInvite(test.homePageIWEB.map().get("MGM_FNAME").trim(),
				test.homePageIWEB.map().get("MGM_LNAME").trim(), test.homePageIWEB.map().get("MGM_Email").trim());
		MGMpageURL = test.asm_MGM.verifyNomineeStatus("Resend", uniqueEmail);
		test.memberShipPage.verifyNomineeStatusOnIWEB(IWEBurl, "PENDING", uniqueEmail,
				test.homePageIWEB.map().get("MGM_FNAME").trim(), test.homePageIWEB.map().get("MGM_LNAME").trim());
		app_ID = test.memberShipPage.getApplicationID();
	}

	// @Test
	public void Step10_New_Individual_Opts_Out_And_Confirm_Status_On_IWEB_And_MGM_Dashboard() {
		Reporter.log("******** Application ID-" + app_ID + "********");
		test.memberShipPage.launchUrl(app_url_MGMOptOut + app_ID);
		test.launchApplication(app_url_MGMUrl);
		test.asm_MGM.loginInToApplication(memberDetails.get(1).trim(), getYamlValue("password"));
		MGMpageURL = test.asm_MGM.verifyNomineeStatus("Opted Out", uniqueEmail);
		test.memberShipPage.verifyNomineeStatusOnIWEB(IWEBurl, "OPTED-OUT", uniqueEmail,
				test.homePageIWEB.map().get("MGM_FNAME").trim(), test.homePageIWEB.map().get("MGM_LNAME").trim());
		test.memberShipPage.launchUrl(app_url_MGMLogout);
	}

	// @Test
	public void Step11_Run_Query_For_Scenario4__And_Fetch_Weblogin() {
		Run_Query_And_Fetch_Weblogin_And_Record_Number("Individuals", "Query Individual",
				getYamlValue("ACS_MGM.query3"), 3);
		webLogin = test.memberShipPage.getCstWebLoginForMembership("3");
		IWEBurl = test.individualsPage.getCurrentURL();
		test.launchApplication(app_url_MGMUrl);
		test.asm_MGM.loginInToApplication(webLogin, getYamlValue("password"));
	}

	// @Test(dependsOnMethods="Step11_Run_Query_For_Scenario4__And_Fetch_Weblogin")
	public void Step12_Verify_That_Clicking_On_Renew_Your_Membership_Now_Link_Redirects_To_OMR() {
		test.asm_MGM.clickOnRenewYourMembershipNow();
		test.asm_OMR.OMRLogo("Online Membership Renewal");
		test.memberShipPage.launchUrl(app_url_MGMLogout);
	}

	// @Test
	public void Step13_scenario5() {
		Run_Query_And_Fetch_Weblogin_And_Record_Number("Individuals", "Query Individual",
				getYamlValue("ACS_MGM.query1"), 2);
		memberDetails = test.memberShipPage.getWebloginAndRecordNumber();

		IWEBurl = test.individualsPage.getCurrentURL();
		test.launchApplication(app_url_MGMUrl);
		test.asm_MGM.loginInToApplication(memberDetails.get(1).trim(), getYamlValue("password"));
		fname = test.homePageIWEB.map().get("MGM_FNAME").trim() + System.currentTimeMillis();
		lname = test.homePageIWEB.map().get("MGM_LNAME").trim() + System.currentTimeMillis();
		uniqueEmail = test.asm_MGM.submitMemberDetailsToInvite(fname, lname,
				test.homePageIWEB.map().get("MGM_Email").trim());

		MGMpageURL = test.asm_MGM.verifyNomineeStatus("Resend", uniqueEmail);
	}
}
