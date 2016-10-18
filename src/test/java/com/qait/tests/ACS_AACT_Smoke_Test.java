package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;
import java.util.ArrayList;
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

public class ACS_AACT_Smoke_Test extends BaseTest {

	private String caseID;
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

	ACS_AACT_Smoke_Test() {

		com.qait.tests.DataProvider_FactoryClass.sheetName = "AACT_OMA";
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_AACT_Smoke_Test(String caseID) {
		this.caseID = caseID;
	}

	@Test
	public void Step01_Launch_Application_Under_Test() {
		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		test.homePageIWEB.addValuesInMap("AACT_OMA", caseID);

		test.launchApplication(app_url_AACT_OMA);
		test.homePage.verifyUserIsOnHomePage("");

	}

	@Test(dependsOnMethods = { "Step01_Launch_Application_Under_Test" })
	public void Step02_Enter_Contact_Information() {
		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		userUniqueDetail = test.ContactInfoPage.enterContactInformation_AACT(caseID);
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyAboutYouPage(caseID);
		isIndividualMember = test.ContactInfoPage.getCreateOnlyIndividualMember(caseID);
		test.ContactInfoPage.enterTestMethodNameToSkipInMap(individualMember, isIndividualMember);
		userEmail = userUniqueDetail.get(0);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
	}

	@Test(dependsOnMethods = { "Step02_Enter_Contact_Information" })
	public void Step03_Enter_Member_Details_In_About_You_Page() {
		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		test.asm_aactPage.enterMemberDetailsAtAboutYouPage(caseID);
		test.ContactInfoPage.clickContinue();
		// test.homePage.verifyCheckoutPage(caseId);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
	}

	@Test(dependsOnMethods = { "Step03_Enter_Member_Details_In_About_You_Page" })
	public void Step04_Verify_Contact_Info_And_Enter_Payment_At_Checkout_Page() {
		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);

		test.checkoutPage.verifyMemberDetail_AACTOMA(caseID);
		test.checkoutPage.verifyMemberEmail(userEmail);
		test.checkoutPage.verifyAACTNationalMembership(caseID);
		test.checkoutPage.verifyPriceValues_AACT(caseID);
		productSubTotal = test.checkoutPage.verifyProductSubTotal("4", "Product Subtotal");
		test.checkoutPage.selectAndVerifyAllAndDefaultDeliveryMethods(caseID);
		test.checkoutPage.enterPaymentInfo(YamlReader.getYamlValue("creditCardInfo.Type"),
				userUniqueDetail.get(1) + " " + userUniqueDetail.get(2),
				YamlReader.getYamlValue("creditCardInfo.Number"), YamlReader.getYamlValue("creditCardInfo.cvv-number"));
		test.checkoutPage.clickAtTestStatement();
		test.ContactInfoPage.clickContinue();
		test.checkoutPage.clickSubmitButtonAtBottom();
		test.homePage.verifyCurrentTab("Confirmation");
	}

	@Test(dependsOnMethods = { "Step04_Verify_Contact_Info_And_Enter_Payment_At_Checkout_Page" })
	public void Step05_Verify_Details_At_Confirmation_Page() {
		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		memberDetail = test.confirmationPage.verifyMemberDetails_AACTOMA(caseID, userUniqueDetail.get(1),
				userUniqueDetail.get(2));
		System.out.println("member detail in test:" + memberDetail[0]);
		test.checkoutPage.verifyAACTNationalMembership(caseID);
		test.checkoutPage.verifyPriceValues_AACT(caseID);
		// test.confirmationPage.verifyPrintReceiptContent(caseID,
		// memberDetail[0], memberDetail[1], userUniqueDetail.get(1),
		// userUniqueDetail.get(2));

	}

	@Test(dependsOnMethods = { "Step05_Verify_Details_At_Confirmation_Page" })
	public void Step06_Launch_Application_Under_Test() {
		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);

		test.launchApplication(app_url_IWEB);
		test.homePage.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test(dependsOnMethods = { "Step06_Launch_Application_Under_Test" })
	public void Step07_Search_Member_In_IWEB_Application_And_Verify_Member_Details() {
		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);

		test.homePageIWEB.clickFindForIndividualsSearch();
		String memberNumber = memberDetail[0];
		test.individualsPage.fillMemberDetailsAndSearch("Record Number", memberNumber);
		test.individualsPage.verifyAACTMemberCreated(caseID);
		test.individualsPage.verifyMemberIsNotCreated();
		test.individualsPage.verifyMemberReceivedNoBenefits();
		test.individualsPage.verifyMemberDetails_AACTOMA(caseID, memberNumber, userEmail, userUniqueDetail.get(1),
				userUniqueDetail.get(2));
		String invoiceNumber = memberDetail[1];
		test.individualsPage.verifyMemberBenefitsDetail_AACTOMA(caseID, invoiceNumber);
		test.homePageIWEB.clickOnSideBarTab("Invoice");
		test.memberShipPage.clickOnSideBar("Find Invoice");
		test.invoicePage.verifyInvoicedDetails_AACTOMA(caseID, "Invoice", invoiceNumber);
	}

	@Test(dependsOnMethods = { "Step07_Search_Member_In_IWEB_Application_And_Verify_Member_Details" })
	public void Step08_Search_Individual_In_IWEB_Application_And_Verify_Details() {
		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		test.homePageIWEB.clickFindForIndividualsSearch();
		test.individualsPage.fillMemberDetailsAndSearch("E-Mail Address", userEmail);
		test.individualsPage.verifyAACTMemberCreated(caseID);
		test.individualsPage.verifyMemberIsNotCreated();
		test.individualsPage.verifyMemberReceivedNoBenefits();
		test.individualsPage.verifyMemberDetails_AACTOMA_Individual(caseID, userEmail, userUniqueDetail.get(1),
				userUniqueDetail.get(2));
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
		test.printMethodName(method.getName());
		// if (caseID != null)
		// Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);

		if (!individualMember.containsKey(method.getName())) {
			individualMember.put(method.getName(), false);
		}
		System.out.println(individualMember.get(method.getName()));
		if (individualMember.get(method.getName())) {
			throw new SkipException("Tests Skipped for expected work flows!");
		}
	}

}
