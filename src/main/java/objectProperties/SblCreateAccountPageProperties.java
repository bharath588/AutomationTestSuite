package objectProperties;

//package FLCSS.floridaautomationtestsuite.object.repository;

/**
* Created by 31586 on 13-12-2016.
*/
public class SblCreateAccountPageProperties {
	
	public static String loginUsernameTxtBox="//input[@name='SWEUserName']";
    public static String loginPasswordTxtBox="//input[@name='SWEPassword']";

  public static String signInBtn="//*[@id='s_swepi_22']";
    public static String homePageVerificationTxt="(.//div[@class='Welcome'])[1]";
    //Properties for Logout
    public static String fileSettingImage="//span[@class='ui-button-text' and text()='File']";
    public static String logoutButton="//span[@class='siebui-icon-logout']";

    //Properties of Entering basic details of Account Creation

    public static String accountOpeningBtn="//*[contains(text(),'Account Opening') and @class='ui-tabs-anchor']";
    public static String newAccountOpeningBtn=".//*[@title='Private:New']";
    public static String accountOpeningPageVerificationTxt=".//*[@aria-label='Agency']";
    public static String agencyNameTxtBox=".//*[@aria-label='Agency']";
    public static String accountPINTxtBox="//input[@aria-labelledby='VAT_registration_number_Label']";
    //public static String accountPINTxtBox=".//*[@name='s_3_1_31_0']";
    public static String clicksecurityQuestionDropdown=".//*[@aria-labelledby='VehicleCount_Label']//following::span[1]";
    //public static String clicksecurityQuestionDropdown=".//*[@aria-labelledby='VehicleCount_Label']";
    //public static String selectSecutiryQuestionValueDropdown="";
    public static String securityQuestion="//input[@aria-labelledby='VehicleCount_Label']";
    public static String securityAnswerTxtBox=".//*[@aria-labelledby='DeviceSoldCount_Label']";
    public static String mobileAlertsOptinandOut="//input[@aria-labelledby='Mobile_Alerts_Label']";
    public static String AlertButton="//button[@id='btn-accept']";
    public static String accountEmailIDTxtBox=".//*[@aria-label='Email Address']";
    public static String statementTypeTxtBox=".//*[@aria-labelledby='Statement_Delivery_Frequency_Label']";
    public static String preferredLanguageTxtBox=".//*[@aria-labelledby='Correspondence_Language_Preference_Label']";
    public static String accountDetailsSaveBtn=".//*[@title='Private:Save']";
    //public static String sectionOneRowCounter=".//*[@id='s_3_rc' and contains(text(),'1 of 1+')]";
    public static String sectionOneRowCounter="(.//*[@class='siebui-appletmenu-btn'])[3]//following::span[1]";

    //properties for selecting statement delivery mode
    public static String statementDeliveryMode = ".//*[@aria-label='Statement Delivery Mode']";
      public static String statementDeliveryFrequencySelectionIcon=".//*[@aria-labelledby='Statement_Delivery_Frequency_Label']//following::span[1]";
      public static String popupQueryComboboxDropdownIcon = ".//*[@aria-labelledby='PopupQueryCombobox_Label']//following::span[1]";
      public static String statementDeliveryModeItem = ".//*[@class='ui-menu-item' and contains(text(),'Delivery Mode')]";
      public static String popupQuerySearchTextBox= ".//*[@aria-labelledby='PopupQuerySrchspec_Label']";
      public static String  popupFindButton = ".//*[@title='Vector Frequency PickList:Find']";
      public static String popupOkayButton = ".//*[@title='Vector Frequency PickList:Ok']";
    //Properties of Entering Contact Details

    public static String contactDetailsNewBtn="//button[@title='Contacts:New']";
    //public static String contactDetailsLstNameElement="//*[contains(@aria-labelledby, '_Last_Name') and contains(@id,'_Last_Name')]";
    public static String contactDetailsLstNameElement="(//td[contains(@id,'_Last_Name')])[1]";
  
