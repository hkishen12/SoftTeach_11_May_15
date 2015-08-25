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

public class PageCheckUtil extends TestBase {
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

	public static void CompareNameETReport(){
		if (elementExists("//*[@id='ctl00_ctl00_ContentPlaceHolder1_ContentPlaceHolder1_divMain']/table/tbody/tr[1]/th").getText().contains("Exam Taker Results"))
			System.out.println("ET Report Page loaded perperly");
		else { 
			Assert.fail("Error in Loading ET Reports Page");
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
	
	public static void CompareNameSummaryReport() {
		try {
			String oldTab = driver.getWindowHandle();
			elementExists("//*[@id='ctl00_ctl00_ContentPlaceHolder1_ContentPlaceHolder1_btnUse25']").click();
			ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
			newTab.remove(oldTab);
			driver.switchTo().window(newTab.get(0));
			System.out.println("New Window Title is :- "+driver.getTitle());
			System.out.println("Summary Report is Opened in New Tab/Window");
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
	
	public static void CompareNameReleaseResults(){
		if (elementExists("//*[@id='divFirst']/table/tbody/tr[1]/th").getText().contains("Release Results"))
			System.out.println("Release Result Page loaded perperly");
		else { 
			Assert.fail("Error in Loading Release Result Page");
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
	
	public static void CompareNameStrengthandOpportunityReports(){
		if (elementExists("//*[@id='divFirst']/table/tbody/tr[1]/th").getText().contains("Strengths and Opportunities"))
			System.out.println("Strengths and Opportunities Page loaded perperly");
		else { 
			Assert.fail("Error in Loading Strengths and Opportunities Page");
		}

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

	public static void CompareNameCategoryReport(){
		if (elementExists("//*[@id='ctl00_ctl00_ContentPlaceHolder1_ContentPlaceHolder1_UpdatePanel1']/table/tbody/tr[1]/th").getText().contains("Category Report"))
			System.out.println("Category Report Page loaded perperly");
		else { 
			Assert.fail("Error in Loading Category Page");
		}
	}

	

}

