package objectProperties;

/**
 * Created by 23319 on 28-12-2016.
 */
public class SblAccountMaintenancePageObject {


    public static String logoutSettingImage="//li[@id='tb_0']";
    public static String logoutButton="//span[@class='siebui-icon-logout']";

    public static String accountsTab="(//*[contains(text(),'Accounts') and @class='ui-tabs-anchor'])[1]";

    public static String accountNumberTxtBox="//input[@aria-label='Account Number' and @aria-labelledby='CSN_Label']";
    public static String goBtn=".//*[@title='Search:Go']";
    public static String accountInfoTab="//*[contains(text(),'Account Info') and @class='ui-tabs-anchor']";

    public static String accountInfoPINTxt="//input[@aria-labelledby='Challenge_Answer_Label' and @aria-label='Challenge Answer' and contains(@name,'s_1')]";
    public static String accountInfoMiscellaniousPINTxtBox="//input[@aria-labelledby='Challenge_Answer_Label' and @aria-label='Challenge Answer' and contains(@name,'s_2')]";

    //Search-HomePage properties
    public static String cellularPhoneTextBox="//input[@aria-label='Cellular Phone']";
    public static String deviceNumberTextBox="//input[@aria-label='Device Number']";
    public static String vectorAccountTableSummary="//table[@summary='Vector Account List']";
    public static String accountNameLable="//div[@class='ui-jqgrid-sortable' and text()='Account Name']";
    public static String accountNameTextBoxHomepageSrch="//input[@aria-label='Account Name']";
    public static String vectorAccountListText="//*[text()='Vector Account List']";
    public static String errorMessageText="//*[text()='Error Message']";
    public static String accountNameLabel="//div[text()='Account Name']";
    //binocular Search page properties
    public static String binocularSearch="//span[@class='siebui-icon-tb-vectorpowerquery ToolbarButtonOn glyphicon fa fa-binoculars']";
    public static String lookInDropDown="//*[@id='LookInObject']";
    public static String searchImageButton="//*[@id='inner-adv-find-button']/img";
    public static String tollIdTextBox="//input[@title='Toll Id']";
    public static String ufmIdTextBox="//input[@title='UFM Id']";
    public static String accountNoTextBoxBinosrch="//input[@title='Account Number']";
    public static String accountNameTextBoxBinosrch="//input[@title='Account Name']";
    public static String emailTextBoxBinosrch="//input[@title='Email Address']";
    public static String plateNoTextBoxBinosrch="//input[@title='Plate Number']";
    public static String invoiceNoTextBoxBinosrch="//input[@title='Invoice Number']";
    public static String transponderNoTextBoxBinosrch="//input[@title='Transponder Number']";
    public static String searchResultsTableOfBinosrch="//*[@id='pq-results']";
    //Properties for Service Request Tab page
    public static String serviceRequestTab="//a[text()='Service Requests']";
    public static String accountNoServiceRqst="//input[@aria-label='Account Number']";
    public static String emailIdServiceRqst="//input[@aria-label='Email Id']";
    public static String xpathOfServiceReqTable = "//*[@summary='Service Request List']";


    public static String ChallangeQA1Dropdown="//input[@aria-labelledby='Challenge_Question_Label' and @aria-label='Challenge Q&A']";
    // public static String ChallangeQA2Dropdown="//*[@aria-label='Challenge Q&A - 2']//following::span";
    // public static String ChallangeQA2TextBox=".//*[@aria-labelledby='Challenge_Answer_2_Label']";
    //public static String addressTab="//a[text()='Addresses']";
    // public static String addressLine1="//input[@aria-labelledby='Street_Address_Label']";
    // public static String addressHistoryTab="//*[text()='Address History']";
    public static String addressCleansedFlag="//input[@aria-labelledby='Address_Cleansed_Flag_Label']//following::span[1]";
    // public static String addressLine1Label="//*[text()='Address Line1']";
   // public static String accountHistoryTab="//*[text()='Account History']";
   // public static String emailAddress="//input[@aria-label='Email Address']";
   // public static String statusLabelAccountHistory="//*[text()='Status']";

