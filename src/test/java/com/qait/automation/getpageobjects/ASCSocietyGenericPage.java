package com.qait.automation.getpageobjects;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;
import static com.qait.automation.utils.DataProvider.csvReaderRowSpecific;
import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.Reporter;

import com.qait.automation.utils.DataProvider;
import com.qait.automation.utils.LayoutValidation;
import com.qait.automation.utils.YamlReader;
import com.qait.keywords.MembershipPageActions_IWEB;

public class ASCSocietyGenericPage extends GetPage {

	protected WebDriver webdriver;
	protected String pageName;
	LayoutValidation layouttest;
	static String csvSeparator = getYamlValue("csv-data-file.data-separator");
	protected int timeOut;
	protected int hiddenFieldTimeOut;
	static int numberOfColumns;
	static int count;
	ArrayList<String> listOfCaseIdToExecute = new ArrayList<String>();
	MembershipPageActions_IWEB memPage;
	public static HashMap<String, String> hashMap = new HashMap<String, String>();

	public ASCSocietyGenericPage(WebDriver driver, String pageName) {
		super(driver, pageName);
		this.webdriver = driver;
		this.pageName = pageName;
		layouttest = new LayoutValidation(driver, pageName);

	}

	public void verifyFieldVisibility(String element, String visibility) throws NoSuchElementException {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		if (visibility.equalsIgnoreCase("hide")) {
			try {
				Reporter.log("Waiting for the element: " + element + " to not to show up", true);
				wait.resetImplicitTimeout(0);
				wait.resetExplicitTimeout(hiddenFieldTimeOut);
				wait.waitForPageToLoadCompletely();
				isElementDisplayed(element);
				org.testng.Assert.fail(
						"ASSERT FAILED : " + element + " is found visible even though it is expected to be hidden");
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
			} catch (NoSuchElementException e) {
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
				logMessage("ASSERT PASSED : " + element + " is hidden");
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
				throw new NoSuchElementException("visibility' field is not displayed in Application.");
			}
		} else if (!(visibility.equalsIgnoreCase("N") || visibility.equalsIgnoreCase("Y"))) {
			logMessage("Data is not valid in sheet");
			throw new NoSuchElementException("data is not valid in sheet");
		}
	}

	public void verifyFieldVisibility(String element, String replacementText, String visibility)
			throws NoSuchElementException {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		if (visibility.equalsIgnoreCase("hide")) {
			try {
				Reporter.log("Waiting for the element: " + element + " " + replacementText + " to not to show up\n",
						true);
				wait.resetImplicitTimeout(5);

				wait.resetExplicitTimeout(hiddenFieldTimeOut);
				isElementDisplayed(element, replacementText); // this is
																// expected to
																// throw
				// exception as element is
				// hidden
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
				Assert.fail("ASSERT FAILED: " + element + " " + replacementText
						+ " is found visible even though it is expected to be hidden\n");
			} catch (NoSuchElementException e) {
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
				logMessage("ASSERT PASS : " + element + " " + replacementText + " is hidden\n");
			}
		} else if (visibility.equalsIgnoreCase("show")) {
			try {
				hardWaitForIEBrowser(4);
				wait.resetImplicitTimeout(5);
				wait.resetExplicitTimeout(hiddenFieldTimeOut);
				isElementDisplayed(element, replacementText);
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
			} catch (NoSuchElementException e) {
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
				logMessage("ASSERT FAILED : " + element + " " + replacementText + " is Show\n");
				throw new NoSuchElementException("visibility' field is not displayed in Application.\n");
			}

		} else {
			logMessage("Data is not valid in sheet");
			throw new NoSuchElementException("data is not valid in sheet\n");
		}
	}

	public String getOmaSheetValue(String caseId, String valueFromDataSheet) {
		String csvLine = csvReaderRowSpecific(getYamlValue("csv-data-file.path_OMA"),
				getYamlValue("csv-data-file.has-header"), caseId);
		return DataProvider
				.getSpecificColumnFromCsvLine(csvLine, csvSeparator, DataProvider.getColumnNumber(valueFromDataSheet))
				.trim();
	}

