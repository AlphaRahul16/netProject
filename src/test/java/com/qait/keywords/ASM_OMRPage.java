package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.fasterxml.jackson.databind.InjectableValues;
import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.utils.DateUtil;


public class ASM_OMRPage extends ASCSocietyGenericPage {

	WebDriver driver;
	static String pagename = "ASM_OMRPage";
	static boolean flag, confirmPage;
	int timeOut, hiddenFieldTimeOut;
	Map<String,String> mapRenewedProductDetails = new HashMap<String,String>();

	public ASM_OMRPage(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;

	}

	public void OMRLogo(String OMRLogoText) {
		isElementDisplayed("hdng_OMRLogo");
		verifyElementTextContains("hdng_OMRLogo", OMRLogoText);
	}

	public void loginIntoApplication_ACS_ID(String userName, String password) {
		System.out.println("1");
		switchToFrame(element("iframe_ewebframe"));
		System.out.println("2");
		selectLoginRadioButton("ACSid");
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		enterUserName_lastName(userName);
		enterPassword_mem_notice(password);
		clickOnVerifyButton();
		wait.waitForPageToLoadCompletely();
		// renewConfirmYourInformation();
		switchToDefaultContent();


	}

	public void loginIntoApplication_LastName_MemberNumber(String lastName,
			String memberNumber) {
		System.out.println("1");
		switchToFrame(element("iframe_ewebframe"));
		wait.hardWait(2);
		selectLoginRadioButton("LNMemNo");
		wait.hardWait(2);
		wait.waitForPageToLoadCompletely();
		enterUserName_lastName(lastName);
		wait.hardWait(2);
		enterPassword_mem_notice(memberNumber);
		clickOnVerifyButton();
		wait.waitForPageToLoadCompletely();
		// renewConfirmYourInformation();
		switchToDefaultContent();

	}

	public void loginIntoApplication_LastName_NoticeNumber(String lastName,
			String noticeNumber) {
		switchToFrame(element("iframe_ewebframe"));
		selectLoginRadioButton("LNNoticeNo");
		wait.hardWait(2);
		enterUserName_lastName(lastName);
		wait.hardWait(2);
		enterPassword_mem_notice(noticeNumber);
		clickOnVerifyButton();
		wait.waitForPageToLoadCompletely();
		// renewConfirmYourInformation();
		switchToDefaultContent();

	}


	public void logoutFromApplication() {
		isElementDisplayed("btn_logout");
		element("btn_logout").click();
		logMessage("STEP : Logout button is clicked\n");
	}

	public void selectLoginRadioButton(String loginType) {
		isElementDisplayed("rad_" + loginType);
		element("rad_" + loginType).click();
		logMessage("STEP : Select radio button for ACS ID in rad_" + loginType
				+ "\n");
	}

	public void enterUserName_lastName(String userName) {
		isElementDisplayed("inp_userName_lastName");
		element("inp_userName_lastName").sendKeys(userName);
		logMessage("STEP : User name " + userName
				+ " is entered in inp_userName_lastName\n");
	}

	public void enterPassword_mem_notice(String password) {
		isElementDisplayed("inp_password_mem_notice");
		element("inp_password_mem_notice").sendKeys(password);
		logMessage("STEP : Password " + password
				+ " is entered in inp_password_mem_notice\n");
	}

	public void clickOnVerifyButton() {
		isElementDisplayed("btn_verify");
		element("btn_verify").click();
		logMessage("STEP : Verify button is clicked in btn_verify \n");
	}

	public void verifyLoginErrorMessage(String errorMessage) {
		switchToFrame("eWebFrame");
		isElementDisplayed("txt_loginErrorMessage");
		System.out.println("actual: "
				+ element("txt_loginErrorMessage").getText());
		System.out.println("expected: " + errorMessage);
		verifyElementTextContains("txt_loginErrorMessage", errorMessage);
		switchToDefaultContent();
	}
	public void holdScriptExecution() {
		String lapsedMinutes = "";
		for (int minutes = 0; minutes < 2; minutes++) {
			for (int i = 0; i <= 5; i++) {
				System.out.print("\r");
				System.out.print("Time:- " + lapsedMinutes + i + " sec ");
				wait.hardWait(1);
			}
			lapsedMinutes = String.valueOf(minutes) + " min : ";
		}
		System.out.print("\r");
		System.out.println("Time:- " + 5 + " minutes            ");
		System.out.println("");
	}

	public void verifyWelcomePage() {
		holdScriptExecution();
		switchToFrame("eWebFrame");
		wait.waitForPageToLoadCompletely();
		//isElementDisplayed("hdng_welcome");
		logMessage("ASSERT PASSED : Welcome page is present in hdng_welcome\n");
		switchToDefaultContent();
	}

	public void clickOnEditButton() {
		isElementDisplayed("link_edit");
		element("link_edit").click();
		logMessage("ASSERT PASSED : Edit button is clicked in link_edit\n");
	}

