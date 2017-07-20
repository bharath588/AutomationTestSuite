package objectProperties;

/**
 * Created by C5056047 on 1/17/2017.
 */
public class WebAccountCreationPageProperties {

    public static String welcomeMsg="(.//div[@class='Welcome'])[1]";
    public static String xpathFirstName="//input[@name='firstName']";
    public static String xpathMiddleName="//input[@name='middleInitial']";
    public static String xpathLastName="//input[@name='lastName']";
    public static String siebelURL1="http://10.36.20.4/callcenter_enu/start.swe?";
    public static String loginUsernameTxtBox1="id=s_swepi_1";
    public static String loginPasswordTxtBox1="id=s_swepi_2";
    public static String signInBtn1=".//*[@id='s_swepi_22']";
    public static String xpathUploadBtn = "//input[@name='fileUpload']"; //2
    public static String xpathSecondaryFirstName="//input[@name='contactnamesid[0].secondaryFirstName']";
    public static String xpathSecondaryMiddleName="//input[@name='contactnamesid[0].secondaryMiddleName']";
    public static String xpathSecondaryLastName="//input[@name='contactnamesid[0].secondaryLastName']";
    public static String xpathAddSecondaryContactBtn="//a[contains(text(),'+ Add Secondary Contact ')]";

    public static String xpathSunpass="//*[@name='parkingOption']";
    public static String xpathMobileAlert="//input[@id='smsOpt0']";
    public static String xpathLocalGovt="//*[@name='nonRevenueOption']";


    public static String xpathAddress1="//input[@name='address1']";
    public static String xpathAddress2="//input[@name='address2']";
    public static String xpathCity="//input[@name='city']";
    public static String xpathState="//select[@name='stateType']";
    public static String xpathZipCode="//input[@name='zipCode1']";
    public static String xpathCountry = "//select[@name='countryType']";
    public static String xpathEmail="//input[@name='email']";
    public static String xpathReEmail="//input[@name='reEmail']";
    public static String xpathFirstPhone="(//input[@name='firstPhone'])[1]";
    public static String xpathPhoneType="//select[@name='phoneTypeUpdate']";
    public static String xpathLanguageType ="//select[@name='languageType']";
    public static String xpathStatemenType ="//select[@name='statementType']";


    public static String xpathUserName="//input[@name='userName']";
    public static String xpathPassword="//input[@name='signUpPassword']";
    public static String xpathReTypePassword="//input[@name='rePassword']";
    public static String xpathSecurityQuestion="//select[@name='securityQuestion']";
    public static String xpathSecurityAnswer="//input[@name='securityAnswer']";

    public static String xpathIpin="//input[@name='digitPin']";
    public static String xpathCommunicationPreferencesBtn="//button[@name='btnCommunicationPreferences']";
    public static String xpathStandardizeRadioButton = "//input[@id='radio2']";
    public static String xpathStandardizeOkButton= "//button[contains(text(),'OK')]";
    public static String xpathTollTagsVehiclesButton= "//button[contains(text(),'NEXT: TOLL TAGS & VEHICLES')]";
    public static String xpathVehicleCountry = "//select[@name='vehicleid[0].vehicleCountry']";
    public static String xpathVehicleState = "//select[@name='vehicleid[0].vehicleState']";
    public static String xpathRadioBtnForAdrress="//input[@name='radio']";
    public static String xpathAddressAcceptenceBtn="//button[@name='btnAcceptAddress']";
    public static String xpathTollTagsBtn="//button[@name='btnTollTagsVechicles']";
    public static String xpathVehicleLicence="//input[@name='vehicleid[0].vehicleLicense']";
    public static String xpathVehicleMake="//select[@name='vehicleid[0].vehicleMake']";
    public static String xpathVehicleModel="//input[@name='vehicleid[0].vehicleModel']";
    public static String xpathVehicleYear="//input[@name='vehicleid[0].vehicleYear']";
    public static String xpathVehicleColor="//input[@name='vehicleid[0].vehicleColor']";
    public static String xpathVehicleEffectiveStartDate="//input[@name='vehicleid[0].effectiveBeginDate']";
    public static String xpathVehicleEffectiveStartTime="//input[@name='vehicleid[0].effectiveBeginTime']";

    public static String xpathAddAnotherVehicleLink="//a[contains(text(),'Add another vehicle')]";
    public static String xpathPortableTollTagsNo ="//input[@name='portableTollTagsNo']";
    public static String xpathPlansAndPaymentButton="//button[@name='btnPlansAndPayment']";
    public static String xpathVehicle = "//input[@name='vehicleid[0].vehicleLicense']";
    public static String xpathPortableTransponder = "//input[@id='portableId']";
    public static String xpathMiniStickerTransponder = "//input[@id='stickerId']";
    public static String xpathPlansAndPayment = "//button[contains(text(),'NEXT: PLANS AND PAYMENT')]";

    public static String xpathCardType = "//select[@name='creditCardType']";
    public static String xpathCardNumber = "//input[@name='cardNumberMasked']";
    public static String xpathCardExpMonth = "//select[@name='creditCExpMonth']";
    public static String xpathCardExpYear = "//select[@name='creditCExpYear']";
    public static String xpathCardSecurityCode = "//input[@name='securityCode']";

