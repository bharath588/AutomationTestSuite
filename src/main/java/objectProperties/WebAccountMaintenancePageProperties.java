package objectProperties;

/**
 * Created by c5063105 on 2/24/2017.
 */
public class WebAccountMaintenancePageProperties {

    public static String xpathUserName ="//input[@id='tt_username1']";
    public static String xpathPassword ="//input[@id='tt_loginPassword1']";
    public static String xpathLoginButton ="//button[@name='btnLogin']";
    public static String xpathErrorMessage ="//div[contains(text(),'We were unable to verify the data you entered, please try again.')]";
    public static String xpathLoginHome ="//h4[contains(text(),'ACCOUNT')]";
    //public static String xpathUsernameErrorMessage = "//*[contains(text(),'Please enter your Username')]";
    public static String xpathUsernameErrorMessage = ".//*[@class='error_msg'])[1]";
    public static String xpathLogout = "//*[@id='account_nav_panel']//a[contains(text(),'Log Out')]";

    public static String xpathSecurityQuestions = "//b[contains(text(),'Setup your your security questions and click UPDATE to save your changes.')]";
    public static String xpathFirstSecurityQuestion = "//select[@name='challengeQuestion']";
    public static String xpathSecondSecurityQuestion = "//select[@name='challengeQuestionTwo']";
    public static String xpathThirdSecurityQuestion = "//select[@name='challengeQuestionThree']";
    public static String xpathFirstSecurityAnswer = "//input[@name='challengeAnswer']";
    public static String xpathSecondSecurityAnswer = "//input[@name='challengeAnswerTwo']";
    public static String xpathThirdSecurityAnswer = "//input[@name='challengeAnswerThree']";
    public static String xpathUpdateButton = "//button[contains(text(),'UPDATE')]";

    public static String xpathAccountProfile = "//*[@id='acctmenu']//a[contains(text(),'Account Profile')]";
    public static String xpathProfile = "//b[text()='PROFILE']";

    public static String xpathEditSecurityQuestionProfile = "//a[@id='showPasswordLink']";
    public static String xpathSecurityVerification = "//b[text()='UPDATE']";
    public static String xpathSecurityQuestion1Profile = "//b[text()='SECURITY QUESTIONS']/..//br[1]";
    public static String xpathSecurityQuestion2Profile = "//b[text()='SECURITY QUESTIONS']/..//br[2]";
    public static String xpathSecurityQuestion3Profile = "//b[text()='SECURITY QUESTIONS']/..//br[3]";

  
    //boopathi
    public static String xpathHomeTab ="//a[@id='home']";
    public static String xpathOverviewTab="//a[text()='Overview']";
    public static String xpathAccountSettingsandPayments="//div/a[contains(text(),'EDIT ACCOUNT SETTINGS AND PAYMENT METHODS')]";
    public static String xpathCardTypeDropdown="//select[@name='cardType']";
    public static String xpathCardNumberField="//input[@name='accountNumber']";
    public static String xpathMonthDropdown="//select[@name='expMonth']";
    public static String xpathYearDropdown="//select[@name='expYear']";
    public static String xpathSubmitBtn="//*[@name='btnSave']";
    public static String xpathPaymentType="//div/b[text()='PAYMENT TYPE']";
    
  

    public static String xpathAddaCreditCard="//div[@class='col-md-4']/a";
    public static String xpathCardHolderFirstName="//input[@name='cardFirstName']";
    public static String xpathCardHolderMiddleName="//input[@name='cardMiddleName']";
    public static String xpathCardHolderLastName="//input[@name='cardLastName']";
    public static String xpathAddPaymentText="//div[@class='bach']/h1";
    public static String xpathNewCardNumberField="//input[@name='ccAccountNoMasked']";
    public static String xpathCVV2Code="//input[@name='securityCode']";
    public static String xpathNewCardAddressLine1="//input[@name='billingAddressLine1']";
    public static String xpathNewCardAddressLine2="//input[@name='billingAddressLine2']";
    public static String xpathNewCardCity="//input[@name='cardCity']";
    public static String xpathNewCardStateDropdown="//select[@name='cardStateType']";
    public static String xpathNewCardCountryDropdown="//select[@name='countryType']";
    public static String xpathNewCardZipcode="//input[@name='cardZipCode']";
    public static String xpathAlternatePhone = "xpath=//div[@id='phoneRow_1']//select";
    public static String xpathAlternatePhoneInput = "//input[@id='firstPhone1']";
    
    
  public static String xpathPassword1 ="//input[@id='tt_loginPassword1']";

