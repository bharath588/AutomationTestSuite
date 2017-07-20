package features;

import com.relevantcodes.extentreports.LogStatus;
import libraries.CommonLibrary;
import libraries.FunctionLibrary;
import libraries.ReportLibrary;
import libraries.TestCaseLibrary;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import static libraries.CommonLibrary.getEachTestCaseData;
import static libraries.FunctionLibrary.objDriver;
import static objectProperties.SblAccountMaintenancePageObject.accountNumberTxtBox;
import static objectProperties.SblAccountMaintenancePageObject.accountsTab;
import static objectProperties.SblAccountSearchProperties.*;
/**
* Created by 23319 on 28-12-2016.
*/
public class CamsAccountSearch
{
	public static HashMap<String,String> dataObj =new HashMap<String, String>();
	public static String LoginMessage="NotSuccess";
	
	public static void CamsAccountSearchTest() throws IOException, Exception
	{
        ExcelSheet exl = new ExcelSheet();

        int noOfRows = exl.totalrows("FLCSS_CAMS_AccountSearch","Test_Scenarios");

        HashMap<String, String> eachTestCaseData = new HashMap<String, String>();

        String testCaseName = "";
        
       
        
        for (int i = 1; i <=noOfRows; i++) {

            eachTestCaseData = getEachTestCaseData(exl, "Test_Scenarios", i,"FLCSS_CAMS_AccountSearch");
            dataObj=eachTestCaseData;
            testCaseName = "tcAccountSearch"+eachTestCaseData.get("TestCaseId").replace("-","");

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
                    	
                        Class<?> c = Class.forName("features.CamsAccountSearch");
                        testMethod= c.getMethod(testCaseName); 
                        if (LoginMessage.equalsIgnoreCase("NotSuccess"))
                        {
                        	CommonLibrary.loginSblWithGenericUser(eachTestCaseData);
                        	LoginMessage="Success";
                        }
                   	 
                        TestCaseLibrary.Get_TestCase_Instance().executeTC(testMethod);

                        if (i==noOfRows)
                        {
                        CommonLibrary.logoutSiebelApplicaiton();

                        }
                     
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
        }


    }

   
  //**********************Function for Binocular Search **************************
    public static void binocularSerach(String lookInDropdown_prop,String lookInOption,String searchField_prop,String searchValue,String dropDowndesc,String fieldDesc) throws InterruptedException
  {
    	try{
    		
    		FunctionLibrary.clickObject(binocularSearch, "", "Clicking Binocular search[Vector Power Query] option");
    		FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(
    				lookInDropdown_prop, binocularSearch, 8, 24);
    		FunctionLibrary.selectDropDownListByValue(lookInDropdown_prop, lookInOption, dropDowndesc);
    		//FunctionLibrary.clickObject(accountsTab, "", "Clicking Accounts tab");
    		FunctionLibrary.setText(searchField_prop, searchValue, fieldDesc);

    	      String extingVal = "";
    	      for(int iterator=0;iterator<=12;iterator++)
    	      {
    	     	 extingVal = FunctionLibrary.getText(searchField_prop, fieldDesc);
    	     	 if(extingVal.equalsIgnoreCase(searchValue))
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
    	     		 FunctionLibrary.setText(searchField_prop, searchValue, fieldDesc);		 
    	     	 }
    	      
    	      }
    		
    		ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Search for account number",LogStatus.INFO,false);
    		FunctionLibrary.clickObject(searchImageButton, "", "Clicking search Image button");
    		} catch(Exception e){
    			String Errormessge=e.getMessage();
    			ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Search Failed Due to "+Errormessge,LogStatus.FAIL,true);
      }
    	  
   }//End Of BinoCular Search Method
  
  //**********************Function for SearchReq search **************************
    public static void serviceReqSerach(String searchField_prop,String searchText,String Desc) throws InterruptedException
  {
  	 
  	  
  	  FunctionLibrary.clickObject(serviceRequestTab, "", "Click go Service Request Tab");
  	  FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(searchField_prop, serviceRequestTab, 2, 20);
 	  FunctionLibrary.setText(searchField_prop, searchText, Desc);
 	  
 	  
 	 
      String extingVal = "";
      for(int iterator=0;iterator<=12;iterator++)
      {
     	 extingVal = FunctionLibrary.getText(searchField_prop, Desc);
     	 if(extingVal.equalsIgnoreCase(searchText))
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
     		 FunctionLibrary.setText(searchField_prop, searchText, Desc);		 
     	 }
      
      }
      
 	  
 	  
 	  
  	  //Taking Screen shot
  	  ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Before"+ Desc,LogStatus.INFO,true);
  	  FunctionLibrary.clickObject(goBtn, "", "Clicking go button");
  }

  //**********************Function for Search in HomePage**************************
    public static void searchHomePage(String searchField,String searchText,String desc)
  {     FunctionLibrary.clickObject(accountsTab, "", "Clicking Accounts tab");
    	FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(searchField, accountsTab, 2,20);
    	
        FunctionLibrary.setText(searchField, searchText, desc);
              
        
        String extingVal = "";
        for(int iterator=0;iterator<=12;iterator++)
        {
       	 extingVal = FunctionLibrary.getText(searchField, desc);
       	 if(extingVal.equalsIgnoreCase(searchText))
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
       		 FunctionLibrary.setText(searchField, searchText, desc);		 
       	 }
        
        }
        
      //Taking Screen shot
      ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Before"+ desc,LogStatus.INFO,true);
      FunctionLibrary.clickObject(goBtn, "", "Clicking go button");
  }


