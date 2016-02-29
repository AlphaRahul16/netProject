package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;

public class ACS_CCED_NCW_MemberNumberLookup_Test {

	TestSessionInitiator test;
	String app_url, caseID;
	String app_url_IWEB = getYamlValue("app_url_IWEB");
	static Map<String, String> memberDetailsMap;
	Map<String, Boolean> skipTest = new HashMap<String, Boolean>();

	public ACS_CCED_NCW_MemberNumberLookup_Test() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "CCED_NCW_MemberNumberLookup";
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_CCED_NCW_MemberNumberLookup_Test(String caseID) {
		this.caseID = caseID;
	}

	@Test
	public void Step00_Member_Number_Lookup_Test() {
		Reporter.log("CaseID Under Test Is : " + caseID, true);
		test.homePageIWEB
				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.selectAndRunQueryMembership("Query Individual",
				"Selenium - Find Active Regular Member");
		memberDetailsMap = test.memberShipPage.getMemberDetails_Iweb();
		test.navigateToURL(test.homePageIWEB.map().get("Application URL"));
		test.memNumLookupPage.enterMemberDetailsInMemberNumberLookup(
				memberDetailsMap.get("firstName"),
				memberDetailsMap.get("lastName"),
				memberDetailsMap.get("emailID"));
		test.memNumLookupPage.checkCertify();
		test.memNumLookupPage.clickOnSubmitButton();
		test.memNumLookupPage.verifyMemberName(
				memberDetailsMap.get("firstName"),
				memberDetailsMap.get("lastName"));
		test.memNumLookupPage.verifyMemberNumber(memberDetailsMap
				.get("memberNumber"));

		test.memNumLookupPage.verifyThankYouMessage();
	}

	@Test
	public void Step01_Verify_Email_Address_IWEB_Test() {
		Reporter.log("CaseID Under Test Is : " + caseID, true);
		test.homePageIWEB
				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
		test.homePageIWEB.clickFindForIndividualsSearch();
		test.individualsPage.fillMemberDetailsAndSearch("Record Number",
				test.homePageIWEB.map().get("Cordinator_MemberNumber"));
		test.individualsPage.navigateToContactInfoMenuOnHoveringMore();
		test.individualsPage.verifyNCW_CCEDEmailPresent("ncw/cced",
				test.homePageIWEB.map().get("ncw/cced_email"));

	}

	@Test
	public void Step02_CCED_Lookup_Test() {
		Reporter.log("CaseID Under Test Is : " + caseID, true);
		test.launchApplication(test.asm_CCEDPage.map().get("Application URL"));
		test.asm_CCEDPage.verifyPageTitle("Chemists Celebrate Earth Day");
		test.asm_CCEDPage
				.selectSearchTypeAndNavigateToCoordinator(test.asm_CCEDPage
						.map().get("searchMethod"));
		test.asm_CCEDPage.FillOutFormToContactCoordinatorAndClickSubmit(
				"First Name", "Last Name", "Email Address", "City",
				"Phone Number");
		test.asm_CCEDPage.VerifyThankyouMessage();
	}

	@Test
	public void Step03_NCW_Lookup_Test() {
		Reporter.log("CaseID Under Test Is : " + caseID, true);
		test.launchApplication(test.asm_NCWPage.map().get("Application URL"));
		test.asm_NCWPage.verifyPageTitle("National Chemistry Week");
		test.asm_NCWPage
				.selectSearchTypeAndNavigateToCoordinator(test.asm_NCWPage
						.map().get("searchMethod"));
		test.asm_NCWPage.FillOutFormToContactCoordinatorAndClickSubmit(
				"First Name", "Last Name", "Email Address", "City",
				"Phone Number");
		test.asm_NCWPage.VerifyThankyouMessage();
	}

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(
				YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.addValuesInMap("CCED_NCW_MemberNumberLookup", caseID);
		test.homePageIWEB.EnterTestMethodNameToSkipInMap_MemberNumber_CCED_NCW(
				skipTest, test.asm_NCWPage.map().get("Application URL"));
	}

	@BeforeMethod
	public void Skip_Tests_For_Different_Apps(Method method) {

		if (!skipTest.containsKey(method.getName())) {
			skipTest.put(method.getName(), false);
		}
		if (skipTest.get(method.getName())) {
			throw new SkipException("Tests Skipped for expected work flows!");
		}
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
