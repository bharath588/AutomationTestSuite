package objectProperties;

/**
 * Created by C5056047 on 1/11/2017.
 */
public class SiebelShiftManagement {

    public static String csrCloseoutMenu = "//a[@class='ui-tabs-anchor' and text()='CSR Closeout']";
    public static String csrDepositTab = "//a[contains(text(),'CSR Deposit')]";
    //public static String goBtn = "id=s_2_1_2_0_Ctrl";
    public static  String todTable = ".//*[@id='s_2_l']/tbody";
    public static String depositVerificationTab = "//a[text()='Deposit Verification']";
    public static String supervisorCloseOutTab = "//a[contains(text(),'Supervisor Closeout')]";
    public static String warningDiv = "//*[@id='_sweview_popup']";
    public static String acceptBtn = "id=btn-accept";
    public static String cancelBtn = "html/body/div[10]/div[1]/button";

    public static  String xpathTourOfDutyTable="//table[@summary='TourOfDuty']";
    public static String openingBalElement = "(//td[contains(@aria-labelledby,'s_2_l_altCalc')])[1]";
    public static String openingBalTxtBox = "//input[@name='Vector_CSR_Opening_Balance']";
    public static String newBtnCsrcloser = "//button[@title='CSR Closeout Deposit:New']";
    public static String editBtnCsrcloser = "//button[@title='CSR Closeout Deposit:Edit']";
    public static  String cashTxtBox = "//input[@aria-label='Cash']";
    public static String dollarsTxtBox = "//input[@aria-label='Dollars']";
    public static String cents1TxtBox = "//input[@aria-label='Cents']";
    public static String saveBtnCsrColouser = "//button[@title='CSR Closeout Deposit:Save']";
    public static String submitDepositBtnCsrColouser = "//button[@title='CSR Closeout Deposit:Submit Deposit']";
    public static String queryTxtBox = "//input[@aria-labelledby='QueryComboBox_Label']";
    public static String queryValueTxtBox = "//input[@aria-labelledby='QuerySrchSpec_Label']";
    public static  String goBtn = "(//span[text()='Go'])[2]";
    public static String okBtn = "//button[@id='btn-accept']";
    public static String goBtnOnPaymentPage="(//button[@title='Open Items List:Go'])[2]";
    public static String vectorInternalProcessParameters="//a[text()='Vector Internal Process Parameters']";
    public static String paramValueElement="//td[@id='1_s_1_l_Param_Value']";
    public static String paramValTxtBox = "//input[@name='Param_Value']";
    public static String dropDownIcon =  "(//span[@class='siebui-icon-dropdown applet-form-combo applet-list-combo'])[1]";
    public static String dropDownListItem="(//ul[@class='ui-autocomplete ui-front ui-menu ui-widget ui-widget-content'])[1]";

    public static String systemDepositCashAmount = "//input[@aria-label='Syscashamt']";
    public static String csrDepositCashAmount = "//input[@aria-label='Csrcashamt']";
    public static String varianceCashAmount = "//input[@aria-label='Variant CASH Amount']";
    public static String queryBtn="//button[@title='My Reports:Query']";
    
    public static String todStatusElement = "//td[@id='1_s_1_l_TOD_Status']";
    public static String todStatusTxtBox = "//input[@name='TOD_Status']";
    public static String openingBalEle ="//*[@id='1_s_2_l_Vector_CSR_Opening_Balance']";
    public static String closeoutDepositMenuBtn="//button[@title='CSR Closeout Deposit Menu']";
    public static String depositsVerificationEdit="//button[@title='CSR Deposits Verification:Edit']";
    public static String totalAmoutBtn="//button[contains(@title,'Total Amount')]";
    public static String verificationCountTxtBox="//input[@aria-label='Verification Count']";
    public static String verificationSubmitBtn="//button[@title='CSR Deposits Verification:Submit Verification']";
    public static String verificationSaveBtn="//button[@title='CSR Deposits Verification:Save']";
    public static String depositMismatchChkBox="//td[contains(@id,'_Deposit_Mismatch')]//span[@class='siebui-icon-checkbox-checked']";
    public static String endDateColumn = "//div[@id='jqgh_s_2_l_Actual_End_Date_Time']";
    public static String sortBtn = "//li[@data-caption='Sort']";
    public static String ccField="//*[@id='CCField']";
    public static String pdr55Btn = "//button[@title='TourOfDuty:PDR 55']";
    public static String tourOfDutyqueryBtn = "//button[@title='TourOfDuty:Query']";
    public static String todTxtBox = "//input[@name='TOD_Id']";
    public static String storeIdElement="//td[@id='1_s_1_l_Vector_Store_Id']";
    public static String storeIdTxtBox = "//input[@name='Vector_Store_Id']";
    public static String todGoBtn = "//button[@title='TourOfDuty:Go']";
    public static String todElement1="//td[@id='1_s_1_l_TOD_Id']";
    
}
