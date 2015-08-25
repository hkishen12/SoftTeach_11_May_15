package com.suiteMarchRelease;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.ReleaseUtil;
import Util.TestUtil;

public class TestCase_EI2632 extends TestBase
{
	public static String Email="";
	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("MarchReleaseSuite","TestCase_EI2632");
	}	 


	@Test
	public void verifyEI2632Test() throws Exception
	{
		System.out.println("Testing EI-2632 about Spanish character in email field");
		Email=ReleaseUtil.randomStringGen("QA7")+"@tester.com"; //generating dynamic email id
		ReleaseUtil.VerifyEI2632(Data.getProperty("FirstName"),Data.getProperty("LastName"),Email,Data.getProperty("Password") );
	}
	@AfterMethod 
	public void DefaultResult(ITestResult result) throws InterruptedException {
		ReleaseUtil.DeleteUser(Email);//cleanup
		System.out.println("Method name: " + result.getMethod().getMethodName());
		System.out.println("Success %:" + result.isSuccess());
		if(!result.isSuccess()){
			TestUtil.takeScreenShot(result.getMethod().getMethodName());
			closeBrowser();
			System.out.println("Closing the Browser");
			openBrowser();	
			System.out.println("Opening the Browser");
			TestUtil.doLogin();
			System.out.println("Performed Login");
		}
		else{
			System.out.println("Testcase is passed, Not required to call Exit!");  

		}
	}

}