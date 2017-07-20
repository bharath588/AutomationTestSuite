package features;

import static features.DimsPurchaseOrderAndReceive.createPurchaseOrderAndReturnId;
import static libraries.FunctionLibrary.closeAllActiveBrowser;
import static libraries.FunctionLibrary.objDriver;
import static objectProperties.SblBoxCheckInCheckOutProperties.BoxesTab;
import static objectProperties.SblBoxCheckInCheckOutProperties.boxesBoxListTxt;
import static objectProperties.SblBoxCheckInCheckOutProperties.maintenanceTab;
import static objectProperties.SblPurchaseOrderProperties.inventoryTab;
import static objectProperties.SblRMAProcessProperties.boxCreationTab;
import static objectProperties.SblBoxCheckInCheckOutProperties.boxActualCountTxt;
import static objectProperties.SblPurchaseOrderProperties.boxReceivedCountValue;
import static objectProperties.SblDeviceStatusChnageProperties.*;
import java.io.IOException;
import java.lang.reflect.Method;
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
import libraries.TestCaseLibrary;

public class DimsDeviceStatusChange {
	
	public static HashMap<String,String> dataObj =null;
	static String desc=null;
	static String accNum=null;

    public static void DimsDeviceStatusChangeTest() throws IOException, Exception {
        ExcelSheet exl = new ExcelSheet();

        int noOfRows = exl.totalrows("FLCSS_DIMS_DeviceStatusChange","Test_Scenarios");

        HashMap<String, String> eachTestCaseData = new HashMap<String, String>();

        String testCaseName = "";

        for (int i = 1; i <=noOfRows; i++) {

            eachTestCaseData = CommonLibrary.getEachTestCaseData(exl, "Test_Scenarios", i,"FLCSS_DIMS_DeviceStatusChange");
            dataObj=eachTestCaseData;
            testCaseName = "tcDeviceStatusChange"+eachTestCaseData.get("TestCaseId").replace("-","");

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
                        Class<?> c = Class.forName("features.DimsDeviceStatusChange");
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
                   dataObj.clear();

                }
                
                eachTestCaseData.clear();
        }


    }
	 
	 public static void tcDeviceStatusChangeDEVST0001() throws Exception
	 {
		 try{
	   
	      searchAccount(dataObj);
	   		
	      //Checking the status of the device before status change
	      FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(deviceSearchBoxElement)));
      
	      searchForDevice("Status", "ACTIVE");
	      
		   int rowNum = FunctionLibrary.getRowNumberFromWebTable(assignedDevicesTable, "ACTIVE", "Get row number");
		         
		   String deviceNumber=FunctionLibrary.getWebTableCellData(assignedDevicesTable, rowNum,1,"Get device number");
		   ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Device number: "+deviceNumber,LogStatus.INFO,false);
		   
	     // searchForDevice("Device Number", deviceNumber);
		   searchForDeviceForStatusChange(deviceNumber);
			                                                
	      
	      changeStatusOfDevice(deviceNumber,dataObj.get("NewStatusOfDevice"));	
	   DimsManualTagAssignment.fieldVerificationInDataBase("siebel.s_asset","owner_asset_num", "x_device_status", deviceNumber);
	   String statusId=DimsManualTagAssignment.fieldVerificationInDataBase("siebel.v_device","device_no", "device_status", deviceNumber);
	   verificationInDataBase(statusId);
	   
	      verifyDeviceStatusChangeUnderDeviceHistoryAndNotes(deviceNumber,"LOST");
    	 }
		 catch(Exception e){
             ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
       }

       
	 }
	 
	 public static void tcDeviceStatusChangeDEVST0003() throws Exception
	 {
		 //String newStatus = dataObj.get("NewStatusOfDevice");
		 try{
		 //accNum = getAccountNumberUsingSql(dataObj.get("EmailIdToGetAccounts"));
			 accNum = dataObj.get("AccountNumber");
		 CommonLibrary.searchForAccount(accNum);
		 FunctionLibrary.clickObject(accountInfoTab, "", "Clickig Account info tab");
		 FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
		                         (plansTab, accountInfoTab, 10, 40);
		 FunctionLibrary.webdrvwaitByVisiblityofElementClickableByXpath(FunctionLibrary.objDriver, 30, enrollMDXBtn);
		 FunctionLibrary.clickObject(enrollMDXBtn, "", "Click enroll MDX button");
		 new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.CONTROL,"s")).perform();
		 ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"MDX is enabled for the account", LogStatus.INFO, true);
		 Thread.sleep(5000);
		 FunctionLibrary.clickObject(plansTab, "", "Clicking Plans Tab");
		 FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
		   (planDetailsTable, plansTab, 10, 40);
		 ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"STANDARD and PBR plans are added to the account", LogStatus.INFO, true);