    //kavita
    public static String newAccountName = "";
    public static String vehiclesNewButtonPlate ="//button[@id='s_2_1_16_0_Ctrl']";
    public static String vehiclesCountyPlate="//*[@id='1_Plate_Country']";
    public static String vehiclePlateCountryList="(//*[contains(@aria-labelledby, '_Plate_Country s_') and contains(@id,'_Plate_Country')])[1]";
    public static String vehicleYearList="(//*[contains(@aria-labelledby, '_Year_of_Vehicle s') and contains(@id,'_Year_of_Vehicle')])[1]";
    public static String vehicleSaveList="//button[@title='Vehicles:Save']";


    public static String deviceTab="//a[contains(text(),'Devices')]";
    public static String deviceDetailsNewListBtn="//*[@title='Device Request List:New']";
    public static String deviceDetailsSaveListBtn=".//*[@title='Device Request List:Save']";
    public static String financialsTab="//*[contains(text(),'Financials')]";
    public static String newListBtn=".//*[@title='Payments List:New']";
    public static String deviceDescTextbox="//input[@name='Smart_Issue_Id']";

    public static String reversalTab="//a[contains(text(),'Reversals')]";
    //public static String reverseBtn="//button[@title='Financial Transaction List:Reverse']";


    public static String statusColumn="//td[@id='1_s_2_l_Device_Status']";
    public static String assignedSaveBtn="//button[@title='Assigned Device List:Save']";
    public static String statusTextbox="//input[@name='Device_Status']";

    //Nishanth
    public static String DevicesTab="//a[contains(text(),'Devices')]";

    //sreeraj

    public static String xpathAccLink = "//a[contains(@name,'CSN')]";
    public static String xpathRebillInfo = "//div[contains(@class,'mceGridField siebui-value mceField')]/input[@aria-labelledby='RebillType_Label']";
    public static String xpathAccountInfoTab = "//a[contains(text(),'Account Info')]";
    public static String xpathDeviceTab = "//a[text()='Devices']";
    public static String xpathDeviceRequestTab = "//a[contains(text(),'Device Request')]";
    public static String xpathDeviceNewButton = "//button[@title='Device Request List:New']";
    public static String xpathDeviceName = "//input[@id='1_Smart_Issue_Id']";
    public static String xpathDeviceTable = ".//*[@summary='Device Request List']";
    public static String xpathDeviceSave = "//button[contains(@title,'Device Request List:Save')]/span[text()='Save']";
    public static String xpathFinancialTab = "//a[text()='Financials']";
    public static String xpathNewBtn = "//button/span[contains(text(),'New')]";
    public static String xpathPaymentType = "//select[@id='PaymentType']";
    public static String xpathSavePaymentType = "//div[contains(@class,'SiebeluiBtn')]/button[contains(text(),'Save')]";
    public static String xpathSaveForProcessing = "//button[contains(@title,'Payments List:Save')]/span[contains(text(),'Save')]";
    public static String xpathProcess = "//button[contains(@title,'Payments List:Process Pay')]/span[contains(text(),'Process Pay')]";
    public static String xpathDeviceDropdown = "//div[@class='siebui-btn-grp-search']/input[@name='s_2_1_1_0']";
    public static String xpathDeviceTextbox = "//div[@class='siebui-btn-grp-search']/input[@name='s_2_1_0_0']";
    public static String xpathGoButton = "//*[@id='s_2_1_2_0_Ctrl']";
    public static String xpathAnonymus = "//input[@aria-label='Is Anonymous']";
    public static String xpathThrshValue = "//input[@aria-labelledby='Rebill_Threshold_Amt_Label']";

    //297

