package com.SuiteReports;

import java.io.File;

import org.testng.annotations.BeforeSuite;

import Base.TestBase;
import Util.TestUtil;

public class TestSuiteBase extends TestBase{
	@BeforeSuite
	public void checkSuiteSkip() throws Exception{
		TestUtil.checkSuiteSkip("ReportsSuite");
	}

}
