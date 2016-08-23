/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qait.automation.utils;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class XlsReader {

	static String downloadFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
			+ File.separator + "resources" + File.separator + "Downloads" + File.separator;

	public XlsReader(String fileLocation, String sheetName) {
		FileInputStream file = null;
		try {
			file = new FileInputStream(new File(fileLocation + ".xlsx"));
			// Get the workbook instance for XLS file
			@SuppressWarnings("resource")
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			// Get first sheet from the workbook
			HSSFSheet sheet = workbook.getSheetAt(0);
			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();
			HSSFRow header = sheet.getRow(3);// Header Name

			System.out.println("Number of Column::" + header.getPhysicalNumberOfCells());

			for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
				System.out.println("  " + header.getCell(i));
			}

			System.out.println(header.getCell(0));

			/*
			 * while (rowIterator.hasNext()) { Row row = rowIterator.next(); //
			 * For each row, iterate through each columns Iterator<Cell>
			 * cellIterator = row.cellIterator();// cell iterator int i = 0;
			 */
			// }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String... a) {
		XlsReader xls = new XlsReader(downloadFilePath + "Workflow", "Statuses");
	}

	static public HashMap<String, String> addValuesInTheMap(String sheetName, int NumRow) {
		FileInputStream file1 = null;
		List<String> key = new ArrayList<String>();
		List<String> value = new ArrayList<String>();
		HashMap<String, String> dataList = new HashMap<String, String>();
		try {
			file1 = new FileInputStream(new File("src/test/resources/TestDataLibrary/Member Transfer.xls"));
			@SuppressWarnings("resource")
			HSSFWorkbook workbook = new HSSFWorkbook(file1);
			HSSFSheet sheet = workbook.getSheetAt(0);
			HSSFRow header = sheet.getRow(2);// Header Name
			System.out.println("Number of Column::" + header.getPhysicalNumberOfCells());

			for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
				System.out.println("  " + header.getCell(i));
				header.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
				if (header.getCell(i).getStringCellValue() == null) {
					key.add("No data");
				} else if (header.getCell(i).getCellType() == Cell.CELL_TYPE_NUMERIC) {
					key.add(header.getCell(i).getNumericCellValue() + "");
				} else if (header.getCell(i).getCellType() == Cell.CELL_TYPE_STRING) {
					key.add(header.getCell(i).getStringCellValue());
				} else if (header.getCell(i).getCellType() == Cell.CELL_TYPE_FORMULA) {
					// System.out.println("Formula is " +
					// header.getCell(i).getCellFormula());
					switch (header.getCell(i).getCachedFormulaResultType()) {
					case Cell.CELL_TYPE_NUMERIC:
						// System.out.println("Last evaluated as: " +
						// header.getCell(i).getNumericCellValue());
						key.add(header.getCell(i).getNumericCellValue() + "");
						break;
					case Cell.CELL_TYPE_STRING:

						key.add(header.getCell(i).getStringCellValue());
						break;
					}
				}
			}
			System.out.println("===========Value List=====================");
			header = sheet.getRow(NumRow);
			for (int j = 0; j < header.getPhysicalNumberOfCells(); j++) {
				System.out.println("  " + header.getCell(j));
				header.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
				if (header.getCell(j).getStringCellValue() == null) {
					value.add("No data");
				} else if (header.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC) {
					value.add(header.getCell(j).getNumericCellValue() + "");
				} else if (header.getCell(j).getCellType() == Cell.CELL_TYPE_STRING) {
					value.add(header.getCell(j).getStringCellValue());
				} else if (header.getCell(j).getCellType() == Cell.CELL_TYPE_FORMULA) {
					System.out.println("Formula is " + header.getCell(j).getCellFormula());
					switch (header.getCell(j).getCachedFormulaResultType()) {
					case Cell.CELL_TYPE_NUMERIC:
						System.out.println("Last evaluated as: " + header.getCell(j).getNumericCellValue());
						value.add(header.getCell(j).getNumericCellValue() + "");
						break;
					case Cell.CELL_TYPE_STRING:
						System.out.println("Last evaluated as \"" + header.getCell(j).getRichStringCellValue() + "\"");
						value.add(header.getCell(j).getStringCellValue());
						break;
					}
				}

			}

			int k = 0;
			System.out.println("Size of Key List::" + key.size());
			System.out.println("Size of value List::" + value.size());

			for (String val : value) {
				dataList.put(key.get(k), val);
				k++;
			}

			System.out.println("===========================Final Data Map=========================");

			for (Map.Entry x : dataList.entrySet()) {
				System.out.println(x.getKey() + " ==> " + x.getValue());
			}

		} catch (Exception e) {

		}
		return dataList;

	}

	static public HashMap<String, String> getValuesFromDataSheet(String sheetName) {
		/* Map<String, String> dataValues = new HashMap<String, String>(); */
		FileInputStream file1 = null;
		List<String> key1 = new ArrayList<String>();
		List<String> value = new ArrayList<String>();
		HashMap<Integer, String> hm = new HashMap<Integer, String>();

		HashMap<String, String> dataList = new HashMap<String, String>();

		int flag = 0;
		try {
			file1 = new FileInputStream(new File("src/test/resources/TestDataLibrary/Member Transfer.xls"));
			// Get the workbook instance for XLS file
			@SuppressWarnings("resource")
			HSSFWorkbook workbook = new HSSFWorkbook(file1);
			// Get first sheet from the workbook
			HSSFSheet sheet = workbook.getSheetAt(0);
			// Iterate through each rows from first sheet
			/* Iterator<Row> rowIterator = sheet.iterator(); */
			HSSFRow header = sheet.getRow(2);// Header Name

			System.out.println("Number of Column::" + header.getPhysicalNumberOfCells());

			for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
				System.out.println("  " + header.getCell(i));
				// key1.add(header.getCell(i).getStringCellValue());
				if (header.getCell(i).getStringCellValue().equalsIgnoreCase("Execute")) {
					flag = i;
				}
				header.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
				if (header.getCell(i).getStringCellValue() == null) {
					value.add("No data");
				} else if (header.getCell(i).getCellType() == Cell.CELL_TYPE_NUMERIC) {
					key1.add(header.getCell(i).getNumericCellValue() + "");
				} else if (header.getCell(i).getCellType() == Cell.CELL_TYPE_STRING) {
					key1.add(header.getCell(i).getStringCellValue());
				} else if (header.getCell(i).getCellType() == Cell.CELL_TYPE_FORMULA) {
					// System.out.println("Formula is " +
					// header.getCell(i).getCellFormula());
					switch (header.getCell(i).getCachedFormulaResultType()) {
					case Cell.CELL_TYPE_NUMERIC:
						// System.out.println("Last evaluated as: " +
						// header.getCell(i).getNumericCellValue());
						key1.add(header.getCell(i).getNumericCellValue() + "");
						break;
					case Cell.CELL_TYPE_STRING:

						key1.add(header.getCell(i).getStringCellValue());
						break;
					}
				}
			}

			System.out.println("flag::" + flag);

			int type;
			int rowCount = sheet.getPhysicalNumberOfRows();

			System.out.println("Number of rowcount: " + rowCount);

			try {
				for (int i = 3; i <= rowCount; i++) {

					Row row = sheet.getRow(i);
					if (row.getCell(flag).getStringCellValue().trim().equalsIgnoreCase("y")) {
						hm.put(i, row.getCell(flag).getStringCellValue().trim());
					}

				}
			} catch (NullPointerException e) {
				System.out.println("Caught Null Pointer");
			}

			System.out.println("Number of Column::" + header.getPhysicalNumberOfCells());

			for (Map.Entry m : hm.entrySet()) {
				/* System.out.println(m.getKey()+" "+m.getValue()); */
				header = sheet.getRow((int) m.getKey());
				for (int j = 0; j < header.getPhysicalNumberOfCells(); j++) {
					header.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
					if (header.getCell(j).getStringCellValue() == null) {
						value.add("No data");
					} else if (header.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC) {
						value.add(header.getCell(j).getNumericCellValue() + "");
					} else if (header.getCell(j).getCellType() == Cell.CELL_TYPE_STRING) {
						value.add(header.getCell(j).getStringCellValue());
					} else if (header.getCell(j).getCellType() == Cell.CELL_TYPE_FORMULA) {
						System.out.println("Formula is " + header.getCell(j).getCellFormula());
						switch (header.getCell(j).getCachedFormulaResultType()) {
						case Cell.CELL_TYPE_NUMERIC:
							System.out.println("Last evaluated as: " + header.getCell(j).getNumericCellValue());
							value.add(header.getCell(j).getNumericCellValue() + "");
							break;
						case Cell.CELL_TYPE_STRING:
							System.out.println(
									"Last evaluated as \"" + header.getCell(j).getRichStringCellValue() + "\"");
							value.add(header.getCell(j).getStringCellValue());
							break;
						}
					}
				}
				break;

			}

			int k = 0;
			System.out.println("Size of Key List::" + key1.size());
			System.out.println("Size of value List::" + value.size());

			for (String val : value) {
				dataList.put(key1.get(k), val);
				k++;
			}

			System.out.println("===========================Final Data Map=========================");

			for (Map.Entry x : dataList.entrySet()) {
				System.out.println(x.getKey() + " ==> " + x.getValue());
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				file1.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return dataList;
	}

	static public List<String> getValuesFromFieldDefinitionsSheet(String sheetName, String columnName) {
		List<String> key = new ArrayList<String>();
		FileInputStream file = null;
		int flag = 0;
		try {
			file = new FileInputStream(new File("src/test/resources/TestDataLibrary/Member Transfer.xls"));
			@SuppressWarnings("resource")
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheet(sheetName);
			HSSFRow header = sheet.getRow(2);
			for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
				if (header.getCell(i).getStringCellValue().equalsIgnoreCase(columnName)) {
					flag = i;
					break;
				}
			}
			int rowCount = sheet.getPhysicalNumberOfRows();
			for (int i = 3; i <= rowCount + 1; i++) {
				Row row = sheet.getRow(i);
				row.getCell(flag).setCellType(Cell.CELL_TYPE_STRING);
				if (row.getCell(flag).getStringCellValue().trim() != null) {
					key.add(row.getCell(flag).getStringCellValue().trim());
				}
			}
			for (String data : key) {
				System.out.print(" " + data);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return key;
	}

	public static List<Integer> getListOfRowsNumberToBeExecuted(String string) {
		FileInputStream file1 = null;
		List<Integer> rowNumList = new ArrayList<Integer>();
		int flag = 0;
		try {
			file1 = new FileInputStream(new File("src/test/resources/TestDataLibrary/Member Transfer.xls"));
			@SuppressWarnings("resource")
			HSSFWorkbook workbook = new HSSFWorkbook(file1);
			HSSFSheet sheet = workbook.getSheetAt(0);
			HSSFRow header = sheet.getRow(2);
			System.out.println("Number of Column::" + header.getPhysicalNumberOfCells());
			for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
				System.out.println("  " + header.getCell(i));
				if (header.getCell(i).getStringCellValue().equalsIgnoreCase("Execute")) {
					flag = i;
					break;
				}

			}

			System.out.println("Flag::" + flag);
			int rowCount = sheet.getPhysicalNumberOfRows();

			System.out.println("Number of rowcount: " + rowCount);

			try {
				for (int i = 3; i <= rowCount; i++) {
					Row row = sheet.getRow(i);
					if (row.getCell(flag).getStringCellValue().trim().equalsIgnoreCase("y")) {
						// hm.put(i,
						// row.getCell(flag).getStringCellValue().trim());
						rowNumList.add(i);
					}

				}
			} catch (NullPointerException e) {
				System.out.println("Caught Null Pointer");
			}

		} catch (Exception e) {

		}

		for (Integer integer : rowNumList) {
			System.out.println("Row Num::" + integer);
		}

		return rowNumList;
	}

	public static List<Integer> getNoOfRowsToBeExecuted(String sheetname) {

		FileInputStream file1 = null;
		List<Integer> rowNumList = new ArrayList<Integer>();
		int flag = 0;
		YamlReader.setYamlFilePath();
		try {
			file1 = new FileInputStream(new File(getYamlValue("excel-data-file.path_"+sheetname)));
			@SuppressWarnings("resource")
			HSSFWorkbook workbook = new HSSFWorkbook(file1);
			HSSFSheet sheet = workbook.getSheetAt(0);
			HSSFRow header = sheet.getRow(0);
			System.out.println("Number of Column ::" + header.getPhysicalNumberOfCells());
			for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
				System.out.println("  " + header.getCell(i));
				if (header.getCell(i).getStringCellValue().equalsIgnoreCase("Execute")) {
					flag = i;
					break;
				}
			}
			System.out.println("Flag::" + flag);
			int rowCount = sheet.getPhysicalNumberOfRows();
			System.out.println("Number of rowcount: " + rowCount);
			try {
				for (int i = 1; i <= rowCount; i++) {
					Row row = sheet.getRow(i);
					if (row.getCell(flag).getStringCellValue().trim().equalsIgnoreCase("y")) {
						// hm.put(i,
						// row.getCell(flag).getStringCellValue().trim());
						rowNumList.add(i);
					}

				}
			} catch (NullPointerException e) {
				System.out.println("Caught Null Pointer");
			}

		} catch (Exception e) {

		}

		for (Integer integer : rowNumList) {
			System.out.println("Row Num::" + integer);
		}

		return rowNumList;
	}

	public static List<String> getExcelHeaderData(HSSFRow header) {
		List<String> key = new ArrayList<String>();
		for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
			System.out.println("Excel Header data: ");
			System.out.println("  " + header.getCell(i));
			// header.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
			if (header.getCell(i).getStringCellValue() == null) {
				key.add("No data");
			} else if (header.getCell(i).getCellType() == Cell.CELL_TYPE_NUMERIC) {
				key.add(header.getCell(i).getNumericCellValue() + "");
			} else if (header.getCell(i).getCellType() == Cell.CELL_TYPE_STRING) {
				key.add(header.getCell(i).getStringCellValue());
			} else if (header.getCell(i).getCellType() == Cell.CELL_TYPE_FORMULA) {
				// System.out.println("Formula is " +
				// header.getCell(i).getCellFormula());
				switch (header.getCell(i).getCachedFormulaResultType()) {
				case Cell.CELL_TYPE_NUMERIC:
					// System.out.println("Last evaluated as: " +
					// header.getCell(i).getNumericCellValue());
					key.add(header.getCell(i).getNumericCellValue() + "");
					break;
				case Cell.CELL_TYPE_STRING:

					key.add(header.getCell(i).getStringCellValue());
					break;
				}
			}
		}
		return key;
	}

	public static List<String> getrowSpecificData(int NumRow, HSSFRow header, HSSFSheet sheet) {
		List<String> value = new ArrayList<String>();
		System.out.println("===========Value List=====================");
		header = sheet.getRow(NumRow);
		for (int j = 0; j < header.getPhysicalNumberOfCells(); j++) {
			System.out.println("  " + header.getCell(j));
			header.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
			if (header.getCell(j).getStringCellValue() == null) {
				value.add("No data");
			} else if (header.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC) {
				value.add(header.getCell(j).getNumericCellValue() + "");
			} else if (header.getCell(j).getCellType() == Cell.CELL_TYPE_STRING) {
				value.add(header.getCell(j).getStringCellValue());
			} else if (header.getCell(j).getCellType() == Cell.CELL_TYPE_FORMULA) {
				System.out.println("Formula is " + header.getCell(j).getCellFormula());
				switch (header.getCell(j).getCachedFormulaResultType()) {
				case Cell.CELL_TYPE_NUMERIC:
					System.out.println("Last evaluated as: " + header.getCell(j).getNumericCellValue());
					value.add(header.getCell(j).getNumericCellValue() + "");
					break;
				case Cell.CELL_TYPE_STRING:
					System.out.println("Last evaluated as \"" + header.getCell(j).getRichStringCellValue() + "\"");
					value.add(header.getCell(j).getStringCellValue());
					break;
				}
			}
		}
		return value;
	}

	public static HashMap<String, String> addValuesInTheMapForExcel(String sheetName, int NumRow) {
		FileInputStream file1 = null;
		List<String> key = new ArrayList<String>();
		List<String> value = new ArrayList<String>();
		HashMap<String, String> dataList = new HashMap<String, String>();
		YamlReader.setYamlFilePath();
		try {
			file1 = new FileInputStream(new File(getYamlValue("excel-data-file.path_" + sheetName)));
			@SuppressWarnings("resource")
			HSSFWorkbook workbook = new HSSFWorkbook(file1);
			HSSFSheet sheet = workbook.getSheetAt(0);
			HSSFRow header = sheet.getRow(0);// Header Name
			System.out.println("Number of Column::" + header.getPhysicalNumberOfCells());
			key = getExcelHeaderData(header);
			value = getrowSpecificData(NumRow, header, sheet);
			int k = 0;
			System.out.println("Size of Key List::" + key.size());
			System.out.println("Size of value List::" + value.size());

			for (String val : value) {
				dataList.put(key.get(k), val.trim());
				k++;
			}
			System.out.println("===========================Final Data Map=========================");
			for (Map.Entry x : dataList.entrySet()) {
				System.out.println(x.getKey() + " ==> " + x.getValue());
			}

		} catch (Exception e) {

		}
		return dataList;

	}

}
