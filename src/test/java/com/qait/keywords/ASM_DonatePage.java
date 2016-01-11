package com.qait.keywords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		logMessage("Step : " + password + " is entered in inp_username\n");
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

	public void enterOtherProgram(String otherProgramValue) {
		isElementDisplayed("inp_otherProgram");
		element("inp_otherProgram").clear();
		element("inp_otherProgram").sendKeys(otherProgramValue);
		logMessage("Step : " + otherProgramValue
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
		logMessage("Step : enter field value: " + fieldValue
				+ " in field name: " + fieldName + " in inp_fieldName\n");

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
		isElementDisplayed("btn_submitPayment");
		// element("btn_continue").click();
		clickUsingXpathInJavaScriptExecutor(element("btn_submitPayment"));
		logMessage("Step : click on continue button in btn_submitPayment\n");
		wait.waitForPageToLoadCompletely();

	}

	public void enterPaymentDetailsForACSDonateSmoke(
			List<String> memberLoginDetails, String cardType,
			String cardHolderName, String cardNumber, String cvvNumber,
			String date_Value, String year_Value) {
		if (memberLoginDetails.size() != 1) {
			String MemberName = MemberFullName.get(0);
			String arrayName[] = MemberName.split(" ");
			MemberName = arrayName[(arrayName.length) - 2] + " "
					+ arrayName[(arrayName.length) - 1];
			System.out.println("Member Full name" + MemberName);
			enterCreditCardHolderName(MemberName);
		} else if (memberLoginDetails.size() == 1) {
			enterCreditCardHolderName(cardHolderName);
		}
		selectCreditCardType(cardType);
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
			logMessage("STEP : " + productName + " display name is"
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

	public String[] checkDonationPrograms(String[] ProgramNameToDonate) {
		String[] programNames = new String[4];
		wait.waitForPageToLoadCompletely();
		for (int i = 0; i < 4; i++) {
			System.out.println("program" + ProgramNameToDonate[i]);
			programNames[i] = isProgram(ProgramNameToDonate[i]);
			System.out.println("program names " + programNames[i]);
		}

		return programNames;

	}

	private String isProgram(String ProgramName) {
		if (ProgramName.length() != 0) {
			IsProgramPledged++;
			System.out.println("ProgramName " + ProgramName + " pledge status "
					+ IsProgramPledged);
		}
		TotalAmountMap
				.put("IsProgramPledged", String.valueOf(IsProgramPledged));
		return ProgramName;

	}

	public void donateMoneyToProgram(String ProgramName[], String Amount[]) {
		wait.waitForPageToLoadCompletely();
		for (int i = 0; i < 3; i++) {
			System.out.println(i);
			if (ProgramName[i].length() == 0) {
				System.out.println("Program name" + ProgramName[i]
						+ " Length is " + ProgramName[i].length());
				logMessage(ProgramName[i] + " is empty");

			} else {
				System.out.println("Program name" + ProgramName[i]
						+ " Length is " + ProgramName[i].length());
				enterDonateValue(ProgramName[i], Amount[i]);
			}
		}
		if (ProgramName[3] != null || ProgramName[3].length() != 0) {

			enterOtherProgram(ProgramName[3]);
			enterOtherAmount(Amount[3]);
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
		System.out.println(element("txt_totalamount").getText()
				.replace("$", ""));
		Assert.assertTrue(totalamount == Double.parseDouble(element(
				"txt_totalamount").getText().replace("$", "")));
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
		System.out.println(element("txt_totalOnDonationPage").getText()
				.replace("$", ""));
		if (pledgedMonthlyTotal == true) {

			Monthlyamount = (totalamount / Double.parseDouble(PledgeMonths));
			Monthlyamount = Math.round(Monthlyamount * 100.00) / 100.00;
			TotalAmountMap.put("MonthlyAmount", String.valueOf(Monthlyamount));
			System.out.println(Monthlyamount);
			Assert.assertTrue(Monthlyamount == Double.parseDouble(element(
					"txt_totalOnDonationPage").getText().replace("$", "")));
			logMessage("Pledged Monthly total displayed as " + Monthlyamount);
		} else {
			Assert.assertTrue(totalamount == Double.parseDouble(element(
					"txt_totalOnDonationPage").getText().replace("$", "")));
			logMessage("Total on Confirmation Page displayed as " + totalamount);
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
		System.out.println("Member login details" + memberLoginDetails.size());
		if (memberLoginDetails.size() > 1) {
			getParticularAddressValue(PhoneNo);
			getParticularAddressValue(Email);
			getParticularAddressValue(Address);
		}

		return mapIwebProductDetails;
	}

	public void getParticularAddressValue(String Name) {

		String actual = element("txt_" + Name).getText().trim();
		mapMemberAddress.put(Name, actual);
	}

	public void clickOnLoginButtonForSpecifiedUser(
			List<String> memberLoginDetails, String ValidEmailAddress) {
		System.out.println("member login details"+memberLoginDetails.size());
		if (memberLoginDetails.size() > 1) {
			wait.waitForPageToLoadCompletely();
			loginIntoApplicationByMember(memberLoginDetails.get(2), "password");
			wait.waitForPageToLoadCompletely();
			verifyMemberOrNonMemberDetails("Name", "Address");
			verifyMemberOrNonMemberDetails("Email", "Email");
			verifyMemberOrNonMemberDetails("Phone", "PhoneNo");
			verifyMemberOrNonMemberDetails("Address", "Address");
			verifyMemberOrNonMemberDetails("City", "Address");
			verifyMemberOrNonMemberDetails("Zip Code", "Address");
			// verifyMemberOrNonMemberDetails("State", "Address");
			// verifyMemberOrNonMemberDetails("Country", "Address");
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
		wait.hardWait(3);
		if (inpfeilds.equals("Name")) {
			wait.hardWait(1);
			// isElementDisplayed("txt_inpName");
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
			logMessage(inpfeilds
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
			if (ProductNames[j].length() != 0) {
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
				logMessage("ASSERT PASSED : Product amount for Product "
						+ elements("txt_confirmDonation_product").get(i)
								.getText() + " is " + totalamount);
				i++;
			} else {
				logMessage("Step : Amount is not donated for Product "
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
			System.out.println("iteration " + iteration);
			System.out.println("count " + count);
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

		isElementDisplayed("txt_thankYouMessage");
		element("txt_thankYouMessage")
				.getText()
				.trim()
				.equals("Thank you for your donation to the American Chemical Society.");
		logMessage("Thankyou Message is verified as "
				+ element("txt_thankYouMessage").getText().trim());
	}

	public void verifyPrintReceiptMessageAfterDonation() {

		isElementDisplayed("lnk_printReceipt");
		element("lnk_printReceipt").getText().trim()
				.equals("Print Your Receipt");
		logMessage("Print Receipt Message is verified as "
				+ element("lnk_printReceipt").getText().trim());

	}

	public void verifyConfirmationEmailAfterDonation(String ConfirmationEmail) {
		isElementDisplayed("txt_confirmationEmailBox");
		element("txt_confirmationEmailBox").getText().trim()
				.contains(ConfirmationEmail);
		logMessage("Confirmation Email displayed on Confirm Your Donation Page as "
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
				logMessage("Break Donation/PledgeMonths checkbox is not checked");
			}
		} else {
			logMessage("Step : Donation not eligible for monthly payments");
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
		System.out.println("Monthly Amount " + Monthlyamount);
		Assert.assertTrue(Monthlyamount == Double.parseDouble(element(
				"txt_monthlyAmountPayble").getText().replace("$", " ").trim()));
	}

	private void checkBreakMyDonationInto() {
		isElementDisplayed("chkbox_breakDonation");
		if (!element("chkbox_breakDonation").isSelected()) {
			pledgedMonthlyTotal = true;
			TotalAmountMap.put("pledgedMonthlyTotal",
					String.valueOf(pledgedMonthlyTotal));
			element("chkbox_breakDonation").click();
			logMessage("Step : " + " is checked in chkbox_breakDonation\n");
		} else {
			logMessage("Step : "
					+ " is already checked in chkbox_breakDonation\n");
		}
	}


	public void verifyProductPledgedSummaryOnConfirmDonationPage(String[] donateProgramNames, String[] amount,String PledgeMonths) {
		if(pledgedMonthlyTotal==true)
		{
			verifyPledgedProductSummary(donateProgramNames);
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
		logMessage("Pledge Message contains Pledge months as " + PledgeMonths);
		Assert.assertTrue(element("txt_pledgeMessage").getText().contains(
				String.valueOf(totalamount)));
		logMessage("Pledge Message contains total amount as " + totalamount);

	}

	private void verifyPledgedProductSummary(String[] donateProgramNames) {
		for (int i = 0; i < donateProgramNames.length; i++) {
			if (donateProgramNames[i].length() != 0) {
				donateProgramNames[0] = donateProgramNames[i];
			}
		}
		isElementDisplayed("txt_confirmDonation_product");
		String pledgeProductName = donateProgramNames[0] + " Pledge";
		System.out.println("pledgeProductName " + pledgeProductName);
		System.out.println(element("txt_confirmDonation_product").getText());
		Assert.assertTrue(element("txt_confirmDonation_product").getText()
				.equals(pledgeProductName));
		logMessage("ASSERT PASSED : Product name on confirm donation page is displayed as : "
				+ donateProgramNames[0]);

	}

}
