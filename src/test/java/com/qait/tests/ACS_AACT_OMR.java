package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_AACT_OMR extends BaseTest {

	private String weblogin, CardHolderName, productName;
	List<String> updatedValues = new ArrayList<String>();
	private String caseID, email, membershipType, invoiceTotal, queryPageUrl;
	private boolean isPrimary;
	List<String> customerFullNameList;
	Map<String, List<String>> updatedDetailsOfMember = new HashMap<String, List<String>>();

	String app_url_IWEB = getYamlValue("app_url_IWEB");
	String app_url_AACT_OMR = getYamlValue("app_url_AACT_OMR");

	public ACS_AACT_OMR() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "AACT_OMR";
	}

	@BeforeClass
	public void initiateTestSession() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());

	}

	@BeforeMethod
	public void printCaseIdExecuted(Method method) {
		test.printMethodName(method.getName());
		if (caseID != null) {
			Reporter.log("       TEST CASE ID : " + caseID + "       \n", true);
		}

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

	@Test(dependsOnMethods = "Step01_Launch_IWEB_Application")
	public void Step02_Select_Members_Tab_And_Select_Valid_User_For_AACT_OMR() {

		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
		test.homePageIWEB.clickOnSideBarTab("Members");
		test.homePageIWEB.clickOnTab("Query Membership");
		queryPageUrl = test.individualsPage.getCurrentURL();
		test.homePageIWEB.verifyUserIsOnHomePage("Query - Membership");
		weblogin = test.memberShipPage.selectValidUserForAACTOMR(getYamlValue("AACT_OMR.queryName"), queryPageUrl);
		test.memberShipPage.clickOnCustomerNameAndNavigateToMembershipPage();
		customerFullNameList = test.memberShipPage.getMemberDetails_AACTOMR();
	}

	@Test(dependsOnMethods = "Step02_Select_Members_Tab_And_Select_Valid_User_For_AACT_OMR")
	public void Step03_Launch_EWEB_Application_And_Login_With_Weblogin() {
		test.homePage.addValuesInMap("AACT_OMR", caseID);
		test.launchApplication(app_url_AACT_OMR);
		test.homePage.verifyUserIsOnHomePage("AACT OMR >> Login");
		// weblogin="amyhageman";
		test.acs_aactOmr.loginWithWeblogin(weblogin, "password");
		test.award_ewebPage.clickOnLoginButton();
		test.homePage.verifyUserIsOnHomePage("AACT OMR >> Demographics");
	}

	@Test(dependsOnMethods = "Step03_Launch_EWEB_Application_And_Login_With_Weblogin")
	public void Step04_Click_On_Edit_Link_And_Update_Contact_Info_On_Online_Membership_Renewal_Page() {
		email = test.acs_aactOmr.getDetailsfromOnlineMembershipPage("EmailAddress");
		test.acs_aactOmr.clickOnLink("edit");
		test.acs_aactOmr.clickButtonByInputValue("Change Email");
		email = test.acs_aactOmr.editEmailOnUpdateAboutYouPage(email);
		isPrimary = test.acs_aactOmr.makeSchoolAddressAsPrimaryAddress();

	}

	@Test(dependsOnMethods = "Step04_Click_On_Edit_Link_And_Update_Contact_Info_On_Online_Membership_Renewal_Page")
	public void Step05_Select_Value_For_Delivery_Method_And_Update_Details_Of_About_You() {
		membershipType = test.acs_aactOmr.getMembershipName("MemberCategory");
		test.acs_aactOmr.selectValuesForChemMatters("How do you want to receive",
				ASCSocietyGenericPage.map().get("How do you want to receive ChemMatters?"), membershipType,
				customerFullNameList.get(2).trim());
		productName = test.acs_aactOmr.getMembershipDetailsFromMembershipInvoiceTable("Product Name");
		updatedDetailsOfMember = test.acs_aactOmr.updateDetailsInAboutYouSection(membershipType);
	}

	@Test(dependsOnMethods = "Step05_Select_Value_For_Delivery_Method_And_Update_Details_Of_About_You")
	public void Step06_Verify_Details_Of_About_You_And_Enter_Gender_Experience_Gradutaion_Date() {

		test.acs_aactOmr.verifydetailsOnOnlineMembershipRenewalPage(email, "EmailAddress");
		test.acs_aactOmr.verifyWorkAddressIsPrimary(isPrimary);
		test.acs_aactOmr.verifyDetailsOfUpdateAboutYou(membershipType, updatedDetailsOfMember);
		test.acs_aactOmr.enterGenderExperienceAndGraduationDetails(ASCSocietyGenericPage.map().get("Gender").trim(),
				ASCSocietyGenericPage.map().get("Experience").trim(),
				ASCSocietyGenericPage.map().get("GradMonth").trim(), ASCSocietyGenericPage.map().get("GradYear").trim(),
				membershipType);
	}

	@Test(dependsOnMethods = "Step06_Verify_Details_Of_About_You_And_Enter_Gender_Experience_Gradutaion_Date")
	public void Step07_Verify_Membership_Invoice_Total_And_Enter_Billing_Information() {

		invoiceTotal = test.acs_aactOmr.getDetailsfromOnlineMembershipPage("TotalAmount");
		CardHolderName = customerFullNameList.get(0).trim();
		String paymentMethod=ASCSocietyGenericPage.map().get("Payment_Method").trim();
		test.acs_aactOmr.enterPaymentInfo(paymentMethod, CardHolderName,
				ASCSocietyGenericPage.map().get(paymentMethod+"_"+"Card_Number").trim(),
				ASCSocietyGenericPage.map().get("CVV_Number").trim(),
				ASCSocietyGenericPage.map().get("Expiry_Month").trim(),
				ASCSocietyGenericPage.map().get("Expiry_Year").trim());
		test.acs_aactOmr.checkAutoRenwealCheckbox(membershipType);
		test.acs_aactOmr.clickButtonById("btnNext", "Continue");
	}

	@Test(dependsOnMethods = "Step07_Verify_Membership_Invoice_Total_And_Enter_Billing_Information")
	public void Step08_Verify_Details_On_Summary_Page_And_Click_On_Submit_Payment_Button() {
		test.acs_aactOmr.verifyPageHeader("txt_summaryHeader", "Membership Renewal Summary",
				"Membership Renewal Summary");
		test.acs_aactOmr.verifyMembershipType(membershipType, "MemberCategory");
		test.acs_aactOmr.verifydetailsOnOnlineMembershipRenewalPage(invoiceTotal, "TotalAmount");
		String paymentMethod=ASCSocietyGenericPage.map().get("Payment_Method").trim();
		test.acs_aactOmr.verifyDetailsOfSummaryPage(email, ASCSocietyGenericPage.map().get("Gender").trim(),
				ASCSocietyGenericPage.map().get("Experience").trim(),
				paymentMethod, CardHolderName,
				ASCSocietyGenericPage.map().get(paymentMethod+"_"+"Card_Number").trim(),
				ASCSocietyGenericPage.map().get("Expiry_Month").trim(),
				ASCSocietyGenericPage.map().get("Expiry_Year").trim());
		test.acs_aactOmr.clickOnSubmitPaymentOnOnlineMembershipRenewalPage();
	}

	@Test(dependsOnMethods = "Step08_Verify_Details_On_Summary_Page_And_Click_On_Submit_Payment_Button")
	public void Step09_Click_On_Print_Your_Receipt_And_Verify_Details_In_Downloaded_PDF() throws IOException {
		test.acs_aactOmr.verifyPageHeader("title_header", "greydarker-title", "Member Invoice -");
		test.asm_PUBSPage._deleteExistingPDFFile("report");
		test.acs_aactOmr.clickButtonByInputValue("Print your receipt");
		test.asm_PUBSPage.verifyDataFromPdfFileForAACTOMR(membershipType, invoiceTotal,
				customerFullNameList.get(1).trim(), productName);
	}

	@Test(dependsOnMethods = "Step09_Click_On_Print_Your_Receipt_And_Verify_Details_In_Downloaded_PDF")
	public void Step10_Launch_IWEB_And_Find_Member() {
		test.launchApplication(app_url_IWEB);
		test.homePage.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
		test.homePageIWEB.clickOnSideBarTab("Members");
		test.homePageIWEB.clickOnTab("Find Members");
		test.homePageIWEB.verifyUserIsOnHomePage("Find - Membership");
		test.individualsPage.selectFieldValueToFindMember("Chapter:",
				"American Association of Chemistry Teachers (AACT)");
		test.individualsPage.fillMemberDetailsAndSearch("Customer ID", customerFullNameList.get(1).trim());

	}

	@Test(dependsOnMethods = "Step10_Launch_IWEB_And_Find_Member")
	public void Step11_Verify_Term_Start_Date_And_End_Date_Is_Not_Null() {
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("invoices");
		test.memberShipPage.verifyTermStartDateAndEndDatesAreNotEmpty();
	}

	@Test(dependsOnMethods = "Step11_Verify_Term_Start_Date_And_End_Date_Is_Not_Null")
	public void Step12_Verify_Auto_Pay_Membership_Flag_Is_Checked_And_Verify_AACT_Membership_Details() {
		test.memberShipPage.verifyAutoPayStatusAfterAutoRenewal("chkmk");
		test.memberShipPage.clickOnCustomerName();
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("chapter memberships");
		test.memberShipPage.verifyMemberTypeInIndividualMemberships("American Association");

	}

	@Test(dependsOnMethods = "Step12_Verify_Auto_Pay_Membership_Flag_Is_Checked_And_Verify_AACT_Membership_Details")
	public void Step13_Verify_AACT_Member_Details_Under_Invoices_Open_Batch() {
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Invoices");
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("invoices (open batch)");
		test.memberShipPage.verifyMembershipTypeInIndividualMemberships("AACTOMR");
	}

	@Test(dependsOnMethods = "Step13_Verify_AACT_Member_Details_Under_Invoices_Open_Batch")
	public void Step14_Click_On_Go_To_Record_And_Verify_Details_On_Invoice_Profile_Page() {
		test.memberShipPage.clickOnGoToRecordButton("AACTOMR", "3");
		test.invoicePage.verifyDetailsOfInvoiceProfilePageforAACTOMR(invoiceTotal, "Yes", "No");
	}

	@Test(dependsOnMethods = "Step14_Click_On_Go_To_Record_And_Verify_Details_On_Invoice_Profile_Page")
	public void Step15_Verify_Payment_Details_Under_Payments() {
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Payments");
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("payments");
		test.memberShipPage.verifyDetailsForPaymentsChildForm("Payment",
				ASCSocietyGenericPage.map().get("Payment_Method").trim(), membershipType, invoiceTotal, productName);
	}

}
