package com.qait.automation;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;
import static com.qait.automation.utils.YamlReader.getYamlValue;
import static com.qait.automation.utils.YamlReader.setYamlFilePath;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;

import com.qait.automation.utils.ConfigPropertyReader;
import com.qait.automation.utils.TakeScreenshot;
import com.qait.keywords.ASMErrorPage;
import com.qait.keywords.ASM_AACTPage;
import com.qait.keywords.ASM_CCEDPage;
import com.qait.keywords.ASM_DonatePage;
import com.qait.keywords.ASM_EGiftPage;
import com.qait.keywords.ASM_FellowNominatePage;
import com.qait.keywords.ASM_GivingGreenPage;
import com.qait.keywords.ASM_MGMPage;
import com.qait.keywords.ASM_MeetingPage;
import com.qait.keywords.ASM_NCWPage;
import com.qait.keywords.ASM_NominatePage;
import com.qait.keywords.ASM_OMRPage;
import com.qait.keywords.ASM_PUBSPage;
import com.qait.keywords.ASM_StorePage;
import com.qait.keywords.ASM_emailPage;
import com.qait.keywords.ASM_memberNumberLookupPage;
import com.qait.keywords.AddMemeber_IWEB;
import com.qait.keywords.BenefitsPage;
import com.qait.keywords.CheckoutPage;
import com.qait.keywords.ConfirmationPage;
import com.qait.keywords.ContactInformationPage;
import com.qait.keywords.EducationAndEmploymentPage;
import com.qait.keywords.FundProfilePage;
import com.qait.keywords.HomePageActions;
import com.qait.keywords.HomePageActions_IWEB;
import com.qait.keywords.IndividualsPageActions_IWEB;
import com.qait.keywords.InvoicePageActions_IWEB;
import com.qait.keywords.MemberNumberLookupPage;
import com.qait.keywords.MemberShipRenewalPage;
import com.qait.keywords.MembershipPageActions_IWEB;
import com.qait.keywords.SubscriptionPage;

public class TestSessionInitiator {

	protected static WebDriver driver;
	private final WebDriverFactory wdfactory;
	String browser;
	String seleniumserver;
	String seleniumserverhost;
	String appbaseurl;
	String applicationpath;
	String chromedriverpath;
	String datafileloc = "";
	static int timeout;
	Map<String, Object> chromeOptions = null;
	DesiredCapabilities capabilities;

	/**
	 * Initiating the page objects
	 */
	public HomePageActions homePage;
	public ContactInformationPage ContactInfoPage;
	public EducationAndEmploymentPage EduAndEmpPage;
	public BenefitsPage BenefitsPage;
	public CheckoutPage checkoutPage;
	public ConfirmationPage confirmationPage;
	public HomePageActions_IWEB homePageIWEB;
	public IndividualsPageActions_IWEB individualsPage;
	public ASMErrorPage asmErrorPage;
	public ASM_CCEDPage asm_CCEDPage;
	public InvoicePageActions_IWEB invoicePage;
	public ASM_StorePage asm_storePage;
	public ASM_AACTPage asm_aactPage;
	public MembershipPageActions_IWEB memberShipPage;
	public ASM_emailPage asm_emailPage;
	public ASM_FellowNominatePage asm_FellowNomiate;
	public ASM_DonatePage asm_Donate;
	public ASM_GivingGreenPage asm_givingGreen;
	public ASM_OMRPage asm_OMR;
	public ASM_MGMPage asm_MGM;
	public ASM_MeetingPage asm_Meeting;
	public ASM_memberNumberLookupPage asm_MemberNumberLookup;
	public ASM_NCWPage asm_NCWPage;
	public ASM_PUBSPage asm_PUBSPage;
	public ASM_NominatePage asm_NominatePage;
	public ASM_EGiftPage asm_EGiftPage;
	public SubscriptionPage subscriptionPage;
	public MemberShipRenewalPage membershipRenewalPage;
	public AddMemeber_IWEB addMember;
	public FundProfilePage fundpofilePage;
	public MemberNumberLookupPage memNumLookupPage;

	public TakeScreenshot takescreenshot;

	public WebDriver getDriver() {
		return this.driver;
	}

