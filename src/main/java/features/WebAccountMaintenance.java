package features;
/**
 * Created by c5063105 on 2/24/2017.
 */

import com.relevantcodes.extentreports.LogStatus;
import libraries.CommonLibrary;
import libraries.FunctionLibrary;
import libraries.ReportLibrary;
import libraries.TestCaseLibrary;
import objectProperties.SblAccountMaintenancePageObject;
import objectProperties.SblCreateAccountPageProperties;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import static libraries.CommonLibrary.getEachTestCaseData;
import static objectProperties.WebAccountMaintenancePageProperties.*;
import static objectProperties.SblCaseManagementProperties.*;
import static libraries.CommonLibrary.dataObj;

import features.ExcelSheet;

//Write to Excel
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import java.io.FileOutputStream;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.Future;

public class WebAccountMaintenance {

    //public static HashMap<String, String> dataObj = null;
/*    public static String WebLoginStatus = "NotSuccess";
    public static String browserType = "";*/

    public static void WebAccountMaintenanceTest() throws IOException, Exception {
        ExcelSheet exl = new ExcelSheet();

        int noOfRows = exl.totalrows("FLCSS_Web_AccountMaintenance", "Test_Scenarios");

        HashMap<String, String> eachTestCaseData = new HashMap<String, String>();

        String testCaseName = "";

        for (int i = 1; i <= noOfRows; i++) {

            eachTestCaseData = getEachTestCaseData(exl, "Test_Scenarios", i, "FLCSS_Web_AccountMaintenance");
            dataObj = eachTestCaseData;
            testCaseName = "tcAccountMaintenanceWeb" + eachTestCaseData.get("TestCaseId").replace("-", "");

            if (eachTestCaseData.get("ExecutionStatus").equalsIgnoreCase("Yes")) {
                CommonLibrary.isClosedAllBrowsers=true;
                Method testMethod = null;
                try {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>" + eachTestCaseData.get("TestCaseId")
                            + "</b>" + ": Test Case Execution is started....................... <br>"
                            + "Test case description: " + eachTestCaseData.get("TestCaseDesc"), LogStatus.INFO, false);
                    try {
                        Class<?> c = Class.forName("features.WebAccountMaintenance");
                        testMethod = c.getMethod(testCaseName);
                        TestCaseLibrary.Get_TestCase_Instance().executeTC(testMethod);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Test method " + testCaseName + " is not found", LogStatus.FAIL, false);
                    }
                } catch (Exception e) {
                    //if alert found. it will be accepted and close the browser.
                    FunctionLibrary.closeAllActiveBrowser();
                }
                dataObj.clear();
            }
        }
        FunctionLibrary.closeAllActiveBrowser();
    }

    /**
     * @return true if driver is alive else false
     */
    public static Boolean isAlive() {
        try {
            FunctionLibrary.objDriver.getCurrentUrl();//or driver.getTitle();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static WebElement findDynamicElement(By by, int timeOut) {
        // System.out.println(DateTime.now() + "****************************" + by);
        WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, timeOut);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        // System.out.println(DateTime.now() + "****************************" + element);
        return element;
    }

    public static void scrollToView(By by) throws InterruptedException {
// Create instance of Javascript executor
        JavascriptExecutor je = (JavascriptExecutor) FunctionLibrary.objDriver;
//Identify the WebElement which will appear after scrolling down
        WebElement element = FunctionLibrary.objDriver.findElement(by);
// now execute query which actually will scroll until that element is not appeared on page.
        je.executeScript("arguments[0].scrollIntoView(true);", element);
        je.executeScript("window.scrollBy(0,-150)", "");
        Thread.sleep(1000);
    }

    public static String fNameLname() {
        String lastName_fristName = "";
        try {
            ExcelSheet exl1 = new ExcelSheet();
            exl1.totalrows("FLCSS_CAMS_CustomerNamesPicker", "Customer_Name_Selection_Helper");
            lastName_fristName = ExcelSheet.getUniqueLastNameAndFirstName("Customer_Name_Selection_Helper");
            System.out.println(lastName_fristName);
        } catch (Exception e) {
            lastName_fristName = null;
        }
        return lastName_fristName;
    }

    //*********************************************************************************************************************
    //*****************************************************************************************************************
    public static void tcAccountMaintenanceWebACMW003() throws Exception {
        try {
            CommonLibrary.loginWebApp(dataObj.get("User Name"), dataObj.get("Password"), "Trying to Login with valid username and password ");
            String Desc;
            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();
            String AccNo = Desc.substring(9).trim();
            try {
                FunctionLibrary.objDriver.findElement(By.xpath(xpathVehiclesTransponders)).click();
            } catch (Exception e) {
                FunctionLibrary.clickObject(xpathVehiclesTransponders, "", "Clicking");
            }
            try{ findDynamicElement(By.xpath(xpathvehiclesAndTranspondersTxt), 60);}
            catch (Exception e)
            { System.out.println("Unable to find "+xpathvehiclesAndTranspondersTxt);
                findDynamicElement(By.xpath(xpathvehiclesAndTranspondersTxt2), 60);
            }

            Thread.sleep(3000);//As the page is getting refreshed, we are not able to click an element without thread.sleep
            //Code to Edit Vehicles Info
            JavascriptExecutor js = (JavascriptExecutor) FunctionLibrary.objDriver;
            js.executeScript("window.scrollBy(0,250)", "");
            Thread.sleep(3000);
            findDynamicElement(By.xpath(xpathVehiclesTable), 120);
            String plateNumber = FunctionLibrary.objDriver.findElement(By.xpath(xpathVehiclesAdded1)).getText();
            scrollToView(By.xpath(xpathVehiclesTable));
            System.out.println(plateNumber);
            Thread.sleep(3000);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathEditBtn)).click();
            findDynamicElement(By.xpath(xpathEditVehicleTxt), 120);
            findDynamicElement(By.xpath(xpathWebPlateNbrTxtBox), 120);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Vehicle details before change", LogStatus.INFO, true);
            FunctionLibrary.selectDropDownList(vehicleMakeDropDown, dataObj.get("Make"), "Selecting Vehicle Make value");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Changed make to " + dataObj.get("Make"), LogStatus.INFO, true);
            js.executeScript("window.scrollBy(0,250)", "");
            FunctionLibrary.objDriver.findElement(By.xpath(xpathUpdateVehicleBtn)).click();
            Thread.sleep(2000);
            findDynamicElement(By.xpath(xpathVehicleInfoChangeSuccessAlert), 120);
            // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathLogout)));
            CommonLibrary.logoutWebApp();
            //Verifying the changes in Siebel application:
            String uname = dataObj.get("Suname");
            String pwd = dataObj.get("SPWD");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying the changes in Siebel Application", LogStatus.INFO, false);
            CommonLibrary.loginSiebelAppWithWebAcc(siebelURL1, uname, pwd, dataObj.get("Browser Type"));
            CommonLibrary.searchForAccount(AccNo);
            FunctionLibrary.clickObject(xpathAccountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathAccountInfoVehiclesTab, xpathAccountInfoTab, 10, 60);
            //Verifying under Devices tab
            FunctionLibrary.clickObject(xpathAccountInfoVehiclesTab, "", "CLicking vehicles tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathVehicleListTable, xpathAccountInfoVehiclesTab, 10, 60);
            FunctionLibrary.clickObject(xpathVehicleListQueryBtn, "", "Clicking Query button");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathPlateNumberTxtBox, xpathVehicleListQueryBtn, 10, 60);
            FunctionLibrary.setText(xpathPlateNumberTxtBox, plateNumber, "Enter plate number");
            FunctionLibrary.clickObject(xpathVehicleListGoBtn, "", "Click go button");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathVehicleListQueryBtn, xpathVehicleListGoBtn, 10, 60);
            String vehicleMake = FunctionLibrary.objDriver.findElement(By.xpath(xpathOfVehicleMake)).getAttribute("title");
            if (vehicleMake.equals(dataObj.get("Make"))) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:Vehicle details should be updated" + "</br>"
                        + "Actual: Vehicle details has been modified as below", LogStatus.PASS, true);
            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:Vehicle details should be updated" + "</br>"
                        + "Actual: Vehicle details has not been modified successfully", LogStatus.FAIL, true);
            }
            //Verifying under Devices history tab
            FunctionLibrary.clickObject(xpathVehicleHistoryTab, "", "CLick vehicle history tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathVehicleHistoryTable, xpathVehicleHistoryTab, 10, 60);
            FunctionLibrary.clickObject(xpathVehicleHistoryQueryBtn, "", "CLick Query button");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathStatusTxtBox, xpathVehicleHistoryQueryBtn, 10, 60);
            FunctionLibrary.clickObject(xpathPlateNbrElement, "", "CLick plate field");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathPlateNbrTxtBox, xpathPlateNbrElement, 10, 60);
            FunctionLibrary.setText(xpathPlateNbrTxtBox, plateNumber, "Enter plate number");
            FunctionLibrary.clickObject(xpathVehicleHistoryGoBtn, "", "Click go button");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathVehicleHistoryQueryBtn, xpathVehicleHistoryGoBtn, 10, 60);
            String vehicleStatus = FunctionLibrary.objDriver.findElement(By.xpath(xpathOfVehicleStatus)).getAttribute("title");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:Vehicle status under history should be CHANGE" + "</br>"
                    + "Actual: Vehicle status under history is " + vehicleStatus, LogStatus.PASS, true);
            CommonLibrary.logoutSiebelApplicaiton();
        } catch (Exception e) {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" + e.getMessage(), LogStatus.FAIL, true);
        }
    }

    public static void tcAccountMaintenanceWebACMW004() throws Exception {
        try {
            CommonLibrary.loginWebApp(dataObj.get("User Name"), dataObj.get("Password"), "Trying to Login with valid username and password ");
            String Desc;
            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();
            String AccNo = Desc.substring(9).trim();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathVehiclesTransponders)).click();
            try{ findDynamicElement(By.xpath(xpathvehiclesAndTranspondersTxt), 60);}
            catch (Exception e)
            { System.out.println("Unable to find "+xpathvehiclesAndTranspondersTxt);
                findDynamicElement(By.xpath(xpathvehiclesAndTranspondersTxt2), 60);
            }
            Thread.sleep(3000);
            JavascriptExecutor js = (JavascriptExecutor) FunctionLibrary.objDriver;
            js.executeScript("window.scrollBy(0,250)", "");
            Thread.sleep(3000);
            findDynamicElement(By.xpath(xpathVehiclesTable), 120);
            scrollToView(By.xpath(xpathVehiclesTable));
            String plateNumber = FunctionLibrary.objDriver.findElement(By.xpath(xpathVehiclesAdded1)).getText();
            System.out.println(plateNumber);

            FunctionLibrary.objDriver.findElement(By.xpath(xpathEditBtn)).click();
            findDynamicElement(By.xpath(xpathEditVehicleTxt), 120);
            findDynamicElement(By.xpath(xpathWebPlateNbrTxtBox), 120);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Changing vehicle details", LogStatus.INFO, false);
            FunctionLibrary.selectDropDownList(vehicleMakeDropDown, dataObj.get("Make"), "Selecting Vehicle Make value");
            FunctionLibrary.objDriver.findElement(By.xpath(xpathVehicleModelTxtBox)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathVehicleModelTxtBox)).sendKeys(dataObj.get("Model"));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathVehicleYearTxtBox)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathVehicleYearTxtBox)).sendKeys(dataObj.get("Year"));
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Changes like Model(" + dataObj.get("Model") + ") and Year(" + dataObj.get("Year") + ") made to the vehicle details", LogStatus.INFO, false);
            js.executeScript("window.scrollBy(0,250)", "");
            FunctionLibrary.objDriver.findElement(By.xpath(xpathUpdateVehicleBtn)).click();
            findDynamicElement(By.xpath(xpathVehicleInfoChangeSuccessAlert), 120);
            Thread.sleep(5000);
            CommonLibrary.logoutWebApp();

            String uname = dataObj.get("Suname");
            String pwd = dataObj.get("SPWD");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying the changes in Siebel Application", LogStatus.INFO, false);
            CommonLibrary.loginSiebelAppWithWebAcc(siebelURL1, uname, pwd, dataObj.get("Browser Type"));
            CommonLibrary.searchForAccount(AccNo);

            FunctionLibrary.clickObject(xpathAccountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathAccountInfoVehiclesTab, xpathAccountInfoTab, 10, 60);

            //Verifying under Devices tab
            FunctionLibrary.clickObject(xpathAccountInfoVehiclesTab, "", "CLicking vehicles tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathVehicleListTable, xpathAccountInfoVehiclesTab, 10, 60);

            FunctionLibrary.clickObject(xpathVehicleListQueryBtn, "", "Clicking Query button");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathPlateNumberTxtBox, xpathVehicleListQueryBtn, 10, 60);

            FunctionLibrary.setText(xpathPlateNumberTxtBox, plateNumber, "Enter plate number");
            FunctionLibrary.clickObject(xpathVehicleListGoBtn, "", "Click go button");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathVehicleListQueryBtn, xpathVehicleListGoBtn, 10, 60);
            String vehicleYear = FunctionLibrary.objDriver.findElement(By.xpath(xpathOfYearOfVehicle)).getAttribute("title");
            String vehicleMake = FunctionLibrary.objDriver.findElement(By.xpath(xpathOfVehicleMake)).getAttribute("title");
            String vehicleModel = FunctionLibrary.objDriver.findElement(By.xpath(xpathOfVehicleModel)).getAttribute("title");

            if (vehicleYear.equals(dataObj.get("Year")) && vehicleMake.equals(dataObj.get("Make"))
                    && vehicleModel.equals(dataObj.get("Model"))) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:Vehicle details should be updated" + "</br>"
                        + "Actual: Vehicle details has been modified as below", LogStatus.PASS, true);
            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:Vehicle details should be updated" + "</br>"
                        + "Actual: Vehicle details has not been modified successfully", LogStatus.FAIL, true);
            }
            //Verifying under Devices history tab
            FunctionLibrary.clickObject(xpathVehicleHistoryTab, "", "CLick vehicle history tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathVehicleHistoryTable, xpathVehicleHistoryTab, 10, 60);

            FunctionLibrary.clickObject(xpathVehicleHistoryQueryBtn, "", "CLick Query button");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathStatusTxtBox, xpathVehicleHistoryQueryBtn, 10, 60);

            FunctionLibrary.clickObject(xpathPlateNbrElement, "", "CLick plate field");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathPlateNbrTxtBox, xpathPlateNbrElement, 10, 60);

            FunctionLibrary.setText(xpathPlateNbrTxtBox, plateNumber, "Enter plate number");
            FunctionLibrary.clickObject(xpathVehicleHistoryGoBtn, "", "Click go button");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathVehicleHistoryQueryBtn, xpathVehicleHistoryGoBtn, 10, 60);
            String vehicleStatus = FunctionLibrary.objDriver.findElement(By.xpath(xpathOfVehicleStatus)).getAttribute("title");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:Vehicle status under history should be CHANGE" + "</br>"
                    + "Actual: Vehicle status under history is " + vehicleStatus, LogStatus.PASS, true);

            CommonLibrary.logoutSiebelApplicaiton();
        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" + e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }
    }

    public static void tcAccountMaintenanceWebACMW006() throws Exception {
        try {
            CommonLibrary.loginWebApp(dataObj.get("User Name"), dataObj.get("Password"), "Trying to Login with valid username and password ");
            String Desc;
            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();
            String AccNo = Desc.substring(9).trim();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathVehiclesTransponders)).click();
            try{ findDynamicElement(By.xpath(xpathvehiclesAndTranspondersTxt), 60);}
            catch (Exception e)
            { System.out.println("Unable to find "+xpathvehiclesAndTranspondersTxt);
                findDynamicElement(By.xpath(xpathvehiclesAndTranspondersTxt2), 60);
            }
            Thread.sleep(3000);
            //Code to Edit Vehicles Info
            JavascriptExecutor js = (JavascriptExecutor) FunctionLibrary.objDriver;
            findDynamicElement(By.xpath(xpathVehiclesTable), 120);
            scrollToView(By.xpath(xpathAddAnotherVehicleBtn));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAddAnotherVehicleBtn)).click();
            findDynamicElement(By.xpath(xpathWebPlateNbrTxtBox), 120);
            String pp = CommonLibrary.randomIdentifier();
            String RandomChar = pp.substring(0, 2);
            String plateNumber = RandomChar + DateTime.now().getMillisOfSecond() + DateTime.now().getSecondOfMinute() + DateTime.now().getMillisOfSecond();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathWebPlateNbrTxtBox)).sendKeys(plateNumber);
            FunctionLibrary.selectDropDownList(vehicleStateDropDown, dataObj.get("PlateState"), "Selecting Plate state value");
            FunctionLibrary.selectDropDownList(vehicleMakeDropDown, dataObj.get("Make"), "Selecting Vehicle Make value");

            FunctionLibrary.objDriver.findElement(By.xpath(xpathVehicleModelTxtBox)).sendKeys(dataObj.get("Model"));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathVehicleYearTxtBox)).sendKeys(dataObj.get("Year"));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathVehicleColorTxtBox)).sendKeys(dataObj.get("VehicleColor"));
            scrollToView(By.xpath(xpathAddVehicleBtn));
            FunctionLibrary.objDriver.findElement(By.xpath("//*[@name='vehicleStartDateField']")).click();
            Thread.sleep(1000);
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_LEFT)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_LEFT)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_LEFT)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_LEFT)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_LEFT)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_LEFT)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_LEFT)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            String StartDate=FunctionLibrary.objDriver.findElement(By.xpath("//*[@name='vehicleStartDateField']")).getText();
            Thread.sleep(1000);
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            Thread.sleep(1000);
            FunctionLibrary.objDriver.findElement(By.xpath("//*[@name='vehicleEndDateField']")).click();
            Thread.sleep(1000); // new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
            for (int k = 0; k < 32; k++) {
                new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_RIGHT)).perform();
            }
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            Thread.sleep(1000);
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            Thread.sleep(1000);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Adding a new vehicle", LogStatus.INFO, true);
            js.executeScript("window.scrollBy(0,250)", "");
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAddVehicleBtn)).click();
            try {
                findDynamicElement(By.xpath(xpathAddVehicleAlert), 120);
                FunctionLibrary.objDriver.findElement(By.xpath(xpathAddVehicleAlert)).click();
            } catch (Exception e) {
                System.out.println(e);
            }
            findDynamicElement(By.xpath(xpathChangePasswordSuccess), 120);
            String addVehicleSuccessAlert = FunctionLibrary.objDriver.findElement(By.xpath
                    (xpathChangePasswordSuccess)).getText();

            try {
                ((JavascriptExecutor) FunctionLibrary.objDriver).executeScript("arguments[0].scrollIntoView();",
                        FunctionLibrary.objDriver.findElement(By.xpath(xpathVehiclesTable)));
            } catch (Exception e) {
                System.out.println(e);
            }
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expexted: Vehicle should be added" + "</br>" +
                    "Actual: Vehicle " + addVehicleSuccessAlert + " is added successfully", LogStatus.PASS, true);
            Thread.sleep(5000);

            CommonLibrary.logoutWebApp();

            //Verifying the changes in Siebel application:

            String uname = dataObj.get("Suname");
            String pwd = dataObj.get("SPWD");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying the changes in Siebel Application", LogStatus.INFO, false);
            CommonLibrary.loginSiebelAppWithWebAcc(siebelURL1, uname, pwd, dataObj.get("Browser Type"));
            CommonLibrary.searchForAccount(AccNo);

            FunctionLibrary.clickObject(xpathAccountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathAccountInfoVehiclesTab, xpathAccountInfoTab, 10, 60);

            //Verifying under Devices tab
            FunctionLibrary.clickObject(xpathAccountInfoVehiclesTab, "", "CLicking vehicles tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathVehicleListTable, xpathAccountInfoVehiclesTab, 10, 60);

            FunctionLibrary.clickObject(xpathVehicleListQueryBtn, "", "Clicking Query button");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathPlateNumberTxtBox, xpathVehicleListQueryBtn, 10, 60);

            FunctionLibrary.setText(xpathPlateNumberTxtBox, plateNumber, "Enter plate number");
            FunctionLibrary.clickObject(xpathVehicleListGoBtn, "", "Click go button");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathVehicleListQueryBtn, xpathVehicleListGoBtn, 10, 60);
            String vehicleYear = FunctionLibrary.objDriver.findElement(By.xpath(xpathOfYearOfVehicle)).getAttribute("title");
            String vehicleMake = FunctionLibrary.objDriver.findElement(By.xpath(xpathOfVehicleMake)).getAttribute("title");
            String vehicleModel = FunctionLibrary.objDriver.findElement(By.xpath(xpathOfVehicleModel)).getAttribute("title");

            if (vehicleYear.equals(dataObj.get("Year")) && vehicleMake.equals(dataObj.get("Make"))
                    && vehicleModel.equals(dataObj.get("Model"))) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:Vehicle should be added" + "</br>"
                        + "Actual: Vehicle is added successfully", LogStatus.PASS, true);
            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:Vehicle details should be updated" + "</br>"
                        + "Actual: Vehicle details has not been modified successfully", LogStatus.FAIL, true);
            }

            //Verifying under Devices history tab
            FunctionLibrary.clickObject(xpathVehicleHistoryTab, "", "CLick vehicle history tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathVehicleHistoryTable, xpathVehicleHistoryTab, 10, 60);

            FunctionLibrary.clickObject(xpathVehicleHistoryQueryBtn, "", "CLick Query button");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathStatusTxtBox, xpathVehicleHistoryQueryBtn, 10, 60);

            FunctionLibrary.clickObject(xpathPlateNbrElement, "", "CLick plate field");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathPlateNbrTxtBox, xpathPlateNbrElement, 10, 60);

            FunctionLibrary.setText(xpathPlateNbrTxtBox, plateNumber, "Enter plate number");
            FunctionLibrary.clickObject(xpathVehicleHistoryGoBtn, "", "Click go button");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathVehicleHistoryQueryBtn, xpathVehicleHistoryGoBtn, 10, 60);
            String vehicleStatus = FunctionLibrary.objDriver.findElement(By.xpath(xpathOfVehicleStatus)).getAttribute("title");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:Vehicle status under history should be ADD" + "</br>"
                    + "Actual: Vehicle status under history is " + vehicleStatus, LogStatus.PASS, true);

            CommonLibrary.logoutSiebelApplicaiton();

        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" +e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }
    }

    public static void tcAccountMaintenanceWebACMW007() throws Exception {
        try {
            CommonLibrary.loginWebApp(dataObj.get("User Name"), dataObj.get("Password"), "Trying to Login with valid username and password ");
            String Desc;
            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();
            String AccNo = Desc.substring(9).trim();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathVehiclesTransponders)).click();
            try{ findDynamicElement(By.xpath(xpathvehiclesAndTranspondersTxt), 60);}
            catch (Exception e)
            {
                System.out.println("Unable to find "+xpathvehiclesAndTranspondersTxt);
                findDynamicElement(By.xpath(xpathvehiclesAndTranspondersTxt2), 60);
            }
            Thread.sleep(3000);
            //Code to Edit Vehicles Info
            JavascriptExecutor js = (JavascriptExecutor) FunctionLibrary.objDriver;
            findDynamicElement(By.xpath(xpathVehiclesTable), 120);
            scrollToView(By.xpath(xpathAddAnotherVehicleBtn));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAddAnotherVehicleBtn)).click();
            findDynamicElement(By.xpath(xpathWebPlateNbrTxtBox), 120);
            String pp = CommonLibrary.randomIdentifier();
            String RandomChar = pp.substring(0, 2);
            String plateNumber = RandomChar + DateTime.now().getMillisOfSecond() + DateTime.now().getSecondOfMinute() + DateTime.now().getMillisOfSecond();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathWebPlateNbrTxtBox)).sendKeys(plateNumber);
            FunctionLibrary.selectDropDownList(vehicleStateDropDown, dataObj.get("PlateState"), "Selecting Plate state value");
            FunctionLibrary.selectDropDownList(vehicleMakeDropDown, dataObj.get("Make"), "Selecting Vehicle Make value");

            FunctionLibrary.objDriver.findElement(By.xpath(xpathVehicleModelTxtBox)).sendKeys(dataObj.get("Model"));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathVehicleYearTxtBox)).sendKeys(dataObj.get("Year"));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathVehicleColorTxtBox)).sendKeys(dataObj.get("VehicleColor"));
            scrollToView(By.xpath(xpathAddVehicleBtn));
            FunctionLibrary.objDriver.findElement(By.xpath("//*[@name='vehicleStartDateField']")).click();
            Thread.sleep(1000);
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_LEFT)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_LEFT)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_LEFT)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_LEFT)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_LEFT)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_LEFT)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_LEFT)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            String StartDate=FunctionLibrary.objDriver.findElement(By.xpath("//*[@name='vehicleStartDateField']")).getText();
            Thread.sleep(1000);
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            Thread.sleep(1000);
            FunctionLibrary.objDriver.findElement(By.xpath("//*[@name='vehicleEndDateField']")).click();
            Thread.sleep(1000); // new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
            for (int k = 0; k < 32; k++) {
                new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_RIGHT)).perform();
            }
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            Thread.sleep(1000);
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            Thread.sleep(1000);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Adding a new vehicle", LogStatus.INFO, true);
            js.executeScript("window.scrollBy(0,250)", "");
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAddVehicleBtn)).click();
            try {
                findDynamicElement(By.xpath(xpathAddVehicleAlert), 120);
                FunctionLibrary.objDriver.findElement(By.xpath(xpathAddVehicleAlert)).click();
            } catch (Exception e) {
                System.out.println(e);
            }
            findDynamicElement(By.xpath(xpathChangePasswordSuccess), 120);
            String addVehicleSuccessAlert = FunctionLibrary.objDriver.findElement(By.xpath
                    (xpathChangePasswordSuccess)).getText();

            try {
                ((JavascriptExecutor) FunctionLibrary.objDriver).executeScript("arguments[0].scrollIntoView();",
                        FunctionLibrary.objDriver.findElement(By.xpath(xpathVehiclesTable)));
            } catch (Exception e) {
                System.out.println(e);
            }
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expexted: Vehicle should be added" + "</br>" +
                    "Actual: Vehicle " + addVehicleSuccessAlert + " is added successfully", LogStatus.PASS, true);
            Thread.sleep(5000);

            CommonLibrary.logoutWebApp();

            //Verifying the changes in Siebel application:

            String uname = dataObj.get("Suname");
            String pwd = dataObj.get("SPWD");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying the changes in Siebel Application", LogStatus.INFO, false);
            CommonLibrary.loginSiebelAppWithWebAcc(siebelURL1, uname, pwd, dataObj.get("Browser Type"));
            CommonLibrary.searchForAccount(AccNo);

            FunctionLibrary.clickObject(xpathAccountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathAccountInfoVehiclesTab, xpathAccountInfoTab, 10, 60);

            //Verifying under Devices tab
            FunctionLibrary.clickObject(xpathAccountInfoVehiclesTab, "", "CLicking vehicles tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathVehicleListTable, xpathAccountInfoVehiclesTab, 10, 60);

            FunctionLibrary.clickObject(xpathVehicleListQueryBtn, "", "Clicking Query button");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathPlateNumberTxtBox, xpathVehicleListQueryBtn, 10, 60);

            FunctionLibrary.setText(xpathPlateNumberTxtBox, plateNumber, "Enter plate number");
            FunctionLibrary.clickObject(xpathVehicleListGoBtn, "", "Click go button");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathVehicleListQueryBtn, xpathVehicleListGoBtn, 10, 60);
            String vehicleYear = FunctionLibrary.objDriver.findElement(By.xpath(xpathOfYearOfVehicle)).getAttribute("title");
            String vehicleMake = FunctionLibrary.objDriver.findElement(By.xpath(xpathOfVehicleMake)).getAttribute("title");
            String vehicleModel = FunctionLibrary.objDriver.findElement(By.xpath(xpathOfVehicleModel)).getAttribute("title");

            if (vehicleYear.equals(dataObj.get("Year")) && vehicleMake.equals(dataObj.get("Make"))
                    && vehicleModel.equals(dataObj.get("Model"))) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:Vehicle should be added" + "</br>"
                        + "Actual: Vehicle is added successfully", LogStatus.PASS, true);
            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:Vehicle details should be updated" + "</br>"
                        + "Actual: Vehicle details has not been modified successfully", LogStatus.FAIL, true);
            }

            //Verifying under Devices history tab
            FunctionLibrary.clickObject(xpathVehicleHistoryTab, "", "CLick vehicle history tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathVehicleHistoryTable, xpathVehicleHistoryTab, 10, 60);

            FunctionLibrary.clickObject(xpathVehicleHistoryQueryBtn, "", "CLick Query button");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathStatusTxtBox, xpathVehicleHistoryQueryBtn, 10, 60);

            FunctionLibrary.clickObject(xpathPlateNbrElement, "", "CLick plate field");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathPlateNbrTxtBox, xpathPlateNbrElement, 10, 60);

            FunctionLibrary.setText(xpathPlateNbrTxtBox, plateNumber, "Enter plate number");
            FunctionLibrary.clickObject(xpathVehicleHistoryGoBtn, "", "Click go button");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathVehicleHistoryQueryBtn, xpathVehicleHistoryGoBtn, 10, 60);
            String vehicleStatus = FunctionLibrary.objDriver.findElement(By.xpath(xpathOfVehicleStatus)).getAttribute("title");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:Vehicle status under history should be ADD" + "</br>"
                    + "Actual: Vehicle status under history is " + vehicleStatus, LogStatus.PASS, true);

            CommonLibrary.logoutSiebelApplicaiton();

        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" +e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }
    }

    public static void tcAccountMaintenanceWebACMW009() throws Exception {
        try {
            CommonLibrary.loginWebApp(dataObj.get("User Name"), dataObj.get("Password"), "Trying to Login with valid username and password ");
            String Desc;
            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();
            String AccNo = Desc.substring(9).trim();
            //FunctionLibrary.objDriver.findElement(By.xpath(xpathVehiclesTransponders)).click();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathVehiclesTransponders)).click();
            try{ findDynamicElement(By.xpath(xpathvehiclesAndTranspondersTxt), 60);}
            catch (Exception e)
            { System.out.println("Unable to find "+xpathvehiclesAndTranspondersTxt);
                findDynamicElement(By.xpath(xpathvehiclesAndTranspondersTxt2), 60);
            }
            Thread.sleep(3000);
            //Code to Edit Vehicles Info
            findDynamicElement(By.xpath(xpathVehiclesTable), 120);
            scrollToView(By.xpath(xpathAddAnotherVehicleBtn));
            String plateNumber = FunctionLibrary.objDriver.findElement(By.xpath(xpathVehiclesAdded1))
                    .getText();
            System.out.println(plateNumber);
            String vehicleState = FunctionLibrary.objDriver.findElement(By.xpath(xpathOfVehicleid))
                    .getText();
            System.out.println(vehicleState);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Vehicle details of previously added plate", LogStatus.INFO, true);
            scrollToView(By.xpath(xpathAddAnotherVehicleBtn));
            Thread.sleep(2000);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAddAnotherVehicleBtn)).click();
            Thread.sleep(2000);
            findDynamicElement(By.xpath(xpathWebPlateNbrTxtBox), 120);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathWebPlateNbrTxtBox)).sendKeys(plateNumber);
            FunctionLibrary.selectDropDownList(vehicleStateDropDown, vehicleState, "Selecting Plate state value");
            //FunctionLibrary.selectDropDownList(vehicleStateDropDown, "CA", "Selecting Plate state value");
            FunctionLibrary.selectDropDownList(vehicleMakeDropDown, dataObj.get("Make"), "Selecting Vehicle Make value");
            FunctionLibrary.objDriver.findElement(By.xpath(xpathVehicleModelTxtBox)).sendKeys(dataObj.get("Model"));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathVehicleYearTxtBox)).sendKeys(dataObj.get("Year"));
            Thread.sleep(1000);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathVehicleColorTxtBox)).sendKeys("Red");
            scrollToView(By.xpath(xpathAddVehicleBtn));
            FunctionLibrary.objDriver.findElement(By.xpath("//*[@name='vehicleStartDateField']")).click();
            Thread.sleep(1000);
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_LEFT)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_LEFT)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_LEFT)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_LEFT)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_LEFT)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_LEFT)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_LEFT)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            String StartDate=FunctionLibrary.objDriver.findElement(By.xpath("//*[@name='vehicleStartDateField']")).getText();
            Thread.sleep(1000);
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            Thread.sleep(1000);
            FunctionLibrary.objDriver.findElement(By.xpath("//*[@name='vehicleEndDateField']")).click();
            Thread.sleep(1000); // new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
            for (int k = 0; k < 32; k++) {
                new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_RIGHT)).perform();
            }
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            Thread.sleep(1000);
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            Thread.sleep(1000);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Adding a new vehicle", LogStatus.INFO, true);
            scrollToView(By.xpath(xpathAddVehicleBtn));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAddVehicleBtn)).click();
            findDynamicElement(By.xpath("//div[@class='alert alert-danger error_msg']"), 120);
            String addVehicleErrorAlert = FunctionLibrary.objDriver.findElement(By.xpath
                    ("//div[@class='alert alert-danger error_msg']")).getText();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected : Plate should not be added" + "</br>" + "Actual : "
                    + "Below alert message is displayed------> " + "</br>" + addVehicleErrorAlert, LogStatus.PASS, true);
            Thread.sleep(5000);

            CommonLibrary.logoutWebApp();
            String uname = dataObj.get("Suname");
            String pwd = dataObj.get("SPWD");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying the changes in Siebel Application", LogStatus.INFO, false);
            CommonLibrary.loginSiebelAppWithWebAcc(siebelURL1, uname, pwd, dataObj.get("Browser Type"));
            CommonLibrary.searchForAccount(AccNo);

            FunctionLibrary.clickObject(xpathAccountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathAccountInfoVehiclesTab, xpathAccountInfoTab, 10, 60);

            //Verifying under Devices tab
            FunctionLibrary.clickObject(xpathAccountInfoVehiclesTab, "", "CLicking vehicles tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathVehicleListTable, xpathAccountInfoVehiclesTab, 10, 60);

            FunctionLibrary.clickObject(xpathVehicleListQueryBtn, "", "Clicking Query button");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathPlateNumberTxtBox, xpathVehicleListQueryBtn, 10, 60);

            FunctionLibrary.setText(xpathPlateNumberTxtBox, plateNumber, "Enter plate number");
            FunctionLibrary.clickObject(xpathVehicleListGoBtn, "", "Click go button");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathVehicleListQueryBtn, xpathVehicleListGoBtn, 10, 60);
            String vehicleMake = FunctionLibrary.objDriver.findElement(By.xpath(xpathOfVehicleMake)).getAttribute("title");

            if (vehicleMake.equals(dataObj.get("Make"))) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:Vehicle details should be updated" + "</br>"
                        + "Actual: Vehicle details has been modified as below", LogStatus.PASS, true);
            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:Vehicle details should be updated" + "</br>"
                        + "Actual: Vehicle details has not been modified successfully", LogStatus.FAIL, true);
            }
            //Verifying under Devices history tab
            FunctionLibrary.clickObject(xpathVehicleHistoryTab, "", "CLick vehicle history tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathVehicleHistoryTable, xpathVehicleHistoryTab, 10, 60);

            FunctionLibrary.clickObject(xpathVehicleHistoryQueryBtn, "", "CLick Query button");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathStatusTxtBox, xpathVehicleHistoryQueryBtn, 10, 60);

            FunctionLibrary.clickObject(xpathPlateNbrElement, "", "CLick plate field");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathPlateNbrTxtBox, xpathPlateNbrElement, 10, 60);

            FunctionLibrary.setText(xpathPlateNbrTxtBox, plateNumber, "Enter plate number");
            FunctionLibrary.clickObject(xpathVehicleHistoryGoBtn, "", "Click go button");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathVehicleHistoryQueryBtn, xpathVehicleHistoryGoBtn, 10, 60);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathOfVehicleStatus)).getAttribute("title");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:Should found one vehicle", LogStatus.PASS, true);
            CommonLibrary.logoutSiebelApplicaiton();

        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" +e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }
    }

    public static void tcAccountMaintenanceWebACMW082() throws InterruptedException {
        try {
            writeVehicleDetailsToExcel("testing_vehicle","Sheet1",1,"NA");
            String userName = dataObj.get("User Name");
            String password = dataObj.get("Password");
            File file = new File(ReportLibrary.getPath() + "\\testdata\\testing_vehicle.xlsx");
            System.out.println(file.getAbsolutePath());
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            String cellValue = sheet.getRow(4).getCell(0).getStringCellValue();
            System.out.println(cellValue);

            String Desc;
            Desc = "Login To App";
            CommonLibrary.loginWebApp(userName, password, Desc);
            //
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Logged in as " + userName, LogStatus.INFO, false);
            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();

            String AccNo = Desc.substring(9).trim();
            System.out.println(AccNo);
            Desc = "click on Transponders";
            FunctionLibrary.objDriver.findElement(By.xpath(xpathVehiclesTransponders)).click();

            Desc = "Start Upload Proces";
            findDynamicElement(By.xpath(xpathAddBulkVehicle), 120);
            scrollToView(By.xpath(xpathAddBulkVehicle));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAddBulkVehicle)).click();
            findDynamicElement(By.xpath(xpathUploadBtn), 120);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathUploadBtn)).sendKeys(file.getAbsolutePath());
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAddBulk)).click();
            findDynamicElement(By.xpath(xpathVehiclesAdded), 120);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathVehiclesAdded)).getText();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathConfrmBulk)).click();
            findDynamicElement(By.xpath(xpathUploadConfrmNew), 120);
            String vehiclesUploadMesage = FunctionLibrary.objDriver.findElement(By.xpath(xpathUploadConfrmNew)).getText();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, vehiclesUploadMesage, LogStatus.PASS, true);
            Desc = "click on Transponders";

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Added Vehicle", LogStatus.INFO, true);
            CommonLibrary.logoutWebApp();

            String uname = dataObj.get("Suname");
            String pwd = dataObj.get("SPWD");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying the changes in Siebel Application", LogStatus.INFO, false);
            CommonLibrary.loginSiebelAppWithWebAcc(siebelURL1, uname, pwd, dataObj.get("Browser Type"));
            CommonLibrary.searchForAccount(AccNo);

            FunctionLibrary.clickObject(xpathAccountInfoTab, "", "Clicking Account Info tab");

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathAccountInfoVehiclesTab, xpathAccountInfoTab, 10, 60);
            FunctionLibrary.clickObject(xpathAccountInfoVehiclesTab, "", "Clicking Account Info tab");

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathAccountInfoVehiclesTab, xpathAccountInfoTab, 10, 60);
            //Verifying under Devices history tab
            FunctionLibrary.clickObject(xpathVehicleHistoryTab, "", "CLick vehicle history tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathVehicleHistoryTable, xpathVehicleHistoryTab, 10, 60);

            FunctionLibrary.clickObject(xpathVehicleHistoryQueryBtn, "", "CLick Query button");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathStatusTxtBox, xpathVehicleHistoryQueryBtn, 10, 60);

            FunctionLibrary.clickObject(xpathPlateNbrElement, "", "CLick plate field");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathPlateNbrTxtBox, xpathPlateNbrElement, 10, 60);

            FunctionLibrary.setText(xpathPlateNbrTxtBox, cellValue, "Enter plate number");
            FunctionLibrary.clickObject(xpathVehicleHistoryGoBtn, "", "Click go button");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathVehicleHistoryQueryBtn, xpathVehicleHistoryGoBtn, 10, 60);
            String xpathVehicleHistoryTable = "//table[@summary=' Account Vehicle History List Applet']";
            WebElement elementWebTable = FunctionLibrary.getElement(xpathVehicleHistoryTable);
            int noOfRows = elementWebTable.findElements(By.tagName("tr")).size();
            System.out.println(noOfRows);
            if (noOfRows > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Vehicle is addedd", LogStatus.PASS, true);
            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Vehicle is not addedd", LogStatus.FAIL, true);
            }
            CommonLibrary.logoutSiebelApplicaiton();
        } catch (Exception e) {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Ocuured "+ e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }
    }

    public static void tcAccountMaintenanceWebACMW087() throws InterruptedException {
        try {
            String url = CommonLibrary.getSettingsSheetInfo().get("WEB_URL_MAIN_QA");
            CommonLibrary.launchBrowser(url, "Launch web App", dataObj.get("Browser Type"));
            String lastName_fristName = fNameLname();
            String serviceRequest = "1-58022951";
            String lname = lastName_fristName.split("_")[1];
            String fname = lastName_fristName.split("_")[0];
            //FunctionLibrary.objDriver.navigate().to(CommonLibrary.getSettingsSheetInfo().get("WEB_URL_QA").toString());
            new WebDriverWait(FunctionLibrary.objDriver, 120);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisibleWebapp(fnametxt, contactusLink, 10, 60);
            // FunctionLibrary.objDriver.findElement(By.xpath(contactusLink)).click();
            FunctionLibrary.objDriver.findElement(By.xpath(fnametxt)).sendKeys(fname);
            FunctionLibrary.objDriver.findElement(By.xpath(lnametxt)).sendKeys(lname);
            FunctionLibrary.selectDropDownListByValue(issueCategory, dataObj.get("Issuecategory"), "Selecting issueCategory");
            FunctionLibrary.selectDropDownListByValue(issue, dataObj.get("Issue"), "Selecting issueCategory");
            scrollToView(By.xpath(xpathEmailId));
            Thread.sleep(1500);
            scrollToView(By.xpath(xpathEmailId));
            FunctionLibrary.objDriver.findElement(By.xpath(emailId)).sendKeys(dataObj.get("Email"));
            FunctionLibrary.objDriver.findElement(By.xpath(retypeEmailId)).sendKeys(dataObj.get("Email"));
            FunctionLibrary.objDriver.findElement(By.xpath(phoneNo)).sendKeys(dataObj.get("Phoneno"));
            FunctionLibrary.objDriver.findElement(By.xpath(comment)).sendKeys(dataObj.get("Comment"));
            scrollToView(By.xpath(submit));
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisibleWebapp(message, submit, 10, 60);

            String text = FunctionLibrary.objDriver.findElement(By.xpath("//b")).getText();
            String[] messageConfirmation = text.split(" ");
            serviceRequest = messageConfirmation[12].replace('.', ' ').trim();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Service Request number:" + serviceRequest, LogStatus.INFO, true);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "CRM Expected: Case Created, Actual: Case Created", LogStatus.PASS, true);


            FunctionLibrary.objDriver.close();
            String uname = dataObj.get("Suname");
            String pwd = dataObj.get("SPWD");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying the changes in Siebel Application", LogStatus.INFO, false);
            CommonLibrary.loginSiebelAppWithWebAcc(siebelURL1, uname, pwd, dataObj.get("Browser Type"));

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathServiceRequestList, xpathServiceRequestTab, 10, 60
            );

            String desc;
            desc = "Clicking on Service request list";
            FunctionLibrary.clickObject(xpathServiceRequestList, "", desc);
            WebDriverWait wait1 = new WebDriverWait(FunctionLibrary.objDriver, 100);
            wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathView)));

            int dropdownlistCount = FunctionLibrary.getDropdownListCount(xpathView);

            System.out.print("dropdownlistCount:" + dropdownlistCount);
            if (dropdownlistCount > 1) {

                FunctionLibrary.selectDropDownList(xpathView, "All Service Requests", "Selecting All Service Requests from dropdown");
                Thread.sleep(10000);

                desc = "Clicking query button";
                FunctionLibrary.clickObject(xpathQuery, "", desc);

                WebDriverWait waitForStatus = new WebDriverWait(FunctionLibrary.objDriver, 120);
                // wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathServiceRequestId)));
                waitForStatus.until(ExpectedConditions.presenceOfElementLocated(By.xpath(queryStatus)));

                desc = "Clicking on Description";
                FunctionLibrary.clickObject(xpathServiceRequestFieldEnable, "", desc);

                desc = "Entering status";
                FunctionLibrary.setText(xpathServiceRequestTextField, serviceRequest, desc);


                desc = "Clicking on Go button";
                FunctionLibrary.clickObject(xpathServiceRequestGoButton, "", desc);

                WebDriverWait waitForCopycaseBtnEnable = new WebDriverWait(FunctionLibrary.objDriver, 120);
                // wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathServiceRequestId)));
                waitForCopycaseBtnEnable.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathOfSRCopy)));

                int rownumberwithServiceRequestone = FunctionLibrary.getRowNumberFromWebTable(serviceRequestListTable, serviceRequest, "");
                FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rownumberwithServiceRequestone, 4, dataObj.get("Issuecategory"), "Verifying queue", true);
                FunctionLibrary.verifyWebTableCellData(xpathServiceRequestTable, rownumberwithServiceRequestone, 5, dataObj.get("Issue"), "Verifying Subqueue", true);
                CommonLibrary.logoutSiebelApplicaiton();
            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Logged in user is not a supervisor", LogStatus.FAIL, true);
            }
        } catch (Exception e) {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Ocuurred " + e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }
    }

    public static void tcAccountMaintenanceWebACMW116() throws InterruptedException {
        try {

            String Desc;
            String userName = dataObj.get("User Name");
            String password = dataObj.get("Password");
            CommonLibrary.loginWebApp(userName, password, "Trying to Login with valid username and password ");
            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();
            String AccNo = Desc.substring(9).trim();

            CommonLibrary.logoutWebApp();

            String sql = "SELECT * FROM SIEBEL.T_WEB_SESSION WHERE ETC_ACCOUNT_ID='" + AccNo + "' ";
            System.out.println("Status " + sql);
            HashMap<String, String> inputAccountNumInfo = CommonLibrary.executeSqlAndGetOutputAsHashMap(sql);
            if (inputAccountNumInfo.size() > 0)
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "verification of T_WEB_SESSION is Success", LogStatus.PASS, false);
            else
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "verification of T_WEB_SESSION is failed", LogStatus.FAIL, false);
        } catch (Exception e) {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" +e.getMessage(), LogStatus.FAIL, true);
            //FunctionLibrary.closeAllActiveBrowser();
        }
    }

    public static void tcAccountMaintenanceWebACMW117() throws InterruptedException {
        try {
            String userName = dataObj.get("User Name");
            String password = dataObj.get("Password");
            String url = CommonLibrary.getSettingsSheetInfo().get("WEB_URL_MAIN_QA");
            CommonLibrary.launchBrowser(url, "Launch web App", dataObj.get("Browser Type"));
            /*FunctionLibrary.objDriver.findElement(By.xpath(xpathUserName)).sendKeys(userName);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathPassword)).sendKeys(password);*/
            findDynamicElement(By.xpath(xpathLoginButton),120);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Trying to Login with blank username and valid password "
                    + "User Name: blank" + " Password: " + password, LogStatus.INFO, true);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathLoginButton)).click();

            try {
                findDynamicElement(By.xpath("(.//*[@class='error_msg'])[1]"),120);
                String alertMessage = FunctionLibrary.objDriver.findElement(By.xpath("//*[contains(text(),'Please enter your Username')]")).getText();
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Login denied, alert message shown for not entering user name: " + alertMessage, LogStatus.PASS, true);
                //wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathUsernameErrorMessage)));
            } catch (Exception e) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Enter user name message not displayed", LogStatus.FAIL, true);
                FunctionLibrary.objDriver.close();
            }
           /* if (FunctionLibrary.objDriver.findElement(By.xpath(xpathUsernameErrorMessage)).isDisplayed()) {
                findDynamicElement(By.xpath(xpathUsernameErrorMessage), 120);
                String alertMessage = FunctionLibrary.objDriver.findElement(By.xpath(xpathUsernameErrorMessage)).getText();
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Login denied, alert message shown for not entering user name: " + alertMessage, LogStatus.PASS, true);
            }*/
            FunctionLibrary.objDriver.close();
        } catch (Exception e) {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" +e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }
    }

    public static void tcAccountMaintenanceWebACMW119() throws InterruptedException {
        try {

            String Desc;
            String userName = dataObj.get("User Name");
            String password = dataObj.get("Password");
            CommonLibrary.loginWebApp(userName, password, "Trying to Login with valid username and password ");
            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();
            String AccNo = Desc.substring(9).trim();

            CommonLibrary.logoutWebApp();

            String sql = "SELECT * FROM SIEBEL.T_WEB_SESSION WHERE ETC_ACCOUNT_ID='" + AccNo + "' ";
            System.out.println("Status " + sql);
            HashMap<String, String> inputAccountNumInfo = CommonLibrary.executeSqlAndGetOutputAsHashMap(sql);
            if (inputAccountNumInfo.size() > 0)
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "verification of T_WEB_SESSION is Success", LogStatus.PASS, false);
            else
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "verification of T_WEB_SESSION is failed", LogStatus.FAIL, false);
        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" +e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }
    }

    public static void tcAccountMaintenanceWebACMW120() throws InterruptedException {
        try {
            String Desc;
            String userName = dataObj.get("User Name");
            String password = dataObj.get("Password");
            CommonLibrary.loginWebApp(userName, password, "Trying to Login with valid username and password ");
            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();
            String AccNo = Desc.substring(9).trim();
            CommonLibrary.logoutWebApp();
            String sql = "SELECT * FROM SIEBEL.T_WEB_SESSION WHERE ETC_ACCOUNT_ID='" + AccNo + "' ";
            System.out.println("Status " + sql);
            HashMap<String, String> inputAccountNumInfo = CommonLibrary.executeSqlAndGetOutputAsHashMap(sql);
            if (inputAccountNumInfo.size() > 0)
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "verification of T_WEB_SESSION is Success", LogStatus.PASS, false);
            else
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "verification of T_WEB_SESSION is failed", LogStatus.FAIL, false);
        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" +e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }
    }

    public static void tcAccountMaintenanceWebACMW121() throws InterruptedException {
        try {
            String Desc;
            String userName = dataObj.get("User Name");
            String password = dataObj.get("Password");
            CommonLibrary.loginWebApp(userName, password, "Trying to Login with valid username and password ");
            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();
            String AccNo = Desc.substring(9).trim();

            CommonLibrary.logoutWebApp();

            String sql = "SELECT * FROM SIEBEL.T_WEB_SESSION WHERE ETC_ACCOUNT_ID='" + AccNo + "' ";
            System.out.println("Status " + sql);
            HashMap<String, String> inputAccountNumInfo = CommonLibrary.executeSqlAndGetOutputAsHashMap(sql);
            if (inputAccountNumInfo.size() > 0)
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "verification of T_WEB_SESSION is Success", LogStatus.PASS, false);
            else
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "verification of T_WEB_SESSION is failed", LogStatus.FAIL, false);
        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" +e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }
    }

    public static void tcAccountMaintenanceWebACMW122() throws InterruptedException {
        try {
            String Desc;
            String userName = dataObj.get("User Name");
            String password = dataObj.get("Password");
            CommonLibrary.loginWebApp(userName, password, "Trying to Login with valid username and password ");
            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();
            String AccNo = Desc.substring(9).trim();
            CommonLibrary.logoutWebApp();

            String sql = "SELECT * FROM SIEBEL.T_WEB_SESSION WHERE ETC_ACCOUNT_ID='" + AccNo + "' ";
            System.out.println("Status " + sql);
            HashMap<String, String> inputAccountNumInfo = CommonLibrary.executeSqlAndGetOutputAsHashMap(sql);
            if (inputAccountNumInfo.size() > 0)
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "verification of T_WEB_SESSION is Success", LogStatus.PASS, false);
            else
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "verification of T_WEB_SESSION is failed", LogStatus.FAIL, false);
        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" +e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }
    }

    public static void tcAccountMaintenanceWebACMW127() throws InterruptedException {
        try {
            String userName = dataObj.get("User Name");
            String password = dataObj.get("Password");
            String Desc;
            String url = CommonLibrary.getSettingsSheetInfo().get("WEB_URL_MAIN_QA");
            CommonLibrary.launchBrowser(url, "Launch web App", dataObj.get("Browser Type"));
            findDynamicElement(By.xpath(xpathUserName),120);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathUserName)).sendKeys(userName);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathPassword)).sendKeys(password);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Trying to login to Account Status - Closed, Account type - Rental RCSP pre, Valid Login."
                    + "User Name: " + userName + " Password: " + password, LogStatus.INFO, true);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathLoginButton)).click();

            WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 50);
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getAccNo)));
            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();
            Desc.substring(9).trim();
        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" +e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }
    }

    public static void tcAccountMaintenanceWebACMW154() throws Exception {
        try {
            String url = CommonLibrary.getSettingsSheetInfo().get("WEB_URL_MAIN_QA");
            CommonLibrary.launchBrowser(url, "Launch web App", dataObj.get("Browser Type"));
            //GEN_ENQ	http://10.36.20.2:9202/vector/account/home/generalViewInquiry.do
            FunctionLibrary.objDriver.navigate().to(CommonLibrary.getSettingsSheetInfo().get("GEN_ENQ").toString());

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisibleWebapp(casenumbertxt, caseradiobtn, 10, 150);

            FunctionLibrary.objDriver.findElement(By.xpath(casenumbertxt)).sendKeys(dataObj.get("Caseno"));
            FunctionLibrary.objDriver.findElement(By.xpath(lastname)).sendKeys(dataObj.get("Caselastname"));
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Case number and last name entered", LogStatus.INFO, true);

            FunctionLibrary.objDriver.findElement(By.xpath(casesubmit)).click();
            String srStatus = FunctionLibrary.getWebTableCellData(caseTable, 1, 2, "Getting cell value");
            FunctionLibrary.verifyWebTableCellData(caseTable, 1, 2, srStatus,
                    "Verifying SR-Status", true);

            String caseNumber = dataObj.get("Caseno");
            String sql = "SELECT * FROM siebel.T_MAILHOUSE_INTERFACE WHERE CASE_ID='" + caseNumber + "' ";
            System.out.println("Status " + caseNumber);
            HashMap<String, String> inputAccountNumInfo = CommonLibrary.executeSqlAndGetOutputAsHashMap(sql);
            if (inputAccountNumInfo.size() > 0)
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " DB T_WEB_SESSION is verified ", LogStatus.PASS, false);
            else
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "DB T_WEB_SESSION is verification failed", LogStatus.FAIL, false);

            FunctionLibrary.objDriver.close();
        } catch (Exception e) {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Ocuurred " + e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }

    }

    public static void tcAccountMaintenanceWebACMW172() throws Exception {
        try {
            String Desc;
            CommonLibrary.loginWebApp(dataObj.get("User Name"), dataObj.get("Password"), "Trying to Login with valid username and password ");

            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();
            String AccNo = Desc.substring(9).trim();
            findDynamicElement(By.xpath(xpathAccountProfile), 120);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAccountProfile)).click();
            // FunctionLibrary.objDriver.findElement(By.xpath(xpathEditAccountSettingLink)).click();
            findDynamicElement(By.xpath(xpathAccountProfileTxt), 120);
            findDynamicElement(By.xpath(xpathEditPasswordSecurityQALink), 120);
            scrollToView(By.xpath(xpathEditPasswordSecurityQALink));
            Thread.sleep(1000);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathEditPasswordSecurityQALink)).click();
            findDynamicElement(By.xpath(xpathOldPasswordTxtBox), 120);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathOldPasswordTxtBox)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathOldPasswordTxtBox)).sendKeys(dataObj.get("Password"));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathNewPasswordTxtBox)).sendKeys(dataObj.get("NewPassword"));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathRetypeNewPasswordTxtBox)).sendKeys(dataObj.get("NewPassword"));
            scrollToView(By.xpath(xpathOfPwdBtn));
            //FunctionLibrary.objDriver.findElement(By.xpath(updatebtn1)).click();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathOfPwdBtn)).click();
            findDynamicElement(By.xpath(xpathPasswordChangeSuccessAlert), 120);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expexted: Password should be reset" + "</br>" +
                    "Actual: Password is reset", LogStatus.PASS, true);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathUserName)).sendKeys(dataObj.get("User Name"));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathPassword)).sendKeys(dataObj.get("NewPassword"));
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Login using the new credential" + "</br>" +
                    " User Name: " + dataObj.get("User Name") + " Password: " + dataObj.get("NewPassword"), LogStatus.INFO, true);
            //FunctionLibrary.objDriver.findElement(By.xpath(xpathLoginButton)).click();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathPassword)).sendKeys(Keys.ENTER);
            //FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathLoginButton)));
            try {
                WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 50);
                wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathLoginHome)));
            } catch (Exception e) {

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Login not success", LogStatus.FAIL, true);
                FunctionLibrary.objDriver.close();
            }
            if (FunctionLibrary.objDriver.findElement(By.xpath(xpathLoginHome)).isDisplayed()) {

                Thread.sleep(1000);
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Successfully logged in.", LogStatus.PASS, true);
                // String  LoginMessage = "Success";
            }
            // FunctionLibrary.objDriver.findElement(By.xpath(xpathLogout)).click();
            CommonLibrary.logoutWebApp();
            String uname = dataObj.get("Suname");
            String pwd = dataObj.get("SPWD");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying the changes in Siebel Application", LogStatus.INFO, false);
            CommonLibrary.loginSiebelAppWithWebAcc(siebelURL1, uname, pwd, dataObj.get("Browser Type"));
            Desc = "Clicking Accounts tab";
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAccountsTab)).click();
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathAccountNumberTxtBox, xpathAccountsTab, 10, 60);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAccountNumberTxtBox)).sendKeys(AccNo);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Search for account number: " + AccNo, LogStatus.INFO, false);
            Desc = "Clicking go button";
            FunctionLibrary.objDriver.findElement(By.xpath(xpathGoBtn)).click();
            WebDriverWait wait8 = new WebDriverWait(FunctionLibrary.objDriver, 15);
            wait8.until(ExpectedConditions.elementToBeClickable(By.xpath(accountList)));

            try {
                FunctionLibrary.selectDropDownList(dropdownhistory, "History", "Selecting History tab");
            } catch (Exception e) {
                System.out.println("History is not in hidden icon");
                // FunctionLibrary.objDriver.findElement(By.xpath("//a[text()='History']")).click();
                FunctionLibrary.objDriver.findElement(By.xpath(historyLink)).click();
            }
            findDynamicElement(By.xpath(nonFinancialsHistoryTab), 120);
            FunctionLibrary.objDriver.findElement(By.xpath(nonFinancialsHistoryTab)).click();
            findDynamicElement(By.xpath(activitiesTable), 120);
            String XpathNonFinacialHistory = activitiesTableXpath;
            int rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNonFinacialHistory, "VIEW", "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Details Not Available in Non Financial History  </b> <br> <b> Non Financial History  Expected  : Not Available  Actual is: Not Available </b>"
                        , LogStatus.PASS, true);
                String sql = "SELECT * FROM siebel.T_MAILHOUSE_INTERFACE WHERE ACCOUNTNUMBER='" + AccNo + "' ";
                System.out.println("Status " + sql);
                HashMap<String, String> inputAccountNumInfo = CommonLibrary.executeSqlAndGetOutputAsHashMap(sql);
                if (inputAccountNumInfo.size() > 0)
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "verification of Email Sent is Success", LogStatus.PASS, false);
                else
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "verification of Email Sent is failed", LogStatus.FAIL, false);
            }
        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" +e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }
    }

    public static void tcAccountMaintenanceWebACMW176() throws Exception {
        try {
            //CommonLibrary.launchBrowser(CommonLibrary.getSettingsSheetInfo().get("URL_Web_QA1").toString(),"Launching siebel app", dataObj.get("Browser Type"));
            CommonLibrary.loginWebApp(dataObj.get("User Name"), dataObj.get("Password"), "Trying to Login with valid username and password ");
            findDynamicElement(By.xpath(xpathAccountProfile), 120);
            String Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();

            String AccNo = Desc.substring(9).trim();
            // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathAccountProfile)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAccountProfile)).click();
            findDynamicElement(By.xpath(xpathAccountProfileTxt), 120);
            //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathAccountProfileTxt)));
            findDynamicElement(By.xpath(xpathEditPasswordSecurityQALink), 120);
            scrollToView(By.xpath(xpathEditPasswordSecurityQALink));
            // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathEditPasswordSecurityQALink)));
            Thread.sleep(1000);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathEditPasswordSecurityQALink)).click();
            findDynamicElement(By.xpath(xpathOldPasswordTxtBox), 120);
            // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathOldPasswordTxtBox)));
            findDynamicElement(By.xpath(xpathOfChallengeQuestion), 120);
            // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathOfChallengeQuestion)));
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Security Question and answer before modification",
                    LogStatus.INFO, true);
            String challengeQuestionBefore = FunctionLibrary.objDriver.findElement(By.xpath(xpathOfChallengeQuestion))
                    .getAttribute("value");
            String challengeQABefore = FunctionLibrary.objDriver.findElement(By.xpath(xpathFirstSecurityQATxtBox))
                    .getAttribute("value");

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Security Question and answer before modification" + "</br>"
                    + "Security Question : " + challengeQuestionBefore + "</br>" + "Security Answer : " + challengeQABefore, LogStatus.INFO, false);

            FunctionLibrary.selectDropDownList(firstSecurityQuestionDropdown, dataObj.get("SecurityQuestion"), "Changing security question");
            FunctionLibrary.objDriver.findElement(By.xpath(xpathFirstSecurityQATxtBox)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathFirstSecurityQATxtBox)).sendKeys(dataObj.get("SecurityQuestionAnswer"));
            scrollToView(By.xpath(xpathOfPwdBtn));
            //FunctionLibrary.objDriver.findElement(By.xpath(updatebtn1)).click();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathOfPwdBtn)).click();
            try {
                // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathOfNoThanks)));
                Thread.sleep(3000);
                FunctionLibrary.objDriver.findElement(By.xpath(xpathOfNoThanks)).click();
            } catch (Exception e) {
                System.out.println(e);
            }
            findDynamicElement(By.xpath(xpathAccountDetailsUpdateSuccessAlert), 120);
            // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathAccountDetailsUpdateSuccessAlert)));
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expexted: Security Question and answer should be updated" + "</br>" +
                    "Actual: Security Question and answer are updated", LogStatus.PASS, true);
            findDynamicElement(By.xpath(xpathFirstSecurityQATxtBox), 120);
            scrollToView(By.xpath(xpathFirstSecurityQATxtBox));
            // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathFirstSecurityQATxtBox)));
            String challengeQuestion = FunctionLibrary.objDriver.findElement(By.xpath(xpathOfChallengeQuestion))
                    .getAttribute("value");
            String challengeQA = FunctionLibrary.objDriver.findElement(By.xpath(xpathFirstSecurityQATxtBox))
                    .getAttribute("value");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Security Question and answer after modification" + "</br>"
                    + "Security Question : " + challengeQuestion + "</br>" + "Security Answer : " + challengeQA, LogStatus.INFO, false);
            if (challengeQuestion.equalsIgnoreCase(dataObj.get("SecurityQuestion")) && challengeQA.equalsIgnoreCase(dataObj.get("SecurityQuestionAnswer"))) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Security Question and answer after modification",
                        LogStatus.INFO, true);
            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Security Question and answer after modification",
                        LogStatus.FAIL, true);
            }

            Thread.sleep(1000);
            CommonLibrary.logoutWebApp();
            //FunctionLibrary.objDriver.findElement(By.xpath(xpathLogout)).click();

            //Checking the changes in siebel

            String uname = dataObj.get("Suname");
            String pwd = dataObj.get("SPWD");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying the changes in Siebel Application", LogStatus.INFO, false);
            CommonLibrary.loginSiebelAppWithWebAcc(siebelURL1, uname, pwd, dataObj.get("Browser Type"));
            CommonLibrary.searchForAccount(AccNo);

            FunctionLibrary.clickObject(xpathAccountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathAccountInfoAddressesTab, xpathAccountInfoTab, 10, 60);
            String challengeQASiebel = FunctionLibrary.objDriver.findElement(By.xpath(xpathOfChallengeAnswer))
                    .getAttribute("value");
            String challengeQuestionSiebel = FunctionLibrary.objDriver.findElement(By.xpath(xpathOfChallengeQa))
                    .getAttribute("value");

            findDynamicElement(By.xpath(xpathOfHistory), 120);
            //  wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathOfHistory)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathOfHistory)).click();
            findDynamicElement(By.xpath(xpathNonFinancialHistory), 120);
            // wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathNonFinancialHistory)));
            FunctionLibrary.clickObject(xpathOfNonFinancialHistory, "", "");
            findDynamicElement(By.xpath(xpathOfChatMsg), 120);
            // wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathOfChatMsg)));
            String XpathNonFinacialHistory = activitiesTableXpath;
            // String XpathNonFinacialHistory=".*//*//**//*[@summary='Activities']";
            int rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNonFinacialHistory, "VIEW", "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Details Not Available in Non Financial History  </b> <br> <b> Non Financial History  Expected  : Not Available  Actual is: Not Available </b>"
                        , LogStatus.PASS, true);
            }
            if (challengeQuestionSiebel.equalsIgnoreCase(dataObj.get("SecurityQuestion")) && challengeQASiebel.equalsIgnoreCase(dataObj.get("SecurityQuestionAnswer"))) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Security Question and answer should be updated"
                        + "</br>" + "Actual: Security Question and answer is updated", LogStatus.PASS, true);
            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Security Question and answer after modification",
                        LogStatus.FAIL, true);
            }
            CommonLibrary.logoutSiebelApplicaiton();
        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" +e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }
    }

    public static void tcAccountMaintenanceWebACMW178() throws InterruptedException {
        try {
            String Desc;
            String userName = dataObj.get("User Name");
            String password = dataObj.get("Password");
            DateTime.now().getMillisOfSecond();
            DateTime.now().getSecondOfMinute();
            DateTime.now().getMillisOfSecond();
            CommonLibrary.loginWebApp(userName, password, "First time login");
            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();
        String newPin=securityPin(4);
            String AccNo = Desc.substring(9).trim();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAccountProfile)).click();
            findDynamicElement(By.xpath(xpathOfPAsswordLink), 120);
            // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathOfPAsswordLink)));
            scrollToView(By.xpath(xpathOfPAsswordLink));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathOfPAsswordLink)).click();
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisibleWebapp(pintxtbox, xpathEditSecurityQuestionProfile, 10, 60);
            scrollToView(By.xpath(xpathOfPwdBtn));
            //FunctionLibrary.objDriver.findElement(By.xpath(xpathEditSecurityQuestionProfile)).click();
            String oldPin = FunctionLibrary.objDriver.findElement(By.xpath(pintxtbox)).getAttribute("value");
            System.out.print("old Pin:" + oldPin);
            FunctionLibrary.scrollDown("", "Scroll down");
            FunctionLibrary.objDriver.findElement(By.xpath(pintxtbox)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(pintxtbox)).sendKeys(newPin);

            FunctionLibrary.objDriver.findElement(By.xpath(xpathOfPwdBtn)).click();
            findDynamicElement(By.xpath(xpathSuccess), 120);
            String op = FunctionLibrary.objDriver.findElement(By.xpath(xpathSuccess)).getText();
            System.out.println("text:++++++++" + op);

            if (op.contains(dataObj.get("Expected Result"))) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Successfully Updated the pin from: " + oldPin + " to: " + newPin, LogStatus.PASS, true);
            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Failed to update the pin: " + oldPin + " to: " + newPin, LogStatus.FAIL, true);
            }

            CommonLibrary.logoutWebApp();
            //
            String uname = dataObj.get("Suname");
            String pwd = dataObj.get("SPWD");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying the changes in Siebel Application", LogStatus.INFO, false);
            CommonLibrary.loginSiebelAppWithWebAcc(siebelURL1, uname, pwd, dataObj.get("Browser Type"));
            //CommonLibrary.searchForAccount(AccNo);
            Desc = "Clicking Accounts tab";
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAccountsTab)).click();
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathAccountNumberTxtBox, xpathAccountsTab, 10, 60);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAccountNumberTxtBox)).sendKeys(AccNo);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Search for account number: " + AccNo, LogStatus.INFO, false);
            Desc = "Clicking go button";
            FunctionLibrary.objDriver.findElement(By.xpath(xpathGoBtn)).click();
            WebDriverWait wait8 = new WebDriverWait(FunctionLibrary.objDriver, 15);
            wait8.until(ExpectedConditions.elementToBeClickable(By.xpath(accountList)));

            try {
                FunctionLibrary.selectDropDownList(dropdownhistory, "History", "Selecting History tab");
            } catch (Exception e) {
                System.out.println("History is not in hidden icon");
                findDynamicElement(By.xpath(historyLink), 120);
                //   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(historyLink)));
                FunctionLibrary.objDriver.findElement(By.xpath(historyLink)).click();
            }
            findDynamicElement(By.xpath(nonFinancialsHistoryTab), 120);
            //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(nonFinancialsHistoryTab)));
            FunctionLibrary.objDriver.findElement(By.xpath(nonFinancialsHistoryTab)).click();
            findDynamicElement(By.xpath(activitiesTable), 120);
            //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(activitiesTable)));
            String XpathNonFinacialHistory = activitiesTableXpath;
            int rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNonFinacialHistory, "VIEW", "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Details Not Available in Non Financial History  </b> <br> <b> Non Financial History  Expected  : Not Available  Actual is: Not Available </b>"
                        , LogStatus.PASS, true);
            }
        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" +e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }
    }

    public static void tcAccountMaintenanceWebACMW269() throws InterruptedException {
        try {
            String url = CommonLibrary.getSettingsSheetInfo().get("WEB_URL_MAIN_QA");
            CommonLibrary.launchBrowser(url, "Launch web App", dataObj.get("Browser Type"));
            String Desc;
            String lastName_fristName = fNameLname();
            String lname = lastName_fristName.split("_")[1];
            String fname = lastName_fristName.split("_")[0];
            // FunctionLibrary.objDriver.navigate().to(CommonLibrary.getSettingsSheetInfo().get("WEB_URL_QA").toString());
            FunctionLibrary.objDriver.findElement(By.xpath(contactusLink)).click();
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisibleWebapp(xpathFName, contactusLink, 10, 60);
            // FunctionLibrary.objDriver.findElement(By.xpath(contactusLink)).click();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathFName)).sendKeys(fname);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathLName)).sendKeys(lname);
            Desc = "Selecting issueCategory";
            FunctionLibrary.selectDropDownListByValue(xpathReasontoContact, dataObj.get("Issuecategory"), Desc);
            Desc = "Selecting issueCategory";
            FunctionLibrary.selectDropDownListByValue(xpathReason, dataObj.get("Issue"), Desc);
            scrollToView(By.xpath(xpathEmailId));
            Thread.sleep(1000);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathEmailId)).sendKeys(dataObj.get("Email"));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathRetypeEmailId)).sendKeys(dataObj.get("Email"));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathPhoneNumber)).sendKeys(dataObj.get("Phoneno"));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathFeedBack)).sendKeys(dataObj.get("Comment"));
            scrollToView(By.xpath(submit));
            FunctionLibrary.objDriver.findElement(By.xpath(submit)).click();
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisibleWebapp(message, submit, 10, 60);
            String text = FunctionLibrary.objDriver.findElement(By.xpath("//b")).getText();
            String[] messageConfirmation = text.split(" ");
            String serviceRequest = messageConfirmation[12].replace('.', ' ').trim();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Service Request number:" + serviceRequest, LogStatus.INFO, true);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "CRM Expected: Case Created, Actual: Case Created", LogStatus.PASS, false);

            String sql = "SELECT * FROM siebel.T_MAILHOUSE_INTERFACE WHERE CASE_ID='" + serviceRequest + "'";
            System.out.println("Status " + sql);
            HashMap<String, String> inputAccountNumInfo = CommonLibrary.executeSqlAndGetOutputAsHashMap(sql);
            if (inputAccountNumInfo.size() > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "verification of Email Sent is Success", LogStatus.PASS, false);
            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "verification of Email Sent is failed", LogStatus.FAIL, false);
            }
            FunctionLibrary.objDriver.close();
        } catch (Exception e) {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Ocuured " + e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }

    }

    public static void tcAccountMaintenanceWebACMW236() throws InterruptedException {
        try {
            String userName = dataObj.get("User Name");
            String password = dataObj.get("Password");
            String Desc;
            CommonLibrary.loginWebApp(userName, password, "Trying to Login with valid username and password ");
            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();

            String AccNo = Desc.substring(9).trim();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAccountProfile)).click();
            try {
                FunctionLibrary.clickObject(editProfileLink, "", "Click Edit Profile link");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            FunctionLibrary.clickObject(managePayementMethod, "", "Click manage payment methods link");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(addCreditCardBankAccNumLink,
                    managePayementMethod, 10, 60);
            int noOfCardsShowing = 0;
            try {
                noOfCardsShowing = FunctionLibrary.getWebTableRowCount(FunctionLibrary.objDriver.findElement(By.xpath(creditcardsTbl)));
            } catch (Exception e1) {
            /*// TODO Auto-generated catch block*/
                e1.printStackTrace();
            }
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "No of payment methods are showing: " + noOfCardsShowing, LogStatus.INFO, true);

            String secondPaymentType = FunctionLibrary.getWebTableCellData(creditcardsTbl, 2, 0, "Get the second payment type");
            String deleteIcon = "(//I[@class='fa fa-remove fa-smedium'])[1]";
            String deleteBtn = "//button[@name='btnSave']";
            String deleteTxtMsg = "//div[contains(text(),'Your Credit Card was deleted')]";

            FunctionLibrary.clickObject(deleteIcon, "", "Click delete icon to remove paytype");
            FunctionLibrary.clickObject(deleteBtn, "", "Click delete button to remove paytype");

            try {
                FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.
                        xpath(deleteTxtMsg)));

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                        "Expected: Message should be displayed - Your Credit Card was deleted. <br> Actual: Message is dispalyed",
                        LogStatus.PASS, true);

            } catch (TimeoutException e) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                        "Expected: Message should be displayed - Your Credit Card was deleted. <br> Actual: Message is not displayed",
                        LogStatus.FAIL, true);
            }

            CommonLibrary.logoutWebApp();

            dataObj.put("UserId", dataObj.get("Suname"));
            dataObj.put("Password", dataObj.get("SPWD"));

            CommonLibrary.loginSblWithTestScenarioSpecificUser(dataObj);

            String accountNum = AccNo;

            CommonLibrary.searchForAccount(accountNum);
            FunctionLibrary.objDriver.findElement(By.linkText(accountNum)).click();

            FunctionLibrary.waitForObject.until(ExpectedConditions.attributeToBe(By.xpath(SblCreateAccountPageProperties.accountCompanyNameTxtBox), "aria-readonly", "false"));
            FunctionLibrary.clickObject(SblAccountMaintenancePageObject.rebillInfoLink, "", "Click Rebill Info link");
            String replenishmentInfoListTbl = "//table[@summary='Replenishment Info List Applet']";
            int rowNum = FunctionLibrary.getRowNumberFromWebTable(replenishmentInfoListTbl, secondPaymentType, "Get the rownumber");

            if (rowNum == 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                        "Expected: Payment type should be deleted -" + secondPaymentType + "<br>. Actual: Deleted", LogStatus.PASS, true);
            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                        "Expected: Payment type should be deleted -" + secondPaymentType + "<br>. Actual: Deleted paytype from Web application is still showing up inn siebel",
                        LogStatus.FAIL, true);
            }
            FunctionLibrary.clickObject(SblAccountMaintenancePageObject.notesTab, "", "Notes");
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(SblAccountMaintenancePageObject.notesListNewBtn)));
            int rowNumber;
            String NotesDescription = secondPaymentType + " Rebill Pay Type record deleted";
            rowNumber = FunctionLibrary.getRowNumberFromWebTable(SblAccountMaintenancePageObject.xpathOfNotesTable, NotesDescription,
                    "get the row number");
            FunctionLibrary.verifyWebTableCellData(SblAccountMaintenancePageObject.xpathOfNotesTable, rowNumber, 10, "WEBUSER",
                    "Verify updated by user", true);

            CommonLibrary.logoutSiebelApplicaiton();
        } catch (Exception e) {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Ocuured " + e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }
    }

    public static void tcAccountMaintenanceWebACMW134() throws InterruptedException {
        try {
            ArrayList<String> details;
            String userName = "";
            String password = "";
            try {
                details= WebAccountCreation.newAccNumber();
                userName=details.get(0);
                password=details.get(1);
                System.out.println(userName);
                System.out.println(password);

            }
            catch (Exception e)
            {
                throw e;
            }
try {
    ExcelSheet.writeTestCaseData("FLCSS_Web_AccountMaintenance","Test_Scenarios",26,userName,"User Name");
    ExcelSheet.writeTestCaseData("FLCSS_Web_AccountMaintenance","Test_Scenarios",30,userName,"User Name");
    ExcelSheet.writeTestCaseData("FLCSS_Web_AccountMaintenance","Test_Scenarios",31,userName,"User Name");
    ExcelSheet.writeTestCaseData("FLCSS_Web_AccountMaintenance","Test_Scenarios",32,userName,"User Name");
    ExcelSheet.writeTestCaseData("FLCSS_Web_AccountMaintenance","Test_Scenarios",33,userName,"User Name");
    ExcelSheet.writeTestCaseData("FLCSS_Web_AccountMaintenance","Test_Scenarios",34,userName,"User Name");
    ExcelSheet.writeTestCaseData("FLCSS_Web_AccountMaintenance","Test_Scenarios",35,userName,"User Name");
    ExcelSheet.writeTestCaseData("FLCSS_Web_AccountMaintenance","Test_Scenarios",36,userName,"User Name");
    ExcelSheet.writeTestCaseData("FLCSS_Web_AccountMaintenance","Test_Scenarios",37,userName,"User Name");
    ExcelSheet.writeTestCaseData("FLCSS_Web_AccountMaintenance","Test_Scenarios",38,userName,"User Name");
    ExcelSheet.writeTestCaseData("FLCSS_Web_AccountMaintenance","Test_Scenarios",30,userName,"AccountNumber");
    ExcelSheet.writeTestCaseData("FLCSS_Web_AccountMaintenance","Test_Scenarios",31,userName,"AccountNumber");
    ExcelSheet.writeTestCaseData("FLCSS_Web_AccountMaintenance","Test_Scenarios",32,userName,"AccountNumber");
    ExcelSheet.writeTestCaseData("FLCSS_Web_AccountMaintenance","Test_Scenarios",33,userName,"AccountNumber");
    ExcelSheet.writeTestCaseData("FLCSS_Web_AccountMaintenance","Test_Scenarios",34,userName,"AccountNumber");
    ExcelSheet.writeTestCaseData("FLCSS_Web_AccountMaintenance","Test_Scenarios",35,userName,"AccountNumber");
    ExcelSheet.writeTestCaseData("FLCSS_Web_AccountMaintenance","Test_Scenarios",36,userName,"AccountNumber");
    ExcelSheet.writeTestCaseData("FLCSS_Web_AccountMaintenance","Test_Scenarios",37,userName,"AccountNumber");
    ExcelSheet.writeTestCaseData("FLCSS_Web_AccountMaintenance","Test_Scenarios",38,userName,"AccountNumber");
}
catch (Exception e)
{
    System.out.println(e.getMessage());
    throw e;
}
            CommonLibrary.isClosedAllBrowsers=true;
            String firstSecurityQuestion = dataObj.get("Security Question1");
            String firstSecurityAnswer = dataObj.get("Security Answer1");
            String secondSecurityQuestion = dataObj.get("Security Question2");
            String secondSecurityAnswer = dataObj.get("Security Answer2");
            String thirdSecurityQuestion = dataObj.get("Security Question3");
            String thirdSecurityAnswer = dataObj.get("Security Answer3");
            CommonLibrary.loginWebApp(userName, password, "First time login");

            try {
                findDynamicElement(By.xpath("//b[text()='Setup your your security questions and click UPDATE to save your changes.']"), 120);
               /* WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 50);
                wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathSecurityQuestions)));*/
                FunctionLibrary.selectDropDownListByValue(xpathFirstSecurityQuestion, firstSecurityQuestion, "");
                FunctionLibrary.objDriver.findElement(By.xpath(xpathFirstSecurityAnswer)).clear();
                FunctionLibrary.objDriver.findElement(By.xpath(xpathFirstSecurityAnswer)).sendKeys(firstSecurityAnswer);
                FunctionLibrary.selectDropDownListByValue(xpathSecondSecurityQuestion, secondSecurityQuestion, "");
                FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondSecurityAnswer)).sendKeys(secondSecurityAnswer);
                FunctionLibrary.selectDropDownListByValue(xpathThirdSecurityQuestion, thirdSecurityQuestion, "");
                FunctionLibrary.objDriver.findElement(By.xpath(xpathThirdSecurityAnswer)).sendKeys(thirdSecurityAnswer);
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Adding security questions and answers", LogStatus.INFO, true);
                //FunctionLibrary.objDriver.findElement(By.xpath("//button[@name='btnUpdate']")).click();
                scrollToView(By.xpath(xpathUpdateButton));
                FunctionLibrary.objDriver.findElement(By.xpath(xpathUpdateButton)).click();
                findDynamicElement(By.xpath(xpathAccountProfile), 120);
                FunctionLibrary.objDriver.findElement(By.xpath(xpathAccountProfile)).click();
                findDynamicElement(By.xpath(xpathProfile), 120);
                try {
                    findDynamicElement(By.xpath(xpathEditSecurityQuestionProfile), 120);
                    FunctionLibrary.objDriver.findElement(By.xpath(xpathEditSecurityQuestionProfile)).click();

                } catch (Exception e) {
                    FunctionLibrary.objDriver.findElement(By.xpath(xpathEditSecurityQuestionProfile)).click();
                }
                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathFirstSecurityAnswer, xpathEditSecurityQuestionProfile, 10, 60);
                findDynamicElement(By.xpath(xpathFirstSecurityAnswer), 120);


                new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();

                FunctionLibrary.verifyDropDownListSelectedOption(xpathFirstSecurityQuestion, firstSecurityQuestion, "Verifying the first dropdown");
                FunctionLibrary.verifyTextBoxValue("" + xpathFirstSecurityAnswer, firstSecurityAnswer, "Verifying first dropdown answer", false);

                FunctionLibrary.verifyDropDownListSelectedOption(xpathSecondSecurityQuestion, secondSecurityQuestion, "Verifying the second dropdown");
                FunctionLibrary.verifyTextBoxValue("" + xpathSecondSecurityAnswer, secondSecurityAnswer, "Verifying second dropdown answer", false);

                FunctionLibrary.verifyDropDownListSelectedOption(xpathThirdSecurityQuestion, thirdSecurityQuestion, "Verifying the third dropdown");
                FunctionLibrary.verifyTextBoxValue("" + xpathThirdSecurityAnswer, thirdSecurityAnswer, "Verifying third dropdown answer", true);
                String Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();
                String AccNo = Desc.substring(9).trim();
                CommonLibrary.logoutWebApp();

                String sql = "SELECT * FROM SIEBEL.T_WEB_SESSION WHERE ETC_ACCOUNT_ID='" + AccNo + "' ";
                System.out.println("Status " + sql);
                HashMap<String, String> inputAccountNumInfo = CommonLibrary.executeSqlAndGetOutputAsHashMap(sql);
                if (inputAccountNumInfo.size() > 0)
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "verification of T_WEB_SESSION is Success", LogStatus.PASS, false);
                else
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "verification of T_WEB_SESSION is failed", LogStatus.FAIL, false);

            } catch (Exception e) {
                System.out.println(e.getMessage());

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unable to add security questions and answers", LogStatus.FAIL, true);
            }
        } catch (Exception e) {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" + e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }
    }

    public static void tcAccountMaintenanceWebACMW156() throws InterruptedException {
        try {
            String url = CommonLibrary.getSettingsSheetInfo().get("WEB_URL_MAIN_QA");
            CommonLibrary.launchBrowser(url, "Launch web App", dataObj.get("Browser Type"));
            String userName = dataObj.get("User Name");
            String zipCode = dataObj.get("Zip Code");
            String invalidAnswer = dataObj.get("Security Answer1");
            String successMessage = dataObj.get("Alert Message");
            String newPassword = dataObj.get("Password");
            JavascriptExecutor js = (JavascriptExecutor) FunctionLibrary.objDriver;
            System.out.println(js.executeScript("return document.readyState").toString().equals("complete"));


            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Clicking on 'Forgot your password or username?' link", LogStatus.INFO, false);
            WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 50);

            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisibleWebapp(xpathResetPasswordText1, xpathForgotPassword, 10, 60);

            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathResetPasswordText1)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathUserName)).sendKeys(userName);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathpZipCode)).sendKeys(zipCode);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Providing Username and zip code", LogStatus.INFO, true);
            Thread.sleep(2000);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSubmitButton1)).click();
            System.out.println("1");
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathResetPasswordText2)));
            System.out.println("2");
            FunctionLibrary.objDriver.findElement(By.xpath(xpathFirstSecurityAnswer)).sendKeys(invalidAnswer);
            System.out.println("3");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Providing answer for security question", LogStatus.INFO, true);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSubmitButton2)).click();

            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathPasswordForgot)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathPasswordForgot)).sendKeys(newPassword);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathRePasswordForgot)).sendKeys(newPassword);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSaveButton)).click();

            try {
                wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathChangePasswordSuccess)));
                wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathLoginButton)));

                String message = FunctionLibrary.objDriver.findElement(By.xpath(xpathChangePasswordSuccess)).getText();
                System.out.println(message);

                if (successMessage.contains(message)) {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Password changed successfully", LogStatus.PASS, true);
                } else {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Couldn't change the password", LogStatus.FAIL, true);
                }
            } catch (Exception e) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Couldn't change the password", LogStatus.FAIL, true);
