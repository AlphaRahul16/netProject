package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_Email_Communication_Preferences_Test extends BaseTest{

	String app_url_IWEB, webLogin, app_url_email, mailingListName,
			mailingListType,customerId,userName;
	List<String> mailingListCategories;
	List<String>categoryList=new ArrayList<String>();
	Map<String,List<String>> mailingListMap=new HashMap<String,List<String>>();

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IWEB");
		app_url_email = getYamlValue("app_url_email");
		mailingListName = getYamlValue("EmailCommunicationPreferences.mailingListName")
				+ System.currentTimeMillis();
		mailingListType = getYamlValue("EmailCommunicationPreferences.mailingListType");
	}
	@BeforeMethod
	public void handleTestMethodName(Method method) {
		test.printMethodName(method.getName());
	}

	@Test
	public void Step01_Launch_Iweb_Application_And_Verify_User_Is_On_Marketing_Page() {
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(
				YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB
				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Marketing");
		test.homePageIWEB
				.verifyUserIsOnHomePage("Marketing | Overview | Overview");
	}

	@Test(dependsOnMethods="Step01_Launch_Iweb_Application_And_Verify_User_Is_On_Marketing_Page")
	public void Step02_Verify_Mailing_List_Popup_Is_Displayed_On_Marketing_Setup_Page_Link() {
		test.homePageIWEB.clickOnTab("Marketing Setup page.");
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("mailing list type");
		categoryList=test.acsMarketingPageIweb.getMailingCategoryList("mailing list type");
		categoryList=test.acsMarketingPageIweb.verifyVisibilityOfGivenListCategory(mailingListType);
		test.awardsPageAction.clickOnPlusIcon("mailing lists");
		test.acsMarketingPageIweb
				.verifyTitleNameForMailingListPopUpIsDisplayed();
	}


	@Test(dependsOnMethods="Step02_Verify_Mailing_List_Popup_Is_Displayed_On_Marketing_Setup_Page_Link")
	public void Step03_Create_A_New_Mailing_List(){
		test.acsMarketingPageIweb.sendListInformationToMailingListPopUp(
				mailingListName, mailingListType);     
		test.homePageIWEB
				.verifyUserIsOnHomePage("Marketing | Overview | Marketing Setup Profile");
	}


	@Test(dependsOnMethods="Step03_Create_A_New_Mailing_List")
	public void Step04_Nevigate_To_Marketing_Module_And_Verify_Addition_Of_Created_List() {
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Marketing");
		test.homePageIWEB.clickOnTab("Manage Mailing Lists");
		test.acsMarketingPageIweb
				.verifyListNameInMailingListRecord(mailingListName);
	}

	@Test(dependsOnMethods="Step04_Nevigate_To_Marketing_Module_And_Verify_Addition_Of_Created_List")
	public void Step05_Select_Added_MailingList_And_Add_Member_In_Mailing_List() {
		test.acsMarketingPageIweb
				.gotoListFromMailingListRecord(mailingListName);  //------
		test.awardsPageAction.clickOnPlusIcon("list members");
		test.acsMarketingPageIweb.clickOnLookUpOption();
		test.memberShipPage.selectRandomUserOnAscendingHeader("Web Login");  //-----
		userName = test.acsMarketingPageIweb
				.getUserNameFromAddUserPopUpTextField();
		test.acsMarketingPageIweb
				.clickOnSaveButtonDisplayedOnMailingListPopUp();
		test.acsMarketingPageIweb.verifyUserNameInList();
	}

	@Test(dependsOnMethods="Step05_Select_Added_MailingList_And_Add_Member_In_Mailing_List")
	public void Step06_Verify_Subscribed_MailingList_Is_Added_In_Email_Communication_Preferences_Form() {
		test.acsMarketingPageIweb.gotoArrowOfGivenUser();
		webLogin = test.memberShipPage.getMemberWebLogin();
		customerId=test.memberShipPage.getCustomerID();
		test.acsMarketingPageIweb
				.clickOnAdditionaInfortmationIcon("Communication Preferences");
		test.acsMarketingPageIweb.expandListTypeInComm_Pref(mailingListType);
		test.acsMarketingPageIweb
				.verifyMailingListIsSubscribedOrUnsubscribedInExpandedListType(mailingListName,"subscribed");
		test.acsMarketingPageIweb
				.clickOnCancelButtonInCommunicationPreferencesPopUp();
	}

	@Test(dependsOnMethods="Step06_Verify_Subscribed_MailingList_Is_Added_In_Email_Communication_Preferences_Form")
	public void Step07_Login_Into_Eweb_And_Unsubscribe_Newly_Added_Subscribed_Mailing_List() {
		test.launchApplication(app_url_email);
		test.asm_emailPage.loginInToApplication(webLogin, "password");
		test.asm_emailPage.verifyMailingListInNewspaperHeading(mailingListName);
		test.asm_emailPage.verifyMailListIsSubscribed(mailingListName);  
		test.asm_emailPage.changeNewsLetterActionValue(mailingListName);
	}
	

	@Test(dependsOnMethods="Step07_Login_Into_Eweb_And_Unsubscribe_Newly_Added_Subscribed_Mailing_List")
	public void Step08_Navigate_To_Iweb_Application_And_Verify_MailingList_Is_UnSubscribed() {
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB
				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.clickOnTab("Find Individual");
		test.individualsPage.fillMemberDetailsAndSearch("Record Number", customerId);
		test.acsMarketingPageIweb
				.clickOnAdditionaInfortmationIcon("Communication Preferences");
		test.acsMarketingPageIweb.expandListTypeInComm_Pref(mailingListType);
		test.acsMarketingPageIweb
				.verifyMailingListIsSubscribedOrUnsubscribedInExpandedListType(mailingListName,"unsubscribed");
		test.acsMarketingPageIweb
				.clickOnCancelButtonInCommunicationPreferencesPopUp();
		}


	@Test(dependsOnMethods="Step08_Navigate_To_Iweb_Application_And_Verify_MailingList_Is_UnSubscribed")
	public void Step09_Login_Into_Eweb_And_Unsubscribe_All_Mailing_List(){
		test.launchApplication(app_url_email);
		test.asm_emailPage.loginInToApplication(webLogin, "password");	
		mailingListCategories = test.asm_emailPage
				.getAllCategoryOfMailingList();
		mailingListMap=test.asm_emailPage.getMailingTypesList(categoryList);
		test.asm_emailPage.clickOnUnsubscribeAllButton();
		test.asm_emailPage.clickOnUnsubscribeAllConfirmButton();
	}
	
	@Test(dependsOnMethods="Step09_Login_Into_Eweb_And_Unsubscribe_All_Mailing_List")
	public void Step10_Launch_Iweb_Application_And_Verify_All_Mailing_Lists_Are_Unsubscribed(){
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB
				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.clickOnTab("Find Individual");
		test.individualsPage.fillMemberDetailsAndSearch("Record Number",customerId ); 

		test.acsMarketingPageIweb
				.clickOnAdditionaInfortmationIcon("Communication Preferences");
//		test.acsMarketingPageIweb
//				.verifyAllListIsSubscribed(mailingListCategories);
		test.acsMarketingPageIweb.verifyAllListIsUnsubscribedOnIweb(mailingListMap, mailingListCategories);
	}

}
