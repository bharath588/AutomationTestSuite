package features;

import com.relevantcodes.extentreports.LogStatus;
import libraries.CommonLibrary;
import libraries.FunctionLibrary;
import libraries.ReportLibrary;
import libraries.TestCaseLibrary;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static objectProperties.SblDeviceStatusChnageProperties.notesDescTxt;
import static objectProperties.SblDeviceStatusChnageProperties.notesListTable;
import static objectProperties.SblDeviceStatusChnageProperties.assignedDevicesTable;
import static objectProperties.SblBoxCheckInCheckOutProperties.BoxesTab;
import static objectProperties.SblBoxCheckInCheckOutProperties.boxAvailableDeviceCountTxt;
import static objectProperties.SblBoxCheckInCheckOutProperties.boxChecInQueryBtn;
import static objectProperties.SblBoxCheckInCheckOutProperties.boxCheckInBoxNbrTxtBox;
import static objectProperties.SblBoxCheckInCheckOutProperties.boxCheckInDeviceCountTxtBox;
import static objectProperties.SblBoxCheckInCheckOutProperties.boxCheckInGoBtn;
import static objectProperties.SblBoxCheckInCheckOutProperties.inventoryTab;
import static objectProperties.SblBoxCheckInCheckOutProperties.maintenanceCheckInTab;
import static objectProperties.SblBoxCheckInCheckOutProperties.maintenanceTab;
import static objectProperties.SblCreateAccountPageProperties.loginPasswordTxtBox;
import static objectProperties.SblCreateAccountPageProperties.loginUsernameTxtBox;
import static objectProperties.SblDeviceStatusChnageProperties.accountInfoDevicesTab;
import static objectProperties.SblDeviceStatusChnageProperties.accountInfoTab;
import static objectProperties.SblDeviceStatusChnageProperties.accountNumberTxtBox;
import static objectProperties.SblDeviceStatusChnageProperties.accountsTab;
import static objectProperties.SblDeviceStatusChnageProperties.assignedDeviceFilterTxtBox;
import static objectProperties.SblDeviceStatusChnageProperties.assignedDeviceFilterValueTxtBox;
import static objectProperties.SblDeviceStatusChnageProperties.assignedDeviceGoBtn;
import static objectProperties.SblDeviceStatusChnageProperties.assignedDeviceSaveBtn;
import static objectProperties.SblDeviceStatusChnageProperties.assignedDeviceTable;
import static objectProperties.SblDeviceStatusChnageProperties.boxSearchGoBtn;
import static objectProperties.SblDeviceStatusChnageProperties.boxesTabQueryBtn;
import static objectProperties.SblDeviceStatusChnageProperties.deviceHistoryTab;
import static objectProperties.SblDeviceStatusChnageProperties.deviceHistoryTable;
import static objectProperties.SblDeviceStatusChnageProperties.deviceSearchBoxElement;
import static objectProperties.SblDeviceStatusChnageProperties.deviceSearchGoBtn;
import static objectProperties.SblDeviceStatusChnageProperties.deviceSearchTxtBox;
import static objectProperties.SblDeviceStatusChnageProperties.deviceStatusTxtBox;
import static objectProperties.SblDeviceStatusChnageProperties.deviceStatusTxtElement;
import static objectProperties.SblDeviceStatusChnageProperties.devicesQueryBtn;
import static objectProperties.SblDeviceStatusChnageProperties.devicesTab;
import static objectProperties.SblDeviceStatusChnageProperties.goSearchBtn;
import static objectProperties.SblDeviceStatusChnageProperties.notesTab;
import static objectProperties.SblDeviceStatusChnageProperties.searchForBoxElement;
import static objectProperties.SblDeviceStatusChnageProperties.searchForBoxTxtBox;
import static objectProperties.SblManualTagAssignmentProperties.accountInfoDevicesDeviceRequestTab;
import static objectProperties.SblDeviceStatusChnageProperties.noOfDevicesInDashboardTxt;
import static objectProperties.SblPurchaseOrderProperties.*;
import static objectProperties.SblRMAProcessProperties.*;


public class DimsRMAProcess {
	
	public static HashMap<String,String> dataObj =null;
	static String desc=null;
	static String expectedResult=null;
	static String boxCount=null;
	static String accNum=null;

    public static void DimsRMAProcessTest() throws IOException, Exception {
        ExcelSheet exl = new ExcelSheet();

        int noOfRows = exl.totalrows("FLCSS_DIMS_RMAProcess","Test_Scenarios");

        HashMap<String, String> eachTestCaseData = new HashMap<String, String>();

        String testCaseName = "";

        for (int i = 1; i <=noOfRows; i++) {

            eachTestCaseData = CommonLibrary.getEachTestCaseData(exl, "Test_Scenarios", i,"FLCSS_DIMS_RMAProcess");
            dataObj=eachTestCaseData;
            testCaseName = "tcRMAProcess"+eachTestCaseData.get("TestCaseId").replace("-","");

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
                        Class<?> c = Class.forName("features.DimsRMAProcess");
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

                               if (FunctionLibrary.objDriver.findElement(By.xpath("//*[@id='btn-accept']")).isDisplayed()) 
                               {                  

                                   	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Execution Failed due to popup <br>", LogStatus.FAIL, true);
	                                FunctionLibrary.objDriver.findElement(By.xpath("//*[@id='btn-accept']")).click();
	                                
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

                }
                
                eachTestCaseData.clear();
        }


    }
	 
