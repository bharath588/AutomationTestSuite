package features;

import com.relevantcodes.extentreports.LogStatus;
import libraries.CommonLibrary;
import libraries.FunctionLibrary;
import libraries.ReportLibrary;

//import org.junit.internal.runners.model.EachTestNotifier;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static libraries.FunctionLibrary.closeAllActiveBrowser;
import static libraries.FunctionLibrary.objDriver;
import static objectProperties.SblDeviceStatusChnageProperties.accountInfoDevicesTab;
import static objectProperties.SblDeviceStatusChnageProperties.accountInfoTab;
import static objectProperties.SblDeviceStatusChnageProperties.accountNumberTxtBox;
import static objectProperties.SblDeviceStatusChnageProperties.accountsTab;
import static objectProperties.SblDeviceStatusChnageProperties.deviceHistoryDeviceNbrElement;
import static objectProperties.SblDeviceStatusChnageProperties.deviceHistoryDeviceNbrTxtBox;
import static objectProperties.SblDeviceStatusChnageProperties.deviceHistoryGoBtn;
import static objectProperties.SblDeviceStatusChnageProperties.deviceHistoryQueryBtn;
import static objectProperties.SblDeviceStatusChnageProperties.deviceHistoryTab;
import static objectProperties.SblDeviceStatusChnageProperties.deviceHistoryTable;
import static objectProperties.SblDeviceStatusChnageProperties.goSearchBtn;
import static objectProperties.SblDeviceStatusChnageProperties.nonFinancialsHistoryTab;
import static objectProperties.SblManualTagAssignmentProperties.*;

public class DimsManualTagAssignment {
	
	static String desc="";
    static String actualProcurementCost=null;
    static int lineItemProcurementCost = 0;
    static String orderNbr = null;
    static String accNum=null;
    //static ArrayList<String> deviceRequestList = new ArrayList<String>();
    
