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

public class TestCase_EI3112 extends TestBase
{
	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("MarchReleaseSuite","TestCase_EI3112");
	}	 


	@Test
	public void verifyEI3112Test() throws InterruptedException 
	{
		System.out.println("Testing EI3112 sub:Edit Post : For performance assessments , 'Edit posting' and 'Export Grade' popup titles are same");
		AssessmentUtil.ClickOnAssessmentsTab();
		//adding required column if not present
		AssessmentUtil.addColumn("Type");
		AssessmentUtil.addColumn("Export Grades");
		AssessmentUtil.addColumn("Post");
		elementExists("//*[@id='examsTable']/thead/tr//span[text()='Status']").click();
		WaitForProgressBar();
		elementExists("//*[@id='examsTable']/thead/tr//span[text()='Status']").click();//click on Status column twice to sort exam posted types
		WaitForProgressBar();
		Thread.sleep(2000);

		for(int i=1; ;i++){
			if(elementExists("//*[@id='examsTable']//tr["+i+"]//img[@src='/STW-war/Icons/icons_softest.png']")!=null &&
					elementExists("//*[@id='examsTable']//tr["+i+"]//a[text()='Export Grades']")!=null &&
					elementExists("//*[@id='examsTable']//tr["+i+"]//a[@title='Edit this posting']")!=null){
				elementExists("//*[@id='examsTable']//tr["+i+"]//a[@title='Edit this posting']").click();//click on edit post button
				WaitForProgressBar();
				System.out.println("Clicked on edit post icon");
				Assert.assertEquals(elementExists("//*[@id='ui-id-9']").getText().trim(), "Edit Posting","Comparing edit post popup titles!");
				elementExists("//*[@id='ui-id-9']").sendKeys(Keys.ESCAPE);//close popup
				elementExists("//*[@id='examsTable']//tr["+i+"]//a[text()='Export Grades']").click();//click on export grades button
				WaitForProgressBar();
				System.out.println("Clicked on export grades button");
				Assert.assertEquals(elementExists("//*[@id='ui-id-15']").getText().trim(), "Export Grades","Comparing Export Grades popup titles!");
				elementExists("//*[@id='ui-id-15']").sendKeys(Keys.ESCAPE);//close popup
				System.out.println("Edit Post : For performance assessments , 'Edit posting' and 'Export Grade' popup titles are different and correct");
				break;
			}
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