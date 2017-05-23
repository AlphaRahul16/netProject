package com.qait.MAPS.tests;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.qait.automation.utils.YamlReader;

import com.qait.automation.utils.DataProvider;

public class MyTest {

	public static void main(String... s) {
//		List<String> dataOfFile = new ArrayList<String>();
//		dataOfFile.add(",");
//		dataOfFile.add("session_title");
//		dataOfFile.add(",,Symposium,Poster,,,,,,,,,,,,,,,,,");
//		YamlReader.getYamlValues("Symposium_FileData");
//		getDataForImportedFile(YamlReader.getYamlValues("Symposium_FileData"));
		String csvFile = "C:\\Users\\hitasheesil\\Downloads\\symposias.csv";
		String data= DataProvider.csvReaderRowSpecific(csvFile, "No", "1");
		System.out.println("data**********"+data);
		//DataProvider.writeDataInAlreadyExistingCSVFile(downloadedFilePath, dataOfFile);
	}

	private static void getDataForImportedFile(Map<String, Object> map) {
		Set keys = map.keySet();
		for (Iterator i = keys.iterator(); i.hasNext();) {
			String key = (String) i.next();
			String value = (String) map.get(key);
			System.err.println(key + " = " + value);
		}

	}
	

}