    //public static String contactDetailsLstNameElement=".//input[@id='1_Last_Name']";
    //public static String contactDetailsLstNameTxtBox=".//*[@id='1_Last_Name']";
    public static String contactDetailsLstNameTxtBox="//input[@name='Last_Name']";

    
    public static String contactDetailsMiddleInitialElement="//*[contains(@aria-labelledby, 'Middle_Intial') and contains(@id,'Middle_Intial')]";
    public static String contactDetailsMiddleInitalTxtBox=".//*[@name='Middle_Intial']";
    
    
   // public static String contactDetailsContactSuffixElement="//*[contains(@aria-labelledby, 'Contact_Suffix') and contains(@id,'Contact_Suffix')]";
  public static String contactDetailsContactSuffixElement="//td[contains(@id, 'Contact_Suffix')]";
    
    public static String contactDetailsContactSuffixTxtBox=".//*[@name='Contact_Suffix']";
    
    
    public static String contactDetailsFrstNameElement="//*[contains(@aria-labelledby, '_First_Name') and contains(@id,'_First_Name')]";
    public static String contactDetailsFrstNameTxtBox=".//*[@id='1_First_Name']";
    public static String contactDetailsPhnNoElement="//td[contains(@id,'Contact_Phone_Number')]";
    public static String contactDetailsPhnNoTxtBox=".//*[@name='Contact_Phone_Number']";
    public static String contactDetailsSaveBtn=".//*[@title='Contacts:Save']";
    //public static String sectionTwoRowCounter=".//*[@id='s_1_rc' and contains(text(),'1 - 1 of 1')]";
    public static String sectionTwoRowCounter="(.//*[@class='siebui-appletmenu-btn'])[2]//following::span[1]";
    //Properties of Entering Address Details

    public static String addressDetailsNewBtn="//button[@title='Addresses:New']";
    public static String addressDetailsAddressTypeBtn=".//*[@id='1_Address_Type']//following::span[1]";
    public static String addressDetailsAddress1Element="//td[contains(@id,'Street_Address')]";
    public static String addressDetailsAddress1TxtBox="//input[contains(@name,'Street_Address')]";
    public static String addressDetailsAddress2TxtBox=".//*[@name='Street_Address_2']";
    public static String addressDetailsCityElement="//td[contains(@id,'_City')]";
    public static String addressDetailsCityTxtBox=".//*[@id='1_City']";
    public static String addressDetailsPostalCodeELement="//td[contains(@id,'_Postal_Code')]";
    public static String addressDetailsPostalCodeTxtBox=".//*[@id='1_Postal_Code']";
    public static String addressDetailsStateElement="//td[contains(@id,'l_State')]";
    public static String addressDetailsStateTxtBox=".//*[@id='1_State']";
    //public static String addressDetailsCountryElement="//*[contains(@aria-labelledby, '_Country s') and contains(@id,'l_Country')]";
    //QA2
    public static String addressDetailsCountryElement="//td[contains(@id,'l_Country')]";
  
    
    public static String addressDetailsCountryTxtBox=".//*[@id='1_Country']";
    public static String addressDetailsSaveBtn=".//*[@title='Addresses:Save']";
    //public static String sectionThreeRowCounter=".//*[@id='s_2_rc' and contains(text(),'1 - 1 of 1')]";
    public static String sectionThreeRowCounter="(.//*[@class='siebui-appletmenu-btn'])[3]//following::span[1]";

    //Properties of Entering Details under Replenishment Tab

    public static String replenishmentTab="//a[contains(text(),'Replenishments')]";
    public static String replenishmentDetailsNewBtn=".//*[@title='Account Replenishment Info List:New']";
    public static String replenushmentDetailsPrimaryChkBox="//input[@id='IsPrimary']";
    public static String replenishmentDetailsRebillPayTypeDropdown="//select[@id='PaymentType']";
    public static String creditCardNoField="//*[@id='CCField']";
    public static String creditCardExpMpnth="//*[@id='ExpMonth']";
    public static String creditCardExpYear="//*[@id='ExpYear']";

    public static String replenishmentDetailsSaveBtn=".//*[@id='saveReBlInf']";
    public static String replenishmentPlanTypeDropdown="//span[@class='siebui-icon-dropdown']";
    public static String replenishmentPlanNameTextBoxElement="//span[@class='cell-wrapperleaf']";
    public static String replenishmentPlanNameTextBox="//*[contains(@aria-labelledby, '_Plan_Name') and contains(@id,'_Plan_Name')]";

    //Properties of Entering Details under Vehicles Tab

