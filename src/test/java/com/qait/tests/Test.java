package com.qait.tests;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;

 
public class Test{  
	public static void main(String args[]){
	String type="home";
	type=Character.toUpperCase(type.charAt(0))+type.substring(1);
		System.out.println(Character.toUpperCase(type.charAt(0))+type.substring(1));
		System.out.println(type);
		
/*
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
		}		
	}
	

	
	
	  
