package com.qait.keywords;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

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
		verifyPageTitleContains(pageTitle);
		logMessage("ASSERT PASSED: verified that user is on " + this.pagename + "\n");

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
		wait.hardWait(2);
		hardWaitForIEBrowser(6);
		isElementDisplayed("hd_sideBar", tabName);

		if (isBrowser("chrome")) {
			element("hd_sideBar", tabName).click();
		} else {

			clickUsingXpathInJavaScriptExecutor(element("hd_sideBar", tabName));
		}
		logMessage("STEP : Click on tab " + tabName + " in hd_sideBar \n");

	}

	public void clickOnMemberShipTab() {
		handleAlert();
		isElementDisplayed("link_memberShip");
		clickUsingXpathInJavaScriptExecutor(element("link_memberShip"));
		logMessage("Step : MemberShip tab is clicked\n");
	}

	public void clickOnSubscriptionTab() {
		handleAlert();
		isElementDisplayed("link_subscription");
		clickUsingXpathInJavaScriptExecutor(element("link_subscription"));
		logMessage("Step : subscription tab is clicked\n");
	}

	public void clickOnCRMTab() {
		handleAlert();
		isElementDisplayed("link_CRM");
		clickUsingXpathInJavaScriptExecutor(element("link_CRM"));
		logMessage("Step : CRM tab is clicked\n");
	}

	public void waitForTabsArea() {
		isElementDisplayed("tab_tabArear");
	}

	public void clickOnModuleTab() {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("btn_tabs");
		wait.hardWait(1);
		hardWaitForChromeBrowser(3);
		
		executeJavascript("document.getElementsByClassName('dropdown-toggle')[3].click()");
		// element("btn_tabs").click();
		logMessage("STEP :  Module tab is clicked\n");
	}

	public void launchUrl(String url) {
		executeJavascript("window.location.replace('" + url + "','_self')");
	}

	public void clickOnTab(String tabName) {

		isElementDisplayed("link_tabsOnModule", tabName);
		element("link_tabsOnModule", tabName).click();
		System.out.println("inside" + System.getProperty("browser"));

		logMessage("STEP : " + tabName + " tab is clicked\n");
	}

	public void clickOnFindNominationTab() {
		isElementDisplayed("lnk_FindNomination");
		element("lnk_FindNomination").click();
		logMessage("STEP : Find Nomination tab is clicked\n");
	}

	public void clickOnAddIndividual() {
		isElementDisplayed("link_addIndividuals");
		clickUsingXpathInJavaScriptExecutor(element("link_addIndividuals"));
		logMessage("Step : add individual link is clicked in link_addIndividuals\n");

	}

	public void clickOnFindTab() {
		isElementDisplayed("lnk_findTab");
		element("lnk_findTab").click();
		logMessage("STEP : Find tab is clicked\n");
	}

	public void clickOnCommitteeSetupPageLink() {
		isElementDisplayed("btn_committeeSetupPage");
		element("btn_committeeSetupPage").click();
		logMessage("Info: Clicked on Committee Setup Page link");
		verifyUserIsOnCommitteeSetupPage();
	}

	public void verifyUserIsOnCommitteeSetupPage() {
		isElementDisplayed("txt_committeeSetup");
		logMessage("Test passed: User is on Committee Setup Page");
	}

	public void enterAuthentication(String uName, String password) {
		if ((isBrowser("ie") || isBrowser("internetexplorer"))) {
			System.out.println("in authentication");
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
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}

	}

}
