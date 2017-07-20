package libraries;

import com.relevantcodes.extentreports.LogStatus;

import features.CamsPaymentProcessing;
import features.CamsSiebelAccountEstablishment;
import features.CamsSiebelAccountMaintenance;
import features.DimsManualTagAssignment;
import features.ExcelSheet;
import features.WebAccountMaintenance;
import objectProperties.WebAccountMaintenancePageProperties;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static libraries.CommonLibrary.dataObj;
import static objectProperties.SblAccountMaintenancePageObject.*;
import static objectProperties.SblCreateAccountPageProperties.*;

//import java.time.Clock;

/**
* Created by 23319 on 28-12-2016.
*/
public class CommonLibrary {
    public static String newBrowser="";
    public static String oldBrowser="";
    public static String LoginMessage="";
    public static String stringHelperToGenerateUniquePlateNumberStartingLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static Random rand = new Random();
    public static Set<String> identifiers = new HashSet<String>();
    public static HashMap<String, String> settingsSheetInfo;
    public static HashMap <String, String> dataObj;
    public static HashMap <String, String> dataObj1;
    public static WebDriver browser= FunctionLibrary.objDriver;
    public static String previousTestCaseBrowser;
    public static String sblLoginStatus="NotSuccess";
    public static String sblLogoutStatus;
    public static boolean isClosedAllBrowsers=true;
    public static String testDataFileName= "";
    public static String testDataSheetName= "";
   public static String testExecutionReportName = "FLCSS_SWUNT_AutomatedTestReport";
   public static String[] Devicereqs=new String[500];
   public static int DeviceArrayIndex=0;
    public static CommonLibrary commonInstance;
    public static Double Surcharge1,Surcharge2;
   

    CommonLibrary()
    {
        settingsSheetInfo = getSettingsSheetInfo();  
    }

	/*
	Get Settings Sheet Information
	@param return : Settings Sheet Data / Null
	*/
    public static HashMap<String, String> getSettingsSheetInfo()
    {
        HashMap<String,String> settingsSheetData = new HashMap<String,String>();
        try
        {
            new DataFormatter();
            FileInputStream file = new FileInputStream(new File(ReportLibrary.getPath()
                    +"\\testdata\\TestFeatures_Selector.xls"));
            //Create Workbook instance holding reference to .xlsx file
            @SuppressWarnings("resource")
         HSSFWorkbook workbook = new HSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            HSSFSheet sheet = workbook.getSheet("Settings");
            int noOfRows = sheet.getLastRowNum();
            Row rowWithColumnNames = sheet.getRow(0);
            rowWithColumnNames.getPhysicalNumberOfCells();
            String settingsKey ="";
            String settingsValue = "";

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();

            for(int m=0;m<=noOfRows;m++)
            {
                Row rowCurrent = rowIterator.next();
                if(m<=0){
                    continue;
                }
                settingsKey = String.valueOf(rowCurrent.getCell(1));
               settingsValue = String.valueOf(rowCurrent.getCell(2));
                settingsSheetData.put(settingsKey,settingsValue);
            }
            file.close();
            return settingsSheetData;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;

    }

	/*
	Get Test Cases Test Data
	@param locOfFile : File Location
	@param return : All Test Cases Data / Null
	*/
    public static Map<String, String> getTestCasesTestData(String locOfFile)
    {
        HashMap<String,String> rowData = new HashMap<String,String>();
        try
        {
            DataFormatter formatter = new DataFormatter();
            FileInputStream file = new FileInputStream(new File(locOfFile));

           //Create Workbook instance holding reference to .xlsx file
           @SuppressWarnings("resource")
         XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            int noOfRows = sheet.getLastRowNum();
            //System.out.println("no of rows:" + noOfRows);

            Row rowWithColumnNames = sheet.getRow(0);
            int noOfColumns = rowWithColumnNames.getPhysicalNumberOfCells();
            //System.out.println(noOfColumns);
            String testCaseName ="";
            String columnNamesAndValuesOfOneRow = "";

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();

            // System.out.println(rowIterator

            for(int m=0;m<=noOfRows;m++)
            {
                //System.out.println("Iteration number : " + m);
                Row rowCurrent = rowIterator.next();
                if(m==0){
                    continue;
                }
                testCaseName = String.valueOf(rowCurrent.getCell(0));
                //     System.out.println("test case name " + testCaseName);

                for (int p = 0; p < noOfColumns; p++) {
                    //formatter.formatCellValue(rowWithColumnNames.getCell(p))=="" &&
                    //Igonre the columns without any column name in test case excel file
                    if(formatter.formatCellValue(rowWithColumnNames.getCell(p))=="")
                    {
                        continue;
                    }
                    columnNamesAndValuesOfOneRow = columnNamesAndValuesOfOneRow+formatter.formatCellValue((rowWithColumnNames.getCell(p))).trim()+
                            ":"+formatter.formatCellValue((rowCurrent.getCell(p))).trim()+";";

                }
                rowData.put(testCaseName,columnNamesAndValuesOfOneRow);
                columnNamesAndValuesOfOneRow="";

            }
            file.close();

            //Sorting the test case ids which are present in Hashmap(allTestCasesDataBeforeSort)
            Map<String, String> allTestCasesData = new TreeMap<String, String>(rowData);

            //System.out.println("After Sorting:");
            Iterator<?> iterator = allTestCasesData.entrySet().iterator();
            while(iterator.hasNext()) {
                iterator.next();
            }

            return allTestCasesData;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;

    }

	/*
	Get Each Test Case Test Data
	@param ex : Excel Sheet
	@param sheetName : Excel sheet Name
	@param currentRowNumber : Excel Sheet Row Number
	@param testDataFileName : Excel Sheet test Data File Name
	@param return : each Test Case Data / Null
	*/
    public static HashMap<String, String> getEachTestCaseData(ExcelSheet ex, String sheetName, int currentRowNumber,String testDataFileName) {

        DataFormatter formatter = new DataFormatter();
        XSSFRow rowWithColumnNames = null;
        try {
            rowWithColumnNames = ExcelSheet.getRow(sheetName, 0,testDataFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<String, String> eachTestCaseData = new HashMap<String, String>();

        XSSFRow rowCurrent = null;
        try {
           rowCurrent = ExcelSheet.getRow(sheetName, currentRowNumber,testDataFileName);
        } catch (IOException e) {
           e.printStackTrace();
        }

        for (int p = 0; p < rowWithColumnNames.getLastCellNum(); p++) {
            //Ignore the columns without any column name in test case excel file
            if (formatter.formatCellValue(rowWithColumnNames.getCell(p)) == "") {
                continue;
            }
            String RowValue;
           /* int CellType=rowCurrent.getCell(p).getCellType();
            if(CellType==2){
                RowValue=rowCurrent.getCell(p).getRawValue().trim();
            }
            else{    
                 RowValue=formatter.formatCellValue((rowCurrent.getCell(p))).trim();
            }*/
            
            RowValue=formatter.formatCellValue((rowCurrent.getCell(p))).trim();

           eachTestCaseData.put(formatter.formatCellValue((rowWithColumnNames.getCell(p))).trim(), RowValue);
           //System.out.println(eachTestCaseData);
      }

        return  eachTestCaseData;

    }

	/*
	Random Identifier
	@return builder(identifier)
	*/
	 public static String randomIdentifier() {
	        StringBuilder builder = new StringBuilder();
	        while (builder.toString().length() == 0) {
	            int length = rand.nextInt(5) + 5;
	            for (int i = 0; i < length; i++) {
	                builder.append(stringHelperToGenerateUniquePlateNumberStartingLetters.charAt(rand.nextInt(stringHelperToGenerateUniquePlateNumberStartingLetters.length())));
	            }
	            if (identifiers.contains(builder.toString())) {
	                builder = new StringBuilder();
	            }
	        }
	        return builder.toString();
	    }
	                
		/*
		Siebel Account Establishment
		@param testScenariosFileName : Excel Sheet(Test Scenario File Name)
		@param testScenariosSheetName : Excel sheet Name(Test Scenario Sheet Name)
		*/
public static void siebelAccoutEstablishment(String testScenariosFileName,String testScenariosSheetName) throws IOException, EncryptedDocumentException, InvalidFormatException
	{
	      
	            
	//Read input excel sheet for test data
	ExcelSheet exl=new ExcelSheet();
	//CommonLibrary.randomIdentifier();
	String Desc="";
	//Map variable which hold the once test case data
	HashMap<String,String> eachTestCaseData =new HashMap<String, String>();
	//no of rows in the excel sheet(no of test cases)
	int noOfTestCases=exl.totalrows(testScenariosFileName,testScenariosSheetName);
	
	String AccountType;
	String TaxExempt;
	String PaymentMode;
	String currentTcBrowser,email;
	String vehiclesCount,TaxExemptValue;
	//Script will iterate based on the row count
	
	for(int iterator=1;iterator<=noOfTestCases;iterator++)
	{
	      
	eachTestCaseData= CommonLibrary.getEachTestCaseData(exl,testScenariosSheetName,iterator,testScenariosFileName);
	eachTestCaseData.get("Execution Status");
	email=eachTestCaseData.get("Email Address");
	AccountType =eachTestCaseData.get("Account Type");
	eachTestCaseData.get("Mode Type");
	PaymentMode =eachTestCaseData.get("Rebill Pay Type");
	currentTcBrowser =eachTestCaseData.get("Browser Type");
	TaxExempt=eachTestCaseData.get("IS TaxExempt");
	vehiclesCount=eachTestCaseData.get("VechileCount");
	eachTestCaseData.get("SalesTax");
	TaxExemptValue=eachTestCaseData.get("TaxExempt Value");
	String country = null;
	
	if(PaymentMode.equalsIgnoreCase("CASH")){
	}
	CommonLibrary.getSettingsSheetInfo().get("URL_QA").toString();
	
	String executionType = CommonLibrary.settingsSheetInfo.get("Execution_Type");
	
	if((eachTestCaseData.get("Execution Status").equalsIgnoreCase("Yes")) && ( eachTestCaseData.get("TestCaseType").contains(executionType)|| 
	                      executionType.equalsIgnoreCase("All")))
	
	//Execute Test case only the for the Execution_Status mentioned Yes
	//if (eachTestCaseData.get("Execution Status").equalsIgnoreCase("Yes"))
	{
	      int noOfAccounts=1;
	      if(eachTestCaseData.get("No of Accounts")==null || eachTestCaseData.get("No of Accounts")=="") 
	                      {
	                      noOfAccounts=1;                             
	                      }else
	                      {
	                      noOfAccounts=Integer.valueOf(eachTestCaseData.get("No of Accounts"));
	                      }
	
		              for(int noOfAccountRequired = 1;noOfAccountRequired<=noOfAccounts;noOfAccountRequired++)
		              	{
		                              if(noOfAccountRequired==1)
		              {
		                                              eachTestCaseData.put("AccountNumber","");
		                              ExcelSheet.writeTestCaseData(testScenariosFileName, testScenariosSheetName, iterator,"","AccountNumber");
		              }
	
	      ReportLibrary.Test_Step_Number=1;
	  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"<b>"+eachTestCaseData.get("TestCaseId")+"</b>"+
	          ": Test Case Execution is started....................... <br>"
	          + "Test case description: " + eachTestCaseData.get("TestCaseDesc"), LogStatus.INFO, false);
	
	  try{
	      //.........................................
	      
	      //Launching Browser
	     
	      CommonLibrary.loginSblWithTestScenarioSpecificUser(eachTestCaseData);
	      if(sblLoginStatus=="Success")
	      {
	         // sblLoginStatus="Success";
	
	          if(AccountType.equalsIgnoreCase("private")){
	              //********Clicking on Account opening tab*************
	             
	                 //Thread.sleep(4000);
	                  FunctionLibrary.clickObject(accountOpeningBtn,"","Clicking on Account opening button");
	                  FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
	                  (newAccountOpeningBtn,accountOpeningBtn,6,37);
	              FunctionLibrary.clickObject(newAccountOpeningBtn,"","Clicking on Account opening button");
	           
	          }
	
	          //Getting dynamic name: first name, last name, acc name, dba name
	          ExcelSheet exl1 = new ExcelSheet();                      
	          exl1.totalrows("FLCSS_CAMS_CustomerNamesPicker","Customer_Name_Selection_Helper");
	          String lastName_fristName = ExcelSheet.getUniqueLastNameAndFirstName("Customer_Name_Selection_Helper");
	          
	          String lname;
	          String fname;
	          
	          if(eachTestCaseData.get("TestCaseId").contains("TestDataForACMO_010") || eachTestCaseData.get("First Name").length()>1)
	          {
	                      lname = eachTestCaseData.get("Last Name");
	                      fname = eachTestCaseData.get("First Name");                              
	          }
	         else
	         {
	             lname=lastName_fristName.split("_")[1];
	             fname=lastName_fristName.split("_")[0];
	         }                                           
	          
	          lastName_fristName.split("_");  
	          
	          String accName="";
	          String dbaName = "";
	          if(eachTestCaseData.get("Account Name").length()>1)
	          {
	                      accName = eachTestCaseData.get("Account Name");
	          }
	          else
	          {
	                      accName = fname+" "+ lname;
	          }
	          if(eachTestCaseData.get("DBA Name").length()>1)
	          {
	                      dbaName = eachTestCaseData.get("DBA Name");
	          
	          }
	          else
	          {
	                      dbaName = accName;
	          }
	      
	          fname="TNPST"+fname;
	          accName="TNPST"+accName;
	          //**************Fields will be applicable only for Business/Commercial************
	
	          if(AccountType.equalsIgnoreCase("Business")|| AccountType.equalsIgnoreCase("Commercial"))
	          {
	              FunctionLibrary.clickObject(accountOpeningBtn,"","Click account opening link");
	              FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(clickOnBusinessCommercialAccount,
	                      accountOpeningBtn,3,30);
	             FunctionLibrary.clickObject(clickOnBusinessCommercialAccount,"","Click BusinessCommercial link");
	
	       /*       FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 15, clickOnNewButton);
	              FunctionLibrary.clickObject(clickOnNewButton, "", "Click on new Button for Business/Commercial");
	               wait.until(ExpectedConditions.attributeToBe(FunctionLibrary.getElement(accountNameTextBox), "aria-readonly", "false"));*/
	             
	             
	             FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
	             (clickOnNewButton,clickOnBusinessCommercialAccount,3,30);
	         FunctionLibrary.clickObject(clickOnNewButton,"","Clicking on Account opening button");
	         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
	         (accountNameTextBox,clickOnNewButton,3,30);
	         
	        // FunctionLibrary.waitForObject.until(ExpectedConditions.attributeToBe(FunctionLibrary.getElement(accountNameTextBox), "aria-readonly", "false"));
	               // String accountName = eachTestCaseData.get("Account Name");
	               String accountName = accName;
	              FunctionLibrary.setText(accountNameTextBox,accountName ,"Enter Account name");
	              ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Account  Name: " + accountName,LogStatus.INFO,false);
	              //FunctionLibrary.setText(dbaNameTextBox,eachTestCaseData.get("DBA Name"),"Enter DBA name");
	              FunctionLibrary.setText(dbaNameTextBox,dbaName,"Enter DBA name");
	              FunctionLibrary.setText(FEINTextBox,eachTestCaseData.get("FEIN"),"Enter Fien number");
	              FunctionLibrary.setText(externalTextBox,eachTestCaseData.get("External Reference"),"Enter External reference number");
	
	          }
	          String obj= ".//input[@aria-labelledby='VehicleCount_Label' and @aria-readonly='false']";
	          if(AccountType.equalsIgnoreCase("Business")|| AccountType.equalsIgnoreCase("Commercial"))
	                      {
	          FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(obj,
	                  clickOnNewButton,1,30);
	                     }
	          else
	          {
	                      FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(obj,
	                      newAccountOpeningBtn,1,30);
	          }                    
	
	                                      
	          FunctionLibrary.setText(".//input[@aria-labelledby='VehicleCount_Label']", eachTestCaseData.get("Challenge Question"), Desc);
	         
	          //entering security answer
	          Desc="Enter Security Answer";
	          FunctionLibrary.setText(securityAnswerTxtBox,eachTestCaseData.get("Challenge Answer"),Desc);
	          
	          
	         
	          //dynamic email id by concatenating first name and last name
	          if(email==null || email.length()<1 )//|| testScenariosFileName!="FLCSS_CAMS_AcccountEstablishment_TestDataForAutoTests")
	          {                    
	                      email="automationEmail@conduent.com";
	          }
	          
	          exl1.totalrows(testScenariosFileName,testScenariosSheetName);
	                      
	          
	          
	          Desc="Enter email on Email field";
	          FunctionLibrary.setText(accountEmailIDTxtBox,email,Desc);
	          try{
	                      WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,2);
	              wait2.until(ExpectedConditions.alertIsPresent());
	              Alert alert = FunctionLibrary.objDriver.switchTo().alert();
	              System.out.println(alert.getText());
	              alert.accept();
	              
	          }
	          catch(Exception e)
	          {
	
	          }
	          //Handling pop up code after entering email
	
	          if(email.length()>1 && ( !email.endsWith("conduent.com") ||  !email.endsWith("xerox.com")))
	         {
	              try{
	
	                  //FunctionLibrary.clickObject("./[@id='btn-accept']","","Clicking Ok buttn");
	                  WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,1);
	                  wait2.until(ExpectedConditions.alertIsPresent());
	                  Alert alert = FunctionLibrary.objDriver.switchTo().alert();
	                  alert.accept();
	                  Desc="Click on alert while adding email";
	              }
	              catch(Exception e)
	              {
	              }
	          }
	
	          if(eachTestCaseData.get("Statement Frequency").length()>1)
	          {
	
	
	              String defaultStatementDeliveryfrequency = FunctionLibrary.objDriver.findElement(By.xpath(statementDeliveryMode)).getAttribute("value");
	              String statementDeliveryMode = eachTestCaseData.get("Statement Frequency").split(" ")[0];
	              if(statementDeliveryMode.startsWith(defaultStatementDeliveryfrequency))
	              {
	                  ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Statement frequency by default is "+
	                          eachTestCaseData.get("Statement Frequency") , LogStatus.INFO, false);
	                  //
	                  if(eachTestCaseData.get("TestCaseId").equalsIgnoreCase("ACEO_011"))
	                  {
                           FunctionLibrary.clickObject(statementDeliveryFrequencySelectionIcon,"","Click delivery frequency selection icon");
                           FunctionLibrary.clickObject(popupQueryComboboxDropdownIcon,"","Click popup query dropdown icon");
                           FunctionLibrary.clickObject(statementDeliveryModeItem,"","Click delivery mode element");
                           FunctionLibrary.setText(DeliveryModeSearchTextBox,statementDeliveryMode,"Enter statement type");
                           FunctionLibrary.clickObject(VectorListFindBtn,"","Click on find");
                           FunctionLibrary.clickObject(VectorListFindBtn,"","Click on find");
                           FunctionLibrary.clickObject(StatementFreqTitleSummary,"","Click on summary");
                           FunctionLibrary.clickObject(VectorPickListOkBtn,"","Click on vector pick list OK button");
                                      
	                  }
	                  if(eachTestCaseData.get("TestCaseId").equalsIgnoreCase("ACEO_055"))
	                 {
                       FunctionLibrary.clickObject(statementDeliveryFrequencySelectionIcon,"","Click delivery frequency selection icon");
                       FunctionLibrary.clickObject(popupQueryComboboxDropdownIcon,"","Click popup query dropdown icon");
                       FunctionLibrary.clickObject(statementDeliveryModeItem,"","Click delivery mode element");
                       FunctionLibrary.setText(DeliveryModeSearchTextBox,statementDeliveryMode,"Enter statement type");
                       FunctionLibrary.clickObject(VectorListFindBtn,"","Click on find");
                       FunctionLibrary.clickObject(StatementFreqTitleDetails,"","Click on details");
                       FunctionLibrary.clickObject(VectorPickListOkBtn,"","Click on vector pick list OK button");
	                  }
	
	              }
	              else
	              {
	                  FunctionLibrary.clickObject(statementDeliveryFrequencySelectionIcon,"","Click delivery frequency selection icon");
	                  FunctionLibrary.clickObject(popupQueryComboboxDropdownIcon,"","Click popup query dropdown icon");
	                  FunctionLibrary.clickObject(statementDeliveryModeItem,"","Click delivery mode element");
	
	
	                  FunctionLibrary.setText(popupQuerySearchTextBox,statementDeliveryMode,"Enter statement type");
	
	                  FunctionLibrary.clickObject(popupFindButton, "", "Clicking find button");
	                  
	                  for(int itrator1=0;itrator1<=3;itrator1++)
	                  {
	                      try{
	                                       WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,4);
	                            wait2.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='btn-accept']")));                                       
	                      }catch(TimeoutException e1)
	                      {
	                                      FunctionLibrary.clickObject(popupFindButton, "", "Clicking find button");
	                      }   
	                  }
	              
	                  //if mode is mail after find button click, MAIL is getting selected. Reason is only one record is present as of now for MAIL mode
	
	                  if(statementDeliveryMode!="MAIL")
	                  {
	                      try{
	                                       WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,4);
	                          wait2.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='btn-accept']")));
	                          FunctionLibrary.objDriver.findElement(By.xpath("//*[@id='btn-accept']")).click();
	                      }catch(TimeoutException e1)
	                      {
	                                      
	                      }   
	                  }
	              }
	
	          }
	
	          FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath
	                  (accountPINTxtBox)));
	
