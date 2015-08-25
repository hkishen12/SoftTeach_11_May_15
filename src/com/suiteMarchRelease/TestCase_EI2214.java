package com.suiteMarchRelease;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.AssessmentUtil;
import Util.ReleaseUtil;
import Util.TestUtil;

public class TestCase_EI2214 extends TestBase
{
	public static String testfolder="";
	public static String assessment="";
	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("MarchReleaseSuite","TestCase_EI2214");
	}	 


	@Test
	public void EI2214() throws Exception
	{
		System.out.println("Testing EI-2214 Duplicate Assessment : Not able to duplicate assessment");
		assessment=randomStringGen("assess2214_");
		ReleaseUtil.verify2214(assessment);
	}

	@AfterMethod 
	public void DefaultResult(ITestResult result) throws InterruptedException {
		AssessmentUtil.deleteAssessment(assessment);//cleanup
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