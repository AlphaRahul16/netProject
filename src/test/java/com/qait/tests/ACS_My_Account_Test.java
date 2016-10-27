package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_My_Account_Test extends BaseTest {

	String app_url_IWEB, app_url_MyAccount,app_url_MyAccount_newUser, userName, customerId,telephoneType,techDivision, webLogin ;
	int number;
	private String caseID;
	Map<String, Boolean> skipTest = new HashMap<String, Boolean>();
	Map<String, String> changedValues= new HashMap<>();
	List<String> memberDetails=new ArrayList<>();
	List<String> techDivisions=new ArrayList<>();
	String myApplications[]={"ACS Fellows","Member Get A Member","Email Newsletters","Gift a Membership"};

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_MyAccount = getYamlValue("app_url_MyAccount");
		app_url_MyAccount_newUser=getYamlValue("app_url_MyAccountNewUser");
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
		Reporter.log("******Case Id :"+caseID+"******\n",true);
		number =test.acsMyAccount.generateThreeDidgitRandomNumber(9999, 999);
		userName=test.acsMyAccount.map().get("First_Name")+number;
		webLogin=test.acsMyAccount.map().get("UserName") + number;
		test.launchApplication(app_url_MyAccount_newUser);
//		test.acsMyAccount.clickOnLoginButton("Log In");
//		test.acsMyAccount.clickOnLoginButton("Registering is easy");
		test.acsMyAccount.createNewUser(test.acsMyAccount.map().get("Email"),test.acsMyAccount.map().get("First_Name"),test.acsMyAccount.map().get("Last_Name")
				,webLogin,test.acsMyAccount.map().get("Password"),number);	
		test.acsMyAccount.clickOnCreateAccountButton("submit_button");
		test.acsMyAccount.clickOnACSWButton("Continue to www.acs.org");
		telephoneType="Home";
	}

	@Test
	public void Step02_Launch_Iweb_Application_And_Find_Existing_Member() {
		Reporter.log("******Case Id :"+caseID+"******\n",true);
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
		webLogin = test.memberShipPage.getMemberWebLogin();
		memberDetails=test.memberShipPage.getMemberDetails();
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("chapter memberships");
		techDivisions=test.memberShipPage.getTechnicalDivisions();
		test.individualsPage.navigateToContactInfoMenuOnHoveringMore();
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("telephone numbers");
		telephoneType=test.memberShipPage.getTelephoneNumberType("telephone numbers");
		test.launchApplication(app_url_MyAccount);
		test.acsMyAccount.clickOnLoginButton("Log In");
		test.acsMyAccount.logInToMyAccount(webLogin,"password");
		userName=memberDetails.get(2)+ " "+memberDetails.get(0);
		number = test.acsMyAccount.generateThreeDidgitRandomNumber(9999, 999);
	}

	@Test
	public void Step03_Verify_Account_Is_Registered_And_Edit_Email() {
		test.acsMyAccount.handleFeedbackForm();
		test.acsMyAccount.verifyUserName(userName, test.acsMyAccount.map().get("Last_Name"),test.acsMyAccount.map().get("Member?"),userName);
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
	 test.acsMyAccount.editPhoneNumber(test.acsMyAccount.map().get("Phone_Number")+number,test.acsMyAccount.map().get("Primary_Member?"),telephoneType);
	 test.acsMyAccount.clickOnEmailSaveButton("home");
	 test.acsMyAccount.verifyEmailSavedMessage(getYamlValue("ACS_MyAccount.phoneSaveMessage"),"msg_phoneSave");
	 test.acsMyAccount.verifyPriamryImageIsPresent("img_phonePrimary", "HomePhone");
	 test.acsMyAccount.changeAddress(test.acsMyAccount.map().get("Member?"), test.acsMyAccount.map());
	 test.acsMyAccount.verifySaveAddressMessage(getYamlValue("ACS_MyAccount.addressSaveMessage"));
	 test.acsMyAccount.clickOnCloseButton();
	 test.acsMyAccount.verifyPriamryImageIsPresent("img_addressPrimary", "WorkAddress");
	 }
	
	@Test
	public void Step05_Verify_Techincal_Divisions_And_My_Applications(){  
		test.acsMyAccount.clickOnSideTab("Technical Divisions");
		test.acsMyAccount.verifyTechnicalDivisions(techDivisions);
		test.acsMyAccount.clickOnSideTab("My Applications");
		test.acsMyAccount.verifyAllMyApplicationsArePresent(myApplications);
	}
	
	@Test
	public void Step06_Select_User_As_An_ACS_Member(){
		test.acsMyAccount.clickOnSideTab("ACS Communities");
		test.acsMyAccount.selectDontWantToBeACSMember(test.acsMyAccount.map().get("ACS_Member"));
		changedValues=test.acsMyAccount.getChangedValues();
	}
	
	@Test 
	public void Step07_Enter_New_Password_And_Verify_Changed_Password(){
		test.acsMyAccount.clickOnSideTab("Password");
		test.acsMyAccount.enterNewPassword(test.acsMyAccount.map().get("Password"), test.acsMyAccount.map().get("New_Password"));
		test.memberShipPage.waitForSpinner();
		test.acsMyAccount.handleFeedbackForm();
		test.acsMyAccount.clickOnLoginButton("Log In");
		test.acsMyAccount.logInToMyAccount(webLogin, test.acsMyAccount.map().get("New_Password"));
		test.acsMyAccount.handleFeedbackForm();
		test.acsMyAccount.verifyUserName(userName, test.acsMyAccount.map().get("Last_Name"),test.acsMyAccount.map().get("Member?"),userName);
		test.acsMyAccount.navigateToAccountLink("My Account");
		test.acsMyAccount.clickOnSideTab("Password");
        test.acsMyAccount.enterNewPassword(test.acsMyAccount.map().get("New_Password"),  test.acsMyAccount.map().get("Password"));
	}
	
	@Test
	public void Step08_Launch_Iweb_Application_Find_Member_And_Verify_Email_Address(){
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.clickOnTab("Find Individual");
		test.individualsPage.fillMemberDetailsAndSearch("Record Number", customerId);
		test.individualsPage.navigateToContactInfoMenuOnHoveringMore();
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("addresses");
		test.memberShipPage.verifyEmailIdEditedToAcsOrg(changedValues.get("Email"));
	}
	
	@Test 
	public void Step09_Verify_Telephone_Number_And_Address_Details(){
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("telephone numbers");
		test.memberShipPage.verifyTelephoneDetails("telephone numbers", changedValues.get("TelephoneType"), changedValues.get("PhoneNumber"));
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("addresses");
		test.memberShipPage.verifyAddressDetails("addresses", changedValues, test.acsMyAccount.map().get("Member?"));
	}
	
	@Test
	public void Step10_Verify_User_Is_ACS_Member(){
		test.memberShipPage.clickOnEditContactInfo();
		test.memberShipPage.verifyIsAcsNetworkMember(test.acsMyAccount.map().get("ACS_Member"));
	}
}
