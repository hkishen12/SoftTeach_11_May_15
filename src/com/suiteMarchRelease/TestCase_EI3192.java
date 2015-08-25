package com.suiteMarchRelease;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.ReleaseUtil;
import Util.TestUtil;

public class TestCase_EI3192 extends TestBase
{

// Runmode of test case in a suite
@BeforeTest
public void checkTestSkip() throws Exception
{	 
	TestUtil.checkTestSkip("MarchReleaseSuite","TestCase_EI3192");
}	 
	 

@Test
public void verifyEI3192Test() throws Exception
{
	System.out.println("Users listed as Inactive by Default when created by Non-Key Admin");
	closeBrowser();
	openBrowser();
	String randomEmail = ReleaseUtil.randomStringGen("EI3192")+"@tester.com";
	ReleaseUtil.CustomLogin(CONFIG.getProperty("KeyAdmin_UserName"),CONFIG.getProperty("KeyAdmin_Password"));

	ReleaseUtil.createNonKeyAdmin("EI-3192","EI-3192",randomEmail,"test123");
	closeBrowser();
	openBrowser();
	ReleaseUtil.CustomLogin(randomEmail,"test123");
	String expectedStr = driver.findElement(By.xpath("//*[@id='accountbar']/ul[1]/li/a")).getText();
	if (!expectedStr.contains("EI-3192, EI-3192")){
		Assert.fail("Login is not true or the Login user name is not matching");
	}
	System.out.println("Login with non key admin is success");
	
	closeBrowser();
	openBrowser();
	//TestUtil.doLogin(); this is not key admin login-doubt
	ReleaseUtil.CustomLogin(CONFIG.getProperty("KeyAdmin_UserName"),CONFIG.getProperty("KeyAdmin_Password"));
	ReleaseUtil.DeleteUser(randomEmail); //clean up
	System.out.println("delete user is success");
	
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