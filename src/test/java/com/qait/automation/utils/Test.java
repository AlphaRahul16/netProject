package com.qait.automation.utils;

import java.io.File;
import java.io.FilenameFilter;

public class Test {

	public static void main(String...strings ){
		
			String filename = "report";
			String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
					+ File.separator + "resources" + File.separator + "DownloadedFiles"; 
					//+ File.separator + fileName + ".pdf";

			File folder = new File(filePath);
			final File[] files = folder.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(final File dir, final String name) {
					System.out.println(name);
					// if(name.matches(filename+"(.*).csv"))
					return name.matches(filename + "(.*).pdf");

				}
			});
			for (final File file : files) {
				System.out.println("deleted file " + file);
				if (!file.delete()) {
					System.err.println("Can't remove " + file.getAbsolutePath());
				}
			}
		}
	}