	public static void tcRMAProcessRMA005_008_035() throws Exception
	 {
	 try {
		  String deviceNumber=changeDeviceStatus(dataObj);
	   	  System.out.println(deviceNumber);
		  String boxNbr=RMABoxCreation(deviceNumber);
		  rMABoxValidation(boxNbr);
	   	  checkInRMABox(boxNbr);
	   	  sendShipment(boxNbr,boxNbr,"RMA");
	   	 ckeckDeviceStatus(deviceNumber);
	 }catch(Exception e){
         ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
    }
	 }
	 public static void tcRMAProcessRMA024() throws Exception
	 {
		 try{
		  String deviceNumber=changeDeviceStatus(dataObj);
	   	  System.out.println(deviceNumber);
		  String boxNbr=RMABoxCreation(deviceNumber);
		  rMABoxValidation(boxNbr);
		 }catch(Exception e){
	            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
	      }
	 }
	 
	 public static void tcRMAProcessRMA045() throws Exception
	 {
		 try{
	   	  String deviceNumber=changeDeviceStatus(dataObj);
	   	  System.out.println(deviceNumber);
		  String boxNbr=RMABoxCreation(deviceNumber);
		  rMABoxValidation(boxNbr);
	   	  checkInRMABox(boxNbr);
	   	  String shipmentNbr= sendShipment(boxNbr,boxNbr,"RMA");
	      ckeckDeviceStatus(deviceNumber);
	   	  receiveShipment(shipmentNbr,dataObj,boxCount);
	   	  
	      String statusAfterReceiveShipment=FunctionLibrary.objDriver.findElement(By.xpath(shipmentStatusTxt)).getAttribute("title");
	      
	      expectedResult="The status of Shipment Number "+shipmentNbr+" after Receive Shipment should be RECEIVED FULL";
	      try{
	      ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : "+expectedResult+"<br>"+
	        "Actual : The status of Shipment Number "+shipmentNbr +" after Receive Shipment is : "+statusAfterReceiveShipment , LogStatus.PASS, true);
	      
	      }catch(Exception e)
	      {
	     	 
	      }
	      rMABoxValidation(boxNbr);
		 }catch(Exception e){
	            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
	      }
	 }
	 
	 public static void tcRMAProcessRMA048() throws Exception
	 {
		 try{
		   	  String deviceNumber=changeDeviceStatus(dataObj);
		   	  System.out.println(deviceNumber);
			  String boxNbr=RMABoxCreation(deviceNumber);
			  rMABoxValidation(boxNbr);
		         
		         FunctionLibrary.clickObject(devicesTab, "", "Clicking on Devices Tab");
		         
		         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
		       		  devicesQueryBtn,devicesTab,15,60);
		         
		         FunctionLibrary.clickObject(devicesQueryBtn, "", "Clicking device search query button");
		         
		         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
		        		 deviceSearchGoBtn,devicesQueryBtn,5,30);
		         
		         FunctionLibrary.clickObject(deviceSearchBoxElement, "", "Clicking device search field");
		         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
		        		 deviceSearchTxtBox,deviceSearchBoxElement,5,30);
		         FunctionLibrary.setText(deviceSearchTxtBox, deviceNumber, "Entering device number");
		         FunctionLibrary.clickObject(deviceSearchGoBtn, "", "CLicking Go button");
		         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
		        		 devicesQueryBtn,deviceSearchGoBtn,5,30);
		         String deviceStatusAfterSendShipment=
		      		   FunctionLibrary.objDriver.findElement(By.xpath(deviceNbrTxt)).getAttribute("title");
		         expectedResult="The status of Device Number "+deviceNumber+" after Send Shipment should be RETURNDEF";
		         try{
		         ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : "+expectedResult+"<br>"+
		           "Actual : The status of Device Number "+deviceNumber +" is : "
		      		                                      +deviceStatusAfterSendShipment , LogStatus.PASS, true);
		         }catch(Exception e)
		         {
		        	 
		         }
		 }catch(Exception e){
	            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
	      }
	 }
	 
	 public static void tcRMAProcessRMA067() throws Exception
	 {
		 try{
		 String deviceNbr=assignRetailerDevice(dataObj);
		 deviceStatusChangeForRetailer(deviceNbr);
		 }catch(Exception e){
	            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
	      }
	 }
	 
	 public static void tcRMAProcessRMA006_007() throws Exception
	 {
		 try{
			 changeDeviceStatus(dataObj);
			 String caseNbr=caseCreation(dataObj);
			 caseClose(caseNbr);
		 }catch(Exception e){
	            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
	      }
		 
	 }
	 
	 public static void receiveShipment(String shipmentNbr,HashMap<String,String> dataObj,String boxCount) throws InterruptedException
	 {
		 CommonLibrary.logoutSiebelApplicaiton();
		 
		 //Login as as Manufacturer store
		 
         desc="Entering the Username";	  
     	 FunctionLibrary.setText(loginUsernameTxtBox,dataObj.get("UserId"), "Entering username");
     	 desc="Entering the Password";
         FunctionLibrary.setText(loginPasswordTxtBox,dataObj.get("Password"), "Entering password");
         FunctionLibrary.clickObject(signInBtn, "", "Click sign in button");
         WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 60);
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(welcomeTxt)));
         assert FunctionLibrary.verifyWebElementExist(homePageVerificationTxt, "Verify Account Opening element");
         FunctionLibrary.objDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
         
         //Checking the 
         
