package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_OMA_Discount_Test extends BaseTest {
	private String caseID;
	String memberName, productSubTotal, Total, userEmail, currency = "$";
	String[] userDetail;
	String[] memberDetail;
	// static String caseID;
	String app_url = getYamlValue("app_url_OMA");
	String app_url_IWEB = getYamlValue("app_url_IWEB");

	ACS_OMA_Discount_Test() {
		DataProvider_FactoryClass.sheetName = "OMA_Discount";
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_OMA_Discount_Test(String caseIDs) {
		this.caseID = caseIDs;
	}

	@Test
	public void Step01_Launch_Application_Under_Test() {
		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		ASCSocietyGenericPage.addValuesInMap("OMA_Discount", caseID);
		test.launchApplication(app_url);
		test.homePage.verifyUserIsOnHomePage("");
	}

	@Test(dependsOnMethods = "Step01_Launch_Application_Under_Test")
	public void Step02_Enter_Contact_Information() {
		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		userDetail = test.ContactInfoPage.enterContactInformation(ASCSocietyGenericPage.map().get("Email"),
				ASCSocietyGenericPage.map().get("FirstName"), ASCSocietyGenericPage.map().get("LastName"),
				ASCSocietyGenericPage.map().get("AddressType"), ASCSocietyGenericPage.map().get("Address"),
				ASCSocietyGenericPage.map().get("City"), ASCSocietyGenericPage.map().get("Country"), "",
				ASCSocietyGenericPage.map().get("ZipCode"));
		test.ContactInfoPage.clickContinue();
		userEmail = userDetail[0];
		test.homePage.verifyCurrentTab("Education & Employment");
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
	}

	@Test(dependsOnMethods = "Step02_Enter_Contact_Information")
	public void Step03_Enter_Education_And_Employment_Info() {
		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		test.EduAndEmpPage.enterEducationAndEmploymentInformation();
		test.ContactInfoPage.clickContinue();
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
		test.checkoutPage.verifyMultiYearShow_Hide(ASCSocietyGenericPage.map().get("multiYearFlag?"));
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		test.checkoutPage.verifyPriceValues_OMADiscount(caseID);
		test.checkoutPage.verifyMemberDetail(caseID);
		test.checkoutPage.verifyMemberEmail(userEmail);
		productSubTotal = test.checkoutPage.verifyProductSubTotal("4", "Product Subtotal");
		Total = test.checkoutPage.verifyTotal(currency);
		test.checkoutPage.verifyTechnicalDivision(caseID);
		test.checkoutPage.verifyPublication(caseID);
		// test.checkoutPage.enterPaymentInfo(
		// YamlReader.getYamlValue("creditCardInfo.Type"), userDetail[1]
		// + " " + userDetail[2],
		// YamlReader.getYamlValue("creditCardInfo.Number"),
		// YamlReader.getYamlValue("creditCardInfo.cvv-number"));
		test.asm_storePage.enterPaymentInfo("CardholderName",  userDetail[1] + " " + userDetail[2]);
		test.asm_storePage.enterPaymentInformation_OMAForAllPaymentTypes(
				ASCSocietyGenericPage.map().get("Payment_Method"),
				ASCSocietyGenericPage.map().get("Visa/MC_Card_Number"),
				ASCSocietyGenericPage.map().get("Diners_Card_Number"),
				ASCSocietyGenericPage.map().get("Reference_Number"),
				ASCSocietyGenericPage.map().get("Discover_Card_Number"),
				ASCSocietyGenericPage.map().get("AMEX_Card_Number"), ASCSocietyGenericPage.map().get("Expiry_Month"),
				ASCSocietyGenericPage.map().get("CVV_Number"), ASCSocietyGenericPage.map().get("Check_Number"),
				ASCSocietyGenericPage.map().get("Expiry_Year"));

		test.checkoutPage.clickAtTestStatement();
		test.ContactInfoPage.clickContinue();
		test.checkoutPage.clickSubmitButtonAtBottom();
		test.homePage.verifyCurrentTab("Confirmation");
	}

	@Test(dependsOnMethods = "Step05_Verify_Contact_Info_And_Enter_Payment_At_Checkout_Page")
	public void Step06_Verify_Details_At_Confirmation_Page() {
		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		memberDetail = test.confirmationPage.verifyMemberDetails(ASCSocietyGenericPage.map().get("City"),
				ASCSocietyGenericPage.map().get("ZipCode"), ASCSocietyGenericPage.map().get("Country"),
				ASCSocietyGenericPage.map().get("Address"));
		System.out.println("invoice : " + memberDetail[1]);
		test.checkoutPage.verifyMemberName(caseID);
		test.checkoutPage.verifyTechnicalDivision(caseID);
		test.checkoutPage.verifyPublication(caseID);
	}

	@Test(dependsOnMethods = "Step06_Verify_Details_At_Confirmation_Page")
	public void Step07_Launch_Application_Under_Test() {
		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		test.launchApplication(app_url_IWEB);
		test.homePage.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test(dependsOnMethods = "Step07_Launch_Application_Under_Test")
	public void Step08_Search_Member_In_Individual_Test() {
		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		String invoiceNumber = memberDetail[1];
		test.homePageIWEB.clickFindForIndividualsSearch();
		String memberNumber = memberDetail[0];
		test.individualsPage.fillMemberDetailsAndSearch("Record Number", memberNumber);
		test.individualsPage.verifyMemberDetails_OMA(ASCSocietyGenericPage.map().get("FirstName"),
				ASCSocietyGenericPage.map().get("LastName"), ASCSocietyGenericPage.map().get("Address"),
				ASCSocietyGenericPage.map().get("City"), ASCSocietyGenericPage.map().get("ZipCode"),
				ASCSocietyGenericPage.map().get("AddressType"), memberDetail[0], userEmail, caseID);
		test.individualsPage.verifyMemberBenefitsDetail_GCSOMA(caseID, invoiceNumber);
		test.homePageIWEB.clickOnSideBarTab("Invoice");
		test.memberShipPage.clickOnSideBar("Find Invoice");
		test.invoicePage.verifyInvoiceDetailsGCSOMA(invoiceNumber, Total, "Yes");
	}

	/**
	 * * Following methods are to setup and clean up the tests
	 */
	@BeforeClass
	public void Start_Test_Session() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
	}

	@BeforeMethod
	public void handleTestMethodName(Method method) {
		test.printMethodName(method.getName());
	}

}
