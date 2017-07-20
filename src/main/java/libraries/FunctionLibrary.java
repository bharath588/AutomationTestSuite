package libraries;


import com.relevantcodes.extentreports.LogStatus;
import org.joda.time.DateTime;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static libraries.FunctionLibrary.objDriver;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;


@SuppressWarnings({ "unused" })
public class FunctionLibrary
{

   public static WebDriver objDriver;
   public static WebElement Browser_Element;
   public static List<WebElement> Browser_Elements;
   public static List<WebElement> Browser_Elements_For_Div;
   public static Actions Element_Action;
   public static WebDriverWait waitForObject;
   public static WebDriverWait Wait_For_Ajax;
   public static File Screen_Shot;
   public static String browser="firefox";
   static FunctionLibrary Function_Library_Instance;
   FunctionLibrary()
   {

   }

   /*
   	Get the web element
  	@param  Str_Property : Identify the web element by using this property of the object
   	@return web element
    */
   public static WebElement getElement(String Str_Property)
   {
     
	   Browser_Element= getLocatorForExist(Str_Property);
      return Browser_Element;
   }

   /*
  	Get the web element
 	@param  Str_Property : Identify the web element by using this property of the object
  	@return web element
   */
   public static WebElement getLocatorForExist(String Str_Property)
   {
	   FunctionLibrary.waitForObject = new WebDriverWait(FunctionLibrary.objDriver, 60);
	   FunctionLibrary.objDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	   
	   WebDriverWait wait1 = new WebDriverWait(objDriver,5);
      WebElement Browser_Element1 = null;
      String Actual_Property = Str_Property;

         Browser_Element1 = objDriver.findElement(By.xpath(Actual_Property));
                  
         int i;
         for(i=0;i<=12;i++)
         {
        	 
        	 try{
        		 wait1.until(ExpectedConditions.elementToBeClickable(By.xpath(Actual_Property)));
        		 
                 if(Browser_Element1.isEnabled())
                 {
                    Browser_Element1 = objDriver.findElement(By.xpath(Actual_Property));
                    break;
                 }
                 else
                 {
                    return Browser_Element1;
                 }
                 
	
        	 }
            
               catch(InvalidElementStateException e1)
               {
            	   System.out.println("Invalid element state exception got it  for object: "+ Actual_Property + ".Giving another try");
            	   try {
   					Thread.sleep(2000);
   				} catch (InterruptedException e) {
   					// TODO Auto-generated catch block
   					e.printStackTrace();
   				}
               }
        	 catch(StaleElementReferenceException e1)
             {
        		 
        		 System.out.println("Stale element exception got it  for object: "+ Actual_Property + ".Giving another try");
        		 try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		// FunctionLibrary.ObjDriver.navigate().refresh();
             }
               catch(WebDriverException e2)
               {
            	   System.out.println("WebDriver exception got it  for object: "+ Actual_Property + ".Giving another try");
               }

         }
         Browser_Element1 = objDriver.findElement(By.xpath(Actual_Property));
      
            //Wait_For_Div();
      return Browser_Element1;
   }

   /*
  	Make flag Error_Flag to true
   */
  public static void Exception_Content()
   {
      Browser_Element = null;
      ReportLibrary.Error_Flag = true;
      //Function_Library.Close_All_Active_Browser();
      //Report_Library.End_Test();
   }
  
  /*
	Set text in text box
	@param  Str_Property : Identify the web element by using this property of the object
	@param  Text_Val : Pass the value to the text box
	@param  Desc : Description of the activity
	@return web element
 */
   public static void setText(String Text_Property,String Text_Val,String Desc)
   {
      if(!Text_Property.matches(""))
      {

         WebElement TextBox = null;

         TextBox = getLocatorForExist(Text_Property);
         
         

         if(TextBox == null)
         {
            if(Desc!="")
            {
               //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc + ": " +Text_Val, LogStatus.FAIL, true);Exception_Content();

            }
         }
         else
         {
            try
            {
            	
            	if(!TextBox.getAttribute("value").startsWith(Text_Val))
            	{

	               TextBox.clear();
	             
	               TextBox.sendKeys(Text_Val);
	              TextBox.sendKeys(Keys.TAB);
            	}
            	else
            	{
            		//TextBox.click();
            		TextBox.sendKeys(Keys.TAB);
            	}
             
            }
            catch(InvalidElementStateException ee)
            {
               //ConfigurationLibrary.Sound_Beep();
               //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+ ": " +Text_Val, LogStatus.FAIL, true);
               ee.printStackTrace();
               Exception_Content();
            }
         }
      }
   }
   
   /*
  	Get text from text box
  	@param  Text_Property : Identify the web element by using this property of the object
  	@param  Desc : Description of the activity
  	@return : Text box value will be return as String
   */
   public static String getText(String Text_Property,String Desc) {
      String Text_Val ="";
     
         WebElement TextBox = null;
         TextBox = getElement(Text_Property);
         if (TextBox == null) {
            if (Desc!="") {
               ReportLibrary.addStep(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);
               Exception_Content();
            }
         } else {
            Text_Val = TextBox.getAttribute("value");
           
         }
      
      return Text_Val;
   }
    
	/*
	Verify the Text Box Value
	@param  Text_Property : Identify the web element by using this property of the object 
	@param Expected_Value : get the value from given text box
	@param  Desc : Description of the activity
	@param screenShot : Screenshot required(Yes or No)
	*/
	