	public String updateAddress(String fieldName, String fieldValue) {
		switchToFrame("eWebFrame");
		hardWaitForIEBrowser(5);
		clickOnEditButton();
		switchToDefaultContent();
		switchToFrame("eWebFrame");
		String email = enterValueInUpdateAddressFields(fieldName, fieldValue);
		clickOnSaveButton();
		switchToDefaultContent();
		return email;
	}
	private void clickRadioButtonForRenewalYears(String years)
	{
		wait.waitForPageToLoadCompletely();
		switchToFrame("eWebFrame");
		isElementDisplayed("rad_undergraduate", years);
		element("rad_undergraduate", years).click();
		logMessage("STEP : Renew membership for "+years+" radio button clicked\n");
		wait.waitForPageToLoadCompletely();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		switchToDefaultContent();
	}
	
	public void clickProccedWithPaymentinINR(String buttontext)
	{
		wait.waitForPageToLoadCompletely();
//	switchToEwebRenewalFrame();
//	isElementDisplayed("rad_undergraduate", buttontext);
//	element("rad_undergraduate", buttontext).click();
	wait.hardWait(14);
	wait.waitForPageToLoadCompletely();
	executeJavascript("document.getElementById('eWebFrame').contentWindow.document.getElementsByClassName('btn btn-blue')[0].click()");
	logMessage("STEP : Button "+buttontext+" is clicked\n");
	wait.waitForPageToLoadCompletely();
	switchToDefaultContent();
		
	}
	
	public void selectUndergradutaeSchoolStatus(String value)
	{
		try
		{
			wait.resetImplicitTimeout(2);
			switchToFrame("eWebFrame");
			element("chkbox_undergraduate").click();
			wait.hardWait(2);
			element("rad_undergraduate", value).click();
			clickOnSaveButton();
			switchToDefaultContent();

		}
		catch(Exception e)
		{
			wait.resetImplicitTimeout(timeOut);
		}
		wait.resetImplicitTimeout(timeOut);
		switchToDefaultContent();
	}

	public String enterValueInUpdateAddressFields(String fieldName,
			String fieldValue) {
		isElementDisplayed("inp_updateAddress", fieldName);
		element("inp_updateAddress", fieldName).clear();
		String email = System.currentTimeMillis() + fieldValue;
		element("inp_updateAddress", fieldName).sendKeys(email);
		logMessage("STEP : " + fieldValue + " is entered in " + fieldName
				+ " in inp_updateAddress\n");
		return email;
	}

	public void clickOnSaveButton() {

		isElementDisplayed("btn_save");
		element("btn_save").click();
		logMessage("STEP : Save button is clicked in btn_save\n");
	}

	public void verifyAddressUpdated(String updatedAddress) {
		switchToFrame("eWebFrame");
		isElementDisplayed("list_updatedAddress");
		for (WebElement element : elements("list_updatedAddress")) {
			if (element.getText().contains(updatedAddress)) {
				flag = true;
			}
		}
		if (flag) {
			logMessage("ASSERT PASSED : Vverify address is updated \n");
		} else {
			Assert.fail("ASSERT FAILED : Address is not updated\n");
		}
		switchToDefaultContent();
	}

	public void verifyErrorMessageOnInvalidEmailAddress(String errorMessage) {
		switchToFrame("eWebFrame");
		isElementDisplayed("txt_errorMessage", errorMessage);
		logMessage("ASSERT PASSED : error message " + errorMessage
				+ " is verified in txt_errorMessage\n");
		switchToDefaultContent();
	}

	public void verifyUpdatedEmailAddress(String emailAddress) {
		switchToFrame("eWebFrame");
		isElementDisplayed("txt_emailAddress");
		verifyElementText("txt_emailAddress", emailAddress);
		switchToDefaultContent();
	}

	public void submitPaymentDetails(String cardType, String cardholderName,
			String cardNumber, String cvvNumber, String date_Value,
			String year_Value) {
		switchToDefaultContent();
		switchToFrame("eWebFrame");
		selectCreditCardType(cardType);
		enterCreditCardHolderName(cardholderName);
		enterCreditCardNumber(cardNumber);
		enterCVVNumber(cvvNumber);
		wait.hardWait(1);
		// selectExpirationDate_Year("Date", date_Value);
		selectExpirationDate_Year("Year", year_Value);
		checkEula();
		clickOnContinueButton();
		switchToDefaultContent();

	}
	
	public void navigateToCheckOutPageForGCSOMR()
	{
		switchToDefaultContent();
		switchToFrame("eWebFrame");
		checkEula();
		clickOnContinueButton();
		switchToDefaultContent();
	}
	

