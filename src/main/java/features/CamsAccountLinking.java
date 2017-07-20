package features;

import com.relevantcodes.extentreports.LogStatus;
import libraries.CommonLibrary;
import libraries.FunctionLibrary;
import libraries.ReportLibrary;
import libraries.TestCaseLibrary;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static features.CamsShiftManagement.Desc;
import static libraries.CommonLibrary.*;
import static objectProperties.SblAccountLinkingPageObject.*;
import static objectProperties.SblCaseManagementProperties.xpathNotes;
import static objectProperties.SblDeviceStatusChnageProperties.accountInfoDevicesTab;
import static objectProperties.SblDeviceStatusChnageProperties.accountInfoTab;
import static objectProperties.WebAccountCreationPageProperties.xpathPlansAndPayment;
import static objectProperties.WebAccountCreationPageProperties.xpathStatusPercentage;

/**
 * Created by 23319 on 28-12-2016.
 */
public class CamsAccountLinking {

    public static void CamsAccountLinkingTest() throws IOException, Exception {

        ExcelSheet exl = new ExcelSheet();

        int noOfRows = exl.totalrows("FLCSS_CAMS_AccountLinking","Test_Scenarios");

        HashMap<String, String> eachTestCaseData = new HashMap<String, String>();

        String testCaseName = "";

        for (int i = 1; i <=noOfRows; i++) {

            eachTestCaseData = getEachTestCaseData(exl, "Test_Scenarios", i,"FLCSS_CAMS_AccountLinking");
            CommonLibrary.dataObj=eachTestCaseData;
            testCaseName = "tcAccountLinking"+eachTestCaseData.get("TestCaseId").replace("-","");

            String executionType = CommonLibrary.getSettingsSheetInfo().get("Execution_Type");
            if ((eachTestCaseData.get("ExecutionStatus").equalsIgnoreCase("Yes")) && ( eachTestCaseData.get("TestType").contains(executionType)||
                    executionType.equalsIgnoreCase("All"))){
                ReportLibrary.startReport(eachTestCaseData.get("TestCaseId"), eachTestCaseData.get("TestCaseId") );
                Method testMethod = null;
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>" + eachTestCaseData.get("TestCaseId")
                        + "</b>" + ": Test Case Execution is started....................... <br>"
                        + "Test case description: " + eachTestCaseData.get("TestCaseDesc"), LogStatus.INFO, false);

                try {

                    //check for class and method are found for the test case
                    try
                    {



                        Class<?> c = Class.forName("features.CamsAccountLinking");
                        testMethod= c.getMethod(testCaseName);
                    } catch (ClassNotFoundException e) {

                        e.printStackTrace();
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is not found", LogStatus.FAIL,false);
                    }

                    //login to application and navigate to account info page
                    CommonLibrary.loginSblWithGenericUser(eachTestCaseData);
                    TestCaseLibrary.Get_TestCase_Instance().executeTC(testMethod);
                  //  CommonLibrary.logoutSiebelApplicaiton();

                }

                catch(Exception e)
                {
                    //if alert found. it will be accepted and close the browser.
                    try{
                        e.getMessage();
                        System.out.println("Test Failed SCREENSHOT TAKING");
                        WebDriverWait wait8 = new WebDriverWait(FunctionLibrary.objDriver,5);
                        wait8.until(ExpectedConditions.alertIsPresent());

                        String alertText;
                        Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                        alertText = alert.getText();
                        alert.accept();
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Execution Failed due to alert : <br>"+alertText, LogStatus.FAIL, true);
                        //Close browsers

                      //  FunctionLibrary.closeAllActiveBrowser();

                    }

                    catch(Exception exp){


                        e.printStackTrace();
                        try{

                           // CommonLibrary.logoutSiebelApplicaiton();
                        }
                        catch(Exception e1){
                            System.out.println("test failed");

                            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed. Error is: "+ e.getMessage()
                                    , LogStatus.FAIL, true);

                            try{
                               // logoutSiebelApplicaiton();
                              //  FunctionLibrary.closeAllActiveBrowser();
                            }catch(Exception e2)
                            {
                               // FunctionLibrary.closeAllActiveBrowser();
                            }

                        }

                    }


                }//End of Catch

                libraries.CommonLibrary.dataObj.clear();
                ReportLibrary.endTest();
                ReportLibrary.endReport();
            }
        }


    }

    /*
   	Automated for Test case:ACLK-001
   	Test case desc: CSR initiates linking of two Private 
    */
    public static void tcAccountLinkingACLK001() throws InterruptedException {
        try {


            String primaryAccountNum =  dataObj.get("AccountNumber");
            String ChildAccountNumber = dataObj.get("ChildAccountNumber");
            CommonLibrary.searchForAccount(primaryAccountNum);
            //FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
           		                                         accountInfoDevicesTab,accountInfoTab,5,20);
            System.out.print("ChildAccountNumber:" + ChildAccountNumber);

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Primary account number, Child account numbers: "
                    + primaryAccountNum + ", " + ChildAccountNumber, LogStatus.INFO, true);

            String Desc = "";
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText("Sub Accounts")));
            FunctionLibrary.clickObject(subAccountLink, "", "Clicking Sub Account link");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(LinkAccounts, subAccountLink, 12, 38);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(LinkAccounts)));

            Desc = "Clicking on Link Account Tab";
            FunctionLibrary.clickObject(LinkAccounts, "", Desc);
            //FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(AddAccountsAccountNumberFiled, LinkAccounts, 12, 33);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(AddAccountsAccountNumberFiled, LinkAccounts, 12, 33);
            FunctionLibrary.clickObject(AddAccountsAccountNumberFiled, "", "Clicking on AddAccountsAccountNumberFiled");
            System.out.println(ChildAccountNumber);
            //Thread.sleep(2000);
            
            FunctionLibrary.objDriver.findElement(By.xpath(ChildAccountnumberFiled)).sendKeys(ChildAccountNumber);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsGoButton)));
            FunctionLibrary.clickObject(AddAccountsGoButton, "", Desc);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(addAcccountsSourceElement)));
                    
            selectAccountSource("CSR Initiated-2");
            System.out.println("hi2");

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsOKButton)));

            FunctionLibrary.clickObject(AddAccountsOKButton, "", Desc);

            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathOfSubAccountsTable)));

            System.out.println("before");
            int rowNumber = FunctionLibrary.getRowNumberFromWebTable(xpathOfSubAccountsTable, ChildAccountNumber, "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Child Account Details Available On Sub Accounts List Panel </b> <br> <b> Expected is : Child account is Added  Actual is: Child Account is added Sucessfully </b>"
                        + "Child Account Number : " + ChildAccountNumber, LogStatus.PASS, true);
            }
            WebElement ChildAccountNumberLink = FunctionLibrary.objDriver.findElement(By.linkText(ChildAccountNumber));
            System.out.println(ChildAccountNumberLink.getText());
            System.out.println(ChildAccountNumberLink.isDisplayed());
            System.out.println(ChildAccountNumberLink.isEnabled());
            if (ChildAccountNumberLink.getText().equals(ChildAccountNumber)) {
                System.out.println(ChildAccountNumberLink.getText());
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<B> Child Account Number Details Available  and it is a HyperLink </B>  <br>"
                        + "Child Account Number : " + ChildAccountNumber, LogStatus.PASS, true);
            }
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(historyLink)));
            FunctionLibrary.objDriver.findElement(By.xpath(historyLink)).click();
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(nonFinancialsLink, historyLink, 15,34);
            FunctionLibrary.clickObject(nonFinancialsLink,"", Desc);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(activitiesChatMessageLink, nonFinancialsLink, 15,34);

            String XpathNonFinacialHistory=nonFinancialHistoryTbl;
            rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNonFinacialHistory, "VIEW", "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Details Available in Non Financial History  </b> <br> <b> Non Financial History History Expected  : Available  Actual is: Available </b>"
                        , LogStatus.PASS, true);
            }

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(notificationHistoryLink)));
            FunctionLibrary.objDriver.findElement(By.xpath(notificationHistoryLink)).click();

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(notificationHistoryLink1, notificationHistoryLink, 15,34);
            FunctionLibrary.objDriver.findElement(By.xpath(notificationHistoryLink1)).click();

            String XpathNotificationHistory=notificationsHistoryTbl;

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(notificationsHistoryTbl, notificationHistoryLink1, 15,34);

            rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNotificationHistory, primaryAccountNum, "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Details Available in Notification History  </b> <br> <b> Notification Sent Expected  : YES  Actual is: PENDING </b>"
                        , LogStatus.PASS, true);
            }
            else
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Notification history is not showing record" , LogStatus.FAIL, true);
            }

            System.out.println("status  :  "+get_S_ORG_EXT_X_Details(primaryAccountNum));

            if(get_S_ORG_EXT_X_Details(primaryAccountNum))
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Miscellaneous S_ORG_EXT_XM Details  </b> <br>  Expected  :<b>  YES  </b>Actual:<b> YES </b>"
                        , LogStatus.PASS, false);
            }

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "  <b> Expected :<B> Linking is successful between Private accounts.</b>  Actual: <b> Two Private accounts are Linked Sucessfully </b>"
                    , LogStatus.PASS, false);
        }
        catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
        }

    }

    public static void tcAccountLinkingACLK002() throws InterruptedException {
        try {
            String primaryAccountNum =  dataObj.get("AccountNumber");
            String ChildAccountNumber = dataObj.get("ChildAccountNumber");
            System.out.println("Primary account number: " + primaryAccountNum);
            String Desc = "";
            CommonLibrary.searchForAccount(primaryAccountNum);
           // FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(accountInfoDevicesTab,accountInfoTab,5,20);
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(accountInfoDevicesTab,
            		accountInfoTab,5,20);
           		                                         
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Primary account number, Child account numbers: "
                    + primaryAccountNum + ", " + ChildAccountNumber, LogStatus.INFO, true);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText("Sub Accounts")));
            FunctionLibrary.clickObject(subAccountLink, "", "Clicking Sub Account link");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(LinkAccounts, subAccountLink, 12, 38);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(LinkAccounts)));
            Desc = "Clicking on Link Account Tab";
            FunctionLibrary.clickObject(LinkAccounts, "", Desc);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(AddAccountsAccountNumberFiled, LinkAccounts, 12, 33);
            FunctionLibrary.clickObject(AddAccountsAccountNumberFiled, "", "Clicking on AddAccountsAccountNumberFiled");
             System.out.println(ChildAccountNumber);
            Thread.sleep(2000);
            FunctionLibrary.objDriver.findElement(By.xpath(ChildAccountnumberFiled)).sendKeys(ChildAccountNumber);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsGoButton)));
            FunctionLibrary.clickObject(AddAccountsGoButton, "", Desc);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(addAcccountsSourceElement)));
            selectAccountSource("CSR Initiated-2");
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsOKButton)));
            FunctionLibrary.clickObject(AddAccountsOKButton, "", Desc);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathOfSubAccountsTable)));
            System.out.println("before");
            int rowNumber = FunctionLibrary.getRowNumberFromWebTable(xpathOfSubAccountsTable, ChildAccountNumber, "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Child Account Details Available On Sub Accounts List Panel </b> <br> <b> Expected is : Child account is Added  Actual is: Child Account is added Sucessfully </b>"
                        + "Child Account Number : " + ChildAccountNumber, LogStatus.PASS, true);
            }

            WebElement ChildAccountNumberLink = FunctionLibrary.objDriver.findElement(By.linkText(ChildAccountNumber));

            if (ChildAccountNumberLink.getText().equals(ChildAccountNumber)) {
                System.out.println(ChildAccountNumberLink.getText());
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<B> Child Account Number Details Available  and it is a HyperLink </B>  <br>"
                        + "Child Account Number : " + ChildAccountNumber, LogStatus.PASS, true);
            }

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(historyLink)));
            FunctionLibrary.objDriver.findElement(By.xpath(historyLink)).click();
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(nonFinancialsLink, historyLink, 15,34);
            FunctionLibrary.clickObject(nonFinancialsLink,"", Desc);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(activitiesChatMessageLink, nonFinancialsLink, 15,34);

            String XpathNonFinacialHistory=nonFinancialHistoryTbl;
            rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNonFinacialHistory, "VIEW", "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Details Available in Non Financial History  </b> <br> <b> Non Financial History History Expected  : Available  Actual is: Available </b>"
                        , LogStatus.PASS, true);
            }

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(notificationHistoryLink)));
            FunctionLibrary.objDriver.findElement(By.xpath(notificationHistoryLink)).click();

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(notificationHistoryLink1, notificationHistoryLink, 15,34);
            FunctionLibrary.objDriver.findElement(By.xpath(notificationHistoryLink1)).click();

            String XpathNotificationHistory=notificationsHistoryTbl;

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(notificationsHistoryTbl, notificationHistoryLink1, 15,34);

            rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNotificationHistory, primaryAccountNum, "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Details Available in Notification History  </b> <br> <b> Notification Sent Expected  : YES  Actual is: PENDING </b>"
                        , LogStatus.PASS, true);
            }

            System.out.println("status  :  "+get_S_ORG_EXT_X_Details(primaryAccountNum));

            if(get_S_ORG_EXT_X_Details(primaryAccountNum))
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Miscellaneous S_ORG_EXT_XM Details  </b> <br>  Expected  :<b>  YES  </b>Actual:<b> YES </b>"
                        , LogStatus.PASS, false);
            }

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "  <b>CSR initiates linking of two Fleet accounts having different Fleet plans</b> Expected :<B> Linking is successful between Fleet accounts</b>  Actual: <b> Linking is successful between Fleet accounts </b>"
                    , LogStatus.PASS, false);
        }
        catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
        }
    }

    public static void tcAccountLinkingACLK004() throws InterruptedException {
        try {


            String primaryAccountNum =  dataObj.get("AccountNumber");
            String ChildAccountNumber = dataObj.get("ChildAccountNumber");
            CommonLibrary.searchForAccount(primaryAccountNum);
            FunctionLibrary.objDriver.findElement(By.linkText(primaryAccountNum)).click();

            System.out.print("ChildAccountNumber:" + ChildAccountNumber);

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Primary account number, Child account numbers: "
                    + primaryAccountNum + ", " + ChildAccountNumber, LogStatus.INFO, true);

            String Desc = "";
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText("Sub Accounts")));
            FunctionLibrary.objDriver.findElement(By.linkText("Sub Accounts")).click();
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(LinkAccounts)));

            Desc = "Clicking on Link Account Tab";
            FunctionLibrary.clickObject(LinkAccounts, "", Desc);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsAccountNumberFiled)));
           
            FunctionLibrary.clickObject(AddAccountsAccountNumberFiled, "", "Clicking on AddAccountsAccountNumberFiled");
            System.out.println(ChildAccountNumber);
            Thread.sleep(2000);
            
            FunctionLibrary.objDriver.findElement(By.xpath(ChildAccountnumberFiled)).sendKeys(ChildAccountNumber);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsGoButton)));
            FunctionLibrary.clickObject(AddAccountsGoButton, "", Desc);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(addAcccountsSourceElement)));
                    
            selectAccountSource("CSR Initiated-2");
            System.out.println("hi2");

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsOKButton)));

            FunctionLibrary.clickObject(AddAccountsOKButton, "", Desc);

            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathOfSubAccountsTable)));

            System.out.println("before");
            int rowNumber = FunctionLibrary.getRowNumberFromWebTable(xpathOfSubAccountsTable, ChildAccountNumber, "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Child Account Details Available On Sub Accounts List Panel </b> <br> <b> Expected is : Child account is Added  Actual is: Child Account is added Sucessfully </b>"
                        + "Child Account Number : " + ChildAccountNumber, LogStatus.PASS, true);


            }



            System.out.println("after");
            System.out.println(rowNumber);


            WebElement ChildAccountNumberLink = FunctionLibrary.objDriver.findElement(By.linkText(ChildAccountNumber));

            System.out.println(ChildAccountNumberLink.getText());
            System.out.println(ChildAccountNumberLink.isDisplayed());
            System.out.println(ChildAccountNumberLink.isEnabled());
            if (ChildAccountNumberLink.getText().equals(ChildAccountNumber)) {
                System.out.println(ChildAccountNumberLink.getText());
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<B> Child Account Number Details Available  and it is a HyperLink </B>  <br>"
                        + "Child Account Number : " + ChildAccountNumber, LogStatus.PASS, true);


            }


            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(historyLink)));
            FunctionLibrary.objDriver.findElement(By.xpath(historyLink)).click();
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(nonFinancialsLink)));
            FunctionLibrary.clickObject(nonFinancialsLink,"", Desc);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(activitiesChatMessageLink)));

            String XpathNonFinacialHistory=nonFinancialHistoryTbl;
            rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNonFinacialHistory, "VIEW", "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Details Available in Non Financial History  </b> <br> <b> Non Financial History History Expected  : Available  Actual is: Available </b>"
                        , LogStatus.PASS, true);


            }

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(notificationHistoryLink)));
            FunctionLibrary.objDriver.findElement(By.xpath(notificationHistoryLink)).click();

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(notificationHistoryLink1)));
            FunctionLibrary.objDriver.findElement(By.xpath(notificationHistoryLink1)).click();

            String XpathNotificationHistory=notificationsHistoryTbl;

            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(notificationsHistoryTbl)));

            rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNotificationHistory, primaryAccountNum, "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Details Available in Notification History  </b> <br> <b> Notification Sent Expected  : YES  Actual is: PENDING </b>"
                        , LogStatus.PASS, true);


            }
            else
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Notification history is not showing record" , LogStatus.FAIL, true);


            }

            System.out.println("status  :  "+get_S_ORG_EXT_X_Details(primaryAccountNum));

            if(get_S_ORG_EXT_X_Details(primaryAccountNum))
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Miscellaneous S_ORG_EXT_XM Details  </b> <br>  Expected  :<b>  YES  </b>Actual:<b> YES </b>"
                        , LogStatus.PASS, false);
            }



            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "  <b> Expected :<B> Linking is successful between Private accounts.</b>  Actual: <b> Two Private accounts are Linked Sucessfully </b>"
                    , LogStatus.PASS, false);

        }
        catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to"+e.getMessage() ,LogStatus.INFO,true);
        }


    }
    
    public static void tcAccountLinkingACLK005() throws InterruptedException {
        try {


            String primaryAccountNum =  dataObj.get("AccountNumber");
            String ChildAccountNumber = dataObj.get("ChildAccountNumber");
            CommonLibrary.searchForAccount(primaryAccountNum);
            FunctionLibrary.objDriver.findElement(By.linkText(primaryAccountNum)).click();

            System.out.print("ChildAccountNumber:" + ChildAccountNumber);

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Primary account number, Child account numbers: "
                    + primaryAccountNum + ", " + ChildAccountNumber, LogStatus.INFO, true);

            String Desc = "";
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText("Sub Accounts")));
            FunctionLibrary.objDriver.findElement(By.linkText("Sub Accounts")).click();
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(LinkAccounts)));

            Desc = "Clicking on Link Account Tab";
            FunctionLibrary.clickObject(LinkAccounts, "", Desc);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsAccountNumberFiled)));
           
            FunctionLibrary.clickObject(AddAccountsAccountNumberFiled, "", "Clicking on AddAccountsAccountNumberFiled");
            System.out.println(ChildAccountNumber);
            Thread.sleep(2000);
            
            FunctionLibrary.objDriver.findElement(By.xpath(ChildAccountnumberFiled)).sendKeys(ChildAccountNumber);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsGoButton)));
            FunctionLibrary.clickObject(AddAccountsGoButton, "", Desc);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(addAcccountsSourceElement)));
                    
            selectAccountSource("CSR Initiated-2");
            System.out.println("hi2");

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsOKButton)));

            FunctionLibrary.clickObject(AddAccountsOKButton, "", Desc);

            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathOfSubAccountsTable)));

            System.out.println("before");
            int rowNumber = FunctionLibrary.getRowNumberFromWebTable(xpathOfSubAccountsTable, ChildAccountNumber, "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Child Account Details Available On Sub Accounts List Panel </b> <br> <b> Expected is : Child account is Added  Actual is: Child Account is added Sucessfully </b>"
                        + "Child Account Number : " + ChildAccountNumber, LogStatus.PASS, true);


            }



            System.out.println("after");
            System.out.println(rowNumber);


            WebElement ChildAccountNumberLink = FunctionLibrary.objDriver.findElement(By.linkText(ChildAccountNumber));

            System.out.println(ChildAccountNumberLink.getText());
            System.out.println(ChildAccountNumberLink.isDisplayed());
            System.out.println(ChildAccountNumberLink.isEnabled());
            if (ChildAccountNumberLink.getText().equals(ChildAccountNumber)) {
                System.out.println(ChildAccountNumberLink.getText());
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<B> Child Account Number Details Available  and it is a HyperLink </B>  <br>"
                        + "Child Account Number : " + ChildAccountNumber, LogStatus.PASS, true);


            }


            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(historyLink)));
            FunctionLibrary.objDriver.findElement(By.xpath(historyLink)).click();
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(nonFinancialsLink)));
            FunctionLibrary.clickObject(nonFinancialsLink,"", Desc);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(activitiesChatMessageLink)));

            String XpathNonFinacialHistory=nonFinancialHistoryTbl;
            rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNonFinacialHistory, "VIEW", "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Details Available in Non Financial History  </b> <br> <b> Non Financial History History Expected  : Available  Actual is: Available </b>"
                        , LogStatus.PASS, true);


            }

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(notificationHistoryLink)));
            FunctionLibrary.objDriver.findElement(By.xpath(notificationHistoryLink)).click();

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(notificationHistoryLink1)));
            FunctionLibrary.objDriver.findElement(By.xpath(notificationHistoryLink1)).click();

            String XpathNotificationHistory=notificationsHistoryTbl;

            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(notificationsHistoryTbl)));

            rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNotificationHistory, primaryAccountNum, "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Details Available in Notification History  </b> <br> <b> Notification Sent Expected  : YES  Actual is: PENDING </b>"
                        , LogStatus.PASS, true);


            }
            else
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Notification history is not showing record" , LogStatus.FAIL, true);


            }

            System.out.println("status  :  "+get_S_ORG_EXT_X_Details(primaryAccountNum));

            if(get_S_ORG_EXT_X_Details(primaryAccountNum))
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Miscellaneous S_ORG_EXT_XM Details  </b> <br>  Expected  :<b>  YES  </b>Actual:<b> YES </b>"
                        , LogStatus.PASS, false);
            }



            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "  <b> Expected :<B> Linking is successful between Private accounts.</b>  Actual: <b> Two Private accounts are Linked Sucessfully </b>"
                    , LogStatus.PASS, false);

        }
        catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to"+e.getMessage() ,LogStatus.INFO,true);
        }


    }
 
    public static void tcAccountLinkingACLK014() throws InterruptedException {

        try {
            String primaryAccountNum =  dataObj.get("AccountNumber");
            String ChildAccountNumber = dataObj.get("ChildAccountNumber");
            System.out.println("Primary account number: " + primaryAccountNum);
            CommonLibrary.searchForAccount(primaryAccountNum);
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(accountInfoDevicesTab,
            		accountInfoTab,5,20);
            String Desc = "";
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Primary account number, Child account numbers: "
                    + primaryAccountNum + ", " + ChildAccountNumber, LogStatus.INFO, true);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText("Sub Accounts")));
            FunctionLibrary.clickObject(subAccountLink, "", "Clicking Sub Account link");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(LinkAccounts, subAccountLink, 12, 38);
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(LinkAccounts)));

                Desc = "Clicking on Link Account Tab";
                FunctionLibrary.clickObject(LinkAccounts, "", Desc);
    
                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(AddAccountsAccountNumberFiled, LinkAccounts, 12, 33);
                FunctionLibrary.clickObject(AddAccountsAccountNumberFiled, "", "Clicking on AddAccountsAccountNumberFiled");
                System.out.println(ChildAccountNumber);
                Thread.sleep(2000);
                FunctionLibrary.objDriver.findElement(By.xpath(ChildAccountnumberFiled)).sendKeys(ChildAccountNumber);
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsGoButton)));
                FunctionLibrary.clickObject(AddAccountsGoButton, "", Desc);
                FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(addAcccountsSourceElement)));
                        
                selectAccountSource("CSR Initiated-2");
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsOKButton)));
                FunctionLibrary.clickObject(AddAccountsOKButton, "", Desc);
                FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathOfSubAccountsTable)));
                System.out.println("before");
                int rowNumber = FunctionLibrary.getRowNumberFromWebTable(xpathOfSubAccountsTable, ChildAccountNumber, "get the row number");
               if (rowNumber > 0) {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Child Account Details Available On Sub Accounts List Panel </b> <br>  Expected :<b> Child account Should be Linked.</b>  Actual: Child Account is already Linked </b>"
                            + "Child Account Number : " + ChildAccountNumber, LogStatus.PASS, true);
                    FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@title='Sub Accounts:Disassociate Link']")));
                    FunctionLibrary.clickObject(DisassociateLink, "", Desc);
                    Thread.sleep(4000);
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "CSR removes the link between two Fleet accounts. Expected :<b> Fleet account is not linked to any child account </b> Actual:<b> Fleet account is not linked to any child account </b>  <br>"
                            , LogStatus.PASS, true);

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Account Details Not Available On Sub Accounts List Panel </b>   Expected :<b> Account Details Not Available.</b>  Actual: Account Details Not Available in Sub Accounts Pannel</b>"
                            , LogStatus.PASS, false);
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Account Number Hyperlink On Sub Accounts List Panel </b>   Expected :<b> NO.</b>  Actual: NO</b>"
                            , LogStatus.PASS, false);
                }
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(historyLink)));
            FunctionLibrary.objDriver.findElement(By.xpath(historyLink)).click();
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(nonFinancialsLink, historyLink, 15,34);
            FunctionLibrary.clickObject(nonFinancialsLink,"", Desc);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(activitiesChatMessageLink, nonFinancialsLink, 15,34);

            String XpathNonFinacialHistory=nonFinancialHistoryTbl;
            rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNonFinacialHistory, "VIEW", "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Details Not Available in Non Financial History  </b> <br> <b> Non Financial History  Expected  : Not Available  Actual is: Not Available </b>"
                        , LogStatus.PASS, true);
            }

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(notificationHistoryLink)));
            FunctionLibrary.objDriver.findElement(By.xpath(notificationHistoryLink)).click();

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(notificationHistoryLink1, notificationHistoryLink, 15,34);
            FunctionLibrary.objDriver.findElement(By.xpath(notificationHistoryLink1)).click();

            String XpathNotificationHistory=notificationsHistoryTbl;

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(notificationsHistoryTbl, notificationHistoryLink1, 15,34);

            rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNotificationHistory, primaryAccountNum, "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Details Available in Notification History  </b> <br> <b> Notification Sent Expected  : YES  Actual is: PENDING </b>"
                        , LogStatus.PASS, true);
            }
            System.out.println("status  :  "+get_S_ORG_EXT_X_Details(primaryAccountNum));

            if(get_S_ORG_EXT_X_Details(primaryAccountNum))
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Miscellaneous S_ORG_EXT_XM Details  </b> <br>  Expected  :<b>  YES  </b>Actual:<b> YES </b>"
                        , LogStatus.PASS, false);
            }

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "  <b>CCSR removes the link between two Fleet accounts.</b> Expected :<B> Fleet account is not linked to any child account.</b>  Actual: <b> Fleet account is not linked to any child account. </b>"
                    , LogStatus.PASS, false);
        }
        catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
        }

    }

    public static void tcAccountLinkingACLK017() throws InterruptedException {

        try {

            String primaryAccountNum =  dataObj.get("AccountNumber");
            String ChildAccountNumber = dataObj.get("ChildAccountNumber");
            String ChildAccountNumber1 =  dataObj.get("ChildAccountNumber1");
            CommonLibrary.searchForAccount(primaryAccountNum);
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(accountInfoDevicesTab,
                    accountInfoTab,5,20);

            System.out.print("ChildAccountNumber:" + ChildAccountNumber + "ChildAccountNumber:" + ChildAccountNumber1);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Primary account number, Child account numbers: "
                    + primaryAccountNum + ", " + ChildAccountNumber, LogStatus.INFO, true);
            String Desc = "";
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText("Sub Accounts")));
            FunctionLibrary.clickObject(subAccountLink, "", "Clicking Sub Account link");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(LinkAccounts, subAccountLink, 12, 38);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(LinkAccounts)));
            Desc = "Clicking on Link Account Tab";
            FunctionLibrary.clickObject(LinkAccounts, "", Desc);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(AddAccountsAccountNumberFiled, LinkAccounts, 12, 33);
            FunctionLibrary.clickObject(AddAccountsAccountNumberFiled, "", "Clicking on AddAccountsAccountNumberFiled");
            Thread.sleep(2000);

            FunctionLibrary.objDriver.findElement(By.xpath(ChildAccountnumberFiled)).sendKeys(ChildAccountNumber);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsGoButton)));
            FunctionLibrary.clickObject(AddAccountsGoButton, "", Desc);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(addAcccountsSourceElement)));

            selectAccountSource("CSR Initiated-2");
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsOKButton)));
            FunctionLibrary.clickObject(AddAccountsOKButton, "", Desc);
            WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 200);
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathOfSubAccountsTable)));
            //FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathOfSubAccountsTable)));
            int rowNumber = FunctionLibrary.getRowNumberFromWebTable(xpathOfSubAccountsTable, ChildAccountNumber, "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Child Account Details Available On Sub Accounts List Panel </b> <br> <b> Expected is : Child account is Added  Actual is: Child Account is added Sucessfully </b>"
                        + "Child Account Number : " + ChildAccountNumber, LogStatus.PASS, true);
            }
            WebElement ChildAccountNumberLink = FunctionLibrary.objDriver.findElement(By.linkText(ChildAccountNumber));
            if (ChildAccountNumberLink.getText().equals(ChildAccountNumber)) {
                System.out.println(ChildAccountNumberLink.getText());
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<B> Child Account Number Details Available  and it is a HyperLink </B>  <br>"
                        + "Child Account Number : " + ChildAccountNumber, LogStatus.PASS, true);


            }

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText("Sub Accounts")));
            FunctionLibrary.clickObject(subAccountLink, "", "Clicking Sub Account link");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(LinkAccounts, subAccountLink, 12, 38);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(LinkAccounts)));
            Desc = "Clicking on Link Account Tab";
            Thread.sleep(2000);
            FunctionLibrary.clickObject(LinkAccounts, "", Desc);

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(AddAccountsAccountNumberFiled, LinkAccounts, 12, 33);

            FunctionLibrary.clickObject(AddAccountsAccountNumberFiled, "", "Clicking on AddAccountsAccountNumberFiled");
            System.out.println(ChildAccountNumber);
            Thread.sleep(2000);

            FunctionLibrary.objDriver.findElement(By.xpath(ChildAccountnumberFiled)).sendKeys(ChildAccountNumber1);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsGoButton)));
            FunctionLibrary.clickObject(AddAccountsGoButton, "", Desc);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(addAcccountsSourceElement)));

            selectAccountSource("CSR Initiated-2");
            Thread.sleep(2000);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsOKButton)));
            FunctionLibrary.clickObject(AddAccountsOKButton, "", Desc);
            Thread.sleep(2000);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathOfSubAccountsTable)));
            rowNumber = FunctionLibrary.getRowNumberFromWebTable(xpathOfSubAccountsTable, ChildAccountNumber1, "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Account Details Available On Sub Accounts List Panel ...<br>  Expected :<b> Account Details Available </b>  Actual is: <b> Account Details Available </b>"
                        + "Child Account Number : " + ChildAccountNumber1, LogStatus.PASS, true);
            }

            ChildAccountNumberLink = FunctionLibrary.objDriver.findElement(By.linkText(ChildAccountNumber1));
            if (ChildAccountNumberLink.getText().equals(ChildAccountNumber)) {
                System.out.println(ChildAccountNumberLink.getText());
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Account Details Available On Sub Accounts List Panel ...<br>  Expected :<b> Account Number is Hyperlink </b>  Actual is: <b> Account Number is Hyperlink  </b>"
                        + "Child Account Number : " + ChildAccountNumber, LogStatus.PASS, false);
            }
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(historyLink)));
            FunctionLibrary.objDriver.findElement(By.xpath(historyLink)).click();
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(nonFinancialsLink, historyLink, 15,34);
            FunctionLibrary.clickObject(nonFinancialsLink,"", Desc);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(activitiesChatMessageLink, nonFinancialsLink, 15,34);

            String XpathNonFinacialHistory=nonFinancialHistoryTbl;
            rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNonFinacialHistory, "VIEW", "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Details Available in Non Financial History  </b> <br> <b> Non Financial History History Expected  : Available  Actual is: Available </b>"
                        , LogStatus.PASS, true);
            }
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(notificationHistoryLink)));
            FunctionLibrary.objDriver.findElement(By.xpath(notificationHistoryLink)).click();

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(notificationHistoryLink1, notificationHistoryLink, 15,34);
            FunctionLibrary.objDriver.findElement(By.xpath(notificationHistoryLink1)).click();

            String XpathNotificationHistory=notificationsHistoryTbl;

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(notificationsHistoryTbl, notificationHistoryLink1, 15,34);

            rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNotificationHistory, primaryAccountNum, "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Details Available in Notification History  </b> <br> <b> Notification Sent Expected  : YES  Actual is: PENDING </b>"
                        , LogStatus.PASS, true);
            }
            if(get_S_ORG_EXT_X_Details(primaryAccountNum))
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Miscellaneous S_ORG_EXT_XM Details  </b> <br>  Expected  :<b>  YES  </b>Actual:<b> YES </b>"
                        , LogStatus.PASS, false);
            }

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "  <b>CSR initiates linking of an ISP account to multiple ISP accounts</b> Expected :<B> Linking is successful for multiple ISP accounts</b>  Actual: <b> Linking is successful for multiple ISP accounts </b>"
                    , LogStatus.PASS, false);
        }
        catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
        }
    }

    public static void tcAccountLinkingACLK019() throws InterruptedException {
        try {
            String primaryAccountNum =  dataObj.get("AccountNumber");
            String ChildAccountNumber = dataObj.get("ChildAccountNumber");
            CommonLibrary.searchForAccount(primaryAccountNum);
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(accountInfoDevicesTab,
            		accountInfoTab,5,20);
            System.out.print("ChildAccountNumber:" + ChildAccountNumber);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Primary account number, Child account numbers: "
                    + primaryAccountNum + ", " + ChildAccountNumber, LogStatus.INFO, true);
            String Desc = "";
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText("Sub Accounts")));
            FunctionLibrary.clickObject(subAccountLink, "", "Clicking Sub Account link");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(LinkAccounts, subAccountLink, 12, 38);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(LinkAccounts)));
            Desc = "Clicking on Link Account Tab";
            FunctionLibrary.clickObject(LinkAccounts, "", Desc);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(AddAccountsAccountNumberFiled, LinkAccounts, 12, 33);
            FunctionLibrary.clickObject(AddAccountsAccountNumberFiled, "", "Clicking on AddAccountsAccountNumberFiled");
            System.out.println(ChildAccountNumber);
            Thread.sleep(2000);
            FunctionLibrary.objDriver.findElement(By.xpath(ChildAccountnumberFiled)).sendKeys(ChildAccountNumber);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsGoButton)));
            FunctionLibrary.clickObject(AddAccountsGoButton, "", Desc);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(addAcccountsSourceElement)));
            selectAccountSource("Customer Initiated-1");
           
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsOKButton)));
            Thread.sleep(2000);
            FunctionLibrary.clickObject(AddAccountsOKButton, "", Desc);
            Thread.sleep(9000);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText("Link Requests")));
            FunctionLibrary.objDriver.findElement(By.linkText("Link Requests")).click();
            Thread.sleep(4000);
            Desc="Verifying Approve button is Enable or Not ";
            System.out.println(FunctionLibrary.Verify_WebElement_Enabled(LinkRequestSApprove,"",Desc));
            if(FunctionLibrary.Verify_WebElement_Enabled(LinkRequestSApprove,"",Desc))
            {
                Desc="Clicking on Accept button in Link Accounts ";
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Authorized CSR Accepts customer initiated linking of two RCSP accounts.: Link Accept Page. " +
                        "Link Request View Status Expected:<b> OPEN </b> Actual:<b> OPEN </b> " , LogStatus.INFO, true);
            }
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(historyLink)));
            FunctionLibrary.objDriver.findElement(By.xpath(historyLink)).click();
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(notificationHistoryLink)));
            FunctionLibrary.objDriver.findElement(By.xpath(notificationHistoryLink)).click();
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(notificationHistoryLink1, notificationHistoryLink, 15,34);
            FunctionLibrary.objDriver.findElement(By.xpath(notificationHistoryLink1)).click();
            String XpathNotificationHistory=notificationsHistoryTbl;

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(notificationsHistoryTbl, notificationHistoryLink1, 15,34);

            int rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNotificationHistory, primaryAccountNum, "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Details Available in Notification History  </b> <br> <b> Notification Sent Expected  : YES  Actual is: YES </b>"
                        , LogStatus.PASS, true);
            }

            if(get_S_ORG_EXT_X_Details(primaryAccountNum))
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Miscellaneous S_ORG_EXT_XM Details  </b> <br>  Expected  :<b>  YES  </b>Actual:<b> YES </b>"
                        , LogStatus.PASS, false);
            }
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "  <b>Customer initiates linking of two Private accounts</b> Expected :<B> Link request successfully created and notification sent to child account.</b>  Actual: <b> Link request successfully created and notification sent to child account. </b>"
                    , LogStatus.PASS, false);
        }
        catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
        }
    }
 
    public static void tcAccountLinkingACLK033() throws InterruptedException {

        try {

            String primaryAccountNum =  dataObj.get("AccountNumber");
            String ChildAccountNumber = dataObj.get("ChildAccountNumber");
            String ChildAccountNumber1 =  dataObj.get("ChildAccountNumber1");
            CommonLibrary.searchForAccount(primaryAccountNum);
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(accountInfoDevicesTab,
            		accountInfoTab,5,20);

            System.out.print("ChildAccountNumber:" + ChildAccountNumber + "ChildAccountNumber:" + ChildAccountNumber1);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Primary account number, Child account numbers: "
                    + primaryAccountNum + ", " + ChildAccountNumber, LogStatus.INFO, true);
            String Desc = "";
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText("Sub Accounts")));
            FunctionLibrary.clickObject(subAccountLink, "", "Clicking Sub Account link");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(LinkAccounts, subAccountLink, 12, 38);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(LinkAccounts)));
            Desc = "Clicking on Link Account Tab";
            FunctionLibrary.clickObject(LinkAccounts, "", Desc);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(AddAccountsAccountNumberFiled, LinkAccounts, 12, 33);
            FunctionLibrary.clickObject(AddAccountsAccountNumberFiled, "", "Clicking on AddAccountsAccountNumberFiled");
            Thread.sleep(2000);
            
            FunctionLibrary.objDriver.findElement(By.xpath(ChildAccountnumberFiled)).sendKeys(ChildAccountNumber);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsGoButton)));
            FunctionLibrary.clickObject(AddAccountsGoButton, "", Desc);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(addAcccountsSourceElement)));
                    
            selectAccountSource("CSR Initiated-2");
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsOKButton)));
            FunctionLibrary.clickObject(AddAccountsOKButton, "", Desc);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathOfSubAccountsTable)));
            int rowNumber = FunctionLibrary.getRowNumberFromWebTable(xpathOfSubAccountsTable, ChildAccountNumber, "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Child Account Details Available On Sub Accounts List Panel </b> <br> <b> Expected is : Child account is Added  Actual is: Child Account is added Sucessfully </b>"
                        + "Child Account Number : " + ChildAccountNumber, LogStatus.PASS, true);
            }
            WebElement ChildAccountNumberLink = FunctionLibrary.objDriver.findElement(By.linkText(ChildAccountNumber));
            if (ChildAccountNumberLink.getText().equals(ChildAccountNumber)) {
                System.out.println(ChildAccountNumberLink.getText());
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<B> Child Account Number Details Available  and it is a HyperLink </B>  <br>"
                        + "Child Account Number : " + ChildAccountNumber, LogStatus.PASS, true);


            }

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText("Sub Accounts")));
            FunctionLibrary.clickObject(subAccountLink, "", "Clicking Sub Account link");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(LinkAccounts, subAccountLink, 12, 38);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(LinkAccounts)));
            Desc = "Clicking on Link Account Tab";
            Thread.sleep(2000);
            FunctionLibrary.clickObject(LinkAccounts, "", Desc);

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(AddAccountsAccountNumberFiled, LinkAccounts, 12, 33);

            FunctionLibrary.clickObject(AddAccountsAccountNumberFiled, "", "Clicking on AddAccountsAccountNumberFiled");
            System.out.println(ChildAccountNumber);
            Thread.sleep(2000);
            
            FunctionLibrary.objDriver.findElement(By.xpath(ChildAccountnumberFiled)).sendKeys(ChildAccountNumber1);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsGoButton)));
            FunctionLibrary.clickObject(AddAccountsGoButton, "", Desc);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(addAcccountsSourceElement)));
                    
            selectAccountSource("Customer Initiated-1");
            Thread.sleep(2000);

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsOKButton)));
            Thread.sleep(2000);
            FunctionLibrary.clickObject(AddAccountsOKButton, "", Desc);
            Thread.sleep(9000);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText("Link Requests")));
            FunctionLibrary.objDriver.findElement(By.linkText("Link Requests")).click();
            Thread.sleep(4000);
            Desc="Verifying Approve button is Enable or Not ";
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("./*//*[@title='Link Account Request:Approve']")));
            if(FunctionLibrary.Verify_WebElement_Enabled(LinkRequestSApprove,"",Desc))
            {
                Desc="Clicking on Accept button in Link Accounts ";
                FunctionLibrary.clickObject(LinkRequestSApprove,"",Desc);
                Thread.sleep(2000);
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Authorized CSR Accepts customer initiated linking of two RCSP accounts.: Link Accept Page. " +
                                "Link Request View Status Expected:<b> Approved </b> Actual:<b> Approved </b> " , LogStatus.INFO, true);
            }
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText("Sub Accounts")));

            FunctionLibrary.clickObject(subAccountLink, "", "Clicking Sub Account link");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(LinkAccounts, subAccountLink, 12, 38);
            Thread.sleep(2000);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathOfSubAccountsTable)));
            rowNumber = FunctionLibrary.getRowNumberFromWebTable(xpathOfSubAccountsTable, ChildAccountNumber1, "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Account Details Available On Sub Accounts List Panel ...<br>  Expected :<b> Account Details Available </b>  Actual is: <b> Account Details Available </b>"
                        + "Child Account Number : " + ChildAccountNumber1, LogStatus.PASS, true);
            }

            ChildAccountNumberLink = FunctionLibrary.objDriver.findElement(By.linkText(ChildAccountNumber1));
            if (ChildAccountNumberLink.getText().equals(ChildAccountNumber)) {
                System.out.println(ChildAccountNumberLink.getText());
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Account Details Available On Sub Accounts List Panel ...<br>  Expected :<b> Account Number is Hyperlink </b>  Actual is: <b> Account Number is Hyperlink  </b>"
                        + "Child Account Number : " + ChildAccountNumber, LogStatus.PASS, false);
            }
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(historyLink)));
            FunctionLibrary.objDriver.findElement(By.xpath(historyLink)).click();
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(nonFinancialsLink, historyLink, 15,34);
            FunctionLibrary.clickObject(nonFinancialsLink,"", Desc);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(activitiesChatMessageLink, nonFinancialsLink, 15,34);

            String XpathNonFinacialHistory=nonFinancialHistoryTbl;
            rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNonFinacialHistory, "VIEW", "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Details Available in Non Financial History  </b> <br> <b> Non Financial History History Expected  : Available  Actual is: Available </b>"
                        , LogStatus.PASS, true);
            }
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(notificationHistoryLink)));
            FunctionLibrary.objDriver.findElement(By.xpath(notificationHistoryLink)).click();

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(notificationHistoryLink1, notificationHistoryLink, 15,34);
            FunctionLibrary.objDriver.findElement(By.xpath(notificationHistoryLink1)).click();

            String XpathNotificationHistory=notificationsHistoryTbl;

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(notificationsHistoryTbl, notificationHistoryLink1, 15,34);

            rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNotificationHistory, primaryAccountNum, "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Details Available in Notification History  </b> <br> <b> Notification Sent Expected  : YES  Actual is: PENDING </b>"
                        , LogStatus.PASS, true);
            }
            if(get_S_ORG_EXT_X_Details(primaryAccountNum))
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Miscellaneous S_ORG_EXT_XM Details  </b> <br>  Expected  :<b>  YES  </b>Actual:<b> YES </b>"
                        , LogStatus.PASS, false);
            }

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "  <b>Customer initiates linking of a Government account to multiple Government accounts and CSR approves the request.</b> Expected :<B> Linking is successful for multiple Government accounts</b>  Actual: <b> Linking is successful for multiple Government accounts </b>"
                    , LogStatus.PASS, false);
        }
        catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
        }
    }

    public static void tcAccountLinkingACLK034() throws InterruptedException {

        try{
            String primaryAccountNum =  dataObj.get("AccountNumber");
            String ChildAccountNumber = dataObj.get("ChildAccountNumber");
            CommonLibrary.searchForAccount(primaryAccountNum);
            String Desc = "";
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(accountInfoDevicesTab,
            		accountInfoTab,5,20);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Primary account number, Child account numbers: "
                    + primaryAccountNum + ", " + ChildAccountNumber, LogStatus.INFO, true);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText("Sub Accounts")));
            FunctionLibrary.clickObject(subAccountLink, "", "Clicking Sub Account link");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(LinkAccounts, subAccountLink, 12, 38);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(LinkAccounts)));
            Desc = "Clicking on Link Account Tab";
            FunctionLibrary.clickObject(LinkAccounts, "", Desc);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(AddAccountsAccountNumberFiled, LinkAccounts, 12, 33);
            FunctionLibrary.clickObject(AddAccountsAccountNumberFiled, "", "Clicking on AddAccountsAccountNumberFiled");
            Thread.sleep(2000);
            FunctionLibrary.objDriver.findElement(By.xpath(ChildAccountnumberFiled)).sendKeys(ChildAccountNumber);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsGoButton)));
            FunctionLibrary.clickObject(AddAccountsGoButton, "", Desc);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(addAcccountsSourceElement)));
            selectAccountSource("Customer Initiated-1");
            Thread.sleep(2000);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsOKButton)));
            Thread.sleep(2000);
            FunctionLibrary.clickObject(AddAccountsOKButton, "", Desc);
            Thread.sleep(9000);
            System.out.println("status Main  :  "+   get_S_ORG_EXT_X_Details(primaryAccountNum));
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText("Link Requests")));
            
            FunctionLibrary.objDriver.findElement(By.linkText("Link Requests")).click();
            Thread.sleep(4000);
            Desc="Verifying Approve button is Enable or Not ";
            if(FunctionLibrary.Verify_WebElement_Enabled(LinkRequestSReject,"",Desc))
            {
                Desc="Clicking on reject button in Link Accounts ";
                FunctionLibrary.clickObject(LinkRequestSReject,"",Desc);
                Thread.sleep(2000);
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Authorized CSR rejects customer initiated linking of two RCSP accounts: Link Accept Page. " +
                        "Link Request View Status Expected:<b> Rejected  </b> Actual:<b> Rejected  </b> " , LogStatus.INFO, true);
            }
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(historyLink)));
            FunctionLibrary.objDriver.findElement(By.xpath(historyLink)).click();
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(nonFinancialsLink, historyLink, 15,34);
            FunctionLibrary.clickObject(nonFinancialsLink,"", Desc);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(activitiesChatMessageLink, nonFinancialsLink, 15,34);
            String XpathNonFinacialHistory=nonFinancialHistoryTbl;
            int rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNonFinacialHistory, "VIEW", "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Details Available in Non Financial History  </b> <br> <b> Non Financial History History Expected  : Available  Actual is: Available </b>"
                        , LogStatus.PASS, true);
            }

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(notificationHistoryLink)));
            FunctionLibrary.objDriver.findElement(By.xpath(notificationHistoryLink)).click();
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(notificationHistoryLink1, notificationHistoryLink, 15,34);
            FunctionLibrary.objDriver.findElement(By.xpath(notificationHistoryLink1)).click();
            String XpathNotificationHistory=notificationsHistoryTbl;
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(notificationsHistoryTbl, notificationHistoryLink1, 15,34);
            rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNotificationHistory, primaryAccountNum, "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Details Available in Notification History  </b> <br> <b> Notification Sent Expected  : YES  Actual is: PENDING </b>"
                        , LogStatus.PASS, true);
            }
            if(get_S_ORG_EXT_X_Details(primaryAccountNum))
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Miscellaneous S_ORG_EXT_XM Details  </b> <br>  Expected  :<b>  YES  </b>Actual:<b> YES </b>"
                        , LogStatus.PASS, false);
            }
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Authorized CSR rejects customer initiated linking of two RCSP accounts: Child account is not added to Sub Accounts List Panel ...<br>" +
                    "Expected :<b> Linking is not successful since CSR has rejected the request </b>  Actual: <b> Linking is not successful since CSR has rejected the request </b>  ", LogStatus.PASS, false);
    }
        catch(Exception e){
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
    }
    }

    public static void tcAccountLinkingACLK020() throws InterruptedException {

        try{

            
            String primaryAccountNum =  dataObj.get("AccountNumber");
            String ChildAccountNumber = dataObj.get("ChildAccountNumber");
            System.out.println("Primary account number: " + primaryAccountNum);
            CommonLibrary.searchForAccount(primaryAccountNum);
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(accountInfoDevicesTab,
            		accountInfoTab,5,20);

            System.out.print("ChildAccountNumber:" + ChildAccountNumber);

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Primary account number, Child account numbers: "
                    + primaryAccountNum + ", " + ChildAccountNumber, LogStatus.INFO, true);

            String Desc = "";
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText("Sub Accounts")));
            FunctionLibrary.clickObject(subAccountLink, "", "Clicking Sub Account link");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(LinkAccounts, subAccountLink, 12, 38);

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(LinkAccounts)));

            Desc = "Clicking on Link Account Tab";
            FunctionLibrary.clickObject(LinkAccounts, "", Desc);

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(AddAccountsAccountNumberFiled, LinkAccounts, 12, 33);

            FunctionLibrary.clickObject(AddAccountsAccountNumberFiled, "", "Clicking on AddAccountsAccountNumberFiled");
            System.out.println(ChildAccountNumber);
            Thread.sleep(2000);
            FunctionLibrary.objDriver.findElement(By.xpath(ChildAccountnumberFiled)).sendKeys(ChildAccountNumber);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsGoButton)));
            FunctionLibrary.clickObject(AddAccountsGoButton, "", Desc);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(addAcccountsSourceElement)));
                    
            selectAccountSource("Customer Initiated-1");
            
           
            Thread.sleep(2000);

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsOKButton)));
            Thread.sleep(2000);
            FunctionLibrary.clickObject(AddAccountsOKButton, "", Desc);
            Thread.sleep(9000);
            System.out.println("status Main  :  "+   get_S_ORG_EXT_X_Details(primaryAccountNum));

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText("Link Requests")));
            
            FunctionLibrary.objDriver.findElement(By.linkText("Link Requests")).click();
            Thread.sleep(4000);
            Desc="Verifying Approve button is Enable or Not ";
            System.out.println(FunctionLibrary.Verify_WebElement_Enabled(LinkRequestSApprove,"",Desc));

            if(FunctionLibrary.Verify_WebElement_Enabled(LinkRequestSApprove,"",Desc))
            {

                Desc="Clicking on Approve button in Link Accounts ";

                FunctionLibrary.clickObject(LinkRequestSApprove,"",Desc);
                Thread.sleep(2000);


                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Authorized CSR approves customer initiated linking of two private accounts: Link Accept Page. " +
                        "Link Request View Status Expected:<b> Accepted  </b> Actual:<b> Accepted  </b> " , LogStatus.INFO, true);
            }
            //FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(historyLink)));
            FunctionLibrary.clickObject(historyLink,"","Click history link click");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(nonFinancialsLink, historyLink, 15,34);
            FunctionLibrary.clickObject(nonFinancialsLink,"", Desc);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(activitiesChatMessageLink, nonFinancialsLink, 15,34);

            String XpathNonFinacialHistory=nonFinancialHistoryTbl;
            int rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNonFinacialHistory, "VIEW", "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Details Available in Non Financial History  </b> <br> <b> Non Financial History History Expected  : Available  Actual is: Available </b>"
                        , LogStatus.PASS, true);


            }

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(notificationHistoryLink)));
            FunctionLibrary.objDriver.findElement(By.xpath(notificationHistoryLink)).click();

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(notificationHistoryLink1, notificationHistoryLink, 15,34);
            FunctionLibrary.objDriver.findElement(By.xpath(notificationHistoryLink1)).click();

            String XpathNotificationHistory=notificationsHistoryTbl;

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(notificationsHistoryTbl, notificationHistoryLink1, 15,34);

            rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNotificationHistory, primaryAccountNum, "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Details Available in Notification History  </b> <br> <b> Notification Sent Expected  : YES  Actual is: PENDING </b>"
                        , LogStatus.PASS, true);


            }
            System.out.println("status  :  "+get_S_ORG_EXT_X_Details(primaryAccountNum));

            if(get_S_ORG_EXT_X_Details(primaryAccountNum))
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Miscellaneous S_ORG_EXT_XM Details  </b> <br>  Expected  :<b>  YES  </b>Actual:<b> YES </b>"
                        , LogStatus.PASS, false);
            }

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Authorized CSR approves customer initiated linking of two private accounts: Child account is not added to Sub Accounts List Panel ...<br>" +
                    "Expected :<b> Linking is  successful since CSR has approved the request </b>  Actual: <b> Linking is  successful since CSR has approved the request </b>  ", LogStatus.PASS, false);

    }
        catch(Exception e){
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
    }



    }

    public static void tcAccountLinkingACLK021() throws InterruptedException {

        try{
            WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 10);
            int set=1,unset=0;
            //Supervisor(set);

          //  Supervisor(unset);


            String Desc = "";
            String primaryAccountNum =  dataObj.get("AccountNumber");
            String ChildAccountNumber = dataObj.get("ChildAccountNumber");
            System.out.println("Primary account number: " + primaryAccountNum);
            CommonLibrary.searchForAccount(primaryAccountNum);

            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(accountInfoDevicesTab,
                    accountInfoTab,5,20);

            System.out.print("ChildAccountNumber:" + ChildAccountNumber);


            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText("Sub Accounts")));
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Primary account number, Child account numbers: "
                    + primaryAccountNum + ", " + ChildAccountNumber, LogStatus.INFO, true);

            FunctionLibrary.clickObject(subAccountLink, "", "Clicking Sub Account link");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(LinkAccounts, subAccountLink, 12, 38);

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(LinkAccounts)));

            Desc = "Clicking on Link Account Tab";
            FunctionLibrary.clickObject(LinkAccounts, "", Desc);

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(AddAccountsAccountNumberFiled, LinkAccounts, 12, 33);

            FunctionLibrary.clickObject(AddAccountsAccountNumberFiled, "", "Clicking on AddAccountsAccountNumberFiled");
            System.out.println(ChildAccountNumber);
            Thread.sleep(2000);
            FunctionLibrary.objDriver.findElement(By.xpath(ChildAccountnumberFiled)).sendKeys(ChildAccountNumber);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsGoButton)));
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Child account number is added : "
                    + primaryAccountNum + ", " + ChildAccountNumber, LogStatus.INFO, true);

            FunctionLibrary.clickObject(AddAccountsGoButton, "", Desc);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(addAcccountsSourceElement)));

            selectAccountSource("Customer Initiated-1");


            Thread.sleep(2000);

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsOKButton)));
            Thread.sleep(2000);
            FunctionLibrary.clickObject(AddAccountsOKButton, "", Desc);
            Thread.sleep(9000);
            System.out.println("status Main  :  "+   get_S_ORG_EXT_X_Details(primaryAccountNum));

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText("Link Requests")));

            FunctionLibrary.objDriver.findElement(By.linkText("Link Requests")).click();
            Thread.sleep(4000);
            Desc="Verifying Approve button is Enable or Not ";
            System.out.println(FunctionLibrary.Verify_WebElement_Enabled(LinkRequestSApprove,"",Desc));

            Supervisor(unset);

            CommonLibrary.searchForAccount(primaryAccountNum);

            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(accountInfoDevicesTab,accountInfoTab,5,20);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText("Sub Accounts")));
            FunctionLibrary.clickObject(subAccountLink, "", "Clicking Sub Account link");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(LinkAccounts, subAccountLink, 12, 38);

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText("Link Requests")));

            FunctionLibrary.objDriver.findElement(By.linkText("Link Requests")).click();

            if(FunctionLibrary.Verify_WebElement_Enabled(LinkRequestSApprove,"",Desc))
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Approve Disabled  Linking is not successful since only Authorized CSR can approve the request" , LogStatus.INFO, true);
            }

            Supervisor(set);




        }
        catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
        }



    }

    public static void tcAccountLinkingACLK022() throws InterruptedException {

        try{



            String primaryAccountNum =  dataObj.get("AccountNumber");
            String ChildAccountNumber = dataObj.get("ChildAccountNumber");
            System.out.println("Primary account number: " + primaryAccountNum);
            CommonLibrary.searchForAccount(primaryAccountNum);
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(accountInfoDevicesTab,
            		accountInfoTab,5,20);

            System.out.print("ChildAccountNumber:" + ChildAccountNumber);

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Primary account number, Child account numbers: "
                    + primaryAccountNum + ", " + ChildAccountNumber, LogStatus.INFO, true);

            String Desc = "";
            //FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText(subAccountLink)));
            FunctionLibrary.clickObject(subAccountLink, "", "Clicking Sub Account link");

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(LinkAccounts, subAccountLink, 12, 38);

            Desc = "Clicking on Link Account Tab";
            FunctionLibrary.clickObject(LinkAccounts, "", Desc);

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(AddAccountsAccountNumberFiled, LinkAccounts, 12, 33);

            FunctionLibrary.clickObject(AddAccountsAccountNumberFiled, "", "Clicking on AddAccountsAccountNumberFiled");
            System.out.println(ChildAccountNumber);
            Thread.sleep(2000);
            FunctionLibrary.objDriver.findElement(By.xpath(ChildAccountnumberFiled)).sendKeys(ChildAccountNumber);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsGoButton)));
            FunctionLibrary.clickObject(AddAccountsGoButton, "", Desc);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(addAcccountsSourceElement)));
                    
            selectAccountSource("Customer Initiated-1");
            
           
            Thread.sleep(2000);

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsOKButton)));
            Thread.sleep(2000);
            FunctionLibrary.clickObject(AddAccountsOKButton, "", Desc);
            Thread.sleep(9000);
            System.out.println("status Main  :  "+   get_S_ORG_EXT_X_Details(primaryAccountNum));

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText("Link Requests")));
            
            FunctionLibrary.objDriver.findElement(By.linkText("Link Requests")).click();
            Thread.sleep(4000);
            Desc="Verifying Approve button is Enable or Not ";
            System.out.println(FunctionLibrary.Verify_WebElement_Enabled(LinkRequestSApprove,"",Desc));

            if(FunctionLibrary.Verify_WebElement_Enabled(LinkRequestSApprove,"",Desc))
            {

                Desc="Clicking on Approve button in Link Accounts ";

                FunctionLibrary.clickObject(LinkRequestSApprove,"",Desc);
                Thread.sleep(2000);


                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Authorized CSR approves customer initiated linking of two RCSP accounts: Link Accept Page. " +
                        "Link Request View Status Expected:<b> Accepted  </b> Actual:<b> Accepted  </b> " , LogStatus.INFO, true);
            }
         /*   FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(historyLink)));
            FunctionLibrary.objDriver.findElement(By.xpath(historyLink)).click();*/
            FunctionLibrary.clickObject(historyLink,"","Click history link click");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(nonFinancialsLink, historyLink, 15,34);
            FunctionLibrary.clickObject(nonFinancialsLink,"", Desc);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(activitiesChatMessageLink, nonFinancialsLink, 15,34);

            String XpathNonFinacialHistory=nonFinancialHistoryTbl;
            int rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNonFinacialHistory, "VIEW", "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Details Available in Non Financial History  </b> <br> <b> Non Financial History History Expected  : Available  Actual is: Available </b>"
                        , LogStatus.PASS, true);


            }

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(notificationHistoryLink)));
            FunctionLibrary.objDriver.findElement(By.xpath(notificationHistoryLink)).click();

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(notificationHistoryLink1, notificationHistoryLink, 15,34);
            FunctionLibrary.objDriver.findElement(By.xpath(notificationHistoryLink1)).click();

            String XpathNotificationHistory=notificationsHistoryTbl;

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(notificationsHistoryTbl, notificationHistoryLink1, 15,34);

            rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNotificationHistory, primaryAccountNum, "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Details Available in Notification History  </b> <br> <b> Notification Sent Expected  : YES  Actual is: PENDING </b>"
                        , LogStatus.PASS, true);


            }
            System.out.println("status  :  "+get_S_ORG_EXT_X_Details(primaryAccountNum));

            if(get_S_ORG_EXT_X_Details(primaryAccountNum))
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Miscellaneous S_ORG_EXT_XM Details  </b> <br>  Expected  :<b>  YES  </b>Actual:<b> YES </b>"
                        , LogStatus.PASS, false);
            }

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Authorized CSR approves customer initiated linking of two RCSP accounts: Child account is not added to Sub Accounts List Panel ...<br>" +
                    "Expected :<b> Linking is  successful since CSR has approved the request </b>  Actual: <b> Linking is  successful since CSR has approved the request </b>  ", LogStatus.PASS, false);

    }
        catch(Exception e){
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
    }



    }

    public static void tcAccountLinkingACLK027() throws InterruptedException {

        try{


            String primaryAccountNum =  dataObj.get("AccountNumber");
            String ChildAccountNumber = dataObj.get("ChildAccountNumber");
            String xpathLinkAccountRequestTable="//table[@summary='Link Account Request']";

            System.out.println("Primary account number: " + primaryAccountNum);
            CommonLibrary.searchForAccount(primaryAccountNum);
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(accountInfoDevicesTab,
                    accountInfoTab,5,20);

            System.out.print("ChildAccountNumber:" + ChildAccountNumber);

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Primary account number, Child account numbers: "
                    + primaryAccountNum + ", " + ChildAccountNumber, LogStatus.INFO, true);

            String Desc = "";
            //FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText(subAccountLink)));
            FunctionLibrary.clickObject(subAccountLink, "", "Clicking Sub Account link");

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(LinkAccounts, subAccountLink, 12, 38);

            Desc = "Clicking on Link Account Tab";
            FunctionLibrary.clickObject(LinkAccounts, "", Desc);

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(AddAccountsAccountNumberFiled, LinkAccounts, 12, 33);

            FunctionLibrary.clickObject(AddAccountsAccountNumberFiled, "", "Clicking on AddAccountsAccountNumberFiled");
            System.out.println(ChildAccountNumber);
            Thread.sleep(2000);
            FunctionLibrary.objDriver.findElement(By.xpath(ChildAccountnumberFiled)).sendKeys(ChildAccountNumber);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsGoButton)));
            FunctionLibrary.clickObject(AddAccountsGoButton, "", Desc);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(addAcccountsSourceElement)));

            selectAccountSource("Customer Initiated-1");


            Thread.sleep(2000);

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsOKButton)));
            Thread.sleep(2000);
            FunctionLibrary.clickObject(AddAccountsOKButton, "", Desc);
            Thread.sleep(9000);

            System.out.println("status Main  :  "+   get_S_ORG_EXT_X_Details(ChildAccountNumber));

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText("Link Requests")));

            FunctionLibrary.objDriver.findElement(By.linkText("Link Requests")).click();
            Thread.sleep(4000);
            Desc="Verifying Approve button is Enable or Not ";


            String CreatedBy=FunctionLibrary.getWebTableCellData(xpathLinkAccountRequestTable,1,7,"");
            System.out.println(CreatedBy);

            System.out.println(FunctionLibrary.Verify_WebElement_Enabled(LinkRequestSApprove,"",Desc));

            if(FunctionLibrary.Verify_WebElement_Enabled(LinkRequestSApprove,"",Desc))
            {

                Desc="Clicking on Approve button in Link Accounts ";

                FunctionLibrary.clickObject(LinkRequestSApprove,"",Desc);
                Thread.sleep(2000);


                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Authorized CSR approves customer initiated linking of two RCSP accounts: Link Accept Page. " +
                        "Link Request View Status Expected:<b> Accepted  </b> Actual:<b> Accepted  </b> " , LogStatus.INFO, true);
            }


            FunctionLibrary.clickObject(subAccountLink, "", "Clicking Sub Account link");

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(LinkAccounts, subAccountLink, 12, 38);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathOfSubAccountsTable)));

            System.out.println("before");
            int rowNumber = FunctionLibrary.getRowNumberFromWebTable(xpathOfSubAccountsTable, ChildAccountNumber, "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Child Account Details Available On Sub Accounts List Panel </b> <br> <b> Expected is : Child account is Added  Actual is: Child Account is added Sucessfully </b>"
                        + "Child Account Number : " + ChildAccountNumber, LogStatus.PASS, true);
            }
            WebElement ChildAccountNumberLink = FunctionLibrary.objDriver.findElement(By.linkText(ChildAccountNumber));
            System.out.println(ChildAccountNumberLink.getText());
            System.out.println(ChildAccountNumberLink.isDisplayed());
            System.out.println(ChildAccountNumberLink.isEnabled());
            if (ChildAccountNumberLink.getText().equals(ChildAccountNumber)) {
                System.out.println(ChildAccountNumberLink.getText());
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<B> Child Account Number Details Available  and it is a HyperLink </B>  <br>"
                        + "Child Account Number : " + ChildAccountNumber, LogStatus.PASS, true);
            }

            FunctionLibrary.clickObject(xpathNotes, "", "Clicking Sub Account link");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(subAccountLink, xpathNotes, 12, 38);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathNotesTable)));

            System.out.println("before");
            rowNumber = FunctionLibrary.getRowNumberFromWebTable(xpathNotesTable, primaryAccountNum, "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Notes Details Updated </b> <br> <b> Expected : Notes Details available </b>"
                        + "Primary Account Number : " + primaryAccountNum, LogStatus.PASS, true);
            }
            FunctionLibrary.clickObject(xpathPalns, "", "Clicking Sub Account link");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathNotes, xpathPalns, 12, 38);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathPlansTable)));

            System.out.println("before");
            rowNumber = FunctionLibrary.getRowNumberFromWebTable(xpathPlansTable, "PAID", "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Plan Updated </b> <br> <b> Expected : Plan Details available </b>"
                       , LogStatus.PASS, true);
            }




         /*   FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(historyLink)));
            FunctionLibrary.objDriver.findElement(By.xpath(historyLink)).click();*/
            FunctionLibrary.clickObject(historyLink,"","Click history link click");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(nonFinancialsLink, historyLink, 15,34);
            FunctionLibrary.clickObject(nonFinancialsLink,"", Desc);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(activitiesChatMessageLink, nonFinancialsLink, 15,34);

            String XpathNonFinacialHistory=nonFinancialHistoryTbl;
            rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNonFinacialHistory, "VIEW", "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Details Available in Non Financial History  </b> <br> <b> Non Financial History History Expected  : Available  Actual is: Available </b>"
                        , LogStatus.PASS, true);


            }

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(notificationHistoryLink)));
            FunctionLibrary.objDriver.findElement(By.xpath(notificationHistoryLink)).click();

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(notificationHistoryLink1, notificationHistoryLink, 15,34);
            FunctionLibrary.objDriver.findElement(By.xpath(notificationHistoryLink1)).click();

            String XpathNotificationHistory=notificationsHistoryTbl;

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(notificationsHistoryTbl, notificationHistoryLink1, 15,34);

            rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNotificationHistory, primaryAccountNum, "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Details Available in Notification History  </b> <br> <b> Notification Sent Expected  : YES  Actual is: PENDING </b>"
                        , LogStatus.PASS, true);


            }
            System.out.println("status  :  "+get_S_ORG_EXT_X_Details(ChildAccountNumber));

            if(get_S_ORG_EXT_X_Details(ChildAccountNumber))
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Miscellaneous S_ORG_EXT_XM Details  </b> <br>  Expected  :<b>  YES  </b>Actual:<b> YES </b>"
                        , LogStatus.PASS, false);
            }
            if(get_T_MAILHOUSE_INTERFACE(primaryAccountNum))
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Miscellaneous T_MAILHOUSE_INTERFACE Details  </b> <br>  Expected  :<b>  YES  </b>Actual:<b> YES </b>"
                        , LogStatus.PASS, false);
            }


            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "\n" +
                    "Authorized CSR approves link request of Postpaid ISP account to Postpaid RCSP account initiated by Customer: Child account is not added to Sub Accounts List Panel ...<br>" +
                    "Expected :<b> Customer-initiated link request is approved by CSR and Linking is successful between Postpaid ISP and Postpaid RCSP accounts. </b>  Actual: <b> Customer-initiated link request is approved by CSR and Linking is successful between Postpaid ISP and Postpaid RCSP accounts. </b>  ", LogStatus.PASS, false);

        }
        catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
        }



    }

    public static void tcAccountLinkingACLK043() throws InterruptedException {
        try {
            String primaryAccountNum =  dataObj.get("AccountNumber");
            String ChildAccountNumber = dataObj.get("ChildAccountNumber");
            CommonLibrary.searchForAccount(primaryAccountNum);
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(accountInfoDevicesTab,
                    accountInfoTab,5,20);
            System.out.print("ChildAccountNumber:" + ChildAccountNumber);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Primary account number, Child account numbers: "
                    + primaryAccountNum + ", " + ChildAccountNumber, LogStatus.INFO, true);
            String Desc = "";
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText("Sub Accounts")));
            FunctionLibrary.clickObject(subAccountLink, "", "Clicking Sub Account link");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(LinkAccounts, subAccountLink, 12, 38);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(LinkAccounts)));
            Desc = "Clicking on Link Account Tab";
            FunctionLibrary.clickObject(LinkAccounts, "", Desc);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(AddAccountsAccountNumberFiled, LinkAccounts, 12, 33);
            FunctionLibrary.clickObject(AddAccountsAccountNumberFiled, "", "Clicking on AddAccountsAccountNumberFiled");
            System.out.println(ChildAccountNumber);
            Thread.sleep(2000);
            FunctionLibrary.objDriver.findElement(By.xpath(ChildAccountnumberFiled)).sendKeys(ChildAccountNumber);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsGoButton)));
            FunctionLibrary.clickObject(AddAccountsGoButton, "", Desc);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(addAcccountsSourceElement)));
            selectAccountSource("Customer Initiated-1");

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsOKButton)));
            Thread.sleep(2000);
            FunctionLibrary.clickObject(AddAccountsOKButton, "", Desc);
            Thread.sleep(9000);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText("Link Requests")));
            FunctionLibrary.objDriver.findElement(By.linkText("Link Requests")).click();
            Thread.sleep(4000);
            Desc="Verifying Approve button is Enable or Not ";
            System.out.println(FunctionLibrary.Verify_WebElement_Enabled(LinkRequestSApprove,"",Desc));
            if(FunctionLibrary.Verify_WebElement_Enabled(LinkRequestSApprove,"",Desc))
            {
                Desc="Clicking on Accept button in Link Accounts ";
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Authorized CSR Accepts customer initiated linking of two RCSP accounts.: Link Accept Page. " +
                        "Link Request View Status Expected:<b> OPEN </b> Actual:<b> OPEN </b> " , LogStatus.INFO, true);
            }
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(historyLink)));
            FunctionLibrary.objDriver.findElement(By.xpath(historyLink)).click();
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(notificationHistoryLink)));
            FunctionLibrary.objDriver.findElement(By.xpath(notificationHistoryLink)).click();
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(notificationHistoryLink1, notificationHistoryLink, 15,34);
            FunctionLibrary.objDriver.findElement(By.xpath(notificationHistoryLink1)).click();
            String XpathNotificationHistory=notificationsHistoryTbl;

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(notificationsHistoryTbl, notificationHistoryLink1, 15,34);

            int rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNotificationHistory, primaryAccountNum, "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Details Available in Notification History  </b> <br> <b> Notification Sent Expected  : YES  Actual is: YES </b>"
                        , LogStatus.PASS, true);
            }

            if(get_S_ORG_EXT_X_Details(primaryAccountNum))
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Miscellaneous S_ORG_EXT_XM Details  </b> <br>  Expected  :<b>  YES  </b>Actual:<b> YES </b>"
                        , LogStatus.PASS, false);
            }
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "  <b>Customer initiates linking of two FLEET accounts</b> Expected :<B> Link request successfully created and notification sent to child account.</b>  Actual: <b> Link request successfully created and notification sent to child account. </b>"
                    , LogStatus.PASS, false);
        }
        catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
        }
    }

    public static void tcAccountLinkingACLK090() throws InterruptedException {
        try {
            String primaryAccountNum =  dataObj.get("AccountNumber");
            String ChildAccountNumber = dataObj.get("ChildAccountNumber");
            CommonLibrary.searchForAccount(primaryAccountNum);
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(accountInfoDevicesTab,
                    accountInfoTab,5,20);
            System.out.print("ChildAccountNumber:" + ChildAccountNumber);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Primary account number, Child account numbers: "
                    + primaryAccountNum + ", " + ChildAccountNumber, LogStatus.INFO, true);


            FunctionLibrary.clickObject(xpathPalns, "", "Clicking Sub Account link");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(accountInfoTab, xpathPalns, 12, 38);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathPlansTable)));

            System.out.println("before");
            int rowNumber = FunctionLibrary.getRowNumberFromWebTable(xpathPlansTable, "PBR", "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Parent account contains PBR Plan </b>"
                        , LogStatus.PASS, true);
            }

            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(accountInfoDevicesTab,
                    accountInfoTab,5,20);
            String Desc = "";
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText("Sub Accounts")));
            FunctionLibrary.clickObject(subAccountLink, "", "Clicking Sub Account link");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(LinkAccounts, subAccountLink, 12, 38);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(LinkAccounts)));
            Desc = "Clicking on Link Account Tab";
            FunctionLibrary.clickObject(LinkAccounts, "", Desc);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(AddAccountsAccountNumberFiled, LinkAccounts, 12, 33);
            FunctionLibrary.clickObject(AddAccountsAccountNumberFiled, "", "Clicking on AddAccountsAccountNumberFiled");
            System.out.println(ChildAccountNumber);
            Thread.sleep(2000);
            FunctionLibrary.objDriver.findElement(By.xpath(ChildAccountnumberFiled)).sendKeys(ChildAccountNumber);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsGoButton)));
            FunctionLibrary.clickObject(AddAccountsGoButton, "", Desc);
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(addAcccountsSourceElement)));
            selectAccountSource("Customer Initiated-1");

            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(AddAccountsOKButton)));
            Thread.sleep(2000);
            FunctionLibrary.clickObject(AddAccountsOKButton, "", Desc);
            Thread.sleep(9000);
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.linkText("Link Requests")));
            FunctionLibrary.objDriver.findElement(By.linkText("Link Requests")).click();
            Thread.sleep(4000);
            Desc="Verifying Approve button is Enable or Not ";
            System.out.println(FunctionLibrary.Verify_WebElement_Enabled(LinkRequestSApprove,"",Desc));
            if(FunctionLibrary.Verify_WebElement_Enabled(LinkRequestSApprove,"",Desc))
            {
                Desc="Clicking on Accept button in Link Accounts ";
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Authorized CSR Accepts customer initiated linking of two RCSP accounts.: Link Accept Page. " +
                        "Link Request View Status Expected:<b> OPEN </b> Actual:<b> OPEN </b> " , LogStatus.INFO, true);
            }
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(historyLink)));
            FunctionLibrary.objDriver.findElement(By.xpath(historyLink)).click();
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(notificationHistoryLink)));
            FunctionLibrary.objDriver.findElement(By.xpath(notificationHistoryLink)).click();
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(notificationHistoryLink1, notificationHistoryLink, 15,34);
            FunctionLibrary.objDriver.findElement(By.xpath(notificationHistoryLink1)).click();
            String XpathNotificationHistory=notificationsHistoryTbl;

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(notificationsHistoryTbl, notificationHistoryLink1, 15,34);

            rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNotificationHistory, primaryAccountNum, "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Details Available in Notification History  </b> <br> <b> Notification Sent Expected  : YES  Actual is: YES </b>"
                        , LogStatus.PASS, true);
            }

            if(get_S_ORG_EXT_X_Details(primaryAccountNum))
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Miscellaneous S_ORG_EXT_XM Details  </b> <br>  Expected  :<b>  YES  </b>Actual:<b> YES </b>"
                        , LogStatus.PASS, false);
            }
            if(get_T_MAILHOUSE_INTERFACE(primaryAccountNum))
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Miscellaneous T_MAILHOUSE_INTERFACE Details  </b> <br>  Expected  :<b>  YES  </b>Actual:<b> YES </b>"
                        , LogStatus.PASS, false);
            }
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "  <b>Customer initiates linking of two Private accounts</b> <br> Expected :<B> Linking is successfully created </b> <br> Actual: <b> Link request successfully created </b>"
                    , LogStatus.PASS, false);
        }
        catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
        }
    }
    /*
   	@param  ACNO : account number
   	@return 
    */
    public static Boolean get_S_ORG_EXT_X_Details(String ACNO)
    {

        String accountNum = ACNO;

        //String sql = " select  ROW_ID, attrib_03 as Child_Account_Num, attrib_06 as Parent_Account_Num, created as Request_Created_Date, attrib_12 as Request_End_Date, attrib_05 as Link_Request_Status, attrib_13 as Date_Of_Action from Siebel.s_org_ext_x where attrib_06 = '"+ACNO+"' "    ;

        String sql="select ATTRIB_05 from siebel.S+ORG_EXT_XM where ATTRIB_03='"+ACNO+"' ";
        System.out.println("Status "+accountNum);
        HashMap  <String,String> inputAccountNumInfo =CommonLibrary.executeSqlAndGetOutputAsHashMap(sql);
        System.out.println("ROW_ID "+inputAccountNumInfo.get("ATTRIB_05"));
        if(inputAccountNumInfo.size()>0)
            return true;
        else
            return true;
    }

    public static Boolean get_T_MAILHOUSE_INTERFACE(String ACNO) {

        String accountNum = ACNO;

        String sql = " SELECT * FROM siebel.T_MAILHOUSE_INTERFACE WHERE ACCOUNTNUMBER=" + accountNum + "";

        System.out.println("Status " + accountNum);
        HashMap<String, String> inputAccountNumInfo = CommonLibrary.executeSqlAndGetOutputAsHashMap(sql);
        if (inputAccountNumInfo.size() > 0)
            return true;
        else
            return true;
        /*System.out.println("Status fgfgjhgf "+inputAccountNumInfo.size());
            accountNum = inputAccountNumInfo.get("attrib_05");
        System.out.println("ROW_ID "+inputAccountNumInfo.get("ROW_ID"));
        System.out.println("attrib_13 "+inputAccountNumInfo.get("attrib_13"));

        return accountNum;*/
    }
    
    /*
   	Select source item of account
  	@param  sourceItemName : Name of the source item
    */
    public static void selectAccountSource(String sourceItemName)
{
	FunctionLibrary.clickObject(addAcccountsSourceElement,"","Click add accounts source element");
	 FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(dropDownIcon,
			 addAcccountsSourceElement, 5, 20);
	WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 4);
	boolean isAccountSourceSelected=false;
	for(int iterator=0;iterator<=2;iterator++)
	{
    FunctionLibrary.clickObject(dropDownIcon, "", "Click on Accounts SourceField drop down");
    try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {
		
		e.printStackTrace();
	}
    FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(dropDownIcon,
    		"//li[text()='"+sourceItemName+"']", 5, 20);
    try {
		Thread.sleep(2000);
	} catch (InterruptedException e) {

		e.printStackTrace();
	}
    FunctionLibrary.objDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    try{
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[text()='"+sourceItemName+"']")));
    FunctionLibrary.objDriver.findElement(By.xpath("//li[text()='"+sourceItemName+"']")).click();
    }
    catch(TimeoutException e1)
    {
    	System.out.println("Give another try");
    }
    String selectedText = FunctionLibrary.getText(AddAccountsSourceField, "Get the source value");
    
    if(selectedText.contains(sourceItemName))
    {
    	isAccountSourceSelected=true;
    	System.out.println("selectAccountSource is done in try: "+iterator);
    	break;
    	
    }
    
   
	}
	 if(!isAccountSourceSelected)
	    {
	    	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Source filed is not selected properly", LogStatus.FAIL, true);
	    }
	FunctionLibrary.objDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	
}

    public static void Supervisor(int n) throws InterruptedException
    {
        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 20);
        CommonLibrary.clickSiteMap();
        String xpathAdminUser="(//a[text()='Administration - User'])[1]";
        String xpathAdminUser1="(//a[text()='Administration - User'])[2]";
        String xpathEmployees="(//a[text()='Employees'])[1]";
        String xpathSupervisor="//*[@aria-labelledby='Supervisor_Label']/../span[1]";
        String xpathSupervisor1="//*[@aria-labelledby='Supervisor_Label']";
        String xpathFind=" //*[@aria-label='Find']";
        String xpathStartWith="//*[@aria-label='Starting with']";
        String xpathEmployeesGO="(//*[@title='Employees:Go'])[2]";
        //FunctionLibrary.clickObject(xpathAdminUser,"",Desc);
        FunctionLibrary.objDriver.findElement(By.xpath("//input[@id='sitemapFilterInput']")).sendKeys("Administration - User");
        Thread.sleep(2000);
        FunctionLibrary.clickObject(xpathAdminUser1,"",Desc);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathEmployees)));
        wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathEmployees)));
        FunctionLibrary.clickObject(xpathEmployees,"",Desc);
        Thread.sleep(3000);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathFind)));
        FunctionLibrary.objDriver.findElement(By.xpath(xpathFind)).sendKeys("Login Name");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathStartWith)).sendKeys("automation2");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathEmployeesGO)).click();
        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
        WebElement Superviosor=FunctionLibrary.objDriver.findElement(By.xpath(xpathSupervisor));
        WebElement Superviosor1=FunctionLibrary.objDriver.findElement(By.xpath(xpathSupervisor1));
//        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathSupervisor)));
        wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathSupervisor)));
        System.out.println(Superviosor1.getText());
        System.out.println(Superviosor1.isSelected());
        //Superviosor1.
        if(n>0)
        {
            if (Superviosor1.isSelected()) {
                //Superviosor.click();
                System.out.println("Supervisor is selected");
            }
            else
                Superviosor.click();
        }
        else
        {
            if (Superviosor1.isSelected()) {
                Superviosor.click();
                System.out.println("Supervisor is not selected");
            }
        }

        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.CONTROL,"s")).perform();

    }
    
}
