package com.qait.automation.getpageobjects;

import static com.qait.automation.getpageobjects.ObjectFileReader.getELementFromFile;
import static com.qait.automation.utils.ConfigPropertyReader.getProperty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.qait.automation.utils.ConfigPropertyReader;
import com.qait.automation.utils.LayoutValidation;

public class GetPage extends BaseUi {

	protected WebDriver webdriver;
	String pageName;
	LayoutValidation layouttest;
	final String case1 = "";

	public GetPage(WebDriver driver, String pageName) {
		super(driver, pageName);
		this.webdriver = driver;
		this.pageName = pageName;
		layouttest = new LayoutValidation(driver, pageName);
	}

	public void testPageLayout(List<String> tagsToBeTested) {
		layouttest.checklayout(tagsToBeTested);
	}

	public void testPageLayout(String tagToBeTested) {
		testPageLayout(Arrays.asList(tagToBeTested));
	}

	public void testPageLayout() {
		testPageLayout(Arrays.asList(getProperty("./Config.properties",
				"browser")));
	}

	protected boolean checkIfElementIsThere(String eleString) {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));

		boolean flag = false;
		try {
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			if (webdriver.findElement(getLocator(eleString)).isDisplayed()) {
				flag = true;
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
			} else {
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
				flag = false;
			}
		} catch (NoSuchElementException ex) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			flag = false;
		}
		return flag;
	}

	protected boolean checkIfElementIsThere(String eleString,
			String replacementEleString) {
		boolean flag = false;
		wait.resetImplicitTimeout(2);
		wait.resetExplicitTimeout(10);
		try {
			if (webdriver.findElement(
					getLocator(eleString, replacementEleString)).isDisplayed()) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (NoSuchElementException ex) {
			flag = false;
		}
		return flag;
	}
	
	protected boolean checkIfElementIsThere(String eleString,
			String replacementEleString1, String replacementEleString2) {
		boolean flag = false;
		wait.resetImplicitTimeout(2);
		wait.resetExplicitTimeout(10);
		try {
			if (webdriver.findElement(
					getLocator(eleString, replacementEleString1,replacementEleString2)).isDisplayed()) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (NoSuchElementException ex) {
			flag = false;
		}
		return flag;
	}

	// TODO: put this in right place, create dedicated class for frame and
	// window handlers
	protected void switchToNestedFrames(String frameNames) {
		switchToDefaultContent();
		String[] frameIdentifiers = frameNames.split(":");
		for (String frameId : frameIdentifiers) {
			wait.waitForFrameToBeAvailableAndSwitchToIt(getLocator(frameId
					.trim()));
		}
	}

	protected WebElement element(String elementToken)
			throws NoSuchElementException {
		return element(elementToken, "");
	}

	protected WebElement element(String elementToken, String replacement)
			throws NoSuchElementException {
		WebElement elem = null;
		By locator = getLocator(elementToken, replacement);
		try {
			elem = wait.waitForElementToBeVisible(webdriver
					.findElement(locator));
		} catch (TimeoutException excp) {
			throw new NoSuchElementException("Element " + elementToken
					+ " with locator " + locator.toString().substring(2)
					+ " not found on the " + this.pageName + " !!!");
		}
		return elem;
	}

	protected WebElement element(String elementToken, String replacement1,
			String replacement2) throws NoSuchElementException {
		WebElement elem = null;
		By locator = getLocator(elementToken, replacement1, replacement2);
		try {
			elem = wait.waitForElementToBeVisible(webdriver
					.findElement(locator));
		} catch (TimeoutException excp) {
			throw new NoSuchElementException("Element " + elementToken
					+ " with locator " + locator.toString().substring(2)
					+ " not found on the " + this.pageName + " !!!");
		}
		return elem;
	}

	protected WebElement element(String elementToken, String replacement1,
			String replacement2, String replacement3)
			throws NoSuchElementException {
		WebElement elem = null;
		By locator = getLocator(elementToken, replacement1, replacement2,
				replacement3);
		try {
			elem = wait.waitForElementToBeVisible(webdriver
					.findElement(locator));
		} catch (TimeoutException excp) {
			throw new NoSuchElementException("Element " + elementToken
					+ " with locator " + locator.toString().substring(2)
					+ " not found on the " + this.pageName + " !!!");
		}
		return elem;
	}

	protected List<WebElement> elements(String elementToken, String replacement) {
		return wait.waitForElementsToBeVisible(webdriver
				.findElements(getLocator(elementToken, replacement)));
	}
	
	protected List<WebElement> elements(String elementToken, String replacement1, String replacement2) {
		return wait.waitForElementsToBeVisible(webdriver
				.findElements(getLocator(elementToken, replacement1,replacement2)));
	}

	protected List<WebElement> elements(String elementToken) {
		return elements(elementToken, "");
	}

	protected void isStringMatching(String actual, String expected) {
		Assert.assertEquals(actual, expected);
		logMessage("ACTUAL STRING : " + actual);
		logMessage("EXPECTED STRING :" + expected);
		logMessage("String compare Assertion passed.");
	}

	protected boolean isElementDisplayed(String elementName,
			String elementTextReplace) {
		wait.waitForElementToBeVisible(element(elementName, elementTextReplace));
		boolean result = element(elementName, elementTextReplace).isDisplayed();
		assertTrue(result, "ASSERT FAILED : Element '" + elementName
				+ "with text " + elementTextReplace + "' is not displayed.");
		logMessage("ASSERT PASSED : Element " + elementName + " with text "
				+ elementTextReplace + " is displayed.");
		return result;
	}

	protected boolean isElementDisplayed(String elementName,
			String elementTextReplace1, String elementTextReplace2,
			String elementTextReplace3) {
		wait.waitForElementToBeVisible(element(elementName,
				elementTextReplace1, elementTextReplace2, elementTextReplace3));
		boolean result = element(elementName, elementTextReplace1,
				elementTextReplace2, elementTextReplace3).isDisplayed();
		assertTrue(result, "ASSERT FAILED : Element '" + elementName
				+ "with text " + elementTextReplace1 + elementTextReplace2
				+ elementTextReplace3 + "' is not displayed.");
		logMessage("ASSERT PASSED : Element " + elementName + " with text "
				+ elementTextReplace1 + elementTextReplace2
				+ elementTextReplace3 + " is displayed.");
		return result;
	}

	protected boolean isElementDisplayed(String elementName,
			String elementTextReplace1, String elementTextReplace2) {
		wait.waitForElementToBeVisible(element(elementName,
				elementTextReplace1, elementTextReplace2));
		boolean result = element(elementName, elementTextReplace1,
				elementTextReplace2).isDisplayed();
		assertTrue(result, "ASSERT FAILED : Element '" + elementName
				+ "with text " + elementTextReplace1 + elementTextReplace2
				+ "' is not displayed.");
		logMessage("ASSERT PASSED : Element " + elementName + " with text "
				+ elementTextReplace1 + "," + elementTextReplace2
				+ " is displayed.");
		return result;
	}

	protected void verifyElementText(String elementName, String expectedText) {
		wait.waitForElementToBeVisible(element(elementName));
		assertEquals(element(elementName).getText().trim(), expectedText,
				"ASSERT FAILED : Element '" + elementName
						+ "' Text is not as expected: ");
		logMessage("ASSERT PASSED: Element " + elementName
				+ " is visible and Text is " + expectedText);
	}

	protected void verifyElementTextContains(String elementName,
			String expectedText) {
		wait.waitForElementToBeVisible(element(elementName));
		assertThat("ASSERT FAILED : Element '" + elementName
				+ "' Text is not as expected: ",
				element(elementName).getText(), containsString(expectedText));
		logMessage("ASSERT PASSED : Element " + elementName
				+ " is visible and Text is " + expectedText);
	}

	protected boolean isElementDisplayed(String elementName)
			throws NoSuchElementException {
		scriptExecutionController();
		boolean result = wait.waitForElementToBeVisible(element(elementName))
				.isDisplayed();
		assertTrue(result, "ASSERT FAILED : Element '" + elementName
				+ "' is not displayed.");
		logMessage("ASSERT PASSED : Element " + elementName + " is displayed.");
		return result;
	}

	protected boolean isElementEnabled(String elementName, boolean expected) {
		wait.waitForElementToBeVisible(element(elementName));
		boolean result = expected && element(elementName).isEnabled();
		assertTrue(result, "ASSERT FAILED : Element '" + elementName
				+ "' is  ENABLED :- " + !expected);
		logMessage("ASSERT PASSED : Element " + elementName + " is enabled :- "
				+ expected);
		return result;
	}

	protected By getLocator(String elementToken) {
		return getLocator(elementToken, "");
	}

	public void clickAndHold(WebElement element) {
		Actions act = new Actions(driver);
		act.clickAndHold(element).build().perform();
	}

	protected By getLocator(String elementToken, String replacement) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replaceAll("\\$\\{.+\\}", replacement);
		// System.out.println("Locator "+locator[2]);
		return getBy(locator[1].trim(), locator[2].trim());
	}

	protected By getLocator(String elementToken, String replacement1,
			String replacement2) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement1);
		locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement2);
		return getBy(locator[1].trim(), locator[2].trim());
	}

	protected By getLocator(String elementToken, String replacement1,
			String replacement2, String replacement3) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement1);
		locator[2] = locator[2].replaceFirst("\\%\\{.+?\\}", replacement2);
		locator[2] = locator[2].replaceFirst("\\#\\{.+?\\}", replacement3);
		return getBy(locator[1].trim(), locator[2].trim());
	}

	private By getBy(String locatorType, String locatorValue) {
		switch (Locators.valueOf(locatorType)) {
		case id:
			return By.id(locatorValue);
		case xpath:
			return By.xpath(locatorValue);
		case css:
			return By.cssSelector(locatorValue);
		case name:
			return By.name(locatorValue);
		case classname:
			return By.className(locatorValue);
		case linktext:
			return By.linkText(locatorValue);
		default:
			return By.id(locatorValue);
		}
	}

	public String getCompleteStateName(String abbreviatedName) {
		switch (StateAbbreviations.valueOf(abbreviatedName)) {
		case AL:
			return "Alabama";
		case AK:
			return "Alaska";
		case AZ:
			return "Arizona";
		case AR:
			return "Arkansas";
		case CA:
			return "California";
		case CO:
			return "Colorado";
		case CT:
			return "Connecticut";
		case DE:
			return "Delaware";
		case FL:
			return "Florida";
		case GA:
			return "Georgia";
		case HI:
			return "Hawaii";
		case ID:
			return "Idaho";
		case IL:
			return "Illinois";
		case IN:
			return "Indiana";
		case IA:
			return "Iowa";
		case KS:
			return "Kansas";
		case KY:
			return "Kentucky";
		case LA:
			return "Louisiana";
		case ME:
			return "Maine";
		case MD:
			return "Maryland";
		case MA:
			return "Massachusetts";
		case MI:
			return "Michigan";
		case MN:
			return "Minnesota";
		case MS:
			return "Mississippi";
		case MO:
			return "Missouri";
		case MT:
			return "Montana";
		case NE:
			return "Nebraska";
		case NV:
			return "Nevada";
		case NH:
			return "New Hampshire";
		case NJ:
			return "New Jersey";
		case NM:
			return "New Mexico";
		case NY:
			return "New York";
		case NC:
			return "North Carolina";
		case ND:
			return "North Dakota";
		case OH:
			return "Ohio";
		case OK:
			return "Oklahoma";
		case OR:
			return "Oregon";
		case PA:
			return "Pennsylvania";
		case RI:
			return "Rhode Island";
		case SC:
			return "South Carolina";
		case SD:
			return "South Dakota";
		case TN:
			return "Tennessee";
		case TX:
			return "Texas";
		case UT:
			return "Utah";
		case VT:
			return "Vermont";
		case VA:
			return "Virginia";
		case WA:
			return "Washington";
		case WV:
			return "West Virginia";
		case WI:
			return "Wisconsin";
		case WY:
			return "Wyoming";
		default:
			return null;
		}
	}

	public void enterTestMethodNameToSkipInMap(
			Map<String, Boolean> individualMember, boolean isIndividualMember) {
		individualMember.put("Step03_Enter_Member_Details_In_About_You_Page",
				isIndividualMember);
		individualMember
				.put("Step04_Verify_Contact_Info_And_Enter_Payment_At_Checkout_Page",
						isIndividualMember);
		individualMember.put("Step05_Verify_Details_At_Confirmation_Page",
				isIndividualMember);
		individualMember
				.put("Step07_Search_Member_In_IWEB_Application_And_Verify_Member_Details",
						isIndividualMember);
		individualMember
				.put("Step08_Search_Individual_In_IWEB_Application_And_Verify_Details",
						!isIndividualMember);
	}

	public void handleAuthenticationPopup(String username, String password) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.authenticateUsing(new UserAndPassword(username, password));
	}

	public void scriptExecutionController() {
		// if
		// (ConfigPropertyReader.getProperty("mode").equalsIgnoreCase("debug"))
		// {
		// wait.hardWait(1);
		// }
	}

	protected void verifyElementTextContent(String elementName,
			String expectedText) {
		wait.waitForElementToBeVisible(element(elementName));
		assertThat("ASSERT FAILED : Element '" + elementName
				+ "' Text is not as expected: ", element(elementName)
				.getAttribute("textContent"), containsString(expectedText));
		logMessage("ASSERT PASSED : Element " + elementName
				+ " is visible and Text is " + expectedText);
	}

	public void EnterTestMethodNameToSkipInMap_MemberNumber_CCED_NCW(
			Map<String, Boolean> skipTest, String AppUrl) {
		String url = ConfigPropertyReader.getProperty("tier");

		String case1 = "https://ewebtest.acs.org/NF" + url
				+ "/membernumberlookup/";
		String case2 = "https://ewebtest.acs.org/NF" + url + "/ccedlookup";
		String case3 = "https://ewebtest.acs.org/NF" + url + "/ncwlookup";

		if (AppUrl.equalsIgnoreCase(case1)) {
			skipTest.put("Step01_Verify_Email_Address_IWEB_Test", true);
			skipTest.put("Step02_CCED_Lookup_Test", true);
			skipTest.put("Step03_NCW_Lookup_Test", true);
		} else if (AppUrl.equalsIgnoreCase(case2)) {
			skipTest.put("Step00_Member_Number_Lookup_Test", true);
			skipTest.put("Step03_NCW_Lookup_Test", true);
		} else if (AppUrl.equalsIgnoreCase(case3)) {
			skipTest.put("Step00_Member_Number_Lookup_Test", true);
			skipTest.put("Step02_CCED_Lookup_Test", true);
		} else {
			logMessage("Error: Invalid Application URL in DataSheet\n");
		}
	}
	
	public static <T,E>T getMapFirstMatchedKeyByValue(Map<T, E> map,E value)
	{
		for (Entry<T, E> entry : map.entrySet()) {
	        if (Objects.equals(value, entry.getValue())) {
	        	System.out.println(entry.getKey());
	        	System.out.println(value);
	            return entry.getKey();
	        }
	    }
		return null;
}
}
