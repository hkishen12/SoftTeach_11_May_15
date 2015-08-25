package com.suiteMarchRelease;

import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.AssessmentUtil;
import Util.TestUtil;

public class TestCase_EI2596 extends TestBase
{
	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("MarchReleaseSuite","TestCase_EI2596");
	}	 


	@Test
	public void verifyEI2596Test() throws Exception
	{
		APP_LOGS.debug("Testing EI-2596 sub: Export Assessment : {QA} not able to export assessment in current QA.");
		driver.get("https://einp2.examsoft.com/nycpm");
		waitInSeconds(2);
		TestUtil.doLogin("4060@examsoft.com", "mpcyn2");
		APP_LOGS.debug("Logged in with Credentials : 4060@examsoft.com/mpcyn2");
		AssessmentUtil.ClickOnAssessmentsTab();
		elementExists("//*[@id='examsTable']//tr[1]/td[2]/a").click();
		WaitForProgressBar();
		waitInSeconds(2);
		APP_LOGS.debug("Clicked on assessment link!");
		elementExists("//*[@id='tabs']//a[text()='Export/Print']").click();
		waitInSeconds(1);
		APP_LOGS.debug("Clicked on Export/Print button!");
		if(elementExists("//*[@class='ui-dialog-title']")!=null){
			APP_LOGS.debug("Popup with window title -"+elementExists("//*[@class='ui-dialog-title']").getText()+" opened!!");
			elementExists("//*[@id='exportExam']").sendKeys(Keys.ESCAPE);
		}else{
			elementExists("//*[@id='exportExam']").sendKeys(Keys.ESCAPE);
			Assert.fail("Export Assessment : {QA} not able to export assessment in current QA.");
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