    //***************************************
    //public static String xpathVehiclesTransponders = "//*[@id='acctmenu']//a[@title='Transponders & Vehicles']";
    public static String xpathVehiclesTransponders = "//*[@id='acctmenu']//a[@title='Transponders and Vehicles']";
    public static String xpathVehicleLostStolen = "//*[@id='resultTransponderid']//tr[1]//*[@id='selectBox']";
    public static String xpathVehicleTagNoOfVehicleLostStolen  = "//*[@id='resultTransponderid']//tr[1]/td[1]";
    public static String ReportLostorStolenValue = "Report lost or stolen";
    public static String xpathTypeOfLostStolen = "//select [@name='newStatus']";
    public static String xpathContinueBtn = "//*[@name='btnContinue']";
    public static String xpathAgreeChk = "//*[@id='agreeChk']//ins";
    public static String xpathStolenLostBtn = "//*[@id='btnReportLostStolen']";
    public static String xpathSeDeviceNumber = "//*[@aria-describedby=' s_2_1_0_0_icon']";
    //Device Number
    public static String xpathEnterDevNo= ".//*[@aria-labelledby='QuerySrchSpec_Label']";
    public static String xpathDeviceList= ".//*[@summary='Assigned Device List']";
    public static String siebelURL1="http://10.36.20.4/callcenter_enu/start.swe?";
    public static String loginUsernameTxtBox1="id=s_swepi_1";
    public static String loginPasswordTxtBox1="id=s_swepi_2";
    public static String signInBtn1=".//*[@id='s_swepi_22']";
    public static String deviceTab="//a[contains(text(),'Devices')]";

    public static String xpathFName= "//input[@name='fname']";
    public static String xpathLName= "//input[@name='lname']";
    public static String xpathReasontoContact = "//select[@name='areaOption']";
    public static String xpathReason = "//select[@name='probOptions']";
    public static String xpathEmailId = "//*[@id='emailId']";
    public static String xpathRetypeEmailId = "//*[@id='retype_emailId']";
    public static String xpathPhoneNumber = "//*[@id='phoneNo']";
    public static String xpathFeedBack= "//*[@id='textarea_feedback']";


    public static String xpathAccountsTab="//*[contains(text(),'Accounts') and @class='ui-tabs-anchor'][1]";
    public static String xpathAccountNumberTxtBox="//input[@aria-label='Account Number' and @aria-labelledby='CSN_Label']";
    public static String xpathGoBtn="//*[@title='Search:Go']";

    public static String contactusLink="//a[text()=' Contact Us']";
    //ublic static String submit="//button[@name='btnSubmit']";
    //public static String message="//b";

   /* public static String xpathAddBulkVehicle= ".//button[@name='btnAddBulkVehicle']";
    public static String xpathUploadBtn = "//input[@name='fileUpload']";
    public static String xpathAddBulk = "//button[@name='btnAddBulk']";*/
    public static String xpathUpdatedMsg = ".//div[@class='message_tag']/div";
    public static String xpathAccountLink = ".//*[@id='acctmenu']/div/ul/li[2]/a";
    // public static String xpathLogout = "//*[@id='account_nav_panel']//a[contains(text(),'Log Out')]";
    public static String xpathBtnUpdate = "//*[@name='btnUpdate']";
    public static String xpathAddress1="//input[@name='addressLine1']";
    public static String xpathAddress2="//input[@name='addressLine2']";
    public static String xpathCity="//input[@name='city']";
    public static String xpathState="//select[@name='stateType']";
    public static String xpathZipCode="//input[@name='zipCode']";
    public static String xpathpZipCode="//input[@name='pzipCode']";
    public static String xpathFirstPhone="//input[@id='firstPhone1']";
    public static String xpathPhoneType="//*[@id='phoneRow_1']/div[1]/select";
    public static String xpathCountry="//select[@name='countryType']";
  
  
  public static String xpathoverViewTab="(//*[contains(text(),'Overview') and @class='active'])[3]";
       public static String xpathVehiclesAndTranspondersTab="(//*[contains(text(),'Transponders & Vehicles')])[2]";
       public static String xpathvehiclesAndTranspondersTxt="//h2[contains(text(),'Transponders & Vehicles')]";
       public static String xpathvehiclesAndTranspondersTxt2="//h2[contains(text(),'Transponders')]";

//Properties to edit vehicles field

