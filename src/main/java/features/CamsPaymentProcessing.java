package features;

import com.relevantcodes.extentreports.LogStatus;

import libraries.CommonLibrary;
import libraries.FunctionLibrary;
import libraries.ReportLibrary;
import libraries.TestCaseLibrary;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import static libraries.CommonLibrary.*;
import static libraries.FunctionLibrary.closeAllActiveBrowser;
import static libraries.FunctionLibrary.objDriver;
import static objectProperties.SblCreateAccountPageProperties.*;
import static objectProperties.SblPaymentProperties.*;

public class CamsPaymentProcessing {

public static void CamsPaymentProcessingTest() throws IOException, Exception {
                   ExcelSheet exl = new ExcelSheet();

                    int noOfRows = exl.totalrows("FLCSS_CAMS_PaymentProcessing","Test_Scenarios");

                    HashMap<String, String> eachTestCaseData = new HashMap<String, String>();

                    String testCaseName = "";
                    for (int i = 1; i <=noOfRows; i++) {

                        eachTestCaseData = getEachTestCaseData(exl, "Test_Scenarios", i,"FLCSS_CAMS_PaymentProcessing");
                        dataObj=eachTestCaseData;
                        testCaseName = "tcPaymentProcessing"+dataObj.get("TestCaseId").replace("-", "");
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
                                    Class<?> c = Class.forName("features.CamsPaymentProcessing");

                                    testMethod= c.getMethod(testCaseName);
                                    CommonLibrary.loginSblWithTestScenarioSpecificUser(eachTestCaseData);
                                    TestCaseLibrary.Get_TestCase_Instance().executeTC(testMethod);
                                    CommonLibrary.logoutSiebelApplicaiton();
                                   // FunctionLibrary.Close_All_Active_Browser();
                                } catch (ClassNotFoundException e) {

                                    e.printStackTrace();
                                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is not found", LogStatus.FAIL,false);
                                }

                            }
                            

                          //End of Try
                            catch(Exception e)
                            {
                               //if alert found. it will be accepted and close the browser.
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
                        //Close browsers

                        closeAllActiveBrowser();

                    }

                    catch(Exception exp){


                        exp.printStackTrace();
                        try{
                                 WebDriverWait wait8 = new WebDriverWait(objDriver,5);
                                 wait8.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(okBtn)));
                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Execution Failed due to popup <br>", LogStatus.FAIL, true);
                                objDriver.findElement(By.xpath(okBtn)).click();

