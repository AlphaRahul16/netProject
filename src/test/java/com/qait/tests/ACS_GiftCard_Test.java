
package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;
import org.testng.internal.TestNGMethod;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.XlsReader;
import com.qait.automation.utils.YamlReader;

public class ACS_GiftCard_Test extends BaseTest{
	private String caseID;
	Map<String,String> giftCardMap = new HashMap<String, String>();
	String app_url_IWEB,app_url_OMA,customerId,redeemedCustomerID;
	private String cardpricevalue, GiftCardNumber;
	private String totalpricebeforeGC,totalpriceAACT,currency = "$",batchprefix="ACS: ";
	String userEmail,memberNumber,productSubTotal,Total,invoiceNumber;
	double creditAmountRemaining;
	String[] userDetail,memberDetail;
	List<String> lineitemsname;


	public ACS_GiftCard_Test() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "GiftCard_Scenariosheet";
		
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_GiftCard_Test(String caseID) {
		this.caseID = caseID;
	    System.out.println(this.caseID);
	    System.setProperty("caseID", this.caseID);
	}
		
		@BeforeClass(alwaysRun = true)
		public void Start_Test_Session() {
			test = new TestSessionInitiator(this.getClass().getSimpleName());
			app_url_IWEB = getYamlValue("app_url_IWEB");
			app_url_OMA=getYamlValue("app_url_OMA");
			System.out.println("before class");
			giftCardMap = test.homePage.addValuesInMap("GiftCard_Scenariosheet", caseID);
		}

		@BeforeMethod(alwaysRun = true)
		public void skipMethodsAccordingToTheScenarioExecuted(Method method) {
			
//			if (!skipTest.containsKey(method.getName())) {
//				System.out.println("not"+method.getName());
//				skipTest.put(method.getName(), false);
//			}
//			if (skipTest.get(method.getName())) {
//				System.out.println("skip yes "+method.getName());
//				throw new SkipException("Tests Skipped for expected work flows!");
//			}
		}
		
		@Test(groups={"1","2","3"})
		public void Step_00_Edit_Existing_BPA() {
			test.launchApplication(app_url_IWEB);
			test.homePage.enterAuthentication(
					YamlReader.getYamlValue("Authentication.userName"),
					YamlReader.getYamlValue("Authentication.password"));
			test.homePageIWEB.clickOnSideBarTab("Individuals");
			for (String name: giftCardMap.keySet()){

	            String key =name.toString();
	            String value = giftCardMap.get(name).toString();  
	            System.out.println(key + " " + value);  


	} 
			
		}
		@Test(groups={"1","2","3"})
		public void Step_01_Run_Query_To_Find_Individual() {
			test.homePageIWEB.clickOnTab("Query Individual");
			test.memberShipPage.selectAndRunQuery("Selenium - Find Active Regular Member");
			customerId=test.memberShipPage.getContactIdOfUser("User");
		}
		
		@Test(groups={"1","2","3"})
		public void Step_02_Click_On_Order_Entry_Button_And_Verify_Centralized_Order_Page() {
			test.memberShipPage.clickOnOrderEntryIcon();
			test.memberShipPage.verifyCentralizedOrderEntryPage("Centralized Order Entry");
			
		}
		
		@Test(groups={"1","2","3"})
		public void Step_03_Click_On_Select_Product_And_Select_Merchandise_Option_and_Verify_Centralized_Order_Entry_Merchandise_Window() {
			test.memberShipPage.clickOnSelectProduct();
			test.memberShipPage.selectMerchandise("merchandise");
			test.memberShipPage.selectMerchandiseProductNameGC("Gift Card");
			test.memberShipPage.clickOnSearchDisplayNameButton();
			cardpricevalue= test.individualsPage.getPriceValueOfGiftCard(giftCardMap.get("Gift_Card_Name"),giftCardMap.get("Sale_Price?"),caseID);
			test.individualsPage.selectOneIndividual(giftCardMap.get("Gift_Card_Name"),"gift card name");
		}
		
		@Test(groups={"2"})
		public void Step_04_Click_Select_Product_And_Merchandise_Option_and_Verify_Centralized_Order_Entry_Merchandise_Window() {
			
			test.memberShipPage.enterSalesPriceForGCMembership(giftCardMap.get("Sale_Price?"));
			test.memberShipPage.selectOverrideReasonForGC(giftCardMap.get("Override_Reason?"));

		}
		
