package com.suiteQuestions;


import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.QuestionUtil;
import Util.TestUtil;

public class TestCase_CreateMCQuestions extends TestBase
{

	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("QuestionsSuite","TestCase_CreateMCQuestions");
	}	 


	@Test
	public void verifyCreateMCQuestionsTest() throws Exception
	{
		String test_Folder = CONFIG.getProperty("QS_CreateQSFolderName");
		if(!test_Folder.matches(".*\\d+.*"))
			 test_Folder="ITEMS";
		String questionType="MC";
		String QStitle=Data.getProperty("QuestionTitle1");
		String questionCategory=Data.getProperty("DefaultCatName");
		QuestionUtil.create_SaveQuestion(test_Folder, questionType, QStitle,questionCategory);
		count++;

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