/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qait.automation;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
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
	private static final DesiredCapabilities capabilities = new DesiredCapabilities();

	public WebDriver getDriver(Map<String, String> seleniumconfig) {

		seleniumServer = seleniumconfig.get("seleniumserver");

		browser = seleniumconfig.get("browser");

		if (seleniumServer.equalsIgnoreCase("local")) {
			if (browser.equalsIgnoreCase("firefox")) {
				return getFirefoxDriver();
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
		options.addArguments("--disable-impl-side-painting");

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
		
		//File pathToBinary = new File("C:\\Mozilla Firefox\\firefox.exe");
		//FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
	       
		//WebDriver driver = new FirefoxDriver(ffBinary,firefoxProfile);
//		String firefoxProfilePath = "./src/test/resources/Profile_"
//				+ ConfigPropertyReader.getProperty("tier");
		FirefoxProfile profile;
//		String autoAuthPath = "src/test/resources/AddOn/autoauth-2.1-fx+fn.xpi";
//		File firefoxProfile = new File(firefoxProfilePath);// path of firefox

//		profile = new FirefoxProfile(firefoxProfile);
//		File extension = new File(autoAuthPath);// path of AutoAuth
//		try {
//			profile.addExtension(extension);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//System.setProperty("webdriver.firefox.binary",
          //      "C:"+File.separator+"Program Files (x86)"+File.separator+"Mozilla Firefox"+File.separator+"firefox.exe");
//        FirefoxBinary binary=new FirefoxBinary();
//        binary.setEnvironmentProperty("binary", "C:"+File.separator+"Program Files (x86)"+File.separator+"Mozilla Firefox"+File.separator+"firefox.exe");
		profile = new FirefoxProfile();
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
		//return new FirefoxDriver(ffBinary,profile);
		return new FirefoxDriver(profile);
	}
}
