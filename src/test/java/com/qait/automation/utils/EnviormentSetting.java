package com.qait.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

@SuppressWarnings("rawtypes")
public class EnviormentSetting {
	public static HashMap<String,String> settings;

	public EnviormentSetting(String fileLocation, String sheetName){
		HSSFWorkbook workbook;
        try{
        	FileInputStream fis = null;
            fis = new FileInputStream(fileLocation);
            workbook = new HSSFWorkbook(fis);
		    HSSFSheet sheet = workbook.getSheet(sheetName);
            Iterator rows = sheet.rowIterator();
            settings = new HashMap<String,String>();
            /* Iterates thru the rows one by one*/
            String key = null;
            String value = null;
            while (rows.hasNext()) {
                HSSFRow row = (HSSFRow) rows.next();
                Iterator cells = row.cellIterator();
                /*Moves ahead with column */
                boolean flag = true;
                while (cells.hasNext()) {
                    HSSFCell cell = (HSSFCell) cells.next();
                    if(flag){
                    	key = cell.toString();
                    	flag=false;
                    }else{
                    	value = cell.toString();
                    }
                }
                settings.put(key, value);
            }
            fis.close();
        }catch(IOException e){e.printStackTrace();}
	}
}
