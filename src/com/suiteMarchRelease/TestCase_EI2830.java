package com.suiteMarchRelease;

import org.junit.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.QuestionUtil;
import Util.TestUtil;

public class TestCase_EI2830 extends TestBase
{

	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("MarchReleaseSuite","TestCase_EI2830");
	}	 


	@Test
	public void verifyEI2830Test() throws Exception
	{
		System.out.println("Testing EI2830 sub:Search by Question ID and Question name is not working in Question tab");
		QuestionUtil.ClickOnQuestionsTab();
		String questionName=elementExists("//*[@id='questionsTable']//tr[1]/td[2]/a").getText().trim();
		elementExists("//*[@id='questionsTable']//tr[1]/td[2]/a").click();//click on first question in list and get its name and id.
		WaitForProgressBar();
		String questionID=elementExists("//*[@id='sidebarcontent']/table/tbody/tr[1]//strong[1]").getText().trim();
		QuestionUtil.searchQuestionByName(questionID);
		if(elementExists("//*[@id='questionsTable']//tr[1]/td[2]/a").getText().trim().equalsIgnoreCase(questionName)){
			APP_LOGS.debug("Question Searched successfully by ID");
		}else{
			Assert.fail("Search by Question ID is not working in Question tab");
		}
		QuestionUtil.searchQuestionByName(questionName);
		if(elementExists("//*[@id='questionsTable']//tr[1]/td[2]/a").getText().trim().equalsIgnoreCase(questionName)){
			APP_LOGS.debug("Question Searched successfully by Name");
		}else{
			Assert.fail("Search by Question Name is not working in Question tab");
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