    public static String vehiclesTab="//a[text()='Vehicles']";
    public static String vehiclesDetailsNewBtn="//button[@title='Vehicles:New']";
    public static String vehiclesDetailsPlateNumberTxtBox="//input[@id='1_Plate_Number']";
    public static String vehicleDetailsPlateStateElement="//td[contains(@id,'_Plate_State')]";
    public static String vehicleDetailsPlateStateTxtBox=".//input[@id='1_Plate_State']";
    public static String vehicleDetailsPlateTypeElement="//td[contains(@id,'_Plate_Type')]";
    public static String vehicleDetailsPlateTypeTxtBox="//input[@id='1_Plate_Type']";
    public static String vehicleDetailsPlateCountryElement="//td[contains(@id,'_Plate_Country')]";
    public static String vehicleDetailsPlateCountryTxtBox="//input[@id='1_Plate_Country']";
    public static String vehicleDetailsVehicleTypeElement="//td[contains(@id,'_Vehicle_Type')]";
    public static String vehicleDetailsVehicleTypeTxtBox=".//*[@id='1_Vehicle_Type']";
    public static String vehicleDetailsVehicleYearElement="//td[contains(@id,'_Year_of_Vehicle')]";
    public static String vehicleDetailsVehicleYearTxtBox="//input[@id='1_Year_of_Vehicle']";
    public static String vehicleDetailsVehicleMakeElement="//td[contains(@id,'_Vehicle_Make')]";
    public static String vehicleDetailsVehicleMakeTxtBox="//input[@id='1_Vehicle_Make']";
    public static String vehicleDetailsVehicleModelElement="//td[contains(@id,'_Vehicle_Model')]";
    public static String vehicleDetailsVehicleModelTxtBox="//input[@id='1_Vehicle_Model']";
    public static String vehicleDetailsSaveBtn="//button[@title='Vehicles:Save']";

    public static String vechileEffectiveEndDateSetText="//*[@id='1_End_Date']";
    public static String vechileEffectiveEndDateClick="//*[@id='1_s_1_l_End_Date']";


    //Properties of Entering Details under Device Request Tab

    public static String deviceRequestTab="//a[contains(text(),'Device Request')]";
    public static String deviceDetailsNewBtn="//button[@title='Device Requests:New']";
    public static String deviceDetailsSmartIssueIdTxtBox="//*[@id='1_Smart_Issue_Id']";
    public static String deviceDetailsPlateNumberElement="//td[contains(@id,'_Plate_Number')]";

    public static String deviceDetailsPlateNumberTxtBox=".//*[@name='Plate_Number']";
    public static String deviceDetailsQuantityElement="//td[contains(@id,'Quantity')]";
    public static String deviceDetailsQuantityTxtBox=".//*[@id='1_Quantity']";
    public static String deviceDetailsSaveBtn=".//*[@title='Device Requests:Save']";


    //Properties of Entering Details under Plans Tab and Payment details

    public static String plansTab="//a[contains(text(),'Plans')]";


    public static String plansNewButton =".//button[@title='New']";
    public static String planTypeDropDownBtn = ".//*[@id='1_Plan_Name']//following::span[1]";
    //public static String planAlreadyExisted = "//*[contains(@aria-labelledby, '_l_Plan_Name s_') and contains(@id,'_l_Plan_Name')]";
    public static String govtAgencyPolice="//td[@title='Police']";
    public static String planAlreadyExisted = "//td[contains(@id, 'Plan_Name')]";
    public static String planDeleteButton="//span[text()='Delete']";
    public static String govtAgencyTextbox="//*[@name='Plan_Name']//following::td[5]";
    public static String plansTabPayBtn=".//*[@title='Pay']";
    public static String paymentDetailsNewBtn=".//*[@title='Payments List:New']";
    public static String paymentDetailsPaymentTypeDropdown=".//select[@id='PaymentType']";
    public static String paymentDetailsBankAccountNbrTxtBox=".//input[@id='BankNum']";
    public static String paymentDetailsRoutingNbrTxtBox=".//input[@id='RoutingNum']";
    public static String paymentDetailsBankAccntFrstNameTxtBox=".//input[@id='CCFirstName']";
    public static String paymentDetailsBankAccntLstNameTxtBox=".//input[@id='CCLastName']";
    public static String paymentDetailsStreetAddressTxtBox=".//input[@id='BlAddress']";
    public static String paymentDetailsZipCodeTxtBox=".//input[@id='BlZipCode']";
    public static String paymentDetailsCityTxtBox=".//input[@id='BlCity']";
    public static String paymentDetailsStateDropdown=".//select[@id='BlState']";
    public static String paymentDetailsCountryDropdown=".//select[@id='BlCountry']";
    public static String paymentDetailsInfoSaveBtn="//button[@id='saveReBlInf']";
    public static String paymentListSaveBtn="//button[@title='Payments List:Save']";
    public static String processPayBtn="//button[@title='Payments List:Process Pay']";
    public static String checkPay="//*[@id='ChkNumber']";
    public static String amountForPayment="//input[@id='Amount']";


