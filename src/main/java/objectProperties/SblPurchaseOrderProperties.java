package objectProperties;


/**
 * Created by 31586 on 15-12-2016.
 */
public class SblPurchaseOrderProperties {

    //Properties for logging in to the Siebel application

    public static String siebelURL="http://10.36.20.4/callcenter_enu/start.swe?";
    public static String loginUsernameTxtBox="//input[@id='s_swepi_1']";
    public static String loginPasswordTxtBox="//input[@id='s_swepi_2']";
    public static String signInBtn="//input[@id='s_swepi_22']";
    public static String homePageVerificationTxt="(.//div[@class='Welcome'])[1]";
    
    //Properties for logout button
    
    public static String logoutSettingImage="//li[@id='tb_0']";
    public static String logoutButton="//span[@class='siebui-icon-logout']";

    //Properties for Purchase Order creation

    public static String inventoryTab="//*[contains(text(),'Inventory') and @class='ui-tabs-anchor']";
    public static String purchaseOrderTab="//*[contains(text(),'Purchase Order') and @class='ui-tabs-anchor']";
    public static String purchaseOrdersNewBtn=".//*[@title='Purchase Orders:New']";
    public static String purchaseOrdersOrderNbrTxtBox="//input[@name='Order_Number']";
    public static String purchaseOrderAgencyElement=".//*[@id='1_s_1_l_Agency_Short_Name']";
    public static String purchaseOrderAgencyTxtBox="(//input[@name='Agency_Short_Name'])";
    public static String purchaseOrderStoreElement=".//*[@id='1_s_1_l_Store']";
    public static String purchaseOrderStoreTxtBox="(//input[@name='Store'])";
    public static String purchaseOrderSaveBtn=".//*[@title='Purchase Orders:Save']";
    public static String purchaseOrderNewStatusTxt=".//*[@id='1_s_1_l_Status' and @title='NEW']";
    public static String purchaseOrderReceiveStatusTxt=".//*[@id='1_s_1_l_Status']";
    public static String poActualBoxCount=".//*[contains(@id,'_Boxes_Actual_Count') and contains(@aria-labelledby,'_Boxes_Actual_Count')]";
    public static String poDeviceOrderedCountTxt="//td[@id='1_s_1_l_Ordered_Count']";
    public static String poDeviceReceivedCountTxt="//td[@id='1_s_1_l_Received_Count']";
    public static String poNumberTxt=".//*[@id='1_s_1_l_Order_Number']/a";
    public static String procurementCostPOTxt=".//*[@id='1_s_1_l_Procurement_Cost']";
    public static String poQueryBtn="//button[@title='Purchase Orders:Query']";
    public static String poGoBtn="//button[@title='Purchase Orders:Go']";
    public static String orderNbrTxtBox="//input[@name='Order_Number']";
    
    

    //Properties for PO Line Items while creating manually

    public static String poLineItemNewBtn=".//*[@title='PO Line Items:New']";
    public static String poDeviceModelTxtBox="//input[@name='Device_Model_Number']";
    public static String poLineItemOrderCountElement=".//*[@id='1_s_3_l_Devices_Ordered_Count']";
    public static String poLineItemOrderCountTxtBox=".//*[@name='Devices_Ordered_Count']";
    public static String poLineItemStartDeviceNoElement=".//*[@id='1_s_3_l_Start_Device_Number']";
    public static String poLineItemStartDeviceNoTxtBox=".//*[@name='Start_Device_Number']";
    public static String poLineItemEndDeviceNoElement=".//*[@id='1_s_3_l_End_Device_Number']";
    public static String poLineItemDOBElement=".//*[@id='1_s_3_l_Vector_Device_DOB']";
    public static String poLineItemDOBTxtBox="//input[@name='Vector_Device_DOB']";
    public static String poLineItemActualDeviceCostElement=".//*[@id='1_s_3_l_Vector_Actual_Device_Cost']";
    public static String poLineItemActualDeviceCostTxtBox="//input[@name='Vector_Actual_Device_Cost']";
    public static String poLineItemSaveBtn=".//*[@title='PO Line Items:Save']";


    //Properties for PO Line Items while Importing File


    public static String poLineItemImportTransponderBtn="//button[@title='PO Line Items:Import Transponder']";
    public static String poLineItemBrowseBox="//input[@name='s_5_1_50_0']";
    public static String poLineItemImportBtn="//button[@title='Vector Inventory Import:Import']";
    //public static String importDocumentPath="D://work//data.txt";

    //Properties for PO Line Item Receipts

