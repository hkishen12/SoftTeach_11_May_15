package com.suiteQuestions;

import org.testng.annotations.BeforeSuite;

import Base.TestBase;
import Util.TestUtil;

public class QuestionSuiteBase extends TestBase{
	@BeforeSuite

	public void checkSuiteSkip() throws Exception
	{
		TestUtil.checkSuiteSkip("QuestionsSuite");
	}

	/*@AfterSuite
	public void cleanUp() throws Exception
	{
		//delete Question folder - A_folder
		QuestionUtil.ClickOnQuestionsTab();
		if(elementExists("//*[@id='questionListingFolder']//span[.='"+CONFIG.getProperty("QS_CreateQSFolderName")+"']")!=null){
			QuestionUtil.DeleteQSInFolder(CONFIG.getProperty("QS_CreateQSFolderName"));
			QuestionUtil.DeleteQSFolder(CONFIG.getProperty("QS_CreateQSFolderName"));
		}
		//delete Question Category - Automation
		QuestionUtil.ClickOnQuestionsTab() ;
		WebElement qsbyc = getObjectxpathNew("QS_QsbyCategoriesLink");
		qsbyc.click();//click on category tab
		waitInSeconds(1);
		if(elementExists("//*[@id='questionListingCategory']//span[.='Automation']")!=null){//check whether Automation category exists
			elementExists("//*[@id='questionListingCategory']//span[.='Automation']").click();
			WaitForProgressBar();
			if(elementExists("//*[@id='gridFilter']").getText().contains("/CATEGORIES/Automation")){
				System.out.println("Category grid for Automation folder loaded!");
			}else{
				Assert.fail("Error in loading category grid!");
			}
			if(elementExists("//*[@id='questionsTable']//tr/*[.='No matching records found']")!=null){//check if questions are there in category
				System.out.println("There are no questions in Automation category!");
				System.out.println("Deleting Automation category!");
				CategoryUtil.DeleteQSCategory_FromQuestionTab("Automation");
			}else{
				elementExists("//*[@id='qListCheckAll']").click();//select all questions checkbox
				Select selectBulkAction=new Select(elementExists("//*[@id='bulkOptionSelect']"));
				selectBulkAction.selectByVisibleText("Remove Items");//select to remove items
				waitInSeconds(2);
				if(isAlertPresent())
					driver.switchTo().alert().accept();
				WaitForProgressBar();
				if(elementExists("//*[@id='questionsTable']//tr/*[.='No matching records found']")!=null){
					System.out.println("All Questions are deleted from Automation category");
				}else if(elementExists(OR.getProperty("Global_Error_Messase"))!=null 
						&& (elementExists(OR.getProperty("Global_Error_Messase")).getText().contains("The following questions were not deleted")
								|| elementExists(OR.getProperty("Global_Error_Messase")).getText().contains("None of the items could be deleted"))){
					System.out.println("All questions were not deleted from category, some might be used on assessment.");
					System.out.println("changing category assignment.");
					elementExists("//*[@id='qListCheckAll']").click();
					Select selectBulkAction1=new Select(elementExists("//*[@id='bulkOptionSelect']"));
					selectBulkAction1.selectByVisibleText("Change Category Assignments");
					elementExists("//*[@id='AddedCatUI1']//*[@class='buttonintable addBECategoryOpen']").click();
					WaitForProgressBar();
					waitInSeconds(1);
					elementExists("//*[@id='catAddBulkEditTreeDiv']/ul/li/ul/li[1]/span/span[2]").click();
					elementExists("//*[@id='btnSelectBulkEditCatsAddTree']").click();
					waitInSeconds(1);
					elementExists("//*[@id='AddedCatUI2']//*[@class='buttonintable removeBECategoryOpen']").click();
					WaitForProgressBar();
					waitInSeconds(1);
					new Actions(driver).moveToElement(elementExists("//*[@id='remCatBulkEditTreeDiv']//span[@title='/CATEGORIES/Automation']")).doubleClick().build().perform();
					elementExists("//*[@id='btnSelectBulkEditCatsRemTree']").click();
					waitInSeconds(1);


					elementExists("//*[@id='AddedCatUI3']/a[@class='button']").click();
					WaitForProgressBar();
					if(elementExists(OR.getProperty("Global_Confirmation_Messase"))!=null 
							&& (elementExists(OR.getProperty("Global_Confirmation_Messase")).getText().contains("The categories for the selected question(s) have been updated."))){
						System.out.println("Categories changed successfully!!");
						System.out.println("Deleting Automation Category!");
						CategoryUtil.DeleteQSCategory_FromQuestionTab("Automation");
					}else
						Assert.fail("Error in changing category!");
				}
			}

		}else{
			System.out.println("Automation category does not exist!");
		}

	}
*/
}
