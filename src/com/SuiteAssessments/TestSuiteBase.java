package com.SuiteAssessments;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import Base.TestBase;
import Util.AssessmentUtil;
import Util.TestUtil;

public class TestSuiteBase extends TestBase{
	@BeforeSuite
	public void checkSuiteSkip() throws Exception{
		TestUtil.checkSuiteSkip("AssessmentsSuite");
		AssessmentUtil.navigateToCreateNewQuestionAssessmentPage();
		AssessmentUtil.doNewAssessment(CONFIG.getProperty("ATitle_string"));
		AssessmentUtil.doNewAssessmentAddQuestions();
		AssessmentUtil.doSaveCreatedAssessment();
		AssessmentUtil.doPostassessment();
	}

	@AfterSuite
	public void cleanUp() throws Exception{
				AssessmentUtil.deleteAssessment(CONFIG.getProperty("ATitle_string"));
				AssessmentUtil.deleteAssessment(CONFIG.getProperty("ATitle_string"));
				AssessmentUtil.deleteAssessment(CONFIG.getProperty("ATitle_string"));
		}

}
