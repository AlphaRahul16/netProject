package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.YamlReader;

public class BenefitsPage extends ASCSocietyGenericPage {

	WebDriver driver;
	String pagename = "BenefitsPage";
	ArrayList<String> score_divisionName = new ArrayList<String>();
	ArrayList<String> score_publicationName = new ArrayList<String>();
	Float subtotalTechnical_score = 0.0f;
	Float subtotalPublication_score = 0.0f;
	int timeOut, hiddenFieldTimeOut;

	public BenefitsPage(WebDriver driver) {
		super(driver, "BenefitsPage");
		this.driver = driver;
	}

	public void addACSPublication(String publicationName) {
		hardWaitForIEBrowser(10);
		if (!publicationName.equalsIgnoreCase("")) {
			System.out.println("add acs publication --------------------------------");
			clickOnACSPublication();
			verifyPublicationConditionsOnBenefitsPage("lblDescriptionText",
					YamlReader.getYamlValues("OMA_SmokeChecklistData.conditionsAcsPublications"));
			verifyListOfPublications(map().get("Country"));
			clickOnAddToMembership(publicationName);
			if (isBrowser("ie") || isBrowser("internet explorer")) {
				verifyPrdouctAddition(publicationName);
			}
			String str = getDivision_publicationScore(publicationName);
			score_publicationName.add(str);
			subtotalPublication_score = subtotalPublication_score + Float.parseFloat(score_publicationName.get(0));
			verifyTotalScorePublication(subtotalPublication_score);
			verifyDivision_PublicationAdded(publicationName);
			verifySaveButtonColor(YamlReader.getYamlValue("OMA_SmokeChecklistData.saveButtonColor"),
					YamlReader.getYamlValue("OMA_SmokeChecklistData.saveButtonColorHex"));
			clickSaveButton();
			logMessage("STEP :  Add ACS Publication is added as " + publicationName + "\n");
		} else {
			logMessage("STEP :  Add ACS Publication value is not present in data sheet\n");
		}

	}
	
	public void verifySaveButtonColor(String color,String colorHex){
		isElementDisplayed("btn_saveColor");
		System.out.println("-----color:"+element("btn_saveColor").getCssValue("background-color"));
		Assert.assertEquals(convertRGBToHexaDecimalFromGetCssValue(element("btn_saveColor"), "background-color"), colorHex
				,"ASSERT FAILED: Color of Save button is not "+color+"\n");
		logMessage("ASSERT PASSED: Color of Save button is "+color+"\n");
	}
	
	public String convertRGBToHexaDecimalFromGetCssValue(WebElement element, String attributeName) {
		  String color = element.getCssValue(attributeName);
		  String s1 = color.substring(5);
		  StringTokenizer st = new StringTokenizer(s1);
		  int r = Integer.parseInt(st.nextToken(",").trim());
		  int g = Integer.parseInt(st.nextToken(",").trim());
		  int b = Integer.parseInt(st.nextToken(",").trim());
		  Color c = new Color(r, g, b);
		  String hex = "#" + Integer.toHexString(c.getRGB()).substring(2);
		  System.out.println("-------hexadecimal vale:"+hex);
		  return hex;
	}
	
	public void verifyListOfPublications(String country){
		String userType;
		if(country.equals("UNITED STATES")){
			userType="domestic";
			verifyPublicationsListSize(userType, Integer.parseInt(YamlReader.getYamlValue("OMA_SmokeChecklistData.acsPublicationsDomesticSize")));
			verifyPublicationNames(YamlReader.getYamlValues("OMA_SmokeChecklistData.acsPublicationsDomesticList"), userType);
		}
		else{
			userType="international";
			verifyPublicationsListSize(userType, Integer.parseInt(YamlReader.getYamlValue("OMA_SmokeChecklistData.acsPublicationsInternationalSize")));
			verifyPublicationNames(YamlReader.getYamlValues("OMA_SmokeChecklistData.acsPublicationsInternationalList"), userType);
		}
	}
	
	public void verifyPublicationsListSize(String userType, int listSize){
		isElementDisplayed("txt_publicationTitle");
		Assert.assertTrue(elements("txt_publicationTitle").size()==listSize,"ASSERT PASSED: ACS Publication list size is not verified as "+listSize+" for "+userType+" user\n");
		logMessage("ASSERT PASSED: ACS Publication list size is verified as "+listSize+" for "+userType+" user\n");
	}
	
	public void verifyPublicationNames(Map<String,Object> publicationList,String userType){
		isElementDisplayed("txt_publicationTitle");
		for(int index=0;index<elements("txt_publicationTitle").size();index++){
			System.out.println("--actual: "+elements("txt_publicationTitle").get(index).getText().trim());
			String key="List"+(index+1);
			System.out.println("-----exp: "+publicationList.get(key));
			Assert.assertEquals(elements("txt_publicationTitle").get(index).getText().trim(),
					publicationList.get(key),"ASSERT PASSED: Publication "+publicationList.get(key)+" is not verified for "+userType+" user\n");
			logMessage("ASSERT PASSED: Publication "+publicationList.get(key)+" is verified for "+userType+" user\n");
		}
	}

