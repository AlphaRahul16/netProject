package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_Address_Validation_Test extends BaseTest {
	
	String app_url_IWEB,expectedZipCode="", individualName ;
	
	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IWEB");
	}	
	
	@BeforeMethod
	public void handleTestMethodName(Method method) {
		test.printMethodName(method.getName());
	}
	
	@Test
	public void Step01_Launch_Iweb_Application() {
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));		
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}
		
//	@Test(dependsOnMethods="Step01_Launch_Iweb_Application")
//	public void Step02_Select_Query_In_Query_Individual_Page(){
//		test.homePageIWEB.clickOnSideBarTab("Individuals");
//		test.memberShipPage.clickOnTab("Query Individual");
//		test.memberShipPage.selectAndRunQuery(getYamlValue("AddressValidation.queryName"));
//	}
//	
//	@Test(dependsOnMethods="Step02_Select_Query_In_Query_Individual_Page")
//	public void Step03_Verify_User_Naviagated_To_Individual_Profile_Page(){
//		individualName=test.acsAddressValidation.verifyIndividualProfilePage();
//		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Individuals | "+individualName);
//	}
//
//	@Test(dependsOnMethods="Step03_Verify_User_Naviagated_To_Individual_Profile_Page")
//	public void Step04_Enter_ZipCode_And_Verify_Replacement_Of_ZipCode(){
//		test.memberShipPage.getContactIdOfUser("Customer");
//		expectedZipCode=test.acsAddressValidation.getZipCode();
//        test.acsAddressValidation.clickOnEditNameAndAddressButton();
//        test.acsAddressValidation.verifyIndividualNameAndAddressInformationPage();
//        test.acsAddressValidation.verifyZipCode(expectedZipCode);
//        test.acsAddressValidation.enterZipCode(expectedZipCode,"12345");
//        test.acsAddressValidation.verifyAddressVerificationWindow();
//        test.acsAddressValidation.verifyAddressVerificationPageZipCode(expectedZipCode);
//        test.acsAddressValidation.verifyReplacementOfZipCode(expectedZipCode);
//	}

}
