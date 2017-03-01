package com.qait.keywords;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class ACS_Yb_Iweb_Action extends ASCSocietyGenericPage{

	WebDriver driver;
	public ACS_Yb_Iweb_Action(WebDriver driver) {
		super(driver, "ACS_Yb_Iweb");
		this.driver = driver;
	}
	
	public void findCommitteeOnTypeCodeBasis(String committeType){
		isElementDisplayed("drpdown_commiteeType");
		selectProvidedTextFromDropDown(element("drpdown_commiteeType"), committeType);
		logMessage("STEP: Committee Type Code is selected as "+committeType+"\n");
	}
	
	public void clickOnEditButton(){
		wait.hardWait(2);
		isElementDisplayed("btn_edit");
		clickUsingXpathInJavaScriptExecutor(element("btn_edit"));
//		element("btn_edit").click();
		logMessage("STEP: Clicked on Edit button\n");
	}
	
	public void verifyDivisionFieldsOnEditCommittePage(String element, String commiteeField){
		isElementDisplayed(element,commiteeField);
		logMessage("ASSERT PASSED: '"+commiteeField+"' field is dispalyed on Committee page\n");
	}
	
	public void verifyFieldIsPresent(String committeeFields[],String type){
		switchToFrame("iframe1");
		for(String fieldName: committeeFields){
			if((fieldName.equals("tax id:") && type.equals("Division")) || type.equals("Regional Meeting")){
				verifyDivisionFieldsOnEditCommittePage("txt_committeeFields",fieldName);
			}
			else
				verifyDivisionFieldsOnEditCommittePage("txt_committeeLabels",fieldName);
		}
		MembershipPageActions_IWEB obj_member=new MembershipPageActions_IWEB(driver);
		obj_member.clickOnCancelButton();
		switchToDefaultContent();
	}
	
	public void verifyCommitteeType(String label,String committeeType){
		isElementDisplayed("txt_committeeDetails",label);
		Assert.assertEquals(committeeType,element("txt_committeeDetails",label).getText().trim(), "ASSERT FAILED: Committee type is not verified as "+committeeType+"\n");
		logMessage("ASSERT PASSED: Committee type is verified as "+committeeType+"\n");
	}
	
	public void getCommitteeName(){
		isElementDisplayed("txt_committeeName");
		logMessage("STEP: Committee selected is "+element("txt_committeeName").getText().trim()+"\n");
	}
}
