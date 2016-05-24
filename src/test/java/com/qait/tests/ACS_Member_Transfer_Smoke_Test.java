package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.XlsReader;

public class ACS_Member_Transfer_Smoke_Test {

	TestSessionInitiator test;
	String app_url_iweb, custId;
	List<String> fieldDefinition = new ArrayList<String>();
	List<String> fieldNameOnSpreadSheet = new ArrayList<String>();
	HashMap<String, String> dataList = new HashMap<String, String>();
	LinkedHashMap<String, String> BeforeList = new LinkedHashMap<String, String>();
	LinkedHashMap<String, String> AfterList = new LinkedHashMap<String, String>();
	

	int i, j;

	@BeforeClass
	public void open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		dataList = XlsReader.getValuesFromDataSheet("Scenerios");
		fieldDefinition = XlsReader.getValuesFromFieldDefinitionsSheet("Field Definitions", "Field Name on Page");
		fieldNameOnSpreadSheet = XlsReader.getValuesFromFieldDefinitionsSheet("Field Definitions", "Field Name on Spreadsheet");
		app_url_iweb = getYamlValue("app_url_IWEB");
		test.launchApplication(app_url_iweb);
	}

	@Test
	public void Step01_TC01_launch_Iweb_And_Fetch_Member_Details_Before_Transfer() {
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
		test.homePageIWEB.clickOnSideBarTab("Members");
		test.homePageIWEB.clickOnTab("Query Membership");
		test.homePageIWEB.verifyUserIsOnHomePage("Query - Membership");
		if (dataList.get("Initial MP Exp Date").equalsIgnoreCase("Future")) {
			test.memberShipPage.selectAndRunQuery("GWV - Member Transfer Query - Future Expire");
		} else if (dataList.get("Initial MP Exp Date").equalsIgnoreCase("Past")) {
			test.memberShipPage.selectAndRunQuery("GWV - Member Transfer Query - Past Expire");
		}
		test.individualsPage.enterValueInExpireInputField(DateUtil.getCurrentdateInStringWithGivenFormate("M/dd/yyyy"));
		test.memberShipPage.clickOnGoButton();
		
		String url = test.asm_CCEDPage.getCurrentURL();

		for (String field : fieldDefinition) {
			System.out.println(""+field);
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

		BeforeList.put(fieldNameOnSpreadSheet.get(j), test.invoicePage.getDataFromInvoiceProfilePage(fieldDefinition.get(j)));
		j++;
		BeforeList.put(fieldNameOnSpreadSheet.get(j), test.invoicePage.getDataFromInvoiceProfilePage(fieldDefinition.get(j)));
		j++;
		BeforeList.put(fieldNameOnSpreadSheet.get(j), test.invoicePage.getDataFromInvoiceProfilePage(fieldDefinition.get(j)));
		j++;

		test.memberShipPage.clickOnCustomerNameAndNavigateToMembershipPage();
		custId = test.memberShipPage.getCustomerID();
		test.invoicePage.expandDetailsMenu("individual memberships");

		BeforeList.put(fieldNameOnSpreadSheet.get(j), test.individualsPage.getMemberType());
		j++;
		BeforeList.put(fieldNameOnSpreadSheet.get(j), test.individualsPage.getMemberStatus());
		j++;
		BeforeList.put(fieldNameOnSpreadSheet.get(j), test.individualsPage.getJoinFieldValue());
		j++;
		BeforeList.put(fieldNameOnSpreadSheet.get(j), test.individualsPage.getEffectiveFieldValue());
		j++;
		BeforeList.put(fieldNameOnSpreadSheet.get(j), test.individualsPage.getExpireDate());
		test.invoicePage.collapseDetailsMenu("individual memberships");
		
		 for(Map.Entry m:BeforeList.entrySet()) {
			   System.out.println(m.getKey()+" "+m.getValue());  
		}

		 test.navigateToURL(url);
		 test.memberShipPage.clickOnMemberTransferButton();
		 test.memberShipPage.updateInformationAfterClickingTransferButton(dataList.get("MP Mbr Type").trim(),"Regular Emeritus Dues C&EN - Print");
	}
	
	@Test
	public void Step02_TC02_Fetch_Member_Details_After_Transfer() {
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

		AfterList.put(fieldNameOnSpreadSheet.get(j), test.invoicePage.getDataFromInvoiceProfilePage(fieldDefinition.get(j)));
		j++;
		AfterList.put(fieldNameOnSpreadSheet.get(j), test.invoicePage.getDataFromInvoiceProfilePage(fieldDefinition.get(j)));
		j++;
		AfterList.put(fieldNameOnSpreadSheet.get(j), test.invoicePage.getDataFromInvoiceProfilePage(fieldDefinition.get(j)));
		j++;

		test.memberShipPage.clickOnCustomerNameAndNavigateToMembershipPage();
		test.invoicePage.expandDetailsMenu("individual memberships");

		AfterList.put(fieldNameOnSpreadSheet.get(j), test.individualsPage.getMemberType());
		j++;
		AfterList.put(fieldNameOnSpreadSheet.get(j), test.individualsPage.getMemberStatus());
		j++;
		AfterList.put(fieldNameOnSpreadSheet.get(j), test.individualsPage.getJoinFieldValue());
		j++;
		AfterList.put(fieldNameOnSpreadSheet.get(j), test.individualsPage.getEffectiveFieldValue());
		j++;
		AfterList.put(fieldNameOnSpreadSheet.get(j), test.individualsPage.getExpireDate());
		test.invoicePage.collapseDetailsMenu("individual memberships");
		
		for(Map.Entry m:AfterList.entrySet()) {
			   System.out.println(m.getKey()+" "+m.getValue());  
		}
		
		System.out.println("Customer ID ::"+custId);
	}
	
	
	@Test
	public void Step03_TC03_Match_Before_Data_With_After_Data_According_To_Mentioned_Criteria() {
		test.memberShipPage.matchBeforeDataWithAfterDataAccordingToMentionedCriteria(BeforeList,AfterList,dataList);
	}
	

}