	public String getPriceSheetValue(String caseId, String valueFromDataSheet) {
		String csvLine = csvReaderRowSpecific(getYamlValue("csv-data-file.path_PriceValue"),
				getYamlValue("csv-data-file.has-header"), caseId);
		return DataProvider.getSpecificColumnFromCsvLine(csvLine, csvSeparator,
				DataProvider.getColumnNumberForPriceValue(valueFromDataSheet)).trim();
	}

	public String getAACT_OmaSheetValue(String caseId, String valueFromDataSheet) {
		String csvLine = csvReaderRowSpecific(getYamlValue("csv-data-file.path_AACT_OMA"),
				getYamlValue("csv-data-file.has-header"), caseId);
		return DataProvider.getSpecificColumnFromCsvLine(csvLine, csvSeparator,
				DataProvider.getColumnNumber_AACTOMA(valueFromDataSheet)).trim();
	}

	public String getACS_Store_SheetValue(String caseId, String valueFromDataSheet) {
		String csvLine = csvReaderRowSpecific(getYamlValue("csv-data-file.path_ACS_Store"),
				getYamlValue("csv-data-file.has-header"), caseId);
		return DataProvider.getSpecificColumnFromCsvLine(csvLine, csvSeparator,
				DataProvider.getColumnNumber_ACS_Store(valueFromDataSheet)).trim();
	}

	public String getACS_Giving_SheetValue(String caseId, String valueFromDataSheet) {
		String csvLine = csvReaderRowSpecific(getYamlValue("csv-data-file.path_giving_donate"),
				getYamlValue("csv-data-file.has-header"), caseId);
		return DataProvider.getSpecificColumnFromCsvLine(csvLine, csvSeparator,
				DataProvider.getColumnNumber_ACS_Giving(valueFromDataSheet)).trim();
	}

	public String getCreateMember_SheetValue(String caseId, String valueFromDataSheet) {
		String csvLine = csvReaderRowSpecific(getYamlValue("csv-data-file.path_createMember"),
				getYamlValue("csv-data-file.has-header"), caseId);
		return DataProvider.getSpecificColumnFromCsvLine(csvLine, csvSeparator,
				DataProvider.getColumnNumber_CreateMember(valueFromDataSheet, csvLine)).trim();
	}

	public static HashMap<String, String> addValuesInMap(String sheetName, String caseID) {
		String csvline2;
		YamlReader.setYamlFilePath();
		String sheetPath = DataProvider.getCsvSheetPath(sheetName);
		System.out.println(sheetPath);
		// String csvLine =
		// csvReaderRowSpecific(getYamlValue("csv-data-file.path_" + sheetName),
		// "false",
		// String.valueOf(1));
		String csvLine = csvReaderRowSpecific(sheetPath, "false", String.valueOf(1));
		String csvLine1 = csvReaderRowSpecific(sheetPath, "true", String.valueOf(caseID));
		numberOfColumns = csvLine.split(csvSeparator).length;
		for (int i = 1; i < numberOfColumns; i++) {
			csvline2 = csvLine.split(csvSeparator)[i].trim();
			csvline2 = csvline2.replace("\"", "").trim();
			hashMap.put(csvline2, DataProvider.getSpecificColumnFromCsvLine(csvLine1, csvSeparator, i).trim());

		}

		return hashMap;

	}

	public static HashMap<String, String> map() {
		return hashMap;
	}

