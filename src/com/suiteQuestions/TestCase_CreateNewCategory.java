package com.suiteQuestions;


import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.QuestionUtil;
import Util.TestUtil;

public class TestCase_CreateNewCategory extends TestBase
{

	public static String categoryName="";
	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("QuestionsSuite","TestCase_CreateNewCategory");
	}	 


	@Test
	public void verifyCreateCategoryTest() throws Exception
	{
		QuestionUtil.ClickOnQuestionsTab() ;
		WebElement qsbyc = getObjectxpathNew("QS_QsbyCategoriesLink");
		qsbyc.click();
		categoryName=randomStringGen("Automation");
		Data.setProperty("DefaultCatName",categoryName );
		Expected_string.setProperty("Qs.Categories.list.12", categoryName);
		QuestionUtil.CreateCategory(categoryName);
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