   public static void verifyTextBoxValue(String Text_Property,String Expected_Value,String Desc, boolean screenShot) 
   {
      if(!Expected_Value.matches("NA"))
      {
         String Actual_Text_Val;
         WebElement TextBox = null;
         TextBox = getElement(Text_Property);
         if(TextBox == null)
         {
            if(Desc!="")
            {
               //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc , LogStatus.FAIL, true);Exception_Content();
            }
         }
         else
         {
        	 
            Actual_Text_Val = TextBox.getAttribute("value");
            if(Actual_Text_Val.equalsIgnoreCase(Expected_Value))
            {
               if(Desc!="")
               {
                  ReportLibrary.addStep(ReportLibrary.Test_Step_Number, Desc+ ": Actual Value :" + Actual_Text_Val + ", Expected Value : " + Expected_Value, LogStatus.PASS, screenShot);
               }
            }
            else
            {
               if(Desc!="")
               {
                 ReportLibrary.addStep(ReportLibrary.Test_Step_Number, Desc+ ": Actual Value :" + Actual_Text_Val + ", Expected Value : " + Expected_Value, LogStatus.FAIL, true);Exception_Content();
               }
            }

         }
      }
   }
    
  /*
	Verify the Text Box Value
	@param  Text_Property : Identify the web element by using this property of the object 
	@param Expected_Value : get the value from given text box and replace empty space with $ symbol
	@param  Desc : Description of the activity
	@param screenShot : Screenshot required(Yes or No)
	*/
  public static void verifyTextBoxValueWithDollars(String Text_Property,String Expected_Value,String Desc, boolean screenShot) 
   {
      if(!Expected_Value.matches("NA"))
      {
         String Actual_Text_Val;
         WebElement TextBox = null;
         TextBox = getElement(Text_Property);
         if(TextBox == null)
         {
            if(Desc!="")
            {
               //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc , LogStatus.FAIL, true);Exception_Content();
            }
         }
         else
         {
        	 
            Actual_Text_Val = TextBox.getAttribute("value");            
            Expected_Value=Expected_Value.replace("$", "").replace(",","");
            Actual_Text_Val=Actual_Text_Val.replace("$", "").replace(",","").replace("(","").replace(")","");
            if(Double.valueOf(Actual_Text_Val).equals(Double.valueOf(Expected_Value)))
            {
               if(Desc!="")
               {
                  ReportLibrary.addStep(ReportLibrary.Test_Step_Number, Desc+ ": Actual Value :" + Actual_Text_Val + ", Expected Value : " + Expected_Value, LogStatus.PASS, screenShot);
               }
            }
            else
            {
               if(Desc!="")
               {
                 ReportLibrary.addStep(ReportLibrary.Test_Step_Number, Desc+ ": Actual Value :" + Actual_Text_Val + ", Expected Value : " + Expected_Value, LogStatus.FAIL, true);Exception_Content();
               }
            }

         }
      }
   }
   
  /*
	Select the dropdown value
	@param  DropDownList_Property : Identify the web element by using this property of the object 
	@param DropDownList_Val : value which has to select in dropdown
	@param  Desc : Description of the activity
	*/
  public static void selectDropDownList(String DropDownList_Property,String DropDownList_Val,String Desc) 
   {
      if(!DropDownList_Val.matches("NA"))
      {
         try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         WebElement Element = null;
         Element = getElement(DropDownList_Property);
         if(Element == null)
         {
            if(Desc!="")
            {
               //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc +": "+DropDownList_Val, LogStatus.FAIL, true);Exception_Content();
            }
         }
         else
         {
            Select DropDownList = new Select(Element);
            DropDownList.selectByVisibleText(DropDownList_Val) ;
            if(Desc!="")
            {
               //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+": "+DropDownList_Val, LogStatus.PASS, false);
            }
         }
      }
   }

   /*
	Select the dropdown value
	@param  DropDownList_Property : Identify the web element by using this property of the object 
	@param DropDownList_Val : value which has to select in dropdown
	@param  Desc : Description of the activity
	*/
   public static void selectDropDownListByValue(String DropDownList_Property,String DropDownList_Val,String Desc)
   {
      if(!DropDownList_Val.matches("NA"))
      {
         WebElement Element = null;
         Element = getElement(DropDownList_Property);
         if(Element == null)
         {
            if(Desc!="")
            {
               //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc +": "+DropDownList_Val, LogStatus.FAIL, true);Exception_Content();
            }
         }
         else
         {
           Select DropDownList = new Select(Element);
            DropDownList.selectByValue(DropDownList_Val);
            //Ajax_Wait();
            //waitForAjax(1500);
            if(Desc!="")
            {
               //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+": "+DropDownList_Val, LogStatus.PASS, false);
            }
         }
      }
   }

   /*
	Select the dropdown value
	@param  DropDownList_Property : Identify the web element by using this property of the object 
	@param DropDownList_Option_Index :Index which has to select in dropdown
	@param  Desc : Description of the activity
	*/
   public static void selectDropDownListByIndex(String DropDownList_Property,String DropDownList_Option_Index,String Desc) throws Exception
   {

      if(! DropDownList_Option_Index.matches("NA"))
      {
         WebElement Element = null;
         Element = getElement(DropDownList_Property);
         if(Element == null)
         {
            if(Desc!="")
            {
               //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc +": "+DropDownList_Option_Index, LogStatus.FAIL, true);Exception_Content();
            }
         }
         else
         {
            Select DropDownList = new Select(Element);
            DropDownList.selectByIndex(Integer.parseInt(DropDownList_Option_Index));
            if(Desc!="")
            {
               //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+": "+DropDownList_Option_Index, LogStatus.PASS, false);
            }
         }
      }
   }

