package com.suiteMarchRelease;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.AssessmentUtil;
import Util.QuestionUtil;
import Util.ReleaseUtil;
import Util.TestUtil;

public class TestCase_EI3121 extends TestBase
{
	public static String testfolder = "";
	public static String QsName ="";
	public static String assessmentTitle ="";

	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("MarchReleaseSuite","TestCase_EI3121");
	}	 


	@Test
	public void verifyEI3121Test() throws Exception
	{
		System.out.println("Assessment Listing Page: \"Last Modified\" value appears same for all posts.");
		testfolder = ReleaseUtil.randomStringGen("testfol_");
		QsName = ReleaseUtil.randomStringGen("EI3121-");
		assessmentTitle=ReleaseUtil.randomStringGen("EI3121_assess_");
		QuestionUtil.CreateQSFolder(testfolder);
		QuestionUtil.create_ApproveQuestion(testfolder, "TF", QsName);
		//Create and Save Assessment in Draft mode
		AssessmentUtil.navigateToCreateNewQuestionAssessmentPage();
		AssessmentUtil.doNewAssessmentWithTitle(assessmentTitle);
		QuestionUtil.addQuestionToAssessment(QsName);
		AssessmentUtil.doSaveCreatedAssessment();

		//Search and Post Assessment
		AssessmentUtil.doSearchAssessmentInfolderWithTitle(assessmentTitle);
		AssessmentUtil.doEditAssessment();
		AssessmentUtil.doPostassessment();

		Thread.sleep(75000);// wait for minimum 1 mins before posting another assessment, 
		//as we need to check time difference between 2 assessment
		AssessmentUtil.doPostassessment();

		//Check
		int res = ReleaseUtil.checkPostTimes();
		if (res==0){
			Assert.fail("result is zero, last modified values are same or the script has returned the default value");
		}else{
			System.out.println("Issue is not reproducible");
		}
	}
	
	@AfterMethod 
	public void DefaultResult(ITestResult result) throws Exception {
		AssessmentUtil.doRetireposting(assessmentTitle);//cleanup
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