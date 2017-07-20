package objectProperties;

public class SblBoxCheckInCheckOutProperties {
	

    //Properties for Box check-in process

    public static String boxesBoxListTxt="//div[@class='siebui-applet-title' and text()='Box List']";
    public static String boxesBoxNbrTxt=".//*[@id='1_s_2_l_Box_Number']";

    //Properties for check-out under Maintenance tab

    public static String inventoryTab="//*[contains(text(),'Inventory') and @class='ui-tabs-anchor']";
    public static String maintenanceTab="//*[contains(text(),'Maintenance') and @class='ui-tabs-anchor']";
    public static String maintenanceCheckOutTab="//a[text()='Check-Out']";
   // public static String maintenanceCheckInTab="//*[contains(text(),'Check-In') and @class='ui-tabs-anchor']";
    public static String boxCheckOutQueryBtn="//button[@title='Box Checkout Form:Query']";
    public static String boxCheckOutBoxNbrTxtBox="//input[@aria-label='Box Number' and @aria-readonly='false']";
    public static String boxCheckOutToTxtBox="//input[@aria-label='Box Checked Out To']";
    public static String vectorEmployeeFrstNameBtn="//td[@title='Sumit']";
    public static String vectorEmployeeOkBtn="//button[@title='Vector Employee:OK']";
    public static String vectorEmployeePopupOkBtn=".//*[@id='btn-accept']";
    public static String vectorEmployeePopupCancelBtn="//button[@title='Vector Employee:Cancel']";
    public static String boxCheckOutGoBtn="//button[@aria-label='Box Checkout Form:Go']";
    public static String BoxesTab="//*[contains(text(),'Boxes') and @class='ui-tabs-anchor']";

    //Properties for check-in under Maintenance tab

    public static String maintenanceCheckInTab="//*[contains(text(),'Check-In') and @class='ui-tabs-anchor']";
    public static String boxChecInQueryBtn=".//*[@title='Box CheckIn Form:Query']";
    public static String boxCheckInBoxNbrTxtBox="//input[@aria-label='Box Number']";
    public static String boxCheckInGoBtn="//button[@aria-label='Box CheckIn Form:Go']";
    public static String boxAvailableDeviceCountTxt=".//*[@id='1_s_1_l_Available_Device_Count']";
    public static String boxCheckInDeviceCountTxtBox="//input[@aria-label='Check In Device Count']";
    public static String boxStatusTxt=".//*[@aria-label='Box Status']";
    
    public static String boxActualCountTxt=".//*[contains(@id,'_s_3_l_Boxes_Actual_Count') and @role='gridcell']";
    public static String boxNbrTxt="//td[@id='1_s_2_l_Box_Number']";
    public static String boxCheckedOutToTxt="//td[@id='1_s_2_l_Box_Checked_Out_To']";
    public static String boxAgencyNameTxt="//td[@id='1_s_2_l_Agency']";
    public static String boxStoreNameTxt="//td[@id='1_s_2_l_Store']";
    public static String boxStartDeviceNbrTxt="//td[@id='1_s_2_l_Start_Device_Number']";
    public static String boxEndDeviceNbrTxt="//td[@id='1_s_2_l_End_Device_Number']";
    public static String boxDeviceCountTxt="//td[@id='1_s_2_l_Available_Device_Count']";
    public static String boxCheckedOutByTxt="//td[@id='1_s_2_l_Box_Checked_Out_By']";
    public static String boxCurrentDeviceCount="//td[@id='1_s_2_l_Device_Count_Current']";
    public static String boxHistoryTab="//a[contains(text(),'Box  History')]";
    public static String boxHistoryTable="//table[@summary='Box History']";

}
