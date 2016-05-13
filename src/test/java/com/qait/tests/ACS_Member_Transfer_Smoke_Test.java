package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;

public class ACS_Member_Transfer_Smoke_Test {

	TestSessionInitiator test;
	String app_url_iweb;

	@BeforeClass
	public void open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_iweb = getYamlValue("app_url_IWEB");
		test.launchApplication(app_url_iweb);
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
	}

	@Test
	public void Step01_TC01_launch_Iweb_And_Fetch_Member_Details() {
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
		test.homePageIWEB.clickOnSideBarTab("Members");
		test.homePageIWEB.clickOnTab("Query Membership");
		test.homePageIWEB.verifyUserIsOnHomePage("Query - Membership");
	}

}
