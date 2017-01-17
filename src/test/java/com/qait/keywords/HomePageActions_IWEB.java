package com.qait.keywords;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class HomePageActions_IWEB extends ASCSocietyGenericPage {

	WebDriver driver;
	String pagename = "HomePage_IWEB";

	public HomePageActions_IWEB(WebDriver driver) {
		super(driver, "HomePage_IWEB");
		this.driver = driver;
	}

	public void verifyUserIsOnHomePage(String pageTitle) {
		handleAlert();
		hardWaitForIEBrowser(3);
		verifyPageTitleContains(pageTitle);

		logMessage("ASSERT PASSED : Verified that user is on " + this.pagename
				+ "\n");

	}

	public void clickFindForIndividualsSearch() {
		if (isIEBrowser()) {
			wait.waitForPageToLoadCompletely();
			hardWaitForIEBrowser(3);
			clickUsingXpathInJavaScriptExecutor(element("link_findIndividuals"));
			hardWaitForIEBrowser(3);
		} else {
			isElementDisplayed("link_findIndividuals");
			wait.hardWait(1);
			clickUsingXpathInJavaScriptExecutor(element("link_findIndividuals"));
			// element("link_findIndividuals").click();
		}
		logMessage("STEP : Click find button for individual search\n");
	}

	public void verifySelectedTab(String tabName) {
		verifyElementText("tab_selectedTab", tabName);
	}

	public void GoToMemberShipModule() {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(3);
		isElementDisplayed("area_overView");
		// clickOnTab("Modules");
		wait.hardWait(1);
		clickOnModuleTab();
		clickOnMemberShipTab();
		clickOnSideBarTab("Members");
	}

	public void GoToMemberShipSetupProfile() {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(3);
		isElementDisplayed("area_overView");
		wait.hardWait(1);
		clickOnModuleTab();
		clickOnMemberShipTab();

	}

	public void GoToSubscriptionModule() {
		isElementDisplayed("area_overView");
		clickOnModuleTab();
		clickOnSubscriptionTab();
	}

	public void GoToCRMModule() {
		isElementDisplayed("area_overView");
		clickOnModuleTab();
		clickOnCRMTab();
	}

	// public void clickOnTab(String tabName) {
	// isElementDisplayed("btn_tabs", tabName);
	// element("btn_tabs", tabName).click();
	// logMessage("Step : click on tab " + tabName + " \n");
	// }

	public void clickOnSideBarTab(String tabName) {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		hardWaitForIEBrowser(10);
		isElementDisplayed("hd_sideBar", tabName);

		if (isBrowser("chrome")||isBrowser("safari")) {
			element("hd_sideBar", tabName).click();
		} else {
			clickUsingXpathInJavaScriptExecutor(element("hd_sideBar", tabName));
		}
		logMessage("STEP : Click on tab " + tabName + " From Left Panel \n");
	}
	
	public void clickOnSideBarTabACS(String tabName) {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		hardWaitForIEBrowser(10);
		isElementDisplayed("hd_sideBarACS", tabName);

		if (isBrowser("chrome")||isBrowser("safari")) {
			element("hd_sideBarACS", tabName).click();
		} else {
			clickUsingXpathInJavaScriptExecutor(element("hd_sideBarACS", tabName));
		}
		logMessage("Step : Click on tab " + tabName + " From Left Panel \n");
	}
	
	public void clickOnFulfillmentOrdersTab() {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		hardWaitForIEBrowser(10);
		isElementDisplayed("fulfillment_tab");

		if (isBrowser("chrome")||isBrowser("safari")) {
			element("fulfillment_tab").click();
		} else {
			clickUsingXpathInJavaScriptExecutor(element("fulfillment_tab"));
		}
		
		
		logMessage("STEP : Click on Fulfillment Orders tab From Left Panel \n");
		wait.hardWait(2);
		isElementDisplayed("overview_tab");
		if (isBrowser("safari"))
			element("overview_tab").click();
		else
			clickUsingXpathInJavaScriptExecutor(element("overview_tab"));
		logMessage("STEP : Overview Under Fulfillment Orders tab is clicked\n");
	}
	
	public void clickOnLeftMenuTab(String tabName) {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		isElementDisplayed("tab_leftSidebar", tabName);
	    element("tab_leftSidebar", tabName).click();
		logMessage("STEP : Click on tab " + tabName + " in tab_leftSidebar \n");

	}

	public void clickOnMemberShipTab() {
		handleAlert();
		isElementDisplayed("link_memberShip");
		clickUsingXpathInJavaScriptExecutor(element("link_memberShip"));
		logMessage("STEP : MemberShip tab is clicked\n");
	}

	public void clickOnSubscriptionTab() {
		handleAlert();
		isElementDisplayed("link_subscription");
		clickUsingXpathInJavaScriptExecutor(element("link_subscription"));
		logMessage("STEP : subscription tab is clicked\n");
	}

	public void clickOnCRMTab() {
		handleAlert();
		isElementDisplayed("link_CRM");
		clickUsingXpathInJavaScriptExecutor(element("link_CRM"));
		logMessage("STEP : CRM tab is clicked\n");
	}

	public void waitForTabsArea() {
		isElementDisplayed("tab_tabArear");
	}

	public void clickOnModuleTab() {		
	
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("btn_tabs");
		wait.hardWait(1);
		hardWaitForChromeBrowser(3);
		if(isBrowser("safari"))
			element("btn_tabs").click();
		else
		    executeJavascript("document.getElementsByClassName('dropdown-toggle')[3].click()");
		logMessage("STEP :  Module tab is clicked\n");
	}

	public void launchUrl(String url) {
		executeJavascript("window.location.replace('" + url + "','_self')");
	}

	public void clickOnTab(String tabName) {
		hardWaitForIEBrowser(3);
		isElementDisplayed("link_tabsOnModule", tabName);
		if (isIEBrowser()|| isBrowser("chrome")) {
			clickUsingXpathInJavaScriptExecutor(element("link_tabsOnModule",
					tabName));
			logMessage("STEP : " + tabName + " tab is clicked\n");
		} else {
			element("link_tabsOnModule", tabName).click();
			logMessage("STEP : " + tabName + " tab is clicked\n");
		}
	}

	public void clickOnSacrfReportingModule() {
		isElementDisplayed("lnk_ScarfReporting");
		element("lnk_ScarfReporting").click();
		logMessage("STEP : SCARF Reporting tab is clicked\n");
	}

	public void clickOnFindNominationTab() {
		isElementDisplayed("lnk_FindNomination");
		element("lnk_FindNomination").click();
		logMessage("STEP : Find Nomination tab is clicked\n");
	}

	public void clickOnAddIndividual() {
		isElementDisplayed("link_addIndividuals");
		clickUsingXpathInJavaScriptExecutor(element("link_addIndividuals"));
		logMessage("STEP : Add individual link is clicked in link_addIndividuals\n");

	}

	public void clickOnFindTab() {
		isElementDisplayed("lnk_findTab");
		element("lnk_findTab").click();
		logMessage("STEP : Find tab is clicked\n");
	}

	public void clickOnCommitteeSetupPageLink() {
		isElementDisplayed("btn_committeeSetupPage");
		element("btn_committeeSetupPage").click();
		logMessage("[Info] : Clicked on Committee Setup Page link");
		verifyUserIsOnCommitteeSetupPage();
	}

	public void verifyUserIsOnCommitteeSetupPage() {
		isElementDisplayed("txt_committeeSetup");
		logMessage("STEP : User is on Committee Setup Page");
	}
	
	public void enterAuthentication(String uName, String password,String baseUrl) {
		if ((isBrowser("ie") || isBrowser("internetexplorer") || baseUrl.contains("https://dev-eweb12/NFDev7"))) {
			System.out.println("in authentication");
			uName="ACS1\\"+uName;
			setClipboardData(uName);
			Robot robot;
			try {
				robot = new Robot();
				setClipboardData(uName);
				robot.delay(2000);
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.delay(2000);
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
				setClipboardData(password);
				robot.delay(2000);
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.delay(2000);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);
				System.out.println("after authentication");
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}

	}

//	public void skipMethodAccordingToScenario(boolean condition, String methodName) {
//		// TODO Auto-generated method stub
//		if(test.homePageIWEB.map().get("Scenario").trim().equals("1"))
//	    {
//			methodName;
//	    }
//	}
}
