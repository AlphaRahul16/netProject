package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.List;
import java.util.Map;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;
import com.qait.keywords.YamlInformationProvider;

public class Subscription_Fulfillment_Test {

	TestSessionInitiator test;
	String app_url_IWEB, subscriptionName;
	YamlInformationProvider getSubscriptionInfo;
	Map<String, Object> mapSubscriptionFulfillment;
	static String taskStartTime, commitStartTime;
	List<String> memberDetails;

	@Test
	public void Step00_Launch_Application_Under_Test() {
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));	
		test.homePageIWEB
				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test
	public void Step01_Search_Member_In_Individual_Test() {
		 test.homePageIWEB.clickOnSideBarTab("Individuals");
		 test.memberShipPage.selectAndRunQueryMembership("Query Individual",getSubscriptionInfo.getSubscriptionInfo("queryName"));
		 memberDetails = test.memberShipPage.getMemberDetails();
		//test.homePageIWEB.clickFindForIndividualsSearch();
		//test.individualsPage.checkMemberDetailsAndSearch("Member Flag");
//		memberDetails = test.memberShipPage.selectMemberAndGetDetails();
	}

	@Test
	public void Step02_Add_Subscription_Through_Order_Entry_Test() {
		test.memberShipPage.navigateToSubscriptionThroughOrderEntry();
		subscriptionName = test.memberShipPage
				.addSubscriptionInOrderEntry(getSubscriptionInfo
						.getSubscriptionInfo("productCode"));
		test.memberShipPage.selectBatchAndPaymentDetails_subscription(
				getSubscriptionInfo.getSubsFul_cenOrdEntry("batch"),
				getSubscriptionInfo.getSubsFul_cenOrdEntry("paymentType"),
				getSubscriptionInfo.getSubsFul_cenOrdEntry("paymentMethod"),
				getSubscriptionInfo.getSubsFul_cenOrdEntry("cardNumber"),
				getSubscriptionInfo.getSubsFul_cenOrdEntry("expireDate"),
				getSubscriptionInfo.getSubsFul_cenOrdEntry("cvvNumber"));
		test.homePageIWEB.GoToSubscriptionModule();
	}

	@Test
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

	@Test
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

	@Test
	public void Step05_Enter_Required_Data_In_Commit_Preview_Test() {
		commitStartTime = test.subscriptionPage
				.navigateToCommitPreviewAndEnterRequiredData(getSubscriptionInfo
						.getSubsFul_Committed("startLapTimeInMinutes"));
	}

	@Test
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

	@Test
	public void Step07_Navigate_To_Subscriber_Profile_Page_And_Verify_Details_Test() {
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.GoToCRMModule();
		test.homePageIWEB.clickFindForIndividualsSearch();
		test.individualsPage.fillMemberDetailsAndSearch("Record Number",
				memberDetails.get(1));//"30182478"
		test.individualsPage.navigateToSubscriptionMenuOnHoveringMore();
		test.subscriptionPage.verifySubscriptionAdded(subscriptionName);
		test.individualsPage.clickOnArrowButtonForProductName(subscriptionName);
		test.individualsPage.navigateToIssuesMenuOnHoveringMore();
		test.subscriptionPage.verifyDetailsOnSubscriberProfile("1",
				memberDetails.get(1), subscriptionName);
	}

	/**
	 * Following methods are to setup and clean up the tests
	 */
	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		mapSubscriptionFulfillment = YamlReader
				.getYamlValues("SubscriptionFulfillment");
		getSubscriptionInfo = new YamlInformationProvider(
				mapSubscriptionFulfillment);
		app_url_IWEB = getYamlValue("app_url_IWEB");
		test.launchApplication(app_url_IWEB);
		
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
	}

	@AfterClass(alwaysRun = true)
	public void Close_Test_Session() {
	 test.closeBrowserSession();
	}
	
}
