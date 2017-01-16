package com.qait.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class Test {
	public static void main(String args[]) {
		
		 String server = "data.qainfotech.com";
	        int port = 21;
	        String user = "acs@qait";
	        String pass = "@cs@123";
	        System.out.println("Abc"+org.apache.commons.lang3.StringUtils.containsIgnoreCase("Abc","Abc"));
	}}
//	 
//	        FTPClient ftpClient = new FTPClient();
//	        try {
//	 
//	            ftpClient.connect(server, port);
//	            ftpClient.login(user, pass);
//	            ftpClient.enterLocalPassiveMode();
//	 
//	            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//	 
//	            // APPROACH #1: uploads first file using an InputStream
//	            File firstLocalFile = new File("D:\\netforumqatests\\target\\surefire-reports\\emailable-report.html");
//	 
//	            String firstRemoteFile = "Projects.zip";
//	            InputStream inputStream = new FileInputStream(firstLocalFile);
//	 
//	            System.out.println("Start uploading first file");
//	            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
//	            inputStream.close();
//	            if (done) {
//	                System.out.println("The first file is uploaded successfully.");
//	            }
	 
//	            File secondLocalFile = new File("D:\\D\\Documents\\ACS_OMA\\ACS_Society\\Society\\target\\surefire-reports\\emailable-report.html");
//	            String secondRemoteFile = "Projects.zip";
//	            inputStream = new FileInputStream(secondLocalFile);
//	 
//	            System.out.println("Start uploading second file");
//	            OutputStream outputStream = ftpClient.storeFileStream(secondRemoteFile);
//	            byte[] bytesIn = new byte[4096];
//	            int read = 0;
//	 
//	            while ((read = inputStream.read(bytesIn)) != -1) {
//	                outputStream.write(bytesIn, 0, read);
//	            }
//	            inputStream.close();
//	            outputStream.close();
//	 
//	            boolean completed = ftpClient.completePendingCommand();
//	            if (completed) {
//	                System.out.println("The second file is uploaded successfully.");
//	            }
//	 
//	        } catch (IOException ex) {
//	            System.out.println("Error: " + ex.getMessage());
//	            ex.printStackTrace();
//	        } finally {
//	            try {
//	                if (ftpClient.isConnected()) {
//	                    //ftpClient.logout();
//	                    ftpClient.disconnect();
//	                }
//	            } catch (IOException ex) {
//	                ex.printStackTrace();
//	            }
//	        }
//	}}
//		
		
//		try {
//			System.out.println("1");
//			throw new NullPointerException();
//		} 
//		catch (Exception e) {
//			System.out.println("2");
//		}
//		finally {
//			System.out.println("3");
//		}
//	}
//}
		

/*
>>>>>>> 68943518e4a9ecf9c1831a26d6e64447cfdc035a
	public void demoTest(){
	WebDriver driver=new FirefoxDriver();
	
	driver.get("https://www.google.co.in/");
	
	String readyState = String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"));
	System.out.println("----"+readyState);
	
	driver.findElement(By.xpath("//input[@title='Search']")).sendKeys("google");
	driver.findElement(By.xpath("//button[@type='submit']")).click();

//    System.out.println("-----"+executeJavascriptReturnValue("document.readyState.match('complete')"));
//	System.out.println("------"+((JavascriptExecutor) driver).executeScript("document.readyState"));

	while(!((JavascriptExecutor) driver).executeScript("document.readyState").equals("complete")){
		System.out.println("waiting for page to load");
	} 
	}*/
//	String[] ar=DateUtil.getNextDate("month", 1);
//	System.out.println(ar[1]+"/"+ar[2]+"/"+ar[0]);
//	ar=DateUtil.getNextDate("month", 2);
//	System.out.println(ar[0]+"/"+ar[1]+"/"+ar[2]);
//	ar=DateUtil.getNextDate("month", 3);
//	System.out.println(ar[0]+"/"+ar[1]+"/"+ar[2]);
//	 DecimalFormat df = new DecimalFormat("#.00");
//    Double payments=new Double(300/3);
//    String am="300.00";
//    Float amount=new Float(am);
//	System.out.println(df.format(amount));
	
//	int am=Integer.parseInt("300");
//	System.out.println(df.);
   
//	WebDriver driver=new FirefoxDriver();
//	public void method(){
//		if(true&true){
//		System.out.println("a");	
//		}
//		
//		if(true&&true){
//			System.out.println("b");	
//			}
//	}
//		@org.testng.annotations.Test
//		public void test1(){
//			Reporter.setEscapeHtml(true);
//			Reporter.log("<b>ayush</b>");
//			
				

	