//            System.out.println(e.getMessage());
            }
            FunctionLibrary.objDriver.close();
        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" +e.getMessage(), LogStatus.FAIL, true);
            // FunctionLibrary.objDriver.close();
            FunctionLibrary.closeAllActiveBrowser();
        }
    }

    public static void tcAccountMaintenanceWebACMW167() throws InterruptedException {
        try {
            String Desc;
            String userName = dataObj.get("User Name");
            String password = dataObj.get("Password");
            String oldUsername = "";
            String newUsername = "user" + DateTime.now().getMillisOfSecond() + DateTime.now().getSecondOfMinute() + DateTime.now().getMillisOfSecond();
            CommonLibrary.loginWebApp(userName, password, "Login with valid username and password");
            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();

            String AccNo = Desc.substring(9).trim();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAccountProfile)).click();
            findDynamicElement(By.xpath(xpathProfile), 120);
            oldUsername = FunctionLibrary.objDriver.findElement(By.xpath(xpathUsername2)).getAttribute("value");
            FunctionLibrary.objDriver.findElement(By.xpath(xpathUsername2)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathUsername2)).sendKeys(newUsername);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Changing the username from: " + oldUsername + " to: " + newUsername, LogStatus.INFO, true);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathUpdateButton2)).click();
            findDynamicElement(By.xpath(xpathSuccess), 120);
            String op = FunctionLibrary.objDriver.findElement(By.xpath(xpathSuccess)).getText();
            System.out.println(op);
            if (op.contains("Account Details Updated Successfully")) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Changed the username from: " + oldUsername + " to: " + newUsername + " successfully", LogStatus.PASS, true);
            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Failed to change the username from: " + oldUsername + " to: " + newUsername, LogStatus.FAIL, true);
            }
            CommonLibrary.logoutWebApp();

            String uname = dataObj.get("Suname");
            String pwd = dataObj.get("SPWD");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying the changes in Siebel Application", LogStatus.INFO, false);
            CommonLibrary.loginSiebelAppWithWebAcc(siebelURL1, uname, pwd, dataObj.get("Browser Type"));

            //CommonLibrary.searchForAccount(AccNo);
            Desc = "Clicking Accounts tab";
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAccountsTab)).click();
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathAccountNumberTxtBox, xpathAccountsTab, 10, 60);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAccountNumberTxtBox)).sendKeys(AccNo);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Search for account number: " + AccNo, LogStatus.INFO, false);
            Desc = "Clicking go button";
            FunctionLibrary.objDriver.findElement(By.xpath(xpathGoBtn)).click();
            findDynamicElement(By.xpath(accountList), 120);
            try {
                FunctionLibrary.selectDropDownList(dropdownhistory, "History", "Selecting History tab");
            } catch (Exception e) {
                System.out.println("History is not in hidden icon");
                findDynamicElement(By.xpath(historyLink), 120);
                FunctionLibrary.objDriver.findElement(By.xpath(historyLink)).click();
            }
            findDynamicElement(By.xpath(nonFinancialsHistoryTab), 120);
            FunctionLibrary.objDriver.findElement(By.xpath(nonFinancialsHistoryTab)).click();
            findDynamicElement(By.xpath(activitiesTable), 120);
            String XpathNonFinacialHistory = activitiesTableXpath;
            int rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNonFinacialHistory, "VIEW", "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " <B> Details Not Available in Non Financial History  </b> <br> <b> Non Financial History  Expected  : Not Available  Actual is: Not Available </b>"
                        , LogStatus.PASS, true);
            }
            CommonLibrary.logoutSiebelApplicaiton();
        } catch (Exception e) {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" +e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }
    }

    public static void tcAccountMaintenanceWebACMW183() throws Exception {
        try {

            String userName = dataObj.get("User Name");
            String password = dataObj.get("Password");
            String oldAddressLine1 = "";
            String oldAddressLine2 = "";
            String oldCity = "";
            String oldZipCode = "";
            String oldState = "";
            String oldCountry = "";
            String newAddressLine1 = dataObj.get("New Address Line1");
            String newAddressLine2 = dataObj.get("New Address Line2");
            String newCity = dataObj.get("New City");
            String newState = dataObj.get("New State");
            String newZipCode = dataObj.get("New Zip Code");
            dataObj.get("New Zip Code Plus");
            String newCountry = dataObj.get("New Country");
            String updatedAddressLine1 = "";
            String updatedAddressLine2 = "";
            String updatedCity = "";
            String updatedState = "";
            String updatedZipCode = "";
            String updatedCountry = "";
            CommonLibrary.loginWebApp(userName, password, "Login with valid username and password");

            String Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();
            String AccNo = Desc.substring(9).trim();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAccountProfile)).click();
            WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 50);
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathProfile)));
scrollToView(By.xpath(xpathZipPlus));
            oldAddressLine1 = FunctionLibrary.objDriver.findElement(By.xpath(xpathAddressLine1)).getAttribute("value");
            oldAddressLine2 = FunctionLibrary.objDriver.findElement(By.xpath(xpathAddressLine2)).getAttribute("value");
            oldCity = FunctionLibrary.objDriver.findElement(By.xpath(xpathCity)).getAttribute("value");
            oldZipCode = FunctionLibrary.objDriver.findElement(By.xpath(xpathZip)).getAttribute("value");
            FunctionLibrary.objDriver.findElement(By.xpath(xpathZipPlus)).getAttribute("value");
            oldState = FunctionLibrary.dropDownListSelectedOption(xpathState, "Selected value");
            oldCountry = FunctionLibrary.dropDownListSelectedOption(xpathCountry, "Selected value");
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAddressLine1)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAddressLine1)).sendKeys(newAddressLine1);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAddressLine2)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAddressLine2)).sendKeys(newAddressLine2);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathCity)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathCity)).sendKeys(newCity);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathZip)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathZip)).sendKeys(newZipCode);
            FunctionLibrary.selectDropDownListByValue(xpathCountry, newCountry, "Selecting Country");
            FunctionLibrary.selectDropDownListByValue(xpathState, newState, "Selecting state");

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Changing the mailing Address " + "<br>" +
                    "AddressLine1 old value: " + oldAddressLine1 + ", AddressLine1 new value: " + newAddressLine1 + "<br>" +
                    "AddressLine2 old value: " + oldAddressLine2 + ", AddressLine2 new value: " + newAddressLine2 + "<br>" +
                    "City old value: " + oldCity + ", City new value: " + newCity + "<br>" +
                    "Zip code old value: " + oldZipCode + ", Zip code new value: " + newZipCode + "<br>" +
                    "State old value: " + oldState + ", State new value: " + newState + "<br>" +
                    "Country old value: " + oldCountry + ", Country new value: " + newCountry + "<br>", LogStatus.INFO, true);
