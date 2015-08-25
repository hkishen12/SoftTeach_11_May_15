package com.SuiteAssessments;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Util.AssessmentUtil;
import Util.QuestionUtil;
import Util.ReleaseUtil;
import Util.TestUtil;

public class TestCase_multiplepost extends TestSuiteBase
{
	public static String testfolder = "";
	public static String QsName ="";
	public static String assessmentTitle ="";
		
// Runmode of test case in a suite
@BeforeTest
public void checkTestSkip() throws Exception
 {	 
	TestUtil.checkTestSkip("AssessmentsSuite","TestCase_multiplepost");
 }	 


@Test
public void multiplepost() throws Exception
	{	
	testfolder = ReleaseUtil.randomStringGen("multipostfol_");
	QsName = ReleaseUtil.randomStringGen("multipost-");
	assessmentTitle=ReleaseUtil.randomStringGen("multipost_assess_");
	QuestionUtil.CreateQSFolder(testfolder);
	QuestionUtil.create_ApproveQuestion(testfolder, "TF", QsName);
	//Create and Save Assessment in Draft mode
	AssessmentUtil.navigateToCreateNewQuestionAssessmentPage();
	AssessmentUtil.doNewAssessmentWithTitle(assessmentTitle);
	QuestionUtil.addQuestionToAssessment(QsName);
	AssessmentUtil.doSaveCreatedAssessment();

	//Search and Post Assessment
	AssessmentUtil.doSearchAssessmentInfolderWithTitle(assessmentTitle);
	AssessmentUtil.doEditAssessment();
	AssessmentUtil.doPostassessment();
	waitInSeconds(5);
	AssessmentUtil.doPostassessment();
	
	List<WebElement> sizeofgrid = driver.findElements(By.xpath("//*[@id='examPostingsTable']/tbody/tr"));
	int results=sizeofgrid.size();
	if(results>1){
		APP_LOGS.debug("Multipost is successful!");
	}else
		Assert.fail("Multipost is unsuccessful");
	}


@AfterMethod 
public void DefaultResult(ITestResult result) throws Exception {
	AssessmentUtil.deleteAssessment(assessmentTitle);//cleanup
	QuestionUtil.DeleteQSInFolder(testfolder);//cleanup
	QuestionUtil.DeleteQSFolder(testfolder);//cleanup

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