    public static String xpathFirstNamePayment = "//input[@id='CCFirstName']" ;
    public static String xpathLastNamePayment = "//input[@id='CCLastName']";
    public static String xpathZipcodePayment = "//input[@id='BlZipCode']";
    public static String xpathStatePayment = "//select[@id='BlState']";
    public static String xpathCountryPayment = "//select[@id='BlCountry']";
    public static String xpathAddressPayment = "//input[@id='BlAddress']";
    public static String xpathCityPayment = "//input[@id='BlCity']";
    public static String xpathCardNumberPayment = "//input[@id='CCField']";
    public static String xpathExpMonthPayment = "//select[@id='ExpMonth']";
    public static String xpathExpYearPayment = "//select[@id='ExpYear']";
    public static String xpathMyAccount = "//a[text()='My Accounts']";
    public static String xpathName = "//input[contains(@aria-label,'Account Name')]";
    public static String xpathStreet = "//input[contains(@aria-label,'Address')]";
    public static String xpathZip = "//input[contains(@aria-label,'Zip Code')]";
    public static String xpathCity = "//input[contains(@aria-label,'City')]";
    public static String xpathState = "//input[contains(@aria-label,'State')]";
    public static String xpathCountry = "//input[contains(@aria-label,'Country')]";


    //107

    public static String xpathVehiclesTab = "//a[contains(text(),'Vehicles')]";
    public static String xpathNewVehicle = "//*[@aria-label='Vehicle:New']//span[text()='New']";
    public static String xpathPlateNumber = "//*[@id='1_Plate_Number']";
    public static String xpathEndDateEnable = "//*[@id='1_s_2_l_End_Date']";
    public static String xpathEndDateText = "//*[@id='1_End_Date']";
    public static String xpathPlateCountryEnable = "//*[@id='1_s_2_l_Plate_Country']";
    public static String xpathPlateCountryText = "//*[@id='1_Plate_Country']";
    public static String xpathPlateStateEnable = "//*[@id='1_s_2_l_Plate_State']";
    public static String xpathPlateStateText = "//*[@id='1_Plate_State']";
    public static String xpathPlateTypeEnable = "//*[@id='1_s_2_l_Plate_Type']";
    public static String xpathPlateTypeText = "//*[@id='1_Plate_Type']";
    public static String xpathSaveVehicle = "//*[@aria-label='Vehicle:Save']//span[text()='Save']";
    public static String xpathVehicleHistoryTab = "//a[contains(text(),'Vehicle History')]";
    public static String xpathSearchDropdown = "//div[@class='siebui-btn-grp-search']/input[@name='s_2_1_0_0']";
    public static String xpathSearchTextbox = "//div[@class='siebui-btn-grp-search']/input[@name='s_2_1_1_0']";
    public static String xpathPlateTable = "//table[contains(@summary,'Account Vehicle History List Applet')]";

    //139

    public static String xpathPlateNumberDeviceEnable = "(//*[@id='1_s_2_l_Vehicle_-_Plate_No'])[1]";
    public static String xpatPlateNumberDeviceText = "//*[@id='1_Vehicle_-_Plate_No']";

    //274

    public static String xpathCheckNumber = "//input[@id='ChkNumber']";


    public static String contactsTab="//a[contains(text(),'Contacts')]";


 public static String reverseBtn="//button[@title='Financial Transaction List:Reverse']";


 
    public static String deviceNumberElement="//td[@id='1_s_2_l_Device_Number']";
    public static String accountHOmeTab="//*[contains(text(),'Accounts Home') and @class='ui-tabs-anchor']";
    public static String preOwnedDevice="//td[@id='1_s_2_l_Retained_Device']";
    public static String preOwnedDevicetxtbox="//input[@name='Retained_Device']";

    public static String tranferReasonCode="//td[@id='1_s_2_l_Vector_Device_Transfer_Reason_Code']";
    public static String tranferReasonCodetxtbox="//input[@name='Vector_Device_Transfer_Reason_Code']";