scrollToView(By.xpath(xpathUpdateButton2));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathUpdateButton2)).click();
            try {
                WebDriverWait wait1 = new WebDriverWait(FunctionLibrary.objDriver, 120);
                wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStandardizeRadioButton)));

                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisibleWebapp(xpathSuccess,
                        xpathStandardizeOkButton, 10, 60);

            } catch (Exception e) {
                System.out.println("Alert not present");
            }

            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathSuccess)));

            String accountDetailUpdationMsg = FunctionLibrary.objDriver.findElement(By.xpath(xpathSuccess)).getText();
            System.out.println(accountDetailUpdationMsg);

            if (accountDetailUpdationMsg.contains("Account Details Updated Successfully")) {
                updatedAddressLine1 = FunctionLibrary.objDriver.findElement(By.xpath(xpathAddressLine1)).getAttribute("value");
                updatedAddressLine2 = FunctionLibrary.objDriver.findElement(By.xpath(xpathAddressLine2)).getAttribute("value");
                updatedCity = FunctionLibrary.objDriver.findElement(By.xpath(xpathCity)).getAttribute("value");
                updatedZipCode = FunctionLibrary.objDriver.findElement(By.xpath(xpathZip)).getAttribute("value");
                FunctionLibrary.objDriver.findElement(By.xpath(xpathZipPlus)).getAttribute("value");
                updatedState = FunctionLibrary.dropDownListSelectedOption(xpathState, "Selected value");
                updatedCountry = FunctionLibrary.dropDownListSelectedOption(xpathCountry, "Selected value");

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Updated the mailing Address", LogStatus.PASS, true);

                if (newAddressLine1.equalsIgnoreCase(updatedAddressLine1)) {

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Address Line1, Expected value: " +
                            newAddressLine1 + ", Actual value: " + updatedAddressLine1, LogStatus.PASS, false);
                } else {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Address line1, Expected value: " +
                            newAddressLine1 + ", Actual value: " + updatedAddressLine1, LogStatus.FAIL, false);
                }

                if (newAddressLine2.equalsIgnoreCase(updatedAddressLine2)) {

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Address Line2, Expected value: " +
                            newAddressLine2 + ", Actual value: " + updatedAddressLine2, LogStatus.PASS, false);
                } else {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Address line2, Expected value: " +
                            newAddressLine2 + ", Actual value: " + updatedAddressLine2, LogStatus.FAIL, false);
                }

                if (newCity.equalsIgnoreCase(updatedCity)) {

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "City, Expected value: " +
                            newCity + ", Actual value: " + updatedCity, LogStatus.PASS, false);
                } else {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "City, Expected value: " +
                            newCity + ", Actual value: " + updatedCity, LogStatus.FAIL, false);
                }

                if (newState.equalsIgnoreCase(updatedState)) {

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "State, Expected value: " +
                            newState + ", Actual value: " + updatedState, LogStatus.PASS, false);
                } else {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "State, Expected value: " +
                            newState + ", Actual value: " + updatedState, LogStatus.FAIL, false);
                }

                if (newCountry.equalsIgnoreCase(updatedCountry)) {

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Country, Expected value: " +
                            newCountry + ", Actual value: " + updatedCountry, LogStatus.PASS, false);
                } else {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Country, Expected value: " +
                            newCountry + ", Actual value: " + updatedCountry, LogStatus.FAIL, false);
                }

                if (newZipCode.equalsIgnoreCase(updatedZipCode)) {

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Zip code, Expected value: " +
                            newZipCode + ", Actual value: " + updatedZipCode, LogStatus.PASS, false);
                } else {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Zip code, Expected value: " +
                            newZipCode + ", Actual value: " + updatedZipCode, LogStatus.FAIL, false);
                }
            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Failed to change the username from: ", LogStatus.FAIL, true);
            }
            String sql = "SELECT * FROM siebel.T_MAILHOUSE_INTERFACE WHERE ACCOUNTNUMBER='" + AccNo + "' ";
            System.out.println("Status " + sql);
            HashMap<String, String> inputAccountNumInfo = CommonLibrary.executeSqlAndGetOutputAsHashMap(sql);
            if (inputAccountNumInfo.size() > 0)
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "verification of Email Sent is Success", LogStatus.PASS, false);
            else
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "verification of Email Sent is failed", LogStatus.FAIL, false);
            CommonLibrary.logoutWebApp();

        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" +e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }
    }

    public static void tcAccountMaintenanceWebACMW168() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 120);
            String userName = dataObj.get("User Name");
            String password = dataObj.get("Password");
            String oldEamilAddress1 = "";
            String oldEmailAddress2 = "";
            String newEmailAddress1 = dataObj.get("Primary Email");
            String newEmailAddress2 = dataObj.get("Secondary Email");
            String updatedEmailAddress1 = "";
            String updatedEmailAddress2 = "";
            String Desc = "";
            String successMessage = dataObj.get("Alert Message");

            CommonLibrary.loginWebApp(userName, password, "Login with valid username and password");
            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();
            String AccNo = Desc.substring(9).trim();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAccountProfile)).click();
            WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 50);
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathProfile)));

            oldEamilAddress1 = FunctionLibrary.objDriver.findElement(By.xpath(xpathEmail)).getAttribute("value");
            oldEmailAddress2 = FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondaryEmail)).getAttribute("value");
            FunctionLibrary.objDriver.findElement(By.xpath(xpathEmail)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathEmail)).sendKeys(newEmailAddress1);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathReEmail)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathReEmail)).sendKeys(newEmailAddress1);
            try {
                FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondaryEmail)).clear();
                FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondaryEmail)).sendKeys(newEmailAddress2);
                FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondaryReEmail)).clear();
                FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondaryReEmail)).sendKeys(newEmailAddress2);
            } catch (Exception e) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Selected account doesn't have secondary email, please select an account with " +
                        "secondary email and continue", LogStatus.FAIL, true);
            }

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Changing the primary and secondary email address " + "<br>" +
                    "Primary e-mail address old value: " + oldEamilAddress1 + ", Primary e-mail new value: " + newEmailAddress1 + "<br>" +
                    "Secondary e-mail old value: " + oldEmailAddress2 + ", Secondary e-mail new value: " + newEmailAddress2 + "<br>", LogStatus.INFO, true);
            try {
                scrollToView(By.xpath(xpathUpdateButton2));
                FunctionLibrary.objDriver.findElement(By.xpath(xpathUpdateButton2)).click();
            } catch (Exception e) {
                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisibleWebapp(xpathSuccess, xpathUpdateButton2, 10, 60);
            }

            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathSuccess)));

            String op = FunctionLibrary.objDriver.findElement(By.xpath(xpathSuccess)).getText();
            System.out.println(op);

            if (op.contains(successMessage)) {
                updatedEmailAddress1 = FunctionLibrary.objDriver.findElement(By.xpath(xpathEmail)).getAttribute("value");
                updatedEmailAddress2 = FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondaryEmail)).getAttribute("value");


                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Updated primary and secondary email address", LogStatus.PASS, true);

                if (newEmailAddress1.equalsIgnoreCase(updatedEmailAddress1)) {

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Primary e-mail address, Expected value: " +
                            newEmailAddress1 + ", Actual value: " + updatedEmailAddress1, LogStatus.PASS, false);
                } else {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Primary e-mail address, Expected value: " +
                            newEmailAddress1 + ", Actual value: " + updatedEmailAddress1, LogStatus.FAIL, false);
                }

                if (newEmailAddress2.equalsIgnoreCase(updatedEmailAddress2)) {

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Secondary e-mail address, Expected value: " +
                            newEmailAddress2 + ", Actual value: " + updatedEmailAddress2, LogStatus.PASS, false);
                } else {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Secondary e-mail address, Expected value: " +
                            newEmailAddress2 + ", Actual value: " + updatedEmailAddress2, LogStatus.FAIL, false);
                }
            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Failed to update the mail address ", LogStatus.FAIL, true);
            }
            CommonLibrary.logoutWebApp();
            String uname = dataObj.get("Suname");
            String pwd = dataObj.get("SPWD");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying the changes in Siebel Application", LogStatus.INFO, false);
            CommonLibrary.loginSiebelAppWithWebAcc(siebelURL1, uname, pwd, dataObj.get("Browser Type"));
            CommonLibrary.searchForAccount(AccNo);
            FunctionLibrary.clickObject(xpathAccountInfoTab, "", "Clicking Account Info tab");
            String updatedEmail = FunctionLibrary.objDriver.findElement(By.xpath(xpathOfEmailAddress)).getText();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Updated Email= " + updatedEmail, LogStatus.PASS, true);
            wait = new WebDriverWait(FunctionLibrary.objDriver, 120);
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    xpathAccountInfoVehiclesTab, xpathAccountInfoTab, 10, 60);
            try {
                FunctionLibrary.selectDropDownList(dropdownhistory, "History", "Selecting History tab");
            } catch (Exception e) {
                System.out.println("History is not in hidden icon");
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath
                        (historyLink)));
                FunctionLibrary.objDriver.findElement(By.xpath(historyLink)).click();
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(nonFinancialsHistoryTab)));
            FunctionLibrary.objDriver.findElement(By.xpath(nonFinancialsHistoryTab)).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(activitiesTable)));
            String XpathNonFinacialHistory = activitiesTableXpath;
            int rowNumber = FunctionLibrary.getRowNumberFromWebTable(XpathNonFinacialHistory, "VIEW", "get the row number");
            if (rowNumber > 0) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying Non-Financial History", LogStatus.PASS, true);
            }
            CommonLibrary.logoutSiebelApplicaiton();
            FunctionLibrary.closeAllActiveBrowser();
            String sql = "SELECT * FROM siebel.T_MAILHOUSE_INTERFACE WHERE ACCOUNTNUMBER='" + AccNo + "' ";
            System.out.println("Status " + sql);
            HashMap<String, String> inputAccountNumInfo = CommonLibrary.executeSqlAndGetOutputAsHashMap(sql);
            if (inputAccountNumInfo.size() > 0)
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "verification of Email Sent is Success", LogStatus.PASS, false);
            else
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "verification of Email Sent is failed", LogStatus.FAIL, false);

        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" +e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }
    }

    public static void tcAccountMaintenanceWebACMW084() throws InterruptedException {
        try {
            String userName = dataObj.get("User Name");
            String password = dataObj.get("Password");
            String Desc;
            Desc = "Login To App";
            CommonLibrary.loginWebApp(userName, password, Desc);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Logged in as " + userName, LogStatus.INFO, false);
            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();
            String AccNo = Desc.substring(9).trim();
            System.out.println(AccNo);
            Desc = "click on Transponders";
            FunctionLibrary.objDriver.findElement(By.xpath(xpathVehiclesTransponders)).click();
            //FunctionLibrary.clickObject(xpathVehiclesTransponders, "", Desc);
            findDynamicElement(By.xpath(scrollToTranspnder), 120);
            scrollToView(By.xpath(scrollToTranspnder));
            String TransponderId = FunctionLibrary.objDriver.findElement(By.xpath(xpathVehicleTagNoOfVehicleLostStolen)).getText();
            System.out.println(TransponderId);
            Desc = "mark devices as stolen or lost";
            FunctionLibrary.selectDropDownListByValue(xpathVehicleLostStolen, ReportLostorStolenValue, Desc);
            Desc = "mark devices as lost";
            Thread.sleep(4000);
            findDynamicElement(By.xpath(xpathTypeOfLostStolen), 120);
            FunctionLibrary.selectDropDownListByValue(xpathTypeOfLostStolen, "Lost", Desc);
            findDynamicElement(By.xpath(xpathContinueBtn), 120);
            Desc = "click on Continue";
            FunctionLibrary.objDriver.findElement(By.xpath(xpathContinueBtn)).click();
            Desc = "click on Accept Conditions";
            // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathAgreeChk)));
            scrollToView(By.xpath(icheckboxMinimal));
            try {
                FunctionLibrary.objDriver.navigate().refresh();
                scrollToView(By.xpath(icheckboxMinimal));
                // WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(divIcheckboxMinimal));
                WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(xpathOfIcheckMail));

                System.out.println("click try in try block");
                element.click();
            } catch (Exception e) {
                System.out.println("click try in catch block");
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(divIcheckboxMinimal)));
                // FunctionLibrary.objDriver.findElement(By.xpath(xpathOfIcheckMail)).click();
            }
            System.out.println("clicked");
            Desc = "click on submit";
            FunctionLibrary.objDriver.findElement(By.xpath(xpathStolenLostBtn)).click();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Submitted the lost transponder ", LogStatus.PASS, true);
            Desc = "Logout from App";
            CommonLibrary.logoutWebApp();
            String uname = dataObj.get("Suname");
            String pwd = dataObj.get("SPWD");
            CommonLibrary.loginSiebelAppWithWebAcc(siebelURL1, uname, pwd, dataObj.get("Browser Type"));
            //CommonLibrary.searchForAccount(AccNo);
            Desc = "Clicking Accounts tab";
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAccountsTab)).click();
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathAccountNumberTxtBox, xpathAccountsTab, 10, 60);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAccountNumberTxtBox)).sendKeys(AccNo);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Search for account number: " + AccNo, LogStatus.INFO, false);
            Desc = "Clicking go button";
            FunctionLibrary.objDriver.findElement(By.xpath(xpathGoBtn)).click();
            findDynamicElement(By.xpath(accountList), 120);
            Desc = "Clicking Devices tab";
            FunctionLibrary.objDriver.findElement(By.xpath(accountCSN)).click();
            // FunctionLibrary.clickObject("//a[contains(@name,'CSN')]", "", "Clicking on Account number link");
            FunctionLibrary.objDriver.findElement(By.xpath(deviceTab)).click();
            findDynamicElement(By.xpath(deviceHistory), 120);
            //FunctionLibrary.clickObject("//a[contains(text(),'Device History')]","","Click Device history");
            FunctionLibrary.objDriver.findElement(By.xpath(deviceHistory)).click();
            String xpathOfDeviceHistoryTable = deviceHistoryList;
            int rowNumberWithDeviceNumber = 1;
            String transponderData = FunctionLibrary.getWebTableCellData(xpathOfDeviceHistoryTable, 1, 7, "");
            System.out.println("Transponder data is " + transponderData);
            System.out.println(transponderData);
            if (transponderData.equalsIgnoreCase("LOST")) {
                System.out.println("First Row");
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "CRM Validation Siebel Verified Expected: Yes, Actual: Yes", LogStatus.PASS, false);
                FunctionLibrary.verifyWebTableCellData(xpathOfDeviceHistoryTable, rowNumberWithDeviceNumber, 7, "LOST", "Verifying status", true);
            } else {
                rowNumberWithDeviceNumber = FunctionLibrary.getRowNumberFromWebTable(xpathOfDeviceHistoryTable, TransponderId, "get the row number");
                System.out.println(rowNumberWithDeviceNumber);
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "CRM Validation Siebel Verified Expected: Yes, Actual: Yes", LogStatus.PASS, false);
                FunctionLibrary.verifyWebTableCellData(xpathOfDeviceHistoryTable, rowNumberWithDeviceNumber, 7, "LOST", "Verifying status", true);
                System.out.println("second Row");
            }
            CommonLibrary.logoutSiebelApplicaiton();
            FunctionLibrary.closeAllActiveBrowser();
        } catch (Exception e) {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" +e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }
    }

    public static void tcAccountMaintenanceWebACMW085() throws InterruptedException {
        try {
            String userName = dataObj.get("User Name");
            String password = dataObj.get("Password");
            String Desc;
            Desc = "Login To App";
            CommonLibrary.loginWebApp(userName, password, Desc);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Logged in as " + userName, LogStatus.INFO, false);
            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();
            String AccNo = Desc.substring(9).trim();
            System.out.println(AccNo);
            Desc = "click on Transponders";
            FunctionLibrary.objDriver.findElement(By.xpath(xpathVehiclesTransponders)).click();
            //FunctionLibrary.clickObject(xpathVehiclesTransponders, "", Desc);
            findDynamicElement(By.xpath(scrollToTranspnder), 120);
            scrollToView(By.xpath(scrollToTranspnder));
            String TransponderId = FunctionLibrary.objDriver.findElement(By.xpath(xpathVehicleTagNoOfVehicleLostStolen)).getText();
            System.out.println(TransponderId);
            Desc = "mark devices as stolen or lost";
            FunctionLibrary.selectDropDownListByValue(xpathVehicleLostStolen, ReportLostorStolenValue, Desc);
            Desc = "mark devices as STOLEN";
            Thread.sleep(4000);
            findDynamicElement(By.xpath(xpathTypeOfLostStolen), 120);
            FunctionLibrary.selectDropDownListByValue(xpathTypeOfLostStolen, "Stolen", Desc);
            findDynamicElement(By.xpath(xpathContinueBtn), 120);
            Desc = "click on Continue";
            FunctionLibrary.objDriver.findElement(By.xpath(xpathContinueBtn)).click();
            Desc = "click on Accept Conditions";
            // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathAgreeChk)));
            scrollToView(By.xpath(icheckboxMinimal));
            try {
                FunctionLibrary.objDriver.navigate().refresh();
                scrollToView(By.xpath(icheckboxMinimal));
                // WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(divIcheckboxMinimal));
                WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(xpathOfIcheckMail));

                System.out.println("click try in try block");
                element.click();
            } catch (Exception e) {
                System.out.println("click try in catch block");
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(divIcheckboxMinimal)));
                // FunctionLibrary.objDriver.findElement(By.xpath(xpathOfIcheckMail)).click();
            }
            System.out.println("clicked");
            Desc = "click on submit";
            FunctionLibrary.objDriver.findElement(By.xpath(xpathStolenLostBtn)).click();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Submitted the STOLEN transponder ", LogStatus.PASS, true);
            Desc = "Logout from App";
            CommonLibrary.logoutWebApp();
            String uname = dataObj.get("Suname");
            String pwd = dataObj.get("SPWD");
            CommonLibrary.loginSiebelAppWithWebAcc(siebelURL1, uname, pwd, dataObj.get("Browser Type"));
            //CommonLibrary.searchForAccount(AccNo);
            Desc = "Clicking Accounts tab";
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAccountsTab)).click();
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathAccountNumberTxtBox, xpathAccountsTab, 10, 60);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAccountNumberTxtBox)).sendKeys(AccNo);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Search for account number: " + AccNo, LogStatus.INFO, false);
            Desc = "Clicking go button";
            FunctionLibrary.objDriver.findElement(By.xpath(xpathGoBtn)).click();
            findDynamicElement(By.xpath(accountList), 120);
            Desc = "Clicking Devices tab";
            FunctionLibrary.objDriver.findElement(By.xpath(accountCSN)).click();
            // FunctionLibrary.clickObject("//a[contains(@name,'CSN')]", "", "Clicking on Account number link");
            FunctionLibrary.objDriver.findElement(By.xpath(deviceTab)).click();
            findDynamicElement(By.xpath(deviceHistory), 120);
            //FunctionLibrary.clickObject("//a[contains(text(),'Device History')]","","Click Device history");
            FunctionLibrary.objDriver.findElement(By.xpath(deviceHistory)).click();
            String xpathOfDeviceHistoryTable = deviceHistoryList;
            int rowNumberWithDeviceNumber = 1;
            String transponderData = FunctionLibrary.getWebTableCellData(xpathOfDeviceHistoryTable, 1, 7, "");
            if (transponderData.equalsIgnoreCase("LOST")) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "CRM Validation Siebel Verified Expected: Yes, Actual: Yes", LogStatus.PASS, false);
                FunctionLibrary.verifyWebTableCellData(xpathOfDeviceHistoryTable, rowNumberWithDeviceNumber, 7, "LOST", "Verifying status", true);
            } else if (transponderData.equalsIgnoreCase("STOLEN")) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "CRM Validation Siebel Verified Expected: Yes, Actual: Yes", LogStatus.PASS, false);
                FunctionLibrary.verifyWebTableCellData(xpathOfDeviceHistoryTable, rowNumberWithDeviceNumber, 7, "STOLEN", "Verifying status", true);
            } else {
                rowNumberWithDeviceNumber = FunctionLibrary.getRowNumberFromWebTable(xpathOfDeviceHistoryTable, TransponderId, "get the row number");
                System.out.println(rowNumberWithDeviceNumber);
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "CRM Validation Siebel Verified Expected: Yes, Actual: Yes", LogStatus.PASS, false);
                FunctionLibrary.verifyWebTableCellData(xpathOfDeviceHistoryTable, rowNumberWithDeviceNumber, 7, "LOST", "Verifying status", true);
            }
            CommonLibrary.logoutSiebelApplicaiton();
            FunctionLibrary.closeAllActiveBrowser();
        } catch (Exception e) {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" +e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }
    }


    public static void tcAccountMaintenanceWebACMW279() throws InterruptedException {
        try {
            CommonLibrary.isClosedAllBrowsers=true;
            String userName = dataObj.get("User Name");
            String password = dataObj.get("Password");
            String mailingAddress = dataObj.get("MailingAddress");
            mailingAddress = mailingAddress.replace("\n", "");
            String[] addressDetails = mailingAddress.split(":");
            String Address1 = addressDetails[0];
            String Address2 = addressDetails[1];
            String City = addressDetails[2];
            String State = addressDetails[3];
            String Country = addressDetails[4];
            String ZipCode = addressDetails[5];
            String Desc;
            Desc = "Login To App";
            CommonLibrary.loginWebApp(userName, password, Desc);
            // ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Logged in as " + userName , LogStatus.INFO, false);
            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();
            String AccNo = Desc.substring(9).trim();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAccountLink)).click();
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisibleWebapp(xpathAddress1, xpathAccountLink, 10, 60);
            scrollToView(By.xpath(xpathCountry));
            Desc = "Selecting Country";
            FunctionLibrary.selectDropDownListByValue(xpathCountry, Country, Desc);
            Desc = "Selecting State";
            FunctionLibrary.selectDropDownListByValue(xpathState, State, Desc);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAddress1)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAddress1)).sendKeys(Address1);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAddress2)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAddress2)).sendKeys(Address2);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathCity)).sendKeys(City);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathZipCode)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathZipCode)).sendKeys(ZipCode);
            scrollToView(By.xpath(xpathBtnUpdate));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathBtnUpdate)).click();
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisibleWebapp(xpathUpdatedMsg, xpathBtnUpdate, 10, 60);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Adding Standerd addrss Address1 " + Address1 + "Address2 " + Address2 + "City" + City + "and ZipCode " + ZipCode, LogStatus.INFO, false);
            Desc = FunctionLibrary.objDriver.findElement(By.xpath(xpathUpdatedMsg)).getText();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, Desc, LogStatus.INFO, true);

            String sql = "ELECT * FROM siebel.T_MAILHOUSE_INTERFACE WHERE ACCOUNTNUMBER='" + AccNo + "' ";
            System.out.println("Status " + sql);
            HashMap<String, String> inputAccountNumInfo = CommonLibrary.executeSqlAndGetOutputAsHashMap(sql);
            if (inputAccountNumInfo.size() > 0)
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "verification of T_WEB_SESSION is Success", LogStatus.PASS, false);
            else
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "verification of T_WEB_SESSION is failed", LogStatus.FAIL, false);
        } catch (Exception e) {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Ocuured " + e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }
    }

    public static void tcAccountMaintenanceWebACMW225() throws InterruptedException {
        try {
            String userName = dataObj.get("User Name");
            String password = dataObj.get("Password");
            CommonLibrary.loginWebApp(userName, password, "Trying to Login with valid username and password ");
            FunctionLibrary.clickObject(editProfileLink, "", "Click Edit Profile link");
            FunctionLibrary.clickObject(managePaymenttMethodsLink, "", "Click manage payment methods link");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(addCreditCardBankAccNumLink, managePaymenttMethodsLink, 10, 60);

            int noOfCardsAdded = 0;
            try {
                noOfCardsAdded = FunctionLibrary.getWebTableRowCount(FunctionLibrary.objDriver.findElement(By.xpath(creditcardsTbl)));
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "No of payment methods already added: " + (noOfCardsAdded - 1), LogStatus.INFO, true);

            if (noOfCardsAdded == 3) {
                String fname = dataObj.get("First Name");
                String mname = dataObj.get("Middle Name");
                String lname = dataObj.get("Last Name");
                String payType = dataObj.get("Card Type");
                String cardNum = dataObj.get("Card Number");
                String expMonth = dataObj.get("Exp Month");
                String expYear = dataObj.get("Exp Year");
                String cvvNum = dataObj.get("Ccv Number");
                String addressLine1 = dataObj.get("New Address Line1");
                String addressLine2 = dataObj.get("New Address Line2");
                String city = dataObj.get("New City");
                String state = dataObj.get("New State");
                String zipcode = dataObj.get("New Zip Code");
                String country = dataObj.get("New Country");
                FunctionLibrary.clickObject(addCreditCardBankAccNumLink, "", "Click add credit card link");
                FunctionLibrary.setText(xpathCardHolderFirstName, fname, "Enter first name");
                FunctionLibrary.setText(xpathCardHolderMiddleName, mname, "Enter middle name");
                FunctionLibrary.setText(xpathCardHolderLastName, lname, "Enter lastname name");

                FunctionLibrary.selectDropDownListByValue(xpathCardTypeDropdown, payType, "Select pay type");
                FunctionLibrary.setText(xpathCardNumberField, cardNum, "Enter first name");
                FunctionLibrary.selectDropDownListByValue(xpathMonthDropdown, expMonth, "Select month");
                FunctionLibrary.selectDropDownListByValue(xpathYearDropdown, expYear, "Select year");
                FunctionLibrary.setText(xpathCVV2Code, cvvNum, "Enter cvv2 code");
                FunctionLibrary.setText(xpathNewCardAddressLine1, addressLine1, "Enter address line1");
                FunctionLibrary.setText(xpathNewCardAddressLine2, addressLine2, "Enter address line2");
                FunctionLibrary.selectDropDownListByValue(xpathNewCardStateDropdown, state, "Select state");
                FunctionLibrary.selectDropDownListByValue(xpathCountry, country, "Select country");
                FunctionLibrary.setText(xpathNewCardCity, city, "Enter city");
                FunctionLibrary.setText(xpathNewCardZipcode, zipcode, "Enter zipcode");
                FunctionLibrary.clickObject(xpathUpdateVehicleBtn, "", "Click save button");
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Added a new paytype: " + payType, LogStatus.INFO, true);
            }

            try {
                noOfCardsAdded = FunctionLibrary.getWebTableRowCount(FunctionLibrary.objDriver.findElement(By.xpath(creditcardsTbl)));
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            if (noOfCardsAdded == 4) {
                Thread.sleep(6000);
                String msgMaxNoOfPaymentMethods = "//*[contains(text(),'You have reached the maximum number of payment methods')]";

                FunctionLibrary.clickObject(addCreditCardBankAccNumLink, "", "Click add credit card link");
                try {
                    FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.
                            xpath(msgMaxNoOfPaymentMethods)));

                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                            "Expected: Message should be displayed - You have reached the maximum number of payment methods. <br> Actual: Message is dispalyed",
                            LogStatus.PASS, true);

                } catch (TimeoutException e) {
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                            "Expected: Message should be displayed - You have reached the maximum number of payment methods. <br> Actual: Message is not displayed",
                            LogStatus.FAIL, true);
                }

            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number,
                        "Pamyent methods are not 3. Please make sure payment methods are 3 before to execute this test case",
                        LogStatus.FAIL, true);
            }
            CommonLibrary.logoutWebApp();
        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" +e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }
    }

    public static void tcAccountMaintenanceWebACMW188() throws InterruptedException {

        String userName = dataObj.get("User Name");
        String password = dataObj.get("Password");
        String phone = dataObj.get("Phoneno");
        try {
            System.out.println("********************************* one");
            CommonLibrary.loginWebApp(userName, password, "Login with provided credentials");
            WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 50);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAccountProfile)).click();
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathProfile)));
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
            System.out.println("********************************* two");
            String selectedPhone = new Select(FunctionLibrary.objDriver.findElement(By.xpath("//div[@id='phoneRow_1']//select"))).getFirstSelectedOption().getText();

            System.out.println("*********************" + selectedPhone);
           /* String selectedPhone = FunctionLibrary.dropDownListSelectedOption(xpathAlternatePhone,"Selected value");
            System.out.println("*********************"+ selectedPhone);*/
            String alternatePhone = FunctionLibrary.objDriver.findElement(By.xpath(xpathAlternatePhoneInput)).getAttribute("value");

            System.out.println("********************************* three");
            if (selectedPhone.equalsIgnoreCase("Alternate Phone")) {
                System.out.println(alternatePhone);
                FunctionLibrary.objDriver.findElement(By.xpath(xpathAlternatePhoneInput)).clear();
                FunctionLibrary.objDriver.findElement(By.xpath(xpathAlternatePhoneInput)).sendKeys(phone);
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Updating tha alternate phone number from: " + alternatePhone
                        + " to: " + phone, LogStatus.INFO, true);
            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Select an account with", LogStatus.INFO, true);
            }
            FunctionLibrary.objDriver.findElement(By.xpath(xpathUpdateButton2)).click();

            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathSuccess)));

            String alertMessage = FunctionLibrary.objDriver.findElement(By.xpath(xpathSuccess)).getText();
            System.out.println(alertMessage);

            if (alertMessage.contains("Account Details Updated Successfully")) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Changed the alternate phone number from: " + alternatePhone + " to: " + phone + " successfully", LogStatus.PASS, true);
            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Failed to change the alternate phone number from: " + alternatePhone + " to: " + phone, LogStatus.FAIL, true);
            }
            CommonLibrary.logoutWebApp();
        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Couldn't change the alternate phone number due to: " +
                    e.getMessage(), LogStatus.FAIL, true);
            CommonLibrary.logoutWebApp();
        }
    }

