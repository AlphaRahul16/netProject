package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.ArrayList;

import junit.framework.Assert;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class BenefitsPage extends ASCSocietyGenericPage {

	WebDriver driver;
	String pagename = "BenefitsPage";
	ArrayList<String> score_divisionName = new ArrayList<String>();
	ArrayList<String> score_publicationName = new ArrayList<String>();
	Float subtotalTechnical_score = 0.0f;
	Float subtotalPublication_score = 0.0f;
	int timeOut,hiddenFieldTimeOut;
	
	public BenefitsPage(WebDriver driver) {
		super(driver, "BenefitsPage");
		this.driver = driver;
	}

	public void addACSPublication(String publicationName) {
		if (!publicationName.equalsIgnoreCase("")) {
			clickOnACSPublication();
			clickOnAddToMembership(publicationName);
			String str = getDivision_publicationScore(publicationName);
			score_publicationName.add(str);
			subtotalPublication_score = subtotalPublication_score
					+ Float.parseFloat(score_publicationName.get(0));

			verifyTotalScorePublication(subtotalPublication_score);
			clickSaveButton();
			verifyDivision_PublicationAdded(publicationName);
		} else {
			logMessage("Step:  add ACS Publication value is not present in data sheet\n");
		}

	}

	public void addACSTechnicalDivision(String divisionName) {
		if (!divisionName.equalsIgnoreCase("")) {
			clickOnACSTechnicalDivision();
			clickOnAddToMembership(divisionName);
			String str = getDivision_publicationScore(divisionName);
			score_divisionName.add(str);
			subtotalTechnical_score = subtotalTechnical_score
					+ Float.parseFloat(score_divisionName.get(0));
			verifyTotalScoreDivision(subtotalTechnical_score);
			clickSaveButton();
			verifyDivision_PublicationAdded(divisionName);
		} else {
			logMessage("Step: ACS Technical Division value is not present in data sheet\n");
		}

	}

	public void verifyCENPresent(String caseId) {
		hiddenFieldTimeOut=Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		if (getOmaSheetValue(caseId, "C&EN Status").equalsIgnoreCase("Y")) {
			try {
				wait.resetImplicitTimeout(0);
				wait.resetExplicitTimeout(hiddenFieldTimeOut);
				boolean flag = element("rad_dues_cenPackage", "C&EN Package")
						.isDisplayed();
				if (flag)
					click(element("rad_dues_cenPackage", "C&EN Package"));
				wait.resetImplicitTimeout(timeOut);
				wait.resetExplicitTimeout(timeOut);
			} catch (Exception exp) {
				
			}

			if (getOmaSheetValue(caseId, "CENtype").equalsIgnoreCase("print")) {
				isElementDisplayed("rad_CENType", "Print");
				click(element("rad_CENType", "Print"));
				logMessage("Step:  rad_CENType is clicked\n");
			} else if (getOmaSheetValue(caseId, "CENtype").equalsIgnoreCase(
					"electronic")) {
				isElementDisplayed("rad_CENType", "Email");
				click(element("rad_CENType", "Email"));
				logMessage("Step:  rad_CENType is clicked\n");
			} else {
				logMessage("Step : CENType is invalid in data sheet\n");
				Assert.fail("ASSERT FAIL : CENType in data sheet is not valid\n");
			}
		} else if (getOmaSheetValue(caseId, "C&EN Status")
				.equalsIgnoreCase("N")) {
			click(element("rad_dues_cenPackage", "Dues Only Package"));
			logMessage("Step:  rad_duesOnlyPackage is clicked\n");
		} else {
			logMessage("Step:  CEN status value is not valid in data sheet\n");
			Assert.fail("ASSERT FAIL : C&EN Status in data sheet is not valid\n");
		}

	}

	private void clickOnACSPublication() {
		isElementDisplayed("btn_ACSPublication");
		click(element("btn_ACSPublication"));
		logMessage("Step:  btn_ACSPublication is clicked\n");
	}

	private void clickOnACSTechnicalDivision() {
		isElementDisplayed("btn_ACStechnicalDivision");
		click(element("btn_ACStechnicalDivision"));
		logMessage("Step:  btn_ACStechnicalDivision is clicked\n");
	}

	private void clickOnAddToMembership(String divisionName) {
		isElementDisplayed("btn_addToMembership", divisionName);
		click(element("btn_addToMembership", divisionName));
		logMessage("Step:  " + divisionName
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
		click(element("btn_save"));
		logMessage("Step:  btn_save is clicked\n");
	}

	private void verifyDivision_PublicationAdded(String divisionName) {
		isStringMatching("Added", element("txt_added", divisionName).getText());
		logMessage("ASSERT PASSED :  ACS " + divisionName
				+ " added successfully in txt_added\n");
	}

	public void addACSPublicationAndTechnicalDivision(String caseId) {
		addACSTechnicalDivision(getOmaSheetValue(caseId, "Technical Division"));
		addACSPublication(getOmaSheetValue(caseId, "PublicationName"));

	}

}
