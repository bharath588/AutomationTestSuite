
package features;

import static libraries.CommonLibrary.siebelAccoutEstablishment;
import static objectProperties.SblCreateAccountPageProperties.TypeValue;
import static objectProperties.SblCreateAccountPageProperties.administrativeBusinessServiceLink;
import static objectProperties.SblCreateAccountPageProperties.firstNotificationParamValue;
import static objectProperties.SblCreateAccountPageProperties.goBtnVectorInternalProcess;
import static objectProperties.SblCreateAccountPageProperties.methodName;
import static objectProperties.SblCreateAccountPageProperties.newInputArgumentsBtn;
import static objectProperties.SblCreateAccountPageProperties.newPropertySetBtn;
import static objectProperties.SblCreateAccountPageProperties.newSimulatorBtn;
import static objectProperties.SblCreateAccountPageProperties.pendingCloseDays;
import static objectProperties.SblCreateAccountPageProperties.pendingOpenDays;
import static objectProperties.SblCreateAccountPageProperties.propertyNameClick;
import static objectProperties.SblCreateAccountPageProperties.propertyNameTextBox;
import static objectProperties.SblCreateAccountPageProperties.propertyNameTextBoxIcon;
import static objectProperties.SblCreateAccountPageProperties.propertySetOkBtn;
import static objectProperties.SblCreateAccountPageProperties.propertySetSaveBtn;
import static objectProperties.SblCreateAccountPageProperties.runBtn;
import static objectProperties.SblCreateAccountPageProperties.searchTextBox;
import static objectProperties.SblCreateAccountPageProperties.serviceName;
import static objectProperties.SblCreateAccountPageProperties.simulatorLink;
import static objectProperties.SblCreateAccountPageProperties.siteMapImage;
import static objectProperties.SblCreateAccountPageProperties.valueTextBox;
import static objectProperties.SblCreateAccountPageProperties.vectorInternalDroddownArrow;
import static objectProperties.SblCreateAccountPageProperties.vectorInternalProcessLink;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import com.relevantcodes.extentreports.LogStatus;

import libraries.FunctionLibrary;
import libraries.ReportLibrary;

public  class CamsSiebelAccountEstablishment {

