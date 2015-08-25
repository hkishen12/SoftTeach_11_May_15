package Util;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Base.TestBase;

public class RubricsUtil extends TestBase {

	//Click on Rubrics tab
	public static void ClickOnRubricsTab(){
		driver.navigate().refresh();
		if(isAlertPresent())
			driver.switchTo().alert().accept();
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='navbar']/ul[2]/li/a")));
		driver.findElement(By.xpath("//*[@id='navbar']/ul[2]/li/a")).click();
		if(isAlertPresent())
			driver.switchTo().alert().accept();
		waitInSeconds(1);
		WaitForProgressBar();
		waitInSeconds(5);
	}

	// Create Rubrics Folder
	public static void createRubricsFolder(String folderName){
		ClickOnRubricsTab();
		APP_LOGS.debug("Clicked on Rubrics Tab!");
		APP_LOGS.debug("--------Rubrics Folder Creation started--------");
		WebElement rubricsCogWheel=elementExists("//*[@id='rubricListingFolder']//td[contains(.,'RUBRICS')]/..//img[contains(@src,'cog.png')]");
		WebElement newFolderLink=elementExists("//*[@id='rubricListingFolder']//td[contains(.,'RUBRICS')]/..//a[contains(text(),'New Folder')]");

		new Actions(driver).moveToElement(rubricsCogWheel).moveToElement(newFolderLink).click().build().perform();
		APP_LOGS.debug("Clicked on new folder link");
		APP_LOGS.debug("Popup window '"+elementExists(OR.getProperty("QS_CreateNewFolderText")).getText()+"' displayed");
		//Enter folder name
		elementExists(OR.getProperty("QS_CreateNewFolderTextArea")).sendKeys(folderName);
		APP_LOGS.debug("Entered folder title '"+folderName+"' in title text field");
		elementExists(OR.getProperty("QS_CreateNewFolderSubmit")).click();
		APP_LOGS.debug("Clicked on Create button!!");

		WaitForProgressBar();
		if(elementExists(OR.getProperty("ConfirmationMessage"))!=null){
			if(elementExists("//*[@id='rubricListingFolder']//span[text()='"+folderName+"']").isDisplayed()) 
				APP_LOGS.debug("Folder "+folderName+" created successfully!");
		}
		APP_LOGS.debug("--------Rubrics Folder Creation ended--------");
	}


	// Edit Rubrics Folder
	public static void editRubricsFolder(String oldFolderName, String newFolderName){
		ClickOnRubricsTab();
		APP_LOGS.debug("Clicked on Rubrics Tab!");
		WebElement rubricsCogWheel=elementExists("//*[@id='rubricListingFolder']//td[contains(.,'"+oldFolderName+"')]/..//img[contains(@src,'cog.png')]");
		WebElement editFolderLink=elementExists("//*[@id='rubricListingFolder']//td[contains(.,'"+oldFolderName+"')]/..//a[contains(text(),'Edit Folder')]");
		Actions action =new Actions(driver);
		waitInSeconds(1);
		action.moveToElement(rubricsCogWheel).build().perform();
		action.moveToElement(editFolderLink).click().build().perform();
		APP_LOGS.debug("Clicked on edit folder link");
		APP_LOGS.debug("Popup window '"+elementExists(OR.getProperty("QS_EditFolderText")).getText()+"' displayed");
		//Enter folder name
		elementExists(OR.getProperty("QS_EditFolderTextArea")).clear();
		elementExists(OR.getProperty("QS_EditFolderTextArea")).sendKeys(newFolderName);
		APP_LOGS.debug("Entered folder title '"+newFolderName+"' in title text field");
		elementExists(OR.getProperty("QS_EditFolderSubmit")).click();
		APP_LOGS.debug("Clicked on Create button!!");

		WaitForProgressBar();
		if(elementExists(OR.getProperty("ConfirmationMessage"))!=null){
			if(elementExists("//*[@id='rubricListingFolder']//span[text()='"+newFolderName+"']").isDisplayed()) 
				APP_LOGS.debug("Folder "+newFolderName+" edited successfully!");
		}
	}

	// Navigate to Rubric creation page
	public static void navigateToRubricCreationPage(){
		ClickOnRubricsTab();
		APP_LOGS.debug("Clicked on Rubrics Tab!");
		elementExists(OR.getProperty("createNewRubricButton")).click();
		waitInSeconds(10);
		APP_LOGS.debug("Create New Ruburic Button Clicked!");
		waitInSeconds(2);
		//check whether 'Create New Rubric' page is displayed or not
		if(elementExists("//div[@class='headline']").getText().trim().equalsIgnoreCase("Create New Rubric"))
			APP_LOGS.debug("'Create New Rubric' page is displayed!");
		else
			Assert.assertEquals(elementExists("//div[@class='headline']").getText().trim(), "Create New Rubric");
	}

	//Enter Rubric title and description on 'Create New Rubric' page
	public static void enterRubricTitleAndDescription(String rubricTitle, String rubricDescription){
		elementExists(OR.getProperty("rubricTitle")).clear();
		elementExists(OR.getProperty("rubricTitle")).sendKeys(rubricTitle);
		APP_LOGS.debug("Entered Rubric title.."+rubricTitle);
		elementExists(OR.getProperty("rubricDescription")).clear();
		elementExists(OR.getProperty("rubricDescription")).sendKeys(rubricDescription);
		APP_LOGS.debug("Entered Rubric description.."+rubricDescription);
	}


	//Verify Rubric title and description
	public static void verifyRubricTitleAndDescription(String rubricTitle, String rubricDescription){
		if(elementExists(OR.getProperty("rubricTitle")).getText().equals(rubricTitle)){
			APP_LOGS.debug("Verified Rubric title.."+rubricTitle);
		}
		if(elementExists(OR.getProperty("rubricDescription")).getText().equals(rubricDescription)){
			APP_LOGS.debug("Verified Rubric description.."+rubricDescription);
		}
	}

	//change rubric folder for New Rubric
	public static void changeRubricFolder(String rubricFolderName){
		String xpath="";
		if(!rubricFolderName.equalsIgnoreCase("RUBRICS"))
			xpath="//*[@title='/RUBRICS/"+rubricFolderName+"']";
		else
			xpath="//*[@title='/"+rubricFolderName+"']";

		elementExists(OR.getProperty("changeRubricFolderButton")).click();

		if(elementExists(OR.getProperty("QS_Other_SelectFolderText"))!=null)
			APP_LOGS.debug("Select Rubric Folder popup opened!");
		else
			Assert.fail("Error in opening 'Select Rubric Folder' popup!!");

		elementExists(xpath).click();// select RUBRIC folder
		WaitForProgressBar();
	}

	//Verify rubric folder
	public static void verifyRubricFolderName(String rubricFolderName){
		if(elementExists("//*[@id='selectedFolder']").getText().contains(rubricFolderName))
			APP_LOGS.debug("Selected folder name: "+elementExists("//*[@id='selectedFolder']").getText());
	}

	//Select rubric dimension
	public static void selectRubricDimension(int rows, int columns){
		elementExists(OR.getProperty("rowCount")).clear();
		elementExists(OR.getProperty("rowCount")).sendKeys(""+rows);
		APP_LOGS.debug("Entered initial rubric rows: "+rows);
		Select selectColumns = new Select(elementExists(OR.getProperty("columnCount")));
		selectColumns.selectByVisibleText(""+columns);
		APP_LOGS.debug("Selected initial rubric columns: "+columns);
	}

	//Select type of rubric scoring
	public static void typeOfScoring(String scoringType){
		if(scoringType.equalsIgnoreCase("Points"))
			elementExists("//*[@id='points_scoring']").click();
		else
			elementExists("//*[@id='scale_scoring']").click();
		APP_LOGS.debug("Selected "+scoringType+" Rubric scoring type!!");
	}

	//click on Create button on 'Create New Rubric' page
	public static void clickOnCreateButton(){
		elementExists(OR.getProperty("createButton")).click();
		APP_LOGS.debug("Clicked on Create button!");
		WaitForProgressBar();
	}

	//enter text for rubric rows and columns
	public static void enterRubricRowsAndColumnsText(String rowText, String columnText){
		for(int i=0;;i++){
			if(elementExists("//*[@id='row_text_"+i+"' and @name='rows["+i+"].text']")!=null){//enter row texts
				elementExists("//*[@id='row_text_"+i+"' and @name='rows["+i+"].text']").clear();
				elementExists("//*[@id='row_text_"+i+"' and @name='rows["+i+"].text']").sendKeys(rowText+(i+1));
				for(int j=0;;j++){//enter column texts
					if(elementExists("//*[@id='cell_text_"+i+"_"+j+"' and @name='rows["+i+"].cells["+j+"].text']")!=null){
						elementExists("//*[@id='cell_text_"+i+"_"+j+"' and @name='rows["+i+"].cells["+j+"].text']").clear();
						elementExists("//*[@id='cell_text_"+i+"_"+j+"' and @name='rows["+i+"].cells["+j+"].text']").sendKeys(columnText+(i+1)+(j+1));
					}else
						break;
				}
			}else
				break;
		}
	}

	//verify text for rubric rows and columns
	public static void verifyRubricRowsAndColumnsText(String rowText, String columnText){
		for(int i=0;;i++){
			if(elementExists("//*[@id='row_text_"+i+"' and @name='rows["+i+"].text']")!=null){//enter row texts
				if(elementExists("//*[@id='row_text_"+i+"' and @name='rows["+i+"].text']").getText().equals(rowText+(i+1))){
					APP_LOGS.debug("Row text verified successfully!");
				}else{
					Assert.fail("Row text not edited successfully!");
				}
				for(int j=0;;j++){//enter column texts
					if(elementExists("//*[@id='cell_text_"+i+"_"+j+"' and @name='rows["+i+"].cells["+j+"].text']")!=null){
						if(elementExists("//*[@id='cell_text_"+i+"_"+j+"' and @name='rows["+i+"].cells["+j+"].text']").getText().equals(columnText+(i+1)+(j+1))){
							APP_LOGS.debug("Column text verified successfully!");
						}else{
							Assert.fail("Column text not edited successfully!");
						}
					}else
						break;
				}
			}else
				break;
		}
	}


	//select rubric category on 'Create New Rubric' page
	public static void selectRubricCategory(String xpath){
		elementExists(OR.getProperty("rubricCategoryButton")).click();
		if(elementExists(OR.getProperty("addCategoriesToRubricDimensionButton"))!=null){
			APP_LOGS.debug("'Add Categories to Rubric Dimension' popup opened!");
		}else
			Assert.fail("Error in opening popup 'Add Categories to Rubric Dimension'");
		elementExists(xpath).click();
		/*if(elementExists("//*[contains(@id,'catright_MINI')]/td[1]/img[@alt='Remove']")!=null){
			APP_LOGS.debug("Category selected successfully!");
		}else
			Assert.fail("Error in category selection!");
		 */
		elementExists(OR.getProperty("doneButton")).click();
		WaitForProgressBar();	
	}


	//remove rubric category
	public static void removeRubricCategory(){
		elementExists(OR.getProperty("rubricCategoryButton")).click();
		if(elementExists(OR.getProperty("addCategoriesToRubricDimensionButton"))!=null){
			APP_LOGS.debug("'Add Categories to Rubric Dimension' popup opened!");
		}else
			Assert.fail("Error in opening popup 'Add Categories to Rubric Dimension'");
		try{
			String script="handleRemoveCategory('#caulight_MINI113196926391621175XX')";
			((JavascriptExecutor) driver).executeScript(script);
		}catch(Exception e){
			System.out.println("No category is added!!");
		}
		elementExists(OR.getProperty("doneButton")).click();
		WaitForProgressBar();	
	}

	//click on Rubric Save button
	public static void clickOnRubricSaveButton(){
		elementExists(OR.getProperty("rubricSaveButton")).click();
		APP_LOGS.debug("Clicked on rubric Save button!");
		WaitForProgressBar();
	}
	//click on Rubric Save As Template button
	public static void clickOnRubricSaveAsTemplateButton(){
		elementExists(OR.getProperty("rubricSaveAsTemplateButton")).click();
		APP_LOGS.debug("Clicked on rubric Save as template button!");
	}

	//enter details on Rubric save as template prompt
	public static void enterDetailsOnSaveAsTemplatePrompt(String templateTitle){
		if(elementExists(OR.getProperty("saveAsTemplatePrompt")).getText().contains("Save As Template"))
			APP_LOGS.debug("Save As Template prompt opened successfully!");
		else
			Assert.fail("Error in opening Save As Template prompt!");
		elementExists(OR.getProperty("saveAsActionInnertitlePopup")).clear();
		elementExists(OR.getProperty("saveAsActionInnertitlePopup")).sendKeys(templateTitle);

		elementExists(OR.getProperty("saveAsPrompt_SaveButton")).click();
		WaitForProgressBar();
	}

	//enter points for point based rubric
	public static void enterPoints(int basePoint){
		for(int i=0;;i++){
			if(elementExists("//*[@id='points' and @name='columns["+i+"].points']")!=null){
				elementExists("//*[@id='points' and @name='columns["+i+"].points']").clear();
				elementExists("//*[@id='points' and @name='columns["+i+"].points']").sendKeys(""+(basePoint/(i+1)));
			}else
				break;
		}
	}

	//verify points for point based rubric
	public static void verifyPoints(int basePoint){
		for(int i=0;;i++){
			if(elementExists("//*[@id='points' and @name='columns["+i+"].points']")!=null){
				if(elementExists("//*[@id='points' and @name='columns["+i+"].points']").getAttribute("value").toString().equals(""+(basePoint/(i+1)))){
					continue;
				}else{
					Assert.fail("Error to verify points for rubric!");
				}
			}else
				break;
		}
	}

	//enter scale range for scale based rubric
	public static void enterScaleRange(int basePoint){
		int j=0;
		for(int i=0;;i++){
			if(elementExists("//*[@id='rangeFirst' and @name='columns["+i+"].maxPoints']")!=null){
				elementExists("//*[@id='rangeFirst' and @name='columns["+i+"].maxPoints']").clear();
				elementExists("//*[@id='rangeFirst' and @name='columns["+i+"].maxPoints']").sendKeys(""+(basePoint-(++j*2)));//max range
				elementExists("//*[@id='rangeLast' and @name='columns["+i+"].minPoints']").clear();
				elementExists("//*[@id='rangeLast' and @name='columns["+i+"].minPoints']").sendKeys(""+(basePoint-(++j*2)));// min range
			}
			else
				break;
		}
	}

	//verify rubric saved confirmation message
	public static void verifyRubricSaveMessage(String expectedMsg){
		if(elementExists(OR.getProperty("ConfirmationMessage")).getText().contains(expectedMsg))
			APP_LOGS.debug("Rubric/Template saved successfully!");
		else
			Assert.fail("Error in rubric/template creation!");
	}

	//click on Save As a Rubric button
	public static void clickOnSaveAsARubricButton(){
		elementExists(OR.getProperty("saveAsRubricButton")).click();
		APP_LOGS.debug("Clicked on Save As a Rubric button!");
	}

	//enter details on Save As a Rubric prompt
	public static void enterDetailsOnSaveAsARubricPrompt(String rubricTitle){
		if(elementExists(OR.getProperty("saveAsRubricPrompt")).getText().contains("Save As A Rubric"))
			APP_LOGS.debug("Save As a Rubric prompt opened successfully!");
		else
			Assert.fail("Error in opening Save As a Rubric prompt!");
		elementExists(OR.getProperty("saveAsActionInnertitlePopup")).clear();
		elementExists(OR.getProperty("saveAsActionInnertitlePopup")).sendKeys(rubricTitle);

		elementExists(OR.getProperty("saveAsPrompt_SaveButton")).click();
		WaitForProgressBar();
	}

	//open rubric folder
	public static void openRubricFolder(String folderName){
		if(elementExists("//*[@id='rubricListingFolder']//span[.='"+folderName+"']")!=null){
			elementExists("//*[@id='rubricListingFolder']//span[.='"+folderName+"']").click();
			WaitForProgressBar();
		}else
			Assert.fail("No such folder exists!");
		if(elementExists("//*[@id='gridFilter']").getText().contains(folderName))
			APP_LOGS.debug("Folder "+folderName+" opened successfully!");
		else 
			Assert.fail("Error in opening folder "+folderName);
	}


	//open rubric or template
	public static void openRubricOrTemplate(String title){
		if(elementExists("//*[@id='rubricsTable']//a[.='"+title+"']")!=null){
			elementExists("//*[@id='rubricsTable']//a[.='"+title+"']").click();
			WaitForProgressBar();
		}else
			Assert.fail("Error in opening rubric/template "+title);
	}

	//click on add dimension button to add row
	public static void addDimension(int count){
		for(int i=1;i<=count;i++){
			elementExists(OR.getProperty("addDimensionButton")).click();
			WaitForProgressBar();
		}
	}

	//click on add level button to add column
	public static void addLevel(int count){
		for(int i=1;i<=count;i++){
			elementExists(OR.getProperty("addLevelButton")).click();
			WaitForProgressBar();
		}
	}

	//click on delete dimension button to delete row
	public static void deleteDimension(int dimensionNumber){
		elementExists("//*[@id='row_"+(dimensionNumber-1)+"']/img[contains(@src,'delete.png')]").click();
		if(elementExists(OR.getProperty("deleteDimensionPopup"))!=null)
			APP_LOGS.debug("Delete Dimension alert opened successfully!");
		else
			Assert.fail("Error in opening Delete Dimension alert!");

		elementExists(OR.getProperty("deleteDimensionSaveButton")).click();
		WaitForProgressBar();
	}

	//click on delete level button to delete column
	public static void deleteLevel(int levelNumber){
		elementExists("//*[@id='col_"+(levelNumber-1)+"']/img[contains(@src,'delete.png')]").click();
		if(elementExists(OR.getProperty("deleteLevelPopup"))!=null)
			APP_LOGS.debug("Delete Level alert opened successfully!");
		else
			Assert.fail("Error in opening Delete Level alert!");

		String script1="removePerformanceLevel('col_"+(levelNumber-1)+"');";
		//String script = "var h1 = document.getElementById('delDimensionHandler');var x1=h1.getElementsByTagName('a')[1];var att = document.createAttribute('id');att.value = 'yesButton';x1.setAttributeNode(att);document.getElementById('yesButton').click();";
		((JavascriptExecutor) driver).executeScript(script1);


		//waitInSeconds(10);
		/*new Actions(driver).moveToElement(elementExists(OR.getProperty("deleteLevelSaveButton"))).build().perform();
new Actions(driver).sendKeys(Keys.TAB).sendKeys(Keys.ENTER).build().perform();*/
		//	elementExists("//div[@id='delDimensionHandler']/a[2]").click();
		//	((JavascriptExecutor)driver).executeScript("document.getElementByClassName('keybutton saveHandler')");

		//WaitForProgressBar();
	}

	//delete rubrics in folder
	public static void deleteRubricsInFolder(String folderName) throws InterruptedException{
		RubricsUtil.ClickOnRubricsTab();
		RubricsUtil.openRubricFolder(folderName);
		if(elementExists("//*[@id='rubricsTable']//td[.='No matching records found']")!=null){
			System.out.println("There are no rubrics in folder - "+folderName);
			return;
		}
		while(elementExists("//*[@id='rubricsTable']//td[.='No matching records found']")==null){
			if(elementExists("//*[@id='rubricsTable']/tbody/tr[1]/td[1]/a")!=null){
				String rubricTitle=elementExists("//*[@id='rubricsTable']/tbody/tr[1]/td[1]/a").getText();
				RubricsUtil.deleteRubricorTemplate(rubricTitle);
			}
		}
	}

	//delete a Rubric
	public static void deleteRubricorTemplate(String rubricorTemplateName){
		if(elementExists("//*[@id='rubricsTable']//tr[contains(.,'"+rubricorTemplateName+"')]//a[@title='Delete this rubric/template']")!=null){
			elementExists("//*[@id='rubricsTable']//tr[contains(.,'"+rubricorTemplateName+"')]//a[@title='Delete this rubric/template']").click();
			if(elementExists(OR.getProperty("RemoveRubricOrTemplateConfirmation"))!=null)
				APP_LOGS.debug("Delete Rubric/Template confirmation popup opened!");
			else
				Assert.fail("Error in opening 'Delete Rubric/Template' popup!!");
			elementExists(OR.getProperty("deleteRubricOrTemplateYesButton")).click();
			WaitForProgressBar();
			if(elementExists(OR.getProperty("ConfirmationMessage")).getText().contains("You have deleted the Rubric"))
				APP_LOGS.debug("Rubric/Template deleted successfully!");
			else
				Assert.fail("Error in rubric/template deletion!");

		}else
			Assert.fail("Rubric "+rubricorTemplateName+" does not exists!!");
	}

	//delete Rubric folder
	public static void deleteRubricFolder(String folderName){
		try{
			RubricsUtil.ClickOnRubricsTab();
			if(elementExists("//*[@id='rubricListingFolder']//tr[contains(.,'"+folderName+"')]//img[contains(@src,'cog.png')]")!=null){
				WebElement cogWheel=elementExists("//*[@id='rubricListingFolder']//tr[contains(.,'"+folderName+"')]//img[contains(@src,'cog.png')]");
				WebElement deleteFolderOption=elementExists("//*[@id='rubricListingFolder']//tr[contains(.,'"+folderName+"')]//a[.='Delete Folder']");
				new Actions(driver).moveToElement(cogWheel).moveToElement(deleteFolderOption).click().build().perform();
				if(elementExists(OR.getProperty("deleteFolderConfirmation"))!=null)
					APP_LOGS.debug("Delete Rubric folder popup opened successfully!!");
				else
					Assert.fail("Error in opening Delete Rubric Folder popup!");
				elementExists(OR.getProperty("deleteRubricFolderYesButton")).click();
				WaitForProgressBar();
				if(elementExists(OR.getProperty("ConfirmationMessage")).getText().equalsIgnoreCase("The folder, "+folderName+", was removed from the system."))
					APP_LOGS.debug("Folder "+folderName+" deleted successfully!");
				else
					Assert.fail("Error in deletion of folder "+folderName);
				if(elementExists("//*[@id='rubricListingFolder']//span[.='"+folderName+"']")==null)
					APP_LOGS.debug("Folder "+folderName+" deleted successfully!");
				else
					Assert.fail("Error in deletion of folder "+folderName);
			}
		}catch(Exception e){
			System.out.println("hii");
		}

	}

	//share Rubric Folder
	public static void ShareFolder(String FolderName) throws InterruptedException{
		ClickOnRubricsTab();
		WebElement itemsCogWheel=elementExists("//*[@id='rubricListingFolder']//td[.='"+FolderName+"']/..//img[contains(@src,'cog.png')]");
		WebElement shareFolderLink=elementExists("//*[@id='rubricListingFolder']//td[.='"+FolderName+"']/..//a[contains(text(),'Share Folder')]");
		Actions action= new Actions(driver);
		waitInSeconds(1);
		action.moveToElement(itemsCogWheel).build().perform();
		action.moveToElement(shareFolderLink).click().build().perform();

		if(getObjectxpathNew("QS_ShareFolder_ResourceNameSR").getText().equals(FolderName))
			System.out.println("Folder is select for Share");
		else
			Assert.fail("Folder is not selected to Share");

		WebElement readonly= getObjectxpathNew("QS_ShareFolder_ReadOnlyTT");
		readonly.click();
		if(readonly.isSelected())
			System.out.println("Access is Selected");
		else
			Assert.fail("Access is not selected to Share");
		getObjectxpathNew("QS_ShareFolder_SaveShareResource").click();
		WaitForProgressBar();

		//Verification Part
		if(getObjectxpathNew("QS_ShareFolder_ConfirMsg").getText().equals("The folder, "+FolderName+", was successfully shared."))
			System.out.println("Folder Sucessfully Shared with User");
		else
			Assert.fail("Folder Sucessfully Shared with User");
	}





}



