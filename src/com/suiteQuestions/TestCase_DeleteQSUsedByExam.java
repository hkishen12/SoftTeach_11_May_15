package com.suiteQuestions;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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

public class TestCase_DeleteQSUsedByExam extends TestBase
{
	public static String testfolder = "";
	public static String QsName ="";
	public static String assessmentTitle ="";

	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("QuestionsSuite","TestCase_DeleteQSUsedByExam");
	}	 


	@Test
	public void verifyDeleteQSUsedByExam() throws Exception
	{
		testfolder = ReleaseUtil.randomStringGen("testfol_");
		QsName = ReleaseUtil.randomStringGen("EI3121-");
		assessmentTitle=ReleaseUtil.randomStringGen("DeleteAssess_");
		QuestionUtil.CreateQSFolder(testfolder);
		QuestionUtil.create_ApproveQuestion(testfolder, "TF", QsName);
		//Create and Save Assessment in Draft mode
		AssessmentUtil.navigateToCreateNewQuestionAssessmentPage();
		AssessmentUtil.doNewAssessmentWithTitle(assessmentTitle);
		QuestionUtil.addQuestionToAssessment(QsName);
		AssessmentUtil.doSaveCreatedAssessment();
		//try to delete question
		QuestionUtil.ClickOnQuestionsTab();
		driver.findElement(By.xpath("//*[@id='questionListingFolder']//span[.='"+testfolder+"']")).click(); 
		WaitForProgressBar();	
		Thread.sleep(2000);
		elementExists("//*[@id='questionsTable']/tbody/tr[1]//img[contains(@src,'delete.png')]").click();
		waitInSeconds(1);
		getObjectxpathNew("QS_Delete_Handler").click();
		WaitForProgressBar();
		if(elementExists("//*[@id='deleteQuestion']").getText().contains("This question was already used in an assessment and cannot be deleted.")){
			Assert.assertTrue(true, "User is not able to delete Questions which is used by any exam.");
			getObjectxpathNew("QS_Delete_Handler").sendKeys(Keys.ESCAPE);
		}else{
			Assert.fail("User is able to delete Questions which is used by any exam.");
			getObjectxpathNew("QS_Delete_Handler").sendKeys(Keys.ESCAPE);
		}
	}
	@AfterMethod 
	public void DefaultResult(ITestResult result) throws InterruptedException {
		AssessmentUtil.deleteAssessment(assessmentTitle);//cleanup
		QuestionUtil.DeleteQSInFolder(testfolder);//cleanup
		QuestionUtil.DeleteQSFolder(testfolder);//cleanup
		System.out.println("Method name: " + result.getMethod().getMethodName());
		System.out.println("Success %:" + result.isSuccess());
		if(!result.isSuccess()){
			TestUtil.takeScreenShot(result.getMethod().getMethodName()+"_"+CONFIG.getProperty("QS_CreateQSFolderName"));
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