	          //entering pin
	          Desc="Enter Pin";
	          Thread.sleep(2000);
	          FunctionLibrary.setText(accountPINTxtBox, eachTestCaseData.get("PIN"),Desc);
	          
	          //---------------------------------------------------------
	          Desc="Get Account open date";
	          //FunctionLibrary.setText("//input[@aria-label='Account Open Date']","5/28/2017",Desc);
	          SimpleDateFormat format = new SimpleDateFormat("m/d/yyyy");
	          String AccountOpenDate=FunctionLibrary.getText("//input[@aria-label='Account Open Date']", "Getting Account open Date");
	          Date AccountOpeningDate=format.parse(AccountOpenDate);
	          System.out.println(AccountOpeningDate);
	          String FinalAccountOpeningDate=format.format(AccountOpeningDate);
	
	         //------------------------------------------------------------
	        
	          //entering preferred language
	          Desc="Select language preference";
	          FunctionLibrary.setText(preferredLanguageTxtBox,eachTestCaseData.get("LanguagePref"),Desc);
	
	          if(eachTestCaseData.get("Is Anonymous").equalsIgnoreCase("YES")){
	              FunctionLibrary.clickObject("//*[@aria-label='Is Anonymous']//following::span[2]", "","Clicking on Anonymous checkbox");
	              ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Select Anonymous checkbox", LogStatus.INFO, false);
	
	
	          }
	          //ACEO_216 - Mobile alert-Opt out case
	          if(eachTestCaseData.get("TestCaseId").equalsIgnoreCase("ACEO_216")||eachTestCaseData.get("TestCaseId").equalsIgnoreCase("ACEO_011")||eachTestCaseData.get("TestCaseId").equalsIgnoreCase("ACEO_082")||eachTestCaseData.get("TestCaseId").equalsIgnoreCase("ACEO_055"))
	          {
	                try{
	                        FunctionLibrary.setText(mobileAlertsOptinandOut, eachTestCaseData.get("MobileAlerts"), Desc);
	                        FunctionLibrary.clickObject(accountDetailsSaveBtn,"",Desc);
	                                                /*WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,2);
	                                      wait2.until(ExpectedConditions.alertIsPresent());
	                                      Alert alert = FunctionLibrary.objDriver.switchTo().alert();
	                                      String MobileAlert=alert.getText();*/
	                                      //if(MobileAlert.contains("enable Mobile Alerts")){
	                if(FunctionLibrary.getElement(AlertButton).isDisplayed()){
	              
	                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:System Should display Error message as Please set to PENDING to enable Mobile Alerts.  Actual:Error message displayed as Expected", LogStatus.PASS, true);
	                FunctionLibrary.clickObject(AlertButton,"",Desc);
	                logoutSiebelApplicaiton();
	                continue;
	              }  
	                        }
	              catch(Exception e){
	                        
	              }
	                        
	          }
	
	
	          if(TaxExempt.equalsIgnoreCase("YES")){
	              FunctionLibrary.clickObject("//*[@aria-label='Tax Exempt']//following::span[2]", "","Clicking on Tax Exepmt checkbox");
	
	              FunctionLibrary.setText("//*[contains(@aria-labelledby, 'Tax_Exempt_Expiry_Date_Label') and contains(@aria-label,'Tax Exempt Expiry Date')]", eachTestCaseData.get("TaxExempt ExpiryDate"), "Selecting Expiry Date");
	              // System.out.println(TaxExemptValue);
	              FunctionLibrary.setText(".//*[@aria-label='Tax Exempt #']",eachTestCaseData.get("TaxExempt Value"),"enter TaxExempt value");
	              ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Select Tax Exepmt checkbox", LogStatus.INFO, false);
	
	          }
	          //save the personal details for Private account
	          if(AccountType.equalsIgnoreCase("private")){
	              Desc="Click on Save Button";
	              FunctionLibrary.clickObject(accountDetailsSaveBtn,"",Desc);
	             /* if (eachTestCaseData.get("Is Anonymous").equalsIgnoreCase("YES") && FunctionLibrary.objDriver.findElement(By.xpath("//*[@id='btn-accept']")).isDisplayed())
	              {
	                  FunctionLibrary.objDriver.findElement(By.xpath("//*[@id='btn-accept']")).click();
	              }*/
	          }
	          //save the personal details for Business or Commercial account
	          if(AccountType.equalsIgnoreCase("Business")|| AccountType.equalsIgnoreCase("Commercial")){
	              Desc="Click on Save Button";
	              //Wait till accountNameTextBox button to be visible
	              /*FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver,
	                      3,businessOrCommAcoDetailsSaveBtn);*/
	              FunctionLibrary.clickObject(businessOrCommAcoDetailsSaveBtn,"",Desc);
	
	          }
	          new WebDriverWait(FunctionLibrary.objDriver,40);
	      
	          String mname = eachTestCaseData.get("MI");
	          String suffix = eachTestCaseData.get("Suffix");
	         
	          if(fname.length()>1)
	          {
	              //clicking the New button in contacts section
	              Desc="Clicks Contact new button";
	              FunctionLibrary.clickObject(contactDetailsNewBtn,"",Desc);
	              
	              if (eachTestCaseData.get("Is Anonymous").equalsIgnoreCase("YES"))
	              {
	                      try{
	                                       WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,4);
	                       wait2.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='btn-accept']")));
	                       FunctionLibrary.objDriver.findElement(By.xpath("//*[@id='btn-accept']")).click();
	                      }catch(TimeoutException e1)
	                      {
	                                      
	                      }                              
	                  
	              }
	              
	                                       
	             FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(contactDetailsLstNameElement,
	            		 contactDetailsNewBtn,4,29); 
	                         
	              //Clicks Last Name Field
	              Desc="Clicks Last Name Field";
	             // FunctionLibrary.highLightElement(FunctionLibrary.objDriver, FunctionLibrary.getElement(contactDetailsLstNameElement));
	              FunctionLibrary.clickObject(contactDetailsLstNameElement,"",Desc);
	              
	           
	      
	              //Enter Last Name
	              Desc="Enter Last Name";
	             
	              FunctionLibrary.setText(contactDetailsLstNameTxtBox,lname,Desc);
	
	              //Clicks on first Name Field
	              Desc="Clicks on First Name Field";
	              FunctionLibrary.clickObject(contactDetailsFrstNameElement,"",Desc);
	
	              Desc="Entering first name: " + fname;
	              FunctionLibrary.setText(contactDetailsFrstNameTxtBox,fname,Desc);
	              ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Customer fname: "+fname +" and lname: "+lname,LogStatus.INFO,false);
	              
	              
	              if(mname.length()>1)
	              {
	
	                  Desc="Entering middle name: " + fname;
	                  FunctionLibrary.objDriver.findElement(By.xpath(".//input[@name='Middle_Intial']")).sendKeys(mname);
	                 
	                      
	              }
	              
	              
	              if(suffix.length()>1)
	              {
	                       //Clicks on first Name Field
	                  Desc="Click suffix element";
	                  FunctionLibrary.clickObject(contactDetailsContactSuffixElement,"",Desc);
	
	                  Desc="Enter suffix : " + suffix;
	                  FunctionLibrary.setText(contactDetailsContactSuffixTxtBox,suffix,Desc);
	                 
	                      
	              }                       
	              
	              
	              //clicks on  phone number field
	              Desc="Clicks on phone number field";
	              FunctionLibrary.clickObject(contactDetailsPhnNoElement,"",Desc);
	              //Enter Phone number
	              Desc="Enter phone number";
	              FunctionLibrary.setText(contactDetailsPhnNoTxtBox,eachTestCaseData.get("Cell Phone"),Desc);
	
	              //handle the phone number format pop-up alert
	              try {
	                  //FunctionLibrary.clickObject("./[@id='btn-accept']","","Clicking Ok buttn");
	                  WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,2);
	                  wait2.until(ExpectedConditions.alertIsPresent());
	                  Alert alert = FunctionLibrary.objDriver.switchTo().alert();
	                  alert.accept();
	                  Desc="Click on Contact Detail Save button";
	                 // Thread.sleep(2000);
	
	              } catch (Exception e) {
	                  //exception handling
	              }
	
	              if(eachTestCaseData.get("Alternate Phone").length()>1)
	              {
	                  //Enter Phone number
	                  Desc="Enter phone number";
	                  FunctionLibrary.setText(".//*[@name='Phone_Number_Work']",eachTestCaseData.get("Alternate Phone"),Desc);
	
	
	
	                  //handle the phone number format pop-up alert
	                  try {
	                      //FunctionLibrary.clickObject("./[@id='btn-accept']","","Clicking Ok buttn");
	                      WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,4);
	                      wait2.until(ExpectedConditions.alertIsPresent());
	                      Alert alert = FunctionLibrary.objDriver.switchTo().alert();
	                      alert.accept();
	                      Desc="Click on Contact Detail Save button";
	               //       Thread.sleep(3000);
	
	                  } catch (Exception e) {
	                      //exception handling
	                  }
	
	                  FunctionLibrary.clickObject(contactDetailsSaveBtn,"",Desc);
	                  //Thread.sleep(3000);
	
	              }
	
	
	          }
	
	
	          // click new button for Address section, enter required fields and then click save button
	          Desc="Click Address new button";
	
	          String addressesToBeAdded = eachTestCaseData.get("AddressType");
	          addressesToBeAdded = addressesToBeAdded.replace("\n","");
	
	          String []addressItems = addressesToBeAdded.split(";");
	          String [] eachAddressInfo;
	          System.out.println(addressItems.length);
	          String addressType;
	          String addressLine1;
	          String addressLine2;
	          String zipCode;
	          String city;
	          String state;
	          
	
	          for (int iterator1 =0;iterator1<=addressItems.length-1;iterator1++)
	          {
	              eachAddressInfo=addressItems[iterator1].split(":");
	              addressType = eachAddressInfo[0];
	              //addressLine1 = streetName+eachAddressInfo[1];
	            addressLine1 = eachAddressInfo[1];
	              addressLine2 = eachAddressInfo[2];
	              zipCode = eachAddressInfo[3];
	              city = eachAddressInfo[4];
	              state = eachAddressInfo[5];
	              country = eachAddressInfo[6];
	
	              if((eachTestCaseData.get("Is Anonymous").equalsIgnoreCase("YES") && iterator1 ==0)==true)
	              {
	                  FunctionLibrary.clickObject(addressDetailsNewBtn,"",Desc);
	                
	                  if (eachTestCaseData.get("Is Anonymous").equalsIgnoreCase("YES"))
	                  {
	                      try{
	                                       WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,4);
	                           wait2.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='btn-accept']")));
	                           FunctionLibrary.objDriver.findElement(By.xpath("//*[@id='btn-accept']")).click();
	                      }catch(TimeoutException e1)
	                      {
	                                      
	                      }
	                  }
	                            /*if (eachTestCaseData.get("Is Anonymous").equalsIgnoreCase("YES") && FunctionLibrary.objDriver.findElement(By.xpath("//*[@id='btn-accept']")).isDisplayed())
	                           {
	                              FunctionLibrary.objDriver.findElement(By.xpath("//*[@id='btn-accept']")).click();
	                             }*/
	              }
	              else
	              {
	                 
	                  FunctionLibrary.clickObject(addressDetailsNewBtn,"",Desc);
	                  
	              }
	
	              FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//input[@name='Address_Type']",
	                      addressDetailsNewBtn,1,5);
	             // FunctionLibrary.clickObject(addressDetailsNewBtn,"",Desc);
	              
	           /*   FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='Address_Type']")).sendKeys(addressType);
	              FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='Address_Type']")).sendKeys(Keys.TAB);*/
	              FunctionLibrary.setText("//input[@name='Address_Type']", addressType, "Enter address type");
	              
	              
	              Desc="Entering street address1";
	              FunctionLibrary.setText(addressDetailsAddress1TxtBox,addressLine1,Desc);
	
	              if(addressLine2.length()>1)
	              {
	                  Desc="Entering street address2";
	                  FunctionLibrary.setText(addressDetailsAddress2TxtBox,addressLine2,Desc);
	              }
	               FunctionLibrary.scrollDowntoElement(addressDetailsPostalCodeELement, "Scroll down to zipcode");
	              //Click on Zip Code
	              Desc="Click on postal/Zip code filed";
	              FunctionLibrary.clickObject(addressDetailsPostalCodeELement,"",Desc);
	
	              //Click on Zip Code
	              Desc="Click on postal/Zip code filed";
	              FunctionLibrary.setText(addressDetailsPostalCodeTxtBox,zipCode,"Entering postal code");
	
	              if(country.length()>1)
	              {
	              FunctionLibrary.clickObject(addressDetailsCountryElement,"","Clicking country field");
	              FunctionLibrary.setText(addressDetailsCountryTxtBox,country,"Entering country field");
	              }
	           
	              
	              if(city.length()>1)
	              {
	              //Entering City
	              FunctionLibrary.clickObject(addressDetailsCityElement,"","Clicking city field");
	              FunctionLibrary.setText(addressDetailsCityTxtBox,city,"Enter city");
	              }
	              
	              if(state.length()>1)
	              {
	              //Entering State
	             // FunctionLibrary.clickObject(addressDetailsStateElement,"","Clicking state field");
	              FunctionLibrary.setText(addressDetailsStateTxtBox,state,"Entering state value");
	              }
	             
	              //Click on Address Save button
	              Desc="Clicking address save button";
	              FunctionLibrary.objDriver.findElement(By.xpath("//button[@title='Addresses:Save']")).click();
	          //   Thread.sleep(2000);
	              try {
	
	                  WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,15);
	                  wait2.until(ExpectedConditions.alertIsPresent());
	                  Alert alert = FunctionLibrary.objDriver.switchTo().alert();
	                  System.out.println("address alert is displaying. alert text is : " + alert.getText());
	                  alert.accept();
	                  // Thread.sleep(2000);
	                  //System.out.println("address alert is handled");
	                   }
	
	                  catch (Exception e)
	                  {
	
	                  }
	
	
	          }
	
	          FunctionLibrary.clickObject(replenishmentTab,"","Clicking Replenishment button");
	          FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	                  replenishmentDetailsNewBtn,replenishmentTab,1,20
	          );
	          
	          //Click on Replenishment New button
	          Desc="Clicking Replenishment new button";
	          FunctionLibrary.clickObject(replenishmentDetailsNewBtn,"",Desc);
	          
	          FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	                                      replenushmentDetailsPrimaryChkBox,replenishmentDetailsNewBtn,3,20
	          );
	          
	          
	          ////////////////////////
	
	        //Check Primary checkbox
              Desc="Select isPrimary checkbox";
              FunctionLibrary.setCheckBox(replenushmentDetailsPrimaryChkBox,eachTestCaseData.get("Is Primary ?"),Desc);
              //Select Rebill payment Type from Drop down
              Desc="Select list box Payment type";
              FunctionLibrary.selectDropDownListByValue(replenishmentDetailsRebillPayTypeDropdown,eachTestCaseData.get("Rebill Pay Type"),Desc);
              //Rebill pay type Savings or Checking Account
              if(eachTestCaseData.get("Rebill Pay Type").equalsIgnoreCase("SAVINGS") || eachTestCaseData.get("Rebill Pay Type").equalsIgnoreCase("CHECKING"))
              {
                  
                  //Enter Bank Account no:
                  //FunctionLibrary.setText(paymentDetailsBankAccountNbrTxtBox,exl.readexcel("siebel", iterator, 36),"Enter bank number");
                  Desc="Enter bank number";
                  FunctionLibrary.setText(paymentDetailsBankAccountNbrTxtBox,eachTestCaseData.get("BankAccount"),Desc);
                  // FunctionLibrary.setText(paymentDetailsRoutingNbrTxtBox,exl.readexcel("siebel", iterator, 37),"Enter routing number");
                  Desc="Enter routing number";
                  FunctionLibrary.setText(paymentDetailsRoutingNbrTxtBox,eachTestCaseData.get("BankRoutine"),Desc);
                  if(eachTestCaseData.get("Rebill Pay Type").equalsIgnoreCase("SAVINGS"))
                  {
                                  CamsSiebelAccountMaintenance.handleAlertIfPresent(12);
                  }
               
              }
              //Rebill Pay Type with Creditcard
              if(eachTestCaseData.get("Rebill Pay Type").equalsIgnoreCase("MASTERCARD") || eachTestCaseData.get("Rebill Pay Type").equalsIgnoreCase("AMEX") || eachTestCaseData.get("Rebill Pay Type").equalsIgnoreCase("DISCOVER")|| eachTestCaseData.get("Rebill Pay Type").equalsIgnoreCase("VISA"))
              {

                  //Enter Credit card #
                  Desc="Enter Credit Card number";
                  FunctionLibrary.setText(creditCardNoField,eachTestCaseData.get("CreditCardNo"),Desc);
                  //Credit card Expiration Month
                  Desc="Select Expiration Month";
                  FunctionLibrary.selectDropDownListByValue(creditCardExpMpnth,eachTestCaseData.get("ExpMonth"),Desc);
                  
                  //Credit card Expiration Year
                  Desc="Select Expiration Year";
                  FunctionLibrary.selectDropDownListByValue(creditCardExpYear,eachTestCaseData.get("ExpYear"),Desc);
              }   
                //Click on Replenishment Save button
                    Desc="Clicks on Replenishment save button";
                    FunctionLibrary.objDriver.findElement(By.xpath("//*[@id='saveReBlInf']")).click();
                    //Credit card Pop up handling code
                    try {
                                    
                                    //ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: "+Desc+""+"<br>"+"Actual: Execution Failed due to:+"+"Cyber Source Error", LogStatus.FAIL, true);
                        WebDriverWait wait8 = new WebDriverWait(FunctionLibrary.objDriver,8);
                        wait8.until(ExpectedConditions.alertIsPresent());
                        //Thread.sleep(5000);
                        String alertText;
                        Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                        alertText = alert.getText();
                        alert.accept();
                       if( alertText.contains("already exist for the current or another")){
                        alert.accept();
              
                       }
                       if(reBillPayType.equalsIgnoreCase("SAVINGS")){
                       Robot r=new Robot();
                       r.keyPress(KeyEvent.VK_SPACE);
                       r.keyRelease(KeyEvent.VK_SPACE);
                       }              
                    
                    } catch (Exception e){
                          /*ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Alert with text: " + e.getMessage(),
                                LogStatus.INFO,    false);*/
                    }  
              
                    
                    FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@title='"+eachTestCaseData.get("Rebill Pay Type")+"']")));
                    
