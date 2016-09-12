/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qait.automation.utils;

import static com.qait.automation.utils.ConfigPropertyReader.getProperty;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;

/**
 *
 * @author prashantshukla
 */
public class TakeScreenshot {

	WebDriver driver;
	String testname;
	String screenshotPath = "/target/Screenshots";

	public TakeScreenshot(String testname, WebDriver driver) {
		this.driver = driver;
		this.testname = testname;
	}

	public void takeScreenshot() {
		screenshotPath = (getProperty("screenshot-path") != null) ? getProperty("screenshot-path") : screenshotPath;		
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_a");
		Date date = new Date();
		String date_time = dateFormat.format(date);
		File file = new File(System.getProperty("user.dir") + File.separator + screenshotPath + File.separator
				+ this.testname + File.separator + date_time);
				
		deleteAllScreenshotsBeforeFiveDays();
		boolean exists = file.exists();
		if (!exists) {
			new File(System.getProperty("user.dir") + File.separator + screenshotPath + File.separator + this.testname
					+ File.separator + date_time).mkdir();
		}

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			String saveImgFile = System.getProperty("user.dir") + File.separator + screenshotPath + File.separator
					+ this.testname + File.separator + date_time + File.separator + "screenshot.png";
			Reporter.log("Save Image File Path : " + saveImgFile, true);
			FileUtils.copyFile(scrFile, new File(saveImgFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void takeScreenShotOnException(ITestResult result) {
		String takeScreenShot = getProperty("take-screenshot");
		if (result.getStatus() == ITestResult.FAILURE) {
			Reporter.log("FAILURE occured at " + DateUtil.converttimestamp(System.currentTimeMillis()), true);
			if (takeScreenShot.equalsIgnoreCase("true") || takeScreenShot.equalsIgnoreCase("yes")) {
				try {
					if (driver != null) {
						takeScreenshot();
					}
				} catch (Exception ex) {
					Reporter.log("Driver is null while taking screen shot", true);
				}
			}
		}
	}
	
	public void deleteAllScreenshotsBeforeFiveDays(){
		File folder = new File("./test-output/screenshots/" + this.testname);
		String[] fileNames = folder.list();
		if(folder.exists()){
		for (int i = 0; i < fileNames.length; i++) {
			File newFile=new File("./test-output/screenshots/" + this.testname+"/"+fileNames[i]);
			String[] folderDateArray = fileNames[i].split("_", 4);
			String folderDateString=folderDateArray[0]+"_"+folderDateArray[1]+"_"+folderDateArray[2];
			Date folderDate=DateUtil.convertStringToDate(folderDateString, "yyyy_MM_dd");
			String[] calDate=DateUtil.getPreviousDate("day",5);
			String previousDateString=calDate[0]+"_"+calDate[1]+"_"+calDate[2];
			Date previousDate=DateUtil.convertStringToDate(previousDateString, "yyyy_MM_dd");
			deleteAllPrevoiusDateScreenshotsFolder(folderDate,previousDate,newFile);
		}
	}
	}
	
	public void deleteAllPrevoiusDateScreenshotsFolder(Date folderDate,Date previousDate,File fileName){
		if(folderDate.before(previousDate)){
			try {
				System.out.println("Folder date:"+folderDate+"\n");
				System.out.println("Prevoius date :"+previousDate+"\n");
				FileUtils.deleteDirectory(fileName);
				System.out.println("Screenshot Folder "+fileName.getName()+" is deleted\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
