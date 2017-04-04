package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_Create_Member_IWEB_Test extends BaseTest {

	private String caseID;
	public String contactID, batchprefix = "ACS: ";
	private String[] memDetails;
	int numberOfDivisions, numberOfSubscriptions;
	String app_url_IWEB = getYamlValue("app_url_IWEB");

	public ACS_Create_Member_IWEB_Test() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "createMember";
	}

	@BeforeMethod
	public void printCaseIdExecuted(Method method) {
		test.printMethodName(method.getName());
		if (caseID != null) {
			Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		}
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_Create_Member_IWEB_Test(String caseID) {
		this.caseID = caseID;
	}

	@Test
	public void Step01_Launch_IWEB_Application_Under_Test() {
		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		test.homePageIWEB.addValuesInMap("createMember", caseID);
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test(dependsOnMethods = "Step01_Launch_IWEB_Application_Under_Test")
	public void Step02_Add_Individual() {
		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		test.homePageIWEB.clickOnAddIndividual();
		memDetails = test.addMember.enterMemberDetailsInAddIndividual();
	}

	@Test(dependsOnMethods = "Step02_Add_Individual")
	public void Step03_Verify_Individual_Details() {
		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		contactID = test.individualsPage.verifyMemberDetails_InAddIndividual(memDetails);
		test.individualsPage.verifyMemberIsNotCreated();
		test.individualsPage.verifyMemberReceivedNoBenefits();
	}

	@Test(dependsOnMethods = "Step03_Verify_Individual_Details")
	public void Step04_Navigate_To_Order_Entry_And_Sell_Membership() {
		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		test.memberShipPage.goToOrderEntry();
		test.memberShipPage.goToAddMembershipAndFillDetails_membership();
		test.memberShipPage.goToAddMemebrshipAndFillDetails_LocalSection();
	}

	@Test(dependsOnMethods = "Step04_Navigate_To_Order_Entry_And_Sell_Membership")
	public void Step05_Sell_Division() {
		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		numberOfDivisions = test.memberShipPage.getDivisionNumbers();
		test.memberShipPage.goToAddMembershipAndFillDetails_Division(numberOfDivisions); // -------
	}

	@Test(dependsOnMethods = "Step05_Sell_Division")
	public void Step06_Sell_Subscription() {
		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		numberOfSubscriptions = test.memberShipPage.getSubscriptionNumbers();
		test.memberShipPage.navigateToSubscriptionInSelectLinkAndSellSubscription(numberOfSubscriptions);
	}

	@Test(dependsOnMethods = "Step06_Sell_Subscription")
	public void Step07_Verify_NetPrice_Amount_And_Make_Payment() {
		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		test.memberShipPage.verifyNetPriceValue("netbalance");
		// test.memberShipPage.selectBatchAndPaymentDetails_subscription(
		// YamlReader.getYamlValue("Acs_CreateMember_IWEB.batch"),
		// YamlReader.getYamlValue("Acs_CreateMember_IWEB.PaymentType"),
		// YamlReader.getYamlValue("creditCardDetails.PaymentMethod.Select"),
		// YamlReader.getYamlValue("creditCardDetails.paymentMethodVisaMC.Number"),
		// YamlReader.getYamlValue("Acs_CreateMember_IWEB.expireDate"),
		// YamlReader.getYamlValue("creditCardDetails.paymentMethodVisaMC.cvv-number"),
		// YamlReader.getYamlValue("creditCardDetails.paymentMethodBOACheck.CheckNumber"));
		//
		test.memberShipPage.selectAndAddBatchIFNotPresent(
				batchprefix + test.homePageIWEB.map().get("Batch_Name?") + System.currentTimeMillis(),
				test.homePageIWEB.map().get("Payment_Type"), test.homePageIWEB.map().get("Payment_Method"));
		test.memberShipPage.fillAllTypeOFPaymentDetails(test.homePageIWEB.map().get("Payment_Method"),
				test.homePageIWEB.map().get("Visa_Card_Number"), test.homePageIWEB.map().get("Diners_Card_Number"),
				test.homePageIWEB.map().get("Reference_Number"), test.homePageIWEB.map().get("Discover_Card_Number"),
				test.homePageIWEB.map().get("AMEX_Card_Number"), test.homePageIWEB.map().get("Expiry_Date"),
				test.homePageIWEB.map().get("CVV_Number"), test.homePageIWEB.map().get("Check_Number"));
		test.memberShipPage.navigateToCRMPageByClickingSaveAndFinish();
	}

	@Test(dependsOnMethods = "Step07_Verify_NetPrice_Amount_And_Make_Payment")
	public void Step08_Verify_Member_Details_In_Individual_And_Chapter_Memberships() {
		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		test.individualsPage.verifyMemberDetails_MemberProfile(memDetails[1], memDetails[2]);
		test.memberShipPage.verifyMemberDetails_IWEB("individual memberships", 1);
		test.memberShipPage.verifyMemberDetails_IWEB("chapter memberships", numberOfDivisions);
	}

	@Test(dependsOnMethods = "Step08_Verify_Member_Details_In_Individual_And_Chapter_Memberships")
	public void Step09_Verify_Member_Details_In_Subscriptions() {
		Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
		test.individualsPage.navigateToSubscriptionAndVerifySubscriptionDetails(numberOfSubscriptions);
	}

	/**
	 * * Following methods are to setup and clean up the tests
	 */
	@BeforeClass
	public void Start_Test_Session() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
	}

}