    public static String deviceHistoryTab="//a[text()='Device History']";
    public static String deviceHistoryTable="//table[@summary='Vector Account Device History List']";

    public static String accountReplenishmentTab="//a[text()='Account Replenishment']";
    public static String accountReplenishmentNewbtn="//button[@title='Replenishment Entry Form:New']";

    public static String financialNewbtn="//button/span[contains(text(),'New')]";
    //public static String historyTab="//a[text()='History']";
    public static String historyFinancialTab="//a[text()='Financials']";
    public static String historyFinancialTable="//table[@summary='Account Financials History List']";
    public static String financialTrnsactionTable="//table[@summary='Financial Transaction List']";
    public static String accountHistoryLink="//a[text()='Account History']";
    public static String xpathOfAccountHistoryTable = "//*[@summary='Account History List Applet']";
    public static String  okBtn = "//*[@id='btn-accept']";
    public static String srNumberTxtBox="//input[@aria-labelledby='SRNumber_Label']";
    public static String serviceReqsubmittBtn="//button[@title='Service Request:Submit']";
    public static String mailReqSubmittedElement="//*[contains(text(),'mail Request has been submitted')]";
    public static String serviceReqSaveBtn="//button[@title='Service Request:Save']";
    
    public static String mailListItem = "//*[text()='MAIL']";
    public static String descriptionTxtBox="//textarea[@aria-labelledby='Description_Label']";
    public static String deliveryModeDropDownIcon=".//*[@aria-labelledby='Delivery_Mode_Label']//following::span[1]";
    public static String mailHouseReqTypeTxtBox="//input[@aria-labelledby='Mail_House_Request_Type_Label']";
    public static String letterRequestLink="//a[contains(text(),'Letter Request')]";
    public static String serviceReqBtn="//button[@title='Service Request:New']";
    
    public static String financialTransListQueryTxtBox="//input[@name='s_1_1_1_0']";
    public static String financialTransListValueTxtBox="//input[@name='s_1_1_0_0']";
    public static String accountsInfoTab = "//a[text()='Account Info']";
    public static String deviceCountTxtBox="//input[@aria-labelledby='DeviceSoldCount_Label']";
    public static String reverseButton="//*[@title='Financial Transaction List:Reverse']";
    public static String nsfChargeFeeChkBox=".//*[@name='ChargeNSFFee']//following::span[1]";
    public static String assignDevicesListGoBtn="(//button[@title='Assigned Device List:Go'])[2]";
    public static String paidElement="//td[text()='PAID']";
    public static String cancelDeviceReqBtn = "//button[@title='Device Request:Cancel Device Request']";
    
    public static String planDetailsNewBtn="//button[@title='Plan Details List:New']";
    public static String planNewBtn = "//button//*[contains(text(),'New')]";
    public static String planNameElement="//*[@id='1_Plan_Name']";
    public static String planSaveBtn="//button//*[contains(text(),'Save')]";
    public static String xpathOfOpenItemsTable = ".//table[contains(@summary,'Open Items List')]";
    public static String planHistoryTab="//a[text()='Plan History']";
    public static String xpathOfPlanHistoryTable = ".//table[contains(@summary,'Plan History List')]";
    public static String notesTab = "//a[contains(text(),'Notes')]";
    public static String xpathOfNotesTable = ".//*[@summary='Notes List']";
    public static String nonFinacialHistoryLink="//span[contains(text(),'Non-Financial History')]";
    public static String historyTableXpath = "//div[@class='popover fade bottom in']//table[@id='tblNonFinancialHistory' and @class='pure-table tblDash']//tr//td[contains(text(),'DEMOGRAPHICS')]";
    
   public static String xpathOfAccountHistory = ".//*[@summary='Account History List Applet']";
    
