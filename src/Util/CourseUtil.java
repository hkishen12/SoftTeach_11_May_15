package Util;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Base.TestBase;


public class CourseUtil extends TestBase {

	public static void clickOnCourseTab() {
		WebElement adminLink=null;
		WebElement CourseLink=null;
		Actions  actions  = new Actions(driver);
		try{
			WebDriverWait wait = new WebDriverWait(driver, 60);
			adminLink=driver.findElement(By.xpath(OR.getProperty("AdminLink1")));
			CourseLink=driver.findElement(By.xpath(OR.getProperty("CourseLink")));
			wait.until(ExpectedConditions.elementToBeClickable(adminLink));
			actions.moveToElement(adminLink).moveToElement(CourseLink).click().build().perform();
		}catch(ElementNotVisibleException e){
			driver.navigate().refresh();
			waitInSeconds(2);
			WebDriverWait wait = new WebDriverWait(driver, 60);
			adminLink=driver.findElement(By.xpath(OR.getProperty("AdminLink1")));
			CourseLink=driver.findElement(By.xpath(OR.getProperty("CourseLink")));
			wait.until(ExpectedConditions.elementToBeClickable(adminLink));
			actions.moveToElement(adminLink).moveToElement(CourseLink).click().build().perform();
		}
		WaitForProgressBar();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='coursegrid']/tbody/tr[1]/td[1]/a")));
		waitInSeconds(10);
	}

	public static void createNewCourse(String CourseID, String CourseName, String MasCourseName ) throws InterruptedException {
		wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(By
				.xpath(OR.getProperty("CreateCourseButton"))));
		
		try{
		((JavascriptExecutor) driver).executeScript("showCreateCourse()");
		}catch(Exception e){
			driver.navigate().refresh();
		}
		
		elementExists(OR.getProperty("CreateCourseButton")).click();
		System.out.println("Clicked on Create New Course Button");
		waitInSeconds(2);
		try{
			if(!elementExists("//*[@id='ui-id-6']").isDisplayed()){
				elementExists(OR.getProperty("CreateCourseButton")).click();
				System.out.println("Clicked on Create New Course Button");
				waitInSeconds(2);
			}
		}catch(TimeoutException e){
			//do nothing
		}
		//Switch to Iframe
		WebElement frameSwitch = elementExists("//*[@id='fEditCourses']");
		driver.switchTo().frame(frameSwitch);
		Thread.sleep(3000);

		driver.findElement(By.xpath("//*[@id='courseid']")).sendKeys(CourseID);
		driver.findElement(By.xpath("//*[@id='name']")).sendKeys(CourseName);
		System.out.println("Course ID & Name are Entered");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='startdate']")).sendKeys("06/09/2014 12:00 AM");
		driver.findElement(By.xpath("//*[@id='enddate']")).sendKeys("09/15/2017 12:00 AM");
		System.out.println("Start & End dates are Entered");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='mastercourseidInput']")).sendKeys(MasCourseName);
		System.out.println("Master Course Name is Entered");
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='saveBtn']")));
		
		elementExists("//*[@id='saveBtn']").click();
		waitInSeconds(2);
		try{
			wait = new WebDriverWait(driver, 10);
			if(wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='saveBtn']")))!=null){
				elementExists("//*[@id='saveBtn']").click();
			}
		}catch(TimeoutException e){
			//do nothing
		}

		System.out.println("Course is Saved");
		Thread.sleep(3000);

		driver.switchTo().defaultContent();// Iframe is Switched to Main Again 

		String confirmMsg = driver.findElement(By.xpath("//*[@id='ctl00_ConfirmMsgLbl']")).getText().trim();
		System.out.println(confirmMsg);

		if(confirmMsg.equalsIgnoreCase("Course Successfully Created")) {
			System.out.println("The Newly Created Course having Course ID is ->" + CourseID);
		}else { 
			Assert.fail("Course is not Created");
		}

	}

	public static void searchCoursebyID(String CourseID) throws InterruptedException {
		Thread.sleep(3000);
		Select selectopt = new Select(driver.findElement(By.xpath("//*[@id='ActiveComboBox']")));
		Thread.sleep(2000);

		selectopt.selectByVisibleText("All");
		System.out.println("All Value is Selected from the Dropdown");

		elementExists("//*[@id='FilterByTextBox']").sendKeys(CourseID);
		System.out.println("CourseId is Entered for Search");

		elementExists("//*[@id='ctl00_ContentPlaceHolder1_SearchforStudIDButton']").click();
		System.out.println("Clicked on Search Button");
		WaitForProgressBar();
		waitInSeconds(5);

		String ScourseId = elementExists("//*[@id='coursegrid']/tbody/tr[1]/td[1]/a").getText().trim();

		if (ScourseId.equalsIgnoreCase(CourseID)) {
			System.out.println("CourseID ->"+  ScourseId + " Successfully Searched" );
		} else 
			Assert.fail("CourseID ->"+ ScourseId + "is not Searched");
	}

	public static void assignETtoCourse() throws InterruptedException {
		elementExists("//*[@id='coursegrid']//*[@title='Assign exam takers to this course.']").click();
		WaitForProgressBar();
		waitInSeconds(7);
		WaitForProgressBar();
		WebElement frameSwitch = driver.findElement(By.xpath("//*[@id='assignCourses']/iframe"));
		driver.switchTo().frame(frameSwitch);

		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By
				.xpath("//*[@id='tree1']/ul/li[1]/span/span[4]/a/img")));


		elementExists("//*[@id='tree1']/ul/li[1]/span/span[4]/a/img").click();
		elementExists("//*[@id='form1']//a[text()='Update']").click();
		WaitForProgressBar();
		waitInSeconds(3);
		WaitForProgressBar();
		driver.switchTo().defaultContent();

		if(elementExists("//*[@id='ctl00_ConfirmMsgLbl']").getText().contains("Assigned ETs successfuly updated")){
			System.out.println("ETs successfuly  Assigned to Course");
		} else {
			Assert.fail("ETs Not Assigned to Course");
		}
	}

	public static void courseClickonExport() throws InterruptedException {

		try {
			elementExists("//*[@id='toolsTopholder']//*[@src='../Icons/csv.png']").click();
			System.out.println("Export to CSV");
			//Process p = Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\src\\Import\\openingCourses.exe");
			Thread.sleep(3000);	

			elementExists("//*[@id='toolsTopholder']//*[@src='../Icons/excel.gif']").click();
			System.out.println("Export to xls");
			//Process p1 = Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\src\\Import\\openingCourses.exe");
			Thread.sleep(3000);

		}catch (Exception e) {
			System.out.println("Export is not working");
		}
	}

	public static void courseEdit() throws InterruptedException {
		elementExists("//*[@id='coursegrid']/tbody/tr/td[1]/a").click();
		Thread.sleep(3000);
		WebElement iframeSwitch = driver.findElement(By.xpath("//*[@id='fEditCourses']"));
		driver.switchTo().frame(iframeSwitch);
		Thread.sleep(2000);

		elementExists("//*[@id='mastercourseidInput']").clear();
		Thread.sleep(2000);
		elementExists("//*[@id='mastercourseidInput']").sendKeys("Edited Automation Course");
		System.out.println("Edited the Master Course Field");
		elementExists("//*[@id='saveBtn']").click();
		Thread.sleep(3000);
		driver.switchTo().defaultContent();

		if(elementExists("//*[@id='ctl00_ConfirmMsgLbl']").getText().equalsIgnoreCase("Saved")) {
			System.out.println("Course Edited successfuly");
		}else {
			Assert.fail("Course is not Edited");	
		}
	}

	public static void exportCoursedata() throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("javascript:openExportCourseData(this)");
		//	elementExists("//*[@id='coursegrid']//*[@title='Export Course Data']").click();
		Thread.sleep(5000);
		WaitForProgressBar();
		if (elementExists("//*[@id='ui-id-7']").getText().equalsIgnoreCase("Export Enrolled Exam Takers")) {
			System.out.println("Export Enrolled Exam Takers popup is opened");
		} else {
			Assert.fail("Export Enrolled Exam Takers is not working");
		}

		WebElement iframeSwitch = driver.findElement(By.xpath("//*[@id='exportCourses']/iframe"));
		driver.switchTo().frame(iframeSwitch);
		Thread.sleep(2000);
		try {
			elementExists("//*[@id='toolsTopholder']//*[@src='../Icons/csv.png']").click();
			System.out.println("Export to CSV");
			//	Process p = Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\src\\Import\\Handling Popup.exe");
			Thread.sleep(3000);	

			elementExists("//*[@id='toolsTopholder']//*[@src='../Icons/excel.gif']").click();
			System.out.println("Export to xls");
			//	Process p1 = Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\src\\Import\\Handling Popup.exe");
			Thread.sleep(3000);

			//	elementExists("//*[@id='toolsTopholder']//*[@src='../Icons/word.gif']").click();
			//	System.out.println("Export to Word");
			//	Process p2 = Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\src\\Import\\Handling Popup.exe");
			Thread.sleep(2000);

		}catch (Exception e) {
			System.out.println("Export is not working");
		}

		Thread.sleep(2000);
		//elementExists("//*[@class='keybutton btnDone' AND text()='Close']").click();
		driver.switchTo().defaultContent();
		elementExists("html/body/div[10]/div[1]/a/span/img").click();
		System.out.println("Clicking on close model");

	}

	public static void CleanUpEditdates() throws InterruptedException {
		elementExists("//*[@id='coursegrid']/tbody/tr/td[1]/a").click();
		Thread.sleep(3000);
		WebElement iframeSwitch = driver.findElement(By.xpath("//*[@id='fEditCourses']"));
		driver.switchTo().frame(iframeSwitch);

		driver.findElement(By.xpath("//*[@id='startdate']")).clear();
		driver.findElement(By.xpath("//*[@id='startdate']")).sendKeys("06/09/2012 12:00 AM");
		System.out.println("Start dates is Entered to make course unavaliable");
		driver.findElement(By.xpath("//*[@id='enddate']")).clear();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='enddate']")).sendKeys("09/15/2012 12:00 AM");
		System.out.println("End dates is Entered to make course unavaliable");
		elementExists("//*[@id='saveBtn']").click();
		System.out.println("Course is Saved");
		Thread.sleep(3000);


		driver.switchTo().defaultContent();

		if(elementExists("//*[@id='ctl00_ConfirmMsgLbl']").getText().equalsIgnoreCase("Saved")) {
			System.out.println("Cleanup Course is Edited successfuly");
		}else {
			Assert.fail("Cleanup Course is not Edited");	
		}
	}

}


