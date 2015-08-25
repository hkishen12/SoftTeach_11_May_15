package com.suiteMarchRelease;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.AssessmentUtil;
import Util.QuestionUtil;
import Util.TestUtil;

public class TestCase_EI2219 extends TestBase
{
	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("MarchReleaseSuite","TestCase_EI2219");
	}	 


	@Test
	public void verifyEI2219Test() throws Exception
	{
		APP_LOGS.debug("Testing EI-2219 Sub: Question Import : Successfully imported count is not proper");
		driver.get("https://einp2.examsoft.com/nycpm");
		waitInSeconds(2);
		TestUtil.doLogin("4060@examsoft.com", "mpcyn2");
		APP_LOGS.debug("Logged in with Credentials : 4060@examsoft.com/mpcyn2");
		QuestionUtil.ClickOnQuestionsTab() ; //click on the question tab
		elementExists(OR.getProperty("QS_ImportQsLink")).click(); //click on Import questions button
		waitInSeconds(2);
		elementExists("//*[@id='rtffile']").sendKeys(System.getProperty("user.dir")+"\\src\\Import\\Nutrition Final Formatted_fixed.rtf");
		APP_LOGS.debug("Selected the file!!");
		elementExists("//*[@id='RTF']").click();
		APP_LOGS.debug("Clicked on Next button!");
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='importItemsHeader']//td[contains(text(),'Import Questions')]")));
		elementExists(OR.getProperty("QS_ImportButton")).click();
		APP_LOGS.debug("Clicked on Import Valid Questions button!");
		WaitForProgressBar();
		waitInSeconds(2);
		QuestionUtil.ComapreTwoStringVal(getObjectxpathNew("QS_ImportTabSeperated_ConfirmMsg").getText().trim(),"Valid questions have been imported.");
		if(elementExists("//*[@id='successfullyImportedItems']").getText().contains("0")){
			Assert.fail("Successfully imported count is not proper");
		}else
			APP_LOGS.debug("Successfully imported count is proper");
	}


	public static void verifyEI3135(String assessment_title) throws Exception {
		Thread.sleep(3000);
		AssessmentUtil.doNewAssessment();
		System.out.println("Reached assessment creation page");
		AssessmentUtil.doNewAssessmentWithTitle(assessment_title);
		AssessmentUtil.doNewAssessmentAddQuestions();
		AssessmentUtil.doSaveCreatedAssessment();
		AssessmentUtil.doPost2assessment();
		if(elementExists(OR.getProperty("posting_Error_Message_icon"))==null){
			Assert.fail("Post assessment: Getting unexpected error while posting assessment");
		}
		else
			System.out.println("Post assessment:Assessment got posted");
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