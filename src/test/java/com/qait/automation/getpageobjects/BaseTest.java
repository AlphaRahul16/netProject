package com.qait.automation.getpageobjects;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;

import com.qait.automation.TestSessionInitiator;

public class BaseTest {
	public TestSessionInitiator test;
	protected static String caseID;

	@AfterMethod(alwaysRun=true)
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
	}


	//@AfterClass(alwaysRun = true)
	public void Close_Browser_Session() {
		test.closeBrowserWindow();
	}
}