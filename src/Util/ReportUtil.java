package Util;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Base.TestBase;

public class ReportUtil extends TestBase {
	public static String actualRecord[][];
	public static Xls_Reader reportXls=null;
	public static boolean flag=false;

	//Common Functions for Reports
	public static void ClickOnAssessmentsTab() throws InterruptedException{
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='navbar']/ul[3]/li/a")).click();
		QuestionUtil.WaitForProgressBar();
		Thread.sleep(5000);
	}

	public static void SearchAssessmentWithTitle(String title) throws Exception
	{			

		System.out.println("Assessment link clicked");
		driver.findElement(By.xpath(OR.getProperty("searchbox_xpath"))).sendKeys(title);
		elementExists("//*[@id='numDaysFromPostingEnd']").sendKeys("All Postings");
		System.out.println("String entered in the Search box");
		Thread.sleep(3000);
		getObjectxpath("searchbutton_xpath").click();
		System.out.println("Search button clicked");
		QuestionUtil.WaitForProgressBar();
	}

	public static void ClickOnGridResult() throws InterruptedException
	{
		Thread.sleep(3000);
		elementExists("//*[@id='examsTable']/tbody/tr/td[2]/a").click();
		System.out.println("Clicked On Grid Result");
		QuestionUtil.WaitForProgressBar();

	}

	//ET Results
	public static void ClickOnETResult() throws InterruptedException 

	{	
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement ReportingLink=driver.findElement(By.xpath(OR.getProperty("ReportingLink")));
		WebElement ETResultLink=driver.findElement(By.xpath(OR.getProperty("ETResultLink")));
		wait.until(ExpectedConditions.elementToBeClickable(ReportingLink));
		Actions  actions  = new Actions(driver);
		actions.moveToElement(ReportingLink).moveToElement(ETResultLink).click().build().perform();
		Thread.sleep(8000);
		System.out.println("Clicked On ET Results label from Reporting/Scoring Dropdown");
	}

	public static void CheckOptionAndView() throws InterruptedException

	{
		elementExists("//*[@id='chkETName']").click();
		System.out.println("Exam Taker Checkbox Checked");
		elementExists("//*[@id='chkResponses']").click();
		System.out.println("Exam Taker Response Checkbox Checked");
		elementExists("//*[@id='chkExamScore']").click();
		System.out.println("Score Checkbox Checked");
		Thread.sleep(2000);
		elementExists("//*[@id='btnView']").click();
		System.out.println("Clicked On View Report Button");
		QuestionUtil.WaitForProgressBar();

	}

	public static void ActualReportValues(){
		int size = driver.findElements(By.xpath("//*[@id='grdResults']/tbody/tr")).size();// get no. of records
		System.out.println(size);
		actualRecord=new String[size][3];
		for (int i=0; i<size;i++ ){
			actualRecord[i][0]=driver.findElement(By.xpath("//*[@id='grdResults']/tbody/tr["+(i+1)+"]/td[3]")).getText().trim();//lname
			actualRecord[i][1]=driver.findElement(By.xpath("//*[@id='grdResults']/tbody/tr["+(i+1)+"]/td[4]")).getText().trim();//fname
			actualRecord[i][2]=driver.findElement(By.xpath("//*[@id='grdResults']/tbody/tr["+(i+1)+"]/td[5]")).getText().trim();//pts
			System.out.println("Record: Last Name="+actualRecord[i][0]+", First Name="+actualRecord[i][1]+", Points="+actualRecord[i][2]);
		}

	}
	//Whenever More than 50 entries displayed on the Grid
	public static void ClickOnPagination() {
		elementExists("//*[@id='toolsAreaBottom']//label/a[2]").click();

	}

	public static void CompareReportValues(){
		reportXls = new Xls_Reader(System.getProperty("user.dir")+"//src//Import//ExpectedValues.xlsx");
		for(int i=0;i<actualRecord.length;i++){
			if(actualRecord[i][0].equalsIgnoreCase(reportXls.getCellData(CONFIG.getProperty("ETResultsSheet"), "Lname", i+2))
					&& actualRecord[i][1].equalsIgnoreCase(reportXls.getCellData(CONFIG.getProperty("ETResultsSheet"), "Fname", i+2))
					&& actualRecord[i][2].equalsIgnoreCase(reportXls.getCellData(CONFIG.getProperty("ETResultsSheet"), "Pts", i+2))){
				System.out.println("Record: Last Name="+actualRecord[i][0]+", First Name="+actualRecord[i][1]+", Points="+actualRecord[i][2]+" data verified successfully!");
			}else{
				System.out.println("Record: Last Name="+actualRecord[i][0]+", First Name="+actualRecord[i][1]+", Points="+actualRecord[i][2]+" data verification failed!");
				flag=true;
			}

		}
		if(flag){
			Assert.fail("Report generated has some issues!");
		}

	}

	//Summary Reports
	public static void ClickOnSummaryReport() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement ReportingLink=driver.findElement(By.xpath(OR.getProperty("ReportingLink")));
		WebElement ETResultLink=driver.findElement(By.xpath(OR.getProperty("SummaryReporttLink")));
		wait.until(ExpectedConditions.elementToBeClickable(ReportingLink));
		Actions  actions  = new Actions(driver);
		actions.moveToElement(ReportingLink).moveToElement(ETResultLink).click().build().perform();
		Thread.sleep(8000);
		System.out.println("Clicked On Summary Report label from Reporting/Scoring Dropdown");

	}

	public static void SummaryReportsData() {
		try {
			String oldTab = driver.getWindowHandle();
			elementExists("//*[@id='ctl00_ctl00_ContentPlaceHolder1_ContentPlaceHolder1_btnUse25']").click();
			ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
			newTab.remove(oldTab);
			driver.switchTo().window(newTab.get(0));
			System.out.println("New Window Title is :- "+driver.getTitle());
			System.out.println("Summary Report is Opened in New Tab/Window");
			/*	System.out.println(elementExists("Assessment Name = "+"//*[@id='pageContainer1']/xhtml:div[2]/xhtml:div[80]").getText().trim());
		System.out.println(elementExists("//*[@id='pageContainer1']/xhtml:div[2]/xhtml:div[2]").getText().trim());
		System.out.println(elementExists("//*[@id='pageContainer1']/xhtml:div[2]/xhtml:div[3]").getText().trim());
		System.out.println(elementExists("//*[@id='pageContainer1']/xhtml:div[2]/xhtml:div[4]").getText().trim());*/
			driver.close();
			driver.switchTo().window(oldTab);
		} catch(Exception e) {
			System.out.println("Hii");
		}

	}

	//Release Results-Post To Exam Taker Portal
	public static void ClickOnReleaseResults() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement ReportingLink=driver.findElement(By.xpath(OR.getProperty("ReportingLink")));
		WebElement ReleaseResultLink=driver.findElement(By.xpath(OR.getProperty("ReleaseETResults")));
		wait.until(ExpectedConditions.elementToBeClickable(ReportingLink));
		Actions  actions  = new Actions(driver);
		actions.moveToElement(ReportingLink).moveToElement(ReleaseResultLink).click().build().perform();
		Thread.sleep(8000);
		System.out.println("Clicked On Release Exam taker Results label from Reporting/Scoring Dropdown");

	}

	public static void SelectETsForResealseResults() throws InterruptedException {

		while(!elementExists("//*[@class='listingsection']").isDisplayed()){
			Thread.sleep(1000);
			System.out.println("wait");
		}
		Thread.sleep(2000);
		System.out.println(elementExists("//*[@type='checkbox' and @onclick='checkAll(this)']").isSelected());
		try {
			if (!elementExists("//*[@type='checkbox' and @onclick='checkAll(this)']").isSelected()) 
			{
				elementExists("//*[@type='checkbox' and @onclick='checkAll(this)']").click();
				System.out.println("Select All checkbox is checked");
			} 
			Thread.sleep(2000);
			elementExists("//*[@id='divFirst']/div[2]/a").click();
			System.out.println("Click on Next Button");
		} catch (Exception e) {
			System.out.println("Error Occuredd");
		}
	}

	public static void SelectConditionsForReleaseResults() throws InterruptedException {

		boolean scoreischecked=driver.findElement(By.xpath("//*[@id='chkShowScore']")).isSelected();
		System.out.println(scoreischecked);
		boolean pointischecked=driver.findElement(By.xpath("//*[@id='chkShowPoints']")).isSelected();
		System.out.println(pointischecked);

		try {
			if (!driver.findElement(By.xpath("//*[@id='chkShowScore']")).isSelected()) 
			{
				driver.findElement(By.xpath("//*[@id='chkShowScore']")).click();
				System.out.println("Checkbox Display Score is checked");  }

			else if(!driver.findElement(By.xpath("//*[@id='chkShowPoints']")).isSelected())
			{
				driver.findElement(By.xpath("//*[@id='chkShowPoints']")).click();
				System.out.println("Checkbox Display Points is checked");  

			} 

		} catch (Exception e) {
			System.out.println("Error Occured while selecting the checkboxes");
		}
	}

	public static void ClickOnPostETReleaseResult() {
		elementExists("//*[@id='divSecond']/div/a[4]").click();
		System.out.println("Clicked on Post to ET Portal button");
		QuestionUtil.WaitForProgressBar();
	}

	public static void MessageVerification() {
		if (elementExists("//*[@id='ctl00_ctl00_ConfirmMsgLbl']").getText().contains("Your exam results have been posted to the exam taker portal.")
				&& elementExists("//*[@id='ctl00_ctl00_ConfirmMsgLbl']").getText().contains("Exam Taker(s) can access their results.") )
			System.out.println("Exam Results are posted to ET Portal");
		else {
			Assert.fail("Exam Results are not posted to ET Portal");
		}

	}

	//Strength and Opportunity Reports
	public static void ClickOnSandOReport() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement ReportingLink=driver.findElement(By.xpath(OR.getProperty("ReportingLink")));
		WebElement SandOReportLink=driver.findElement(By.xpath(OR.getProperty("SandOReports")));
		wait.until(ExpectedConditions.elementToBeClickable(ReportingLink));
		Actions  actions  = new Actions(driver);
		actions.moveToElement(ReportingLink).moveToElement(SandOReportLink).click().build().perform();
		Thread.sleep(8000);
		System.out.println("Clicked On Strengths and Opportunities label from Reporting/Scoring Dropdown");
	}

	public static void SelectETsForSandOReports() throws InterruptedException {

		while(!elementExists("//*[@class='listingsection']").isDisplayed()){
			Thread.sleep(1000);
			System.out.println("wait");
		}
		Thread.sleep(4000);
		System.out.println(elementExists("//*[@type='checkbox' and @onclick='checkAll(this)']").isSelected());
		try {
			if (!elementExists("//*[@type='checkbox' and @onclick='checkAll(this)']").isSelected()) 
			{
				elementExists("//*[@type='checkbox' and @onclick='checkAll(this)']").click();
				System.out.println("Select All checkbox is checked");
			} 
			elementExists("//*[@id='divFirst']/div[2]/a").click();
			System.out.println("Click on Next Button");
		} catch (Exception e) {
			System.out.println("Error Occuredd while selecting Ets");
		}

	}

	public static void SelectConditionsForSandOReports() throws InterruptedException {

		boolean scoreischecked=driver.findElement(By.xpath("//*[@id='chkShowScore']")).isSelected();
		System.out.println(scoreischecked);
		boolean pointischecked=driver.findElement(By.xpath("//*[@id='chkShowPoints']")).isSelected();
		System.out.println(pointischecked);

		try {
			if (!driver.findElement(By.xpath("//*[@id='chkShowScore']")).isSelected()) 
			{
				driver.findElement(By.xpath("//*[@id='chkShowScore']")).click();
				System.out.println("Checkbox Display Score is checked");  }

			else if(!driver.findElement(By.xpath("//*[@id='chkShowPoints']")).isSelected())
			{
				driver.findElement(By.xpath("//*[@id='chkShowPoints']")).click();
				System.out.println("Checkbox Display Points is checked");  

			} 

		} catch (Exception e) {
			System.out.println("Error Occured while selecting the checkboxes");
		}
	}

	public static void ClickOnDownloadButtonSandOReport() throws InterruptedException {
		elementExists("//*[@id='ctl00_ctl00_ContentPlaceHolder1_ContentPlaceHolder1_btnDownload']").click();
		QuestionUtil.WaitForProgressBar();
		System.out.println("Clicked on Download Button for S and O Report");
		Thread.sleep(6000);

	}



	//Category Report
	public static void ClickOnCategoryReport() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement ReportingLink=driver.findElement(By.xpath(OR.getProperty("ReportingLink")));
		WebElement CategoryLink=driver.findElement(By.xpath(OR.getProperty("CategoryReport")));
		wait.until(ExpectedConditions.elementToBeClickable(ReportingLink));
		Actions  actions  = new Actions(driver);
		actions.moveToElement(ReportingLink).moveToElement(CategoryLink).click().build().perform();
		Thread.sleep(8000);
		System.out.println("Clicked On Category Reports label from Reporting/Scoring Dropdown");
	}

	public static void SelectOptionForCategoryReports() throws InterruptedException {

		while(!elementExists("//*[@id='opener']").isDisplayed()){
			Thread.sleep(1000);
			System.out.println("wait");
		}
		Thread.sleep(4000);
		System.out.println(elementExists("//*[@id='rbSummary']").isSelected());
		try {
			if (!elementExists("//*[@id='rbSummary']").isSelected()) 
			{
				elementExists("//*[@id='rbSummary']").click();
				System.out.println("Summary Radio button is checked in Category Report");
			} 
			elementExists("//a[contains (text(), 'View Report')]").click();
			System.out.println("Click on View Report Button");
			QuestionUtil.WaitForProgressBar();
		} catch (Exception e) {
			System.out.println("Error Occuredd while selecting Options in Category Report");
		}
		Thread.sleep(2000);

	}

	public static void ActualCatReportValues(){

		int size = driver.findElements(By.xpath("//*[@id='grdResults']/tbody/tr")).size();// get no. of records
		System.out.println(size);
		actualRecord=new String[size][6];
		for (int i=0; i<size;i++ ){
			actualRecord[i][0]=driver.findElement(By.xpath("//*[@id='grdResults']/tbody/tr["+(i+1)+"]/td[1]")).getText().trim();//Category Name
			actualRecord[i][1]=driver.findElement(By.xpath("//*[@id='grdResults']/tbody/tr["+(i+1)+"]/td[2]")).getText().trim();//No. of Items
			actualRecord[i][2]=driver.findElement(By.xpath("//*[@id='grdResults']/tbody/tr["+(i+1)+"]/td[3]")).getText().trim();//Correct
			actualRecord[i][3]=driver.findElement(By.xpath("//*[@id='grdResults']/tbody/tr["+(i+1)+"]/td[4]")).getText().trim();//%Correct
			actualRecord[i][4]=driver.findElement(By.xpath("//*[@id='grdResults']/tbody/tr["+(i+1)+"]/td[5]")).getText().trim();//Incorrect
			actualRecord[i][5]=driver.findElement(By.xpath("//*[@id='grdResults']/tbody/tr["+(i+1)+"]/td[6]")).getText().trim();//%Correct
			System.out.println("Record: Category Name="+actualRecord[i][0]+", No. of Items="+actualRecord[i][1]+", Correct="+actualRecord[i][2]+", %Correct="+actualRecord[i][3]+ ", Incorrect="+actualRecord[i][4]+", %Incorrect="+actualRecord[i][5]);
		}
	}

	public static void CompareCatReportValues(){

		reportXls = new Xls_Reader(System.getProperty("user.dir")+"//src//Import//ExpectedValues.xlsx");
		String categoryValue=null;
		for(int i=0;i<actualRecord.length;i++){
			//categoryValue="asd.123";
			categoryValue=reportXls.getCellData(CONFIG.getProperty("CategoryReportSheet"), "Category", i+2);
			Pattern p= Pattern.compile("[a-zA-Z]+");
			boolean flag1= p.matcher(categoryValue).find();
			if(!flag1)
				categoryValue=categoryValue.contains(".") ?categoryValue.substring(0,categoryValue.lastIndexOf(".")):categoryValue;
				if(actualRecord[i][0].equalsIgnoreCase(categoryValue)
						&& actualRecord[i][1].equalsIgnoreCase(reportXls.getCellData(CONFIG.getProperty("CategoryReportSheet"), "Items", i+2).substring(0, reportXls.getCellData(CONFIG.getProperty("CategoryReportSheet"), "Items", i+2).lastIndexOf(".")))
						&& actualRecord[i][2].equalsIgnoreCase(reportXls.getCellData(CONFIG.getProperty("CategoryReportSheet"), "Correct", i+2).substring(0, reportXls.getCellData(CONFIG.getProperty("CategoryReportSheet"), "Correct", i+2).lastIndexOf(".")))
						&& actualRecord[i][3].equalsIgnoreCase(reportXls.getCellData(CONFIG.getProperty("CategoryReportSheet"), "CorrectPercent", i+2).substring(0, reportXls.getCellData(CONFIG.getProperty("CategoryReportSheet"), "CorrectPercent", i+2).lastIndexOf(".")))
						&& actualRecord[i][4].equalsIgnoreCase(reportXls.getCellData(CONFIG.getProperty("CategoryReportSheet"), "Incorrect", i+2).substring(0, reportXls.getCellData(CONFIG.getProperty("CategoryReportSheet"), "Incorrect", i+2).lastIndexOf(".")))
						&& actualRecord[i][5].equalsIgnoreCase(reportXls.getCellData(CONFIG.getProperty("CategoryReportSheet"), "IncorrectPercent", i+2).substring(0, reportXls.getCellData(CONFIG.getProperty("CategoryReportSheet"), "IncorrectPercent", i+2).lastIndexOf(".")))){
					System.out.println("Record: Category Name="+actualRecord[i][0]+", No. of Items="+actualRecord[i][1]+", Correct="+actualRecord[i][2]+", %Correct="+actualRecord[i][3]+ ", Incorrect="+actualRecord[i][4]+", %Incorrect="+actualRecord[i][5]+" data verified successfully!");
				}else{
					System.out.println("Record: Category Name="+actualRecord[i][0]+", No. of Items="+actualRecord[i][1]+", Correct="+actualRecord[i][2]+", %Correct="+actualRecord[i][3]+ ", Incorrect="+actualRecord[i][4]+", %Incorrect="+actualRecord[i][5]+" data verification failed!");
					flag=true;
				}
		}

		if(flag){
			Assert.fail("Category Reports Actual values doesnt macth with Expected values.");
		}
	}

	//Release Results- Send Email to ETs
	public static void ClickOnSendEmailOptionReleaseResult() {
		elementExists("//*[@id='divSecond']/div/a[3]").click();

	}

	public static void SendEmailWithConditionsReleaseResult()  {
		int count=0;
		while(!elementExists("//*[@id='rbAllETs']").isDisplayed()) {
			waitInSeconds(1);
			count++;
			System.out.println("wait..");
			if(count>10) break;
		}
		elementExists("//*[@id='rbAllETs']").click();
		System.out.println("Selected the Radio button for All Exam Takers");
		elementExists("//a[contains (text(), 'Send Link With Email') and contains(@class, 'keybutton')]").click();
		System.out.println("Clicked on Send Link With Email Button");
		QuestionUtil.WaitForProgressBar();
	}

	public static void MessageEmailVerificationReleaseResult() {
		if (elementExists("//*[@id='ctl00_ctl00_ConfirmMsgLbl']").getText().contains("email(s) were successfully sent") 
				&& elementExists("//*[@id='ctl00_ctl00_ConfirmMsgLbl']").getText().contains("Exam Taker(s) can access their results"))
			System.out.println("Exam Results are posted to ET Portal by Email");
		else {
			Assert.fail("Exam Results are not posted to ET Portal by Email");
		}				

	}

}

