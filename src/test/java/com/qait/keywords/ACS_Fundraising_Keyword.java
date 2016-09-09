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
		MembershipPageActions_IWEB obj = new MembershipPageActions_IWEB(driver);
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
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
			obj.clickOnRandomPage();
			selectAnyRandomConstituent();
		}
	}

	public void verifyGiftInformation() {
		boolean flag = false;
		String field = null;
		String giftinfo[] = { "first gift (join) date", "first gift amount", "highest gift date", "highest gift amount",
				"latest gift date", "latest gift amount", "ytd amount", "last-year amount", "lifetime amount" };
		wait.waitForPageToLoadCompletely();
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
				DateUtil.getCurrentdateInStringWithGivenFormate("M/d/yyyy"),
				"ASSERT FAILED: Gift date is not entered as Current date\n");
		logMessage("ASSERT PASSED: Gift date is entered as Current date\n");
	}

	public void enterGiftAmount(String amount, String field) {
		isElementDisplayed("inp_giftAmount", field);
		element("inp_giftAmount", field).clear();
		element("inp_giftAmount", field).sendKeys(amount);
		logMessage("STEP: " + field + " entered as " + amount + "\n");
		element("table_form").click();
	}

	public void verifyDeductibleAmount(String amount, String field) {
		wait.waitForPageToLoadCompletely();
		isElementDisplayed("inp_giftAmount", field);
		System.out.println(element("inp_giftAmount", field).getAttribute("value"));
		Assert.assertEquals(element("inp_giftAmount", field).getAttribute("value"), amount,
				"ASSERT FAILED: Deductible amount does not matches with the Gift amount\n");
		logMessage("ASSERT PASSED: Deductible amount matches with the Gift amount\n");
	}

	public void verifyGiftType(String giftValue) {
		isElementDisplayed("txt_giftType");
		Assert.assertEquals(element("txt_giftType").getText().trim(), giftValue,
				"ASSERT FAILED: Gift type is not" + giftValue + "\n");
		logMessage("ASSERT PASSED: Gift type is " + giftValue + "\n");
	}

	public void clickOnSaveButton() {
		isElementDisplayed("btn_save");
		element("btn_save").click();
		logMessage("STEP: Clicked on Save button\n");
	}

	public void selectBatchAndPaymentDetails_Fundraising(String batchName, String paymentType, String paymentMethod,
			String cardNumber, String expireDate, String cvvNumber, String checkNumber) {
		MembershipPageActions_IWEB obj = new MembershipPageActions_IWEB(driver);
		wait.waitForPageToLoadCompletely();
		obj.selectOrderEntryInfo("PaymentType", paymentType);
		obj.waitForSpinner();
		obj.selectOrderEntryInfo("paymentMethod", paymentMethod);
		obj.waitForSpinner();
		System.out.println("check number" + checkNumber);

		if (paymentMethod.equalsIgnoreCase("Visa/MC")) {
			obj.enterCardDetails("cardNumber", cardNumber);
			obj.selectMemberInfo("expireDate", expireDate);
			obj.enterCardDetails("cvvNumber", cvvNumber);
		} else if (paymentMethod.equalsIgnoreCase("BOA - Check")) {
			obj.enterCardDetails("checkNumber", checkNumber);

		} else {
			Assert.fail("ASSERT FAILED : Payment method " + paymentMethod + " is not correct \n");
		}
		clickOnSaveButton();
		switchToDefaultContent();
	}
	
	public void verifyNewGiftInformation(String expectedValue) {
		String giftinfo[] = { "first gift (join) date", "first gift amount", "highest gift date", "highest gift amount",
				"latest gift date", "latest gift amount", "ytd amount" ,"lifetime amount" };
		for (String info : giftinfo) {
			if (info.contains("date")) {
				verifyGiftInformationIsDispalyed(info, DateUtil.getCurrentdateInStringWithGivenFormate("M/d/yyyy"));
			} else
				verifyGiftInformationIsDispalyed(info, expectedValue);
		}

	}

	public void verifyGiftIsAdded(String tabName, String expectedValue, int index, String giftInfo) {
		isElementDisplayed("txt_listData", tabName, String.valueOf(index), String.valueOf(1));
//		System.out.println("-----expected:" + expectedValue);
//		System.out.println("-----actual:"
//				+ element("txt_listData", tabName, String.valueOf(index), String.valueOf(1)).getText().trim());
		Assert.assertEquals(element("txt_listData", tabName, String.valueOf(index), String.valueOf(1)).getText().trim(),
				expectedValue, "ASSERT FAILED: " + giftInfo + " is not verified as " + expectedValue + "\n");
		logMessage("ASSERT PASSED: " + giftInfo + " is verified as " + expectedValue + "\n");
	}

	public void verifyGiftInformationIsDispalyed(String giftField, String expectedValue) {
		isElementDisplayed("txt_giftInformation", giftField);
		Assert.assertEquals(element("txt_giftInformation", giftField).getText().trim(), expectedValue, "");
		logMessage("ASSERT PASSED: " + giftField + " value is verified as " + expectedValue + "\n");
	}

}
