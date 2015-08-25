package com.suiteMarchRelease;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.TestUtil;

public class TestCase_EI2541 extends TestBase
{

	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("MarchReleaseSuite","TestCase_EI2541");
	}	 


	@Test
	public static void verifyEI2541Test() throws Exception
	{
		System.out.println("Testing EI2541 sub:Login Page : In QA Login page, student ID is not appearing under Exam Takers");
		 //logout
		   Thread.sleep(3000);
		   WebElement setting= elementExists("//*[@id='accountbar']//*[@class='accountnav user']/img");
		   WebElement logoutlink=elementExists("//*[@id='logoutLink']");
		   
		   Actions action= new Actions(driver);
		   Action logout= action.moveToElement(setting).moveToElement(logoutlink).click().build();
		   logout.perform();
		   WaitForProgressBar();
		   Thread.sleep(2000);
		if(elementExists("//*[@id='websitebody']//tr[2]/td[contains(text(),'Student ID:')]")!=null)
			APP_LOGS.debug("In QA Login page, student ID is appearing under Exam Takers section!");
		else
			Assert.fail(" In QA Login page, student ID is not appearing under Exam Takers");
	}

	@AfterMethod 
	public void DefaultResult(ITestResult result) throws InterruptedException {
		TestUtil.doLogin();
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