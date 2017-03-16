package com.qait.MAPS.tests;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class Maps_Submission_Test extends BaseTest {
	private String maps_url;
	
	@BeforeClass
	public void Start_Test_Session() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		maps_url = YamlReader.getYamlValue("MAPS_Url");
	}
	
	@Test
	public void MAPS_1_Launch_Application() {
     System.out.println("-----------");
		test.launchMAPSApplication(maps_url);
		
		Assert.assertTrue(test.maps_SSOPage.verifyUserIsOnMAPSHomePage("Meeting Abstracts Programming System"), message);

	}

}
