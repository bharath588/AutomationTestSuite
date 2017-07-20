package features;

import static objectProperties.SblBoxCheckInCheckOutProperties.boxesBoxListTxt;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.LogStatus;

import libraries.CommonLibrary;
import libraries.FunctionLibrary;
import libraries.ReportLibrary;

import static libraries.FunctionLibrary.closeAllActiveBrowser;
import static libraries.FunctionLibrary.objDriver;
import static objectProperties.SblBatchTagAssignmentProperties.*;
import static objectProperties.SblBoxCheckInCheckOutProperties.boxActualCountTxt;
import static objectProperties.SblPurchaseOrderProperties.boxReceivedCountValue;

public class DimsBatchTagAssignment {
                
    static String desc="";
    static String actualProcurementCost=null;
    static int lineItemProcurementCost = 0;
    String orderNbr = null;
    static String[] boxNumbers=null;
    static int iterator = 0;
                
    public static void DimsBatchTagAssignmentTest() throws IOException,Exception {
                  ExcelSheet exl=new ExcelSheet();
                 // String LoginMessage="NotSuccess";

                  HashMap<String,String> eachTestCaseData =new HashMap<String, String>();
                  //HashMap<String,String> eachTestCaseData2 =new HashMap<String, String>();


                  int noOfRows=exl.totalrows("FLCSS_DIMS_BatchTagAssignment","Test_Scenarios");
                  
                     for(int i=1;i<=noOfRows;i++)
                     {
                              eachTestCaseData=CommonLibrary.getEachTestCaseData(exl,"Test_Scenarios",i,"FLCSS_DIMS_BatchTagAssignment");
                              String executionType = CommonLibrary.settingsSheetInfo.get("Execution_Type");
                              
                              if ((eachTestCaseData.get("ExecutionStatus").equalsIgnoreCase("Yes")) && ( eachTestCaseData.get("TestCaseType").contains(executionType)|| 
                              		executionType.equalsIgnoreCase("All")))
                              {
                                                     
                                  try{                                              
                                            
                         ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"<b>"+
                      eachTestCaseData.get("TestCaseId")+"</b>"+": Test Case Execution is started..... <br>"
                       + "Test case description: " + eachTestCaseData.get("TestCaseDesc"), LogStatus.PASS, false);
                                              
                             //Login to Siebel Application
                              
                            CommonLibrary.loginSblWithGenericUser(eachTestCaseData);
                                                                                            
                          //Creating Purchase Order
                                                                 
                           try{                                  
                                
                           desc="Creating Purchase Order";
                           String poId = DimsPurchaseOrderAndReceive.createPurchaseOrderAndReturnId(eachTestCaseData);
                           System.out.println("Create po id : " + poId);
                                
                        //Receiving the Purchase Order        
                                
                           desc="Receiving Purchase Order";
                          DimsPurchaseOrderAndReceive.createPurchaseOrderLineItems(eachTestCaseData,poId);
                           
                          DimsPurchaseOrderAndReceive.receivePurchaseOrderLineItem(eachTestCaseData,poId);
                                
                       //Checking the No of Boxes received
                                
                            String noOfBoxes=null;
                            int actualBoxCount=0;
                            List<WebElement> boxNumber=FunctionLibrary.objDriver.findElements(By.xpath(boxActualCountTxt));
                            for(WebElement element:boxNumber){
                                   noOfBoxes=element.getAttribute("title");
                                   int BoxCount=Integer.parseInt(noOfBoxes);
                                   actualBoxCount=actualBoxCount+BoxCount;
                                }                                             
                               
                            Thread.sleep(10000);
       // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@id='1_s_1_l_Status' and @title='RECEIVED FULL']")));
                            FunctionLibrary.clickObject(".//*[@name='Order Number' and text()='"+poId+"']","",
                                                                    "Clicking order nuumber");
                            assert FunctionLibrary.verifyWebElementExist(boxesBoxListTxt,"Verify Box List element");
                            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "The no of boxes received are : "+
                                                                 actualBoxCount, LogStatus.INFO, false);
                  List<WebElement>noOfboxNbrLocation=FunctionLibrary.objDriver.findElements(By.xpath(boxReceivedCountValue));
                            System.out.println(noOfboxNbrLocation.size());
                            boxNumbers = new String[noOfboxNbrLocation.size()];
                             for (WebElement element : noOfboxNbrLocation) {
                                       boxNumbers[iterator]=element.getAttribute("title").toString();
                                       System.out.println(boxNumbers[iterator]);
                                            //iterator = iterator+1;
                               }
                                
          addBatchTagAssignmentParameter(eachTestCaseData);
                                                                               
           // runBatchTagAssignmentJob();
          
/*                 FunctionLibrary.clickObject(inventoryTab, "", "Clicking Inventory tab");
                 FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                		 batchTagAssignmenParametersTab,inventoryTab,3,20);
                 FunctionLibrary.clickObject(batchTagAssignmenParametersTab, "", "Clicking Batch Tag Assisgment Parameter tab");
                                 Thread.sleep(5000);
                       try{ 
               FunctionLibrary.clickObject(batchTagParameterQueryBtn,"", "Clicking query button");  
               FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
                         (batchTagParamaterGoBtn, batchTagParameterQueryBtn, 5, 30);
               FunctionLibrary.clickObject(batchTagParameterStartBoxNbrElement, "", "Clicking start box number field");
               FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
                                    (batchTagParameterStartBoxNbrTxtBox, batchTagParameterStartBoxNbrElement, 5, 30);
               FunctionLibrary.setText(batchTagParameterStartBoxNbrTxtBox, boxNumbers[iterator], "Entering box number");
               FunctionLibrary.clickObject(batchTagParamaterGoBtn, "", "Clicking on Go button");
               FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
                                         (batchTagParameterQueryBtn,batchTagParamaterGoBtn, 5, 30);
                  //int rownum1 = FunctionLibrary.getRowNumberFromWebTable(deviceHistoryTable, boxNumbers[iterator], "Get the row nunber");
                  
                 String totalDevicesProcessed=FunctionLibrary.objDriver.findElement(By.xpath
                                                 (totalDeviceProcessedCountTxt)).getAttribute("title");
                 int totalDevicesProcessedInteger=Integer.parseInt(totalDevicesProcessed);
                 if(totalDevicesProcessedInteger==0)
                 {
                 ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "The no of devices processed : "+
                                                totalDevicesProcessed, LogStatus.WARNING, false);
                 String totalDevicesCount=FunctionLibrary.objDriver.findElement(By.xpath
                                          (totalDeviceCountTxt)).getAttribute("title");
                    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "The no of devices currently present in the box "+
                    		boxNumbers[iterator]+" is : "+totalDevicesCount, LogStatus.WARNING, false);
                 }else
                 {
                	 ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "The no of devices processed : "+
                             totalDevicesProcessed, LogStatus.INFO, false);
                       String totalDevicesCount=FunctionLibrary.objDriver.findElement(By.xpath
                                                 (totalDeviceCountTxt)).getAttribute("title");
                ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "The no of devices currently present in the box "+
 		                              boxNumbers[iterator]+" is : "+totalDevicesCount, LogStatus.INFO, false);
                 }
                }catch(Exception e)
                {
                	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "All devices are being processed", LogStatus.INFO, false);
                }
                 */   
                               }catch(StaleElementReferenceException e)
                               {
                            	   
                               }
                
                           	CommonLibrary.logoutSiebelApplicaiton();
                    
                              
                            }catch(Exception e)                             
                                {e.printStackTrace();
                              // String Errormsg=e.getMessage();
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
                                   	 wait8.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='btn-accept']")));
                                           ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Execution Failed due to popup <br>", LogStatus.FAIL, true);
                                           objDriver.findElement(By.xpath("//*[@id='btn-accept']")).click();

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
                               }                           
                     }//End of main outer IF

                  }//End Of outer for loop


            }//End of Manual Tag Assignment Method
             
    public static void dataMorphingBeforeBatchTagAssignment(HashMap<String,String> dataObj) 
                                                  throws SQLException, ClassNotFoundException, InterruptedException
            {
                                            //Connecting to DataBase
     
       Class.forName("oracle.jdbc.driver.OracleDriver");
	    String connStringOracleDb = "jdbc:oracle:thin:@";
	    String userName = CommonLibrary.getSettingsSheetInfo().get("QA_USERNAME");	                
	    String password = CommonLibrary.getSettingsSheetInfo().get("QA_PASSWORD") ;    
	    String dbHostName=CommonLibrary.getSettingsSheetInfo().get("DataBaseHostName");
		String dbPort=CommonLibrary.getSettingsSheetInfo().get("DataBasePort");
		dbPort=dbPort.substring(0, 4);
		String dbServiceName=CommonLibrary.getSettingsSheetInfo().get("DataBaseServiceName");
		connStringOracleDb = connStringOracleDb+dbHostName+":"+dbPort+":"+dbServiceName;
        //step2 create  the connection object
        Connection con=DriverManager.getConnection(
        		connStringOracleDb,userName,password); 
Statement stmt=con.createStatement();
       
     // execute data morphing query depending on the status of the Device Request
       
       //if(deviceRequestStatus.equals("PAID")){
       
       String getDeviceRequest="select sr_num from siebel.s_srv_req where CST_OU_ID in"
                            + " (select row_id from siebel.s_org_ext where ou_num in ('"+dataObj.get("DataMorphingAccountNumbers")+"))"
                            +"and smart_issue_id='"+ dataObj.get("BatchTagParaDeviceModel")+"'";
       
       ResultSet rs=stmt.executeQuery(getDeviceRequest);
       String[] arr = null;
       String deviceRequest=null;
       String deviceRequestToBeMorphed=null;
       int i=0;
      while(rs.next()){
            String em = rs.getString("SR_NUM");
           arr = em.split("\n");
           for (i =0; i < arr.length; i++){
           deviceRequest=deviceRequest+"'"+arr[i] + "',";                                
           }}
      
      deviceRequestToBeMorphed=deviceRequest.substring(4, deviceRequest.length()-1);
      System.out.println(deviceRequestToBeMorphed);
            String murphPaidStatus="Update siebel.S_SRV_REQ set SR_STAT_ID='PAID21' where SR_STAT_ID='PAID' and publish_cd ='FLCCSS'"+ 
                "and SR_NUM not in ("+deviceRequestToBeMorphed+")";   
       stmt.executeQuery(murphPaidStatus);
       Thread.sleep(5000);
       stmt.executeQuery("commit");
       Thread.sleep(2000);
       String murphPartialStatus="Update siebel.S_SRV_REQ set SR_STAT_ID='PARTIAL FULFILLED21' where SR_STAT_ID='PARTIAL FULFILLED' and publish_cd ='FLCCSS'";
       stmt.executeQuery(murphPartialStatus);
       Thread.sleep(5000);
       stmt.executeQuery("commit");
       Thread.sleep(2000);
/*           }
           else if(deviceRequestStatus.equals("PARTIAL FULFILLED")){
                String murphPartialStatus="Update siebel.S_SRV_REQ set SR_STAT_ID='PARTIAL FULFILLED21' where SR_STAT_ID='PARTIAL FULFILLED' and publish_cd ='FLCCSS'"+ 
                    "and SR_NUM not in ('"+deviceRequestID+"')";   
           stmt.executeQuery(murphPartialStatus);
           Thread.sleep(5000);
                                   stmt.executeQuery("commit");
                                   Thread.sleep(2000);
                                   String murphPaidStatus="Update siebel.S_SRV_REQ set SR_STAT_ID='PAID21' where SR_STAT_ID='PAID' and publish_cd ='FLCCSS'";
                                   stmt.executeQuery(murphPaidStatus);
                                   Thread.sleep(5000);
                                   stmt.executeQuery("commit");
                                   Thread.sleep(2000);
           }*/
       
     //step5 close the connection object
       con.close();
       System.out.println("Data Morphing has been completed");
       ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Data Morphing has been completed",
               LogStatus.INFO, false);
            }
             
    public static void revertBackStatusAfterRunningBatchJob() throws SQLException, ClassNotFoundException, InterruptedException
  {
                            //Connecting to DataBase

  Class.forName("oracle.jdbc.driver.OracleDriver");
    String connStringOracleDb = "jdbc:oracle:thin:@";
    String userName = CommonLibrary.getSettingsSheetInfo().get("QA_USERNAME");	                
    String password = CommonLibrary.getSettingsSheetInfo().get("QA_PASSWORD") ;    
    String dbHostName=CommonLibrary.getSettingsSheetInfo().get("DataBaseHostName");
	String dbPort=CommonLibrary.getSettingsSheetInfo().get("DataBasePort");
	dbPort=dbPort.substring(0, 4);
	String dbServiceName=CommonLibrary.getSettingsSheetInfo().get("DataBaseServiceName");
	connStringOracleDb = connStringOracleDb+dbHostName+":"+dbPort+":"+dbServiceName;
  //step2 create  the connection object
      Connection con=DriverManager.getConnection(
      		connStringOracleDb,userName,password); 
Statement stmt=con.createStatement();
      stmt.executeQuery("Update siebel.S_SRV_REQ set SR_STAT_ID='PARTIAL FULFILLED' where SR_STAT_ID='PARTIAL FULFILLED21'"
                            + " and publish_cd ='FLCCSS'");
  Thread.sleep(3000);
  stmt.executeQuery("commit");
  Thread.sleep(1000);
  stmt.executeQuery("Update siebel.S_SRV_REQ set SR_STAT_ID='PAID' where SR_STAT_ID='PAID21' and publish_cd ='FLCCSS'");
  Thread.sleep(3000);
  stmt.executeQuery("commit");
  con.close();
  System.out.println("Device requests reverted back to the actual status");
  ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Device requests reverted back to the actual status",
          LogStatus.INFO, false);
  }
             
    public static void addBatchTagAssignmentParameter(HashMap<String,String> dataObj) throws InterruptedException
            {
    	try{
      WebDriverWait wait= new WebDriverWait(FunctionLibrary.objDriver, 30) ;                                   
                                            
     FunctionLibrary.clickObject(inventoryTab, "", "Clicking Inventory tab");
     FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
    		 batchTagAssignmenParametersTab,inventoryTab,5,40);
     FunctionLibrary.clickObject(batchTagAssignmenParametersTab, "", "Clicking Batch Tag Assisgment Parameter tab");
     FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
    		 batchTagAssignmentParameterNewBtn,batchTagAssignmenParametersTab,3,20);
     ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Creating a new batch request",
                               LogStatus.INFO, false);                          
    
     FunctionLibrary.clickObject(batchTagAssignmentParameterNewBtn, "", "Clicking New button");
    
   /*  try{
            FunctionLibrary.objDriver.findElement(By.xpath(popUpAcceptBtn)).click();
     }catch(Exception e)
     {
            System.out.println("No alert is present");
     }*/
     FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
    		 batchTagParameterAccountTypeTxtBox,batchTagAssignmentParameterNewBtn,3,20);
     FunctionLibrary.setText(batchTagParameterAccountTypeTxtBox, dataObj.get("AccountType"), "Entering Account Type");
     //Thread.sleep(3000);
     FunctionLibrary.clickObject(batchTagParameterAgencyElement, "", "Clicking Agency field");
     FunctionLibrary.setText(batchTagParameterAgencyTxtBox, dataObj.get("AgencyName"), "Entering Agency name");
     //Thread.sleep(3000);
     FunctionLibrary.clickObject(batchTagParameterMaxDeviceRqstToProcessElement, "", 
                                                                          "Clicking Max Device Request to process field");
     FunctionLibrary.setText(batchTagParamaterMaxDeviceRqstToProcessTxtBox, dataObj.get("MaxDeviceRequestToProcess"),
                                                                          "Entering Max Device Request to process count");
     FunctionLibrary.clickObject(batchTagParameterMaxDeviceAssgnPerRqstElement, "",
                                                                          "Clicking Max device assigned per request field");
     FunctionLibrary.setText(batchTagParameterMaxDeviceAssgnPerRqstTxtBox, dataObj.get("MaxDeviceAssignedPerReq"), 
                                                                          "Entering Max device assigned per request field");

     
     FunctionLibrary.clickObject(batchTagParameterStoreNameElement, "", "Clicking Store name field");
     FunctionLibrary.clickObject(batchTagParameterStoreNameApplet,"", "Clicking search button of Store name");
     FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
                        (appletPopup, batchTagParameterStoreNameApplet, 5, 30);
     FunctionLibrary.setText(storeNameTxtBox, dataObj.get("StoreName"), "");
     FunctionLibrary.clickObject(batchTagParameterStoreFindBtn, "", "Clicking find button");
     wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(batchTagParameterDeviceModelElement)));
     FunctionLibrary.clickObject(batchTagParameterDeviceModelElement, "", "Clicking Device Model field");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
            		batchTagParameterDeviceModelTxtBox,batchTagParameterDeviceModelElement,1,20);
            FunctionLibrary.setText(batchTagParameterDeviceModelTxtBox,dataObj.get("BatchTagParaDeviceModel"), "Entering Device Model ");

                    FunctionLibrary.clickObject(batchTagParameterStartBoxNbrElement, "", "Clicking Start box field");
                    FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    		startBoxNbrIcon,batchTagParameterStartBoxNbrElement,3,20);
                    FunctionLibrary.objDriver.findElement(By.xpath(startBoxNbrIcon)).click();
                    FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    		appletPopup,startBoxNbrIcon,3,20);
                  
                    FunctionLibrary.objDriver.findElement(By.xpath(boxPickListQueryBtn)).click();
                    FunctionLibrary.objDriver.findElement(By.xpath(boxNameTxtBox)).sendKeys(boxNumbers[iterator]);
                    //Thread.sleep(2000);
                    try{
                    FunctionLibrary.objDriver.findElement(By.xpath(boxPickListGoBtn)).click();
                    }catch(Exception e)
                    {
                            FunctionLibrary.objDriver.findElement(By.xpath(boxPickListGoBtn)).click();
                            FunctionLibrary.objDriver.findElement(By.xpath(boxPickListGoBtn)).click();
                    }
                    //Thread.sleep(2000);
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(boxPickListQueryBtn)));
                    FunctionLibrary.objDriver.findElement(By.xpath(boxPickListOkBtn)).click();
                    Thread.sleep(3000);
                    FunctionLibrary.clickObject(batchTagParameterEndBoxNbrElement, "", "Clicking End box number");
                    FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    		endBoxNbrTxtBox,batchTagParameterEndBoxNbrElement,1,20);
                    FunctionLibrary.objDriver.findElement(By.xpath(endBoxNbrIcon)).click();
                    FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    		appletPopup,endBoxNbrIcon,1,20);
                    FunctionLibrary.objDriver.findElement(By.xpath(boxPickListQueryBtn)).click();
                    FunctionLibrary.objDriver.findElement(By.xpath(boxNameTxtBox)).sendKeys(boxNumbers[iterator]);
                    //Thread.sleep(2000);
                    try{
                    FunctionLibrary.objDriver.findElement(By.xpath(boxPickListGoBtn)).click();
                    }catch(Exception e)
                    {
                            FunctionLibrary.objDriver.findElement(By.xpath(boxPickListGoBtn)).click();
                            FunctionLibrary.objDriver.findElement(By.xpath(boxPickListGoBtn)).click();
                    }
                    //Thread.sleep(2000);
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(boxPickListQueryBtn)));
                    FunctionLibrary.objDriver.findElement(By.xpath(boxPickListOkBtn)).click();
                    Thread.sleep(3000);
                    try{
                    FunctionLibrary.clickObject(batchTagParameterPrinterNameElement, "", "Clicking printer name field");
                    FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                    		batchTagParameterPrinterNameTxtBox,batchTagParameterPrinterNameElement,1,20);
                    FunctionLibrary.setText(batchTagParameterPrinterNameTxtBox, dataObj.get("PrinterName"), "Clicking  Printer name search button");
                    }catch(StaleElementReferenceException e){System.out.println(e);}
                    
                    new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.CONTROL,"s")).perform();
                                            
		        String totalDevicesCount=FunctionLibrary.objDriver.findElement(By.xpath
		                                      (totalDeviceCountTxt)).getAttribute("title");
		          ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "The no of devices currently present in the box : "+
		                               totalDevicesCount, LogStatus.INFO, false);
		                       
		     ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Batch Tag Assignment parameters :", LogStatus.INFO, true);
		   	 }
				 catch(Exception e){
		            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
		      }
       }
            
    public static void runBatchTagAssignmentJob() throws ClassNotFoundException, SQLException, InterruptedException
            
             {
                            //Navigating to SiteMap
     
    
        	
        	CommonLibrary.clickSiteMap();
        	
    WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 120);
            
     FunctionLibrary.clickObject(siteMapFilterTxtBox, "", "Clicking Site Map filter field");
     FunctionLibrary.setText(siteMapFilterTxtBox, "server management", "Clicking Site Map filter field");
     wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(serverManagementLink)));
     
     FunctionLibrary.clickObject(serverManagementLink, "", "Clicking Server Management link");
     wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(componentsTab)));
     FunctionLibrary.clickObject(componentsTab, "", "Clicking Component tab");
     FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
    		 componentsQueryBtn,componentsTab,3,20);
     Thread.sleep(3000);
     //Checking the status of the query
     FunctionLibrary.objDriver.findElement(By.xpath(componentsQueryBtn)).click();
     FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
    		 serviceFullNameElement,componentsQueryBtn,3,20);
     FunctionLibrary.objDriver.findElement(By.xpath(serviceFullNameElement)).sendKeys("VectorBatchTagAssgn");               
     FunctionLibrary.objDriver.findElement(By.xpath(componentsGoBtn)).click();
     wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(componentsQueryBtn)));
     String componentState=FunctionLibrary.objDriver.findElement(By.xpath(componentsStateTxt)).getAttribute("title");
     System.out.println(componentState);
              
     //Creating a new Job line item
     
     if(componentState.equalsIgnoreCase("Online"))
    	 
     {
    	 ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "The status of the batch job under Components tab is : "
                 +componentState,LogStatus.INFO, true);
         ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Running the Vector Batch Tag Assignment Job ",
                 LogStatus.INFO, false);
            FunctionLibrary.clickObject(jobsTab, "", "Clicking Jobs tab");
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(jobNewBtn,jobsTab,3,20);
            //Adding a new job details
            FunctionLibrary.objDriver.findElement(By.xpath(jobNewBtn)).click();
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(jobNameTxtBox,jobNewBtn,3,20);
            FunctionLibrary.objDriver.findElement(By.xpath(jobNameTxtBox)).sendKeys("VectorBatchTagAssgn");
         String jobId=FunctionLibrary.objDriver.findElement(By.xpath(jobIdTxt)).getAttribute("title");
         String currentJobStatusBeforeRun=FunctionLibrary.objDriver.findElement(By.xpath(jobStatusTxt)).getAttribute("title");
            FunctionLibrary.clickObject(submitJobButton, "", "Clicking Submit job button");
            Thread.sleep(3000);
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Status of the job after submit is : "
                                            +currentJobStatusBeforeRun, LogStatus.INFO, true);
     
            FunctionLibrary.objDriver.findElement(By.xpath(jobQueryBtn)).click();
            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
            		jobGoBtn,jobQueryBtn,3,20);
            FunctionLibrary.objDriver.findElement(By.xpath(jobIdTxt)).click();
            FunctionLibrary.objDriver.findElement(By.xpath(jobTxtBox)).sendKeys(jobId);
            FunctionLibrary.objDriver.findElement(By.xpath(jobGoBtn)).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(jobQueryBtn)));
            String currentJobStatusAfterRun=FunctionLibrary.objDriver.findElement(By.xpath
                                                            (jobStatusTxt)).getAttribute("title");
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Status of the job after submit is : "
                                            +currentJobStatusAfterRun, LogStatus.INFO, true);
            String currentJobStatus=null;
            for(int timeperiod=0;timeperiod<30;timeperiod++)
            {
                            
                            currentJobStatus=FunctionLibrary.objDriver.findElement(By.xpath
                                                            (jobStatusTxt)).getAttribute("title");
                            if(currentJobStatus.equalsIgnoreCase("Success"))
                            { 
                            	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Status of the job after submit is : "
                                        +currentJobStatus, LogStatus.INFO, true);
                              break;
                            }
                            else if(currentJobStatus.equalsIgnoreCase("Error"))
                            { 
                            	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Status of the job after submit is : "
                                        +currentJobStatus, LogStatus.FAIL, true);
                              break;
                            }
                           
                            else
                            {
                             if(!currentJobStatus.equals("Error") ){                                                                   
                             Thread.sleep(60000);
                             try{
                        //FunctionLibrary.clickObject("xpath=./[@id='btn-accept']","","Clicking Ok buttn");
                        WebDriverWait wait2 = new WebDriverWait(FunctionLibrary.objDriver,1);
                        wait2.until(ExpectedConditions.alertIsPresent());
                        Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                        alert.accept();                     
                               } catch(Exception e) {
                                   System.out.println("alert is not present");
                                            }
                                                            
                   new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ALT,Keys.ENTER)).perform();                                                                    
                           }
                         else{
                        	 break;
                        	 }
                   }
            }
            
             
             
   }else
   {
	   ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "The status of the batch job under Components tab is : "
               +componentState,LogStatus.WARNING, true);
   }
    
 }

}