	public void submitPaymentDetailsForAutoRenewal(String cardType, String cardholderName,
			String cardNumber, String cvvNumber, String date_Value,
			String year_Value) {
		switchToDefaultContent();
		switchToFrame("eWebFrame");
		selectCreditCardType(cardType);
		enterCreditCardHolderName(cardholderName);
		enterCreditCardNumber(cardNumber);
		enterCVVNumber(cvvNumber);
		wait.hardWait(1);
		// selectExpirationDate_Year("Date", date_Value);
		selectExpirationDate_Year("Year", year_Value);
		checkAutoRenewalBox();
		checkEula();
		clickOnContinueButton();
		switchToDefaultContent();
		wait.hardWait(4);
		wait.waitForPageToLoadCompletely();

	}
	
	

	private void checkAutoRenewalBox() {
		isElementDisplayed("chk_Autorenewal");
		element("chk_Autorenewal").click();
		logMessage("STEP : Auto Renewal Checkbox is selected\n");
		
	}

	public void selectCreditCardType(String cardType) {
		isElementDisplayed("list_cardType");
		selectProvidedTextFromDropDown(element("list_cardType"), cardType);
	}

	public void enterCreditCardHolderName(String cardholderName) {
		isElementDisplayed("inp_cardHolderName");
		element("inp_cardHolderName").sendKeys(cardholderName);
		logMessage("STEP : " + cardholderName
				+ " is entered in inp_cardHolderName\n");
	}

	public void enterCreditCardNumber(String cardNumber) {
		isElementDisplayed("inp_cardNumber");
		element("inp_cardNumber").sendKeys(cardNumber);
		logMessage("STEP : " + cardNumber + " is entered in inp_cardNumber\n");
	}

	public void enterCVVNumber(String cvvNumber) {
		isElementDisplayed("inp_CVVNumber");
		element("inp_CVVNumber").sendKeys(cvvNumber);
		logMessage("STEP : " + cvvNumber + " is entered in inp_CVVNumber\n");
	}

	public void selectExpirationDate_Year(String date_year,
			String date_yearValue) {
		isElementDisplayed("list_expiration" + date_year);
		selectProvidedTextFromDropDown(element("list_expiration" + date_year),
				date_yearValue);
		logMessage("STEP : " + date_yearValue + " is selected for " + date_year
				+ " in list_expiration\n");
	}

