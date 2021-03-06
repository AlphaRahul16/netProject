package com.qait.keywords;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.DateUtil;

public class FundProfilePage extends ASCSocietyGenericPage {
	WebDriver driver;
	int timeOut, hiddenFieldTimeOut;
	int donationCount = 0;
	Map<String, String> mapFundOrder = new HashMap<String, String>();
	List<Integer> AwardsNominateDateCompareList = new ArrayList<Integer>();

	public FundProfilePage(WebDriver driver) {
		super(driver, "FundProfilePage");
		this.driver = driver;
	}

	public void deleteSuggestedDonationAmount() {
		try {
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			if (isElementDisplayed("btn_removeDonation")) {
				for (int i = 0; i < elements("btn_removeDonation").size();) {
					// wait.waitForPageToLoadCompletely();
					wait.hardWait(2);
					if (isBrowser("ie") || isBrowser("internet explorer")) {
						// clickUsingXpathInJavaScriptExecutor(elements("btn_removeDonation").get(0));
						hoverClick(elements("btn_removeDonation").get(0));
						logMessage("STEP : Remove Button is clicked");
						hardWaitForIEBrowser(5);
						wait.waitForPageToLoadCompletely();
						try {
							Alert alert = driver.switchTo().alert();
							// alert.accept();
							Robot robot = new Robot();
							robot.delay(2000);
							robot.keyPress(KeyEvent.VK_ENTER);
							robot.delay(2000);
							robot.keyRelease(KeyEvent.VK_ENTER);

						} catch (AWTException e) {
							e.printStackTrace();
						}
						// handleAlert();
						// switchToDefaultContent();
					} else {
						elements("btn_removeDonation").get(0).click();
						logMessage("STEP : Remove Button is clicked");
						Alert alert = driver.switchTo().alert();
						alert.accept();
					}
				}
			}
		} catch (Exception e) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}
		// wait.resetImplicitTimeout(timeOut);
		// wait.resetExplicitTimeout(timeOut);

