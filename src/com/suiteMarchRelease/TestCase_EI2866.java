package com.suiteMarchRelease;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.TestUtil;

public class TestCase_EI2866 extends TestBase
{

	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("MarchReleaseSuite","TestCase_EI2866");
	}	 


	@Test
	public void verifyEI2866Test() throws Exception
	{   
		System.out.println("Testing 2866 : sub: Clicking on My Preference should not throw error");
		TestUtil.doMyPreference();
		if (elementExists("//*[@id='savePreferencesbtn']").isDisplayed())
			System.out.println("My Preference Page is loaded properly!");
		else 
			Assert.fail("My Preference page not loaded");
	}

	@AfterMethod 
	public void DefaultResult(ITestResult result) throws InterruptedException {
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