//***********************************************************************

    public static void tcAccountMaintenanceWebACMW189() throws Exception {
        try {
            String userName = dataObj.get("User Name");
            String password = dataObj.get("Password");
            String Desc;
            Desc = "Login To App";
            CommonLibrary.loginWebApp(userName, password, Desc);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Logged in as " + userName, LogStatus.INFO, false);

            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();
            String AccNo = Desc.substring(9).trim();
            System.out.println(AccNo);
            findDynamicElement(By.xpath(xpathAccountProfile), 120);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathAccountProfile)).click();
            findDynamicElement(By.xpath(updateAccountProfileBtn), 120);
            try {
//FunctionLibrary.objDriver.navigate().refresh();
                scrollToView(By.xpath(addAnotherPhoneNbrLink));
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Phone number details before adding a new number" + userName, LogStatus.INFO, true);
                WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(addAnotherPhoneNbrLink));
                System.out.println("click try in try block");
                element.click();
            } catch (Exception e) {
                System.out.println("click try in catch block");
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(addAnotherPhoneNbrLink)));
                FunctionLibrary.objDriver.findElement(By.xpath(addAnotherPhoneNbrLink)).click();
            }

            try {
                findDynamicElement(By.xpath(selectPhoneTypeDropDown2), 120);
                FunctionLibrary.selectDropDownListByValue(selectPhoneTypeDropDown2, dataObj.get("PhoneType"), "Entering expiry year");
                FunctionLibrary.objDriver.findElement(By.xpath(phoneDetailsTxtBox2)).sendKeys(dataObj.get("Phoneno"));
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Phone number details after adding a new number", LogStatus.INFO, true);
            } catch (Exception e) {
                findDynamicElement(By.xpath(selectPhoneTypeDropDown3), 120);
                FunctionLibrary.selectDropDownListByValue(selectPhoneTypeDropDown3, dataObj.get("PhoneType"), "Entering expiry year");
                FunctionLibrary.objDriver.findElement(By.xpath(phoneDetailsTxtBox3)).sendKeys(dataObj.get("Phoneno"));
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Phone number details after adding a new number", LogStatus.INFO, true);
            }

            try {
                scrollToView(By.xpath(xpathBtnUpdate));
                WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(xpathBtnUpdate));
                System.out.println("click try in try block");
                element.click();
            } catch (Exception e) {
                System.out.println("click try in catch block");
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathBtnUpdate)));
                FunctionLibrary.objDriver.findElement(By.xpath(xpathBtnUpdate)).click();
                Thread.sleep(15000);
            }
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Account details should be updated" + "<br>"
                    + "Actual: Account details is updated successfully", LogStatus.PASS, true);

            CommonLibrary.logoutWebApp();
            CommonLibrary.loginSiebelApp("", "", dataObj.get("Suname"), dataObj.get("SPWD"));
            CommonLibrary.searchForAccount(AccNo);

