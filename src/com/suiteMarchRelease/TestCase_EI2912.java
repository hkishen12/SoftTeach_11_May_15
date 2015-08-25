package com.suiteMarchRelease;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.CategoryUtil;
import Util.QuestionUtil;
import Util.ReleaseUtil;
import Util.TestUtil;

public class TestCase_EI2912 extends TestBase
{
	public static String Test_Folder="";
	public static String Test_Category="";
	public static String Test_QsTitle="";
	public static String Test_Category1="";

	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("MarchReleaseSuite","TestCase_EI2912");
	}	 


	@Test
	public void verifyEI1292Test() throws Exception
	{
		Test_Folder=ReleaseUtil.randomStringGen("Test_Fold");
		Test_Category=ReleaseUtil.randomStringGen("Test_Category");
		Test_QsTitle=ReleaseUtil.randomStringGen("11223344");
		Test_Category1=ReleaseUtil.randomStringGen("Test_Category1");
		System.out.println("Testing EI2912 sub:Category Tooltip: Tooltip does not show correct path when we move destination folder");
		ReleaseUtil.VerifyEI2912(Test_Folder,Test_Category,Test_QsTitle,Test_Category1);
	}

	@AfterMethod 
	public void DefaultResult(ITestResult result) throws InterruptedException {
		QuestionUtil.DeleteQSInFolder(Test_Folder);//cleanup
		CategoryUtil.DeleteSubCategory(Test_Category1);//cleanup // pass parent category name to delete sub-category
		CategoryUtil.DeleteQSCategory(Test_Category1);//cleanup
		QuestionUtil.DeleteQSFolder(Test_Folder);//cleanup
		System.out.println("Cleanup done");
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