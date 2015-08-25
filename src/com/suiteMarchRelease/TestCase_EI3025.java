package com.suiteMarchRelease;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.AssessmentUtil;
import Util.QuestionUtil;
import Util.ReleaseUtil;
import Util.TestUtil;

public class TestCase_EI3025 extends TestBase
{
	public static String EI3025_Assessment="";
	public static String test_folder="";
	// Runmode of test case in a suite

	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("MarchReleaseSuite","TestCase_EI3025");
	}	 


	@Test
	public static void verifyEI3025Test() throws Exception
	{
		System.out.println("Testing EI-3025 Sub:Assessment Export/Print: Not able to Export/Print scantron based assessment");
		String FITB_Q=ReleaseUtil.randomStringGen("EI3025FITB_Q");
		String Essay_Q=ReleaseUtil.randomStringGen("EI3025Essay_Q");
		String MCQ_Q=ReleaseUtil.randomStringGen("EI3025MCQ_Q");
		String TF_Q=ReleaseUtil.randomStringGen("EI3025TF_Q");
		EI3025_Assessment=ReleaseUtil.randomStringGen("EI3025_assess");
		test_folder=ReleaseUtil.randomStringGen("Test_Folder");
		QuestionUtil.CreateQSFolder(test_folder);
		ReleaseUtil.VerifyEI3025(test_folder,FITB_Q,Essay_Q,MCQ_Q,TF_Q,EI3025_Assessment);
	}
	@AfterMethod 
	public void DefaultResult(ITestResult result) throws Exception {
		AssessmentUtil.deleteAssessment(EI3025_Assessment);//cleanup
		QuestionUtil.DeleteQSInFolder(test_folder);//cleapup
		QuestionUtil.DeleteQSFolder(test_folder);//cleanup
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