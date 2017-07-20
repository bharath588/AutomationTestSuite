package objectProperties;
public class SblRMAProcessProperties {
	
	public static String boxCreationTab="//*[contains(text(),'Box Creation') and @class='ui-tabs-anchor']";
	
	//Properties of Box Transfer Tab
	public static String expiredRMABoxTab="//*[contains(text(),'RMA/Expired Box') and @class='ui-tabs-anchor']";
	public static String returnDeviceNbrTxtBox="//input[@aria-labelledby='Device_Number_Label' and @aria-readonly='false']";
	public static String boxTransferTab="//*[contains(text(),'Box Transfers') and @class='ui-tabs-anchor']";
	public static String boxTransferNewBtn="//button[@title='Send Shipment List:New']";
	public static String boxTransferToStoreSearchBtn=".//*[@id='s_1_2_24_0_icon']";
	public static String storeNameSearchTxtBox="//input[@aria-labelledby='PopupQuerySrchspec_Label']";
	public static String storeNameFindBtn="//button[@title='Store Pick Applet:Find']";
	public static String startBoxNbrElement=".//*[@id='1_s_1_l_Start_Box_Number']";
	public static String startBoxNbrTxtBox=".//*[@id='1_Start_Box_Number']";
	public static String endBoxNbrElement=".//*[@id='1_s_1_l_End_Box_Number']";
	public static String endBoxNbrTxtBox=".//*[@id='1_End_Box_Number']";
	public static String courierTrackingNbrElement=".//*[@id='1_s_1_l_Courier_Tracking_Number']";
	public static String courierTrackingNbrTxtBox=".//*[@id='1_Courier_Tracking_Number']";
	public static String courierNameElement=".//*[@id='1_s_1_l_Courier_Name']";
	public static String courierNameTxtBox=".//*[@id='1_Courier_Name']";
	public static String sendShipmentSaveBtn="//button[@title='Send Shipment List:Save']";
	public static String sendShipmentBtn="//button[@title='Send Shipment List:Send Shipment']";
	
	//Properties of Boxes tab
	
	public static String boxesTab="//*[contains(text(),'Boxes') and @class='ui-tabs-anchor']";
	public static String receiveShipmentTab="//*[contains(text(),'Receive Shipment') and @class='ui-tabs-anchor']";
	public static String receiveShipmentBoxCountElement=".//*[@id='1_s_2_l_Box_Received_Count']";
	public static String receiveShipmentBoxCountTxtBox=".//*[@id='1_Box_Received_Count']";
	public static String toolsTab="//span[@class='ui-button-text' and contains(text(),'Tools')]";
	public static String userPreferenceTab="//a[@href='javascript:void(0)' and contains(text(),'User Preferences')]";
	
	//Properties under Retailer tab
	
	public static String retailerTab="//*[contains(text(),'Retailer') and @class='ui-tabs-anchor']";
	public static String retailerReturnsTab="//*[contains(text(),'Retailer Returns') and @class='ui-tabs-anchor']";
	public static String retailerQueryBtn="(//button[@title='Retailer Accounts:Query'])[1]";
	public static String retailerAccountNameElement="//td[@id='1_s_2_l_Account_Name']";
	public static String retailerAccountNameTxtBox="//input[@name='Account_Name']";
	public static String retailerAccountNameLink="//a[@name='Account_Name']";
	public static String retailerGoBtn="(//button[@title='Retailer Accounts:Go'])[1]";
	public static String retailerAccountTable="//table[@summary='Retailer Accounts']";
	public static String retailerDeviceListTable="//table[@summary='All Device List']";
	
	//Properties under Retail Tag Search tab
	
	public static String retailTagSerachTab="//*[contains(text(),'Retail Tag Search') and @class='ui-tabs-anchor']";
	public static String retailTagDeviceNbrTxtBox="//input[@aria-label='Device Number']";
	public static String addToExistingAccountBtn ="//button[@title='Retail Tag Registration:Add To Existing Account']";
	public static String accountNbrTxtBox="//input[@aria-label='Account Number']";
	public static String transferDeviceBtn="//button[@title='Retail Tag Addition:Transfer Device']";
	
	//Proprerties under Service Request Tab
	
