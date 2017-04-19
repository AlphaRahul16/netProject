package com.qait.MAPS.tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Test {
	public static void main(String[] args) throws IOException {
		File dir = new File(".");
		String loc = "C:\\Users\\hitasheesil\\Downloads\\" + "decision_template.csv";
		System.out.println("loc"+loc);
		FileWriter fstream = new FileWriter(loc, true);
		BufferedWriter out = new BufferedWriter(fstream);
		
		out.write("2345230");
		//out.newLine();
		out.write(",Accept");
		out.newLine();
		// close buffer writer
		out.close();
	}
}