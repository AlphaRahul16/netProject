/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qait.automation;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import com.qait.automation.utils.ConfigPropertyReader;

public class WebDriverFactory {
	private static String browser;
	private static final DesiredCapabilities capabilities = new DesiredCapabilities();

	public WebDriver getDriver(Map<String, String> seleniumconfig) {
		browser = seleniumconfig.get("browser");

		if (seleniumconfig.get("seleniumserver").equalsIgnoreCase("local")) {
			if (browser.equalsIgnoreCase("firefox")) {
				return getFirefoxDriver();
			} else if (browser.equalsIgnoreCase("chrome")) {
				return getChromeDriver(seleniumconfig.get("driverpath")+"chromedriver.exe");
			} else if (browser.equalsIgnoreCase("Safari")) {
				return getSafariDriver();
			} else if ((browser.equalsIgnoreCase("ie"))
					|| (browser.equalsIgnoreCase("internetexplorer"))
					|| (browser.equalsIgnoreCase("internet explorer"))) {
				return getInternetExplorerDriver(seleniumconfig
						.get("driverpath")+"IEDriverServer.exe");
			}
		}
		if (seleniumconfig.get("seleniumserver").equalsIgnoreCase("remote")) {
			return setRemoteDriver(seleniumconfig);
		}
		return new FirefoxDriver();
	}

	private WebDriver setRemoteDriver(Map<String, String> selConfig) {
		DesiredCapabilities cap = null;
		browser = selConfig.get("browser");
		if (browser.equalsIgnoreCase("firefox")) {
			cap = DesiredCapabilities.firefox();
		} else if (browser.equalsIgnoreCase("chrome")) {
			cap = DesiredCapabilities.chrome();
		} else if (browser.equalsIgnoreCase("Safari")) {
			cap = DesiredCapabilities.safari();
		} else if ((browser.equalsIgnoreCase("ie"))
				|| (browser.equalsIgnoreCase("internetexplorer"))
				|| (browser.equalsIgnoreCase("internet explorer"))) {
			cap = DesiredCapabilities.internetExplorer();
		}
		String seleniuhubaddress = selConfig.get("seleniumserverhost");
		URL selserverhost = null;
		try {
			selserverhost = new URL(seleniuhubaddress);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		cap.setJavascriptEnabled(true);
		return new RemoteWebDriver(selserverhost, cap);
	}

	private static WebDriver getChromeDriver(String driverpath) {
		System.setProperty("webdriver.chrome.driver", driverpath);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-extensions");
		options.addArguments("test-type");
		
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		return new ChromeDriver(cap);
	}

	private static WebDriver getInternetExplorerDriver(String driverpath) {
		System.setProperty("webdriver.ie.driver", driverpath);
		capabilities.setCapability("ignoreZoomSetting", true);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		return new InternetExplorerDriver(capabilities);
	}

	private static WebDriver getSafariDriver() {
		return new SafariDriver();
	}

	private static WebDriver getFirefoxDriver() {
		String firefoxProfilePath = "./src/test/resources/Profile_"
				+ ConfigPropertyReader.getProperty("tier");
		FirefoxProfile profile;
		String autoAuthPath = "src/test/resources/AddOn/autoauth-2.1-fx+fn.xpi";
		File firefoxProfile = new File(firefoxProfilePath);// path of firefox
															// profile
		profile = new FirefoxProfile(firefoxProfile);
		File extension = new File(autoAuthPath);// path of AutoAuth
		try {
			profile.addExtension(extension);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		profile.setPreference("browser.cache.disk.enable", false);
		return new FirefoxDriver(profile);
	}
}