    public static String accountNumberFieldTxt="//input[@aria-label='Acct #']";

    //**********************----***********************************************************//
    //Properties for Commercial/Business
  //properties to change Logon Mode
    public static String clickOnTool="//span[@class='ui-button-text' and text()='Tools']";
    public static String clickOnUserPreference="//a[@href='javascript:void(0)' and text()='User Preferences']";
    public static String clickOnLogOnModeIcon=".//*[@aria-label='Logon Mode']//following::span[1]";
    public static String clickOnSave="//button[@title='User Profile:Save']";
    public static String businessOrCommAcoDetailsSaveBtn=".//*[@title='Business/Commercial:Save']";
    //Properties for change to commercial account type
    public static String siteMapImage="//span[@class='siebui-icon-tb-sitemap ToolbarButtonOn glyphicon']";
    public static String administrativeUserLink="(//a[contains(text(),'Administration - User')])[2]";
    public static String employeeLink="//a[contains(text(),'Employees')]";
    public static String employeeSearchComboBoxIcon=".//*[@aria-label='Find']//following::span[1]";
    public static String selectLoginName=".//*[@data-lovtype='Employee List Applet:QueryComboBox' and text()='Login Name']";
    public static String loginnameTextBox="//input[@class='siebui-ctrl-input siebui-align-left siebui-input-align-left s_1_1_16_0']";
    public static String selectGo="//button[@class='siebui-ctrl-btn siebui-icon-find s_1_1_20_0 appletButton']";
     public static String clickOnSaveCommercialAccount="//button[@title='Employee:Save']";
    //public static String clickOnBusinessCommercialAccount="//a[@href='#s_sctrl_tabView_noop' and text()='Business/Commercial Open Account View']";
    public static String clickOnBusinessCommercialAccount="//a[@class='ui-tabs-anchor' and text()='Business/Commercial Open Account View']";
    public static String clickOnNewButton="//button[@title='Business/Commercial:New']";
   /* //public static String selectSecutiryQuestionValueDropdown="";
    public static String securityAnswerTxtBox=".//*[@aria-labelledby='DeviceSoldCount_Label']";
    public static String accountEmailIDTxtBox=".//*[@aria-label='Email Address']";
    public static String statementTypeTxtBox=".//*[@aria-labelledby='Statement_Delivery_Frequency_Label']";
    public static String preferredLanguageTxtBox=".//*[@aria-labelledby='Correspondence_Language_Preference_Label']";
    public static String accountDetailsSaveBtn=".//*[@title='Private:Save']";
    public static String sectionOneRowCounter=".//*[@id='s_3_rc' and contains(text(),'1 of 1+')]";*/

    //for business and commercial extra added mandatory fields
    public static String accountNameTextBox ="//input[@aria-labelledby='Name_Label' and @aria-readonly='false']";
    //public static String dbaNameTextBox=".//*[@aria-label='DBA Name']";
    public static String dbaNameTextBox="//input[contains(@aria-label, 'DBA Name')]";
    public static String externalTextBox="//*[contains(@aria-label, 'External Reference')]";
    public static String FEINTextBox="//*[contains(@aria-label, 'FEIN')]";
    public static String planNameDropDownIcon="//span[@class='siebui-icon-dropdown']";

    // account maintenance properties
    public static String accountCompanyNameTxtBox = "//*[contains(@aria-label, 'Company Name')]";
    public static String commercialAccountSelect=".//input[@type='checkbox' and @aria-labelledby='Commercial_Account_Open_Label']";

