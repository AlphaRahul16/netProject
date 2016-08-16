package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_AddressChangeTermsInvoice_Test extends BaseTest {
	
	String app_url_iweb,chpName;
	Map<String, String> membershipDateList = new HashMap<String, String>();
	List<String> addressType=new ArrayList<>();

	
	@BeforeClass
	public void open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_iweb = getYamlValue("app_url_IWEB");
	}
	
	@Test
	public void Step01_Launch_Iweb_Application(){
		test.launchApplication(app_url_iweb);
		test.acsAddressValidation.waitForPageReadyState();
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}
	
	@Test
	public void Step02_Select_And_Run_Query_In_Query_Individual_Page() {
		String individualName;
		test.homePageIWEB.clickOnSideBarTab("Individuals");
		test.memberShipPage.clickOnTab("Query Individual");
		test.memberShipPage.selectAndRunQuery(getYamlValue("AddressChange.queryName"));
		individualName=test.acsAddressValidation.verifyIndividualProfilePage();
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Individuals | "+individualName);
	}

	@Test
	public void Step03_Expand_individual_membership_tab_and_get_effective_date(){
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("individual memberships");
		membershipDateList=test.individualsPage.getParticularMemberDetails("individual memberships", "Active");
		test.memberShipPage.collapseDetailsMenu("individual memberships");
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("chapter memberships");
		chpName=test.individualsPage.matchEffectiveAndExpiryDate(membershipDateList, "chapter memberships");
	}
	
	@Test
	public void Step04_Naviagate_To_Contact_Info_Option_And_Add_New_Address(){
		test.individualsPage.navigateToContactInfoMenuOnHoveringMore();
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("addresses");
		addressType=test.individualsPage.verifyAddressType("addresses");
        test.individualsPage.clickOnPlusSign("addresses",2);
        test.individualsPage.addNewAddress(YamlReader.getYamlValue("AddressChange.organization"),YamlReader.getYamlValue("AddressChange.department"),
        		YamlReader.getYamlValue("AddressChange.address"),YamlReader.getYamlValue("AddressChange.city"),YamlReader.getYamlValue("AddressChange.postalCode"),
        		YamlReader.getYamlValue("AddressChange.state"),addressType);
        test.individualsPage.verifyAdditionOfNewAddress(addressType.get(0),"addresses");
	}
	
	@Test
	public void Step05_Navigate_To_Membbership_Tab_And_Verify_Transferred_Chapter(){
		test.individualsPage.navigateToGeneralMenuOnHoveringMore("Membership");
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("individual memberships");
		test.memberShipPage.expandDetailsMenuIfAlreadyExpanded("chapter memberships");
        test.individualsPage.verifyChapterStatusIsTransferred("chapter memberships", chpName);
	}
	
	@Test
	public void Step06_Verify_New_Chapter_Is_Added_Under_Chapter_Memberships_Tab(){
		test.individualsPage.verifyNewChapterIsAdded("chapter memberships");
	}

}
