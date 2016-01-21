package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.keywords.YamlInformationProvider;

public class ACS_AACT_Smoke_Test {

	TestSessionInitiator test;
	YamlInformationProvider getKeyValueAACT;

	String csvdatafilepath_AACT_OMA = getYamlValue("csv-data-file.path_AACT_OMA");
	String csvSeparator = getYamlValue("csv-data-file.data-separator");

	String memberName, productSubTotal, Total;
	ArrayList<String> userUniqueDetail;
	String userEmail;
	String[] memberDetail;
	Map<String, Object> userInfo = null;
	String app_url_AACT_OMA = getYamlValue("app_url_AACT");
	String app_url_IWEB = getYamlValue("app_url_IWEB");
	Map<String, Boolean> individualMember = new HashMap<String, Boolean>(); // To
	// Save
	// the
	// individual member
	// state
	// for
	// recovery

	boolean isIndividualMember; // To Save the individual member state for
								// recovery

	ACS_AACT_Smoke_Test(Map<String, Object> usereInfoMap) {
		this.userInfo = usereInfoMap;
		getKeyValueAACT = new YamlInformationProvider(usereInfoMap);
	}

	@Test
	public void Step01_Launch_Application_Under_Test() {

		String caseId = getKeyValueAACT.get_AACTInfo("CASEID");
		Reporter.log("****** TEST CASE ID : " + caseId + " ******\n", true);
		test.launchApplication(app_url_AACT_OMA);
		test.homePage.verifyUserIsOnHomePage("");

	}

	@Test
	public void Step02_Enter_Contact_Information() {
		String caseId = getKeyValueAACT.get_AACTInfo("CASEID");
		Reporter.log("****** TEST CASE ID : " + caseId + " ******\n", true);
		userUniqueDetail = test.ContactInfoPage
				.enterContactInformation_AACT(caseId);
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyAboutYouPage(caseId);
		isIndividualMember = test.ContactInfoPage
				.getCreateOnlyIndividualMember(caseId);
		test.ContactInfoPage.enterTestMethodNameToSkipInMap(individualMember,
				isIndividualMember);
		userEmail = userUniqueDetail.get(0);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
	}

