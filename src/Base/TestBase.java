package Base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.AssertionFailedError;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import Util.ErrorUtil;
import Util.Xls_Reader;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;


public class TestBase {
	public static Logger APP_LOGS=null;
	public static Properties CONFIG=null;
	public static Properties OR=null;
	public static Properties Data=null;
	public static Properties CONFIG_ENV=null;
	public static Properties Expected_string=null;
	public static boolean isBrowserOpened=false;
	public static WebDriver driver =null;
	public static WebDriver dr=null;
	public static boolean isLoggedIn=false;
	public static boolean isInitalized=false;
	public static Xls_Reader suiteXls=null;
	public static String idforQSSearch=null;
	public static boolean setSkipFlag=false;
	public static WebDriverWait wait =null;
	public static Document doc;
	public static int count=0;//this field is updated while creating each question type and used in category folder to verify assigned question

	// initializing the Tests
	public static void initialize() throws Exception
	{
		// logs
		if(!isInitalized)
		{
			PropertyConfigurator.configure("src/Logs/log4j.properties");// this line is optional if log4j.properties file is kept at src
			//PropertyConfigurator.configure("src/Logs/log4j.xml"); //can use xml to configure instead of properties file.

			APP_LOGS = Logger.getLogger("ExamLogger");
			APP_LOGS.debug("Initialized log file successfully!!");

			// to suppress warning message related to log4j initialization in system
			System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");
			APP_LOGS.debug("Initialized log4j in system properly!!");

			//Reading the Excel and property file			
			APP_LOGS.debug("Loading Property files");

			// config property file
			CONFIG_ENV= new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir") +"//src//config//config_env.properties");
			CONFIG_ENV.load(ip);
			
			if(System.getenv("Environment")!=null){//this is set through Jenkins
				CONFIG_ENV.setProperty("Environment", System.getenv("Environment"));
			}
			CONFIG = new Properties();
			String fileName = "config_"+CONFIG_ENV.getProperty("Environment")+".properties";
			ip = new FileInputStream(System.getProperty("user.dir")+"//src//config//"+fileName);
			CONFIG.load(ip);

			// OR property file
			OR= new Properties();
			ip = new FileInputStream(System.getProperty("user.dir")
					+ "//src//config//OR.properties");
			OR.load(ip);

			// Data Properties file
			Data= new Properties();
			ip = new FileInputStream(System.getProperty("user.dir")
					+ "//src//config//DataFile.properties");
			Data.load(ip);
			if(System.getenv("BASE_USERNAME")!=null){
				Data.setProperty("UserName_String", System.getenv("BASE_USERNAME"));
			}
			if(System.getenv("BASE_PASSWORD")!=null){
				Data.setProperty("Password_string", System.getenv("BASE_PASSWORD"));
			}
			if(System.getenv("TEMPLATE_NOTICE")!=null){
				Data.setProperty("TemplateNotice_string", System.getenv("TEMPLATE_NOTICE"));
			}

			// Assert_String Properties file
			Expected_string= new Properties();
			ip = new FileInputStream(System.getProperty("user.dir")
					+ "//src//config//Assert_String.properties");
			Expected_string.load(ip);	
			if(System.getenv("BASE_LOGGED_USERNAME")!=null){
				Expected_string.setProperty("LoggedUsername_String", System.getenv("BASE_LOGGED_USERNAME"));
			}
			if(System.getenv("TEMPLATENOTICE_ASSERT")!=null){
				Expected_string.setProperty("TemplateNoticetext_string", System.getenv("TEMPLATENOTICE_ASSERT"));
			}

			APP_LOGS.debug("Loaded Property Files successfully");

			// LOad the Excel file		
			APP_LOGS.debug("Loading Excel files");
			suiteXls = new Xls_Reader(System.getProperty("user.dir")+"//src//com//xls//Suite.xlsx");
			APP_LOGS.debug("Loaded Excel Suite Files successfully");
			//-----------------------------------------------------------------------
			try {
				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
				//Using factory get an instance of document builder
				DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
				//parse using builder to get DOM representation of the XML file
				doc = docBuilder.parse (new File(System.getProperty("user.dir")+"//src//com//xls//Suite.xml"));


			}catch (SAXParseException err) {
				System.out.println ("** Parsing error" + ", line " + err.getLineNumber () + ", uri " + err.getSystemId ());
				System.out.println(" " + err.getMessage ());
			}catch(SAXException se) {
				se.printStackTrace();
			}catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}
		//-----------------------------------------------------------------------

		isInitalized=true;

	}

