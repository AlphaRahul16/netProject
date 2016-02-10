package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.keywords.YamlInformationProvider;

public class ACS_FellowNominate {
	TestSessionInitiator test;
	String app_url_nominateFellow;
	private String caseID;
	String app_url_IWEB;
	Map<String, String> map = new HashMap<String, String>();
	
	public ACS_FellowNominate() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "fellowNominate";
	}
	
	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_FellowNominate(String caseID) {
		this.caseID = caseID;
	}
	
	@Test
	public void Step01_TC01_Enter_Valid_Credentials_To_Login_Into_Application() {
		map= test.homePageIWEB.addValuesInMap("fellowNominate", caseID);
		for (Entry<String, String> entry : map.entrySet()) {
		    String key = entry.getKey().toString();
		    String value = entry.getValue();
		    System.out.println("key, " + key + " value " + value);
		}
		
		// For Login 
		
		test.asm_FellowNomiate.loginInToApplication(map.get("username"),"password");
		test.asm_FellowNomiate.verifyUserIsOnFellowsDashboard();
	
		// For Local Section
		
		test.asm_FellowNomiate.preRequisiteForACSNomination(map.get("NominationType"), "Name",
		map.get("NomineeName"), "education");
		test.asm_FellowNomiate.fillAllRequiredDetailsToSubmitACSFellowsNominations("Local Section",map);
		test.asm_FellowNomiate.clickReturnToDashBoardButton();
		
		// For Individual
		
		test.asm_FellowNomiate.preRequisiteForACSIndividualNomination("Individual", "Name", map.get("NomineeName"),"education");
		test.asm_FellowNomiate.fillAllRequiredDetailsToSubmitACSFellowsNominations("Individual",map);
		test.asm_FellowNomiate.clickReturnToDashBoardButton();
		
		// For Technical division
		
		test.asm_FellowNomiate.preRequisiteForACSNomination("Technical Division","Name",map.get("NomineeName"),"education");
		test.asm_FellowNomiate.fillAllRequiredDetailsToSubmitACSFellowsNominations("Technical Division",map);
	}
	
	@Test
	public void Step02_TC03_Navigate_To_Iweb() {
		test.navigateToIWEBUrlOnNewBrowserTab(app_url_IWEB);
	}
	
	@BeforeMethod
	public void OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_nominateFellow = getYamlValue("app_url_nominateFellow");
		test.launchApplication(app_url_nominateFellow);
		app_url_IWEB = getYamlValue("app_url_IWEB");
		
	}
	
	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
		//test.closeBrowserSession();
	}
}
