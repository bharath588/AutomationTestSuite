package features;

import com.relevantcodes.extentreports.LogStatus;
import libraries.CommonLibrary;
import libraries.FunctionLibrary;
import libraries.ReportLibrary;
import libraries.TestCaseLibrary;
import org.joda.time.DateTime;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import static libraries.CommonLibrary.getEachTestCaseData;
import static objectProperties.SblAccountMaintenancePageObject.reversalTab;
import static objectProperties.SblAccountSearchProperties.accountNumberTxtBox;
import static objectProperties.SblCaseManagementProperties.*;
import static objectProperties.SblCreateAccountPageProperties.*;
import static libraries.CommonLibrary.dataObj;
import static objectProperties.WebAccountMaintenancePageProperties.*;
import static objectProperties.WebAccountMaintenancePageProperties.xpathOfSRCopy;

/**
 * Created by C5063105 on 2/3/2017.
 */
public class CaseManagement {

    // public static HashMap<String,String> dataObj =null;

    public static void CaseManagementTest() throws IOException, Exception {
        ExcelSheet exl = new ExcelSheet();

        int noOfRows = exl.totalrows("FLCSS_CASE_CaseManagement","Test_Scenarios");

        HashMap<String, String> eachTestCaseData = new HashMap<String, String>();

        String testCaseName = "";

        for (int i = 1; i <=noOfRows; i++) {

            eachTestCaseData = getEachTestCaseData(exl, "Test_Scenarios", i,"FLCSS_CASE_CaseManagement");
            CommonLibrary.dataObj=eachTestCaseData;
            testCaseName = "tcCaseManagement"+eachTestCaseData.get("TestCaseId").replace("-","");

            if (eachTestCaseData.get("ExecutionStatus").equalsIgnoreCase("Yes"))
            {
                Method testMethod = null;
                try {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>" + eachTestCaseData.get("TestCaseId")
                            + "</b>" + ": Test Case Execution is started....................... <br>"
                            + "Test case description: " + eachTestCaseData.get("TestCaseDesc"), LogStatus.INFO, false);

                    //check for class and method are found for the test case
                    try
                    {
                        Class<?> c = Class.forName("features.CaseManagement");
                        testMethod= c.getMethod(testCaseName);
                        if(eachTestCaseData.get("First LoginUserName").equalsIgnoreCase("")) {
                            CommonLibrary.loginSblWithTestScenarioSpecificUser(eachTestCaseData);
                        }
                        else{
                            CommonLibrary.loginSiebelAppFirstUser(eachTestCaseData);
                        }
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

                            if (FunctionLibrary.objDriver.findElement(By.xpath(okButton)).isDisplayed())
                            {

                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Execution Failed due to popup <br>", LogStatus.FAIL, true);
                                FunctionLibrary.objDriver.findElement(By.xpath(okButton)).click();

                                //Method for Logout and Closing browser
                                ////////logoutSiebelApplicaiton();
                                FunctionLibrary.closeAllActiveBrowser();

                            }
                        }
                        catch(Exception e1){
                            System.out.println("test failed");
                            // CommonLibrary.logoutSiebelApplicaiton();
                            //FunctionLibrary.Close_All_Active_Browser();

                        }


                    }


                }//End of Catch

                dataObj.clear();

            }
        }


    }
    /*
        Automated for Test case:CMGT-056
        Test case desc: Scan Word document and add as an attachment to existing fleet account SR; hold SR
     */
    public static void tcCaseManagementCMGT056() throws Exception {

        try {
            String accountNumber = dataObj.get("AccountNumber");
            String notesText = dataObj.get("Notes");
            String descriptionText = dataObj.get("Description");
            String queueValue = dataObj.get("Queue");
            String subQueueValue = dataObj.get("SubQueue");
            String priority = dataObj.get("SR Priority");
            String srStatus = dataObj.get("Status");
            String subStatus = dataObj.get("SRSubStatus");
            String holdExpDate = dataObj.get("HoldExpDate");
            String holdReason = dataObj.get("HoldReason");
            String desc = "";
            String serviceRequestId = "";
            String path = ReportLibrary.getPath() + "\\testdata\\" + "Test_doc" + ".docx";

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathServiceRequestList
                    ,xpathServiceRequestTab,2,40);

            desc = "Clicking on Service request list";
           // FunctionLibrary.clickObject(xpathServiceRequestList, "", desc);

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathNewServiceRequestButton
                    ,xpathServiceRequestList,10,30);


            WebDriverWait waitforNeW = new WebDriverWait(FunctionLibrary.objDriver, 90);
            waitforNeW.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNewServiceRequestButton)));

            desc = "Selecting All Service Request";
            FunctionLibrary.selectDropDownList(xpathView,"All Service Requests",desc);

            //For All Service Requests and My Service Requests page elements are common and all My Service Requests page elements are already loaded
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//li[text()='AGENCY']",
                    "//input[@aria-label='Queue']//following::span[1]", 10, 30);


            // creating new SR
            serviceRequestId = createSrWithOwner(accountNumber, notesText, priority, descriptionText, queueValue, subQueueValue);

            CommonLibrary.logoutSiebelApplicaiton();

            //login as CSR
            CommonLibrary.loginSiebelAppSecondUser(dataObj);

            desc = "Clicking on service Request tab";
            FunctionLibrary.clickObject(xpathServiceRequestTab, "", desc);

            FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceReuestSearchField)).sendKeys(serviceRequestId);

            desc = "Clicking on Service Request Search Go Button";
            FunctionLibrary.clickObject(xpathServiceRequestSearchGo, "", desc);


            try {
                WebDriverWait wait4 = new WebDriverWait(FunctionLibrary.objDriver, 150);
                wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathResult)));

                if (FunctionLibrary.objDriver.findElement(By.xpath(xpathResult)).isDisplayed()) {

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Service request found with SR#: " + serviceRequestId, LogStatus.INFO, true);


                }
            } catch (Exception e) {

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Couldn't find the Service Request", LogStatus.FAIL, true);
            }


            desc = "Clicking on attachments";
            FunctionLibrary.clickObject(xpathAttachments, "", desc);


            FunctionLibrary.objDriver.findElement(By.xpath(xpathNewAttachFileButton)).sendKeys(path);

            Thread.sleep(3000);
            // Logout from the application

            if (FunctionLibrary.objDriver.findElement(By.xpath(xpathAttachedDoc)).isDisplayed()) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Attached the document", LogStatus.PASS, true);
            } else {

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Document not attached", LogStatus.FAIL, true);
            }

            FunctionLibrary.objDriver.findElement(By.xpath(xpathMoreInfo)).click();


            WebDriverWait wait3 = new WebDriverWait(FunctionLibrary.objDriver, 400);
            wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathAccountNumber)));
            try {
                if (FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestShowMore)).isDisplayed()) {

                    FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestShowMore)).click();

                }
            }catch (Exception e){
                System.out.println("Show more is not displayed");
            }


            wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathSRStatus)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSRStatus)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSRStatus)).sendKeys(srStatus);

            FunctionLibrary.objDriver.findElement(By.xpath(xpathSRSubStatus)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSRSubStatus)).sendKeys(subStatus);
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();

            wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathHoldReason)));

            FunctionLibrary.objDriver.findElement(By.xpath(xpathHoldReason)).sendKeys(holdReason);

            wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathServiceRequestHoldExpDate)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestHoldExpDate)).sendKeys(holdExpDate);


            desc = "Saving the service request";
            FunctionLibrary.objDriver.findElement(By.xpath("//button[contains(@aria-label,'Service Requests:Save')]/span[text()='Save']")).click();

            wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathHoldReason)));

            //verifying status and sub status
            int ServiceRequestRowNumber = 1;
            FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable,ServiceRequestRowNumber, 6, "On-Hold",
                    "Verifying SR Status",true);

            String actualSubstatus = FunctionLibrary.objDriver.findElement(By.xpath(xpathSRSubStatus)).getAttribute("value");

            if(subStatus.equalsIgnoreCase(actualSubstatus)){

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying sub status, Expected value: " + subStatus +
                        " Actual value: "+ actualSubstatus, LogStatus.PASS, false);

            }

            else{

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying sub status, Expected value: " + subStatus +
                        " Actual value: "+ actualSubstatus, LogStatus.FAIL, false);
            }

            try
            {
                FunctionLibrary.selectDropDownList("//select[@aria-label='Third Level View Bar']","Service Request History","");
            }
            catch(Exception e)
            {

                FunctionLibrary.clickObject(xpathServiceRequestHistoryTab, "", "");
            }

            WebDriverWait waitForHistoryTable = new WebDriverWait(FunctionLibrary.objDriver, 120);
// wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathServiceRequestId)));
            waitForHistoryTable.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathServiceRequestHistoryTable)));


            int vectorSerivicerequestHistoryCount = FunctionLibrary.getWebTableRowCount(vectorServiceRequestHistory, "", "Service Request List");
            System.out.println("vectorSerivicerequestHistoryCount:++++++++" + vectorSerivicerequestHistoryCount);

            if (vectorSerivicerequestHistoryCount != 0) {
                //FunctionLibrary.scrollDown("","scroll down");
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Serivice requests History", LogStatus.PASS, true);
            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Cloned Serivice are not available", LogStatus.FAIL, true);
            }


        }
        catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown error occurred, error message: " + e.getMessage(), LogStatus.FAIL, true);
        }

    }
    /*
        Automated for Test case:CMGT-011
        Test case desc: SR creation for walk-in customers; scan MSDOCs and add as an attachment; add notes
     */
    public static void tcCaseManagementCMGT011() throws Exception {

        try {

            String accountNumber = dataObj.get("AccountNumber");
            String notesText = dataObj.get("Notes");
            String descriptionText = dataObj.get("Description");
            String queueValue = dataObj.get("Queue");
            String subQueueValue = dataObj.get("SubQueue");
            String priority = dataObj.get("SR Priority");
            String creator = dataObj.get("First LoginUserName");
            String srStatus = dataObj.get("Status");
            String subStatus = dataObj.get("SRSubStatus");
            String owner = dataObj.get("Owner");
            String desc = "";
            String serviceRequestId = "";
            String path = ReportLibrary.getPath() + "\\testdata\\" + "Test_doc" + ".docx";


            desc = "Clicking on service Request tab";

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathServiceRequestList
                    ,xpathServiceRequestTab,2,30);

            desc = "Clicking on Service request list";
            FunctionLibrary.clickObject(xpathServiceRequestList, "", desc);
            WebDriverWait waitforNeW = new WebDriverWait(FunctionLibrary.objDriver, 90);
            waitforNeW.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNewServiceRequestButton)));

            serviceRequestId = createSr(accountNumber, notesText, priority, descriptionText, queueValue, subQueueValue);



            waitforNeW.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathAttachments)));

            desc = "Clicking on attachments";
          //  FunctionLibrary.clickObject(xpathAttachments, "", desc);

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//input[@title='Attachments:New File']"
                    ,xpathAttachments,5,20);

            Thread.sleep(5000);

            FunctionLibrary.objDriver.findElement(By.xpath("//input[@title='Attachments:New File']")).sendKeys(path);

            Thread.sleep(3000);
            // Logout from the application

            if (FunctionLibrary.objDriver.findElement(By.xpath(xpathAttachedDoc)).isDisplayed()) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Attached the document", LogStatus.PASS, true);
            } else {

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Document not attached", LogStatus.FAIL, true);
            }

            CommonLibrary.logoutSiebelApplicaiton();

            //login as supervisor
            CommonLibrary.loginSiebelAppSecondUser(dataObj);

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathServiceReuestSearchField
                    ,xpathServiceRequestTab,10,30);

           // desc = "Clicking on service Request tab";
            //FunctionLibrary.clickObject(xpathServiceRequestTab, "", desc);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    "//li[text()='PAYMENT']",xpathServiceReuestSearchField,10,20);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceReuestSearchField)).sendKeys(serviceRequestId);

            desc = "Clicking on Service Request Search Go Button";
            FunctionLibrary.clickObject(xpathServiceRequestSearchGo, "", desc);

            try {
                WebDriverWait wait4 = new WebDriverWait(FunctionLibrary.objDriver, 150);
                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                        "//li[text()='PAYMENT']",xpathResult,2,2);
                wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathResult)));

                if (FunctionLibrary.objDriver.findElement(By.xpath(xpathResult)).isDisplayed()) {

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Service request found with SR#: " + serviceRequestId, LogStatus.PASS, true);

                    int rowNumberWithSR = 1;

                    DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
                    Date date = new Date();
                    String expectedDate=dateFormat.format(date);
                    String dateOpened = FunctionLibrary.getWebTableCellData(xpathServiceRequestTable,rowNumberWithSR,3,"get date");
                    String[] dateOpenedSplit=dateOpened.split(" ");

                    if(expectedDate.equalsIgnoreCase(dateOpenedSplit[0])) {
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying Date Opened <br> Expected  : " + expectedDate + "  Actual: " + dateOpenedSplit[0] , LogStatus.PASS, true);
                    }

                    else{

                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying Date Opened <br> Expected  :  " + expectedDate + "  Actual: " + dateOpenedSplit[0] , LogStatus.FAIL, true);
                    }

                    FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 2, serviceRequestId,
                            "Verifying SR#",false);
                    FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 4, queueValue,
                            "Verifying Queue value",false);
                    FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 5, subQueueValue,
                            "Verifying Sub Queue Value",false);
                    FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 12, priority,
                            "Verifying SR priority",false);
                    FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 6, srStatus,
                            "Verifying SR status",false);
                    FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 14, creator,
                            "Verifying SR creator",false);
                    FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 25, owner,
                            "Verifying SR owner field is blank or not",false);

                    String actualSubstatus = FunctionLibrary.objDriver.findElement(By.xpath(xpathSRSubStatus)).getAttribute("value");

                    if(subStatus.equalsIgnoreCase(actualSubstatus)){

                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying sub status, Expected value: " + subStatus +
                                " Actual value: "+ actualSubstatus, LogStatus.PASS, false);

                    }

                    else{

                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying sub status, Expected value: " + subStatus +
                                " Actual value: "+ actualSubstatus, LogStatus.FAIL, false);
                    }


                }
            } catch (Exception e) {

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Couldn't find the Service Request", LogStatus.FAIL, true);
            }


            Thread.sleep(3000);


            try
            {
                FunctionLibrary.selectDropDownList("//select[@aria-label='Third Level View Bar']","Notes","");
            }
            catch(Exception e)
            {
                desc = "Clicking on Notes tab";
             //   FunctionLibrary.clickObject(xpathNotesTab, "", desc);
                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathNotesNewButton
                        ,xpathNotesTab,5,10);

            }


            WebDriverWait wait6 = new WebDriverWait(FunctionLibrary.objDriver, 90);
            wait6.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNotesNewButton)));


            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathNotesFocus,xpathNotesNewButton,5,10);

         //   FunctionLibrary.objDriver.findElement(By.xpath(xpathNotesNewButton)).click();

            //wait6.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathNotesCount)));

            desc = "Clicking on Notes Focus";
            FunctionLibrary.objDriver.findElement(By.xpath(xpathNotesFocus)).click();

            wait6.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNotesText)));

            FunctionLibrary.objDriver.findElement(By.xpath(xpathNotesText)).click();

            FunctionLibrary.objDriver.findElement(By.xpath(xpathNotesText)).sendKeys(notesText);

            desc="Clicking on Notes save button";
            FunctionLibrary.clickObject(xpathNotesSaveButton,"",desc);

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Added notes", LogStatus.INFO, true);

            Thread.sleep(4000);


            try
            {
                FunctionLibrary.selectDropDownList("//select[@aria-label='Third Level View Bar']","Service Request History","");
            }
            catch(Exception e)
            {
                desc = "Clicking on Service Request Tab";
                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathServiceRequestHistoryGoButton
                        ,xpathServiceRequestHistoryTab,5,10);
            }

            wait6.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathServiceRequestHistoryGoButton)));

            boolean dbVerification = get_S_SRV_REQ_Details(serviceRequestId);

            System.out.println(dbVerification);

            if(dbVerification){

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verified Service request in DB", LogStatus.PASS, false);
            }
            else{

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verification of Service request in DB failed", LogStatus.FAIL, false);
            }



            int ServiceRequestHistoryRowNumber = 1;
            FunctionLibrary.verifyWebTableCellData(xpathServiceRequestHistoryTable,ServiceRequestHistoryRowNumber, 22, serviceRequestId,
                    "Verifying SR# in Service Request History Table",true);



            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathAttachedDoc
                    ,xpathAttachments,20,40);

            try {

                WebDriverWait wait5 = new WebDriverWait(FunctionLibrary.objDriver, 150);
                wait5.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathAttachedDoc)));

                if (FunctionLibrary.objDriver.findElement(By.xpath(xpathAttachedDoc)).isDisplayed()) {

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Attached Document is present", LogStatus.PASS, true);
                }

            } catch (Exception e) {

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Attached Document is not present", LogStatus.FAIL, true);
            }

        }
        catch (Exception e) {


            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown error occurred, error message: " + e.getMessage(), LogStatus.FAIL, true);
        }

    }

    /*
        Automated for Test case:CMGT-044
        Test case desc: SR Search with Queue,Priority and Owner
     */
    public static void tcCaseManagementCMGT044() throws Exception {

        try {
            String Owner = dataObj.get("Owner");
            String Queue = dataObj.get("Queue");
            String priority = dataObj.get("SR Priority");
            String RequestDroddownVal=dataObj.get("REQUESTSDROPDOWNVALUE");
            RequestDroddownVal="'"+RequestDroddownVal+"'";
            String desc = "";

            //FunctionLibrary.webdrvwaitByVisiblityofElementClickableByXpath(FunctionLibrary.objDriver, 150, xpathServiceRequestTab);

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathServiceRequestList
                    ,xpathServiceRequestTab,2,30);

            desc = "Clicking on Service request list";
            FunctionLibrary.clickObject(xpathServiceRequestList, "", desc);
            WebDriverWait waitforNeW = new WebDriverWait(FunctionLibrary.objDriver, 90);
            waitforNeW.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNewServiceRequestButton)));
            desc = "Selecting All Service Request";
            FunctionLibrary.selectDropDownList(xpathView,"All Service Requests",desc);

            //For All Service Requests and My Service Requests page elements are common and all My Service Requests page elements are already loaded
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//li[text()='AGENCY']",
                    "//input[@aria-label='Queue']//following::span[1]", 10, 30);


            // FunctionLibrary.selectDropDownListByValue(requestDropdown, RequestDroddownVal, desc);


            desc = "Clicking on Query button";
            FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceReqQueryField)).click();


            desc = "Enter Owner Name";
            FunctionLibrary.objDriver.findElement(By.xpath(ownerFieldSrch)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(ownerFieldSrch)).sendKeys(Owner);

            desc="Enter Queue";
            FunctionLibrary.objDriver.findElement(By.xpath(xpathQueue)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathQueue)).sendKeys(Queue);

            desc="Enter Priority";
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSRPriority)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSRPriority)).sendKeys(priority);

            desc="Click on Go Button";
            FunctionLibrary.clickObject(xpathServiceRequestSecondGoBtn, "", desc);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//li[text()='AGENCY']",
                    xpathAccountNumber, 10, 30);
            int size = FunctionLibrary.objDriver.findElements(By.xpath(xpathserviceReqListTable+"/tbody/tr")).size();
            System.out.println("size:" + size);
            if (size > 1){
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "service request search Result with Owner Value: " + Owner +"," +"Queue value:" +Queue+","+"Priority Value :"+priority, LogStatus.PASS, true);
            }
            else
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "service request search Result not available with Owner Value: " + Owner +"," +"Queue value:" +Queue+","+"Priority Value :"+priority, LogStatus.FAIL, true);
            }
        }
        catch (Exception e) {
            String Errormsg=e.getMessage();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Service Request search having issue with Error Message :" +Errormsg, LogStatus.FAIL, true);
        }

    }//End of CMGT-044 Test

    /*
        Automated for Test case:CMGT-127
        Test case desc: SR creation CSR for private account with walk-in customers
     */
    public static void tcCaseManagementCMGT127() throws Exception {

        try {
            //Getting Account # from DB
            //String accountNumber =CommonLibrary.executeSqlAndGetOutputAsHashMap(sql).get("ACCOUNT_NO");
            String accountNumber = dataObj.get("AccountNumber");
            String notesText = dataObj.get("Notes");
            String descriptionText = dataObj.get("Description");
            String queueValue = dataObj.get("Queue");
            String subQueueValue = dataObj.get("SubQueue");
            String creator = dataObj.get("First LoginUserName");
            String srStatus = dataObj.get("Status");
            String subStatus = dataObj.get("SRSubStatus");
            String owner = dataObj.get("Owner");
            String priority = dataObj.get("SR Priority");
            String desc = "";
            String serviceRequestId = "";
            String status = "";
            // Thread.sleep(20000);

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathServiceRequestList
                    ,xpathServiceRequestTab,2,40);

            desc = "Clicking on Service request list";
            FunctionLibrary.clickObject(xpathServiceRequestList, "", desc);
            WebDriverWait waitforNew = new WebDriverWait(FunctionLibrary.objDriver, 90);
            waitforNew.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNewServiceRequestButton)));

            /*FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//li[text()='AGENCY']",
                    "//input[@aria-label='Queue']//following::span[1]", 15, 120);*/
            desc = "Clicking on new SR button";
            FunctionLibrary.clickObject(xpathNewServiceRequestButton, "", desc);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//li[text()='AGENCY']",
                    "//input[@aria-label='Queue']//following::span[1]", 15, 120);


            System.out.println(serviceRequestId);


            WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 150);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAccountNumber)).sendKeys(accountNumber);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSRPriority)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSRPriority)).sendKeys(priority);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathNotes)).sendKeys(notesText);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathDescription)).sendKeys(descriptionText);
            status = FunctionLibrary.objDriver.findElement(By.xpath(xpathSRStatus)).getAttribute("value");
            System.out.println(status);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathQueue)).sendKeys(queueValue);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSubQueue)).sendKeys(subQueueValue);

            String firstName = FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestFirstName)).getAttribute("value");
            String lastName = FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestLastName)).getAttribute("value");
            String email =FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestEmail)).getAttribute("value");
            String accountName =FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestAccountName)).getAttribute("value");
            String cellularPhone =FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestCellularPhone)).getAttribute("value");
            serviceRequestId = FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestId)).getText();

            if(!firstName.equalsIgnoreCase("")&&!lastName.equalsIgnoreCase("")&&!email.equalsIgnoreCase("")
                    &&!accountName.equalsIgnoreCase("")&&!cellularPhone.equalsIgnoreCase("")){

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Account details populated ", LogStatus.INFO, true);
            }
            else{
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Account details not populated ", LogStatus.FAIL, true);
            }

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Adding the service request with SR#: " + serviceRequestId, LogStatus.INFO, true);


            desc = "Saving the service request";
            //  FunctionLibrary.clickObject(xpathServiceRequestSaveButton, "", desc);
            try {
                FunctionLibrary.objDriver.findElement(By.xpath("//button[contains(@aria-label,'Service Requests:Save')]")).click();
            }
            catch(Exception e)
            {
                FunctionLibrary.clickObject("//button[contains(@aria-label,'Service Requests:Save')]", "", "");
            }


            try{
                FunctionLibrary.waitForObject.until(ExpectedConditions.alertIsPresent());
                Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                alert.accept();
            }catch (Exception e){

                System.out.println(e.getMessage());

            }

            System.out.println(serviceRequestId);
            desc = "Logout Siebel application";
            // Logout from the application
            Thread.sleep(2000);
            CommonLibrary.logoutSiebelApplicaiton();
            //login as supervisor
            CommonLibrary.loginSiebelAppSecondUser(dataObj);

            desc = "Clicking on service Request tab";
            FunctionLibrary.clickObject(xpathServiceRequestTab, "", desc);

            FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceReuestSearchField)).sendKeys(serviceRequestId);

            desc = "Clicking on Service Request Search Go Button";
            FunctionLibrary.clickObject(xpathServiceRequestSearchGo, "", desc);


            try {
                WebDriverWait wait4 = new WebDriverWait(FunctionLibrary.objDriver, 150);
                wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathResult)));

                if (FunctionLibrary.objDriver.findElement(By.xpath(xpathResult)).isDisplayed()) {

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Service request found with SR#: " + serviceRequestId, LogStatus.PASS, true);

                    int rowNumberWithSR = 1;

                    DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
                    Date date = new Date();
                    String expectedDate=dateFormat.format(date);
                    String dateOpened = FunctionLibrary.getWebTableCellData(xpathServiceRequestTable,rowNumberWithSR,3,"get date");
                    String[] dateOpenedSplit=dateOpened.split(" ");

                    if(expectedDate.equalsIgnoreCase(dateOpenedSplit[0])) {
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying Date Opened <br> Expected  : " + expectedDate + "  Actual: " + dateOpenedSplit[0] , LogStatus.PASS, true);
                    }

                    else{

                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying Date Opened <br> Expected  :  " + expectedDate + "  Actual: " + dateOpenedSplit[0] , LogStatus.FAIL, true);
                    }

                    FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 2, serviceRequestId,
                            "Verifying SR#", false);
                    FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 4, queueValue,
                            "Verifying Queue value", false);
                    FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 5, subQueueValue,
                            "Verifying Sub Queue Value", false);
                    FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 12, priority,
                            "Verifying SR priority", false);
                    FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 6, srStatus,
                            "Verifying SR status", false);
                    FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 14, creator,
                            "Verifying SR creator", false);
                    FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 25, owner,
                            "Verifying SR owner field is blank or not", false);

                    String actualSubstatus = FunctionLibrary.objDriver.findElement(By.xpath(xpathSRSubStatus)).getAttribute("value");

                    if (subStatus.equalsIgnoreCase(actualSubstatus)) {

                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying sub status, Expected value: " + subStatus +
                                " Actual value: " + actualSubstatus, LogStatus.PASS, false);


                    } else {

                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying sub status, Expected value: " + subStatus +
                                " Actual value: " + actualSubstatus, LogStatus.FAIL, false);
                    }

                    try
                    {
                        FunctionLibrary.selectDropDownList("//select[@aria-label='Third Level View Bar']","Notes","");
                    }
                    catch(Exception e)
                    {
                        desc = "Clicking on Notes tab";
                        FunctionLibrary.clickObject(xpathNotesTab, "", desc);
                    }

                    WebDriverWait wait6 = new WebDriverWait(FunctionLibrary.objDriver, 90);
                    wait6.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNotesNewButton)));

                    FunctionLibrary.objDriver.findElement(By.xpath(xpathNotesNewButton)).click();

                    wait6.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathNotesCount)));

                    desc = "Clicking on Notes Focus";
                    FunctionLibrary.objDriver.findElement(By.xpath(xpathNotesFocus)).click();

                    wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNotesText)));

                    FunctionLibrary.objDriver.findElement(By.xpath(xpathNotesText)).click();

                    FunctionLibrary.objDriver.findElement(By.xpath(xpathNotesText)).sendKeys(notesText);

                    desc="Clicking on Notes save button";
                    FunctionLibrary.clickObject(xpathNotesSaveButton,"",desc);

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Added notes", LogStatus.INFO, true);

                    boolean dbVerification = get_S_SRV_REQ_Details(serviceRequestId);

                    System.out.println(dbVerification);

                    if(dbVerification){

                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verified Service request in DB", LogStatus.PASS, false);
                    }
                    else{

                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verification of Service request in DB failed", LogStatus.FAIL, false);
                    }

                    /*desc="Clicking on Service Request Tab";
                    FunctionLibrary.clickObject(xpathServiceRequestHistoryTab,"",desc);*/

                    try
                    {
                        FunctionLibrary.selectDropDownList("//select[@aria-label='Third Level View Bar']","Service Request History","");
                    }
                    catch(Exception e)
                    {
                        desc = "Clicking on Service Request Tab";
                        FunctionLibrary.clickObject(xpathServiceRequestHistoryTab, "", desc);
                    }

                    wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathServiceRequestHistoryGoButton)));

                    int ServiceRequestHistoryRowNumber = 1;
                    FunctionLibrary.verifyWebTableCellData(xpathServiceRequestHistoryTable,ServiceRequestHistoryRowNumber, 22, serviceRequestId,
                            "Verifying SR# in Service Request History Table",true);

                    //  CommonLibrary.logoutSiebelApplicaiton();
                }
            } catch (Exception e) {

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Couldn't find the Service Request", LogStatus.FAIL, true);
                //CommonLibrary.logoutSiebelApplicaiton();
            }



        }catch (Exception e) {
            String Errormsg=e.getMessage();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Service Request search having issue with Error Message :" +Errormsg, LogStatus.FAIL, true);
        }

    }//End of CMGT127 - Test

    /*
       Automated for Test case:CMGT-024
       Test case desc: SR # search and all details should be present in case view for that customer
    */
    public static void tcCaseManagementCMGT024() throws Exception {

        try {
            //Getting Account # from DB
            String accountNumber = dataObj.get("AccountNumber");
            // String accountNumber =CommonLibrary.executeSqlAndGetOutputAsHashMap(sql).get("ACCOUNT_NO");
            String notesText = dataObj.get("Notes");
            String descriptionText = dataObj.get("Description");
            String queueValue = dataObj.get("Queue");
            String subQueueValue = dataObj.get("SubQueue");
            String priority = dataObj.get("SR Priority");
            dataObj.get("SR Status");
            String desc = "";
            String serviceRequestId = "";
            String status = "";
            // Thread.sleep(20000);
            desc = "Clicking on service Request tab";
            //  FunctionLibrary.clickObject(xpathServiceRequestTab, "", desc);



            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathServiceRequestList
                    ,xpathServiceRequestTab,2,20);
            desc = "Clicking on Service request list";
            FunctionLibrary.clickObject(xpathServiceRequestList, "", desc);
            desc="Wait for ServiceRequest list page to load";
            WebDriverWait waitforNeW3 = new WebDriverWait(FunctionLibrary.objDriver, 90);
            waitforNeW3.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNewServiceRequestButton)));

            desc = "Clicking on new SR button";
            FunctionLibrary.clickObject(serviceRequestNewBtn, "", desc);

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//li[text()='PAYMENT']",
                    "//input[@aria-label='Queue']//following::span[1]", 15, 120);


            FunctionLibrary.objDriver.findElement(By.xpath(xpathAccountNumber)).sendKeys(accountNumber);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSRPriority)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSRPriority)).sendKeys(priority);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathNotes)).sendKeys(notesText);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathDescription)).sendKeys(descriptionText);

            serviceRequestId = FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestId)).getText();
            System.out.println("serviceRequestId1: "+serviceRequestId);


            status = FunctionLibrary.objDriver.findElement(By.xpath(xpathSRStatus)).getAttribute("value");
            System.out.println(status);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathQueue)).sendKeys(queueValue);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSubQueue)).sendKeys(subQueueValue);


            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Adding the service request with SR#: " + serviceRequestId, LogStatus.INFO, true);


            desc = "Saving the service request";
            //FunctionLibrary.clickObject(xpathServiceRequestSaveButton, "", desc);

            FunctionLibrary.objDriver.findElement(By.xpath("//button[contains(@aria-label,'Service Requests:Save')]/span[text()='Save']")).click();

            WebDriverWait wait5 = new WebDriverWait(FunctionLibrary.objDriver, 90);

            try{
                wait5.until(ExpectedConditions.alertIsPresent());
                Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                alert.accept();
            }catch (Exception e){

                System.out.println(e.getMessage());

            }

            System.out.println(serviceRequestId);
            FunctionLibrary.clickObject(xpathServiceRequestTab, "", desc);

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(accountNumberTxtBox,
                    xpathServiceRequestTab, 15, 120);

            FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceReuestSearchField)).sendKeys(serviceRequestId);

            desc = "Clicking on Service Request Search Go Button";
            FunctionLibrary.clickObject(xpathServiceRequestSearchGo, "", desc);


            try {
                WebDriverWait wait4 = new WebDriverWait(FunctionLibrary.objDriver, 150);
                wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathResult)));

                if (FunctionLibrary.objDriver.findElement(By.xpath(xpathResult)).isDisplayed()) {

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Service request found with SR#: " + serviceRequestId, LogStatus.PASS, true);


                }
            } catch (Exception e) {

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Couldn't find the Service Request", LogStatus.FAIL, true);
            }



        }catch (Exception e) {
            String Errormsg=e.getMessage();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Service Request search having issue with Error Message :" +Errormsg, LogStatus.FAIL, true);
        }

    }//End of CMGT024 - Test
    /*
      Automated for Test case:CMGT-128
      Test case desc: SR creation for non-customer inquiries in walk-in mode
     */
    public static void tcCaseManagementCMGT128() throws Exception {

        try {

            String notesText = dataObj.get("Notes");
            String descriptionText = dataObj.get("Description");
            String queueValue = dataObj.get("Queue");
            String subQueueValue = dataObj.get("SubQueue");
            String priority = dataObj.get("SR Priority");
            String firstName = dataObj.get("First Name");
            String lastName = dataObj.get("Last Name");
            String emailId = dataObj.get("Email Id");
            String creator = dataObj.get("First LoginUserName");
            String srStatus = dataObj.get("Status");
            String subStatus = dataObj.get("SRSubStatus");
            String owner = dataObj.get("Owner");

            String desc = "";
            String serviceRequestId = "";
            String status = "";
            String path = ReportLibrary.getPath() + "\\testdata\\" + "Test_doc" + ".docx";

            desc = "Clicking on service Request tab";
            // FunctionLibrary.clickObject(xpathServiceRequestTab, "", desc);



            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathServiceRequestList
                    ,xpathServiceRequestTab,2,20);

            desc = "Clicking on Service request list";
            FunctionLibrary.clickObject(xpathServiceRequestList, "", desc);

            WebDriverWait wait6 = new WebDriverWait(FunctionLibrary.objDriver, 90);
            wait6.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNewServiceRequestButton)));
            desc = "Clicking on new SR button";
            FunctionLibrary.clickObject(xpathNewServiceRequestButton, "", desc);

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//li[text()='AGENCY']",
                    "//input[@aria-label='Account Name']", 5, 40);

            FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestFirstName)).sendKeys(firstName);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestLastName)).sendKeys(lastName);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestEmail)).sendKeys(emailId);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSRPriority)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSRPriority)).sendKeys(priority);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathNotes)).sendKeys(notesText);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathDescription)).sendKeys(descriptionText);
            status = FunctionLibrary.objDriver.findElement(By.xpath(xpathSRStatus)).getAttribute("value");
            System.out.println(status);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathQueue)).sendKeys(queueValue);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSubQueue)).sendKeys(subQueueValue);
            serviceRequestId = FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestId)).getText();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Adding the service request with SR#: " + serviceRequestId + ". For the non customer"
                    , LogStatus.INFO, true);


            FunctionLibrary.objDriver.findElement(By.xpath("//button[contains(@aria-label,'Service Requests:Save')]/span[text()='Save']")).click();
            //    FunctionLibrary.objDriver.findElement(By.xpath("//button[contains(@aria-label,'Service Requests:Save')]")).click();



            System.out.println(serviceRequestId);
            wait6.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathAttachments)));
            desc = "Clicking on attachments";
            FunctionLibrary.clickObject(xpathAttachments, "", desc);


            FunctionLibrary.objDriver.findElement(By.xpath(xpathNewAttachFileButton)).sendKeys(path);

            Thread.sleep(3000);
            // Logout from the application

            if (FunctionLibrary.objDriver.findElement(By.xpath(xpathAttachedDoc)).isDisplayed()) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Attached the document", LogStatus.PASS, true);
            } else {

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Document not attached", LogStatus.FAIL, true);
            }

            CommonLibrary.logoutSiebelApplicaiton();

            //login as supervisor
            CommonLibrary.loginSiebelAppSecondUser(dataObj);

            desc = "Clicking on service Request tab";
            FunctionLibrary.clickObject(xpathServiceRequestTab, "", desc);

            FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceReuestSearchField)).sendKeys(serviceRequestId);

            desc = "Clicking on Service Request Search Go Button";
            FunctionLibrary.clickObject(xpathServiceRequestSearchGo, "", desc);



            WebDriverWait wait4 = new WebDriverWait(FunctionLibrary.objDriver, 150);
            wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathResult)));

            if (FunctionLibrary.objDriver.findElement(By.xpath(xpathResult)).isDisplayed()) {

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Service request found with SR#: " + serviceRequestId, LogStatus.PASS, true);

                int rowNumberWithSR = 1;

                DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
                Date date = new Date();
                String expectedDate=dateFormat.format(date);
                String dateOpened = FunctionLibrary.getWebTableCellData(xpathServiceRequestTable,rowNumberWithSR,3,"get date");
                String[] dateOpenedSplit=dateOpened.split(" ");

                if(expectedDate.equalsIgnoreCase(dateOpenedSplit[0])) {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying Date Opened <br> Expected  : " + expectedDate + "  Actual: " + dateOpenedSplit[0] , LogStatus.PASS, true);
                }

                else{

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying Date Opened <br> Expected  :  " + expectedDate + "  Actual: " + dateOpenedSplit[0] , LogStatus.FAIL, true);
                }

                FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 2, serviceRequestId,
                        "Verifying SR#",false);
                FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 4, queueValue,
                        "Verifying Queue value",false);
                FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 5, subQueueValue,
                        "Verifying Sub Queue Value",false);
                FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 12, priority,
                        "Verifying SR priority",false);
                FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 6, srStatus,
                        "Verifying SR status", false);
                FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 14, creator,
                        "Verifying SR creator", false);
                FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 25, owner,
                        "Verifying SR owner field is blank or not", false);

                String actualSubstatus = FunctionLibrary.objDriver.findElement(By.xpath(xpathSRSubStatus)).getAttribute("value");

                if (subStatus.equalsIgnoreCase(actualSubstatus)) {

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying sub status, Expected value: " + subStatus +
                            " Actual value: " + actualSubstatus, LogStatus.PASS, false);


                } else {

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying sub status, Expected value: " + subStatus +
                            " Actual value: " + actualSubstatus, LogStatus.FAIL, false);
                }

                try
                {
                    FunctionLibrary.selectDropDownList("//select[@aria-label='Third Level View Bar']","Notes","");
                }
                catch(Exception e)
                {
                    desc = "Clicking on Notes tab";
                    FunctionLibrary.clickObject(xpathNotesTab, "", desc);
                }

                WebDriverWait wait7 = new WebDriverWait(FunctionLibrary.objDriver, 90);
                wait7.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNotesNewButton)));

                FunctionLibrary.objDriver.findElement(By.xpath(xpathNotesNewButton)).click();

                wait6.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathNotesCount)));

                desc = "Clicking on Notes Focus";
                FunctionLibrary.objDriver.findElement(By.xpath(xpathNotesFocus)).click();

                wait6.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNotesText)));

                FunctionLibrary.objDriver.findElement(By.xpath(xpathNotesText)).click();

                FunctionLibrary.objDriver.findElement(By.xpath(xpathNotesText)).sendKeys(notesText);

                desc="Clicking on Notes save button";
                FunctionLibrary.clickObject(xpathNotesSaveButton,"",desc);

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Added notes", LogStatus.INFO, true);

                boolean dbVerification = get_S_SRV_REQ_Details(serviceRequestId);

                System.out.println(dbVerification);

                if(dbVerification){

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verified Service request in DB", LogStatus.PASS, false);
                }
                else{

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verification of Service request in DB failed", LogStatus.FAIL, false);
                }

                /*desc="Clicking on Service Request Tab";
                FunctionLibrary.clickObject(xpathServiceRequestHistoryTab,"",desc);*/

                try
                {
                    FunctionLibrary.selectDropDownList("//select[@aria-label='Third Level View Bar']","Service Request History","");
                }
                catch(Exception e)
                {
                    desc = "Clicking on Service Request Tab";
                    FunctionLibrary.clickObject(xpathServiceRequestHistoryTab, "", desc);
                }

                wait6.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathServiceRequestHistoryGoButton)));

                int ServiceRequestHistoryRowNumber = 1;
                FunctionLibrary.verifyWebTableCellData(xpathServiceRequestHistoryTable,ServiceRequestHistoryRowNumber, 22, serviceRequestId,
                        "Verifying SR# in Service Request History Table",true);

            }


            desc = "Clicking on attachments";
            FunctionLibrary.clickObject(xpathAttachments, "", desc);

            try {

                WebDriverWait wait5 = new WebDriverWait(FunctionLibrary.objDriver, 150);
                wait5.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathAttachedDoc)));

                if (FunctionLibrary.objDriver.findElement(By.xpath(xpathAttachedDoc)).isDisplayed()) {

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Attached Document is present", LogStatus.PASS, true);

                }

            } catch (Exception e) {

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Attached Document is not present", LogStatus.FAIL, true);

            }

        }
        catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown error occurred", LogStatus.FAIL, true);
        }

    }
    /*
      Automated for Test case:CMGT-023
      Test case desc: Login as CSR; open SRs displayed by default
    */
    public static void tcCaseManagementCMGT023() throws Exception {

        try {
            dataObj.get("AccountNumber");
            dataObj.get("Notes");
            dataObj.get("Description");
            dataObj.get("Queue");
            dataObj.get("SubQueue");
            dataObj.get("SR Priority");
            String desc = "";
            ReportLibrary.getPath();

            desc = "Clicking on service Request tab";
            FunctionLibrary.clickObject(xpathServiceRequestTab, "", desc);

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathServiceRequestList
                    ,xpathServiceRequestTab,2,20);

            desc = "Clicking on Service request list";
            FunctionLibrary.clickObject(xpathServiceRequestList, "", desc);

            WebDriverWait waitforNeW = new WebDriverWait(FunctionLibrary.objDriver, 90);
            waitforNeW.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNewServiceRequestButton)));

            String xpathOfServiceRequestList = ".//table[@summary='Service Request List']";

            List<WebElement> rows =FunctionLibrary.objDriver.findElements(By.xpath(".//table[@summary='Service Request List']/tbody/tr"));
            //List<WebElement> rows =FunctionLibrary.objDriver.findElements(By.xpath(".//table[@summary='Service Request List']"));
            System.out.println("Total number of rows :"+ rows.size());
            //
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"verifying all "+(rows.size()-1)+" rows",LogStatus.INFO,true);

            for(int RowCount=0;RowCount<rows.size();RowCount++) {
                if(RowCount!=0) {

                    FunctionLibrary.verifyWebTableCellData(xpathOfServiceRequestList,RowCount,6,"Open","Verifying status",false);


                }
            }
        }
        catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown error occurred", LogStatus.FAIL, true);
        }
    }
    /*
       Automated for Test case:CMGT-099
       Test case desc: Supervisor able to view all customer and non-customer escalated SRs
     */
    public static void tcCaseManagementCMGT099() throws Exception {

        try {

            String accountNumber = dataObj.get("AccountNumber");
            String notesText = dataObj.get("Notes");
            String descriptionText = dataObj.get("Description");
            String queueValue = dataObj.get("Queue");
            String subQueueValue = dataObj.get("SubQueue");
            String priority = dataObj.get("SR Priority");
            String firstName = dataObj.get("First LoginUserName");
            String lastName = dataObj.get("First LoginUserName");
            dataObj.get("Email Id");
            String status=dataObj.get("Status");

            String desc = "";
            String serviceRequestId1 = "";
            String serviceRequestId2 = "";
            FunctionLibrary.clickObject(xpathServiceRequestTab, "", desc);

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathServiceReuestSearchField,
                    xpathServiceRequestTab, 15, 120);
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@aria-label='Status']")).sendKeys("Escalated");

            desc = "Clicking on Service Request Search Go Button";
            FunctionLibrary.clickObject(xpathServiceRequestSearchGo, "", desc);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//li[text()='PAYMENT']",
                    "//input[@aria-label='Account Name']", 15, 60);

            String xpathOfServiceRequestList = ".//table[@summary='Service Request List']";

            List<WebElement> rows =FunctionLibrary.objDriver.findElements(By.xpath(".//table[@summary='Service Request List']/tbody/tr"));

            System.out.println("Total number of rows :"+ rows.size());

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"verifying all "+(rows.size()-1)+" rows",LogStatus.INFO,true);

            for(int RowCount=0;RowCount<rows.size();RowCount++) {
                if(RowCount!=0) {

                    FunctionLibrary.verifyWebTableCellData(xpathOfServiceRequestList,RowCount,6,"Escalated","Verifying status",false);

                }
            }

        }
        catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown error occurred", LogStatus.FAIL, true);
        }


    }//End of CMGT099 - Test

    /*
       Automated for Test case:CMGT-036
       Test case desc: Search SR with queue, sub-queue, and priority for non-customers
     */
    public static void tcCaseManagementCMGT036() throws Exception {

        try {

            String notesText = dataObj.get("Notes");
            String descriptionText = dataObj.get("Description");
            String queueValue = dataObj.get("Queue");
            String subQueueValue = dataObj.get("SubQueue");
            String priority = dataObj.get("SR Priority");
            String firstName = dataObj.get("First Name");
            String lastName = dataObj.get("Last Name");
            String emailId = dataObj.get("Email Id");
            String srStatus = dataObj.get("Status");
            String subStatus = dataObj.get("SRSubStatus");
            String owner = dataObj.get("First LoginUserName");

            String desc = "";
            String serviceRequestId = "";
            String status = "";
            String path = ReportLibrary.getPath() + "\\testdata\\" + "Test_doc" + ".docx";

            desc = "Clicking on service Request tab";
// FunctionLibrary.clickObject(xpathServiceRequestTab, "", desc);



            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathServiceRequestList
                    ,xpathServiceRequestTab,2,20);

            desc = "Clicking on Service request list";
            FunctionLibrary.clickObject(xpathServiceRequestList, "", desc);

            WebDriverWait wait6 = new WebDriverWait(FunctionLibrary.objDriver, 90);
            wait6.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNewServiceRequestButton)));
            desc = "Clicking on new SR button";
            FunctionLibrary.clickObject(xpathNewServiceRequestButton, "", desc);

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//li[text()='AGENCY']",
                    "//input[@aria-label='Account Name']", 5, 40);

            FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestFirstName)).sendKeys(firstName);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestLastName)).sendKeys(lastName);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestEmail)).sendKeys(emailId);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSRPriority)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSRPriority)).sendKeys(priority);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathNotes)).sendKeys(notesText);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathDescription)).sendKeys(descriptionText);
            status = FunctionLibrary.objDriver.findElement(By.xpath(xpathSRStatus)).getAttribute("value");
            System.out.println(status);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathQueue)).sendKeys(queueValue);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSubQueue)).sendKeys(subQueueValue);
            serviceRequestId = FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestId)).getText();



            try {
                FunctionLibrary.objDriver.findElement(By.xpath("//button[contains(@aria-label,'Service Requests:Save')]")).click();
            }
            catch(Exception e)
            {
                FunctionLibrary.clickObject("//button[contains(@aria-label,'Service Requests:Save')]", "", "");
            }


            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathServiceReuestSearchField,
                    xpathServiceRequestTab, 15, 120);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceReuestSearchField)).sendKeys(serviceRequestId);

            desc = "Clicking on Service Request Search Go Button";
            FunctionLibrary.clickObject(xpathServiceRequestSearchGo, "", desc);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//li[text()='PAYMENT']",
                    "//input[@aria-label='Queue']//following::span[1]", 10, 30);


            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(""+xpathOwnerPopupHeaderField,xpathServiceRequestOwnerField,5,50);
            WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 90);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathOwnerPopupHeaderField)));

            FunctionLibrary.objDriver.findElement(By.xpath(xpathOwnerPopupSearchField)).clear();

            FunctionLibrary.setText(""+xpathOwnerPopupSearchField, "User ID", "Enter account number");
            FunctionLibrary.setText(""+xpathOwnerpopupSearchValue, owner, "Enter account number");

            FunctionLibrary.clickObject(xpathOwnerPopupGoButton,"","");
          /*  try {
                FunctionLibrary.clickObject("//button[@title='Pick Service Request Owner:OK']","","Clicking on OK if more then 1 user exists");
            }
            catch (Exception e)
            {

            }*/

            try {
                FunctionLibrary.objDriver.findElement(By.xpath("//button[contains(@aria-label,'Service Requests:Save')]")).click();
            }
            catch(Exception e)
            {
                FunctionLibrary.clickObject("//button[contains(@aria-label,'Service Requests:Save')]", "", "");
            }

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNewServiceRequestButton)));

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Service request created with SR#: " + serviceRequestId + ". For the non customer"
                    , LogStatus.INFO, true);

            //select all services
            desc = "select My services";
            FunctionLibrary.clickObject(xpathView, "", desc);
            FunctionLibrary.clickObject(xpathMyServices, "", desc);
            Thread.sleep(4000);
            desc = "Clicking on Query Button";
            FunctionLibrary.clickObject(xpathOfQuery, "", desc);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//li[text()='AGENCY']",
                    "//input[@aria-label='Queue']//following::span[1]", 15, 120);

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Search SR by Queue " + queueValue + " Sub Queue" + subQueueValue + " and SR Priority" + priority, LogStatus.INFO, true);

            desc = "Set Queue";
            FunctionLibrary.setText(XpathQueryQueue, queueValue, desc);

            desc = "Set Priority";
            FunctionLibrary.setText(XpathQuerySRPriority, priority, desc);

            desc = "Set Sub Queue";
            FunctionLibrary.setText(XpathQuerySubQueue, subQueueValue, desc);

            desc = "Enter SR number";
            // FunctionLibrary.setText(XpathQuerySRNumber, accountNumber, desc);

            desc = "click on Go";
            FunctionLibrary.clickObject(XpathQueryGo, "", desc);
            Thread.sleep(6000);

            String xpathOfServiceRequestList = ".//table[@summary='Service Request List']";

            List<WebElement> rows = FunctionLibrary.objDriver.findElements(By.xpath(".//table[@summary='Service Request List']/tbody/tr"));
            //List<WebElement> rows =FunctionLibrary.objDriver.findElements(By.xpath(".//table[@summary='Service Request List']"));
            System.out.println("Total number of rows :" + rows.size());
            //
            //  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"verifying all "+(rows.size()-1)+" rows",LogStatus.INFO,true);
            boolean srCheck=false;
            if (rows.size() > 1) {
                for (int RowCount = 1; RowCount < rows.size(); RowCount++) {

                    String Account= FunctionLibrary.getWebTableCellData(xpathOfServiceRequestList, RowCount, 19, "");

                    System.out.println("Account:" + Account);


                    if (Account.equals("")) {

                        String SR = FunctionLibrary.getWebTableCellData(xpathOfServiceRequestList, RowCount, 2, "");
                        // FunctionLibrary.verifyWebTableCellData(xpathOfServiceRequestList, RowCount, 2, SR, "Verifying SR for " + "<b>" + "Empty " + "</b>" + "Account Number", false);

                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verified SR " + SR + " for Non customer", LogStatus.PASS, false);
                        srCheck=true;
                    }
                    else if(RowCount==rows.size()-1 && !Account.equals("") && srCheck==false )
                    {
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Sr not Available for Non customer", LogStatus.FAIL, false);

                    }
                }

            }
            else
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Sr not Available", LogStatus.FAIL, true);

            }
        }

        catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown error occurred", LogStatus.FAIL, true);
        }
    }

    /*
      Automated for Test case:CMGT-050
      Test case desc: SR # search without entering Closure code
    */
    public static void tcCaseManagementCMGT050() throws Exception {

        try {
            //Getting Account # from DB
            String accountNumber = dataObj.get("AccountNumber");
            //String accountNumber =CommonLibrary.executeSqlAndGetOutputAsHashMap(sql).get("ACCOUNT_NO");
            String notesText = dataObj.get("Notes");
            String descriptionText = dataObj.get("Description");
            String queueValue = dataObj.get("Queue");
            String subQueueValue = dataObj.get("SubQueue");
            String priority = dataObj.get("SR Priority");
            String desc = "";
            String serviceRequestId = "";
            String status = "";


            desc = "Clicking on service Request tab";
            //FunctionLibrary.clickObject(xpathServiceRequestTab, "", desc);

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathServiceRequestList
                    ,xpathServiceRequestTab,2,20);

            //  FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 15, accountNumberTxtBox);
            desc = "Clicking on Service request list";
            FunctionLibrary.clickObject(xpathServiceRequestList, "", desc);
            desc="Wait for ServiceRequest list page to load";
            WebDriverWait waitforNeW = new WebDriverWait(FunctionLibrary.objDriver, 90);
            waitforNeW.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNewServiceRequestButton)));

            desc = "Clicking on new SR button";
            FunctionLibrary.clickObject(xpathNewServiceRequestButton, "", desc);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//li[text()='PAYMENT']",
                    "//input[@aria-label='Queue']//following::span[1]", 10, 30);


            FunctionLibrary.objDriver.findElement(By.xpath(xpathAccountNumber)).sendKeys(accountNumber);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSRPriority)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSRPriority)).sendKeys(priority);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathNotes)).sendKeys(notesText);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathDescription)).sendKeys(descriptionText);
            status = FunctionLibrary.objDriver.findElement(By.xpath(xpathSRStatus)).getAttribute("value");
            System.out.println(status);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathQueue)).sendKeys(queueValue);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSubQueue)).sendKeys(subQueueValue);
            serviceRequestId = FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestId)).getText();

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Adding the service request with SR#: " + serviceRequestId, LogStatus.INFO, true);
            desc = "Saving the service request";
            FunctionLibrary.objDriver.findElement(By.xpath("//button[contains(@aria-label,'Service Requests:Save')]/span[text()='Save']")).click();

            WebDriverWait wait=new WebDriverWait(FunctionLibrary.objDriver, 150);
            try{
                wait.until(ExpectedConditions.alertIsPresent());
                Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                alert.accept();
            }catch (Exception e){

                System.out.println(e.getMessage());

            }

            System.out.println(serviceRequestId);
            desc = "click on Service Request Home Tab";


            FunctionLibrary.clickObject(xpathServiceRequestTab, "", desc);

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathServiceReuestSearchField,
                    xpathServiceRequestTab, 15, 120);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceReuestSearchField)).sendKeys(serviceRequestId);

            desc = "Clicking on Service Request Search Go Button";
            FunctionLibrary.clickObject(xpathServiceRequestSearchGo, "", desc);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//li[text()='PAYMENT']",
                    "//input[@aria-label='Queue']//following::span[1]", 10, 30);

            try {

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathResult)));

                if (FunctionLibrary.objDriver.findElement(By.xpath(xpathResult)).isDisplayed()) {

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Service request found with SR#: " + serviceRequestId, LogStatus.PASS, true);
                    //Change the status to closed
                    FunctionLibrary.objDriver.findElement(By.xpath(xpathSRStatus)).clear();
                    FunctionLibrary.objDriver.findElement(By.xpath(xpathSRStatus)).sendKeys("Closed");
                    //save the SR without giving Closure code
                    new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.CONTROL, "s")).perform();
                    //pop up should be displayed
                    if(FunctionLibrary.objDriver.findElement(By.xpath(closureCodePopUp)).isDisplayed()){
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:Pop Should be displayed with message <b>Please enter the Closure Code <br> <B>Actual:Pop Up displayed as Expected", LogStatus.PASS, true);

                    }
                    FunctionLibrary.objDriver.findElement(By.xpath(okButton)).click();
                    FunctionLibrary.closeAllActiveBrowser();
                    CommonLibrary.loginSiebelAppSecondUser(dataObj);


                  /*  try {

                        WebDriverWait waitForProcess = new WebDriverWait(FunctionLibrary.objDriver,90);
                        waitForProcess.until(ExpectedConditions.alertIsPresent());
                        Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                        String alerttext=alert.getText();
                        alert.accept();
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:Pop up Should be displayed with message <b>"+alerttext, LogStatus.PASS, true);

                    }
                    catch (Exception e) {
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Unknown error",LogStatus.FAIL,true);
                    }*/
                    FunctionLibrary.closeAllActiveBrowser();
                    CommonLibrary.loginSiebelAppSecondUser(dataObj);
                }
            } catch (Exception e) {

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, e.getMessage(), LogStatus.FAIL, true);
            }



        }catch (Exception e) {
            String Errormsg=e.getMessage();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Service Request search having issue with Error Message :" +Errormsg, LogStatus.FAIL, true);
        }

    }//End of CMGT050 - Test

    /*
      Automated for Test case:CMGT-051
      Test case desc: SR closure and verification of number of open SRs in dashboard; email sent
    */
    public static void tcCaseManagementCMGT051() throws Exception {

        try {
            String notesText = dataObj.get("Notes");
            String descriptionText = dataObj.get("Description");
            String queueValue = dataObj.get("Queue");
            String subQueueValue = dataObj.get("SubQueue");
            String priority = dataObj.get("SR Priority");
            String firstName = dataObj.get("First Name");
            String lastName = dataObj.get("Last Name");
            String emailId = dataObj.get("Email Id");
            String Status = dataObj.get("Status");
            String owner = dataObj.get("First LoginUserName");

            String desc = "";
            String serviceRequestId = "";
            String status = "";
            String path = ReportLibrary.getPath() + "\\testdata\\" + "Test_doc" + ".docx";

            desc = "Clicking on service Request tab";

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathServiceRequestList
                    ,xpathServiceRequestTab,2,20);

            desc = "Clicking on Service request list";
            FunctionLibrary.clickObject(xpathServiceRequestList, "", desc);

            WebDriverWait wait6 = new WebDriverWait(FunctionLibrary.objDriver, 90);
            wait6.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNewServiceRequestButton)));
            desc = "Clicking on new SR button";
            FunctionLibrary.clickObject(xpathNewServiceRequestButton, "", desc);

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//li[text()='AGENCY']",
                    "//input[@aria-label='Account Name']", 5, 20);

            FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestFirstName)).sendKeys(firstName);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestLastName)).sendKeys(lastName);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestEmail)).sendKeys(emailId);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSRPriority)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSRPriority)).sendKeys(priority);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathNotes)).sendKeys(notesText);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathDescription)).sendKeys(descriptionText);
            status = FunctionLibrary.objDriver.findElement(By.xpath(xpathSRStatus)).getAttribute("value");
            System.out.println(status);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathQueue)).sendKeys(queueValue);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSubQueue)).sendKeys(subQueueValue);
            serviceRequestId = FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestId)).getText();



            try {
                FunctionLibrary.objDriver.findElement(By.xpath("//button[contains(@aria-label,'Service Requests:Save')]")).click();
            }
            catch(Exception e)
            {
                FunctionLibrary.clickObject("//button[contains(@aria-label,'Service Requests:Save')]", "", "");
            }


            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathServiceReuestSearchField,
                    xpathServiceRequestTab, 15, 120);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceReuestSearchField)).sendKeys(serviceRequestId);

            desc = "Clicking on Service Request Search Go Button";
            FunctionLibrary.clickObject(xpathServiceRequestSearchGo, "", desc);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//li[text()='PAYMENT']",
                    "//input[@aria-label='Queue']//following::span[1]", 10, 30);


            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(""+xpathOwnerPopupHeaderField,xpathServiceRequestOwnerField,5,50);
            WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 120);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathOwnerPopupHeaderField)));

            FunctionLibrary.objDriver.findElement(By.xpath(xpathOwnerPopupSearchField)).clear();

            FunctionLibrary.setText(""+xpathOwnerPopupSearchField, "User ID", "Enter account number");
            FunctionLibrary.setText(""+xpathOwnerpopupSearchValue, owner, "Enter account number");

            FunctionLibrary.clickObject(xpathOwnerPopupGoButton,"","");
         /*   try {
            	if (FunctionLibrary.objDriver.findElement(By.xpath("//button[@title='Pick Service Request Owner:OK']")).isDisplayed()) {
                    FunctionLibrary.clickObject("//button[@title='Pick Service Request Owner:OK']","","Clicking on OK if more then 1 user exists");
                	}

            }
            catch (Exception e)
            {

            }*/

            try {
                FunctionLibrary.objDriver.findElement(By.xpath("//button[contains(@aria-label,'Service Requests:Save')]")).click();
            }
            catch(Exception e)
            {
                FunctionLibrary.clickObject("//button[contains(@aria-label,'Service Requests:Save')]", "", "");
            }

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNewServiceRequestButton)));

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Service request created with SR#: " + serviceRequestId
                    , LogStatus.INFO, true);

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("(//font)[2]",
                    hometab, 10, 60);

            String sRcountTextBeforeClose=FunctionLibrary.objDriver.findElement(By.xpath("(//font)[2]")).getText();
            String[] sRcountTextSplit=sRcountTextBeforeClose.split(":");
            int srCount=Integer.parseInt(sRcountTextSplit[1].trim());
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verified number of open SRs in dashboard before closing SR:"+srCount, LogStatus.INFO, true);


            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathServiceReuestSearchField,
                    xpathServiceRequestTab, 15, 45);

            FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceReuestSearchField)).sendKeys(serviceRequestId);

            desc = "Clicking on Service Request Search Go Button";
            FunctionLibrary.clickObject(xpathServiceRequestSearchGo, "", desc);


            List<WebElement> rows = FunctionLibrary.objDriver.findElements(By.xpath(".//table[@summary='Service Request List']/tbody/tr"));
            //List<WebElement> rows =FunctionLibrary.objDriver.findElements(By.xpath(".//table[@summary='Service Request List']"));
            System.out.println("Total number of rows :" + rows.size());
            //
            //  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"verifying all "+(rows.size()-1)+" rows",LogStatus.INFO,true);
            if (rows.size() > 1) {
                String serviceRequestId1 = FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestId)).getText();
                System.out.println("serviceRequestId1" + serviceRequestId1);
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(srStatus)));

                FunctionLibrary.objDriver.findElement(By.xpath(srStatus)).clear();
                FunctionLibrary.objDriver.findElement(By.xpath(srStatus)).sendKeys("Closed");
                //     FunctionLibrary.objDriver.findElement(By.xpath(xpathClosureCode)).clear();
                FunctionLibrary.objDriver.findElement(By.xpath(xpathClosureCode)).sendKeys("COMPLETED");

                FunctionLibrary.objDriver.findElement(By.xpath("//button[contains(@aria-label,'Service Requests:Save')]/span[text()='Save']")).click();

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Open status SR closed by entering closure status as COMPLETED ", LogStatus.INFO, true);
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathServiceRequestTab)));
                FunctionLibrary.clickObject(xpathServiceRequestTab, "", "Clicking on SR tab");
                WebDriverWait waitForSearch = new WebDriverWait(FunctionLibrary.objDriver, 90);
                waitForSearch.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathServiceReuestSearchField)));
                FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceReuestSearchField)).sendKeys(serviceRequestId1);


                FunctionLibrary.clickObject(xpathServiceRequestSearchGo, "", "Clicking on Service Request Search Go Button");

                WebDriverWait wait1=new WebDriverWait(FunctionLibrary.objDriver, 150);
                wait1.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathserviceReqListTable)));
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Closed SR" , LogStatus.PASS, true);

                /*FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathServiceRequestHistoryTable,
                        serviceRequestHistory, 10, 40);*/

                try
                {
                    FunctionLibrary.selectDropDownList("//select[@aria-label='Third Level View Bar']","Service Request History","");
                }
                catch(Exception e)
                {

                    FunctionLibrary.clickObject(xpathServiceRequestHistoryTab, "", "");
                }

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathServiceRequestHistoryTable)));

                int vectorSerivicerequestHistoryCount = FunctionLibrary.getWebTableRowCount(vectorServiceRequestHistory, "", "Service Request List");
                System.out.println("vectorSerivicerequestHistoryCount: " + vectorSerivicerequestHistoryCount);


                int rownumberwithServiceRequestone=FunctionLibrary.getRowNumberFromWebTableByTwoText(vectorServiceRequestHistory,serviceRequestId1,"Closed","");
                FunctionLibrary.verifyWebTableCellData(vectorServiceRequestHistory,rownumberwithServiceRequestone , 26, "Closed", "Verifying Service Request status in service request history",true);


                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("(//font)[2]",
                        hometab, 10, 60);

                String sRcountTextAfterClose = FunctionLibrary.objDriver.findElement(By.xpath("(//font)[2]")).getText();
                String[] sRcountTextSplitAfterClose = sRcountTextAfterClose.split(":");
                int srCountAfterclose = Integer.parseInt(sRcountTextSplitAfterClose[1].trim());
                if (srCountAfterclose == srCount - 1) {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verified number of open SRs in dashboard after SR closure:" + srCountAfterclose, LogStatus.PASS, true);

                } else {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown error occured", LogStatus.FAIL, true);
                }
            }
            else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Open SR not available", LogStatus.FAIL, true);
            }

        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown error occurred", LogStatus.FAIL, true);
        }
    }

    /*
      Automated for Test case:CMGT-108
      Test case desc: Supervisor rejects escalated adjustment SR that is created when refund amount is greater than FL adjustment clerk approval limit
    */
    public static void tcCaseManagementCMGT108() throws Exception {
        String desc;
        try {
            CommonLibrary.clickSiteMap();
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@id='sitemapFilterInput']")).sendKeys("internal Controls");

            FunctionLibrary.clickObject(internalControls, "", "Click on internal controls link");

            FunctionLibrary.clickObject(internalControlsQueryBtn,"","Clicking on Query button");


            FunctionLibrary.clickObject(internalControlThresholdResponsibility,"","Clicking on Responsibilty");
            FunctionLibrary.setText(internalControlThresholdResponsibilitytxtbox,dataObj.get("Responsibility"),"Entering Responsibilty");

            FunctionLibrary.clickObject(intenalControlsQueryPaytype,"","Clicking on pay type");
            FunctionLibrary.setText(intenalControlsQueryPaytypetxtbox,dataObj.get("Paytype"),"Entering Pay Type");
            FunctionLibrary.clickObject(internalControlsQueryTransactiontype,"","Clicking on transaction type");
            FunctionLibrary.setText(internalControlsQueryTransactiontypetxtbox,dataObj.get("Transactiontype"),"Entering transaction Type");
            FunctionLibrary.clickObject(internalControlQueryGobtn,"","Clicking on Go button");
            Thread.sleep(5000);

            //  String xpathOfPaymentpaidTable = "./*//*[@summary='Financial Transaction List']";

            int size=FunctionLibrary.objDriver.findElements(By.xpath("//table[@summary='Internal Control Threshold List']/tbody/tr")).size();
            System.out.println("size:"+size);
            String maximumamount="";
            if(size>1) {
                int getrowNumberInternalThresholdTable = FunctionLibrary.getRowNumberFromWebTable(internalControlsThresholdTable, dataObj.get("Paytype"), "get the row number of cash tranaction");

                maximumamount = FunctionLibrary.getWebTableCellData(internalControlsThresholdTable, getrowNumberInternalThresholdTable, 9, "Getting value");
                System.out.println("Approval Amount:" + maximumamount);


                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                        "Internal Threshold settings", LogStatus.INFO, true);
            }
            else {

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                        "Internal Threshold not set", LogStatus.FAIL, true);
            }

            FunctionLibrary.clickObject(accountsTab,"","Clicking on accounts tab");

            CommonLibrary.searchForAccount(dataObj.get("AccountNumber"));

            FunctionLibrary.clickObject(CRMfinancialsTab, "", "Clicking on financials tab");

            WebDriverWait waitforAdjustment = new WebDriverWait(FunctionLibrary.objDriver, 90);
            waitforAdjustment.until(ExpectedConditions.elementToBeClickable(By.xpath(adjustmentTab)));

            FunctionLibrary.clickObject(adjustmentTab,"","clicking on adjustment tab");
            waitforAdjustment.until(ExpectedConditions.elementToBeClickable(By.xpath(adjustmentNewbtn)));
            maximumamount=maximumamount.replace(",","");
            Double unitPriceAmount = Double.valueOf(maximumamount);
            //   Double addedAmount=unitPriceAmount+Double.parseDouble(dataObj.get("GreaterApprovalAmount"));
            Double addedAmount=unitPriceAmount+getValueforSr();
            String adjustedAmountToParse=Double.toString(addedAmount);

            FunctionLibrary.clickObject(adjustmentNewbtn,"","Clicking on new button");
            Thread.sleep(4000);
            // FunctionLibrary.setText(adjustmentCategorytxtbox,"TOLDEPOSIT","Entering category");
            FunctionLibrary.setText(adjustmentSubCategorytxtbox,dataObj.get("Subcategory"),"Entering category");
            FunctionLibrary.setText(adjustmentUnitpricetxtbox,adjustedAmountToParse,"Entering category");
            FunctionLibrary.clickObject(adjustmentSavebtn,"","Clicking on Save");
            Thread.sleep(4000);
            FunctionLibrary.clickObject(adjustmentBtn,"","Clicking on Adjust");
            Thread.sleep(6000);

            int rownumberwithServiceRequestone=FunctionLibrary.getRowNumberFromWebTable(serviceRequestListTable,"ADJUSTMENT","");


            String xpathSRTbl=""+xpathserviceReqListTable;
            String ServiceRequestID = FunctionLibrary.getWebTableCellData(xpathSRTbl,rownumberwithServiceRequestone,2,"get ServiceRequestID");
            String icStatus =FunctionLibrary.objDriver.findElement(By.xpath("//td[@title='ESCALATED']/span[1]/img")).getAttribute("title");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Service request found with SR#: " + ServiceRequestID, LogStatus.PASS, true);

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "IC Status: " + icStatus, LogStatus.PASS, true);


            FunctionLibrary.objDriver.findElement(By.xpath(xpathMoreInfo)).click();

            String owner=FunctionLibrary.objDriver.findElement(By.xpath("//input[@aria-label='Owner']")).getAttribute("value");
            if(owner.equals(""))
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"verifying unassigned owner",LogStatus.PASS,true);
            }
            else
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"verifying Owner:Unassigned",LogStatus.FAIL,true);
            }

            String statusText=FunctionLibrary.objDriver.findElement(By.xpath(srStatus)).getAttribute("value");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"verifying status:"+statusText,LogStatus.PASS,true);

            String substatusText=FunctionLibrary.objDriver.findElement(By.xpath("//input[@aria-label='SR Sub Status']")).getAttribute("value");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"verifying Substatus:"+substatusText,LogStatus.PASS,true);

            CommonLibrary.logoutSiebelApplicaiton();
            Thread.sleep(3000);
            CommonLibrary.loginSiebelAppSecondUser(dataObj);

            FunctionLibrary.webdrvwaitByVisiblityofElementClickableByXpath(FunctionLibrary.objDriver, 20, xpathServiceRequestTab);
            //search for service Request ID
            desc = "Clicking on service Request tab";
            FunctionLibrary.clickObject(xpathServiceRequestTab, "", desc);

            FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceReuestSearchField)).sendKeys(ServiceRequestID);

            desc = "Clicking on Service Request Search Go Button";
            FunctionLibrary.clickObject(xpathServiceRequestSearchGo, "", desc);

            WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 150);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathResult)));


            FunctionLibrary.objDriver.findElement(By.xpath(ownerFieldSrch)).sendKeys(dataObj.get("Second LoginUserName"));
            //  ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Adding the service request with SR#: " + ServiceRequestID, LogStatus.INFO, true);
            desc = "Saving the service request";
            FunctionLibrary.clickObject(xpathServiceRequestSaveButton, "", desc);

            //FunctionLibrary.clickObject(escalationTab, "", "click on Escalation Tab");

            try
            {
                FunctionLibrary.selectDropDownList("//select[@aria-label='Third Level View Bar']","Escalation","");
            }
            catch(Exception e)
            {

                FunctionLibrary.clickObject(escalationTab, "", "");
            }
            Thread.sleep(4000);
            //FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(adjustmentRejectButton, escalationTab, 5, 20);
            FunctionLibrary.clickObject(adjustmentRejectButton, "", "click on Reject");
            //   FunctionLibrary.objDriver.findElement(By.xpath(xpathrejectCode)).sendKeys("REJECT");

            FunctionLibrary.setText(xpathrejectCodeXpath, "REJECT", "Entering Reject code");


            FunctionLibrary.clickObject(rejectCodesaveBtn, "", "click on Save");
            Thread.sleep(8000);

            // FunctionLibrary.webdrvwaitByVisiblityofElementClickableByXpath(FunctionLibrary.objDriver, 20, xpathserviceReqListTable);
            String actual = FunctionLibrary.objDriver.findElement(By.xpath("//td[@title='REJECTED']/span[1]/img")).getAttribute("title");
            System.out.println("actual:" + actual);

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Verifying SR sub-status, Expected :REJECTED <br> Actual:"+actual,LogStatus.PASS,true);

            FunctionLibrary.objDriver.findElement(By.xpath(xpathMoreInfo)).click();

            String ownerField=FunctionLibrary.objDriver.findElement(By.xpath("//input[@aria-label='Owner']")).getAttribute("value");

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"verifying owner:"+ownerField,LogStatus.PASS,true);

            try
            {
                FunctionLibrary.selectDropDownList("//select[@aria-label='Third Level View Bar']","Notes","");
            }
            catch(Exception e)
            {
                desc = "Clicking on Notes tab";
                FunctionLibrary.clickObject(xpathNotesTab, "", desc);
            }

            WebDriverWait wait6 = new WebDriverWait(FunctionLibrary.objDriver, 90);
            wait6.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNotesNewButton)));

            FunctionLibrary.objDriver.findElement(By.xpath(xpathNotesNewButton)).click();

            wait6.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathNotesCount)));

            desc = "Clicking on Notes Focus";
            FunctionLibrary.objDriver.findElement(By.xpath(xpathNotesFocus)).click();

            wait6.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNotesText)));

            FunctionLibrary.objDriver.findElement(By.xpath(xpathNotesText)).click();

            FunctionLibrary.objDriver.findElement(By.xpath(xpathNotesText)).sendKeys(dataObj.get("Notestext"));

            desc="Clicking on Notes save button";
            FunctionLibrary.clickObject(xpathNotesSaveButton,"",desc);

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Added notes", LogStatus.INFO, true);


        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown error occurred:" + e.getMessage(), LogStatus.FAIL, true);
        }
    }

    /*
       Automated for Test case:CMGT-107
       Test case desc: Login as FL ADJUSTMENT CLERK; Close approved SR's
     */
    public static void tcCaseManagementCMGT107() throws Exception {

        String desc;
        String srStatus = dataObj.get("Status");
        String subStatus = dataObj.get("SRSubStatus");
        String closureText = dataObj.get("ClosureText");
        try {

            CommonLibrary.clickSiteMap();

            FunctionLibrary.objDriver.findElement(By.xpath("//input[@id='sitemapFilterInput']")).sendKeys("internal Controls");

            FunctionLibrary.clickObject(internalControls, "", "Click on internal controls link");

            FunctionLibrary.clickObject(internalControlsQueryBtn,"","Clicking on Query button");


            FunctionLibrary.clickObject(internalControlThresholdResponsibility,"","Clicking on Responsibilty");
            FunctionLibrary.setText(internalControlThresholdResponsibilitytxtbox,dataObj.get("Responsibility"),"Entering Responsibilty");

            FunctionLibrary.clickObject(intenalControlsQueryPaytype,"","Clicking on pay type");
            FunctionLibrary.setText(intenalControlsQueryPaytypetxtbox,dataObj.get("Paytype"),"Entering Pay Type");
            FunctionLibrary.clickObject(internalControlsQueryTransactiontype,"","Clicking on transaction type");
            FunctionLibrary.setText(internalControlsQueryTransactiontypetxtbox,dataObj.get("Transactiontype"),"Entering transaction Type");
            FunctionLibrary.clickObject(internalControlQueryGobtn,"","Clicking on Go button");
            Thread.sleep(5000);

            int size=FunctionLibrary.objDriver.findElements(By.xpath("//table[@summary='Internal Control Threshold List']/tbody/tr")).size();
            System.out.println("size:"+size);
            String maximumamount="";
            if(size>1) {
                int getrowNumberInternalThresholdTable = FunctionLibrary.getRowNumberFromWebTable(internalControlsThresholdTable, dataObj.get("Paytype"), "get the row number of cash tranaction");

                maximumamount = FunctionLibrary.getWebTableCellData(internalControlsThresholdTable, getrowNumberInternalThresholdTable, 9, "Getting value");
                System.out.println("Approval Amount:" + maximumamount);

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                        "Internal Threshold settings", LogStatus.INFO, true);
            }
            else {

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                        "Internal Threshold not set", LogStatus.FAIL, true);
            }

            FunctionLibrary.clickObject(accountsTab,"","Clicking on accounts tab");

            CommonLibrary.searchForAccount(dataObj.get("AccountNumber"));

            FunctionLibrary.waitForObject = new WebDriverWait(FunctionLibrary.objDriver, 90);
            FunctionLibrary.waitForObject.until(ExpectedConditions.presenceOfElementLocated(By.linkText(dataObj.get("AccountNumber"))));

            FunctionLibrary.clickObject(CRMfinancialsTab, "", "Clicking on financials tab");
            WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 90);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(adjustmentTab)));

            FunctionLibrary.clickObject(adjustmentTab,"","clicking on adjustment tab");

            maximumamount=maximumamount.replace(",","");
            Double unitPriceAmount = Double.valueOf(maximumamount);
            //Double addedAmount =unitPriceAmount+Double.parseDouble(dataObj.get("GreaterApprovalAmount"));
            Double addedAmount =unitPriceAmount+getValueforSr();
            String adjustedAmountToParse=Double.toString(addedAmount);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(adjustmentNewbtn)));
            FunctionLibrary.clickObject(adjustmentNewbtn,"","Clicking on new button");
            Thread.sleep(4000);

            FunctionLibrary.setText(adjustmentSubCategorytxtbox,dataObj.get("Subcategory"),"Entering category");
            FunctionLibrary.setText(adjustmentUnitpricetxtbox,adjustedAmountToParse,"Entering category");
            FunctionLibrary.clickObject(adjustmentSavebtn,"","Clicking on Save");
            Thread.sleep(4000);
            FunctionLibrary.clickObject(adjustmentBtn,"","Clicking on Adjust");

            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(serviceRequestListTable)));
            int rownumberwithServiceRequestone=FunctionLibrary.getRowNumberFromWebTable(serviceRequestListTable,"ADJUSTMENT","");


            String xpathSRTbl=""+xpathserviceReqListTable;
            String ServiceRequestID = FunctionLibrary.getWebTableCellData(xpathSRTbl,rownumberwithServiceRequestone,2,"get ServiceRequestID");
            String icStatus =FunctionLibrary.objDriver.findElement(By.xpath("//td[@title='ESCALATED']/span[1]/img")).getAttribute("title");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Service request found with SR#: " + ServiceRequestID, LogStatus.INFO, true);

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "IC Status: " + icStatus, LogStatus.INFO, true);

            CommonLibrary.logoutSiebelApplicaiton();
            Thread.sleep(3000);
            CommonLibrary.loginSiebelAppSecondUser(dataObj);

            FunctionLibrary.webdrvwaitByVisiblityofElementClickableByXpath(FunctionLibrary.objDriver, 20, xpathServiceRequestTab);
            //search for service Request ID
            desc = "Clicking on service Request tab";
            FunctionLibrary.clickObject(xpathServiceRequestTab, "", desc);

            FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceReuestSearchField)).sendKeys(ServiceRequestID);

            desc = "Clicking on Service Request Search Go Button";
            FunctionLibrary.clickObject(xpathServiceRequestSearchGo, "", desc);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathResult)));

            FunctionLibrary.objDriver.findElement(By.xpath(ownerFieldSrch)).sendKeys(dataObj.get("Second LoginUserName"));
            //  ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Adding the service request with SR#: " + ServiceRequestID, LogStatus.INFO, true);
            desc = "Saving the service request";
            FunctionLibrary.clickObject(xpathServiceRequestSaveButton, "", desc);
            Thread.sleep(4000);
           /* wait.until(ExpectedConditions.elementToBeClickable(By.xpath(escalationTab)));

            FunctionLibrary.clickObject(escalationTab, "", "click on Escalation Tab");*/


            try
            {
                FunctionLibrary.selectDropDownList("//select[@aria-label='Third Level View Bar']","Escalation","");
            }
            catch(Exception e)
            {

                FunctionLibrary.clickObject(escalationTab, "", "");
            }

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(adjustmentApproveButton)));

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Supervisor approving the SR ", LogStatus.INFO, true);
            FunctionLibrary.clickObject(adjustmentApproveButton, "", "click on approve");

          /*  try {

                WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,90);
                wait2.until(ExpectedConditions.alertIsPresent());
                Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                alert.accept();

            }
            catch (Exception e) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Unknown error",LogStatus.FAIL,true);
            }*/

            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='_sweview_popup']")));
                String alertText=FunctionLibrary.objDriver.findElement(By.xpath("//div[@id='_sweview_popup']")).getText();
              //  if(alertText.equals("Internal Control Threshold Master Data is missing.(SBL-EXL-00151)")) {
                    if(alertText.equals("This Service request has been approved and assigned to the CSR for processing to continue(SBL-EXL-00151)")) {
                        FunctionLibrary.objDriver.findElement(By.xpath(okButton)).click();
                }
                else
                {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Unknown error",LogStatus.FAIL,true);
                    FunctionLibrary.objDriver.findElement(By.xpath(okButton)).click();
                }
            }
            catch (Exception e) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Unknown error",LogStatus.FAIL,true);
            }

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//td[@title='APPROVED']/span[1]/img", okButton, 20, 30);

            String actual=FunctionLibrary.objDriver.findElement(By.xpath("//td[@title='APPROVED']/span[1]/img")).getAttribute("title");
            System.out.println("actual:"+actual);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected :APPROVED <br> Actual:"+actual,LogStatus.PASS,true);

            CommonLibrary.logoutSiebelApplicaiton();
            CommonLibrary.loginSiebelAppFirstUser(dataObj);

            //search for service Request ID
            desc = "Clicking on service Request tab";
            FunctionLibrary.clickObject(xpathServiceRequestTab, "", desc);

            FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceReuestSearchField)).sendKeys(ServiceRequestID);

            desc = "Clicking on Service Request Search Go Button";
            FunctionLibrary.clickObject(xpathServiceRequestSearchGo, "", desc);

            Thread.sleep(10000);

          /*  FunctionLibrary.clickObject(escalationTab, "", "click on Escalation Tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(adjustProcess, escalationTab, 5, 20);*/
            
            
            
            try
            {
                FunctionLibrary.selectDropDownList("//select[@aria-label='Third Level View Bar']","Escalation","");
            }
            catch(Exception e)
            {

                FunctionLibrary.clickObject(escalationTab, "", "");
            }

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(adjustProcess)));
            FunctionLibrary.clickObject(adjustProcess, "", "click on Process");

            String actualProcessAlert=FunctionLibrary.objDriver.findElement(By.xpath("//div[@id='_sweview_popup']")).getText();

            FunctionLibrary.objDriver.findElement(By.xpath(okButton)).click();

          /*  String actualProcessAlert="";
            try {

                WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,90);
                wait2.until(ExpectedConditions.alertIsPresent());
                Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                actualProcessAlert=alert.getText();
                alert.accept();

            }
            catch (Exception e) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Unknown error",LogStatus.FAIL,true);
            }*/

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Verifying Processed Message, Expected :Transaction Successfully Processed(SBL-EXL-00151)"+"<br>"+
                    "Actual:"+actualProcessAlert,LogStatus.PASS,true);

            //   FunctionLibrary.objDriver.findElement(By.xpath(xpathrejectCode)).sendKeys("REJECT");

            FunctionLibrary.webdrvwaitByVisiblityofElementClickableByXpath(FunctionLibrary.objDriver, 15, xpathserviceReqListTable);
            String actualval=FunctionLibrary.objDriver.findElement(By.xpath("//td[@title='PROCESSED']/span[1]/img")).getAttribute("title");
            System.out.println("actual:"+actual);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Verifying Sub status, Expected :PROCESSED <br> Actual:"+actualval,LogStatus.PASS,true);

            FunctionLibrary.objDriver.findElement(By.xpath(xpathMoreInfo)).click();
            String statusText=FunctionLibrary.objDriver.findElement(By.xpath("//input[@aria-label='SR Status']")).getAttribute("value");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"verifying status after process:"+statusText,LogStatus.PASS,true);


        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown error occurred:" + e.getMessage(), LogStatus.FAIL, true);
        }


    }
    /*
    Automated for Test case:CMGT-111
    Test case desc: Supervisor rejects escalated reversal SR that is created when reversal amount is greater than FL reversal clerk approval limit
   */
    public static void tcCaseManagementCMGT111() throws Exception {
        try
        {

            String desc = "";
            ReportLibrary.getPath();
            dataObj.get("ExecutionStatus");
            dataObj.get("Account Type");
            dataObj.get("AccountNumber");
            dataObj.get("Mode Type");



            //******************Changing Mode***********************************************
            //Function call for Change Logon mode
            //changeLogonMode(ModeType); //Calling ChangeLogonMode Function
            CommonLibrary.clickSiteMap();
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@id='sitemapFilterInput']")).sendKeys("internal Controls");

            FunctionLibrary.clickObject(internalControls, "", "Click on internal controls link");

            FunctionLibrary.clickObject(internalControlsQueryBtn, "", "Clicking on Query button");


            FunctionLibrary.clickObject(internalControlThresholdResponsibility, "", "Clicking on Responsibilty");
            FunctionLibrary.setText(internalControlThresholdResponsibilitytxtbox, dataObj.get("Responsibility"), "Entering Responsibilty");

            FunctionLibrary.clickObject(intenalControlsQueryPaytype, "", "Clicking on pay type");
            FunctionLibrary.setText(intenalControlsQueryPaytypetxtbox, dataObj.get("Paytype"), "Entering Pay Type");
            FunctionLibrary.clickObject(internalControlsQueryTransactiontype, "", "Clicking on transaction type");
            FunctionLibrary.setText(internalControlsQueryTransactiontypetxtbox, dataObj.get("Transactiontype"), "Entering transaction Type");
            FunctionLibrary.clickObject(internalControlQueryGobtn, "", "Clicking on Go button");
            //Thread.sleep(5000);
            WebDriverWait wait1 = new WebDriverWait(FunctionLibrary.objDriver, 150);
            wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@title='Internal Control Threshold List:Query']")));

            int size = FunctionLibrary.objDriver.findElements(By.xpath("//table[@summary='Internal Control Threshold List']/tbody/tr")).size();
            System.out.println("size:" + size);
            String maxValue = "";
            if (size > 1) {
                int getrowNumberInternalThresholdTable = FunctionLibrary.getRowNumberFromWebTable(internalControlsThresholdTable, dataObj.get("Paytype"), "get the row number of cash tranaction");

                maxValue = FunctionLibrary.getWebTableCellData(internalControlsThresholdTable, getrowNumberInternalThresholdTable, 9, "Getting value");
                System.out.println("Approval Amount:" + maxValue);

                String existingAmount = maxValue;
                //existingAmount=existingAmount.replace("$","");
                existingAmount=existingAmount.replace(",","");
                //Double addedAmount = Double.parseDouble(dataObj.get("GreaterApprovalAmount"));
                //System.out.print("addedamount:"+addedAmount);
                Double amount = Double.parseDouble(existingAmount);
                System.out.print("amount:"+amount);
                Double amountToBeReversed = amount +  getValueforSr();
                String amountToBeReversedstring=Double.toString(amountToBeReversed);


                int rowNumber = FunctionLibrary.getRowNumberFromWebTable(internalControlsThresholdTable, dataObj.get("Responsibility"), "get the row number");
                System.out.println("rownumber:" + rowNumber);

                FunctionLibrary.verifyWebTableCellData(internalControlsThresholdTable, rowNumber, 6, "REVERSAL", "Verifying internal control settings", true);

                CommonLibrary.searchForAccount(dataObj.get("AccountNumber"));
                wait1.until(ExpectedConditions.elementToBeClickable(By.xpath(linkFinancial)));
                FunctionLibrary.clickObject(linkFinancial, "", "Clicking on financial tab");
                wait1.until(ExpectedConditions.elementToBeClickable(By.xpath(accountReplenishmentTab)));

                FunctionLibrary.clickObject(accountReplenishmentTab, "", "Clicking on account replenishment");
                Thread.sleep(2000);
                WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 90);
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(accountReplenishmentNewbtn)));
                //FunctionLibrary.clickObject(accountReplenishmentNewbtn, "", "Clicking on Save");
                FunctionLibrary.objDriver.findElement(By.xpath(accountReplenishmentNewbtn)).click();
                Thread.sleep(4000);
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(amounttxt)));
                FunctionLibrary.objDriver.findElement(By.xpath(amounttxt)).sendKeys(amountToBeReversedstring);
                Thread.sleep(4000);
                //   FunctionLibrary.setText(amounttxt, amountToBeReversed.toString(), "Entering amount");
                FunctionLibrary.clickObject(accountReplenishmentSavebutton, "", "Clicking on Save");

                FunctionLibrary.scrollDowntoElement(newPaymentList, "Scrol down");
                FunctionLibrary.clickObject(newPaymentList, "", "click on newPaymentList");

                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(saveRebillInfo,
                        newPaymentList, 3, 20);

                FunctionLibrary.scrollDowntoElement(saveRebillInfo, "Scrol down");
                FunctionLibrary.selectDropDownListByValue(replenishmentDetailsRebillPayTypeDropdown,dataObj.get("Paytype"),"select cash from dropdown");
                if(dataObj.get("Paytype").equalsIgnoreCase("SAVINGS") || dataObj.get("Paytype").equalsIgnoreCase("CHECKING"))
                {
                    //Enter Bank Account no:
                    FunctionLibrary.setText(paymentDetailsBankAccountNbrTxtBox,dataObj.get("BankAccount"),"Enter bank number");
                    // Enter Routing No:
                    FunctionLibrary.setText(paymentDetailsRoutingNbrTxtBox,dataObj.get("BankRoutine"),"Enter Routing number");
                }
                if(dataObj.get("Paytype").equalsIgnoreCase("CHECK")){

                    FunctionLibrary.objDriver.findElement(By.xpath("//input[@id='ChkNumber']")).sendKeys(dataObj.get("Checknumber"));

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                            "Doing payment through check", LogStatus.INFO, true);

                }

                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(paymentListSaveBtn,
                        saveRebillInfo, 3, 20);
                FunctionLibrary.clickObject(paymentListSaveBtn,"","Saving payment");
                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(processPayBtn,
                        paymentListSaveBtn, 3, 20);
                FunctionLibrary.clickObject(processPayBtn,"","Clicking on process pay");
                try {

                    WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,90);
                    wait2.until(ExpectedConditions.alertIsPresent());
                    Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                    alert.accept();

                }
                catch (Exception e) {

                }
                FunctionLibrary.objDriver.findElement(By.xpath("//*[@title='Financial Transaction List:Reverse']")).click();
                Thread.sleep(6000);

                try {

                    WebDriverWait wait4 = new WebDriverWait(FunctionLibrary.objDriver, 60);
                    wait4.until(ExpectedConditions.alertIsPresent());
                    Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                    String alertText = alert.getText();
                    System.out.println(alertText);
                    alert.accept();

                } catch (Exception e) {
                    //exception handling
                }

                wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathserviceReqListTable)));
                String xpathSRTbl = ""+xpathserviceReqListTable;
                int rowNumberSR = FunctionLibrary.getRowNumberFromWebTable(xpathSRTbl, "REVERSAL", "Get the row number");
                String ServiceRequestID = FunctionLibrary.getWebTableCellData(xpathSRTbl, rowNumberSR, 2, "get ServiceRequestID");

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Service request with SR#: " + ServiceRequestID, LogStatus.INFO, true);

                CommonLibrary.logoutSiebelApplicaiton();

                CommonLibrary.loginSiebelAppSecondUser(dataObj);

                FunctionLibrary.webdrvwaitByVisiblityofElementClickableByXpath(FunctionLibrary.objDriver, 20, xpathServiceRequestTab);

                desc = "Clicking on service Request tab";
                FunctionLibrary.clickObject(xpathServiceRequestTab, "", desc);
                FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceReuestSearchField)).sendKeys(ServiceRequestID);

                desc = "Clicking on Service Request Search Go Button";
                FunctionLibrary.clickObject(xpathServiceRequestSearchGo, "", desc);

                WebDriverWait waitForSRsearch = new WebDriverWait(FunctionLibrary.objDriver, 150);
                waitForSRsearch.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathResult)));
                FunctionLibrary.objDriver.findElement(By.xpath(ownerFieldSrch)).sendKeys(dataObj.get("Second LoginUserName"));
                //  ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Adding the service request with SR#: " + ServiceRequestID, LogStatus.INFO, true);
                desc = "Saving the service request";
                FunctionLibrary.clickObject(xpathServiceRequestSaveButton, "", desc);

                // FunctionLibrary.clickObject(escalationTab, "", "click on Escalation Tab");
                Thread.sleep(4000);
                //  FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(rejectButton, escalationTab, 5, 20);

                try
                {
                    FunctionLibrary.selectDropDownList("//select[@aria-label='Third Level View Bar']","Escalation","");
                }
                catch(Exception e)
                {

                    FunctionLibrary.clickObject(escalationTab, "", "");
                }
                FunctionLibrary.clickObject(rejectButton, "", "click on Reject");

                FunctionLibrary.setText(xpathrejectCodeXpath, "REJECT", "Entering Reject code");

                Thread.sleep(4000);
                FunctionLibrary.clickObject(rejectCodesaveBtn, "", "click on Save");

                Thread.sleep(5000);
                FunctionLibrary.webdrvwaitByVisiblityofElementClickableByXpath(FunctionLibrary.objDriver, 15, xpathserviceReqListTable);
                String actual = FunctionLibrary.objDriver.findElement(By.xpath("//td[@title='REJECTED']/span[1]/img")).getAttribute("title");
                System.out.println("actual:" + actual);
                //  ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected :REJECTED <br> Actual:" + actual, LogStatus.PASS, true);
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Verifying SR sub-status, Expected :REJECTED <br> Actual:"+actual,LogStatus.PASS,true);

                FunctionLibrary.objDriver.findElement(By.xpath(xpathMoreInfo)).click();

                String owner=FunctionLibrary.objDriver.findElement(By.xpath("//input[@aria-label='Owner']")).getAttribute("value");

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"verifying owner:"+owner,LogStatus.PASS,true);

                try
                {
                    FunctionLibrary.selectDropDownList("//select[@aria-label='Third Level View Bar']","Notes","");
                }
                catch(Exception e)
                {
                    desc = "Clicking on Notes tab";
                    FunctionLibrary.clickObject(xpathNotesTab, "", desc);
                }

                WebDriverWait wait6 = new WebDriverWait(FunctionLibrary.objDriver, 90);
                wait6.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNotesNewButton)));

                FunctionLibrary.objDriver.findElement(By.xpath(xpathNotesNewButton)).click();

                wait6.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathNotesCount)));

                desc = "Clicking on Notes Focus";
                FunctionLibrary.objDriver.findElement(By.xpath(xpathNotesFocus)).click();

                wait6.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNotesText)));

                FunctionLibrary.objDriver.findElement(By.xpath(xpathNotesText)).click();

                FunctionLibrary.objDriver.findElement(By.xpath(xpathNotesText)).sendKeys(dataObj.get("Notestext"));

                desc="Clicking on Notes save button";
                FunctionLibrary.clickObject(xpathNotesSaveButton,"",desc);

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Added notes", LogStatus.INFO, true);

                CommonLibrary.logoutSiebelApplicaiton();
                Thread.sleep(10000);
                CommonLibrary.loginSiebelAppFirstUser(dataObj);

                FunctionLibrary.webdrvwaitByVisiblityofElementClickableByXpath(FunctionLibrary.objDriver, 20, xpathServiceRequestTab);
                //search for service Request ID
                desc = "Clicking on service Request tab";
                FunctionLibrary.clickObject(xpathServiceRequestTab, "", desc);

                FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceReuestSearchField)).sendKeys(ServiceRequestID);

                desc = "Clicking on Service Request Search Go Button";
                FunctionLibrary.clickObject(xpathServiceRequestSearchGo, "", desc);

                Thread.sleep(10000);

                FunctionLibrary.objDriver.findElement(By.xpath(srStatus)).clear();
                FunctionLibrary.objDriver.findElement(By.xpath(srStatus)).sendKeys(dataObj.get("Status"));


                FunctionLibrary.objDriver.findElement(By.xpath("//input[@aria-label='SR Sub Status']")).clear();
                FunctionLibrary.objDriver.findElement(By.xpath("//input[@aria-label='SR Sub Status']")).sendKeys(dataObj.get("SRSubStatus"));
                FunctionLibrary.objDriver.findElement(By.xpath("//input[@aria-label='Closure Code']")).clear();
                FunctionLibrary.objDriver.findElement(By.xpath("//input[@aria-label='Closure Code']")).sendKeys("INTERNAL CONTROL");
                FunctionLibrary.clickObject(xpathServiceRequestSaveButton, "", desc);

                String statusTextCSR=FunctionLibrary.objDriver.findElement(By.xpath(srStatus)).getAttribute("value");
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"verifying status:"+statusTextCSR,LogStatus.PASS,true);
                String subStatusTextCSR=FunctionLibrary.objDriver.findElement(By.xpath("//input[@aria-label='SR Sub Status']")).getAttribute("value");
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"verifying sub-status:"+subStatusTextCSR,LogStatus.PASS,true);

                CommonLibrary.searchForAccount(dataObj.get("AccountNumber"));

                FunctionLibrary.objDriver.findElement(By.xpath("//a[text()='History']")).click();

                FunctionLibrary.objDriver.findElement(By.xpath("//a[text()='Non Financials']")).click();

                String activitiestable = "//table[@summary='Activities']";
                int rowNumberOne = FunctionLibrary.getRowNumberFromWebTable(activitiestable, "SERVICE_REQUEST", "Get the row number");
                System.out.println(rowNumberOne);
                FunctionLibrary.verifyWebTableCellData(activitiestable, rowNumberOne, 5, "SR is rejected. reason -REJECT",
                        "verifying in Non-Financial History", true);

            }
            else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                        "Internal Threshold not set", LogStatus.FAIL, true);
            }

        }
        catch(Exception e)
        {
            String errorMsg=e.getMessage();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown error occured "+errorMsg
                    , LogStatus.FAIL, true);
        }


    }//End of CMGT-111
    /*
       Automated for Test case:CMGT-033
       Test case desc: Search unassigned SRs
     */
    public static void tcCaseManagementCMGT033() throws Exception {
        try {
            dataObj.get("Notes");
            dataObj.get("Description");
            dataObj.get("Queue");
            dataObj.get("SubQueue");
            dataObj.get("SR Priority");
            String desc = "";

            String SRSubStatus = dataObj.get("SRSubStatus");
            ReportLibrary.getPath();

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathServiceRequestList
                    , xpathServiceRequestTab, 5, 20);
            desc = "Clicking on Service request list";
            FunctionLibrary.clickObject(xpathServiceRequestList, "", desc);
            WebDriverWait waitforNeW = new WebDriverWait(FunctionLibrary.objDriver, 90);
            waitforNeW.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNewServiceRequestButton)));
            int dropdownlistCount = FunctionLibrary.getDropdownListCount(xpathView);

            System.out.println("dropdownlistCount: " + dropdownlistCount);
            if (dropdownlistCount > 1) {

                FunctionLibrary.selectDropDownList(xpathView,"All Service Requests","Selecting All Service Requests from dropdown");

                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//li[text()='AGENCY']",
                        "//input[@aria-label='Queue']//following::span[1]", 15, 120);
                desc = "Clicking on Query";
                Thread.sleep(3000);
                FunctionLibrary.clickObject(queryBtn, "", desc);

                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathSubStatus,
                        queryBtn, 15, 120);
                FunctionLibrary.objDriver.findElement(By.xpath(xpathSubStatus)).clear();
                FunctionLibrary.objDriver.findElement(By.xpath(xpathSubStatus)).sendKeys(SRSubStatus);
                Thread.sleep(3000);


                desc="Clicking on Go button";
                FunctionLibrary.clickObject(xpathServiceRequestGoButton,"",desc);
                Thread.sleep(4000);

                WebDriverWait waitForCopycaseBtn = new WebDriverWait(FunctionLibrary.objDriver, 120);

                waitForCopycaseBtn.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@title='Service Requests:Copy Case']")));

                WebElement table = FunctionLibrary.objDriver.findElement(By.xpath("//table[@summary='Service Request List']"));
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Searching for Unassigned Service Requests"
                        , LogStatus.INFO, false);
                int totalrowCount = FunctionLibrary.getWebTableRowCount(table);

                for (int i = 1; i <totalrowCount; i++) {

                    String sid = FunctionLibrary.getWebTableCellData("//table[@summary='Service Request List']", i, 2, "Getting service request");
                    boolean isScreenShotRequired = false;
                    if(i==1)
                    {
                        isScreenShotRequired = true;
                    }
                    FunctionLibrary.verifyWebTableCellData("//table[@summary='Service Request List']", i, 25,
                            "", "Verifying blank Owner Name for the SR:" + sid, isScreenShotRequired);

                }

            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Logged in user is not Supervisor", LogStatus.FAIL, true);
            }
        }
        catch (Exception e) {
            String Errormsg=e.getMessage();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                    "Unknown Error Message :"
                            +Errormsg, LogStatus.FAIL, true);
        }

    }
    /*
     Automated for Test case:CMGT-018
     Test case desc: SR creation for reversals
   */
    public static void tcCaseManagementCMGT018() throws Exception {
        try
        {

            ReportLibrary.getPath();
            dataObj.get("ExecutionStatus");
            dataObj.get("Account Type");
            String acctNumber=dataObj.get("AccountNumber");

            CommonLibrary.clickSiteMap();
            FunctionLibrary.scrollDowntoElement(internalControls, "Scroll down to Internal Controls link");

            FunctionLibrary.clickObject(internalControls, "", "Click on internal controls link");

            FunctionLibrary.clickObject(internalControlsQueryBtn,"","Clicking on Query button");


            FunctionLibrary.clickObject(internalControlThresholdResponsibility,"","Clicking on Responsibilty");
            FunctionLibrary.setText(internalControlThresholdResponsibilitytxtbox,dataObj.get("Responsibility"),"Entering Responsibilty");

            FunctionLibrary.clickObject(intenalControlsQueryPaytype,"","Clicking on pay type");
            FunctionLibrary.setText(intenalControlsQueryPaytypetxtbox,dataObj.get("Paytype"),"Entering Pay Type");
            FunctionLibrary.clickObject(internalControlsQueryTransactiontype,"","Clicking on transaction type");
            FunctionLibrary.setText(internalControlsQueryTransactiontypetxtbox,dataObj.get("Transactiontype"),"Entering transaction Type");
            FunctionLibrary.clickObject(internalControlQueryGobtn,"","Clicking on Go button");
            Thread.sleep(5000);

            int size=FunctionLibrary.objDriver.findElements(By.xpath("//table[@summary='Internal Control Threshold List']/tbody/tr")).size();
            System.out.println("size:"+size);
            String maximumThresholdBal="";
            if(size>1) {
                String xpathOfInternalControlThresholdTbl=".//table[@summary='Internal Control Threshold List']";
                int rowNumber = FunctionLibrary.getRowNumberFromWebTable(xpathOfInternalControlThresholdTbl, "CASH", "Get the row number");
                FunctionLibrary.getWebTableCellData(xpathOfInternalControlThresholdTbl,rowNumber,8,"get minimum Threshold value ");
                maximumThresholdBal = FunctionLibrary.getWebTableCellData(xpathOfInternalControlThresholdTbl,rowNumber,9,"get minimum Threshold value ");


                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                        "Internal Threshold settings", LogStatus.INFO, true);
            }
            else {

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                        "Internal Threshold not set", LogStatus.FAIL, true);

            }

            Thread.sleep(3000);

            String existingAmount=maximumThresholdBal;
            //existingAmount=existingAmount.replace("$","");
            existingAmount=existingAmount.replace(",","");
            // double addedAmount = Double.parseDouble(dataObj.get("GreaterApprovalAmount"));
            Double amount = Double.parseDouble(existingAmount);
            Double amountToBeReversed=amount+getValueforSr();
            String amountToBeReversedstring=Double.toString(amountToBeReversed);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "existing threshold maximum amount "+
                    ""+existingAmount, LogStatus.INFO, true);

            CommonLibrary.searchForAccount(acctNumber);

            WebDriverWait wait1 = new WebDriverWait(FunctionLibrary.objDriver,30);
            wait1.until(ExpectedConditions.elementToBeClickable(By.xpath(linkFinancial)));
            FunctionLibrary.clickObject(linkFinancial, "", "Clicking on financial tab");
            wait1.until(ExpectedConditions.elementToBeClickable(By.xpath(accountReplenishmentTab)));

            FunctionLibrary.clickObject(accountReplenishmentTab, "", "Clicking on account replenishment");
            Thread.sleep(2000);
            WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 90);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(accountReplenishmentNewbtn)));
            //FunctionLibrary.clickObject(accountReplenishmentNewbtn, "", "Clicking on Save");
            FunctionLibrary.objDriver.findElement(By.xpath(accountReplenishmentNewbtn)).click();
            Thread.sleep(4000);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(amounttxt)));
            FunctionLibrary.objDriver.findElement(By.xpath(amounttxt)).sendKeys(amountToBeReversedstring);
            Thread.sleep(4000);
            //   FunctionLibrary.setText(amounttxt, amountToBeReversed.toString(), "Entering amount");
            FunctionLibrary.clickObject(accountReplenishmentSavebutton, "", "Clicking on Save");


            FunctionLibrary.scrollDowntoElement(newPaymentList, "Scrol down");
            //FunctionLibrary.clickObject(newPaymentList, "", "click on newPaymentList");

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(saveRebillInfo,
                    newPaymentList, 5, 20);

            FunctionLibrary.scrollDowntoElement(saveRebillInfo, "Scrol down");
            FunctionLibrary.selectDropDownListByValue(replenishmentDetailsRebillPayTypeDropdown,dataObj.get("Paytype"),"select cash from dropdown");
            if(dataObj.get("Paytype").equalsIgnoreCase("SAVINGS") || dataObj.get("Paytype").equalsIgnoreCase("CHECKING"))
            {

                FunctionLibrary.setText(paymentDetailsBankAccountNbrTxtBox,dataObj.get("BankAccount"),"Enter bank number");

                FunctionLibrary.setText(paymentDetailsRoutingNbrTxtBox,dataObj.get("BankRoutine"),"Enter Routing number");
            }
            if(dataObj.get("Paytype").equalsIgnoreCase("CHECK")){

                FunctionLibrary.objDriver.findElement(By.xpath("//input[@id='ChkNumber']")).sendKeys(dataObj.get("Checknumber"));

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                        "Doing payment through check", LogStatus.INFO, true);

            }

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(paymentListSaveBtn,
                    saveRebillInfo, 5, 20);
            //   FunctionLibrary.clickObject(paymentListSaveBtn,"","Saving payment");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(processPayBtn,
                    paymentListSaveBtn, 5, 20);
            FunctionLibrary.clickObject(processPayBtn,"","Clicking on process pay");
            try {

                WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,90);
                wait2.until(ExpectedConditions.alertIsPresent());
                Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                alert.accept();

            }
            catch (Exception e) {
                //exception handling
            }
            FunctionLibrary.clickObject(reverseBtn, "", "clicking on reverse");
            try {

                WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,30);
                wait2.until(ExpectedConditions.alertIsPresent());
                Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                String alertText = alert.getText();
                alert.accept();

            }
            catch (Exception e) {

            }


            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Amonut To be reversed : "+
                    amountToBeReversed,LogStatus.INFO,true);

            WebDriverWait wait4 = new WebDriverWait(FunctionLibrary.objDriver, 150);
            wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathResult)));
            String ServiceRequestID="";
            if (FunctionLibrary.objDriver.findElement(By.xpath(xpathResult)).isDisplayed()) {
                String xpathSRTbl=xpathserviceReqListTable;
                int rowNumberSR = FunctionLibrary.getRowNumberFromWebTable(xpathSRTbl, "REVERSAL", "Get the row number");
                ServiceRequestID = FunctionLibrary.getWebTableCellData(xpathSRTbl,rowNumberSR,2,"get ServiceRequestID");
                String icStatus =FunctionLibrary.objDriver.findElement(By.xpath("//td[@title='ESCALATED']/span[1]/img")).getAttribute("title");
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Service request created with SR#: " + ServiceRequestID, LogStatus.PASS, true);

                DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
                Date date = new Date();
                String expectedDate=dateFormat.format(date);
                String dateOpened = FunctionLibrary.getWebTableCellData(xpathSRTbl,rowNumberSR,3,"get date");
                String[] dateOpenedSplit=dateOpened.split(" ");

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"<b>Verifying Date Opened</b> <br> Expected  :<b>  "+expectedDate+"  </b>Actual:<b> "+dateOpenedSplit[0]+" </b ",LogStatus.PASS,true);



                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "IC Status: " + icStatus, LogStatus.PASS, true);

            }

            if(get_S_SRV_REQ_Details(ServiceRequestID))
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"<b>Verifying in S_SRV_REQ_Details</b> <br> Expected  :<b>  YES  </b>Actual:<b> YES </b ",LogStatus.PASS,false);

            }
            else
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"<b>Verifying in S_SRV_REQ_Details</b> <br> Expected  :<b>  YES  </b>Actual:<b> NO </b ",LogStatus.PASS,false);
            }
            FunctionLibrary.objDriver.findElement(By.xpath(xpathMoreInfo)).click();

            String owner=FunctionLibrary.objDriver.findElement(By.xpath("//input[@aria-label='Owner']")).getAttribute("value");
            if(owner.equals(""))
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"verifying unassigned owner",LogStatus.PASS,true);
            }
            else
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"verifying Owner:Unassigned",LogStatus.FAIL,true);
            }

            String statusText=FunctionLibrary.objDriver.findElement(By.xpath(srStatus)).getAttribute("value");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"verifying status:"+statusText,LogStatus.PASS,true);

            String substatusText=FunctionLibrary.objDriver.findElement(By.xpath("//input[@aria-label='SR Sub Status']")).getAttribute("value");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"verifying Substatus:"+substatusText,LogStatus.PASS,true);

            CommonLibrary.searchForAccount(acctNumber);
            FunctionLibrary.objDriver.findElement(By.xpath("//a[text()='History']")).click();

           // FunctionLibrary.objDriver.findElement(By.xpath("//a[text()='Non Financials']")).click();


            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//table[@summary='Activities']", "//a[text()='Non Financials']", 15, 60);

            String activitiestable = "//table[@summary='Activities']";
            int rowNumberOne = FunctionLibrary.getRowNumberFromWebTable(activitiestable, "ADD", "Get the row number");
            System.out.println(rowNumberOne);
            FunctionLibrary.verifyWebTableCellData(activitiestable, rowNumberOne, 5, "Add Escalation",
                    "verifying in Non-Financial History", true);
        }
        catch(Exception e)
        {
            String Errormsg=e.getMessage();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                    "Service Request creation is having issue with Error Message :"
                            +Errormsg, LogStatus.FAIL, true);
        }


    }
    /*
       Automated for Test case:CMGT-005
       Test case desc: SR creation by invoice number
     */
    public static void tcCaseManagementCMGT005() throws Exception {
        try {

            String accountNumber = dataObj.get("AccountNumber");
            String notesText = dataObj.get("Notes");
            String descriptionText = dataObj.get("Description");
            String queueValue = dataObj.get("Queue");
            String subQueueValue = dataObj.get("SubQueue");
            String priority = dataObj.get("SR Priority");


            String desc = "";
            String serviceRequestId1 = "";
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathServiceRequestList,xpathServiceRequestTab,8,60);

            desc = "Clicking on Service request list";
            FunctionLibrary.clickObject(xpathServiceRequestList, "", desc);
            WebDriverWait waitforNeW = new WebDriverWait(FunctionLibrary.objDriver, 90);
            waitforNeW.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNewServiceRequestButton)));
            int dropdownlistCount=FunctionLibrary.getDropdownListCount(xpathView);

            System.out.println("dropdownlistCount: "+dropdownlistCount);
            if(dropdownlistCount>1) {

                FunctionLibrary.scrollDown("","Scrolling Down");

                desc = "Clicking on new SR button";
                FunctionLibrary.clickObject(serviceRequestNewBtn, "", desc);
                Thread.sleep(4000);
                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathAccountNumber,
                        "//input[@aria-label='Account Name']", 15, 120);

                System.out.println("serviceRequestId1: "+serviceRequestId1);

                FunctionLibrary.setText(xpathAccountNumber,accountNumber,"Enter account number");
                FunctionLibrary.setText(Queue,queueValue,"Clicking on queue");
                FunctionLibrary.setText(SubQueue,subQueueValue,"Clicking on subqueue");

                FunctionLibrary.scrollUp("","Scrolling up");
                FunctionLibrary.objDriver.findElement(By.xpath(xpathSRPriority)).clear();
                FunctionLibrary.objDriver.findElement(By.xpath(xpathSRPriority)).sendKeys(priority);
                FunctionLibrary.objDriver.findElement(By.xpath(xpathNotes)).sendKeys(notesText);
                FunctionLibrary.objDriver.findElement(By.xpath(xpathDescription)).sendKeys(descriptionText);
                serviceRequestId1 = FunctionLibrary.objDriver.findElement(By.xpath("//label")).getText();
                try {
                    if (FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestShowMore)).isDisplayed()) {

                        FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestShowMore)).click();

                    }
                }catch (Exception e){
                    System.out.println("Show more is not displayed");
                }


                FunctionLibrary.scrollDown("","");
                FunctionLibrary.clickObject(invoiceBtn,"","Clicking on invoice");

                if(FunctionLibrary.objDriver.findElement(By.xpath(invoiceSelectBtn)).isEnabled())
                {
                    FunctionLibrary.clickObject(invoiceSelectBtn, "", "Clicking on invoice");
                    Thread.sleep(5000);
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Adding the service request with SR#: " + serviceRequestId1 +
                            " with Account Number " + accountNumber, LogStatus.INFO, true);
                    FunctionLibrary.objDriver.findElement(By.xpath("//button[contains(@aria-label,'Service Requests:Save')]/span[text()='Save']")).click();
                    try {

                        WebDriverWait wait8 = new WebDriverWait(FunctionLibrary.objDriver,30);
                        wait8.until(ExpectedConditions.alertIsPresent());
                        Alert alert = FunctionLibrary.objDriver.switchTo().alert();

                        alert.accept();
                    }
                    catch (Exception e)
                    {

                    }
                    System.out.print("sre"+serviceRequestId1);
                    FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathServiceReuestSearchField
                        ,"//a[text()='Service Requests Home']",30,60);
                    waitforNeW.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathServiceReuestSearchField)));
                    FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                            "//li[text()='PAYMENT']",xpathServiceReuestSearchField,2,2);
                    FunctionLibrary.setText(xpathServiceReuestSearchField,serviceRequestId1,"Enter account number");
                    //   FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceReuestSearchField)).sendKeys(serviceRequestId1);

                    desc = "Clicking on Service Request Search Go Button";
                    FunctionLibrary.clickObject(xpathServiceRequestSearchGo, "", desc);
                    Thread.sleep(6000);


                    try
                    {

                        FunctionLibrary.selectDropDownList("//select[@aria-label='Third Level View Bar']","Service Request History","");
                    }
                    catch(Exception e)
                    {

                        FunctionLibrary.clickObject(xpathServiceRequestHistoryTab, "", "");
                    }
                    waitforNeW.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathServiceRequestHistoryTable)));
                    int vectorSerivicerequestHistoryCount = FunctionLibrary.getWebTableRowCount(vectorServiceRequestHistory, "", "Service Request List");
                    System.out.println("vectorSerivicerequestHistoryCount: " + vectorSerivicerequestHistoryCount);


                    if (vectorSerivicerequestHistoryCount>1) {


                        int rownumberwithServiceRequestone=FunctionLibrary.getRowNumberFromWebTable(vectorServiceRequestHistory,serviceRequestId1,"");
                        FunctionLibrary.verifyWebTableCellData(vectorServiceRequestHistory,rownumberwithServiceRequestone , 22, serviceRequestId1, "Verifying Service Request in service request history",true);


                    } else {
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "SR not available", LogStatus.FAIL, true);
                    }

                    if(get_S_SRV_REQ_Details(serviceRequestId1))
                    {
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"<b>Verifying in S_SRV_REQ_Details</b> <br> Expected  :<b>  YES  </b>Actual:<b> YES </b ",LogStatus.PASS,false);

                    }
                    else
                    {
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"<b>Verifying in S_SRV_REQ_Details</b> <br> Expected  :<b>  YES  </b>Actual:<b> NO </b ",LogStatus.PASS,false);
                    }
                    waitforNeW.until(ExpectedConditions.elementToBeClickable(By.xpath(activityTab)));
                    FunctionLibrary.clickObject(activityTab,"","Clicking on activity tab");
                    FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Activities:New']")));
                    int rownumberactivitylist=FunctionLibrary.getRowNumberFromWebTable(activitylistTable,"PENDING","");
                    FunctionLibrary.verifyWebTableCellData(activitylistTable,rownumberactivitylist , 1, "Pay Invoice", "Verifying Activity",true);
                    FunctionLibrary.verifyWebTableCellData(activitylistTable,rownumberactivitylist , 2, "PENDING", "Activity Status",false);


                }
                else
                {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Invoice not available", LogStatus.FAIL, true);
                }
            }

            else
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Logged in user is not CSR", LogStatus.FAIL, true);
            }

        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown error occurred:", LogStatus.FAIL, true);
        }
    }
    /*
        Automated for Test case:CMGT-109
        Test case desc: Supervisor approves escalated reversal SR that
        is created when reversal amount is greater than FL reversal clerk approval limit
      */
    public static void tcCaseManagementCMGT109() throws Exception
    {
        try {
            CommonLibrary.clickSiteMap();
            String desc="";
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@id='sitemapFilterInput']")).sendKeys("internal Controls");

            FunctionLibrary.clickObject(internalControls, "", "Click on internal controls link");

            FunctionLibrary.clickObject(internalControlsQueryBtn, "", "Clicking on Query button");


            FunctionLibrary.clickObject(internalControlThresholdResponsibility, "", "Clicking on Responsibilty");
            FunctionLibrary.setText(internalControlThresholdResponsibilitytxtbox, dataObj.get("Responsibility"), "Entering Responsibilty");

            FunctionLibrary.clickObject(intenalControlsQueryPaytype, "", "Clicking on pay type");
            FunctionLibrary.setText(intenalControlsQueryPaytypetxtbox, dataObj.get("Paytype"), "Entering Pay Type");
            FunctionLibrary.clickObject(internalControlsQueryTransactiontype, "", "Clicking on transaction type");
            FunctionLibrary.setText(internalControlsQueryTransactiontypetxtbox, dataObj.get("Transactiontype"), "Entering transaction Type");
            FunctionLibrary.clickObject(internalControlQueryGobtn, "", "Clicking on Go button");
            //Thread.sleep(5000);
            WebDriverWait wait1 = new WebDriverWait(FunctionLibrary.objDriver, 150);
            wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@title='Internal Control Threshold List:Query']")));

            int size = FunctionLibrary.objDriver.findElements(By.xpath("//table[@summary='Internal Control Threshold List']/tbody/tr")).size();
            System.out.println("size:" + size);
            String maxValue = "";
            if (size > 1) {
                int getrowNumberInternalThresholdTable = FunctionLibrary.getRowNumberFromWebTable(internalControlsThresholdTable, dataObj.get("Paytype"), "get the row number of cash tranaction");

                maxValue = FunctionLibrary.getWebTableCellData(internalControlsThresholdTable, getrowNumberInternalThresholdTable, 9, "Getting value");
                System.out.println("Approval Amount:" + maxValue);
                String existingAmount=maxValue;
                //existingAmount=existingAmount.replace("$","");
                existingAmount=existingAmount.replace(",","");
                //double addedAmount = Double.parseDouble(dataObj.get("GreaterApprovalAmount"));
                Double amount = Double.parseDouble(existingAmount);
                //Double amountToBeReversed=amount+addedAmount;
                Double amountToBeReversed=amount+ getValueforSr();

                int rowNumber = FunctionLibrary.getRowNumberFromWebTable(internalControlsThresholdTable, dataObj.get("Responsibility"), "get the row number");
                System.out.println("rownumber:" + rowNumber);

                FunctionLibrary.verifyWebTableCellData(internalControlsThresholdTable, rowNumber, 6,"REVERSAL", "Verifying internal control settings", true);

                CommonLibrary.searchForAccount(dataObj.get("AccountNumber"));

                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(accountReplenishment, linkFinancial, 30, 30);

                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(accountReplenishmentNewbtn, accountReplenishmentTab, 20, 30);
                FunctionLibrary.clickObject(accountReplenishmentNewbtn,"", "Clicking on account replenishment new button");

                Thread.sleep(3000);
                FunctionLibrary.setText(amounttxt,amountToBeReversed.toString(),"Entering amount");
                FunctionLibrary.clickObject(accountReplenishmentSavebutton,"","Clicking on Save");
                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                        replenishmentDetailsRebillPayTypeDropdown,newPaymentList,5,20
                );
                FunctionLibrary.scrollDown("","");
                FunctionLibrary.scrollDown("", "");
                FunctionLibrary.selectDropDownListByValue("//select[@id='PaymentType']", dataObj.get("Paytype"), "Selecting Payment type");
                if(dataObj.get("Paytype").equalsIgnoreCase("SAVINGS") || dataObj.get("Paytype").equalsIgnoreCase("CHECKING"))
                {
                    //Enter Bank Account no:
                    FunctionLibrary.setText(paymentDetailsBankAccountNbrTxtBox,dataObj.get("BankAccount"),"Enter bank number");
                    // Enter Routing No:
                    FunctionLibrary.setText(paymentDetailsRoutingNbrTxtBox,dataObj.get("BankRoutine"),"Enter Routing number");
                }
                if(dataObj.get("Paytype").equalsIgnoreCase("CHECK")){

                    FunctionLibrary.objDriver.findElement(By.xpath("//input[@id='ChkNumber']")).sendKeys(dataObj.get("Checknumber"));

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                            "Doing payment through check", LogStatus.INFO, true);

                }


                FunctionLibrary.clickObject(accountReplenishmentPaymentSave, "", "Saving the payment");
                FunctionLibrary.clickObject(paymentListSaveBtn, "", "Saving payment");
                FunctionLibrary.clickObject(processPayBtn, "", "Clicking on process pay");
                try {

                    WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 90);
                    wait2.until(ExpectedConditions.alertIsPresent());
                    Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                    alert.accept();

                } catch (Exception e) {
                    //exception handling
                }
                FunctionLibrary.objDriver.findElement(By.xpath("//*[@title='Financial Transaction List:Reverse']")).click();
                Thread.sleep(6000);

                try {

                    WebDriverWait wait4 = new WebDriverWait(FunctionLibrary.objDriver, 60);
                    wait4.until(ExpectedConditions.alertIsPresent());
                    Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                    String alertText = alert.getText();

                    alert.accept();

                } catch (Exception e) {
                    //exception handling
                }

                String xpathSRTbl=xpathserviceReqListTable;
                int rowNumberSR = FunctionLibrary.getRowNumberFromWebTable(xpathSRTbl, "REVERSAL", "Get the row number");
                String ServiceRequestID = FunctionLibrary.getWebTableCellData(xpathSRTbl,rowNumberSR,2,"get ServiceRequestID");


                CommonLibrary.logoutSiebelApplicaiton();
                Thread.sleep(10000);
                CommonLibrary.loginSiebelAppSecondUser(dataObj);

                FunctionLibrary.webdrvwaitByVisiblityofElementClickableByXpath(FunctionLibrary.objDriver, 20, xpathServiceRequestTab);
                //search for service Request ID
                desc = "Clicking on service Request tab";
                FunctionLibrary.clickObject(xpathServiceRequestTab, "", desc);

                FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceReuestSearchField)).sendKeys(ServiceRequestID);

                desc = "Clicking on Service Request Search Go Button";
                FunctionLibrary.clickObject(xpathServiceRequestSearchGo, "", desc);

                Thread.sleep(10000);
                FunctionLibrary.objDriver.findElement(By.xpath(ownerFieldSrch)).sendKeys(dataObj.get("Second LoginUserName"));

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Adding the service request with SR#: " + ServiceRequestID, LogStatus.INFO, true);
                desc = "Saving the service request";
                FunctionLibrary.clickObject(xpathServiceRequestSaveButton, "", desc);
                Thread.sleep(4000);

                // FunctionLibrary.clickObject(escalationTab, "", "click on Escalation Tab");



                try
                {
                    FunctionLibrary.selectDropDownList("//select[@aria-label='Third Level View Bar']","Escalation","");
                }
                catch(Exception e)
                {

                    FunctionLibrary.clickObject(escalationTab, "", "");
                }
                // FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(Reversalapprovebutton, escalationTab, 5, 20);
                FunctionLibrary.clickObject(Reversalapprovebutton, "", "click on approve");

                try {
                    String alertText=FunctionLibrary.objDriver.findElement(By.xpath("//div[@id='_sweview_popup']")).getText();
                    if(alertText.equals("Internal Control Threshold Master Data is missing.(SBL-EXL-00151)")) {
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Unknown error",LogStatus.FAIL,true);
                        FunctionLibrary.objDriver.findElement(By.xpath(okButton)).click();
                    }
                    else
                    {
                        FunctionLibrary.objDriver.findElement(By.xpath(okButton)).click();
                    }
                }
                catch (Exception e) {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Unknown error",LogStatus.FAIL,true);
                }



              /*  try {

                    WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,90);
                    wait2.until(ExpectedConditions.alertIsPresent());
                    Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                    alert.accept();

                }
                catch (Exception e) {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Unknown error",LogStatus.FAIL,true);
                }*/

                String actual=FunctionLibrary.objDriver.findElement(By.xpath("//td[@title='APPROVED']/span[1]/img")).getAttribute("title");
                System.out.println("actual:"+actual);
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Verifying SR sub-status, Expected :APPROVED <br> Actual:"+actual,LogStatus.PASS,true);

                FunctionLibrary.objDriver.findElement(By.xpath(xpathMoreInfo)).click();
                String statusText=FunctionLibrary.objDriver.findElement(By.xpath(srStatus)).getAttribute("value");
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"verifying status:"+statusText,LogStatus.PASS,true);

                String owner=FunctionLibrary.objDriver.findElement(By.xpath("//input[@aria-label='Owner']")).getAttribute("value");

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"verifying owner:"+owner,LogStatus.PASS,true);



            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                        "Internal Threshold not set", LogStatus.FAIL, true);
            }
        }

        catch(Exception e)
        {
            e.getMessage();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                    "Unknown error occured", LogStatus.FAIL, true);
        }



    }
    
    
    public static void tcCaseManagementCMGT150() throws Exception {

        try {


            String accountNumber = dataObj.get("AccountNumber");
            String notesText = dataObj.get("Notes");
            String descriptionText = dataObj.get("Description");
            String queueValue = dataObj.get("Queue");
            String subQueueValue = dataObj.get("SubQueue");
            String priority = dataObj.get("SR Priority");
            String desc="";
            String activityDepartment="";
            String employeeDepartment="";

            CommonLibrary.clickSiteMap();

            FunctionLibrary.objDriver.findElement(By.xpath("//input[@id='sitemapFilterInput']")).sendKeys("Activity Templates");
            FunctionLibrary.clickObject(activityTemplate, "", "Clicking on activity template");
            FunctionLibrary.clickObject(activityTemplateQueryBtn, "", "Clicking on Qury button");
            FunctionLibrary.clickObject(activityTemplateQueryName, "", "Clicking on name");
            FunctionLibrary.setText(activityTemplateQueryNametxt, dataObj.get("ActivityName"), "Entering name");
            FunctionLibrary.clickObject(activityTemplateQueryGoBtn, "", "Clicking on Go button");

            int size = FunctionLibrary.objDriver.findElements(By.xpath("//table[@summary='Admin Premise List']/tbody/tr")).size();
            System.out.println("size:" + size);
            if (size > 1) {
                Thread.sleep(2000);
                FunctionLibrary.clickObject(activityTemplateDetailsTab, "", "Clicking on Activity template Details");
                int getrowNumberActivityTemplateList = FunctionLibrary.getRowNumberFromWebTable(activityTemplateDetailsTable, "PENDING", "get the row number of cash tranaction");
                System.out.println("GetrowNumber:" + getrowNumberActivityTemplateList);

                FunctionLibrary.verifyWebTableCellData(activityTemplateDetailsTable, getrowNumberActivityTemplateList, 3,
                        "PENDING", "Verifying Activity", true);

                activityDepartment = FunctionLibrary.getWebTableCellData(activityTemplateDetailsTable,getrowNumberActivityTemplateList,12,"get ServiceRequestID");

              /*  CommonLibrary.logoutSiebelApplicaiton();

                CommonLibrary.loginSiebelAppSecondUser(dataObj);*/
                String serviceRequestId1 = "";

                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathServiceRequestList
                        ,xpathServiceRequestTab,10,20);

                desc = "Clicking on Service request list";
                FunctionLibrary.clickObject(xpathServiceRequestList, "", desc);
                WebDriverWait waitforNeW = new WebDriverWait(FunctionLibrary.objDriver, 90);
                waitforNeW.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNewServiceRequestButton)));

                int dropdownlistCount = FunctionLibrary.getDropdownListCount(xpathView);

                System.out.println("dropdownlistCount:" + dropdownlistCount);
                if (dropdownlistCount > 1) {

                    FunctionLibrary.scrollDown("", "Scrolling Down");

                    desc = "Clicking on new SR button";
                    FunctionLibrary.clickObject(serviceRequestNewBtn, "", desc);

                    FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathAccountNumber,
                            "//input[@aria-label='Account Name']", 15, 30);

                    Thread.sleep(5000);


                    System.out.println("serviceRequestId1:" + serviceRequestId1);

                    FunctionLibrary.objDriver.findElement(By.xpath(xpathAccountNumber)).sendKeys(accountNumber);
                    FunctionLibrary.objDriver.findElement(By.xpath(xpathSRPriority)).clear();
                    FunctionLibrary.objDriver.findElement(By.xpath(xpathSRPriority)).sendKeys(priority);
                    FunctionLibrary.objDriver.findElement(By.xpath(xpathNotes)).sendKeys(notesText);
                    FunctionLibrary.objDriver.findElement(By.xpath(xpathDescription)).sendKeys(descriptionText);
                    serviceRequestId1 = FunctionLibrary.objDriver.findElement(By.xpath("//label")).getText();
                    FunctionLibrary.setText(Queue, queueValue, "Clicking on queue");
                    FunctionLibrary.setText(SubQueue, subQueueValue, "Clicking on subqueue");


                    FunctionLibrary.scrollUp("", "Scrolling up");




                    desc = "Saving the service request";

                    FunctionLibrary.objDriver.findElement(By.xpath("//button[contains(@aria-label,'Service Requests:Save')]/span[text()='Save']")).click();
                    WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 90);
                    try {

                        wait.until(ExpectedConditions.alertIsPresent());
                        Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                        alert.accept();

                    }
                    catch (Exception e) {
                        //exception handling
                    }

                    CommonLibrary.clickSiteMap();

                    FunctionLibrary.objDriver.findElement(By.xpath("//input[@id='sitemapFilterInput']")).sendKeys("Administration - User");
                    FunctionLibrary.objDriver.findElement(By.xpath("//li//a[text()='Administration - User']")).click();

                    Thread.sleep(5000);

                    FunctionLibrary.objDriver.findElement(By.xpath("//a[text()='Employees']")).click();
                   // Thread.sleep(5000);
                   waitforNeW.until(ExpectedConditions.elementToBeClickable(By.xpath(employeeSearchComboBoxIcon)));
                   // FunctionLibrary.clickObject(employeeSearchComboBoxIcon, "", "Click on comboboxIcon");
                    FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(selectLoginName,
                            employeeSearchComboBoxIcon, 5, 20);

                    JavascriptExecutor js = (JavascriptExecutor) FunctionLibrary.objDriver;
                    js.executeScript("return document.readyState").toString().equals("complete");
                    FunctionLibrary.clickObject(selectLoginName, "", "select Login Name");
                    //Set User Name on the Login Text Box
                    FunctionLibrary.setText("//input[@aria-labelledby='QuerySrchSpec_Label']",dataObj.get("First LoginUserName"), "Enter Login Name");
                    FunctionLibrary.clickObject("(//button[@aria-label='Employees:Go'])[2]", "", "Click on GO");
                    Thread.sleep(4000);
                    employeeDepartment=FunctionLibrary.objDriver.findElement(By.xpath("//input[@aria-label='Department']")).getAttribute("value");

                    FunctionLibrary.scrollDown("","");
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Checking the supervisor department", LogStatus.INFO, true);

                  /*  CommonLibrary.logoutSiebelApplicaiton();
                    CommonLibrary.loginSiebelAppFirstUser(dataObj);*/
                    FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathServiceRequestList
                            ,xpathServiceRequestTab,2,40);

                    desc = "Clicking on Service request list";
                    FunctionLibrary.clickObject(xpathServiceRequestList, "", desc);


                    WebDriverWait waitforNeW1 = new WebDriverWait(FunctionLibrary.objDriver, 90);
                    waitforNeW1.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNewServiceRequestButton)));

                    int dropdownlistCountforsupervisor = FunctionLibrary.getDropdownListCount(xpathView);

                    System.out.println("dropdownlistCount:" + dropdownlistCountforsupervisor);
                    if (dropdownlistCountforsupervisor>1 ) {

                        FunctionLibrary.selectDropDownList(xpathView, "All Service Requests", "Selecting All Service Requests from dropdown");
                        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//li[text()='AGENCY']",
                                "//input[@aria-label='Queue']//following::span[1]", 5, 60);

                        desc = "Clicking query button";
                        FunctionLibrary.clickObject(xpathQuery, "", desc);
                        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathServiceRequestFieldEnable,
                                xpathQuery, 15, 60);
                        desc = "Clicking on service request";
                        FunctionLibrary.clickObject(xpathServiceRequestFieldEnable, "", desc);

                        desc = "Entering service request";
                        FunctionLibrary.setText(xpathServiceRequestTextField, serviceRequestId1, desc);

                        desc = "Clicking on Go button";
                        FunctionLibrary.clickObject(xpathServiceRequestGoButton, "", desc);

                        Thread.sleep(4000);

                        FunctionLibrary.setText(srOwnertxt, dataObj.get("First LoginUserName"), "Entering owner");

                        desc = "Saving the service request";
                        FunctionLibrary.clickObject(xpathServiceRequestSaveButton, "", desc);
                        Thread.sleep(3000);


                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Assigned Service request SR#: " + serviceRequestId1 + " with Account Number " + accountNumber, LogStatus.INFO, true);

                        if (!activityDepartment.equals(employeeDepartment)) {
                            FunctionLibrary.clickObject(activityTab, "", "Clicking on activity tab");
                            //    FunctionLibrary.clickObject(activityStatus, "", desc);

                            FunctionLibrary.clickObject("//table[@summary='Order Dashboard Details']//tr/td[contains(text(), 'PENDING')]","","");
                            FunctionLibrary.objDriver.findElement(By.xpath(activityStatustxt)).clear();

                            FunctionLibrary.setText(activityStatustxt,"APPROVED","");
                            Thread.sleep(1000);
                            FunctionLibrary.clickObject("//table[@summary='Order Dashboard Details']//tr/td[contains(text(), 'APPROVED')]","","");
                            FunctionLibrary.objDriver.findElement(By.xpath(activityStatustxt)).clear();
                            FunctionLibrary.setText(activityStatustxt,"Completed","");

                         String actualProcessAlert=FunctionLibrary.objDriver.findElement(By.xpath("//div[@id='_sweview_popup']")).getText();
                         /*   String actualProcessAlert="";
                            try {

                                wait.until(ExpectedConditions.alertIsPresent());
                                Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                                actualProcessAlert=alert.getText();
                                alert.accept();
                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Verifying alert"+actualProcessAlert,LogStatus.PASS,true);

                            }
                            catch (Exception e) {
                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error", LogStatus.FAIL, true);
                            }
                        */



                            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Verifying alert"+actualProcessAlert,LogStatus.PASS,true);
                            FunctionLibrary.objDriver.findElement(By.xpath(okButton)).click();


                        }
                        else
                        {
                            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Activity department and employee department are same", LogStatus.FAIL, true);
                        }
                    }

                    else
                    {
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Logged in user is not Supervisor", LogStatus.FAIL, true);
                    }



                } else {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Logged in user is not CSR", LogStatus.FAIL, true);
                }


            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Activity Not FOund", LogStatus.FAIL, true);
            }
        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown error occurred:" + e, LogStatus.FAIL, true);
        }
    }
    /*
       Automated for Test case:CMGT-150
       Test case desc: Department and Supervisor check flag is enabled;
       Supervisor department is different from activity department; Supervisor approves the pending activity
     */
   /* public static void tcCaseManagementCMGT150() throws Exception {

        try {


            String accountNumber = dataObj.get("AccountNumber");
            String notesText = dataObj.get("Notes");
            String descriptionText = dataObj.get("Description");
            String queueValue = dataObj.get("Queue");
            String subQueueValue = dataObj.get("SubQueue");
            String priority = dataObj.get("SR Priority");
            String desc="";
            String activityDepartment="";
            String employeeDepartment="";

            CommonLibrary.clickSiteMap();

            FunctionLibrary.objDriver.findElement(By.xpath("//input[@id='sitemapFilterInput']")).sendKeys("Activity Templates");
            FunctionLibrary.clickObject(activityTemplate, "", "Clicking on activity template");
            FunctionLibrary.clickObject(activityTemplateQueryBtn, "", "Clicking on Qury button");
            FunctionLibrary.clickObject(activityTemplateQueryName, "", "Clicking on name");
            FunctionLibrary.setText(activityTemplateQueryNametxt, dataObj.get("ActivityName"), "Entering name");
            FunctionLibrary.clickObject(activityTemplateQueryGoBtn, "", "Clicking on Go button");

            int size = FunctionLibrary.objDriver.findElements(By.xpath("//table[@summary='Admin Premise List']/tbody/tr")).size();
            System.out.println("size:" + size);
            if (size > 1) {
                Thread.sleep(2000);
                FunctionLibrary.clickObject(activityTemplateDetailsTab, "", "Clicking on Activity template Details");
                int getrowNumberActivityTemplateList = FunctionLibrary.getRowNumberFromWebTable(activityTemplateDetailsTable, "PENDING", "get the row number of cash tranaction");
                System.out.println("GetrowNumber:" + getrowNumberActivityTemplateList);

                FunctionLibrary.verifyWebTableCellData(activityTemplateDetailsTable, getrowNumberActivityTemplateList, 3,
                        "PENDING", "Verifying Activity", true);

                activityDepartment = FunctionLibrary.getWebTableCellData(activityTemplateDetailsTable,getrowNumberActivityTemplateList,12,"get ServiceRequestID");

                CommonLibrary.logoutSiebelApplicaiton();

                CommonLibrary.loginSiebelAppSecondUser(dataObj);
                String serviceRequestId1 = "";

                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathServiceRequestList
                        ,xpathServiceRequestTab,10,20);

                desc = "Clicking on Service request list";
                FunctionLibrary.clickObject(xpathServiceRequestList, "", desc);
                WebDriverWait waitforNeW = new WebDriverWait(FunctionLibrary.objDriver, 90);
                waitforNeW.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNewServiceRequestButton)));

                int dropdownlistCount = FunctionLibrary.getDropdownListCount(xpathView);

                System.out.println("dropdownlistCount:" + dropdownlistCount);
                if (dropdownlistCount > 1) {

                    FunctionLibrary.scrollDown("", "Scrolling Down");

                    desc = "Clicking on new SR button";
                    FunctionLibrary.clickObject(serviceRequestNewBtn, "", desc);

                    FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathAccountNumber,
                            "//input[@aria-label='Account Name']", 15, 30);

                    Thread.sleep(5000);


                    System.out.println("serviceRequestId1:" + serviceRequestId1);

                    FunctionLibrary.objDriver.findElement(By.xpath(xpathAccountNumber)).sendKeys(accountNumber);
                    FunctionLibrary.objDriver.findElement(By.xpath(xpathSRPriority)).clear();
                    FunctionLibrary.objDriver.findElement(By.xpath(xpathSRPriority)).sendKeys(priority);
                    FunctionLibrary.objDriver.findElement(By.xpath(xpathNotes)).sendKeys(notesText);
                    FunctionLibrary.objDriver.findElement(By.xpath(xpathDescription)).sendKeys(descriptionText);
                    serviceRequestId1 = FunctionLibrary.objDriver.findElement(By.xpath("//label")).getText();
                    FunctionLibrary.setText(Queue, queueValue, "Clicking on queue");
                    FunctionLibrary.setText(SubQueue, subQueueValue, "Clicking on subqueue");


                    FunctionLibrary.scrollUp("", "Scrolling up");

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Adding the service request with SR#: " + serviceRequestId1 + " with Account Number " + accountNumber, LogStatus.INFO, true);


                    desc = "Saving the service request";

                    FunctionLibrary.objDriver.findElement(By.xpath("//button[contains(@aria-label,'Service Requests:Save')]/span[text()='Save']")).click();
                    WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 90);
                    try {

                        wait.until(ExpectedConditions.alertIsPresent());
                        Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                        alert.accept();

                    }
                    catch (Exception e) {
                        //exception handling
                    }

                    CommonLibrary.clickSiteMap();

                    FunctionLibrary.objDriver.findElement(By.xpath("//input[@id='sitemapFilterInput']")).sendKeys("Administration - User");
                    FunctionLibrary.objDriver.findElement(By.xpath("//li//a[text()='Administration - User']")).click();

                    Thread.sleep(5000);

                    FunctionLibrary.objDriver.findElement(By.xpath("//a[text()='Employees']")).click();
                   // Thread.sleep(5000);
                   waitforNeW.until(ExpectedConditions.elementToBeClickable(By.xpath(employeeSearchComboBoxIcon)));
                   // FunctionLibrary.clickObject(employeeSearchComboBoxIcon, "", "Click on comboboxIcon");
                    FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(selectLoginName,
                            employeeSearchComboBoxIcon, 5, 20);

                    JavascriptExecutor js = (JavascriptExecutor) FunctionLibrary.objDriver;
                    js.executeScript("return document.readyState").toString().equals("complete");
                    FunctionLibrary.clickObject(selectLoginName, "", "select Login Name");
                    //Set User Name on the Login Text Box
                    FunctionLibrary.setText("//input[@aria-labelledby='QuerySrchSpec_Label']",dataObj.get("First LoginUserName"), "Enter Login Name");
                    FunctionLibrary.clickObject("(//button[@aria-label='Employees:Go'])[2]", "", "Click on GO");
                    Thread.sleep(4000);
                    employeeDepartment=FunctionLibrary.objDriver.findElement(By.xpath("//input[@aria-label='Department']")).getAttribute("value");

                    FunctionLibrary.scrollDown("","");
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Checking the supervisor department", LogStatus.INFO, true);

                    CommonLibrary.logoutSiebelApplicaiton();
                    CommonLibrary.loginSiebelAppFirstUser(dataObj);
                    FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathServiceRequestList
                            ,xpathServiceRequestTab,2,40);

                    desc = "Clicking on Service request list";
                    FunctionLibrary.clickObject(xpathServiceRequestList, "", desc);


                    WebDriverWait waitforNeW1 = new WebDriverWait(FunctionLibrary.objDriver, 90);
                    waitforNeW1.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNewServiceRequestButton)));

                    int dropdownlistCountforsupervisor = FunctionLibrary.getDropdownListCount(xpathView);

                    System.out.println("dropdownlistCount:" + dropdownlistCountforsupervisor);
                    if (dropdownlistCountforsupervisor>1 ) {

                        FunctionLibrary.selectDropDownList(xpathView, "All Service Requests", "Selecting All Service Requests from dropdown");
                        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//li[text()='AGENCY']",
                                "//input[@aria-label='Queue']//following::span[1]", 5, 60);

                        desc = "Clicking query button";
                        FunctionLibrary.clickObject(xpathQuery, "", desc);
                        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathServiceRequestFieldEnable,
                                xpathQuery, 15, 60);
                        desc = "Clicking on service request";
                        FunctionLibrary.clickObject(xpathServiceRequestFieldEnable, "", desc);

                        desc = "Entering service request";
                        FunctionLibrary.setText(xpathServiceRequestTextField, serviceRequestId1, desc);

                        desc = "Clicking on Go button";
                        FunctionLibrary.clickObject(xpathServiceRequestGoButton, "", desc);

                        Thread.sleep(4000);

                        FunctionLibrary.setText(srOwnertxt, dataObj.get("First LoginUserName"), "Entering owner");

                        desc = "Saving the service request";
                        FunctionLibrary.clickObject(xpathServiceRequestSaveButton, "", desc);
                        Thread.sleep(3000);

                        if (!activityDepartment.equals(employeeDepartment)) {
                            FunctionLibrary.clickObject(activityTab, "", "Clicking on activity tab");
                            //    FunctionLibrary.clickObject(activityStatus, "", desc);

                            FunctionLibrary.clickObject("//table[@summary='Order Dashboard Details']//tr/td[contains(text(), 'PENDING')]","","");
                            FunctionLibrary.objDriver.findElement(By.xpath(activityStatustxt)).clear();

                            FunctionLibrary.setText(activityStatustxt,"APPROVED","");
                            Thread.sleep(1000);
                            FunctionLibrary.clickObject("//table[@summary='Order Dashboard Details']//tr/td[contains(text(), 'APPROVED')]","","");
                            FunctionLibrary.objDriver.findElement(By.xpath(activityStatustxt)).clear();
                            FunctionLibrary.setText(activityStatustxt,"Completed","");

                         String actualProcessAlert=FunctionLibrary.objDriver.findElement(By.xpath("//div[@id='_sweview_popup']")).getText();
                            String actualProcessAlert="";
                            try {

                                wait.until(ExpectedConditions.alertIsPresent());
                                Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                                actualProcessAlert=alert.getText();
                                alert.accept();
                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Verifying alert"+actualProcessAlert,LogStatus.PASS,true);

                            }
                            catch (Exception e) {
                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error", LogStatus.FAIL, true);
                            }
                        



                            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Verifying alert"+actualProcessAlert,LogStatus.PASS,true);
                            FunctionLibrary.objDriver.findElement(By.xpath(okButton)).click();


                        }
                        else
                        {
                            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Activity department and employee department are same", LogStatus.FAIL, true);
                        }
                    }

                    else
                    {
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Logged in user is not Supervisor", LogStatus.FAIL, true);
                    }



                } else {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Logged in user is not CSR", LogStatus.FAIL, true);
                }


            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Activity Not FOund", LogStatus.FAIL, true);
            }
        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown error occurred:" + e, LogStatus.FAIL, true);
        }
    }
*/
    /*
       Automated for Test case:CMGT-059
       Test case desc: Copy details from a
       closed SR and create a new SR, i.e, new SR is cloned from closed SR for ISP accounts
     */
    public static void tcCaseManagementCMGT059() throws Exception {
        String desc;
        try {
           /* desc = "Clicking on service Request tab";
            FunctionLibrary.clickObject(xpathServiceRequestTab, "", desc);*/

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathServiceRequestList
                    ,xpathServiceRequestTab,2,20);

            desc = "Clicking on Service request list";
            FunctionLibrary.clickObject(xpathServiceRequestList, "", desc);

            WebDriverWait waitforNeW = new WebDriverWait(FunctionLibrary.objDriver, 90);
            waitforNeW.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNewServiceRequestButton)));

            int dropdownlistCount=FunctionLibrary.getDropdownListCount(xpathView);

            System.out.println("dropdownlistCount:"+dropdownlistCount);
            if(dropdownlistCount>1) {


                FunctionLibrary.selectDropDownList(xpathView, "All Service Requests", "Selecting All Service Requests from dropdown");
                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//li[text()='AGENCY']",
                        "//input[@aria-label='Queue']//following::span[1]", 15, 60);
                desc = "Clicking query button";
                //  FunctionLibrary.clickObject(xpathQuery, "", desc);

                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//button[@aria-label='Service Requests:Query Assistant']", xpathQuery, 15, 60);
                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//li[text()='AGENCY']",
                        "//input[@aria-label='Queue']//following::span[1]", 15, 45);
                desc = "Clicking on status";
                //  FunctionLibrary.clickObject(xpathStatus, "", desc);

                desc = "Entering status";
                FunctionLibrary.setText(srStatus, "Closed", desc);

                desc = "Clicking on closure code";
                //  FunctionLibrary.clickObject(xpathClosurecode, "", desc);

                desc = "Entering closure code";
                FunctionLibrary.setText(xpathClosureText, "COMPLETED", desc);


                desc = "Clicking on Go button";
                FunctionLibrary.clickObject(xpathServiceRequestGoButton, "", desc);



                WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 120);

                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@title='Service Requests:Copy Case']")));


                int serivicerequestlist = FunctionLibrary.getWebTableRowCount(serviceRequestListTable, "", "Service Request List");
                System.out.println("serive:++++++++" + serivicerequestlist);
                if (serivicerequestlist > 1) {
                    desc = "Clicking on copy case button";
                    //FunctionLibrary.clickObject(copyCaseBtn, "", desc);
                    FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//button[@title='Service Requests:Copy Case' and @disabled='disabled']",
                            copyCaseBtn, 5, 20);

                    desc = "Getting service request from table";
                    String serviceRequestNumber = FunctionLibrary.getWebTableCellData(serviceRequestListTable, 1, 2, desc);
                    System.out.println("Service request number:" + serviceRequestNumber);
                    FunctionLibrary.objDriver.findElement(By.xpath("//input[@aria-label='Notes']")).sendKeys(dataObj.get("Notes"));


                    desc = "Clicking on Save btn";
                    FunctionLibrary.clickObject(saveBtn, "", desc);
                    Thread.sleep(5000);
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Cloned Serivice request:" + serviceRequestNumber, LogStatus.INFO, false);

                    FunctionLibrary.scrollDown("","");
                    String statusText=FunctionLibrary.objDriver.findElement(By.xpath(srStatus)).getAttribute("value");
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Veryfying status:"+statusText,LogStatus.PASS,true);

                    String substatusText=FunctionLibrary.objDriver.findElement(By.xpath("//input[@aria-label='SR Sub Status']")).getAttribute("value");
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Veryfying Substatus:"+substatusText,LogStatus.PASS,true);


                  /*  desc = "Clicking on service request history tab";
                    FunctionLibrary.clickObject(serviceRequestHistory, "", desc);*/


                    try
                    {
                        FunctionLibrary.selectDropDownList("//select[@aria-label='Third Level View Bar']","Service Request History","");
                    }
                    catch(Exception e)
                    {

                        FunctionLibrary.clickObject(xpathServiceRequestHistoryTab, "", "");
                    }

                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathServiceRequestHistoryGoButton)));


                    int vectorSerivicerequestHistoryCount = FunctionLibrary.getWebTableRowCount(vectorServiceRequestHistory, "", "Service Request List");
                    System.out.println("vectorSerivicerequestHistoryCount: " + vectorSerivicerequestHistoryCount);

                    if (vectorSerivicerequestHistoryCount > 1) {

                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Serivice requests History", LogStatus.PASS, true);
                    } else {
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Cloned Serivice are not available", LogStatus.FAIL, true);
                    }

                } else {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Serivice requests are not available", LogStatus.FAIL, true);
                }
            }
            else
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Logged in user is not supervisor", LogStatus.FAIL, true);
            }

        }
        catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to"+e.getMessage() ,LogStatus.INFO,true);
        }

    }
    /*
    Automated for Test case:CMGT-017
    Test case desc: SR creation for adjustments
  * */
    public static void tcCaseManagementCMGT017() throws Exception {
        try {

            String acctNumber=dataObj.get("AccountNumber");

            CommonLibrary.clickSiteMap();
            FunctionLibrary.scrollDowntoElement(internalControls, "Scroll down to Internal Controls link");
            FunctionLibrary.clickObject(internalControls, "", "Click on internal controls link");

            FunctionLibrary.clickObject(internalControlsQueryBtn,"","Clicking on Query button");
          /*  FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
                    (internalControlThresholdResponsibility, internalControlsQueryBtn,
                            10, 30);*/

            FunctionLibrary.clickObject(internalControlThresholdResponsibility,"","Clicking on Responsibilty");
            FunctionLibrary.setText(internalControlThresholdResponsibilitytxtbox,dataObj.get("Responsibility"),"Entering Responsibilty");

            FunctionLibrary.clickObject(intenalControlsQueryPaytype,"","Clicking on pay type");
            FunctionLibrary.setText(intenalControlsQueryPaytypetxtbox,dataObj.get("Paytype"),"Entering Pay Type");
            FunctionLibrary.clickObject(internalControlsQueryTransactiontype,"","Clicking on transaction type");
            FunctionLibrary.setText(internalControlsQueryTransactiontypetxtbox,dataObj.get("Transactiontype"),"Entering transaction Type");
            FunctionLibrary.clickObject(internalControlQueryGobtn,"","Clicking on Go button");
            Thread.sleep(5000);

            int size=FunctionLibrary.objDriver.findElements(By.xpath("//table[@summary='Internal Control Threshold List']/tbody/tr")).size();
            System.out.println("size:"+size);
            String maximumamount="";
            if(size>1) {
                int getrowNumberInternalThresholdTable = FunctionLibrary.getRowNumberFromWebTable(internalControlsThresholdTable, dataObj.get("Paytype"), "get the row number of cash tranaction");

                maximumamount = FunctionLibrary.getWebTableCellData(internalControlsThresholdTable, getrowNumberInternalThresholdTable, 9, "Getting value");
                System.out.println("Approval Amount:" + maximumamount);


                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                        "Internal Threshold settings", LogStatus.INFO, true);
            }
            else {

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                        "Internal Threshold not set", LogStatus.FAIL, true);


            }

            CommonLibrary.searchForAccount(dataObj.get("AccountNumber"));

            FunctionLibrary.waitForObject = new WebDriverWait(FunctionLibrary.objDriver, 90);
            FunctionLibrary.waitForObject.until(ExpectedConditions.presenceOfElementLocated(By.linkText(dataObj.get("AccountNumber"))));

            FunctionLibrary.clickObject(CRMfinancialsTab, "", "Clicking on financials tab");

            WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 120);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(adjustmentTab)));

            FunctionLibrary.clickObject(adjustmentTab,"","clicking on adjustment tab");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(adjustmentNewbtn)));

            //double addedAmountfromExcel = Double.parseDouble(dataObj.get("GreaterApprovalAmount"));
            maximumamount=maximumamount.replace(",","");
            Double unitPriceAmount = Double.valueOf(maximumamount);
            Double addedAmount =unitPriceAmount+getValueforSr();
            String adjustedAmountToParse=Double.toString(addedAmount);

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Doing adjustment for  the amount: " + adjustedAmountToParse + " with sub category: "
                    +dataObj.get("Subcategory"), LogStatus.INFO, true);
            FunctionLibrary.clickObject(adjustmentNewbtn,"","Clicking on new button");
            Thread.sleep(4000);
            // FunctionLibrary.setText(adjustmentCategorytxtbox,"TOLDEPOSIT","Entering category");
            FunctionLibrary.setText(adjustmentSubCategorytxtbox,dataObj.get("Subcategory"),"Entering category");
            // FunctionLibrary.setText(adjustmentUnitpricetxtbox,adjustedAmountToParse,"Entering price");
            FunctionLibrary.setText(adjustmentUnitpricetxtbox,adjustedAmountToParse,"Entering price");
            System.out.print("amount dynamic:"+ getValueforSr());
            FunctionLibrary.clickObject(adjustmentSavebtn,"","Clicking on Save");
            Thread.sleep(4000);
            FunctionLibrary.clickObject(adjustmentBtn,"","Clicking on Adjust");
            Thread.sleep(6000);
           /* FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(navigateBtn,
                    adjustmentBtn, 20, 40);*/


            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(navigateBtn)));

            DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
            Date date = new Date();
            String expectedDate=dateFormat.format(date);

            int rownumberwithServiceRequestone=FunctionLibrary.getRowNumberFromWebTable(serviceRequestListTable,"ADJUSTMENT","");

            String xpathSRTbl=xpathserviceReqListTable;
            String ServiceRequestID = FunctionLibrary.getWebTableCellData(xpathSRTbl,rownumberwithServiceRequestone,2,"get ServiceRequestID");

            String icStatus =FunctionLibrary.objDriver.findElement(By.xpath("//td[@title='ESCALATED']/span[1]/img")).getAttribute("title");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Service request created with SR#: " + ServiceRequestID, LogStatus.PASS, true);


            String dateOpened = FunctionLibrary.getWebTableCellData(xpathSRTbl,rownumberwithServiceRequestone,3,"get date");
            String[] dateOpenedSplit=dateOpened.split(" ");

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"<b>Verifying Date Opened</b> <br> Expected  :<b>  "+expectedDate+"  </b>Actual:<b> "+dateOpenedSplit[0]+" </b ",LogStatus.PASS,true);


            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "IC Status: " + icStatus, LogStatus.PASS, true);
            if(get_S_SRV_REQ_Details(ServiceRequestID))
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"<b>Verifying in S_SRV_REQ_Details</b> <br> Expected  :<b>  YES  </b>Actual:<b> YES </b ",LogStatus.PASS,false);

            }
            else
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"<b>Verifying in S_SRV_REQ_Details</b> <br> Expected  :<b>  YES  </b>Actual:<b> NO </b ",LogStatus.PASS,false);
            }

            FunctionLibrary.objDriver.findElement(By.xpath(xpathMoreInfo)).click();

            String owner=FunctionLibrary.objDriver.findElement(By.xpath("//input[@aria-label='Owner']")).getAttribute("value");
            if(owner.equals(""))
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"verifying unassigned owner",LogStatus.PASS,true);
            }
            else
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"verifying Owner:Unassigned",LogStatus.FAIL,true);
            }

            String statusText=FunctionLibrary.objDriver.findElement(By.xpath(srStatus)).getAttribute("value");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"verifying status:"+statusText,LogStatus.PASS,true);

            String substatusText=FunctionLibrary.objDriver.findElement(By.xpath("//input[@aria-label='SR Sub Status']")).getAttribute("value");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"verifying Substatus:"+substatusText,LogStatus.PASS,true);

            CommonLibrary.searchForAccount(dataObj.get("AccountNumber"));

           /* FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
                    ("//a[text()='Non Financials']", "//a[text()='History']",
                            15, 45);*/
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='History']")));

            FunctionLibrary.objDriver.findElement(By.xpath("//a[text()='History']")).click();

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Non Financials']")));


            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//table[@summary='Activities']", "//a[text()='Non Financials']", 5, 30);


            String activitiestable = "//table[@summary='Activities']";
            int rowNumberOne = FunctionLibrary.getRowNumberFromWebTable(activitiestable, "ADD", "Get the row number");
            System.out.println(rowNumberOne);
            FunctionLibrary.verifyWebTableCellData(activitiestable, rowNumberOne, 5, "Add Transfer",
                    "verifying in Non-Financial History", true);



        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown error occurred:", LogStatus.FAIL, true);
        }
    }

    public static void tcCaseManagementCMGT156() throws Exception {

        try {
            CommonLibrary.clickSiteMap();

            FunctionLibrary.objDriver.findElement(By.xpath("//input[@id='sitemapFilterInput']")).sendKeys("internal Controls");

            FunctionLibrary.clickObject(internalControls, "", "Click on internal controls link");

            FunctionLibrary.clickObject(internalControlsQueryBtn, "", "Clicking on Query button");


            FunctionLibrary.clickObject(internalControlThresholdResponsibility, "", "Clicking on Responsibilty");
            FunctionLibrary.setText(internalControlThresholdResponsibilitytxtbox, dataObj.get("Responsibility"), "Entering Responsibilty");

            FunctionLibrary.clickObject(intenalControlsQueryPaytype, "", "Clicking on pay type");
            FunctionLibrary.setText(intenalControlsQueryPaytypetxtbox, dataObj.get("Paytype"), "Entering Pay Type");
            FunctionLibrary.clickObject(internalControlsQueryTransactiontype, "", "Clicking on transaction type");
            FunctionLibrary.setText(internalControlsQueryTransactiontypetxtbox, dataObj.get("Transactiontype"), "Entering transaction Type");
            FunctionLibrary.clickObject(internalControlQueryGobtn, "", "Clicking on Go button");
            Thread.sleep(5000);

            int size = FunctionLibrary.objDriver.findElements(By.xpath("//table[@summary='Internal Control Threshold List']/tbody/tr")).size();
            System.out.print("size:" + size);
            String initiationAmount = "";
            if (size > 1) {
                int getrowNumberInternalThresholdTable = FunctionLibrary.getRowNumberFromWebTable(internalControlsThresholdTable, dataObj.get("Paytype"), "get the row number of cash tranaction");

                initiationAmount = FunctionLibrary.getWebTableCellData(internalControlsThresholdTable, getrowNumberInternalThresholdTable, 8, "Getting value");
                System.out.print("Approval Amount:" + initiationAmount);

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                        "Internal Threshold settings", LogStatus.INFO, true);
            } else {

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                        "Internal Threshold not set", LogStatus.FAIL, true);


            }
            CommonLibrary.searchForAccount(dataObj.get("AccountNumber"));

            FunctionLibrary.objDriver.findElement(By.xpath("//a[text()='History']")).click();
            WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 90);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@summary='Tolls & EPS Transactions List']")));
            boolean plusIconforTranaction;
            String laneTransactionId = "";
            do {
                plusIconforTranaction = false;
               int tranactionCount = FunctionLibrary.objDriver.findElements(By.xpath("//table[@summary='Tolls & EPS Transactions List']/tbody/tr")).size();
                System.out.print("Devicecount:" + tranactionCount);

                if (tranactionCount >= 2) {
                    for (int i =2; i <=tranactionCount; i++) {
                    //  laneTransactionId = FunctionLibrary.getWebTableCellData("//table[@summary='Tolls & EPS Transactions List']", i, 12, "get ServiceRequestID");
                      //  FunctionLibrary.objDriver.findElement(By.xpath("//table[@summary='Tolls & EPS Transactions List']/tbody/tr[" + i + "]")).click();

                        FunctionLibrary.objDriver.findElement(By.xpath("//table[@summary='Tolls & EPS Transactions List']/tbody/tr[" + i + "]/td[16]")).click();

                        //Thread.sleep(2000);

                        if (FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='Marked_for_Dispute']")).getAttribute("aria-checked").equals("false")) {

                            System.out.print("checkbox clicking");
                         //   FunctionLibrary.objDriver.findElement(By.xpath("//label[@id='Marked_for_Dispute']/span[1]")).click();

                            FunctionLibrary.objDriver.findElement(By.xpath("//table[@summary='Tolls & EPS Transactions List']/tbody/tr[" + i + "]/td[16]/span")).click();
                            FunctionLibrary.objDriver.findElement(By.xpath("//button[@title='Tolls & EPS Transactions List:Dispute']")).click();
                            Thread.sleep(4000);
                            try {
                               String disputedText = FunctionLibrary.objDriver.findElement(By.xpath("//div[@id='_sweview_popup']")).getText();
                                //Thread.sleep(2000);

                           /*   String disputedText = "";
                                System.out.println("disputed Text:"+disputedText);

                                Thread.sleep(2000);


                                try {

                                    // WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 90);
                                    wait.until(ExpectedConditions.alertIsPresent());
                                    Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                                    disputedText=alert.getText();
                                    alert.accept();

                                } catch (Exception e) {
                                    //exception handling
                                }*/

                                if (disputedText.equals("This transaction can not be processed as there a OPEN service request for this toll(SBL-EXL-00151)") || disputedText.equals("Total Disputed Amount cannot exceed the Toll Amount(SBL-EXL-00151)")) {
                                    FunctionLibrary.objDriver.findElement(By.xpath(okButton)).click();
                                    if (i == tranactionCount) {
                                        System.out.println("NEXT:"+FunctionLibrary.objDriver.findElement(By.xpath("(//span[@class='siebui-row-counter'])[1]")).getText());
                                        Thread.sleep(2000);
                                        if (FunctionLibrary.objDriver.findElement(By.xpath("(//span[@class='siebui-row-counter'])[1]")).getText().contains("+")) {
                                            Thread.sleep(2000);
                                            FunctionLibrary.scrollDown("","");
                                            FunctionLibrary.objDriver.findElement(By.xpath("//span[@title='Next record set']")).click();
                                            Thread.sleep(4000);
                                            plusIconforTranaction=true;
                                        }
                                       /* else
                                        {
                                            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Tranaction not available", LogStatus.FAIL, true);

                                        }*/
                                    }
                                    continue;
                                } else
                                    break;
                            } catch (Exception e) {
                                break;

                            }


                        } else {
                            //ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Disputed already", LogStatus.FAIL, true);

                        }


                    }
                } else {
                    // ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Tranaction not available", LogStatus.FAIL, true);
                }


            }while (plusIconforTranaction == true) ;


            if(FunctionLibrary.objDriver.findElements(By.xpath("//table[@summary='Vector Account Toll Dispute Recievables']")).size()>0) {
                FunctionLibrary.objDriver.findElement(By.xpath("//table[@summary='Vector Account Toll Dispute Recievables']//tr[2]/td[6]")).click();
                String unitPrice = FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='Unit_Price']")).getAttribute("value");

                FunctionLibrary.objDriver.findElement(By.xpath("//table[@summary='Vector Account Toll Dispute Recievables']//tr[2]/td[4]")).click();
                 laneTransactionId = FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='Toll_Transaction_Id']")).getAttribute("value");
                System.out.print("laneID:" + laneTransactionId);
                System.out.print("unit:" + unitPrice);


                unitPrice = unitPrice.replace("$", "");

                Double initiationAmountDouble = Double.parseDouble(initiationAmount);
                Double amount = Double.parseDouble(unitPrice);

                if (amount > initiationAmountDouble) {
                    FunctionLibrary.objDriver.findElement(By.xpath("//table[@summary='Vector Account Toll Dispute Recievables']//tr[2]/td[8]")).click();
                    FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='Reason_Code']")).sendKeys(dataObj.get("DisputeReason"));
                    FunctionLibrary.objDriver.findElement(By.xpath("//button[@title='Vector Account Toll Dispute Recievables:Process']")).click();

                    WebDriverWait wait1 = new WebDriverWait(FunctionLibrary.objDriver, 90);
                    wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@summary='Service Request List']")));
                    int rownumberwithServiceRequestone = FunctionLibrary.getRowNumberFromWebTable(serviceRequestListTable, "TOLLDISPUTE", "");

                    String xpathSRTbl = "" + xpathserviceReqListTable;
                    String ServiceRequestID = FunctionLibrary.getWebTableCellData(xpathSRTbl, rownumberwithServiceRequestone, 2, "get ServiceRequestID");
                    String icStatus = FunctionLibrary.objDriver.findElement(By.xpath("//td[@title='ESCALATED']/span[1]/img")).getAttribute("title");
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Service request created with SR#: " + ServiceRequestID, LogStatus.PASS, true);

                    DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
                    Date date = new Date();
                    String expectedDate = dateFormat.format(date);
                    String dateOpened = FunctionLibrary.getWebTableCellData(xpathSRTbl, rownumberwithServiceRequestone, 3, "get date");
                    String[] dateOpenedSplit = dateOpened.split(" ");

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>Verifying Date Opened</b> <br> Expected  :<b>  " + expectedDate + "  </b>Actual:<b> " + dateOpenedSplit[0] + " </b ", LogStatus.PASS, true);
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "IC Status: " + icStatus, LogStatus.PASS, true);

                    if (get_S_SRV_REQ_Details(ServiceRequestID)) {
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>Verifying in S_SRV_REQ_Details</b> <br> Expected  :<b>  YES  </b>Actual:<b> YES </b ", LogStatus.PASS, false);

                    } else {
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>Verifying in S_SRV_REQ_Details</b> <br> Expected  :<b>  YES  </b>Actual:<b> NO </b ", LogStatus.PASS, false);
                    }

                 //   FunctionLibrary.objDriver.findElement(By.xpath("//a[text()='More Info']")).click();

                    FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathServiceReuestSearchField
                            ,"//a[text()='Service Requests Home']",30,60);
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathServiceReuestSearchField)));
                    FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                            "//li[text()='PAYMENT']",xpathServiceReuestSearchField,2,2);
                    FunctionLibrary.setText(xpathServiceReuestSearchField,ServiceRequestID,"Enter account number");
                    //   FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceReuestSearchField)).sendKeys(serviceRequestId1);

                    String desc = "Clicking on Service Request Search Go Button";
                    FunctionLibrary.clickObject(xpathServiceRequestSearchGo, "", desc);
                    FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                            "//li[text()='PAYMENT']",xpathServiceReuestSearchField,2,2);

                    String owner = FunctionLibrary.objDriver.findElement(By.xpath("//input[@aria-label='Owner']")).getAttribute("value");
                    if (owner.equals("")) {
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "verifying unassigned owner", LogStatus.PASS, true);
                    } else {
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "verifying Owner:Unassigned", LogStatus.FAIL, true);
                    }

                    String statusText = FunctionLibrary.objDriver.findElement(By.xpath("//input[@aria-label='SR Status']")).getAttribute("value");
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "verifying status:" + statusText, LogStatus.PASS, true);

                    String substatusText = FunctionLibrary.objDriver.findElement(By.xpath("//input[@aria-label='SR Sub Status']")).getAttribute("value");
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "verifying Substatus:" + substatusText, LogStatus.PASS, true);

                    CommonLibrary.searchForAccount(dataObj.get("AccountNumber"));
                    FunctionLibrary.objDriver.findElement(By.xpath("//a[text()='History']")).click();
                    WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 90);
                    wait2.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Non Financials']")));

                    FunctionLibrary.objDriver.findElement(By.xpath("//a[text()='Non Financials']")).click();
                    //FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//table[@summary='Activities']", "//a[text()='Non Financials']", 10, 40);
                    wait2.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@summary='Activities']")));
                    String activitiestable = "//table[@summary='Activities']";
                    int rowNumberOne = FunctionLibrary.getRowNumberFromWebTable(activitiestable, "SERVICE_REQUEST", "Get the row number");
                    System.out.println(rowNumberOne);
                    FunctionLibrary.verifyWebTableCellData(activitiestable, rowNumberOne, 5, "SR Created",
                            "verifying in Non-Financial History", true);
                    Thread.sleep(4000);

                    CommonLibrary.logoutSiebelApplicaiton();
                    CommonLibrary.loginSiebelAppSecondUser(dataObj);


                    FunctionLibrary.clickObject(xpathServiceRequestTab, "", "Clicking on service Request tab");

                    FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceReuestSearchField)).sendKeys(ServiceRequestID);


                    FunctionLibrary.clickObject(xpathServiceRequestSearchGo, "", "Clicking on Service Request Search Go Button");

                    Thread.sleep(10000);
                    FunctionLibrary.objDriver.findElement(By.xpath(ownerFieldSrch)).sendKeys(dataObj.get("Second LoginUserName"));

                    FunctionLibrary.clickObject(xpathServiceRequestSaveButton, "", "Saving the service request");


                 /*   FunctionLibrary.clickObject(escalationTab, "", "click on Escalation Tab");
                    FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(diputeApprove, escalationTab, 15, 30);*/



                    try
                    {
                        FunctionLibrary.selectDropDownList("//select[@aria-label='Third Level View Bar']","Escalation","");
                    }
                    catch(Exception e)
                    {

                        FunctionLibrary.clickObject(escalationTab, "", "");
                    }

                    FunctionLibrary.clickObject(diputeApprove, "", "click on approve");

