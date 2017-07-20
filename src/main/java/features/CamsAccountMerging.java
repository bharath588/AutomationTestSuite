package features;

import com.relevantcodes.extentreports.LogStatus;
import libraries.CommonLibrary;
import libraries.FunctionLibrary;
import libraries.ReportLibrary;
import libraries.TestCaseLibrary;
import objectProperties.SblAccountMaintenancePageObject;
import objectProperties.SblCreateAccountPageProperties;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import static libraries.CommonLibrary.*;
import static libraries.FunctionLibrary.objDriver;
import static objectProperties.SblAccountMaintenancePageObject.okBtn;
import static objectProperties.SblAccountMerging.*;

/**
 * Created by C5056047 on 1/17/2017.
 */
public class CamsAccountMerging {

    //<editor-fold desc="Initialization">
    public static WebDriver browser= objDriver;
    public static String previousTestCaseBrowser;
    public static String LoginMessage="NotSuccess";
    public static String applicationUrl;
    public static String Execution_Status;
    public static String currentTcBrowser;
    public static int iterator;
    public static double targetAcctBalBefore=0;
    public static double targetpptlBalBefore=0;
    public static double sourceAcctBalBefore=0;
    public static double sourcepptlBalBefore=0;
    public static double source1AcctBalBefore=0;
    public static double source1pptlBalBefore=0;
    public static int targetDeviceCountBefore = 0;
    public static int targetVehicleCountBefore = 0;
    public static int sourceDeviceCountBefore = 0;
    public static int sourceVehicleCountBefore = 0;
    public static int source1DeviceCountBefore = 0;
    public static int source1VehicleCountBefore = 0;
    public static double targetAcctBalAfter=0;
    public static double targetpptlBalAfter=0;
    public static double sourceAcctBalAfter=0;
    public static double sourcepptlBalAfter=0;
    public static double source1AcctBalAfter=0;
    public static double source1pptlBalAfter=0;
    public static int targetDeviceCountAfter = 0;
    public static int targetVehicleCountAfter = 0;
    public static int sourceDeviceCountAfter = 0;
    public static int sourceVehicleCountAfter = 0;
    public static int source1DeviceCountAfter = 0;
    public static int source1VehicleCountAfter = 0;
    
