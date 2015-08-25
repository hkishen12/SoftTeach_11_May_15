package com.suiteRubrics;


import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.RubricsUtil;
import Util.TestUtil;

public class TestCase_CreatePointBasedRubric extends TestBase
{
	public static String rubricTitle="";
	public static String rubricDescription="";

	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("RubricsSuite","TestCase_CreatePointBasedRubric");
	}	 


	@Test
	public void VerifyCreatePointBasedRubric() throws Exception
	{
		rubricTitle=randomStringGen("rubricTitle_");
		rubricDescription=randomStringGen("rubricDescription_");
		RubricsUtil.navigateToRubricCreationPage();
		RubricsUtil.enterRubricTitleAndDescription(rubricTitle, rubricDescription);
		RubricsUtil.changeRubricFolder(Data.getProperty("RubricsFolderName"));
		RubricsUtil.verifyRubricFolderName(Data.getProperty("RubricsFolderName"));
		RubricsUtil.selectRubricDimension(2,2);
		RubricsUtil.typeOfScoring("Points");
		RubricsUtil.clickOnCreateButton();
		RubricsUtil.enterRubricRowsAndColumnsText("row","col");
		RubricsUtil.enterPoints(100);
		String xpath=OR.getProperty("categoryXpath");
		RubricsUtil.selectRubricCategory(xpath);
		RubricsUtil.clickOnRubricSaveButton();
		RubricsUtil.verifyRubricSaveMessage(Expected_string.getProperty("rubricConfirmationMessage"));
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