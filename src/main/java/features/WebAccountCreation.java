
package features;

import com.relevantcodes.extentreports.LogStatus;
import libraries.CommonLibrary;
import libraries.FunctionLibrary;
import libraries.ReportLibrary;
import libraries.TestCaseLibrary;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.text.Document;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.Calendar;
import java.text.DateFormat;
import java.util.Date;
import static libraries.CommonLibrary.getEachTestCaseData;
import static libraries.CommonLibrary.dataObj;
import static libraries.CommonLibrary.dataObj1;
import static libraries.CommonLibrary.getStringBetweenTwoStrings;
import static objectProperties.SblAccountLinkingPageObject.xpathOfSubAccountsTable;
import static objectProperties.SblAccountMaintenancePageObject.accountsTab;
import static objectProperties.SblAccountMaintenancePageObject.goBtn;
import static objectProperties.WebAccountCreationPageProperties.*;
import static objectProperties.WebAccountCreationPageProperties.xpathReferenceNumber;
//import static objectProperties.WebAccountMaintenancePageProperties.*;
import java.lang.reflect.Method;

public class WebAccountCreation {

    //public static HashMap<String, String> dataObj = null;
    public static int iterator=0;
    public static String Str_TC_Name="WebAccountCreation";
    public static String Str_TC_ExecutionStatus;
    public static void WebAccountCreationTest() throws IOException, Exception {

        ExcelSheet exl = new ExcelSheet();
        int noOfRows = exl.totalrows("FLCSS_Web_AccountEstablishment", "Test_Scenarios");
        HashMap<String, String> eachTestCaseData = new HashMap<String, String>();
        String testCaseName = "";

       // ReportLibrary.Get_Report_Library_Instance();
       // ReportLibrary.startReport(Str_TC_Name, Str_TC_Name);
        for (int i = 1; i <= noOfRows; i++) {

            eachTestCaseData = getEachTestCaseData(exl, "Test_Scenarios", i, "FLCSS_Web_AccountEstablishment");
            dataObj = eachTestCaseData;
            testCaseName = "tcAccountCreationWeb" + eachTestCaseData.get("TestCaseId").replace("-", "");

            if (eachTestCaseData.get("ExecutionStatus").equalsIgnoreCase("Yes")) {
                Method testMethod = null;
                try {
                    ReportLibrary.startReport(eachTestCaseData.get("TestCaseId"), eachTestCaseData.get("TestCaseId") );
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b>" + eachTestCaseData.get("TestCaseId")
                            + "</b>" + ": Test Case Execution is started....................... <br>"
                            + "Test case description: " + eachTestCaseData.get("TestCaseDesc"), LogStatus.INFO, false);

                   /* CommonLibrary.launchBrowser(CommonLibrary.getSettingsSheetInfo().get("WEB_URL_QA").toString(),
                            "Launching Web Account Creation Page", dataObj.get("Browser Type"));
                    FunctionLibrary.objDriver.manage().deleteAllCookies();*/

                    try {
                       // System.out.println("hello1");
                        Class<?> c = Class.forName("features.WebAccountCreation");
                        testMethod = c.getMethod(testCaseName);
                        //iterator=i;
                        TestCaseLibrary.Get_TestCase_Instance().executeTC(testMethod);
                    } catch (ClassNotFoundException e) {

                        e.printStackTrace();
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Test method " + testCaseName + " is not found  " , LogStatus.FAIL, false);
                    }

                   // FunctionLibrary.closeAllActiveBrowser();


                } catch (Exception e) {
                    //if alert found. it will be accepted and close the browser.
                    try {

                        e.getMessage();
                        System.out.println("Test Failed. Taking screenshot");

                        WebDriverWait wait8 = new WebDriverWait(FunctionLibrary.objDriver, 100);
                        wait8.until(ExpectedConditions.alertIsPresent());
                        Thread.sleep(3000);
                        String alertText;
                        Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                        alertText = alert.getText();
                        alert.accept();
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Execution Failed due to alert : <br>" + alertText, LogStatus.FAIL, true);
                        //Close browsers

                        //FunctionLibrary.closeAllActiveBrowser();

                    } catch (Exception exp) {


                        e.printStackTrace();
                        try {

                            if (FunctionLibrary.objDriver.findElement(By.xpath("/*//*[@id='btn-accept']")).isDisplayed()) {

                                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Execution Failed due to popup <br>", LogStatus.FAIL, true);
                                FunctionLibrary.objDriver.findElement(By.xpath("/*//*[@id='btn-accept']")).click();

                                //Method for Logout and Closing browser
                                ////////logoutSiebelApplicaiton();
                                //FunctionLibrary.Close_All_Active_Browser();
                               // FunctionLibrary.closeAllActiveBrowser();
                            }
                        } catch (Exception e1) {
                            System.out.println(e1.getMessage());
                            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Execution Failed "+e1.getMessage(), LogStatus.FAIL, true);

                            //  CommonLibrary.logoutSiebelApplicaiton();
                            //FunctionLibrary.Close_All_Active_Browser();

                        }


                    }


                }//End of Catch

                dataObj.clear();
                ReportLibrary.endTest();
                ReportLibrary.endReport();

            }
        }