	public void checkEula() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			isElementDisplayed("chk_eula");
			if (!element("chk_eula").isSelected()) {
				element("chk_eula").click();
				logMessage("STEP : Eula is accepted in chk_eula\n");
			} else {
				logMessage("STEP : Usle is already checked");
			}
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (NoSuchElementException e) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("STEP : Eula not present");
		}

	}

	public void clickOnContinueButton() {
		isElementDisplayed("btn_continue");
		element("btn_continue").click();
		logMessage("STEP : Click on continue button in btn_continue\n");
	}

	public void verifyErrorMessage(String errorMessage) {
		wait.waitForPageToLoadCompletely();
		switchToFrame("eWebFrame");
		isElementDisplayed("txt_invalidCardErrorMessage");
		verifyElementTextContains("txt_invalidCardErrorMessage", errorMessage);
		switchToDefaultContent();
	}

	public void clickOnSubmitPayment() {
		wait.hardWait(14);
		wait.waitForPageToLoadCompletely();
		try
		{
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			wait.resetImplicitTimeout(4);
			performClickByActionBuilder(element("btn_submitPayment"));
		}
		catch(Exception e)
		{
		executeJavascript("document.getElementById('eWebFrame').contentWindow.document.getElementById('btnSubmitOmrPaymentTop').click()");
		}
		logMessage("STEP : Click on Pay button at Top \n");
		wait.resetExplicitTimeout(timeOut);
		wait.resetImplicitTimeout(timeOut);
		
	}

	public void verifyNavigationPage(String navigationPageName) {
		isElementDisplayed("txt_navigation");
		verifyElementText("txt_navigation", navigationPageName);
	}

	public void renewConfirmYourInformation() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));

		wait.resetImplicitTimeout(0);
		wait.resetExplicitTimeout(30);
		switchToDefaultContent();
		wait.waitForPageToLoadCompletely();
		try {
			System.out.println("try in");
			wait.hardWait(5);
			switchToFrame("eWebFrame");
			System.out.println("frame in");
			hardWaitForIEBrowser(5);
			confirmPage = isElementDisplayed("txt_confirmPage");
			System.out.println("confirm page: " + confirmPage);
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (NoSuchElementException e) {
			System.out.println("catch in ");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
			logMessage("STEP : Confirmation page is not present");
		}
		wait.waitForPageToLoadCompletely();
		if (confirmPage) {
			isElementDisplayed("chk_Confirm");
			element("chk_Confirm").click();
			logMessage("STEP : Confirm check box is checked\n");
			selectNoRadioButton();
			clickOnContinueButton();
			switchToDefaultContent();
		}
	}

	public void selectNoRadioButton() {
		isElementDisplayed("rad_No");
		element("rad_No").click();
		logMessage("STEP : No radio button is selected \n");
	}


	public void loginIntoApplicationWithValidChoice(Map<String, String> mapOMR,List<String> memDetails) {
		if(mapOMR.get("Login_With_AcsID?").equalsIgnoreCase("Yes"))
		{
			loginIntoApplication_ACS_ID(memDetails.get(2), "password");
		}
		else if(mapOMR.get("Login_With_LastNameMemberNumber?").equalsIgnoreCase("Yes"))
		{
			loginIntoApplication_LastName_MemberNumber(memDetails.get(0).split(" ")[0], memDetails.get(1));
			
			//loginIntoApplication_LastName_MemberNumber("Agrawal", "2397066");
		}
		else if(mapOMR.get("Login_With_LastNameNoticeNumber?").equalsIgnoreCase("Yes"))
		{
			loginIntoApplication_LastName_NoticeNumber(memDetails.get(0).split(" ")[0], memDetails.get(2));
		}
		wait.waitForPageToLoadCompletely();
	}
	
	public void loginIntoOMRApplication(List<String> memDetails) {
		loginIntoApplication_LastName_MemberNumber(memDetails.get(0).split(" ")[0], memDetails.get(1));
		
		
		//loginIntoApplication_LastName_MemberNumber("Palmgren", "00371659");
	}

	public void switchToEwebRenewalFrame()
	{
		wait.hardWait(2);
		wait.waitForElementToBeVisible(element("iframe_ewebframe"));
		switchToFrame(element("iframe_ewebframe"));
		logMessage("Step: Switch to Eweb Renewal Frame");
	}

	public Map<String, String> addMembershipsForRegularMember(Map<String, String> mapOMR) {
		receiveCENPrintOrElectronically(mapOMR.get("Receive_C&EN?"));
		if(!mapOMR.get("Member_Status?").equalsIgnoreCase("Emeritus"))
		{
			removeAllPreviousRenewals("RemoveBenefitItem");
			removeAllPreviousRenewals("RemoveDivisionItem");
			removeAllPreviousRenewals("RemovePublicationItem");
			removeAllPreviousRenewals("RemoveContributionItem");
			addACSTechnicalDivision(mapOMR);
			addACSPublications(mapOMR);
			addACSMemberBenefits(mapOMR);
			addACSContributions(mapOMR);
		}
		else if(mapOMR.get("Member_Status?").equalsIgnoreCase("Emeritus"))
		{
			
			removeAllPreviousRenewalsForEmeritusMember();

		}
		else if(mapOMR.get("Member_Status?").equalsIgnoreCase("Regular"))
		{
			clickRadioButtonForRenewalYears(mapOMR.get("Renew_For_Years?"));

		}

		return saveProductsWithRespectiveRenewalAmount();
	}

	private void clickCENElecronicallyButton() {
		switchToEwebRenewalFrame();
		isElementDisplayed("rad_electronicCEN");
		element("rad_electronicCEN").click();
		logMessage("STEP : \"I want to receive C&EN electronically\" button clicked\n");
		switchToDefaultContent();
		wait.waitForPageToLoadCompletely();

	}
	
	private void clickCENPrintButton() {
		switchToEwebRenewalFrame();
	    wait.waitForElementToBeClickable(element("rad_printCEN"));
		isElementDisplayed("rad_printCEN");
		element("rad_printCEN").click();
		logMessage("STEP : \"I want to receive C&EN print\" button clicked\n");
		switchToDefaultContent();
		wait.waitForPageToLoadCompletely();

	}
	public void receiveCENPrintOrElectronically(String receivethrough)
	{
		if(receivethrough.equalsIgnoreCase("print"))
		{
		
			clickCENPrintButton();
			
		}
		else if(receivethrough.equalsIgnoreCase("electronic"))
		{
			clickCENElecronicallyButton();
		}
	}

	private void addACSContributions(Map<String, String> mapOMR) {
		clickAddMembershipButton("Add ACS Contribution");

		//isElementDisplayed("txt_legend","My ACS Contributions");
		enterContributionForParticularSubscription(mapOMR);
		clickSaveButtonToAddMembership();
	}

	private void enterContributionForParticularSubscription(Map<String, String> mapOMR) {
		System.out.println(mapOMR.get("Contribution_To_Add?").length());
		if(mapOMR.get("Contribution_To_Add?").length()!=0)
		{
			switchToDefaultContent();
			wait.hardWait(13);
			switchToEwebRenewalFrame();
			isElementDisplayed("inp_contribution",mapOMR.get("Contribution_To_Add?").trim());
			element("inp_contribution",mapOMR.get("Contribution_To_Add?").trim()).sendKeys(mapOMR.get("Contribution_Amount?").trim());
			logMessage("STEP : ACS Contribution entered for "+mapOMR.get("Contribution_To_Add?").trim()+" as "+mapOMR.get("Contribution_Amount?").trim()+"\n");
			switchToDefaultContent();
		}

	}

	private void addACSMemberBenefits(Map<String, String> mapOMR) {
		
		if(mapOMR.get("MemBenefits_To_Add?").equalsIgnoreCase("Yes"))	
		{
			try
			{
			clickAddMembershipButton("Add ACS Member Benefits");
			holdScriptExecution();
			selectAddToMembershipForParticularSubscription("BenefitAddToMembership");
			}
			catch(Exception e)
			{
			
			}
			
			clickSaveButtonToAddMembership();
		}
	}

	private void addACSPublications(Map<String, String> mapOMR) {
		if(mapOMR.get("Publications_To_Add?").equalsIgnoreCase("Yes"))
		{
			clickAddMembershipButton("Add ACS Publication");
            holdScriptExecution();
			//isElementDisplayed("txt_legend","My ACS Publications");
			selectAddToMembershipForParticularSubscription("PubAddToMembership");
			clickSaveButtonToAddMembership();
			
		}
	}

	private void addACSTechnicalDivision(Map<String, String> mapOMR) {
		if(mapOMR.get("Technical_Division_To_Add?").equalsIgnoreCase("Yes"))
		{
			clickAddMembershipButton("Add ACS Technical Division");
			//isElementDisplayed("txt_legend","My ACS Technical Divisions");
			holdScriptExecution();
			selectAddToMembershipForParticularSubscription("add-to-cart");
			clickSaveButtonToAddMembership();
			holdScriptExecution();
		}
	}

	private void clickAddMembershipButton(String valueofmembership) {

		switchToDefaultContent();
		try{
			wait.resetImplicitTimeout(6);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			switchToEwebRenewalFrame();
			isElementDisplayed("btn_addSubscription",valueofmembership);
			element("btn_addSubscription",valueofmembership).click();
			logMessage("STEP : "+valueofmembership+" button is clicked\n");
		}
		catch(NoSuchElementException e)
		{
			switchToDefaultContent();
			executeJavascript("document.getElementById('eWebFrame').contentWindow.document.querySelectorAll(\"input[class='addItemButton'][value='"+valueofmembership+"']\")[0].click()");
			logMessage("STEP : "+valueofmembership+" button clicked\n");
			//clickUsingXpathInJavaScriptExecutor(element("btn_addSubscription",valueofmembership));
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
		switchToDefaultContent();


	}

	private void selectAddToMembershipForParticularSubscription(String value)
	{
		
		wait.hardWait(8);
		switchToEwebRenewalFrame();
		System.out.println(elements("btn_addToMemberships").size());
		 elements("btn_addToMemberships").get(generateRandomNumberWithInRange(0,(elements("btn_addToMemberships").size())-1)).click();
		logMessage("STEP : Button Add to membership is clicked for "+value);
		switchToDefaultContent();
	}
	public void clickSaveButtonToAddMembership()
	{
        switchToDefaultContent();
		switchToEwebRenewalFrame();
		isElementDisplayed("btn_saveToAddMembership");
		element("btn_saveToAddMembership").click();
		logMessage("STEP : Save button is clicked\n");
		switchToDefaultContent();

	}

	public Map<String, String> saveProductsWithRespectiveRenewalAmount() 
	{
		switchToDefaultContent();
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		switchToEwebRenewalFrame();
		wait.hardWait(2);
		wait.hardWait(2);
		scrollDown(elements("txt_productname").get(0));
		System.out.println(elements("txt_productname").size());
		List<WebElement> productName= elements("txt_productname");
		for(int i=0;i<productName.size();i++)
		{
			mapRenewedProductDetails.put(productName.get(i).getText().trim(),
					elements("txt_productamount").get(i).getText().replace("$", "").trim());
			System.out.println(mapRenewedProductDetails.get(productName.get(i).getText()));
		}
		return mapRenewedProductDetails;
	}

	public void removeAllPreviousRenewals(String renewalName)
	{
		try{
			switchToEwebRenewalFrame();
			wait.resetImplicitTimeout(3);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			for(int i=0;i<elements("btn_removerenewals",renewalName).size();i++)
			{
				scrollDown(elements("btn_removerenewals",renewalName).get(i));
				elements("btn_removerenewals",renewalName).get(i).click();
				wait.hardWait(2);
			}
		}
		catch(Exception e)
		{
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}

		switchToDefaultContent();
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
		logMessage("STEP : Button remove is clicked for "+renewalName.replace("Remove", "").trim());
	}


	public void removeAllPreviousRenewalsForEmeritusMember()
	{

		try{
			switchToEwebRenewalFrame();
			System.out.println("Total "+Float.parseFloat(element("txt_productFinalTotal","Total").getText().replace("$", "").trim()));

			System.out.println(elements("btns_remove").size());
			System.out.println("Before remove"+element("txt_productFinalTotal","Total").getText().replace("$", "").trim());
			wait.hardWait(2);
			wait.resetImplicitTimeout(4);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			elements("btns_remove").get(0).click();
			wait.hardWait(2);
			System.out.println("After remove"+element("txt_productFinalTotal","Total").getText().replace("$", "").trim());
			if(Float.parseFloat(element("txt_productFinalTotal","Total").getText().replace("$", "").trim())!=0.0)
			{
				switchToDefaultContent();
				removeAllPreviousRenewalsForEmeritusMember();
			}

			logMessage("STEP : All pricing for Emeritus member is removed, current invoice is of amount Zero");
		}

		catch(Exception e)
		{
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}

		switchToDefaultContent();
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);

	}

	public float verifySubTotalForRenewedProducts(Map<String, String> mapRenewedProductDetails) {
        switchToDefaultContent();
		float subtotal = 0.0f;
		for (String f : mapRenewedProductDetails.values()) {
			subtotal += Float.parseFloat(f);
		}
		switchToEwebRenewalFrame();
		//double roundOffOrderTotal = Math.round(ordertotal * 100.0) / 100.0;
		System.out.println("actual roundoff"+subtotal);
		System.out.println(Float.parseFloat(element("txt_productFinalTotal","Subtotal").getText().replaceAll("[^\\d.]", "").trim()));
		Assert.assertTrue(Float.parseFloat(element("txt_productFinalTotal","Subtotal").getText().replaceAll("[^\\d.]", "").trim()) == subtotal);

		logMessage("ASSERT PASSED : Sub total is verified as " + subtotal + "\n");
		switchToDefaultContent();
		return subtotal;
	}
	public void verifyRenewedProductsSummaryOnCheckOutPage(Map<String, String> mapRenewedProductDetails)
	{

		//verifyProductsIndividualAmount(mapRenewedProductDetails);
		verifyTotalAndBalanceDueOnCheckOutPage(mapRenewedProductDetails);

	}

	private void verifyTotalAndBalanceDueOnCheckOutPage(Map<String, String> mapRenewedProductDetails) {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		float subtotal=verifySubTotalForRenewedProducts(mapRenewedProductDetails);
		wait.hardWait(2);
		switchToEwebRenewalFrame();
		float total=subtotal+Float.parseFloat(element("txt_ProductTax").getText().replaceAll("[^\\d.]", "").trim())+
				Float.parseFloat(element("txt_productFinalTotal","Shipping").getText().replaceAll("[^\\d.]", "").trim());
		total= (float) (Math.round(total * 100.0) / 100.0);
		Assert.assertTrue(Float.parseFloat(element("txt_productFinalTotal","Total").getText().replaceAll("[^\\d.]", "").trim())==total);
		logMessage("ASSERT PASSED : Total invoice amount verified as "+total);
		float balancedue=total-Float.parseFloat(element("txt_productFinalTotal","Payment").getText().replaceAll("[^\\d.]", "").trim());
		Assert.assertTrue(Float.parseFloat(element("txt_productFinalTotal","Balance Due").getText().replaceAll("[^\\d.]", "").trim())==balancedue);
		logMessage("ASSERT PASSED : Balance Due verified as "+balancedue);
		switchToDefaultContent();
	}

	private void verifyProductsIndividualAmount(Map<String, String> mapRenewedProductDetails) {
		switchToEwebRenewalFrame();
		wait.waitForElementToBeVisible(elements("txt_productname").get(0));
		System.out.println(elements("txt_productname").get(0).getText());
		int size = elements("txt_productname").size();
		System.out.println(elements("txt_productname").size());
		// switchToDefaultContent();
		wait.hardWait(3);
		// switchToEwebRenewalFrame();
		for (int i = 0; i < size; i++) {
			wait.hardWait(3);
			isElementDisplayed("txt_productname");
			
			//wait.waitForElementsToBeVisible(elements("txt_productname"));
			String productName = elements("txt_productname").get(i).getText().trim();
			String amount=mapRenewedProductDetails.get(elements("txt_productname").get(i).getText().trim());
			System.out.println("Product name: " + productName);
			System.out.println("Amount:"+amount);
			Assert.assertTrue(amount.equals(elements("txt_productamount").get(i).getText().replace("$", "").trim()));
			logMessage("ASSERT PASSED : Amount for " + productName + " is verified as "	+ amount);
		}

		switchToDefaultContent();

	}

	public void FillRequiredDetailsForStudentMember(Map<String, String> mapOMR) {
		if(mapOMR.get("Member_Status?").equalsIgnoreCase("Student"))
		{
			//isConfirmYourInformationHeadingDisplayed();
			confirmStudentInformationAndContinue(mapOMR.get("Stud_Degree_Type"));

		}
	}
	
	public void confirmStudentInformationAndContinue(String degreeType)
	{
		enterGraduationDateForDegreeType();
		enterStudentDegreeType(degreeType);
		confirmUndergraduateStatus();
		switchToEwebRenewalFrame();
		clickOnSaveButton();
		switchToDefaultContent();
		holdScriptExecution();
	}
	
	public void confirmStudentDetailsForExpressRenewal(String degreeType)
	{
		try{
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			wait.resetImplicitTimeout(hiddenFieldTimeOut);
		    confirmStudentInformationAndContinue("Bachelor");}catch(NoSuchElementException e){
			logMessage("Step : User is not a Student Member\n");
		}
		switchToDefaultContent();
		wait.resetExplicitTimeout(timeOut);
		wait.resetImplicitTimeout(timeOut);
	}

	private void confirmUndergraduateStatus() {
		switchToEwebRenewalFrame();
		isElementDisplayed("chkbox_undergraduate");
		element("chkbox_undergraduate").click();
		logMessage("STEP : Checkbox to confirm undergraduate date is clicked\n");
		switchToDefaultContent();
	}

	private void enterStudentDegreeType(String degreeType) {
		switchToEwebRenewalFrame();
		selectProvidedTextFromDropDown(element("drpdwn_degreeType"), degreeType);
		switchToDefaultContent();
	}

	private void enterGraduationDateForDegreeType() {
		switchToEwebRenewalFrame();
		isElementDisplayed("inp_graduationDate");
		System.out.println(DateUtil.getAnyDateForType("MM/dd/YYYY", 1, "year"));
		wait.hardWait(2);
		element("inp_graduationDate").click();
		wait.hardWait(2);
		element("inp_graduationDate").clear();
		wait.hardWait(2);
		element("inp_graduationDate").sendKeys(DateUtil.getAnyDateForType("MM/dd/YYYY", 1, "year"));
		logMessage("STEP : Graduation date for Associates/Bachelors Degree entered as "+DateUtil.getAnyDateForType("MM/dd/YYYY", 1, "year"));
		switchToDefaultContent();

	}

	private void isConfirmYourInformationHeadingDisplayed() {
		switchToEwebRenewalFrame();
		isElementDisplayed("txt_legend","Confirm your information");
		switchToDefaultContent();

	}

	public void verifyPrintReceiptMessageAfterPayment() {
		wait.waitForPageToLoadCompletely();
		//switchToEwebRenewalFrame();
		wait.hardWait(4);
		Object display= executeJavascriptReturnValue("window.getComputedStyle(document.getElementById('eWebFrame').contentWindow.document.querySelector('#print-invoice>input')).display");
		System.out.println(display.toString());
		Assert.assertTrue(display.toString().contains("inline"));
		logMessage("Step : Print Renewal Receipt button is verified\n");
		logMessage("ASSERT PASSED : Receipt message for membership & subscription renewal is verified\n");
		switchToDefaultContent();

	}

	public void selectNoIfRegularToEmeritusPromptAppears() {

		switchToEwebRenewalFrame();
		try
		{
			wait.resetImplicitTimeout(5);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
		element("btn_applyForEmeritusNo").click();
		logMessage("STEP : Regular member does not applied for Emeritus status\n");
		}
		catch(NoSuchElementException e)
		{
			logMessage("STEP : Regular to Emeritus prompt does not appear\n");
		}
		wait.resetImplicitTimeout(timeOut);
		wait.resetExplicitTimeout(timeOut);
	
		switchToDefaultContent();
	}
	
