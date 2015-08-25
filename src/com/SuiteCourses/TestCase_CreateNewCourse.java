package com.SuiteCourses;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Util.CourseUtil;
import Util.TestUtil;

public class TestCase_CreateNewCourse extends TestSuiteBase {

	public String CourseID="";
	public String CourseName="";
	public String CourseMasName="";

	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("CoursesSuite","TestCase_CreateNewCourse");
	}	 

	@Test
	public void CreateNewCourse() throws Exception
	{		
		CourseID = randomStringGen("Auto15_Course_ID");
		CourseName = randomStringGen("Auto15_Course_Name");
		CourseMasName = randomStringGen("Auto15_Course_MasterName");

		CourseUtil.clickOnCourseTab();
		CourseUtil.createNewCourse(CourseID, CourseName ,CourseMasName);
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