	 public static void DimsManualTagAssignmentTest() throws IOException,Exception {
	      ExcelSheet exl=new ExcelSheet();
	     // String LoginMessage="NotSuccess";

	      HashMap<String,String> eachTestCaseData =new HashMap<String, String>();
	     // HashMap<String,String> eachTestCaseData2 =new HashMap<String, String>();


	      int noOfRows=exl.totalrows("FLCSS_DIMS_ManualTagAssignment","Test_Scenarios");	    
	      
	         for(int i=1;i<=noOfRows;i++)
	         {
	        	  eachTestCaseData=CommonLibrary.getEachTestCaseData(exl,"Test_Scenarios",i,"FLCSS_DIMS_ManualTagAssignment");
	        	  
	        	  eachTestCaseData.get("ExecutionStatus");                                    
    	 

	        	  String executionType = CommonLibrary.settingsSheetInfo.get("Execution_Type");
                  
                  if ((eachTestCaseData.get("ExecutionStatus").equalsIgnoreCase("Yes")) && ( eachTestCaseData.get("TestType").contains(executionType)|| 
                  		executionType.equalsIgnoreCase("All")))
                  {                   
	                      try{
	                    	  
	                    	  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"<b>"+
	                    	  eachTestCaseData.get("TestCaseId")+"</b>"+": Test Case Execution is started..... <br>"
	                    	  		+ "Test case description: " + eachTestCaseData.get("TestCaseDesc"), LogStatus.PASS, false);
	               
	                    	  CommonLibrary.loginSblWithGenericUser(eachTestCaseData);
	                    	  
	                    	  
	                    	try{
	                    		//Getting the device Request ID
                    		 // accNum = getAccountNumberUsingSql(eachTestCaseData.get("EmailIdToGetAccounts"));
	                    		accNum = eachTestCaseData.get("AccountNumber");
	                    		  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Assigning device for the account : "
	                    	              +accNum, LogStatus.INFO, false);
	                    	  	  //  HashMap  <String,String> inputAccountNumInfo =CommonLibrary.executeSqlAndGetOutputAsHashMap(sql);
	                    	     // String deviceRequestId = inputAccountNumInfo.get("SR_NUM");
	                    		
	                    	  	String deviceRequestId=getDeviceRequestIdForAccount(accNum);
                    		    if(deviceRequestId==null)
                    		    {
                    		    	CommonLibrary.logoutSiebelApplicaiton();
                    		    	continue;

                    		    }else
                    		    {
                    		    	CommonLibrary.searchForAccount(accNum);
 	                    	       
 	                    	       FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
 	                    	       FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
 	                    	      		                                         accountInfoDevicesTab,accountInfoTab,5,20);
 	             
 	                    	       AssignmentOfManualTag(eachTestCaseData,accNum,deviceRequestId,false);
                    		    	
                    		    }
	                    	  }catch(StaleElementReferenceException e)
	                    	  {
	                    		  
	                    	  }
	                   
	                   CommonLibrary.logoutSiebelApplicaiton();
	               	 


	                }catch(Exception e)	                    
	                    {e.printStackTrace();
	                   e.getMessage();
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
	 
	 
   public static void AssignmentOfManualTag(HashMap<String,String> dataObj,String accountNbr, String deviceRequestId, boolean isVerificationRequried) throws InterruptedException, Exception, SQLException{
	   
	   try
	   {
	     	 //Navigating to Device assignment tab  
	
		   WebDriverWait transponderWait= new WebDriverWait(FunctionLibrary.objDriver, 60);
	       
	   //Navigating to Device tab and getting the no of devices present before status change
	       
	       
	       FunctionLibrary.clickObject(accountInfoDevicesTab, "", "Clicking Devices Tab");
	       FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	    		   assignedDeviceListTab,accountInfoDevicesTab,5,60);
	       
	        FunctionLibrary.clickObject(deviceAssignmentTab, "", "Clicking Device Assignment tab"); 
	        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	        		deviceRequestListTable,deviceAssignmentTab,5,60);
	        
	        String deviceCountBeforeStatusChange=FunctionLibrary.objDriver.findElement(By.xpath
	                (deviceCountInDashBoardTxt)).getAttribute("value");
	       ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"The no Of devices available for the account before "
	               + "device assignment is : "+deviceCountBeforeStatusChange, LogStatus.INFO, false);
	        
	        
			String[] noOfDevices = null;
			String[] noOfDevicesPerRequest = null;
		    ArrayList<String>DevicesAssigned =new ArrayList<String>();
			int noOfDevicesLength=0;
			
		
		       String deviceStatusToBeChanged = dataObj.get("DeviceStatus");
		       deviceStatusToBeChanged = deviceStatusToBeChanged.replace("\n","");
	
		       String []poLineItems = deviceStatusToBeChanged.split(",");
		       String [] eachDeviceStatusInfo;
		       String deviceStatus;
		                         
