package com.SuitePageCheck;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Util.PageCheckUtil;
import Util.PageCheckUtil;
import Util.TestUtil;

public class TestCase_PageCheckReleaseResult extends TestSuiteBase {
	
	
	// Runmode of test case in a suite  
	@BeforeTest
	public void checkTestSkip() throws Exception
	 {	 
		TestUtil.checkTestSkip("PageCheckSuite","TestCase_PageCheckReleaseResult");
	 }	 
	
	@Test
	public void PageCheckReleaseResult() throws Exception
	{	
		PageCheckUtil.ClickOnAssessmentsTab();		
	    PageCheckUtil.SearchAssessmentWithTitle(CONFIG.getProperty("ExamName_Automation"));
		PageCheckUtil.ClickOnGridResult();
		PageCheckUtil.ClickOnReleaseResults();
		PageCheckUtil.CompareNameReleaseResults();
	}
	
	@AfterMethod
	public void DefaultResutl(ITestResult result) throws InterruptedException {
		System.out.println("method name:" + result.getMethod().getMethodName());
		System.out.println("Sucess %:" + result.isSuccess());
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
			System.out.println("Method is pass Not need to call Exit");  

		}
	}

}
