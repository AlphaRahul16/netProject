package com.qait.automation.utils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class SeleniumWait {

	WebDriver driver;
	WebDriverWait wait;

	int timeout;

	public SeleniumWait(WebDriver driver, int timeout) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, timeout);
		this.timeout = timeout;
	}

	/**
	 * Returns webElement found by the locator if element is visible
	 * 
	 * @param locator
	 * @return
	 */
	public WebElement getWhenVisible(By locator) {
		WebElement element;
		element = (WebElement) wait.until(ExpectedConditions
				.visibilityOfElementLocated(locator));
		return element;
	}

	public WebElement getWhenClickable(By locator) {
		WebElement element;
		element = (WebElement) wait.until(ExpectedConditions
				.elementToBeClickable(locator));
		return element;
	}

	public boolean waitForPageTitleToBeExact(String expectedPagetitle) {
		return wait.until(ExpectedConditions.titleIs(expectedPagetitle)) != null;
	}

	public boolean waitForPageTitleToContain(String expectedPagetitle) {
		return wait.until(ExpectedConditions.titleContains(expectedPagetitle)) != null;
	}

	public WebElement waitForElementToBeVisible(WebElement element)
			throws NoSuchElementException {
		WebElement webElement = (WebElement) wait.until(ExpectedConditions
				.visibilityOf(element));
		elementHighlight(webElement);
		return webElement;
	}

	public void waitForFrameToBeAvailableAndSwitchToIt(By locator) {
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
	}

	public List<WebElement> waitForElementsToBeVisible(List<WebElement> elements) {
		List<WebElement> webElements = (List<WebElement>) wait
				.until(ExpectedConditions.visibilityOfAllElements(elements));
		elementHighlight(webElements.get(webElements.size() - 1));
		return webElements;
	}

	public boolean waitForElementToBeInVisible(By locator) {
		return wait.until(ExpectedConditions
				.invisibilityOfElementLocated(locator)) != null;
	}

	public WebElement waitForElementToBeClickable(WebElement element) {
		return (WebElement) wait.until(ExpectedConditions
				.elementToBeClickable(element));
	}

	public void clickWhenReady(By locator) {
		WebElement element = (WebElement) wait.until(ExpectedConditions
				.elementToBeClickable(locator));
		element.click();
	}

	public void waitForMsgToastToDisappear() {
		int i = 0;
		resetImplicitTimeout(1);
		try {
			while (driver.findElement(By.className("toast-message"))
					.isDisplayed() && i <= timeout) {
				hardWait(1);
				i++;
			}
		} catch (Exception e) {
		}
		resetImplicitTimeout(timeout);
	}

	public void waitForElementToDisappear(WebElement element) {
		int i = 0;
		resetImplicitTimeout(2);
		try {
			while (element.isDisplayed() && i <= timeout) {
				hardWait(1);
				i++;
			}
		} catch (Exception e) {
			System.out.println("INFO : " + element + " is not appeared\n");
		}
		resetImplicitTimeout(timeout);
	}

	public WebElement getWhenVisible(By locator, int timeout) {
		WebElement element = null;
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		element = wait.until(ExpectedConditions
				.visibilityOfElementLocated(locator));
		return element;
	}

	public void resetImplicitTimeout(int newTimeOut) {
		try {
			driver.manage().timeouts()
					.implicitlyWait(newTimeOut, TimeUnit.SECONDS);
		} catch (Exception e) {
		}
	}

	public void resetExplicitTimeout(int newTimeOut) {
		try {
			this.wait = new WebDriverWait(driver, newTimeOut);
		} catch (Exception e) {
		}
	}

	// TODO Implement Wait for page load for page synchronizations
	public void waitForPageToLoadCompletely() {
		if (ConfigPropertyReader.getProperty("browser").equalsIgnoreCase(
				"internetexplorer")
				|| ConfigPropertyReader.getProperty("browser")
						.equalsIgnoreCase("ie")
				|| ConfigPropertyReader.getProperty("browser")
						.equalsIgnoreCase("IE")) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By
					.xpath("//*")));
		}

	}

	public void hardWait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	public void elementHighlight(WebElement element) {
		if (ConfigPropertyReader.getProperty("mode").equalsIgnoreCase("debug")
				|| ConfigPropertyReader.getProperty("mode").equalsIgnoreCase(
						"Debug")
				|| ConfigPropertyReader.getProperty("mode").equalsIgnoreCase(
						"DEBUG")) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"arguments[0].setAttribute('style', arguments[1]);",
					element, " border: 1px solid red;");
			// js.executeScript(
			// "arguments[0].setAttribute('style', arguments[1]);",
			// element, "");color: red;
		}

	}


}
