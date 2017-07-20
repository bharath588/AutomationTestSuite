package objectProperties;

/**
 * Created by 50021824 on 06-02-2017.
 */
public class SblAccountLinkingPageObject
{
    public static String LinkAccounts=".//*[@title='Sub Accounts:Link Accounts']";
    public static String AddAccountsAccountNumberFiled="//td[contains(@id,'1_s_3_l_CSN')]";
    //public static String ChildAccountnumberFiled="//input[@id='1_CSN']";
    public static String ChildAccountnumberFiled="//input[@id='1_CSN']";
    public static String addAcccountsSourceElement = "//td[contains(@id,'_l_Calc_Link_Account_Source')]";
    public static String AddAccountsSourceField="//input[@name='Calc_Link_Account_Source']";
    public static String AddAccountsGoButton="//button[@title='Add Accounts:Go']";
    public static String AddAccountsOKButton="//button[@title='Add Accounts:OK']";
    public static String xpathOfSubAccountsTable = "./*//*[@summary='Sub Accounts']";
    public static String accountInfoTab="//*[contains(text(),'Account Info') and @class='ui-tabs-anchor']";
    public static String dropDownIcon = "//span[@class='siebui-icon-dropdown']";
    public static String historyLink =  "//a[text()='History']";
    public static String nonFinancialsLink = "//a[contains(text(),'Non Financials')]";
    public static String activitiesChatMessageLink= "//a[contains(text(),'Activities - Chat Message')]";
    public static String notificationHistoryLink="//a[text()='Notification History']";
    public static String notificationHistoryLink1="(//a[text()='Notification History'])[2]";
    public static String notificationsHistoryTbl = "//table[@summary='Notifications']";
    public static String nonFinancialHistoryTbl="//table[@summary='Activities']";
    public static String DisassociateLink = "//button[@title='Sub Accounts:Disassociate Link']";
    public static String LinkRequestSReject="//button[@title='Link Account Request:Reject']";
    //public static String LinkAccounts=".//*[@title='Sub Accounts:Link Accounts']";
    public static String LinkRequestSApprove="//button[@title='Link Account Request:Approve']";
    public static String subAccountLink = "//a[text()='Sub Accounts']";

    public static String xpathNotes="//a[contains(text(),'Notes')]";
    public static String xpathPalns="//a[contains(text(),'Plans')]";
    public static String xpathNotesTable="//table[@summary='Notes List']";
    public static String xpathPlansTable="//table[@summary='Plan Details List']";
    
}
