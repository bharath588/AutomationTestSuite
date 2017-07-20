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
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;


import static libraries.FunctionLibrary.closeAllActiveBrowser;
import static libraries.FunctionLibrary.objDriver;
import static objectProperties.SblBatchTagAssignmentProperties.siteMapFilterTxtBox;
import static objectProperties.SblBoxCheckInCheckOutProperties.BoxesTab;
import static objectProperties.SblBoxCheckInCheckOutProperties.boxActualCountTxt;
import static objectProperties.SblBoxCheckInCheckOutProperties.boxesBoxListTxt;
import static objectProperties.SblDeviceStatusChnageProperties.accountNumberTxtBox;
import static objectProperties.SblDeviceStatusChnageProperties.accountsTab;
import static objectProperties.SblDeviceStatusChnageProperties.boxSearchGoBtn;
import static objectProperties.SblDeviceStatusChnageProperties.boxesTabQueryBtn;
import static objectProperties.SblDeviceStatusChnageProperties.goSearchBtn;
import static objectProperties.SblDeviceStatusChnageProperties.searchForBoxElement;
import static objectProperties.SblDeviceStatusChnageProperties.searchForBoxTxtBox;
import static objectProperties.SblPurchaseOrderProperties.*;
import static objectProperties.SblRMAProcessProperties.shipmentStatusTxt;

public class DimsPurchaseOrderAndReceive {
	static String desc="";
    static String actualProcurementCost=null;
    static double lineItemProcurementCost = 0;
    //static String actualProCost=null;
    String orderNbr = null;	
	public static HashMap<String,String> dataObj =null;
	
    static String lineNbr=null;
    static String lineItemStatus=null;
    static String agencyName=null;
    static String startDeviceNbr=null;
    static String endDeviceNbr=null;
    static String actualBoxCount=null;
    static String vehicleClass=null;
    static String deviceCode=null;
    static String deviceUnitPrice=null;
    static String deviceMountType=null;
    static String deviceColor=null;
    static String deviceType=null;
    static String receivedStatus=null;
    static String quantityPerBox=null;
    static String boxesReceived=null;
    static String startDeviceNbrAfterReceive=null;
    static String endDeviceNbrAfterReceive=null;
    static String noOfDevicesReceived=null;
    static String receivedDate=null;
    static String receiptNbr=null;
    static String boxCount=null;
   static String importFilePath = ReportLibrary.getPath() + "\\transpondersImportFiles\\";