    public static void CamsAccountMergingTest() throws IOException, Exception {
        ExcelSheet exl = new ExcelSheet();
        int noOfRows = exl.totalrows("FLCSS_CAMS_AccountMerging","Test_Scenarios");
        HashMap<String, String> eachTestCaseData = new HashMap<String, String>();
        String testCaseName = "";

        for (int i = 1; i <=noOfRows; i++) {

            eachTestCaseData = getEachTestCaseData(exl, "Test_Scenarios", i,"FLCSS_CAMS_AccountMerging");
            dataObj=eachTestCaseData;
            testCaseName = "tcAccountMerging"+eachTestCaseData.get("TestCaseId").replace("-","");

            String executionType = CommonLibrary.settingsSheetInfo.get("Execution_Type");
	        if ((eachTestCaseData.get("ExecutionStatus").equalsIgnoreCase("Yes")) && ( eachTestCaseData.get("TestType").contains(executionType)|| 
            		executionType.equalsIgnoreCase("All"))){
                Method testMethod = null;
                try {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>" + eachTestCaseData.get("TestCaseId")
                            + "</b>" + ": Test Case Execution is started....................... <br>"
                            + "Test case description: " + eachTestCaseData.get("TestCaseDesc"), LogStatus.INFO, false);

                    //check for class and method are found for the test case
                    try
                    {
                        Class<?> c = Class.forName("features.CamsAccountMerging");
                        testMethod= c.getMethod(testCaseName);
                        CommonLibrary.loginSblWithGenericUser(eachTestCaseData);
                        TestCaseLibrary.Get_TestCase_Instance().executeTC(testMethod);
                        CommonLibrary.logoutSiebelApplicaiton();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test method "+ testCaseName +" is not found", LogStatus.FAIL,false);
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
                    }
                    catch(Exception exp){
                        e.printStackTrace();
                        try{
                            if (FunctionLibrary.objDriver.findElement(By.xpath(okBtn)).isDisplayed())
                            {
                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Execution Failed due to popup <br>", LogStatus.FAIL, true);
                                FunctionLibrary.objDriver.findElement(By.xpath(okBtn)).click();
                                //Method for Logout and Closing browser
                                ////////logoutSiebelApplicaiton();
                                FunctionLibrary.closeAllActiveBrowser();
                            }
                        }
                        catch(Exception e1){
                            System.out.println("test failed");
                            CommonLibrary.logoutSiebelApplicaiton();
                            //FunctionLibrary.Close_All_Active_Browser();
                        }
                    }
                }//End of Catch
                dataObj.clear();
                //<editor-fold desc="Initialization">
                targetAcctBalBefore=0;
                targetpptlBalBefore=0;
                sourceAcctBalBefore=0;
                sourcepptlBalBefore=0;
                source1AcctBalBefore=0;
                source1pptlBalBefore=0;
                targetDeviceCountBefore = 0;
                targetVehicleCountBefore = 0;
                sourceDeviceCountBefore = 0;
                sourceVehicleCountBefore = 0;
                source1DeviceCountBefore = 0;
                source1VehicleCountBefore = 0;
                targetAcctBalAfter=0;
                targetpptlBalAfter=0;
                sourceAcctBalAfter=0;
                sourcepptlBalAfter=0;
                source1AcctBalAfter=0;
                source1pptlBalAfter=0;
                targetDeviceCountAfter = 0;
                targetVehicleCountAfter = 0;
                sourceDeviceCountAfter = 0;
                sourceVehicleCountAfter = 0;
                source1DeviceCountAfter = 0;
                source1VehicleCountAfter = 0;
                //</editor-fold>
            }
        }
    }

    public static void tcAccountMergingACMG001() throws Exception {
    	try{

            String targetAccountNumber =  dataObj.get("Target AccountNumber");
            String sourceAccountNumber =  dataObj.get("Source AccountNumber");
            // Searching Target account and get the details
            searchAccount("target", targetAccountNumber, "ACTIVE", "PRIVATE");
            // Searching Source account and get the details
            searchAccount("source", sourceAccountNumber, "ACTIVE", "PRIVATE");
          //click on merge
//            try {
//                Select dropDown = new Select(FunctionLibrary.getElement(tabDropdown));
//                dropDown.selectByVisibleText("Account Merger View");
//            } catch (Exception e) {
//                System.out.println("History is not in hidden icon");
//                FunctionLibrary.objDriver.findElement(By.xpath(mergingViewer )).click();
//            } 	
            //FunctionLibrary.objDriver.findElement(By.linkText("Account Merger View")).click();
            
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
            		targetAccountField, accountMergingViewLink, 15,33);
            
            // Adding Target account
            addAccountsToMerge("target", targetAccountNumber);
            // Thread.sleep(1000);
            // Adding Source account
            addAccountsToMerge("source", sourceAccountNumber);

            // Merge Accounts
            FunctionLibrary.clickObject(mergeBtn, "", "Clicks on Merge button");
            //WebDriverWait wait3 = new WebDriverWait(objDriver, 50);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(afterMergeClickIdentificationElement)));
            if(FunctionLibrary.getElement(warningDiv).isDisplayed() && FunctionLibrary.getElement(warningDiv).getText().equalsIgnoreCase("Accounts have been merged successfully(SBL-EXL-00151)")){
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, FunctionLibrary.getElement(warningDiv).getText(), LogStatus.PASS, true);
                FunctionLibrary.clickObject(divOKBtn, "", "Clicks on OK button");
                Thread.sleep(1000);
                FunctionLibrary.clickObject(accountsearchListTab, "", "Click on Account Search list tab");
                JavascriptExecutor js2 = (JavascriptExecutor) objDriver;
                js2.executeScript("return document.readyState").toString().equals("complete");
                WebDriverWait wait4 = new WebDriverWait(objDriver, 50);
                wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(goBtnSearchLst)));
                // Searching target account and get the details after merging
                accountSearchAftMerge("target", targetAccountNumber, "ACTIVE", 1);
                // Thread.sleep(2000);
                // Searching source account and get the details after merging
                accountSearchAftMerge("source", sourceAccountNumber, "CLOSED", 1);
            }else{
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Failed to merge account numbers, reason:  "+FunctionLibrary.getElement(successMessage).getText(), LogStatus.FAIL, true);
                FunctionLibrary.clickObject(divOKBtn, "", "Clicks on OK button");
                for(int i = 0 ; i < 2; i++){
                    if(FunctionLibrary.getElement(mergeTargetAcnt).isEnabled() ){
                        FunctionLibrary.clickObject(deleteBtn, "", "Clicks on delete button");
                        Thread.sleep(2000);
                        Alert deleteAlert = objDriver.switchTo().alert();
                        deleteAlert.accept();
                    }
                }
            }
    	}
            catch(Exception e)
            {
                String Errormsg=e.getMessage();

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Test is failed: <br>"+Errormsg, LogStatus.FAIL, true);
            }//End of Catch
    }

     public static void tcAccountMergingACMG006() throws Exception {
    	try
    	{
    	
        String targetAccountNumber =  dataObj.get("Target AccountNumber");
        String sourceAccountNumber =  dataObj.get("Source AccountNumber");
        
        dataObj.put("Payment Type","CASH");
        dataObj.put("Payment Amount", "5000");
        dataObj.put("TransType", "R");
        
        CamsPaymentProcessing.oneTimePayment(sourceAccountNumber);
        
       /* FunctionLibrary.clickObject(accountsTab, "", "Click on Accounts tab");
             
        
        
        // Account Search list
        FunctionLibrary.clickObject(accountsearchListTab, "", "Click on Account Search list tab");
        JavascriptExecutor js1 = (JavascriptExecutor) objDriver;
        js1.executeScript("return document.readyState").toString().equals("complete");
        WebDriverWait wait1 = new WebDriverWait(objDriver, 90);
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(refereceTxtBox)));
       */ 
        // Searching Target account and get the details
        searchAccount("target", targetAccountNumber, "ACTIVE", "BUSINESS");
        // Searching Source account and get the details
        searchAccount("source", sourceAccountNumber, "ACTIVE", "PRIVATE");
   
        //FunctionLibrary.objDriver.findElement(By.linkText("Account Merger View")).click();
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		targetAccountField, accountMergingViewLink, 15,33);
        
        WebDriverWait wait2 = new WebDriverWait(objDriver, 90);
        
        //wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(referenceBtn)));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(addBtn)));
        
        // Adding Target account
        addAccountsToMerge("target", targetAccountNumber);
        // Adding Source account
        addAccountsToMerge("source", sourceAccountNumber);

        // Merge Accounts
        Thread.sleep(1000);
        FunctionLibrary.clickObject(mergeBtn, "", "Clicks on Merge button");
        //WebDriverWait wait3 = new WebDriverWait(objDriver, 50);
        FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(afterMergeClickIdentificationElement)));
        if(FunctionLibrary.getElement(warningDiv).isDisplayed() && FunctionLibrary.getElement(warningDiv).getText().equalsIgnoreCase("Accounts have been merged successfully(SBL-EXL-00151)")){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, FunctionLibrary.getElement(warningDiv).getText(), LogStatus.PASS, true);
            FunctionLibrary.clickObject(divOKBtn, "", "Clicks on OK button");
            Thread.sleep(1000);
            FunctionLibrary.clickObject(accountsearchListTab, "", "Click on Account Search list tab");
            JavascriptExecutor js2 = (JavascriptExecutor) objDriver;
            js2.executeScript("return document.readyState").toString().equals("complete");
            WebDriverWait wait4 = new WebDriverWait(objDriver, 50);
            wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(goBtnSearchLst)));
            // Searching target account and get the details after merging
            accountSearchAftMerge("target", targetAccountNumber, "ACTIVE", 1);
            // Thread.sleep(2000);
            // Searching source account and get the details after merging
            accountSearchAftMerge("source", sourceAccountNumber, "CLOSED", 1);
        }else{
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Failed to merge account numbers, reason:  "+FunctionLibrary.getElement(successMessage).getText(), LogStatus.FAIL, true);
            FunctionLibrary.clickObject(divOKBtn, "", "Clicks on OK button");
            for(int i = 0 ; i < 2; i++){
                if(FunctionLibrary.getElement(mergeTargetAcnt).isEnabled() ){
                    FunctionLibrary.clickObject(deleteBtn, "", "Clicks on delete button");
                    Thread.sleep(2000);
                    Alert deleteAlert = objDriver.switchTo().alert();
                    deleteAlert.accept();
                }
            }
        }
    	}
        catch(Exception e)
        {
            String Errormsg=e.getMessage();

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Test is failed: <br>"+Errormsg, LogStatus.FAIL, true);
        }//End of Catch
    }

    public static void tcAccountMergingACMG011() throws Exception {
    	try{
            FunctionLibrary.clickObject(accountsTab, "", "Click on Accounts tab");
            JavascriptExecutor js = (JavascriptExecutor) objDriver;
            js.executeScript("return document.readyState").toString().equals("complete");

            // Account Search list
            FunctionLibrary.clickObject(accountsearchListTab, "", "Click on Account Search list tab");
            JavascriptExecutor js1 = (JavascriptExecutor) objDriver;
            js1.executeScript("return document.readyState").toString().equals("complete");
            WebDriverWait wait1 = new WebDriverWait(objDriver, 90);
            wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(refereceTxtBox)));
            String targetAccountNumber =  dataObj.get("Target AccountNumber");
            String sourceAccountNumber =  dataObj.get("Source AccountNumber");
            String sourceAccountNumber1 =  dataObj.get("Source AccountNumber1");
            // Searching Target account and get the details
            searchAccount("target", targetAccountNumber, "ACTIVE", "PRIVATE");
            // Searching Source account and get the details
            searchAccount("source", sourceAccountNumber, "ACTIVE", "PRIVATE");
            // Searching Source account and get the details
            searchAccount("source1", sourceAccountNumber1, "ACTIVE", "PRIVATE");

            FunctionLibrary.clickObject(accountMergingViewLink, "", "Click merger view link");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
            		targetAccountField, accountMergingViewLink, 15,33);
            
            WebDriverWait wait2 = new WebDriverWait(objDriver, 90);
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(addBtn)));
            // Adding Target account
            addAccountsToMerge("target", targetAccountNumber);
            Thread.sleep(1000);
            // Adding Source account
            addAccountsToMerge("source", sourceAccountNumber);
            Thread.sleep(1000);
            // Adding 2nd Source account
            addAccountsToMerge("source1", sourceAccountNumber1);

            // Merge Accounts
            Thread.sleep(5000);
            
            FunctionLibrary.clickObject(mergeBtn, "", "Clicks on Merge button");
            //WebDriverWait wait3 = new WebDriverWait(objDriver, 50);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(afterMergeClickIdentificationElement)));
            if(FunctionLibrary.getElement(warningDiv).isDisplayed() && FunctionLibrary.getElement(warningDiv).getText().equalsIgnoreCase("Accounts have been merged successfully(SBL-EXL-00151)")){
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, FunctionLibrary.getElement(warningDiv).getText(), LogStatus.PASS, true);
                FunctionLibrary.clickObject(divOKBtn, "", "Clicks on OK button");
                Thread.sleep(1000);
                FunctionLibrary.clickObject(accountsearchListTab, "", "Click on Account Search list tab");
                JavascriptExecutor js2 = (JavascriptExecutor) objDriver;
                js2.executeScript("return document.readyState").toString().equals("complete");
                WebDriverWait wait4 = new WebDriverWait(objDriver, 50);
                wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(goBtnSearchLst)));

                // Searching target account and get the details after merging
                accountSearchAftMerge("target", targetAccountNumber, "ACTIVE", 2);
                Thread.sleep(2000);
                // Searching source account and get the details after merging
                accountSearchAftMerge("source", sourceAccountNumber, "CLOSED", 2);
                Thread.sleep(2000);
                // Searching source account and get the details after merging
                accountSearchAftMerge("source1", sourceAccountNumber1, "CLOSED", 2);

            }else{
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Failed to merge account numbers, reason:  "+FunctionLibrary.getElement(successMessage).getText(), LogStatus.FAIL, true);
                FunctionLibrary.clickObject(divOKBtn, "", "Clicks on OK button");
                for(int i = 0 ; i < 3; i++){
                    if(FunctionLibrary.getElement(mergeTargetAcnt).isEnabled() ){
                        FunctionLibrary.clickObject(deleteBtn, "", "Clicks on delete button");
                        Thread.sleep(2000);
                        Alert deleteAlert = objDriver.switchTo().alert();
                        deleteAlert.accept();
                    }
                }
            }
    	}
        catch(Exception e)
        {
            String Errormsg=e.getMessage();

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Test is failed: <br>"+Errormsg, LogStatus.FAIL, true);
        }//End of Catch
    }
 
    public static void tcAccountMergingACMG016() throws Exception {
        try {

        	  String targetAccountNumber =  dataObj.get("Target AccountNumber");
              String sourceAccountNumber =  dataObj.get("Source AccountNumber");
              
         
            String sql = "UPDATE SIEBEL.S_ORG_EXT SET CUST_STAT_CD = 'CLOSED' WHERE OU_NUM='" + targetAccountNumber + "' ";
            CommonLibrary.executeSqlAndGetOutputAsHashMap(sql);

            searchAccount("target", targetAccountNumber, "CLOSED", "PRIVATE");
            // Searching Source account and get the details
            searchAccount("source", sourceAccountNumber, "ACTIVE", "PRIVATE");
            //click on merge
/*            try {
                Select dropDown = new Select(FunctionLibrary.getElement(tabDropdown));
                dropDown.selectByVisibleText("Account Merger View");
            } catch (Exception e) {
                System.out.println("History is not in hidden icon");
                FunctionLibrary.objDriver.findElement(By.xpath(mergingViewer )).click();
            }
*/          
            FunctionLibrary.clickObject(mergingViewer, "", "Click merger viewr link");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(addBtn, mergingViewer, 12, 37);
            CamsShiftManagement.searchForTod("Account #", targetAccountNumber);
      
            try {
                FunctionLibrary.clickObject(targetAccountField, "", "Clicks on target account field");
                FunctionLibrary.clickObject(targetAccountYesNo, "", "Selected the target account");
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, targetAccountNumber + " Target Account number is not closed", LogStatus.FAIL, true);
            } catch (Exception e) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Target Account number " + targetAccountNumber + " is closed", LogStatus.PASS, true);
            }

        } catch (Exception e) {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Uknown Error Occurred", LogStatus.FAIL, true);

        }
    }
 
    public static void tcAccountMergingACMG040() throws Exception {
    	try{
    	
            String targetAccountNumber =  dataObj.get("Target AccountNumber");       
            String sourceAccountNumber =  dataObj.get("Source AccountNumber");
            
            // Searching Target account and get the details
            searchAccount("target", targetAccountNumber, "ACTIVE", "PRIVATE");
            
            makeAccountToDeceased();
            // Searching Source account and get the details
            searchAccount("source", sourceAccountNumber, "ACTIVE", "PRIVATE");
          //click on merge
//            try {
//                Select dropDown = new Select(FunctionLibrary.getElement(tabDropdown));
//                dropDown.selectByVisibleText("Account Merger View");
//            } catch (Exception e) {
//                System.out.println("History is not in hidden icon");
//                FunctionLibrary.objDriver.findElement(By.xpath(mergingViewer )).click();
//            } 
            //FunctionLibrary.objDriver.findElement(By.linkText("Account Merger View")).click();
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
            		targetAccountField, accountMergingViewLink, 15,33);
            
            // Adding Target account
            addAccountsToMerge("target", targetAccountNumber);
            // Thread.sleep(1000);
            // Adding Source account
            addAccountsToMerge("source", sourceAccountNumber);

            // Merge Accounts
            FunctionLibrary.clickObject(mergeBtn, "", "Clicks on Merge button");
            //WebDriverWait wait3 = new WebDriverWait(objDriver, 50);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(divOKBtn)));
            if(FunctionLibrary.getElement(warningDiv).isDisplayed() && FunctionLibrary.getElement(warningDiv).getText().equalsIgnoreCase("Accounts have been merged successfully(SBL-EXL-00151)")){
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, FunctionLibrary.getElement(warningDiv).getText(), LogStatus.PASS, true);
                FunctionLibrary.clickObject(divOKBtn, "", "Clicks on OK button");
                Thread.sleep(1000);
                FunctionLibrary.clickObject(accountsearchListTab, "", "Click on Account Search list tab");
                JavascriptExecutor js2 = (JavascriptExecutor) objDriver;
                js2.executeScript("return document.readyState").toString().equals("complete");
                WebDriverWait wait4 = new WebDriverWait(objDriver, 50);
                wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(goBtnSearchLst)));
                // Searching target account and get the details after merging
                accountSearchAftMerge("target", targetAccountNumber, "ACTIVE", 1);
                // Thread.sleep(2000);
                // Searching source account and get the details after merging
                accountSearchAftMerge("source", sourceAccountNumber, "CLOSED", 1);
            }else{
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Failed to merge account numbers, reason:  "+FunctionLibrary.getElement(successMessage).getText(), LogStatus.FAIL, true);
                FunctionLibrary.clickObject(divOKBtn, "", "Clicks on OK button");
                for(int i = 0 ; i < 2; i++){
                    if(FunctionLibrary.getElement(mergeTargetAcnt).isEnabled() ){
                        FunctionLibrary.clickObject(deleteBtn, "", "Clicks on delete button");
                        Thread.sleep(2000);
                        Alert deleteAlert = objDriver.switchTo().alert();
                        deleteAlert.accept();
                    }
                }
            }
    	}
            catch(Exception e)
            {
                String Errormsg=e.getMessage();

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Test is failed: <br>"+Errormsg, LogStatus.FAIL, true);
            }//End of Catch
    }

    public static void tcAccountMergingACMG059() throws Exception {
    	try{

            String targetAccountNumber =  dataObj.get("Target AccountNumber");
            String sourceAccountNumber =  dataObj.get("Source AccountNumber");
            // Searching Target account and get the details
            searchAccount("target", targetAccountNumber, "ACTIVE", "BUSINESS");
            // Searching Source account and get the details
            searchAccount("source", sourceAccountNumber, "ACTIVE", "BUSINESS");
          //click on merge
//            try {
//                Select dropDown = new Select(FunctionLibrary.getElement(tabDropdown));
//                dropDown.selectByVisibleText("Account Merger View");
//            } catch (Exception e) {
//                System.out.println("History is not in hidden icon");
//                FunctionLibrary.objDriver.findElement(By.xpath(mergingViewer )).click();
//            } 
            //FunctionLibrary.objDriver.findElement(By.linkText("Account Merger View")).click();
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
            		targetAccountField, accountMergingViewLink, 15,33);
            
            // Adding Target account
            addAccountsToMerge("target", targetAccountNumber);
            // Thread.sleep(1000);
            // Adding Source account
            addAccountsToMerge("source", sourceAccountNumber);

            // Merge Accounts
            FunctionLibrary.clickObject(mergeBtn, "", "Clicks on Merge button");
            //WebDriverWait wait3 = new WebDriverWait(objDriver, 50);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(afterMergeClickIdentificationElement)));
            if(FunctionLibrary.getElement(warningDiv).isDisplayed() && FunctionLibrary.getElement(warningDiv).getText().equalsIgnoreCase("Accounts have been merged successfully(SBL-EXL-00151)")){
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, FunctionLibrary.getElement(warningDiv).getText(), LogStatus.PASS, true);
                FunctionLibrary.clickObject(divOKBtn, "", "Clicks on OK button");
                Thread.sleep(1000);
                FunctionLibrary.clickObject(accountsearchListTab, "", "Click on Account Search list tab");
                JavascriptExecutor js2 = (JavascriptExecutor) objDriver;
                js2.executeScript("return document.readyState").toString().equals("complete");
                WebDriverWait wait4 = new WebDriverWait(objDriver, 50);
                wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(goBtnSearchLst)));
                // Searching target account and get the details after merging
                accountSearchAftMerge("target", targetAccountNumber, "ACTIVE", 1);
                // Thread.sleep(2000);
                // Searching source account and get the details after merging
                accountSearchAftMerge("source", sourceAccountNumber, "CLOSED", 1);
            }else{
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Failed to merge account numbers, reason:  "+FunctionLibrary.getElement(successMessage).getText(), LogStatus.FAIL, true);
                FunctionLibrary.clickObject(divOKBtn, "", "Clicks on OK button");
                for(int i = 0 ; i < 2; i++){
                    if(FunctionLibrary.getElement(mergeTargetAcnt).isEnabled() ){
                        FunctionLibrary.clickObject(deleteBtn, "", "Clicks on delete button");
                        Thread.sleep(2000);
                        Alert deleteAlert = objDriver.switchTo().alert();
                        deleteAlert.accept();
                    }
                }
            }
    	}
            catch(Exception e)
            {
                String Errormsg=e.getMessage();

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Test is failed: <br>"+Errormsg, LogStatus.FAIL, true);
            }//End of Catch
    }
 
    // Search account number and get the account details
    public static void searchAccount(String account, String accountNumber, String accountStatus, String accountType) throws Exception {
    	
    	CommonLibrary.searchForAccount(accountNumber);
    	

        if(objDriver.findElements(By.xpath("//*[@id='1_s_2_l_CSN']")).size() > 0 && FunctionLibrary.getElement(searchedAcntNo).getText().equals(accountNumber)
                && FunctionLibrary.getElement(accountStatusField).getText().equalsIgnoreCase(accountStatus)){
            if(!FunctionLibrary.getElement(accountTypeField).getText().equalsIgnoreCase(accountType)){
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Entered "+ account +" account is not a "+ accountType +" account", LogStatus.FAIL, true);
                throw new Exception("Entered "+ account +" account is not a "+ accountType +" account");
            }
            if(account.equalsIgnoreCase("target")){
            	
                targetDeviceCountBefore = Integer.parseInt(FunctionLibrary.getElement(deviceCount).getAttribute("value"));
                targetVehicleCountBefore = Integer.parseInt(FunctionLibrary.getElement(vehicleCount).getAttribute("value"));
                if(FunctionLibrary.getElement(acctBal).getAttribute("value").startsWith("$")){ targetAcctBalBefore = Double.parseDouble(FunctionLibrary.getElement(acctBal).getAttribute("value").replace("$","").replace(",", "")); }
                if(FunctionLibrary.getElement(pptlBal).getAttribute("value").startsWith("$")){ targetpptlBalBefore = Double.parseDouble(FunctionLibrary.getElement(pptlBal).getAttribute("value").replace("$","").replace(",", "")); }
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>"+"Target Account Number : "+ FunctionLibrary.getElement(searchedAcntNo).getText()+"</b>" + "<br>"+ " Account Type: "+FunctionLibrary.getElement(accountTypeField).getText()
                        +"<br>"+ " Account Status: "+FunctionLibrary.getElement(accountStatusField).getText()+"<br>"+ " Account balance: "+targetAcctBalBefore +"<br>"+"PPTL Balance: "+targetpptlBalBefore
                        +"<br>"+ "No of Devices before merging: "+targetDeviceCountBefore+ "<br>"+"No of Vehicles before merging: "+ targetVehicleCountBefore, LogStatus.PASS, true);
            }else if(account.equalsIgnoreCase("source")){
                sourceDeviceCountBefore = Integer.parseInt(FunctionLibrary.getElement(deviceCount).getAttribute("value"));
                sourceVehicleCountBefore = Integer.parseInt(FunctionLibrary.getElement(vehicleCount).getAttribute("value"));
                if(FunctionLibrary.getElement(acctBal).getAttribute("value").startsWith("$")){ sourceAcctBalBefore = Double.parseDouble(FunctionLibrary.getElement(acctBal).getAttribute("value").replace("$","").replace(",", "")); }
                if(FunctionLibrary.getElement(pptlBal).getAttribute("value").startsWith("$")){ sourcepptlBalBefore = Double.parseDouble(FunctionLibrary.getElement(pptlBal).getAttribute("value").replace("$","").replace(",", "")); }
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Source Account Number : "+ FunctionLibrary.getElement(searchedAcntNo).getText() + "<br>"+ " Account Type: "+FunctionLibrary.getElement(accountTypeField).getText()
                        +"<br>"+ " Account Status: "+FunctionLibrary.getElement(accountStatusField).getText()+"<br>"+ " Account Balance: "+sourceAcctBalBefore +"<br>"+"PPTL Balance: "+sourcepptlBalBefore
                        +"<br>"+ "No. of Devices before merging: "+sourceDeviceCountBefore+ "<br>"+"No. of Vehicles before merging: "+ sourceVehicleCountBefore, LogStatus.PASS, true);
            }else if(account.equalsIgnoreCase("source1")){
                source1DeviceCountBefore = Integer.parseInt(FunctionLibrary.getElement(deviceCount).getAttribute("value"));
                source1VehicleCountBefore = Integer.parseInt(FunctionLibrary.getElement(vehicleCount).getAttribute("value"));
                if(FunctionLibrary.getElement(acctBal).getAttribute("value").startsWith("$")){ source1AcctBalBefore = Double.parseDouble(FunctionLibrary.getElement(acctBal).getAttribute("value").replace("$","").replace(",", "")); }
                if(FunctionLibrary.getElement(pptlBal).getAttribute("value").startsWith("$")){ source1pptlBalBefore = Double.parseDouble(FunctionLibrary.getElement(pptlBal).getAttribute("value").replace("$","").replace(",", "")); }
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "2nd Source Account Number : "+ FunctionLibrary.getElement(searchedAcntNo).getText() + "<br>"+ " Account Type: "+FunctionLibrary.getElement(accountTypeField).getText()
                        +"<br>"+ " Account Status: "+FunctionLibrary.getElement(accountStatusField).getText()+"<br>"+ " Account Balance: "+source1AcctBalBefore +"<br>"+"PPTL Balance: "+source1pptlBalBefore
                        +"<br>"+ "No. of Devices before merging: "+source1DeviceCountBefore+ "<br>"+"No. of Vehicles before merging: "+ source1VehicleCountBefore, LogStatus.PASS, true);
            }
        }
        else{
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Entered account number is not valid please check the accuont type & status"+ "<br>"+"Account Number : "+ FunctionLibrary.getElement(searchedAcntNo).getText() + "<br>"+ " Account Type: "+FunctionLibrary.getElement(accountTypeField).getText()
                    +"<br>"+ " Account Status: "+FunctionLibrary.getElement(accountStatusField).getText(), LogStatus.FAIL, true);
        }
    }

    // Add account to merge
    public static void addAccountsToMerge(String account, String accountnumber) throws InterruptedException {
        Thread.sleep(1000);
        CamsShiftManagement.searchForTod("Account #", accountnumber);
        if(account.equalsIgnoreCase("target")){
            FunctionLibrary.clickObject(targetAccountField, "", "Clicks on "+ account+" account field");
            FunctionLibrary.clickObject(targetAccountYesNo, "", "Selected the "+ account+ " account");
        }else if(account.equalsIgnoreCase("source")){
            FunctionLibrary.clickObject(sourceAccountField, "", "Clicks on "+ account+" account field");
            FunctionLibrary.clickObject(sourceAccountYesNo, "", "Selected the "+ account+" account");
        } else if(account.equalsIgnoreCase("source1")){
            FunctionLibrary.clickObject(sourceAccountField, "", "Clicks on 2nd source account field");
            FunctionLibrary.clickObject(sourceAccountYesNo, "", "Selected the 2nd source account");
        }

        FunctionLibrary.clickObject(addBtn, "", "Clicks on Add button");
        Thread.sleep(3000);
        JavascriptExecutor js2 = (JavascriptExecutor) objDriver;
        js2.executeScript("return document.readyState").toString().equals("complete");
        WebDriverWait waita = new WebDriverWait(objDriver, 20);
        waita.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(mergeTargetAcnt)));
        WebElement acctListTbl = FunctionLibrary.getElement(mergeListTable);
        List<WebElement> rowdata = acctListTbl.findElements(By.tagName("tr"));
        int rowsize = rowdata.size();
        boolean acctAdded = false;
        outerloop:
        for(int i=1; i<rowsize; i++){
            java.util.List<WebElement> columnData = rowdata.get(i).findElements(By.tagName("td"));
            if(columnData.get(1).getText().equalsIgnoreCase(accountnumber)){
                acctAdded = true;
                break outerloop;
            }
        }
        if(acctAdded){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Added "+account +" account number : " + accountnumber + " to merge", LogStatus.PASS, true);
        }
        else{
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Failed to add "+ account +" account number: " + accountnumber + " to merge", LogStatus.FAIL, true);
        }
    }

    // Account search after merging accounts and get the account details
    public static void accountSearchAftMerge(String account, String accountNumber, String accountStatus, int noofsource) throws InterruptedException {
    	
    	CommonLibrary.searchForAccount(accountNumber);
    	Thread.sleep(5000);
        if(objDriver.findElements(By.xpath("//*[@id='1_s_2_l_CSN']")).size() > 0 && FunctionLibrary.getElement(searchedAcntNo).getText().equals(accountNumber)
                && FunctionLibrary.getElement(accountStatusField).getText().equalsIgnoreCase(accountStatus)){
            if(account.equalsIgnoreCase("target")){
                targetDeviceCountAfter = Integer.parseInt(FunctionLibrary.getElement(deviceCount).getAttribute("value"));
                targetVehicleCountAfter = Integer.parseInt(FunctionLibrary.getElement(vehicleCount).getAttribute("value"));
                if(FunctionLibrary.getElement(acctBal).getAttribute("value").startsWith("$"))
                { targetAcctBalAfter = Double.parseDouble(FunctionLibrary.getElement(acctBal).getAttribute("value").replace("$","").replace(",", "")); }
                if(FunctionLibrary.getElement(pptlBal).getAttribute("value").startsWith("$"))
                { targetpptlBalAfter = Double.parseDouble(FunctionLibrary.getElement(pptlBal).getAttribute("value").replace("$","").replace(",", "")); }
                
                targetAcctBalBefore = Math.round(targetAcctBalBefore*100)/100.0d;
                sourceAcctBalBefore = Math.round(sourceAcctBalBefore*100)/100.0d;
                source1AcctBalBefore = Math.round(source1AcctBalBefore*100)/100.0d;                
                targetAcctBalAfter = Math.round(targetAcctBalAfter*100)/100.0d;
                
                targetpptlBalBefore = Math.round(targetpptlBalBefore*100)/100.0d;
                sourcepptlBalBefore = Math.round(sourcepptlBalBefore*100)/100.0d;
                targetpptlBalAfter = Math.round(targetpptlBalAfter*100)/100.0d;
                source1pptlBalBefore = Math.round(source1pptlBalBefore*100)/100.0d;
                
                if(noofsource == 1){
                   if((targetAcctBalBefore+sourceAcctBalBefore) == targetAcctBalAfter && (targetpptlBalBefore + sourcepptlBalBefore) == targetpptlBalAfter){
                       ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "After merging, Target Account Number : "+ FunctionLibrary.getElement(searchedAcntNo).getText() +
                               "<br>" + "Account Status is: "+FunctionLibrary.getElement(accountStatusField).getText()+ "<br>"+" Acct balance after merging: "+targetAcctBalAfter +"<br>" +"PPTL balance after merging: "+targetpptlBalAfter
                               +"<br>"+ "No. of devices after merging: "+targetDeviceCountAfter+"<br>"+"No. of vehicles not updated after merging: "+targetVehicleCountAfter, LogStatus.PASS, true);
                       if(!((targetDeviceCountBefore+sourceDeviceCountBefore) == targetDeviceCountAfter)){
                           ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "No. of devices not updated after merging: "+targetDeviceCountAfter, LogStatus.FAIL, false);
                       }
                       if(!((targetVehicleCountBefore+sourceVehicleCountBefore) == targetVehicleCountAfter)){
                           ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "No. of vehicles not updated after merging: "+targetVehicleCountAfter, LogStatus.FAIL, true);
                       }
                   }else{
                       ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Expected account balance after merging: "+(targetAcctBalBefore+sourceAcctBalBefore) +"<br>"+
                               "<br>"+" Actual account balance after merging: "+targetAcctBalAfter +"<br>"+
                               "<br>"+" Expected PPTL balance after merging: "+(targetpptlBalBefore + sourcepptlBalBefore) +"<br>"+
                               "<br>"+" Actual PPTL balance after merging: "+targetpptlBalAfter +"<br>"
                               , LogStatus.FAIL, true);
                   }
               }else if(noofsource == 2){
                   if((targetAcctBalBefore+sourceAcctBalBefore+source1AcctBalBefore) == targetAcctBalAfter && (targetpptlBalBefore + sourcepptlBalBefore+source1pptlBalBefore) == targetpptlBalAfter){
                       ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "After merging, Target Account Number : "+ FunctionLibrary.getElement(searchedAcntNo).getText() +
                               "<br>" + "Account Status is: "+FunctionLibrary.getElement(accountStatusField).getText()+"<br>"+" Acct balance after merging: "+targetAcctBalAfter +"<br>" +"PPTL balance after merging: "+targetpptlBalAfter
                               +"<br>"+ "No. of devices after merging: "+targetDeviceCountAfter+"<br>"+"No. of vehicles not updated after merging: "+targetVehicleCountAfter, LogStatus.PASS, true);
                       if(!((targetDeviceCountBefore+sourceDeviceCountBefore+source1DeviceCountBefore) == targetDeviceCountAfter)){
                           ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "No. of devices not updated after merging: "+targetDeviceCountAfter, LogStatus.FAIL, false);
                       }
                       if(!((targetVehicleCountBefore+sourceVehicleCountBefore+source1VehicleCountBefore) == targetVehicleCountAfter)){
                           ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "No. of vehicles not updated after merging: "+targetVehicleCountAfter, LogStatus.FAIL, true);
                       }
                   }else{
                      /* ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "After merging Target account balance is not updated, Account Number : "+ FunctionLibrary.getElement(searchedAcntNo).getText() +
                               "<br>"+" Acct balance after merging: "+targetAcctBalAfter +"<br>" +"PPTL balance after merging: "+targetpptlBalAfter, LogStatus.FAIL, true);*/
                	   
                	   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Expected account balance after merging: "+(targetAcctBalBefore+sourceAcctBalBefore+source1AcctBalBefore) +"<br>"+
                               "<br>"+" Actual account balance after merging: "+ targetAcctBalAfter +"<br>"+
                               "<br>"+" Expected PPTL balance after merging: "+ (targetpptlBalBefore + sourcepptlBalBefore+source1pptlBalBefore) +"<br>"+
                               "<br>"+" Actual PPTL balance after merging: "+ targetpptlBalAfter +"<br>"
                               , LogStatus.FAIL, true);
                   }
               }

            } else if(account.equalsIgnoreCase("source")){
                sourceDeviceCountAfter = Integer.parseInt(FunctionLibrary.getElement(deviceCount).getAttribute("value"));
                sourceVehicleCountAfter = Integer.parseInt(FunctionLibrary.getElement(vehicleCount).getAttribute("value"));
                if(FunctionLibrary.getElement(acctBal).getAttribute("value").startsWith("$")){ sourceAcctBalAfter = Double.parseDouble(FunctionLibrary.getElement(acctBal).getAttribute("value").replace("$","").replace(",", "")); }
                if(FunctionLibrary.getElement(pptlBal).getAttribute("value").startsWith("$")){ sourcepptlBalAfter = Double.parseDouble(FunctionLibrary.getElement(pptlBal).getAttribute("value").replace("$","").replace(",", "")); }
                if(sourceAcctBalAfter == 0.0 && sourceDeviceCountAfter == 0 && sourceVehicleCountAfter == 0){
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "After merging, Source Account Number : "+ FunctionLibrary.getElement(searchedAcntNo).getText() +
                            "<br>"+ " Account Status: "+FunctionLibrary.getElement(accountStatusField).getText() + "<br>"+" Acct balance after merging: "+sourceAcctBalAfter +"<br>" +"PPTL balance after merging: "+sourcepptlBalAfter
                            +"<br>"+"No. of devices after merging: "+sourceDeviceCountAfter +"<br>"+"No. of vehicles after megring: "+sourceVehicleCountAfter, LogStatus.PASS, true);
                    if(!(sourceDeviceCountAfter == 0)){
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "No. of devices not updated after merging: "+sourceDeviceCountAfter, LogStatus.FAIL, false);
                    }
                    if(!(sourceVehicleCountAfter == 0)){
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "No. of vehicles not updated after merging: "+sourceVehicleCountAfter, LogStatus.FAIL, false);
                    }
                }else{
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "After merging Source account balance is not updated, Account Number : "+ FunctionLibrary.getElement(searchedAcntNo).getText() +
                            "<br>"+" Acct balance after merging: "+sourceAcctBalAfter +"<br>" +"PPTL balance after merging: "+sourcepptlBalAfter, LogStatus.FAIL, true);
                }
            }else if(account.equalsIgnoreCase("source1")){
                source1DeviceCountAfter = Integer.parseInt(FunctionLibrary.getElement(deviceCount).getAttribute("value"));
                source1VehicleCountAfter = Integer.parseInt(FunctionLibrary.getElement(vehicleCount).getAttribute("value"));
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, FunctionLibrary.getElement(acctBal).getAttribute("value"), LogStatus.PASS, false);
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, FunctionLibrary.getElement(pptlBal).getAttribute("value"), LogStatus.PASS, false);
                if(FunctionLibrary.getElement(acctBal).getAttribute("value").startsWith("$")){ source1AcctBalAfter = Double.parseDouble(FunctionLibrary.getElement(acctBal).getAttribute("value").replace("$","").replace(",", "")); }
                if(FunctionLibrary.getElement(pptlBal).getAttribute("value").startsWith("$")){ source1pptlBalAfter = Double.parseDouble(FunctionLibrary.getElement(pptlBal).getAttribute("value").replace("$","").replace(",", "")); }
                if(source1AcctBalAfter == 0.0 && source1DeviceCountAfter == 0 && source1VehicleCountAfter == 0){
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "After merging, 2nd Source Account Number : "+ FunctionLibrary.getElement(searchedAcntNo).getText() +
                            "<br>"+ " Account Status: "+FunctionLibrary.getElement(accountStatusField).getText() + "<br>"+" Acct balance after merging: "+source1AcctBalAfter +"<br>" +"PPTL balance after merging: "+source1pptlBalAfter
                            +"<br>"+"No. of devices after merging: "+source1DeviceCountAfter +"<br>"+"No. of vehicles after megring: "+source1VehicleCountAfter, LogStatus.PASS, true);
                    if(!(source1DeviceCountAfter == 0)){
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "No. of devices not updated after merging: "+source1DeviceCountAfter, LogStatus.FAIL, false);
                    }
                    if(!(source1VehicleCountAfter == 0)){
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "No of vehicles not updated after merging: "+source1VehicleCountAfter, LogStatus.FAIL, false);
                    }
                }else{
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "After merging 2nd source account balance is not updated, Account Number : "+ FunctionLibrary.getElement(searchedAcntNo).getText() +
                            "<br>"+" Acct balance after merging: "+source1AcctBalAfter +"<br>" +"PPTL balance after merging: "+source1pptlBalAfter, LogStatus.FAIL, true);
                }
            }

        }
        else{
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "After merging "+ account +" Account is not updated : "+ FunctionLibrary.getElement(searchedAcntNo).getText() + "<br>"+ " Account Type: "+FunctionLibrary.getElement(accountTypeField).getText()
                    +"<br>"+ " Account Status: "+FunctionLibrary.getElement(accountStatusField).getText(), LogStatus.FAIL, true);
        }
    }

    public static void tcAccountMergingACMG062old() throws Exception {
    	try{

            String targetAccountNumber =  dataObj.get("Target AccountNumber");
            String sourceAccountNumber =  dataObj.get("Source AccountNumber");
            // Searching Target account and get the details
            searchAccount("target", targetAccountNumber, "ACTIVE", "COMMERCIAL");
            // Searching Source account and get the details
            searchAccount("source", sourceAccountNumber, "ACTIVE", "COMMERCIAL");
          //click on merge
//            try {
//                Select dropDown = new Select(FunctionLibrary.getElement(tabDropdown));
//                dropDown.selectByVisibleText("Account Merger View");
//            } catch (Exception e) {
//                System.out.println("History is not in hidden icon");
//                FunctionLibrary.objDriver.findElement(By.xpath(mergingViewer )).click();
//            } 
            //FunctionLibrary.objDriver.findElement(By.linkText("Account Merger View")).click();
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
            		targetAccountField, accountMergingViewLink, 15,33);
            
            // Adding Target account
            addAccountsToMerge("target", targetAccountNumber);
            // Thread.sleep(1000);
            // Adding Source account
            addAccountsToMerge("source", sourceAccountNumber);

            // Merge Accounts
            FunctionLibrary.clickObject(mergeBtn, "", "Clicks on Merge button");
            //WebDriverWait wait3 = new WebDriverWait(objDriver, 50);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(afterMergeClickIdentificationElement)));
            if(FunctionLibrary.getElement(warningDiv).isDisplayed() && FunctionLibrary.getElement(warningDiv).getText().equalsIgnoreCase("Accounts have been merged successfully(SBL-EXL-00151)")){
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, FunctionLibrary.getElement(warningDiv).getText(), LogStatus.PASS, true);
                FunctionLibrary.clickObject(divOKBtn, "", "Clicks on OK button");
                Thread.sleep(1000);
                FunctionLibrary.clickObject(accountsearchListTab, "", "Click on Account Search list tab");
                JavascriptExecutor js2 = (JavascriptExecutor) objDriver;
                js2.executeScript("return document.readyState").toString().equals("complete");
                WebDriverWait wait4 = new WebDriverWait(objDriver, 50);
                wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(goBtnSearchLst)));
                // Searching target account and get the details after merging
                accountSearchAftMerge("target", targetAccountNumber, "ACTIVE", 1);
                // Thread.sleep(2000);
                // Searching source account and get the details after merging
                accountSearchAftMerge("source", sourceAccountNumber, "CLOSED", 1);
            }else{
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Failed to merge account numbers, reason:  "+FunctionLibrary.getElement(successMessage).getText(), LogStatus.FAIL, true);
                FunctionLibrary.clickObject(divOKBtn, "", "Clicks on OK button");
                for(int i = 0 ; i < 2; i++){
                    if(FunctionLibrary.getElement(mergeTargetAcnt).isEnabled() ){
                        FunctionLibrary.clickObject(deleteBtn, "", "Clicks on delete button");
                        Thread.sleep(2000);
                        Alert deleteAlert = objDriver.switchTo().alert();
                        deleteAlert.accept();
                    }
                }
            }
    	}
            catch(Exception e)
            {
                String Errormsg=e.getMessage();

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Test is failed: <br>"+Errormsg, LogStatus.FAIL, true);
            }//End of Catch
    }

    public static void tcAccountMergingACMG062() throws Exception {
    	try{

            String targetAccountNumber =  dataObj.get("Target AccountNumber");
            String sourceAccountNumber =  dataObj.get("Source AccountNumber");
            // Searching Target account and get the details
            searchAccount("target", targetAccountNumber, "ACTIVE", "COMMERCIAL");
            // Searching Source account and get the details
            searchAccount("source", sourceAccountNumber, "ACTIVE", "COMMERCIAL");
          //click on merge
//            try {
//                Select dropDown = new Select(FunctionLibrary.getElement(tabDropdown));
//                dropDown.selectByVisibleText("Account Merger View");
//            } catch (Exception e) {
//                System.out.println("History is not in hidden icon");
//                FunctionLibrary.objDriver.findElement(By.xpath(mergingViewer )).click();
//            } 	
            //FunctionLibrary.objDriver.findElement(By.linkText("Account Merger View")).click();
            
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
            		targetAccountField, accountMergingViewLink, 15,33);
            
            // Adding Target account
            addAccountsToMerge("target", targetAccountNumber);
            // Thread.sleep(1000);
            // Adding Source account
            addAccountsToMerge("source", sourceAccountNumber);

            // Merge Accounts
            FunctionLibrary.clickObject(mergeBtn, "", "Clicks on Merge button");
            //WebDriverWait wait3 = new WebDriverWait(objDriver, 50);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(afterMergeClickIdentificationElement)));
            if(FunctionLibrary.getElement(warningDiv).isDisplayed() && FunctionLibrary.getElement(warningDiv).getText().equalsIgnoreCase("Accounts have been merged successfully(SBL-EXL-00151)")){
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, FunctionLibrary.getElement(warningDiv).getText(), LogStatus.PASS, true);
                FunctionLibrary.clickObject(divOKBtn, "", "Clicks on OK button");
                Thread.sleep(1000);
                FunctionLibrary.clickObject(accountsearchListTab, "", "Click on Account Search list tab");
                JavascriptExecutor js2 = (JavascriptExecutor) objDriver;
                js2.executeScript("return document.readyState").toString().equals("complete");
                WebDriverWait wait4 = new WebDriverWait(objDriver, 50);
                wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(goBtnSearchLst)));
                // Searching target account and get the details after merging
                accountSearchAftMerge("target", targetAccountNumber, "ACTIVE", 1);
                // Thread.sleep(2000);
                // Searching source account and get the details after merging
                accountSearchAftMerge("source", sourceAccountNumber, "CLOSED", 1);
            }else{
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Failed to merge account numbers, reason:  "+FunctionLibrary.getElement(successMessage).getText(), LogStatus.FAIL, true);
                FunctionLibrary.clickObject(divOKBtn, "", "Clicks on OK button");
                for(int i = 0 ; i < 2; i++){
                    if(FunctionLibrary.getElement(mergeTargetAcnt).isEnabled() ){
                        FunctionLibrary.clickObject(deleteBtn, "", "Clicks on delete button");
                        Thread.sleep(2000);
                        Alert deleteAlert = objDriver.switchTo().alert();
                        deleteAlert.accept();
                    }
                }
            }
    	}
            catch(Exception e)
            {
                String Errormsg=e.getMessage();

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Test is failed: <br>"+Errormsg, LogStatus.FAIL, true);
            }//End of Catch
    }
    
    public static void makeAccountToDeceased()  throws InterruptedException
        {
        	 String accNum=dataObj.get("Target AccountNumber");
        	 /*accNum="32648759";
        	 CommonLibrary.loginSblWithGenericUser(dataObj);
        	CommonLibrary.searchForAccount(accNum);
            FunctionLibrary.objDriver.findElement(By.linkText(accNum)).click();
            FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 60, contactTab);*/
        	 FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(contactTab, SblAccountMaintenancePageObject.accountsInfoTab,15,36);
            FunctionLibrary.clickObject(contactTab, "", "clicK On Contact Tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(contactNewBtn, contactTab, 10,43);
            FunctionLibrary.clickObject(contactDetailsDeaceased, "", "Click deceased element");	
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(deceasedRadioBtn, contactDetailsDeaceased, 10,43);
            FunctionLibrary.clickObject(deceasedRadioBtn, "", "Click the deceased radio button");
            
            WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver,4);

            try
            {
               wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(okBtn)));
               FunctionLibrary.clickObject(okBtn, "", "Click ok button");
              } catch(TimeoutException e)
            {
            	  
            }
            
            FunctionLibrary.clickObject(deceasedSaveBtn, "", "clicK On Save");
            CamsSiebelAccountMaintenance.handleAlertIfPresent(10);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(checkBoxDeaceased)));
            
            if(FunctionLibrary.objDriver.findElement(By.xpath(checkBoxDeaceased)).isDisplayed())
            {
            	 ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "The ACCOUNT IS MADE TO DECEASED "+accNum, LogStatus.INFO, true);
            }else
            {
            	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "The ACCOUNT IS UNABLE TO MADE TO DECEASED "+accNum, LogStatus.INFO, true);
            }
            

        }


}