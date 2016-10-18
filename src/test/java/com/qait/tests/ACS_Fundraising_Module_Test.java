package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;

public class ACS_Fundraising_Module_Test extends BaseTest {
	String app_url_IWEB, batchName;
	private String caseID;
	double previousAmount, newAmount;

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IWEB");
	}
	@BeforeMethod
	public void handleTestMethodName(Method method) {
		test.printMethodName(method.getName());
	}

	public ACS_Fundraising_Module_Test() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "Fundraising";
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_Fundraising_Module_Test(String caseID) {
		this.caseID = caseID;
	}

	@Test
	public void Step01_Launch_Iweb_Application() {
		test.homePageIWEB.addValuesInMap("Fundraising", caseID);
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test(dependsOnMethods="Step01_Launch_Iweb_Application")
	public void Step02_Navigate_To_Fundraising_Module_And_Find_A_Constituent() {
		test.homePageIWEB.clickOnModuleTab();
		test.memberShipPage.clickOnTab("Fundraising");
		test.homePageIWEB.clickOnSideBarTab("Constituents");
		test.homePageIWEB.clickOnTab("Find Constituent");
		test.acsFundraising.findConstituent(0, test.memberShipPage.map().get("Constituent Sort Name"),
				"Constituent Sort Name");
		test.memberShipPage.clickOnGoButton();
	}

	@Test(dependsOnMethods="Step02_Navigate_To_Fundraising_Module_And_Find_A_Constituent")
	public void Step03_Select_Random_Constituent_And_Verify_Gift_Information() {
		test.memberShipPage.clickOnRandomPage(10, 1);
		test.acsFundraising.selectAnyRandomConstituent();
		test.acsFundraising.verifyGiftInformation();
	}

	@Test(dependsOnMethods="Step03_Select_Random_Constituent_And_Verify_Gift_Information")
	public void Step04_Adding_A_Gift() {
		test.acsFundraising.clickOnAddGiftButton("add gift");
		test.acsFundraising.switchToFrame("iframe1");
		test.acsFundraising.selectFundCode("fund code", test.memberShipPage.map().get("Fund Code"));
		test.memberShipPage.waitForSpinner();
		test.acsFundraising.verifyGiftDate("giftDate");
		test.acsFundraising.verifyGiftType(YamlReader.getYamlValue("ACS_Fundraising_Module.giftType"));
		test.acsFundraising.enterGiftAmount(test.memberShipPage.map().get("Gift Amount"), "gift amount");
//		test.acsFundraising.clickOnTableForm();
		test.memberShipPage.waitForSpinner();
		test.acsFundraising.verifyDeductibleAmount(test.memberShipPage.map().get("Gift Amount"), "tax deductible amt");
	}

	@Test(dependsOnMethods="Step04_Adding_A_Gift")
	public void Step05_Create_A_New_Batch_And_Enter_Payment_Details() {
		batchName = test.acsVoidInvoice.createBatchForFundraising(1, 6, "QA");
		test.acsVoidInvoice.switchToIframe1();
		test.acsFundraising.selectBatchAndPaymentDetails_Fundraising(batchName,
				test.memberShipPage.map().get("paymentType"), test.memberShipPage.map().get("paymentmethod"),
				test.memberShipPage.map().get("Card Number"), getYamlValue("ACS_Fundraising_Module.CreditCardExpiration"),
				test.memberShipPage.map().get("CVV"), getYamlValue("ACS_Fundraising_Module.checkNumber"));
	}

	@Test(dependsOnMethods="Step05_Create_A_New_Batch_And_Enter_Payment_Details")
	public void Step06_Verify_Gift_Is_Added() {
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("gifts");
		test.acsFundraising.verifyGiftIsAdded("gifts", DateUtil.getCurrentdateInStringWithGivenFormate("M/d/yyyy"), 4,
				"Gift Date", 1);
		test.acsFundraising.verifyGiftIsAdded("gifts", test.memberShipPage.map().get("Gift Amount"), 5, "gift amount",
				1);
		test.acsFundraising.verifyGiftIsAdded("gifts", test.memberShipPage.map().get("Fund Code"), 7, "Fund code", 1);
		test.acsFundraising.verifyGiftIsAdded("gifts", "Fundraising", 8, "Campaign", 1);
		test.acsFundraising.verifyNewGiftInformation(test.memberShipPage.map().get("Gift Amount"));
	}

	@Test(dependsOnMethods="Step06_Verify_Gift_Is_Added")
	public void Step07_Adding_A_Pledge() {
		batchName="ACS: "+batchName;
		test.acsFundraising.clickOnAddGiftButton("add pledge");
		test.acsVoidInvoice.switchToIframe1();
		test.acsFundraising.selectFundCode("fund code", test.memberShipPage.map().get("Fund Code"));
		test.acsFundraising.selectFundCode("appeal code", test.memberShipPage.map().get("Appeal Code"));
		test.acsFundraising.enterGiftAmount(test.memberShipPage.map().get("Pledge Amount"), "amount");
		test.acsFundraising.addBatchAndPledgeType(batchName, YamlReader.getYamlValue("ACS_Fundraising_Module.pledgeType")); //"ACS: SELENIUM_BATCH"
		test.acsFundraising.verifyBatchIsPresent(YamlReader.getYamlValue("ACS_Fundraising_Module.pledgeType"));
		test.acsFundraising.enterNumberOfPaymentInstallments(YamlReader.getYamlValue("ACS_Fundraising_Module.noOfInstallments"));
		test.memberShipPage.selectOrderEntryInfo("orderFrequency",YamlReader.getYamlValue("ACS_Fundraising_Module.orderFrequency") );
		test.acsFundraising.setGiftDate();
		test.acsFundraising.clickOnSaveButton();
	}

	@Test(dependsOnMethods="Step07_Adding_A_Pledge")
	public void Step08_Verify_Addition_Of_Pledge_And_Schedule_Date_Of_Payments() {
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("gifts");
		test.acsFundraising.verifyPledgeIsAdded("gifts", 11, 4, test.memberShipPage.map().get("Fund Code"),
				test.memberShipPage.map().get("Appeal Code"), test.memberShipPage.map().get("Pledge Amount"));
		test.memberShipPage.collapseDetailsMenu("gifts");
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("constituent pledges");
		test.acsFundraising.verifyPledgeInstallments("constituent pledges", 3,
				test.memberShipPage.map().get("Pledge Amount"));
	}

}
