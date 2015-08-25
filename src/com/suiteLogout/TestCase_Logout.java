package com.suiteLogout;


import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.TestUtil;

public class TestCase_Logout extends TestBase
{

	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("LogoutSuite","TestCase_Logout");
	}	 


	@Test
	public void LogOutTest() throws Exception
	{
		TestUtil.doLogout();
	}

	@AfterMethod 
	public void DefaultResult(ITestResult result) throws InterruptedException {
		System.out.println("Method name: " + result.getMethod().getMethodName());
		System.out.println("Success %:" + result.isSuccess());
		driver.quit();
		if(result.isSuccess()){
			System.out.println("Testcase is passed, Not required to call Exit!");  
		}
	}

}