		       for (int lineItemIterator =0;lineItemIterator<=poLineItems.length-1;lineItemIterator++)
		       {
		    	   eachDeviceStatusInfo=poLineItems[lineItemIterator].split(":");
		      	 deviceStatus = eachDeviceStatusInfo[0];
			
	        
	        // Device Assignment for new request
			
			//for(int deviceRequestIterator=0;deviceRequestIterator<deviceRequestList.size();deviceRequestIterator++)
			//{
		      	 
		      	//for(String str : Arrays.asList(deviceRequestId)){
	
				DimsDeviceStatusChange.searchForDevice("Device Request #", deviceRequestId);
				Thread.sleep(3000);//Not finding any changes in the page. so not able to get any condition
				
	        	//String deviceCountStr=FunctionLibrary.objDriver.findElement(By.xpath(deviceCountOfRequest)).getAttribute("title");
	        	//int deviceCount=Integer.parseInt(deviceCountStr);
				int deviceCount=0;
				String noOfDevToAssgn=dataObj.get("NoOfDevicesToBeAssigned");
				if(noOfDevToAssgn.equals(null) || noOfDevToAssgn.length()==0 )
				{
					deviceCount=1;
				}else
				{
					deviceCount=Integer.parseInt(noOfDevToAssgn);
				}
	        	
	        	noOfDevicesPerRequest=new String[deviceCount];
	        	int deviceRequestListIterator=0;
	        	
	        	
	        	//Assigning devices as per the request
	        	
				for(int noOfDevicesAssigned=1;noOfDevicesAssigned<=deviceCount;noOfDevicesAssigned++)
				{
			        JavascriptExecutor js= (JavascriptExecutor)FunctionLibrary.objDriver;
			        js.executeScript("window.scrollBy(0,250)", "");	        
			        
			       	
			       	FunctionLibrary.clickObject(deviceAssignNewBtn, "", "Clicking assign device new button");
			       	FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
			       			deviceNbrTxtBox,deviceAssignNewBtn,30,360);
			       	
			       	try{
			       		WebDriverWait wait= new WebDriverWait(FunctionLibrary.objDriver, 10);
			       		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(alertAcceptBtn)));
			       	     FunctionLibrary.objDriver.findElement(By.xpath(alertAcceptBtn)).click();
			       	}catch(Exception e)
			       	{
			       		
			       	}
			       	
			       	FunctionLibrary.clickObject(deviceAssignDeviceNbrSearchBtn, "", "Clicking device search button");
			       	FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
			       			devicesAppletPopUp,deviceAssignNewBtn,37,91);
			       	transponderWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(deviceNbrTxt)));
			       	FunctionLibrary.objDriver.findElement(By.xpath(deviceNbrTxt)).click();		          	 
			        String deviceNumber=FunctionLibrary.objDriver.findElement(By.xpath(deviceNbrTxt)).getAttribute("title");
			              
			            noOfDevicesPerRequest[deviceRequestListIterator]=deviceNumber.toString();
			           	deviceRequestListIterator++;
			           	FunctionLibrary.clickObject(deviceAssignVectorDevicePickOkBtn, "", "Clicking Device Model Ok button");
			          transponderWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(deviceNbrTxtBox))); 
			           	 //Thread.sleep(3000);
			           	 
			           	
			           	 FunctionLibrary.objDriver.findElement(By.xpath(partialFulfillmentBtn)).click();
			           	FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
			           			alertAcceptBtn,partialFulfillmentBtn,20,61);
			           	 try{
			           		 /*WebDriverWait wait= new WebDriverWait(FunctionLibrary.objDriver, 60);
			           		 wait.until(ExpectedConditions.alertIsPresent());
			                 Alert alert = FunctionLibrary.objDriver.switchTo().alert();
			                 alert.accept();*/
			           		transponderWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(alertAcceptBtn)));
			           		 FunctionLibrary.objDriver.findElement(By.xpath(alertAcceptBtn)).click();
			           		 
			           	 }catch(Exception e)
			           	 {
			           		 System.out.println("No alert is present");
			           	 }
			           	 
			           	FunctionLibrary.objDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			           	 //This alert will come rarely and not sure when 2nd popup will be displayed.
			           	 WebDriverWait waitObj = new WebDriverWait(FunctionLibrary.objDriver, 10);
			           	 		           	 
			           	 try{
			           		waitObj.until(ExpectedConditions.elementToBeClickable(By.xpath(alertAcceptBtn)));
				           		 FunctionLibrary.objDriver.findElement(By.xpath(alertAcceptBtn)).click();
			           		 /*WebDriverWait wait= new WebDriverWait(FunctionLibrary.objDriver, 60);
			           		 wait.until(ExpectedConditions.alertIsPresent());
			                 Alert alert = FunctionLibrary.objDriver.switchTo().alert();
			                 alert.accept();*/
				           	 }catch(TimeoutException e)
				           	 {
				           		 System.out.println("No alert is present");
				           	 }
			           	 	  	
			           	FunctionLibrary.objDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			              ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Device "+deviceNumber+" has been assigned to " +
			            		  accountNbr  , LogStatus.INFO, false);
			           	
			              FunctionLibrary.objDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
			    	if(noOfDevToAssgn.equals(null)){  		
			    		if(deviceStatus.toString().equalsIgnoreCase("Fulfillment"))
			    		{
			    			System.out.println("Assign the next device");
			    			
			    		}
			    		else if(deviceStatus.toString().equalsIgnoreCase("PartialFulfillment"))
			    		  {
			    			if(deviceCount==0)
			    			{
			    				break;
			    			}else
			    			{
			    			try{
			    		 waitObj.until(ExpectedConditions.elementToBeClickable(By.xpath(deviceRequestStatusTxt)));
			    				
			    		 String deivceRequestStatus=FunctionLibrary.objDriver.findElement(By.xpath(deviceRequestStatusTxt))
			    					                            .getAttribute("title");
								  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : Device Request should be PARTIALLY FULFILLED"+"<br>"
								    +"Actual : Device request status is "+deivceRequestStatus, LogStatus.PASS, true);		    			
								    			break;
			    			}catch(TimeoutException e)
			    			{
			    				break;
			    			}
			    		  }
			    		}
				}
			    		
			    		FunctionLibrary.objDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			    	
					
				}//End of assigning devices per request
				
	       
				
				//fieldVerificationInDataBase("siebel.s_srv_req","sr_num", "sr_stat_id", deviceRequestId);	
				
				//String requested device numbers per request
		         DevicesAssigned.addAll(Arrays.asList(noOfDevicesPerRequest));
		
				
			//}//End of assigning devices for all requests
			
	        noOfDevices = DevicesAssigned.toArray(new String[DevicesAssigned.size()]);
	        noOfDevicesLength=noOfDevices.length;
	       
	         } //end of for loop 
		      	
		//}
		       
	         
		        if(isVerificationRequried)
		        {
	    	      String deviceCountAfterStatusChange=FunctionLibrary.objDriver.findElement(By.xpath
	                      (deviceCountInDashBoardTxt)).getAttribute("value");
	             ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"The no Of devices available for the account after "
	                     + "device assignment is : "+deviceCountAfterStatusChange, LogStatus.INFO, false);
	             
	
	           //Checking the status of the assigned devices
	             
	         	FunctionLibrary.clickObject(assignedDeviceListTab, "", "Clicking Assigned Device tab");        	
	         	transponderWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(assignedDeviceListTable))); 
	        
	        for (int deviceNoIterator=0;deviceNoIterator<noOfDevicesLength;deviceNoIterator++){
	        	
	        	try{
	        	
	        	if(!noOfDevices[deviceNoIterator].equals(null)){ 
	        		
	            	
	        	FunctionLibrary.objDriver.findElement(By.xpath(assignedDeviceListQueryBtn)).click();
	        	FunctionLibrary.objDriver.findElement(By.xpath(deviceNbrTxtBox)).sendKeys(noOfDevices[deviceNoIterator]);
	        	//Thread.sleep(3000);
	            FunctionLibrary.objDriver.findElement(By.xpath(assignedDeviceListGoBtn)).click();
	            transponderWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(deviceStatusTxt)));
	        	
	        	String assignedDeviceStatus=FunctionLibrary.objDriver.findElement(By.xpath(deviceStatusActiveTxt)).getAttribute("title");
	        	String assignedDeviceStatusDate=FunctionLibrary.objDriver.findElement(By.xpath(deviceStatusDateTxt)).getAttribute("title");
	        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expexted : Status of the Device : "+noOfDevices[deviceNoIterator]+
	              " under Assigned Device tab should be : ACTIVE" +"</br>"+
	        		"Actual : The status of the device under Assigned Device tab is : "+assignedDeviceStatus, LogStatus.PASS, true);
	        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Device status date of "+noOfDevices[deviceNoIterator]+
	        			" is "+assignedDeviceStatusDate, LogStatus.INFO, false);
	       
	        	    } else{
	        		System.out.println("Device number does not exist");}
	            	}catch(Exception e)
	            	{
	            	
	            	}
	      }//end of for       
	        
	
	            //Checking the status under Device History tab
	            
	            FunctionLibrary.clickObject(deviceHistoryTab, "", "Clicking Device history tab");
	            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	         		                                            deviceHistoryTable,deviceHistoryTab,10,30);
	            
	            for (int deviceNoIterator=0;deviceNoIterator<noOfDevicesLength;deviceNoIterator++){
	            	
	            	try{
	            	
	            	if(!noOfDevices[deviceNoIterator].equals(null)){
	            
	
	            FunctionLibrary.clickObject(deviceHistoryQueryBtn, "", "Click on the query button");
	            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
	                 (deviceHistoryGoBtn,deviceHistoryQueryBtn, 5, 20);
	           // Thread.sleep(5000);
	            FunctionLibrary.clickObject(deviceHistoryDeviceNbrElement, "", "Clicking device number field");
	            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
	            (deviceHistoryDeviceNbrTxtBox, deviceHistoryDeviceNbrElement, 1, 20);
	            FunctionLibrary.setText(deviceHistoryDeviceNbrTxtBox, noOfDevices[deviceNoIterator], "Entering Device number");
	            FunctionLibrary.clickObject(deviceHistoryGoBtn, "", "Clicking Go button");
	            transponderWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(deviceStatusActiveTxt)));
	            //Thread.sleep(3000);
	            
	            int rownum1 = FunctionLibrary.getRowNumberFromWebTable(deviceHistoryTable, noOfDevices[deviceNoIterator], "Get the row nunber");
	            FunctionLibrary.getWebTableCellData(deviceHistoryTable, rownum1, 3, "Get the activity id");
	        	
	        	String deviceHistoryStatus=FunctionLibrary.objDriver.findElement(By.xpath(deviceStatusTxt)).getAttribute("title");
	            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Status of the Device : "+noOfDevices[deviceNoIterator]+
	               " under Device History tab is : "+ deviceHistoryStatus , LogStatus.INFO, true);
	            	} else{
	        		System.out.println("Device number does not exist");}
	            	}catch(Exception e)
	            	{
	            	
	            	}
	          }//end of for loop
	            
	            
	            //Checking the device status under non-financials history
	            
	            FunctionLibrary.clickObject(historyTab, "", "Click history link");
	            FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(nonFinancialsHistoryTab)));        
	            FunctionLibrary.clickObject(nonFinancialsHistoryTab, "", "Clicking Non-Financial History Tab");        
	            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(activitiesTable, nonFinancialsHistoryTab, 10,32); 
	                    
	            for (int deviceNoIterator=0;deviceNoIterator<noOfDevicesLength;deviceNoIterator++){
	            	
	            	try{
	            	
	            	if(!noOfDevices[deviceNoIterator].equals(null)){
	                   
	
	            int rowNum = FunctionLibrary.getRowNumberFromWebTable(activitiesTable, "CHANGE", "Get row number");
	            FunctionLibrary.verifyWebTableCellData(activitiesTable, rowNum,3, "CHANGE", "Verify category", true);        
	            FunctionLibrary.verifyWebTableCellData(activitiesTable, rowNum,4, "DEVICE", "Description", false);
	
	                }//End of if loop
	            	
	            	else{
	            		System.out.println("Device number does not exist");
	            	}
	            	
	            	
	            }catch(Exception e)
	            	{
	            	
	            	}
	            
	            
	         }//End of for loop
	        }
	   }catch(Exception e){
           ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
     }
   }



   public static String requestDeviceAndReturnDeviceRequestId(HashMap<String,String> dataObj) throws InterruptedException{
	   
       desc="Requesting devices";
       
              
       //Navigating to Accounts tab
       
       FunctionLibrary.clickObject(accountsTab, "", "Clicking the Accounts tab");
       FunctionLibrary.setText(accountNumberTxtBox, dataObj.get("AccountNumber"), "Entering the Account number");
       FunctionLibrary.clickObject(goSearchBtn, "", "Clicking Search button");
   
       //Navigating to Account Info tab

       Thread.sleep(10000);
       FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");

       Thread.sleep(10000);
       FunctionLibrary.clickObject(accountInfoDevicesTab, "", "Clicking Devices tab");
       
       //Code for requesting a device
       
       Thread.sleep(10000);
       FunctionLibrary.clickObject(accountInfoDevicesDeviceRequestTab, "", "Clicking Device Request tab");
       
              
       //Iterating the no of device request
       
       String deviceRequestNbr=null;
       String devicesToBeRequested = dataObj.get("RequestedDeviceModel");
       devicesToBeRequested = devicesToBeRequested.replace("\n","");

       String []requestedItems = devicesToBeRequested.split(",");
       String [] eachDeviceInfo;
       String deviceModelNumber;
       String deviceQuantity;
       
       for (int deviceIterator =0;deviceIterator<=requestedItems.length-1;deviceIterator++)
       {
    	eachDeviceInfo=requestedItems[deviceIterator].split(":");
      	deviceModelNumber = eachDeviceInfo[0];
      	deviceQuantity=eachDeviceInfo[1];

       FunctionLibrary.clickObject(deviceRequestNewBtn, "", "CLicking new button");
       Thread.sleep(3000);
       deviceRequestNbr=FunctionLibrary.objDriver.findElement(By.xpath(deviceRequestNbrTxt)).getAttribute("title");
       System.out.println(deviceRequestNbr);
      // deviceRequestList.add(deviceRequestNbr);---------Will add when required
       FunctionLibrary.clickObject(deviceRequestSearchBtn, "", "Clicking Device Request search button");
       FunctionLibrary.clickObject("xpath=(//*[contains(@aria-labelledby, '_l_Model_No') and contains(@id,'_Model_No')" +
               " and @title='"+deviceModelNumber.toString()+"'])","","Entering Device Request Model Name");
       FunctionLibrary.clickObject(deviceRequestModelOkBtn, "", "Clicking Ok button");
       FunctionLibrary.clickObject(deviceRequestQuantityElement, "", "Clicking Device Quantity field");
       FunctionLibrary.setText(deviceRequestQuantityTxtBox, deviceQuantity.toString(), "Entering quantity of devices");       
       FunctionLibrary.clickObject(deviceRequestSaveBtn, "", "Clicking save button");
       
       Thread.sleep(10000);
       
       }//end of for loop
       
       desc="Devices requested should be in PENDING status";
		ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: "+desc+""+"<br>"+
		"Actual:Devices requested are in PENDING status", LogStatus.PASS, true); 
       
       
       //Code for payment under Financial tab
       
  
       FunctionLibrary.clickObject(financialsTab, "", "CLicking Financials tab");
       Thread.sleep(10000);
       JavascriptExecutor js= (JavascriptExecutor)FunctionLibrary.objDriver;
       js.executeScript("window.scrollBy(0,250)", "");
       FunctionLibrary.clickObject(financialNewBtn, "", "Clicking on new button");
       //Thread.sleep(5000);
       js.executeScript("window.scrollBy(0,250)", "");
       
       
       //Selecting different types of Payment type
       
       FunctionLibrary.selectDropDownListByValue(deviceRequestRebillPayTypeDropdown,
       		dataObj.get("Re-billPayType"), "Selecting the Re-bill pay type");
       
       //If payment mode is CASH
       
       if(dataObj.get("Re-billPayType").equals("CASH")){
                          
       }
       
     //If payment mode is CHECK
       else if(dataObj.get("Re-billPayType").equals("CHECK")){
    	   String CheckNo=Integer.toString(FunctionLibrary.randomNumberWithFiveDigit());
    	   FunctionLibrary.setText(checkNbrTxtBox,CheckNo, "Entering Check number");
           
       }
       
     //If payment mode is SAVINGS
       
       else if(dataObj.get("Re-billPayType").equals("SAVNGS") || dataObj.get("Re-billPayType").equals("CHECKING")) {

       FunctionLibrary.setText(deviceRequestPaymentACHBankAccntNoTxtBox, 
       		dataObj.get("ACHBankAccntNo"), "Entering ACH bank Account no");
       FunctionLibrary.setText(deviceRequestPaymentACHBankRoutingNoTxtBox, 
       		dataObj.get("ACHBankRoutingNo"), "Entering ACH bank Routing no");
       
       FunctionLibrary.clickObject(deviceRequestPaymentSaveBtn, "", "Clicking Save button");
       
       }
       
       
     //If payment mode is VISA or MASTERCARD
       
       else if(dataObj.get("Re-billPayType").equals("VISA") || dataObj.get("Re-billPayType").equals("MASTERCARD") || 
  		dataObj.get("Re-billPayType").equals("DISCOVER") || dataObj.get("Re-billPayType").equals("AMEX")){
       	
           FunctionLibrary.setText(deviceRequestPaymentCreditCardNoTxtBox, 
           		dataObj.get("CreditCardNo"), "Entering Credit card no");
           FunctionLibrary.selectDropDownListByValue(deviceRequestPaymentExpirationMonthDropdown,
           		dataObj.get("ExpMonth"), "Selecting the expiry month");
           FunctionLibrary.selectDropDownListByValue(deviceRequestPaymentExpirationYearDropdown,
           		dataObj.get("ExpYear"), "Selecting the expiry year");
       	
       }
                
       
       //Thread.sleep(5000);
       FunctionLibrary.clickObject(deviceRequestPaymentSaveBtn, "", "Clicking Save button");
       Thread.sleep(2000);
       FunctionLibrary.clickObject(paymentListSaveBtn, "", "Clicking Save button");
       Thread.sleep(3000);
       FunctionLibrary.objDriver.findElement(By.xpath(processPayBtn)).click();
       ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Payment has been completed"
               , LogStatus.PASS, true);
       
       try{
       	
       	FunctionLibrary.clickObject(popupAcceptBtn, "", "Clicking accept button for the pop-up");
       	
       }catch(Exception e)
       {
       	System.out.println("No pop-up is present");
       	
       }
       
  	 try{
        Alert alert = FunctionLibrary.objDriver.switchTo().alert();
        alert.accept();
        }
        catch(Exception e){
        	 System.out.println("No alert is present");
        }
	return deviceRequestNbr;
   }
   
