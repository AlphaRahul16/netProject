package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_YB_Iweb_Regression_Test extends BaseTest {

	String app_url_iweb_yb;
	
	String[] divisionFields={"tax id:","officer change month:"};
	String[] localSectionFields={"district number:","district number rest:","primary region:","officer change month:","tax id:"};
	String[] regionalMeetingFields={"Location city:","Location state:","Host:"};
	String[] acsCommitteeFields={"conflict of interest?","age minimum:","age maximum:","yellowbook committee?","board chair appointed?","committee term length:","max number of terms:",
			"council membership required?","acs membership required?","required ratio of councilors:","display preference form online?"};

	@BeforeClass
	public void Open_Browser_Window() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_iweb_yb = getYamlValue("app_url_iweb_yb");
	}
	
	@DataProvider(name="committeFields")
	public Object[][] answerDetails(){
		return new Object[][] {{divisionFields,"Division"},{localSectionFields,"Local Section"},{regionalMeetingFields,"Regional Meeting"},{acsCommitteeFields,"ACS Committee"}};
	}
	
	@Test
	public void Step01_Launch_Iweb_Application_For_Yellow_Book() {
		test.launchApplication(app_url_iweb_yb);
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.verifyUserIsOnHomePage("CRM | Overview | Overview and Setup");
	}
	
	@Test
	public void Step02_Navigate_To_Committee_Module_And_Find_Division_Type_Committee(){
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Committee");
		test.homePageIWEB.verifyUserIsOnHomePage("Committee | Overview | Overview and Setup");
		test.homePageIWEB.clickOnTab("Management");
	}
	
	@Test(dataProvider="committeFields")
	public void Step03_Find_Division_Type_Committee_And_Verify_Details(String[] committeeFields, String committeeType){
		test.homePageIWEB.clickOnTab("Find Committee");
		test.homePageIWEB.verifyUserIsOnHomePage("Find - Committee");
		test.acsYbIweb.findCommitteeOnTypeCodeBasis(committeeType);
		test.individualsPage.clickGoButton();
		test.memberShipPage.clickOnAnyRandomMember();
		test.acsYbIweb.getCommitteeName();
		test.acsYbIweb.verifyCommitteeType("type", committeeType);
		test.acsYbIweb.clickOnEditButton();
		test.acsYbIweb.verifyFieldIsPresent(committeeFields, committeeType);
	}
	
	@BeforeMethod
	public void handleTestMethodName(Method method) {
		test.printMethodName(method.getName());
	}
}
