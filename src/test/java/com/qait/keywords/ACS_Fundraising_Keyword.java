package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.DateUtil;

public class ACS_Fundraising_Keyword extends ASCSocietyGenericPage {

	WebDriver driver;
	String pageName = "ACS_Fundraising";

	public ACS_Fundraising_Keyword(WebDriver driver) {
		super(driver, "ACS_Fundraising");
		this.driver = driver;
	}

	public void findConstituent(int index, String ConstituentName, String field) {
		isElementDisplayed("txtbox_constituent", String.valueOf(index));
		element("txtbox_constituent", String.valueOf(index)).sendKeys(ConstituentName);
		logMessage("STEP: " + field + " is entered as: " + ConstituentName + "\n");
	}

	public void selectAnyRandomConstituent() {
		boolean flag = false;
		String constituentName = null;
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("table_rows");
		for (int i = 3; i <= elements("table_rows").size(); i++) {
			if (element("txt_firstGiftDate", String.valueOf(i), String.valueOf(7)).getText().equals("")) {
				flag = true;
				constituentName = element("txt_firstGiftDate", String.valueOf(i), String.valueOf(4)).getText().trim();
				element("txt_firstGiftDate", String.valueOf(i), String.valueOf(3)).click();
				logMessage("STEP : Constituent selected is: " + constituentName + "\n");
				break;
			}
		}
		if (!flag) {
			MembershipPageActions_IWEB obj = new MembershipPageActions_IWEB(driver);
			obj.clickOnRandomPage();
			selectAnyRandomConstituent();
		}
	}

	public void verifyGiftInformation() {
		boolean flag = false;
		String field = null;
		String giftinfo[] = { "first gift (join) date", "first gift amount", "highest gift date", "highest gift amount",
				"latest gift date", "latest gift amount", "ytd amount", "last-year amount", "lifetime amount" };
		for (String info : giftinfo) {
			if (element("txt_giftInformation", info).getText().trim().equals("")) {
				flag = true;
			} else {
				flag = false;
				field = info;
				break;
			}
		}
		Assert.assertTrue(flag, "ASSERT FAILED: Gift information for " + field + " is not null\n");
		logMessage("ASSERT PASSED: Gift information for all the fields is null\n");
	}

	public void clickOnAddGiftButton(String buttonName) {
		isElementDisplayed("btn_addGift", buttonName);
		element("btn_addGift", buttonName).click();
		logMessage("STEP: Clicked on " + buttonName + " button\n");
	}

	public void selectFundCode(String field, String code) {
		isElementDisplayed("list_fundraisingCode", field);
		selectProvidedTextFromDropDown(element("list_fundraisingCode", field), code);
		logMessage("STEP: " + field + " is entered as " + code);
	}

	public void verifyGiftDate() {
		isElementDisplayed("txt_giftDate");
		Assert.assertEquals(element("txt_giftDate").getAttribute("value"),
				DateUtil.getCurrentdateInStringWithGivenFormate("M/d/yyyy"), "ASSERT FAILED: Gift date is not entered as Current date\n");
		logMessage("ASSERT PASSED: Gift date is entered as Current date\n");
	}
	
	public void enterGiftAmount(String amount,String field){
		isElementDisplayed("inp_giftAmount",field);
		element("inp_giftAmount",field).clear();
		element("inp_giftAmount",field).sendKeys(amount);
		logMessage("STEP: "+field+" entered as "+amount+"\n");
		element("table_form").click();
	}
	
	public void verifyDeductibleAmount(String amount,String field){
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("inp_giftAmount",field);
		System.out.println(element("inp_giftAmount",field).getAttribute("value"));
	}
	
	public void verifyGiftType(String giftValue){
		isElementDisplayed("txt_giftType");
		Assert.assertEquals(element("txt_giftType").getText().trim(),giftValue,"ASSERT FAILED: Gift type is not"+giftValue+"\n");
		logMessage("ASSERT PASSED: Gift type is "+giftValue+"\n");
	}
	

}
