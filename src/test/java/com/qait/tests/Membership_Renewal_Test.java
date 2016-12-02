package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;
import com.qait.keywords.YamlInformationProvider;

public class Membership_Renewal_Test extends BaseTest {

	String app_url_IWEB, name, time, runTaskDateTime, customerId;
	YamlInformationProvider getMemRenewalInfo;
	Map<String, Object> mapMemberShipRenewal;
	List<String> memberDetail1, memberDetail2;
	Object[] obj = new Object[2];
	static int invocationCount = 0;

	@Test
	public void Step00_Launch_Application_Under_Test() {
		test.homePageIWEB.enterAuthentication(
				YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB
				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test
	public void Step01_Add_ACS_Renewal_Cycle() {
		String date[] = DateUtil.getNextDate("month", 6);
		test.homePageIWEB.GoToMemberShipSetupProfile();
		test.homePageIWEB.clickOnSideBarTab("Overview");
		test.membershipRenewalPage.clickOnAcsRenewalCycleTab();
		test.memberShipPage.clickOnSideBar("Add");
		name = test.membershipRenewalPage.addACSRenewalCycle(date[1],
				getMemRenewalInfo.getMemRenewalAddACSRenewalCycle("expYear"),
				getMemRenewalInfo
						.getMemRenewalAddACSRenewalCycle("renewalYear"),
				getMemRenewalInfo.getMemRenewalAddACSRenewalCycle("type"),
				getMemRenewalInfo
						.getMemRenewalAddACSRenewalCycle("renewalLength"));
	}

	@Test
	public void Step02_Find_Two_Member_And_Get_Detail() {
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnMemberShipTab();
		test.homePageIWEB.clickOnSideBarTab("Members");
		test.memberShipPage.clickOnSideBar("Query Membership");
		test.memberShipPage.selectAndRunQuery("Selenium - Renewal Query");
		test.memberShipPage.selectMemberForRenewal("Regular");
		test.individualsPage.selectOneIndividual("Active", "member status");
		customerId = test.memberShipPage.getContactIdOfUser("Member");
		memberDetail1 = test.memberShipPage.getMemberDetailsOnMembershipPage();

		test.memberShipPage.clickOnSideBar("Query Membership");
		test.memberShipPage.selectAndRunQuery("Selenium - Renewal Query");
		test.memberShipPage.selectMemberForRenewal("Regular");
		test.individualsPage.selectOneIndividual("Active", "member status");
		memberDetail2 = test.memberShipPage.getMemberDetailsOnMembershipPage();

		test.memberShipPage.selectAndRunQueryMembership("Query Membership",
				getMemRenewalInfo.getMemRenewalInfo("queryName"));
		test.memberShipPage.enterCustomerIdsInRunQuery(memberDetail1.get(3),
				memberDetail2.get(3));
		obj[0] = memberDetail1;
		obj[1] = memberDetail2;

	}

	@Test
	public void Step03_Navigate_To_Add_Membership_Renewal_And_Fill_Details() {
		test.memberShipPage.clickOnSideBar("ACS Renew Memberships");
		runTaskDateTime = test.membershipRenewalPage
				.fillDetailsAtAddMembershipRenewalPage(getMemRenewalInfo
						.getRenewalInfoAtAdd("association"), getMemRenewalInfo
						.getRenewalInfoAtAdd("renewalYear"), name,
						getMemRenewalInfo.getRenewalInfoAtAdd("batch"),
						getMemRenewalInfo
								.getMemRenewalInfo("timeSlabInMinutes"));
	}

	@Test
	public void Step04_verify_Renewal_For_Scheduled_Processing_Success() {
		test.membershipRenewalPage
				.verifyRenewalForScheduled(
						name,
						getMemRenewalInfo.getRenewalInfoAtAdd("association"),
						runTaskDateTime,
						getMemRenewalInfo
								.getRenewalInfoForScheduled("proforma"),
						getMemRenewalInfo.getRenewalInfoForScheduled("status"),
						getMemRenewalInfo
								.getRenewalInfoForScheduled("createInvoiceScheduleTask"),
						getMemRenewalInfo.getRenewalInfoAtAdd("batch"));
		test.membershipRenewalPage
				.clickOnSubInfoDropdown("batch renewal summary");
		test.membershipRenewalPage.verifyNoResultDisplay();

		test.membershipRenewalPage.holdScriptUntilVerifyStatus(
				getMemRenewalInfo.getRenewalInfoForProcessing("status"),
				getMemRenewalInfo
						.getMemRenewalInfo("maxWaitTimeInMinutesForStatus"));

		test.membershipRenewalPage.holdScriptUntilVerifyStatus(
				getMemRenewalInfo.getRenewalInfoForSuccess("status"),
				getMemRenewalInfo
						.getMemRenewalInfo("maxWaitTimeInMinutesForStatus"));

		test.membershipRenewalPage.verifyRenewalSubDetails(getMemRenewalInfo
				.getRenewalInfoForSuccess("numberOfRenewals"),
				getMemRenewalInfo
						.getRenewalInfoForSuccess("numberOfInvoicesCreated"),
				getMemRenewalInfo.getRenewalInfoForSuccess("numberOfErrors"),
				getMemRenewalInfo
						.getMemRenewalInfo("maxWaitTimeInMinutesForStatus"));
	}

	@Test
	public void Step05_Fill_Details_In_Create_Renewal_Invoices() {
		time = test.membershipRenewalPage
				.navigateToCreateRenewalInvoicesAndEnterInvoiceTaskStartTimeAndDate(getMemRenewalInfo
						.getMemRenewalInfo("timeSlabInMinutes"));
		test.membershipRenewalPage.verifyErrorMessage();

	}

	@Test
	public void Step06_Verify_Renewal_Details_For_create_Renewal_Invoices() {
		test.membershipRenewalPage
				.verifyCreateInvoiceTaskStartTimeAndDate(time);

		test.membershipRenewalPage.holdScriptUntilVerifyStatus(
				getMemRenewalInfo.getRenewalInfoForSuccess("status"),
				getMemRenewalInfo
						.getMemRenewalInfo("maxWaitTimeInMinutesForStatus"));

		test.membershipRenewalPage
				.verifyRenewalSubDetails(
						getMemRenewalInfo
								.getCreateRenewalInvoiceSuccessInfo("numberOfRenewals"),
						getMemRenewalInfo
								.getCreateRenewalInvoiceSuccessInfo("numberOfInvoicesCreated"),
						getMemRenewalInfo
								.getCreateRenewalInvoiceSuccessInfo("numberOfErrors"),
						getMemRenewalInfo
								.getMemRenewalInfo("maxWaitTimeInMinutesForStatus"));
	}

	@Test(invocationCount = 2)
	public void Step07_Navigate_To_Membership_Profile_Page_And_Verify_Details_Test() {
		@SuppressWarnings("unchecked")
		List<String> memberDetails = (List<String>) obj[invocationCount];
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnMemberShipTab();
		test.homePageIWEB.clickOnSideBarTab("Members");
		test.memberShipPage.clickOnSideBar("Query Membership");
		test.memberShipPage.selectAndRunQuery("Selenium - Select One Member");
		test.memberShipPage.enterSingleCustomerIdInRunQuery(memberDetails
				.get(3));
		test.individualsPage.selectOneIndividual(memberDetails.get(4),
				"expire date");
		test.memberShipPage.verifyMembershipDetailsOnRenewal(
				memberDetails.get(4), memberDetails.get(0),
				memberDetails.get(1), memberDetails.get(3),
				memberDetails.get(5), memberDetails.get(6),
				memberDetails.get(2));

		test.memberShipPage.verifyInvoiceDetailsOnRenewal(memberDetails.get(7),
				memberDetails.get(8));
		test.memberShipPage.navigateToInvoicePageForFirstProduct();

		test.invoicePage.verifyMemberDetailsOnInvoicePage(
				getMemRenewalInfo.getRenewalInvoiceDetails("proforma"),
				getMemRenewalInfo.getRenewalInvoiceDetails("paidInFull"),
				memberDetails.get(3),
				getMemRenewalInfo.getRenewalInfoAtAdd("batch"),
				memberDetails.get(8));
		test.invoicePage.expandDetailsMenuIfAlreadyExpanded("line items");
		test.invoicePage.verifyInvoiceDetailsOnInvoiceProfilePage(
				memberDetails.get(8), memberDetails.get(7));

		invocationCount++;

	}

	/**
	 * Following methods are to setup and clean up the tests
	 */
	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		mapMemberShipRenewal = YamlReader.getYamlValues("MemberShipRenewal");
		getMemRenewalInfo = new YamlInformationProvider(mapMemberShipRenewal);
		app_url_IWEB = getYamlValue("app_url_IwebReporting");
		test.launchApplication(app_url_IWEB);

	}

	@BeforeMethod
	public void handleTestMethodName(Method method) {
		test.printMethodName(method.getName());
	}
}
