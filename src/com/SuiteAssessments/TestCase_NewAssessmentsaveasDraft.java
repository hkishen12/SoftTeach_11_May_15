package com.SuiteAssessments;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Util.AssessmentUtil;
import Util.TestUtil;

public class TestCase_NewAssessmentsaveasDraft extends TestSuiteBase
{

	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("AssessmentsSuite","TestCase_NewAssessmentsaveasDraft");
	}	 


	@Test
	public void NewAssessmentsaveasDraft() throws Exception
	{	
		String assessTitle=randomStringGen("saveAsAssess_");
		AssessmentUtil.navigateToCreateNewQuestionAssessmentPage();
		AssessmentUtil.doNewAssessmentWithTitle(assessTitle);
		AssessmentUtil.doSaveCreatedAssessment();
		AssessmentUtil.doSearchAssessment(assessTitle);
		waitInSeconds(5);
		if(elementExists("//*[@id='examsTable']/tbody/tr/td[contains(text(),'Draft')]")!=null){
			APP_LOGS.debug("New Assessment is saved as draft");
			AssessmentUtil.deleteAssessment(assessTitle);
		}else{
			AssessmentUtil.deleteAssessment(assessTitle);
			Assert.fail();	
		}
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