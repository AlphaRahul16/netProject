package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;

public class ACS_Address_Change_On_Proforma_Test extends BaseTest {
	String app_url_iweb, oldLocalSection;
	String newChpName = "Chemical Society of", invoiceName = "Chem Society Of Washington",
			batchName = "ACS: Selenium_Batch";
	private String caseID;
	Map<String, String> individualDates = new HashMap<>();
	List<String> addressType = new ArrayList<String>();

	public ACS_Address_Change_On_Proforma_Test() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "AddressChangeOnProforma";
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_Address_Change_On_Proforma_Test(String caseID) {
		this.caseID = caseID;
		ASCSocietyGenericPage.addValuesInMap("AddressChangeOnProforma", caseID);
		System.setProperty("scenario", ASCSocietyGenericPage.map().get("ScenarioNo"));
	}

	@BeforeClass(alwaysRun = true)
	public void Start_Test_Session() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_iweb = getYamlValue("app_url_IWEB");
		System.out.println("Step: Case Id is "+this.caseID);
	}

	@BeforeMethod
	public void handleTestMethodName(Method method) {
		test.printMethodName(method.getName());
	}

	@Test(groups = { "3", "4" })
	public void Step01_Launch_Iweb_Application() {
		test.launchApplication(app_url_iweb);
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test(groups = { "3", "4" })
	public void Step02_Navigate_To_CRM_Module_And_Execute_Query() {
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.clickOnTab("Query Individual");
		test.memberShipPage.selectAndRunQuery(test.memberShipPage.map().get("Query")); //"Selenium – Member with Proforma USA expired"
		test.memberShipPage.verifyOueryAskAtRunTimePage();
		test.memberShipPage.clickOnGoButtonAfterPackageSelection();
		test.memberShipPage.getCustomerID(); // ------ alert handling
	}

	@Test(groups = { "3", "4" })
	public void Step03_Verify_Term_Dates_For_National_And_Chapter_Membership() {
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("individual memberships");
		individualDates = test.memberShipPage.getTermDatesOfIndividualMembership("individual memberships");
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("chapter memberships");
		oldLocalSection = test.memberShipPage.verifyTermDatesOfChapterMembership("chapter memberships",
				individualDates);
		test.memberShipPage.clickOnGoToRecordLink("3");
	}

	@Test(groups = { "3", "4" })
	public void Step04_Verfiy_Invoice_With_Null_Term_Dates_And_Click_On_Customer_Name() {
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("invoices");
		test.memberShipPage.getInvoiceId(test.memberShipPage.map().get("ScenarioNo")); 
		test.memberShipPage.verifyMemberPaymentStatus(test.memberShipPage.map().get("ScenarioNo"));
		test.memberShipPage.clickOnCustomerName();
	}

	@Test(groups = { "3", "4" })
	public void Step05_Add_new_Local_Section() {
		List<String> addressTypes = new ArrayList<>();
		addressTypes.add("work 3");
		addressTypes.add("home 2");
		test.individualsPage.navigateToContactInfoMenuOnHoveringMore();
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("addresses");
		addressType = test.individualsPage.verifyAddressType("addresses",addressTypes);
		test.individualsPage.clickOnPlusSign("addresses", 2);
		test.individualsPage.addNewAddress(YamlReader.getYamlValue("AddressChange.organization"),
				YamlReader.getYamlValue("AddressChange.department"), YamlReader.getYamlValue("AddressChange.address"),
				YamlReader.getYamlValue("AddressChange.city"), YamlReader.getYamlValue("AddressChange.postalCode"),
				YamlReader.getYamlValue("AddressChange.state"), addressType);
		test.individualsPage.verifyAdditionOfNewAddress(addressType.get(0), "addresses");
	}

	@Test(groups = { "3", "4" })
	public void Step06_Verify_Individual_Membership_Details_Are_Unchnaged() {
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Membership");
		test.memberShipPage.verifyTermDatesAreUnchanged(individualDates, "individual memberships");
		test.memberShipPage.clickOnGoToRecordLink("3");
		test.memberShipPage.verifyMemberPaymentStatus(test.memberShipPage.map().get("ScenarioNo"));
		test.memberShipPage.verifyMemberDetailsOnMemberShipProfile("effective date",
				individualDates.get("EffectiveDate").toString());
		test.memberShipPage.verifyMemberDetailsOnMemberShipProfile("expire date",
				individualDates.get("ExpireDate").toString());
		test.memberShipPage.clickOnGoToRecordButton(test.memberShipPage.getInvoiceId(1, 12), "9");
		test.memberShipPage.verifyMemberDetailsOnMemberShipProfile("proforma", "Yes");
		test.memberShipPage.navigateToBackPage();
	}

	@Test(groups = {"4"})
	public void Step07_Verify_Details_Of_New_Local_Section_For_Scenario4() { // scenario4
		test.memberShipPage.clickOnCustomerName();
		test.memberShipPage.verifyAdditionOfNewLSAndDetailsOfOldLS(oldLocalSection, newChpName, individualDates,
				test.memberShipPage.map().get("ScenarioNo"));
		test.memberShipPage.clickOnGoToRecordButton(newChpName, "1");
		test.memberShipPage.verifyMemberDetailsOnMemberShipProfile("effective date",
				DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/yyyy", "EST5EDT"));
		test.memberShipPage.verifyMemberDetailsOnMemberShipProfile("expire date",
				individualDates.get("ExpireDate").toString());
		test.memberShipPage.verifyPaymentStatus("Free");// caseId
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("invoices");
		test.memberShipPage.verifyAdditionOfNewInvoiceOnMembershipProfilePage(invoiceName);
		test.memberShipPage.clickOnGoToRecordButton(invoiceName, "1");
		test.memberShipPage.verifyMemberDetailsOnMemberShipProfile("proforma", "Yes");
	}

	@Test(groups = { "3"})
	public void Step07_Verify_Addition_Of_New_Local_Section() {// scenario3
		test.memberShipPage.navigateToBackPage();
		test.memberShipPage.verifyAdditionOfNewLSAndDetailsOfOldLS(oldLocalSection, newChpName, individualDates,
				test.memberShipPage.map().get("ScenarioNo"));
		test.memberShipPage.clickOnGoToRecordButton(newChpName, "1");
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("invoices");
		test.memberShipPage.verifyAdditionOfNewInvoiceOnMembershipProfilePage(invoiceName);
		test.memberShipPage.clickOnGoToRecordButton(invoiceName, "1");
		test.memberShipPage.verifyMemberDetailsOnMemberShipProfile("proforma", "Yes");
	}

	@Test(groups = { "3", "4" })
	public void Step08_Add_Payment_On_Accounting_Profile_Page_For_scenario3() {// both
		test.invoicePage.clickOnAddPaymentIcon();
		test.invoicePage.enterPaymentDetails(batchName, test.memberShipPage.map().get("PaymentMethod"),
				test.memberShipPage.map().get("CardNumber"),
				getYamlValue("ACS_Fundraising_Module.CreditCardExpiration"), test.memberShipPage.map().get("CVV"),
				test.memberShipPage.map().get("ChequeNumber"));
	}

	@Test(groups = { "4" })
	public void Step09_Verify_Term_Dates_For_Scenario4() {// scenario4
		test.memberShipPage.clickOnCustomerName();
		individualDates = test.memberShipPage.getTermDatesOfIndividualMembership("individual memberships");
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("chapter memverships");
		test.memberShipPage.verifyTerminateDateIsCurrentDate(oldLocalSection,
				DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("MM/dd/yyyy", "EST5EDT"), 7,
				"Terminate date");
		test.memberShipPage.verifyTerminateDateIsCurrentDate(newChpName,
				DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("MM/dd/yyyy", "EST5EDT"), 3, "Join date");
		test.memberShipPage.clickOnGoToRecordButton(newChpName, "1");
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("invoices");
		test.memberShipPage.verifyAdditionOfNewInvoiceOnMembershipProfilePage(invoiceName);
		test.memberShipPage.verifyTerminateDateIsCurrentDate(invoiceName,
				DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/yyyy", "EST5EDT"), 10,
				"Term Start Date");
		test.memberShipPage.verifyTerminateDateIsCurrentDate(invoiceName, individualDates.get("ExpireDate").toString(),
				11, "Term End Date");
	}

	@Test(groups = { "3"})
	public void Step09_Verify_New_Dates_flip_To_Synch_With_National_Dates() {// scenario3
		test.memberShipPage.clickOnCustomerName();
		individualDates = test.memberShipPage.getTermDatesOfIndividualMembership("individual memberships");
		test.memberShipPage.verifyLSDatesFlipToSynchWithNationalDates(newChpName, individualDates);
	}
}