		logMessage("STEP : All amounts inside suggested donation amounts are removed\n");
	}

	public void clickAddDonationButton(String headingname) {
		wait.hardWait(6);
		wait.waitForElementToBeVisible(element("btn_addDonationAmount", headingname));
		isElementDisplayed("btn_addDonationAmount", headingname);
		if (isBrowser("ie") || (isBrowser("internet explorer"))) {
			clickUsingXpathInJavaScriptExecutor(element("btn_addDonationAmount", headingname));
		} else {
			wait.hardWait(4);
			element("btn_addDonationAmount", headingname).click();
		}
		wait.waitForPageToLoadCompletely();
		logMessage("STEP : Add Donation button clicked\n");

	}

	public int addDonationAmountFromDataSheet(Map<String, String> mapSheetData) {
		int donationCount = getDonationAmountFromDataSheet(mapSheetData);
		System.out.println(donationCount + "  Donation count");
		for (int i = 0; i < donationCount; i++) {
			if (mapSheetData.get("suggested_donation_amount" + i).length() != 0) {
				clickAddDonationButton("suggested donation amounts");

				wait.hardWait(2);
				switchToFrame("iframe1");
				checkDefaultPriceCheckboxFromSpreadSheetValue(mapSheetData, toString().valueOf(i));
				enterDonationDetails("suggested donation amount", mapSheetData, i);
				enterDonationDetails("display order", mapSheetData, i);
				System.out.println("Default price::" + mapSheetData.get("Default_price?"));
				toString();
				System.out.println("value of i " + String.valueOf(i));
				isElementDisplayed("btn_save");
				if (isBrowser("ie") || (isBrowser("internet explorer")))
					clickUsingXpathInJavaScriptExecutor(element("btn_save"));
				else
					element("btn_save").click();
				wait.hardWait(1);
				switchToDefaultContent();
			}
		}
		return donationCount;
	}

	private void enterDonationDetails(String Name, Map<String, String> mapSheetData, int number) {
		isElementDisplayed("input_DonationDetails", Name);
		if (Name.equals("suggested donation amount")) {
			element("input_DonationDetails", Name).sendKeys(mapSheetData.get("suggested_donation_amount" + number));
			logMessage("STEP : Donation amount entered as " + mapSheetData.get("suggested_donation_amount" + number));
		} else if (Name.equals("display order")) {
			element("input_DonationDetails", Name).sendKeys(mapSheetData.get("Display_Order" + number));
			logMessage("STEP : Display Order entered as " + mapSheetData.get("Display_Order" + number));
		}

	}

	private void checkDefaultPriceCheckboxFromSpreadSheetValue(Map<String, String> mapSheetData, String number) {

		isElementDisplayed("chkbox_defaultprice");
		if (mapSheetData.get("Default_price?").contains(number)) {
			System.out.println("----in if");
			wait.hardWait(2);
			clickUsingXpathInJavaScriptExecutor(element("chkbox_defaultprice"));
			// element("chkbox_defaultprice").click();
			wait.hardWait(2);
			logMessage("STEP : Checkbox default price is clicked\n");
		}
	}

	private int getDonationAmountFromDataSheet(Map<String, String> mapSheetData) {

		for (int i = 0; i < mapSheetData.size(); i++) {

			// System.out.println(i);
			System.out.println(mapSheetData.get("suggested_donation_amount" + i));
			try {
				if (mapSheetData.get("suggested_donation_amount" + i).length() != 0) {
					System.out.println(i + " pass");
					donationCount++;
				}
			} catch (Exception e) {
				wait.resetExplicitTimeout(timeOut);
				wait.resetImplicitTimeout(timeOut);

			}

		}
		logMessage("STEP : " + donationCount + " donation amounts to be added from sheet\n");
		return donationCount;
	}

	public Map<String, String> arrangeDisplayOrderInAscendingOrder(int donationcount,
			Map<String, String> mapSheetData) {
		switchToDefaultContent();
		wait.waitForPageToLoadCompletely();
		ArrayList<Integer> displayorder = new ArrayList<Integer>();
		for (int i = 0; i < donationcount; i++) {
			if (mapSheetData.get("Display_Order" + i).length() != 0) {
				displayorder.add(Integer.parseInt(mapSheetData.get("Display_Order" + i)));
				System.out.println(displayorder.get(i));
			}

		}

		Collections.sort(displayorder);

		return getFundDisplayOrderOnEweb(displayorder);

	}

	private Map<String, String> getFundDisplayOrderOnEweb(ArrayList<Integer> displayorder) {

		wait.waitForPageToLoadCompletely();
		wait.hardWait(3);
		wait.resetExplicitTimeout(timeOut);
		wait.resetImplicitTimeout(timeOut);
		for (int i = 0; i < (displayorder.size() - 1); i++) {
			System.out.println(displayorder.size());
			wait.waitForPageToLoadCompletely();
			toString();
			wait.hardWait(2);
			mapFundOrder.put("Amount" + i,
					element("txt_fundNameByOrder", String.valueOf(displayorder.get(i))).getText().replace(",", ""));
			System.out.println(mapFundOrder.get("Amount" + i));
		}
		return mapFundOrder;
	}

	public void editpostToWebAndRemoveFromWebDates_AwardNomination() {
		String postToWeb = element("inp_invoiceValue", "post to web").getText().trim();
		String removeFromWeb = element("inp_invoiceValue", "remove from web").getText().trim();
		if(postToWeb.length() == 0 || removeFromWeb.length()==0)
		{
		editDatesIfNotPresent(postToWeb, removeFromWeb);
		}
		else
		{
		System.out.println(
				"flag" + isCurrentDateFallsBetweenTwoDates(postToWeb,
						removeFromWeb));
		if (isCurrentDateFallsBetweenTwoDates(element("inp_invoiceValue", "post to web").getText().trim(),
				element("inp_invoiceValue", "remove from web").getText().trim()) == true) {
			clickEditButtonOnAwardsProfile();
			editPostToAndRemoveFromDatesForAwardNomination();

		}
		}
	}

	private void editPostToAndRemoveFromDatesForAwardNomination() {
		switchToFrame(element("iframeMessageMenu"));
		if (AwardsNominateDateCompareList.get(0) == -1) {
			System.out.println("post " + DateUtil.getAnyDateForType("MM/dd/yyyy", -1, "year"));
			fillDatesAsNamed("awh_post_to_web_date", -1);
		}
		if (AwardsNominateDateCompareList.get(1) == 1) {
			System.out.println("remove " + DateUtil.getAnyDateForType("MM/dd/yyyy", 1, "year"));
			fillDatesAsNamed("awh_remove_from_web_date", 1);

		}
		clickSaveButtonInAwardNomination();

	}

	private void clickSaveButtonInAwardNomination() {
		isElementDisplayed("btn_save");
		click(element("btn_save"));
		switchToDefaultContent();
		logMessage("STEP : Save button clicked in btn_save");

	}

	private void clickEditButtonOnAwardsProfile() {
		isElementDisplayed("img_editAwards");
		click(element("img_editAwards"));
		logMessage("STEP : Edit Button is clicked in img_editAwards\n");

	}

	private boolean isCurrentDateFallsBetweenTwoDates(String postToWeb, String removeFromWeb) {
		boolean flag = true;
		int comaprePostToWeb;
		int compareRemoveFromWeb;
		comaprePostToWeb = DateUtil
				.convertStringToDate(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"), "MM/dd/yyyy")
				.compareTo(DateUtil.convertStringToDate(postToWeb, "MM/dd/yyyy"));
		AwardsNominateDateCompareList.add(comaprePostToWeb);
		compareRemoveFromWeb = DateUtil
				.convertStringToDate(DateUtil.getCurrentdateInStringWithGivenFormate("MM/dd/yyyy"), "MM/dd/yyyy")
				.compareTo(DateUtil.convertStringToDate(removeFromWeb, "MM/dd/yyyy"));
		AwardsNominateDateCompareList.add(compareRemoveFromWeb);
		System.out.println("postToWeb" + postToWeb);
		System.out.println("compareRemoveFromWeb" + compareRemoveFromWeb);
		if ((comaprePostToWeb == 1 || compareRemoveFromWeb == 0)
				&& (compareRemoveFromWeb == -1 || compareRemoveFromWeb == 0)) {
			flag = false;
		}
		return flag;

	}
	
	private void editDatesIfNotPresent(String postToWeb, String removeFromWeb) {
		System.out.println("length "+postToWeb.length());

			clickEditButtonOnAwardsProfile();
			wait.hardWait(10);
			switchToDefaultContent();
			switchToFrame(element("iframeMessageMenu"));
			fillDatesAsNamed("awh_post_to_web_date", -1);
			fillDatesAsNamed("awh_remove_from_web_date", 1);
			clickSaveButtonInAwardNomination();
	}
	
	private void fillDatesAsNamed(String name,int difference)
	{
		EnterTextInFieldByJavascript(name, DateUtil.getAnyDateForType("MM/dd/yyyy", difference, "year"));
		logMessage("STEP : For "+name+" date entered as " + DateUtil.getAnyDateForType("MM/dd/yyyy",difference, "year"));
	}

}