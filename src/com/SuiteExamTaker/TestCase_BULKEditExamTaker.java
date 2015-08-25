package com.SuiteExamTaker;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Util.ExamTakerUtil;
import Util.TestUtil;

public class TestCase_BULKEditExamTaker extends TestSuiteBase
{

	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("ExamTakerSuite","TestCase_BULKEditExamTaker");
	}	 


	@Test
	public void BULKEditET() throws Exception
	{			
		System.out.println("ExamTaker Accessible from Excel");
		ExamTakerUtil.ClickOnExamTakerTab();
		//ExamTakerUtil.ClickOnExportExcel();
		waitInSeconds(5);
		ExamTakerUtil.doSearchByAnyOption("StudentName", "Automation");
		ExamTakerUtil.ClickOnBulkEdit();
		ExamTakerUtil.doBulkEdit();

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