   /*
	Verify the dropdown value
	@param  DropDownList_Property : Identify the web element by using this property of the object 
	@param Expected_Option : value which has to check in dropdown
	@param  Desc : Description of the activity
	*/
   public static void verifyDropDownListSelectedOption(String DropDownList_Property,String Expected_Option,String Desc) throws Exception
   {
      if(!Expected_Option.matches("NA"))
      {
         String Actual_Option;
         WebElement Element = null;
         Element = getElement(DropDownList_Property);
         if(Element == null)
         {
            //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc +": "+Expected_Option, LogStatus.FAIL, true);Exception_Content();
         }
         else
         {
            Select DropDownList = new Select(Element);
            Actual_Option = DropDownList.getFirstSelectedOption().getText();
            if(Actual_Option.equalsIgnoreCase(Expected_Option))
            {
               if(Desc!="")
               {
                  //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+": Expected Option:"+Expected_Option+", Actual Option: "+Actual_Option, LogStatus.PASS, true);
               }
            }
            else
            {
               if(Desc!="")
               {
                  //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc+": Expected Option:"+Expected_Option+", Actual Option: "+Actual_Option, LogStatus.FAIL, true);Exception_Content();
               }
            }
         }
      }
   }
  
   /*
	Verify the dropdown value
	@param  DropDownList_Property : Identify the web element by using this property of the object 
	@param Expected_Option : value which has to check in dropdown
	@param  Desc : Description of the activity
	*/
   public static void verifyDropDownListOptionsNotExist(String DropDownList_Property,String Expected_Options,String Desc) throws Exception
   {
      if(!Expected_Options.matches("NA"))
      {
        // Thread.sleep(500);
         String Final_OutPut = "";
         List <WebElement> Actual_Option_List;
         String[] Expected_Option_List;
         WebElement Element = null;
         Element = getElement(DropDownList_Property);
         if(Element == null)
         {
            if(Desc!="")
            {
               //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc +": "+Expected_Options, LogStatus.FAIL, true);Exception_Content();
            }
         }
         else
         {
            Select DropDownList = new Select(Element);
            Actual_Option_List = DropDownList.getOptions();
            Expected_Option_List = Expected_Options.split(";");
            int i;
            int j;
            int Exist;
            for(i=0;i<Expected_Option_List.length;i++)
            {
               Exist = 0;
               for(j=0;j<Actual_Option_List.size();j++)
               {
                  if(Expected_Option_List[i].toString().equalsIgnoreCase(Actual_Option_List.get(j).getText()))
                  {
                     Exist = Exist +1;
                     break;
                  }
               }
               if(Exist>0)
               {
                  Final_OutPut = Final_OutPut + Expected_Option_List[i].toString();
               }
            
            }
            if(Final_OutPut.contains(""))
            {
               if(Desc!="")
               {
                  ReportLibrary.addStep(ReportLibrary.Test_Step_Number, Desc+": Expected Option " + Expected_Options + " is not present. "+Final_OutPut, LogStatus.PASS, true);
               }
            }
            else
            {
               if(Desc!="")
               {
                  ReportLibrary.addStep(ReportLibrary.Test_Step_Number, Desc+": Expected Option " + Expected_Options +"is  present"+Final_OutPut, LogStatus.FAIL, true);Exception_Content();
               }
            }
         }
      }
   }

   /*
	select the check box
	@param  CheckBox_Pro : Identify the web element by using this property of the object 
	@param CheckBox_Val : check box value(yes or no) 
	@param  Desc : Description of the activity
	*/
	
   public static void setCheckBox(String CheckBox_Pro,String CheckBox_Val,String Desc)
   {

      if(!CheckBox_Val.toString().matches("NA"))
      {
         String IsChecked;
         WebElement CheckBox = null;
         CheckBox = getElement(CheckBox_Pro);

         waitForObject.until(ExpectedConditions.visibilityOf(CheckBox));
         waitForObject.until(ExpectedConditions.elementToBeClickable(CheckBox));
         if(CheckBox == null)
         {
            //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
         }
         else
         {
            IsChecked = CheckBox.getAttribute("value");
            if(CheckBox_Val.equals("Yes"))
            {

               if(IsChecked!="true" )
               {
                  CheckBox.click() ;
               }
            }
            else if(CheckBox_Val.matches("No"))
            {
               if(IsChecked!="true")
               {
                  //// Element_Action.click(CheckBox).build().perform();
                  CheckBox.click() ;
               }
            }

            else if(CheckBox_Val.matches("Yes"))
            {
               if(IsChecked==null)
               {
                  CheckBox.click() ;
               }
            }

         }
      }
   }

   /*
	Click on object
	@param  Object_Name_Pro : Identify the web element by using this property of the object 
	@param Event : event to click on selected object
	@param  Desc : Description of the activity
	*/
	
   public static void clickObject(String Object_Name_Pro,String Event,String Desc)
   {
      if(!Event.matches("NA"))
      {
         WebElement Object = null;
         try
         {

            Object = getElement(Object_Name_Pro);
            waitForObject.until(ExpectedConditions.visibilityOf(Object));
            waitForObject.until(ExpectedConditions.elementToBeClickable(Object));

            if(Object == null)
            {
               if(Desc!="")
               {
                  ReportLibrary.addStep(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);
                  //Exception_Content();
               }
            }
            else
            {
               highLightElement(objDriver,Object);

               try
               {
                  Object.click();
               }
               catch (WebDriverException e)
               {

                  new Actions(FunctionLibrary.objDriver).moveToElement(Object).perform();
                  new Actions(FunctionLibrary.objDriver).click().perform();

               }
               if(Desc!="")
               {
                  //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);
               }
            }
         }
         catch (StaleElementReferenceException elementHasDisappeared) {
            //ConfigurationLibrary.Sound_Beep();
            Object = getElement(Object_Name_Pro);
            if(Object == null)
            {
               if(Desc!="")
               {
                  ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Expected:" +Desc, LogStatus.FAIL, true);
                  //Exception_Content();
               }
            }
            else
            {
               Object.click();
              // Wait_For_Div();
               if(Desc!="")
               {
                  //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);
               }
            }
         }

      }
   }

