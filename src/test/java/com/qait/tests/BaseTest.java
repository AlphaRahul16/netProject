package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.qait.automation.TestSessionInitiator;

public class BaseTest {
	TestSessionInitiator test;
	String app_url_IWEB;

	@BeforeMethod
	public void printTestMethodName(Method method) {
		test.printMethodName(method.getName());
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
	}

	//@AfterClass(alwaysRun = true)
	public void Close_Browser_Session() {
		test.closeBrowserWindow();
	}

}
