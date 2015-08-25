package com.suiteMarchRelease;

import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.AssessmentUtil;
import Util.TestUtil;

public class TestCase_EI2760 extends TestBase
{

	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("MarchReleaseSuite","TestCase_EI2760");
	}	 


	@Test
	public void verifyEI1292Test() throws Exception
	{
		APP_LOGS.debug("Testing EI2760 sub:Asssessment Creation: On 'Add Questions to Assessment' page, color of + button does not change on addition of question");
		AssessmentUtil.navigateToCreateNewQuestionAssessmentPage();
		elementExists("//a[contains(@class,'examsAddQuestionsOpen')]").click();
		APP_LOGS.debug("Clicked on 'Add Questions To Assessment' button");
		waitInSeconds(1);
		APP_LOGS.debug("Popup window with title '"+elementExists("//*[@id='ui-id-11']").getText()+"' opened!");
		if(elementExists("//*[@id='questionsTable']//tr[1]//img[@src='/STW-war/images/Icons/add.png']").getAttribute("src").trim().
				contains("/STW-war/images/Icons/add.png")){
			APP_LOGS.debug("Color of + button before adding is green!");
		}
		elementExists("//*[@id='questionsTable']//tr[1]//img[@src='/STW-war/images/Icons/add.png']").click();
		APP_LOGS.debug("Clicked on add question button");
		APP_LOGS.debug("Confirmation message '"+elementExists("//*[@id='examsAddQuestions']//tr/td[2]//ul").getText()+"' is displayed!");
		if(elementExists("//*[@id='questionsTable']//tr[1]//img[@src='/STW-war/images/Icons/delete.png']").getAttribute("src").trim().
				contains("/STW-war/images/Icons/delete.png")){
			APP_LOGS.debug("Color of + button before adding is red!");
		}else{
			elementExists("/html/body/div[17]").sendKeys(Keys.ESCAPE);//click on escape to close popup
			WaitForProgressBar();
			elementExists("//*[@id='examCreateEdit']//a[text()='Cancel']").click();//click on cancel button
			WaitForProgressBar();
			Assert.fail("On 'Add Questions to Assessment' page, color of + button does not change on addition of question");
		}
		elementExists("/html/body/div[17]").sendKeys(Keys.ESCAPE);//click on escape to close popup
		WaitForProgressBar();
		elementExists("//*[@id='examCreateEdit']//a[text()='Cancel']").click();//click on cancel button
		WaitForProgressBar();
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