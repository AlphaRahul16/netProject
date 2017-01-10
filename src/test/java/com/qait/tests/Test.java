package com.qait.tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;

import com.gargoylesoftware.htmlunit.javascript.host.Screen;
import com.qait.automation.utils.DateUtil;


 
public class Test{  
	public static void main(String args[]){
     
	   // WebDriver driver = new FirefoxDriver();
//	    JavascriptExecutor js = (JavascriptExecutor) driver; 
//	    js.executeScript("var jq = document.createElement('script');"+
//            "jq.src = 'https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js';"+
//"document.getElementsByTagName('head')[0].appendChild(jq);");
//	    
//	    js.executeScript("jQuery.noConflict();console.log('jQuery injected');");
	    
	    //driver.get("http://www.google.com");
	    
	    FTPClient client = new FTPClient();
	    FileInputStream fis = null;

	    try {
	        client.connect("ftp.qainfotech.com");
	        client.login("acs@qait", "@cs@qait");
	        System.out.println("login");

	        //
	        // Create an InputStream of the file to be uploaded
	        //
	        String filename = "./target/surefire-reports/emailable-report.html";
	        fis = new FileInputStream(filename);

	        //
	        // Store file to server
	        //
	        client.storeFile(filename, fis);
	        System.out.println("Stored");
	        client.logout();
	        System.out.println("Success");
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (fis != null) {
	                fis.close();
	            }
	            client.disconnect();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	}	
	}
	

	
	
	  