	public static void extractAndCompareTextFromPdfFile(String filename, String texttocompare, int totalnumberofpages) {
		String textinpdf, filepath;
		filepath = "./src/test/resources/UploadFiles/" + filename + ".pdf";
		try {
			textinpdf = extractFromPdf(filename, filepath, 1).trim();
			String textarray[] = texttocompare.trim().split(" ");
			for (int i = 0; i < (textarray.length) - 1; i++) {
				System.out.println("textarray[i]= " + textarray[i]);
				System.out.println();
				Assert.assertTrue(textinpdf.trim().contains(textarray[i]));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String extractFromPdf(String filename, String filepath, int totalnumberofpages) throws IOException {
		String parsedText = "";
		PDFTextStripper pdfStripper = null;
		PDDocument pdDoc = null;
		COSDocument cosDoc = null;
		PDFParser parser = null;
		File file = null;
		RandomAccessBufferedFileInputStream raf = null;

		try {
			file = new File(filepath);
			raf = new RandomAccessBufferedFileInputStream(file);
			parser = new PDFParser(raf);
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			pdfStripper.setStartPage(1);
			pdfStripper.setEndPage(2);
			parsedText = pdfStripper.getText(pdDoc);
			System.out.println("Parsed Text::" + parsedText);

		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} finally {
			try {
				if (cosDoc != null)
					cosDoc.close();
				if (pdDoc != null)
					pdDoc.close();
				raf.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		parsedText = parsedText.replaceAll(System.getProperty("line.separator"), "");

		return parsedText;
	}

	public void extractAndCompareTextFromPdfFile(String filename, String texttocompare, int totalnumberofpages,
			String fileFrom) {

		try {
			String textinpdf = extractFromPdf(filename, 1, fileFrom).trim();
			String textarray[] = texttocompare.trim().split(" ");
			for (int i = 0; i < textarray.length; i++) {
				Assert.assertTrue(textinpdf.trim().contains(textarray[i].trim()),
						"ASSERT FAILED: " + texttocompare + " CONTENT IN THE PDF FILE IS NOT MATCHED\n ");
				logMessage("ASSERT PASSED :" + texttocompare + " CONTENT IN THE PDF FILE IS MATCHED \n");
			}

			if (fileFrom == "downloads" || fileFrom == "System") {
				File dir = new File("./src/test/resources/DownloadedFiles/");
				System.out.println("directory" + dir.getName());
				// String []myFiles = dir.list();

				for (File f : dir.listFiles()) {
					System.out.println("files in directory " + f.getName());

					if (f.getName().contains(filename)) {

						FileWriter file = new FileWriter("./src/test/resources/DownloadedFiles/" + f.getName());
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

	public String extractFromPdf(String filename, int totalnumberofpages, String fileFrom) throws IOException {
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
			parser = new PDFParser(new RandomAccessBufferedFileInputStream(file));
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

		memPage = new MembershipPageActions_IWEB(webdriver);

		wait.waitForPageToLoadCompletely();
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("btn_detailsMenuAACT", menuName);
			clickUsingXpathInJavaScriptExecutor(element("btn_detailsMenuAACT", menuName));
			// element("btn_detailsMenuAACT", menuName).click();

			logMessage("STEP : " + menuName + " bar is clicked to expand" + "\n");

			memPage.waitForSpinner();
		} catch (NoSuchElementException | AssertionError | TimeoutException Exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("STEP : Spinner is not present \n");
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
	}

	public void collapseDetailsMenu(String menuName) {
		memPage = new MembershipPageActions_IWEB(driver);

		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(10);
			isElementDisplayed("icon_up", menuName);
			clickUsingXpathInJavaScriptExecutor(element("icon_up", menuName));
			// element("icon_up", menuName).click();
			memPage.waitForSpinner();
			logMessage("STEP : " + menuName + " bar collapse bar clicked\n");
		} catch (NoSuchElementException | AssertionError | TimeoutException Exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("STEP : Spinner is not present \n");
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
	}

	public boolean isElementPresentOrNot(String elem) {
		boolean flag = false;
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(10);
			element(elem).isDisplayed();

			System.out.println("STEP : Element " + element(elem).toString() + " is displayed");

			flag = true;
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (NoSuchElementException | AssertionError | TimeoutException Exp) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("STEP : Element " + elem + " is not present \n");
		}
		return flag;
	}

	public void expandDetailsMenuIfAlreadyExpanded(String menuName) {
		memPage = new MembershipPageActions_IWEB(driver);

		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		hardWaitForIEBrowser(5);
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));

		if (checkIfElementIsThere("icon_up", menuName)) {
			logMessage("STEP : " + menuName + " menu bar is already expanded\n");
		} else {

			try {
				wait.resetImplicitTimeout(2);
				wait.resetExplicitTimeout(hiddenFieldTimeOut);
				isElementDisplayed("btn_detailsMenuAACT", menuName);
				clickUsingXpathInJavaScriptExecutor(element("btn_detailsMenuAACT", menuName));
				// element("btn_detailsMenuAACT", menuName).click();
				logMessage("STEP : " + menuName + " bar is clicked to expand" + "\n");
				memPage.waitForSpinner();
			} catch (NoSuchElementException | AssertionError | TimeoutException Exp) {
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
				logMessage("STEP : Spinner is not present \n");
			}
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}

	}

	public static int generateRandomNumberWithInRange(int MinRange, int MaxRange) {
		int randomNumber = MinRange + (int) (Math.random() * ((MaxRange - MinRange) + 1));
		return randomNumber;
	}

	public void waitForLoaderToDisappear() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(4);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			// handleAlert();
			isElementDisplayed("img_Loader");
			wait.waitForElementToDisappear(element("img_Loader"));
			logMessage("STEP : Wait for Loader to be disappeared \n");

		} catch (NoSuchElementException Exp) {

			logMessage("STEP : Loader is not present \n");
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
	}

	public void waitForProcessBarToDisappear() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(12);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			// handleAlert();
			isElementDisplayed("img_processbar");
			wait.waitForElementToDisappear(element("img_processbar"));
			logMessage("STEP : Wait for process bar to be disappeared \n");

		} catch (Exception Exp) {

			logMessage("STEP : Process bar is not present \n");
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
	}

	public void dragAndDrop(WebElement sourceElement, WebElement destinationElement) {
		Actions action = new Actions(driver);
		action.dragAndDrop(sourceElement, destinationElement).perform();
		action.clickAndHold(sourceElement).moveToElement(destinationElement).release().build().perform();
		
//		action.moveToElement(sourceElement, 0, 0).moveByOffset(0, 100).build().perform();
		//action.dragAndDrop(sourceElement, destinationElement).perform();
//		Actions builder = new Actions(driver);
//
//		Action dragAndDrop = builder.clickAndHold(sourceElement).moveToElement(destinationElement)
//				.release(destinationElement).build();
//		dragAndDrop.perform();
		logMessage("Step: drag from " + sourceElement + " to " + destinationElement + "\n");
	}

	public void waitForLoadingImageToDisappear(String loaderText) {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(7);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("img_loading", loaderText);
			wait.waitForElementToDisappear(element("img_loading", loaderText));
			logMessage("STEP : Wait for Loading image to be disappeared \n");

		} catch (NoSuchElementException Exp) {

			logMessage("STEP : Loading image is not present \n");
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
	}

	public void verifyValidFileIsDownloaded(String filepath) {
		wait.hardWait(6);
		// waitForLoadingImageToDisappear("Generating CSV file... Please wait");

		File sourceFile = new File(filepath);
		Assert.assertTrue(sourceFile.exists(), "ASSERT FAILED: file is not downloaded \n");
		logMessage("ASSERT PASSED: '" + filepath + "' is downloaded \n");

	}
	
	public static String getfilesByUniquePartOFName(String uniqueValue)
	{
		File dir = new File("./src/test/resources/DownloadedFiles");
		File[] foundFiles = dir.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.contains(uniqueValue);

	}});
		String[] filepath = foundFiles[0].toString().split("\\\\");
		String filename = filepath[filepath.length-1];
		System.out.println("name "+filename);
		return filename;
		
		

}
	
	public static String cleanDownloadsDirectory(String uniqueValue)
	{
		File dir = new File("./src/test/resources/DownloadedFiles");
		File[] foundFiles = dir.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.contains(uniqueValue);

	}});
		String[] filepath = foundFiles[0].toString().split("\\\\");
		String filename = filepath[filepath.length-1];
		System.out.println("name "+filename);
		return filename;
		
		

}
}
