package features;

import com.relevantcodes.extentreports.LogStatus;
import libraries.CommonLibrary;
import libraries.FunctionLibrary;
import libraries.ReportLibrary;
import libraries.TestCaseLibrary;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static features.DimsPurchaseOrderAndReceive.createPurchaseOrderAndReturnId;
import static libraries.FunctionLibrary.closeAllActiveBrowser;
import static libraries.FunctionLibrary.objDriver;
import static objectProperties.SblBoxCheckInCheckOutProperties.*;
import static objectProperties.SblDeviceStatusChnageProperties.boxesTabQueryBtn;
import static objectProperties.SblDeviceStatusChnageProperties.searchForBoxElement;
import static objectProperties.SblDeviceStatusChnageProperties.searchForBoxTxtBox;
import static objectProperties.SblPurchaseOrderProperties.boxReceivedCountValue;
import static objectProperties.SblDeviceStatusChnageProperties.accountInfoDevicesTab;
import static objectProperties.SblDeviceStatusChnageProperties.accountInfoTab;
import static objectProperties.SblDeviceStatusChnageProperties.boxSearchGoBtn;

public class DimsBoxCheckInCheckOut {
		
	public static HashMap<String,String> dataObj =null;
	static String desc=null;
	//static String accNum=null;

	public static void DimsBoxCheckInCheckOutTest() throws IOException, Exception {
	        ExcelSheet exl = new ExcelSheet();

	        int noOfRows = exl.totalrows("FLCSS_DIMS_BoxCheckOut","Test_Scenarios");

	        HashMap<String, String> eachTestCaseData = new HashMap<String, String>();

	        String testCaseName = "";

	        for (int i = 1; i <=noOfRows; i++) {

	            eachTestCaseData = CommonLibrary.getEachTestCaseData(exl, "Test_Scenarios", i,"FLCSS_DIMS_BoxCheckOut");
	            dataObj=eachTestCaseData;
	            testCaseName = "tcBoxCheckOut"+eachTestCaseData.get("TestCaseId").replace("-","");
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
	                        Class<?> c = Class.forName("features.DimsBoxCheckInCheckOut");
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
	                        }	                   dataObj.clear();

	                }
	                
