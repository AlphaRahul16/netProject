package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

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

public class ACS_Create_Member_Test {

	TestSessionInitiator test;
	YamlInformationProvider getKeyValue;

	String csvdatafilepath_OMA = getYamlValue("csv-data-file.path_OMA");
	String csvdatafilepath_PriceValues = getYamlValue("csv-data-file.path_PriceValue");
	String csvSeparator = getYamlValue("csv-data-file.data-separator");

	String memberName, productSubTotal, Total, userEmail;
	String[] userDetail;
	String[] memberDetail, quantities;
	Map<String, Object> userInfo = null;
	String app_url = getYamlValue("app_url_OMA");
	String app_url_IWEB = getYamlValue("app_url_IWEB");

	static Map<String, Boolean> errorMap = new HashMap<String, Boolean>(); // To
																			// Save
																			// the
																			// error
																			// state
																			// for
																			// recovery

	boolean isErrorMessage; // To Save the error state for recovery

	ACS_Create_Member_Test(Map<String, Object> usereInfoMap) {
		this.userInfo = usereInfoMap;
		getKeyValue = new YamlInformationProvider(usereInfoMap);
	}

	@Test
	public void Step01_Launch_Application_Under_Test() {
		String caseId = getKeyValue.getEduEmpInfo("CASEID");
		Reporter.log("****** TEST CASE ID : " + caseId + " ******\n", true);
		test.launchApplication(app_url);
		test.homePage.verifyUserIsOnHomePage("");
	}

	@Test(dependsOnMethods="Step01_Launch_Application_Under_Test")
	public void Step02_Enter_Contact_Information() {
		String caseId = getKeyValue.getEduEmpInfo("CASEID");
		Reporter.log("****** TEST CASE ID : " + caseId + " ******\n", true);
		userDetail = test.ContactInfoPage.enterContactInformation(
				getKeyValue.getContactInfoName("email"),
				getKeyValue.getContactInfoName("name", "firstname"),
				getKeyValue.getContactInfoName("name", "lastname"),
				getKeyValue.getContactInfoName("address", "type"),
				getKeyValue.getContactInfoName("address", "address"),
				getKeyValue.getContactInfoName("address", "city"),
				getKeyValue.getContactInfoName("address", "country"),
				getKeyValue.getContactInfoName("address", "state"),
				getKeyValue.getContactInfoName("address", "zipCode"));
		test.ContactInfoPage.clickContinue();
		userEmail = userDetail[0];
		test.homePage.verifyCurrentTab("Education & Employment");
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
	}

