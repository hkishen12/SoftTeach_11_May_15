package com.SuiteAssessments;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Util.AssessmentUtil;
import Util.TestUtil;

public class TestCase_NewAssessmentAddQuestions extends TestSuiteBase
{
	public static String assessTitle="";
	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("AssessmentsSuite","TestCase_NewAssessmentAddQuestions");
	}	 


	@Test
	public void NewAssessmentAddQuestions() throws Exception
	{			
		assessTitle=randomStringGen("assessment_");
		AssessmentUtil.navigateToCreateNewQuestionAssessmentPage();
		AssessmentUtil.doNewAssessment(assessTitle);
		AssessmentUtil.doNewAssessmentAddQuestions();
		AssessmentUtil.doSaveCreatedAssessment();
	}

	@AfterMethod 
	public void DefaultResult(ITestResult result) throws InterruptedException {
		AssessmentUtil.deleteAssessment(assessTitle);//cleanup
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