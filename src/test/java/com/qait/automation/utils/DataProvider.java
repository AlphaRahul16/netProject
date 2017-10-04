package com.qait.automation.utils;

import static com.qait.automation.TestSessionInitiator._getSessionConfig;
import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class DataProvider {
	static String ASM_dataSheet = YamlReader.getYamlValue("ASM_Data_File.path");
	static String ASM_dataSheetSeparator = YamlReader.getYamlValue("ASM_Data_File.data-separator");
	static int count;

	static String csvSeparator = getYamlValue("csv-data-file.data-separator");
	static ArrayList<String> listOfCaseIdToExecute = new ArrayList<>();

	public DataProvider() {
	}

	public static String getSpecificColumnFromCsvLine(String csvLine, String csvSeparator, int columnNumber) {
		String returnStr = ""; // return blank if value / column not present
		try {

			returnStr = csvLine.split(csvSeparator)[columnNumber];
			returnStr = returnStr.replaceAll("\"", "").trim();
		} catch (ArrayIndexOutOfBoundsException ex) {
			// Reporter.log(
			// "Column Number "
			// + columnNumber
			// +
			// " in the data csv file is empty . Please check your test script
			// OR keyword",
			// true);
		}

		return returnStr;
	}

	public static String csvReaderRowSpecific(String csvFile, String hasHeader, String rowNumberExact) {
		BufferedReader br = null;
		ArrayList<String> dataRows = new ArrayList<>();
		String line = "";
		try {
			br = new BufferedReader(new FileReader(csvFile));
			if (hasHeader.equalsIgnoreCase("yes") || hasHeader.equalsIgnoreCase("true")) {
				br.readLine();// in case first line is header in the csv file
			}
			while ((line = br.readLine()) != null) {
				dataRows.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		int rowNumber = Integer.parseInt(rowNumberExact) - 1;
		return dataRows.get(rowNumber);
	}

	public static int getColumnNumber(String columnName) {
		String csvdatafilepath = getYamlValue("csv-data-file.path_OMA");
		String csvSeparator = getYamlValue("csv-data-file.data-separator");
		String firstCSVLine = csvReaderRowSpecific(csvdatafilepath, "false", "1");
		String[] arr = firstCSVLine.split(csvSeparator);
		for (int i = 0; i <= arr.length - 1; i++) {
			if (arr[i].trim().equalsIgnoreCase(columnName)) {
				return i;
			}
		}
		return -1;

	}

	public static int getTotalNumberOfRowsInSheet(String csvFile, String hasHeader) {
		count = 0;
		BufferedReader br = null;

		String line = "";
		try {
			br = new BufferedReader(new FileReader(csvFile));
			if (hasHeader.equalsIgnoreCase("yes") || hasHeader.equalsIgnoreCase("true")) {
				br.readLine();// in case first line is header in the csv file
			}
			while ((line = br.readLine()) != null) {
				count++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return count;
	}

	public static int getColumnNumberForPriceValue(String columnName) {
		String csvdatafilepath = getYamlValue("csv-data-file.path_PriceValue");
		String csvSeparator = getYamlValue("csv-data-file.data-separator");
		String firstCSVLine = csvReaderRowSpecific(csvdatafilepath, "false", "1");
		String[] arr = firstCSVLine.split(csvSeparator);
		for (int i = 0; i <= arr.length - 1; i++) {
			if (arr[i].trim().equalsIgnoreCase(columnName)) {
				return i;
			}
		}
		return -1;

	}

	public static int getColumnNumber_AACTOMA(String columnName) {
		String csvdatafilepath = getYamlValue("csv-data-file.path_AACT_OMA");
		String csvSeparator = getYamlValue("csv-data-file.data-separator");
		String firstCSVLine = csvReaderRowSpecific(csvdatafilepath, "false", "1");
		String[] arr = firstCSVLine.split(csvSeparator);
		for (int i = 0; i <= arr.length - 1; i++) {
			if (arr[i].trim().equalsIgnoreCase(columnName)) {
				return i;
			}
		}
		return -1;

	}

	public static int getColumnNumber_ACS_Store(String columnName) {
		String csvdatafilepath = getYamlValue("csv-data-file.path_ACS_Store");
		String csvSeparator = getYamlValue("csv-data-file.data-separator");
		String firstCSVLine = csvReaderRowSpecific(csvdatafilepath, "false", "1");
		String[] arr = firstCSVLine.split(csvSeparator);
		for (int i = 0; i <= arr.length - 1; i++) {
			if (arr[i].trim().equalsIgnoreCase(columnName)) {
				return i;
			}
		}
		return -1;

	}

	public static int getColumnNumber_ACS_Giving(String columnName) {
		String csvdatafilepath = getYamlValue("csv-data-file.path_giving_donate");
		String csvSeparator = getYamlValue("csv-data-file.data-separator");
		String firstCSVLine = csvReaderRowSpecific(csvdatafilepath, "false", "1");
		String[] arr = firstCSVLine.split(csvSeparator);
		for (int i = 0; i <= arr.length - 1; i++) {
			if (arr[i].trim().equalsIgnoreCase(columnName)) {
				return i;
			}
		}
		return -1;

	}

	public static int getColumnNumber_CreateMember(String columnName, String sheetPath) {
		// String csvdatafilepath =
		// getYamlValue("csv-data-file.path_createMember");
		String csvdatafilepath = sheetPath;
		String csvSeparator = getYamlValue("csv-data-file.data-separator");
		String firstCSVLine = csvReaderRowSpecific(csvdatafilepath, "false", "1");
		String[] arr = firstCSVLine.split(csvSeparator);
		for (int i = 0; i <= arr.length - 1; i++) {
			if (arr[i].replaceAll("\"", "").trim().equalsIgnoreCase(columnName)) {
				return i;
			}
		}
		return -1;

	}

	// Read data against given Row Id and column Name and return it to caller
	public static String getColumnData(String rowId, String columnName) {
		BufferedReader br = null;
		ArrayList<String> dataRows = new ArrayList<String>();
		String line = "";
		String requiredDataRow;
		int columnNumber = -1;
		int rowNumber = -1;
		String columnData = "";
		try {
			br = new BufferedReader(new FileReader(ASM_dataSheet));

			// Get Column Number
			String[] headers = br.readLine().split(ASM_dataSheetSeparator);
			for (int i = 0; i <= headers.length - 1; i++)
				if (headers[i].trim().equalsIgnoreCase(columnName))
					columnNumber = i;

			int rowIndex = 1;
			while ((line = br.readLine()) != null) {
				dataRows.add(line);
				if (line.split(ASM_dataSheetSeparator)[0].equalsIgnoreCase(rowId))
					rowNumber = rowIndex;
				rowIndex++;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		requiredDataRow = dataRows.get(rowNumber - 1);
		try {
			columnData = requiredDataRow.split(ASM_dataSheetSeparator)[columnNumber];
		} catch (ArrayIndexOutOfBoundsException ex) {
		}
		return columnData.replace("\\\"", "\"");
	}

	public static List<String> getcaseIdToExecute(String executeColumnName, String executeColumnValue,
			String caseIdColumnName, String sheetName) {
		int caseCount = 0;
		String sheetPath;
		listOfCaseIdToExecute.removeAll(listOfCaseIdToExecute);
		YamlReader.setYamlFilePath();
		String csvSeparator = getYamlValue("csv-data-file.data-separator");

		sheetPath = getCsvSheetPath(sheetName);
		int totalNumberOfRows = getTotalNumberOfRowsInSheet(sheetPath, "true");
		for (int i = 1; i <= totalNumberOfRows; i++) {
			String csvLine = csvReaderRowSpecific(sheetPath, "true", String.valueOf(i));

			String value = getSpecificColumnFromCsvLine(csvLine, csvSeparator,
					getColumnNumber_CreateMember(executeColumnName, sheetPath));

			if (value.contains(executeColumnValue)) {
				String csvLine1 = csvReaderRowSpecific(sheetPath, "true", String.valueOf(i));
				String value1 = DataProvider.getSpecificColumnFromCsvLine(csvLine1, csvSeparator,
						getColumnNumber_CreateMember(caseIdColumnName, sheetPath));
				listOfCaseIdToExecute.add(value1);
				caseCount++;
			}
		}
		return listOfCaseIdToExecute;
	}

	public static List<String> get(String sheetName) {
		return getcaseIdToExecute("caseID Execute", "Yes", "caseID", sheetName);
	}

	public static List<String> getIndividualLandingPageData() {
		return getcaseIdToExecute("caseID Execute", "Yes", "caseID", "landingPage");
	}

	public static String getRandomSpecificLineFromTextFile(String FileName) {
		YamlReader.setYamlFilePath();
		FileName = getYamlValue(("csv-data-file.path_" + FileName));
		LineIterator it = null;
		int lines = 0;
		String line = null;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(FileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			while (reader.readLine() != null)
				lines++;
			reader.close();
		} catch (IOException e) {
			System.out.println("Execption");
		}

		try {
			it = IOUtils.lineIterator(new BufferedReader(new FileReader(FileName)));
		} catch (FileNotFoundException e1) {

			e1.printStackTrace();
		}

		lines = ASCSocietyGenericPage.generateRandomNumberWithInRange(1, lines);

		for (int lineNumber = 0; it.hasNext(); lineNumber++) {
			line = (String) it.next();

			if (lineNumber == lines) {
				break;
			}
		}

		System.out.println("------random program:" + line);
		return line;
	}

	public static String getCsvSheetPath(String sheetName) {
		String sheetPath;
		if (_getSessionConfig().get("tier").equalsIgnoreCase("dev9")
				|| _getSessionConfig().get("tier").equalsIgnoreCase("stage8")
				|| _getSessionConfig().get("tier").equalsIgnoreCase("stage4")
				||_getSessionConfig().get("tier").equalsIgnoreCase("stage7")
				||_getSessionConfig().get("tier").equalsIgnoreCase("dev4")
				||_getSessionConfig().get("tier").equalsIgnoreCase("stage5")) { //
			sheetPath = getYamlValue("csv-data-file.path_" + sheetName);
			sheetPath = sheetPath.replace("TestDataLibrary", "Payment_Processor_DataLibrary");
		} else
			sheetPath = getYamlValue("csv-data-file.path_" + sheetName);
		return sheetPath;
	}

}
