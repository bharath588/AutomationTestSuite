package objectProperties;


public class SblBatchTagAssignmentProperties {
       
       //Properties for Batch Tag Assignment Parameters tab
       
       public static String inventoryTab="//*[contains(text(),'Inventory') and @class='ui-tabs-anchor']";
       public static String batchTagAssignmenParametersTab="//*[contains(text(),'Batch Tag Assignment Parameters')"
                                                                  + " and @class='ui-tabs-anchor']";
       public static String batchTagAssignmentParameterNewBtn =".//*[@id='s_1_1_12_0_Ctrl']";
       public static String batchTagParameterAccountTypeElement="(.//*[contains(@id,'_s_1_l_Account_Type') "
                                                                  + "and contains(@aria-labelledby,'s_1_l_Account_Type')])[1]";
       public static String batchTagParameterAccountTypeTxtBox=".//*[@name='Account_Type']";
       public static String batchTagParameterAgencyElement=".//*[@id='1_s_1_l_Agency_Name']";
       public static String batchTagParameterAgencyTxtBox="//input[@name='Agency_Name']"; 
       public static String batchTagParameterAgencyOkBtn=".//*[@title='Agency:OK']";
       
       
       public static String batchTagParameterMaxDeviceRqstToProcessElement="(.//*[contains"
                                                                        + "(@id,'_s_1_l_Maximum_Device_Request_to_Process')])[2]";
       public static String batchTagParamaterMaxDeviceRqstToProcessTxtBox=".//*[@id='1_Maximum_Device_Request_to_Process']";
       public static String batchTagParameterMaxDeviceAssgnPerRqstElement="(.//*[contains"
                     + "(@id,'_s_1_l_Maximum_Devices_to_Assign_per_request')])[2]";
       public static String batchTagParameterMaxDeviceAssgnPerRqstTxtBox=".//*[@id='1_Maximum_Devices_to_Assign_per_request']";
       public static String batchTagParameterPrinterNameElement="(.//*[contains(@id,'_s_1_l_Printer_Profile_Name')])[2]";
       public static String batchTagParameterPrinterNameTxtBox="//input[@name='Printer_Profile_Name']";
       public static String batchTagParameterStoreNameElement="(.//*[contains(@id,'1_s_1_l_Store_Name')])";
       public static String batchTagParameterStoreNameApplet="//span[@id='s_1_2_34_0_icon']";
       public static String batchTagParameterStoreOkBtn=".//*[@title='Store Pick Applet:OK']";
       public static String batchTagParameterStoreFindBtn="//button[@title='Store Pick Applet:Find']";
       
       
       public static String batchTagParameterDeviceModelElement="//td[@id='1_s_1_l_Device_Model']";
       public static String batchTagParameterDeviceModelTxtBox="//input[@name='Device_Model']";
       public static String batchTagParameterDeviceModelPickBtn="//button[@title='Vector Device Model PickList:Pick']";
       
       public static String batchTagParameterStartBoxNbrElement="(.//*[contains(@id,'_s_1_l_Starting_Box_ID')])[2]";
       public static String batchTagParameterStartBoxNbrTxtBox="//input[@name='Starting_Box_ID']";
       public static String batchTagParameterEndBoxNbrElement="(.//*[contains(@id,'_s_1_l_End_Box_ID')])[2]";
       public static String batchTagParameterEndBoxNbrTxtBox="//input[@name='End_Box_ID']";
       public static String batchTagParameterSaveBtn="//button[@title='Batch Tag Assignment  Batch List:Save']";
       public static String batchTagParameterQueryBtn="//button[@title='Batch Tag Assignment  Batch List:Query']";
       public static String batchTagParamaterGoBtn="(//button[@title='Batch Tag Assignment  Batch List:Go'])[1]";
       
       //Properties under sitemap
       
       public static String siteMapIcon="//span[@class='siebui-icon-tb-sitemap ToolbarButtonOn glyphicon']";
       public static String siteMapFilterTxtBox=".//*[@id='sitemapFilterInput']";
       public static String serverManagementLink="(//a[contains(text(),'Administration - Server Management')])[2]";
       public static String componentsTab="//*[contains(text(),'Components') and @class='ui-tabs-anchor']";
       public static String jobsTab="//*[contains(text(),'Jobs') and @class='ui-tabs-anchor']";
       public static String submitJobButton="//button[@title='Jobs:Submit Job']";
       public static String cancelBatchRequestBtn="//button[@aria-label='Batch Tag Assignment  Batch List:Cancel Batch Request']";
       public static String totalDeviceProcessedCountTxt="//td[@id='1_s_1_l_Total_Device_Processed_Count']";
       public static String totalDeviceCountTxt="//td[@id='1_s_1_l_Total_Device_Count']";
       public static String popUpAcceptBtn="//button[@id='btn-accept']";
       public static String appletPopup="//div[@name='popup']";
       public static String storeNameTxtBox="//input[@aria-labelledby='PopupQuerySrchspec_Label']";
       public static String startBoxNbrIcon="//span[@id='s_1_2_33_0_icon']";
       public static String boxPickListQueryBtn="//button[@title='Vector Box PickList:Query']";
       public static String boxNameTxtBox="//input[@name='Name']";
       public static String boxPickListGoBtn="//button[@title='Vector Box PickList:Go']";
       public static String boxPickListOkBtn="//button[@title='Vector Box PickList:OK']";
       public static String endBoxNbrTxtBox="//input[@name='End_Box_ID']";
       public static String endBoxNbrIcon="//span[@id='s_1_2_35_0_icon']";
       public static String componentsQueryBtn="//button[@title='Components:Query']";
       public static String serviceFullNameElement=".//*[@id='1_Service_Full_Name']";
       public static String componentsGoBtn="(//button[@title='Components:Go'])[1]";
       public static String componentsStateTxt=".//*[@id='1_s_2_l_State']";
       
       public static String jobNewBtn="//button[@title='Jobs:New']";
       public static String jobNameTxtBox="//input[@id='1_Job_Name']";
       public static String jobStatusTxt="//td[@id='1_s_1_l_Status_Displayed']";
       public static String jobIdTxt="//td[@id='1_s_1_l_Id']";
       public static String jobQueryBtn="//button[@title='Jobs:Query']";
       public static String jobGoBtn="(//button[@title='Jobs:Go'])[1]";
       public static String jobTxtBox="//input[@name='Id']";
       
}