       public static String xpathEditBtn="(//a[contains(text(),'Edit')])[1]";
       public static String xpathEditVehicleTxt="//h1[contains(text(),'Edit a Vehicle')]";
       public static String xpathWebPlateNbrTxtBox="//input[@name='licensePlate']";
       public static String vehicleMakeDropDown="//select[@name='vehicleMake']";
       public static String vehicleStateDropDown="//select[@name='plateState']";
       public static String xpathVehicleModelTxtBox="//input[@name='vehicleModel']";
       public static String xpathVehicleYearTxtBox="//input[@name='vehicleYear']";
       public static String xpathUpdateVehicleBtn="//button[@name='btnSave']";
       public static String xpathVehicleInfoChangeSuccessAlert=
                        "//*[contains(text(),'The vehicle information was successfully changed.')]";
       public static String xpathAddVehicleAlert="(//button[@class='btn btn-cancel btn-block'])[1]";

       
       
       public static String xpathAddAnotherVehicleBtn="//button[@name='btnAddVehicle']";
       public static String plateStateDropDown="//select[@name='plateState']";
       public static String xpathVehicleColorTxtBox="//input[@name='iagCode']";
       public static String xpathAddVehicleBtn="//button[@name='btnAddVehicle']";
       public static String xpathUpdateCancelBtn="//button[@name='btnCancel']";
       
//Properties under Vehicles tab under Siebel Application      
       
       public static String xpathAccountInfoTab="//a[contains(text(),'Account Info') and @class='ui-tabs-anchor']";
       public static String xpathAccountInfoAddressesTab="//*[contains(text(),'Addresses') and @class='ui-tabs-anchor']";
       public static String xpathAccountInfoVehiclesTab="//*[contains(text(),'Vehicles') and @class='ui-tabs-anchor']";
       public static String xpathVehicleHistoryTab="//*[contains(text(),'Vehicle History') and @class='ui-tabs-anchor']";
       public static String xpathVehicleListTable="//table[@summary='Vehicles']";
       public static String xpathVehicleListQueryBtn="//button[@title='Vehicles:Query']";
       public static String xpathPlateNumberTxtBox="//input[@name='Plate_Number']";
       public static String xpathVehicleListGoBtn="(//button[@title='Vehicles:Go'])[1]";
       
       public static String xpathVehicleHistoryTable="//table[@summary=' Account Vehicle History List Applet']";
       public static String xpathVehicleHistoryQueryBtn="//button[@title=' Account Vehicle History List Applet:Query']";
       public static String xpathStatusTxtBox="//input[@name='Status']";
       public static String xpathPlateNbrElement="//td[@id='1_s_2_l_Plate_Number']";
       public static String xpathPlateNbrTxtBox="//input[@name='Plate_Number']";
       public static String xpathVehicleHistoryGoBtn="(//button[@title=' Account Vehicle History List Applet:Go'])[1]";
       
       
//Properties under change password field
       
       public static String xpathForgotPasswordLink ="//a[contains(text(),'Forgot your password or username?')]";
       public static String xpathUserNameTxtBox="//input[@name='username']";
       public static String xpathZipCodeTxtBox="//input[@name='pzipCode']";

