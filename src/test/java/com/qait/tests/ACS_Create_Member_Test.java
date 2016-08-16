package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.HashMap;
import java.util.Map;

import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_Create_Member_Test extends BaseTest {

	private String caseID;
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

	ACS_Create_Member_Test() {

		com.qait.tests.DataProvider_FactoryClass.sheetName = "OMA";
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_Create_Member_Test(String caseID) {
		this.caseID = caseID;
	}

	@Test
	public void Step01_Launch_Application_Under_Test() {
		
		test.homePageIWEB.addValuesInMap("OMA", caseID);
		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		test.launchApplication(app_url);
		test.homePage.verifyUserIsOnHomePage("");
	}

	@Test(dependsOnMethods = "Step01_Launch_Application_Under_Test")
	public void Step02_Enter_Contact_Information() {

		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);

		userDetail = test.ContactInfoPage.enterContactInformation(
				test.homePageIWEB.map().get("Email"), test.homePageIWEB.map()
						.get("FirstName"),
				test.homePageIWEB.map().get("LastName"), test.homePageIWEB
						.map().get("AddressType"),
				test.homePageIWEB.map().get("Address"), test.homePageIWEB.map()
						.get("City"), test.homePageIWEB.map().get("Country"),
				test.homePageIWEB.map().get("State"), test.homePageIWEB.map()
						.get("ZipCode"));
		test.ContactInfoPage.clickContinue();
		userEmail = userDetail[0];
		test.homePage.verifyCurrentTab("Education & Employment");
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
	}

	@Test(dependsOnMethods = "Step02_Enter_Contact_Information")
	public void Step03_Enter_Education_And_Employment_Info() {

		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		test.EduAndEmpPage.enterEducationAndEmploymentInformation(caseID);
		test.ContactInfoPage.clickContinue();
		isErrorMessage = test.EduAndEmpPage.verifyDisplayedMessage(caseID);
		errorMap.put(caseID, isErrorMessage);
	}

	@Test(dependsOnMethods = "Step03_Enter_Education_And_Employment_Info")
	public void Step04_Enter_Benefits_Info() {

		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		test.homePage.verifyCurrentTab("Benefits");
		test.BenefitsPage.addACSPublicationAndTechnicalDivision(caseID);
		test.BenefitsPage.verifyCENPresent(caseID);
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("Checkout");
	}

	@Test(dependsOnMethods = "Step04_Enter_Benefits_Info")
	public void Step05_Verify_Contact_Info_And_Enter_Payment_At_Checkout_Page() {

		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		quantities = test.checkoutPage.verifyPriceValues(caseID);
		test.checkoutPage.verifyMemberDetail(caseID);
		test.checkoutPage.verifyMemberEmail(userEmail);
		productSubTotal = test.checkoutPage.verifyProductSubTotal("4",
				"Product Subtotal");
		Total = test.checkoutPage.verifyTotal();
		test.checkoutPage.verifyTechnicalDivision(caseID);
		test.checkoutPage.verifyPublication(caseID);
		test.checkoutPage.enterPaymentInfo(
				YamlReader.getYamlValue("creditCardInfo.Type"), userDetail[1] + " "
						+ userDetail[2],
				YamlReader.getYamlValue("creditCardInfo.Number"),
				YamlReader.getYamlValue("creditCardInfo.cvv-number"));
		test.checkoutPage.clickAtTestStatement();
		test.ContactInfoPage.clickContinue();
		test.checkoutPage.clickSubmitButtonAtBottom();
		test.homePage.verifyCurrentTab("Confirmation");
	}

	@Test(dependsOnMethods = "Step05_Verify_Contact_Info_And_Enter_Payment_At_Checkout_Page")
	public void Step06_Verify_Details_At_Confirmation_Page() {

		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		memberDetail = test.confirmationPage.verifyMemberDetails(
				test.homePageIWEB.map().get("City"), test.homePageIWEB.map()
						.get("ZipCode"),
				test.homePageIWEB.map().get("Country"), test.homePageIWEB.map()
						.get("Address"));
		test.checkoutPage.verifyMemberName(caseID);
		test.checkoutPage.verifyTechnicalDivision(caseID);
		test.checkoutPage.verifyPublication(caseID);
	}

	@Test(dependsOnMethods = "Step06_Verify_Details_At_Confirmation_Page")
	public void Step07_Launch_Application_Under_Test() {

		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		test.launchApplication(app_url_IWEB);
		test.homePage.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"), YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB
				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test(dependsOnMethods = "Step07_Launch_Application_Under_Test")
	public void Step08_Search_Member_In_Individual_Test() {
		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		String invoiceNumber = memberDetail[1];
		test.homePageIWEB.clickFindForIndividualsSearch();
		String memberNumber = memberDetail[0];
		test.individualsPage.fillMemberDetailsAndSearch("Record Number",
				memberNumber);
		test.individualsPage.verifyMemberDetails_OMA(test.homePageIWEB.map()
				.get("FirstName"), test.homePageIWEB.map().get("LastName"),
				test.homePageIWEB.map().get("Address"), test.homePageIWEB.map()
						.get("City"), test.homePageIWEB.map().get("ZipCode"),
				test.homePageIWEB.map().get("AddressType"), memberDetail[0],
				userEmail, caseID);
		test.individualsPage.verifyIndividualProfileDetails(caseID, quantities);
		test.individualsPage.verifyMemberBenefitsDetail(caseID, invoiceNumber);
		test.homePageIWEB.clickOnSideBarTab("Invoice");
		test.memberShipPage.clickOnSideBar("Find Invoice");
		test.invoicePage.verifyInvoicedDetails(caseID, "Invoice",
				invoiceNumber, quantities, Total);
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

		if (!errorMap.containsKey(caseID)) {
			errorMap.put(caseID, false);
		}
		if (errorMap.get(caseID)) {
			throw new SkipException(
					"Tests Skipped due to expected error found!");
		}
	}
}
