package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;
import com.qait.keywords.YamlInformationProvider;

public class Subscription_Fulfillment_Test extends BaseTest{

	String app_url_IWEB, subscriptionName;
	YamlInformationProvider getSubscriptionInfo;
	Map<String, Object> mapSubscriptionFulfillment;
	static String taskStartTime, commitStartTime;
	List<String> memberDetails;
	private String batchprefix = "ACS: ";

	// String cardNumber, paymentMethod, expireDate, cvvNumber, checkNumber;
	public Subscription_Fulfillment_Test() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "Subscription_Fulfillment";
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public Subscription_Fulfillment_Test(String caseID) {
		this.caseID = caseID;
	}

	@Test
	public void Step00_Launch_Application_Under_Test() {
		System.out.println("payment type*************" + ASCSocietyGenericPage.map().get("Payment_Type"));
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test(dependsOnMethods="Step00_Launch_Application_Under_Test")
	public void Step01_Search_Member_In_Individual_Test() {
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.selectAndRunQueryMembership("Query Individual",
				getSubscriptionInfo.getSubscriptionInfo("queryName"));
		memberDetails = test.memberShipPage.getMemberDetails();
		// test.homePageIWEB.clickFindForIndividualsSearch();
		// test.individualsPage.checkMemberDetailsAndSearch("Member Flag");
		// memberDetails = test.memberShipPage.selectMemberAndGetDetails();
	}

	@Test(dependsOnMethods="Step01_Search_Member_In_Individual_Test")
	public void Step02_Add_Subscription_Through_Order_Entry_Test() {
		test.memberShipPage.navigateToSubscriptionThroughOrderEntry();
		subscriptionName = test.memberShipPage
				.addSubscriptionInOrderEntry(getSubscriptionInfo
						.getSubscriptionInfo("productCode"));
		
		test.memberShipPage.selectAndAddBatchIFNotPresent(
				batchprefix + ASCSocietyGenericPage.map().get("Batch_Name?") + System.currentTimeMillis(),
				ASCSocietyGenericPage.map().get("Payment_Type"), ASCSocietyGenericPage.map().get("Payment_Method"));
		
		test.memberShipPage.fillAllTypeOFPaymentDetails(ASCSocietyGenericPage.map().get("Payment_Method"),
				ASCSocietyGenericPage.map().get("Visa_Card_Number"),
				ASCSocietyGenericPage.map().get("Diners_Card_Number"),
				ASCSocietyGenericPage.map().get("Reference_Number"),
				ASCSocietyGenericPage.map().get("Discover_Card_Number"),
				ASCSocietyGenericPage.map().get("AMEX_Card_Number"), ASCSocietyGenericPage.map().get("Expiry_Date"),
				ASCSocietyGenericPage.map().get("CVV_Number"), ASCSocietyGenericPage.map().get("Check_Number"));
		test.memberShipPage.navigateToCRMPageByClickingSaveAndFinish();
//		test.memberShipPage.selectBatchAndPaymentDetails_subscription(
//				getSubscriptionInfo.getSubsFul_cenOrdEntry("batch"),
//				getSubscriptionInfo.getSubsFul_cenOrdEntry("paymentType"),
//				paymentMethod, cardNumber, expireDate, cvvNumber, checkNumber
//
//		);
		test.homePageIWEB.GoToSubscriptionModule();
	}

	@Test(dependsOnMethods="Step02_Add_Subscription_Through_Order_Entry_Test")
	public void Step03_Edit_Subscription_And_Verify_Preview_Status_For_Scheduled_Test() {
		taskStartTime = test.subscriptionPage.editSubscription(
				subscriptionName, getSubscriptionInfo
						.getSubsFul_PreviewComplete("startLapTimeInMinutes"),
				getSubscriptionInfo.getSubscriptionInfo("fulfillmentType"));

		test.subscriptionPage.clickOnSubLinkSideBar("List Fulfillment Batches");
		test.subscriptionPage
				.verifyPreviewStatusInListForFirst(
						getSubscriptionInfo
								.getSubsFul_Schedule("previewStatus"),
						getSubscriptionInfo
								.getSubscriptionInfo("maxWaitTimeInMinutesForPreviewStatus"));
	}

	@Test(dependsOnMethods="Step03_Edit_Subscription_And_Verify_Preview_Status_For_Scheduled_Test")
	public void Step04_Verify_Subscription_Details_For_Preview_Complete_Test() {
		test.subscriptionPage
				.verifyPreviewStatusInListForFirst(
						getSubscriptionInfo
								.getSubsFul_PreviewComplete("previewStatus"),
						getSubscriptionInfo
								.getSubscriptionInfo("maxWaitTimeInMinutesForPreviewStatus"));
		test.subscriptionPage.navigateToFirstSubscriptionTask();
		test.subscriptionPage
				.verifySubscriptionDetails(
						subscriptionName,
						getSubscriptionInfo
								.getSubsFul_PreviewComplete("scheduledTask"),
						taskStartTime,
						getSubscriptionInfo
								.getSubsFul_PreviewComplete("scheduledTaskCompleted"),
						getSubscriptionInfo
								.getSubsFul_PreviewComplete("previewStatus"),
						getSubscriptionInfo
								.getSubsFul_PreviewComplete("commitScheduledTask"),
						getSubscriptionInfo
								.getSubsFul_PreviewComplete("fulfillmentType"),
						getSubscriptionInfo
								.getSubsFul_PreviewComplete("updateStartIssue"));
	}

	@Test(dependsOnMethods="Step04_Verify_Subscription_Details_For_Preview_Complete_Test")
	public void Step05_Enter_Required_Data_In_Commit_Preview_Test() {
		commitStartTime = test.subscriptionPage
				.navigateToCommitPreviewAndEnterRequiredData(getSubscriptionInfo
						.getSubsFul_Committed("startLapTimeInMinutes"));
	}

	@Test(dependsOnMethods="Step05_Enter_Required_Data_In_Commit_Preview_Test")
	public void Step06_Verify_Subscription_Details_For_Commited_Test() {
		test.subscriptionPage
				.verifySubscriptionDetails(
						subscriptionName,
						getSubscriptionInfo
								.getSubsFul_Committed("scheduledTask"),
						taskStartTime,
						getSubscriptionInfo
								.getSubsFul_Committed("scheduledTaskCompleted"),
						getSubscriptionInfo
								.getSubsFul_Committed("previewStatusOnStartCommit"),
						getSubscriptionInfo
								.getSubsFul_Committed("commitScheduledTask"),
						getSubscriptionInfo
								.getSubsFul_Committed("fulfillmentType"),
						getSubscriptionInfo
								.getSubsFul_Committed("updateStartIssue"));

		test.subscriptionPage.clickOnSubLinkSideBar("List Fulfillment Batches");

		test.subscriptionPage
				.verifyPreviewStatusInListForFirst(
						getSubscriptionInfo
								.getSubsFul_Committed("previewStatus"),
						getSubscriptionInfo
								.getSubscriptionInfo("maxWaitTimeInMinutesForPreviewStatus"));

		test.subscriptionPage.navigateToFirstSubscriptionTask();
		test.subscriptionPage
				.verifySubscriptionDetails(
						subscriptionName,
						getSubscriptionInfo
								.getSubsFul_Committed("scheduledTask"),
						taskStartTime,
						getSubscriptionInfo
								.getSubsFul_Committed("scheduledTaskCompleted"),
						getSubscriptionInfo
								.getSubsFul_Committed("previewStatus"),
						getSubscriptionInfo
								.getSubsFul_Committed("commitScheduledTask"),
						getSubscriptionInfo
								.getSubsFul_Committed("fulfillmentType"),
						getSubscriptionInfo
								.getSubsFul_Committed("updateStartIssue"));
		test.subscriptionPage
				.verifyIssueInSubscriptionFulfillmentBatchSummary(subscriptionName);

	}

	@Test(dependsOnMethods="Step06_Verify_Subscription_Details_For_Commited_Test")
	public void Step07_Navigate_To_Subscriber_Profile_Page_And_Verify_Details_Test() {
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.GoToCRMModule();
		test.homePageIWEB.clickFindForIndividualsSearch();
		test.individualsPage.fillMemberDetailsAndSearch("Record Number",
				memberDetails.get(1));// "30182478"
		test.individualsPage.navigateToSubscriptionMenuOnHoveringMore();
		test.subscriptionPage.verifySubscriptionAdded(subscriptionName);
		test.individualsPage.clickOnArrowButtonForProductName(subscriptionName);
		test.individualsPage.navigateToIssuesMenuOnHoveringMore();
		test.subscriptionPage.verifyDetailsOnSubscriberProfile("1",
				memberDetails.get(1), subscriptionName);
	}

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		mapSubscriptionFulfillment = YamlReader
				.getYamlValues("SubscriptionFulfillment");
		getSubscriptionInfo = new YamlInformationProvider(
				mapSubscriptionFulfillment);
		ASCSocietyGenericPage.addValuesInMap("Subscription_Fulfillment", caseID);
		app_url_IWEB = getYamlValue("app_url_IWEB");
		test.launchApplication(app_url_IWEB);
//		cardNumber = YamlReader
//				.getYamlValue("SubscriptionFulfillment.centralizedOrderEntry.paymentMethodVisaMC.cardNumber");
//		expireDate = YamlReader
//				.getYamlValue("SubscriptionFulfillment.centralizedOrderEntry.paymentMethodVisaMC.expireDate");
//		cvvNumber = YamlReader
//				.getYamlValue("SubscriptionFulfillment.centralizedOrderEntry.paymentMethodVisaMC.cvvNumber");
//		paymentMethod = YamlReader
//				.getYamlValue("SubscriptionFulfillment.centralizedOrderEntry.PaymentMethod.Select");
//		checkNumber = YamlReader
//				.getYamlValue("SubscriptionFulfillment.centralizedOrderEntry.paymentMethodBOACheck.checkNumber");

	}
	@BeforeMethod
	public void handleTestMethodName(Method method) {
		test.printMethodName(method.getName());
	}

}