       public static String xpathSecurityQATxtBox="//input[@name='challengeAnswer']";
       public static String xpathResetPasswordTxt="//h1[contains(text(),'Reset Password')]";
       public static String xpathTypePasswordTxtBox="//input[@name='newPassword']";
       public static String xpathReTypePasswordTxtBox="//input[@name='retypePassword']";
       public static String xpathSaveBtn="//button[contains(text(),'SAVE')]";
       public static String xpathPasswordChangeSuccessAlert=
                        "//*[contains(text(),'Password Changed Successfully')]";
       
       
//Properties under change security question field      
       
       public static String xpathEditAccountSettingLink="//a[contains(text(),'EDIT ACCOUNT SETTINGS AND PAYMENT METHODS')]";
       public static String xpathAccountProfileTxt="//h2[contains(text(),'Account Profile')]";
       public static String xpathEditPasswordSecurityQALink="//a[@id='showPasswordLink']";
       public static String xpathOldPasswordTxtBox="//input[@name='currentPassword']";
       public static String xpathNewPasswordTxtBox="//input[@name='newPassword']";
       public static String xpathRetypeNewPasswordTxtBox="//input[@name='retypeNewPassword']";
       public static String xpathUpdatePasswordSecurityQABtn="//button[@name='btnSavePassword']";
       public static String firstSecurityQuestionDropdown="//select[@name='challengeQuestion']";
       public static String xpathFirstSecurityQATxtBox="//input[@name='challengeAnswer']";
       public static String xpathAccountDetailsUpdateSuccessAlert=
                        "//*[contains(text(),'Account Details Updated Successfully')]";
       public static String xpathPasswordResetBtn="//button[@name='btnReset']";
       public static String xpathChangePasswordTxt="//h4[contains(text(),'Change Password')]";


    public static String xpathEmail = "//input[@name='emailAddress']";
    public static String xpathReEmail = "//input[@name='reEmailAddress']";
    public static String xpathSecondaryEmail = "//input[@name='secondaryEmailAddress']";
    public static String xpathSecondaryReEmail = "//input[@name='reSecondaryEmailAddress']";

    public static String xpathAddressLine1 = "//input[@name='addressLine1']";
    public static String xpathAddressLine2 = "//input[@name='addressLine2']";

    public static String xpathZip = "//input[@name='zipCode']";

    public static String xpathZipPlus = "//input[@name='zipCodePlus']";

    public static String xpathStandardizeRadioButton = "//input[@id='radio2']";
    public static String xpathStandardizeOkButton= "//button[contains(text(),'OK')]";

   

    public static String xpathForgotPassword = "//a[contains(text(),'Forgot your password or username?')]";
    public static String xpathResetPasswordText1 = ".//*[contains(text(),'Reset Password')]";//*[@id='content']//td[text()='Reset Password']";

    public static String xpathSubmitButton1 = "//button[text()='Submit']";
    public static String xpathResetPasswordText2 = "//*[contains(text(),'Answer the security question to reset your password.')]";
    public static String xpathSubmitButton2 = "//input[@name='challengeAnswer']/../..//button[text()='Submit']";
    public static String xpathVerificationMessage = "//div[contains(text(),'Your answer did not match. Please enter the correct answer for the security question.')]";
    public static String xpathUsername2 = "//input[@name='userName']";
    //public static String xpathUpdateButton2 = "//button[@name='btnUpdate']";
    public static String xpathUpdateButton2 = "(.//button[@name='btnUpdate'])[1]";
    public static String xpathSuccess = "//div[@class='message_tag']//div";

    public static String xpathPasswordForgot = "//input[@id='tt_newPassword']";
    public static String xpathRePasswordForgot = "//input[@id='tt_retypePassword']";
    public static String xpathSaveButton = "//button[text()='SAVE']";
    public static String xpathChangePasswordSuccess = "//div[@class='alert alert-success message_tag']";
    public static String xpathManagePaymentsLink="//a[contains(text(),' Methods')]";
    public static String xpathPaymentEditImage="//img[contains(@src,'edit.gif')]";


     public static String dropdownhistory="//select[@id='j_s_sctrl_tabView']";
     public static String nonFinancialsHistoryTab="//*[contains(text(),'Non Financials') and @class='ui-tabs-anchor']";

