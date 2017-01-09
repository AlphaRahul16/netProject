/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qait.automation;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
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

public class WebDriverFactory {
	private static String browser;
	String seleniumServer;
	static String downloadFilePath = System.getProperty("user.dir")
			+ File.separator + "src" + File.separator + "test" + File.separator
			+ "resources" + File.separator + "DownloadedFiles";
	private static DesiredCapabilities capabilities = new DesiredCapabilities();

	public WebDriver getDriver(Map<String, String> seleniumconfig) {

		seleniumServer = seleniumconfig.get("seleniumserver");

		browser = seleniumconfig.get("browser");

		if (seleniumServer.equalsIgnoreCase("local")) {
			if (browser.equalsIgnoreCase("firefox")) {
				return getFirefoxDriver(seleniumconfig.get("tier"));
			} else if (browser.equalsIgnoreCase("chrome")) {
				
				if(System.getProperty("os.name").equals("Linux"))
				{
					return getChromeDriver(seleniumconfig.get("driverpath")
							+ "chromedriver");	
				}
				else if(System.getProperty("os.name").contains("Windows"))
				{
					return getChromeDriver(seleniumconfig.get("driverpath")
						+ "chromedriver.exe");
				}
				
			} else if (browser.equalsIgnoreCase("Safari")) {
				return getSafariDriver();
			} else if ((browser.equalsIgnoreCase("ie"))
					|| (browser.equalsIgnoreCase("internetexplorer"))
					|| (browser.equalsIgnoreCase("internet explorer"))) {
				return getInternetExplorerDriver(seleniumconfig
						.get("driverpath") + "IEDriverServer.exe");
			}
		}
		if (seleniumServer.equalsIgnoreCase("remote")) {
			return setRemoteDriver(seleniumconfig);
		}
		return new FirefoxDriver();
	}

	private WebDriver setRemoteDriver(Map<String, String> selConfig) {
		DesiredCapabilities cap = null;
		browser = selConfig.get("browser");
		if (browser.equalsIgnoreCase("firefox")) {
			cap = DesiredCapabilities.firefox();
			cap.setCapability(FirefoxDriver.PROFILE, getFirefoxProfile(selConfig.get("tier")));
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
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		
		chromePrefs.put("download.prompt_for_download", "false");
		// disable flash and the PDF viewer
		chromePrefs.put("plugins.plugins_disabled", new String[]{
		    "Adobe Flash Player", "Chrome PDF Viewer"});
		
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadFilePath);
		options.setExperimentalOption("prefs", chromePrefs);	
		options.addArguments("--disable-extensions");
		options.addArguments("test-type");
		options.addArguments("--disable-impl-side-painting");

		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		return new ChromeDriver(cap);
	}
	
	private static WebDriver getInternetExplorerDriver(String driverpath) {
		System.setProperty("webdriver.ie.driver", driverpath);
		capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability("ignoreZoomSetting", true);
//		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
////		  DesiredCapabilities ieCapabilities = null;
//		capabilities.setBrowserName("internet explorer");
//		
//		//capabilities.setVersion("9");
//	//	System.out.println("----"+capabilities.);
		return new InternetExplorerDriver(capabilities);
	}

	private static WebDriver getSafariDriver() {
		return new SafariDriver();
	}

	private static WebDriver getFirefoxDriver(String tier) {
		FirefoxProfile profile;
		if(tier.equalsIgnoreCase("Dev7")){
		String firefoxProfilePath = "."+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"Profile_"
				+ tier;
		String autoAuthPath = "."+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"AddOn"+File.separator+"autoauth-2.1-fx+fn_0.xpi";
		File firefoxProfile = new File(firefoxProfilePath);

		profile = new FirefoxProfile(firefoxProfile);
		File extension = new File(autoAuthPath);
		try {
			profile.addExtension(extension);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		else
			profile = new FirefoxProfile();
	
//		profile = new FirefoxProfile();
		profile.setPreference("browser.download.useDownloadDir", true);
		profile.setPreference("browser.cache.disk.enable", false);
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
		profile.setPreference("intl.accept_languages","UTF-8" );
		profile.setPreference(
				"browser.helperApps.neverAsk.saveToDisk",
				"application/pdf, application/csv, application/ris, text/csv,text/plain, application/zip, application/x-zip, application/x-zip-compressed, application/download, application/octet-stream,text/csv,application/vnd.ms-excel");
		profile.setPreference("plugin.disable_full_page_plugin_for_types", "application/pdf,application/vnd.adobe.xfdf,application/vnd.fdf,application/vnd.adobe.xdp+xml");
		profile.setPreference("browser.download.manager.showWhenStarting",
				false);
		profile.setPreference("browser.download.manager.focusWhenStarting",
				false);
		profile.setPreference("browser.download.dir", downloadFilePath);

		profile.setPreference("browser.download.manager.showAlertOnComplete",
				false);
		
		profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
		profile.setPreference("pdfjs.disabled", true);
		
		profile.setPreference("browser.download.manager.showAlertOnComplete",false);

		profile.setPreference("browser.download.manager.showAlertOnComplete",false);
		profile.setPreference("browser.download.manager.showWhenStartinge",false);
		profile.setPreference("browser.download.panel.shown",false);
		profile.setPreference("browser.download.useToolkitUI",true);
		profile.setPreference("dom.disable_beforeunload", true);
//		profile.setPreference("network.http.phishy-userpass-length", 255);
//		profile.setPreference("network.automatic-ntlm-auth.trusted-uris","yourDomain");
//		return new FirefoxDriver(ffbinary,profile);
		return new FirefoxDriver(profile);
	}
	
	public static FirefoxProfile getFirefoxProfile(String tier){
		FirefoxProfile profile = null;
		if(tier.equalsIgnoreCase("Dev7")){
		String firefoxProfilePath = "."+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"Profile_"
				+ tier;
		String autoAuthPath = "."+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"AddOn"+File.separator+"autoauth-2.1-fx+fn_0.xpi";
		File firefoxProfile = new File(firefoxProfilePath);

		profile = new FirefoxProfile(firefoxProfile);
		File extension = new File(autoAuthPath);
		try {
			profile.addExtension(extension);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		return profile;
}
}
