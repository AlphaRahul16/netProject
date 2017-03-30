package com.qait.MAPS.keywords;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.itextpdf.text.log.SysoCounter;
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

	public void verifyLinksUnderNamedModule(String text) {
		try {
			Assert.assertTrue(isElementDisplayed("btn_select", text),
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
	
	public void verifyCrossImageForNamedDropDown(String fieldName)
	{
		Assert.assertTrue(isElementDisplayed("img_CrossFilter", fieldName),
				"ASSERT FAILED: Image for " + fieldName + "is not verified'\n");
		logMessage("ASSERT PASSED: 'Image for " + fieldName + "is not verified \n");
		
	}

	public void verifyDropDown(String fieldName) {
		
		switch(fieldName)
		{
			case "Found In" :
				isElementDisplayed("comboBox_reviewPage", fieldName);
				click(element("comboBox_reviewPage", fieldName));
				Assert.assertTrue(elements("list_drpdwnoptions").size()>0," no option available on clicking dropdown "+fieldName);
				click(element("comboBox_reviewPage", fieldName)); 
			break;
			
			case "Records per page" : 
			isElementDisplayed("drpdwn_records", fieldName);
			click(element("drpdwn_records", fieldName));
			Assert.assertTrue(elements("list_drpdwnoptions").size()>0," no option available on clicking dropdown "+fieldName);
			click(element("drpdwn_records", fieldName)); 
			break;
			
			case "Toggle View":
			case "Export to Excel":
			case "Import/Export to Excel":
				isElementDisplayed("btn_ImportExportExcel",fieldName);
				click(element("btn_ImportExportExcel",fieldName));
				Assert.assertTrue(elements("list_drpdwnoptions").size()>0," no option available on clicking dropdown "+fieldName);
				click(element("btn_ImportExportExcel",fieldName));
			break;
			
			case "Default":
				logMessage("Step : Dropdown not available\n");
			
			logMessage("ASSERT PASSED: '" + fieldName + "' dropdown is verified with options\n");
				
		}
		

	}

	public void verifyRoleDropDown() {
		isElementDisplayed("drpdown_role");
		click(element("drpdown_role"));
		Assert.assertTrue(isElementDisplayed("listItem"));
		click(element("drpdown_role"));
		logMessage("ASSERT PASSED: 'Role'dropdown is verified \n");
	}

	public void verifyExpandIconUnderNamedModule() {
		Assert.assertTrue(isElementDisplayed("btn_expandIcon"),
				"ASSERT FAILED: 'Expand this grid to full screen' icon is not displayed ");
		logMessage("ASSERT PASSED: 'Expand this grid to full screen' icon is displayed \n");
	}

	public void verifyButton(String btnName) {
		Assert.assertTrue(isElementDisplayed("btn_ImportExportExcel", btnName),
				"ASSERT FAILED: " + btnName + " is not displayed ");
		logMessage("ASSERT PASSED: " + btnName + " is displayed ");
	}

	public void verifyReviewerScoreReportTable() {
		Assert.assertTrue(isElementDisplayed("table_ReviewerScoreReport"),
				"ASSERT FAILED: Reviewer Score Report table is not displayed ");
		logMessage("ASSERT PASSED: Reviewer Score Report table is displayed ");
	}

	public void verifyPaginationSectionAtTheBottomOfTheTable() {
		Assert.assertTrue(isElementDisplayed("table_pageination"),
				"ASSERT FAILED: Pagination section is not at the bottom of the table");
		logMessage("ASSERT PASSED: Pagination section is present at the bottom of the table \n");
	}

	public void enterDetailsAtSaveGridConfigurationPage(String value) {
		isElementDisplayed("input_SaveGridConfig", "Name");
		element("input_SaveGridConfig", "Name").sendKeys(value);
		logMessage("STEP: Enter details at Save Grid Configuration Page");

	}

	public void enterValueInFilter(String value) {
		isElementDisplayed("input_filter", "Filter");
		element("input_filter", "Filter").sendKeys(value);
		logMessage("STEP: " + value + " is entered in filter input box \n");
	}

	public void verifyTheResult(String cID) {
		waitForLoaderToDisappear();
		isElementDisplayed("txt_tabledata");
		System.out.println("actual" + elements("txt_tabledata").get(0).getText());
		Assert.assertEquals(cID, elements("txt_tabledata").get(0).getText());
		logMessage("ASSERT PASSED: Result contains " + cID + "\n");
	}

	public String getValueFromReviewerScoreReportTable() {
		isElementDisplayed("txt_tabledata");
		String cID = element("txt_tabledata").getText();
		System.out.println("CID" + cID);
		return cID;
	}

	public void verifyOptionsffromRecordsPerPageDropdown(String[] pageSize) {
		int i = 0;
		isElementDisplayed("comboBox_reviewPage", "Page");
		click(element("comboBox_reviewPage", "Page"));
		for (WebElement element : elements("listItem")) {
			Assert.assertEquals(element.getText().trim(), pageSize[i]);
			i++;
		}
		logMessage("ASSERT PASSED: All the option in Record per page drop down is displayed correctly.");
	}

	public void clickOnButtonAtSaveGridConfigurationPage(String text) {
		isElementDisplayed("btn_ImportExportExcel", text);
		click(element("btn_ImportExportExcel", text));
		logMessage("STEP: '" + text + "' button is clicked \n");

	}

	public void selectExistingConfigurationFromGridConfigurationDropdown() {
		isElementDisplayed("img_dropdown");
		//clickUsingXpathInJavaScriptExecutor(elements("img_dropdown").get(0));
		elements("img_dropdown").get(0).click();
		logMessage("STEP: Clicked on grid config drop down");
		wait.hardWait(3);
		isElementDisplayed("list_gripConfig");
		click(elements("list_gripConfig").get(2));
		logMessage("STEP: Existing configuration from the Grid Configuration dropdown is selected \n ");
	}
	public void verifybuttonOnRolesPage(String text) {
		Assert.assertTrue(isElementDisplayed("btn_ImportExportExcel", text));
		logMessage("ASSERT PASSED: " + text + " is displayed \n");

	}

}
