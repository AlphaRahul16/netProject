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
import com.qait.automation.utils.YamlReader;

public class ACS_Address_Change_On_Proforma_Test extends BaseTest {
	String app_url_iweb,oldLocalSection;
	String newChpName="Chemical Society of",invoiceName="Chem Society Of Washington";
	private String caseID;
	Map<String,String> individualDates=new HashMap<>();
	List<String> addressType = new ArrayList<String>();


	@BeforeClass
	public void open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_iweb = getYamlValue("app_url_IWEB");
	}

	@BeforeMethod
	public void handleTestMethodName(Method method) {
		test.printMethodName(method.getName());
	}
	
//	public ACS_Address_Change_On_Proforma_Test() {
//		com.qait.tests.DataProvider_FactoryClass.sheetName = "";
//	}

//	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
//	public ACS_Address_Change_On_Proforma_Test(String caseID) {
//		this.caseID = caseID;
//	}
	
	@Test
	public void Step01_Launch_Iweb_Application() {
//		test.homePageIWEB.addValuesInMap("", caseID); 
		test.launchApplication(app_url_iweb);
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}
	
	@Test
	public void Step02_Navigate_To_CRM_Module_And_Execute_Query(){
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.clickOnTab("Query Individual");
		test.memberShipPage 
				.selectAndRunQuery("Selenium - Find Random Active Member with Proforma USA expired");  //"Selenium - Find Random Active Member with Proforma USA not expired "
		test.memberShipPage.verifyOueryAskAtRunTimePage();
	    test.memberShipPage.clickOnGoButtonAfterPackageSelection();
	    test.memberShipPage.getCustomerID();  //------ alert handling
	}
	
	@Test
	public void Step03_Verify_Term_Dates_For_National_And_Chapter_Membership(){
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("individual memberships");
		individualDates=test.memberShipPage.getTermDatesOfIndividualMembership("individual memberships");
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("chapter memberships");
		oldLocalSection=test.memberShipPage.verifyTermDatesOfChapterMembership("chapter memberships", individualDates);
		test.memberShipPage.clickOnGoToRecordLink("3");
	}
	
	@Test
	public void Step04_Verfiy_Invoice_With_Null_Term_Dates_And_Click_On_Customer_Name(){
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("invoices");
		test.memberShipPage.getInvoiceId("3");	//caseId
		test.memberShipPage.verifyMemberPaymentStatus("3");//caseId
		test.memberShipPage.clickOnCustomerName();
	}
	
	@Test
	public void Step05_Add_new_Local_Section(){
		test.individualsPage.navigateToContactInfoMenuOnHoveringMore();
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("addresses");
		addressType = test.individualsPage.verifyAddressType("addresses");
		test.individualsPage.clickOnPlusSign("addresses", 2);
		test.individualsPage.addNewAddress(
				YamlReader.getYamlValue("AddressChange.organization"),
				YamlReader.getYamlValue("AddressChange.department"),
				YamlReader.getYamlValue("AddressChange.address"),
				YamlReader.getYamlValue("AddressChange.city"),
				YamlReader.getYamlValue("AddressChange.postalCode"),
				YamlReader.getYamlValue("AddressChange.state"), addressType);
		test.individualsPage.verifyAdditionOfNewAddress(addressType.get(0),
				"addresses");
	}
	
	@Test
	public void Step06_Verify_Individual_Membership_Details_Are_Unchnaged(){
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Membership");
        test.memberShipPage.verifyTermDatesAreUnchanged(individualDates, "individual memberships");
		test.memberShipPage.clickOnGoToRecordLink("3");
		test.memberShipPage.verifyMemberPaymentStatus("3");//caseId
		test.memberShipPage.verifyMemberDetailsOnMemberShipProfile("effective date",individualDates.get("EffectiveDate").toString());
		test.memberShipPage.verifyMemberDetailsOnMemberShipProfile("expire date",individualDates.get("ExpireDate").toString());
	}
	
	@Test
	public void Step07_Verify_Addition_Of_New_Local_Section(){
		String batchName="ACS: Selenium_Batch";
		test.memberShipPage.verifyAdditionOfNewLSAndDetailsOfOldLS(oldLocalSection, newChpName, individualDates);
		test.memberShipPage.clickOnGoToRecordButton(newChpName, "1");
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("invoices");
		test.memberShipPage.verifyAdditionOfNewInvoiceOnMembershipProfilePage(invoiceName);
		test.memberShipPage.clickOnGoToRecordButton(invoiceName, "1");
		test.memberShipPage.verifyMemberDetailsOnMemberShipProfile("proforma","Yes");
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("line items");
		test.memberShipPage.verifyDetailsForNewLS(invoiceName, "", "amount due");
		test.memberShipPage.verifyDetailsForNewLS(invoiceName, "N", "paid");
        test.invoicePage.clickOnAddPaymentIcon();
//        test.memberShipPage.enterPaymentDetails();
		test.invoicePage.enterPaymentDetails(batchName,
				test.memberShipPage.map().get("paymentType"), test.memberShipPage.map().get("paymentmethod"),
				test.memberShipPage.map().get("Card Number"), getYamlValue("ACS_Fundraising_Module.CreditCardExpiration"),
				test.memberShipPage.map().get("CVV"), getYamlValue("ACS_Fundraising_Module.checkNumber"));
	}
}