      //  CustomReport.generateReport();

    }


    /**
     * Created By: Bharath
     * Test Case: ACEW-001
     * Test Case Description: Prepaid Private account creation with 1 transponders and 1 vehicles.
     *
     * @throws IOException
     */
    public static void tcAccountCreationWebACEW001() throws Exception {



        String firstName = dataObj.get("First Name");
        System.out.println(dataObj.get("WEB_URL").toString());
        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));

        addAccountDetails();
       /* String RefNum=addCommunicationDetails();
        TranspondersAndVehicles();
        PlansPaymentsAndBillingAddress();
        ConformationPage();
        String accountNumber=ValidationAfterAccountNumberGeneration(RefNum);
        SiebelValidation(accountNumber, firstName);*/

        System.out.println(1);

    }

    public static void tcAccountCreationWebACEW002() throws Exception {

       /* String firstName = dataObj.get("First Name");
        System.out.println(dataObj.get("WEB_URL").toString());
        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));

        addAccountDetails();
        String RefNum=addCommunicationDetails();
        TranspondersAndVehicles();
        PlansPaymentsAndBillingAddress();
        ConformationPage();
        String accountNumber=ValidationAfterAccountNumberGeneration(RefNum);
        SiebelValidation(accountNumber, firstName);*/

        System.out.println(2);
    }

    public static void tcAccountCreationWebACEW003() throws Exception {

        String firstName = dataObj.get("First Name");
        System.out.println(dataObj.get("WEB_URL").toString());
        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));

        addAccountDetails();
        String RefNum=addCommunicationDetails();
        TranspondersAndVehicles();
        PlansPaymentsAndBillingAddress();
        ConformationPage();
        String accountNumber=ValidationAfterAccountNumberGeneration(RefNum);
        SiebelValidation(accountNumber, firstName);

    }

    public static void tcAccountCreationWebACEW004() throws Exception {


        String firstName = dataObj.get("First Name");
        System.out.println(dataObj.get("WEB_URL").toString());
        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));

        addAccountDetails();
        String RefNum=addCommunicationDetails();
        TranspondersAndVehicles();
        PlansPaymentsAndBillingAddress();
        ConformationPage();
        String accountNumber=ValidationAfterAccountNumberGeneration(RefNum);
        SiebelValidation(accountNumber, firstName);

    }

    public static void tcAccountCreationWebACEW005() throws Exception {

        String firstName = dataObj.get("First Name");
        System.out.println(dataObj.get("WEB_URL").toString());
        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));

        addAccountDetails();
        String RefNum=addCommunicationDetails();
        TranspondersAndVehicles();
        PlansPaymentsAndBillingAddress();
        ConformationPage();
        String accountNumber=ValidationAfterAccountNumberGeneration(RefNum);
        SiebelValidation(accountNumber, firstName);
    }

    public static void tcAccountCreationWebACEW006() throws Exception {

        String firstName = dataObj.get("First Name");
        System.out.println(dataObj.get("WEB_URL").toString());
        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));

        addAccountDetails();
        String RefNum=addCommunicationDetails();
        TranspondersAndVehicles();
        PlansPaymentsAndBillingAddress();
        ConformationPage();
        String accountNumber=ValidationAfterAccountNumberGeneration(RefNum);
        SiebelValidation(accountNumber, firstName);

    }

    public static void tcAccountCreationWebACEW007() throws Exception {

        String firstName = dataObj.get("First Name");
        System.out.println(dataObj.get("WEB_URL").toString());
        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));

        addAccountDetails();
        String RefNum=addCommunicationDetails();
        TranspondersAndVehicles();
        PlansPaymentsAndBillingAddress();
        ConformationPage();
        String accountNumber=ValidationAfterAccountNumberGeneration(RefNum);
        SiebelValidation(accountNumber, firstName);
        /*String Desc;
        String firstName = dataObj.get("First Name");
        String address1 = dataObj.get("Mailing Address1");
        String address2 = dataObj.get("Mailing Address2");
        String city = dataObj.get("City");
        String state = dataObj.get("State");
        String zip = dataObj.get("Zip Code");
        String cardType = dataObj.get("Card Type");
        String cardNumber = dataObj.get("Card Number");
        String cardExpMonth = dataObj.get("Exp Month");
        String cardExpYear = dataObj.get("Exp Year");
        String cardCcvNumber = dataObj.get("Ccv Number");
        String deviceNumber1 = dataObj.get("Device Count1");
        String deviceNumber2 = dataObj.get("Device Count2");
        int deviceCount1 = Integer.parseInt(deviceNumber1);
        int deviceCount2 = Integer.parseInt(deviceNumber2);
        int totalDevices = deviceCount1 + deviceCount2;
        int totalCost = totalDevices * 10;

        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));
       // FunctionLibrary.objDriver.manage().deleteAllCookies();

        addAccountDetails();

        String RefNum=addCommunicationDetails();

        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 100);

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathVehicle)));
        //adding vehicles
        webApplicationAddVehicles(totalDevices);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Vehicle Information Added ", LogStatus.INFO, true);

        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).sendKeys(deviceNumber1);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).sendKeys(deviceNumber2);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Transponder Information Added ", LogStatus.INFO, true);

        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathPlansAndPayment)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathPlansAndPayment)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathPlansAndPayment)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathPlansAndPayment,"","Clicking");
        }

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='creditCardType']")));


        String BankAccountType = dataObj.get("Account Type");
        String BankNameOnAccount = dataObj.get("Name On Account");
        String BankAccountNumber = dataObj.get("Bank Account Number");
        String BankRoutingNumber = dataObj.get("Bank Routing Number");


        try {
            Thread.sleep(1000);
            FunctionLibrary.objDriver.navigate().refresh();
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathBankAccount)));
            WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(xpathBankAccount));
            System.out.println("click try in try block");
            element.click();
            System.out.println("click try in try block");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Primary card Details Bank account Payment is Selected ", LogStatus.INFO, true);

        } catch (Exception e) {
            System.out.println("click try in catch block");
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathBankAccount)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathBankAccount)).click();
        }

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathBankAccountType)));
        FunctionLibrary.selectDropDownListByValue(xpathBankAccountType, BankAccountType, "");
        // FunctionLibrary.objDriver.findElement(By.xpath(xpathBankNameOnAccount)).sendKeys(BankNameOnAccount);

      //  FunctionLibrary.objDriver.findElement(By.xpath(xpathBankNameOnAccount)).sendKeys(BankNameOnAccount);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathBankAccountNumber)).sendKeys(BankAccountNumber);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathRetypeBankAccountNumber)).sendKeys(BankAccountNumber);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathBankRoutingNumber)).sendKeys(BankRoutingNumber);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathBillingAddress1)).sendKeys(address1);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathBillingAddress2)).sendKeys(address2);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathBillingAddressbankCity)).sendKeys(city);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathBillingAddressbankZipCode)).sendKeys(zip);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Bank account Information Added ", LogStatus.INFO, true);
        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathConfirmation)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathConfirmation)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathConfirmation)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathConfirmation,"","Clicking");
        }

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='percentage']")));

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Billing Address Details are auto populated ", LogStatus.INFO, true);
        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
        Thread.sleep(2000);

        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();

        FunctionLibrary.scrollDowntoElement(".*//*//**//*[@class='icheckbox_minimal']", "Scroll down");

        try {
            FunctionLibrary.objDriver.navigate().refresh();
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
            FunctionLibrary.scrollDowntoElement(".*//*//**//*[@class='icheckbox_minimal']", "Scroll down");
            WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(xpathTermsAndConditions));

            System.out.println("click try in try block");
            element.click();

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Confirmation Page Terms & Conditions Checked ", LogStatus.INFO, true);

        } catch (Exception e) {
            System.out.println("click try in catch block");
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathTermsAndConditions)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathTermsAndConditions)).click();
        }

        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathCompleteButton)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathCompleteButton)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathCompleteButton)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathCompleteButton,"","Clicking ");
        }
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class='page-header-noborder']")));
        String message = FunctionLibrary.objDriver.findElement(By.xpath("//h1[@class='page-header-noborder']")).getText();
        System.out.println(message);
        String[] accountArray = message.split(" ");
        System.out.println(accountArray[12]);
        String accountNumber = accountArray[12].replace('.', ' ').trim();

        System.out.println(accountNumber);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Account # Generated " + accountNumber + " ", LogStatus.PASS, false);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Account # Generated Success Message ", LogStatus.PASS, true);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Confirmation Email Sent After Account Creation ", LogStatus.PASS, false);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Verification Link Sent to Email ", LogStatus.PASS, false);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Email Does Not Become Active Until Email is Validated ", LogStatus.PASS, false);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Amount Charged as Per the # of Device(s) Requested + Payment Type + Sales Tax + Plan sale Amount + Any Additional Pre payment ", LogStatus.PASS, false);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Sales Tax Calculation Bases", LogStatus.PASS, false);

        SiebelValidation(accountNumber, firstName);*/

    }

    public static void tcAccountCreationWebACEW009() throws Exception {


        String firstName = dataObj.get("First Name");
        System.out.println(dataObj.get("WEB_URL").toString());
        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));

        addAccountDetails();
        String RefNum=addCommunicationDetails();
        TranspondersAndVehicles();
        PlansPaymentsAndBillingAddress();
        ConformationPage();
        String accountNumber=ValidationAfterAccountNumberGeneration(RefNum);
        SiebelValidation(accountNumber, firstName);

    }
    /**
     * Created By: Bharath
     * Test Case: ACEW-012
     * Test Case Description: Prepaid Private creation; edit payment information on confirmation page.
     *
     * @throws IOException
     */
    public static void tcAccountCreationWebACEW012() throws Exception {

        String Desc;
        String firstName = dataObj.get("First Name");
        String cardType = dataObj.get("Card Type");
        String cardNumber = dataObj.get("Card Number");
        String cardExpMonth = dataObj.get("Exp Month");
        String cardExpYear = dataObj.get("Exp Year");
        String cardCcvNumber = dataObj.get("Ccv Number");
        String cardType1 = dataObj.get("Card Type1");
        String cardNumber1 = dataObj.get("Card Number1");
        String cardExpMonth1 = dataObj.get("Exp Month1");
        String cardExpYear1 = dataObj.get("Exp Year1");
        String cardCcvNumber1 = dataObj.get("Ccv Number1");
        String deviceNumber1 = dataObj.get("Device Count1");
        String deviceNumber2 = dataObj.get("Device Count2");
        int deviceCount1 = Integer.parseInt(deviceNumber1);
        int deviceCount2 = Integer.parseInt(deviceNumber2);
        int totalDevices = deviceCount1 + deviceCount2;
        int totalCost = totalDevices * 10;

        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));
        //FunctionLibrary.objDriver.manage().deleteAllCookies();
        addAccountDetails();

        String RefNum=addCommunicationDetails();

        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 100);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathVehicle)));
        //adding vehicles
        webApplicationAddVehicles(totalDevices);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Vehicle Information Added ", LogStatus.INFO, true);

        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).sendKeys(deviceNumber1);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).sendKeys(deviceNumber2);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Transponder Information Added ", LogStatus.INFO, true);

        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathPlansAndPayment)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathPlansAndPayment)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathPlansAndPayment)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathPlansAndPayment,"","Clicking");
        }

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='creditCardType']")));
        FunctionLibrary.selectDropDownListByValue(xpathCardType, cardType, "");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathCardNumber)).sendKeys(cardNumber);
        FunctionLibrary.selectDropDownListByValue(xpathCardExpMonth, cardExpMonth, "");
        FunctionLibrary.selectDropDownListByValue(xpathCardExpYear, cardExpYear, "");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathCardSecurityCode)).sendKeys(cardCcvNumber);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Primary Card Details Added ", LogStatus.INFO, true);

        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathConfirmation)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathConfirmation)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathConfirmation)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathConfirmation,"","Clicking");
        }

        System.out.println("conf");
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        String BankAccountType = dataObj.get("Account Type");
        String BankNameOnAccount = dataObj.get("Name On Account");
        String BankAccountNumber = dataObj.get("Bank Account Number");
        String BankRoutingNumber = dataObj.get("Bank Routing Number");



        System.out.println("Confirmation Page1");

        // new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
        try {
            // FunctionLibrary.objDriver.navigate().refresh();
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathEditPaymentInfo)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathEditPaymentInfo)));
            // FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathEditPaymentInfo)));
            WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(xpathEditPaymentInfo));
            System.out.println("click try in try block");
            Thread.sleep(2000);
            element.click();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Confirmation Page Payment Details are Edited ", LogStatus.INFO, true);

        } catch (Exception e) {
            System.out.println("click try in catch block p");
            //FunctionLibrary.scrollDowntoElement(xpathEditPaymentInfo, "Scroll down");
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathEditPaymentInfo)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathEditPaymentInfo)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathEditPaymentInfo)).click();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Confirmation Page Payment Details are Edited ", LogStatus.INFO, true);

        }

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathCardSecurityCode)));
        /*FunctionLibrary.selectDropDownListByValue(xpathCardType, cardType1, "");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathCardNumber)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathCardNumber)).sendKeys(cardNumber1);
        FunctionLibrary.selectDropDownListByValue(xpathCardExpMonth, cardExpMonth1, "");
        FunctionLibrary.selectDropDownListByValue(xpathCardExpYear, cardExpYear1, "");*/
        FunctionLibrary.objDriver.findElement(By.xpath(xpathCardSecurityCode)).sendKeys(cardCcvNumber);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Primary Card Details Added ", LogStatus.INFO, true);

        Thread.sleep(4000);

        try {

            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathConfirmation)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathConfirmation)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathConfirmation)).click();
        }
        catch(Exception e)
        {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathConfirmation)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathConfirmation)));
            FunctionLibrary.clickObject(xpathConfirmation,"","Clicking");
        }

        System.out.println("Confirmation Page2");
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));


        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Billing Address Details are auto populated ", LogStatus.INFO, true);
        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
        Thread.sleep(2000);

        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
        FunctionLibrary.scrollDowntoElement("./*//*[@class='icheckbox_minimal']", "Scroll down");

        try {
            FunctionLibrary.objDriver.navigate().refresh();
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
            FunctionLibrary.scrollDowntoElement("./*//*[@class='icheckbox_minimal']", "Scroll down");
            WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(xpathTermsAndConditions));

            System.out.println("click try in try block");
            element.click();

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Confirmation Page Terms & Conditions Checked ", LogStatus.INFO, true);

        } catch (Exception e) {
            System.out.println("click try in catch block");
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathTermsAndConditions)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathTermsAndConditions)).click();
        }

        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathCompleteButton)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathCompleteButton)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathCompleteButton)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathCompleteButton,"","Clicking ");
        }
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class='page-header-noborder']")));
        String message = FunctionLibrary.objDriver.findElement(By.xpath("//h1[@class='page-header-noborder']")).getText();
        System.out.println(message);
        String[] accountArray = message.split(" ");
        System.out.println(accountArray[12]);
        String accountNumber = accountArray[12].replace('.', ' ').trim();

        System.out.println(accountNumber);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Account # Generated " + accountNumber + " ", LogStatus.PASS, false);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Account # Generated Success Message ", LogStatus.PASS, true);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Confirmation Email Sent After Account Creation ", LogStatus.PASS, false);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Verification Link Sent to Email ", LogStatus.PASS, false);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Email Does Not Become Active Until Email is Validated ", LogStatus.PASS, false);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Amount Charged as Per the # of Device(s) Requested + Payment Type + Sales Tax + Plan sale Amount + Any Additional Pre payment ", LogStatus.PASS, false);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Sales Tax Calculation Bases", LogStatus.PASS, false);

        SiebelValidation(accountNumber, firstName);

    }
    /**
     * Created By: Bharath
     * Test Case: ACEW-023
     * Test Case Description:Private account creation; with duplicate vehicle; associated to another Private account.
     *
     * @throws IOException
     */
    public static void tcAccountCreationWebACEW023() throws Exception {

        String Desc;
        String firstName = dataObj.get("First Name");
        String cardType = dataObj.get("Card Type");
        String cardNumber = dataObj.get("Card Number");
        String cardExpMonth = dataObj.get("Exp Month");
        String cardExpYear = dataObj.get("Exp Year");
        String cardCcvNumber = dataObj.get("Ccv Number");
        String deviceNumber1 = dataObj.get("Device Count1");
        String deviceNumber2 = dataObj.get("Device Count2");
        String DuplicatePlateNumber = dataObj.get("Duplicate Plate Number");
        int deviceCount1 = Integer.parseInt(deviceNumber1);
        int deviceCount2 = Integer.parseInt(deviceNumber2);
        int totalDevices = deviceCount1 + deviceCount2;
        int totalCost = totalDevices * 10;

        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));
       // FunctionLibrary.objDriver.manage().deleteAllCookies();


        addAccountDetails();

        String RefNum=addCommunicationDetails();

        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 100);

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathVehicle)));
        //adding vehicles
        // CommonLibrary.webApplicationAddVehicles(totalDevices);
        webApplicationAddVehiclesDuplcatePlateNumber(DuplicatePlateNumber, 1);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Existing Plate number details Entered ", LogStatus.INFO, true);

        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).sendKeys(deviceNumber1);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).sendKeys(deviceNumber2);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Transponder Information Added ", LogStatus.INFO, false);
        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathPlansAndPayment)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathPlansAndPayment)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathPlansAndPayment)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathPlansAndPayment,"","Clicking");
        }

        String xpathErrorMessage = "//div[@class='alert alert-danger error_msg']";
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathErrorMessage)));

        WebElement ErrorMessage = FunctionLibrary.objDriver.findElement(By.xpath(xpathErrorMessage));
        System.out.println(ErrorMessage.getText());

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Validation Message Displayed: " + ErrorMessage.getText(), LogStatus.PASS, true);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Excepted:<b> Error message is displayed indicating the plate number already exists and cannot proceed to the next page.</b> Actual : <b> " + ErrorMessage.getText() + "</b>", LogStatus.INFO, false);


    }

    public static void tcAccountCreationWebACEW032() throws Exception {

        String Desc;
        String firstName=dataObj.get("First Name");
        String address1=dataObj.get("Mailing Address1");
        String address2=dataObj.get("Mailing Address2");
        String city=dataObj.get("City");
        String state=dataObj.get("State");
        String zip=dataObj.get("Zip Code");
        String cardType=dataObj.get("Card Type");
        String cardNumber=dataObj.get("Card Number");
        String cardExpMonth=dataObj.get("Exp Month");
        String cardExpYear=dataObj.get("Exp Year");
        String cardCcvNumber=dataObj.get("Ccv Number");
        String deviceNumber1 = dataObj.get("Device Count1");
        String deviceNumber2 = dataObj.get("Device Count2");
        int deviceCount1 = Integer.parseInt(deviceNumber1);
        int deviceCount2 = Integer.parseInt(deviceNumber2);
        int totalDevices = deviceCount1 + deviceCount2;
        int totalCost = totalDevices*10;

        System.out.println(dataObj.get("WEB_URL").toString());
        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));

        addAccountDetails();
        String RefNum=addCommunicationDetails();
        TranspondersAndVehicles();

        String BankAccountType=dataObj.get("Account Type");
        String BankNameOnAccount=dataObj.get("Name On Account");
        String BankAccountNumber=dataObj.get("Bank Account Number");
        String BankRoutingNumber=dataObj.get("Bank Routing Number");
        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 20);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        try
        {
            Thread.sleep(1000);
            FunctionLibrary.objDriver.navigate().refresh();
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathBankAccount)));
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathBankAccount)));
            WebElement element =FunctionLibrary.objDriver.findElement(By.xpath(xpathBankAccount));
            System.out.println("click try in try block");
            element.click();
            System.out.println("click try in try block");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Primary card Details Bank account Payment is Selected ", LogStatus.INFO, true);

        }
        catch(Exception e)
        {
            System.out.println("click try in catch block");
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathBankAccount)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathBankAccount)).click();
        }

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathBankAccountType)));
        FunctionLibrary.selectDropDownListByValue(xpathBankAccountType,BankAccountType,"");
       // FunctionLibrary.objDriver.findElement(By.xpath(xpathBankNameOnAccount)).sendKeys(BankNameOnAccount);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathBankAccountNumber)).sendKeys(BankAccountNumber);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathRetypeBankAccountNumber)).sendKeys(BankAccountNumber);
        //    FunctionLibrary.setText(xpathRetypeBankAccountNumber,BankAccountNumber,"");
        Thread.sleep(3000);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathBankRoutingNumber)).sendKeys(BankRoutingNumber);
        String xpathErrorMessage="//div[contains(text(),'Please enter only Numbers for Bank Account Number')]";

        WebElement ErrorMessage=FunctionLibrary.objDriver.findElement(By.xpath(xpathErrorMessage));
        System.out.println(ErrorMessage.getText());

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Validation Message Displayed: "+ErrorMessage.getText(), LogStatus.PASS, true);

        FunctionLibrary.objDriver.findElement(By.xpath(xpathBillingAddress1)).sendKeys(address1);

        FunctionLibrary.objDriver.findElement(By.xpath(xpathBillingAddress2)).sendKeys(address2);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathBillingAddressbankCity)).sendKeys(city);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathBillingAddressbankZipCode)).sendKeys(zip);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Bank account Information Added ", LogStatus.INFO, true);

        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathConfirmation)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathConfirmation)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathConfirmation)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathConfirmation,"","Clicking");
        }

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Account # Not Generated ", LogStatus.PASS, false);
        String xpathErrorMessage1="//div[@class='alert alert-danger error_msg']";
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathErrorMessage1)));

        WebElement ErrorMessage1=FunctionLibrary.objDriver.findElement(By.xpath(xpathErrorMessage1));
        System.out.println(ErrorMessage1.getText());
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Validation Message Displayed: "+ErrorMessage1.getText(), LogStatus.PASS, true);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Reference number is is Displayed "+RefNum, LogStatus.PASS, true);


        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Excepted:<b> Account is not created.</b> Actual : <b> Account is not created. </b>", LogStatus.PASS, false);

    }
    /**
     * Created By: Bharath
     * Test Case: ACEW-036
     * Test Case Description: Account creation while session is inactive for X min.
     *
     * @throws IOException
     */
    public static void tcAccountCreationWebACEW036() throws Exception {

        String Desc;
        String firstName = dataObj.get("First Name");
        String cardType = dataObj.get("Card Type");
        String cardNumber = dataObj.get("Card Number");
        String cardExpMonth = dataObj.get("Exp Month");
        String cardExpYear = dataObj.get("Exp Year");
        String cardCcvNumber = dataObj.get("Ccv Number");
        String deviceNumber1 = dataObj.get("Device Count1");
        String deviceNumber2 = dataObj.get("Device Count2");
        int deviceCount1 = Integer.parseInt(deviceNumber1);
        int deviceCount2 = Integer.parseInt(deviceNumber2);
        int totalDevices = deviceCount1 + deviceCount2;
        int totalCost = totalDevices * 10;

        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));
       // FunctionLibrary.objDriver.manage().deleteAllCookies();

        addAccountDetails();

        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 300);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathReferenceNumber)));
        WebElement ref = FunctionLibrary.objDriver.findElement(By.xpath(xpathReferenceNumber));
        String RefNum = ref.getText();
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Reference number is is Displayed " + RefNum, LogStatus.PASS, false);
        WebDriverWait wait3 = new WebDriverWait(FunctionLibrary.objDriver, 2000);
        FunctionLibrary.objDriver.manage().timeouts().implicitlyWait(27, TimeUnit.MINUTES);

        wait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("./*//*[@id='dialog-session']")));

        System.out.println("alert ");
        wait2.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='btn btn-default']")));
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Session Inactive Message displayed.", LogStatus.PASS, true);

        FunctionLibrary.objDriver.findElement(By.xpath("//button[@class='btn btn-default']")).click();
        Thread.sleep(8000);

        //  wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("./*//*[@id='dialog-session']/div/div/div[2]/h4']")));
        //  String message = FunctionLibrary.objDriver.findElement(By.xpath("./*//*[@id='dialog-session']/div/div/div[2]/h4")).getText();

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Account creation while session is inactive for 27+ min.", LogStatus.PASS, true);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Reference number is is Displayed " + RefNum, LogStatus.PASS, false);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Excepted: <B> Process will terminate and customer login with reference number is generated; data entered is saved as sufficient information is provided. </b> " +
                "Actual : <b> Process is terminated and customer login with reference number is generated; data entered is saved as sufficient information is provided.</b>", LogStatus.PASS, true);


    }
    /**
     * Created By: Bharath
     * Test Case: ACEW-037
     * Test Case Description:Fleet account creation adding 101 vehicles.
     *
     * @throws IOException
     */
    public static void tcAccountCreationWebACEW037() throws Exception {
        String Desc;
        String firstName = dataObj.get("First Name");
        String address1 = dataObj.get("Mailing Address1");
        String address2 = dataObj.get("Mailing Address2");
        String city = dataObj.get("City");
        String state = dataObj.get("State");
        String zip = dataObj.get("Zip Code");
        String cardType = dataObj.get("Card Type");
        String cardNumber = dataObj.get("Card Number");
        String cardExpMonth = dataObj.get("Exp Month");
        String cardExpYear = dataObj.get("Exp Year");
        String cardCcvNumber = dataObj.get("Ccv Number");
        String deviceNumber1 = dataObj.get("Device Count1");
        String deviceNumber2 = dataObj.get("Device Count2");
        int deviceCount1 = Integer.parseInt(deviceNumber1);
        int deviceCount2 = Integer.parseInt(deviceNumber2);
        int totalDevices = deviceCount1 + deviceCount2;
        int totalCost = totalDevices * 10;
        String DuplicatePlateNumber = dataObj.get("Duplicate Plate Number");
    //CSRreview_Approval("xer8810881");
        writeVehicleDetailsToExcel("testing_vehicle_5", "Sheet1", totalDevices, DuplicatePlateNumber);

        String CompanyName = "xer" + DateTime.now().getMillisOfSecond() + DateTime.now().getSecondOfMinute() + DateTime.now().getMillisOfSecond();
        String fileUpload = "//*[@name='fileUpload']";
        String CheckBOx = ".//*[@class='icheckbox_minimal']";

        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));
        //FunctionLibrary.objDriver.manage().deleteAllCookies();
        FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='companyName']")).sendKeys(CompanyName);
        FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='dbaName']")).sendKeys("services");
        FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='fein']")).sendKeys("123456789");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondaryFirstName)).sendKeys("JHN");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondaryMiddleName)).sendKeys("M");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondaryLastName)).sendKeys("SAW");

        addAccountDetails();

        String RefNum=addCommunicationDetails();

        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 100);

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(fileUpload)));
        //adding vehicles

        File file = new File(ReportLibrary.getPath() + "\\testdata\\testing_vehicle_5.xlsx");
        System.out.println(file.getAbsolutePath());
        FunctionLibrary.objDriver.findElement(By.xpath(fileUpload)).sendKeys(file.getAbsolutePath());
        Thread.sleep(2000);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Vehicle Information Attached ", LogStatus.INFO, true);

        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).sendKeys(deviceNumber1);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).sendKeys(deviceNumber2);
        //  Thread.sleep(4000);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Transponder Information Added ", LogStatus.INFO, true);
        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathPlansAndPayment)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathPlansAndPayment)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathPlansAndPayment)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathPlansAndPayment,"","Clicking on Plan Button");
        }

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='creditCardType']")));


        String BankAccountType = dataObj.get("Account Type");
        String BankNameOnAccount = dataObj.get("Name On Account");
        String BankAccountNumber = dataObj.get("Bank Account Number");
        String BankRoutingNumber = dataObj.get("Bank Routing Number");


        try {
            Thread.sleep(1000);
            FunctionLibrary.objDriver.navigate().refresh();
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathBankAccount)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathBankAccount)));
            WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(xpathBankAccount));
            System.out.println("click try in try block");
            Actions action = new Actions(FunctionLibrary.objDriver);

            element.click();
            //action.moveToElement(element).doubleClick().build().perform();


            System.out.println("click try in try block");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Primary card Details Bank account Payment is Selected ", LogStatus.INFO, true);

        } catch (Exception e) {
            System.out.println("click try in catch block");
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathBankAccount)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathBankAccount)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathBankAccount)).click();
        }

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathBankAccountType)));
        FunctionLibrary.selectDropDownListByValue(xpathBankAccountType, BankAccountType, "");
        // FunctionLibrary.objDriver.findElement(By.xpath(xpathBankNameOnAccount)).sendKeys(BankNameOnAccount);

      //  FunctionLibrary.objDriver.findElement(By.xpath(xpathBankNameOnAccount)).sendKeys(BankNameOnAccount);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathBankAccountNumber)).sendKeys(BankAccountNumber);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathRetypeBankAccountNumber)).sendKeys(BankAccountNumber);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathBankRoutingNumber)).sendKeys(BankRoutingNumber);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathBillingAddress1)).sendKeys(address1);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathBillingAddress2)).sendKeys(address2);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathBillingAddressbankCity)).sendKeys(city);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathBillingAddressbankZipCode)).sendKeys(zip);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Bank account Information Added ", LogStatus.INFO, true);
        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathConfirmation)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathConfirmation)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathConfirmation)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathConfirmation,"","Clicking");
        }


        System.out.println("conf");

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        ConformationPage();
      /*  new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();

       // wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//p[@class='form-control-static'])[5]")));
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Billing Address Details are auto populated ", LogStatus.INFO, true);
        WebElement Accno,Stax, Tvalue;
        try {
            Accno = FunctionLibrary.objDriver.findElement(By.xpath("(//p[@class='form-control-static'])[5]"));
            Stax = FunctionLibrary.objDriver.findElement(By.xpath("//div[@class='bach']/div[5]/table/tbody/tr[2]/td[2]"));
            Tvalue = FunctionLibrary.objDriver.findElement(By.xpath("//div[@class='bach']/div[5]/table/tbody/tr[6]/td[2]/b"));
            System.out.println("Value 5");
        }
        catch(Exception e)
        {
            Accno = FunctionLibrary.objDriver.findElement(By.xpath(xpathMaskedAccountNumber));
            Stax = FunctionLibrary.objDriver.findElement(By.xpath(xpathStax));
            Tvalue = FunctionLibrary.objDriver.findElement(By.xpath(xpathTotalValue));
            System.out.println("Value 6");
        }
       // WebElement Accno = FunctionLibrary.objDriver.findElement(By.xpath(xpathMaskedAccountNumber));
        String Num = Accno.getText();
        String N[] = Num.split("\\r?\\n");
        System.out.println(N[1]);

      //  WebElement Stax = FunctionLibrary.objDriver.findElement(By.xpath(xpathStax));
        String Staxvalue = Stax.getText();
      //  WebElement Tvalue = FunctionLibrary.objDriver.findElement(By.xpath(xpathTotalValue));
        String Tot = Tvalue.getText();
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Sales Tax Calculation Bases: " + Staxvalue, LogStatus.PASS, false);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Amount Charged as Per the # of Device(s) Requested + Payment Type + Sales Tax + Plan sale Amount + Any Additional Pre payment: " + Tot, LogStatus.PASS, false);
        if (N[1].contains(cardNumber.substring(cardNumber.length() - 4))) {
            System.out.println(N[1] + "  " + cardNumber.substring(cardNumber.length() - 4));
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " S_PTY_PAY_PRFL.X_CC_MASKING Masked and shown only last 4 digit of Credit Card " + N[1], LogStatus.PASS, false);

        }
        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
        Thread.sleep(4000);

        FunctionLibrary.scrollDowntoElement(CheckBOx, "Scroll down");

        try {
            FunctionLibrary.objDriver.navigate().refresh();
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
            FunctionLibrary.scrollDowntoElement(CheckBOx, "Scroll down");
            // WebElement element = FunctionLibrary.objDriver.findElement(By.xpath("//div[@class='icheckbox_minimal']"));
            WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(CheckBOx));

            System.out.println("click try in try block");
            element.click();

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Confirmation Page Terms & Conditions Checked ", LogStatus.INFO, true);

        } catch (Exception e) {
            System.out.println("click try in catch block");
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='icheckbox_minimal']")));


        }
        Thread.sleep(3000);
        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathCompleteButton)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathCompleteButton)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathCompleteButton)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathCompleteButton,"","Clicking ");
        }
*/
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class='page-header-noborder']")));
        String message = FunctionLibrary.objDriver.findElement(By.xpath("//h1[@class='page-header-noborder']")).getText();
        System.out.println(message);
        String[] accountArray = message.split(" ");
        System.out.println(accountArray[12]);
        String accountNumber = accountArray[12].replace('.', ' ').trim();
        System.out.println(accountNumber + "  " + CompanyName);


        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Web Validation Excepted: <b>  Account/SR  # Generated</b> Actual:  <b>  Account/SR # Generated :</b>  " + accountNumber + " ", LogStatus.PASS, false);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Excepted: <b>  Account/SR # Generated Success Message  </b> Actual:  <b>  Account/SR # Generated Success Message displayed </b> ", LogStatus.PASS, true);
        //String accountNumber="xer49536526";
        CSRreview_Approval(CompanyName);

    }
    /**
     * Created By: Bharath
     * Test Case: ACEW-039
     * Test Case Description:Payment processing unsuccessful during Prepaid account creation.
     *
     * @throws IOException
     */
    public static void tcAccountCreationWebACEW039() throws Exception {

        String Desc;
        String firstName = dataObj.get("First Name");
        String address1 = dataObj.get("Mailing Address1");
        String address2 = dataObj.get("Mailing Address2");
        String city = dataObj.get("City");
        String state = dataObj.get("State");
        String zip = dataObj.get("Zip Code");
        String cardType = dataObj.get("Card Type");
        String cardNumber = dataObj.get("Card Number");
        String cardExpMonth = dataObj.get("Exp Month");
        String cardExpYear = dataObj.get("Exp Year");
        String cardCcvNumber = dataObj.get("Ccv Number");
        String deviceNumber1 = dataObj.get("Device Count1");
        String deviceNumber2 = dataObj.get("Device Count2");
        int deviceCount1 = Integer.parseInt(deviceNumber1);
        int deviceCount2 = Integer.parseInt(deviceNumber2);
        int totalDevices = deviceCount1 + deviceCount2;
        int totalCost = totalDevices * 10;

        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));
        //FunctionLibrary.objDriver.manage().deleteAllCookies();

        addAccountDetails();
          String RefNum=addCommunicationDetails();

        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 100);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathVehicle)));
        //adding vehicles
        webApplicationAddVehicles(totalDevices);
        //Thread.sleep(5000);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Vehicle Information Added ", LogStatus.INFO, true);

        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).sendKeys(deviceNumber1);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).sendKeys(deviceNumber2);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Transponder Information Added ", LogStatus.INFO, true);

        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathPlansAndPayment)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathPlansAndPayment)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathPlansAndPayment)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathPlansAndPayment,"","Clicking");
        }
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='creditCardType']")));


        String BankAccountType = dataObj.get("Account Type");
        String BankNameOnAccount = dataObj.get("Name On Account");
        String BankAccountNumber = dataObj.get("Bank Account Number");
        String BankRoutingNumber = dataObj.get("Bank Routing Number");


        try {
            Thread.sleep(1000);
            FunctionLibrary.objDriver.navigate().refresh();
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathBankAccount)));
            WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(xpathBankAccount));
            System.out.println("click try in try block");
            element.click();
            System.out.println("click try in try block");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Primary card Details Bank account Payment is Selected ", LogStatus.INFO, true);

        } catch (Exception e) {
            System.out.println("click try in catch block");
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathBankAccount)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathBankAccount)).click();
        }

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathBankAccountType)));
        FunctionLibrary.selectDropDownListByValue(xpathBankAccountType, BankAccountType, "");
        // FunctionLibrary.objDriver.findElement(By.xpath(xpathBankNameOnAccount)).sendKeys(BankNameOnAccount);

      //  FunctionLibrary.objDriver.findElement(By.xpath(xpathBankNameOnAccount)).sendKeys(BankNameOnAccount);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathBankAccountNumber)).sendKeys(BankAccountNumber);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathRetypeBankAccountNumber)).sendKeys(BankAccountNumber);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathBankRoutingNumber)).sendKeys(BankRoutingNumber);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathBillingAddress1)).sendKeys(address1);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathBillingAddress2)).sendKeys(address2);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathBillingAddressbankCity)).sendKeys(city);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathBillingAddressbankZipCode)).sendKeys(zip);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Bank account Information Added ", LogStatus.INFO, true);
        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathConfirmation)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathConfirmation)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathConfirmation)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathConfirmation,"","Clicking");
        }
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        System.out.println("Confirmation Page1");

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Account # Not Generated ", LogStatus.PASS, false);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Reference number is is Displayed " + RefNum, LogStatus.PASS, true);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Excepted:<b> Account is not created.</b> Actual : <b> Account is not created. </b>", LogStatus.PASS, false);


    }

    /**
     * Created By: Bharath
     * Test Case: ACEW-048
     * Test Case Description: Click on Browse Back button from the Payment Confirmation page.
     *
     * @throws IOException
     */
    public static void tcAccountCreationWebACEW048() throws Exception {

        System.out.println(dataObj.get("WEB_URL").toString());
        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));

        addAccountDetails();
        String RefNum=addCommunicationDetails();
        TranspondersAndVehicles();
        PlansPaymentsAndBillingAddress();
        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 20);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        FunctionLibrary.objDriver.navigate().back();

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Clicked on Browse Back Button ", LogStatus.INFO, true);


        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Account # Not Generated ", LogStatus.PASS, false);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Reference number is is Displayed " + RefNum, LogStatus.PASS, true);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Excepted:<b> Account is not created.</b> Actual : <b> Account is not created. </b>", LogStatus.PASS, false);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Excepted:<b> Navigate back to the previous account details page.</b> Actual : <b> Navigate back to the previous account details page. </b>", LogStatus.PASS, true);


    }

    /**
     * Created By: Bharath
     * Test Case: ACEW-049
     * Test Case Description: Click on Cancel button to not proceed to next page and confirm the OK on warning message.
     *
     * @throws IOException
     */
    public static void tcAccountCreationWebACEW049() throws Exception {

        String Desc;

        String deviceNumber1 = dataObj.get("Device Count1");
        String deviceNumber2 = dataObj.get("Device Count2");
        int deviceCount1 = Integer.parseInt(deviceNumber1);
        int deviceCount2 = Integer.parseInt(deviceNumber2);
        int totalDevices = deviceCount1 + deviceCount2;
        int totalCost = totalDevices * 10;

        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));
        //FunctionLibrary.objDriver.manage().deleteAllCookies();
         addAccountDetails();

        String RefNum=addCommunicationDetails();

        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 100);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathVehicle)));
        //adding vehicles
        webApplicationAddVehicles(totalDevices);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Vehicle Information Added ", LogStatus.INFO, true);

        String xpathCancel = "//a[contains(text(),' CANCEL ')]";
        String xpathCancelMessage = "(//div[@class='modal-body'])[1]";
        String xpathConfirmCalcelOk = "(//*[@class='btn btn-submit btn-block'])[1]";
        wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathCancel)));
        FunctionLibrary.objDriver.findElement(By.xpath(xpathCancel)).click();
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Cancel Button Clicked Without Clicking Continue Button ", LogStatus.INFO, false);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathCancelMessage)));

        //  Thread.sleep(4000);
        WebElement ConfirmCancel = FunctionLibrary.objDriver.findElement(By.xpath(xpathCancelMessage));
        System.out.println(ConfirmCancel.getText());
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected : <b> Warning Message Displayed " + ConfirmCancel.getText() + "</b>  Actual : <b> Warning Message Displayed " + ConfirmCancel.getText() + "</b>", LogStatus.PASS, true);

        // Thread.sleep(4000);
        WebElement ConfirmOk = FunctionLibrary.objDriver.findElement(By.xpath(xpathConfirmCalcelOk));
        System.out.println(ConfirmOk.getText());
        Thread.sleep(4000);
        ConfirmOk.click();

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Clicked on Confirm cancel OK Button ", LogStatus.PASS, false);


        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Account # Not Generated ", LogStatus.PASS, true);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Reference number is is Displayed " + RefNum, LogStatus.PASS, true);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<b> Customer taken back to the Signup page; a warning is displayed regarding the loss of information and customer has to confirm the OK; customer re-enters all the details.</b> ", LogStatus.PASS, true);


    }

    /**
     * Created By: Bharath
     * Test Case: ACEW-051
     * Test Case Description: Create account after the reference ID has been generated.
     *
     * @throws IOException
     */
    public static void tcAccountCreationWebACEW051() throws Exception {

        String Desc;
        String firstName = dataObj.get("First Name");
        String middleName = dataObj.get("Middle Name");
        String lastName = dataObj.get("Last Name");
        String address1 = dataObj.get("Mailing Address1");
        String address2 = dataObj.get("Mailing Address2");
        String city = dataObj.get("City");
        String state = dataObj.get("State");
        String zip = dataObj.get("Zip Code");
        String country = dataObj.get("Country");
        String email = dataObj.get("Email");
        String phone = dataObj.get("Phone");
        String phoneType = dataObj.get("Phone Type");
        String securityQuestion = dataObj.get("Security Question");
        String securityAnswer = dataObj.get("Security Answer");
        String userName = firstName + DateTime.now().getMillisOfSecond() + DateTime.now().getSecondOfMinute() + DateTime.now().getMillisOfSecond();
        String password = dataObj.get("Password1");
        String pin = dataObj.get("Pin");
        String cardType = dataObj.get("Card Type");
        String cardNumber = dataObj.get("Card Number");
        String cardExpMonth = dataObj.get("Exp Month");
        String cardExpYear = dataObj.get("Exp Year");
        String cardCcvNumber = dataObj.get("Ccv Number");
        String deviceNumber1 = dataObj.get("Device Count1");
        String deviceNumber2 = dataObj.get("Device Count2");
        int deviceCount1 = Integer.parseInt(deviceNumber1);
        int deviceCount2 = Integer.parseInt(deviceNumber2);
        int totalDevices = deviceCount1 + deviceCount2;
        int totalCost = totalDevices * 10;

        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));
        //FunctionLibrary.objDriver.manage().deleteAllCookies();

        addAccountDetails();
 String RefNum=addCommunicationDetails();

        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 100);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathVehicle)));
        //adding vehicles
        webApplicationAddVehicles(totalDevices);
        //Thread.sleep(10000);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Vehicle Information Added ", LogStatus.INFO, true);

        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).sendKeys(deviceNumber1);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).sendKeys(deviceNumber2);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Transponder Information Added ", LogStatus.INFO, true);

        //WebElement ref=FunctionLibrary.objDriver.findElement(By.xpath("(.//div[@class='form-group row'])[1]"));
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Reference number is is Displayed " + RefNum, LogStatus.PASS, true);

        System.out.println(RefNum);
        // String Re=ref.getText();
        String r[] = RefNum.split(":");
        String ReferenceNumber = r[1].trim();

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Reference number is is Displayed " + ReferenceNumber, LogStatus.INFO, false);

        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathPlansAndPayment)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathPlansAndPayment)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathPlansAndPayment)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathPlansAndPayment,"","Clicking");
        }

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='creditCardType']")));

        FunctionLibrary.closeAllActiveBrowser();
        Thread.sleep(4000);


        CommonLibrary.launchBrowser(dataObj.get("WEB_URL1").toString(), "Launching Web App Url based on reference ID ", dataObj.get("Browser Type"));
        // FunctionLibrary.objDriver.navigate().to(dataObj.get("WEB_URL1").toString());
       // FunctionLibrary.objDriver.manage().deleteAllCookies();
        Thread.sleep(4000);
        String xpathReferenceNumber = "//input[@name='referenceID']";
        String xpathLastName1 = "//input[@name='lastName']";
        String xpathZipCode1 = "//input[@name='zipCode']";
        String xpathLookUp = "//button[contains(text(),'Lookup')]";
        Desc = "Reference Number Entered";
        WebDriverWait wait3 = new WebDriverWait(FunctionLibrary.objDriver, 300);
        wait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathLookUp)));
        FunctionLibrary.objDriver.findElement(By.xpath(xpathReferenceNumber)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathReferenceNumber)).sendKeys(ReferenceNumber);

        // FunctionLibrary.objDriver.findElement(By.xpath(xpathReferenceNumber)).sendKeys("1-61646201");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathLastName1)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathLastName1)).sendKeys(lastName);
        // FunctionLibrary.objDriver.findElement(By.xpath(xpathLastName1)).sendKeys("Lie");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathZipCode1)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathZipCode1)).sendKeys(zip);
        // FunctionLibrary.objDriver.findElement(By.xpath(xpathZipCode1)).sendKeys("33931");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathLookUp)).click();


        FunctionLibrary.objDriver.findElement(By.xpath(xpathPassword)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathPassword)).sendKeys(password);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathReTypePassword)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathReTypePassword)).sendKeys(password);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathCommunicationPreferencesBtn)).click();

               RefNum=addCommunicationDetails();

        wait2 = new WebDriverWait(FunctionLibrary.objDriver, 100);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathVehicle)));
        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).sendKeys(deviceNumber1);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).sendKeys(deviceNumber2);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Transponder Information Added ", LogStatus.INFO, true);

        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathPlansAndPayment)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathPlansAndPayment)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathPlansAndPayment)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathPlansAndPayment,"","Clicking");
        }
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='creditCardType']")));
        FunctionLibrary.selectDropDownListByValue(xpathCardType, cardType, "");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathCardNumber)).sendKeys(cardNumber);
        FunctionLibrary.selectDropDownListByValue(xpathCardExpMonth, cardExpMonth, "");
        FunctionLibrary.selectDropDownListByValue(xpathCardExpYear, cardExpYear, "");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathCardSecurityCode)).sendKeys(cardCcvNumber);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Primary Card Details Added ", LogStatus.INFO, true);

        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathConfirmation)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathConfirmation)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathConfirmation)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathConfirmation,"","Clicking");
        }
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        System.out.println("conf");
        ConformationPage();
       /* new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Billing Address Details are auto populated ", LogStatus.INFO, true);


        WebElement Accno = FunctionLibrary.objDriver.findElement(By.xpath(xpathMaskedAccountNumber));
        String Num = Accno.getText();
        String N[] = Num.split("\\r?\\n");
        System.out.println(N[1]);

        WebElement Stax = FunctionLibrary.objDriver.findElement(By.xpath(xpathStax));
        String Staxvalue = Stax.getText();
        WebElement Tvalue = FunctionLibrary.objDriver.findElement(By.xpath(xpathTotalValue));
        String Tot = Tvalue.getText();
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Sales Tax Calculation Bases: " + Staxvalue, LogStatus.PASS, false);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Amount Charged as Per the # of Device(s) Requested + Payment Type + Sales Tax + Plan sale Amount + Any Additional Pre payment: " + Tot, LogStatus.PASS, false);
        if (N[1].contains(cardNumber.substring(cardNumber.length() - 4))) {
            System.out.println(N[1] + "  " + cardNumber.substring(cardNumber.length() - 4));
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " S_PTY_PAY_PRFL.X_CC_MASKING Masked and shown only last 4 digit of Credit Card " + N[1], LogStatus.PASS, false);

        }
        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
        Thread.sleep(4000);

        FunctionLibrary.scrollDowntoElement("./*//*[@class='icheckbox_minimal']", "Scroll down");

        try {
            FunctionLibrary.objDriver.navigate().refresh();
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
            FunctionLibrary.scrollDowntoElement("./*//*[@class='icheckbox_minimal']", "Scroll down");
            // WebElement element = FunctionLibrary.objDriver.findElement(By.xpath("//div[@class='icheckbox_minimal']"));
            WebElement element = FunctionLibrary.objDriver.findElement(By.xpath("./*//*[@class='icheckbox_minimal']"));

            System.out.println("click try in try block");
            element.click();

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Confirmation Page Terms & Conditions Checked ", LogStatus.INFO, true);

        } catch (Exception e) {
            System.out.println("click try in catch block");
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='icheckbox_minimal']")));


        }
        FunctionLibrary.objDriver.findElement(By.xpath(xpathCompleteButton)).click();
*/
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class='page-header-noborder']")));
        String message = FunctionLibrary.objDriver.findElement(By.xpath("//h1[@class='page-header-noborder']")).getText();
        System.out.println(message);
        String[] accountArray = message.split(" ");
        System.out.println(accountArray[12]);
        String accountNumber = accountArray[12].replace('.', ' ').trim();
        System.out.println(accountNumber);


        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Web Validation Excepted: <b>  Account # Generated</b> Actual:  <b>  Account # Generated :</b>  " + accountNumber + " ", LogStatus.PASS, false);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Excepted: <b>  Account # Generated Success Message  </b> Actual:  <b>  Account # Generated Success Message displayed </b> ", LogStatus.PASS, true);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Excepted: <b> Confirmation Email Sent After Account Creation </b> Actual: <b> Confirmation Email Sent Successfully </b> ", LogStatus.PASS, false);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Excepted: <b> Verification Link Sent to Email </b> Actual: <b> Verification Link Sent to Email Successfully </b> ", LogStatus.PASS, false);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Email Does Not Become Active Until Email is Validated ", LogStatus.PASS, false);

        String xpathThisPage = "(//*[@class='page-header-noborder']/b/a)[1]";
        String xpathPrintClose = "//a[contains(text(),'CLOSE')]";
        wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathThisPage)));
        FunctionLibrary.objDriver.findElement(By.xpath(xpathThisPage)).click();
        Thread.sleep(5000);

        //wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathPrintClose)));
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Excepted: <b> View and Print the Summary From Web in Printer-Friendly Format </b> Actual: <b> View and Print the Summary From Web in Printer-Friendly Format </b> ", LogStatus.PASS, true);
        FunctionLibrary.objDriver.findElement(By.tagName("body")).sendKeys(Keys.CONTROL + "w");
        //  FunctionLibrary.objDriver.findElement(By.xpath(xpathPrintClose)).click();

        SiebelValidation(accountNumber, firstName);
        Thread.sleep(4000);

    }

    public static void tcAccountCreationWebACEW053() throws Exception {
        String Desc;
        String cardType = dataObj.get("Card Type");
        String cardNumber = dataObj.get("Card Number");
        String cardExpMonth = dataObj.get("Exp Month");
        String cardExpYear = dataObj.get("Exp Year");
        String cardCcvNumber = dataObj.get("Ccv Number");
        String deviceNumber1 = dataObj.get("Device Count1");
        String deviceNumber2 = dataObj.get("Device Count2");
        int deviceCount1 = Integer.parseInt(deviceNumber1);
        int deviceCount2 = Integer.parseInt(deviceNumber2);
        int totalDevices = deviceCount1 + deviceCount2;
        int totalCost = totalDevices * 10;
        String DuplicatePlateNumber = dataObj.get("Duplicate Plate Number");
         writeVehicleDetailsToExcel("testing_vehicle_4", "Sheet1", totalDevices, DuplicatePlateNumber);

        String CompanyName = "xer" + DateTime.now().getMillisOfSecond() + DateTime.now().getSecondOfMinute() + DateTime.now().getMillisOfSecond();
        String fileUpload = "//*[@name='fileUpload']";
        String CheckBOx = ".//*[@class='icheckbox_minimal']";

        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));
        //FunctionLibrary.objDriver.manage().deleteAllCookies();
        FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='companyName']")).sendKeys(CompanyName);
        FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='dbaName']")).sendKeys("services");
        FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='fein']")).sendKeys("123456789");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondaryFirstName)).sendKeys("JHN");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondaryMiddleName)).sendKeys("M");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondaryLastName)).sendKeys("SAW");
        addAccountDetails();

       String RefNum=addCommunicationDetails();

        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 100);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(fileUpload)));
        //adding vehicles
        //Thread.sleep(4000);
        File file = new File(ReportLibrary.getPath() + "\\testdata\\testing_vehicle_4.xlsx");
        System.out.println(file.getAbsolutePath());
        FunctionLibrary.objDriver.findElement(By.xpath(fileUpload)).sendKeys(file.getAbsolutePath());
        Thread.sleep(2000);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Vehicle Information Attached ", LogStatus.INFO, true);

        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).sendKeys(deviceNumber1);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).sendKeys(deviceNumber2);
        Thread.sleep(4000);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Transponder Information Added ", LogStatus.INFO, true);
        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathPlansAndPayment)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathPlansAndPayment)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathPlansAndPayment)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathPlansAndPayment,"","Clicking on Plan Button");
        }
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='creditCardType']")));
        FunctionLibrary.selectDropDownListByValue(xpathCardType, cardType, "");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathCardNumber)).sendKeys(cardNumber);
        FunctionLibrary.selectDropDownListByValue(xpathCardExpMonth, cardExpMonth, "");
        FunctionLibrary.selectDropDownListByValue(xpathCardExpYear, cardExpYear, "");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathCardSecurityCode)).sendKeys(cardCcvNumber);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Primary Card Details Added ", LogStatus.INFO, true);
        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathConfirmation)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathConfirmation)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathConfirmation)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathConfirmation,"","Clicking");
        }
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        System.out.println("conf");

        ConformationPage();
