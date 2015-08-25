package Util;

import java.util.EmptyStackException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import Base.TestBase;

public class UsersUtil extends TestBase
{

	public static void clickOnUsersMenu(){
		WebElement adminLink = elementExists(OR.getProperty("AdminLink"));
		WebElement usersLink=elementExists(OR.getProperty("usersLink"));
		if(usersLink!=null){
			Actions  actions  = new Actions(driver);
			actions.moveToElement(adminLink).moveToElement(usersLink).click().build().perform();
			WaitForProgressBar();
		}
		if(elementExists(OR.getProperty("userListTable"))!=null){
			APP_LOGS.debug("Users page loaded successfully!");
		}else
			Assert.assertTrue(false, "Error in loading Users page!");
	}

	public static void clickOnAddNewUserButton(){
		elementExists(OR.getProperty("addNewUserButton")).click();
		WaitForProgressBar();
		if(elementExists(OR.getProperty("ceateNewUserHeadline"))!=null){
			APP_LOGS.debug("Create New User page opened successfully!");
		}else
			Assert.assertTrue(false, "Error in opening create new user page!");
	}

	public static void enterUserDetails(String fname, String lname,String userEmailAddress){
		elementExists(OR.getProperty("userFirstName")).sendKeys(fname);
		elementExists(OR.getProperty("userLastName")).sendKeys(lname);
		elementExists(OR.getProperty("userEmailAddress")).sendKeys(userEmailAddress);
		elementExists(OR.getProperty("userPhoneNumber")).sendKeys("9876543210");
		elementExists(OR.getProperty("userPassword")).sendKeys("123456");
		elementExists(OR.getProperty("userConfirmPassword")).sendKeys("123456");
	}

	public static void ClickOnSaveUserSettingsBtn(){
		elementExists(OR.getProperty("saveUserSettingsButton")).click();
		WaitForProgressBar();
		if(elementExists(OR.getProperty("ConfirmationMessage")).getText().trim().equals("The new user has been added.")){
			System.out.println("New user has been added successfully!");
		}else
			Assert.assertTrue(false, "Error in adding user!");
	}

	public static void searchUserByEmail(String userEmailAddress){
		elementExists(OR.getProperty("userSearchTextField")).clear();
		elementExists(OR.getProperty("userSearchTextField")).sendKeys(userEmailAddress);
		elementExists(OR.getProperty("searchUserButton")).click();
		WaitForProgressBar();
		if(elementExists("//*[@class='dataTables_empty']")==null){
			APP_LOGS.debug("Record searched successfully!");
		}
		else{
			APP_LOGS.debug("No Records Found!!");
			throw new EmptyStackException();
		}
	}

	public static void deleteUser(String userEmailAddress){
		elementExists("//*[@id='userListTable']//td[.='"+userEmailAddress+"']//..//img[contains(@src,'delete.png')]").click();
		waitInSeconds(2);
		if(elementExists(OR.getProperty("deleteUserTitle")).isDisplayed()){
			elementExists(OR.getProperty("deleteUserYesButton")).click();
			WaitForProgressBar();
		}else
			Assert.assertTrue(false,"Error in user deletion!!");
		if(elementExists(OR.getProperty("ConfirmationMessage")).getText().trim().contains("You have successfully deleted the user")){
			System.out.println("New user has been deleted successfully!");
		}else
			Assert.assertTrue(false, "Error in deleting user!");
	}
	public static void clickOnEditBtn(String userEmailAddress){
		elementExists("//*[@id='userListTable']//td[.='"+userEmailAddress+"']//..//img[contains(@src,'edit-green.gif')]").click();
		WaitForProgressBar();
		if(elementExists(OR.getProperty("manageUserSettingsHeadline"))!=null){
			APP_LOGS.debug("Create New User page opened successfully!");
		}else
			Assert.assertTrue(false, "Error in opening manage user settings page!");
	}

