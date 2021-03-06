package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.XlsReader;

public class ACS_Member_Transfer_Smoke_Test extends BaseTest{

	String app_url_iweb, custId;
	String url;
	List<Integer> rowNumberList = new ArrayList<Integer>();
	List<String> fieldDefinition = new ArrayList<String>();
	List<String> fieldNameOnSpreadSheet = new ArrayList<String>();
	HashMap<String, String> dataList = new HashMap<String, String>();
	LinkedHashMap<String, String> BeforeList = new LinkedHashMap<String, String>();
	LinkedHashMap<String, String> AfterList = new LinkedHashMap<String, String>();
	LinkedHashMap<String, String> resultList = new LinkedHashMap<String, String>();
	/*
	 * @SuppressWarnings("unused")
	 */ private int caseID;

	int i, j;

	@Factory(dataProviderClass = com.qait.tests.Data_Provider_Member_Transfer_Class.class, dataProvider = "data")
	public ACS_Member_Transfer_Smoke_Test(int caseID) {
		this.caseID = caseID;
	}

	@BeforeClass
	public void open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		// rowNumberList =
		// XlsReader.getListOfRowsNumberToBeExecuted("Scenerios");
		// dataList = XlsReader.getValuesFromDataSheet("Scenerios");
		System.out.println("Row Num::" + caseID);
		dataList = XlsReader.addValuesInTheMap("Scenerios", caseID);
		fieldDefinition = XlsReader.getValuesFromFieldDefinitionsSheet("Field Definitions", "Field Name on Page");
		fieldNameOnSpreadSheet = XlsReader.getValuesFromFieldDefinitionsSheet("Field Definitions",
				"Field Name on Spreadsheet");
		app_url_iweb = getYamlValue("app_url_IWEB");
	}
	@BeforeMethod
	public void handleTestMethodName(Method method) {
		test.printMethodName(method.getName());
	}


	@Test
	public void Step01_TC01_launch_Iweb_And_Fetch_Member_Details_Before_Transfer() {
		test.launchApplication(app_url_iweb);
		System.out.println("case id::" + caseID);
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
		test.homePageIWEB.clickOnSideBarTab("Members");
		test.homePageIWEB.clickOnTab("Query Membership");
		test.homePageIWEB.verifyUserIsOnHomePage("Query - Membership");
		if (dataList.get("Initial MP Exp Date").equalsIgnoreCase("Future")) {
			test.memberShipPage.selectAndRunQuery("Selenium - Member Transfer Query - Future Expire");
		} else if (dataList.get("Initial MP Exp Date").equalsIgnoreCase("Past")) {
			test.memberShipPage.selectAndRunQuery("Selenium - Member Transfer Query - Past Expire");
		}
		test.individualsPage.enterValueInMemberStatusField("Member Type", "ACS : " + dataList.get("Initial Mbr Type"));
		test.individualsPage.enterValueInMemberStatusField("Member Status",
				"ACS : " + dataList.get("Initial Mbr Status"));
		test.individualsPage.enterValueInMemberStatusField("Member Package",
				"ACS : Regular Member : " + dataList.get("Initial Mbr Package"));
		test.individualsPage.enterValueInExpireInputField(DateUtil.getCurrentdateInStringWithGivenFormate("M/d/yyyy"));
		test.memberShipPage.clickOnGoAskButton();

		url = test.asm_CCEDPage.getCurrentURL();

		for (String field : fieldDefinition) {
			System.out.println("" + field);
		}

		for (i = 0; i < 4; i++) {
			BeforeList.put(fieldNameOnSpreadSheet.get(i),
					test.memberShipPage.getMemberInfoOnMemberShipProfile(fieldDefinition.get(i)));
		}

		for (j = i; j < 7; j++) {
			BeforeList.put(fieldNameOnSpreadSheet.get(j),
					test.memberShipPage.getMemberDetailsOnMemberShipProfile(fieldDefinition.get(j)));
		}
		BeforeList.put(fieldNameOnSpreadSheet.get(j), test.memberShipPage.numberOfYearsForInactiveMember());
		j++;
		BeforeList.put(fieldNameOnSpreadSheet.get(j), test.memberShipPage.getPaymentStatus());
		j++;
		test.invoicePage.expandDetailsMenu("invoices");
		BeforeList.put(fieldNameOnSpreadSheet.get(j), test.memberShipPage.getProductName());
		j++;
		BeforeList.put(fieldNameOnSpreadSheet.get(j), test.memberShipPage.getPriceValue());
		j++;
		BeforeList.put(fieldNameOnSpreadSheet.get(j), test.memberShipPage.getTermStartDate());
		j++;
		BeforeList.put(fieldNameOnSpreadSheet.get(j), test.memberShipPage.getTermEndDate());
		j++;
		test.memberShipPage.navigateToInvoicePageForFirstProduct();

		BeforeList.put(fieldNameOnSpreadSheet.get(j),
				test.invoicePage.getDataFromInvoiceProfilePage(fieldDefinition.get(j)));
		j++;
		BeforeList.put(fieldNameOnSpreadSheet.get(j),
				test.invoicePage.getDataFromInvoiceProfilePage(fieldDefinition.get(j)));
		j++;
		BeforeList.put(fieldNameOnSpreadSheet.get(j),
				test.invoicePage.getDataFromInvoiceProfilePage(fieldDefinition.get(j)));
		j++;

		test.memberShipPage.clickOnCustomerNameAndNavigateToMembershipPage();
		custId = test.memberShipPage.getCustomerID();
		test.invoicePage.expandDetailsMenu("individual memberships");

		BeforeList.put(fieldNameOnSpreadSheet.get(j),
				test.individualsPage.getIndividualInformationWhichHasTerminateValueNull(5));
		j++;
		BeforeList.put(fieldNameOnSpreadSheet.get(j),
				test.individualsPage.getIndividualInformationWhichHasTerminateValueNull(6));
		j++;
		BeforeList.put(fieldNameOnSpreadSheet.get(j),
				test.individualsPage.getIndividualInformationWhichHasTerminateValueNull(7));
		j++;
		BeforeList.put(fieldNameOnSpreadSheet.get(j),
				test.individualsPage.getIndividualInformationWhichHasTerminateValueNull(9));
		j++;
		BeforeList.put(fieldNameOnSpreadSheet.get(j),
				test.individualsPage.getIndividualInformationWhichHasTerminateValueNull(10));
		test.invoicePage.collapseDetailsMenu("individual memberships");

		for (Map.Entry m : BeforeList.entrySet()) {
			System.out.println(m.getKey() + " " + m.getValue());
		}

		test.memberShipPage.verifyDataBeforeTransferFullFilledTheCriteria(BeforeList, dataList,dataList.get("ID"),custId);
	}

	@Test(dependsOnMethods = "Step01_TC01_launch_Iweb_And_Fetch_Member_Details_Before_Transfer")
	public void Step02_TC02_Fetch_Member_Details_After_Transfer() {
		test.navigateToURL(url);
		test.memberShipPage.clickOnMemberTransferButton();
		test.memberShipPage.updateInformationAfterClickingTransferButton(dataList.get("Target Mbr Type").trim(),
				dataList.get("Target Mbr Package"));

		for (i = 0; i < 4; i++) {
			AfterList.put(fieldNameOnSpreadSheet.get(i),
					test.memberShipPage.getMemberInfoOnMemberShipProfile(fieldDefinition.get(i)));
		}

		for (j = i; j < 7; j++) {
			AfterList.put(fieldNameOnSpreadSheet.get(j),
					test.memberShipPage.getMemberDetailsOnMemberShipProfile(fieldDefinition.get(j)));
		}
		AfterList.put(fieldNameOnSpreadSheet.get(j), test.memberShipPage.numberOfYearsForInactiveMember());
		j++;
		AfterList.put(fieldNameOnSpreadSheet.get(j), test.memberShipPage.getPaymentStatus());
		j++;

		AfterList.put(fieldNameOnSpreadSheet.get(j), test.memberShipPage.getProductName());
		j++;
		AfterList.put(fieldNameOnSpreadSheet.get(j), test.memberShipPage.getPriceValue());
		j++;
		AfterList.put(fieldNameOnSpreadSheet.get(j), test.memberShipPage.getTermStartDate());
		j++;
		AfterList.put(fieldNameOnSpreadSheet.get(j), test.memberShipPage.getTermEndDate());
		j++;
		test.memberShipPage.navigateToInvoicePageForFirstProduct();

		AfterList.put(fieldNameOnSpreadSheet.get(j),
				test.invoicePage.getDataFromInvoiceProfilePage(fieldDefinition.get(j)));
		j++;
		AfterList.put(fieldNameOnSpreadSheet.get(j),
				test.invoicePage.getDataFromInvoiceProfilePage(fieldDefinition.get(j)));
		j++;
		AfterList.put(fieldNameOnSpreadSheet.get(j),
				test.invoicePage.getDataFromInvoiceProfilePage(fieldDefinition.get(j)));
		j++;

		test.memberShipPage.clickOnCustomerNameAndNavigateToMembershipPage();
		test.invoicePage.expandDetailsMenu("individual memberships");

		AfterList.put(fieldNameOnSpreadSheet.get(j),
				test.individualsPage.getIndividualInformationWhichHasTerminateValueNull(5));
		j++;
		AfterList.put(fieldNameOnSpreadSheet.get(j),
				test.individualsPage.getIndividualInformationWhichHasTerminateValueNull(6));
		j++;
		AfterList.put(fieldNameOnSpreadSheet.get(j),
				test.individualsPage.getIndividualInformationWhichHasTerminateValueNull(7));
		j++;
		AfterList.put(fieldNameOnSpreadSheet.get(j),
				test.individualsPage.getIndividualInformationWhichHasTerminateValueNull(9));
		j++;
		AfterList.put(fieldNameOnSpreadSheet.get(j),
				test.individualsPage.getIndividualInformationWhichHasTerminateValueNull(10));
		test.invoicePage.collapseDetailsMenu("individual memberships");

		for (Map.Entry m : AfterList.entrySet()) {
			System.out.println(m.getKey() + " " + m.getValue());
		}

		Reporter.log("Customer/Member ID ::" + custId);
	}

	@Test(dependsOnMethods = "Step01_TC01_launch_Iweb_And_Fetch_Member_Details_Before_Transfer")
	public void Step03_TC03_Match_Before_Data_With_After_Data_According_To_Mentioned_Criteria() {
//		resultList = 
		test.memberShipPage.matchBeforeDataWithAfterDataAccordingToMentionedCriteria(BeforeList,AfterList, dataList, custId);
//		 test.memberShipPage.verifyResultListData(resultList);
		
	}

}
