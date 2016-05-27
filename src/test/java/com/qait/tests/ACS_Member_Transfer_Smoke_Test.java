package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.XlsReader;
import com.qait.automation.utils.YamlReader;

public class ACS_Member_Transfer_Smoke_Test {

	TestSessionInitiator test;
	String app_url_iweb;
	List<String> fieldDefinition = new ArrayList<String>();
	HashMap<String, String> dataList = new HashMap<String, String>();
	HashMap<String, String> BeforeList = new HashMap<String, String>();

	int i, j;

	@BeforeClass
	public void open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		dataList = XlsReader.getValuesFromDataSheet("Scenerios");
		fieldDefinition = XlsReader.getValuesFromFieldDefinitionsSheet("Field Definitions", "Field Name on Page");
		app_url_iweb = getYamlValue("app_url_IWEB");
		test.launchApplication(app_url_iweb);
	}

	@Test
	public void Step01_TC01_launch_Iweb_And_Fetch_Member_Details() {
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

		test.individualsPage.enterFieldValue("Membership :: Expire Date Greater Than",
				DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"));
		test.memberShipPage.clickOnGoButton();

		for (i = 0; i < 4; i++) {
			BeforeList.put(fieldDefinition.get(i),
					test.memberShipPage.getMemberInfoOnMemberShipProfile(fieldDefinition.get(i)));
		}

		for (j = i; j < 7; j++) {
			BeforeList.put(fieldDefinition.get(j),
					test.memberShipPage.getMemberDetailsOnMemberShipProfile(fieldDefinition.get(j)));
		}
		BeforeList.put(fieldDefinition.get(j), test.memberShipPage.numberOfYearsForInactiveMember());
		j++;
		BeforeList.put(fieldDefinition.get(j), test.memberShipPage.getPaymentStatus());
		j++;
		test.invoicePage.expandDetailsMenu("invoices");
		BeforeList.put(fieldDefinition.get(j++), test.memberShipPage.getProductName());
		BeforeList.put(fieldDefinition.get(j++), test.memberShipPage.getPriceValue());
		BeforeList.put(fieldDefinition.get(j++), test.memberShipPage.getTermStartDate());
		BeforeList.put(fieldDefinition.get(j++), test.memberShipPage.getTermEndDate());
	}

}