	@Test
	public void Step03_Enter_Member_Details_In_About_You_Page() {
		String caseId = getKeyValueAACT.get_AACTInfo("CASEID");
		Reporter.log("****** TEST CASE ID : " + caseId + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		test.asm_aactPage.enterMemberDetailsAtAboutYouPage(caseId);
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCheckoutPage(caseId);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
	}

	@Test
	public void Step04_Verify_Contact_Info_And_Enter_Payment_At_Checkout_Page() {
		String caseId = getKeyValueAACT.get_AACTInfo("CASEID");
		Reporter.log("****** TEST CASE ID : " + caseId + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);

		test.checkoutPage.verifyMemberDetail_AACTOMA(getKeyValueAACT
				.get_AACTInfo("CASEID"));
		test.checkoutPage.verifyMemberEmail(userEmail);
		test.checkoutPage.verifyAACTNationalMembership(caseId);
		test.checkoutPage.verifyPriceValues_AACT(caseId);
		productSubTotal = test.checkoutPage.verifyProductSubTotal("4",
				"Product Subtotal");
		test.checkoutPage.selectAndVerifyAllAndDefaultDeliveryMethods(caseId);
		test.checkoutPage.enterPaymentInfo(
				getKeyValueAACT.getCreditCardInfo("Type"),
				userUniqueDetail.get(1) + " " + userUniqueDetail.get(2),
				getKeyValueAACT.getCreditCardInfo("Number"),
				getKeyValueAACT.getCreditCardInfo("cvv-number"));
		test.checkoutPage.clickAtTestStatement();
		test.ContactInfoPage.clickContinue();
		test.checkoutPage.clickSubmitButtonAtBottom();
		test.homePage.verifyCurrentTab("Confirmation");
	}

	@Test
	public void Step05_Verify_Details_At_Confirmation_Page() {
		String caseId = getKeyValueAACT.get_AACTInfo("CASEID");
		Reporter.log("****** TEST CASE ID : " + caseId + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		memberDetail = test.confirmationPage.verifyMemberDetails_AACTOMA(
				caseId, userUniqueDetail.get(1), userUniqueDetail.get(2));
		test.checkoutPage.verifyAACTNationalMembership(caseId);
		test.checkoutPage.verifyPriceValues_AACT(caseId);
		test.confirmationPage.verifyPrintReceiptContent(caseId,
				memberDetail[0], memberDetail[1], userUniqueDetail.get(1),
				userUniqueDetail.get(2));

	}

	@Test
	public void Step06_Launch_Application_Under_Test() {
		String caseId = getKeyValueAACT.get_AACTInfo("CASEID");
		Reporter.log("****** TEST CASE ID : " + caseId + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);

		test.navigateToIWEBUrlOnNewBrowserTab(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(
				getKeyValueAACT.getAuthenticationInfo("userName"),
				getKeyValueAACT.getAuthenticationInfo("password"));
		test.homePageIWEB
				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test
	public void Step07_Search_Member_In_IWEB_Application_And_Verify_Member_Details() {
		String caseId = getKeyValueAACT.get_AACTInfo("CASEID");
		Reporter.log("****** TEST CASE ID : " + caseId + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);

		test.homePageIWEB.clickFindForIndividualsSearch();
		String memberNumber = memberDetail[0];
		test.individualsPage.fillMemberDetailsAndSearch("Record Number",
				memberNumber);
		test.individualsPage.verifyAACTMemberCreated(caseId);
		test.individualsPage.verifyMemberIsNotCreated();
		test.individualsPage.verifyMemberReceivedNoBenefits();
		test.individualsPage.verifyMemberDetails_AACTOMA(caseId, memberNumber,
				userEmail, userUniqueDetail.get(1), userUniqueDetail.get(2));
		String invoiceNumber = memberDetail[1];
		test.individualsPage.verifyMemberBenefitsDetail_AACTOMA(
				getKeyValueAACT.get_AACTInfo("CASEID"), invoiceNumber);
		test.homePageIWEB.clickOnSideBarTab("Invoice");
		test.memberShipPage.clickOnSideBar("Find Invoice");
		test.invoicePage.verifyInvoicedDetails_AACTOMA(
				getKeyValueAACT.get_AACTInfo("CASEID"), "Invoice",
				invoiceNumber);
	}

	@Test
	public void Step08_Search_Individual_In_IWEB_Application_And_Verify_Details() {
		String caseId = getKeyValueAACT.get_AACTInfo("CASEID");
		Reporter.log("****** TEST CASE ID : " + caseId + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		test.homePageIWEB.clickFindForIndividualsSearch();
		test.individualsPage.fillMemberDetailsAndSearch("E-Mail Address",
				userEmail);
		test.individualsPage.verifyAACTMemberCreated(caseId);
		test.individualsPage.verifyMemberIsNotCreated();
		test.individualsPage.verifyMemberReceivedNoBenefits();
		test.individualsPage.verifyMemberDetails_AACTOMA_Individual(caseId,
				userEmail, userUniqueDetail.get(1), userUniqueDetail.get(2));
	}

	/**
	 * * Following methods are to setup and clean up the tests
	 */
	@BeforeClass
	public void Start_Test_Session() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
	}

	@BeforeMethod
	public void Skip_Tests_For_Expected_Member_Created(Method method) {
		if (!individualMember.containsKey(method.getName())) {
			individualMember.put(method.getName(), false);
		}
		System.out.println(individualMember.get(method.getName()));
		if (individualMember.get(method.getName())) {
			throw new SkipException("Tests Skipped for expected work flows!");
		}
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
	}

	 @AfterClass(alwaysRun = true)
	public void Close_Test_Session() {
		//test.closeBrowserSession();
	}

}
