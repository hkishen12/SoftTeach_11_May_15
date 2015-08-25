package Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Base.TestBase;

public class CategoryUtil extends TestBase {
	// Verify Create Questions Links
	public static void VerifyDefaultCategory() throws InterruptedException {
		ClickOnCategoryTab();
		APP_LOGS.debug("Clicked on the Category_tab");
		WaitForProgressBar();
		List <WebElement> catlist=driver.findElements(By.xpath("//*[@id='categoriesListingTreeDiv']/tbody/tr"));
		WebElement gf=null;
		int count=0;
		for (int i=1;i<=catlist.size();i++ ){
			if(i!=0){

				gf=driver.findElement(By.xpath("//*[@id='categoriesListingTreeDiv']/tbody/tr["+i+"]/td[1]/span/span[2]"));
			}

			APP_LOGS.debug(gf.getText());

			if(gf.getText().equals(Data.getProperty("CAT_Name.1"))
					||gf.getText().equals(Data.getProperty("CAT_Name.2"))
					||gf.getText().equals(Data.getProperty("CAT_Name.3"))
					||gf.getText().equals(Data.getProperty("CAT_Name.4"))
					||gf.getText().equals(Data.getProperty("CAT_Name.5"))
					){

				count++;

			}

		}

		if (count>=1){

			APP_LOGS.debug("Categories are displayed for User");
		}
		else{
			Assert.fail("All Categories are not displayed or some missing ");
		}

	}

	public static void checkBreadcrumAndHeading(String breadcrumText, String headingText){
		String expectedBreadcrum="Home  >  "+breadcrumText;
		String expectedHeading=headingText;
		Assert.assertTrue("Verifying Breadcrum on Categories page - ", elementExists("//*[@id='breadcrumb']").getText().trim().equalsIgnoreCase(expectedBreadcrum));
		Assert.assertTrue("Verifying Heading on Categories page - ", elementExists("//*[@class='headline']").getText().trim().equalsIgnoreCase(expectedHeading));
	}

