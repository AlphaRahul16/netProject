package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_Fundraising_Module_Test extends BaseTest {
	String app_url_IWEB;
	private String caseID;
	double previousAmount, newAmount;

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IWEB");
	}

	public ACS_Fundraising_Module_Test() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "Fundraising";
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_Fundraising_Module_Test(String caseID) {
		this.caseID = caseID;
	}

	@Test
	public void Step01_Launch_Iweb_Application() {
		test.homePageIWEB.addValuesInMap("Fundraising", caseID);
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}

	@Test
	public void Step02_Navigate_To_Fundraising_Module_And_Find_A_Constituent() {
		test.homePageIWEB.clickOnModuleTab();
		test.memberShipPage.clickOnTab("Fundraising");
		test.homePageIWEB.clickOnSideBarTab("Constituents");
		test.homePageIWEB.clickOnTab("Find Constituent");
		test.acsFundraising.findConstituent(0, test.memberShipPage.map().get("Constituent Sort Name?"),
				"Constituent Sort Name");
		test.memberShipPage.clickOnGoButton();
	}

	@Test
	public void Step03_Select_Random_Constituent_And_Verify_Gift_Information() {
		test.memberShipPage.clickOnRandomPage(10, 1);
		test.acsFundraising.selectAnyRandomConstituent();
		test.acsFundraising.verifyGiftInformation();
	}

	@Test
	public void Step04_Add_A_Gift() {
		test.acsFundraising.clickOnAddGiftButton("add gift");
		test.acsFundraising.switchToFrame("iframe1");
		test.acsFundraising.selectFundCode("fund code", test.memberShipPage.map().get("Fund Code?"));
		test.memberShipPage.waitForSpinner();
		test.acsFundraising.verifyGiftDate();
		test.acsFundraising.verifyGiftType("Outright");
		// test.acsFundraising.selectFundCode("campaign code",
		// test.memberShipPage.map().get("Fundraising"));
		test.acsFundraising.enterGiftAmount(test.memberShipPage.map().get("Gift Amount?"), "gift amount");
		test.memberShipPage.waitForSpinner();
		test.acsFundraising.verifyDeductibleAmount(test.memberShipPage.map().get("Gift Amount?"), "tax deductible amt");
	}

}
