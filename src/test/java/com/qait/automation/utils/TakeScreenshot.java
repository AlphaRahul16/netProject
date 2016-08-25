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

	public void checkFilePresentInScreenshotFolder() {
		File folder = new File("./test-output/screenshots/" + this.testname);
		String[] fileNames = folder.list();
		for (int i = 0; i < fileNames.length; i++) {
			File file = new File(fileNames[i]);
			String[] folderDateArray = fileNames[i].split("_", 4);
			System.out.println("-----" + folderDateArray[0]);
			System.out.println("-----" + folderDateArray[1]);
			System.out.println("-----" + folderDateArray[2]);
			String[] currentDateArray = DateUtil.getCurrentdateInStringWithGivenFormate("yyyy_MM_dd").split("_", 3);
			System.out.println("----current date" + currentDateArray[2]);
			if (folderDateArray[1].equals(currentDateArray[1])) {
				System.out.println("same month");
				deleteFolder(Integer.parseInt(folderDateArray[2]), Integer.parseInt(currentDateArray[2]), file);
			}
		}
	}

	public void deleteFolder(int folderDate, int currentDate, File fileName) {
		if (currentDate - 5 == folderDate) {
			System.out.println("----file name:"+fileName);
			File newFile=new File("./test-output/screenshots/" + this.testname+"/"+fileName);
			System.out.println(newFile.exists());
			newFile.delete();
			System.out.println("---file deleted");
		}
	}

}
