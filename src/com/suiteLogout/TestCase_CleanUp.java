package com.suiteLogout;


import java.util.List;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.CategoryUtil;
import Util.QuestionUtil;
import Util.TestUtil;

public class TestCase_CleanUp extends TestBase
{
	
// Runmode of test case in a suite
@BeforeTest
public void checkTestSkip() throws Exception
{	 
	TestUtil.checkTestSkip("LogoutSuite","TestCase_CleanUp");
}	 
	 

@Test
public void CleanUpTest() throws Exception
{
	//Cleaning Question Section
	List<String> folderlist=QuestionUtil.CheckForFolderExist();
	
	for(String FolderName:folderlist){
		System.out.println("|"+FolderName.trim()+"|"+CONFIG.getProperty("QS_CreateQSFolderName")+"|");
		if(FolderName.equals("/QUESTIONS/"+CONFIG.getProperty("QS_CreateQSFolderName"))){
			QuestionUtil.CleanUp(CONFIG.getProperty("QS_CreateQSFolderName"));
			QuestionUtil.DeleteQSFolder(CONFIG.getProperty("QS_CreateQSFolderName"));
		}
		if(FolderName.equals("/QUESTIONS/"+CONFIG.getProperty("QS_CreateQSFolderNameDel"))){
			QuestionUtil.DeleteQSFolder(CONFIG.getProperty("QS_CreateQSFolderNameDel"));
		}
		if(FolderName.equals("/QUESTIONS/"+CONFIG.getProperty("QS_CreateQSFolderNameForEdited"))){
			QuestionUtil.DeleteQSFolder(CONFIG.getProperty("QS_CreateQSFolderNameForEdited"));
		}
		if(FolderName.equals("/QUESTIONS/"+CONFIG.getProperty("QS_CreateQSFolderNameForEdit"))){
			QuestionUtil.DeleteQSFolder(CONFIG.getProperty("QS_CreateQSFolderNameForEdit"));
		}
		
		
	}
	//Cleaning Category Section
	List<String> catlist=CategoryUtil.CheckForCategoryExist();
	for(String ct:catlist){	
		
		ct.trim();
		System.out.println("|"+ct.trim()+"|"+Data.getProperty("CAT_Name.1"));
	if(ct.equals(Data.getProperty("CAT_Name.1"))){
		CategoryUtil.ClickOnCategoryTab();
		QuestionUtil.WaitForProgressBar();
		APP_LOGS.debug("Clicked on the Category_tab");
		CategoryUtil.DeleteCategory(Data.getProperty("CAT_Name.1"));
	 }
	if(ct.equals(Data.getProperty("DefaultCatName"))){
		CategoryUtil.ClickOnCategoryTab();
		QuestionUtil.WaitForProgressBar();
		APP_LOGS.debug("Clicked on the Category_tab");
		CategoryUtil.DeleteCategory(Data.getProperty("DefaultCatName"));
	 }
	}
	
	//Cleaning Assessment Section
		
		for(String FolderName:folderlist){
			System.out.println("|"+FolderName.trim()+"|"+CONFIG.getProperty("QS_CreateQSFolderName")+"|");
			if(FolderName.equals("/EXAMS/"+Data.getProperty("AssessmentFolderName_string"))){
				//QuestionUtil.CleanUp(Data.getProperty("AssessmentFolderName_string"));
				//QuestionUtil.DeleteQSFolder(Data.getProperty("AssessmentFolderName_string"));
				System.out.println("found match folder in assessment");
					}
			if(FolderName.equals("/EXAMS/"+Data.getProperty("assessmentfolderedit_string"))){
				//QuestionUtil.CleanUp(Data.getProperty("assessmentfolderedit_string"));
				//QuestionUtil.DeleteQSFolder(Data.getProperty("assessmentfolderedit_string"));
				System.out.println("found match folder in assessment");
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