	//public static String serviceRequestTab="//*[@aria-label='Service Request Selected']";
	public static String serviceRequestsTab="(//*[contains(text(),'Service Request') and @class='ui-tabs-anchor'])[2]";
	public static String serviceRequestTable="//table[@summary='Service Request List']";
	public static String serviceRequestAccountSearchGoBtn="//button[@title='Search:Go']";
	public static String serviceRequestNewBtn="//button[@title='Service Requests:New']";
	public static String statusOpenField="//td[@id='1_s_3_l_SR_Status' and @title='Open']";
	public static String serviceRequestQueueTxtBox="//input[@aria-label='Queue']";
	public static String serviceRequestSubQueueTxtBox="//input[@aria-label='Sub Queue']";
	public static String serviceRequestDescriptionTextBox="//textarea[@aria-labelledby='Description_Label']";
	public static String serviceRequestQueryBtn="//button[@title='Service Requests:Query']";
	public static String serviceRequestNbrElement="//td[@id='1_s_3_l_SR_Number']";
	public static String serviceRequestNbrTxtBox="//input[@name='SR_Number']";
	public static String serviceRequestGoBtn="//button[@title='Service Requests:Go']";
	public static String serviceRequestStatusTxtBox="//input[@aria-label='SR Status']";
	public static String serviceRequestClosureCodeTxtBox="//input[@aria-label='Closure Code']";
	
	public static String deviceNbrTxt="//td[@id='1_s_2_l_Device_Status']";
	public static String welcomeTxt="(.//div[@class='Welcome'])[1]";
	
	public static String receiveShipmentQueryBtn="//button[@title='Receive Shipment List:Query']";
	public static String shipmentNbrTxtBox=".//*[@id='1_Shipment_Number']";
	public static String receiveShipmentGoBtn="(//button[@title='Receive Shipment List:Go'])[1]";
	public static String receiveShipmentSaveBtn="//button[@title='Receive Shipment List:Save']";
	public static String popUpAcceptBtn="//button[@id='btn-accept']";
	public static String shipmentStatusTxt="//td[@id='1_s_2_l_Status']";
	public static String replacementElement=".//*[@id='1_s_2_l_Replacement_Required']";
	public static String replacementBtn=".//*[@id='Replacement_Required']";
	public static String deviceRequestListTable="//table[@summary='Device Request List']";
	public static String deviceEntryQueryBtn="//button[@title='Device Entry:Query']";
	public static String deviceEntryGoBtn="//button[@title='Device Entry:Go']";
	public static String boxNbrTxt=".//*[@id='1_s_1_l_Box_Number']";
	public static String boxNbrTxtBox="//input[@aria-label='Box Number']";
	public static String checkInGoBtn="(//button[@title='Box List for Check In:Go'])[1]";
	public static String shipmentReceiveStatusTxt=".//*[@id='1_s_1_l_Status']";
	public static String shipmentNbrTxt=".//*[@id='1_s_1_l_Shipment_Number']";
	public static String deviceStatusTxt="//td[@id='1_s_2_l_Device_Status']";
	public static String deviceNbrElement="//td[@id='1_s_1_l_Device_Number']";
	
	public static String assignedDeviceListSaveBtn="//button[@title='Assigned Device List:Save']";
	public static String deviceStatusRETURNDEFTxt="//td[@id='1_s_2_l_Device_Status' and @title='RETURNDEF']";
	public static String notesListTable="//table[@summary='Notes List']";
	public static String notesDescTxt="//td[@id='1_s_2_l_Comment']";
	public static String deviceReplaceTxt="//td[@id='1_s_3_l_Area' and @title='DEVICE_REPLACE']";
	public static String srNbrTxt="//td[@id='1_s_3_l_SR_Number']";
	public static String srStatusTxt="//td[@id='1_s_3_l_SR_Status']";
	public static String srClosedStatusTxt="//td[@id='1_s_3_l_SR_Status' and @title='Closed']";
	
	public static String boxListGoBtn="//button[@title='Box List:Go']";
	public static String boxCheckedOutToTxt=".//*[@id='1_s_2_l_Box_Checked_Out_To']";
	public static String boxStatusTxt=".//*[@id='1_s_2_l_Box_Status']";
	public static String boxStoreName=".//*[@id='1_s_2_l_Store']";
	public static String deviceCountInBoxTxt=".//*[@id='1_s_2_l_Device_Count_Current']";
	public static String availableDeviceInBoxTxt=".//*[@id='1_s_2_l_Available_Device_Count']";
	public static String vectorBoxCountTxt="//td[@id='1_s_1_l_Vector_Box_Count']";
	

}