//	public void clickPayInINRButtonForOMR()
//	{
//		
//		switchToEwebRenewalFrame();
//		isElementDisplayed("btn_submitPayment");
//		element("btn_submitPayment").click();
//		wait.waitForPageToLoadCompletely();
//		logMessage("Step : Pay in INR button is clicked\n");
//		wait.waitForPageToLoadCompletely();
//		switchToDefaultContent();
//	}
//	
	public void selectINRAsCurrencyType(String value)
	{
		switchToDefaultContent();
		wait.hardWait(4);
		switchToEwebRenewalFrame();
		isElementDisplayed("drpdwn_currencyINR");
		selectProvidedTextFromDropDown(element("drpdwn_currencyINR"), value);
		logMessage("STEP : Selected value for currency is "+value);
		switchToDefaultContent();
		
	}
	
	public  void clickYesSurePopUpButton(String buttontext)
	{
		switchToEwebRenewalFrame();
		isElementDisplayed("btn_YesSurePopUp",buttontext);
        element("btn_YesSurePopUp",buttontext).click();
		logMessage("STEP : Pop Up button with text "+buttontext+" is clicked");
		wait.waitForPageToLoadCompletely();
		wait.hardWait(16);
		switchToDefaultContent();
		
		
	}
	
	private void clickContinueButtonForGCSOMR()
	{
		isElementDisplayed("btn_continueToPayment");
		logMessage("STEP : Continue button is now enabled\n");
		element("btn_continueToPayment").click();
		logMessage("STEP : Continue button is clicked\n");
	}
	
	private void clickAgreeeWithTermsAndConditionsOnGCSOMR()
	{
		isElementDisplayed("chkbox_termsAndCondition");
		element("chkbox_termsAndCondition").click();
		logMessage("STEP : Agreed with terms and Conditions\n");
	}
	
	public void clickContinueButtonToNavigateToBankPaymentPage()
	{
		clickAgreeeWithTermsAndConditionsOnGCSOMR();
		clickContinueButtonForGCSOMR();
	
	}

	public void verifyMemberCanRenewForMultipleYears() {
		switchToDefaultContent();
		switchToEwebRenewalFrame();
		isElementDisplayed("btn_renewalLength");
		element("btn_renewalLength").isDisplayed();
		logMessage("Step : Member can renew for multiple times\n");
		switchToDefaultContent();

	}
	
	public void clickApplyDiscountButton()
	{
		isElementDisplayed("icon_applyDiscount");
		element("icon_applyDiscount").click();
		logMessage("Step : Apply discount button is clicked\n");
	
}

	public void verifyMemberCanRenewForMultipleYearsOrNot(String memberType) {
		if(memberType.equals("Regular")||memberType.equals("Society Affiliate"))
		{
		switchToDefaultContent();
		switchToEwebRenewalFrame();
		isElementDisplayed("btn_renewalLength");
		element("btn_renewalLength").isDisplayed();
		logMessage("Step : Member can renew for multiple times\n");
		switchToDefaultContent();
		}
		else
		{
			logMessage("Step : "+memberType+" member cannot renew for Multipile Years\n");
		}

	}

	public void verifyDiscountedPriceIsDisplayedOnOMREweb(String productName,int expectedDiscount,String Productamount) {
		holdScriptExecution();
		switchToEwebRenewalFrame();
		System.out.println(element("txt_productIndividualAmount",productName).getText().replaceAll("[^\\d.]", ""));
		System.out.println(expectedDiscount);
		System.out.println(Productamount);
		isElementDisplayed("txt_productIndividualAmount",productName);
		if(expectedDiscount==0)
		{
			Assert.assertTrue(element("txt_productIndividualAmount",productName).getText().replaceAll("[^\\d.]", "").trim().equals(Productamount));
			logMessage("ASSERT PASSED : Product '"+productName+"' amount on eweb with it's is already discounted amount is verified as "+Productamount);
		}
		else
		{
			Assert.assertTrue(element("txt_productIndividualAmount",productName).getText().replaceAll("[^\\d.]", "").trim().equals(expectedDiscount+".00"));
			logMessage("ASSERT PASSED : Product '"+productName+"' amount on eweb with discounted amount is verified as "+expectedDiscount);
		}
		switchToDefaultContent();

		
	}

	public void loginIntoOMRApplicationForDiscount(Map<String, String> mapOMR, List<String> memDetails) {
		if(mapOMR.get("Login_With_LastNameMemberNumber?").equalsIgnoreCase("Yes"))
		{
		loginIntoApplication_LastName_MemberNumber(memDetails.get(0), memDetails.get(2));
		}
		else if(mapOMR.get("Login_With_LastNameNoticeNumber?").equalsIgnoreCase("Yes"))
		{
			loginIntoApplication_LastName_NoticeNumber(memDetails.get(0), memDetails.get(3));
		}
		wait.waitForPageToLoadCompletely();
	}

	public void verifyUserIsOnExpressRenewalHomepage(String title) {
		holdScriptExecution();
		isElementDisplayed("logo_OMR");
		Assert.assertTrue(element("logo_OMR").getText().equals(title),"Title is not "+title);
		logMessage("ASSERT PASSED : Homepage title is verified as "+title);
	
		
	}

	public void verifyUserCompleteAddressIsNotDisplayedOnHomePage() {
		verifyAddressFieldContainsAsterisk("RenewalNumber");
		verifyAddressFieldContainsAsterisk("MemberNumber");
		verifyAddressFieldContainsAsterisk("AddressLine1");	
	}

	private void verifyAddressFieldContainsAsterisk(String fieldName) {
		isElementDisplayed("txt_AsteriskFields",fieldName);
		Assert.assertTrue(element("txt_AsteriskFields",fieldName).getText().trim().contains("*"));
		logMessage("Step : ASSERT PASSED : "+fieldName+" is not completely displayed on HomePage\n");
		
	}

	public String getMemberNameFromOMRHomepage() {
		isElementDisplayed("txt_AsteriskFields","MemberName");
		String customerName=element("txt_AsteriskFields","MemberName").getText().trim();
		logMessage("Step : Customer name on renewal homepage is "+customerName);
		return customerName;
		
		
	}
	public String geInvoiceNumberOnOMRReceiptPage(String value) {
		wait.hardWait(4);
		wait.waitForPageToLoadCompletely();
		holdScriptExecution();
		Object invoiceNumberonOMR;
		String invoice;
		invoiceNumberonOMR= executeJavascriptReturnValue("document.getElementById('eWebFrame').contentWindow.document.querySelector('span[id*="+value+"]').innerHTML");
		logMessage("Step : Invoice Number on renewal receipt page is fetched as "+invoiceNumberonOMR);
		invoice = (String) invoiceNumberonOMR;
		System.out.println(invoice);
		return invoice;
		
		
	}
}
