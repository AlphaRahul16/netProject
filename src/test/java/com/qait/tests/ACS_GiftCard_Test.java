package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.XlsReader;
import com.qait.automation.utils.YamlReader;

public class ACS_GiftCard_Test extends BaseTest{
	private int caseID;
	Map<String,String> giftCardMap = new HashMap<String, String>();
	String app_url_IWEB;
	private String cardpricevalue;
	
	public ACS_GiftCard_Test() {
		 com.qait.tests.Data_Provider_Factory_Class_Xml.sheetName = "GiftCard_Scenariosheet";
		}

		@Factory(dataProviderClass = com.qait.tests.Data_Provider_Factory_Class_Xml.class, dataProvider = "data")
		public ACS_GiftCard_Test(int caseID) {
			this.caseID = caseID;
		}
		
		@BeforeClass
		public void Start_Test_Session() {
			test = new TestSessionInitiator(this.getClass().getSimpleName());
			app_url_IWEB = getYamlValue("app_url_IWEB");
			giftCardMap = XlsReader.addValuesInTheMapForExcel("GiftCard_Scenariosheet", caseID);
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
			test.memberShipPage.verifyProductNameInLineItem(giftCardMap.get("Gift_Card_Name"));
			test.memberShipPage.selectAndAddBatchIFNotPresentForGiftCard(giftCardMap.get("GC_Batch_Name?"),giftCardMap.get("Payment_Type"));
			test.memberShipPage.fillAllTypeOFPaymentDetails(giftCardMap.get("Payment_Method"), giftCardMap.get("Visa_Card_Number"), giftCardMap.get("Diners_Card_Number"), giftCardMap.get("Reference_Number"),
					giftCardMap.get("Discover_Card_Number"), giftCardMap.get("Expiry_Date"), giftCardMap.get("CVV_Number"), giftCardMap.get("Check_Number"));
				
		}
		
		@Test
		public void Step_05_Verify_on_CRM() {
			test.individualsPage.navigateToGeneralMenuOnHoveringMore("Other Actg");
			test.individualsPage.expandDetailsMenu("gift cards purchased");
			
		}
		
		@Test
		public void Step_06_launch_OMA() {
			
		}
}