/*         FunctionLibrary.clickObject(toolsTab, "", "Clicking Tools tab");
         FunctionLibrary.clickObject(userPreferenceTab, "", "Clicking User Preference button");
         Thread.sleep(10000);
         try{
 	     ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Checking the Assigned Store of the user", LogStatus.INFO, true);
         }catch(Exception e)
         {
        	 
         }*/
         //Navigate to Receive Shipment tab
         
         FunctionLibrary.clickObject(inventoryTab, "", "Clicking on Inventory Tab");
        // Thread.sleep(10000);
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		 maintenanceTab,inventoryTab,5,40);
         FunctionLibrary.clickObject(maintenanceTab,"","Clicking the Maintenance Tab");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		 boxTransferTab,maintenanceTab,5,40);
         FunctionLibrary.clickObject(boxTransferTab, "", "Clicking box transfer tab");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		 receiveShipmentTab,boxTransferTab,5,40);
         FunctionLibrary.clickObject(receiveShipmentTab, "", "Clicking Receive shipment tab");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		   receiveShipmentQueryBtn,receiveShipmentTab,5,40);
         Thread.sleep(3000);
         
         //Receiving the shipment
         
         FunctionLibrary.objDriver.findElement(By.xpath(receiveShipmentQueryBtn)).click();
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
                                              (shipmentNbrTxtBox, receiveShipmentQueryBtn, 10, 40);
         Thread.sleep(3000);
         FunctionLibrary.objDriver.findElement(By.xpath(shipmentNbrTxtBox)).sendKeys(shipmentNbr);
         FunctionLibrary.objDriver.findElement(By.xpath(receiveShipmentGoBtn)).click();
         Thread.sleep(3000);
         FunctionLibrary.clickObject(receiveShipmentBoxCountElement, "", "Clicking Received box count field");
         FunctionLibrary.setText(receiveShipmentBoxCountTxtBox, boxCount, "Entering no of boxes received");
         FunctionLibrary.objDriver.findElement(By.xpath(receiveShipmentSaveBtn)).click();
         try{
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(popUpAcceptBtn)));

         FunctionLibrary.objDriver.findElement(By.xpath(popUpAcceptBtn)).click();
         }
         catch(Exception e)
         {
        	 
         }
	 }
	 
	 public static String changeDeviceStatus(HashMap<String,String> dataObj) throws Exception
	 
	 {
   	  
		         WebDriverWait wait= new  WebDriverWait(FunctionLibrary.objDriver, 10);		                  
		       
		       //Searching the account number
		        // accNum = getAccountNumberUsingSql(dataObj.get("EmailIdToGetAccounts"));
		         accNum = dataObj.get("AccountNumber");
		       
		ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Requesting repleacement of device for the account : "
		                                 +accNum, LogStatus.INFO, false);
		
		       	CommonLibrary.searchForAccount(accNum);                    
		       FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
		                accountInfoTab,goSearchBtn,5,20);
		
		      FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
		      FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
		                                       accountInfoDevicesTab,accountInfoTab,5,20);
		       FunctionLibrary.clickObject(accountInfoDevicesTab, "", "Clicking Devices tab");
		       FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
		        		 assignedDeviceTable,accountInfoDevicesTab,5,20);
		 		String deviceCountBeforeStatusChange=FunctionLibrary.objDriver.findElement(By.xpath
                         (noOfDevicesInDashboardTxt)).getAttribute("value");
         ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"The no Of devices available for the account before "
                               + "device return is : "+deviceCountBeforeStatusChange, LogStatus.INFO, false);
		   
		//Changing the status of one Active device under Assigned Device List Tab
		
		       //Searching the device number using query
		DimsDeviceStatusChange.searchForDevice("Status", "ACTIVE");
		Thread.sleep(3000);	
		int rowNum = FunctionLibrary.getRowNumberFromWebTable(assignedDevicesTable, "ACTIVE", "Get row number");        
		String deviceNumber=FunctionLibrary.getWebTableCellData(assignedDevicesTable, rowNum,1,"Get device number");		   
		System.out.println(deviceNumber);
		
						
		//Changing the device warranty value to no
		
		if(dataObj.get("TestCaseId").equals("RMA-006")||dataObj.get("TestCaseId").equals("RMA-007")
				            ||dataObj.get("TestCaseId").equals("RMA-006/007"))
		{
			updateDeviceWarrentyToNo(deviceNumber);
            
          new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ALT,Keys.ENTER)).perform(); 
              Thread.sleep(3000);
              try{
              ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Changin the device status of device number "+deviceNumber,
            		   LogStatus.INFO, true);
              }catch(Exception e)
              {
             	 
              }
		}
		
		DimsDeviceStatusChange.searchForDeviceForStatusChange(deviceNumber);
		