//Checking the status under Non-Financial History tab

            FunctionLibrary.clickObject(historyTab, "", "Click history link");
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(nonFinancialsHistoryTab)));
            FunctionLibrary.clickObject(nonFinancialsHistoryTab, "", "Clicking Non-Financial History Tab");
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(activitiesTable)));
            int rowNum = FunctionLibrary.getRowNumberFromWebTable(activitiesTable, "AUDITACCNT", "Get row number");
            FunctionLibrary.verifyWebTableCellData(activitiesTable, rowNum, 3, "AUDITACCNT", "Verify category", true);
            FunctionLibrary.verifyWebTableCellData(activitiesTable, rowNum, 4, "VIEW", "Verify sub-category", false);
            int rowNum1 = FunctionLibrary.getRowNumberFromWebTable(activitiesTable, "CONTACT", "Get row number");
            FunctionLibrary.verifyWebTableCellData(activitiesTable, rowNum1, 4, "CONTACT", "Verify sub-category", false);
            FunctionLibrary.verifyWebTableCellData(activitiesTable, rowNum1, 3, "CHANGE", "Verify Category", false);

            CommonLibrary.logoutSiebelApplicaiton();

        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" + e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }

    }

    public static void tcAccountMaintenanceWebACMW214() throws Exception {
        try {
            String userName = dataObj.get("User Name");
            String password = dataObj.get("Password");
            String Desc;
            Desc = "Login To App";
            CommonLibrary.loginWebApp(userName, password, Desc);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Logged in as " + userName, LogStatus.INFO, false);

            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();
            String AccNo = Desc.substring(9).trim();
            System.out.println(AccNo);
            findDynamicElement(By.xpath(xpathAccountProfile), 120);
            FunctionLibrary.objDriver.findElement(By.xpath(managePayementMethod)).click();
           /* findDynamicElement(By.xpath(updateAccountProfileBtn), 120);
            scrollToView(By.xpath(managePaymentMethodLink));
            try {
                scrollToView(By.xpath(managePaymentMethodLink));
                WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(managePaymentMethodLink));
                System.out.println("click try in try block");
                element.click();
            } catch (Exception e) {
                System.out.println("click try in catch block");
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(managePaymentMethodLink)));
                FunctionLibrary.objDriver.findElement(By.xpath(managePaymentMethodLink)).click();
            }*/
            findDynamicElement(By.xpath(addCreditCardOrBankAccountBtn), 120);
            scrollToView(By.xpath(addCreditCardOrBankAccountBtn));
            try {
                scrollToView(By.xpath(addCreditCardOrBankAccountBtn));
                WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(addCreditCardOrBankAccountBtn));
                System.out.println("click try in try block");
                element.click();
            } catch (Exception e) {
                System.out.println("click try in catch block");
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(addCreditCardOrBankAccountBtn)));
                FunctionLibrary.objDriver.findElement(By.xpath(addCreditCardOrBankAccountBtn)).click();
            }
            findDynamicElement(By.xpath(addPaymentMethodTxt), 120);

            FunctionLibrary.selectDropDownListByValue(cardTypeDropDown, dataObj.get("Card Type"), "Entering Card type");
            FunctionLibrary.objDriver.findElement(By.xpath(accountNumberTxtBox)).sendKeys(dataObj.get("Card Number"));
            FunctionLibrary.selectDropDownListByValue(expiryMonthDropDown, dataObj.get("Exp Month"), "Entering expiry month");
            FunctionLibrary.selectDropDownListByValue(expiryYearDropDown, dataObj.get("Exp Year"), "Entering expiry year");
            FunctionLibrary.objDriver.findElement(By.xpath(cardCVVNbrTxtBox)).sendKeys(dataObj.get("Ccv Number"));
            String mailingAddress = dataObj.get("MailingAddress");
            mailingAddress = mailingAddress.replace("\n", "");

            String[] addressDetails = mailingAddress.split(":");
            String Address1 = addressDetails[0];