    public static void CamsSiebelAccountEstablishmentTest() throws Exception {

        String testScenariosFileName = "FLCSS_CAMS_AcccountEstablishment";
        String testScenariosSheetName = "Test_Scenarios";
        siebelAccoutEstablishment(testScenariosFileName, testScenariosSheetName);

    }//End Of Class

    
    public static void pendingPaymentOption(HashMap<String, String> dataObj) throws Exception
    { 
     try{
    	 String paramValToBeAdded = dataObj.get("ParamVal");
    	 paramValToBeAdded = paramValToBeAdded.replace("\n","");

         String []paramValues = paramValToBeAdded.split(":");
         String paramValueSetToZero;
         String paramValueSetToSeven;
         String paramValueSetToFourteen;
         paramValueSetToZero = paramValues[0];
         paramValueSetToSeven = paramValues[1];
         paramValueSetToFourteen = paramValues[2];
         //call sitemap function
         siteMap();
         //call vectorInternalProcessParam  function to set Parameters
         vectorInternalProcessParam();
         FunctionLibrary.clickObject(firstNotificationParamValue,"", "click on First Notification param Value"); 
         //set first pending notification param value to zero
         System.out.println("Setting up firstNotificationParamValue to Zero ");
         setParamVal(paramValueSetToZero );
         //Call function for Run Job for pending pay
         runJobPendingPay(dataObj);
//=====================================================================
    //code for Query in table for email notification
   // Mailhouse query: SELECT * FROM SIEBEL.CX_MHOUSE_INT ORDER BY CREATED DESC
   // Fields to verify: Created, Firstname, Last name, FileType, FIELD2, Email

    String emailNotificationQuery="SELECT CREATED,FIELD2,FIRSTNAME,LASTNAME,FILETYPE,EMAIL FROM SIEBEL.CX_MHOUSE_INT ORDER BY CREATED DESC";
    System.out.println("check email notification status in DB");
    dbStatusCheckAfterRunningBatchJob(emailNotificationQuery);
//=====================================================================    
    //code for 14days
    //call sitemap function
    //Thread.sleep(5000);
    siteMap();
    //Thread.sleep(15000);
    //call vectorInternalProcessParam  function to set Parameters
    vectorInternalProcessParam();
   // Thread.sleep(3000);
    FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 10, vectorInternalDroddownArrow);
    //Select Account Type from Vector Drop down
    FunctionLibrary.clickObject(vectorInternalDroddownArrow,"", "click on Vector Dropdown Arrow");
    FunctionLibrary.clickObject(TypeValue,"", "Select Accout Type from Dropdown");
    //search for Pending and click on GO
    FunctionLibrary.setText(searchTextBox,"PEND", "Enter Service Name");
    FunctionLibrary.clickObject(goBtnVectorInternalProcess,"", "click on Go Button");
    //click on pending open days param value
    FunctionLibrary.clickObject(pendingOpenDays,"", "click on First Notification param Value"); 
    System.out.println("Setting up pendingOpenDays to Zero ");
    //set  pending open days param value to zero
    setParamVal(paramValueSetToZero);
    //click on pending close days param value
    FunctionLibrary.clickObject(pendingCloseDays,"", "click on First Notification param Value"); 
    System.out.println("Setting up pendingCloseDays to Zero ");
    //set  pending close days param value to zero
    setParamVal(paramValueSetToZero);
    //Call function for Run Job for pending pay
    runJobPendingPay(dataObj);
//======================================================================    
    //code for Query in table for closed status
    String closedStatusQuery="SELECT CUST_STAT_CD,X_PEND_OPEN_NOTIFY_DT,POPULATION_GRP_CD,NAME FROM SIEBEL.S_ORG_EXT";
    System.out.println("check status in DB");
    dbStatusCheckAfterRunningBatchJob(closedStatusQuery);
    
//======================================================================    
    //Code for Reset Notification back to its original value
    siteMap();
    vectorInternalProcessParam();
    FunctionLibrary.webdrvwaitByVisiblityofElementClickableByXpath(FunctionLibrary.objDriver, 15,firstNotificationParamValue);
    FunctionLibrary.clickObject(firstNotificationParamValue,"", "click on First Notification param Value"); 
    //set first pending notification param value to zero
    System.out.println("ReSetting up firstNotificationParamValue to Zero ");
    setParamVal(paramValueSetToSeven);
    //Select Account Type from Vector Drop down
    FunctionLibrary.clickObject(vectorInternalDroddownArrow,"", "click on Vector Dropdown Arrow");
    FunctionLibrary.clickObject(TypeValue,"", "Select Accout Type from Dropdown");
    FunctionLibrary.setText(searchTextBox,"PEND", "Enter Service Name");
    FunctionLibrary.clickObject(goBtnVectorInternalProcess,"", "click on Go Button");
    //click on pending open days param value
    FunctionLibrary.clickObject(pendingOpenDays,"", "click on First Notification param Value"); 
    //set  pending open days param value to 7
    System.out.println("ReSetting up pendingOpenDays to value Seven ");
    setParamVal(paramValueSetToSeven);//******************************************************************************
    //click on pending close days param value
    FunctionLibrary.clickObject(pendingCloseDays,"", "click on First Notification param Value"); 
    //set  pending close days param value to 14
    System.out.println("ReSetting up pendingCloseDays to value fourteen ");
    setParamVal(paramValueSetToFourteen);//***************************************************************************
    }catch(Exception e){e.printStackTrace();}
    } 
    /**Created By Boopathi
     * Method for Going to parameter set up page[Vector Internal Process parameter page]
     * @throws InterruptedException
     */
    public static void vectorInternalProcessParam() throws InterruptedException{
    	try{
    	String Desc;
    	Thread.sleep(3000);
    	//FunctionLibrary.webdrvwaitByVisiblityofElementClickableByXpath(FunctionLibrary.ObjDriver, 20, "xpath=//*[@id='sitemapFilterInput']");
    	//#####################Scroll down to VectorINternal ProcessParameters  link##############
        FunctionLibrary.objDriver.findElement(By.xpath("//*[@id='sitemapFilterInput']")).sendKeys("internal");  
        //Move Cursor to VectorINternal ProcessParameters  Link
        Desc="Move Cursor To Vector Business Rules Link";
        FunctionLibrary.scrollDowntoElement(vectorInternalProcessLink, Desc);

        //FunctionLibrary.clickObject(vectorBusinessRulesLink, "", "Click on vector Business Rules link");
        FunctionLibrary.clickObject(vectorInternalProcessLink, "", "Click on vector Internal Process link");
    	}catch(Exception e){e.printStackTrace();}
    }
    
    /**Created By Boopathi
     * Method for Going to Site Map
     * @throws InterruptedException
     */
    public static void siteMap() throws InterruptedException{
    	try{
    		FunctionLibrary.webdrvwaitByVisiblityofElementClickableByXpath(FunctionLibrary.objDriver, 20, siteMapImage);
        FunctionLibrary.clickObject(siteMapImage, "", "Click on SiteMap image");

        JavascriptExecutor js = (JavascriptExecutor) FunctionLibrary.objDriver;
        js.executeScript("return document.readyState").toString().equals("complete");
        Thread.sleep(3000);
    	}catch(Exception e){e.printStackTrace();}
    }
    /**Created By Boopathi
     * Method for Setup Parameter value for Job run
     * @throws InterruptedException
     */
    public static void setParamVal(String paramValueSetToZero){
    	try{
    	FunctionLibrary.objDriver.findElement(By.xpath("//*[@name='Param_Value']")).clear();
        //FunctionLibrary.ObjDriver.findElement(By.xpath("//*[@name='Param_Value']")).sendKeys(dataObj.get("ParamVal"));
    	FunctionLibrary.objDriver.findElement(By.xpath("//*[@name='Param_Value']")).sendKeys(paramValueSetToZero);
    	}catch(Exception e){e.printStackTrace();}
    }
    /**Created By Boopathi
     * Method for Job run
     * @throws InterruptedException
     */
    public static void runJobPendingPay(HashMap<String, String> dataObj) throws InterruptedException{
    	try{
    	String Desc;
        //code for Running Job
        FunctionLibrary.clickObject(siteMapImage, "", "Click on SiteMap image");

        JavascriptExecutor js1 = (JavascriptExecutor) FunctionLibrary.objDriver;
        js1.executeScript("return document.readyState").toString().equals("complete");

        //#####################Scroll down to VectorINternal ProcessParameters  link##############

        //Move Cursor to administrative Business Service Link
        Desc="Move Cursor To administrative Business Service Link";
        FunctionLibrary.scrollDowntoElement(administrativeBusinessServiceLink, Desc);

       // Thread.sleep(2000);
        FunctionLibrary.clickObject(administrativeBusinessServiceLink, "", "Click on administrative Business Service Link");
        //click on Simulator Link
        FunctionLibrary.clickObject(simulatorLink, "", "Click on simulator Link");
        //wait for page loading
        FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 10, newSimulatorBtn);
        //click on New Button
        FunctionLibrary.clickObject(newSimulatorBtn, "", "Click on New button");
        //Click and Set text on Service name text box
        FunctionLibrary.clickObject(serviceName, "", "Click on Service Name");
        FunctionLibrary.setText(serviceName,dataObj.get("ServiceName"), "Enter Service Name");
        //Click and Set text on Method name text box
        FunctionLibrary.clickObject(methodName, "", "Click on method Name");
        FunctionLibrary.setText(methodName,dataObj.get("MethodName"), "Enter method Name");
        //click on New Button in Inout Arguments Section
        FunctionLibrary.clickObject(newInputArgumentsBtn, "", "Click on New button in Input Arguments");
        //click on property Name
        FunctionLibrary.clickObject(propertyNameClick, "", "Click on Property Name");
        //click on property Name Text Box ICon
        FunctionLibrary.clickObject(propertyNameTextBoxIcon, "", "Click on Property Name");
        //wait for Object loading
        FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 10, newPropertySetBtn);
        //click on New Button
        FunctionLibrary.clickObject(newPropertySetBtn, "", "Click on New Button on Properties Set page");
       // Thread.sleep(2000);
        FunctionLibrary.webdrvwaitByVisiblityofElementLocByXpath(FunctionLibrary.objDriver, 10, propertyNameTextBox);
        //Set Text on property Name TextBox
        FunctionLibrary.setText(propertyNameTextBox,dataObj.get("PropertyName"), "Enter Property Name");
        //Set Text on Value TextBox
        FunctionLibrary.setText(valueTextBox,dataObj.get("PropertyValue"), "Enter Value");
        //click on New Button
        FunctionLibrary.clickObject(propertySetSaveBtn, "", "Click on Save Button on Properties Set page");
        FunctionLibrary.clickObject(propertySetOkBtn, "", "Click on Save Button on Properties Set page");
        
        
        //click on Run Button
        FunctionLibrary.clickObject(runBtn, "", "Click on Run Button");
    	}catch(Exception e){e.printStackTrace();}
    }
    
    //method for query in db
    public static void dbStatusCheckAfterRunningBatchJob(String sql) throws SQLException, ClassNotFoundException, InterruptedException
    {
    	try{

    		//step1 load the driver class
    		            Class.forName("oracle.jdbc.driver.OracleDriver");

    		//step2 create  the connection object
    		            Connection con=DriverManager.getConnection(
    		                    "jdbc:oracle:thin:@10.36.96.2:1521:flvecqa1","sumitb","burnwal");

    		//step3 create the statement object
    		            Statement stmt=con.createStatement();

    		//step4 execute query
    		            //ResultSet rs=stmt.executeQuery("Select OU_NUM,OU_TYPE_CD,CUST_STAT_CD from siebel.S_ORG_EXT Where OU_TYPE_CD='PRIVATE' and CUST_STAT_CD='CLOSE PEND'");
    		            ResultSet rs=stmt.executeQuery(sql);
    		            
    		            while(rs.next()){
    		            	int i=0;
    		            	if(i==0){
    		            	System.out.println(rs.getString(1)+"  "+rs.getString(2));
    		            	ReportLibrary.addStep(ReportLibrary.Test_Step_Number,rs.getString(1)+"  "+rs.getString(2),LogStatus.INFO,true);
    		                //System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getString(4));
    		            	break;
    		            	}
    		            }

    		//step5 close the connection object
    		            con.close();

    		        }catch(Exception e){ System.out.println(e);}

   /* System.out.println("Status Verified in DB");
    ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, "Status Verified in DB",
            LogStatus.INFO, false);*/
    }

    
}


