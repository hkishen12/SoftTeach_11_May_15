package com.suiteMarchRelease;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.AssessmentUtil;
import Util.TestUtil;

public class TestCase_RUB661 extends TestBase
{
	public static String assessmentTitle="";
	public static String folderName="";
	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("MarchReleaseSuite","TestCase_RUB661");
	}	 


	@Test
	public void verifyRUB541Test() throws Exception
	{
		System.out.println("TestCase_RUB661: Sub: Addition/deletion of rubrics to performance assessment doesnt update the total added rubrics count on 'Add Rubrics' window");
		assessmentTitle=randomStringGen("assess_rub661_");
		folderName=randomStringGen("folder_rub661_");
		AssessmentUtil.createNewExamFolder(folderName);
		AssessmentUtil.navigateToCreateNewPerformanceAssessmentPage();
		elementExists("//*[@id='rubricRadioBtn']").click();
		APP_LOGS.debug("Selected scoring by Rubrics!!");
		AssessmentUtil.performanceAssessmentwithTitle_FolderName(assessmentTitle,folderName);
		AssessmentUtil.addRubricsToAssessment();//add 1st rubric to assessment
		AssessmentUtil.savePerformanceAssessment();
		AssessmentUtil.addRubricsToAssessment();//add 2nd rubric to assessment
		AssessmentUtil.savePerformanceAssessment();
		AssessmentUtil.removeAttachedRubric();//remove 1 attached rubric
		int count_before=(driver.findElements(By.xpath("//*[@id='rubricsExamDraftTable']/tbody/tr")).size())-2;//the table has 2 extra rows, so delete 2 from total count
		elementExists("//img[@title='Add Rubrics']").click();
		System.out.println("clickded on Add Rubrics button!");	
		waitInSeconds(2);
		int count_after=Integer.parseInt(elementExists("//*[@id='totalRubricsCount']").getText().trim());
		elementExists("//a[@onclick='doneAddingRubricsToExam()']").click();
		APP_LOGS.debug("Clicked on Close button!");
		WaitForProgressBar();
		if(count_after==count_before)
			APP_LOGS.debug("Addition/deletion of rubrics to performance assessment updates the total added rubrics count on 'Add Rubrics' window correctly");
		else
			Assert.fail("Addition/deletion of rubrics to performance assessment doesnt update the total added rubrics count on 'Add Rubrics' window");
		elementExists("//*[@id='perfAssessmentForm']//a[text()='Cancel']").click();
		driver.switchTo().alert().accept();
		WaitForProgressBar();
	}

	@AfterMethod 
	public void DefaultResult(ITestResult result) throws Exception {
		AssessmentUtil.deleteAssessment(assessmentTitle);//cleanup
		AssessmentUtil.doSearchAssessmentInfolderWithTitle(folderName);
		System.out.println("Assessment is searched");
		Thread.sleep(3000);
		if(elementExists("//*[@id='examsTable']//tr//img[@src='/STW-war/Icons/delete.png']")!=null){
			// to delete performance assessment
			Thread.sleep(2000);
			elementExists("//*[@id='examsTable']//tr//img[@src='/STW-war/Icons/delete.png']").click();
			driver.findElement(By.xpath("//*[@id='retireExamListingBtn']")).click();
			WaitForProgressBar();
		}
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