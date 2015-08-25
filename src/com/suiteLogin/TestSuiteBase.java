package com.suiteLogin;

import org.testng.annotations.BeforeSuite;

import Base.TestBase;
import Util.TestUtil;

public class TestSuiteBase extends TestBase
{
@BeforeSuite

public void checkSuiteSkip() throws Exception
{	
	initialize();
	System.out.println("******************************* EXECUTION ENVIRONMENT DETAILS *******************************");
	System.out.println("Environment = "+ CONFIG_ENV.getProperty("Environment"));
	System.out.println(CONFIG_ENV.getProperty("Environment") +" URL = "+ CONFIG.getProperty("baseUrl"));
	System.out.println("Runmode file type = "+CONFIG_ENV.getProperty("runmode_fileType") +" file.");
	System.out.println("*********************************************************************************************");                  
	TestUtil.checkSuiteSkip("LoginSuite");
}
   
}