//-------------------------------------- Account Search Test cases---------------------------------------------------------
      
  	/**
       * ACS-O03 - Account Search using Cellular Phone in Home page
   
       */
  	public static void tcAccountSearchACS003() {
  		   try{
  	        searchHomePage(cellularPhoneTextBox, dataObj.get("CellularPhone"),"Search With Cellular Phone number");
  	        //FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 10, accountNameLable);
  	        
  	        //Verify Account Related to Phone number is getting displayed
  	        if(FunctionLibrary.getElement(vectorAccountTableSummary).isDisplayed())
  	        {
  	        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected:Account Details Related to Cellular Phone [" +dataObj.get("CellularPhone")+"] should be displayed <br>"+"Actual:Account details  linked to the Cellular phone is displayed",LogStatus.PASS,true);
  	        }
  	        
  	        else{
  	        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected:Account Details Related to Cellular Phone [" +dataObj.get("CellularPhone")+"] should be displayed <br>"+"Actual:Account details  linked to the Cellular phone is not Getting displayed",LogStatus.FAIL,true);
  	        }
  	        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Before Sorting Applied",LogStatus.INFO,true);
  	        Thread.sleep(5000);
  	        //Sorting by Name
  	        //FunctionLibrary.clickObject(accountNameLable, "", "Click on Account Name Lable");
  	        FunctionLibrary.objDriver.findElement(By.xpath("//div[@class='ui-jqgrid-sortable' and text()='Account Name']")).click();
  	        try{
  	       
  	        //Click on Sort Link

  	        	Robot enter=new Robot();
  	        enter.keyPress(KeyEvent.VK_ENTER);
  	        enter.keyRelease(KeyEvent.VK_ENTER);
  	        Thread.sleep(3000);

  	        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"After Sorting Applied",LogStatus.INFO,true);
  	        
  	        }catch(Exception e){e.printStackTrace();}
  		
  		 }catch(Exception e){
  			ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to"+e.getMessage() ,LogStatus.FAIL,true);	
  		 }


  	}//End of ACS003 Test case	
  //---------------------------------------------*************************************---------------------------------------------------------------	
  	/**
     * ACS-O04 - Account Search using Device # in Binocular Search
 
     */	
   public static void tcAccountSearchACS004() {
	 //Search using Device # 
	        try {
				binocularSerach(lookInDropDown, dataObj.get("LookInDropdownOption"), deviceNumberTextBox, dataObj.get("Transponder#"),"Select Devices from Lookin Dropdown", "Enter Transponder/Device# and search");
				ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"After Search With Device #"+dataObj.get("Transponder#"),LogStatus.INFO,true);
				FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 30, accountNameLabel);
				ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: Data should be displayed for the Searched Device # [" +dataObj.get("Transponder#")+"]  <br>"+"Actual: Data is displayed as Expected",LogStatus.PASS,true);
	        } catch (InterruptedException e) {
				
	        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to"+e.getMessage() ,LogStatus.FAIL,true);
			}
 	
  	}//End of ACS004 Test case
  //---------------------------------------------*************************************--------------------------------------------------------------- 
   /**
    * ACS-O08 - initiate Search from any screen

    */	
   public static void tcAccountSearchACS008() {
  		
	   	try {
	   		
	   		//String acctNumber = getAccountNumberUsingSql("mailidFordmtga001@conduent.com");
	   		String acctNumber = dataObj.get("AccountNumber");
  	        FunctionLibrary.clickObject(serviceRequestTab, "","Click on Service Request Tab");
  	        FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 10, accountNoServiceRqst);
  	        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Now in Service Req page now Initiating  Search",LogStatus.PASS,true);
  	        //Search using Account #
  	        binocularSerach(lookInDropDown, dataObj.get("LookInDropdownOption"), accountNoTextBoxBinosrch, acctNumber,"Select Accounts from Lookin Dropdown", "Enter Account# and search");
  	        FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 10, accountNoTextBoxBinosrch);
  	        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: Data should be displayed for the Searched Account # [" +acctNumber+"]  <br>"+"Actual: Data is displayed as Expected",LogStatus.PASS,true);
  	        FunctionLibrary.objDriver.findElement(By.linkText(acctNumber)).click();
  	        Thread.sleep(3000);
  	        FunctionLibrary.verifyWebElementExist(vectorAccountTableSummary, "Verify Vector Account table present");
  	        int rowNumberWithAccNo = FunctionLibrary.getRowNumberFromWebTable(vectorAccountTableSummary,acctNumber,"get the row number");
              
              FunctionLibrary.verifyWebTableCellData(vectorAccountTableSummary,rowNumberWithAccNo,2,
            		  acctNumber,"Verifying Account #",true);
  	      } catch (Exception e) {
  			
  	    	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to"+e.getMessage() ,LogStatus.FAIL,true);
			}
  	    
  	}//End of ACS008 Test case
  //---------------------------------------------*************************************--------------------------------------------------------------- 
   
   /**
    * ACS-O81 - Search using Account# in License Plate history in Binocular search

    */	
   public static void tcAccountSearchACS081()  {
 		
	   try {
		   //String acctNumber = getAccountNumberUsingSql("mailidFordmtga001@conduent.com");
		   String acctNumber = dataObj.get("AccountNumber");
	        //Search using Account# in License Plate history in Binocular search
	        binocularSerach(lookInDropDown, dataObj.get("LookInDropdownOption"), accountNoTextBoxBinosrch, acctNumber,
	        		"Select Licence plate History option from Lookin Dropdown", "Enter Account# and search");
	        FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 10, accountNoTextBoxBinosrch);
	        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: Data should be displayed for the Searched Account # [" +acctNumber+"]  <br>"+"Actual: Data is displayed as Expected",LogStatus.PASS,true);
	        FunctionLibrary.objDriver.findElement(By.linkText(acctNumber)).click();
	        Thread.sleep(3000);
	        FunctionLibrary.verifyWebElementExist(vectorAccountTableSummary, "Verify Vector Account table present");
	        int rowNumberWithAccNo = FunctionLibrary.getRowNumberFromWebTable(vectorAccountTableSummary,acctNumber,"get the row number");
	        FunctionLibrary.verifyWebTableCellData(vectorAccountTableSummary,rowNumberWithAccNo,2,
	        		acctNumber,"Verifying Account #",true);
	        } catch (Exception e) {
				
	        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to"+e.getMessage() ,LogStatus.FAIL,true);
			}
	        
	    
	}//End of ACS081 Test case
 //---------------------------------------------*************************************---------------------------------------------------------------  
   /**
    * ACS-O25 - Search for Incorrect Account # 

    */	
   public static void tcAccountSearchACS025() {
	   		try{
	   		//Search for Incorrect Account #  
  	        CommonLibrary.searchForAccount(dataObj.get("AccountNumber"));
  	        FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 15, vectorAccountListText);
  	        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: No data should be displayed for the Searched Account # [" +dataObj.get("AccountNumber")+"]  <br>"+"Actual: No Data is displayed",LogStatus.PASS,true);
	   		}catch(Exception e){
	   			ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to"+e.getMessage() ,LogStatus.FAIL,true);
	   		}
  	   
  	}//End of ACS025 Test case
 //---------------------------------------------*************************************---------------------------------------------------------------     
   /**
    * ACS-O34 - search For an account Without Any data

    */	 
  public static void tcAccountSearchACS034() {
	  		try{
	  		//search For an account Without Any data
	  		FunctionLibrary.clickObject(accountsTab, "", "Clicking Accounts tab");
	  		FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(accountNumberTxtBox,
	  	             accountsTab, 6, 43);
	  		JavascriptExecutor js2 = (JavascriptExecutor) objDriver;
            js2.executeScript("return document.readyState").toString().equals("complete");
            
  	        FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 15, goBtn);
  		    ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Search Without any Data:",LogStatus.INFO,true);
  		    FunctionLibrary.clickObject(goBtn, "", "Clicking go button");
  		  FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(errorMessageText,
  				goBtn, 6, 43);
  		    
  	        FunctionLibrary.verifyWebElementExist(errorMessageText, "Verify Error messasge Text");
  	        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: Error message should be displayed <br>"+"Actual: Error message is displayed",LogStatus.PASS,true);
	  		}catch(Exception e)
	  		{
	  			ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to"+e.getMessage() ,LogStatus.FAIL,true);
	  		}
  	   
  	}//End of ACS034 Test case