   /*
	Double Click on object
	@param  Object_Name_Pro : Identify the web element by using this property of the object 
	@param Event : event to Double click on selected object
	@param  Desc : Description of the activity
	*/
   public static void DblclickObject(String Object_Name_Pro,String Event,String Desc) throws Exception
   {
      if(!Event.matches("NA"))
      {
         WebElement Object = null;
         Object = getElement(Object_Name_Pro);
         if(Object == null)
         {
            if(Desc!="")
            {
               //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
            }
         }
         else
         {
            Element_Action.doubleClick(Object).build().perform();
            if(Desc!="")
            {
               //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, false);
            }
         }
      }
   }

    /*
	Capture a screenshot
	@param  ScreenShot_Location : selecting location to store screenShot
	@return location*/
   public static String captureScreenShot(String ScreenShot_Location)
   {
      Screen_Shot = ((TakesScreenshot)objDriver).getScreenshotAs(OutputType.FILE);
      Screen_Shot.renameTo(new File (ScreenShot_Location));

      return ScreenShot_Location;
   }

    /*
	get time stamp
	@return time stamp*/
   public String getTimeStamp()
   {
      Long l = Calendar.getInstance().getTimeInMillis();
      return l.toString();
   }

   /*
	get Row Count
	@return number of rows in selected table*/
   public static int getWebTableRowCount(WebElement Table_Element) throws Exception
   {
      return Table_Element.findElements(By.tagName("tr")).size();
   }

   /*
	get Column Count
	@return number of Columns in selected table*/
   public static int getWebTableColumnCount(WebElement Table_Element) throws Exception
   {
      return Table_Element.findElements(By.tagName("tr")).get(0).findElements(By.tagName("td")).size();
   }

   /*
	verify Web Table Data Not Exist
	@param  WebElement_Pro : Identify the web element by using this property of the object 
	@param ExpectedText : text which has to verify in a table
	@param  Desc : Description of the activity
	*/
   public static void verifyWebTableDataNotExist(String WebElement_Pro,String ExpectedText,String Desc) throws Exception
   {
      if(!ExpectedText.matches("NA"))
      {
         int Row;
         int Column;
         int iterate;
         int iterate1;
         Boolean ExistFlg = false;
         WebElement Element = null;
         Element = getElement(WebElement_Pro);
         Row = Element.findElements(By.tagName("tr")).size();
         Column = Element.findElements(By.tagName("th")).size();// Element.findElements(By.tagName("tr")).get(0).findElements(By.tagName("td")).size();
         for(iterate = 0; iterate< Row;iterate++)
         {
            for(iterate1 = 0; iterate1< Column;iterate1++)
            {
               if(ExpectedText.equalsIgnoreCase(Element.findElements(By.tagName("td")).get(iterate1).getText()))
               {
                  highLightElement(objDriver,Element.findElements(By.tagName("td")).get(iterate1));
                  ExistFlg = true;
                  break;
               }
            }
            if(ExistFlg)
            {
               break;
            }
         }

         if(!ExistFlg)
         {
            if(Desc!="")
            {
               //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc + " : "+ ExpectedText, LogStatus.PASS, true);
            }
         }
         else
         {
            if(Desc!="")
            {
               //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc + " : "+ ExpectedText, LogStatus.FAIL, true);Exception_Content();
            }
         }
      }
   }

   /*
	verify Web Element
	@param  WebElement_Pro : Identify the web element by using this property of the object 
	@param  Desc : Description of the activity
	@return true/false	
	*/
   public static boolean verifyWebElementExist(String WebElement_Pro,String Desc)
   {
      boolean isExist = false;
      if(!WebElement_Pro.matches("NA"))
      {
         //Thread.sleep(1000);
         List<WebElement> Elements = null;
         WebElement Element = null;
         String Actual_Property = WebElement_Pro.substring(6);
         if(getLocatorForExist(WebElement_Pro) == null)
         {
            if(Desc!="")
            {

              ReportLibrary.addStep(ReportLibrary.Test_Step_Number, Desc+"Elememnt is not present: " + WebElement_Pro, LogStatus.FAIL, true);
              //Exception_Content();
            }
         }
         else
         {
            if(getLocatorForExist(WebElement_Pro).isDisplayed())
            {
               isExist=true;
               //ReportLibrary.addStep(ReportLibrary.Test_Step_Number, Desc+"Element : "+WebElement_Pro, LogStatus.PASS, true);
            }
         }

      }
      return  isExist;
  }
   
/*
	verify Web Element
	@param  WebElement_Pro : Identify the web element by using this property of the object 
	@param Event: get event to be perform(Enable/disable)
	@param  Desc : Description of the activity
	@return true/false	
	*/   
   public static boolean verifWebElementEnabled(String WebElement_Pro,String Event,String Desc)
   {
      if(!Event.matches("NA"))
      {
         try {
            Thread.sleep(500);

         }
         catch(Exception e)
        {

         }

        // Wait_For_Div();
         WebElement Element = null;
         Element = getElement(WebElement_Pro);
         if(Element == null)
         {
            if(Desc!="")
            {
               ////ReportLibrary.Add_Step(//ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
            }
         }
         else
         {
            if(Element.isEnabled())
            {
               if(Desc!="")
               {
                 return true;
                  ////ReportLibrary.Add_Step(//ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, true);
               }
            }
            else
            {
               if(Desc!="")
               {
                  return true;
                  ////ReportLibrary.Add_Step(//ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
               }
            }
         }
      }
      return false;
   }