	private void _initPage() {
		ContactInfoPage = new ContactInformationPage(driver);
		homePage = new HomePageActions(driver);
		EduAndEmpPage = new EducationAndEmploymentPage(driver);
		BenefitsPage = new BenefitsPage(driver);
		checkoutPage = new CheckoutPage(driver);
		confirmationPage = new ConfirmationPage(driver);
		homePageIWEB = new HomePageActions_IWEB(driver);
		individualsPage = new IndividualsPageActions_IWEB(driver);
		asmErrorPage = new ASMErrorPage(driver);
		asm_CCEDPage = new ASM_CCEDPage(driver);
		invoicePage = new InvoicePageActions_IWEB(driver);
		asm_storePage = new ASM_StorePage(driver);
		asm_aactPage = new ASM_AACTPage(driver);
		memberShipPage = new MembershipPageActions_IWEB(driver);
		asm_emailPage = new ASM_emailPage(driver);
		asm_FellowNomiate = new ASM_FellowNominatePage(driver);
		asm_Donate = new ASM_DonatePage(driver);
		asm_givingGreen = new ASM_GivingGreenPage(driver);
		asm_OMR = new ASM_OMRPage(driver);
		asm_Meeting = new ASM_MeetingPage(driver);
		asm_MemberNumberLookup = new ASM_memberNumberLookupPage(driver);
		asm_MGM = new ASM_MGMPage(driver);
		asm_NCWPage = new ASM_NCWPage(driver);
		asm_PUBSPage = new ASM_PUBSPage(driver);
		asm_NominatePage = new ASM_NominatePage(driver);
		asm_EGiftPage = new ASM_EGiftPage(driver);
		subscriptionPage = new SubscriptionPage(driver);
		membershipRenewalPage = new MemberShipRenewalPage(driver);
		addMember = new AddMemeber_IWEB(driver);
		fundpofilePage = new FundProfilePage(driver);
		memNumLookupPage = new MemberNumberLookupPage(driver);
	}

	/**
	 * Page object Initiation done
	 */

	public TestSessionInitiator(String testname) {
		wdfactory = new WebDriverFactory();
		testInitiator(testname);
	}

	private void testInitiator(String testname) {
		setYamlFilePath();
		_configureBrowser();
		_initPage();
		takescreenshot = new TakeScreenshot(testname, this.driver);
	}

