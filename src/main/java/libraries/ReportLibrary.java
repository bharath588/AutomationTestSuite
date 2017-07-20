package libraries;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import features.CustomReport;
//import com.relevantcodes.extentreports.model.ScreenCapture;
//import com.relevantcodes.extentreports.view.ScreenshotHtml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
//import org.joda.time.DateTime;

public class ReportLibrary {
	public static ExtentReports ExtendReport_Instance;
	public static ExtentTest ExtTest_Instance;
	public static int Test_Step_Number;
	public static String Report_Base_Path;
	public static String Report_Suite_Path;
	public static String Report_Suite_HTML_Path;
	public static String Report_Suite_HTMLs_Path;
	public static String Report_Suite_SS_Path;
	public static String Report_Suite_SS_F_Path;
	public static ReportLibrary Report_Library_Instance;
	public static String SS_Folder = null;
	public static Boolean Error_Flag;
	public static File objDirecotry;
	public static String desc;
	String path;
	public static long BStartTime,BEndTime;
	public static int BTpass=0,BTfail=0;
	public static String Btestname;
	public static String BTstatus="PASS";
	public static String BStepName = "Step " ;


	ReportLibrary()
	{

	}
	/*
	report library instance
	@param return : Report Library Instance
	*/
	public static ReportLibrary Get_Report_Library_Instance()
	{

		if(Report_Library_Instance == null)
		{
			return Report_Library_Instance = new ReportLibrary();
		}
		else
		{
			return Report_Library_Instance;
		}
	}

	/*
	extend report instance
	@param Test_Name : Name Of Test
	@param return : Extend Report Instance
	*/
	@SuppressWarnings("deprecation")
	public static ExtentReports getExtends(String Test_Name)
	{
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd hh.mm.ss.S aa");
			String formattedDate = dateFormat.format(new Date()).toString();
			String dateTimeStamp = formattedDate.replace("-","").replace(" ","_").replace(".","_");
			
			if (ExtendReport_Instance == null) {

				Report_Base_Path = ReportLibrary.getPath() + "\\Report";
				ReportLibrary.checkCreateDirectory(Report_Base_Path);
				Report_Suite_Path = Report_Base_Path + "\\Suite";
				ReportLibrary.checkCreateDirectory(Report_Suite_Path);
				Report_Suite_HTML_Path = Report_Suite_Path + "\\Suite_HTML";
				ReportLibrary.checkCreateDirectory(Report_Suite_HTML_Path);
				Report_Suite_HTMLs_Path = Report_Suite_HTML_Path +"\\"+CommonLibrary.testExecutionReportName + "_" +dateTimeStamp;
				Report_Suite_SS_F_Path = Report_Suite_Path + "\\Suite_SS\\Screenshots\\";
				ReportLibrary.checkCreateDirectory(Report_Suite_Path + "\\Suite_SS\\");
				ReportLibrary.checkCreateDirectory(Report_Suite_SS_F_Path);
				ExtendReport_Instance = new ExtentReports(Report_Suite_HTMLs_Path + ".html", true);
				ExtendReport_Instance.addSystemInfo("Chrome", "54.0.0").
						addSystemInfo("Selenium Version", "3.0.1");
				ExtendReport_Instance.assignProject("Author:Boopathi");
				ExtendReport_Instance.config().documentTitle("TEST AUTOMATION REPORT").reportName("UNIFIED PARTNER").reportHeadline("MANAGEMENT TEST AUTOMATION ANALYSIS");
				Error_Flag = false;
			} else {
				return ExtendReport_Instance;
			}

		}
		catch(Exception e)
		{

		}
		return ExtendReport_Instance;
	}
