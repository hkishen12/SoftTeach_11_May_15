package com.suiteMarchRelease;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.ReleaseUtil;
import Util.TestUtil;

public class TestCase_EI3011 extends TestBase
{
	public static String Email="";
	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("MarchReleaseSuite","TestCase_EI3011");
	}	 


	@Test
	public void verifyEI3011Test() throws Exception
	{   
		Email=ReleaseUtil.randomStringGen("Test")+"@Examsoft.com";
		System.out.println("Testing EI3011 :User Delete : Unknown error while deleting user");
		ReleaseUtil.CreateUser(Data.getProperty("FirstName"),Data.getProperty("LastName"),Email,Data.getProperty("Password"));
		waitInSeconds(2);
	}
	@AfterMethod 
	public void DefaultResult(ITestResult result) throws InterruptedException {
		ReleaseUtil.DeleteUser(Email);
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