	// selenium Webdriver open a browser if its not opened		
	public static void openBrowser()
	{
		if(!isBrowserOpened)
		{
			FirefoxProfile profile = new FirefoxProfile();
			DesiredCapabilities dc=new DesiredCapabilities();
			dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,UnexpectedAlertBehaviour.ACCEPT);
			dc.setCapability(FirefoxDriver.PROFILE, profile);

			profile.setEnableNativeEvents(true);

			profile.setPreference("browser.download.folderList", 2);
			profile.setPreference("browser.download.manager.showWhenStarting", false);
			profile.setPreference("browser.download.dir", System.getProperty("user.dir")+"\\src\\Download");
			profile.setPreference("browser.download.downloadDir", System.getProperty("user.dir")+"\\src\\Download");
			profile.setPreference("browser.download.defaultFolder", System.getProperty("user.dir")+"\\src\\Download");
			profile.setPreference("browser.download.manager.closeWhenDone", true);
			profile.setPreference("pdfjs.disabled", true);
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/zip,text/csv,application/msword,application/excel,application/pdf," +
					"application/vnd.ms-excel,application/msword,application/unknown,application/vnd.openxmlformats-officedocument.wordprocessingml.document");

			if(CONFIG.getProperty("browserType").equalsIgnoreCase("Firefox") ||CONFIG.getProperty("browserType").equalsIgnoreCase("FF") ){
				driver = new FirefoxDriver(profile);
			}
			else if (CONFIG.getProperty("browserType").equals("InternetExplorer")||CONFIG.getProperty("browserType").equalsIgnoreCase("IE")){
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\src\\drivers\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
			}
			else if (CONFIG.getProperty("browserType").equals("GoogleChrome")||CONFIG.getProperty("browserType").equalsIgnoreCase("CHROME")){
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\drivers\\chromedriver.exe");
				driver = new ChromeDriver();
			}
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			isBrowserOpened=true;
			driver.get(CONFIG.getProperty("baseUrl"));
			APP_LOGS.debug("Selecting the Browser");

		}
	}	


	//Navigate to URL
	public static String navigate(String URL_Name)
	{
		try{
			driver.navigate().to(CONFIG.getProperty(URL_Name));
		}catch(Throwable t){
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("Not able to navigate to selected URL" +URL_Name);
		}
		return "pass";
	}

	//close browser
	public void closeBrowser()
	{
		driver.quit();
		isBrowserOpened=false;
		APP_LOGS.debug("driver quit");
	}

	//getObjectid find element by ID
	public static WebElement getObjectid(String idkey) {
		try{
			return driver.findElement(By.id(OR.getProperty(idkey)));
		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" +idkey);
			return null;


		}
	}


	//getObjectid find element by id with input string	
	public static String getObjectidInput(String idKey, String Input)
	{ 
		try{
			driver.findElement(By.id(OR.getProperty(idKey))).sendKeys(CONFIG.getProperty(Input));
		}catch(Throwable t)
		{
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" + idKey);
		}
		return "pass";
	}


	//getObjectid find element by Xpath
	public static WebElement getObjectxpath(String xpathKey) {
		try{
			return driver.findElement(By.xpath(OR.getProperty(xpathKey)));
		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" +xpathKey);
			return null;
		}

	}

	public static WebElement getObjectxpathNew(String xpathKey) {
		wait = new WebDriverWait(driver, 2);

		try{
			for (int second = 0;; second++) {
				Thread.sleep(1000l);
				if (second >= 5){
					Assert.fail("timeout : " + xpathKey);}
				try {
					if (wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(OR.getProperty(xpathKey)))) != null)
						break;
				} catch (Exception e) {
					APP_LOGS.debug("---value-- "+xpathKey); 
				}
			}

			APP_LOGS.debug("-----"+driver.findElement(By.xpath(OR.getProperty(xpathKey))).getText());
			return driver.findElement(By.xpath(OR.getProperty(xpathKey)));
		}catch(Throwable t){
			// report error
			APP_LOGS.debug("No element present" +xpathKey);
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());

			return null;
		}

	}

	public static WebElement getObjectxpathDirect(String xpath) {
		wait = new WebDriverWait(driver, 2);

		try{
			for (int second = 0;; second++) {
				Thread.sleep(1000l);
				if (second >= 5){
					Assert.fail("timeout : " + xpath);}
				try {
					if (wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))) != null)
						break;
				} catch (Exception e) {
					APP_LOGS.debug("---value-- "+xpath); 
				}
			}

			APP_LOGS.debug("-----"+driver.findElement(By.xpath(xpath)).getText());
			return driver.findElement(By.xpath(xpath));
		}catch(Throwable t){
			// report error
			APP_LOGS.debug("No element present" +xpath);
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());

			return null;
		}

	}

	//getObjectid find element by Xpath with input string
	public static String getObjectxpathInput(String xpathKey, String Input) {
		try{
			driver.findElement(By.xpath(OR.getProperty(xpathKey))).sendKeys(CONFIG.getProperty(Input));
		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" +xpathKey );
		}
		return Input;
	}

	public static String getObjectidInputSelect (String idKey,String Input){
		try{
			Select select = new Select(driver.findElement(By.id(OR.getProperty(idKey))));
			select.selectByVisibleText(Data.getProperty(Input));
		} catch (Throwable t)

		{
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" + idKey);
		}
		return "pass";
	}

	// getObjectid find element by linkText	
	public static WebElement getObjectlinkText(String linkTextKey) {
		try{
			return driver.findElement(By.linkText(OR.getProperty(linkTextKey))); 
		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" +linkTextKey);		
			return null;
		}
	}	


	//getObjectid find element by linkText with input string
	public static String getObjectlinkTextInput(String xpathKey, String Input) {
		try{
			driver.findElement(By.linkText(OR.getProperty(xpathKey))).sendKeys(Data.getProperty(Input));
		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present"  +xpathKey );
		}
		return Input;
	}


	//getObjectid find element by Css	
	public static WebElement getObjectcss(String CssKey) {
		try{
			return driver.findElement(By.cssSelector(OR.getProperty(CssKey)));
		}catch(Throwable t){
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" +CssKey );		
			return null;
		}

	}



	//getObjectid find element by Css	
	public static WebElement getObjectclassName(String classname) {
		try{
			return driver.findElement(By.className(OR.getProperty(classname)));
		}catch(Throwable t){
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No element present" +classname );		
			return null;
		}

	}

	// Iframe Switch code
	public static void frameSwitchclass(String FrameName) throws InterruptedException
	{		
		try{
			Thread.sleep(5000);
			driver.switchTo().frame(Data.getProperty(FrameName));
			Thread.sleep(3000);

		}catch(Throwable t){
			// report error
			ErrorUtil.addVerificationFailure(t);
			Assert.assertTrue(false,t.getMessage());
			APP_LOGS.debug("No frame present " +FrameName);
		}	

	}

	//compare titles
	public void compareTitle(String expectedVal){

		Assert.assertEquals(driver.getTitle() ,(Expected_string.getProperty(expectedVal)));

	}

	// compare Strings
	public static  void comparestringtext( String actualValue, String expectedVal)
	{
		Assert.assertEquals(actualValue,(Expected_string.getProperty(expectedVal)));
	}


	// compare Strings
	public static  void comparestring( String actualValue, String expectedVal)
	{
		try{
			System.out.println("Actual Value"+"|"+actualValue+"|" );
			System.out.println("Expected Value"+"|"+Expected_string.getProperty(expectedVal)+"|" );

			Assert.assertEquals(actualValue,(Expected_string.getProperty(expectedVal)));
		}
		catch(Throwable t){
			System.out.println("-----in Catch");
			// code to report the error in testng

			ErrorUtil.addVerificationFailure(t);
			APP_LOGS.debug("Values do not match"  +actualValue );
			throw new AssertionFailedError("Compare String fails for --"+actualValue+"--&--"+Expected_string.getProperty(expectedVal));

		}
	}

	// isTextPresent Strings	

	public static  void PagesourceAssert(String expectedVal)
	{
		try{
			Assert.assertTrue(driver.getPageSource().contains((Expected_string.getProperty(expectedVal))));

		}catch(Throwable t){
			// code to report the error in testng
			ErrorUtil.addVerificationFailure(t);
			APP_LOGS.debug("Values do match");

			return;

		}
	}

	//find the test suite is runnable
	public static boolean isSuiteRunnable(Xls_Reader xls , String suiteName)
	{
		boolean isexecutable = false;
		for(int i=2; i<=xls.getRowCount("Test Suite"); i++)
		{

			if((xls.getCellData("Test Suite", "TSID", i)).equals(suiteName))
			{
				if((xls.getCellData("Test Suite", "Runmode", i)).equalsIgnoreCase("Y"))
				{
					isexecutable =  true;
					break;
				}
				else
				{
					isexecutable = false;				}
			}
		}
		xls= null;
		return isexecutable;

	}

	/// returns true if runmode of the test is equal to Y
	public static boolean isTestCaseRunnable(Xls_Reader xls,String SheetName, String testCaseName){
		boolean isExecutable=false;
		for(int i=2; i<= xls.getRowCount(SheetName) ; i++){

			if(xls.getCellData(SheetName, "testCaseName", i).equalsIgnoreCase(testCaseName)){
				if(setSkipFlag){
					xls.setCellData(SheetName, "Runmode", i, "N");
				}else if(xls.getCellData(SheetName, "Runmode", i).equalsIgnoreCase("Y")){
					isExecutable= true;
				}else{
					isExecutable= false;
				}
			}
		}

		return isExecutable;

	}


	//find the test suite is runnable
	public static boolean isSuiteRunnable_XML(Document doc , String suiteName)
	{
		boolean isexecutable = false;
		NodeList listOfTestSuite = doc.getElementsByTagName("TestSuite");
		for(int s=0; s<listOfTestSuite.getLength() ; s++){
			if(((Element)listOfTestSuite.item(s)).getAttribute("name").equalsIgnoreCase(suiteName)){
				if(((Element)listOfTestSuite.item(s)).getAttribute("runmode").equalsIgnoreCase("Y")){
					isexecutable =  true;
					break;
				}else{
					isexecutable = false;	
				}
			}
		}
		return isexecutable;
	}

	/// returns true if runmode of the test is equal to Y
	public static boolean isTestCaseRunnable_XML(Document doc,String suiteName, String testCaseName){
		boolean isexecutable = false;
		NodeList listOfTestSuite = doc.getElementsByTagName("TestSuite");
		for(int s=0; s<listOfTestSuite.getLength() ; s++){
			if(((Element)listOfTestSuite.item(s)).getAttribute("name").equalsIgnoreCase(suiteName)){
				NodeList listOfTestCase = doc.getElementsByTagName("TestCase");
				for(int t=0;t<listOfTestCase.getLength() ; t++){
					if(((Element)listOfTestCase.item(t)).getAttribute("name").equalsIgnoreCase(testCaseName)){
						if(((Element)listOfTestCase.item(t)).getAttribute("runmode").equalsIgnoreCase("Y")){
							isexecutable =  true;
							break;
						}else{
							isexecutable = false;	
						}

					}

				}
			}
		}	
		return isexecutable;
	}

	// update results for a particular data set	
	public static void reportDataSetResult(Xls_Reader xls, String testCaseName, int rowNum,String result)
	{	
		xls.setCellData(testCaseName, "Results", rowNum, result);
	}	

	public static void WaitForProgressBar() {
		wait = new WebDriverWait(driver, 120);
		if (wait.until(ExpectedConditions.invisibilityOfElementLocated(By
				.xpath("//div[@class='progress-bar green stripes']")))) {

			System.out.println("Progress bar invisible");
		}
	}

	public static String randomStringGen(String input) throws InterruptedException{
		StringBuffer essayQs_Name=new StringBuffer(input);
		Random random = new Random();
		for(int i =0; i<5; i++){			//attach upto 3 random numbers
			essayQs_Name=essayQs_Name.append(random.nextInt(10));
		}
		return essayQs_Name.toString();
	}

	//wait for web element until it is visible.
	public static WebElement elementExists(String xpath){
		wait = new WebDriverWait(driver, 2);
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
			System.out.println("Element having xpath - "+xpath+" is present!");
			return driver.findElement(By.xpath(xpath));  
		}catch(ElementNotFoundException e1){
			System.out.println("Element having xpath - "+xpath+" is not present!");
			return null;
		} catch (TimeoutException e) {
			System.out.println("Element having xpath - "+xpath+" is not present!");
			return null;
		}
	}
	public static String getDate(int period,String format)
	{
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat(format);
		currentDate.add(Calendar.DAY_OF_MONTH, period);
		String date = formatter.format(currentDate.getTime());
		return date;
	}
	public static String StringwithDate(String input) throws InterruptedException{
		StringBuffer essayQs_Name=new StringBuffer(input);
		essayQs_Name=essayQs_Name.append(getDate(0,"dd-mmm-yyyy"));

		return essayQs_Name.toString();
	}


	public static void StaleElementHandleByXPATH (String xpathelement){
		int count = 0; 
		while (count < 4){
			try {
				WebElement yourSlipperyElement= driver.findElement(By.xpath(xpathelement));
				yourSlipperyElement.click(); 
			} catch (StaleElementReferenceException e){
				e.toString();
				System.out.println("Trying to recover from a stale element :" + e.getMessage());
				count = count+1;
			}
			count = count+4;
		}
	}
	public static void waitInSeconds(int seconds){
		long time=seconds*1000;
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static boolean isAlertPresent() 
	{ 
		try 
		{ 
			driver.switchTo().alert(); 
			return true; 
		}   // try 
		catch (NoAlertPresentException Ex) 
		{ 
			return false; 
		}   // catch 
	}   // isAlertPresent()

}
