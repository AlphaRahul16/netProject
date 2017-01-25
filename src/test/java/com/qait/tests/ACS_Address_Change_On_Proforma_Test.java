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
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;

public class ACS_Address_Change_On_Proforma_Test extends BaseTest {
	String app_url_iweb,oldLocalSection;
	String newChpName="Chemical Society of",invoiceName="Chem Society Of Washington",batchName="ACS: Selenium_Batch";
	private String caseID;
	Map<String,String> individualDates=new HashMap<>();
	List<String> addressType = new ArrayList<String>();


//	@BeforeClass(alwaysRun=true)
//	public void open_Browser_Window() {
//		System.out.println("Before class");
//		test = new TestSessionInitiator(this.getClass().getSimpleName());
//		System.out.println("Before class");
//		app_url_iweb = getYamlValue("app_url_IWEB");
//		System.out.println("------case id:::::"+caseID);
//		test.homePageIWEB.addValuesInMap("AddressChangeOnProforma", caseID); 
//		System.setProperty("caseid",caseID);
//		System.out.println("scenario:::::"+System.getProperty("caseID"));
//	}
	
	public ACS_Address_Change_On_Proforma_Test() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "AddressChangeOnProforma";
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_Address_Change_On_Proforma_Test(String caseID) {
		this.caseID = caseID;
	    System.out.println(this.caseID);
	    System.setProperty("caseID", this.caseID);

	}
	
	@BeforeClass(alwaysRun = true)
	public void Start_Test_Session() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_iweb = getYamlValue("app_url_IWEB");
		System.out.println("before class");
		test.homePage.addValuesInMap("AddressChangeOnProforma", caseID);
	}
	
	@BeforeMethod
	public void handleTestMethodName(Method method) {
		test.printMethodName(method.getName());
	}
	
	@Test //(groups={"3","4"})
	public void Step01_Launch_Iweb_Application() {//scenario3
		test.launchApplication(app_url_iweb);
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}
	
	@Test(groups={"4"})
	public void Step02_Navigate_To_CRM_Module_And_Execute_Query(){//scenario3
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.clickOnTab("Query Individual");
		test.memberShipPage 
				.selectAndRunQuery("Selenium – Member with Proforma USA expired");  //test.memberShipPage.map().get("Query") "Selenium - Find Random Active Member with Proforma USA not expired "
		test.memberShipPage.verifyOueryAskAtRunTimePage();
	    test.memberShipPage.clickOnGoButtonAfterPackageSelection();
	    test.memberShipPage.getCustomerID();  //------ alert handling
	}
	
