/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qait.automation.report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.nio.file.Path;
//import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.qait.automation.utils.DateUtil;

/**
 *
 * @author QAIT
 */
public class ReformatTestFile {

	public String replacealltimestamp(String html) {

		List<String> allMatches = new ArrayList<String>();
		Matcher m = Pattern.compile("[0-9]{13}").matcher(html);

		while (m.find()) {
			allMatches.add(m.group());
		}
		for (String entrySet : allMatches) {
			for (int i = 1; i <= 10; i++) {
				html = html.replace("<td rowspan=\"" + i + "\">" + entrySet + "</td>",
						"<td rowspan=\"" + i + "\">" + DateUtil.converttimestamp(entrySet) + "</td>");
			}
		}
		return html;
	}

	public void writeLargerTextFile(String aFileName, String html) throws IOException {
		Path path = Paths.get(aFileName);
		File file = new File(aFileName);
		file.getParentFile().mkdirs();
		file.createNewFile();
		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(aFileName), "ISO-8859-1"))) {
			writer.write(html);

		}
	}

protected	String readLargerTextFile(String aFileName) throws IOException {
		String html = "";
		Path path = Paths.get(aFileName);
		try (Scanner scanner = new Scanner(path, "ISO-8859-1")) {
			while (scanner.hasNextLine()) {
				// process each line in some way
				html = html + scanner.nextLine() + "\n";
			}
		}
		return html;
	}

	public static void createMemberTransferCompleteTestLog(String aFileName, String html,String scenarioID) {
		String filePath = aFileName + File.separator + DateUtil.getCurrentdateInStringWithGivenFormate("dd MMM YYYY")+File.separator +"MT"+scenarioID+".html";
		Path path = Paths.get(filePath);
		File file = new File(filePath);
		file.getParentFile().mkdirs();
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(filePath), "ISO-8859-1"))) {
		
			writer.write(html);

		} catch(Exception e){
			
		}
	}
}