	public static void checkExportResults_Excel_CSV(String fileType, String fileName){
		elementExists(OR.getProperty(fileType+"_results_icon_categories")).click();
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


	public static void checkSearch(String searchText){
		elementExists("//*[@id='categoriesListingTreeDiv']//input[@name='treeSearch']").sendKeys(searchText);
		waitInSeconds(2);
		if(elementExists("//*[@id='categoriesListingTreeDiv']//td[contains(.,'"+searchText+"')]")!=null){
			APP_LOGS.debug("Category "+searchText+" searched successfully!!");
		}else
			Assert.assertTrue("Error in "+searchText+" search",false);
		elementExists("//*[@id='categoriesListingTreeDiv']/thead/tr/th[1]/div/button[@id='btnResetSearch']").click();
		if(elementExists("//*[@id='categoriesListingTreeDiv']//input[@name='treeSearch']").getText().equals("")){
			APP_LOGS.debug("Reset search is working fine!!");
		}else
			Assert.assertTrue("Error in resetting search text field",false);
	}

	//getObjectxpathNewNew
	public static void VerifyCreateCategory() throws InterruptedException{

		CreateCategory(Data.getProperty("CAT_Name.1"));
		/*
		CreateCategory(Data.getProperty("CAT_Name.2"));
		CreateCategory(Data.getProperty("CAT_Name.3"));
		CreateCategory(Data.getProperty("CAT_Name.4"));
		CreateCategory(Data.getProperty("CAT_Name.5"));*/






	}
	public static void CreateCategory(String CatName) throws InterruptedException{
		ClickOnCategoryTab();
		WaitForProgressBar();
		APP_LOGS.debug("Clicked on the Category_tab");
		getObjectxpathNew("CAT_NewCategoryButton").click();
		APP_LOGS.debug("clicked on New Category button");
		Thread.sleep(3000l);
		getObjectxpathNew("CAT_NewCategoryText");
		APP_LOGS.debug("Create New Category text verified");
		Thread.sleep(3000l);
		elementExists("//*[@id='resourceNameCR']").sendKeys(CatName);
		APP_LOGS.debug("Enter New Category name");
		Thread.sleep(2000l);
		getObjectxpathNew("CAT_SelectCategoryDestination").click();
		APP_LOGS.debug("click on Select Destination");
		WaitForProgressBar();
		getObjectxpathNew("CAT_SelectParentCategory");
		APP_LOGS.debug("Select Parent Category text verified");
		driver.findElement(By.xpath("//*[@id='selectDestinationCategoryTree']//span[.='CATEGORIES']")).click();
		APP_LOGS.debug("Selected CATEGORIES Parent ");
		WaitForProgressBar();
		getObjectxpathNew("CAT_SaveCreateCategory").click();
		WaitForProgressBar();
		if(getObjectxpathNew("CAT_CategoryConfirmMsg").getText().equals("The category, "+CatName+", was successfully created.")){
			APP_LOGS.debug("Category Created Message Verified");	
		}
		else{
			Assert.fail("Category not created");
		}

	}

	public static void changeCategoryParent(String Category,String destninationCategory) throws InterruptedException{
		APP_LOGS.debug("--------Edit Category start--------");
		ClickOnCategoryTab();
		Thread.sleep(2000);
		String xpath  = "//*[@id='categoriesListingTreeDiv']//span[text()='"+Category+"']/..//img[@alt='Edit Category']";
		elementExists(xpath).click();
		APP_LOGS.debug("Clicked on edit category icon for category '"+Category+"' !!");
		Thread.sleep(2000);
		APP_LOGS.debug("Popup window '"+elementExists("//span[contains(text(),'Edit Category')]").getText()+"' displayed");

		elementExists("//a[contains(text(),'Select Destination')]").click();//click on select destination button
		APP_LOGS.debug("Clicked on Select Destination button");
		WaitForProgressBar();
		elementExists("//*[@id='categoriesSelectTreeDiv']//span[text()='"+destninationCategory+"']").click();//select destination category
		APP_LOGS.debug("Made category '"+destninationCategory+"' as a parent category for category '"+Category+"'!");
		Thread.sleep(2000);
		elementExists("//*[@id='editResource']//a[text()='Save']").click();// click on save button
		APP_LOGS.debug("Clicked on Save button");
		WaitForProgressBar();
		if(elementExists("//*[@id='globalMessageDiv']//*[contains(@class,'confirmationLabel')]")!=null){
			APP_LOGS.debug("Change of parent category was successfully!");
		}
		else
			Assert.fail("Failed to change parent category!!");
		APP_LOGS.debug("--------Edit Category end--------");
	}


	public static void VerifyDeleteCategory() throws InterruptedException {
		ClickOnCategoryTab();
		WaitForProgressBar();
		APP_LOGS.debug("Clicked on the Category_tab");
		DeleteCategory(Data.getProperty("CAT_Name.1"));
		/**
		DeleteCategory(Data.getProperty("CAT_Name.2"));
		DeleteCategory(Data.getProperty("CAT_Name.3"));
		DeleteCategory(Data.getProperty("CAT_Name.4"));
		DeleteCategory(Data.getProperty("CAT_Name.5"));*/

	}
	public static void DeleteQSCategory(String CatName) throws InterruptedException{
		Thread.sleep(2000);
		CategoryUtil.ClickOnCategoryTab();
		String xpath="//span[@title='"+CatName+"']//span[5]/img";
		driver.findElement(By.xpath(xpath)).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='deleteCancelYesDiv']/a[2]")).click();
		Thread.sleep(2000);
		WaitForProgressBar();
	}


	public static void DeleteQSCategory_FromQuestionTab(String CatName) throws InterruptedException{
		QuestionUtil.ClickOnQuestionsTab() ;
		WebElement qsbyc = getObjectxpathNew("QS_QsbyCategoriesLink");
		qsbyc.click();//click on category tab
		waitInSeconds(1);

		WebElement categoryCogWheel=elementExists("//*[@id='questionListingCategory']//td[contains(.,'"+CatName+"')]/..//img[contains(@src,'cog.png')]");
		WebElement deleteCategoryLink=elementExists("//*[@id='questionListingCategory']//td[contains(.,'"+CatName+"')]/..//a[contains(text(),'Delete Category')]");

		new Actions(driver).moveToElement(categoryCogWheel).moveToElement(deleteCategoryLink).click().build().perform();
		WebElement qsfoldersubmit = getObjectxpathNew("QS_DeleteFolderYes");
		qsfoldersubmit.click();
		WaitForProgressBar();
		WebElement qsfoldersucessfully = getObjectxpathNew("Qs_CreNewFolderSucessMsg");
		String ActualMsg = qsfoldersucessfully.getText();
		//Verification part
		QuestionUtil.ComapreTwoStringVal(ActualMsg.trim(),"The category, "+CatName+", was removed from the system.");
		System.out.println("Verified Category Sucessfully deleted message!");
		WaitForProgressBar();
		Thread.sleep(5000l);

		//Verification part
		if(elementExists("//*[@id='questionListingCategory']//span[text()='"+CatName+"']")!=null)
			Assert.fail(CatName+":Category not deleted");
		else
			System.out.println("Verified category below Top level Folder Items "+CatName);
	}

