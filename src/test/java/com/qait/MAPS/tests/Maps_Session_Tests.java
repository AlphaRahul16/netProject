package com.qait.MAPS.tests;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class Maps_Session_Tests extends BaseTest {
	private String maps_url;
	private String[] roles = {"OPA Staff","Program Viewer","Program Chair Sessioning","Abstract Editor","Session Admin"};

	@BeforeClass
	public void Start_Test_Session() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		maps_url = YamlReader.getYamlValue("MAPS_Url");
	}

	@BeforeMethod
	public void printCaseIdExecuted(Method method) {
		test.printMethodName(method.getName());
	}
	
	/**
	 *    Test case : Submission class
	 *    MAPS_1 : launch application 
	 */
	@Test
	public void MAPS_1_Launch_Application() {
		test.launchMAPSApplication(maps_url);
		test.maps_SSOPage.verifyUserIsOnMAPSHomePage("Meeting Abstracts Programming System");
	}

	/**
	 * Test case :  Submission class
	 * MAPS_3 : login functionality
	 */
	@Test
	public void MAPS_3_LogIn_With_Valid_Credentials() {
		test.maps_SSOPage.loginWithValidCredentials(YamlReader.getYamlValue("LogIn_Details.userID"),
				YamlReader.getYamlValue("LogIn_Details.password"));
	}
	
	@Test
	public void MAPS_Session_1_Click_On_Session_In_Top_Navigation_Menu() {
		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Session");
		test.maps_SSOPage.verifyUserIsOnTabPage("Session");
		test.maps_reviewpage.verifyPageHeader("Multiple Role Selection");
		test.maps_sessionpage.verifyApplicationDisplaysRadioButtonOnClickingSessionTab(roles);
	
	}
	
//	@Test
//	public void Test04_MAPS_Session_4_User_Should_Navigate_To_Abstract_Page_On_Clicking_Go_Button() {
//		test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("OPA Staff");
//		test.maps_sessionpage.clickButtonToContinueToNextPage("Select");
//		test.maps_sessionpage.clickButtononLeftNavigationPanel("Sessioning");
//		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Abstracts Assigned To Me");
//	}
//
//	@Test
//	public void MAPS_Session_67_Verify_options_available_on_Program_Viewer_page(){
//		
//	}
	
	@Test
	public void MAPS_Session_914_Verify_User_Navigates_To_Room_Availability_Page(){
//		test.maps_SSOPage.clickOnTabOnUpperNavigationBar("Session");
		test.maps_sessionpage.clickNamedRadioButtonOnRoleSelectionPage("Session Admin");
		test.maps_sessionpage.clickButtonToContinueToNextPage("Select");
		test.maps_sessionpage.clickButtononLeftNavigationPanel("Meeting Setup");
		test.maps_sessionpage.clickSubHeadingLeftNavigationPanel("Room Availability");
		test.maps_reviewpage.verifyAbstractTitleUnderReviewModule("Room Availability");
	}
	
	@Test
	public void MAPS_Session_915_Verify_Sections_Are_Displayed_On_Room_Availability_Page(){
		test.maps_sessionpage.verifySectionsOnRoomAvailabilityPage("Rooms", 1);
		test.maps_sessionpage.verifySectionsOnRoomAvailabilityPage("Room Availability", 2);
	}
	
	@Test
	public void MAPS_Session_919_Verify_Fields_Are_Displayed_Under_Rooms_Section(){
        test.maps_sessionpage.verifyFilterDropdwonOnRoomAvailabalityPage();
        test.maps_sessionpage.verifyFieldsOnRoomAvailablityPage("Save/Edit", 1);
        test.maps_sessionpage.verifyFieldsOnRoomAvailablityPage("Delete", 1);
	}
	
	@Test
	public void MAPS_Session_920_Verify_Application_Displays_Filter_Results_On_Room_Availability_Page(){
		test.maps_sessionpage.clickOnArrowButton("Room Name");
	}
}

