package com.qait.MAPS.keywords;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class Review_Page_Actions extends ASCSocietyGenericPage {

	WebDriver driver;
	static String pagename = "Review_Page";

	public Review_Page_Actions(WebDriver driver) {
		super(driver, pagename);

	}

	public void verifyPageHeader(String header) {
		Assert.assertTrue(isElementDisplayed("txt_header", header),
				"ASSERT FAILED: Expected page header is '" + header + "'\n");
		logMessage("ASSERT PASSED: Verified page header as '" + header + "'\n");
	}

	public void selectRoleForReview(String role) {
		isElementDisplayed("radiobtn_role", role);
		click(element("radiobtn_role", role));
		logMessage("STEP: '" + role + "' is selected for review \n ");
	}

	public void clickOnButton(String text) {
		isElementDisplayed("btn_select", text);
		click(element("btn_select", text));
		logMessage("STEP: '" + text + "' button is clicked \n");
	}

	public void selectAbstractType(String text) {
		wait.waitForPageToLoadCompletely();
		waitForLoaderToDisappear();
		isElementDisplayed("txt_reportType", text);
		click(element("txt_reportType", text));
		logMessage("STEP: Abstract is selected as '" + text + "' \n");
	}

	public void verifyAbstractTitleUnderReviewModule(String title) {
		Assert.assertTrue(isElementDisplayed("txt_abstractTitle", title),
				"ASSERT FAILED: Expected page title is '" + title + "'\n");
		logMessage("ASSERT PASSED: Page title is verified as '" + title + "' \n");
	}

	public void verifyLinksUnderReviewModule(String text) {
		try {
			Assert.assertTrue(isElementDisplayed("lnk_reviewerScoreReport", text),
					"ASSERT FAILED: Expected link is '" + text + "'\n");
		} catch (NoSuchElementException e) {
			Assert.assertTrue(isElementDisplayed("txt_reportType", text),
					"ASSERT FAILED: Expected link is '" + text + "'\n");
		}
		logMessage("ASSERT PASSED: '" + text + "' link is verified \n");
	}

	public void verifyTextField(String text) {
		Assert.assertTrue(isElementDisplayed("input_filter", text),
				"ASSERT FAILED: Expected text field is '" + text + "'\n");
		logMessage("ASSERT PASSED: '" + text + "' text field is verified \n");
	}

	public void verifyDropDown(String text) {
		isElementDisplayed("comboBox_reviewPage", text);
		click(element("comboBox_reviewPage",text));
		Assert.assertTrue(isElementDisplayed("listItem"));
		logMessage("ASSERT PASSED: '" + text + "' is verified as dropdown \n");
	}

	public void verifyDropDownButtonType(String field) {
		// TODO Auto-generated method stub
		isElementDisplayed("btn_ImportExportExcel", field);
	}

}