//String Address2="";
            String City = addressDetails[1];
            String State = addressDetails[2];
            String Country = addressDetails[3];
            String ZipCode = addressDetails[4];

            FunctionLibrary.objDriver.findElement(By.xpath(addressLine1TxtBox)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(addressLine1TxtBox)).sendKeys(Address1);
//FunctionLibrary.objDriver.findElement(By.xpath(addressLine2TxtBox)).sendKeys(Address2);
            FunctionLibrary.objDriver.findElement(By.xpath(cityTxtBox)).sendKeys(City);
            FunctionLibrary.objDriver.findElement(By.xpath(zipCode1TxtBox)).sendKeys(ZipCode);
            FunctionLibrary.selectDropDownListByValue(stateDropDown, State, "Selecting state");
            FunctionLibrary.selectDropDownListByValue(countryDropDown, Country, "Selecting country");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Entering AMEX card details", LogStatus.INFO, true);

            try {
                scrollToView(By.xpath(submitPaymentBtn));
                WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(submitPaymentBtn));
                System.out.println("click try in try block");
                element.click();
            } catch (Exception e) {
                System.out.println("click try in catch block");
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(submitPaymentBtn)));
                FunctionLibrary.objDriver.findElement(By.xpath(submitPaymentBtn)).click();
            }
            findDynamicElement(By.xpath(creditCardAddedSuccessMsg), 120);
            String successMsg = FunctionLibrary.objDriver.findElement(By.xpath(creditCardAddedSuccessMsg)).getText();
            System.out.println(successMsg);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Confirmation message should be displayed" + "br"
                    + "Actual: Confirmation message is diaplyed with content----->" + successMsg, LogStatus.PASS, true);

            CommonLibrary.logoutWebApp();
//login to siebel for verification

            CommonLibrary.loginSiebelApp("", "", dataObj.get("Suname"), dataObj.get("SPWD"));
            CommonLibrary.searchForAccount(AccNo);

