package objectProperties;

/**
 * Created by 23319 on 28-12-2016.
 */
public class SblAccountSearchProperties {

    //**************************************************************
    public static String siebelURL="http://10.36.20.4/callcenter_enu/start.swe?";
    public static String siebelLoginUsernameTxtBox="id=s_swepi_1";
    public static String siebelLoginPasswordTxtBox="id=s_swepi_2";

    public static String siebelSignInBtn="id=s_swepi_22";
    public static String siebelHomePageVerificationTxt="(.//div[@class='Welcome'])[1]";
    //Properties for Logout
    public static String logoutSettingImage="//li[@id='tb_0']";
    public static String logoutButton="//span[@class='siebui-icon-logout']";

    public static String accountsTab="//*[contains(text(),'Accounts') and @class='ui-tabs-anchor']";
    public static String accountNumberTxtBox="//input[@aria-label='Account Number' and @aria-labelledby='CSN_Label']";
    public static String goBtn=".//*[@title='Search:Go']";
    public static String accountInfoTab="//*[contains(text(),'Account Info') and @class='ui-tabs-anchor']";
    public static String ChallangeQA1Dropdown="//input[@aria-labelledby='Challenge_Question_Label' and @aria-label='Challenge Q&A']";
    public static String ChallangeQA2Dropdown="//*[@aria-label='Challenge Q&A - 2']//following::span";
    public static String ChallangeQA2TextBox=".//*[@aria-labelledby='Challenge_Answer_2_Label']";
    public static String addressTab="//a[text()='Addresses']";
    public static String addressLine1="//input[@aria-labelledby='Street_Address_Label']";
    public static String addressHistoryTab="//*[text()='Address History']";
    public static String addressCleansedFlag="//input[@aria-labelledby='Address_Cleansed_Flag_Label']//following::span[1]";
    public static String addressLine1Label="//*[text()='Address Line1']";
    public static String accountHistoryTab="//*[text()='Account History']";
    public static String emailAddress="//input[@aria-label='Email Address']";
    public static String statusLabelAccountHistory="//*[text()='Status']";
  
    public static String accountInfoPINTxt="//input[@aria-labelledby='Challenge_Answer_Label' and @aria-label='Challenge Answer' and contains(@name,'s_1')]";
    public static String accountInfoMiscellaniousPINTxtBox="//input[@aria-labelledby='Challenge_Answer_Label' and @aria-label='Challenge Answer' and contains(@name,'s_2')]";
    
    //Search-HomePage properties
    public static String cellularPhoneTextBox="//input[@aria-label='Cellular Phone']";
    public static String deviceNumberTextBox="//input[@title='Device Number']";
    public static String vectorAccountTableSummary="//table[@summary='Vector Account List']";
    public static String accountNameLable="//div[@class='ui-jqgrid-sortable' and text()='Account Name']";
    public static String accountNameTextBoxHomepageSrch="//input[@aria-label='Account Name']";
    public static String vectorAccountListText="//*[text()='Vector Account List']";
    public static String errorMessageText="//*[text()='Error Message']";
    public static String accountNameLabel="//div[text()='Account Name']";
    public static String accountNumberLabel="//div[text()='Account Number']";
    public static String emailHomePage="//input[@aria-label='Email Id']";
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
    public static String tollIdBinocularSearch="//input[@title='Toll Id']";
    public static String ufmIdBinocularSearch="//input[@title='UFM Id']";
    public static String findBinocularSearchBtn="//button[@title='Find']";
    public static String firstLevelViewBar="//select[@aria-label='First Level View Bar']";
    public static String selectNotificationSearch="//select[@aria-label='First Level View Bar']/option[text()='Notification Search']";
    public static String queryNotificationBtn="//button[@title='Notifications:Query']";
    public static String notificationIdTxtBox="//*[@id='1_Notification_Id']";
    public static String notificationSearchLabel="//*[@id='1_s_1_l_Notification_Id']";
    public static String notificationGoBtn="//button[@title='Notifications:Go']";
    public static String licensePlateNumbetTxtBox="//input[@title='License Plate Number']";
    
    //Properties for Service Request Tab page
    public static String serviceRequestTab="//a[text()='Service Requests']";
    public static String accountNoServiceRqst="//input[@aria-label='Account Number']";
    public static String emailIdServiceRqst="//input[@aria-label='Email']";
    public static String xpathOfServiceReqTable = "//*[@summary='Service Request List']";
    
    //validation table
    public static String tollIdSearchValidationTable="//table[@id='pq-results']";
    public static String notificationValidationTable="//table[@summary='Notifications']";
  
  

}
