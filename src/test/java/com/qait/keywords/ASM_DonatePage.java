package com.qait.keywords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.text.WordUtils;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;
import com.qait.automation.getpageobjects.ASCSocietyGenericPage.*;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class ASM_DonatePage extends GetPage {
	WebDriver driver;
	static String url;
	static String pagename = "ASM_DonatePage";
	int IsProgramPledged;
	boolean pledgedMonthlyTotal = false;
	double totalamount = 0;
	String productNameValues1[] = new String[4];
	Map<String, List<String>> mapIwebProductDetails = new HashMap<String, List<String>>();
	Map<String, String> mapMemberAddress = new HashMap<>();
	List<String> MemberFullName = new ArrayList<String>();
	Map<String, String> TotalAmountMap = new HashMap<String, String>();

	public ASM_DonatePage(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

	public void loginIntoApplication(String userName, String password) {
		clickOnLoginButton();
		clickOnACSIDRadioButton();
		wait.hardWait(3);
		enterUserName(userName);
		enterPassword(password);
		clickOnVerifyButton();
	}

	public void loginIntoApplicationByMember(String userName, String password) {
		clickLoginOnContactInfoPage();
		wait.hardWait(3);
		enterUserName(userName);
		enterPasswordForMember(password);
		clickOnLoginButtonForMember();
	}
	
	public void loginIntoApplicationByMemberViaIDOrMemmberNumber(String userName, String password) {
		clickLoginOnContactInfoPage();
		clickOnLoginByMemberNumberLastName();
		wait.hardWait(3);
		enterUserName(userName);
		enterPassword(password);
		clickOnLoginButtonForMember();
	}

	public void clickOnLoginButton() {
		isElementDisplayed("btn_login");
		element("btn_login").click();
		logMessage("Step : Login button is clicked in btn_login\n");
	}

	public void clickLoginOnContactInfoPage() {

		isElementDisplayed("btn_memlogin");
		element("btn_memlogin").click();
		logMessage("Step : Login button is clicked on contact Info Page\n");
	}

	public void clickOnACSIDRadioButton() {
		isElementDisplayed("rad_acsId");
		if (!element("rad_acsId").isSelected())
			element("rad_acsId").click();
		logMessage("Step : ACS ID radio button is checked in rad_acsId\n");
	}

	public void enterUserName(String userName) {
		isElementDisplayed("inp_username");
		element("inp_username").clear();
		element("inp_username").sendKeys(userName);
		logMessage("Step : " + userName + " is entered in inp_username\n");
	}

	public void enterPassword(String password) {
		isElementDisplayed("inp_password");
		element("inp_password").clear();
		element("inp_password").sendKeys(password);
		logMessage("Step : " + password + " is entered in inp_password\n");
	}

	public void enterPasswordForMember(String password) {
		isElementDisplayed("inp_passwordMember");
		element("inp_passwordMember").clear();
		element("inp_passwordMember").sendKeys(password);
		logMessage("Step : " + password + " is entered in inp_passwordMember\n");
	}

	public void clickOnVerifyButton() {
		isElementDisplayed("btn_verify");
		element("btn_verify").click();
		logMessage("Step : Verify button is clicked in btn_verify\n");
		wait.hardWait(3);

	}

	public void clickOnLoginButtonForMember() {
		isElementDisplayed("btn_loginMember");
		element("btn_loginMember").click();
		logMessage("Step : Login button is clicked in btn_loginMember\n");
		wait.hardWait(3);

	}

	public void enterDonateValue(String donateProgram, String donateValue) {
		try {
			isElementDisplayed("inp_DonateProgram", donateProgram);
			element("inp_DonateProgram", donateProgram).clear();
			element("inp_DonateProgram", donateProgram).sendKeys(donateValue);
			logMessage("Step : " + donateValue + " is entered for "
					+ donateProgram + " in inp_DonateProgram\n");
		} catch (StaleElementReferenceException stlExp) {
			isElementDisplayed("inp_DonateProgram", donateProgram);
			element("inp_DonateProgram", donateProgram).clear();
			element("inp_DonateProgram", donateProgram).sendKeys(donateValue);
			logMessage("Step : " + donateValue + " is entered for "
					+ donateProgram + " in inp_DonateProgram\n");
		}

	}

	public void clickOnContinueButton() {
		wait.waitForPageToLoadCompletely();
		wait.hardWait(1);
		try {
			isElementDisplayed("btn_continue");
			element("btn_continue").click();
		} catch (Exception e) {
			clickUsingXpathInJavaScriptExecutor(element("btn_continue"));

		}
		logMessage("Step : click on continue button in btn_continue\n");
		wait.hardWait(3);
	}

	public void enterOtherProgram(String otherProgramName) {
		isElementDisplayed("inp_otherProgram");
		element("inp_otherProgram").clear();
		element("inp_otherProgram").sendKeys(otherProgramName);
		logMessage("Step : " + otherProgramName
				+ " is entered in inp_otherProgram\n");
	}

	public void verifyCurrentPage(String pageName) {
		wait.hardWait(3);
		try {
			wait.waitForPageToLoadCompletely();
		} catch (TimeoutException timeOutExp) {
			logMessage("Page didn't load completely and perform an actions on loaded elements \n");
		}

		url = getCurrentURL();
		String[] splitUrl = url.split("Code=");
		if (splitUrl[1].equalsIgnoreCase("allprograms")) {
			Assert.assertTrue(pageName.contains("Make a donation"));
			logMessage("ASSERT PASSED : Current page is Make a donation page\n");
		} else if (splitUrl[1].equalsIgnoreCase("ContactInfo")) {
			Assert.assertTrue(pageName.contains("Contact info"));
			logMessage("ASSERT PASSED : Current page is Contact Info page\n");
		} else if (splitUrl[1].equalsIgnoreCase("Checkout")) {
			Assert.assertTrue(pageName.contains("Confirm your donation"));
			logMessage("ASSERT PASSED : Current page is Confirm your donation page\n");
		} else if (splitUrl[1].equalsIgnoreCase("confirmation")) {
			Assert.assertTrue(pageName.contains("confirmation"));
			logMessage("ASSERT PASSED : Current page is confirmation page\n");
		} else {
			Assert.fail("Current page is not expected\n");
		}
	}

	public void enterOtherAmount(String otherAmount) {
		isElementDisplayed("inp_otherAmount");
		element("inp_otherAmount").sendKeys(otherAmount);
		logMessage("Step : " + otherAmount + " is entered in inp_otherAmount\n");
	}

	public void logOut() {
		isElementDisplayed("btn_logout");
		element("btn_logout").click();
		logMessage("Step : log out button is clicked\n");
	}

	public void clickOnContinueAsGuest() {
		isElementDisplayed("btn_continueAsGuest");
		element("btn_continueAsGuest").click();
		logMessage("Step : continue as a guest button is clicked in btn_continueAsGuest\n");
		// verifyContinueAsGuest();
	}

	public void verifyContinueAsGuest() {
		try {
			isElementDisplayed("fieldSet_form");
			logMessage("ASSERT PASSED : Continue as a guest form is appeared\n");
		} catch (Exception stlExp) {
			isElementDisplayed("fieldSet_form");
			logMessage("ASSERT PASSED : Continue as a guest form is appeared\n");
		}

	}

	public void enterRequiredDetailsInNonMemberFormForIndividualLandingPage(String firstName,
			String lastName, String email, String phone, String address,
			String city, String state, String zipcode, String country) {

		enterNonMemberFieldValue("FirstName", firstName);
		enterNonMemberFieldValue("LastName", lastName);
		enterNonMemberFieldValue("Email", email);
		enterNonMemberFieldValue("Phone", phone);
		enterNonMemberFieldValue("Adr_Line1", address);
		enterNonMemberFieldValue("City", city);
		selectNonMemberFieldValue("State", state);
		enterNonMemberFieldValue("ZipCode", zipcode);
		selectNonMemberFieldValue("Country", country);
		clickOnContinueButton();

	}
	public void enterRequiredDetailsInNonMemberForm(String firstName,
			String lastName, String email, String phone, String address,
			String city, String state, String zipcode, String country) {

		enterNonMemberFieldValue("FirstName", firstName);
		enterNonMemberFieldValue("LastName", lastName);
		enterNonMemberFieldValue("Email", email);
		enterNonMemberFieldValue("Phone", phone);
		enterNonMemberFieldValue("Adr_Line1", address);
		enterNonMemberFieldValue("City", city);
		selectNonMemberFieldValue("State", state);
		enterNonMemberFieldValue("ZipCode", zipcode);
		selectNonMemberFieldValue("Country", country);
		clickOnSubmitPaymentButton();

	}

	public void enterNonMemberFieldValue(String fieldName, String fieldValue) {
		isElementDisplayed("inp_fieldName", fieldName);
		element("inp_fieldName", fieldName).clear();
		element("inp_fieldName", fieldName).sendKeys(fieldValue);
		logMessage("Step :  "+fieldName+" in in inp_fieldName is entered as : "+fieldValue+"\n");

	}

	public void selectNonMemberFieldValue(String fieldName, String fieldValue) {
		isElementDisplayed("list_fieldName", fieldName);
		selectProvidedTextFromDropDown(element("list_fieldName", fieldName),
				fieldValue);
	}

	public void verifyLoginErrorMessagePresent(String errorMessage) {
		isElementDisplayed("txt_loginErrorMessage");
		verifyElementTextContains("txt_loginErrorMessage", errorMessage);
		logMessage("ASSERT PASSED : Error message " + errorMessage
				+ " is appeared on invalid credentials\n");
	}

	public void verifyErrorMessage(String errorMessage) {
		isElementDisplayed("txt_errorMessage");
		verifyElementTextContains("txt_errorMessage", errorMessage);
		logMessage("ASSERT PASSED : Error message "
				+ errorMessage
				+ " is appeared on enter invalid data in contact info in txt_errorMessage\n");
	}

	public void checkGiftToSomeoneCheckBox() {
		isElementDisplayed("inp_giftToSomene");
		if (!element("inp_giftToSomene").isSelected()) {
			element("inp_giftToSomene").click();
			logMessage("Step : gift to someone checkbox is checked in inp_giftToSomene\n");
		} else {
			logMessage("Step : gift to someone checkbox is already checked\n");
		}

	}

	public void checkInHonor_MemoryCheckbox(String checkboxName) {
		isElementDisplayed("chk_honor_memory", checkboxName);
		if (!element("chk_honor_memory", checkboxName).isSelected()) {
			element("chk_honor_memory", checkboxName).click();
			logMessage("Step : " + checkboxName
					+ " is checked in chk_honor_memory\n");
		} else {
			logMessage("Step : " + checkboxName
					+ " is already checked in chk_honor_memory\n");
		}
	}

	public void enterHonor_MemoryValue(String honor_memory,
			String honor_memoryValue) {
		isElementDisplayed("inp_honor_memory",
				WordUtils.capitalize(honor_memory));
		element("inp_honor_memory", WordUtils.capitalize(honor_memory))
				.sendKeys(honor_memoryValue);
		logMessage("Step : " + honor_memoryValue + " is entered for "
				+ honor_memory + " in inp_honor_memory\n");
	}

	public void enterRecipientEmail(String emailAddress) {
		isElementDisplayed("inp_recipientEmail");
		element("inp_recipientEmail").sendKeys(emailAddress);
		logMessage("Step : " + emailAddress
				+ " is entered in inp_recipientEmail\n");

	}

	public void enterRecipientPersonalisedMessage(String Message) {
		isElementDisplayed("inp_recipientPersonalisedMsg");
		element("inp_recipientPersonalisedMsg").sendKeys(Message);
		logMessage("Step : " + Message
				+ " is entered in inp_recipientPersonalisedMsg\n");

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

	public void enterRequiredDetailsToConfirmYourDonation(String honor_memory,
			String honor_memoryValue, String sendCardType,
			String... sendCardInfo) {
		checkGiftToSomeoneCheckBox();
		checkInHonor_MemoryCheckbox(honor_memory);
		enterHonor_MemoryValue(honor_memory, honor_memoryValue);
		enterSendCardInfo(sendCardType, sendCardInfo);

	}

	public void enterPaymentDetails(String cardType, String cardholderName,
			String cardNumber, String cvvNumber, String date_Value,
			String year_Value) {
		selectCreditCardType(cardType);
		enterCreditCardHolderName(cardholderName);
		enterCreditCardNumber(cardNumber);
		enterCVVNumber(cvvNumber);
		selectExpirationDate_Year("Date", date_Value);
		selectExpirationDate_Year("Year", year_Value);
		clickOnSubmitPaymentButton();
	}

	public void clickOnSubmitPaymentButton() {
		wait.hardWait(3);
		wait.waitForPageToLoadCompletely();
		wait.hardWait(2);
		//isElementDisplayed("btn_submitPayment");
		wait.hardWait(2);
		// element("btn_continue").click();
		clickUsingXpathInJavaScriptExecutor(element("btn_submitPayment"));
		logMessage("Step : click on continue button in btn_submitPayment\n");
		wait.waitForPageToLoadCompletely();

	}

	public void enterPaymentDetailsForACSDonateSmoke(
			List<String> memberLoginDetails, String cardType,
			String cardHolderName, String cardNumber, String cvvNumber,
			String date_Value, String year_Value) {
		selectCreditCardType(cardType);
		if (memberLoginDetails.size() != 1) {
			System.out.println(memberLoginDetails.size());
			System.out.println(MemberFullName.get(0));
			String MemberName = MemberFullName.get(0);
			String arrayName[] = MemberName.split(" ");
			MemberName = arrayName[(arrayName.length) - 2] + " "
					+ arrayName[(arrayName.length) - 1];
			enterCreditCardHolderName(MemberName);
		} else if (memberLoginDetails.size() == 1) {
			enterCreditCardHolderName(cardHolderName);
		}
	
		enterCreditCardNumber(cardNumber);
		enterCVVNumber(cvvNumber);
		selectExpirationDate_Year("Date", date_Value);
		selectExpirationDate_Year("Year", year_Value);
		clickOnSubmitPaymentButton();

	}

	public void enterSendCardInfo(String sendCardType, String[] sendCardinfo) {
		if (sendCardType.equalsIgnoreCase("Email")) {
			enterRecipientEmail(sendCardinfo[0]);
		} else if (sendCardType.equalsIgnoreCase("Postal")) {
			checkSendCradType(sendCardType);
			enterPostalEmailInTo(sendCardinfo[0]);
			enterPostalMailInfo("Adr_Line1", sendCardinfo[1]);
			enterPostalMailInfo("City", sendCardinfo[2]);
			selectPostalMailInfo("State", sendCardinfo[3]);
			enterPostalMailInfo("ZipCode", sendCardinfo[4]);
		}
	}

	public void enterSendCardInfoForGivingApplication(String sendCardType,
			String[] sendCardinfo) {
		if (sendCardType.equalsIgnoreCase("Email")) {
			enterRecipientEmail(sendCardinfo[3]);
			enterRecipientPersonalisedMessage(sendCardinfo[4]);
		} else if (sendCardType.equalsIgnoreCase("Postal")) {
			checkSendCradType(sendCardType);
			enterPostalEmailInTo(sendCardinfo[5]);
			enterPostalMailInfo("Adr_Line1", sendCardinfo[6]);
			enterPostalMailInfo("City", sendCardinfo[7]);
			selectPostalMailInfo("State", sendCardinfo[8]);
			enterPostalMailInfo("ZipCode", sendCardinfo[9]);
		}
	}

	public void enterPostalEmailInTo(String postalEmail) {
		isElementDisplayed("inp_to");
		element("inp_to").sendKeys(postalEmail);
		logMessage("Step : " + postalEmail + " is entered in inp_to\n");
	}

	public void checkSendCradType(String typeName) {
		isElementDisplayed("rad_sendCardType", typeName);
		if (!element("rad_sendCardType", typeName).isSelected()) {
			element("rad_sendCardType", typeName).click();
			logMessage("Step : " + typeName
					+ " is checked in rad_sendCardType\n");
		} else {
			logMessage("Step : " + typeName
					+ " is already checked in rad_sendCardType\n");
		}
	}

	public void enterPostalMailInfo(String fieldName, String fieldValue) {
		isElementDisplayed("inp_postalMailInfo", fieldName);
		element("inp_postalMailInfo", fieldName).sendKeys(fieldValue);
		logMessage("Step : " + fieldValue
				+ " is entered in inp_postalMailInfo\n");
	}

	public void selectPostalMailInfo(String fieldName, String fieldValue) {
		isElementDisplayed("list_postalMailInfo", fieldName);
		selectProvidedTextFromDropDown(
				element("list_postalMailInfo", fieldName), fieldValue);
		logMessage("Step : " + fieldValue
				+ " is entered in list_postalMailInfo\n");
	}

	public void verifyCreditCardErrorMessage(String errorMessage) {
		isElementDisplayed("txt_creditCardError");
		verifyElementTextContains("txt_creditCardError", errorMessage);
		logMessage("ASSERT PASSED : Verified error message " + errorMessage
				+ " in txt_creditCardError\n");
	}

	public String[] retreiveProductDetails() {
          
		return getProductNamesAndCodes();

	}

	private String[] getProductNamesAndCodes() {
		String productName = null;
		for (int i = 0; i < elements("txt_productNames").size(); i++) {
			productNameValues1[i] = elements("txt_productNames").get(i)
					.getText();
			List<String> productNameValues = new ArrayList<String>();
			productName = elements("txt_productNames").get(i).getText();
			isElementDisplayed("txt_product_displayName", productName);
			productNameValues.add(element("txt_product_displayName",
					productName).getText());
			isElementDisplayed("txt_product_Code", productName);
			productNameValues.add(element("txt_product_Code", productName)
					.getText());
			mapIwebProductDetails.put(productName, productNameValues);
			logMessage("STEP : " + productName + " display name is "
					+ productNameValues.get(0) + " And Code is "
					+ productNameValues.get(1));

		}
		for (Map.Entry<String, List<String>> entry : mapIwebProductDetails
				.entrySet()) {
			String key = entry.getKey();
			List<String> values = entry.getValue();
			System.out.println("Key = " + key);
			System.out.println("Values = " + values + "\n");
		}
		return productNameValues1;

	}

	public String[] checkDonationPrograms(String[] ProgramAmountToDonate) {
		String[] programAmount = new String[4];
		wait.waitForPageToLoadCompletely();
		for (int i = 0; i < 4; i++) {
			programAmount[i] = isProgram(ProgramAmountToDonate[i]);
		}

		return programAmount;

	}

	private String isProgram(String programAmount) {
		if (programAmount.length() != 0) {
			IsProgramPledged++;
		}
		TotalAmountMap
				.put("IsProgramPledged", String.valueOf(IsProgramPledged));
		return programAmount;

	}

	public void donateMoneyToProgram(String ProgramName[], String Amount[],String OtherProgramName) {
		wait.waitForPageToLoadCompletely();
		for (int i = 0; i <ProgramName.length ; i++) {

			if (Amount[i].length() == 0) {
				System.out.println("Program name " + ProgramName[i]
						+ " Length is " + ProgramName[i].length());

			} 
			else if(ProgramName[i].equals(OtherProgramName))
					{
				enterOtherProgram(OtherProgramName);
				enterOtherAmount(Amount[3]);
					}
			
			else if(Amount[i].length() != 0){
				System.out.println("Program name " + ProgramName[i]
						+ " Length is " + ProgramName[i].length());
				enterDonateValue(ProgramName[i], Amount[i]);
			}
		}
	}

	public void verifyTotalAmountEnteredOnProductDonation(String[] Amount) {
		double sum = 0.00;
		for (int i = 0; i < Amount.length; i++) {
			if (!Amount[i].equals(""))
				sum = Double.parseDouble(Amount[i]) + sum;
		}
		double totalamount = Math.round(sum * 100.00) / 100.00;
		isElementDisplayed("txt_totalamount");
		Assert.assertTrue(totalamount == Double.parseDouble(element(
				"txt_totalamount").getText().replace("$", "")));
		logMessage("ASSERT PASSED : Total calculated amount entered on donation page is "+totalamount);
		clickOnSubmitPaymentButton();

	}

	public Map<String, String> verifyTotalAmountOnDonationPage(String[] Amount,
			String PledgeMonths) {
		double sum = 0.00;
		double Monthlyamount = 0.00;

		for (int i = 0; i < Amount.length; i++) {
			if (Amount[i].length() != 0)
				sum = Double.parseDouble(Amount[i]) + sum;
			totalamount = Math.round(sum * 100.00) / 100.00;
		}
		TotalAmountMap.put("TotalAmount", String.valueOf(totalamount));
		isElementDisplayed("txt_totalOnDonationPage");
		if (pledgedMonthlyTotal == true) {

			Monthlyamount = (totalamount / Double.parseDouble(PledgeMonths));
			Monthlyamount = Math.round(Monthlyamount * 100.00) / 100.00;
			TotalAmountMap.put("MonthlyAmount", String.valueOf(Monthlyamount));
			Assert.assertTrue(Monthlyamount == Double.parseDouble(element(
					"txt_totalOnDonationPage").getText().replace("$", "")));
			logMessage("ASSERT PASSED : Pledged Monthly total displayed as " + Monthlyamount);
		} else {
			Assert.assertTrue(totalamount == Double.parseDouble(element(
					"txt_totalOnDonationPage").getText().replace("$", "")));
			logMessage("ASSERT PASSED : Total on Confirmation Page displayed as " + totalamount);
		}
		return TotalAmountMap;

	}
	

	public void verifyProductNamesFromIweb(String[] productNameKey) {
		wait.waitForPageToLoadCompletely();
		for (int i = 0; i < 3; i++) {
			System.out.println(productNameKey[i]);
			isElementDisplayed("txt_DonateProgram", productNameKey[i]);
		}

	}

	public Map<String, List<String>> getUserAddressDetails(
			List<String> memberLoginDetails, String PhoneNo, String Email,
			String Address) {
		if (memberLoginDetails.size() > 1) {
			getParticularAddressValue(PhoneNo);
			getParticularAddressValue(Email);
			getParticularAddressValue(Address);
		}

		return mapIwebProductDetails;
	}

	public void getParticularAddressValue(String Name) {

		String actualvalue = element("txt_" + Name).getText().trim();
		mapMemberAddress.put(Name, actualvalue);
		logMessage("Step : User "+Name+" is displayed on iweb as "+actualvalue);
	}
	private void clickOnLoginByMemberNumberLastName()
	{
		
		isElementDisplayed("rad_lastNameMemNumber");
		element("rad_lastNameMemNumber").click();
		
		
	}

	public void clickOnLoginButtonForSpecifiedUser(
			List<String> memberLoginDetails, String ValidEmailAddress,String sheetValue,List<String> memberdetails) {
		System.out.println("member login details"+memberLoginDetails.size());
		if (memberLoginDetails.size() > 1) {
			wait.waitForPageToLoadCompletely();
			if(sheetValue.equalsIgnoreCase("YES"))
			{
				System.out.println("$$$$$$$$");
			loginIntoApplicationByMemberViaIDOrMemmberNumber(memberdetails.get(1), memberdetails.get(0));
			}
			else
			{
			loginIntoApplicationByMember(memberLoginDetails.get(2), "password");
			}
			wait.waitForPageToLoadCompletely();
			try
			{
				wait.hardWait(2);
				System.out.println("asfsfsffs");
			verifyMemberOrNonMemberDetails("Name", "Address");
			verifyMemberOrNonMemberDetails("Email", "Email");
			verifyMemberOrNonMemberDetails("Phone", "PhoneNo");
			verifyMemberOrNonMemberDetails("Address", "Address");
			verifyMemberOrNonMemberDetails("City", "Address");
			verifyMemberOrNonMemberDetails("Zip Code", "Address");
			}
			catch(StaleElementReferenceException e)
			{
				System.out.println("sfsfsf");
			}
			element("txtbox_inpfeild", "Email").click();
			element("txtbox_inpfeild", "Email").clear();
			element("txtbox_inpfeild", "Email").sendKeys(ValidEmailAddress);
			clickOnSubmitPaymentButton();

		} else {
			clickOnContinueAsGuest();

		}

	}

	public void verifyMemberOrNonMemberDetails(String inpfeilds, String Address) {
		wait.waitForPageToLoadCompletely();
		System.out.println(inpfeilds);
		wait.hardWait(2);
		if (inpfeilds.equals("Name")) {
			wait.hardWait(10);
			isElementDisplayed("txt_inpName");
			MemberFullName.add(element("txt_inpName").getText().trim());
			Assert.assertTrue(mapMemberAddress.get(Address).contains(
					element("txt_inpName").getText().trim()));
			logMessage("Name in Address feild is verified as "
					+ element("txt_inpName").getText());
		} else {
			wait.hardWait(1);
			isElementDisplayed("txtbox_inpfeild", inpfeilds);
			System.out.println(element("txtbox_inpfeild", inpfeilds)
					.getAttribute("value"));
			System.out.println(mapMemberAddress.get(Address));
			Assert.assertTrue(mapMemberAddress.get(Address).contains(
					element("txtbox_inpfeild", inpfeilds).getAttribute("value")
							.trim()));
			logMessage("ASSERT PASSED : "+inpfeilds
					+ " in Address feild is verified as "
					+ element("txtbox_inpfeild", inpfeilds).getAttribute(
							"value"));
		}
	}

	public void verifyProductDetailsOnConfirmDonationPage(
			String[] ProductNames, String[] Amount) {
		System.out.println(ProductNames.length);
		int i = 0;
		for (int j = 0; j < ProductNames.length; j++) {
			System.out.println(j);
			System.out.println("Program name " + ProductNames[j]
					+ " length is " + ProductNames[j].length() + " Amount is "
					+ Amount[j]);
			if (Amount[j].length() != 0) {
				double totalamount = Math
						.round(Double.parseDouble(Amount[j]) * 100.00) / 100.00;
				Assert.assertTrue(elements("txt_confirmDonation_product")
						.get(i).getText().equals(ProductNames[j]));
				logMessage("ASSERT PASSED : Product name on confirm donation page is displayed as : "
						+ ProductNames[j]);
				Assert.assertTrue(Double.parseDouble(element(
						"txt_confirmDonation_amount",
						elements("txt_confirmDonation_product").get(i)
								.getText()).getText()) == totalamount);
				logMessage("ASSERT PASSED : Product amount donated for Product "
						+ elements("txt_confirmDonation_product").get(i)
								.getText() + " is " + totalamount);
				i++;
			} else {
				System.out.println("Step : Amount is not donated for Product "
						+ ProductNames[j]);
			}
		}
	}

	public void enterGuestRequiredDetailsInForm(
			List<String> memberLoginDetails, String FirstName, String LastName,
			String validEmail, String PhoneNo, String Address, String City,
			String State, String ZipCode, String Country) {

		if (memberLoginDetails.size() == 1) {

			enterRequiredDetailsInNonMemberForm(FirstName, LastName,
					validEmail, PhoneNo, Address, City, State, ZipCode, Country);

		}
	}
	
	public void enterGuestRequiredDetailsInFormForIndividualLandingPage(
			List<String> memberLoginDetails, String FirstName, String LastName,
			String validEmail, String PhoneNo, String Address, String City,
			String State, String ZipCode, String Country) {

		if (memberLoginDetails.size() == 1) {

			enterRequiredDetailsInNonMemberFormForIndividualLandingPage(FirstName, LastName,
					validEmail, PhoneNo, Address, City, State, ZipCode, Country);

		}
	}

	public void sendCardOrEmailFromSpreadsheet(
			String isCardSelectedInSpreadsheet, String inHonorOf,
			String inMemoryOf, String... a) {
		if (isCardSelectedInSpreadsheet.equalsIgnoreCase("YES")) {
			checkGiftToSomeoneCheckBox();
			if (inHonorOf.length() != 0 && inMemoryOf.length() == 0) {

				checkInHonor_MemoryCheckbox("honor");
				enterHonor_MemoryValue("honor", inHonorOf);
			} else if (inMemoryOf.length() != 0 && inHonorOf.length() == 0) {
				checkInHonor_MemoryCheckbox("memory");
				enterHonor_MemoryValue("memory", inMemoryOf);
			}

		} else {
			logMessage("Step : Option to send an Ecard or mail a card Not Selected");
		}

	}

	public void selectASendCardMethod(String isCardSelectedInSpreadsheet,
			String... a) {
		if (isCardSelectedInSpreadsheet.equalsIgnoreCase("YES")
				| isCardSelectedInSpreadsheet.length() != 0) {
			int count = 0, iteration = 0;
			for (int i = 0; i < 3; i++) {
				if (a[i].equalsIgnoreCase("YES")) {

					count++;
					iteration = i;
				}
			}
			if (count == 1) {
				if (iteration == 1) {

					enterSendCardInfoForGivingApplication("Email", a);

				} else if (iteration == 2) {

					enterSendCardInfoForGivingApplication("Postal", a);

				} else if (iteration == 0) {
					checkSendCradType("Nothing");
				}
			} else {
				Assert.fail("More than 1 feild in Datasheet Contains YES");
			}

		}
	}

	public void verifyThankyouMessageAfterDonation() {
        wait.waitForPageToLoadCompletely();
        wait.waitForElementToBeVisible(element("txt_thankYouMessage"));
		isElementDisplayed("txt_thankYouMessage");
		Assert.assertTrue(element("txt_thankYouMessage")
				.getText()
				.trim()
				.equals("Thank you for your donation to the American Chemical Society."));
		logMessage("ASSERT PASSED : Thankyou Message is verified as "
				+ element("txt_thankYouMessage").getText().trim());
	}

	public void verifyPrintReceiptMessageAfterDonation() {

		isElementDisplayed("lnk_printReceipt");
		element("lnk_printReceipt").getText().trim()
				.equals("Print Your Receipt");
		logMessage("ASSERT PASSED : Print Receipt Message is verified as "
				+ element("lnk_printReceipt").getText().trim());

	}

	public void verifyConfirmationEmailAfterDonation(String ConfirmationEmail) {
		isElementDisplayed("txt_confirmationEmailBox");
		element("txt_confirmationEmailBox").getText().trim()
				.contains(ConfirmationEmail);
		logMessage("ASSERT PASSED : Confirmation Email displayed on Confirm Your Donation Page as "
				+ ConfirmationEmail);

	}

	public void BreakMyDonationForMonthlyPayments(String isBreakDonationTrue,
			String PledgeMonths) {
		if (IsProgramPledged == 1) {
			if ((isBreakDonationTrue.equalsIgnoreCase("YES") || isBreakDonationTrue
					.length() != 0) && PledgeMonths.length() != 0) {
				checkBreakMyDonationInto();
				selectProvidedTextFromDropDown(element("drpdown_pledgeMonths"),
						PledgeMonths);
				verifyMonthlyAmountPayable(PledgeMonths);
			} else if ((isBreakDonationTrue.equalsIgnoreCase("YES") && PledgeMonths
					.length() == 0)
					| (!isBreakDonationTrue.equalsIgnoreCase("YES") && PledgeMonths
							.length() != 0)) {
				Assert.fail("Break Donation/PledgeMonths in datasheet are Empty");
				
			} else {
				logMessage("ASSERT PASSED : Break Donation/PledgeMonths checkbox is not checked");
			}
		} else {
			logMessage("ASSERT PASSED : Donation not eligible for monthly payments");
		}

	}
	public void BreakMyDonationForMonthlyPaymentsForIndividualLanding(String isBreakDonationTrue,
			String PledgeMonths) {
	
			if ((isBreakDonationTrue.equalsIgnoreCase("YES") || isBreakDonationTrue
					.length() != 0) && PledgeMonths.length() != 0) {
				checkBreakMyDonationInto();
				selectProvidedTextFromDropDown(element("drpdown_pledgeMonths"),
						PledgeMonths);
				verifyMonthlyAmountPayable(PledgeMonths);
			} else if ((isBreakDonationTrue.equalsIgnoreCase("YES") && PledgeMonths
					.length() == 0)
					| (!isBreakDonationTrue.equalsIgnoreCase("YES") && PledgeMonths
							.length() != 0)) {
				Assert.fail("Break Donation/PledgeMonths in datasheet are Empty");
				
			} else {
				logMessage("ASSERT PASSED : Break Donation/PledgeMonths checkbox is not checked");
			}
		

	}

	private void verifyMonthlyAmountPayable(String PledgeMonths) {
		double Totalamount = 0;
		double Monthlyamount = 0;
		isElementDisplayed("txt_monthlyAmountPayble");
		Totalamount = Double.parseDouble(element("txt_totalOnDonationPage")
				.getText());
		Monthlyamount = (Totalamount / Double.parseDouble(PledgeMonths));
		Monthlyamount = Math.round(Monthlyamount * 100.00) / 100.00;
		System.out.println(element(
				"txt_monthlyAmountPayble").getText().replace("$", " ").trim());
		Assert.assertTrue(Monthlyamount == Double.parseDouble(element(
				"txt_monthlyAmountPayble").getText().replace("$", " ").trim()));
		logMessage("ASSERT PASSED : Monthly payable amount is verifed as "+Monthlyamount);
	}

	private void checkBreakMyDonationInto() {
		isElementDisplayed("chkbox_breakDonation");
		if (!element("chkbox_breakDonation").isSelected()) {
			System.out.println("###############pledgedMonthlyTotal is True##############");
			wait.hardWait(1);
			pledgedMonthlyTotal = true;
			TotalAmountMap.put("pledgedMonthlyTotal",
					String.valueOf(pledgedMonthlyTotal));
			System.out.println(pledgedMonthlyTotal+"  pledgedMonthlyTotal value");
			element("chkbox_breakDonation").click();
			logMessage("Step : breakdonation  is checked in chkbox_breakDonation\n");
		} else {
			logMessage("Step : "
					+ " is already checked in chkbox_breakDonation\n");
		}
	}


	public void verifyProductPledgedSummaryOnConfirmDonationPage(String[] donateProgramNames, String[] amount,String PledgeMonths) {
		if(pledgedMonthlyTotal==true)
		{
			verifyPledgedProductSummary(donateProgramNames,amount);
			verifyPledgeMessageOnSummaryTable(PledgeMonths);
		} else {
			verifyProductDetailsOnConfirmDonationPage(donateProgramNames,
					amount);
		}

	}

	private void verifyPledgeMessageOnSummaryTable(String PledgeMonths) {
		isElementDisplayed("txt_pledgeMessage");
		Assert.assertTrue(element("txt_pledgeMessage").getText().contains(
				PledgeMonths));
		logMessage("ASSERT PASSED : Pledge Message contains Pledge months as " + PledgeMonths);
		System.out.println(element("txt_pledgeMessage").getText());
		System.out.println(totalamount);
		Assert.assertTrue(element("txt_pledgeMessage").getText().contains(
				String.valueOf(totalamount)));
		logMessage("ASSERT PASSED : Pledge Message contains total amount as " + totalamount);

	}

	private void verifyPledgedProductSummary(String[] donateProgramNames,String[] amount) {
		for (int i = 0; i < amount.length; i++) {
			if (amount[i].length() != 0) {
				donateProgramNames[0] = donateProgramNames[i];
			}
		}
		System.out.println("Donate Program Names"+donateProgramNames);
		isElementDisplayed("txt_confirmDonation_product");
		String pledgeProductName = donateProgramNames[0] + " Pledge";
		System.out.println(pledgeProductName);
		System.out.println(element("txt_confirmDonation_product").getText());
		Assert.assertTrue(element("txt_confirmDonation_product").getText()
				.equals(pledgeProductName));
		logMessage("ASSERT PASSED : Product name on confirm donation page is displayed as : "
				+ pledgeProductName);

	}



	public String[] getDisplayedProductNamesOnEweb(String[] productNameKey, String  OtherProgramName) {
//		Set<String> myset;
//		String[] ProductNames;
//		myset=mapIwebProductDetails.keySet();
//		ProductNames=myset.toArray(new String[myset.size()]);
	List<String> list = new ArrayList<String>(Arrays.asList(productNameKey));
		list.remove("Suspense Gift");
		System.out.println("other program length "+OtherProgramName.length());
		if(OtherProgramName.length()!=0)
		{
		list.add(OtherProgramName);
		}
		productNameKey=list.toArray(new String[0]);
		 for ( int i=0;i<productNameKey.length;i++ ) {
			System.out.println( "program names in eweb"+productNameKey[i]);
			}
		 return productNameKey;
		
	}

	
	public void verifyIndividualDonationDisplayOrder(int donationcount, Map<String, String> mapSheetData,
			Map<String, String> mapFundOrder) {
		String defaultamount=mapSheetData.get("Default_price?");
        wait.waitForPageToLoadCompletely();
        wait.hardWait(2);
		Assert.assertTrue(element("rad_defaultAmount").getText().replaceAll("[$,]", "").trim().equals(mapSheetData.get(defaultamount)));
		System.out.println("Donation count "+donationcount);
		System.out.println("Size "+elements("rad_landingAmount").size());
		Assert.assertTrue(donationcount==elements("rad_landingAmount").size());
	for(int i=0;i<donationcount;i++)
	{
		System.out.println(i);
		System.out.println("Actual "+mapFundOrder.get("Amount"+i));
		System.out.println(Double.parseDouble(elements("rad_landingAmount").get(i).getText().replaceAll("[$,]","")));
		Assert.assertTrue(Double.parseDouble(elements("rad_landingAmount").get(i).getText().replaceAll("[$,]",""))==Double.parseDouble(mapFundOrder.get("Amount"+i)));

	}
		
	}
	
	public String[] donateAmountToSpecifiedFund(Map<String, String> mapSheetData)
	{
		if(mapSheetData.get("Amount_to_be_donated").length()==0)
		{
			logMessage("Step : Amount to be donated is default Amount\n");
		}
		else if(mapSheetData.get("Amount_to_be_donated").length()!=0)
		{
			if(mapSheetData.get("Amount_to_be_donated").contains("Other_donation_amount"))
			{
				donateAmountToOtherFund(mapSheetData.get("Other_donation_amount"));
			}
			else
			{
				donateAmountToProgram(mapSheetData);
			}
		}
		System.out.println(element("txt_individualProgramHeading").getText().trim());
		String[] ProductNames=new String[]{element("txt_individualProgramHeading").getText().trim()};
		System.out.println(ProductNames[0]);
		return ProductNames;
	}
	private void donateAmountToProgram(Map<String, String> mapSheetData) {
		String amountToBeDonated=mapSheetData.get("Amount_to_be_donated");
		
		String amountToBeDonatedindollars="\\$"+mapSheetData.get(amountToBeDonated);
		System.out.println("Dollars "+amountToBeDonatedindollars);
		element("rad_donatedAmount1",amountToBeDonatedindollars).click();
		logMessage("Step : Amount selected for donation is "+"$"+mapSheetData.get(amountToBeDonated));
	}

	public void donateAmountToOtherFund(String otherAmount)
	{

		if(otherAmount.length()!=0)
			{
			wait.waitForPageToLoadCompletely();
			element("rad_otherdonationAmount").click();
			
			wait.waitForElementToBeVisible(element("inp_otherAmountLandingPage"));
			wait.hardWait(2);
		element("inp_otherAmountLandingPage").click();
		element("inp_otherAmountLandingPage").clear();
		element("inp_otherAmountLandingPage").sendKeys(otherAmount);
		System.out.println("other amount is "+ otherAmount);
			}
	
		
	}


	public String[] getTotalAmountDonatedForIndividualLandingPage() {
	
		String[] totalAmount=new String[]{element("txt_totalamount").getText().replace("$", "")};
		System.out.println(totalAmount[0].length());
		System.out.println(totalAmount);
		totalamount=Double.parseDouble(totalAmount[0]);
		return totalAmount;
	}

		
	}


