package com.SuiteCourses;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Util.CourseUtil;
import Util.TestUtil;

public class TestCase_CourseExport extends TestSuiteBase {

	public String CourseID="";
	public String CourseName="";
	public String CourseMasName="";

	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("CoursesSuite","TestCase_CourseExport");
	}	 

	@Test
	public void CourseExport() throws Exception
	{		
		CourseID = randomStringGen("Course_ID_");
		CourseName = randomStringGen("Course_Name_");
		CourseMasName = randomStringGen("Course_MasterName_");
		driver.navigate().refresh();
		CourseUtil.clickOnCourseTab();
		CourseUtil.createNewCourse(CourseID, CourseName ,CourseMasName);
		CourseUtil.clickOnCourseTab();
		CourseUtil.searchCoursebyID(CourseID);
		CourseUtil.courseClickonExport();
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