     //  178

    public static String pintxtbox="//input[@name='securityPin']";
    public static String updatebtn="//button[@name='btnSavePassword']";
    public static String updatebtn1=".//button[@class='btnbt btn-lg btn-submit btn-block']";
    public static String noThnksbtn="//button[text()='No, thanks']";

    //087
    //public static String contactusLink="//a[text()=' Contact Us']";
    public static String fnametxt="//input[@name='fname']";
    public static String lnametxt="//input[@name='lname']";
    public static String issueCategory="//select[@name='areaOption']";
    public static String issue="//select[@name='probOptions']";
    public static String emailId="//input[@name='eid']";
    public static String retypeEmailId="//input[@name='retypeEid']";
    public static String phoneNo="//input[@name='phoneNo']";
    public static String comment="//textarea[@name='otherDetails']";
    public static String submit="//button[@name='btnSubmit']";
    public static String message="//b";

    //007
    public static String vehiclesAndTransponder="(//a[@title='Vehicles & Transponders'])[2]";
    public static String addVehiclebtn="//button[@name='btnAddVehicle']";
    public static String licencePlatetxt="//input[@name='licensePlate']";
    public static String errorMsg="//div[@class='alert alert-danger error_msg']";


    //154
    public static String caseradiobtn=".//input [@value='case']";
    public static String casenumbertxt="//input[@name='caseNumber']";
    public static String lastname="//input[@name='lastName']";
    public static String casesubmit="//button[@name='btnSubmit']";
    public static String caseTable="//table[@id='resultVehicleid']";
    //public static String getAccNo=".//*[@id='content']//h4']";
    public static String getAccNo="//h4[contains(text(),'ACCOUNT')]";
    public static String scrollToTranspnder="//*[@id='resultTransponderid']//tr[1]/td[1]";
    public static String icheckboxMinimal=".//*[@class='icheckbox_minimal']";
    public static String divIcheckboxMinimal="//div[@class='icheckbox_minimal']";
    public static String welcomeMsg="(.//div[@class='Welcome'])[1]";
    public static String accountList="//button[@title='Vector Account List:Go']";
    public static String accountCSN="//a[contains(@name,'CSN')]";
    public static String deviceHistory="//a[contains(text(),'Device History')]";
    public static String deviceHistoryList=".//*[@summary='Vector Account Device History List']";
    public static String historyLink="//a[text()='History']";//"//*[contains(text(),'History') and @class='ui-tabs-anchor']";
    public static String activitiesTable="//table[@summary='Activities']";
    public static String activitiesTableXpath="./*//*[@summary='Activities']";
    public static String xpathOfChallengeQuestion="//select[@name='challengeQuestion']";
    public static String xpathOfChallengeQa="//input[@aria-label='Challenge QA']";
    public static String xpathOfChallengeAnswer="//input[@aria-label='Challenge Answer']";
    public static String xpathOfHistory="//a[text()='History']";
    public static String xpathOfNonFinancialHistory="//a[contains(text(),'Non Financials')]";
    public static String xpathOfSRCopy="//button[@title='Service Requests:Copy Case']";
    public static String xpathOfPwdBtn=".//*[@id='Password_RowId_4']/div[2]/button";
    public static String xpathNonFinancialHistory="//a[contains(text(),'Non Financials')]";
    public static String xpathOfIcheckMail=".//*[@class='icheckbox_minimal']";
    public static String xpathOfVehicleMake="//td[@id='1_s_2_l_Vehicle_Make']";
    public static String xpathOfVehicleStatus="//td[@id='1_s_2_l_Status']";
    public static String xpathOfYearOfVehicle="//td[@id='1_s_2_l_Year_of_Vehicle']";
    public static String xpathOfVehicleModel="//td[@id='1_s_2_l_Vehicle_Model']";
    public static String xpathOfVehicleid="//*[@id='resultVehicleid']/tbody/tr[1]/td[3]";
    public static String xpathOfTableAddress="//table[@summary='Addresses']";
    public static String xpathOfPostalCode="//td[@id='1_s_3_l_Postal_Code']";
    public static String xpathOfLogin="//input[@name='login']";
    public static String xpathOfNoThanks="//button[contains(text(),'No, thanks')]";
    public static String xpathOfChatMsg="//a[contains(text(),'Activities - Chat Message')]";
    public static String xpathOfPAsswordLink=".//*[@id='showPasswordLink']";
    public static String xpathOfEmailAddress="//*[@aria-label='Email Address']";
    public static String xpathOfmyModal=".//*[@id='myModal']";

