package com.qait.automation.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;

public class CSVFileReaderWriter {
	
	public static void _deleteExistingCSVFile(String filePath,String filename) {
		File folder = new File(filePath);
		final File[] files = folder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(final File dir, final String name) {
				System.out.println(name);
				// if(name.matches(filename+"(.*).csv"))
				return name.matches(filename + "(.*).csv");

			}
		});
		for (final File file : files) {
			System.out.println("deleted file " + file);
			if (!file.delete()) {
				System.err.println("Can't remove " + file.getAbsolutePath());
			}
		}
	}
	
	
	public static void writeDataInAlreadyExistingCSVFile(String sheetpath, List<String> dataOfFile) {
		File dir = new File(".");
		// String sheetpath = "C:\\Users\\hitasheesil\\Downloads\\" +
		// "decision_template.csv";
		System.out.println("loc" + sheetpath);
		FileWriter fstream;
		try {
			fstream = new FileWriter(sheetpath, true);
			BufferedWriter out = new BufferedWriter(fstream);
			for(String data: dataOfFile){
				out.write(data);
				
			}	
			out.newLine();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
