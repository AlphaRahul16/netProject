package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class ASM_OMRPage extends GetPage {

	WebDriver driver;
	static String pagename = "ASM_OMRPage";
	static boolean flag, confirmPage;
	int timeOut, hiddenFieldTimeOut;

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
    	System.out.println("2");
		selectLoginRadioButton("LNMemNo");
    	System.out.println("3");
		enterUserName_lastName(lastName);
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
		enterUserName_lastName(lastName);
		enterPassword_mem_notice(noticeNumber);
		clickOnVerifyButton();
		wait.waitForPageToLoadCompletely();
		// renewConfirmYourInformation();
		switchToDefaultContent();

	}

	public void selectLoginRadioButton(String loginType) {
		isElementDisplayed("rad_" + loginType);
		element("rad_" + loginType).click();
		logMessage("STEP : select radio button for ACS ID in rad_" + loginType
				+ "\n");
	}

	public void enterUserName_lastName(String userName) {
		isElementDisplayed("inp_userName_lastName");
		element("inp_userName_lastName").sendKeys(userName);
		logMessage("STEP : user name " + userName
				+ " is entered in inp_userName_lastName\n");
	}

	public void enterPassword_mem_notice(String password) {
		isElementDisplayed("inp_password_mem_notice");
		element("inp_password_mem_notice").sendKeys(password);
		logMessage("STEP : password " + password
				+ " is entered in inp_password_mem_notice\n");
	}

	public void clickOnVerifyButton() {
		isElementDisplayed("btn_verify");
		element("btn_verify").click();
		logMessage("STEP : verify button is clicked in btn_verify \n");
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

	public void verifyWelcomePage() {
		switchToFrame("eWebFrame");
		isElementDisplayed("hdng_welcome");
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
		logMessage("STEP : save button is clicked in btn_save\n");
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
			logMessage("ASSERT PASSED : verify address is updated \n");
		} else {
			Assert.fail("ASSERT FAILED : address is not updated\n");
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

	public void selectCreditCardType(String cardType) {
		isElementDisplayed("list_cardType");
		selectProvidedTextFromDropDown(element("list_cardType"), cardType);
	}

	public void enterCreditCardHolderName(String cardholderName) {
		isElementDisplayed("inp_cardHolderName");
		element("inp_cardHolderName").sendKeys(cardholderName);
		logMessage("Step : " + cardholderName
				+ " is entered in inp_cardHolderName\n");
	}

	public void enterCreditCardNumber(String cardNumber) {
		isElementDisplayed("inp_cardNumber");
		element("inp_cardNumber").sendKeys(cardNumber);
		logMessage("Step : " + cardNumber + " is entered in inp_cardNumber\n");
	}

	public void enterCVVNumber(String cvvNumber) {
		isElementDisplayed("inp_CVVNumber");
		element("inp_CVVNumber").sendKeys(cvvNumber);
		logMessage("Step : " + cvvNumber + " is entered in inp_CVVNumber\n");
	}

	public void selectExpirationDate_Year(String date_year,
			String date_yearValue) {
		isElementDisplayed("list_expiration" + date_year);
		selectProvidedTextFromDropDown(element("list_expiration" + date_year),
				date_yearValue);
		logMessage("Step : " + date_yearValue + " is selected for " + date_year
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
				logMessage("STEP : eula is accepted in chk_eula\n");
			} else {
				logMessage("STEP : usle is already checked");
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
		logMessage("Step : click on continue button in btn_continue\n");
	}

	public void verifyErrorMessage(String errorMessage) {
		wait.waitForPageToLoadCompletely();
		switchToFrame("eWebFrame");
		isElementDisplayed("txt_invalidCardErrorMessage");
		verifyElementTextContains("txt_invalidCardErrorMessage", errorMessage);
		switchToDefaultContent();
	}

	public void clickOnSubmitPayment() {
		switchToFrame("eWebFrame");
		isElementDisplayed("btn_submitPayment");
		element("btn_submitPayment").click();
		logMessage("STEP : click on submit button at txt_errorMessage\n");
		switchToDefaultContent();
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
			logMessage("STEP : confirmation page is not present");
		}
		wait.waitForPageToLoadCompletely();
		if (confirmPage) {
			isElementDisplayed("chk_Confirm");
			element("chk_Confirm").click();
			logMessage("STEP : confirm check box is checked\n");
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
    
        	//loginIntoApplication_ACS_ID(memDetails.get(2), "password");
        	loginIntoApplication_ACS_ID("ebruoral", "password");
        	
        }
        else if(mapOMR.get("Login_With_LastNameMemberNumber?").equalsIgnoreCase("Yes"))
        {
        	loginIntoApplication_LastName_MemberNumber(memDetails.get(0).split(" ")[0], memDetails.get(1));
        }
        else if(mapOMR.get("Login_With_LastNameNoticeNumber?").equalsIgnoreCase("Yes"))
        {
        	loginIntoApplication_LastName_NoticeNumber(memDetails.get(0).split(" ")[0], memDetails.get(3));
        }
        wait.waitForPageToLoadCompletely();
		
	}
	
	public void switchToEwebRenewalFrame()
	{
		switchToFrame(element("iframe_ewebframe"));
	}

	public void addMembershipsForRegularMember(Map<String, String> mapOMR) {
	
		//addACSTechnicalDivision(mapOMR);
		addACSPublications(mapOMR);
		addACSMemberBenefits(mapOMR);
		addACSContributions(mapOMR);
	}

	private void addACSContributions(Map<String, String> mapOMR) {
		clickAddMembershipButton("Add ACS Contribution");
		
		//isElementDisplayed("txt_legend","My ACS Contributions");
		enterContributionForParticularSubscription(mapOMR.get("Contribution_To_Add?").trim(),mapOMR.get("Contribution_Amount?").trim());
		clickSaveButtonToAddMembership();
		wait.waitForPageToLoadCompletely();
	}

	private void enterContributionForParticularSubscription(String contribution,String price) {
		isElementDisplayed("inp_contribution",contribution);
		element("inp_contribution",contribution).sendKeys(price);
		logMessage("Step : ACS Contribution entered for "+contribution+" as "+price+"\n");
		
	}

	private void addACSMemberBenefits(Map<String, String> mapOMR) {
		
		clickAddMembershipButton("Add ACS Member Benefits");
	//isElementDisplayed("txt_legend","My ACS Member Benefits");
	selectAddToMembershipForParticularSubscription(mapOMR.get("MemBenefits_To_Add?").trim(),"BenefitAddToMembership");
	clickSaveButtonToAddMembership();
	wait.waitForPageToLoadCompletely();
	}

	private void addACSPublications(Map<String, String> mapOMR) {
		
		clickAddMembershipButton("Add ACS Publication");
		//isElementDisplayed("txt_legend","My ACS Publications");
		//selectAddToMembershipForParticularSubscription(mapOMR.get("Publications_To_Add?").trim(),"PubAddToMembership");
		clickSaveButtonToAddMembership();
		wait.waitForPageToLoadCompletely();
	}

	private void addACSTechnicalDivision(Map<String, String> mapOMR) {
		clickAddMembershipButton("Add ACS Technical Division");
		//isElementDisplayed("txt_legend","My ACS Technical Divisions");
		selectAddToMembershipForParticularSubscription(mapOMR.get("Technical_Division_To_Add?").trim(),"add-to-cart");
		clickSaveButtonToAddMembership();
		wait.waitForPageToLoadCompletely();
	}

	private void clickAddMembershipButton(String valueofmembership) {
		isElementDisplayed("btn_addSubscription",valueofmembership);
		element("btn_addSubscription",valueofmembership).click();
		wait.waitForPageToLoadCompletely();
		logMessage("Step : "+valueofmembership+" button is clicked\n");
		
	}
	private void selectAddToMembershipForParticularSubscription(String name,String value)
	{
		switchToDefaultContent();
		switchToEwebRenewalFrame();
		wait.waitForPageToLoadCompletely();
		wait.hardWait(7);
	
			element("btn_addToMemberships",name,value).click();
	
	}
	public void clickSaveButtonToAddMembership()
	{
		
		isElementDisplayed("btn_save");
		element("btn_save").click();
		wait.waitForPageToLoadCompletely();
		logMessage("Step : Save button is clicked\n");
	}

}
