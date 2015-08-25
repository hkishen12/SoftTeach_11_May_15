package com.suiteMarchRelease;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.TestUtil;

public class TestCase_EI2851 extends TestBase
{
   
	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("MarchReleaseSuite","TestCase_EI2851");
	}	 


	@Test
	public void verifyEI3241Test() throws Exception
	{
		System.out.println("Testing EI-2851 Sub:Questions By Category: List does not appear after selecting folder");
		
		//Click on Question Tab
	    Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id='navbar']/ul[1]/li/a")).click();
		
		//Clicked on Question by Category
		Thread.sleep(5000);
		driver.findElement(By.xpath("//*[@id='categories1']")).click();
		
		//Clicking on the displayed Category and capturing Text
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id='categoriesTreeDiv']/ul/li/ul/li[1]/span/span[3]/span[1]")).click();
		String E = driver.findElement(By.xpath("//*[@id='categoriesTreeDiv']/ul/li/ul/li[1]/span/span[3]/span[1]")).getText();
		String categorynane = E.trim();
		System.out.println("Category selected is" +categorynane);
		
		// Counting the question number in the Folder
		Thread.sleep(3000);
		String Actual = driver.findElement(By.xpath("//*[@id='categoriesTreeDiv']/ul/li/ul/li[1]/span/span[3]/span[3]")).getText().trim();
		System.out.println("No. of Question count in the folder are" +  Actual );
				
		// Counting the question number in the page
		String Expected = driver.findElement(By.xpath("//*[@id='sizeOfGrid']/strong")).getText().trim();
		System.out.println("No. of Question displayed on the Page are" +  Expected );
				 
		// Comparing both the Count
			if (Actual.equalsIgnoreCase(Expected)) {
				System.out.println("No. of Question in Folder and Page are Same");
				 } else {
					 Assert.fail("Folder Count and Page count are different");
				 }
				 
	   
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