    //For verify account info on payment  page
    public static String agencyName =".//*[@aria-label='Agency']";
      public static String accName =".//*[@aria-label='Acct Name']";
      public static String accType=".//*[@aria-label='Acct Type']";
      public static String accStatus=".//*[@aria-label='Acct Status']";
      public static String finRebilStatus =".//*[@aria-label='Fin Sts/Rebill']";
      public static String rebillType=".//*[@aria-label='Rebill Type']";
      public static String challengeQuestion=".//*[@aria-label='Challenge QA']";
      public static String ChallengeAnswer=".//*[@aria-label='Challenge Answer']";

      public static String accNumber=".//*[@aria-label='Acct #']";
      public static String noOfDevices=".//*[@aria-label='# of Devices']";
      public static String noOfVehicles=".//*[@aria-label='# of Vehicles']";
      public static String noOfOpenSr=".//*[@aria-label='# of Open SR']";
      public static String noOfOpenInv=".//*[@aria-label='# of Open Inv']";
      public static String linkAccBalance=".//*[@aria-label='Link Account Balance']";
      public static String accBalance=".//*[@aria-label='Acct Bal']";
      //public static String pptlBalance=".//*[@aria-label='PPTL Bal']";
      public static String postpaidToll=".//*[@aria-label='Postpaid Toll']";
      public static String tollInvBal=".//*[@aria-label='Toll Inv Bal']";
      public static String invOvrPmt=".//*[@aria-label='Inv OvrPmt']";
      public static String ccExpiry=".//*[@aria-label='CC Expriry']";
      public static String reminderMsg=".//*[@aria-label='Reminder Msg']";
      public static String lastTollDate=".//*[@aria-label='Last Toll Date']";
      public static String lastdispDateAmt=".//*[@aria-label='Last Disp Date/Amt']";
      public static String lastPmtDate=".//*[@aria-label='Last Pmt Date']";
      public static String accOpeningDate=".//*[@aria-label='Acct Open Date']";
      public static String pmtDueDate=".//*[@aria-label='Pmt Due Date']";


      public static String castAccCheckBox=".//input[@type='checkbox' and @aria-label='Cast Account']";
      public static String isPostPaidCheckBox=".//input[@type='checkbox' and @aria-label='Is Post Paid']";
      public static String nixieStatusCheckBox=".//input[@type='checkbox' and @aria-label='Nixie Status']";
      public static String courtCheckBox=".//input[@type='checkbox' and @aria-label='Court']";
      public static String nsfCheckBox=".//input[@type='checkbox' and @aria-label='NSF']";
      public static String accountHoldCheckBox=".//input[@type='checkbox' and @aria-label='Account Hold']";
      public static String isAnonymousCheckBox=".//input[@type='checkbox' and @aria-label='Is Anonymous']";
      public static String tagsPricesTable = "//*[@summary='Open Items List']";

    //payment Maintenance properties
    public static String dropdownhistory="//select[@id='j_s_sctrl_tabView']";
    public static String historyOption="//select[@id='j_s_sctrl_tabView']/option[text()='History']";

    public static String linkFinancial="//a[@href='#s_sctrl_tabView_noop' and text()='Financials']";
    public static String accountReplenishment="//a[@class='ui-tabs-anchor' and text()='Account Replenishment']";
    public static String newBtnAmount=".//*[@title='Replenishment Entry Form:New']";
    public static String amonutTxtBox=".//*[@aria-labelledby='BalanceForward_Label' and @aria-readonly='false']";
    public static String replenishmentSave=".//*[@title='Replenishment Entry Form:Save']";
    public static String newPaymentList=".//*[@title='Payments List:New']";
    public static String rebillPaymentType=".//*[@id='PaymentType']";
    public static String saveRebillInfo=".//*[@id='saveReBlInf']";
    public static String savePaymentList=".//*[@title='Payments List:Save']";
    public static String processPay=".//*[@aria-label='Payments List:Process Pay']";
    public static String financialsUnderHistroy="//a[@href='#s_vctrl_div_tabScreen_noop' and text()='Financials']";
    public static String nonFinancialsHistroy="//a[text()='Non Financials']";
    public static String financialHistroyTable=".//table[@summary='Account Financials History List']";
    public static String nonFinancialHistroyTable=".//table[@summary='Activities']";
    public static String accountBalTxtBox="//input[@aria-label='Acct Bal']";
    public static String ppTlBalTxtBox="//input[@aria-label='PPTL Bal']";
    public static String accountFinancialHistroyList="//a[@href='#s_vctrl_div_tabScreen_noop' and text()='Financials']";
    public static String popUpErrorMsgInvalidPay="//*[contains(text(),'This is not a valid Pay Type')]";
    public static String reverseBtn="//*[@title='Financial Transaction List:Reverse']";
    //public static String internalControls="//*[text()='Internal Controls' and contains(@id,'s_smc')]";
    public static String internalControls="//a[text()='Internal Controls']";
    public static String initiationAmountTxtBox="//input[@name='Initiation_Amt']";
    public static String historyLink="//a[@href='#s_sctrl_tabView_noop' and text()='History']";
    public static String approvalAmountTxtBox="//input[@name='Approval_Amt']";
    public static String initiationTextBox="//td[@tabindex='-1' and text()='100']";
    public static String approvalTextBox="//td[@tabindex='-1' and text()='10,000']";
    public static String approvalInfolink="//a[@href='#s_sctrl_tabView_noop' and text()='Account Info']";
    public static String rebillThresholdAmount="//input[@aria-labelledby='Rebill_Threshold_Amt_Label']";

