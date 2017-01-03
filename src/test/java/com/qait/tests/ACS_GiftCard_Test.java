package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.XlsReader;
import com.qait.automation.utils.YamlReader;

public class ACS_GiftCard_Test extends BaseTest{
	private String caseID;
	Map<String,String> giftCardMap = new HashMap<String, String>();
	String app_url_IWEB,app_url_OMA,customerId,redeemedCustomerID;
	private String cardpricevalue, GiftCardNumber;
	private String totalpricebeforeGC,currency = "$";
	String sourceCode,app_url_sourceCode,userEmail,memberNumber,productSubTotal,Total,invoiceNumber;
	String[] userDetail,quantities,memberDetail;
	boolean isErrorMessage;
	static Map<String, Boolean> errorMap = new HashMap<String, Boolean>();
	Map<String,String> productAmounts=new HashMap<>();

	public ACS_GiftCard_Test() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "GiftCard_Scenariosheet";
	}

	
	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_GiftCard_Test(String caseID) {
		this.caseID = caseID;
	}
		
		@BeforeClass
		public void Start_Test_Session() {
			test = new TestSessionInitiator(this.getClass().getSimpleName());
			app_url_IWEB = getYamlValue("app_url_IWEB");
			app_url_OMA=getYamlValue("app_url_OMA");
			giftCardMap = test.homePage.addValuesInMap("GiftCard_Scenariosheet", caseID);
		}

		@BeforeMethod
		public void printTestMethodName(Method method) {
			test.printMethodName(method.getName());

		}
		
		@Test
		public void Step_01_Edit_Existing_BPA() {
			
			test.launchApplication(app_url_IWEB);
			test.homePage.enterAuthentication(
					YamlReader.getYamlValue("Authentication.userName"),
					YamlReader.getYamlValue("Authentication.password"));
			test.homePageIWEB.clickOnSideBarTab("Individuals");
			test.homePageIWEB.clickOnTab("Query Individual");
			test.memberShipPage.selectAndRunQuery("Selenium - Find Active Regular Member");
			customerId=test.memberShipPage.getContactIdOfUser("User");
}
		
		@Test
		public void Step_02_Click_On_Order_Entry_Button_And_Verify_Centralized_Order_Page() {
			test.memberShipPage.clickOnOrderEntryIcon();
			test.memberShipPage.verifyCentralizedOrderEntryPage("Centralized Order Entry");
			
		}
		
		@Test
		public void Step_03_Click_Select_Product_And_Merchandise_Option_and_Verify_Centralized_Order_Entry_Merchandise_Window() {
			test.memberShipPage.clickOnSelectProduct();
			test.memberShipPage.selectMerchandise("merchandise");
			test.memberShipPage.selectMerchandiseProductNameGC("Gift Card");
			test.memberShipPage.clickOnSearchDisplayNameButton();
			cardpricevalue= test.individualsPage.getPriceValueOfGiftCard(giftCardMap.get("Gift_Card_Name"));
			test.individualsPage.selectOneIndividual(giftCardMap.get("Gift_Card_Name"),"gift card name");
			test.memberShipPage.clickOnSaveAndFinish();

		}
		
		@Test
		public void Step_04_Verify_that_Selected_Item_Is_Added_Into_Line_Items() {
			test.memberShipPage.verifyProductNameInLineItem("ACS Membership eGift Card");
			test.memberShipPage.selectAndAddBatchIFNotPresentForGiftCard(giftCardMap.get("GC_Batch_Name?"),giftCardMap.get("Payment_Type"),giftCardMap.get("Payment_Method"));
			test.memberShipPage.fillAllTypeOFPaymentDetails(giftCardMap.get("Payment_Method"), giftCardMap.get("Visa_Card_Number"), giftCardMap.get("Diners_Card_Number"), giftCardMap.get("Reference_Number"),
					giftCardMap.get("Discover_Card_Number"), giftCardMap.get("Expiry_Date"), giftCardMap.get("CVV_Number"), giftCardMap.get("Check_Number"));
				
		}
		
		@Test
		public void Step_05_Verify_on_CRM() {
			test.individualsPage.navigateToGeneralMenuOnHoveringMore("Other Actg");
			test.individualsPage.expandDetailsMenu("gift cards purchased");
			GiftCardNumber=test.individualsPage.verifyGiftItemPurchasedDetailsBeforeRedeeming(giftCardMap.get("GC_Batch_Name?"),cardpricevalue);
		}
		
	
		
		@Test
		public void Step_06_Enter_Contact_Information() {
			test.homePage.addValuesInMap("GiftCard_Scenariosheet", "4");
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

		@Test
		public void Step_07_Enter_Education_And_Employment_Info() {
			Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
			Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
			test.EduAndEmpPage.enterEducationAndEmploymentInformation();
			test.ContactInfoPage.clickContinue();
		}

		@Test
		public void Step_08_Enter_Benefits_Info() {
			Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
			Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
			test.homePage.verifyCurrentTab("Benefits");
			test.BenefitsPage.addACSPublicationAndTechnicalDivision(toString().valueOf(caseID));
			test.BenefitsPage.verifyCENPresent(toString().valueOf(caseID));
			test.ContactInfoPage.clickContinue();
			test.homePage.verifyCurrentTab("Checkout");
		}

		@Test
		public void Step_09_Verify_Contact_Info_And_Enter_Payment_At_Checkout_Page() {
			Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
			Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
			productSubTotal = test.checkoutPage.verifyProductSubTotal("4",
					"Product Subtotal");
			totalpricebeforeGC=test.checkoutPage.getToalpriceonCheckoutPage();
			test.checkoutPage.applyACSGiftCard("GiftCardReedeem", GiftCardNumber, "CardButton");
			test.checkoutPage.verifyTotalAfterApplyingGiftCard(currency,totalpricebeforeGC,cardpricevalue);
			test.checkoutPage.enterPaymentInfo(
					YamlReader.getYamlValue("creditCardInfo.Type"), userDetail[1]
							+ " " + userDetail[2],
					YamlReader.getYamlValue("creditCardInfo.Number"),
					YamlReader.getYamlValue("creditCardInfo.cvv-number"));
			test.checkoutPage.clickAtTestStatement();
			test.ContactInfoPage.clickContinue();
			test.checkoutPage.clickSubmitButtonAtBottom();
			test.homePage.verifyCurrentTab("Confirmation");
			redeemedCustomerID=test.confirmationPage.getMemberDetail("member-number");
		}
		
	@Test
	public void Step_10_Launch_Application_Under_Test() {
		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		test.launchApplication(app_url_IWEB);
		test.homePage.enterAuthentication(
				YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB
				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
		
	}
	
	@Test
	public void Step_11_Search_Indiviudual_By_ContactID()
	{
		test.homePageIWEB.clickFindForIndividualsSearch();
		test.individualsPage.enterFieldValue("Record Number", customerId);
		test.individualsPage.clickGoButton();
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Other Actg");
		test.individualsPage.expandDetailsMenu("gift cards purchased");
		test.individualsPage.verifyGiftItemPurchasedDetailsAfterRedeeming(GiftCardNumber, cardpricevalue);
		test.individualsPage.clickOnRedeemedCustomerIDInGiftCardPurchasedBar(redeemedCustomerID);
		
	}
	
	@Test
	public void Step_12_Verify_Redeemed_Customer_Details_on_Iweb()
	{
		test.individualsPage.collapseDetailsMenu("gift cards purchased");
		test.individualsPage.expandDetailsMenu("gift cards redeemed");
		
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Invoices");
		test.individualsPage.clickGotoRecordForRenewal();
		test.invoicePage.verifyInvoiceDetailsAfterRenewal();
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Payments");
		test.invoicePage.verifyPaymentDetailsForGiftCard(giftCardMap.get("GC_Batch_Name?"),cardpricevalue);
		
		
	}
		
		
}
