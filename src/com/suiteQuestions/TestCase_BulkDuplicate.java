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

public class TestCase_BulkDuplicate extends TestBase
{
	public static String questionFolderName="";
	public static String EssayQuestion="";
	public static String TFQuestion="";
	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("QuestionsSuite","TestCase_BulkDuplicate");
	}	 


	@Test
	public void verifyBulkDuplicate() throws Exception
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
		selectBulkAction.selectByVisibleText("Duplicate Items");
		WaitForProgressBar();
		elementExists("//*[@id='AddedFolderUI1']/a[.='Select Folders']").click();
		waitInSeconds(2);
		elementExists("//*[@id='bulkEditFolderTreeDiv']//span[.='ITEMS']").click();
		WaitForProgressBar();
		waitInSeconds(2);
		elementExists("//*[@id='AddedFolderUI2']/a[.='Duplicate Items']").click();
		WaitForProgressBar();
		if(elementExists(OR.getProperty("ConfirmationMessage")).getText().trim().equals("The selected item(s) have been duplicated to the folder: ITEMS."))
			APP_LOGS.debug("Questions duplicated successfully!!");
		else 
			Assert.assertTrue("Error in bulk question duplication!", false);
		QuestionUtil.openQuestionFolder("ITEMS");
		boolean flag=false;
		for(int i=1;i<=3;i++){
			if(elementExists("//*[@id='questionsTable']//tr["+i+"]/td[2]").getText().contains("Copy of "+EssayQuestion)){
				APP_LOGS.debug("Questions bulk duplicated at destination folder!!");
				flag=true;
				break;
			}
		}
		if(!flag){
			Assert.assertTrue("Questions not bulk duplicated at destination folder!!",false);
		}
	}

	@AfterMethod 
	public void DefaultResult(ITestResult result) throws InterruptedException {
		QuestionUtil.DeleteQSInFolder(questionFolderName);
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