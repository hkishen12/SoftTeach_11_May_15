package com.suiteMarchRelease;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.AssessmentUtil;
import Util.ReleaseUtil;
import Util.TestUtil;

public class TestCase_EI3135 extends TestBase
{
	public static String assesstitle="";
	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("MarchReleaseSuite","TestCase_EI3135");
	}	 


	@Test
	public void verifyEI3135Test() throws Exception
	{
		System.out.println("Testing EI-3135 sub:Post assessment: Getting unexpected error while posting assessment");
		assesstitle=randomStringGen("assess_");
		ReleaseUtil.verifyEI3135(assesstitle);
	}

	@AfterMethod 
	public void DefaultResult(ITestResult result) throws Exception {
		AssessmentUtil.doRetireposting(assesstitle);//cleanup
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