package com.suiteQuestions;


import java.util.Random;

import org.junit.Assert;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.QuestionUtil;
import Util.TestUtil;

public class TestCase_BulkRemoveItems extends TestBase
{
	public static String questionFolderName="";
	public static String EssayQuestion="";
	public static String TFQuestion="";
	
	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("QuestionsSuite","TestCase_BulkRemoveItems");
	}	 


	@Test
	public void verifyBulkMoveToAnotherFolder() throws Exception
	{
		questionFolderName="questionFolder"+(100000 + new Random().nextInt(900000));
		EssayQuestion="EssayQuestion"+(100000 + new Random().nextInt(900000));
		TFQuestion="TFQuestion"+(100000 + new Random().nextInt(900000));
		QuestionUtil.CreateQSFolder(questionFolderName);
		QuestionUtil.create_SaveQuestion(questionFolderName, "ESSAY", EssayQuestion,"");
		QuestionUtil.create_SaveQuestion(questionFolderName, "TF", TFQuestion,"");
		QuestionUtil.ClickOnQuestionsTab();
		QuestionUtil.openQuestionFolder(questionFolderName);
		elementExists(OR.getProperty("questionListCheckAll")).click();
		Select selectBulkAction=new Select(elementExists("//*[@id='bulkOptionSelect']"));
		selectBulkAction.selectByVisibleText("Remove Items");
		waitInSeconds(2);
		if(isAlertPresent())
			driver.switchTo().alert().accept();
		WaitForProgressBar();
		if(elementExists(OR.getProperty("ConfirmationMessage")).getText().trim().equals("You have removed the requested questions."))
			APP_LOGS.debug("Questions removed successfully!!");
		else 
			Assert.assertTrue("Error in bulk question removal!", false);
		QuestionUtil.openQuestionFolder(questionFolderName);
		if(elementExists("//*[@id='questionsTable']//tr/*[.='No matching records found']")!=null)
			System.out.println("Bulk removed questions from folder "+questionFolderName);
		else
			Assert.assertTrue("Error in Bulk question removal from folder "+questionFolderName,false);
	}


	@AfterMethod 
	public void DefaultResult(ITestResult result) throws InterruptedException {
		QuestionUtil.DeleteQSFolder(questionFolderName);

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