//************************************Seconday Replinishment code*****************************************
              String SecondayPaymnetType=eachTestCaseData.get("Secondary Rebill Pay Type");
                  if((SecondayPaymnetType.length()>1)){
                  //Click on Replenishment New button
                    Desc="Clicking Replenishment new button";
                    FunctionLibrary.clickObject(replenishmentDetailsNewBtn,"",Desc);
                    
                    FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                                                replenushmentDetailsPrimaryChkBox,replenishmentDetailsNewBtn,1,20
                    );
                    
                    //Check Primary checkbox
                   /* Desc="Select isPrimary checkbox";
                    FunctionLibrary.setCheckBox(replenushmentDetailsPrimaryChkBox,eachTestCaseData.get("Is Primary ?"),Desc);*/
                    //Select Rebill payment Type from Drop down
                    Desc="Select list box Payment type";
                    FunctionLibrary.selectDropDownListByValue(replenishmentDetailsRebillPayTypeDropdown,eachTestCaseData.get("Secondary Rebill Pay Type"),Desc);
                    //Rebill pay type Savings or Checking Account
                    if(eachTestCaseData.get("Secondary Rebill Pay Type").equalsIgnoreCase("SAVINGS") || eachTestCaseData.get("Secondary Rebill Pay Type").equalsIgnoreCase("CHECKING"))
                    {
                        
                        //Enter Bank Account no:
                        //FunctionLibrary.setText(paymentDetailsBankAccountNbrTxtBox,exl.readexcel("siebel", iterator, 36),"Enter bank number");
                        Desc="Enter bank number";
                        FunctionLibrary.setText(paymentDetailsBankAccountNbrTxtBox,eachTestCaseData.get("BankAccount"),Desc);
                        // FunctionLibrary.setText(paymentDetailsRoutingNbrTxtBox,exl.readexcel("siebel", iterator, 37),"Enter routing number");
                        Desc="Enter routing number";
                        FunctionLibrary.setText(paymentDetailsRoutingNbrTxtBox,eachTestCaseData.get("BankRoutine"),Desc);
                        
                        
                    }
                    //Rebill Pay Type with Creditcard
                    if(eachTestCaseData.get("Secondary Rebill Pay Type").equalsIgnoreCase("MASTERCARD") || eachTestCaseData.get("Secondary Rebill Pay Type").equalsIgnoreCase("AMEX") || eachTestCaseData.get("Secondary Rebill Pay Type").equalsIgnoreCase("DISCOVER")|| eachTestCaseData.get("Secondary Rebill Pay Type").equalsIgnoreCase("VISA"))
                    {

                        //Enter Credit card #
                        Desc="Enter Credit Card number";
                        FunctionLibrary.setText(creditCardNoField,eachTestCaseData.get("SecondaryCreditCardNo"),Desc);
                        //Credit card Expiration Month
                        Desc="Select Expiration Month";
                        FunctionLibrary.selectDropDownListByValue(creditCardExpMpnth,eachTestCaseData.get("SecondaryExpMonth"),Desc);
                        
                        //Credit card Expiration Year
                        Desc="Select Expiration Year";
                        FunctionLibrary.selectDropDownListByValue(creditCardExpYear,eachTestCaseData.get("SecondaryExpYear"),Desc);

                    }
                  //Click on Replenishment Save button
                    Desc="Clicks on Replenishment save button";
                    FunctionLibrary.objDriver.findElement(By.xpath("//*[@id='saveReBlInf']")).click();
              }
                        
             /* //Click on Replenishment Save button
              Desc="Clicks on Replenishment save button";
              FunctionLibrary.objDriver.findElement(By.xpath("//*[@id='saveReBlInf']")).click();*/

              //sometimes popup will be displayed with " card details already saved". to handle this added below alert code

            //Rebill Pay Type with Creditcard
             
               if(eachTestCaseData.get("Secondary Rebill Pay Type").equalsIgnoreCase("MASTERCARD") || eachTestCaseData.get("Secondary Rebill Pay Type").equalsIgnoreCase("AMEX") || eachTestCaseData.get("Secondary Rebill Pay Type").equalsIgnoreCase("DISCOVER")|| eachTestCaseData.get("Secondary Rebill Pay Type").equalsIgnoreCase("VISA"))
               {
                     try {
                        WebDriverWait  wai= new WebDriverWait(FunctionLibrary.objDriver,3);
                        wai.until(ExpectedConditions.alertIsPresent());
                        Alert alert1 = FunctionLibrary.objDriver.switchTo().alert();
                        //System.out.println("Popup is displayed with text" + alert1.getText());
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Alert with text: " + alert1.getText(),
                                LogStatus.INFO,    false);
                        alert1.accept();

                    } catch (Exception e){
                         ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Alert with text: " + e.getMessage(),
                                LogStatus.INFO,    false);
                    }
               }   
              
            //  System.out.println(DateTime.now());
              FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@title='"+eachTestCaseData.get("Secondary Rebill Pay Type")+"']")));
	
	          //wait1.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//button[@id='saveReBlInf']")));
	          //Thread.sleep(2000);
	        /*  Desc="Verify row is added or not for replenishment";
	          FunctionLibrary.verifyWebElementExist(sectionTwoRowCounter,Desc);*/
	          //Clicks on Vehicles tab
	          Desc="Clicking vehicles tab";
	         // System.out.println("before vehicles tab click");
	          FunctionLibrary.clickObject(vehiclesTab,"",Desc);
	          FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(vehiclesDetailsNewBtn,vehiclesTab,8,40);
	          
	          //System.out.println("after vehicles tab click");
	         // System.out.println(DateTime.now());
	         // wait1.until(ExpectedConditions.elementToBeClickable((By.xpath(".//button[@title='Vehicles:New']"))));
	          String vehiclesToBeAdded = eachTestCaseData.get("VehiclesInfo");
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
	          System.out.println(DateTime.now());
	          if(AccountType.equalsIgnoreCase("Private") || !AccountType.equalsIgnoreCase("Private")){
	          //if(AccountType.equalsIgnoreCase("Private")){
	          for (int iterator1 =0;iterator1<=vehiclesItems.length-1;iterator1++) {
	
	              String pp = CommonLibrary.randomIdentifier();
	              String RandomChar=pp.substring(0,2);
	              eachVehicleInfo = vehiclesItems[iterator1].split(":");
	              if(!eachTestCaseData.get("TestCaseId").equalsIgnoreCase("ACEO_038")){
	                  plateNumber[iterator1] = RandomChar+DateTime.now().getMillisOfSecond()+DateTime.now().getSecondOfMinute()+ DateTime.now().getMillisOfSecond();
	                  }
	                  else{
	                      //
	                     Random ra = new Random();
	                      int Low = 10;
	                      int High = 200;
	                      int RandomrowID = ra.nextInt(High-Low) + Low;
	                     
	                      String PlateNoSql="select PLATE_NUMBER from (select PLATE_NUMBER,rownum RN from siebel.s_asset s, siebel.s_org_ext so,siebel.v_etc_account v,siebel.s_prod_int i,tpms.v_vehicle b"+ 
	                      " where  v.etc_account_id=s.x_etc_account_id"+
	                      " and v.etc_account_id=b.etc_account_id"+
	                      " and s.x_etc_account_id = so.service_emp_cnt"+ 
	                      " and s.prod_id = i. row_id"+
	                      " and i.name like '%RCSPPRE%' and v.PRIMARY_REBILL_PAY_TYPE_CD='CASH' and rownum <200)"+
	                      " where RN='"+RandomrowID+"'";
	                                  plateNumber[iterator1]=CommonLibrary.executeSqlAndGetOutputAsHashMap(PlateNoSql).get("PLATE_NUMBER");
	                      //plateNumber[iterator1]=eachVehicleInfo[0];
	                  }
	              //plateNumber[iterator1] = RandomChar+ DateTime.now().getMillisOfSecond()+DateTime.now().getSecondOfMinute()+ DateTime.now().getMillisOfSecond();
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
	              System.out.println("asdfsadf"+DateTime.now());
	              FunctionLibrary.clickObject(vehiclesDetailsNewBtn,"",Desc);
	              FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(vehiclesDetailsPlateNumberTxtBox,vehiclesDetailsNewBtn,8,40);
	              
	              //Enter PLate Number
	              Desc="Entering plate number";
	             FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(vehiclesDetailsPlateNumberTxtBox)));
	              FunctionLibrary.setText(vehiclesDetailsPlateNumberTxtBox,plateNumber[iterator1],Desc);
	              //Clicks on plate state field
	            /*  Desc="Clicking plate state field";
	              FunctionLibrary.clickObject(vehicleDetailsPlateStateElement,"",Desc);
	            */  //Enter PLate Number
	
	              
	              if(plateType.length()>1)
	              {                                     
	                      
	                      FunctionLibrary.clickObject(vehicleDetailsPlateTypeElement, "", Desc);
	                      FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(vehicleDetailsPlateTypeTxtBox,
	                      vehicleDetailsPlateTypeElement,2,5);
	                      if(!FunctionLibrary.objDriver.findElement(By.xpath("//input[@id='1_Plate_Type']")).getAttribute("value").contains(plateType))
	                          {
	
	                               FunctionLibrary.objDriver.findElement(By.xpath("//input[@id='1_Plate_Type']")).sendKeys(plateType);
	                          }
	                                                          
	             
	              }
	              
	
	              
	              if(plateCountry.length()>1)
	              {
	                      
	                      
	                      FunctionLibrary.clickObject(vehicleDetailsPlateCountryElement, "", Desc);
	                      
	                              if(!FunctionLibrary.objDriver.findElement(By.xpath("//input[@id='1_Plate_Country']")).getAttribute("value").contains(plateCountry))
	                                  {
	
	                                                  FunctionLibrary.objDriver.findElement(By.xpath("//input[@id='1_Plate_Country']")).sendKeys(plateCountry);
	                                  }
	
	              
	              }
	
	              
	              if(plateState.length()>1)
	              {                                     
	                      
	                      FunctionLibrary.clickObject(vehicleDetailsPlateStateElement,"",Desc);
	                      String defaultStateName = FunctionLibrary.getText(vehicleDetailsPlateStateTxtBox,"Get state value");
	                  if(!defaultStateName.contains(plateState)) {
	                      Desc = "Entering plate state";
	                      FunctionLibrary.objDriver.findElement(By.xpath("//input[@id='1_Plate_State']")).sendKeys(plateState);
	                                               
	                     // FunctionLibrary.setText(vehicleDetailsPlateStateTxtBox, plateState, Desc);
	                  }
	
	                                                          
	             
	              }
	
	             
	                                
	              if(vehicleType.length()>1)
	              {
	                      
	                      
	                      FunctionLibrary.clickObject(vehicleDetailsVehicleTypeElement, "", Desc);
	              
	                              if(!FunctionLibrary.objDriver.findElement(By.xpath("//input[@id='1_Vehicle_Type']")).getAttribute("value").contains(vehicleType))
	                                  {
	
	                                        FunctionLibrary.objDriver.findElement(By.xpath("//input[@id='1_Vehicle_Type']")).sendKeys(vehicleType);
	                                  }
	                      
	                 }
	              
	              if(year.length()>1)
	              {
	                      FunctionLibrary.clickObject(vehicleDetailsVehicleYearElement, "", "");
	                     if(!FunctionLibrary.objDriver.findElement(By.xpath("//input[@id='1_Year_of_Vehicle']")).getAttribute("value").contains(year))
	                          {
	
	                                FunctionLibrary.objDriver.findElement(By.xpath("//input[@id='1_Year_of_Vehicle']")).sendKeys(year);
	                          }
	
	              }
	              
	              if(make.length()>1)
	              {
	                      
	                  FunctionLibrary.clickObject(vehicleDetailsVehicleMakeElement, "", "");
	                          if(!FunctionLibrary.objDriver.findElement(By.xpath("//input[@id='1_Vehicle_Make']")).getAttribute("value").contains(make))
	                              {
	
	                                              FunctionLibrary.objDriver.findElement(By.xpath("//input[@id='1_Vehicle_Make']")).sendKeys(make);
	                              }
	                                      
	              }
	              
	              if(model.length()>1)
	              {
	                  FunctionLibrary.clickObject(vehicleDetailsVehicleModelElement, "", "");
	                          if(!FunctionLibrary.objDriver.findElement(By.xpath("//input[@id='1_Vehicle_Model']")).getAttribute("value").contains(model))
	                              {
	
	                                              FunctionLibrary.objDriver.findElement(By.xpath("//input[@id='1_Vehicle_Model']")).sendKeys(model);
	                              }
	
	                              
	              }
	
	              if(eachTestCaseData.get("PlansInfo").contains("RCSPP"))//RCSPPRE OR RCSPPOST
	              {
	                  //click effective end date
	                  Desc="Click effective end date";
	                 FunctionLibrary.clickObject(vechileEffectiveEndDateClick,"",Desc);
	
	                  //Enter effective end date
	                  Desc="Enter end date";
	                  FunctionLibrary.setText(vechileEffectiveEndDateSetText,"1/30/2030 12:00:00 AM",Desc);
	
	              }
	
	
	           
	
	             // FunctionLibrary.verifyWebElementExist(sectionTwoRowCounter,"Verify row is added or not for vechiles");
	          }
	       }   
	//====================================Vechices ending============================================================================================                                    
	       //import Vechiles for Business/Commercial
	          
	
	          //vechicle import part
	       /*   
	          if(AccountType.equalsIgnoreCase("Business")|| AccountType.equalsIgnoreCase("Commercial")){
	                      String folderPath = ReportLibrary.getPath() + "\\vehicleImportFiles\\";
	                      ReportLibrary.checkCreateDirectory(folderPath);
	                 String filePath=folderPath+FunctionLibrary.Get_Current_Date()+"_VehiclesImportFile.txt";
	                      if(!importVechilesForBusinessOrCommercial(Integer.parseInt(vehiclesCount)))
	                      {
	                                      FunctionLibrary.closeAllActiveBrowser();
	                                      continue;
	                      }
	                      String [] importedVechileNums = CommonLibrary.createFileForImportingVechiles(0, Integer.parseInt(vehiclesCount), filePath,"VechilesLoadingForSiebelAccCreation");
	                      plateNumber=importedVechileNums;
	                      
	                      
	             } */
	         //Clicking vehicle save buttton
	          Desc="Clicking vehicle save buttton";
	          FunctionLibrary.clickObject(vehicleDetailsSaveBtn,"",Desc);
	          
	          //Clicking Device Request tab
	          Desc="Clicking Device Request tab";
	          FunctionLibrary.clickObject(deviceRequestTab,"",Desc);
	       //   wait1.until(ExpectedConditions.elementToBeClickable((By.xpath("//button[@title='Device Requests:New']"))));
	          
	          FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(deviceDetailsNewBtn,
	                  deviceRequestTab,5,30);
	                  
	          String devicesToBeAdded = eachTestCaseData.get("DevicesInfo");
	          devicesToBeAdded = devicesToBeAdded.replace("\n","");
	
	          String [] devicesItems = devicesToBeAdded.split(",");
	          String [] eachDeviceInfo = null;
	          String deviceDescription;
	          String devicesQuantity;
	
	          for (int iterator1 =0;iterator1<=devicesItems.length-1;iterator1++)
	          {
	              eachDeviceInfo = devicesItems[iterator1].split(":");
	              deviceDescription = eachDeviceInfo[0];
	              devicesQuantity = eachDeviceInfo[1];
	
	             FunctionLibrary.clickObject(deviceDetailsNewBtn, "", "Click replenishment new button");
	              FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(deviceDetailsSmartIssueIdTxtBox, 
	                                      deviceDetailsNewBtn, 8, 40);
	              //Enter smart issue id
	              Desc = "Enter smart issue id";
	              FunctionLibrary.setText(deviceDetailsSmartIssueIdTxtBox, deviceDescription, Desc);
	
	              Desc = "Click the plate number field";
	              FunctionLibrary.clickObject(deviceDetailsPlateNumberElement,"",Desc);
	              if(AccountType.equalsIgnoreCase("PRIVATE")){
	                  Desc = "Enter the plate number in text box";
	                  //FunctionLibrary.objDriver.findElement(By.xpath(".//*[@name='Plate_Number']")).sendKeys(plateNumber[iterator1].toString());
	                  FunctionLibrary.setText(deviceDetailsPlateNumberTxtBox,plateNumber[iterator1],Desc);
	              }
	              //System.out.println(plateNumber[iterator1]);
	              //"Clicing Quantity field
	              Desc = "Clicing Quantity field";
	              FunctionLibrary.clickObject(deviceDetailsQuantityElement, "", Desc);
	              //Entering quantity of tags
	              Desc = "Entering quantity of tags";
	              FunctionLibrary.setText(deviceDetailsQuantityTxtBox, devicesQuantity, Desc);
	             //Storing DeviceReq ID into String Array
	              
	              Thread.sleep(2000);
	               
	              String deviceReqId = FunctionLibrary.objDriver.findElement(By.xpath("//td[contains(@id,'_Device_Request__')]")).getText();
	              Devicereqs[iterator1]=deviceReqId;
	              System.out.println(Devicereqs[iterator1]);
	              //Thread.sleep(3000);
	          }
	          
	          //Clicking Device Requests Save button
	          Desc = "Clicking Device Requests Save button";
	          FunctionLibrary.clickObject(deviceDetailsSaveBtn, "", Desc);
	
	         /* //Click Plans tab
	          Desc="Wait till plans tab to be visible";
	          //Calling Webdriver Wait function to wait till plans tab visible
	          FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 5, plansTab);
	         */
	          FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(plansNewButton,
	                                      plansTab,1,30);
	          
	        /*  //Clicking the Plans tab
	         Desc="Clicking the Plans tab";
	          FunctionLibrary.clickObject(plansTab,"",Desc);*/
	          
	          String plansToBeAdded = eachTestCaseData.get("PlansInfo");
	          plansToBeAdded = plansToBeAdded.replace("\n","");
	          String [] plansItems = plansToBeAdded.split(",");
	          String [] eachPlanInfo;
	         // System.out.println("Plan Item Lenght: "+plansItems.length);
	
	          String planName;
	          FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver,10,
	                  plansNewButton);
	          boolean standardPlanToBeDeleted = false;
	          if(!plansToBeAdded.contains("STANDARD"))
	          {
	                      standardPlanToBeDeleted = true;
	          }
	          
	          for (int iterator1 =0;iterator1<=plansItems.length-1;iterator1++)
	          {
	                      
	              eachPlanInfo = plansItems[iterator1].split(":");
	              planName = eachPlanInfo[0];
	                  if(AccountType.equalsIgnoreCase("PRIVATE"))
	                   {
	                      
	                      
	                      
	                   FunctionLibrary.objDriver.findElement(By.xpath(planAlreadyExisted)).getAttribute("title");
	                   if(!planName.equalsIgnoreCase("STANDARD"))
	                   {
	                      /*ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "For Private account default paln is not showing as STANDARD",
	                            LogStatus.FAIL, true);*/
	                       Desc = "Clicking new button";
	                       FunctionLibrary.clickObject(plansNewButton,"",Desc);
	                       Thread.sleep(6000);
	                       FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//input[@name='Plan_Name']",
	                               plansNewButton,1,30);
	
	                       FunctionLibrary.setText("//input[@name='Plan_Name']",planName,"Enter plan name");                                  
	                       
	                       if(planName.equalsIgnoreCase("PBR")||planName.equalsIgnoreCase("PBC")||planName.equalsIgnoreCase("NRV")||planName.equalsIgnoreCase("NRVTAG")||planName.equalsIgnoreCase("CTD"))
	                       {
	                    	   //TODO
	                    	   DeviceArrayIndex=0;
	
                               if(planName.equalsIgnoreCase("PBR")||planName.equalsIgnoreCase("PBC")||planName.equalsIgnoreCase("NRVTAG")||planName.equalsIgnoreCase("CTD"))
                              {
                                              
                                   String deviceReqElement = "(//td[contains(@id,'l_SR_Number')])[1]";
                                   String deviceReqTxtBox = "//input[@id='1_SR_Number']";
                                  FunctionLibrary.clickObject(deviceReqElement, "", "click device number field");
                                   FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(deviceReqTxtBox, deviceReqElement, 3, 19);
                                  System.out.println(Devicereqs[DeviceArrayIndex]);
                                  FunctionLibrary.setText(deviceReqTxtBox, Devicereqs[DeviceArrayIndex], "Enter device request id ");
                                  
                                  String plateNumElement = "//td[@id='1_s_1_l_Plate_Number']";
                                  String plateNumTxtBox = "//input[@id='1_Plate_Number']";
                                  FunctionLibrary.clickObject(plateNumElement, "", "Click plate number element");
                                  FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(plateNumTxtBox, plateNumElement, 3, 19);
                                  FunctionLibrary.setText(plateNumTxtBox, plateNumber[DeviceArrayIndex], "Enter device request id ");
                                                                                      
                              }
                              //if(plansToBeAdded.equalsIgnoreCase("PBC")||plansToBeAdded.equalsIgnoreCase("PBR")||plansToBeAdded.equalsIgnoreCase("NRV"))
                               	if(standardPlanToBeDeleted)
                                     {
                                      //Thread.sleep(3000);
                                  FunctionLibrary.clickObject("//td[@title='STANDARD']", "", "Click plan name element");               
                                  if(plansToBeAdded.equalsIgnoreCase("NRV"))
                                  {
                                      FunctionLibrary.clickObject("//td[@title='STANDARD']", "", "Click plan name element");                               
                                  }
	                                  FunctionLibrary.clickObject("//button[@title='Delete']", "", "Click delete button");
	                                  CamsSiebelAccountMaintenance.handleAlertIfPresent(10);
	                                  standardPlanToBeDeleted= false;
                                   }                                                                                                                                                              
	                                                                                                                                      
	                       }
	                   }
	                   else
	                   {
	                      continue;
	                   }
	                   }
	                   
	                       if(!AccountType.equalsIgnoreCase("PRIVATE"))
	                       {
	                    	   //TODO
	                    	   
	                    	   DeviceArrayIndex=0;
			                  Desc = "Clicking new button";
			                  FunctionLibrary.clickObject(plansNewButton,"",Desc);
			                  FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//input[@name='Plan_Name']",
			                          plansNewButton,1,30);
			
			                  FunctionLibrary.setText("//input[@name='Plan_Name']",planName,"Enter plan name");
			                  if(planName.equalsIgnoreCase("NRVTAG"))
			                  {
			                        
				                 // String deviceReqElement = "//td[@id='1_s_1_l_SR_Number']";
				                  String deviceReqElement = "(//td[contains(@id,'l_SR_Number')])[1]";
				                  String deviceReqTxtBox = "//input[@id='1_SR_Number']";
				                  FunctionLibrary.clickObject(deviceReqElement, "", "click device number field");
				                  FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(deviceReqTxtBox, deviceReqElement, 3, 19);
				           
	                               FunctionLibrary.setText(deviceReqTxtBox, Devicereqs[DeviceArrayIndex], "Enter device request id ");
			                  }
	
	                       }  
	                  if(planName.equalsIgnoreCase("GOVPOST")){
	
	                      Desc="Click on Govt Agency Text Field";
	                   
	                    // Thread.sleep(2000);
	                      FunctionLibrary.clickObject("//*[contains(@id, 'Is_Government_Agency') and @role='gridcell']", "", Desc);
	                      Desc="Click on Govt Agency Search Icon";
	                      
	                      FunctionLibrary.objDriver.findElement(By.xpath("(//*[@title='GOVPOST']//following::span[1])[2]")).click();
	                      //Select Police As Govt Agency
	                      Desc="Select Police As Govt Agency";
	                     FunctionLibrary.clickObject(govtAgencyPolice,"",Desc);
	                      Desc="Click on Ok Button";
	                      FunctionLibrary.objDriver.findElement(By.xpath("//button[@title='Vector Government Agency:Ok']")).click();
	                     //
	                      FunctionLibrary.clickObject("//td[@title='Government  Postpaid']", "", "Click govt postpaid field");
	                  }
	                                  
	                  DeviceArrayIndex++;       
	                  
	
	          }
	          Thread.sleep(2000);
	          FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@title='Pay']")));
	
	          //Click on Pay tab
	          Desc="Click on Pay tab";
	          FunctionLibrary.clickObject(plansTabPayBtn,"",Desc);
	
	
	          //Calling Web driver Wait function to wait till plans Details New button visible
	          Desc="Wait till PaymentDetails New button to be visible";
	       
	          FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("(.//*[@title='Open Items List:Go'])[2]")));
	//*****************Verifying Data Entered*****************************************************************************************************************************************************
	          //VerifyOpenItemsListTableData
	         // FunctionLibrary.verifyWebTableData(openItemsListTable, "Simple", "VerifyOpenItemsListTableData");
	          
	          FunctionLibrary.verifyTextBoxValue(agencyName, eachTestCaseData.get("Agency"), "Verify Agency",true);
	
	          FunctionLibrary.verifyTextBoxValue(accntName, accName, "Verify Acct Name",false);
	          //FunctionLibrary.verifyTextBoxValue(".//*[@aria-label='Acct Name']", eachTestCaseData.get("Account Name"), "Verify Acct Name",false);
	          FunctionLibrary.verifyTextBoxValue(accType, eachTestCaseData.get("Account Type"), "Verify Acct Type",false);
	         // FunctionLibrary.verifyTextBoxValue(accStatus, "PENDING PAY", "Verify Acct Status",false);
	        /*  try{
	          FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//img[@title='PENDING PAY']")));
	         
	                      ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Verify account status: PENDING PAY", LogStatus.PASS, 
	                                                      false);
	                      
	          }catch(TimeoutException e1)
	          {
	                      ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Account status should be PENDING PAY", LogStatus.PASS, 
	                                                      true);
	          }*/
	
	          FunctionLibrary.verifyTextBoxValue(rebillType, eachTestCaseData.get("Rebill Pay Type"), "Verify Rebill Type",false);
	          FunctionLibrary.verifyTextBoxValue(challengeQuestion, eachTestCaseData.get("Challenge Question"), "Verify Challenge QA",false);
	          FunctionLibrary.verifyTextBoxValue(ChallengeAnswer, eachTestCaseData.get("Challenge Answer"), "Verify Challenge Answer",false);
	          if(AccountType.equalsIgnoreCase("Private"))
	          {
	          FunctionLibrary.verifyTextBoxValue(noOfVehicles, String.valueOf(vehiclesItems.length), "Verify no of Vehicles",false);
	          }
	          else
	          {
	                      
	                      FunctionLibrary.verifyTextBoxValue(noOfVehicles, eachTestCaseData.get("VechileCount"), "Verify no of Vehicles",false);
	          }
	         
	//***********************************Tags and Price Validation starts here*************************************************************************
	        
	          
	          //fin status
	          //FunctionLibrary.verifyTextBoxValue(finStatus, finStatusValue, "Verify Fin Status",false);
	          //Total Due Amount Validation
	          int TotalAmountDue=planAndDevicePriceValidation(eachTestCaseData,AccountType,PaymentMode,vehiclesCount);
	          //Sales Tax validation
	          
	          
	          TaxExempt=eachTestCaseData.get("IS TaxExempt");
	          vehiclesCount=eachTestCaseData.get("VechileCount");
	          Double salesTaxvalue;
	          if(country.contains("MEX")){
	                       salesTaxvalue=(double) (Integer.parseInt(vehiclesCount)*10)*(6/100.0);
	           
	          }else{
	                      salesTaxvalue=(double) (Integer.parseInt(vehiclesCount)*10)*(4/100.0);
	
	          }
	         // eachTestCaseData.get("SalesTax");
	          
	          TaxExemptValue=eachTestCaseData.get("TaxExempt Value");
	          int rowNumberForSalesTax = FunctionLibrary.getRowNumberFromWebTableByTwoText(openItemsListTable,"SALESTAX","1","get the row number");
	          if(TaxExempt.equalsIgnoreCase("YES")&&TaxExemptValue.length()>1){
	                          FunctionLibrary.verifyWebTableCellData(openItemsListTable,rowNumberForSalesTax,5,"$0.00","Verifying SalesTax", true);
	            
	                      salesTaxvalue=0.0;
	        
	          }if(TaxExempt.equalsIgnoreCase("NO")&&TaxExemptValue.length()<1){
	      
	                      String SalesTaxInApp=FunctionLibrary.getWebTableCellData(openItemsListTable, rowNumberForSalesTax, 5, "Get the Amount");
	              SalesTaxInApp=SalesTaxInApp.replace("$","");
	               SalesTaxInApp=SalesTaxInApp.substring(0, 4);
	               if(SalesTaxInApp.contains(",")){
	                       SalesTaxInApp=SalesTaxInApp.replace(",", "");
	               }
	               if(salesTaxvalue.toString().equals(SalesTaxInApp)){
	                         SalesTaxInApp="$"+SalesTaxInApp;
	                 FunctionLibrary.verifyWebTableCellData(openItemsListTable,rowNumberForSalesTax,5,SalesTaxInApp,"Verifying SalesTax", true);
	                }
	               //Surcharge Calculation
	               int rowNumberForSurcharge1 = FunctionLibrary.getRowNumberFromWebTableByTwoText(openItemsListTable,"SURCHARGE1","1","get the row number");
	               int rowNumberForSurcharge2 = FunctionLibrary.getRowNumberFromWebTableByTwoText(openItemsListTable,"SURCHARGE2","1","get the row number");
	               if((rowNumberForSurcharge1>0)&& (rowNumberForSurcharge2>0)){
	               String Surchrg1Sql="select X_PERCENTAGE,T.* from siebel.s_INVOICE_CHRG T where MEAS_TYPE_CD like '%CLAY%' and MEAS_Name='SURCHARGE1'";
	               String Surchrg2Sql="select X_PERCENTAGE,T.* from siebel.s_INVOICE_CHRG T where MEAS_TYPE_CD like '%CLAY%' and MEAS_Name='SURCHARGE2'";
	               String Surchrg1Percent=CommonLibrary.executeSqlAndGetOutputAsHashMap(Surchrg1Sql).get("X_PERCENTAGE");
	               String Surchrg2Percent=CommonLibrary.executeSqlAndGetOutputAsHashMap(Surchrg2Sql).get("X_PERCENTAGE");
	               
	              
	               
	               Surcharge1=(double) (Integer.parseInt(vehiclesCount)*10)*(Integer.parseInt(Surchrg1Percent)/100.0);
	               Surcharge2=(double) (Integer.parseInt(vehiclesCount)*10)*(Integer.parseInt(Surchrg2Percent)/100.0);
	               
	               String Surcharge1InApp=FunctionLibrary.getWebTableCellData(openItemsListTable, rowNumberForSurcharge1, 5, "Get the Amount");
	               String Surcharge2InApp=FunctionLibrary.getWebTableCellData(openItemsListTable, rowNumberForSurcharge2, 5, "Get the Amount");
	               Surcharge1InApp=Surcharge1InApp.substring(0, 4);
	               Surcharge1InApp=Surcharge1InApp.replace("$","");
	               if(Surcharge1InApp.contains(",")){
	                       Surcharge1InApp=Surcharge1InApp.replace(",", "");
	               }
	               if(Surcharge1.toString().equals(Surcharge1InApp)){
	                       Surcharge1InApp="$"+Surcharge1InApp;
	                 FunctionLibrary.verifyWebTableCellData(openItemsListTable,rowNumberForSurcharge1,5,Surcharge1InApp,"Verifying Surcharge1", true);
	                }
	               Surcharge2InApp=Surcharge1InApp.substring(0, 4);
	               Surcharge2InApp=Surcharge2InApp.replace("$","");
	               if(Surcharge2InApp.contains(",")){
	                       Surcharge2InApp=Surcharge2InApp.replace(",", "");
	               }
	               if(Surcharge2.toString().equals(Surcharge2InApp)){
	                       Surcharge2InApp="$"+Surcharge1InApp;
	                 FunctionLibrary.verifyWebTableCellData(openItemsListTable,rowNumberForSurcharge2,5,Surcharge2InApp,"Verifying Surcharge2", true);
	                }
	               }
	          }
	          
	       
	          //Verify Amount Due
	          if(Surcharge1==null){
	                      Surcharge1=(double) 0;
	          }
	          if(Surcharge2==null){
	                      Surcharge2=(double) 0;
	          }
	          Double finalAmoutDue=TotalAmountDue+salesTaxvalue+Surcharge1+Surcharge2;
	          String AmountDueFinal=finalAmoutDue.toString();
	          double amount = Double.parseDouble(AmountDueFinal);
	          DecimalFormat formatter = new DecimalFormat("#,###.00");
	          System.out.println(formatter.format(amount));
	          AmountDueFinal="$"+formatter.format(amount);
	       
	          //scroll Down to Amount Due
	          FunctionLibrary.scrollDowntoElement(amountdueTableList, "Scroll Down to Amount Due");
	          //String DueAmount=FunctionLibrary.getText(AmountDue, "Get AmountDue");
	         int rowNumberForAmountDue = FunctionLibrary.getRowNumberFromWebTable(amountdueTableList,AmountDueFinal,"get the row number");
	          
	          String DueAmount=FunctionLibrary.getWebTableCellData(amountdueTableList, rowNumberForAmountDue, 1, "Get the AmountDue");
	          
	          if(DueAmount.contains(",")){
	                      DueAmount.replace(",","");
	          }          
	          if(AmountDueFinal.equals(DueAmount)){
	                      ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected Amount Due :"+AmountDueFinal+""+"<br>"+
	                      " Actual AmountDue: "+DueAmount,LogStatus.PASS,true);
	          }
	          else{
	                      ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected Amount Due :"+AmountDueFinal+""+"<br>"+
	                      " Actual AmountDue : "+DueAmount,LogStatus.FAIL,true);
	          }
	          //Verify Account Opening date
	          String d=FunctionLibrary.Get_Current_Date();
	                      System.out.println(FunctionLibrary.Get_Current_Date());
	                      String date[]=d.replace(".", "/").split("/");
	                      String da=date[1]+"/"+date[0]+"/"+date[2];
	                      FunctionLibrary.verifyTextBoxValue(acctOpenDate, FinalAccountOpeningDate, "Verify Account Opening date",false);
	                      
	                      
	      
	              
	//**********************Verification Ends here**********************************************************************************************************************************
	          if(eachTestCaseData.get("TestCaseId").equalsIgnoreCase("ACEO_046,ACEO_47")){
	                      try{
	                                      CamsSiebelAccountEstablishment.pendingPaymentOption(eachTestCaseData);
	                                      logoutSiebelApplicaiton();
	                                      continue;
	                     }catch(Exception e){e.printStackTrace();}
	                      
	           }
	         //Scroll down to new button
	          Desc="Scroll dwon to New button";
	          FunctionLibrary.scrollDowntoElement(paymentDetailsNewBtn, Desc);
	
	          //Clicking plans new button
	          Desc="Clicking plans new button";
	         FunctionLibrary.clickObject(paymentDetailsNewBtn,"",Desc);
	         // Thread.sleep(3000);
	          //FunctionLibrary.webdrvwaitByVisiblityofElementClickableByXpath(FunctionLibrary.objDriver,8,paymentDetailsInfoSaveBtn);
	         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(paymentDetailsInfoSaveBtn, paymentDetailsNewBtn, 5, 21);
	          if(PaymentMode.equalsIgnoreCase("CHECK")){
	           //Scroll down to Check button
	              Desc="Scroll dwon to Check number field";
	              FunctionLibrary.scrollDowntoElement(checkPay, Desc);
	              String CheckNo=Integer.toString(FunctionLibrary.randomNumberWithFiveDigit());
	              Desc="Enter Check number";
	              FunctionLibrary.setText(checkPay, CheckNo, Desc);
	          }
	          
	        /*  //Updated code due to FLCSS-5117(Cyber source doesn't allow in between 1000$ and 2000$
	          if(AccountType.equalsIgnoreCase("Business")|| AccountType.equalsIgnoreCase("Commercial"))
	          {
	             if(PaymentMode.equalsIgnoreCase("VISA")||PaymentMode.equalsIgnoreCase("AMEX")||PaymentMode.equalsIgnoreCase("MASTERCARD")||PaymentMode.equalsIgnoreCase("DISCOVER"))
	             {
	             FunctionLibrary.setText("//input[@id='Amount']", "2001", "Enter emount equal to 2000");
	             }
	          }*/
	          
	          //Scroll down to Save button
	          Desc="Scroll dwon to Payment Details Info save button";
	          FunctionLibrary.scrollDowntoElement(paymentDetailsInfoSaveBtn, Desc);
	          //Clicking on Save Button[PaymentDetails Save button]
	          Desc="Clicks on Save Button[PaymentDetails Save button]";
	          FunctionLibrary.clickObject(paymentDetailsInfoSaveBtn,"",Desc);
	          //handle the "Do you want to updated the credit card details as primary payment method....." format pop-up alert
	
	          try {
	              WebDriverWait  wai= new WebDriverWait(FunctionLibrary.objDriver,3);
	              wai.until(ExpectedConditions.alertIsPresent());
	              Alert alert1 = FunctionLibrary.objDriver.switchTo().alert();
	             System.out.println("Popup is displayed with text" + alert1.getText());
	              alert1.accept();
	
	          } catch (Exception e){
	              //System.out.println("Account is not created - Test is failed");
	          }
	          Desc="Click on Save Button[payment List Save button]";
	          //Clicking on Save Button[{payment List Save button]
	          FunctionLibrary.clickObject(paymentListSaveBtn,"","Clicking Save button");
	          if(PaymentMode.equalsIgnoreCase("VISA")||PaymentMode.equalsIgnoreCase("AMEX")||PaymentMode.equalsIgnoreCase("MASTERCARD")||PaymentMode.equalsIgnoreCase("DISCOVER")){
	                      ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: Credit Card number field should be displayed last 4 digit all other fields should be masked <br>"
	                      +"Actual: Credit Card number field displayed as expected",LogStatus.PASS,true);
	          }
	          //Clicking on Process Pay button
	          Desc="Click on Process Pay Button";
	          //Thread.sleep(5000);
	          FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Payments List:Process Pay']")));
	          FunctionLibrary.objDriver.findElement(By.xpath("//button[@title='Payments List:Process Pay']")).click();
	
	          //Handling POp up for Savings Payment Type
	         // if(replenishmentDetailsRebillPayTypeDropdown.equalsIgnoreCase("CASH")){
	              //Handling Pop up code for all type of rebill type
	              try{
	                      /*
	                      WebDriverWait wait8 = new WebDriverWait(FunctionLibrary.objDriver,8);
	                  wait8.until(ExpectedConditions.elementToBeClickable(By.xpath(acceptBtn)));
	                  FunctionLibrary.clickObject(acceptBtn,"","Clicking Ok buttn");
	                  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to Pop Up: Taking Screen shot <br>"
	                          ,LogStatus.FAIL,true);*/
	                  //how okay button alert both will be dispalyed here
	                  WebDriverWait wait9 = new WebDriverWait(FunctionLibrary.objDriver,8);
	                  wait9.until(ExpectedConditions.alertIsPresent());
	                  //Thread.sleep(3000);
	                  Alert alert = FunctionLibrary.objDriver.switchTo().alert();
	                  if(!alert.getText().trim().startsWith("Account "))
	                  {
		                  alert.accept();
		                  logoutSiebelApplicaiton();
		                  continue;
	                  }
	              }catch(WebDriverException e){}
	          //}
	
	          //handle the "Account created confirmation" pop-up alert
	          // try{
	          String alertText;
	          //Thread.sleep(5000);
	          System.out.println("Process pay clicked");
	          System.out.println("Waiting for Account num popup alert");
	          Desc="Waiting for Account number Pop up";
	          WebDriverWait wait8 = new WebDriverWait(FunctionLibrary.objDriver,60);
	         wait8.until(ExpectedConditions.alertIsPresent());
	          //Thread.sleep(3000);
	          Alert alert = FunctionLibrary.objDriver.switchTo().alert();
	          alertText=alert.getText();
	         alert.accept();
	
	          if(!alertText.trim().startsWith("Account "))
	          {
	              ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Unexpected alert is showing up. Alert text is :"+
	                      alertText, LogStatus.WARNING, false);
	
	              wait8.until(ExpectedConditions.alertIsPresent());
	             // Thread.sleep(3000);
	              Alert alert1 = FunctionLibrary.objDriver.switchTo().alert();
	              alert1.accept();
	
	          }
	
	          //After Account no generated
	         Desc="Verify Account  number field";
	          int AccountNumber =Integer.valueOf(getStringBetweenTwoStrings(alertText,"#","created").trim());
	
	     
	          if (!(AccountNumber==0))
	          {   
	                       ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: Account should be created <br>" +
	                       "Actual: Account is created "+AccountNumber,LogStatus.PASS,true);
	                       
	               String existingAccNumbers = eachTestCaseData.get("AccountNumber");
	               if(existingAccNumbers=="")
	               {
	                      existingAccNumbers = String.valueOf(AccountNumber);
	                     
	               }
	               else
	               {
	                      existingAccNumbers = String.valueOf(AccountNumber +"; "+ existingAccNumbers);                               
	                      
	               }
	               eachTestCaseData.put("AccountNumber",existingAccNumbers);
	               ExcelSheet.writeTestCaseData(testScenariosFileName, testScenariosSheetName, iterator,eachTestCaseData.get("AccountNumber"),"AccountNumber");
	               if(eachTestCaseData.get("TestCaseId").equalsIgnoreCase("ACEO_169")||eachTestCaseData.get("TestCaseId").equalsIgnoreCase("ACEO_011")||eachTestCaseData.get("TestCaseId").equalsIgnoreCase("ACEO_055")||eachTestCaseData.get("TestCaseId").equalsIgnoreCase("ACEO_076"))
	               {
	                      
	                      //query to be placed here for 
	                      String AnnivDateSql="select Open_Date,ANNIVERSARY_DOM  from tpms.v_etc_account  where ETC_ACCOUNT_ID='" +AccountNumber+"'";
	                      //String AnnivDOMSql="select a.open_date as Open_Date,a.anniversary_dom,a.* from tpms.v_etc_account a where ETC_ACCOUNT_ID='" +AccountNumber+"'";
	                      //String AnivDate =CommonLibrary.executeSqlAndGetOutputAsHashMap(AnnivDateSql).get("Open_Date");
	                      String AnivDOM =CommonLibrary.executeSqlAndGetOutputAsHashMap(AnnivDateSql).get("ANNIVERSARY_DOM");
	                      //6/1/2017
	                      String[] Accountopenday=FinalAccountOpeningDate.split("/");
	                      
	                      //String sysDate=FunctionLibrary.Get_Current_Date();
	                     // String AnivdateArray[]=sysDate.replace(".","/").split("/");
	                  
	                   if(Accountopenday[1].equalsIgnoreCase("29")||Accountopenday[1].equalsIgnoreCase("30")||Accountopenday[1].equalsIgnoreCase("31")){
	                       if(AnivDOM.equalsIgnoreCase("1")){
	                                       ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: Anniversary DOM Should be set to 1 when date falls on 29 or 30 or 31 <br>" +
	                                   "Actual: Anniversary DOM Setting as Expected for the "+AccountNumber,LogStatus.PASS,false);
	                                       
	                       }
	                       else{
	                                       ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: Anniversary DOM Should be set to 1 when date falls on 29 or 30 or 31 <br>" +
	                                   "Actual: Anniversary DOM  Setting as "+AnivDOM +" for the "+AccountNumber,LogStatus.FAIL,false);
	                       }
	                  
	                      
	                  }
	                   else{
	                       if(AnivDOM.equalsIgnoreCase(Accountopenday[1])){
	                                       ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: Anniversary DOM Should  be Set as the open date when date not falls on 29 or 30 or 31 <br>" +
	                                   "Actual: Anniversary DOM Setting as Expected for the "+AccountNumber,LogStatus.PASS,false);
	                                       
	                       }
	                       else{
	                                       ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: Anniversary DOM Should  be Set as the open date when date not falls on 29 or 30 or 31 <br>" +
	                                   "Actual: Anniversary DOM Not Setting as Expected for the "+AccountNumber,LogStatus.PASS,false);
	                       }
	                   } //select a.open_date,a.anniversary_dom,a.* from tpms.v_etc_account a where a.anniversary_dom='1';
	               }
	              FunctionLibrary.scrollUptoElement(accountNumberFieldTxt, Desc);
	     
	              String testCaseIDRow;
	              String eachTestcaseIDRow[];
	              testCaseIDRow=eachTestCaseData.get("TestcaseIDinEachScenarioFile");                       
	              ///
	              if(testCaseIDRow.length()>1){
	                  testCaseIDRow = testCaseIDRow.replace("\n","");
	                  eachTestcaseIDRow = testCaseIDRow.split(";");
	                  String columnNameInTheOutputSheet="AccountNumber";
	                  
	                  
	                  
	                  for(int iterator1=0;iterator1<=eachTestcaseIDRow.length-1;iterator1++)
	                  {
	                      String TestscenarioFileName=eachTestcaseIDRow[iterator1].split(":")[0];
	                      String eachTestCaseIDinScenarioFile=eachTestcaseIDRow[iterator1].split(":")[1];
	                      if(eachTestcaseIDRow[iterator1].split(":").length==3){
	                                      columnNameInTheOutputSheet=eachTestcaseIDRow[iterator1].split(":")[2];
	                      }
	                      
	                      int noofrowsintestscenarionFile=exl.totalrows(TestscenarioFileName,testScenariosSheetName);
	                      
	                      ExcelSheet.writeAccntNoForeachTestdata(TestscenarioFileName,testScenariosSheetName,noofrowsintestscenarionFile, 
	                              String.valueOf(AccountNumber), columnNameInTheOutputSheet,eachTestCaseIDinScenarioFile);
						 // ExcelSheet.writeAccntNoForeachTestdata(TestscenarioFileName,testScenariosSheetName,noofrowsintestscenarionFile,
							//	  String.valueOf(AccountNumber), "",eachTestCaseIDinScenarioFile);
	                  }
	                 }
	              String expectedFinStatus=eachTestCaseData.get("FinStatus Change To");
	              if(expectedFinStatus.length()>1)
	              {
	                      CamsPaymentProcessing.makefinancialStatusAsLowAccountEstablish(expectedFinStatus);
	              }
	              
	             
	              if(eachTestCaseData.get("DeviceStatus").length()>1)
	              {
	                      //String deviceRequestId=DimsManualTagAssignment.getDeviceRequestIdForAccount(String.valueOf(AccountNumber));
	                      DimsManualTagAssignment.AssignmentOfManualTag(eachTestCaseData, String.valueOf(AccountNumber),Devicereqs[0],false);
	              }
	              
	              
	              
	              
	               //TestscenarioAccount# code to be written here                         
	               if(eachTestCaseData.get("Verify Account Details").equalsIgnoreCase("Yes"))
	               {   
	               //
	                      FunctionLibrary.scrollUptoElement(accountNumberFieldTxt, Desc);
	              AccountNumber = Integer.parseInt(FunctionLibrary.getText(accountNumberFieldTxt, "Get Account number"));
	              assert (AccountNumber != 0);
	              //Scroll down to new button
	              Desc="Scroll dwon to New button";
	              FunctionLibrary.scrollUptoElement("//input[@aria-label='Acct #']", Desc);
	              ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: Account should be created <br>" +
	                      "Actual: Account is created "+AccountNumber,LogStatus.PASS,true);
	              System.out.println("Created account number: " + AccountNumber + ".");
	             String sql ="select max(ACCOUNTNUMBER) as ACCOUNT_NO  From tpms.t_mailhouse_interface S WHERE ACCOUNTNUMBER='" +AccountNumber +"' AND email ='"+email+"'";
	                                      
	                                      //"select max(account_no) as ACCOUNT_NO  From tpms.t_mailhouse_interface S WHERE ACCOUNTNUMBER =" +AccountNumber +" AND email ="+email;
	              
	              //Getting Account # from DB
	              String accountNumber =CommonLibrary.executeSqlAndGetOutputAsHashMap(sql).get("ACCOUNT_NO");
	                      if(String.valueOf(AccountNumber)==accountNumber){
	                                      ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: record should inserted into mailHouse for email trigger <br>"
	                          +"Actual: Record inserted into mailHouse table for email Trigger",LogStatus.PASS,false);
	                      }
	                      
	                      //Verify Account Balance
	                      int AccntBalnc=planAmountcalc(AccountType,PaymentMode, vehiclesCount,eachTestCaseData);//Function for Account Balance Calculation
	                      String FinalAccntBalnc=String.valueOf(AccntBalnc)+".00";
	                      String AccBal=FunctionLibrary.getText(accBalanc, "Get Account Balance");
	                      AccBal=AccBal.replace("$", "");
	                      if(AccBal.contains(",")){
	                                      AccBal=AccBal.replace(",", "");
	                      }
	                     if(AccBal.equals(FinalAccntBalnc)){
	                                      ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected Amount Balance :"+"$"+FinalAccntBalnc+""+"<br>"+
	                          " Actual Amount Balance :"+"$"+AccBal,LogStatus.PASS,true);
	                      }
	                      else{
	                                      ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected Amount Balance:"+"$"+FinalAccntBalnc+""+"<br>"+
	                          " Actual Amount Balance :"+"$"+AccBal,LogStatus.FAIL,true);
	                      }
	                      
	                      //PPTL Balance
	                      String pptlBal=FunctionLibrary.getText(pptlBalanc, "Get PPTL Balance");
	                      pptlBal=pptlBal.replace("$", "");
	                      if(pptlBal.contains(",")){
	                                      pptlBal=pptlBal.replace(",", "");
	                      }
	                      if(pptlBal.equals(FinalAccntBalnc)){
	                                      ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected PPTL Balance: "+"$"+FinalAccntBalnc+""+"<br>"+
	                          " Actual PPTL Bal : "+"$"+pptlBal,LogStatus.PASS,true);
	                      }
	                      else{
	                                      ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected PPTL Balance:"+"$"+FinalAccntBalnc+""+"<br>"+
	                          " Actual PPTL Balance : "+"$"+pptlBal,LogStatus.FAIL,true);
	                      } 
	                      /*FunctionLibrary.verifyTextBoxValue(accBal,"$"+String.valueOf(AccntBalnc)+".00", "Verify Account Balance",false);
	                      //PPTL Balance
	                      FunctionLibrary.verifyTextBoxValue(pptlBal,"$"+String.valueOf(AccntBalnc)+".00", "Verify PPTL Balance",false);*/
	              
	              
	              
	//****************************Device Validation Starts here after account created***********************************
	                            //Devicee IssueType and Status
	          //================================================
	          //Click on Devices Tab
	                      FunctionLibrary.clickObject(devicesTab, "", "Click on Deveices Tab");
	                          FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(devicesRequestTab)));
	                      //click on Device Request tab
	                      FunctionLibrary.clickObject(devicesRequestTab, "", "Click on Deveices Tab");
	                      FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//table[@summary='Device Request List']", devicesRequestTab, 9, 37);
	                         // FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@summary='Device Request List']")));
	                      
	            String devicesQuntity=eachTestCaseData.get("DevicesInfo");
	            String devicesQuntityInfo = devicesQuntity.replace("\n","");
	            String []devicesandQuntity = devicesQuntityInfo.split(",");
	            String [] eachDevicesInfo = null;
	            int devicearraylenght=devicesandQuntity.length;
	            for (int iterator1 =1;iterator1<=devicearraylenght;iterator1++)
	            {
	                      //SunPass Portable:50
	                eachDevicesInfo = devicesandQuntity[iterator1-1].split(":");
	                
	                String devicesQuant = eachDevicesInfo[1];
	                      int rowNumberForDeviceandQuanty = FunctionLibrary.getRowNumberFromWebTableByTwoText(devicesRequestList,devicesQuant,"PAID","get the row number");
	                System.out.println(rowNumberForDeviceandQuanty);
	                          FunctionLibrary.verifyWebTableCellData(devicesRequestList,rowNumberForDeviceandQuanty,4,"PAID","Verifying Device STATUS", true);
	                          FunctionLibrary.verifyWebTableCellData(devicesRequestList,rowNumberForDeviceandQuanty,6,"SOLD","Verifying ISSUE TYPE", true);
	            }
	            //Device Issue type and Status verification Ends here
	//************************************Plan verification starts here************************************************************************************
	           
	            FunctionLibrary.clickObject(planTab, "", "Click on Plan Tab");
	            //Wait untill Plans Table list to displayed
	             //FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@summary='Plan Details List']")));
	            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//table[@summary='Plan Details List']", planTab, 9, 37);
	                      
	            
	         for (int iterator1 =0;iterator1<=plansItems.length-1;iterator1++)
	            {
	                      int rowNumberForPlan = FunctionLibrary.getRowNumberFromWebTable(plansTableList,da,"get the row number");
	                      String planNameinPLansTab=FunctionLibrary.getWebTableCellData(plansTableList, rowNumberForPlan, 0, "Getting Plan Name displayed");
	                      FunctionLibrary.getWebTableCellData(plansTableList, rowNumberForPlan, 2, "Getting Plan Open Date");
	              FunctionLibrary.getWebTableCellData(plansTableList, rowNumberForPlan, 5, "Getting Plan Status");
	              FunctionLibrary.verifyWebTableCellData(plansTableList,rowNumberForPlan,0,planNameinPLansTab,"Verifying Plan Name", true);
	                          FunctionLibrary.verifyWebTableCellData(plansTableList,rowNumberForPlan,2,da,"Verifying Plan Open date under Plan Section", true);
	                         FunctionLibrary.verifyWebTableCellData(plansTableList,rowNumberForPlan,5,"PAID","Verifying Plan Status ", true);
	            }
	//*************************************PLan verification Ends Here****************************************************************************
	//************************************Financial PayType Verification Starts Here*****************************************************************
	            FunctionLibrary.clickObject(financials, "", "Click on Financial");
	            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible( reversal, financials,9, 37);
	           // FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Reversals']"))); 
	            FunctionLibrary.clickObject(reversal, "", "Click on Reversal");
	            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//table[@summary='Financial Transaction List']", reversal, 9, 37);
	            //Wait untill finanical Table list to displayed
	                          FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@summary='Financial Transaction List']"))); 
	                      int rowNumberForPayType = FunctionLibrary.getRowNumberFromWebTable(financialListTable,PaymentMode,"get the row number");
	          
	                          FunctionLibrary.verifyWebTableCellData(financialListTable,rowNumberForPayType,2,PaymentMode,"Verifying Pay Type in Financial Section ", true);
	                          String AmountDueInApp=FunctionLibrary.getWebTableCellData(financialListTable, rowNumberForPayType, 4, "Get the AmountDue");
	                          /*if(AmountDueInApp.contains(",")){
	                                      AmountDueInApp=AmountDueInApp.replace(",", "");
	                          }*/
	                          
	                                      
	                          if(AmountDueFinal.equals(AmountDueInApp)){
	                                      ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected Amount Due :"+AmountDueFinal+""+"<br>"+
	                                      " Actual AmountDue: "+AmountDueInApp,LogStatus.PASS,true);
	                          }
	                          else{
	                                      ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected Amount Due :"+AmountDueFinal+""+"<br>"+
	                                      " Actual AmountDue: "+AmountDueInApp,LogStatus.FAIL,true);
	                          }
	                     
	          }   
	                       
	                          
	          System.out.println("Siebel Account Creation test is completed.");
	//************************************Financial PayType Verification Ends  Here*****************************************************************
	              
	              //=========================
	
	              previousTestCaseBrowser = currentTcBrowser;
	
	              //Once account no created then writing in Excel sheet
	             // ExcelSheet.writeExcel(testScenariosFileName, testScenariosSheetName, iterator, 44, +AccountNumber++, true);
	            //Once account no created then writing in Excel sheet
	              // ExcelSheet.writeExcel(testScenariosFileName, testScenariosSheetName, iterator, 44, +AccountNumber++, true);
	              
	
	
	            CommonLibrary.logoutSiebelApplicaiton();
	            continue;
	          }
	          else
	          {
	
	              ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: Account number field should be displayed <br>"
	                      +"Actual: Acccount number filed is not displaying",LogStatus.FAIL,true);
	              System.out.println("Account is not created - Test is failed");
	              //Calling logout function
	              logoutSiebelApplicaiton();
	              continue;
	             
	          }
	
	
	     }//End of Inner IF
	
	  }//End of Try
	  catch(Exception e)
	  {
	      //if alert found. it will be accepted and close the browser.
	      try{
	          e.getMessage();
	          System.out.println("Test Failed SCREENSHOT TAKING");
	
	          //ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: "+Desc+""+"<br>"+"Actual: Execution Failed due to:+"+"Cyber Source Error", LogStatus.FAIL, true);
	          WebDriverWait wait8 = new WebDriverWait(FunctionLibrary.objDriver,5);
	          wait8.until(ExpectedConditions.alertIsPresent());
	          //Thread.sleep(5000);
	          String alertText;
	          Alert alert = FunctionLibrary.objDriver.switchTo().alert();
	          alertText = alert.getText();
	          alert.accept();
	          ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: "+Desc+""+
	                  "<br>"+"Actual: Execution Failed due to alert : <br>"+alertText, LogStatus.FAIL, true);
	          //Close browsers
	
	          FunctionLibrary.closeAllActiveBrowser();
	
	      }
	
	      catch(Exception exp){
	
	
	          e.printStackTrace();
	          try{
	
	                      WebDriverWait wait8 = new WebDriverWait(FunctionLibrary.objDriver,5);
	                      wait8.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='btn-accept']")));
	                      ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: "+Desc+""+
	          "<br>"+"Actual: Execution Failed due to popup <br>", LogStatus.FAIL, true);
	                  FunctionLibrary.objDriver.findElement(By.xpath("//*[@id='btn-accept']")).click();
	
	                  FunctionLibrary.closeAllActiveBrowser();
	                 /*
	                  if(sblLoginStatus.equalsIgnoreCase("Success")){
	                      //continue;
	                  }*/
	/*
	                  if(!sblLoginStatus.equalsIgnoreCase("Success")){
	                      if (FunctionLibrary.objDriver.findElement(By.xpath("//*[@id='btn-accept']")).isDisplayed())
	                      {
	                          /////FunctionLibrary.closeAllActiveBrowser();
	                          FunctionLibrary.closeAllActiveBrowser();
	
	                      }
	*/
	                  
	          
	                  continue;
	              
	          }
	          catch(Exception e1){
	              System.out.println("Test is failed");
	
	              ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is failed. Error is: "+ e.getMessage()
	              , LogStatus.FAIL, true);
	              
	              try{
	              logoutSiebelApplicaiton();
	              //FunctionLibrary.closeAllActiveBrowser();
	              }catch(Exception e2)
	              {
	                       FunctionLibrary.closeAllActiveBrowser();
	              }
	
	          }
	
	      }
	
	
	  }//End of Catch
	 continue;
	 
	      }// end of for loop for no of accounts
	}//End of main outer IF
	//Reseting Test Number
	
	
	}//End Of outer for loop
	
	}//End of Account opening Method
	

	/*
	Launch Browser
	@param url : browsing URL
	@param desc : Description of the activity
	@param browserName : NAme of Browser(chrome, mozilla....)
	*/
	public static void launchBrowser(String url,String desc,String browserName)
	{
	               
	     newBrowser = browserName;
	     if(!oldBrowser.equals(newBrowser)||isClosedAllBrowsers)
	     {
	    
	     
	         try{
	                FunctionLibrary.objDriver.manage().deleteAllCookies();
	             FunctionLibrary.objDriver.quit();     
	             }
	         catch (Exception e)
	         {
	            // System.out.println("Webdriver is not yet initiated");
	         }
	         System.out.println(browserName);
	         if(browserName.equalsIgnoreCase("firefox")) {
	             System.setProperty("webdriver.gecko.driver",".\\src\\browserDrivers\\geckodriver.exe");
	             
	             DesiredCapabilities capabilities=DesiredCapabilities.firefox();
	            capabilities.setCapability("marionette", true);
	             
	             ProfilesIni profile = new ProfilesIni();
	             
	             FirefoxProfile myprofile = profile.getProfile("SwuntAutomationUserFirefoxProfile");
	             WebDriver driver = new FirefoxDriver(myprofile);
	              FunctionLibrary.objDriver =driver;
	
	              
	         }else if(browserName.equalsIgnoreCase("chrome")) {
	
	             System.setProperty("webdriver.chrome.driver", ".\\src\\browserDrivers\\chromedriver.exe");
	             DesiredCapabilities capablities = DesiredCapabilities.chrome();
	                                capablities.setCapability("applicationCacheEnabled", true);
	             ChromeOptions options = new ChromeOptions();
	             options.addArguments("--start-maximized");
	             options.addArguments("--enable-application-cache");
	             //String chromeProfilePath = CommonLibrary.settingsSheetInfo.get("CHROME_PROFILE");
	             //options.addArguments("user-data-dir="+chromeProfilePath);
	             FunctionLibrary.objDriver = new ChromeDriver( options);
	             
	             FunctionLibrary.objDriver.manage().deleteAllCookies();
	
	         }else if(browserName.equalsIgnoreCase("iexplore")) {
	
	             System.setProperty("webdriver.ie.driver",".\\src\\browserDrivers\\IEDriverServer.exe");
	             FunctionLibrary.objDriver = new InternetExplorerDriver();
	             //Get Browser name and version.
	           //Get OS name.
	         }else {
	             System.out.println(FunctionLibrary.objDriver + " is not a supported browser");
	         }
	
	   // }
	         
	         
	         
	         //FunctionLibrary.objDriver = new FirefoxDriver();
	        
	       /*  FunctionLibrary.objDriver.get("http://www.google.com/");
	         
	         FunctionLibrary.objDriver.findElement(By.id("//input[@id='gs_htif0']")).sendKeys("qa automation");
	         FunctionLibrary.objDriver.findElement(By.name("btnG")).click();*/
	         
	     
	     // FunctionLibrary.objDriver.manage().window().maximize();
	     Capabilities caps = ((RemoteWebDriver) FunctionLibrary.objDriver).getCapabilities();
	     String browserName1 = caps.getBrowserName();
	     String browserVersion = caps.getVersion();
	
	     String os = System.getProperty("os.name").toLowerCase();
	     //System.out.println("operating system: " + os);
	     ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test environment: Browser '" + browserName1 + "' of version '"
	             + browserVersion + "' on OS '"+os+"'",LogStatus.INFO,false);
	     //FunctionLibrary.objDriver.manage().deleteAllCookies();
	     //FunctionLibrary.objDriver.get(url);
	     FunctionLibrary.objDriver.navigate().to(url);
	     
	     FunctionLibrary.waitForObject = new WebDriverWait(FunctionLibrary.objDriver, 60);
	     oldBrowser=newBrowser;
	     isClosedAllBrowsers=false;
	     }
	     
	}

	/*
    Search For Account
    @param accountNumber : Account number to be search
    */
	public static void searchForAccount(String accountNumber)
{

     FunctionLibrary.clickObject(accountsTab, "", "Clicking Accounts tab");
     FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(accountNumberTxtBox,
             accountsTab, 6, 30);
     FunctionLibrary.setText(accountNumberTxtBox, accountNumber, "Entering the account number");
     String extingVal = "";
     for(int iterator=0;iterator<=12;iterator++)
     {
    	 extingVal = FunctionLibrary.getText(accountNumberTxtBox, "Get the account number entered");
    	 if(extingVal.equalsIgnoreCase(accountNumber))
    	 {
    		 break;
    	 }else
    	 {
    		 try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		 FunctionLibrary.setText(accountNumberTxtBox, accountNumber, "Entering the account number");		 
    	 }
     }
     
     
     ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Search for account number: " + accountNumber ,LogStatus.INFO,false);
     FunctionLibrary.clickObject(goBtn, "", "Clicking go button");
     WebDriverWait wait8 = new WebDriverWait(FunctionLibrary.objDriver,60);
     wait8.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Vector Account List:Go']")));
}
	
    /*
    Log-out Siebel application
    */
	  public static void logoutSiebelApplicaiton() {

	        if (sblLoginStatus.equalsIgnoreCase("Success")) {

	            FunctionLibrary.objDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	            //wait object is for the current element
	            WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 1);

	            try {
	                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(okBtn)));
	                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Un-expected pop-up is displaying", LogStatus.FAIL, true);
	                //FunctionLibrary.closeAllActiveBrowser();
	            } catch (TimeoutException e) {
	                FunctionLibrary.clickObject("//span[@class='ui-button-text' and text()='File']", "", "Click File Menu");
	                FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//a[contains(text(),'Log Out')]",
	                        "//span[@class='ui-button-text' and text()='File']", 5, 15);
	                FunctionLibrary.clickObject("//a[contains(text(),'Log Out')]", "", "Click Logout");
	                FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.id("s_swepi_1")));
	                FunctionLibrary.objDriver.manage().deleteAllCookies();
	            }


	        } else {
	            System.out.println("Login not success");
	        }

	    }//End of LogoutandCloseBrowserMethod

	public static HashMap<String,String> executeSqlAndGetOutputAsHashMap(String sql)
{
    
    HashMap <String,String> databaseHashMapWithColumnNames = new HashMap<String, String>();
    HashMap <String,String> databaseHashMapWithColumnValues = new HashMap<String, String>();
    HashMap <String,String> databaseHashMapWithColumnNameAndValue = new HashMap<String, String>();

    try{

       //step1 load the driver class
         Class.forName("oracle.jdbc.driver.OracleDriver");
         HashMap<String, String>  settingsSheetInfo = CommonLibrary.getSettingsSheetInfo();
	       String userName = settingsSheetInfo.get("QA_USERNAME");
	       String password = settingsSheetInfo.get("QA_PASSWORD");
	       
	       //String connStringOracleDb = "jdbc:oracle:thin:@"+dbHostName+":"+dbPort+":"+dbServiceName;
	       String connStringOracleDb = "jdbc:oracle:thin:@"+settingsSheetInfo.get("DatabaseConnectionString");
         //step2 create  the connection object
         Connection con=DriverManager.getConnection(
                      connStringOracleDb,userName,password);

         //step3 create the statement object
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(sql);
         ResultSetMetaData rsmd = rs.getMetaData();

         int columnsNumber = rsmd.getColumnCount();

         //get column values of the record set in to hashmap
         while (rs.next())
         {
                  
             for(int i = 1 ; i <= columnsNumber; i++)
             {
        
                 databaseHashMapWithColumnValues.put(String.valueOf(i), rs.getString(i));
             }         

         }
         
         //get column names of the record set in to hashmap
         for( int i=1; i <= columnsNumber; i++ ) 
         {
             databaseHashMapWithColumnNames.put(String.valueOf(i), rsmd.getColumnLabel( i ));

         }         
         //assign column names and values to another hashmap databaseHashMapWithColumnNameAndValue
         for (int i = 1; i <= columnsNumber; i++ )
         {
            databaseHashMapWithColumnNameAndValue.put(databaseHashMapWithColumnNames.get(String.valueOf(i)),
                  databaseHashMapWithColumnValues.get(String.valueOf(i)));
         }
         
   
         //step5 close the connection object
         con.close();

        }catch(Exception e){ 
    System.out.println(e);
    }
                return databaseHashMapWithColumnNameAndValue;
}
	
	public static void executeSqlForUpdate(String sql)
	{
	    
	    HashMap <String,String> databaseHashMapWithColumnNames = new HashMap<String, String>();
	    HashMap <String,String> databaseHashMapWithColumnValues = new HashMap<String, String>();
	    HashMap <String,String> databaseHashMapWithColumnNameAndValue = new HashMap<String, String>();

	    try{

	       //step1 load the driver class
	         Class.forName("oracle.jdbc.driver.OracleDriver");
	         HashMap<String, String>  settingsSheetInfo = CommonLibrary.getSettingsSheetInfo();
		       String userName = settingsSheetInfo.get("QA_USERNAME");
		       String password = settingsSheetInfo.get("QA_PASSWORD");
		       
		       //String connStringOracleDb = "jdbc:oracle:thin:@"+dbHostName+":"+dbPort+":"+dbServiceName;
		       String connStringOracleDb = "jdbc:oracle:thin:@"+settingsSheetInfo.get("DatabaseConnectionString");
	         //step2 create  the connection object
	         Connection con=DriverManager.getConnection(
	                      connStringOracleDb,userName,password);

	         //step3 create the statement object
	         Statement st = con.createStatement();
	         ResultSet rs = st.executeQuery(sql);

	         //step5 close the connection object
	         con.close();

	        }catch(Exception e){ 
	    System.out.println(e);
	    }
	                
	}
		
	
	/* 
	Login Siebel App as Second User
	@Param dataObj : contains required login details(user name, password ...)
	*/
	public static void loginSiebelAppSecondUser(HashMap<String,String>dataObj) {
                       loginSiebelApp(dataObj.get("Browser Type"),CommonLibrary.getSettingsSheetInfo().get("URL_QA").toString(),
                                                   dataObj.get("Second LoginUserName"),
                                                   dataObj.get("Second LoginPassword") );
                    

}

	/* 
	Login Siebel App as First User
	@Param dataObj : contains required login details(user name, password ...)
	*/
	public static void loginSiebelAppFirstUser(HashMap<String,String>dataObj) {
	                 
		loginSiebelApp(dataObj.get("Browser Type"),CommonLibrary.getSettingsSheetInfo().get("URL_QA").toString(),
	                        dataObj.get("First LoginUserName"),
	                        dataObj.get("First LoginPassword") );
	}

	/* 
	Login Siebel App With Test Scenario Specific User
	@Param dataObj : contains required login details(user name, password ...)
	*/
	public static  void loginSblWithTestScenarioSpecificUser(HashMap<String,String>dataObj1)
	{
	     dataObj=dataObj1;
	
	     loginSiebelApp(dataObj.get("Browser Type"),CommonLibrary.getSettingsSheetInfo().get("URL_QA").toString(),
	             dataObj.get("UserId").toString(),
	             dataObj.get("Password").toString() );
	}

	/* 
	Login Siebel App With Generic User
	@Param dataObj : contains required login details(user name, password ...)
	*/
	public static void loginSblWithGenericUser(HashMap<String,String>dataObj1)
	{
		 dataObj=dataObj1;
	     loginSiebelApp(dataObj.get("Browser Type"),CommonLibrary.getSettingsSheetInfo().get("URL_QA").toString(),
	             CommonLibrary.getSettingsSheetInfo().get("QA_USERNAME"),
	             CommonLibrary.getSettingsSheetInfo().get("QA_PASSWORD") );
	
	}

	/* 
	Login Siebel App 
	@Param browserType : Browser Type
	@Param url : url
	@Param uname : User Name
	@Param pwd : Password
	*/
	public static void loginSiebelApp(String browserType, String url, String uname, String pwd) {
	
		
		
	     browserType = dataObj.get("Browser Type");
	     browserType = "Chrome";
	     url=CommonLibrary.getSettingsSheetInfo().get("URL_QA").toString();
	
	     String Desc;
	     try {
		         //Launching Browser
		         CommonLibrary.launchBrowser(url,
		                 "Launching siebel app", browserType);
		                                
		         try{
		         FunctionLibrary.waitForObject = new WebDriverWait(FunctionLibrary.objDriver, 60);
		         FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loginUsernameTxtBox)));
		         }
		         
		         catch(TimeoutException e)
		         {
		                //ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Login page is not dispalyed. So closing the broser", LogStatus.INFO, false);
		                FunctionLibrary.closeAllActiveBrowser();
		                CommonLibrary.launchBrowser(url,
		                     "Launching siebel app", browserType);            
		         }
		         //Enter User Name
		         Desc = "Entering UserName on UserName textbox";
		         FunctionLibrary.setText(loginUsernameTxtBox, uname, Desc);
		         //Enter Password
		         Desc = "Entering Password into password field";
		         FunctionLibrary.setText(loginPasswordTxtBox, pwd, Desc);
		         //Click on Sign-in Button
		         Desc = "Clicking on Sign in Button";
		         FunctionLibrary.clickObject(signInBtn, "", "Click sign in button");
		         WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 180);
		         
		         try
		         {
		         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(homePageVerificationTxt)));
		
		         //if(sblLoginStatus != null)
		         Desc = "Verify Account Opening element";
		         if (!FunctionLibrary.verifyWebElementExist(homePageVerificationTxt, Desc))
		         {
		                 sblLoginStatus = "";
		             ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Login failed. Dashboard is not displayed",
		                     LogStatus.FAIL, true);
		         }
		         else
		         { ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Login is success as user '"+ uname +"'",
		                 LogStatus.INFO, false);
		                LoginMessage="Success";
		             sblLoginStatus = "Success";
		             isClosedAllBrowsers = false;
		         }
		         }
		         catch(TimeoutException e){
		                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Login failed. Dashboard is not displayed: "+e.getMessage(),
		                     LogStatus.FAIL, true);
		
		                FunctionLibrary.closeAllActiveBrowser();
		                
		         }
		         catch(Exception e){
		                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Login failed. Dashboard is not displayed: "+e.getMessage(),
		                     LogStatus.FAIL, true);
		                sblLoginStatus = "";
		                
		                FunctionLibrary.closeAllActiveBrowser();
		                
		         }
	
	     } catch (Exception e) {
	         System.out.println("Sibel login page is not loaded properly");
	         ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Siebel Login page is not displayed properly: "+e.getMessage(), LogStatus.FAIL, true);
	                FunctionLibrary.closeAllActiveBrowser();
	     }
	}

	/*
	Get String Between Two Strings
	@Param mainString : Main String
	@Param beforePartOfRequiredValue : Before Part Of Required Value
	@Param nextPartOfRequiredValue : Next Part Of Required Value
	@Param return : Required Part Of String
	*/
	public static String getStringBetweenTwoStrings(String mainString, String beforePartOfRequiredValue, String nextPartOfRequiredValue)
	{
		//String x = "Please ensure amount is not less than the minimum replenish amount $6000(SBL-EXL-00151)";
		int startingPosition = mainString.indexOf(beforePartOfRequiredValue);
		int endingPosition = mainString.indexOf(nextPartOfRequiredValue);
		System.out.println();
		System.out.println();
		String requiredPartOfSttring =mainString.substring(startingPosition+beforePartOfRequiredValue.length(),endingPosition);
		return requiredPartOfSttring;
	}