	public void addACSTechnicalDivision(String divisionName) {
		if (!divisionName.equalsIgnoreCase("")) {
			clickOnACSTechnicalDivision();
			verifyTechnicalDivisionCondition(map().get("Member Type?"), 
					YamlReader.getYamlValue("OMA_SmokeChecklistData.txtTechnicalDivisionCondition1"),"lblDescriptionText");
			clickOnAddToMembership(divisionName);
			if (isBrowser("ie") || isBrowser("internet explorer")) {
				verifyPrdouctAddition(divisionName);
			}
			String str = getDivision_publicationScore(divisionName);
			score_divisionName.add(str);
			subtotalTechnical_score = subtotalTechnical_score + Float.parseFloat(score_divisionName.get(0));
			verifyTotalScoreDivision(subtotalTechnical_score);
			verifyDivision_PublicationAdded(divisionName);
			clickSaveButton();
			logMessage("STEP : ACS Technical Division is added as" + divisionName + " \n");
		} else {
			logMessage("STEP : ACS Technical Division value is not present in data sheet\n");
		}

	}

	public void verifyCENPresent(String caseId) {
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		hardWaitForIEBrowser(20);
		if (map().get("C&EN Status").equalsIgnoreCase("Y")) {
			try {
				wait.resetImplicitTimeout(0);
				wait.resetExplicitTimeout(hiddenFieldTimeOut);
				boolean flag = element("rad_dues_cenPackage", "C&EN Package").isDisplayed();
				if (flag)
					clickUsingXpathInJavaScriptExecutor(element("rad_dues_cenPackage", "C&EN Package"));
				// click(element("rad_dues_cenPackage", "C&EN Package"));
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
			} catch (Exception exp) {

			}

			if (map().get("CENtype").equalsIgnoreCase("print")) {
				// wait.waitForPageToLoadCompletely();
				hardWaitForIEBrowser(20);
				isElementDisplayed("rad_CENType", "Print");
				clickUsingXpathInJavaScriptExecutor(element("rad_CENType", "Print"));
				// click(element("rad_CENType", "Print"));
				logMessage("STEP :  rad_CENType is clicked\n");
			} else if (map().get("CENtype").equalsIgnoreCase("electronic")) {
				isElementDisplayed("rad_CENType", "Email");
				clickUsingXpathInJavaScriptExecutor(element("rad_CENType", "Email"));
				// click(element("rad_CENType", "Email"));
				logMessage("STEP :  rad_CENType is clicked\n");
			} else {
				logMessage("STEP : CENType is invalid in data sheet\n");
				Assert.fail("ASSERT FAIL : CENType in data sheet is not valid\n");
			}
		} else if (map().get("C&EN Status").equalsIgnoreCase("N")) {
			clickUsingXpathInJavaScriptExecutor(element("rad_dues_cenPackage", "Dues Only Package"));
			// click(element("rad_dues_cenPackage", "Dues Only Package"));
			logMessage("STEP :  rad_duesOnlyPackage is clicked\n");
		} else {
			logMessage("STEP :  CEN status value is not valid in data sheet\n");
			Assert.fail("ASSERT FAIL : C&EN Status in data sheet is not valid\n");
		}

	}

	private void clickOnACSPublication() {
		wait.waitForPageToLoadCompletely();
		hardWaitForIEBrowser(25);
		isElementDisplayed("btn_ACSPublication");
		clickUsingXpathInJavaScriptExecutor(element("btn_ACSPublication"));
		// click(element("btn_ACSPublication"));
		logMessage("STEP :  btn_ACSPublication is clicked\n");
	}

	private void clickOnACSTechnicalDivision() {
		isElementDisplayed("btn_ACStechnicalDivision");
		clickUsingXpathInJavaScriptExecutor(element("btn_ACStechnicalDivision"));
		// click(element("btn_ACStechnicalDivision"));
		logMessage("STEP :  btn_ACStechnicalDivision is clicked\n");
	}

	public void clickOnAddToMembership(String divisionName) {
		hardWaitForIEBrowser(20);
		if (isBrowser("ie") || isBrowser("internet explorer")) {
			element("btn_addToMembership", divisionName).click();
			logMessage("STEP :  " + divisionName + " btn_addToMembership is clicked\n");
		} else {
			wait.waitForPageToLoadCompletely();
			wait.hardWait(5);
			isElementDisplayed("btn_addToMembership", divisionName);
			element("btn_addToMembership", divisionName).click();
			logMessage("STEP :  " + divisionName + " btn_addToMembership is clicked\n");
			// clickUsingXpathInJavaScriptExecutor(element("btn_addToMembership",
			// divisionName));
		}
		// click(element("btn_addToMembership", divisionName));

	}

	private String getDivision_publicationScore(String divisionName) {
		String score_division = element("txt_divisionScore", divisionName).getText();
		String division1_score = score_division.replace("$", "");
		return division1_score;
	}

	private void verifyTotalScoreDivision(Float subtotalTechnical_score) {
		Float actualTechnicalScore = Float
				.parseFloat(element("txt_technicalDivisionSubtotal").getText().replace("$", ""));
		Assert.assertEquals(subtotalTechnical_score, actualTechnicalScore);

	}

