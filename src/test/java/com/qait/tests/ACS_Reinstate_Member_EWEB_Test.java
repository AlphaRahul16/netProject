package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.HashMap;
import java.util.List;
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
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.keywords.YamlInformationProvider;

public class ACS_Reinstate_Member_EWEB_Test extends BaseTest{

	static String invoiceNumber, yearsOfService;
	List<String> memberContactDetails;
	YamlInformationProvider getKeyValue;
	String csvdatafilepath_OMA = getYamlValue("csv-data-file.path_OMA");
	String csvdatafilepath_PriceValues = getYamlValue("csv-data-file.path_PriceValue");
	String csvSeparator = getYamlValue("csv-data-file.data-separator");
	String memberName, productSubTotal, Total;
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

	ACS_Reinstate_Member_EWEB_Test(Map<String, Object> usereInfoMap) {
		this.userInfo = usereInfoMap;
		getKeyValue = new YamlInformationProvider(usereInfoMap);
	}

	@Test
	public void Step00_Launch_IWEB_Application_Under_Test() {
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB
				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test
	public void Step01_Get_Inactive_Regular_Member_List() {
		test.homePageIWEB.GoToMemberShipModule();
		test.memberShipPage
				.selectAndRunQueryMembership("Query Membership","SELENIUM - Find Inactive Regular Member");
	}

	@Test
	public void Step02_Select_First_Inactive_Regular_Member_And_Get_Member_Details() {
		memberContactDetails = test.memberShipPage
				.selectFirstInactiveRegularMemberAndVerifyExistingDetails();
		yearsOfService = test.memberShipPage.numberOfYearsForInactiveMember();
	}

	@Test
	public void Step03_Launch_EWEB_Application_Under_Test() {
		test.launchApplication(app_url);
	}

	@Test
	public void Step04_Enter_Contact_Information() {
		test.ContactInfoPage.enterContactInformationWithMemberNumber(
				memberContactDetails.get(3), memberContactDetails.get(1),
				memberContactDetails.get(2), memberContactDetails.get(4),
				getKeyValue.getContactInfoName("address", "type"),
				getKeyValue.getContactInfoName("address", "address"),
				getKeyValue.getContactInfoName("address", "city"),
				getKeyValue.getContactInfoName("address", "country"),
				getKeyValue.getContactInfoName("address", "state"),
				getKeyValue.getContactInfoName("address", "zipCode"));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("Education & Employment");
	}

	@Test
	public void Step05_Enter_Education_And_Employment_Info() {
		String caseId = getKeyValue.getEduEmpInfo("CASEID");
		Reporter.log("****** TEST CASE ID : " + caseId + " ******\n", true);
		test.EduAndEmpPage.enterEducationAndEmploymentInformation(getKeyValue
				.getEduEmpInfo("CASEID"));
		test.ContactInfoPage.clickContinue();
		isErrorMessage = test.EduAndEmpPage.verifyDisplayedMessage(getKeyValue
				.getEduEmpInfo("CASEID"));
		errorMap.put(caseId, isErrorMessage);
	}

	@Test
	public void Step06_Enter_Benefits_Info() {
		String caseId = getKeyValue.getEduEmpInfo("CASEID");
		Reporter.log("****** TEST CASE ID : " + caseId + " ******\n", true);
		test.homePage.verifyCurrentTab("Benefits");
		test.BenefitsPage.addACSPublicationAndTechnicalDivision(getKeyValue
				.getEduEmpInfo("CASEID"));
		test.BenefitsPage.verifyCENPresent(getKeyValue.getEduEmpInfo("CASEID"));
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("Checkout");
	}

	@Test
	public void Step07_Verify_Contact_Info_And_Enter_Payment_At_Checkout_Page() {
		String caseId = getKeyValue.getEduEmpInfo("CASEID");
		Reporter.log("****** TEST CASE ID : " + caseId + " ******\n", true);
		String emailIdAcsOrg = memberContactDetails.get(3);
		quantities = test.checkoutPage.verifyPriceValues(getKeyValue
				.getEduEmpInfo("CASEID"));
		test.checkoutPage.verifyMemberDetail(getKeyValue
				.getEduEmpInfo("CASEID"));
		test.checkoutPage.verifyMemberEmail(emailIdAcsOrg);
		productSubTotal = test.checkoutPage.verifyProductSubTotal("4",
				"Product Subtotal");
		Total = test.checkoutPage.verifyTotal();
		test.checkoutPage.verifyTechnicalDivision(getKeyValue
				.getEduEmpInfo("CASEID"));
		test.checkoutPage
				.verifyPublication(getKeyValue.getEduEmpInfo("CASEID"));
		test.checkoutPage.enterPaymentInfo(
				getKeyValue.getCreditCardInfo("Type"),
				getKeyValue.getCreditCardInfo("Holder-name"),
				getKeyValue.getCreditCardInfo("Number"),
				getKeyValue.getCreditCardInfo("cvv-number"));
		test.checkoutPage.clickAtTestStatement();
		test.ContactInfoPage.clickContinue();
		test.checkoutPage.clickSubmitButton();
		test.homePage.verifyCurrentTab("Confirmation");
	}

	@Test
	public void Step08_Verify_Member_Details_At_Confirmation_Page() {
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

	@Test
	public void Step09_Launch_IWEB_Application_Under_Test() {
		test.homePageIWEB.navigateToUrl(app_url_IWEB);
		test.homePageIWEB
				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test
	public void Step10_Verify_Member_Reinstated_Successfully() {
		invoiceNumber = memberDetail[1];
		test.homePageIWEB.clickFindForIndividualsSearch();
		String memberNumber = memberContactDetails.get(4);
		test.individualsPage.fillMemberDetailsAndSearch("Record Number",
				memberNumber);
		test.memberShipPage.numberOfYearsForActiveMember(yearsOfService);
		test.memberShipPage.verifyMemberReinstatedSuccessfully(getKeyValue
				.getEduEmpInfo("CASEID"));
		test.memberShipPage.editSameEmailId(memberContactDetails.get(6));
	}

	@Test
	public void Step11_Verify_Member_Details_And_Benefits_And_Invoice_In_IWEB() {
		String sameEmailId = memberContactDetails.get(6);
		test.individualsPage.verifyMemberDetails(memberContactDetails.get(1),
				memberContactDetails.get(2),
				getKeyValue.getContactInfoName("address", "address"),
				getKeyValue.getContactInfoName("address", "city"),
				getKeyValue.getContactInfoName("address", "zipCode"),
				getKeyValue.getContactInfoName("address", "type"),
				memberContactDetails.get(4), sameEmailId);
		test.individualsPage.verifyIndividualProfileDetails(
				getKeyValue.getEduEmpInfo("CASEID"), quantities);
		test.individualsPage.verifyMemberBenefitsDetail_Reinstate(
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

}
