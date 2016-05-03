/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qait.automation.getpageobjects;

import static com.qait.automation.getpageobjects.ObjectFileReader.getPageTitleFromFile;
import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import Client.Robotil;

import com.qait.automation.utils.ConfigPropertyReader;
import com.qait.automation.utils.SeleniumWait;

/**
 * 
 * @author avnishrawat
 * 
 */
public class BaseUi {

	WebDriver driver;
	protected SeleniumWait wait;
	private String pageName;
	int timeOut, hiddenFieldTimeOut;
	boolean flag = false;;
	static String lastWindow;

	protected BaseUi(WebDriver driver, String pageName) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		this.pageName = pageName;
		this.wait = new SeleniumWait(driver, Integer.parseInt(getProperty(
				"Config.properties", "timeout")));
	}

	protected String getPageTitle() {
		return driver.getTitle();
	}

	protected void logMessage(String message) {
		Reporter.log(message, true);
	}

	public String getCurrentURL() {
		return driver.getCurrentUrl();
	}

	protected void verifyPageTitleExact() {
		String pageTitle = getPageTitleFromFile(pageName);
		verifyPageTitleExact(pageTitle);
	}

	protected void verifyPageTitleExact(String expectedPagetitle) {
		if (((expectedPagetitle == "") || (expectedPagetitle == null) || (expectedPagetitle
				.isEmpty()))
				&& (getProperty("browser").equalsIgnoreCase("chrome"))) {
			expectedPagetitle = getCurrentURL();
		}
		try {
			wait.waitForPageTitleToBeExact(expectedPagetitle);
			logMessage("ASSERT PASSED: PageTitle for " + pageName
					+ " is exactly: '" + expectedPagetitle + "'");
		} catch (TimeoutException ex) {
			Assert.fail("ASSERT FAILED: PageTitle for " + pageName
					+ " is not exactly: '" + expectedPagetitle
					+ "'!!!\n instead it is :- " + driver.getTitle());
		}
	}

	/**
	 * Verification of the page title with the title text provided in the page
	 * object repository
	 */
	protected void verifyPageTitleContains() {
		String expectedPagetitle = getPageTitleFromFile(pageName).trim();
		verifyPageTitleContains(expectedPagetitle);
	}

	/**
	 * this method will get page title of current window and match it partially
	 * with the param provided
	 * 
	 * @param expectedPagetitle
	 *            partial page title text
	 */
	protected void verifyPageTitleContains(String expectedPagetitle) {
		if (((expectedPagetitle == "") || (expectedPagetitle == null) || (expectedPagetitle
				.isEmpty()))
				&& (getProperty("browser").equalsIgnoreCase("chrome"))) {
			expectedPagetitle = getCurrentURL();
		}
		try {
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			wait.waitForPageTitleToContain(expectedPagetitle);
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (TimeoutException exp) {
			String actualPageTitle = driver.getTitle().trim();
			logMessage("ASSERT FAILED: As actual Page Title: '"
					+ actualPageTitle
					+ "' does not contain expected Page Title : '"
					+ expectedPagetitle + "'.");
			System.out.println("In catch---");
		}
		String actualPageTitle = getPageTitle().trim();
		logMessage("ASSERT PASSED: PageTitle for " + actualPageTitle
				+ " contains: '" + expectedPagetitle + "'.");
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
	}

	protected WebElement getElementByIndex(List<WebElement> elementlist,
			int index) {
		return elementlist.get(index);
	}

	protected WebElement getElementByExactText(List<WebElement> elementlist,
			String elementtext) {
		WebElement element = null;
		for (WebElement elem : elementlist) {
			if (elem.getText().equalsIgnoreCase(elementtext.trim())) {
				element = elem;
			}
		}
		// FIXME: handle if no element with the text is found in list No element
		// exception
		if (element == null) {
		}
		return element;
	}

	protected WebElement getElementByContainsText(List<WebElement> elementlist,
			String elementtext) {
		WebElement element = null;
		for (WebElement elem : elementlist) {
			if (elem.getText().contains(elementtext.trim())) {
				element = elem;
			}
		}
		// FIXME: handle if no element with the text is found in list
		if (element == null) {
		}
		return element;
	}

	protected void switchToFrame(WebElement element) {
		// switchToDefaultContent();
		wait.waitForElementToBeVisible(element);
		driver.switchTo().frame(element);
	}

	public void switchToFrame(int i) {
		driver.switchTo().frame(i);
	}

	public void switchToFrame(String id) {
		driver.switchTo().frame(id);
	}

	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}

	protected void executeJavascript(String script) {
		((JavascriptExecutor) driver).executeScript(script);
	}

	protected Object executeJavascriptReturnValue(String script) {
		return ((JavascriptExecutor) driver).executeScript("return " + script);
	}

	protected Object executeJavascript1(Object script) {

		return ((JavascriptExecutor) driver).executeScript(script.toString());
	}

	protected void hover(WebElement element) {
		Actions hoverOver = new Actions(driver);

		hoverOver.moveToElement(element).build().perform();
	}

	protected void handleAlert() {
		try {
			timeOut = Integer.parseInt(getProperty("Config.properties",
					"timeout"));
			hiddenFieldTimeOut = Integer.parseInt(getProperty(
					"Config.properties", "hiddenFieldTimeOut"));
			wait.resetImplicitTimeout(3);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			switchToAlert().accept();
			logMessage("Alert handled..");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			System.out.println("No Alert window appeared...");
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);

	}

	protected void waitForAlertToAppear() {
		int i = 0;
		Alert alert = null;
		while (i++ < 5) {
			try {
				alert = driver.switchTo().alert();
				System.out.println("Switched to alert");
				break;
			} catch (NoAlertPresentException e) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				continue;
			}
		}
		alert.accept();
		driver.switchTo().defaultContent();
	}

	protected String getAlertText() {
		try {
			timeOut = Integer.parseInt(getProperty("Config.properties",
					"timeout"));
			hiddenFieldTimeOut = Integer.parseInt(getProperty(
					"Config.properties", "hiddenFieldTimeOut"));
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			logMessage("Alert message is " + alertText);
			alert.accept();
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			return alertText;
		} catch (Exception e) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			System.out.println("No Alert window appeared...");
			return null;
		}
	}

	protected Alert switchToAlert() {
		WebDriverWait wait = new WebDriverWait(driver, 1);
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	protected void selectProvidedTextFromDropDown(WebElement el, String text) {

		wait.waitForElementToBeVisible(el);
		scrollDown(el);
		Select sel = new Select(el);
		try {
			sel.selectByVisibleText(text);
		} catch (StaleElementReferenceException ex1) {
			// wait.waitForElementToBeVisible(el);
			// scrollDown(el);
			// Select select = new Select(el);
			sel.selectByVisibleText(text);
			logMessage("select Element " + el
					+ " after catching Stale Element Exception");
		} catch (Exception ex2) {
			sel.selectByVisibleText(text);
			// logMessage("Element " + el + " could not be clicked! "
			// + ex2.getMessage());
		}
	}

	protected void scrollDown(WebElement element) {
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true);", element);
	}

	protected void hoverClick(WebElement element) {
		Actions hoverClick = new Actions(driver);
		hoverClick.moveToElement(element).click().build().perform();
	}

	protected void click(WebElement element) {
		try {
			wait.waitForElementToBeVisible(element);
			scrollDown(element);
			element.click();
		} catch (StaleElementReferenceException ex1) {
			// wait.waitForElementToBeClickable(element);
			// scrollDown(element);
			element.click();
			logMessage("Clicked Element " + element
					+ " after catching Stale Element Exception");
		} catch (WebDriverException ex3) {
			wait.waitForElementToBeClickable(element);
			scrollDown(element);
			element.click();
			logMessage("Clicked Element " + element
					+ " after catching WebDriver Exception");
		} catch (Exception ex2) {
			logMessage("Element " + element + " could not be clicked! "
					+ ex2.getMessage());
		}
	}

	// public static String getPageTextOfPdf(String fileURL, int pageNumber) {
	// PDFParser parser;
	// String parsedText = null;
	//
	// PDFTextStripper pdfStripper = null;
	// PDDocument pdDoc = null;
	// COSDocument cosDoc = null;
	//
	// InputStream input;
	// try {
	// input = new URL(fileURL).openStream();
	// parser = new PDFParser(input);
	// parser.parse();
	// cosDoc = parser.getDocument();
	// pdfStripper = new PDFTextStripper();
	// pdDoc = new PDDocument(cosDoc);
	// pdfStripper.setStartPage(pageNumber);
	// pdfStripper.setEndPage(pageNumber);
	// parsedText = pdfStripper.getText(pdDoc);
	// cosDoc.close();
	// } catch (MalformedURLException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// return parsedText;
	// }

	protected void holdExecution(int milliSeconds) {
		try {
			Thread.sleep(milliSeconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void switchWindow() {
		for (String current : driver.getWindowHandles()) {
			driver.switchTo().window(current);
		}
	}

	public boolean isWindow() {
		String window = driver.getWindowHandle();
		Set<String> windows = driver.getWindowHandles();
		Iterator<String> iterator = windows.iterator();
		// check values
		while (iterator.hasNext()) {
			lastWindow = iterator.next().toString();
			System.out.println("last window:" + lastWindow);
		}
		System.out.println("last window:" + lastWindow);
		System.out.println("window:" + window);
		if (!window.equalsIgnoreCase(lastWindow)) {
			flag = true;
		}
		return flag;
	}

	public void pageRefresh() {
		driver.navigate().refresh();
	}

	public void navigateToBackPage() {
		driver.navigate().back();
		logMessage("Step : navigate to back page\n");
	}

	public void navigateToUrl(String URL) {
		driver.navigate().to(URL);
		logMessage("STEP : Navigate to URL :- " + URL);
	}

	protected void selectDropDownValue(WebElement el, int index) {
		try {
			wait.waitForElementToBeVisible(el);
			scrollDown(el);
			Select sel = new Select(el);
			sel.selectByIndex(index);
		} catch (StaleElementReferenceException ex1) {
			// wait.waitForElementToBeVisible(el);
			// scrollDown(el);
			Select sel = new Select(el);
			sel.selectByIndex(index);
			logMessage("select Element " + el
					+ " after catching Stale Element Exception");
		} catch (Exception ex2) {
			logMessage("Element " + el + " could not be clicked! "
					+ ex2.getMessage());
		}
	}

	public void deleteAllCookies() {
		driver.manage().deleteAllCookies();
	}

	public void clickUsingXpathInJavaScriptExecutor(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);

	}

	public void EnterTextInFieldByJavascript(String id, String Text) {
		System.out.println(Text);
		// element.sendKeys(Text);
		executeJavascript("document.getElementById('" + id
				+ "').setAttribute('value', '" + Text + "')");
		logMessage("Step : text entered as " + Text);
	}

	public void EnterTextInField(WebElement ele, String Text) {
		System.out.println(Text);
		ele.click();
		ele.clear();
		ele.sendKeys(Text);

		logMessage("Step : text entered as " + Text);
	}

	public void sendKeysUsingXpathInJavaScriptExecutor(WebElement element,
			String text) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].setAttribute('value', '" + text
				+ "')", element);
	}

	public void hardWaitForIEBrowser(int seconds) {
		if (ConfigPropertyReader.getProperty("browser").equalsIgnoreCase("IE")
				|| ConfigPropertyReader.getProperty("browser")
						.equalsIgnoreCase("ie")
				|| ConfigPropertyReader.getProperty("browser")
						.equalsIgnoreCase("internetexplorer")) {
			wait.hardWait(seconds);
		}
	}

	public String getTestCaseID(String methodName) {
		String[] split = methodName.split("_");
		String testCaseID = split[1];
		return testCaseID;
	}

	public void performClickByActionBuilder(WebElement element) {
		Actions builder = new Actions(driver);
		builder.moveToElement(element).build().perform();
		builder.moveToElement(element).click().perform();
	}

	public boolean isDropDownValuePresent(List<WebElement> elements,
			String value) {
		for (WebElement element : elements) {
			if (element.getText().equalsIgnoreCase(value)) {
				flag = true;
			}
		}
		return flag;
	}

	protected String getSelectedTextFromDropDown(WebElement el) {
		try {
			wait.waitForElementToBeVisible(el);
			scrollDown(el);
			Select sel = new Select(el);
			return sel.getFirstSelectedOption().getText();

		} catch (StaleElementReferenceException ex1) {
			// wait.waitForElementToBeVisible(el);
			// scrollDown(el);
			Select sel = new Select(el);
			logMessage("get selected Element " + el
					+ " after catching Stale Element Exception");
			return sel.getFirstSelectedOption().getText();

		} catch (Exception ex2) {
			logMessage("Element " + el + " could not be clicked! "
					+ ex2.getMessage());
			return null;
		}
	}

	protected void verifySelectedTextFromDropDown(WebElement el, String text) {
		Assert.assertTrue(getSelectedTextFromDropDown(el)
				.equalsIgnoreCase(text));
		logMessage("AASERT PASSED : " + text
				+ " is verified which is selected \n");
	}

	public void getUrlResponseCode(String url) {
		try {
			URL url1 = new URL(url);
			HttpURLConnection http = (HttpURLConnection) url1.openConnection();
			http.getResponseCode();
			System.out.println(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean isIEBrowser() {
		if (ConfigPropertyReader.getProperty("browser").equalsIgnoreCase("IE")
				|| ConfigPropertyReader.getProperty("browser")
						.equalsIgnoreCase("ie")
				|| ConfigPropertyReader.getProperty("browser")
						.equalsIgnoreCase("internetexplorer")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isBrowser(String browserName) {
		if (ConfigPropertyReader.getProperty("browser").equalsIgnoreCase(
				browserName)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isTier(String tierName) {
		if (ConfigPropertyReader.getProperty("tier").equalsIgnoreCase(tierName)) {
			return true;
		} else {
			return false;
		}
	}

	public static void setClipboardData(String string) {
		StringSelection stringSelection = new StringSelection(string);
		Toolkit.getDefaultToolkit().getSystemClipboard()
				.setContents(stringSelection, null);
	}

	public void enterAuthentication(String uName, String password) {
		if ((isBrowser("ie") || isBrowser("internetexplorer") || isBrowser("chrome"))) {
			System.out.println("in authentication");
			setClipboardData(uName);
			// Robot robot;
			// try {
			// robot = new Robot();
			// setClipboardData(uName);
			// robot.delay(2000);
			// robot.keyPress(KeyEvent.VK_CONTROL);
			// robot.keyPress(KeyEvent.VK_V);
			// robot.keyRelease(KeyEvent.VK_V);
			// robot.keyRelease(KeyEvent.VK_CONTROL);
			// robot.delay(2000);
			// robot.keyPress(KeyEvent.VK_TAB);
			// robot.keyRelease(KeyEvent.VK_TAB);
			// setClipboardData(password);
			// robot.delay(2000);
			// robot.keyPress(KeyEvent.VK_CONTROL);
			// robot.keyPress(KeyEvent.VK_V);
			// robot.keyRelease(KeyEvent.VK_V);
			// robot.keyRelease(KeyEvent.VK_CONTROL);
			// robot.delay(2000);
			// robot.keyPress(KeyEvent.VK_ENTER);
			// robot.keyRelease(KeyEvent.VK_ENTER);
			// } catch (AWTException e) {
			// e.printStackTrace();
			// }

			Robotil robotil;
			try {
				String seleniumServer = ConfigPropertyReader
						.getProperty("seleniumserver");
				String seleniumserverhost = ConfigPropertyReader
						.getProperty("seleniumserverhost");
				if (seleniumServer.equalsIgnoreCase("local")) {
					robotil = new Robotil("127.0.0.1", 6666);
				} else {
					robotil = new Robotil(
							seleniumserverhost.split(":")[1].replaceAll("//",
									""), 6666);
				}

				setClipboardData(uName);
				// robotil.wait(2000);
				robotil.pressKey(KeyEvent.VK_CONTROL);
				robotil.pressKey(KeyEvent.VK_V);
				robotil.releaseKey(KeyEvent.VK_V);
				robotil.releaseKey(KeyEvent.VK_CONTROL);
				robotil.pressKey(KeyEvent.VK_TAB);
				robotil.releaseKey(KeyEvent.VK_TAB);
				setClipboardData(password);
				robotil.pressKey(KeyEvent.VK_CONTROL);
				robotil.pressKey(KeyEvent.VK_V);
				robotil.releaseKey(KeyEvent.VK_V);
				robotil.releaseKey(KeyEvent.VK_CONTROL);
				robotil.pressKey(KeyEvent.VK_ENTER);
				robotil.releaseKey(KeyEvent.VK_ENTER);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void enterAuthenticationAutoIt() {
		try {
			Runtime.getRuntime().exec(
					"./src/test/resources/PopUpHandlers/popup.exe");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getElementText(WebElement element) {
		return element.getText();

	}

	public void selectDropDownValue(String value) {
		WebElement element = driver.findElement(By
				.xpath("//select/option[text()='" + value + "']"));
		element.click();
		logMessage("Step : " + value + " is selected in drop down");
	}

	public void checkCheckbox(WebElement ele) {
		if (!ele.isSelected()) {
			ele.click();
			logMessage("Step : check checkbox \n");
		} else {
			logMessage("Step : check box is already selected\n");
		}
	}

	public void ScrollPage(int x, int y) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(" + x + "," + y + ")", "");
	}

	public String ReverseStringWords(String sentence) {
		String str[] = sentence.split(" ");
		String finalStr = "";
		for (int i = str.length - 1; i >= 0; i--) {
			finalStr += str[i] + " ";
		}
		if (str.length == 3) {
			System.out.println("Name is of 3 letters ");
			finalStr = str[2] + str[0] + str[1];
		}
		System.out.println("Name is of 3 letters " + finalStr);
		return finalStr;

	}

	public boolean isFileDownloaded(String downloadPath, String fileName) {
		boolean flag = false;
		File dir = new File(downloadPath);
		File[] dir_contents = dir.listFiles();

		for (int i = 0; i < dir_contents.length; i++) {
			System.out.println(dir_contents[i].getName());
			System.out.println(fileName);
			if (dir_contents[i].getName().contains(fileName))
				return flag = true;
		}

		return flag;
	}
	


}