//Checking the status under Non-Financial History tab

            FunctionLibrary.clickObject(historyTab, "", "Click history link");
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(nonFinancialsHistoryTab)));
            FunctionLibrary.clickObject(nonFinancialsHistoryTab, "", "Clicking Non-Financial History Tab");
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(activitiesTable)));
            int rowNum = FunctionLibrary.getRowNumberFromWebTable(activitiesTable, "AUDITACCNT", "Get row number");
            FunctionLibrary.verifyWebTableCellData(activitiesTable, rowNum, 4, "VIEW", "Verify category", true);
            FunctionLibrary.verifyWebTableCellData(activitiesTable, rowNum, 3, "AUDITACCNT", "Verify sub-category", false);

            CommonLibrary.logoutSiebelApplicaiton();
        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" + e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }

    }

    public static void tcAccountMaintenanceWebACMW216() throws Exception {
        try {
            CommonLibrary.loginSblWithGenericUser(dataObj);
            CommonLibrary.searchForAccount(dataObj.get("AccountNumber"));
            FunctionLibrary.clickObject(siebelAcountInfoTab, "", "CLicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
                    (siebelAddressesTab, siebelAcountInfoTab, 8, 40);
            FunctionLibrary.clickObject(siebelAddressesTab, "", "CLicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
                    (siebelAddressesTable, siebelAddressesTab, 8, 40);
            FunctionLibrary.clickObject(addressesQueryBtn, "", "Clicking Query button");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
                    (addressesGoBtn, addressesQueryBtn, 8, 40);
            FunctionLibrary.setText(addressTypeTxtBox, "BILLING", "Entering Adress type");
            FunctionLibrary.clickObject(addressesGoBtn, "", "Clicking Query button");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
                    (addressesQueryBtn, addressesGoBtn, 8, 40);
            String addressLine1 = FunctionLibrary.objDriver.findElement(By.xpath(addressLine1Txt)).getAttribute("title");
            String zipCode = FunctionLibrary.objDriver.findElement(By.xpath(zipCodeTxt)).getAttribute("title");
            String city = FunctionLibrary.objDriver.findElement(By.xpath(cityTxt)).getAttribute("title");
            String state = FunctionLibrary.objDriver.findElement(By.xpath(stateTxt)).getAttribute("title");
            CommonLibrary.logoutSiebelApplicaiton();
            String userName = dataObj.get("User Name");
            String password = dataObj.get("Password");
            String Desc;
            //WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 120);
            Desc = "Login To App";
            CommonLibrary.loginWebApp(userName, password, Desc);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Logged in as " + userName, LogStatus.INFO, false);

            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();
            String AccNo = Desc.substring(9).trim();
            System.out.println(AccNo);
            findDynamicElement(By.xpath(managePayementMethod), 120);
            try {
                WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(managePayementMethod));
                System.out.println("click try in try block");
                element.click();
            } catch (Exception e) {
                System.out.println("click try in catch block");
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(managePaymentMethodLink)));
                FunctionLibrary.objDriver.findElement(By.xpath(managePaymentMethodLink)).click();
            }

// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(managePaymentMethods)));
            findDynamicElement(By.xpath(addCreditCardOrBankAccountBtn), 120);
            scrollToView(By.xpath(addCreditCardOrBankAccountBtn));
            try {
                scrollToView(By.xpath(addCreditCardOrBankAccountBtn));
                WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(addCreditCardOrBankAccountBtn));
                System.out.println("click try in try block");
                element.click();
            } catch (Exception e) {
                System.out.println("click try in catch block");
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(addCreditCardOrBankAccountBtn)));
                FunctionLibrary.objDriver.findElement(By.xpath(addCreditCardOrBankAccountBtn)).click();
            }
            findDynamicElement(By.xpath(addPaymentMethodTxt), 120);
            FunctionLibrary.selectDropDownListByValue(cardTypeDropDown, dataObj.get("Card Type"), "Entering Card type");
            FunctionLibrary.objDriver.findElement(By.xpath(accountNumberTxtBox)).sendKeys(dataObj.get("Card Number"));
            FunctionLibrary.selectDropDownListByValue(expiryMonthDropDown, dataObj.get("Exp Month"), "Entering expiry month");
            FunctionLibrary.selectDropDownListByValue(expiryYearDropDown, dataObj.get("Exp Year"), "Entering expiry year");
            FunctionLibrary.objDriver.findElement(By.xpath(cardCVVNbrTxtBox)).sendKeys(dataObj.get("Ccv Number"));
            FunctionLibrary.objDriver.findElement(By.xpath(billingAddressTxtBox)).sendKeys(addressLine1);
            FunctionLibrary.objDriver.findElement(By.xpath(cityNameTxtBox)).sendKeys(city);
            FunctionLibrary.selectDropDownListByValue(stateNameDropDown, state, "Entering expiry month");
            FunctionLibrary.objDriver.findElement(By.xpath(zipCodeTxtBox)).sendKeys(zipCode);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Entering AMEX card details", LogStatus.INFO, true);

            try {
                scrollToView(By.xpath(submitPaymentBtn));
                WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(submitPaymentBtn));
                System.out.println("click try in try block");
                element.click();
            } catch (Exception e) {
                System.out.println("click try in catch block");
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(submitPaymentBtn)));
                FunctionLibrary.objDriver.findElement(By.xpath(submitPaymentBtn)).click();
            }
            findDynamicElement(By.xpath(creditCardAddedSuccessMsg), 120);
            String successMsg = FunctionLibrary.objDriver.findElement(By.xpath(creditCardAddedSuccessMsg)).getText();
            System.out.println(successMsg);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Confirmation message should be displayed" + "br"
                    + "Actual: Confirmation message is diaplyed with content----->" + successMsg, LogStatus.PASS, true);

            CommonLibrary.logoutWebApp();
            CommonLibrary.loginSiebelApp("", "", dataObj.get("Suname"), dataObj.get("SPWD"));
            CommonLibrary.searchForAccount(AccNo);

//Checking the status under Non-Financial History tab

            FunctionLibrary.clickObject(historyTab, "", "Click history link");
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(nonFinancialsHistoryTab)));
            FunctionLibrary.clickObject(nonFinancialsHistoryTab, "", "Clicking Non-Financial History Tab");
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(activitiesTable)));
            int rowNum = FunctionLibrary.getRowNumberFromWebTable(activitiesTable, "AUDITACCNT", "Get row number");
            FunctionLibrary.verifyWebTableCellData(activitiesTable, rowNum, 4, "VIEW", "Verify category", true);
            FunctionLibrary.verifyWebTableCellData(activitiesTable, rowNum, 3, "AUDITACCNT", "Verify sub-category", false);

            CommonLibrary.logoutSiebelApplicaiton();
        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" + e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }

    }

    public static void tcAccountMaintenanceWebACMW054() throws Exception {
        try {
            String userName = dataObj.get("User Name");
            String password = dataObj.get("Password");
            String Desc;
            Desc = "Login To App";
            CommonLibrary.loginWebApp(userName, password, Desc);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Logged in as " + userName, LogStatus.INFO, false);
            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();
            String AccNo = Desc.substring(9).trim();
            System.out.println(AccNo);
            findDynamicElement(By.xpath(addFundsLink), 120);
            FunctionLibrary.objDriver.findElement(By.xpath(addFundsLink)).click();
            findDynamicElement(By.xpath(paymentAmountTxtBox), 120);

            String paymentType = FunctionLibrary.objDriver.findElement(By.xpath("//select[@name='selectedCardRowId']")).getText();
            paymentType = paymentType.substring(0, 4);
            try {
                FunctionLibrary.scrollDown(continueForPaymentBtn, "scroll down");
                WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(continueForPaymentBtn));
                System.out.println("click try in try block");
                element.click();
            } catch (Exception e) {
                System.out.println("click try in catch block");
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(continueForPaymentBtn)));
                FunctionLibrary.objDriver.findElement(By.xpath(continueForPaymentBtn)).click();
            }
            findDynamicElement(By.xpath(cardCVVNbrTxtBox), 120);
            FunctionLibrary.objDriver.findElement(By.xpath(cardCVVNbrTxtBox)).sendKeys("112");
            FunctionLibrary.objDriver.findElement(By.xpath(useAccountAddressCheckBox)).click();
            findDynamicElement(By.xpath(addressLine1TxtBoxNew), 120);
            //Thread.sleep(5000);

            String mailingAddress = dataObj.get("MailingAddress");
            mailingAddress = mailingAddress.replace("\n", "");

            String[] addressDetails = mailingAddress.split(":");
            String Address1 = addressDetails[0];
            String Address2 = "";
            String City = addressDetails[1];
            String State = addressDetails[2];
            String Country = addressDetails[3];
            String ZipCode = addressDetails[4];

            FunctionLibrary.objDriver.findElement(By.xpath(addressLine1TxtBoxNew)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(addressLine1TxtBoxNew)).sendKeys(Address1);
            FunctionLibrary.objDriver.findElement(By.xpath(addressLine2TxtBoxNew)).sendKeys(Address2);
            FunctionLibrary.objDriver.findElement(By.xpath(cityTxtBoxNew)).sendKeys(City);
            FunctionLibrary.objDriver.findElement(By.xpath(zipCode1TxtBoxNew)).sendKeys(ZipCode);
            FunctionLibrary.selectDropDownListByValue(stateDropDownNew, State, "Selecting state");
            FunctionLibrary.selectDropDownListByValue(countryDropDownNew, Country, "Selecting country");

            FunctionLibrary.scrollDown(continueForPaymentBtn, "Scroll down");
            FunctionLibrary.clickObject(continueForPaymentBtn, "", "Click continue button");
            findDynamicElement(By.xpath(returnToMyAccountBtn), 120);
            String confirmationMsg = FunctionLibrary.objDriver.findElement(By.xpath(replenishmentAddConfirmationMsg)).getText();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected Result : Confirmation message should be displayed" + "</br>"
                    + "Actual Message : Confirmation Message is displayed with below content----->" + "</br>" + confirmationMsg, LogStatus.PASS, true);
            FunctionLibrary.objDriver.findElement(By.xpath(returnToMyAccountBtn)).click();
            findDynamicElement(By.xpath(addFundsLink), 120);
            String accountBalanceAfterAddingAmount = FunctionLibrary.objDriver.findElement(By.xpath(accountBalanceTxt)).getText();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Accoount Balance after adding replenishment is---->" + "</br>" +
                    accountBalanceAfterAddingAmount, LogStatus.INFO, true);


            CommonLibrary.logoutWebApp();
            CommonLibrary.loginSiebelApp("", "", dataObj.get("Suname"), dataObj.get("SPWD"));
            CommonLibrary.searchForAccount(AccNo);

            //DimsManualTagAssignment.fieldVerificationInDataBase("siebel.s_src_payment","x_etc_accnt_id", "", AccNo);

            FunctionLibrary.clickObject(financialsTab, "", "Clicking Financials Tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(reversalsTab, financialsTab, 10, 40);
            FunctionLibrary.clickObject(reversalsTab, "", "Clicking Reversals tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
                    (financialTransactionListTable, reversalsTab, 10, 40);
            int rowNum2 = FunctionLibrary.getRowNumberFromWebTable(financialTransactionListTable, paymentType, "Get row number");
            FunctionLibrary.verifyWebTableCellData(financialTransactionListTable, rowNum2, 2, paymentType, "Verify RE-BILL type", true);
            //FunctionLibrary.verifyWebTableCellData(financialTransactionListTable, rowNum2,4, "REBILL", "Verify sub-category", false);

            CommonLibrary.logoutSiebelApplicaiton();

        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" + e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }

    }

    public static void tcAccountMaintenanceWebACMW057() throws Exception {
        try {
            String userName = dataObj.get("User Name");
            String password = dataObj.get("Password");
            String Desc;
            Desc = "Login To App";
            CommonLibrary.loginWebApp(userName, password, Desc);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Logged in as " + userName, LogStatus.INFO, false);
            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();
            String AccNo = Desc.substring(9).trim();
            System.out.println(AccNo);
            findDynamicElement(By.xpath(transpondersAndVehiclesTab), 120);
            try {
                WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(transpondersAndVehiclesTab));
                System.out.println("click try in try block");
                element.click();
            } catch (Exception e) {
                System.out.println("click try in catch block");
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(transpondersAndVehiclesTab)));
                FunctionLibrary.objDriver.findElement(By.xpath(transpondersAndVehiclesTab)).click();
            }
            findDynamicElement(By.xpath(transpondersTxt), 120);
            findDynamicElement(By.xpath(purchaseAdditionalTranspondersBtn), 120);
            try {
                WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(purchaseAdditionalTranspondersBtn));
                System.out.println("click try in try block");
                element.click();
            } catch (Exception e) {
                System.out.println("click try in catch block");
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(purchaseAdditionalTranspondersBtn)));
                FunctionLibrary.objDriver.findElement(By.xpath(purchaseAdditionalTranspondersBtn)).click();
            }
            findDynamicElement(By.xpath(portableDeviceCountTxtBox), 120);
            FunctionLibrary.objDriver.findElement(By.xpath(portableDeviceCountTxtBox)).sendKeys("2");
            try {
                FunctionLibrary.scrollDown(continueMailBtn, "scroll down");
                WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(continueMailBtn));
                System.out.println("click try in try block");
                element.click();
            } catch (Exception e) {
                System.out.println("click try in catch block");
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(continueMailBtn)));
                FunctionLibrary.objDriver.findElement(By.xpath(continueMailBtn)).click();
            }
            findDynamicElement(By.xpath(orderTransponderTxt), 120);
            try {
                FunctionLibrary.scrollDown(continueMailBtn, "scroll down");
                WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(continueMailBtn));
                System.out.println("click try in try block");
                element.click();
            } catch (Exception e) {
                System.out.println("click try in catch block");
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(continueMailBtn)));
                FunctionLibrary.objDriver.findElement(By.xpath(continueMailBtn)).click();
            }
            findDynamicElement(By.xpath(addAndPayWithNewCardBankAccountLink), 120);
            FunctionLibrary.objDriver.findElement(By.xpath(addAndPayWithNewCardBankAccountLink)).click();
            findDynamicElement(By.xpath(addPaymentInformationTxt), 120);
            FunctionLibrary.objDriver.findElement(By.xpath(bankAccountIcon1)).click();
            findDynamicElement(By.xpath(bankAccountTypeDropDown), 120);
// FunctionLibrary.selectDropDownListByValue(bankAccountTypeDropDown,"SAVING","Entering expiry year");
            FunctionLibrary.objDriver.findElement(By.xpath(accountNbrTxtBox)).sendKeys(dataObj.get("BankAccountNumber"));
            FunctionLibrary.objDriver.findElement(By.xpath(confirmAccountNbrTxtBoxNew)).sendKeys(dataObj.get("BankAccountNumber"));
            FunctionLibrary.objDriver.findElement(By.xpath(bankRoutingNbrTxtBox)).sendKeys(dataObj.get("BankRoutingNumber"));

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Adding Bank account details", LogStatus.INFO, true);
            scrollToView(By.xpath(xpathContinueBtn));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathContinueBtn)).click();
            findDynamicElement(By.xpath(bankDetailsAddedSuccessMsg), 120);
            String messageContent = FunctionLibrary.objDriver.findElement(By.xpath(bankDetailsAddedSuccessMsg)).getText();
            if (!messageContent.equals(null)) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Account details added success message should be displayed" + "<br>"
                        + "Actual: Success Message shouild be displayed with content-----> " + messageContent, LogStatus.PASS, true);
            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Account details added success message should be displayed" + "<br>"
                        + "Actual: Success Message is not displayed ", LogStatus.FAIL, true);
            }
            CommonLibrary.logoutWebApp();
            CommonLibrary.loginSiebelApp("", "", dataObj.get("Suname"), dataObj.get("SPWD"));
            CommonLibrary.searchForAccount(AccNo);

//Verification under devices tab
            FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(deviceTab, accountInfoTab, 10, 40);
            FunctionLibrary.clickObject(deviceTab, "", "Clicking devices tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
                    (xpathDeviceList, deviceTab, 10, 40);
            FunctionLibrary.clickObject(deviceRequestTab, "", "Clicking Device history tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
                    (deviceRequestListTable, deviceRequestTab, 10, 40);
            int rowNum1 = FunctionLibrary.getRowNumberFromWebTable(deviceRequestListTable, "WEB", "Get row number");
            FunctionLibrary.verifyWebTableCellData(deviceRequestListTable, rowNum1, 14, "WEB", "Verify Source", true);
            FunctionLibrary.verifyWebTableCellData(deviceRequestListTable, rowNum1, 4, "PAID", "Verify status", false);

//Verification under Financials tab
            FunctionLibrary.clickObject(financialsTab, "", "Clicking Financials Tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(reversalsTab, financialsTab, 10, 40);
            FunctionLibrary.clickObject(reversalsTab, "", "Clicking Reversals tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
                    (financialTransactionListTable, reversalsTab, 10, 40);
            int rowNum2 = FunctionLibrary.getRowNumberFromWebTable(financialTransactionListTable, "SAVINGS", "Get row number");
            FunctionLibrary.verifyWebTableCellData(financialTransactionListTable, rowNum2, 2, "SAVINGS", "Verify RE-BILL type", true);
//FunctionLibrary.verifyWebTableCellData(financialTransactionListTable, rowNum2,4, "REBILL", "Verify sub-category", false);
            CommonLibrary.logoutSiebelApplicaiton();
        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" + e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }

    }


    public static void tcAccountMaintenanceWebACMW096() throws Exception {
        try {
            String userName = dataObj.get("User Name");
            String password = dataObj.get("Password");
            String transponderValue = null;
            String Desc;
            Desc = "Login To App";
            CommonLibrary.loginWebApp(userName, password, Desc);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Logged in as " + userName, LogStatus.INFO, false);

            WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 120);
            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();
            String AccNo = Desc.substring(9).trim();
            System.out.println(AccNo);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(transpondersAndVehiclesTab)));

            try {
                scrollToView(By.xpath(plansTab));
                WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(plansTab));
                System.out.println("click try in try block");
                element.click();
            } catch (Exception e) {
                System.out.println("click try in catch block");
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(plansTab)));
                FunctionLibrary.objDriver.findElement(By.xpath(plansTab)).click();
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(plansTxt)));
            try {
                FunctionLibrary.scrollDowntoElement(planNameDropDown, "Scroll down");
                scrollToView(By.xpath(transponderNbrTxtBox));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(planNameDropDown)));
                FunctionLibrary.selectDropDownListByValue(planNameDropDown, "PBC", "Entering Card type");
            } catch (Exception e) {
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(planNameDropDown)));
                FunctionLibrary.selectDropDownListByValue(planNameDropDown, "PBC", "Entering Card type");
            }

            try {
                scrollToView(By.xpath(transponderTypeDropDown));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(transponderTypeDropDown)));
                FunctionLibrary.selectDropDownListByValue(transponderTypeDropDown, "INTERIOR-BEIGE", "Entering Card type");
            } catch (Exception e) {
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(transponderTypeDropDown)));
                FunctionLibrary.selectDropDownListByValue(transponderTypeDropDown, "INTERIOR-BEIGE", "Entering Card type");
                FunctionLibrary.scrollUptoElement(transponderTypeDropDown, "Scroll up");
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Adding transponder specific normal plan", LogStatus.INFO, true);
            }
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(addPlanTransponderBtn)));
                FunctionLibrary.objDriver.findElement(By.xpath(addPlanTransponderBtn)).click();
            } catch (Exception e) {
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(addPlanTransponderBtn)));
                FunctionLibrary.objDriver.findElement(By.xpath(addPlanTransponderBtn)).click();
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(planSummaryTxt)));
            try {
                scrollToView(By.xpath(continueToPaymentBtn));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(continueToPaymentBtn)));
                FunctionLibrary.objDriver.findElement(By.xpath(continueToPaymentBtn)).click();
            } catch (Exception e) {
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(continueToPaymentBtn)));
                FunctionLibrary.objDriver.findElement(By.xpath(continueToPaymentBtn)).click();
            }

            try {

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(addAndPayWithNewCardBankAccountLink)));
                FunctionLibrary.objDriver.findElement(By.xpath(addAndPayWithNewCardBankAccountLink)).click();
            } catch (Exception e) {
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(addAndPayWithNewCardBankAccountLink)));
                FunctionLibrary.objDriver.findElement(By.xpath(addAndPayWithNewCardBankAccountLink)).click();
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(cardTypeDropDown)));
            FunctionLibrary.selectDropDownListByValue(cardTypeDropDown, dataObj.get("Card Type"), "Entering Card type");
            FunctionLibrary.objDriver.findElement(By.xpath(cardNumberEnterTxtBox)).sendKeys(dataObj.get("Card Number"));
            FunctionLibrary.selectDropDownListByValue(expiryMonthDropDown, dataObj.get("Exp Month"), "Entering expiry month");
            FunctionLibrary.selectDropDownListByValue(expiryYearDropDown, dataObj.get("Exp Year"), "Entering expiry year");
            FunctionLibrary.objDriver.findElement(By.xpath(cardCVVNbrTxtBox)).sendKeys(dataObj.get("Ccv Number"));
// FunctionLibrary.scrollDowntoElement(continuePaymentBtn, "Scroll down");
/*FunctionLibrary.objDriver.findElement(By.xpath(preSetAmountTxtBox)).clear();
FunctionLibrary.objDriver.findElement(By.xpath(preSetAmountTxtBox)).sendKeys("30");*/
// FunctionLibrary.objDriver.findElement(By.xpath(easyPayOptionCheckBox)).click();
            try {
                scrollToView(By.xpath(continuePaymentBtn));
                WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(continuePaymentBtn));
                System.out.println("click try in try block");
                element.click();
            } catch (Exception e) {
                System.out.println("click try in catch block");
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(continuePaymentBtn)));
                FunctionLibrary.objDriver.findElement(By.xpath(continuePaymentBtn)).click();
            }

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(creditCardAddedSuccessMsg)));
            String successMsg = FunctionLibrary.objDriver.findElement(By.xpath(creditCardAddedSuccessMsg)).getText();
            System.out.println(successMsg);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Confirmation message should be displayed" + "br"
                    + "Actual: Confirmation message is diaplyed with content----->" + successMsg, LogStatus.PASS, true);

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='account_nav_panel']/a")));
            FunctionLibrary.objDriver.findElement(By.xpath("//*[@id='account_nav_panel']/a")).click();
            Thread.sleep(3000);

            CommonLibrary.loginSiebelApp("", "", dataObj.get("Suname"), dataObj.get("SPWD"));
            CommonLibrary.searchForAccount(AccNo);

//Checking the status under Non-Financial History tab

            FunctionLibrary.clickObject(historyTab, "", "Click history link");
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(nonFinancialsHistoryTab)));
            FunctionLibrary.clickObject(nonFinancialsHistoryTab, "", "Clicking Non-Financial History Tab");
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(activitiesTable)));
            int rowNum = FunctionLibrary.getRowNumberFromWebTable(activitiesTable, "REBILL", "Get row number");
            FunctionLibrary.verifyWebTableCellData(activitiesTable, rowNum, 3, "ADD", "Verify category", true);
            FunctionLibrary.verifyWebTableCellData(activitiesTable, rowNum, 4, "REBILL", "Verify sub-category", false);


//Checking under Re-bill Info section

            FunctionLibrary.objDriver.findElement(By.xpath(accountInfoBtn)).click();
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
                    (accountRebillInfoBtn, accountInfoBtn, 10, 40);
            FunctionLibrary.clickObject(accountRebillInfoBtn, "", "Clicking re-bill info section");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
                    (replenishmentAppletTable, accountRebillInfoBtn, 10, 40);
            int rowNum1 = FunctionLibrary.getRowNumberFromWebTable(replenishmentAppletTable, dataObj.get("Card Type"), "Get row number");
            FunctionLibrary.verifyWebTableCellData(replenishmentAppletTable, rowNum1, 2, dataObj.get("Card Type"), "Verify RE-BILL type", true);
// FunctionLibrary.verifyWebTableCellData(replenishmentAppletTable, rowNum1,4, "REBILL", "Verify sub-category", false);