//	@Test
//	public void Step03_Verify_Term_Dates_For_National_And_Chapter_Membership(){//scenario3
//		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("individual memberships");
//		individualDates=test.memberShipPage.getTermDatesOfIndividualMembership("individual memberships");
//		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("chapter memberships");
//		oldLocalSection=test.memberShipPage.verifyTermDatesOfChapterMembership("chapter memberships", individualDates);
//		test.memberShipPage.clickOnGoToRecordLink("3");
//	}
//	
//	@Test
//	public void Step04_Verfiy_Invoice_With_Null_Term_Dates_And_Click_On_Customer_Name(){//scenario3
//		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("invoices");
//		test.memberShipPage.getInvoiceId(test.memberShipPage.map().get("ScenarioNo"));	//caseId
//		test.memberShipPage.verifyMemberPaymentStatus(test.memberShipPage.map().get("ScenarioNo"));//caseId
//		test.memberShipPage.clickOnCustomerName();
//	}
//	
//	@Test
//	public void Step05_Add_new_Local_Section(){//scenario3
//		test.individualsPage.navigateToContactInfoMenuOnHoveringMore();
//		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("addresses");
//		addressType = test.individualsPage.verifyAddressType("addresses");
//		test.individualsPage.clickOnPlusSign("addresses", 2);
//		test.individualsPage.addNewAddress(
//				YamlReader.getYamlValue("AddressChange.organization"),
//				YamlReader.getYamlValue("AddressChange.department"),
//				YamlReader.getYamlValue("AddressChange.address"),
//				YamlReader.getYamlValue("AddressChange.city"),
//				YamlReader.getYamlValue("AddressChange.postalCode"),
//				YamlReader.getYamlValue("AddressChange.state"), addressType);
//		test.individualsPage.verifyAdditionOfNewAddress(addressType.get(0),
//				"addresses");
//	}
//	
//	@Test
//	public void Step06_Verify_Individual_Membership_Details_Are_Unchnaged(){//scenario3
//		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Membership");
//        test.memberShipPage.verifyTermDatesAreUnchanged(individualDates, "individual memberships");
//		test.memberShipPage.clickOnGoToRecordLink("3");
//		test.memberShipPage.verifyMemberPaymentStatus(test.memberShipPage.map().get("ScenarioNo"));//caseId
//		test.memberShipPage.verifyMemberDetailsOnMemberShipProfile("effective date",individualDates.get("EffectiveDate").toString());
//		test.memberShipPage.verifyMemberDetailsOnMemberShipProfile("expire date",individualDates.get("ExpireDate").toString());
//	}
//	
//	@Test
//	public void Step07_Verify_Addition_Of_New_Local_Section(){//scenario3
//		test.memberShipPage.verifyAdditionOfNewLSAndDetailsOfOldLS(oldLocalSection, newChpName, individualDates,test.memberShipPage.map().get("ScenarioNo"));
//		test.memberShipPage.clickOnGoToRecordButton(newChpName, "1");
//		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("invoices");
//		test.memberShipPage.verifyAdditionOfNewInvoiceOnMembershipProfilePage(invoiceName);
//		test.memberShipPage.clickOnGoToRecordButton(invoiceName, "1");
//		test.memberShipPage.verifyMemberDetailsOnMemberShipProfile("proforma","Yes");
////		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("line items");
////		test.memberShipPage.verifyDetailsForNewLS(invoiceName, "0.00", "amount due",6);
////		test.memberShipPage.verifyPaidValueForNewLS(invoiceName, "N", "paid",7);
//	}
//	
////	@Test
//	public void Step08_Verify_Addition_Of_New_Local_Section_And_Invoice_Details(){
//		test.memberShipPage.verifyAdditionOfNewLSAndDetailsOfOldLS(oldLocalSection, newChpName, individualDates,test.memberShipPage.map().get("ScenarioNo"));
//		test.memberShipPage.clickOnGoToRecordButton(newChpName, "1");
//		test.memberShipPage.verifyMemberDetailsOnMemberShipProfile("effective date",DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/yyyy", "EST5EDT"));
//		test.memberShipPage.verifyMemberDetailsOnMemberShipProfile("expire date",individualDates.get("ExpireDate").toString());
//		test.memberShipPage.verifyPaymentStatus("Free");//caseId
//		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("invoices");
//		test.memberShipPage.verifyTermEndDateAndStartDateIsEmpty();
////		test.memberShipPage.clickOnGoToRecordButton(invoiceName, "1");
////		test.memberShipPage.verifyMemberDetailsOnMemberShipProfile("proforma","Yes");
//
//	}
//	
////	@Test
//	public void Step09_Verify_Details_On_Accounting_Profile_Page(){
//		test.memberShipPage.clickOnGoToRecordButton(invoiceName, "1");
//		test.memberShipPage.verifyMemberDetailsOnMemberShipProfile("proforma","Yes");
//		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("line items");
//		test.memberShipPage.verifyDetailsForNewLS(invoiceName, "0.00", "amount due",6);
//		test.memberShipPage.verifyPaidValueForNewLS(invoiceName, "N", "paid",7);
//	}
//	
//	@Test
//	public void Step08_Add_Payment_On_Accounting_Profile_Page_For_scenario3(){//scenario3
//        test.invoicePage.clickOnAddPaymentIcon();
//		test.invoicePage.enterPaymentDetails(batchName, test.memberShipPage.map().get("PaymentMethod"),
//				test.memberShipPage.map().get("CardNumber"), getYamlValue("ACS_Fundraising_Module.CreditCardExpiration"),
//				test.memberShipPage.map().get("CVV"), test.memberShipPage.map().get("ChequeNumber"));
//	}
//	
//	@Test
//	public void Step09_Verify_New_Dates_flip_To_Synch_With_National_Dates(){//scenario3
//		test.memberShipPage.clickOnCustomerName();
//		individualDates=test.memberShipPage.getTermDatesOfIndividualMembership("individual memberships");
//		test.memberShipPage.verifyLSDatesFlipToSynchWithNationalDates(newChpName, individualDates);
//	}
}