   /*
	High-Light Element
	@param driver : slected driver details
	@param  element : Identify the web element by using this property of the object 
	*/ 	
   public static void highLightElement(WebDriver driver, WebElement element)
   {
      try
      {
         JavascriptExecutor js=(JavascriptExecutor)driver;

         js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);

         try
         {
            Thread.sleep(100);
         }
         catch (InterruptedException e) {
            //ConfigurationLibrary.Sound_Beep();
            System.out.println(e.getMessage());
         }

         js.executeScript("arguments[0].setAttribute('style','border: solid 2px white')", element);
      }
      catch(WebDriverException e)
      {
         //ConfigurationLibrary.Sound_Beep();
      }
   }

   /*
	Click Web Table Data
	@param  WebElement_Pro : Identify the web element by using this property of the object 
	@param ExpectedText: get text to perform event
	@param  Desc : Description of the activity
	@return true/false	
	*/   
   public static void clickWebTableData(String WebElement_Pro,String ExpectedText,String Desc) throws Exception
   {
      if(!ExpectedText.matches("NA"))
      {
         int Row;
         int Column;
         int iterate;
         int iterate1;
         WebElement Element = null;
         WebElement Actual_Element = null;
         Element = getElement(WebElement_Pro);
         List<WebElement> element2 = Element.findElements(By.tagName("tr"));
         Row=element2.size();
         System.out.println(Row);
         //Row = Element.findElements(By.tagName("tr")).size();
         Column = Element.findElements(By.tagName("td")).size();
         System.out.println(Row);
         // Element.findElements(By.tagName("tr")).get(0).findElements(By.tagName("td")).size();
         /*List<WebElement> rows=Element.findElements(By.tagName("tr"));

         for(int rnum=0;rnum<rows.size();rnum++)
         {
         List<WebElement> columns=rows.get(rnum).findElements(By.tagName("td"));
         System.out.println("Number of columns:"+columns.size());
         for(int cnum=0;cnum<columns.size();cnum++)
         {
         System.out.println(columns.get(cnum).getText());
         }
         } */


         for(iterate = 0; iterate< Row;iterate++)
         {
            for(iterate1 = 0; iterate1< Column;iterate1++)
            {
               Actual_Element = Element.findElements(By.tagName("td")).get(iterate1);
               if(Actual_Element.isEnabled())
               {
                  if(ExpectedText.equalsIgnoreCase(Element.findElements(By.tagName("td")).get(iterate1).getText()))
                  {
                     highLightElement(objDriver,Element.findElements(By.tagName("td")).get(iterate1));

                     Actual_Element.click();
                     break;
                  }
               }
            }
         }
         Thread.sleep(2000);
      }
   }
   
    /*
	Scroll Down
	@param  element : Identify the web element by using this property of the object 
	@param  Desc : Description of the activity
	*/
   public static void scrollDown(String Event,String Desc)
   {
      if(!Event.matches("NA"))
      {
         ((JavascriptExecutor) objDriver).executeScript("scroll(0,250);");
      }

   }

   /*
	Scroll Up
	@param  element : Identify the web element by using this property of the object 
	@param  Desc : Description of the activity
	*/
   public static void scrollUp(String Event,String Desc)
   {
      if(!Event.matches("NA"))
      {
         JavascriptExecutor JsE = (JavascriptExecutor)objDriver;
         JsE.executeScript("window.scrollBy(0,-250)", "");
      }
   }
   
/*
	get Row Count
	@param  Table_Element : Identify the web element by using this property of the object 
	@Param Event: 
	@return number of rows in selected table
	*/
   public static int getWebTableRowCount(String Table_Element,String Event,String Webtablename) throws Exception
   {
      int row_cnt=0;
      if(!Event.matches("NA"))
      {
         WebElement Element = getElement(Table_Element);
         row_cnt=Element.findElements(By.tagName("tr")).size();
         if(row_cnt>0)
         {
            //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, "the row count for the webtable "+ Webtablename + " is : "+ row_cnt, LogStatus.PASS, true);
         }

      }
      return row_cnt;
   }