//Checking re-bill info history section

            FunctionLibrary.clickObject(replenishmentHistoryTab, "", "Clicking re-bill history info section");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
                    (replenishmentHistoryAppletTable, accountRebillInfoBtn, 10, 40);
            int rowNum2 = FunctionLibrary.getRowNumberFromWebTable(replenishmentHistoryAppletTable, dataObj.get("Card Type"), "Get row number");
            FunctionLibrary.verifyWebTableCellData(replenishmentHistoryAppletTable, rowNum2, 4, dataObj.get("Card Type"), "Verify RE-BILL type", true);
            FunctionLibrary.verifyWebTableCellData(replenishmentHistoryAppletTable, rowNum2, 1, "ADD", "Verify sub-category", false);

            CommonLibrary.logoutSiebelApplicaiton();

        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" + e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }

    }

    public static void tcAccountMaintenanceWebACMW101() throws Exception {
        try {
            CommonLibrary.loginSblWithGenericUser(dataObj);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verifying the changes in Siebel Application", LogStatus.INFO, false);
            CommonLibrary.searchForAccount(dataObj.get("AccountNumber"));

            FunctionLibrary.clickObject(xpathAccountInfoTab, "", "Clicking Account Info tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    accountThresholdAmountTxt, xpathAccountInfoTab, 5, 20);
            String thresholdAmount = FunctionLibrary.objDriver.findElement(By.xpath(accountThresholdAmountTxt)).getAttribute("value");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Rebill Threshold amount for the account is ---->" +
                    thresholdAmount, LogStatus.INFO, true);
            thresholdAmount = thresholdAmount.substring(0, thresholdAmount.length() - 3);
            thresholdAmount = thresholdAmount.replace("$", "");
            thresholdAmount = thresholdAmount.replaceAll(",", "");

            CommonLibrary.logoutSiebelApplicaiton();

            FunctionLibrary.objDriver.get(CommonLibrary.getSettingsSheetInfo().get("WEB_URL_MAIN_QA").toString());
            String userName = dataObj.get("User Name");
            String password = dataObj.get("Password");
            String Desc;
            Desc = "Login To App";
            CommonLibrary.loginWebApp(userName, password, Desc);

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Logged in as " + userName, LogStatus.INFO, false);
            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();
            String AccNo = Desc.substring(9).trim();
            System.out.println(AccNo);
            findDynamicElement(By.xpath(addFundsLink), 120);
            String accountBalanceBeforeAddingAmount = FunctionLibrary.objDriver.findElement(By.xpath(accountBalanceTxt)).getText();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Accoount Balance before adding replenishment is---->" + "</br>" +
                    accountBalanceBeforeAddingAmount, LogStatus.INFO, true);
            FunctionLibrary.objDriver.findElement(By.xpath(addFundsLink)).click();

            findDynamicElement(By.xpath(paymentAmountTxtBox), 120);
            int defaultamount = Integer.parseInt(thresholdAmount);
            int currentReplenishmentAmount = defaultamount - 1;
            String currentAmount = String.valueOf(currentReplenishmentAmount);
            FunctionLibrary.objDriver.findElement(By.xpath(paymentAmountTxtBox)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(paymentAmountTxtBox)).sendKeys(currentAmount);

            try {
//FunctionLibrary.scrollDown(continueForPaymentBtn, "scroll down");
                WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(continueForPaymentBtn));
                System.out.println("click try in try block");
//element.click();
                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
                        (errorMsgTxt, continueForPaymentBtn, 10, 60);
            } catch (Exception e) {
                System.out.println("click try in catch block");
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(continueForPaymentBtn)));
                FunctionLibrary.objDriver.findElement(By.xpath(continueForPaymentBtn)).click();
            }


            findDynamicElement(By.xpath(errorMsgTxt), 120);
            String errorMsgContent = FunctionLibrary.objDriver.findElement(By.xpath(errorMsgTxt)).getText();
            findDynamicElement(By.xpath(errorMsgContentTxt), 120);
            if (!errorMsgContent.equals(null)) {
                String msgContent = FunctionLibrary.objDriver.findElement(By.xpath(errorMsgContentTxt)).getText();
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Error message should be displayed" + "<br>"
                        + "Actual: Error message is displayed with content-------------->" + "<br>" + msgContent, LogStatus.PASS, true);
            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Error message should be displayed" + "<br>"
                        + "Actual: Error message is not displayed", LogStatus.FAIL, true);
            }
            CommonLibrary.logoutWebApp();
        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" + e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }
    }

    public static void tcAccountMaintenanceWebACMW217() throws Exception {
        try {
            String userName = dataObj.get("User Name");
            String password = dataObj.get("Password");
            String Desc;
            Desc = "Login To App";
            CommonLibrary.loginWebApp(userName, password, Desc);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Logged in as " + userName, LogStatus.INFO, false);

            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();
            String AccNo = Desc.substring(9).trim();
            System.out.println(AccNo);
            findDynamicElement(By.xpath(xpathAccountProfile), 120);
            FunctionLibrary.objDriver.findElement(By.xpath(managePayementMethod)).click();
           /* findDynamicElement(By.xpath(updateAccountProfileBtn), 120);
            scrollToView(By.xpath(managePayementMethod));
            try {
                //scrollToView(By.xpath(managePaymentMethodLink));
                WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(managePayementMethod));
                System.out.println("click try in try block");
                element.click();
            } catch (Exception e) {
                System.out.println("click try in catch block");
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(managePaymentMethodLink)));
                FunctionLibrary.objDriver.findElement(By.xpath(managePaymentMethodLink)).click();
            }*/

            findDynamicElement(By.xpath(addCreditCardOrBankAccountBtn), 120);
            scrollToView(By.xpath(addCreditCardOrBankAccountBtn));
            try {
                WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(addCreditCardOrBankAccountBtn));
                System.out.println("click try in try block");
                element.click();
            } catch (Exception e) {
                System.out.println("click try in catch block");
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(addCreditCardOrBankAccountBtn)));
                FunctionLibrary.objDriver.findElement(By.xpath(addCreditCardOrBankAccountBtn)).click();
            }
            findDynamicElement(By.xpath(addPaymentMethodTxt), 120);

            FunctionLibrary.selectDropDownListByValue(cardTypeDropDown, dataObj.get("Card Type"), "Entering Card type");
            FunctionLibrary.objDriver.findElement(By.xpath(accountNumberTxtBox)).sendKeys(dataObj.get("Card Number"));
            FunctionLibrary.selectDropDownListByValue(expiryMonthDropDown, dataObj.get("Exp Month"), "Entering expiry month");
            FunctionLibrary.selectDropDownListByValue(expiryYearDropDown, dataObj.get("Exp Year"), "Entering expiry year");
            FunctionLibrary.objDriver.findElement(By.xpath(cardCVVNbrTxtBox)).sendKeys(dataObj.get("Ccv Number"));
            String mailingAddress = dataObj.get("MailingAddress");
            mailingAddress = mailingAddress.replace("\n", "");

            String[] addressDetails = mailingAddress.split(":");
            String Address1 = addressDetails[0];
            String Address2 = "";
            String City = addressDetails[1];
            String State = addressDetails[2];
            String Country = addressDetails[3];
            String ZipCode = addressDetails[4];

            FunctionLibrary.objDriver.findElement(By.xpath(addressLine1TxtBox)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(addressLine1TxtBox)).sendKeys(Address1);
            FunctionLibrary.objDriver.findElement(By.xpath(addressLine2TxtBox)).sendKeys(Address2);
            FunctionLibrary.objDriver.findElement(By.xpath(cityTxtBox)).sendKeys(City);
            FunctionLibrary.objDriver.findElement(By.xpath(zipCode1TxtBox)).sendKeys(ZipCode);
            FunctionLibrary.selectDropDownListByValue(stateDropDown, State, "Selecting state");
            FunctionLibrary.selectDropDownListByValue(countryDropDown, Country, "Selecting country");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Entering AMEX card details", LogStatus.INFO, true);

            try {
                scrollToView(By.xpath(submitPaymentBtn));
                WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(submitPaymentBtn));
                System.out.println("click try in try block");
                element.click();
            } catch (Exception e) {
                System.out.println("click try in catch block");
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(submitPaymentBtn)));
                FunctionLibrary.objDriver.findElement(By.xpath(submitPaymentBtn)).click();
            }
            findDynamicElement(By.xpath(creditCardAddedSuccessMsg), 120);
            String successMsg = FunctionLibrary.objDriver.findElement(By.xpath(creditCardAddedSuccessMsg)).getText();
            System.out.println(successMsg);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Confirmation message should be displayed" + "br"
                    + "Actual: Confirmation message is diaplyed with content----->" + successMsg, LogStatus.PASS, true);

            CommonLibrary.logoutWebApp();
//login to siebel for verification

            CommonLibrary.loginSiebelApp("", "", dataObj.get("Suname"), dataObj.get("SPWD"));
            CommonLibrary.searchForAccount(AccNo);

//Checking the status under Non-Financial History tab

            FunctionLibrary.clickObject(historyTab, "", "Click history link");
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(nonFinancialsHistoryTab)));
            FunctionLibrary.clickObject(nonFinancialsHistoryTab, "", "Clicking Non-Financial History Tab");
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(activitiesTable)));
            int rowNum = FunctionLibrary.getRowNumberFromWebTable(activitiesTable, "AUDITACCNT", "Get row number");
            FunctionLibrary.verifyWebTableCellData(activitiesTable, rowNum, 4, "VIEW", "Verify category", true);
            FunctionLibrary.verifyWebTableCellData(activitiesTable, rowNum, 3, "AUDITACCNT", "Verify sub-category", false);

//Checking under Re-bill Info section

            FunctionLibrary.objDriver.findElement(By.xpath(accountInfoBtn)).click();
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
                    (accountRebillInfoBtn, accountInfoBtn, 10, 40);
            FunctionLibrary.clickObject(accountRebillInfoBtn, "", "Clicking re-bill info section");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
                    (replenishmentAppletTable, accountRebillInfoBtn, 10, 40);
            int rowNum1 = FunctionLibrary.getRowNumberFromWebTable(replenishmentAppletTable, dataObj.get("Card Type"), "Get row number");
            FunctionLibrary.verifyWebTableCellData(replenishmentAppletTable, rowNum1, 2, dataObj.get("Card Type"), "Verify RE-BILL type", true);
// FunctionLibrary.verifyWebTableCellData(replenishmentAppletTable, rowNum1,4, "REBILL", "Verify sub-category", false);

//Checking re-bill info history section

            FunctionLibrary.clickObject(replenishmentHistoryTab, "", "Clicking re-bill history info section");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
                    (replenishmentHistoryAppletTable, accountRebillInfoBtn, 10, 40);
            int rowNum2 = FunctionLibrary.getRowNumberFromWebTable(replenishmentHistoryAppletTable, dataObj.get("Card Type"), "Get row number");
            FunctionLibrary.verifyWebTableCellData(replenishmentHistoryAppletTable, rowNum2, 4, dataObj.get("Card Type"), "Verify RE-BILL type", true);
            FunctionLibrary.verifyWebTableCellData(replenishmentHistoryAppletTable, rowNum2, 1, "ADD", "Verify sub-category", false);

            CommonLibrary.logoutSiebelApplicaiton();
        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" + e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }

    }

    public static void tcAccountMaintenanceWebACMW233() throws Exception {
        try {
            String userName = dataObj.get("User Name");
            String password = dataObj.get("Password");
            String Desc;
            Desc = "Login To App";
            CommonLibrary.loginWebApp(userName, password, Desc);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Logged in as " + userName, LogStatus.INFO, false);
            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();
            String AccNo = Desc.substring(9).trim();
            System.out.println(AccNo);
            findDynamicElement(By.xpath(addFundsLink), 120);
            String accountBalanceBeforeAddingAmount = FunctionLibrary.objDriver.findElement(By.xpath(accountBalanceTxt)).getText();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Accoount Balance before adding replenishment is---->" + "</br>" +
                    accountBalanceBeforeAddingAmount, LogStatus.INFO, true);
            String amountBefore = accountBalanceBeforeAddingAmount.substring(18);
            Float amountBeforeInInteger = Float.parseFloat(amountBefore);
            FunctionLibrary.objDriver.findElement(By.xpath(addFundsLink)).click();

            findDynamicElement(By.xpath(paymentAmountTxtBox), 120);
            String defaultReplenishmentValue = FunctionLibrary.objDriver.findElement(By.xpath(paymentAmountTxtBox)).getAttribute("value");
            defaultReplenishmentValue = defaultReplenishmentValue.substring(0, defaultReplenishmentValue.length() - 3);
            int defaultamount = Integer.parseInt(defaultReplenishmentValue);
            int currentReplenishmentAmount = defaultamount + 10;
            String currentAmount = String.valueOf(currentReplenishmentAmount);
            FunctionLibrary.objDriver.findElement(By.xpath(paymentAmountTxtBox)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(paymentAmountTxtBox)).sendKeys(currentAmount);

            try {
                FunctionLibrary.scrollDown(continueForPaymentBtn, "scroll down");
                WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(continueForPaymentBtn));
                System.out.println("click try in try block");
                element.click();
            } catch (Exception e) {
                System.out.println("click try in catch block");
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(continueForPaymentBtn)));
                FunctionLibrary.objDriver.findElement(By.xpath(continueForPaymentBtn)).click();
            }
            findDynamicElement(By.xpath(cardCVVNbrTxtBox), 120);
            FunctionLibrary.objDriver.findElement(By.xpath(cardCVVNbrTxtBox)).sendKeys("112");
            FunctionLibrary.scrollDown(continueForPaymentBtn, "scroll down");
            FunctionLibrary.clickObject(continueForPaymentBtn, "", "Click continue button");
            findDynamicElement(By.xpath(returnToMyAccountBtn), 120);
            String confirmationMsg = FunctionLibrary.objDriver.findElement(By.xpath(replenishmentAddConfirmationMsg)).getText();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected Result : Confirmation message should be displayed" + "</br>"
                    + "Actual Message : Confirmation Message is displayed with below content----->" + "</br>" + confirmationMsg, LogStatus.PASS, true);
            FunctionLibrary.objDriver.findElement(By.xpath(returnToMyAccountBtn)).click();
            findDynamicElement(By.xpath(addFundsLink), 120);
            String accountBalanceAfterAddingAmount = FunctionLibrary.objDriver.findElement(By.xpath(accountBalanceTxt)).getText();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Accoount Balance after adding replenishment is---->" + "</br>" +
                    accountBalanceAfterAddingAmount, LogStatus.INFO, true);
            String amountAfter = accountBalanceAfterAddingAmount.substring(18);
            Float amountAfterInInteger = Float.parseFloat(amountAfter);
            Float actualAmount = amountBeforeInInteger + currentReplenishmentAmount;

            if (actualAmount.equals(amountAfterInInteger)) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected Result : Account balance should be updated" + "</br>"
                        + "Actual Result : Account balance is updated with amount" + amountAfterInInteger, LogStatus.PASS, true);
            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected Result : Account balance should be updated" + "</br>"
                        + "Actual Result : Account balance is not updated", LogStatus.FAIL, true);
            }
            CommonLibrary.logoutWebApp();
            CommonLibrary.loginSiebelApp("", "", dataObj.get("Suname"), dataObj.get("SPWD"));
            CommonLibrary.searchForAccount(AccNo);

//Checking the status under Non-Financial History tab

            FunctionLibrary.clickObject(historyTab, "", "Click history link");
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(nonFinancialsHistoryTab)));
            FunctionLibrary.clickObject(nonFinancialsHistoryTab, "", "Clicking Non-Financial History Tab");
            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(activitiesTable)));
            int rowNum = FunctionLibrary.getRowNumberFromWebTable(activitiesTable, "AUDITACCNT", "Get row number");
            FunctionLibrary.verifyWebTableCellData(activitiesTable, rowNum, 3, "AUDITACCNT", "Verify category", true);
            FunctionLibrary.verifyWebTableCellData(activitiesTable, rowNum, 4, "VIEW", "Verify sub-category", false);

//Checking under Financial History section

            FunctionLibrary.clickObject(financialsTab2, "", "Clicking Financials Tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
                    (accountFinancialHistoryListTable, financialsTab2, 10, 40);
            int rowNum1 = FunctionLibrary.getRowNumberFromWebTable(accountFinancialHistoryListTable, "TOLDEPOSIT", "Get row number");
            FunctionLibrary.verifyWebTableCellData(accountFinancialHistoryListTable, rowNum1, 6, "TOLDEPOSIT", "Verify category", true);
            FunctionLibrary.verifyWebTableCellData(accountFinancialHistoryListTable, rowNum1, 7, "PPTL", "Verify sub-category", false);

            CommonLibrary.logoutSiebelApplicaiton();
        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" + e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }
    }

    public static void tcAccountMaintenanceWebACMW234() throws Exception {
        try {
            String userName = dataObj.get("User Name");
            String password = dataObj.get("Password");
            String Desc;
            Desc = "Login To App";
            CommonLibrary.loginWebApp(userName, password, Desc);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Logged in as " + userName, LogStatus.INFO, false);
            Desc = FunctionLibrary.objDriver.findElement(By.xpath(getAccNo)).getText();
            String AccNo = Desc.substring(9).trim();
            System.out.println(AccNo);
            findDynamicElement(By.xpath(addFundsLink), 120);
            String accountBalanceBeforeAddingAmount = FunctionLibrary.objDriver.findElement(By.xpath(accountBalanceTxt)).getText();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Accoount Balance before adding replenishment is---->" + "</br>" +
                    accountBalanceBeforeAddingAmount, LogStatus.INFO, true);
            String amountBefore = accountBalanceBeforeAddingAmount.substring(18);
            Float amountBeforeInInteger = Float.parseFloat(amountBefore);
            FunctionLibrary.objDriver.findElement(By.xpath(addFundsLink)).click();
            findDynamicElement(By.xpath(paymentAmountTxtBox), 120);
            String defaultReplenishmentValue = FunctionLibrary.objDriver.findElement(By.xpath(paymentAmountTxtBox)).getAttribute("value");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Default Replenishment amount is : " + defaultReplenishmentValue,
                    LogStatus.INFO, true);
            defaultReplenishmentValue = defaultReplenishmentValue.substring(0, defaultReplenishmentValue.length() - 3);
            int defaultamount = Integer.parseInt(defaultReplenishmentValue);
            int currentReplenishmentAmount = defaultamount - 10;
            String currentAmount = String.valueOf(currentReplenishmentAmount);
            FunctionLibrary.objDriver.findElement(By.xpath(paymentAmountTxtBox)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(paymentAmountTxtBox)).sendKeys(currentAmount);

            try {
                FunctionLibrary.scrollDown(continueForPaymentBtn, "scroll down");
                WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(continueForPaymentBtn));
                System.out.println("click try in try block");
                element.click();
            } catch (Exception e) {
                System.out.println("click try in catch block");
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(continueForPaymentBtn)));
                FunctionLibrary.objDriver.findElement(By.xpath(continueForPaymentBtn)).click();
            }
            findDynamicElement(By.xpath(errorMsgTxt), 120);
            String errorMsgContent = FunctionLibrary.objDriver.findElement(By.xpath(errorMsgTxt)).getText();
            findDynamicElement(By.xpath(errorMsgContentTxt), 120);
            if (!errorMsgContent.equals(null)) {
                String msgContent = FunctionLibrary.objDriver.findElement(By.xpath(errorMsgContentTxt)).getText();
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Error message should be displayed" + "<br>"
                        + "Actual: Error message is displayed with content-------------->" + "<br>" + msgContent, LogStatus.PASS, true);
            } else {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Error message should be displayed" + "<br>"
                        + "Actual: Error message is not displayed", LogStatus.FAIL, true);
            }
            CommonLibrary.logoutWebApp();
            Thread.sleep(3000);
        } catch (Exception e) {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unknown Error Occurred" + e.getMessage(), LogStatus.FAIL, true);
            FunctionLibrary.closeAllActiveBrowser();
        }

    }

    public static void writeVehicleDetailsToExcel(String testDataFileName, String sheetname, int rownum, String Duplicate) throws EncryptedDocumentException, InvalidFormatException, IOException {

        String filelocation = ReportLibrary.getPath() + "\\testdata\\" + testDataFileName + ".xlsx";
        FileInputStream fis = new FileInputStream(filelocation);

        String mailingAddress = dataObj.get("MailingAddress");
        mailingAddress = mailingAddress.replace("\n", "");
        String[] addressDetails = mailingAddress.split(":");
        XSSFWorkbook w = new XSSFWorkbook(fis);
        XSSFSheet s = w.getSheet(sheetname);
        int count = 0;
        int NumberOfRows = rownum + 4;
        for (int i = 4; i < NumberOfRows; i++) {
            XSSFRow r = s.getRow(i);
            String state = addressDetails[1];
            String country = addressDetails[0];
            String a[] = new String[13];
            if (!Duplicate.equalsIgnoreCase("NA") && count == 0) {
                a[0] = Duplicate;
                count = 1;
                System.out.print(a[0]);
            } else {
                a[0] = state + randomString(3) + DateTime.now().getMillisOfSecond();
                System.out.print(a[0]);
            }
            a[1] = country;
            a[2] = state;
            a[3] = "0";
            a[4] = "2014";
            a[5] = "Acura";
            a[6] = "1.5";
            a[7] = "Brown";
            DateFormat dateFormat = new SimpleDateFormat("MM-dd-YYYY hh:mm:ss a");
            Calendar calReturn = Calendar.getInstance();
            calReturn.add(Calendar.DATE, -30);
            Date date = calReturn.getTime();
            System.out.println(calReturn.getTime() + "   " + dateFormat.format(date));
            a[8] = dateFormat.format(date);
            //  a[8] ="04-15-2017 10:30:00 AM";

            dateFormat.format(date);
            calReturn.add(Calendar.DATE, +180);
            Date date1 = calReturn.getTime();
            System.out.println(calReturn.getTime() + "   " + dateFormat.format(date1));
            a[9] = dateFormat.format(date1);
            a[9] = "10-16-2017 10:30:00 AM";
            a[10] = "N";
            a[11] = "N";
            a[12] = "N";
            for (int j = 0; j < 1; j++) {
                Cell cell = r.createCell(j);
                // cell.setCellType(cell.CELL_TYPE_STRING);
                cell.setCellValue(a[j]);
            }
            System.out.println(i);
        }
    /*XSSFCellStyle headerStyle = w.createCellStyle();
    Font headerFont = w.createFont();
    //headerStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
    if (headerRow) {
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
    } else {
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
    }
    headerStyle.setFont(headerFont);
    cell.setCellStyle(headerStyle);*/
        FileOutputStream fos = new FileOutputStream(filelocation);
        w.write(fos);
        fos.close();

    }

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static SecureRandom rnd = new SecureRandom();

    public static String randomString(int len) {

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }
    static final String numerics = "0123456789";
    static SecureRandom rndNumber = new SecureRandom();

    public static String securityPin(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(numerics.charAt(rndNumber.nextInt(numerics.length())));
        return sb.toString();
    }


}
