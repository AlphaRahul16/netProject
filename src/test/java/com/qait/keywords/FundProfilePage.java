package com.qait.keywords;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class FundProfilePage extends ASCSocietyGenericPage
{
	WebDriver driver;
	int timeOut, hiddenFieldTimeOut;
	int donationCount=0;
	Map<String,String> mapFundOrder = new HashMap<String,String>();
	
	
	public FundProfilePage(WebDriver driver) {
		super(driver, "FundProfilePage");
		this.driver = driver;
	}

	public void deleteSuggestedDonationAmount() {
		try
		{
			wait.resetImplicitTimeout(2);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
		if(isElementDisplayed("btn_removeDonation"))
		{
		for(int i=0;i<elements("btn_removeDonation").size();)
		{
	    wait.waitForPageToLoadCompletely();
	    wait.hardWait(2);
		elements("btn_removeDonation").get(0).click();
		Alert alert = driver.switchTo().alert();
		alert.accept();
		}
		}
		}
		catch(Exception e)
		{
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
		
		logMessage("Step : All amounts inside suggested donation amounts are removed\n");
	}

	public void clickAddDonationButton(String headingname) {
		isElementDisplayed("btn_addDonationAmount",headingname);
		element("btn_addDonationAmount",headingname).click();
		wait.waitForPageToLoadCompletely();
		logMessage("Step : Add Donation button clicked\n");
		
	}

	public int addDonationAmountFromDataSheet(Map<String, String> mapSheetData) {
		int donationCount=getDonationAmountFromDataSheet(mapSheetData);
		System.out.println(donationCount+"Donation count");
		for(int i=0;i<donationCount;i++)
		{
		if(mapSheetData.get("suggested_donation_amount"+i).length()!=0)
		{
		clickAddDonationButton("suggested donation amounts");
		
		wait.hardWait(2);
		switchToFrame("iframe1");
		checkDefaultPriceCheckboxFromSpreadSheetValue(mapSheetData,toString().valueOf(i));
		enterDonationDetails("suggested donation amount",mapSheetData,i);
		enterDonationDetails("display order",mapSheetData,i);
		System.out.println(mapSheetData.get("Default_price?"));
		toString();
		System.out.println(String.valueOf(i));
		element("btn_save").click();
		wait.hardWait(1);
		switchToDefaultContent();
		}
		}
		return donationCount;
	}

	private void enterDonationDetails(String Name, Map<String, String> mapSheetData,int number) {
		isElementDisplayed("input_DonationDetails",Name);
		if(Name.equals("suggested donation amount"))
		{
		element("input_DonationDetails",Name).sendKeys(mapSheetData.get("suggested_donation_amount"+number));
		logMessage("Step : donation amount entered as "+mapSheetData.get("suggested_donation_amount"+number));
		}
		else if(Name.equals("display order"))
		{
		element("input_DonationDetails",Name).sendKeys(mapSheetData.get("Display_Order"+number));
		logMessage("Step : Display Order entered as "+mapSheetData.get("Display_Order"+number));
		}

		
	}

	private void checkDefaultPriceCheckboxFromSpreadSheetValue(Map<String, String> mapSheetData,String number)
	{
		
		isElementDisplayed("chkbox_defaultprice");
		if(mapSheetData.get("Default_price?").contains(number))
		{
			wait.hardWait(2);
			element("chkbox_defaultprice").click();
			wait.hardWait(2);
			logMessage("Step : checkbox default price is clicked\n");
		}
	}

	private int getDonationAmountFromDataSheet(Map<String, String> mapSheetData) {
	
		for(int i=0;i<mapSheetData.size();i++)
		{

			System.out.println(i);
			System.out.println(mapSheetData.get("suggested_donation_amount"+i));
			try
			{
			if(mapSheetData.get("suggested_donation_amount"+i).length()!=0)
			{
				System.out.println(i+" pass");
				donationCount++;
			}
			}
			catch(Exception e)
			{
			wait.resetExplicitTimeout(timeOut);
			wait.resetImplicitTimeout(timeOut);

			}
				
		}
		logMessage("Step : "+ donationCount+" donation amounts to be added from sheet\n");
		return donationCount;
	}

	public Map<String, String> arrangeDisplayOrderInAscendingOrder(int donationcount,Map<String, String> mapSheetData) {
		switchToDefaultContent();
		wait.waitForPageToLoadCompletely();
		ArrayList<Integer> displayorder = new ArrayList<Integer>();
		for(int i=0;i<donationcount;i++)
		{
			if(mapSheetData.get("Display_Order"+i).length()!=0)
			{
			displayorder.add(Integer.parseInt(mapSheetData.get("Display_Order"+i)));
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
		for(int i=0;i<displayorder.size();i++)
		{
			System.out.println(i);
			System.out.println("Display order "+displayorder.get(i));
            wait.waitForPageToLoadCompletely();
			toString();
			System.out.println(element("txt_fundNameByOrder",String.valueOf(displayorder.get(i))).getText().replace(",",""));
			toString();
			mapFundOrder.put("Amount"+i,element("txt_fundNameByOrder", String.valueOf(displayorder.get(i))).getText().replace(",",""));
		}
		return mapFundOrder;
	}


	
	

}