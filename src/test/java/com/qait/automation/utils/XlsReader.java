/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qait.automation.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

public class XlsReader {

	static String downloadFilePath = System.getProperty("user.dir")
			+ File.separator + "src" + File.separator + "test" + File.separator
			+ "resources" + File.separator + "Downloads" + File.separator;

	public XlsReader(String fileLocation , String sheetName) {
		 FileInputStream file = null;
         try {
            file  = new FileInputStream(new File(fileLocation+".xlsx"));
             // Get the workbook instance for XLS file
             @SuppressWarnings("resource")
             HSSFWorkbook workbook = new HSSFWorkbook(file);
             // Get first sheet from the workbook
             HSSFSheet sheet = workbook.getSheetAt(0);
             // Iterate through each rows from first sheet
             Iterator<Row> rowIterator = sheet.iterator();
             HSSFRow header =  sheet.getRow(0);//Header Name
             System.out.println(header.getCell(0));
             
             /*
             while (rowIterator.hasNext()) {
                 Row row = rowIterator.next();  // For each row, iterate through each columns
                 Iterator<Cell> cellIterator = row.cellIterator();// cell iterator
                  int i = 0;
             */           
             //  }
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }  finally{
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

}