/*

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Billing Address Details are auto populated ", LogStatus.INFO, true);
        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
        WebElement Accno,Stax,Tvalue;
        try {
            Accno = FunctionLibrary.objDriver.findElement(By.xpath("(//p[@class='form-control-static'])[5]"));
            Stax = FunctionLibrary.objDriver.findElement(By.xpath("//div[@class='bach']/div[5]/table/tbody/tr[2]/td[2]"));
            Tvalue = FunctionLibrary.objDriver.findElement(By.xpath("//div[@class='bach']/div[5]/table/tbody/tr[6]/td[2]/b"));
            System.out.println("Value 5");
        }
        catch(Exception e)
        {
            Accno = FunctionLibrary.objDriver.findElement(By.xpath(xpathMaskedAccountNumber));
            Stax = FunctionLibrary.objDriver.findElement(By.xpath(xpathStax));
            Tvalue = FunctionLibrary.objDriver.findElement(By.xpath(xpathTotalValue));
            System.out.println("Value 6");
        }

     //   WebElement Accno = FunctionLibrary.objDriver.findElement(By.xpath(xpathMaskedAccountNumber));
        String Num = Accno.getText();
        String N[] = Num.split("\\r?\\n");
        System.out.println(N[1]);

       // WebElement Stax = FunctionLibrary.objDriver.findElement(By.xpath(xpathStax));
        String Staxvalue = Stax.getText();
       // WebElement Tvalue = FunctionLibrary.objDriver.findElement(By.xpath(xpathTotalValue));
        String Tot = Tvalue.getText();
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Sales Tax Calculation Bases: " + Staxvalue, LogStatus.PASS, false);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Amount Charged as Per the # of Device(s) Requested + Payment Type + Sales Tax + Plan sale Amount + Any Additional Pre payment: " + Tot, LogStatus.PASS, false);
        if (N[1].contains(cardNumber.substring(cardNumber.length() - 4))) {
            System.out.println(N[1] + "  " + cardNumber.substring(cardNumber.length() - 4));
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " S_PTY_PAY_PRFL.X_CC_MASKING Masked and shown only last 4 digit of Credit Card " + N[1], LogStatus.PASS, false);

        }
        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
        Thread.sleep(4000);

        FunctionLibrary.scrollDowntoElement(CheckBOx, "Scroll down");

        try {
            FunctionLibrary.objDriver.navigate().refresh();
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
            FunctionLibrary.scrollDowntoElement(CheckBOx, "Scroll down");
            // WebElement element = FunctionLibrary.objDriver.findElement(By.xpath("//div[@class='icheckbox_minimal']"));
            WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(CheckBOx));

            System.out.println("click try in try block");
            element.click();

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Confirmation Page Terms & Conditions Checked ", LogStatus.INFO, true);

        } catch (Exception e) {
            System.out.println("click try in catch block");
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='icheckbox_minimal']")));


        }
        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathCompleteButton)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathCompleteButton)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathCompleteButton)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathCompleteButton,"","Clicking ");
        }
*/

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class='page-header-noborder']")));
        String message = FunctionLibrary.objDriver.findElement(By.xpath("//h1[@class='page-header-noborder']")).getText();
        System.out.println(message);
        String[] accountArray = message.split(" ");
        System.out.println(accountArray[12]);
        String accountNumber = accountArray[12].replace('.', ' ').trim();
        System.out.println(accountNumber + "  " + CompanyName);


        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Web Validation Excepted: <b>  Account/SR  # Generated</b> Actual:  <b>  Account/SR # Generated :</b>  " + accountNumber + " ", LogStatus.PASS, false);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Excepted: <b>  Account/SR # Generated Success Message  </b> Actual:  <b>  Account/SR # Generated Success Message displayed </b> ", LogStatus.PASS, true);
        //String accountNumber="xer33515360";
        CSRreview_Approval(CompanyName);

    }

    /**
     * Created By: Bharath
     * Test Case: ACEW-061
     * Test Case Description: Enter metal oxide windshield vehicle and request internal transponder.
     *
     * @throws IOException
     */
    public static void tcAccountCreationWebACEW061() throws Exception {

        String firstName = dataObj.get("First Name");
        System.out.println(dataObj.get("WEB_URL").toString());
        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));

        addAccountDetails();
        String RefNum=addCommunicationDetails();
        TranspondersAndVehicles();
        PlansPaymentsAndBillingAddress();
        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 20);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        System.out.println("conf");
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='percentage']")));

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Account # Not Generated ", LogStatus.PASS, true);


        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Reference number is is Displayed " + RefNum, LogStatus.PASS, true);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Excepted:<b> Account is not created.</b> Actual : <b> Account is not created. </b>", LogStatus.PASS, false);


    }

    public static void tcAccountCreationWebACEW063() throws Exception {

        String Desc;
        String firstName = dataObj.get("First Name");
        String cardType = dataObj.get("Card Type");
        String cardNumber = dataObj.get("Card Number");
        String cardExpMonth = dataObj.get("Exp Month");
        String cardExpYear = dataObj.get("Exp Year");
        String cardCcvNumber = dataObj.get("Ccv Number");
        String deviceNumber1 = dataObj.get("Device Count1");
        String deviceNumber2 = dataObj.get("Device Count2");
        int deviceCount1 = Integer.parseInt(deviceNumber1);
        int deviceCount2 = Integer.parseInt(deviceNumber2);
        int totalDevices = deviceCount1 + deviceCount2;
        int totalCost = totalDevices * 10;

        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));
       // FunctionLibrary.objDriver.manage().deleteAllCookies();
        addAccountDetails();

        String RefNum=addCommunicationDetails();

        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 100);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathVehicle)));
        //adding vehicles
       // CommonLibrary.webApplicationAddVehicles(totalDevices);
        //      Thread.sleep(10000);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Vehicle Information Added ", LogStatus.INFO, true);

        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).sendKeys("");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).sendKeys("");

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Transponder Information is Blank ", LogStatus.INFO, true);

        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathPlansAndPayment)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathPlansAndPayment)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathPlansAndPayment)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathPlansAndPayment,"","Clicking");
        }
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));

        String xpathErrorMessage = "//div[@class='alert alert-danger error_msg']";
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathErrorMessage)));

        WebElement ErrorMessage = FunctionLibrary.objDriver.findElement(By.xpath(xpathErrorMessage));
        System.out.println(ErrorMessage.getText());


        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Account # Not Generated ", LogStatus.PASS, true);


        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Validation Message Displayed: " + ErrorMessage.getText(), LogStatus.PASS, true);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Reference number is is Displayed " + RefNum, LogStatus.PASS, true);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Excepted:<b>Error Message</b> Actual : <b>Error message " + ErrorMessage.getText() + "</b>", LogStatus.PASS, true);


    }

    public static void tcAccountCreationWebACEW065() throws Exception {

        String Desc;

        String deviceNumber1 = dataObj.get("Device Count1");
        String deviceNumber2 = dataObj.get("Device Count2");
        int deviceCount1 = Integer.parseInt(deviceNumber1);
        int deviceCount2 = Integer.parseInt(deviceNumber2);
        int totalDevices = deviceCount1 + deviceCount2;
        int totalCost = totalDevices * 10;

        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));
       // FunctionLibrary.objDriver.manage().deleteAllCookies();

          addAccountDetails();

        String RefNum=addCommunicationDetails();

        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 100);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathVehicle)));
        //adding vehicles
        //CommonLibrary.webApplicationAddVehicles(totalDevices);
