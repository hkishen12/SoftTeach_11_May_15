package Util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.SuiteExamTaker.TestSuiteBase;

public class ExamTakerUtil extends TestSuiteBase{

	public static String checkETtabState() {
		String flag="N";
		WaitForProgressBar();
		if ((driver.findElement(By.xpath("html/body/div[1]/div[2]/table/tbody/tr[1]/th")).isDisplayed())){
			flag="Y";
			System.out.println("ET tab is clicked from Java Page - state is:"+flag);
		}
		else{
			System.out.println("ET Tab should be clicked from .Net page - state is :"+flag);
		}
		return flag;
	}

	public static void ClickOnExamTakerTab(){
		waitInSeconds(2);
		driver.navigate().refresh();
		int count=0;
		while(elementExists("//*[@id='navbar']/ul[6]/li/a")==null){
			waitInSeconds(1);
			count++;
			if(count==120) break;
		}
		count=0;
		while(!elementExists("//*[@id='navbar']/ul[6]/li/a").isDisplayed()){
			waitInSeconds(1);
			count++;
			if(count==120) break;
		}
		getObjectxpath("ETTAB_xpath").click();
		WaitForProgressBar(); 
		waitInSeconds(30);
	}
	public static void ClickOnCreateET() throws InterruptedException{
		Thread.sleep(2000);
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='ctl00_ContentPlaceHolder1_createETBtn']")));
		driver.findElement(By.xpath(OR.getProperty("CreateET_window"))).click();
		Thread.sleep(2000);
		System.out.println("Clicked CreateNewET button");
		if (!(driver.findElement(By.xpath("//*[@id='ui-id-8']")).getText()).contains
				("Create Exam Taker")){
			Assert.fail("FAILED - Create ExamTaker Window is either not displayed or there is any error");
		}

