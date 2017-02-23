package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_MGM_Test extends BaseTest {
	private String IWEBurl, MGMpageURL;
	private String app_url_IWEB, app_url_MGMUrl, app_url_MGMjoin, app_url_MGMOptOut, app_url_MGMLogout;
	private List<String> memberDetails;
	private String uniqueEmail, app_ID, memberID, fname, lname, fname_Iweb, lname_Iweb;
	private String caseID, constitID, memberDetailUrl2, resendCount;
	private List<String> customerFullNameList, uniqueEmails;
	private String webLogin, scenarioNo, sourceCode, webLogin2;
	private List<String> StatusOnEweb, StatusOnIweb, StatusOnEwebAfterClickResend, StatusOnIwebAfterClickResend;

	public ACS_MGM_Test() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "MGM";
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_MGM_Test(String caseID) {
		this.caseID = caseID;
	}

	@BeforeClass
	public void initiateTestSession() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IWEB");
		app_url_MGMUrl = getYamlValue("app_url_MGMUrl");
		app_url_MGMjoin = getYamlValue("app_url_MGMjoin");
		app_url_MGMOptOut = getYamlValue("app_url_MGMOputOut");
		app_url_MGMLogout = getYamlValue("app_url_MGMLogout");
		ASCSocietyGenericPage.addValuesInMap("MGM", toString().valueOf(caseID));
		test.launchApplication(app_url_IWEB);
		test.homePage.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		resendCount = ASCSocietyGenericPage.map().get("Resend").trim();
		StatusOnEweb = test.asm_MGM.getStatus(ASCSocietyGenericPage.map().get("StatusOnEweb").trim());
		StatusOnIweb = test.asm_MGM.getStatus(ASCSocietyGenericPage.map().get("StatusOnIweb").trim());
		StatusOnEwebAfterClickResend = test.asm_MGM
				.getStatus(ASCSocietyGenericPage.map().get("StatusOnEwebAfterClickResend").trim());
		StatusOnIwebAfterClickResend = test.asm_MGM
				.getStatus(ASCSocietyGenericPage.map().get("StatusOnIwebAfterClickResend").trim());
	}

	@BeforeMethod
	public void skipMethodsAccordingToTheScenarioExecuted(Method method) {
		scenarioNo = ASCSocietyGenericPage.map().get("Scenario").trim();
		test.printMethodName(method.getName());
		// Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		if (!(method.getName().contains("Scenario" + scenarioNo))) {
			throw new SkipException("Tests Skipped for expected work flows!");
		}
	}

	@Test
	public void Step01_Run_Query_And_Fetch_Weblogin_FirstName_LastName_For_Scenario2() {
		Launch_IWeb_Application_And_Run_Query("Members", "Query Membership", getYamlValue("ACS_MGM.query2"), 0,
				"Membership");
		webLogin = test.memberShipPage.getCstWebLoginForMembership("7", "Web login");
		fname_Iweb = test.memberShipPage.getCstWebLoginForMembership("3", "Firstname");
		lname_Iweb = test.memberShipPage.getCstWebLoginForMembership("4", "Last name");
		IWEBurl = test.individualsPage.getCurrentURL();
	}

	@Test
	public void Step02_Launch_MGM_Application_And_Verify_All_Details_Are_Prepopulated_On_Clicking_Apply_for_ACS_Membership_link_For_Scenario2() {
		Reporter.log("****** CASE ID : " + caseID + " ******\n", true);
		test.launchApplication(app_url_MGMUrl);
		test.asm_MGM.loginInToApplication(webLogin, getYamlValue("password"));
		test.asm_MGM.clickOnApplyForACSMembership();
		test.ContactInfoPage.verifyDetailsArePrepopulated();
	}

	@Test
	public void Step03_Activate_The_Membership_On_OMA_And_Invite_New_Member_From_MGM_Scenario2() {
		Reporter.log("****** CASE ID : " + caseID + " ******\n", true);
		test.ContactInfoPage.clickContinue();

		test.EduAndEmpPage.enterEducationAndEmploymentInformation();
		test.ContactInfoPage.clickContinue();

		test.BenefitsPage.addACSPublicationAndTechnicalDivision(caseID);
		test.ContactInfoPage.clickContinue();

//		test.checkoutPage.enterPaymentInfo(YamlReader.getYamlValue("creditCardInfo.Type"),
//				fname_Iweb + " " + lname_Iweb, YamlReader.getYamlValue("creditCardInfo.Number"),
//				YamlReader.getYamlValue("creditCardInfo.cvv-number"));
		
		test.asm_storePage.enterPaymentInfo("CardholderName","test Selenium");
		test.asm_storePage.enterPaymentInformation_OMAForAllPaymentTypes(test.homePageIWEB.map().get("Payment_Method"), test.homePageIWEB.map().get("Visa_Card_Number"), test.homePageIWEB.map().get("Diners_Card_Number"), test.homePageIWEB.map().get("Reference_Number"),
				test.homePageIWEB.map().get("Discover_Card_Number"),test.homePageIWEB.map().get("AMEX_Card_Number") ,test.homePageIWEB.map().get("Expiry_Month"), test.homePageIWEB.map().get("CVV_Number"), test.homePageIWEB.map().get("Check_Number"),test.homePageIWEB.map().get("Expiry_Year"));

		
		test.checkoutPage.clickAtTestStatement();
		test.ContactInfoPage.clickContinue();
		test.checkoutPage.clickSubmitButtonAtBottom();
		test.launchApplication(app_url_MGMLogout);
		test.launchApplication(app_url_MGMUrl);
		test.asm_MGM.loginInToApplication(webLogin, getYamlValue("password"));
		uniqueEmail = test.asm_MGM.submitMemberDetailsToInvite(ASCSocietyGenericPage.map().get("MGM_FNAME").trim(),
				ASCSocietyGenericPage.map().get("MGM_LNAME").trim(),
				ASCSocietyGenericPage.map().get("MGM_Email").trim());
		test.asm_MGM.verifyNomineeStatus(StatusOnEweb.get(0), uniqueEmail);
	}

	@Test
	public void Step04_Run_Query_And_Fetch_Weblogin_For_Scenario3() {
		Launch_IWeb_Application_And_Run_Query("Individuals", "Query Individual", getYamlValue("ACS_MGM.query1"), 0,
				"CRM");
		webLogin = test.memberShipPage.getCstWebLogin();
		IWEBurl = test.individualsPage.getCurrentURL();
	}

	@Test
	public void Step05_Invite_Non_Member_And_Verify_Nominee_Status_On_MGM_And_IWEB_For_Scenario3() {
		Reporter.log("****** CASE ID : " + caseID + " ******\n", true);
		test.launchApplication(app_url_MGMUrl);
		test.asm_MGM.loginInToApplication(webLogin, getYamlValue("password"));
		uniqueEmail = test.asm_MGM.submitMemberDetailsToInvite(ASCSocietyGenericPage.map().get("MGM_FNAME").trim(),
				ASCSocietyGenericPage.map().get("MGM_LNAME").trim(),
				ASCSocietyGenericPage.map().get("MGM_Email").trim());

		MGMpageURL = test.asm_MGM.verifyNomineeStatus(StatusOnEweb.get(0), uniqueEmail);
		test.launchApplication(app_url_MGMLogout);
		test.memberShipPage.verifyNomineeStatusOnIWEB(IWEBurl, StatusOnIweb.get(0), uniqueEmail,
				ASCSocietyGenericPage.map().get("MGM_FNAME").trim(),
				ASCSocietyGenericPage.map().get("MGM_LNAME").trim());
		app_ID = test.memberShipPage.getApplicationID(uniqueEmail);
	}

	@Test
	public void Step06_New_Individual_Opts_Out_And_Confirm_Status_On_IWEB_And_MGM_Dashboard_Scenario3() {
		Reporter.log("******** Application ID-" + app_ID + "********");
		test.launchApplication(app_url_MGMOptOut + app_ID);
		test.launchApplication(app_url_MGMUrl);
		test.asm_MGM.loginInToApplication(webLogin, getYamlValue("password"));
		MGMpageURL = test.asm_MGM.verifyNomineeStatus(StatusOnEweb.get(1), uniqueEmail);
		test.memberShipPage.verifyNomineeStatusOnIWEB(IWEBurl, StatusOnIweb.get(1), uniqueEmail,
				ASCSocietyGenericPage.map().get("MGM_FNAME").trim(),
				ASCSocietyGenericPage.map().get("MGM_LNAME").trim());
		test.launchApplication(app_url_MGMLogout);
	}

	@Test
	public void Step07_Run_Query_Fetch_Weblogin_And_Verify_Invite_Option_Is_not_Visible_Scenario4() {
		Launch_IWeb_Application_And_Run_Query("Individuals", "Query Individual", getYamlValue("ACS_MGM.query3"), 3,
				"CRM");
		webLogin = test.memberShipPage.getCstWebLoginForMembership("3", "Web login");
		fname_Iweb = test.memberShipPage.getCstWebLoginForMembership("4", "Firstname");
		lname_Iweb = test.memberShipPage.getCstWebLoginForMembership("5", "Last name");
		IWEBurl = test.individualsPage.getCurrentURL();
		test.launchApplication(app_url_MGMUrl);
		test.asm_MGM.loginInToApplication(webLogin, getYamlValue("password"));
		test.asm_MGM.inviteButtonIsNotDisplayed();
	}

	@Test
	public void Step08_Renew_Membership_And_Invite_A_New_Member_Scenario4() { //uncommented paymnet steps 
		Reporter.log("****** CASE ID : " + caseID + " ******\n", true);
		test.asm_MGM.clickOnRenewYourMembershipNow();
		test.asm_OMR.OMRLogo("Online Membership Renewal");
//		test.asm_OMR.submitPaymentDetails(YamlReader.getYamlValue("creditCardInfo.Type"), fname_Iweb + " " + lname_Iweb,
//				YamlReader.getYamlValue("creditCardInfo.Number"), YamlReader.getYamlValue("creditCardInfo.cvv-number"),
//				YamlReader.getYamlValue("creditCardInfo.CreditCardExpiration").split("\\/")[0],
//				YamlReader.getYamlValue("creditCardInfo.CreditCardExpiration").split("\\/")[1]);
		
		test.asm_OMR.submitPaymentDetails(test.homePageIWEB.map().get("Payment_Method"),"test Selenium", toString().valueOf(test.homePageIWEB.map().get("Visa_Card_Number").trim()), test.homePageIWEB.map().get("Diners_Card_Number"),
				test.homePageIWEB.map().get("Discover_Card_Number"),test.homePageIWEB.map().get("AMEX_Card_Number"),
				test.homePageIWEB.map().get("CVV_Number"), test.homePageIWEB.map().get("Expiry_Month"), test.homePageIWEB.map().get("Expiry_Year"));
		
		test.asm_OMR.clickOnSubmitPayment();
		test.launchApplication(app_url_MGMLogout);
		test.launchApplication(app_url_MGMUrl);
		test.asm_MGM.loginInToApplication(webLogin, getYamlValue("password"));
		uniqueEmail = test.asm_MGM.submitMemberDetailsToInvite(ASCSocietyGenericPage.map().get("MGM_FNAME").trim(),
				ASCSocietyGenericPage.map().get("MGM_LNAME").trim(),
				ASCSocietyGenericPage.map().get("MGM_Email").trim());
		test.asm_MGM.verifyNomineeStatus(StatusOnEweb.get(0), uniqueEmail);
		test.launchApplication(app_url_MGMLogout);
	}

	@Test
	public void Step09_Fetch_DefaultSourcecode_For_Scenario1_Scenario5() {
		Reporter.log("\n ****** CASE ID : " + caseID + " ******\n", true);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
		test.memberShipPage.clickOnTab("Membership Setup");
		sourceCode = test.memberShipPage.getDefaultSourceCodeForMGM(ASCSocietyGenericPage.map().get("Country"));
	}

	@Test
	public void Step09_Launch_MGM_Application_To_Invite_New_Member_For_Scenario1_Scenario5_Scenario6() {

		Launch_IWeb_Application_And_Run_Query("Individuals", "Query Individual", getYamlValue("ACS_MGM.query1"), 0,
				"CRM");
		webLogin = test.memberShipPage.getCstWebLogin();
		IWEBurl = test.individualsPage.getCurrentURL();
		test.launchApplication(app_url_MGMUrl);
		test.asm_MGM.loginInToApplication(webLogin, getYamlValue("password"));

	}

	@Test
	public void Step10_Invite_Nominee_And_Verify_Nominee_Status_On_MGM_And_IWEB_Scenario1_Scenario5() {
		Reporter.log("****** CASE ID : " + caseID + " ******\n", true);
		fname = ASCSocietyGenericPage.map().get("MGM_FNAME").trim() + System.currentTimeMillis();
		lname = ASCSocietyGenericPage.map().get("MGM_LNAME").trim() + System.currentTimeMillis();
		uniqueEmail = test.asm_MGM.submitMemberDetailsToInvite(fname, lname,
				ASCSocietyGenericPage.map().get("MGM_Email").trim());
		MGMpageURL = test.asm_MGM.verifyNomineeStatus(StatusOnEweb.get(0), uniqueEmail);
		test.memberShipPage.verifyNomineeStatusOnIWEB(IWEBurl, StatusOnIweb.get(0), uniqueEmail, fname, lname);
		test.asm_MGM.clickOnresendLink(resendCount, MGMpageURL, uniqueEmail, IWEBurl, fname, lname,
				StatusOnEwebAfterClickResend, StatusOnIwebAfterClickResend);
		app_ID = test.memberShipPage.getApplicationID(uniqueEmail);
		test.launchApplication(app_url_MGMLogout);
	}

	@Test
	public void Step11_Get_Member_Details_From_IWEB_And_Invite_Same_Member_On_MGM_For_Scenario5() {
		test.launchApplication(app_url_IWEB);
		Launch_IWeb_Application_And_Run_Query("Individuals", "Query Individual", getYamlValue("ACS_MGM.query1"), 0,
				"CRM");
		webLogin2 = test.memberShipPage.getCstWebLogin();
		test.memberShipPage.handleAlert();
		// detailsForMember2 = test.memberShipPage.getWebloginAndRecordNumber();
		memberDetailUrl2 = test.individualsPage.getCurrentURL();
		test.launchApplication(app_url_MGMUrl);
		test.asm_MGM.loginInToApplication(webLogin2, getYamlValue("password"));
		test.asm_MGM.submitSameMemberDetailsToInvite(fname, lname, uniqueEmail);
		test.asm_MGM.verifyNomineeStatus(StatusOnEweb.get(0), uniqueEmail);
		test.memberShipPage.verifyNomineeStatusOnIWEB(memberDetailUrl2, StatusOnIweb.get(0), uniqueEmail, fname, lname);
		test.launchApplication(app_url_MGMLogout);
	}

	@Test
	public void Step12_New_Individual_Joins_ACS_And_Verify_Source_Code_Is_Prepopulated_For_Scenario1_Scenario5() {
		Reporter.log("****** CASE ID : " + caseID + " ******\n", true);
		test.launchApplication(app_url_MGMjoin + app_ID);

		test.ContactInfoPage.enterContactInformationWithMemberNumber(uniqueEmail, fname, lname, "",
				ASCSocietyGenericPage.map().get("AddressType"), ASCSocietyGenericPage.map().get("Address"),
				ASCSocietyGenericPage.map().get("City"), ASCSocietyGenericPage.map().get("Country"),
				ASCSocietyGenericPage.map().get("State"), ASCSocietyGenericPage.map().get("ZipCode"));
		test.ContactInfoPage.clickContinue();

		test.EduAndEmpPage.enterEducationAndEmploymentInformation();
		test.ContactInfoPage.clickContinue();

		test.BenefitsPage.addACSPublicationAndTechnicalDivision(caseID);
		test.ContactInfoPage.clickContinue();
		test.checkoutPage.verifySourceCodeIsAlreadyPrepoulated(sourceCode);

//		test.checkoutPage.enterPaymentInfo(YamlReader.getYamlValue("creditCardInfo.Type"), fname + " " + lname,
//				YamlReader.getYamlValue("creditCardInfo.Number"), YamlReader.getYamlValue("creditCardInfo.cvv-number"));
		
		test.asm_storePage.enterPaymentInfo("CardholderName","test Selenium");
		test.asm_storePage.enterPaymentInformation_OMAForAllPaymentTypes(test.homePageIWEB.map().get("Payment_Method"), test.homePageIWEB.map().get("Visa_Card_Number"), test.homePageIWEB.map().get("Diners_Card_Number"), test.homePageIWEB.map().get("Reference_Number"),
				test.homePageIWEB.map().get("Discover_Card_Number"),test.homePageIWEB.map().get("AMEX_Card_Number") ,test.homePageIWEB.map().get("Expiry_Month"), test.homePageIWEB.map().get("CVV_Number"), test.homePageIWEB.map().get("Check_Number"),test.homePageIWEB.map().get("Expiry_Year"));

		
		test.checkoutPage.clickAtTestStatement();
		test.ContactInfoPage.clickContinue();
		test.checkoutPage.clickSubmitButtonAtBottom();
		constitID = test.confirmationPage.getMemberDetail("member-number");

	}

	@Test
	public void Step13_Nominee_Joins_From_Member1_Invitation_And_Verify_Status_As_Joined_On_MGM_IWEB_Scenario5_Scenario1() {
		Reporter.log("****** CASE ID : " + caseID + " ******\n", true);
		test.launchApplication(app_url_MGMUrl);
		test.asm_MGM.loginInToApplication(webLogin, getYamlValue("password"));
		test.asm_MGM.verifyNomineeStatus(StatusOnEweb.get(1), uniqueEmail);
		test.launchApplication(app_url_MGMLogout);
		test.memberShipPage.verifyNominatorDetailsOnIweb(IWEBurl, app_ID,
				ASCSocietyGenericPage.map().get("Program").trim(), ASCSocietyGenericPage.map().get("Channel").trim(),
				fname, lname, uniqueEmail, StatusOnIweb.get(1), "Paid", sourceCode, constitID);
		customerFullNameList = test.memberShipPage.getCustomerFullNameAndContactID();

	}

	@Test
	public void Step14_Verify_Nominee_Status_As_Already_A_Member_On_Iweb_And_MGM_For_Member2_Scenario5() {
		Reporter.log("****** CASE ID : " + caseID + " ******\n", true);
		test.launchApplication(app_url_MGMUrl);
		test.asm_MGM.loginInToApplication(webLogin2, getYamlValue("password"));
		test.asm_MGM.verifyNomineeStatus(StatusOnEweb.get(2), uniqueEmail);
		test.launchApplication(app_url_MGMLogout);
		test.memberShipPage.verifyNomineeStatusOnIWEB(memberDetailUrl2, StatusOnIweb.get(2), uniqueEmail, fname, lname);
	}

	@Test
	public void Step15_Verify_Nomiee_Details_On_IWEB_Scenario1() {
		Reporter.log("****** CASE ID : " + caseID + " ******\n", true);
		test.memberShipPage.clickOnConstitID_underMyACSNominations();
		test.memberShipPage.verifyNomieeDetails(app_ID, ASCSocietyGenericPage.map().get("Program").trim(),
				ASCSocietyGenericPage.map().get("Channel").trim(), StatusOnIweb.get(1), "Paid", sourceCode, memberID);
	}

	@Test
	public void Step16_Verify_User_Is_Navigated_To_The_Nominators_Record_Page_On_Clicking_The_NominatorID_Link_For_Scenario1() {
		Reporter.log("****** CASE ID : " + caseID + " ******\n", true);
		test.memberShipPage.clickOnNominatorID_underMyACSApplication();
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Individuals |" + customerFullNameList.get(0).trim());
	}

	@Test
	public void Step17_Invite_New_Members_For_Scenario6() {

		uniqueEmails = test.asm_MGM.InviteNewMembersAccordingToInviteeNumber(
				ASCSocietyGenericPage.map().get("Number_Of_Invitee").trim(),
				ASCSocietyGenericPage.map().get("MGM_FNAME").trim(),
				ASCSocietyGenericPage.map().get("MGM_LNAME").trim(),
				ASCSocietyGenericPage.map().get("MGM_Email").trim());
	}

	@Test
	public void Step18_Verify_All_Invitees_On_MGM_And_IWEB_For_Scenario6() {
		Reporter.log("****** CASE ID : " + caseID + " ******\n", true);
		test.asm_MGM.verifythatAllInviteesExistOnMGM(uniqueEmails, StatusOnEweb.get(0));
		test.launchApplication(IWEBurl);
		test.memberShipPage.verifythatAllInviteesExistOnIWEB(uniqueEmails, StatusOnIweb.get(0));
	}

	public void Launch_IWeb_Application_And_Run_Query(String sideBarTab, String tab, String query, int times,
			String moduleName) {
		Reporter.log("****** CASE ID : " + caseID + " ******\n", true);

		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab(moduleName);
		test.homePageIWEB.clickOnSideBarTab(sideBarTab);
		test.memberShipPage.clickOnTab(tab);
		test.memberShipPage.selectAndRunQuery(query);
		test.memberShipPage.enterCurrentDatesInQuery(times);
		test.memberShipPage.clickOnGoButtonAfterPackageSelection();
	}

}
