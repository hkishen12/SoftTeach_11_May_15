package com.suiteMarchRelease;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.QuestionUtil;
import Util.ReleaseUtil;
import Util.TestUtil;

public class TestCase_EI2447 extends TestBase
{
	public static String testfolder="";
	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("MarchReleaseSuite","TestCase_EI2447");
	}	 


	@Test
	public void verifyEI2447Test() throws Exception
	{
		System.out.println("Testing EI-2447 Tab Delimited Import");
		testfolder=randomStringGen("testfol");
		ReleaseUtil.VerifyEI2447TabDelimited(testfolder);
	}

	@AfterMethod 
	public void DefaultResult(ITestResult result) throws InterruptedException {
		QuestionUtil.DeleteQSInFolder(testfolder);//cleaup
		QuestionUtil.DeleteQSFolder(testfolder);//cleanup
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