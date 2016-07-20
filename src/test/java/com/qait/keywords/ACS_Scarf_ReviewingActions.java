package com.qait.keywords;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.util.List;

import org.openqa.selenium.WebDriver;

import com.qait.automation.getpageobjects.ASCSocietyGenericPage;

public class ACS_Scarf_ReviewingActions extends ASCSocietyGenericPage {
	List<String> answers;
	WebDriver driver;
	static String pagename = "Scarf_Reviewing";
	int timeOut = Integer.parseInt(getProperty("Config.properties", "timeout"));;

	public ACS_Scarf_ReviewingActions(WebDriver driver) {
		super(driver, pagename);
		this.driver = driver;
	}

}
