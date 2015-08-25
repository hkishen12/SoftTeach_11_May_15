package com.suiteLogin;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.TestUtil;

public class TestCase_Login extends TestBase
{
	
// Runmode of test case in a suite
@BeforeTest
public void checkTestSkip() throws Exception
{	 
  	TestUtil.checkTestSkip("LoginSuite","TestCase_Login");
}	 

@Test  
public void TestCaseLogin() throws Exception 
	{
	 openBrowser();	
	 TestUtil.doLogin();
	 }

@AfterMethod 
public void DefaultResult(ITestResult result) throws InterruptedException {
  System.out.println("Method name: " + result.getMethod().getMethodName());
  System.out.println("Success %:" + result.isSuccess());
  System.out.println("Testcase name: "+result.getInstanceName().substring(result.getInstanceName().lastIndexOf(".")+1, result.getInstanceName().length()));
  if(!result.isSuccess()){
	  setSkipFlag=true;
	  System.out.println("Login failed!..SKIPPING ALL REMAINING TESTCASES");
	  TestUtil.takeScreenShot(result.getMethod().getMethodName());
	  closeBrowser();
	  System.out.println("Closing the Browser");
  }
  else{
	  System.out.println("Testcase is passed, Not required to call Exit!");  
	  
  }
}



}