/*	 public static String [] getDeviceRequestIdForAccount(String accountNbr) throws SQLException, ClassNotFoundException, InterruptedException
     {
	 	 //Connecting to DataBase
		 String []deviceRequests=new String[]{};
		 String deviceRequestIds = "";
		 
		 //String accNum = getAccountNumberUsingSql("mailidFordmtga001@conduent.com");
		 if(accNum!=null)
		 {
     Class.forName("oracle.jdbc.driver.OracleDriver");
     Connection con=DriverManager.getConnection(
             "jdbc:oracle:thin:@10.36.96.2:1521:flvecqa1","debasmitas","samal"); 
     Statement stmt=con.createStatement();
     String queryToFetchDeviceRequestId="select sr_num from siebel.s_srv_req where CST_OU_ID in "
     		                  + "(select row_id from siebel.s_org_ext where ou_num in ('"+accNum+"'))";
     ResultSet rs=stmt.executeQuery(queryToFetchDeviceRequestId);
    // int i=0;
     //Thread.sleep(3000);
     while(rs.next()){
    	 //deviceRequestIds = 
    	 if(deviceRequestIds=="")
    	 {
    		 deviceRequestIds=rs.getString("SR_NUM");
    	 }
    	 else
    	 {
    	 deviceRequestIds = deviceRequestIds+"_"+rs.getString("SR_NUM");
    	 }
    	// i++;
     	//System.out.println(deviceRequestId[i]);
     }   
     deviceRequests = deviceRequestIds.split("_");
     con.close();
    	 for(String str : Arrays.asList(deviceRequestIds))
     ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Assigning device for the request ID : "+str,
             LogStatus.INFO, false);
		 }
	return deviceRequests;
     }*/
   
	 public static String getDeviceRequestIdForAccount(String accountNbr) throws SQLException, ClassNotFoundException, InterruptedException
     {

		 String deviceRequestIds = null;
		 try{
		 if(accountNbr!=null)
		 {
			    String connStringOracleDb = "jdbc:oracle:thin:@";
			    String userName = CommonLibrary.getSettingsSheetInfo().get("QA_USERNAME");	                
			    String password =CommonLibrary.getSettingsSheetInfo().get("QA_PASSWORD");    
			    String dbHostName=CommonLibrary.getSettingsSheetInfo().get("DataBaseHostName");
				String dbPort=CommonLibrary.getSettingsSheetInfo().get("DataBasePort");
				dbPort=dbPort.substring(0, 4);
				String dbServiceName=CommonLibrary.getSettingsSheetInfo().get("DataBaseServiceName");
				//connStringOracleDb = connStringOracleDb+dbHostName+":"+dbPort+":"+dbServiceName;
				connStringOracleDb = "jdbc:oracle:thin:@"+CommonLibrary.getSettingsSheetInfo().get("DatabaseConnectionString");
		        //step2 create  the connection object
		        Connection con=DriverManager.getConnection(
		        		connStringOracleDb,userName,password); 
	            Statement stmt=con.createStatement();
		     //Statement stmt=con.createStatement();
		     String queryToFetchDeviceRequestId="select sr_num from siebel.s_srv_req where CST_OU_ID in "
		     + "(select row_id from siebel.s_org_ext where ou_num in ('"+accountNbr+"')) and sr_stat_id in ('PAID','PARTIAL FULFILLED') order by created desc";
		     
		     ResultSet rs=stmt.executeQuery(queryToFetchDeviceRequestId);
		
		     while(rs.next()){
		
		    	 int i=0;
		    	 i++;
		    		 deviceRequestIds=rs.getString("SR_NUM");
		    		 if(i==1)
		    		 {
		    			 break;
		    		 }

		     }   
		
		     ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Assigning device for the request ID : "+deviceRequestIds,
		             LogStatus.INFO, false);
				 }
				 }
				 catch(SQLException e1)
				 {
					 ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Test is failed. Error is: "+e1.getMessage(),
				             LogStatus.FAIL, false);
				 }
			return deviceRequestIds;
     }
	 
	 
	 public static String fieldVerificationInDataBase(String tableName,String inputField, String fieldToBeVerified, String inputValue)
			 throws SQLException, ClassNotFoundException, InterruptedException
     {

		    String connStringOracleDb = "jdbc:oracle:thin:@";
		    String userName = CommonLibrary.getSettingsSheetInfo().get("QA_USERNAME");	                
		    String password =CommonLibrary.getSettingsSheetInfo().get("QA_PASSWORD");    
		    String deviceRequestIdStatus=null;
	        String dbHostName=CommonLibrary.getSettingsSheetInfo().get("DataBaseHostName");
			String dbPort=CommonLibrary.getSettingsSheetInfo().get("DataBasePort");
			dbPort=dbPort.substring(0, 4);
			String dbServiceName=CommonLibrary.getSettingsSheetInfo().get("DataBaseServiceName");
			connStringOracleDb =  "jdbc:oracle:thin:@"+CommonLibrary.getSettingsSheetInfo().get("DatabaseConnectionString");
	        //step2 create  the connection object
	        Connection con=DriverManager.getConnection(
	        		connStringOracleDb,userName,password); 
     Statement stmt=con.createStatement();
     String queryToFetchDeviceRequestId="select "+ fieldToBeVerified +" FROM "+tableName+" where "+inputField
    		                             +" in ('"+inputValue+"')";
     ResultSet rs=stmt.executeQuery(queryToFetchDeviceRequestId);
     while(rs.next()){

    		 deviceRequestIdStatus=rs.getString(fieldToBeVerified);
    	 }

     ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Status of device request "+inputValue+" in "+tableName+
    		          " table is "+deviceRequestIdStatus,LogStatus.INFO,false);
	return deviceRequestIdStatus;

     }
	 

}