/*
                    try {

                        WebDriverWait waitForProcess = new WebDriverWait(FunctionLibrary.objDriver,90);
                        waitForProcess.until(ExpectedConditions.alertIsPresent());
                        Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                        alert.accept();

                    }
                    catch (Exception e) {
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Unknown error",LogStatus.FAIL,true);
                    }*/


                    try {
                        String alertText = FunctionLibrary.objDriver.findElement(By.xpath("//div[@id='_sweview_popup']")).getText();
                        if (alertText.equals("Internal Control Threshold Master Data is missing.(SBL-EXL-00151)")) {
                            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown error", LogStatus.FAIL, true);
                        } else {
                            FunctionLibrary.objDriver.findElement(By.xpath(okButton)).click();
                        }
                    } catch (Exception e) {
                        //exception handling
                    }



                    WebDriverWait waitforServiceRequestList = new WebDriverWait(FunctionLibrary.objDriver, 60);
                    waitforServiceRequestList.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathserviceReqListTable)));

                    String actual = FunctionLibrary.objDriver.findElement(By.xpath("//td[@title='APPROVED']/span[1]/img")).getAttribute("title");
                    System.out.println("actual:" + actual);
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected :APPROVED <br> Actual:" + actual, LogStatus.PASS, true);

                    CommonLibrary.logoutSiebelApplicaiton();
                    CommonLibrary.loginSiebelAppFirstUser(dataObj);

                    FunctionLibrary.clickObject(xpathServiceRequestTab, "", "Clicking on service Request tab");

                    FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceReuestSearchField)).sendKeys(ServiceRequestID);


                    FunctionLibrary.clickObject(xpathServiceRequestSearchGo, "", "Clicking on Service Request Search Go Button");

                    Thread.sleep(10000);

                 /*   FunctionLibrary.clickObject(escalationTab, "", "click on Escalation Tab");
                    FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(diputeProcess, escalationTab, 5, 20);*/

                    try
                    {
                        FunctionLibrary.selectDropDownList("//select[@aria-label='Third Level View Bar']","Escalation","");
                    }
                    catch(Exception e)
                    {

                        FunctionLibrary.clickObject(escalationTab, "", "");
                    }
                    FunctionLibrary.clickObject(diputeProcess, "", "click on Process");


                    try {

                       // WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 90);
                        wait2.until(ExpectedConditions.alertIsPresent());
                        Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                        alert.accept();

                    } catch (Exception e) {
                        //exception handling
                    }


                    WebDriverWait waitforServiceRequestListAfterProcess = new WebDriverWait(FunctionLibrary.objDriver, 60);
                    waitforServiceRequestListAfterProcess.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathserviceReqListTable)));

                    String actualval = FunctionLibrary.objDriver.findElement(By.xpath("//td[@title='PROCESSED']/span[1]/img")).getAttribute("title");
                    System.out.println("actual:" + actual);
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying Sub status, Expected :PROCESSED <br> Actual:" + actualval, LogStatus.PASS, true);
                    String statusTextProcess = FunctionLibrary.objDriver.findElement(By.xpath("//td[@title='Closed']")).getAttribute("title");
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "verifying status after process:" + statusTextProcess, LogStatus.PASS, true);

                    Thread.sleep(2000);

                    String accNumber = dataObj.get("AccountNumber");
                    System.out.print("accountNo:"+accNumber);
                    if (get_CX_DISPUTE_TOLL_Details(laneTransactionId, accNumber)) {
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>Verifying in S_SRV_REQ_Details</b> <br> Expected  :<b>  YES  </b>Actual:<b> YES </b ", LogStatus.PASS, false);

                    } else {
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>Verifying in S_SRV_REQ_Details</b> <br> Expected  :<b>  YES  </b>Actual:<b> NO </b ", LogStatus.PASS, false);
                    }


                } else {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Initiation Amount is not less then the Unit price", LogStatus.FAIL, true);
                }
            }

            else
            {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Tranaction not available", LogStatus.FAIL, true);
            }


        }


        catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown error occurred", LogStatus.FAIL, true);
        }
    }

    public static void tcCaseManagementCMGT110() throws Exception {
        try
        {

            String desc = "";
            ReportLibrary.getPath();
            dataObj.get("ExecutionStatus");
            dataObj.get("Account Type");
            dataObj.get("AccountNumber");
            dataObj.get("Mode Type");

            CommonLibrary.clickSiteMap();
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@id='sitemapFilterInput']")).sendKeys("internal Controls");

            FunctionLibrary.clickObject(internalControls, "", "Click on internal controls link");

            FunctionLibrary.clickObject(internalControlsQueryBtn, "", "Clicking on Query button");


            FunctionLibrary.clickObject(internalControlThresholdResponsibility, "", "Clicking on Responsibilty");
            FunctionLibrary.setText(internalControlThresholdResponsibilitytxtbox, dataObj.get("Responsibility"), "Entering Responsibilty");

            FunctionLibrary.clickObject(intenalControlsQueryPaytype, "", "Clicking on pay type");
            FunctionLibrary.setText(intenalControlsQueryPaytypetxtbox, dataObj.get("Paytype"), "Entering Pay Type");
            FunctionLibrary.clickObject(internalControlsQueryTransactiontype, "", "Clicking on transaction type");
            FunctionLibrary.setText(internalControlsQueryTransactiontypetxtbox, dataObj.get("Transactiontype"), "Entering transaction Type");
            FunctionLibrary.clickObject(internalControlQueryGobtn, "", "Clicking on Go button");

            WebDriverWait wait1 = new WebDriverWait(FunctionLibrary.objDriver, 150);
            wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@title='Internal Control Threshold List:Query']")));

            int size = FunctionLibrary.objDriver.findElements(By.xpath("//table[@summary='Internal Control Threshold List']/tbody/tr")).size();
            System.out.println("size:" + size);
            String maxValue = "";
            if (size > 1) {
                int getrowNumberInternalThresholdTable = FunctionLibrary.getRowNumberFromWebTable(internalControlsThresholdTable, dataObj.get("Paytype"), "get the row number of cash tranaction");

                maxValue = FunctionLibrary.getWebTableCellData(internalControlsThresholdTable, getrowNumberInternalThresholdTable, 9, "Getting value");
                System.out.println("Approval Amount:" + maxValue);
                String existingAmount=maxValue;
                //existingAmount=existingAmount.replace("$","");
                existingAmount=existingAmount.replace(",","");
                // double addedAmount = Double.parseDouble(dataObj.get("GreaterApprovalAmount"));
                Double amount = Double.parseDouble(existingAmount);
                Double amountToBeReversed=amount+getValueforSr();
                String amountToBeReversedstring=Double.toString(amountToBeReversed);
                int rowNumber = FunctionLibrary.getRowNumberFromWebTable(internalControlsThresholdTable, dataObj.get("Responsibility"), "get the row number");
                System.out.println("rownumber:" + rowNumber);

                FunctionLibrary.verifyWebTableCellData(internalControlsThresholdTable, rowNumber, 6,"REVERSAL", "Verifying internal control settings", true);
                CommonLibrary.searchForAccount(dataObj.get("AccountNumber"));


                wait1.until(ExpectedConditions.elementToBeClickable(By.xpath(linkFinancial)));
                FunctionLibrary.clickObject(linkFinancial, "", "Clicking on financial tab");
                wait1.until(ExpectedConditions.elementToBeClickable(By.xpath(accountReplenishmentTab)));

                FunctionLibrary.clickObject(accountReplenishmentTab, "", "Clicking on account replenishment");
                Thread.sleep(2000);
                WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 90);
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(accountReplenishmentNewbtn)));
                //FunctionLibrary.clickObject(accountReplenishmentNewbtn, "", "Clicking on Save");
                FunctionLibrary.objDriver.findElement(By.xpath(accountReplenishmentNewbtn)).click();
                Thread.sleep(4000);
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(amounttxt)));
                FunctionLibrary.objDriver.findElement(By.xpath(amounttxt)).sendKeys(amountToBeReversedstring);
                Thread.sleep(4000);

                FunctionLibrary.clickObject(accountReplenishmentSavebutton, "", "Clicking on Save");


                FunctionLibrary.scrollDowntoElement(newPaymentList, "Scrol down");
                //FunctionLibrary.clickObject(newPaymentList, "", "click on newPaymentList");

                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(saveRebillInfo,
                        newPaymentList, 5, 20);

                FunctionLibrary.scrollDowntoElement(saveRebillInfo, "Scrol down");
                FunctionLibrary.selectDropDownListByValue(replenishmentDetailsRebillPayTypeDropdown,dataObj.get("Paytype"),"select cash from dropdown");
                if(dataObj.get("Paytype").equalsIgnoreCase("SAVINGS") || dataObj.get("Paytype").equalsIgnoreCase("CHECKING"))
                {
                    //Enter Bank Account no:
                    FunctionLibrary.setText(paymentDetailsBankAccountNbrTxtBox,dataObj.get("BankAccount"),"Enter bank number");
                    // Enter Routing No:
                    FunctionLibrary.setText(paymentDetailsRoutingNbrTxtBox,dataObj.get("BankRoutine"),"Enter Routing number");
                }
                if(dataObj.get("Paytype").equalsIgnoreCase("CHECK")){

                    FunctionLibrary.objDriver.findElement(By.xpath("//input[@id='ChkNumber']")).sendKeys(dataObj.get("Checknumber"));

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                            "Doing payment through check", LogStatus.INFO, true);

                }
                //FunctionLibrary.clickObject(saveRebillInfo, "", "click on saveRebillInfo");
                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(paymentListSaveBtn,
                        saveRebillInfo, 5, 20);
                // FunctionLibrary.clickObject(paymentListSaveBtn,"","Saving payment");
                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(processPayBtn,
                        paymentListSaveBtn, 5, 20);
                FunctionLibrary.clickObject(processPayBtn,"","Clicking on process pay");
                try {

                    WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,90);
                    wait2.until(ExpectedConditions.alertIsPresent());
                    Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                    alert.accept();

                }
                catch (Exception e) {
                    //exception handling
                }
                FunctionLibrary.clickObject(reverseBtn,"","Clicking on reverse");
                try {

                    WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,90);
                    wait2.until(ExpectedConditions.alertIsPresent());
                    Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                    alert.accept();

                }
                catch (Exception e) {
                    //exception handling
                }

                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathserviceReqListTable)));
                String xpathSRTbl=""+xpathserviceReqListTable;
                int rowNumberSR = FunctionLibrary.getRowNumberFromWebTable(xpathSRTbl, "REVERSAL", "Get the row number");
                String ServiceRequestID = FunctionLibrary.getWebTableCellData(xpathSRTbl,rowNumberSR,2,"get ServiceRequestID");
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Service Request created with SR#:"+ServiceRequestID,LogStatus.PASS,true);

                CommonLibrary.logoutSiebelApplicaiton();
                CommonLibrary.loginSiebelAppSecondUser(dataObj);

                desc = "Clicking on service Request tab";
                FunctionLibrary.clickObject(xpathServiceRequestTab, "", desc);
                WebDriverWait waitForSearch = new WebDriverWait(FunctionLibrary.objDriver, 90);
                waitForSearch.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathServiceReuestSearchField)));
                FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceReuestSearchField)).sendKeys(ServiceRequestID);

                desc = "Clicking on Service Request Search Go Button";
                FunctionLibrary.clickObject(xpathServiceRequestSearchGo, "", desc);

                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathserviceReqListTable)));

                FunctionLibrary.objDriver.findElement(By.xpath(ownerFieldSrch)).sendKeys(dataObj.get("Second LoginUserName"));


                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Service request with SR#: " + ServiceRequestID, LogStatus.INFO, true);
                desc = "Saving the service request";
                FunctionLibrary.clickObject(xpathServiceRequestSaveButton, "", desc);

                // FunctionLibrary.clickObject(escalationTab, "", "click on Escalation Tab");
                //  FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(Reversalapprovebutton, escalationTab, 5, 20);

                try
                {
                    FunctionLibrary.selectDropDownList("//select[@aria-label='Third Level View Bar']","Escalation","");
                }
                catch(Exception e)
                {

                    FunctionLibrary.clickObject(escalationTab, "", "");
                }
                FunctionLibrary.clickObject(Reversalapprovebutton, "", "click on approve");
                try {
                    String alertText=FunctionLibrary.objDriver.findElement(By.xpath("//div[@id='_sweview_popup']")).getText();
                    if(alertText.equals("Internal Control Threshold Master Data is missing.(SBL-EXL-00151)")) {
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Unknown error",LogStatus.FAIL,true);
                        FunctionLibrary.objDriver.findElement(By.xpath(okButton)).click();
                    }
                    else
                    {
                        FunctionLibrary.objDriver.findElement(By.xpath(okButton)).click();
                    }
                }
                catch (Exception e) {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Unknown error",LogStatus.FAIL,true);
                }

               /* try {

                    WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,90);
                    wait2.until(ExpectedConditions.alertIsPresent());
                    Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                    alert.accept();

                }
                catch (Exception e) {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Unknown error",LogStatus.FAIL,true);
                }*/

                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//td[@title='APPROVED']/span[1]/img", okButton, 20, 30);


                String actual=FunctionLibrary.objDriver.findElement(By.xpath("//td[@title='APPROVED']/span[1]/img")).getAttribute("title");
                System.out.println("actual:"+actual);
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected :APPROVED <br> Actual:"+actual,LogStatus.PASS,true);

                CommonLibrary.logoutSiebelApplicaiton();
                CommonLibrary.loginSiebelAppFirstUser(dataObj);


                desc = "Clicking on service Request tab";
                FunctionLibrary.clickObject(xpathServiceRequestTab, "", desc);

                FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceReuestSearchField)).sendKeys(ServiceRequestID);

                desc = "Clicking on Service Request Search Go Button";
                FunctionLibrary.clickObject(xpathServiceRequestSearchGo, "", desc);

                Thread.sleep(10000);

                FunctionLibrary.clickObject(escalationTab, "", "click on Escalation Tab");
                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(processbutton, escalationTab, 5, 20);
                String amountReversedAfterProcess=FunctionLibrary.objDriver.findElement(By.xpath("//input[@aria-label='Amount']")).getAttribute("value");

                FunctionLibrary.clickObject(processbutton, "", "click on Process");



              /*  String actualProcessAlert="";
                try {

                    WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,90);
                    wait2.until(ExpectedConditions.alertIsPresent());
                    Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                    actualProcessAlert=alert.getText();
                    alert.accept();

                }
                catch (Exception e) {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Unknown error",LogStatus.FAIL,true);
                }
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Verifying Processed Message, Expected :Transaction Successfully Processed(SBL-EXL-00151)"+"<br>"+
                        "Actual:"+actualProcessAlert,LogStatus.PASS,true);*/

                String actualProcessAlert=FunctionLibrary.objDriver.findElement(By.xpath("//div[@id='_sweview_popup']")).getText();

                FunctionLibrary.objDriver.findElement(By.xpath(okButton)).click();

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Verifying Processed Message" +
                        actualProcessAlert,LogStatus.PASS,true);

                FunctionLibrary.webdrvwaitByVisiblityofElementClickableByXpath(FunctionLibrary.objDriver, 15, xpathserviceReqListTable);
                String actualval=FunctionLibrary.objDriver.findElement(By.xpath("//td[@title='PROCESSED']/span[1]/img")).getAttribute("title");
                System.out.println("actual:"+actual);
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Verifying Sub status, Expected :PROCESSED <br> Actual:"+actualval,LogStatus.PASS,true);

             //   FunctionLibrary.objDriver.findElement(By.xpath(xpathMoreInfo)).click();


                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathServiceReuestSearchField
                        ,"//a[text()='Service Requests Home']",30,60);
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathServiceReuestSearchField)));
                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                        "//li[text()='PAYMENT']",xpathServiceReuestSearchField,2,2);
                FunctionLibrary.setText(xpathServiceReuestSearchField,ServiceRequestID,"Enter account number");
                //   FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceReuestSearchField)).sendKeys(serviceRequestId1);

               desc = "Clicking on Service Request Search Go Button";
                FunctionLibrary.clickObject(xpathServiceRequestSearchGo, "", desc);
                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                        "//li[text()='PAYMENT']",xpathServiceReuestSearchField,2,2);
                String statusText=FunctionLibrary.objDriver.findElement(By.xpath(srStatus)).getAttribute("value");
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"verifying status:"+statusText,LogStatus.PASS,true);

                CommonLibrary.searchForAccount(dataObj.get("AccountNumber"));

                FunctionLibrary.webdrvwaitByVisiblityofElementClickableByXpath(FunctionLibrary.objDriver, 20, linkFinancial);


                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(reversalTab, linkFinancial, 10, 30);


                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(financialTransListTbl, reversalTab, 10, 30);


                int rowNumberforReversal = FunctionLibrary.getRowNumberFromWebTable(financialTransListTbl, "("+amountReversedAfterProcess+")", "get the row number");
                System.out.println("rownumber:" + rowNumber);

                FunctionLibrary.verifyWebTableCellData(financialTransListTbl, rowNumberforReversal, 3,"XR", "Verifying in Financial Reversal", true);


            }
            else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                        "Internal Threshold not set", LogStatus.FAIL, true);
            }

        }
        catch(Exception e)
        {
            String Errormsg=e.getMessage();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                    "Unknown Error Message :"
                            +Errormsg, LogStatus.FAIL, true);
        }


    }

    public static String createSrWithOwner(String accNum,String notes,String priority,String description,String queue, String subQueue) throws InterruptedException
    {
        String desc = "Clicking on new SR button";
        FunctionLibrary.clickObject(xpathNewServiceRequestButton, "", desc);

        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 60);
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//li[text()='PAYMENT']",
                "//input[@aria-label='Queue']//following::span[1]", 15, 120);

        FunctionLibrary.setText(""+xpathAccountNumber, accNum, "Enter account number");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathSRPriority)).clear();
        FunctionLibrary.setText(""+xpathSRPriority, priority, "Enter account number");
        FunctionLibrary.setText(""+xpathNotes, notes, "Enter account number");
        FunctionLibrary.setText(""+xpathDescription, description, "Enter account number");
        String status = FunctionLibrary.getText(""+xpathSRStatus,"Get the value from sr field");

        System.out.println(status);
        FunctionLibrary.setText(""+xpathQueue, queue, "Enter account number");
        FunctionLibrary.setText(""+xpathSubQueue, subQueue, "Enter account number");

        String serviceRequestId = FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestId)).getText();
        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();


        desc = "Clicking on owner field";
        System.out.println(desc);

        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(""+xpathOwnerPopupHeaderField,xpathServiceRequestOwnerField,5,50);

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathOwnerPopupHeaderField)));

        FunctionLibrary.objDriver.findElement(By.xpath(xpathOwnerPopupSearchField)).clear();

        FunctionLibrary.setText(""+xpathOwnerPopupSearchField, "User ID", "Enter account number");
        FunctionLibrary.setText(""+xpathOwnerpopupSearchValue, dataObj.get("Second LoginUserName"), "Enter account number");

        FunctionLibrary.clickObject(xpathOwnerPopupGoButton,"","");
       /* try {
        	if (FunctionLibrary.objDriver.findElement(By.xpath("//button[@title='Pick Service Request Owner:OK']")).isDisplayed()) {
            FunctionLibrary.clickObject("//button[@title='Pick Service Request Owner:OK']","","Clicking on OK if more then 1 user exists");
        	}
        }
        catch (Exception e)
        {

        }
*/
        Thread.sleep(2000);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Adding the service request with SR#: " + serviceRequestId, LogStatus.INFO, true);

        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_UP)).perform();
        desc = "Saving the service request";
        FunctionLibrary.objDriver.findElement(By.xpath("//button[contains(@aria-label,'Service Requests:Save')]")).click();


        try{

            wait2.until(ExpectedConditions.alertIsPresent());
            Thread.sleep(1000);
            Alert alert = FunctionLibrary.objDriver.switchTo().alert();
            alert.accept();
            return serviceRequestId;
        }catch (Exception e){

            System.out.println(e.getMessage());
            return serviceRequestId;

        }
    }

    public static String createSr(String accNum,String notes,String priority,String description,String queue, String subQueue) throws InterruptedException
    {
        String desc = "Clicking on new SR button";
        FunctionLibrary.clickObject(serviceRequestNewBtn, "", desc);

        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//li[text()='PAYMENT']",
                "//input[@aria-label='Queue']//following::span[1]", 15, 120);


        FunctionLibrary.setText(""+xpathAccountNumber, accNum, "Enter account number");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathSRPriority)).clear();
        FunctionLibrary.setText(""+xpathSRPriority, priority, "Enter account number");
        FunctionLibrary.setText(""+xpathNotes, notes, "Enter account number");
        FunctionLibrary.setText(""+xpathDescription, description, "Enter account number");

        String serviceRequestId = FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestId)).getText();
        System.out.println("serviceRequestId1: "+serviceRequestId);

        String status = FunctionLibrary.getText(""+xpathSRStatus,"Get the value from sr field");
        String firstName = FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestFirstName)).getAttribute("value");
        String lastName = FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestLastName)).getAttribute("value");
        String email =FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestEmail)).getAttribute("value");
        String accountName =FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestAccountName)).getAttribute("value");
        String cellularPhone =FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestCellularPhone)).getAttribute("value");

        if(!firstName.equalsIgnoreCase("")&&!lastName.equalsIgnoreCase("")&&!email.equalsIgnoreCase("")
                &&!accountName.equalsIgnoreCase("")&&!cellularPhone.equalsIgnoreCase("")){

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Account details populated ", LogStatus.INFO, true);
        }
        else{
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Account details not populated ", LogStatus.FAIL, true);
        }

        System.out.println(status);
        FunctionLibrary.setText(xpathQueue,queue,"");
        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.DOWN)).perform();
        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.DOWN)).perform();
        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();

        FunctionLibrary.setText(""+xpathSubQueue, subQueue, "Enter account number");
        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
        Thread.sleep(2000);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Adding the service request with SR#: " + serviceRequestId, LogStatus.INFO, true);

        WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 150);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@aria-label,'Service Requests:Save')]")));
        desc = "Saving the service request";

        try {
            FunctionLibrary.objDriver.findElement(By.xpath("//button[contains(@aria-label,'Service Requests:Save')]")).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject("//button[contains(@aria-label,'Service Requests:Save')]", "", "");
        }


        try{

            FunctionLibrary.waitForObject.until(ExpectedConditions.alertIsPresent());
            Thread.sleep(1000);
            Alert alert = FunctionLibrary.objDriver.switchTo().alert();
            alert.accept();
            return serviceRequestId;
        }catch (Exception e){

            System.out.println(e.getMessage());
            return serviceRequestId;

        }

    }



    public static void tcCaseManagementCMGT136() throws Exception {

        try {
            String accountNumber = dataObj.get("AccountNumber");
            String notesText = dataObj.get("Notes");
            String descriptionText = dataObj.get("Description");
            String queueValue = dataObj.get("Queue");
            String subQueueValue = dataObj.get("SubQueue");
            String priority = dataObj.get("SR Priority");
            String srStatus = dataObj.get("Status");
            String subStatus = dataObj.get("SRSubStatus");
            String holdExpDate = dataObj.get("HoldExpDate");
            String holdReason = dataObj.get("HoldReason");
            String desc="";
           // String serviceRequestId = "";
            String path = ReportLibrary.getPath() + "\\testdata\\" + "Test_doc" + ".docx";

            String lastName_fristName=WebAccountMaintenance.fNameLname();
            String lname=lastName_fristName.split("_")[1];
            String fname=lastName_fristName.split("_")[0];
            FunctionLibrary.objDriver.navigate().to(CommonLibrary.getSettingsSheetInfo().get("WEB_URL_QA").toString());
            WebDriverWait wait= new WebDriverWait(FunctionLibrary.objDriver, 100);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisibleWebapp(fnametxt,contactusLink,5,50);
            // FunctionLibrary.objDriver.findElement(By.xpath(contactusLink)).click();
            FunctionLibrary.objDriver.findElement(By.xpath(fnametxt)).sendKeys(fname);
            FunctionLibrary.objDriver.findElement(By.xpath(lnametxt)).sendKeys(lname);
            FunctionLibrary.selectDropDownListByValue(issueCategory,dataObj.get("Issuecategory"),"Selecting issueCategory");
            FunctionLibrary.selectDropDownListByValue(issue,dataObj.get("Issue"),"Selecting issueCategory");
            FunctionLibrary.scrollDown(xpathEmailId,"Scrolling down");Thread.sleep(1000);
            FunctionLibrary.objDriver.findElement(By.xpath(emailId)).sendKeys(dataObj.get("Email Id"));
            FunctionLibrary.objDriver.findElement(By.xpath(retypeEmailId)).sendKeys(dataObj.get("Email Id"));
            FunctionLibrary.objDriver.findElement(By.xpath(phoneNo)).sendKeys(dataObj.get("Phoneno"));
            FunctionLibrary.objDriver.findElement(By.xpath(comment)).sendKeys(dataObj.get("Comment"));
            FunctionLibrary.scrollDown("","Scrolling down");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisibleWebapp(message,submit,5,50);


            String text=FunctionLibrary.objDriver.findElement(By.xpath("//b")).getText();
            String[] messageConfirmation=text.split(" ");


            String serviceRequest=messageConfirmation[12].replace('.',' ').trim();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Service Request created from web:"+serviceRequest, LogStatus.INFO, true);
            FunctionLibrary.closeAllActiveBrowser();

            String uname=dataObj.get("First LoginUserName");
            String pwd=dataObj.get("First LoginPassword");

            CommonLibrary.loginSiebelAppWithWebAcc(CommonLibrary.getSettingsSheetInfo().get("URL_QA").toString(),uname,pwd );

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathServiceRequestList,xpathServiceRequestTab,10,30
            );

            FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceReuestSearchField)).sendKeys(serviceRequest);

            desc = "Clicking on Service Request Search Go Button";
            FunctionLibrary.clickObject(xpathServiceRequestSearchGo, "", desc);
            Thread.sleep(6000);

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(""+xpathOwnerPopupHeaderField,xpathServiceRequestOwnerField,5,50);
            WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 150);
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathOwnerPopupHeaderField)));

            FunctionLibrary.objDriver.findElement(By.xpath(xpathOwnerPopupSearchField)).clear();

            FunctionLibrary.setText(""+xpathOwnerPopupSearchField, "User ID", "Enter account number");
            FunctionLibrary.setText(""+xpathOwnerpopupSearchValue, dataObj.get("Second LoginUserName"), "Enter account number");
            FunctionLibrary.clickObject(xpathOwnerPopupGoButton,"","");
            Thread.sleep(2000);


            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_UP)).perform();
            desc = "Saving the service request";
            FunctionLibrary.objDriver.findElement(By.xpath("//button[contains(@aria-label,'Service Requests:Save')]")).click();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Service request Assigned" , LogStatus.INFO, true);

            CommonLibrary.logoutSiebelApplicaiton();

            //login as supervisor
            CommonLibrary.loginSiebelAppSecondUser(dataObj);


            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathServiceRequestList,xpathServiceRequestTab,10,30
            );

            FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceReuestSearchField)).sendKeys(serviceRequest);

             desc = "Clicking on Service Request Search Go Button";
            FunctionLibrary.clickObject(xpathServiceRequestSearchGo, "", desc);
            WebDriverWait wait4 = new WebDriverWait(FunctionLibrary.objDriver, 150);
            try {

                wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathResult)));

                if (FunctionLibrary.objDriver.findElement(By.xpath(xpathResult)).isDisplayed()) {

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Service request found with SR#: " + serviceRequest, LogStatus.INFO, true);


                }
            } catch (Exception e) {

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Couldn't find the Service Request", LogStatus.FAIL, true);
            }

            desc = "Clicking on attachments";
            FunctionLibrary.clickObject(xpathAttachments, "", desc);

            Thread.sleep(5000);

            FunctionLibrary.objDriver.findElement(By.xpath("//input[@title='Attachments:New File']")).sendKeys(path);

            Thread.sleep(3000);
            // Logout from the application

            if (FunctionLibrary.objDriver.findElement(By.xpath(xpathAttachedDoc)).isDisplayed()) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Attached the document", LogStatus.PASS, true);
            } else {

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Document not attached", LogStatus.FAIL, true);
            }


            try {

                wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMoreInfo)));
                wait4.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathMoreInfo)));

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Service request found with SR#: " + serviceRequest, LogStatus.PASS, true);


                    FunctionLibrary.objDriver.findElement(By.xpath(xpathMoreInfo)).click();
                    FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//li[text()='PAYMENT']",
                            "//input[@aria-label='Queue']//following::span[1]", 15, 30);

                    WebDriverWait wait3 = new WebDriverWait(FunctionLibrary.objDriver, 80);
                    wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathAccountNumber)));
                    try {
                        if (FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestShowMore)).isDisplayed()) {

                            FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestShowMore)).click();

                        }
                    }catch (Exception e){
                        System.out.println("Show more is not displayed");
                    }

                    Thread.sleep(8000);
                    wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathSRStatus)));
                    FunctionLibrary.objDriver.findElement(By.xpath(xpathSRStatus)).clear();
                    FunctionLibrary.objDriver.findElement(By.xpath(xpathSRStatus)).sendKeys(srStatus);
                    Thread.sleep(5000);
                    FunctionLibrary.objDriver.findElement(By.xpath(xpathSRSubStatus)).clear();
                    FunctionLibrary.objDriver.findElement(By.xpath(xpathSRSubStatus)).sendKeys(subStatus);
                    new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();

                    wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathHoldReason)));

                    FunctionLibrary.objDriver.findElement(By.xpath(xpathHoldReason)).sendKeys(holdReason);
                    Thread.sleep(1000);
                    wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathHoldReason)));
                    FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestHoldExpDate)).sendKeys(holdExpDate);

                    desc = "Saving the service request";
                    FunctionLibrary.objDriver.findElement(By.xpath("//button[contains(@aria-label,'Service Requests:Save')]/span[text()='Save']")).click();


                    int rowNumberWithSR = 1;

                    DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
                    Date date = new Date();
                    String expectedDate=dateFormat.format(date);
                    String dateOpened = FunctionLibrary.getWebTableCellData(xpathServiceRequestTable,rowNumberWithSR,3,"get date");
                    String[] dateOpenedSplit=dateOpened.split(" ");

                    if(expectedDate.equalsIgnoreCase(dateOpenedSplit[0])) {
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying Date Opened <br> Expected  : " + expectedDate + "  Actual: " + dateOpenedSplit[0] , LogStatus.PASS, true);
                    }

                    else{

                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying Date Opened <br> Expected  :  " + expectedDate + "  Actual: " + dateOpenedSplit[0] , LogStatus.FAIL, true);
                    }

                    FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 2, serviceRequest,
                            "Verifying SR#",false);
                    FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 4, queueValue,
                            "Verifying Queue value",false);
                    FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 5, subQueueValue,
                            "Verifying Sub Queue Value",false);

                    FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 6, srStatus,
                            "Verifying SR status",false);


                    String actualSubstatus = FunctionLibrary.objDriver.findElement(By.xpath(xpathSRSubStatus)).getAttribute("value");

                    if(subStatus.equalsIgnoreCase(actualSubstatus)){

                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying sub status, Expected value: " + subStatus +
                                " Actual value: "+ actualSubstatus, LogStatus.PASS, false);

                    }

                    else{

                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying sub status, Expected value: " + subStatus +
                                " Actual value: "+ actualSubstatus, LogStatus.FAIL, false);
                    }



            } catch (Exception e) {

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Couldn't find the Service Request", LogStatus.FAIL, true);
            }


            Thread.sleep(3000);

            try
            {
                FunctionLibrary.selectDropDownList("//select[@aria-label='Third Level View Bar']","Service Request History","");
            }
            catch(Exception e)
            {
                desc = "Clicking on Service Request Tab";
                FunctionLibrary.clickObject(xpathServiceRequestHistoryTab, "", desc);
            }
            WebDriverWait wait6 = new WebDriverWait(FunctionLibrary.objDriver, 80);
            wait6.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathServiceRequestHistoryGoButton)));

            int ServiceRequestHistoryRowNumber = 1;
            FunctionLibrary.verifyWebTableCellData(xpathServiceRequestHistoryTable,ServiceRequestHistoryRowNumber, 22, serviceRequest,
                    "Verifying SR# in Service Request History Table",true);


            desc = "Clicking on attachments";
            FunctionLibrary.clickObject(xpathAttachments, "", desc);

            try {

                WebDriverWait wait5 = new WebDriverWait(FunctionLibrary.objDriver, 150);
                wait5.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathAttachedDoc)));

                if (FunctionLibrary.objDriver.findElement(By.xpath(xpathAttachedDoc)).isDisplayed()) {

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Attached Document is present", LogStatus.PASS, true);
                }

            } catch (Exception e) {

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Attached Document is not present", LogStatus.FAIL, true);
            }

        }
        catch (Exception e) {


            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown error occurred, error message: " + e.getMessage(), LogStatus.FAIL, true);
        }


    }




    public static void tcCaseManagementCMGT151() throws Exception {

        try {
            String accountNumber = dataObj.get("AccountNumber");
            String notesText = dataObj.get("Notes");
            String descriptionText = dataObj.get("Description");
            String queueValue = dataObj.get("Queue");
            String subQueueValue = dataObj.get("SubQueue");
            String priority = dataObj.get("SR Priority");
            String srStatus = dataObj.get("Status");
            String subStatus = dataObj.get("SRSubStatus");
            String holdExpDate = dataObj.get("HoldExpDate");
            String holdReason = dataObj.get("HoldReason");
            String desc="";
            String[] srStatusSplit = srStatus.split(",");

            String lastName_fristName = WebAccountMaintenance.fNameLname();
            String lname = lastName_fristName.split("_")[1];
            String fname = lastName_fristName.split("_")[0];
            FunctionLibrary.objDriver.navigate().to(CommonLibrary.getSettingsSheetInfo().get("WEB_URL_QA").toString());
            WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 100);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisibleWebapp(fnametxt, contactusLink, 5, 50);
            // FunctionLibrary.objDriver.findElement(By.xpath(contactusLink)).click();
            FunctionLibrary.objDriver.findElement(By.xpath(fnametxt)).sendKeys(fname);
            FunctionLibrary.objDriver.findElement(By.xpath(lnametxt)).sendKeys(lname);
            FunctionLibrary.selectDropDownListByValue(issueCategory, dataObj.get("Issuecategory"), "Selecting issueCategory");
            FunctionLibrary.selectDropDownListByValue(issue, dataObj.get("Issue"), "Selecting issueCategory");
            FunctionLibrary.scrollDown(xpathEmailId, "Scrolling down");
            Thread.sleep(1000);
            FunctionLibrary.objDriver.findElement(By.xpath(emailId)).sendKeys(dataObj.get("Email Id"));
            FunctionLibrary.objDriver.findElement(By.xpath(retypeEmailId)).sendKeys(dataObj.get("Email Id"));
            FunctionLibrary.objDriver.findElement(By.xpath(phoneNo)).sendKeys(dataObj.get("Phoneno"));
            FunctionLibrary.objDriver.findElement(By.xpath(comment)).sendKeys(dataObj.get("Comment"));
            FunctionLibrary.scrollDown("", "Scrolling down");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisibleWebapp(message, submit, 5, 50);


            String text = FunctionLibrary.objDriver.findElement(By.xpath("//b")).getText();
            String[] messageConfirmation = text.split(" ");


            String serviceRequest = messageConfirmation[12].replace('.', ' ').trim();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Service Request created from web:" + serviceRequest, LogStatus.INFO, true);

            FunctionLibrary.closeAllActiveBrowser();

            String uname = dataObj.get("First LoginUserName");
            String pwd = dataObj.get("First LoginPassword");

            CommonLibrary.loginSiebelAppWithWebAcc(CommonLibrary.getSettingsSheetInfo().get("URL_QA").toString(), uname, pwd);

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathServiceRequestList, xpathServiceRequestTab, 10, 30
            );

            FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceReuestSearchField)).sendKeys(serviceRequest);

            desc = "Clicking on Service Request Search Go Button";
            FunctionLibrary.clickObject(xpathServiceRequestSearchGo, "", desc);
            Thread.sleep(6000);

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("" + xpathOwnerPopupHeaderField, xpathServiceRequestOwnerField, 5, 50);
            WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 150);
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathOwnerPopupHeaderField)));

            FunctionLibrary.objDriver.findElement(By.xpath(xpathOwnerPopupSearchField)).clear();

            FunctionLibrary.setText("" + xpathOwnerPopupSearchField, "User ID", "Enter account number");
            FunctionLibrary.setText("" + xpathOwnerpopupSearchValue, dataObj.get("Second LoginUserName"), "Enter account number");
            FunctionLibrary.clickObject(xpathOwnerPopupGoButton, "", "");
            Thread.sleep(2000);


            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_UP)).perform();
            desc = "Saving the service request";
            FunctionLibrary.objDriver.findElement(By.xpath("//button[contains(@aria-label,'Service Requests:Save')]")).click();

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Service request Assigned" , LogStatus.INFO, true);
            CommonLibrary.logoutSiebelApplicaiton();

            //login as supervisor
            CommonLibrary.loginSiebelAppSecondUser(dataObj);


            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathServiceRequestList, xpathServiceRequestTab, 10, 30
            );

            FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceReuestSearchField)).sendKeys(serviceRequest);

            desc = "Clicking on Service Request Search Go Button";
            FunctionLibrary.clickObject(xpathServiceRequestSearchGo, "", desc);
            WebDriverWait wait4 = new WebDriverWait(FunctionLibrary.objDriver, 150);
            try {

                wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathResult)));

                if (FunctionLibrary.objDriver.findElement(By.xpath(xpathResult)).isDisplayed()) {

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Service request found with SR#: " + serviceRequest, LogStatus.INFO, true);


                }
            } catch (Exception e) {

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Couldn't find the Service Request", LogStatus.FAIL, true);
            }



            try {

                wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMoreInfo)));
                wait4.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathMoreInfo)));

                FunctionLibrary.objDriver.findElement(By.xpath(xpathMoreInfo)).click();
                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//li[text()='PAYMENT']",
                        "//input[@aria-label='Account Name']", 5, 15);

                WebDriverWait wait3 = new WebDriverWait(FunctionLibrary.objDriver, 80);
                wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathAccountNumber)));
                try {
                    if (FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestShowMore)).isDisplayed()) {

                        FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestShowMore)).click();

                    }
                } catch (Exception e) {
                    System.out.println("Show more is not displayed");
                }

                Thread.sleep(8000);
                wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathSRStatus)));
                FunctionLibrary.objDriver.findElement(By.xpath(xpathSRStatus)).clear();
                FunctionLibrary.objDriver.findElement(By.xpath(xpathSRStatus)).sendKeys(srStatusSplit[0]);
                Thread.sleep(5000);
                FunctionLibrary.objDriver.findElement(By.xpath(xpathSRSubStatus)).clear();
                FunctionLibrary.objDriver.findElement(By.xpath(xpathSRSubStatus)).sendKeys(subStatus);
                new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();

                wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathHoldReason)));

                FunctionLibrary.objDriver.findElement(By.xpath(xpathHoldReason)).sendKeys(holdReason);
                Thread.sleep(1000);
                wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathHoldReason)));
                FunctionLibrary.objDriver.findElement(By.xpath(xpathServiceRequestHoldExpDate)).sendKeys(holdExpDate);

                desc = "Saving the service request";
                FunctionLibrary.objDriver.findElement(By.xpath("//button[contains(@aria-label,'Service Requests:Save')]/span[text()='Save']")).click();


                wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathSRStatus)));
                FunctionLibrary.objDriver.findElement(By.xpath(xpathSRStatus)).clear();
                FunctionLibrary.objDriver.findElement(By.xpath(xpathSRStatus)).sendKeys(srStatusSplit[1]);
                FunctionLibrary.objDriver.findElement(By.xpath(xpathSRSubStatus)).clear();
                FunctionLibrary.objDriver.findElement(By.xpath(xpathSRSubStatus)).sendKeys(subStatus);

                FunctionLibrary.objDriver.findElement(By.xpath("//input[@aria-label='Closure Code']")).clear();
                FunctionLibrary.objDriver.findElement(By.xpath("//input[@aria-label='Closure Code']")).sendKeys(dataObj.get("ClosureText"));

                FunctionLibrary.objDriver.findElement(By.xpath("//button[contains(@aria-label,'Service Requests:Save')]/span[text()='Save']")).click();

            //    wait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathHoldReason)));

                int rowNumberWithSR = 1;

                DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy");
                Date date = new Date();
                String expectedDate = dateFormat.format(date);
                String dateOpened = FunctionLibrary.getWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 3, "get date");
                String[] dateOpenedSplit = dateOpened.split(" ");

                if (expectedDate.equalsIgnoreCase(dateOpenedSplit[0])) {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying Date Opened <br> Expected  : " + expectedDate + "  Actual: " + dateOpenedSplit[0], LogStatus.PASS, true);
                } else {

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying Date Opened <br> Expected  :  " + expectedDate + "  Actual: " + dateOpenedSplit[0], LogStatus.FAIL, true);
                }

                FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 2, serviceRequest,
                        "Verifying SR#", false);
                FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 4, queueValue,
                        "Verifying Queue value", false);
                FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 5, subQueueValue,
                        "Verifying Sub Queue Value", false);

                FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rowNumberWithSR, 6, srStatusSplit[1],
                        "Verifying SR status", false);


                String actualSubstatus = FunctionLibrary.objDriver.findElement(By.xpath(xpathSRSubStatus)).getAttribute("value");

                if (subStatus.equalsIgnoreCase(actualSubstatus)) {

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying sub status, Expected value: " + subStatus +
                            " Actual value: " + actualSubstatus, LogStatus.PASS, false);

                } else {

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying sub status, Expected value: " + subStatus +
                            " Actual value: " + actualSubstatus, LogStatus.FAIL, false);
                }


            } catch (Exception e) {

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Couldn't find the Service Request", LogStatus.FAIL, true);
            }


            Thread.sleep(3000);

            try {
                FunctionLibrary.selectDropDownList("//select[@aria-label='Third Level View Bar']", "Service Request History", "");
            } catch (Exception e) {
                desc = "Clicking on Service Request Tab";
                FunctionLibrary.clickObject(xpathServiceRequestHistoryTab, "", desc);
            }
            WebDriverWait wait6 = new WebDriverWait(FunctionLibrary.objDriver, 80);
            wait6.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathServiceRequestHistoryGoButton)));

            int ServiceRequestHistoryRowNumber = 1;
            FunctionLibrary.verifyWebTableCellData(xpathServiceRequestHistoryTable, ServiceRequestHistoryRowNumber, 22, serviceRequest,
                    "Verifying SR# in Service Request History Table", true);


        }
        catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown error occurred, error message: " + e.getMessage(), LogStatus.FAIL, true);
        }

    }



    public static Boolean get_S_SRV_REQ_Details(String serviceRequestId)
    {
        String sql = " select * from Siebel.s_Srv_Req where SR_NUM='"+serviceRequestId+"'";

        System.out.println("Status "+serviceRequestId);
        HashMap  <String,String> inputAccountNumInfo =CommonLibrary.executeSqlAndGetOutputAsHashMap(sql);
        if(inputAccountNumInfo.get("SR_NUM").equals(serviceRequestId))
            return true;
        else
            return false;

    }


    public static Boolean get_CX_DISPUTE_TOLL_Details(String laneId,String accNo)
    {

       String sql = " select * from Siebel.CX_DISPUTE_TOLL WHERE LANE_TX_ID = '"+laneId+"' and ETC_ACCOUNT_ID='"+accNo+"'";
       // String sql = " select * from Siebel.CX_DISPUTE_TOLL WHERE ETC_ACCOUNT_ID ='"+accNo+"'";
        System.out.println("Status "+laneId);
        HashMap  <String,String> inputAccountNumInfo =CommonLibrary.executeSqlAndGetOutputAsHashMap(sql);
        if(inputAccountNumInfo.get("LANE_TX_ID").equals(laneId) && inputAccountNumInfo.get("ETC_ACCOUNT_ID").equals(accNo) )
            return true;
        else
            return false;

    }

    public static int getValueforSr()
    {
        int amount;
        amount=DateTime.now().getMillisOfSecond()+DateTime.now().getSecondOfMinute()+ DateTime.now().getMillisOfSecond();
        return amount;
    }







}//End of CASE Management class
