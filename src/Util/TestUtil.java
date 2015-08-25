package Util;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;

import Base.TestBase;

public class TestUtil extends TestBase
{

	public static void doLogin() throws InterruptedException{
		if(elementExists("//*[@id='emExamsoftIdLoginLink']")!=null){//check SAML is on/off
			System.out.println("SAML link is present, expanding link!");
			elementExists("//*[@id='emExamsoftIdLoginLink']").click();
			waitInSeconds(2);
		}else
			System.out.println("SAML link is not present!");
		getObjectxpath("Username_input").clear();
		getObjectxpathInput("Username_input", "UserName_String");
		//APP_LOGS.debug("Enter the Username");
		getObjectid("User_password_input").clear();
		getObjectidInput("User_password_input","Password_string");
		//APP_LOGS.debug("Enter the password");
		//Changes to support local server
		List<WebElement> buttonlist=driver.findElements(By.xpath("//*[@value='Login']"));
		if(buttonlist.size()!=0){
			buttonlist.get(buttonlist.size()-1).click();
		}
		else{
			Assert.fail("login button not displayed with:xpath://*[@value='Login']");
		}
		Thread.sleep(5000L);
		checkNotification();
		/*	//Assertion 
		WebElement Loginuser = getObjectxpath("Username_link");
		String UserName_string = Loginuser.getText();
		System.out.println("name:" + UserName_string);
		System.out.println(Expected_string.getProperty("LoggedUsername_String"+ "test"));

		ArrayList<String> ls = new ArrayList<String>();
		ls.add(Expected_string.getProperty("LoggedUsername_String_Uidemodeux").trim());
		ls.add(Expected_string.getProperty("LoggedUsername_String_QA").trim());

		if (ls.contains(UserName_string.trim()))
			System.out.println("Login user string matching");
		else
			Assert.fail("Login user string is not matching"+"Actual:"+UserName_string);*/

	}

	public static void checkNotification()
	{
		if(elementExists("//*[@id='notificationAck']")!=null){
			if(elementExists("//*[@id='notificationAck']").isDisplayed()){
				elementExists("//*[@id='notificationAck']").click();
				WaitForProgressBar();
				elementExists("//div[13]/div[1]/a//img[contains(@src,'x.png')]").click();
			}
		}
	}

	public static void doMyPreference() throws InterruptedException
	{
		WebElement acctbar=driver.findElement(By.xpath(OR.getProperty("Username_link")));
		WebElement myPreferencesLink=driver.findElement(By.xpath("//*[@id='accountbar']//a[text()='My Preferences']"));
		new Actions(driver).moveToElement(acctbar).moveToElement(myPreferencesLink).click().build().perform();
		WaitForProgressBar();
	}

	public static void doLogin(String username, String password) throws InterruptedException{
		getObjectxpath("Username_input").clear();
		// enter username
		try{
			driver.findElement(By.xpath(OR.getProperty("Username_input"))).sendKeys(username);
		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" +"Username_input" );
		}
		//ENTER PASSWORD
		getObjectid("User_password_input").clear();
		try{
			driver.findElement(By.id(OR.getProperty("User_password_input"))).sendKeys(password);
		}catch(Throwable t)
		{
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" + "User_password_input");
		}

		List<WebElement> buttonlist=driver.findElements(By.xpath("//*[@value='Login']"));
		if(buttonlist.size()!=0){
			buttonlist.get(buttonlist.size()-1).click();
		}
		else{
			Assert.fail("login button not displayed with:xpath://*[@value='Login']");
		}
		Thread.sleep(5000L);
	}



