package com.qait.automation.getpageobjects;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;
import static com.qait.automation.utils.DataProvider.csvReaderRowSpecific;
import static com.qait.automation.utils.YamlReader.getYamlValue;
import junit.framework.Assert;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.qait.automation.utils.DataProvider;
import com.qait.automation.utils.LayoutValidation;

public class ASCSocietyGenericPage extends GetPage {

	protected WebDriver webdriver;
	String pageName;
	LayoutValidation layouttest;
	String csvSeparator = getYamlValue("csv-data-file.data-separator");
	int timeOut, hiddenFieldTimeOut;

	public ASCSocietyGenericPage(WebDriver driver, String pageName) {
		super(driver, pageName);
		this.webdriver = driver;
		this.pageName = pageName;
		layouttest = new LayoutValidation(driver, pageName);
	}

	public void verifyFieldVisibility(String element, String visibility)
			throws NoSuchElementException {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		if (visibility.equalsIgnoreCase("hide")) {
			try {
				Reporter.log("Waiting for the element: " + element
						+ " to not to show up", true);
				wait.resetImplicitTimeout(0);
				wait.resetExplicitTimeout(hiddenFieldTimeOut);
				isElementDisplayed(element);
				org.testng.Assert
						.fail("ASSERT FAILED: "
								+ element
								+ " is found visible even though it is expected to be hidden");
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
			} catch (NoSuchElementException e) {
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
				logMessage("ASSERT PASSED: " + element + " is hidden");
			}

		} else if (visibility.equalsIgnoreCase("show")) {
			try {
				wait.resetImplicitTimeout(0);
				wait.resetExplicitTimeout(hiddenFieldTimeOut);
				isElementDisplayed(element);
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
			} catch (NoSuchElementException e) {
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
				logMessage("ASSERT FAILED: " + element + " is Show");
				throw new NoSuchElementException(
						"visibility' field is not displayed in Application.");
			}
		} else if (!(visibility.equalsIgnoreCase("N") || visibility
				.equalsIgnoreCase("Y"))) {
			logMessage("data is not valid in sheet");
			throw new NoSuchElementException("data is not valid in sheet");
		}
	}

	public void verifyFieldVisibility(String element, String replacementText,
			String visibility) throws NoSuchElementException {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		if (visibility.equalsIgnoreCase("hide")) {
			try {
				Reporter.log("Waiting for the element: " + element + " "+replacementText
						+ " to not to show up\n", true);
				wait.resetImplicitTimeout(0);
				wait.resetExplicitTimeout(hiddenFieldTimeOut);
				isElementDisplayed(element,replacementText); // this is expected to throw
				// exception as element is
				// hidden
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
				Assert.fail("ASSERT FAILED: "
						+ element + " "+replacementText
						+ " is found visible even though it is expected to be hidden\n");
			} catch (NoSuchElementException e) {
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
				logMessage("ASSERT PASS: " + element + " "+replacementText + " is hidden\n");
			}
		} else if (visibility.equalsIgnoreCase("show")) {
			try {
				wait.resetImplicitTimeout(0);
				wait.resetExplicitTimeout(hiddenFieldTimeOut);
				isElementDisplayed(element,replacementText);
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
			} catch (NoSuchElementException e) {
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
				logMessage("ASSERT FAILED: " + element + " "+replacementText+" is Show\n");
				throw new NoSuchElementException(
						"visibility' field is not displayed in Application.\n");
			}

		} else {
			logMessage("data is not valid in sheet");
			throw new NoSuchElementException("data is not valid in sheet\n");
		}
	}

	public String getOmaSheetValue(String caseId, String valueFromDataSheet) {
		String csvLine = csvReaderRowSpecific(
				getYamlValue("csv-data-file.path_OMA"),
				getYamlValue("csv-data-file.has-header"), caseId);
		return DataProvider.getSpecificColumnFromCsvLine(csvLine, csvSeparator,
				DataProvider.getColumnNumber(valueFromDataSheet)).trim();
	}

	public String getPriceSheetValue(String caseId, String valueFromDataSheet) {
		String csvLine = csvReaderRowSpecific(
				getYamlValue("csv-data-file.path_PriceValue"),
				getYamlValue("csv-data-file.has-header"), caseId);
		return DataProvider.getSpecificColumnFromCsvLine(csvLine, csvSeparator,
				DataProvider.getColumnNumberForPriceValue(valueFromDataSheet))
				.trim();
	}

	public String getAACT_OmaSheetValue(String caseId, String valueFromDataSheet) {
		String csvLine = csvReaderRowSpecific(
				getYamlValue("csv-data-file.path_AACT_OMA"),
				getYamlValue("csv-data-file.has-header"), caseId);
		return DataProvider.getSpecificColumnFromCsvLine(csvLine, csvSeparator,
				DataProvider.getColumnNumber_AACTOMA(valueFromDataSheet))
				.trim();
	}
	public String getACS_Store_SheetValue(String caseId, String valueFromDataSheet) {
		String csvLine = csvReaderRowSpecific(
				getYamlValue("csv-data-file.path_ACS_Store"),
				getYamlValue("csv-data-file.has-header"), caseId);
		return DataProvider.getSpecificColumnFromCsvLine(csvLine, csvSeparator,
				DataProvider.getColumnNumber_ACS_Store(valueFromDataSheet))
				.trim();
	}

}
