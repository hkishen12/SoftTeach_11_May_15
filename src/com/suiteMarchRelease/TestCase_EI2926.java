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

public class TestCase_EI2926 extends TestBase
{
	public static String QuesfolderName="";
	public static String email="";
	public static String Test_QsTitle="";
	public static String assessmentTitle="";
	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("MarchReleaseSuite","TestCase_EI2926");
	}	 


	@Test
	public void verifyEI2926Test() throws Exception
	{
		System.out.println("Testing EI2926 sub:Unable to save assessment with questions that have read-only permissions");
		QuesfolderName=ReleaseUtil.randomStringGen("fol2926_");
		email=randomStringGen("xyx")+"@my.com";
		Test_QsTitle=randomStringGen("778899");// question title
		assessmentTitle=randomStringGen("assess_");
		ReleaseUtil.VerifyEI2926(QuesfolderName,Test_QsTitle,assessmentTitle,email);
	}


	@AfterMethod 
	public void DefaultResult(ITestResult result) throws Exception {
		//---delete user
		TestUtil.doLogout();
		isBrowserOpened=false;
		openBrowser();	
		TestUtil.doLogin();
		//--------------
		AssessmentUtil.deleteAssessment(assessmentTitle);//cleanup
		QuestionUtil.DeleteQSInFolder(QuesfolderName);//cleanup
		QuestionUtil.DeleteQSFolder(QuesfolderName);//cleanup
		ReleaseUtil.DeleteUser(email);//cleanup

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