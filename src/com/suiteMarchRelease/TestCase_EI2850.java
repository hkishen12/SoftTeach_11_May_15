package com.suiteMarchRelease;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.QuestionUtil;
import Util.ReleaseUtil;
import Util.TestUtil;

public class TestCase_EI2850 extends TestBase
{
	public static String EssayQsEI2850="";
	public static String testfolder="";
	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("MarchReleaseSuite","TestCase_EI2850");
	}	 


	@Test
	public void verifyEI2850Test() throws Exception
	{
		System.out.println("Testing EI-2850 sub:Search Question: Not able to find Draft questions with basic search by ID or Name");
		String EssayQsEI2850 = randomStringGen("EI2850_");//generate random essay question title
		String testfolder = randomStringGen("testfolEI2850_");
		ReleaseUtil.Verify2850(EssayQsEI2850,testfolder);
	}

	@AfterMethod 
	public void DefaultResult(ITestResult result) throws InterruptedException {
		QuestionUtil.DeleteQSInFolder(testfolder);//cleanup
		QuestionUtil.DeleteQSFolder(testfolder);//cleanup
		System.out.println("Method name: " + result.getMethod().getMethodName());
		System.out.println("Success %:" + result.isSuccess());
		QuestionUtil.DeleteEssayQuestions(EssayQsEI2850);//cleanup
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