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
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_BPA_Test extends BaseTest {
	private int caseID;
	private String customerId,promocode;
	String app_url_IWEB = getYamlValue("app_url_IWEB");
	String[] BPATypeInfoArray = {"industry","title","last industry update","last job title update"};
	Map<String,String> bpaMap;
	
	
	public ACS_BPA_Test() {
	 com.qait.tests.Data_Provider_Factory_Class_Xml.sheetName = "BPA_Scenariosheet";
	}

	@Factory(dataProviderClass = com.qait.tests.Data_Provider_Factory_Class_Xml.class, dataProvider = "data")
	public ACS_BPA_Test(int caseID) {
		this.caseID = caseID;
	}
	@Test
	public void Step_01_Edit_Existing_BPA() {
		app_url_IWEB = getYamlValue("app_url_IWEB");
		//test.enterAuthenticationAutoIt();
		//test.enterAuthenticationAutoIt();
        test.launchApplication(app_url_IWEB);
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.homePageIWEB.clickOnTab("Query Individual");
		
		test.memberShipPage.selectAndRunQuery("Selenium - Find Active Regular Member");
		customerId=test.memberShipPage.getMemberDetailsOnMemberShipProfile("contact id");
		test.individualsPage.clickImageButtonsOnAdditionalInformationPanel("Demographics");
		bpaMap=test.individualsPage.fillBPAInformationAfterClickinhDemographics(BPATypeInfoArray);
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Log");
		test.individualsPage.expandDetailsMenu("acs demographics change log");
		test.individualsPage.verifyDemographicChangeLogChildform(BPATypeInfoArray,bpaMap);
	}
	

	@Test(dependsOnMethods={"Step_01_Edit_Existing_BPA"})
	public void Step_02_Create_BPA_From_Rapid_Entry_Form() {
		test.homePageIWEB.clickOnTab("Find BPA Info");
		promocode=test.individualsPage.fillDataOnRapidEntryFormAndSaveChanges(bpaMap,BPATypeInfoArray,customerId);
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.homePageIWEB.clickOnTab("Find Individual");
		test.individualsPage.enterFieldValue("Record Number", customerId);
		test.individualsPage.clickGoButton();
		test.individualsPage.verifyDemographicChangeLogChildform(BPATypeInfoArray,bpaMap);
		test.individualsPage.verifyChildFormForUpdatedDemographicLog("Call to Action",promocode);
		
	} 
	
	
	
	@BeforeClass
	public void Start_Test_Session() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
	}

	@BeforeMethod
	public void printTestMethodName(Method method) {
		test.printMethodName(method.getName());

	}
}