/*
	extend report start
	@param Test_Name : Name Of Test
	@param Test_Description : Description of the activity
	@param return : Extend Report Instance
	*/
	public static ExtentTest startReport(String Test_Name,String Test_Description)
	{

		BStartTime=System.currentTimeMillis();
		Btestname=Test_Name;
		//if (ExtTest_Instance == null)
		//{
		//SS_Folder = Report_Suite_SS_Path+"/" + Function_Library.Get_Function_Instance()
		//.Get_TimeStamp();
		Test_Step_Number = 1;
		ExtTest_Instance = getExtends(Test_Name).startTest(Test_Name, Test_Description);
		//	}
		System.out.println(Test_Name + ExtTest_Instance);
		return ExtTest_Instance;
	}

	public static ExtentTest getReport()
	{
		return ReportLibrary.ExtTest_Instance;
	}

	/*
	Add Step
	@param Step : Step Number
	@param Test_Description : Description of the activity
	@param Status : Log Status
	@param ScreenShot : Screen Shot(true or False)
	*/
	public static void addStep(int Step, String Description, LogStatus Status, boolean ScreenShot)
	{
		//ScreenShot=false;
		desc=Description;
		String StepName = "Step " + Step ;
		BStepName=StepName;
		if(ScreenShot)
		{
			//For KNE Execution-Delete after
			//String path = Report_Suite_SS_F_Path + "//Suite_SS_" +Function_Library.Get_Function_Instance().Get_TimeStamp()+".png";
			System.out.println(Report_Suite_SS_F_Path);
			String screenshotId = FunctionLibrary.Get_Function_Instance().getTimeStamp();
			String path=Report_Suite_SS_F_Path+screenshotId +".png";

			String Bpath="";
			System.out.println(path);
			
			FunctionLibrary.captureScreenShot(path);
			path ="../Suite_SS/Screenshots/"+screenshotId+".png";
			System.out.println(Description);
			System.out.println(Status);
			getReport().log(Status, StepName,Description + getReport().addScreenCapture(path));

			String projectPath = System.getProperty("user.dir");

			Bpath=projectPath + File.separator+"Report\\Suite\\Suite_SS\\Screenshots\\"+screenshotId+".png";

			System.out.println(Status);
			CustomReport.addStep(String.valueOf(Status)+"#"+BStepName+"#"+Description+"#"+Bpath);
			if(!BTstatus.equalsIgnoreCase("FAIL"))
			{
				BTstatus= String.valueOf(Status);BTfail++;
			}

		}
		else
		{	System.out.println(Description);
			if(!BTstatus.equalsIgnoreCase("FAIL"))
			{
				BTstatus= String.valueOf(Status);
			}
			System.out.println(Status);
			getReport().log(Status, StepName, Description);
			System.out.println(Status);
			CustomReport.addStep(String.valueOf(Status)+"#"+BStepName+"#"+Description+"#NA");
		}
		Test_Step_Number = Test_Step_Number + 1;
		ExtendReport_Instance.flush();
	}                           
	
	/* End Test*/
	public static void endTest()
	{
	/*	if(Report_Library.Error_Flag)
		{
			Report_Library.Add_Step(Report_Library.Test_Step_Number, "Fail" , LogStatus.FAIL,false)	;
		}*/

		Test_Step_Number = 1;
		ExtendReport_Instance.endTest(ExtTest_Instance);
		BEndTime=System.currentTimeMillis();
		System.out.println( ExtTest_Instance);

		String etime=CustomReport.formatTimeUnit(BEndTime-BStartTime);
		if(BTstatus.equalsIgnoreCase("info"))
			BTstatus="PASS";
		CustomReport.log(Btestname+"#"+BTstatus+"#Message#"+etime);
	}

	/*
	End the report*/
	public static void endReport()
	{

		ExtendReport_Instance.flush();

		try {

			CustomReport.generateReport();

			File gb_objDirecotry = new File(".");
			String Project_path = gb_objDirecotry.getCanonicalPath();
			FileWriter writer = new FileWriter(Project_path+"/Result_path.txt");
			writer.write("");
			String path=Report_Suite_HTMLs_Path.toString();
			String path2;
			path2=path.substring(62);
			System.out.println(path2);
			writer.write(path2);
			writer.close();

		//	CustomReport.generateReport();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	check directory path
	@param Path : directory path*/
	public static void checkCreateDirectory(String Path)
	{
		File f = new File(Path);
		if(!f.exists())
		{
			f.mkdir();
		}
	}

	/*get directory path
	@param return : diretory path
	*/
	public static String getPath() {
		String Bpath="";
		try {

			objDirecotry = new File(".");
			Bpath = objDirecotry.getCanonicalPath();
		}
		catch (Exception e)
		{

		}
		return Bpath;
	}
}
	