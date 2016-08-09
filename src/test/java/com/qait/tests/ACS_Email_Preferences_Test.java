package com.qait.tests;
import static com.qait.automation.utils.YamlReader.getYamlValue;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;

public class ACS_Email_Preferences_Test {

	TestSessionInitiator test;
	String app_url_IWEB, webLogin, app_url_email;
			

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IWEB");
		app_url_email = getYamlValue("app_url_email");
	}

	@Test
	public void Step01_Launch_Iweb_Application_And_Verify_User_Is_On_Home_Page() {
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(
				YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB
				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test
	public void Step02_Goto_Marketing_module_And_Verify_User_Is_On_Marketting_Module()
	{
		
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Marketing");
		
		
		
		
	}
	
	
	
	

}
