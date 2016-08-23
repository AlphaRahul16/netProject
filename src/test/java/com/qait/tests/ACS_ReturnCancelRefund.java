package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_ReturnCancelRefund extends BaseTest {

	String app_url_IWEB;
	String creditbatchName, refundbatchname;
	String invoiceTotal;
	ArrayList<String> refundInfo;
	List<String> batchValues = new ArrayList<>();

	@Test
	public void Step01_Navigate_To_Find_Batch_Tab_Under_Accounting_Module() {

		test.homePageIWEB.clickOnSideBarTab("Invoice");
		test.homePageIWEB.clickOnTab("Query Invoice");
	}

	@Test
	public void Step02_Search_For_Store_Batches_And_Navigate_To_Invoice_Profile_Page() {

		test.memberShipPage.selectAndRunQuery("Selenium return/cancel");

	}

	@Test
	public void Step03_Select_Random_Invoice_Record_And_Fetch_Data_For_That_Invoice() {

		invoiceTotal = test.invoicePage
				.getDataFromInvoiceProfilePage("invoice total");

	}

	@Test
	public void Step04_Click_On_Void_Invoice_Button_And_Verify_Void_Invoice_Message() {
		test.acsVoidInvoice.clickOnVoidInvoiceButton("return/cancel", 3);
		creditbatchName = test.acsVoidInvoice.createBatch(1, 6, "QA");
		test.acsVoidInvoice.saveChangesForReturnCancel();
		test.acsVoidInvoice
				.verifyVoidInvoiceMessage(getYamlValue("VoidWithAdjustment.voidMessage"));
	}

	@Test
	public void Step05_Navigate_To_Other_ACTG_On_Membership_Page_And_Verify_Credit_Has_Been_Added_To_List() {
		test.memberShipPage.clickOnCustomerNameAndNavigateToMembershipPage();
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Other Actg");
		test.individualsPage.expandDetailsMenu("credits");
		test.acsVoidInvoice.verifyCrediHasBeenAddedToList(creditbatchName,
				invoiceTotal);
	}

	@Test
	public void Step06_Navigate_To_Batch_Profile_Page_And_Process_batch_For_Close() {
		test.acsVoidInvoice.clickOnGotoCreditRecord(creditbatchName);
		test.acsVoidInvoice
				.NavigateToBatchProfilePageByClickingOnBatchName(creditbatchName);
		test.acsbatchProcessing.clickEditButtonOnBatchProcessingPage();
		batchValues = test.acsbatchProcessing
				.enterDetailsForBatchProcessingAndClickSaveButton();
		test.acsbatchProcessing.verifyDetailsOnBatchSummaryInfo("count",
				batchValues.get(1));
		test.acsbatchProcessing
				.clickOnBatchProcessButtonsAndVerifyPopUpWindowAppears();
		test.acsbatchProcessing.verifyDetailsOnBatchSummaryInfo("total",
				batchValues.get(0));
	}

	@Test
	public void Step07_Navigate_To_Add_Refund_Page_And_Search_For_Current_Refunds() {
		test.homePageIWEB.clickOnSideBarTab("Refund");
		test.homePageIWEB.clickOnTab("Add Refund");
		test.acsVoidInvoice.enterCreditFromAndTooDates("From", -1);
		test.acsVoidInvoice.enterCreditFromAndTooDates("Thru", 1);
		test.acsVoidInvoice.clickSearchRefundsButton();
	}

	@Test
	public void Step08_Create_New_Batch_For_Refund_And_And_Fetch_Refund_Info() {
		refundbatchname = test.acsVoidInvoice.createBatchForRefunds(1, 6, "QA");
		test.acsbatchProcessing.uncheckAllRefundCCACHCheckboxes();
		refundInfo = test.acsbatchProcessing.getRefundAmountInfo();
		test.acsVoidInvoice.clickOnSaveButtonAndHandelAlert();
	}

	@Test
	public void Step10_Verify_Refund_And_Credit_Details() {
		test.invoicePage.expandDetailsMenu("refund detail");
		test.acsbatchProcessing.verifyRefundDetails(refundInfo, "refund");
		test.invoicePage.collapseDetailsMenu("refund detail");
		test.invoicePage.expandDetailsMenu("related credit");
		test.acsbatchProcessing.verifyRefundDetails(refundInfo, "return");
		test.invoicePage.collapseDetailsMenu("related credit");
	}

	@Test
	public void Step11_Prcoess_Refund_Batch_For_Close_And_Verify_FTP_Button_Is_Inactive() {
		test.acsVoidInvoice
				.NavigateToBatchProfilePageByClickingOnBatchName(refundbatchname);
		test.acsbatchProcessing.clickEditButtonOnBatchProcessingPage();
		test.acsbatchProcessing
				.enterDetailsForBatchProcessingAndClickSaveButton();
		test.acsbatchProcessing
				.clickOnBatchProcessButtonsAndVerifyPopUpWindowAppears();
		test.acsbatchProcessing.verifyFtpReportButtonIsInactive();
	}

	@BeforeClass
	public void open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IWEB");
		test.launchApplication(app_url_IWEB);
		test.homePage.enterAuthentication(
				YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
	}

}
