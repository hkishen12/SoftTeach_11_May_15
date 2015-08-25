package com.suiteQuestions;


import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.QuestionUtil;
import Util.TestUtil;

public class TestCase_FolderQuestionCount_LHS_RHS_Grid extends TestBase
{

	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("QuestionsSuite","TestCase_FolderQuestionCount_LHS_RHS_Grid");
	}	 


	@Test
	public void verifyFolderQuestionCount_LHS_RHS_Grid(){
		QuestionUtil.ClickOnQuestionsTab();
		QuestionUtil.check_LHS_RHS_Count("Folder", "ITEMS");
		elementExists(OR.getProperty("QS_QsbyCategoriesLink")).click();
		waitInSeconds(2);
		QuestionUtil.check_LHS_RHS_Count("Category", "CATEGORIES");
		elementExists(OR.getProperty("CreateMC_Link")).click();
		WaitForProgressBar();
	}

	@AfterMethod 
	public void DefaultResult(ITestResult result) throws InterruptedException {
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