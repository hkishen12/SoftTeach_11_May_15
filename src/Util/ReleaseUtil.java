package Util;

import java.text.ParseException;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Base.TestBase;

public class ReleaseUtil extends TestBase
{
	public static WebDriverWait wait = new WebDriverWait(driver,120);


	public static void VerifyEI2447TabDelimited (String FolderName) throws InterruptedException{
		QuestionUtil.CreateQSFolder(FolderName);
		QuestionUtil.ClickOnQuestionsTab() ; //click on the question tab
		getObjectxpathNew("QS_ImportQsLink").click(); //click on Import questions button
		getObjectxpathNew("QS_ImportTabSeperated_SelectFolder").click(); //Select folder
		driver.findElement(
				By.xpath("//span[@title='/"
						+ (CONFIG_ENV.getProperty("RootFolderName")) + "/"
						+ FolderName + "']")).click();
		QuestionUtil.WaitForProgressBar();
		driver.findElement(By.id("tabdelimfile")).sendKeys(System.getProperty("user.dir")+"\\src\\Import\\QRGs_Questions_Tab_sample_Draft.txt");
		System.out.println("uploading files");
		getObjectxpathNew("QS_Import_Draftonly_Tab_radio").click();
		driver.findElement(By.id("TXT")).click();
		QuestionUtil.WaitForProgressBar();
		Thread.sleep(5001);

		int tr = driver.findElements(By.xpath("//table[@id='itemsImportTable']//tr")).size();
		for(int i = 1; i<tr ; i++){
			if (driver.findElement(By.xpath("//*[@id='itemsImportTable']/tbody/tr["+i+"]/td[22]")).getText().trim().equalsIgnoreCase("DRAFT")){
				System.out.println("Status is DRAFT for Row:"+i);
			}
			else{
				Assert.fail("Status is not Matching/DRAFT forRow:"+i);
			}

		}

		getObjectxpathNew("QS_ImportButton").click();
		QuestionUtil.WaitForProgressBar();
		Thread.sleep(5001);
		QuestionUtil.ComapreTwoStringVal(getObjectxpathNew("QS_ImportTabSeperated_ConfirmMsg").getText(),"Valid questions have been imported.");
	}


	public static void verifyEI3135(String assessment_title) throws Exception {
		Thread.sleep(3000);
		AssessmentUtil.navigateToCreateNewQuestionAssessmentPage();
		System.out.println("Reached assessment creation page");
		AssessmentUtil.doNewAssessmentWithTitle(assessment_title);
		AssessmentUtil.doNewAssessmentAddQuestions();
		AssessmentUtil.doSaveCreatedAssessment();
		AssessmentUtil.doPost2assessment();
		if(elementExists(OR.getProperty("posting_Error_Message_icon"))==null){
			Assert.fail("Post assessment: Getting unexpected error while posting assessment");
		}
		else
			System.out.println("Post assessment:Assessment got posted");
	}