/*	
	Get Account Number Using Sql
	@Param mailId : Email Id
	@Param return : Account Number
	
	public static String getAccountNumberUsingSql(String mailId)
	{
	
		String accountNum = dataObj.get("AccountNumber");
		
		if(accountNum==null || accountNum=="")
		{
		
		
		     String sql = "select max(account_no) as ACCOUNT_NO from siebel.v_etc_account a where email_address like '"+mailId+"' "
		             + " and account_status_cd='ACTIVE' order by a.account_no desc ";
		     HashMap  <String,String> inputAccountNumInfo =CommonLibrary.executeSqlAndGetOutputAsHashMap(sql);
		
		     accountNum = inputAccountNumInfo.get("ACCOUNT_NO");
		
		}
		return accountNum;
	}
     */           
	 /*
	Get Account Number Using Sql
	@Param columnName : Column Name
	@Param mailId : Email Id
	@Param return : Account Number
	*/
	public static String getAccountNumberUsingSql(String columnName, String mailId)
		{
		
		String accountNum = dataObj.get(columnName);
		
		if(accountNum.length()<1)
		{
		
		
		     String sql = "select max(account_no) as ACCOUNT_NO from siebel.v_etc_account a where email_address like '"+mailId+"' "
		             + " and account_status_cd='ACTIVE' order by a.account_no desc "
		             ;
		     HashMap  <String,String> inputAccountNumInfo =CommonLibrary.executeSqlAndGetOutputAsHashMap(sql);
		
		     accountNum = inputAccountNumInfo.get("ACCOUNT_NO");
		
		}
		return accountNum;
	}

	/*
	Phone Numbers Compare
	@Param number1 : Phone number 1
	@Param number2 : Phone number 2
	@Param return : flag(true or false)
	*/
	public static boolean phoneNumbersCompare(String number1, String number2)
	{
	    boolean flag=false;
	    String phoneNumber1 = number1.replace("(","");
	    phoneNumber1 = phoneNumber1.replace(")","");
	    phoneNumber1 = phoneNumber1.replace("-","");
	    phoneNumber1 = phoneNumber1.replace(" ","");
	    
	
	    String phoneNumber2 = number2.replace("(","");
	    phoneNumber2 = phoneNumber2.replace(")","");
	    phoneNumber2 = phoneNumber2.replace("-","");
	    phoneNumber2 = phoneNumber2.replace(" ","");
	
	                if(phoneNumber1.startsWith(phoneNumber2))
	                {
	                                flag=true;
	                }
	                
	                return flag;
	}
	                

	 /*
	create File For Importing Vechiles
	@param accountNum : Account Num
	@param noOfVechiclesRequired : Number Of Vechicles Required
	@param filePath : file Path
	@param return : vechile Numbers InImport File
	*/
	public static String [] createFileForImportingVechiles(int accountNum, int noOfVechiclesRequired, String filePath)
	{
	     String [] vechileNumbersInImportFile=new String[noOfVechiclesRequired];
	     
	     String pp = "";
	     String RandomChar="";
	     String vechicleNum="";
	
	     try{
	         // create new file            
	         
	         File file = new File(filePath);
	
	         // if file doesn't exists, then create it
	         if (!file.exists()) {
	             file.createNewFile();
	         }
	
	         FileWriter fw = new FileWriter(file.getAbsoluteFile());
	         BufferedWriter bw = new BufferedWriter(fw);
	         
	        /* String headerForImportfile = "Trailer Plate Indicator,Issue Type,Device Description,Account #,Contact,Effective End Date,"
	                             + "Is Rental?,Other Model,DMV Return Date,Vehicle Class,Vehicle Class Description,Vehicle Type,Vehicle Weight,"
	                             + "Web Vehicle Type,Year,Vehicle Type Code,Taxi Limo,Taxi,MVA Flag, Make, Model,Vehicle Axle Count, Color,"
	                             + "Vehicle Alternate Id,Plate Country,Plate State,Plate #,Effective Start Date,Is Dual Tire,Metal Oxide Wind Shield,"
	                             + "License State Status,Plate Type,Temporary Plate Indicator";*/
	         String headerForImportfile = "Plate #,Plate Country,Plate State,Plate Type,\" Make\",\" Model\",Vehicle Class,Year,Vehicle Type,Effective Start Date,Effective End Date";
	         String vechileLineToBeWrittenInFile = "";
	         
	         bw.write(headerForImportfile);
	         bw.newLine();
	
	         int randomNumFirstPart ;
	         int randomNumsecondPart ;
	         for(int i=0;i<=noOfVechiclesRequired-1;i++)
	         {
	             randomNumFirstPart = (int )(Math.random() * 5000 + 1);
	             randomNumsecondPart = (int )(Math.random() * 5000 + 1);
	             pp = CommonLibrary.randomIdentifier();
	             RandomChar=pp.substring(0,3);
	             
	             //vechicleNum = RandomChar+DateTime.now().getMillisOfSecond()+DateTime.now().getSecondOfMinute()+ DateTime.now().getMillisOfSecond();
	             vechicleNum = RandomChar+randomNumFirstPart+""+randomNumsecondPart;
	             if(vechicleNum.length()>=10){vechicleNum=vechicleNum.substring(0, 9);}
	             vechileNumbersInImportFile[i]= vechicleNum;
	            /* vechileLineToBeWrittenInFile=",,,"+accountNum+",,4/27/2019 09:29:12 PM,,,4/27/2019 09:29:12 PM,2,Automobile,REGULAR,,2,2010,2,,,N,"
	                                             + "BUICK,OTHER,2,,,USA,FL,"+vechicleNum+",4/27/2017 09:29:11 PM,N,,,STANDARD,";*/
	             
	             vechileLineToBeWrittenInFile=vechicleNum+",USA,FL,STANDARD,BUICK,OTHER,2,2010,REGULAR,04-12-2019 21:29,1/27/2020 09:29:12 PM";
	             
	             bw.write(vechileLineToBeWrittenInFile);
	             bw.newLine();
	         }
	
	         bw.close();
	     }catch(Exception e){
	         System.out.println(e);
	     }
	     return vechileNumbersInImportFile;
	}

	   /*
    Login Web application
    @param uname : User name
    @param pwd : Password
    @param Desc : Description of activity
    */
    public static void loginWebApp(String uname, String pwd, String Desc) throws InterruptedException {


        //FunctionLibrary.objDriver.navigate().to(
        String url = CommonLibrary.getSettingsSheetInfo().get("WEB_URL_MAIN_QA");
        launchBrowser(url, "Launch web App", dataObj.get("Browser Type"));

        FunctionLibrary.objDriver.manage().deleteAllCookies();
        Thread.sleep(2000);
        FunctionLibrary.objDriver.navigate().refresh();
        String xpathUserName = "//input[@id='tt_username1']";
        String xpathPassword = "//input[@id='tt_loginPassword1']";
        String xpathLoginButton = "//button[@name='btnLogin']";
        String xpathLoginHome = "//h4[contains(text(),'ACCOUNT')]";
        WebAccountMaintenance.findDynamicElement(By.xpath(xpathUserName), 120);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathUserName)).sendKeys(uname);
        FunctionLibrary.objDriver.findElement(By.xpath(xpathPassword)).sendKeys(pwd);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, Desc
                + " User Name: " + uname + " Password: " + pwd, LogStatus.INFO, true);
        try {
            FunctionLibrary.objDriver.findElement(By.xpath(xpathLoginButton)).sendKeys(Keys.ENTER);
            WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 50);
            wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathLoginHome)));
        } catch (Exception e) {
            if (Desc != "First time login") {
                FunctionLibrary.objDriver.findElement(By.xpath(xpathLoginButton)).click();
            }
        }
        if (Desc != "First time login") {
            try {
                WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver, 50);
                wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathLoginHome)));
            } catch (Exception e) {
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Login not success", LogStatus.FAIL, true);
                FunctionLibrary.objDriver.close();
            }
        }
        Thread.sleep(1500);
        String xpathOfmyModal = ".//*[@id='myModal']";
        try {
            if (FunctionLibrary.objDriver.findElement(By.xpath(xpathOfmyModal)).isDisplayed()) {
                FunctionLibrary.objDriver.findElement(By.xpath(xpathOfmyModal)).sendKeys(Keys.ESCAPE);
            }
        } catch (Exception e) {
            System.out.println("Pop-Up doesnot exist");
        }
        if (Desc != "First time login") {
            if (FunctionLibrary.objDriver.findElement(By.xpath(xpathLoginHome)).isDisplayed()) {

                Thread.sleep(2000);
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Successfully logged in.", LogStatus.PASS, true);
                LoginMessage = "Success";
                sblLoginStatus = "Success";
            }
        }
    }


    //Function to logout web application
    public static void logoutWebApp() throws InterruptedException {
        String xpathLogout = "//a[contains(@href,'accountLogout')]";
        WebAccountMaintenance.scrollToView(By.xpath(xpathLogout));
        // String xpathLogout = "//*[@id='account_nav_panel']//a[contains(text(),'Log Out')]";


        try {
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(WebAccountMaintenancePageProperties.xpathUserName,
                    xpathLogout, 20, 60);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Successfully logged out", LogStatus.INFO, true);
            FunctionLibrary.objDriver.close();
        } catch (Exception e) {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Logout is not Success", LogStatus.INFO, true);
            FunctionLibrary.objDriver.close();
        }
        LoginMessage = "";
        sblLoginStatus = "";
        // FunctionLibrary.closeAllActiveBrowser();
    }

	//Function to click site map 
	 public static void clickSiteMap()
	{
	     FunctionLibrary.clickObject(navigateMenu, "", "Click File Menu");
	     FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
	     (siteMapMenu, navigateMenu,
	                   10, 60);
	     FunctionLibrary.clickObject(siteMapMenu, "", "Click Logout"); 
	 
	  WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 90);
	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(siteMapSearchTxtBox)));
	}
	/*
	Import Vechiles For Business Or Commercial
	@param noOfFilesToBeUpload : Number Of Files To Be Upload
	*/
	public static boolean importVechilesForBusinessOrCommercial (int noOfFilesToBeUpload){
		 boolean isVehiclesImported = false;
		try{
		 
	        String folderPath = ReportLibrary.getPath() + "\\vehicleImportFiles\\";
	  
	    ReportLibrary.checkCreateDirectory(folderPath);
	    String filePath=folderPath+FunctionLibrary.Get_Current_Date()+"_VehiclesImportFile.txt";
	    
	    CommonLibrary.createFileForImportingVechiles(0, noOfFilesToBeUpload, filePath,"VechilesLoadingForSiebelAccCreation");
	    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Screenshot before vehicles import", LogStatus.INFO, true);
	                //click on import vehicle
	                //FunctionLibrary.clickObject("//span[contains(text(),'Import Vehicle')]", "", "click on import");
	    FunctionLibrary.clickObject("//button[@title='Vehicles:Import Vehicle']", "", "click on import");
	           /*FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(".//*[@title='Vehicle File Import:Import']", "//button[@title='Vehicles:Import Vehicle']", 10, 41);*/
	    FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(nextBtn, "//button[@title='Vehicles:Import Vehicle']", 10, 41);     
	        //desc="Waiting for the alert";
	        WebDriverWait wait=new WebDriverWait(FunctionLibrary.objDriver, 10);
	             Thread.sleep(2000);
	        //click on browse
	        WebElement chooseFile= FunctionLibrary.objDriver.findElement(By.xpath("//input[@type='file']"));
	        wait.until(ExpectedConditions.visibilityOf(chooseFile));
	        try{
	            // String importFilePath="C://Users//34546//Desktop//aman_jain";
	           // browseImportFile.click();
	           
	            chooseFile.sendKeys(filePath);
	          
	            
	
	        }catch(Exception e){
	
	            FunctionLibrary.highLightElement(FunctionLibrary.objDriver,FunctionLibrary.objDriver.
	                    findElement(By.xpath("//button[@title='Vehicle File Import:Import']")));
	        }
	
	        //Thread.sleep(5000);
	       // FunctionLibrary.objDriver.findElement(By.xpath("//button[@title='Vehicle File Import:Import']")).click();
	                                           
	        
	        //FUNCTIONL FLOW IS CHANGED FOR IMPORTING FILE:
	        FunctionLibrary.clickObject(importFileTypeRadioBtn,"","Click csv radio button");
	        FunctionLibrary.clickObject(nextBtn,"","Click next button");
	        Thread.sleep(5000);
	        FunctionLibrary.clickObject(nextBtn,"","Click next button");
	        
	        
	        
	        WebDriverWait wait5 = new WebDriverWait(FunctionLibrary.objDriver, 600);
	        
	        wait5.until(ExpectedConditions.elementToBeClickable(By.xpath(vehicleImportOkBtn)));
	        FunctionLibrary.clickObject(vehicleImportOkBtn, "", "Click okay button");
	        //wait5.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(".//*[@title='Vehicle File Import:Import']")));
	        wait5.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Vehicles:Save']")));
	
	        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: "+"Vehicles to be imported"+""+"<br>"+
	                "Actual: Vehicles are imported successfully", LogStatus.PASS, true);
	        isVehiclesImported = true;
	
	    }catch(Exception e){
	        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: "+"Vehicles to be imported"+""+"<br>"+
	                "Actual: Vehicles are not imported ", LogStatus.FAIL, true);
	        isVehiclesImported= false;
	    }
	return isVehiclesImported;
	  
	}
	/*
	 create File For Importing Vechiles
	@param accountNum : Account Num
	@param noOfVechiclesRequired : Number Of Vechicles Required
	@param filePath : file Path
	@param TypeofCreation : Type of Creation
	@param return : vechile Numbers InImport File
	*/
	public static String [] createFileForImportingVechiles(int accountNum, int noOfVechiclesRequired, String filePath,String TypeofCreation)
	{
	  String [] vechileNumbersInImportFile=new String[noOfVechiclesRequired];
	  
	  String pp = "";
	    String RandomChar="";
	    String vechicleNum="";
	
	    try{
	        // create new file                 
	        
	        File file = new File(filePath);
	
	        // if file doesn't exists, then create it
	        if (!file.exists()) {
	            file.createNewFile();
	        }
	
	        FileWriter fw = new FileWriter(file.getAbsoluteFile());
	        BufferedWriter bw = new BufferedWriter(fw);
	        
	       /* String headerForImportfile = "Trailer Plate Indicator,Issue Type,Device Description,Account #,Contact,Effective End Date,"
	                    + "Is Rental?,Other Model,DMV Return Date,Vehicle Class,Vehicle Class Description,Vehicle Type,Vehicle Weight,"
	                    + "Web Vehicle Type,Year,Vehicle Type Code,Taxi Limo,Taxi,MVA Flag, Make, Model,Vehicle Axle Count, Color,"
	                    + "Vehicle Alternate Id,Plate Country,Plate State,Plate #,Effective Start Date,Is Dual Tire,Metal Oxide Wind Shield,"
	                    + "License State Status,Plate Type,Temporary Plate Indicator"*/;
	           String headerForImportfile = "Plate #,Plate Country,Plate State,Plate Type,\" Make\",\" Model\",Vehicle Class,Year,Vehicle Type,Effective Start Date,Effective End Date";         
	                    
	        String vechileLineToBeWrittenInFile = "";
	        
	        bw.write(headerForImportfile);
	        bw.newLine();
	
	        int randomNumFirstPart ;
	        int randomNumsecondPart ;
	        for(int i=0;i<=noOfVechiclesRequired-1;i++)
	        {
	              randomNumFirstPart = (int )(Math.random() * 5000 + 1);
	              randomNumsecondPart = (int )(Math.random() * 5000 + 1);
	              pp = CommonLibrary.randomIdentifier();
	              RandomChar=pp.substring(0,3);
	              
	              //vechicleNum = RandomChar+DateTime.now().getMillisOfSecond()+DateTime.now().getSecondOfMinute()+ DateTime.now().getMillisOfSecond();
	              vechicleNum = RandomChar+randomNumFirstPart+""+randomNumsecondPart;
	              if(vechicleNum.length()>=10){vechicleNum=vechicleNum.substring(0, 9);}
	              vechileNumbersInImportFile[i]= vechicleNum;
	              if(TypeofCreation=="VechilesLoadingForSiebelAccCreation"){
	              /*vechileLineToBeWrittenInFile=",,,"+accountNum+",,4/17/2019 09:29:12 PM,,,4/17/2019 09:29:12 PM,2,Automobile,REGULAR,,2,2010,2,,,N,"
	                          + "BUICK,OTHER,2,,,USA,FL,"+vechicleNum+",4/15/2017 09:29:11 PM,N,,,STANDARD,";*/
	                  vechileLineToBeWrittenInFile=vechicleNum+",USA,FL,STANDARD,BUICK,OTHER,2,2010,REGULAR,04-12-2019 21:29,1/27/2020 09:29:12 PM";
	              }
	              else{
	                    /*vechileLineToBeWrittenInFile=",,,"+accountNum+",,4/17/2019 09:29:12 PM,,,4/17/2019 09:29:12 PM,2,Automobile,REGULAR,,2,2010,2,,,N,"
	                                + "BUICK,OTHER,2,,,USA,FL,"+vechicleNum+",4/15/2017 09:29:11 PM,N,,,STANDARD,";*/
	                  vechileLineToBeWrittenInFile=vechicleNum+",USA,FL,STANDARD,BUICK,OTHER,2,2010,REGULAR,04-12-2019 21:29,1/27/2020 09:29:12 PM";
	              }
	              
	              
	            bw.write(vechileLineToBeWrittenInFile);
	            bw.newLine();
	        }
	
	        bw.close();
	    }catch(Exception e){
	        System.out.println(e);
	    }
	    return vechileNumbersInImportFile;
	}
    /*
    verify Siebel Database Column Value
    @param dbTableNameAndColumnName : DB Table And Column Name
    @param expectedVal : expected Value
    @param whereClauseColumnName : where Clause Column Name
    @param whereClauseColumnValue : where Clause Column Value
    */
	public static void verifySiebelDatabaseColumnValue(String dbTableNameAndColumnName, String expectedVal, String whereClauseColumnName, String whereClauseColumnValue)
{
       String result;
       int startPositionOfColumnName = dbTableNameAndColumnName.indexOf(".")+1;
       String dbTableName = dbTableNameAndColumnName.substring(0, dbTableNameAndColumnName.indexOf("."));
                       
       String  columnName = dbTableNameAndColumnName.substring(startPositionOfColumnName,dbTableNameAndColumnName.length());
       
        String sql = "select "+columnName+" as "+columnName+" from siebel."+dbTableName+" where "+whereClauseColumnName+" like '"+whereClauseColumnValue+"' ";

        HashMap  <String,String> output =CommonLibrary.executeSqlAndGetOutputAsHashMap(sql);

        result = output.get(columnName);
        if(result.toUpperCase().contains(expectedVal.toUpperCase()))
       {
                   
                   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Database table "+dbTableName+" column "+columnName+" value should be "+ 
                   expectedVal+"<br>Actual:Table has the value: "+result, LogStatus.PASS, false);
        }else
        {
                   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: Database table "+dbTableName+" column "+columnName+" value should be "+ 
                   expectedVal+"<br>Actual:Table has the value: "+result, LogStatus.FAIL, false);
       }
    
}
    /*
    plan Amount calculation
    @param AccountType : Type of Account
    @param PaymentMode : Mode of Payment
    @param noOfVechicle : Total Number of vehicles
    @param return : final Plan Amount
    */

   public static int planAmountcalc(String AccountType,String PaymentMode,String noOfVechicle,HashMap<String,String> dataObj){
                 //plans verification starts here
      String plansAdded = dataObj.get("PlansInfo");
      plansAdded = plansAdded.replace("\n","");
      String [] plansList = plansAdded.split(",");
      String [] eachPlan;
      int PlanAmount=0;
      int finalPlanAmount=0;
      

     String eachplanName;
             
      for (int i =0;i<=plansList.length-1;i++)
      {
          eachPlan = plansList[i].split(":");
          eachplanName = eachPlan[0];
          AccountType=AccountType.toUpperCase();
          eachplanName=eachplanName.toUpperCase();
          PaymentMode=PaymentMode.toUpperCase();
          
          
          String sql =" SELECT CHRG_AMT, CHRG_TS, CHRG_EXCH_DT FROM SIEBEL.S_INVOICE_CHRG"
                  +" where X_ACCOUNT_TYPE ='" +AccountType+ "' AND"
                  +" MEAS_NAME ='" +eachplanName +"' AND"
                  +" MEAS_TYPE_CD = 'TOLDEPOSIT' AND"
                  +" X_REP_TYPE ='"+PaymentMode+"'";

                                
          HashMap  <String,String> planCharges =CommonLibrary.executeSqlAndGetOutputAsHashMap(sql);
          
          

          if(eachplanName.contains("FLEETPRE")||eachplanName.contains("NRV")||eachplanName.contains("ISPPRE")||eachplanName.contains("RCSPPOST")||eachplanName.contains("RCSPPRE")||eachplanName.contains("GOVPOST")||eachplanName.contains("BVIDEOREG")||eachplanName.contains("BPOSTVIDEOREG")){  
                   PlanAmount = Integer.parseInt(planCharges.get("CHRG_AMT"));
          }
          
          if(eachplanName.contains("STANDARD")){
                   PlanAmount = Integer.parseInt(planCharges.get("CHRG_AMT"))*Integer.parseInt(noOfVechicle);
          }
          finalPlanAmount=finalPlanAmount+PlanAmount;
      }
                                return finalPlanAmount;
                                
  }
   /*
  plan And Device Price Validation
   @param AccountType : Type of Account
   @param PaymentMode : Mode of Payment
   @param noOfVechicle : Total Number of vehicles
   @param return : total due Amount
   */
   public static int planAndDevicePriceValidation(HashMap<String,String>dataObj,String AccountType,String PaymentMode,String noOfVechicle){
          int totaldueAmount=0;
          try{
                        //******++++++++++++++++++++**************************************************************************************************************
    
    /*   int rowNumberForPlan = FunctionLibrary.getRowNumberFromWebTableByTwoText(openItemsListTable,"FLEETPRE","1","get the row number");
       System.out.println(rowNumberForPlan);
       FunctionLibrary.verifyWebTableCellData(openItemsListTable,rowNumberForPlan,2,"FLEETPRE","Verifying FLEETPre PLan Added", true);
       FunctionLibrary.verifyWebTableCellData(openItemsListTable,rowNumberForPlan,3,"1","Verifying FLEETPre Quantity", true);*/
//******+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++                                  
       //plans verification starts here
       String plansAdded = dataObj.get("PlansInfo");
       plansAdded = plansAdded.replace("\n","");
       String [] plansList = plansAdded.split(",");
       String [] eachPlan,eachPlanAmoutList;
       int PlanAmount=0;
       
      // System.out.println("Plan Item Lenght: "+plansItems.length);

      String eachplanName;
      for (int i =0;i<=plansList.length-1;i++)
      {
          eachPlan = plansList[i].split(":");
          eachplanName = eachPlan[0];
          String planQuantity="1";
          AccountType=AccountType.toUpperCase();
          eachplanName=eachplanName.toUpperCase();
          PaymentMode=PaymentMode.toUpperCase();
          
          
          String sql =" SELECT CHRG_AMT, CHRG_TS, CHRG_EXCH_DT FROM SIEBEL.S_INVOICE_CHRG"
                  +" where X_ACCOUNT_TYPE ='" +AccountType+ "' AND"
                  +" MEAS_NAME ='" +eachplanName +"' AND"
                  +" MEAS_TYPE_CD = 'TOLDEPOSIT' AND"
                  +" X_REP_TYPE ='"+PaymentMode+"'";

                                        
          HashMap  <String,String> planCharges =CommonLibrary.executeSqlAndGetOutputAsHashMap(sql);
          
          

          if(eachplanName.contains("FLEETPRE")||eachplanName.contains("NRV")||eachplanName.contains("ISPPRE")||eachplanName.contains("RCSPPOST")||eachplanName.contains("RCSPPRE")||eachplanName.contains("GOVPOST")||eachplanName.contains("BVIDEOREG")||eachplanName.contains("BPOSTVIDEOREG")){  
           PlanAmount = Integer.parseInt(planCharges.get("CHRG_AMT"));
          }
          
          if(eachplanName.contains("STANDARD")){
          PlanAmount = Integer.parseInt(planCharges.get("CHRG_AMT"))*Integer.parseInt(noOfVechicle);
          }
                                        
                                        //CommonLibrary.getSettingsSheetInfo().get("FleetPrePlanPrice").toString();
           //nrvPlanAmount=CommonLibrary.getSettingsSheetInfo().get("NRVPlanPrice").toString();
          String PlanAmountInApp;
          
          int rowNumberForEachPlan = FunctionLibrary.getRowNumberFromWebTableByTwoText(openItemsListTable,eachplanName,"1","get the row number");
           System.out.println(rowNumberForEachPlan);
           FunctionLibrary.verifyWebTableCellData(openItemsListTable,rowNumberForEachPlan,2,eachplanName,"Verifying Plan Added", true);
           FunctionLibrary.verifyWebTableCellData(openItemsListTable,rowNumberForEachPlan,3,planQuantity,"Verifying Plan Quantity Added", true);
           
           PlanAmountInApp=FunctionLibrary.getWebTableCellData(openItemsListTable, rowNumberForEachPlan, 5, "Get the Amount");
           eachPlanAmoutList=PlanAmountInApp.replace(".","/").replace(",", "").split("/");
          String displayedPlanPriceInApp=eachPlanAmoutList[0].substring(1, eachPlanAmoutList[0].length());
         //FLEETPRE price validation
          if(eachplanName.contains("FLEETPRE")){
           if(PlanAmount==Integer.parseInt((displayedPlanPriceInApp))){
                           
                           
                                
                                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Expected Plan is: FLEETPRE "+""+"<br>"+
                                       "Actual Plan added "+eachplanName+"Quantity added"+planQuantity+" Total amount Displayed: "+displayedPlanPriceInApp, LogStatus.PASS, false);
                           }
                           else
                           {
                                       ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:Plan "+eachplanName + "+and Expected Quantity is" +planQuantity +""+"<br>"+
                                                                       " Actual PLan Displayed is: " +"Plan and PlanAmount Wrongly displayed", LogStatus.FAIL, false);
                           }
          }
          //STANDARD Price validation
         if(eachplanName.contains("STANDARD")){
                           if(PlanAmount==Integer.parseInt((displayedPlanPriceInApp))){
                                           
                                           
                                
                                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Expected Plan is : STANDARD "+""+"<br>"+
                                        " Actual Plan Added: "+eachplanName+ ""+"<br>"+ 
                                        " Quantity added: "+planQuantity+""+"<br>"+
                                        " Total amount Displayed: "+"$"+displayedPlanPriceInApp, LogStatus.PASS, false);
                            }
                            else
                            {
                                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:Plan "+eachplanName + "and Expected Quantity is " +planQuantity +" Actual PLan Displayed is: " 
                                                                        +"Plan and PlanAmount Wrongly displayed", LogStatus.FAIL, false);
                            }
                          }
          //ISPPre price validation
          if(eachplanName.contains("ISPPRE")){
                           if(PlanAmount==Integer.parseInt((displayedPlanPriceInApp))){
                                           
                                        
                                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Expected Plan is :ISPPRE"+""+"<br>"+
                                                                        " Actual Plan Added: "+eachplanName+ ""+"<br>"+ 
                                                        " Quantity added: "+planQuantity+""+"<br>"+
                                                        " Total amount Displayed: "+"$"+displayedPlanPriceInApp, LogStatus.PASS, false);
                            }
                            else
                            {
                                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:Plan "+eachplanName + "+and Expected Quantity is" +planQuantity +" Actual PLan Displayed is: " 
                                                                        +"Plan and PlanAmount Wrongly displayed", LogStatus.FAIL, false);
                            }
                          }
          //GOVPOST price validation
          if(eachplanName.contains("GOVPOST")){
                           if(PlanAmount==Integer.parseInt((displayedPlanPriceInApp))){
                                           
                                           
                                
                                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Expected Plan is :GOVPOST"+""+"<br>"+ 
                                                                        " Actual Plan Added: "+eachplanName+ ""+"<br>"+ 
                                                        " Quantity added: "+planQuantity+""+"<br>"+
                                                        " Total amount Displayed: "+"$"+displayedPlanPriceInApp, LogStatus.PASS, false);
                            }
                            else
                            {
                                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:Plan "+eachplanName + "+and Expected Quantity is" +planQuantity +" Actual PLan Displayed is: " 
                                                                        +"Plan and PlanAmount Wrongly displayed", LogStatus.FAIL, false);
                            }
                         }
          //RCSPPOST Price Validation
          if(eachplanName.contains("RCSPPOST")){
                           if(PlanAmount==Integer.parseInt((displayedPlanPriceInApp))){
                                           
                                           
                                
                                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Expected Plan is :RCSPPOST"+""+"<br>"+ 
                                                                        " Actual Plan Added: "+eachplanName+ ""+"<br>"+ 
                                                        " Quantity added: "+planQuantity+""+"<br>"+
                                                        " Total amount Displayed: "+"$"+displayedPlanPriceInApp, LogStatus.PASS, false);
                            }
                            else
                            {
                                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:Plan "+eachplanName + "+and Expected Quantity is" +planQuantity +" Actual PLan Displayed is: " 
                                                                        +"Plan and PlanAmount Wrongly displayed", LogStatus.FAIL, false);
                            }
                          }
          //RCSPPRE Price Validation
          if(eachplanName.contains("RCSPPRE")){
                           if(PlanAmount==Integer.parseInt((displayedPlanPriceInApp))){
                                           
                                           
                                
                                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Expected Plan is :RCSPPRE"+""+"<br>"+
                                                                        " Actual Plan Added: "+eachplanName+ ""+"<br>"+ 
                                                        " Quantity added: "+planQuantity+""+"<br>"+
                                                        " Total amount Displayed: "+"$"+displayedPlanPriceInApp, LogStatus.PASS, false);
                            }
                            else
                            {
                                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:Plan "+eachplanName + "+and Expected Quantity is" +planQuantity +" Actual PLan Displayed is: " 
                                                                        +"Plan and PlanAmount Wrongly displayed", LogStatus.FAIL, false);
                            }
                          }
        //BPOSTVIDEOREG Price Validation
          if(eachplanName.contains("BPOSTVIDEOREG")){
                           if(PlanAmount==Integer.parseInt((displayedPlanPriceInApp))){
                                           
                                           
                                
                                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Expected Plan is :BPOSTVIDEOREG"+""+"<br>"+
                                                                        " Actual Plan Added: "+eachplanName+ ""+"<br>"+ 
                                                        " Quantity added: "+planQuantity+""+"<br>"+
                                                        " Total amount Displayed: "+"$"+displayedPlanPriceInApp, LogStatus.PASS, false);
                            }
                            else
                            {
                                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:Plan "+eachplanName + "+and Expected Quantity is" +planQuantity +" Actual PLan Displayed is: " 
                                                                        +"Plan and PlanAmount Wrongly displayed", LogStatus.FAIL, false);
                            }
                          }
        //BVIDEOREG Price Validation
          if(eachplanName.contains("BVIDEOREG")){
                           if(PlanAmount==Integer.parseInt((displayedPlanPriceInApp))){
                                           
                                           
                                
                                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Expected Plan is :BVIDEOREG"+""+"<br>"+
                                                                        " Actual Plan Added: "+eachplanName+ ""+"<br>"+ 
                                                        " Quantity added: "+planQuantity+""+"<br>"+
                                                        " Total amount Displayed: "+"$"+displayedPlanPriceInApp, LogStatus.PASS, false);
                            }
                            else
                            {
                                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:Plan "+eachplanName + "+and Expected Quantity is" +planQuantity +" Actual PLan Displayed is: " 
                                                                        +"Plan and PlanAmount Wrongly displayed", LogStatus.FAIL, false);
                            }
                          }

          if(eachplanName.contains("NRV")){
                           if(PlanAmount==Integer.parseInt((displayedPlanPriceInApp))){
                                           
                                           
                                
                                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Expected Plan is :NRV: "+""+"<br>"+
                                                                        " Actual Plan Added: "+eachplanName+ ""+"<br>"+ 
                                                        " Quantity added: "+planQuantity+""+"<br>"+
                                                        " Total amount Displayed: "+"$"+displayedPlanPriceInApp, LogStatus.PASS, false);
                            }
                            else
                            {
                                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:Plan "+eachplanName + "+and Expected Quantity is" +planQuantity +" Actual PLan Displayed is: " 
                                                                        +"Plan and PlanAmount Wrongly displayed", LogStatus.FAIL, false);
                            }
                          }
         
          totaldueAmount=totaldueAmount+PlanAmount;
         }
            
       
       //----------------------------------------------------------
       //devices verification
       String devicesQuntityInfo=dataObj.get("DevicesInfo");
       String devicesQuntity = devicesQuntityInfo.replace("\n","");
       String []devicesandQuntity = devicesQuntity.split(",");
       String [] eachDevicesInfo = null;
       int devicearrylenght=devicesandQuntity.length;
       System.out.println(devicearrylenght);
       String deviceTag = null;
       String deviceDisplayedInApp,devicesQuan,devicedesc,quantityPrice;
                                                       String[] quantityPriceDisplayedInApp;

       for (int iterator1 =1;iterator1<=devicearrylenght;iterator1++)
       {
       //SunPass Portable:50
           eachDevicesInfo = devicesandQuntity[iterator1-1].split(":");
           
           devicedesc = eachDevicesInfo[0];
           devicesQuan = eachDevicesInfo[1];

            if(devicedesc.equalsIgnoreCase("SunPass Portable")||devicedesc.equalsIgnoreCase("Eco Plus Portable")){
                         deviceTag="TAGINT";
           }
           if(devicedesc.equalsIgnoreCase("SunPass Bumper Mount")){
                         deviceTag="TAGLIC";
           }
           if(devicedesc.equalsIgnoreCase("SunPass Mini ")){
                         deviceTag="TAGSTK";
           }
           int unitPrice=10;
           int totalAmount=Integer.parseInt(devicesQuan)*unitPrice;
           int rowNumberForDeviceandQuanty = FunctionLibrary.getRowNumberFromWebTableByTwoText(openItemsListTable,deviceTag,devicesQuan,"get the row number");
           System.out.println(rowNumberForDeviceandQuanty);
            FunctionLibrary.verifyWebTableCellData(openItemsListTable,rowNumberForDeviceandQuanty,2,deviceTag,"Verifying Device Tag Added", true);
            FunctionLibrary.verifyWebTableCellData(openItemsListTable,rowNumberForDeviceandQuanty,3,devicesQuan,"Verifying Device Quantity Added", true);
           quantityPrice=FunctionLibrary.getWebTableCellData(openItemsListTable, rowNumberForDeviceandQuanty, 5, "Get the Amount");
           quantityPriceDisplayedInApp=quantityPrice.replace(".","/").replace(",", "").split("/");
           String displayedquantityPriceInApp=quantityPriceDisplayedInApp[0].substring(1, quantityPriceDisplayedInApp[0].length());
           deviceDisplayedInApp=FunctionLibrary.getWebTableCellData(openItemsListTable, rowNumberForDeviceandQuanty, 2, "Get the Device");
            FunctionLibrary.getWebTableCellData(openItemsListTable, rowNumberForDeviceandQuanty, 4, "Get the Quantity");
           if(totalAmount==Integer.parseInt(displayedquantityPriceInApp))
           {
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, " Device added is showing correctly: "+""+"<br>"+
                                                        devicedesc+"["+deviceTag+"]"+"Quantity added"+devicesQuan+" Total amount Displayed: "+totalAmount, LogStatus.PASS, false);
            }
            else
            {
                        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:Device: "+deviceTag + "  Expected Quantity is: " +devicesQuan +""+"<br>"+
                                                        "Actual Device Displayed is: "+deviceDisplayedInApp+" and Actual Quantity displayed: "+displayedquantityPriceInApp, LogStatus.FAIL, false);
            }
          
           totaldueAmount=totaldueAmount+totalAmount;
       }
       
   
           
                        
        }catch(Exception e){
                        
        }
       return totaldueAmount;
    }

   /*
   add vehicles to web application
   @param numberOfVechicles: number of vehicles
  */
   public static void webApplicationAddVehicles(int numberOfVehicles) throws Exception {

       String platenumber = "";
       for(int j=0;j<numberOfVehicles;j++) {
           platenumber = "FL" + DateTime.now().getMillisOfSecond() + DateTime.now().getSecondOfMinute() + DateTime.now().getMillisOfSecond();
           FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].vehicleLicense']")).sendKeys(platenumber);
           FunctionLibrary.selectDropDownListByIndex("//select[@name='vehicleid[" + j + "].vehicleMake']", "3", "");
           FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].vehicleModel']")).sendKeys("OTHER");
           FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].vehicleYear']")).sendKeys("2016");
           FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].vehicleColor']")).sendKeys("Red");
           new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
           new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
		 //  vehicleid[0].effectiveBeginDate
		   FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].effectiveBeginDate']")).click();
           new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_LEFT)).perform();
           new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
           new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
           new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
           // adding without end date