    //82
    public static String xpathAddBulkVehicle= ".//button[@name='btnAddBulkVehicle']";//1
    public static String xpathUploadBtn = "//input[@name='fileUpload']"; //2
    public static String xpathAddBulk = "//button[@name='btnAddBulk']";//3
    public static String xpathConfrmBulk = ".//button[@name='btnConfirm']";//4
    public static String xpathVehiclesTable = "//table[@id='resultVehicleid']";//5
    public static String xpathVehiclesAdded = "//*[@id='resultTransponderid']/tbody/tr/td[2]";//5
    public static String xpathVehiclesAdded1 = "//*[@id='resultVehicleid']/tbody/tr[1]/td[1]";//5

    public static String xpathUploadConfrm=".//*[@class='panel-body']/div";//Files have uploaded Successfully
    
    public static String editProfileLink = "//a[contains(text(),'Edit Profile')]";
    public static String managePaymenttMethodsLink = "(//a[contains(text(),'Manage Payment Methods')])[3]";
    public static String addCreditCardBankAccNumLink = "//a[contains(text(),'ADD A CREDIT CARD/ BANK ACCOUNT')]";
    public static String creditcardsTbl = "//table[@id='paymentItemid']";
    public static String xpathUploadConfrmNew=".//*[contains(text(),'File(s) have uploaded Successfully.You will get confirmation email once your file processed')]";


    //Siebel and web properties by Debasmita

    public static String siebelAcountInfoTab="//a[contains(text(),'Account Info')]";
    public static String siebelAddressesTab="//a[contains(text(),'Addresses')]";
    public static String siebelAddressesTable="//table[@summary='Addresses']";
    public static String addressesQueryBtn="//button[@title='Addresses:Query']";
    public static String addressesGoBtn="(//button[@title='Addresses:Go'])[1]";
    public static String addressTypeTxtBox="//input[@name='Address_Type']";
    public static String addressLine1Txt="//td[@id='1_s_3_l_Street_Address']";
    public static String zipCodeTxt="//td[@id='1_s_3_l_Postal_Code']";
    public static String cityTxt="//td[@id='1_s_3_l_City']";
    public static String stateTxt="//td[@id='1_s_3_l_State']";


   // public static String webAccountProfileTab="(//a[contains(text(),'Account Profile')])[3]";
    public static String updateAccountProfileBtn="(//button[@name='btnSavePassword'])[1]";
    public static String managePaymentMethodLink="//a[contains(text(),'Manage Payment Methods')]";
    public static String managePaymentMethods="//h2[contains(text(),'Manage Payment Methods')]";
    public static String addCreditCardOrBankAccountBtn="//a[contains(text(),'ADD A CREDIT CARD/ BANK ACCOUNT')]";
    public static String addPaymentMethodTxt="//h1[contains(text(),'Add a Payment Method')]";
    public static String addPaymentInformationTxt="//b[contains(text(),'Add Payment Information')]";
    public static String cardTypeDropDown="//select[@name='cardType']";
    public static String accountNumberTxtBox="//input[@name='accountNumber']";
    //public static String creditCardNbrTxtBox="//input[@name='maskedCardNumber']";
    public static String cardNumberEnterTxtBox="//input[@name='maskedCardNumber']";
    public static String continuePaymentBtn="(//button[@name='btnContinue'])[1]";
    //public static String continuePaymentBtn=".//*[@class='check_paytype']//button[@name='btnContinue']";
    public static String expiryMonthDropDown="//select[@name='expMonth']";
    public static String expiryYearDropDown="//select[@name='expYear']";
    public static String cardCVVNbrTxtBox="//input[@name='securityCode']";
    public static String billingAddressTxtBox="//input[@name='billingAddressLine1']";
    public static String cityNameTxtBox="//input[@name='cardCity']";
    public static String stateNameDropDown="//select[@name='cardStateType']";
    public static String zipCodeTxtBox="//input[@name='cardZipCode']";
    public static String submitPaymentBtn="//button[@name='btnSave']";
    //public static String continuePaymentBtn="//button[@name='btnContinue']";
    public static String popUpCloseBtn="//button[contains(text(),'Close ')]";
    public static String creditCardAddedSuccessMsg="//div[contains(text(),'Your Credit Card was added.')]";
    public static String bankDetailsAddedSuccessMsg="//div[contains(text(),'Your Bank Account was added.')]";
    public static String historyTab="//a[text()='History']";
    public static String historyFinancialsTab="(//a[text()='Financials'])[2]";
    public static String financialsTable="//table[@summary='Account Financials History List']";

