package features;

import com.relevantcodes.extentreports.LogStatus;
import libraries.CommonLibrary;
import libraries.FunctionLibrary;
import libraries.ReportLibrary;
import libraries.TestCaseLibrary;

import org.joda.time.DateTime;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import static libraries.CommonLibrary.getEachTestCaseData;
import static libraries.CommonLibrary.sblLoginStatus;
import static libraries.CommonLibrary.dataObj;
import static objectProperties.SblCreateAccountPageProperties.*;
import static objectProperties.SblDeviceStatusChnageProperties.accountInfoDevicesTab;
import static objectProperties.SblDeviceStatusChnageProperties.accountInfoTab;
import static objectProperties.SblAccountMaintenancePageObject.*;
/**
* Created by 23319 on 28-12-2016.
*/
public class CamsSiebelAccountMaintenance {
	
    public static void CamsSiebelAccountMaintenanceTest() throws IOException, Exception {
        ExcelSheet exl = new ExcelSheet();

        int noOfRows = exl.totalrows("FLCSS_CAMS_AccountMaintenance","Test_Scenarios");

        HashMap<String, String> eachTestCaseData = new HashMap<String, String>();

        String testCaseName = "";

        for (int i = 1; i <=noOfRows; i++) {
        	
            eachTestCaseData = getEachTestCaseData(exl, "Test_Scenarios", i,"FLCSS_CAMS_AccountMaintenance");
            dataObj=eachTestCaseData;
            
            testCaseName = "tcAccountMaintenance"+eachTestCaseData.get("TestCaseId").replace("-","");
            String executionType = CommonLibrary.settingsSheetInfo.get("Execution_Type");
       
            if ((eachTestCaseData.get("ExecutionStatus").equalsIgnoreCase("Yes")) && ( eachTestCaseData.get("TestCaseType").contains(executionType)|| 
            		executionType.equalsIgnoreCase("All")))
                {
                	Method testMethod = null;
                    try {
                    	  ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>" + eachTestCaseData.get("TestCaseId")
                          + "</b>" + ": Test Case Execution is started....................... <br>"
                          + "Test case description: " + eachTestCaseData.get("TestCaseDesc"), LogStatus.INFO, false);
                            
                    	//check for class and method are found for the test case
                    	try
                    	{
                        Class<?> c = Class.forName("features.CamsSiebelAccountMaintenance");
                        testMethod= c.getMethod(testCaseName);    
                        
                    	} catch (ClassNotFoundException e) {                        	
                        	
                            e.printStackTrace();
                            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test method "+ testCaseName +" is not found", LogStatus.FAIL,false);
                        }
                       /* catch(Exception e)
                    	{
                        	 ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed "+e.getMessage(), LogStatus.FAIL,true);
                        	CommonLibrary.logoutSiebelApplicaiton();
                    	}*/
                           
                    	CommonLibrary.loginSblWithTestScenarioSpecificUser(eachTestCaseData);
                    	 if(sblLoginStatus=="Success"){                        
 	                        TestCaseLibrary.Get_TestCase_Instance().executeTC(testMethod);  
 		                    CommonLibrary.logoutSiebelApplicaiton();	                	
                         } 
                    } 
                    
                    catch(Exception e)
                    {
                    //if alert found. it will be accepted and close the browser. 
                        try{
                            
                        	e.getMessage();
                            System.out.println("Test Failed. Taking screenshot");
                            
                           WebDriverWait wait8 = new WebDriverWait(FunctionLibrary.objDriver,10);
                            wait8.until(ExpectedConditions.alertIsPresent());
                            Thread.sleep(3000);
                            String alertText;
                            Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                            alertText = alert.getText();
                            alert.accept();
                            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Execution Failed due to alert : <br>"+alertText, LogStatus.FAIL, true);
                            //Close browsers 
                            
                            FunctionLibrary.closeAllActiveBrowser();  
                            //continue;
                                
                        }
                        
                        catch(Exception exp){
                           
                           
                           e.printStackTrace();
                           try{

                               if (FunctionLibrary.objDriver.findElement(By.xpath(okBtn)).isDisplayed()) 
                               {                  

                                   	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Execution Failed due to popup <br>", LogStatus.FAIL, true);
	                                //FunctionLibrary.objDriver.findElement(By.xpath(okBtn)).click();
	                                
	                                //Method for Logout and Closing browser
	                                ////////logoutSiebelApplicaiton();
	                                FunctionLibrary.closeAllActiveBrowser();                                 
                                       
                               }
                           	}
                         catch(Exception e1){
                             System.out.println("test failed");

                             ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed. Error is: "+ e.getMessage()
                             , LogStatus.FAIL, true);
                             
                             try{
                             CommonLibrary.logoutSiebelApplicaiton();
                             FunctionLibrary.closeAllActiveBrowser();
                             }catch(Exception e2)
                             {
                             	 FunctionLibrary.closeAllActiveBrowser();
                             }
                             
                         }                          
                                  
                           
                        }                                                     
                        
                     
                 }//End of Catch                     
                   
                   dataObj.clear();

                }
                
