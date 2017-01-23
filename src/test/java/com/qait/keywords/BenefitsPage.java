package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

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
			clickOnACSPublication();
			clickOnAddToMembership(publicationName);
			if(isBrowser("ie") || isBrowser("internet explorer")){
			verifyPrdouctAddition(publicationName);    
			}
			String str = getDivision_publicationScore(publicationName);
			score_publicationName.add(str);
			subtotalPublication_score = subtotalPublication_score
					+ Float.parseFloat(score_publicationName.get(0));

			verifyTotalScorePublication(subtotalPublication_score);
			verifyDivision_PublicationAdded(publicationName);
			clickSaveButton();

		} else {
			logMessage("STEP :  Add ACS Publication value is not present in data sheet\n");
		}

	}

	public void addACSTechnicalDivision(String divisionName) {
		if (!divisionName.equalsIgnoreCase("")) {
			clickOnACSTechnicalDivision();
			clickOnAddToMembership(divisionName);
			if(isBrowser("ie") || isBrowser("internet explorer")){
			verifyPrdouctAddition(divisionName);  
			}
			String str = getDivision_publicationScore(divisionName);
			score_divisionName.add(str);
			subtotalTechnical_score = subtotalTechnical_score
					+ Float.parseFloat(score_divisionName.get(0));
			verifyTotalScoreDivision(subtotalTechnical_score);
			verifyDivision_PublicationAdded(divisionName);
			clickSaveButton();

		} else {
			logMessage("STEP : ACS Technical Division value is not present in data sheet\n");
		}

	}

	public void verifyCENPresent(String caseId) {
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		hardWaitForIEBrowser(20);
		if (map().get("C&EN Status").equalsIgnoreCase("Y")) {
			try {
				wait.resetImplicitTimeout(0);
				wait.resetExplicitTimeout(hiddenFieldTimeOut);
				boolean flag = element("rad_dues_cenPackage", "C&EN Package")
						.isDisplayed();
				if (flag)
					clickUsingXpathInJavaScriptExecutor(element(
							"rad_dues_cenPackage", "C&EN Package"));
				// click(element("rad_dues_cenPackage", "C&EN Package"));
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
			} catch (Exception exp) {

			}

			if (map().get("CENtype").equalsIgnoreCase("print")) {
				// wait.waitForPageToLoadCompletely();
				hardWaitForIEBrowser(20);
				isElementDisplayed("rad_CENType", "Print");
				clickUsingXpathInJavaScriptExecutor(element("rad_CENType",
						"Print"));
				// click(element("rad_CENType", "Print"));
				logMessage("STEP :  rad_CENType is clicked\n");
			} else if (map().get("CENtype").equalsIgnoreCase("electronic")) {
				isElementDisplayed("rad_CENType", "Email");
				clickUsingXpathInJavaScriptExecutor(element("rad_CENType",
						"Email"));
				// click(element("rad_CENType", "Email"));
				logMessage("STEP :  rad_CENType is clicked\n");
			} else {
				logMessage("STEP : CENType is invalid in data sheet\n");
				Assert.fail("ASSERT FAIL : CENType in data sheet is not valid\n");
			}
		} else if (map().get("C&EN Status").equalsIgnoreCase("N")) {
			clickUsingXpathInJavaScriptExecutor(element("rad_dues_cenPackage",
					"Dues Only Package"));
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

	private void clickOnAddToMembership(String divisionName) {
		hardWaitForIEBrowser(20);
		if(isBrowser("ie")||isBrowser("internet explorer")){
			element("btn_addToMembership",
				divisionName).click();
		}
		else{
			isElementDisplayed("btn_addToMembership", divisionName);
		clickUsingXpathInJavaScriptExecutor(element("btn_addToMembership",
				divisionName));
		}
		// click(element("btn_addToMembership", divisionName));
		logMessage("STEP :  " + divisionName
				+ " btn_addToMembership is clicked\n");
	}

	private String getDivision_publicationScore(String divisionName) {
		String score_division = element("txt_divisionScore", divisionName)
				.getText();
		String division1_score = score_division.replace("$", "");
		return division1_score;
	}

	private void verifyTotalScoreDivision(Float subtotalTechnical_score) {
		Float actualTechnicalScore = Float.parseFloat(element(
				"txt_technicalDivisionSubtotal").getText().replace("$", ""));
		Assert.assertEquals(subtotalTechnical_score, actualTechnicalScore);

	}

	private void verifyTotalScorePublication(Float subtotalPublication_score) {
		Float actualTechnicalScore = Float.parseFloat(element(
				"txt_technicalDivisionSubtotal").getText().replace("$", ""));
		Assert.assertEquals(subtotalPublication_score, actualTechnicalScore);
	}

	private void clickSaveButton() {
		isElementDisplayed("btn_save");
		clickUsingXpathInJavaScriptExecutor(element("btn_save"));
		wait.waitForPageToLoadCompletely();
		// click(element("btn_save"));
		logMessage("STEP :  btn_save is clicked\n");
	}

	private void verifyDivision_PublicationAdded(String divisionName) {
		isStringMatching("Added", element("txt_added", divisionName).getText());
		logMessage("ASSERT PASSED :  ACS " + divisionName
				+ " added successfully in txt_added\n");
	}

	public void addACSPublicationAndTechnicalDivision(String caseId) {
		wait.hardWait(3);
		addACSTechnicalDivision(map().get("Technical Division"));
		addACSPublication(map().get("PublicationName"));

	}
	
	public void verifyPrdouctAddition(String divisionName){
		int count=0;
		hardWaitForIEBrowser(5);
	    //System.out.println("------added is present:"+checkIfElementIsThere("txt_productAdded",divisionName));
		while(!checkIfElementIsThere("txt_productAdded",divisionName) && (count<6)){
			clickOnAddToMembership(divisionName);
			System.out.println("Step: "+divisionName+" is again clicked");
			count++;
		}
	}

}
