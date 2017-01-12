package com.qait.keywords;

import java.text.DecimalFormat;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.ConfigPropertyReader;
import com.qait.automation.utils.DateUtil;

public class ACS_Fundraising_Action extends ASCSocietyGenericPage {

	WebDriver driver;
	String pageName = "ACS_Fundraising";

	public ACS_Fundraising_Action(WebDriver driver) {
		super(driver, "ACS_Fundraising");
		this.driver = driver;
	}

	public void findConstituent(int index, String ConstituentName, String field) {
		hardWaitForIEBrowser(3);
		isElementDisplayed("txtbox_constituent", String.valueOf(index));
		element("txtbox_constituent", String.valueOf(index)).sendKeys(ConstituentName);
		logMessage("STEP : " + field + " is entered as " + ConstituentName + "\n");
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
//				element("txt_firstGiftDate", String.valueOf(i), String.valueOf(3)).click();
				clickUsingXpathInJavaScriptExecutor(element("txt_firstGiftDate", String.valueOf(i), String.valueOf(3)));
				logMessage("STEP : Constituent selected is " + constituentName + "\n");
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
		wait.hardWait(3);
		hardWaitForIEBrowser(10);
		for (String info : giftinfo) {
			if (element("txt_giftInformation", info).getText().trim().equals("")) {
				flag = true;
			} else {
				flag = false;
				field = info;
				break;
			}
		}
		Assert.assertTrue(flag, "ASSERT FAILED : Gift information for " + field + " is not null\n");
		logMessage("ASSERT PASSED : Gift information for all the fields is null\n");
	}

	public void clickOnAddGiftButton(String buttonName) {
		isElementDisplayed("btn_addGift", buttonName);
		if (ConfigPropertyReader.getProperty("browser").equalsIgnoreCase("IE")
				|| ConfigPropertyReader.getProperty("browser")
						.equalsIgnoreCase("ie")
				|| ConfigPropertyReader.getProperty("browser")
						.equalsIgnoreCase("internetexplorer")){
			clickUsingXpathInJavaScriptExecutor(element("btn_addGift", buttonName));
		}
		else
			clickUsingXpathInJavaScriptExecutor(element("btn_addGift", buttonName));
//		    element("btn_addGift", buttonName).click();	
		logMessage("STEP : Clicked on " + buttonName + " button\n");
	}

	public void selectFundCode(String field, String code) {
        hardWaitForIEBrowser(8);
        dynamicWait(10,"list_fundraisingCode", field);
//		isElementDisplayed("list_fundraisingCode", field);
		selectProvidedTextFromDropDown(element("list_fundraisingCode", field), code);
		logMessage("STEP : " + field + " is entered as " + code);
	}

