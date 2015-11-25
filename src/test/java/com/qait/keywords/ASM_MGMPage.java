package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.qait.automation.getpageobjects.GetPage;

public class ASM_MGMPage extends GetPage {
	WebDriver driver;
	boolean flag;
	int timeOut, hiddenFieldTimeOut, pageSize;
	static String pagename = "ASM_MGMPage";
	static int nextPage;

	public ASM_MGMPage(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

	public void clickOnLoginLink() {
		isElementDisplayed("link_login");
		element("link_login").click();
		logMessage("STEP : click on login link in link_login\n");
		isElementDisplayed("form_login");
		logMessage("ASSERT PASSED : Login form appear\n");
	}

	public void loginInToApplication(String userName, String password) {
		clickOnLoginLink();
		enterUserName(userName);
		enterPassword(password);
		clickOnLoginButton();
	}

	public void enterUserName(String userName) {
		isElementDisplayed("inp_userName");
		element("inp_userName").clear();
		element("inp_userName").sendKeys(userName);
		logMessage("STEP : enter " + userName + " in inp_userName\n");
	}

	public void enterPassword(String password) {
		isElementDisplayed("inp_password");
		element("inp_password").clear();
		element("inp_password").sendKeys(password);
		logMessage("STEP : enter " + password + " in inp_password\n");
	}

	public void clickOnLoginButton() {
		isElementDisplayed("btn_login");
		element("btn_login").click();
		logMessage("STEP : click on login button in btn_login\n");

	}

	public void verifyWelcomePage() {
		hardWaitForIEBrowser(7);
		isElementDisplayed("label_welcomePage");
		logMessage("ASSERT PASSED : Welcome page is appeared on login in to application in label_welcomePage\n");
	}

	public void clickOnInviteButton() {
		isElementDisplayed("btn_invite");
		element("btn_invite").click();
		logMessage("STEP : Invite someone to join ACS button is clicked in btn_invite\n");
		isElementDisplayed("modal_invite");
		logMessage("ASSERT PASSED : Invite someone to join ACS form appeared in modal_invite\n ");
	}

	public String submitMemberDetailsToInvite(String fName, String lName,
			String email) {
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
			String uniqueEmail = email[0] + System.currentTimeMillis() + "@"
					+ email[1];
			element("inp_inviteMemberDetails", detailName)
					.sendKeys(uniqueEmail);
			logMessage("STEP : enter " + uniqueEmail + " in " + detailName
					+ " in inp_inviteMemberDetails\n");
			return uniqueEmail;
		} else {
			isElementDisplayed("inp_inviteMemberDetails", detailName);
			element("inp_inviteMemberDetails", detailName).clear();
			element("inp_inviteMemberDetails", detailName)
					.sendKeys(detailValue);
			logMessage("STEP : enter " + detailValue + " in " + detailName
					+ " in inp_inviteMemberDetails\n");
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
		logMessage("ASSERT PASSED : error message " + errorMessage
				+ " appeared \n");
	}

	public void verifyInviteMemberSuccessfully(String email) {
		isElementDisplayed("list_email");
		hardWaitForIEBrowser(4);
		for (WebElement element : elements("list_email")) {
			if (element.getText().equalsIgnoreCase(email)) {
				flag = true;
			}
		}
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
		if (!flag) {
			Assert.fail("ASSERT FAILED : Member not invited successfully \n");
		} else {
			logMessage("ASSERT PASSED : Member invited successfully in txt_email\n");
		}

	}

	public boolean isPagingPresent() {
		timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
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
		hiddenFieldTimeOut = Integer.parseInt(getProperty("Config.properties",
				"hiddenFieldTimeOut"));
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

}