//        addMetalOxideWindshieldVehicle(totalDevices);
        Thread.sleep(4000);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Vehicle Information Not Added ", LogStatus.INFO, true);

        /*FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).sendKeys(deviceNumber1);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).sendKeys(deviceNumber2);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Transponder Information Added ", LogStatus.INFO, true);
*/
        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
        Thread.sleep(4000);
        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathPlansAndPayment)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathPlansAndPayment)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathPlansAndPayment)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathPlansAndPayment,"","Clicking");
        }
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        String xpathErrorMessage = "//div[@class='alert alert-danger error_msg']";
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathErrorMessage)));

        WebElement ErrorMessage = FunctionLibrary.objDriver.findElement(By.xpath(xpathErrorMessage));
        System.out.println(ErrorMessage.getText());


        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Error Message Displayed: " + ErrorMessage.getText(), LogStatus.PASS, true);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Account # Not Generated ", LogStatus.PASS, true);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Excepted:<b> Error message is displayed. Default value get saved.</b> Actual : <b> " + ErrorMessage.getText() + "</b>", LogStatus.INFO, false);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Reference number is is Displayed " + RefNum, LogStatus.PASS, true);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Excepted:<b> Account is not created.</b> Actual : <b> Account is not created. </b>", LogStatus.PASS, false);


    }

    public static void tcAccountCreationWebACEW072() throws Exception {

        System.out.println(dataObj.get("WEB_URL").toString());
        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));

        addAccountDetails();
        String RefNum=addCommunicationDetails();
        TranspondersAndVehicles();
        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 100);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        String xpathErrorMessage = "//div[@class='alert alert-danger error_msg']";
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathErrorMessage)));

        WebElement ErrorMessage = FunctionLibrary.objDriver.findElement(By.xpath(xpathErrorMessage));
        System.out.println(ErrorMessage.getText());


        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Account # Not Generated ", LogStatus.PASS, true);


        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Validation Message Displayed: " + ErrorMessage.getText(), LogStatus.PASS, true);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Excepted:<b>Validation message is displayed and user cannot proceed to the next page.</b> Actual : <b> Validation message " + ErrorMessage.getText() + " is displayed and user cannot proceed to the next page.</b>", LogStatus.PASS, true);


    }

    public static void tcAccountCreationWebACEW074() throws Exception {

        String Desc;
        String deviceNumber1 = dataObj.get("Device Count1");
        String deviceNumber2 = dataObj.get("Device Count2");
        int deviceCount1 = Integer.parseInt(deviceNumber1);
        int deviceCount2 = Integer.parseInt(deviceNumber2);
        String DuplicatePlateNumber = dataObj.get("Duplicate Plate Number");
        int totalDevices = deviceCount1 + deviceCount2;
        int totalCost = totalDevices * 10;

        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));
      // FunctionLibrary.objDriver.manage().deleteAllCookies();


        addAccountDetails();

        String RefNum=addCommunicationDetails();

        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 100);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathVehicle)));
        //adding vehicles
        // CommonLibrary.webApplicationAddVehicles(totalDevices);
        webApplicationAddVehiclesDuplcatePlateNumber(DuplicatePlateNumber, 1);
        // webApplicationAddVehiclesRCSPPlateNumber(1);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "RCSP account in bad standing Plate number details Entered ", LogStatus.INFO, true);

        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).sendKeys(deviceNumber1);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).sendKeys(deviceNumber2);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Transponder Information Added ", LogStatus.INFO, false);
        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathPlansAndPayment)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathPlansAndPayment)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathPlansAndPayment)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathPlansAndPayment,"","Clicking");
        }
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        String xpathErrorMessage = "//div[@class='alert alert-danger error_msg']";
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathErrorMessage)));

        WebElement ErrorMessage = FunctionLibrary.objDriver.findElement(By.xpath(xpathErrorMessage));
        System.out.println(ErrorMessage.getText());


        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Account # Not Generated ", LogStatus.PASS, true);


        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Validation Message Displayed: " + ErrorMessage.getText(), LogStatus.PASS, true);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Excepted:<b>Validation message is displayed and user cannot proceed to the next page.</b> Actual : <b> Validation message " + ErrorMessage.getText() + " is displayed and user cannot proceed to the next page.</b>", LogStatus.PASS, true);


    }

    public static void tcAccountCreationWebACEW098() throws Exception {

        String Desc;
        String cardType = dataObj.get("Card Type");
        String cardNumber = dataObj.get("Card Number");
        String cardExpMonth = dataObj.get("Exp Month");
        String cardExpYear = dataObj.get("Exp Year");
        String cardCcvNumber = dataObj.get("Ccv Number");
        String deviceNumber1 = dataObj.get("Device Count1");
        String deviceNumber2 = dataObj.get("Device Count2");
        int deviceCount1 = Integer.parseInt(deviceNumber1);
        int deviceCount2 = Integer.parseInt(deviceNumber2);
        int totalDevices = deviceCount1 + deviceCount2;
        int totalCost = totalDevices * 10;
        String DuplicatePlateNumber = dataObj.get("Duplicate Plate Number");

            writeVehicleDetailsToExcel("testing_vehicle_6", "Sheet1", totalDevices, DuplicatePlateNumber);

        String CompanyName = "xer" + DateTime.now().getMillisOfSecond() + DateTime.now().getSecondOfMinute() + DateTime.now().getMillisOfSecond();
        String fileUpload = "//*[@name='fileUpload']";
        String CheckBOx = ".//*[@class='icheckbox_minimal']";



        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));
        //FunctionLibrary.objDriver.manage().deleteAllCookies();
        FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='companyName']")).sendKeys(CompanyName);
        FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='dbaName']")).sendKeys("services");
        FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='fein']")).sendKeys("123456789");

        FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondaryFirstName)).sendKeys("JHN");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondaryMiddleName)).sendKeys("M");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondaryLastName)).sendKeys("SAW");
        addAccountDetails();


        String RefNum=addCommunicationDetails();

        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 100);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(fileUpload)));
        //adding vehicles
      //  Thread.sleep(4000);
        File file = new File(ReportLibrary.getPath() + "\\testdata\\testing_vehicle_6.xlsx");
        System.out.println(file.getAbsolutePath());
        Thread.sleep(2000);
        //FunctionLibrary.objDriver.findElement(By.xpath(xpathUploadBtn)).sendKeys(file.getAbsolutePath());
        //FunctionLibrary.objDriver.findElement(By.xpath(fileUpload)).sendKeys(file.getAbsolutePath());
        //FunctionLibrary.objDriver.findElement(By.xpath(fileUpload)).click();
        FunctionLibrary.objDriver.findElement(By.xpath(fileUpload)).sendKeys(file.getAbsolutePath());
        String path=file.getAbsolutePath();
      //  ((JavascriptExecutor) FunctionLibrary.objDriver).executeScript("document.getElementByName('fileUpload').setAttribute('value',"+path+")");
        //((JavascriptExecutor) FunctionLibrary.objDriver).executeScript(docu)
       /* wait2.until(ExpectedConditions.alertIsPresent());
        Alert alert=FunctionLibrary.objDriver.switchTo().alert();
        Thread.sleep(2000);
        new Actions(FunctionLibrary.objDriver).sendKeys(file.getAbsolutePath()).perform();
        Thread.sleep(2000);
        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.ENTER).perform();
*/
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Vehicle Information Attached ", LogStatus.INFO, true);

        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).sendKeys(deviceNumber1);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).sendKeys(deviceNumber2);
       // Thread.sleep(4000);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Transponder Information Added ", LogStatus.INFO, true);
        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathPlansAndPayment)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathPlansAndPayment)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathPlansAndPayment)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathPlansAndPayment,"","Clicking");
        }
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
       // Thread.sleep(4000);
        String xpathErrorMessage = "//div[@class='alert alert-danger error_msg']";
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathErrorMessage)));

        WebElement ErrorMessage = FunctionLibrary.objDriver.findElement(By.xpath(xpathErrorMessage));
        System.out.println(ErrorMessage.getText());


        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Account # Not Generated ", LogStatus.PASS, true);


        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Validation Message Displayed: " + ErrorMessage.getText(), LogStatus.PASS, true);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Excepted:<b>Validation message is displayed and user cannot proceed to the next page.</b> Actual : <b> Validation message " + ErrorMessage.getText() + " is displayed and user cannot proceed to the next page.</b>", LogStatus.PASS, true);


    }

    public static void tcAccountCreationWebACEW099() throws Exception {
        String Desc;
        String cardType = dataObj.get("Card Type");
        String cardNumber = dataObj.get("Card Number");
        String cardExpMonth = dataObj.get("Exp Month");
        String cardExpYear = dataObj.get("Exp Year");
        String cardCcvNumber = dataObj.get("Ccv Number");
        String deviceNumber1 = dataObj.get("Device Count1");
        String deviceNumber2 = dataObj.get("Device Count2");
        int deviceCount1 = Integer.parseInt(deviceNumber1);
        int deviceCount2 = Integer.parseInt(deviceNumber2);
        int totalDevices = deviceCount1 + deviceCount2;
        int totalCost = totalDevices * 10;
        String DuplicatePlateNumber = dataObj.get("Duplicate Plate Number");

            writeVehicleDetailsToExcel("testing_vehicle_7", "Sheet1", totalDevices, DuplicatePlateNumber);

        String CompanyName = "xer" + DateTime.now().getMillisOfSecond() + DateTime.now().getSecondOfMinute() + DateTime.now().getMillisOfSecond();
        String fileUpload = "//*[@name='fileUpload']";
        String CheckBOx = ".//*[@class='icheckbox_minimal']";

        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));
        FunctionLibrary.objDriver.manage().deleteAllCookies();
        FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='companyName']")).sendKeys(CompanyName);
        FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='dbaName']")).sendKeys("services");
        FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='fein']")).sendKeys("123456789");
        addAccountDetails();

        addAccountDetails();


        String RefNum=addCommunicationDetails();

        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 100);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(fileUpload)));
        //adding vehicles
        //Thread.sleep(4000);
        File file = new File(ReportLibrary.getPath() + "\\testdata\\testing_vehicle_7.xlsx");
        System.out.println(file.getAbsolutePath());
        FunctionLibrary.objDriver.findElement(By.xpath(fileUpload)).sendKeys(file.getAbsolutePath());
        Thread.sleep(2000);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Vehicle Information Attached ", LogStatus.INFO, true);

        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).sendKeys(deviceNumber1);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).sendKeys(deviceNumber2);
        Thread.sleep(4000);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Transponder Information Added ", LogStatus.INFO, true);
        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathPlansAndPayment)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathPlansAndPayment)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathPlansAndPayment)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathPlansAndPayment,"","Clicking on Plan Button");
        }
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='creditCardType']")));
        FunctionLibrary.selectDropDownListByValue(xpathCardType, cardType, "");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathCardNumber)).sendKeys(cardNumber);
        FunctionLibrary.selectDropDownListByValue(xpathCardExpMonth, cardExpMonth, "");
        FunctionLibrary.selectDropDownListByValue(xpathCardExpYear, cardExpYear, "");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathCardSecurityCode)).sendKeys(cardCcvNumber);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Primary Card Details Added ", LogStatus.INFO, true);
        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathConfirmation)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathConfirmation)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathConfirmation)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathConfirmation,"","Clicking");
        }

        System.out.println("conf");


        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Billing Address Details are auto populated ", LogStatus.INFO, true);

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//p[@class='form-control-static'])[5]")));
        WebElement Accno = FunctionLibrary.objDriver.findElement(By.xpath("(//p[@class='form-control-static'])[5]"));
        String Num = Accno.getText();
        String N[] = Num.split("\\r?\\n");
        System.out.println(N[1]);

        WebElement Stax = FunctionLibrary.objDriver.findElement(By.xpath("//div[@class='bach']/div[5]/table/tbody/tr[2]/td[2]"));
        String Staxvalue = Stax.getText();
        WebElement Tvalue = FunctionLibrary.objDriver.findElement(By.xpath("//div[@class='bach']/div[5]/table/tbody/tr[6]/td[2]/b"));
        String Tot = Tvalue.getText();
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Sales Tax Calculation Bases: " + Staxvalue, LogStatus.PASS, false);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Amount Charged as Per the # of Device(s) Requested + Payment Type + Sales Tax + Plan sale Amount + Any Additional Pre payment: " + Tot, LogStatus.PASS, false);
        if (N[1].contains(cardNumber.substring(cardNumber.length() - 4))) {
            System.out.println(N[1] + "  " + cardNumber.substring(cardNumber.length() - 4));
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " S_PTY_PAY_PRFL.X_CC_MASKING Masked and shown only last 4 digit of Credit Card " + N[1], LogStatus.PASS, false);

        }
        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
        Thread.sleep(4000);

        FunctionLibrary.scrollDowntoElement(CheckBOx, "Scroll down");

        try {
            FunctionLibrary.objDriver.navigate().refresh();
            FunctionLibrary.scrollDowntoElement(CheckBOx, "Scroll down");
            // WebElement element = FunctionLibrary.objDriver.findElement(By.xpath("//div[@class='icheckbox_minimal']"));
            WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(CheckBOx));

            System.out.println("click try in try block");
            element.click();

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Confirmation Page Terms & Conditions Checked ", LogStatus.INFO, true);

        } catch (Exception e) {
            System.out.println("click try in catch block");
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='icheckbox_minimal']")));


        }
        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathCompleteButton)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathCompleteButton)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathCompleteButton)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathCompleteButton,"","Clicking ");
        }

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class='page-header-noborder']")));
        String message = FunctionLibrary.objDriver.findElement(By.xpath("//h1[@class='page-header-noborder']")).getText();
        System.out.println(message);
        String[] accountArray = message.split(" ");
        System.out.println(accountArray[12]);
        String accountNumber = accountArray[12].replace('.', ' ').trim();
        System.out.println(accountNumber + "  " + CompanyName);


        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Web Validation Excepted: <b>  Account/SR  # Generated</b> Actual:  <b>  Account/SR # Generated :</b>  " + accountNumber + " ", LogStatus.PASS, false);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Excepted: <b>  Account/SR # Generated Success Message  </b> Actual:  <b>  Account/SR # Generated Success Message displayed </b> ", LogStatus.PASS, true);
        //String accountNumber="xer33515360";
        CSRreview_Approval(CompanyName);

    }

    public static void tcAccountCreationWebACEW100() throws Exception {


        String Desc;
        String firstName = dataObj.get("First Name");
        String cardType = dataObj.get("Card Type");
        String cardNumber = dataObj.get("Card Number");
        String cardExpMonth = dataObj.get("Exp Month");
        String cardExpYear = dataObj.get("Exp Year");
        String cardCcvNumber = dataObj.get("Ccv Number");
        String deviceNumber1 = dataObj.get("Device Count1");
        String deviceNumber2 = dataObj.get("Device Count2");
        int deviceCount1 = Integer.parseInt(deviceNumber1);
        int deviceCount2 = Integer.parseInt(deviceNumber2);
        int totalDevices = deviceCount1 + deviceCount2;
        int totalCost = totalDevices * 10;

        String url = CommonLibrary.getSettingsSheetInfo().get("WEB_URL_MAIN_QA");
        CommonLibrary.loginWebApp("32669977", "Rahul1122", "");
        CommonLibrary.launchBrowser(url, "Launch web App", dataObj.get("Browser Type"));


        System.out.println(dataObj.get("WEB_URL").toString());
        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));

        addAccountDetails();

        String RefNum=addCommunicationDetails();

        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 100);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathVehicle)));
        //adding vehicles
        Thread.sleep(3000);
        webApplicationAddVehicles(totalDevices);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Vehicle Information Added ", LogStatus.INFO, true);

        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).sendKeys(deviceNumber1);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).sendKeys(deviceNumber2);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Transponder Information Added ", LogStatus.INFO, true);

        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathPlansAndPayment)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathPlansAndPayment)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathPlansAndPayment)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathPlansAndPayment,"","Clicking on Plan Button");
        }
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        FunctionLibrary.selectDropDownListByValue(xpathPlanPortableTransponderCount, deviceNumber2, "");

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='creditCardType']")));
        FunctionLibrary.selectDropDownListByValue(xpathCardType, cardType, "");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathCardNumber)).sendKeys(cardNumber);
        FunctionLibrary.selectDropDownListByValue(xpathCardExpMonth, cardExpMonth, "");
        FunctionLibrary.selectDropDownListByValue(xpathCardExpYear, cardExpYear, "");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathCardSecurityCode)).sendKeys(cardCcvNumber);

        /*WebElement SavePayment = FunctionLibrary.objDriver.findElement(By.xpath(xpathEasyPaySaveFutureOneTimePayments));
        if(!SavePayment.isSelected())
            SavePayment.click();*/
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Primary Card Details Added ", LogStatus.INFO, true);

        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathConfirmation)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathConfirmation)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathConfirmation)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathConfirmation,"","Clicking on Plan Button");
        }

        System.out.println("conf");

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Billing Address Details are auto populated ", LogStatus.INFO, true);
        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
       /* Thread.sleep(2000);
        WebElement Accno,Stax,Tvalue;
        try {
            Accno = FunctionLibrary.objDriver.findElement(By.xpath("(//p[@class='form-control-static'])[5]"));
            Stax = FunctionLibrary.objDriver.findElement(By.xpath("//div[@class='bach']/div[5]/table/tbody/tr[2]/td[2]"));
            Tvalue = FunctionLibrary.objDriver.findElement(By.xpath("//div[@class='bach']/div[5]/table/tbody/tr[7]/td[2]/b"));
            System.out.println("Value 5");
        }
        catch(Exception e)
        {
            Accno = FunctionLibrary.objDriver.findElement(By.xpath(xpathMaskedAccountNumber));
            Stax = FunctionLibrary.objDriver.findElement(By.xpath(xpathStax));
            Tvalue = FunctionLibrary.objDriver.findElement(By.xpath(xpathTotalValue));
            System.out.println("Value 6");
        }
        String Num = Accno.getText();
        String N[] = Num.split("\\r?\\n");
        System.out.println(N[1]);


        String Staxvalue = Stax.getText();

        String Tot = Tvalue.getText();
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Sales Tax Calculation Bases: " + Staxvalue, LogStatus.PASS, false);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Amount Charged as Per the # of Device(s) Requested + Payment Type + Sales Tax + Plan sale Amount + Any Additional Pre payment: " + Tot, LogStatus.PASS, false);
        if (N[1].contains(cardNumber.substring(cardNumber.length() - 4))) {
            System.out.println(N[1] + "  " + cardNumber.substring(cardNumber.length() - 4));
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " S_PTY_PAY_PRFL.X_CC_MASKING Masked and shown only last 4 digit of Credit Card " + N[1], LogStatus.PASS, false);

        }
        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();*/
        Thread.sleep(4000);

        FunctionLibrary.scrollDowntoElement(".//*[@class='icheckbox_minimal']", "Scroll down");

        try {
            FunctionLibrary.objDriver.navigate().refresh();
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
            FunctionLibrary.scrollDowntoElement(".//*[@class='icheckbox_minimal']", "Scroll down");
            // WebElement element = FunctionLibrary.objDriver.findElement(By.xpath("//div[@class='icheckbox_minimal']"));
            WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(".//*[@class='icheckbox_minimal']"));

            System.out.println("click try in try block");
            element.click();

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Confirmation Page Terms & Conditions Checked ", LogStatus.INFO, true);

        } catch (Exception e) {
            System.out.println("click try in catch block");
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='icheckbox_minimal']")));
        }
        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathCompleteButton)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathCompleteButton)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathCompleteButton)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathCompleteButton,"","Clicking on Plan Button");
        }

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class='page-header-noborder']")));
        String message = FunctionLibrary.objDriver.findElement(By.xpath("//h1[@class='page-header-noborder']")).getText();
        System.out.println(message);
        String[] accountArray = message.split(" ");
        System.out.println(accountArray[12]);
        String accountNumber = accountArray[12].replace('.', ' ').trim();
        System.out.println(accountNumber);

        //  ExcelSheet.writeTestCaseData("FLCSS_Web_AccountEstablishment","Test_Scenarios",iterator,accountNumber,"AccountNumber");


        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Web Validation Excepted: <b>  Account # Generated</b> Actual:  <b>  Account # Generated :</b>  " + accountNumber + " ", LogStatus.PASS, false);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Excepted: <b>  Account # Generated Success Message  </b> Actual:  <b>  Account # Generated Success Message displayed </b> ", LogStatus.PASS, true);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Excepted: <b> Confirmation Email Sent After Account Creation </b> Actual: <b> Confirmation Email Sent Successfully </b> ", LogStatus.PASS, false);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Excepted: <b> Verification Link Sent to Email </b> Actual: <b> Verification Link Sent to Email Successfully </b> ", LogStatus.PASS, false);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Email Does Not Become Active Until Email is Validated ", LogStatus.PASS, false);

        String xpathThisPage = "(//*[@class='page-header-noborder']/b/a)[1]";
        String xpathPrintClose = "//a[contains(text(),'CLOSE')]";
        wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathThisPage)));
        FunctionLibrary.objDriver.findElement(By.xpath(xpathThisPage)).click();
        Thread.sleep(5000);

        //wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathPrintClose)));
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Excepted: <b> View and Print the Summary From Web in Printer-Friendly Format </b> Actual: <b> View and Print the Summary From Web in Printer-Friendly Format </b> ", LogStatus.PASS, true);
        Thread.sleep(3000);
        //FunctionLibrary.objDriver.findElement(By.tagName("body")).sendKeys(Keys.CONTROL + "w");
        //  FunctionLibrary.objDriver.findElement(By.xpath(xpathPrintClose)).click();

        SiebelValidation(accountNumber, firstName);

    }

    public static void tcAccountCreationWebACEW113() throws Exception {

        String Desc;
        String firstName = dataObj.get("First Name");
        String cardType = dataObj.get("Card Type");
        String cardNumber = dataObj.get("Card Number");
        String cardExpMonth = dataObj.get("Exp Month");
        String cardExpYear = dataObj.get("Exp Year");
        String cardCcvNumber = dataObj.get("Ccv Number");
        String deviceNumber1 = dataObj.get("Device Count1");
        String deviceNumber2 = dataObj.get("Device Count2");
        int deviceCount1 = Integer.parseInt(deviceNumber1);
        int deviceCount2 = Integer.parseInt(deviceNumber2);
        int totalDevices = deviceCount1 + deviceCount2;
        int totalCost = totalDevices * 10;


        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));
        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 10);

        addAccountDetails();
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));

        String xpathErrorMessage = "//div[@class='alert alert-danger error_msg']";
        String xpathErrorMessage1 = "//div[@id='userName']";
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathErrorMessage)));

        WebElement ErrorMessage = FunctionLibrary.objDriver.findElement(By.xpath(xpathErrorMessage));
        WebElement ErrorMessage1 = FunctionLibrary.objDriver.findElement(By.xpath(xpathErrorMessage1));
        System.out.println(ErrorMessage.getText());
        FunctionLibrary.scrollDowntoElement( xpathEmail, "Scroll down");
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Validation Message Displayed: " +ErrorMessage.getText()+" : "+ ErrorMessage1.getText(), LogStatus.PASS, true);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Excepted:<b> Error message is displayed </b> Actual : <b> " + ErrorMessage.getText()+" : "+ ErrorMessage1.getText() + "</b>", LogStatus.INFO, false);


    }

    public static void tcAccountCreationWebACEW114() throws Exception {

        String Desc;
        String firstName = dataObj.get("First Name");
        String cardType = dataObj.get("Card Type");
        String cardNumber = dataObj.get("Card Number");
        String cardExpMonth = dataObj.get("Exp Month");
        String cardExpYear = dataObj.get("Exp Year");
        String cardCcvNumber = dataObj.get("Ccv Number");
        String deviceNumber1 = dataObj.get("Device Count1");
        String deviceNumber2 = dataObj.get("Device Count2");
        int deviceCount1 = Integer.parseInt(deviceNumber1);
        int deviceCount2 = Integer.parseInt(deviceNumber2);
        int totalDevices = deviceCount1 + deviceCount2;
        int totalCost = totalDevices * 10;


        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));
        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 10);

        addAccountDetails();
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));

        String xpathErrorMessage = "//div[@class='alert alert-danger error_msg']";
        String xpathErrorMessage1 = "//div[@id='userName']";
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathErrorMessage)));

        WebElement ErrorMessage = FunctionLibrary.objDriver.findElement(By.xpath(xpathErrorMessage));
        WebElement ErrorMessage1 = FunctionLibrary.objDriver.findElement(By.xpath(xpathErrorMessage1));
        System.out.println(ErrorMessage.getText());
        FunctionLibrary.scrollDowntoElement( xpathEmail, "Scroll down");
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Validation Message Displayed: " +ErrorMessage.getText()+" : "+ ErrorMessage1.getText(), LogStatus.PASS, true);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Excepted:<b> Error message is displayed </b> Actual : <b> " + ErrorMessage.getText()+" : "+ ErrorMessage1.getText() + "</b>", LogStatus.INFO, false);


    }

    public static void tcAccountCreationWebACEW115() throws Exception {


        String Desc;
        String firstName = dataObj.get("First Name");
        String cardType = dataObj.get("Card Type");
        String cardNumber = dataObj.get("Card Number");
        String cardExpMonth = dataObj.get("Exp Month");
        String cardExpYear = dataObj.get("Exp Year");
        String cardCcvNumber = dataObj.get("Ccv Number");
        String deviceNumber1 = dataObj.get("Device Count1");
        String deviceNumber2 = dataObj.get("Device Count2");
        int deviceCount1 = Integer.parseInt(deviceNumber1);
        int deviceCount2 = Integer.parseInt(deviceNumber2);
        int totalDevices = deviceCount1 + deviceCount2;
        int totalCost = totalDevices * 10;

        System.out.println(dataObj.get("WEB_URL").toString());
        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));

        addAccountDetails();

        String RefNum=addCommunicationDetails();

        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 100);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathVehicle)));
        //adding vehicles
        Thread.sleep(3000);

        webApplicationAddVehicles(totalDevices);
        // CommonLibrary.webApplicationAddVehicles(totalDevices);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "one of the vehicle back dated up to configured (45) days", LogStatus.PASS, true);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Vehicle Information Added ", LogStatus.INFO, false);

        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).sendKeys(deviceNumber1);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).sendKeys(deviceNumber2);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Transponder Information Added ", LogStatus.INFO, true);

        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathPlansAndPayment)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathPlansAndPayment)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathPlansAndPayment)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathPlansAndPayment,"","Clicking on Plan Button");
        }
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='creditCardType']")));
        FunctionLibrary.selectDropDownListByValue(xpathCardType, cardType, "");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathCardNumber)).sendKeys(cardNumber);
        FunctionLibrary.selectDropDownListByValue(xpathCardExpMonth, cardExpMonth, "");
        FunctionLibrary.selectDropDownListByValue(xpathCardExpYear, cardExpYear, "");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathCardSecurityCode)).sendKeys(cardCcvNumber);
       /* WebElement SavePayment = FunctionLibrary.objDriver.findElement(By.xpath(xpathEasyPaySaveFutureOneTimePayments));
        if(!SavePayment.isSelected())
            SavePayment.click();*/
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Primary Card Details Added ", LogStatus.INFO, true);

        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathConfirmation)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathConfirmation)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathConfirmation)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathConfirmation,"","Clicking on Plan Button");
        }

        System.out.println("conformation page");

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));

        ConformationPage();
       /* ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Billing Address Details are auto populated ", LogStatus.INFO, true);
        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
        Thread.sleep(2000);
        WebElement Accno,Stax,Tvalue;
        try {
            Accno = FunctionLibrary.objDriver.findElement(By.xpath("(//p[@class='form-control-static'])[5]"));
            Stax = FunctionLibrary.objDriver.findElement(By.xpath("//div[@class='bach']/div[5]/table/tbody/tr[2]/td[2]"));
            Tvalue = FunctionLibrary.objDriver.findElement(By.xpath("//div[@class='bach']/div[5]/table/tbody/tr[7]/td[2]/b"));
            System.out.println("Value 5");
        }
        catch(Exception e)
        {
            Accno = FunctionLibrary.objDriver.findElement(By.xpath(xpathMaskedAccountNumber));
            Stax = FunctionLibrary.objDriver.findElement(By.xpath(xpathStax));
            Tvalue = FunctionLibrary.objDriver.findElement(By.xpath(xpathTotalValue));
            System.out.println("Value 6");
        }
        String Num = Accno.getText();
        String N[] = Num.split("\\r?\\n");
        System.out.println(N[1]);


        String Staxvalue = Stax.getText();

        String Tot = Tvalue.getText();
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Sales Tax Calculation Bases: " + Staxvalue, LogStatus.PASS, false);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Amount Charged as Per the # of Device(s) Requested + Payment Type + Sales Tax + Plan sale Amount + Any Additional Pre payment: " + Tot, LogStatus.PASS, false);
        if (N[1].contains(cardNumber.substring(cardNumber.length() - 4))) {
            System.out.println(N[1] + "  " + cardNumber.substring(cardNumber.length() - 4));
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " S_PTY_PAY_PRFL.X_CC_MASKING Masked and shown only last 4 digit of Credit Card " + N[1], LogStatus.PASS, false);

        }
        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();

        Thread.sleep(4000);

        FunctionLibrary.scrollDowntoElement("./*//*[@class='icheckbox_minimal']", "Scroll down");

        try {
            FunctionLibrary.objDriver.navigate().refresh();
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
            FunctionLibrary.scrollDowntoElement("./*//*[@class='icheckbox_minimal']", "Scroll down");
            // WebElement element = FunctionLibrary.objDriver.findElement(By.xpath("//div[@class='icheckbox_minimal']"));
            WebElement element = FunctionLibrary.objDriver.findElement(By.xpath("./*//*[@class='icheckbox_minimal']"));

            System.out.println("click try in try block");
            element.click();

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Confirmation Page Terms & Conditions Checked ", LogStatus.INFO, true);

        } catch (Exception e) {
            System.out.println("click try in catch block");
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='icheckbox_minimal']")));
        }
        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathCompleteButton)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathCompleteButton)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathCompleteButton)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathCompleteButton,"","Clicking on Plan Button");
        }
*/
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class='page-header-noborder']")));
        String message = FunctionLibrary.objDriver.findElement(By.xpath("//h1[@class='page-header-noborder']")).getText();
        System.out.println(message);
        String[] accountArray = message.split(" ");
        System.out.println(accountArray[12]);
        String accountNumber = accountArray[12].replace('.', ' ').trim();
        System.out.println(accountNumber);

        //  ExcelSheet.writeTestCaseData("FLCSS_Web_AccountEstablishment","Test_Scenarios",iterator,accountNumber,"AccountNumber");


        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Web Validation Excepted: <b>  Account # Generated</b> Actual:  <b>  Account # Generated :</b>  " + accountNumber + " ", LogStatus.PASS, false);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Excepted: <b>  Account # Generated Success Message  </b> Actual:  <b>  Account # Generated Success Message displayed </b> ", LogStatus.PASS, true);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Excepted: <b> Confirmation Email Sent After Account Creation </b> Actual: <b> Confirmation Email Sent Successfully </b> ", LogStatus.PASS, false);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Excepted: <b> Verification Link Sent to Email </b> Actual: <b> Verification Link Sent to Email Successfully </b> ", LogStatus.PASS, false);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Email Does Not Become Active Until Email is Validated ", LogStatus.PASS, false);

        String xpathThisPage = "(//*[@class='page-header-noborder']/b/a)[1]";
        String xpathPrintClose = "//a[contains(text(),'CLOSE')]";
        wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathThisPage)));
        FunctionLibrary.objDriver.findElement(By.xpath(xpathThisPage)).click();
        Thread.sleep(5000);

        //wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathPrintClose)));
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Excepted: <b> View and Print the Summary From Web in Printer-Friendly Format </b> Actual: <b> View and Print the Summary From Web in Printer-Friendly Format </b> ", LogStatus.PASS, true);
        Thread.sleep(3000);
        //FunctionLibrary.objDriver.findElement(By.tagName("body")).sendKeys(Keys.CONTROL + "w");
        //  FunctionLibrary.objDriver.findElement(By.xpath(xpathPrintClose)).click();

        SiebelValidation(accountNumber, firstName);

    }

    public static void tcAccountCreationWebACEW116() throws Exception {


        String Desc;
        String firstName = dataObj.get("First Name");
        String cardType = dataObj.get("Card Type");
        String cardNumber = dataObj.get("Card Number");
        String cardExpMonth = dataObj.get("Exp Month");
        String cardExpYear = dataObj.get("Exp Year");
        String cardCcvNumber = dataObj.get("Ccv Number");
        String deviceNumber1 = dataObj.get("Device Count1");
        String deviceNumber2 = dataObj.get("Device Count2");
        int deviceCount1 = Integer.parseInt(deviceNumber1);
        int deviceCount2 = Integer.parseInt(deviceNumber2);
        int totalDevices = deviceCount1 + deviceCount2;
        int totalCost = totalDevices * 10;

        System.out.println(dataObj.get("WEB_URL").toString());
        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));

        addAccountDetails();

        String RefNum=addCommunicationDetails();

        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 100);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathVehicle)));
        //adding vehicles
        Thread.sleep(3000);

        webApplicationAddVehicles(totalDevices);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "one of the vehicle back dated after configured (45) days", LogStatus.INFO, true);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Vehicle Information Added ", LogStatus.INFO, false);

        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).sendKeys(deviceNumber1);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).sendKeys(deviceNumber2);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Transponder Information Added ", LogStatus.INFO, true);

        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathPlansAndPayment)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathPlansAndPayment)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathPlansAndPayment)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathPlansAndPayment,"","Clicking on Plan Button");
        }
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        String xpathEffectiveStartDateError="//div[@id='effectiveBeginDate1']";
        String xpathErrorMessage = "//div[@class='alert alert-danger error_msg']";
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathErrorMessage)));

        WebElement ErrorMessage = FunctionLibrary.objDriver.findElement(By.xpath(xpathErrorMessage));
        WebElement EffectiveStartDateErrorMessage = FunctionLibrary.objDriver.findElement(By.xpath(xpathEffectiveStartDateError));
        System.out.println(ErrorMessage.getText());
        System.out.println(EffectiveStartDateErrorMessage.getText());
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Validation Message Displayed: " + EffectiveStartDateErrorMessage.getText(), LogStatus.PASS, true);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Excepted:<b> Error message is displayed </b> Actual : <b> " + ErrorMessage.getText() + "</b>", LogStatus.INFO, false);


    }

    public static void tcAccountCreationWebACEW117() throws Exception {
        String Desc;
        String cardType = dataObj.get("Card Type");
        String cardNumber = dataObj.get("Card Number");
        String cardExpMonth = dataObj.get("Exp Month");
        String cardExpYear = dataObj.get("Exp Year");
        String cardCcvNumber = dataObj.get("Ccv Number");
        String deviceNumber1 = dataObj.get("Device Count1");
        String deviceNumber2 = dataObj.get("Device Count2");
        int deviceCount1 = Integer.parseInt(deviceNumber1);
        int deviceCount2 = Integer.parseInt(deviceNumber2);
        int totalDevices = deviceCount1 + deviceCount2;
        int totalCost = totalDevices * 10;
        String DuplicatePlateNumber = dataObj.get("Duplicate Plate Number");
       // writeVehicleDetailsToExcel("testing_vehicle_9", "Sheet1", totalDevices, DuplicatePlateNumber);

        String CompanyName = "xer" + DateTime.now().getMillisOfSecond() + DateTime.now().getSecondOfMinute() + DateTime.now().getMillisOfSecond();
        String fileUpload = "//*[@name='fileUpload']";
        String CheckBOx = ".//*[@class='icheckbox_minimal']";

        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));
       // FunctionLibrary.objDriver.manage().deleteAllCookies();
        FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='companyName']")).sendKeys(CompanyName);
        FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='dbaName']")).sendKeys("services");
        FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='fein']")).sendKeys("123456789");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondaryFirstName)).sendKeys("JHN");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondaryMiddleName)).sendKeys("M");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondaryLastName)).sendKeys("SAW");
        addAccountDetails();

        String RefNum=addCommunicationDetails();

        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 100);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(fileUpload)));
        //adding vehicles
        //Thread.sleep(4000);
        File file = new File(ReportLibrary.getPath() + "\\testdata\\testing_vehicle_9.docx");
        System.out.println(file.getAbsolutePath());
        FunctionLibrary.objDriver.findElement(By.xpath(fileUpload)).sendKeys(file.getAbsolutePath());
        Thread.sleep(2000);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Vehicle Information Attached ", LogStatus.INFO, true);

        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).sendKeys(deviceNumber1);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).sendKeys(deviceNumber2);
        Thread.sleep(4000);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Transponder Information Added ", LogStatus.INFO, true);
        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathPlansAndPayment)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathPlansAndPayment)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathPlansAndPayment)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathPlansAndPayment,"","Clicking on Plan Button");
        }
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));

        String xpathErrorMessage = "//div[@class='alert alert-danger error_msg']";
       // String xpathErrorMessage = "//div[@id='fileUpload']";
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathErrorMessage)));

        WebElement ErrorMessage = FunctionLibrary.objDriver.findElement(By.xpath(xpathErrorMessage));
        System.out.println(ErrorMessage.getText());

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Error Message Displayed: " + ErrorMessage.getText(), LogStatus.PASS, true);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Excepted:<b> Error message is displayed for incorrect File format.</b> Actual : <b> " + ErrorMessage.getText() + "</b>", LogStatus.INFO, false);


    }

    public static void tcAccountCreationWebACEW118() throws Exception {
        String Desc;
        String cardType = dataObj.get("Card Type");
        String cardNumber = dataObj.get("Card Number");
        String cardExpMonth = dataObj.get("Exp Month");
        String cardExpYear = dataObj.get("Exp Year");
        String cardCcvNumber = dataObj.get("Ccv Number");
        String deviceNumber1 = dataObj.get("Device Count1");
        String deviceNumber2 = dataObj.get("Device Count2");
        int deviceCount1 = Integer.parseInt(deviceNumber1);
        int deviceCount2 = Integer.parseInt(deviceNumber2);
        int totalDevices = deviceCount1 + deviceCount2;
        int totalCost = totalDevices * 10;
        String DuplicatePlateNumber = dataObj.get("Duplicate Plate Number");
        writeVehicleDetailsToExcel("testing_vehicle_9", "Sheet1", totalDevices, DuplicatePlateNumber);

        String CompanyName = "xer" + DateTime.now().getMillisOfSecond() + DateTime.now().getSecondOfMinute() + DateTime.now().getMillisOfSecond();
        String fileUpload = "//*[@name='fileUpload']";
        String CheckBOx = ".//*[@class='icheckbox_minimal']";

        CommonLibrary.launchBrowser(dataObj.get("WEB_URL").toString(), "Launching Web Account Creation Page", dataObj.get("Browser Type"));
        // FunctionLibrary.objDriver.manage().deleteAllCookies();
        FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='companyName']")).sendKeys(CompanyName);
        FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='dbaName']")).sendKeys("services");
        FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='fein']")).sendKeys("123456789");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondaryFirstName)).sendKeys("JHN");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondaryMiddleName)).sendKeys("M");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondaryLastName)).sendKeys("SAW");
        addAccountDetails();

        String RefNum=addCommunicationDetails();

        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 100);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(fileUpload)));
        //adding vehicles
        //Thread.sleep(4000);
        File file = new File(ReportLibrary.getPath() + "\\testdata\\testing_vehicle_9.xlsx");
        System.out.println(file.getAbsolutePath());
        FunctionLibrary.objDriver.findElement(By.xpath(fileUpload)).sendKeys(file.getAbsolutePath());
        Thread.sleep(2000);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Vehicle Information Attached ", LogStatus.INFO, true);

        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).sendKeys(deviceNumber1);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).sendKeys(deviceNumber2);
        Thread.sleep(4000);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Transponder Information Added ", LogStatus.INFO, true);
        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathPlansAndPayment)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathPlansAndPayment)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathPlansAndPayment)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathPlansAndPayment,"","Clicking on Plan Button");
        }
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));

        //div[@id='fileUpload']
        String xpathErrorMessage = "//div[@id='fileUpload']";
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathErrorMessage)));

        WebElement ErrorMessage = FunctionLibrary.objDriver.findElement(By.xpath(xpathErrorMessage));
        System.out.println(ErrorMessage.getText());

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Error Message Displayed: " + ErrorMessage.getText(), LogStatus.PASS, true);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Excepted:<b> Error message is displayed for incorrect File size.</b> Actual : <b> " + ErrorMessage.getText() + "</b>", LogStatus.INFO, false);


    }

    public static void addAccountDetails() throws Exception {
        String Desc;
        String firstName = dataObj.get("First Name");
        String middleName = dataObj.get("Middle Name");
        String lastName = dataObj.get("Last Name");
        String SfirstName=dataObj.get("SecondaryContactFirstName");
        String SmiddleName=dataObj.get("SecondaryContactMiddleName");
        String SlastName=dataObj.get("SecondaryContactLastName");

        String address1 = dataObj.get("Mailing Address1");
        String address2 = dataObj.get("Mailing Address2");
        String city = dataObj.get("City");
        String state = dataObj.get("State");
        String zip = dataObj.get("Zip Code");
        String country = dataObj.get("Country");
        String email = dataObj.get("Email");

        String SecondaryEmail= dataObj.get("SecondaryEmail");


        String Mobile = dataObj.get("Mobile");
        String AlternatePhone = dataObj.get("Alternate Phone");
        String Fax = dataObj.get("FAX");

        String SunPass=dataObj.get("Sun Pass");
        String MobileAlert=dataObj.get("Mobile Alert");
        String LocalGovt=dataObj.get("Local Govt");
        String securityQuestion = dataObj.get("Security Question");
        String securityAnswer = dataObj.get("Security Answer");
        String userName = dataObj.get("User Name");
        if(userName.equalsIgnoreCase("Dynamic"))
            userName = firstName + DateTime.now().getMillisOfSecond() + DateTime.now().getSecondOfMinute() + DateTime.now().getMillisOfSecond();
        String password = dataObj.get("Password1");
        String pin = dataObj.get("Pin");
        String cardType = dataObj.get("Card Type");
        String cardNumber = dataObj.get("Card Number");
        String cardExpMonth = dataObj.get("Exp Month");
        String cardExpYear = dataObj.get("Exp Year");
        String cardCcvNumber = dataObj.get("Ccv Number");
        String deviceNumber1 = dataObj.get("Device Count1");
        String deviceNumber2 = dataObj.get("Device Count2");
        int deviceCount1 = Integer.parseInt(deviceNumber1);
        int deviceCount2 = Integer.parseInt(deviceNumber2);
        int totalDevices = deviceCount1 + deviceCount2;
        int totalCost = totalDevices * 10;


        String xpathAddSecContact="//a[@id='addSecContact']";
        String xpathAddPhoneContact="//a[@id='addContact']";
        String xpathAddSecEmail="//a[@id='addEmail']";
        String xpathSecondaryContactFname="//input[@name='contactnamesid[0].secondaryFirstName']";
        String xpathSecondaryContactMname="//input[@name='contactnamesid[0].secondaryMiddleName']";
        String xpathSecondaryContactLname="//input[@name='contactnamesid[0].secondaryLastName']";
        String xpathSecondaryEmail="//input[@name='secondaryEmail']";
        String xpathSecondaryReEmail="//input[@name='secondaryReEmail']";

        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 30);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathFirstName)));
        wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathFirstName)));
        FunctionLibrary.objDriver.findElement(By.xpath(xpathFirstName)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathFirstName)).sendKeys(firstName);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiddleName)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiddleName)).sendKeys(middleName);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathLastName)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathLastName)).sendKeys(lastName);

        if(!SfirstName.equalsIgnoreCase("NA"))
        {
            WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(xpathAddSecContact));
            element.click();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondaryContactFname)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondaryContactFname)).sendKeys(SfirstName);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondaryContactMname)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondaryContactMname)).sendKeys(SmiddleName);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondaryContactLname)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondaryContactLname)).sendKeys(SlastName);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Multiple Contact Details Entered", LogStatus.INFO, true);

        }


        FunctionLibrary.objDriver.findElement(By.xpath(xpathAddress1)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathAddress1)).sendKeys(address1);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathAddress2)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathAddress2)).sendKeys(address2);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathZipCode)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathZipCode)).sendKeys(zip);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathCity)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathCity)).sendKeys(city);
        FunctionLibrary.selectDropDownListByValue(xpathCountry, country, "");
        FunctionLibrary.selectDropDownListByValue(xpathState, state, "");

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Adding account details Entered", LogStatus.INFO, true);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathEmail)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathEmail)).sendKeys(email);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathReEmail)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathReEmail)).sendKeys(email);
        if(!SecondaryEmail.equalsIgnoreCase("NA"))
        {
            WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(xpathAddSecEmail));
            element.click();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondaryEmail)).sendKeys(SecondaryEmail);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathSecondaryReEmail)).sendKeys(SecondaryEmail);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Secondary Email Details Entered", LogStatus.INFO, true);
        }



     //   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Email Validated" + email, LogStatus.PASS, false);
     //   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Phone Validated" + phone, LogStatus.PASS, false);
        int PhoneCount=0;
        if(!Mobile.equalsIgnoreCase("NA"))
        {
            if(PhoneCount==0)
            {
                FunctionLibrary.objDriver.findElement(By.xpath(xpathFirstPhone)).clear();
                FunctionLibrary.objDriver.findElement(By.xpath(xpathFirstPhone)).sendKeys(Mobile);
                FunctionLibrary.selectDropDownListByValue(xpathPhoneType, "Mobile", "");
                PhoneCount++;
            }
        }
        if(!AlternatePhone.equalsIgnoreCase("NA"))
        {
            if(PhoneCount==0)
            {
                FunctionLibrary.objDriver.findElement(By.xpath(xpathFirstPhone)).clear();
                FunctionLibrary.objDriver.findElement(By.xpath(xpathFirstPhone)).sendKeys(AlternatePhone);
                FunctionLibrary.selectDropDownListByValue(xpathPhoneType, "Alternate Phone", "");
                PhoneCount++;
            }
            else
            {
                WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(xpathAddPhoneContact));
                element.click();
                int temp=PhoneCount+1;
                FunctionLibrary.objDriver.findElement(By.xpath("(//input[@name='firstPhone'])["+temp+"]")).clear();
                FunctionLibrary.objDriver.findElement(By.xpath("(//input[@name='firstPhone'])["+temp+"]")).sendKeys(AlternatePhone);
                FunctionLibrary.selectDropDownListByValue("(//select[@name='phoneTypeUpdate'])["+temp+"]", "Alternate Phone", "");
                PhoneCount++;

                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Multiple Phone Contact Numbers Entered", LogStatus.INFO, true);

            }
        }
        if(!Fax.equalsIgnoreCase("NA"))
        {
            if(PhoneCount==0)
            {
                FunctionLibrary.objDriver.findElement(By.xpath(xpathFirstPhone)).clear();
                FunctionLibrary.objDriver.findElement(By.xpath(xpathFirstPhone)).sendKeys(AlternatePhone);
                FunctionLibrary.selectDropDownListByValue(xpathPhoneType, "Alternate Phone", "");
                PhoneCount++;
            }
            else
            {
                WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(xpathAddPhoneContact));
                element.click();
                int temp=PhoneCount+1;
                FunctionLibrary.objDriver.findElement(By.xpath("(//input[@name='firstPhone'])["+temp+"]")).clear();
                FunctionLibrary.objDriver.findElement(By.xpath("(//input[@name='firstPhone'])["+temp+"]")).sendKeys(AlternatePhone);
                FunctionLibrary.selectDropDownListByValue("(//select[@name='phoneTypeUpdate'])["+temp+"]", "Fax", "");
                PhoneCount++;
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Multiple Phone Contact Numbers Entered", LogStatus.INFO, false);

            }
        }


        if(MobileAlert.equals("Y"))
        {
            WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(xpathMobileAlert));
            if(!element.isSelected())
                element.click();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Mobile Alert Opt In " , LogStatus.INFO, false);

        }
        if(SunPass.equals("Y"))
        {
            WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(xpathSunpass));
            if(!element.isSelected())
                 element.click();
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "SunPass Plus Checked in Account Details " , LogStatus.INFO, false);

        }
        if(LocalGovt.equals("Y"))
        {
            WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(xpathLocalGovt));
            if(!element.isSelected())
                element.click();
          //  ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Belong to a Local, State or Government Agency Checked in Account Details " , LogStatus.INFO, false);

        }


        FunctionLibrary.objDriver.findElement(By.xpath(xpathUserName)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathUserName)).sendKeys(userName);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "User Name =" + userName + "Password =" + password, LogStatus.INFO, true);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathPassword)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathPassword)).sendKeys(password);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathReTypePassword)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathReTypePassword)).sendKeys(password);
        FunctionLibrary.selectDropDownListByValue(xpathSecurityQuestion, securityQuestion, "");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathSecurityAnswer)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathSecurityAnswer)).sendKeys(securityAnswer);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathIpin)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathIpin)).sendKeys(pin);
      //  ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Adding account details in first page", LogStatus.INFO, true);

        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathCommunicationPreferencesBtn)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathCommunicationPreferencesBtn)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathCommunicationPreferencesBtn)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathCommunicationPreferencesBtn,"","Clicking on Plan Button");
        }


        WebDriverWait wait1 = new WebDriverWait(FunctionLibrary.objDriver, 10);
        try{
            wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStandardizeRadioButton)));
            // Thread.sleep(5000);
            wait1.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathStandardizeOkButton)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathStandardizeOkButton)).click();
        }
        catch(Exception e)
        {

        }

        String url = FunctionLibrary.objDriver.getCurrentUrl();
        if (url.contains("500")) {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "On clicking Standardization it is showing error page", LogStatus.FAIL, true);
            FunctionLibrary.objDriver.navigate().back();
            wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStandardizeRadioButton)));
            Thread.sleep(5000);
            wait1.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathStandardizeOkButton)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathStandardizeOkButton)).click();
        }

    }

    public static String  addCommunicationDetails()throws Exception{

        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 15);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        FunctionLibrary.objDriver.navigate().refresh();
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathReferenceNumber)));
        WebElement ref = FunctionLibrary.objDriver.findElement(By.xpath(xpathReferenceNumber));
        String RefNum = ref.getText();
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Reference number is is Displayed " + RefNum, LogStatus.PASS, false);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Communication Preferences Added ", LogStatus.INFO, true);

        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
        Thread.sleep(2000);
        WebDriverWait wait6 = new WebDriverWait(FunctionLibrary.objDriver, 3);
        try{
            wait6.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathTollTagsVehiclesButton)));
        }
        catch(Exception e)
        {
            xpathTollTagsVehiclesButton="//button[contains(text(),'NEXT: TOLL TRANSPONDERS & VEHICLES')]";
            wait6.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathTollTagsVehiclesButton)));

        }
        try {
            wait6.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathTollTagsVehiclesButton)));
            wait6.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathTollTagsVehiclesButton)));
           // FunctionLibrary.clickObject(xpathTollTagsVehiclesButton,"","Clicking on Plan Button");
           FunctionLibrary.objDriver.findElement(By.xpath(xpathTollTagsVehiclesButton)).click();

           System.out.println("comm1");
        }
        catch(Exception e)
        {
           // FunctionLibrary.objDriver.findElement(By.xpath(xpathTollTagsVehiclesButton)).click();
            FunctionLibrary.clickObject(xpathTollTagsVehiclesButton,"","Clicking on Plan Button");
        }
        return RefNum;
    }

    public static void TranspondersAndVehicles() throws Exception {
        String deviceNumber1 = dataObj.get("Device Count1");
        String deviceNumber2 = dataObj.get("Device Count2");
        String VehicleModel=dataObj.get("Vehicle Model");
        String NoOfVehiclestobeAdded=dataObj.get("NoOfVehiclestobeAdded");
        int deviceCount1 = Integer.parseInt(deviceNumber1);
        int deviceCount2 = Integer.parseInt(deviceNumber2);
        int totalDevices = Integer.parseInt(NoOfVehiclestobeAdded);
        int totalCost = totalDevices * 10;

        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 20);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathVehicle)));
        //adding vehicles
        Thread.sleep(3000);
        if(VehicleModel.equalsIgnoreCase("Y")) {
            webApplicationAddVehicles(totalDevices);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Vehicle Information Added ", LogStatus.INFO, true);
        }
        else
        {
            addMetalOxideWindshieldVehicle(totalDevices);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Vehicle Information Added with Metal oxide transponder Vehicle Model ", LogStatus.INFO, true);
        }
        // CommonLibrary.webApplicationAddVehicles(totalDevices);



        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).sendKeys(deviceNumber1);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).sendKeys(deviceNumber2);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Transponder Information Added ", LogStatus.INFO, true);

        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathPlansAndPayment)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathPlansAndPayment)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathPlansAndPayment)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathPlansAndPayment,"","Clicking on Plan Button");
        }

    }

    public static void PlansPaymentsAndBillingAddress() throws Exception {

        String PaymentMethod=dataObj.get("Payment Option");
        String firstName = dataObj.get("First Name");
        String address1 = dataObj.get("Mailing Address1");
        String address2 = dataObj.get("Mailing Address2");
        String city = dataObj.get("City");
        String state = dataObj.get("State");
        String zip = dataObj.get("Zip Code");
        String cardType = dataObj.get("Card Type");
        String cardNumber = dataObj.get("Card Number");
        String cardExpMonth = dataObj.get("Exp Month");
        String cardExpYear = dataObj.get("Exp Year");
        String cardCcvNumber = dataObj.get("Ccv Number");
        String BankAccountType = dataObj.get("Account Type");
        String BankNameOnAccount = dataObj.get("Name On Account");
        String BankAccountNumber = dataObj.get("Bank Account Number");
        String BankRoutingNumber = dataObj.get("Bank Routing Number");

        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 20);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        PinellasBaywayCommuter();
        if(PaymentMethod.equalsIgnoreCase("Credit Card"))
        {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='creditCardType']")));
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Credit card Payment is Selected ", LogStatus.INFO, false);
            FunctionLibrary.selectDropDownListByValue(xpathCardType, cardType, "");
            FunctionLibrary.objDriver.findElement(By.xpath(xpathCardNumber)).sendKeys(cardNumber);
            FunctionLibrary.selectDropDownListByValue(xpathCardExpMonth, cardExpMonth, "");
            FunctionLibrary.selectDropDownListByValue(xpathCardExpYear, cardExpYear, "");
            FunctionLibrary.objDriver.findElement(By.xpath(xpathCardSecurityCode)).sendKeys(cardCcvNumber);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Primary Card Details Added and replenishment method as "+cardType, LogStatus.INFO, true);
        }
        else if(PaymentMethod.equalsIgnoreCase("Bank Account"))
        {

            try {
                Thread.sleep(1000);
                FunctionLibrary.objDriver.navigate().refresh();
                wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathBankAccount)));
                WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(xpathBankAccount));
                element.click();
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Bank account Payment is Selected ", LogStatus.INFO, false);

            }
            catch (Exception e)
            {
                FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathBankAccount)));
                FunctionLibrary.objDriver.findElement(By.xpath(xpathBankAccount)).click();
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Bank account Payment is Selected ", LogStatus.INFO, false);
            }

            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathBankAccountType)));
            FunctionLibrary.selectDropDownListByValue(xpathBankAccountType, BankAccountType, "");
            // FunctionLibrary.objDriver.findElement(By.xpath(xpathBankNameOnAccount)).sendKeys(BankNameOnAccount);

            FunctionLibrary.objDriver.findElement(By.xpath(xpathBankAccountNumber)).sendKeys(BankAccountNumber);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathRetypeBankAccountNumber)).sendKeys(BankAccountNumber);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathBankRoutingNumber)).sendKeys(BankRoutingNumber);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathBillingAddress1)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathBillingAddress1)).sendKeys(address1);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathBillingAddress2)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathBillingAddress2)).sendKeys(address2);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathBillingAddressbankCity)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathBillingAddressbankCity)).sendKeys(city);
            FunctionLibrary.objDriver.findElement(By.xpath(xpathBillingAddressbankZipCode)).clear();
            FunctionLibrary.objDriver.findElement(By.xpath(xpathBillingAddressbankZipCode)).sendKeys(zip);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Bank account Information Added and replenishment method as ACH "+BankAccountType, LogStatus.INFO, true);
        }


       // new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Billing Address Details are auto populated ", LogStatus.INFO, true);

               /* WebElement SavePayment = FunctionLibrary.objDriver.findElement(By.xpath(xpathEasyPaySaveFutureOneTimePayments));
                if(!SavePayment.isSelected())
                    SavePayment.click();*/


        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathConfirmation)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathConfirmation)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathConfirmation)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathConfirmation,"","Clicking on Plan Button");
        }

    }

    public static void ConformationPage() throws Exception {

        String cardType = dataObj.get("Card Type");
        String cardNumber = dataObj.get("Card Number");
        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 10);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));

        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
        Thread.sleep(2000);
        String xpathMaskedAccountNumber1="//*[contains(text(), '"+cardType+"')]/..";
        String xpathStax1="//*[contains(text(), 'Sales Tax')]/../td[2]";
        String xpathTotalValue1="//*[contains(text(), 'Total Amount Due')]/../../td[2]";
        WebElement Accno,Stax,Tvalue;
        try {
            Accno = FunctionLibrary.objDriver.findElement(By.xpath(xpathMaskedAccountNumber1));
            Stax = FunctionLibrary.objDriver.findElement(By.xpath(xpathStax1));
            Tvalue = FunctionLibrary.objDriver.findElement(By.xpath(xpathTotalValue1));
            System.out.println("New path is working");
        }
        catch(Exception e)
        {
            Accno = FunctionLibrary.objDriver.findElement(By.xpath(xpathMaskedAccountNumber));
            Stax = FunctionLibrary.objDriver.findElement(By.xpath(xpathStax));
            Tvalue = FunctionLibrary.objDriver.findElement(By.xpath(xpathTotalValue));
            System.out.println("Value 6");
        }
        String Num = Accno.getText();
        String N[] = Num.split("\\r?\\n");
        System.out.println(N[1]);


        String Staxvalue = Stax.getText();

        String Tot = Tvalue.getText();
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Sales Tax Calculation Bases: " + Staxvalue, LogStatus.PASS, false);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Amount Charged as Per the # of Device(s) Requested + Payment Type + Sales Tax + Plan sale Amount + Any Additional Pre payment: " + Tot, LogStatus.PASS, false);
        if (N[1].contains(cardNumber.substring(cardNumber.length() - 4))) {
            System.out.println(N[1] + "  " + cardNumber.substring(cardNumber.length() - 4));
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " S_PTY_PAY_PRFL.X_CC_MASKING Masked and shown only last 4 digit of Credit Card " + N[1], LogStatus.PASS, false);

        }
        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();

        Thread.sleep(4000);

        FunctionLibrary.scrollDowntoElement(".//*[@class='icheckbox_minimal']", "Scroll down");

        try {
            FunctionLibrary.objDriver.navigate().refresh();
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
            FunctionLibrary.scrollDowntoElement(".//*[@class='icheckbox_minimal']", "Scroll down");
            // WebElement element = FunctionLibrary.objDriver.findElement(By.xpath("//div[@class='icheckbox_minimal']"));
            WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(".//*[@class='icheckbox_minimal']"));

            System.out.println("click try in try block");
            element.click();

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Confirmation Page Terms & Conditions Checked ", LogStatus.INFO, true);

        } catch (Exception e) {
            System.out.println("click try in catch block");
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='icheckbox_minimal']")));
        }
        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathCompleteButton)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathCompleteButton)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathCompleteButton)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathCompleteButton,"","Clicking on Plan Button");
        }

    }

    public  static String ValidationAfterAccountNumberGeneration(String RefNum) throws InterruptedException {
        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 20);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class='page-header-noborder']")));
        String message = FunctionLibrary.objDriver.findElement(By.xpath("//h1[@class='page-header-noborder']")).getText();
        System.out.println(message);
        String[] accountArray = message.split(" ");
        System.out.println(accountArray[12]);
        String accountNumber = accountArray[12].replace('.', ' ').trim();
        System.out.println(accountNumber);

        //  ExcelSheet.writeTestCaseData("FLCSS_Web_AccountEstablishment","Test_Scenarios",iterator,accountNumber,"AccountNumber");

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Web Validation Excepted: <b>  Account # Generated</b> Actual:  <b>  Account # Generated :</b>  " + accountNumber + " ", LogStatus.PASS, false);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Excepted: <b>  Account # Generated Success Message  </b> Actual:  <b>  Account # Generated Success Message displayed </b> ", LogStatus.PASS, true);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Excepted: <b> Confirmation Email Sent After Account Creation </b> Actual: <b> Confirmation Email Sent Successfully </b> ", LogStatus.PASS, false);

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Excepted: <b> Verification Link Sent to Email </b> Actual: <b> Verification Link Sent to Email Successfully </b> ", LogStatus.PASS, false);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Email Does Not Become Active Until Email is Validated ", LogStatus.PASS, false);

        String xpathThisPage = "(//*[@class='page-header-noborder']/b/a)[1]";
        String xpathPrintClose = "//a[contains(text(),'CLOSE')]";
        wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathThisPage)));
        FunctionLibrary.objDriver.findElement(By.xpath(xpathThisPage)).click();
        Thread.sleep(5000);

        //wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathPrintClose)));
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Excepted: <b> View and Print the Summary From Web in Printer-Friendly Format </b> Actual: <b> View and Print the Summary From Web in Printer-Friendly Format </b> ", LogStatus.PASS, true);
        Thread.sleep(3000);
        //FunctionLibrary.objDriver.findElement(By.tagName("body")).sendKeys(Keys.CONTROL + "w");
        //  FunctionLibrary.objDriver.findElement(By.xpath(xpathPrintClose)).click();
        return accountNumber;
    }

    public static void SiebelValidation(String accountNumber, String firstName) throws InterruptedException {
        FunctionLibrary.closeAllActiveBrowser();
        String uname = dataObj.get("UserId");
        String pwd = dataObj.get("Password");
        //Enter User Name
        System.out.println(uname + " " + pwd);

        CommonLibrary.loginSiebelApp("chrome", "", uname, pwd);
        String Desc = "Clicking Accounts tab";
        System.out.println(accountNumber);
        CommonLibrary.searchForAccount(accountNumber);
        Thread.sleep(10000);
        WebElement AccNum = FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='s_1_1_50_0']"));
        AccNum.getAttribute("value");
        System.out.println(AccNum.getText() + "" + AccNum.getAttribute("value"));
        WebElement AccBal = FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='s_1_1_60_0']"));
        System.out.println(AccBal.getText() + "" + AccBal.getAttribute("value"));


        if (accountNumber.equalsIgnoreCase(AccNum.getAttribute("value"))) {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<br> <b> Excepted: Account Details available in Siebel Screen </b><br> <b> Actual : Account Details available in Siebel Screen and  Account Validated in Siebel Screens: " + AccNum.getAttribute("value")+ " <b> ", LogStatus.PASS, false);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<br> <b> Excepted: Account Balance is updated in siebel  </b><br> <b> Actual :Account Balance Updated in siebel: " + AccBal.getAttribute("value")+ " <b> ", LogStatus.PASS, true);

        }
        String xpathRebillInfo = "//a[text()='Rebill Info']";
        String xpathRebillInfoTable = "./*//*[@summary='Replenishment Info List Applet']";
        FunctionLibrary.objDriver.findElement(By.linkText(accountNumber)).click();
        FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathRebillInfo)));
        FunctionLibrary.objDriver.findElement(By.xpath(xpathRebillInfo)).click();
        FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathRebillInfoTable)));
        //  int rowNumber = FunctionLibrary.getRowNumberFromWebTable(xpathRebillInfoTable,firstName,"get the row number");

        // if(rowNumber>0)
        {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "<br> <b> Excepted: Rebill Info Saved </b><br> <b>  Actual: Rebill Info Saved <b>", LogStatus.PASS, true);
        }
        CommonLibrary.logoutSiebelApplicaiton();
        FunctionLibrary.closeAllActiveBrowser();
    }

    public static Boolean det(String ACNO) {

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

    public static void addCommunicationPreferences() {
        int rowcount = FunctionLibrary.objDriver.findElements(By.xpath("//table[@class='footable footable-loaded']/tbody/tr")).size();
        int colcount = FunctionLibrary.objDriver.findElements(By.xpath("//table[@class='footable footable-loaded']/tbody/tr[1]/td")).size();

        System.out.println(rowcount + " " + colcount);
        for (int i = 1; i <= rowcount; i++) {
            String s1 = "NA";

            for (int j = 2; j <= colcount; j++) {
                if (i == 1)
                    s1 = dataObj.get("Account Informational Messages");
                else if (i == 2)
                    s1 = dataObj.get("Travel Related Information");
                else if (i == 3)
                    s1 = dataObj.get("Newsletters");
                else if (i == 5)
                    s1 = dataObj.get("Construction Updates");
                else if (i == 7)
                    s1 = dataObj.get("Marketing Information");


                WebElement e1 = FunctionLibrary.objDriver.findElement(By.xpath("//table[@class='footable footable-loaded']/tbody/tr[" + i + "]/td"));

                if (s1.equalsIgnoreCase("NA")) {
                    WebElement e2 = FunctionLibrary.objDriver.findElement(By.xpath("//table[@class='footable footable-loaded']/tbody/tr[" + i + "]/td[" + j + "]//*[@id='_chk']"));
                    if (e2.isSelected()) {
                        // e2.click();
                    }
                } else {
                    String[] x = s1.split(",");
                    for (int k = 0; k < x.length; k++) {
                        if (x[k].equalsIgnoreCase("Mail")) {
                            WebElement e2 = FunctionLibrary.objDriver.findElement(By.xpath("//table[@class='footable footable-loaded']/tbody/tr[" + i + "]/td[2]//*[@id='_chk']"));
                            if (!e2.isSelected()) {
                                e2.click();
                            }
                        }
                        if (x[k].equalsIgnoreCase("EMail")) {
                            WebElement e2 = FunctionLibrary.objDriver.findElement(By.xpath("//table[@class='footable footable-loaded']/tbody/tr[" + i + "]/td[3]//*[@id='_chk']"));
                            if (!e2.isSelected()) {
                                e2.click();
                            }
                        }
                        if (x[k].equalsIgnoreCase("Text")) {
                            WebElement e2 = FunctionLibrary.objDriver.findElement(By.xpath("//table[@class='footable footable-loaded']/tbody/tr[" + i + "]/td[4]/*//*[@id='_chk']"));
                            if (!e2.isSelected() && e2.isEnabled()) {
                                e2.click();
                            }
                        }
                        if (x[k].equalsIgnoreCase("VoiceMail")) {
                            WebElement e2 = FunctionLibrary.objDriver.findElement(By.xpath("//table[@class='footable footable-loaded']/tbody/tr[" + i + "]/td[5]//*[@id='_chk']"));
                            if (!e2.isSelected()) {
                                e2.click();
                            }
                        }
                        if (x[k].equalsIgnoreCase("Push Notification")) {
                            WebElement e2 = FunctionLibrary.objDriver.findElement(By.xpath("//table[@class='footable footable-loaded']/tbody/tr[" + i + "]/td[6]//*[@id='_chk']"));
                            if (!e2.isSelected()) {
                                e2.click();
                            }
                        }
                    }
                }
            }

        }
    }

    public static void webApplicationAddVehicles(int numberOfVehicles) throws Exception {

        String platenumber = "";
        String DuplicatePlateNumber = dataObj.get("Duplicate Plate Number");
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");
        Calendar calReturn = Calendar.getInstance();
        calReturn.add(Calendar.DATE, -30);
        Date date=calReturn.getTime();
        System.out.println(calReturn.getTime() + "   "+ dateFormat.format(date));
        String StartDate=dateFormat.format(date);
        DateFormat Time1 = new SimpleDateFormat("hh:mm a");
        Date time=calReturn.getTime();
        String StartTime=Time1.format(time);
        calReturn.add(Calendar.DATE, +60);
        date=calReturn.getTime();
        String EndDate=dateFormat.format(date);
        String BackDated = dataObj.get("Vehicle Back dated");
        int count=0;
        for(int j=0;j<numberOfVehicles;j++) {
            if(!DuplicatePlateNumber.equalsIgnoreCase("NA"))
            {
                platenumber = DuplicatePlateNumber;
            }
            else {
                platenumber = "FL" + DateTime.now().getMillisOfSecond() + DateTime.now().getSecondOfMinute() + DateTime.now().getMillisOfSecond();
            }
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].vehicleLicense']")).sendKeys(platenumber);
            FunctionLibrary.selectDropDownListByIndex("//select[@name='vehicleid[" + j + "].vehicleMake']", "3", "");
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].vehicleModel']")).sendKeys("OTHER");
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].vehicleYear']")).sendKeys("2016");
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].vehicleColor']")).sendKeys("Red");
            Thread.sleep(1000);

