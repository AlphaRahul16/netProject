package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_My_Account_Test extends BaseTest {

	String app_url_IWEB, app_url_MyAccount, userName, customerId,telephoneType,techDivision;
	int number;
	private String caseID;
	Map<String, Boolean> skipTest = new HashMap<String, Boolean>();
	Map<String, String> changedValues= new HashMap<>();
	List<String> memberDetails=new ArrayList<>();
	String myApplications[]={"ACS Fellows","Member Get A Member","Email Newsletters","Gift a Membership"};

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_MyAccount = getYamlValue("app_url_MyAccount");
		app_url_IWEB = getYamlValue("app_url_IwebMyAccount");
		test.homePageIWEB.addValuesInMap("MyAccount", caseID);
		test.homePageIWEB.EnterTestMethodNameToSkipInMap_InMyAccount(skipTest, test.acsMyAccount.map().get("Member?"));
	}

	@BeforeMethod
	public void Skip_Tests_For_Different_Members(Method method) {
//		test.printMethodName(method.getName());
		if (!skipTest.containsKey(method.getName())) {
			skipTest.put(method.getName(), false);
		}
		if (skipTest.get(method.getName())) {
			throw new SkipException("Tests Skipped for expected work flows!");
		}
	}

	public ACS_My_Account_Test() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "MyAccount";
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_My_Account_Test(String caseID) {
		this.caseID = caseID;
	}

	@Test
	public void Step01_Launch_My_Account_Application_And_Create_New_Member() {
		// number=test.acsMyAccount.createMember(test.acsMyAccount.map().get("Member?"),test.acsMyAccount.map(),test);
		number = test.acsMyAccount.generateThreeDidgitRandomNumber(999, 99);
		userName=test.acsMyAccount.map().get("First_Name")+number;
		test.launchApplication(app_url_MyAccount);
		test.acsMyAccount.clickOnLoginButton("Log In");
		test.acsMyAccount.clickOnLoginButton("Registering is easy");
		test.acsMyAccount.enterEmail("email", test.acsMyAccount.map().get("Email"), "Email", number);
		test.acsMyAccount.enterNewMemberDetails("firstName", test.acsMyAccount.map().get("First_Name") + number,
				"First Name");
		test.acsMyAccount.enterNewMemberDetails("lastName", test.acsMyAccount.map().get("Last_Name"), "Last Name");
		test.acsMyAccount.enterNewMemberDetails("userName", test.acsMyAccount.map().get("UserName") + number,
				"User Name");
		test.acsMyAccount.enterNewMemberDetails("passwordPwd", test.acsMyAccount.map().get("Password"), "Password");
		test.acsMyAccount.enterNewMemberDetails("confirmPassword", test.acsMyAccount.map().get("Password"),
				"Confirm Password");
		test.acsMyAccount.clickOnCreateAccountButton("submit_button");
		test.acsMyAccount.clickOnACSWButton("ACSWWW AEM");
	}

	@Test
	public void Step02_Launch_Iweb_Application_And_Find_Existing_Member() {
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.clickOnTab("Find Individual");
		test.memberShipPage.checkAdvanceNewCheckbox();
		test.memberShipPage.selectAdvanceNewDropDown("Web Login:", "Contains");
		test.memberShipPage.enterValueInAdvanceNewInput("Web Login:", "acstest_%");
		test.memberShipPage.selectAdvanceOptionFlag("Member Flag:", "13");
		test.memberShipPage.clickOnGoButton();
		test.memberShipPage.clickOnRandomPage(10, 1);
		test.memberShipPage.clickOnAnyRandomMember();
		String webLogin = test.memberShipPage.getMemberWebLogin();
		memberDetails=test.memberShipPage.getMemberDetails();
		test.individualsPage.navigateToContactInfoMenuOnHoveringMore();
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("telephone numbers");
		telephoneType=test.memberShipPage.getTelephoneNumberType("telephone numbers");
		userName=memberDetails.get(2)+ " "+memberDetails.get(0);
		number = test.acsMyAccount.generateThreeDidgitRandomNumber(999, 99);
		test.launchApplication(app_url_MyAccount);
		test.acsMyAccount.clickOnLoginButton("Log In");
		test.acsMyAccount.enterLoginDetails("userid", webLogin);
		test.acsMyAccount.enterLoginDetails("password", "password");
		test.acsMyAccount.clickOnSaveButton("Log In");
	}

	@Test
	public void Step03_Verify_Account_Is_Registered_And_Edit_Email() {
		test.acsMyAccount.handleFeedbackForm();
		test.acsMyAccount.verifyUserName(userName, test.acsMyAccount.map().get("Last_Name"),test.acsMyAccount.map().get("Member?"),userName);
//		test.acsMyAccount.handleFeedbackForm();
		test.acsMyAccount.navigateToAccountLink("My Account");
		customerId=test.acsMyAccount.getMemberCustomerId();
		test.acsMyAccount.clickOnACSWButton("Change Email");
		test.acsMyAccount.enterNewEmail(test.acsMyAccount.map().get("Edit_Email"), test.acsMyAccount.map().get("Email"),
				number);
		test.acsMyAccount.clickOnEmailSaveButton("primary");
		test.acsMyAccount.verifyEmailSavedMessage(getYamlValue("ACS_MyAccount.emailSaveMessage"), "msg_emailSaved");
	}

	@Test
	 public void Step04_Edit_Phone_Number_And_Add_Address(){
	 test.acsMyAccount.changePhoneNumber("Home Add Phone",test.acsMyAccount.map().get("Member?"),telephoneType);
	 test.acsMyAccount.editPhoneNumber(test.acsMyAccount.map().get("Phone_Number")+number,test.acsMyAccount.map().get("Primary_Member?"));
	 test.acsMyAccount.clickOnEmailSaveButton("home");
	 test.acsMyAccount.verifyEmailSavedMessage(getYamlValue("ACS_MyAccount.phoneSaveMessage"),"msg_phoneSave");
	 test.acsMyAccount.verifyPriamryImageIsPresent("img_phonePrimary", "HomePhone");
	 test.acsMyAccount.changeAddress(test.acsMyAccount.map().get("Member?"), test.acsMyAccount.map());
	 test.acsMyAccount.verifySaveAddressMessage(getYamlValue("ACS_MyAccount.addressSaveMessage"));
	 test.acsMyAccount.clickOnCloseButton();
//	 test.acsMyAccount.verifyPriamryImageIsPresent("img_addressPrimary", "WorkAddress");
	 }
	
	@Test
	public void Step05_Verify_Techincal_Division_And_My_Applications(){
		test.acsMyAccount.clickOnSideTab("Technical Divisions");
		techDivision=test.acsMyAccount.getTechnicalDivisions();
		test.acsMyAccount.clickOnSideTab("My Applications");
		test.acsMyAccount.verifyAllMyApplicationsArePresent(myApplications);
	}
	
	@Test
	public void Step06_Select_User_As_An_ACS_Member(){
		test.acsMyAccount.clickOnSideTab("ACS Communities");
		test.acsMyAccount.selectDontWantToBeACSMember(test.acsMyAccount.map().get("ACS_Member"));
		changedValues=test.acsMyAccount.getChangedValues();
		changedValues.put("Technical Division",techDivision);
	}
	
	@Test
	public void Step07_Launch_Iweb_Application_And_Find_Member(){
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.clickOnTab("Find Individual");
		test.individualsPage.fillMemberDetailsAndSearch("Record Number", customerId);
		test.individualsPage.navigateToContactInfoMenuOnHoveringMore();
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("addresses");
		test.memberShipPage.verifyEmailIdEditedToAcsOrg(changedValues.get("Email"));
	}
	
	@Test void Step08_Verify_Telephone_And_Address_Details(){
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("telephone numbers");
		test.memberShipPage.verifyTelephoneDetails("telephone numbers", changedValues.get("TelephoneType"), changedValues.get("PhoneNumber"));
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("addresses");
		test.memberShipPage.verifyAddressDetails("addresses", changedValues, test.acsMyAccount.map().get("Member?"));
	}
	
	
}
