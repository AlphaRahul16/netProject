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
	        //System.out.println("Abc"+org.apache.commons.lang3.StringUtils.containsIgnoreCase("Abc","Abc"));
	        
	    	String a = "aabcgdbdgeads";
			int i,j,count;
			char temp;
			String b[] = new String[15];
			System.out.println(a.length());
			for(i=0;i<a.length()-1;i++)
			{
				 count = 1;
				System.out.println("before"+i);
				temp = a.charAt(i++);
				System.out.println("after"+i);
				if(a.charAt(i)==temp)
				{
					count++;
					
				}
				
				System.out.println(temp+" count is "+count);
			}
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

//	public static void main(String[] args) {
////		System.out.print("\007");
////	    System.out.flush();
//	}
//}
//	public static void main(String args[]) {
//		
//		 String server = "data.qainfotech.com";
//	        int port = 21;
//	        String user = "acs@qait";
//	        String pass = "@cs@123";

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
		
	

		// String type="home";
		// type=Character.toUpperCase(type.charAt(0))+type.substring(1);
		// System.out.println(Character.toUpperCase(type.charAt(0))+type.substring(1));
		//
		// System.out.println(DateUtil.convertStringToParticularDateFormat("2/2022","MM/yyyy"))
		// ;
		//
		// String currentDate = DateUtil.getCurrentTime("hh:mm a", "EST5EDT");
		//
		// Date dateInDate = DateUtil.convertStringToDate(currentDate,
		// "hh:mm a");
		// System.out.println(DateUtils.addMinutes(dateInDate,
		// Integer.parseInt("8")));
		//
		// String srcCode = "";
		//
		// if(srcCode.equals("")){
		// System.out.println("1");
		// }

		/*
		 * ); ======= String currentDate = DateUtil.getCurrentTime("hh:mm a",
		 * "IST"); System.out.println(currentDate); Date dateInDate =
		 * DateUtil.convertStringToDate(currentDate, "hh:mm a"); Date
		 * dateAfterMinutesAdded = DateUtils.addMinutes(dateInDate,
		 * Integer.parseInt("5")); SimpleDateFormat sdf = new
		 * SimpleDateFormat("h:mm:ss a"); String dateWithTimeSlabInString =
		 * sdf.format(dateAfterMinutesAdded);
		 * System.out.println(dateWithTimeSlabInString); String runTaskDateTime
		 * = DateUtil .getCurrentdateInStringWithGivenFormateForTimeZone(
		 * "MM/dd/YYYY", "IST") + " " + dateWithTimeSlabInString;
		 * 
		 * /* >>>>>>> 68943518e4a9ecf9c1831a26d6e64447cfdc035a public void
		 * demoTest(){ WebDriver driver=new FirefoxDriver();
		 * 
		 * driver.get("https://www.google.co.in/");
		 * 
		 * String readyState = String.valueOf(((JavascriptExecutor)
		 * driver).executeScript("return document.readyState"));
		 * System.out.println("----"+readyState);
		 * 
		 * driver.findElement(By.xpath("//input[@title='Search']")).sendKeys(
		 * "google");
		 * driver.findElement(By.xpath("//button[@type='submit']")).click();
		 * 
		 * // System.out.println("-----"+executeJavascriptReturnValue(
		 * "document.readyState.match('complete')")); //
		 * System.out.println("------"+((JavascriptExecutor)
		 * driver).executeScript("document.readyState"));
		 * 
		 * while(!((JavascriptExecutor)
		 * driver).executeScript("document.readyState").equals("complete")){
		 * System.out.println("waiting for page to load"); } }
		 */
		// String[] ar=DateUtil.getNextDate("month", 1);
		// System.out.println(ar[1]+"/"+ar[2]+"/"+ar[0]);
		// ar=DateUtil.getNextDate("month", 2);
		// System.out.println(ar[0]+"/"+ar[1]+"/"+ar[2]);
		// ar=DateUtil.getNextDate("month", 3);
		// System.out.println(ar[0]+"/"+ar[1]+"/"+ar[2]);
		// DecimalFormat df = new DecimalFormat("#.00");
		// Double payments=new Double(300/3);
		// String am="300.00";
		// Float amount=new Float(am);
		// System.out.println(df.format(amount));

		// int am=Integer.parseInt("300");
		// System.out.println(df.);


		// WebDriver driver=new FirefoxDriver();
		// public void method(){
		// if(true&true){
		// System.out.println("a");
		// }
		//
		// if(true&&true){
		// System.out.println("b");
		// }
		// }
		// @org.testng.annotations.Test
		// public void test1(){
		// Reporter.setEscapeHtml(true);
		// Reporter.log("<b>ayush</b>");
		//

//		String currentDate = DateUtil.getCurrentTime("hh:mm a", "IST");
//		System.out.println(currentDate);
//		Date dateInDate = DateUtil.convertStringToDate(currentDate, "hh:mm a");
//		Date dateAfterMinutesAdded = DateUtils.addMinutes(dateInDate,
//				Integer.parseInt("5"));
//		SimpleDateFormat sdf = new SimpleDateFormat("h:mm:ss a");
//		String dateWithTimeSlabInString = sdf.format(dateAfterMinutesAdded);
//		System.out.println(dateWithTimeSlabInString);
//		String runTaskDateTime = DateUtil
//				.getCurrentdateInStringWithGivenFormateForTimeZone(
//						"MM/dd/YYYY", "IST")
//				+ " " + dateWithTimeSlabInString;
//		
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
				

	