	private void _configureBrowser() {
		driver = wdfactory.getDriver(_getSessionConfig());
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(getProperty("timeout")), TimeUnit.SECONDS);
	}

	private Map<String, String> _getSessionConfig() {
		String[] configKeys = { "tier", "browser", "seleniumserver", "seleniumserverhost", "timeout", "driverpath" };
		Map<String, String> config = new HashMap<String, String>();
		for (String string : configKeys) {
try
{
			if(System.getProperty(string).isEmpty())
			{
			config.put(string, getProperty("./Config.properties", string));
			}
			else
			{
			config.put(string, System.getProperty(string));
			}
}
		
	
		
catch(NullPointerException e)
{
	config.put(string, getProperty("./Config.properties", string));
}
		}
		return config;
	}
	

	public void launchApplication() {
		launchApplication(getYamlValue("baseurl"));
	}

	public void launchApplication(String baseurl) {
		try {
			Reporter.log("The test browser is :- " + _getSessionConfig().get("browser") + "\n", true);

			deleteAllCookies();
			driver.get(baseurl);
			Reporter.log("\nThe application url is :- " + baseurl, true);
			if ((baseurl.equalsIgnoreCase("https://stag-12iweb/NFStage4/iweb/"))
					&& (ConfigPropertyReader.getProperty("browser").equalsIgnoreCase("IE")
							|| ConfigPropertyReader.getProperty("browser").equalsIgnoreCase("ie")
							|| ConfigPropertyReader.getProperty("browser").equalsIgnoreCase("internetexplorer"))) {
				try {
					Thread.sleep(8000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			if (!baseurl.equalsIgnoreCase("https://iwebtest.acs.org/NFStage3/iweb")) {
				handleSSLCertificateCondition(baseurl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openUrl(String url) {
		driver.navigate().to(url);
		// driver.get(url);
	}

	public void closeBrowserSession() {
		try {
			Runtime.getRuntime().exec("taskkill /F /IM plugin-container.exe");
		} catch (IOException e1) {
			driver.quit();
		}
		
	}

	public void deleteAllCookies() {
		driver.manage().deleteAllCookies();
	}

	public void closeBrowserWindow() {
		driver.close();
	}

	public void handleSSLCertificateCondition(String baseurl) {
		if (driver.getTitle().contains("Certificate Error")) {
			driver.get("javascript:document.getElementById('overridelink').click();");
			System.out.println("Step : handle SSL certificate condition\n");
			if (baseurl.equalsIgnoreCase("https://stag-12iweb/NFStage4/iweb/")
					&& ConfigPropertyReader.getProperty("browser").equalsIgnoreCase("IE")
					|| ConfigPropertyReader.getProperty("browser").equalsIgnoreCase("ie")
					|| ConfigPropertyReader.getProperty("browser").equalsIgnoreCase("internetexplorer")) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				Alert alert = driver.switchTo().alert();
				alert.accept();
			}
		}

		if (baseurl.equalsIgnoreCase("https://stag-12iweb/NFStage4/iweb/")
				&& ConfigPropertyReader.getProperty("browser").equalsIgnoreCase("IE")
				|| ConfigPropertyReader.getProperty("browser").equalsIgnoreCase("ie")
				|| ConfigPropertyReader.getProperty("browser").equalsIgnoreCase("internetexplorer")) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		} else {
		}

	}

	public static boolean closeAllOtherWindows(String openWindowHandle) {
		Set<String> allWindowHandles = driver.getWindowHandles();
		for (String currentWindowHandle : allWindowHandles) {
			if (!currentWindowHandle.equals(openWindowHandle)) {
				driver.switchTo().window(currentWindowHandle);
				driver.close();
			}
		}

		driver.switchTo().window(openWindowHandle);
		if (driver.getWindowHandles().size() == 1)
			return true;
		else
			return false;
	}

	public void navigateToURL(String baseURL) {

		driver.navigate().to(baseURL);
		Reporter.log("\nThe application url is :- " + baseURL, true);
	}

	public void navigateToIWEBUrlOnNewBrowserTab(String baseURL) {
		if (_getSessionConfig().get("browser").equalsIgnoreCase("firefox")
				|| _getSessionConfig().get("browser").equalsIgnoreCase("ie")) {
			driver.manage().deleteAllCookies();
			openUrl(baseURL);
		} else if (_getSessionConfig().get("browser").equalsIgnoreCase("chrome")) {
			Robot robot;
			try {
				robot = new Robot();
				robot.delay(2000);
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_T);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.keyRelease(KeyEvent.VK_T);
				// String base = driver.getWindowHandle();
				//
				// Set<String> set = driver.getWindowHandles();
				for (String s : driver.getWindowHandles()) {
					driver.switchTo().window(s);
				}
				// set.remove(base);
				// assert set.size() == 1;
				// driver.switchTo().window((String) set.toArray()[0]);
				driver.navigate().to(baseURL);
				Reporter.log("\nThe application url is :- " + baseURL, true);
			} catch (AWTException e) {
				e.printStackTrace();
			}

		}

		// else
		// if(_getSessionConfig().get("browser").equalsIgnoreCase("chrome"))
		// {
		// Robot robot;
		// try {
		// System.out.println("Open URL 4");
		// robot = new Robot();
		// robot.delay(2000);
		// robot.keyPress(KeyEvent.VK_CONTROL);
		// robot.keyPress(KeyEvent.VK_T);
		// robot.keyRelease(KeyEvent.VK_CONTROL);
		// robot.keyRelease(KeyEvent.VK_T);
		// String base = driver.getWindowHandle();
		// Set<String> set = driver.getWindowHandles();
		// Assert.assertTrue(closeAllOtherWindows(base));
		// // set.remove(base);
		// // assert set.size() == 1;
		// // driver.switchTo().window((String) set.toArray()[0]);
		// driver.get(baseURL);
		// Reporter.log("\nThe application url is :- " + baseURL, true);
		// }
		// catch (AWTException e) {
		// System.out.println("Open URL 5");
		// driver.get(baseURL);
		// e.printStackTrace();
		// }

		// }
	}
}