	public static void editUser(String fname, String lname,String newUserEmailAddress, String oldUserEmailAddress){
		clickOnEditBtn(oldUserEmailAddress);
		elementExists(OR.getProperty("userFirstName")).clear();
		elementExists(OR.getProperty("userLastName")).clear();
		elementExists(OR.getProperty("userEmailAddress")).clear();
		elementExists(OR.getProperty("userPhoneNumber")).clear();
		elementExists(OR.getProperty("userPassword")).clear();
		elementExists(OR.getProperty("userConfirmPassword")).clear();
		elementExists(OR.getProperty("userFirstName")).sendKeys(fname);
		elementExists(OR.getProperty("userLastName")).sendKeys(lname);
		elementExists(OR.getProperty("userEmailAddress")).sendKeys(newUserEmailAddress);
		elementExists(OR.getProperty("userPhoneNumber")).sendKeys("9876543219");
		elementExists(OR.getProperty("userPassword")).sendKeys("test123");
		elementExists(OR.getProperty("userConfirmPassword")).sendKeys("test123");
		elementExists(OR.getProperty("saveUserSettingsButton")).click();
		WaitForProgressBar();
		if(elementExists(OR.getProperty("ConfirmationMessage")).getText().trim().equals("The user has been updated.")){
			System.out.println("User has been updated/edited successfully!");
		}else
			Assert.assertTrue(false, "Error in editing user!");
	}

	public static void verifyUserEditDetails(String fname, String lname,String userEmailAddress){
		WebElement firstName=elementExists("//*[@id='userListTable']/tbody//td[.='"+fname+"']");
		System.out.println("Expected first name: "+fname+", Actual first name: "+firstName.getText());
		WebElement lastName=elementExists("//*[@id='userListTable']/tbody//td[.='"+lname+"']");
		System.out.println("Expected last name: "+lname+", Actual last name: "+lastName.getText());
		WebElement email=elementExists("//*[@id='userListTable']/tbody//td[.='"+userEmailAddress+"']");
		System.out.println("Expected email address: "+userEmailAddress+", Actual email address: "+email.getText());

		if(firstName!=null && lastName!=null && email!=null)
			APP_LOGS.debug("User edited successfully!");
		else
			Assert.assertTrue(false, "Error in editing user!");
	}

	public static void check_uncheck_IncludeGraderOnlyAccounts(boolean checkboxflag){
		if(checkboxflag){
			if(!elementExists(OR.getProperty("includeGraderOnlyAccountsCheckBox")).isSelected()){
				elementExists(OR.getProperty("includeGraderOnlyAccountsCheckBox")).click();
				WaitForProgressBar();
			}
			System.out.println("Checkbox is checked!");
		}else{
			if(elementExists(OR.getProperty("includeGraderOnlyAccountsCheckBox")).isSelected()){
				elementExists(OR.getProperty("includeGraderOnlyAccountsCheckBox")).click();
				WaitForProgressBar();
			}
			System.out.println("Checkbox is unchecked!");
		}
	}
	public static void emailUser(String userEmailAddress){
		elementExists("//*[@id='userListTable']//td[.='"+userEmailAddress+"']//..//img[contains(@src,'email.png')]").click();
		waitInSeconds(2);
		if(elementExists(OR.getProperty("emailUserTitle")).isDisplayed()){
			elementExists(OR.getProperty("emailUserSubject")).sendKeys("Test email Subject");
			elementExists(OR.getProperty("emailUserContent")).sendKeys("Test email Content");
			elementExists(OR.getProperty("bccUser")).clear();
			elementExists(OR.getProperty("emailUserSendButton")).click();
			WaitForProgressBar();
		}else
			Assert.assertTrue(false,"Error in sending email!");
		
		if(elementExists(OR.getProperty("ConfirmationMessage")).getText().trim().contains("Email sent to 1 User(s).")){
			System.out.println("Email sent to user successfully!");
		}else
			Assert.assertTrue(false, "Error in sending email!");
	}
}//end of class