    public static String xpathEasyPaySaveFutureOneTimePayments="//input[@name='easyPayOption' and @id='storedCard']";


    //Bank Account
    public static String xpathBankAccount="(//div[@class='tabs-section']/ul/li)[2]";
    public static String xpathBankAccountType="//select[@name='bankAccountType']";
    public static String xpathBankNameOnAccount="//input[@name='bankNameOnAccount']";
    public static String xpathBankAccountNumber="//input[@name='bankAccountNumberMasked']";
    public static String xpathRetypeBankAccountNumber="//input[@name='retypeBankAccountNumberMasked']";
    public static String xpathBankRoutingNumber="//input[@name='bankRoutingNumber']";
    public static String xpathEditPaymentInfo="//div[@class='bach']/div[6]/div[2]/a";
    public static String xpathTermsAndConditions=".//*[@class='icheckbox_minimal']";
    public static String xpathBillingAddress1="//*[@name='bankBillingAddressLine1']";
    public static String xpathBillingAddress2="//*[@name='bankBillingAddressLine2']";
    public static String xpathBillingAddressbankCity="//*[@name='bankCity']";
    public static String xpathBillingAddressbankZipCode="//*[@name='bankZipCode']";

    public static String xpathConfirmation = "//button[contains(text(),'NEXT: CONFIRMATION')]";
    public static String xpathAgreeCheckBox = "//input[@name='agreed']";
    public static String xpathCompleteButton = "//button[@id='btnComplete']";

    //button[@class='btn btn-default']
    public static String xpathTotalAmount = "//p[@id='tr_tollTagDeposit']";
    public static String xpathAddrPayment = "//label[text()='Account Details:']/../div[1]";
    public static String xpathMailPayment = "//label[text()='Account Details:']/../div[2]";
    public static String xpathCardFirstName = "//input[@name='cardFirstName']";
    public static String xpathCardMiddleName = "//input[@name='cardMiddleName']";
    public static String xpathCardLastName = "//input[@name='cardLastName']";
    public static String xpathBillingAddress1Payment = "//input[@id='billingAddressLine1Id']";
    public static String xpathBillingAddress2Payment = "//input[@id='billingAddressLine2Id']";
    public static String xpathStatePayment = "//select[@id='cardStateTypeId']";
    public static String xpathCountryPayment = "//select[@id='countryTypeId']";
    public static String xpathZipPayment = "//input[@id='cardZipCodeId']";
    public static String xpathCityPayment = "//input[@id='cardCityId']";
    public static String xpathCardDetailsConfirmation = "//label[contains(text(),'Payment:')]/..//p";
    public static String xpathPlanPortableTransponderCount = "(//select[@id='_chk'])[1]";
    public static String xpathPlanStickerTransponderCount = "(//select[@id='_chk'])[2]";

    //siebel

    public static String xpathAccountNumber =  "//input[@aria-label='Acct #']";
    public static String xpathAccountBalance =  "//input[@aria-label='Acct Bal']";
    public static String xpathPptlBalance =  "//input[@aria-label='PPTL Bal']";
    public static String xpathRebillInfo =  "//input[@aria-label='Rebill Type']";
    public static String xpathVehicleNumber = "//input[@aria-label='# of Vehicles']";

    public static String xpathVehiclesTabCount = "//span[@id='s_2_rc']";
    public static String xpathDevicesTab = "//a[text()='Devices']";
    public static String xpathDeviceRequestTab = "//a[contains(text(),'Device Request')]";
    public static String xpathDeviceDropdown = "//div[@class='siebui-btn-grp-search']/input[@name='s_2_1_1_0']";
    public static String xpathDeviceTextbox = "//div[@class='siebui-btn-grp-search']/input[@name='s_2_1_0_0']";
    public static String xpathGoButton = "//*[@id='s_2_1_2_0_Ctrl']";
    public static String xpathDeviceRequestPageVisiblity = "//div[text()='Device Request List']";
    public static String xpathPlansTextbox = "//input[@aria-label='Starting with']";
    public static String xpathPlansGoButton = "//button[@id='s_2_1_0_0_Ctrl']";
    public static String xpathPlansListPageVisiblity = "//div[text()='Plan Details List']";
    public static String xpathPlansTab = "//a[text()='Plans']";

    public static String xpathReferenceNumber=".//form[@name='communicationPreferenceForm']/div[2]/div";
    public static String xpathStatusPercentage="//span[@class='percentage']";
    public static String xpathMaskedAccountNumber="(//p[@class='form-control-static'])[6]";
    public static String xpathStax="//div[@class='bach']/div[6]/table/tbody/tr[2]/td[2]";
    public static String xpathTotalValue="//div[@class='bach']/div[6]/table/tbody/tr[7]/td[2]/b";


    public static String xpathSunPassPortableInternalMount="(//table[@id='commuter']//..//select)[1]";
    public static String xpathSunPassMiniSticker="(//table[@id='commuter']//..//select)[2]";


}
