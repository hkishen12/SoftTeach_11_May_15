package com.suiteUsers;


import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.TestUtil;
import Util.UsersUtil;

public class TestCase_IncludeGraderOnlyAccountsCheck extends TestBase
{

	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("UsersSuite","TestCase_IncludeGraderOnlyAccountsCheck");
	}	 

	@Test
	public void verifyIncludeGraderOnlyAccountsCheck() throws Exception	{
		UsersUtil.clickOnUsersMenu();
		String userEmailAddress=Data.getProperty("userEmailAddress");
		String restrictedUserEmailAddress=Data.getProperty("restrictedUserEmailAddress");

		// With Include Grader Only Accounts checkbox Unchecked
		APP_LOGS.debug("Searching user with 'Include Grader Only Accounts' checkbox unchecked...");
		UsersUtil.check_uncheck_IncludeGraderOnlyAccounts(false);
		try{
			UsersUtil.searchUserByEmail(restrictedUserEmailAddress);
		}catch(Exception e){
			APP_LOGS.debug("Restricted user is not searched!");
		}
		UsersUtil.searchUserByEmail(userEmailAddress);
		APP_LOGS.debug("Non-restricted user is searched!");

		// With Include Grader Only Accounts checkbox checked
		APP_LOGS.debug("Searching user with 'Include Grader Only Accounts' checkbox checked...");
		UsersUtil.check_uncheck_IncludeGraderOnlyAccounts(true);
		UsersUtil.searchUserByEmail(restrictedUserEmailAddress);
		APP_LOGS.debug("Restricted user is searched!");
		UsersUtil.searchUserByEmail(userEmailAddress);
		APP_LOGS.debug("Non-restricted user is searched!");
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