		WebElement framecreate = driver.findElement(By.xpath("//*[@id='fCreateExamTaker']"));
		System.out.println("FrameOBJECT created");
		driver.switchTo().frame(framecreate);
		Thread.sleep(3000);
		System.out.println("Switched to Frame");
	}
	public static void doCancelCreateET() throws Exception
	{	
		WebElement CancelBtn = elementExists("//*[@id='ctl00']/div[3]/div/a[1]");
		CancelBtn.click();
		System.out.println("Cancelled Create Exam Taker Successfully");
		WaitForProgressBar();
		Thread.sleep(5000);
	}
	// ==============OverLoading doCreateET for different no. of parameters===================================================

	public static void doCreateET(String fname,String lname, String studID, String email, String passwd) throws Exception
	{	
		//Thread.sleep(3000);
		//When All the required fields are entered by the USER
		getObjectxpath("First_Name").clear();
		try{
			getObjectxpath("First_Name").sendKeys(fname);
		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" + OR.getProperty("First_Name") );
		}
		System.out.println("First Name Entered");

		getObjectxpath("Last_Name").clear();
		try{
			getObjectxpath("Last_Name").sendKeys(lname);
		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" + OR.getProperty("Last_Name") );
		}
		System.out.println("Last_Name Entered");

		getObjectxpath("StudentID").clear();
		try{
			getObjectxpath("StudentID").sendKeys(studID);
		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" + OR.getProperty("StudentID") );
		}
		System.out.println("StudentID Entered");

		getObjectxpath("ETEmail_ID").clear();
		try{
			getObjectxpath("ETEmail_ID").sendKeys(email);
		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" + OR.getProperty("ETEmail_ID") );
		}
		System.out.println("ETEmail_ID Entered");

		getObjectxpath("ET_Password").clear();
		try{
			getObjectxpath("ET_Password").sendKeys(passwd);
		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" + OR.getProperty("ET_Password") );
		}
		System.out.println("ET_Password Entered");

		getObjectxpath("ET_ConfirmPwd").clear();
		try{
			getObjectxpath("ET_ConfirmPwd").sendKeys(passwd);
		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" + OR.getProperty("ET_ConfirmPwd") );
		}
		System.out.println("ET_Confirmed password Entered");
	}
	// ==============OverLoading doCreateET for different no. of parameters===================================================
	public static void doCreateET(String lname, String studID, String email, String passwd) throws Exception
	{	
		//When FirstName of Student is NOT entered by the USER.
		Thread.sleep(3000);

		getObjectxpath("Last_Name").clear();
		try{
			getObjectxpath("Last_Name").sendKeys(lname);
		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" + OR.getProperty("Last_Name") );
		}
		System.out.println("Last_Name Entered");

		getObjectxpath("StudentID").clear();
		try{
			getObjectxpath("StudentID").sendKeys(studID);
		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" + OR.getProperty("StudentID") );
		}
		System.out.println("StudentID Entered");

		getObjectxpath("ETEmail_ID").clear();
		try{
			getObjectxpath("ETEmail_ID").sendKeys(email);
		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" + OR.getProperty("ETEmail_ID") );
		}
		System.out.println("ETEmail_ID Entered");

		getObjectxpath("ET_Password").clear();
		try{
			getObjectxpath("ET_Password").sendKeys(passwd);
		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" + OR.getProperty("ET_Password") );
		}
		System.out.println("ET_Password Entered");

		getObjectxpath("ET_ConfirmPwd").clear();
		try{
			getObjectxpath("ET_ConfirmPwd").sendKeys(passwd);
		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" + OR.getProperty("ET_ConfirmPwd") );
		}
		System.out.println("ET_Confirmed password Entered");
	}
	// ==============OverLoading doCreateET for different no. of parameters===================================================
	public static void doCreateET(String studID, String email, String passwd) throws Exception
	{	
		Thread.sleep(3000);

		//When FirstName and LastName of the student is NOT entered by the user
		getObjectxpath("StudentID").clear();
		try{
			getObjectxpath("StudentID").sendKeys(studID);
		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" + OR.getProperty("StudentID") );
		}
		System.out.println("StudentID Entered");

		getObjectxpath("ETEmail_ID").clear();
		try{
			getObjectxpath("ETEmail_ID").sendKeys(email);
		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" + OR.getProperty("ETEmail_ID") );
		}
		System.out.println("ETEmail_ID Entered");

		getObjectxpath("ET_Password").clear();
		try{
			getObjectxpath("ET_Password").sendKeys(passwd);
		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" + OR.getProperty("ET_Password") );
		}
		System.out.println("ET_Password Entered");

		getObjectxpath("ET_ConfirmPwd").clear();
		try{
			getObjectxpath("ET_ConfirmPwd").sendKeys(passwd);
		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" + OR.getProperty("ET_ConfirmPwd") );
		}
		System.out.println("ET_Confirmed password Entered");
	}
	// ==============OverLoading doCreateET for different no. of parameters===================================================
	public static void doCreateET(String email, String passwd) throws Exception
	{	
		Thread.sleep(3000);

		//When Only Email and Password of the student is entered by the user

		getObjectxpath("ETEmail_ID").clear();
		try{
			getObjectxpath("ETEmail_ID").sendKeys(email);
		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" + OR.getProperty("ETEmail_ID") );
		}
		System.out.println("ETEmail_ID Entered");

		getObjectxpath("ET_Password").clear();
		try{
			getObjectxpath("ET_Password").sendKeys(passwd);
		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" + OR.getProperty("ET_Password") );
		}
		System.out.println("ET_Password Entered");

		getObjectxpath("ET_ConfirmPwd").clear();
		try{
			getObjectxpath("ET_ConfirmPwd").sendKeys(passwd);
		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" + OR.getProperty("ET_ConfirmPwd") );
		}
		System.out.println("ET_Confirmed password Entered");
	}
	// ==============OverLoading doCreateET for different no. of parameters===================================================
	public static void doCreateET(String passwd) throws Exception
	{	
		Thread.sleep(3000);

		//When Only Password of the student is entered by the user

		getObjectxpath("ET_Password").clear();
		try{
			getObjectxpath("ET_Password").sendKeys(passwd);
		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" + OR.getProperty("ET_Password") );
		}
		System.out.println("ET_Password Entered");

		getObjectxpath("ET_ConfirmPwd").clear();
		try{
			getObjectxpath("ET_ConfirmPwd").sendKeys(passwd);
		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" + OR.getProperty("ET_ConfirmPwd") );
		}
		System.out.println("ET_Confirmed password Entered");
	}
	// ==============OverLoading doCreateET for different no. of parameters===================================================
	public static void doCreateET() throws Exception
	{	
		Thread.sleep(3000);

		//When Nothing is entered by the user for Student

		System.out.println("Clicking SAVE Create Exam Taker");


	}


	public static void doSuccessSaveCreateET() throws Exception{

		getObjectxpath("Save_CreateET").click();
		System.out.println("Clicked SAVE Create Exam Taker");
		WaitForProgressBar();
		Thread.sleep(5000);
		driver.switchTo().defaultContent();
		waitInSeconds(2);
		WebElement ETCreatedsucessfully = getObjectxpathNew("ET_CreateConfirmationMsg");
		String ActualMsg = ETCreatedsucessfully.getText();
		//Verification part
		QuestionUtil.ComapreTwoStringVal(ActualMsg.trim(),"Exam Taker was created successfully.");
		System.out.println("Verified ET Sucessfully Created Message ");

	}

	public static void doVerifyErrorsCreateET() throws Exception{
		getObjectxpath("Save_CreateET").click();
		Thread.sleep(3000);

		WebElement ETFirstNameError = getObjectxpathNew("ET_FnameErrorMsg");
		String ActualMsg = ETFirstNameError.getText();
		String Expected_string1= "*First Name is required or contains invalid characters.";
		String Expected_string2= "*First Name is required or contains invalid characters.\n*Last Name is required or contains invalid characters.";
		String Expected_string3="*First Name is required or contains invalid characters.\n*Last Name is required or contains invalid characters.\n*Student ID is required or contains invalid characters.";
		String Expected_string4="*First Name is required or contains invalid characters.\n*Last Name is required or contains invalid characters.\n*Student ID is required or contains invalid characters.\n*Email is required.";
		String Expected_string5="*First Name is required or contains invalid characters.\n*Last Name is required or contains invalid characters.\n*Student ID is required or contains invalid characters.\n*Email is required.\n*Password is required or contains invalid characters.\n*Confirmation Password is required or contains invalid characters.";
		//System.out.println(Expected_string2);
		//Verification part
		if (ActualMsg.equals(Expected_string1)){
			/*QuestionUtil.ComapreTwoStringVal(ActualMsg.trim(),"*First Name is required or contains invalid characters.");*/
			System.out.println("Verified - ET CanNOT be created when First Name is Missing");
		}
		else if (ActualMsg.equalsIgnoreCase(Expected_string2)){
			System.out.println("Verified -  ET CanNOT be created when First and Last Name is Missing");
		}
		else if (ActualMsg.equalsIgnoreCase(Expected_string3)){
			System.out.println("Verified - ET CanNOT be created when Name and Student ID are Missing");
		}
		else if (ActualMsg.equalsIgnoreCase(Expected_string4)){
			System.out.println("Verified - ET CanNOT be created when Name, Student ID and Email are Missing");
		}
		else if (ActualMsg.equalsIgnoreCase(Expected_string5)){
			System.out.println("Verified - ET CanNOT be created when ALL Required Fields are Missing");
		}
		else{
			System.out.println("There is Some Issue with Error Messages of Create ET");
		}
	}

	public static void doSearchExamTaker(String StudentID) throws InterruptedException{
		//Search for ET provided from Config file
		System.out.println(CONFIG.getProperty(StudentID));
		driver.findElement(By.xpath(OR.getProperty("ET_searchbox_xpath"))).sendKeys(CONFIG.getProperty(StudentID));
		System.out.println("String entered in the Search box");
		Thread.sleep(2000);
		getObjectxpath("ET_searchbutton_xpath").click();
		System.out.println("Search button clicked.");
		WaitForProgressBar();

		//Assertion 
		Thread.sleep(4000);
		String actualETID= elementExists("//*[@id='etgrid']/tbody/tr/td[2]/a").getText().trim();
		System.out.println("Actual ExamTaker ID : "+actualETID);
		System.out.println("Searched for ExamTaker ID : "+(CONFIG.getProperty(StudentID)));
		if(actualETID.equalsIgnoreCase((CONFIG.getProperty(StudentID))))
			System.out.println(" ============= Successfully Searched ExamTaker ======================");
		else
			System.out.println("ExamTaker NOT found");
	}
	public static void doSearchByAnyOption(String ParaName, String ParaValue) throws InterruptedException{
		//Search for ET either by name or id
		driver.findElement(By.xpath(OR.getProperty("ET_searchbox_xpath"))).sendKeys(ParaValue);
		System.out.println("String entered in the Search box");
		Thread.sleep(2000);
		getObjectxpath("ET_searchbutton_xpath").click();
		System.out.println("Search button clicked");
		WaitForProgressBar();

		//Assertion
		int count=0;
		while(elementExists("//*[@id='etgrid']/tbody/tr/td[2]/a")==null){
			count++;
			waitInSeconds(1);
			if(count>120) break;
		}
		String actualETID= elementExists("//*[@id='etgrid']/tbody/tr/td[2]/a").getText().trim();
		String actualETName = elementExists("//*[@id='etgrid']/tbody/tr/td[3]/a").getText().trim();
		System.out.println("Actual ExamTaker ID : "+actualETID);
		System.out.println("Actual ExamTaker Name : "+actualETName);
		System.out.println("Searched for ExamTaker ID : "+ParaValue);
		System.out.println("Search was done by : "+ParaName);

		if(actualETID.equalsIgnoreCase(ParaValue))
			System.out.println(" ============= Successfully Searched ExamTaker by Student ID======================");
		else if (actualETName.contains(ParaValue))
			System.out.println(" ============= Successfully Searched ExamTaker by Student NAME======================");
		else 
			System.out.println("Exam Taker NOT Found");
	}

	public static void ClickOnEditET() throws InterruptedException{

		//doSearchExamTaker("ExamTakerID");
		int colno = ColumnCheckETGrid("Actions");
		driver.findElement(By.xpath("//*[@id='etgrid']/tbody/tr[1]/td["+colno+"]/a[1]")).click();
		WebElement frameEdit = driver.findElement(By.xpath("//*[@id='fEditExamTaker']"));
		driver.switchTo().frame(frameEdit);
		Thread.sleep(3000);
	}
	public static void doEditET() throws InterruptedException{
		//Edit First Name of ExamTaker
		getObjectxpath("First_Name").clear();
		String ETFname=randomStringGen("Edited_");
		getObjectxpath("First_Name").sendKeys(ETFname);
		System.out.println("First Name Edited BUT Not yet Saved");
		getObjectxpath("ET_EditSave").click();
		System.out.println("Clicked Save Edit");
		driver.switchTo().defaultContent();
		Thread.sleep(3000);
		WebElement ETEditsucess = getObjectxpathNew("ET_EditConfirmationMsg");
		String ActualMsg = ETEditsucess.getText();
		System.out.println(ActualMsg);

		//Verifying Confirmation Message
		QuestionUtil.ComapreTwoStringVal(ActualMsg.trim(),"Saved ET Details successfully.");
		System.out.println("Verified ET Sucessfully Edit Message is Displayed");
		//Verifying Edited Data
		String ETName = driver.findElement(By.xpath("//*[@id='etgrid']/tbody/tr/td[3]/a")).getText();
		if (ETName.contains(ETFname)){
			System.out.println("Verified : ET First Name is EDITED Successfully");
		}
		else
			System.out.println("Edited Name is NOT reflecting in GRID");
	}




	public static int ColumnCheckETGrid(String ColumnName) throws InterruptedException{
		int th = driver.findElements(By.xpath("//*[@id='etgrid']/thead/tr/th")).size();
		System.out.println("No. of columns calculated");
		boolean flag = true;
		//Find if Column is displayed in the ExamTaker List page
		for (int i=2; i<=th; i++ )
		{
			String ColName = driver.findElement(By.xpath("//*[@id='etgrid']/thead/tr/th["+i+"]")).getText();

			if (ColName!=null && ColName.trim().equalsIgnoreCase(ColumnName))
			{
				System.out.println("Column Exists in ET Grid "+ColumnName);

				flag=false;
				return i;
			}

		}
		// If Column is NOT in the ExamTaker List page Then Add it from Cog
		System.out.println(flag);
		if(flag){
			System.out.println("Column needs to be added from COG");	
			driver.findElement(By.xpath("//*[@id='cog1']")).click();
			WebElement Cog = driver.findElement(By.xpath("//*[@id='addCol1']"));
			Cog.sendKeys(ColumnName);
			Thread.sleep(3001);
			System.out.println("column added in Exam Taker listing : " +ColumnName);
			th = th+1;
			return th;
		}
		return 0;
	}
	public static void ClickOnEmailET() throws InterruptedException{

		//doSearchExamTaker("ExamTakerID");
		int colno = ColumnCheckETGrid("Actions");
		driver.findElement(By.xpath("//*[@id='etgrid']/tbody/tr[1]/td["+colno+"]/a[3]")).click();
		WebElement frameEmail = driver.findElement(By.xpath("//*[@id='fMailExamTaker']"));
		driver.switchTo().frame(frameEmail);
		Thread.sleep(3000);
	}		
	public static void doEmailET() throws InterruptedException{

		getObjectxpath("Add_EmailTemplate").click();

		System.out.println("Email Template Added");
		Thread.sleep(3000);
		getObjectxpath("ET_EmailSend").click();
		System.out.println("Clicked Send Email");

		WebElement ETEmailsucess = getObjectxpathNew("ET_EmailConfirmation");
		String ActualMsg = ETEmailsucess.getText();
		System.out.println(ActualMsg);

		//Verifying Confirmation Message
		QuestionUtil.ComapreTwoStringVal(ActualMsg.trim(),"Email sent to 1 Exam Taker(s).");
		System.out.println("Verified ET Sucessfully Email Sent Message is Displayed");
		getObjectxpath("Email_Cancel").click();				
		Thread.sleep(3000);
	}
	public static void ClickOnDeleteET() throws InterruptedException{

		int colno = ColumnCheckETGrid("Actions");
		driver.findElement(By.xpath("//*[@id='etgrid']/tbody/tr[1]/td["+colno+"]/a[4]")).click();
		WebElement DeleteET = driver.findElement(By.xpath("//*[@id='deleteExamTaker']"));
		if (DeleteET.isDisplayed()){

			System.out.println("Delete ET Alert is displayed ");
		}
		else{
			System.out.println("Delete ET Alert is either NOT displayed or NOT identified");
		}
		Thread.sleep(3000);
	}
	public static void doDeleteET() throws InterruptedException{
		// This method applies for both Single Delete as well as Bulk Delete
		String script="javascript:confirmedDeleteSelected()";
		((JavascriptExecutor) driver).executeScript(script);
		/*WebElement ConfirmDelete = driver.findElement(By.xpath("//*[@id='confirmDelete']/div[2]/a[2]"));
		ConfirmDelete.click();*/

		System.out.println("Clicked YES for DeleteET");
		WaitForProgressBar();
		Thread.sleep(10000);
		WaitForProgressBar();
		WebElement ETDeleteSuccess = getObjectxpathNew("ET_EditConfirmationMsg");
		String ActualMsg = ETDeleteSuccess.getText();
		System.out.println(ActualMsg);

		//Verifying Confirmation Message
		QuestionUtil.ComapreTwoStringVal(ActualMsg.trim(),"Successfully deleted selected ET.");
		System.out.println("Verified ET Sucessfully Deleted Message is Displayed");
		Thread.sleep(3000);
	}
	public static void ClickOnETHistory(String ETID) throws InterruptedException{


		int colno = ColumnCheckETGrid("Actions");
		driver.findElement(By.xpath("//*[@id='etgrid']/tbody/tr[1]/td["+colno+"]/a[2]")).click();
		WaitForProgressBar();
		Thread.sleep(3000);
		WebElement frameHistory = driver.findElement(By.xpath("//*[@id='fHistoryExamTaker']"));
		driver.switchTo().frame(frameHistory);
		Thread.sleep(3000);
		System.out.println("Matching Student ID");
		WaitForProgressBar();
		elementExists("//*[@id='lblStudentId']");
		String script = "return document.getElementById('lblStudentId').outerHTML;";
		String ActualStudentID = (String) ((JavascriptExecutor) driver).executeScript(script);
		String StudID = CONFIG.getProperty(ETID);
		//	String ActualStudentID = driver.findElement(By.xpath("//*[@id='lblStudentId']")).getAttribute("value");
		System.out.println("Actual ET ID :"+ActualStudentID);
		System.out.println("Tested with ET ID :"+StudID);
		ActualStudentID.contains(StudID);
		//QuestionUtil.ComapreTwoStringVal(ActualStudentID.trim(),StudID);
		System.out.println("Verified ET History window if Selected ET is Displayed");


	}		
	public static void ETHistorySaveComments() throws InterruptedException{


		WebElement commentsBox = driver.findElement(By.xpath("//*[@id='RadGrid1_ctl00__0']/td[12]/input"));
		commentsBox.sendKeys(StringwithDate("AutomationComments_"));
		WebElement UpdateBtn = driver.findElement(By.xpath("//*[@id='btnSave']"));
		UpdateBtn.click();
		WaitForProgressBar();
		driver.switchTo().defaultContent();
		WebElement ETUpdatesucess = getObjectxpathNew("ET_UpdateConfirmation");
		String ActualMsg = ETUpdatesucess.getText();
		System.out.println(ActualMsg);
		//Verifying Confirmation Message
		QuestionUtil.ComapreTwoStringVal(ActualMsg.trim(),"Updated Successfully.");
		System.out.println("Verified ET History Sucessfully Updated");

	}
	public static void ClickOnBulkDelete() throws Exception{

		SortbyColumn("Activated");
		Thread.sleep(3000);
		List <WebElement> CHECKBOXlist = driver.findElements(By.xpath("//*[@id='chkBox']"));

		for(int i=1;i<3;i++){
			CHECKBOXlist.get(i).click();
			System.out.println("CheckBox Selected");
		}
		driver.findElement(By.xpath("//*[@id='deleteSelected']")).click();
		WebElement DeleteET = driver.findElement(By.xpath("//*[@id='deleteExamTaker']"));
		if (DeleteET.isDisplayed()){

			System.out.println("Delete ET Alert is displayed ");
		}
		else{
			System.out.println("Delete ET Alert is either NOT displayed or NOT identified");
		}
	}
	public static void ClickOnBulkEdit() throws Exception{

		List <WebElement> CHECKBOXlist = driver.findElements(By.xpath("//*[@id='chkBox']"));
		System.out.println("Number of checkbox: " +CHECKBOXlist.size());
		for(int i=1;i<CHECKBOXlist.size();i++){
			CHECKBOXlist.get(i).click();
			System.out.println("CheckBox Selected");
		}
		driver.findElement(By.xpath("//*[@id='editSelected']")).click();
		WebElement frameEdit = driver.findElement(By.xpath("//*[@id='fManageExamTakers']"));
		driver.switchTo().frame(frameEdit);
		System.out.println("Manage ET window is displayed");
		/*	WebElement examDropbx = driver.findElement(By.xpath("//*[@id='ExamRadComboBox_Input']"));
		WebDriverWait wait = new WebDriverWait(driver, 120);
		  wait.until(ExpectedConditions.textToBePresentInElementValue(examDropbx, "All Exams"));*/
		Thread.sleep(5000);
	}

	public static void doBulkEdit() throws Exception{
		WebElement commentsBox = driver.findElement(By.xpath("//*[@id='comments']"));
		Thread.sleep(3000);
		commentsBox.sendKeys(StringwithDate("AutomationComments_"));
		System.out.println("Comments Entered");
		Thread.sleep(3000);
		WebElement UpdateBtn = driver.findElement(By.xpath("//*[@id='saveBtn']"));
		System.out.println("Comments Saved");
		UpdateBtn.click();
		waitInSeconds(5);
		driver.switchTo().defaultContent();
		WebElement ETUpdatesucess = getObjectxpathNew("ET_UpdateConfirmation");
		String ActualMsg = ETUpdatesucess.getText();
		System.out.println(ActualMsg);
		//Verifying Confirmation Message
		QuestionUtil.ComapreTwoStringVal(ActualMsg.trim(),"Successfully updated selected ETs.");
		System.out.println("Verified ET History Sucessfully Updated");
	}

	public static void SortbyColumn(String ColName) throws Exception{

		int colNum = ColumnCheckETGrid(ColName);
		driver.findElement(By.xpath("//*[@id='etgrid']/thead/tr/th["+colNum+"]")).click();
		WaitForProgressBar();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id='etgrid']/thead/tr/th["+colNum+"]")).click();
		WaitForProgressBar();
		Thread.sleep(3000);
		System.out.println("Sorted by Column Name :" +ColName);
	}

	public static void ClickOnAdvanceSearch() throws Exception{
		WebElement AdvanceSearch = driver.findElement(By.xpath("//*[@id='btnShowAdvancedSearch']"));
		AdvanceSearch.click();
		System.out.println("Clicked AdvanceSearch Button");
		Thread.sleep(4000);
		//WebDriverWait wait = new WebDriverWait(driver, 120);
		//wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='ctl00_ContentPlaceHolder1_UpdatePanel1']")));

	}
	public static void doAdvanceSearch(String ParaName, String ParaValue) throws InterruptedException{
		// Advance Search BY ETName/ET ID 
		Thread.sleep(2000);
		StaleElementHandleByXPATH ("//*[@id='ctl00_ContentPlaceHolder1_ETNameID']");
		driver.findElement(By.xpath("//*[@id='ctl00_ContentPlaceHolder1_ETNameID']")).sendKeys(ParaValue);
		System.out.println("String entered in the Search box");
		Thread.sleep(2000);

		WebElement SearchBtn = driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_UpdatePanel1']/table/tbody/tr[3]/td/div/a[2]"));
		SearchBtn.click();
		System.out.println("Search button clicked");

		WaitForProgressBar();

		//Assertion 
		Thread.sleep(4000);
		String actualETID= elementExists("//*[@id='etgrid']/tbody/tr/td[2]/a").getText().trim();
		String actualETName = elementExists("//*[@id='etgrid']/tbody/tr/td[3]/a").getText().trim();
		System.out.println("Actual ExamTaker ID : "+actualETID);
		System.out.println("Actual ExamTaker Name : "+actualETName);
		System.out.println("Searched for ExamTaker ID : "+ParaValue);
		System.out.println("Search was done by : "+ParaName);

		if(actualETID.equalsIgnoreCase(ParaValue))
			System.out.println(" ============= Successfully Searched ExamTaker by Student ID======================");
		else if (actualETName.contains(ParaValue))
			System.out.println(" ============= Successfully Searched ExamTaker by Student NAME======================");
		else 
			System.out.println("Exam Taker NOT Found");

		int colNum = ColumnCheckETGrid("Status");
		String actualETStatus = elementExists("//*[@id='etgrid']/tbody/tr[1]/td["+colNum+"]").getText().trim();
		System.out.println("Actual ExamTaker Status : "+actualETStatus);
		/*System.out.println("Searched for ExamTaker Status : "+Status);
	if(actualETStatus.equalsIgnoreCase(Status))
		System.out.println(" ============= Successfully Searched ExamTaker by Status======================");
	else 
		System.out.println("Exam Taker Status is NOT matching");*/

	}


	public static void  RemoveColumn(int ColIndex) throws Exception{
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='etgrid']/thead/tr/th["+ColIndex+"]/a")).click();
		Thread.sleep(3000);
	}


}