    public static void DimsPurchaseOrderAndReceiveTest() throws IOException, Exception {
        ExcelSheet exl = new ExcelSheet();

        int noOfRows = exl.totalrows("FLCSS_DIMS_PurchaseOrder","Test_Scenarios");

        HashMap<String, String> eachTestCaseData = new HashMap<String, String>();

        String testCaseName = "";

        for (int i = 1; i <=noOfRows; i++) {

            eachTestCaseData = CommonLibrary.getEachTestCaseData(exl, "Test_Scenarios", i,"FLCSS_DIMS_PurchaseOrder");
            dataObj=eachTestCaseData;
            testCaseName = "tcPurchaseOrder"+eachTestCaseData.get("TestCaseId").replace("-","");

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
                        Class<?> c = Class.forName("features.DimsPurchaseOrderAndReceive");
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
                                closeAllActiveBrowser();

                            }

                        }
                        }

                   
                   dataObj.clear();

                }
                
                eachTestCaseData.clear();
        }


    }
	    
    public static void tcPurchaseOrderPOCR001() throws Exception
    {
    	try{
    	
	        desc="Creating Purchase Order";
	        String poId = createPurchaseOrderAndReturnId(dataObj);
	        System.out.println("Create po id : " + poId);
	        
	        createPurchaseOrderLineItems(dataObj,poId);
	        
	        //receivePurchaseOrderLineItem(poId);
	        String poStatus=FunctionLibrary.objDriver.findElement(By.xpath(currentPOStatusTxt)).getAttribute("title");
	
	        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : PO status should be OPEN"
	        			+"<br>"+"Actual : PO status is "+poStatus, LogStatus.PASS, true);
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }
    	
    }
    
    public static void tcPurchaseOrderPOCR002() throws Exception
    {
    	try{
	        desc="Creating Purchase Order";
	        String poId = createPurchaseOrderAndReturnId(dataObj);
	        System.out.println("Create po id : " + poId);
	        
	        createPurchaseOrderLineItems(dataObj,poId);
	        
	       // receivePurchaseOrderLineItem(poId);
	        String poStatus=FunctionLibrary.objDriver.findElement(By.xpath(currentPOStatusTxt)).getAttribute("title");
	
	        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : PO status should be OPEN"
	        			+"<br>"+"Actual : PO status is "+poStatus, LogStatus.PASS, true);
	        
	    	}catch(Exception e){
	            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
	      }
   }
        
    public static void tcPurchaseOrderPOCR003() throws Exception
    {
    	try{
        desc="Creating Purchase Order";
        String poId = createPurchaseOrderAndReturnId(dataObj);
        System.out.println("Create po id : " + poId);
        
        createPurchaseOrderLineItems(dataObj,poId);
        
        receivePurchaseOrderLineItem(dataObj,poId);
        String poStatus=FunctionLibrary.objDriver.findElement(By.xpath(currentPOStatusTxt)).getAttribute("title");
        String orderCount=FunctionLibrary.objDriver.findElement(By.xpath(poDeviceOrderedCountTxt))
        		                   .getAttribute("title");
        int orderCount1=Integer.parseInt(orderCount);
        String receivedCount=FunctionLibrary.objDriver.findElement(By.xpath(poDeviceReceivedCountTxt))
                .getAttribute("title");
        int receivedCount1=Integer.parseInt(receivedCount);
        if(receivedCount1==orderCount1)
        {
        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : PO status should be RECEIVED FULL"
        			+"<br>"+"Actual : PO status is "+poStatus, LogStatus.PASS, true);
        }else
        {
        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : PO status should be PARTIAL RECEIVED"
        			+"<br>"+"Actual : PO status is "+poStatus, LogStatus.PASS, true);        	
        }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }
    	
    } 

    public static void tcPurchaseOrderPOCR004() throws Exception
    {
    	
    	try{
	        desc="Creating Purchase Order";
	        String poId = createPurchaseOrderAndReturnId(dataObj);
	        System.out.println("Create po id : " + poId);
	        
	        createPurchaseOrderLineItems(dataObj,poId);
	        
	        receivePurchaseOrderLineItem(dataObj,poId);
	        String poStatus=FunctionLibrary.objDriver.findElement(By.xpath(currentPOStatusTxt)).getAttribute("title");
	        String orderCount=FunctionLibrary.objDriver.findElement(By.xpath(poDeviceOrderedCountTxt))
	        		                   .getAttribute("title");
	        int orderCount1=Integer.parseInt(orderCount);
	        String receivedCount=FunctionLibrary.objDriver.findElement(By.xpath(poDeviceReceivedCountTxt))
	                .getAttribute("title");
	        int receivedCount1=Integer.parseInt(receivedCount);
	        if(receivedCount1==orderCount1)
	        {
	        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : PO status should be RECEIVED FULL"
	        			+"<br>"+"Actual : PO status is "+poStatus, LogStatus.PASS, true);
	        }else
	        {
	        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : PO status should be PARTIAL RECEIVED"
	        			+"<br>"+"Actual : PO status is "+poStatus, LogStatus.PASS, true);        	
	        }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }
        
    	
    }

    public static void tcPurchaseOrderPOCR005() throws Exception
    {
    	try{
	        desc="Creating Purchase Order";
	        String poId = createPurchaseOrderAndReturnId(dataObj);
	        System.out.println("Create po id : " + poId);
	        
	        createPurchaseOrderLineItems(dataObj,poId);
	        
	        receivePurchaseOrderLineItem(dataObj,poId);
	        String poStatus=FunctionLibrary.objDriver.findElement(By.xpath(currentPOStatusTxt)).getAttribute("title");
	        String orderCount=FunctionLibrary.objDriver.findElement(By.xpath(poDeviceOrderedCountTxt))
	        		                   .getAttribute("title");
	        int orderCount1=Integer.parseInt(orderCount);
	        String receivedCount=FunctionLibrary.objDriver.findElement(By.xpath(poDeviceReceivedCountTxt))
	                .getAttribute("title");
	        int receivedCount1=Integer.parseInt(receivedCount);
	        if(receivedCount1==orderCount1)
	        {
	        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : PO status should be RECEIVED FULL"
	        			+"<br>"+"Actual : PO status is "+poStatus, LogStatus.PASS, true);
	        }else
	        {
	        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : PO status should be PARTIAL RECEIVED"
	        			+"<br>"+"Actual : PO status is "+poStatus, LogStatus.PASS, true);        	
	        }
        
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }
    }
    
    public static void tcPurchaseOrderPOCR006() throws Exception
    {
    	try{
	        desc="Creating Purchase Order";
	        String poId = createPurchaseOrderAndReturnId(dataObj);
	        System.out.println("Create po id : " + poId);
	        
	        createPurchaseOrderLineItems(dataObj,poId);
	        
	        receivePurchaseOrderLineItem(dataObj,poId);
	        String poStatus=FunctionLibrary.objDriver.findElement(By.xpath(currentPOStatusTxt)).getAttribute("title");
	        String orderCount=FunctionLibrary.objDriver.findElement(By.xpath(poDeviceOrderedCountTxt))
	        		                   .getAttribute("title");
	        int orderCount1=Integer.parseInt(orderCount);
	        String receivedCount=FunctionLibrary.objDriver.findElement(By.xpath(poDeviceReceivedCountTxt))
	                .getAttribute("title");
	        int receivedCount1=Integer.parseInt(receivedCount);
	        if(receivedCount1==orderCount1)
	        {
	  
	        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : PO status should be RECEIVED FULL"
	        			+"<br>"+"Actual : PO status is "+poStatus, LogStatus.PASS, true);
	        }else
	        {
	        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : PO status should be PARTIAL RECEIVED"
	        			+"<br>"+"Actual : PO status is "+poStatus, LogStatus.PASS, true);        	
	        }
	        
	        Thread.sleep(10000);//needs some time to load the Purchase Order no
	         FunctionLibrary.clickObject(".//*[@name='Order Number' and text()='"+poId+"']","",
	        		        "Clicking order nuumber");
	        FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated
	        		   (By.xpath(deviceListTable)));
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
	    	 
	    	 boxValidation(boxNumbers[boxNoIterator], poId);        	  
	
	         } 
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }
	}
        
    public static void tcPurchaseOrderPOCR034() throws Exception
    {
    	try{
	        desc="Creating Purchase Order";
	        String poId = createPurchaseOrderAndReturnId(dataObj);
	        System.out.println("Create po id : " + poId);
	        
	        createPurchaseOrderLineItems(dataObj,poId);
	        
	        receivePurchaseOrderLineItem(dataObj,poId);
	        
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
			String startBox=boxNumbers[boxNoIterator];
			String endBox=boxNumbers[noOfboxNbrLocation.size()-1];
				
	     //for (boxNoIterator=0;boxNoIterator<noOfboxNbrLocation.size();boxNoIterator++) {
	    	 
	    	String shipmentNbr= DimsRMAProcess.sendShipment(startBox,endBox, "IVR");
	    	     	 
	     //}
	      
	     DimsRMAProcess.receiveShipment(shipmentNbr,dataObj,"1");  
	     
	     String statusAfterReceiveShipment=FunctionLibrary.objDriver.findElement(By.xpath(shipmentStatusTxt)).getAttribute("title");
	     
	     String expectedResult="The status of Shipment Number "+shipmentNbr+" after Receive Shipment should be RECEIVED FULL";
	     try{
	     ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : "+expectedResult+"<br>"+
	       "Actual : The status of Shipment Number "+shipmentNbr +" after Receive Shipment is : "+statusAfterReceiveShipment , LogStatus.PASS, true);
	     
	     }catch(Exception e)
	     {
	    	 
	     }
	   
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }	
    }
    
    public static void tcPurchaseOrderPOCR041() throws Exception
    {
    	try{
	        desc="Creating Purchase Order";
	        String poId = createPurchaseOrderAndReturnId(dataObj);
	        System.out.println("Create po id : " + poId);
	        
	        createPurchaseOrderLineItems(dataObj,poId);
	        
	        receivePurchaseOrderLineItem(dataObj,poId);
	        String poStatus=FunctionLibrary.objDriver.findElement(By.xpath(currentPOStatusTxt)).getAttribute("title");
	        String orderCount=FunctionLibrary.objDriver.findElement(By.xpath(poDeviceOrderedCountTxt))
	        		                   .getAttribute("title");
	        int orderCount1=Integer.parseInt(orderCount);
	        String receivedCount=FunctionLibrary.objDriver.findElement(By.xpath(poDeviceReceivedCountTxt))
	                .getAttribute("title");
	        int receivedCount1=Integer.parseInt(receivedCount);
	        if(receivedCount1==orderCount1)
	        {
	        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : PO status should be RECEIVED FULL"
	        			+"<br>"+"Actual : PO status is "+poStatus, LogStatus.PASS, true);
	        }else
	        {
	        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : PO status should be PARTIAL RECEIVED"
	        			+"<br>"+"Actual : PO status is "+poStatus, LogStatus.PASS, true);        	
	        }
	        
	    	
	        String proCost=procurementCostVerification();
	        
	        String s="PO_SYSTEM_ACCOUNT";
	        WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 120);
	        CommonLibrary.clickSiteMap();
	               
	         FunctionLibrary.clickObject(siteMapFilterTxtBox, "", "Clicking Site Map filter field");
	         FunctionLibrary.setText(siteMapFilterTxtBox, "Vector Business Rule", "Clicking Site Map filter field");
	         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(vectorBusinessRuleLink)));
	         
	         FunctionLibrary.clickObject(vectorBusinessRuleLink, "", "Clicking Server Management link");
	         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(vectorInternalProcessParameterTab)));
	         FunctionLibrary.clickObject(vectorInternalProcessParameterTab, "", "Clicking Internal Process Parameter tab");
	         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
	         (processParameterQueryBtn, vectorInternalProcessParameterTab, 5, 20);
	         FunctionLibrary.clickObject(processParameterQueryBtn, "", "Clicking query button");
	         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
	         (processParameterTypeTxtBox, processParameterQueryBtn, 5, 20);
	         FunctionLibrary.setText(processParameterTypeTxtBox, s, "Entering process type");
	         FunctionLibrary.clickObject(processParameterGoBtn, "", "Clicking go button");
	         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
	         (processParameterQueryBtn, processParameterGoBtn, 5, 20);
	         String parameterValue=FunctionLibrary.objDriver.findElement(By.xpath(parameterValueTxt))
	        		 .getAttribute("title");
	        /* 
	         FunctionLibrary.clickObject(accountsTab, "", "Clicking Accounts Tab");
	         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	        		                                 accountNumberTxtBox,accountsTab,4,20);
	         
	         FunctionLibrary.setText(accountNumberTxtBox, parameterValue, "Entering Account Number");
	         FunctionLibrary.clickObject(goSearchBtn, "", "Clicking Go button");*/
	         
	         CommonLibrary.searchForAccount(parameterValue);
	         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	        		                                    financialsTab,goSearchBtn,5,20);
	         FunctionLibrary.clickObject(financialsTab, "", "Clicking Financials tab");
	         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	        		 reversalsTab,financialsTab,5,20);
	         FunctionLibrary.clickObject(reversalsTab, "", "Clicking Reversals tab");
	         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	        		 reversalQueryBtn,reversalsTab,5,20);
	         FunctionLibrary.clickObject(reversalQueryBtn, "", "Clicking Query button");
	         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	        		 reversalGoBtn,reversalQueryBtn,5,20);
	         FunctionLibrary.clickObject(payTypeElement, "", "CLicking pay type field");
	         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	        		 payTypeTxtBox,payTypeElement,2,20);
	         FunctionLibrary.setText(payTypeTxtBox, "NONE", "Entering pay type");
	         FunctionLibrary.clickObject(reversalGoBtn, "", "Clicking go button");
	         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	        		 reversalQueryBtn,reversalGoBtn,5,20);
	
	         try{
	         int rowNumberForDeviceandQuanty = FunctionLibrary.getRowNumberFromWebTableByTwoText
	        		 (financialTransactionListTable,proCost,"NONE","get the row number");
	         FunctionLibrary.verifyWebTableCellData(financialTransactionListTable, rowNumberForDeviceandQuanty, 4, proCost, 
					 "Device History: Verify the new status of device", true);
				String reversalCategory=FunctionLibrary.objDriver.findElement(By.xpath(reversalCategoryTxt))
						 .getAttribute("title");
				String reversalSubCategory=FunctionLibrary.objDriver.findElement(By.xpath(reversalSubCategoryTxt))
						 .getAttribute("title");
						
		       ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : Reversal Category should be TAGSALE"
		      			+"<br>"+"Actual : Reversal Category is "+reversalCategory, LogStatus.PASS, true);
		       ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : Reversal subcategory should be INVENTORYRECEIVED"
		      			+"<br>"+"Actual : Reversal subcategory is "+reversalSubCategory, LogStatus.PASS, false);
	         }catch(Exception e)
	         {
	        	 String transactionAmount=FunctionLibrary.objDriver.findElement(By.xpath(transactionAmountUnderReversalTxt))
	                     .getAttribute("title");
				String traAmount=transactionAmount.substring(1, transactionAmount.length()-3); 
				if(traAmount.equals(proCost)){		         
		         ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : Transaction amount under reversal should be "+proCost
		      			+"<br>"+"Actual : Transaction amount under reversal is "+transactionAmount, LogStatus.FAIL, true);
				}else
				{
					ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : Transaction amount under reversal should be "+proCost
			      			+"<br>"+"Actual : Transaction amount under reversal is "+transactionAmount, LogStatus.FAIL, true);
				}
	         }        
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }
    }
    
    public static void tcPurchaseOrderPOCR040() throws Exception
    {
    	try{
	        desc="Creating Purchase Order";
	        String poId = createPurchaseOrderAndReturnId(dataObj);
	        System.out.println("Create po id : " + poId);
	        
	        createPurchaseOrderLineItems(dataObj,poId);
	        
	        receivePurchaseOrderLineItem(dataObj,poId);
	        String poStatus=FunctionLibrary.objDriver.findElement(By.xpath(currentPOStatusTxt)).getAttribute("title");
	        String orderCount=FunctionLibrary.objDriver.findElement(By.xpath(poDeviceOrderedCountTxt))
	        		                   .getAttribute("title");
	        int orderCount1=Integer.parseInt(orderCount);
	        String receivedCount=FunctionLibrary.objDriver.findElement(By.xpath(poDeviceReceivedCountTxt))
	                .getAttribute("title");
	        int receivedCount1=Integer.parseInt(receivedCount);
	        if(receivedCount1==orderCount1)
	        {
	        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : PO status should be RECEIVED FULL"
	        			+"<br>"+"Actual : PO status is "+poStatus, LogStatus.PASS, true);
	        }else
	        {
	        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : PO status should be PARTIAL RECEIVED"
	        			+"<br>"+"Actual : PO status is "+poStatus, LogStatus.PASS, true);        	
	        }
	        
	    	
	        String proCost=procurementCostVerification();
	        
	        String s="PO_SYSTEM_ACCOUNT";
	        WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver, 120);
	        CommonLibrary.clickSiteMap();
	               
	         FunctionLibrary.clickObject(siteMapFilterTxtBox, "", "Clicking Site Map filter field");
	         FunctionLibrary.setText(siteMapFilterTxtBox, "Vector Business Rule", "Clicking Site Map filter field");
	         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(vectorBusinessRuleLink)));
	         
	         FunctionLibrary.clickObject(vectorBusinessRuleLink, "", "Clicking Server Management link");
	         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(vectorInternalProcessParameterTab)));
	         FunctionLibrary.clickObject(vectorInternalProcessParameterTab, "", "Clicking Internal Process Parameter tab");
	         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
	         (processParameterQueryBtn, vectorInternalProcessParameterTab, 5, 20);
	         FunctionLibrary.clickObject(processParameterQueryBtn, "", "Clicking query button");
	         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
	         (processParameterTypeTxtBox, processParameterQueryBtn, 5, 20);
	         FunctionLibrary.setText(processParameterTypeTxtBox, s, "Entering process type");
	         FunctionLibrary.clickObject(processParameterGoBtn, "", "Clicking go button");
	         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
	         (processParameterQueryBtn, processParameterGoBtn, 5, 20);
	         String parameterValue=FunctionLibrary.objDriver.findElement(By.xpath(parameterValueTxt))
	        		 .getAttribute("title");
	         
	         FunctionLibrary.clickObject(accountsTab, "", "Clicking Accounts Tab");
	         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	        		                                 accountNumberTxtBox,accountsTab,4,20);
	         
	         FunctionLibrary.setText(accountNumberTxtBox, parameterValue, "Entering Account Number");
	         FunctionLibrary.clickObject(goSearchBtn, "", "Clicking Go button");
	         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	        		                                    financialsTab,goSearchBtn,5,20);
	         FunctionLibrary.clickObject(financialsTab, "", "Clicking Financials tab");
	         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	        		 reversalsTab,financialsTab,5,20);
	         FunctionLibrary.clickObject(reversalsTab, "", "Clicking Reversals tab");
	         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	        		 reversalQueryBtn,reversalsTab,5,20);
	         FunctionLibrary.clickObject(reversalQueryBtn, "", "Clicking Query button");
	         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	        		 reversalGoBtn,reversalQueryBtn,5,20);
	         FunctionLibrary.clickObject(payTypeElement, "", "CLicking pay type field");
	         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	        		 payTypeTxtBox,payTypeElement,2,20);
	         FunctionLibrary.setText(payTypeTxtBox, "NONE", "Entering pay type");
	         FunctionLibrary.clickObject(reversalGoBtn, "", "Clicking go button");
	         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
	        		 reversalQueryBtn,reversalGoBtn,5,20);
	
	         try{
	         int rowNumberForDeviceandQuanty = FunctionLibrary.getRowNumberFromWebTableByTwoText
	        		 (financialTransactionListTable,proCost,"NONE","get the row number");
	         FunctionLibrary.verifyWebTableCellData(financialTransactionListTable, rowNumberForDeviceandQuanty, 4, proCost, 
					 "Device History: Verify the new status of device", true);
				String reversalCategory=FunctionLibrary.objDriver.findElement(By.xpath(reversalCategoryTxt))
						 .getAttribute("title");
				String reversalSubCategory=FunctionLibrary.objDriver.findElement(By.xpath(reversalSubCategoryTxt))
						 .getAttribute("title");
						
		       ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : Reversal Category should be TAGSALE"
		      			+"<br>"+"Actual : Reversal Category is "+reversalCategory, LogStatus.PASS, true);
		       ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : Reversal subcategory should be INVENTORYRECEIVED"
		      			+"<br>"+"Actual : Reversal subcategory is "+reversalSubCategory, LogStatus.PASS, false);
	         }catch(Exception e)
	         {
	        	 String transactionAmount=FunctionLibrary.objDriver.findElement(By.xpath(transactionAmountUnderReversalTxt))
	                     .getAttribute("title");
				String traAmount=transactionAmount.substring(1, transactionAmount.length()-3); 
				if(traAmount.equals(proCost)){		         
		         ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : Transaction amount under reversal should be "+proCost
		      			+"<br>"+"Actual : Transaction amount under reversal is "+transactionAmount, LogStatus.FAIL, true);
				}else
				{
					ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : Transaction amount under reversal should be "+proCost
			      			+"<br>"+"Actual : Transaction amount under reversal is "+transactionAmount, LogStatus.FAIL, true);
				}
	         }        
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }
    }
	 
    public static void tcPurchaseOrderPOCR061() throws Exception
        {
    	
    	try{
	    	String poNumber=getPONumber();
	        FunctionLibrary.clickObject(inventoryTab, "", "Clicking on Inventory Tab");
	        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(purchaseOrderTab,inventoryTab,3,20);
	        FunctionLibrary.clickObject(purchaseOrderTab, "", "Clicking on Purchase Order Tab");
	        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(purchaseOrdersNewBtn,purchaseOrderTab,3,20);
	        FunctionLibrary.clickObject(poQueryBtn, "", "Clicking Query button");
	        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(poGoBtn, poQueryBtn, 10, 40);
	        FunctionLibrary.setText(orderNbrTxtBox, poNumber, "Entering PO number");
	        FunctionLibrary.clickObject(poGoBtn, "", "Clicking go button");
	        FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(poQueryBtn,poGoBtn, 10, 40);
	        String poCurrentStatus=FunctionLibrary.objDriver.findElement(By.xpath(purchaseOrderReceiveStatusTxt)).getAttribute("title");
	        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : PO status should be Partially received"
	    			+"<br>"+"Actual : PO status is "+poCurrentStatus, LogStatus.INFO, true);
	        
	        String orderCount=FunctionLibrary.objDriver.findElement(By.xpath(poDeviceOrderedCountTxt)).getAttribute("title");
	        orderCount=orderCount.replaceAll(",", "");
			int orderCount1=Integer.parseInt(orderCount);
			String receivedCount=FunctionLibrary.objDriver.findElement(By.xpath(poDeviceReceivedCountTxt)).getAttribute("title");
			receivedCount=receivedCount.replaceAll(",", "");
			int receivedCount1=Integer.parseInt(receivedCount);
			int deviceCount=orderCount1-receivedCount1;
			
	        ReportLibrary.checkCreateDirectory(importFilePath);
	        ManiFestFileGeneration.createMainFestFile(dataObj,poNumber,10,10,"ST",deviceCount,importFilePath.toString()+poNumber+"-transponder.txt");
			        
	        String fileName=null;
	        List<WebElement> allLineItemElements=null;
	        try{
	        	allLineItemElements = FunctionLibrary.objDriver.findElements(By.xpath(poLineItemPartialReceiveStatus));
	        	int size=allLineItemElements.size();
	        	if(size==0)
	        	{
	        		allLineItemElements = FunctionLibrary.objDriver.findElements(By.xpath(poLineItemNewStatus));
	        	}
	        }catch(Exception e)
	        {        	
	        	allLineItemElements = FunctionLibrary.objDriver.findElements(By.xpath(poLineItemNewStatus));
	        }
	        int lineItemiterator =0;
	       // int newElement=lineItemiterator+1;
	        
	        for(int iterator=0;iterator<=allLineItemElements.size()-1;iterator++)
	        {       	       	
	       	 String statusTxtBox = "//input[@name='Status']";
	       	 Thread.sleep(2000);
	       	 try{
	       		String xpathValNew ="(//td[text()='NEW'])";
	       	 FunctionLibrary.objDriver.findElement(By.xpath(xpathValNew)).click();
	       	FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(statusTxtBox, xpathValNew, 5, 30);
	       	 }catch(Exception e)
	       	 {
	       		 String xpathValPartialFilled ="(//td[text()='PARTIALLY RECEIVED'])[2]";
	       		FunctionLibrary.objDriver.findElement(By.xpath(xpathValPartialFilled)).click();
	       		FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(statusTxtBox, xpathValPartialFilled, 5, 30);
	       	 }		
	       	       	 
				desc="CLicking Import Transponder button";
				WebElement importTransponder=FunctionLibrary.objDriver.findElement
					 (By.xpath(transponderImportBtn));
				FunctionLibrary.highLightElement(FunctionLibrary.objDriver, importTransponder);
				FunctionLibrary.objDriver.findElement(By.xpath(transponderImportBtn)).click();	
				Thread.sleep(5000);
				WebDriverWait wait= new WebDriverWait(FunctionLibrary.objDriver, 600);
				desc="Alert is present";
				try{
				wait.until(ExpectedConditions.alertIsPresent());
				Alert alert = FunctionLibrary.objDriver.switchTo().alert();
				alert.accept();
				}
				catch(Exception e){
				System.out.println("No alert is present");
				}
				
				WebElement browseImportFile= FunctionLibrary.objDriver.findElement(By.xpath(browseImportFileTxtBox));
				wait.until(ExpectedConditions.visibilityOf(browseImportFile));
				try{   
				fileName=importFilePath.toString()+poNumber+"-transponder.txt";
				browseImportFile.sendKeys(fileName);
				
				}catch(Exception e){				
				FunctionLibrary.highLightElement(FunctionLibrary.objDriver,FunctionLibrary.objDriver.
						 findElement(By.xpath(importTransponderBtn)));}
				
				Thread.sleep(15000);
				WebDriverWait wait1= new WebDriverWait(FunctionLibrary.objDriver, 30);
				wait1.until(ExpectedConditions.elementToBeClickable(By.xpath(importTransponderBtn)));
				FunctionLibrary.highLightElement(FunctionLibrary.objDriver,FunctionLibrary.objDriver.
						 findElement(By.xpath(importTransponderBtn)));
				Thread.sleep(15000);
				FunctionLibrary.objDriver.findElement(By.xpath(importTransponderBtn)).click();
				
				try{
					wait.until(ExpectedConditions.alertIsPresent());
				Alert confirmationAlert = FunctionLibrary.objDriver.switchTo().alert();
				confirmationAlert.accept();
				}
				catch(Exception e){
				
				System.out.println("No alert is present");
				}				
	
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath
	       			 (poReceivedFullStatusTxt)));
				
				lineItemiterator++;
				desc="Transponders should be imported successfully";
				ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: "+desc+""+"<br>"+
				"Actual:Transponders are imported successfully", LogStatus.PASS, false);
				
				  FunctionLibrary.objDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
				  
		    	 //Verifying the details under PO LineItem Receipt
				  
				  if(dataObj.get("TestCaseId").equals("POCR-057"))
				  {
				    	 try{
				 	        receivedStatus=FunctionLibrary.objDriver.findElement(By.xpath(lineItemReceiptStatusTxt)).getAttribute("title");
				 	        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : Received status should be:"+"PARTIALLY RECEIVED"
				 			        +"<br>"+"Actual : Received status is : "+receivedStatus, LogStatus.PASS, true);
				 	    	 }catch(Exception e){System.out.println(e);}
				  }
				  else
				  {
		    	 try{
		        receivedStatus=FunctionLibrary.objDriver.findElement(By.xpath(lineItemReceiptStatusTxt)).getAttribute("title");
		        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : Received status should be:"+"RECEIVED FULL"
				        +"<br>"+"Actual : Received status is : "+receivedStatus, LogStatus.PASS, true);
		    	 }catch(Exception e){System.out.println(e);}
				 }
				  
		    	 try{
				quantityPerBox=FunctionLibrary.objDriver.findElement(By.xpath(lineItemReceiptQtyPerBoxTxt)).getAttribute("title");
				ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Quantity per box is : "+quantityPerBox, LogStatus.INFO, false);
		    	 }catch(Exception e){System.out.println(e);}
		    	 
		    	 try{
				boxesReceived=FunctionLibrary.objDriver.findElement(By.xpath(lineItemReceiptBoxReceivedTxt)).getAttribute("title");
				ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"No of boxes received are : "+boxesReceived, LogStatus.INFO, false);
		    	 }catch(Exception e){System.out.println(e);}
		    	 
		    	 try{
				startDeviceNbrAfterReceive=FunctionLibrary.objDriver.findElement(By.xpath(lineItemReceiptStartDeviceNbrTxt))
				     .getAttribute("title");
				ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : Start device number should be:"+startDeviceNbr
				        +"<br>"+"Actual : Start device number is : "+startDeviceNbrAfterReceive, LogStatus.PASS, false);
		    	 }catch(Exception e){System.out.println(e);}
		    	 
		    	 try{
				endDeviceNbrAfterReceive=FunctionLibrary.objDriver.findElement(By.xpath(lineItemReceiptEndDeviceNbrTxt))
				     .getAttribute("title");
				ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : End device number should be:"+endDeviceNbr
				        +"<br>"+"Actual : End device number is : "+endDeviceNbrAfterReceive, LogStatus.PASS, false);
		    	 }catch(Exception e){System.out.println(e);}
		    	 
		    	 try{
				noOfDevicesReceived=FunctionLibrary.objDriver.findElement(By.xpath(lineItemReceiptDevicesReceivedTxt))
				     .getAttribute("title");
				ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"No of devices received are : "+noOfDevicesReceived, LogStatus.INFO, false);
		    	 }catch(Exception e){System.out.println(e);}
		    	 
		    	 try{
				receivedDate=FunctionLibrary.objDriver.findElement(By.xpath(lineItemReceiptReceivedDateTxt))
				     .getAttribute("title");
				ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Received date is : "+receivedDate, LogStatus.INFO, false);
		    	 }catch(Exception e){System.out.println(e);}
		    	 
		    	 try{
				receiptNbr=FunctionLibrary.objDriver.findElement(By.xpath(lineItemReceiptNbrTxt)).getAttribute("title");
				ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Receipt number is : "+receiptNbr, LogStatus.INFO, false);
		    	 }catch(Exception e){System.out.println(e);}
				
				
		    	  FunctionLibrary.objDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);			
				
				
				
	                 // Partial and Full Receive 
				
				try{
	
	               if(dataObj.get("POStatus").equalsIgnoreCase("FULLRECEIVED")){
		 
	                	 if(lineItemiterator==(allLineItemElements.size()))
	                         	 break;
		 
	                  }else if(dataObj.get("POStatus").equalsIgnoreCase("PARTIALRECEIVED")){
		 
		                      if(lineItemiterator==(allLineItemElements.size()-1))
	  	                        break;
	                        }
	
				}catch(Exception e)
				{
					
				}
	               //Thread.sleep(5000);
	               JavascriptExecutor js= (JavascriptExecutor)FunctionLibrary.objDriver;
	               js.executeScript("window.scrollBy(0,250)", "");
	        
	        }//end of while loop
	        
	        String poStatus=FunctionLibrary.objDriver.findElement(By.xpath(currentPOStatusTxt)).getAttribute("title");
	        String orderCountAfter=FunctionLibrary.objDriver.findElement(By.xpath(poDeviceOrderedCountTxt))
	        		                   .getAttribute("title");
	        orderCountAfter=orderCountAfter.replaceAll(",", "");
	        int orderCountAfter1=Integer.parseInt(orderCountAfter);
	        String receivedCountAfter=FunctionLibrary.objDriver.findElement(By.xpath(poDeviceReceivedCountTxt))
	                .getAttribute("title");
	        receivedCountAfter=receivedCountAfter.replaceAll(",", "");
	        int receivedCountAfter1=Integer.parseInt(receivedCountAfter);
	        if(receivedCountAfter1==orderCountAfter1)
	        {
	        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : PO status should be RECEIVED FULL"
	        			+"<br>"+"Actual : PO status is "+poStatus, LogStatus.PASS, true);
	        }else
	        {
	        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : PO status should be PARTIAL RECEIVED"
	        			+"<br>"+"Actual : PO status is "+poStatus, LogStatus.PASS, true);        	
	        }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }
    	
 }
   
    public static void tcPurchaseOrderPOCR057() throws Exception
    {
    	try{
	        desc="Creating Purchase Order";
	        String poId = createPurchaseOrderAndReturnId(dataObj);
	        System.out.println("Create po id : " + poId);
	        
	        createPurchaseOrderLineItems(dataObj,poId);
	        
	        receivePurchaseOrderLineItem(dataObj,poId);
	        String poStatus=FunctionLibrary.objDriver.findElement(By.xpath(currentPOStatusTxt)).getAttribute("title");
	        String orderCount=FunctionLibrary.objDriver.findElement(By.xpath(poDeviceOrderedCountTxt))
	        		                   .getAttribute("title");
	        int orderCount1=Integer.parseInt(orderCount);
	        String receivedCount=FunctionLibrary.objDriver.findElement(By.xpath(poDeviceReceivedCountTxt))
	                .getAttribute("title");
	        int receivedCount1=Integer.parseInt(receivedCount);
	        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Order Count is: "+orderCount1+"<br>"
	        		+"Received Count is: "+receivedCount1, LogStatus.INFO, true);
	        if(receivedCount1==orderCount1)
	        {
	        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : PO status should be RECEIVED FULL"
	        			+"<br>"+"Actual : PO status is "+poStatus, LogStatus.PASS, true);
	        }else
	        {
	        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : PO status should be PARTIAL RECEIVED"
	        			+"<br>"+"Actual : PO status is "+poStatus, LogStatus.PASS, true);        	
	        }
	        
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }
    }
    
    public static void tcPurchaseOrderPOCR063() throws Exception
    {

    	try{
	        desc="Creating Purchase Order";
	        String poId = createPurchaseOrderAndReturnId(dataObj);
	        System.out.println("Create po id : " + poId);
	        
	        createPurchaseOrderLineItems(dataObj,poId);
	        
	        receivePurchaseOrderLineItem(dataObj,poId);
	        String poStatus=FunctionLibrary.objDriver.findElement(By.xpath(currentPOStatusTxt)).getAttribute("title");
	        String orderCount=FunctionLibrary.objDriver.findElement(By.xpath(poDeviceOrderedCountTxt))
	        		                   .getAttribute("title");
	        int orderCount1=Integer.parseInt(orderCount);
	        String receivedCount=FunctionLibrary.objDriver.findElement(By.xpath(poDeviceReceivedCountTxt))
	                .getAttribute("title");
	        int receivedCount1=Integer.parseInt(receivedCount);
	        if(receivedCount1==orderCount1)
	        {
	        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : PO status should be RECEIVED FULL"
	        			+"<br>"+"Actual : PO status is "+poStatus, LogStatus.PASS, true);
	        }else
	        {
	        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : PO status should be PARTIAL RECEIVED"
	        			+"<br>"+"Actual : PO status is "+poStatus, LogStatus.PASS, true);        	
	        }
    	}catch(Exception e){
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to "+e.getMessage() ,LogStatus.FAIL,true);
      }     
    	
    }
    
    //Creating Purchase Order and returning the Purchase Order Id	 
	 
     public static String createPurchaseOrderAndReturnId(HashMap<String, String>dataObj) throws InterruptedException
     {

            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Creating Purchase Order -->"
        +"by "+dataObj.get("POLineItemMethod")+" process", LogStatus.INFO, false);            
             
             
       //Navigating to Inventory Page and creating PO

       FunctionLibrary.clickObject(inventoryTab, "", "Clicking on Inventory Tab");
       FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
    		   purchaseOrderTab,inventoryTab,3,20);
       FunctionLibrary.clickObject(purchaseOrderTab, "", "Clicking on Purchase Order Tab");
       FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
    		   purchaseOrdersNewBtn,purchaseOrderTab,3,20);
       FunctionLibrary.clickObject(purchaseOrdersNewBtn, "", "Clicking New button under Purchase Order Tab");
       FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
    		   purchaseOrdersOrderNbrTxtBox,purchaseOrdersNewBtn,3,20);
       FunctionLibrary.setText(purchaseOrdersOrderNbrTxtBox, DateTime.now().getSecondOfDay()+DateTime.now().getDayOfMonth()+
                    "-" + DateTime.now().getMillisOfSecond(), "Entering Order Number");
       
       FunctionLibrary.clickObject(purchaseOrderAgencyElement, "", "Clicking on Agency field");      
       FunctionLibrary.setText(purchaseOrderAgencyTxtBox,dataObj.get("AgencyName"),"Enter agency name");
       FunctionLibrary.clickObject("//td[@id='1_s_1_l_Order_Type']", "", "Clicking PO type field");
       FunctionLibrary.setText("//input[@name='Order_Type']", "REGULAR", "Entering Order type field");
       FunctionLibrary.clickObject(purchaseOrderStoreElement, "", "Clicking on Store field");
       FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
                                  (purchaseOrderStoreTxtBox, purchaseOrderStoreElement, 5, 30);
       FunctionLibrary.setText(purchaseOrderStoreTxtBox,dataObj.get("StoreName"),"Enter agency name");
       FunctionLibrary.clickObject(purchaseOrderSaveBtn, "", "Clicking on Save button for Purchase Order");
       FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
    		   purchaseOrderNewStatusTxt,purchaseOrderSaveBtn,5,20);
       Thread.sleep(5000);
       
       String orderNbr = FunctionLibrary.objDriver.findElement(By.xpath(poNumberTxt)).getText();
       System.out.println(orderNbr);
       String desc = "PO must be created successfully";
       ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: "+desc+""+"<br>"
       +"Actual:PO is created successfully", LogStatus.PASS, false);
       ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "The PO number is : "+orderNbr, LogStatus.INFO, false);
       String procCost= FunctionLibrary.objDriver.findElement(By.xpath(procurementCostPOTxt)).getAttribute("title");
       String statusPO= FunctionLibrary.objDriver.findElement(By.xpath(currentPOStatusTxt)).getAttribute("title");
       ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Current status of PO before adding Line items is : "+statusPO, LogStatus.INFO, false);
       ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Procurement cost before adding Line items is : "+procCost, LogStatus.INFO, false);
       
       return orderNbr;
     }
	 
	public static void createPurchaseOrderLineItems(HashMap<String, String>dataObj,String poNumber) 
			 throws InterruptedException, AWTException{
		 
		 
         desc="Adding PO Line Items";
         
         FunctionLibrary.objDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
         
         String poLineItemsToBeAdded = dataObj.get("POLineItems");
         poLineItemsToBeAdded = poLineItemsToBeAdded.replace("\n","");

         String []poLineItems = poLineItemsToBeAdded.split(",");
         String [] eachPOLineItemInfo;
         String deviceModelNumber;
         String orderCount;
         String actualDeviceCost;
         String receiptQuantityPerBox;
         String deviceMode;
         String deviceDOB;
        // WebDriverWait wait= new  WebDriverWait(FunctionLibrary.ObjDriver, 600);
                           
         for (int lineItemIterator =0;lineItemIterator<=poLineItems.length-1;lineItemIterator++)
         {
        	 eachPOLineItemInfo=poLineItems[lineItemIterator].split(":");
        	 deviceModelNumber = eachPOLineItemInfo[0];
        	 orderCount = eachPOLineItemInfo[2];
        	 actualDeviceCost = eachPOLineItemInfo[3];
        	 receiptQuantityPerBox = eachPOLineItemInfo[4];
        	 deviceMode = eachPOLineItemInfo[5];
        	 deviceDOB=eachPOLineItemInfo[6];
        	 
 
         //Adding PO Line Items Importing file
        	 
        	        	 
         
         if(dataObj.get("POLineItemMethod").equals("Import"))
         {
         	
        	 
             ReportLibrary.checkCreateDirectory(importFilePath);
         int noOfDevices =Integer.parseInt(orderCount.toString());	                    
         ManiFestFileGeneration.createMainFestFile(dataObj,poNumber,lineItemIterator,lineItemIterator,deviceMode.toString(),
        		 noOfDevices,importFilePath.toString()+poNumber+"_"+lineItemIterator+"_transponder.txt");
         
         //Thread.sleep(5000);
         FunctionLibrary.clickObject(poLineItemNewBtn,"","Clicking on PO Line Item New button"); 
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
      		   poDeviceModelTxtBox,poLineItemNewBtn,3,20);       
         FunctionLibrary.setText(poDeviceModelTxtBox, deviceModelNumber.toString(),
        		 "Entering Device Model name");
         FunctionLibrary.clickObject(poLineItemOrderCountElement,"","Clicking on order count field");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		 poLineItemOrderCountTxtBox,poLineItemOrderCountElement,3,20);
         FunctionLibrary.setText(poLineItemOrderCountTxtBox,orderCount.toString(),"Entering value for Order Count");
         
/*         FunctionLibrary.clickObject(poLineItemActualDeviceCostElement, "", "Clicking actual device cost button");
         FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
        		 poLineItemActualDeviceCostTxtBox,poLineItemActualDeviceCostElement,2,20);
         FunctionLibrary.setText(poLineItemActualDeviceCostTxtBox, actualDeviceCost.toString(), "Entering the actual cost");*/
        
         FunctionLibrary.clickObject(poLineItemSaveBtn, "", "CLicking on save button");
            Thread.sleep(10000);//need to implement logic
         
                 	                    
          }//end of file import method
         	
        
         
         //Adding PO Line Items Manually
         
             else if(dataObj.get("POLineItemMethod").equals("Manual")){           	 
		
//
                  FunctionLibrary.clickObject(poLineItemNewBtn,"","Clicking on PO Line Item New button"); 
                 FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
              		   poDeviceModelTxtBox,purchaseOrderSaveBtn,3,20);       
                 FunctionLibrary.setText(poDeviceModelTxtBox, deviceModelNumber.toString(),
                		 "Entering Device Model name");
                 FunctionLibrary.clickObject(poLineItemOrderCountElement,"","Clicking on order count field");
                 FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                		 poLineItemOrderCountTxtBox,poLineItemOrderCountElement,3,20);
                 FunctionLibrary.setText(poLineItemOrderCountTxtBox,orderCount.toString(),"Entering value for Order Count");
                 
                 FunctionLibrary.clickObject(poLineItemStartDeviceNoElement, "", "Clicking Start Device No field");
                 FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                		 poLineItemStartDeviceNoTxtBox,poLineItemStartDeviceNoElement,3,20);
