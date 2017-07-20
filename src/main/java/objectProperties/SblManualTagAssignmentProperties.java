package objectProperties;

public class SblManualTagAssignmentProperties {
	
	//Properties for Accounts tab
	
	public static String accountsTab="//*[contains(text(),'Accounts') and @class='ui-tabs-anchor']";
	public static String accountNumberTxtBox="//input[@aria-labelledby='CSN_Label' and contains(@aria-label,'Account Number')]";
	public static String goSearchBtn="//button[@title='Search:Go' and contains(@id,'_0_Ctrl')]";
	
	//Properties for Account Info tab
	
	
	public static String accountInfoTab="//*[contains(text(),'Account Info') and @class='ui-tabs-anchor']";
	public static String accountInfoDevicesTab="//*[contains(text(),'Devices') and @class='ui-tabs-anchor']";
	public static String accountInfoDevicesDeviceRequestTab="//*[contains(text(),'Device Request') and @class='ui-tabs-anchor']";
	
	//Properties for device request
	
	public static String deviceRequestNewBtn=".//*[@title='Device Request List:New']";
	public static String deviceRequestSearchBtn=".//*[@id='s_2_2_36_0_icon']";
	public static String deviceRequestQuantityElement=".//*[@id='1_s_2_l_Quantity']";
	public static String deviceRequestQuantityTxtBox=".//*[@id='1_Quantity']";
	public static String deviceRequestModelOkBtn=".//*[contains(@id,'_0_Ctrl') and @title='Vector Device Model PickList:Ok']";
	public static String deviceRequestSaveBtn=".//*[@title='Device Request List:Save']";
	
	//Properties for Financial tab
	
	public static String financialsTab=".//*[contains(text(),'Financials') and @class='ui-tabs-anchor']";
	public static String financialNewBtn=".//*[@title='Payments List:New']";
	public static String deviceRequestRebillPayTypeDropdown="(.//*[@id='PaymentType'])[1]";
	public static String deviceRequestPaymentSaveBtn="(.//*[@id='saveReBlInf'])[1]";
	public static String deviceRequestPaymentACHBankAccntNoTxtBox="(.//*[@id='BankNum'])[1]";
	public static String deviceRequestPaymentACHBankRoutingNoTxtBox="(.//*[@id='RoutingNum'])[1]";
	public static String deviceRequestPaymentCreditCardNoTxtBox="(.//*[@id='CCField'])[1]";
	public static String deviceRequestPaymentExpirationMonthDropdown="(.//*[@id='ExpMonth'])[1]";
	public static String deviceRequestPaymentExpirationYearDropdown="(.//*[@id='ExpYear'])[1]";
	public static String paymentListSaveBtn="//button[@title='Payments List:Save']";
	public static String processPayBtn="//button[@title='Payments List:Process Pay']";
	public static String popupAcceptBtn=".//*[@id='btn-accept']";
	public static String checkNbrTxtBox="//input[@id='ChkNumber']";
	
	//Properties of Assign Device Tab
	
	public static String deviceAssignmentTab=".//*[contains(text(),'Device Assignment') and @class='ui-tabs-anchor']";
	public static String deviceAssignNewBtn="//button[@title='Device Assign:New']";
	public static String deviceAssignDeviceNbrSearchBtn=".//*[@id='s_1_2_20_0_icon']";
	public static String deviceAssignVectorDevicePickOkBtn=".//*[@title='Vector Device Pick:Ok']";
	public static String deviceAssignPartialFulfillmentBtn=".//*[@title='Device Assign:Partial Fulfillment']";
	public static String deviceAssignGoBtn=".//*[@title='Device Assign:Go']";
	public static String assignedDeviceListTab=".//*[contains(text(),'Assigned Devices List') and @class='ui-tabs-anchor']";
	public static String deviceHistoryTab=".//*[contains(text(),'Device History') and @class='ui-tabs-anchor']";
	
	public static String deviceHistoryDropdown=".//*[@id='j_s_sctrl_tabView']";
	public static String historyNonFinancialTab=".//*[contains(text(),'Non Financials') and @class='ui-tabs-anchor']";
	
   //Properties under Non-Financial History tab
    
    public static String hiddenIconDropDown="//select[@aria-label='Second Level View Bar']";
    public static String nonFinancialsHistoryTab="//*[contains(text(),'Non Financials') and @class='ui-tabs-anchor']";
    public static String nonFinancialQueryBtn="//button[@title='Activities:Query']";
    public static String nonFinancialSubCategoryTxtBox="(//input[@aria-label='Sub Category'])[1]";
    public static String nonFinancialGoBtn="//button[@title='Activities:Go']";
    public static String nonFinancialCategoryTxt="//td[@id='1_s_3_l_Class']";
    
   
    public static String deviceRequestListTable="//table[@summary='Device Request List']";
    public static String deviceCountInDashBoardTxt="//input[@aria-label='# of Devices']";
    public static String deviceNbrTxtBox="//input[@name='Device_Number']";
    public static String devicesAppletPopUp="//div[@name='popup']";
    public static String deviceCountOfRequest=".//*[@id='1_s_3_l_Quantity']";
    public static String deviceNbrTxt="(//td[contains(@id,'_l_Owner_Asset_Number')])[1]";
    public static String partialFulfillmentBtn=".//*[@title='Device Assign:Partial Fulfillment']";
    public static String alertAcceptBtn="//button[@id='btn-accept']";
    public static String deviceRequestStatusTxt="//td[@id='1_s_3_l_Status']";
    
    public static String assignedDeviceListTable="//table[@summary='Assigned Device List']";
    public static String assignedDeviceListQueryBtn="//button[@aria-label='Assigned Device List:Query']";
    public static String assignedDeviceListGoBtn="(//button[@title='Assigned Device List:Go'])[1]";
    public static String deviceStatusTxt="//td[@id='1_s_2_l_Device_Status']";
    public static String deviceStatusDateTxt="//td[@id='1_s_2_l_Status_Date']";
    public static String deviceStatusActiveTxt="//td[@id='1_s_2_l_Device_Status' and @title='ACTIVE']";
    public static String historyTab="//a[text()='History']";
    public static String activitiesTable="//table[@summary='Activities']";
    public static String deviceRequestNbrTxt=".//*[@id='1_s_2_l_Device_Request_Number']";
    

	
}
