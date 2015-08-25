package com.suiteMarchRelease;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.QuestionUtil;
import Util.ReleaseUtil;
import Util.TestUtil;

public class TestCase_EI3243 extends TestBase
{
	public static String folderName="";
	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("MarchReleaseSuite","TestCase_EI3243");
	}	 


	@Test
	public void verifyEI3243Test() throws Exception
	{
		System.out.println("Testing EI-3243 Sub:Clicking on the Next from Question view page takes the user to Blank  page");
		String QsTitle1, QsTitle2;
		QsTitle1 = ReleaseUtil.randomStringGen("EI3243_Qs_");
		QsTitle2 = ReleaseUtil.randomStringGen("EI3243_Qs_");
		folderName = ReleaseUtil.randomStringGen("EI3243_");

		ReleaseUtil.VerifyEI3243(QsTitle1, folderName);

		//Create a Qus and Approve it
		QuestionUtil.create_ApproveQuestion(folderName,"TF",QsTitle2);
		//ReleaseUtil.VerifyEI3243(QsTitle2, folderName);

		//Capturing the Question Title of above created Question
		Thread.sleep(2001);
		String str1 = driver.findElement(By.xpath("//*[@id='breadcrumb']/strong")).getText();
		String createdQus = str1.trim();
		System.out.println("Created Question Title is" + createdQus );


		//Click on next Button and Capturing the next Question Title
		driver.findElement(By.xpath("//*[@id='nextQuestion']/img")).click();
		QuestionUtil.WaitForProgressBar();

		Thread.sleep(2001);
		String str2 = driver.findElement(By.xpath("//*[@id='breadcrumb']/strong")).getText();
		String nextQus = str2.trim();
		System.out.println("Next Question Title is" + nextQus );



		//Comparing
		if(!createdQus.equalsIgnoreCase(nextQus)){
			System.out.println("Navigating to the next Question and Next Button is working fine");
		}else {
			Assert.fail("Next Button doesnt work");
		}



	}
	@AfterMethod 
	public void DefaultResult(ITestResult result) throws InterruptedException {
		//clean up part
		QuestionUtil.DeleteQSInFolder(folderName);
		QuestionUtil.DeleteQSFolder(folderName);

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