package objectProperties;

/**
 * Created by C5063105 on 2/6/2017.
 */
public class SblCaseManagementProperties {

  //***************************************************************
  public static String XpathQueryQueue = "//input[@aria-label='Queue']";
  public static String XpathQuerySRPriority = "//input[contains(@aria-label,'SR Priority')]";
  public static String XpathQuerySubQueue = "//input[@aria-label='Sub Queue']";
  public static String XpathQueryGo = "//button[@aria-label='Service Request:Go']";
  public static String XpathQuerySRNumber = "//input[@aria-labelledby='s_2_l_SR_Number ']";
  public static String xpathOfQuery  ="//button[@id='s_2_1_19_0_Ctrl']";
  public static String xpathMyServices  ="//option[contains(.,'My Service Requests')]";
  public static String sitemapFilterInput=".//*[@id='sitemapFilterInput']";

  public static String xpathServiceReqQueryField = "//button[@title='Service Request:Query']";
  public static String xpathServiceRequestSecondGoBtn ="(//button[@data-display='Go'])[2]";
  public static String xpathServiceRequestShowMore = "//span[contains(@title,'Service Request:Show more')]";
  public static String xpathMoreInfo = "//a[text()='More Info']";
  public static String xpathServiceRequestHoldExpDate = "//input[@aria-label='Hold Expiry Date']";
  public static String xpathHoldReason = "//input[@aria-label='Hold Reason']";
  public static String xpathClosureText = "//input[@aria-label='Closure Code']";

public static String accountsTab="(//*[contains(text(),'Accounts') and @class='ui-tabs-anchor'])[1]";
public static String accountInfoTab="//*[contains(text(),'Account Info') and @class='ui-tabs-anchor']";
public static String financialsTab="//*[contains(text(),'Financials')]";
public static String myServiceRequestLinkDropdown="//select[@name='s_vis_div']";
public static String queryBtn="//button[@title='Service Requests:Query']";
public static String xpathSubStatus="//input[@aria-label='SR Sub Status']";
public static String xpathgoLink="//button[@title='Service Request:Go']";
public static String XpathServiceReqListTableRow="//table[@summary='Service Request List']/div[1]/table/tbody/tr[1]/td";
public static String serviceReqTab="//a[@href='#s_sctrl_tabView_noop' and text()='Service Request']";
public static String newServiceReqBtn="//button[@title='Service Requests:New']";
public static String xpathClosureCode="//input[@aria-label='Closure Code']";
public static String serviceReqListTable="//table[@summary='Service Request List']";
public static String clickOnApprove="//button[@title='Adjustment Entry Form:Approve']";
public static String xpathclosureCodeTxtBtn="//input[@aria-label='Closure Code']";
public static String xpathServiceRequestTab = "//a[text()='Service Requests']";
public static String xpathServiceRequestList = "//a[text()='Service Requests List']";
public static String xpathNewServiceRequestButton = "//button[contains(@title,'Service Requests:New')]/span[contains(text(),'New')]";
public static String newServiceRequestbtn="//button[@name='s_1_1_166_0']";
public static String xpathServiceRequestId = "//div[@class='siebui-applet-title']//label";
public static String xpathServiceRequestSaveButton = "//button[contains(@aria-label,'Service Requests:Save')]/span[text()='Save']";
public static String xpathAccountNumber ="//input[contains(@aria-label,'Account Number')]";
public static String xpathSRPriority ="//input[contains(@aria-label,'SR Priority')]";
public static String xpathQueue = "//input[@aria-label='Queue']";
public static String xpathSubQueue = "//input[@aria-label='Sub Queue']";
public static String xpathSRStatus = "//input[contains(@aria-label,'SR Status')]";
  public static String xpathSRSubStatus = "//input[contains(@aria-label,'SR Sub Status')]";
public static String xpathDescription = "//textarea[@aria-labelledby='Description_Label']";
public static String xpathNotes = "//input[contains(@aria-label,'Notes')]";
public static String xpathAttachments = "//a[contains(text(),'Attachments')]";
public static String xpathNewAttachFileButton = "//input[contains(@title,'Attachments:New File')]";
public static String xpathAttachmentGoButton ="//button[contains(@aria-label,'Attachments:Go')]";
public static String xpathView = "//select[@name='s_vis_div']";
public static String xpathQuery = "//button/span[text()='Query']";
public static String xpathServiceRequestFieldEnable = "//td[@id='1_s_2_l_SR_Number']";
public static String xpathServiceRequestTextField ="//input[@id='1_SR_Number']";
public static String xpathServiceRequestGoButton ="//button[@aria-label='Service Requests:Go']";
public static String xpathResult="//span[contains(text(),'1 - 1 of 1')]";
public static String xpathNotesTab = "//a[text()='Notes']";
public static String xpathNotesNewButton ="//button[contains(@aria-label,'Notes:New')]";
public static String xpathNotesFocus = "//*[@id='1_s_2_l_Note']/div";
public static String xpathNotesText = "//textarea[@id='1_Note']";
public static String xpathNotesCount = "//span[@id='s_2_rc' and text()='1 - 1 of 1']";
public static String xpathNotesSaveButton = "//button[@aria-label='Notes:Save']";
public static String xpathServiceRequestHistoryTab = "//a[text()='Service Request History']";
public static String xpathServiceReuestSearchField = "//input[contains(@aria-labelledby,'SRNumber_Label')]";
public static String xpathServiceRequestSearchGo = "//button[contains(@aria-label,'Search:Go')]";
public static String xpathAttachedDoc = "//a[text()='Test_doc']";
public static String xpathServiceRequestTable = "//table[@summary='Service Request List']";
public static String xpathServiceRequestHistoryTable = "//table[@summary='Vector SR History']";
public static String xpathServiceRequestHistoryGoButton = "//button[@aria-label='Vector SR History:Go']";
public static String xpathServiceRequestFirstName = "//input[@aria-labelledby='Contact_First_Name_Label']";
  public static String xpathServiceRequestAccountName = "//input[@aria-labelledby='Account_Name_Label']";
  public static String xpathServiceRequestCellularPhone = "//input[@aria-labelledby='Work_Phone_#_Label']";
public static String xpathServiceRequestLastName = "//input[@aria-labelledby='Contact_Last_Name_Label']";
public static String xpathServiceRequestEmail = "//input[@aria-labelledby='Email_Label']";
public static String xpathServiceRequestOwnerField = "//input[@aria-label='Owner']/..//span";
public static String xpathOwnerPopupSearchField = "//input[@aria-label='Find']";
public static String xpathOwnerpopupSearchValue = "//input[@aria-label='Starting with']";
public static String xpathOwnerPopupHeaderField = "//span[text()='Pick Service Request Owner']";
public static String xpathOwnerPopupGoButton = "//td[@class='siebui-popup-filter']//button[@aria-label='Pick Service Request Owner:Go']";


public static String xpathStatus="//td[@id='1_s_2_l_SR_Status']";
public static String xpathStatustxtbox="//input[@name='SR_Status']";

public static String xpathClosurecode="//td[@id='1_s_2_l_Reason_Code']";
public static String xpathClosurecodetxtbox="//input[@name='Reason_Code']";

public static String serviceRequestListTable="//table[@summary='Service Request List']";
public static String copyCaseBtn="//button[@title='Service Requests:Copy Case']";

public static  String saveBtn="//button[@title='Service Requests:Save']";
public  static String serviceRequestHistory="//a[text()='Service Request History']";

public static  String vectorServiceRequestHistory="//table[@summary='Vector SR History']";
public static String navigateBtn="//button[@title='Service Requests:Navigate']";
public static String queryStatus="//td[@id='1_s_2_l_SR_Status']";

public static String queryDescription="//td[@id='1_s_2_l_Description']";
public static String internalControlsQueryBtn="//button[@title='Internal Control Threshold List:Query']";
public static String intenalControlsQueryPaytype="//td[@id='1_s_1_l_Pay_Type']";
public static String intenalControlsQueryPaytypetxtbox="//input[@name='Pay_Type']";

public static String internalControlsQueryTransactiontype="//td[@id='1_s_1_l_Transaction_Type']";
public static String internalControlsQueryTransactiontypetxtbox="//input[@name='Transaction_Type']";
public static String internalControlQueryGobtn="//button[@title='Internal Control Threshold List:Go']";
public static String internalControlsThresholdTable="//table[@summary='Internal Control Threshold List']";

public static String internalControlThresholdNewbtn="//button[@title='Internal Control Threshold List:New']";
public static String internalControlThresholdAgency="//td[@id='1_s_1_l_Agency_ID']";
public static String internalControlThresholdAgencytxtbox="//input[@name='Agency_ID']";

public static String internalControlThresholdAgencyName="//td[@id='1_s_1_l_Agency_Name']";
public static String internalControlThresholdAgencyNametxtbox="//input[@name='Agency_Name']";

public static String internalControlThresholdResponsibility="//td[@id='1_s_1_l_Responsibility']";
public static String internalControlThresholdResponsibilitytxtbox="//input[@name='Responsibility']";

public static String internalControlThresholdStore="//td[@id='1_s_1_l_Store']";
public static String internalControlThresholdStoretxtbox="//input[@name='Store']";

public static String internalControlThresholdPaytype="//td[@id='1_s_1_l_Pay_Type']";
public static String internalControlThresholdPaytypetxtbox="//input[@name='Pay_Type']";

public static String internalControlThresholdTransactiontype="//td[@id='1_s_1_l_Transaction_Type']";
public static String internalControlThresholdSTransactiontypetxtbox="//input[@name='Transaction_Type']";

public static String internalControlThresholdDefaultBatch="//td[@id='1_s_1_l_Default']";
public static String internalControlThresholdSDefaultBatchtxtbox="//input[@name='Default']";

public static String internalControlThresholdInitiationAmount="//td[@id='1_s_1_l_Initiation_Amt']";
public static String internalControlThresholdInitiationtxtbox="//input[@name='Initiation_Amt']";

public static String internalControlThresholdApprovalAmount="//td[@id='1_s_1_l_Approval_Amt']";
public static String internalControlThresholdApprovaltxtbox="//input[@name='Approval_Amt']";

public static String internalControlThresholdEscalationHeirarchy="//td[@id='1_s_1_l_Escalation_Hierarchy']";
public static String internalControlThresholdEscalationHeirarchytxtbox="//input[@name='Escalation_Hierarchy']";

public static String internalControlThresholdTransferRole="//td[@id='1_s_1_l_Transfer_Role']";
public static String internalControlThresholdTransferRoletxtbox="//input[@name='Transfer_Role']";
public static String vectorInternalProcessParametersTab="//a[text()='Vector Internal Process Parameters']";
public static String vectorInternalProcessParametersQuerybtn="//button[@title='Vector Process Parameters:Query']";

public static String vectorInternalProcessQueyType="//td[@id='1_s_1_l_Type']";
public static String vectorInternalProcessQueyTypetxtbox="//input[@name='Type']";


public static String vectorInternalProcessParametersTypetxtbox=" //input[@name='s_1_1_1_0']";
public static String vectorInternalProcessParametersGobtn="(//button[@title='Vector Process Parameters:Go'])[1]";
public static String vectorInternalProcessParametersTable="//table[@summary='Vector Process Parameters']";
//public static String accountsTab="(//*[contains(text(),'Accounts') and @class='ui-tabs-anchor'])[1]";
public static String adjustmentTab="//a[contains(text(),'Adjustments')]";
public static String adjustmentNewbtn="//button[@title='Adjustment Entry Form:New']";
public static String adjustmentCategorytxtbox="//input[@name='s_3_1_6_0']";
public static String adjustmentSubCategorytxtbox="//input[@name='s_3_1_7_0']";
public static String adjustmentUnitpricetxtbox="//input[@name='s_3_1_5_0']";
public static String adjustmentSavebtn="//button[@title='Adjustment Entry Form:Save']";
public static String adjustmentBtn="//button[@title='Adjustment Entry Form:Adjust']";

public static String CRMfinancialsTab="//*[contains(text(),'Financials')]";

public static String activityTemplate="//a[text()='Activity Templates']";
public static String activityTemplateQueryBtn="//button[@title='Activity Templates:Query']";
public static String activityTemplateQueryName="//td[@id='1_s_1_l_Name']";
public static String activityTemplateQueryNametxt="//input[@name='Name']";
public static String activityTemplateQueryGoBtn="//button[@title='Activity Templates:Go']";

public static String activityTemplateListTable="//table[@summary='Admin Premise List']";
public static String welcomeText="(.//div[@class='Welcome'])[1]";

public static String serviceRequestNewBtn="//button[@title='Service Request:New']";

public static String Queue ="//input[@aria-label='Queue']";
public static String queueList="//li[text()='ESCALATION']";
public static String SubQueue ="//input[@aria-label='Sub Queue']";

public static String invoiceBtn="//input[@aria-label='Invoice#']//following::span[1]";
public static String invoiceSelectBtn="//button[@title='Pick an Invoice:Select']";
public static String serviceRequestLabel="//label[@id='s_1_1_68_0_Label']";
public static String activityTab="//a[text()='Activities']";

public static String activitylistTable="//table[@summary='Order Dashboard Details']";

public static String activityTemplateDetailsTab="//a[text()='Activity Template Details']";
public static String activityTemplateDetailsTable="//table[@summary='Premise Meter Location List']";

//public static  String srStatus="//input[@aria-label='SR Status']";

public static String employees="(//a[text()='Employees'])[4]";

public static String srOwnertxt="//input[@aria-label='Owner']";

public static String activityStatus="//td[@id='1_s_2_l_Activity_Status']";

public static String activityStatustxt="//input[@name='Activity_Status']";
//public static String linkFinancial="//a[@href='#s_sctrl_tabView_noop' and text()='Financials']";


public static String clickOnNewBtnOfAdjustment="//button[@title='Adjustment Entry Form:New']";
public static String xpathcategoryTxtBox="//input[@aria-label='Category']";
public static String clickOnAdjustment="//a[@href='#s_vctrl_div_tabScreen_noop' and text()='Adjustments']";
public static String xpathQuantityTxtBox="//input[@aria-label='Quantity']";
public static String xpathSubCategoryTxtBox="//input[@aria-label='Sub Category']";
public static String xpathUnitPriceTxtBox="//input[@aria-label='Unit Price']";
public static String clcikOnSaveAdjustment="//button[@title='Adjustment Entry Form:Save']";
public static String clickOnAdjust="//button[@title='Adjustment Entry Form:Adjust']";

public static String ownerFieldSrch = "//input[@aria-labelledby='Owner_Label']";
public static String queueFieldHomePageSrch = "//input[@aria-labelledby='Area_Label']";
public static String requestDropdown = "//select[@title='Visibility']";
public static String srStatus = "//input[@aria-label='SR Status']";
public static String closureCodePopUp="//*[@id='_sweview_popup']";
public static String okButton="//*[@id='btn-accept']";
public static String xpathServiceReqSummary="//table[contains(@summary,'Service Request List')]";
public static String replinshmentCategory="//input[@aria-label='Category']/following::span";
public static String reRecCategory="//td[text()='RETAILREC']";
public static String okButtonCategory="//button/span[text()='Ok']";

public static String escalationTab="//a[@href='#s_vctrl_div_tabScreen_noop' and text()='Escalation']";
public static String rejectButton="//button[@aria-label='Reversal:Reject']";
  public static String adjustmentRejectButton = "//button[@aria-label='Adjustment Entry Form:Reject']";
  public static String adjustmentApproveButton = "//button[@aria-label='Adjustment Entry Form:Approve']";
  public static String adjustmentApproveOk = "//button[@id='btn-accept']";
public static String xpathrejectCode="//input[@aria-label='Reject Code']";
public static String rejectCodesaveBtn="//button[@title='Reject Code:Save']";
public static String xpathserviceReqListTable="//table[@summary='Service Request List']";

//public static String accountBalTxtBox="//input[@aria-label='Acct Bal']";

public static String hometab="//a[text()='Home']";

public static String amounttxt="//input[@aria-label='Amount']";

public static String accountReplenishmentNewbutton="//button[@title='Replenishment Entry Form:New']";
public static String accountReplenishmentSavebutton="//button[@title='Replenishment Entry Form:Save']";
public static String Paymentnewbutton="//button[@title='Payments List:New']";
public static String accountReplenishmentPaymentSave="//button[@id='saveReBlInf']";
public static String Reversalapprovebutton="//button[@title='Reversal:Approve']";
public static String sitemapFileter="//input[@id='sitemapFilterInput']";
  public static String queryDescriptiontxtbox="//textarea[@name='Description']";


// public static String siteMapImage="//span[@class='siebui-icon-tb-sitemap ToolbarButtonOn glyphicon']";

public static String accountReplenishmentTab="//a[text()='Account Replenishment']";
public static String accountReplenishmentNewbtn="//button[@title='Replenishment Entry Form:New']";
  public static String xpathrejectCodeXpath = "//input[@aria-label='Reject Code']";


public static String diputeProcess="//button[@title='Process']";
  public static String diputeApprove="//button[@title='Approve']";
  public static String processbutton="//button[@title='Reversal:Process']";
  public static String adjustProcess="//button[@title='Adjustment Entry Form:Process']";
//////////////////////////////////
  
  public static String financialTransListTbl = "//table[@summary='Financial Transaction List']";
  public static String allSrsDropDown="//select[@name='s_pdq']";
  
  
  



}
