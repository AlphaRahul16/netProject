package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.YamlReader;

public class ACS_Email_Preferences_Test {

	TestSessionInitiator test;
	String app_url_IWEB, webLogin, app_url_email, mailingListName,
			mailingListType,customerId,userName;
	List<String> mailingListCategories;

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IWEB");
		app_url_email = getYamlValue("app_url_email");
		mailingListName = getYamlValue("EmailCommunicationPreferences.mailingListName")
				+ System.currentTimeMillis();
		mailingListType = getYamlValue("EmailCommunicationPreferences.mailingListType");
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
	public void Step02_Goto_Marketing_module_And_Verify_User_Is_On_Marketing_Page() {

		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Marketing");
		test.homePageIWEB
				.verifyUserIsOnHomePage("Marketing | Overview | Overview");
	}

	@Test
	public void Step03_Go_To_Marketing_Setup_Page_Link_And_Verify_Mailing_List_Popup_Is_Displayed() {
		test.homePageIWEB.clickOnTab("Marketing Setup page.");
		test.awardsPageAction.clickOnPlusIcon("mailing lists");
		test.acsMarketingPageIweb
				.verifyTitleNameForMailingListPopUpIsDisplayed();
	}

	@Test

	public void Step04_Send_Information_In_Popup_And_Save_Them_Then_Verify_Popup_Is_Closed() {
		test.acsMarketingPageIweb.sendListInformationToMailingListPopUp(
				mailingListName, mailingListType);     //--
		test.homePageIWEB
				.verifyUserIsOnHomePage("Marketing | Overview | Overview");
	}

	@Test
	public void Step05_Nevigate_To_Marketting_Module_And_Verify_Addition_Of_Created_List() {
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Marketing");
		test.homePageIWEB.clickOnTab("Manage Mailing Lists");
		test.acsMarketingPageIweb
				.verifyListNameInMailingListRecord(mailingListName);
	}

	@Test
	public void Step06_Select_The_Newly_Added_List_And_Add_Member_In_List() {
		test.acsMarketingPageIweb
				.gotoListFromMailingListRecord(mailingListName);
		test.awardsPageAction.clickOnPlusIcon("list members");
		test.acsMarketingPageIweb.clickOnLookUpOption();
		test.memberShipPage.selectRandomUserOnAscendingHeader("Web Login");
		userName = test.acsMarketingPageIweb
				.getUserNameFromAddUserPopUpTextField();
		test.acsMarketingPageIweb
				.clickOnSaveButtonDisplayedOnMailingListPopUp();
		test.acsMarketingPageIweb.verifyUserNameInList();
	}

	@Test
	public void Step07_Verify_MailingList_Is_Added_In_Email_Communication_Preferences_Form_And_Is_Subscribed() {
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

	@Test
	public void Step08_Login_Into_Eweb_And_Verify_Mailing_List_Is_Subscribed_Under_The_Mailing_List_Type_Tab_And_Unsubscribe_The_User() {
		test.launchApplication(app_url_email);
		test.asm_emailPage.loginInToApplication(webLogin, "password");
		test.asm_emailPage.verifyMailingListInNewspaperHeading(mailingListName);
		test.asm_emailPage.verifyMailListIsSubscribed(mailingListName);  //
		test.asm_emailPage.changeNewsLetterActionValue(mailingListName);
	}
	
	@Test
	public void Step09_Navigate_To_Iweb_Application_And_Verify_MailingList_Is_UnSubscribed() {

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

	@Test
	public void Step10_Login_Into_Eweb_And_Unsubscribe_All_Mailing_List_And_Verify_In_Iweb() {
		test.launchApplication(app_url_email);
		test.asm_emailPage.loginInToApplication(webLogin, "password");
		mailingListCategories = test.asm_emailPage
				.getAllCategoryOfMailingList();
		test.asm_emailPage.clickOnUnsubscribeAllButton();
		test.asm_emailPage.clickOnUnsubscribeAllConfirmButton();
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB
				.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.clickOnTab("Find Individual");
		test.individualsPage.fillMemberDetailsAndSearch("Record Number", customerId);

		test.acsMarketingPageIweb
				.clickOnAdditionaInfortmationIcon("Communication Preferences");
		test.acsMarketingPageIweb
				.verifyAllListIsSubscribed(mailingListCategories);

	}

}
