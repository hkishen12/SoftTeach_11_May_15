package com.suiteUsers;


import java.util.Random;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.TestUtil;
import Util.UsersUtil;

public class TestCase_EditUser extends TestBase
{

	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("UsersSuite","TestCase_EditUser");
	}	 

	@Test
	public void verifyEditUser() throws Exception
	{
		UsersUtil.clickOnUsersMenu();
		String oldUserEmailAddress=Data.getProperty("userEmailAddress");
		UsersUtil.searchUserByEmail(oldUserEmailAddress);
		String fname="fname_"+(1000+new Random().nextInt(999));
		String lname="lname_"+(1000+new Random().nextInt(999));
		String newUserEmailAddress="user_"+(100000+new Random().nextInt(999999))+"@examsoft.com";
		UsersUtil.editUser(fname,lname, newUserEmailAddress,oldUserEmailAddress);
		elementExists(OR.getProperty("cancelUserChangesButton")).click();
		WaitForProgressBar();
		if(elementExists(OR.getProperty("userListTable"))!=null){
			APP_LOGS.debug("Users page loaded successfully!");
		}else
			Assert.assertTrue(false, "Error in loading Users page!");
		UsersUtil.searchUserByEmail(newUserEmailAddress);
		UsersUtil.verifyUserEditDetails(fname, lname, newUserEmailAddress);
		Data.setProperty("userEmailAddress", newUserEmailAddress);
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