package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.DateUtil;
import com.qait.automation.utils.YamlReader;
import com.qait.keywords.YamlInformationProvider;

public class ACS_FellowNominate extends BaseTest{

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
		com.qait.tests.DataProvider_FactoryClass.sheetName = "fellowNominate";
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_FellowNominate(String caseID) {
		this.caseID = caseID;
	}

	@Test
	public void Step01_TC01_CreateMember_As_A_Prerequisite_For_Fellow_Nomination() {
		test.homePageIWEB.addValuesInMap("fellowNominate", "3");
		test.homePageIWEB.clickOnAddIndividual();
		memDetails = test.addMember.enterMemberDetailsInAddIndividual();
		test.memberShipPage.goToOrderEntry();
		test.memberShipPage
				.goToAddMembershipAndFillDetails_membershipAsFellowPrequisite();
		test.memberShipPage
				.goToAddMemebrshipAndFillDetails_LocalSectionAsFellowPrequisite();
		numberOfDivisions = test.memberShipPage.getDivisionNumbers();
		test.memberShipPage
				.goToAddMembershipAndFillDetails_DivisionAsFellowPrequisite(numberOfDivisions);
		test.memberShipPage
				.selectBatchAndPaymentDetails_subscription(
						YamlReader.getYamlValue("Acs_CreateMember_IWEB.batch"),
						YamlReader
								.getYamlValue("Acs_CreateMember_IWEB.PaymentType"),
						YamlReader
								.getYamlValue("creditCardDetails.PaymentMethod.Select"),
						YamlReader
								.getYamlValue("creditCardDetails.paymentMethodVisaMC.Number"),
						YamlReader
								.getYamlValue("Acs_CreateMember_IWEB.expireDate"),
						YamlReader
								.getYamlValue("creditCardDetails.paymentMethodVisaMC.cvv-number"),
						YamlReader
								.getYamlValue("creditCardDetails.paymentMethodBOACheck.CheckNumber"));
	}

	@Test
	// For Login Into Fellow Nominate Application
	public void Step02_TC02_Enter_Valid_Credentials_To_Login_Into_Application() {
		memberDetails = test.memberShipPage.getCustomerFullNameAndContactID();
		test.launchApplication(app_url_nominateFellow);
		test.asm_FellowNomiate.loginInToApplicationByLastNameAndMemberNumber(
				memberDetails.get(0).split(" ")[0], memberDetails.get(1));
		test.asm_FellowNomiate.verifyUserIsOnFellowsDashboard();
	}

	@Test
	// For Individual
	public void Step03_TC03_Nominate_For_Individual_FellowType() {

		test.asm_FellowNomiate.preRequisiteForACSIndividualNomination(
				"Individual", "Name",
				getFellowNominated.getASM_fellowNominated("NomineeName"),
				"education");
		test.asm_FellowNomiate
				.fillAllRequiredDetailsToSubmitACSFellowsNominations("Individual");
		test.asm_FellowNomiate.clickReturnToDashBoardButton();
		test.asm_FellowNomiate.verifyUserIsAbleToViewSubmittedNominations();
		// test.asm_FellowNomiate.clickPrintPDFButton("Individual Nomination");
	}

	@Test
	// For Local Section
	public void Step04_TC04_Nominate_For_LocalSection_FellowType() {

		test.asm_FellowNomiate.preRequisiteForACSNomination("Local Section",
				"Name",
				getFellowNominated.getASM_fellowNominated("NomineeName"),
				"education");
		test.asm_FellowNomiate
				.fillAllRequiredDetailsToSubmitACSFellowsNominations("Local Section");
		test.asm_FellowNomiate.clickReturnToDashBoardButton();

	}

	@Test
	// For Technical division
	public void Step05_TC05_Nominate_For_TechnicalDivision_FellowType() {
		test.asm_FellowNomiate.preRequisiteForACSNomination(
				"Technical Division", "Name",
				getFellowNominated.getASM_fellowNominated("NomineeName"),
				"education");
		test.asm_FellowNomiate
				.fillAllRequiredDetailsToSubmitACSFellowsNominations("Technical Division");
		test.asm_FellowNomiate.clickReturnToDashBoardButton();
		test.asm_FellowNomiate.verifyNominatedPersonCannotBeNominatedAgain(
				"Technical Division", "Name");

	}

	@Test(dataProvider = "test1")
	public void Step06_TC06_Navigate_To_Iweb_And_Verify_Nomination_Details(
			String FellowType) {
		test.launchApplication(app_url_IWEB);

		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Fellows");
		test.homePageIWEB.clickOnFindNominationTab();
		test.individualsPage.selectFeildValue("fellow year", toString()
				.valueOf(DateUtil.getCurrentYear()));
		System.out.println("Fellow tupe " + FellowType);
		test.individualsPage.selectFeildValue("Fellow Type", FellowType);
		test.individualsPage.clickGoButton();
		NomineeName = test.asm_FellowNomiate
				.selectNomineeForParticularFellowType(FellowType);
		test.individualsPage.SelectFellowNominatorForVerification(
				NomineeName.trim(), memberDetails.get(0));
		test.asm_FellowNomiate
				.verifyDetailsOnIwebForFellowNomination(FellowType);

	}

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		mapFellowNominatedSmoke = YamlReader
				.getYamlValues("ACS_fellowNominatedData");
		getFellowNominated = new YamlInformationProvider(
				mapFellowNominatedSmoke);
		app_url_IWEB = getYamlValue("app_url_IWEB");
		app_url_nominateFellow = getYamlValue("app_url_nominateFellow");
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthenticationAutoIt();
	}

	@DataProvider(name = "test1")
	public static Object[][] primeNumbers() {
		return new Object[][] { { "Individual" }, { "Local Section" },
				{ "Technical Division" } };
	}

}
