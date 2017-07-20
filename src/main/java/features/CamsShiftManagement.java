package features;

import com.relevantcodes.extentreports.LogStatus;

import libraries.CommonLibrary;
import libraries.FunctionLibrary;
import libraries.ReportLibrary;
import libraries.TestCaseLibrary;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static libraries.CommonLibrary.*;
import static libraries.FunctionLibrary.*;
import static objectProperties.SblAccountMaintenancePageObject.okBtn;
import static objectProperties.SblAccountMaintenancePageObject.xpathPaymentType;
import static objectProperties.SblBatchTagAssignmentProperties.siteMapFilterTxtBox;
import static objectProperties.SblCreateAccountPageProperties.*;
import static objectProperties.SblDeviceStatusChnageProperties.accountInfoDevicesTab;
import static objectProperties.SblDeviceStatusChnageProperties.accountInfoTab;
import static objectProperties.SiebelShiftManagement.*;

/**
 * Created by C5056047 on 1/11/2017.
 */
public class CamsShiftManagement {

    public static WebDriver browser= objDriver;
    public static String previousTestCaseBrowser;
    public static String LoginMessage="NotSuccess";
    public static String applicationUrl;
    public static String Execution_Status;
    public static String AccountType;
    public static String ModeType;
    public static String currentTcBrowser;
    public static String Desc="";
    public static int iterator;
    //Map variable which hold the once test case data
    public static HashMap<String,String> eachTestCaseData =new HashMap<String, String>();

    public static void CamsShiftManagementTest() throws IOException, Exception {
        ExcelSheet exl = new ExcelSheet();

        int noOfRows = exl.totalrows("FLCSS_CAMS_ShiftManagement","Test_Scenarios");

        HashMap<String, String> eachTestCaseData = new HashMap<String, String>();

        String testCaseName = "";

        for (int i = 1; i <=noOfRows; i++) {

            eachTestCaseData = getEachTestCaseData(exl, "Test_Scenarios", i,"FLCSS_CAMS_ShiftManagement");
            dataObj = eachTestCaseData;

            testCaseName = "tcShiftManagement"+eachTestCaseData.get("TestCaseId").replace("-","");

            String executionType = CommonLibrary.getSettingsSheetInfo().get("Execution_Type");
            if ((eachTestCaseData.get("ExecutionStatus").equalsIgnoreCase("Yes")) && ( eachTestCaseData.get("TestCaseType").contains(executionType)|| 
            		executionType.equalsIgnoreCase("All"))){
                Method testMethod = null;
                try {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>" + eachTestCaseData.get("TestCaseId")
                            + "</b>" + ": Test Case Execution is started....................... <br>"
                            + "Test case description: " + eachTestCaseData.get("TestCaseDesc"), LogStatus.INFO, false);

                    //check for class and method are found for the test case
                    try
                    {
                        Class<?> c = Class.forName("features.CamsShiftManagement");
                        testMethod= c.getMethod(testCaseName);
                        //CommonLibrary.loginSblWithTestScenarioSpecificUser(eachTestCaseData);
                        TestCaseLibrary.Get_TestCase_Instance().executeTC(testMethod);
                        // CommonLibrary.logoutSiebelApplicaiton();
                    } catch (ClassNotFoundException e) {

                        e.printStackTrace();
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test method "+ testCaseName +" is not found", LogStatus.FAIL,false);
                    }
                 //   ReportLibrary.endTest();

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
                                FunctionLibrary.objDriver.findElement(By.xpath(okBtn)).click();
                                
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
   
    public static void tcShiftManagementSMGT001_006(){
        try {
           
            CommonLibrary.loginSiebelAppFirstUser(dataObj);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: CSR should login siebel. Actual: CSR is logged in siebel",
                    LogStatus.PASS, true);
              

            clickObject(csrCloseoutMenu, "", "Click CSR CloseOut menu");
            waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathTourOfDutyTable,
                    csrCloseoutMenu, 6, 30);
            
            waitForObject.until(ExpectedConditions.elementToBeClickable(getElement(goBtn)));
            String item1 ;
            String item2;
            for(iterator=0;iterator<=5;iterator++)
            {
            	
	    		FunctionLibrary.clickObject(endDateColumn,"","Click end time column");
	    		FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(sortBtn, 
	    				endDateColumn, 2, 8);
	    		FunctionLibrary.clickObject(sortBtn,"","Click sort button");
	    		Thread.sleep(2000);
	    		item1 = FunctionLibrary.getWebTableCellData(xpathTourOfDutyTable, 2, 4, "Get end time of item1");
	    		
	           item2 = FunctionLibrary.getWebTableCellData(xpathTourOfDutyTable, 3, 4, "Get end time of item2");
	           org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyy hh:mm:ss a");
	           DateTime endTimeOfItem1 = formatter.parseDateTime(item1);
	           DateTime endTimeOfItem2 = formatter.parseDateTime(item2);
		       int rowNumWithOpenStatus = FunctionLibrary.getRowNumberFromWebTable(xpathTourOfDutyTable, "OPEN","Get row number with open status");
	           if( endTimeOfItem1.compareTo(endTimeOfItem2)==1 && rowNumWithOpenStatus==1)
	           {
	        	   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Last tod should be closed. Acutal: It is closed and at "+item1, 
	        			   LogStatus.PASS, true);
	        	   break;
	           	
	           }
            }
           Thread.sleep(2000); 
           FunctionLibrary.verifyWebTableCellData(todTable, 1, 9, "OPEN", "Verify current tod status", true);
           FunctionLibrary.verifyWebTableCellData(todTable, 2, 9, "CLOSED", "Verify previous tod status", false);
            
              
            searchForTod("Status", "OPEN");
            
            String todId = getWebTableCellData(xpathTourOfDutyTable,1,1,"Get todid");
            if(todId!="")
            {
            	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Extected: A Tod id should be created with OPEN status. Actual: Tod is created: " +todId,
                        LogStatus.PASS, true);
            }else
            {
            	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Extected: A Tod id should be created. Actual: Tod is not created",
                        LogStatus.FAIL, true);
            }
            clickObject("//a[text()='Accounts']","","Click on deposit verification tab");

