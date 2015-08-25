package com.suiteMarchRelease;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.QuestionUtil;
import Util.ReleaseUtil;
import Util.TestUtil;

public class TestCase_EI3241 extends TestBase
{
    //Global Declaration
	public static String email1="";
	
	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("MarchReleaseSuite","TestCase_EI3241");
	}	 


	@Test
	public void verifyEI3241Test() throws Exception
	{
		System.out.println("Testing EI-3241 Sub:Edit User : The green progress bar never stops while editing user");
		
		email1=randomStringGen("xyx")+"@my.com";
		
		//Creating User with Restricted Rights and Editing it
	    ReleaseUtil.VerifyEI2632("Tom","Smith",email1,"123456");	
	    
	    WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement adminLink=driver.findElement(By.xpath(OR.getProperty("AdminLink")));
		WebElement usersLink=driver.findElement(By.xpath(OR.getProperty("UsersLink")));
		wait.until(ExpectedConditions.elementToBeClickable(adminLink));
		Actions  actions  = new Actions(driver);
		actions.moveToElement(adminLink).moveToElement(usersLink).click().build().perform();
		QuestionUtil.WaitForProgressBar();

		
	    driver.findElement(By.xpath("//*[@id='omniSearch']")).sendKeys(email1);
	    Thread.sleep(2000);
	    driver.findElement(By.xpath("//*[@id='searchUsers']")).click();
	    QuestionUtil.WaitForProgressBar();
	    driver.findElement(By.xpath("//*[@id='userListTable']//td[15]/a[1]/img")).click();
	    QuestionUtil.WaitForProgressBar();
	    
	    Thread.sleep(2000);
	    driver.findElement(By.xpath("//*[@id='viewMyaccountSection_1']/table/tbody/tr[4]/td[3]/input")).click();
	    System.out.println("Changed the rights of Question from Restried to Full Rightl");
	    
	    driver.findElement(By.xpath(OR.getProperty("SaveUser_button"))).click();
		QuestionUtil.WaitForProgressBar();
		System.out.println(email1);
		
		if ((driver.findElement(By.xpath(OR.getProperty("QS_ImportTabSeperated_ConfirmMsg")))).getText().trim().equalsIgnoreCase("The user has been updated.")){
			System.out.println("User is Edited successfully");
		}
		else{
			Assert.fail("There is some problem while Editing new user");
		}
	    
	   
	}
	@AfterMethod 
	public void DefaultResult(ITestResult result) throws InterruptedException {
		//clean up part
		ReleaseUtil.DeleteUser(email1);
		
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