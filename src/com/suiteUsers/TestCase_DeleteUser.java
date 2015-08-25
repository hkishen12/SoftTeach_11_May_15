package com.suiteUsers;


import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.TestUtil;
import Util.UsersUtil;

public class TestCase_DeleteUser extends TestBase
{

	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("UsersSuite","TestCase_DeleteUser");
	}	 

	@Test
	public void verifyDeleteUser() throws Exception
	{
		UsersUtil.clickOnUsersMenu();
		String userEmailAddress=Data.getProperty("userEmailAddress");
		UsersUtil.searchUserByEmail(userEmailAddress);
		UsersUtil.deleteUser(userEmailAddress);
	}

	@AfterMethod 
	public void DefaultResult(ITestResult result) throws InterruptedException {
		String userEmailAddress=Data.getProperty("restrictedUserEmailAddress");
		UsersUtil.searchUserByEmail(userEmailAddress);
		UsersUtil.deleteUser(userEmailAddress);
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