	@Test(dependsOnMethods="Step02_Enter_Contact_Information")
	public void Step03_Enter_Education_And_Employment_Info() {
		String caseId = getKeyValue.getEduEmpInfo("CASEID");
		Reporter.log("****** TEST CASE ID : " + caseId + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		test.EduAndEmpPage.enterEducationAndEmploymentInformation(getKeyValue
				.getEduEmpInfo("CASEID"));
		test.ContactInfoPage.clickContinue();
		isErrorMessage = test.EduAndEmpPage.verifyDisplayedMessage(getKeyValue
				.getEduEmpInfo("CASEID"));
		errorMap.put(caseId, isErrorMessage);
	}

	@Test(dependsOnMethods="Step03_Enter_Education_And_Employment_Info")
	public void Step04_Enter_Benefits_Info() {
		String caseId = getKeyValue.getEduEmpInfo("CASEID");
		Reporter.log("****** TEST CASE ID : " + caseId + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		test.homePage.verifyCurrentTab("Benefits");
		test.BenefitsPage.addACSPublicationAndTechnicalDivision(getKeyValue
				.getEduEmpInfo("CASEID"));
		test.BenefitsPage.verifyCENPresent(getKeyValue.getEduEmpInfo("CASEID"));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("Checkout");
	}

	@Test(dependsOnMethods="Step04_Enter_Benefits_Info")
	public void Step05_Verify_Contact_Info_And_Enter_Payment_At_Checkout_Page() {
		String caseId = getKeyValue.getEduEmpInfo("CASEID");
		Reporter.log("****** TEST CASE ID : " + caseId + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		quantities = test.checkoutPage.verifyPriceValues(getKeyValue
				.getEduEmpInfo("CASEID"));
		test.checkoutPage.verifyMemberDetail(getKeyValue
				.getEduEmpInfo("CASEID"));
		test.checkoutPage.verifyMemberEmail(userEmail);
		productSubTotal = test.checkoutPage.verifyProductSubTotal("4",
				"Product Subtotal");
		Total = test.checkoutPage.verifyTotal();
		test.checkoutPage.verifyTechnicalDivision(getKeyValue
				.getEduEmpInfo("CASEID"));
		test.checkoutPage
				.verifyPublication(getKeyValue.getEduEmpInfo("CASEID"));
		test.checkoutPage.enterPaymentInfo(
				getKeyValue.getCreditCardInfo("Type"), userDetail[1] + " "
						+ userDetail[2],
				getKeyValue.getCreditCardInfo("Number"),
				getKeyValue.getCreditCardInfo("cvv-number"));
		test.checkoutPage.clickAtTestStatement();
		test.ContactInfoPage.clickContinue();
		test.checkoutPage.clickSubmitButtonAtBottom();
		test.homePage.verifyCurrentTab("Confirmation");
	}

	@Test(dependsOnMethods="Step05_Verify_Contact_Info_And_Enter_Payment_At_Checkout_Page")
	public void Step06_Verify_Details_At_Confirmation_Page() {
		String caseId = getKeyValue.getEduEmpInfo("CASEID");
		Reporter.log("****** TEST CASE ID : " + caseId + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		memberDetail = test.confirmationPage.verifyMemberDetails(
				getKeyValue.getContactInfoName("address", "city"),
				getKeyValue.getContactInfoName("address", "zipCode"),
				getKeyValue.getContactInfoName("address", "country"),
				getKeyValue.getContactInfoName("address", "address"));
		test.checkoutPage.verifyMemberName(getKeyValue.getEduEmpInfo("CASEID"));
		test.checkoutPage.verifyTechnicalDivision(getKeyValue
				.getEduEmpInfo("CASEID"));
		test.checkoutPage
				.verifyPublication(getKeyValue.getEduEmpInfo("CASEID"));
	}

	@Test(dependsOnMethods="Step06_Verify_Details_At_Confirmation_Page")
	public void Step07_Launch_Application_Under_Test() {
		String caseId = getKeyValue.getEduEmpInfo("CASEID");
		Reporter.log("****** TEST CASE ID : " + caseId + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(
				getKeyValue.getAuthenticationInfo("userName"),
				getKeyValue.getAuthenticationInfo("password"));
		test.homePageIWEB
				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test(dependsOnMethods="Step07_Launch_Application_Under_Test")
	public void Step08_Search_Member_In_Individual_Test() {
		String caseId = getKeyValue.getEduEmpInfo("CASEID");
		Reporter.log("****** TEST CASE ID : " + caseId + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		String invoiceNumber = memberDetail[1];
		test.homePageIWEB.clickFindForIndividualsSearch();
		String memberNumber = memberDetail[0];
		test.individualsPage.fillMemberDetailsAndSearch("Record Number",
				memberNumber);
		test.individualsPage.verifyMemberDetails_OMA(
				getKeyValue.getContactInfoName("name", "firstname"),
				getKeyValue.getContactInfoName("name", "lastname"),
				getKeyValue.getContactInfoName("address", "address"),
				getKeyValue.getContactInfoName("address", "city"),
				getKeyValue.getContactInfoName("address", "zipCode"),
				getKeyValue.getContactInfoName("address", "type"),
				memberDetail[0], userEmail, caseId);
		test.individualsPage.verifyIndividualProfileDetails(
				getKeyValue.getEduEmpInfo("CASEID"), quantities);
		test.individualsPage.verifyMemberBenefitsDetail(
				getKeyValue.getEduEmpInfo("CASEID"), invoiceNumber);
		test.homePageIWEB.clickOnSideBarTab("Invoice");
		test.memberShipPage.clickOnSideBar("Find Invoice");
		test.invoicePage.verifyInvoicedDetails(
				getKeyValue.getEduEmpInfo("CASEID"), "Invoice", invoiceNumber,
				quantities, Total);
	}

	/**
	 * * Following methods are to setup and clean up the tests
	 */
	@BeforeClass
	public void Start_Test_Session() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
	}

	@BeforeMethod
	public void skip_tests_if_error_message() {
		String caseId = getKeyValue.getEduEmpInfo("CASEID");
		if (!errorMap.containsKey(caseId)) {
			errorMap.put(caseId, false);
		}
		if (errorMap.get(caseId)) {
			throw new SkipException(
					"Tests Skipped due to expected error found!");
		}
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
	}

//	 @AfterClass(alwaysRun = true)
	public void Close_Test_Session() {
		test.closeBrowserSession();
	}

}
