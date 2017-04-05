package com.qait.automation.getpageobjects;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.ConfigPropertyReader;

/**
 * This class reads the PageObjectRepository text files. Uses buffer reader.
 * 
 * @author prashantshukla
 * 
 */
public class ObjectFileReader {

	static String tier;
	static String commonPageObjects = "CommonPageObjects/";
	static String filepath = "src/test/resources/";
	static FileReader commonspecFile;

	public static String[] getELementFromFile(String pageName, String elementName) {
		setTier();
		try {
			return setSpecFiles(pageName, elementName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String[] setSpecFiles(String pageName, String elementName) throws Exception {
		// FileReader particularspecFile = null;
		String pageObjectRepositoryPath;
		try {
			if (TestSessionInitiator._getSessionConfig().get("tier").equalsIgnoreCase("maps")) {
				pageObjectRepositoryPath = "PageObjectRepository_Maps/";
				filepath = "src/test/resources/" + pageObjectRepositoryPath;
				commonspecFile = new FileReader(filepath + pageName + ".spec");
			} else {
				pageObjectRepositoryPath = "PageObjectRepository/";
				filepath = "src/test/resources/" + pageObjectRepositoryPath;
//				System.out.println("***********commonspecFile" + filepath + commonPageObjects + pageName + ".spec");
				commonspecFile = new FileReader(filepath + commonPageObjects + pageName + ".spec");

				// particularspecFile = new FileReader(filepath + tier +
				// pageName + ".spec");
			}

			return getElement(commonspecFile, elementName);
		} catch (NullPointerException e) {
//			System.out.println("**************particularspecFile" + filepath + tier + pageName + ".spec");
			FileReader particularspecFile = new FileReader(filepath + tier + pageName + ".spec");
			return getElement(particularspecFile, elementName);
		}

	}

	public static String getPageTitleFromFile(String pageName) {
		setTier();
		BufferedReader br = null;
		String returnElement = "";
		try {
			br = new BufferedReader(new FileReader(filepath + tier + pageName + ".spec"));
			String line = br.readLine();

			while (line != null && !line.startsWith("========")) {
				String titleId = line.split(":", 3)[0];
				if (titleId.equalsIgnoreCase("pagetitle") || titleId.equalsIgnoreCase("title")
						|| titleId.equalsIgnoreCase("page title")) {
					returnElement = line;
					break;
				}
				line = br.readLine();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println(returnElement);
		return returnElement.split(":", 2)[1].trim();
	}

	private static String[] getElement(FileReader specFile, String elementName) throws Exception {
		ArrayList<String> elementLines = getSpecSection(specFile);
		for (String elementLine : elementLines) {
			if (elementLine.startsWith(elementName)) {
				return elementLine.split(" ", 3);
			}
		}
		throw new NullPointerException();
	}

	private static ArrayList<String> getSpecSection(FileReader specfile) {
		String readBuff = null;
		ArrayList<String> elementLines = new ArrayList<String>();

		try {
			BufferedReader buff = new BufferedReader(specfile);
			try {
				boolean flag = false;
				readBuff = buff.readLine();
				while ((readBuff = buff.readLine()) != null) {
					if (readBuff.startsWith("========")) {
						flag = !flag;
					}
					if (flag) {
						elementLines.add(readBuff.trim().replaceAll("[ \t]+", " "));
					}
					if (!elementLines.isEmpty() && !flag) {
						break;
					}
				}
			} finally {
				buff.close();
				if (elementLines.get(0).startsWith("========")) {
					elementLines.remove(0);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Spec File not found at location :- " + filepath);
		} catch (IOException e) {
			System.out.println("exceptional case");
		}
		return elementLines;
	}

	private static void setTier() {

		try {
			if (System.getProperty("tier").contains("defaultTier") || System.getProperty("tier").isEmpty())
				tier = Tiers.valueOf(getProperty("Config.properties", "tier")).toString();
			else {
				tier = System.getProperty("tier");
			}
		} catch (NullPointerException e) {
			tier = Tiers.valueOf(getProperty("Config.properties", "tier")).toString();
		}
		switch (Tiers.valueOf(tier)) {
		case production:
		case PROD:
		case PRODUCTION:
		case Production:
		case prod:
			tier = "PROD/";
			break;
		case STAGE3:
		case Stage3:
		case stage3:
			tier = "STAGE3/";
			break;
		case STAGE2:
		case Stage2:
		case stage2:
			tier = "STAGE2/";
			break;
		case STAGE7:
		case Stage7:
		case stage7:
			tier = "STAGE7/";
			break;
		case STAGE1:
		case Stage1:
		case stage1:
			tier = "STAGE1/";
			break;
		case STAGE4:
		case Stage4:
		case stage4:
			tier = "STAGE4/";
			break;
		case STAGE5:
		case Stage5:
		case stage5:
			tier = "STAGE5/";
			break;
		case Dev:
		case DEV:
		case dev:
			tier = "Dev/";
			break;
		case Dev4:
		case DEV4:
		case dev4:
			tier = "Dev4/";
			break;
		case Dev7:
		case DEV7:
		case dev7:
			tier = "DEV7/";
			break;
		case Dev8:
		case DEV8:
		case dev8:
			tier = "DEV8/";
			break;
		case Dev9:
		case DEV9:
		case dev9:
			tier = "DEV9/";
			break;
		case Dev3:
		case DEV3:
		case dev3:
			tier = "DEV3/";
			break;
		case MAPS:
		case maps:
		case Maps:
			tier = "MAPS/";
			break;
		case STAGE8:
		case Stage8:
		case stage8:
			tier = "STAGE8/";
			break;
		}
	}
}