    public static String poLineItemReceiptsNewBtn=".//*[@title='PO Line Item Receipts:New' and contains(@id,'s_2_1_13_0_Ctrl')"
    		+ "]";
    //public static String poLineItemReceiptQtyPerBoxElement=".//*[@aria-labelledby='s_2_l_Quantity_Per_Box s_2_l_altCombo']";
    public static String poLineItemReceiptQtyPerBoxDropDownBtn=".//*[@id='1_Quantity_Per_Box']";
    //public static String poLineItemReceiptQtyPerBoxValue=".//*[@data-lovtype='Vector PO Line Item Receipt List Applet:Quantity Per Box' and contains(text(),'30')]";
    public static String poLineItemReceiptSaveBtn=".//*[@title='PO Line Item Receipts:Save']";
    public static String poLineItemReceiptReceiveBtn=".//*[@title='PO Line Item Receipts:Receive']";
    public static String poLineItemReceiptStatusTxt=".//*[@id='1_s_2_l_Receipt_Status']";
    public static String poLineItemStatusTxt=".//*[@id='1_s_3_l_Status']";



    //Properties for Box check-in process

    public static String boxesBoxListTxt="//div[@class='siebui-applet-title' and text()='Box List']";
    public static String boxesBoxNbrTxt=".//*[@id='1_s_2_l_Box_Number']";

    //Properties for check-out under Maintenance tab

    public static String maintenanceTab="//*[contains(text(),'Maintenance') and @class='ui-tabs-anchor']";
    public static String maintenanceCheckOutTab="//*[contains(text(),'Check-Out') and @class='ui-tabs-anchor']";
    public static String boxCheckOutQueryBtn=".//*[@title='Box Checkout Form:Query']";
    public static String boxCheckOutBoxNbrTxtBox="//input[@aria-label='Box Number']";
    public static String boxCheckOutToLookupBtn=".//*[@id='s_1_1_12_0_icon']";
    public static String vectorEmployeeFrstNameBtn="//td[@title='Sumit']";
    public static String vectorEmployeeOkBtn="//button[@title='Vector Employee:OK']";
    public static String vectorEmployeePopupOkBtn=".//*[@id='btn-accept']";
    public static String vectorEmployeePopupCancelBtn="//button[@title='Vector Employee:Cancel']";
    public static String boxCheckOutGoBtn="//button[@aria-label='Box Checkout Form:Go']";

    //Properties for check-in under Maintenance tab

    public static String maintenanceCheckInTab="//*[contains(text(),'Check-In') and @class='ui-tabs-anchor']";
    public static String boxChecInQueryBtn=".//*[@title='Box CheckIn Form:Query']";
    public static String boxCheckInBoxNbrTxtBox="//input[@aria-label='Box Number']";
    public static String boxCheckInGoBtn="//button[@aria-label='Box CheckIn Form:Go']";
    public static String boxAvailableDeviceCountTxt=".//*[@id='1_s_1_l_Available_Device_Count']";
    public static String boxCheckInDeviceCountTxtBox="//input[@aria-label='Check In Device Count']";

    
    //Propreties for System Account
    
    public static String vectorBusinessRuleLink="(//a[contains(text(),'Vector Business Rules')])[2]";
    public static String vectorInternalProcessParameterTab="//*[contains(text(),'Vector Internal Process Parameters')]";
    public static String processParameterQueryBtn="//button[@title='Vector Process Parameters:Query']";
    public static String processParameterTypeTxtBox="//input[@name='Type']";
    public static String processParameterGoBtn="(//button[@title='Vector Process Parameters:Go'])[1]";
    public static String financialsTab="//*[contains(text(),'Financials') and @class='ui-tabs-anchor']";
    public static String reversalsTab="//*[contains(text(),'Reversals') and @class='ui-tabs-anchor']";
    public static String reversalQueryBtn="//button[@title='Financial Transaction List:Query']";
    public static String reversalGoBtn="(//button[@title='Financial Transaction List:Go'])[1]";
    public static String payTypeElement="//td[@id='1_s_1_l_Pay_Type']";
    public static String payTypeTxtBox="//input[@name='Pay_Type']";
    
    
    public static String currentPOStatusTxt="//td[@id='1_s_1_l_Status']";
   // public static String orderCountAfterPOReceiveValue="//td[@id='1_s_1_l_Ordered_Count']";
   // public static String receiveCountValue="//td[@id='1_s_1_l_Received_Count']";
    public static String deviceListTable="//table[@summary='Device List']";
    public static String boxReceivedCountValue=".//*[contains(@id,'_s_2_l_Box_Number') and @role='gridcell']";
    public static String parameterValueTxt="//td[@id='1_s_1_l_Param_Value']";
    public static String reversalCategoryTxt="//td[@id='1_s_3_l_Category']";
    public static String reversalSubCategoryTxt="//td[@id='1_s_3_l_Sub_Category']";
    public static String financialTransactionListTable="//table[@summary='Financial Transaction List']";
    public static String transactionAmountUnderReversalTxt="//td[@id='1_s_1_l_Amount']";
    public static String transponderImportBtn="//button[@title='PO Line Items:Import Transponder']";
    public static String browseImportFileTxtBox="//input[contains(@class,'siebui-ctrl-file')]";
    public static String importTransponderBtn="//button[@title='Vector Inventory Import:Import']";
    public static String poReceivedFullStatusTxt="//td[@id='1_s_2_l_Receipt_Status' and @title='RECEIVED FULL']";
    