	public static void DeleteSubCategory(String parentCategory) throws InterruptedException{
		Thread.sleep(2000);
		CategoryUtil.ClickOnCategoryTab();
		List <WebElement> catlist=driver.findElements(By.xpath("//div[@id='categoriesListingTreeDiv']//li[@class='dynatree-lastsib']/ul//li"));
		WebElement gf=null;
		for (int i=1;i<catlist.size();i++ ){
			gf=driver.findElement(By.xpath("//*[@id='categoriesListingTreeDiv']/ul/li/ul/li["+i+"]/span/span[3]/span[1]"));
			if(gf.getText().equals(parentCategory)){
				driver.findElement(By.xpath("//*[@id='categoriesListingTreeDiv']/ul/li/ul/li["+i+"]/span/span[2]")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//*[@id='categoriesListingTreeDiv']/ul/li/ul/li["+i+"]/ul/li/span//img[@alt='Delete Category']")).click();
				WaitForProgressBar();
				getObjectxpathNew("CAT_ConfirmDelOkBtn").click();
				Thread.sleep(2000);
				System.out.println("Deleted sub category!");
				break;
			}
		}
	}
	public static void DeleteCategory(String CatName) throws InterruptedException{
		if(elementExists("//*[@id='categoriesListingTreeDiv']//td[contains(.,'"+CatName+"')]/..//img[contains(@src,'delete.png')]")!=null){
			elementExists("//*[@id='categoriesListingTreeDiv']//td[contains(.,'"+CatName+"')]/..//img[contains(@src,'delete.png')]").click();
			WaitForProgressBar();
			getObjectxpathNew("CAT_ConfirmDelOkBtn").click();
			WaitForProgressBar();
			Thread.sleep(2000);
			String msg=getObjectxpathNew("CAT_CategoryConfirmMsg").getText();
			if(msg.equals("The category, "+CatName+", was removed from the system.")){
				APP_LOGS.debug("Category is deleted");
				System.out.println("Deleted category "+CatName);
			}
			else{
				APP_LOGS.debug("|The category, "+CatName+", was not removed from the system.|");
				APP_LOGS.debug("|"+msg+"|");
				Assert.fail("Category "+CatName+" was not deleted from system.");
			}
			/*	Thread.sleep(2000l);
			getObjectxpathNew("CAT_BtnRefreshTree").click();
			WaitForProgressBar();*/

		}
		else{
			System.out.println("Category "+CatName+" does not exist.");
		}

	}

	public static void ClickOnCategoryTab() throws InterruptedException{
		driver.navigate().refresh();
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='navbar']/ul[4]/li/a")));
		driver.findElement(By.xpath("//*[@id='navbar']/ul[4]/li/a")).click();
		if(isAlertPresent())
			driver.switchTo().alert().accept();
		waitInSeconds(1);
		WaitForProgressBar();
		Thread.sleep(5000);
	}

	public static List<String> CheckForCategoryExist() throws InterruptedException{
		List <String> gh= new ArrayList<String>();
		ClickOnCategoryTab();
		WaitForProgressBar();
		getObjectxpathNew("CAT_BtnRefreshTree").click();
		APP_LOGS.debug("Clicked on the Category_tab");
		WaitForProgressBar();
		List <WebElement> catlist=driver.findElements(By.xpath("//div[@id='categoriesListingTreeDiv']//li[@class='dynatree-lastsib']/ul//li"));
		WebElement gf=null;

		for (int i=0;i<=catlist.size();i++ ){

			gf=driver.findElement(By.xpath("//div[@id='categoriesListingTreeDiv']//li[@class='dynatree-lastsib']/ul/li//span[@class='dynatree-title']/span"));

			if(i!=0){

				gf=driver.findElement(By.xpath("//div[@id='categoriesListingTreeDiv']//li[@class='dynatree-lastsib']/ul/li["+i+"]//span[@class='dynatree-title']/span"));
			}

			System.out.println(gf.getText());



			gh.add(gf.getText());
		}

		return gh;
	}
}