/*		 int rowNumberForStandardPlan = FunctionLibrary.getRowNumberFromWebTable
				 (planDetailsTable, "STANDARD", "Get row number for STANDARD plan");
		 int rowNumberForPBRPlan = FunctionLibrary.getRowNumberFromWebTable
				 (planDetailsTable, "PBR", "Get row number for STANDARD plan");
		 FunctionLibrary.verifyWebTableCellData
		               (planDetailsTable, rowNumberForStandardPlan, 1, "STANDARD", "Checking Plan", true);
		 FunctionLibrary.verifyWebTableCellData
                        (planDetailsTable, rowNumberForPBRPlan, 1, "PBR", "Checking Plan", false);*/
		 
		 
         FunctionLibrary.clickObject(accountInfoDevicesTab, "", "Clicking Devices Tab");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		 assignedDeviceTable,accountInfoDevicesTab,5,20);
         
 		String deviceCountBeforeStatusChange=FunctionLibrary.objDriver.findElement(By.xpath
 				                                        (noOfDevicesInDashboardTxt)).getAttribute("value"); 		
 		
 		ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"The no Of devices available for the account before "
 				+ "device status change is : "+deviceCountBeforeStatusChange, LogStatus.INFO, false);
		 
	      FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(deviceSearchBoxElement)));	      
	      searchForDevice("Status", "ACTIVE");	      
		  int rowNum = FunctionLibrary.getRowNumberFromWebTable(assignedDevicesTable, "ACTIVE", "Get row number");		         
		  String deviceNumber=FunctionLibrary.getWebTableCellData(assignedDevicesTable, rowNum, 1,"Get device number");
		  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Device number: "+deviceNumber,LogStatus.INFO,false);
		      searchForDeviceForStatusChange(deviceNumber);	                                          

	      changeStatusOfDevice(deviceNumber, dataObj.get("NewStatusOfDevice"));
	      DimsManualTagAssignment.fieldVerificationInDataBase("siebel.s_asset","owner_asset_num", "x_device_status", deviceNumber);
		   String statusId=DimsManualTagAssignment.fieldVerificationInDataBase("siebel.v_device","device_no", "device_status", deviceNumber);
		   verificationInDataBase(statusId);
	      verifyDeviceStatusChangeUnderDeviceHistoryAndNotes(deviceNumber,dataObj.get("NewStatusOfDevice"));
		  }catch(Exception e){
	             ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
	       }
		 
	 }
	 
	 public static void tcDeviceStatusChangeDEVST0005() throws Exception
	 {
		 String newStatus = dataObj.get("NewStatusOfDevice");
		 try{
			   
		      searchAccount(dataObj);
			
		      //Checking the status of the device before status change
		      FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(deviceSearchBoxElement)));
	      
		      searchForDevice("Status", "ACTIVE");
		      
			   int rowNum = FunctionLibrary.getRowNumberFromWebTable(assignedDevicesTable, "ACTIVE", "Get row number");
			         
			   String deviceNumber=FunctionLibrary.getWebTableCellData(assignedDevicesTable, rowNum,1,"Get device number");

			  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Device number: "+deviceNumber,LogStatus.INFO,false);
			   
		      //searchForDevice("Device Number", deviceNumber);
			  searchForDeviceForStatusChange(deviceNumber);

				                                           
		      changeStatusOfDevice(deviceNumber, newStatus);
		      DimsManualTagAssignment.fieldVerificationInDataBase("siebel.s_asset","owner_asset_num", "x_device_status", deviceNumber);
			   String statusId=DimsManualTagAssignment.fieldVerificationInDataBase("siebel.v_device","device_no", "device_status", deviceNumber);
			   verificationInDataBase(statusId);
		      verifyDeviceStatusChangeUnderDeviceHistoryAndNotes(deviceNumber,newStatus);
	    	 }
			 catch(Exception e){
	             ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
	       }
	 }
	 
	 public static void tcDeviceStatusChangeDEVST0010() throws Exception
	 {
		 
		 try{
			   
		      searchAccount(dataObj);
			
		      //Checking the status of the device before status change
		      FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(deviceSearchBoxElement)));
	      
		      searchForDevice("Status", "ACTIVE");
		      
			   int rowNum = FunctionLibrary.getRowNumberFromWebTable(assignedDevicesTable, "ACTIVE", "Get row number");
			         
			   String deviceNumber=FunctionLibrary.getWebTableCellData(assignedDevicesTable, rowNum,1,"Get device number");
			   ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Device number: "+deviceNumber,LogStatus.INFO,false);
			   //searchForDevice("Device Number", deviceNumber);
			   searchForDeviceForStatusChange(deviceNumber);
     
		      changeStatusOfDevice(deviceNumber,dataObj.get("OldStatusOfDevice(if not ACTIVE)"));
		      searchForDeviceForStatusChange(deviceNumber);
		      changeStatusOfDevice(deviceNumber,dataObj.get("NewStatusOfDevice"));
		      DimsManualTagAssignment.fieldVerificationInDataBase("siebel.s_asset","owner_asset_num", "x_device_status", deviceNumber);
			   String statusId=DimsManualTagAssignment.fieldVerificationInDataBase("siebel.v_device","device_no", "device_status", deviceNumber);
			   verificationInDataBase(statusId);
		      verifyDeviceStatusChangeUnderDeviceHistoryAndNotes(deviceNumber,dataObj.get("NewStatusOfDevice"));
			
	        
	    	 }
			 catch(Exception e){
	             ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
	       }

		 
	 
	 }
	 
	 public static void tcDeviceStatusChangeDEVST0037() throws Exception
	 {
		 
		 try{
			   
		      searchAccount(dataObj);

			
		      //Checking the status of the device before status change
		      FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(deviceSearchBoxElement)));
	      
		      searchForDevice("Status", "ACTIVE");
		      
			   int rowNum = FunctionLibrary.getRowNumberFromWebTable(assignedDevicesTable, "ACTIVE", "Get row number");
			         
			   String deviceNumber=FunctionLibrary.getWebTableCellData(assignedDevicesTable, rowNum, 1,"Get device number");

			      ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Device number: "+deviceNumber,LogStatus.INFO,false);

			      searchForDeviceForStatusChange(deviceNumber);
				                                          

		      changeStatusOfDevice(deviceNumber, dataObj.get("OldStatusOfDevice(if not ACTIVE)"));
		      searchForDeviceForStatusChange(deviceNumber);
		      changeStatusOfDevice(deviceNumber, dataObj.get("NewStatusOfDevice"));
		      DimsManualTagAssignment.fieldVerificationInDataBase("siebel.s_asset","owner_asset_num", "x_device_status", deviceNumber);
			   String statusId=DimsManualTagAssignment.fieldVerificationInDataBase("siebel.v_device","device_no", "device_status", deviceNumber);
			   verificationInDataBase(statusId);
		      verifyDeviceStatusChangeUnderDeviceHistoryAndNotes(deviceNumber,dataObj.get("NewStatusOfDevice"));
	    	 }
			 catch(Exception e){
	             ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
	       }

	 }
	 
	 public static void tcDeviceStatusChangeDEVST0030() throws Exception
	 {
		 try{
		 WebDriverWait wait=new WebDriverWait(FunctionLibrary.objDriver, 120);
		 //Creating PO
		 
         desc="Creating Purchase Order";
         String poId = createPurchaseOrderAndReturnId(dataObj);
         System.out.println("Create po id : " + poId);
         
         
         //Receiving PO
         
         desc="Receiving Purchase Order";
         DimsPurchaseOrderAndReceive.createPurchaseOrderLineItems(dataObj,poId);
         
         DimsPurchaseOrderAndReceive.receivePurchaseOrderLineItem(dataObj,poId);
         
         Thread.sleep(5000);
         
         //Calculating no of Boxes
         	                    
         String noOfBoxes=null;
         int actualBoxCount=0;
         List<WebElement> boxNumber=FunctionLibrary.objDriver.findElements(By.xpath(boxActualCountTxt));
         for(WebElement element:boxNumber){
         	noOfBoxes=element.getAttribute("title");
         	int BoxCount=Integer.parseInt(noOfBoxes);
         	actualBoxCount=actualBoxCount+BoxCount;
         }	               	                    
        
        System.out.println("No of boxes are : "+ actualBoxCount);
    	                   
         
       //Code to get the box numbers
        
         Thread.sleep(10000);//needs some time to load the Purchase Order no
         FunctionLibrary.clickObject(".//*[@name='Order Number' and text()='"+poId+"']","",
         		        "Clicking order nuumber");
         assert FunctionLibrary.verifyWebElementExist(boxesBoxListTxt,"Verify Box List element");
         
		    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "The no of boxes received are : "+
			                                actualBoxCount, LogStatus.INFO, false);
			List<WebElement>noOfboxNbrLocation=FunctionLibrary.objDriver.findElements(By.xpath(boxReceivedCountValue));
			System.out.println(noOfboxNbrLocation.size());
			String[] boxNumbers = new String[noOfboxNbrLocation.size()];
			int iterator = 0;
			for (WebElement element : noOfboxNbrLocation) {
				boxNumbers[iterator]=element.getAttribute("title").toString();
				iterator = iterator+1;
			       }
			
			//Iterating for multiple boxes
			
		     for (int boxNoIterator=0;boxNoIterator<noOfboxNbrLocation.size();boxNoIterator++)
		     {	        	  
	    	      /*FunctionLibrary.clickObject(BoxesTab, "", "Clicking Boxes tab");
	    	      FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	    	    		  boxesTabQueryBtn,BoxesTab,3,20);*/
	        	  FunctionLibrary.clickObject(boxesTabQueryBtn, "", "Clicking Boxes Query button");
	        	  
	        	  FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
	        	  (searchForBoxElement,boxesTabQueryBtn, 15, 60);
	        	  
	        	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchForBoxElement)));
	        	  
	        	  FunctionLibrary.clickObject(searchForBoxElement, "", "CLicking the box field");
	        	  FunctionLibrary.setText(searchForBoxTxtBox, boxNumbers[boxNoIterator], "Entering box number");	        	             
	        	  FunctionLibrary.clickObject(boxSearchGoBtn, "", "Click Go button");
	        	  //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(deviceListTable)));
	        	  FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
	        	  (boxesTabQueryBtn,boxSearchGoBtn, 5, 60);
	        	  
	        	  String noOfDevicesInsideBoxDisplayed=FunctionLibrary.objDriver.findElement(By.xpath
	        			  (deviceCountInBoxTxt)).getAttribute("title");
	        	  Integer.parseInt(noOfDevicesInsideBoxDisplayed);
	        	  
	        	  //Iterating for multiple devices
	        	  
	        	/*for(int deviceCount=0;deviceCount<=noOfDevicePresent;deviceCount++)
	        	   {*/
	        	  String deviceNumber=FunctionLibrary.objDriver.findElement(By.xpath(deviceSearchBoxElement1))
	        			                     .getAttribute("title");
	              FunctionLibrary.clickObject(devicesTab, "", "Clicking on Devices Tab");
	              
	              FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	            		  devicesQueryBtn,devicesTab,15,60);
	              
	              FunctionLibrary.clickObject(devicesQueryBtn, "", "Clicking device search query button");
	              
	              FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	            		  deviceSearchBoxElement,devicesQueryBtn,5,30);
	              
	             // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(deviceListTable)));
	              FunctionLibrary.clickObject(deviceSearchBoxElement, "", "Clicking device search field");
	              FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
	              (deviceSearchTxtBox, deviceSearchBoxElement, 5, 30);
	              FunctionLibrary.objDriver.findElement(By.xpath(deviceSearchTxtBox)).sendKeys(deviceNumber);
	              FunctionLibrary.clickObject(deviceSearchGoBtn, "", "CLicking Go button");
	              FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisibleWebapp
	              (deviceListQueryBtn, deviceSearchGoBtn, 5, 60);
	              //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(deviceListQueryBtn)));
	              String deviceStatusBeforeChange=
	           		   FunctionLibrary.objDriver.findElement(By.xpath(deviceStatusTxt)).getAttribute("title");
	              
	              ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Status of the device "+deviceNumber
	                      + " before status change is : "+deviceStatusBeforeChange, LogStatus.INFO, true);             
	              
		                // }
	              
	            //Changing the device status from inventory to tested
	              
	              FunctionLibrary.clickObject("//span[@id='s_1_1_40_0_icon']", "", "");
	              FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
	              ("//div[@name='popup']", "//span[@id='s_1_1_40_0_icon']", 5, 30);
	              FunctionLibrary.clickObject("//td[contains(text(),'"+dataObj.get("OldStatusOfDevice(if not ACTIVE)")+"')]", "", "");
	              FunctionLibrary.clickObject("//button[@title='Device Status PickApplet:Ok']", "", "");
	              
	              /*FunctionLibrary.setText(deviceStatusChangeTxtBox, dataObj.get("OldStatusOfDevice(if not ACTIVE)"),
	            		                                 "Changing the device status for the first time");
	              new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.CONTROL,"s")).perform();*/
	              wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath
	            		("//td[@id='1_s_2_l_Device_Status' and @title='"+dataObj.get("OldStatusOfDevice(if not ACTIVE)")+"']")));
	              
	              String deviceStatusAfterFirstChange=
		           		   FunctionLibrary.objDriver.findElement(By.xpath(deviceStatusTxt)).getAttribute("title");
		              
		         ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Status of the device "+deviceNumber
		                      + " after first status change is : "+deviceStatusAfterFirstChange, LogStatus.INFO, true); 
		              
			            //Changing the device status from tested to returned defective
		         
		         try{
		        	
		        	 Thread.sleep(5000);
		         FunctionLibrary.clickObject(deviceStatusChangeTxtBox,"","Clicking the device status text box");	 
		              
		         FunctionLibrary.setText(deviceStatusChangeTxtBox, dataObj.get("NewStatusOfDevice"),
		            		                                 "Changing the device status for the second time");
		         new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.CONTROL,"s")).perform();
		         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath
		            ("//td[@id='1_s_2_l_Device_Status' and @title='"+dataObj.get("NewStatusOfDevice")+"']")));
		         }catch(StaleElementReferenceException e)
		         {
		        	 
		         }
		              
		         String deviceStatusAftersecondChange=
			          FunctionLibrary.objDriver.findElement(By.xpath(deviceStatusTxt)).getAttribute("title");
			              
			     ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : Status of the device "+deviceNumber
			                 + " should be RETURNDEF : "+"</br>"+"Actual : The status of the device "+deviceNumber
			    		              +" after status change is "+deviceStatusAftersecondChange, LogStatus.INFO, true);      
	              
	              
		     }
		 }
		 catch(Exception e){
             ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
       } 	  
		 
	 }
	 
	 public static void tcDeviceStatusChangeDEVST0044() throws Exception
	 {
		 
		 try{
		String deviceStatus = "ACTIVE";
		 searchAccount(dataObj);
		
	      //Checking the status of the device before status change
	      FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(deviceSearchBoxElement)));
     
	      searchForDevice("Status", deviceStatus);
	      
		   int rowNum = FunctionLibrary.getRowNumberFromWebTable(assignedDevicesTable, "ACTIVE", "Get row number");
		         
		   String deviceNumber=    FunctionLibrary.getWebTableCellData(assignedDevicesTable, rowNum,
		        		 1,"Get device number");
		   
	     // searchForDevice("Device Number", deviceNumber);
		   searchForDeviceForStatusChange(deviceNumber);

	      ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Device number: "+deviceNumber,LogStatus.INFO,false);
			                                           
		 
		//Checking Device status under Devices tab
		
        FunctionLibrary.clickObject(inventoryTab, "", "Clicking on Devices Tab");
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
      		  devicesQueryBtn,inventoryTab,3,20);
        FunctionLibrary.clickObject(devicesQueryBtn, "", "Clicking device search query button");
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
        (deviceSearchBoxElement, devicesQueryBtn, 5, 60);
       // FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(deviceListTable)));
        //Thread.sleep(5000);
        FunctionLibrary.clickObject(deviceSearchBoxElement, "", "Clicking device search field");
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
        (deviceSearchTxtBox, deviceSearchBoxElement, 5, 60);
        FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(deviceSearchTxtBox)));
        //FunctionLibrary.setText(deviceSearchTxtBox, deviceNumber, "Entering device number");
        FunctionLibrary.objDriver.findElement(By.xpath(deviceSearchTxtBox)).sendKeys(deviceNumber);
        FunctionLibrary.clickObject(deviceSearchGoBtn, "", "CLicking Go button");
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
        (deviceListQueryBtn, deviceSearchGoBtn, 5, 60);
        FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(deviceListQueryBtn)));
        
    	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Status of the device "+deviceNumber+" before "
                + "device status change is : "+deviceStatus, LogStatus.INFO, true);
    	
    	//Creating miscellanious box for the device
    	
        FunctionLibrary.clickObject(maintenanceTab,"","Clicking the Maintenance Tab");
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		deviceReturnsTab,maintenanceTab,3,20);
        FunctionLibrary.clickObject(deviceReturnsTab,"","Clicking Device Returns Tab");
        
        
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		deviceReturnsQueryBtn,deviceReturnsTab,10,60);
        FunctionLibrary.clickObject(deviceReturnsQueryBtn, "", "Clicking Query button");
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
                        ( deviceEntryCancelBtn,deviceReturnsQueryBtn, 4, 20);
        FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(deviceEntryCancelBtn)));
        System.out.println(deviceNumber);
        try{
        FunctionLibrary.setText(deviceNumberTxtBox, deviceNumber, "Entering the device number");
        FunctionLibrary.clickObject(deviceReturnsGoBtn, "", "Clicking search go button");
        }catch(StaleElementReferenceException e)
        {
        	System.out.println(e);
        }
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		miscExpiredBoxListTable,deviceReturnsGoBtn,3,60);
        String miscBoxNbr=FunctionLibrary.objDriver.findElement(By.xpath(boxNbrTxt)).getAttribute("title");
        String boxType=FunctionLibrary.objDriver.findElement(By.xpath(boxTypeTxt)).getAttribute("title");
        System.out.println(boxType);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : Miscellanious box should be created"+"</br>"+
              "Actual ; Box "+miscBoxNbr+" has been created for device "+deviceNumber+" with box typs as "
                                                         +boxType, LogStatus.PASS, true);
		 }
		 catch(Exception e){
             ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
       }
		
	 }
	 
	 public static void tcDeviceStatusChangeDEVST0004() throws Exception
	 {
		 String newStatus = dataObj.get("NewStatusOfDevice");
		 try{
			   
		      searchAccount(dataObj);   
		     
			
		      //Checking the status of the device before status change
		      FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(deviceSearchBoxElement)));
	      
		      searchForDevice("Status", "ACTIVE");
		      
			   int rowNum = FunctionLibrary.getRowNumberFromWebTable(assignedDevicesTable, "ACTIVE", "Get row number");
			         
			   String deviceNumber= FunctionLibrary.getWebTableCellData(assignedDevicesTable, rowNum,1,"Get device number");
			   ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Device number : "+deviceNumber,LogStatus.INFO, false);
			   updateStatusDateOfDevice(deviceNumber);
			   
		      //searchForDevice("Device Number", deviceNumber);
			   searchForDeviceForStatusChange(deviceNumber);

		      changeStatusOfDevice(deviceNumber, newStatus);
		      DimsManualTagAssignment.fieldVerificationInDataBase("siebel.s_asset","owner_asset_num", "x_device_status", deviceNumber);

			   String statusId=DimsManualTagAssignment.fieldVerificationInDataBase("siebel.v_device","device_no", "device_status", deviceNumber);
			   verificationInDataBase(statusId);
		      verifyDeviceStatusChangeUnderDeviceHistoryAndNotes(deviceNumber,newStatus);
	    	 }
			 catch(Exception e){
	             ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
	       }
		 

	 }
	 
	 public static void tcDeviceStatusChangeDEVST0039() throws Exception
	 {
		 try{
		 WebDriverWait wait=new WebDriverWait(FunctionLibrary.objDriver, 120);
		 //Creating PO
		 
         desc="Creating Purchase Order";
         String poId = createPurchaseOrderAndReturnId(dataObj);
         System.out.println("Create po id : " + poId);
         
         
         //Receiving PO
         
         desc="Receiving Purchase Order";
         DimsPurchaseOrderAndReceive.createPurchaseOrderLineItems(dataObj,poId);
         
         DimsPurchaseOrderAndReceive.receivePurchaseOrderLineItem(dataObj,poId);
         
         Thread.sleep(5000);
         
         //Calculating no of Boxes
         	                    
         String noOfBoxes=null;
         int actualBoxCount=0;
         List<WebElement> boxNumber=FunctionLibrary.objDriver.findElements(By.xpath(boxActualCountTxt));
         for(WebElement element:boxNumber){
         	noOfBoxes=element.getAttribute("title");
         	int BoxCount=Integer.parseInt(noOfBoxes);
         	actualBoxCount=actualBoxCount+BoxCount;
         }	               	                    
        
        System.out.println("No of boxes are : "+ actualBoxCount);
    	                   
         
       //Code to get the box numbers
        
         Thread.sleep(10000);//needs some time to load the Purchase Order no
         FunctionLibrary.clickObject(".//*[@name='Order Number' and text()='"+poId+"']","",
         		        "Clicking order nuumber");
         assert FunctionLibrary.verifyWebElementExist(boxesBoxListTxt,"Verify Box List element");
         
		    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "The no of boxes received are : "+
			                                actualBoxCount, LogStatus.INFO, false);
			List<WebElement>noOfboxNbrLocation=FunctionLibrary.objDriver.findElements(By.xpath(boxReceivedCountValue));
			System.out.println(noOfboxNbrLocation.size());
			String[] boxNumbers = new String[noOfboxNbrLocation.size()];
			int iterator = 0;
			for (WebElement element : noOfboxNbrLocation) {
				boxNumbers[iterator]=element.getAttribute("title").toString();
				iterator = iterator+1;
			       }
			
			//Iterating for multiple boxes
			
		     for (int boxNoIterator=0;boxNoIterator<noOfboxNbrLocation.size();boxNoIterator++)
		     {	        	  
	    	      FunctionLibrary.clickObject(BoxesTab, "", "Clicking Boxes tab");
	    	      FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	    	    		  boxesTabQueryBtn,BoxesTab,3,20);
	    	      
	        	  FunctionLibrary.clickObject(boxesTabQueryBtn, "", "Clicking Boxes Query button");

	        	  FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(boxListGoBtn, 
	        			  boxesTabQueryBtn, 15, 60);
	        	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(boxListGoBtn)));
	        	  
	        	  FunctionLibrary.clickObject(searchForBoxElement, "", "CLicking the box field");
	        	  FunctionLibrary.setText(searchForBoxTxtBox, boxNumbers[boxNoIterator], "Entering box number");	        	             
	        	  FunctionLibrary.clickObject(boxSearchGoBtn, "", "Click Go button");
	        	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(deviceListTable)));
	        	  
	        	  String noOfDevicesInsideBoxDisplayed=FunctionLibrary.objDriver.findElement(By.xpath
	        			  (deviceCountInBoxTxt)).getAttribute("title");
	        	  Integer.parseInt(noOfDevicesInsideBoxDisplayed);
	        	  
	        	  //Iterating for multiple devices
	        	  
	        	/*for(int deviceCount=0;deviceCount<=noOfDevicePresent;deviceCount++)
	        	   {*/
	        	  String deviceNumber=FunctionLibrary.objDriver.findElement(By.xpath(deviceNbrTxt)).getAttribute("title");
	              FunctionLibrary.clickObject(devicesTab, "", "Clicking on Devices Tab");
	              FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	            		  devicesQueryBtn,devicesTab,3,20);
	              FunctionLibrary.clickObject(devicesQueryBtn, "", "Clicking device search query button");
	              
	              FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	            		  deviceListGoBtn,devicesQueryBtn,5,30);
	              
	              FunctionLibrary.clickObject(deviceSearchBoxElement, "", "Clicking device search field");
	              wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(deviceSearchTxtBox)));
	             // FunctionLibrary.setText(deviceSearchTxtBox, deviceNumber, "Entering device number");
	              FunctionLibrary.objDriver.findElement(By.xpath(deviceSearchTxtBox)).sendKeys(deviceNumber);
	              FunctionLibrary.clickObject(deviceSearchGoBtn, "", "CLicking Go button");
	              wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(deviceListQueryBtn)));
	              String deviceStatusBeforeChange=
	           		   FunctionLibrary.objDriver.findElement(By.xpath(deviceStatusTxt)).getAttribute("title");
	              
	              ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Status of the device "+deviceNumber
	                      + " before status change is : "+deviceStatusBeforeChange, LogStatus.INFO, true);             
	              
		                // }
	              
	            //Changing the device status from inventory to tested
	              
	              FunctionLibrary.setText(deviceStatusChangeTxtBox, dataObj.get("OldStatusOfDevice(if not ACTIVE)"),
	            		                                 "Changing the device status for the first time");
	              new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.CONTROL,"s")).perform();
	              wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath
	            		("//td[@id='1_s_2_l_Device_Status' and @title='"+dataObj.get("OldStatusOfDevice(if not ACTIVE)")+"']")));
	              
	              String deviceStatusAfterFirstChange=
		           		   FunctionLibrary.objDriver.findElement(By.xpath(deviceStatusTxt)).getAttribute("title");
		              
		         ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Status of the device "+deviceNumber
		                      + " after first status change is : "+deviceStatusAfterFirstChange, LogStatus.INFO, true); 
		         
		         //Creating box for the device
		         
		         FunctionLibrary.clickObject(boxCreationTab, "", "Clicking Box Creation tab");
		         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
		        		 deviceEntryFormTab,boxCreationTab,3,20);		         
		         FunctionLibrary.clickObject(deviceEntryQueryBtn, "", "Clicking Query button");
		         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
		        		 deviceEntryDeviceNbrTxtBox,deviceEntryQueryBtn,3,20);
		         Thread.sleep(3000);
		         FunctionLibrary.setText(deviceEntryDeviceNbrTxtBox, deviceNumber, "Entering device number");
		         FunctionLibrary.clickObject(deviceEntryGoBtn, "", "Clicking go button");
		         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
		        		 boxListTable,deviceEntryGoBtn,3,20);
		         String boxNbr=FunctionLibrary.objDriver.findElement(By.xpath(boxNbrTxt)).getAttribute("title");
		         String boxType=FunctionLibrary.objDriver.findElement(By.xpath(boxTypeTxt)).getAttribute("title");
		         
		         ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : Straggler box should be created"+"</br>"+
		                 "Actual ; Box "+boxNbr+" has been created for device "+deviceNumber+" with box typs as "
		                                                            +boxType, LogStatus.PASS, true);
		              
		     }
		 }
		 catch(Exception e){
             ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
       }
	 }
	 
     public static void updateStatusDateOfDevice(String devNbr) throws SQLException, ClassNotFoundException, InterruptedException
    {
            
    	 //Connecting to DataBase
		
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
		String query="update siebel.s_asset set INSTALL_DT=TO_DATE('09/07/2016 6:27:28 AM', 'MM/DD/YYYY HH:MI:SS AM') "
				+ "where row_id in(select row_id from siebel.s_asset where owner_asset_num in ('"+devNbr+"'))";
		stmt.executeQuery(query);
		//Thread.sleep(3000);
		stmt.executeQuery("commit");
		//Thread.sleep(1000);
		System.out.println("Device status date has been updated");
		con.close();
		
   }
     
     public static void searchAccount(HashMap<String,String> dataObj)
     {
         // Search the account
         
    	 //accNum = getAccountNumberUsingSql(dataObj.get("EmailIdToGetAccounts"));
    	 accNum = dataObj.get("AccountNumber");
        /* FunctionLibrary.clickObject(accountsTab, "", "Clicking Accounts Tab");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		                                 accountNumberTxtBox,accountsTab,4,20);
         
         FunctionLibrary.setText(accountNumberTxtBox, accNum, "Entering Account Number");
         FunctionLibrary.clickObject(goSearchBtn, "", "Clicking Go button");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		                                              accountInfoTab,goSearchBtn,5,20);
         */
    	 CommonLibrary.searchForAccount(accNum);
         FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		                                         accountInfoDevicesTab,accountInfoTab,5,20);
         
     //Navigating to Device tab and getting the no of devices present before status change
         
 		
     ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Changing the device status for Account : "
     		                                               +accNum, LogStatus.INFO, false);
         
         FunctionLibrary.clickObject(accountInfoDevicesTab, "", "Clicking Devices Tab");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		 assignedDeviceTable,accountInfoDevicesTab,5,20);
         
 		String deviceCountBeforeStatusChange=FunctionLibrary.objDriver.findElement(By.xpath
 				                                        (noOfDevicesInDashboardTxt)).getAttribute("value");
 		
 		
 		ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"The no Of devices available for the account before "
 				+ "device status change is : "+deviceCountBeforeStatusChange, LogStatus.INFO, false);
     }
    
     public static void searchForDevice(String searchType, String searchVal) throws InterruptedException
     {
    	 try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	    FunctionLibrary.clickObject(assignedDeviceFilterTxtBox, "", "click the field");
    		FunctionLibrary.setText(assignedDeviceFilterTxtBox, searchType, "Entering the value to filter");		
    		FunctionLibrary.clickObject(assignedDeviceFilterValueTxtBox, "", "click the field");
    		//FunctionLibrary.setText(assignedDeviceFilterValueTxtBox, searchVal, "Entering the value to filter");
    		FunctionLibrary.objDriver.findElement(By.xpath(assignedDeviceFilterValueTxtBox)).sendKeys(searchVal);
    		//new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.ENTER)).perform();

    		try{
    		FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath
	                   ("//td[@title='"+searchVal+"']")));
    		}catch(Exception e)
    		{
    			
    		}
    		Thread.sleep(3000);
    		FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath
	                   ("//td[@title='"+searchVal+"']")));   		
  }
       
     public static void searchForDeviceForStatusChange(String searchVal)
     {
    	 try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	    FunctionLibrary.clickObject(assignedDevicesQueryBtn, "", "click query button");
    	    FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
    	    		deviceNbrTxtBox,assignedDevicesQueryBtn,4,32);
    	    FunctionLibrary.setText(deviceNbrTxtBox, searchVal, "Entering the value to filter");
    	    FunctionLibrary.clickObject(deviceNbrGoBtn, "", "click go button");
    	    FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(assignedDeviceListQueryBtn)));
    		FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath
	                   ("//td[@title='"+searchVal+"']")));   		
  }   
     
     public static void changeStatusOfDevice(String deviceNumber, String newSTatus) throws InterruptedException
          { 	
 		
 		 ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Screenshot before status changing ",LogStatus.INFO, true); 
    	 
    	//Changing the status of the device as per the requirement
    	    FunctionLibrary.clickObject(deviceStatusTxtElement, "", "Clicking device status field");
    	    
    	    FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
    	 		   deviceStatusTxtBox,deviceStatusTxtElement,8,30);    	    
    	    
    	    FunctionLibrary.setText(deviceStatusTxtBox, newSTatus, "Entering new device status");   			
			 FunctionLibrary.clickObject(assignedDeviceSaveBtn, "", "Clicking Save button");
    		     Thread.sleep(3000);
 		      
            }
     
     public static void verifyDeviceStatusChangeUnderDeviceHistoryAndNotes(String deviceNumber, String newStaus) throws InterruptedException
     {
    	 //Checking the status of the device under device history tab
         
         FunctionLibrary.clickObject(deviceHistoryTab, "", "Clicking Device history tab");
         
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
      		                                            deviceHistoryTable,deviceHistoryTab,20,60);
         int rowNumberForDeviceandQuanty = FunctionLibrary.getRowNumberFromWebTableByTwoText(deviceHistoryTable,deviceNumber,newStaus,"get the row number");

         try{
		 if(dataObj.get("NewStatusOfDevice").equalsIgnoreCase("FOUND"))
		  {
			 if(dataObj.get("OldStatusOfDevice(if not ACTIVE)").equalsIgnoreCase("STOLEN"))
			 {
				 FunctionLibrary.verifyWebTableCellData(deviceHistoryTable, rowNumberForDeviceandQuanty, 7, newStaus, 
						 "Device History: Verify the new status of device", true);

			 }else if(dataObj.get("OldStatusOfDevice(if not ACTIVE)").equalsIgnoreCase("LOSTINMAIL"))
			 {
				 FunctionLibrary.verifyWebTableCellData(deviceHistoryTable, rowNumberForDeviceandQuanty, 7, newStaus, 
						 "Device History: Verify the new status of device", true);
			 }
		 
		 }else
		   {
			 FunctionLibrary.verifyWebTableCellData(deviceHistoryTable, rowNumberForDeviceandQuanty, 7, newStaus, 
					 "Device History: Verify the new status of device", true);

		  }
         }catch(Exception e)
         {
        	 FunctionLibrary.verifyWebTableCellData(deviceHistoryTable, rowNumberForDeviceandQuanty, 7, newStaus, 
					 "Device History: Verify the new status of device", true);
         }
         
         String activityId = FunctionLibrary.getWebTableCellData(deviceHistoryTable, rowNumberForDeviceandQuanty, 3, "Get the activity id");
      
         //Checking the status under Notes tab
       
         FunctionLibrary.clickObject(notesTab, "", "Clicking Notes tab");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(notesListTable,notesTab,10,33);
         String notesDescription=FunctionLibrary.objDriver.findElement(By.xpath(notesDescTxt)).getAttribute("title");
         ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"The Note Description is ---> "+notesDescription, LogStatus.INFO, true);
         
        //Checking the status under Non-Financial History tab
         
        
         FunctionLibrary.clickObject(historyTab, "", "Click history link");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(nonFinancialsHistoryTab,historyTab,10,33);         
         FunctionLibrary.clickObject(nonFinancialsHistoryTab, "", "Clicking Non-Financial History Tab");
         
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(activitiesTable,nonFinancialsHistoryTab,10,33);
         
         int rowNum = FunctionLibrary.getRowNumberFromWebTable(activitiesTable, activityId, "Get row number");
         
         FunctionLibrary.verifyWebTableCellData(activitiesTable, rowNum,3, "CHANGE", "Verify category", true);
         
         FunctionLibrary.verifyWebTableCellData(activitiesTable, rowNum,4, "DEVICE", "Description", false);
         
     }
     
	 public static void verificationInDataBase(String statusId)
			 throws SQLException, ClassNotFoundException, InterruptedException
     {

		    String connStringOracleDb = "jdbc:oracle:thin:@";
		    String userName = CommonLibrary.getSettingsSheetInfo().get("QA_USERNAME");	                
		    String password = CommonLibrary.getSettingsSheetInfo().get("QA_PASSWORD") ;    
		    String deviceRequestIdStatus=null;
	        String dbHostName=CommonLibrary.getSettingsSheetInfo().get("DataBaseHostName");
			String dbPort=CommonLibrary.getSettingsSheetInfo().get("DataBasePort");
			dbPort=dbPort.substring(0, 4);
			String dbServiceName=CommonLibrary.getSettingsSheetInfo().get("DataBaseServiceName");
			connStringOracleDb = connStringOracleDb+dbHostName+":"+dbPort+":"+dbServiceName;
	        //step2 create  the connection object
	        Connection con=DriverManager.getConnection(
	        		connStringOracleDb,userName,password); 
     Statement stmt=con.createStatement();
     String queryToFetchDeviceRequestId="SELECT display_value from siebel.v_codes_all where code_type='DEVICE_STATUS'"
     		+ " and code_id in ('"+statusId+"')";
     ResultSet rs=stmt.executeQuery(queryToFetchDeviceRequestId);
     while(rs.next()){

    		 deviceRequestIdStatus=rs.getString("DISPLAY_VALUE");
    	 }

     ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Status of device request in v_codes_all"+
    		          " table is "+deviceRequestIdStatus,LogStatus.INFO,false);
	
     }

}
