package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import java.lang.reflect.Method;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.getpageobjects.BaseTest;
import com.qait.automation.utils.YamlReader;

public class ACS_Rubber_Insurance_Test extends BaseTest {
	String app_url_IWEB, individualName, customerId, importedFile;
	String caseID;

	public ACS_Rubber_Insurance_Test() {
		com.qait.tests.DataProvider_FactoryClass.sheetName = "Import_Profile";
	}

	@Factory(dataProviderClass = com.qait.tests.DataProvider_FactoryClass.class, dataProvider = "data")
	public ACS_Rubber_Insurance_Test(String caseID) {
		this.caseID = caseID;
	}

	@Test
	public void Step01_Launch_Iweb_Application_And_Navigate_To_Add_file() {
		test.homePageIWEB.addValuesInMap("Import_Profile", caseID);
		test.launchApplication(app_url_IWEB);
		test.homePageIWEB.enterAuthentication(YamlReader.getYamlValue("Authentication.userName"),
				YamlReader.getYamlValue("Authentication.password"));
		test.homePageIWEB.clickOnModuleTab();
		test.homePageIWEB.clickOnTab("Membership");
		test.homePageIWEB.clickOnSideBarTabACS("ACS");
		test.homePageIWEB.clickOnTab("Add File");
		test.homePageIWEB.verifyUserIsOnHomePage("Add - ACS Import Match");
	}

	@Test(dependsOnMethods="Step01_Launch_Iweb_Application_And_Navigate_To_Add_file")
	public void Step02_Import_File_For_Rubber_Insurance() {
		importedFile = test.memberShipPage.importProfileSheet(test.homePageIWEB
				.map().get("Import_Name").trim(),
				test.homePageIWEB.map().get("Description").trim(),
				test.homePageIWEB.map().get("File_Type").trim());
		test.memberShipPage.selectFileToUpload(test.homePageIWEB.map()
				.get("Import_File").trim());
		test.memberShipPage.clickOnSaveButtonForBillingAddress();
	}

	@Test(dependsOnMethods="Step02_Import_File_For_Rubber_Insurance")
	public void Step03_Verify_ACS_Import_Match_Profile_Page_And_Match_total_child_form_is_not_null() {
		test.memberShipPage.verifyACSImportMatchProfilePage(importedFile,
				test.homePageIWEB.map().get("Description"), test.homePageIWEB
						.map().get("File_Type"),
				test.homePageIWEB.map().get("Import_File"));
		test.memberShipPage.verifyMatchTotalChildForm();
		test.homePageIWEB.clickOnTab("List File");

		test.memberShipPage.verifyImportedFileIsPresentInList(importedFile);
	}

	@BeforeClass
	public void OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_IWEB = getYamlValue("app_url_IWEB");
		Reporter.log("App URL Iweb:: " + app_url_IWEB + "\n");

	}

	@BeforeMethod
	public void handleTestMethodName(Method method) {
		test.printMethodName(method.getName());
	}
}
