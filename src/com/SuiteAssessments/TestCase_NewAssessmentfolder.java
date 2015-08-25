package com.SuiteAssessments;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Util.AssessmentUtil;
import Util.TestUtil;

public class TestCase_NewAssessmentfolder extends TestSuiteBase
{

	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("AssessmentsSuite","TestCase_NewAssessmentfolder");
	}	 


	@Test
	public void newAssessmentfolder() throws Exception
	{			

		String assessmentFolderName=randomStringGen(Data.getProperty("AssessmentFolderName_string"));
		Data.setProperty("AssessmentFolderName_string",assessmentFolderName );
		String confirmationMessage=Expected_string.getProperty("FolderCreationmess_string").replace("AutomationFolder", assessmentFolderName);
		String confirmationMessage1=Expected_string.getProperty("Foldersharemess_string").replace("AutomationFolder", assessmentFolderName);
		String confirmationMessage2=Expected_string.getProperty("Folderdeletionmess_string").replace("AutomationFolder", assessmentFolderName);
		String confirmationMessage3=Expected_string.getProperty("folderreeditmess_string").replace("AutomationFolder", assessmentFolderName);
		Expected_string.setProperty("FolderCreationmess_string",confirmationMessage);
		Expected_string.setProperty("Foldersharemess_string",confirmationMessage1);
		Expected_string.setProperty("Folderdeletionmess_string",confirmationMessage2);
		Expected_string.setProperty("folderreeditmess_string",confirmationMessage3);
		AssessmentUtil.donewAssessmentfolder();
		
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