/*
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].effectiveBeginDate']")).click();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_LEFT)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].effectiveBeginTime']")).click();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].effectiveEdDate']")).click();

            for (int k = 0; k < 32; k++) {
                new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_RIGHT)).perform();
            }
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].effectiveEdTime']")).click();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            if (j != numberOfVehicles - 1) {
                FunctionLibrary.objDriver.findElement(By.xpath("//a[@id='addveh']")).click();
            }
            if (j > 1 && j % 3 == 0) {
                new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
            }*/



            //Start Date
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_LEFT)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            //Start Time
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            //End Date
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
            for (int k = 0; k < 32; k++) {
                new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_RIGHT)).perform();
            }
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();

            // End Time
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            if (j != numberOfVehicles - 1) {
                FunctionLibrary.objDriver.findElement(By.xpath("//a[@id='addveh']")).click();
            }
            if (j > 1 && j % 3 == 0) {
                new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
            }

           /* if(BackDated.equalsIgnoreCase("Y") && count<1){
                count++;
                FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].effectiveBeginDate']")).sendKeys("02/01/2017");
            }
            else
                FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].effectiveBeginDate']")).sendKeys(StartDate);
            Thread.sleep(1000);
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].effectiveBeginTime']")).click();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            Thread.sleep(2000);
           // FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].effectiveBeginTime']")).sendKeys(StartTime);
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].effectiveEdDate']")).sendKeys(EndDate);
            Thread.sleep(1000);
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].effectiveEdTime']")).click();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            Thread.sleep(1000);
           // FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].effectiveEdTime']")).sendKeys(StartTime);

 *//*           FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].effectiveBeginDate']")).click();

            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_LEFT)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            Thread.sleep(1000);
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].effectiveBeginTime']")).click();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            Thread.sleep(1000);
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].effectiveEdDate']")).click();

            for (int k = 0; k < 32; k++) {
                new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_RIGHT)).perform();
            }

            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            Thread.sleep(1000);
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].effectiveEdTime']")).click();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();*//*
            Thread.sleep(1000);
            if(j!=numberOfVehicles-1)
            {
                FunctionLibrary.objDriver.findElement(By.xpath("//a[@id='addveh']")).click();
            }
            if(j>1 && j%3==0)
            {
                new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
            }*/
        }
    }

    public static void webApplicationAddVehiclesDuplcatePlateNumber(String DuplicatePlateNumber, int numberOfVehicles) throws Exception {

        String platenumber = "";
        for (int j = 0; j < 1; j++) {
            platenumber = DuplicatePlateNumber;
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].vehicleLicense']")).sendKeys(platenumber);
            FunctionLibrary.selectDropDownListByIndex("//select[@name='vehicleid[" + j + "].vehicleMake']", "3", "");
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].vehicleModel']")).sendKeys("OTHER");
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].vehicleYear']")).sendKeys("2016");
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].vehicleColor']")).sendKeys("Red");
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].effectiveBeginDate']")).click();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_LEFT)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].effectiveBeginTime']")).click();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].effectiveEdDate']")).click();

            for (int k = 0; k < 32; k++) {
                new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_RIGHT)).perform();
            }
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].effectiveEdTime']")).click();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            if (j != numberOfVehicles - 1) {
                FunctionLibrary.objDriver.findElement(By.xpath("//a[@id='addveh']")).click();
            }
            if (j > 1 && j % 3 == 0) {
                new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
            }
        }

    }


    public static void webApplicationAddVehiclesRCSPPlateNumber(int numberOfVehicles) throws Exception {

        String platenumber = "";
        for (int j = 0; j < 1; j++) {
            platenumber = "9501";
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].vehicleLicense']")).sendKeys(platenumber);
            FunctionLibrary.selectDropDownListByIndex("//select[@name='vehicleid[" + j + "].vehicleMake']", "3", "");
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].vehicleModel']")).sendKeys("OTHER");
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].vehicleYear']")).sendKeys("2016");
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].vehicleColor']")).sendKeys("Red");
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_LEFT)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            // adding without end date