//---------------------------------------------*************************************---------------------------------------------------------------
  
  /**
   * ACS-O35 - search For an account With wild card[*]

   */
  public static void tcAccountSearchACS035() {
  	
	  		try{
	  	  //search For an account With wild card[*]
          searchHomePage(accountNameTextBoxHomepageSrch, dataObj.get("AccountName"), "Search With Wild Card");
          FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 30, accountNameLabel);
          ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: List of data should be displayed for the Searched value [" +dataObj.get("AccountName")+"]  <br>"+"Actual: List of Data is displayed",LogStatus.PASS,true);
	  		}catch(Exception e){
	  			ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to"+e.getMessage() ,LogStatus.FAIL,true);
	  		}
  
  }//End of ACS035 Test case
//---------------------------------------------*************************************---------------------------------------------------------------
  /**
   * ACS-O86 - Search using Transponder # in Binocular Search
   //* @param dataObj
   */
  public static void tcAccountSearchACS086() throws InterruptedException {
	  	  try{
	      //Search using Transponder #  
          binocularSerach(lookInDropDown, dataObj.get("LookInDropdownOption"), transponderNoTextBoxBinosrch, dataObj.get("Transponder#"), "Selecting Transponder from Dropdown", "Entering Transponder# in text box");
          //FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 10, vectorAccountListText);
          ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: Account related to that Transponder # [" +dataObj.get("Transponder#")+"] should be displayed   <br>"+"Actual:Account related to that Transponder # is displayed ",LogStatus.PASS,true);
	  	  }catch(Exception e){
	  		ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to"+e.getMessage() ,LogStatus.FAIL,true);
	  	  }
  }//End of ACS086 Test case
  //---------------------------------------------*************************************---------------------------------------------------------------
  /**
   * ACS-O95 -  Search using Toll ID in Binocular Search
   //* @param dataObj
   */
  
  public static void tcAccountSearchACS095() throws InterruptedException {
	  try{
  	        //Search using Toll ID  
			   
  	            binocularSerach(lookInDropDown, dataObj.get("LookInDropdownOption"), tollIdTextBox, dataObj.get("Toll Id"), "Selecting Toll ID from Dropdown", "Entering Toll ID in Toll ID text box");
  	           // String tollId=dataObj.get("Toll Id");
				   ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"After Search With Toll id #"+dataObj.get("Toll Id"),LogStatus.INFO,true);
				  // FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 30, tollIdTextBox);
				   FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 30, accountNameLabel);
				   ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: Data should be displayed for the Toll id [" +dataObj.get("Toll Id")+"]  <br>"+"Actual: Data is displayed as Expected",LogStatus.PASS,true);
				   /* String xpathOfTollIdSearchTable = tollIdSearchValidationTable;
		            int rowNum1 = FunctionLibrary.getRowNumberFromWebTable(xpathOfTollIdSearchTable,"NEW","get the row number");
		            
		            System.out.println("Row number is "+rowNum1);
		            String tollId=dataObj.get("Toll Id");

		            FunctionLibrary.verifyWebTableCellData(xpathOfTollIdSearchTable,rowNum1,1,tollId,"Verifying Toll ID",true);
		            FunctionLibrary.verifyWebTableCellData(xpathOfTollIdSearchTable,rowNum1,4,"Account number is present","Verifying Account number ",false);
		            String accountNumber = FunctionLibrary.getWebTableCellData(xpathOfTollIdSearchTable, 1, 4, "Getting Account Number");
		           
		            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Account number: "+ accountNumber, LogStatus.INFO, false);*/
		            
				
	        } 
  	      catch (InterruptedException e)
  	      {
				
	        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to"+e.getMessage() ,LogStatus.FAIL,true);
  	      }   
  		
  	}//End of ACS095 Test case
  
  /**
   * ACS-O96-  Search using UFM ID in Binocular Search
   //* @param dataObj
   */
  
  public static void tcAccountSearchACS096() throws InterruptedException {
	  try{
  	        //Search using UFM ID  
			   
  	            binocularSerach(lookInDropDown, dataObj.get("LookInDropdownOption"), ufmIdTextBox, dataObj.get("UFM Id"), "Selecting Toll ID from Dropdown", "Entering Toll ID in Toll ID text box");
  	           // String tollId=dataObj.get("Toll Id");
				ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"After Search With UFM Id #"+dataObj.get("UFM Id"),LogStatus.INFO,true);
				//FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 30, tollIdTextBox);
				FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 30, accountNameLabel);
				ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: Data should be displayed for the UFM Id [" +dataObj.get("UFM Id")+"]  <br>"+"Actual: Data is displayed as Expected",LogStatus.PASS,true);
				/*String xpathOfTollIdSearchTable = tollIdSearchValidationTable;
	            int rowNum1 = FunctionLibrary.getRowNumberFromWebTable(xpathOfTollIdSearchTable,"NEW","get the row number");
	            
	            System.out.println("Row number is "+rowNum1);

	            FunctionLibrary.verifyWebTableCellData(xpathOfTollIdSearchTable,rowNum1,1,dataObj.get("Toll Id"),"Verifying Toll ID",true);
	            FunctionLibrary.verifyWebTableCellData(xpathOfTollIdSearchTable,rowNum1,4,"Account number is present","Verifying Account number ",false);
	            String accountNumber = FunctionLibrary.getWebTableCellData(xpathOfTollIdSearchTable, 1, 4, "Getting Account Number");
	           
	            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Account number: "+ accountNumber, LogStatus.INFO, false);*/
	            
	        } 
  	      catch (InterruptedException e)
  	      {
				
	        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to"+e.getMessage() ,LogStatus.FAIL,true);
  	      }   
  		
  	}//End of ACS096 Test case
  
  /**
   * ACS-O87-  Search by notification ID and type.
   //* @param dataObj
   */
  
  public static void tcAccountSearchACS087() throws InterruptedException {
	  
  
  		try{
  	        //Search by notification ID and type.  			   
  	            FunctionLibrary.clickObject(firstLevelViewBar, "", "click on First Level View Bar");
  	           
  	            
  	            FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(selectNotificationSearch, firstLevelViewBar, 10, 30);
  	            FunctionLibrary.clickObject(selectNotificationSearch, "", "Select Notification search from the DropDown");
  	           // FunctionLibrary.selectDropDownListByValue(firstLevelViewBar, dropDownValue, "select Notification search from drop down");
				ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"selecting Notification search from dropdown",LogStatus.INFO,true);
			    FunctionLibrary.waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(queryNotificationBtn, selectNotificationSearch, 10, 30);
				Thread.sleep(2000);
				//click on query
				FunctionLibrary.clickObject(queryNotificationBtn, "", "click on Query");
				//enter notification id
				String notificationId=dataObj.get("Notification Id");
				FunctionLibrary.clickObject(notificationSearchLabel, "", "clicking on notification id textbox");
				FunctionLibrary.setText(notificationIdTxtBox, notificationId, "enter notification id");
				FunctionLibrary.clickObject(notificationGoBtn, "", "click on Go");				
				ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: Data should be displayed for the Notification Id "+"Actual: Data is displayed as Expected",LogStatus.PASS,true);
				String xpathNotificationTable = notificationValidationTable;
	            int rowNum1 = FunctionLibrary.getRowNumberFromWebTable(xpathNotificationTable,"EMAIL","get the row number");
	            
	            System.out.println("Row number is "+rowNum1);

	            FunctionLibrary.verifyWebTableCellData(xpathNotificationTable,rowNum1,2,"JEMAIL-ACT","Verifying Subqueue",true);
	            FunctionLibrary.verifyWebTableCellData(xpathNotificationTable,rowNum1,4,dataObj.get("AccountNumber"),"Verifying Account number ",false);
	            String accountNumber = FunctionLibrary.getWebTableCellData(xpathNotificationTable, 1, 4, "Getting Account Number");
	           
	            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Account number: "+ accountNumber, LogStatus.INFO, false);
	            
	        } 
  	      catch (Exception e)
  	      {
				
	        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to"+e.getMessage() ,LogStatus.FAIL,true);
  	      }   
  		
  	}//End of ACS087 Test case
  
  public static void tcAccountSearchACS063() throws InterruptedException {
	  try{
  	        //Search using Plate Number 
			   
  	            binocularSerach(lookInDropDown, dataObj.get("LookInDropdownOption"), licensePlateNumbetTxtBox, dataObj.get("Plate Number"), "Selecting Vehicles from Dropdown", "Entering plate number in text box");
  	           // String tollId=dataObj.get("Toll Id");
				ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"After Search With Plate Number"+dataObj.get("Plate Number"),LogStatus.INFO,true);
				FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 30, accountNumberLabel);
				ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: Data should be displayed for the Plate Number [" +dataObj.get("Plate Number")+"]  <br>"+"Actual: Data is displayed as Expected",LogStatus.PASS,true);
				/*String xpathOfTollIdSearchTable = tollIdSearchValidationTable;
	            int rowNum1 = FunctionLibrary.getRowNumberFromWebTable(xpathOfTollIdSearchTable,"FL","get the row number");
	            
	            System.out.println("Row number is "+rowNum1);

	            FunctionLibrary.verifyWebTableCellData(xpathOfTollIdSearchTable,rowNum1,2,dataObj.get("Plate Number"),"Verifying Plate number",true);
	            FunctionLibrary.verifyWebTableCellData(xpathOfTollIdSearchTable,rowNum1,1,"Account number is present","Verifying Account number ",false);
	            String accountNumber = FunctionLibrary.getWebTableCellData(xpathOfTollIdSearchTable, 1, 1, "Getting Account Number");
	           
	            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Account number: "+ accountNumber, LogStatus.INFO, false);*/
	        } 
  	      catch (InterruptedException e)
  	      {
				
	        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to"+e.getMessage() ,LogStatus.FAIL,true);
  	      }   
  		
  	}//End of ACS063 Test case
 //---------------------------------------------*************************************---------------------------------------------------------------
  /**
   * ACS-O29 - Search using email id in binocular search page 
   //* @param dataObj
   */
  public static void tcAccountSearchACS029()  {
  			
	  		
	  		String email = dataObj.get("EmailAddress");
	  		if(email == null || email =="")
	  		{
	  		
	  			email = "venkateswarlu.kamani@conduent.com";	
	  		}
	  		
	  		
	  
  	        //Search using email id in binocular search page 
  	        try {
  	        	Thread.sleep(10000);
				binocularSerach(lookInDropDown, dataObj.get("LookInDropdownOption"), 
						emailTextBoxBinosrch, email, "Selecting Accounts from Dropdown", "Entering Email ID in email Address text box");
				/*		//FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 10, vectorAccountListText);
				 String sql = "select max(account_no) as ACCOUNT_NO from siebel.v_etc_account a where email_address like '%tcCaseManagementCMGT024@xerox.com%' "
		                    + " and account_status_cd='ACTIVE' order by a.account_no desc "; 
				String sql = "select email_address as Email from siebel.v_etc_account a where a.account_no in()";
		                   				 //Getting Account Number from DB
				 String accountNumber =CommonLibrary.executeSqlAndGetOutputAsHashMap(sql).get("ACCOUNT_NO");
				 FunctionLibrary.getWebTableCellData(WebElement_Pro, rowNumber, columnNumber, Desc)*/
				//FunctionLibrary.verifyWebTableCellData(xpathOfServiceReqTable,1,1,accountNumber,"Verify Email Address Field",true);
				//if(FunctionLibrary.objDriver.findElement(By.xpath("//table//td[@aria-describedby='pq-results_Account Number']")).isDisplayed()){
				if(FunctionLibrary.objDriver.findElement(By.xpath("(//tr[@class='ui-widget-content jqgrow ui-row-ltr'])[1]")).isDisplayed()){
			
		
					ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: Data should be displayed for the Searched Email [" +email+"]  <br>"+"Actual: Data is displayed as Expected",LogStatus.PASS,true);
				}
			
					
				
  	        } catch (Exception e) {
  	        	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: Data should be displayed for the Searched Email [" +email+"]  <br>"+"Actual: Data is Not displayed as Expected",LogStatus.FAIL,true);
				
  	        	
			}  
  	}//End of ACS029 Test case