    public static String vehicleHistoryTab=".//*[@href='#s_vctrl_div_tabView_noop' and text()='Vehicle History']";
    public static String vehicleHistoryComboBoxIcon=".//*[@aria-label='Find']//following::span[1]";
    public static String selectPlateNumber=".//*[@data-lovtype='Vector Account Vehicle History List Applet:QueryComboBox' and text()='Plate Number']";
    public static String accountVehicleHistorySearchBox=".//*[@aria-labelledby='QuerySrchSpec_Label']";
    public static String selectGoForVehicleSearch="//button[@aria-label=' Account Vehicle History List Applet:Go']";

    public static String ChallangeQA1Dropdown="//input[@aria-labelledby='Challenge_Question_Label' and @aria-label='Challenge Q&A']";
    public static String ChallangeQA2Dropdown="//*[@aria-label='Challenge Q&A - 2']//following::span";
    public static String ChallangeQA2TextBox=".//*[@aria-labelledby='Challenge_Answer_2_Label']";
    public static String addressTab="//a[text()='Addresses']";
    public static String addressLine1="//input[@aria-labelledby='Street_Address_Label']";
    public static String addressHistoryTab="//*[text()='Address History']";
    public static String addressCleansedFlag="//input[@aria-labelledby='Address_Cleansed_Flag_Label']//following::span[1]";
    public static String addressLine1Label="//*[text()='Address Line1']";
   // public static String accountHistoryTab="//*[text()='Account History']";
   // public static String emailAddress="//input[@aria-label='Email Address']";
    public static String statusLabelAccountHistory="//*[text()='Status']";
    public static String accountHistoryTab="//*[text()='Account History']";
    public static String emailAddress="//input[@aria-label='Email Address']";

    public static String vectorBusinessRulesTab="//*[contains(@aria-label,'Vector Business Rules Selected')]";
    public static String vectorBusinessRulesLink="//*[contains(text(),'Vector Business Rules')]";
   
    public static String vectorInternalProcessParamTab="//*[contains(text(),'Vector Internal Process Parameters')]";
    public static String firstNotificationParamValue="//*[@title='FIRST_NOTIFICATION_DAYS']//following::td[3]";
    public static String pendingOpenDays="//*[@title='PEND_OPEN_DAYS']//following::td[3]";
    public static String pendingCloseDays="//*[@title='PEND_CLOSE_DAYS']//following::td[3]";
    public static String vectorInternalProcessLink="//span/a[ text()='Vector Internal Process Parameters']";
    public static String vectortype=".//*[@data-lovtype='Employee List Applet:QueryComboBox' and text()='Login Name']";
    public static String simulatorLink="//*[text()='Simulator']";
    public static String newSimulatorBtn="//*[@title='Service Methods:New']";
    public static String serviceName="//*[@name='Service_Name']";
    public static String methodName="//*[@name='Method_Name']";
    public static String newInputArgumentsBtn="//*[@title='Input Arguments:New']";
    public static String administrativeBusinessServiceLink="//*[text()='Administration - Business Service' and contains(@id,'s_sma')]";
    public static String propertyNameClick="//*[contains(@aria-labelledby,'_PropertyKey')]";
    public static String propertyNameTextBoxIcon="//*[@name='PropertyKey']/following::span";
    public static String newPropertySetBtn="//*[@title='Property Set Properties:New']";
    public static String propertyNameTextBox="//*[contains(@aria-labelledby,'Key_Label')]";
    public static String valueTextBox="//*[contains(@aria-labelledby,'Value_Label')]";
    public static String propertySetOkBtn="//*[@title='Property Set Properties:OK']";
    public static String propertySetSaveBtn="//*[@title='Property Set Properties:Save']";
    public static String searchTextBox="//*[@aria-labelledby='QuerySrchSpec_Label']";
    public static String vectorInternalDroddownArrow="//*[@aria-labelledby='QueryComboBox_Label']/following::span";
    public static String TypeValue="//li[text()='Type']";
    public static String goBtnVectorInternalProcess="//*[contains(@class,'siebui-ctrl-btn siebui-icon-find') and @title='Vector Process Parameters:Go']";
    public static String runBtn="//*[@data-display='Run']";

