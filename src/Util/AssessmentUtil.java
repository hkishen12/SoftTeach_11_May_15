package Util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Base.TestBase;

public class AssessmentUtil extends TestBase
{
	public static WebDriverWait wait = new WebDriverWait(driver,9000);
	public static String folderMain=".//span[@title='/EXAMS/";
	public static String foldersub="']/..//li/img";
	public static boolean flag=false;

	public static String checkRubricsState() {
		String flag="n";
		getObjectxpath("Rubrics_button").click();
		WaitForProgressBar();
		try {
			if ((driver.findElement(By.xpath("//div[@class='headline']")).getText()).trim().equalsIgnoreCase("Rubrics")){
				flag="y";
				System.out.println("Rubrics state is:"+flag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static void removeAttachedRubric() {
		//remove first rubric in list
		if(elementExists("//*[@id='rubricsExamDraftTable']//tr[1]//img[@src='/STW-war/Icons/delete.png']")!=null){
			elementExists("//*[@id='rubricsExamDraftTable']//tr[1]//img[@src='/STW-war/Icons/delete.png']").click();
			APP_LOGS.debug("Clicked on remove rubric button");
			WaitForProgressBar();
		}
		else
			Assert.fail("No rubric is attached to this performance rubric!");
	}

	public static void addColumn(String ColumnName) throws InterruptedException {
		System.out.println("-----Adding column - "+ColumnName+" through cog wheel-----");
		if(elementExists("//*[@id='examsTable']/thead/tr").getText().contains(ColumnName)){// if column is already added
			System.out.println("Column '"+ColumnName+"' is already present!");
			return;
		}
		if(elementExists("//*[@id='cog_examsTable']").isDisplayed()){//if cog wheel is visible then click it else ignore
			elementExists("//*[@id='cog_examsTable']").click();
		}
		Thread.sleep(1000);
		Select addColumn=new Select(elementExists("//*[@id='addCol_examsTable']"));
		try{
			addColumn.selectByVisibleText(ColumnName+" ");// markup has column name+space
		}catch(Exception e){
			Assert.fail("Invalid column Name!");//if wrong column name is passed.
		}
		WaitForProgressBar();
	}



	public static void ClickOnAssessmentsTab() throws InterruptedException{
		if(isAlertPresent())
			driver.switchTo().alert().accept();
		driver.navigate().refresh();
		waitInSeconds(2);
		/*WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='navbar']/ul[3]/li/a")));*/

		driver.findElement(By.xpath("//*[@id='navbar']/ul[3]/li/a")).click();
		if(isAlertPresent())
			driver.switchTo().alert().accept();
		waitInSeconds(1);
		QuestionUtil.WaitForProgressBar();
		Thread.sleep(5000);
	}


	public static void performanceAssessmentwithTitle_FolderName(String assessmentTitle, String folderName){
		elementExists("//*[@id='assessmentTitle']").sendKeys(assessmentTitle);
		APP_LOGS.debug("Entered Assessment title "+assessmentTitle);
		elementExists("//*[@id='sidebarcontent']//a[contains(text(),'Select Folder')]").click();
		APP_LOGS.debug("Clicked on Select Folder");
		waitInSeconds(2);
		APP_LOGS.debug("Pop up window "+elementExists("//*[@id='ui-id-9']").getText()+" appeared");
		elementExists("//span[@title='/EXAMS/"+folderName+"']").click();
		APP_LOGS.debug("Clicked on folder "+folderName);
		WaitForProgressBar();
	}

	public static void doNewAssessment() throws Exception
	{	
		Thread.sleep(3000);
		String rubrics = checkRubricsState();

		AssessmentUtil.ClickOnAssessmentsTab();
		System.out.println("Assessment Tab Clicked");
		getObjectxpath("CreateNewAssessment_xpath").click();
		System.out.println("Create New Assessment Button Clicked");
		if (rubrics.equalsIgnoreCase("y")) {
			getObjectxpath("Newpopup_xpath").click();
			QuestionUtil.WaitForProgressBar();

			if (driver instanceof JavascriptExecutor) {
				((JavascriptExecutor) driver)
				.executeScript("window.onbeforeunload = null;");
			}
		}
		if (driver instanceof JavascriptExecutor) {
			((JavascriptExecutor) driver)
			.executeScript("window.onbeforeunload = null;");
		}


	}	

	public static void navigateToCreateNewPerformanceAssessmentPage() throws Exception
	{	
		AssessmentUtil.ClickOnAssessmentsTab();
		APP_LOGS.debug("Clicked on Assessment Tab!");
		elementExists(OR.getProperty("CreateNewAssessment_xpath")).click();
		APP_LOGS.debug("Create New Assessment Button Clicked!");
		Thread.sleep(1000);
		elementExists("//a[contains(@onclick,'createPerformanceExam')]").click();
		APP_LOGS.debug("Clicked on Performance Grading assessment type!");
		WaitForProgressBar();
		//check whether 'Create New Assessment' page is displayed or not
		if(elementExists("//div[@class='headline']").getText().trim().equalsIgnoreCase("Create New Assessment"))
			APP_LOGS.debug("'Create New Assessment' page is displayed!");
		else
			Assert.assertEquals(elementExists("//div[@class='headline']").getText().trim(), "Create New Assessment");
	}		
	public static void navigateToCreateNewQuestionAssessmentPage() throws Exception
	{	
		AssessmentUtil.ClickOnAssessmentsTab();
		APP_LOGS.debug("Clicked on Assessment Tab!");
		elementExists(OR.getProperty("CreateNewAssessment_xpath")).click();
		APP_LOGS.debug("Create New Assessment Button Clicked!");
		Thread.sleep(1000);
		elementExists("//*[@id='advanced']/a[text()='From Question']").click();
		APP_LOGS.debug("Clicked on From Question Bank assessment type!");
		WaitForProgressBar();
		//check whether 'Create New Assessment' page is displayed or not
		if(elementExists("//div[@class='headline']").getText().trim().equalsIgnoreCase("Create New Assessment"))
			APP_LOGS.debug("'Create New Assessment' page is displayed!");
		else
			Assert.assertEquals(elementExists("//div[@class='headline']").getText().trim(), "Create New Assessment");
	}		

	public static void doNewAssessment2() throws Exception
	{
		Thread.sleep(3000);
		getObjectxpath("Atitle_xpath").clear();
		getObjectxpathInput("Atitle_xpath", "ATitle_string");
		System.out.println("Assessment Title Entered");	

		Thread.sleep(3000);
		getObjectxpath("Folderclick_xpath").click();
		System.out.println("Folder Selected ");

		Thread.sleep(3000);
		getObjectxpath("Examfolder_xpath").click();
		System.out.println("Exam Folder Selected");
	}

	public static void doNewAssessment(String AssessmentTitle) throws Exception
	{
		Thread.sleep(2000);
		getObjectxpath("Atitle_xpath").clear();
		try{
			driver.findElement(By.xpath(OR.getProperty("Atitle_xpath"))).sendKeys(AssessmentTitle);
		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" +"Atitle_xpath" );
		}
		System.out.println("Assessment Title Entered");	

		Thread.sleep(3000);
		getObjectxpath("Folderclick_xpath").click();
		System.out.println("Folder Selected ");

		Thread.sleep(3000);
		getObjectxpath("Examfolder_xpath").click();
		System.out.println("Exam Folder Selected");
	}	
	public static void doNewAssessmentWithTitle(String title) throws Exception
	{
		Thread.sleep(3000);
		getObjectxpath("Atitle_xpath").clear();
		try{
			getObjectxpath("Atitle_xpath").sendKeys(title);
		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" + OR.getProperty("Atitle_xpath") );
		}
		System.out.println("Assessment Title Entered");	

		Thread.sleep(3000);
		getObjectxpath("Folderclick_xpath").click();
		System.out.println("Folder Selected");

		Thread.sleep(3000);
		getObjectxpath("Examfolder_xpath").click();
		System.out.println("Exam Folder Selected");
	}

	public static void doNewAssessmentCustomNotice() throws Exception
	{	
		getObjectxpath("AddCustomNotice_Xpath").click();
		System.out.println("Add Custom Notice Clicked");
		Thread.sleep(3000);
		getObjectxpathInput("CustomNoticeTemplateName_xpath", "CustomNoticetitle_string");
		System.out.println("Custom Notice Template Clicked");

		//go to iframe i.e. create notice content text field
		driver.switchTo().frame(driver.findElement(By.xpath("//*[@id='cke_contents_noticeRichText']/iframe")));
		((JavascriptExecutor) driver).executeScript("document.body.innerHTML = '<p><br></p>'");
		WebElement we = driver.findElement(By.tagName("p"));
		Thread.sleep(4000);
		we.sendKeys("Welcome");
		Thread.sleep(4000);
		driver.switchTo().defaultContent();//coming back to parent window
		getObjectxpath("CustomNoticecreatebutton_xpath").click();
		System.out.println("Clicked on Create Notice Button");
		QuestionUtil.WaitForProgressBar();
		Thread.sleep(3000);

		//Assertion 
		WebElement text = getObjectxpath("preExamNoticesTable_xpath");
		String enteredtext = text.getText();
		System.out.println("enteredtext"+enteredtext);
		comparestringtext(enteredtext,"CustomNoticetext_string");
		System.out.println(" =============Custom Notice Added Successfully======================");
		Thread.sleep(3000);

	}

	public static void doSearchAssessmentInfolderWithTitle(String title) throws Exception
	{			
		Thread.sleep(3000);
		AssessmentUtil.ClickOnAssessmentsTab();
		System.out.println("Assessment link clicked");
		driver.findElement(By.xpath(OR.getProperty("searchbox_xpath"))).sendKeys(title);
		getObjectxpath("allPostingDropbox").sendKeys("All Postings");
		System.out.println("String entered in the Search box");
		Thread.sleep(3000);
		getObjectxpath("searchbutton_xpath").click();
		System.out.println("Search button clicked");
		WaitForProgressBar();
	}



	public static void doNewAssessmentNoticeTemplate() throws Exception
	{	


		Thread.sleep(3000);
		//getObjectxpathInput("SelectTemplate_xpath", "TemplateNotice_string");
		//new Select(driver.findElement(By.id("examNotices"))).selectByVisibleText("UAT Test");
		if(CONFIG_ENV.getProperty("Environment").equals("qa"))
			new Select(driver.findElement(By.id("examNotices"))).selectByVisibleText(Data.getProperty("TemplateNotice_string"));
		if(CONFIG_ENV.getProperty("Environment").equals("uidemo"))
			new Select(driver.findElement(By.id("examNotices"))).selectByVisibleText(Data.getProperty("TemplateNotice_string1"));
		if(CONFIG_ENV.getProperty("Environment").equals("uidemodeux"))
			new Select(driver.findElement(By.id("examNotices"))).selectByVisibleText(Data.getProperty("TemplateNotice_string"));
		System.out.println("Selected Template Notice");


		Thread.sleep(3000);
		//getObjectxpath("SelectTemplate_xpath").sendKeys(Keys.RETURN);


		//Assertion 
		WebElement text = getObjectxpath("preExamNoticesTable_xpath");
		String enteredtext = text.getText();
		System.out.println("enteredtext: "+enteredtext);
		if(CONFIG_ENV.getProperty("Environment").equals("qa"))
			comparestringtext(enteredtext,"TemplateNoticetext_string");
		if(CONFIG_ENV.getProperty("Environment").equals("uidemo"))
			comparestringtext(enteredtext,"TemplateNoticetext_string1");
		if(CONFIG_ENV.getProperty("Environment").equals("uidemodeux"))
			comparestringtext(enteredtext,"TemplateNoticetext_string");
		System.out.println(" ============= Template Notice successfully added ======================");
		Thread.sleep(3000);


	}
	public static void doExportPrintforScantron() throws Exception
	{	
		/*
	String FolderNametoExport="ExportedExam";
	String FileNametoExport="Assessment_create";
	File zp = new File(System.getProperty("user.dir")+"\\src\\Download\\"+FolderNametoExport+".zip");
	File zp2= new File(System.getProperty("user.dir")+"\\src\\Download\\"+FileNametoExport+".doc");
	zp.deleteOnExit();
	zp2.deleteOnExit();
	Thread.sleep(2000l);
		 */

		Thread.sleep(3000); 
		getObjectxpath("ExportPrintbutton_xpath").click();
		System.out.println("Click on export print button");

		QuestionUtil.WaitForProgressBar();

		// Selecting Scantron Format option for Export/Print
		Thread.sleep(3000);

		Select formatDropdown= new Select(driver.findElement(By.id("selectedTestFormatValue")));
		formatDropdown.selectByVisibleText("Scantron Format");


		/*	wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id='selectedTestFormatValue']")))).click();

		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id='selectedTestFormatValue']")))).sendKeys(Keys.ARROW_DOWN);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id='selectedTestFormatValue']")))).sendKeys(Keys.RETURN);*/
		//wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id='selectedTestFormatValue']")))).sendKeys("Scantron Format");
		//driver.findElement(By.xpath("//*[@id='selectedTestFormatValue']")).sendKeys("Scantron Format");
		//wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("")("//*[@id='selectedTestFormatValue']/option[@option value=1]")))).click();
		//driver.findElement(By.xpath("//*[@id='selectedTestFormatValue']/option[@option value=1]")).sendKeys("Scantron Format");

		//Scantron Popup for FITB is Handled
		Thread.sleep(5000);
		driver.findElement(By.xpath("//*[@id='fitbScantronPopup']/div[3]/a[2]")).click();
		//driver.switchTo().alert().accept();
		System.out.println("Scantron Format is Selected in the Dropdown");

		//Scantron Version is Selected
		//Thread.sleep(3000);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id='selectedExportScantronVersion']")))).sendKeys("4 - A, B, C, D");
		System.out.println("Scantron Version is Selected");

		//Click on the Export Button
		//	Thread.sleep(3000); 
		getObjectxpath("printexportbutton_xpath").click();	
		System.out.println("Click on export and print button button");

		Thread.sleep(3000); 
		//driver.findElement(By.cssSelector("#basic-modal-examsPrintExport > div > a.button.btnDone")).click();
		driver.findElement(By.xpath("//*[@id='basic-modal-examsPrintExport']/div/a[1]")).click();
		System.out.println("Click on close button");

		System.out.println("Scantron Version are Generated for the Assessment");
	}

	public static void doNewAssessmentAddQuestions() throws Exception
	{	
		Thread.sleep(3000);
		getObjectxpath("AddQuestoAss_xpath").click();
		System.out.println("click Add Questions to Assessment button");

		Thread.sleep(3000);
		WebElement textquestion = getObjectxpath("firstrowtext_xpath");
		String text = textquestion.getText();
		System.out.println("Question text-->"+text);

		if(elementExists(OR.getProperty("firstquestion_xpath")).isEnabled())
			getObjectxpath("firstquestion_xpath").click();
		else
			getObjectxpath("secondquestion_xpath").click();

		System.out.println("Able to select first checkbox");


		Thread.sleep(3000);
		//QuestionUtil.WaitForProgressBar();


		getObjectxpath("AddSelectedqu_xpath").click();
		System.out.println("click Add Selected to Assessment button");
		QuestionUtil.WaitForProgressBar();	
		Thread.sleep(3000);

		//	getObjectxpath("firstquestionlink_xpath").click();
		//	QuestionUtil.WaitForProgressBar();	
		//	Thread.sleep(3000);


		getObjectxpath("closebuttonQA_xpath").click();
		System.out.println("click close button ");

		QuestionUtil.WaitForProgressBar();



		//Assertion 

		Thread.sleep(3000);
		Thread.sleep(3000);
		WebElement selectedquestion = getObjectxpath("Selectedrowtest_xpath");
		String selectedtext = selectedquestion.getText();
		System.out.println("Verified text -->"+selectedtext);

		Thread.sleep(3000);
		Assert.assertEquals(text,selectedtext);
		System.out.println(" ============= successfully Added question ======================");

	}

	public static void doNewAssessmentAddQuestionWithTitle(String questionTitle) throws Exception
	{	
		getObjectxpath("AddQuestoAss_xpath").click();
		System.out.println("click Add Questions to Assessment button");

		Thread.sleep(3000);
		elementExists("//*[@id='omniBasicSearchInput']").sendKeys(questionTitle);
		Thread.sleep(1000);
		elementExists("//*[@id='btnDoQuestionsBasicSearchExamEC']").click();
		WaitForProgressBar();

		WebElement textquestion = getObjectxpath("firstrowtext_xpath");
		String text = textquestion.getText();
		System.out.println("Question text-->"+text);


		getObjectxpath("firstquestion_xpath").click();
		System.out.println("Able to select first checkbox");

		Thread.sleep(3000);

		getObjectxpath("AddSelectedqu_xpath").click();
		System.out.println("click Add Selected to Assessment button ");
		QuestionUtil.WaitForProgressBar();	
		Thread.sleep(3000);

		getObjectxpath("closebuttonQA_xpath").click();
		System.out.println("click close button ");

		QuestionUtil.WaitForProgressBar();

		//Assertion 
		Thread.sleep(3000);
		Thread.sleep(3000);
		WebElement selectedquestion = getObjectxpath("Selectedrowtest_xpath");
		String selectedtext = selectedquestion.getText();
		System.out.println("Verified text -->"+selectedtext);
		Thread.sleep(3000);
		Assert.assertEquals(text,selectedtext);
		System.out.println(" ============= successfully Added question ======================");

	}


	public static void dodeleteAddQuestions() throws Exception
	{	
		Thread.sleep(3000);
		getObjectxpath("savebutton_xpath").click();
		System.out.println("Click on save button");	
	}

	public static void deleteAssessment(String assessmentTitle)
	{
		try{
			doSearchAssessmentInfolderWithTitle(assessmentTitle);
			APP_LOGS.debug("Assessment is searched");
			if(elementExists("//*[@id='examsTable']//tr/*[.='No matching records found']")!=null)//if no records searched then return
				return;
			waitInSeconds(2);
			if(elementExists("//*[@id='examsTable']//tr[1]//a[@title='Remove Assessment']")!=null){
				elementExists("//*[@id='examsTable']//tr[1]//a[@title='Remove Assessment']").click();
				waitInSeconds(2);
				APP_LOGS.debug("Clicked on Delete icon");
				elementExists("//*[@id='retireExamListingBtn']").click();
				APP_LOGS.debug("Assessment is deleted");
				WaitForProgressBar();
				waitInSeconds(2);
				return;
			}
			flag=false;
			if(!flag){
				elementExists("//*[@id='examsTable']//tr[1]//a[@title='Remove Posting']").click();
			}

			waitInSeconds(2);
			APP_LOGS.debug("Clicked on Delete icon");
			elementExists("//*[@id='retireExamListingBtn']").click();
			if(!flag){
				APP_LOGS.debug("Assessment is unposted");
				flag=true;
			}
			WaitForProgressBar();
			waitInSeconds(2);
			if(flag)
				deleteAssessment(assessmentTitle);

		}catch(Exception e){
			flag=false;//reset to original value
		}

	}


	public static void doCancelCreatedAssessment() throws Exception
	{	
		Thread.sleep(3000);
		getObjectxpath("Cancelassessment_xpath").click();
		System.out.println("Click on Cancel button");
		waitInSeconds(4);
	}

	public static void doSaveCreatedAssessment() throws Exception
	{	
		Thread.sleep(3000);
		elementExists(OR.getProperty("savebuttonc_xpath")).click();
		System.out.println("Save Button Clicked");	
		QuestionUtil.WaitForProgressBar();
	}
	public static void savePerformanceAssessment() throws Exception
	{	
		waitInSeconds(2);
		elementExists("//*[@id='savePerfAssessment']").click();
		APP_LOGS.debug("Save Button Clicked");	
		WaitForProgressBar();
	}
	public static void addRubricsToAssessment() throws Exception
	{	
		elementExists("//img[@title='Add Rubrics']").click();
		System.out.println("clickded on Add Rubrics button!");	
		waitInSeconds(2);
		elementExists("//span[@title='/RUBRICS']").click();
		WaitForProgressBar();
		for(int i=1;i<=5;i++){
			//if rubric is already added then skip it and try to add next rubric
			if(elementExists("//*[@id='rubricsTable']//tr["+i+"]//img[@src='/STW-war/images/Icons/add.png']")!=null){
				elementExists("//*[@id='rubricsTable']//tr["+i+"]//img[@src='/STW-war/images/Icons/add.png']").click();
				WaitForProgressBar();
				if(elementExists("//*[@id='rubricsTable']//tr["+i+"]//img[@src='/STW-war/images/Icons/delete.png']")!=null)
					APP_LOGS.debug("Added rubric");
				break;
			}
		}
		elementExists("//a[@onclick='doneAddingRubricsToExam()']").click();
		APP_LOGS.debug("Clicked on Close button!");
		WaitForProgressBar();
	}

	public static void doSearchAssessmentInfolder() throws Exception
	{			

		Thread.sleep(3000);
		getObjectxpath("Assessments_xpath").click();
		System.out.println("Assessment link clicked");
		QuestionUtil.WaitForProgressBar();
		Thread.sleep(3000);
		getObjectxpath("allPostingDropbox").sendKeys("All Postings");
		getObjectxpathInput("searchbox_xpath","ATitle_string");
		System.out.println("String entered in the Search box");


		Thread.sleep(3000);
		getObjectxpath("searchbutton_xpath").click();
		System.out.println("Search button clicked");



		QuestionUtil.WaitForProgressBar();

		//Assertion 
		Thread.sleep(4000);
		WebElement text = getObjectxpath("searchedassessment");
		System.out.println(text);
		String enteredtext = text.getText();
		System.out.println("enteredtext"+enteredtext);
		comparestringtext(enteredtext,"SearchAssert_string");
		System.out.println(" ============= Successfully Searched Assessment ======================");
		Thread.sleep(3000);

		QuestionUtil.WaitForProgressBar();

	}
	public static void doSearchAssessment(String AssessmentTitle) throws Exception
	{			
		AssessmentUtil.ClickOnAssessmentsTab();
		System.out.println("Assessment link clicked!");

		driver.findElement(By.xpath(OR.getProperty("searchbox_xpath"))).sendKeys(AssessmentTitle);
		waitInSeconds(2);
		getObjectxpath("allPostingDropbox").sendKeys("All Postings");
		System.out.println("String entered in the Search box");
		Thread.sleep(2000);
		getObjectxpath("searchbutton_xpath").click();
		System.out.println("Search button clicked");
		WaitForProgressBar();

		//Assertion 
		Thread.sleep(4000);
		String actualAssessTitle= elementExists("//*[@id='examsTable']//tr[1]/td[2]/a").getText().trim();
		if(actualAssessTitle.equalsIgnoreCase(AssessmentTitle))
			System.out.println(" ============= Successfully Searched Assessment ======================");
		else
			System.out.println("Assessment not found");
	}

	public static void doMostReceRevisedAssessment() throws Exception
	{
		elementExists(OR.getProperty("Assessments_xpath")).click();
		APP_LOGS.debug("Clicked on Assessment Tab!!");
		WaitForProgressBar();
		waitInSeconds(3);

		getObjectxpath("ExamsAdvancedSearch_xpath").click();
		APP_LOGS.debug("Advanced search option select");

		WaitForProgressBar();
		elementExists(OR.getProperty("AdvancedSearchbutton_xpath")).click();
		APP_LOGS.debug("Advanced search button clicked");
		WaitForProgressBar();
		waitInSeconds(6);

		elementExists("//*[@id='examsTable']//tr//span[text()='Assessment ID']").click();
		WaitForProgressBar();
		waitInSeconds(3);
		elementExists("//*[@id='examsTable']//tr//span[text()='Assessment ID']").click();
		WaitForProgressBar();
		waitInSeconds(3);
		elementExists("//*[@id='examsTable']//tr//span[text()='Status']").click();
		WaitForProgressBar();
		waitInSeconds(3);
		elementExists("//*[@id='examsTable']//tr//span[text()='Status']").click();
		WaitForProgressBar();
		waitInSeconds(3);
		if(elementExists(OR.getProperty("Firstassertion_xpath")).getText().equalsIgnoreCase(Expected_string.getProperty("SearchAssert_string")) ||
				elementExists(OR.getProperty("Secondassertion_xpath")).getText().equalsIgnoreCase(Expected_string.getProperty("SearchAssert_string"))) {
			Assert.assertTrue(true);
		}
		else
			Assert.assertTrue(false);

		//comparestringtext(elementExists(OR.getProperty("Firstassertion_xpath")).getText(),"SearchAssert_string");
		System.out.println("pass");
	}

	public static void doNewAssessmentsaveasDraft() throws Exception
	{	
		//Assertion 
		WebElement text = getObjectxpath("StatusCheck_xpath");
		String enteredtext = text.getText();
		System.out.println("entered text = "+enteredtext);
		comparestringtext(enteredtext,"AssessmentDraft_string");
		System.out.println(" ============= successfully save as draft question ======================");
		Thread.sleep(3000);


	}

	public static void doAssessmentdelete() throws Exception
	{

		Thread.sleep(3000); 
		getObjectxpath("deleteasseration_xpath").click();
		System.out.println("delete link click");

		Thread.sleep(3000); 
		getObjectxpath("deleteokbutton_xpath").click();
		System.out.println("delete link click");


	}


	public static void doAssessmentdelete(String assessmentTitle) throws Exception
	{
		AssessmentUtil.deleteAssessment(assessmentTitle);
		AssessmentUtil.dodeleteAssessmentfolder();
		driver.findElement(By.xpath("//*[@id='examsTable']//tr//a[@title='Remove Assessment']/img")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='retireExamListingBtn']")).click();
		QuestionUtil.WaitForProgressBar();
		if(driver.findElement(By.xpath(OR.getProperty("QS_ImportTabSeperated_ConfirmMsg"))).isDisplayed()){
			System.out.println("Assessment is deleted!!");
		}else
			Assert.fail("Assessment deletion failed!!");
	}




	public static void doEditAssessment() 
	{			
		waitInSeconds(3);
		int count=0;
		while(elementExists(OR.getProperty("Assessmentname_xpath"))==null){
			count++;
			waitInSeconds(1);
			if(count>120) break;
		}
		getObjectxpath("Assessmentname_xpath").click();
		System.out.println("Click on assessment name link ");
		QuestionUtil.WaitForProgressBar();
	}

	public static void docloseExportPrint() throws Exception
	{			


		Thread.sleep(3000); 
		getObjectxpath("ExportPrintbutton_xpath").click();
		System.out.println("Click on export print button");

		QuestionUtil.WaitForProgressBar();

		Thread.sleep(3000); 
		driver.findElement(By.cssSelector("#basic-modal-examsPrintExport > div > a.button.btnDone")).click();
		System.out.println("Click on close button");

	}


	public static void doExportPrint() throws Exception
	{			
		String FolderNametoExport="ExportedExam";
		String FileNametoExport="Assessment_create";
		File zp = new File(System.getProperty("user.dir")+"\\src\\Download\\"+FolderNametoExport+".zip");
		File zp2= new File(System.getProperty("user.dir")+"\\src\\Download\\"+FileNametoExport+".doc");
		zp.deleteOnExit();
		zp2.deleteOnExit();
		Thread.sleep(2000l);

		Thread.sleep(3000); 
		getObjectxpath("ExportPrintbutton_xpath").click();
		System.out.println("Click on export print button");

		QuestionUtil.WaitForProgressBar();

		Thread.sleep(3000); 
		getObjectxpath("printexportbutton_xpath").click();	
		System.out.println("Click on export and print button button");

		Thread.sleep(3000); 
		//driver.findElement(By.cssSelector("#basic-modal-examsPrintExport > div > a.button.btnDone")).click();
		System.out.println("Click on close button");

		int count=0;
		APP_LOGS.debug("Count"+count);
		while(true){
			if (zp.exists()){
				break;
			}
			if (count==50000){
				APP_LOGS.debug("Count"+count);
			}
			count++;
		}
		Thread.sleep(10000l);


		Thread.sleep(10000l);
		if (zp2.exists()){

			APP_LOGS.debug("File is Extracted on location");
		}
		else{
			Assert.fail("File is not Extracted");
		}
		Thread.sleep(10000l);

	}




	public static void doPostassessment() throws Exception
	{			
		/*Dimension d = new Dimension(1728, 930);// reduce window size for post assessment pop
		driver.manage().window().setSize(d);
		 */
		Thread.sleep(3000); 
		getObjectxpath("post_xpath").click();
		System.out.println("Click on post button");

		QuestionUtil.WaitForProgressBar();
		waitInSeconds(5);
		elementExists("//*[@id='examPassword']").sendKeys("test123");//enter assessment password
		System.out.println("Entered the password");
		waitInSeconds(1);

		Thread.sleep(3000);
		getObjectid("DownloadEnd_id").clear();
		getObjectidInput("DownloadEnd_id","DownloadEnd_string");
		System.out.println("End date Entered");

		Thread.sleep(3000); 
		Select selectCourse=new Select(elementExists("//*[@id='courseID']"));
		selectCourse.selectByVisibleText("Available to ALL Exam Takers");

		/*getObjectidInputSelect("course_id","courseid_string");*/
		System.out.println("Course Selected");


		waitInSeconds(1);
		if(elementExists("//*[@id='examReviewPassword']")!=null){
			if(elementExists("//*[@id='examReviewPassword']").isDisplayed()){
				elementExists("//*[@id='examReviewPassword']").sendKeys("test123");//enter review password
				waitInSeconds(1);
			}
		}



		/*Select reviewType=new Select(elementExists("//*[@id='secureExamReview']"));
		reviewType.selectByVisibleText("Immediately After Assessment");
		waitInSeconds(1);
		elementExists("//*[@id='examReviewPassword']").sendKeys("test");//enter review password
		waitInSeconds(1);*/

		Thread.sleep(3000);
		getObjectxpath("viewPostExam_xpath").click();
		System.out.println("Click on post button");
		Thread.sleep(3000);
		if(isAlertPresent())
			driver.switchTo().alert().accept();
		WaitForProgressBar();
		if(elementExists(OR.getProperty("confirmPostId"))!=null){
			if(elementExists(OR.getProperty("confirmPostId")).isDisplayed())
				elementExists(OR.getProperty("confirmPostId")).click();
		}

		Thread.sleep(1000);

		if(isAlertPresent())
			driver.switchTo().alert().accept();

		Thread.sleep(1000);

		System.out.println("Click on submit post Exam button");

		WaitForProgressBar();

	}

	public static void doPost2assessment() throws Exception
	{			

		Thread.sleep(3000); 
		getObjectxpath("post_xpath").click();
		System.out.println("Click on post button");


		QuestionUtil.WaitForProgressBar();
		elementExists("//*[@id='examPassword']").sendKeys("test");//enter assessment password
		waitInSeconds(1);

		Thread.sleep(3000);
		getObjectid("DownloadEnd_id").clear();
		getObjectidInput("DownloadEnd_id","DownloadEnd_string");
		System.out.println("End date Entered");

		Thread.sleep(3000); 
		getObjectidInputSelect("course_id","courseid_string");
		System.out.println("Course Selected");

		Select reviewType=new Select(elementExists("//*[@id='secureExamReview']"));
		reviewType.selectByVisibleText("Immediately After Assessment");
		waitInSeconds(1);
		elementExists("//*[@id='examReviewPassword']").sendKeys("test");//enter review password
		waitInSeconds(1);

		Thread.sleep(3000);
		getObjectxpath("viewPostExam_xpath").click();
		System.out.println("Click on post button");

		Thread.sleep(3000);
		driver.switchTo().alert().accept();


		System.out.println("Click on submit post Exam button");

		QuestionUtil.WaitForProgressBar();

	}

	public static void dorePostassessment() throws Exception
	{			

		driver.findElement(By.linkText("Post Assessment")).click();
		System.out.println("Click on post button");
		QuestionUtil.WaitForProgressBar();
		elementExists("//*[@id='examPassword']").sendKeys("test");//enter assessment password
		waitInSeconds(1);

		Thread.sleep(3000);
		getObjectid("DownloadEnd_id").clear();
		getObjectidInput("DownloadEnd_id","DownloadEnd_string");
		System.out.println("Enter end date");

		Thread.sleep(3000); 
		getObjectidInputSelect("course_id","courseid_string");
		System.out.println("select course");

		Select reviewType=new Select(elementExists("//*[@id='secureExamReview']"));
		reviewType.selectByVisibleText("Immediately After Assessment");
		waitInSeconds(1);
		elementExists("//*[@id='examReviewPassword']").sendKeys("test");//enter review password
		waitInSeconds(1);

		Thread.sleep(3000);
		getObjectxpath("viewPostExam_xpath").click();
		System.out.println("Click on post button");

		Thread.sleep(3000);
		driver.switchTo().alert().accept();


		System.out.println("Click on submit Print Export Exam button");

		QuestionUtil.WaitForProgressBar();

	}



	public static void doPostassessmentcheck() throws Exception
	{			

		Thread.sleep(3000);
		//Actions actions = new Actions(driver);
		//actions.sendKeys(Keys.BACK_SPACE).perform();

		JavascriptExecutor jsx = (JavascriptExecutor)driver;
		jsx.executeScript("window.scrollBy(0,650)", "");
		System.out.println("BACK_SPACE perfrom");
		//Assertion 
		Thread.sleep(3000);		
		WebElement text = getObjectxpath("firstpost_xpath");
		String enteredtext = text.getText();

		System.out.println("enteredtext"+enteredtext);

		comparestringtext(enteredtext,"firstpost_string");
		System.out.println(" ============= successfully Post assessment checked ======================");
		Thread.sleep(3000);


	}

	public static void doPostassessmentchecking() throws Exception
	{			

		WebElement text=null;
		for(int i=2;1<=10;i++){
			if(elementExists("//*[@id='examsTable']/thead/tr/th["+i+"]").getText().contains("Status")){
				text = elementExists("//*[@id='examsTable']/tbody/tr[1]/td["+i+"]");
				break;
			}
		}
		//Assertion 

		String enteredtext = text.getText();
		System.out.println("enteredtext::"+enteredtext);
		comparestringtext(enteredtext,"AssessmentPosted_string");

		System.out.println(" ============= successfully Post assessment checked ======================");
		Thread.sleep(3000);


	}


	public static void doUnpostposting() throws Exception
	{
		waitInSeconds(5); 
		if(elementExists("//table[@id='examPostingsTable']").isDisplayed()){
			if(elementExists(OR.getProperty("unpostimg_xpath")).isDisplayed()){
				getObjectxpath("unpostimg_xpath").click();
				System.out.println("Unpost image click ");	
				waitInSeconds(3); 
				getObjectxpath("unpostokbutton_xpath").click();
				System.out.println("Unpost ok button click ");
				WaitForProgressBar();
				System.out.println(elementExists("//*[@id='globalMessageDiv']").getText());
			}
		}

		Assert.assertTrue(elementExists("//*[@id='globalMessageDiv']").getText().contains(Expected_string.getProperty("unpostmessage_string1")));
		System.out.println(" ============= The Assessment Posting ID ======================");
		Thread.sleep(3000);
		Assert.assertTrue(elementExists("//*[@id='globalMessageDiv']").getText().contains(Expected_string.getProperty("unpostmessage_string2")));
		System.out.println(" ============= successfully save as removed  ======================");
		Thread.sleep(3000);
	}

	public static void doDuplicateposting() throws Exception
	{			
		Thread.sleep(3000); 
		getObjectlinkText("Duplicate_linktext").click();
		System.out.println("Duplicate button click ");	

		Thread.sleep(3000); 
		QuestionUtil.WaitForProgressBar();

		//Assertion 
		Thread.sleep(3000);		
		WebElement text = getObjectxpath("Atitle_xpath");
		String enteredtext = text.getAttribute("value");
		System.out.println("enteredtext::"+enteredtext);
		comparestringtext(enteredtext,"assessmentduplicate");
		System.out.println(" ============= successfully create duplicate assessment ======================");
		Thread.sleep(3000);

	}

	public static void doRetireposting(String assessmentTitle) throws Exception
	{	
		doSearchAssessmentInfolderWithTitle(assessmentTitle);
		elementExists("//*[@id='examsTable']/tbody/tr[1]/td[2]/a").click();//click on assessment title link
		WaitForProgressBar();
		Thread.sleep(3000); 
		getObjectxpath("retireExamOpen_xpath").click();
		System.out.println("retire Exam Open button click");	
		Thread.sleep(3000); 
		getObjectxpath("retireExamOpenok_xpath").click();
		System.out.println("retire Exam Open ok button click ");	
		QuestionUtil.WaitForProgressBar();
	}


	public static void doRetireposting() throws Exception
	{	
		Thread.sleep(3000); 
		getObjectxpath("retireExamOpen_xpath").click();
		System.out.println("retire Exam Open button click");	
		Thread.sleep(3000); 
		getObjectxpath("retireExamOpenok_xpath").click();
		System.out.println("retire Exam Open ok button click ");	
		QuestionUtil.WaitForProgressBar();
	}




	public static void doexamsPreviewOpen() throws Exception
	{			
		Thread.sleep(5000); 
		getObjectxpath("Preview_linktext").click();
		System.out.println("Click on preview button");
		WaitForProgressBar();
		waitInSeconds(2);
		if(elementExists(OR.getProperty("PreviewiPadConfirmation")).isDisplayed())
			getObjectxpath("PreviewiPadConfirmation").click();
		QuestionUtil.WaitForProgressBar();

		WebElement Tabname = getObjectxpath("previewDownloadlink");
		String string = Tabname.getText();
		comparestringtext(string,"PreviewLink_String");
		System.out.println(" ============= preview is get displayed successfully  ======================");

		Thread.sleep(3000); 
		getObjectxpath("Previewok_xpath").click();
		System.out.println("Preview ok button pressed");

	}

	public static void donewAssessmentfolder() throws Exception
	{
		Thread.sleep(3000);
		getObjectxpath("Assessments_xpath").click();
		System.out.println("Assessment click");

		QuestionUtil.WaitForProgressBar();

		Thread.sleep(3000);
		WebElement examCogWheel=elementExists("//*[@id='assessmentListingFolder']//td[contains(.,'EXAMS')]/..//img[contains(@src,'cog.png')]");
		WebElement newFolderLink=elementExists("//*[@id='assessmentListingFolder']//td[contains(.,'EXAMS')]/..//a[contains(text(),'New Folder')]");

		new Actions(driver).moveToElement(examCogWheel).moveToElement(newFolderLink).click().build().perform();


		Thread.sleep(3000);
		/*	WebElement qsfolder1= driver.findElement(By.xpath("//*[@id='assessmentListingFolder']//span/*[text()='EXAMS']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
				qsfolder1, "color: yellow; border: 2px solid yellow;");
		Locatable hoverItem = (Locatable) qsfolder1;
		Mouse mouse = ((HasInputDevices) driver).getMouse();
		mouse.mouseMove(hoverItem.getCoordinates());

		//getObjectxpath("QS_NewFolderOptionLink").click();
		String fd= qsfolder1.findElement(
				By.xpath("//a[text()='New Folder']")).getAttribute("onclick");

		String scriptName="createFolderOpen";
		fd=fd.replaceAll("\"", "'");
		String [] javascr=fd.split(scriptName);
		String kj=javascr[1];
		System.out.println("New Folder:"+js.executeScript(scriptName+kj));
		Thread.sleep(3000);*/
		getObjectxpath("NewFoldername_xpath").sendKeys(Data.getProperty("AssessmentFolderName_string"));
		Thread.sleep(3000);
		getObjectxpath("saveFolderbutton_xpath").click();
		WaitForProgressBar();
		Thread.sleep(3000);

		//Assertion 
		WebElement Message = getObjectxpath("FolderconfirMsg_xpath");
		String Message_string = Message.getText();
		comparestring(Message_string, "FolderCreationmess_string");
		System.out.println("============= Folder Created Succesfully ======================");
		APP_LOGS.debug("================= Folder Created Succesfully ======================");
		Thread.sleep(3000);
	}

	public static void createNewExamFolder(String folderName) throws Exception
	{
		APP_LOGS.debug("--------Exam Folder Creation start--------");
		ClickOnAssessmentsTab();
		WebElement rootFolderCogWheel = elementExists("//*[@id='examFolderTreeDiv']//span[@title='/EXAMS']/../span[2]");
		WebElement cogWheel_NewFolderLink= elementExists("//*[@id='examFolderTreeDiv']//span[@title='/EXAMS']/../span[2]//a[text()='New Folder']");
		//click on new folder link
		new Actions(driver).moveToElement(rootFolderCogWheel).moveToElement(cogWheel_NewFolderLink).click().build().perform();
		APP_LOGS.debug("Clicked on new folder link");
		APP_LOGS.debug("Popup window '"+elementExists(OR.getProperty("QS_CreateNewFolderText")).getText()+"' displayed");
		//Enter folder name
		elementExists(OR.getProperty("QS_CreateNewFolderTextArea")).sendKeys(folderName);
		APP_LOGS.debug("Entered folder title '"+folderName+"' in title text field");
		elementExists(OR.getProperty("QS_CreateNewFolderSubmit")).click();
		APP_LOGS.debug("Clicked on Submit button!!");
		if(elementExists("//*[@id='createResource']//span[@class='errorLabel']").isDisplayed())
			Assert.fail("Invalid folder title characters used!!");
		WaitForProgressBar();
		if(elementExists("//*[@id='globalMessageDiv']//*[contains(@class,'confirmationLabel')]")!=null){
			if(elementExists("//span[@title='/"+CONFIG_ENV.getProperty("examRootFolderName")+"/"+folderName+"']").isDisplayed()) 
				APP_LOGS.debug("Folder "+folderName+" created successfully!");
		}
		else
			Assert.fail("Error in Question folder creation!!");
		APP_LOGS.debug("--------Question Folder Creation end--------");
	}




	public static void donewAssessmentfolderold() throws Exception
	{
		FirefoxProfile prof = new FirefoxProfile();
		prof.setEnableNativeEvents(true);	

		Thread.sleep(3000);
		getObjectxpath("Assessments_xpath").click();
		System.out.println("Assessment click");

		QuestionUtil.WaitForProgressBar();

		Thread.sleep(3000);
		WebElement qsfolder1= driver.findElement(By.xpath("//span[@title='/EXAMS']/..//li/img"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
				qsfolder1, "color: yellow; border: 2px solid yellow;");
		Locatable hoverItem = (Locatable) qsfolder1;
		Mouse mouse = ((HasInputDevices) driver).getMouse();
		mouse.mouseMove(hoverItem.getCoordinates());


		String fd= qsfolder1.findElement(By.xpath("..//a[text()='Create Folder']")).getAttribute("onclick");


		String scriptName="createFolderOpen";
		fd=fd.replaceAll("\"", "'");
		String [] javascr=fd.split(scriptName);
		String kj=javascr[1];
		System.out.println("Delete Script"+js.executeScript(scriptName+kj));
		Thread.sleep(3000l);

		//Assertion 
		WebElement Message = getObjectxpath("FolderconfirMsg_xpath");
		String Message_string = Message.getText();
		comparestring(Message_string, "FolderCreationmess_string");
		System.out.println("============= successfully Create folder ======================");
		APP_LOGS.debug("================= successfully Create folder ======================");
		Thread.sleep(3000);

	}

	public static void dodeleteAssessmentfolder() throws Exception
	{
		Thread.sleep(3000);
		getObjectxpath("Assessments_xpath").click();
		System.out.println("Assessment click");

		QuestionUtil.WaitForProgressBar();

		Thread.sleep(3000);
		WebElement automationFolderCogWheel=elementExists("//*[@id='assessmentListingFolder']//td[contains(.,'"+Data.getProperty("AssessmentFolderName_string")+"')]/..//img[contains(@src,'cog.png')]");
		WebElement deleteFolderLink=elementExists("//*[@id='assessmentListingFolder']//td[contains(.,'"+Data.getProperty("AssessmentFolderName_string")+"')]/..//a[contains(text(),'Delete Folder')]");

		new Actions(driver).moveToElement(automationFolderCogWheel).moveToElement(deleteFolderLink).click().build().perform();

		/*
		Thread.sleep(3000);
		WebElement qsfolder1= driver.findElement(By.xpath("//*[@id='assessmentListingFolder']//span/*[text()='AutomationFolder']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
				qsfolder1, "color: yellow; border: 2px solid yellow;");
		Locatable hoverItem = (Locatable) qsfolder1;
		Mouse mouse = ((HasInputDevices) driver).getMouse();
		mouse.mouseMove(hoverItem.getCoordinates());


		String fd= qsfolder1.findElement(By.xpath("..//a[text()='Delete Folder']")).getAttribute("onclick");


		String scriptName="deleteFolderOpen";
		fd=fd.replaceAll("\"", "'");
		String [] javascr=fd.split(scriptName);
		String kj=javascr[1];
		System.out.println("Delete Script"+js.executeScript(scriptName+kj));
		Thread.sleep(3000l);

		//qsfolder1.findElement(By.xpath("..//a[contains(@onclick,'deleteFolderOpen')]")).click();
		Thread.sleep(3000);

		 */
		getObjectxpath("deletefolderbutton_xpath").click();
		WaitForProgressBar();
		Thread.sleep(2000);
		//Assertion 
		WebElement Message = getObjectxpath("FolderconfirMsg_xpath");
		String Message_string = Message.getText();
		comparestring(Message_string, "Folderdeletionmess_string");
		System.out.println("============= successfully delete folder ======================");
		APP_LOGS.debug("================= successfully delete folder ======================");


	}
	public static void deleteExamFolder(String folderName) throws Exception
	{
		APP_LOGS.debug("--------Exam Folder Deletion start--------");
		ClickOnAssessmentsTab();
		WebElement rootFolderCogWheel = elementExists("//*[@id='examFolderTreeDiv']//span[@title='/EXAMS/"+folderName+"']/../span[2]");
		WebElement cogWheel_NewFolderLink= elementExists("//*[@id='examFolderTreeDiv']//span[@title='/EXAMS/"+folderName+"']/../span[2]//a[text()='Delete Folder']");
		//click on new folder link
		new Actions(driver).moveToElement(rootFolderCogWheel).moveToElement(cogWheel_NewFolderLink).click().build().perform();
		APP_LOGS.debug("Clicked on delete folder link");
		APP_LOGS.debug("Popup window '"+elementExists("//span[contains(text(),'Delete Folder')]").getText()+"' displayed");
		//Enter folder name
		elementExists("//*[@id='deleteCancelYesDiv']/a[text()='Yes']").click();
		WaitForProgressBar();
		if(elementExists("//*[@id='globalMessageDiv']//*[contains(@class,'confirmationLabel')]")!=null){
			APP_LOGS.debug("Folder "+folderName+" deleted successfully!");
		}
		else
			Assert.fail("Error in folder deletion!!");
		APP_LOGS.debug("--------Question Folder Deletion end--------");
	}

	public static void doeditAssessmentfolder( ) throws Exception
	{
		Thread.sleep(3000);
		getObjectxpath("Assessments_xpath").click();
		System.out.println("Assessment click");

		QuestionUtil.WaitForProgressBar();

		Thread.sleep(3000);
		WebElement automationFolderCogWheel=elementExists("//*[@id='assessmentListingFolder']//td[contains(.,'"+Data.getProperty("AssessmentFolderName_string")+"')]/..//img[contains(@src,'cog.png')]");
		WebElement editFolderLink=elementExists("//*[@id='assessmentListingFolder']//td[contains(.,'"+Data.getProperty("AssessmentFolderName_string")+"')]/..//a[contains(text(),'Edit Folder')]");

		new Actions(driver).moveToElement(automationFolderCogWheel).moveToElement(editFolderLink).click().build().perform();


		/*WebElement qsfolder1= driver.findElement(By.xpath("//*[@id='assessmentListingFolder']//span/*[text()='AutomationFolder']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
				qsfolder1, "color: yellow; border: 2px solid yellow;");
		Locatable hoverItem = (Locatable) qsfolder1;
		Mouse mouse = ((HasInputDevices) driver).getMouse();
		mouse.mouseMove(hoverItem.getCoordinates());

		String fd= qsfolder1.findElement(By.xpath("..//a[text()='Edit Folder']")).getAttribute("onclick");


		String scriptName="editFolderOpen";
		fd=fd.replaceAll("\"", "'");
		String [] javascr=fd.split(scriptName);
		String kj=javascr[1];
		System.out.println("Edit Folder"+js.executeScript(scriptName+kj));
		Thread.sleep(3000l);

		//qsfolder1.findElement(By.xpath("..//a[contains(@onclick,'editFolderOpen')]")).click();
		 */		Thread.sleep(3000);

		 getObjectxpath("EditFoldername_xpath").clear();
		 getObjectxpath("EditFoldername_xpath").sendKeys(Data.getProperty("assessmentfolderedit_string"));
		 Thread.sleep(3000);
		 getObjectxpath("editFolderbutton_xpath").click();
		 WaitForProgressBar();
		 Thread.sleep(3000);

		 //Assertion 
		 WebElement Message = getObjectxpath("FolderconfirMsg_xpath");
		 String Message_string = Message.getText();
		 comparestring(Message_string, "Foldereditmess_string");
		 System.out.println("============= successfully edit folder name  ======================");
		 APP_LOGS.debug("================= successfully edit folder name  ======================");
		 Thread.sleep(3000);


	}

	public static void doreeditAssessmentfolder( ) throws Exception
	{
		Thread.sleep(3000);
		getObjectxpath("Assessments_xpath").click();
		System.out.println("Assessment click");

		QuestionUtil.WaitForProgressBar();

		Thread.sleep(3000);
		WebElement automationFolderEditCogWheel=elementExists("//*[@id='assessmentListingFolder']//td[contains(.,'AutomationFolderedit')]/..//img[contains(@src,'cog.png')]");
		WebElement editFolderLink=elementExists("//*[@id='assessmentListingFolder']//td[contains(.,'AutomationFolderedit')]/..//a[contains(text(),'Edit Folder')]");

		new Actions(driver).moveToElement(automationFolderEditCogWheel).moveToElement(editFolderLink).click().build().perform();
		/*WebElement qsfolder1= driver.findElement(By.xpath("//span[@title='/EXAMS/AutomationFolderedit']/..//li/img"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
				qsfolder1, "color: yellow; border: 2px solid yellow;");
		Locatable hoverItem = (Locatable) qsfolder1;
		Mouse mouse = ((HasInputDevices) driver).getMouse();
		mouse.mouseMove(hoverItem.getCoordinates());

		String fd= qsfolder1.findElement(By.xpath("..//a[text()='Edit Folder']")).getAttribute("onclick");


		String scriptName="editFolderOpen";
		fd=fd.replaceAll("\"", "'");
		String [] javascr=fd.split(scriptName);
		String kj=javascr[1];
		System.out.println("Reedit Script"+js.executeScript(scriptName+kj));
		Thread.sleep(3000l);

		//qsfolder1.findElement(By.xpath("..//a[contains(@onclick,'editFolderOpen')]")).click();
		Thread.sleep(3000);



		//qsfolder1.findElement(By.xpath("..//a[contains(@onclick,'editFolderOpen')]")).click();
		 */		Thread.sleep(3000);

		 getObjectxpath("EditFoldername_xpath").clear();
		 getObjectxpath("EditFoldername_xpath").sendKeys(Data.getProperty("AssessmentFolderName_string"));
		 WaitForProgressBar();
		 Thread.sleep(3000);
		 getObjectxpath("editFolderbutton_xpath").click();
		 Thread.sleep(3000);

		 //Assertion 
		 Thread.sleep(3000);
		 WebElement Message = getObjectxpath("FolderconfirMsg_xpath");
		 String Message_string = Message.getText();
		 comparestring(Message_string, "folderreeditmess_string");
		 System.out.println("============= successfully reedit folder name ======================");
		 APP_LOGS.debug("================= successfully reedit folder name ======================");
		 Thread.sleep(3000);


	}

	public static void doshareAssessmentfolder() throws Exception
	{
		Thread.sleep(3000);
		getObjectxpath("Assessments_xpath").click();
		System.out.println("Assessment click");

		QuestionUtil.WaitForProgressBar();

		Thread.sleep(3000);
		WebElement automationFolderCogWheel=elementExists("//*[@id='assessmentListingFolder']//td[contains(.,'"+Data.getProperty("AssessmentFolderName_string")+"')]/..//img[contains(@src,'cog.png')]");
		WebElement shareFolderLink=elementExists("//*[@id='assessmentListingFolder']//td[contains(.,'"+Data.getProperty("AssessmentFolderName_string")+"')]/..//a[contains(text(),'Share Folder')]");

		new Actions(driver).moveToElement(automationFolderCogWheel).moveToElement(shareFolderLink).click().build().perform();

		/*
		JavascriptExecutor jsx = (JavascriptExecutor)driver;
		jsx.executeScript("window.scrollBy(0,250)", "");

		Thread.sleep(3000);
		WebElement qsfolder1= driver.findElement(By.xpath("//*[@id='assessmentListingFolder']//span/*[text()='AutomationFolder']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
				qsfolder1, "color: yellow; border: 2px solid yellow;");
		Locatable hoverItem = (Locatable) qsfolder1;
		Mouse mouse = ((HasInputDevices) driver).getMouse();
		mouse.mouseMove(hoverItem.getCoordinates());

		String fd= qsfolder1.findElement(By.xpath("..//a[text()='Share Folder']")).getAttribute("onclick");


		String scriptName="shareFolderOpen";
		fd=fd.replaceAll("\"", "'");
		String [] javascr=fd.split(scriptName);
		String kj=javascr[1];
		((JavascriptExecutor)driver).executeScript(scriptName+kj);
		Thread.sleep(3000l);
		//qsfolder1.findElement(By.xpath("..//a[contains(@onclick,'shareFolderOpen')]")).click();
		Thread.sleep(3000);*/


		//		Thread.sleep(3000);
		//		List<WebElement> radios = driver.findElements(By.xpath(".//*[@id='permission']"));
		//		System.out.println("Total radio buttons -> "+ radios.size());
		//		
		//		Thread.sleep(3000);
		//		
		//		radios.get(7).click();
		//		
		//		System.out.println("Select share option -  Approve ");




		//getObjectxpathInput("shareuserlist", "usernameshare_string");
		//System.out.println("Select username usernameshare_string");

		//Thread.sleep(3000);
		driver.findElement(By.xpath(OR.getProperty("firstRadioButton"))).click();
		getObjectxpath("sharefolderbutton_xpath").click();
		System.out.println("share folder button click");


		//Assertion 
		WaitForProgressBar();
		Thread.sleep(3000);
		WebElement Message = getObjectxpath("FolderconfirMsg_xpath");
		String Message_string = Message.getText();
		comparestring(Message_string, "Foldersharemess_string");
		System.out.println("============= Successfully Shared folder ======================");
		APP_LOGS.debug("================= Successfully Shared folder ======================");
		Thread.sleep(3000);



	}

	//Verify the Column Name Present:
	public static void CheckforColumnNameAdd() throws InterruptedException{
		HashMap<String,Integer> ad= new HashMap<String,Integer>();
		HashMap<String,Integer> adafter= new HashMap<String,Integer>();

		Thread.sleep(3000);
		getObjectxpath("Assessments_xpath").click();
		System.out.println("click Assessment tab");

		QuestionUtil.WaitForProgressBar();
		List<WebElement> qselmrev = driver
				.findElements(By.xpath("//table[@id='questionsTable']//thead/tr//th"));
		int count=1;
		for (WebElement er:qselmrev){
			APP_LOGS.debug("|"+er.getText()+"|"+count);
			er.getText();
			if(!er.getText().equals("")){
				ad.put(er.getText(),count);
			}	


			count++;
		}
		boolean isActionColumnPreset=false;
		boolean isIDColumnPreset=false;
		boolean isStatusColumnPreset=false;
		for ( String key : ad.keySet()) {
			System.out.println("Key iterator fails"+"|"+key+"|");
			if(key.equals("Actions ")){
				isActionColumnPreset=true;
			}
			if(key.equals("ID/Rev ")){
				isIDColumnPreset=true;
			}
			if(key.equals("Status ")){
				isStatusColumnPreset=true;
			}


		}

		if(isActionColumnPreset==false){
			getObjectxpathDirect("//div[@id='addColDiv_questionsTable']//a[@id='cog_questionsTable']").click();
			new Select(driver.findElement(By.id("addCol_questionsTable"))).selectByVisibleText("Actions ");
			QuestionUtil.WaitForProgressBar();

		}
		if(isIDColumnPreset==false){
			getObjectxpathDirect("//div[@id='addColDiv_questionsTable']//a[@id='cog_questionsTable']").click();
			new Select(driver.findElement(By.id("addCol_questionsTable"))).selectByVisibleText("ID/Rev ");
			QuestionUtil.WaitForProgressBar();
		}
		if(isStatusColumnPreset==false){
			getObjectxpathDirect("//div[@id='addColDiv_questionsTable']//a[@id='cog_questionsTable']").click();
			new Select(driver.findElement(By.id("addCol_questionsTable"))).selectByVisibleText("Status ");
			QuestionUtil.WaitForProgressBar();
		}



		List<WebElement> qselmrev1 = driver
				.findElements(By.xpath("//table[@id='questionsTable']//thead/tr//th"));
		int count2=1;
		for (WebElement er:qselmrev1){
			APP_LOGS.debug("|"+er.getText()+"|"+count2);
			er.getText();
			if(!er.getText().equals("")){
				adafter.put(er.getText(),count2);
			}	


			count2++;
		}
		//Verification Part	
		isActionColumnPreset=false;
		isIDColumnPreset=false;
		isStatusColumnPreset=false;
		for ( String key : adafter.keySet() ) {
			if(key.equals("Actions ")){
				isActionColumnPreset=true;
			}
			if(key.equals("ID/Rev ")){
				isIDColumnPreset=true;
			}
			if(key.equals("Status ")){
				isStatusColumnPreset=true;
			}

		}

		if(isActionColumnPreset==false){
			Assert.fail("Actions Column is not displayed on UI.Please add Manually to Proceeds");	
		}
		if(isIDColumnPreset==false){
			Assert.fail("ID/Rev Column is not displayed on UI.Please add Manually to Proceeds");	
		}
		if(isStatusColumnPreset==false){
			Assert.fail("Status Column is not displayed on UI.Please add Manually to Proceeds");	
		}


	}

	public static List<String> AssessmentsCheckForFolderExist() throws InterruptedException{
		List <String> gh = new ArrayList<String>();
		getObjectxpath("Assessments_xpath").click();
		System.out.println("click Assessment tab");
		//    	WebElement qsbyf = getObjectxpathNew("QS_QsbyFolderLink");
		//		qsbyf.click();
		Thread.sleep(5000l);
		//		String className = qsbyf.getAttribute("class");
		//		TestBase.comparestring(className, "QS_DefaultbyFolderClName");

		List <WebElement> er = driver.findElements(By.xpath("//*[@id='examFolderTreeDiv']/ul//li//span[@class='dynatree-title']"));
		for ( WebElement de:er){
			if(!de.getText().equals("")){
				//System.out.println(de.getText());
				gh.add(de.findElement(By.xpath("./span")).getAttribute("title"));
			}

		}


		return gh;
	}



}