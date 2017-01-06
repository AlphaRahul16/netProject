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
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;

public class ACS_SourceCodes_Test extends BaseTest {

	String app_url_IWEB,sourceCode,app_url_OMA,app_url_sourceCode,userEmail,memberNumber,productSubTotal,Total,invoiceNumber;
	private String caseID;
	String[] userDetail,quantities,memberDetail;
	boolean isErrorMessage;
	static Map<String, Boolean> errorMap = new HashMap<String, Boolean>();
	Map<String,String> productAmounts=new HashMap<String,String>();
	static String currency = "$";

	
	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IwebMyAccount");
		app_url_OMA=getYamlValue("app_url_OMA");
		app_url_sourceCode=getYamlValue("app_url_SourceCodeOMA");
		test.homePageIWEB.addValuesInMap("SourceCodes", caseID);
	}
	
	public ACS_SourceCodes_Test() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "SourceCodes";
	}
	
	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_SourceCodes_Test(String caseID) {
		this.caseID = caseID;
	}
	
	@Test
	public void Step01_Launch_Iweb_Application() {
		Reporter.log("****** CASE ID : " + caseID + " ******\n", true);
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}
	
	@Test(dependsOnMethods="Step01_Launch_Iweb_Application")
	public void Step02_Navigate_To_Marketing_Module_And_Find_Domestic_Source_Code(){
		Reporter.log("****** CASE ID : " + caseID + " ******\n", true);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Marketing");
		test.homePageIWEB.verifyUserIsOnHomePage("Marketing | Overview | Overview");
		test.acsScarfReporting.clickOnACSSideBarTab("ACS Source Code", 7);
		test.homePageIWEB.clickOnTab("Query Source Code");
		test.homePageIWEB.verifyUserIsOnHomePage("Marketing | ACS Source Code");
		test.memberShipPage.selectAndRunQuery(getYamlValue("ACS_SourceCodes.queryName"));
		test.memberShipPage.verifyOueryAskAtRunTimePage();
//		test.memberShipPage.enterExpiryDatesBeforeAndAfterSourceCodes();
	    test.memberShipPage.clickOnGoButtonAfterPackageSelection();
	    test.memberShipPage.selectDomesticProduct();
	    sourceCode=test.memberShipPage.getSourceCodeValue("source code");
	    test.memberShipPage.verifyIsSourceCodeActive("end date");
	}
	
	@Test(dependsOnMethods="Step02_Navigate_To_Marketing_Module_And_Find_Domestic_Source_Code")
	public void Step03_Launch_OMA_Eweb_And_Enter_Contact_Information_Details(){
		Reporter.log("****** CASE ID : " + caseID + " ******\n", true);
		app_url_sourceCode=app_url_sourceCode+sourceCode;
		test.launchApplication(test.homePageIWEB.map().get("Scenario1_LaunchOMA").equals("Yes")?app_url_OMA:app_url_sourceCode);
		userDetail = test.ContactInfoPage.enterContactInformation(
				test.homePageIWEB.map().get("Email"), test.homePageIWEB.map()
						.get("Fname"),
				test.homePageIWEB.map().get("Lname"), test.homePageIWEB
						.map().get("AddressType"),
				test.homePageIWEB.map().get("Address"), test.homePageIWEB.map()
						.get("City"), test.homePageIWEB.map().get("Country"),
				test.homePageIWEB.map().get("State"), test.homePageIWEB.map()
						.get("ZipCode"));
		test.ContactInfoPage.clickContinue();
		userEmail = userDetail[0];
		test.homePage.verifyCurrentTab("Education & Employment");
	}
	
	@Test(dependsOnMethods="Step03_Launch_OMA_Eweb_And_Enter_Contact_Information_Details")
	public void Step04_Enter_Education_And_Employment_Info() {
		Reporter.log("****** CASE ID : " + caseID + " ******\n", true);
		test.EduAndEmpPage.enterEducationAndEmploymentInformation();
		test.ContactInfoPage.clickContinue();
		isErrorMessage = test.EduAndEmpPage.verifyDisplayedMessage(caseID);
		errorMap.put(caseID, isErrorMessage);
	}
	
	@Test(dependsOnMethods="Step04_Enter_Education_And_Employment_Info")
	public void Step05_Enter_Benefits_Info() {
		Reporter.log("****** CASE ID : " + caseID + " ******\n", true);
		test.homePage.verifyCurrentTab("Benefits");
		test.BenefitsPage.addACSPublicationAndTechnicalDivision(caseID);
		test.BenefitsPage.verifyCENPresent(caseID);
		test.ContactInfoPage.clickContinue();
		test.homePage.verifyCurrentTab("Checkout");
	}
	
	@Test(dependsOnMethods="Step05_Enter_Benefits_Info")
	public void Step06_Verify_Contact_Info_And_Enter_Payment_At_Checkout_Page() {
		Reporter.log("****** CASE ID : " + caseID + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		productAmounts=test.checkoutPage.verifyAdditionOfProductAndGetPrice();
		test.checkoutPage.verifyMemberDetail(caseID);
		test.checkoutPage.verifyMemberEmail(userEmail);
		productSubTotal = test.checkoutPage.verifyProductSubTotal("4",
				"Product Subtotal");
		Total = test.checkoutPage.verifyTotal(currency);
//		test.checkoutPage.verifyTechnicalDivision(caseID);
//		test.checkoutPage.verifyPublication(caseID);
		test.checkoutPage.enterSourceCodeDetails(sourceCode,test.homePageIWEB.map().get("Scenario1_LaunchOMA"));
		sourceCode=test.checkoutPage.verifySourceCodeIsValid(sourceCode);
		test.checkoutPage.enterPaymentInfo(
				YamlReader.getYamlValue("creditCardInfo.Type"), userDetail[1]
						+ " " + userDetail[2],
				YamlReader.getYamlValue("creditCardInfo.Number"),
				YamlReader.getYamlValue("creditCardInfo.cvv-number"));
		test.checkoutPage.clickAtTestStatement();
		test.ContactInfoPage.clickContinue();
		test.checkoutPage.clickSubmitButtonAtBottom();
		test.homePage.verifyCurrentTab("Confirmation");
	}
	
	@Test(dependsOnMethods="Step06_Verify_Contact_Info_And_Enter_Payment_At_Checkout_Page")
	public void Step07_Verify_Details_At_Confirmation_Page() {
		Reporter.log("****** CASE ID : " + caseID + " ******\n", true);
		Reporter.log("****** USER EMAIL ID : " + userEmail + " ******\n", true);
		memberDetail = test.confirmationPage.verifyMemberDetails(
				test.homePageIWEB.map().get("City"), test.homePageIWEB.map()
						.get("ZipCode"),
				test.homePageIWEB.map().get("Country"), test.homePageIWEB.map()
						.get("Address"));
		test.checkoutPage.verifyMemberName(caseID);
		test.checkoutPage.verifyTechnicalDivision(caseID);
		test.checkoutPage.verifyPublication(caseID);
	}
	
	@Test(dependsOnMethods="Step07_Verify_Details_At_Confirmation_Page")
	public void Step08_Launch_Iweb_Application_And_Find_Individual(){
		Reporter.log("****** CASE ID : " + caseID + " ******\n", true);
		Step01_Launch_Iweb_Application();
		invoiceNumber = memberDetail[1];
		test.homePageIWEB.clickFindForIndividualsSearch();
		memberNumber = memberDetail[0];
		test.individualsPage.fillMemberDetailsAndSearch("Record Number",
				memberNumber);
	}
	
	@Test(dependsOnMethods="Step08_Launch_Iweb_Application_And_Find_Individual")
	public void Step09_Verify_Member_Details_And_Source_Code(){
		Reporter.log("****** CASE ID : " + caseID + " ******\n", true);
		test.individualsPage.verifyMemberDetails_OMA(test.homePageIWEB.map()
				.get("Fname"), test.homePageIWEB.map().get("Lname"),
				test.homePageIWEB.map().get("Address"), test.homePageIWEB.map()
						.get("City"), test.homePageIWEB.map().get("ZipCode"),
				test.homePageIWEB.map().get("AddressType"), memberDetail[0],
				userEmail, caseID);
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("my acs applications");
		test.memberShipPage.verifyMemberDetails("my acs applications",2, 11, "Paid", "Payment Status");//11, 1
		test.memberShipPage.verifyMemberDetails("my acs applications",2, 12, sourceCode, "Source Code");//12, 1
	}
	
	@Test(dependsOnMethods="Step09_Verify_Member_Details_And_Source_Code")
	public void Step10_Verify_Invoice_Details(){
		Reporter.log("****** CASE ID : " + caseID + " ******\n", true);
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Invoices");
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("invoices (open batch)");
		test.memberShipPage.verifyMemberDetails("invoices (open batch)",2, 4, invoiceNumber, "Invoice Number");//4, 1
		test.memberShipPage.verifyMemberDetails("invoices (open batch)", 2,7, "N", "Proforma");//7, 1
		test.memberShipPage.verifyMemberDetails("invoices (open batch)",2, 8, "N", "Invoice Open");//8, 1
        test.memberShipPage.clickOnGoToRecordButton(invoiceNumber, "1");	
		test.invoicePage.verifyMemberDetails("transaction date", DateUtil.getCurrentdateInStringWithGivenFormate("M/d/YYYY"));
        test.invoicePage.verifyInvoiceProfile("proforma", "No");
		test.invoicePage.verifyInvoiceProfile("invoice total", Total);
		test.invoicePage.verifyInvoiceProfile("balance", "$0.00");
		test.invoicePage.verifyInvoiceProfile("paid in full", "Yes");
		test.invoicePage.verifyInvoiceProfile("customer id",memberNumber);
	}
	
	@Test(dependsOnMethods="Step10_Verify_Invoice_Details")
	public void Step11_Verify_Payment_Details(){
		Reporter.log("****** CASE ID : " + caseID + " ******\n", true);
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Payments");
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("payments");
        test.invoicePage.verifyProductsPaymentDateAsCurrentDate();
        test.invoicePage.verifyProductAmountonIweb(productAmounts);
	}
	
	@BeforeMethod
	public void handleTestMethodName(Method method) {
		test.printMethodName(method.getName());
	}
}
