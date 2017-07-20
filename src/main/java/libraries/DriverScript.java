package libraries;

import com.relevantcodes.extentreports.LogStatus;

import java.lang.reflect.Method;

import org.openqa.selenium.io.TemporaryFilesystem;

import static libraries.CommonLibrary.getSettingsSheetInfo;

public class DriverScript {
	
	public static String Str_TC_Name="";
	public static String Str_TC_ExecutionStatus;
	public static void main(String args[]) throws Exception
	{
		
		int testScenariosCount = 0;
		int iterator1;
		String TD_Config_Excel;
		String TC_Sheet;
		TD_Config_Excel = "TestFeatures_Selector.xls";
		TC_Sheet = "TestCase_Configuration";
		CommonLibrary.settingsSheetInfo=getSettingsSheetInfo();

		ConfigurationLibrary.Create_New_Instance = true;
		testScenariosCount = ConfigurationLibrary.getSheetRowCount(TD_Config_Excel,TC_Sheet);
		ReportLibrary.Get_Report_Library_Instance();

		ConfigurationLibrary.Create_New_Instance = false;

		for (iterator1 = 0; iterator1 <= (testScenariosCount); iterator1++)
		{
			ConfigurationLibrary.Create_New_Instance = false;
			Str_TC_Name = ConfigurationLibrary.getCellValue(TD_Config_Excel, TC_Sheet,iterator1, 1);
			Str_TC_ExecutionStatus = ConfigurationLibrary.getCellValue(TD_Config_Excel, TC_Sheet,iterator1, 2);
			
			if (Str_TC_ExecutionStatus.trim().toLowerCase().equals("yes"))
			{
					    
				ReportLibrary.startReport(Str_TC_Name, Str_TC_Name);
				try {

					Class<?> c = Class.forName("features."+Str_TC_Name);
					Method m = c.getMethod(Str_TC_Name+"Test");
					 
					TestCaseLibrary.Get_TestCase_Instance().executeTC(m);
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					ReportLibrary.addStep(ReportLibrary.Test_Step_Number,"Test is not found", LogStatus.FAIL,false);
				}
				System.out.println("Test case execution is done: "+Str_TC_Name);
				//FunctionLibrary.objDriver.quit();

				ReportLibrary.endTest();
				ReportLibrary.endReport();

			}
		}
		TemporaryFilesystem.getDefaultTmpFS().deleteTemporaryFiles();
}	


}