	private void verifyTotalScorePublication(Float subtotalPublication_score) {
		Float actualTechnicalScore = Float
				.parseFloat(element("txt_technicalDivisionSubtotal").getText().replace("$", ""));
		Assert.assertEquals(subtotalPublication_score, actualTechnicalScore);
	}

	private void clickSaveButton() {
		isElementDisplayed("btn_save");
		// clickUsingXpathInJavaScriptExecutor(element("btn_save"));
		wait.waitForPageToLoadCompletely();
		click(element("btn_save"));
		wait.hardWait(4);
		logMessage("STEP :  btn_save is clicked\n");
	}

	private void verifyDivision_PublicationAdded(String divisionName) {
		isStringMatching("Added", element("txt_added", divisionName).getText());
		logMessage("ASSERT PASSED :  ACS " + divisionName + " added successfully in txt_added\n");
	}

	public void addACSPublicationAndTechnicalDivision(String caseId) {
		wait.hardWait(3);
		verifyTechnicalDivisionFreeDivision(map().get("Member Type?"), YamlReader.getYamlValue("OMA_SmokeChecklistData.txtTechnicalDivisionCondition1"));
		addACSTechnicalDivision(map().get("Technical Division"));
		wait.waitForPageToLoadCompletely();
		System.out.println("before add -=-=-===-=-=-=-=");
		verifyTextForAcsPublications(YamlReader.getYamlValue("OMA_SmokeChecklistData.acsPublicationsHeading"));
		verifyPublicationConditionsOnBenefitsPage("lblPublicationMarket",YamlReader.getYamlValues("OMA_SmokeChecklistData.conditionsAcsPublications"));
		
		addACSPublication(map().get("PublicationName"));

	}
	
	public void verifyPrdouctAddition(String divisionName) {
		int count = 0;
		hardWaitForIEBrowser(5);
		// System.out.println("------added is
		// present:"+checkIfElementIsThere("txt_productAdded",divisionName));
		while (!checkIfElementIsThere("txt_productAdded", divisionName) && (count < 6)) {
			clickOnAddToMembership(divisionName);
			System.out.println("Step: " + divisionName + " is again clicked");
			count++;
		}
	}
	
	public void verifyTechnicalDivisionFreeDivision(String memberType,String expCondition){
		if(memberType.equals("Society Affiliate")){
			Assert.assertFalse(checkIfElementIsThere("txt_freeDivision"),
					"ASSERT FAILED: Free division text should not be available for "+memberType+"\n");
			logMessage("ASSERT PASSED: Free division text is not available for "+memberType+"\n");
		}
		else{
		isElementDisplayed("txt_freeDivision");
		Assert.assertEquals(element("txt_freeDivision").getText().trim(), expCondition);
		logMessage("ASSERT PASSED: "+expCondition+" text is available for "+memberType+"\n");
	  }
	}
	
	public void verifyTechnicalDivisionCondition(String memberType,String expCondition,String sectionName){
		if(!memberType.equals("Society Affiliate")){
			for(int i=1;i<=elements("list_technicalDivisionPoints",sectionName).size();i++){
			if(elements("list_technicalDivisionPoints",sectionName).get(i).getText().trim().equals(YamlReader.getYamlValue("OMA_SmokeChecklistData.txtTechnicalDivisionCondition1"))){
				Assert.assertFalse(true,"ASSERT FAILED: "+YamlReader.getYamlValue("OMA_SmokeChecklistData.txtTechnicalDivisionCondition1")+" should not be displayed on page\n");
			}
		  }	
		}
		else
			verifyConditionsOnBenefitsPage(sectionName,YamlReader.getYamlValue("OMA_SmokeChecklistData.txtTechnicalDivisionCondition2"), 1);
	}
	
	public void verifyConditionsOnBenefitsPage(String sectionId,String expCondition,int index){
		isElementDisplayed("txt_technicalDivisionPoints",sectionId,String.valueOf(index));
		System.out.println("-------expCondition:  "+expCondition);
		String output = element("txt_technicalDivisionPoints",sectionId,String.valueOf(index)).getText().trim();
		output=output.replaceAll("\\n"," ");
		System.out.println("actual:  "+output);
		Assert.assertEquals(output, expCondition,"ASSERT FAILED: "+expCondition+" text is not displayed on page\n");
		logMessage("ASSERT PASSED: "+expCondition+" text is displayed on page\n");
	}
	
	public void verifyPublicationConditionsOnBenefitsPage(String sectionId,Map<String,Object> publicationConditions){
		isElementDisplayed("list_technicalDivisionPoints",sectionId);
		for(int index=1;index<=publicationConditions.size();index++){ //start indexing from 1
			verifyConditionsOnBenefitsPage(sectionId, publicationConditions.get("condition"+index).toString(), index);
		}
	}
	
	public void verifyTextForAcsPublications(String text){
		isElementDisplayed("txt_acsPublications",text);
		logMessage("ASSERT PASSED: "+text+" is displayed on page\n");
	}

}
