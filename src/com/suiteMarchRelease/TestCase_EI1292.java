package com.suiteMarchRelease;

import org.openqa.selenium.Keys;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.ReleaseUtil;
import Util.TestUtil;

public class TestCase_EI1292 extends TestBase
{

	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("MarchReleaseSuite","TestCase_EI1292");
	}	 


	@Test
	public void verifyEI1292Test() throws Exception
	{
		System.out.println("Testing EI1292 sub:FSX Exam : Text 'Upload' appears on hover over Upload button.");
		ReleaseUtil.VerifyEI1292();
	}

	@AfterMethod 
	public void DefaultResult(ITestResult result) throws InterruptedException {
		elementExists("//input[@id='uploadButton_fsx']").sendKeys(Keys.ESCAPE);
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