/*
	Scroll Down
	@param  Text_Property : Identify the web element by using this property of the object 
	@param  Desc : Description of the activity
	*/
   public static void scrollDowntoElement(String Text_Property,String Desc) throws InterruptedException{
         WebElement elemnt = getLocatorForExist(Text_Property);
      ((JavascriptExecutor) FunctionLibrary.objDriver).executeScript("arguments[0].scrollIntoView(true);", elemnt);
            
   }
   
    /*
	Scroll Up
	@param  Text_Property : Identify the web element by using this property of the object 
	@param  Desc : Description of the activity
	*/
   public static void scrollUptoElement(String Text_Property,String Desc) throws InterruptedException{
               WebElement elemnt = getLocatorForExist(Text_Property);
            ((JavascriptExecutor) FunctionLibrary.objDriver).executeScript("arguments[0].scrollIntoView(false);", elemnt);
                  
         }

   //Forming 5 digit random number and returning same
   public static int randomNumberWithFiveDigit() {
                  Random r = new Random( System.currentTimeMillis() );
                  return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
              }
   
   /*
	Get web Cell Data
	@param  WebElement_Pro : Identify the web element by using this property of the object 
	@param  rowNumber : Row number where has to get data
	@param  columnNumber : Column number where has to get data
	@param  Desc : Description of the activity
	*/
   public static String getWebTableCellData(String WebElement_Pro,int rowNumber, int columnNumber,String Desc)
   {
      String cellValue = "";
      Boolean verifyStatus = false;
      if(!WebElement_Pro.matches("NA"))
      {

         WebElement rowElement = null;
         WebElement actualCellElement= null;
         //String text = repalceStrings(ExpectedText);
         WebElement elementWebTable = getElement(WebElement_Pro);
         rowElement = elementWebTable.findElements(By.tagName("tr")).get(rowNumber);
         actualCellElement = rowElement.findElements(By.tagName("td")).get(columnNumber);


         if(actualCellElement!=null)
         {

               //highLightElement(ObjDriver,actualCellElement);
             cellValue = actualCellElement.getText();

               /*ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc + " : "+ ExpectedText + ".<br>"
                     +"Actual is: " +actualCellElement.getText(), LogStatus.PASS, true);
*/

         }
         else
         {
            ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Web table cell is not found with row "
                  + "number " + rowNumber + " and column number " + columnNumber, LogStatus.FAIL, true);
         }


      }

      return cellValue;


   }
   
   /*
	verify web Cell Data
	@param  WebElement_Pro : Identify the web element by using this property of the object 
	@param  rowNumber : Row number where has to get data
	@param  columnNumber : Column number where has to get data		
	@param  ExpectedText : 	The text which has to verify
	@param  Desc : Description of the activity
	@param  takeScreenShot : get the screen(true or false)
	*/
   public static boolean verifyWebTableCellData(String WebElement_Pro,int rowNumber, int columnNumber, String ExpectedText,String Desc,Boolean takeScreenShot)
   {
      Boolean verifyStatus = false;
      if(!ExpectedText.matches("NA"))
      {

         WebElement rowElement = null;
         WebElement actualCellElement= null;
       //  String text = repalceStrings(ExpectedText);
         String text = ExpectedText;
         WebElement elementWebTable = getElement(WebElement_Pro);
         rowElement = elementWebTable.findElements(By.tagName("tr")).get(rowNumber);
         actualCellElement = rowElement.findElements(By.tagName("td")).get(columnNumber);
         String cellValue;

         if(actualCellElement!=null)
         {
            if(actualCellElement.getText().toUpperCase().contains(text.toUpperCase()))
            {

               highLightElement(objDriver,actualCellElement);
               verifyStatus = true;
               ReportLibrary.addStep(ReportLibrary.Test_Step_Number, Desc + " : "+ ExpectedText + ".<br>"
                     +"Actual is: " +actualCellElement.getText(), LogStatus.PASS, takeScreenShot);

            }
            else
            {
               ReportLibrary.addStep(ReportLibrary.Test_Step_Number, Desc + " : "+ ExpectedText + ".<br>"
                     +"Actual is: " +actualCellElement.getText(), LogStatus.FAIL, true);
               Exception_Content();

            }

         }
         else
         {
           ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Web table cell is not found with row "
                  + "number " + rowNumber + " and column number " + columnNumber, LogStatus.FAIL, takeScreenShot);
         }


      }

      return verifyStatus;


   }
   
    /*
	get row number using web Cell Data
	@param  WebElement_Pro : Identify the web element by using this property of the object 	
	@param  rowText : 	The text which has to verify
	@param  Desc : Description of the activity
	@param  rowNumber : Row number
	*/
   public static int getRowNumberFromWebTable(String WebElement_Pro,String rowText,String Desc)
   {
      int rowNumber = 0;
      Boolean rowFoundWithText = false;
      if(!WebElement_Pro.matches("NA"))
      {

         WebElement rowElement = null;
         WebElement actualCellElement= null;
         //String text = repalceStrings(ExpectedText);
         WebElement elementWebTable = getElement(WebElement_Pro);
         int noOfRows = elementWebTable.findElements(By.tagName("tr")).size();
         //no of columns in the first row
         int noOfColumns = elementWebTable.findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).size();
         if(elementWebTable!=null)
         {

            for(int iterator = 0; iterator< noOfRows;iterator++)
            {
               rowElement = elementWebTable.findElements(By.tagName("tr")).get(iterator);

               for(int iterator1 = 0; iterator1< noOfColumns;iterator1++)
               {
                  actualCellElement = rowElement.findElements(By.tagName("td")).get(iterator1);
                  if(actualCellElement.getText().toUpperCase().contains(rowText.toUpperCase()))
                  {
                     rowNumber = iterator;
                     rowFoundWithText =true;
                     break;

                  }
               }
               if(rowFoundWithText)
               {
                  break;
               }
            }

         }

      }
      return rowNumber;


   }

   /*
	Wait For Object If Not Present Then Click Previous Element To Make This Object Visible
	@param  propOfCurrentElement : Identify the current web element by using this property of the object 	
	@param  propOfPreviousElement :propertyof web element by using this property of the object 	
	@param  secsWaitForOneInterval : 	time intervals
	@param  totalSecsToWait : total seconds to wait
	*/	
   public static void waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisible(String propOfCurrentElement, 
		   String propOfPreviousElement,int secsWaitForOneInterval, int totalSecsToWait )
   {
      FunctionLibrary.objDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
      //wait object is for the current element
      WebDriverWait wait = new WebDriverWait(FunctionLibrary.objDriver,secsWaitForOneInterval);
      //wait1 object is for the previous element
     // WebDriverWait wait1 = new WebDriverWait(FunctionLibrary.ObjDriver,60);
      int iterations = totalSecsToWait/secsWaitForOneInterval;

      if(propOfCurrentElement.startsWith("xpath"))
      {
         propOfCurrentElement=propOfCurrentElement.split("xpath=")[1];
      }
      if(propOfPreviousElement.startsWith("xpath"))
      {
         propOfPreviousElement=propOfPreviousElement.split("xpath=")[1];
      }

      for(int i=0;i<=iterations;i++)
    	 try
      	{
	         try
	         {
	            System.out.println(DateTime.now() + " looking for object " + propOfCurrentElement);
	            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propOfCurrentElement)));
	            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(propOfCurrentElement)));
	            System.out.println("found element in try " + i);
	            break;
	           } catch(TimeoutException e)
	         {
	            try {
	              // wait.until(ExpectedConditions.elementToBeClickable(By.xpath(propOfPreviousElement)));
	               FunctionLibrary.objDriver.findElement(By.xpath(propOfPreviousElement)).click();
	
	               System.out.println("current object not found. so clicking prevous element after timeout exception "
	                       + propOfPreviousElement);
	            }catch(InvalidElementStateException e1)
	            {/*
	               ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number,"Object is not found: "+ xpathPreviousObjProp
	               , LogStatus.FAIL, true);
	               break;*/
	            }
	            catch(WebDriverException e2)
	            {/*
	               ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number,"Object is not found: "+ xpathPreviousObjProp
	               , LogStatus.FAIL, true);
	               break;*/
	            	
	            }
	            catch(NoSuchElementException e2)
	            {/*
	               ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number,"Object is not found: "+ xpathPreviousObjProp
	               , LogStatus.FAIL, true);
	               break;*/
	            	
	            }
	
	
	
	            //continue;
	         }
	         catch (Exception e)
	         {
	
	            //FunctionLibrary.clickObject(propOfPreviousElement,"","Click object");
	            System.out.print("exception block  is executed");
	         }
	       //  FunctionLibrary.ObjDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
      	}
      catch(Exception e)
      {
    	  FunctionLibrary.objDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);  
      }
      //FunctionLibrary.ObjDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      FunctionLibrary.objDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
   }
   
   /*
	Get Dropdown List Count
	@param  DropDownList_Property : Identify the web element by using this property of the object 	
	@param  return : total elemts present in dropdown
	*/
	public static int getDropdownListCount(String DropDownList_Property) throws Exception
   {
      WebElement Element = null;
      Element = getElement(DropDownList_Property);

      Select DropDownList = new Select(Element);

      List<WebElement> elementCount = DropDownList.getOptions();
      //   System.out.println("Number of items: " + elementCount.size());
      return elementCount.size();
   }

   /*
	Wait For Object If Not Present Then Click Previous Element To Make This Object Visible
	@param  propOfCurrentElement : Identify the current web element by using this property of the object 	
	@param  propOfPreviousElement :propertyof web element by using this property of the object 	
	@param  secsWaitForOneInterval : 	time intervals
	@param  totalSecsToWait : total seconds to wait
	*/
   public static void waitForObjectIfNotPresentThenClickPreviousEleToMakeThisObjVisibleWebapp(String propOfCurrentElement, 
		   String propOfPreviousElement,int secsWaitForOneInterval, int totalSecsToWait )
   {
      WebDriverWait wait1 = new WebDriverWait(FunctionLibrary.objDriver,secsWaitForOneInterval);
      int iterations = totalSecsToWait/secsWaitForOneInterval;
      // wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propOfCurrentElement.split("xpath=")[1])));
      for(int i=0;i<=iterations;i++) {
         try {
            System.out.println(DateTime.now() + "for object " + propOfCurrentElement);
           // wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propOfCurrentElement.split("xpath=")[1])));
            wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(propOfCurrentElement)));
            System.out.println("found element in try " + i);
            break;
          /*  if(FunctionLibrary.ObjDriver.findElement(By.xpath(propOfCurrentElement.split("xpath=")[1].toString())).isDisplayed())
            {
               System.out.println("hello");
               break;
            }
*/
         } catch (TimeoutException e) {
            System.out.println("object not found. then clicking prevous element with timeout exception");
            try{
            FunctionLibrary.objDriver.findElement(By.xpath(propOfPreviousElement)).click();
            }
            catch(Exception e1){
                  System.out.println(e1.getMessage());
                  
            }
            // FunctionLibrary.clickObject(propOfPreviousElement, "", "Click object");
            // FunctionLibrary.clickObject(propOfCurrentElement,"","Click object");


            //continue;
         } catch (Exception e) {

            //FunctionLibrary.clickObject(propOfPreviousElement,"","Click object");
            System.out.print("exception block  is executed");
         }
      }

   }
   
   /*
	Drop Down List Selected Option
	@param  DropDownList_Property : Identify the current web element by using this property of the object 
	@param  return : selected dropdown value
	@param  Desc : Description of the activity
	*/
   public static String dropDownListSelectedOption(String DropDownList_Property,String Desc) throws Exception
   {
   String Actual_Option="";
   if(!DropDownList_Property.matches("NA"))
   {

      WebElement Element = null;
      Element = getElement(DropDownList_Property);
      if(Element == null)
      {
         //ReportLibrary.Add_Step(ReportLibrary.Test_Step_Number, Desc +": "+Expected_Option, LogStatus.FAIL, true);Exception_Content();
      }
      else
      {
         Select DropDownList = new Select(Element);
         Actual_Option = DropDownList.getFirstSelectedOption().getText();

         return Actual_Option;
      }
   }
   return Actual_Option;
}

	 /*
	Get Function Instance
	@param  return : Function Instance
	*/
   public static FunctionLibrary Get_Function_Instance()
   {
      if (Function_Library_Instance == null)
      {
         return Function_Library_Instance = new FunctionLibrary();

      }
      else
      {
         return Function_Library_Instance;
      }
   }

   
   /*
	WebDriver wait By Visiblity of Element Locate By Xpath
	@param  ObjDriver : current object driver 	
	@param  secs : Seconds towait	
	@param  Text_Property : text which has to check for visibility
	*/
   public static void webdrvwaitByVisiblityofElementLocByXpath(WebDriver ObjDriver,int secs,String Text_Property ){

      WebElement elemnt = getLocatorForExist(Text_Property);
      WebDriverWait wait1 = new WebDriverWait(ObjDriver,secs);
      wait1.until(ExpectedConditions.visibilityOf(elemnt));

   }
   /*
	WebDriver wait By Visiblity of Element Locate By Xpath
	@param  ObjDriver : current object driver 	
	@param  secs : Seconds towait	
	@param  Text_Property : text which has to check for clickable
	*/
   public static void webdrvwaitByVisiblityofElementClickableByXpath(WebDriver ObjDriver,int secs,String Text_Property ){
      WebElement elemnt = getLocatorForExist(Text_Property);
      WebDriverWait wait1 = new WebDriverWait(ObjDriver,secs);
      wait1.until(ExpectedConditions.elementToBeClickable(elemnt));
   }
   
   // close all active web driver sessions/browsers 
   public static void closeAllActiveBrowser()
   {
	   if(!CommonLibrary.isClosedAllBrowsers)
	   {
		   FunctionLibrary.objDriver.manage().deleteAllCookies();
	      try{
	        CommonLibrary.sblLoginStatus = "";
	    
	      CommonLibrary.oldBrowser="";
	      Set<String> BrowserList = null;
	      Iterator<String> IteratorList = null;
	      int Browser_Cnt = 0;
	      int i;
	      BrowserList = objDriver.getWindowHandles();
	      Browser_Cnt = BrowserList.size();
	      IteratorList = BrowserList.iterator();
	      String[] WinList = new String[Browser_Cnt];
	      for(i = 0 ; i <Browser_Cnt ; i++)
	      {
	         WinList[i] = IteratorList.next();
	
	      }
	      for(i = Browser_Cnt-1 ; i >=0 ; i--)
	      {
	         //ObjDriver.switchTo().window(WinList[i]).manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			 objDriver.switchTo().window(WinList[i]).manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			// ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Closed the browser", LogStatus.INFO, false);
	         objDriver.quit();
	         
	      }
	      }
	      catch(Exception e)
	      {
	         
	      }
	      CommonLibrary.isClosedAllBrowsers=true;
	   }
   }
   
   // Get current date @param return : Date
   public static String Get_Current_Date()
   {
      DateTime currentDate = new DateTime();
      String D = String.valueOf(currentDate.getDayOfMonth());
      String M = String.valueOf(currentDate.getMonthOfYear());
      String Y = String.valueOf(currentDate.getYear());
      String Date123 = D +"."+M+"."+Y;
      return Date123;
   }

      
