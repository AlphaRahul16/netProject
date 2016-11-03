package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_AACT_OMR extends BaseTest {

	private String weblogin, custId,CardHolderName;
	List<String> updatedValues = new ArrayList<String>();
	private String caseID, email, membershipType, invoiceTotal;
	private boolean isPrimary;
	List<String> customerFullNameList;

	String app_url_IWEB = getYamlValue("app_url_IWEB");
	String app_url_AACT_OMR = getYamlValue("app_url_AACT_OMR");

	public ACS_AACT_OMR() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "AACT_OMR";
	}

	@BeforeClass
	public void initiateTestSeesion() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		if (caseID != null) {
			Reporter.log("       TEST CASE ID : " + caseID + "       \n", true);
		}
	}

	@BeforeMethod
	public void printCaseIdExecuted(Method method) {
		test.printMethodName(method.getName());

	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_AACT_OMR(String caseID) {
		this.caseID = caseID;
	}
	
	@Test
	public void Step01_Launch_IWEB_Application() {
		test.homePageIWEB.addValuesInMap("AACT_OMR", caseID);
		test.launchApplication(app_url_IWEB);
		test.homePage.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
	}

	@Test (dependsOnMethods="Step01_Launch_IWEB_Application")
	public void Step02_Select_members_side_bar_tab_and_click_on_Query_membership() {

		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
		test.homePageIWEB.clickOnSideBarTab("Members");
		test.homePageIWEB.clickOnTab("Query Membership");
		test.homePageIWEB.verifyUserIsOnHomePage("Query - Membership");

	}

	@Test(dependsOnMethods="Step02_Select_members_side_bar_tab_and_click_on_Query_membership")
	public void Step03_Select_And_Run_Query_On_Membership_Page() {

		test.memberShipPage.selectAndRunQuery(getYamlValue("AACT_OMR.queryName"));
		test.memberShipPage.enterExpiryDatesBeforeAndAfterForAACTOMR();
		test.memberShipPage.clickOnGoButtonAfterPackageSelection();
	}

	@Test(dependsOnMethods="Step03_Select_And_Run_Query_On_Membership_Page")
	public void Step04_Fetch_CstWebLogin_And_Verify_TermStartDate_And_TermEndDate_In_Invoice_Childform() {

		weblogin = test.memberShipPage.getCstWebLogin();
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("invoices");
		test.memberShipPage.verifyTermStartDateAndTermEndDateIsEmptyForAACT();
		test.memberShipPage.collapseDetailsMenu("invoices");
		test.memberShipPage.clickOnCustomerNameAndNavigateToMembershipPage();
		custId = test.memberShipPage.getCustomerID();
		customerFullNameList = test.memberShipPage.getCustomerFullNameAndContactID();
		
	}

	@Test(dependsOnMethods="Step04_Fetch_CstWebLogin_And_Verify_TermStartDate_And_TermEndDate_In_Invoice_Childform")
	public void Step05_Launch_EWEB_Application_and_Login_with_fetched_Weblogin() {
		test.homePageIWEB.addValuesInMap("AACT_OMR", caseID);
		test.launchApplication(app_url_AACT_OMR);
		test.homePage.verifyUserIsOnHomePage("AACT OMR >> Login");
		test.acs_aactOmr.loginWithWeblogin(weblogin, "password");
		test.award_ewebPage.clickOnLoginButton();
		test.homePage.verifyUserIsOnHomePage("AACT OMR >> Demographics");

	}

	@Test(dependsOnMethods="Step05_Launch_EWEB_Application_and_Login_with_fetched_Weblogin")
	public void Step06_click_on_Edit_link_and_update_contact_info_on_online_membership_renewal_page() {
		email = test.acs_aactOmr.getDetailsfromOnlineMembershipPage("EmailAddress");
		isPrimary = test.acs_aactOmr.getPrimaryAddress();
		test.acs_aactOmr.clickOnLink("edit");
		test.acs_aactOmr.clickButtonByInputValue("Change Email");
		test.acs_aactOmr.editEmailOnUpdateAboutYouPage(email);
		isPrimary = test.acs_aactOmr.makeSchoolAsPrimaryAddress();
		test.acs_aactOmr.clickButtonByInputValue("Return to Renewal");

	}

	@Test(dependsOnMethods="Step06_click_on_Edit_link_and_update_contact_info_on_online_membership_renewal_page")
	public void Step07_Select_value_for_How_do_you_want_to_receive_ChemMatters_Update_details_of_About_You() {
		test.acs_aactOmr.selectValuesForChemMatters("How do you want to receive",
				test.homePageIWEB.map().get("How do you want to receive ChemMatters?"));
		updatedValues = test.acs_aactOmr.updateDetailsfoAboutYouSection();
		//test.acs_aactOmr.clickButtonByInputValue("Save");
	}

	@Test(dependsOnMethods="Step07_Select_value_for_How_do_you_want_to_receive_ChemMatters_Update_details_of_About_You")
	public void Step08_Verify_details_of_About_You_and_Enter_Gender_Experience_gradutaion_Date() {

		test.acs_aactOmr.verifydetailsOnOnlineMembershipRenewalPage(email.replace("XXX", ""), "EmailAddress");
		test.acs_aactOmr.verifyDetailsOfUpdateAboutYou(updatedValues);
		test.acs_aactOmr.verifyWorkAddressIsPrimary(isPrimary);
		test.acs_aactOmr.enterGenderExperienceAndGraduationDetails(test.homePageIWEB.map().get("Gender").trim(),
				test.homePageIWEB.map().get("Experience").trim(), test.homePageIWEB.map().get("GradMonth").trim(),
				test.homePageIWEB.map().get("GradYear").trim());
	}

	@Test(dependsOnMethods="Step08_Verify_details_of_About_You_and_Enter_Gender_Experience_gradutaion_Date")
	public void Step09_Verify_Membership_Invoice_Total_and_Enter_Billing_Information() {
	
		invoiceTotal = test.acs_aactOmr.getDetailsfromOnlineMembershipPage("TotalAmount");
		membershipType = test.acs_aactOmr.getMembershipName("category");
		CardHolderName=customerFullNameList.get(0).trim();
		test.acs_aactOmr.enterPaymentInfo(test.homePageIWEB.map().get("CreditCardType").trim(),CardHolderName,
				test.homePageIWEB.map().get("CreditCardNumber").trim(), test.homePageIWEB.map().get("CcvNumber").trim(),
				test.homePageIWEB.map().get("ExpirationMonth").trim(),
				test.homePageIWEB.map().get("ExpirationYear").trim());
		test.acs_aactOmr.checkAutoRenwealCheckbox("chkAutoRenewal");
		test.acs_aactOmr.clickButtonById("btnNext");
	}

	@Test(dependsOnMethods="Step09_Verify_Membership_Invoice_Total_and_Enter_Billing_Information")
	public void Step10_Verify_Details_On_Summary_Page() {
		test.acs_aactOmr.verifyPageHeader("txt_summaryHeader", "Membership Renewal Summary",
				"Membership Renewal Summary");
		test.acs_aactOmr.verifyMembershipType(membershipType, "category");
		test.acs_aactOmr.verifydetailsOnOnlineMembershipRenewalPage(invoiceTotal, "TotalAmount");
		test.acs_aactOmr.verifyDetailsOfSummaryPage(email.replace("XXX", ""), test.homePageIWEB.map().get("Gender").trim(),
				test.homePageIWEB.map().get("Experience").trim(),
				test.homePageIWEB.map().get("CreditCardType").trim(),CardHolderName,
				test.homePageIWEB.map().get("CreditCardNumber").trim(),
				test.homePageIWEB.map().get("ExpirationMonth").trim(),
				test.homePageIWEB.map().get("ExpirationYear").trim());
	}
	@Test(dependsOnMethods="Step10_Verify_Details_On_Summary_Page")
	public void Step11_Click_On_Submit_Payment(){
		test.acs_aactOmr.clickOnSubmitPaymentOnOnlineMembershipRenewalPage();
		
	}
	@Test(dependsOnMethods="Step11_Click_On_Submit_Payment")
	public void Step12_Click_On_Print_Your_Receipt_and_Verify_Details_For_Print() throws IOException {
		test.acs_aactOmr.verifyPageHeader("title_header", "greydarker-title", "Member Invoice -");
		test.asm_PUBSPage._deleteExistingFIleFile("report");
		test.acs_aactOmr.clickButtonByInputValue("Print your receipt");
		test.asm_PUBSPage.verifyDataFromPdfFileForAACTOMR(membershipType,invoiceTotal);
	}
	@Test(dependsOnMethods="Step12_Click_On_Print_Your_Receipt_and_Verify_Details_For_Print")
	public void Step13_Launch_IWEB_and_Find_Member_and_navigate_To_membership_profile_page() {
		test.launchApplication(app_url_IWEB);
		test.homePage.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
		test.homePageIWEB.clickOnSideBarTab("Members");
		test.homePageIWEB.clickOnTab("Find Members");
		test.homePageIWEB.verifyUserIsOnHomePage("Find - Membership");
		test.individualsPage.selectFieldValueToFindMember("Chapter:", "American Association of Chemistry Teachers (AACT)");
		test.individualsPage.fillMemberDetailsAndSearch("Customer ID",custId);
				
	}
	@Test(dependsOnMethods="Step13_Launch_IWEB_and_Find_Member_and_navigate_To_membership_profile_page")
	public void Step14_verify_term_start_date_and_end_date_is_not_null(){
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("invoices");
		test.memberShipPage.verifyTermStartDateAndEndDatesAreNotEmpty();
	}
	@Test(dependsOnMethods="Step14_verify_term_start_date_and_end_date_is_not_null")
	public void Step15_Verify_auto_pay_membership_flag_is_checked_And_Verify_AACT_membership_details(){
		test.memberShipPage.verifyAutoPayStatusAfterAutoRenewal("chkmk");
		test.memberShipPage.clickOnCustomerName();
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("chapter memberships");
		test.memberShipPage.verifyMemberTypeInIndividualMemberships("American Association");
		
	}
	@Test(dependsOnMethods="Step15_Verify_auto_pay_membership_flag_is_checked_And_Verify_AACT_membership_details")
	public void Step16_Click_on_More_link_and_select_Invoices_Verify_AACT_Member_details_under_Invoices_open_batch_childform(){
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Invoices");
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("invoices (open batch)");
		test.memberShipPage.verifyMembershipTypeInIndividualMemberships("AACTOMR");
	}
	@Test(dependsOnMethods="Step16_Click_on_More_link_and_select_Invoices_Verify_AACT_Member_details_under_Invoices_open_batch_childform")
	public void Step17_Click_on_got_to_record_and_Verify_details_on_Invoice_profile_page(){
		test.memberShipPage.clickOnGoToRecordButton("AACTOMR", "3");
		test.invoicePage.verifyDetailsOfInvoiceProfilePageforAACTOMR(invoiceTotal,"Yes","No");
	}
	@Test(dependsOnMethods="Step17_Click_on_got_to_record_and_Verify_details_on_Invoice_profile_page")
	public void Step18_Click_on_More_link_and_select_Payments_Verify_payment_details_under_payments_child_form (){
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Payments");
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("payments");
		test.memberShipPage.verifyDetailsForPaymentsChildForm("Payment",test.homePageIWEB.map().get("CreditCardType").trim(),membershipType,invoiceTotal);
	}	

}
