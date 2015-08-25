package Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import Base.TestBase;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class QuestionUtil extends TestBase {

	// Verify Create Questions Links
	//getObjectxpathNewNew
	public static void VerifyCreateQSLink() throws InterruptedException {
		ClickOnQuestionsTab() ;
		WaitForProgressBar();
		WebElement qsmc = getObjectxpathNew("QS_CreateMultiChoiseLink");
		TestBase.comparestring(qsmc.getText().trim(), "QS_CreateMCLinkText");
		System.out.println("Verified the Presence of CreateMultiChoiseLink");
		WebElement qstf = getObjectxpathNew("QS_CreateTrueFalseLink");
		TestBase.comparestring(qstf.getText().trim(), "QS_CreateTFLinkText");
		System.out.println("Verified the Presence of CreateTrueFalseLink");
		WebElement qses = getObjectxpathNew("QS_CreateEassyLink");
		TestBase.comparestring(qses.getText().trim(), "QS_CreateESLinkText");
		System.out.println("Verified the Presence of CreateEassyLink");
		WebElement qsfitb = getObjectxpathNew("QS_CreateFITBLink");
		TestBase.comparestring(qsfitb.getText().trim(), "QS_CreateFITBLinkText");
		System.out.println("Verified the Presence of CreateFITBLink");
		WebElement qsimp = getObjectxpathNew("QS_ImportQsLink");
		TestBase.comparestring(qsimp.getText().trim(), "QS_ImportQsLinkText");
		System.out.println("Verified the Presence of Import Questions");

	}


	public static void checkBreadcrumAndHeading(String breadcrumText, String headingText){
		String expectedBreadcrum="Home  >  "+breadcrumText;
		String expectedHeading=headingText;
		Assert.assertTrue("Verifying Breadcrum on Questions page - ", elementExists("//*[@id='breadcrumb']").getText().trim().equalsIgnoreCase(expectedBreadcrum));
		Assert.assertTrue("Verifying Heading on Questions page - ", elementExists("//*[@class='headline']").getText().trim().equalsIgnoreCase(expectedHeading));
	}


	public static void openQuestionFolder(String questionFolderName){
		if(elementExists("//*[@id='questionListingFolder']//span[.='"+questionFolderName+"']")!=null){
			driver.findElement(By.xpath("//*[@id='questionListingFolder']//span[.='"+questionFolderName+"']")).click(); 
			WaitForProgressBar();	
			waitInSeconds(2);
		}else
			Assert.assertTrue("Folder "+questionFolderName+" Not Found", false);
	}


	public static void checkExportResults_Excel_CSV(String fileType, String fileName){
		elementExists(OR.getProperty(fileType+"_results_icon")).click();
		WaitForProgressBar();
		waitInSeconds(2);
		File file = new File(System.getProperty("user.dir")+"\\src\\Download\\"+fileName);
		if(file.exists()){ 
			APP_LOGS.debug(fileType+" file downloaded successfully");
			file.delete();
		}
		else{
			Assert.assertTrue(fileType+" file not downloaded", false);
		}
	}



	public static void checkCommunityLink() throws Exception{
		try{
			TestUtil.doClickCommunitylink();
			TestUtil.doCheckShareanIdeaTab();
			TestUtil.doCheckReportaProblemTab();
			TestUtil.doCheckGivePraiseTab();
			TestUtil.doCheckGivePraiseTab();
			TestUtil.CheckAskaQuestionTab();
			APP_LOGS.debug("Community Link is verified!");
		}catch(Exception e){
			Assert.assertTrue("Community Link cannot be verified!", false);
		}
	}

	public static void checkExpandShrinkResultsLink(){
		elementExists(OR.getProperty("expandShrinkResultsLink")).click();
		WaitForProgressBar();
		if(elementExists(OR.getProperty("expandShrinkResultsLink")).getText().trim().replace("\n", "").contains("SHOWFOLDERS"))
			APP_LOGS.debug("Question Listing Folder grid is shrinked!");
		else
			Assert.assertTrue("Question Listing Folder grid is NOT shrinked!",false);
		elementExists(OR.getProperty("expandShrinkResultsLink")).click();
		WaitForProgressBar();
		if(elementExists(OR.getProperty("expandShrinkResultsLink")).getText().trim().replace("\n", "").contains("EXPANDRESULTS"))
			APP_LOGS.debug("Question Listing Folder grid is expanded!");
		else
			Assert.assertTrue("Question Listing Folder grid is NOT expanded!",false);
	}

	public static void checkHelpGuideLink() throws Exception{
		elementExists("//*[@id='headerTutorial']/div").click();
		if(elementExists("//*[@id='headerTutorial']//a[contains(.,'Resource Library')]")!=null)
			APP_LOGS.debug("Help Guide Link is verified!!");
		else
			Assert.assertTrue("Help Guide Link cannot be verified!!", false);
	}

	public static void checkSearch(String searchType, String searchText){
		elementExists("//*[@id='questionListing"+searchType+"']//input[@name='treeSearch']").sendKeys(searchText);
		waitInSeconds(2);
		if(elementExists("//*[@id='questionListing"+searchType+"']//td[contains(.,'"+searchText+"')]")!=null){
			APP_LOGS.debug("Question "+searchType+" searched successfully!!");
		}else
			Assert.assertTrue("Error in "+searchType+" search",false);
		if(searchType.equals("Folder"))
			elementExists("//*[@id='btnResetSearch']").click();
		else
			elementExists("//*[@id='questionListingCategory']/thead/tr/th[1]/div/button[@id='btnResetSearch']").click();
		if(elementExists("//*[@id='questionListing"+searchType+"']//input[@name='treeSearch']").getText().equals("")){
			APP_LOGS.debug("Reset search is working fine!!");
		}else
			Assert.assertTrue("Error in resetting search text field",false);
	}

	public static void check_LHS_RHS_Count(String groupingType, String groupingName){
		elementExists("//*[@id='questionListing"+groupingType+"']//td[1]//span[.='"+groupingName+"']").click();
		WaitForProgressBar();
		String LHS_GridCount=elementExists("//*[@id='questionListing"+groupingType+"']//tr[1]/td[2]/span").getText();
		String RHS_GridCount=elementExists("//*[@id='sizeOfGrid']/strong").getText();
		if(LHS_GridCount.equals(RHS_GridCount))
			APP_LOGS.debug("LHS and RHS "+groupingType+" question count is coming same. ");
		else
			Assert.assertTrue("LHS and RHS "+groupingType+" question count are not matching!!", false);
	}



	public static void addQuestionToAssessment(String name) throws InterruptedException{
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='createtabs']//a[@class='buttonintable examsAddQuestionsOpen']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='omniBasicSearchInput']")).clear();
		driver.findElement(By.xpath("//*[@id='omniBasicSearchInput']")).sendKeys(name);
		driver.findElement(By.xpath("//*[@id='btnDoQuestionsBasicSearchExamEC']")).click();
		Thread.sleep(2000);
		QuestionUtil.WaitForProgressBar();
		driver.findElement(By.xpath("//*[@id='qSelectCheckAll']")).click();
		driver.findElement(By.xpath("//*[@id='NumberSelectedRows']//a[@class='keybutton']")).click();
		Thread.sleep(2000);
		QuestionUtil.WaitForProgressBar();
		driver.findElement(By.xpath("//*[@id='myTable']//a[@class='keybutton' and contains(text(),'Close')]")).click();
		Thread.sleep(2000);
		QuestionUtil.WaitForProgressBar();
	}

	public static void DeleteEssayQuestions(String name) throws InterruptedException {
		QuestionUtil.searchQuestionByName(name);
		if(driver.findElement(By.xpath("//*[@id='questionsTable']/tbody/tr[1]//a[2]/img")).isDisplayed()){
			driver.findElement(By.xpath("//*[@id='questionsTable']/tbody/tr[1]//a[2]/img")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//*[@id='deleteQuestionHandler']/a")).click();
			WaitForProgressBar();
		}
	}

	//Verify the Column Name Present:
	public static void CheckforColumnNameAdd() throws InterruptedException{
		HashMap<String,Integer> ad= new HashMap<String,Integer>();
		HashMap<String,Integer> adafter= new HashMap<String,Integer>();
		ClickOnQuestionsTab();
		WaitForProgressBar();
		//code for setting list size.
		getWebElementByLocator(By.id("questionsTable_length"));
		getWebElementByLocator(By.linkText("25")).click();
		WaitForProgressBar();
		WebElement len=getWebElementByLocator(By.linkText("25"));
		if(len.getAttribute("class").equals("dt_selected_link")){
			System.out.println("Question list size is set");
		}
		else{
			Assert.fail("Question list size is not set");
		}

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
			WaitForProgressBar();

		}
		if(isIDColumnPreset==false){
			getObjectxpathDirect("//div[@id='addColDiv_questionsTable']//a[@id='cog_questionsTable']").click();
			new Select(driver.findElement(By.id("addCol_questionsTable"))).selectByVisibleText("ID/Rev ");
			WaitForProgressBar();
		}
		if(isStatusColumnPreset==false){
			getObjectxpathDirect("//div[@id='addColDiv_questionsTable']//a[@id='cog_questionsTable']").click();
			new Select(driver.findElement(By.id("addCol_questionsTable"))).selectByVisibleText("Status ");
			WaitForProgressBar();
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

	// Default Questions by Folder
	public static void VerifydefaultQSbyFolderLink() throws InterruptedException {
		ClickOnQuestionsTab() ;
		WebElement qsbyf = getObjectxpathNew("QS_QsbyFolderLink");
		String className = qsbyf.getAttribute("class");
		TestBase.comparestring(className, "QS_DefaultbyFolderClName");
		System.out.println("Verifed Question by Folder is default on Question_tab");
		WebElement qsbyc = getObjectxpathNew("QS_QsbyCategoriesLink");
		String className1 = qsbyc.getAttribute("class");
		TestBase.comparestring(className1, "QS_DefaultbyCategoriesClNameEmpty");
		System.out.println("Verifed Question by Categories is not default on Question_tab");

	}
	// Delete Question Folder
	public static void DeleteQSFolder(String FolderName) throws InterruptedException {
		ClickOnQuestionsTab() ;
		WebElement folderCogWheel=elementExists("//*[@id='questionListingFolder']//td[contains(.,'"+FolderName+"')]/..//img[contains(@src,'cog.png')]");
		WebElement deleteFolderLink=elementExists("//*[@id='questionListingFolder']//td[contains(.,'"+FolderName+"')]/..//a[contains(text(),'Delete Folder')]");

		new Actions(driver).moveToElement(folderCogWheel).moveToElement(deleteFolderLink).click().build().perform();
		/*
		WebElement qsfolder1 = driver.findElement(By.xpath("//span[@title='/"+(CONFIG_ENV.getProperty("RootFolderName"))+"/"+FolderName+"']/../span[2]/ul/li/img"));


		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
				qsfolder1, "color: yellow; border: 2px solid yellow;");
		Locatable hoverItem = (Locatable) qsfolder1;
		Mouse mouse = ((HasInputDevices) driver).getMouse();
		mouse.mouseMove(hoverItem.getCoordinates());


		String fd= qsfolder1.findElement(
				By.xpath(OR.getProperty("Qs_DeleteFolderOptionLink"))).getAttribute("onclick");

		String scriptName="deleteFolderOpen";
		fd=fd.replaceAll("\"", "'");
		String [] javascr=fd.split(scriptName);
		String kj=javascr[1];
		((JavascriptExecutor)driver).executeScript(scriptName+kj);
		Thread.sleep(5000l);
		System.out.println("Window displayed with Name"
				+ getObjectxpathNew("QS_DeleteFolderText").getText());*/



		WebElement qsfoldersubmit = getObjectxpathNew("QS_DeleteFolderYes");
		qsfoldersubmit.click();
		WaitForProgressBar();
		WebElement qsfoldersucessfully = getObjectxpathNew("Qs_CreNewFolderSucessMsg");
		String ActualMsg = qsfoldersucessfully.getText();
		//Verification part
		ComapreTwoStringVal(ActualMsg.trim(),"The folder, "+FolderName+", was removed from the system.");
		System.out.println("Verified Folder Sucessfully Created Message ");
		WaitForProgressBar();
		Thread.sleep(5000l);

		//Verification part
		if(elementExists("//*[@id='questionListingFolder']//span[text()='"+FolderName+"']")!=null)
			Assert.fail(FolderName+":Folder not deleted");
		else
			System.out.println("Verified Folder below Top level Folder Items "+FolderName);
	}

	//Compare Two Text inner method
	public static void ComapreTwoStringVal(String one,String two){

		if (one.equals(two)) {
			System.out.println("String Match:"+one+":"+two);

		} else {
			System.out.println("String not Match:"+one+":"+two);
			Assert.fail("String not Match:"+one+":"+two);
		}

	}

	// Create Question Folder
	public static void CreateQSFolder(String FolderName) throws InterruptedException {
		APP_LOGS.debug("--------Question Folder Creation start---------");
		ClickOnQuestionsTab();
		WebElement examsCogWheel=elementExists("//*[@id='questionListingFolder']//td[contains(.,'ITEMS')]/..//img[contains(@src,'cog.png')]");
		WebElement newFolderLink=elementExists("//*[@id='questionListingFolder']//td[contains(.,'ITEMS')]/..//a[contains(text(),'New Folder')]");

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", newFolderLink);


		/*Actions action =new Actions(driver);
		waitInSeconds(1);
		action.moveToElement(examsCogWheel).moveToElement(newFolderLink).click().build().perform();*/

		APP_LOGS.debug("Clicked on new folder link");
		APP_LOGS.debug("Popup window '"+elementExists(OR.getProperty("QS_CreateNewFolderText")).getText()+"' displayed");
		//Enter folder name
		elementExists(OR.getProperty("QS_CreateNewFolderTextArea")).sendKeys(FolderName);
		APP_LOGS.debug("Entered folder title '"+FolderName+"' in title text field");
		elementExists(OR.getProperty("QS_CreateNewFolderSubmit")).click();
		APP_LOGS.debug("Clicked on Submit button!!");

		WaitForProgressBar();
		if(elementExists("//*[@id='globalMessageDiv']//*[contains(@class,'confirmationLabel')]")!=null){
			if(elementExists("//*[@id='questionListingFolder']//span[text()='"+FolderName+"']").isDisplayed()) 
				APP_LOGS.debug("Folder "+FolderName+" created successfully!");
		}
		APP_LOGS.debug("--------Question Folder Creation end--------");
	}

	// Verify Selection of Folder
	public static void VerifySelectionOfQSFolder(String FolderName) throws InterruptedException {
		ClickOnQuestionsTab() ;
		WebElement qsbyf = getObjectxpathNew("QS_QsbyFolderLink");
		qsbyf.click();
		Thread.sleep(5000l);
		String className = qsbyf.getAttribute("class");
		TestBase.comparestring(className, "QS_DefaultbyFolderClName");

		WaitForProgressBar();


		//click on the Folder Name

		WebElement qsfolder = driver.findElement(By.xpath("//*[@id='questionListingFolder']//span[.='"+FolderName+"']"));
		qsfolder.click();
		Thread.sleep(5000l);
		List<WebElement> qselm = driver.findElements(By.xpath("//table [@id='questionsTable']//tbody[@role='alert']//tr"));
		/*System.out.println("-------Size" + qselm.size());

		String ConstantXpath="//table [@id='questionsTable']//tbody[@role='alert']/tr[";

		Enumeration<String> keys = (Enumeration<String>) Expected_string.propertyNames();
		List<String> keyList = new ArrayList<String>();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			if (key.startsWith("title.qs.")) {
				keyList.add(key);

			}
		}
		Collections.sort(keyList);
		int count=0;
		for (int i = 0; i < qselm.size(); i++) {


			if (i != 0) {


				for(String title:keyList){
					System.out.println("|"+driver.findElement(By.xpath(ConstantXpath + i + "]/td[2]")).getText()+"|"+title+"|");
					if (driver.findElement(By.xpath(ConstantXpath + i + "]/td[2]")).getText()
							.equals(Expected_string.get(title))
							) {
						System.out.println("Verified QS: "
								+ Expected_string.get(title));
						count++;
						break;

					}


				}


				System.out.println("Title: "
						+ driver.findElement(
								By.xpath("//table [@id='questionsTable']//tbody[@role='alert']/tr["
										+ i + "]/td[2]")).getText()+"|");

			}

		}
		//Verification Part
		 */		
		//if(count>=2){
		if (count>=qselm.size())
			System.out.println("More than two question verified");
		else
			Assert.fail("Question is not present in the selected folder");
	}
	// Edit Folder
	public static void EditQSFolder(String FolderName,String EditedName) throws InterruptedException {
		ClickOnQuestionsTab() ;
		/*WebElement qsbyf = getObjectxpathNew("QS_QsbyFolderLink");
		qsbyf.click();
		Thread.sleep(5000l);
		String className = qsbyf.getAttribute("class");
		TestBase.comparestring(className, "QS_DefaultbyFolderClName");
		WaitForProgressBar();
		WebElement qsfolder1 = driver.findElement(By.xpath("//*[@id='questionListingFolder']//span[.='"+FolderName+"']/../span[2]/ul/li/img"));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
				qsfolder1, "color: yellow; border: 2px solid yellow;");
		Locatable hoverItem = (Locatable) qsfolder1;
		Mouse mouse = ((HasInputDevices) driver).getMouse();
		mouse.mouseMove(hoverItem.getCoordinates());
		Thread.sleep(5000l);
		String fd=qsfolder1
				.findElement(By.xpath(OR.getProperty("Qs_EditFolderOptionLink")))
				.getAttribute("onclick");


		String scriptName="editFolderOpen";
		fd=fd.replaceAll("\"", "'");
		String [] javascr=fd.split(scriptName);
		String kj=javascr[1];
		System.out.println("Edit script:"+js.executeScript(scriptName+kj));
		//----
		 */		
		WebElement folderCogWheel=elementExists("//*[@id='questionListingFolder']//td[contains(.,'"+FolderName+"')]/..//img[contains(@src,'cog.png')]");
		WebElement editFolderLink=elementExists("//*[@id='questionListingFolder']//td[contains(.,'"+FolderName+"')]/..//a[contains(text(),'Edit Folder')]");

		new Actions(driver).moveToElement(folderCogWheel).moveToElement(editFolderLink).click().build().perform();


		Thread.sleep(5000l);
		System.out.println("Window displayed with Name"
				+ getObjectxpathNew("QS_EditFolderText").getText());
		WebElement qsedittxar = getObjectxpathNew("QS_EditFolderTextArea");
		qsedittxar.clear();
		qsedittxar.sendKeys(EditedName);
		WebElement qsfoldersubmit = getObjectxpathNew("QS_EditFolderSubmit");
		qsfoldersubmit.click();
		WaitForProgressBar();
		Thread.sleep(3000l);
		WebElement qsfoldersucessfully = getObjectxpathNew("Qs_CreNewFolderSucessMsg");
		String ActualMsg = qsfoldersucessfully.getText();
		//--Verification part
		ComapreTwoStringVal(ActualMsg.trim(),"The folder, "+EditedName+", was successfully edited and saved.");
		System.out.println("Verified Folder Sucessfully Edited Message ");
		//QS_FolderNamePresent Verification part
		if (elementExists("//*[@id='questionListingFolder']//span[text()='"+EditedName+"']")
				.isDisplayed()) {
			System.out.println("Verified Folder below Top level Folder Items "+EditedName);

		} else {

			Assert.fail(FolderName+":Folder not Edited");
		}

	}
	// Verify Questions by Category
	public static void VerifyQSbyCategories() throws InterruptedException {
		ClickOnQuestionsTab();
		WebElement qsbyc = getObjectxpathNew("QS_QsbyCategoriesLink");
		qsbyc.click();
		Thread.sleep(5000l);
		String className1 = qsbyc.getAttribute("class");
		TestBase.comparestring(className1, "QS_DefaultbyCategoriesClName");
		System.out.println("Verifed Question by Categories is selected on Question_tab");
		List<String> keyListAc = new ArrayList<String>();
		List<WebElement> listcat = driver.findElements(By.xpath("//*[@id='questionListingCategory']/tbody/tr/td[1]/span/span[2]"));
		int Count = 0;
		for (WebElement cat : listcat) {

			if (!cat.getText().trim().equals("")) {
				System.out.println("---Categories Text  : "+ cat.getText());
				keyListAc.add(cat.getText());
				Count++;
			}
		}
		System.out.println("List Count" + Count);

		Enumeration<String> keys = (Enumeration<String>) Expected_string.propertyNames();
		List<String> keyList = new ArrayList<String>();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			if (key.startsWith("Qs.Categories.list")) {
				keyList.add(key);

			}
		}
		Collections.sort(keyList);


		int countval=0;
		for (String cl : keyList) {
			int coulist = 0;
			for(String cl1:keyListAc){
				if (Expected_string.getProperty(cl).equals(keyListAc.get(coulist))
						) {

					System.out.println("--Verified List"
							+ Expected_string.getProperty(cl) + "--- with --"
							+ keyListAc.get(coulist));
					countval++;
				} else {


				}
				coulist++;
			}


		}
		//Verification Part
		if(countval==keyList.size()){
			System.out.println("--Verified List"+countval);
		}
		else{
			Assert.fail("Count not match");
		}

	}

	public static void DeleteCategory(String CategoryName){
		String cogWheel="//*[@id='categoriesTreeDiv']//span[text()='"+CategoryName+"']/..//*[@id='options']//img";
		String deleteCategoryXpath="//*[@id='categoriesTreeDiv']//span[text()='"+CategoryName+"']/..//*[@id='options']//a[text()='Delete Category']";
		if(elementExists(cogWheel)!=null){
			new Actions(driver).moveToElement(elementExists(cogWheel)).moveToElement(elementExists(deleteCategoryXpath)).click().build().perform();
			WaitForProgressBar();
			elementExists("//*[@id='deleteCancelYesDiv']/a[text()='Yes']").click();//click on Yes button
			WaitForProgressBar();
			String deleteConfirmationMsg="The category, "+CategoryName+", was removed from the system.";
			String actualConfirmationMsg=elementExists("//*[@id='globalMessageDiv']/div[2]/table").getText().trim();

			if(deleteConfirmationMsg.equals(actualConfirmationMsg)){
				System.out.println("Category got deleted successfully!!");
			}else{
				Assert.assertTrue("Error in category deletion!!", false);
			}
		}
	}

	// Create New Category
	public static void CreateCategory(String CatName) throws InterruptedException {
		ClickOnQuestionsTab() ;
		getObjectxpathNew("QS_QsbyCategoriesLink").click();
		Thread.sleep(3000l);
		String className = getObjectxpathNew("QS_QsbyCategoriesLink").getAttribute("class");
		TestBase.comparestring(className, "QS_DefaultbyCategoriesClName");
		System.out.println("Verifed Question by Categories is selected on Question_tab.");

		WebElement categoryCogWheel=elementExists("//*[@id='questionListingCategory']//td[.='CATEGORIES']/..//img[contains(@src,'cog.png')]");
		WebElement newCategoryOption=elementExists("//*[@id='questionListingCategory']//td[.='CATEGORIES']/..//a[contains(text(),'New Category')]");

		new Actions(driver).moveToElement(categoryCogWheel).moveToElement(newCategoryOption).click().build().perform();
		waitInSeconds(1);
		if(elementExists("//span[@id='ui-id-1' or text()='Create Category']")!=null){
			APP_LOGS.debug("Create category popup opened!");
		}else{
			Assert.assertTrue("Create Category popup did not appeared!", false);
		}

		getObjectxpathNew("QSCAT_CreateCategoryTextArea").sendKeys(CatName);
		getObjectxpathNew("QSCAT_CreateCategorySaveButton").click();

		WaitForProgressBar();
		WebElement qsfoldersucessfully = getObjectxpathNew("Qs_CreNewFolderSucessMsg");
		String ActualMsg = qsfoldersucessfully.getText();
		//Verification Part
		ComapreTwoStringVal(ActualMsg.trim(),
				"The category, "+CatName+", was successfully created.");
		System.out.println("Verified Categories Sucessfully Created Message "
				+ ActualMsg.trim());

		//Verification Part
		driver.findElement(By.xpath("//*[@id='questionListingCategory']//td[.='"+CatName+"']"));

	}

	//Verify Select Category Folder
	public static void VerifySelectCategory(String CatName) throws InterruptedException {
		ClickOnQuestionsTab() ;
		WebElement qsbyc = getObjectxpathNew("QS_QsbyCategoriesLink");
		qsbyc.click();
		WaitForProgressBar();
		String className1 = qsbyc.getAttribute("class");
		TestBase.comparestring(className1, "QS_DefaultbyCategoriesClName");
		System.out.println("Verifed Question by Categories is selected on Question_tab");
		//WaitForProgressBar();
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollTo(0,Math.max(document.documentElement.scrollHeight," +
				"document.body.scrollHeight,document.documentElement.clientHeight));");
		driver.findElement(By.xpath("//*[@id='questionListingCategory']//span[.='"+CatName+"']")).click();
		WaitForProgressBar();
		Thread.sleep(3000);
		if(getWebElementByLocator(By.xpath("//p[@id='gridFilter']")).getText().equals("/CATEGORIES/"+CatName)){
			System.out.println("Automation category is selected");
		}
		else{
			System.out.println("Text Present on Grid Filter"+getWebElementByLocator(By.xpath("//p[@id='gridFilter']")).getText());
			Assert.fail("Automation Category Filter is not selected");
		}

		List<WebElement> qselm = driver.findElements(By.xpath("//table [@id='questionsTable']//tbody[@role='alert']//tr"));
		/*		int count1=0;
		WebElement nd1=null; 
		for (int i = 1; i <=qselm.size(); i++) {

			nd1=driver.findElement(By.xpath("//table [@id='questionsTable']//tbody[@role='alert']/tr/td[2]"));


			if (i!=1){
				nd1=driver.findElement(By.xpath("//table[@id='questionsTable']//tbody[@role='alert']/tr["+ i + "]/td[2]"));
			}
			System.out.println("----"+nd1.getText());
			if(nd1.getText().equals(Data.getProperty("QuestionTitle1"))||
					nd1.getText().equals(Data.getProperty("QuestionTitle2"))||
					nd1.getText().equals(Data.getProperty("QuestionTitle3"))||
					nd1.getText().equals(Data.getProperty("QuestionTitle4"))){

				count1++;
				System.out.println("----"+count1);

			}



		}*/
		//Verification Part
		//if (count1>=3){
		if (count>=qselm.size())
			System.out.println("All the Questions Verified");
		else
			Assert.fail("All Questions are not verified");
	}

	public static void selectCategory(String CatName) throws InterruptedException {
		driver.findElement(By.xpath("//*[@id='categories']/section/table/tbody/tr[1]/td/a")).click();
		Thread.sleep(2000);
		getObjectxpathNew("QS_Other_AddCategoryQSText");
		System.out.println("Add Categories to Question");
		Thread.sleep(3000l);
		driver.findElement(By.xpath("//*[@id='categoriesToAdd']/tbody/tr[.='"+CatName+"']//img[contains(@src,'add.png')]")).click();
		Thread.sleep(2000l);
		driver.findElement(By.xpath("//*[@id='addRemCatDoneSavedQuestion']")).click();
		Thread.sleep(2000);
	}


	public static void addQsToCategory(String CatName, String QuestionName) throws InterruptedException {
		ClickOnQuestionsTab() ;
		WaitForProgressBar();
		QuestionUtil.searchQuestionByName(QuestionName);
		driver.findElement(By.xpath("//*[@id='questionsTable']/tbody/tr/td[2]/a")).click();
		WaitForProgressBar();
		QuestionUtil.selectCategory(CatName);
	}

	public static void checkCategoryForQuestionCount(String CatName) throws InterruptedException {
		CategoryUtil.ClickOnCategoryTab();
		WaitForProgressBar();
		String xpath="//span[@title='"+CatName+"']//a";
		if(Integer.parseInt(driver.findElement(By.xpath(xpath)).getText())>0){
			System.out.println("Question count is greater than 0 for category="+CatName);
		}
		else
			Assert.fail("Question count is blank!");
	}

	public static void tryDeleteCategoryWithQuestion(String CatName) throws InterruptedException {
		Thread.sleep(2000);
		CategoryUtil.ClickOnCategoryTab();
		WaitForProgressBar();
		String xpath="//span[@title='"+CatName+"']//span[5]/img";
		driver.findElement(By.xpath(xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='deleteCancelYesDiv']/a[2]")).click();
		Thread.sleep(2000);
		if(driver.findElement(By.xpath("//div[@class='errorDisplay']")).isDisplayed()){
			System.out.println("Cannot delete Category without untagging question from it !");
		}else
			Assert.fail("Able to delete Category without untagging question from it!");
		driver.findElement(By.xpath("//*[@id='deleteOKDiv']/a")).click();
		Thread.sleep(2000);
		WaitForProgressBar();
	}

	public static void checkQuestionforBlankPage(String QuestionName) throws InterruptedException {
		ClickOnQuestionsTab() ;
		WaitForProgressBar();

		QuestionUtil.searchQuestionByName(QuestionName);
		driver.findElement(By.xpath("//*[@id='questionsTable']/tbody/tr/td[2]/a")).click();
		WaitForProgressBar();
		if(!driver.findElement(By.xpath("//*[@id='createtabs']/section/table/tbody//p")).getText().isEmpty()){
			System.out.println("Question is not coming as blank page after untagging it from category!");
		}
		else
			Assert.fail("Question is  coming as blank page after untagging it from category!");
	}
	//Create MC Questions
	public static void CreateMCQuestions(String QStitle) throws InterruptedException {
		ClickOnQuestionsTab() ;


		if (!QStitle.equals("")){


		}
		else{

			QStitle="Automation Multiple Choice Question";
		}
		WaitForProgressBar();

		WebElement qsbyf = getObjectxpathNew("QS_QsbyFolderLink");
		qsbyf.click();
		Thread.sleep(5000l);
		String className = qsbyf.getAttribute("class");
		TestBase.comparestring(className, "QS_DefaultbyFolderClName");
		WebElement qsmc = getObjectxpathNew("QS_CreateMultiChoiseLink");
		TestBase.comparestring(qsmc.getText().trim(), "QS_CreateMCLinkText");
		qsmc.click();
		WaitForProgressBar();

		String questions[] = { Data.getProperty("QuestionName"),
				Data.getProperty("QuestionOption1"),
				Data.getProperty("QuestionOption2"),
				Data.getProperty("QuestionOption3"),
				Data.getProperty("QuestionOption4") };

		String framepath[] = { OR.getProperty("Questionpath"),
				OR.getProperty("choiceonepath"),
				OR.getProperty("choicetwopath"),
				OR.getProperty("choicethreepath"),
				OR.getProperty("choicefourpath") };

		Thread.sleep(3000);


		for (int i = 0; i < questions.length; i++) {
			driver.switchTo().defaultContent();
			WebElement q = driver.findElement(By.xpath(framepath[i]));
			driver.switchTo().frame(q);
			WebElement we = driver.findElement(By.tagName("body"));
			System.out.println(we.getTagName());
			((JavascriptExecutor) driver).executeScript("document.body.innerHTML = '<p><br></p>'");
			we = driver.findElement(By.tagName("p"));
			System.out.println(we.getTagName());
			we.sendKeys(questions[i]);
			driver.switchTo().defaultContent();

		}

		getObjectxpathNew("QS_MC_CreateMultipleChoiceQuestionHeading");
		System.out.println("Verified Create Multiple Choice Question heading");

		idforQSSearch = driver.findElement(By.xpath("//div[@id='sidebarcontent']//table[@class='imageformtable']//tbody/tr/td/strong")).getText();
		System.out.println("Question with id for search: " + idforQSSearch);
		getObjectxpathNew("QS_MC_QuestionTitle").sendKeys(QStitle);

		System.out.println("Entered Create Multiple Choice Question Title");
		getObjectxpathNew("QS_MC_selectFolderToQuestion").click();
		System.out.println("Select Folder to create MC Questions");
		EnterOtherQuestiondetails(CONFIG.getProperty("QS_CreateQSFolderName"),Data.getProperty("DefaultCatName"));

		getObjectxpathNew("QS_MC_OptionACheckbox").click();
		System.out.println("Selected option A");
		Thread.sleep(2000l);
		getObjectxpathNew("QS_MC_OptionBCheckbox").click();
		System.out.println("Selected option B");
		Thread.sleep(2000l);
		getObjectxpathNew("QS_MC_SaveButtonText").click();
		System.out.println("Question Saved");
		Thread.sleep(2000l);
		//Verification part
		ComapreTwoStringVal(getObjectxpathNew("QS_MC_QuestionSucessfullySaveMsgVerfication")
				.getText(),"This question was successfully saved");

		getObjectxpathNew("QS_MC_CancelButtonText").click();
		Thread.sleep(2000l);
		WaitForProgressBar();
	}
	//Create TF Questions
	public static void CreateTFQuestions(String QStitle) throws InterruptedException {
		ClickOnQuestionsTab() ;
		if (!QStitle.equals("")){


		}
		else{

			QStitle="Automation True/False Choice Question";
		}
		WaitForProgressBar();

		WebElement qsbyf = getObjectxpathNew("QS_QsbyFolderLink");
		qsbyf.click();
		Thread.sleep(3000l);
		String className = qsbyf.getAttribute("class");
		TestBase.comparestring(className, "QS_DefaultbyFolderClName");
		WebElement qstf = getObjectxpathNew("QS_CreateTrueFalseLink");
		TestBase.comparestring(qstf.getText().trim(), "QS_CreateTFLinkText");
		System.out.println("Verified the Presence of CreateTrueFalseLink");
		qstf.click();
		WaitForProgressBar();
		getObjectxpathNew("QS_TF_CreateTrueFalseQuestionHeading");
		System.out.println("Verified Create True False Question heading");

		getObjectxpathNew("QS_MC_QuestionTitle").sendKeys(
				QStitle);

		System.out.println("Entered Create Multiple Choice Question Title");
		getObjectxpathNew("QS_MC_selectFolderToQuestion")
		.click();
		System.out.println("Select Folder to create True/False Questions");
		Thread.sleep(2000l);
		EnterOtherQuestiondetails(CONFIG.getProperty("QS_CreateQSFolderName"),Data.getProperty("DefaultCatName"));
		String questions[] = { Data.getProperty("QuestionName"), Data.getProperty("QuestionOption1"), Data.getProperty("QuestionOption2"),
				Data.getProperty("QuestionOption3"), Data.getProperty("QuestionOption4") };
		WebElement we;

		for (int i = 1; i < 2; i++) {
			driver.switchTo().frame(i);
			we = driver.switchTo().activeElement();
			we.click();
			we.sendKeys(questions[i - 1]);
			System.out.println("TagName--:" + we.getTagName());

			driver.switchTo().defaultContent();
			Thread.sleep(2000);
		}

		Thread.sleep(2000l);
		getObjectxpathNew("QS_MC_SaveButtonText").click();
		System.out.println("Question Saved");
		Thread.sleep(2000l);
		//Verification part
		ComapreTwoStringVal(getObjectxpathNew("QS_MC_QuestionSucessfullySaveMsgVerfication")
				.getText(),"This question was successfully saved");

		getObjectxpathNew("QS_MC_CancelButtonText").click();
		Thread.sleep(2000l);
		WaitForProgressBar();
	}
	//Create Essay Questions
	public static void CreateEssayQuestions(String QStitle) throws InterruptedException {
		ClickOnQuestionsTab() ;
		WaitForProgressBar();

		WebElement qsbyf = getObjectxpathNew("QS_QsbyFolderLink");
		qsbyf.click();
		Thread.sleep(3000l);
		String className = qsbyf.getAttribute("class");
		TestBase.comparestring(className, "QS_DefaultbyFolderClName");
		WebElement qses = getObjectxpathNew("QS_CreateEassyLink");
		TestBase.comparestring(qses.getText().trim(), "QS_CreateESLinkText");
		System.out.println("Verified the Presence of CreateEassyLink");
		qses.click();
		WaitForProgressBar();
		getObjectxpathNew("QS_Essay_CreateEssayQuestionHeading");
		System.out.println("Verified Create Essay Question heading");

		getObjectxpathNew("QS_MC_QuestionTitle").sendKeys(
				QStitle);

		System.out.println("Entered Create Essay Question Title");
		getObjectxpathNew("QS_MC_selectFolderToQuestion")
		.click();
		System.out.println("Select Folder to create Eassy Questions");
		Thread.sleep(2000l);

		EnterOtherQuestiondetails(CONFIG.getProperty("QS_CreateQSFolderName"),Data.getProperty("DefaultCatName"));
		String questions[] = { Data.getProperty("QuestionName"), Data.getProperty("QuestionOption1"), Data.getProperty("QuestionOption2"),
				Data.getProperty("QuestionOption3"), Data.getProperty("QuestionOption4") };
		WebElement we;

		for (int i = 1; i < 2; i++) {
			driver.switchTo().frame(i);
			we = driver.switchTo().activeElement();
			we.click();
			we.sendKeys(questions[i - 1]);
			System.out.println("TagName--:" + we.getTagName());

			driver.switchTo().defaultContent();
			Thread.sleep(2000);
		}

		getObjectxpathNew("QS_MC_SaveButtonText").click();
		System.out.println("Question Saved");
		Thread.sleep(2000l);
		//Verification part
		ComapreTwoStringVal(getObjectxpathNew("QS_MC_QuestionSucessfullySaveMsgVerfication")
				.getText(),"This question was successfully saved");

		getObjectxpathNew("QS_MC_CancelButtonText").click();
		WaitForProgressBar();
		Thread.sleep(2000l);

	}
	//Create FITB Question
	public static void CreateFITBQuestions(String QStitle) throws InterruptedException {
		ClickOnQuestionsTab() ;
		if (!QStitle.equals("")){


		}
		else{

			QStitle="Automation FITB Question";
		}
		WaitForProgressBar();

		WebElement qsbyf = getObjectxpathNew("QS_QsbyFolderLink");
		qsbyf.click();
		Thread.sleep(5000l);
		String className = qsbyf.getAttribute("class");
		TestBase.comparestring(className, "QS_DefaultbyFolderClName");
		WebElement qsfitb = getObjectxpathNew("QS_CreateFITBLink");
		TestBase.comparestring(qsfitb.getText().trim(), "QS_CreateFITBLinkText");
		System.out.println("Verified the Presence of CreateFITBLink");
		qsfitb.click();
		WaitForProgressBar();
		getObjectxpathNew("QS_FITB_CreateFITBQuestionHeading");
		System.out.println("Verified Create Fill in the Blank/Matching Question heading");

		getObjectxpathNew("QS_MC_QuestionTitle").sendKeys(
				QStitle);

		System.out.println("Entered Create FITB Question Title");

		getObjectxpathNew("QS_MC_selectFolderToQuestion")
		.click();
		System.out.println("Select Folder to create FITB Questions");
		Thread.sleep(2000l);
		EnterOtherQuestiondetails(CONFIG.getProperty("QS_CreateQSFolderName"),Data.getProperty("DefaultCatName"));

		String questions[] = { Data.getProperty("QuestionName"), Data.getProperty("QuestionOption1"), Data.getProperty("QuestionOption2"),
				Data.getProperty("QuestionOption3"), Data.getProperty("QuestionOption4") };
		WebElement we;

		for (int i = 1; i < 2; i++) {
			driver.switchTo().frame(i);
			we = driver.switchTo().activeElement();
			we.click();
			we.sendKeys(questions[i - 1]);
			System.out.println("TagName--:" + we.getTagName());

			driver.switchTo().defaultContent();
			Thread.sleep(2000);
		}

		getObjectxpathNew("QS_FITB_blanksTableHeaders")
		.click();
		Thread.sleep(2000);
		getObjectxpathNew("QS_FITB_blankText1")
		.sendKeys("Tom");
		Thread.sleep(2000);

		for (int i = 1; i < 2; i++) {
			driver.switchTo().frame(i);
			we = driver.switchTo().activeElement();

			System.out.println("Text under Question--:"
					+ we.findElement(By.xpath("//p/img")).getAttribute("src"));

			driver.switchTo().defaultContent();
			Thread.sleep(2000);
		}

		getObjectxpathNew("QS_MC_SaveButtonText").click();
		System.out.println("Question Saved");
		Thread.sleep(2000l);
		//Verification Part
		ComapreTwoStringVal(getObjectxpathNew("QS_MC_QuestionSucessfullySaveMsgVerfication")
				.getText(),"This question was successfully saved");

		getObjectxpathNew("QS_MC_CancelButtonText").click();
		Thread.sleep(2000l);
		WaitForProgressBar();
	}
	//Enter other Question details
	public static void EnterOtherQuestiondetails(String FolderName) throws InterruptedException {
		// Generic Information
		getObjectxpathNew("QS_Other_SelectFolderText");
		System.out.println("Verified Select Folder window title");
		driver.findElement(By.xpath("//*[@id='questionListingFolder']//span[.='"+FolderName+"']")).click();

		System.out.println("Select Folder a window title");
		Thread.sleep(2000l);
		getObjectxpathNew("QS_Other_SelectedQSFolder").getText();
		System.out.println("Selected Folder :  "
				+ getObjectxpathNew("QS_Other_SelectedQSFolder")
				.getText());
		//Verification
		getObjectxpathNew("QS_Other_SelectGroup").sendKeys(
				Data.getProperty("QuestionsGroup"));
		System.out.println("Entered Automation Group ");
		getObjectxpathNew("QS_Other_AddCategoryOpenButton")
		.click();
		Thread.sleep(2000l);

	}
	//Enter other Question details
	public static void EnterOtherQuestiondetails(String FolderName,String CatName) throws InterruptedException {
		// Generic Information
		getObjectxpathNew("QS_Other_SelectFolderText");
		System.out.println("Verified Select Folder window title");
		driver.findElement(By.xpath("//*[@id='questionListingFolder']//span[.='"+FolderName+"']")).click();

		System.out.println("Select Folder a window title");
		Thread.sleep(2000l);
		getObjectxpathNew("QS_Other_SelectedQSFolder").getText();
		System.out.println("Selected Folder :  "
				+ getObjectxpathNew("QS_Other_SelectedQSFolder")
				.getText());
		//Verification
		getObjectxpathNew("QS_Other_SelectGroup").sendKeys(
				Data.getProperty("QuestionsGroup"));
		System.out.println("Entered Automation Group ");
		getObjectxpathNew("QS_Other_AddCategoryOpenButton")
		.click();
		Thread.sleep(2000l);
		System.out.println("Click on Add Category");
		getObjectxpathNew("QS_Other_AddCategoryQSText");
		System.out.println("Add Categories to Question");
		Thread.sleep(3000l);

		driver.findElement(By.xpath("//span[@title='/CATEGORIES/"+CatName+"']/..//img[@alt='Add']")).click();
		Thread.sleep(2000l);
		System.out.println("Added Automation Categories to RHP");

		getWebElementByLocator(By.id("questionsAddCategory"));

		Thread.sleep(5000l);
		try{
			driver.findElement(By.xpath("//td[@class='categoryright']//span[@title='/CATEGORIES/"+CatName+"']")).getText();
			System.out.println("Category Name displayed in Right /CATEGORIES/"+CatName);
		}
		catch(Throwable e){
			driver.findElement(By.xpath("//td[@class='categoryright']//span[@title='"+CatName+"']")).getText();
			System.out.println("Category Name displayed in Right"+CatName);
		}
		Thread.sleep(2000l);

		getObjectxpathNew("QS_Other_CategorybtnDone").click();

		System.out.println("Categories added");

		Thread.sleep(2000l);

		getObjectxpathNew("QS_Other_AddedCategoryList");

		System.out.println("Categories added"
				+ getObjectxpathNew("QS_Other_AddedCategoryList")
				.getText());

		getObjectxpathNew("QS_Other_CommentTextArea").sendKeys(
				Data.getProperty("QuestionComment"));
		Thread.sleep(2000l);
		// Generic Information

	}
	//Approve MC Questions	
	public static void CreateAndApproveQuestions(String FolderName) throws InterruptedException {
		CreateMCQuestions("Automation Question for Approve");

		WaitForProgressBar();
		WebElement qsbyf = getObjectxpathNew("QS_QsbyFolderLink");
		qsbyf.click();
		String className = qsbyf.getAttribute("class");
		TestBase.comparestring(className, "QS_DefaultbyFolderClName");
		WaitForProgressBar();
		WaitForProgressBar();

		getObjectxpathDirect("//*[@id='questionListingFolder']//span[.='"+FolderName+"']").click();
		WaitForProgressBar();
		List<WebElement> qselaction = driver
				.findElements(By
						.xpath("//table[@id='questionsTable']//thead/tr//th"));
		int Count=1;
		for (WebElement gr:qselaction) {
			if(gr.getText().contains("Actions")){
				break;

			}
			Count++;

		}

		List<WebElement> qselm = driver
				.findElements(By
						.xpath("//table [@id='questionsTable']//tbody[@role='alert']//tr"));

		for (int i = 0; i < qselm.size(); i++) {
			System.out.println(
					driver
					.findElement(
							By.xpath("//table [@id='questionsTable']//tbody[@role='alert']/tr/td[2]"))
							.getText());
			if(driver
					.findElement(
							By.xpath("//table [@id='questionsTable']//tbody[@role='alert']/tr/td[2]"))
							.getText()
							.equals(Data.getProperty("QuestionTitle5"))){
				System.out.println("Automation Question for Approve");
				driver
				.findElement(
						By.xpath("//table[@id='questionsTable']//tbody[@role='alert']/tr/td["+Count+"]/a[contains(@onclick,'openDraftQuestion')]")).click();
				break;
			}


		}
		Thread.sleep(2000l);
		WaitForProgressBar();
		getObjectxpathNew("QS_Approve_McqEditApproveItem").click();
		//Verification Part
		ComapreTwoStringVal(getObjectxpathNew("QS_Approve_SucessfullyMsg")
				.getText(),"This question was successfully approved");
		getObjectxpathNew("QS_MC_CancelButtonText").click();
		Thread.sleep(2000l);
		WaitForProgressBar();
	}
	//Approve Bulk Questions
	public static void CreateBulkApproveQuestions(String FolderName) throws InterruptedException{
		ClickOnQuestionsTab() ;
		WebElement qsbyf = getObjectxpathNew("QS_QsbyFolderLink");
		qsbyf.click();
		Thread.sleep(5000l);
		String className = qsbyf.getAttribute("class");
		TestBase.comparestring(className, "QS_DefaultbyFolderClName");
		String bul="//*[@id='questionListingFolder']//span[.='"+FolderName+"']";
		driver.findElement(By.xpath(bul)).click();
		WaitForProgressBar();
		if(elementExists("//*[@id='questionsTable']//tr/*[.='No matching records found']")!=null){
			System.out.println("There are no questions in folder - "+FolderName+". Hence cannot do bulk approve.");
			return;
		}
		driver.switchTo().defaultContent();
		Actions we = new Actions(driver);
		we.click(driver.findElement(By.xpath("//input[@name='qListCheckAll']"))).build().perform();
		new Select(driver.findElement(By.id("bulkOptionSelect"))).selectByVisibleText("Change Status to Approved");
		Thread.sleep(2000l);
		Alert alert = driver.switchTo().alert();
		alert.accept();
		WaitForProgressBar();
		Thread.sleep(2000l);
		ComapreTwoStringVal(getObjectxpathNew("QS_ImportTabSeperated_ConfirmMsg").getText().trim(),"The selected question(s) have been approved and can now be used on an assessment.");
	}

	public static void ImportQuestionsTabSepetated(String FolderName) throws InterruptedException{
		ClickOnQuestionsTab() ;
		/*WaitForProgressBar();
		WebElement qsbyf = getObjectxpathNew("QS_QsbyFolderLink");
		qsbyf.click();
		Thread.sleep(5000l);
		String className = qsbyf.getAttribute("class");
		TestBase.comparestring(className, "QS_DefaultbyFolderClName");
		WaitForProgressBar();
		Thread.sleep(3000l);*/
		WebElement qsimp = getObjectxpathNew("QS_ImportQsLink");
		TestBase.comparestring(qsimp.getText().trim(), "QS_ImportQsLinkText");
		System.out.println("Verified the Presence of Import Questions");
		qsimp.click();
		Thread.sleep(5000l);
		getObjectxpathNew("QS_ImportTabSeperated_SelectFolder").click();
		Thread.sleep(5000l);
		System.out.println("Verified Select Folder window title");

		driver.findElement(By.xpath("//span[@title='/"+CONFIG_ENV.getProperty("RootFolderName")+"/"+FolderName+"']")).click();

		System.out.println("Select Folder a window title");
		Thread.sleep(2000l);


		System.out.println("Selected Folder :  "
				+ getObjectxpathNew("QS_ImportTabSeperated_SelectedFolderTXT")
				.getText());

		driver.findElement(By.id("tabdelimfile")).sendKeys(System.getProperty("user.dir")+"\\src\\Import\\ImportQuestion.txt");
		System.out.println("uploading files");
		driver.findElement(By.id("TXT")).click();
		System.out.println("Next to process");
		Thread.sleep(3000l);
		System.out.println(getObjectxpathNew("QS_ImportTabSeperated_ConfirmMsg").getText());

		System.out.println(getObjectxpathNew("QS_ImportTabSeperated_ReadyToImportItems").getText());
		System.out.println(getObjectxpathNew("QS_ImportTabSeperated_ItemsFailedValidation").getText());

		List<WebElement> importedlist = driver.findElements(By.xpath("//table[@id='itemsImportTable']//tr"));
		int count=1;
		System.out.println("---------Size------------"+(importedlist.size()-1));
		for(int i = 1; i < importedlist.size(); i++){
			System.out.println("---------"+count);
			System.out.println("|"+driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[1]")).getText()+"|");
			System.out.println("|"+driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[2]")).getText()+"|");
			System.out.println("|"+driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[3]")).getText()+"|");
			System.out.println("|"+driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[4]")).getText()+"|");
			System.out.println("|"+driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[5]")).getText()+"|");
			System.out.println("|"+driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[6]")).getText()+"|");
			System.out.println("|"+driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[7]")).getText()+"|");
			System.out.println("|"+driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[8]")).getText()+"|");
			System.out.println("|"+driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[9]")).getText()+"|");
			System.out.println("|"+driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[10]")).getText()+"|");
			System.out.println("|"+driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[11]")).getText()+"|");
			System.out.println("|"+driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[12]")).getText()+"|");
			System.out.println("|"+driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[13]")).getText()+"|");
			System.out.println("|"+driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[14]")).getText()+"|");
			System.out.println("|"+driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[15]")).getText()+"|");
			System.out.println("|"+driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[16]")).getText()+"|");
			System.out.println("|"+driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[17]")).getText()+"|");

			count++;

			switch(i){

			case 1: if(driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[1]")).getText().equals("1") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[2]")).getText().equals("1") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[3]")).getText().equals(CONFIG_ENV.getProperty("RootFolderName")+"/"+FolderName) &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[4]")).getText().equals("MC") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[5]")).getText().equals("What is your favorite Car?") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[6]")).getText().equals("ODDI") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[7]")).getText().equals("Suzuki") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[8]")).getText().equals("Honda") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[9]")).getText().equals("Yamaha") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[10]")).getText().equals("") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[11]")).getText().equals("C") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[12]")).getText().equals("") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[13]")).getText().equals("") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[14]")).getText().equals("Automation") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[15]")).getText().equals("AutomationGroup") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[16]")).getText().equals("Yes") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[17]")).getText().equals("DRAFT") 

					)
			{
				System.out.println("Data Match Row"+i);
				continue;
			}
			else{
				Assert.fail("Data Match fail Row"+i);

			}
			case 2: if(driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[1]")).getText().equals("2") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[2]")).getText().equals("2") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[3]")).getText().equals(CONFIG_ENV.getProperty("RootFolderName")+"/"+FolderName) &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[4]")).getText().equals("TF") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[5]")).getText().equals("Is ODDI your favorite Car?") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[6]")).getText().equals("TRUE") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[7]")).getText().equals("FALSE") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[8]")).getText().equals("") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[9]")).getText().equals("") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[10]")).getText().equals("") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[11]")).getText().equals("B") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[12]")).getText().equals("") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[13]")).getText().equals("") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[14]")).getText().equals("Automation") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[15]")).getText().equals("AutomationGroup") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[16]")).getText().equals("Y") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[17]")).getText().equals("DRAFT")  

					)
			{
				System.out.println("Data Match Row"+i);
				continue;
			}
			else{
				Assert.fail("Data Match fail Row"+i);

			}
			case 3: if(driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[1]")).getText().equals("3") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[2]")).getText().equals("3") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[3]")).getText().equals(CONFIG_ENV.getProperty("RootFolderName")+"/"+FolderName) &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[4]")).getText().equals("E") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[5]")).getText().equals("Write a Essay on your favorite Car.") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[6]")).getText().equals("") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[7]")).getText().equals("") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[8]")).getText().equals("") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[9]")).getText().equals("") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[10]")).getText().equals("") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[11]")).getText().equals("") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[12]")).getText().equals("") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[13]")).getText().equals("") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[14]")).getText().equals("Automation") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[15]")).getText().equals("AutomationGroup") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[16]")).getText().equals("") &&
					driver.findElement(By.xpath("//table[@id='itemsImportTable']//tr["+i+"]/td[17]")).getText().equals("DRAFT") 

					)
			{
				System.out.println("Data Match Row"+i);
				continue;
			}
			else{
				Assert.fail("Data Match fail Row"+i);

			}



			}

		}//for
		driver.findElement(By.xpath("//a[text()='Import Valid Questions']")).click();
		WaitForProgressBar();

		//Verification
		ComapreTwoStringVal(getObjectxpathNew("QS_ImportTabSeperated_ConfirmMsg").getText(),"Valid questions have been imported.");

	}
	//Import Questions RFT Format    
	public static void ImportQuestionsRFTFormat(String FolderName) throws InterruptedException {
		ClickOnQuestionsTab() ;
		/*WebElement qsbyf = getObjectxpathNew("QS_QsbyFolderLink");
		qsbyf.click();
		Thread.sleep(5000l);
		String className = qsbyf.getAttribute("class");
		TestBase.comparestring(className, "QS_DefaultbyFolderClName");
		WaitForProgressBar();*/
		WebElement qsimp = getObjectxpathNew("QS_ImportQsLink");
		TestBase.comparestring(qsimp.getText().trim(), "QS_ImportQsLinkText");
		System.out.println("Verified the Presence of Import Questions");
		qsimp.click();
		Thread.sleep(5000l);
		getObjectxpathNew("QS_ImportRFTFormat_SelectFolder").click();
		Thread.sleep(5000l);
		System.out.println("Verified Select Folder window title");

		driver.findElement(By.xpath("//span[@title='/"+CONFIG_ENV.getProperty("RootFolderName")+"/"+FolderName+"']")).click();

		System.out.println("Select Folder a window title");
		Thread.sleep(2000l);


		System.out.println("Selected Folder : "+ getObjectxpathNew("QS_ImportRTFFormat_SelectedFolderRTF").getText());

		driver.findElement(By.id("rtffile")).sendKeys(System.getProperty("user.dir")+"\\src\\Import\\res00001.dat");
		System.out.println("uploading files");
		Select importFilter=  new Select(driver.findElement(By.id("importWithFilter")));
		importFilter.selectByVisibleText("QTI - Include Fill in the Blank (XML, DAT files)");


		driver.findElement(By.id("RTF")).click();
		System.out.println("Next to process");
		WaitForProgressBar();
		Thread.sleep(2000);


		if (driver.findElement(	By.xpath(OR.getProperty("QS_ImportTabSeperated_ConfirmMsg"))).getText().equalsIgnoreCase(
				"Your file has been reviewed. Please review any possible error messages/issues and proceed with importing valid questions.")) {
			System.out.println("Validation Pass");
		} else {
			Assert.fail("RTF Import Validation message is not matching: Fail");
		}
		driver.findElement(By.xpath("//a[text()='Import Valid Questions']")).click();
		WaitForProgressBar();
		//Verification Part
		ComapreTwoStringVal(getObjectxpathNew("QS_ImportTabSeperated_ConfirmMsg").getText(),"Valid questions have been imported.");
	}


	public static void ClickOnQuestionsTab(){
		
		/*WebElement qsfolder1=driver.findElement(By.xpath("//*[@id='navbar']/ul[1]/li/a"));
		Locatable hoverItem = (Locatable) qsfolder1;
		Mouse mouse = ((HasInputDevices) driver).getMouse();
		mouse.mouseDown(hoverItem.getCoordinates());
		mouse.mouseUp(hoverItem.getCoordinates());*/
		
		
		driver.navigate().refresh();
		if(isAlertPresent())
			driver.switchTo().alert().accept();
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='navbar']/ul[1]/li/a")));
		driver.findElement(By.xpath("//*[@id='navbar']/ul[1]/li/a")).click();
		if(isAlertPresent())
			driver.switchTo().alert().accept();
		waitInSeconds(1);
		WaitForProgressBar();
		waitInSeconds(5);
	}

	//Search Questions By ID 
	public static void SearchQuestionsById(String FolderName) throws InterruptedException{
		ClickOnQuestionsTab();
		elementExists(OR.getProperty("QS_QsbyFolderLink")).click();
		WaitForProgressBar();
		System.out.println("Clicked on Question by Folder tab!");
		elementExists("//*[@id='questionListingFolder']//span[.='"+FolderName+"']").click();
		WaitForProgressBar();
		waitInSeconds(2);
		System.out.println("Clicked on Question by Folder - "+FolderName+"!");
		int tr = driver.findElements(By.xpath("//*[@id='questionsTable']/tbody/tr")).size(); //row count
		int th = driver.findElements(By.xpath("//*[@id='questionsTable']/thead/tr/th")).size(); //column count
		int revisiontd=0;
		if (tr==0)
			Assert.fail("No questions to select in the folder");
		else {
			//finding out the column number of revision
			for (int i = 3; i <= th; i++) {
				String column1 = "//*[@id='questionsTable']/thead/tr/th[";
				String column2 = "]";
				String status = driver.findElement(
						By.xpath(column1 + i + column2)).getText();

				if (status != null && status.trim().equalsIgnoreCase("ID/Rev")) {
					revisiontd = i;
				}
			}
		}
		String revID = driver.findElement(By.xpath("//*[@id='questionsTable']/tbody/tr[1]/td["+revisiontd+"]/a")).getText();
		revID = revID.substring(0, revID.indexOf("/")).trim();

		getObjectxpathNew("QS_SearchById_OmniBasicSearchInput").clear();
		getObjectxpathNew("QS_SearchById_OmniBasicSearchInput").sendKeys(revID);
		waitInSeconds(1);
		getObjectxpathNew("QS_SearchById_BtnDoQuestionsBasicSearch").click();
		WaitForProgressBar();

		int tr1 = driver.findElements(By.xpath("//*[@id='questionsTable']/tbody/tr")).size(); //row count after search
		String revID1="";
		if (tr1==0)
			Assert.fail("No result found after search");
		else if (tr1>1)
			Assert.fail("More than 1 question with same revId!");
		else {
			revID1 = driver.findElement(By.xpath("//*[@id='questionsTable']/tbody/tr[1]/td["+revisiontd+"]/a")).getText();
			revID1 = revID1.substring(0, revID1.indexOf("/")).trim();
		}

		//Verification part
		if(revID.equalsIgnoreCase(revID1)){
			System.out.println("Question searched with id "+revID);
		}
		else{
			Assert.fail("Question not searched with id "+revID);
		}
	}




	//Search Question By Name
	public static void SearchQuestionsByName(String FolderName) throws InterruptedException{
		ClickOnQuestionsTab();
		elementExists(OR.getProperty("QS_QsbyFolderLink")).click();
		WaitForProgressBar();
		System.out.println("Clicked on Question by Folder tab!");
		elementExists("//*[@id='questionListingFolder']//span[.='"+FolderName+"']").click();
		WaitForProgressBar();
		waitInSeconds(2);
		System.out.println("Clicked on Question by Folder - "+FolderName+"!");
		int tr = driver.findElements(By.xpath("//*[@id='questionsTable']/tbody/tr")).size(); //row count
		String questionName="";
		if (tr==0)
			Assert.fail("No questions to select in the folder");
		else {
			questionName=elementExists("//*[@id='questionsTable']//tr[1]/td[2]/a").getText().trim();
		}

		getObjectxpathNew("QS_SearchById_OmniBasicSearchInput").clear();
		getObjectxpathNew("QS_SearchById_OmniBasicSearchInput").sendKeys(questionName);
		waitInSeconds(1);
		getObjectxpathNew("QS_SearchById_BtnDoQuestionsBasicSearch").click();
		WaitForProgressBar();
		String actualResult="";
		int tr1 = driver.findElements(By.xpath("//*[@id='questionsTable']/tbody/tr")).size(); //row count after search
		if (tr1==0)
			Assert.fail("No result found after search");
		else {
			actualResult=elementExists("//*[@id='questionsTable']//tr[1]/td[2]/a").getText().trim();
		}

		//Verification part
		if(questionName.equalsIgnoreCase(actualResult)){
			System.out.println("Question searched with name "+questionName);
		}
		else{
			Assert.fail("Question not searched with name "+questionName);
		}
	}

	//Share Questions    
	public static void ShareFolder(String FolderName) throws InterruptedException{
		ClickOnQuestionsTab() ;
		WebElement itemsCogWheel=elementExists("//*[@id='questionListingFolder']//td[.='"+FolderName+"']/..//img[contains(@src,'cog.png')]");
		WebElement shareFolderLink=elementExists("//*[@id='questionListingFolder']//td[.='"+FolderName+"']/..//a[contains(text(),'Share Folder')]");

		new Actions(driver).moveToElement(itemsCogWheel).moveToElement(shareFolderLink).click().build().perform();


		/*WaitForProgressBar();
		WebElement qsbyf = getObjectxpathNew("QS_QsbyFolderLink");
		qsbyf.click();
		Thread.sleep(5000);
		String className = qsbyf.getAttribute("class");
		TestBase.comparestring(className, "QS_DefaultbyFolderClName");
		WaitForProgressBar();

		WebElement qsfolder1 = driver.findElement(By.xpath("//*[@id='questionListingFolder']//span[.='"+FolderName+"']/../span[2]/ul/li/img"));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
				qsfolder1, "color: yellow; border: 2px solid yellow;");
		Locatable hoverItem = (Locatable) qsfolder1;
		Mouse mouse = ((HasInputDevices) driver).getMouse();
		mouse.mouseMove(hoverItem.getCoordinates());
		Thread.sleep(5000l);
		String fd=qsfolder1.findElement(By.xpath("..//a[text()='Share Folder']")).getAttribute("onclick");
		String scriptName="shareFolderOpen";
		fd=fd.replaceAll("\"", "'");
		String [] javascr=fd.split(scriptName);
		String kj=javascr[1];
		System.out.println("Share Script"+js.executeScript(scriptName+kj));


		// driver.findElement(By.xpath("//ul[@class='optionsMenu shadow']//a[contains(text(),'Delete Folder')]")).click();
		 */		Thread.sleep(5000l);


		 if(getObjectxpathNew("QS_ShareFolder_ResourceNameSR").getText().equals(FolderName)){

			 System.out.println("Folder is select for Share");

		 }
		 else{
			 Assert.fail("Folder is not selected to Share");


		 }

		 WebElement readonly=
				 getObjectxpathNew("QS_ShareFolder_ReadOnlyTT");
		 readonly.click();

		 if(readonly.isSelected()){


			 System.out.println("Access is Selected");

		 }
		 else{
			 Assert.fail("Access is not selected to Share");


		 }
		 /**
	    new Select(driver.findElement(By.id("userUID"))).selectByVisibleText(Data.getProperty("usernameshare_string"));

	    if(new Select(driver.findElement(By.id("userUID"))).getAllSelectedOptions().get(0).getText().equals(Data.getProperty("usernameshare_string"))){

	    System.out.println("Selected Share User option"+new Select(driver.findElement(By.id("userUID"))).getAllSelectedOptions().get(0).getText());
	    }
	    else{
	    	Assert.fail("User is not selected to Share");
	    }
		  */
		 getObjectxpathNew("QS_ShareFolder_SaveShareResource").click();

		 WaitForProgressBar();
		 //Verification Part
		 if(getObjectxpathNew("QS_ShareFolder_ConfirMsg").getText().equals("The folder, "+FolderName+", was successfully shared.")){

		 }
		 else{
			 Assert.fail("Folder Sucessfully Shared with User");
		 }

	}

	//Delete QS In Folder 

	public static void DeleteQSInFolder(String Folder) throws InterruptedException{
		ClickOnQuestionsTab();
		if(elementExists("//*[@id='questionListingFolder']//span[.='"+Folder+"']")!=null){
			driver.findElement(By.xpath("//*[@id='questionListingFolder']//span[.='"+Folder+"']")).click(); 
			WaitForProgressBar();	
			Thread.sleep(2000);
			if(elementExists("//*[@id='questionsTable']//tr/*[.='No matching records found']")!=null){
				System.out.println("There are no questions in folder - "+Folder);
				return;
			}
			elementExists("//*[@id='qListCheckAll']").click();
			Select selectBulkAction=new Select(elementExists("//*[@id='bulkOptionSelect']"));
			selectBulkAction.selectByVisibleText("Remove Items");
			waitInSeconds(2);
			if(isAlertPresent())
				driver.switchTo().alert().accept();
			WaitForProgressBar();
			if(elementExists("//*[@id='questionsTable']//tr/*[.='No matching records found']")!=null){
				System.out.println("All Questions are deleted from folder - "+Folder);
				return;
			}else if(elementExists(OR.getProperty("Global_Error_Messase"))!=null 
					&& (elementExists(OR.getProperty("Global_Error_Messase")).getText().contains("The following questions were not deleted")
							|| elementExists(OR.getProperty("Global_Error_Messase")).getText().contains("None of the items could be deleted"))){
				System.out.println("All questions were not deleted from folder, some might be used on assessment.");
				System.out.println("Moving questions to another folder.");
				elementExists("//*[@id='qListCheckAll']").click();
				Select selectBulkAction1=new Select(elementExists("//*[@id='bulkOptionSelect']"));
				selectBulkAction1.selectByVisibleText("Move to Another Folder");
				elementExists("//*[@id='AddedFolderUI1']//*[@class='buttonintable selectBEFolderOpen']").click();
				waitInSeconds(1);
				elementExists("//*[@id='bulkEditFolderTreeDiv']//span[.='ITEMS']").click();
				WaitForProgressBar();
				elementExists("//*[@id='AddedFolderUI2']//*[@class='button']").click();
				WaitForProgressBar();
			}
		}else{
			System.out.println("the folder "+Folder+" does not exists!");
			return;
		}
	}


	public static void ExportQuestions(String QSFolderNametoExport)throws InterruptedException{
		ClickOnQuestionsTab() ;
		WebElement qsbyf = getObjectxpathNew("QS_QsbyFolderLink");
		qsbyf.click();
		Thread.sleep(5000l);
		String className = qsbyf.getAttribute("class");
		TestBase.comparestring(className, "QS_DefaultbyFolderClName");

		File zp = new File(System.getProperty("user.dir")+"\\src\\Download\\"+QSFolderNametoExport+".zip");
		File zp2= new File(System.getProperty("user.dir")+"\\src\\Download\\"+QSFolderNametoExport+".pdf");
		zp.deleteOnExit();
		zp2.deleteOnExit();
		Thread.sleep(2000l);
		WaitForProgressBar();
		WebElement qsfolder1 = driver.findElement(By.xpath("//span[@title='/"+CONFIG_ENV.getProperty("RootFolderName")+"/"+QSFolderNametoExport+"']/../span[2]/ul/li/img"));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
				qsfolder1, "color: yellow; border: 2px solid yellow;");
		Locatable hoverItem = (Locatable) qsfolder1;
		Mouse mouse = ((HasInputDevices) driver).getMouse();
		mouse.mouseMove(hoverItem.getCoordinates());


		String fd= qsfolder1.findElement(By.xpath("..//li[@class='exportAccess']//a")).getAttribute("onclick");

		String scriptName="exportQuestionsFolderOpen";
		fd=fd.replaceAll("\"", "'");
		String [] javascr=fd.split(scriptName);
		String kj=javascr[1];
		js.executeScript(scriptName+kj);
		Thread.sleep(3000l);

		getObjectxpathNew("QS_Export_ExportQuestionsText");
		getObjectxpathNew("QS_Export_ExportQuestionsFromResource").click();

		Thread.sleep(60000l);
		System.out.println("Checking the Export Window  is displayed or not");
		boolean windowOpen=false;
		try{
			if(driver.findElement(By.xpath(OR.getProperty("QS_Export_ExportQuestionsText"))).isDisplayed()){
				windowOpen=true;
				System.out.println("Checking the Export Window  is displayed");
			}
			System.out.println("Checking the Export Window  is displayed not displayed");
		}
		catch(Throwable e){
			if(windowOpen==true){
				getWebElementByLocator(By.linkText("Cancel")).click();
				System.out.println("Clicking on Cancel Button to Close Export Window.");
			}

		}

		int count=0;
		System.out.println("Count"+count);
		while(true){
			if (zp.exists()){
				break;
			}
			if (count==50000){
				System.out.println("Count"+count);
				Assert.fail("File Not Downlaoded");
			}
			count++;
		}
		Thread.sleep(10000l);

		File zp1 = new File(System.getProperty("user.dir")+"\\src\\Download");


		//ZipUtils zu = new ZipUtils();
		ZipUtils.extract(zp, zp1);

		Thread.sleep(10000l);
		if (zp2.exists()){

			System.out.println("File is Extracted on location");
		}
		else{
			Assert.fail("File is not Extracted");
		}

		try {
			PdfReader reader = new PdfReader(System.getProperty("user.dir")+"\\src\\Download\\"+QSFolderNametoExport+".pdf");

			System.out.println("This PDF has "+reader.getNumberOfPages()+" pages.");
			int counttext=0; 
			for(int i=1;i<=reader.getNumberOfPages();i++){

				if(!reader.isTampered()&&!reader.isEncrypted()){
					String page = PdfTextExtractor.getTextFromPage(reader, i);

					if(page.contains("what is your nick name ?")){
						System.out.println("page Extracted and contain Text");
						counttext++;
					}
					else{
						System.out.println("page Extracted and not contain Text");
					}

				}
			}
			//Verification part
			if (counttext>0){
				System.out.println("page Extracted contain Text times"+counttext);	
			}
			else{
				Assert.fail("page doesnot contain the Text");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static void WaitForProgressBar() {
		WebDriverWait wait = new WebDriverWait(driver, 250);

		if (wait.until(ExpectedConditions.invisibilityOfElementLocated(By
				.xpath("//div[@class='progress-bar green stripes']")))) {

			System.out.println("Progress bar invisible");
		}

	}

	public static void CleanUp(String Folder) throws InterruptedException{
		ClickOnQuestionsTab() ;
		WaitForProgressBar();

		WebElement qsbyf = getObjectxpathNew("QS_QsbyFolderLink");
		qsbyf.click();
		Thread.sleep(2000l);
		String className = qsbyf.getAttribute("class");
		TestBase.comparestring(className, "QS_DefaultbyFolderClName");
		WaitForProgressBar();
		driver.findElement(By.xpath("//span[@title='/"+CONFIG_ENV.getProperty("RootFolderName")+"/"+Folder+"']")).click();
		WaitForProgressBar();
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.textToBePresentInElement(By.xpath("//p[@id='gridFilter']"), "/"+CONFIG_ENV.getProperty("RootFolderName")+"/"+Folder));

		Thread.sleep(2000l);
		System.out.println("|"+driver.findElement(By.xpath("//p[@id='gridFilter']")).getText()+"|");
		if(driver.findElement(By.xpath("//p[@id='gridFilter']")).getText().equals("/"+CONFIG_ENV.getProperty("RootFolderName")+"/"+Folder)){
			System.out.println("Automation Folder is selected");
		}
		else{
			Assert.fail("Automation Folder Filter is not selected");
		}
		List<WebElement> qselaction = driver
				.findElements(By
						.xpath("//table[@id='questionsTable']//thead/tr//th"));
		int Count=1;
		for (WebElement gr:qselaction) {
			if(gr.getText().contains("Actions")){
				break;

			}
			Count++;

		}
		System.out.println("count------"+Count);
		List<WebElement> qselm = driver
				.findElements(By
						.xpath("//table [@id='questionsTable']//tbody[@role='alert']//tr"));
		int count1=0;
		WebElement nd1=null; 
		WebElement nd2=null;
		WebElement nd3=null;
		WebElement nd4=null;
		WebElement nd5=null;
		String QStitle="";
		String RevisionID="";
		System.out.println("|"+qselm.size()+"|");
		int NodeletedQS=0;

		for (int i = qselm.size(); i > 0; i--) {

			if(driver.findElement(
					By.xpath("//table [@id='questionsTable']//tbody[@role='alert']/tr/td")).getText().equals("No matching records found")){
				break;

			}

			nd1=driver.findElement(
					By.xpath("//table [@id='questionsTable']//tbody[@role='alert']/tr/td["+Count+"]"));



			if (i!=1){
				nd1=driver
						.findElement(
								By.xpath("//table[@id='questionsTable']//tbody[@role='alert']/tr["
										+ i + "]/td["+Count+"]"));
			}
			System.out.println("----"+nd1.getText());


			nd1.findElement(By.xpath(".//a[2]")).click();
			Thread.sleep(2000l);
			getObjectxpathNew("QS_Delete_Handler").click();
			count1++;
			WaitForProgressBar();
			//Verification Test
			//If Question not deleted move to 
			if (driver.findElement(By.id("ct100_ConfirMsgLbl")).getText().contains("You have successfully deleted the question")){

			}
			else{
				NodeletedQS++;

			}


		}

		if (NodeletedQS>0){

			System.out.println("Questions are not deleted"+NodeletedQS);
			//Calling for Move Method.

		}





	}

	public static List<String> CheckForFolderExist() throws InterruptedException{
		List <String> gh = new ArrayList<String>();
		ClickOnQuestionsTab() ;
		WebElement qsbyf = getObjectxpathNew("QS_QsbyFolderLink");
		qsbyf.click();
		Thread.sleep(5000l);
		String className = qsbyf.getAttribute("class");
		TestBase.comparestring(className, "QS_DefaultbyFolderClName");

		List <WebElement> er = driver.findElements(By.xpath("//*[@id='folderTreeDiv']/ul//li//span[@class='dynatree-title']"));
		for ( WebElement de:er){
			if(!de.getText().equals("")){
				//System.out.println(de.getText());
				gh.add(de.findElement(By.xpath("./span")).getAttribute("title"));
			}

		}


		return gh;
	}


	//Move Questions
	public static void MoveQuestions(String FromFolderName,String ToFolderName) throws InterruptedException{
		ClickOnQuestionsTab() ;
		WaitForProgressBar();
		WebElement qsbyf = getObjectxpathNew("QS_QsbyFolderLink");
		qsbyf.click();
		Thread.sleep(5000l);
		String className = qsbyf.getAttribute("class");
		TestBase.comparestring(className, "QS_DefaultbyFolderClName");
		Thread.sleep(5000l);
		String bul="//span[@title='/"+CONFIG_ENV.getProperty("RootFolderName")+"/"+FromFolderName+"']";
		driver.findElement(By.xpath(bul)).click();
		WaitForProgressBar();
		driver.switchTo().defaultContent();
		Thread.sleep(2000l);
		new Select(driver.findElement(By.id("bulkOptionSelect"))).selectByVisibleText("Move to Another Folder");
		Thread.sleep(5000l);
		Actions we = new Actions(driver);
		we.click(driver.findElement(By.name("qListCheckAll"))).build().perform();
		Thread.sleep(5000l);

		WaitForProgressBar();

		getObjectxpathNew("QS_MoveFolderSelectFolder").click();
		Thread.sleep(5000l);
		getObjectxpathNew("QS_Other_SelectFolderText");
		System.out.println("Verified Select Folder window title");
		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[@id='btnRefreshTree']"))));	

		getObjectxpathNew("QS_MoveFolderRefreshTree").click();
		WaitForProgressBar();
		driver.findElement(By.xpath("//span[@title='/"+CONFIG_ENV.getProperty("RootFolderName")+"/"+ToFolderName+"']")).click();

		Thread.sleep(2000l);
		getObjectxpathNew("QS_Other_SelectedQSFolder").getText();
		System.out.println("Selected Folder :  "
				+ getObjectxpathNew("QS_Other_SelectedQSFolder")
				.getText());

		getObjectxpathNew("QS_MoveQuestionButton").click();
		Thread.sleep(5000l);
		WaitForProgressBar();

		System.out.println(getObjectxpathNew("QS_BulkApprove_ConfirMsg").getText().equals("The selected question(s) have been moved to this folder: "+ToFolderName+"."));


	}
	public static void ChangeCategories(String FromCatName,String ToCatName) throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver, 60);

		ClickOnQuestionsTab() ;
		WaitForProgressBar();
		WebElement qsbyc = getObjectxpathNew("QS_QsbyCategoriesLink");
		qsbyc.click();
		Thread.sleep(5000l);
		String className1 = qsbyc.getAttribute("class");
		TestBase.comparestring(className1, "QS_DefaultbyCategoriesClName");
		System.out.println("Verifed Question by Categories is selected on Question_tab");
		WaitForProgressBar();
		driver.findElement(By.xpath("//span[@title='/CATEGORIES/"+FromCatName+"']")).click();
		WaitForProgressBar();
		if(driver.findElement(By.xpath("//p[@id='gridFilter']")).getText().equals("/CATEGORIES/"+FromCatName)){
			System.out.println("Automation category is selected");
		}
		else{
			Assert.fail("Automation Category Filter is not selected");
		}
		driver.switchTo().defaultContent();
		Thread.sleep(2000l);
		new Select(driver.findElement(By.id("bulkOptionSelect"))).selectByVisibleText("Change Category Assignments");
		Thread.sleep(5000l);
		Actions we = new Actions(driver);
		we.click(driver.findElement(By.xpath("//div[@id='bulkButtons']//div[@id='AddedCatUI1']//a[@class='buttonintable addBECategoryOpen']"))).build().perform();
		Thread.sleep(5000l);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[text()='Select Categories']"))));
		APP_LOGS.debug(driver.findElement(By.xpath("//span[text()='Select Categories']")).getText());
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@id='catAddBulkEditTreeDiv']//span[@title='/CATEGORIES/AutomationDoNotDisturbCatForMove']"))));
		driver.findElement(By.xpath("//div[@id='catAddBulkEditTreeDiv']//span[@title='/CATEGORIES/AutomationDoNotDisturbCatForMove']")).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[@id='btnSelectBulkEditCatsAddTree']"))));
		driver.findElement(By.xpath("//a[@id='btnSelectBulkEditCatsAddTree']")).click();
		WaitForProgressBar();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@id='AddedCatUI2']//a[contains(text(),'Remove Categories')]"))));
		driver.findElement(By.xpath("//div[@id='AddedCatUI2']//a[contains(text(),'Remove Categories')]")).click();

		Thread.sleep(10000l);
		WaitForProgressBar();
		getObjectxpath("QS_MoveCat_SelectCategory");

		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@id='remCatBulkEditTreeDiv']//span[@title='/CATEGORIES/Automation']"))));
		driver.findElement(By.xpath("//div[@id='remCatBulkEditTreeDiv']//span[@title='/CATEGORIES/Automation']")).click();

		driver.findElement(By.id("btnSelectBulkEditCatsAddTree")).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//table[@id='questionsTable']/thead/tr/th/input[@id='qListCheckAll']"))));
		driver.findElement(By.xpath("//table[@id='questionsTable']/thead/tr/th/input[@id='qListCheckAll']")).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@id='AddedCatUI3']//a[contains(text(),'Update Categories')]"))));
		driver.findElement(By.xpath("//div[@id='AddedCatUI3']//a[contains(text(),'Update Categories')]")).click();






	}

	public static void AddQStoAssessment(String FolderName) throws InterruptedException{
		WaitForProgressBar();
		driver.findElement(By.linkText("Assessments")).click();
		WaitForProgressBar();
		driver.findElement(By.partialLinkText("Create New Assessment")).click();
		WaitForProgressBar();
		String Assessmentid=getObjectxpathDirect("//div[@id='sidebarcontent']//table[@class='imagetable']//td[@class='formlabel']/strong").getText();
		driver.findElement(By.id("examTitle")).sendKeys("Automation Assement");
		Thread.sleep(2000l);
		driver.findElement(By.partialLinkText("Select Folder")).click();
		WaitForProgressBar();
		driver.findElement(By.id("examfolderTreeDiv")).findElement(By.xpath(".//span[@title='/EXAMS']")).click();
		WaitForProgressBar();
		driver.findElement(By.partialLinkText("Add Questions to Assessment")).click();
		WaitForProgressBar();
		getObjectxpathDirect("//span[text()='Add Questions to Assessment']");
		Thread.sleep(2000l);
		driver.findElement(By.id("omniBasicSearchInput")).sendKeys("123671");
		Thread.sleep(2000l);
		driver.findElement(By.linkText("Search")).click();
		WaitForProgressBar();
		driver.findElement(By.id("qSelectCheckAll")).click();
		WaitForProgressBar();
		driver.findElement(By.linkText("Add Selected to Assessment")).click();
		WaitForProgressBar();
		driver.findElement(By.linkText("Close")).click();
		WaitForProgressBar();
		driver.findElement(By.linkText("Save")).click();
		//Moving on the Questions:
		WaitForProgressBar();
		driver.findElement(By.linkText("Questions")).click();
		WaitForProgressBar();
		driver.findElement(By.id("omniBasicSearchInput")).sendKeys("123671");
		Thread.sleep(2000l);
		driver.findElement(By.linkText("Search")).click();
		WaitForProgressBar();
		List<WebElement> qselaction = driver
				.findElements(By
						.xpath("//table[@id='questionsTable']//thead/tr//th"));
		int Count=1;
		for (WebElement gr:qselaction) {
			if(gr.getText().contains("Actions")){
				break;

			}
			Count++;

		}
		int Count1=1;
		for (WebElement gr:qselaction) {
			if(gr.getText().contains("ID/Rev")){
				break;

			}
			Count1++;

		}
		List<WebElement> qselm = driver
				.findElements(By
						.xpath("//table [@id='questionsTable']//tbody[@role='alert']//tr"));

		WebElement nd1=null; 
		WebElement nd2=null;

		System.out.println("|"+qselm.size()+"|");


		for (int i = qselm.size(); i > 0; i--) {

			if(driver.findElement(
					By.xpath("//table [@id='questionsTable']//tbody[@role='alert']/tr/td")).getText().equals("No matching records found")){
				break;

			}

			nd1=driver.findElement(
					By.xpath("//table [@id='questionsTable']//tbody[@role='alert']/tr/td["+Count+"]"));
			nd2=driver.findElement(
					By.xpath("//table [@id='questionsTable']//tbody[@role='alert']/tr/td["+Count1+"]"));



			if (i!=1){
				nd1=driver
						.findElement(
								By.xpath("//table[@id='questionsTable']//tbody[@role='alert']/tr["
										+ i + "]/td["+Count+"]"));
				nd2=driver
						.findElement(
								By.xpath("//table[@id='questionsTable']//tbody[@role='alert']/tr["
										+ i + "]/td["+Count1+"]"));
			}
			System.out.println("----"+nd2.getText());

			if(nd2.getText().contains("123671")){
				nd1.findElement(By.xpath(".//a[2]")).click();
				Thread.sleep(2000l);
				getObjectxpathNew("QS_Delete_Handler").click();
				WaitForProgressBar();
				if (driver.findElement(By.id("deleteQuestion")).findElement(By.xpath("//div[@class='messageDiv']//span[@class='modal_messageLbl']//li")).getText().contains("This question was already used in an assessment and cannot be deleted")){
					System.out.println("Question in Assessement not deleted");
				}
				else{

					Assert.fail("Question canot Deleted message is not observed"); 
				}
				WebElement xloc=driver.findElement(By.id("deleteQuestion")).findElement(By.xpath("preceding-sibling::div"));
				xloc.findElement(By.xpath(".//img[contains(@src,'x.png')]")).click();
				WaitForProgressBar();
			}


		}
		//Back to Assessment
		driver.findElement(By.linkText("Assessments")).click();
		WaitForProgressBar();
		driver.findElement(By.id("examIdOrName")).sendKeys(Assessmentid);
		Thread.sleep(2000l);
		driver.findElement(By.linkText("Search")).click();
		WaitForProgressBar();
		//check for the Actions Column Present
		List<WebElement> aselaction = driver
				.findElements(By
						.xpath("//table[@id='examsTable']//thead/tr//th"));
		int Count3=1;
		for (WebElement gr:aselaction) {
			if(gr.getText().contains("Actions")){
				break;

			}
			Count3++;

		}
		int Count4=1;
		for (WebElement gr:aselaction) {
			if(gr.getText().contains("Assessment ID")){
				break;

			}
			Count4++;

		}
		//add the Column if not present
		if(Count3==1){

			driver.findElement(By.id("cog_examsTable")).click();
			new Select(driver.findElement(By.id("addCol_examsTable"))).selectByVisibleText("Actions ");
			WaitForProgressBar();


		}
		if(Count4==1){
			driver.findElement(By.id("cog_examsTable")).click();
			new Select(driver.findElement(By.id("addCol_examsTable"))).selectByVisibleText("Assessment ID ");
			WaitForProgressBar();

		}
		//Verification

		List<WebElement> aselaction1 = driver
				.findElements(By
						.xpath("//table[@id='examsTable']//thead/tr//th"));
		Count3=1;
		for (WebElement gr:aselaction1) {
			if(gr.getText().contains("Actions")){
				break;

			}
			Count3++;

		}
		Count4=1;
		for (WebElement gr:aselaction1) {
			if(gr.getText().contains("Assessment ID")){
				break;

			}
			Count4++;

		}

		if(Count4==1||Count3==1){
			Assert.fail("Actions or Assement Column is not added");

		}
		List<WebElement> aselm = driver
				.findElements(By
						.xpath("//table [@id='examsTable']//tbody[@role='alert']//tr"));

		WebElement nd3=null; 
		WebElement nd4=null;

		System.out.println("|"+aselm.size()+"|");

		int count=0;
		for (int i = aselm.size(); i > 0; i--) {

			if(driver.findElement(
					By.xpath("//table [@id='examsTable']//tbody[@role='alert']/tr/td")).getText().equals("No matching records found")){
				break;

			}

			nd3=driver.findElement(
					By.xpath("//table [@id='examsTable']//tbody[@role='alert']/tr/td["+Count3+"]"));
			nd4=driver.findElement(
					By.xpath("//table [@id='examsTable']//tbody[@role='alert']/tr/td["+Count4+"]"));



			if (i!=1){
				nd3=driver
						.findElement(
								By.xpath("//table[@id='examsTable']//tbody[@role='alert']/tr["
										+ i + "]/td["+Count3+"]"));
				nd4=driver
						.findElement(
								By.xpath("//table[@id='examsTable']//tbody[@role='alert']/tr["
										+ i + "]/td["+Count4+"]"));
			}
			System.out.println("----"+nd4.getText());

			if(nd4.getText().contains(Assessmentid)){
				nd3.findElement(By.xpath(".//a[2]")).click();
				Thread.sleep(2000l);
				getObjectxpathDirect("//a[@id='retireExamListingBtn']").click();
				WaitForProgressBar();
				count++;
				break;
			}
		}
		if(count>0 && driver.findElement(By.id("ct100_ConfirMsgLbl")).getText().contains("You have successfully deleted the assessment")){
			System.out.println("Assesment Deleleted");
		}
		else{
			Assert.fail("Assessment not deleted");
		}


	}

	public static void checkFortimeNeeded(By by){
		Date nt = new Date();
		System.out.println(nt.getTime());
		driver.findElement(by);
		Date nt1 = new Date();
		System.out.println(nt1.getTime());
		System.out.println("diff"+by+(nt1.getTime()-nt.getTime()));
	}
	public static WebElement getWebElementByLocator(By by){

		WebDriverWait wait = new WebDriverWait(driver, 2);

		try{
			for (int second = 0;; second++) {
				Thread.sleep(1000l);
				if (second >= 5){
					Assert.fail("timeout : " + by);}
				try {
					if (wait.until(ExpectedConditions.visibilityOfElementLocated(by)) != null)
						break;
				} catch (Exception e) {
					APP_LOGS.debug("---value-- "+by); 
				}
			}

			APP_LOGS.debug("-----"+driver.findElement(by).getText());
			return driver.findElement(by);
		}catch(Throwable t){
			// report error
			APP_LOGS.debug("No element present" +by);
			ErrorUtil.addVerificationFailure(t);
			Assert.fail(t.getMessage());

			return null;
		}
	}
	public static void searchQuestionByName(String name) throws InterruptedException{
		ClickOnQuestionsTab();
		Thread.sleep(3000);
		driver.findElement(By.xpath(OR.getProperty("QS_Search_Box"))).clear();
		driver.findElement(By.xpath(OR.getProperty("QS_Search_Box"))).sendKeys(name);
		Thread.sleep(2000);
		driver.findElement(By.xpath(OR.getProperty("QS_Search_Box"))).sendKeys(Keys.RETURN);
		QuestionUtil.WaitForProgressBar();
		Thread.sleep(10000);
	}

	public static void approveQuestionByName(String name) throws InterruptedException{
		searchQuestionByName(name);
		driver.findElement(By.xpath("//*[@id='questionsTable']/tbody/tr/td[2]/a")).click();
		QuestionUtil.WaitForProgressBar();
		Thread.sleep(3000);
		driver.findElement(By.xpath(OR.getProperty("QS_Approve_Button"))).click();
		QuestionUtil.WaitForProgressBar();
		Thread.sleep(2000);
		getObjectxpathNew("QS_MC_CancelButtonText").click();
		WaitForProgressBar();
		Thread.sleep(2000l);
	}

	public static void createNewRevision(String name) throws InterruptedException{
		searchQuestionByName(name);
		driver.findElement(By.xpath("//*[@id='questionsTable']/tbody/tr/td[2]/a")).click();
		QuestionUtil.WaitForProgressBar();
		driver.findElement(By.xpath(OR.getProperty("QS_CreateNewRevision_Button"))).click();
		QuestionUtil.WaitForProgressBar();

		WebElement we;

		for (int i = 1; i < 2; i++) {
			driver.switchTo().frame(i);
			we = driver.switchTo().activeElement();
			we.click();
			we.clear();
			we.sendKeys("New Edited Text");
			System.out.println("TagName--:" + we.getTagName());

			driver.switchTo().defaultContent();
			Thread.sleep(2000);
		}

		driver.findElement(By.xpath(OR.getProperty("QS_Approve_Button"))).click();
		QuestionUtil.WaitForProgressBar();
		getObjectxpathNew("QS_MC_CancelButtonText").click();
		WaitForProgressBar();
		Thread.sleep(2000l);

	}

	public static void create_ApproveQuestion(String test_Folder,String questionType, String QStitle) throws InterruptedException {
		int noOfAnswerValues=0;
		ClickOnQuestionsTab() ;
		//if Question title is blank, then pass default title
		if(QStitle.equals(""))
			QStitle=randomStringGen("QsTitle");

		if(questionType.equalsIgnoreCase("MC")){
			elementExists(OR.getProperty("CreateMC_Link")).click();
			noOfAnswerValues=4;
		}else if(questionType.equalsIgnoreCase("TF")){
			elementExists(OR.getProperty("CreateTF_Link")).click();
		}else if(questionType.equalsIgnoreCase("ESSAY")){
			elementExists(OR.getProperty("CreateEssay_Link")).click();
		}else if(questionType.equalsIgnoreCase("FITB")){
			elementExists(OR.getProperty("CreateFITB_Link")).click();
		}else{
			Assert.fail("Incorrect value for Question type is passed");
		}
		WaitForProgressBar();
		Thread.sleep(2000);
		elementExists(OR.getProperty("QuestionTitle")).sendKeys(QStitle);
		System.out.println("Entered Question title = "+QStitle);
		elementExists(OR.getProperty("SelectFolderLink")).click();
		System.out.println("Clicked on Select Folder Button!");
		Thread.sleep(2000);
		elementExists(OR.getProperty("QS_Other_SelectFolderText"));
		System.out.println("Verified that Select Folder Popup is present!");
		driver.findElement(By.xpath("//*[@id='selectFolderTree']//span[text()='"+test_Folder+"']")).click();
		WaitForProgressBar();
		Thread.sleep(2000);
		System.out.println("Selected Folder :  "+ elementExists(OR.getProperty("QS_Other_SelectedQSFolder")).getText());
		elementExists(OR.getProperty("QS_Other_SelectGroup")).sendKeys(Data.getProperty("QuestionsGroup"));
		Thread.sleep(2000);

		//data values
		String values[] = { Data.getProperty("QuestionName"), Data.getProperty("QuestionOption1"), Data.getProperty("QuestionOption2"),
				Data.getProperty("QuestionOption3"), Data.getProperty("QuestionOption4") };
		//frame xpaths
		String frameXpath[] = { OR.getProperty("Questionpath"),	OR.getProperty("choiceonepath"), OR.getProperty("choicetwopath"), 
				OR.getProperty("choicethreepath"), OR.getProperty("choicefourpath") };

		WebElement we;
		//fill values in respective frames
		for (int i = 0; i <=noOfAnswerValues; i++) {
			driver.switchTo().frame(driver.findElement(By.xpath(frameXpath[i])));
			((JavascriptExecutor) driver).executeScript("document.body.innerHTML = '<p><br></p>'");
			we = driver.findElement(By.tagName("p"));
			we.click();
			Thread.sleep(4000);
			we.sendKeys(values[i]);
			Thread.sleep(4000);
			driver.switchTo().defaultContent();
			Thread.sleep(2000);
		}

		//only for FITB Questions
		if(questionType.equalsIgnoreCase("FITB")){
			elementExists(OR.getProperty("QS_FITB_blanksTableHeaders")).click();
			System.out.println("Clicked on Create new blank button!");
			WaitForProgressBar();
			Thread.sleep(2000);
			elementExists(OR.getProperty("QS_FITB_blankText1")).sendKeys("Tom");
			System.out.println("Entered blank answer!");
			Thread.sleep(2000);
		}

		//only for MC Questions
		if(questionType.equalsIgnoreCase("MC")){
			elementExists(OR.getProperty("correctMCQCheckBox")).click();
		}


		//click on approve button
		elementExists(OR.getProperty("QS_Approve_Button")).click();
		System.out.println("Clicked on Approve button!");
		Thread.sleep(2000);
		WaitForProgressBar();
		if(elementExists(OR.getProperty("Global_Error_Messase"))!=null)
			System.out.println(questionType+" question approved successfully!");
	}


	public static void create_SaveQuestion(String test_Folder,String questionType, String QStitle, String categoryName) throws InterruptedException {
		int noOfAnswerValues=0;
		ClickOnQuestionsTab() ;
		//if Question title is blank, then pass default title
		if(QStitle.equals(""))
			QStitle=randomStringGen("QsTitle");

		if(questionType.equalsIgnoreCase("MC")){
			elementExists(OR.getProperty("CreateMC_Link")).click();
			noOfAnswerValues=4;
		}else if(questionType.equalsIgnoreCase("TF")){
			elementExists(OR.getProperty("CreateTF_Link")).click();
		}else if(questionType.equalsIgnoreCase("ESSAY")){
			elementExists(OR.getProperty("CreateEssay_Link")).click();
		}else if(questionType.equalsIgnoreCase("FITB")){
			elementExists(OR.getProperty("CreateFITB_Link")).click();
		}else{
			Assert.fail("Incorrect value for Question type is passed");
		}
		WaitForProgressBar();
		Thread.sleep(2000);
		elementExists(OR.getProperty("QuestionTitle")).sendKeys(QStitle);
		System.out.println("Entered Question title = "+QStitle);
		elementExists(OR.getProperty("SelectFolderLink")).click();
		System.out.println("Clicked on Select Folder Button!");
		Thread.sleep(2000);
		elementExists(OR.getProperty("QS_Other_SelectFolderText"));
		System.out.println("Verified that Select Folder Popup is present!");
		driver.findElement(By.xpath("//*[@id='selectFolderTree']//span[text()='"+test_Folder+"']")).click();
		WaitForProgressBar();
		Thread.sleep(2000);
		System.out.println("Selected Folder :  "+ elementExists(OR.getProperty("QS_Other_SelectedQSFolder")).getText());
		elementExists(OR.getProperty("QS_Other_SelectGroup")).sendKeys(Data.getProperty("QuestionsGroup"));
		Thread.sleep(2000);

		//add Question Category
		if(!categoryName.equals("")){
			elementExists(OR.getProperty("QS_Other_AddCategoryOpenButton")).click();
			waitInSeconds(1);
			System.out.println("Clicked on Add Category button!");
			if(elementExists(OR.getProperty("QS_Other_AddCategoryQSText"))!=null)
				System.out.println("'Add Categories to Question' popup opened!");
			else
				Assert.assertTrue("'Add Categories to Question' popup did not opned!", false);
			elementExists("//*[@id='categoriesToAdd']//td[.='"+categoryName+"']/..//span/img[contains(@src,'add.png')]").click();
			waitInSeconds(1);
			System.out.println("Added Automation Category to RHP!");
			if(elementExists("//*[@id='categoriesToRemove']/tbody/tr[.='"+categoryName+"']//img[contains(@src,'delete.png')]")!=null)
				System.out.println("Category Name displayed in Right "+categoryName);
			else
				Assert.assertTrue("Category Name displayed in Right "+categoryName, false);
			elementExists(OR.getProperty("QS_Other_CategorybtnDone")).click();
			WaitForProgressBar();
			System.out.println("Category added!");
			if(elementExists(OR.getProperty("QS_Other_AddedCategoryList"))!=null)
				System.out.println("Categories added: "+ elementExists(OR.getProperty("QS_Other_AddedCategoryList")).getText());
		}

		//data values
		String values[] = { Data.getProperty("QuestionName"), Data.getProperty("QuestionOption1"), Data.getProperty("QuestionOption2"),
				Data.getProperty("QuestionOption3"), Data.getProperty("QuestionOption4") };
		//frame xpaths
		String frameXpath[] = { OR.getProperty("Questionpath"),	OR.getProperty("choiceonepath"), OR.getProperty("choicetwopath"), 
				OR.getProperty("choicethreepath"), OR.getProperty("choicefourpath") };

		WebElement we;
		//fill values in respective frames
		for (int i = 0; i <=noOfAnswerValues; i++) {
			driver.switchTo().frame(driver.findElement(By.xpath(frameXpath[i])));
			((JavascriptExecutor) driver).executeScript("document.body.innerHTML = '<p><br></p>'");
			we = driver.findElement(By.tagName("p"));
			we.click();
			Thread.sleep(2000);
			we.sendKeys(values[i]);
			Thread.sleep(2000);
			driver.switchTo().defaultContent();
			Thread.sleep(2000);
		}

		//only for FITB Questions
		if(questionType.equalsIgnoreCase("FITB")){
			elementExists(OR.getProperty("QS_FITB_blanksTableHeaders")).click();
			System.out.println("Clicked on Create new blank button!");
			WaitForProgressBar();
			Thread.sleep(2000);
			elementExists(OR.getProperty("QS_FITB_blankText1")).sendKeys("Tom");
			System.out.println("Entered blank answer!");
			Thread.sleep(2000);
		}

		//only for MC Questions
		if(questionType.equalsIgnoreCase("MC")){
			elementExists(OR.getProperty("correctMCQCheckBox")).click();
		}


		//click on approve button
		elementExists("//*[@id='createQuestionForm']//a[text()='Save']").click();
		System.out.println("Clicked on Save button!");
		Thread.sleep(2000);
		WaitForProgressBar();
		if(elementExists(OR.getProperty("Global_Error_Messase"))!=null)
			System.out.println(questionType+" question saved successfully!");
	}
}



