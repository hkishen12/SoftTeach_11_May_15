package com.suiteRubrics;


import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.RubricsUtil;
import Util.TestUtil;

public class TestCase_SaveTemplateAsRubric extends TestBase
{
	public static String rubricTitle="";
	public static String rubricDescription="";

	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("RubricsSuite","TestCase_SaveTemplateAsRubric");
	}	 


	@Test
	public void VerifySaveTemplateAsRubric() throws Exception
	{
		if(Data.getProperty("rubricTemplateName").equals("")){
			rubricTitle=randomStringGen("rubricTitle_");
			rubricDescription=randomStringGen("rubricDescription_");
			RubricsUtil.navigateToRubricCreationPage();
			RubricsUtil.enterRubricTitleAndDescription(rubricTitle, rubricDescription);
			RubricsUtil.changeRubricFolder(Data.getProperty("RubricsFolderName"));
			RubricsUtil.verifyRubricFolderName(Data.getProperty("RubricsFolderName"));
			RubricsUtil.selectRubricDimension(2, 2);
			RubricsUtil.typeOfScoring("Points");
			RubricsUtil.clickOnCreateButton();
			RubricsUtil.enterRubricRowsAndColumnsText("row","col");
			RubricsUtil.enterPoints(100);
			RubricsUtil.clickOnRubricSaveAsTemplateButton();
			String templateTitle=rubricTitle+"_template";
			RubricsUtil.enterDetailsOnSaveAsTemplatePrompt(templateTitle);
			RubricsUtil.verifyRubricSaveMessage(Expected_string.getProperty("templateConfirmationMessage"));
		}else{
			RubricsUtil.ClickOnRubricsTab();
			RubricsUtil.openRubricFolder(Data.getProperty("RubricsFolderName"));
			RubricsUtil.openRubricOrTemplate(Data.getProperty("rubricTemplateName"));
		}
		RubricsUtil.clickOnSaveAsARubricButton();
		RubricsUtil.enterDetailsOnSaveAsARubricPrompt("TemplateSavedAsARubric");
		RubricsUtil.verifyRubricSaveMessage(Expected_string.getProperty("rubricConfirmationMessage"));

		//set this value in data config file, it will be used save template as rubric in future test cases
		Data.setProperty("rubricTemplateName", "");
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