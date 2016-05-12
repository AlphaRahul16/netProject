package com.qait.automation.report;


import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

public class MyListner extends TestListenerAdapter{
	 private int m_count = 0;
	 private String testname;
	 
	@Override
    public void onTestFailure(ITestResult tr) {
		
		Reporter.log(tr.getName()+ "--Test method failed\n",true);
    }
	 
    @Override
    public void onTestSkipped(ITestResult tr) {
    	Reporter.log(tr.getName()+ "--Test method skipped\n",true);
    }
	 
    @Override
    public void onTestSuccess(ITestResult tr) {
        Reporter.log(tr.getName()+ "--Test method success\n",true);
    }
    
    @Override
 	public void onFinish(ITestContext testContext)
 	{
    	Reporter.log("Execution Finished Successfully",true);
    	System.out.println(testContext.getAllTestMethods());
    	
 	}
    
   
    }
	 
  


