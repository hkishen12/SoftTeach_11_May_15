package com.suiteLogin;

import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.TestUtil;

public class TestCase_ETLostUserIDPassword extends TestBase
{

	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("LoginSuite","TestCase_ETLostUserIDPassword");
	}	 

	@Test  
	public void TestCaseETLostUserIDPassword() throws Exception 
	{
		openBrowser();	

		if(elementExists("//a[contains(text(),'Lost Student ID or Password?')]")!=null){
			if(elementExists("//a[contains(text(),'Lost Student ID or Password?')]").isDisplayed()){
				elementExists("//a[contains(text(),'Lost Student ID or Password?')]").click();
				APP_LOGS.debug("Clicked on Forgot Student ID or Password linked!!");
				waitInSeconds(10);
				if(elementExists("//*[@id='ui-id-1']").getText().trim().contains("Forgot Your Student ID or Password")){
					APP_LOGS.debug("Forgot Student ID or Password popup oppened!!");
					elementExists("//*[@id='ui-id-1']").sendKeys(Keys.ESCAPE);
					Assert.assertTrue(true,"Forgot Student ID or Password popup oppened!!");
					closeBrowser();
					System.out.println("Closing the Browser");

				}else
					Assert.assertFalse(true,"Forgot Student ID or Password popup didn't oppened!!");
			}
		}else{
			Assert.assertFalse(true,"Forgot Student ID or Password link didn't oppened!!");
		}
	}

	@AfterMethod 
	public void DefaultResult(ITestResult result) throws InterruptedException {
		System.out.println("Method name: " + result.getMethod().getMethodName());
		System.out.println("Success %: " + result.isSuccess());
		System.out.println("Testcase name: "+result.getInstanceName().substring(result.getInstanceName().lastIndexOf(".")+1, result.getInstanceName().length()));

		if(!result.isSuccess()){
			TestUtil.takeScreenShot(result.getMethod().getMethodName());
			closeBrowser();
			System.out.println("Closing the Browser");
		}
		else{
			System.out.println("Testcase is passed, Not required to call Exit!");  

		}
	}



}