	public static void CreateUser(String FirstName, String LastName, String Email, String Password)
	{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement adminLink=driver.findElement(By.xpath(OR.getProperty("AdminLink")));
		WebElement usersLink=driver.findElement(By.xpath(OR.getProperty("UsersLink")));
		wait.until(ExpectedConditions.elementToBeClickable(adminLink));
		Actions  actions  = new Actions(driver);
		actions.moveToElement(adminLink).moveToElement(usersLink).click().build().perform();
		QuestionUtil.WaitForProgressBar();
		driver.findElement(By.xpath(OR.getProperty("AddNewUserButton"))).click();
		QuestionUtil.WaitForProgressBar();
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(OR.getProperty("FirstName_input")))));

		driver.findElement(By.xpath(OR.getProperty("FirstName_input"))).sendKeys(FirstName);
		driver.findElement(By.xpath(OR.getProperty("LastName_input"))).sendKeys(LastName);
		driver.findElement(By.xpath(OR.getProperty("Email_input"))).sendKeys(Email);
		driver.findElement(By.xpath(OR.getProperty("Password_input"))).sendKeys(Password);
		driver.findElement(By.xpath(OR.getProperty("ConfirmPassword_input"))).sendKeys(Password);
		driver.findElement(By.xpath(OR.getProperty("SaveUser_button"))).click();
		WaitForProgressBar();

		System.out.println(Email);

	}

	public static void VerifyEI2912(String Test_Folder,String Test_Category,String Test_QsTitle,String Test_Category1) throws Exception {
		QuestionUtil.CreateQSFolder(Test_Folder);
		QuestionUtil.create_ApproveQuestion(Test_Folder,"TF",Test_QsTitle);
		CategoryUtil.CreateCategory(Test_Category);
		QuestionUtil.addQsToCategory(Test_Category,Test_QsTitle);
		CategoryUtil.CreateCategory(Test_Category1);
		CategoryUtil.changeCategoryParent(Test_Category,Test_Category1);
		QuestionUtil.searchQuestionByName(Test_QsTitle);
		driver.findElement(By.xpath("//*[@id='questionsTable']/tbody/tr/td[2]/a")).click();
		QuestionUtil.WaitForProgressBar();
		String categorypath_after=driver.findElement(By.xpath("//*[@id='categoryList']//a")).getAttribute("title");
		System.out.println(categorypath_after);
		if(categorypath_after.equalsIgnoreCase("CATEGORIES/"+Test_Category1+"/"+Test_Category)){
			System.out.println("Tooltip shows correct path when we move destination folder");
		}else{
			Assert.fail("Tooltip does not show correct path when we move destination folder");
		}

	}
	public static void searchUserWithEmail(String email) throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement adminLink=driver.findElement(By.xpath(OR.getProperty("AdminLink")));
		WebElement usersLink=driver.findElement(By.xpath(OR.getProperty("UsersLink")));
		wait.until(ExpectedConditions.elementToBeClickable(adminLink));
		Actions  actions  = new Actions(driver);
		actions.moveToElement(adminLink).moveToElement(usersLink).click().build().perform();
		QuestionUtil.WaitForProgressBar();
		elementExists("//*[@id='omniSearch']").sendKeys(email);
		Thread.sleep(1000);
		elementExists("//*[@id='searchUsers']").click();//click on search button
		WaitForProgressBar();
		if(elementExists("//*[@id='userListTable']/tbody/tr/td[4]").getText().trim().equalsIgnoreCase(email)){
			System.out.println("User with email id "+email+" searched successfully!!");
		}
		else
			Assert.fail("Failed to search user with email id "+email);
	}

	public static void VerifyEI2926(String QuesfolderName,String questionTitle,String assessmentTitle,String email) throws Exception {
		QuestionUtil.CreateQSFolder(QuesfolderName);//create Question folder
		QuestionUtil.create_ApproveQuestion(QuesfolderName, "TF", questionTitle);
		CreateUser("mike", "tayson",email,"123456");//create user

		searchUserWithEmail(email);
		elementExists("//*[@id='userListTable']//img[@src='/STW-war/Icons/edit-green.gif']").click();//click on edit button
		WaitForProgressBar();

		driver.findElement(By.xpath("//*[@id='viewMyaccountSection_1']//input[@name='mainAccessRightsExamsFull']")).click();//full access to assessment
		driver.findElement(By.xpath("//*[@id='itemFolderDiv']//span[text()='"+QuesfolderName+"']/../span[2]/input[@class='qfViewPerm']")).click();//give read-only access to question folder 
		driver.findElement(By.xpath(OR.getProperty("SaveUser_button"))).click();
		QuestionUtil.WaitForProgressBar();

		TestUtil.doLogout();
		isBrowserOpened=false;
		openBrowser();	
		TestUtil.doLogin(email,"123456");// login with new user

		AssessmentUtil.navigateToCreateNewQuestionAssessmentPage();//navigate to create new assessment page
		AssessmentUtil.doNewAssessmentWithTitle(assessmentTitle);//give assessment title and select EXAM folder
		AssessmentUtil.doNewAssessmentAddQuestionWithTitle(questionTitle);//add question to assessment
		AssessmentUtil.doSaveCreatedAssessment();
		if(driver.findElement(By.xpath(OR.getProperty("QS_ImportTabSeperated_ConfirmMsg"))).isDisplayed()){
			System.out.println("Able to save assessment with questions that have read-only permissions");
			elementExists("//*[@id='examQuestionsTable']//tr//img[@src='/STW-war/Icons/delete.png']").click();//delete added question from assessment - cleanup
			WaitForProgressBar();
			AssessmentUtil.doSaveCreatedAssessment();
			WaitForProgressBar();
			elementExists("//*[@id='examCreateEdit']//div[2]/a[text()='Cancel']").click();
			WaitForProgressBar();
		}else{
			elementExists("//*[@id='examQuestionsTable']//tr//img[@src='/STW-war/Icons/delete.png']").click();//delete added question from assessment - cleanup
			WaitForProgressBar();
			AssessmentUtil.doSaveCreatedAssessment();
			WaitForProgressBar();
			AssessmentUtil.ClickOnAssessmentsTab();
			elementExists("//*[@id='examCreateEdit']//div[2]/a[text()='Cancel']").click();
			WaitForProgressBar();
		}
	}

	public static void VerifyEI2632 (String FirstName, String LastName, String Email, String Password) throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement adminLink=driver.findElement(By.xpath(OR.getProperty("AdminLink")));
		WebElement usersLink=driver.findElement(By.xpath(OR.getProperty("UsersLink")));
		wait.until(ExpectedConditions.elementToBeClickable(adminLink));
		Actions  actions  = new Actions(driver);
		actions.moveToElement(adminLink).moveToElement(usersLink).click().build().perform();
		QuestionUtil.WaitForProgressBar();
		driver.findElement(By.xpath(OR.getProperty("AddNewUserButton"))).click();
		QuestionUtil.WaitForProgressBar();
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(OR.getProperty("FirstName_input")))));

		driver.findElement(By.xpath(OR.getProperty("FirstName_input"))).sendKeys(FirstName);
		driver.findElement(By.xpath(OR.getProperty("LastName_input"))).sendKeys(LastName);
		driver.findElement(By.xpath(OR.getProperty("Email_input"))).sendKeys(Email);
		driver.findElement(By.xpath(OR.getProperty("Password_input"))).sendKeys(Password);
		driver.findElement(By.xpath(OR.getProperty("ConfirmPassword_input"))).sendKeys(Password);
		driver.findElement(By.xpath(OR.getProperty("SaveUser_button"))).click();
		QuestionUtil.WaitForProgressBar();
		System.out.println(Email);

		if(driver.findElement(By.xpath(OR.getProperty("Global_Error_Messase"))).isDisplayed()){
			Assert.fail("The user with this email = "+Email+" already exists. Delete it and then rerun test" );
		}

		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(OR.getProperty("QS_ImportTabSeperated_ConfirmMsg")))));
		//Assertion

		if ((driver.findElement(By.xpath(OR.getProperty("QS_ImportTabSeperated_ConfirmMsg")))).getText().trim().equalsIgnoreCase("The new user has been added.")){
			System.out.println("User is created successfully");
		}
		else{
			Assert.fail("There is some problem while creating new user");
		}
	}


	public static void VerifyEI1394() throws Exception {

		AssessmentUtil.navigateToCreateNewQuestionAssessmentPage();
		AssessmentUtil.doNewAssessmentCustomNotice();
		String ReorderButton = driver.findElement(
				By.xpath(OR.getProperty("ReorderNotice_button_xpath")))
				.getText();

		if (ReorderButton.trim().equalsIgnoreCase("Reorder")) {
			System.out
			.println("Reorder button is present after notice is created");
		} else {
			Assert.fail("Recorder button is not found");
		}

	}
	public static void verifyEI3240() throws Exception {
		QuestionUtil.ClickOnQuestionsTab();
		driver.findElement(By.xpath("//*[@id='websitebody']/table[1]//td[1]/a[5]")).click();// click import Questions link
		QuestionUtil.WaitForProgressBar();
		Thread.sleep(2000);

		Select filterDropdown = new Select(driver.findElement(By.id("importWithFilter")));
		List<WebElement> l= filterDropdown.getOptions();
		int filterSize= l.size();
		if(filterSize>1){
			System.out.println("Filters are appearing for RTF import.");
		}else{
			Assert.fail("Filters are not appearing for RTF import.");
		}
	}



	public static void VerifyEI2696() throws Exception {
		String Test_Folder=randomStringGen("Test_Folder");
		String Test_Category=randomStringGen("Test_Category");
		String Test_QsTitle=randomStringGen("778899");
		QuestionUtil.CreateQSFolder(Test_Folder);
		QuestionUtil.create_ApproveQuestion(Test_Folder,"TF",Test_QsTitle);
		CategoryUtil.CreateCategory(Test_Category);
		QuestionUtil.addQsToCategory(Test_Category,Test_QsTitle);
		QuestionUtil.checkCategoryForQuestionCount(Test_Category);
		QuestionUtil.ClickOnQuestionsTab();//its work around as its taking lot of time for page to load when we click on 
		QuestionUtil.WaitForProgressBar();							//category tab when we are already on category page
		QuestionUtil.tryDeleteCategoryWithQuestion(Test_Category);
		QuestionUtil.checkQuestionforBlankPage(Test_QsTitle);
		QuestionUtil.DeleteQSInFolder(Test_Folder);//cleanup
		CategoryUtil.DeleteQSCategory(Test_Category);//cleanup
		QuestionUtil.DeleteQSFolder(Test_Folder);//cleanup
	}


	public static void VerifyEI1292() throws Exception {
		AssessmentUtil.ClickOnAssessmentsTab();
		System.out.println("Assessment link clicked");
		//Thread.sleep(3000);
		getObjectxpath("allPostingDropbox").sendKeys("All Postings");
		driver.findElement(By.xpath(OR.getProperty("searchbox_xpath"))).sendKeys("FSX");
		System.out.println("String entered in the Search box");
		Thread.sleep(3000);
		getObjectxpath("searchbutton_xpath").click();
		System.out.println("Search button clicked");
		WaitForProgressBar();
		driver.findElement(By.xpath("//*[@id='examsTable']/tbody/tr[1]/td[2]/a")).click();
		Thread.sleep(2000);
		WaitForProgressBar();
		elementExists("//a[@id='uploadPDF']").click();
		Thread.sleep(2000);
		System.out.println(driver.findElement(By.id("fileUpload_fsx")).getText());
		System.out.println(driver.findElement(By.id("uploadButton_fsx")).getAttribute("value"));
	}

	public static void DeleteUser(String email) throws InterruptedException {
		searchUserWithEmail(email);
		elementExists("//*[@id='userListTable']//img[@src='/STW-war/Icons/delete.png']").click();
		Thread.sleep(2000);
		driver.findElement(By.xpath(OR.getProperty("DeleteUserConfirmation_Yes"))).click();
		QuestionUtil.WaitForProgressBar();
		if((driver.findElement(By.xpath(OR.getProperty("Global_Confirmation_Messase")))).getText().startsWith("You have successfully deleted the user")){
			System.out.println("User deleted from the System with email"+email);
		}
		else{
			Assert.fail("User not deleted");
		}
	}

	public static String randomStringGen(String input) throws InterruptedException{
		StringBuffer essayQs_Name=new StringBuffer(input);
		Random random = new Random();
		for(int i =0; i<3; i++){			//attach upto 3 random numbers
			essayQs_Name=essayQs_Name.append(random.nextInt(10));
		}
		return essayQs_Name.toString();
	}

	public static void Verify3026(String testfolder, String QsTitle) throws InterruptedException{
		QuestionUtil.CreateQSFolder(testfolder);
		QuestionUtil.create_ApproveQuestion(testfolder, "ESSAY", QsTitle);
		QuestionUtil.createNewRevision(QsTitle);
		QuestionUtil.searchQuestionByName(QsTitle);
		Thread.sleep(2000);
		String revID = driver.findElement(By.xpath("//*[@id='questionsTable']/tbody/tr/td[4]/a")).getText();
		revID = revID.substring(revID.indexOf("/")+1).trim();
		int newRevID = Integer.parseInt(revID);

		if (newRevID==2){
			System.out.println("Revision ID is 2: So the Editing and Revision change is working");
		}
		else
		{
			Assert.fail("Revision is is not2: Actual revision id is:" + newRevID);
		}
	}

	public static void Verify3012() throws InterruptedException{
		clickOnAssessment();
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(OR.getProperty("ExamTakers_xpath")))));
		getObjectxpath("ExamTakers_xpath").click();
		if (driver.getCurrentUrl().equalsIgnoreCase("https://www.examsoft.com/ui/redirect.aspx")){
			Assert.fail("Page is redirecting to "+driver.getCurrentUrl());
		}
		else{
			System.out.println("Verify3012 success");
		}



	}

	public static void Verify2850(String QsTitle, String testfolder) throws InterruptedException{
		QuestionUtil.CreateQSFolder(testfolder);
		QuestionUtil.create_SaveQuestion(testfolder, "ESSAY", QsTitle,"");
		QuestionUtil.searchQuestionByName(QsTitle);
		int size = driver.findElements(By.xpath("//*[@id='questionsTable']/tbody/tr")).size();
		if (size>0){
			System.out.println("Search is working, number of Qs searched: "+ size);
		}
		else{
			Assert.fail("Draft Question Search did not work, Please delete created Qs manually");
		}
	}

	//This method clicks on the Assessment tab
	private static void clickOnAssessment() throws InterruptedException{
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(OR.getProperty("Assessments_xpath")))));
		getObjectxpath("Assessments_xpath").click();
		System.out.println("Assessment Tab Clicked");
		QuestionUtil.WaitForProgressBar();
		Thread.sleep(3000);
	}

	//This method clicks on the AssessmentID column twice to sort it with posted assessments on top
	private static void sortByColumnName(String assess_statusName) throws InterruptedException{
		driver.findElement(By.xpath("//*[@id='examsTable']//tr//*[text()='"+assess_statusName+"']")).click();
		QuestionUtil.WaitForProgressBar();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='examsTable']//tr//*[text()='"+assess_statusName+"']")).click();
		QuestionUtil.WaitForProgressBar();
		Thread.sleep(2000);
	}

	public static boolean existsElement(String xpath) {
		try {
			driver.findElement(By.xpath(xpath));
		} catch (NoSuchElementException e) {
			return false;
		}
		return true;
	}

	public static void Verify3113() throws InterruptedException{
		int i;
		String editAssessmentName="";
		clickOnAssessment();
		getObjectxpath("allPostingDropbox").sendKeys("All Postings");
		getObjectxpath("searchbutton_xpath").click();
		QuestionUtil.WaitForProgressBar();
		Thread.sleep(2000);
		sortByColumnName("Assessment ID");
		sortByColumnName("Status");
		int records = driver.findElements(By.xpath("//*[@id='examsTable']//tr")).size();
		for (i=1; i<=records; i++ )//edit posting for Grade Column Name for any posting
		{
			if(existsElement("//*[@id='examsTable']//tr["+i+"]//a[@title='Edit this posting']")){
				editAssessmentName=driver.findElement(By.xpath("//*[@id='examsTable']//tr["+i+"]/td[2]/a")).getText();
				driver.findElement(By.xpath("//*[@id='examsTable']//tr["+i+"]//a[@title='Edit this posting']")).click();
				QuestionUtil.WaitForProgressBar();
				Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty("columnName"))).clear();
				driver.findElement(By.xpath(OR.getProperty("columnName"))).sendKeys("new column");
				driver.findElement(By.xpath(OR.getProperty("viewPostExam_xpath"))).click();
				try{
					if(isAlertPresent())
						driver.switchTo().alert().accept();
					waitInSeconds(1);
					if(isAlertPresent())
						driver.switchTo().alert().accept();
					QuestionUtil.WaitForProgressBar();
					Thread.sleep(3000);
					break;
				}catch(Exception e){
					if(existsElement("//*[@id='postExam']//td[1]/img")){//if error msg
						driver.findElement(By.xpath("//*[@id='basic-modal-examsPostExam']/div[4]/a[1]")).click();
						continue;
					}
				}
			}
		}
		for (int j=i+1; j<=records; j++ )// checking whether edit posting is propagating to other postings too
		{
			if(existsElement("//*[@id='examsTable']//tr["+i+"]//a[@title='Edit this posting']")){
				driver.findElement(By.xpath("//*[@id='examsTable']//tr["+j+"]//a[@title='Edit this posting']")).click();
				QuestionUtil.WaitForProgressBar();
				Thread.sleep(2000);
				if(driver.findElement(By.xpath(OR.getProperty("columnName"))).getText().equalsIgnoreCase("new column")){
					driver.findElement(By.xpath("//*[@id='basic-modal-examsPostExam']/div[4]/a[1]")).click();//click on cancel button on edit posting window
					Assert.fail("Grade Column Name in edit posting is propagating to other postings too!");
				}else{
					driver.findElement(By.xpath("//*[@id='basic-modal-examsPostExam']/div[4]/a[1]")).click();
					System.out.println("Grade Column Name in edit posting is not propagating to other postings!");
					break;
				}
			}
		}

		for (i=1; i<=records; i++ )//cleanup for Grade Column Name
		{
			if(driver.findElement(By.xpath("//*[@id='examsTable']//tr["+i+"]/td[2]/a")).getText().equalsIgnoreCase(editAssessmentName)){
				driver.findElement(By.xpath("//*[@id='examsTable']//tr["+i+"]//a[@title='Edit this posting']")).click();
				QuestionUtil.WaitForProgressBar();
				Thread.sleep(2000);
				driver.findElement(By.xpath(OR.getProperty("columnName"))).clear();
				driver.findElement(By.xpath(OR.getProperty("viewPostExam_xpath"))).click();
				if(isAlertPresent())
					driver.switchTo().alert().accept();
				waitInSeconds(1);
				if(isAlertPresent())
					driver.switchTo().alert().accept();
				QuestionUtil.WaitForProgressBar();
				Thread.sleep(3000);
				break;
			}
		}

	}

	public static void VerifyEI3082() throws InterruptedException{
		QuestionUtil.ClickOnQuestionsTab() ;
		QuestionUtil.WaitForProgressBar();
		Thread.sleep(2001);
		driver.findElement(By.xpath(OR.getProperty("QS_AdvancedSearch"))).click();
		driver.findElement(By.xpath(OR.getProperty("QS_SelectCategory"))).click();
		QuestionUtil.WaitForProgressBar();
		Thread.sleep(2001);
		driver.findElement(By.xpath("//*[@id='questionCategoriesTreeDiv']/ul/li/ul/li[1]/span/span[2]")).click();
		driver.findElement(By.xpath("//*[@id='questionCategoriesTreeDiv']/ul/li/ul/li[1]/span/span[2]")).sendKeys(Keys.ESCAPE);
		Thread.sleep(2001);
		//driver.findElement(By.xpath(OR.getProperty("QS_SelectCategoryDone"))).click();
		if (driver.findElement(By.xpath(OR.getProperty("QS_SelectCategoryIndicator"))).isDisplayed()){
			Assert.fail("Category is getting selected without clicking on the Done button, issue is reproducible");
		}
		System.out.println("Category is not selected, Issue is not reproducible");

	}

	public static void ClickOnRubricTab() throws InterruptedException{
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(OR.getProperty("RubricTab"))));
		driver.findElement(By.xpath(OR.getProperty("RubricTab"))).click();
		if(isAlertPresent())
			driver.switchTo().alert().accept();
		waitInSeconds(1);
		QuestionUtil.WaitForProgressBar();
		Thread.sleep(5000);
	}

	public static void VerifyRUB541() throws InterruptedException{
		WebElement rootCog,newRubric;
		String expectedStr, actualStr;
		ClickOnRubricTab();
		QuestionUtil.WaitForProgressBar();
		Thread.sleep(2001);
		expectedStr = driver.findElement(By.xpath(OR.getProperty("RubricRootFolderText"))).getText().trim();
		rootCog = driver.findElement(By.xpath(OR.getProperty("RubricRootCog")));
		newRubric = driver.findElement(By.xpath(OR.getProperty("RubricCreateNew")));
		Actions  actions  = new Actions(driver);
		actions.moveToElement(rootCog).moveToElement(newRubric).click().build().perform();
		QuestionUtil.WaitForProgressBar();
		Thread.sleep(2001);
		if (existsElement(OR.getProperty("RubricSelectedFolder")))
		{
			actualStr = driver.findElement(By.xpath(OR.getProperty("RubricSelectedFolder"))).getText().trim();
			if(expectedStr.equalsIgnoreCase(actualStr)){
				System.out.println("Rubrics folder name is getting updated, issue is not reproducible");
				System.out.println("Expected String: "+ expectedStr +" Actual String: "+actualStr );
			}
			else{
				Assert.fail("Rubrics create Folder is present but the folder text is not matching");
			}
		}

		Assert.fail("Rubrics folder is not present, Issue is reproducible");

	}

	//create a folder in rubric/qs/assessment
	public static void createFolder(String name) throws InterruptedException{
		WebElement rootCog,newFolder;
		rootCog = driver.findElement(By.xpath(OR.getProperty("RubricRootCog")));
		newFolder = driver.findElement(By.xpath(OR.getProperty("newFolder")));
		Actions  actions  = new Actions(driver);
		actions.moveToElement(rootCog).moveToElement(newFolder).click().build().perform();
		driver.findElement(By.xpath(OR.getProperty("newFolderEditbox"))).sendKeys(name);
		driver.findElement(By.xpath(OR.getProperty("saveFolderbutton_xpath"))).click();
		QuestionUtil.WaitForProgressBar();
		Thread.sleep(2001);

	}


	public static void VerifyEI3023() throws InterruptedException{
		String lengthyString, newString, srcFolder, destFolder;
		lengthyString="255 charactersssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
				"sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss" +
				"sssssssssssssssssssssssssssssssssssssssssssssssssssssssss12345";
		srcFolder=randomStringGen("EI3023Source");
		destFolder=randomStringGen("EI3023Dest");

		QuestionUtil.ClickOnQuestionsTab();
		createFolder(srcFolder);
		Thread.sleep(2001);
		createFolder(destFolder);
		Thread.sleep(2001);
		QuestionUtil.create_ApproveQuestion(srcFolder,"TF",lengthyString);
		QuestionUtil.ClickOnQuestionsTab();
		driver.findElement(By.xpath("//*[text()='" +srcFolder+ "']/../span[1]")).click();
		QuestionUtil.WaitForProgressBar();
		Thread.sleep(3001);
		driver.findElement(By.xpath(OR.getProperty("Qs_CheckAll"))).click();
		driver.findElement(By.xpath(OR.getProperty("Qs_BulkEditDropBox"))).sendKeys("Duplicate Items");
		driver.findElement(By.xpath(OR.getProperty("Qs_BulkEditDropBox"))).sendKeys(Keys.RETURN);
		driver.findElement(By.xpath(OR.getProperty("Qs_SelectFolder"))).click();
		driver.findElement(By.xpath("//*[@id='bulkEditFolderTreeDiv']/ul//*[text()='"+destFolder+"']/../span[1]")).click();
		QuestionUtil.WaitForProgressBar();
		Thread.sleep(3001);
		driver.findElement(By.xpath(OR.getProperty("Qs_DuplicateItems_Button"))).click();
		QuestionUtil.WaitForProgressBar();
		Thread.sleep(2001);
		if (!(driver.findElement(By.xpath(OR.getProperty("QS_ImportTabSeperated_ConfirmMsg"))).getText()).contains
				("The selected item(s) have been duplicated")){
			Assert.fail("Bulk duplication not success");
		}
		System.out.println("Bulk duplication: success");
		driver.findElement(By.xpath("//*[text()='" +destFolder+ "']/../span[1]")).click();
		QuestionUtil.WaitForProgressBar();
		Thread.sleep(3001);
		newString = driver.findElement(By.xpath("//*[@id='questionsTable']/tbody/tr[1]/td[2]/a")).getText().trim();
		int length = newString.length();
		if (newString.contains("Copy of") && length==254){
			System.out.println("Duplicated Qs has Copy of and the length is: "+ length);
			System.out.println("Performing cleanup part");
			//delete Qs in the folder
			QuestionUtil.ClickOnQuestionsTab();
			QuestionUtil.DeleteQSInFolder(srcFolder);
			QuestionUtil.ClickOnQuestionsTab();
			QuestionUtil.DeleteQSInFolder(destFolder);
			//delete folders
			QuestionUtil.ClickOnQuestionsTab();
			QuestionUtil.DeleteQSFolder(srcFolder);
			QuestionUtil.ClickOnQuestionsTab();
			QuestionUtil.DeleteQSFolder(destFolder);
		}
		else{
			Assert.fail("Issue is not pass: Check String length and Copy of text");
		}

	}

	public static void CustomLogin(String user, String pass) throws InterruptedException{
		driver.findElement(By.xpath(OR.getProperty("Username_input"))).sendKeys(user);
		driver.findElement(By.xpath(OR.getProperty("Password_input"))).sendKeys(pass);
		driver.findElement(By.xpath(OR.getProperty("Password_input"))).sendKeys(Keys.RETURN);
		QuestionUtil.WaitForProgressBar();
		Thread.sleep(5000);

	}

	public static void createNonKeyAdmin(String FirstName, String LastName, String Email, String Password) throws InterruptedException{

		WebElement adminLink=driver.findElement(By.xpath(OR.getProperty("AdminLink")));
		WebElement usersLink=driver.findElement(By.xpath(OR.getProperty("UsersLink")));
		wait.until(ExpectedConditions.elementToBeClickable(adminLink));
		Actions  actions  = new Actions(driver);
		actions.moveToElement(adminLink).moveToElement(usersLink).click().build().perform();
		QuestionUtil.WaitForProgressBar();
		driver.findElement(By.xpath(OR.getProperty("AddNewUserButton"))).click();
		QuestionUtil.WaitForProgressBar();
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(OR.getProperty("FirstName_input")))));

		driver.findElement(By.xpath(OR.getProperty("FirstName_input"))).sendKeys(FirstName);
		driver.findElement(By.xpath(OR.getProperty("LastName_input"))).sendKeys(LastName);
		driver.findElement(By.xpath(OR.getProperty("Email_input"))).sendKeys(Email);
		driver.findElement(By.xpath(OR.getProperty("Password_input"))).sendKeys(Password);
		driver.findElement(By.xpath(OR.getProperty("ConfirmPassword_input"))).sendKeys(Password);
		driver.findElement(By.xpath("//*[@id='viewMyaccountSection_1']/table/tbody/tr[3]/td[3]/input")).click();
		driver.findElement(By.xpath("//*[@id='viewMyaccountSection_1']/table/tbody/tr[4]/td[3]/input")).click();
		driver.findElement(By.xpath("//*[@id='viewMyaccountSection_1']/table/tbody/tr[5]/td[3]/input")).click();
		driver.findElement(By.xpath("//*[@id='viewMyaccountSection_1']/table/tbody/tr[6]/td[3]/input")).click();
		//driver.findElement(By.xpath("//*[@id='viewMyaccountSection_1']/table/tbody/tr[7]/td[3]/input")).click(); need to check
		driver.findElement(By.xpath(OR.getProperty("SaveUser_button"))).click();
		QuestionUtil.WaitForProgressBar();
		Thread.sleep(3000);
	}

	public static void VerifyEI3025(String test_folder,String FITB_Q,String Essay_Q,String MCQ_Q,String TF_Q,String EI3025_Assessment) throws Exception {

		QuestionUtil.create_ApproveQuestion(test_folder,"MC",MCQ_Q);
		QuestionUtil.create_ApproveQuestion(test_folder,"TF",TF_Q);
		QuestionUtil.create_ApproveQuestion(test_folder,"ESSAY",Essay_Q);
		QuestionUtil.create_ApproveQuestion(test_folder,"FITB",FITB_Q);
		AssessmentUtil.navigateToCreateNewQuestionAssessmentPage();
		AssessmentUtil.doNewAssessmentWithTitle(EI3025_Assessment);
		QuestionUtil.addQuestionToAssessment("EI3025");
		AssessmentUtil.doSaveCreatedAssessment();

		AssessmentUtil.doSearchAssessmentInfolderWithTitle(EI3025_Assessment);
		AssessmentUtil.doEditAssessment();
		AssessmentUtil.doPostassessment();

		AssessmentUtil.doExportPrintforScantron();
	}

	public static void VerifyEI3243(String QsTitle, String folderName) throws Exception {

		//Create a folder and a qs inside that
		QuestionUtil.ClickOnQuestionsTab();
		createFolder(folderName);
		Thread.sleep(2001);
		QuestionUtil.create_ApproveQuestion(folderName,"TF",QsTitle);



	}

	public static void VerifyEI2979() throws Exception {

		//Click on Question Tab
		QuestionUtil.ClickOnQuestionsTab();

		//Clicked on Question by Category
		driver.findElement(By.xpath("//*[@id='categories1']")).click();

		//Clicking on the displayed Category and capturing Text
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id='categoriesTreeDiv']/ul/li/ul/li[1]/span/span[3]/span[1]")).click();
		String Expected = driver.findElement(By.xpath("//*[@id='categoriesTreeDiv']/ul/li/ul/li[1]/span/span[3]/span[1]")).getText().trim();
		System.out.println("Category selected is" +Expected);

		//Clicking on the Filter and capturing Text
		String Actual = driver.findElement(By.xpath("//*[@id='gridFilter']")).getText().trim();
		System.out.println("Text displayed in Filter is" +Actual);

		//Comparison of both the Text
		if (Actual.contains(Expected)) {
			System.out.println("Filter Showing the Correct Selected Category");
		}else {
			Assert.fail("Filter Showing incorrect Selected Category");
		}

	}	

	public static int checkPostTimes() throws InterruptedException, ParseException{
		Thread.sleep(2000);
		int size = driver.findElements(By.xpath("//*[@id='examPostingsTable']/thead/tr/th")).size();
		for (int i=1; i<=size; i++){
			String targetText = driver.findElement(By.xpath("//*[@id='examPostingsTable']/thead/tr/th["+i+"]/span")).getText().trim();
			if (targetText.equalsIgnoreCase("Last Modified")){
				String postTime1 = driver.findElement(By.xpath("//*[@id='examPostingsTable']/tbody/tr[1]/td["+i+"]")).getText();
				String postTime2 = driver.findElement(By.xpath("//*[@id='examPostingsTable']/tbody/tr[2]/td["+i+"]")).getText();
				System.out.println("date1: "+postTime1);
				System.out.println("date2: "+postTime2);
				if(postTime1.equalsIgnoreCase(postTime2))
					return 0;
				else 
					return 1;
			}
		}
		return 0;
	}

	public static void verifyEI3242() throws InterruptedException{
		QuestionUtil.ClickOnQuestionsTab();
		Thread.sleep(3000);
		if (!(driver.findElement(By.xpath("//*[@id='folderTreeDiv']/ul/li/span")).isDisplayed())){
			Assert.fail("Folder tree for Question tab is not getting displayed");
		}

		QuestionUtil.ClickOnQuestionsTab();
		driver.findElement(By.xpath("//*[@id='categories1']")).click();
		WaitForProgressBar();
		Thread.sleep(3000);
		if (!(driver.findElement(By.xpath("//*[@id='categoriesTreeDiv']/ul/li/span")).isDisplayed())){
			Assert.fail("Folder tree for Question-category tab is not getting displayed");
		}

		ClickOnRubricTab();
		Thread.sleep(3000);
		if (!(driver.findElement(By.xpath("//*[@id='folderTreeDiv']/ul/li/span")).isDisplayed())){
			Assert.fail("Folder tree for Rubrics tab is not getting displayed");
		}

		clickOnAssessment();
		Thread.sleep(3000);
		if (!(driver.findElement(By.xpath("//*[@id='examFolderTreeDiv']/ul/li/span")).isDisplayed())){
			Assert.fail("Folder tree for Assessment tab is not getting displayed");
		}


	}
	public static void ExportGradeZeroUploads(int m) throws InterruptedException{
		//Function to verify Export Grade for ZeroUpload Assessment
		driver.findElement(By.xpath("//*[@id='examsTable']/tbody/tr[1]/td["+m+"]/a")).click();
		System.out.println("Clicked Export Grades");
		QuestionUtil.WaitForProgressBar();
		Thread.sleep(3001);
		if (!(driver.findElement(By.xpath("//*[@id='gradeNonScoredExamPopup']/div[1]")).isDisplayed())){
			Assert.fail("Export Grade window is NOT displayed");
		}

		driver.findElement(By.xpath("//*[@id='openExportGradePopupBtn']")).click();
		System.out.println("Clicked YES Button to go Next window");
		Thread.sleep(9001);
		WebElement theFrame = driver.findElement(By.id("gradeexportframe"));
		driver.switchTo().frame(theFrame);
		driver.findElement(By.xpath("//*[@id='A2']")).click();
		Thread.sleep(3001);
		System.out.println("Clicked Export Button in Next window");

		if (!(driver.findElement(By.xpath("//*[@id='ctl00_ctl00_ErrMsgLbl']")).getText()).contains
				("There is no data to export.")){
			Assert.fail("FAILED - error message is not displayed for 0 uploads");
		}
		System.out.println("PASS - Error message is displayed for 0 uploads ");
		driver.findElement(By.xpath("//*[@id='A1']")).click();
		Thread.sleep(3001);

	}
	public static void VerifyEI2432() throws Exception {
		//Create Exam from COG

		String rubrics = AssessmentUtil.checkRubricsState();
		AssessmentUtil.ClickOnAssessmentsTab();
		System.out.println("Assessment Tab Clicked");
		if (rubrics.equalsIgnoreCase("y")) {

			WebElement rootCog,newAssessment;
			rootCog = driver.findElement(By.xpath(OR.getProperty("ExamRootCog")));
			newAssessment=elementExists("//*[@id='examFolderTreeDiv']//span[@title='/EXAMS']/../span[2]//a[text()='Create Assessment']");
			//newAssessment = driver.findElement(By.xpath(OR.getProperty("CreateAssessmentCog")));
			Actions  actions  = new Actions(driver);
			actions.moveToElement(rootCog).moveToElement(newAssessment).click().build().perform();
			Thread.sleep(3001);								
			getObjectxpath("Newpopup_xpath").click();

			QuestionUtil.WaitForProgressBar();
			//Verify Selected Folder Name 
			if (!(driver.findElement(By.xpath("//*[@id='selectedFolder']/a")).getText()).contains
					("EXAMS")){
				Assert.fail("Folder is not getting selected when we create Softest Exam using cog icon : EI2432 FAILED");
			}
			System.out.println("PASS - Correct Folder is getting selected when we create SofTest Exam using cog icon");
			driver.findElement(By.xpath("//*[@id='examCreateEdit']/table/tbody/tr/td[3]/div[2]/a[1]")).click();

			QuestionUtil.WaitForProgressBar();
			//AssessmentUtil.ClickOnAssessmentsTab();
			rootCog = driver.findElement(By.xpath(OR.getProperty("ExamRootCog")));
			newAssessment=elementExists("//*[@id='examFolderTreeDiv']//span[@title='/EXAMS']/../span[2]//a[text()='Create Assessment']");
			actions.moveToElement(rootCog).moveToElement(newAssessment).click().build().perform();
			driver.findElement(By.xpath("//*[@id='advanced']/a[text()='Performance']")).click();
			QuestionUtil.WaitForProgressBar();
			if (!(driver.findElement(By.xpath("//*[@id='selectedFolder']/a")).getText()).contains
					("EXAMS")){
				Assert.fail("Folder is not getting selected when we create Performance Exam using cog icon : EI2432 FAILED");
			}
			System.out.println("PASS - Correct Folder is getting selected when we create Performance Exam using cog icon");
		}
		else{
			//When Only Softest Assessment Can be Created i.e. Rubrics is OFF
			WebElement rootCog,newAssessment;
			rootCog = driver.findElement(By.xpath(OR.getProperty("ExamRootCog")));
			newAssessment=elementExists("//*[@id='examFolderTreeDiv']//span[@title='/EXAMS']/../span[2]//a[text()='Create Assessment']");
			Actions  actions  = new Actions(driver);
			actions.moveToElement(rootCog).moveToElement(newAssessment).click().build().perform();
			Thread.sleep(3001);								

			QuestionUtil.WaitForProgressBar();
			//Verify Selected Folder Name 
			if (!(driver.findElement(By.xpath("//*[@id='selectedFolder']/a")).getText()).contains
					("EXAMS")){
				Assert.fail("Folder is not getting selected when we create Softest Exam using cog icon : EI2432 FAILED");
			}
			System.out.println("PASS - Correct Folder is getting selected when we create SofTest Exam using cog icon");
		}
	}
	public static void VerifyEI3120(String assessTitle) throws Exception {
		AssessmentUtil.navigateToCreateNewQuestionAssessmentPage();
		AssessmentUtil.doNewAssessment(assessTitle);
		AssessmentUtil.doNewAssessmentAddQuestions();
		AssessmentUtil.doSaveCreatedAssessment(); 
		AssessmentUtil.doPostassessment();
		clickOnAssessment();
		int th = driver.findElements(By.xpath("//*[@id='examsTable']/thead/tr/th")).size();
		System.out.println("No. of columns calculated");
		boolean flag = true;
		//Find if Export Grade column is displayed in the Assessment List page
		for (int i=2; i<=th; i++ )
		{
			String ExportGrade = driver.findElement(By.xpath("//*[@id='examsTable']/thead/tr/th["+i+"]/span")).getText();
			if (ExportGrade!=null && ExportGrade.trim().equalsIgnoreCase("Export Grades"))
			{
				ExportGradeZeroUploads(i);
				flag=false;
				break;

			}

		}
		// If ExportGrade is NOT in the Assessment List page Then Add it from Cog
		System.out.println(flag);
		if(flag){
			System.out.println("Export Grade Column needs to be added from COG");	
			driver.findElement(By.xpath("//*[@id='cog_examsTable']")).click();
			WebElement Cog = driver.findElement(By.xpath("//*[@id='addCol_examsTable']"));
			Cog.sendKeys("Export Grades");
			Thread.sleep(3001);
			System.out.println("Export Grade column added in Assessment listing");
			th = th+1;
			ExportGradeZeroUploads(th);
		}

	}
	public static void verify2214(String assessTitle)throws Exception{
		//Verify Duplicate Action for Posted Exam
		AssessmentUtil.navigateToCreateNewQuestionAssessmentPage();
		AssessmentUtil.doNewAssessment(assessTitle);
		AssessmentUtil.doNewAssessmentAddQuestions();
		AssessmentUtil.doSaveCreatedAssessment(); 
		AssessmentUtil.doPostassessment();
		AssessmentUtil.doDuplicateposting();
		AssessmentUtil.doSaveCreatedAssessment(); 
		System.out.println("Successfully Duplicated POSTED Exam");
		//Verify Duplicate Action for Draft Exam
		AssessmentUtil.navigateToCreateNewQuestionAssessmentPage();
		AssessmentUtil.doNewAssessment(assessTitle);
		AssessmentUtil.doNewAssessmentAddQuestions();
		AssessmentUtil.doSaveCreatedAssessment(); 
		AssessmentUtil.doDuplicateposting();
		AssessmentUtil.doSaveCreatedAssessment(); 
		System.out.println("Successfully Duplicated DRAFT Exam");
	}

	public static void verify3194(String assessmentTitle, String QsTitle, String folderName) throws Exception{
		String expected="Custom", actual1, actual2;
		QuestionUtil.ClickOnQuestionsTab();
		createFolder(folderName);
		Thread.sleep(2001);
		QuestionUtil.create_ApproveQuestion(folderName, "TF", QsTitle);
		//Go to create assessment page, create two custom notices and add target folder as default
		AssessmentUtil.navigateToCreateNewQuestionAssessmentPage();
		AssessmentUtil.doNewAssessmentWithTitle(assessmentTitle);
		AssessmentUtil.doNewAssessmentCustomNotice();//create custom notice
		AssessmentUtil.doNewAssessmentCustomNotice();//create custom notice
		AssessmentUtil.doNewAssessmentAddQuestions();
		AssessmentUtil.doSaveCreatedAssessment();
		AssessmentUtil.doPostassessment();

		//Duplicate Assessment
		elementExists(OR.getProperty("Duplicate_Button_postedAssessment")).click();
		QuestionUtil.WaitForProgressBar();
		Thread.sleep(5000);

		actual1 = driver.findElement(By.xpath("//*[@id='preExamNoticesTable']/tbody/tr[1]/td[4]")).getText();
		actual2 = driver.findElement(By.xpath("//*[@id='preExamNoticesTable']/tbody/tr[2]/td[4]")).getText();

		if (actual1.equalsIgnoreCase(expected)&& actual2.equalsIgnoreCase(expected)){
			System.out.println("Notice is still displayed as Custom: ");
		}
		else{
			System.out.println("Actual values of the notices: "+actual1+" & "+actual2);
			Assert.fail("Issue is not pass: expected notice string does not match!!");
		}
	}

}


