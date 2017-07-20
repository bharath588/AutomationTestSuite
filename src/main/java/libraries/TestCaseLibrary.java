package libraries;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.relevantcodes.extentreports.LogStatus;

public class TestCaseLibrary extends FunctionLibrary        //UPM_Object_Mapping_Library
{
	
	public static TestCaseLibrary TestCase_Library_Instance;
	TestCaseLibrary()
	{
		
	}
	/*Test case instance
	@param return : TestCase Library Instance
	*/
	public static TestCaseLibrary Get_TestCase_Instance()
	{
		if(TestCase_Library_Instance == null)
		{
			return TestCase_Library_Instance = new TestCaseLibrary();
		}
		else
		{
			return TestCase_Library_Instance;
		}
	}
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	public String TC_N;
	/*Test case Execution
	@param TC_Name : Name Of Test case
	*/
	public void executeTC(Method TC_Name) throws NoSuchMethodException,

	SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, Exception
	{
		try
		{

			TC_Name.invoke(this);
		}
		catch(InvocationTargetException e)
		{
			e.printStackTrace();
			ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Test is failed", LogStatus.FAIL, true);
			FunctionLibrary.closeAllActiveBrowser();
			
		}
		
		catch (Exception e)
		{
			ReportLibrary.addStep(ReportLibrary.Test_Step_Number, "Test is failed. "+e.getMessage(), LogStatus.FAIL, true);
			FunctionLibrary.closeAllActiveBrowser();
		}
	}

}
