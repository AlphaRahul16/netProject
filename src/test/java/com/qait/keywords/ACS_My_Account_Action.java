package com.qait.keywords;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.poi.ss.formula.functions.Replace;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class ACS_My_Account_Action extends ASCSocietyGenericPage{

	WebDriver driver;
	String pageName = "ACS_MyAccount",app_url_IWEB,app_url_MyAccount;
	HashMap<String, String> changedValues= new HashMap<>();

	public ACS_My_Account_Action(WebDriver driver){
		super(driver,"ACS_MyAccount");
//		app_url_IWEB = getYamlValue("app_url_IwebMyAccount");
//		app_url_MyAccount= getYamlValue("app_url_MyAccount");
		this.driver=driver;
	}
	
	public void clickOnLoginButton(String btnName){
		isElementDisplayed("btn_registerMember",btnName);
		element("btn_registerMember",btnName).click();
		logMessage("STEP: Clicked on "+btnName+" button\n");
	}
	
	public void logInToMyAccount(String webLogin,String password){
//		wait.waitForPageToLoadCompletely();
		wait.hardWait(4);
		enterLoginDetails("userid", webLogin);
		enterLoginDetails("password", password);
		clickOnSaveButton("Log In");
	}
	
	public void createNewUser(String emailId,String fName,String lName,String webLogin,String password,int number){
		enterEmail("email",emailId , "Email", number);
		enterNewMemberDetails("firstName", fName + number,"First Name");
		enterNewMemberDetails("lastName", lName, "Last Name");
		enterNewMemberDetails("userName", webLogin,"User Name");
		enterNewMemberDetails("passwordPwd",password , "Password");
		enterNewMemberDetails("confirmPassword",password,"Confirm Password");
	}
	
	public void enterNewMemberDetails(String field,String value,String fieldName){
		isElementDisplayed("input_memberDetails",field);
		element("input_memberDetails",field).sendKeys(value);
		logMessage("Step: "+fieldName+" is entered as "+value+"\n");
	}
	
	public void clickOnCreateAccountButton(String btnName){
		isElementDisplayed("input_memberDetails",btnName);
		element("input_memberDetails",btnName).click();
		logMessage("STEP: Clicked on "+btnName+"\n");
	}
	
	public int generateThreeDidgitRandomNumber(int max, int min){
		Random rand = new Random();
		int randomNumber = rand.nextInt((max - min) + 1) + min;
		System.out.println("-------numbr "+randomNumber);
		return randomNumber;
	}
	
	public void enterEmail(String field,String value,String fieldName,int number){
	    value=value.replaceAll("@acs.org", number+"@acs.org");
		System.out.println("-------"+value);
		isElementDisplayed("input_memberDetails",field);
		element("input_memberDetails",field).sendKeys(value);
		logMessage("Step: "+fieldName+" is entered as "+value+"\n");
	}
	
	public void verifyUserName(String firstName,String lastName,String memberType,String userName){
		String name;
		if(memberType.equals("New Member")){
		   name=firstName+" "+lastName;
		}
		else
		   name=userName;
//		wait.waitForPageToLoadCompletely();
		isElementDisplayed("btn_myAccount");
		Assert.assertTrue(element("btn_myAccount").getText().equals(name),"ASSERT FAILED: Username is not verified as "+name);
		logMessage("ASSERT PASSED: Username is verified as "+name);
	}
	
	public void clickOnACSWButton(String btnValue){
		isElementDisplayed("btn_acsw",btnValue);
		element("btn_acsw",btnValue).click();
		logMessage("STEP: Clicked on "+btnValue+" button\n");
	}
	
	public void navigateToAccountLink(String link){
		isElementDisplayed("btn_myAccount");
		element("btn_myAccount").click();
		isElementDisplayed("list_accountLink",link);
		element("list_accountLink",link).click();
		logMessage("STEP: "+link+" is clicked\n");
	}
	
	public void enterNewEmail(String editEmail,String email,int number){
		editEmail=editEmail.replace("test", "test"+number);
		isElementDisplayed("input_email");
		element("input_email").clear();
		element("input_email").sendKeys(editEmail);
		logMessage("STEP: Email value edited as "+editEmail+"\n");
		changedValues.put("Email", editEmail);
	}
	
	public void clickOnEmailSaveButton(String id){
		isElementDisplayed("btn_emailSave",id);
		element("btn_emailSave",id).click();
		logMessage("STEP: Clicked on Save button\n");
	}
	
	public void verifyEmailSavedMessage(String expectedMsg,String field){
		isElementDisplayed(field);
		Assert.assertEquals(element(field).getText().trim(),expectedMsg,"ASSERT FAILED: "+expectedMsg+" is not verified\n");
		logMessage("ASSERT PASSED: "+expectedMsg+" message is verified\n");
	}
	
	public void changePhoneNumber(String btnName,String memberType,String telephoneType){
		if(memberType.equals("New Member")){
			clickOnAddPhoneButton(btnName,"btn_addPhone");
			changedValues.put("TelephoneType","home");
		}
		else{
			if(telephoneType.equals("NoTelephoneNumber"))
				clickOnAddPhoneButton(btnName,"btn_addPhone");
			else
				clickOnChangePhoneButton("btn_changePhone",telephoneType);
			changedValues.put("TelephoneType",Character.toLowerCase(telephoneType.charAt(0))+telephoneType.substring(1));
		}
	}
	
	public void clickOnChangePhoneButton(String btnName,String telephoneType){
		isElementDisplayed(btnName,telephoneType);
		element(btnName,telephoneType).click();
		logMessage("STEP: Change Phone button is clicked\n");
	}
	
	public void changeAddress(String memberType,HashMap<String, String> map){
		if(memberType.equals("Existing Member")){
		isElementDisplayed("btn_ChangeAddress");
		element("btn_ChangeAddress").click();
		logMessage("STEP: Clicked on Change Address button\n");
		enterNewAddressDetails("Address-2",map.get("Address Line 1"));
		clickOnSaveAddressButton();
		changedValues.put("AddressType", element("btn_ChangeAddress").getAttribute("id"));
		changedValues.put("Address-2", map.get("Address Line 1"));
	  }
		else{
			clickOnAddPhoneButton("Work Add Address","btn_addAddress");
			enterNewAddress(map);
			changedValues.put("AddressType","work");
		}
			
	}
	
	public void clickOnAddPhoneButton(String btnName,String btnValue){
		isElementDisplayed(btnValue);
		element(btnValue).click();
		logMessage("STEP: "+btnName+" button is clicked\n");
	}
	
	public void editPhoneNumber(String phnNumber,String primaryPhone,String telephoneType){
		wait.hardWait(2);
		isElementDisplayed("inp_homePhone",telephoneType);
		element("inp_homePhone",telephoneType).clear();
		element("inp_homePhone",telephoneType).sendKeys(phnNumber);
		logMessage("STEP: Phone number is edited as "+phnNumber+"\n");
		if(primaryPhone.equals("Yes") && checkIfElementIsThere("chkbx_primary"))
			selectPrimaryCheckbox("chkbx_primary");
		changedValues.put("PhoneNumber", phnNumber);
	}
	
	public void selectPrimaryCheckbox(String checkbox){
		isElementDisplayed(checkbox);
		element(checkbox).click();
		logMessage("STEP: Mark as Primary checkbox is checked\n");
	}
	
	public void enterNewAddressDetails(String fieldId,String value){
		isElementDisplayed("inp_changeAddress",fieldId);
//		if(!element("inp_changeAddress",fieldId).getText().equals(""))
       		element("inp_changeAddress",fieldId).clear();
		element("inp_changeAddress",fieldId).sendKeys(value);
		logMessage("STEP: "+fieldId+" value is entered as "+value+"\n");
	}
	
	public void selectAddressState(String field,String value){
		isElementDisplayed("inp_changeAddress",field); 
		selectProvidedTextFromDropDown(element("inp_changeAddress",field), value);
		logMessage("STEP: "+field+" value is entered as "+value+"\n");
	}
	
	public void enterNewAddress(HashMap<String, String> map){
		enterNewAddressDetails("company",map.get("Company"));
		selectAddressState("country",map.get("Country"));  //stateTxtInput
		enterNewAddressDetails("City",map.get("City"));
		enterNewAddressDetails("Address-1",map.get("Address Line 1"));
		enterNewAddressDetails("Postal",map.get("Zip_Code"));
		selectAddressState("states",map.get("State")); 
		selectPrimaryCheckbox("chckbox_addressPrimary");
		clickOnSaveAddressButton();
		changedValues.put("Company", map.get("Company"));
		changedValues.put("Country", map.get("Country"));
		changedValues.put("City", map.get("City"));
		changedValues.put("Address Line 1", map.get("Expected_AddressIweb?"));
		changedValues.put("Zip_Code", map.get("Zip_Code"));
		changedValues.put("State", map.get("State"));
//		enterNewAddressDetails("stateTxtInput",map.get("State")); //stateTxtInput
//		changedValues.put("Address Line 1", map.get("Address Line 1"));
//		enterNewAddressDetails("country",map.get("Country"));
	}
	
	public void clickOnSaveAddressButton(){
		isElementDisplayed("btn_addressSave");
		element("btn_addressSave").click();
		logMessage("STEP: Save Address button is clicked\n");
	}
	
	public void verifySaveAddressMessage(String message){
		isElementDisplayed("msg_addressSave");
		Assert.assertEquals(element("msg_addressSave").getText().trim(),message,"ASSERT PASSED: "+message+" is not verified\n");
		logMessage("ASSERT PASSED: "+message+" is verified\n");
	}
	
	public void verifyPriamryImageIsPresent(String image,String field){
		wait.hardWait(3);
		isElementDisplayed(image);
		logMessage("ASSERT PASSED: Verified "+field+" is set as primary\n" );
	}
	
	public void selectDontWantToBeACSMember(String member){
		if(member.equals("No")){
			isElementDisplayed("radioBtn_acsMember","out");
			element("radioBtn_acsMember","out").click();
			logMessage("STEP: Does not want to be member of the ACS network option is selected \n");
			clickOnSaveButton("Save");
		}
		else{
			isElementDisplayed("radioBtn_acsMember","in");
//			if(element("radioBtn_acsMember","in").isSelected())
			element("radioBtn_acsMember","in").click();
			logMessage("STEP: want to be member of the ACS network option is selected \n");
			clickOnSaveButton("Save");
		}
		changedValues.put("ACS Member", member);
	}
	
	public void clickOnSaveButton(String btnName){
		isElementDisplayed("btn_Save",btnName);
		element("btn_Save",btnName).click();
		logMessage("STEP: Clicked on "+btnName+" button\n");
	}
	
	public void enterLoginDetails(String loginId,String value){
//		wait.waitForPageToLoadCompletely();
		isElementDisplayed("inp_loginDetails",loginId);
		element("inp_loginDetails",loginId).sendKeys(value);
		logMessage("STEP: "+loginId+" is entered as "+value+"\n");
	}
	
	public void clickOnSideTab(String tabName){
		isElementDisplayed("tab_myAccount",tabName);
		element("tab_myAccount",tabName).click();
		logMessage("STEP: "+tabName+" side tab is clicked\n");
	}
	
	public void verifyTechnicalDivisions(List<String> techDivisions){
		if(techDivisions.size()>0){
		isElementDisplayed("list_techincalDivisions");
		String ewebDivisions=element("list_techincalDivisions").getText().trim();
		for(int i=0;i<techDivisions.size();i++){
			Assert.assertTrue(ewebDivisions.contains(techDivisions.get(i)),"ASSERT FAILED: Technical Division "+techDivisions.get(i) +" is not verified on Technical divisions page\n");
			logMessage("ASSERT PASSED: Technical Division "+techDivisions.get(i) +" is verified on Technical divisions page\n");
		}
	 }
		else
			logMessage("STEP: Technical divisions are not assigned to the member\n");
	}
	
	public void verifyAllMyApplicationsArePresent(String myApplications[]){
		for(String myApp: myApplications){
			isElementDisplayed("list_myApplications",myApp);
			logMessage("ASSERT PASSED: Verified "+myApp+" is present under My Applications page\n");
		}
	}
	
	public String getMemberCustomerId(){
		isElementDisplayed("txt_customerId");
		String customerId=element("txt_customerId").getText().trim().replace("Customer Number", "");
		customerId=customerId.replace("Member Number", "");
		logMessage("STEP: Customer Id is "+customerId);
		return customerId;
	}
	
	public void clickOnCloseButton(){
		isElementDisplayed("btn_close");
		element("btn_close").click();
		logMessage("STEP: Clicked on close button\n");
	}
	
	public void handleFeedbackForm(){
		wait.hardWait(10);
		if(checkIfElementIsThere("form_surveyFeedback")){
		isElementDisplayed("btn_NoThanks");
		element("btn_NoThanks").click();
		logMessage("STEP: Clicked on No, Thanks button on Feedback form\n");
	  }
		else
			logMessage("STEP: Feedback form does not appear\n");
	}
	
	public Map<String,String> getChangedValues(){
		System.out.println("--------"+changedValues);
		return changedValues;
	}
	
	public void enterNewPassword(String oldPassword, String newPassword){
        wait.hardWait(2);
		enterNewAddressDetails("idOldPwd", oldPassword);
        enterNewAddressDetails("idNewPwd", newPassword);
        enterNewAddressDetails("idConfNewPwd", newPassword);
        clickOnSaveButton("Save Changes");
   	    verifyEmailSavedMessage(getYamlValue("ACS_MyAccount.passwordChangedMessage"),"msg_passwordChanged");
		navigateToAccountLink("Log Out");
	}
}