    public static String lineItemLineNbrTxt="//td[@id='1_s_3_l_Line_Number']";
    public static String lineItemStatusTxt="//td[@id='1_s_3_l_Status']";
    public static String lineItemAgencyTxt="//td[@id='1_s_3_l_Agency']";
    public static String lineItemStartDeviceNbrTxt="//td[@id='1_s_3_l_Start_Device_Number']";
    public static String lineItemEndDeviceNbrTxt="//td[@id='1_s_3_l_End_Device_Number']";
    public static String lineItemBoxCountTxt="//td[@id='1_s_3_l_Boxes_Actual_Count']";
    public static String lineItemVehicleClassTxt="//td[@id='1_s_3_l_Vehicle_Class']";
    public static String lineItemDeviceCodeTxt="//td[@id='1_s_3_l_Device_Code_Bit1']";
    public static String lineItemDeviceUnitPriveTxt="//td[@id='1_s_3_l_Device_Unit_Price']";
    public static String lineItemDeviceMountTypeTxt="//td[@id='1_s_3_l_Mount_Type']";
    public static String lineItemDeviceColorTxt="//td[@id='1_s_3_l_Device_Color']";
    public static String lineItemDeviceTypeTxt="//td[@id='1_s_3_l_Device_Type']";
    public static String poLineItemNewStatus="//td[contains(@aria-labelledby,'s_3_l_altCombo') and @title='NEW']";
    public static String poLineItemPartialReceiveStatus="//td[contains(@aria-labelledby,'s_3_l_altCombo') and @title='PARTIALLY RECEIVED']";
    public static String lineItemReceiptStatusTxt="//td[@id='1_s_2_l_Receipt_Status']";
    public static String lineItemReceiptQtyPerBoxTxt="//td[@id='1_s_2_l_Quantity_Per_Box']";
    public static String lineItemReceiptBoxReceivedTxt="//td[@id='1_s_2_l_Actual_Number_of_Boxes_Recieved']";
    public static String lineItemReceiptStartDeviceNbrTxt="//td[@id='1_s_2_l_Actual_received_Start_Device_Number']";
    public static String lineItemReceiptEndDeviceNbrTxt="//td[@id='1_s_2_l_Actual_Received_End_Device_Number']";
    public static String lineItemReceiptDevicesReceivedTxt="//td[@id='1_s_2_l_Actual_Number_of_Devices']";
    public static String lineItemReceiptReceivedDateTxt="//td[@id='1_s_2_l_Received_DateTime']";
    public static String lineItemReceiptNbrTxt="//td[@id='1_s_2_l_Receipt_Number']";
    public static String procurementCostPerLineItemElement="//td[contains(@id,'Vector_Line_Procurement_Cost')]";
    public static String lineItemReceiptNewElement="//td[@id='1_s_2_l_Receipt_Number']";
    public static String lineItemNewElement="(//td[contains(@aria-labelledby,'s_3_l_altCombo') and @title='NEW'])[1]";
    public static String lineItemReceiveBtn=".//*[@title='PO Line Item Receipts:Receive']";
    
    public static String boxesTabBoxNbrTxt="//td[@id='1_s_2_l_Box_Number']";
    public static String boxesTabBoxTypeTxt="//td[@id='1_s_2_l_Box_Type']";
    public static String boxesTabBoxStatusTxt="//td[@id='1_s_2_l_Box_Status']";
    public static String boxesTabDeviceCharTxt="//td[@id='1_s_2_l_Device_Characteristics']";
    public static String boxesTabStartDeviceNbrTxt="//td[@id='1_s_2_l_Start_Device_Number']";
    public static String boxesTabBoxDeviceCountTxt="//td[@id='1_s_2_l_Device_Count_Current']";
    public static String boxesTabDeviceModelTxt="//td[@id='1_s_2_l_Device_Model']";
    public static String boxesTabDeviceStatusTxt="//td[@id='1_s_1_l_Device_Status']";
    public static String boxesTabDOBTxt="//td[@id='1_s_1_l_Device_DOB']";
    public static String boxesTabManufacturerDateTxt="//td[@id='1_s_1_l_Warranty_in_Months']";
    public static String boxesTabStoreNameTxt="//td[@id='1_s_1_l_Store']";
    public static String procurementCostForPOTxt="//td[@id='1_s_1_l_Procurement_Cost']";
   // public static String poStatusTxt="//input[@name='Status']";
   
    
}

