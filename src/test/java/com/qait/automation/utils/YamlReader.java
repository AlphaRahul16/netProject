/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qait.automation.utils;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Map;

import org.testng.Reporter;
import org.yaml.snakeyaml.Yaml;

import com.qait.automation.getpageobjects.Tiers;

@SuppressWarnings("unchecked")
public class YamlReader {

	public static String yamlFilePath = "";
	public static String newFilePath = "";
	public static String commonFilePath = "";
	
	public static String setYamlFilePath() {
		commonFilePath = "src/test/resources/testdata/Common_TestData.yml";
		String tier = "";
		try {
			if (System.getProperty("tier").contains("defaultTier")
					|| System.getProperty("tier").isEmpty())
				tier = Tiers.valueOf(getProperty("Config.properties", "tier"))
						.toString();
			else {
				tier = System.getProperty("tier");
			}
		} catch (NullPointerException e) {
			tier = Tiers.valueOf(getProperty("Config.properties", "tier"))
					.toString();

		}

		if (tier.equalsIgnoreCase("dev4")) {
			yamlFilePath = "src/test/resources/testdata/DEV4_TestData.yml";

		} else if (tier.equalsIgnoreCase("dev")) {
			yamlFilePath = "src/test/resources/testdata/DEV_TestData.yml";

		}
		else if (tier.equalsIgnoreCase("STAGE5")
				|| tier.equalsIgnoreCase("Stage5")
				|| tier.equalsIgnoreCase("stage5")) {
			yamlFilePath = "src/test/resources/testdata/STAGE5_TestData.yml";
		}
		else if (tier.equalsIgnoreCase("STAGE4")
				|| tier.equalsIgnoreCase("Stage4")
				|| tier.equalsIgnoreCase("stage4")) {
			yamlFilePath = "src/test/resources/testdata/STAGE4_TestData.yml";
		} else if (tier.equalsIgnoreCase("STAGE3")
				|| tier.equalsIgnoreCase("Stage3")
				|| tier.equalsIgnoreCase("stage3")) {
			yamlFilePath = "src/test/resources/testdata/STAGE3_TestData.yml";
		} else if (tier.equalsIgnoreCase("STAGE2")
				|| tier.equalsIgnoreCase("Stage2")
				|| tier.equalsIgnoreCase("stage2")) {
			yamlFilePath = "src/test/resources/testdata/STAGE2_TestData.yml";
		} else if (tier.equalsIgnoreCase("STAGE1")
				|| tier.equalsIgnoreCase("Stage1")
				|| tier.equalsIgnoreCase("stage1")) {
			yamlFilePath = "src/test/resources/testdata/STAGE1_TestData.yml";
		} else if (tier.equalsIgnoreCase("prod")
				|| tier.equalsIgnoreCase("production")) {
			yamlFilePath = "src/test/resources/testdata/PROD_TestData.yml";
		}else if (tier.equalsIgnoreCase("dev7")
				|| tier.equalsIgnoreCase("Dev7")
				|| tier.equalsIgnoreCase("DEV7")) {
			yamlFilePath = "src/test/resources/testdata/Dev7_TestData.yml";
		}else if (tier.equalsIgnoreCase("dev8")
				|| tier.equalsIgnoreCase("Dev8")
				|| tier.equalsIgnoreCase("DEV8")) {
			yamlFilePath = "src/test/resources/testdata/Dev8_TestData.yml";
		}
		else if (tier.equalsIgnoreCase("dev3")
				|| tier.equalsIgnoreCase("Dev3")
				|| tier.equalsIgnoreCase("DEV3")) {
			yamlFilePath = "src/test/resources/testdata/Dev3_TestData.yml";
		}
		else if (tier.equalsIgnoreCase("dev9")
				|| tier.equalsIgnoreCase("Dev9")
				|| tier.equalsIgnoreCase("DEV9")) {
			yamlFilePath = "src/test/resources/testdata/Dev9_TestData.yml";
		} else if (tier.equalsIgnoreCase("STAGE7")
				|| tier.equalsIgnoreCase("Stage7")
				|| tier.equalsIgnoreCase("stage7")) {
			yamlFilePath = "src/test/resources/testdata/STAGE7_TestData.yml";}
		else if (tier.equalsIgnoreCase("STAGE8")
				|| tier.equalsIgnoreCase("Stage8")
				|| tier.equalsIgnoreCase("stage8")) {
			yamlFilePath = "src/test/resources/testdata/STAGE8_TestData.yml";}
		else if (tier.equalsIgnoreCase("Maps")
				|| tier.equalsIgnoreCase("maps")
				|| tier.equalsIgnoreCase("MAPS")) {
			yamlFilePath = "src/test/resources/testdata/MAPS_TestData.yml";}
		else {
			Reporter.log(
					"YOU HAVE PROVIDED WRONG TIER IN CONFIG!!! using dev test data",
					true);
		}

		return yamlFilePath;
	}

	public static String getYamlValue(String token) {		
		try{
			return getValue(token);	
		} catch (FileNotFoundException ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}

	public static String getData(String token) {
		return getYamlValue(token);
	}

	public static Map<String, Object> getYamlValues(String token){
		
		Reader doc = null;
		Yaml yaml;
		Map<String, Object> object;
			try{
				try {
					doc = new FileReader(commonFilePath);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				yaml = new Yaml();
				object = (Map<String, Object>) yaml.load(doc);
				return parseMap(object, token + ".");
			}catch (NullPointerException e) {
				try {
					doc = new FileReader(yamlFilePath);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				yaml = new Yaml();
				object = (Map<String, Object>) yaml.load(doc);
				return parseMap(object, token + ".");
			}
		/*try {
			doc = new FileReader(yamlFilePath);
		} catch (FileNotFoundException ex) {
			System.out.println("File not valid or missing!!!");
			ex.printStackTrace();
			return null;
		}
		Yaml yaml = new Yaml();
		// TODO: check the type casting of object into the Map and create
		// instance in one place
		Map<String, Object> object = (Map<String, Object>) yaml.load(doc);*/
		//return parseMap(object, token + ".");
	/*	Map<String, Object> object = (Map<String, Object>) yaml.load(doc);
		return parseMap(object, token + ".");*/

	}

	private static String getValue(String token) throws FileNotFoundException {
		Reader doc;
		Yaml yaml;
		Map<String, Object> object;
			try{
				doc = new FileReader(commonFilePath);
				yaml = new Yaml();
				object = (Map<String, Object>) yaml.load(doc);
				return getMapValue(object, token);
		}catch(NullPointerException e){
			doc = new FileReader(yamlFilePath);
			yaml = new Yaml();
			object = (Map<String, Object>) yaml.load(doc);
			return getMapValue(object, token);
		}
	}

	public static String getMapValue(Map<String, Object> object, String token) {
		// TODO: check for proper yaml token string based on presence of '.'
		String[] st = token.split("\\.");
		return parseMap(object, token).get(st[st.length - 1]).toString();
	}

	private static Map<String, Object> parseMap(Map<String, Object> object,
			String token) {
		if (token.contains(".")) {
			String[] st = token.split("\\.");
			object = parseMap((Map<String, Object>) object.get(st[0]),
					token.replace(st[0] + ".", ""));
		}
		return object;
	}

//	public static int generateRandomNumber(int MinRange, int MaxRange) {
//		int randomNumber = MinRange
//				+ (int) (Math.random() * ((MaxRange - MinRange) + 1));
//		return randomNumber;
//	}

}
