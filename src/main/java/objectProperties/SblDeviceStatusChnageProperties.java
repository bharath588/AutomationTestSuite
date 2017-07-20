package objectProperties;

public class SblDeviceStatusChnageProperties {
	
	//Properties for Accounts tab
	
	public static String accountsTab="//*[contains(text(),'Accounts') and @class='ui-tabs-anchor']";
	public static String accountNumberTxtBox="//input[@aria-labelledby='CSN_Label' and contains(@aria-label,'Account Number')]";
	public static String goSearchBtn="//button[@title='Search:Go' and contains(@id,'_0_Ctrl')]";
	
	//Properties for Account Info tab
	
	
	public static String accountInfoTab="//*[contains(text(),'Account Info') and @class='ui-tabs-anchor']";
	public static String accountInfoDevicesTab="//*[contains(text(),'Devices') and @class='ui-tabs-anchor']";
	public static String accountInfoDevicesDeviceRequestTab="//*[contains(text(),'Device Request') and @class='ui-tabs-anchor']";
	
	//Properties under DashBoard
	
    public static String noOfDeviceText="//input[@aria-label='# of Devices']";
    
    //Properties under Assigned Device Tab
    
    public static String assignedDeviceTable="//table[@summary='Assigned Device List']";
    public static String firstDeviceNbrTxt="//td[@id='1_s_2_l_Device_Number']";
    public static String assignedDeviceFilterTxtBox="//input[@aria-labelledby='QueryComboBox_Label']";
    public static String assignedDeviceFilterValueTxtBox="//input[@aria-labelledby='QuerySrchSpec_Label']";
    public static String assignedDeviceGoBtn="(//button[@title='Assigned Device List:Go'])[2]";
    public static String deviceStatusTxtElement="//td[@id='1_s_2_l_Device_Status']";
    public static String statusIconPickBtn="//span[contains(@id,'s_2_2_59_0_icon']";
    public static String deviceStatusPickAppletPopUp="//div[@name='popup']";
    public static String deviceStatusPickAppletOkBtn="//button[@title='Device Status PickApplet:Ok']";
    public static String assignedDeviceSaveBtn="//button[@title='Assigned Device List:Save']";
    public static String deviceStatusTxtBox="//input[@name='Device_Status']";
    
    public static String assignedDevicesQueryBtn="//button[@title='Assigned Device List:Query']";
    public static String deviceNbrTxtBox="//input[@name='Device_Number']";
    public static String deviceNbrGoBtn="(//button[@title='Assigned Device List:Go'])[1]";
    
    //Properties under device history tab
    
    public static String deviceHistoryTab="//*[contains(text(),'Device History') and @class='ui-tabs-anchor']";
    public static String deviceHistoryTable="//table[@summary='Vector Account Device History List']";
    public static String deviceHistoryQueryBtn="//button[@title='Vector Account Device History List:Query']";
    public static String deviceHistoryDeviceNbrElement="//td[@id='1_s_2_l_Device_Number']";
    public static String deviceHistoryDeviceNbrTxtBox="//input[@name='Device_Number']";
    public static String deviceHistoryGoBtn="//button[@title='Vector Account Device History List:Go']";
    public static String deviceHistoryDeviceStatusField="//td[@id='1_s_2_l_Device_Status']";
    
    //Properties under Notes tab	
    
    public static String notesTab="//*[contains(text(),'Notes') and @class='ui-tabs-anchor']";
    		
   //Properties under Non-Financial History tab
    
    public static String hiddenIconDropDown="//select[@aria-label='Second Level View Bar']";
    public static String nonFinancialsHistoryTab="//*[contains(text(),'Non Financials') and @class='ui-tabs-anchor']";
    public static String nonFinancialQueryBtn="//button[@title='Activities:Query']";
    public static String nonFinancialSubCategoryTxtBox="(//input[@aria-label='Sub Category'])[1]";
    public static String nonFinancialGoBtn="//button[@title='Activities:Go']";
    public static String nonFinancialCategoryTxt="//td[@id='1_s_3_l_Class']";
    
    //Properties under boxes tab
    
    public static String boxesTabQueryBtn="//button[@title='Box List:Query']";
    public static String searchForBoxElement=".//*[@id='1_s_2_l_Box_Number']";
    public static String searchForBoxTxtBox="//input[@id='1_Box_Number']";
    public static String boxSearchGoBtn="//button[@title='Box List:Go']";
    
     // Properties under Devices tab 
         		
    public static String devicesTab="//*[contains(text(),'Devices') and @class='ui-tabs-anchor']";
    public static String devicesQueryBtn="//button[@title='Devices List:Query']";
    public static String deviceSearchBoxElement=".//*[@id='1_s_2_l_Device_Number']";
    public static String deviceSearchBoxElement1=".//*[@id='1_s_1_l_Device_Number']";
    public static String deviceSearchTxtBox="//input[@name='Device_Number']";
    public static String deviceSearchGoBtn="//button[@title='Devices List:Go']";
    public static String deviceStatusChangeTxtBox="//input[@aria-label='Device Status']";
    
      // Properties under Devices Entry Form tab 
    
    public static String deviceReturnsTab="//*[contains(text(),'Device Returns') and @class='ui-tabs-anchor']";
    public static String deviceReturnsQueryBtn="//button[@aria-label='Device Entry:Query']";
    public static String deviceNumberTxtBox="//input[@aria-label='Device Number']";
    public static String deviceReturnsGoBtn="//button[@title='Device Entry:Go']";
    public static String miscExpiredBoxListTable="//table[@summary='Misc & Expired Box List']";
    
    
    // Properties under Devices Returns tab 
    
    public static String deviceEntryFormTab="//*[contains(text(),'Device Entry Form') and @class='ui-tabs-anchor']";
    public static String deviceEntryQueryBtn="//button[@title='Device Entry:Query']";
    public static String deviceEntryDeviceNbrTxtBox="//input[@aria-label='Device Number']";
    public static String deviceEntryGoBtn="//button[@title='Device Entry:Go']";
    public static String boxListTable="//table[@summary='Box List']";
    public static String plansTab="//a[contains(text(),'Plans')]";
    public static String planDetailsTable="//table[@summary='Plan Details List']";
    public static String enrollMDXBtn="//*[@aria-label='Enroll MDX']//following::span[2]";
    
    
    public static String assignedDevicesTable = "//table[@summary='Assigned Device List']";
    public static String deviceListTable="//table[@summary='Device List']";
    public static String deviceCountInBoxTxt="//td[@id='1_s_2_l_Device_Count_Current']";
    public static String deviceListQueryBtn="//button[@title='Devices List:Query']";
    public static String deviceStatusTxt="//td[@id='1_s_2_l_Device_Status']";
    public static String deviceEntryCancelBtn="//button[@title='Device Entry:Cancel']";
    public static String boxNbrTxt=".//*[@id='1_s_2_l_Box_Number']";
    public static String boxTypeTxt=".//*[@id='1_s_2_l_Box_Type']";
    public static String boxListGoBtn="//button[@title='Box List:Go']";
    public static String deviceNbrTxt="//td[@id='1_s_1_l_Device_Number']";
    public static String noOfDevicesInDashboardTxt="//input[@aria-label='# of Devices']";
    public static String assignedDeviceListQueryBtn="//button[@title='Assigned Device List:Query']";
    public static String historyTab="//a[text()='History']";
    public static String activitiesTable="//table[@summary='Activities']";
    public static String notesListTable="//table[@summary='Notes List']";
    public static String notesDescTxt="//td[@id='1_s_2_l_Comment']";
    public static String deviceListGoBtn="//button[@title='Devices List:Go']";

}
