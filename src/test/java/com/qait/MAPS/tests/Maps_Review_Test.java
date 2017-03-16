package com.qait.MAPS.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class Maps_Review_Test extends BaseTest {
	private String maps_url;
	
	@BeforeClass
	public void Start_Test_Session() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		maps_url = YamlReader.getYamlValue("MAPS_Url");
	}
	
	@Test
	public void MAPS_01_Launch_Application() {
		test.launchMAPSApplication(maps_url);
		test.maps_SSOPage.verifyUserIsOnMAPSHomePage("Meeting Abstracts Programming System");
	}
	
	@Test
	public void MAPS_03_LogIn_With_Valid_Credentials() {
        test.maps_SSOPage.loginWithValidCredentials(YamlReader.getYamlValue("LogIn_Details.userID"), YamlReader.getYamlValue("LogIn_Details.password"));
	}
	
	@Test
	public void MAPS_06_Verify_User_Is_Navigated_To_Welcome_Page() {
		test.maps_SSOPage.verifyUserIsOnWelcomePage("Welcome");	
	}
	
	

}
