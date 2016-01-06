package com.qait.keywords;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.GetPage;
import com.qait.automation.utils.ConfigPropertyReader;

public class ASMErrorPage extends GetPage {
	WebDriver driver;
	String pagename = "ASMErrorPage";
	int timeOut = Integer.parseInt(ConfigPropertyReader.getProperty("timeout"));

	public ASMErrorPage(WebDriver driver) {
		super(driver, "ASMErrorPage");
		this.driver = driver;
	}

	public void verifyASMError(String errorMsz) {
		
//		isElementDisplayed("txt_ASMerror", errorMsz);
//		logMessage("ASSERT PASSED : " + errorMsz
//				+ " is verified in txt_ASMerror\n");
		getASMErrorId(errorMsz);
		navigateToBackPage();
	}

	public void verifyASMErrorNotPresent(String errorMsz) {
		try {
			wait.resetImplicitTimeout(2);
			//isElementDisplayed("txt_ASMerror", errorMsz);
			isElementDisplayed("txt_asmNumber");
			logMessage("ASM Error Number is : "+element("txt_asmNumber").getText());
			wait.resetImplicitTimeout(timeOut);
			Assert.fail("ASM Error Present unexpectedly\n");
		} catch (Exception exp) {
			try
			{
				isElementDisplayed("txt_asmMessage");
				logMessage("ASM Error Message is : "+element("txt_asmNumber").getText());
				wait.resetImplicitTimeout(timeOut);
				Assert.fail("ASM Error Present unexpectedly\n");
			
			}
			catch(Exception e2)
			{
				wait.resetImplicitTimeout(timeOut);
				logMessage("ASSERT PASSED : ASM Error page is not present\n");
			}
         
		}
		wait.resetImplicitTimeout(timeOut);
	}

//	public void getASMErrorId(String errorMsz) {
//		isElementDisplayed("txt_ASMerror", errorMsz);
//		String asmErrorText = element("txt_ASMerror", errorMsz).getText();
//		String[] splitAsmError = asmErrorText.split("Your support ID is: ");
//		String supportId = splitAsmError[1];
//		logMessage("ASSERT PASSED : ASM Support ID is : " + supportId + "\n");
//	}
	public void getASMErrorId(String errorMsz) {
		try
		{
		wait.resetImplicitTimeout(2);
		System.out.println("sfsfsfsf");
		isElementDisplayed("txt_asmNumber");
		String asmErrorText = element("txt_asmNumber").getText();
//		String[] splitAsmError = asmErrorText.split("Your support ID is: ");
//		String supportId = splitAsmError[1];
		logMessage("ASSERT PASSED : ASM Error Number is : " + asmErrorText + "\n");
		}
		catch(Exception e2)
		{
		try
		{
			wait.resetImplicitTimeout(2);
			isElementDisplayed("txt_asmMessage");
			logMessage("ASSERT PASSED : ASM Error Message is : " + element("txt_asmMessage").getText() + "\n");
		}
		catch(Exception e)
		{
			wait.resetImplicitTimeout(timeOut);
		}
		}
		wait.resetImplicitTimeout(timeOut);
	}
	
}
