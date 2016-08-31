package com.qait.automation.getpageobjects;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.qait.automation.TestSessionInitiator;

public class BaseTest {
	public TestSessionInitiator test;
	public static String caseID;

	@BeforeMethod
	public void printTestMethodName(Method method) {
		test.printMethodName(method.getName());
		if (caseID != null)
			Reporter.log("****** TEST CASE ID : " + caseID + " ******\n", true);
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
	}

	// @AfterClass(alwaysRun = true)
	public void Close_Browser_Session() {
		test.closeBrowserWindow();
	}

}
