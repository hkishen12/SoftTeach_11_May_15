package com.suiteUsers;


import java.util.Random;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.TestUtil;
import Util.UsersUtil;

public class TestCase_CreateRestrictedUser extends TestBase
{

	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("UsersSuite","TestCase_CreateRestrictedUser");
	}	 

	@Test
	public void verifyCreateRestrictedUser() throws Exception
	{
		UsersUtil.clickOnUsersMenu();
		UsersUtil.clickOnAddNewUserButton();
		String fname="GraderFname_"+(1000+new Random().nextInt(999));
		String lname="GraderLname_"+(1000+new Random().nextInt(999));
		String userEmailAddress="GraderOnly_"+(100000+new Random().nextInt(999999))+"@examsoft.com";
		Data.setProperty("restrictedUserEmailAddress", userEmailAddress);
		UsersUtil.enterUserDetails(fname, lname, userEmailAddress);
		elementExists(OR.getProperty("restrictedGraderCB")).click();
		WaitForProgressBar();
		((JavascriptExecutor)driver).executeScript("document.getElementById('restrictedGraderWarningDlgBtn').click()");
		waitInSeconds(2);
		UsersUtil.ClickOnSaveUserSettingsBtn();
		if(elementExists(OR.getProperty("ConfirmationMessage")).getText().trim().equals("The new user has been added.")){
			System.out.println("New user '"+userEmailAddress+"' has been added successfully!");
		}else
			Assert.assertTrue(false, "Error in adding user!");
		UsersUtil.searchUserByEmail(userEmailAddress);
		UsersUtil.clickOnEditBtn(userEmailAddress);
		if(!elementExists("//*[@id='tabs-content']").isDisplayed())
			APP_LOGS.debug("Successfully created restricted user!");
		else
			Assert.assertTrue(false, "Error in Restricted user creation!");
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