    public static String emailAddTxtBox="//input[contains(@aria-label,'Email Address')]";
   // public static String accountHistoryLink="//a[contains(text(),'Account History')]";
    
    
    public static String  vehicleListNewBtn="//button[@title='Vehicles:New']";
    public static String queryTxtBox="//input[@aria-labelledby='QueryComboBox_Label']";
    public static String queryValTxtBox="//input[@aria-labelledby='QuerySrchSpec_Label']";
    public static String goSpanBtn = "(//span[text()='Go'])[2]";
    public static String statusElement="//td[@id='1_s_2_l_Status']";
    
    public static String paymentListNewBtn="//button[@title='Payments List:New']";
    public static String notesListNewBtn="//button[@title='Notes List:New']";
    public static String notesTable = "//table[@summary='Notes List']";
    public static String taxExemptField="//*[@aria-label='Is Tax Exempt']";
    public static String isTaxExemptRadioBtn="//*[@aria-label='Is Tax Exempt']//following::span[1]";
    public static String taxExemptExpiryDateTxtBox="//*[contains(@aria-labelledby, 'Tax_Exempt_Expiry_Date_Label') and contains(@aria-label,'Tax Exempt Expiry Date')]";
    public static String taxExemptNumTxtBox=".//*[@aria-labelledby='Vector_Tax_Exempt_Number_Label']";
    public static String contactsListTab="//a[text()='Contact List']";
    
    public static String contactsHistory ="//a[contains(text(),'Contact History')]";
    public static   String xpathOfContactTable = "//table[@summary='Contacts History']";
    public static String addressesTbl="//table[@summary='Addresses']";
    public static String  xpathOfAddressTable = "//*[@summary='Address History']";
    public static String rebillTypeTxtBox="//input[@aria-label='Rebill Type']";
    public static String rebillInfoLink="//a[text()='Rebill Info']";
    public static String rebillInfoAppletNewBtn=".//*[@title='Replenishment Info List Applet:New']";
    public static String bankNumTxtBox="//input[@id='BankNum']";
    public static String routingNumTxtBox="//input[@id='RoutingNum']";
    public static String replenishmentHistoryTab="//a[contains(text(),'Replenishments History')]";
    public static String xpathOfRebillinfoTable = "./*//*[@summary='Replenishment Info History List']";
    public static String importVehicleBtn="//button[@title='Vehicles:Import Vehicle']";
    public static String browseFileTxtBox="//input[@class='siebui-ctrl-file ']";
    public static String vehicleImportBtn1=".//*[@title='Vehicle File Import:Import']";
    public static String plateNumberElement="(//td[contains(@id,'Plate_Number')])[1]";
    
    public static String vehicleListSaveBtn="//button[@title='Vehicles:Save']";
    
   // public static String xpathOfAddressTable = "//*[@summary='Address History']";
   public static String replenishListEditBtn=".//*[@title='Replenishment Info List Applet:Edit']";
    public static String assignedDeviceList="//table[@summary='Assigned Device List']";
    public static String assignedDeviceListTblRows="//table[@summary='Assigned Device List']//tr";
    public static String deviceHistoryLink="//a[contains(text(),'Device History')]";
    public static String xpathOfDeviceHistoryTable = ".//*[@summary='Vector Account Device History List']";
    public static String statementDeliveryDropDown="//input[@aria-labelledby='Statement_Delivery_Frequency_Label']//following::span[1]";
    public static String mailElement="//td[text()='MAIL']";
    public static String stmtDelivModeTxtBox="//input[@aria-label='Stmt Deliv Mode']";
   // public static String xpathOfAccountHistory = ".//*[@summary='Account History List Applet']";
    public static String countryTxtBox="//input[@id='countryText']";
    public static String stateTxtBox="//input[@id='StateText']";
    public static String vehicleEndDtElement="(//td[contains(@id,'_l_End_Date')])[1]";
    public static String compositePaymentGoBtn="(//button[@title='Composite Payment:Go'])[2]";
}
