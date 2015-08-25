package com.suiteMarchRelease;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.TestBase;
import Util.QuestionUtil;
import Util.TestUtil;

public class TestCase_EI2216 extends TestBase
{

	// Runmode of test case in a suite
	@BeforeTest
	public void checkTestSkip() throws Exception
	{	 
		TestUtil.checkTestSkip("MarchReleaseSuite","TestCase_EI2216");
	}	 


	@Test
	public void verifyEI2216Test() throws Exception
	{
		System.out.println("Testing EI2216 sub:	User is unable to create folder in \"Questions by folder\" using special characters \"<<>>!@#$%^&*()_+{}:\"<>?~[];\',./\\");
		//first condition with allowed special characters
		String prepend=randomStringGen("special");
		String foldername=prepend+"<<>>!@#$%^&*()_+{}:<>?~[];\'\",.";
		APP_LOGS.debug("--------Question Folder Creation start(Allowed special characters)--------");
		QuestionUtil.ClickOnQuestionsTab();
		WebElement rootFolderCogWheel = elementExists("//*[@id='folderTreeDiv']//span[@title='/ITEMS']/../span[2]");
		WebElement cogWheel_NewFolderLink= elementExists("//*[@id='folderTreeDiv']//span[@title='/ITEMS']/../span[2]//a[text()='New Folder']");
		//click on new folder link
		new Actions(driver).moveToElement(rootFolderCogWheel).moveToElement(cogWheel_NewFolderLink).click().build().perform();
		APP_LOGS.debug("Clicked on new folder link");
		APP_LOGS.debug("Popup window '"+elementExists(OR.getProperty("QS_CreateNewFolderText")).getText()+"' displayed");
		//Enter folder name
		elementExists(OR.getProperty("QS_CreateNewFolderTextArea")).sendKeys(foldername);
		APP_LOGS.debug("Entered folder title '"+foldername+"' in title text field");
		elementExists(OR.getProperty("QS_CreateNewFolderSubmit")).click();
		APP_LOGS.debug("Clicked on Submit button!!");
		if(elementExists("//*[@id='createResource']//span[@class='errorLabel']").isDisplayed())
			Assert.fail("Invalid Question title characters used!!");
		WaitForProgressBar();
		if(elementExists("//*[@id='globalMessageDiv']//*[contains(@class,'confirmationLabel')]")!=null){
			APP_LOGS.debug("Folder "+foldername+" created successfully!");
		}
		else
			Assert.fail("Error in Question folder creation!!");
		APP_LOGS.debug("--------Question Folder Creation end--------");
		APP_LOGS.debug("Question folder created with valid special characters!!");




		APP_LOGS.debug("--------Question Folder Deletion start--------");
		//to avoid staleElementReferenceException again declare webElements for cogwheel
		WebElement rootFolderCogWheel2 = elementExists("//*[@id='folderTreeDiv']//span[contains(@title,'/ITEMS/"+prepend+"')]/../span[2]");
		WebElement cogWheel_NewFolderLink2= elementExists("//*[@id='folderTreeDiv']//span[contains(@title,'/ITEMS/"+prepend+"')]/../span[2]//a[text()='Delete Folder']");
		//click on new folder link
		new Actions(driver).moveToElement(rootFolderCogWheel2).moveToElement(cogWheel_NewFolderLink2).click().build().perform();		
		APP_LOGS.debug("Clicked on delete folder link");
		APP_LOGS.debug("Popup window '"+elementExists("//span[contains(text(),'Delete Folder')]").getText()+"' displayed");
		elementExists("//a[text()='Yes']").click();
		APP_LOGS.debug("Clicked on Delete OK button!!");
		WaitForProgressBar();
		if(elementExists("//*[@id='globalMessageDiv']//*[contains(@class,'confirmationLabel')]")!=null){
			APP_LOGS.debug("Folder "+foldername+" deleted successfully!");
		}
		APP_LOGS.debug("--------Question Folder Deletion end--------");



		//second condition with invalid special characters
		APP_LOGS.debug("--------Question Folder Creation start(Invalid special characters)--------");
		//click on new folder link
		//to avoid staleElementReferenceException again declare webElements for cogwheel
		WebElement rootFolderCogWheel1 = elementExists("//*[@id='folderTreeDiv']//span[@title='/ITEMS']/../span[2]");
		WebElement cogWheel_NewFolderLink1= elementExists("//*[@id='folderTreeDiv']//span[@title='/ITEMS']/../span[2]//a[text()='New Folder']");
		new Actions(driver).moveToElement(rootFolderCogWheel1).moveToElement(cogWheel_NewFolderLink1).click().build().perform();
		APP_LOGS.debug("Clicked on new folder link");
		APP_LOGS.debug("Popup window '"+elementExists(OR.getProperty("QS_CreateNewFolderText")).getText()+"' displayed");
		//Enter folder name
		elementExists(OR.getProperty("QS_CreateNewFolderTextArea")).sendKeys("/\\");
		APP_LOGS.debug("Entered folder title '"+"/\\"+"' in title text field");
		elementExists(OR.getProperty("QS_CreateNewFolderSubmit")).click();
		waitInSeconds(2);
		APP_LOGS.debug("Clicked on Submit button!!");
		if(elementExists("//*[@id='createResource']//span[@class='errorLabel']").isDisplayed()){
			APP_LOGS.debug("Invalid Question title characters used!!");
			elementExists(OR.getProperty("QS_CreateNewFolderSubmit")).sendKeys(Keys.ESCAPE);
		}
		else{
			elementExists(OR.getProperty("QS_CreateNewFolderSubmit")).sendKeys(Keys.ESCAPE);
			Assert.fail("Folder created for invalid characters!!");
		}
		APP_LOGS.debug("--------Question Folder Creation end--------");
	}

	@AfterMethod 
	public void DefaultResult(ITestResult result) throws InterruptedException {
		System.out.println("Method name: " + result.getMethod().getMethodName());
		System.out.println("Success %:" + result.isSuccess());
		if(!result.isSuccess()){
			TestUtil.takeScreenShot(result.getMethod().getMethodName());
			closeBrowser();
			System.out.println("Closing the Browser");
			openBrowser();	
			System.out.println("Opening the Browser");
			TestUtil.doLogin();
			System.out.println("Performed Login");
		}
		else{
			System.out.println("Testcase is passed, Not required to call Exit!");  

		}
	}

}