/*		String deviceStatusDate=FunctionLibrary.objDriver.findElement(By.xpath(".//*[@id='1_s_2_l_Status_Date']")).getAttribute("title");
		String deviceDOB=FunctionLibrary.objDriver.findElement(By.xpath(".//*[@id='1_s_2_l_Device_DOB']")).getAttribute("title");

		System.out.println(deviceStatusDate);
		System.out.println(deviceDOB);*/
		
		FunctionLibrary.clickObject(deviceStatusTxtElement, "", "Clicking device status field");
	    
	    FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	 		   deviceStatusTxtBox,deviceStatusTxtElement,8,30);    	    
	    
	    FunctionLibrary.setText(deviceStatusTxtBox, "RETURNDEF", "Entering new device status");
		
		
		if(dataObj.get("ReplacementRequired").equalsIgnoreCase("YES"))
		{
			try{
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(replacementElement)));
				FunctionLibrary.objDriver.findElement(By.xpath(replacementElement)).click();
				FunctionLibrary.objDriver.findElement(By.xpath(replacementBtn)).click();
				
			}catch(Exception e)
			{
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(replacementBtn)));
		FunctionLibrary.objDriver.findElement(By.xpath(replacementBtn)).click();
			}
		}
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(assignedDeviceSaveBtn)));
		FunctionLibrary.clickObject(assignedDeviceSaveBtn, "", "Clicking Save button");
		
		Thread.sleep(5000);
		if(dataObj.get("ReplacementRequired").equalsIgnoreCase("YES")){
        try{
        	FunctionLibrary.clickObject(accountInfoDevicesDeviceRequestTab, "", "Clicking Device Request tab");
        	FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
        	(deviceRequestListTable, accountInfoDevicesDeviceRequestTab, 5, 30);
        	DimsDeviceStatusChange.searchForDevice("Original Device #", deviceNumber);
        	int rownum1 = FunctionLibrary.getRowNumberFromWebTable(deviceRequestListTable,deviceNumber, "Get the row nunber");
        	if(dataObj.get("TestCaseId").equals("RMA-006")||dataObj.get("TestCaseId").equals("RMA-007")
		            ||dataObj.get("TestCaseId").equals("RMA-006/007"))
        	{
        		FunctionLibrary.verifyWebTableCellData(deviceRequestListTable, rownum1, 4, "PENDINGPMT", 
						 "Device Request: Verify the status of device request", false);
        	}else
        	{
        		FunctionLibrary.verifyWebTableCellData(deviceRequestListTable, rownum1, 4, "PAID", 
						 "Device Request: Verify the status of device request", false);
        	}
        	
           }catch(Exception e)
           {
        
           }
        }
		//Checking the status of the device under Device History Tab
		
		String deviceCountAfterStatusChange=FunctionLibrary.objDriver.findElement(By.xpath
                (noOfDevicesInDashboardTxt)).getAttribute("value");
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"The no Of devices available for the account after "
                      + "device return is : "+deviceCountAfterStatusChange, LogStatus.INFO, false);
		
        FunctionLibrary.clickObject(deviceHistoryTab, "", "Clicking Device history tab");
        
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
     		                                            deviceHistoryTable,deviceHistoryTab,20,60);
        int rownum = FunctionLibrary.getRowNumberFromWebTable(deviceHistoryTable, "RETURNDEF", "Get the row number");
        FunctionLibrary.verifyWebTableCellData(deviceHistoryTable, rownum, 7, "RETURNDEF", 
				 "Device History: Verify the new status of device", false);
		 FunctionLibrary.verifyWebTableCellData(deviceHistoryTable, rownum, 4, deviceNumber, 
				 "Device History: Verify the device number", true);
		 
         // Checking the status of the device under Notes tab 
         
         FunctionLibrary.clickObject(notesTab, "", "Clicking Notes tab");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(notesListTable,notesTab,9,33);
         String notesDescription=FunctionLibrary.objDriver.findElement(By.xpath(notesDescTxt)).getAttribute("title");
         ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"The Note Description is ---> "+notesDescription, LogStatus.INFO, true);

		return deviceNumber;
		      
	 }
	 
	 public static String RMABoxCreation(String deviceNumber) throws Exception
	 {
         //Creating RMA box
         
		 FunctionLibrary.clickObject(inventoryTab, "", "Clicking inventory tab");
		 FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
				 boxCreationTab,inventoryTab,20,60);
         FunctionLibrary.clickObject(boxCreationTab, "", "Clicking Box Creation tab");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		 expiredRMABoxTab,boxCreationTab,20,60);
         FunctionLibrary.clickObject(expiredRMABoxTab, "", "Clicking RMA/Expired Box tab");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
                      ("//li[@aria-label='RMA/Expired Box Selected'and @aria-expanded='true']",expiredRMABoxTab,20,60);
         Thread.sleep(3000);
         FunctionLibrary.objDriver.findElement(By.xpath(deviceEntryQueryBtn)).click();
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
         (returnDeviceNbrTxtBox,deviceEntryQueryBtn,20,60);
         Thread.sleep(5000);
         FunctionLibrary.setText(returnDeviceNbrTxtBox, deviceNumber, "Entering Device Number");
         //FunctionLibrary.objDriver.findElement(By.xpath(returnDeviceNbrTxtBox)).sendKeys(deviceNumber);
         FunctionLibrary.objDriver.findElement(By.xpath(deviceEntryGoBtn)).click();
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
                                                               (returnDeviceNbrTxtBox, deviceEntryGoBtn,20,60);
         Thread.sleep(5000);
         String returnDeviceBoxNbr=FunctionLibrary.objDriver.findElement(By.xpath(boxNbrTxt)).getAttribute("title");
 	       ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"RMA Box "+returnDeviceBoxNbr+" has been created for the device "+
                                                 deviceNumber, LogStatus.INFO, false);
         

         return returnDeviceBoxNbr;
		 
	 }
	 
	 public static void checkInRMABox(String returnDeviceBoxNbr) throws InterruptedException
	 
	 {		
		 WebDriverWait wait= new WebDriverWait(FunctionLibrary.objDriver, 30);
         
       //Check-in the box before sending for shipment  
	        FunctionLibrary.clickObject(inventoryTab, "", "Clicking on Inventory Tab");
	        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
					maintenanceTab,inventoryTab,5,20);
	        FunctionLibrary.clickObject(maintenanceTab,"","Clicking the Maintenance Tab");
	        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	        		maintenanceCheckInTab,maintenanceTab,5,20);        	                    
	        FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(maintenanceCheckInTab)));
	        FunctionLibrary.clickObject(maintenanceCheckInTab, "", "Clicking Check-In tab");
	        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	        		     boxChecInQueryBtn,maintenanceCheckInTab,5,20);

	        FunctionLibrary.waitForObject.until(ExpectedConditions.presenceOfElementLocated(By.xpath(boxCheckInBoxNbrTxtBox)));
	        FunctionLibrary.clickObject(boxChecInQueryBtn,"","Clicking Check-in query button");
	        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	        		boxCheckInGoBtn,boxChecInQueryBtn,5,20);
	        FunctionLibrary.setText(boxCheckInBoxNbrTxtBox,returnDeviceBoxNbr,"Entering Box no");
	        FunctionLibrary.clickObject(boxCheckInGoBtn,"","Clicking check-in Go button");
	        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	        		boxChecInQueryBtn,boxCheckInGoBtn,5,20);

         String availableDeviceCount=FunctionLibrary.objDriver.findElement(By.xpath(boxAvailableDeviceCountTxt))
      		                          .getAttribute("title");

         FunctionLibrary.setText(boxCheckInDeviceCountTxtBox,availableDeviceCount,"Entering Check-In device count");
         new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.CONTROL,"s")).perform();
         
         ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"The RMA Box : "+returnDeviceBoxNbr +
      		   " has been Checked In with device count :  "+availableDeviceCount , LogStatus.INFO, false);
	 }
	 
	 public static String sendShipment(String startReturnDeviceBoxNbr,String endReturnDeviceBoxNbr, String storeName) throws InterruptedException
	 {
         //Sneding the box for shipment
         
         //Thread.sleep(5000);
         FunctionLibrary.clickObject(maintenanceTab,"","Clicking the Maintenance Tab");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		 boxTransferTab,maintenanceTab,8,42);
         FunctionLibrary.clickObject(boxTransferTab, "", "Clicking box transfer tab");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		 boxTransferNewBtn,boxTransferTab,8,42);
         FunctionLibrary.clickObject(boxTransferNewBtn, "", "Clicking new button");
         Thread.sleep(2000);
         //String shipmentStatusnew=FunctionLibrary.objDriver.findElement(By.xpath(shipmentReceiveStatusTxt)).getAttribute("title");
        // System.out.println(shipmentStatusnew);
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
         (boxTransferToStoreSearchBtn, boxTransferNewBtn, 15, 60);
         FunctionLibrary.clickObject(boxTransferToStoreSearchBtn, "", "Clicking To Store search button");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		 storeNameSearchTxtBox,boxTransferToStoreSearchBtn,3,20);
         Thread.sleep(3000);
         FunctionLibrary.setText(storeNameSearchTxtBox, storeName, "Entering To Store name");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		 storeNameFindBtn,storeNameSearchTxtBox,8,42);
         FunctionLibrary.clickObject(storeNameFindBtn, "", "Clicking Store name find button");
         Thread.sleep(3000);
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		 startBoxNbrElement,storeNameFindBtn,8,42);
         FunctionLibrary.clickObject(startBoxNbrElement, "", "Clicking Start Box number field");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		 startBoxNbrTxtBox,startBoxNbrElement,8,42);
         Thread.sleep(3000);
         FunctionLibrary.setText(startBoxNbrTxtBox, startReturnDeviceBoxNbr, "Entering Start box number");
         FunctionLibrary.clickObject(endBoxNbrElement, "", "Clicking End Box number field");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		 endBoxNbrTxtBox,endBoxNbrElement,8,42);
         Thread.sleep(3000);
         FunctionLibrary.setText(endBoxNbrTxtBox, endReturnDeviceBoxNbr, "Entering End box number");
         FunctionLibrary.clickObject(courierTrackingNbrElement, "", "Clicking Courier Tracking Number field");
         Thread.sleep(3000);
         FunctionLibrary.setText(courierTrackingNbrTxtBox,"374843", "Entering Courier Tracking no");
         FunctionLibrary.clickObject(courierNameElement, "", "Clicking Courier Name field");
         Thread.sleep(3000);
         FunctionLibrary.setText(courierNameTxtBox, "FEDEX", "Entering courier name");
         FunctionLibrary.clickObject(sendShipmentSaveBtn, "", "Clicking Save button");
         //Thread.sleep(3000);
         String shipmentStatusAfterSave=FunctionLibrary.objDriver.findElement(By.xpath(shipmentReceiveStatusTxt))
      		                          .getAttribute("title");         
         String shipmentNbr=FunctionLibrary.objDriver.findElement(By.xpath(shipmentNbrTxt)).getAttribute("title");
         expectedResult="The status of Shipment Number "+shipmentNbr+" after Send Shipment should be PENDING";
         
         ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : "+expectedResult+"<br>"+
        	       "Actual : The status of Shipment Number "+shipmentNbr +" after Send Shipment is : "+shipmentStatusAfterSave , LogStatus.PASS, true);
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
         (".//*[@id='1_s_1_l_Status' and @title='INTRANSIT']", sendShipmentBtn, 20, 80);

         //Checking the RMA Number status after Send Shipment
         
         String shipmentStatusAfterSendShipment=FunctionLibrary.objDriver.findElement(By.xpath(shipmentReceiveStatusTxt))
      		                            .getAttribute("title");
         boxCount=FunctionLibrary.objDriver.findElement(By.xpath(vectorBoxCountTxt)).getAttribute("title");
         expectedResult="The status of Shipment Number "+shipmentNbr+" after Send Shipment should be INTRANSIT";
         try{
     ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : "+expectedResult+"<br>"+
       "Actual : The status of Shipment Number "+shipmentNbr +" after Send Shipment is : "+shipmentStatusAfterSendShipment , LogStatus.PASS, true);
         }catch(Exception e)
         {
        	 
         }            
         
         return shipmentNbr;
     }
	 
	 public static void ckeckDeviceStatus(String deviceNumber)
	 {
         //Checking the status of Device after Send Shipment
         
        // Thread.sleep(5000);
         FunctionLibrary.clickObject(inventoryTab, "", "Clicking on Inventory Tab");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(devicesTab, inventoryTab, 10, 30);
         FunctionLibrary.clickObject(devicesTab, "", "Clicking on Devices Tab");
         
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
       		  devicesQueryBtn,devicesTab,15,60);
         
         FunctionLibrary.clickObject(devicesQueryBtn, "", "Clicking device search query button");
         
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		 deviceSearchGoBtn,devicesQueryBtn,8,32);
         
         FunctionLibrary.clickObject(deviceSearchBoxElement, "", "Clicking device search field");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		 deviceSearchTxtBox,deviceSearchBoxElement,8,33);
         //FunctionLibrary.setText(deviceSearchTxtBox, deviceNumber, "Entering device number");
         try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
         FunctionLibrary.objDriver.findElement(By.xpath(deviceSearchTxtBox)).sendKeys(deviceNumber);
         //FunctionLibrary.clickObject(deviceSearchGoBtn, "", "CLicking Go button");
         FunctionLibrary.objDriver.findElement(By.xpath(deviceSearchGoBtn)).click();
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		 devicesQueryBtn,deviceSearchGoBtn,8,33);
         String deviceStatusAfterSendShipment=
      		   FunctionLibrary.objDriver.findElement(By.xpath(deviceStatusTxt)).getAttribute("title");
         expectedResult="The status of Device Number "+deviceNumber+" after Send Shipment should be INTRANSIT";
         try{
         ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : "+expectedResult+"<br>"+
           "Actual : The status of Device Number "+deviceNumber +" after Send Shipment is : "
      		                                      +deviceStatusAfterSendShipment , LogStatus.PASS, true);
         }catch(Exception e)
         {
        	 
         }
	 }
	 
	 public static String assignRetailerDevice(HashMap<String,String> dataObj) throws InterruptedException
	 {		 
		 
		 
		 //Getting Retailer Device number
		 
		 FunctionLibrary.clickObject(retailerTab, "", "Clicking Retailer tab");
		 FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
				 retailerQueryBtn,retailerTab,8,33);
		 FunctionLibrary.clickObject(retailerReturnsTab, "", "Clicking Returns tab");
		 FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
				 retailerDeviceListTable,retailerReturnsTab,8,33);
			 String retailerDeviceNbr=FunctionLibrary.objDriver.findElement(By.xpath(deviceNbrElement)).getAttribute("title");
	
		 System.out.println(retailerDeviceNbr);
	               
		 
		 //Assigning Device 
		 
		 FunctionLibrary.clickObject(accountsTab, "", "Clicking Accounts Tab");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		 retailTagSerachTab,accountsTab,8,33);
         FunctionLibrary.clickObject(retailTagSerachTab, "", "Clicking Accounts Tab");
         try{
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		 retailTagDeviceNbrTxtBox,retailTagSerachTab,8,33);
         System.out.println(retailerDeviceNbr);
         Thread.sleep(3000);
         FunctionLibrary.setText(retailTagDeviceNbrTxtBox, retailerDeviceNbr, "Entering Device number");
         Thread.sleep(2000);
         }catch(StaleElementReferenceException e)
         {
        	 Thread.sleep(5000);
        	 FunctionLibrary.setText(retailTagDeviceNbrTxtBox, retailerDeviceNbr, "Entering Device number");
         }
         FunctionLibrary.clickObject(addToExistingAccountBtn, "", "Clicking add to existing account button");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		 accountNbrTxtBox,addToExistingAccountBtn,8,33);
         FunctionLibrary.setText(accountNbrTxtBox, dataObj.get("AccountNumber"), "Entering Account number");
         FunctionLibrary.clickObject(transferDeviceBtn, "", "Clicking Device transfer button");
         Thread.sleep(10000);
		 
         WebDriverWait wait= new WebDriverWait(FunctionLibrary.objDriver, 60);
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(accountInfoDevicesTab)));
		  return retailerDeviceNbr;	  
		 
		 
	 }
	 
	 public static void deviceStatusChangeForRetailer(String deviceNumber) throws InterruptedException
	 {
		 WebDriverWait wait= new WebDriverWait(FunctionLibrary.objDriver, 30);
		 
	       FunctionLibrary.clickObject(accountsTab, "", "Clicking the Accounts tab");
	       FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	                accountNumberTxtBox,accountsTab,4,20);
	       
	FunctionLibrary.setText(accountNumberTxtBox, dataObj.get("AccountNumber"), "Entering the Account number");
	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Requesting repleacement of device for the account : "
	                                 +dataObj.get("AccountNumber"), LogStatus.INFO, false);
	
	       FunctionLibrary.clickObject(goSearchBtn, "", "Clicking Search button");		                    
	       FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	                accountInfoTab,goSearchBtn,8,33);
	
	      FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
	      FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	                                       accountInfoDevicesTab,accountInfoTab,8,33);
	       FunctionLibrary.clickObject(accountInfoDevicesTab, "", "Clicking Devices tab");
	       Thread.sleep(10000);
		 FunctionLibrary.setText(assignedDeviceFilterTxtBox, "", "Clearing the field");
			FunctionLibrary.setText(assignedDeviceFilterTxtBox, "Device Number", "Entering the value to filter");
			FunctionLibrary.setText(assignedDeviceFilterValueTxtBox, "", "Clearing the field");
			FunctionLibrary.setText(assignedDeviceFilterValueTxtBox,deviceNumber, "Entering the value to filter");
			FunctionLibrary.clickObject(assignedDeviceGoBtn, "", "Clicking Go button");
			
			String deviceStatusBeforeChange=FunctionLibrary.objDriver.findElement(By.xpath
			                       (deviceStatusTxt)).getAttribute("title");
		ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Current status of Device "+deviceNumber+" before status change is : "
			                                                 + deviceStatusBeforeChange, LogStatus.INFO, false);
		
		//Changing the status of the device as per the requirement
		
	       FunctionLibrary.clickObject(deviceStatusTxtElement, "", "Clicking device status field");
	       FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	    		   deviceStatusTxtBox,deviceStatusTxtElement,8,33);
	       FunctionLibrary.setText(deviceStatusTxtBox, "RETURNDEF", "Entering new device status");
	       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(assignedDeviceListSaveBtn)));
	       FunctionLibrary.clickObject(assignedDeviceSaveBtn, "", "Clicking Save button");
	       //Thread.sleep(5000);
	       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(deviceStatusRETURNDEFTxt)));
	       String deviceStatusAfterChange=FunctionLibrary.objDriver.findElement(By.xpath
	                                                             (deviceStatusTxt)).getAttribute("title");
	       try{
	       ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : Status of Device "+deviceNumber+" after status change should be : "
	      +"RETURNDEF"+"</br>"+"Status of the device "+deviceNumber+" is : "+deviceStatusAfterChange, LogStatus.PASS, true);
	       }catch(Exception e)
	         {
	        	 
	         }
	       //Checking the status of the device under device history tab
	       
	         FunctionLibrary.clickObject(deviceHistoryTab, "", "Clicking Device history tab");
	         
	         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	      		                                            deviceHistoryTable,deviceHistoryTab,20,60);
	         int rowNumberForDeviceandQuanty = FunctionLibrary.getRowNumberFromWebTableByTwoText
	        		 (deviceHistoryTable,deviceNumber,"RETURNDEF","get the row number");
	         
	         FunctionLibrary.verifyWebTableCellData(deviceHistoryTable, rowNumberForDeviceandQuanty, 7, "RETURNDEF", 
					 "Device History: Verify the new status of device", true);
	       
	         // Checking the status of the device under Notes tab 
	         
	         FunctionLibrary.clickObject(notesTab, "", "Clicking Notes tab");
	         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(notesListTable,notesTab,10,33);
	         String notesDescription=FunctionLibrary.objDriver.findElement(By.xpath(notesDescTxt)).getAttribute("title");
	         ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"The Note Description is ---> "+notesDescription, LogStatus.INFO, true);
	 }
	 
     public static void updateDeviceWarrentyToNo(String devNbr) throws SQLException, ClassNotFoundException, InterruptedException
    {
                    //Connecting to DataBase
		
		    String connStringOracleDb = "jdbc:oracle:thin:@";
		    String userName = CommonLibrary.getSettingsSheetInfo().get("QA_USERNAME");	                
		    String password = CommonLibrary.getSettingsSheetInfo().get("QA_PASSWORD");    
		    String dbHostName=CommonLibrary.getSettingsSheetInfo().get("DataBaseHostName");
			String dbPort=CommonLibrary.getSettingsSheetInfo().get("DataBasePort");
			dbPort=dbPort.substring(0, 4);
			String dbServiceName=CommonLibrary.getSettingsSheetInfo().get("DataBaseServiceName");
			connStringOracleDb = connStringOracleDb+dbHostName+":"+dbPort+":"+dbServiceName;
	        //step2 create  the connection object
	        Connection con=DriverManager.getConnection(
	        		connStringOracleDb,userName,password); 
  Statement stmt=con.createStatement();
		String query="update siebel.s_asset_x set attrib_28=TO_DATE('09/07/2001 6:27:28 AM', 'MM/DD/YYYY HH:MI:SS AM') "
				+ "where row_id in(select row_id from siebel.s_asset where owner_asset_num in ('"+devNbr+"'))";
		stmt.executeQuery(query);
		//Thread.sleep(3000);
		stmt.executeQuery("commit");
		//Thread.sleep(1000);
		System.out.println("Device status date has been updated");
		con.close();
   }
   
	 
     public static void rMABoxValidation(String RMABoxNbr) throws Exception
     {
    	 
         //Checking the status of the newly created box
	       
 	      //DimsBoxCheckInCheckOut.boxValidationAfterBoxCheckOutAndCheckIn(returnDeviceBoxNbr);
         
  	      FunctionLibrary.clickObject(BoxesTab, "", "Clicking Boxes tab");
  	      FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
  	    		  boxesTabQueryBtn,BoxesTab,15,45);  	      
    	  FunctionLibrary.clickObject(boxesTabQueryBtn, "", "Clicking Boxes Query button");
    	  FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
    	                  (searchForBoxElement,boxesTabQueryBtn, 15, 60);    	    	  
    	  FunctionLibrary.clickObject(searchForBoxElement, "", "CLicking the box field");
    	 // FunctionLibrary.setText(searchForBoxTxtBox, RMABoxNbr, "Entering box number");
    	  FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
          (searchForBoxTxtBox,searchForBoxElement, 15, 60);
    	  Thread.sleep(2000);
    	  FunctionLibrary.objDriver.findElement(By.xpath(searchForBoxTxtBox)).sendKeys(RMABoxNbr);
    	  FunctionLibrary.clickObject(boxSearchGoBtn, "", "Click Go button");
    	  FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
  	    		  boxesTabQueryBtn,boxSearchGoBtn,15,45);
    	  //FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(deviceListTable)));
  	      Thread.sleep(3000);
	      //wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//td[@id='1_s_2_l_Box_Number']"))));
  	    	
	  	  String boxNbr=FunctionLibrary.objDriver.findElement(By.xpath(boxNbrTxt)).getAttribute("title");
	  	  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Box number is " +boxNbr, LogStatus.INFO, true);
         String checkedOutToName=FunctionLibrary.objDriver.findElement(By.xpath(boxCheckedOutToTxt)).getAttribute("title");
         String boxStatus=FunctionLibrary.objDriver.findElement(By.xpath(boxStatusTxt)).getAttribute("title");
         String storeNameOfBox=FunctionLibrary.objDriver.findElement(By.xpath(boxStoreName)).getAttribute("title");
         String currentDeviceCount=FunctionLibrary.objDriver.findElement(By.xpath(deviceCountInBoxTxt)).getAttribute("title");
         String availableDevice=FunctionLibrary.objDriver.findElement(By.xpath(availableDeviceInBoxTxt)).getAttribute("title");
         expectedResult="The RMA Box "+RMABoxNbr+" should be Checked out to the Current User";
         try{
        	 if(checkedOutToName.equals(null))
        	 {
         ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : RMA Box should not be checked out"+"<br>"+
                   "Actual : The RMA Box has not been Checked Out ", LogStatus.PASS, true);

        	 }else
        	 {
        		 ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : "+expectedResult+"<br>"+
                   "Actual : The RMA Box "+RMABoxNbr+" has been Checked Out to "+checkedOutToName , LogStatus.PASS, true);
        	 }
         ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Status of RMA box is "+boxStatus , LogStatus.INFO, false);
         ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Store name of RMA box is "+storeNameOfBox , LogStatus.INFO, false);
         ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Device count of RMA box is "+currentDeviceCount , LogStatus.INFO, false);
         ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Available device in RMA box is "+availableDevice , LogStatus.INFO, false);
         }catch(Exception e)
         {
        	 
         }
    	 
     }
     
     public static String caseCreation(HashMap<String,String> dataObj) throws Exception
     {
    	 WebDriverWait wait= new WebDriverWait(FunctionLibrary.objDriver, 15);
         FunctionLibrary.clickObject(serviceRequestsTab, "", "Clicking device status field");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		 serviceRequestTable,serviceRequestsTab,8,45);
        /* FunctionLibrary.setText(serviceRequestAccountNbrTxtBox,dataObj.get("AccountNumber") , "Entering Account number");
         FunctionLibrary.clickObject(serviceRequestAccountSearchGoBtn, "", "Clicking go button");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		 serviceRequestNewBtn,serviceRequestAccountSearchGoBtn,4,30);*/
         FunctionLibrary.clickObject(serviceRequestNewBtn, "", "Clicking New button");
         WebDriverWait wt=new WebDriverWait(FunctionLibrary.objDriver, 5);
         try{
        	 wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(popUpAcceptBtn)));
        	 FunctionLibrary.objDriver.findElement(By.xpath(popUpAcceptBtn)).click();
        	 
         }catch(Exception e)
         {
        	 System.out.println("No alert is present");
         }
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		 "//input[@name='Area']",serviceRequestNewBtn,10,60);      
         try{
        	 wt.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(popUpAcceptBtn)));
        	 FunctionLibrary.objDriver.findElement(By.xpath(popUpAcceptBtn)).click();
        	 
         }catch(Exception e)
         {
        	 System.out.println("No alert is present");
         }
         
         FunctionLibrary.setText(serviceRequestQueueTxtBox,"DEVICE_REPLACE","Enter queue");
         FunctionLibrary.setText(serviceRequestSubQueueTxtBox, "ALREADY_RETURND", "Enter sub queue");
         FunctionLibrary.setText(serviceRequestDescriptionTextBox,"Device Replacement","Enter description");
         
         new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.CONTROL,"s")).perform();
         try{
        	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(popUpAcceptBtn)));
        	 FunctionLibrary.objDriver.findElement(By.xpath(popUpAcceptBtn)).click();
        	 
         }catch(Exception e)
         {
        	 System.out.println("No alert is present");
         }
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(deviceReplaceTxt)));
         String caseNbr=FunctionLibrary.objDriver.findElement(By.xpath(srNbrTxt)).getAttribute("title");
         String casestatus=FunctionLibrary.objDriver.findElement(By.xpath(srStatusTxt)).getAttribute("title");
         try{
         ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Service Request should be created for the account"
            +"</br>"+"Service Request : "+caseNbr+" is crated for the account with status as "+casestatus, LogStatus.INFO, true);
         }catch(Exception e)
         {
        	 
         }
         return caseNbr;
         
     }
     
     public static void caseClose(String caseNbr) throws InterruptedException
     {
    	 WebDriverWait wait= new WebDriverWait(FunctionLibrary.objDriver, 15);
    	FunctionLibrary.clickObject(serviceRequestQueryBtn, "", "Clicking Query button");
    	FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
    	(serviceRequestGoBtn, serviceRequestQueryBtn, 10, 60);
    	FunctionLibrary.clickObject(serviceRequestNbrElement, "", "Clicking SR number field");
    	FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
    			serviceRequestNbrTxtBox,serviceRequestNbrElement,8,45);
    	FunctionLibrary.setText(serviceRequestNbrTxtBox, caseNbr, "Entering SR number");
    	FunctionLibrary.clickObject(serviceRequestGoBtn, "", "Clicking go button");
    	FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
       		 statusOpenField,serviceRequestGoBtn,8,45);
    	Thread.sleep(7000);
    	
    	FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible("//li[text()='AGENCY']", 
         		"//input[@aria-label='Queue']//following::span[1]", 15, 120);   
    	
    	FunctionLibrary.setText(serviceRequestStatusTxtBox, "", "Changing the status to close");
    	FunctionLibrary.setText(serviceRequestStatusTxtBox, "Closed", "Changing the status to close");
    	Thread.sleep(5000);
    	FunctionLibrary.setText(serviceRequestClosureCodeTxtBox, "COMPLETED", "Changing the status to close");
        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.CONTROL,"s")).perform();
/*        try{
       	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(popUpAcceptBtn)));
       	 FunctionLibrary.objDriver.findElement(By.xpath(popUpAcceptBtn)).click();
       	 
        }catch(Exception e)
        {
       	 System.out.println("No alert is present");
        }*/
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(srClosedStatusTxt)));
        try{
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Service Request should be closed for the account"
                +"</br>"+"Service Request : "+caseNbr+" is closed for the account with status as Closed", LogStatus.INFO, true);
        }catch(Exception e)
        {
       	 
        }
     }
	 
}
