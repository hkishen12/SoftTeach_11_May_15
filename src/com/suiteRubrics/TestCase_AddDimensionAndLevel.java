package com.suiteRubrics;


import org.junit.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.RubricsUtil;
import Util.TestUtil;

public class TestCase_AddDimensionAndLevel extends TestBase
{
	public static String rubricTitle="";
	public static String rubricDescription="";

	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("RubricsSuite","TestCase_AddDimensionAndLevel");
	}	 


	@Test
	public void VerifyAddDimensionAndLevel() throws Exception
	{
		rubricTitle=randomStringGen("rubricTitle_");
		rubricDescription=randomStringGen("rubricDescription_");
		RubricsUtil.navigateToRubricCreationPage();
		RubricsUtil.enterRubricTitleAndDescription(rubricTitle, rubricDescription);
		RubricsUtil.changeRubricFolder(Data.getProperty("RubricsFolderName"));
		RubricsUtil.verifyRubricFolderName(Data.getProperty("RubricsFolderName"));
		RubricsUtil.selectRubricDimension(2,2);
		RubricsUtil.clickOnCreateButton();
		RubricsUtil.addDimension(1);
		RubricsUtil.addLevel(1);
		//check row addition
		if(elementExists("//*[@name='rows[2].cells[0].text']")!=null){
			APP_LOGS.debug("Rubric row added succesfully!");
			
			//check column addition
			if(elementExists("//*[@id='cell_text_0_2']")!=null)
				APP_LOGS.debug("Rubric column added succesfully!");
			else
				Assert.fail("Error in adding rubric level!");

		}else{
			Assert.fail("Error in adding rubric dimension!");
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