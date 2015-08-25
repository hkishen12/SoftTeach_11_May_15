package com.suiteRubrics;


import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.RubricsUtil;
import Util.TestUtil;

public class TestCase_EditRubric extends TestBase
{
	public static String rubricTitle="";
	public static String rubricDescription="";

	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("RubricsSuite","TestCase_EditRubric");
	}	 


	@Test
	public void VerifyEditRubric() throws Exception
	{
		//create point based rubric
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

		//edit rubric title and description
		RubricsUtil.enterRubricTitleAndDescription(rubricTitle+"_Edited", rubricDescription+"_Edited");
		//edit rubric folder
		RubricsUtil.changeRubricFolder("RUBRICS");
		//edit rubric rows and column text
		RubricsUtil.enterRubricRowsAndColumnsText("editedRow","editedCol");
		//edit rubric points
		RubricsUtil.enterPoints(90);
		//edit rubric category
		RubricsUtil.removeRubricCategory();
		String xpathEdited=OR.getProperty("editedCategoryXpath");
		RubricsUtil.selectRubricCategory(xpathEdited);

		RubricsUtil.clickOnRubricSaveButton();

		RubricsUtil.verifyRubricSaveMessage(Expected_string.getProperty("rubricConfirmationMessage"));
		RubricsUtil.verifyRubricFolderName("RUBRICS");
		RubricsUtil.verifyRubricRowsAndColumnsText("editedRow", "editedCol");
		RubricsUtil.verifyPoints(90);
		if(!elementExists(OR.getProperty("rubricCategoryLink")).getText().equals("CATEGORIES"))
			APP_LOGS.debug("Verified category edited successfully!");
		else
			Assert.fail("Error in category change!");
		RubricsUtil.changeRubricFolder(Data.getProperty("RubricsFolderName"));
		APP_LOGS.debug("Rubric edited successfully!");
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