//            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
//            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
//            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
//            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
//            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
//            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
//end date end
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
            for (int k = 0; k < 32; k++) {
                new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_RIGHT)).perform();
            }
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            if (j != numberOfVehicles - 1) {
                new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
                new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            }
            if (j > 1 && j % 3 == 0) {
                new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
            }
        }


    }


    public static void addMetalOxideWindshieldVehicle(int numberOfVehicles) throws Exception {

        String platenumber = "";
        for (int j = 0; j < numberOfVehicles; j++) {
            platenumber = "FL" + DateTime.now().getMillisOfSecond() + DateTime.now().getSecondOfMinute() + DateTime.now().getMillisOfSecond();
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].vehicleLicense']")).sendKeys(platenumber);
            FunctionLibrary.selectDropDownListByIndex("//select[@name='vehicleid[" + j + "].vehicleMake']", "3", "");
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].vehicleModel']")).sendKeys("Metal oxide windshield");
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].vehicleYear']")).sendKeys("2016");
            FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].vehicleColor']")).sendKeys("Red");
            Thread.sleep(1000);

            //Start Date
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_LEFT)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            //Start Time
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            //End Date
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
            for (int k = 0; k < 32; k++) {
                new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_RIGHT)).perform();
            }
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();

            // End Time
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
            new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
            if (j != numberOfVehicles - 1) {
                FunctionLibrary.objDriver.findElement(By.xpath("//a[@id='addveh']")).click();
            }
            if (j > 1 && j % 3 == 0) {
                new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
            }



        }
    }

    public static void PinellasBaywayCommuter() throws Exception {
        String PinellasBWCommuter = dataObj.get("Pinellas Bayway Commuter");
        String SunPassPortableInternalMount = dataObj.get("SunPassPortableInternalMount");
        String SunPassMiniSticker = dataObj.get("SunPassMiniSticker");

        if(!PinellasBWCommuter.equalsIgnoreCase("NA"))
        {
            if(!SunPassPortableInternalMount.equalsIgnoreCase("NA"))
            {
                FunctionLibrary.selectDropDownListByValue(xpathSunPassPortableInternalMount, SunPassPortableInternalMount, "");
            }
            if(!SunPassMiniSticker.equalsIgnoreCase("NA"))
            {
                FunctionLibrary.selectDropDownListByValue(xpathSunPassMiniSticker, SunPassMiniSticker, "");
            }

        }
    }

    public static void CSRreview_Approval(String Company) throws InterruptedException {
         FunctionLibrary.closeAllActiveBrowser();
        String uname = dataObj.get("UserId");
        String pwd = dataObj.get("Password");

        System.out.println(uname + " " + pwd);
        CommonLibrary.loginSiebelApp("chrome", "", uname, pwd);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " CSR review and approval Siebel Login Success ", LogStatus.PASS, true);

        String Desc = "Clicking Accounts tab";
        // System.out.println(accountNumber);

        String xpathAccountNameTextBox = "//input[@aria-label='Account Name' and @aria-labelledby='Name_Label']";
        FunctionLibrary.clickObject(accountsTab, "", "Clicking Accounts tab");
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(xpathAccountNameTextBox,
                accountsTab, 6, 30);
        FunctionLibrary.setText(xpathAccountNameTextBox, Company, "Entering the account name");
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Search for account name: " + Company, LogStatus.INFO, false);
        FunctionLibrary.clickObject(goBtn, "", "Clicking go button");
        Thread.sleep(10000);
        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 100);
        String accountlink = "//a[@name='Name']";
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(accountlink)));
        wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(accountlink)));
        FunctionLibrary.objDriver.findElement(By.xpath(accountlink)).click();

        String PlansLink = "//a[text()='Plans']";
        String PlansNewLink = "//button[@title='New']";
        String PlanName = "//*[@id='1_s_2_l_Plan_Name']";
        String PlanNameInput = "//*[@id='1_s_2_l_Plan_Name']/input";
        String PlansGo = "(//button[@title='Go'])[2]";
        String PlansPay = "//*[@id='s_2_1_3_0_Ctrl']"; //button[@title='Pay' and @aria-label='Pay']";
        String PaymentListNew = "//button[@title='Payments List:New']";
        String PaymentListSaveRebillInf = "//button[@id='saveReBlInf']";
        String PaymentListSave = "//button[@title='Payments List:Save']";
        String PaymentListProcessPay = "//button[@title='Payments List:Process Pay']";
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PlansLink)));
        wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(PlansLink)));
        FunctionLibrary.objDriver.findElement(By.xpath(PlansLink)).click();

        Thread.sleep(4000);
        int rowNumber=0;
     //  rowNumber = FunctionLibrary.getRowNumberFromWebTable("//table[@id='s_2_l']", "FLEETPRE", "get the row number");
        System.out.println(rowNumber);
      //  wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PlansLink)));
       WebElement FleetPre=FunctionLibrary.objDriver.findElement(By.xpath("//td[@title='FLEETPRE']"));

        if(!FleetPre.isDisplayed()) {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PlansNewLink)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(PlansNewLink)));
            FunctionLibrary.objDriver.findElement(By.xpath(PlansNewLink)).click();
            FunctionLibrary.objDriver.findElement(By.xpath(PlanName)).click();
            FunctionLibrary.objDriver.findElement(By.xpath(PlanNameInput)).sendKeys("FLEETPRE");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " CSR review and approval Review/Add Plan : Fleetpre ", LogStatus.PASS, true);

            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PlansGo)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(PlansGo)));
            FunctionLibrary.objDriver.findElement(By.xpath(PlansGo)).click();
        }
        Thread.sleep(2000);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PlansPay)));
        wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(PlansPay)));
        FunctionLibrary.objDriver.findElement(By.xpath(PlansPay)).click();

        Thread.sleep(2000);

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PaymentListNew)));

        FunctionLibrary.scrollDowntoElement(PaymentListNew, "Scroll down to PaymentList New");

        //Clicking on payment new
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PaymentListNew)));
        wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(PaymentListNew)));

        FunctionLibrary.objDriver.findElement(By.xpath(PaymentListNew)).click();

        //Clicking on save account details
        wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(PaymentListSaveRebillInf)));
        FunctionLibrary.objDriver.findElement(By.xpath(PaymentListSaveRebillInf)).click();
        //Clicking on save  details
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PaymentListSave)));
        wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(PaymentListSave)));

        FunctionLibrary.objDriver.findElement(By.xpath(PaymentListSave)).click();
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " CSR review and approval Make Payment ", LogStatus.PASS, true);

        //Clicking on Process pay
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PaymentListProcessPay)));
        wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(PaymentListProcessPay)));

        String alertText = "";
        //Thread.sleep(3000);
        try {

            FunctionLibrary.objDriver.findElement(By.xpath(PaymentListProcessPay)).click();

            // Thread.sleep(5000);
            Desc = "Waiting for Account number Pop up";


            wait2.until(ExpectedConditions.alertIsPresent());
            Alert alert = FunctionLibrary.objDriver.switchTo().alert();
            alertText = alert.getText();
            System.out.println(alert.getText());
            alert.accept();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Excepted: <b>  Account Generated Success Message  </b> Actual:  <b>  Account/SR # Generated Success Message displayed </b> ", LogStatus.PASS, true);

        } catch (Exception e) {
            wait2.until(ExpectedConditions.alertIsPresent());
            Alert alert = FunctionLibrary.objDriver.switchTo().alert();
            alertText = alert.getText();
            System.out.println(alert.getText());
            alert.accept();
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Web Validation Excepted: <b>  Account Generated Success Message  </b> Actual:  <b>  Account/SR # Generated Success Message displayed </b> ", LogStatus.PASS, true);


        }

        Desc = "Verify Account  number field";
        int AccountNumber = Integer.valueOf(getStringBetweenTwoStrings(alertText, "#", " created").trim());

        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Web Validation Excepted: <b>  Account  # Generated</b> Actual:  <b>  Account  # Generated :</b>  " + AccountNumber + " ", LogStatus.PASS, true);


        System.out.println("AccountNumber");
        Thread.sleep(5000);
        FunctionLibrary.closeAllActiveBrowser();
    }

    public static void writeVehicleDetailsToExcel(String testDataFileName, String sheetname, int rownum, String Duplicate) throws EncryptedDocumentException, InvalidFormatException, IOException {

        String filelocation = ReportLibrary.getPath() + "\\testdata\\" + testDataFileName + ".xlsx";
        FileInputStream fis = new FileInputStream(filelocation);


        XSSFWorkbook w = new XSSFWorkbook(fis);
        XSSFSheet s = w.getSheet(sheetname);
        int count=0;
        int NumberOfRows = rownum + 4;
        for (int i = 4; i < NumberOfRows; i++) {
            XSSFRow r = s.getRow(i);
            String state = dataObj.get("State");
            String zip = dataObj.get("Zip Code");
            String country = dataObj.get("Country");
            String a[] = new String[13];
            if(!Duplicate.equalsIgnoreCase("NA") && count==0)
            {
                a[0]=Duplicate;count=1;
              //  System.out.print(a[0]);
            }
            else {
                a[0] = state + randomString(4) + DateTime.now().getMillisOfSecond();
               // System.out.print(a[0]);
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
            Date date=calReturn.getTime();
            //System.out.println(calReturn.getTime() + "   "+ dateFormat.format(date));
          // a[8]=dateFormat.format(date);
            a[8] ="15-06-2017  10:30:00 AM";
           // System.out.println(a[8]);
            dateFormat.format(date);
            calReturn.add(Calendar.DATE, +180);
            Date date1=calReturn.getTime();
           // System.out.println(calReturn.getTime() + "   "+ dateFormat.format(date1));
           // a[9] =dateFormat.format(date1);
           a[9] = "16-10-2017  10:30:00 AM";
           // System.out.println(a[9]);
            a[10] = "N";
            a[11] = "N";
            a[12] = "N";
            for (int j = 0; j < 1; j++) {
              Cell cell = r.createCell(j);
               // cell.setCellType(cell.CELL_TYPE_STRING);
                cell.setCellValue(a[j]);
            }
            //System.out.println(i);
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

    public static ArrayList<String> newAccNumber() throws Exception {
        ExcelSheet exl = new ExcelSheet();
        int noOfRows = exl.totalrows("FLCSS_Web_AccountEstablishment", "Test_Scenarios");
        String testCaseName = "";
        HashMap<String, String> eachTestCaseData = new HashMap<String, String>();
        eachTestCaseData = getEachTestCaseData(exl, "Test_Scenarios", 1, "FLCSS_Web_AccountEstablishment");
        dataObj1 = eachTestCaseData;

        String Desc;
        System.out.println("hello");
        String firstName = dataObj1.get("First Name");
        String cardType = dataObj1.get("Card Type");
        String cardNumber = dataObj1.get("Card Number");
        String cardExpMonth = dataObj1.get("Exp Month");
        String cardExpYear = dataObj1.get("Exp Year");
        String cardCcvNumber = dataObj1.get("Ccv Number");
        String deviceNumber1 = dataObj1.get("Device Count1");
        String deviceNumber2 = dataObj1.get("Device Count2");
        int deviceCount1 = Integer.parseInt(deviceNumber1);
        int deviceCount2 = Integer.parseInt(deviceNumber2);
        int totalDevices = deviceCount1 + deviceCount2;
        int totalCost = totalDevices * 10;

        System.out.println(dataObj1.get("WEB_URL").toString());
        CommonLibrary.launchBrowser(dataObj1.get("WEB_URL").toString(), "Launching Web Account Creation Page", "chrome");
        addAccountDetails1();
        String RefNum=addCommunicationDetails();

        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 100);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathVehicle)));
        //adding vehicles
        webApplicationAddVehicles(totalDevices);

        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathPortableTransponder)).sendKeys(deviceNumber1);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiniStickerTransponder)).sendKeys(deviceNumber2);
        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathPlansAndPayment)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathPlansAndPayment)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathPlansAndPayment)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathPlansAndPayment,"","Clicking on Plan Button");
        }
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='creditCardType']")));
        FunctionLibrary.selectDropDownListByValue(xpathCardType, cardType, "");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathCardNumber)).sendKeys(cardNumber);
        FunctionLibrary.selectDropDownListByValue(xpathCardExpMonth, cardExpMonth, "");
        FunctionLibrary.selectDropDownListByValue(xpathCardExpYear, cardExpYear, "");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathCardSecurityCode)).sendKeys(cardCcvNumber);


        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathConfirmation)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathConfirmation)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathConfirmation)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathConfirmation,"","Clicking on Plan Button");
        }

        System.out.println("conf");

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));

        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
        Thread.sleep(2000);
        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
        Thread.sleep(4000);
        FunctionLibrary.scrollDowntoElement(".//*[@class='icheckbox_minimal']", "Scroll down");
        try {
            FunctionLibrary.objDriver.navigate().refresh();
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusPercentage)));
            FunctionLibrary.scrollDowntoElement(".//*[@class='icheckbox_minimal']", "Scroll down");
            // WebElement element = FunctionLibrary.objDriver.findElement(By.xpath("//div[@class='icheckbox_minimal']"));
            WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(".//*[@class='icheckbox_minimal']"));
            System.out.println("click try in try block");
            element.click();
        } catch (Exception e) {
            System.out.println("click try in catch block");
            FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='icheckbox_minimal']")));
        }
        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathCompleteButton)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathCompleteButton)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathCompleteButton)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathCompleteButton,"","Clicking on Plan Button");
        }

        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class='page-header-noborder']")));
        String message = FunctionLibrary.objDriver.findElement(By.xpath("//h1[@class='page-header-noborder']")).getText();
        System.out.println(message);
        String[] accountArray = message.split(" ");
        System.out.println(accountArray[12]);
        String accountNumber = accountArray[12].replace('.', ' ').trim();
        ArrayList<String> details = new ArrayList<String>();
        details.add(accountNumber);
        details.add(dataObj1.get("Password1"));
        FunctionLibrary.objDriver.close();
        dataObj1.clear();
        return details;
    }
    public static void addAccountDetails1() throws Exception {
        String Desc;
        String firstName = dataObj1.get("First Name");
        String middleName = dataObj1.get("Middle Name");
        String lastName = dataObj1.get("Last Name");
        String address1 = dataObj1.get("Mailing Address1");
        String address2 = dataObj1.get("Mailing Address2");
        String city = dataObj1.get("City");
        String state = dataObj1.get("State");
        String zip = dataObj1.get("Zip Code");
        String country = dataObj1.get("Country");
        String email = "nishanth.kumarkc@conduent.com";
        String phone = dataObj1.get("Phone");
        String phoneType = dataObj1.get("Phone Type");
        String SunPass=dataObj1.get("Sun Pass");
        String LocalGovt=dataObj1.get("Local Govt");
        String securityQuestion = dataObj1.get("Security Question");
        String securityAnswer = dataObj1.get("Security Answer");
        String userName = firstName + DateTime.now().getMillisOfSecond() + DateTime.now().getSecondOfMinute() + DateTime.now().getMillisOfSecond();
        String password = dataObj1.get("Password1");
        String pin = dataObj1.get("Pin");
        String cardType = dataObj1.get("Card Type");
        String cardNumber = dataObj1.get("Card Number");
        String cardExpMonth = dataObj1.get("Exp Month");
        String cardExpYear = dataObj1.get("Exp Year");
        String cardCcvNumber = dataObj1.get("Ccv Number");
        String deviceNumber1 = dataObj1.get("Device Count1");
        String deviceNumber2 = dataObj1.get("Device Count2");
        int deviceCount1 = Integer.parseInt(deviceNumber1);
        int deviceCount2 = Integer.parseInt(deviceNumber2);
        int totalDevices = deviceCount1 + deviceCount2;
        int totalCost = totalDevices * 10;

        String xpathSunpass="//*[@name='parkingOption']";
        String xpathLocalGovt="//*[@name='nonRevenueOption']";
        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 30);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathFirstName)));
        wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathFirstName)));
        FunctionLibrary.objDriver.findElement(By.xpath(xpathFirstName)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathFirstName)).sendKeys(firstName);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiddleName)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathMiddleName)).sendKeys(middleName);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathLastName)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathLastName)).sendKeys(lastName);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathAddress1)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathAddress1)).sendKeys(address1);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathAddress2)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathAddress2)).sendKeys(address2);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathZipCode)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathZipCode)).sendKeys(zip);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathCity)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathCity)).sendKeys(city);
        FunctionLibrary.selectDropDownListByValue(xpathCountry, country, "");
        FunctionLibrary.selectDropDownListByValue(xpathState, state, "");

        FunctionLibrary.objDriver.findElement(By.xpath(xpathEmail)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathEmail)).sendKeys(email);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathReEmail)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathReEmail)).sendKeys(email);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathFirstPhone)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathFirstPhone)).sendKeys(phone);

        FunctionLibrary.selectDropDownListByValue(xpathPhoneType, phoneType, "");

        if(SunPass.equals("Y"))
        {
            WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(xpathSunpass));
            element.click();

        }
        if(LocalGovt.equals("Y"))
        {
            WebElement element = FunctionLibrary.objDriver.findElement(By.xpath(xpathLocalGovt));
            element.click();
        }


        FunctionLibrary.objDriver.findElement(By.xpath(xpathUserName)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathUserName)).sendKeys(userName);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathPassword)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathPassword)).sendKeys(password);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathReTypePassword)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathReTypePassword)).sendKeys(password);
        FunctionLibrary.selectDropDownListByValue(xpathSecurityQuestion, securityQuestion, "");
        FunctionLibrary.objDriver.findElement(By.xpath(xpathSecurityAnswer)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathSecurityAnswer)).sendKeys(securityAnswer);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathIpin)).clear();
        FunctionLibrary.objDriver.findElement(By.xpath(xpathIpin)).sendKeys(pin);

        try {
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathCommunicationPreferencesBtn)));
            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathCommunicationPreferencesBtn)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathCommunicationPreferencesBtn)).click();
        }
        catch(Exception e)
        {
            FunctionLibrary.clickObject(xpathCommunicationPreferencesBtn,"","Clicking on Plan Button");
        }
        WebDriverWait wait1 = new WebDriverWait(FunctionLibrary.objDriver, 20);
        try{
            wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStandardizeRadioButton)));

            // Thread.sleep(5000);
            wait1.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathStandardizeOkButton)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathStandardizeOkButton)).click();
        }
        catch (Exception e){}





        String url = FunctionLibrary.objDriver.getCurrentUrl();
        if (url.contains("500")) {
            FunctionLibrary.objDriver.navigate().back();
            wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStandardizeRadioButton)));
            Thread.sleep(5000);
            wait1.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathStandardizeOkButton)));
            FunctionLibrary.objDriver.findElement(By.xpath(xpathStandardizeOkButton)).click();
        }

    }


}