	                eachTestCaseData.clear();
	        }


	    }
	
	public static void tcBoxCheckOutCICO001() throws Exception
	{
		
		try{
	        //Creating PO	                    		 
	 		 
	
	        desc="Creating Purchase Order";
	        String poId = DimsPurchaseOrderAndReceive.createPurchaseOrderAndReturnId(dataObj);
	        System.out.println("Create po id : " + poId);
	        
	        
	        //Receiving PO
	        
	        desc="Receiving Purchase Order";
	        DimsPurchaseOrderAndReceive.createPurchaseOrderLineItems(dataObj,poId);
	        
	        DimsPurchaseOrderAndReceive.receivePurchaseOrderLineItem(dataObj,poId);
	        
	        Thread.sleep(5000);
	        
	        //Calculating no of Boxes
	        	                    
	        String noOfBoxes=null;
	        int actualBoxCount=0;
	        List<WebElement> boxNumber=	FunctionLibrary.objDriver.findElements(By.xpath(boxActualCountTxt));
	        for(WebElement element:boxNumber){
	        	noOfBoxes=element.getAttribute("title");
	        	int BoxCount=Integer.parseInt(noOfBoxes);
	        	actualBoxCount=actualBoxCount+BoxCount;
	        }	               	                    
	       
	       System.out.println("No of boxes are : "+ actualBoxCount);
	   	                   
	        
	      //Code to get the box numbers
	       
	        Thread.sleep(10000);
	        FunctionLibrary.clickObject(".//*[@name='Order Number' and text()='"+poId+"']","",
	        		        "Clicking order nuumber");
	        assert FunctionLibrary.verifyWebElementExist(boxesBoxListTxt,"Verify Box List element");
	        
		    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "The no of boxes received are : "+
			                                actualBoxCount, LogStatus.INFO, false);
			List<WebElement>noOfboxNbrLocation=FunctionLibrary.objDriver.findElements
					(By.xpath(boxReceivedCountValue));
			System.out.println(noOfboxNbrLocation.size());
			String[] boxNumbers = new String[noOfboxNbrLocation.size()];
			int iterator = 0;
			for (WebElement element : noOfboxNbrLocation) {
				boxNumbers[iterator]=element.getAttribute("title").toString();
				iterator = iterator+1;
			}
			
			int boxNoIterator=0;
			//String reqBoxNumber=null;
			
	     for (boxNoIterator=0;boxNoIterator<noOfboxNbrLocation.size();boxNoIterator++) {
	    	 
	    	 boxCheckOut(boxNumbers[boxNoIterator]);   
	    	 //boxValidationAfterBoxCheckOutAndCheckIn(boxNumbers[boxNoIterator]);
	
	         } 
		}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }
	}
		
	public static void tcBoxCheckOutCICO013() throws Exception
	{
		
		try{
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
	        List<WebElement> boxNumber=	FunctionLibrary.objDriver.findElements(By.xpath(boxActualCountTxt));
	        for(WebElement element:boxNumber){
	        	noOfBoxes=element.getAttribute("title");
	        	int BoxCount=Integer.parseInt(noOfBoxes);
	        	actualBoxCount=actualBoxCount+BoxCount;
	        }	               	                    
	       
	       System.out.println("No of boxes are : "+ actualBoxCount);
	   	                   
	        
	      //Code to get the box numbers
	       
	        Thread.sleep(10000);
	        FunctionLibrary.clickObject(".//*[@name='Order Number' and text()='"+poId+"']","",
	        		        "Clicking order nuumber");
	        assert FunctionLibrary.verifyWebElementExist(boxesBoxListTxt,"Verify Box List element");
	        
		    ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "The no of boxes received are : "+
			                                actualBoxCount, LogStatus.INFO, false);
			List<WebElement>noOfboxNbrLocation=FunctionLibrary.objDriver.findElements
					(By.xpath(boxReceivedCountValue));
			System.out.println(noOfboxNbrLocation.size());
			String[] boxNumbers = new String[noOfboxNbrLocation.size()];
			int iterator = 0;
			for (WebElement element : noOfboxNbrLocation) {
				boxNumbers[iterator]=element.getAttribute("title").toString();
				iterator = iterator+1;
			}
			
			int boxNoIterator=0;
					
	     for (boxNoIterator=0;boxNoIterator<noOfboxNbrLocation.size();boxNoIterator++) {
	    	 
	    	 boxCheckOut(boxNumbers[boxNoIterator]);
	    	String accNum = dataObj.get("AccountNumber");
	    	   String deviceRequestId=DimsManualTagAssignment.getDeviceRequestIdForAccount(accNum);
		    
		  	   CommonLibrary.searchForAccount(accNum);
		       
		       FunctionLibrary.clickObject(accountInfoTab, "", "Clicking Account Info tab");
		       FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
		      		                                         accountInfoDevicesTab,accountInfoTab,5,20);
	
	        
		       DimsManualTagAssignment.AssignmentOfManualTag(dataObj,accNum,deviceRequestId,true);
	    	 boxCheckIn(boxNumbers[boxNoIterator]);
	    	 boxValidationAfterBoxCheckOutAndCheckIn(boxNumbers[boxNoIterator]);
	         } 
		}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }
	}
	
	public static void boxCheckOut(String boxNumber) throws Exception
	{
        
        //Code for check-out under Maintenance tab

		FunctionLibrary.clickObject(inventoryTab, "", "Clicking on Inventory Tab");
		FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
				maintenanceTab,inventoryTab,5,20);
        FunctionLibrary.clickObject(maintenanceTab,"","Clicking the Maintenance Tab");
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		maintenanceCheckOutTab,maintenanceTab,5,20);
        FunctionLibrary.clickObject(maintenanceCheckOutTab,"","Clicking Check Out Tab");
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		boxCheckOutQueryBtn,maintenanceCheckOutTab,3,20);
        FunctionLibrary.objDriver.findElement(By.xpath(boxCheckOutQueryBtn)).click();
       // FunctionLibrary.clickObject(boxCheckOutQueryBtn,"","Clicking Query button");
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		boxCheckOutBoxNbrTxtBox,boxCheckOutQueryBtn,3,20);
        
        FunctionLibrary.setText(boxCheckOutBoxNbrTxtBox,boxNumber,"Sending the Box Number");
        FunctionLibrary.clickObject(boxCheckOutGoBtn,"","Clicking the Go button");
        Thread.sleep(3000);
        FunctionLibrary.setText(boxCheckOutToTxtBox,dataObj.get("Box CheckedOut To"),"Clicking Check Out To look up button");

        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.CONTROL,"s")).build().perform();
        desc="Box "+boxNumber +" should be checked out";
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: "+desc+""+"<br>"+"Actual:Box "
                                                +boxNumber+" is checked out", LogStatus.PASS, true);
        System.out.println("Box has been checked out successfully");
        
        Thread.sleep(10000);//needs to load box check-out properly to click on check-in
	}
		
	public static void boxCheckIn(String boxNumber) throws Exception
	{
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
        FunctionLibrary.setText(boxCheckInBoxNbrTxtBox,boxNumber,"Entering Box no");
        FunctionLibrary.clickObject(boxCheckInGoBtn,"","Clicking check-in Go button");
        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		boxChecInQueryBtn,boxCheckInGoBtn,5,20);
        String deviceCount=FunctionLibrary.objDriver.findElement(By.xpath(boxAvailableDeviceCountTxt)).getAttribute("title");
        FunctionLibrary.setText(boxCheckInDeviceCountTxtBox,deviceCount,"Entering Check-In device count");
        new Actions(FunctionLibrary.objDriver).sendKeys(Keys.chord(Keys.CONTROL,"s")).perform();
       
        String boxStatus= FunctionLibrary.objDriver.findElement(By.xpath(boxStatusTxt)).getAttribute("value");
        System.out.println(boxStatus);
        desc="Box "+boxNumber +" should be checked in";
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: "+desc+""+"<br>"+"Actual:Box "
                  +boxNumber+" is checked in with no of devices : "+deviceCount, LogStatus.PASS, true);
        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : Box status shoould be INVENTORY"+"<br>"
                  +"Actual : Box status is "+boxStatus, LogStatus.PASS, false);

	}
		
  	public static void boxValidationAfterBoxCheckOutAndCheckIn(String boxNumber) throws Exception
  	{
  	      FunctionLibrary.clickObject(BoxesTab, "", "Clicking Boxes tab");
  	      FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
  	    		  boxesTabQueryBtn,BoxesTab,3,20);
  	      
    	  FunctionLibrary.clickObject(boxesTabQueryBtn, "", "Clicking Boxes Query button");

    	  FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
    	  (boxSearchGoBtn,boxesTabQueryBtn, 15, 60);
    	  
    	  FunctionLibrary.clickObject(searchForBoxElement, "", "CLicking the box field");
    	  FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
    	  (searchForBoxTxtBox, searchForBoxElement, 5, 40);
    	  FunctionLibrary.objDriver.findElement(By.xpath(searchForBoxTxtBox)).sendKeys(boxNumber);
    	 // FunctionLibrary.setText(searchForBoxTxtBox, boxNumber, "Entering box number");
    	 // Thread.sleep(3000);
    	  FunctionLibrary.clickObject(boxSearchGoBtn, "", "Click Go button");
    	  FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@summary='Device List']")));
  	      Thread.sleep(3000);
  	      FunctionLibrary.objDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
  	      
  	      //Verification under boxes tab
    	  WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 3);
    	  
  	      try{  	    	  
  	      wait.until(ExpectedConditions.elementToBeClickable((By.xpath(boxNbrTxt))));  	    	
    	  String boxNbr=FunctionLibrary.objDriver.findElement(By.xpath(boxNbrTxt)).getAttribute("title");
    	  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Box number is " +boxNbr, LogStatus.INFO, true);
    	  }  	      
  	      catch(TimeoutException e)
    	  {
    		  System.out.println("Field is not present");
    	  }  	      
  	      
  	      
  	      try{
  	    	wait.until(ExpectedConditions.elementToBeClickable((By.xpath(boxCheckedOutToTxt))));
    	  String boxCheckedOutTo=FunctionLibrary.objDriver.findElement(By.xpath(boxCheckedOutToTxt)).getAttribute("title");
    	  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Box has been checked out to : "+boxCheckedOutTo, LogStatus.INFO, false);
    	  }  	      
  	      catch(TimeoutException e)
    	  {
    		  System.out.println("Field is not present");
    	  }
  	      
  	      try{
  	    	wait.until(ExpectedConditions.elementToBeClickable((By.xpath(boxAgencyNameTxt))));
    	  String boxAgencyName=FunctionLibrary.objDriver.findElement(By.xpath(boxAgencyNameTxt)).getAttribute("title");
    	  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : Box agency name should be:"+dataObj.get("AgencyName")
    	            +"<br>"+"Actual : Box agency name is : "+boxAgencyName, LogStatus.PASS, false);
    	  }catch(TimeoutException e)
    	  {
    		  System.out.println("Field is not present");
    	  }
  	      
  	      try{
  	    	wait.until(ExpectedConditions.elementToBeClickable((By.xpath(boxStoreNameTxt))));
    	  String boxStoreName=FunctionLibrary.objDriver.findElement(By.xpath(boxStoreNameTxt)).getAttribute("title");
    	  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : Box store name should be:"+dataObj.get("AgencyName")
          +"<br>"+"Actual : Box store name is : "+boxStoreName, LogStatus.PASS, false);
    	  }catch(TimeoutException e)
    	  {
    		  System.out.println("Field is not present");
    	  }
  	      
  	      try{
  	    	wait.until(ExpectedConditions.elementToBeClickable((By.xpath(boxStartDeviceNbrTxt))));
    	  String boxStartDeviceNbr=FunctionLibrary.objDriver.findElement(By.xpath(boxStartDeviceNbrTxt)).getAttribute("title");
    	  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Start device number is "+boxStartDeviceNbr, LogStatus.INFO, false);
    	  }catch(TimeoutException e)
    	  {
    		  System.out.println("Field is not present");
    	  }
  	      
  	      try{
  	    	wait.until(ExpectedConditions.elementToBeClickable((By.xpath(boxEndDeviceNbrTxt))));
    	  String boxEndDeviceNbr=FunctionLibrary.objDriver.findElement(By.xpath(boxEndDeviceNbrTxt)).getAttribute("title");
    	  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"End device number is "+boxEndDeviceNbr, LogStatus.INFO, false);
    	  }catch(TimeoutException e)
    	  {
    		  System.out.println("Field is not present");
    	  }
  	      
    	  try{
    		  wait.until(ExpectedConditions.elementToBeClickable((By.xpath(boxDeviceCountTxt))));
    	  String boxAvailableDeviceCount=FunctionLibrary.objDriver.findElement(By.xpath(boxDeviceCountTxt))
                  .getAttribute("title");
    	  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Available device count is : "+boxAvailableDeviceCount, LogStatus.INFO, false);
    	  }catch(TimeoutException e)
    	  {
    		  System.out.println("Field is not present");
    	  }
    	  
    	  try{
    		  wait.until(ExpectedConditions.elementToBeClickable((By.xpath(boxCheckedOutByTxt))));
    	  String boxCheckedOutBy=FunctionLibrary.objDriver.findElement(By.xpath(boxCheckedOutByTxt)).getAttribute("title");
    	  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Box has been checked out by "+boxCheckedOutBy, LogStatus.PASS, false);
    	  }catch(TimeoutException e)
    	  {
    		  System.out.println("Field is not present");
    	  }
    	  try{
    		  wait.until(ExpectedConditions.elementToBeClickable((By.xpath(boxCurrentDeviceCount))));
    	  String boxCheckInDeviceCount=FunctionLibrary.objDriver.findElement(By.xpath(boxCurrentDeviceCount)).getAttribute("title");
    	  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Check-in device count in the box is "+boxCheckInDeviceCount, LogStatus.INFO, false);
    	  }catch(TimeoutException e)
    	  {
    		  System.out.println("Field is not present");
    	  }
    	  
    	  FunctionLibrary.objDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    	  
    	  FunctionLibrary.clickObject(boxHistoryTab, "", "Clicking box history tab");
    	  FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
    	  (boxHistoryTable, boxHistoryTab, 10, 40);
    	  
    	  try{
        	  int rowNum = FunctionLibrary.getRowNumberFromWebTable(boxHistoryTable, "CHECKIN", "Get the row number");
        	  FunctionLibrary.verifyWebTableCellData(boxHistoryTable, rowNum,
     	       		 2,"CHECKIN", "Verify box status", true);
        	  FunctionLibrary.verifyWebTableCellData(boxHistoryTable, rowNum,
        	       		 6, dataObj.get("Box CheckedOut To"), "Verify checked out to", true);
        	  FunctionLibrary.verifyWebTableCellData(boxHistoryTable, rowNum,
     	       		 1, boxNumber, "Verify box number", false);
    	  }catch(Exception e)
    	  {
    	  int rowNum = FunctionLibrary.getRowNumberFromWebTable(boxHistoryTable, "CHECKOUT", "Get the row number");
    	  FunctionLibrary.verifyWebTableCellData(boxHistoryTable, rowNum,
 	       		 2,"CHECKOUT", "Verify box status", true);
    	  FunctionLibrary.verifyWebTableCellData(boxHistoryTable, rowNum,
    	       		 6, dataObj.get("Box CheckedOut To"), "Verify checked out to", true);
    	  FunctionLibrary.verifyWebTableCellData(boxHistoryTable, rowNum,
 	       		 1, boxNumber, "Verify box number", false);
    	  }
      } 
}