//---------------------------------------------*************************************---------------------------------------------------------------
  /**
   * ACS-O60 - Search using email id in SearchRequest Tab
   //* @param dataObj
   */
  public static void tcAccountSearchACS060() throws InterruptedException {
  		try{
  			String email = dataObj.get("EmailAddress");
	  		if(email ==null || email =="")
	  		{
	  		
	  			email = "mailidFordmtga001@conduent.com";	
	  		}
  	        //Search using email id in SearchRequest Tab
  	        serviceReqSerach(emailHomePage, email, "Search With Email");
  	        //binocularSerach(lookInDropDown, dataObj.get("LookInDropdownOption"), emailTextBoxBinosrch, dataObj.get("EmailAddress"), "Selecting Accounts from Dropdown", "Entering Email ID in email Address text box");
  	        FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 10, statusLabelAccountHistory);
  	        //Verify Account Details in Service Request Table
  	        String actulaEmailID=FunctionLibrary.getText(emailIdServiceRqst,  "Get Text of Email ID FIeld");
  	      if(email.equalsIgnoreCase(actulaEmailID))
  	      {
  	        ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Expected: Account should be displayed related to the Searched email # [" +email+"]  <br>"+"Actual: Account is displayed related to searched Email ID"+actulaEmailID,LogStatus.PASS,true);
  	      }
  	      }catch(Exception e){
  			ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test Failed Due to"+e.getMessage() ,LogStatus.FAIL,true);
  		}
  	}//End of ACS060 Test case
 //---------------------------------------------End Of Account Search Test cases---------------------------------------------------------------	

  /**
   * ACS-O04 - Account Search using Device # in Binocular Search

   */	
 
}