/*
	verify Web Element
	@param  WebElement_Pro : Identify the web element by using this property of the object 
	@param Event: get event to be perform(Enable/disable)
	@param  Desc : Description of the activity
	@return number of rows
	*/   
   public static boolean Verify_WebElement_Enabled(String WebElement_Pro,String Event,String Desc)
   {
      if(!Event.matches("NA"))
      {
         try {
            Thread.sleep(500);

         }
         catch(Exception e)
        {

         }

        
         WebElement Element = null;
         Element = getElement(WebElement_Pro);
         if(Element == null)
         {
            if(Desc!="")
            {
               ////ReportLibrary.Add_Step(//ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
            }
         }
         else
         {
            if(Element.isEnabled())
            {
               if(Desc!="")
               {
                 return true;
                  ////ReportLibrary.Add_Step(//ReportLibrary.Test_Step_Number, Desc, LogStatus.PASS, true);
               }
            }
            else
            {
               if(Desc!="")
               {
                  return true;
                  ////ReportLibrary.Add_Step(//ReportLibrary.Test_Step_Number, Desc, LogStatus.FAIL, true);Exception_Content();
               }
            }
         }
      }
      return false;
   }

   /*
	Get row number with two text(two string data)
	@param  WebElement_Pro : Identify the web element by using this property of the object 
	@param rowText1: string data 1
	@param Quantity: string data 2
	@param  Desc : Description of the activity
	@return true/false	
	*/   
   public static int getRowNumberFromWebTableByTwoText(String WebElement_Pro,String rowText1,String Quantity,String Desc)
   {
      int rowNumber = 0;
      Boolean rowFoundWithText = false;
      if(!WebElement_Pro.matches("NA"))
      {
         //int i=0;
         WebElement rowElement = null;
         WebElement actualCellElement= null;
         //String text = repalceStrings(ExpectedText);
         WebElement elementWebTable = getElement(WebElement_Pro);
         int noOfRows = elementWebTable.findElements(By.tagName("tr")).size();
         //no of columns in the first row
         int noOfColumns = elementWebTable.findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).size();
         if(elementWebTable!=null)
         {
           
            for(int iterator = 1; iterator< noOfRows;iterator++)
            {
               rowElement = elementWebTable.findElements(By.tagName("tr")).get(iterator);

               for(int iterator1 = 1; iterator1< noOfColumns;iterator1++)
               {
                  actualCellElement = rowElement.findElements(By.tagName("td")).get(iterator1);
                  if(actualCellElement.getText().toUpperCase().contains(rowText1.toUpperCase()))
                  {
                    
                      for( int i=0;i<noOfColumns;i++)
                      {
                          actualCellElement = rowElement.findElements(By.tagName("td")).get(i);
                          if(actualCellElement.getText().toUpperCase().contains(Quantity.toUpperCase()))
                          {
                                rowNumber = iterator;
                          rowFoundWithText =true;
                          break;
                          }
                      }

                    

                  }
               }
               if(rowFoundWithText)
               {
                  break;
               }
            }

         }

      }
      return rowNumber;


   }

}

