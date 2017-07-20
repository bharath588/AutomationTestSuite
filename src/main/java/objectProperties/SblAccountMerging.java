package objectProperties;

/**
 * Created by C5056047 on 1/17/2017.
 */
public class SblAccountMerging {

    //Properties of Entering basic details of Account Creation
    public static String accountsearchListTab="//a[text()='Account Search List']";
    public static String accountNoFieldSrchLst = "//input[@class='siebui-ctrl-input siebui-align-left siebui-input-align-left s_2_1_2_0']";
    public static String goBtnSearchLst = "//button[@class='siebui-ctrl-btn siebui-icon-find s_2_1_0_0 appletButton']";
    public static String searchedAcntNo = "//*[@id='1_s_2_l_CSN']";
    public static String accountsTab="//*[contains(text(),'Accounts') and @class='ui-tabs-anchor']";
    public static String tabDropdown = "//Select[@id='j_s_sctrl_tabView' and @aria-label='Second Level View Bar']";
    public static String accountDropdown = "(//input[@class='siebui-ctrl-select siebui-input-popup siebui-align-left siebui-input-align-left ui-autocomplete-input'])[1]";
    public static String accountNumberField = "//input[@class='siebui-ctrl-input siebui-align-left siebui-input-align-left s_1_1_1_0']";
    public static String goButton = "//button[@class='siebui-ctrl-btn siebui-icon-find s_1_1_2_0 appletButton']";
    public static String targetAccountField = "//*[@id='1_s_1_l_Target_Account']";
    public static String sourceAccountField = "//*[@id='1_s_1_l_Source_Account']";
    public static String targetAccountYesNo = "//*[@id='Target_Account']/span[1]";
    public static String sourceAccountYesNo = "//*[@id='Source_Account']/span[1]";
    public static String addBtn = "//button[@title='Account Merger Selection List Applet:Add']";
    public static String mergeTargetAcnt = "//*[@id='1_s_2_l_Account_No']";
    public static String mergesourceAccnt = "//*[@id='2_s_2_l_Account_No']";
    public static String warningDiv = "//*[@id='_sweview_popup']";
    public static String divCloseBtn = "html/body/div[10]/div[1]/button";
    public static String mergeBtn = "//button[@title='Account Merger Selection List:Merge']";
    public static String accountTypeField = "//*[@id='1_s_2_l_Type']";
    public static String accountStatusField = "//*[@id='1_s_2_l_Account_Status']";
    public static String acctBal = "//input[@aria-label='Acct Bal']";
    public static String pptlBal = "//input[@aria-label='PPTL Bal']";
    public static String mergeSuccesMessageDiv = "html/body/div[10]";
    public static String successMessage = "html/body/div[10]/div[2]";
    public static String divOKBtn = "//button[@id='btn-accept']";
    public static String deviceCount = "//input[@aria-labelledby='DeviceSoldCount_Label']";
    public static String vehicleCount = "//input[@aria-labelledby='VehicleCount_Label']";
    public static String mergeListTable = "//*[@id='s_2_l']/tbody";
    public static String deleteBtn = "//*[@id='s_2_1_3_0_Ctrl']";
    public static String okBtn="//*[@id='btn-accept']";
    public static String refereceTxtBox="//input[@class='siebui-ctrl-select siebui-input-popup siebui-align-left siebui-input-align-left ui-autocomplete-input']";
    public static String referenceBtn="//button[@class='siebui-ctrl-btn siebui-icon-mergeraccounts s_2_1_0_0 appletButton']";
    public static String afterMergeClickIdentificationElement="//div[contains(text(),'Accounts have been merged successfully')]";
    public static String mergingViewer = "//*[contains(text(),'Account Merger View') and @class='ui-tabs-anchor']";
 public static String contactTab="//a[text()='Contacts']";
    
    public static String contactDetailsDeaceased="//td[contains(@id,'_Vector_Contact_Deceased')]";   
    public static String contactDetailsLstNameTxtBox="//input[@name='Last_Name']";
    public static String contactNewBtn="//button[@title='Contacts:New']";
    public static String deceasedRadioBtn="//input[@id='1_Vector_Contact_Deceased']/following::span[2]";
    public static String deceasedSaveBtn="//button[@title='Contacts:Save']";
    public static String checkBoxDeaceased="(//span[@class='siebui-icon-checkbox-checked'])[2]";
    public static String accountMergingViewLink = "//a[text()='Account Merger View']";
}