		@Test(groups={"1","2","3"})
		public void Step_05_Verify_that_Selected_Item_Is_Added_Into_Line_Items() {
			test.memberShipPage.clickOnSaveAndFinish();
			test.memberShipPage.verifyProductNameInLineItem(giftCardMap.get("Gift_CardName_In_LineItems?").trim());
			
			lineitemsname=test.memberShipPage.getAddedLineItemsNamesInOrderEntry();
			
			test.memberShipPage.selectAndAddBatchIFNotPresent(batchprefix+giftCardMap.get("GC_Batch_Name?"),giftCardMap.get("Payment_Type"),giftCardMap.get("Payment_Method"));
			test.memberShipPage.fillAllTypeOFPaymentDetails(giftCardMap.get("Payment_Method"), giftCardMap.get("Visa_Card_Number"), giftCardMap.get("Diners_Card_Number"), giftCardMap.get("Reference_Number"),
					giftCardMap.get("Discover_Card_Number"),test.homePageIWEB.map().get("AMEX_Card_Number"), giftCardMap.get("Expiry_Date"), giftCardMap.get("CVV_Number"), giftCardMap.get("Check_Number"));
				test.memberShipPage.navigateToCRMPageByClickingSaveAndFinish();
		}
		
		
		
		@Test(groups={"1","2","3"})
		public void Step_06_Verify_on_CRM() {
			test.individualsPage.navigateToGeneralMenuOnHoveringMore("Other Actg");
			test.individualsPage.expandDetailsMenu("gift cards purchased");
			GiftCardNumber=test.individualsPage.verifyGiftItemPurchasedDetailsBeforeRedeeming(giftCardMap.get("GC_Batch_Name?"),cardpricevalue);
			test.individualsPage.collapseDetailsMenu("gift cards purchased");
		}
		