            try{
               WebDriverWait wait2 = new WebDriverWait(objDriver,20);
                wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(okBtn)));
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>Test SMGT-006</b> is passed. Expected: Popup is showing for Opening balance is "
                		+ "mandate when the customer is not entered the opening balance. <br>Actual: Popup is dispalyed", LogStatus.PASS, true);
                objDriver.findElement(By.xpath(okBtn)).click();

                logoutSiebelApplicaiton();
               }catch(TimeoutException e1)
            {
            	
            	closeSiebelInErrorCase();
                logoutSiebelApplicaiton();

            }
                                
        }
        catch(Exception e)
        {
            String Errormsg=e.getMessage();

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Test is failed: <br>"+Errormsg, LogStatus.FAIL, true);
        }//End of Catch

    }
  
    public static void tcShiftManagementSMGT004(){
        try {
        	 String accountNum = dataObj.get("AccountNumber");
            CommonLibrary.loginSiebelAppFirstUser(dataObj);

            String todId=addOpeningBalanceAndReturnTodId(dataObj.get("Opening Shift Balance"));
            
           	CommonLibrary.searchForAccount(accountNum);
           	
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
        
            
            String paymentTypePaidDepositedAmounts [] = dataObj.get("Payment Type:Paid:Deposited").split(",") ;
            String amountToBePaid;
            String payType;
            String []amountPaidAndProcessId = null ;
            for(int iterator=0;iterator<=paymentTypePaidDepositedAmounts.length-1;iterator++)
            {
           	 payType = paymentTypePaidDepositedAmounts[iterator].split(":")[0];
           	 amountToBePaid = paymentTypePaidDepositedAmounts[iterator].split(":")[1];
           	 amountPaidAndProcessId = makePaymentWithReplenishment(amountToBePaid,"TOTALDEPOSIT",payType.trim());
            }
            
            verifyFinancialChkBox(todId, dataObj.get("Financial On Previous Day"));
            
            
            logoutSiebelApplicaiton();
            
            Calendar cal = Calendar.getInstance();
    	    cal.add(Calendar.DATE, -1);
    	    String yesterdayDate = new SimpleDateFormat("dd-MMM-yyyy").format(cal.getTime());
    	    
            String sqlToMakeTodToPreviousDay = "update siebel.S_TMSHT_ITEM set BILLING_DT='"+ yesterdayDate +"' where row_id='"+todId+"'";
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Execuing sql query to make TOD "+todId+ " is from previous day. <br> Sql is: "
            			+ sqlToMakeTodToPreviousDay, LogStatus.INFO, false);
            
            executeSqlForUpdate(sqlToMakeTodToPreviousDay);
            

            //BLOCK START: Verify tour of duty message on user dashboard
            {  
	           
	            String Desc;
	            try {
	                //FunctionLibrary.Wait_For_Object = new WebDriverWait(FunctionLibrary.ObjDriver, 60);
	                waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loginUsernameTxtBox)));
	            } catch (Exception e) {
	                System.out.println("Problem in Home Page");
	            }
	
	            //Enter User Name
	            Desc = "Entering UserName on UserName textbox";
	            FunctionLibrary.setText(loginUsernameTxtBox, dataObj.get("First LoginUserName"), Desc);
	            //Enter Password
	            Desc = "Entering Password into password field";
	            setText(loginPasswordTxtBox, dataObj.get("First LoginPassword"), Desc);
	            //Click on Sign-in Button
	            Desc = "Clicking on Sign in Button";
	            clickObject(signInBtn, "", "Click sign in button");
	            WebDriverWait wait1 = new WebDriverWait(objDriver, 120);
	            wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Your tour of duty is not open, please reach your supervisor')]")));
	            sblLoginStatus = "Success";
	
	            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Amount deposited by login user has mismatch. So unable to see dash board. Tour of duty error message is showing up on dashboard."
	                    ,LogStatus.PASS,true);
	
	            CommonLibrary.logoutSiebelApplicaiton();

            }
            //BLOCK END: Verify tour of duty message on user dashboard
       
            String sqlToClearTod = "UPDATE siebel.S_TMSHT_ITEM SET BILLABLE_FLG='N' WHERE BILLABLE_FLG='Y'";
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Execuing sql query to clear tour of duty. Sql is " + sqlToClearTod, LogStatus.INFO, false);
            
            executeSqlForUpdate(sqlToClearTod);
            
            loginSiebelAppFirstUser(dataObj);
            
            WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver,180);
            try{
                
            	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(homePageVerificationTxt)));
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "First user tour of duty error message is not displaying now. CSR can see dashboard"
                        , LogStatus.PASS, true);
            }
            catch (TimeoutException e1) {
                
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Your tour of duty is not open, please reach your supervisor')]")));
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "User still getting Tour of duty pending error message"
                            , LogStatus.FAIL, true);
                
            }
          logoutSiebelApplicaiton();
       
            
            
            
        }
        catch(Exception e)
        {
            String Errormsg=e.getMessage();

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Test is failed: <br>"+Errormsg, LogStatus.FAIL, true);
            closeSiebelInErrorCase();
        }//End of Catch

    }
   
    
    public static void tcShiftManagementSMGT009_029(){
        try {
        	 String accountNum = dataObj.get("AccountNumber");
            CommonLibrary.loginSiebelAppFirstUser(dataObj);

            String todId=addOpeningBalanceAndReturnTodId(dataObj.get("Opening Shift Balance"));
            
           	CommonLibrary.searchForAccount(accountNum);
           	
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
        
            
            String paymentTypePaidDepositedAmounts [] = dataObj.get("Payment Type:Paid:Deposited").split(",") ;
            String amountToBePaid;
            String payType;
            String []amountPaidAndProcessId = null ;
            for(int iterator=0;iterator<=paymentTypePaidDepositedAmounts.length-1;iterator++)
            {
           	 payType = paymentTypePaidDepositedAmounts[iterator].split(":")[0];
           	 amountToBePaid = paymentTypePaidDepositedAmounts[iterator].split(":")[1];
           	 amountPaidAndProcessId = makePaymentWithReplenishment(amountToBePaid,"TOTALDEPOSIT",payType.trim());
            }
            
        
            boolean isMisMatchCheckShouldBePresent;
            if(dataObj.get("VerifyDepositMisMatch").startsWith("Y"))
            {
            	isMisMatchCheckShouldBePresent = true;
            }
            else
            {
            	isMisMatchCheckShouldBePresent = false;
            }
            
            enterDollarAmountAndSubmitDeposite(todId, paymentTypePaidDepositedAmounts, isMisMatchCheckShouldBePresent);
            
            logoutSiebelApplicaiton();
            loginSiebelAppSecondUser(dataObj);
            
       	 	closeDepositAsSupervisor(todId, paymentTypePaidDepositedAmounts,isMisMatchCheckShouldBePresent);
            logoutSiebelApplicaiton();

        }
        catch(Exception e)
        {
            String Errormsg=e.getMessage();

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Test is failed: <br>"+Errormsg, LogStatus.FAIL, true);
            closeSiebelInErrorCase();
        }//End of Catch

    }
    
    public static void tcShiftManagementSMGT011_026(){
        try {
        	 String accountNum = dataObj.get("AccountNumber");
            CommonLibrary.loginSiebelAppFirstUser(dataObj);

            String todId=addOpeningBalanceAndReturnTodId(dataObj.get("Opening Shift Balance"));
            
           	CommonLibrary.searchForAccount(accountNum);
           	
            //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
            
            //FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 20, ChallangeQA2Dropdown);
             
             String paymentTypePaidDepositedAmounts [] = dataObj.get("Payment Type:Paid:Deposited").split(",") ;
             String amountToBePaid = "";
             String payType;
             String []amountPaidAndProcessId = null ;
             for(int iterator=0;iterator<=paymentTypePaidDepositedAmounts.length-1;iterator++)
             {
            	 payType = paymentTypePaidDepositedAmounts[iterator].split(":")[0];
            	 amountToBePaid = paymentTypePaidDepositedAmounts[iterator].split(":")[1];
            	 amountPaidAndProcessId = makePaymentWithReplenishment(amountToBePaid,"TOTALDEPOSIT",payType.trim());
             }
            
             
            
            
            
            
            String varianceDollarSetTo = dataObj.get("VarianceDollarSetTo");             
             String varianceDollarValue = dataObj.get("VarianceDollarValue");
             String variancePrecentageSetTo = dataObj.get("VariancePrecentageSetTo");
             String variancePercentageValue = dataObj.get("VariancePercentageValue");   
             //Set values: dollar set to, dollar value, percentage set to, percentage value
             setVectorInternalProcessParameters(varianceDollarSetTo, varianceDollarValue,variancePrecentageSetTo, variancePercentageValue);             
             
             int amountPaid = Integer.valueOf(amountPaidAndProcessId[0]);
             int dollarVairanceVal = Integer.valueOf(varianceDollarValue);
             // amountDepositingExceedsDollarVariance = dataObj.get("Csr Deposit");
             ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>Csr payments done for the amount 'System Amount' : "+ dataObj.get("System Deposit") + 
            		 ". And Csr is depositing exceeds the Dollar variance '"+ dollarVairanceVal +"' value : " + dataObj.get("Csr Deposit") +"<b>",  
            		 LogStatus.INFO, false);
             
            boolean isMisMatchCheckShouldBePresent;
            if(dataObj.get("VerifyDepositMisMatch").startsWith("Y"))
            {
            	isMisMatchCheckShouldBePresent = true;
            }
            else
            {
            	isMisMatchCheckShouldBePresent = false;
            }
            
            enterDollarAmountAndSubmitDeposite(todId, paymentTypePaidDepositedAmounts, isMisMatchCheckShouldBePresent);
            logoutSiebelApplicaiton();
            loginSiebelAppSecondUser(dataObj);
            
            
            depositVerification(todId, amountToBePaid);
            
       	 	closeDepositAsSupervisor(todId, paymentTypePaidDepositedAmounts,isMisMatchCheckShouldBePresent);
             
                        
            logoutSiebelApplicaiton();

        }
        catch(Exception e)
        {
            String Errormsg=e.getMessage();

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Test is failed: <br>"+Errormsg, LogStatus.FAIL, true);
            closeSiebelInErrorCase();
        }//End of Catch

    }

    public static void tcShiftManagementSMGT013(){
        try {
        	 String accountNum = dataObj.get("AccountNumber");
            CommonLibrary.loginSiebelAppFirstUser(dataObj);

            String todId=addOpeningBalanceAndReturnTodId(dataObj.get("Opening Shift Balance"));
            
           	CommonLibrary.searchForAccount(accountNum);
           	
            //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
          
            String paymentTypePaidDepositedAmounts [] = dataObj.get("Payment Type:Paid:Deposited").split(",") ;
            String amountToBePaid;
            String payType;
            String []amountPaidAndProcessId = null ;
            for(int iterator=0;iterator<=paymentTypePaidDepositedAmounts.length-1;iterator++)
            {
           	 payType = paymentTypePaidDepositedAmounts[iterator].split(":")[0];
           	 amountToBePaid = paymentTypePaidDepositedAmounts[iterator].split(":")[1];
           	 amountPaidAndProcessId = makePaymentWithReplenishment(amountToBePaid,"TOTALDEPOSIT",payType.trim());
            }
            
        
            
            
             String varianceDollarSetTo = dataObj.get("VarianceDollarSetTo");             
             String varianceDollarValue = dataObj.get("VarianceDollarValue");
             String variancePrecentageSetTo = dataObj.get("VariancePrecentageSetTo");
             String variancePercentageValue = dataObj.get("VariancePercentageValue");   
             //Set values: dollar set to, dollar value, percentage set to, percentage value
             setVectorInternalProcessParameters(varianceDollarSetTo, varianceDollarValue,variancePrecentageSetTo, variancePercentageValue);             
             
             int amountPaid = Integer.valueOf(amountPaidAndProcessId[0]);
             int addAmountWithPercentage = (Integer.valueOf(amountPaid)/ 100)*2;
             int amountPaidExceedPercentageVariance = amountPaid +addAmountWithPercentage+2;
             ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>Csr payments done for the amount 'System Amount' : "+amountPaid + 
            		 ". And Csr is depositing more than Percentage variance '"+ variancePercentageValue +"' value : " + amountPaidExceedPercentageVariance +"<b>",  
            		 LogStatus.INFO, false);
             
            boolean isMisMatchCheckShouldBePresent;
            if(dataObj.get("VerifyDepositMisMatch").startsWith("Y"))
            {
            	isMisMatchCheckShouldBePresent = true;
            }
            else
            {
            	isMisMatchCheckShouldBePresent = false;
            }
           // String paymentTypePaidDepositedAmounts [] = dataObj.get("Payment Type:Paid:Deposited").split(",") ;
            enterDollarAmountAndSubmitDeposite(todId, paymentTypePaidDepositedAmounts, isMisMatchCheckShouldBePresent);
            
            logoutSiebelApplicaiton();
            loginSiebelAppSecondUser(dataObj);
       	 	closeDepositAsSupervisor(todId,paymentTypePaidDepositedAmounts,isMisMatchCheckShouldBePresent);
                        
            logoutSiebelApplicaiton();

        }
        catch(Exception e)
        {
            String Errormsg=e.getMessage();

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Test is failed: <br>"+Errormsg, LogStatus.FAIL, true);
            closeSiebelInErrorCase();
        }//End of Catch

    }
   
    public static void tcShiftManagementSMGT021_27_28_39(){
        try {
        	
        	 String accountNum = dataObj.get("AccountNumber");
        	
            String amount = dataObj.get("Payment Amount");         
       
            CommonLibrary.loginSiebelAppFirstUser(dataObj);
            
            String todId=addOpeningBalanceAndReturnTodId("1");
            
           	CommonLibrary.searchForAccount(accountNum);
           	
            //FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
           /* //FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 20, ChallangeQA2Dropdown);
            
           
            String []amountPaidAndProcessId = makePaymentWithReplenishment(amount,"TOTALDEPOSIT","CASH");
            amount = amountPaidAndProcessId[0];
            //amount is correct value, depositMismatchAmount is in-correct value
            String depositMismatchAmount = String.valueOf(Integer.valueOf(amount)+10);            		
            */
            
            String paymentTypePaidDepositedAmounts [] = dataObj.get("Payment Type:Paid:Deposited").split(",") ;
            String amountToBePaid = "";
            String payType;
            String []amountPaidAndProcessId = null ;
            for(int iterator=0;iterator<=paymentTypePaidDepositedAmounts.length-1;iterator++)
            {
           	 payType = paymentTypePaidDepositedAmounts[iterator].split(":")[0];
           	 amountToBePaid = paymentTypePaidDepositedAmounts[iterator].split(":")[1];
           	 amountPaidAndProcessId = makePaymentWithReplenishment(amountToBePaid,"TOTALDEPOSIT",payType.trim());
            }
            
        
            boolean isMisMatchCheckShouldBePresent;
			if(dataObj.get("VerifyDepositMisMatch").startsWith("Y"))
            {
            	isMisMatchCheckShouldBePresent = true;
            }
            else
            {
            	isMisMatchCheckShouldBePresent = false;
            }     
			//String paymentTypePaidDepositedAmounts [] = dataObj.get("Payment Type:Paid:Deposited").split(",") ;
            enterDollarAmountAndSubmitDeposite(todId, paymentTypePaidDepositedAmounts, isMisMatchCheckShouldBePresent);  
            CommonLibrary.logoutSiebelApplicaiton();
            //BLOCK START: Verify tour of duty message on user dashboard
            {  
	           
	            String Desc;
	            try {
	                //FunctionLibrary.Wait_For_Object = new WebDriverWait(FunctionLibrary.ObjDriver, 60);
	                waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loginUsernameTxtBox)));
	            } catch (Exception e) {
	                System.out.println("Problem in Home Page");
	            }
	
	            //Enter User Name
	            Desc = "Entering UserName on UserName textbox";
	            FunctionLibrary.setText(loginUsernameTxtBox, dataObj.get("First LoginUserName"), Desc);
	            //Enter Password
	            Desc = "Entering Password into password field";
	            setText(loginPasswordTxtBox, dataObj.get("First LoginPassword"), Desc);
	            //Click on Sign-in Button
	            Desc = "Clicking on Sign in Button";
	            clickObject(signInBtn, "", "Click sign in button");
	            WebDriverWait wait1 = new WebDriverWait(objDriver, 120);
	            wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Your tour of duty is not open, please reach your supervisor')]")));
	            sblLoginStatus = "Success";
	
	            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"<b>Test case SMGT-027</b><br>Amount deposited by login user has mismatch. So unable to see dash board. Tour of duty error message is showing up on dashboard."
	                    ,LogStatus.PASS,true);
	
	            CommonLibrary.logoutSiebelApplicaiton();

            }
            //BLOCK END: Verify tour of duty message on user dashboard
                        
       	  	CommonLibrary.loginSiebelAppSecondUser(dataObj);
       	  	
            depositVerification(todId, amountToBePaid);
            
            boolean isClosed = closeDepositAsSupervisorAfterVerification(todId, paymentTypePaidDepositedAmounts,isMisMatchCheckShouldBePresent);
            if(isClosed)
            {
            	 ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>Test case SMGT-028</b> <br>Expected: Tod should be closed. Actual: Tod is closed", LogStatus.PASS, false);
            }
            else
            {
            	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>Test case SMGT-028</b> Expected: Tod should be closed. Actual: Tod is not closed", LogStatus.FAIL, false);
            }
               
                        
            logoutSiebelApplicaiton();
                     
            loginSiebelAppFirstUser(dataObj);
            
            WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver,180);
            try{
                
            	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(homePageVerificationTxt)));
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "First user tour of duty error message is not displaying now. CSR can see dashboard"
                        , LogStatus.PASS, true);
            }
            catch (TimeoutException e1) {
                
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Your tour of duty is not open, please reach your supervisor')]")));
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "User still getting Tour of duty pending error message"
                            , LogStatus.FAIL, true);
                
            }
          logoutSiebelApplicaiton();
        }
        catch(Exception e)
        {
            String Errormsg=e.getMessage();

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Test is failed: <br>"+Errormsg, LogStatus.FAIL, true);
            closeSiebelInErrorCase();
        }//End of Catch

    }
    
    public static String[] makePaymentWithReplenishment(String amountToBeAdded, String paymentCategory, String paymentType) throws Exception
    {
	String [] amountPaidAndPaymentRefernceNum=  new String[2];

    String AccountBal=getText(accountBalTxtBox, "Account Balance");
    
    String existingAmount=AccountBal;
    existingAmount=existingAmount.replace("$","");
    existingAmount=existingAmount.replace(",","");
    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Existing  account balance " +
            existingAmount, LogStatus.INFO, true);

    clickObject(linkFinancial, "", "click on financials");
    waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(goBtnOnPaymentPage, linkFinancial, 10, 33);
    WebDriverWait wait = new WebDriverWait(objDriver, 50);
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(goBtnOnPaymentPage)));
    
    clickObject(accountReplenishment, "", "click on accountReplenishment");
    waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
            newBtnAmount, accountReplenishment, 2, 20 );
    
    clickObject(newBtnAmount, "", "click on newBtnAmount");
    
    waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(amonutTxtBox, newBtnAmount, 3, 20);
    
    //Thread.sleep(2000);
    wait.until(ExpectedConditions.attributeToBe(getElement(amonutTxtBox), "aria-readonly", "false"));
    amountPaidAndPaymentRefernceNum[0] = amountToBeAdded;
    setText(amonutTxtBox, amountToBeAdded, "Enter Amount");
   
    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Adding amount is " +
			amountToBeAdded +" with pay type "+ paymentType, LogStatus.INFO, true);   
    
    clickObject(replenishmentSave, "", "click on replenishmentSave");
    scrollDowntoElement(newPaymentList, "Scroll down");
    clickObject(newPaymentList, "", "click on newPaymentList");
    waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(saveRebillInfo, 
    		newPaymentList, 2, 20);
    scrollDowntoElement(saveRebillInfo, "Scroll down");
    
    FunctionLibrary.selectDropDownListByValue(xpathPaymentType, paymentType.trim(), "Selecting payment type" + paymentType.trim());
   
    
    if(paymentType.equalsIgnoreCase("CHECK"))
    {
    	String CheckNo=Integer.toString(FunctionLibrary.randomNumberWithFiveDigit());
        Desc="Enter Check number";
        FunctionLibrary.setText(checkPay, CheckNo, Desc);
    }
    if(paymentType.equalsIgnoreCase("SAVINGS")|| paymentType.equalsIgnoreCase("CHECKING"))
    {
    	dataObj.put("ACHBankRouting","3453453451");
    	dataObj.put("ACHBankNumber","122235821");
    	String SavingNumber=dataObj.get("ACHBankRouting");
        String RoutingNumber=dataObj.get("ACHBankNumber");
        FunctionLibrary.setText("//input[@id='BankNum']",SavingNumber,"Enter banking number");
        FunctionLibrary.setText("//input[@id='RoutingNum']", RoutingNumber, "Enter routing number");
    }
    if(paymentType.equalsIgnoreCase("VISA")|| paymentType.equalsIgnoreCase("MASTERCARD") || paymentType.equalsIgnoreCase("DISCOVER")||paymentType.equalsIgnoreCase("AMEX"))
    {
    	if(paymentType.equalsIgnoreCase("VISA")){dataObj.put("CreditCardNo","4159282222222221");}
    	if(paymentType.equalsIgnoreCase("MASTERCARD")){dataObj.put("CreditCardNo","5472063333333330");}
    	if(paymentType.equalsIgnoreCase("DISCOVER")){dataObj.put("CreditCardNo","6011993333333334");}
    	if(paymentType.equalsIgnoreCase("AMEX")){dataObj.put("CreditCardNo","341234111111111");}
    	dataObj.put("ExpMonth","12");
    	dataObj.put("ExpYear","2024");
    	
        String CreditCard= dataObj.get("CreditCardNo");
        String ExpMonth=dataObj.get("ExpMonth");
        String ExpYear=dataObj.get("ExpYear");

        Desc="Enter CC Details";

        System.out.println(CreditCard);
        FunctionLibrary.setText(ccField,CreditCard,"Enter credit card number");
        Desc="Selecting Expiry Month";
       
        selectDropDownList(creditCardExpMpnth,ExpMonth,Desc);
        Desc="Selecting Expiry Year";
        selectDropDownList(creditCardExpYear,ExpYear,Desc);

    }
    	
 
    clickObject(saveRebillInfo, "", "click on saveRebillInfo");
    if(paymentType.equalsIgnoreCase("SAVINGS"))
    	
    {
    	CamsSiebelAccountMaintenance.handleAlertIfPresent(20);
    }
    waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@title='"+paymentType+"']")));
    
    clickObject(savePaymentList, "", "click on savePaymentList");
  /*  if(paymentType.equalsIgnoreCase("SAVINGS"))
    	
    {
    	CamsSiebelAccountMaintenance.handleAlertIfPresent(20);
    }*/
    
    waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(processPay, savePaymentList, 5, 36);
    clickObject(processPay, "", "click on processPay");
 /*   if(paymentType.equalsIgnoreCase("SAVINGS"))
    	
    {
    	CamsSiebelAccountMaintenance.handleAlertIfPresent(20);
    }*/
    try {

        WebDriverWait wait2 = new WebDriverWait(objDriver,50);
        wait2.until(ExpectedConditions.alertIsPresent());
        Alert alert = objDriver.switchTo().alert();
        String alertText=alert.getText();
        System.out.println(alert.getText());
        alert.accept();
        if(alertText.contains("Transaction Processed"))
        {
        	//get payment reference number
            String[] paymentRefNumber = alertText.split(":");
            System.out.println(paymentRefNumber[1]);	
            amountPaidAndPaymentRefernceNum[1]=paymentRefNumber[1];
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Payment is proceessed and reference number is: "+ paymentRefNumber[1], 
            		LogStatus.PASS, true);
            
            Thread.sleep(4);
            String finalBalAfterPayment=getText(accountBalTxtBox, "Account Balance");
            
            
            finalBalAfterPayment=finalBalAfterPayment.replace("$","");
            finalBalAfterPayment=finalBalAfterPayment.replace(",","");
            
            if(Double.valueOf(finalBalAfterPayment)==Double.valueOf(amountPaidAndPaymentRefernceNum[0])+Double.valueOf(existingAmount))
            {
            	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Updated account balance " +
            			finalBalAfterPayment, LogStatus.PASS, true);   
            }
            else
            {
            	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Existing balance: " +existingAmount + ". Added amount "+ amountPaidAndPaymentRefernceNum[0]+
            			". Final amount showing up = "+ finalBalAfterPayment, LogStatus.FAIL, true);	
            }
            
        }
        else
        {
        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Payment is failed. reference number alert is not displaying", 
            		LogStatus.FAIL, true);
        }

        //Thread.sleep(3000);
        
        

    }
    catch (Exception e) {
        //exception handling
    }


