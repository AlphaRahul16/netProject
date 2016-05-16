package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.utils.YamlReader;
import com.qait.automation.TestSessionInitiator;


public class ACS_Address_Validation_Test {
	
	TestSessionInitiator test;
	String app_url_IWEB, expectedZipCode="", individualName ;
	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IWEB");
	}
	
	@Test
	public void Step01_Launch_Iweb_Application() {
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}
		
	@Test
	public void Step02_Select_Query_In_Query_Individual_Page(){
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.clickOnTab("Query Individual");
		test.memberShipPage.selectAndRunQuery(getYamlValue("AddressValidation.queryName"));
	}
	
	@Test
	public void Step03_Verify_User_Naviagated_To_Individual_Profile_Page(){
		individualName=test.acsAddressValidation.verifyIndividualProfilePage();
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Individuals | "+individualName);
	}

	@Test
	public void Step04_Enter_ZipCode_And_Verify_ZipCode(){
		test.memberShipPage.getContactIdOfUser("Contact");
		expectedZipCode=test.acsAddressValidation.getZipCode();
        test.acsAddressValidation.clickOnEditNameAndAddressButton();
        test.acsAddressValidation.verifyIndividualNameAndAddressInformationPage();
        test.acsAddressValidation.verifyZipCode(expectedZipCode);
        test.acsAddressValidation.enterZipCode(expectedZipCode,"12345");
        test.acsAddressValidation.verifyAddressVerificationWindow();
        test.acsAddressValidation.verifyAddressVerificationPageZipCode(expectedZipCode);
        test.acsAddressValidation.verifyReplacementOfZipCode(expectedZipCode);
	}
	
	
	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result)
	{
		test.takescreenshot.takeScreenShotOnException(result);
	}
	
	@AfterClass
	public void Close_Browser_Session() {
		test.closeBrowserWindow();
	}

}