    public static String transpondersAndVehiclesTab="//a[contains(text(),'Transponders and Vehicles')]";
    public static String transpondersTxt="//h1[contains(text(),'TRANSPONDERS')]";
    public static String firstTransponderTxt="//*[@id='resultTransponderid']/tbody/tr[1]/td[1]";
    public static String plansTab="(//a[@title='Plans'])[3]";
    public static String plansTxt="//h1[contains(text(),'Plans')]";
    public static String planNameDropDown="//select[@name='tagSpecificPlanName']";
    public static String transponderNbrTxtBox="//input[@name='addTransponderNumber']";
    public static String transponderTypeDropDown="//select[@name='addTransponderType']";
    public static String addPlanTransponderBtn="//button[@name='btnAddPlan']";
    public static String planSummaryTxt="//h1[contains(text(),'Plan Summary')]";
    public static String continueToPaymentBtn="//button[@name='btnAddPlan']";
    public static String selectPaymentMethodTxt="//p[contains(text(),'Select the payment method and click Continue')]";
    public static String cardRowDropDown="//select[@name='selectedCardRowId']";
    public static String continueForPaymentBtn="(//button[@name='btnContinue'])[2]";
   // public static String addAndPaywithNewCardLink="//a[contains(text(),'Add and pay with a new credit card / bank account')]";
    public static String addAnotherPhoneNbrLink="//a[contains(text(),'+ Add another Phone number')]";
    public static String selectPhoneTypeDropDown="(//select[@name='phoneTypeUpdate'])[2]";
    public static String phoneDetailsTxtBox="(//input[@name='phonedetails'])[2]";
    public static String webURL="http://138.69.165.241/vector/account/home/accountLogin.do";
    public static String deviceRequestTab="//a[contains(text(),'Device Request')]";

    public static String deviceRequestListTable="//table[@summary='Device Request List']";

    //***********************
    public  static  String managePayementMethod="//*[@id='acctmenu']//a[contains(text(),'Manage Payment Methods')]";

    public static String accountInfoBtn="//a[contains(text(),'Account Info')]";
    public static String accountRebillInfoBtn="//a[contains(text(),'Rebill Info')]";
    public static String replenishmentAppletTable="//table[@summary='Replenishment Info List Applet']";
    public static String replenishmentHistoryTab="//a[contains(text(),'Replenishments History')]";
    public static String replenishmentHistoryAppletTable="//table[@summary='Replenishment Info History List']";

