package com.qait.tests;

import static com.qait.automation.utils.YamlReader.getYamlValue;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qait.automation.TestSessionInitiator;
import com.qait.automation.utils.DataProvider;
import com.qait.automation.utils.YamlReader;

public class ASM_Store_Smoke {
	TestSessionInitiator test;
	String app_url_Store;
	String headerName = this.getClass().getSimpleName();

	@Test
	public void Step01_TC01_EnterInvalidTextInSearchFieldAndVerifyASMErrorPresent() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.searchText(DataProvider.getColumnData(tcId,
				headerName));
		test.asmErrorPage.verifyASMError(YamlReader
				.getYamlValue("ASM_URLRejectedErrorMsz"));
	}

	@Test
	public void Step02_TC02_VerifyASMErrorNotPresentOnEnterValidTextInSearchField() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.searchText(DataProvider.getColumnData(tcId,
				headerName));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASMErrorPageTitle"));
		test.asm_storePage.verifySearchSuccessfully();
	}

	@Test
	public void Step03_TC03_VerifyASMErrorNotPresentForValidUserNameToLoginInToApplication() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.loginIntoApplication(DataProvider.getColumnData(
				tcId, headerName), YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASMErrorPageTitle"));
		test.asm_storePage.verifyLoginSuccessfully();
	}

	@Test
	public void Step04_TC04_VerifyASMErrorNotPresentForValidPasswordToLoginInToApplication() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.loginIntoApplication(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				DataProvider.getColumnData(tcId, headerName));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASMErrorPageTitle"));
		test.asm_storePage.verifyLoginSuccessfully();
	}

	@Test
	public void Step05_TC05_VerifyASMErrorNotPresentForInValidPasswordToLoginInToApplication() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.loginIntoApplication(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				DataProvider.getColumnData(tcId, headerName));
		test.asmErrorPage.verifyASMErrorNotPresent(YamlReader
				.getYamlValue("ASMErrorPageTitle"));
		test.asm_storePage.verifyNotLoginSuccessfully();
	}

	@Test
	public void Step06_TC06_VerifyASMErrorNotPresentForValidFirstNameInShippingAddress() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForShippingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidShippingInformation();
		test.asm_storePage.enterShippingAddress("FirstName",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.appAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToPaymentInfoPage();
	}

	@Test
	public void Step07_TC07_VerifyASMErrorNotPresentForValidMiddleNameInShippingAddress() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForShippingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidShippingInformation();
		test.asm_storePage.enterShippingAddress("MiddleName",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.appAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToPaymentInfoPage();
	}

	@Test
	public void Step08_TC08_VerifyASMErrorNotPresentForValidLastNameInShippingAddress() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForShippingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidShippingInformation();
		test.asm_storePage.enterShippingAddress("LastName",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.appAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToPaymentInfoPage();
	}

	@Test
	public void Step09_TC09_VerifyASMErrorNotPresentForValidCompanyNameInShippingAddress() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForShippingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidShippingInformation();
		test.asm_storePage.enterShippingAddress("CompanyName",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.appAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToPaymentInfoPage();
	}

	@Test
	public void Step10_TC10_VerifyASMErrorNotPresentForInValidCompanyNameInShippingAddress() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForShippingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidShippingInformation();
		test.asm_storePage.enterShippingAddress("CompanyName",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.appAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToPaymentInfoPage();
	}

	@Test
	public void Step11_TC11_VerifyASMErrorNotPresentForValidDepartmentNameInShippingAddress() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForShippingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidShippingInformation();
		test.asm_storePage.enterShippingAddress("Department",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.appAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToPaymentInfoPage();
	}

	@Test
	public void Step12_TC12_VerifyASMErrorNotPresentForInValidAddressInShippingAddress() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForShippingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidShippingInformation();
		test.asm_storePage.enterShippingAddress("Adr_Line1",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.appAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToPaymentInfoPage();
	}

	@Test
	public void Step13_TC13_VerifyASMErrorNotPresentForValidAddressInShippingAddress() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForShippingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidShippingInformation();
		test.asm_storePage.enterShippingAddress("Adr_Line1",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.appAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToPaymentInfoPage();
	}

	@Test
	public void Step14_TC14_VerifyASMErrorNotPresentForValidAddress2InShippingAddress() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForShippingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidShippingInformation();
		test.asm_storePage.enterShippingAddress("Adr_Line2",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.appAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToPaymentInfoPage();
	}

	@Test
	public void Step15_TC15_VerifyASMErrorNotPresentForValidCityInShippingAddress() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForShippingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidShippingInformation();
		test.asm_storePage.enterShippingAddress("City",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.appAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToPaymentInfoPage();
	}

	@Test
	public void Step16_TC16_VerifyASMErrorNotPresentForValidStateInShippingAddress() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForShippingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidShippingInformation();
		test.asm_storePage.selectShippingAddress("State",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.appAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToPaymentInfoPage();
	}

	@Test
	public void Step17_TC17_VerifyASMErrorNotPresentForValidZipCodeInShippingAddress() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForShippingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidShippingInformation();
		test.asm_storePage.enterShippingAddress("ZipCode",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.appAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToPaymentInfoPage();
	}

	@Test
	public void Step18_TC18_VerifyASMErrorNotPresentForValidProvinceInShippingAddress() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForShippingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidShippingInformation();
		test.asm_storePage.enterShippingAddress("Province",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.appAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToPaymentInfoPage();
	}

	@Test
	public void Step19_TC19_VerifyASMErrorNotPresentForValidFirstNameInBillingAddress() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForBillingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.clickOnNewAddressInBillingAddress();
		test.asm_storePage.FillValidBillingInformation();
		test.asm_storePage.enterBillingAddress("FirstName",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.appAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToPaymentInfoPage();
	}

	@Test
	public void Step20_TC20_VerifyASMErrorNotPresentForValidMiddleNameInBillingAddress() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForBillingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidBillingInformation();
		test.asm_storePage.enterBillingAddress("MiddleName",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.appAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToPaymentInfoPage();
	}

	@Test
	public void Step21_TC21_VerifyASMErrorNotPresentForValidLastNameInBillingAddress() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForBillingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidBillingInformation();
		test.asm_storePage.enterBillingAddress("LastName",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.appAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToPaymentInfoPage();
	}

	@Test
	public void Step22_TC22_VerifyASMErrorNotPresentForValidCompanyNameInBillingAddress() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForBillingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidBillingInformation();
		test.asm_storePage.enterBillingAddress("Company",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.appAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToPaymentInfoPage();
	}

	@Test
	public void Step23_TC23_VerifyASMErrorNotPresentForInValidCompanyNameInBillingAddress() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForBillingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidBillingInformation();
		test.asm_storePage.enterBillingAddress("Company",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.appAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToPaymentInfoPage();
	}

	@Test
	public void Step24_TC24_VerifyASMErrorNotPresentForValidDepartmentNameInBillingAddress() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForBillingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidBillingInformation();
		test.asm_storePage.enterBillingAddress("Department",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.appAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToPaymentInfoPage();
	}

	@Test
	public void Step25_TC25_VerifyASMErrorNotPresentForValidAddressInBillingAddress() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForBillingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidBillingInformation();
		test.asm_storePage.enterBillingAddress("Adr_Line1",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.appAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToPaymentInfoPage();
	}

	@Test
	public void Step26_TC26_VerifyASMErrorNotPresentForValidAddress2InBillingAddress() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForBillingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidBillingInformation();
		test.asm_storePage.enterBillingAddress("Adr_Line2",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.appAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToPaymentInfoPage();
	}

	@Test
	public void Step27_TC27_VerifyASMErrorNotPresentForValidCityInBillingAddress() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForBillingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidBillingInformation();
		test.asm_storePage.enterBillingAddress("City",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.appAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToPaymentInfoPage();
	}

	@Test
	public void Step28_TC28_VerifyASMErrorNotPresentForValidStateInBillingAddress() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForBillingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidBillingInformation();
		test.asm_storePage.selectBillingAddress("State",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.appAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToPaymentInfoPage();
	}

	@Test
	public void Step29_TC29_VerifyASMErrorNotPresentForValidZipCodeInBillingAddress() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForBillingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidBillingInformation();
		test.asm_storePage.enterBillingAddress("ZipCode",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.appAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToPaymentInfoPage();
	}

	@Test
	public void Step30_TC30_VerifyASMErrorNotPresentForValidProvinceInBillingAddress() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForBillingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidBillingInformation();
		test.asm_storePage.enterBillingAddress("Province",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.appAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToPaymentInfoPage();
	}

	@Test
	public void Step31_TC31_VerifyASMErrorNotPresentForValidPhoneNumberInAdditionalContactInformation() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForBillingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidBillingInformation();
		test.asm_storePage.enterAdditionalContactInformation();
		test.asm_storePage.enterEmail_Phone("Phone",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.appAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToPaymentInfoPage();
	}

	@Test
	public void Step32_TC32_VerifyASMErrorNotPresentForValidEmailIdInAdditionalContactInformation() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForBillingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidBillingInformation();
		test.asm_storePage.checkAddNewShippingPhoneNumber();
		test.asm_storePage.enterAdditionalContactInformation();
		test.asm_storePage.enterEmail_Phone("Email",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.appAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToPaymentInfoPage();
	}

	@Test
	public void Step33_TC33_VerifyASMErrorNotPresentForInValidEmailIdInAdditionalContactInformation() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForBillingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidBillingInformation();
		test.asm_storePage.checkAddNewShippingPhoneNumber();
		test.asm_storePage.enterAdditionalContactInformation();
		test.asm_storePage.enterEmail_Phone("Email",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.appAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToPaymentInfoPage();
	}

	@Test
	public void Step34_TC34_VerifyASMErrorNotPresentForValidCardHolderNameInPaymentInformation() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForBillingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidBillingInformation();
		test.asm_storePage.clickOnContinue();
		test.asm_storePage.verifyPaymentInformationPageIsPresent();
		test.asm_storePage.enterPaymentInformation();
		test.asm_storePage.enterPaymentInfo("CardHolderName",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.verifyApplicationAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToSecureCheckoutPage();
	}

	@Test
	public void Step35_TC35_VerifyASMErrorNotPresentForValidCreditCardNumberInPaymentInformation() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForBillingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidBillingInformation();
		test.asm_storePage.clickOnContinue();
		
		test.asm_storePage.enterPaymentInformation();
		test.asm_storePage.enterPaymentInfo("CCNumber",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.verifyApplicationAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToSecureCheckoutPage();
	}

	@Test
	public void Step36_TC36_VerifyASMErrorNotPresentForInValidCreditCardNumberInPaymentInformation() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForBillingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidBillingInformation();
		test.asm_storePage.clickOnContinue();
		
		test.asm_storePage.enterPaymentInformation();
		test.asm_storePage.enterPaymentInfo("CCNumber",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.verifyApplicationAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToSecureCheckoutPage();
	}

	@Test
	public void Step37_TC37_VerifyASMErrorNotPresentForValidCreditCardVerificationNumberInPaymentInformation() {
		String tcId = test.ContactInfoPage.getTestCaseID(Thread.currentThread()
				.getStackTrace()[1].getMethodName());
		test.asm_storePage.preRequisiteForBillingAddress(YamlReader
				.getYamlValue("ASM_StoreSmokeChecklist_Data.userName"),
				YamlReader
						.getYamlValue("ASM_StoreSmokeChecklist_Data.password"));
		test.asm_storePage.FillValidBillingInformation();
		test.asm_storePage.clickOnContinue();
		
		test.asm_storePage.enterPaymentInformation();
		test.asm_storePage.enterPaymentInfo("CVV",
				DataProvider.getColumnData(tcId, headerName));
		test.asm_storePage
				.verifyApplicationAcceptsDataAndVerifyAsmErrorNotPresentAndNavigatesToSecureCheckoutPage();
	}

	@AfterMethod
	public void take_screenshot_on_failure(ITestResult result) {
		test.takescreenshot.takeScreenShotOnException(result);
		test.closeBrowserSession();
	}

	@BeforeMethod
	public void OpenBrowserWindow() {
		test = new TestSessionInitiator(this.getClass().getSimpleName());
		app_url_Store = getYamlValue("app_url_Store");
		test.launchApplication(app_url_Store);
	}

}