    public static String minRebillThresholdAmount = "//input[@aria-label='Rebill Thrsh Amt']";
    public static String finStatus="//input[@aria-label='Fin Sts/Rebill']";
    public static String accBal="//input[@aria-label='Acct Bal']";
    public static String pptlBal="//input[@aria-label='PPTL Bal']";
    public static String taxExempt="//input[@aria-label='Tax Exempt #']";
    public static String reBillPayType="//input[@aria-label='Rebill Type']";
    public static String acctOpenDate="//input[@aria-label='Acct Open Date']";
    public static String openItemsListTable="//table[@summary='Open Items List']";
    public static String sunPassPortable="//table//td[@title='TAGINT']";
    public static String devicesTab="//a[text()='Devices']";
    public static String devicesRequestTab="//a[text()='Device Request']";
    public static String devicesRequestList="//table[@summary='Device Request List']";
    public static String planTab="//a[text()='Plans']";
    public static String plansTableList="//table[@summary='Plan Details List']";
    public static String letterReqTab="//a[text()='Letter Request']";
    public static String historyTab="//a[text()='History']";
    public static String notificationHistory="//a[text()='Notification History']";
    public static String financials="//a[text()='Financials']";
    
    public static String reversal="//a[text()='Reversals']";
    public static String financialListTable=" //table[@summary='Financial Transaction List']";
    public static String amountdueTableList="//table[@summary='Composite Payment']";
    public static String acceptBtn="//button[@id='btn-accept']";
    public static String vehicleCountTxtBox="//input[@aria-labelledby='VehicleCount_Label']";
    public static String accntName =".//*[@aria-label='Acct Name']";
    public static String pptlBalanc="//input[@aria-label='PPTL Bal']";
    public static String accBalanc="//input[@aria-label='Acct Bal']";
    public static String navigateMenu="//span[@class='ui-button-text' and text()='Navigate']";
    public static String siteMapMenu="//a[contains(text(),'Site Map')]";

    public static String siteMapSearchTxtBox="//input[@id='sitemapFilterInput']";
    public static String importFileTypeRadioBtn = "//input[@class='siebui-ctrl-radio ' and @value='Auto Mapping']";
    public static String nextBtn = "//button[@title='Import:Next']";
    public static String vehicleImportOkBtn = "//button[@title='Import:OK']";
    public static String aniversaryDate="//input[@aria-label='Acnt Anniversary Date']";

  //laxmi newly added properties in SblCreateAccountPageProperties.java

    public static String StatementFreqIcon="(//span[@class='siebui-icon-pick applet-form-pick applet-list-pick'])[2]";
    public static String StatementFreqTitleSummary="//td[@title='Summary']";
    public static String StatementFreqTitleDetails="//td[@title='Detail']";
    public static String VectorPickListOkBtn="//button[@title='Vector Frequency PickList:Ok']";
    public static String StatementTypeVectorFreqList= ".//*[@class='ui-menu-item' and contains(text(),'Statement Type')]";
    public static String DeliveryModeSearchTextBox="//input[@aria-labelledby='PopupQuerySrchspec_Label']";
    public static String VectorListFindBtn="//button[@title='Vector Frequency PickList:Find']";

    public static String xpathOfMDXLabel=".//input[@aria-labelledby='Enroll_in_MDX_Label']";
}