	public void verifyGiftDate(String dateType) {
		isElementDisplayed("txt_" + dateType);
		Assert.assertEquals(element("txt_" + dateType).getAttribute("value"),
				DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/yyyy","EST"),
				"ASSERT FAILED : " + dateType + " is not verified as Current date "+DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/yyyy","EST")+"\n");
		logMessage("ASSERT PASSED : " + dateType + " is verified as Current date "+DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/yyyy","EST")+"\n");
	}

	public void enterGiftAmount(String amount, String field) {
		isElementDisplayed("inp_giftAmount", field);
		element("inp_giftAmount", field).clear();
		element("inp_giftAmount", field).sendKeys(amount);
		logMessage("STEP : " + field + " entered as " + amount + "\n");
		wait.hardWait(4);
        element("table_form").click();
        logMessage("STEP: table_form is clicked\n");
//		wait.hardWait(2);
//		clickUsingXpathInJavaScriptExecutor(element("table_form"));
	}
	
	public void clickOnTableForm(){
		if(!((isBrowser("ie")) || isBrowser("IE")))
		    element("table_form").click();
		else{
			isElementDisplayed("table_form");
			clickUsingXpathInJavaScriptExecutor(element("table_form"));
		}
//			element("label_giftDate").click();
	}

	public void verifyDeductibleAmount(String amount, String field) {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(6);
		isElementDisplayed("inp_giftAmount", field);
		wait.hardWait(2);
		System.out.println(element("inp_giftAmount", field).getAttribute("value"));
		Assert.assertEquals(element("inp_giftAmount", field).getAttribute("value"), amount,
				"ASSERT FAILED : Deductible amount value is not same as the Gift amount "+amount+"\n");
		logMessage("ASSERT PASSED : Deductible amount value is same as the Gift amount "+amount+"\n");
	}

	public void verifyGiftType(String giftValue) {
		isElementDisplayed("txt_giftType", "gift type");
		Assert.assertEquals(element("txt_giftType", "gift type").getText().trim(), giftValue,
				"ASSERT FAILED : Gift type is not" + giftValue + "\n");
		logMessage("ASSERT PASSED : Gift type is " + giftValue + "\n");
	}

	public void clickOnSaveButton() {
		isElementDisplayed("btn_save");
		// element("btn_save").click();
		clickUsingXpathInJavaScriptExecutor(element("btn_save"));
		logMessage("STEP : Clicked on Save button\n");
		switchToDefaultContent();
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
	}

	public void verifyNewGiftInformation(String expectedValue) {
		String giftinfo[] = { "first gift (join) date", "first gift amount", "highest gift date", "highest gift amount",
				"latest gift date", "latest gift amount", "ytd amount", "lifetime amount" };
		for (String info : giftinfo) {
			if (info.contains("date")) {
				verifyGiftInformationIsDispalyed(info, DateUtil.getCurrentdateInStringWithGivenFormateForTimeZone("M/d/yyyy","EST"));
			} else
				verifyGiftInformationIsDispalyed(info, expectedValue);
		}

	}

	public void verifyGiftIsAdded(String tabName, String expectedValue, int index, String giftInfo, int rowIndex) {
		isElementDisplayed("txt_listData", tabName, String.valueOf(index), String.valueOf(1));
		// System.out.println("-----expected:" + expectedValue);
		// System.out.println("-----actual:"
		// + element("txt_listData", tabName, String.valueOf(index),
		// String.valueOf(1)).getText().trim());
		Assert.assertEquals(
				element("txt_listData", tabName, String.valueOf(index), String.valueOf(rowIndex)).getText().trim(),
				expectedValue, "ASSERT FAILED : " + giftInfo + " is not verified as " + expectedValue + "\n");
		logMessage("ASSERT PASSED : " + giftInfo + " is verified as " + expectedValue + "\n");
	}

	public void verifyGiftInformationIsDispalyed(String giftField, String expectedValue) {
		isElementDisplayed("txt_giftInformation", giftField);
		Assert.assertEquals(element("txt_giftInformation", giftField).getText().trim(), expectedValue, "");
		logMessage("ASSERT PASSED : " + giftField + " value is verified as " + expectedValue + "\n");
	}

	public void addBatchAndPledgeType(String batchName, String pledgeType) {
		MembershipPageActions_IWEB obj = new MembershipPageActions_IWEB(driver);
		obj.selectOrderEntryInfo("batch", batchName);
		obj.waitForSpinner();
		obj.selectOrderEntryInfo("PaymentType", pledgeType);
		obj.waitForSpinner();
	}

	public void enterNumberOfPaymentInstallments(String value) {
		isElementDisplayed("inp_noOfInstallments");
		element("inp_noOfInstallments").sendKeys(value);
		logMessage("STEP : Number of payments installments entered as " + value + "\n");
	}

	public boolean verifyBatchIsPresent(String pledgeType) {
		boolean flag = false;
		MembershipPageActions_IWEB obj = new MembershipPageActions_IWEB(driver);
		isElementDisplayed("list_batchCreditPage");
		System.out.println("-----pledge type:" + element("txt_giftType", "pledge type").getText().trim());
		while (element("txt_giftType", "pledge type").getText().trim().equals("Please select")) {
			obj.selectOrderEntryInfo("PaymentType", pledgeType);
			obj.waitForSpinner();
		}
		return flag;

	}

	public void setGiftDate() {
		MembershipPageActions_IWEB obj = new MembershipPageActions_IWEB(driver);
		isElementDisplayed("txt_paymentDate");
		String nextDate[] = DateUtil.getNextDate("month", 1);
		element("txt_paymentDate").clear();
		element("txt_paymentDate").sendKeys(nextDate[1] + "/" + nextDate[2] + "/" + nextDate[0]);
		obj.waitForSpinner();
		element("txt_calendarDate").click();
		logMessage("STEP : First payment date entered as " + nextDate[1] + "/" + nextDate[2] + "/" + nextDate[0]);
		obj.waitForSpinner();
	}

	public void verifyPledgeIsAdded(String tabName, int index1, int index2, String fundCode, String appealCode,
			String amount) {
		int i;
		wait.hardWait(3);
		isElementDisplayed("list_tableRows", tabName);
		for (i = 1; i <= elements("list_tableRows", tabName).size(); i++) {
			wait.hardWait(1);
			System.out.println("----"
					+ element("txt_listData", tabName, String.valueOf(index1), String.valueOf(i)).getText().trim());
			System.out.println("----"
					+ element("txt_listData", tabName, String.valueOf(index2), String.valueOf(i)).getText().trim());
			System.out.println("----" + DateUtil.getCurrentdateInStringWithGivenFormate("M/d/yyyy"));

			if(ConfigPropertyReader.getProperty("tier").equalsIgnoreCase("Dev7")){
			if (element("txt_listData", tabName, String.valueOf(index1), String.valueOf(i)).getText().trim()
					.equals("No")
					&& element("txt_listData", tabName, String.valueOf(index2), String.valueOf(i)).getText().trim()
							.equals(DateUtil.getCurrentdateInStringWithGivenFormate("M/d/yyyy"))) {
				break;
			}
		  }else{
				if (element("txt_listData", tabName, String.valueOf(index1), String.valueOf(i)).getText().trim()
						.equals("Yes")
						&& element("txt_listData", tabName, String.valueOf(index2), String.valueOf(i)).getText().trim()
								.equals(DateUtil.getCurrentdateInStringWithGivenFormate("M/d/yyyy"))) {
					break;
				}
		  }
		}
		verifyGiftIsAdded(tabName, amount, 5, "Gift Amount", i);
		verifyGiftIsAdded(tabName, fundCode, 7, "Fund Code", i);
		verifyGiftIsAdded(tabName, appealCode, 9, "Appeal Code", i);
	}

	public void verifyPledgeInstallments(String tabName,int noOfPaymnets,String totalAmount) {
		wait.hardWait(1);
		isElementDisplayed("lst_pledgeRow", tabName);
		isElementDisplayed("txt_pledgeDate", tabName, DateUtil.getCurrentdateInStringWithGivenFormate("M/d/yyyy"));
		if (elements("txt_pledgeDate", tabName, DateUtil.getCurrentdateInStringWithGivenFormate("M/d/yyyy"))
				.size() < 2) {
			clickOnPledgeFolder(tabName);
		} else {
			elements("btn_pledgeFolder", tabName, DateUtil.getCurrentdateInStringWithGivenFormate("M/d/yyyy")).get(0)
					.click();
			logMessage("STEP : Clicked on first folder image\n");
		}
		Float amount=new Float(totalAmount);
		verifyInstallments(noOfPaymnets, amount);
	}

	public void clickOnPledgeFolder(String tabName) {
		isElementDisplayed("btn_pledgeFolder", tabName, DateUtil.getCurrentdateInStringWithGivenFormate("M/d/yyyy"));
		element("btn_pledgeFolder", tabName, DateUtil.getCurrentdateInStringWithGivenFormate("M/d/yyyy")).click();
		logMessage("STEP : Clicked on folder image\n");
	}

	public void verifyInstallments(float noOfPayments, float totalAmount) {
		 DecimalFormat df = new DecimalFormat("#.00");
		float payments = totalAmount / noOfPayments;
		int count=1;
		wait.hardWait(2);
		System.out.println("----Payment installments are:" + payments);
		isElementDisplayed("list_folderTable");
		if (noOfPayments == elements("list_folderTable").size() - 1) {
			for (int i = 2; i <= elements("list_folderTable").size(); i++) {
				String[] ar = DateUtil.getNextDate("month", count);	
				String date=ar[1] + "/" + ar[2] + "/" + ar[0];
				Assert.assertTrue(element("txt_folderData", String.valueOf(i), String.valueOf(4)).getText().trim()
								.equals(DateUtil.convertStringToParticularDateFormat(date, "m/d/yyyy")),
						"ASSERT FAILED: Schedule date for installment "+count+" is not verified as " + DateUtil.convertStringToDate(date, "m/d/yyyy"));
				logMessage("ASSERT PASSED: Schedule date for installment "+count+" is verified as " + DateUtil.convertStringToDate(date, "m/d/yyyy"));
				Assert.assertTrue(element("txt_folderData", String.valueOf(i), String.valueOf(6)).getText().trim()
						.equals(df.format(payments)),
				"ASSERT FAILED : Schedule amount for installment "+count+" is not verified as " + df.format(payments));
		logMessage("ASSERT PASSED : Schedule amount for installment "+count+" is verified as " + df.format(payments));
		count++;
			}
		}
		else
			Assert.assertFalse(true,"ASSERT FAILED : Payment installments are not "+payments);
	}

}
