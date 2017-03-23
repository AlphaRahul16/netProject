package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class ASM_MGMPage extends GetPage {
	WebDriver driver;
	boolean flag;
	List<String> uniqueEmails = new ArrayList<>();
	int timeOut, hiddenFieldTimeOut, pageSize;
	static String pagename = "ASM_MGMPage";
	static int nextPage;
	MembershipPageActions_IWEB membershipPageIweb;
	public static HashMap<Integer, String[]> nomineeStatusMap = new HashMap<Integer, String[]>();
	String[][] nomineeStatus = { { "Resend", "PENDING" }, { "Pending", "PENDING" } };

	public ASM_MGMPage(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

	public void clickOnLoginLink() {
		isElementDisplayed("link_login");
		element("link_login").click();
		logMessage("STEP : Click on login link in link_login\n");
		isElementDisplayed("form_login");
		logMessage("ASSERT PASSED : Login form appears\n");
	}

	public void loginInToApplication(String userName, String password) {
		wait.waitForPageToLoadCompletely();
		clickOnLoginLink();
		enterUserName(userName);
		enterPassword(password);
		clickOnLoginButton();
	}

	public void enterUserName(String userName) {
		isElementDisplayed("inp_userName");
		element("inp_userName").clear();
		element("inp_userName").sendKeys(userName);
		logMessage("STEP : Username is entered as '" + userName + "' \n");
	}

	public void enterPassword(String password) {
		isElementDisplayed("inp_password");
		element("inp_password").clear();
		element("inp_password").sendKeys(password);
		logMessage("STEP :  Password is entered as '" + password + "'\n");
	}

	public void clickOnLoginButton() {
		isElementDisplayed("btn_login");
		if (isBrowser("ie")) {
			clickUsingXpathInJavaScriptExecutor(element("btn_login"));
		} else {
			element("btn_login").click();
		}

		logMessage("STEP : Click on login button \n");

	}

	public void verifyWelcomePage() {
		hardWaitForIEBrowser(7);
		isElementDisplayed("label_welcomePage");
		logMessage("ASSERT PASSED : Welcome page is appeared on login in to application in label_welcomePage\n");
	}

	public void clickOnInviteButton() {
		if (isBrowser("ie")) {
			isElementDisplayed("link_applyACSmembership", "Invite someone to join");
			clickUsingXpathInJavaScriptExecutor(element("link_applyACSmembership", "Invite someone to join"));
		} else {
			isElementDisplayed("btn_invite");
			element("btn_invite").click();
		}
		logMessage("STEP : Invite someone to join ACS button is clicked in btn_invite\n");
		isElementDisplayed("modal_invite");
		logMessage("ASSERT PASSED : Invite someone to join ACS form appeared in modal_invite\n ");
	}

	public String submitMemberDetailsToInvite(String fName, String lName, String email) {
		clickOnInviteButton();
		enterDetailsToInvite("FirstName", fName);
		enterDetailsToInvite("LastName", lName);
		String uniqueEmail = enterDetailsToInvite("Email", email);
		clickOnSendInviteButton();
		return uniqueEmail;
	}

	public String enterDetailsToInvite(String detailName, String detailValue) {
		if (detailName.equalsIgnoreCase("email")) {
			isElementDisplayed("inp_inviteMemberDetails", detailName);
			element("inp_inviteMemberDetails", detailName).clear();
			String[] email = detailValue.split("@");
			String uniqueEmail = email[0] + System.currentTimeMillis() + "@" + email[1];
			element("inp_inviteMemberDetails", detailName).sendKeys(uniqueEmail);
			logMessage("STEP : " + detailName + " is entered as " + uniqueEmail + " in inp_inviteMemberDetails\n");
			return uniqueEmail;
		} else {
			isElementDisplayed("inp_inviteMemberDetails", detailName);
			element("inp_inviteMemberDetails", detailName).clear();
			element("inp_inviteMemberDetails", detailName).sendKeys(detailValue);
			logMessage("STEP : " + detailName + " is entered as " + detailValue + " in inp_inviteMemberDetails\n");
			return null;
		}

	}

	public void clickOnSendInviteButton() {
		isElementDisplayed("btn_sendInvite");
		element("btn_sendInvite").click();
		logMessage("STEP : Send invite button is clicked in btn_sendInvite\n");
	}

	public void verifyErrorMessage(String errorMessage) {
		isElementDisplayed("txt_errorMessage");
		verifyElementTextContains("txt_errorMessage", errorMessage);
		logMessage("ASSERT PASSED : Error message " + errorMessage + " appeared \n");
	}

	public void verifyInviteMemberSuccessfully(String email) {
		isElementDisplayed("list_email");
		hardWaitForIEBrowser(4);
		for (WebElement element : elements("list_email")) {
			if (element.getText().equalsIgnoreCase(email)) {
				flag = true;
			}
		}
		try {

			if (isPagingPresent()) {
				while (elements("list_pageNumber").size() > 0) {
					WebElement nextPageElement = elements("list_pageNumber").get(0);
					System.out.println("in while ");
					nextPageElement.click();
					System.out.println("click");
					wait.waitForPageToLoadCompletely();
					hardWaitForIEBrowser(3);
					for (WebElement element : elements("list_email")) {
						if (element.getText().equalsIgnoreCase(email)) {
							System.out.println("verify");
							flag = true;
							break;
						}
					}
					if (!flag) {
						System.out.println("end while");
						if (!isNextPagePresent()) {
							break;
						}
					} else {
						break;
					}
				}
			}
		} catch (Exception e) {

		}
		if (!flag) {
			Assert.fail("ASSERT FAILED : Member not invited successfully \n");
		} else {
			logMessage("ASSERT PASSED : Member invited successfully in txt_email\n");
		}

	}

	public boolean isPagingPresent() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			flag = isElementDisplayed("txt_paging");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (Exception e) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}
		return flag;

	}

	public boolean isNextPagePresent() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties", "hiddenFieldTimeOut"));
		try {
			wait.resetImplicitTimeout(0);
			wait.resetExplicitTimeout(hiddenFieldTimeOut);
			flag = isElementDisplayed("list_pageNumber");
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		} catch (Exception e) {
			wait.resetImplicitTimeout(timeOut);
			wait.resetExplicitTimeout(timeOut);
		}
		return flag;

	}

	public String verifyNomineeStatus(String status, String email) {
		boolean flag = true;

		for (int i = 0; i < 30; i++) {
			wait.hardWait(2);
			isElementDisplayed("link_nomineeStatus", email);
			String nomineeStatus = element("link_nomineeStatus", email).getText().trim();
			if (status.equals(nomineeStatus)) {
				flag = true;
				break;
			} else {
				flag = false;
			}
		}
		Assert.assertTrue(flag, "ASSERT FAILED: nominee status is not same \n");
		logMessage("ASSERT PASSED: Status of the nominee is " + status + "\n");
		return getCurrentURL();
	}
	// public String verifyNomineeStatus(String status, String email) {
	// System.out.println("status" + status);
	// boolean flag = false;
	// int attempts = 0;
	// for (int i = 0; i < 30; i++) {
	// wait.hardWait(2);
	//// while (attempts < 2) {
	//// try {
	// isElementDisplayed("link_nomineeStatus", email);
	// String nomineeStatus = element("link_nomineeStatus",
	// email).getText().trim();
	// if (status.equals(nomineeStatus)) {
	// flag = true;
	// break;
	// } else {
	// flag = false;
	//// }
	//// } catch (StaleElementReferenceException e) {
	//// attempts++;
	//// logMessage("[INFO:] Status on MGM Dashboard is not same after " +
	// attempts + "\n");
	//// }
	// }
	//// if (flag == true) {
	//// break;
	//// }
	// }
	// Assert.assertTrue(flag,"ASSERT FAILED: nominee status is not same \n");
	//
	// logMessage("ASSERT PASSED: Status of the nominee is " + status + "\n");
	// return getCurrentURL();
	// }

	public void clickOnNomineeStatus(String email) {
		isElementDisplayed("link_nomineeStatus", email);
		clickUsingXpathInJavaScriptExecutor(element("link_nomineeStatus", email));
		// element("link_nomineeStatus", email).click();
		logMessage("STEP: Nominee Status link is clicked \n");
	}

	public String verifyStatusAfterClickResend(String MGMpageURL, String email, String status) {
		launchUrl(MGMpageURL);
		clickOnNomineeStatus(email);
		verifyNomineeStatus(status, email);
		return getCurrentURL();
	}

	public void clickOnApplyForACSMembership() {
		hardWaitForIEBrowser(5);
		isElementDisplayed("link_applyACSmembership", "Apply for ACS Membership");
		logMessage("ASSERT PASSED: Apply for ACS Mambership link is present for Non active member \n");
		if (isBrowser("ie") || isBrowser("internet explorer")) {
			clickUsingXpathInJavaScriptExecutor(element("link_applyACSmembership", "Apply for ACS Membership"));
		} else
			element("link_applyACSmembership", "Apply for ACS Membership").click();		
		logMessage("STEP: 'Apply for ACS Mambership' link is clicked \n");
	}

	public void clickOnRenewYourMembershipNow() {
		isElementDisplayed("link_applyACSmembership", "Renew your membership now");
		logMessage("ASSERT PASSED:Renew your membership now link is present \n");
		if (isBrowser("ie")|| isBrowser("internet explorer")) {
			clickUsingXpathInJavaScriptExecutor(element("link_applyACSmembership", "Renew your membership now"));
		} else {
			element("link_applyACSmembership", "Renew your membership now").click();
		}		
		wait.waitForPageToLoadCompletely();
		logMessage("STEP: 'Renew your membership now' link is clicked \n");
	}

	public void submitSameMemberDetailsToInvite(String fName, String lName, String email) {
		clickOnInviteButton();
		enterSameDetailsToInvite("FirstName", fName);
		enterSameDetailsToInvite("LastName", lName);
		enterSameDetailsToInvite("Email", email);
		clickOnSendInviteButton();
	}

	public void enterSameDetailsToInvite(String detailName, String detailValue) {

		isElementDisplayed("inp_inviteMemberDetails", detailName);
		element("inp_inviteMemberDetails", detailName).clear();
		element("inp_inviteMemberDetails", detailName).sendKeys(detailValue);
		logMessage("STEP : " + detailName + " is entered as " + detailValue + " in inp_inviteMemberDetails\n");

	}

	public void inviteButtonIsNotDisplayed() {
		Assert.assertFalse(checkIfElementIsThere("btn_invite"), "ASSERT FAILED: Invite option is given \n");
		logMessage("ASSERT PASSED: Active member with a renewal is not given the option to invite a member\n");
	}

	public void clickOnresendLink(String resendCount, String MGMpageURL, String uniqueEmail, String IWEBurl,
			String fname, String lname, List<String> ewebStatus, List<String> IwebStatus) {
		membershipPageIweb = new MembershipPageActions_IWEB(driver);
		// nomineeStatusMap.put(1, nomineeStatus[0]);
		// nomineeStatusMap.put(2, nomineeStatus[1]);
		for (int i = 0; i < Integer.parseInt(resendCount); i++) {
			MGMpageURL = verifyStatusAfterClickResend(MGMpageURL, uniqueEmail, ewebStatus.get(i));
			membershipPageIweb.verifyNomineeStatusOnIWEB(IWEBurl, IwebStatus.get(i), uniqueEmail, fname, lname);
			logMessage("\n ASSERT PASSED: Status is verified after click on Resend link " + String.valueOf(i + 1)
					+ " time \n");
		}

	}

	public List<String> InviteNewMembersAccordingToInviteeNumber(String inviteeCount, String fname, String lname,
			String email) {
		for (int i = 0; i < Integer.parseInt(inviteeCount); i++) {
			String firstname = fname + System.currentTimeMillis();
			String lastname = lname + System.currentTimeMillis();
			String uniqueEmail = submitMemberDetailsToInvite(firstname, lastname, email);
			uniqueEmails.add(uniqueEmail);
		}
		return uniqueEmails;
	}

	public void verifythatAllInviteesExistOnMGM(List<String> emails, String status) {
		List<String> uniqueEmails = getAllInvitees();
		List<String> notfoundEmails = new ArrayList<>();

		boolean flag = true;int i=0;String nomineeStatus= "";
		for (String email : emails) {
			if (uniqueEmails.contains(email.trim())) {
				try{
					nomineeStatus = element("link_nomineeStatus", email).getText().trim();
					System.out.println("nomineeStatus*******"+nomineeStatus);
				}catch(NoSuchElementException e){
					clickOnPageLinkOnMGM(i);
					nomineeStatus = element("link_nomineeStatus", email).getText().trim();
					System.out.println("nomineeStatus*******"+nomineeStatus);
				}		
				
				Assert.assertEquals(nomineeStatus, status);
				logMessage("ASSERT PASSED: Nominee status is " + status + " for " + email + " member \n ");
			} else {
				notfoundEmails.add(email);
			}
		}
		if (notfoundEmails.size() > 0) {
			for (String emailNotFound : notfoundEmails) {
				logMessage("Email " + emailNotFound + "not found");
			}
			flag = false;
		} else {
			flag = true;
		}
		Assert.assertTrue(flag, "ASSERT FAILED: Invitee is not Present in MGM \n");
		logMessage("ASSERT PASSED: All invitees are present on MGM Dashboard \n");
	}

	private List<String> getAllInvitees() {
		List<String> uniqueEmails = new ArrayList<>();
		
		 int pageLinkSize = elements("link_pages").size();
		int i = 0;
		 do {
		for (int count = 0; count < elements("lbl_nomineeEmail").size(); count++) {
			isElementDisplayed("lbl_nomineeEmail");
			wait.hardWait(2);
			uniqueEmails.add(elements("lbl_nomineeEmail").get(count).getText().trim());
		}
		 i++;
		 if (i > pageLinkSize) {
		 break;
		 }
		 clickOnPageLinkOnMGM(i);
		 } while (i <= pageLinkSize);
		return uniqueEmails;
	}

	private void clickOnPageLinkOnMGM(int i) {
		isElementDisplayed("link_pages", String.valueOf(i));
		element("link_pages", String.valueOf(i)).click();
		logMessage("STEP: Page link " + String.valueOf(i + 1) + "  is clicked \n");
	}

	public List<String> getStatus(String status) {
		// List<String> statuses = new ArrayList<>();
		System.out.println("status:::" + status);
		List<String> statuses = Arrays.asList(status.split(","));
		return statuses;
	}

}
