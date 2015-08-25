package com.SuiteExamTaker;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Util.ExamTakerUtil;
import Util.TestUtil;

import com.SuiteExamTaker.TestSuiteBase;

public class TestCase_EmailExamTaker extends TestSuiteBase
{


	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("ExamTakerSuite","TestCase_EmailExamTaker");
	}	 


	@Test
	public void EmailET() throws Exception
	{			
		System.out.println("ExamTaker Accessible from Excel");
		ExamTakerUtil.ClickOnExamTakerTab();
		ExamTakerUtil.doSearchExamTaker("ExamTakerID");
		ExamTakerUtil.ClickOnEmailET();
		ExamTakerUtil.doEmailET();

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