                eachTestCaseData.clear();
                continue;
        }


    }



    public static void tcAccountMaintenanceACMO760() throws InterruptedException {
        try {
            int month = DateTime.now().getMonthOfYear();
            String monthName = DateTime.now().withMonthOfYear(month).toString("MMM");

            String accountNum = dataObj.get("AccountNumber");

            if (accountNum.length() > 1) {
                CommonLibrary.searchForAccount(accountNum);
                FunctionLibrary.clickObject(xpathAccLink, "", "Clicking on Account number link");
                JavascriptExecutor js = (JavascriptExecutor) FunctionLibrary.objDriver;
                WebAccountMaintenance.findDynamicElement(By.xpath(xpathOfMDXLabel), 120);
                WebAccountMaintenance.scrollToView(By.xpath(xpathOfMDXLabel));
                String valueOfResearchFlag = FunctionLibrary.objDriver.findElement(By.xpath(xpathOfMDXLabel)).getAttribute("readonly");
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Before Enrolling for MDX plan ", LogStatus.INFO, true);
                if (valueOfResearchFlag.equalsIgnoreCase("null") || valueOfResearchFlag.equalsIgnoreCase("false")) {
                    FunctionLibrary.clickObject(xpathOfMDXLabel, "Click", "Clicking on MDX Plan");
                    //Saving the updated Data
                    new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.CONTROL, "s")).perform();
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Enroll MDX plan before March month:  <br> Expected :MDX plan is not enrolled and Enroll MDX flag is read only. <br>"
                            + " Actual:MDX plan is Enrolled and Enroll MDX flag is not read only in the month of " + monthName, LogStatus.FAIL, true);
                } else {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Enroll MDX plan before March month:  <br> Expected :MDX plan is not enrolled and Enroll MDX flag is read only. <br>"
                            + " Actual:MDX plan is not enrolled and Enroll MDX flag is read only in the month of " + monthName, LogStatus.PASS, true);
                }
            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Input account number is blank. Please execute test data generation script before to run this test case. " +
                        "Test data row is 'TestDataForACMO_374' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" + accountNum, LogStatus.FAIL, false);
            }
        } catch (Exception e) {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" + e.getMessage(), LogStatus.FAIL, true);
        }
    }

    public static void tcAccountMaintenanceACMO761() throws InterruptedException {
        try {
            int month = DateTime.now().getMonthOfYear();
            String monthName = DateTime.now().withMonthOfYear(month).toString("MMM");

            String accountNum = dataObj.get("AccountNumber");

            if (accountNum.length() > 1) {
                CommonLibrary.searchForAccount(accountNum);
                FunctionLibrary.clickObject(xpathAccLink, "", "Clicking on Account number link");
                JavascriptExecutor js = (JavascriptExecutor) FunctionLibrary.objDriver;
                WebAccountMaintenance.findDynamicElement(By.xpath(xpathOfMDXLabel), 120);
                WebAccountMaintenance.scrollToView(By.xpath(xpathOfMDXLabel));
                String valueOfResearchFlag = FunctionLibrary.objDriver.findElement(By.xpath(xpathOfMDXLabel)).getAttribute("readonly");
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Before Enrolling for MDX plan ", LogStatus.INFO, true);
                if (valueOfResearchFlag.equalsIgnoreCase("null") || valueOfResearchFlag.equalsIgnoreCase("false")) {
                    FunctionLibrary.clickObject(xpathOfMDXLabel, "Click", "Clicking on MDX Plan");
                    //Saving the updated Data
                    new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.CONTROL, "s")).perform();
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Enroll MDX plan after April month:  <br> Expected :MDX plan is not enrolled and Enroll MDX flag is read only. <br>"
                            + " Actual:MDX plan is Enrolled and Enroll MDX flag is not read only in the month of " + monthName, LogStatus.FAIL, true);
                } else {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Enroll MDX plan after April month:  <br> Expected :MDX plan is not enrolled and Enroll MDX flag is read only. <br>"
                            + " Actual:MDX plan is not enrolled and Enroll MDX flag is read only in the month of " + monthName, LogStatus.PASS, true);
                }
            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Input account number is blank. Please execute test data generation script before to run this test case. " +
                        "Test data row is 'TestDataForACMO_374' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" + accountNum, LogStatus.FAIL, false);
            }
        } catch (Exception e) {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" + e.getMessage(), LogStatus.FAIL, true);
        }
    }

    public static void tcAccountMaintenanceACMO762() throws InterruptedException {
        try {
            int month = DateTime.now().getMonthOfYear();
            String monthName = DateTime.now().withMonthOfYear(month).toString("MMM");

            String accountNum = dataObj.get("AccountNumber");

            if (accountNum.length() > 1) {
                CommonLibrary.searchForAccount(accountNum);
                FunctionLibrary.clickObject(xpathAccLink, "", "Clicking on Account number link");
                JavascriptExecutor js = (JavascriptExecutor) FunctionLibrary.objDriver;
                WebAccountMaintenance.findDynamicElement(By.xpath(xpathOfMDXLabel), 120);
                WebAccountMaintenance.scrollToView(By.xpath(xpathOfMDXLabel));
                String valueOfResearchFlag = FunctionLibrary.objDriver.findElement(By.xpath(xpathOfMDXLabel)).getAttribute("readonly");
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Before Enrolling for MDX plan ", LogStatus.INFO, true);
                if (valueOfResearchFlag.equalsIgnoreCase("null") || valueOfResearchFlag.equalsIgnoreCase("false")) {
                    FunctionLibrary.clickObject(xpathOfMDXLabel, "Click", "Clicking on MDX Plan");
                    //Saving the updated Data
                    new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.CONTROL, "s")).perform();
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Enroll MDX plan during April month:  <br> Expected :MDX plan is enrolled and MDX fiscal year is autopopulated as Current year. <br>"
                            + " Actual:MDX plan is Enrolled and Enroll MDX flag is not read only in the month of " + monthName, LogStatus.PASS, true);
                } else {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Enroll MDX plan during April month:  <br> Expected :MDX plan is enrolled and MDX fiscal year is autopopulated as Current year <br>"
                            + " Actual:MDX plan is not enrolled and Enroll MDX flag is read only in the month of " + monthName, LogStatus.FAIL, true);
                }
            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Input account number is blank. Please execute test data generation script before to run this test case. " +
                        "Test data row is 'TestDataForACMO_374' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" + accountNum, LogStatus.FAIL, false);
            }
        } catch (Exception e) {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" + e.getMessage(), LogStatus.FAIL, true);
        }
    }

    public static void tcAccountMaintenanceACMO002() throws Exception
    {
        try{
        	String accountNum=dataObj.get("AccountNumber");
        	
        	
            if(!(accountNum==null))
            {
                CommonLibrary.searchForAccount(accountNum);
                FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(accountNum)));
                //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
                FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
               		                                         accountInfoDevicesTab,accountInfoTab,5,20);
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Before Updating Challenge QA2",LogStatus.INFO,true);
                FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 60, ChallangeQA2Dropdown);
                //Clicking Challenge QA-2 drop down";
                FunctionLibrary.clickObject(ChallangeQA2Dropdown,"","Clicking Challenge QA-2 drop down");
                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(""
                		+ "(//*[contains(text(),'"+dataObj.get("ChallengeQA-2 Ques")+"')])[1]", ChallangeQA2Dropdown, 3, 9);
                //Selecting Challenge QA-2 from drop down
                FunctionLibrary.clickObject("(//*[contains(text(),'"+dataObj.get("ChallengeQA-2 Ques")+"')])[1]","",
                		"Selecting Challenge QA-2 from drop down");
                //entering security answer
                String Desc = "Enter Security Answer";
                FunctionLibrary.setText(ChallangeQA2TextBox,dataObj.get("ChallengeQA-2 Ans"),Desc);
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"ChallengeQA-2 is updating to: "+dataObj.get("ChallengeQA-2 Ques") + 
                			". ChallengeQA-2 Ans is updating to: "+dataObj.get("ChallengeQA-2 Ans"),LogStatus.INFO,false);
                
                //Saving the updated Data
                new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.CONTROL, "s")).perform();
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected :Challenge QA2 should be Updated <br>"
                		+ " Actual:Challenge QA2 Field updated with value of"+dataObj.get("ChallengeQA-2 Ques"),LogStatus.PASS,true);
                Thread.sleep(2000);
                //Scroll up to Account History Tab
                FunctionLibrary.scrollUptoElement(accountHistoryTab, "Scroll up to Account History Tab");
                //Click on Account History Tab
                FunctionLibrary.clickObject(accountHistoryTab,"","Clicking Account History Tab");
           /*     //Wait until status Label AccountHistory visible
                FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 10, statusLabelAccountHistory);*/
                
                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathOfAccountHistoryTable, accountHistoryTab, 10, 21);
                int rowNumberWithChallenhgeQA2 = FunctionLibrary.getRowNumberFromWebTable(xpathOfAccountHistoryTable,
                		dataObj.get("ChallengeQA-2 Ques"),"get the row number");
                FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistoryTable,rowNumberWithChallenhgeQA2,1,
                        "CHANGE","<b>Account History<b><br>Verifying status of Challenge QA2 change", true);
                
                verifyNonFinacialHistory("AUDITACCNT","VIEW");
                 
                CommonLibrary.verifySiebelDatabaseColumnValue(dataObj.get("Verify Db Column1"),
                		dataObj.get("ChallengeQA-2 Ques"),"ou_num",String.valueOf(accountNum));
                CommonLibrary.verifySiebelDatabaseColumnValue(dataObj.get("Verify Db Column2"),
                		dataObj.get("ChallengeQA-2 Ans"),"ou_num",String.valueOf(accountNum));
            }
            else
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                        "Test data row is 'TestDataForACMO_002' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

            }
        }catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }


    }//end of 002 Method

    public static void tcAccountMaintenanceACMO008() throws IOException    {
    	
    	try{
       String accountNum=dataObj.get("AccountNumber");
        if(accountNum.length()>1)
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number: " +accountNum,LogStatus.INFO,false);

            String oldAccountName="";
	        String newAccountName = "";
	    CommonLibrary.searchForAccount(accountNum);
        //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
        FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
       		                                         accountInfoDevicesTab,accountInfoTab,5,20);
        
        FunctionLibrary.waitForObject.until(ExpectedConditions.attributeToBe(By.xpath(accountCompanyNameTxtBox),"aria-readonly", "false"));
       
	    
	    oldAccountName = FunctionLibrary.objDriver.findElement(By.xpath(accountCompanyNameTxtBox)).getAttribute("value").toString();
	    
	      ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Existing account  name:"+ 
	    		  					oldAccountName,LogStatus.INFO,true);
	      
	    String lastName_fristName = "";
	    ExcelSheet exl1 = new ExcelSheet();              	   
        exl1.totalrows("FLCSS_CAMS_CustomerNamesPicker","Customer_Name_Selection_Helper");
  		lastName_fristName = ExcelSheet.getUniqueLastNameAndFirstName("Customer_Name_Selection_Helper");
  		newAccountName=lastName_fristName.replace("_", " ");
  		lastName_fristName.split("_");
  		
  		exl1.totalrows("FLCSS_CAMS_AccountMaintenance","Test_Scenarios");  		
	      
	      ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Updating to account  name:"+ 
	    		  							newAccountName,LogStatus.INFO,false);
	   
	    FunctionLibrary.setText(accountCompanyNameTxtBox, newAccountName, 
	    		"Enter new account name");
	    new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.CONTROL, "s")).perform();
	    
	    ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Changed the account name and saved the page",LogStatus.INFO,false);
	
	    
     /*  try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {

			//e.printStackTrace();
		}*/
	   /* FunctionLibrary.highLightElement(FunctionLibrary.objDriver,
	    		FunctionLibrary.objDriver.findElement(By.xpath(accountHistoryLink)));*/
	    
	    //Verifying Account History
        FunctionLibrary.clickObject(accountHistoryLink, "", "Click Account History");
        
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathOfAccountHistory, accountHistoryLink, 10, 21);
        
        
        
        int rowNumberWithNewAccountName = FunctionLibrary.getRowNumberFromWebTable(xpathOfAccountHistory, 
        		newAccountName, "get the row number");
        System.out.println(rowNumberWithNewAccountName);
        FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistory, rowNumberWithNewAccountName, 1, 
        		"CHANGE", "Verifying status", true);
        //FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistory,rowNumberWithEmailId,2,"17","Verifying version");
        FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistory, rowNumberWithNewAccountName, 4, 
        		"ACTIVE", "Status", false);
        FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistory, rowNumberWithNewAccountName, 38,
        		accountNum, "Verifying account number", false);
        FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistory, rowNumberWithNewAccountName, 37,
        		newAccountName, "Verifying account name", false);

        //Verifying Notes
        String NotesDescription = newAccountName;
        FunctionLibrary.clickObject(notesTab, "", "Notes");
        String xpathOfNotesTable = ".//*[@summary='Notes List']";
        int rowNumberWithAccountNameDesc;
        rowNumberWithAccountNameDesc = FunctionLibrary.getRowNumberFromWebTable(xpathOfNotesTable, NotesDescription,
                "get the row number");
        System.out.println(rowNumberWithAccountNameDesc);
        FunctionLibrary.verifyWebTableCellData(xpathOfNotesTable, rowNumberWithAccountNameDesc, 2, "ACCOUNT UPDATE",
                "Verifying note in system log", true);
        FunctionLibrary.verifyWebTableCellData(xpathOfNotesTable, rowNumberWithAccountNameDesc, 3, "DEMOGRAPHICS",
                "Verifying note sub type in system log", false);
        FunctionLibrary.verifyWebTableCellData(xpathOfNotesTable, rowNumberWithAccountNameDesc, 4, accountNum,
                "Verifying Account number in system log", false);
        String verifyTextForAccountNameChangeInNotes = "CHANGE:'Acct/Company Name' changed from '" + oldAccountName + "' to '"
                + newAccountName + "'.";

        FunctionLibrary.verifyWebTableCellData(xpathOfNotesTable, rowNumberWithAccountNameDesc, 5,
        		verifyTextForAccountNameChangeInNotes, "Verifying account name change description", false);

        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_008' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
        
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }


    }

    public static void tcAccountMaintenanceACMO010() throws InterruptedException, IOException {
    	try{
    		
    	String accountNum=dataObj.get("AccountNumber");
    
    	if(accountNum.isEmpty()|| accountNum==null){
	        	
	    	String sql = "select account_no from siebel.v_etc_account a where email_address is null and account_name like '%AUTOMATION USER%' "+ 
	    			"and rownum=1 order by account_no desc";
	        	
	    	accountNum = CommonLibrary.executeSqlAndGetOutputAsHashMap(sql).get("ACCOUNT_NO");
	    	if(accountNum==null){accountNum="";};
	    
    	}	
    	
        if(accountNum.length()>1)
        {
            CommonLibrary.searchForAccount(accountNum);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(accountNum)));
            //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
            FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 20, emailAddress);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Before Updating Email Address",LogStatus.INFO,true);
    
            String lastName_fristName = "";
    	    ExcelSheet exl1 = new ExcelSheet();              	   
            exl1.totalrows("FLCSS_CAMS_CustomerNamesPicker","Customer_Name_Selection_Helper");
      		lastName_fristName = ExcelSheet.getUniqueLastNameAndFirstName("Customer_Name_Selection_Helper");
      		String newEmail=lastName_fristName.split("_")[0]+"."+lastName_fristName.split("_")[1]+"@conduent.com";
      		
      		
      		exl1.totalrows("FLCSS_CAMS_AccountMaintenance","Test_Scenarios");  		
       		 
            
            //entering security answer
            try{
                FunctionLibrary.setText(emailAddress,newEmail,"Enter Primary Email Address");
                //Handling pop up code after entering email
                if(FunctionLibrary.objDriver.findElement(By.xpath(okBtn)).isDisplayed()){

                    FunctionLibrary.clickObject(okBtn,"","Clicking Ok buttn on Alert");
                }
                try{
                    WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,15);
                    wait2.until(ExpectedConditions.alertIsPresent());
                    Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                    alert.accept();
                }
                catch(Exception e){};

            }catch(Exception e){};
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.CONTROL, "s")).perform();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected:Email Address Should be Updated <br>"+"Actual:Email updated with Value of: "+newEmail, LogStatus.PASS,true);
            Thread.sleep(3000);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(accountHistoryLink)));
           
            //Verifying Account History
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@summary='Account History List Applet']")));
            
            //Verify Updated Email Address in Account history page
            int rowNumberWithEmailAddress = FunctionLibrary.getRowNumberFromWebTable(xpathOfAccountHistoryTable,newEmail,"get the row number of Email Address Field");
            FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistoryTable,rowNumberWithEmailAddress,1,
                    "CHANGE","Verifying status of Email Address", true);
            FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistoryTable,rowNumberWithEmailAddress,34,
                    newEmail,"Verify Email Address Field", false);
            //Screen shot on Address History page
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Screenshot of Account History page",LogStatus.PASS,false);
        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_010' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
        
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }


    }//End of ACMO010 Method

    public static void tcAccountMaintenanceACMO014() throws IOException {
    	
    	try{
    	
    	String accountNum=dataObj.get("AccountNumber");

        if(accountNum.length()>1)
        {

            String lastName_fristName = "";
    	    ExcelSheet exl1 = new ExcelSheet();              	   
            exl1.totalrows("FLCSS_CAMS_CustomerNamesPicker","Customer_Name_Selection_Helper");
      		lastName_fristName = ExcelSheet.getUniqueLastNameAndFirstName("Customer_Name_Selection_Helper");
      		String newEmailId=lastName_fristName.split("_")[0]+"."+lastName_fristName.split("_")[1]+"@conduent.com";
      		
      		
      		exl1.totalrows("FLCSS_CAMS_AccountMaintenance","Test_Scenarios");  		

         System.out.println(newEmailId);
        String oldEmailId;
        CommonLibrary.searchForAccount(accountNum);
        FunctionLibrary.clickObject(xpathAccLink, "", "Clicking on Account number link");
        oldEmailId = FunctionLibrary.objDriver.findElement(By.xpath(emailAddTxtBox)).getAttribute("value");
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>"
                + " Updating the mail id from: " + oldEmailId + " to: " + newEmailId, LogStatus.INFO, true);
        FunctionLibrary.objDriver.findElement(By.xpath(emailAddTxtBox)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(emailAddTxtBox)).sendKeys(newEmailId);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>"
                + " email id updated to: " + newEmailId, LogStatus.INFO, true);

        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.CONTROL, "s")).perform();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            String alertText;

            Alert alert = FunctionLibrary.objDriver.switchTo().alert();
            alertText =alert.getText();
            System.out.println("Popup is displayed with text" + alertText);

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>"
                    + "Popup dispalyed with text: " + alertText, LogStatus.INFO, false);

            alert.accept();
        }
        catch(Exception e)
        {  try{
            FunctionLibrary.clickObject("//button[contains(text(),'Ok')]", "", "Clicking Ok button");
        }
        catch(Exception e1){}
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e1) {

            e1.printStackTrace();
        }
        //Verifying Account History
        FunctionLibrary.clickObject(accountHistoryLink, "", "Click Account History");

        //String xpathOfAccountHistory = ".//*[@summary='Account History List Applet']";
        int rowNumberWithEmailId = FunctionLibrary.getRowNumberFromWebTable(xpathOfAccountHistory, newEmailId, "get the row number");
        System.out.println(rowNumberWithEmailId);
        FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistory, rowNumberWithEmailId, 1,
                "CHANGE", "Verifying status", true);
        //FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistory,rowNumberWithEmailId,2,"17","Verifying version");
        FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistory, rowNumberWithEmailId, 4,
                "ACTIVE", "Status", false);
        FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistory, rowNumberWithEmailId, 11,
                "Email", "Correspondence Delivery Mode", false);
        FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistory, rowNumberWithEmailId, 30,
                accountNum, "Verifying account number", false);
        FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistory, rowNumberWithEmailId, 34,
                newEmailId, "Verifying email", false);

        //Verifying Notes

        String NotesDescription = newEmailId;
        FunctionLibrary.clickObject(notesTab, "", "Notes");
        int rowNumberWithEmailDesc;
        rowNumberWithEmailDesc = FunctionLibrary.getRowNumberFromWebTable(xpathOfNotesTable, NotesDescription,
                "get the row number");
        System.out.println(rowNumberWithEmailDesc);
        FunctionLibrary.verifyWebTableCellData(xpathOfNotesTable, rowNumberWithEmailDesc, 2, "ACCOUNT UPDATE",
                "Verifying note in system log", true);
        FunctionLibrary.verifyWebTableCellData(xpathOfNotesTable, rowNumberWithEmailDesc, 3, "DEMOGRAPHICS",
                "Verifying note sub type in system log", false);
        FunctionLibrary.verifyWebTableCellData(xpathOfNotesTable, rowNumberWithEmailDesc, 4, accountNum,
                "Verifying Account number in system log", false);

        String verifyTextForEmailChangeInNotes = "CHANGE:'Email Address' changed from '" + oldEmailId + "' to '"
                + newEmailId + "'.";

        FunctionLibrary.verifyWebTableCellData(xpathOfNotesTable, rowNumberWithEmailDesc, 5,
                verifyTextForEmailChangeInNotes, "Verifying email change description", false);

        //In non-finanical History

        FunctionLibrary.clickObject(nonFinacialHistoryLink, "", "Click on Non-Financial History");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement mailElement = FunctionLibrary.objDriver.findElement(By.xpath(historyTableXpath));
        System.out.println(mailElement.getText());
        System.out.println(mailElement.isDisplayed());
        if (mailElement.isDisplayed()) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>"
                    + "Email updated log is present in Non financial  history ", LogStatus.PASS, true);

        } else {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>"
                    + "Couldn't find Email updated log in Non financial  history ", LogStatus.FAIL, true);
        }

        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_014' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
        
        
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }



    }

    public static void tcAccountMaintenanceACMO037()    {
    	
    	try{
    	String acctNumber=dataObj.get("AccountNumber");

        if(acctNumber.length()>1)
        {
        String accountType=dataObj.get("Account Type");

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Search for account number: "+acctNumber,LogStatus.INFO,false);

        //search for account
        CommonLibrary.searchForAccount(acctNumber);//326089539
        //click the link with acccount number
        FunctionLibrary.objDriver.findElement(By.linkText(acctNumber)).click();
        FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 60, ChallangeQA2Dropdown);
        String taxExemptButton=FunctionLibrary.objDriver.findElement(By.xpath(taxExemptField)).getAttribute("aria-checked");
        if(accountType.equalsIgnoreCase("Commercial")&& taxExemptButton.equalsIgnoreCase("false"))
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Take screenshot before selecing - Is Tax Exempt - radio button", LogStatus.INFO, true);
            FunctionLibrary.clickObject(isTaxExemptRadioBtn, "","Clicking on IsExepmt RadioButton");

            FunctionLibrary.setText(taxExemptExpiryDateTxtBox, dataObj.get("Tax Exempt Expiry Date"), "Selecting Expiry Date");

            FunctionLibrary.setText(taxExemptNumTxtBox,dataObj.get("TaxExempt"),"enter TaxExempt value");
            verifyNonFinacialHistoryRefIsSubCategory("CHANGE","SalesTax","SalesTax Exemption Flag Updated");
        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Is Tax Exempt - radio button is already selected", 
            		LogStatus.PASS, true);
        }

        }

        else
    {
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                "Test data row is 'TestDataForACMO_037' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +acctNumber,LogStatus.FAIL,false);

    }
        
    }catch(Exception e){
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
  }
    

    }
    
    public static void tcAccountMaintenanceACMO038() throws InterruptedException, IOException    {
    	
    	try{
    	String acctNumber=dataObj.get("AccountNumber");

        if(acctNumber.length()>1)
        {

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Search for account number: "+acctNumber,LogStatus.INFO,false);

        //search for account
        CommonLibrary.searchForAccount(acctNumber);//326088391
        //click the link with acccount number
        FunctionLibrary.objDriver.findElement(By.linkText(acctNumber)).click();
        //click on contacts
        FunctionLibrary.clickObject(contactsTab, "","Clicking on Contacts");
        //clicking the New button in contacts section


        String contactsToBeAdded = dataObj.get("Contacts");
        contactsToBeAdded = contactsToBeAdded.replace("\n","");

        String []contactsItems = contactsToBeAdded.split(",");
        String [] eachContactInfo;
        System.out.println(contactsItems.length);
        String contact1FirstName ="";
        String contact2FirstName="";
        String contact1LastName="";
        String contact2LastName="";
        String contact1PhoneNum="";
        String contact2PhoneNum="";
        for (int j =0;j<=contactsItems.length-1;j++)
        {

      	  String lastName_fristName = "";
    	    ExcelSheet exl1 = new ExcelSheet();              	   
          exl1.totalrows("FLCSS_CAMS_CustomerNamesPicker","Customer_Name_Selection_Helper");
      	lastName_fristName = ExcelSheet.getUniqueLastNameAndFirstName("Customer_Name_Selection_Helper");
          		
      		
      		exl1.totalrows("FLCSS_CAMS_AccountMaintenance","Test_Scenarios");  		

            eachContactInfo=contactsItems[j].split(":");
            if(j==0)
            {

                contact1FirstName = lastName_fristName.split("_")[0];
                contact1LastName = lastName_fristName.split("_")[1];
                contact1PhoneNum = eachContactInfo[2];

                //Scroll down to element
                FunctionLibrary.scrollDowntoElement(contactsListTab, "Scrol down");

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Take screenshot before adding 1st contact", LogStatus.INFO, true);

                FunctionLibrary.clickObject(contactDetailsNewBtn,"","Clicks Contact new button");
                //Thread.sleep(3000);
                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("(//input[@name='SSA_Primary_Field']/following::span[1])[1]", contactDetailsNewBtn, 20, 63);
                //Thread.sleep(3000);
                //Click on Last name element
                FunctionLibrary.clickObject(contactDetailsLstNameElement,"","Clicking on LastName");
                //Click on last name TextBox
                FunctionLibrary.setText(contactDetailsLstNameTxtBox,contact1LastName,"Clicking on LastName TextBox");
                //Click on First name element
                FunctionLibrary.clickObject(contactDetailsFrstNameElement,"","Clicking on FirstName");
                //Click on First name TextBox
                FunctionLibrary.setText(contactDetailsFrstNameTxtBox,contact1FirstName,"Clicking on FirstName textbox");
                //Click on cell phone element
                FunctionLibrary.clickObject(contactDetailsPhnNoElement,"","Clicking on Cell number");
                //Click on cell phone  TextBox
                FunctionLibrary.setText(contactDetailsPhnNoTxtBox,contact1PhoneNum,"Clicking on Cell number");
                handleAlertIfPresent(3);
            }
            else
            {
                contact2FirstName = lastName_fristName.split("_")[0];
                contact2LastName = lastName_fristName.split("_")[1];
                contact2PhoneNum = eachContactInfo[2];
                FunctionLibrary.scrollDowntoElement(contactsListTab, "Scrol down");
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Take screenshot before adding 2nd contact", LogStatus.INFO, true);

                FunctionLibrary.clickObject(contactDetailsNewBtn,"","Clicks Contact new button");
                //Thread.sleep(3000);
               /* //Click on Last name element
                FunctionLibrary.clickObject(contactDetailsLstNameElement,"","Clicking on LastName");
                */
               // FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(contactDetailsLstNameTxtBox, contactDetailsNewBtn, 20, 63);
               
                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("(//input[@name='SSA_Primary_Field']/following::span[1])[1]", contactDetailsNewBtn, 20, 63);
                //Thread.sleep(3000);
                //Click on Last name element
                FunctionLibrary.clickObject(contactDetailsLstNameElement,"","Clicking on LastName");
                
                //Click on last name TextBox
                FunctionLibrary.setText(contactDetailsLstNameTxtBox,contact2LastName,"Clicking on LastName TextBox");
                //Click on First name element
                FunctionLibrary.clickObject(contactDetailsFrstNameElement,"","Clicking on FirstName");
                //Click on First name TextBox
                FunctionLibrary.setText(contactDetailsFrstNameTxtBox,contact2FirstName,"Clicking on FirstName textbox");
                //Click on cell phone element
                FunctionLibrary.clickObject(contactDetailsPhnNoElement,"","Clicking on Cell number");
                //Click on cell phone  TextBox
                FunctionLibrary.setText(contactDetailsPhnNoTxtBox,contact2PhoneNum,"Clicking on Cell number");
                handleAlertIfPresent(3);
            }
            //handle the phone number format pop-up alert
            try {

                WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,8);
                wait2.until(ExpectedConditions.alertIsPresent());
                Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                alert.accept();
                Thread.sleep(3000);

            } catch (Exception e) {
                //exception handling
            }
            FunctionLibrary.clickObject(contactDetailsSaveBtn,"","save all details");
            FunctionLibrary.scrollDowntoElement(contactsListTab, "Scrol down");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Take screenshot after adding contacts", LogStatus.INFO, true);

        }/*
        WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 120);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(contactsHistory)));
        Thread.sleep(3000);
 */       FunctionLibrary.clickObject(contactsHistory,"","Click Contact history");
        
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathOfContactTable, contactsHistory, 20, 63);
        
        int rowNumberWithContactName1 = FunctionLibrary.getRowNumberFromWebTable(xpathOfContactTable,contact1FirstName,"get the row number");
        System.out.println(rowNumberWithContactName1);
        FunctionLibrary.verifyWebTableCellData(xpathOfContactTable,rowNumberWithContactName1,1,"ADD","Verifying status of contact one", true);
        FunctionLibrary.verifyWebTableCellData(xpathOfContactTable,rowNumberWithContactName1,5,contact1LastName,"Verifying lastName of contact one", false);

        FunctionLibrary.verifyWebTableCellData(xpathOfContactTable,rowNumberWithContactName1,6, contact1FirstName,"Verifying firstName of contact one", false);
        
        String contactPhoneNum1Showing = FunctionLibrary.getWebTableCellData(xpathOfContactTable, rowNumberWithContactName1, 9, "Get the contact1 phone number");
        
        if(CommonLibrary.phoneNumbersCompare(contactPhoneNum1Showing, contact1PhoneNum))
        {
        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Contact 1 phone number is showing correctly: "+
        				contact1PhoneNum, LogStatus.PASS, false);
        }
        else
        {
        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: "+contactPhoneNum1Showing + ". Actual is: "+ 
        				contact1PhoneNum, LogStatus.FAIL, false);
        }

        int rowNumberWithContactName2 = FunctionLibrary.getRowNumberFromWebTable(xpathOfContactTable,contact2FirstName,"get the row number");
        System.out.println(rowNumberWithContactName2);
        FunctionLibrary.verifyWebTableCellData(xpathOfContactTable,rowNumberWithContactName2,1,"ADD","Verifying status of contact two", false);
        FunctionLibrary.verifyWebTableCellData(xpathOfContactTable,rowNumberWithContactName2,5,contact2LastName,"Verifying lastName of contact two", false);

        FunctionLibrary.verifyWebTableCellData(xpathOfContactTable,rowNumberWithContactName2,6, contact2FirstName,"Verifying firstName of contact two", false);
        String contactPhoneNum2Showing = FunctionLibrary.getWebTableCellData(xpathOfContactTable, rowNumberWithContactName2, 9, "Get the contact2 phone number");
        if(CommonLibrary.phoneNumbersCompare(contactPhoneNum2Showing, contact2PhoneNum))
        {
        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Contact2 phone number is showing correctly: "+
        				contact2PhoneNum, LogStatus.PASS, false);
        }
        else
        {
        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: "+contactPhoneNum2Showing + ". Actual is: "+ 
        				contact2PhoneNum, LogStatus.FAIL, false);
        }
         String sql1 = "SELECT CONCAT(last_name,fst_name)as fullname from siebel.s_contact s, siebel.s_org_ext o "
        		+ "where o.serVICE_EMP_CNT = s.X_etc_account_id AND O.OU_NUM = '"+acctNumber+"' and concat(last_name,fst_name)='"+(contact1LastName+contact1FirstName).toUpperCase()+"'";
        if(!(CommonLibrary.executeSqlAndGetOutputAsHashMap(sql1).get("FULLNAME")==null))
		{
			ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: First addded contact should be in DB. <br> Actual: Contact is present "+contact1LastName+contact1FirstName, LogStatus.PASS,false);
		}
        else
        {
        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: First addded contact should be in DB. <br> Actual: Contact is not present "+contact1LastName+contact1FirstName, LogStatus.FAIL,false);
        }
        
        String sql2 = "SELECT CONCAT(last_name,fst_name)as fullname from siebel.s_contact s, siebel.s_org_ext o "
        		+ "where o.serVICE_EMP_CNT = s.X_etc_account_id AND O.OU_NUM = '"+acctNumber+"' and concat(last_name,fst_name)='"+(contact2LastName+contact2FirstName).toUpperCase()+"'";
        if(!(CommonLibrary.executeSqlAndGetOutputAsHashMap(sql2).get("FULLNAME")==null))
		{
			ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: Second addded contact should be in DB. <br> Actual: Contact is present - "+contact2LastName+contact2FirstName, LogStatus.PASS,false);
		}
        else
        {
        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: Second addded contact should be in DB. <br> Actual: Contact is not present - "+contact2LastName+contact2FirstName, LogStatus.FAIL,false);
        }
        }

        else
    {
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                "Test data row is 'TestDataForACMO_038' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +acctNumber,LogStatus.FAIL,false);

    }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }


  }

    public static void tcAccountMaintenanceACMO057() throws InterruptedException     {
    	
    	try{
    		
    	
    	String accountNum=dataObj.get("AccountNumber");

        if(accountNum.length()>1)
        {


            CommonLibrary.searchForAccount(accountNum);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(accountNum)));
            //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
            FunctionLibrary.webdrvwaitByVisiblityofElementClickableByXpath(FunctionLibrary.objDriver, 10, addressTab);
            //Click on Address Tab
            FunctionLibrary.clickObject(addressTab,"","Clicking Address Tab");
            
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
            		addressLine1,addressTab,8,25);
            //Scroll down to element
            FunctionLibrary.scrollDowntoElement(addressLine1, "Scrol down");
            //Taking Screen shot before Updating Address
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Before Updating un-Deliverable Mailing Address",LogStatus.INFO,true);
            //Wait for Element to be visible
            FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 5, addressLine1);
            
            int rowNum = FunctionLibrary.getRowNumberFromWebTable(addressesTbl, "MAILING", "get row number");
            String objToClick = "//td[@id='"+rowNum+"_s_3_l_Street_Address']";
            
            String addressLine1Text = dataObj.get("AddressInfo");
            String Desc = "Enter Address ";
            FunctionLibrary.clickObject(objToClick, "","click the object");
            FunctionLibrary.setText(addressDetailsAddress1TxtBox,addressLine1Text,Desc);
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.CONTROL, "s")).perform();
            

            try {
                WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,10);
                wait2.until(ExpectedConditions.alertIsPresent());
                Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                alert.accept();
                System.out.println("address alert is handled");
            }
            catch (Exception e)
            {
                //exception handling
                System.out.println("address alert is not present");
            }
            Thread.sleep(10000);
            try{
            	FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@aria-label='Address Cleansed Flag' and @aria-checked='true']")));
            	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: Address cleansing falg should be checked. <br>Actual: It is checked",LogStatus.PASS,true);
            }catch(TimeoutException e){
            	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: Address cleansing falg should be checked. <br>Actual: It is not checked",LogStatus.FAIL,true);	
            }
            
            System.out.println("Taking screen capture");
            try {
                Thread.sleep(3000);
                WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,10);
                wait2.until(ExpectedConditions.alertIsPresent());
                Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                alert.accept();
                Thread.sleep(3000);
                System.out.println("address alert is handled");
            }
            catch (Exception e)
            {
                //exception handling
                System.out.println("address alert is not present");
            }
            //Scroll down to element
            FunctionLibrary.scrollDowntoElement(addressLine1, "Scrol down");
            //addressCleansedFlag
            //Click on Address History Tab
            FunctionLibrary.clickObject(addressHistoryTab,"","Clicking Address History Tab");
            FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 10, addressLine1Label);
            //Verify Updated Address Details

            String xpathOfAddressTable = "//*[@summary='Address History']";
            int rowNumberWithContactName1 = FunctionLibrary.getRowNumberFromWebTable(xpathOfAddressTable,dataObj.get("AddressInfo"),"get the row number");
            FunctionLibrary.verifyWebTableCellData(xpathOfAddressTable,rowNumberWithContactName1,1,
                    "CHANGE","Verifying status ", true);
            FunctionLibrary.verifyWebTableCellData(xpathOfAddressTable,rowNumberWithContactName1,5,
                    dataObj.get("AddressInfo"),"Verifying un-Deliverable Mailing Address", false);
            //Verifying Nixie Status Set to 'N'
            FunctionLibrary.verifyWebTableCellData(xpathOfAddressTable,rowNumberWithContactName1,12,
                    dataObj.get("NixieFlag"),"Verifying un-Deliverable Mailing Address", false);
            //Screen shot on Address History page
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Screenshot Address History page",LogStatus.PASS,true);
            
            verifyChangedAddressPresentInDbOrNot(addressLine1Text);

        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_057' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
        
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }


    }

    public static void tcAccountMaintenanceACMO062() throws InterruptedException     {
    	
    	try{ 
    		
    		//Private account created for 38, we can use for this test case aswell
    		String accountNum=dataObj.get("AccountNumber");
        
        if(accountNum.length()>1)
        {

            CommonLibrary.searchForAccount(accountNum);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(accountNum)));
            //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
            FunctionLibrary.webdrvwaitByVisiblityofElementClickableByXpath(FunctionLibrary.objDriver, 10, addressTab);
            //Click on Address Tab
            FunctionLibrary.clickObject(addressTab,"","Clicking Address Tab");
            //Scroll down to element
            FunctionLibrary.scrollDowntoElement(addressLine1, "Scrol down");
            
            //Taking Screen shot before Updating Address
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Before Updating Incorrect Mailing Address",LogStatus.INFO,true);
            //Wait for Element to be visible
            FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 5, addressLine1);
            
            int rowNum = FunctionLibrary.getRowNumberFromWebTable(addressesTbl, "MAILING", "get row number");
            String objToClick = "//td[@id='"+rowNum+"_s_3_l_Street_Address']";
            
            String addressLine1Text = dataObj.get("AddressInfo");
            String Desc = "Enter Address ";
            FunctionLibrary.clickObject(objToClick, "","click the object");
            FunctionLibrary.setText(addressDetailsAddress1TxtBox,addressLine1Text,Desc);
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.CONTROL, "s")).perform();

            try {
                WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,10);
                wait2.until(ExpectedConditions.alertIsPresent());
                Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                alert.accept();
                System.out.println("address alert is handled");
            }
            catch (Exception e)
            {
                //exception handling
                System.out.println("address alert is not present");
            }
            Thread.sleep(15000);
            WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 60);
            try{
            	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@aria-label='Address Cleansed Flag' and @aria-checked='true']")));
            	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: Address cleansing falg should not be checked. <br>Actual: It is checked",LogStatus.FAIL,true);
            }catch(TimeoutException e){
            	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: Address cleansing falg should not be checked. <br>Actual: It is not checked",LogStatus.PASS,true);	
            }

            System.out.println("Taking screen capture");
            try {
                Thread.sleep(3000);
                WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,10);
                wait2.until(ExpectedConditions.alertIsPresent());
                Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                alert.accept();
                //Thread.sleep(3000);
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Alert is displaying",LogStatus.INFO,true);
            }
            catch (Exception e)
            {
                //exception handling
                System.out.println("address alert is not present");
            }
            //Scroll down to element
            FunctionLibrary.scrollDowntoElement(addressLine1, "Scrol down");
            //Taking Screen shot After Updating Address
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected :Mailing Address should be Updated <br> Actual:Mailing Address updated with value of"+dataObj.get("AddressInfo"),LogStatus.PASS,true);
            System.out.println(" screen captured");
            //addressCleansedFlag
            //Click on Address History Tab
            FunctionLibrary.clickObject(addressHistoryTab,"","Clicking Address History Tab");
            System.out.println("Taking screen capture");
            try {
                Thread.sleep(3000);
                WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,10);
                wait2.until(ExpectedConditions.alertIsPresent());
                Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                alert.accept();
               // Thread.sleep(3000);
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Alert is displaying",LogStatus.INFO,true);
                
            }
            catch (Exception e)
            {
                //exception handling
                System.out.println("address alert is not present");
            }
            FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 10, addressLine1Label);
            //Verify Updated Address Details
            
            int rowNumberWithContactName1 = FunctionLibrary.getRowNumberFromWebTable(xpathOfAddressTable,addressLine1Text,"get the row number");
            FunctionLibrary.verifyWebTableCellData(xpathOfAddressTable,rowNumberWithContactName1,1,
                    "CHANGE","Verifying status",true);
            FunctionLibrary.verifyWebTableCellData(xpathOfAddressTable,rowNumberWithContactName1,5,
                    dataObj.get("AddressInfo"),"Verifying Mailing Address",false);
            //Screen shot on Address History page
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Screenshot Address History page",LogStatus.PASS,true);
            
			verifyChangedAddressPresentInDbOrNot(addressLine1Text);
        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_062' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }


    }

    public static void tcAccountMaintenanceACMO069() throws InterruptedException     {
    	try{
    	String accountNum=dataObj.get("AccountNumber");

        if(accountNum.length()>1)
        {

            CommonLibrary.searchForAccount(accountNum);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(accountNum)));
            //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
            FunctionLibrary.webdrvwaitByVisiblityofElementClickableByXpath(FunctionLibrary.objDriver, 10, addressTab);
            //Click on Address Tab
            FunctionLibrary.clickObject(addressTab,"","Clicking Address Tab");
            //Scroll down to element
            FunctionLibrary.scrollDowntoElement(addressLine1, "Scrol down");
            //Taking Screen shot before Updating Address
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Before Updating Partial Billing Address",LogStatus.INFO,true);
            
            //Wait for Element to be visible
            FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 5, addressLine1);
            
            int rowNum = FunctionLibrary.getRowNumberFromWebTable(addressesTbl, "BILLING", "get row number");
            String objToClick = "//td[@id='"+rowNum+"_s_3_l_Street_Address']";
            
            String addressLine1Text = dataObj.get("AddressInfo");
            String Desc = "Enter Address ";
            FunctionLibrary.clickObject(objToClick, "","click the object");
            FunctionLibrary.setText(addressDetailsAddress1TxtBox,addressLine1Text,Desc);
           
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.CONTROL, "s")).perform();
            
            try {
                WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,10);
                wait2.until(ExpectedConditions.alertIsPresent());
                Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                alert.accept();
                System.out.println("address alert is handled");
            }
            catch (Exception e)
            {
                //exception handling
                System.out.println("address alert is not present");
            }
            Thread.sleep(15000);
            WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 30);
            try{
            	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@aria-label='Address Cleansed Flag' and @aria-checked='true']")));
            	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: Address cleansing falg should not be checked. <br>Actual: It is checked",LogStatus.FAIL,true);
            }catch(TimeoutException e){
            	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: Address cleansing falg should not be checked. <br>Actual: It is not checked",LogStatus.PASS,true);	
            }
            try {
                Thread.sleep(3000);
                WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,10);
                wait2.until(ExpectedConditions.alertIsPresent());
                Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                alert.accept();
                Thread.sleep(3000);
                System.out.println("address alert is handled");
            }
            catch (Exception e)
            {
                //exception handling
                System.out.println("address alert is not present");
            }
            //Scroll down to element
            FunctionLibrary.scrollDowntoElement(addressLine1, "Scrol down");
            //Taking Screen shot After Updating Address
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected :Partial Billing Address should be Updated <br> Actual:Partial Billing Addressupdated with value of"+dataObj.get("AddressInfo"),LogStatus.PASS,true);
            System.out.println("screen captured");
            //addressCleansedFlag
            //Click on Address History Tab
            FunctionLibrary.clickObject(addressHistoryTab,"","Clicking Address History Tab");
            FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 10, addressLine1Label);
            //Verify Updated Address Details
           
            int rowNumberWithContactName1 = FunctionLibrary.getRowNumberFromWebTable(xpathOfAddressTable,addressLine1Text,"get the row number");
            FunctionLibrary.verifyWebTableCellData(xpathOfAddressTable,rowNumberWithContactName1,1,
                    "CHANGE","Verifying status", true);
            FunctionLibrary.verifyWebTableCellData(xpathOfAddressTable,rowNumberWithContactName1,5,
                    dataObj.get("AddressInfo"),"Verifying Partial Billing Address", false);
            //Screen shot on Address History page
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Screenshot Address History page",LogStatus.PASS,true);
            
			verifyChangedAddressPresentInDbOrNot(addressLine1Text);

        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_069' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }



    }

    public static void tcAccountMaintenanceACMO075()  {
    	try{
    		
    	
        String accountNum = dataObj.get("AccountNumber");

        if(accountNum.length()<1)
        {


            String sql = "select max(account_no) as ACCOUNT_NO from siebel.v_etc_account a where email_address like 'mailidForacmo075@conduent.com' "
                    + " and account_status_cd='ACTIVE' order by a.account_no desc "
                    ;
            HashMap  <String,String> inputAccountNumInfo =CommonLibrary.executeSqlAndGetOutputAsHashMap(sql);

            accountNum = inputAccountNumInfo.get("ACCOUNT_NO");
        }

        if(accountNum.length()>1)
        {


            CommonLibrary.searchForAccount(accountNum);
        //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
        FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
       		                                         accountInfoDevicesTab,accountInfoTab,5,20);
                try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String Desc="Click Address new button";

        String addressesToBeAdded = dataObj.get("AddressInfo");
        addressesToBeAdded = addressesToBeAdded.replace("\n","");

        String []addressItems = addressesToBeAdded.split(",");
        String [] eachAddressInfo;
        System.out.println(addressItems.length);
        String addressType;
        String addressLine1;
        String zipCode;
        String city;
        String state;
        String country;

        for (int j =0;j<=addressItems.length-1;j++) {
            eachAddressInfo = addressItems[j].split(":");
            addressType = eachAddressInfo[0];
            addressLine1 = eachAddressInfo[1];
            zipCode = eachAddressInfo[3];
            city = eachAddressInfo[4];
            state = eachAddressInfo[5];
            country = eachAddressInfo[6];

            System.out.println(addressType+"  " +addressLine1+"  " +zipCode+"  " +city+"  " + state+"  " +country);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            FunctionLibrary.clickObject(addressDetailsNewBtn, "", Desc);

            Desc = "Clicking Address type drop down";
            FunctionLibrary.clickObject(addressDetailsAddressTypeBtn, "", Desc);
            //change the value of "AddressType"
            Desc = "Select Address Type";
            FunctionLibrary.clickObject("(//*[contains(text(),'" + addressType + "')])[1]", "", Desc);


            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            Desc = "Clicking street address1";
            FunctionLibrary.clickObject(addressDetailsAddress1Element, "", Desc);
            Desc = "Entering street address1";
            FunctionLibrary.setText(addressDetailsAddress1TxtBox, addressLine1, Desc);


            Desc = "Click on postal/Zip code filed";
            FunctionLibrary.clickObject(addressDetailsPostalCodeELement, "", Desc);
            //Click on Zip Code
            Desc = "Click on postal/Zip code filed";
            FunctionLibrary.setText(addressDetailsPostalCodeTxtBox, zipCode, "Entering postal code");
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.CONTROL, "s")).perform();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            FunctionLibrary.clickObject(addressHistoryTab,"","Click Address History");
            int rowNumberAddressType = FunctionLibrary.getRowNumberFromWebTable(xpathOfAddressTable,addressType,"get the row number");

            System.out.println(rowNumberAddressType);
            FunctionLibrary.verifyWebTableCellData(xpathOfAddressTable,rowNumberAddressType,1,
                    "ADD","Verifying status", true);
            FunctionLibrary.verifyWebTableCellData(xpathOfAddressTable,rowNumberAddressType,2,
                    "0","Verifying Version", false);
            FunctionLibrary.verifyWebTableCellData(xpathOfAddressTable,rowNumberAddressType,4,
                    addressType,"Verifying Address Type", false);
            FunctionLibrary.verifyWebTableCellData(xpathOfAddressTable,rowNumberAddressType,5,
                    "6475 FOX ST","Verifying address Line 1", false);
            FunctionLibrary.verifyWebTableCellData(xpathOfAddressTable,rowNumberAddressType,7,
                    zipCode,"Verifying zip Code", false);
            FunctionLibrary.verifyWebTableCellData(xpathOfAddressTable,rowNumberAddressType,9,
                    "WEST VANCOUVER","Verifying city", false);


            FunctionLibrary.verifyWebTableCellData(xpathOfAddressTable,rowNumberAddressType,10,
                    "BC","Verifying state", false);
            FunctionLibrary.verifyWebTableCellData(xpathOfAddressTable,rowNumberAddressType,11,
                    "CAN","Verifying country", false);

        }
        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_075' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }

    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }


    }

    public static void tcAccountMaintenanceACMO082() throws Exception    {
    	try{
    	String accountNum=dataObj.get("AccountNumber");
 
        if(accountNum.length()>1)
        {

        String ReBillPayType=dataObj.get("Rebill Pay Type");
        String CreditCard= dataObj.get("CreditCardNo");
        String ExpMonth=dataObj.get("ExpMonth");
        String ExpYear=dataObj.get("ExpYear");
        String Desc;
        System.out.println(dataObj.get("TestCaseId"));

        CommonLibrary.searchForAccount(accountNum);
        //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
        FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
       		                                         accountInfoDevicesTab,accountInfoTab,5,20);
        FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(addressTab)));
        
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Default rebill type: " +
        FunctionLibrary.getElement(rebillTypeTxtBox).getAttribute("value"),
                LogStatus.INFO,    true);
        
        //Clicking on Rebill Info tab
        FunctionLibrary.clickObject(rebillInfoLink,"","Click Rebill Info link");

        FunctionLibrary.clickObject(replenishListEditBtn,"","Click edit button");
        Desc="Selecting MasterCard from ReBill Pay type";
        
            FunctionLibrary.selectDropDownListByValue(replenishmentDetailsRebillPayTypeDropdown,ReBillPayType,Desc);
            
        Desc="Enter CC Details";

        System.out.println(CreditCard);
        FunctionLibrary.setText(creditCardNoField,CreditCard,"Enter credit card number");
        Desc="Selecting Expiry Month";
       
        FunctionLibrary.selectDropDownList(creditCardExpMpnth,ExpMonth,Desc);
      

        Desc="Selecting Expiry Year";
        
            FunctionLibrary.selectDropDownList(creditCardExpYear,ExpYear,Desc);
       
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Updating to Rebill type: "+ReBillPayType,
                            LogStatus.INFO,    true);
                      

        Desc="Click On Save";
        FunctionLibrary.clickObject(replenishmentDetailsSaveBtn,"",Desc);
        
        try {
            WebDriverWait  wai= new WebDriverWait(FunctionLibrary.objDriver,8);
            wai.until(ExpectedConditions.alertIsPresent());
            Alert alert1 = FunctionLibrary.objDriver.switchTo().alert();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Alert with text: " + alert1.getText(),
                    LogStatus.INFO,    false);
            alert1.accept();

        } catch (Exception e){

        }
        FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@title='"+ReBillPayType+"']")));
           
        String rebillTypeUpdatedTo= FunctionLibrary.getElement(rebillTypeTxtBox).getAttribute("value");
       if(rebillTypeUpdatedTo.startsWith(ReBillPayType))
    	   {
    	   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Updated to Rebill pay type: "+ReBillPayType,
    		                LogStatus.PASS,    true);
    		        
    	   }
       
       else
       {
    	   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: "+ReBillPayType +". Actual: "+rebillTypeUpdatedTo,
	                LogStatus.FAIL,    true); 
       }
       
      WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 8);
       try{
    	   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath
    			   ("//td[contains(@id,'Is_Primary')]//span[@class='siebui-icon-checkbox-checked']")));
    	   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Primary checkbox should be checked<br> Actual:Checked",
	                LogStatus.PASS,    true);
       }
       catch(TimeoutException e1)
       {
    	   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Primary checkbox should be checked<br> Actual: Not Checked",
	                LogStatus.FAIL,    true);
         
       }
        //Clicking on Replenishments History

        FunctionLibrary.clickObject(replenishmentHistoryTab,"","Click Replenishment History link");

        String xpathOfRebillinfoTable = "./*//*[@summary='Replenishment Info History List']";
        FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOf(FunctionLibrary.getElement(xpathOfRebillinfoTable)));
        int rowNumber = FunctionLibrary.getRowNumberFromWebTable(xpathOfRebillinfoTable,ReBillPayType,"get the row number");
        System.out.println("after");
        System.out.println(rowNumber);
        FunctionLibrary.verifyWebTableCellData(xpathOfRebillinfoTable,rowNumber,1,
                "CHANGE","Verifying status", false);
        FunctionLibrary.verifyWebTableCellData(xpathOfRebillinfoTable,rowNumber,4,
                ReBillPayType,"Verifying ReBill Type", false);
        
        
        verifyNonFinacialHistoryRefDescription("CHANGE", "REBILL", "Rebill History");
    	
    	String sql = "selECT S.* FROM SIEBEL.s_pty_pay_prfl S, siebel.s_org_ext o where S.X_ETC_ACCOUNT_ID = O.SERVICE_EMP_cnt and o.ou_num = '"+accountNum+"'";
    	
    	FunctionLibrary.verifyTextBoxValue(finStatus, "AUTOPAY", "Verify Fin Status", true);
    	FunctionLibrary.verifyTextBoxValue(rebillTypeTxtBox, ReBillPayType, "Verify Rebill Type", true);
    	
    	HashMap<String, String> sqlResult = CommonLibrary.executeSqlAndGetOutputAsHashMap(sql);
    	if(sqlResult.get("PAY_TYPE_CD").toString().equalsIgnoreCase(ReBillPayType))
    	{
    		
    		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Master card should be updated in database.<br>Actual: updated", LogStatus.PASS, false);
    	}else
    	{
    		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Master card should be updated in database.<br>Actual: Not updated", LogStatus.FAIL, false);
    	}
    	
    	String maskedCcNumShouldBe = "XXXXXXXXXXXX"+dataObj.get("CreditCardNo").substring(dataObj.get("CreditCardNo").length() - 4);
    	
    	if(sqlResult.get("X_CC_MASKING").toString().contains(maskedCcNumShouldBe))
    	{
    		
    		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Master card should be updated in database.<br>Actual: updated - "+maskedCcNumShouldBe, LogStatus.PASS, false);
    	}else
    	{
    		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Master card number should be updated and masked in database.<br>Actual: Not updated", LogStatus.FAIL, false);
    	}
    	
    
        
        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_082' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
        
        
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }

    	
    	
    }

    public static void tcAccountMaintenanceACMO085() throws Exception  {
        // Please select an account number with Rebill info has mastercard re
        // Reading account number from Excel
    	try{
    		String accountNum=dataObj.get("AccountNumber");
    	 

        if(accountNum.length()>1)
        {


           // String AccountNumber=dataObj.get("AccountNumber");
        String ReBillPayType=dataObj.get("Rebill Pay Type");
        String CreditCard="4159282222222221";//dataObj.get("CreditCardNo");
        String ExpMonth=dataObj.get("ExpMonth");
        String ExpYear=dataObj.get("ExpYear");
        String Desc;        CommonLibrary.searchForAccount(accountNum);
        CommonLibrary.searchForAccount(accountNum);
        //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
        FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
       		                                         accountInfoDevicesTab,accountInfoTab,5,20);
        FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(addressTab)));
        
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Default rebill type: " +
        FunctionLibrary.getElement(rebillTypeTxtBox).getAttribute("value"),
                LogStatus.INFO,    true);
        
        //Clicking on Rebill Info tab
        FunctionLibrary.clickObject(rebillInfoLink,"","Click Rebill Info link");

        FunctionLibrary.clickObject(replenishListEditBtn,"","Click edit button");
        Desc="Selecting MasterCard from ReBill Pay type";
        
           // FunctionLibrary.Select_DropDownList(replenishmentDetailsRebillPayTypeDropdown,ReBillPayType,Desc);
            FunctionLibrary.selectDropDownListByValue(replenishmentDetailsRebillPayTypeDropdown,ReBillPayType,Desc);
            
        Desc="Enter CC Details";

        System.out.println(CreditCard);
        FunctionLibrary.setText(creditCardNoField,CreditCard,"Enter credit card number");
        Desc="Selecting Expiry Month";
       
        FunctionLibrary.selectDropDownList(creditCardExpMpnth,ExpMonth,Desc);      

        Desc="Selecting Expiry Year";
        
            FunctionLibrary.selectDropDownList(creditCardExpYear,ExpYear,Desc);
       
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Updating to Rebill type: "+ReBillPayType,
                            LogStatus.INFO,    true);
                      

        Desc="Click On Save";
        FunctionLibrary.clickObject(replenishmentDetailsSaveBtn,"",Desc);
        try {
            WebDriverWait  wai= new WebDriverWait(FunctionLibrary.objDriver,8);
            wai.until(ExpectedConditions.alertIsPresent());
            Alert alert1 = FunctionLibrary.objDriver.switchTo().alert();
            String alertText =  alert1.getText();
            alert1.accept();
          //System.out.println("Popup is displayed with text" + alert1.getText());
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Alert with text: " +alertText,
                    LogStatus.INFO,    false);

        } catch (Exception e){

        }
        
        FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@title='"+ReBillPayType+"']")));
        String rebillTypeUpdatedTo= FunctionLibrary.getElement(rebillTypeTxtBox).getAttribute("value");
       if(rebillTypeUpdatedTo.startsWith(ReBillPayType))
    	   {
    	   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Updated to Rebill pay type: "+ReBillPayType,
    		                LogStatus.PASS,    true);
    		        
    	   }
       
       else
       {
    	   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: "+ReBillPayType +". Actual: "+rebillTypeUpdatedTo,
	                LogStatus.FAIL,    true); 
       }
        //Clicking on Replenishments History

        FunctionLibrary.clickObject(replenishmentHistoryTab,"","Click Replenishment History link");

        String xpathOfRebillinfoTable = "./*//*[@summary='Replenishment Info History List']";
        FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOf(FunctionLibrary.getElement(xpathOfRebillinfoTable)));
        int rowNumber = FunctionLibrary.getRowNumberFromWebTable(xpathOfRebillinfoTable,ReBillPayType,"get the row number");
        System.out.println("after");
        System.out.println(rowNumber);
        FunctionLibrary.verifyWebTableCellData(xpathOfRebillinfoTable,rowNumber,1,
                "CHANGE","Verifying status", false);
        FunctionLibrary.verifyWebTableCellData(xpathOfRebillinfoTable,rowNumber,4,
                ReBillPayType,"Verifying ReBill Type", false);
        }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }

    }

    public static void tcAccountMaintenanceACMO088()  {
        // Please select an account number with Rebill info has mastercard re
    	try{
    	String accountNum=dataObj.get("AccountNumber");
  	 

        if(accountNum.length()>1)
        {

          //  String AccountNumber="326074837";  //dataObj.get("AccountNumber");
        String ReBillPayType=dataObj.get("Rebill Pay Type");
        String RoutingNumber=dataObj.get("ACHBankRouting");
        String SavingNumber=dataObj.get("ACHBankNumber");
        String Desc;
        CommonLibrary.searchForAccount(accountNum);
        //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
        FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
       		                                         accountInfoDevicesTab,accountInfoTab,5,20);
        FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(addressTab)));
        
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Default rebill type: " +
        FunctionLibrary.getElement(rebillTypeTxtBox).getAttribute("value"),
                LogStatus.INFO,    true);
        
        //Clicking on Rebill Info tab
        FunctionLibrary.clickObject(rebillInfoLink,"","Click Rebill Info link");

        FunctionLibrary.clickObject(replenishListEditBtn,"","Click edit button");
        Desc="Selecting MasterCard from ReBill Pay type";
        
           // FunctionLibrary.Select_DropDownList(replenishmentDetailsRebillPayTypeDropdown,ReBillPayType,Desc);
            FunctionLibrary.selectDropDownListByValue(replenishmentDetailsRebillPayTypeDropdown,ReBillPayType,Desc);
            

        FunctionLibrary.setText(paymentDetailsBankAccountNbrTxtBox,SavingNumber,"Enter banking number");
        FunctionLibrary.setText(paymentDetailsRoutingNbrTxtBox, RoutingNumber, "Enter routing number");


        Desc="Click On Save";
        FunctionLibrary.clickObject(replenishmentDetailsSaveBtn,"",Desc);
        try {
            WebDriverWait  wai= new WebDriverWait(FunctionLibrary.objDriver,8);
            wai.until(ExpectedConditions.alertIsPresent());
            Alert alert1 = FunctionLibrary.objDriver.switchTo().alert();
            //System.out.println("Popup is displayed with text" + alert1.getText());
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Alert with text: " + alert1.getText(),
                    LogStatus.INFO,    false);
            alert1.accept();

        } catch (Exception e){

        }
        
        FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@title='"+ReBillPayType+"']")));

        String rebillTypeUpdatedTo= FunctionLibrary.getElement(rebillTypeTxtBox).getAttribute("value");
       if(rebillTypeUpdatedTo.startsWith(ReBillPayType))
    	   {
    	   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Updated to Rebill pay type: "+ReBillPayType,
    		                LogStatus.PASS,    true);
    		        
    	   }
       
       else
       {
    	   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: "+ReBillPayType +". Actual: "+rebillTypeUpdatedTo,
	                LogStatus.FAIL,    true); 
       }
        //Clicking on Replenishments History

        FunctionLibrary.clickObject(replenishmentHistoryTab,"","Click Replenishment History link");

        String xpathOfRebillinfoTable = "./*//*[@summary='Replenishment Info History List']";
        FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOf(FunctionLibrary.getElement(xpathOfRebillinfoTable)));
        int rowNumber = FunctionLibrary.getRowNumberFromWebTable(xpathOfRebillinfoTable,ReBillPayType,"get the row number");
        System.out.println("after");
        System.out.println(rowNumber);
        FunctionLibrary.verifyWebTableCellData(xpathOfRebillinfoTable,rowNumber,1,
                "CHANGE","Verifying status", false);
        FunctionLibrary.verifyWebTableCellData(xpathOfRebillinfoTable,rowNumber,4,
                ReBillPayType,"Verifying ReBill Type", false);
        
               
               
        verifyNonFinacialHistoryRefDescription("CHANGE", "REBILL", "Rebill History");
    	
    	String sql = "selECT S.* FROM SIEBEL.s_pty_pay_prfl S, siebel.s_org_ext o where S.X_ETC_ACCOUNT_ID = O.SERVICE_EMP_cnt and o.ou_num = '"+accountNum+"'";
    	
    	FunctionLibrary.verifyTextBoxValue(finStatus, "AUTOPAY", "Verify Fin Status", true);
    	FunctionLibrary.verifyTextBoxValue(rebillTypeTxtBox, ReBillPayType, "Verify Rebill Type", true);
    	
    	HashMap<String, String> sqlResult = CommonLibrary.executeSqlAndGetOutputAsHashMap(sql);
    	if(sqlResult.get("PAY_TYPE_CD").toString().equalsIgnoreCase(ReBillPayType))
    	{
    		
    		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Saving rebill type should be updated in database.<br>Actual: updated", LogStatus.PASS, false);
    	}else
    	{
    		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Saving rebill type should be updated in database.<br>Actual: Not updated\"", LogStatus.FAIL, false);
    	}
    	
    	String maskedBankAccNumShouldBe = "xxxxxx"+dataObj.get("ACHBankNumber").substring(dataObj.get("ACHBankNumber").length() - 4);
    	
    	if(sqlResult.get("X_ACCNTNUM_MASKING").toString().contains(maskedBankAccNumShouldBe))
    	{
    		
    		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Bank number should be updated in database.<br>Actual: updated - "+maskedBankAccNumShouldBe, LogStatus.PASS, false);
    	}else
    	{
    		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Bank number should be updated and masked in database.<br>Actual: Not updated", LogStatus.FAIL, false);
    	}
        }

        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_088' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }


    }
    
    public static void tcAccountMaintenanceACMO089()  {
    	try{
    	String accountNum=dataObj.get("AccountNumber");
    	 

        if(accountNum.length()>1)
        {
        String ReBillPayType=dataObj.get("Rebill Pay Type");
        String RoutingNumber=dataObj.get("ACHBankRouting");
        String SavingNumber=dataObj.get("ACHBankNumber");
        String Desc;
        CommonLibrary.searchForAccount(accountNum);
        //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
        FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
       		                                         accountInfoDevicesTab,accountInfoTab,5,20);
        FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(addressTab)));
        
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Default rebill type: " +
        FunctionLibrary.getElement(rebillTypeTxtBox).getAttribute("value"),
                LogStatus.INFO,    true);
        
        //Clicking on Rebill Info tab
        FunctionLibrary.clickObject(rebillInfoLink,"","Click Rebill Info link");

        FunctionLibrary.clickObject(replenishListEditBtn,"","Click edit button");
        Desc="Selecting MasterCard from ReBill Pay type";
        
           // FunctionLibrary.Select_DropDownList(replenishmentDetailsRebillPayTypeDropdown,ReBillPayType,Desc);
            FunctionLibrary.selectDropDownListByValue(replenishmentDetailsRebillPayTypeDropdown,ReBillPayType,Desc);
            

        FunctionLibrary.setText(paymentDetailsBankAccountNbrTxtBox,SavingNumber,"Enter banking number");
        FunctionLibrary.setText(paymentDetailsRoutingNbrTxtBox, RoutingNumber, "Enter routing number");


        Desc="Click On Save";
        FunctionLibrary.clickObject(replenishmentDetailsSaveBtn,"",Desc);
        try {
            WebDriverWait  wai= new WebDriverWait(FunctionLibrary.objDriver,8);
            wai.until(ExpectedConditions.alertIsPresent());
            Alert alert1 = FunctionLibrary.objDriver.switchTo().alert();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Alert with text: " + alert1.getText(),
                    LogStatus.INFO,    false);
            alert1.accept();

        } catch (Exception e){

        }
        
        FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@title='"+ReBillPayType+"']")));

        String rebillTypeUpdatedTo= FunctionLibrary.getElement(rebillTypeTxtBox).getAttribute("value");
       if(rebillTypeUpdatedTo.startsWith(ReBillPayType))
    	   {
    	   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Updated to Rebill pay type: "+ReBillPayType,
    		                LogStatus.PASS,    true);
    		        
    	   }
       
       else
       {
    	   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: "+ReBillPayType +". Actual: "+rebillTypeUpdatedTo,
	                LogStatus.FAIL,    true); 
       }
        //Clicking on Replenishments History

        FunctionLibrary.clickObject(replenishmentHistoryTab,"","Click Replenishment History link");

        String xpathOfRebillinfoTable = "./*//*[@summary='Replenishment Info History List']";
        FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOf(FunctionLibrary.getElement(xpathOfRebillinfoTable)));
        int rowNumber = FunctionLibrary.getRowNumberFromWebTable(xpathOfRebillinfoTable,ReBillPayType,"get the row number");
        System.out.println("after");
        System.out.println(rowNumber);
        FunctionLibrary.verifyWebTableCellData(xpathOfRebillinfoTable,rowNumber,1,
                "CHANGE","Verifying status", false);
        FunctionLibrary.verifyWebTableCellData(xpathOfRebillinfoTable,rowNumber,4,
                ReBillPayType,"Verifying ReBill Type", false);
        verifyNonFinacialHistoryRefDescription("CHANGE", "REBILL", "Rebill History");
    	
    	String sql = "selECT S.* FROM SIEBEL.s_pty_pay_prfl S, siebel.s_org_ext o where S.X_ETC_ACCOUNT_ID = O.SERVICE_EMP_cnt and o.ou_num = '"+accountNum+"'";
    	
    	FunctionLibrary.verifyTextBoxValue(finStatus, "AUTOPAY", "Verify Fin Status", true);
    	FunctionLibrary.verifyTextBoxValue(rebillTypeTxtBox, ReBillPayType, "Verify Rebill Type", true);
    	
    	HashMap<String, String> sqlResult = CommonLibrary.executeSqlAndGetOutputAsHashMap(sql);
    	if(sqlResult.get("PAY_TYPE_CD").toString().equalsIgnoreCase(ReBillPayType))
    	{
    		
    		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Saving rebill type should be updated in database.<br>Actual: updated", LogStatus.PASS, false);
    	}else
    	{
    		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Saving rebill type should be updated in database.<br>Actual: Not updated\"", LogStatus.FAIL, false);
    	}
    	
    	String maskedBankAccNumShouldBe = "xxxxx"+dataObj.get("ACHBankNumber").substring(dataObj.get("ACHBankNumber").length() - 4);
    	
    	if(sqlResult.get("X_ACCNTNUM_MASKING").toString().toUpperCase().contains(maskedBankAccNumShouldBe.toUpperCase()))
    	{
    		
    		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Bank number should be updated in database.<br>Actual: updated - "+maskedBankAccNumShouldBe, LogStatus.PASS, false);
    	}else
    	{
    		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Bank number should be updated and masked in database -"+maskedBankAccNumShouldBe.toUpperCase()
    				+ "<br>Actual: And having is-"+sqlResult.get("X_ACCNTNUM_MASKING").toString().toUpperCase(), LogStatus.FAIL, false);
    	}
          
        
        }
                else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_089' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
        
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }


    }

    public static void tcAccountMaintenanceACMO100()    {
    	try{
    	String accountNum=dataObj.get("AccountNumber");
    	 

        if(accountNum.length()>1)
        {

        String ReBillPayType=dataObj.get("Rebill Pay Type");
        String ACHBankRouting= dataObj.get("ACHBankRouting");
        String ACHBankNumber=dataObj.get("ACHBankNumber");
        String Desc;
        System.out.println(dataObj.get("TestCaseId"));

        CommonLibrary.searchForAccount(accountNum);
        //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
        FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
       		                                         accountInfoDevicesTab,accountInfoTab,5,20);
        FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(addressTab)));
        
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Default rebill type: " +
        FunctionLibrary.getElement(rebillTypeTxtBox).getAttribute("value"),
                LogStatus.INFO,    true);
        
        //Clicking on Rebill Info tab
        FunctionLibrary.clickObject(rebillInfoLink,"","Click Rebill Info link");

        FunctionLibrary.clickObject(rebillInfoAppletNewBtn,"","Click edit button");
        Desc="Selecting MasterCard from ReBill Pay type";
        
           // FunctionLibrary.Select_DropDownList(replenishmentDetailsRebillPayTypeDropdown,ReBillPayType,Desc);
            FunctionLibrary.selectDropDownListByValue(replenishmentDetailsRebillPayTypeDropdown,ReBillPayType,Desc);
            
        Desc="Enter ACH bank account Details";
        
        FunctionLibrary.setText(bankNumTxtBox,ACHBankNumber,"Enter banking number");
        FunctionLibrary.setText(routingNumTxtBox, ACHBankRouting, "Enter routing number");

        Desc="Click On Save";
        FunctionLibrary.clickObject(replenishmentDetailsSaveBtn,"",Desc);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        handleAlertIfPresent(10);
        //Clicking on Replenishments History

        FunctionLibrary.objDriver.findElement(By.xpath(replenishmentHistoryTab)).click();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        

        System.out.println("before");
        int rowNumber = FunctionLibrary.getRowNumberFromWebTable(xpathOfRebillinfoTable,ReBillPayType,"get the row number");
        System.out.println("after");
        System.out.println(rowNumber);
        FunctionLibrary.verifyWebTableCellData(xpathOfRebillinfoTable,rowNumber,1,
                "ADD","Verifying status", false);
        FunctionLibrary.verifyWebTableCellData(xpathOfRebillinfoTable,rowNumber,4,
                ReBillPayType,"Verifying ReBill Type", false);
        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_100' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }


    }
  
    public static void tcAccountMaintenanceACMO107() throws Exception {
    	
    	try{

        String accountNum=dataObj.get("AccountNumber");
        
        if(accountNum.length()>2)
        {
            CommonLibrary.searchForAccount(accountNum);
            //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
            
       		//to add a new vehicle, a device must be added before to it.    
    		addDeviceAndReturnDeviceId();
    	    
            //add vehicle now                                   
            String []plateNumsAdded=addVehicleAndReturnPlateNums();
                      
            //do payment  now                                         
            makePayment(dataObj.get("Rebill Pay Type"));  
                     
            FunctionLibrary.clickObject(accountsInfoTab, "", "Clickk Account Info Link");
            FunctionLibrary.clickObject(vehiclesTab, "", "Click vehicles link");
            
            verifyVehicleUnderVehicleHistory(plateNumsAdded[0]);
            
            verifyNotesByRefOfDescription(plateNumsAdded[0],"VEHICLES","Vehicle History");            

        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_107' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
        
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }



    }

    public static void tcAccountMaintenanceACMO108() {
    	try{

    		String accountNum=dataObj.get("AccountNumber");
    	 

        if(accountNum.length()>1)
        	
        {
        	
        	CamsPaymentProcessing.makefinancialStatusAsLow(accountNum);
            CommonLibrary.searchForAccount(accountNum);
           // FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
            
       		//to add a new vehicle, a device must be added before to it.    
    		addDeviceAndReturnDeviceId();
    	    
            //add vehicle now                                   
            String []plateNumsAdded=addVehicleAndReturnPlateNums();
                      
            //do payment  now                                         
            makePayment(dataObj.get("Rebill Pay Type"));  
                     
            FunctionLibrary.clickObject(accountsInfoTab, "", "Clickk Account Info Link");
            FunctionLibrary.clickObject(vehiclesTab, "", "Click vehicles link");
            
            verifyVehicleUnderVehicleHistory(plateNumsAdded[0]);

        }
               else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_108' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
        
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }


    }

    public static void tcAccountMaintenanceACMO109() throws Exception{
    	try{
    		String accountNum=dataObj.get("AccountNumber");
    	 
        if(accountNum.length()>1)
        {
        	CamsPaymentProcessing.makefinancialStatusAsZero(accountNum);
            CommonLibrary.searchForAccount(accountNum);
            //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
            
       		//to add a new vehicle, a device must be added before to it.    
    		addDeviceAndReturnDeviceId();
    	    
            //add vehicle now                                   
            String []plateNumsAdded=addVehicleAndReturnPlateNums();
                      
            //do payment  now                                         
            makePayment(dataObj.get("Rebill Pay Type"));  
                     
            FunctionLibrary.clickObject(accountsInfoTab, "", "Clickk Account Info Link");
            FunctionLibrary.clickObject(vehiclesTab, "", "Click vehicles link");
            
            verifyVehicleUnderVehicleHistory(plateNumsAdded[0]);

        }
       
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_109' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }

    }

    public static void tcAccountMaintenanceACMO110() throws Exception{
    	try{

    		String accountNum=dataObj.get("AccountNumber");
    	 
        if(accountNum.length()>1)
        {
            CommonLibrary.searchForAccount(accountNum);
            //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
            
       		//to add a new vehicle, a device must be added before to it.    
    		addDeviceAndReturnDeviceId();
    	    
            //add vehicle now                                   
            String []plateNumsAdded=addVehicleAndReturnPlateNums();
                      
            //do payment  now                                         
            makePayment(dataObj.get("Rebill Pay Type"));  
                     
            FunctionLibrary.clickObject(accountsInfoTab, "", "Clickk Account Info Link");
            FunctionLibrary.clickObject(vehiclesTab, "", "Click vehicles link");
            
            verifyVehicleUnderVehicleHistory(plateNumsAdded[0]);

        }
               else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_110' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }

    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }

    }

    public static void tcAccountMaintenanceACMO118() throws Exception {
    	try{
    		String accountNum=dataObj.get("AccountNumber");
    	 
        dataObj.get("Deviceinfo");
        dataObj.get("Rebill Pay Type");

        if(accountNum.length()>1)
        {
            CommonLibrary.searchForAccount(accountNum);
            //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
            
       		//to add a new vehicle, a device must be added before to it.    
    		addDeviceAndReturnDeviceId();
    	    
            //add vehicle now                                   
            String []plateNumsAdded=addVehicleAndReturnPlateNums();
                      
            //do payment  now                                         
            makePayment(dataObj.get("Rebill Pay Type"));  
                     
            FunctionLibrary.clickObject(accountsInfoTab, "", "Clickk Account Info Link");
            FunctionLibrary.clickObject(vehiclesTab, "", "Click vehicles link");
            
            verifyVehicleUnderVehicleHistory(plateNumsAdded[0]);

               }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_118' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }


    }

    public static void tcAccountMaintenanceACMO125() throws InterruptedException  {
    	try{
    		String accountNum=dataObj.get("AccountNumber");
    	 
    		
  
        if(accountNum.length()>1)
        {
       	 //search for account
	        CommonLibrary.searchForAccount(accountNum);//326117562
	        //click the link with account number
	        //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
	        FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
	        FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 60, ChallangeQA2Dropdown);
	        
	        
	        addDeviceAndReturnDeviceId();
       	
           String folderPath = ReportLibrary.getPath() + "\\vehicleImportFiles\\";
           ReportLibrary.checkCreateDirectory(folderPath);
           String filePath=folderPath+accountNum+"_VehiclesImportFile.txt";
           
           String [] importedVechileNums = CommonLibrary.createFileForImportingVechiles(Integer.valueOf(accountNum), 
           								Integer.valueOf(dataObj.get("Deviceinfo").split(":")[1]), filePath);
	       
	        FunctionLibrary.clickObject(vehiclesTab,"","Clicking vehicles tab");
	        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(vehicleListNewBtn,
	        		vehiclesTab, 6, 20);
	        FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(vehicleListNewBtn)));
	        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Screenshot before vehicles import", LogStatus.INFO, true);

	        vehicleImportAccountMaintenance(filePath);
	        makePayment(dataObj.get("Rebill Pay Type"));          
	        

           FunctionLibrary.clickObject(accountsInfoTab, "", "Clickk Account Info Link");
           FunctionLibrary.clickObject(vehiclesTab, "", "Click vehicles link");
	        
	        FunctionLibrary.clickObject(vehicleHistoryTab, "", "clicking on vehicle history");
	        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
	        ("//table[@summary=' Account Vehicle History List Applet']", vehicleHistoryTab, 10, 40);
	        
	        FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(plateNumberElement)));
	        for(int iterator = 0;iterator<=importedVechileNums.length-1;iterator++)
	        {
	        	if(iterator==0 || iterator==1 ||iterator==importedVechileNums.length-1)
	        	{
	        		
	    	
		            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifing imported vehicle number: "+ 
		            importedVechileNums[iterator], LogStatus.INFO, false);
		             FunctionLibrary.setText(queryTxtBox,"Plate Number","Enter Plate Number in search drop down");
		             FunctionLibrary.setText(queryValTxtBox,importedVechileNums[iterator],"Enter Plate Number value");
		             FunctionLibrary.clickObject(""+xpathGoButton,"","Click Go button");//(//span[text()='Go'])[2]
		             ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
		                     "Searching for added plate number in vehicle history tab", LogStatus.INFO, false);
		             Thread.sleep(5000);

		             int rowNumberWithPlateNumber = 1;
		             System.out.println(rowNumberWithPlateNumber);
		             FunctionLibrary.verifyWebTableCellData(xpathPlateTable, rowNumberWithPlateNumber, 1, "ADD",
		                     "Verifying Status",true);
		             FunctionLibrary.verifyWebTableCellData(xpathPlateTable, rowNumberWithPlateNumber, 4, importedVechileNums[iterator],
		                     "Verifying plate number",false);
		    		  String sql1 = "select * from siebel.V_vehicle where plate_number = '"+importedVechileNums[iterator]+"'";
		    	        if(!(CommonLibrary.executeSqlAndGetOutputAsHashMap(sql1).get("PLATE_NUMBER")==null))
		    			{
		    				ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Epected: Newly added vehicle's number "+importedVechileNums[iterator]+" should be in database <br> Actual: Vehicle number is present",LogStatus.PASS,false);
		    			}
		    	        else
		    	        {
		    				ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Epected: Newly added vehicle's number "+importedVechileNums[iterator]+" should be in database <br> Actual: Vehicle number is present",LogStatus.FAIL,false);
		    	        }
		  
	
	        	}
	        }
	        }
        else
    {
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                "Test data row is 'TestDataForACMO_125' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

    }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }



    }//end of while loop

    public static void tcAccountMaintenanceACMO131() throws Exception{
    	try{
    		String accountNum=dataObj.get("AccountNumber");
    	 
        if(accountNum.length()>1)
        {

            CommonLibrary.searchForAccount(accountNum);
            //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
            
       		//to add a new vehicle, a device must be added before to it.    
    		addDeviceAndReturnDeviceId();
    	    
            //add vehicle now                                   
            String []plateNumsAdded=addVehicleAndReturnPlateNums();
                      
            //do payment  now                                         
            makePayment(dataObj.get("Rebill Pay Type"));  
                     
            FunctionLibrary.clickObject(accountsInfoTab, "", "Clickk Account Info Link");
            FunctionLibrary.clickObject(vehiclesTab, "", "Click vehicles link");
            
            verifyVehicleUnderVehicleHistory(plateNumsAdded[0]);

        }
        
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_131' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }



    }

    public static void tcAccountMaintenanceACMO133() throws Exception{
    	try{
    		String accountNum=dataObj.get("AccountNumber");
    	 

        if(accountNum.length()>1)
        {
        	CamsPaymentProcessing.makefinancialStatusAsZero(accountNum);
            CommonLibrary.searchForAccount(accountNum);
            //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
            
       		//to add a new vehicle, a device must be added before to it.    
    		addDeviceAndReturnDeviceId();
    	    
            //add vehicle now                                   
            String []plateNumsAdded=addVehicleAndReturnPlateNums();
                      
            //do payment  now                                         
            makePayment(dataObj.get("Rebill Pay Type"));  
                     
            FunctionLibrary.clickObject(accountsInfoTab, "", "Clickk Account Info Link");
            FunctionLibrary.clickObject(vehiclesTab, "", "Click vehicles link");
            
            verifyVehicleUnderVehicleHistory(plateNumsAdded[0]);
        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_133' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }



    }

    public static void tcAccountMaintenanceACMO138() throws Exception {
    	try{

    		String accountNum=dataObj.get("AccountNumber");
    	 
        dataObj.get("Deviceinfo");
        if(accountNum.length()>1)
        {
            CommonLibrary.searchForAccount(accountNum);
            //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
            
       		//to add a new vehicle, a device must be added before to it.    
    		addDeviceAndReturnDeviceId();
    	    
            //add vehicle now                                   
            String []plateNumsAdded=addVehicleAndReturnPlateNums();
                 
            //do payment  now                                         
            makePayment(dataObj.get("Rebill Pay Type"));  
                     
            FunctionLibrary.clickObject(accountsInfoTab, "", "Clickk Account Info Link");
            FunctionLibrary.clickObject(vehiclesTab, "", "Click vehicles link");
            
            verifyVehicleUnderVehicleHistory(plateNumsAdded[0]);


        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_138' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }


    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }

    }

    public static void tcAccountMaintenanceACMO139() throws Exception {

      try{  
        dataObj.get("Deviceinfo");
        String accountNum=dataObj.get("AccountNumber");
        
        if(accountNum.length()>1)
        {
            CommonLibrary.searchForAccount(accountNum);
            //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
            
       		//to add a new vehicle, a device must be added before to it.    
    		addDeviceAndReturnDeviceId();
    	    
            //add vehicle now                                   
            String []plateNumsAdded=addVehicleAndReturnPlateNums();
                      
            //do payment  now                                         
            makePayment(dataObj.get("Rebill Pay Type"));  
                     
            FunctionLibrary.clickObject(accountsInfoTab, "", "Clickk Account Info Link");
            FunctionLibrary.clickObject(vehiclesTab, "", "Click vehicles link");
            
            verifyVehicleUnderVehicleHistory(plateNumsAdded[0]);

        }


        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_139' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
      }catch(Exception e){
          ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
    }


    }

    public static void tcAccountMaintenanceACMO141() throws InterruptedException     {
	try{
		String accountNum=dataObj.get("AccountNumber");
	 

        if(accountNum.length()>1)
        {
	     
        	addPlanAndMakePayment(accountNum,dataObj.get("PlansInfo"),dataObj.get("Rebill Pay Type"));

        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_141' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }

    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }


    }

    public static void tcAccountMaintenanceACMO143() throws InterruptedException     {
    	try{
    	String accountNum=dataObj.get("AccountNumber");
   	 

        if(accountNum.length()>1)
        {
        	addPlanAndMakePayment(accountNum,dataObj.get("PlansInfo"),dataObj.get("Rebill Pay Type"));
        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_143' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
    }

    	catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }



    }

    public static void tcAccountMaintenanceACMO180() throws Exception {
    	try{

    		String accountNum=dataObj.get("AccountNumber");
    	 
        if(accountNum.length()>1)
        {

            CommonLibrary.searchForAccount(accountNum);
            //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
            
       		//to add a new vehicle, a device must be added before to it.    
    		addDeviceAndReturnDeviceId();
    	    
            //add vehicle now                                   
            String []plateNumsAdded=addVehicleAndReturnPlateNums();
                      
            //do payment  now                                         
            makePayment(dataObj.get("Rebill Pay Type"));  
                     
            FunctionLibrary.clickObject(accountsInfoTab, "", "Clickk Account Info Link");
            FunctionLibrary.clickObject(vehiclesTab, "", "Click vehicles link");
            
            verifyVehicleUnderVehicleHistory(plateNumsAdded[0]);
            }
        
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_180' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }

    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }


    }

    public static void tcAccountMaintenanceACMO181() throws Exception {
    	
    	try{

    		String accountNum=dataObj.get("AccountNumber");
    	 

        if(accountNum.length()>1)
        {
            CommonLibrary.searchForAccount(accountNum);
            //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
            
       		//to add a new vehicle, a device must be added before to it.    
    		addDeviceAndReturnDeviceId();
    	    
            //add vehicle now                                   
            String []plateNumsAdded=addVehicleAndReturnPlateNums();
                      
            //do payment  now                                         
            makePayment(dataObj.get("Rebill Pay Type"));  
                     
            FunctionLibrary.clickObject(accountsInfoTab, "", "Clickk Account Info Link");
            FunctionLibrary.clickObject(vehiclesTab, "", "Click vehicles link");
            
            verifyVehicleUnderVehicleHistory(plateNumsAdded[0]);
        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_181' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }

    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }

    }

    public static void tcAccountMaintenanceACMO193() throws InterruptedException {
    	try{

    		String accountNum=dataObj.get("AccountNumber");
        if(accountNum.length()>1)
        {

            CommonLibrary.searchForAccount(accountNum);

            //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
  	
	    	addDeviceAndReturnDeviceId();
	    	String firstPaymentMode = "VISA";
	    	String cardAmount = dataObj.get("Cardamount");
	    	String cashAmount = dataObj.get("Cashamount");
	    	String secondPaymentType= dataObj.get("Rebill Pay Type");
	    	String cardNum = dataObj.get("CreditCardNo");
	    	String cardExpMonth = dataObj.get("ExpMonth");
	    	String cardExpYear = dataObj.get("ExpYear");

	    	String stringFormatForMultiPayments = 
	    			firstPaymentMode+"_"+cardNum+"_"+cardExpMonth+"_"+cardExpYear+"_"+cardAmount+";"+secondPaymentType+"_"+cashAmount;
	    	
	    	String paymnetRefNum = makePaymentWithMultiModes(stringFormatForMultiPayments);
	    	
	    	 FunctionLibrary.objDriver.findElement(By.xpath(financialTransListQueryTxtBox)).sendKeys("Composit Transaction Id");
	         FunctionLibrary.objDriver.findElement(By.xpath(financialTransListValueTxtBox)).sendKeys(paymnetRefNum);
	
	         FunctionLibrary.clickObject(goBtn, "", "Clicking Go button");
	         Thread.sleep(4000);
	
	         int getrowNumberWithFinanceTransactionCash = FunctionLibrary.getRowNumberFromWebTable(financialListTable,
	                 "CASH", "get the row number of cash tranaction");
	         int getrowNumberWithFinanceTransactionVisa = FunctionLibrary.getRowNumberFromWebTable(financialListTable,
	                 "VISA", "get the row number of card transaction");
	
	         FunctionLibrary.verifyWebTableCellData(financialListTable,getrowNumberWithFinanceTransactionCash,4,
	                 "$10.00","Verifying Payment by Cash ", true);
	         FunctionLibrary.verifyWebTableCellData(financialListTable,getrowNumberWithFinanceTransactionVisa,4,
	                 "$20.00","Verifying Payment by Card", false);
	
        }
    	 
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }

    }

    public static void tcAccountMaintenanceACMO199() throws InterruptedException {
    	try{
    		String accountNum=dataObj.get("AccountNumber");
    	         if(accountNum.length()>1)
        {


            CommonLibrary.searchForAccount(accountNum);

        
        //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
        FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(addressTab)));
        String Desc="";
        Desc="Clicking Devices tab";
        FunctionLibrary.clickObject(deviceTab,"",Desc);
        
        FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(assignedDeviceList)));

        int deviceListCount=FunctionLibrary.objDriver.findElements(By.xpath(assignedDeviceListTblRows)).size();
        System.out.print("Devicecount:"+deviceListCount);
        if(deviceListCount>=2) {
            System.out.print(FunctionLibrary.objDriver.findElement(By.xpath(statusColumn)).getText());

            if(FunctionLibrary.objDriver.findElement(By.xpath(statusColumn)).getText().equals("ACTIVE")) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Device Status is in ACTIVE",LogStatus.INFO,true);
                Desc = "clicking on status";
                FunctionLibrary.clickObject(statusColumn, "", Desc);
                FunctionLibrary.objDriver.findElement(By.xpath(statusTextbox)).clear();

                Desc = "Enter status field";
                FunctionLibrary.setText(statusTextbox, "LOST", Desc);

                String deviceNumber=FunctionLibrary.objDriver.findElement(By.xpath(deviceNumberElement)).getText();

                Desc = "Clicking Save button";
                FunctionLibrary.clickObject(assignedSaveBtn, "", Desc);
                Thread.sleep(5000);
                FunctionLibrary.clickObject(deviceHistoryLink,"","Click Device history");
                String xpathOfDeviceHistoryTable = ".//*[@summary='Vector Account Device History List']";
                int rowNumberWithDeviceNumber = FunctionLibrary.getRowNumberFromWebTable(xpathOfDeviceHistoryTable,deviceNumber,"get the row number");

                FunctionLibrary.verifyWebTableCellData(xpathOfDeviceHistoryTable,rowNumberWithDeviceNumber,6,"LOST","Verifying status", true);
            }
            else
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Assigned Device is not in Active Status",LogStatus.FAIL,true);
            }
        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Assigned Devices not available",LogStatus.FAIL,true);
        }


        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_199' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }


    }

    public static void tcAccountMaintenanceACMO200() throws InterruptedException {
    	try{
    		String accountNum=dataObj.get("AccountNumber");
    	 
        if(accountNum.length()>1)
        {


            CommonLibrary.searchForAccount(accountNum);

        Thread.sleep(8000);
        //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
        FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
       		                                         accountInfoDevicesTab,accountInfoTab,5,20);
        String Desc="";
        //HashMap<String,String> eachTestCaseData =new HashMap();

        Desc="Clicking Devices tab";
        FunctionLibrary.clickObject(deviceTab,"",Desc);

        int deviceListCount=FunctionLibrary.objDriver.findElements(By.xpath(assignedDeviceListTblRows)).size();
        System.out.print("Devicecount:"+deviceListCount);
        if(deviceListCount>=2) {
            System.out.print("TEXT:++++++"+FunctionLibrary.objDriver.findElement(By.xpath(statusColumn)).getText());
            if(FunctionLibrary.objDriver.findElement(By.xpath(statusColumn)).getText().equals("ACTIVE")) {
                Desc = "clicking on status";
                FunctionLibrary.clickObject(statusColumn, "", Desc);
                FunctionLibrary.objDriver.findElement(By.xpath(statusTextbox)).clear();

                Desc = "Enter status field";
                FunctionLibrary.setText(statusTextbox, "STOLEN", Desc);

                String deviceNumber=FunctionLibrary.objDriver.findElement(By.xpath(deviceNumberElement)).getText();

                Desc = "Clicking Save button";
                FunctionLibrary.clickObject(assignedSaveBtn, "", Desc);
                Thread.sleep(5000);
                FunctionLibrary.clickObject(deviceHistoryLink,"","Click Device history");
                
                int rowNumberWithDeviceNumber = FunctionLibrary.getRowNumberFromWebTable(xpathOfDeviceHistoryTable,deviceNumber,"get the row number");

                FunctionLibrary.verifyWebTableCellData(xpathOfDeviceHistoryTable,rowNumberWithDeviceNumber,6,"STOLEN","Verifying status", true);
            }
            else
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Assigned Device is not in Active Status",LogStatus.FAIL,true);
            }
        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Assigned Devices not available",LogStatus.FAIL,true);
        }


        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_200' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }



    }
   
    public static void tcAccountMaintenanceACMO205() throws Exception {
    	
    	try{
    		String accountNum=dataObj.get("AccountNumber");
    	 
        if(accountNum.length()>1)
        {

            CommonLibrary.searchForAccount(accountNum);
            //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
            
       		//to add a new vehicle, a device must be added before to it.    
    		addDeviceAndReturnDeviceId();
    	    
            //add vehicle now                                   
            String []plateNumsAdded=addVehicleAndReturnPlateNums();
                      
            //do payment  now                                         
            makePayment(dataObj.get("Rebill Pay Type"));  
                     
            FunctionLibrary.clickObject(accountsInfoTab, "", "Clickk Account Info Link");
            FunctionLibrary.clickObject(vehiclesTab, "", "Click vehicles link");
            
            verifyVehicleUnderVehicleHistory(plateNumsAdded[0]);
        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_205' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }



    }

    public static void tcAccountMaintenanceACMO206() throws Exception {
    	try{
    		String accountNum=dataObj.get("AccountNumber");
    	 
        if(accountNum.length()>1)
        {

            CommonLibrary.searchForAccount(accountNum);
            //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
            
       		//to add a new vehicle, a device must be added before to it.    
    		addDeviceAndReturnDeviceId();
    	    
            //add vehicle now                                   
            String []plateNumsAdded=addVehicleAndReturnPlateNums();
                      
            //do payment  now                                         
            makePayment(dataObj.get("Rebill Pay Type"));  
                     
            FunctionLibrary.clickObject(accountsInfoTab, "", "Clickk Account Info Link");
            FunctionLibrary.clickObject(vehiclesTab, "", "Click vehicles link");
            
            verifyVehicleUnderVehicleHistory(plateNumsAdded[0]);
        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_206' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }



    }

    public static void tcAccountMaintenanceACMO207() throws Exception {
    	try{
    		String accountNum=dataObj.get("AccountNumber");
    	 
        if(accountNum.length()>1)
        {

            CommonLibrary.searchForAccount(accountNum);
            //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
            
       		//to add a new vehicle, a device must be added before to it.    
    		addDeviceAndReturnDeviceId();
    	    
            //add vehicle now                                   
            String []plateNumsAdded=addVehicleAndReturnPlateNums();
                      
            //do payment  now                                         
            makePayment(dataObj.get("Rebill Pay Type"));  
                     
            FunctionLibrary.clickObject(accountsInfoTab, "", "Clickk Account Info Link");
            FunctionLibrary.clickObject(vehiclesTab, "", "Click vehicles link");
            
            verifyVehicleUnderVehicleHistory(plateNumsAdded[0]);

        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_207' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }

    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }

    }

    public static void tcAccountMaintenanceACMO215() throws InterruptedException{
    	try{
    		String accountNum=dataObj.get("AccountNumber");
    	 
        if(accountNum.length()>1)
        {

        CommonLibrary.searchForAccount(accountNum);

        
        //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
        FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
       		                                         accountInfoDevicesTab,accountInfoTab,5,20);

        FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 60, ChallangeQA2Dropdown);
       // Thread.sleep(6000);
        FunctionLibrary.objDriver.findElement(By.xpath(statementDeliveryDropDown)).click();
        //Select Mail
       // WebElement elementWebTable = FunctionLibrary.getElement(xpathOfNotesTable);
        FunctionLibrary.objDriver.findElement(By.xpath(mailElement)).click();
        //Click on Ok
        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
        //Save
        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.CONTROL, "s")).perform();

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Email Changed to MAil",LogStatus.INFO,false);
        Thread.sleep(15000);
        FunctionLibrary.verifyTextBoxValue(stmtDelivModeTxtBox, "MAIL", "Verify after save chage the statement frequency mode", true);
        //Thread.sleep(3000);
        System.out.println("Saved Successfully");
        //Verify Account history
        FunctionLibrary.clickObject(accountHistoryLink,"","Click Account History");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        
        
        int rowNumberWithEmailId = FunctionLibrary.getRowNumberFromWebTable(xpathOfAccountHistory,"Mail","get the row number");
        System.out.println(rowNumberWithEmailId);
        FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistory,rowNumberWithEmailId,1,
                "CHANGE","Verifying status", true);
        FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistory,rowNumberWithEmailId,4,
                "ACTIVE","Status", false);
        FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistory,rowNumberWithEmailId,11,
                "EMAIL","Correspondence Delivery Mode", false);
        FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistory,rowNumberWithEmailId,35,
                "MONTHLY","Verifying Delivery Frequency Mode", false);
           FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistory,rowNumberWithEmailId,45,
                "Mail","Verifying email", false);
             
        verifyNonFinacialHistory("AUDITACCNT","VIEW");
        System.out.println("Completed Of Non Financial History");

        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_215' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }

    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }

    }

    public static void tcAccountMaintenanceACMO216() {
    	try{
    		String accountNum=dataObj.get("AccountNumber");
    	 
        if(accountNum.length()>1)
        {


            String newEmailId = "abcd" + DateTime.now().getMillisOfSecond() + DateTime.now().getSecondOfMinute() + DateTime.now().getMillisOfSecond() +
                    "@testmail.com";
            System.out.println(newEmailId);
            String oldEmailId;
            //String accountNumber = dataObj.get("AccountNumber");

            // CommonLibrary.loginSiebelApp(dataObj);
            CommonLibrary.searchForAccount(accountNum);
            FunctionLibrary.clickObject(xpathAccLink, "", "Clicking on Account number link");
            oldEmailId = FunctionLibrary.objDriver.findElement(By.xpath(emailAddTxtBox)).getAttribute("value");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>"
                    + " Updating the mail id from: " + oldEmailId + " to: " + newEmailId, LogStatus.INFO, true);
            FunctionLibrary.objDriver.findElement(By.xpath(emailAddTxtBox)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(emailAddTxtBox)).sendKeys(newEmailId);

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>"
                    + " email id updated to: " + newEmailId, LogStatus.INFO, true);

            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.CONTROL, "s")).perform();

            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                String alertText;

                Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                alertText =alert.getText();
                System.out.println("Popup is displayed with text" + alertText);

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>"
                        + "Popup dispalyed with text: " + alertText, LogStatus.INFO, false);

                alert.accept();
            }
            catch(Exception e)
            {  try{
                FunctionLibrary.clickObject(okBtn, "", "Clicking Ok button");
            }
            catch(Exception e1){}
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e1) {

                e1.printStackTrace();
            }
            //Verifying Account History
            FunctionLibrary.clickObject(accountHistoryLink, "", "Click Account History");

            
            int rowNumberWithEmailId = FunctionLibrary.getRowNumberFromWebTable(xpathOfAccountHistory, newEmailId, "get the row number");
            System.out.println(rowNumberWithEmailId);
            FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistory, rowNumberWithEmailId, 1,
                    "CHANGE", "Verifying status", true);
            //FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistory,rowNumberWithEmailId,2,"17","Verifying version");
            FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistory, rowNumberWithEmailId, 4,
                    "ACTIVE", "Status", false);
            FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistory, rowNumberWithEmailId, 11,
                    "Email", "Correspondence Delivery Mode", false);
            FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistory, rowNumberWithEmailId, 30,
                    accountNum, "Verifying account number", false);
            FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistory, rowNumberWithEmailId, 34,
                    newEmailId, "Verifying email", false);

            //Verifying Notes

            String NotesDescription = newEmailId;
            FunctionLibrary.clickObject(notesTab, "", "Notes");
            
            int rowNumberWithEmailDesc;
            rowNumberWithEmailDesc = FunctionLibrary.getRowNumberFromWebTable(xpathOfNotesTable, NotesDescription,
                    "get the row number");
            System.out.println(rowNumberWithEmailDesc);
            FunctionLibrary.verifyWebTableCellData(xpathOfNotesTable, rowNumberWithEmailDesc, 2, "ACCOUNT UPDATE",
                    "Verifying note in system log", true);
            FunctionLibrary.verifyWebTableCellData(xpathOfNotesTable, rowNumberWithEmailDesc, 3, "DEMOGRAPHICS",
                    "Verifying note sub type in system log", false);
            FunctionLibrary.verifyWebTableCellData(xpathOfNotesTable, rowNumberWithEmailDesc, 4, accountNum,
                    "Verifying Account number in system log", false);
            FunctionLibrary.verifyWebTableCellData(xpathOfNotesTable, rowNumberWithEmailDesc, 8, "SUMITB",
                    "Verifying created by in system log", false);
            String verifyTextForEmailChangeInNotes = "CHANGE:'Email Address' changed from '" + oldEmailId + "' to '"
                    + newEmailId + "'.";

            FunctionLibrary.verifyWebTableCellData(xpathOfNotesTable, rowNumberWithEmailDesc, 5,
                    verifyTextForEmailChangeInNotes, "Verifying email change description", false);

            //In non-finanical History

            FunctionLibrary.clickObject(nonFinacialHistoryLink, "", "Click on Non-Financial History");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            WebElement mailElement = FunctionLibrary.objDriver.findElement(By.xpath(historyTableXpath));
            System.out.println(mailElement.getText());
            System.out.println(mailElement.isDisplayed());
            if (mailElement.isDisplayed()) {

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>"
                        + "Email updated log is present in Non financial  history ", LogStatus.PASS, true);

            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>"
                        + "Couldn't find Email updated log in Non financial  history ", LogStatus.FAIL, true);
            }

        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_216' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }

    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }

    }

    public static void tcAccountMaintenanceACMO219() {
    	try{
    		String accountNum=dataObj.get("AccountNumber");
    	 
        if(accountNum.length()>1)
        {


            String newEmailId = "abcd" + DateTime.now().getMillisOfSecond() + DateTime.now().getSecondOfMinute() + DateTime.now().getMillisOfSecond() +
                    "@testmail.com";
            System.out.println(newEmailId);
            String oldEmailId;
            CommonLibrary.searchForAccount(accountNum);
            FunctionLibrary.clickObject(xpathAccLink, "", "Clicking on Account number link");
            oldEmailId = FunctionLibrary.objDriver.findElement(By.xpath(emailAddTxtBox)).getAttribute("value");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>"
                    + " Updating the mail id from: " + oldEmailId + " to: " + newEmailId, LogStatus.INFO, true);
            FunctionLibrary.objDriver.findElement(By.xpath(emailAddTxtBox)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(emailAddTxtBox)).sendKeys(newEmailId);

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>"
                    + " email id updated to: " + newEmailId, LogStatus.INFO, true);

            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.CONTROL, "s")).perform();

            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                String alertText;

                Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                alertText =alert.getText();
                System.out.println("Popup is displayed with text" + alertText);

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>"
                        + "Popup dispalyed with text: " + alertText, LogStatus.INFO, false);

                alert.accept();
            }
            catch(Exception e)
            {  try{
                FunctionLibrary.clickObject("//button[contains(text(),'Ok')]", "", "Clicking Ok button");
            }
            catch(Exception e1){}
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e1) {

                e1.printStackTrace();
            }
            //Verifying Account History
            FunctionLibrary.clickObject(accountHistoryLink, "", "Click Account History");

            
            int rowNumberWithEmailId = FunctionLibrary.getRowNumberFromWebTable(xpathOfAccountHistory, newEmailId, "get the row number");
            System.out.println(rowNumberWithEmailId);
            FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistory, rowNumberWithEmailId, 1,
                    "CHANGE", "Verifying status", true);
            FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistory, rowNumberWithEmailId, 4,
                    "ACTIVE", "Status", false);
            FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistory, rowNumberWithEmailId, 11,
                    "Email", "Correspondence Delivery Mode", false);
            FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistory, rowNumberWithEmailId, 30,
                    accountNum, "Verifying account number", false);
            FunctionLibrary.verifyWebTableCellData(xpathOfAccountHistory, rowNumberWithEmailId, 34,
                    newEmailId, "Verifying email", false);

            //Verifying Notes

            String NotesDescription = newEmailId;
            FunctionLibrary.clickObject(notesTab, "", "Notes");
            ;
            int rowNumberWithEmailDesc;
            rowNumberWithEmailDesc = FunctionLibrary.getRowNumberFromWebTable(xpathOfNotesTable, NotesDescription,
                    "get the row number");
            System.out.println(rowNumberWithEmailDesc);
            FunctionLibrary.verifyWebTableCellData(xpathOfNotesTable, rowNumberWithEmailDesc, 2, "ACCOUNT UPDATE",
                    "Verifying note in system log", true);
            FunctionLibrary.verifyWebTableCellData(xpathOfNotesTable, rowNumberWithEmailDesc, 3, "DEMOGRAPHICS",
                    "Verifying note sub type in system log", false);
            FunctionLibrary.verifyWebTableCellData(xpathOfNotesTable, rowNumberWithEmailDesc, 4, accountNum,
                    "Verifying Account number in system log", false);
            FunctionLibrary.verifyWebTableCellData(xpathOfNotesTable, rowNumberWithEmailDesc, 8, "SUMITB",
                    "Verifying created by in system log", false);
            String verifyTextForEmailChangeInNotes = "CHANGE:'Email Address' changed from '" + oldEmailId + "' to '"
                    + newEmailId + "'.";

            FunctionLibrary.verifyWebTableCellData(xpathOfNotesTable, rowNumberWithEmailDesc, 5,
                    verifyTextForEmailChangeInNotes, "Verifying email change description", false);

            //In non-finanical History

            FunctionLibrary.clickObject(nonFinacialHistoryLink, "", "Click on Non-Financial History");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            WebElement mailElement = FunctionLibrary.objDriver.findElement(By.xpath(historyTableXpath));
            System.out.println(mailElement.getText());
            System.out.println(mailElement.isDisplayed());
            if (mailElement.isDisplayed()) {

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>"
                        + "Email updated log is present in Non financial  history ", LogStatus.PASS, true);

            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>"
                        + "Couldn't find Email updated log in Non financial  history ", LogStatus.FAIL, true);
            }

            // CommonLibrary.logoutSiebelApplicaiton();
        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_219' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }

    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }

    }

    public static void tcAccountMaintenanceACMO235() throws InterruptedException {
    	try{

    		String accountNum=dataObj.get("AccountNumber");
    	 
        String Desc = "";

               if(accountNum.length()>1)
        {


            CommonLibrary.searchForAccount(accountNum);
            FunctionLibrary.clickObject(xpathAccLink, "", "Clicking on Account number link");
            WebElement we = FunctionLibrary.objDriver.findElement(By.xpath("//div[contains(@class,'mceGridField siebui-value mceField')]/input[@aria-labelledby='RebillType_Label']"));
            String rebill = we.getAttribute("value");
            if (rebill.equalsIgnoreCase("CASH")) {


                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Account rebill type is cash", LogStatus.INFO, false);
                //Clicking Device Requests new button

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                        "Clicking on Plans Tab", LogStatus.INFO, false);
                FunctionLibrary.clickObject(plansTab, "", "clicking on plans tab");
                FunctionLibrary.objDriver.findElement(By.xpath(planNewBtn)).click();
                FunctionLibrary.objDriver.findElement(By.xpath(planNameElement)).sendKeys("STANDARD");
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                        "Adding new plan", LogStatus.INFO, true);
                FunctionLibrary.objDriver.findElement(By.xpath(planSaveBtn)).click();
                Thread.sleep(3000);
                new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_UP)).perform();
                Desc = "Clicking Financials tab";
                FunctionLibrary.clickObject(financialsTab, "", Desc);
                Thread.sleep(8000);
                int rowNumber = 1;
                System.out.println(rowNumber);
                FunctionLibrary.verifyWebTableCellData(xpathOfOpenItemsTable, rowNumber, 2, "STANDARD",
                        "Verifying Subcategory",true);
                FunctionLibrary.verifyWebTableCellData(xpathOfOpenItemsTable, rowNumber, 3, "1",
                        "Verifying quantity",false);
                FunctionLibrary.verifyWebTableCellData(xpathOfOpenItemsTable, rowNumber, 4, "$10.00",
                        "Verifying unit price",false);

                Thread.sleep(4000);
                new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
                Desc = "Clicking on new button";
                FunctionLibrary.clickObject(xpathNewBtn, "", Desc);


                new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
                Thread.sleep(4000);
                Desc = "clicking on save button";
                FunctionLibrary.objDriver.findElement(By.xpath(paymentDetailsInfoSaveBtn)).click();
                Thread.sleep(4000);
                FunctionLibrary.objDriver.findElement(By.xpath(paymentListSaveBtn)).click();

                Thread.sleep(4000);

                FunctionLibrary.objDriver.findElement(By.xpath(processPayBtn)).click();

                FunctionLibrary.waitForObject = new WebDriverWait(FunctionLibrary.objDriver, 90);
                FunctionLibrary.waitForObject.until(ExpectedConditions.alertIsPresent());

                Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                System.out.println("Popup is displayed with text" + alert.getText());
                String alertText = alert.getText();
                alert.accept();
                String[] paymentRefNumber = alertText.split(":");

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>"
                        + "Successfully done the payment for the device, Reference number is" + paymentRefNumber[1].trim(), LogStatus.INFO, false);

                FunctionLibrary.objDriver.findElement(By.xpath(accountsInfoTab)).click();
                Thread.sleep(4000);


                new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_UP)).perform();
                FunctionLibrary.clickObject(plansTab, "", "clicking on plans tab");
                FunctionLibrary.clickObject(planHistoryTab, "", "clicking on plans History");
                int rowNumberWithPlanType = 1;
                System.out.println(rowNumberWithPlanType);
                FunctionLibrary.verifyWebTableCellData(xpathOfPlanHistoryTable, rowNumberWithPlanType, 2, "STANDARD",
                        "Verifying Subcategory in plan history",true);
                FunctionLibrary.verifyWebTableCellData(xpathOfPlanHistoryTable, rowNumberWithPlanType, 8, "ADD",
                        "Verifying Status in plan history",false);


            } else {

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>"
                        + "For selected account rebill type is not cash, please select an account with rebill type as cash", LogStatus.FAIL, false);

            }



        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_235' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }


    }

    public static void tcAccountMaintenanceACMO239() throws InterruptedException {
    	try{

    		String accountNum=dataObj.get("AccountNumber");
    	 
        if(accountNum.length()>1)
        {
        	 CommonLibrary.searchForAccount(accountNum);
             //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
        	 FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
             FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
            		                                         accountInfoDevicesTab,accountInfoTab,5,20);
             
        		//to add a new vehicle, a device must be added before to it.    
     		addDeviceAndReturnDeviceId();
     	    
             //add vehicle now                                   
             String []plateNumsAdded=addVehicleAndReturnPlateNums();
                       
             //do payment  now                                         
             makePayment(dataObj.get("Rebill Pay Type"));  
             
             verifySalesTaxAndTagIntAmountAfterPayment(dataObj.get("Deviceinfo").split(":")[1]);
                      
             FunctionLibrary.clickObject(accountsInfoTab, "", "Clickk Account Info Link");
             FunctionLibrary.clickObject(vehiclesTab, "", "Click vehicles link");
             
             verifyVehicleUnderVehicleHistory(plateNumsAdded[0]);   
             
             }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_239' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }


    }

    public static void tcAccountMaintenanceACMO240() throws InterruptedException {
    	try{

    		String accountNum=dataObj.get("AccountNumber");
    	         if(accountNum.length()>1)
        {


    	        	 CommonLibrary.searchForAccount(accountNum);
    	             //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
    	        	 FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
    	             FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
    	            		                                         accountInfoDevicesTab,accountInfoTab,5,20);
    	             
    	        		//to add a new vehicle, a device must be added before to it.    
    	     		addDeviceAndReturnDeviceId();
    	     	    
    	             //add vehicle now                                   
    	             String []plateNumsAdded=addVehicleAndReturnPlateNums();
    	             
    	             //do payment  now                                         
    	             makePayment(dataObj.get("Rebill Pay Type"));  
    	                        

    	             verifySalesTaxAndTagIntAmountAfterPayment(dataObj.get("Deviceinfo").split(":")[1]);
    	                      
    	             FunctionLibrary.clickObject(accountsInfoTab, "", "Clickk Account Info Link");
    	             FunctionLibrary.clickObject(vehiclesTab, "", "Click vehicles link");
    	             
    	             verifyVehicleUnderVehicleHistory(plateNumsAdded[0]);   
    	            
        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_240' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }


    }

    public static void tcAccountMaintenanceACMO265() {
    	try{

    		String accountNum=dataObj.get("AccountNumber");
    if(accountNum.length()>1)
    	{
        CommonLibrary.searchForAccount(accountNum);
        //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
        FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
       		                                         accountInfoDevicesTab,accountInfoTab,5,20);
        
        String sqlToGetNoOfRequestInDb = "select count(*) as COUNT from siebel.T_mailhouse_interface where accountnumber = '"+accountNum+"'";
        
        int noOfRecordsInDbBefore = Integer.valueOf(CommonLibrary.executeSqlAndGetOutputAsHashMap(sqlToGetNoOfRequestInDb).get("COUNT"));
        
        FunctionLibrary.clickObject(letterRequestLink, "", "Clicking letter request tab");
        FunctionLibrary.clickObject(serviceReqBtn,"","Click Request new button");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        FunctionLibrary.setText(mailHouseReqTypeTxtBox,"ACCT_PROFILE_MAIL",
                "Enter request type");

        FunctionLibrary.clickObject(deliveryModeDropDownIcon,"",
                "Click delivery model drop down");
        FunctionLibrary.clickObject(mailListItem,"","Select the delivery mode as Mail");
        FunctionLibrary.setText(descriptionTxtBox,"Adding a request",
                "Enter value in description field");


        FunctionLibrary.clickObject(serviceReqSaveBtn,"","Click save button");

        String serviceRequestNumber = FunctionLibrary.getText(srNumberTxtBox,"Get the sr number");
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Creating service request: " + serviceRequestNumber, LogStatus.INFO,true);

        FunctionLibrary.clickObject(serviceReqsubmittBtn,"","Click Request submit button");

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Sumittted the service request", LogStatus.INFO,true);

        if (FunctionLibrary.objDriver.findElement(By.xpath(mailReqSubmittedElement)).isDisplayed())
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: popup should be displayed with the text 'mail Request has been submitted'.<br>" +
                    "Actual: popup is dispalyed",LogStatus.PASS,true) ;
            FunctionLibrary.clickObject(okBtn,"","Clicking Ok buttn");

        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: popup should be displayed with the text 'Email Request has been submitted'.<br>" +
                    "Actual: popup is not dispalyed",LogStatus.FAIL,true);

        }
        
        int noOfRecordsInDbAfterLetterRequest = Integer.valueOf(CommonLibrary.executeSqlAndGetOutputAsHashMap(sqlToGetNoOfRequestInDb).get("COUNT"));
        
        if(noOfRecordsInDbAfterLetterRequest==noOfRecordsInDbBefore+1)
        {
        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Number of records in database table T_mailhouse_interface: "+(noOfRecordsInDbBefore+1) +"<br>" +
                    "Actual: "+noOfRecordsInDbAfterLetterRequest,LogStatus.PASS,false);
        }else
        {	
        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Number of records in database table T_mailhouse_interface: "+(noOfRecordsInDbBefore+1) +"<br>" +
                "Actual: "+noOfRecordsInDbAfterLetterRequest,LogStatus.FAIL,false);        	
        }
        
    }
    else
    {
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                "Test data row is 'TestDataForACMO_265' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

    }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }

}

    public static void tcAccountMaintenanceACMO274() throws InterruptedException {
    	try{

    		String accountNum=dataObj.get("AccountNumber");
    	 
        if(accountNum.length()>1)
        {

            CommonLibrary.searchForAccount(accountNum);

            //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
  	
	    	addDeviceAndReturnDeviceId();

            addVehicleAndReturnPlateNums();
            
	    	String secondPaymentType = "CHECK";
	    	String checkNum=Integer.toString(FunctionLibrary.randomNumberWithFiveDigit());
	    	String cardAmount = "10";
	    	String checkAmount = "10.8";
	    	String firstPaymentMode  = dataObj.get("Rebill Pay Type");
	    	String cardNum = dataObj.get("CreditCardNo");
	    	String cardExpMonth = dataObj.get("ExpMonth");
	    	String cardExpYear = dataObj.get("ExpYear");
	    	String stringFormatForMultiPayments = firstPaymentMode+"_"+cardNum+"_"+cardExpMonth+"_"+cardExpYear+"_"+cardAmount+";"+
	    			secondPaymentType+"_"+checkNum+"_"+checkAmount;
	    	String paymnetRefNum = makePaymentWithMultiModes(stringFormatForMultiPayments);
	    	
	    	 FunctionLibrary.objDriver.findElement(By.xpath(financialTransListQueryTxtBox)).sendKeys("Composit Transaction Id");
	         FunctionLibrary.objDriver.findElement(By.xpath(financialTransListValueTxtBox)).sendKeys(paymnetRefNum);
	
	         FunctionLibrary.clickObject(goBtn, "", "Clicking Go button");
	         Thread.sleep(4000);
	
	         int getrowNumberWithFinanceTransactionCash = FunctionLibrary.getRowNumberFromWebTable(financialListTable,
	                 "CASH", "get the row number of cash tranaction");
	         int getrowNumberWithFinanceTransactionVisa = FunctionLibrary.getRowNumberFromWebTable(financialListTable,
	                 "VISA", "get the row number of card transaction");
	
	         FunctionLibrary.verifyWebTableCellData(financialListTable,getrowNumberWithFinanceTransactionCash,4,
	                 "$10.00","Verifying Payment by Cash ", true);
	         FunctionLibrary.verifyWebTableCellData(financialListTable,getrowNumberWithFinanceTransactionVisa,4,
	                 "$20.00","Verifying Payment by Card", false);
	
        }
    	 
   	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }


    }

    public static void tcAccountMaintenanceACMO282() throws Exception {
    	try{

    		String accountNum=dataObj.get("AccountNumber");
    	 
        if(accountNum.length()>1)
        {
        	 CommonLibrary.searchForAccount(accountNum);
             //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
        	 FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
             FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
            		                                         accountInfoDevicesTab,accountInfoTab,5,20);
             
        		//to add a new vehicle, a device must be added before to it.    
     		addDeviceAndReturnDeviceId();
     	    
             //add vehicle now                                   
             String []plateNumsAdded=addVehicleAndReturnPlateNums();
                       
             //do payment  now                                         
             makePayment(dataObj.get("Rebill Pay Type"));  

             verifySalesTaxAndTagIntAmountAfterPayment(dataObj.get("Deviceinfo").split(":")[1]);
                      
        
             FunctionLibrary.clickObject(accountsInfoTab, "", "Clickk Account Info Link");
             FunctionLibrary.clickObject(vehiclesTab, "", "Click vehicles link");
             
             verifyVehicleUnderVehicleHistory(plateNumsAdded[0]);       
             }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_282' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }


    }

    public static void tcAccountMaintenanceACMO283() throws InterruptedException {
    	try{

    		String accountNum=dataObj.get("AccountNumber");

        if(accountNum.length()>1)
        {
            CommonLibrary.searchForAccount(accountNum);
            //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
            
       		//to add a new vehicle, a device must be added before to it.    
    		addDeviceAndReturnDeviceId();
    	    
            //add vehicle now                                   
            String []plateNumsAdded=addVehicleAndReturnPlateNums();
                      
            //do payment  now                                         
            makePayment(dataObj.get("Rebill Pay Type"));  

            verifySalesTaxAndTagIntAmountAfterPayment(dataObj.get("Deviceinfo").split(":")[1]);
                     
            FunctionLibrary.clickObject(accountsInfoTab, "", "Clickk Account Info Link");
            FunctionLibrary.clickObject(vehiclesTab, "", "Click vehicles link");
            
            verifyVehicleUnderVehicleHistory(plateNumsAdded[0]);

        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_283' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }


    }

    public static void tcAccountMaintenanceACMO284() throws InterruptedException {
    	try{

    		String accountNum=dataObj.get("AccountNumber");
    	 
       
        if(accountNum.length()>1)
        {
        	 CommonLibrary.searchForAccount(accountNum);
             //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
        	 FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
             FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
            		                                         accountInfoDevicesTab,accountInfoTab,5,20);
             
        		//to add a new vehicle, a device must be added before to it.    
     		addDeviceAndReturnDeviceId();
     	    
             addVehicleAndReturnPlateNums();
              
             ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Paying through card: "+dataObj.get("CreditCardNo")+ ", "+dataObj.get("ExpMonth") +", "+dataObj.get("ExpYear"),LogStatus.INFO,false);
             //do payment  now                                         
             makePayment(dataObj.get("Rebill Pay Type"));                     

             verifySalesTaxAndTagIntAmountAfterPayment(dataObj.get("Deviceinfo").split(":")[1]);
                      

        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_284' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }

    }

    public static void tcAccountMaintenanceACMO285() throws InterruptedException {
    	try{

    		String accountNum=dataObj.get("AccountNumber");
    	 
        if(accountNum.length()>1)
        {
        	 CommonLibrary.searchForAccount(accountNum);
             //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
        	 FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
             FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
            		                                         accountInfoDevicesTab,accountInfoTab,5,20);
             
        		//to add a new vehicle, a device must be added before to it.    
     		addDeviceAndReturnDeviceId();
     	    
             addVehicleAndReturnPlateNums();
                       
             //do payment  now                                         
             makePayment(dataObj.get("Rebill Pay Type"), true);
                      


        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_285' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }

    }

    public static void tcAccountMaintenanceACMO286() throws InterruptedException {
    	try{

    		String accountNum=dataObj.get("AccountNumber");
    	 
        if(accountNum.length()>1)
        {

        	 CommonLibrary.searchForAccount(accountNum);
             //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
        	 FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
             FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
            		                                         accountInfoDevicesTab,accountInfoTab,5,20);
        		//to add a new vehicle, a device must be added before to it.    
     		addDeviceAndReturnDeviceId();
     	    
             addVehicleAndReturnPlateNums();
                       
             //do payment  now                                         
             makePayment(dataObj.get("Rebill Pay Type"), true);  
                      
             
        }
             else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_286' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }


    }
    
    public static void tcAccountMaintenanceACMO287() throws InterruptedException {
    	try{

    		String accountNum=dataObj.get("AccountNumber");
    	 
        if(accountNum.length()>1)
        {

        	 CommonLibrary.searchForAccount(accountNum);
             //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
        	 FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
             FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
            		                                         accountInfoDevicesTab,accountInfoTab,5,20);
        		//to add a new vehicle, a device must be added before to it.    
     		addDeviceAndReturnDeviceId();
     	    
             addVehicleAndReturnPlateNums();
                       
             //do payment  now                                         
             makePayment(dataObj.get("Rebill Pay Type"), true);  
                      
             
        }
             else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_287' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }

   }

    public static void tcAccountMaintenanceACMO175() throws InterruptedException {
    	try{

    		String accountNum=dataObj.get("AccountNumber");
    	 
        if(accountNum.length()>1)
        {

        	 CommonLibrary.searchForAccount(accountNum);
             //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
        	 FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
             FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
            		                                         accountInfoDevicesTab,accountInfoTab,5,20);
             
        		//to add a new vehicle, a device must be added before to it.    
     		addDeviceAndReturnDeviceId();
     	    
             addVehicleAndReturnPlateNums();
                       
             //do payment  now                                         
             makePayment(dataObj.get("Rebill Pay Type"));
                      
             
        }
             else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_290' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }
    }

    public static void tcAccountMaintenanceACMO295() throws Exception {
    	
    	try{

    		String accountNum=dataObj.get("AccountNumber");
        
        if(accountNum.length()>1)
        {
            CommonLibrary.searchForAccount(accountNum);
            //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
       		//to add a new vehicle, a device must be added before to it.    
    		addDeviceAndReturnDeviceId();
    	    
            //add vehicle now                                   
            String []plateNumsAdded=addVehicleAndReturnPlateNums();
                      
            //do payment  now                                         
            makePayment(dataObj.get("Rebill Pay Type"));  
                     
            FunctionLibrary.clickObject(accountsInfoTab, "", "Clickk Account Info Link");
            FunctionLibrary.clickObject(vehiclesTab, "", "Click vehicles link");
            
            verifyVehicleUnderVehicleHistory(plateNumsAdded[0]);

        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_295' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
        
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }

    }

    public static void tcAccountMaintenanceACMO296() throws InterruptedException {
    	try{
    		String accountNum=dataObj.get("AccountNumber");

            if(accountNum.length()>1)
            {
            	
            	
            	 //search for account
    	        CommonLibrary.searchForAccount(accountNum);//326117562
    	        //click the link with account number
    	        //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
    	        FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
               		                                         accountInfoDevicesTab,accountInfoTab,5,20);
    	        FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 60, ChallangeQA2Dropdown);   	        
    	        
    	        addDeviceAndReturnDeviceId();
            	
                String folderPath = ReportLibrary.getPath() + "\\vehicleImportFiles\\";
                ReportLibrary.checkCreateDirectory(folderPath);
                String filePath=folderPath+accountNum+"_VehiclesImportFile.txt";
                
                String [] importedVechileNums = CommonLibrary.createFileForImportingVechiles(Integer.valueOf(accountNum), 
                								Integer.valueOf(dataObj.get("Deviceinfo").split(":")[1]), filePath);
    	       
    	        FunctionLibrary.clickObject(vehiclesTab,"","Clicking vehicles tab");
    	        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(vehicleListNewBtn,
    	        		vehiclesTab, 6, 20);
    	        FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(vehicleListNewBtn)));
    	        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Screenshot before vehicles import", LogStatus.INFO, true);
    	  /*      //click on import vehicle
    	        FunctionLibrary.clickObject(importVehicleBtn, "", "click on import");
    	        //desc="Waiting for the alert";
    	        
   
    	        WebDriverWait wait=new WebDriverWait(FunctionLibrary.objDriver, 10);
    	             Thread.sleep(3000);
    	        //click on browse
    	        WebElement browseImportFile= FunctionLibrary.objDriver.findElement(By.xpath(browseFileTxtBox));
    	        wait.until(ExpectedConditions.visibilityOf(browseImportFile));
    	        try{
    	            // String importFilePath="C://Users//34546//Desktop//aman_jain";
    	            browseImportFile.sendKeys(filePath);
    	
    	        }catch(Exception e){
    	
    	            FunctionLibrary.highLightElement(FunctionLibrary.objDriver,FunctionLibrary.objDriver.
    	                    findElement(By.xpath(vehicleImportBtn1)));
    	        }
    	
    	        Thread.sleep(5000);
    	        FunctionLibrary.objDriver.findElement(By.xpath(vehicleImportBtn1)).click();
    	        
    	        
    	        
    	        
    	        
    	        
    	        Thread.sleep(10000);
    	        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: "+"Vehicles to be imported"+""+"<br>"+
    	                "Actual: Vehicles are imported successfully", LogStatus.PASS, true);
    	*/
    	        vehicleImportAccountMaintenance(filePath);
    	        makePayment(dataObj.get("Rebill Pay Type"));   

                FunctionLibrary.clickObject(accountsInfoTab, "", "Clickk Account Info Link");
                FunctionLibrary.clickObject(vehiclesTab, "", "Click vehicles link");
    	        
    	        FunctionLibrary.clickObject(vehicleHistoryTab, "", "clicking on vehicle history");
    	        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
    	        ("//table[@summary=' Account Vehicle History List Applet']", vehicleHistoryTab, 10, 40);
    	        FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(plateNumberElement)));
    	        for(int iterator = 0;iterator<=importedVechileNums.length-1;iterator++)
    	        {
    	        	if(iterator==0 || iterator==1 ||iterator==importedVechileNums.length-1)
    	        	{
    	        		
    	    	
    		            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifing imported vehicle number: "+ 
    		            importedVechileNums[iterator], LogStatus.INFO, false);
    		             FunctionLibrary.setText(queryTxtBox,"Plate Number","Enter Plate Number in search drop down");
    		             FunctionLibrary.setText(queryValTxtBox,importedVechileNums[iterator],"Enter Plate Number value");
    		             FunctionLibrary.clickObject(""+xpathGoButton,"","Click Go button");//(//span[text()='Go'])[2]
   		             ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
    		                     "Searching for added plate number in vehicle history tab", LogStatus.INFO, false);
    		             Thread.sleep(5000);

    		             int rowNumberWithPlateNumber = 1;
    		             System.out.println(rowNumberWithPlateNumber);
    		             FunctionLibrary.verifyWebTableCellData(xpathPlateTable, rowNumberWithPlateNumber, 1, "ADD",
    		                     "Verifying Status",true);
    		             FunctionLibrary.verifyWebTableCellData(xpathPlateTable, rowNumberWithPlateNumber, 4, importedVechileNums[iterator],
    		                     "Verifying plate number",false);
    		             
    		       		
    		     		
    		    		  String sql1 = "select * from siebel.V_vehicle where plate_number = '"+importedVechileNums[iterator]+"'";
    		    	        if(!(CommonLibrary.executeSqlAndGetOutputAsHashMap(sql1).get("PLATE_NUMBER")==null))
    		    			{
    		    				ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Epected: Newly added vehicle's number "+importedVechileNums[iterator]+" should be in database <br> Actual: Vehicle number is present",LogStatus.PASS,false);
    		    			}
    		    	        else
    		    	        {
    		    				ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Epected: Newly added vehicle's number "+importedVechileNums[iterator]+" should be in database <br> Actual: Vehicle number is present",LogStatus.FAIL,false);
    		    	        }
    		  
    	
    	        	}
    	        }
    	        }
    	
            else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                    "Test data row is 'TestDataForACMO_296' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

        }
        	}catch(Exception e){
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
          }



          }

    public static void tcAccountMaintenanceACMO297() throws Exception {
    	try{
    		String accountNum=dataObj.get("AccountNumber");
           
           if(accountNum.length()>1)
           {
               CommonLibrary.searchForAccount(accountNum);
               //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
               FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
               FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
              		                                         accountInfoDevicesTab,accountInfoTab,5,20);
               
          		//to add a new vehicle, a device must be added before to it.    
       			addDeviceAndReturnDeviceId();
       	    
               //add vehicle now                                   
               String []plateNumsAdded=addVehicleAndReturnPlateNums();
                         
               //do payment  now                                         
               makePayment(dataObj.get("Rebill Pay Type"));  
                        
               FunctionLibrary.clickObject(accountsInfoTab, "", "Clickk Account Info Link");
               FunctionLibrary.clickObject(vehiclesTab, "", "Click vehicles link");
               
               verifyVehicleUnderVehicleHistory(plateNumsAdded[0]);
               
           }
           else
           {
               ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
                       "Test data row is 'TestDataForACMO_107' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);

           }
           
       	}catch(Exception e){
               ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
         }

    }
  
    public static void makePayment(String paymentType) 
    
    {
    	try{
    	
        String Desc;
        Desc = "Clicking Financials tab";
        FunctionLibrary.clickObject(xpathFinancialTab, "", Desc);
        
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
        ("(//button[@title='Open Items List:Go'])[2]",xpathFinancialTab,8, 30);

        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
        FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated
        		(By.xpath(paymentListNewBtn)));

        FunctionLibrary.scrollDowntoElement(paymentListNewBtn, "Scroll down");
        Desc = "Clicking on new button";
        FunctionLibrary.clickObject(paymentListNewBtn, "", Desc);////button[@title='Payments List:New']

        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
        
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathPaymentType, paymentListNewBtn, 5, 20);
        
       Select element= new Select(FunctionLibrary.getElement(xpathPaymentType));
       String defaultSelectedPayType = element.getFirstSelectedOption().getText();
        
       
       
        FunctionLibrary.selectDropDownListByValue(xpathPaymentType, paymentType, "Selecting cash");
        if(paymentType.equalsIgnoreCase("CHECK"))
        {
        	String CheckNo=Integer.toString(FunctionLibrary.randomNumberWithFiveDigit());
            Desc="Enter Check number";
            FunctionLibrary.setText(checkPay, CheckNo, Desc);
        }
        if(paymentType.equalsIgnoreCase("SAVINGS")|| paymentType.equalsIgnoreCase("CHECKINGS"))
        {
        	String SavingNumber=dataObj.get("ACHBankRouting");
            String RoutingNumber=dataObj.get("ACHBankNumber");
            FunctionLibrary.setText(paymentDetailsBankAccountNbrTxtBox,SavingNumber,"Enter banking number");
            FunctionLibrary.setText(paymentDetailsRoutingNbrTxtBox, RoutingNumber, "Enter routing number");
        }
        
        if(paymentType.equalsIgnoreCase("VISA")|| paymentType.equalsIgnoreCase("MASTERCARD") || paymentType.equalsIgnoreCase("DISCOVER")||paymentType.equalsIgnoreCase("AMEX"))
	        {
	        	
	            String CreditCard= dataObj.get("CreditCardNo");
	            String ExpMonth=dataObj.get("ExpMonth");
	            String ExpYear=dataObj.get("ExpYear");
	
	          
	            if (!defaultSelectedPayType.equalsIgnoreCase(paymentType)) {
	            	 Desc="Enter CC Details";
	   	          
	   	          //  FunctionLibrary.setText("//*[@id='Amount']", amount, "Set amount");
	   	            FunctionLibrary.setText(creditCardNoField,"","Enter credit card number");
	   	            System.out.println(CreditCard);
	   	            FunctionLibrary.setText(creditCardNoField,CreditCard,"Enter credit card number");
	   	            Desc="Selecting Expiry Month";
	   	           
	   	            try {
	   					FunctionLibrary.selectDropDownList(creditCardExpMpnth,ExpMonth,Desc);
	   				} catch (Exception e) {
	   					// TODO Auto-generated catch block
	   					e.printStackTrace();
	   				}
	   	          
	   	
	   	            Desc="Selecting Expiry Year";
	   	            
	   	                FunctionLibrary.selectDropDownList(creditCardExpYear,ExpYear,Desc);
	   		
				}
	           
	        }
        FunctionLibrary.scrollDowntoElement("//*[@id='Amount']", "Scroll down to save button");
        Desc = "clicking on save button";
        FunctionLibrary.clickObject(saveRebillInfo, "", "Click save button");
        try{
            Alert alert = FunctionLibrary.objDriver.switchTo().alert();
            System.out.println("Popup is displayed with text" + alert.getText());
            alert.accept();
        }catch(Exception e)
        {
        	System.out.println("No alert is present");
        }
        FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@title='"+paymentType+"']")));
        
        try{
        	
            Alert alert = FunctionLibrary.objDriver.switchTo().alert();
            System.out.println("Popup is displayed with text" + alert.getText());
            alert.accept();
        }catch(Exception e)
        {
        	System.out.println("No alert is present");
        }
        FunctionLibrary.clickObject(paymentListSaveBtn,"","Click payment save button");
        FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(processPayBtn)));
        FunctionLibrary.clickObject(processPayBtn,"","Click process pay button");
      
        String alertText;
        try{
        
        WebDriverWait wait  = new WebDriverWait(FunctionLibrary.objDriver, 60);
        wait.until(ExpectedConditions.alertIsPresent());

        Alert alert = FunctionLibrary.objDriver.switchTo().alert();
        System.out.println("Popup is displayed with text" + alert.getText());
        alertText= alert.getText();
        alert.accept();
        if(!alertText.contains("Transaction Processed"))
        {
        	try{
        		wait.until(ExpectedConditions.alertIsPresent());
	        	Alert alert1 = FunctionLibrary.objDriver.switchTo().alert();
	            System.out.println("Popup is displayed with text" + alert.getText());
	            alertText = alert1.getText();
	            alert1.accept();
	            System.out.println(alertText);
	            if(alertText.contains("Transaction Processed"))
	            {
	            	//get payment reference number
	                String[] paymentRefNumber = alertText.split(":");
	                System.out.println(paymentRefNumber[1]);	
	                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Payment is proceessed and reference number is: "+ paymentRefNumber[1], 
	                		LogStatus.PASS, true);
	            }
	            else
	            {
	            	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Payment is failed. reference number alert is not displaying", 
	                		LogStatus.FAIL, true);
	            }
	            
        	}catch(Exception e)
        	{
        		
        	}
        }
        else
        {           
            	//get payment reference number
                String[] paymentRefNumber = alertText.split(":");
                System.out.println(paymentRefNumber[1]);	
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Payment is proceessed and reference number is: "+ paymentRefNumber[1], 
                		LogStatus.PASS, true);
        
        }
              
        
        }
        catch(Exception e)
        {
        	
        }

    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }
    }
    
    public static void makePayment(String paymentType, boolean isWithWrongCardInfo) 
    {
        String inValidCreditCardNum = "" ;
        String validCreditCardNum="";

    	try{
        String Desc;
        Desc = "Clicking Financials tab";
        FunctionLibrary.clickObject(xpathFinancialTab, "", Desc);
        
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(compositePaymentGoBtn,xpathFinancialTab, 
          		 4, 30);

       // new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();

        FunctionLibrary.scrollDowntoElement(paymentListNewBtn, "Scroll down to new button");
        Desc = "Clicking on new button";
        FunctionLibrary.clickObject(paymentListNewBtn, "", Desc);////button[@title='Payments List:New']

       // new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
        
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathPaymentType, paymentListNewBtn, 4, 27);
        FunctionLibrary.selectDropDownListByValue(xpathPaymentType, paymentType, "Selecting paytype");
     
        String ExpMonth;
        String ExpYear;
        	
            //String CreditCard= dataObj.get("CreditCardNo");
             ExpMonth=dataObj.get("ExpMonth");
             ExpYear=dataObj.get("ExpYear");
            
            inValidCreditCardNum = "1234123412341234";
            validCreditCardNum = dataObj.get("CreditCardNo");

            Desc="Enter CC Details";
            clearTextField(FunctionLibrary.objDriver.findElement(By.xpath(creditCardNoField)));            
            FunctionLibrary.setText(creditCardNoField,inValidCreditCardNum,"Enter credit card number");
            Desc="Selecting Expiry Month";
           
            try {
				FunctionLibrary.selectDropDownList(creditCardExpMpnth,ExpMonth,Desc);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
          
            Desc="Selecting Expiry Year";            
            FunctionLibrary.selectDropDownList(creditCardExpYear,ExpYear,Desc);

        //ACMO-285
        FunctionLibrary.scrollDowntoElement(saveRebillInfo, "");
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"<b>ACMO-285: Payment for the transponder by entering invalid card number</b>"
        						+ "<br>Saving replenishment info wiht invalide card number"+ inValidCreditCardNum, LogStatus.INFO, false);
        
        Desc = "clicking on save button";
        FunctionLibrary.clickObject(saveRebillInfo, "", "Click save button");
        verifypaymentFailReasonAlert("Card Validation");
        clearTextField(FunctionLibrary.objDriver.findElement(By.xpath("//input[@id='CCField']")));
        FunctionLibrary.selectDropDownListByValue(xpathPaymentType, "CASH", "Selecting cash");
        FunctionLibrary.selectDropDownListByValue(xpathPaymentType, paymentType, "Selecting payment type");
        FunctionLibrary.setText("//input[@id='CCField']", validCreditCardNum,"Enter credit card number");
        
        
        
        //ACMO-286
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"<b>ACMO-286: Payment for the transponder by entering expiration date which is past</b>"
				+ "<br>Testing with previous year", LogStatus.INFO, false);

        FunctionLibrary.scrollDowntoElement(saveRebillInfo, "");
        String previousYear = String.valueOf(DateTime.now().getYear()-1);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Verifying previous years are not available in the credit card expire year field ("+previousYear+")", LogStatus.INFO, true);
        FunctionLibrary.verifyDropDownListOptionsNotExist(creditCardExpYear, previousYear,"Verify previous year is not dispalying");
        try {
   				FunctionLibrary.selectDropDownList(creditCardExpMpnth,ExpMonth,Desc);
   			} catch (Exception e) {
   				// TODO Auto-generated catch block
   				e.printStackTrace();
   			}
             
               Desc="Selecting Expiry Year";            
               FunctionLibrary.selectDropDownList(creditCardExpYear,ExpYear,Desc);

        
        //ACMO-290

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"<b>ACMO-290: Payment for the transponder while billing address is blank</b>"
				+ "<br>Testing by remove street address", LogStatus.INFO, false);
        FunctionLibrary.scrollDowntoElement(saveRebillInfo, "");
        String streetAdderss = FunctionLibrary.getText(xpathAddressPayment, "Get the street address"); 
        clearTextField(FunctionLibrary.objDriver.findElement(By.xpath(xpathAddressPayment)));
        
        //FunctionLibrary.setText(xpathAddressPayment, "", "Remove street address");  
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Verfying with street address with no value", LogStatus.INFO, true);
        FunctionLibrary.clickObject(saveRebillInfo, "", "Click save button");
        verifypaymentFailReasonAlert("Please enter the Street Address of the Mailing Address of the Card Holder");
        FunctionLibrary.setText(xpathAddressPayment, streetAdderss, "Re-enter the correct street address"); 
        
      //ACMO-288
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"<b>ACMO-288: Payment for the transponder by entering invalid name on the card</b>"
				+ "<br>Testing by new value in first name field", LogStatus.INFO, false);
        FunctionLibrary.scrollDowntoElement(saveRebillInfo, "");
        String fnameOfCardHolder = FunctionLibrary.getText(xpathFirstNamePayment, "Get the fristname of the card holder");        
        FunctionLibrary.setText(xpathFirstNamePayment, "1234", "Enter in-correct name in first name field");  
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Verfying with invalid fristname", LogStatus.INFO, true);
        FunctionLibrary.clickObject(saveRebillInfo, "", "Click save button");
        verifypaymentFailReasonAlert("Invalid Character");
        FunctionLibrary.clickObject(xpathFirstNamePayment, "", "Click first name field");
        clearTextField(FunctionLibrary.objDriver.findElement(By.xpath(xpathFirstNamePayment)));
        FunctionLibrary.setText(xpathFirstNamePayment, fnameOfCardHolder, "Enter correct name in first name field");
       
        //ACMO-289

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"<b>ACMO-289: Payment for the transponder by entering invalid ZIP Code</b>"
				+ "<br>Testing by wrong zipcode", LogStatus.INFO, false);
        FunctionLibrary.scrollDowntoElement(saveRebillInfo, "");
        FunctionLibrary.setText(xpathZipcodePayment, "ABCD", "Enter in-correct zipcode"); 
        FunctionLibrary.setText(countryTxtBox, "USA", "Enter country");
        FunctionLibrary.setText(stateTxtBox, "FL", "Enter country");        
        FunctionLibrary.setText(xpathCityPayment, "FLORIDA", "Enter country");
          
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Verfying with invalid zipcode", LogStatus.INFO, true);
        FunctionLibrary.clickObject(saveRebillInfo, "", "Click save button");
        verifypaymentFailReasonAlert("Card Validation");
        
      FunctionLibrary.closeAllActiveBrowser();
            	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }
    }
    
    public static String[] addVehicleAndReturnPlateNums()

    
    {
    	try{

    //	FunctionLibrary.clickObject(accountInfoTab, "", "Click account info tab");
        String Desc = "Clicking vehicles tab";
        FunctionLibrary.clickObject(vehiclesTab,"",Desc);
        String vehiclesToBeAdded = dataObj.get("VehiclesInfo");
        vehiclesToBeAdded = vehiclesToBeAdded.replace("\n","");

        String []vehiclesItems = vehiclesToBeAdded.split(",");
        String [] eachVehicleInfo;
        System.out.println(vehiclesItems.length);
        String []plateNumber=new String[vehiclesItems.length];
        String plateState;
        String plateType;
        String plateCountry;
        String vehicleType;
        String year;
        String make;
        String model;
    	String noOfVechiclesBeforeToAddVehcile = "";
    	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "No of vehicles are going to be added: " + vehiclesItems.length, LogStatus.INFO, false);
    	noOfVechiclesBeforeToAddVehcile = FunctionLibrary.getText(vehicleCountTxtBox,"Get the no of vechicles");
        for (int iterator1 =0;iterator1<=vehiclesItems.length-1;iterator1++) {

            String pp = CommonLibrary.randomIdentifier();
            String RandomChar=pp.substring(0,2);
            eachVehicleInfo = vehiclesItems[iterator1].split(":");
           
                	plateNumber[iterator1]=eachVehicleInfo[0];
            
            if(dataObj.get("TestCaseId").contains("175"))
            {
            	//plateNumber[iterator1]=getVehicleNumberForClosedAccount();
            	String sqlToGetPlateNumOfClosedAccount = "select PLATE_NUMBER,account_no from (" + 
            			" select DISTINCT(V.PLATE_NUMBER), rownum as n_th_row,account_no from siebel.v_vehicle v, siebel.v_etc_account a where a.account_status_cd='CLOSED') " + 
            			" where N_TH_ROW = "+(int )(Math.random() * 5000 + 1);
            	HashMap <String, String> palateNumWithClosedAccNum = CommonLibrary.executeSqlAndGetOutputAsHashMap(sqlToGetPlateNumOfClosedAccount);
            	plateNumber[iterator1] = palateNumWithClosedAccNum.get("PLATE_NUMBER");
            	String closedAccNum = palateNumWithClosedAccNum.get("ACCOUNT_NO");
            	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Getting a plate number from DB: Plate number "+plateNumber[iterator1]+" of closed account "+closedAccNum, LogStatus.INFO, false);
            }else
            {
            plateNumber[iterator1] = RandomChar+ DateTime.now().getMillisOfSecond()+DateTime.now().getSecondOfMinute()+ DateTime.now().getMillisOfSecond();
            }
            System.out.println(plateNumber[iterator1]);
            plateState = eachVehicleInfo[1];
            plateType = eachVehicleInfo[2];
            plateCountry = eachVehicleInfo[3];
            vehicleType = eachVehicleInfo[4];
            year = eachVehicleInfo[5];
            make = eachVehicleInfo[6];
            model = eachVehicleInfo[7];
           // Thread.sleep(4000);
            //Clicks on vehicles new button
            Desc="Clicking vehicles new button";
            
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(vehicleListNewBtn, 
            		vehiclesTab, 2, 20);
           FunctionLibrary.clickObject(vehicleListNewBtn,"",Desc);
            

           FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(vehiclesDetailsPlateNumberTxtBox,vehicleListNewBtn, 
           		 2, 20);
       	 
            
            //Enter PLate Number
            Desc="Entering plate number";
            
            FunctionLibrary.setText(vehiclesDetailsPlateNumberTxtBox,plateNumber[iterator1],Desc);
            
            if(plateCountry.length()>1)
            {
            	FunctionLibrary.clickObject(vehicleDetailsPlateCountryElement, "", Desc);
            	
            	if(!FunctionLibrary.objDriver.findElement(By.xpath(vehicleDetailsPlateCountryTxtBox)).getAttribute("value").contains(plateCountry))
					{
            	
						FunctionLibrary.objDriver.findElement(By.xpath(vehicleDetailsPlateCountryTxtBox)).sendKeys(plateCountry);
					}
            	
            	
            
            }
            
            //Clicks on plate state field
            Desc="Clicking plate state field";
            FunctionLibrary.clickObject(vehicleDetailsPlateStateElement,"",Desc);
            //Enter PLate Number
           
            
           // String defaultStateName = FunctionLibrary.getText(vehicleDetailsPlateStateElement,"Get state value");
            String defaultStateName = FunctionLibrary.getText(vehicleDetailsPlateStateTxtBox,"Get state value");
            if(!defaultStateName.contains(plateState)) {
                Desc = "Entering plate number";
                FunctionLibrary.objDriver.findElement(By.xpath(vehicleDetailsPlateStateTxtBox)).sendKeys(plateState);
            }

            
           
            if(plateType.length()>1)
            {
            	
            	FunctionLibrary.clickObject(vehicleDetailsPlateTypeElement, "", Desc);
            	
            	FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(vehicleDetailsPlateTypeTxtBox, 
            			vehicleDetailsPlateTypeElement, 2, 10);
            	
            	
            	if(!FunctionLibrary.objDriver.findElement(By.xpath(vehicleDetailsPlateTypeTxtBox)).getAttribute("value").contains(plateType))
					{
            		
						FunctionLibrary.objDriver.findElement(By.xpath(vehicleDetailsPlateTypeTxtBox)).sendKeys(plateType);
					}
					                        	
            
            }
            
      
            
            if(vehicleType.length()>1)
            {
            	
            	
            	FunctionLibrary.clickObject(vehicleDetailsVehicleTypeElement, "", Desc);
            
            	if(!FunctionLibrary.objDriver.findElement(By.xpath(vehicleDetailsVehicleTypeTxtBox)).getAttribute("value").contains(vehicleType))
					{
            		
						FunctionLibrary.objDriver.findElement(By.xpath(vehicleDetailsVehicleTypeTxtBox)).sendKeys(vehicleType);
					}
            	
            
            }
            
            if(year.length()>1)
            {
            	FunctionLibrary.clickObject(vehicleDetailsVehicleYearElement, "", "");
               	if(!FunctionLibrary.objDriver.findElement(By.xpath(vehicleDetailsVehicleYearTxtBox)).getAttribute("value").contains(year))
					{
               		
						FunctionLibrary.objDriver.findElement(By.xpath(vehicleDetailsVehicleYearTxtBox)).sendKeys(year);
					}
                        	
             }
            
            if(make.length()>1)
            {
            	
            	FunctionLibrary.clickObject(vehicleDetailsVehicleMakeElement, "", "");
            	if(!FunctionLibrary.objDriver.findElement(By.xpath(vehicleDetailsVehicleMakeTxtBox)).getAttribute("value").contains(make))
				{
            	
					FunctionLibrary.objDriver.findElement(By.xpath(vehicleDetailsVehicleMakeTxtBox)).sendKeys(make);
				}
                    	
            }
            
            if(model.length()>1)
            {
            	FunctionLibrary.clickObject(vehicleDetailsVehicleModelElement, "", "");
            	if(!FunctionLibrary.objDriver.findElement(By.xpath(vehicleDetailsVehicleModelTxtBox)).getAttribute("value").contains(model))
				{
            		
					//FunctionLibrary.objDriver.findElement(By.xpath(vehicleDetailsVehicleModelTxtBox)).sendKeys(model);
					FunctionLibrary.setText(vehicleDetailsVehicleModelTxtBox, model, "Enter model");
				}
                        
            }
            
            
            if(dataObj.get("Plan").contains("RCSPP"))//RCSPPRE OR RCSPPOST
            {
                //click effective end date
                Desc="Click effective end date";
                FunctionLibrary.clickObject(vehicleEndDtElement,"",Desc);

                //Enter effective end date
                Desc="Enter end date";
                FunctionLibrary.setText(vechileEffectiveEndDateSetText,"1/30/2030 12:00:00 AM",Desc);

            }

        }
        
        //Clicking vehicle save buttton
        Desc="Clicking vehicle save buttton";
        FunctionLibrary.clickObject(vehicleListSaveBtn,"",Desc);
        
        Thread.sleep(5000);
        
        //FunctionLibrary.waitForObject.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(vehicleDetailsVehicleModelTxtBox)));
        FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(vehicleDetailsVehicleModelElement)));
  
    	String noOfVechiclesAfterToAddVehcile = FunctionLibrary.getText(vehicleCountTxtBox,"Get the no of vechicles");
    	 
    	if(Double.valueOf(noOfVechiclesAfterToAddVehcile).equals(Double.valueOf(noOfVechiclesBeforeToAddVehcile)+vehiclesItems.length))
    	{
    		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Vehicle count should be: "+(int)(Double.valueOf(noOfVechiclesBeforeToAddVehcile)+vehiclesItems.length)
					+" <br> Actual: "+noOfVechiclesAfterToAddVehcile, LogStatus.PASS,true);
    		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Vehicle is added with plate number: "+plateNumber[0], LogStatus.PASS, false);
    	}
    	else
    	{
    		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Vehicle count should be: "+(Double.valueOf(noOfVechiclesBeforeToAddVehcile)+vehiclesItems.length)
    						+" <br> Actual: "+noOfVechiclesAfterToAddVehcile, LogStatus.FAIL,true);
    	}
        
        
        return plateNumber;
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }
		return null;
		
    }

    public static String addDeviceAndReturnDeviceId()
    {
    	try{

        //to add vehicle, we need to add first device.
        String deviceName = dataObj.get("Deviceinfo");
        FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(addressTab)));

                FunctionLibrary.clickObject(xpathDeviceTab, "", "clicking Devices tab");
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(assignDevicesListGoBtn)));
                FunctionLibrary.clickObject(xpathDeviceRequestTab, "", "clicking on Device Request");
                FunctionLibrary.clickObject(xpathDeviceNewButton, "", "Clicking Device Requests new button");

                deviceName=deviceName.split(":")[0];
                FunctionLibrary.setText(xpathDeviceName, deviceName.toString(), "Adding card");

                FunctionLibrary.clickObject(deviceDetailsQuantityElement, "", "Click details quantity");

                FunctionLibrary.setText(deviceDetailsQuantityTxtBox, dataObj.get("Deviceinfo").split(":")[1], "Enter quantity");
                
                

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                        "Adding device quantity: "+ dataObj.get("Deviceinfo").split(":")[1] , LogStatus.INFO, true);

                String deviceId = FunctionLibrary.getWebTableCellData(xpathDeviceTable, 1, 5, "Getting cell value");

                System.out.println(deviceId);


                FunctionLibrary.clickObject(xpathDeviceSave, "", "Clicking Device Requests Save button");

                new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_UP)).perform();
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Devices is added with request number: "+deviceId, LogStatus.PASS, true);
                return deviceId;
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }
		return null;

    }
    
    public static void verifyVehicleUnderVehicleHistory(String vehicleNum)
    {
    	try{
        FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(vehicleListNewBtn)));
        FunctionLibrary.clickObject(""+xpathVehicleHistoryTab,"","Click history tab");

        
        FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(statusElement)));
        FunctionLibrary.setText(queryTxtBox,"Plate Number","Enter Plate Number in search drop down");
        FunctionLibrary.setText(queryValTxtBox,vehicleNum,"Enter Plate Number value");
        FunctionLibrary.clickObject(goSpanBtn,"","Click Go button");
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                "Searching for added plate number in vehicle history tab", LogStatus.INFO, false);
     
        try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        int rowNumberWithPlateNumber = 1;
        System.out.println(rowNumberWithPlateNumber);
        FunctionLibrary.verifyWebTableCellData(xpathPlateTable, rowNumberWithPlateNumber, 1, "ADD",
                "Verifying Status",true);
        FunctionLibrary.verifyWebTableCellData(xpathPlateTable, rowNumberWithPlateNumber, 4, vehicleNum,
                "Verifying plate number",false);
       
  	  String sql1 = "select * from siebel.V_vehicle where plate_number = '"+vehicleNum+"'";
      if(!(CommonLibrary.executeSqlAndGetOutputAsHashMap(sql1).get("PLATE_NUMBER")==null))
		{
			ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Epected: Newly added vehicle's number "+vehicleNum+" should be in database <br> Actual: Vehicle number is present",LogStatus.PASS,false);
		}
      else
      {
			ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Epected: Newly added vehicle's number "+vehicleNum+" should be in database <br> Actual: Vehicle number is not present",LogStatus.FAIL,false);
      }

        
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }

    	
    	
    }

    //paymentModesAndInfo: Example of Syntax"Cash_20;VISA_1223445353455_12_2024_20;MASTERCARD_1234234234234_12_2028_10;SAVINGS_123423423_01234234_10"
    //rebilltype_cardnumber_month_year_amount
    public static String makePaymentWithMultiModes(String paymentModesInfo) 
        
    {
    
    	String [] paymentModesAndInfo = paymentModesInfo.split(";");
    	String paymentType;
    	String amount = "" ;
    	String paymentProcessId="";
    	try{
    	
        String Desc;
        Desc = "Clicking Financials tab";
        FunctionLibrary.clickObject(xpathFinancialTab, "", Desc);
        
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
        (compositePaymentGoBtn,xpathFinancialTab,12, 36);
               
        for(int iterator=0;iterator<=paymentModesAndInfo.length-1;iterator++)
    	{    		
	            
        	paymentType = paymentModesAndInfo[iterator].split("_")[0];
        	FunctionLibrary.scrollDowntoElement(paymentListNewBtn, "scroll down to save button");
	        Desc = "Clicking on new button";
	        FunctionLibrary.clickObject(paymentListNewBtn, "", Desc);
	        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathPaymentType, paymentListNewBtn, 6, 30);
	        FunctionLibrary.scrollDowntoElement(xpathPaymentType, "scroll down to payment type field");
	        FunctionLibrary.selectDropDownListByValue(xpathPaymentType, paymentType, "Selecting cash");
	        
	        if(paymentType.equalsIgnoreCase("CASH"))
	        {
	        	amount =paymentModesAndInfo[iterator].split("_")[1];
	            FunctionLibrary.setText(amountForPayment, amount, Desc);
	        }
	    
	        if(paymentType.equalsIgnoreCase("CHECK"))
	        {	
	        	String checkNum = paymentModesAndInfo[iterator].split("_")[1];
	        	
	        	//String CheckNo=Integer.toString(FunctionLibrary.randomNumberWithFiveDigit());
	            amount =paymentModesAndInfo[iterator].split("_")[2];
	            FunctionLibrary.setText(amountForPayment, amount, Desc);
	            Desc="Enter Check number";
	            FunctionLibrary.setText(checkPay, checkNum, Desc);
	
	        }
	        if(paymentType.equalsIgnoreCase("SAVINGS")|| paymentType.equalsIgnoreCase("CHECKINGS"))
	        {
	        	String SavingNumber=paymentModesAndInfo[iterator].split("_")[1];
	            String RoutingNumber=paymentModesAndInfo[iterator].split("_")[2];

	            amount =paymentModesAndInfo[iterator].split("_")[3];
	            FunctionLibrary.setText(amountForPayment, amount, Desc);
	            
	            FunctionLibrary.setText(paymentDetailsBankAccountNbrTxtBox,SavingNumber,"Enter banking number");
	            FunctionLibrary.setText(paymentDetailsRoutingNbrTxtBox, RoutingNumber, "Enter routing number");
	            
	        }
	        if(paymentType.equalsIgnoreCase("VISA")|| paymentType.equalsIgnoreCase("MASTERCARD") || paymentType.equalsIgnoreCase("DISCOVER")||paymentType.equalsIgnoreCase("AMEX"))
	        {
	            FunctionLibrary.selectDropDownListByValue(xpathPaymentType, "CASH", "Selecting cash to clear card number");
	            FunctionLibrary.selectDropDownListByValue(xpathPaymentType, paymentType, "Selecting cash");
	        	        	
	            String CreditCard= paymentModesAndInfo[iterator].split("_")[1];
	            String ExpMonth=paymentModesAndInfo[iterator].split("_")[2];
	            String ExpYear=paymentModesAndInfo[iterator].split("_")[3];
	
	            Desc="Enter CC Details";
	
	            System.out.println(CreditCard);
	            amount =paymentModesAndInfo[iterator].split("_")[4];
	            FunctionLibrary.setText(amountForPayment, amount, Desc);
	            //FunctionLibrary.setText(creditCardNoField," ","Enter credit card number ");
	            FunctionLibrary.setText(creditCardNoField,CreditCard,"Enter credit card number");
	            Desc="Selecting Expiry Months";
	           
	            try {
					FunctionLibrary.selectDropDownList(creditCardExpMpnth,ExpMonth,Desc);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	          
	
	            Desc="Selecting Expiry Year";
	            
	                FunctionLibrary.selectDropDownList(creditCardExpYear,ExpYear,Desc);
	
	        }
	        	
	        
	        Desc = "clicking on save button";
	        FunctionLibrary.clickObject(saveRebillInfo, "", "Click save button");
	        try{
	            Alert alert = FunctionLibrary.objDriver.switchTo().alert();
	            System.out.println("Popup is displayed with text" + alert.getText());
	            alert.accept();
	        }catch(Exception e)
	        {
	        	System.out.println("No alert is present");
	        }
	        
	        FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@title='"+paymentType.toUpperCase()+"']")));
            
	   
	        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Making payment for replenishment type "+ paymentType +", amount "+amount, LogStatus.INFO, true);
    	}
        
        
        
        try{
        	   WebDriverWait wait  = new WebDriverWait(FunctionLibrary.objDriver, 5);
               wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = FunctionLibrary.objDriver.switchTo().alert();
            System.out.println("Popup is displayed with text" + alert.getText());
            alert.accept();
        }catch(Exception e)
        {
        	System.out.println("No alert is present");
        }
        FunctionLibrary.clickObject(paymentListSaveBtn,"","Click payment save button");
        FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(processPayBtn)));
        FunctionLibrary.clickObject(processPayBtn,"","Click process pay button");
      
        String alertText;
        try{
        
        WebDriverWait wait  = new WebDriverWait(FunctionLibrary.objDriver, 60);
        wait.until(ExpectedConditions.alertIsPresent());

        Alert alert = FunctionLibrary.objDriver.switchTo().alert();
        System.out.println("Popup is displayed with text" + alert.getText());
        alertText= alert.getText();
        alert.accept();
        if(!alertText.contains("Transaction Processed"))
        {
        	try{
        		wait.until(ExpectedConditions.alertIsPresent());
	        	Alert alert1 = FunctionLibrary.objDriver.switchTo().alert();
	            System.out.println("Popup is displayed with text" + alert.getText());
	            alertText = alert1.getText();
	            alert1.accept();
	            System.out.println(alertText);
	            if(alertText.contains("Transaction Processed"))
	            {
	            	//get payment reference number
	                String[] paymentRefNumber = alertText.split(":");
	                paymentProcessId = paymentRefNumber[1];
	                System.out.println(paymentProcessId);	
	                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Payment is proceessed and reference number is: "+ paymentProcessId, 
	                		LogStatus.PASS, true);
	            }
	            else
	            {
	            	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Payment is failed. reference number alert is not displaying", 
	                		LogStatus.FAIL, true);
	            }
	            
        	}catch(Exception e)
        	{
        		
        	}
        }
        else
        {           
            	//get payment reference number
                String[] paymentRefNumber = alertText.split(":");
                paymentProcessId = paymentRefNumber[1];
                System.out.println(paymentProcessId);	
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Payment is proceessed and reference number is: "+ paymentProcessId, 
                		LogStatus.PASS, true);
        
        }
              
        
        }
        catch(Exception e)
        {
        	
        }

    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }
		return paymentProcessId;
    }

    public static void verifypaymentFailReasonAlert(String alerWithText)
    {
    	
        String alertText;
        try{
        
	        FunctionLibrary.waitForObject.until(ExpectedConditions.alertIsPresent());
	
	        Alert alert = FunctionLibrary.objDriver.switchTo().alert();
	        System.out.println("Popup is displayed with text" + alert.getText());
	        alertText= alert.getText();
	        alert.accept();
	        if(alertText.contains(alerWithText))
	        {
	        	
	                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: popup should be displayed.<br> Actual: Popup dispalying wiht text: "+alertText, 
	                		LogStatus.PASS, true);
		          	         
	        }
	        else
	        {           
	        	  ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: popup should be displayed. <br> Actual: pop is not displyed.", 
	              		LogStatus.FAIL, true);
	        
	        }
              
        
        }
        catch(Exception e)
        {
        	
        }
        }

	public static void clearTextField(WebElement element)
	{
		 element.click();
		 Actions action = new Actions(FunctionLibrary.objDriver);
		 action.doubleClick(element).perform();
	     element.clear();     
	     
	}
	
	public static void verifyNonFinacialHistory(String category, String subcategory)
	{
		FunctionLibrary.clickObject(historyTab, "", "Click history link");
	    
	    FunctionLibrary.clickObject(nonFinancialsHistroy,"","Click Non Financial under history");
	    FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOf(FunctionLibrary.getElement(nonFinancialHistroyTable)));
	    
	    int rowNumberWithAmount = FunctionLibrary.getRowNumberFromWebTable(nonFinancialHistroyTable,category, "get the row number");
	    
	    FunctionLibrary.verifyWebTableCellData(nonFinancialHistroyTable, rowNumberWithAmount, 3,
	    		category, "<b>NonFinacial History Table:</b> <br>Verifying category", true);
	    FunctionLibrary.verifyWebTableCellData(nonFinancialHistroyTable,rowNumberWithAmount,4,
	    		subcategory,"Verifying sub category", false);
	  	
	}
	
	public static void verifyNonFinacialHistoryRefIsSubCategory(String category, String subcategory, String description)
	{
		FunctionLibrary.clickObject(historyTab, "", "Click history link");
	    
	    FunctionLibrary.clickObject(nonFinancialsHistroy,"","Click Non Financial under history");
	    FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOf(FunctionLibrary.getElement(nonFinancialHistroyTable)));
	    
	    int rowNumberWithAmount = FunctionLibrary.getRowNumberFromWebTable(nonFinancialHistroyTable,subcategory, "get the row number");
	    
	    FunctionLibrary.verifyWebTableCellData(nonFinancialHistroyTable, rowNumberWithAmount, 3,
	    		category, "<b>NonFinacial History Table:</b> <br>Verifying category", true);
	    FunctionLibrary.verifyWebTableCellData(nonFinancialHistroyTable,rowNumberWithAmount,4,
	    		subcategory,"Verifying sub category", false);
	    FunctionLibrary.verifyWebTableCellData(nonFinancialHistroyTable,rowNumberWithAmount,5,
	    		description,"Verifying description", false);
	  	
	}
	
	public static void verifyNonFinacialHistoryRefDescription(String category, String subcategory, String description)
	{
		FunctionLibrary.clickObject(historyTab, "", "Click history link");
	    
	    FunctionLibrary.clickObject(nonFinancialsHistroy,"","Click Non Financial under history");
	    FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOf(FunctionLibrary.getElement(nonFinancialHistroyTable)));
	    
	    int rowNumberWithAmount = FunctionLibrary.getRowNumberFromWebTable(nonFinancialHistroyTable,description, "get the row number");
	    
	    FunctionLibrary.verifyWebTableCellData(nonFinancialHistroyTable, rowNumberWithAmount, 3,
	    		category, "<b>NonFinacial History Table:</b> <br>Verifying category", true);
	    FunctionLibrary.verifyWebTableCellData(nonFinancialHistroyTable,rowNumberWithAmount,4,
	    		subcategory,"Verifying sub category", false);
	    FunctionLibrary.verifyWebTableCellData(nonFinancialHistroyTable,rowNumberWithAmount,5,
	    		description,"Verifying description", false);
	  	
	}
		
	public static void verifyNotesByRefOfDescription(String notesDescription,  String notesSubType, String nonFinDescription)
	{
	    FunctionLibrary.objDriver.findElement(By.xpath(notesTab)).click();
	    FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(notesListNewBtn)));
	    
	    
	    int rowNumber = FunctionLibrary.getRowNumberFromWebTable(notesTable,notesDescription, "get the row number");
	    
	    FunctionLibrary.verifyWebTableCellData(notesTable, rowNumber, 3,
	    		notesSubType, "<b>Notes:</b> <br>Verifying sub type", true);
	    FunctionLibrary.verifyWebTableCellData(notesTable,rowNumber,5,
	    		notesDescription,"Verifying sub category", false);
	    FunctionLibrary.verifyWebTableCellData(notesTable,rowNumber,6,
	    		nonFinDescription,"Verifying description", false);}
	
	public static void verifyChangedAddressPresentInDbOrNot(String addressLine1Text)
	{
		String sql = "select ADDR_LINE1 from siebel.cx_addorg_hst where version>0  order by row_id desc";
	    
	    if(CommonLibrary.executeSqlAndGetOutputAsHashMap(sql).get("ADDR_LINE1").contains(addressLine1Text.toUpperCase()))
		{
			ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Epected: Newly updated address "+addressLine1Text+" should be in database <br> Actual: Address is present",LogStatus.PASS,false);
		}
	    else
	    {
	    	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Epected: Newly updated address "+addressLine1Text+" should be in database <br> Actual: Address is present",LogStatus.PASS,false);
	    }
	}
	
	public static void verifySalesTaxAndTagIntAmountAfterPayment(String noOfDevices)
	{
	    
	    try {
			FunctionLibrary.scrollDowntoElement(financialTrnsactionTable, "Scroll down to financial transaction table");
		} catch (InterruptedException e){
			e.printStackTrace();
		}
	    String financialTransactionDetailTbl = "//table[@summary='Financial Transaction Details List']";
	    int rowNum1 = FunctionLibrary.getRowNumberFromWebTableByTwoText(financialTransactionDetailTbl, "TAGSALE", "SALESTAX", "Get the row number");
	    int rowNum2 = FunctionLibrary.getRowNumberFromWebTableByTwoText(financialTransactionDetailTbl, "TAGSALE", "TAGINT", "Get the row number");
	    
	    String amountSalesTax = FunctionLibrary.getWebTableCellData(financialTransactionDetailTbl,rowNum1,4,"Get the amount of sales tax");
	    amountSalesTax = amountSalesTax.replace("$","").replace(",","");
	    String amountTagInt = FunctionLibrary.getWebTableCellData(financialTransactionDetailTbl,rowNum2,4,"Get the amount of tag int");
	    amountTagInt = amountTagInt.replace("$","").replace(",","");
	    String noOfDevicesAdded = noOfDevices;
	    Double eachDevicePrice = 10.00;
	    Double salesTaxPercentage=0.4;
	    Double expectedSalesTax = salesTaxPercentage*Double.valueOf(noOfDevicesAdded);
	    Double expectedTagIntAmount = eachDevicePrice*Double.valueOf(noOfDevicesAdded);
	
	    if(expectedSalesTax.equals(Double.valueOf(amountSalesTax)))
	    {
	   	 ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected sales tax: "+expectedSalesTax +". Actual: "+amountSalesTax, LogStatus.PASS, true);
	    }else
	    {
	   	 ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected sales tax: "+expectedSalesTax +". Actual: "+amountSalesTax, LogStatus.FAIL, true);
	    }
	
	    if(expectedTagIntAmount.equals(Double.valueOf(amountTagInt)))
	    {
	   	 ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected TAG INT amount: "+expectedTagIntAmount +". Actual: "+amountTagInt, LogStatus.PASS, true);
	    }else
	    {
	   	 ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected TAG INT amount: "+expectedTagIntAmount +". Actual: "+amountTagInt, LogStatus.FAIL, true);
	    }
	}
	
	public static void tcAccountMaintenanceACMO281() {
		try{
	
			String accountNum=dataObj.get("AccountNumber");
		 
	
	    if(accountNum.length()>1)
	    	
	    {
	    	
	    	CamsPaymentProcessing.makefinancialStatusAsLow(accountNum);
	        CommonLibrary.searchForAccount(accountNum);
	        //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
	        FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
	        
	   		//to add a new vehicle, a device must be added before to it.    
			addDeviceAndReturnDeviceId();
		    
	        //add vehicle now                                   
	        String []plateNumsAdded=addVehicleAndReturnPlateNums();
	                  
	        //do payment  now                                         
	        makePayment(dataObj.get("Rebill Pay Type"));  
	                 
	        FunctionLibrary.clickObject(accountsInfoTab, "", "Clickk Account Info Link");
	        FunctionLibrary.clickObject(vehiclesTab, "", "Click vehicles link");
	        
	        verifyVehicleUnderVehicleHistory(plateNumsAdded[0]);
	
	    }
	           else
	    {
	        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Input account number is blank. Please execute test data generation script before to run this test case. " +
	                "Test data row is 'TestDataForACMO_281' in test data file ' FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests'" +accountNum,LogStatus.FAIL,false);
	
	    }
	    
		}catch(Exception e){
	        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
	  }
	
	
	}
	
	public static void tcAccountMaintenanceACMO201() throws InterruptedException {
		
		
			
			String transferFromAccount = dataObj.get("AccountNumber");
			String transferToAccount = dataObj.get("Trasnfer To AccountNumber");
			
			ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Trafer from account " + transferFromAccount + ". Transfer to account "+transferToAccount,
					LogStatus.INFO, false);
	        CommonLibrary.searchForAccount(transferFromAccount);
	
	        //Thread.sleep(8000);
	        FunctionLibrary.objDriver.findElement(By.linkText(transferFromAccount)).click();
	        String Desc="";
	        //HashMap<String,String> eachTestCaseData =new HashMap();
	        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	                deviceTab,accountInfoTab,6,20
	        );
	
	        Desc="Clicking Devices tab";
	        FunctionLibrary.clickObject(deviceTab,"",Desc);
	        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(deviceNumberElement, 
	        		deviceTab, 20, 40);
	        String deviceNumber;
	        //Checking the status of the device before status change
		      FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(deviceNumberElement)));
	      
		      DimsDeviceStatusChange.searchForDevice("Status", "ACTIVE");
		      
		      FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@id='1_s_2_l_Device_Status' and @title='ACTIVE']")));

	            if(FunctionLibrary.objDriver.findElement(By.xpath(statusColumn)).getText().equals("ACTIVE")) {
	            	
	                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Device Status is in ACTIVE",LogStatus.INFO,true);
	                Desc = "clicking on status";
	                FunctionLibrary.clickObject(statusColumn, "", Desc);
	                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(statusTextbox,statusColumn, 
	    	        		 20, 40);
	                FunctionLibrary.objDriver.findElement(By.xpath(statusTextbox)).clear();
	
	                Desc = "Enter status field";
	                FunctionLibrary.setText(statusTextbox, "CUSTRETAINED", Desc);
	
	                deviceNumber=FunctionLibrary.objDriver.findElement(By.xpath(deviceNumberElement)).getText();
	
	                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Moving Transponder"+ deviceNumber+ " from account number:"+transferFromAccount,LogStatus.INFO,true);
	
	
	                Desc = "Clicking Save button";
	                FunctionLibrary.clickObject(assignedSaveBtn, "", Desc);
	                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(accountHOmeTab)));
	
	                FunctionLibrary.clickObject(accountHOmeTab,"","Clicking on home tab");
	                CommonLibrary.searchForAccount(transferToAccount);
	                String noOfDevicesBeforeTransferInTransferToAccount = FunctionLibrary.getText(deviceCountTxtBox, "Get the no of devices in transfer to account");
	                
	                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "No of devices before transfer to the the account "+transferToAccount +": "+
	                		noOfDevicesBeforeTransferInTransferToAccount, LogStatus.INFO, true);
	                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText(transferToAccount)));
	
	                FunctionLibrary.objDriver.findElement(By.linkText(transferToAccount)).click();
	
	                Desc = "Clicking Devices tab";
	                FunctionLibrary.clickObject(deviceTab, "", Desc);
	
	
	                Desc = "Clicking Device Request tab";
	                FunctionLibrary.clickObject(deviceRequestTab, "", Desc);
	                Thread.sleep(5000);
	
	
	                Desc = "Clicking Device Requests new button";
	                FunctionLibrary.clickObject(deviceDetailsNewListBtn, "", Desc);
	
	                Desc = "Enter Device description";
	                FunctionLibrary.setText(deviceDescTextbox, "TRX", Desc);	
	
	                Desc = "Clicking on pre owned device";
	                FunctionLibrary.clickObject(preOwnedDevice, "", Desc);
	                //Entering quantity of tags
	                Desc = "Entering pre owned device";
	                FunctionLibrary.setText(preOwnedDevicetxtbox, deviceNumber, Desc);
	                //Clicking Device Requests Save button
	                Desc = "Clicking Device Requests Save button";
	                FunctionLibrary.clickObject(tranferReasonCode, "", Desc);
	                Desc = "Entering transfer reason code";
	                FunctionLibrary.setText(tranferReasonCodetxtbox, "TAG TRANSFERRED", Desc);
	                FunctionLibrary.clickObject(deviceDetailsSaveListBtn, "", Desc);
	
	                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	                        ".//*[@title='Vector Account Device History List:Query']",deviceHistoryTab,8,24
	                );
	
	                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Moved Transponder "+ deviceNumber+ " from account number "+transferFromAccount+ " to account number "+transferToAccount,LogStatus.INFO,false);
		
	                int rownumber=FunctionLibrary.getRowNumberFromWebTable(deviceHistoryTable,deviceNumber,"Getting row number");
	                FunctionLibrary.verifyWebTableCellData(deviceHistoryTable,rownumber,4,
	                        deviceNumber,"Verifying Device Number", true);
	                FunctionLibrary.verifyWebTableCellData(deviceHistoryTable,rownumber,7,
	                        "ACTIVE","Verifying Device Status", true);
	                FunctionLibrary.verifyWebTableCellData(deviceHistoryTable,rownumber,6,
	                        transferToAccount,"Verifying Transfer To Account Number", true);
	                String noOfDevicesAfterTransferInTransferToAccount = FunctionLibrary.getText(deviceCountTxtBox, "Get the no of devices in transfer to account");
	                
	                if(Integer.valueOf(noOfDevicesAfterTransferInTransferToAccount)==Integer.valueOf(noOfDevicesBeforeTransferInTransferToAccount)+1)
            		{
	                	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: No of Devices in Transfer To account "+(Integer.valueOf(noOfDevicesBeforeTransferInTransferToAccount)+1)+
	                			"<br> Actual: "+noOfDevicesAfterTransferInTransferToAccount,LogStatus.PASS,false);
	                	
            		}
	                else
	                {
	                	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: No of Devices in Transfer To account "+Integer.valueOf(noOfDevicesBeforeTransferInTransferToAccount)+1+
	                			"<br> Actual: "+noOfDevicesAfterTransferInTransferToAccount,LogStatus.FAIL,false);
	                		
	                }
	
	            
	        }
	        else
	        {
	            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Assigned Devices not available",LogStatus.FAIL,true);
	        }
	
	
	    }
	
	 public static void tcAccountMaintenanceACMO196() throws InterruptedException {
		 
		 
			 String accNum=dataObj.get("AccountNumber");
			// accNum = "32624990";
			 String checkAmount="50";
			 dataObj.put("Payment Amount", checkAmount);
			 dataObj.put("CheckNumber",String.valueOf(FunctionLibrary.randomNumberWithFiveDigit()));
			 
			 dataObj.put("Payment Type",dataObj.get("Rebill Pay Type"));
			 dataObj.put("TransType","R");
			 CamsPaymentProcessing.oneTimePayment(accNum);
			 
	        String Desc="Click financial tab";
	        FunctionLibrary.clickObject(financialsTab, "", Desc);
	        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	        		reverseButton,reversalTab,15,60);
	
	            int rowNumberOfCheck=FunctionLibrary.getRowNumberFromWebTable(financialTrnsactionTable,"CHECK","getting row number");
	
	            System.out.print("rowNumberOfCheck:"+rowNumberOfCheck);
	            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Screenshot before NSF reversal" , LogStatus.INFO, true);
	        	 String cellToClickToShowUpNsfFlg = "//td[@id='"+rowNumberOfCheck+"_s_1_l_ChargeNSFFee']";
	            FunctionLibrary.clickObject(cellToClickToShowUpNsfFlg, "", "Click the cell to check nsf  flag");
	           FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(nsfChargeFeeChkBox, 
	            		cellToClickToShowUpNsfFlg, 8, 25);
	            Thread.sleep(4000);
	
	                Desc = "Checking NSF";
	                System.out.print("checkbox clicking");
	                FunctionLibrary.objDriver.findElement(By.xpath(nsfChargeFeeChkBox)).click();
	
	            Desc = "Clicking reverse button";
	            FunctionLibrary.clickObject(reverseButton, "", Desc);
	            try {
	            	 WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,10);
	                 wait2.until(ExpectedConditions.alertIsPresent());
	                 
	                Alert alert1 = FunctionLibrary.objDriver.switchTo().alert();
	                System.out.println("Popup is displayed with text" + alert1.getText());
	                alert1.accept();
	
	
	            } catch (Exception e){
	
	            }
	
	
	            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "NSF added" , LogStatus.INFO, true);
	
	            FunctionLibrary.clickObject(historyTab,"","Clicking on history");
	            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
	            ("(//a[text()='Financials'])[2]", historyTab, 10, 40);
	            FunctionLibrary.clickObject("(//a[text()='Financials'])[2]","","Clicking financial tab");
	            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
	            ("//button[@title='Account Financials History List:View CC']", "(//a[text()='Financials'])[2]", 10, 40);
	
	            FunctionLibrary.waitForObject.until(ExpectedConditions.presenceOfElementLocated(By.xpath(historyFinancialTable)));
	            Thread.sleep(15000);
	            
	            int rowNumWithNsfEntry = FunctionLibrary.getRowNumberFromWebTableByTwoText(historyFinancialTable,"NSF","NONE","get the row number");
	            rowNumWithNsfEntry= rowNumWithNsfEntry+1;
	            FunctionLibrary.verifyWebTableCellData(historyFinancialTable,rowNumWithNsfEntry,7,
	                    "NSF","Verify NSF item under financial history table", true);
	            int rowNumber=FunctionLibrary.getRowNumberFromWebTable(historyFinancialTable,"PPTL","get the row number");          	
	            FunctionLibrary.verifyWebTableCellData(historyFinancialTable,rowNumber,8,
	                    "CHECK","Verifying Payment Type", false);
	            
	           /* int rowNumWithNsfEntry = FunctionLibrary.getRowNumberFromWebTableByTwoText(historyFinancialTable,"FEE","NSF","get the row number");
	            FunctionLibrary.verifyWebTableCellData(historyFinancialTable,rowNumWithNsfEntry,6,
	                    "NSF","Verify NSF item under financial history table", true);
	            
	           int rowNumber=FunctionLibrary.getRowNumberFromWebTableByTwoText(historyFinancialTable,"TOLLDEPOST","PPTL","get the row number");	          	
	            FunctionLibrary.verifyWebTableCellData(historyFinancialTable,rowNumber,7,
	                    "CHECK","Verifying Payment Type", false);*/
	
	    }
	 
	  public static void tcAccountMaintenanceACMO208() throws Exception {
	    	
	    	try{

	        String accountNum=dataObj.get("AccountNumber");
	        
	        if(accountNum.length()>1)
	        {
	            CommonLibrary.searchForAccount(accountNum);
	            //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
	            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
	            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
	            dataObj.get("Deviceinfo");
	            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(addressTab)));

	                    FunctionLibrary.clickObject(xpathDeviceTab, "", "clicking Devices tab");
	                    FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(assignDevicesListGoBtn)));
	                    FunctionLibrary.clickObject(xpathDeviceRequestTab, "", "clicking on Device Request");
	                    int rowNum = FunctionLibrary.getRowNumberFromWebTable(xpathDeviceTable, "PAID", "Get rownumbe of device which has paid status");
	                    String deviceId = FunctionLibrary.getWebTableCellData(xpathDeviceTable, rowNum, 5, "Getting cell value");
	                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Device id: "+deviceId, LogStatus.INFO, true);
	                    FunctionLibrary.verifyWebTableCellData(xpathDeviceTable, rowNum, 4, "PAID", "Verify the status of device", true);
	                    FunctionLibrary.clickObject(paidElement, "", "Click the paid status element");

	                    System.out.println(deviceId);


	                    FunctionLibrary.clickObject(cancelDeviceReqBtn, "", "Clicking Cancel Device Request button");
	                    Thread.sleep(7000);
	                    FunctionLibrary.verifyWebTableCellData(xpathDeviceTable, 1, 4, "CANCELLED", "Verify the status of device", true);
	        }      
	        	}catch(Exception e){
	                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
	          }	  }

  
           public static void addPlanAndMakePayment(String accountNum, String planName, String paymentType) throws InterruptedException
           {

           	String Desc = "";
   	        CommonLibrary.searchForAccount(accountNum);
   	        //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
	   	     FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
	         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		                                         accountInfoDevicesTab,accountInfoTab,5,20);

               //Clicking Device Requests new button

               ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                       "Clicking on Plans Tab", LogStatus.INFO, false);
               FunctionLibrary.clickObject(planTab, "", "Click plan tab");
               FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(planDetailsNewBtn,
                       plansTab,6,30);
                    
               FunctionLibrary.clickObject(planDetailsNewBtn,"","Click plans list new buttton");
               FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(planNameElement,
            		   planDetailsNewBtn,6,30);
               //FunctionLibrary.objDriver.findElement(By.xpath(planNameElement)).sendKeys(planName);
               FunctionLibrary.setText(planNameElement, planName,"Enter plan name");
               
               ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                       "Adding new plan", LogStatus.INFO, true);
               FunctionLibrary.clickObject(planSaveBtn,"","Click plans save button");
               Thread.sleep(3000);
               new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_UP)).perform();
               Desc = "Clicking Financials tab";
               FunctionLibrary.clickObject(financialsTab, "", Desc);
               FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathOfOpenItemsTable,
            		   financialsTab,10,36);
                    

               int rowNumber = 1;
               System.out.println(rowNumber);
               FunctionLibrary.verifyWebTableCellData(xpathOfOpenItemsTable, rowNumber, 2, planName,
                       "Verifying Subcategory",true);
               FunctionLibrary.verifyWebTableCellData(xpathOfOpenItemsTable, rowNumber, 3, "1",
                       "Verifying quantity",false);
               FunctionLibrary.verifyWebTableCellData(xpathOfOpenItemsTable, rowNumber, 4, "$0.00",
                       "Verifying unit price",false);

               Thread.sleep(4000);
              // new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
               FunctionLibrary.scrollDowntoElement(paymentListNewBtn, "Scroll down to new button");
               Desc = "Clicking on new button";
               FunctionLibrary.clickObject(paymentListNewBtn, "", Desc);
               
               FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathPaymentType,
            		   paymentListNewBtn, 8, 24);


              /* new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
               //Thread.sleep(4000);
               
               FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(paymentType, 
            		   paymentListNewBtn, 5, 30);*/
               
               
               
               FunctionLibrary.selectDropDownListByValue(xpathPaymentType, paymentType, "Selecting cash");
               if(paymentType.equalsIgnoreCase("CHECK"))
               {
               	String CheckNo=Integer.toString(FunctionLibrary.randomNumberWithFiveDigit());
                   Desc="Enter Check number";
                   FunctionLibrary.setText(checkPay, CheckNo, Desc);
               }
               if(paymentType.equalsIgnoreCase("SAVINGS")|| paymentType.equalsIgnoreCase("CHECKINGS"))
               {
               	String SavingNumber=dataObj.get("ACHBankRouting");
                   String RoutingNumber=dataObj.get("ACHBankNumber");
                   FunctionLibrary.setText(paymentDetailsBankAccountNbrTxtBox,SavingNumber,"Enter banking number");
                   FunctionLibrary.setText(paymentDetailsRoutingNbrTxtBox, RoutingNumber, "Enter routing number");
               }
               
       	        if(paymentType.equalsIgnoreCase("VISA")|| paymentType.equalsIgnoreCase("MASTERCARD") || paymentType.equalsIgnoreCase("DISCOVER")||paymentType.equalsIgnoreCase("AMEX"))
       	        {
       	        	
       	            String CreditCard= dataObj.get("CreditCardNo");
       	            String ExpMonth=dataObj.get("ExpMonth");
       	            String ExpYear=dataObj.get("ExpYear");
       	
       	          
       	           // String defaultSelectedPayType;
					//if (!(defaultSelectedPayType.equalsIgnoreCase(paymentType))) {
       	            	 Desc="Enter CC Details";
       	   	          
       	   	          //  FunctionLibrary.setText("//*[@id='Amount']", amount, "Set amount");
       	   	            FunctionLibrary.setText(creditCardNoField,"","Enter credit card number");
       	   	            System.out.println(CreditCard);
       	   	            FunctionLibrary.setText(creditCardNoField,CreditCard,"Enter credit card number");
       	   	            Desc="Selecting Expiry Month";
       	   	           
       	   	            try {
       	   					FunctionLibrary.selectDropDownList(creditCardExpMpnth,ExpMonth,Desc);
       	   				} catch (Exception e) {
       	   					// TODO Auto-generated catch block
       	   					e.printStackTrace();
       	   				}
       	   	          
       	   	
       	   	            Desc="Selecting Expiry Year";
       	   	            
       	   	                FunctionLibrary.selectDropDownList(creditCardExpYear,ExpYear,Desc);
       	   		
       				//}
       	
       	        }   
                       
               
               
               //Scroll down to Save button
               Desc="Scroll dwon to Payment Details Info save button";
               FunctionLibrary.scrollDowntoElement(paymentDetailsInfoSaveBtn, Desc);
               Desc="Clicks on Save Button[PaymentDetails Save button]";
               FunctionLibrary.clickObject(paymentDetailsInfoSaveBtn,"",Desc);
               Desc="Click on Save Button[payment List Save button]";
               FunctionLibrary.clickObject(paymentListSaveBtn,"","Clicking Save button");
               //Clicking on Process Pay button
               Desc="Click on Process Pay Button";
               FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(processPayBtn)));
               FunctionLibrary.objDriver.findElement(By.xpath(processPayBtn)).click();
               
               FunctionLibrary.waitForObject.until(ExpectedConditions.alertIsPresent());

               Alert alert = FunctionLibrary.objDriver.switchTo().alert();
               System.out.println("Popup is displayed with text" + alert.getText());
               String alertText = alert.getText();
               alert.accept();
               String[] paymentRefNumber = alertText.split(":");

               ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>"
                       + "Successfully done the payment for the device, Reference number is" + paymentRefNumber[1].trim(), LogStatus.INFO, false);

               FunctionLibrary.objDriver.findElement(By.xpath(accountsInfoTab)).click();
               Thread.sleep(4000);

               new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_UP)).perform();
               FunctionLibrary.clickObject(plansTab, "", "clicking on plans tab");
               FunctionLibrary.clickObject(planHistoryTab, "", "clicking on plans History");

               FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathOfPlanHistoryTable,
            		   planHistoryTab,10,36);
               
               int rowNumberWithPlanType = 1;

               System.out.println(rowNumberWithPlanType);
               FunctionLibrary.verifyWebTableCellData(xpathOfPlanHistoryTable, rowNumberWithPlanType, 2, planName,
                       "Verifying Subcategory in plan history",true);
               FunctionLibrary.verifyWebTableCellData(xpathOfPlanHistoryTable, rowNumberWithPlanType, 8, "ADD",
                       "Verifying Status in plan history",false);

           }
