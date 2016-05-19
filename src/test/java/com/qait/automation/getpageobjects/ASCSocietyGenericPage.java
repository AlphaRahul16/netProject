package com.qait.automation.getpageobjects;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;
import static com.qait.automation.utils.DataProvider.csvReaderRowSpecific;
import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;

import com.qait.automation.utils.DataProvider;
import com.qait.automation.utils.LayoutValidation;
import com.qait.automation.utils.YamlReader;

public class ASCSocietyGenericPage extends GetPage {

	protected WebDriver webdriver;
	String pageName;
	LayoutValidation layouttest;
	String csvSeparator = getYamlValue("csv-data-file.data-separator");
	int timeOut, hiddenFieldTimeOut, numberOfColumns;
	static int count;
	ArrayList<String> listOfCaseIdToExecute = new ArrayList<String>();
	public static HashMap<String, String> hashMap = new HashMap<String, String>();

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
				wait.waitForPageToLoadCompletely();
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
				Reporter.log("Waiting for the element: " + element + " "
						+ replacementText + " to not to show up\n", true);
				wait.resetImplicitTimeout(0);
				wait.resetExplicitTimeout(hiddenFieldTimeOut);
				isElementDisplayed(element, replacementText); // this is
																// expected to
																// throw
				// exception as element is
				// hidden
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
				Assert.fail("ASSERT FAILED: "
						+ element
						+ " "
						+ replacementText
						+ " is found visible even though it is expected to be hidden\n");
			} catch (NoSuchElementException e) {
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
				logMessage("ASSERT PASS: " + element + " " + replacementText
						+ " is hidden\n");
			}
		} else if (visibility.equalsIgnoreCase("show")) {
			try {
				wait.resetImplicitTimeout(0);
				wait.resetExplicitTimeout(hiddenFieldTimeOut);
				isElementDisplayed(element, replacementText);
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
			} catch (NoSuchElementException e) {
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
				logMessage("ASSERT FAILED: " + element + " " + replacementText
						+ " is Show\n");
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

	public String getACS_Store_SheetValue(String caseId,
			String valueFromDataSheet) {
		String csvLine = csvReaderRowSpecific(
				getYamlValue("csv-data-file.path_ACS_Store"),
				getYamlValue("csv-data-file.has-header"), caseId);
		return DataProvider.getSpecificColumnFromCsvLine(csvLine, csvSeparator,
				DataProvider.getColumnNumber_ACS_Store(valueFromDataSheet))
				.trim();
	}

	public String getACS_Giving_SheetValue(String caseId,
			String valueFromDataSheet) {
		String csvLine = csvReaderRowSpecific(
				getYamlValue("csv-data-file.path_giving_donate"),
				getYamlValue("csv-data-file.has-header"), caseId);
		return DataProvider.getSpecificColumnFromCsvLine(csvLine, csvSeparator,
				DataProvider.getColumnNumber_ACS_Giving(valueFromDataSheet))
				.trim();
	}

	public String getCreateMember_SheetValue(String caseId,
			String valueFromDataSheet) {
		String csvLine = csvReaderRowSpecific(
				getYamlValue("csv-data-file.path_createMember"),
				getYamlValue("csv-data-file.has-header"), caseId);
		return DataProvider.getSpecificColumnFromCsvLine(csvLine, csvSeparator,
				DataProvider.getColumnNumber_CreateMember(valueFromDataSheet))
				.trim();
	}

	public HashMap<String, String> addValuesInMap(String sheetName,
			String caseID) {

		YamlReader.setYamlFilePath();
		String csvLine = csvReaderRowSpecific(
				getYamlValue("csv-data-file.path_" + sheetName), "false",
				String.valueOf(1));
		String csvLine1 = csvReaderRowSpecific(
				getYamlValue("csv-data-file.path_" + sheetName), "true",
				String.valueOf(caseID));
		numberOfColumns = csvLine.split(csvSeparator).length;
		for (int i = 1; i < numberOfColumns; i++) {

			hashMap.put(csvLine.split(csvSeparator)[i], DataProvider
					.getSpecificColumnFromCsvLine(csvLine1, csvSeparator, i)
					.trim());
		}

		return hashMap;

	}

	public HashMap<String, String> map() {
		return hashMap;
	}

	public static void extractAndCompareTextFromPdfFile(String filename,
			String texttocompare, int totalnumberofpages) {
		String textinpdf;
		try {
			textinpdf = extractFromPdf(filename, 1).trim();
			String textarray[] = texttocompare.trim().split(" ");
			for (int i = 0; i < (textarray.length) - 1; i++) {
				System.out.println(textinpdf);
				System.out.println(textarray[i]);
				Assert.assertTrue(textinpdf.trim().contains(texttocompare));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String extractFromPdf(String filename, int totalnumberofpages)
			throws IOException {
		String uploadedfilepath = "./src/test/resources/UploadFiles/"
				+ filename + ".pdf";
		String parsedText = null;
		PDFTextStripper pdfStripper = null;
		PDDocument pdDoc = null;
		COSDocument cosDoc = null;
		PDFParser parser = null;
		File file = null;

		try {
			file = new File(uploadedfilepath);
			parser = new PDFParser(
					new RandomAccessBufferedFileInputStream(file));
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			pdfStripper.setStartPage(1);
			pdfStripper.setEndPage(2);
			parsedText = pdfStripper.getText(pdDoc);
			System.out.println(parsedText);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} finally {
			try {
				if (cosDoc != null)
					cosDoc.close();
				if (pdDoc != null)
					pdDoc.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return parsedText;
	}

	public void extractAndCompareTextFromPdfFile(String filename,
			String texttocompare, int totalnumberofpages, String fileFrom) {

		try {
			String textinpdf = extractFromPdf(filename, 1, fileFrom).trim();
			String textarray[] = texttocompare.trim().split(" ");
			for (int i = 0; i < textarray.length; i++) {
				Assert.assertTrue(textinpdf.trim()
						.contains(textarray[i].trim()), "ASSERT FAILED: "
						+ texttocompare
						+ " CONTENT IN THE PDF FILE IS NOT MATCHED\n ");
				logMessage("ASSERT PASSED:" + texttocompare
						+ " CONTENT IN THE PDF FILE IS MATCHED \n");
			}

			if (fileFrom == "downloads" || fileFrom == "System") {
				File dir = new File("./src/test/resources/DownloadedFiles/");
				System.out.println("directory" + dir.getName());
				// String []myFiles = dir.list();

				for (File f : dir.listFiles()) {
					System.out.println("files in directory " + f.getName());

					if (f.getName().contains(filename)) {

						FileWriter file = new FileWriter(
								"./src/test/resources/DownloadedFiles/"
										+ f.getName());
						File ff = new File("D:/" + f.getName());
						file.flush();
						file.close();
						ff.delete();

						break;
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String extractFromPdf(String filename, int totalnumberofpages,
			String fileFrom) throws IOException {
		String filepath = null;
		if (fileFrom == "System" || fileFrom == "uploads") {
			filepath = "./src/test/resources/UploadFiles/" + filename + ".pdf";
		} else if (fileFrom == "WebApplication" || fileFrom == "downloads") {
			System.out.println("In downloads");
			File dir = new File("./src/test/resources/DownloadedFiles");
			System.out.println("directory name " + dir.getName());
			for (File f : dir.listFiles()) {
				System.out.println("File name" + f.getName());
				if (f.getName().startsWith(filename)) {
					filepath = f.toString();
					System.out.println("file path" + filepath.toString());
					break;
				}
			}
		} else {
			return null;
		}
		String parsedText = null;
		PDFTextStripper pdfStripper = null;
		PDDocument pdDoc = null;
		COSDocument cosDoc = null;
		PDFParser parser = null;
		File file = null;

		try {
			file = new File(filepath);
			while (!file.exists() && count < 10) {
				wait.hardWait(2);
				file = new File(filepath);
				count++;
			}
			parser = new PDFParser(
					new RandomAccessBufferedFileInputStream(file));
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			pdfStripper.setStartPage(1);
			pdfStripper.setEndPage(2);
			parsedText = pdfStripper.getText(pdDoc);
			System.out.println(parsedText);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} finally {
			try {
				if (cosDoc != null)
					cosDoc.close();
				if (pdDoc != null)
					pdDoc.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return parsedText;
	}

	public void expandDetailsMenu(String menuName) {
		wait.waitForPageToLoadCompletely();
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("btn_detailsMenuAACT", menuName);
			// clickUsingXpathInJavaScriptExecutor(element("btn_detailsMenuAACT",
			// menuName));
			element("btn_detailsMenuAACT", menuName).click();

			logMessage("STEP : " + menuName + " bar is clicked to expand"
					+ "\n");
			waitForSpinner();
		} catch (NoSuchElementException | AssertionError | TimeoutException Exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("STEP : Spinner is not present \n");
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
	}

	public void waitForSpinner() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		try {
			handleAlert();
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(10);
			isElementDisplayed("img_spinner");
			wait.waitForElementToDisappear(element("img_spinner"));
			logMessage("STEP : Wait for spinner to be disappeared \n");

		} catch (NoSuchElementException | AssertionError | TimeoutException Exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("STEP : Spinner is not present \n");
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
	}

	public void collapseDetailsMenu(String menuName) {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(10);
			isElementDisplayed("icon_up", menuName);
			clickUsingXpathInJavaScriptExecutor(element("icon_up", menuName));
			// element("icon_up", menuName).click();
			waitForSpinner();
			logMessage("STEP : " + menuName + " bar collapse bar clicked\n");
		} catch (NoSuchElementException | AssertionError | TimeoutException Exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("STEP : Spinner is not present \n");
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
	}

}