		/*@Test(groups={"3"})
		public void Step_07_Select_Second_Individual_For__AACT_Gift_Card()
		{
			Step_01_Run_Query_To_Find_Individual();
			Step_02_Click_On_Order_Entry_Button_And_Verify_Centralized_Order_Page();

		}
		
		@Test(groups={"3"})
		public void Step_08_AACT_GC_Methods()
		{
			
			totalpriceAACT=test.memberShipPage.goToAddMembershipAndFillDetails_DivisionAsFellowPrequisite(1);
		}
		
		@Test(groups={"3"})
		public void Step_09_Scenario3_AACT_Redeem()
		{
			test.memberShipPage.selectAndAddBatchIFNotPresentForGiftCard(batchprefix+giftCardMap.get("GC_Batch_Name?"),giftCardMap.get("Payment_Type"),"Gift Card Adjustment");
			test.memberShipPage.findRedeemableGiftCardAndRedeemItFForAACTGc("Redeem Gift Card", GiftCardNumber);
			test.memberShipPage.verifyTotalCreditAndRemaningBalanceInOrderEntry(giftCardMap.get("Iweb Product Name?").trim(),cardpricevalue,totalpriceAACT);
			test.memberShipPage.clickOnSaveAndFinish();
		}
		
		@Test(groups={"1","2"})
		public void Step_10_Enter_Contact_Information() {
			test.launchApplication(app_url_OMA);
			Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
			userDetail = test.ContactInfoPage.enterContactInformation(
					test.homePageIWEB.map().get("Email"), test.homePageIWEB.map()
							.get("FirstName"),
					test.homePageIWEB.map().get("LastName"), test.homePageIWEB
							.map().get("AddressType"),
					test.homePageIWEB.map().get("Address"), test.homePageIWEB.map()
							.get("City"), test.homePageIWEB.map().get("Country"),
							test.homePageIWEB.map().get("State"), test.homePageIWEB.map().get("ZipCode"));
			test.ContactInfoPage.clickContinue();
			userEmail = userDetail[0];
			test.homePage.verifyCurrentTab("Education & Employment");
			Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		}

		@Test(groups={"1","2"})
		public void Step_11_Enter_Education_And_Employment_Info() {
			Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
			Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
			test.EduAndEmpPage.enterEducationAndEmploymentInformation();
			test.ContactInfoPage.clickContinue();
		}

		@Test(groups={"1","2"})
		public void Step_12_Enter_Benefits_Info() {
			Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
			Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
			test.homePage.verifyCurrentTab("Benefits");
			test.BenefitsPage.addACSPublicationAndTechnicalDivision(toString().valueOf(caseID));
			test.BenefitsPage.verifyCENPresent(toString().valueOf(caseID));
			test.ContactInfoPage.clickContinue();
			test.homePage.verifyCurrentTab("Checkout");
		}

		@Test(groups={"1","2"})
		public void Step_13_Verify_Contact_Info_And_Enter_Payment_At_Checkout_Page() {
			Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
			Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
			productSubTotal = test.checkoutPage.verifyProductSubTotal("4",
					"Product Subtotal");
			totalpricebeforeGC=test.checkoutPage.getToalpriceOnCheckoutPage();
			test.checkoutPage.applyACSGiftCard("GiftCardReedeem", GiftCardNumber, "CardButton");
			creditAmountRemaining=test.checkoutPage.verifyTotalAfterApplyingGiftCard(currency,totalpricebeforeGC,cardpricevalue);
		}
		
		
		@Test(groups={"1","2"})
		public void Step_14_Enter_Payment_Details_At_Checkout_Page()
		{
			test.checkoutPage.enterPaymentInfo(
					YamlReader.getYamlValue("creditCardInfo.Type"), userDetail[1]
							+ " " + userDetail[2],
					YamlReader.getYamlValue("creditCardInfo.Number"),
					YamlReader.getYamlValue("creditCardInfo.cvv-number"));
		}
		
		@Test(groups={"1","2"})
		public void Step_15_Click_I_Attest_Checkbox_And_Navigate_To_Confirmation_Page()
		{
			test.checkoutPage.clickAtTestStatement();
			test.ContactInfoPage.clickContinue();
			test.checkoutPage.clickSubmitButtonAtBottom();
			test.homePage.verifyCurrentTab("Confirmation");
			redeemedCustomerID=test.confirmationPage.getMemberDetail("member-number");
		}
		
	@Test(groups={"1","2"})
	public void Step_16_Launch_Application_Under_Test() {
		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB
				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
		
	}
	
	@Test(groups={"1","2"})
	public void Step_17_Search_Indiviudual_By_ContactID()
	{
		test.homePageIWEB.clickFindForIndividualsSearch();
		test.individualsPage.enterFieldValue("Record Number", customerId);
		test.individualsPage.clickGoButton();
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Other Actg");
		test.individualsPage.expandDetailsMenu("gift cards purchased");
		test.individualsPage.verifyGiftItemPurchasedDetailsAfterRedeeming(GiftCardNumber, cardpricevalue);
		test.individualsPage.clickOnRedeemedCustomerIDInGiftCardPurchasedBar(redeemedCustomerID);
	}	
	
	@Test(groups={"1","2","3"})
	public void Step_18_Verify_Redeemed_Customer_Details_on_Iweb()
	{
		test.individualsPage.expandDetailsMenu("gift cards redeemed");
		test.individualsPage.verifyRedeemedGiftCardDetails(GiftCardNumber, cardpricevalue);
		
	}
	
	@Test(groups={"1","2","3"})
	public void Step_19_Verify_Credit_Details_On_Iweb()
	{
		test.individualsPage.expandDetailsMenu("credits");
		test.memberShipPage.verifyCreditDetailsOnIndividualProfile(cardpricevalue+".00",toString().valueOf(creditAmountRemaining));
		test.individualsPage.collapseDetailsMenu("credits");
		
	}

	@Test(groups={"1","2","3"})
	public void Step_20_Verify_Redeemed_Customer_Details_on_Iweb()
	{
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Invoices");
		test.individualsPage.expandDetailsMenu("invoices (open batch)");
		test.individualsPage.clickGotoRecordForRenewal();
		test.invoicePage.verifyInvoiceDetailsAfterRenewal();
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Payments");
		test.individualsPage.expandDetailsMenu("payments");
		test.invoicePage.verifyPaymentDetailsForGiftCard(giftCardMap.get("Iweb Product Name?"),cardpricevalue);
		
		
	}*/
}
	