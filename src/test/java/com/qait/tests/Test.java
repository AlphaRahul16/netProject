package com.qait.tests;

import org.testng.Reporter;

 
public class Test{  
   
	/*protected Test(WebDriver driver, String pageName) {
>>>>>>> 0526b45ed3b886c391ab3ab918a074ba1207744a
		super(driver, pageName);
		// TODO Auto-generated constructor stub
	}*/
	
	
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
	
	public void method(){
		if(true&true){
		System.out.println("a");	
		}
		
		if(true&&true){
			System.out.println("b");	
			}
	}
		@org.testng.annotations.Test
		public void test1(){
			Reporter.setEscapeHtml(true);
			Reporter.log("<b>ayush</b>");
		}		
	}
	

	
	
	  
