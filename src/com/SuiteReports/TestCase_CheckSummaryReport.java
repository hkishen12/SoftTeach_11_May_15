package com.SuiteReports;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Util.ReportUtil;
import Util.TestUtil;

public class TestCase_CheckSummaryReport extends TestSuiteBase {
	
	
	// Runmode of test case in a suite  
	@BeforeTest
	public void checkTestSkip() throws Exception
	 {	 
		TestUtil.checkTestSkip("ReportsSuite","TestCase_CheckSummaryReport");
	 }	 
	
	@Test
	public void CheckSummaryReports() throws Exception
	{	
     ReportUtil.ClickOnAssessmentsTab();
     ReportUtil.SearchAssessmentWithTitle(CONFIG.getProperty("ExamName_Automation"));
	 //For More Than 50 Students.
     //ReportUtil.SearchAssessmentWithTitle("Hari_New_Scantron_Mixed All"); 
	 ReportUtil.ClickOnGridResult();
	 ReportUtil.ClickOnSummaryReport();
	 ReportUtil.SummaryReportsData();
	 
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