return amountPaidAndPaymentRefernceNum;
}
    
    public static void setVectorInternalProcessParameters(String variancedollarSetTo, String varianceDollarVal, String variancePercentSetTo, String variancePercentVal)
    {
    	CommonLibrary.logoutSiebelApplicaiton();
    	CommonLibrary.loginSiebelAppSecondUser(dataObj);
    	clickSiteMap();
        
        WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 90);
         FunctionLibrary.clickObject(siteMapFilterTxtBox, "", "Clicking Site Map filter field");
         FunctionLibrary.setText(siteMapFilterTxtBox, "Vector Internal Process Parameters", "Search for : Vector Internal Process Parameters");
         FunctionLibrary.clickObject(vectorInternalProcessParameters, "", "Clicking Vector Internal Process Parameters link");
         
         wait.until(ExpectedConditions.attributeToBe(getElement(queryTxtBox), "aria-readonly", "false"));
         FunctionLibrary.setText(queryTxtBox, "Type", "Enter Type is search type field");
                  
         //set CSR_PERCENT_VARIANCE_ENABLE
         FunctionLibrary.setText(queryValueTxtBox, "CSR_PERCENT_VARIANCE_ENABLE", "Enter value in search field");
         FunctionLibrary.clickObject(goBtn, "", "Click go button");
         FunctionLibrary.clickObject(paramValueElement, "","");
         
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(paramValTxtBox,
        		paramValueElement, 2, 20);
         
         FunctionLibrary.setText(paramValTxtBox, variancePercentSetTo, "Enter value in search field");
         ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Screenshot with CSR_PERCENT_VARIANCE_ENABLE",
        		 LogStatus.INFO, true);
         
         
       //set CSR_DOLLAR_VARIANCE_ENABLE 
         FunctionLibrary.setText(queryValueTxtBox, "CSR_DOLLAR_VARIANCE_ENABLE", "Enter value in search field");
         FunctionLibrary.clickObject(goBtn, "", "Click go button");
         FunctionLibrary.clickObject(paramValueElement, "","");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(paramValTxtBox,
         		paramValueElement, 2, 20);
         FunctionLibrary.setText(paramValTxtBox, variancedollarSetTo, "Enter value in search field");
         ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Screenshot with CSR_DOLLAR_VARIANCE_ENABLE",
        		 LogStatus.INFO, true);
         
       //set CSR_VARIANCE_PERCENT
         FunctionLibrary.setText(queryValueTxtBox, "CSR_VARIANCE_PERCENT", "Enter value in search field");
         FunctionLibrary.clickObject(goBtn, "", "Click go button");
         FunctionLibrary.clickObject(paramValueElement, "","");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(paramValTxtBox,
         		paramValueElement, 2, 20);
         FunctionLibrary.setText(paramValTxtBox, variancePercentVal, "Enter value in search field");

         ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Screenshot with CSR_VARIANCE_PERCENT",
        		 LogStatus.INFO, true);
        
       //set CSR_VARIANCE_DOLLAR 
         FunctionLibrary.setText(queryValueTxtBox, "CSR_VARIANCE_DOLLAR", "Enter value in search field");
         FunctionLibrary.clickObject(goBtn, "", "Click go button");
         FunctionLibrary.clickObject(paramValueElement, "","");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(paramValTxtBox,
          		paramValueElement, 2, 20);
         FunctionLibrary.setText(paramValTxtBox, varianceDollarVal, "Enter value in search field");
         
         ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Variance dollar set to : " + variancedollarSetTo + ". Variance dollar value set to: "+
        		 varianceDollarVal +". Variance percentage set to : "+ variancePercentSetTo+ ". Variance percentage value set to :"+variancePercentVal,
        		 LogStatus.INFO, true);
         
         CommonLibrary.logoutSiebelApplicaiton();
     	CommonLibrary.loginSiebelAppFirstUser(dataObj);
         
    }
    
    public static boolean closeDepositAsSupervisor(String todId, String []paymentTypePaidDepositedAmounts, boolean isWithMismatch) throws Exception
    
    {
    	boolean isTodClosed = false;
    	
    	 clickObject(csrCloseoutMenu, "", "Click CSR CloseOut menu");
         waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(goBtn,
                 csrCloseoutMenu, 6, 30);
         
         
         searchForTod("Status", "OPEN");
        
         clickObject(openingBalElement,"","Click Opening balance element");
         
         
        // clickObject(openingBalElement,"","Click the opening balance element");
        String openingBalOfSupervisorFieldType = objDriver.findElement(By.xpath(openingBalTxtBox)).getAttribute("aria-readonly");
         if(openingBalOfSupervisorFieldType==null)
         {openingBalOfSupervisorFieldType="false";};
         if(openingBalOfSupervisorFieldType.contains("false"))
         {
        	 setText(openingBalTxtBox,"1","Entering opening balance");
             
         }        
         clickObject(supervisorCloseOutTab,"","Click supervisor closer tab");
        waitForObject.until(ExpectedConditions.elementToBeClickable(getElement(queryBtn)));
        
        if(dataObj.get("TestCaseId").contains("SMGT-009_029"))
        {
        	 String todSearchStatus = dataObj.get("TodSearchWithStatus");
             todSearchStatus = todSearchStatus.replace("\n","");
	
	          String []searchWithStatus = todSearchStatus.split(",");
	           
     		
	          for (int iterator1 =0;iterator1<=searchWithStatus.length-1;iterator1++)
	          {
	                         
	        	  ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "SMGT_029: Search tod items with status - " + searchWithStatus[iterator1], LogStatus.INFO, false);
	        		
	            clickObject(tourOfDutyqueryBtn,"","Click tour of duty query");       
	        	waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(todTxtBox, 
	        			tourOfDutyqueryBtn, 5, 21);
	        	clickObject(storeIdElement, "","Click storeIdTxtBox element");
	        	setText(storeIdTxtBox, "10","Enter StoreId");
	        	 clickObject(todStatusElement,"","Click status element");  
	        	 setText(todStatusTxtBox, searchWithStatus[iterator1],"Enter tod status"); 
	        	 clickObject(todGoBtn, "", "Click tod go button");
	        	 Thread.sleep(3000);
	        	 int rowCount = getWebTableRowCount(xpathTourOfDutyTable,"","Tour of duty table");
	        	 String todIdInFristRow = objDriver.findElement(By.xpath(todElement1)).getText();
	        	 
	        	 if(rowCount>=2 && todIdInFristRow.length()>1)
	        	 {
	        		 ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "SMGT_029: Expected: Multiple tod items are dispalying with status - " + searchWithStatus[iterator1] +
	        				 "<br> Actual: Dispalyed", LogStatus.PASS, true);
	        		 
	        	 }
	        	 else
	        	 {
	        		 ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "SMGT_029: Expected: Multiple tod items are dispalying with status - " + searchWithStatus[iterator1] +
	        				 "<br> Actual: Not Dispalyed", LogStatus.FAIL, true);	        		 
	        	 }
	          }
        }
        
        
        
        
        searchForTod("TOD Id", todId);
      
      
      try {
		scrollDowntoElement(csrDepositCashAmount, "Scroll down to Csr deposit cash amount field");
	} catch (InterruptedException e) {
		
		e.printStackTrace();
	}
      
      
      String amountDeposited;
      String amountPaid;
 
      Double varianceAmount;
      for(int iterator=0;iterator<=paymentTypePaidDepositedAmounts.length-1;iterator++)
      {  
     	String payType = paymentTypePaidDepositedAmounts[iterator].split(":")[0].trim();
     	amountPaid = paymentTypePaidDepositedAmounts[iterator].split(":")[1];
     	amountDeposited = paymentTypePaidDepositedAmounts[iterator].split(":")[2];
     	varianceAmount = Double.valueOf(amountPaid)-Double.valueOf(amountDeposited);
     	
    	String sysDepositTxtBoxDynamicProp = null;
		if(payType.contains("CASH")){sysDepositTxtBoxDynamicProp = "Syscashamt";}
       	if(payType.contains("CHECK")){sysDepositTxtBoxDynamicProp = "Syscheckamt";}
       	if(payType.contains("VISA")){sysDepositTxtBoxDynamicProp = "Sysccamt";}
       	if(payType.contains("MASTERCARD")){sysDepositTxtBoxDynamicProp = "SysMasterCCAmt";}
       	if(payType.contains("AMEX")){sysDepositTxtBoxDynamicProp = "SysAmexCCAmt";}
       	if(payType.contains("DISCOVER")){sysDepositTxtBoxDynamicProp = "SysDiscCCAmt";}
       	if(payType.contains("SAVINGS")){sysDepositTxtBoxDynamicProp = "SysSavingsCCAmt";}
       	if(payType.contains("CHECKING")){sysDepositTxtBoxDynamicProp = "SysCheckingsCCAmt";}
       	       	
       	String csrDepositTxtBoxDynamicProp = null;
		if(payType.contains("CASH")){csrDepositTxtBoxDynamicProp = "Csrcashamt";}
       	if(payType.contains("CHECK")){csrDepositTxtBoxDynamicProp = "Csrcheckamt";}
       	if(payType.contains("VISA")){csrDepositTxtBoxDynamicProp = "Csrccamt";}
       	if(payType.contains("MASTERCARD")){csrDepositTxtBoxDynamicProp = "CsrMasterCCAmt";}
       	if(payType.contains("AMEX")){csrDepositTxtBoxDynamicProp = "CsrAmexCCAmt";}
       	if(payType.contains("DISCOVER")){csrDepositTxtBoxDynamicProp = "CsrDiscCCAmt";}
       	if(payType.contains("SAVINGS")){csrDepositTxtBoxDynamicProp = "CsrSavingsCCAmt";}
       	if(payType.contains("CHECKING")){csrDepositTxtBoxDynamicProp = "CsrCheckingsCCAmt";}
     	
      	String varianceDepositTxtBoxDynamicProp = null;
    		if(payType.contains("CASH")){varianceDepositTxtBoxDynamicProp = "Variant CASH Amount";}
           	if(payType.contains("CHECK")){varianceDepositTxtBoxDynamicProp = "Variant CHK Amount";}
           	if(payType.contains("VISA")){varianceDepositTxtBoxDynamicProp = "Variant VISA Amount";}
           	if(payType.contains("MASTERCARD")){varianceDepositTxtBoxDynamicProp = "Variant MST Amount";}
           	if(payType.contains("AMEX")){varianceDepositTxtBoxDynamicProp = "Variant AMX Amount";}
           	if(payType.contains("DISCOVER")){varianceDepositTxtBoxDynamicProp = "Variant DISC Amount";}
           	if(payType.contains("SAVINGS")){varianceDepositTxtBoxDynamicProp = "Variant SAV Amount";}
           	if(payType.contains("CHECKING")){varianceDepositTxtBoxDynamicProp = "Variant CHKNG Amount";}
 
     	  verifyTextBoxValueWithDollars("//input[@aria-label='"+ sysDepositTxtBoxDynamicProp +"']", amountPaid, "Verify deposited amount",true);
          verifyTextBoxValueWithDollars("//input[@aria-label='"+ csrDepositTxtBoxDynamicProp +"']",amountDeposited, "Verify Csr paid amount",false);      
          verifyTextBoxValueWithDollars("//input[@aria-label='"+ varianceDepositTxtBoxDynamicProp +"']", String.valueOf(varianceAmount), "Verify Variance",false); 
      }
                
       
      try {
  		scrollUptoElement(goBtn, "Scroll up to Go button");
  	} catch (InterruptedException e) {
  		// TODO Auto-generated catch block
  		e.printStackTrace();
  	}
        
        
      FunctionLibrary.objDriver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
        
        if(isWithMismatch)
        {
        	try{
        		WebDriverWait wait1 = new WebDriverWait(objDriver, 2);
        		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(depositMismatchChkBox)));
        		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Diposit mismatch checkbox should be selected. Actual: Selected."
	                    , LogStatus.PASS, true);
        	}
        	catch(TimeoutException e1)
        	{
        		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Diposit mismatch checkbox should be selected. Actual:But it is not selected."
	                    , LogStatus.FAIL, true);
        	}
        
        }
        else
        {
        	try{
        		WebDriverWait wait1 = new WebDriverWait(objDriver, 2);
        		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(depositMismatchChkBox)));
        		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Diposit mismatch checkbox should not be selected. Actual: Selected."
	                    , LogStatus.FAIL, true);
        	}
        	catch(TimeoutException e1)
        	{
        		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Diposit mismatch checkbox should not be selected. Actual:Not selected."
	                    , LogStatus.PASS, true);
        	}
        
        }
        FunctionLibrary.objDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        clickObject(todStatusElement,"","Click tod status element");
        waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(todStatusTxtBox, 
        		todStatusElement, 2, 14);
        setText(todStatusTxtBox,"CLOSED","Set status value as CLOSED");
        
        clickObject(todStatusElement,"","Click tod status element");
          return isTodClosed;

    }
    
    public static boolean closeDepositAsSupervisorAfterVerification(String todId, String [] paymentTypePaidDepositedAmounts, boolean isWithMismatch)
    
    {
    	boolean isTodClosed = false;
    	
    	clickObject(supervisorCloseOutTab,"","Click supervisor closer tab");
    	waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(pdr55Btn, 
    			supervisorCloseOutTab, 5, 21);
        waitForObject.until(ExpectedConditions.elementToBeClickable(getElement(queryBtn)));
        
        searchForTod("TOD Id", todId);
        
        

        String amountDeposited;
        String amountPaid;
   
        Double varianceAmount;
        for(int iterator=0;iterator<=paymentTypePaidDepositedAmounts.length-1;iterator++)
        {
      	  
      	
      	  
       	 String payType = paymentTypePaidDepositedAmounts[iterator].split(":")[0].trim();
       	amountPaid = paymentTypePaidDepositedAmounts[iterator].split(":")[1];
       	amountDeposited = paymentTypePaidDepositedAmounts[iterator].split(":")[2];
       	varianceAmount = Double.valueOf(amountPaid)-Double.valueOf(amountDeposited);
       	
      	String sysDepositTxtBoxDynamicProp = null;
  		if(payType.contains("CASH")){sysDepositTxtBoxDynamicProp = "Syscashamt";}
         	if(payType.contains("CHECK")){sysDepositTxtBoxDynamicProp = "Syscheckamt";}
         	if(payType.contains("VISA")){sysDepositTxtBoxDynamicProp = "Sysccamt";}
         	if(payType.contains("MASTERCARD")){sysDepositTxtBoxDynamicProp = "SysMasterCCAmt";}
         	if(payType.contains("AMEX")){sysDepositTxtBoxDynamicProp = "SysAmexCCAmt";}
         	if(payType.contains("DISCOVER")){sysDepositTxtBoxDynamicProp = "SysDiscCCAmt";}
         	if(payType.contains("SAVINGS")){sysDepositTxtBoxDynamicProp = "SysSavingsCCAmt";}
         	if(payType.contains("CHECKING")){sysDepositTxtBoxDynamicProp = "SysCheckingsCCAmt";}
         	
         	
         	String csrDepositTxtBoxDynamicProp = null;
  		if(payType.contains("CASH")){csrDepositTxtBoxDynamicProp = "Csrcashamt";}
         	if(payType.contains("CHECK")){csrDepositTxtBoxDynamicProp = "Csrcheckamt";}
         	if(payType.contains("VISA")){csrDepositTxtBoxDynamicProp = "Csrccamt";}
         	if(payType.contains("MASTERCARD")){csrDepositTxtBoxDynamicProp = "CsrMasterCCAmt";}
         	if(payType.contains("AMEX")){csrDepositTxtBoxDynamicProp = "CsrAmexCCAmt";}
         	if(payType.contains("DISCOVER")){csrDepositTxtBoxDynamicProp = "CsrDiscCCAmt";}
         	if(payType.contains("SAVINGS")){csrDepositTxtBoxDynamicProp = "CsrSavingsCCAmt";}
         	if(payType.contains("CHECKING")){csrDepositTxtBoxDynamicProp = "CsrCheckingsCCAmt";}
       	
        	String varianceDepositTxtBoxDynamicProp = null;
      		if(payType.contains("CASH")){varianceDepositTxtBoxDynamicProp = "Variant CASH Amount";}
             	if(payType.contains("CHECK")){varianceDepositTxtBoxDynamicProp = "Variant CHK Amount";}
             	if(payType.contains("VISA")){varianceDepositTxtBoxDynamicProp = "Variant VISA Amount";}
             	if(payType.contains("MASTERCARD")){varianceDepositTxtBoxDynamicProp = "Variant MST Amount";}
             	if(payType.contains("AMEX")){varianceDepositTxtBoxDynamicProp = "Variant AMX Amount";}
             	if(payType.contains("DISCOVER")){varianceDepositTxtBoxDynamicProp = "Variant DISC Amount";}
             	if(payType.contains("SAVINGS")){varianceDepositTxtBoxDynamicProp = "Variant SAV Amount";}
             	if(payType.contains("CHECKING")){varianceDepositTxtBoxDynamicProp = "Variant CHKNG Amount";}
   
       	  verifyTextBoxValueWithDollars("//input[@aria-label='"+sysDepositTxtBoxDynamicProp+"']", amountPaid, "Verify System paid amount",true);
            verifyTextBoxValueWithDollars("//input[@aria-label='"+csrDepositTxtBoxDynamicProp+"']",amountDeposited, "Verify Csr deposited amount",false);      
            verifyTextBoxValueWithDollars("//input[@aria-label='"+varianceDepositTxtBoxDynamicProp+"']", String.valueOf(varianceAmount), "Verify Variance",false); 
        }
        

        
    /*    sss
        
      String systemDepositCashAmount = "//input[@aria-label='Syscashamt']";
      String csrDepositCashAmount = "//input[@aria-label='Csrcashamt']";
      String varianceCashAmount = "//input[@aria-label='Variant CASH Amount']";
      
      Double varianceAmount = Double.valueOf(csrDepositedAmount)-Double.valueOf(dataObj.get("Payment Amount"));
      
      try {
		scrollDowntoElement(csrDepositCashAmount, "Scroll down to Csr deposit cash amount field");
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      verifyTextBoxValueWithDollars(systemDepositCashAmount, dataObj.get("Payment Amount"), "Verify System deposit amount",true);
      verifyTextBoxValueWithDollars(csrDepositCashAmount,csrDepositedAmount, "Verify Csr paid amount",false);      
      verifyTextBoxValueWithDollars(varianceCashAmount, String.valueOf(varianceAmount), "Verify Variance",false);       
       */
      try {
  		scrollUptoElement(goBtn, "Scroll up to Go button");
  	} catch (InterruptedException e) {
  		// TODO Auto-generated catch block
  		e.printStackTrace();
  	}
        
        
        
        
        if(isWithMismatch)
        {
        	try{
        		WebDriverWait wait1 = new WebDriverWait(objDriver, 2);
        		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(depositMismatchChkBox)));
        		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Diposit mismatch checkbox should be selected. Actual: Selected."
	                    , LogStatus.PASS, true);
        	}
        	catch(TimeoutException e1)
        	{
        		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Diposit mismatch checkbox should be selected. Actual:But it is not selected."
	                    , LogStatus.FAIL, true);
        	}
        
        }
        else
        {
        	try{
        		WebDriverWait wait1 = new WebDriverWait(objDriver, 2);
        		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(depositMismatchChkBox)));
        		//highLightElement(ObjDriver, Get_Element(depositMismatchChkBox));
        		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Diposit mismatch checkbox should not be selected. Actual: Selected."
	                    , LogStatus.FAIL, true);
        	}
        	catch(TimeoutException e1)
        	{
        		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Diposit mismatch checkbox should not be selected. Actual:Not selected."
	                    , LogStatus.PASS, true);
        	}
        
        }
        
        clickObject(todStatusElement,"","Click tod status element");
        waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(todStatusTxtBox, 
        		todStatusElement, 2, 14);
        setText(todStatusTxtBox,"CLOSED","Set status value as CLOSED");
        
        clickObject(todStatusElement,"","Click tod status element");
        
        String todStatus = getText(todStatusTxtBox,"Get status of tour deposit");
       
        if(todStatus.contains("CLOSED")) {
            isTodClosed = true;
        }
       return isTodClosed;

    }
    
    public static void enterDollarAmountAndSubmitDeposite(String todId, String []paymentTypePaidDepositedAmounts, boolean isSubmittingWithMismatch)
    {
    	 clickObject(csrCloseoutMenu, "", "Click CSR CloseOut menu");
         waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(goBtn,
                 csrCloseoutMenu, 6, 30);
    
         searchForTod("TOD Id", todId);
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        if(dataObj.get("TestCaseId").equalsIgnoreCase("SMGT-021_27_28_39"))
        {
    		try{
        		WebDriverWait wait1 = new WebDriverWait(objDriver, 2);
        		wait1.until(ExpectedConditions.visibilityOf(getElement("//td[contains(@id,'_Financial')]//span[@class='siebui-icon-checkbox-checked']")));
        		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Financials checkbox should be selected. Actual: Selected."
	                    , LogStatus.PASS, true);
        	}
        	catch(TimeoutException e1)
        	{
        		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Financials checkbox should be selected. Actual:But it is not selected."
	                    , LogStatus.FAIL, true);
        	}
        	
    
        }
    	
        clickObject(newBtnCsrcloser,"","Click new csr closer button");
        
        waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(cashTxtBox, newBtnCsrcloser, 8, 20);

   	 try {
			Thread.sleep(4000);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}
        try{
        waitForObject.until(ExpectedConditions.attributeToBe(By.xpath("//input[@aria-label='Cash']"), "aria-readonly", "false"));
        }
        catch(StaleElementReferenceException e1)
        
        {
        	
             
        	
        }
        //setText(cashTxtBox,"80","Enter cash value");
        try {
			Thread.sleep(4000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        //String paymentTypePaidDepositedAmounts [] = dataObj.get("Payment Type:Paid:Deposited").split(",") ;
        String amountToBeDeposited;
        String payType;
        String amountTxtBoxDynamicProp = "";
       
        for(int iterator=0;iterator<=paymentTypePaidDepositedAmounts.length-1;iterator++)
        {
       	 payType = paymentTypePaidDepositedAmounts[iterator].split(":")[0].trim();
       	amountToBeDeposited = paymentTypePaidDepositedAmounts[iterator].split(":")[2];
       	
       	if(payType.contains("CASH")){amountTxtBoxDynamicProp = "Cash";}
       	if(payType.contains("CHECK")){amountTxtBoxDynamicProp = "Check Amount";}
       	if(payType.contains("VISA")){amountTxtBoxDynamicProp = "CC Amount";}
       	if(payType.contains("MASTERCARD")){amountTxtBoxDynamicProp = "CsrMasterCCAmt";}
       	if(payType.contains("AMEX")){amountTxtBoxDynamicProp = "CsrAmexCCAmt";}
       	if(payType.contains("DISCOVER")){amountTxtBoxDynamicProp = "CsrDiscCCAmt";}
       	if(payType.contains("SAVINGS")){amountTxtBoxDynamicProp = "CsrSavingsCCAmt";}
       	if(payType.contains("CHECKING")){amountTxtBoxDynamicProp = "CsrCheckingsCCAmt";}
    	
       	
       	setText("//input[@aria-label='"+amountTxtBoxDynamicProp+"']",String.valueOf(amountToBeDeposited),"Enter amount");
       	
       	try {
			Thread.sleep(8000);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

       	try{
            WebDriverWait wait3 = new WebDriverWait(objDriver,10);
            wait3.until(ExpectedConditions.alertIsPresent());
            Alert alert = objDriver.switchTo().alert();
            String alertText = alert.getText();
            System.out.println("Alert is displaying with text: " + alertText);
            alert.accept();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Alert is displaying with text: " + alertText,
                    LogStatus.INFO, false);

        }
        catch(Exception e)
        {
            System.out.println("alert is not displayed after total amount button click");
        }
       	try{
            WebDriverWait wait3 = new WebDriverWait(objDriver,10);
            wait3.until(ExpectedConditions.alertIsPresent());
            Alert alert = objDriver.switchTo().alert();
            String alertText = alert.getText();
            System.out.println("Alert is displaying with text: " + alertText);
            alert.accept();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Alert is displaying with text: " + alertText,
                    LogStatus.INFO, false);

        }
        catch(Exception e)
        {
            System.out.println("alert is not displayed after total amount button click");
        }
       	
    	if(payType.contains("CASH")){amountTxtBoxDynamicProp = "Cash";
    	setText(dollarsTxtBox,String.valueOf(String.valueOf(amountToBeDeposited)),"Enter dollars value");
    	try {
			Thread.sleep(6000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	  try{
              WebDriverWait wait3 = new WebDriverWait(objDriver,8);
              wait3.until(ExpectedConditions.alertIsPresent());
              Alert alert = objDriver.switchTo().alert();
              String alertText = alert.getText();
              System.out.println("Alert is displaying with text: " + alertText);
              alert.accept();
              ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Alert is displaying with text: " + alertText,
                      LogStatus.INFO, false);

          }
          catch(Exception e)
          {
              System.out.println("alert is not displayed after total amounnt button click");
          }
    	  
    	
    	}
    	
       	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Enter the "+payType+" amount as: "+ amountToBeDeposited,
                LogStatus.INFO, false);
        }
       
        
//        setText(dollarsTxtBox,String.valueOf(dollarAmount),"Enter dollars value");
       
        FunctionLibrary.clickObject(totalAmoutBtn, "", "Click  total  amount");
        try{
            WebDriverWait wait3 = new WebDriverWait(objDriver,8);
            wait3.until(ExpectedConditions.alertIsPresent());
            Alert alert = objDriver.switchTo().alert();
            String alertText = alert.getText();
            System.out.println("Alert is displaying with text: " + alertText);
            alert.accept();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Alert is displaying with text: " + alertText,
                    LogStatus.INFO, false);

        }
        catch(Exception e)
        {
            System.out.println("alert is not displayed after total amounnt button click");
        }
                
        try{
            WebDriverWait wait4 = new WebDriverWait(objDriver,8);
            wait4.until(ExpectedConditions.alertIsPresent());
            Alert alert = objDriver.switchTo().alert();
            String alertText = alert.getText();
            System.out.println("Alert is displaying with text: " + alertText);
            alert.accept();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Alert is displaying with text: " + alertText,
                    LogStatus.INFO, false);

        }
        catch(Exception e)
        {
            System.out.println("alert is not displayed after total amount button click ");
        }
                    
        clickObject(saveBtnCsrColouser,"","Click new csr closer button");          
        try{
            WebDriverWait wait5 = new WebDriverWait(objDriver,4);
            wait5.until(ExpectedConditions.alertIsPresent());
            Alert alert = objDriver.switchTo().alert();
            String alertText = alert.getText();
            System.out.println("Alert is displaying with text: " + alertText);
            alert.accept();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Alert is displaying with text: " + alertText,
                    LogStatus.INFO, false);
        }
        catch(Exception e)
        {
            System.out.println("alert is not displayed after save button click");
        }
             
        
        
        
       
        clickObject(submitDepositBtnCsrColouser,"","Click submit closer button");
        waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[text()='DEPOSITED']")));
     
        verifyWebTableCellData(xpathTourOfDutyTable,
                1,9,"DEPOSITED","Verify the status of TOUR of Duty", true); 
        String totalCashAmount = "//input[@aria-label='Total Cash Amount']";
        String amountShowingAsDeposited=getText(totalCashAmount,"Get saved total cash amount");
        objDriver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Amount deposited by login user: "+amountShowingAsDeposited,
                LogStatus.INFO,true);  
        if(isSubmittingWithMismatch)
        {
        		try{
            		WebDriverWait wait1 = new WebDriverWait(objDriver, 2);
            		wait1.until(ExpectedConditions.visibilityOf(getElement(depositMismatchChkBox)));
            		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Diposit mismatch checkbox should be selected. Actual: Selected."
    	                    , LogStatus.PASS, true);
            	}
            	catch(TimeoutException e1)
            	{
            		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Diposit mismatch checkbox should be selected. Actual:But it is not selected."
    	                    , LogStatus.FAIL, true);
            	}
        }
        else
        {
        	try{
        		WebDriverWait wait1 = new WebDriverWait(objDriver, 2);
        		wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(depositMismatchChkBox)));
        		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Diposit mismatch checkbox should not be selected. Actual: selected."
	                    , LogStatus.FAIL, true);
        	}
        	catch(TimeoutException e1)
        	{
        		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Diposit mismatch checkbox should not be selected. Actual:It is not selected."
	                    , LogStatus.PASS, true);
        	}
        	
            	
        }
        
        objDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }
   
    public static String addOpeningBalanceAndReturnTodId(String openingBalAmount) throws InterruptedException
    {
        clickObject(csrCloseoutMenu, "", "Click CSR CloseOut menu");
        waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(queryTxtBox,
                csrCloseoutMenu, 5, 60);
                   
        searchForTod("Status", "OPEN");
        
        int rownum = getRowNumberFromWebTable(xpathTourOfDutyTable,"OPEN","Get row number of open item");
        String todId = getWebTableCellData(xpathTourOfDutyTable,rownum,1,"Get Tod Id");
        System.out.println(todId);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Tour of Deposit Id: " + todId, LogStatus.INFO,true);
      	
        clickObject(openingBalElement,"","Click Opening balance element");
        String openingBalOfSupervisorFieldType = objDriver.findElement(By.xpath(openingBalTxtBox)).getAttribute("aria-readonly");
        if(openingBalOfSupervisorFieldType==null){openingBalOfSupervisorFieldType="false";};
        	CommonLibrary.logoutSiebelApplicaiton();
        	CommonLibrary.loginSiebelAppSecondUser(dataObj);
        	
        	 clickObject(csrCloseoutMenu, "", "Click CSR CloseOut menu");
             waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(queryTxtBox,
                     csrCloseoutMenu, 8, 60);
        	
             searchForTod("Status", "OPEN");
             clickObject(openingBalElement,"","Click Opening balance element");             
             openingBalOfSupervisorFieldType = objDriver.findElement(By.xpath(openingBalTxtBox)).getAttribute("aria-readonly");
             if(openingBalOfSupervisorFieldType==null)
             {openingBalOfSupervisorFieldType="false";};
             if(openingBalOfSupervisorFieldType.contains("false"))
             {
            	 setText(openingBalTxtBox,openingBalAmount,"Entering opening balance");
                 
             }
        	//closing the existing tod
        	clickObject(supervisorCloseOutTab,"","Click supervisor closer tab");
            waitForObject.until(ExpectedConditions.elementToBeClickable(getElement(queryBtn)));
            searchForTod("TOD Id", todId);
            clickObject(todStatusElement,"","Click tod status element");
            waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(todStatusTxtBox, 
            		todStatusElement, 2, 20);
            setText(todStatusTxtBox,"CLOSED","Close the current tod  to continue with the test case");
              ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Closed the existing tod to avoid the 'payments done earier by this user' to test the current"
            		+ " deposits " + todId,
                    LogStatus.INFO,true);
            
            logoutSiebelApplicaiton();
            loginSiebelAppFirstUser(dataObj);
              clickObject(csrCloseoutMenu, "", "Click CSR CloseOut menu");
            waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(queryTxtBox,
                    csrCloseoutMenu, 2, 30);
                       
            searchForTod("Status", "OPEN");      
            waitForObject.until(ExpectedConditions.attributeToBe(getElement(queryTxtBox), "aria-readonly", "false"));
           
            rownum =getRowNumberFromWebTable(xpathTourOfDutyTable,"OPEN","Get row number of open item");
            todId = getWebTableCellData(xpathTourOfDutyTable,rownum,1,"Get todid");
            System.out.println(todId);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Tour of Deposit Id: " + todId, LogStatus.INFO,true);
          	
            clickObject(openingBalElement,"","Click Opening balance element");
            setText(openingBalTxtBox,openingBalAmount,"Entering opening balance");
        
        return todId;
    }

    public static void depositVerification(String todId, String amount) throws InterruptedException
    {

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Logged  in as Supervisor user"
                ,LogStatus.INFO,false);

        clickObject(csrCloseoutMenu, "", "Click CSR CloseOut menu");
        waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(depositVerificationTab,
                csrCloseoutMenu, 5, 30);
        waitForObject.until(ExpectedConditions.elementToBeClickable(getElement(closeoutDepositMenuBtn)));

        searchForTod("Status", "OPEN");
        
        clickObject(openingBalElement,"","Click the opening balance element");
        
        waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(openingBalTxtBox, 
        		openingBalElement, 3, 30);
        
        String openingBalOfSupervisorFieldType = objDriver.findElement(By.xpath(openingBalTxtBox)).getAttribute("aria-readonly");
        if(openingBalOfSupervisorFieldType.contains("false"))
        {
            setText(openingBalTxtBox,dataObj.get("10"),"Entering opening balance");
        }

        clickObject(depositVerificationTab,"","Click deposit verification tab");
        
        Thread.sleep(4000);
             
       	searchForTod("TOD Id", todId);
       	int noOfVerifications = 0;
       	if(dataObj.get("TestCaseId").contains("SMGT-021_27_28_39"))
       	{
       		noOfVerifications=2;
       	}else
       	{
       		noOfVerifications = 1;
       	}
       	for(int iterator =1;iterator<=noOfVerifications;iterator++)
       	{
	        clickObject(depositsVerificationEdit,"","Click edit button");
	        Thread.sleep(4000);
	        waitForObject.until(ExpectedConditions.attributeToBe(By.xpath(dollarsTxtBox), "aria-readonly", "false"));
	        
	        if(iterator==1 && dataObj.get("TestCaseId").contains("SMGT-021_27_28_39"))
	        {
	        
	        	setText(dollarsTxtBox,String.valueOf(Integer.valueOf(amount)+10),"Enter dollars by supervisor");
	        }
	        else
	        {
	        	
	        	setText(dollarsTxtBox,amount,"Enter dollars by supervisor");	
	        }
	
	        try{
	            WebDriverWait wait2 = new WebDriverWait(objDriver,30);
	            wait2.until(ExpectedConditions.alertIsPresent());
	            Alert alert = objDriver.switchTo().alert();
	            String alertText = alert.getText();
	            System.out.println("Alert is displaying with text: " + alertText);
	            alert.accept();
	            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Alert is displaying with text: " + alertText,
	                    LogStatus.INFO, false);
	
	        }
	        catch(Exception e)
	        {
	            System.out.println("alert is not handled");
	        }
	        
	        FunctionLibrary.clickObject(totalAmoutBtn, "", "Click  total  amount");
	        try{
	            WebDriverWait wait3 = new WebDriverWait(objDriver,4);
	            wait3.until(ExpectedConditions.alertIsPresent());
	            Alert alert = objDriver.switchTo().alert();
	            String alertText = alert.getText();
	            System.out.println("Alert is displaying with text: " + alertText);
	            alert.accept();
	            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Alert is displaying with text: " + alertText,
	                    LogStatus.INFO, false);
	
	        }
	        catch(Exception e)
	        {
	            System.out.println("alert is not displayed");
	        }
	                
	        try{
	            WebDriverWait wait4 = new WebDriverWait(objDriver,4);
	            wait4.until(ExpectedConditions.alertIsPresent());
	            Alert alert = objDriver.switchTo().alert();
	            String alertText = alert.getText();
	            System.out.println("Alert is displaying with text: " + alertText);
	            alert.accept();
	            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Alert is displaying with text: " + alertText,
	                    LogStatus.INFO, false);
	
	        }
	        catch(Exception e)
	        {
	            System.out.println("alert is not displayed");
	        }
	
	        clickObject(verificationSubmitBtn,"","Click save button");
	        Thread.sleep(5000);
	        waitForObject.until(ExpectedConditions.attributeToBe(By.xpath(dollarsTxtBox), "aria-readonly", "true"));
	        
	        String verificationCount = getText(verificationCountTxtBox,
	                "Get the verification count");
	        if(iterator==1)
		     {
	        	if(dataObj.get("TestCaseId").contains("SMGT-011_026"))
	        	{
			        if(Integer.parseInt(verificationCount)!=0) {
			            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>Test case SMGT-026 </b> Expected: Verification count should increase. <br> Actual: Verification count is: "+verificationCount
			                    , LogStatus.PASS, true);
			        }
			        else
			        {
			        	 ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>Test case SMGT-026 </b> Expected: Verification count should increase. <br> Actual: Verification count is not increased"
			                     , LogStatus.FAIL, true);
			        }
	        	}
	        	if(dataObj.get("TestCaseId").contains("SMGT-021_27_28_39"))
	        	{
			        if(Integer.parseInt(verificationCount)!=0) {
			            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>Test case SMGT-021 </b> Expected: Verification count should increase. <br> Actual: Verification count is: "+verificationCount
			                    , LogStatus.PASS, true);
			        }
			        else
			        {
			        	 ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>Test case SMGT-021 </b> Expected: Verification count should increase. <br> Actual: Verification count is not increased"
			                     , LogStatus.FAIL, true);
			        }
	        	}
		     }
	        
	        if(iterator==2)
		     {
		        if(Integer.parseInt(verificationCount)==2) {
		            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>Test case SMGT-039 </b> Expected: Verification count should increase to 2. <br> Actual: Verification count is: "+verificationCount
		                    , LogStatus.PASS, true);
		        }
		        else
		        {
		        	 ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>Test case SMGT-039</b> Expected: Verification count should increase to 2. <br> Actual: Verification count is not equal to 2"
		                     , LogStatus.FAIL, true);
		        }
		        
		        
		        	String verificaitonSubmitBtnProp = FunctionLibrary.objDriver.findElement(By.xpath(verificationSubmitBtn)).getAttribute("disabled");
                    if (verificaitonSubmitBtnProp.contains("true"))
                    {                  

                    	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>Test case SMGT-039</b>: Expected: Verification buttons disabled <br> Actual: Vefication button is disabled", LogStatus.PASS, true);

                    }
                    else
                    {
		        	 ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>Test case SMGT-039</b>: Expected: Verification buttons disabled <br> Actual: Vefication button is not disabled", LogStatus.FAIL, true);
		        }
		        
		        
		     }
       	}
    }
    
    public static void searchForTod(String type, String value) 
    {
    	waitForObject.until(ExpectedConditions.elementToBeClickable(getElement(goBtn)));  
        
        waitForObject.until(ExpectedConditions.attributeToBe(By.xpath(queryTxtBox), "aria-readonly", "false"));
        
        String dropDown =dropDownIcon;
        String textOfElement = "//li[text()='"+type+"']";
        
        for(int iterator =0;iterator<=10;iterator++)
        {
        FunctionLibrary.clickObject(dropDown,"", "Click type drop down");
	        try
	        {
	        	WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 3);
	        	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dropDownListItem)));
	        	break;
	        }
	        catch(TimeoutException e1)
	        {
	        	 FunctionLibrary.clickObject(dropDown,"", "Click type drop down");
	        }
        }
        FunctionLibrary.clickObject(textOfElement, "", "Click the type");
        FunctionLibrary.objDriver.findElement(By.xpath(queryValueTxtBox)).sendKeys(value);
        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
        
    }
    
   public static void closeSiebelInErrorCase()
   {
       try{

           System.out.println("Test Failed. Taking screenshot");

           WebDriverWait wait8 = new WebDriverWait(objDriver,5);
           wait8.until(ExpectedConditions.alertIsPresent());
           Thread.sleep(3000);
           String alertText;
           Alert alert = objDriver.switchTo().alert();
           alertText = alert.getText();
           alert.accept();
           ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Execution Failed due to alert : <br>"+alertText, LogStatus.FAIL, true);
           closeAllActiveBrowser();

       }

       catch(Exception exp){
           exp.printStackTrace();
           
           try{
           	 WebDriverWait wait8 = new WebDriverWait(objDriver,5);
           	 wait8.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(okBtn)));
                   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Execution Failed due to popup <br>", LogStatus.FAIL, true);
                   objDriver.findElement(By.xpath(okBtn)).click();
                   closeAllActiveBrowser();
           }
           catch(TimeoutException e1)
           {
           	
           }
           catch(Exception e1){
               System.out.println("test failed");
               ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Execution Failed <br>"+e1.getMessage(), LogStatus.FAIL, true);
               CommonLibrary.logoutSiebelApplicaiton();
           }


       }

   }
   
   public static void verifyFinancialChkBox(String todId, String shouldSelectOrNot)
   {
	    boolean isFinancialOnPreviousDay;
        if(shouldSelectOrNot.startsWith("Y"))
        {
        	isFinancialOnPreviousDay = true;
        }
        else
        {
        	isFinancialOnPreviousDay = false;
        }
        
        
        clickObject(csrCloseoutMenu, "", "Click CSR CloseOut menu");
        waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(goBtn,
                csrCloseoutMenu, 6, 30);
   
        searchForTod("TOD Id", todId);
       try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
   
       if(isFinancialOnPreviousDay)
       {   	   
      
	        try{
	    		WebDriverWait wait1 = new WebDriverWait(objDriver, 2);
	    		wait1.until(ExpectedConditions.visibilityOf(getElement("//td[contains(@id,'_Financial')]//span[@class='siebui-icon-checkbox-checked']")));
	    		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Financials checkbox should be selected. Actual: Selected."
	                    , LogStatus.PASS, true);
	    	}
	    	catch(TimeoutException e1)
	    	{
	    		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Financials checkbox should be selected. Actual:But it is not selected."
	                    , LogStatus.FAIL, true);
	    	}
        
       }else
       {
    	   try{
	    		WebDriverWait wait1 = new WebDriverWait(objDriver, 2);
	    		wait1.until(ExpectedConditions.visibilityOf(getElement("//td[contains(@id,'_Financial')]//span[@class='siebui-icon-checkbox-checked']")));
	    		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Financials checkbox should not be selected. Actual:But it is selected."
	                    , LogStatus.FAIL, true);
	    	}
	    	catch(TimeoutException e1)
	    	{
	    		
	    		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Financials checkbox should not be selected. Actual: Not Selected."
	                    , LogStatus.PASS, true);
	    	}
       }
   
   }
    
}