public static void handleAlertIfPresent(int timeInSecs)
{
    try {

        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,timeInSecs);
        wait2.until(ExpectedConditions.alertIsPresent());
        Alert alert = FunctionLibrary.objDriver.switchTo().alert();
        alert.accept();

    } catch (Exception e) {
    }
}

public static void vehicleImportAccountMaintenance(String filepath)
{
    //click on import vehicle
    FunctionLibrary.clickObject(importVehicleBtn, "", "click on import");
  /*  FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(browseFileTxtBox,
    		importVehicleBtn, 20, 60);
    //desc="Waiting for the alert";
    WebDriverWait wait=new WebDriverWait(FunctionLibrary.objDriver, 10);
         Thread.sleep(3000);
    //click on browse
    WebElement browseImportFile= FunctionLibrary.objDriver.findElement(By.xpath(browseFileTxtBox));
    wait.until(ExpectedConditions.visibilityOf(browseImportFile));
    try{

        browseImportFile.sendKeys(filePath);

    }catch(Exception e){

        FunctionLibrary.highLightElement(FunctionLibrary.objDriver,FunctionLibrary.objDriver.
                findElement(By.xpath(vehicleImportBtn1)));
    }

    Thread.sleep(5000);
    FunctionLibrary.objDriver.findElement(By.xpath(vehicleImportBtn1)).click();
    Thread.sleep(10000);
*/	        
    
    
    /*FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(".//*[@title='Vehicle File Import:Import']", "//button[@title='Vehicles:Import Vehicle']", 10, 41);*/
    FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(nextBtn, importVehicleBtn, 10, 41);     
                //desc="Waiting for the alert";
                WebDriverWait wait=new WebDriverWait(FunctionLibrary.objDriver, 10);
                     try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                //click on browse
                WebElement chooseFile= FunctionLibrary.objDriver.findElement(By.xpath("//input[@type='file']"));
                wait.until(ExpectedConditions.visibilityOf(chooseFile));
                try{
                    // String importFilePath="C://Users//34546//Desktop//aman_jain";
                   // browseImportFile.click();
                   
                    chooseFile.sendKeys(filepath);
                  
                    
        
                }catch(Exception e){
        
                    FunctionLibrary.highLightElement(FunctionLibrary.objDriver,FunctionLibrary.objDriver.
                            findElement(By.xpath("//button[@title='Vehicle File Import:Import']")));
                }
        
                //Thread.sleep(5000);
               // FunctionLibrary.objDriver.findElement(By.xpath("//button[@title='Vehicle File Import:Import']")).click();
                                                   
                
                //FUNCTIONL FLOW IS CHANGED FOR IMPORTING FILE:
                FunctionLibrary.clickObject(importFileTypeRadioBtn,"","Click csv radio button");
                FunctionLibrary.clickObject(nextBtn,"","Click next button");
                try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                FunctionLibrary.clickObject(nextBtn,"","Click next button");
                
                
                
                WebDriverWait wait5 = new WebDriverWait(FunctionLibrary.objDriver, 300);
                
                wait5.until(ExpectedConditions.elementToBeClickable(By.xpath(vehicleImportOkBtn)));
                FunctionLibrary.clickObject(vehicleImportOkBtn, "", "Click okay button");
                //wait5.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(".//*[@title='Vehicle File Import:Import']")));
                wait5.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Vehicles:Save']")));
        
           
    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: "+"Vehicles to be imported"+""+"<br>"+
            "Actual: Vehicles are imported successfully", LogStatus.PASS, true);
}
}