    public static String addFundsLink="(//a[contains(text(),'Add Funds')])[6]";
    public static String addAndPayWithNewCardBankAccountLink="//a[contains(text(),'Add and pay with a new credit card / bank account')]";
    public static String oneTimePaymentOptionCheckBox="(//input[@name='easyPayOption'])[2]";
    public static String financialsTab2="(//a[contains(text(),'Financials')])[2]";
    public static String reversalsTab="//a[contains(text(),'Reversals')]";
    public static String purchaseAdditionalTranspondersBtn="//button[contains(text(),'PURCHASE ADDITIONAL TRANSPONDER(S)')]";
    public static String portableDeviceCountTxtBox="//input[@name='portableTransponder']";
    public static String continueMailBtn="//button[@name='btnContinuemail']";
    public static String orderTransponderTxt="//h1[contains(text(),'Order a Transponder')]";
    public static String financialTransactionListTable="//table[@summary='Financial Transaction List']";
    public static String paymentAmountTxtBox="//input[@name='transactionAmount']";
    public static String accountBalanceTxt="//h3[contains(text(),'Account Balance:')]";
    public static String returnToMyAccountBtn="//button[@name='btnreturn']";
    public static String replenishmentAddConfirmationMsg="//p[contains(text(),'Your payment of ')]";
    public static String accountFinancialHistoryListTable="//table[@summary='Account Financials History List']";
    public static String useAccountAddressCheckBox="//input[@name='useAcntAddr4Bill']";
    public static String cityTxtBox="//input[@name='billCity']";
    public static String stateDropDown="//select[@name='state']";
    public static String countryDropDown="//select[@name='country']";
    public static String zipCode1TxtBox="//input[@name='zipCode']";
    public static String errorMsgTxt="//div[contains(text(),'Please Fix the Error(s) Below')]";
    public static String errorMsgContentTxt="//div[contains(text(),'Please enter an amount of ')]";
    public static String addAnotherVehicleLink="//button[@name='btnAddVehicle']";
    public static String licensePlateNbrTxt="//*[@id='resultVehicleid']/tbody/tr/td[1]";
    public static String purchaseAdditionalTranspondersLink="//button[@name='btnOrderTollTag']";
    public static String selectNewStatusDropDown="(//select[@name='newStatus'])[1]";
    public static String plateNumberTxtBox="//input[@name='plateNumber']";
    public static String associatePlateSaveBtn="//button[@name='btnSaveAssociatePlate']";
    public static String accountThresholdAmountTxt="//input[@aria-label='Rebill Thrsh Amt']";

    public static String selectPhoneTypeDropDown3="(//select[@name='phoneTypeUpdate'])[3]";
    public static String phoneDetailsTxtBox3="(//input[@name='phonedetails'])[3]";
    public static String selectPhoneTypeDropDown2="(//select[@name='phoneTypeUpdate'])[2]";
    public static String phoneDetailsTxtBox2="(//input[@name='phonedetails'])[2]";
    public static String accountDetailsUpdatedTxt="//*[contains(text(),'Account Details Updated Successfully')]";
    public static String preSetAmountTxtBox="(//input[@name='replenishmentAmount'])[1]";
    public static String accountInfoTabSiebel="//a[contains(text(),'Account Info')]";
    public static String rebillInfoTab="//a[contains(text(),'Rebill Info')]";
    public static String replenishmentInfoTable="//table[@summary='Replenishment Info List Applet']";
    public static String bankAccountIcon="//a[contains(text(),'Bank Account')]";
    public static String bankAccountIcon1="//i[@class='fa fa-bank']";
    public static String bankAccountTypeDropDown="//select[@name='bankAccountType']";
    public static String accountNbrTxtBox="//input[@name='bankAccountNumber']";
    public static String accountNbrTxtBox1="//input[@name='bankAccountNumber']";
    public static String confirmAccountNbrTxtBox="//input[@name='bankReAccountNumber']";
    public static String bankRoutingNbrTxtBox="//input[@name='bankRoutingNumber']";
    public static String easyPayOptionCheckBox="(//input[@name='easyPayOption'])[1]";

    public static String addressLine1TxtBoxNew="//input[@name='billAddrLine1']";
    public static String addressLine1TxtBox="//input[@name='billingAddressLine1']";
    public static String addressLine2TxtBoxNew="//input[@name='billAddrLine2']";
    public static String addressLine2TxtBox="//input[@name='billingAddressLine2']";
    public static String cityTxtBoxNew="//input[@name='billCity']";
    public static String stateDropDownNew="//select[@name='state']";
    public static String countryDropDownNew="//select[@name='country']";
    public static String zipCode1TxtBoxNew="//input[@name='zipCode']";
    public static String confirmAccountNbrTxtBoxNew="//input[@name='bankReAccountNumber']";

}
