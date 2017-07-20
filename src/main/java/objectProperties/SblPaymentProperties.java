package objectProperties;

public class SblPaymentProperties {
      public static String initiationAmountTxtBox="//input[@name='Initiation_Amt']";
    public static String approvalAmountTxtBox="//input[@name='Approval_Amt']";
    public static String initiationTextBox="//td[@tabindex='-1' and text()='100']";
    public static String approvalTextBox="//td[@tabindex='-1' and text()='10,000']";
   public static String categoryTxtBox="//input[@aria-label='Category']";

    public static String siebelURL="http://10.36.20.4/callcenter_enu/start.swe?";
     public static String homePageVerificationTxt="(.//div[@class='Welcome'])[1]";
    //Properties for Logout
    public static String logoutSettingImage="//li[@id='tb_0']";
    public static String logoutButton="//span[@class='siebui-icon-logout']";

    public static String financialsTab="//*[contains(text(),'Financials')]";
    public static String adjustmentsTab="//a[contains(text(),'Adjustments')]";
    public static String adjustmentsNewBtn=".//*[@id='s_3_1_19_0_Ctrl']";
    public static String categoryLabel="//input[contains(@aria-labelledby,'Category_Label')]";
    
    
    public static String selectCategory=".//*[@id='s_4_1_25_0_Ctrl']";
    public static String unitPriceLabel="//input[contains(@aria-labelledby,'Unit_Price_Label')]";
    public static String quantityLabel="//input[contains(@aria-labelledby,'Quantity_Label')]";
    public static String reasonCodeLabel="//input[contains(@aria-labelledby,'Reason_Code_Label')]";
    public static String adjustButton="//button[contains(@title,'Adjustment Entry Form:Adjust')]";
    public static String xpathOfVechiclesTables=".//*[@summary='Financial Transaction List']";
    public static String financialTransactionListTable = ".//*[@summary='Financial Transaction List']";
    public static String tabView2=".//*[@id='j_s_sctrl_tabView']";
    public static String tabView2History=".//*[@id='j_s_sctrl_tabView']/option[4]";
    public static String tabView2Fianancial="//li[@aria-controls='s_vctrl_div_tabScreen_noop']/a[text()='Financials']";
    public static String tabView2FianancialHistory=".//*[@summary='Account Financials History List']";
    public static String subCategoryLabel="//input[@aria-label='Sub Category']";
    public static String adjustQuantity=".//input[@aria-label='Quantity']";
    public static String balanceTransferTab="//a[contains(.,'Balance Transfer')]";
    public static String balanceTransferNewBtn=".//button[@title='Balance Transfer:New']";
    public static String transferDestinationAccount=".//input[@aria-labelledby='Transfer_Destination_Account_Label']";
    public static String transferTypeLabel=".//input[@aria-labelledby='Transfer_Type_Label']";
    public static String transferPrice=".//input[@aria-labelledby='Transfer_Amount_Label']";
    public static String transferReason=".//input[@aria-labelledby='Transfer_Reason_Label']";
    public static String saveBalanceTransfer=".//button[@title='Balance Transfer:Save']";
    public static String processBalTrnsfer=".//button[@title='Balance Transfer:Process']";
    public static String balTransferAlert="//div[@id='_sweview_popup']";
    public static String balTransferAlertAccept="//button[@id='btn-accept']";
    public static String pptlBalance=".//input[@aria-labelledby='Prepaid_Balance_Label']";
    
    
    public static String okBtn = "//*[@id='btn-accept']";
    public static String balanceForwardTxtBox="//input[@aria-labelledby='BalanceForward_Label']";
    
    public static String accountFinHistoryListViewBtn="//button[@title='Account Financials History List:View CC']";
    
    
    public static String compositeTransactionIdTxtBox="//input[@aria-label='Composite Transaction Id']";
    
    public static String internalControlThresholdQueryBtn="//button[@title='Internal Control Threshold List:Query']";
    public static String responsibilityElement="//td[@id='1_s_1_l_Responsibility']";
    public static String responsibilityTxtBox="//input[@name='Responsibility']";
    public static String paytypeElement="//td[@id='1_s_1_l_Pay_Type']";
    public static String transactionTypeElement="//td[@id='1_s_1_l_Transaction_Type']";
    public static String internalControlThresholdGoBtn="(//button[@aria-label='Internal Control Threshold List:Go'])[1]";
    public static String intrernalControlThresholdTbl=".//table[@summary='Internal Control Threshold List']";
    
    public static String xpathOfFinancialTable = ".//*[@summary='Service Request List']";
    
    public static String amountTxtBox="//input[@aria-label='Amount']";
    public static String accountFinancialStatusTxtBox="//input[@aria-labelledby='AccountFinancialStatus_Label']";
    public static String xpathOfAmountTable = "//table[@summary='Account Financials History List']";
    
    public static String paymentsGoBtn="//button[@title='Payments List:Go']";
    public static String paymentNotesLink="//a[text()='Payment Notes']";
    public static String paymentsListTbl="//table[@summary='Payments List']";
    
    public static String reverseChkBox="//td[@id='2_s_1_l_Reversed' and @title='Checked']";
    public static String financialTransListTbl="//table[@summary='Financial Transaction List']";
   // public static String xpathOfFinancialTable = ".//*[@summary='Service Request List']";
    public static String newButtonRefund="//button[@title='Refund Entry Form:New']";
    public static String refundButton="//a[@href='#s_vctrl_div_tabScreen_noop' and text()='Refunds']";
    public static String refundCategoryLabel="//input[@aria-labelledby='Category_Label']";
    public static String paymentRefundLabel="//input[@aria-labelledby='Payment_Category_Label']";
    public static String amountLabel="//input[@aria-labelledby='Amount_Label']";
    public static String refundCodeLabel="//input[@aria-labelledby='Vector_Refund_Code_Label']";
    public static String refundSaveBtn="//button[@title='Refund Entry Form:Save']";
    public static String refundBtn="//button[@title='Refund Entry Form:Refund']";
    public static String financialTransactionTable="//table[@summary='Financial Transaction List']";
    public static String popUpWindowOkCategoryLabelRefund="//button[@title='Select Category and Sub Category:Ok']";
    public static String popUpWindowPaymentTransactionRefund="//button[@title='Payment Transaction:OK']";
    public static String firstLevelViewBar="//select[@aria-label='First Level View Bar']";
    
    
    
  //added properties for newly added test cases
    public static String selectRefundAutomationCheck="//select[@aria-label='First Level View Bar']/option[text()='Refund Check Automation']";
    public static String queryRefundAutomationCheckBtn="//button[@title='Refund Check Automation:Query']";
    public static String accountNumTxtBox="//input[@name='Accnt_Num']";
    public static String refundCheckStatusGoBtn="//button[@title='Refund Check Automation:Go']";
    public static String refundCheckStatusApproveBtn="//button[@title='Refund Check Automation:Approve']";
    public static String refundCheckAutomationTable="//table[@summary='Refund Check Automation']";
    public static String printerSubmittedMessage="//div[@id='_sweview_popup']";
    public static String clickOnOkPopupWindowForPrintingSubmissionMessage="//button[@id='btn-accept']";
    public static String clickOnPrintButton="//button[@title='Refund Check Automation:Print']";
  
    
}


