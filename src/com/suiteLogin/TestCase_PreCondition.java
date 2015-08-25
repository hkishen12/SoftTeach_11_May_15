package com.suiteLogin;

import java.util.List;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.CategoryUtil;
import Util.QuestionUtil;
import Util.TestUtil;

public class TestCase_PreCondition extends TestBase
{
	
// Runmode of test case in a suite
@BeforeTest
public void checkTestSkip() throws Exception
{	 
  	TestUtil.checkTestSkip("LoginSuite","TestCase_PreCondition");
}	 

@Test  
public void TestCaseCheckForQuestionsFolderName() throws Exception 
	{
	List<String> folderlist=QuestionUtil.CheckForFolderExist();
	int Count=0;
	String FolderNames = null;
	for(String FolderName:folderlist){
		System.out.println("|"+FolderName.trim()+"|"+CONFIG.getProperty("QS_CreateQSFolderName")+"|");
		if(FolderName.equals("/QUESTIONS/"+CONFIG.getProperty("QS_CreateQSFolderName"))){
			FolderNames=FolderNames+":"+"/ITEMS/"+CONFIG.getProperty("QS_CreateQSFolderName");
	       Count++;
		}
		if(FolderName.equals("/QUESTIONS/"+CONFIG.getProperty("QS_CreateQSFolderNameDel"))){
			FolderNames=FolderNames+":"+"/ITEMS/"+CONFIG.getProperty("QS_CreateQSFolderNameDel");
			Count++;
		}
		if(FolderName.equals("/QUESTIONS/"+CONFIG.getProperty("QS_CreateQSFolderNameForEdited"))){
			FolderNames=FolderNames+":"+"/ITEMS/"+CONFIG.getProperty("QS_CreateQSFolderNameForEdited");
			Count++;
		}
		if(FolderName.equals("/QUESTIONS/"+CONFIG.getProperty("QS_CreateQSFolderNameForEdit"))){
			FolderNames=FolderNames+":"+"/ITEMS/"+CONFIG.getProperty("QS_CreateQSFolderNameForEdit");
			Count++;
		}
		
		
	}
	if (Count>0){
		Assert.fail("Folders Are Present With Name: So either remove the folders Manually or Changs the Automations Folders name in Data files before Executions of the Scripts"+FolderNames);
		
	}
	}
@Test  
public void TestCaseCheckForCategoryName() throws Exception 
	{
	int Count1=0;
	String CatName = null;
	//Cleaning Category Section
	List<String> catlist=CategoryUtil.CheckForCategoryExist();
	for(String ct:catlist){	
		
		ct.trim();
		System.out.println("|"+ct.trim()+"|"+Data.getProperty("CAT_Name.1"));
	if(ct.equals(Data.getProperty("CAT_Name.1"))){
		CatName=CatName+":"+Data.getProperty("CAT_Name.1");
		Count1++;	
	 }
	if(ct.equals(Data.getProperty("DefaultCatName"))){
		CatName=CatName+":"+Data.getProperty("DefaultCatName");
		Count1++;	
	 }
	}
	if (Count1>0){
		Assert.fail("Categories Are Present With Name: So either remove the Categories Manually or Changs the Automations Categories name in Data files before Executions of the Scripts"+CatName);
		
	}
	
	 }
@Test
public void TestCaseCheckForColumnsNameAdd() throws Exception 
{
	
	QuestionUtil.CheckforColumnNameAdd();

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
