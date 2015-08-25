package com.suiteMarchRelease;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.AssessmentUtil;
import Util.QuestionUtil;
import Util.ReleaseUtil;
import Util.TestUtil;

public class TestCase_EI3194 extends TestBase
{
	public String assessmentTitle, QsTitle, folderName;
	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("MarchReleaseSuite","TestCase_EI3194");
	}	 


	@Test
	public void verifyEI3194Test() throws Exception
	{
		System.out.println("TestCase_EI3194 sub:Duplicating an assessment having notices, changes all the notices type to 'Template'");
		assessmentTitle = ReleaseUtil.randomStringGen("EI3194_Assessment_");
		QsTitle = ReleaseUtil.randomStringGen("EI3194_Qs_");
		folderName = ReleaseUtil.randomStringGen("EI3194_");
		ReleaseUtil.verify3194(assessmentTitle, QsTitle, folderName);
	}

	@AfterMethod 
	public void DefaultResult(ITestResult result) throws Exception {
		//clean up part
		AssessmentUtil.doRetireposting(assessmentTitle);
		QuestionUtil.DeleteQSInFolder(folderName);
		QuestionUtil.DeleteQSFolder(folderName);
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