	public static void checkSuiteSkip(String TestSuiteName) throws Exception
	{
		boolean flag=false;
		initialize();
		System.out.println("***************************************************************************");
		APP_LOGS.debug("Checking Runmode of suite '"+TestSuiteName+"'");
		if(CONFIG_ENV.getProperty("runmode_fileType").equalsIgnoreCase("xml")){
			flag=isSuiteRunnable_XML(doc,TestSuiteName);
			APP_LOGS.debug("Runmode of '" +TestSuiteName+"' is "+ flag);
		}else{
			flag=isSuiteRunnable(suiteXls,TestSuiteName);
			APP_LOGS.debug("Runmode of '" +TestSuiteName+"' is "+ flag);
		}
		if(!flag)
		{
			APP_LOGS.debug("Skipped '" +TestSuiteName +" ' as the runmode was set to NO");
			System.out.println("***************************************************************************");
			throw new SkipException("Runmode of '" +TestSuiteName + "' is set to 'NO', hence skipping all tests in Suite!");
		}
		System.out.println("***************************************************************************");
	}

	public static void checkTestSkip(String TestSuiteName, String TestcaseName) throws Exception
	{
		boolean flag=false;
		System.out.println("............................................................................................................................................");
		APP_LOGS.debug("Checking Runmode of " +TestcaseName);
		if(CONFIG_ENV.getProperty("runmode_fileType").equalsIgnoreCase("xml")){
			flag=isTestCaseRunnable_XML(doc,TestSuiteName ,TestcaseName );
			APP_LOGS.debug("Runmode of '" +TestcaseName+"' is "+ flag);
		}else{
			flag=isTestCaseRunnable(suiteXls,TestSuiteName ,TestcaseName );
			APP_LOGS.debug("Runmode of '" +TestcaseName+"' is "+ flag );
		}
		if(!flag)
		{
			APP_LOGS.debug("Skipped '" +TestcaseName +"' as the runmode was set to NO");
			throw new SkipException("Runmode of '" +TestcaseName +"' is set to 'NO', hence Skipping it!");
		}
	}
	//update results for a particular data set	
	public static void reportDataSetResult(Xls_Reader xls, String testsheeteName, int rowNum,String result)
	{	
		xls.setCellData(testsheeteName, "Results", rowNum, result);
		//.setCellData(testCaseName, "Results", rowNum, result);
	}


	public static void doClickCommunitylink() throws Exception
	{			
		waitInSeconds(5);
		getObjectxpath("Community_xpath").click();   
		Thread.sleep(5000);

		driver.switchTo().frame("gsfn-iframe-0");
		Thread.sleep(3000);
		WebElement Tabname = getObjectxpath("Worldwidetest_xpath");
		String string = Tabname.getText();
		comparestringtext(string,"ExamSoftWorldwide_string");

	}

	public static void doCheckShareanIdeaTab() throws Exception
	{			
		Thread.sleep(500L);
		getObjectxpath("ShareanIdea_xpath").click();
		Thread.sleep(1000L);
		WebElement Tabname = getObjectxpath("ShareanIdea_xpath");
		String string = Tabname.getText();
		comparestringtext(string,"ShareanIdea_string");
	}

	public static void doCheckReportaProblemTab() throws Exception
	{			
		Thread.sleep(500L);
		getObjectxpath("ReportaProblem_xpath").click();
		Thread.sleep(1000L);
		WebElement Tabname = getObjectxpath("ReportaProblem_xpath");
		String string = Tabname.getText();
		comparestringtext(string,"ReportaProblem_string");

	}

	public static void doCheckGivePraiseTab() throws Exception
	{			
		Thread.sleep(500L);
		getObjectxpath("GivePraise_xpath").click();
		Thread.sleep(1000L);
		WebElement Tabname = getObjectxpath("GivePraise_xpath");
		String string = Tabname.getText();
		comparestringtext(string,"GivePraise_string");

	}

	public static void CheckAskaQuestionTab() throws Exception
	{			
		Thread.sleep(500L);
		getObjectxpath("AskaQuestion_xpath").click();
		Thread.sleep(1000L);
		WebElement Tabname = getObjectxpath("AskaQuestion_xpath");
		String string = Tabname.getText();
		comparestringtext(string,"AskaQuestion");
		Thread.sleep(1000L);

		driver.switchTo().defaultContent();
		getObjectxpath("CommunityClose_xpath").click(); 
	}



