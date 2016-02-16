package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.beust.jcommander.Parameter;
import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;
import com.qait.keywords.ASM_DonatePage;
import com.qait.keywords.YamlInformationProvider;

public class ACS_FellowNominate {
	TestSessionInitiator test;
	String app_url_nominateFellow;
	private String[] memDetails;
	int numberOfDivisions;
	private String caseID;
	String app_url_IWEB;
	String NomineeName;
	List<String> memberDetails;
	YamlInformationProvider getFellowNominated;
	Map<String, Object> mapFellowNominatedSmoke;
	Map<String, String> mapFellowNomineeDetails;
	
	public ACS_FellowNominate() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "createMember";
	}
	
	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_FellowNominate(String caseID) {
		this.caseID = caseID;
	}
	
	
	@Test
	public void Step01_TC01_CreateMember_As_A_Prerequisite_For_Fellow_Nomination() {
		test.homePageIWEB.addValuesInMap("createMember", caseID);
		test.homePageIWEB.enterAuthentication("C00616","password");
		test.homePageIWEB.clickOnAddIndividual();
		memDetails = test.addMember.enterMemberDetailsInAddIndividual();
		test.memberShipPage.goToOrderEntry();
		test.memberShipPage.goToAddMembershipAndFillDetails_membership();
		test.memberShipPage.goToAddMemebrshipAndFillDetails_LocalSection();
		numberOfDivisions = test.memberShipPage.getDivisionNumbers();
		test.memberShipPage
				.goToAddMembershipAndFillDetails_Division(numberOfDivisions);
		test.memberShipPage.selectBatchAndPaymentDetails_subscription(
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.batch"),
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.PaymentType"),
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.paymentMethod"),
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.cardNumber"),
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.expireDate"),
				YamlReader.getYamlValue("Acs_CreateMember_IWEB.cvvNumber"));
	}
	
	
	@Test
	public void Step02_TC02_Enter_Valid_Credentials_To_Login_Into_Application() {

    	memberDetails = test.memberShipPage.getCustomerFullNameAndContactID();
	
		// For Login 
    	test.launchApplication(app_url_nominateFellow);
		test.asm_FellowNomiate.loginInToApplicationByLastNameAndMemberNumber(memberDetails.get(0).split(" ")[0],memberDetails.get(1));
    

    	//test.asm_FellowNomiate.loginInToApplicationByLastNameAndMemberNumber("LN1455524487370","30956094");
		test.asm_FellowNomiate.verifyUserIsOnFellowsDashboard();
		
		// For Individual
		
		test.asm_FellowNomiate.preRequisiteForACSIndividualNomination("Individual", "Name",getFellowNominated.getASM_fellowNominated("NomineeName"),"education");
		test.asm_FellowNomiate.fillAllRequiredDetailsToSubmitACSFellowsNominations("Individual");
		test.asm_FellowNomiate.clickReturnToDashBoardButton();
	
		// For Local Section
		
		test.asm_FellowNomiate.preRequisiteForACSNomination("Local Section", "Name",
				getFellowNominated.getASM_fellowNominated("NomineeName"), "education");
		test.asm_FellowNomiate.fillAllRequiredDetailsToSubmitACSFellowsNominations("Local Section");
		test.asm_FellowNomiate.clickReturnToDashBoardButton();

		// For Technical division
		
		test.asm_FellowNomiate.preRequisiteForACSNomination("Technical Division","Name",getFellowNominated.getASM_fellowNominated("NomineeName"),"education");
		test.asm_FellowNomiate.fillAllRequiredDetailsToSubmitACSFellowsNominations("Technical Division");
	test.launchApplication(app_url_IWEB);
	test.homePageIWEB.enterAuthentication("C00186","ACS2016!");
	}
    

	@Test(dataProvider = "test1")
	public void Step03_TC03_Navigate_To_Iweb(String FellowType) {

		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Fellows");
		test.homePageIWEB.clickOnFindNominationTab();
		test.individualsPage.selectFeildValue("fellow year",toString().valueOf(DateUtil.getCurrentYear()));
		System.out.println(FellowType);
		test.individualsPage.selectFeildValue("Fellow Type",FellowType);
		test.individualsPage.clickGoButton();
		test.asm_FellowNomiate.printmap();
		NomineeName=test.asm_FellowNomiate.selectNomineeForParticularFellowType(FellowType);
		test.individualsPage.SelectFellowNominatorForVerification(NomineeName,memberDetails.get(0));
		//test.individualsPage.SelectFellowNominatorForVerification("Dean Bauer","LN1455524487370 Selenium FellowMN");
		test.asm_FellowNomiate.verifyDetailsOnIwebForFellowNomination();
		
	}
	
	
	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		mapFellowNominatedSmoke = YamlReader
				.getYamlValues("ACS_fellowNominatedData");
		getFellowNominated = new YamlInformationProvider(
				mapFellowNominatedSmoke);
		app_url_IWEB = getYamlValue("app_url_IWEB");
		app_url_nominateFellow=getYamlValue("app_url_nominateFellow");
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication("C00616",
		"password");
	}
	
	@AfterClass
	public void Close_Browser_Session() {
		//test.closeBrowserSession();
	}
	
	
	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);

	}
	 @DataProvider(name = "test1")
	   public static Object[][] primeNumbers() {
	      return new Object[][] {{"Individual"}, {"Local Section"}, {"Technical Division"}};
	   }

}
