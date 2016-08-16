package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_Email_Preferences_Test extends BaseTest{

	TestSessionInitiator test;
	String app_url_IWEB, webLogin, app_url_email, mailingListName,
			mailingListType, userName;

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IWEB");
		app_url_email = getYamlValue("app_url_email");
		 mailingListName = getYamlValue("mailingListName")
		 + System.currentTimeMillis();
	
		mailingListType = getYamlValue("mailingListType");
		
	}

	@Test
	public void Step01_Launch_Iweb_Application_And_Verify_User_Is_On_Home_Page() {
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(
				YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB
				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test
	public void Step02_Goto_Marketing_module_And_Verify_User_Is_On_Marketting_Module() {

		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Marketing");
		test.homePageIWEB
				.verifyUserIsOnHomePage("Marketing | Overview | Overview");
	}

	@Test
	public void Step03_Go_To_Marketting_Setup_Page_Link_Then_Goto_PlusSign_Button_Of_MailingList_Tab_And_Verify_Popup_Is_Opened() {
		test.homePageIWEB.clickOnTab("Marketing Setup page.");
		test.awardsPageAction.clickOnPlusIcon("mailing lists");
		test.acsMarketingPageIweb.verifyTitleNameForMailingListPopUp();
	}

	@Test
	public void Step04_Send_Information_In_Popup_And_Save_Them_Then_Verify_Popup_Is_Closed() {

		test.acsMarketingPageIweb.sendListInformationToMailingListPopUp(mailingListName, mailingListType);
		test.acsMarketingPageIweb.clickOnSaveButtonDisplayedOnMailingListPopUp();
		test.homePageIWEB
				.verifyUserIsOnHomePage("Marketing | Overview | Overview");
	}

	@Test
	public void Step05_Nevigate_Back_To_Marketting_Module_Then_Click_On_Create_Manage_Mailing_Lists_And_Verify_Created_List_Is_Displayed() {
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Marketing");
		test.homePageIWEB.clickOnTab("Manage Mailing Lists");
		test.acsMarketingPageIweb.verifyListNameInMailingListRecord(mailingListName);
	}

	@Test
	public void Step06_Goto_The_List_And_Add_Some_Members_In_List_And_Verify_Added_User_Is_In_The_List() {
		test.acsMarketingPageIweb.gotoListFromMailingListRecord(mailingListName);
		test.awardsPageAction.clickOnPlusIcon("list members");
		test.acsMarketingPageIweb.clickOnLookUpOption();
		test.acsMarketingPageIweb.clickOnRandomUser();
		userName = test.acsMarketingPageIweb.getUserNameFromAddUserPopUpTextField();
		test.acsMarketingPageIweb.clickOnSaveButtonDisplayedOnMailingListPopUp();
		test.acsMarketingPageIweb.verifyUserNameInList();
	}

	@Test
	public void Step07_Goto_Added_User_Profile_Page_Then_Goto_Comm_Prefs_And_Verify_MailingList_Is_In_The_Respectabls_Tab_And_Subscribed() {
		test.acsMarketingPageIweb.gotoArrowOfGivenUser();
		webLogin = test.memberShipPage.getMemberWebLogin();
		test.acsMarketingPageIweb
				.clickOnAdditionaInfortmationIcon("Communication Preferences");
		test.acsMarketingPageIweb.expandListTypeInComm_Pref(mailingListType);
		test.acsMarketingPageIweb
				.verifyMailingListIsSubscribed(mailingListName);
	}

	@Test
	public void Step08_Login_Into_Eweb_And_Verify_Mailing_List_Is_Subscribed_Under_The_Mailing_List_Type_Tab_And_Unsubscribe_The_User() {
		test.launchApplication(app_url_email);
		test.asm_emailPage.loginInToApplication(webLogin, "password");
		test.asm_emailPage
				.verifyMailingListInNewspaperHeading(mailingListName);
		test.asm_emailPage.verifyMailListIsSubscribed(mailingListName);
		test.asm_emailPage
				.changeNewsLetterActionValue(mailingListName);
	}

	@Test
	public void Step09_Goto_User_Profile_In_Iweb_Application_Then_Goto_Comm_Prefs_And_Verify_MailingList_Is_In_The_Respectabls_Tab_And_UnSubscribed() {

		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.clickOnTab("Find Individual");
		test.individualsPage.fillMemberDetailsAndSearch("Web Login", webLogin);
		test.acsMarketingPageIweb
		.clickOnAdditionaInfortmationIcon("Communication Preferences");
		test.acsMarketingPageIweb.expandListTypeInComm_Pref(mailingListType);
		test.acsMarketingPageIweb
		.clickOnAdditionaInfortmationIcon("Communication Preferences");
		test.acsMarketingPageIweb.expandListTypeInComm_Pref(mailingListType);
		test.acsMarketingPageIweb.verifyMailingListIsUnSubscribed(mailingListName);
		
	}

}