	public static void doCheckTrainingVideoinQuestions() throws Exception
	{			
		Set<String> winIds = driver.getWindowHandles();
		System.out.println("Total browsers-> "+winIds.size());
		Iterator<String> it= winIds.iterator();
		System.out.println(it.next());

		Thread.sleep(1000L);
		//getObjectxpath("Questions_xpath").click();
		driver.findElement(By.linkText("Questions")).click();
		System.out.println("click on questions tab ");

		WebDriverWait wait = new WebDriverWait(driver,9000);
		String loadimage="//*[@id='loadinganimationwrapper']/tbody/tr/td/div/span";
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(loadimage)));
		System.out.println("loadinganimationwrapper is displayed");

		Thread.sleep(1000L);
		getObjectxpath("QVideoimg_xpath").click();
		System.out.println("click on questions tab ");

		//New window  
		Thread.sleep(20000);
		winIds = driver.getWindowHandles();
		System.out.println("Total windows->"+ winIds.size());
		it= winIds.iterator();
		String mainWindowId = it.next();
		String tabWinId = it.next();
		System.out.println("mainWindowId -> " +mainWindowId);
		System.out.println("tabWinId - >" +tabWinId);
		driver.switchTo().window(tabWinId);

		//Check All Tab
		Thread.sleep(3000L);
		WebElement All = getObjectxpath("Alltab_xpath");
		String Alltab = All.getText();
		System.out.println("All Tab ::" +Alltab);
		comparestringtext(Alltab,"AllTab_string");

		//Check videos tab
		Thread.sleep(3000L);
		WebElement videos = getObjectxpath("VideosTab_xpath");
		String videostab = videos.getText();
		System.out.println("videos tab ::" +videostab);
		comparestringtext(videostab,"Videostab_string");

		//Check Quick Reference Guides tab
		Thread.sleep(3000L);
		WebElement Guides = getObjectxpath("QuickreferaceGuides_xpath");
		String Guidestab = Guides.getText();
		System.out.println("Quick Reference Guides ::" +Guidestab);
		comparestringtext(Guidestab,"QuickReferenceGuides_string");



		driver.close();
		//switch to new window
		driver.switchTo().window(mainWindowId);
	}


	public static void doCheckTrainingVideoinAssessments() throws Exception
	{			
		Set<String> winIds = driver.getWindowHandles();
		System.out.println("Total browsers-> "+winIds.size());
		Iterator<String> it= winIds.iterator();
		System.out.println(it.next());

		Thread.sleep(1000L);
		getObjectxpath("Assessments_xpath").click();
		System.out.println("click on questions tab ");

		WebDriverWait wait = new WebDriverWait(driver,9000);
		String loadimage="//*[@id='loadinganimationwrapper']/tbody/tr/td/div/span";
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(loadimage)));
		System.out.println("loadinganimationwrapper is displayed");

		Thread.sleep(1000L);
		getObjectxpath("QVideoimg_xpath").click();
		System.out.println("click on questions tab ");

		//New window  
		Thread.sleep(20000);
		winIds = driver.getWindowHandles();
		System.out.println("Total windows->"+ winIds.size());
		it= winIds.iterator();
		String mainWindowId = it.next();
		String tabWinId = it.next();
		System.out.println("mainWindowId -> " +mainWindowId);
		System.out.println("tabWinId - >" +tabWinId);
		driver.switchTo().window(tabWinId);

		//Check All Tab
		Thread.sleep(3000L);
		WebElement All = getObjectxpath("Alltab_xpath");
		String Alltab = All.getText();
		System.out.println("All Tab ::" +Alltab);
		comparestringtext(Alltab,"AllTab_string");

		//Check videos tab
		Thread.sleep(3000L);
		WebElement videos = getObjectxpath("VideosTab_xpath");
		String videostab = videos.getText();
		System.out.println("videos tab ::" +videostab);
		comparestringtext(videostab,"Videostab_string");

		//Check Quick Reference Guides tab
		Thread.sleep(3000L);
		WebElement Guides = getObjectxpath("QuickreferaceGuides_xpath");
		String Guidestab = Guides.getText();
		System.out.println("Quick Reference Guides ::" +Guidestab);

		driver.close();
		//switch to new window
		driver.switchTo().window(mainWindowId);
	}


	public static void doCheckTrainingVideoinCategories() throws Exception
	{			
		Set<String> winIds = driver.getWindowHandles();
		System.out.println("Total browsers-> "+winIds.size());
		Iterator<String> it= winIds.iterator();
		System.out.println(it.next());

		Thread.sleep(1000L);
		getObjectxpath("Categories_xpath").click();
		System.out.println("click on questions tab ");

		WebDriverWait wait = new WebDriverWait(driver,9000);
		String loadimage="//*[@id='loadinganimationwrapper']/tbody/tr/td/div/span";
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(loadimage)));
		System.out.println("loadinganimationwrapper is displayed");

		Thread.sleep(1000L);
		getObjectxpath("QVideoimg_xpath").click();
		System.out.println("click on questions tab ");

		//New window  
		Thread.sleep(20000);
		winIds = driver.getWindowHandles();
		System.out.println("Total windows->"+ winIds.size());
		it= winIds.iterator();
		String mainWindowId = it.next();
		String tabWinId = it.next();
		System.out.println("mainWindowId -> " +mainWindowId);
		System.out.println("tabWinId - >" +tabWinId);
		driver.switchTo().window(tabWinId);

		//Check All Tab
		Thread.sleep(3000L);
		WebElement All = getObjectxpath("Alltab_xpath");
		String Alltab = All.getText();
		System.out.println("All Tab ::" +Alltab);
		comparestringtext(Alltab,"AllTab_string");

		//Check videos tab
		Thread.sleep(3000L);
		WebElement videos = getObjectxpath("VideosTab_xpath");
		String videostab = videos.getText();
		System.out.println("videos tab ::" +videostab);
		comparestringtext(videostab,"Videostab_string");

		//Check Quick Reference Guides tab
		Thread.sleep(3000L);
		WebElement Guides = getObjectxpath("QuickreferaceGuides_xpath");
		String Guidestab = Guides.getText();
		System.out.println("Quick Reference Guides ::" +Guidestab);
		comparestringtext(Guidestab,"QuickReferenceGuides_string");


		driver.close();
		//switch to new window
		driver.switchTo().window(mainWindowId);
	}




	public static void CheckExamsoftUniversity() throws Exception
	{			
		Set<String> winIds = driver.getWindowHandles();
		System.out.println("Total browsers-> "+winIds.size());
		Iterator<String> it= winIds.iterator();
		System.out.println(it.next());

		getObjectxpath("haticon_xpath").click();
		System.out.println("able to click on hat icon");

		//New window  
		Thread.sleep(20000);
		winIds = driver.getWindowHandles();
		System.out.println("Total windows->"+ winIds.size());
		it= winIds.iterator();
		String mainWindowId = it.next();
		String tabWinId = it.next();
		System.out.println("mainWindowId -> " +mainWindowId);
		System.out.println("tabWinId - >" +tabWinId);
		driver.switchTo().window(tabWinId);
		Assert.assertTrue(driver.getTitle().contains("ExamSoft University"));
		System.out.println("*******ExamSoft University windows is display********************");
		driver.close();
		//switch to new window;
		driver.switchTo().window(mainWindowId);
	}





	public static void doLogout() throws InterruptedException
	{
		Thread.sleep(6000);
		FirefoxProfile prof = new FirefoxProfile();
		prof.setEnableNativeEvents(true);
		WebElement acctbar=driver.findElement(By.xpath(OR.getProperty("Username_link")));
		Thread.sleep(500);
		Actions builder = new Actions(driver); 
		builder.moveToElement(acctbar).build().perform();
		Thread.sleep(6000);
		try {
			((JavascriptExecutor)driver).executeScript("document.getElementById('logoutLink').click()");
		} catch (Exception e) {
			((JavascriptExecutor)driver).executeScript("javascript:logoutmainapp()");
		}

		Thread.sleep(8000);
		APP_LOGS.debug("==================== successfully logout  ====================");
	}

	public static void takeScreenShot(String MethodName){
		System.out.println("Taking screen shot for "+MethodName);
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"\\XSLT_Reports\\output\\ScreenShot\\"+MethodName+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}