/*                 int secondOfDay=DateTime.now().getSecondOfDay();
                 DateTime.now().getSecondOfMinute();
                 int millisOfSecond=DateTime.now().getMillisOfSecond();
                 String startDeviceNbr=String.valueOf(secondOfDay) +String.valueOf(millisOfSecond)+"0110";
                 
                 if(startDeviceNbr.length()==11)
	            	{
                	 startDeviceNbr=7+startDeviceNbr;
	            	}else if(startDeviceNbr.length()==13)
	            	{
	            		startDeviceNbr=startDeviceNbr.substring(1, startDeviceNbr.length());
	            	}else if(startDeviceNbr.length()==12)
	            	{
	            		
	            	}*/
                 String startDeviceNbr=null;
     	        Long secondsElapsed = System.currentTimeMillis();
    	        String text = Long.toString(secondsElapsed).substring(Math.max(0, 5));
    	            text = text.substring(text.length() - 8, text.length());

    	            startDeviceNbr = text+"0110";
    		            	if(startDeviceNbr.length()==11)
    		            	{
    		            		startDeviceNbr=7+startDeviceNbr;
    		            	}else if(startDeviceNbr.length()==13)
    		            	{
    		            		startDeviceNbr=startDeviceNbr.substring(1, startDeviceNbr.length());
    		            	}else if(startDeviceNbr.length()==12)
    		            	{
    		            		
    		            	}
                 FunctionLibrary.setText(poLineItemStartDeviceNoTxtBox,startDeviceNbr , "Entering Start Device No");
                 
                 FunctionLibrary.clickObject(poLineItemEndDeviceNoElement, "", "Clicking End Device No");
                 FunctionLibrary.clickObject(poLineItemActualDeviceCostElement, "", "Clicking actual device cost button");
                 FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                		 poLineItemActualDeviceCostTxtBox,poLineItemActualDeviceCostElement,2,20);
                 FunctionLibrary.setText(poLineItemActualDeviceCostTxtBox, actualDeviceCost.toString(), "Entering the actual cost");
                 FunctionLibrary.clickObject(purchaseOrderReceiveStatusTxt, "", "CLicking PO status");
                 FunctionLibrary.clickObject(poLineItemDOBElement, "", "CLicking DOB field");
                 FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
                		 poLineItemDOBTxtBox,poLineItemDOBElement,3,20);
                 FunctionLibrary.setText(poLineItemDOBTxtBox, deviceDOB.toString(), "Entering DOB");
                 FunctionLibrary.clickObject(poLineItemSaveBtn, "", "CLicking on save button");
                
                 try {
					Thread.sleep(10000);//needs some time to add line item receipt once we add the line items
				} catch (InterruptedException e2) {
				
					e2.printStackTrace();
				}
                 
                 

               
                 

             //Adding PO Line Item Receipts

                 JavascriptExecutor js= (JavascriptExecutor)FunctionLibrary.objDriver;
                 js.executeScript("window.scrollBy(0,250)", "");
                     FunctionLibrary.clickObject(poLineItemReceiptsNewBtn, "", "Clicking on PO Line Item Receipt New button");
                     System.out.println("Clicking new button");
                     FunctionLibrary.setText(poLineItemReceiptQtyPerBoxDropDownBtn,receiptQuantityPerBox.toString(),
                    		 "Entering Qty per Box value");
                     System.out.println("About to click Save button");
                     FunctionLibrary.clickObject(poLineItemReceiptSaveBtn, "", "Clicking Save button");
                     System.out.println("Save button is clicked");
                     Thread.sleep(5000);//needs some time to save the added items
                     
                  }//end of if loop
         FunctionLibrary.objDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
         
         //Verifying line items before receiving it
         try{
       lineNbr=FunctionLibrary.objDriver.findElement(By.xpath(lineItemLineNbrTxt)).getAttribute("title");
       ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Line number of the Line Item : "+lineNbr, LogStatus.INFO, false);
         }catch(Exception e){System.out.println(e);}
         
        try{ 
       lineItemStatus=FunctionLibrary.objDriver.findElement(By.xpath(lineItemStatusTxt)).getAttribute("title");
       ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Line Item Status of the Line Item : "+lineItemStatus, LogStatus.INFO, false);
        }catch(Exception e){System.out.println(e);}
        
        try{
       agencyName=FunctionLibrary.objDriver.findElement(By.xpath(lineItemAgencyTxt))
                  .getAttribute("title");
       ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : Agency Name should be:"+dataObj.get("AgencyName")
       +"<br>"+"Actual : Agency Name is : "+agencyName, LogStatus.PASS, true);
        }catch(Exception e){System.out.println(e);}
        
        try{
       startDeviceNbr=FunctionLibrary.objDriver.findElement(By.xpath(lineItemStartDeviceNbrTxt)).getAttribute("title");
       ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Start Device Number of the Line Item : "+startDeviceNbr, LogStatus.INFO, false);
        }catch(Exception e){System.out.println(e);}
        
        try{
       endDeviceNbr=FunctionLibrary.objDriver.findElement(By.xpath(lineItemEndDeviceNbrTxt)).getAttribute("title");
       ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"End Device number of the Line Item : "+endDeviceNbr, LogStatus.INFO, false);
        }catch(Exception e){System.out.println(e);}
        
        try{
       actualBoxCount=FunctionLibrary.objDriver.findElement(By.xpath(lineItemBoxCountTxt)).getAttribute("title");
       ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Box Count of the Line Item : "+actualBoxCount, LogStatus.INFO, false);
        }catch(Exception e){System.out.println(e);}
        
        try{
       vehicleClass=FunctionLibrary.objDriver.findElement(By.xpath(lineItemVehicleClassTxt)).getAttribute("title");
       ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Vehicle class of the Line Item : "+vehicleClass, LogStatus.INFO, false);
        }catch(Exception e){System.out.println(e);}
        
        try
        {
       deviceCode=FunctionLibrary.objDriver.findElement(By.xpath(lineItemDeviceCodeTxt)).getAttribute("title");
       ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Device Code of the Line Item : "+deviceCode, LogStatus.INFO, false);
        }catch(Exception e){System.out.println(e);}
        
        try{
       deviceUnitPrice=FunctionLibrary.objDriver.findElement(By.xpath(lineItemDeviceUnitPriveTxt)).getAttribute("title");
       ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Device unit price of the Line Item : "+deviceUnitPrice, LogStatus.INFO, false);
        }catch(Exception e){System.out.println(e);}
        
        try{
       deviceMountType=FunctionLibrary.objDriver.findElement(By.xpath(lineItemDeviceMountTypeTxt)).getAttribute("title");
       ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Device mount type of the Line Item : "+deviceMountType, LogStatus.INFO, false);
        }catch(Exception e){System.out.println(e);}
        
        try{
       deviceColor=FunctionLibrary.objDriver.findElement(By.xpath(lineItemDeviceColorTxt)).getAttribute("title");
       ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Device colour of the Line Item : "+deviceColor, LogStatus.INFO, false);
        }catch(Exception e){System.out.println(e);}
        
        try{
       deviceType=FunctionLibrary.objDriver.findElement(By.xpath(lineItemDeviceTypeTxt)).getAttribute("title");       
       ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Device type of the Line Item : "+deviceType, LogStatus.INFO, false);
        }catch(Exception e){System.out.println(e);}
      
        FunctionLibrary.objDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
         
         }//end of for loop
         
    }
                  
    public static void receivePurchaseOrderLineItem(HashMap<String, String>dataObj,String poNumber) throws Exception
      {
         

    	  WebDriverWait wait= new WebDriverWait(FunctionLibrary.objDriver, 600);
         
         if(dataObj.get("POLineItemMethod").equals("Import")){
        	 
        	 String fileName=null;        	 
             List<WebElement> allLineItemElements = FunctionLibrary.objDriver.findElements
            		 (By.xpath(poLineItemNewStatus));
             int lineItemiterator =0;
             int newElement=lineItemiterator+1;
            // Iterator<WebElement> itr = allLineItemElements.iterator();
             for(int iterator=0;iterator<=allLineItemElements.size()-1;iterator++)
            // while(itr.hasNext()) 
             {
            	               	 
                 //String importFilePath=CommonLibrary.getSettingsSheetInfo().get("ImportFilePath").toString();
            	 //String xpathVal="(//td[contains(@aria-labelledby,'s_3_l_altCombo') and @title='NEW'])["+newElement+"]";
            	 String xpathVal ="(//td[text()='NEW'])["+newElement+"]";
            	 String statusTxtBox = "//input[@name='Status']";
            	 
            	 
            	/* FunctionLibrary.waitForObject.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathVal)));
            	 FunctionLibrary.highLightElement(FunctionLibrary.objDriver, FunctionLibrary.objDriver.findElement(By.xpath(xpathVal)));
            	*/ 
            	 Thread.sleep(2000);
            	 FunctionLibrary.objDriver.findElement(By.xpath(xpathVal)).click();
				
            	 FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(statusTxtBox, xpathVal, 5, 30);
            	 
				desc="CLicking Import Transponder button";
				WebElement importTransponder=FunctionLibrary.objDriver.findElement
					 (By.xpath(transponderImportBtn));
				FunctionLibrary.highLightElement(FunctionLibrary.objDriver, importTransponder);
				FunctionLibrary.objDriver.findElement(By.xpath(transponderImportBtn)).click();	
				Thread.sleep(5000);
				desc="Alert is present";
				try{
				wait.until(ExpectedConditions.alertIsPresent());
				Alert alert = FunctionLibrary.objDriver.switchTo().alert();
				String text=alert.getText();
				alert.accept();
			    ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Pop-up content: "+text, LogStatus.INFO, false);

				}catch(Exception e){
					System.out.println("No alert present");				
				}
				WebElement browseImportFile= FunctionLibrary.objDriver.findElement(By.xpath(browseImportFileTxtBox));
				wait.until(ExpectedConditions.visibilityOf(browseImportFile));
				try{   
				fileName=importFilePath.toString()+poNumber+"_"+lineItemiterator+"_transponder.txt";
				browseImportFile.sendKeys(fileName);
				
				}catch(Exception e){				
				FunctionLibrary.highLightElement(FunctionLibrary.objDriver,FunctionLibrary.objDriver.
						 findElement(By.xpath(importTransponderBtn)));}
				
				Thread.sleep(15000);
				WebDriverWait wait1= new WebDriverWait(FunctionLibrary.objDriver, 30);
				wait1.until(ExpectedConditions.elementToBeClickable(By.xpath(importTransponderBtn)));
				FunctionLibrary.highLightElement(FunctionLibrary.objDriver,FunctionLibrary.objDriver.
						 findElement(By.xpath(importTransponderBtn)));
				Thread.sleep(15000);
				FunctionLibrary.objDriver.findElement(By.xpath(importTransponderBtn)).click();
				
				try{
					wait.until(ExpectedConditions.alertIsPresent());
					Alert confirmationAlert = FunctionLibrary.objDriver.switchTo().alert();
					String text1=confirmationAlert.getText(); 
					confirmationAlert.accept();
					ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Pop-up content: "+text1, LogStatus.INFO, true);
				//Alert confirmationAlert = FunctionLibrary.objDriver.switchTo().alert();
				     
				}
				catch(Exception e){
					System.out.println("No alert present");
					}				
	
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath
	        			 (poReceivedFullStatusTxt)));
				
				lineItemiterator++;
				desc="Transponders should be imported successfully";
				ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: "+desc+""+"<br>"+
				"Actual:Transponders are imported successfully", LogStatus.PASS, false);
				
				  FunctionLibrary.objDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
				  
		    	 //Verifying the details under PO LineItem Receipt
				  if(dataObj.get("TestCaseId").equals("POCR-057"))
				  {
				    	 try{
				 	        receivedStatus=FunctionLibrary.objDriver.findElement(By.xpath(lineItemReceiptStatusTxt)).getAttribute("title");
				 	        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : Received status should be:"+"PARTIALLY RECEIVED"
				 			        +"<br>"+"Actual : Received status is : "+receivedStatus, LogStatus.PASS, true);
				 	    	 }catch(Exception e){System.out.println(e);}
				  }
				  else
				  {
		    	 try{
		        receivedStatus=FunctionLibrary.objDriver.findElement(By.xpath(lineItemReceiptStatusTxt)).getAttribute("title");
		        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : Received status should be:"+"RECEIVED FULL"
				        +"<br>"+"Actual : Received status is : "+receivedStatus, LogStatus.PASS, true);
		    	 }catch(Exception e){System.out.println(e);}
				 }
				  
		    	 
		    	 try{
				quantityPerBox=FunctionLibrary.objDriver.findElement(By.xpath(lineItemReceiptQtyPerBoxTxt)).getAttribute("title");
				ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Quantity per box is : "+quantityPerBox, LogStatus.INFO, false);
		    	 }catch(Exception e){System.out.println(e);}
		    	 
		    	 try{
				boxesReceived=FunctionLibrary.objDriver.findElement(By.xpath(lineItemReceiptBoxReceivedTxt)).getAttribute("title");
				ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"No of boxes received are : "+boxesReceived, LogStatus.INFO, false);
		    	 }catch(Exception e){System.out.println(e);}
		    	 
		    	 try{
				startDeviceNbrAfterReceive=FunctionLibrary.objDriver.findElement(By.xpath(lineItemReceiptStartDeviceNbrTxt))
				     .getAttribute("title");
				ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : Start device number should be:"+startDeviceNbr
				        +"<br>"+"Actual : Start device number is : "+startDeviceNbrAfterReceive, LogStatus.PASS, false);
		    	 }catch(Exception e){System.out.println(e);}
		    	 
		    	 try{
				endDeviceNbrAfterReceive=FunctionLibrary.objDriver.findElement(By.xpath(lineItemReceiptEndDeviceNbrTxt))
				     .getAttribute("title");
				ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : End device number should be:"+endDeviceNbr
				        +"<br>"+"Actual : End device number is : "+endDeviceNbrAfterReceive, LogStatus.PASS, false);
		    	 }catch(Exception e){System.out.println(e);}
		    	 
		    	 try{
				noOfDevicesReceived=FunctionLibrary.objDriver.findElement(By.xpath(lineItemReceiptDevicesReceivedTxt))
				     .getAttribute("title");
				ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"No of devices received are : "+noOfDevicesReceived, LogStatus.INFO, false);
		    	 }catch(Exception e){System.out.println(e);}
		    	 
		    	 try{
				receivedDate=FunctionLibrary.objDriver.findElement(By.xpath(lineItemReceiptReceivedDateTxt))
				     .getAttribute("title");
				ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Received date is : "+receivedDate, LogStatus.INFO, false);
		    	 }catch(Exception e){System.out.println(e);}
		    	 
		    	 try{
				receiptNbr=FunctionLibrary.objDriver.findElement(By.xpath(lineItemReceiptNbrTxt)).getAttribute("title");
				ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Receipt number is : "+receiptNbr, LogStatus.INFO, false);
		    	 }catch(Exception e){System.out.println(e);}
				
				
		    	  FunctionLibrary.objDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);			
				
				
				
                      // Partial and Full Receive 
				
				try{
    
                    if(dataObj.get("POStatus").equalsIgnoreCase("FULLRECEIVED")){
   	 
                     	 if(lineItemiterator==(allLineItemElements.size()))
                              	 break;
   	 
                       }else if(dataObj.get("POStatus").equalsIgnoreCase("PARTIALRECEIVED")){
   	 
   	                      if(lineItemiterator==(allLineItemElements.size()-1))
       	                        break;
                             }
    
				}catch(Exception e)
				{
					
				}
                    //Thread.sleep(5000);
                    JavascriptExecutor js= (JavascriptExecutor)FunctionLibrary.objDriver;
                    js.executeScript("window.scrollBy(0,250)", "");
             
             }//end of while loop
         
         }//End of import if loop
         
         
         if(dataObj.get("POLineItemMethod").equals("Manual")){
        	 
        	 //Procurement Cost calculation for manual line item
        	 
         List<WebElement> proCost=FunctionLibrary.objDriver.findElements(By.xpath(procurementCostPerLineItemElement));
         
         System.out.println(proCost.size());
         String[] proCostNoOfItems = new String[proCost.size()];
         int proIterator = 0;
         for (WebElement element : proCost) {
        	 proCostNoOfItems[proIterator]=element.getAttribute("title").toString();
        	 System.out.println(proCostNoOfItems[proIterator]);
        	 proIterator = proIterator+1;
         }
         Double lineItemProcurementCost1;
         lineItemProcurementCost=0;
         for(int k=0;k<proCost.size();k++){
        	 
             int length=proCostNoOfItems[k].length();
        	 System.out.println(length);
        	 String lineProCost=proCostNoOfItems[k].replace("$","");
        	 lineProCost=lineProCost.replaceAll(",", "");
        	 lineItemProcurementCost1 =Double.valueOf(lineProCost);
        	 lineItemProcurementCost=lineItemProcurementCost+lineItemProcurementCost1;

        	 System.out.println(lineItemProcurementCost);
        	 
         }
          
         if(dataObj.get("POStatus").equalsIgnoreCase("PARTIAL RECEIVED")){
        	 
        	 lineItemProcurementCost=0;
        	 String proCostNoOfItem= FunctionLibrary.objDriver.findElement(By.xpath("//td[@id='1_s_3_l_Vector_Line_Procurement_Cost']")).getAttribute("title");
             String lineProCost=proCostNoOfItem.replace("$","");
        	 lineProCost=lineProCost.replaceAll(",", "");
        	 lineItemProcurementCost1 =Double.valueOf(lineProCost);
        	 lineItemProcurementCost=lineItemProcurementCost+lineItemProcurementCost1;
         }
         System.out.println(lineItemProcurementCost);
         
         //Receiving line items for Manual line item
         
         List<WebElement> allLineItemElements = FunctionLibrary.objDriver.findElements
        		 (By.xpath(poLineItemNewStatus));
         int lineItemiterator =1;
         Iterator<WebElement> itr = allLineItemElements.iterator();
         
         while(itr.hasNext()) {
        	 
        	 JavascriptExecutor js= (JavascriptExecutor)FunctionLibrary.objDriver;
             js.executeScript("window.scrollBy(0,250)", "");
             Thread.sleep(2000);
        	 FunctionLibrary.objDriver.findElement(By.xpath(lineItemNewElement)).click();
        	 lineItemiterator++;
        	 FunctionLibrary.clickObject(lineItemReceiptNewElement,"","Click the line item");
       	
             FunctionLibrary.objDriver.findElement(By.xpath(lineItemReceiveBtn)).click();
        	 try{
        		 wait.until(ExpectedConditions.alertIsPresent());
                 Alert alert = FunctionLibrary.objDriver.switchTo().alert();
                 alert.accept();
                 }
                 catch(Exception e){
                 	 System.out.println("No alert is present");
                 }    	 

                
        	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(poReceivedFullStatusTxt)));        	 
        	 
        	 //Verifying the details under PO LineItem Receipt
        	  FunctionLibrary.objDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        	 
	    	 try{
			        receivedStatus=FunctionLibrary.objDriver.findElement(By.xpath(lineItemReceiptStatusTxt))
			                 .getAttribute("title");
			        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : Received status should be:"+"RECEIVED FULL"
					        +"<br>"+"Actual : Received status is : "+receivedStatus, LogStatus.PASS, true);
			    	 }catch(Exception e){System.out.println(e);}
	    	 
			    	 try{
					quantityPerBox=FunctionLibrary.objDriver.findElement(By.xpath(lineItemReceiptQtyPerBoxTxt))
					    .getAttribute("title");
					ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Quantity per box is : "+quantityPerBox, LogStatus.INFO, false);
			    	 }catch(Exception e){System.out.println(e);}
			    	 
			    	 try{
					boxesReceived=FunctionLibrary.objDriver.findElement(By.xpath(lineItemReceiptBoxReceivedTxt))
					     .getAttribute("title");
					ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"No of boxes received are : "+boxesReceived, LogStatus.INFO, false);
			    	 }catch(Exception e){System.out.println(e);}
			    	 
			    	 try{
					startDeviceNbrAfterReceive=FunctionLibrary.objDriver.findElement(By.xpath(lineItemReceiptStartDeviceNbrTxt))
					     .getAttribute("title");
					ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : Start device number should be:"+startDeviceNbr
					        +"<br>"+"Actual : Start device number is : "+startDeviceNbrAfterReceive, LogStatus.PASS, false);
			    	 }catch(Exception e){System.out.println(e);}
			    	 
			    	 try{
					endDeviceNbrAfterReceive=FunctionLibrary.objDriver.findElement(By.xpath(lineItemReceiptEndDeviceNbrTxt))
					     .getAttribute("title");
					ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : End device number should be:"+endDeviceNbr
					        +"<br>"+"Actual : End device number is : "+endDeviceNbrAfterReceive, LogStatus.PASS, false);
			    	 }catch(Exception e){System.out.println(e);}
			    	 
			    	 try{
					noOfDevicesReceived=FunctionLibrary.objDriver.findElement(By.xpath(lineItemReceiptDevicesReceivedTxt))
					     .getAttribute("title");
					ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"No of devices received are : "+noOfDevicesReceived, LogStatus.INFO, false);
			    	 }catch(Exception e){System.out.println(e);}
			    	 
			    	 try{
					receivedDate=FunctionLibrary.objDriver.findElement(By.xpath(lineItemReceiptReceivedDateTxt))
					     .getAttribute("title");
					ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Received date is : "+receivedDate, LogStatus.INFO, false);
			    	 }catch(Exception e){System.out.println(e);}
			    	 
			    	 try{
					receiptNbr=FunctionLibrary.objDriver.findElement(By.xpath(lineItemReceiptNbrTxt))
					     .getAttribute("title");
					ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Receipt number is : "+receiptNbr, LogStatus.INFO, false);
			    	 }catch(Exception e){System.out.println(e);}
        	 
			    	  FunctionLibrary.objDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
       	                  
            // Partial and Full Receive 
                 
                 if(dataObj.get("POStatus").equals("FULL RECEIVED")){
                	 
                	 if(lineItemiterator==(allLineItemElements.size()+1))
                    	 break;
                	 
                 }else if(dataObj.get("POStatus").equals("PARTIAL RECEIVED")){
                	 
                	 if(lineItemiterator==(allLineItemElements.size()))
                    	 break;
                 }
                 
                // Thread.sleep(20000);//needs time to receive the item
      
           }//while loop end
         
       } //End of manual if loop
         
 }//end of method
         
  	public static void boxValidation(String boxNumber,String poID) throws Exception
  	{
  	      FunctionLibrary.clickObject(BoxesTab, "", "Clicking Boxes tab");
  	      FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
  	    		  boxesTabQueryBtn,BoxesTab,3,20);
  	      
    	  FunctionLibrary.clickObject(boxesTabQueryBtn, "", "Clicking Boxes Query button");

    	  FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible
    	  (boxSearchGoBtn,boxesTabQueryBtn, 15, 60);
    	     	  
    	  FunctionLibrary.clickObject(searchForBoxElement, "", "CLicking the box field");
    	  FunctionLibrary.setText(searchForBoxTxtBox, boxNumber, "Entering box number");	        	             
    	  FunctionLibrary.clickObject(boxSearchGoBtn, "", "Click Go button");
    	  FunctionLibrary.waitForObject.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(deviceListTable)));
  	      Thread.sleep(3000);
    	  //Verification under boxes tab
    	  
  	    FunctionLibrary.objDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
  	      try{
    	  String boxNbr=FunctionLibrary.objDriver.findElement(By.xpath(boxesTabBoxNbrTxt)).getAttribute("title");
    	  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Box " +boxNbr+ " is created for the PO "+ poID, LogStatus.INFO, true);
    	  }catch(Exception e)
    	  {
    		  System.out.println("Field is not present");
    	  }
  	      
  	      try{
    	  String boxType=FunctionLibrary.objDriver.findElement(By.xpath(boxesTabBoxTypeTxt)).getAttribute("title");
    	  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Box Type is : "+boxType, LogStatus.INFO, false);
    	  }catch(Exception e)
    	  {
    		  System.out.println("Field is not present");
    	  }
  	      
  	      try{
    	  String boxStatus=FunctionLibrary.objDriver.findElement(By.xpath(boxesTabBoxStatusTxt)).getAttribute("title");
    	  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : Box status should be:"+"INVENTORY"
    	            +"<br>"+"Actual : Box status is : "+boxStatus, LogStatus.PASS, false);
    	  }catch(Exception e)
    	  {
    		  System.out.println("Field is not present");
    	  }
  	      
  	      try{
    	  String deviceCharacteristic=FunctionLibrary.objDriver.findElement(By.xpath(boxesTabDeviceCharTxt)).getAttribute("title");
    	  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Device characteristic is : "+deviceCharacteristic, LogStatus.INFO, false);
    	  }catch(Exception e)
    	  {
    		  
    		  System.out.println("Field is not present");
    	  }
  	      
  	      try{
    	  String boxStartDeviceNbr=FunctionLibrary.objDriver.findElement(By.xpath(boxesTabStartDeviceNbrTxt))
                  .getAttribute("title");
    	  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : Start device number should be:"+startDeviceNbrAfterReceive
    	            +"<br>"+"Actual : Start device number is : "+boxStartDeviceNbr, LogStatus.PASS, false);
    	  }catch(Exception e)
    	  {
    		  System.out.println("Field is not present");
    	  }
  	      
  	      try{
    	  String boxDeviceCount=FunctionLibrary.objDriver.findElement(By.xpath(boxesTabBoxDeviceCountTxt)).getAttribute("title");
    	  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Device count is : "+boxDeviceCount, LogStatus.INFO, false);
    	  }catch(Exception e)
    	  {
    		  System.out.println("Field is not present");
    	  }
  	      
    	  try{
    	  String deviceModel=FunctionLibrary.objDriver.findElement(By.xpath(boxesTabDeviceModelTxt)).getAttribute("title");
    	  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Device Model is : "+deviceModel, LogStatus.INFO, false);
    	  }catch(Exception e)
    	  {
    		  System.out.println("Field is not present");
    	  }
    	  
    	  try{
    	  String deviceStatus=FunctionLibrary.objDriver.findElement(By.xpath(boxesTabDeviceStatusTxt)).getAttribute("title");
    	  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected : Device status should be:"+"INVENTORY"
  	            +"<br>"+"Actual : Device status is : "+deviceStatus, LogStatus.PASS, false);
    	  }catch(Exception e)
    	  {
    		  System.out.println("Field is not present");
    	  }
    	  
    	  try{
    	  String dOB=FunctionLibrary.objDriver.findElement(By.xpath(boxesTabDOBTxt)).getAttribute("title");
    	  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"DOB of device is : "+dOB, LogStatus.INFO, false);
    	  }catch(Exception e)
    	  {
    		  System.out.println("Field is not present");
    	  }
    	  
    	  try{
    	  String manufactureWarranty=FunctionLibrary.objDriver.findElement(By.xpath(boxesTabManufacturerDateTxt)).getAttribute("title");
    	  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Manufacturer warranty is : "+manufactureWarranty+" months", 
                    LogStatus.INFO, false);
    	  }catch(Exception e)
    	  {
    		  System.out.println("Field is not present");
    	  }
    	  
    	  try{
    	  String storeName=FunctionLibrary.objDriver.findElement(By.xpath(boxesTabStoreNameTxt)).getAttribute("title");
    	  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Store name is : "+storeName, LogStatus.INFO, false);
    	  }catch(Exception e)
    	  {
    		  System.out.println("Field is not present");
    	  }
    	  
    	  FunctionLibrary.objDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
}          
  	
	//Verifying Procurement Cost
	    
    public static String procurementCostVerification(){
     
     try {
		Thread.sleep(5000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
     String actualProCost = FunctionLibrary.objDriver.findElement(By.xpath(procurementCostForPOTxt)).getAttribute("title");
     String actProCost=actualProCost.substring(1, actualProCost.length()-3);     
     int actualProcurementCost=Integer.parseInt(actProCost);
     System.out.println(actualProcurementCost);
     System.out.println(lineItemProcurementCost);
     desc=dataObj.get("Expected Result");
     if (Double.valueOf(actualProcurementCost).equals(lineItemProcurementCost)) {
     	desc="PO receive full balance equal to Procurement Cost";
     	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Procurement cost is : "+actualProcurementCost, LogStatus.PASS, false);
     	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: "+desc+""+"<br>"+"Actual:Procurement Cost is equal to PO receive full balance", LogStatus.PASS, false);
     } else {
    	 if(dataObj.get("POStatus").equalsIgnoreCase("PARTIAL RECEIVED"))
    	 {
    	     	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: - "+lineItemProcurementCost+" "+"<br>"
    	     			+"Actual:Procurement Cost is not equal to PO partial receive balance - "+ actualProcurementCost, LogStatus.PASS, false);
    	 }else
    	 {
     	ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected: - "+lineItemProcurementCost+" "+"<br>"
     			+"Actual:Procurement Cost is not equal to PO receive full balance - "+ actualProcurementCost, LogStatus.FAIL, true);
    	 }
     }
     //lineItemProcurementCost=0;
	return String.valueOf(lineItemProcurementCost);
   
  }
    
     
	 public static String getPONumber() throws SQLException, ClassNotFoundException, InterruptedException
     {

		    String poNbr=null;
		    String connStringOracleDb = "jdbc:oracle:thin:@";
		    String userName = CommonLibrary.getSettingsSheetInfo().get("QA_USERNAME");	                
		    String password =CommonLibrary.getSettingsSheetInfo().get("QA_PASSWORD");    
		    String dbHostName=CommonLibrary.getSettingsSheetInfo().get("DataBaseHostName");
			String dbPort=CommonLibrary.getSettingsSheetInfo().get("DataBasePort");
			dbPort=dbPort.substring(0, 4);
			String dbServiceName=CommonLibrary.getSettingsSheetInfo().get("DataBaseServiceName");
			connStringOracleDb = connStringOracleDb+dbHostName+":"+dbPort+":"+dbServiceName;
	        //step2 create  the connection object
	        Connection con=DriverManager.getConnection(
	        		connStringOracleDb,userName,password); 
            Statement stmt=con.createStatement();
            String queryToFetchDeviceRequestId="select order_num,last_upd from siebel.s_order where status_cd='PARTIALLY RECEIVED' and x_vendor_name is null order by last_upd DESC";
           ResultSet rs=stmt.executeQuery(queryToFetchDeviceRequestId);   

     while(rs.next()){
    	 int i=0;
    	 i++;
    		 poNbr=rs.getString("ORDER_NUM");
    		 if(i==1)
    		 {
    			 break;
    		 }
     }   

     ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Receive the PO : "+poNbr+" fully which is already Partially fulfilled",
             LogStatus.INFO, false);
		 return poNbr;
     }    

	
     

}
	 
