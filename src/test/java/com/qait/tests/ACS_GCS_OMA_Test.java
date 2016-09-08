package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_GCS_OMA_Test extends BaseTest {

	String memberName, productSubTotal, Total, userEmail, currency = "₹";
	String[] userDetail;
	String[] memberDetail, quantities;
	//static String caseID;
	String app_url = getYamlValue("app_url_OMA");
	String app_url_IWEB = getYamlValue("app_url_IWEB");

	ACS_GCS_OMA_Test() {
		DataProvider_FactoryClass.sheetName = "GCS_OMA";
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_GCS_OMA_Test(String caseIDs) {
		BaseTest.caseID = caseIDs;
	}

	@Test
	public void Step01_Launch_Application_Under_Test() {

		test.homePageIWEB.addValuesInMap("GCS_OMA", caseID);
		test.launchApplication(app_url);
		test.homePage.verifyUserIsOnHomePage("");
	}

	@Test(dependsOnMethods = "Step01_Launch_Application_Under_Test")
	public void Step02_Enter_Contact_Information() {

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

		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		test.EduAndEmpPage.enterEducationAndEmploymentInformation();
		test.ContactInfoPage.clickContinue();

	}

	@Test(dependsOnMethods = "Step03_Enter_Education_And_Employment_Info")
	public void Step04_Enter_Benefits_Info() {

		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		test.homePage.verifyCurrentTab("Benefits");
		test.BenefitsPage.addACSPublicationAndTechnicalDivision(caseID);
		test.BenefitsPage.verifyCENPresent(caseID);
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("Checkout");
	}

	@Test(dependsOnMethods = "Step04_Enter_Benefits_Info")
	public void Step05_Verify_Contact_Info_And_Enter_Payment_At_Checkout_Page() {
		test.checkoutPage.selectCurrency("Indian Rupee");
		test.checkoutPage.verifyMultiYearShow_Hide(test.checkoutPage.map().get(
				"multiYearFlag?"));

		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);

		test.checkoutPage.verifyMemberDetail(caseID);
		test.checkoutPage.verifyMemberEmail(userEmail);
		productSubTotal = test.checkoutPage.verifyProductSubTotal("4",
				"Product Subtotal");
		Total = test.checkoutPage.verifyTotal(currency);
		test.checkoutPage.verifyTechnicalDivision(caseID);
		test.checkoutPage.verifyPublication(caseID);
		test.checkoutPage.clickAtTestStatement();

		test.checkoutPage.clickOnPayInINRButton();
		test.checkoutPage.verifyHeadingAtCheckoutPage();
		test.checkoutPage
				.clickOnPaymentTypeButton("Proceed with payment in INR");
		test.checkoutPage.waitForLoaderToDisappear();
		test.checkoutPage.selectOnPaymentMethodButton(test.checkoutPage.map()
				.get("Payment Type"));
		test.checkoutPage.checkIAgreeTermsAndCondition();
		test.checkoutPage.clickOnContinueButton();

		test.gcsPaymentPage.verifyPageTitleExact("Paynetz");

	}

	@Test(dependsOnMethods = "Step05_Verify_Contact_Info_And_Enter_Payment_At_Checkout_Page")
	public void Step06_TC01_Bank_Payment_Page() {
		test.gcsPaymentPage
				.EnterDetailsOnBankPaymentPageAndProcessFutherSimulation(
						test.gcsPaymentPage.map().get("Payment Type"),
						test.gcsPaymentPage.map().get("Mobile_Number"),
						test.gcsPaymentPage.map().get("Email_Id"),
						test.gcsPaymentPage.map().get("Card Number"),
						test.gcsPaymentPage.map().get("FirstName") + " "
								+ test.gcsPaymentPage.map().get("LastName"),
						test.gcsPaymentPage.map().get(
								"CreditCardExpiration_Month"),
						test.gcsPaymentPage.map().get(
								"CreditCardExpiration_Year"),
						test.gcsPaymentPage.map().get("CVV_Number"),
						test.gcsPaymentPage.map().get("Bank_Name"));
	}

	@Test(dependsOnMethods = "Step06_TC01_Bank_Payment_Page")
	public void Step06_Verify_Details_At_Confirmation_Page() {

		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		memberDetail = test.confirmationPage.verifyMemberDetails(
				test.homePageIWEB.map().get("City"), test.homePageIWEB.map()
						.get("ZipCode"),
				test.homePageIWEB.map().get("Country"), test.homePageIWEB.map()
						.get("Address"));
		test.checkoutPage.verifyMemberName_GCSOMA(caseID);

	}

	@Test(dependsOnMethods = "Step06_Verify_Details_At_Confirmation_Page")
	public void Step07_Launch_Application_Under_Test() {

		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		test.launchApplication(app_url_IWEB);
		test.homePage.enterAuthentication(
				YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB
				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test(dependsOnMethods = "Step07_Launch_Application_Under_Test")
	public void Step08_Search_Member_In_Individual_Test() {

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
		test.individualsPage.verifyIndividualProfileDetails_GCSOMA();
		test.individualsPage.verifyMemberBenefitsDetail_GCSOMA(caseID,
				invoiceNumber);
		test.homePageIWEB.clickOnSideBarTab("Invoice");
		test.memberShipPage.clickOnSideBar("Find Invoice");

		test.invoicePage
				.verifyInvoiceDetailsGCSOMA(invoiceNumber, Total, "Yes");

		test.individualsPage.ClickonMoreAndMenuNameLink("Payments");
		test.invoicePage.expandChildTabMenuAndverifyGCSOMA();
		test.invoicePage.getTableHeadingsAndVerifyPaymentValues(
				"acs global constituent system log", "INR", test.homePageIWEB
						.map().get("Payment Type"));
	}

	/**
	 * * Following methods are to setup and clean up the tests
	 */
	@BeforeClass
	public void Start_Test_Session() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
	}

}