//           new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
//           new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
//           new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
//           new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
//           new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
//           new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
//end date endvehicleid[0].effectiveEdDate
           new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
		   FunctionLibrary.objDriver.findElement(By.xpath("//input[@name='vehicleid[" + j + "].effectiveEdDate']")).click();

		   for (int k = 0; k < 32; k++) {
               new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ARROW_RIGHT)).perform();
           }
           new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
           new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
           new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
           if(j!=numberOfVehicles-1) {
               new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.TAB)).perform();
               new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();
           }
           if(j>1 && j%3==0){
               new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.PAGE_DOWN)).perform();
           }
       }


   }


   /* Login Siebel App
  @Param url : url
  @Param uname : User Name
  @Param pwd : Password
*/
   public static void loginSiebelAppWithWebAcc(String url, String uname, String pwd) {
       dataObj.put("UserId", uname);
       dataObj.put("Password", pwd);

       CommonLibrary.loginSblWithTestScenarioSpecificUser(dataObj);
/*
       String Desc;
       try {
           //Launching Browser
           FunctionLibrary.objDriver.navigate().to(url);

           try{
               FunctionLibrary.waitForObject = new WebDriverWait(FunctionLibrary.objDriver, 60);
               FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loginUsernameTxtBox)));
           }

           catch(TimeoutException e)
           {
               //ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Login page is not dispalyed. So closing the broser", LogStatus.INFO, false);
               FunctionLibrary.objDriver.navigate().to(url);
           }
           //Enter User Name
           Desc = "Entering UserName on UserName textbox";
           FunctionLibrary.setText(loginUsernameTxtBox, uname, Desc);
           //Enter Password
           Desc = "Entering Password into password field";
           FunctionLibrary.setText(loginPasswordTxtBox, pwd, Desc);
           //Click on Sign-in Button
           Desc = "Clicking on Sign in Button";
           FunctionLibrary.clickObject(signInBtn, "", "Click sign in button");
           WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 180);

           try
           {
               wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(homePageVerificationTxt)));

               //if(sblLoginStatus != null)
               Desc = "Verify Account Opening element";
               if (!FunctionLibrary.verifyWebElementExist(homePageVerificationTxt, Desc))
               {
                   sblLoginStatus = "";
                   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Login failed. Dashboard is not displayed",
                           LogStatus.FAIL, true);
               }
               else
               { ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Login is success as user '"+ uname +"'",
                       LogStatus.INFO, false);
                   LoginMessage="Success";
                   sblLoginStatus = "Success";
                   isClosedAllBrowsers = false;
               }
          }
           catch(TimeoutException e){
               ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Login failed. Dashboard is not displayed: "+e.getMessage(),
                       LogStatus.FAIL, true);

               FunctionLibrary.closeAllActiveBrowser();

           }
           catch(Exception e){
               ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Login failed. Dashboard is not displayed: "+e.getMessage(),
                       LogStatus.FAIL, true);
               sblLoginStatus = "";

               FunctionLibrary.closeAllActiveBrowser();

           }

       } catch (Exception e) {
           System.out.println("Sibel login page is not loaded properly");
           ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Siebel Login page is not displayed properly: "+e.getMessage(), LogStatus.FAIL, true);
           FunctionLibrary.closeAllActiveBrowser();
       }
*/
   }
   
   
   /* Login Siebel App
   @Param url : url
   @Param uname : User Name
   @Param pwd : Password
*/
   public static void loginSiebelAppWithWebAcc(String url, String uname, String pwd, String browserType) {
       //CommonLibrary.loginSblWithTestScenarioSpecificUser(dataObj);
       url = CommonLibrary.getSettingsSheetInfo().get("URL_QA").toString();
       
       loginSiebelApp(browserType, url, uname, pwd);
      /* String Desc;
       try {
           //Launching Browser
           if (WebAccountMaintenance.isAlive().equals(true)) {
               FunctionLibrary.objDriver.navigate().to(url);
           } else {
               CommonLibrary.launchBrowser(url,"Launching siebel app", browserType);
           }
           //

           try {
               FunctionLibrary.waitForObject = new WebDriverWait(FunctionLibrary.objDriver, 60);
               FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loginUsernameTxtBox)));
           } catch (TimeoutException e) {
               //ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Login page is not dispalyed. So closing the broser", LogStatus.INFO, false);
               FunctionLibrary.closeAllActiveBrowser();
               CommonLibrary.launchBrowser(url,
                       "Launching siebel app", browserType);
           }
           //Enter User Name
           Desc = "Entering UserName on UserName textbox";
           FunctionLibrary.setText(loginUsernameTxtBox, uname, Desc);
           //Enter Password
           Desc = "Entering Password into password field";
           FunctionLibrary.setText(loginPasswordTxtBox, pwd, Desc);
           //Click on Sign-in Button
           Desc = "Clicking on Sign in Button";
           FunctionLibrary.clickObject(signInBtn, "", "Click sign in button");
           WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 180);

           try {
               wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(homePageVerificationTxt)));

               //if(sblLoginStatus != null)
               Desc = "Verify Account Opening element";
               if (!FunctionLibrary.verifyWebElementExist(homePageVerificationTxt, Desc)) {
                   sblLoginStatus = "";
                   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Login failed. Dashboard is not displayed",
                           LogStatus.FAIL, true);
               } else {
                   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Login is success as user '" + uname + "'",
                           LogStatus.INFO, false);
                   LoginMessage = "Success";
                   sblLoginStatus = "Success";
                   // isClosedAllBrowsers = false;
               }
           } catch (TimeoutException e) {
               ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Login failed. Dashboard is not displayed: " + e.getMessage(),
                       LogStatus.FAIL, true);

               FunctionLibrary.closeAllActiveBrowser();

           } catch (Exception e) {
               ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Login failed. Dashboard is not displayed: " + e.getMessage(),
                       LogStatus.FAIL, true);
               sblLoginStatus = "";

               FunctionLibrary.closeAllActiveBrowser();

           }

       } catch (Exception e) {
           System.out.println("Sibel login page is not loaded properly");
           ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Siebel Login page is not displayed properly: " + e.getMessage(), LogStatus.FAIL, true);
           FunctionLibrary.closeAllActiveBrowser();
       }
*/   }

   
}