                                //Method for Logout and Closing browser
                                ////////logoutSiebelApplicaiton();
                                closeAllActiveBrowser();

                           
                        }
                        catch(TimeoutException e1)
                        {
                                
                        }
                        catch(Exception e1){
                            System.out.println("Test is failed");
                            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Execution Failed <br>"+e1.getMessage(), LogStatus.FAIL, true);
                            CommonLibrary.logoutSiebelApplicaiton();
                            //Close_All_Active_Browser();

                        }

                    }
                            libraries.CommonLibrary.dataObj.clear();
                            }
                        }
                        else
                        {
                                  dataObj.clear();eachTestCaseData.clear();
                        }
                    }


                }

    public static void tcPaymentProcessingPMTP001() throws Exception {

                try{
                                String acctNumber =dataObj.get("AccountNumber");
                                oneTimePayment(acctNumber);
                                }
            catch(Exception e)
                {
                                 ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed: "+e.getMessage(),LogStatus.FAIL,true);
          
                }
    }

    public static void tcPaymentProcessingPMTP002() throws Exception
    {

                try{
   
                                //negative test only: so account created for PMTP001 test case is fine
                                String acctNumber =dataObj.get("AccountNumber");
          //search for account
            CommonLibrary.searchForAccount(acctNumber);          
           
            String AccountBal=FunctionLibrary.getText(accountBalTxtBox,  "Account Balance");
            String existingAmount=AccountBal;
            existingAmount=existingAmount.replace("$","");
            existingAmount=existingAmount.replace(",","");
            int addedAmount =10;
            Double.parseDouble(existingAmount);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Account Balance before adding Amount :"+existingAmount, LogStatus.INFO, true);
           
            //click the link Financial
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(linkFinancial)));
            FunctionLibrary.clickObject(linkFinancial, "", "click on financials");

           FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(accountReplenishment,linkFinancial,15,34);
            FunctionLibrary.clickObject(accountReplenishment, "", "click on accountReplenishment");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(newBtnAmount,accountReplenishment,15,34);
            FunctionLibrary.clickObject(newBtnAmount, "", "click on newBtnAmount");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(amonutTxtBox,newBtnAmount,15,34);
            Thread.sleep(3000);
            FunctionLibrary.waitForObject.until(ExpectedConditions.attributeToBe(By.xpath(balanceForwardTxtBox),
                                "aria-readonly", "false"));
            FunctionLibrary.setText(amonutTxtBox, dataObj.get("Payment Amount"), "Enter Amount");
           
            FunctionLibrary.clickObject(replenishmentSave, "", "click on replenishmentSave");
            FunctionLibrary.scrollDowntoElement(newPaymentList, "Scrol down");
            FunctionLibrary.clickObject(newPaymentList, "", "click on newPaymentList");
            
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(saveRebillInfo,
                                newPaymentList, 3, 20);
            
            FunctionLibrary.scrollDowntoElement(saveRebillInfo, "Scrol down");
            FunctionLibrary.selectDropDownListByValue(replenishmentDetailsRebillPayTypeDropdown,dataObj.get("Payment Type"),"select cash from dropdown");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Info:Adding Amount With ReBill Type as CASH :"+""+"Take screenshot after adding Amount with CASH as ReBillType"+""+addedAmount, LogStatus.INFO, true);

            FunctionLibrary.clickObject(saveRebillInfo, "", "click on saveRebillInfo");
              
            try
            {
                  WebDriverWait waitObj = new WebDriverWait(FunctionLibrary.objDriver, 15);
                                 
                                waitObj.until(ExpectedConditions.alertIsPresent());
              Alert alert1 = FunctionLibrary.objDriver.switchTo().alert();
              String alertText =alert1.getText();
              
              alert1.accept();
              if(alertText.contains("Cash payments are not allowed with the logged in user"))
              {
                  ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:  Alert should be displayed with text: Cash payments are not allowed with the logged in user's Logon Mode.<br> Atual: Displayed - "+alertText,
                                                                                LogStatus.PASS, true);
              }else
              {
                  ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:  Alert should be displayed with text: Cash payments are not allowed with the logged in user's Logon Mode.<br> Atual: Not displayed",
                                                                                LogStatus.PASS, true);
              }
            }catch(TimeoutException e1)
            {
                  ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Popup should be display as invalid paytype: "+
                                                                ".<br>Acutal: Popup is not displayed ", LogStatus.FAIL, true);
                
            }
            catch(Exception e1)
            {
                  ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Popup should be display as invalid paytype "+
                                                                ".<br>Acutal: Popup is not displayed ", LogStatus.FAIL, true);
                
            }
                                
                                }
                    catch(Exception e)
                                {
                                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed: "+e.getMessage(),LogStatus.FAIL,true);
                  
                                }
    }
    
    public static void tcPaymentProcessingPMTP003() throws Exception
    {

                try{
   
                                //negative test only: so account created for PMTP001 test case is fine
                                String acctNumber =dataObj.get("AccountNumber");
                                
           
          //search for account
            CommonLibrary.searchForAccount(acctNumber);          
           
            String AccountBal=FunctionLibrary.getText(accountBalTxtBox, "Account Balance");
            String existingAmount=AccountBal;
            existingAmount=existingAmount.replace("$","");
            existingAmount=existingAmount.replace(",","");
            int addedAmount =10;
            Double.parseDouble(existingAmount);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Account Balance before adding Amount :"+"<br>"+"Account Balance is :"+existingAmount, LogStatus.INFO, true);
           
            //click the link Financial
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(linkFinancial)));
            FunctionLibrary.clickObject(linkFinancial, "", "click on financials");

           FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(accountReplenishment,linkFinancial,15,34);
            FunctionLibrary.clickObject(accountReplenishment, "", "click on accountReplenishment");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(newBtnAmount,accountReplenishment,15,34);
            FunctionLibrary.clickObject(newBtnAmount, "", "click on newBtnAmount");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(amonutTxtBox,newBtnAmount,15,34);
            Thread.sleep(3000);
            FunctionLibrary.waitForObject.until(ExpectedConditions.attributeToBe(By.xpath(balanceForwardTxtBox),
                                "aria-readonly", "false"));
            FunctionLibrary.setText(amonutTxtBox, dataObj.get("Payment Amount"), "Enter Amount");
            
            FunctionLibrary.clickObject(replenishmentSave, "", "click on replenishmentSave");
            FunctionLibrary.scrollDowntoElement(newPaymentList, "Scrol down");
            FunctionLibrary.clickObject(newPaymentList, "", "click on newPaymentList");
            
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(saveRebillInfo,
                                newPaymentList, 3, 20);
            
            
            FunctionLibrary.scrollDowntoElement(saveRebillInfo, "Scrol down");
            FunctionLibrary.selectDropDownListByValue(replenishmentDetailsRebillPayTypeDropdown,dataObj.get("Payment Type"),"select cash from dropdown");
            FunctionLibrary.setText("//input[@id='ChkNumber']",dataObj.get("CheckNumber"),"Enter check number");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Info:Adding Amount With ReBill Type as CHECK :"+""+
                    "Take screenshot after adding Amount with CHECK as ReBillType"+""+addedAmount, LogStatus.INFO, true);
                   
            FunctionLibrary.clickObject(saveRebillInfo, "", "click on saveRebillInfo");
            
            try
            {
                  WebDriverWait waitObj = new WebDriverWait(FunctionLibrary.objDriver, 15);
                                 
                                waitObj.until(ExpectedConditions.alertIsPresent());

              Alert alert1 = FunctionLibrary.objDriver.switchTo().alert();
              String alertText =alert1.getText();
              
              alert1.accept();
              if(alertText.contains("payments are not allowed with the logged in user"))
              {
                  ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:  Alert should be displayed with text: Check payments are not allowed with the logged in user's Logon Mode.<br> Atual: Displayed - "+alertText,
                                                                                LogStatus.PASS, true);
              }else
              {
                  ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:  Alert should be displayed with text: Check payments are not allowed with the logged in user's Logon Mode.<br> Atual: Not displayed",
                                                                                LogStatus.PASS, true);
              }

            }catch(TimeoutException e1)
            {
                  ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Popup should be display as invalid paytype: "+
                                                                ".<br>Acutal: Popup is not displayed ", LogStatus.FAIL, true);
                
            }
            catch(Exception e1)
            {
                  ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Popup should be display as invalid paytype "+
                                                                ".<br>Acutal: Popup is not displayed ", LogStatus.FAIL, true);
                
            }
                                
                                                }
                    catch(Exception e)
                                {
                                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed: "+e.getMessage(),LogStatus.FAIL,true);
                  
                                }
   }
       
    public static void tcPaymentProcessingPMTP005() throws Exception
    {
                try{

                                String acctNumber =dataObj.get("AccountNumber");
                                oneTimePayment(acctNumber);            
                                }
                                catch(Exception e)
                                {
                                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed: "+e.getMessage(),LogStatus.FAIL,true);
                                
                                }
        }

    public static void tcPaymentProcessingPMTP012() throws Exception
    {
                try
                {
                                //negative test only: so account created for PMTP005 test case is fine
                                String acctNumber =dataObj.get("AccountNumber");
                           
        //search for account
          CommonLibrary.searchForAccount(acctNumber);          
         
          String AccountBal=FunctionLibrary.getText(accountBalTxtBox, "Account Balance");
          
          String existingAmount=AccountBal;
          existingAmount=existingAmount.replace("$","").replace(",","");
          FunctionLibrary.objDriver.findElement(By.linkText(acctNumber)).click();
          FunctionLibrary.waitForObject.until(ExpectedConditions.attributeToBe(By.xpath(accountCompanyNameTxtBox),"aria-readonly", "false"));
          
          String minRebillThresholdAmt = FunctionLibrary.getText(minRebillThresholdAmount,  "Minimum threshold amount");
          minRebillThresholdAmt=minRebillThresholdAmt.replace("$","").replace(",","");
          Double addAmount =Double.valueOf(minRebillThresholdAmt)-1;
                  
          ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Account Balance before adding Amount :"+existingAmount, LogStatus.INFO, true);
          ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Minimum rebill threshold amount is: "+ minRebillThresholdAmt+
                                  ". And try to add less than threshold amount: "+addAmount+"$", LogStatus.INFO, true);
          
          
          //click the link Financial
          FunctionLibrary.clickObject(linkFinancial, "", "click on financials");
        
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(accountReplenishment,linkFinancial,15,34);
          FunctionLibrary.clickObject(accountReplenishment, "", "click on accountReplenishment");
          FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(newBtnAmount,accountReplenishment,15,34);
          FunctionLibrary.clickObject(newBtnAmount, "", "click on newBtnAmount");
          FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(amonutTxtBox,newBtnAmount,15,34);
          Thread.sleep(3000);
          FunctionLibrary.waitForObject.until(ExpectedConditions.attributeToBe(By.xpath(balanceForwardTxtBox),
                                "aria-readonly", "false"));
          FunctionLibrary.setText(amonutTxtBox, String.valueOf(addAmount), "Enter Amount");          
          FunctionLibrary.clickObject(replenishmentSave, "", "click on replenishmentSave");
        
                   
          String popUpWithMinReplenishmentAmount = "//*[contains(text(),'minimum replen')]";
          
          try
          {
                  WebDriverWait waitObj = new WebDriverWait(FunctionLibrary.objDriver, 10);
                                  waitObj.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(popUpWithMinReplenishmentAmount)));
                                  
                  WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(popUpWithMinReplenishmentAmount));
              ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Error message should be dispalyed: "+element.getText()+
                                                                ".<br>Atual: Error Message is displayed ", LogStatus.PASS, true);
              
              FunctionLibrary.clickObject(okBtn, "", "Click ok button");
              FunctionLibrary.closeAllActiveBrowser();
          }catch(TimeoutException e1)
          {
                  ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Popup should be display with amount should not be less than minimum threshold amount: "+
                                                                ".<br>Acutal: Popup is not displayed ", LogStatus.FAIL, true);
              
          }
          catch(Exception e1)
          {
                  ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Popup should be display with amount should not be less than minimum threshold amount: "+
                                                                ".<br>Acutal: Popup is not displayed ", LogStatus.FAIL, true);
              
          }
                }
          catch(Exception e)
                                {
                                                 ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed. "+e.getMessage(),LogStatus.FAIL,true);
                  
                                }
                
                            
            
    }

    public static void tcPaymentProcessingPMTP013() throws Exception {

        try{
            String acctNumber =dataObj.get("AccountNumber");
            twoTimePayment(acctNumber);
         }
        	catch(Exception e)
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed: "+e.getMessage(),LogStatus.FAIL,true);
  
        }
    }
        
    public static void tcPaymentProcessingPMTP019() throws Exception
    {
                try{

                                String acctNumber =dataObj.get("AccountNumber");
                                oneTimePayment(acctNumber);
                                
                }
                catch(Exception e)
                {
                                 ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed: "+e.getMessage(),LogStatus.FAIL,true);
      
                }
    }

    public static void tcPaymentProcessingPMTP027() throws Exception
    {
                try{

                                String acctNumber =dataObj.get("AccountNumber");
                                                if(acctNumber.length()>1)
                                                {
                                                                paymentReversal(acctNumber,dataObj.get("Is WithIn Threshold"));
                                                }
                }
                                catch(Exception e)
                                {
                                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed: "+e.getMessage(),LogStatus.FAIL,true);
                                
                                }   

}
  
    public static void tcPaymentProcessingPMTP034() throws Exception
    {
        try{

                        String acctNumber =dataObj.get("AccountNumber");
                                        if(acctNumber.length()>1)
                                        {
                                        	            oneTimePayment(acctNumber);
                                                        paymentReversal(acctNumber,dataObj.get("Is WithIn Threshold"));
                                        }
        }
                        catch(Exception e)
                        {
                                         ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed: "+e.getMessage(),LogStatus.FAIL,true);
                        
                        }   
    }
    
    public static void tcPaymentProcessingPMTP035() throws Exception
    {
                try{

                                String acctNumber =dataObj.get("AccountNumber");
                                                if(acctNumber.length()>1)
                                                {
                                                                paymentReversal(acctNumber,dataObj.get("Is WithIn Threshold"));
                                                }
                }
                                catch(Exception e)
                                {
                                                 ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed: "+e.getMessage(),LogStatus.FAIL,true);
                                
                                }   
     }

    public static void tcPaymentProcessingPMTP040() throws Exception
    {
                try{

                                String acctNumber =dataObj.get("AccountNumber");
                                                if(acctNumber.length()>1)
                                                {
                                                                paymentReversal(acctNumber,dataObj.get("Is WithIn Threshold"));
                                                }
                }
                                catch(Exception e)
                                {
                                                 ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed: "+e.getMessage(),LogStatus.FAIL,true);
                                
                                }   
        
    }

    public static void tcPaymentProcessingPMTP046() throws InterruptedException {
                try
                {
                                
            
            String acctNumber =dataObj.get("AccountNumber");
            
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Pre-req account is private. And amount would be only 10$. So adding amount 10000 before testing debit functionality", LogStatus.INFO, false);
            dataObj.put("Payment Type","CASH");
            dataObj.put("Payment Amount", "10000");
            dataObj.put("TransType", "R");
            
            CamsPaymentProcessing.oneTimePayment(acctNumber);
            
            dataObj.put("Payment Type","NONE");
            //dataObj.put("Payment Amount", "10000");
            dataObj.put("TransType", "E");
            
            
            
            if(acctNumber.length()>1)
            {
                                placeAdjustmentAndVerifyFinancialHistory(acctNumber,dataObj.get("Is WithIn Threshold"));    
            }
                                }
                                catch(Exception e)
                                {
                                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed: "+e.getMessage(),LogStatus.FAIL,true);
                                
                                }
    }

    public static void tcPaymentProcessingPMTP047() throws InterruptedException {
                try
                {
            String acctNumber =dataObj.get("AccountNumber");
            if(acctNumber.length()>1)
            {
                                placeAdjustmentAndVerifyFinancialHistory(acctNumber,dataObj.get("Is WithIn Threshold"));    
            }
                                }
                                catch(Exception e)
                                {
                                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed: "+e.getMessage(),LogStatus.FAIL,true);
                                
                                }
    }
   
    public static void tcPaymentProcessingPMTP048() throws InterruptedException {
                try
                {
            String acctNumber =dataObj.get("AccountNumber");
            if(acctNumber.length()>1)
            {
                                placeAdjustmentAndVerifyFinancialHistory(acctNumber,dataObj.get("Is WithIn Threshold"));    
            }
                                }
                                catch(Exception e)
                                {
                                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed: "+e.getMessage(),LogStatus.FAIL,true);
                                
                                }
    }

    public static void tcPaymentProcessingPMTP055() throws InterruptedException {
                try{
                        String Desc = ""; 
                        //dataObj.get("ExecutionStatus");
                        String transferFromAccount =dataObj.get("AccountNumber");
                        String transferToAccount =dataObj.get("Trasnfer To AccountNumber");
                                
                        String [] trasferToAccountNumAndAmount;
                        String adjustAmount = dataObj.get("AdjustInfo");
                        trasferToAccountNumAndAmount=adjustAmount.split(":");
                                        
                       // String transferToAccount=trasferToAccountNumAndAmount[0];
                        String subCategory  = trasferToAccountNumAndAmount[0];
                        String transferAmount  = trasferToAccountNumAndAmount[1];
        
                        CommonLibrary.searchForAccount(transferFromAccount); 
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>"
                    + "Transfering Amount from "+transferFromAccount +" To "+ transferToAccount, LogStatus.INFO, true);
            
                        FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(transferFromAccount)));
                        FunctionLibrary.objDriver.findElement(By.linkText(transferFromAccount)).click();
                        FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 60, ChallangeQA2Dropdown);
                        
            //goto financials tab
            Desc = "Clicking Financials tab";
            FunctionLibrary.clickObject(financialsTab, "", Desc);
            FunctionLibrary.clickObject(balanceTransferTab, "", Desc);
            FunctionLibrary.scrollDown("New", "Scroll down");
            Desc = "Clicking on new button";
            FunctionLibrary.clickObject(balanceTransferNewBtn, "", Desc);
            
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(transferDestinationAccount, 
                                balanceTransferNewBtn, 6, 24);
            FunctionLibrary.waitForObject.until(ExpectedConditions.attributeToBe(By.xpath(transferDestinationAccount),
                                "aria-readonly", "false"));            
            
            WebElement el= FunctionLibrary.objDriver.findElement(By.xpath(pptlBalance));
            String balanceOfAccount1BeforeTransfer = el.getAttribute("value");
            System.out.println("Balance avialable in 'Transfer account' before transfer"+  balanceOfAccount1BeforeTransfer);
            
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Balance avialable in 'Transfer account' before transfer"+  balanceOfAccount1BeforeTransfer,
                                                                                                                LogStatus.INFO, false);
            
            balanceOfAccount1BeforeTransfer=balanceOfAccount1BeforeTransfer.replace("$","");
            balanceOfAccount1BeforeTransfer=balanceOfAccount1BeforeTransfer.replace(",","");     
            
            FunctionLibrary.waitForObject.until(ExpectedConditions.attributeToBe(FunctionLibrary.getElement(transferDestinationAccount), "aria-readonly", "false"));
            Desc = "Entering Transfer_Destination_Account";
            FunctionLibrary.setText(transferDestinationAccount, transferToAccount, Desc);
            //Thread.sleep(1000);
            Desc="entering Transfer_Type_Label";
            FunctionLibrary.setText(transferTypeLabel,subCategory, Desc);
            //Thread.sleep(1000);
            //enter Unit_Price_Label
            Desc = "Entering Transfer Price";
            FunctionLibrary.setText(transferPrice, transferAmount, Desc);
            //click on save
            Desc = "clicking on save button";
            FunctionLibrary.clickObject(""+saveBalanceTransfer,"","Click save balance transfer button");
           // Thread.sleep(500);//click on save
            Desc = "clicking on process button";
            FunctionLibrary.clickObject(""+processBalTrnsfer,"","Click process button");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(""+balTransferAlert, ""+processBalTrnsfer, 4, 20);
            //Thread.sleep(4000);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(balTransferAlert)));
            String alertText=FunctionLibrary.objDriver.findElement(By.xpath(balTransferAlert)).getText();
            
            if(alertText.contains("$"+transferAmount+" Transferred"))
            {
                
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Popup shoule be displayed with message "+alertText
                        +"<br>"+"Actual: Message is displayed", LogStatus.PASS, true);
            }
            else
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Popup shoule be displayed with message "+alertText
                        +"<br>"+"Actual: Message is not displayed with expected text", LogStatus.FAIL, true);
            }


            FunctionLibrary.clickObject(""+balTransferAlertAccept,"","Click balance transfer alert button");
            Double balnceOfAccount1AfterTransfer= Double.valueOf(balanceOfAccount1BeforeTransfer)  - 
                                                                                Double.valueOf(transferAmount);
            
            
            el= FunctionLibrary.objDriver.findElement(By.xpath(pptlBalance));
            String balanceOfAccount1AfterTransfer = el.getAttribute("value");
            balanceOfAccount1AfterTransfer=balanceOfAccount1AfterTransfer.replace("(", "").replace(")", "").replace("$", "").replace(",", "");
           
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Balance amount of Account1 After Transfer"+  balanceOfAccount1AfterTransfer, LogStatus.INFO, false);

            Double balanceofAccout1ShouldBeAfterTransfer = Double.valueOf(balanceOfAccount1BeforeTransfer)-Double.valueOf(transferAmount);

            if(Double.valueOf(balanceOfAccount1AfterTransfer).equals(balanceofAccout1ShouldBeAfterTransfer))
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Balance after transfer "+balanceofAccout1ShouldBeAfterTransfer
                        +"<br>"+"Actual: Displaying: "+balanceOfAccount1AfterTransfer, LogStatus.PASS, true);
            }
            else
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Balance after transfer "+balanceofAccout1ShouldBeAfterTransfer
                        +"<br>"+"Actual: Displaying: "+balanceOfAccount1AfterTransfer, LogStatus.FAIL, true);
            }

            verifyTransferNotes(dataObj.get("Payment Type"),transferAmount);
            
            //Verify in financial history table
            FunctionLibrary.clickObject(historyTab, "", "Click history link");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verification under financial history", LogStatus.INFO, false);
            FunctionLibrary.clickObject(financialsUnderHistroy,"","Click Financial under history");
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOf(FunctionLibrary.getElement(financialHistroyTable)));
       
            //Verify in financial history
            String xpathOfFinancialTable = tabView2FianancialHistory;
            int rowNum1 = FunctionLibrary.getRowNumberFromWebTable(xpathOfFinancialTable,"BALTRANSFER","get the row number");
            
            System.out.println("Row number is "+rowNum1);

            FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable,rowNum1,4,dataObj.get("TransType"),"Verifying Trans type",true);
            FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable,rowNum1,6,"BALTRANSFER","Verifying Category",false);
            FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable,rowNum1,7, subCategory,"Verifying Sub Category",false);
            FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable,rowNum1,8, dataObj.get("Payment Type"),"Verifying Payment type",false);
            String transactionNumber= FunctionLibrary.getWebTableCellData(xpathOfFinancialTable, 1, 2, "Getting cell value");
            System.out.println("Transaction Number is  "+transactionNumber);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Transaction number: "+ transactionNumber, LogStatus.INFO, false);
            
            
            String transferedAmountActual = FunctionLibrary.getWebTableCellData(xpathOfFinancialTable, 1, 8, "Getting cell value");
            String postTransActionBalActual = FunctionLibrary.getWebTableCellData(xpathOfFinancialTable, 1, 11, "Getting cell value");
            
            transferedAmountActual=transferedAmountActual.replace("(", "").replace(")", "").replace("$", "").replace(",", "");
            postTransActionBalActual=postTransActionBalActual.replace("(", "").replace(")", "").replace("$", "").replace(",", "");
            
            if(balnceOfAccount1AfterTransfer.equals(Double.valueOf(postTransActionBalActual)))
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Financial Transaction table should show post transaction balance as :"+balnceOfAccount1AfterTransfer
                        +"<br>"+"Actual: Displaying: "+postTransActionBalActual, LogStatus.PASS, true);
           
            }
            else
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Financial Transaction table should show post transaction balance as "+balnceOfAccount1AfterTransfer
                        +"<br>"+"Actual: Displaying: "+postTransActionBalActual, LogStatus.FAIL, true);
           
            }
            
            CommonLibrary.searchForAccount(transferToAccount); 
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>"
                    + "Verifying tranfered amount in Transerred to account " +transferToAccount, LogStatus.INFO, true);
            
                        FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(transferToAccount)));
                        FunctionLibrary.objDriver.findElement(By.linkText(transferToAccount)).click();
                        FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 60, ChallangeQA2Dropdown);
                       
                        FunctionLibrary.clickObject("//a[text()='Financials']", "", "Click Finacials link");
                        verifyTransferNotes(dataObj.get("Payment Type"),transferAmount);
                        
            FunctionLibrary.scrollUptoElement(historyTab, "");
            //Verify in financial history table
            FunctionLibrary.clickObject(historyTab, "", "Click history link");
            FunctionLibrary.clickObject(financialsUnderHistroy,"","Click Financial under history");
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(financialHistroyTable)));
            String autoCreatedTransactionNumber = String.valueOf(Integer.valueOf(transactionNumber)+1);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Auto generated transaction Id due to amount transfer"+autoCreatedTransactionNumber, LogStatus.INFO, false);
            rowNum1 = FunctionLibrary.getRowNumberFromWebTable(xpathOfFinancialTable,autoCreatedTransactionNumber,"get the row number");
                        
            System.out.println("Row number is "+rowNum1);
            FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable,rowNum1,4,dataObj.get("TransType"),"<b>Financial History</b><br>Verifying Pay type",true);
            FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable,rowNum1,8,dataObj.get("Payment Type"),"Verifying Pay type",false);
            FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable,rowNum1,6,"BALTRANSFER","Verifying Category",false);
            FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable,rowNum1,7, subCategory,"Verifying Sub Category",false);
         
            String amountReceived = FunctionLibrary.getWebTableCellData(xpathOfFinancialTable, 1, 9, "Getting cell value");
            postTransActionBalActual = FunctionLibrary.getWebTableCellData(xpathOfFinancialTable, 1, 11, "Getting cell value");
            
            amountReceived=amountReceived.replace("(", "").replace(")", "").replace("$", "").replace(",", "");
            
            if(Double.valueOf(transferAmount).equals(Double.valueOf(amountReceived)))
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Received amount should be: "+transferAmount
                        +"<br>"+"Actual: Displaying: "+amountReceived, LogStatus.PASS, false);
           
            }
            else
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Financial Transaction table should show amount as :"+transferAmount
                        +"<br>"+"Actual: Displaying: "+amountReceived, LogStatus.FAIL, true);
           
            }            
            
                }
                catch(Exception e)
                {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed: "+e.getMessage(),LogStatus.FAIL,true);
                
                }
    }
   
    public static void tcPaymentProcessingPMTP065() throws Exception
    {
                
                try{
                                //same account number can be used as in test case 40. Both test case are using only private account
                                String acctNumber =dataObj.get("AccountNumber");
                                                if(acctNumber.length()>1)
                                                {
                                                                paymentReversal(acctNumber,dataObj.get("Is WithIn Threshold"));
                                                }
                }
                                catch(Exception e)
                                {
                                                 ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed: "+e.getMessage(),LogStatus.FAIL,true);
                                
                                }   

       }

    public static void tcPaymentProcessingPMTP078() throws Exception
    {
                
    try{
                                //same account number can be used as in test case 40. Both test case are using only private account
                                //String acctNumber = getAccountNumberUsingSql("mailidForPmtp078@conduent.com");
                                String acctNumber = dataObj.get("AccountNumber");
                                                if(acctNumber.length()>1)
                                                {
                                                                paymentRefund(acctNumber,dataObj.get("Is WithIn Threshold"));
                                                }
                }
                                catch(Exception e)
                                {
                                                 ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed: "+e.getMessage(),LogStatus.FAIL,true);
                                
                                }
    }
	public static void tcPaymentProcessingPMTP081() throws Exception
	    {
	                
	    try{
	                                //same account number can be used as in test case 40. Both test case are using only private account
	                                //String acctNumber = getAccountNumberUsingSql("mailidForPmtp081@conduent.com");
	                                String acctNumber = dataObj.get("AccountNumber");
	                                                if(acctNumber.length()>1)
	                                                {
	                                                                //paymentRefundWithoutThreshold(acctNumber);
	                                                                String refundPayType = dataObj.get("Refund Payment Type");
	                                                                String tranType=dataObj.get("TransType");            
	                            String refundAmount=dataObj.get("RefundAmount");         
	
	                            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Refund Pay Type value is : "+
	                                    ""+refundPayType, LogStatus.INFO, true);
	
	                            //search for account
	                           CommonLibrary.searchForAccount(acctNumber);
	                            
	                            //click the link Financial
	                            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='#s_sctrl_tabView_noop' and text()='Financials']")));
	                            FunctionLibrary.clickObject(linkFinancial, "", "click on financials");
	                            String accountBalBeforePayemnt=FunctionLibrary.getText(accountBalTxtBox, "Account Balance");
	
	                            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Existing acccount balance: "+
	                                                   accountBalBeforePayemnt,LogStatus.INFO,false);
	
	                            String categoryValue="TOLDIPOSIT";
	                           // String refundPaymentAmount=dataObj.get("RefundAmount");
	                           // FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='ui-id-268']")));
	                            FunctionLibrary.clickObject(refundButton, "", "click on Refund");
	                            Thread.sleep(3000);
	                            FunctionLibrary.clickObject(newButtonRefund, "", "click on New");
	                            try {
	                                                                Thread.sleep(3000);
	                                                } catch (InterruptedException e1) {
	                                                                // TODO Auto-generated catch block
	                                                                e1.printStackTrace();
	                                                }
	                             String Desc="enter the value for Category Label";
	                             WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver,100);
	                             wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-labelledby='Category_Label']")));
	                            
	                           // FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@aria-labelledby='Category_Label']")));
	                            FunctionLibrary.setText(refundCategoryLabel, categoryValue, Desc);            
	                            FunctionLibrary.clickObject(popUpWindowOkCategoryLabelRefund, "", "click on OK");
	                            try {
	                                                                Thread.sleep(3000);
	                                                } catch (InterruptedException e1) {
	                                                                // TODO Auto-generated catch block
	                                                                e1.printStackTrace();
	                                                }
	                           /* WebDriverWait wait1 = new WebDriverWait(FunctionLibrary.objDriver,100);
	                            wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-labelledby='Payment_Category_Label']"))); */
	                            FunctionLibrary.waitForObject.until(ExpectedConditions.attributeToBe(By.xpath("//input[@aria-labelledby='Payment_Category_Label']"), "aria-readonly", "false"));
	                            FunctionLibrary.setText(paymentRefundLabel, categoryValue, "enter Payment Value");
	                            FunctionLibrary.clickObject(popUpWindowPaymentTransactionRefund, "", "click on OK");
	                            String AmountToRefunded=dataObj.get("RefundAmount");
	                            try {
	                                                                Thread.sleep(3000);
	                                                } catch (InterruptedException e1) {
	                                                                // TODO Auto-generated catch block
	                                                                e1.printStackTrace();
	                                                }
	                           // WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,100);
	                           // wait2.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-labelledby='Amount_Label']")));
	                            FunctionLibrary.setText(amountLabel, AmountToRefunded, "enter amount for Refund");
	                            String refundCode="OTHER";
	                            FunctionLibrary.setText(refundCodeLabel, refundCode, "enter refund code");
	                            try {
	                                                                Thread.sleep(2000);
	                                                } catch (InterruptedException e1) {
	                                                                // TODO Auto-generated catch block
	                                                                e1.printStackTrace();
	                                                }
	                            FunctionLibrary.clickObject(refundSaveBtn, "", "click on save");
	                            try {
	                                                                Thread.sleep(3000);
	                                                } catch (InterruptedException e1) {
	                                                                // TODO Auto-generated catch block
	                                                                e1.printStackTrace();
	                                                }
	                            FunctionLibrary.clickObject(refundBtn, "", "click on Refund");      
	                          
	                            String processNum = null;
	                            try {
	
	                                WebDriverWait wait3 = new WebDriverWait(FunctionLibrary.objDriver,40);
	                                wait3.until(ExpectedConditions.alertIsPresent());
	                                Alert alert = FunctionLibrary.objDriver.switchTo().alert();
	                                String alertText = alert.getText();
	                                System.out.println(alertText);
	                                processNum = alertText.split("Payment Confirmation Number : ")[1];
	                                processNum=processNum.split(" ")[0].trim();
	                                System.out.println(processNum);
	                                alert.accept();
	                                //Thread.sleep(3000);
	
	                            }
	                            catch (Exception e) {
	                                //exception handling
	                            }
	
	                            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@title='Financial Transaction List:Reverse']")));
	                                                   
	                                   String accountBalAfterRefund=FunctionLibrary.getText(accountBalTxtBox, "Account Balance");
	                                   accountBalAfterRefund=accountBalAfterRefund.replace("$", "");
	                                   accountBalAfterRefund=accountBalAfterRefund.replace(",", "");  
	                                   
	                                  String xpathOfFinancialTable = financialTransactionTable;
	                              int rowNumber1 = FunctionLibrary.getRowNumberFromWebTable(xpathOfFinancialTable, "D", "Get the row number");
	                              System.out.println(rowNumber1);
	                              
	                             // FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable, rowNumber1, 2, "CASH",
	                                //      "Verifying payType", true);
	                              FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable,rowNumber1,3,
	                                      "D","Verifying Transaction Type ", false);      
	                                           
	                             //  verifyRefundInfoUnderPaymentNotes(refundPayType);  
	                              
	                              FunctionLibrary.objDriver.findElement(By.xpath("//a[text()='Payment Notes']")).click();
	                              FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Payments List:Go']")));
	                              String notesTable = "//table[@summary='Payments List']";
	                              
	                              String paymentRefundType=dataObj.get("Refund Payment Type");
	                              FunctionLibrary.verifyWebTableCellData(notesTable, 1, 1, paymentRefundType, "<b>Notes:</b><br>Verify payment type", true);
	                             // FunctionLibrary.verifyWebTableCellData(notesTable, 2, 1, refundPayType, "Verify refund type", false);
	                                                      
	                                           FunctionLibrary.clickObject("//a[text()='History']", "", "Click history link");
	                                           FunctionLibrary.clickObject(financialsUnderHistroy,"","Click Financial under history");
	                                           //FunctionLibrary.clickObject(financialHistroyTable,"","Click Financial history table");
	                                           FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Account Financials History List:View CC']")));
	                                          verifyRefundInfoUnderHistoryTab(processNum,refundPayType, tranType,"TOLDEPOSIT","REFUND", 
	                                                                  refundAmount);
	                                           accountBalBeforePayemnt=accountBalBeforePayemnt.replace(",", "").replace("$", "");
	                                           if(Double.valueOf(accountBalBeforePayemnt).equals(Double.valueOf(accountBalAfterRefund)))
	                                           {
	                                               ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Account Balance after Refund Amount :"+accountBalBeforePayemnt+
	                                                       "<br>"+"The Account Balance is :"+accountBalAfterRefund, LogStatus.FAIL, true);
	
	                                           }
	                                           else
	                                           {
	                                               ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Account Balance after Refund Amount :"+ accountBalAfterRefund +
	                                                       "<br>"+"Actual:Account Balance is :"+accountBalAfterRefund, LogStatus.PASS, true);
	
	                                           }
	
	
	                                            
	                            
	                                String accountBalAfterRefund1=FunctionLibrary.getText(accountBalTxtBox, "Account Balance");
	                                String amountShowing = FunctionLibrary.getText("//input[@aria-label='PPTL Bal']", "Get the Amount");
	                                amountShowing = amountShowing.replace(",", "").replace("$", "");
	                                accountBalAfterRefund1 = accountBalAfterRefund1.replace(",", "").replace("$", "");
	                                if(Double.valueOf(accountBalAfterRefund1).equals(Double.valueOf(amountShowing)))
	                                {
	                                   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Amount:: "+accountBalAfterRefund+"<br> Actual: "+amountShowing, 
	                                                                   LogStatus.PASS,false);
	                                }else
	                                {
	                                   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Amount:: "+accountBalAfterRefund+"<br> Actual: "+amountShowing, 
	                                                                   LogStatus.FAIL,false);
	                                }
	                                                }
	                }
	                                catch(Exception e)
	                                {
	                                                 ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed: "+e.getMessage(),LogStatus.FAIL,true);
	                                
	                                }
	    
	  }
	public static void tcPaymentProcessingPMTP082() throws Exception
	{
	               
	 try{
	                               //same account number can be used as in test case 40. Both test case are using only private account
	                               //String acctNumber = getAccountNumberUsingSql("mailidForPmtp081@conduent.com");
	                               String acctNumber = dataObj.get("AccountNumber");
	                                               if(acctNumber.length()>1)
	                                               {
	                                                               //paymentRefundWithoutThreshold(acctNumber);
	                                                               String refundPayType = dataObj.get("Refund Payment Type");
	                                                               String tranType=dataObj.get("TransType");            
	                            String refundAmount=dataObj.get("RefundAmount");         
	
	                            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Refund Pay Type value is : "+
	                                    ""+refundPayType, LogStatus.INFO, true);
	
	                            //search for account
	                            CommonLibrary.searchForAccount(acctNumber);
	                            
	                            //click the link Financial
	                            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='#s_sctrl_tabView_noop' and text()='Financials']")));
	                            FunctionLibrary.clickObject(linkFinancial, "", "click on financials");
	                            String accountBalBeforePayemnt=FunctionLibrary.getText(accountBalTxtBox, "Account Balance");
	
	                            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Existing acccount balance: "+
	                                                   accountBalBeforePayemnt,LogStatus.INFO,false);
	
	                            String categoryValue="TOLDIPOSIT";
	                           // String refundPaymentAmount=dataObj.get("RefundAmount");
	                           // FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='ui-id-268']")));
	                            FunctionLibrary.clickObject(refundButton, "", "click on Refund");
	                            Thread.sleep(3000);
	                            FunctionLibrary.clickObject(newButtonRefund, "", "click on New");
	                            try {
	                                                                Thread.sleep(3000);
	                                                } catch (InterruptedException e1) {
	                                                                // TODO Auto-generated catch block
	                                                                e1.printStackTrace();
	                                                }
	                             String Desc="enter the value for Category Label";
	                             WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver,100);
	                             wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-labelledby='Category_Label']")));
	                            
	                           // FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@aria-labelledby='Category_Label']")));
	                            FunctionLibrary.setText(refundCategoryLabel, categoryValue, Desc);            
	                            FunctionLibrary.clickObject(popUpWindowOkCategoryLabelRefund, "", "click on OK");
	                            try {
	                                                                Thread.sleep(3000);
	                                                } catch (InterruptedException e1) {
	                                                                // TODO Auto-generated catch block
	                                                                e1.printStackTrace();
	                                                }
	                           /* WebDriverWait wait1 = new WebDriverWait(FunctionLibrary.objDriver,100);
	                            wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-labelledby='Payment_Category_Label']"))); */
	                            FunctionLibrary.waitForObject.until(ExpectedConditions.attributeToBe(By.xpath("//input[@aria-labelledby='Payment_Category_Label']"), "aria-readonly", "false"));
	                            FunctionLibrary.setText(paymentRefundLabel, categoryValue, "enter Payment Value");
	                            FunctionLibrary.clickObject(popUpWindowPaymentTransactionRefund, "", "click on OK");
	                            String AmountToRefunded=dataObj.get("RefundAmount");
	                            try {
	                                                                Thread.sleep(3000);
	                                                } catch (InterruptedException e1) {
	                                                                // TODO Auto-generated catch block
	                                                                e1.printStackTrace();
	                                                }
	                           // WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,100);
	                           // wait2.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-labelledby='Amount_Label']")));
	                            FunctionLibrary.setText(amountLabel, AmountToRefunded, "enter amount for Refund");
	                            String refundCode="OTHER";
	                            FunctionLibrary.setText(refundCodeLabel, refundCode, "enter refund code");
	                            try {
	                                                                Thread.sleep(2000);
	                                                } catch (InterruptedException e1) {
	                                                                // TODO Auto-generated catch block
	                                                                e1.printStackTrace();
	                                                }
	                            FunctionLibrary.clickObject(refundSaveBtn, "", "click on save");
	                            try {
	                                                                Thread.sleep(3000);
	                                                } catch (InterruptedException e1) {
	                                                                // TODO Auto-generated catch block
	                                                                e1.printStackTrace();
	                                                }
	                            FunctionLibrary.clickObject(refundBtn, "", "click on Refund");      
	                          
	                            String processNum = null;
	                            try {
	
	                                WebDriverWait wait3 = new WebDriverWait(FunctionLibrary.objDriver,40);
	                                wait3.until(ExpectedConditions.alertIsPresent());
	                                Alert alert = FunctionLibrary.objDriver.switchTo().alert();
	                                String alertText = alert.getText();
	                                System.out.println(alertText);
	                                processNum = alertText.split("Payment Confirmation Number : ")[1];
	                                processNum=processNum.split(" ")[0].trim();
	                                System.out.println(processNum);
	                                alert.accept();
	                                //Thread.sleep(3000);
	
	                            }
	                            catch (Exception e) {
	                                //exception handling
	                            }
	
	                            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@title='Financial Transaction List:Reverse']")));
	                                                   
	                                   String accountBalAfterRefund=FunctionLibrary.getText(accountBalTxtBox, "Account Balance");
	                                   accountBalAfterRefund=accountBalAfterRefund.replace("$", "");
	                                   accountBalAfterRefund=accountBalAfterRefund.replace(",", "");  
	                                   
	                                  String xpathOfFinancialTable = financialTransactionTable;
	                              int rowNumber1 = FunctionLibrary.getRowNumberFromWebTable(xpathOfFinancialTable, "D", "Get the row number");
	                              System.out.println(rowNumber1);
	                              
	                              //FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable, rowNumber1, 2, "CHECK",
	                                    //  "Verifying payType", true);
	                              FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable,rowNumber1,3,
	                                      "D","Verifying Transaction Type ", false);      
	                                           
	                             //  verifyRefundInfoUnderPaymentNotes(refundPayType);  
	                              
	                              FunctionLibrary.objDriver.findElement(By.xpath("//a[text()='Payment Notes']")).click();
	                              FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Payments List:Go']")));
	                              String notesTable = "//table[@summary='Payments List']";
	                              
	                              String paymentRefundType=dataObj.get("Refund Payment Type");
	                              FunctionLibrary.verifyWebTableCellData(notesTable, 1, 1, paymentRefundType, "<b>Notes:</b><br>Verify payment type", true);
	                              //FunctionLibrary.verifyWebTableCellData(notesTable, 2, 1, refundPayType, "Verify refund type", false);
	                                                     
	                                           FunctionLibrary.clickObject("//a[text()='History']", "", "Click history link");
	                                          FunctionLibrary.clickObject(financialsUnderHistroy,"","Click Financial under history");
	                                          //FunctionLibrary.clickObject(financialHistroyTable,"","Click Financial history table");
	                                          FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Account Financials History List:View CC']")));
	                                         verifyRefundInfoUnderHistoryTab(processNum,refundPayType, tranType,"TOLDEPOSIT","REFUND", 
	                                                                  refundAmount);
	                                          accountBalBeforePayemnt=accountBalBeforePayemnt.replace(",", "").replace("$", "");
	                                          if(Double.valueOf(accountBalBeforePayemnt).equals(Double.valueOf(accountBalAfterRefund)))
	                                          {
	                                              ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Account Balance after Refund Amount :"+accountBalBeforePayemnt+
	                                                      "<br>"+"The Account Balance is :"+accountBalAfterRefund, LogStatus.FAIL, true);
	
	                                          }
	                                          else
	                                          {
	                                              ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Account Balance after Refund Amount :"+ accountBalAfterRefund +
	                                                      "<br>"+"Actual:Account Balance is :"+accountBalAfterRefund, LogStatus.PASS, true);
	
	                                          }                                               
	                            
	                                String accountBalAfterRefund1=FunctionLibrary.getText(accountBalTxtBox, "Account Balance");
	                                String amountShowing = FunctionLibrary.getText("//input[@aria-label='PPTL Bal']", "Get the Amount");
	                                amountShowing = amountShowing.replace(",", "").replace("$", "");
	                                accountBalAfterRefund1 = accountBalAfterRefund1.replace(",", "").replace("$", "");
	                                if(Double.valueOf(accountBalAfterRefund1).equals(Double.valueOf(amountShowing)))
	                                {
	                                   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Amount:: "+accountBalAfterRefund+"<br> Actual: "+amountShowing, 
	                                                                   LogStatus.PASS,false);
	                                }else
	                                {
	                                   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Amount:: "+accountBalAfterRefund+"<br> Actual: "+amountShowing, 
	                                                                   LogStatus.FAIL,false);
	                                }
	                                               }
	}
	                
	                                catch(Exception e)
	                                {
	                                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed: "+e.getMessage(),LogStatus.FAIL,true);
	                                
	                                }              
	
	
	
	}
	public static void tcPaymentProcessingPMTP083() throws Exception
	{
	               
	 try{
	                               //same account number can be used as in test case 40. Both test case are using only private account
	                               //String acctNumber = getAccountNumberUsingSql("mailidForPmtp081@conduent.com");
	                               String acctNumber = dataObj.get("AccountNumber");
	                                               if(acctNumber.length()>1)
	                                               {
	                                                               //paymentRefundWithoutThreshold(acctNumber);
	                                                               String refundPayType = dataObj.get("Refund Payment Type");
	                                                               String tranType=dataObj.get("TransType");            
	                            String refundAmount=dataObj.get("RefundAmount");         
	
	                            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Refund Pay Type value is : "+
	                                    ""+refundPayType, LogStatus.INFO, true);
	
	                            //search for account
	                            CommonLibrary.searchForAccount(acctNumber);
	                            
	                            //click the link Financial
	                            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='#s_sctrl_tabView_noop' and text()='Financials']")));
	                            FunctionLibrary.clickObject(linkFinancial, "", "click on financials");
	                            String accountBalBeforePayemnt=FunctionLibrary.getText(accountBalTxtBox, "Account Balance");
	
	                            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Existing acccount balance: "+
	                                                   accountBalBeforePayemnt,LogStatus.INFO,false);
	
	                            String categoryValue="TOLDIPOSIT";
	                           // String refundPaymentAmount=dataObj.get("RefundAmount");
	                           // FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='ui-id-268']")));
	                            FunctionLibrary.clickObject(refundButton, "", "click on Refund");
	                            Thread.sleep(3000);
	                            FunctionLibrary.clickObject(newButtonRefund, "", "click on New");
	                            try {
	                                                                Thread.sleep(3000);
	                                                } catch (InterruptedException e1) {
	                                                                // TODO Auto-generated catch block
	                                                                e1.printStackTrace();
	                                                }
	                             String Desc="enter the value for Category Label";
	                             WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver,100);
	                             wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-labelledby='Category_Label']")));
	                            
	                           // FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@aria-labelledby='Category_Label']")));
	                            FunctionLibrary.setText(refundCategoryLabel, categoryValue, Desc);            
	                            FunctionLibrary.clickObject(popUpWindowOkCategoryLabelRefund, "", "click on OK");
	                            try {
	                                                                Thread.sleep(3000);
	                                                } catch (InterruptedException e1) {
	                                                                // TODO Auto-generated catch block
	                                                                e1.printStackTrace();
	                                                }
	                           /* WebDriverWait wait1 = new WebDriverWait(FunctionLibrary.objDriver,100);
	                            wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-labelledby='Payment_Category_Label']"))); */
	                            FunctionLibrary.waitForObject.until(ExpectedConditions.attributeToBe(By.xpath("//input[@aria-labelledby='Payment_Category_Label']"), "aria-readonly", "false"));
	                            FunctionLibrary.setText(paymentRefundLabel, categoryValue, "enter Payment Value");
	                            FunctionLibrary.clickObject(popUpWindowPaymentTransactionRefund, "", "click on OK");
	                            String AmountToRefunded=dataObj.get("RefundAmount");
	                            try {
	                                                                Thread.sleep(3000);
	                                                } catch (InterruptedException e1) {
	                                                                // TODO Auto-generated catch block
	                                                                e1.printStackTrace();
	                                                }
	                           // WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,100);
	                           // wait2.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-labelledby='Amount_Label']")));
	                            FunctionLibrary.setText(amountLabel, AmountToRefunded, "enter amount for Refund");
	                            String refundCode="OTHER";
	                            FunctionLibrary.setText(refundCodeLabel, refundCode, "enter refund code");
	                            try {
	                                                                Thread.sleep(2000);
	                                                } catch (InterruptedException e1) {
	                                                                // TODO Auto-generated catch block
	                                                                e1.printStackTrace();
	                                                }
	                            FunctionLibrary.clickObject(refundSaveBtn, "", "click on save");
	                            try {
	                                                                Thread.sleep(3000);
	                                                } catch (InterruptedException e1) {
	                                                                // TODO Auto-generated catch block
	                                                                e1.printStackTrace();
	                                                }
	                            FunctionLibrary.clickObject(refundBtn, "", "click on Refund");      
	                          
	                            String processNum = null;
	                            try {
	
	                                WebDriverWait wait3 = new WebDriverWait(FunctionLibrary.objDriver,40);
	                                wait3.until(ExpectedConditions.alertIsPresent());
	                                Alert alert = FunctionLibrary.objDriver.switchTo().alert();
	                                String alertText = alert.getText();
	                                System.out.println(alertText);
	                                processNum = alertText.split("Payment Confirmation Number : ")[1];
	                                processNum=processNum.split(" ")[0].trim();
	                                System.out.println(processNum);
	                                alert.accept();
	                                //Thread.sleep(3000);
	
	                            }
	                            catch (Exception e) {
	                                //exception handling
	                            }
	
	                            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@title='Financial Transaction List:Reverse']")));
	                                                   
	                                   String accountBalAfterRefund=FunctionLibrary.getText(accountBalTxtBox, "Account Balance");
	                                   accountBalAfterRefund=accountBalAfterRefund.replace("$", "");
	                                   accountBalAfterRefund=accountBalAfterRefund.replace(",", "");  
	                                   
	                                  String xpathOfFinancialTable = financialTransactionTable;
	                              int rowNumber1 = FunctionLibrary.getRowNumberFromWebTable(xpathOfFinancialTable, "D", "Get the row number");
	                              System.out.println(rowNumber1);
	                              
	                              FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable, rowNumber1, 2, "CHECK",
	                                      "Verifying payType", true);
	                              FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable,rowNumber1,3,
	                                      "D","Verifying Transaction Type ", false);      
	                                           
	                             //  verifyRefundInfoUnderPaymentNotes(refundPayType);  
	                              
	                              FunctionLibrary.objDriver.findElement(By.xpath("//a[text()='Payment Notes']")).click();
	                              FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Payments List:Go']")));
	                              String notesTable = "//table[@summary='Payments List']";
	                              
	                              String paymentRefundType=dataObj.get("Refund Payment Type");
	                              FunctionLibrary.verifyWebTableCellData(notesTable, 1, 1, paymentRefundType, "<b>Notes:</b><br>Verify payment type", true);
	                             // FunctionLibrary.verifyWebTableCellData(notesTable, 2, 1, refundPayType, "Verify refund type", false);
	                                                     
	                                           FunctionLibrary.clickObject("//a[text()='History']", "", "Click history link");
	                                          FunctionLibrary.clickObject(financialsUnderHistroy,"","Click Financial under history");
	                                          //FunctionLibrary.clickObject(financialHistroyTable,"","Click Financial history table");
	                                          FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Account Financials History List:View CC']")));
	                                         verifyRefundInfoUnderHistoryTab(processNum,refundPayType, tranType,"TOLDEPOSIT","REFUND", 
	                                                                  refundAmount);
	                                          accountBalBeforePayemnt=accountBalBeforePayemnt.replace(",", "").replace("$", "");
	                                          if(Double.valueOf(accountBalBeforePayemnt).equals(Double.valueOf(accountBalAfterRefund)))
	                                          {
	                                              ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Account Balance after Refund Amount :"+accountBalBeforePayemnt+
	                                                      "<br>"+"The Account Balance is :"+accountBalAfterRefund, LogStatus.FAIL, true);
	
	                                          }
	                                          else
	                                          {
	                                              ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Account Balance after Refund Amount :"+ accountBalAfterRefund +
	                                                      "<br>"+"Actual:Account Balance is :"+accountBalAfterRefund, LogStatus.PASS, true);
	
	                                          }                                               
	                            
	                                String accountBalAfterRefund1=FunctionLibrary.getText(accountBalTxtBox, "Account Balance");
	                                String amountShowing = FunctionLibrary.getText("//input[@aria-label='PPTL Bal']", "Get the Amount");
	                                amountShowing = amountShowing.replace(",", "").replace("$", "");
	                                accountBalAfterRefund1 = accountBalAfterRefund1.replace(",", "").replace("$", "");
	                                if(Double.valueOf(accountBalAfterRefund1).equals(Double.valueOf(amountShowing)))
	                                {
	                                   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Amount:: "+accountBalAfterRefund+"<br> Actual: "+amountShowing, 
	                                                                   LogStatus.PASS,false);
	                                }else
	                                {
	                                   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Amount:: "+accountBalAfterRefund+"<br> Actual: "+amountShowing, 
	                                                                   LogStatus.FAIL,false);
	                                }
	                                               }
	
	
	                                               }
	                
	                                catch(Exception e)
	                               {
	                                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed: "+e.getMessage(),LogStatus.FAIL,true);
	                               
	                                }
	
	}
	public static void tcPaymentProcessingPMTP079() throws Exception
	{
	                
	try{
	                                //same account number can be used as in test case 40. Both test case are using only private account
	                                //String acctNumber = getAccountNumberUsingSql("mailidForPmtp040@conduent.com");
	                                String acctNumber = dataObj.get("AccountNumber");
	                                                if(acctNumber.length()>1)
	                                                {
	                                                                paymentRefund(acctNumber,dataObj.get("Is WithIn Threshold"));
	                                                }
	                }
	                                catch(Exception e)
	                                {
	                                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed: "+e.getMessage(),LogStatus.FAIL,true);
	                                
	                                }
	}
	
	public static void tcPaymentProcessingPMTP086() throws Exception
	{
	                
	try{
	                                //same account number can be used as in test case 40. Both test case are using only private account
	                                //String acctNumber = getAccountNumberUsingSql("mailidForPmtp040@conduent.com");
	                                String acctNumber = dataObj.get("AccountNumber");
	                                                if(acctNumber.length()>1)
	                                                {
	                                                	            oneTimePayment(acctNumber);
	                                                                paymentRefundForSRCreation(acctNumber,dataObj.get("Is WithIn Threshold"));
	                                                }
	                }
	                                catch(Exception e)
	                                {
	                                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed: "+e.getMessage(),LogStatus.FAIL,true);
	                                
	                                }
	}
	public static void tcPaymentProcessingPMTP090() throws Exception
	{
	                
	try{
	                                //same account number can be used as in test case 40. Both test case are using only private account
	                                //String acctNumber = getAccountNumberUsingSql("mailidForPmtp040@conduent.com");
	                                String acctNumber = dataObj.get("AccountNumber");
	                                                if(acctNumber.length()>1)
	                                                {
	                                                                
	                                                                //paymentRefundWithoutThreshold(acctNumber);
	                                                                String refundPayType = dataObj.get("Refund Payment Type");
	                                                                dataObj.get("TransType");            
	                            dataObj.get("RefundAmount");         
	
	                            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Refund Pay Type value is : "+
	                                    ""+refundPayType, LogStatus.INFO, true);
	
	                            //search for account
	                            CommonLibrary.searchForAccount(acctNumber);
	                            
	                            //click the link Financial
	                            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='#s_sctrl_tabView_noop' and text()='Financials']")));
	                            FunctionLibrary.clickObject(linkFinancial, "", "click on financials");
	                            String accountBalBeforePayemnt=FunctionLibrary.getText(accountBalTxtBox, "Account Balance");
	
	                            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Existing acccount balance: "+
	                                                   accountBalBeforePayemnt,LogStatus.INFO,false);
	
	                            String categoryValue="TOLDIPOSIT";
	                           // String refundPaymentAmount=dataObj.get("RefundAmount");
	                           // FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='ui-id-268']")));
	                            FunctionLibrary.clickObject(refundButton, "", "click on Refund");
	                            Thread.sleep(3000);
	                            FunctionLibrary.clickObject(newButtonRefund, "", "click on New");
	                            try {
	                                                                Thread.sleep(3000);
	                                                } catch (InterruptedException e1) {
	                                                                // TODO Auto-generated catch block
	                                                                e1.printStackTrace();
	                                                }
	                             String Desc="enter the value for Category Label";
	                             WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver,100);
	                             wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-labelledby='Category_Label']")));
	                            
	                           // FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@aria-labelledby='Category_Label']")));
	                            FunctionLibrary.setText(refundCategoryLabel, categoryValue, Desc);            
	                            FunctionLibrary.clickObject(popUpWindowOkCategoryLabelRefund, "", "click on OK");
	                            try {
	                                                                Thread.sleep(3000);
	                                                } catch (InterruptedException e1) {
	                                                                // TODO Auto-generated catch block
	                                                               e1.printStackTrace();
	                                                }
	                           /* WebDriverWait wait1 = new WebDriverWait(FunctionLibrary.objDriver,100);
	                            wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-labelledby='Payment_Category_Label']"))); */
	                            FunctionLibrary.waitForObject.until(ExpectedConditions.attributeToBe(By.xpath("//input[@aria-labelledby='Payment_Category_Label']"), "aria-readonly", "false"));
	                            FunctionLibrary.setText(paymentRefundLabel, categoryValue, "enter Payment Value");
	                            FunctionLibrary.clickObject(popUpWindowPaymentTransactionRefund, "", "click on OK");
	                            String AmountToRefunded=dataObj.get("RefundAmount");
	                            try {
	                                                                Thread.sleep(3000);
	                                                } catch (InterruptedException e1) {
	                                                                // TODO Auto-generated catch block
	                                                                e1.printStackTrace();
	                                                }
	                           // WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,100);
	                           // wait2.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-labelledby='Amount_Label']")));
	                            FunctionLibrary.setText(amountLabel, AmountToRefunded, "enter amount for Refund");
	                            String refundCode="OTHER";
	                            FunctionLibrary.setText(refundCodeLabel, refundCode, "enter refund code");
	                            try {
	                                                                Thread.sleep(2000);
	                                                } catch (InterruptedException e1) {
	                                                                // TODO Auto-generated catch block
	                                                                e1.printStackTrace();
	                                                }
	                            FunctionLibrary.clickObject(refundSaveBtn, "", "click on save");
	                            try {
	                                                                Thread.sleep(3000);
	                                                } catch (InterruptedException e1) {
	                                                                // TODO Auto-generated catch block
	                                                                e1.printStackTrace();
	                                                }
	                            FunctionLibrary.clickObject(refundBtn, "", "click on Refund");      
	                          
	                            String processNum = null;
	                            try {
	
	                                WebDriverWait wait3 = new WebDriverWait(FunctionLibrary.objDriver,40);
	                                wait3.until(ExpectedConditions.alertIsPresent());
	                                Alert alert = FunctionLibrary.objDriver.switchTo().alert();
	                                String alertText = alert.getText();
	                                System.out.println(alertText);
	                                processNum = alertText.split("Payment Confirmation Number : ")[1];
	                                processNum=processNum.split(" ")[0].trim();
	                                System.out.println(processNum);
	                                alert.accept();
	                                //Thread.sleep(3000);
	
	                            }
	                            catch (Exception e) {
	                                //exception handling
	                            }
	
	                            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@title='Financial Transaction List:Reverse']")));
	                                                   
	                                   String accountBalAfterRefund=FunctionLibrary.getText(accountBalTxtBox, "Account Balance");
	                                   accountBalAfterRefund=accountBalAfterRefund.replace("$", "");
	                                   accountBalAfterRefund=accountBalAfterRefund.replace(",", "");  
	                                   
	                                  String xpathOfFinancialTable = financialTransactionTable;
	                              int rowNumber1 = FunctionLibrary.getRowNumberFromWebTable(xpathOfFinancialTable, "D", "Get the row number");
	                              System.out.println(rowNumber1);
	                              
	                              FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable, rowNumber1, 2, "CHECK",
	                                      "Verifying payType", true);
	                              FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable,rowNumber1,3,
	                                      "D","Verifying Transaction Type ", false);  
	                             // FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable,rowNumber1,26,
	                              //        "NEW","Verifying Refund Status ", false);
	                              
	                              FunctionLibrary.clickObject(firstLevelViewBar, "", "click on First Level View Bar");
	                                           
	                                            
	                                          FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(selectRefundAutomationCheck, firstLevelViewBar, 10, 30);
	                                          FunctionLibrary.clickObject(selectRefundAutomationCheck, "", "Select Refund Automation check from the DropDown");
	                                          ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"selecting Refund Automation checkh from dropdown",LogStatus.INFO,true);
	                                                      FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(queryRefundAutomationCheckBtn, selectRefundAutomationCheck, 10, 30);
	                                                                  Thread.sleep(2000);
	                                                                //click on query
	                                                    FunctionLibrary.clickObject(queryRefundAutomationCheckBtn, "", "click on Query");
	                                                    
	                                                                //FunctionLibrary.clickObject(notificationSearchLabel, "", "clicking on notification id textbox");
	                                                                FunctionLibrary.setText(accountNumTxtBox, acctNumber, "enter account number");
	                                                                FunctionLibrary.clickObject(refundCheckStatusGoBtn, "", "click on go");
	                                                                Thread.sleep(2000);
	                                                                FunctionLibrary.clickObject(refundCheckStatusApproveBtn, "", "click on Approve");
	                                                                
	                                                                 String xpathOfRefundCheckAutomationTable = refundCheckAutomationTable;
	                                FunctionLibrary.getRowNumberFromWebTable(xpathOfRefundCheckAutomationTable, "APPROVED", "Get the row number");
	                                System.out.println(rowNumber1);
	                              
	                                FunctionLibrary.verifyWebTableCellData(xpathOfRefundCheckAutomationTable, rowNumber1, 4, "APPROVED",
	                                      "Verifying Refund status", true);                                                                   
	                                           
	                                                }
	                                                
	}
	                                catch(Exception e)
	                                {
	                                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed: "+e.getMessage(),LogStatus.FAIL,true);
	                                
	                                }
	}
	
	public static void tcPaymentProcessingPMTP092() throws Exception
{
                
try{
                                //same account number can be used as in test case 40. Both test case are using only private account
                                //String acctNumber = getAccountNumberUsingSql("mailidForPmtp040@conduent.com");
                                String acctNumber = dataObj.get("AccountNumber");
                                                if(acctNumber.length()>1)
                                                {
                                                                //paymentRefund(acctNumber,dataObj.get("Is WithIn Threshold"));
                                                                //paymentRefundWithoutThreshold(acctNumber);
                                                                String refundPayType = dataObj.get("Refund Payment Type");
                                                               dataObj.get("TransType");            
                            dataObj.get("RefundAmount");         

                            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Refund Pay Type value is : "+
                                    ""+refundPayType, LogStatus.INFO, true);

                            //search for account
                            CommonLibrary.searchForAccount(acctNumber);
                            
                            //click the link Financial
                            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='#s_sctrl_tabView_noop' and text()='Financials']")));
                            FunctionLibrary.clickObject(linkFinancial, "", "click on financials");
                            String accountBalBeforePayemnt=FunctionLibrary.getText(accountBalTxtBox, "Account Balance");

                            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Existing acccount balance: "+
                                                   accountBalBeforePayemnt,LogStatus.INFO,false);

                            String categoryValue="TOLDIPOSIT";
                           // String refundPaymentAmount=dataObj.get("RefundAmount");
                           // FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='ui-id-268']")));
                            FunctionLibrary.clickObject(refundButton, "", "click on Refund");
                            Thread.sleep(3000);
                            FunctionLibrary.clickObject(newButtonRefund, "", "click on New");
                            try {
                                                                Thread.sleep(3000);
                                                } catch (InterruptedException e1) {
                                                                // TODO Auto-generated catch block
                                                                e1.printStackTrace();
                                                }
                             String Desc="enter the value for Category Label";
                             WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver,100);
                             wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-labelledby='Category_Label']")));
                            
                           // FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@aria-labelledby='Category_Label']")));
                            FunctionLibrary.setText(refundCategoryLabel, categoryValue, Desc);            
                            FunctionLibrary.clickObject(popUpWindowOkCategoryLabelRefund, "", "click on OK");
                            try {
                                                                Thread.sleep(3000);
                                                } catch (InterruptedException e1) {
                                                                // TODO Auto-generated catch block
                                                                e1.printStackTrace();
                                                }
                           /* WebDriverWait wait1 = new WebDriverWait(FunctionLibrary.objDriver,100);
                            wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-labelledby='Payment_Category_Label']"))); */
                            FunctionLibrary.waitForObject.until(ExpectedConditions.attributeToBe(By.xpath("//input[@aria-labelledby='Payment_Category_Label']"), "aria-readonly", "false"));
                            FunctionLibrary.setText(paymentRefundLabel, categoryValue, "enter Payment Value");
                            FunctionLibrary.clickObject(popUpWindowPaymentTransactionRefund, "", "click on OK");
                            String AmountToRefunded=dataObj.get("RefundAmount");
                            try {
                                                                Thread.sleep(3000);
                                                } catch (InterruptedException e1) {
                                                                // TODO Auto-generated catch block
                                                                e1.printStackTrace();
                                                }
                           // WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,100);
                           // wait2.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-labelledby='Amount_Label']")));
                            FunctionLibrary.setText(amountLabel, AmountToRefunded, "enter amount for Refund");
                            String refundCode="OTHER";
                            FunctionLibrary.setText(refundCodeLabel, refundCode, "enter refund code");
                            try {
                                                                Thread.sleep(2000);
                                                } catch (InterruptedException e1) {
                                                                // TODO Auto-generated catch block
                                                                e1.printStackTrace();
                                                }
                            FunctionLibrary.clickObject(refundSaveBtn, "", "click on save");
                            try {
                                                                Thread.sleep(3000);
                                                } catch (InterruptedException e1) {
                                                                // TODO Auto-generated catch block
                                                                e1.printStackTrace();
                                                }
                            FunctionLibrary.clickObject(refundBtn, "", "click on Refund");      
                          
                            String processNum = null;
                            try {

                                WebDriverWait wait3 = new WebDriverWait(FunctionLibrary.objDriver,40);
                                wait3.until(ExpectedConditions.alertIsPresent());
                                Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                                String alertText = alert.getText();
                                System.out.println(alertText);
                                processNum = alertText.split("Payment Confirmation Number : ")[1];
                                processNum=processNum.split(" ")[0].trim();
                                System.out.println(processNum);
                                alert.accept();
                                //Thread.sleep(3000);

                            }
                            catch (Exception e) {
                                //exception handling
                            }

                            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@title='Financial Transaction List:Reverse']")));
                                                   
                                   String accountBalAfterRefund=FunctionLibrary.getText(accountBalTxtBox, "Account Balance");
                                   accountBalAfterRefund=accountBalAfterRefund.replace("$", "");
                                   accountBalAfterRefund=accountBalAfterRefund.replace(",", "");  
                                   
                                  String xpathOfFinancialTable = financialTransactionTable;
                              int rowNumber1 = FunctionLibrary.getRowNumberFromWebTable(xpathOfFinancialTable, "D", "Get the row number");
                              System.out.println(rowNumber1);
                              
                              FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable, rowNumber1, 2, "CHECK",
                                      "Verifying payType", true);
                              FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable,rowNumber1,3,
                                      "D","Verifying Transaction Type ", false);  
                            //  FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable,rowNumber1,26,
                               //       "NEW","Verifying Refund Status ", false);
                              
                              FunctionLibrary.clickObject(firstLevelViewBar, "", "click on First Level View Bar");
                                           
                                            
                                          FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(selectRefundAutomationCheck, firstLevelViewBar, 10, 30);
                                          FunctionLibrary.clickObject(selectRefundAutomationCheck, "", "Select Refund Automation check from the DropDown");
                                          ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"selecting Refund Automation checkh from dropdown",LogStatus.INFO,true);
                                                      FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(queryRefundAutomationCheckBtn, selectRefundAutomationCheck, 10, 30);
                                                                  Thread.sleep(2000);
                                                                //click on query
                                                    FunctionLibrary.clickObject(queryRefundAutomationCheckBtn, "", "click on Query");
                                                    
                                                                //FunctionLibrary.clickObject(notificationSearchLabel, "", "clicking on notification id textbox");
                                                                FunctionLibrary.setText(accountNumTxtBox, acctNumber, "enter account number");
                                                                FunctionLibrary.clickObject(refundCheckStatusGoBtn, "", "click on go");
                                                                /*FunctionLibrary.clickObject(refundCheckStatusApproveBtn, "", "click on Approve");
                                                                
                                                                String xpathOfRefundCheckAutomationTable = refundCheckAutomationTable;
                                int rowNumber = FunctionLibrary.getRowNumberFromWebTable(xpathOfRefundCheckAutomationTable, "APPROVED", "Get the row number");
                                System.out.println(rowNumber1);
                              
                                FunctionLibrary.verifyWebTableCellData(xpathOfRefundCheckAutomationTable, rowNumber1, 4, "APPROVED",
                                     "Verifying Refund status", true);*/
                                FunctionLibrary.clickObject(clickOnPrintButton, "", "click on Print");
                                FunctionLibrary.clickObject(clickOnOkPopupWindowForPrintingSubmissionMessage, "", "click on OK");
                                
                               /* WebDriverWait wait3 = new WebDriverWait(FunctionLibrary.objDriver,40);
                wait3.until(ExpectedConditions.alertIsPresent());
                Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                String alertText = alert.getText();
                System.out.println(alertText);*/
                                          
                                }

                                                
                }
                                catch(Exception e)
                                {
                                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed: "+e.getMessage(),LogStatus.FAIL,true);
                                
                                }
}

    
    public static void tcPaymentProcessingPMTPMakeInsufficentBal() throws InterruptedException {
                try
                {
            String acctNumber =dataObj.get("AccountNumber");
            if(acctNumber.length()>1)
            {
                                makefinancialStatusAsInsuffient(acctNumber);    
            }
                                }
                                catch(Exception e)
                                {
                                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed: "+e.getMessage(),LogStatus.FAIL,true);
                                
                                }
    }
    
    public static void tcPaymentProcessingPMTPMakeLowBal() throws InterruptedException {
                try
                {
            String acctNumber =dataObj.get("AccountNumber");
            if(acctNumber.length()>1)
            {
                makefinancialStatusAsLow(acctNumber);    
            }
                                }
                                catch(Exception e)
                                {
                                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed: "+e.getMessage(),LogStatus.FAIL,true);
                                
                                }
    }
       
    public static void placeAdjustmentAndVerifyFinancialHistory(String accNum, String isWithInThreshold) throws InterruptedException
    {
        String Desc = ""; 
        
        String [] adjustAmountItems;
        String adjustAmount = dataObj.get("AdjustInfo");
        adjustAmountItems=adjustAmount.split(":");
        String isDebitOrCrditType = adjustAmountItems[0];
        String adjustmentCategoryType = adjustAmountItems[1];
        String adjustmentSubCategoryType = adjustAmountItems[2];
        String quantity = "1";
       String amount;
        String payType = dataObj.get("Payment Type");
        
        
        String [] minMaxThresholdValues  = getMinMaxThresholdValues(dataObj.get("User Responsibility"),payType,"ADJUSTMENT");
                                String minimumThresholdBal = minMaxThresholdValues[0];
                                String maximumThresholdBal= minMaxThresholdValues[1];
       //reading less value for minimum threshold value
       String existingAmount=minimumThresholdBal;
       existingAmount=existingAmount.replace(",","");
       
       Double amountToBeAdjusted;
       if(isWithInThreshold.equalsIgnoreCase("Yes"))
       {
                   amountToBeAdjusted=Double.valueOf(minimumThresholdBal)-1;   
       }
       else
       {
                   amountToBeAdjusted=Double.valueOf(maximumThresholdBal)+1;
       }
       amount = String.valueOf(amountToBeAdjusted);
                CommonLibrary.searchForAccount(accNum); 
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Adjustment category type: "+ adjustmentCategoryType +", Sub category type: "+ 
                adjustmentSubCategoryType +", Quanity: "+quantity+", Amount: "+amount, LogStatus.INFO, false);
                
        FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(accNum)));
        FunctionLibrary.objDriver.findElement(By.linkText(accNum)).click();
        FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 60, ChallangeQA2Dropdown);

        //click on financials tab
        Desc = "Clicking Financials tab";
        FunctionLibrary.clickObject(financialsTab, "", Desc);
        //click on adjustments tab
        FunctionLibrary.clickObject(adjustmentsTab, "", Desc);
        String initalAccountBal;
        WebElement el= FunctionLibrary.objDriver.findElement(By.xpath(pptlBalance));
        initalAccountBal = el.getAttribute("value");
        System.out.println("Amount Before adjustment"+  initalAccountBal);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>"
                + "Amount Before adjustment "+  initalAccountBal, LogStatus.INFO, true);
        initalAccountBal=initalAccountBal.replace("(", "").replace(")", "").replace("$", "").replace(",", "");

        FunctionLibrary.scrollDown("New", "Scroll down");
        //click on new button @account payment tab
        Desc = "Clicking on new button";
        FunctionLibrary.clickObject(adjustmentsNewBtn, "", Desc);
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(categoryLabel, adjustmentsNewBtn, 6, 24);
        FunctionLibrary.waitForObject.until(ExpectedConditions.attributeToBe(By.xpath(categoryTxtBox), "aria-readonly", "false"));
        //enter Category_Label
        Desc = "Entering Category_Label";
        Desc = "Entering Sub category label";
        FunctionLibrary.setText(subCategoryLabel, adjustmentSubCategoryType, Desc);
        
        //enter qunantity
        Desc = "Entering Unit_Price_Label";
        FunctionLibrary.setText(quantityLabel, quantity, Desc);
        //enter Unit_Price_Label
        Desc = "Entering Unit_Price_Label";
        FunctionLibrary.setText(unitPriceLabel, amount, Desc);
        
        Desc = "clicking on Adjust button";
        //FunctionLibrary.ObjDriver.findElement(By.xpath(adjustButton)).click();
        FunctionLibrary.clickObject(""+adjustButton,"","Click adjust button");
        if(isWithInThreshold.equalsIgnoreCase("yes"))
        {
                  //wait for an alert
            // FunctionLibrary.Wait_For_Object = new WebDriverWait(FunctionLibrary.ObjDriver, 60);
             FunctionLibrary.waitForObject.until(ExpectedConditions.alertIsPresent());
             Alert alert = FunctionLibrary.objDriver.switchTo().alert();
             System.out.println("Popup is displayed with text" + alert.getText());
             String alertText = alert.getText();
             alert.accept();
             //get payment reference number
             String[] paymentRefNumber = alertText.split(":");
             String transactionId= paymentRefNumber[1].trim();
             System.out.println(transactionId);
             ReportLibrary.addStep(ReportLibrary.Test_Step_Number,  "Transaction processed. Payment Reference number " + transactionId, LogStatus.INFO, true);
           
                        String accountBalanceAfterUpdate;
                        el= FunctionLibrary.objDriver.findElement(By.xpath(pptlBalance));
                        accountBalanceAfterUpdate = el.getAttribute("value");
                
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Amount After adjustment "+  accountBalanceAfterUpdate, LogStatus.INFO, false);
                
                        Double amountForAdjustmentDone=Double.valueOf(quantity)*Double.valueOf(amount);
                        
                        //reading less value for minimum threshold value
                        String existingAccBal=accountBalanceAfterUpdate;
                        existingAccBal=existingAccBal.replace("$","");
                        existingAccBal=existingAccBal.replace(",","");
                        Double totalAmountAfterAdjustment;
                        if(isDebitOrCrditType.equalsIgnoreCase("DEBIT"))
                        {
                                totalAmountAfterAdjustment = Double.valueOf(initalAccountBal) - Double.valueOf(amountForAdjustmentDone);
                        }
                        else
                        {
                                totalAmountAfterAdjustment = Double.valueOf(initalAccountBal) + Double.valueOf(amountForAdjustmentDone);
                        }
                         
                        int rowNum = FunctionLibrary.getRowNumberFromWebTable(financialTransactionListTable,transactionId,"get the row number");
                        FunctionLibrary.verifyWebTableCellData(financialTransactionListTable,rowNum,2,"NONE","<b>Financial Transactions:</b><br>Verifying Pay type",true);
                        
                        String amountShowingAsAdjustmentDone=FunctionLibrary.getWebTableCellData(financialTransactionListTable,rowNum,4,"Get amount done for ajustment");
                        amountShowingAsAdjustmentDone=amountShowingAsAdjustmentDone.replace("(", "");
                        amountShowingAsAdjustmentDone=amountShowingAsAdjustmentDone.replace(")", "");
                        amountShowingAsAdjustmentDone=amountShowingAsAdjustmentDone.replace("$", "");
                        amountShowingAsAdjustmentDone=amountShowingAsAdjustmentDone.replace(",", "");
                        
                        if(Double.valueOf(amountForAdjustmentDone).equals(Double.valueOf(amountShowingAsAdjustmentDone)))
                        {
                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Financial Transaction table should show amount as :"+amountForAdjustmentDone
                                    +"<br>"+"Actual: Displaying: "+amountShowingAsAdjustmentDone, LogStatus.PASS, true);
                        }
                        else
                        {
                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Financial Transaction table should show amount as :"+amountForAdjustmentDone
                                    +"<br>"+"Actual: Displaying: "+amountShowingAsAdjustmentDone, LogStatus.FAIL, true);
                        }
                        
                        verifyAdjustmentNotes(dataObj.get("Payment Type"), String.valueOf(amountForAdjustmentDone));
                     
                        //Verify in financial history table
                        FunctionLibrary.clickObject(historyTab, "", "Click history link");
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Account Balance after Adding Amount :"+"<br>"+"The Account Balance is :"+
                                                                                                                totalAmountAfterAdjustment, LogStatus.INFO, true);
                        FunctionLibrary.clickObject(financialsUnderHistroy,"","Click Financial under history");
                        FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOf(FunctionLibrary.getElement(financialHistroyTable)));
                        String xpathOfAmountTable = financialHistroyTable;
                
                        int rowNumberWithAmount = FunctionLibrary.getRowNumberFromWebTable(xpathOfAmountTable,transactionId, "get the row number");
                        
                        FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable, rowNumberWithAmount, 2,transactionId, "<b>Financial History</b><br>Verifying transaction id", true);
                        FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable, rowNumberWithAmount, 4,dataObj.get("TransType"), "Verifying transaction id", false);
                        FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable, rowNumberWithAmount, 6,adjustmentCategoryType, "Verifying adjustment category type", false);
                        FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable, rowNumberWithAmount, 7,adjustmentSubCategoryType, "Verifying adjustment sub category type", false);
                        FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable,rowNumberWithAmount,8,"NONE","Verifying Payment Type Option", false);
                        
                        String balShowingUnderFinancialHistory=FunctionLibrary.getWebTableCellData(xpathOfAmountTable,rowNumberWithAmount,11,"Get balance showing under finacial history");
                        balShowingUnderFinancialHistory=balShowingUnderFinancialHistory.replace("$", "").replace(",", "").replace("(", "").replace(")", "");
                        //round decimal value
                        totalAmountAfterAdjustment=Math.round(totalAmountAfterAdjustment*100)/100.0d;     
                        if(Double.valueOf(totalAmountAfterAdjustment).equals(Double.valueOf(balShowingUnderFinancialHistory)))
                        {
                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Post Transaction Balance should show in financial history table: "+totalAmountAfterAdjustment
                                    +"<br>"+"Actual: Displaying: "+balShowingUnderFinancialHistory, LogStatus.PASS, true);
                        }
                        else
                        {
                            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Post Transaction Balance should show in financial history table: "+totalAmountAfterAdjustment
                                    +"<br>"+"Actual: But displaying: "+balShowingUnderFinancialHistory, LogStatus.FAIL, true);
                        }
                        
                        
                        String accPptlBalance=FunctionLibrary.getText(ppTlBalTxtBox, "Get PPTL Balance");
                        accPptlBalance=accPptlBalance.replace("$","").replace(",","").replace("(","").replace(")","");
                        
                        if(Double.valueOf(totalAmountAfterAdjustment).equals(Double.valueOf(accPptlBalance)))
                        {
                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: PPTL Balance should be: "+totalAmountAfterAdjustment
                                    +"<br>"+"Actual: Displaying: "+accPptlBalance, LogStatus.PASS, true);
                        }
                        else
                        {
                            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: PPTL Balance should be: "+totalAmountAfterAdjustment
                                    +"<br>"+"Actual: But displaying: "+accPptlBalance, LogStatus.FAIL, true);
                        }
                        
        }
        else
        {
            
            int rowNumber1 = FunctionLibrary.getRowNumberFromWebTable(xpathOfFinancialTable, "ESCALATION", "Get the row number");
            System.out.println(rowNumber1);
            
            FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable, rowNumber1, 6, "Open",
                    "Verifying status", true);
            FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable,rowNumber1,4,
                    "ESCALATION","Verifying QUEUE Type ", false);
            FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable,rowNumber1,5,"ADJUSTMENT","Verifying Sub Queue of ESCALATION", false);
            
            String amountShowing = FunctionLibrary.getText(amountTxtBox, "Get the Amount");
            amountShowing = amountShowing.replace(",", "").replace("$", "");
            if(Double.valueOf(amount).equals(Double.valueOf(amountShowing)))
            {
                   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Amount:: "+amount+"<br> Actual: "+amountShowing, 
                                                   LogStatus.PASS,false);
            }else
            {
                   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Amount:: "+amount+"<br> Actual: "+amountShowing, 
                                                   LogStatus.FAIL,false);
            }  
        }

    }
    
    public static void oneTimePayment(String acctNumber) throws InterruptedException
    {
        
        String transactionId="";
        String amountPaid;

      //search for account
     CommonLibrary.searchForAccount(acctNumber);  
     String AccountBal=FunctionLibrary.getText(accountBalTxtBox, "Account Balance");
     String existingAmount=AccountBal;
     existingAmount=existingAmount.replace("$","").replace("(","").replace(")","").replace(",","");
     
     amountPaid = dataObj.get("Payment Amount");        
     Double amount = Double.valueOf(existingAmount);
     Double finalAccountBal=amount+Double.valueOf(amountPaid);
     ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Account Balance before adding Amount :"+"<br>"+
                                "Account Balance is :"+existingAmount, LogStatus.INFO, true);
     
     //click the link Financial
     FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(linkFinancial)));
     FunctionLibrary.clickObject(linkFinancial, "", "click on financials");
     FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(accountReplenishment,linkFinancial,15,34);
     //FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(accountReplenishment)));
     FunctionLibrary.clickObject(accountReplenishment, "", "click on accountReplenishment");
     FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(newBtnAmount,accountReplenishment,15,34);
     FunctionLibrary.clickObject(newBtnAmount, "", "click on newBtnAmount");
     FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(amonutTxtBox,newBtnAmount,15,34);
     Thread.sleep(3000);
     FunctionLibrary.waitForObject.until(ExpectedConditions.attributeToBe(By.xpath(balanceForwardTxtBox),
                                "aria-readonly", "false"));
     FunctionLibrary.setText(amonutTxtBox, dataObj.get("Payment Amount"), "Enter Amount");
     
     FunctionLibrary.clickObject(replenishmentSave, "", "click on replenishmentSave");
     FunctionLibrary.scrollDowntoElement(newPaymentList, "Scroll down");
     FunctionLibrary.clickObject(newPaymentList, "", "click on newPaymentList");
     
     
     FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(saveRebillInfo,
                                newPaymentList, 3, 20);
     
                FunctionLibrary.selectDropDownListByValue(replenishmentDetailsRebillPayTypeDropdown,dataObj.get("Payment Type"),"Select payment type from the drop down");
    
     FunctionLibrary.scrollDowntoElement(saveRebillInfo, "Scroll down");
     if(dataObj.get("Payment Type").equalsIgnoreCase("SAVINGS") || dataObj.get("Payment Type").equalsIgnoreCase("CHECKING"))
     {
         //Enter Bank Account no:
         FunctionLibrary.setText(paymentDetailsBankAccountNbrTxtBox,dataObj.get("BankAccount"),"Enter bank number");
         // Enter Routing No:
         FunctionLibrary.setText(paymentDetailsRoutingNbrTxtBox,dataObj.get("BankRoutine"),"Enter Routing number");
     }
    
     if(dataObj.get("Payment Type").contains("CHECK"))
     {
         //Scroll down to Check button
         FunctionLibrary.scrollDowntoElement(checkPay, "Scroll dwon to Check number field");
         //Enter CHECK no:
         FunctionLibrary.setText("//input[@id='ChkNumber']",dataObj.get("CheckNumber"),"Enter check number");
     }
     
     //Rebill Pay Type with Creditcard
    if(dataObj.get("Payment Type").equalsIgnoreCase("MASTERCARD") || dataObj.get("Payment Type").equalsIgnoreCase("AMEX") || dataObj.get("Payment Type").equalsIgnoreCase("DISCOVER")|| dataObj.get("Payment Type").equalsIgnoreCase("VISA"))
     {
                FunctionLibrary.selectDropDownListByValue(replenishmentDetailsRebillPayTypeDropdown,"CASH","Select payment type from the drop down");
         
                FunctionLibrary.selectDropDownListByValue(replenishmentDetailsRebillPayTypeDropdown,dataObj.get("Payment Type"),"Select payment type from the drop down");
                
         FunctionLibrary.setText(creditCardNoField,dataObj.get("CreditCardNo"),"Enter Credit Card number");
         //Credit card Expiration Month
         FunctionLibrary.selectDropDownListByValue(creditCardExpMpnth,dataObj.get("ExpMonth"),"Select Expiration Month");
         //Credit card Expiration Year
         FunctionLibrary.selectDropDownListByValue(creditCardExpYear,dataObj.get("ExpYear"),"Select Expiration Year");

     }
     FunctionLibrary.clickObject(saveRebillInfo, "", "click on saveRebillInfo");
     
     try {

         WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,10);
         wait2.until(ExpectedConditions.alertIsPresent());
         Alert alert = FunctionLibrary.objDriver.switchTo().alert();
         alert.accept();

     }
     catch (Exception e1) {
         //exception handling
     }

     
     FunctionLibrary.scrollDowntoElement(savePaymentList, "Scroll down");
     FunctionLibrary.clickObject(savePaymentList, "", "click on savePaymentList");
     Thread.sleep(2000);
     try {

         WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,4);
         wait2.until(ExpectedConditions.alertIsPresent());
         Alert alert = FunctionLibrary.objDriver.switchTo().alert();
         alert.accept();

     }
     catch (Exception e) {
         //exception handling
     }
     FunctionLibrary.clickObject(processPay, "", "click on processPay");
     try {

         WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,40);
         wait2.until(ExpectedConditions.alertIsPresent());
         Alert alert = FunctionLibrary.objDriver.switchTo().alert();
         transactionId= alert.getText().split(":")[1].trim();
         alert.accept();
         System.out.println(transactionId);
         ReportLibrary.addStep(ReportLibrary.Test_Step_Number,  "Transaction processed. Payment Reference number " + transactionId, LogStatus.INFO, true);
      
         //Thread.sleep(3000);

     }
     catch (Exception e) {
         //exception handling
     }

     ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Amount Added :"+dataObj.get("Payment Amount"), LogStatus.INFO, true);
     
    verifyFinancialTransactionInfoUnderReversalTab(transactionId,dataObj.get("Payment Type"),dataObj.get("TransType"), amountPaid);
    verifyOneTimePaymentNotes(dataObj.get("Payment Type"),amountPaid);
               FunctionLibrary.clickObject(historyTab, "", "Click history link");
    FunctionLibrary.clickObject(financialsUnderHistroy,"","Click Financial under history");
    FunctionLibrary.clickObject(financialHistroyTable,"","Click Financial history table");
    
     verifyFinancialHistoryInfoUnderHistoryTab(transactionId,dataObj.get("Payment Type"),dataObj.get("TransType"), "TOLDEPOSIT","PPTL", 
                                 amountPaid,String.valueOf(finalAccountBal));
    }
    
    public static void verifyFinancialTransactionInfoUnderReversalTab(String transactionId, String payType, String transType, String amount)
    {
                
                int rowNumberWithAmount = FunctionLibrary.getRowNumberFromWebTable(financialTransListTbl,transactionId, "get the row number");
        
        FunctionLibrary.verifyWebTableCellData(financialTransListTbl, rowNumberWithAmount, 23,transactionId, "<b>Finacial Transaction Table:</b> <br>Verifying transaction id", true);
        FunctionLibrary.verifyWebTableCellData(financialTransListTbl,rowNumberWithAmount,2,payType,"Verifying Payment Type Option", false);
        FunctionLibrary.verifyWebTableCellData(financialTransListTbl,rowNumberWithAmount,3,transType.split(";")[0],"Verifying Transaction Type Option", false);
        
        
        String amountPaidShowing=FunctionLibrary.getWebTableCellData(financialTransListTbl,rowNumberWithAmount,4,"Get amount");
        amountPaidShowing=amountPaidShowing.replace("(", "").replace(")", "").replace("$", "").replace(",", "");
      
        
        if(Double.valueOf(amount).equals(Double.valueOf(amountPaidShowing)))
        {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Financial Transaction table should show amount as :"+amount
                    +"<br>"+"Actual: Displaying: "+amountPaidShowing, LogStatus.PASS, true);
        }
        else
        {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Financial Transaction table should show amount as :"+amount
                    +"<br>"+"Actual: Displaying: "+amountPaidShowing, LogStatus.FAIL, true);
        }
        
        if(payType.equalsIgnoreCase("CHECKING"))
        {
                String maskedBankNumber = "xxxxxx"+dataObj.get("BankAccount").substring(dataObj.get("BankAccount").length() - 4);
                        FunctionLibrary.verifyWebTableCellData(financialTransListTbl,rowNumberWithAmount,11,maskedBankNumber,"Verifying Masked Bank account number", false);
            FunctionLibrary.verifyWebTableCellData(financialTransListTbl,rowNumberWithAmount,12,dataObj.get("BankRoutine"),"Verifying Bank routing number", false);
            
        }
        if(payType.equals("CHECK"))
        {
                        FunctionLibrary.verifyWebTableCellData(financialTransListTbl,rowNumberWithAmount,13,dataObj.get("CheckNumber"),"Verifying check number", false);
            
            
        }
        if(payType.equalsIgnoreCase("MASTERCARD") || payType.equalsIgnoreCase("AMEX") || payType.equalsIgnoreCase("DISCOVER")|| payType.equalsIgnoreCase("VISA"))
        {String creditCardNumberWithMasking = dataObj.get("CreditCardNo").substring(dataObj.get("CreditCardNo").length()-4);
        creditCardNumberWithMasking = "xxxxxxxx"+creditCardNumberWithMasking;
        FunctionLibrary.verifyWebTableCellData(financialTransactionListTable,rowNumberWithAmount,6,creditCardNumberWithMasking,"Verifying credit card number", false);
        FunctionLibrary.verifyWebTableCellData(financialTransactionListTable,rowNumberWithAmount,7,dataObj.get("ExpMonth"),"Verifying expiring month", false);
        FunctionLibrary.verifyWebTableCellData(financialTransactionListTable,rowNumberWithAmount,8,dataObj.get("ExpYear"),"Verifying expiring year", false);
        
        }

                
    }
    
    public static void verifyFinancialHistoryInfoUnderHistoryTab(String transactionId, String payType, String transType, String category, 
                                String subcategory, String amount, String postTxnBal)
    {
    
        String xpathOfAmountTable = financialHistroyTable;

        int rowNumberWithAmount = FunctionLibrary.getRowNumberFromWebTable(xpathOfAmountTable,transactionId, "get the row number");
        
        FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable, rowNumberWithAmount, 2,transactionId, "<b>Finacial History Table:</b> <br>Verifying transaction id", true);
        FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable,rowNumberWithAmount,8,payType,"Verifying Payment Type Option", false);
        
        String balShowingUnderFinancialHistory=FunctionLibrary.getWebTableCellData(xpathOfAmountTable,rowNumberWithAmount,11,"Get balance showing under finacial history");
        
        balShowingUnderFinancialHistory=balShowingUnderFinancialHistory.replace("(", "").replace(")", "").replace("$", "").replace(",", "");


        FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable,rowNumberWithAmount,4,transType.split(";")[0],"Verifying category", false);
        FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable,rowNumberWithAmount,6,category,"Verifying sub category", false);
        FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable,rowNumberWithAmount,7,subcategory,"Verifying sub category Type Option", false);
           
        
        if(Double.valueOf(postTxnBal).equals(Double.valueOf(balShowingUnderFinancialHistory)))
        {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Post Transaction Balance should show in financial history table: "+postTxnBal
                    +"<br>"+"Actual: Displaying: "+balShowingUnderFinancialHistory, LogStatus.PASS, true);
        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Post Transaction Balance should show in financial history table: "+postTxnBal
                    +"<br>"+"Actual: But displaying: "+balShowingUnderFinancialHistory, LogStatus.FAIL, true);
        }

        String accountMainBalShowing=FunctionLibrary.getText(accountBalTxtBox, "Account Balance");
        accountMainBalShowing=accountMainBalShowing.replace("$", "").replace(",", "");
   
        if(Double.valueOf(postTxnBal).equals(Double.valueOf(accountMainBalShowing)))
        {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Account balance should be displayed"+postTxnBal
                    +"<br>"+"Actual: Displaying: "+accountMainBalShowing, LogStatus.PASS, false);
        }
        else
        {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Account balance should be displayed"+postTxnBal
                    +"<br>"+"Actual: Displaying: "+accountMainBalShowing, LogStatus.FAIL, true);
        }

                
    }

    public static boolean verifyReversalTransactionInfoUnderReversalTab(String transactionId, String payType, String []transType, String amount)
    {
                boolean isXrTransTypeItemCreated = false;
                
                String xrTransactionId = String.valueOf(Integer.valueOf(transactionId)+1);
                int rowWithXrNumber = 0;
                for(int iterator=0;iterator<=3;iterator++)
                {
                                
                                rowWithXrNumber =FunctionLibrary.getRowNumberFromWebTable(financialTransListTbl, xrTransactionId, "Get the row number with transaction id of XR");
                                if(rowWithXrNumber==1)
                                {
                                                break;
                                }
                                else
                                {
                                                FunctionLibrary.objDriver.navigate().refresh();
                                                try {
                                                                                Thread.sleep(8000);
                                                                } catch (InterruptedException e) {
                                                                                // TODO Auto-generated catch block
                                                                                e.printStackTrace();
                                                                }
                                                
                                }
                }
                if(rowWithXrNumber==1)
                {
                                
                                isXrTransTypeItemCreated = true;
                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: System should generate auto XR item. Actual: Created with id: "+xrTransactionId, 
                                                                LogStatus.PASS, true);
                                int rowNumberWithAmount = FunctionLibrary.getRowNumberFromWebTable(financialTransListTbl,transactionId, "get the row number");
            
            FunctionLibrary.verifyWebTableCellData(financialTransListTbl, rowNumberWithAmount, 23,transactionId, "<b>Finacial Transaction Table:</b> <br>Verifying transaction id", true);
            FunctionLibrary.verifyWebTableCellData(financialTransListTbl,rowNumberWithAmount,2,payType,"Verifying Payment Type Option", false);
            FunctionLibrary.verifyWebTableCellData(financialTransListTbl,rowNumberWithAmount,3,transType[0],"Verifying Transaction Type Option", false);
            
            String amountPaidShowing=FunctionLibrary.getWebTableCellData(financialTransListTbl,rowNumberWithAmount,4,"Get amount");
            amountPaidShowing=amountPaidShowing.replace("(", "").replace(")", "").replace("$", "").replace(",", "");
          
            
            if(Double.valueOf(amount).equals(Double.valueOf(amountPaidShowing)))
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Financial Transaction table should show amount as :"+amount
                        +"<br>"+"Actual: Displaying: "+amountPaidShowing, LogStatus.PASS, true);
            }
            else
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Financial Transaction table should show amount as :"+amount
                        +"<br>"+"Actual: Displaying: "+amountPaidShowing, LogStatus.FAIL, true);
            }
            
            if(payType=="CHECKING")
            {
                            FunctionLibrary.verifyWebTableCellData(financialTransListTbl,rowNumberWithAmount,11,dataObj.get("BankAccount"),"Verifying Bank account number", false);
                FunctionLibrary.verifyWebTableCellData(financialTransListTbl,rowNumberWithAmount,12,dataObj.get("BankRoutine"),"Verifying Bank routing number", false);
                
            }
            if(payType=="CHECK")
            {
                            FunctionLibrary.verifyWebTableCellData(financialTransListTbl,rowNumberWithAmount,13,dataObj.get("CheckNumber"),"Verifying Bank account number", false);
                
                
            }
            if(payType.equalsIgnoreCase("MASTERCARD") || payType.equalsIgnoreCase("AMEX") || payType.equalsIgnoreCase("DISCOVER")|| payType.equalsIgnoreCase("VISA"))
            {String creditCardNumberWithMasking = dataObj.get("CreditCardNo").substring(dataObj.get("CreditCardNo").length()-4);
            creditCardNumberWithMasking = "xxxxxxxx"+creditCardNumberWithMasking;
            FunctionLibrary.verifyWebTableCellData(financialTransactionListTable,rowNumberWithAmount,6,creditCardNumberWithMasking,"Verifying credit card number", false);
            FunctionLibrary.verifyWebTableCellData(financialTransactionListTable,rowNumberWithAmount,7,dataObj.get("ExpMonth"),"Verifying expiring month", false);
            FunctionLibrary.verifyWebTableCellData(financialTransactionListTable,rowNumberWithAmount,8,dataObj.get("ExpYear"),"Verifying expiring year", false);
            
            }
            
           if( FunctionLibrary.objDriver.findElement(By.xpath(reverseChkBox)).isDisplayed())
           {
                   
                   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Reverse checkbox should be checked.<br>Acutal: Reverse check box is checked",
                                                   LogStatus.PASS, true);
           }
           else
           {
                   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Reverse checkbox should be checked.<br>Acutal: Reverse check box is not checked",
                                                   LogStatus.FAIL, true);
           }
           
           String autoGeneratingTransactionid = String.valueOf(Integer.valueOf(transactionId)+1);
           
           int rowNumberWithAmount1 = FunctionLibrary.getRowNumberFromWebTable(financialTransListTbl,autoGeneratingTransactionid, "get the row number");
           
           FunctionLibrary.verifyWebTableCellData(financialTransListTbl, rowNumberWithAmount1, 23,autoGeneratingTransactionid, "<b>Finacial Transaction Table:</b> <br>Verifying auto generated transaction id", true);
           FunctionLibrary.verifyWebTableCellData(financialTransListTbl,rowNumberWithAmount1,2,payType,"Verifying Payment Type Option", false);
           FunctionLibrary.verifyWebTableCellData(financialTransListTbl,rowNumberWithAmount1,3,transType[1],"Verifying Transaction Type Option", false);
           
           String amountPaidShowing1=FunctionLibrary.getWebTableCellData(financialTransListTbl,rowNumberWithAmount1,4,"Get amount");
           amountPaidShowing1=amountPaidShowing1.replace("(", "").replace(")", "").replace("$", "").replace(",", "");
         
           
           if(Double.valueOf(amount).equals(Double.valueOf(amountPaidShowing1)))
           {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Financial Transaction table should show XR amount as :"+amount
                       +"<br>"+"Actual: Displaying: "+amountPaidShowing1, LogStatus.PASS, true);
           }
           else
           {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Financial Transaction table should show XR amount as :"+amount
                       +"<br>"+"Actual: Displaying: "+amountPaidShowing1, LogStatus.FAIL, true);
           }

                }
                else
                {
                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: System should generate auto XR item. Actual: Not created", 
                                                                LogStatus.FAIL, true);
                                
                }
                                return isXrTransTypeItemCreated;
                     
                
    }

    public static void verifyReversalInfoUnderHistoryTab(String transactionId, String payType, String[] transType, String category, 
                                String subcategory, String amount, String postTxnBal)
    {
    
        

        int rowNumberWithAmount = FunctionLibrary.getRowNumberFromWebTable(xpathOfAmountTable,transactionId, "get the row number");
        rowNumberWithAmount=2;
        FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable, rowNumberWithAmount, 2,transactionId, "<b>Finacial History Table:</b> <br>Verifying transaction id", true);
        FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable,rowNumberWithAmount,8,payType,"Verifying Payment Type Option", false);
        
        String balShowingUnderFinancialHistory=FunctionLibrary.getWebTableCellData(xpathOfAmountTable,rowNumberWithAmount,11,"Get balance showing under finacial history");
        
        balShowingUnderFinancialHistory=balShowingUnderFinancialHistory.replace("(", "").replace(")", "").replace("$", "").replace(",", "");


        FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable,rowNumberWithAmount,4,transType[0],"Verifying category", false);
        FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable,rowNumberWithAmount,6,category,"Verifying sub category", false);
        FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable,rowNumberWithAmount,7,subcategory,"Verifying sub category Type Option", false);
        
        //Double expectedValforPostBal = Double.valueOf(postTxnBal)+Double.valueOf(amount);
       
    
        
        if(Double.valueOf(postTxnBal).equals(Double.valueOf(balShowingUnderFinancialHistory)))
        {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Post Transaction Balance should show in financial history table: "+postTxnBal
                    +"<br>"+"Actual: Displaying: "+balShowingUnderFinancialHistory, LogStatus.PASS, true);
        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Post Transaction Balance should show in financial history table: "+postTxnBal
                    +"<br>"+"Actual: But displaying: "+balShowingUnderFinancialHistory, LogStatus.FAIL, true);
        }
        String autoGeneratingTransactionid = String.valueOf(Integer.valueOf(transactionId)+1);
        
        
        int rowNumberWithAmount1 = FunctionLibrary.getRowNumberFromWebTable(xpathOfAmountTable,autoGeneratingTransactionid, "get the row number");
        rowNumberWithAmount1=1;
        FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable, rowNumberWithAmount1, 2,autoGeneratingTransactionid, "<b>Finacial History Table:</b> <br>Verifying auto generated transaction id", true);
        FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable,rowNumberWithAmount1,8,payType,"Verifying Payment Type Option", false);
        
        balShowingUnderFinancialHistory=FunctionLibrary.getWebTableCellData(xpathOfAmountTable,rowNumberWithAmount1,11,"Get balance showing under finacial history");
        
        balShowingUnderFinancialHistory=balShowingUnderFinancialHistory.replace("(", "").replace(")", "").replace("$", "").replace(",", "");


        FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable,rowNumberWithAmount1,4,transType[1],"Verifying category", false);
        FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable,rowNumberWithAmount1,6,category,"Verifying sub category", false);
        FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable,rowNumberWithAmount1,7,subcategory,"Verifying sub category Type Option", false);
        
    
        Double balance = Double.valueOf(postTxnBal)-Double.valueOf(amount);
        balance = Math.round(balance*100.0)/100.0;
        if(balance.equals(Double.valueOf(balShowingUnderFinancialHistory)))
        {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Post Transaction Balance should show in financial history table: "+balance
                    +"<br>"+"Actual: Displaying: "+balShowingUnderFinancialHistory, LogStatus.PASS, true);
        }
        else
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Post Transaction Balance should show in financial history table: "+balance
                    +"<br>"+"Actual: But displaying: "+balShowingUnderFinancialHistory, LogStatus.FAIL, true);
        }        
      
                
    }

    public static void verifyReversalInfoUnderPaymentNotes(String reversalPayType,  String amountToBeReversed)
    {
        FunctionLibrary.objDriver.findElement(By.xpath(paymentNotesLink)).click();
        FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(paymentsGoBtn)));
        String notesTable = paymentsListTbl;
        
        FunctionLibrary.verifyWebTableCellData(notesTable, 1, 1, reversalPayType, "<b>Notes:</b><br>Verify reversal XR type", true);
        FunctionLibrary.verifyWebTableCellData(notesTable, 2, 1, reversalPayType, "Verify reversal R type", false);
        
        String reversalR_Amount = FunctionLibrary.getWebTableCellData(notesTable, 2,2,"Get reversal type R amount");
        String reversalXr_Amount = FunctionLibrary.getWebTableCellData(notesTable, 1,2,"Get reversal type XR amount");
        reversalR_Amount = reversalR_Amount.replace("$", "").replace(",", "").replace("(", "").replace(")", "");
        reversalXr_Amount = reversalXr_Amount.replace("$", "").replace(",", "").replace("(", "").replace(")", "");
        if(Double.valueOf(amountToBeReversed).equals(Double.valueOf(reversalXr_Amount)))
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Reversal type XR amount should be same as paid amount "+
                                amountToBeReversed+". <br> Actual: Amount is displaying."+reversalXr_Amount, LogStatus.PASS, true);
        }
        else
        {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Reversal type XR amount should be same as paid amount "+
                        amountToBeReversed+". <br> Actual: Amount is displaying."+reversalXr_Amount, LogStatus.FAIL, true);
        }

        if(Double.valueOf(amountToBeReversed).equals(Double.valueOf(reversalR_Amount)))
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Reversal type R amount should be same as paid amount "+
                                amountToBeReversed+". <br> Actual: Amount is displaying."+reversalR_Amount, LogStatus.PASS, true);
        }
        else
        {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Reversal type R amount should be same as paid amount "+
                        amountToBeReversed+". <br> Actual: Amount is displaying."+reversalR_Amount, LogStatus.FAIL, true);
        }   
    }
   
    public static void verifyTransferNotes(String payType,  String amount)
    {
        FunctionLibrary.objDriver.findElement(By.xpath(paymentNotesLink)).click();
        FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(paymentsGoBtn)));
        String notesTable = paymentsListTbl;
        
        FunctionLibrary.verifyWebTableCellData(notesTable, 1, 1, payType, "<b>Notes:</b><br>Verify Transfer pay type", true);
        
        
        String amoutShowing = FunctionLibrary.getWebTableCellData(notesTable, 1,2,"Get transfered amount");
       
        amoutShowing = amoutShowing.replace("$", "").replace(",", "").replace("(", "").replace(")", "");
        if(Double.valueOf(amount).equals(Double.valueOf(amoutShowing)))
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Adjust amount should be :"+
                                amount+". <br> Actual: Amount is displaying."+amoutShowing, LogStatus.PASS, false);
        }
        else
        {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Adjust amount should be :"+
                        amount+". <br> Actual: Amount is displaying."+amoutShowing, LogStatus.FAIL, true);
        }   
    }
   
    public static void verifyAdjustmentNotes(String payType,  String amount)
    {
        FunctionLibrary.objDriver.findElement(By.xpath(paymentNotesLink)).click();
        FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(paymentsGoBtn)));
        String notesTable = paymentsListTbl;
        
        FunctionLibrary.verifyWebTableCellData(notesTable, 1, 1, payType, "<b>Notes:</b><br>Verify Transfer pay type", true);
        
        
        String amoutShowing = FunctionLibrary.getWebTableCellData(notesTable, 1,2,"Get Adjustment amount");
       
        amoutShowing = amoutShowing.replace("$", "").replace(",", "").replace("(", "").replace(")", "");
        if(Double.valueOf(amount).equals(Double.valueOf(amoutShowing)))
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Adjustment amount should be :"+
                                amount+". <br> Actual: Amount is displaying."+amoutShowing, LogStatus.PASS, false);
        }
        else
        {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Adjustment amount should be :"+
                        amount+". <br> Actual: Amount is displaying."+amoutShowing, LogStatus.FAIL, true);
        }   
    }
      
    public static void verifyOneTimePaymentNotes(String payType,  String amount)
    {
        FunctionLibrary.objDriver.findElement(By.xpath(paymentNotesLink)).click();
        FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(paymentsGoBtn)));
        String notesTable = paymentsListTbl;
        
        FunctionLibrary.verifyWebTableCellData(notesTable, 1, 1, payType, "<b>Notes:</b><br>Verify Onetime pay type", true);
       
        if(!(payType.equalsIgnoreCase("CASH"))&& !(payType.equalsIgnoreCase("CHECK")))
        {
        FunctionLibrary.verifyWebTableCellData(notesTable, 1, 10, "APPROVED", "CC Response Status", true); 
        }
        String amoutShowing = FunctionLibrary.getWebTableCellData(notesTable, 1,2,"Get paid amount");
       
        amoutShowing = amoutShowing.replace("$", "").replace(",", "").replace("(", "").replace(")", "");
        if(Double.valueOf(amount).equals(Double.valueOf(amoutShowing)))
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: OneTime Payment amount should be :"+
                                amount+". <br> Actual: Amount is displaying."+amoutShowing, LogStatus.PASS, false);
        }
        else
        {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: OneTime Payment amount should be :"+
                        amount+". <br> Actual: Amount is displaying."+amoutShowing, LogStatus.FAIL, true);
        }   
    }
   
    public static void paymentReversal(String acctNumber, String isWithInThreshold) throws InterruptedException
   {
                
            String reversalPayType = dataObj.get("Payment Type");
            String [] minMaxThresholdValues  = getMinMaxThresholdValues(dataObj.get("User Responsibility"),reversalPayType,"REVERSAL");
            String minimumThresholdBal = minMaxThresholdValues[0];
            String maximumThresholdBal= minMaxThresholdValues[1];
           //reading less value for minimum threshold value
           String existingAmount=minimumThresholdBal;
           //existingAmount=existingAmount.replace("$","");
           existingAmount=existingAmount.replace(",","");
           
           Double amountToBeReversed;
           if(isWithInThreshold.equalsIgnoreCase("Yes"))
           {
                   amountToBeReversed=Double.valueOf(minimumThresholdBal)-1;   
           }
           else
           {
                   amountToBeReversed=Double.valueOf(maximumThresholdBal)+1;
           }
           if(isWithInThreshold.equalsIgnoreCase("Equal"))
           {
                   amountToBeReversed=Double.valueOf(minimumThresholdBal);
           }

           ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "existing threshold amount "+
                   ""+existingAmount, LogStatus.INFO, true);

           //search for account
           CommonLibrary.searchForAccount(acctNumber);
           
           //click the link Financial
           FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(linkFinancial)));
           FunctionLibrary.clickObject(linkFinancial, "", "click on financials");


           String accountBalBeforePayemnt=FunctionLibrary.getText(accountBalTxtBox, "Account Balance");

           ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Existing acccount balance: "+
                                   accountBalBeforePayemnt,LogStatus.INFO,false);


          FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(accountReplenishment,linkFinancial,15,34);
           FunctionLibrary.clickObject(accountReplenishment, "", "click on accountReplenishment");
           FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(newBtnAmount,accountReplenishment,15,34);
           
           FunctionLibrary.clickObject(newBtnAmount, "", "click on newBtnAmount");
           FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(amonutTxtBox,newBtnAmount,15,34);
         /*  try {
                                                Thread.sleep(3000);
                                } catch (InterruptedException e1) {
                                                // TODO Auto-generated catch block
                                                e1.printStackTrace();
                                }*/
           FunctionLibrary.waitForObject.until(ExpectedConditions.attributeToBe(By.xpath(balanceForwardTxtBox),
                                "aria-readonly", "false"));
           ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Amonut being added : "+
                   amountToBeReversed,LogStatus.INFO,false);
           FunctionLibrary.setText(amonutTxtBox, amountToBeReversed.toString(), "Enter Amount");
         
           FunctionLibrary.clickObject(replenishmentSave, "", "click on replenishmentSave");

           FunctionLibrary.clickObject(newPaymentList, "", "click on newPaymentList");
           
           FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(saveRebillInfo,
                                newPaymentList, 3, 20);
           
           FunctionLibrary.scrollDowntoElement(saveRebillInfo, "Scroll down");
           FunctionLibrary.selectDropDownListByValue(replenishmentDetailsRebillPayTypeDropdown,reversalPayType,"select cash from dropdown");
           if(reversalPayType.equalsIgnoreCase("SAVINGS") || reversalPayType.equalsIgnoreCase("CHECKING"))
           {
               //Enter Bank Account no:
               FunctionLibrary.setText(paymentDetailsBankAccountNbrTxtBox,dataObj.get("BankAccount"),"Enter bank number");
               // Enter Routing No:
               FunctionLibrary.setText(paymentDetailsRoutingNbrTxtBox,dataObj.get("BankRoutine"),"Enter Routing number");
           }
           
           if(reversalPayType.equalsIgnoreCase("CHECK"))
           {
               //Enter Bank Account no:
                   FunctionLibrary.setText(checkPay, dataObj.get("CheckNumber"),"Enter Check number");
               try {

                   WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,5);
                   wait2.until(ExpectedConditions.alertIsPresent());
                   Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                   alert.accept();
               }
               catch (Exception e) {
                   //exception handling
               }
           }
           
           FunctionLibrary.clickObject(saveRebillInfo, "", "click on saveRebillInfo");
           //handle the Payment Processing pop-up alert
           try {

               WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,8);
               wait2.until(ExpectedConditions.alertIsPresent());
               Alert alert = FunctionLibrary.objDriver.switchTo().alert();
               alert.accept();
           }
           catch (Exception e) {
               //exception handling
           }
           FunctionLibrary.clickObject(savePaymentList, "", "click on savePaymentList");
           FunctionLibrary.clickObject(processPay, "", "click on processPay");
           Thread.sleep(2000);
           if(reversalPayType.equalsIgnoreCase("CHECK"))
           {
                   try {

                   WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,5);
                   wait2.until(ExpectedConditions.alertIsPresent());
                   Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                   String alertText = alert.getText();
                   System.out.println(alertText);
                   alert.accept();

               }
               catch (Exception e) {
                   //exception handling
               }

           
           }
           String processNum = null;
           try {

               WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,40);
               wait2.until(ExpectedConditions.alertIsPresent());
               Alert alert = FunctionLibrary.objDriver.switchTo().alert();
               String alertText = alert.getText();
               System.out.println(alertText);
               processNum = alertText.split("Payment Confirmation Number : ")[1];
               processNum=processNum.split(" ")[0].trim();
               System.out.println(processNum);
               alert.accept();

           }
           catch (Exception e) {
               //exception handling
           }

           FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@title='Financial Transaction List:Reverse']")));

           FunctionLibrary.clickObject(reverseBtn, "", "clicking on reverse");
           try {

               WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,8);
               wait2.until(ExpectedConditions.alertIsPresent());
               Alert alert = FunctionLibrary.objDriver.switchTo().alert();
               alert.accept();

           }
           catch (Exception e) {
               //exception handling
           }
           Thread.sleep(5000);

           if(isWithInThreshold.equalsIgnoreCase("YES") || isWithInThreshold.equalsIgnoreCase("Equal") )
           {   
                   
                   String accountBalAfterPayment=FunctionLibrary.getText(accountBalTxtBox, "Account Balance");
                   accountBalAfterPayment=accountBalAfterPayment.replace("$", "");
                   accountBalAfterPayment=accountBalAfterPayment.replace(",", "");
    
                           Double postTransBalForTypeR = amountToBeReversed +Double.valueOf(accountBalAfterPayment);
                           String [] tranTypes = dataObj.get("TransType").split(";");
                           boolean isXrTranTypeItemCreated =  verifyReversalTransactionInfoUnderReversalTab(processNum,reversalPayType,
                                                   tranTypes,amountToBeReversed.toString());
                           
                           if(isXrTranTypeItemCreated)
                           {
                           verifyReversalInfoUnderPaymentNotes(reversalPayType,amountToBeReversed.toString());          
                                      
                           FunctionLibrary.clickObject(historyTab, "", "Click history link");
                           FunctionLibrary.clickObject(financialsUnderHistroy,"","Click Financial under history");
                           FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(accountFinHistoryListViewBtn)));
                           verifyReversalInfoUnderHistoryTab(processNum,reversalPayType, tranTypes,"TOLDEPOSIT","PPTL", 
                                                amountToBeReversed.toString(),String.valueOf(postTransBalForTypeR));
                           accountBalBeforePayemnt=accountBalBeforePayemnt.replace(",", "").replace("$", "");
                           if(Double.valueOf(accountBalBeforePayemnt).equals(Double.valueOf(accountBalAfterPayment)))
                           {
                               ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Account Balance after Reversing Amount :"+accountBalBeforePayemnt+
                                       "<br>"+"The Account Balance is :"+accountBalAfterPayment, LogStatus.PASS, true);

                           }
                           else
                           {
                               ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Account Balance after Reversing Amount :"+ accountBalBeforePayemnt +
                                       "<br>"+"Actual:Account Balance is :"+accountBalAfterPayment, LogStatus.FAIL, true);

                           }


                           }
           }
           else
           {

               
               int rowNumber1 = FunctionLibrary.getRowNumberFromWebTable(xpathOfFinancialTable, "ESCALATION", "Get the row number");
              
               FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable, rowNumber1, 6, "Open",
                       "Verifying status", true);
               FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable,rowNumber1,4,
                       "ESCALATION","Verifying QUEUE Type ", false);
               FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable,rowNumber1,5,"REVERSAL","Verifying Sub Queue of ESCALATION", false);
               FunctionLibrary.verifyTextBoxValue("//input[@aria-label='Pay Type']", reversalPayType, "Verify Paytype", false);
               
            /*   String transactionIdShowing = FunctionLibrary.getText(compositeTransactionIdTxtBox, "Get the Transaction Id");
               transactionIdShowing = transactionIdShowing.replace(",", "");
               
               if(Double.valueOf(processNum).equals(Double.valueOf(transactionIdShowing)))
               {
                   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Process number: "+processNum+"<br> Actual: "+transactionIdShowing, 
                                                   LogStatus.PASS,false);
               }else
               {
                   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Process number: "+processNum+"<br> Actual: "+transactionIdShowing, 
                                                   LogStatus.FAIL,false);
               }
           */
              /* String amountShowing = FunctionLibrary.getText(amountTxtBox, "Get the Amount");
               amountShowing = amountShowing.replace(",", "").replace("$", "");
               if(Double.valueOf(amountToBeReversed).equals(Double.valueOf(amountShowing)))
               {
                   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Amount:: "+amountToBeReversed+"<br> Actual: "+amountShowing, 
                                                   LogStatus.PASS,false);
               }else
               {
                   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Amount:: "+amountToBeReversed+"<br> Actual: "+amountShowing, 
                                                   LogStatus.FAIL,false);
               }*/
           }

   }
   
    public static String [] getMinMaxThresholdValues(String userResponsibility, String payType, String transactionType) throws InterruptedException
   {
                   String [] minAndMaxThresholdValues = new String [2];
                   CommonLibrary.clickSiteMap();
                                
                                FunctionLibrary.scrollDowntoElement(internalControls, "Scroll down");
			      WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver,100);
			      wait.until(ExpectedConditions.elementToBeClickable(By.xpath(internalControls)));
			      FunctionLibrary.clickObject(internalControls, "", "Click internal controls link");
			    dataObj.get("Payment Type");
                FunctionLibrary.clickObject(internalControlThresholdQueryBtn, "", "Click Query button");
                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//input[@name='Agency_ID']",
                                                internalControlThresholdQueryBtn, 6, 30);
                FunctionLibrary.clickObject(responsibilityElement, "", "Click Responsiblity");
                
                FunctionLibrary.setText(responsibilityTxtBox, userResponsibility, "Enter Responsibility");
                /*FunctionLibrary.setText("//input[@name='Store']", "FLCCSS", "Enter Store");*/
                FunctionLibrary.clickObject(paytypeElement, "", "Click the paytype");
                FunctionLibrary.clickObject(replenishmentPlanTypeDropdown, "", "Click paytype drop down");
                FunctionLibrary.clickObject("//li[text()='"+payType+"']", "", "Click paytype item");
                FunctionLibrary.clickObject(transactionTypeElement, "", "Click the paytype");
                FunctionLibrary.clickObject(replenishmentPlanTypeDropdown, "", "Click transactoin type drop down");
                FunctionLibrary.clickObject("//li[text()='"+transactionType+"']", "", "Click transaction type drop down");
                FunctionLibrary.objDriver.findElement(By.xpath
                  (internalControlThresholdGoBtn)).click();
        Thread.sleep(3000);
        
        int rowNumber = FunctionLibrary.getRowNumberFromWebTable(intrernalControlThresholdTbl, userResponsibility, "Get the row number");
        String minimumThresholdBal = FunctionLibrary.getWebTableCellData(intrernalControlThresholdTbl,rowNumber,8,"get minimum Threshold value ");
        String maximumThresholdBal = FunctionLibrary.getWebTableCellData(intrernalControlThresholdTbl,rowNumber,9,"get maximum Threshold value ");
        minAndMaxThresholdValues[0]= minimumThresholdBal.replace(",","");
        minAndMaxThresholdValues[1]= maximumThresholdBal.replace(",","");
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Snapshot of internal contrl threshold page", LogStatus.INFO, true);
        return minAndMaxThresholdValues;
     
   }

    public static void makefinancialStatusAsInsuffient(String accNum) throws InterruptedException
    {
        String Desc = ""; 
        
        String isDebitOrCrditType = "DEBIT";
        String adjustmentCategoryType = "FEE";
        String adjustmentSubCategoryType ="REACTIVATIONFEE" ;
        String amount = "";
        String quantity = "1";
        String existingAmount=amount;
       existingAmount=existingAmount.replace(",","");
       
                CommonLibrary.searchForAccount(accNum); 
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Adjustment category type: "+ adjustmentCategoryType +", Sub category type: "+ 
                adjustmentSubCategoryType +", Quanity: "+quantity+", Amount: "+amount, LogStatus.INFO, false);
                
        FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(accNum)));
        FunctionLibrary.objDriver.findElement(By.linkText(accNum)).click();
        FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 60, ChallangeQA2Dropdown);

        //click on financials tab
        Desc = "Clicking Financials tab";
        FunctionLibrary.clickObject(financialsTab, "", Desc);
        //click on adjustments tab
        FunctionLibrary.clickObject(adjustmentsTab, "", Desc);
        String initalAccountBal;
        WebElement el= FunctionLibrary.objDriver.findElement(By.xpath(pptlBalance));
        initalAccountBal = el.getAttribute("value");
        System.out.println("Amount Before adjustment"+  initalAccountBal);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>"
                + "Amount Before adjustment "+  initalAccountBal, LogStatus.INFO, true);
        initalAccountBal=initalAccountBal.replace("(", "").replace(")", "").replace("$", "").replace(",", "");

        FunctionLibrary.scrollDown("New", "Scroll down");
        //click on new button @account payment tab
        Desc = "Clicking on new button";
        FunctionLibrary.clickObject(adjustmentsNewBtn, "", Desc);
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(categoryLabel, adjustmentsNewBtn, 6, 24);
        FunctionLibrary.waitForObject.until(ExpectedConditions.attributeToBe(By.xpath(categoryTxtBox), "aria-readonly", "false"));
        //enter Category_Label
        Desc = "Entering Category_Label";
        Desc = "Entering Sub category label";
        FunctionLibrary.setText(subCategoryLabel, adjustmentSubCategoryType, Desc);
        amount =String.valueOf(Double.valueOf(initalAccountBal)+6);
        //enter qunantity
        Desc = "Entering Unit_Price_Label";
        FunctionLibrary.setText(quantityLabel, quantity, Desc);

        //enter Unit_Price_Label
        Desc = "Entering Unit_Price_Label";
        FunctionLibrary.setText(unitPriceLabel, amount, Desc);

        Desc = "clicking on Adjust button";
        //FunctionLibrary.ObjDriver.findElement(By.xpath(adjustButton)).click();
        FunctionLibrary.clickObject(""+adjustButton,"","Click adjust button");
        
                  //wait for an alert
             FunctionLibrary.waitForObject.until(ExpectedConditions.alertIsPresent());
             Alert alert = FunctionLibrary.objDriver.switchTo().alert();
             System.out.println("Popup is displayed with text" + alert.getText());
             String alertText = alert.getText();
             alert.accept();
             String[] paymentRefNumber = alertText.split(":");
             String transactionId= paymentRefNumber[1].trim();
             System.out.println(transactionId);
             ReportLibrary.addStep(ReportLibrary.Test_Step_Number,  "Transaction processed. Payment Reference number " + transactionId, LogStatus.INFO, true);
           
                        String accountBalanceAfterUpdate;
                        el= FunctionLibrary.objDriver.findElement(By.xpath(pptlBalance));
                        accountBalanceAfterUpdate = el.getAttribute("value");
                
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Amount After adjustment "+  accountBalanceAfterUpdate, LogStatus.INFO, false);
                
                        Double amountForAdjustmentDone=Double.valueOf(quantity)*Double.valueOf(amount);
                        
                        //reading less value for minimum threshold value
                        String existingAccBal=accountBalanceAfterUpdate;
                        existingAccBal=existingAccBal.replace("$","");
                        existingAccBal=existingAccBal.replace(",","");
                        Double totalAmountAfterAdjustment;
                        if(isDebitOrCrditType=="DEBIT")
                        {
                                totalAmountAfterAdjustment = Double.valueOf(initalAccountBal) - Double.valueOf(amountForAdjustmentDone);
                        }
                        else
                        {
                                totalAmountAfterAdjustment = Double.valueOf(initalAccountBal) + Double.valueOf(amountForAdjustmentDone);
                        }
                         
                        int rowNum = FunctionLibrary.getRowNumberFromWebTable(financialTransactionListTable,transactionId,"get the row number");
                        FunctionLibrary.verifyWebTableCellData(financialTransactionListTable,rowNum,2,"NONE","<b>Financial Transactions:</b><br>Verifying Pay type",true);
                        
                        String amountShowingAsAdjustmentDone=FunctionLibrary.getWebTableCellData(financialTransactionListTable,rowNum,4,"Get amount done for ajustment");
                        amountShowingAsAdjustmentDone=amountShowingAsAdjustmentDone.replace("(", "");
                        amountShowingAsAdjustmentDone=amountShowingAsAdjustmentDone.replace(")", "");
                        amountShowingAsAdjustmentDone=amountShowingAsAdjustmentDone.replace("$", "");
                        amountShowingAsAdjustmentDone=amountShowingAsAdjustmentDone.replace(",", "");
                        
                        if(Double.valueOf(amountForAdjustmentDone).equals(Double.valueOf(amountShowingAsAdjustmentDone)))
                        {
                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Financial Transaction table should show amount as :"+amountForAdjustmentDone
                                    +"<br>"+"Actual: Displaying: "+amountShowingAsAdjustmentDone, LogStatus.PASS, true);
                        }
                        else
                        {
                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Financial Transaction table should show amount as :"+amountForAdjustmentDone
                                    +"<br>"+"Actual: Displaying: "+amountShowingAsAdjustmentDone, LogStatus.FAIL, true);
                        }
                        
                        verifyAdjustmentNotes(dataObj.get("Payment Type"), String.valueOf(amountForAdjustmentDone));
                     
                        //Verify in financial history table
                        FunctionLibrary.clickObject(historyTab, "", "Click history link");
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Account Balance after Adding Amount :"+"<br>"+"The Account Balance is :"+
                                                                                                                totalAmountAfterAdjustment, LogStatus.INFO, true);
                        FunctionLibrary.clickObject(financialsUnderHistroy,"","Click Financial under history");
                        FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOf(FunctionLibrary.getElement(financialHistroyTable)));
                        String xpathOfAmountTable = financialHistroyTable;
                
                        int rowNumberWithAmount = FunctionLibrary.getRowNumberFromWebTable(xpathOfAmountTable,transactionId, "get the row number");
                        
                        FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable, rowNumberWithAmount, 2,transactionId, "<b>Financial History</b><br>Verifying transaction id", true);
                        FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable, rowNumberWithAmount, 4,dataObj.get("TransType"), "Verifying transaction id", false);
                        FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable, rowNumberWithAmount, 6,adjustmentCategoryType, "Verifying adjustment category type", false);
                        FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable, rowNumberWithAmount, 7,adjustmentSubCategoryType, "Verifying adjustment sub category type", false);
                        FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable,rowNumberWithAmount,8,"NONE","Verifying Payment Type Option", false);
                        
                   

    }
    
    public static void makefinancialStatusAsLow(String accNum) throws InterruptedException
    {
        String Desc = ""; 
        String adjustmentCategoryType = "FEE";
        String adjustmentSubCategoryType ="REACTIVATIONFEE" ;
        String amount = "";
        String quantity = "1";
        String payType = "CASH";
        dataObj.put("Payment Type",payType);
        
       //reading less value for minimum threshold value
       String existingAmount=amount;
       existingAmount=existingAmount.replace(",","");
                CommonLibrary.searchForAccount(accNum); 
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Adjustment category type: "+ adjustmentCategoryType +", Sub category type: "+ 
                adjustmentSubCategoryType +", Quanity: "+quantity+", Amount: "+amount, LogStatus.INFO, false);
                
                
                String finStatus = FunctionLibrary.getText(accountFinancialStatusTxtBox, "Get the text on financial status");
                
                if(!finStatus.equalsIgnoreCase("LOW"))
                {
                                
                        FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(accNum)));
                        FunctionLibrary.objDriver.findElement(By.linkText(accNum)).click();
                        FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 60, ChallangeQA2Dropdown);
                
                        //click on financials tab
                        Desc = "Clicking Financials tab";
                        FunctionLibrary.clickObject(financialsTab, "", Desc);
                        //click on adjustments tab
                        FunctionLibrary.clickObject(adjustmentsTab, "", Desc);
                        String initalAccountBal;
                        WebElement el= FunctionLibrary.objDriver.findElement(By.xpath(pptlBalance));
                        initalAccountBal = el.getAttribute("value");
                        System.out.println("Amount Before adjustment"+  initalAccountBal);
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>"
                                + "Amount Before adjustment "+  initalAccountBal, LogStatus.INFO, true);
                        initalAccountBal=initalAccountBal.replace("(", "").replace(")", "").replace("$", "").replace(",", "");
                
                        FunctionLibrary.scrollDown("New", "Scroll down");
                        //click on new button @account payment tab
                        Desc = "Clicking on new button";
                        FunctionLibrary.clickObject(adjustmentsNewBtn, "", Desc);
                        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(categoryLabel, adjustmentsNewBtn, 6, 24);
                        FunctionLibrary.waitForObject.until(ExpectedConditions.attributeToBe(By.xpath(categoryTxtBox), "aria-readonly", "false"));
                        //enter Category_Label
                        Desc = "Entering Category_Label";
                        Desc = "Entering Sub category label";
                        FunctionLibrary.setText(subCategoryLabel, adjustmentSubCategoryType, Desc);
                        
                        //enter qunantity
                        Desc = "Entering Unit_Price_Label";
                        FunctionLibrary.setText(quantityLabel, "1", Desc);
                        //enter Unit_Price_Label
                        amount =String.valueOf(Double.valueOf(initalAccountBal)-1);
                        Desc = "Entering Unit_Price_Label";
                        FunctionLibrary.setText(unitPriceLabel, amount, Desc);
                        
                        
                        Desc = "clicking on Adjust button";
                        //FunctionLibrary.ObjDriver.findElement(By.xpath(adjustButton)).click();
                        FunctionLibrary.clickObject(""+adjustButton,"","Click adjust button");
                        
                                  //wait for an alert
                             FunctionLibrary.waitForObject.until(ExpectedConditions.alertIsPresent());
                             Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                             System.out.println("Popup is displayed with text" + alert.getText());
                             String alertText = alert.getText();
                             alert.accept();
                             //get payment reference number
                             String[] paymentRefNumber = alertText.split(":");
                             String transactionId= paymentRefNumber[1].trim();
                             System.out.println(transactionId);
                             ReportLibrary.addStep(ReportLibrary.Test_Step_Number,  "Transaction processed. Payment Reference number " + transactionId, LogStatus.INFO, true);
                           
                             finStatus = FunctionLibrary.getText(accountFinancialStatusTxtBox, "Get the text on financial status");
                                
                                if(finStatus.equalsIgnoreCase("LOW"))
                                {
                                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Financial status of account is LOW", LogStatus.PASS, true);
                                
                                }else
                                {
                                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Financial status of account is not LOW", LogStatus.FAIL, true);
                                }
                }
                else
                {
                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Financial status of account is LOW", LogStatus.INFO, true);
                }

    }
   
    public static void makefinancialStatusAsZero(String accNum) throws InterruptedException
    {
        String Desc = ""; 
        
        String adjustmentCategoryType = "FEE";
        String adjustmentSubCategoryType ="REACTIVATIONFEE" ;
        String amount = "";
        String quantity = "1";
      // String amount;
        String payType = "CASH";
        dataObj.put("Payment Type",payType);
        
       //reading less value for minimum threshold value
       String existingAmount=amount;
       //existingAmount=existingAmount.replace("$","");
       existingAmount=existingAmount.replace(",","");
       
                CommonLibrary.searchForAccount(accNum); 
                String finStatus = FunctionLibrary.getText(accountFinancialStatusTxtBox, "Get the text on financial status");
                
                if(!finStatus.equalsIgnoreCase("ZERO"))
                {
                                
                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Adjustment category type: "+ adjustmentCategoryType +", Sub category type: "+ 
                                adjustmentSubCategoryType +", Quanity: "+quantity+", Amount: "+amount, LogStatus.INFO, false);
                                
                        FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(accNum)));
                        FunctionLibrary.objDriver.findElement(By.linkText(accNum)).click();
                        FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 60, ChallangeQA2Dropdown);
                
                        //click on financials tab
                        Desc = "Clicking Financials tab";
                        FunctionLibrary.clickObject(financialsTab, "", Desc);
                        //click on adjustments tab
                        FunctionLibrary.clickObject(adjustmentsTab, "", Desc);
                        String initalAccountBal;
                        WebElement el= FunctionLibrary.objDriver.findElement(By.xpath(pptlBalance));
                        initalAccountBal = el.getAttribute("value");
                        System.out.println("Amount Before adjustment"+  initalAccountBal);
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>"
                                + "Amount Before adjustment "+  initalAccountBal, LogStatus.INFO, true);
                        initalAccountBal=initalAccountBal.replace("(", "").replace(")", "").replace("$", "").replace(",", "");
                
                        FunctionLibrary.scrollDown("New", "Scroll down");
                        //click on new button @account payment tab
                        Desc = "Clicking on new button";
                        FunctionLibrary.clickObject(adjustmentsNewBtn, "", Desc);
                        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(categoryLabel, adjustmentsNewBtn, 6, 24);
                        FunctionLibrary.waitForObject.until(ExpectedConditions.attributeToBe(By.xpath(categoryTxtBox), "aria-readonly", "false"));
                        //enter Category_Label
                        Desc = "Entering Category_Label";
                        Desc = "Entering Sub category label";
                        FunctionLibrary.setText(subCategoryLabel, adjustmentSubCategoryType, Desc);
                        
                        //enter qunantity
                        Desc = "Entering Unit_Price_Label";
                        FunctionLibrary.setText(quantityLabel, "1", Desc);
                        //enter Unit_Price_Label
                        amount =String.valueOf(Double.valueOf(initalAccountBal));
                        Desc = "Entering Unit_Price_Label";
                        FunctionLibrary.setText(unitPriceLabel, amount, Desc);
                        Desc = "clicking on Adjust button";
                        //FunctionLibrary.ObjDriver.findElement(By.xpath(adjustButton)).click();
                        FunctionLibrary.clickObject(""+adjustButton,"","Click adjust button");
                        
                                  //wait for an alert
                             FunctionLibrary.waitForObject.until(ExpectedConditions.alertIsPresent());
                             Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                             System.out.println("Popup is displayed with text" + alert.getText());
                             String alertText = alert.getText();
                             alert.accept();
                             //get payment reference number
                             String[] paymentRefNumber = alertText.split(":");
                             String transactionId= paymentRefNumber[1].trim();
                             System.out.println(transactionId);
                             ReportLibrary.addStep(ReportLibrary.Test_Step_Number,  "Transaction processed. Payment Reference number " + transactionId, LogStatus.INFO, true);
                           
                             finStatus = FunctionLibrary.getText(accountFinancialStatusTxtBox, "Get the text on financial status");
                                                
                                                if(finStatus.equalsIgnoreCase("ZERO"))
                                                {
                                                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Financial status of account is ZERO", LogStatus.PASS, true);
                                                
                                                }else
                                                {
                                                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Financial status of account is not ZERO", LogStatus.FAIL, true);
                                                }
                }
                else
                {
                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Financial status of account is ZERO", LogStatus.INFO, true);
                }

    }

    public static void paymentRefund(String acctNumber, String isWithInThreshold) throws InterruptedException
    {
                
            String refundPayType = dataObj.get("Payment Type");
           String tranType=dataObj.get("TransType");
           String [] minMaxThresholdValues  = getMinMaxThresholdValues(dataObj.get("User Responsibility"),refundPayType,"REFUND");
           String minimumThresholdBal = minMaxThresholdValues[0];
           //reading less value for minimum threshold value
            String existingAmount=minimumThresholdBal;
            //existingAmount=existingAmount.replace("$","");
            existingAmount=existingAmount.replace(",","");
            
            String refundAmount=dataObj.get("RefundAmount");         

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "existing Minimum threshold amount "+
                    ""+existingAmount, LogStatus.INFO, true);

            //search for account
            CommonLibrary.searchForAccount(acctNumber);
            
            //click the link Financial
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='#s_sctrl_tabView_noop' and text()='Financials']")));
            FunctionLibrary.clickObject(linkFinancial, "", "click on financials");
            String accountBalBeforePayemnt=FunctionLibrary.getText(accountBalTxtBox, "Account Balance");

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Existing acccount balance: "+
                                   accountBalBeforePayemnt,LogStatus.INFO,false);

            String categoryValue="TOLDIPOSIT";
           // String refundPaymentAmount=dataObj.get("RefundAmount");
           // FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='ui-id-268']")));
            FunctionLibrary.clickObject(refundButton, "", "click on Refund");
            Thread.sleep(3000);
            FunctionLibrary.clickObject(newButtonRefund, "", "click on New");
            try {
                                                Thread.sleep(3000);
                                } catch (InterruptedException e1) {
                                                // TODO Auto-generated catch block
                                                e1.printStackTrace();
                                }
             String Desc="enter the value for Category Label";
             WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver,100);
             wait.until(ExpectedConditions.elementToBeClickable(By.xpath(refundCategoryLabel)));
            
           // FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(refundCategoryLabel)));
            FunctionLibrary.setText(refundCategoryLabel, categoryValue, Desc);            
            FunctionLibrary.clickObject(popUpWindowOkCategoryLabelRefund, "", "click on OK");
            try {
                                                Thread.sleep(3000);
                                } catch (InterruptedException e1) {
                                                // TODO Auto-generated catch block
                                                e1.printStackTrace();
                                }
           /* WebDriverWait wait1 = new WebDriverWait(FunctionLibrary.objDriver,100);
            wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-labelledby='Payment_Category_Label']"))); */
            FunctionLibrary.waitForObject.until(ExpectedConditions.attributeToBe(By.xpath("//input[@aria-labelledby='Payment_Category_Label']"), "aria-readonly", "false"));
            FunctionLibrary.setText(paymentRefundLabel, categoryValue, "enter Payment Value");
            FunctionLibrary.clickObject(popUpWindowPaymentTransactionRefund, "", "click on OK");
            String AmountToRefunded=dataObj.get("RefundAmount");
            try {
                                                Thread.sleep(3000);
                                } catch (InterruptedException e1) {
                                                // TODO Auto-generated catch block
                                                e1.printStackTrace();
                                }
           // WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,100);
           // wait2.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-labelledby='Amount_Label']")));
            FunctionLibrary.setText(amountLabel, AmountToRefunded, "enter amount for Refund");
            String refundCode="OTHER";
            FunctionLibrary.setText(refundCodeLabel, refundCode, "enter refund code");
          //  FunctionLibrary.setText("//input[@aria-label='Pay Type']", "CHECK", Desc);
            try {
                                                Thread.sleep(2000);
                                } catch (InterruptedException e1) {
                                                // TODO Auto-generated catch block
                                                e1.printStackTrace();
                                }
            FunctionLibrary.clickObject(refundSaveBtn, "", "click on save");
            try {
                                                Thread.sleep(3000);
                                } catch (InterruptedException e1) {
                                                // TODO Auto-generated catch block
                                                e1.printStackTrace();
                                }
            
            
            FunctionLibrary.clickObject(refundBtn, "", "click on Refund");      
          
            String processNum = null;
            try {

                WebDriverWait wait3 = new WebDriverWait(FunctionLibrary.objDriver,40);
                wait3.until(ExpectedConditions.alertIsPresent());
                Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                String alertText = alert.getText();
                System.out.println(alertText);
                processNum = alertText.split("Payment Confirmation Number : ")[1];
                processNum=processNum.split(" ")[0].trim();
               System.out.println(processNum);
                alert.accept();
                //Thread.sleep(3000);

            }
            catch (Exception e) {
                //exception handling
            }

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@title='Financial Transaction List:Reverse']")));
            if(isWithInThreshold.equalsIgnoreCase("YES") || isWithInThreshold.equalsIgnoreCase("Equal") )
            {                     
                   String accountBalAfterRefund=FunctionLibrary.getText(accountBalTxtBox, "Account Balance");
                   accountBalAfterRefund=accountBalAfterRefund.replace("$", "");
                   accountBalAfterRefund=accountBalAfterRefund.replace(",", "");  
                   
                  String xpathOfFinancialTable = financialTransactionTable;
              int rowNumber1 = FunctionLibrary.getRowNumberFromWebTable(xpathOfFinancialTable, "D", "Get the row number");
              System.out.println(rowNumber1);
              
             // FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable, rowNumber1, 2, "VISA",
                //      "Verifying payType", true);
              FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable,rowNumber1,3,
                      "D","Verifying Transaction Type ", false);      
                           
              verifyRefundInfoUnderPaymentNotes(refundPayType);          
                                      
                           FunctionLibrary.clickObject(historyTab, "", "Click history link");
                          FunctionLibrary.clickObject(financialsUnderHistroy,"","Click Financial under history");
                          //FunctionLibrary.clickObject(financialHistroyTable,"","Click Financial history table");
                          FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Account Financials History List:View CC']")));
                         verifyRefundInfoUnderHistoryTab(processNum,refundPayType, tranType,"TOLDEPOSIT","REFUND", 
                                                  refundAmount);
                          accountBalBeforePayemnt=accountBalBeforePayemnt.replace(",", "").replace("$", "");
                          if(Double.valueOf(accountBalBeforePayemnt).equals(Double.valueOf(accountBalAfterRefund)))
                          {
                              ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Account Balance after Refund Amount :"+accountBalBeforePayemnt+
                                      "<br>"+"The Account Balance is :"+accountBalAfterRefund, LogStatus.FAIL, true);

                          }
                          else
                          {
                              ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Account Balance after Refund Amount :"+ accountBalAfterRefund +
                                      "<br>"+"Actual:Account Balance is :"+accountBalAfterRefund, LogStatus.PASS, true);

                          }


                          }
            
            
                String accountBalAfterRefund=FunctionLibrary.getText(accountBalTxtBox, "Account Balance");
                String amountShowing = FunctionLibrary.getText("//input[@aria-label='PPTL Bal']", "Get the Amount");
                amountShowing = amountShowing.replace(",", "").replace("$", "");
                accountBalAfterRefund = accountBalAfterRefund.replace(",", "").replace("$", "");
                if(Double.valueOf(accountBalAfterRefund).equals(Double.valueOf(amountShowing)))
                {
                   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Amount:: "+accountBalAfterRefund+"<br> Actual: "+amountShowing, 
                                                   LogStatus.PASS,false);
                }else
                {
                   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Amount:: "+accountBalAfterRefund+"<br> Actual: "+amountShowing, 
                                                   LogStatus.FAIL,false);
                }
}
    public static void paymentRefundWithoutThreshold(String acctNumber) throws InterruptedException
    {
                
                                                String refundPayType = dataObj.get("Refund Payment Type");
                                               String tranType=dataObj.get("TransType");            
            String refundAmount=dataObj.get("RefundAmount");         

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Refund Pay Type value is : "+
                    ""+refundPayType, LogStatus.INFO, true);

            //search for account
            CommonLibrary.searchForAccount(acctNumber);
            
            //click the link Financial
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='#s_sctrl_tabView_noop' and text()='Financials']")));
            FunctionLibrary.clickObject(linkFinancial, "", "click on financials");
            String accountBalBeforePayemnt=FunctionLibrary.getText(accountBalTxtBox, "Account Balance");

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Existing acccount balance: "+
                                   accountBalBeforePayemnt,LogStatus.INFO,false);

            String categoryValue="TOLDIPOSIT";
           // String refundPaymentAmount=dataObj.get("RefundAmount");
           // FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='ui-id-268']")));
            FunctionLibrary.clickObject(refundButton, "", "click on Refund");
            Thread.sleep(3000);
            FunctionLibrary.clickObject(newButtonRefund, "", "click on New");
            try {
                                                Thread.sleep(3000);
                                } catch (InterruptedException e1) {
                                                // TODO Auto-generated catch block
                                                e1.printStackTrace();
                                }
             String Desc="enter the value for Category Label";
             WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver,100);
             wait.until(ExpectedConditions.elementToBeClickable(By.xpath(refundCategoryLabel)));
            
           // FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(refundCategoryLabel)));
            FunctionLibrary.setText(refundCategoryLabel, categoryValue, Desc);            
            FunctionLibrary.clickObject(popUpWindowOkCategoryLabelRefund, "", "click on OK");
            try {
                                                Thread.sleep(3000);
                                } catch (InterruptedException e1) {
                                                // TODO Auto-generated catch block
                                                e1.printStackTrace();
                                }
           /* WebDriverWait wait1 = new WebDriverWait(FunctionLibrary.objDriver,100);
            wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-labelledby='Payment_Category_Label']"))); */
            FunctionLibrary.waitForObject.until(ExpectedConditions.attributeToBe(By.xpath("//input[@aria-labelledby='Payment_Category_Label']"), "aria-readonly", "false"));
            FunctionLibrary.setText(paymentRefundLabel, categoryValue, "enter Payment Value");
            FunctionLibrary.clickObject(popUpWindowPaymentTransactionRefund, "", "click on OK");
            String AmountToRefunded=dataObj.get("RefundAmount");
            try {
                                                Thread.sleep(3000);
                                } catch (InterruptedException e1) {
                                                // TODO Auto-generated catch block
                                                e1.printStackTrace();
                                }
           // WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,100);
           // wait2.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-labelledby='Amount_Label']")));
            FunctionLibrary.setText(amountLabel, AmountToRefunded, "enter amount for Refund");
            String refundCode="OTHER";
            FunctionLibrary.setText(refundCodeLabel, refundCode, "enter refund code");
            try {
                                                Thread.sleep(2000);
                                } catch (InterruptedException e1) {
                                                // TODO Auto-generated catch block
                                                e1.printStackTrace();
                                }
            FunctionLibrary.clickObject(refundSaveBtn, "", "click on save");
            try {
                                                Thread.sleep(3000);
                                } catch (InterruptedException e1) {
                                                // TODO Auto-generated catch block
                                                e1.printStackTrace();
                                }
            FunctionLibrary.clickObject(refundBtn, "", "click on Refund");      
          
            String processNum = null;
            try {

                WebDriverWait wait3 = new WebDriverWait(FunctionLibrary.objDriver,40);
                wait3.until(ExpectedConditions.alertIsPresent());
                Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                String alertText = alert.getText();
                System.out.println(alertText);
                processNum = alertText.split("Payment Confirmation Number : ")[1];
                processNum=processNum.split(" ")[0].trim();
                System.out.println(processNum);
                alert.accept();
                //Thread.sleep(3000);

            }
            catch (Exception e) {
                //exception handling
            }

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@title='Financial Transaction List:Reverse']")));
                                   
                   String accountBalAfterRefund=FunctionLibrary.getText(accountBalTxtBox, "Account Balance");
                   accountBalAfterRefund=accountBalAfterRefund.replace("$", "");
                   accountBalAfterRefund=accountBalAfterRefund.replace(",", "");  
                   
                  String xpathOfFinancialTable = financialTransactionTable;
              int rowNumber1 = FunctionLibrary.getRowNumberFromWebTable(xpathOfFinancialTable, "D", "Get the row number");
              System.out.println(rowNumber1);
              
              //FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable, rowNumber1, 2, "VISA",
                   //   "Verifying payType", true);
              FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable,rowNumber1,3,
                      "D","Verifying Transaction Type ", false);      
                           
              verifyRefundInfoUnderPaymentNotes(refundPayType);          
                                      
                           FunctionLibrary.clickObject(historyTab, "", "Click history link");
                          FunctionLibrary.clickObject(financialsUnderHistroy,"","Click Financial under history");
                          //FunctionLibrary.clickObject(financialHistroyTable,"","Click Financial history table");
                          FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Account Financials History List:View CC']")));
                         verifyRefundInfoUnderHistoryTab(processNum,refundPayType, tranType,"TOLDEPOSIT","REFUND", 
                                                  refundAmount);
                          accountBalBeforePayemnt=accountBalBeforePayemnt.replace(",", "").replace("$", "");
                          if(Double.valueOf(accountBalBeforePayemnt).equals(Double.valueOf(accountBalAfterRefund)))
                          {
                              ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Account Balance after Refund Amount :"+accountBalBeforePayemnt+
                                      "<br>"+"The Account Balance is :"+accountBalAfterRefund, LogStatus.FAIL, true);

                          }
                          else
                          {
                              ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Account Balance after Refund Amount :"+ accountBalAfterRefund +
                                      "<br>"+"Actual:Account Balance is :"+accountBalAfterRefund, LogStatus.PASS, true);

                          }


                           
            
                String accountBalAfterRefund1=FunctionLibrary.getText(accountBalTxtBox, "Account Balance");
                String amountShowing = FunctionLibrary.getText("//input[@aria-label='PPTL Bal']", "Get the Amount");
                amountShowing = amountShowing.replace(",", "").replace("$", "");
                accountBalAfterRefund = accountBalAfterRefund.replace(",", "").replace("$", "");
                if(Double.valueOf(accountBalAfterRefund1).equals(Double.valueOf(amountShowing)))
                {
                   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Amount:: "+accountBalAfterRefund+"<br> Actual: "+amountShowing, 
                                                   LogStatus.PASS,false);
                }else
                {
                   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Amount:: "+accountBalAfterRefund+"<br> Actual: "+amountShowing, 
                                                   LogStatus.FAIL,false);
                }
    }

    public static void verifyRefundInfoUnderHistoryTab(String transactionId, String payType, String transType, String category, 
                                String subcategory, String amount)
    {
    
       // String xpathOfAmountTable = "//table[@summary='Account Financials History List']";

        int rowNumber = FunctionLibrary.getRowNumberFromWebTable(xpathOfAmountTable,transactionId, "get the row number");
       // rowNumber=2;
        FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable, rowNumber, 2,transactionId, "<b>Finacial History Table:</b> <br>Verifying transaction id", true);
       // FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable,rowNumber,8,payType,"Verifying Payment Type Option", false);
        
        String balShowingUnderFinancialHistory=FunctionLibrary.getWebTableCellData(xpathOfAmountTable,rowNumber,11,"Get balance showing under finacial history");
        
        balShowingUnderFinancialHistory=balShowingUnderFinancialHistory.replace("(", "").replace(")", "").replace("$", "").replace(",", "");


        FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable,rowNumber,4,transType,"Verifying category", false);
        FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable,rowNumber,6,category,"Verifying sub category", false);
        FunctionLibrary.verifyWebTableCellData(xpathOfAmountTable,rowNumber,7,subcategory,"Verifying sub category Type Option", false);
        String amountAfterRefund=FunctionLibrary.getText("//input[@aria-label='Acct Bal']", "Get the Amount");
        amountAfterRefund=amountAfterRefund.replace("(", "").replace(")", "").replace("$", "").replace(",", "");
        
        if(amountAfterRefund.equals(balShowingUnderFinancialHistory))
        {
                
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Post Transaction Balance should show in financial history table: "+amountAfterRefund
                    +"<br>"+"Actual: Displaying: "+balShowingUnderFinancialHistory, LogStatus.PASS, true); 
        }
        else
        {
        
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Post Transaction Balance should show in financial history table: "+amountAfterRefund
                    +"<br>"+"Actual: But displaying: "+balShowingUnderFinancialHistory, LogStatus.FAIL, true);
        }
        
                
    }
    public static void verifyRefundInfoUnderPaymentNotes(String refundPayType)
    {
        FunctionLibrary.objDriver.findElement(By.xpath("//a[text()='Payment Notes']")).click();
        FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Payments List:Go']")));
        String notesTable = paymentsListTbl;
        
        String paymentType=dataObj.get("Payment Type");
        FunctionLibrary.verifyWebTableCellData(notesTable, 1, 1, paymentType, "<b>Notes:</b><br>Verify payment type", true);
        //FunctionLibrary.verifyWebTableCellData(notesTable, 2, 1, refundPayType, "Verify refund type", false);
        
        String refund_Amount = FunctionLibrary.getWebTableCellData(notesTable, 2,2,"Get refund type R amount");
        String refund1_Amount = FunctionLibrary.getWebTableCellData(notesTable, 1,2,"Get refund type XR amount");
        refund_Amount = refund_Amount.replace("$", "").replace(",", "").replace("(", "").replace(")", "");
        refund1_Amount = refund1_Amount.replace("$", "").replace(",", "").replace("(", "").replace(")", "");
        
    }
   
    public static void makefinancialStatusAsLowAccountEstablish(String expectedStatus) throws InterruptedException
    {
        String Desc = ""; 
        String adjustmentSubCategoryType ="REACTIVATIONFEE" ;
      /* // String amount = "";
        String quantity = "1";
        String payType = "CASH";
     //   dataObj.put("Payment Type",payType);
        
       //reading less value for minimum threshold value
       String existingAmount=amount;
       existingAmount=existingAmount.replace(",","");
                
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Adjustment category type: "+ adjustmentCategoryType +", Sub category type: "+ 
                adjustmentSubCategoryType +", Quanity: "+quantity+", Amount: "+amount, LogStatus.INFO, false);*/
                
                
                String finStatus = FunctionLibrary.getText(accountFinancialStatusTxtBox, "Get the text on financial status");
                
                if(!finStatus.equalsIgnoreCase(expectedStatus))
                {
                
                        //click on financials tab
                        Desc = "Clicking Financials tab";
                        FunctionLibrary.clickObject(financialsTab, "", Desc);
                        //click on adjustments tab
                        FunctionLibrary.clickObject(adjustmentsTab, "", Desc);
                        String initalAccountBal;
                        WebElement el= FunctionLibrary.objDriver.findElement(By.xpath(pptlBalance));
                        initalAccountBal = el.getAttribute("value");
                        System.out.println("Amount Before adjustment"+  initalAccountBal);
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>"
                                + "Amount Before adjustment "+  initalAccountBal, LogStatus.INFO, true);
                        initalAccountBal=initalAccountBal.replace("(", "").replace(")", "").replace("$", "").replace(",", "");
                
                        FunctionLibrary.scrollDown("New", "Scroll down");
                        //click on new button @account payment tab
                        Desc = "Clicking on new button";
                        FunctionLibrary.clickObject(adjustmentsNewBtn, "", Desc);
                        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(categoryLabel, adjustmentsNewBtn, 6, 24);
                        FunctionLibrary.waitForObject.until(ExpectedConditions.attributeToBe(By.xpath(categoryTxtBox), "aria-readonly", "false"));
                        //enter Category_Label
                        Desc = "Entering Category_Label";
                        Desc = "Entering Sub category label";
                        FunctionLibrary.setText(subCategoryLabel, adjustmentSubCategoryType, Desc);
                        
                        //enter qunantity
                        Desc = "Entering Unit_Price_Label";
                        FunctionLibrary.setText(quantityLabel, "1", Desc);
                        //enter Unit_Price_Label
                        String amount = "1" ;
                        if(expectedStatus.equalsIgnoreCase("LOW"))
                        {
                                amount=String.valueOf(Double.valueOf(initalAccountBal)-1);
                        }
                        else if(expectedStatus.equalsIgnoreCase("INSUFFICENT"))
                        {
                                amount =String.valueOf(Double.valueOf(initalAccountBal)+10);
                        } 
                        else if(expectedStatus.equalsIgnoreCase("ZERO"))
                        {
                                amount =String.valueOf(Double.valueOf(initalAccountBal));
                        } 
                     
                        
                        Desc = "Entering Unit_Price_Label";
                        FunctionLibrary.setText(unitPriceLabel, amount, Desc);                
                        
                        Desc = "clicking on Adjust button";
                        //FunctionLibrary.ObjDriver.findElement(By.xpath(adjustButton)).click();
                        FunctionLibrary.clickObject(""+adjustButton,"","Click adjust button");
                        
                                  //wait for an alert
                             FunctionLibrary.waitForObject.until(ExpectedConditions.alertIsPresent());
                             Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                             System.out.println("Popup is displayed with text" + alert.getText());
                             String alertText = alert.getText();
                             alert.accept();
                             //get payment reference number
                             String[] paymentRefNumber = alertText.split(":");
                             String transactionId= paymentRefNumber[1].trim();
                             System.out.println(transactionId);
                             ReportLibrary.addStep(ReportLibrary.Test_Step_Number,  "Transaction processed. Payment Reference number " + transactionId, LogStatus.INFO, true);
                           
                             finStatus = FunctionLibrary.getText(accountFinancialStatusTxtBox, "Get the text on financial status");
                                
                                if(finStatus.equalsIgnoreCase(expectedStatus))
                                {
                                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Financial status of account is "+expectedStatus, LogStatus.PASS, true);
                                
                                }else
                                {
                                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Financial status of account is not "+expectedStatus, LogStatus.FAIL, true);
                                }
                }
                else
                {
                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Financial status of account is "+expectedStatus, LogStatus.INFO, true);
                }

    }
    public static void paymentRefundForSRCreation(String acctNumber, String isWithInThreshold) throws InterruptedException
   {
                
            String refundPayType = dataObj.get("Payment Type");
            String [] minMaxThresholdValues  = getMinMaxThresholdValues(dataObj.get("User Responsibility"),refundPayType,"REFUND");
            String minimumThresholdBal = minMaxThresholdValues[0];
            String maximumThresholdBal= minMaxThresholdValues[1];
           //reading less value for minimum threshold value
            String existingAmount=minimumThresholdBal;
           //existingAmount=existingAmount.replace("$","");
            existingAmount=existingAmount.replace(",","");
           
           Double amountToBeRefund;
           if(isWithInThreshold.equalsIgnoreCase("Yes"))
           {
                   amountToBeRefund=Double.valueOf(minimumThresholdBal)+110;   
           }
           else
           {
                   amountToBeRefund=Double.valueOf(maximumThresholdBal)+1;
           }
           if(isWithInThreshold.equalsIgnoreCase("Equal"))
           {
                   amountToBeRefund=Double.valueOf(minimumThresholdBal);
           }

           ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "existing threshold amount "+
                   ""+existingAmount, LogStatus.INFO, true);

           //search for account
           CommonLibrary.searchForAccount(acctNumber);
           
           //click the link Financial
           FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='#s_sctrl_tabView_noop' and text()='Financials']")));
           FunctionLibrary.clickObject(linkFinancial, "", "click on financials");


           String accountBalBeforePayemnt=FunctionLibrary.getText(accountBalTxtBox, "Account Balance");

           ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Existing acccount balance: "+
                                   accountBalBeforePayemnt,LogStatus.INFO,false);


           FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='ui-tabs-anchor' and text()='Account Replenishment']")));
           FunctionLibrary.clickObject(accountReplenishment, "", "click on accountReplenishment");
           FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(newBtnAmount,accountReplenishment,15,34);
           FunctionLibrary.clickObject(newBtnAmount, "", "click on newBtnAmount");
           FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(amonutTxtBox,newBtnAmount,15,34);
           
           try {
                                                Thread.sleep(3000);
                                } catch (InterruptedException e1) {
                                                // TODO Auto-generated catch block
                                                e1.printStackTrace();
                                }
           FunctionLibrary.waitForObject.until(ExpectedConditions.attributeToBe(By.xpath("//input[@aria-labelledby='BalanceForward_Label']"),
                                "aria-readonly", "false"));
           ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Amonut being added : "+
                                   amountToBeRefund,LogStatus.INFO,false);
           FunctionLibrary.setText(amonutTxtBox, amountToBeRefund.toString(), "Enter Amount");
         
           FunctionLibrary.clickObject(replenishmentSave, "", "click on replenishmentSave");

           FunctionLibrary.clickObject(newPaymentList, "", "click on newPaymentList");
           
           FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(saveRebillInfo,
                                newPaymentList, 3, 20);
           
           FunctionLibrary.scrollDowntoElement(saveRebillInfo, "Scroll down");
           
           //Thread.sleep(2000);

           FunctionLibrary.selectDropDownListByValue(replenishmentDetailsRebillPayTypeDropdown,refundPayType,"select cash from dropdown");
           if(refundPayType.equalsIgnoreCase("SAVINGS") || refundPayType.equalsIgnoreCase("CHECKING"))
           {
               //Enter Bank Account no:
               FunctionLibrary.setText(paymentDetailsBankAccountNbrTxtBox,dataObj.get("BankAccount"),"Enter bank number");
               // Enter Routing No:
               FunctionLibrary.setText(paymentDetailsRoutingNbrTxtBox,dataObj.get("BankRoutine"),"Enter Routing number");
           }
           
           if(refundPayType.equalsIgnoreCase("CHECK"))
           {
               //Enter Bank Account no:
                   FunctionLibrary.setText(checkPay, dataObj.get("CheckNumber"),"Enter Check number");
               try {

                   WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,5);
                   wait2.until(ExpectedConditions.alertIsPresent());
                   Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                   //System.out.println(alert.getText());
                   alert.accept();
                   //Thread.sleep(3000);
               }
               catch (Exception e) {
                   //exception handling
               }
           }
           
           FunctionLibrary.clickObject(saveRebillInfo, "", "click on saveRebillInfo");
           //handle the Payment Processing pop-up alert
           try {

               WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,8);
               wait2.until(ExpectedConditions.alertIsPresent());
               Alert alert = FunctionLibrary.objDriver.switchTo().alert();
               //System.out.println(alert.getText());
               alert.accept();
               //Thread.sleep(3000);
           }
           catch (Exception e) {
               //exception handling
           }

        //   FunctionLibrary.scrollDowntoElement(savePaymentList, "Scroll down");
           FunctionLibrary.clickObject(savePaymentList, "", "click on savePaymentList");
           FunctionLibrary.clickObject(processPay, "", "click on processPay");
           Thread.sleep(2000);
           if(refundPayType.equalsIgnoreCase("CHECK"))
           {
                   try {

                   WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,5);
                   wait2.until(ExpectedConditions.alertIsPresent());
                   Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                   String alertText = alert.getText();
                   System.out.println(alertText);
                   alert.accept();
                   //Thread.sleep(3000);

               }
               catch (Exception e) {
                   //exception handling
               }

           
           }
           String processNum = null;
           try {

               WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,40);
               wait2.until(ExpectedConditions.alertIsPresent());
               Alert alert = FunctionLibrary.objDriver.switchTo().alert();
               String alertText = alert.getText();
               System.out.println(alertText);
               processNum = alertText.split("Payment Confirmation Number : ")[1];
               processNum=processNum.split(" ")[0].trim();
               System.out.println(processNum);
               alert.accept();
               //Thread.sleep(3000);

           }
           catch (Exception e) {
               //exception handling
          }

           FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@title='Financial Transaction List:Reverse']")));

        /*   FunctionLibrary.clickObject(reverseBtn, "", "clicking on reverse");
           try {

               WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,8);
               wait2.until(ExpectedConditions.alertIsPresent());
               Alert alert = FunctionLibrary.objDriver.switchTo().alert();
               //System.out.println(alert.getText());
               alert.accept();
               //Thread.sleep(3000);

           }
           catch (Exception e) {
               //exception handling
           }*/
           
           String categoryValue="TOLDIPOSIT";
           // String refundPaymentAmount=dataObj.get("RefundAmount");
           // FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='ui-id-268']")));
            FunctionLibrary.clickObject(refundButton, "", "click on Refund");
            Thread.sleep(3000);
            FunctionLibrary.clickObject(newButtonRefund, "", "click on New");
            try {
                                                Thread.sleep(3000);
                                } catch (InterruptedException e1) {
                                                // TODO Auto-generated catch block
                                                e1.printStackTrace();
                                }
             String Desc="enter the value for Category Label";
             WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver,100);
             wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-labelledby='Category_Label']")));
            
           // FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@aria-labelledby='Category_Label']")));
            FunctionLibrary.setText(refundCategoryLabel, categoryValue, Desc);            
            FunctionLibrary.clickObject(popUpWindowOkCategoryLabelRefund, "", "click on OK");
            try {
                                                Thread.sleep(3000);
                                } catch (InterruptedException e1) {
                                                // TODO Auto-generated catch block
                                                e1.printStackTrace();
                                }
           /* WebDriverWait wait1 = new WebDriverWait(FunctionLibrary.objDriver,100);
            wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-labelledby='Payment_Category_Label']"))); */
            FunctionLibrary.waitForObject.until(ExpectedConditions.attributeToBe(By.xpath("//input[@aria-labelledby='Payment_Category_Label']"), "aria-readonly", "false"));
            FunctionLibrary.setText(paymentRefundLabel, categoryValue, "enter Payment Value");
            FunctionLibrary.clickObject(popUpWindowPaymentTransactionRefund, "", "click on OK");
            String AmountToRefunded=dataObj.get("RefundAmount");
            try {
                                                Thread.sleep(3000);
                                } catch (InterruptedException e1) {
                                                // TODO Auto-generated catch block
                                                e1.printStackTrace();
                                }
           // WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,100);
           // wait2.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-labelledby='Amount_Label']")));
            FunctionLibrary.setText(amountLabel, AmountToRefunded, "enter amount for Refund");
            String refundCode="OTHER";
            FunctionLibrary.setText(refundCodeLabel, refundCode, "enter refund code");
            try {
                                                Thread.sleep(2000);
                                } catch (InterruptedException e1) {
                                                // TODO Auto-generated catch block
                                                e1.printStackTrace();
                                }
            FunctionLibrary.clickObject(refundSaveBtn, "", "click on save");
            try {
                                                Thread.sleep(3000);
                                } catch (InterruptedException e1) {
                                                // TODO Auto-generated catch block
                                                e1.printStackTrace();
                                }
            FunctionLibrary.clickObject(refundBtn, "", "click on Refund"); 
           Thread.sleep(5000);

           if(isWithInThreshold.equalsIgnoreCase("YES") || isWithInThreshold.equalsIgnoreCase("Equal") )
           {   
                   
                   String accountBalAfterPayment=FunctionLibrary.getText(accountBalTxtBox, "Account Balance");
                   accountBalAfterPayment=accountBalAfterPayment.replace("$", "");
                   accountBalAfterPayment=accountBalAfterPayment.replace(",", "");
    
                           Double postTransBalForTypeR = amountToBeRefund +Double.valueOf(accountBalAfterPayment);
                           String [] tranTypes = dataObj.get("TransType").split(";");
                           boolean isXrTranTypeItemCreated =  verifyReversalTransactionInfoUnderReversalTab(processNum,refundPayType,
                                                   tranTypes,amountToBeRefund.toString());
                           
                           if(isXrTranTypeItemCreated)
                           {
                           verifyReversalInfoUnderPaymentNotes(refundPayType,amountToBeRefund.toString());          
                                      
                           FunctionLibrary.clickObject("//a[text()='History']", "", "Click history link");
                           FunctionLibrary.clickObject(financialsUnderHistroy,"","Click Financial under history");
                           //FunctionLibrary.clickObject(financialHistroyTable,"","Click Financial history table");
                           FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Account Financials History List:View CC']")));
                           verifyReversalInfoUnderHistoryTab(processNum,refundPayType, tranTypes,"TOLDEPOSIT","PPTL", 
                                                amountToBeRefund.toString(),String.valueOf(postTransBalForTypeR));
                           accountBalBeforePayemnt=accountBalBeforePayemnt.replace(",", "").replace("$", "");
                           if(Double.valueOf(accountBalBeforePayemnt).equals(Double.valueOf(accountBalAfterPayment)))
                           {
                               ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Account Balance after Reversing Amount :"+accountBalBeforePayemnt+
                                       "<br>"+"The Account Balance is :"+accountBalAfterPayment, LogStatus.PASS, true);

                           }
                           else
                           {
                               ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Account Balance after Reversing Amount :"+ accountBalBeforePayemnt +
                                       "<br>"+"Actual:Account Balance is :"+accountBalAfterPayment, LogStatus.FAIL, true);

                           }


                           }
           }
           else
           {

               String xpathOfFinancialTable = ".//*[@summary='Service Request List']";
               int rowNumber1 = FunctionLibrary.getRowNumberFromWebTable(xpathOfFinancialTable, "ESCALATION", "Get the row number");
              /* ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                       "Capturing Screen for Service Request", LogStatus.INFO, true);*/
               System.out.println(rowNumber1);
               
               FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable, rowNumber1, 6, "Open",
                       "Verifying status", true);
               FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable,rowNumber1,4,
                       "ESCALATION","Verifying QUEUE Type ", false);
               FunctionLibrary.verifyWebTableCellData(xpathOfFinancialTable,rowNumber1,5,"REVERSAL","Verifying Sub Queue of ESCALATION", false);
               FunctionLibrary.verifyTextBoxValue("//input[@aria-label='Pay Type']", refundPayType, "Verify Paytype", false);
               
               String transactionIdShowing = FunctionLibrary.getText("//input[@aria-label='Composite Transaction Id']", "Get the Transaction Id");
               transactionIdShowing = transactionIdShowing.replace(",", "");
               
               if(Double.valueOf(processNum).equals(Double.valueOf(transactionIdShowing)))
               {
                   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Process number: "+processNum+"<br> Actual: "+transactionIdShowing, 
                                                   LogStatus.PASS,false);
               }else
               {
                   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Process number: "+processNum+"<br> Actual: "+transactionIdShowing, 
                                                   LogStatus.FAIL,false);
               }
           
               String amountShowing = FunctionLibrary.getText("//input[@aria-label='Amount']", "Get the Amount");
               amountShowing = amountShowing.replace(",", "").replace("$", "");
               if(Double.valueOf(amountToBeRefund).equals(Double.valueOf(amountShowing)))
               {
                   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Amount:: "+amountToBeRefund+"<br> Actual: "+amountShowing, 
                                                   LogStatus.PASS,false);
               }else
               {
                   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Amount:: "+amountToBeRefund+"<br> Actual: "+amountShowing, 
                                                   LogStatus.FAIL,false);
               }
           }
   }

  //Two Time Payment
    public static void twoTimePayment(String acctNumber) throws Exception
    {
        
        String transactionId="";
        String amountPaid;

      //search for account
     CommonLibrary.searchForAccount(acctNumber);  
     String AccountBal=FunctionLibrary.getText(accountBalTxtBox, "Account Balance");
     String existingAmount=AccountBal;
     existingAmount=existingAmount.replace("$","").replace("(","").replace(")","").replace(",","");
     
     amountPaid = dataObj.get("Payment Amount");        
     Double amount = Double.valueOf(existingAmount);
     Double finalAccountBal=amount+Double.valueOf(amountPaid);
     ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Account Balance before adding Amount :"+"<br>"+
                                "Account Balance is :"+existingAmount, LogStatus.INFO, true);
     
     //click the link Financial
     FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(linkFinancial)));
     FunctionLibrary.clickObject(linkFinancial, "", "click on financials");
     FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(accountReplenishment,linkFinancial,15,34);
     //FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(accountReplenishment)));
     FunctionLibrary.clickObject(accountReplenishment, "", "click on accountReplenishment");
     FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(newBtnAmount,accountReplenishment,15,34);
     FunctionLibrary.clickObject(newBtnAmount, "", "click on newBtnAmount");
     FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(amonutTxtBox,newBtnAmount,15,34);
     Thread.sleep(3000);
     FunctionLibrary.waitForObject.until(ExpectedConditions.attributeToBe(By.xpath(balanceForwardTxtBox),
                                "aria-readonly", "false"));
     FunctionLibrary.setText(amonutTxtBox, dataObj.get("Payment Amount"), "Enter Amount");
     
     FunctionLibrary.clickObject(replenishmentSave, "", "click on replenishmentSave");
     FunctionLibrary.scrollDowntoElement(newPaymentList, "Scroll down");
     FunctionLibrary.clickObject(newPaymentList, "", "click on newPaymentList");
     
     
     FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(saveRebillInfo,
                                newPaymentList, 3, 20);
     
                FunctionLibrary.selectDropDownListByValue(replenishmentDetailsRebillPayTypeDropdown,dataObj.get("Payment Type"),"Select payment type from the drop down");
    
     FunctionLibrary.scrollDowntoElement(saveRebillInfo, "Scroll down");
     if(dataObj.get("Payment Type").equalsIgnoreCase("SAVINGS") || dataObj.get("Payment Type").equalsIgnoreCase("CHECKING"))
     {
         //Enter Bank Account no:
         FunctionLibrary.setText(paymentDetailsBankAccountNbrTxtBox,dataObj.get("BankAccount"),"Enter bank number");
         // Enter Routing No:
         FunctionLibrary.setText(paymentDetailsRoutingNbrTxtBox,dataObj.get("BankRoutine"),"Enter Routing number");
     }
    
     if(dataObj.get("Payment Type").contains("CHECK"))
     {
         //Scroll down to Check button
         FunctionLibrary.scrollDowntoElement(checkPay, "Scroll dwon to Check number field");
         //Enter CHECK no:
         FunctionLibrary.setText("//input[@id='ChkNumber']",dataObj.get("CheckNumber"),"Enter check number");
     }
     
     //Rebill Pay Type with Creditcard
    if(dataObj.get("Payment Type").equalsIgnoreCase("MASTERCARD") || dataObj.get("Payment Type").equalsIgnoreCase("AMEX") || dataObj.get("Payment Type").equalsIgnoreCase("DISCOVER")|| dataObj.get("Payment Type").equalsIgnoreCase("VISA"))
     {
                FunctionLibrary.selectDropDownListByValue(replenishmentDetailsRebillPayTypeDropdown,"CASH","Select payment type from the drop down");
         
                FunctionLibrary.selectDropDownListByValue(replenishmentDetailsRebillPayTypeDropdown,dataObj.get("Payment Type"),"Select payment type from the drop down");
                
         FunctionLibrary.setText(creditCardNoField,dataObj.get("CreditCardNo"),"Enter Credit Card number");
         //Credit card Expiration Month
         FunctionLibrary.selectDropDownListByValue(creditCardExpMpnth,dataObj.get("ExpMonth"),"Select Expiration Month");
         //Credit card Expiration Year
         FunctionLibrary.selectDropDownListByValue(creditCardExpYear,dataObj.get("ExpYear"),"Select Expiration Year");

     }
     FunctionLibrary.clickObject(saveRebillInfo, "", "click on saveRebillInfo");
     
     try {

         WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,10);
         wait2.until(ExpectedConditions.alertIsPresent());
         Alert alert = FunctionLibrary.objDriver.switchTo().alert();
         alert.accept();

     }
     catch (Exception e1) {
         //exception handling
     }

     
     FunctionLibrary.scrollDowntoElement(savePaymentList, "Scroll down");
     FunctionLibrary.clickObject(savePaymentList, "", "click on savePaymentList");
     Thread.sleep(2000);
     try {

         WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,4);
         wait2.until(ExpectedConditions.alertIsPresent());
         Alert alert = FunctionLibrary.objDriver.switchTo().alert();
         alert.accept();

     }
     catch (Exception e) {
         //exception handling
     }
       FunctionLibrary.clickObject(processPay, "", "click on processPay");
       
       try
       {
    	   Boolean b=objDriver.findElement(By.xpath(processPay)).isDisplayed();
           if(b==true)
           {
        	   Boolean b1=objDriver.findElement(By.xpath(processPay)).isEnabled();
        	   if(b1==true)
        	   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:The Process Pay button should not be Enabled after clicking on One time : "
                       +"<br>"+"Actual:The Process pay button is not Getting enabled: ", LogStatus.INFO, true);
           }
           else{
        	   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: The system should navigate to the Financial Transaction List screen: "
                       +"<br>"+"Actual:The system is Navigating to the Financial Transaction List screen: ", LogStatus.PASS, true);
           }
       }
       catch (Exception e)
       {
    	   
       }
       try {

           WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,40);
           wait2.until(ExpectedConditions.alertIsPresent());
           Alert alert = FunctionLibrary.objDriver.switchTo().alert();
           transactionId= alert.getText().split(":")[1].trim();
           alert.accept();
           System.out.println(transactionId);
           ReportLibrary.addStep(ReportLibrary.Test_Step_Number,  "Transaction processed. Payment Reference number " + transactionId, LogStatus.INFO, true);
        
           //Thread.sleep(3000);

       }
       catch (Exception e) {
           //exception handling
       }
       
            
       //verifying ProcessPay button visibility
      /* Boolean b=objDriver.findElement(By.xpath(processPay)).isDisplayed();
       if(b==true)
       {
    	   Boolean b1=objDriver.findElement(By.xpath(processPay)).isEnabled();
    	   if(b1==true)
    	   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:The Process Pay button should not be Enabled after clicking on One time : "
                   +"<br>"+"Actual:The Process pay button is not Getting enabled: ", LogStatus.INFO, true);
       }
       else{
    	   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: The system should navigate to the Financial Transaction List screen: "
                   +"<br>"+"Actual:The system is Navigating to the Financial Transaction List screen: ", LogStatus.PASS, true);
       }*/
      // FunctionLibrary.DblclickObject(processPay, "", "Double click on processPay");
    /* Actions action = new Actions(objDriver);
     //action.moveToElement(objDriver.findElement(By.xpath(processPay))).doubleClick().build().perform();
     WebElement ele  = objDriver.findElement(By.xpath(processPay));     
     action.doubleClick(ele);
     action.perform();*/
    

     ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Amount Added :"+dataObj.get("Payment Amount"), LogStatus.INFO, true);
     
    verifyFinancialTransactionInfoUnderReversalTab(transactionId,dataObj.get("Payment Type"),dataObj.get("TransType"), amountPaid);
    verifyOneTimePaymentNotes(dataObj.get("Payment Type"),amountPaid);
               FunctionLibrary.clickObject(historyTab, "", "Click history link");
    FunctionLibrary.clickObject(financialsUnderHistroy,"","Click Financial under history");
    FunctionLibrary.clickObject(financialHistroyTable,"","Click Financial history table");
    
     verifyFinancialHistoryInfoUnderHistoryTab(transactionId,dataObj.get("Payment Type"),dataObj.get("TransType"), "TOLDEPOSIT","PPTL", 
                                 amountPaid,String.valueOf(finalAccountBal));
    }
    
}
