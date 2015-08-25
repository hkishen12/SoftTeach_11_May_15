package com.suiteMarchRelease;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.QuestionUtil;
import Util.ReleaseUtil;
import Util.TestUtil;

public class TestCase_EI3026 extends TestBase
{
	public static String essayQsName="";
	public static String testfolder="";
	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("MarchReleaseSuite","TestCase_EI3026");
	}	 


	@Test
	public void verifyEI3026Test() throws Exception
	{
		System.out.println("Testing EI-3026 sub:Question New Revision : Not able to create a new Revision of the Question");
		essayQsName=randomStringGen("EssayQs1EI3026_");
		testfolder=randomStringGen("test3026fol");
		ReleaseUtil.Verify3026(testfolder,essayQsName);
	}
	@AfterMethod 
	public void DefaultResult(ITestResult result) throws InterruptedException {
		QuestionUtil.DeleteQSInFolder(testfolder);//cleanup
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